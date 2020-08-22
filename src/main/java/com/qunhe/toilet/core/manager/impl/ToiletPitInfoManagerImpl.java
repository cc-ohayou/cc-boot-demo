package com.qunhe.toilet.core.manager.impl;

import com.qunhe.toilet.core.manager.ToiletPitInfoManager;
import com.qunhe.toilet.core.mapper.master.ToiletPitInfoMapper;
import com.qunhe.toilet.core.mapper.master.ToiletPitUseRecordMapper;
import com.qunhe.toilet.facade.domain.common.condition.ToiletPitInfoCondition;
import com.qunhe.toilet.facade.domain.common.enums.toilet.SexTypeEnum;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitInfo;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitInfoExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author bupo
 * @DATE 2020/8/21 15:09
 * @Description
 */
@Slf4j
@Component
public class ToiletPitInfoManagerImpl  extends  BaseManagerImpl implements ToiletPitInfoManager {

    @Resource
    ToiletPitInfoMapper toiletPitInfoMapper;
    @Resource
    ToiletPitUseRecordMapper toiletPitUseRecordMapper;


    @Override
    public boolean addRecord(ToiletPitInfo info) {
        return toiletPitInfoMapper.insert(info)>0;
    }

    @Override
    public boolean updateInfoByCondition(ToiletPitInfo tolietPitInfo, ToiletPitInfoCondition condition) {
        ToiletPitInfoExample  toiletPitInfoExample  =getGeneralExample(condition);
        tolietPitInfo.setUpdateTime(new Date());
        return toiletPitInfoMapper.updateByExampleSelective(tolietPitInfo,toiletPitInfoExample)>0;
    }

    @Override
    public boolean updateInfoByPrimaryKey(ToiletPitInfo tolietPitInfo) {
        return toiletPitInfoMapper.updateByPrimaryKeySelective(tolietPitInfo)>0;

    }

    private ToiletPitInfoExample getGeneralExample(ToiletPitInfoCondition condition) {

        ToiletPitInfoExample  toiletPitInfoExample =new ToiletPitInfoExample();
        ToiletPitInfoExample.Criteria  criteria =  toiletPitInfoExample.createCriteria();
        if(nonNull(condition.getBuildNo())){
            criteria.andBuildingNoEqualTo(condition.getBuildNo());
        }
        if(nonNull(condition.getFloorNo())){
            criteria.andFloorNoEqualTo(condition.getFloorNo());
        }
        if(nonNull(condition.getRoomNo())){
            criteria.andRoomNoEqualTo(condition.getRoomNo());
        }

        if(nonNull(condition.getPitNo())){
            criteria.andPitPositionNoEqualTo(condition.getPitNo());
        }
        if(nonNull(condition.getUsingState())){
            criteria.andUseStateEqualTo(condition.getUsingState()==1?true:false);
        }
        if(nonNull(condition.getSexType())){
            criteria.andTolietTypeEqualTo(SexTypeEnum.getTypeDescById(condition.getSexType()));
        }
        return toiletPitInfoExample;
    }

    @Override
    public List<ToiletPitInfo> selectListByCondition(ToiletPitInfoCondition condition) {
        ToiletPitInfoExample  toiletPitInfoExample  =getGeneralExample(condition);
        return toiletPitInfoMapper.selectByExample(toiletPitInfoExample);
    }
}
