package com.qunhe.toilet.core.service.impl;

import com.qunhe.toilet.core.common.settings.RedisChannel;
import com.qunhe.toilet.core.common.settings.SettingsHolder;
import com.qunhe.toilet.core.manager.RedisManager;
import com.qunhe.toilet.core.manager.ToiletPitInfoManager;
import com.qunhe.toilet.core.manager.ToiletPitUseRecordManager;
import com.qunhe.toilet.core.manager.ToiletSubRecordManager;
import com.qunhe.toilet.core.service.IntelligentToiletService;
import com.qunhe.toilet.facade.domain.common.condition.ToiletPitInfoCondition;
import com.qunhe.toilet.facade.domain.common.condition.ToiletPitUseRecordCondition;
import com.qunhe.toilet.facade.domain.common.exception.BusinessException;
import com.qunhe.toilet.facade.domain.common.param.TolietParam;
import com.qunhe.toilet.facade.domain.common.util.AssertUtil;
import com.qunhe.toilet.facade.domain.common.util.DateUtil;
import com.qunhe.toilet.facade.domain.common.util.JsonUtil;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitInfo;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitSubRecord;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitUseRecord;
import com.qunhe.toilet.facade.domain.vo.FloorPitInfoVo;
import com.qunhe.toilet.facade.domain.vo.ToiletPitInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.qunhe.toilet.core.common.settings.SettingsEnum.TOILET_PIT_SLEEP_EARLY_TIME;
import static com.qunhe.toilet.core.common.settings.SettingsEnum.TOILET_PIT_SLEEP_LATE_TIME;
import static java.util.Collections.EMPTY_LIST;

/**
 * @Author bupo
 * @DATE 2020/8/21 13:43
 * @Description
 */
@Slf4j
@Service
public class IntelligentToiletServiceImpl extends BaseServiceImpl implements IntelligentToiletService {

    @Resource
    ToiletPitInfoManager toiletPitInfoManager;
    @Resource
    ToiletPitUseRecordManager toiletPitUseRecordManager;

    @Resource
    ToiletSubRecordManager toiletSubRecordManager;
    @Resource
    RedisManager  redisManager;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public long dealToiletPitChangeAction(TolietParam param) {

        checkParam(param);

        /**
         * 1、占用记录处理
         */
        ToiletPitInfo toiletPitInfo = getPitInfo(param);
        if (param.isStatus()) {
            addUseRecord(toiletPitInfo);
        } else {
            releasePitUseRecord(toiletPitInfo);
        }
        /**
         * 2、更新坑位状态
         */
        updatePitUsingState(param, toiletPitInfo);
        Calendar nowTime = Calendar.getInstance();
        return getSleepTime(nowTime);
    }

    private void checkParam(TolietParam param) {
        AssertUtil.isNullObj(param.getBuildNo(), "建筑编号不可为空");
        AssertUtil.isNullObj(param.getFloorNo(), "楼层不可为空");
        AssertUtil.isNullObj(param.getRoomNo(), "房间编号不可为空");
        AssertUtil.isNullObj(param.getSexType(), "厕所类型(性别)不可为空");
        AssertUtil.isNullObj(param.getPitNo(), "坑位编号不可为空");
    }

    @Override
    public List<FloorPitInfoVo> getPitList(TolietParam param) {
        AssertUtil.isNullObj(param.getBuildNo(), "建筑编号不可为空");
        List<ToiletPitInfo> toiletPitInfo = getToiletPitInfos(param);
        if (isEmpty(toiletPitInfo)) {
            return EMPTY_LIST;
        }
        List<ToiletPitInfoVo> toiletPitInfoVos = new ArrayList<>(toiletPitInfo.size());
        toiletPitInfo.stream().forEach(toiletPitInfo1 -> {
            try {
                convertToiletPitInfoToVo(toiletPitInfoVos, toiletPitInfo1);
            } catch (Exception e) {
                log.warn("convertToiletPitInfoToVo  falied", toiletPitInfo1.getId(), e);
            }
        });
        // 根据楼层进行分组
        Map<Integer, List<ToiletPitInfoVo>> map = toiletPitInfoVos.stream().collect(Collectors.groupingBy(ToiletPitInfoVo::getFloorNo));
        List<FloorPitInfoVo> list = new ArrayList<>(map.size());
        Set<Map.Entry<Integer, List<ToiletPitInfoVo>>> set = map.entrySet();
        for (Map.Entry<Integer, List<ToiletPitInfoVo>> entry : set) {
            FloorPitInfoVo floorPitInfoVo = new FloorPitInfoVo();
            floorPitInfoVo.setFloorId(entry.getKey());
            floorPitInfoVo.setFloorNo(entry.getKey());
            floorPitInfoVo.setTolietRoomList(entry.getValue());
            list.add(floorPitInfoVo);
        }
        return list;
    }

    @Override
    public long getPitTime(TolietParam param) {
        Calendar nowTime = Calendar.getInstance();

        return getSleepTime(nowTime);
    }

    @Override
    public boolean subPit(TolietParam param) {
        AssertUtil.isNullParamStr(param.getLdap(),"预约需要填入花名拼音哦");
        AssertUtil.isNullObj(param.getFloorNo(),"预约楼层不可为空");
        //添加一条订阅记录  默认持续时间十五分钟
        ToiletPitSubRecord  record = new ToiletPitSubRecord();
        record.setFloorId(param.getFloorNo());
        Date  now = new Date();
        record.setNotifyStartTime(now);
        record.setNotifyEndTime(DateUtil.getAfterMinutes(now,15));
        record.setLdap( param.getLdap());
        record.setUid(0L);
        if(!toiletSubRecordManager.addRecord(record)){
            throw new BusinessException("抱歉，预约失败咯，请致电管理员吧");
        }
        return true;
    }

    @Override
    public boolean doSubPitEvent(String param) {
        // 根据释放的坑位的楼层归属  查询所有的关联的且还有效的预约记录进行企信消息推送  每个坑位发生释放动作就触发一次
        /**
         * 1、根据坑位信息获取到所有有效的订阅记录
         */

        ToiletPitInfo toiletPitInfo = JsonUtil.readValue(param,ToiletPitInfo.class);
        toiletPitInfo.getFloorNo();



        return false;
    }

    private List<ToiletPitInfo> getToiletPitInfos(TolietParam param) {
        ToiletPitInfoCondition condition =  convertParamToCondition(param);
        condition.setDeleted(false);
        condition.setBuildNo(param.getBuildNo());
        return toiletPitInfoManager.selectListByCondition(condition);
    }

    private void convertToiletPitInfoToVo(List<ToiletPitInfoVo> toiletPitInfoVos, ToiletPitInfo toiletPitInfo1) {
        ToiletPitInfoVo toiletPitInfoVo = new ToiletPitInfoVo();
        toiletPitInfoVo.setBuildingName(toiletPitInfo1.getBuildingNo() + "号楼");
        toiletPitInfoVo.setBuildNo(toiletPitInfo1.getBuildingNo());
        toiletPitInfoVo.setFloorNo(toiletPitInfo1.getFloorNo());
        toiletPitInfoVo.setRoomNo(toiletPitInfo1.getRoomNo());
        toiletPitInfoVo.setRoomName(toiletPitInfo1.getRoomNo() + "号室");
        toiletPitInfoVo.setPitNo(toiletPitInfo1.getPitPositionNo());
        toiletPitInfoVo.setUsingState(toiletPitInfo1.getUseState());

        int userdTime = getUserdTime(toiletPitInfo1);
        toiletPitInfoVo.setUsedTime(userdTime);
        toiletPitInfoVo.setSexType(toiletPitInfo1.getTolietType());
        toiletPitInfoVo.setAvailableSign("ok".equalsIgnoreCase(toiletPitInfo1.getAvailableStatus()));
        toiletPitInfoVo.setInconvenientSign(toiletPitInfo1.getInconvenientSign());
        toiletPitInfoVos.add(toiletPitInfoVo);
    }

    private int getUserdTime(ToiletPitInfo toiletPitInfo1) {
        int userdTime = 0;
        if (toiletPitInfo1.getUseState()) {
            ToiletPitUseRecordCondition toiletPitUseRecordCondition = new ToiletPitUseRecordCondition();
            toiletPitUseRecordCondition.setPitId(toiletPitInfo1.getId());
            toiletPitUseRecordCondition.setIsDelete(false);
            //占用中  获取到关联的记录计算出使用时间
            List<ToiletPitUseRecord> list = toiletPitUseRecordManager.selectByCondition(toiletPitUseRecordCondition);
            if (isEmpty(list)) {
                userdTime = 0;
                log.warn("");
            } else {
                userdTime = DateUtil.getMinuteIntervalToNow(list.get(0).getCreateTime());
            }

        }
        return userdTime;
    }


    private ToiletPitInfo getPitInfo(TolietParam param) {
        ToiletPitInfoCondition condition = convertParamToCondition(param);
        condition.setDeleted(false);
        List<ToiletPitInfo> toiletPitInfo = toiletPitInfoManager.selectListByCondition(condition);
        AssertUtil.isTrueBIZ(isEmpty(toiletPitInfo), "坑位信息不存在,请确认该坑位已正确录入系统 - -");
        return toiletPitInfo.get(0);
    }

    private void updatePitUsingState(TolietParam param, ToiletPitInfo toiletPitInfo) {
        ToiletPitInfo updateTarget = new ToiletPitInfo();
        updateTarget.setUseState(param.isStatus());
        updateTarget.setId(toiletPitInfo.getId());
        toiletPitInfoManager.updateInfoByPrimaryKey(updateTarget);
    }

    private void addUseRecord(ToiletPitInfo toiletPitInfo) {
        ToiletPitUseRecord record = new ToiletPitUseRecord();
        record.setTolietPitId(toiletPitInfo.getId());
        record.setUid(0L);
        if (!toiletPitUseRecordManager.addRecord(record)) {
            throw new BusinessException("添加占用记录失败");
        }
    }

    private void releasePitUseRecord(ToiletPitInfo toiletPitInfo) {
        ToiletPitUseRecord toiletPitUseRecord = new ToiletPitUseRecord();
        toiletPitUseRecord.setDeleted(true);
        ToiletPitUseRecordCondition toiletPitUseRecordCondition = new ToiletPitUseRecordCondition();
        toiletPitUseRecordCondition.setPitId(toiletPitInfo.getId());
        toiletPitUseRecordCondition.setIsDelete(false);
        if (!toiletPitUseRecordManager.updateByCondition(toiletPitUseRecord, toiletPitUseRecordCondition)) {
            throw new BusinessException("废弃占用记录失败");
        }
        //释放后发一个消息提醒订阅的人
         redisManager.publish(RedisChannel.TOILET_FIND.getValue(), JsonUtil.write(toiletPitInfo));

    }

    private ToiletPitInfoCondition convertParamToCondition(TolietParam param) {

        ToiletPitInfoCondition condition = new ToiletPitInfoCondition();
        BeanUtils.copyProperties(param, condition);
        return condition;
    }

    long getSleepTime(Calendar nowTime) {
        int lateHour = Integer.parseInt(SettingsHolder.getProperty(TOILET_PIT_SLEEP_LATE_TIME));
        int earlyHour = Integer.parseInt(SettingsHolder.getProperty(TOILET_PIT_SLEEP_EARLY_TIME));

        int nowHour = nowTime.get(Calendar.HOUR_OF_DAY);
        long nowMills = nowTime.getTimeInMillis();
        if (nowHour >= earlyHour && nowHour < lateHour && lateHour - nowHour > 1) {
            return 0;
        } else if (nowHour < earlyHour) {
            if (nowHour < earlyHour && earlyHour - nowHour > 1) {
                return 60 * 60 * 1000;
            } else {
                //计算当前距离指定时间还有多长时间 单位毫秒
                return getIntervalMillis(earlyHour, nowMills);
            }
        } else if (nowHour >= lateHour) {
            return 60 * 60 * 1000;
        } else if (nowHour < lateHour && lateHour - nowHour <= 1) {
            return getIntervalMillis(lateHour, nowMills);
        }
        return 0;
    }

    private long getIntervalMillis(int endHour, long nowMills) {
        Calendar deadTime = Calendar.getInstance();
        deadTime.set(Calendar.HOUR_OF_DAY, endHour);
        deadTime.set(Calendar.MINUTE, 0);
        deadTime.set(Calendar.SECOND, 0);
        deadTime.set(Calendar.MILLISECOND, 0);
        long sleepMillis = deadTime.getTimeInMillis() - nowMills;
        return sleepMillis;
    }


    public static void main(String[] args) {
        IntelligentToiletServiceImpl intelligentToiletService = new IntelligentToiletServiceImpl();

        Calendar nowTime = Calendar.getInstance();
        nowTime.set(Calendar.HOUR_OF_DAY, 8);
        nowTime.set(Calendar.MINUTE, 59);
        nowTime.set(Calendar.SECOND, 0);
        nowTime.set(Calendar.MILLISECOND, 0);
        System.out.println(intelligentToiletService.getSleepTime(nowTime));
    }
}
