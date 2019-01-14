package com.cc.ccbootdemo.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.cc.ccbootdemo.core.common.settings.SettingsEnum;
import com.cc.ccbootdemo.core.common.settings.SettingsHolder;
import com.cc.ccbootdemo.core.manager.MqManager;
import com.cc.ccbootdemo.core.manager.RedisManager;
import com.cc.ccbootdemo.core.manager.UserManager;
import com.cc.ccbootdemo.core.service.UserService;
import com.cc.ccbootdemo.facade.domain.bizobject.CustomProperties;
import com.cc.ccbootdemo.facade.domain.bizobject.Manga;
import com.cc.ccbootdemo.facade.domain.bizobject.OperateBiz;
import com.cc.ccbootdemo.facade.domain.bizobject.UserInfo;
import com.cc.ccbootdemo.facade.domain.bizobject.param.LoginParam;
import com.cc.ccbootdemo.facade.domain.bizobject.param.OperListQueryParam;
import com.cc.ccbootdemo.facade.domain.bizobject.param.SearchBaseParam;
import com.cc.ccbootdemo.facade.domain.bizobject.strgy.StrgyBiz;
import com.cc.ccbootdemo.facade.domain.common.constants.RedisConstants;
import com.cc.ccbootdemo.facade.domain.common.enums.EnvType;
import com.cc.ccbootdemo.facade.domain.common.enums.redis.RedisKeyEnum;
import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.cc.ccbootdemo.facade.domain.common.param.MQProducerParam;
import com.cc.ccbootdemo.facade.domain.common.util.AssertUtil;
import com.cc.ccbootdemo.facade.domain.common.util.PsPage;
import com.cc.ccbootdemo.facade.domain.common.util.log.MyMarker;
import com.cc.ccbootdemo.facade.domain.common.util.upyun.UploadUtil;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:43.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Resource
    UserManager userManager;
    @Resource
    RedisManager  redisManager;
    @Resource
    MqManager mqManager;

    private CustomProperties customProperties=new CustomProperties();

    @Override
    public List<UserInfo> getUserList(User params) {
        logger.info("params="+params);
        return userManager.getAllUserList(params);
    }

    @Override
    public void addUser(User params) {
        userManager.addUser(params);
    }

    @Override
    public void pushMsg() {
        Set<String> uids=redisManager.smembers(RedisConstants.SPECIAL_MEN_UID);
        Set<String> targetUids=redisManager.smembers(RedisConstants.SPECIAL_TARGET_MEN_UID);
        //此处使用targetUserId和商户id进行查询 cids可能有多个 不同客户端都有效 都推送
        //获取当前时间和上次间隔时间新建策略
        Map<String,Object> params=new HashMap<>();
        params.put("uids",uids);
        params.put("lastTime",redisManager.get(RedisConstants.LAST_PUSH_TIME));
        List<StrgyBiz>  strgys=userManager.getStrgyList(uids);
        for (int i = 0; i < strgys.size(); i++) {
            pushGeTuiMsg(new ArrayList<>(targetUids),strgys.get(i).toString(),"000");
        }

    }

    @Override
    public String produce(MQProducerParam param) {
        AssertUtil.isNullParamStr(param.getTopic(),"发送消息主题不可为空");
        AssertUtil.isNullParamStr(param.getTags(),"发送消息tag格式不可为空");
        AssertUtil.isNullParamStr(param.getMessage(),"发送消息内容不可为空");
        return mqManager.produceMsg(param);
    }

    @Override
    public String getDownloadUrl() {
        return SettingsHolder.getProperty(SettingsEnum.DOWNLOAD_URL_APK);
    }

    @Override
    public List<Manga> getMangaList(SearchBaseParam param) {
        List list= Collections.EMPTY_LIST;
        try{
            list= JSON.parseArray( redisManager.get(RedisKeyEnum.MANGA_LIST.getValue()),Manga.class);
        }catch(Exception e){
           logger.error(MyMarker.getInstance("cc-test"), "!!!getMangaList json转换失败",e);
        }
        return  list ;
    }

    @Override
    public CustomProperties getCustomProperties(SearchBaseParam param) {
        customProperties.setDownLoadUrl(SettingsHolder.getProperty(SettingsEnum.DOWNLOAD_URL_APK));
        customProperties.setUpdateSign(SettingsHolder.getProperty(SettingsEnum.UPDATE_APP_SIGN));
        return customProperties;
    }

    @Override
    public PsPage<OperateBiz> getOperateList(OperListQueryParam param) {
        List<String> list= Collections.EMPTY_LIST;
        long totalCou=0L;
        AssertUtil.isTrueParam(param.getCurrPage()<=0,"当前页不可为空");
        try{
             totalCou=redisManager.llen(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),null);

            initOffsetSizeInfo(param, Math.toIntExact(totalCou));
        if(EnvType.SIT.getValue().equals(param.getEnvType())) {
//            redis的lrange  右边end是包含自身的 所以需要减去1   0 10 会返回11调数据  第一页用0 9  第二页 10 19   起始位置要加上偏移量
                list = redisManager.lrange(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),param.getOffset(),param.getOffset()+param.getSize()-1,null);

        }
        }catch(Exception e){
            logger.error(MyMarker.getInstance("cc-test"), "!!!getOperateList json转换失败",e);
        }
        List<OperateBiz> listBiz=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            listBiz.add(JSON.parseObject(list.get(i),OperateBiz.class));
        }
        return new PsPage<>(listBiz, Math.toIntExact(totalCou),param.getSize(),param.getCurrPage());
    }

    @Override
    public UserInfo login(LoginParam param) {
        AssertUtil.isNullParamStr(param.getUserName(),"用户名不可为空");
        AssertUtil.isNullParamStr(param.getPwd(),"密码不可为空");
//        hash(param.getUserName());
       /* String pwd=redisManager.hget(RedisKeyEnum.USER_INFO.getValue(),param.getUserName());
        if(StringUtils.isEmpty(pwd)||!pwd.equals(param.getPwd())){
            throw new BusinessException("用户名或密码不正确");
        }
*/
        UserInfo userInfo=userManager.getUserInfo(param.getUserName());
        if(userInfo==null||!param.getPwd().equals(userInfo.getPwd())){
            throw new BusinessException("用户名或密码不正确");
        }
        return userInfo;
    }

    @Override
    public int updateUserInfo(UserInfo pojo){
        AssertUtil.isNullParamStr(pojo.getUid(),"用户id不可为空");
        return userManager.updateUserInfoSelective(pojo);
    }


    @Override
    public String updateHeadImg(String userId, MultipartFile multfile) {

        String headImage ;
        try {
            headImage = UploadUtil.upload("/user/headImages/", multfile.getBytes(), userId);
        } catch (Exception e) {
            logger.error("上传头像失败!!!",e);
           throw  new BusinessException("头像上传失败");
        }
        UserInfo user = new UserInfo();
        user.setUid(userId);
        user.setHeadImage(headImage);
        userManager.updateUserInfoSelective(user);
        return headImage;
    }
}
