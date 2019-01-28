package com.cc.ccbootdemo.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.cc.ccbootdemo.core.common.settings.SettingsEnum;
import com.cc.ccbootdemo.core.common.settings.SettingsHolder;
import com.cc.ccbootdemo.core.service.UserService;
import com.cc.ccbootdemo.facade.domain.bizobject.CustomProperties;
import com.cc.ccbootdemo.facade.domain.bizobject.Manga;
import com.cc.ccbootdemo.facade.domain.bizobject.UserInfo;
import com.cc.ccbootdemo.facade.domain.bizobject.param.LoginParam;
import com.cc.ccbootdemo.facade.domain.bizobject.param.OperListQueryParam;
import com.cc.ccbootdemo.facade.domain.bizobject.param.RegistParam;
import com.cc.ccbootdemo.facade.domain.bizobject.param.SearchBaseParam;
import com.cc.ccbootdemo.facade.domain.bizobject.strgy.StrgyBiz;
import com.cc.ccbootdemo.facade.domain.common.constants.RedisConstants;
import com.cc.ccbootdemo.facade.domain.common.dataobject.mail.MailInfo;
import com.cc.ccbootdemo.facade.domain.common.enums.EnvType;
import com.cc.ccbootdemo.facade.domain.common.enums.redis.RedisKeyEnum;
import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.cc.ccbootdemo.facade.domain.common.param.MQProducerParam;
import com.cc.ccbootdemo.facade.domain.common.param.ResetPwdParam;
import com.cc.ccbootdemo.facade.domain.common.util.*;
import com.cc.ccbootdemo.facade.domain.common.util.log.MyMarker;
import com.cc.ccbootdemo.facade.domain.common.util.upyun.UploadUtil;
import com.cc.ccbootdemo.facade.domain.dataobject.OperateBiz;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import com.cc.ccbootdemo.facade.domain.dataobject.UserAttachDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:43.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{
    private Logger logger= LoggerFactory.getLogger(this.getClass());


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
        customProperties.setLoginBgUrl(SettingsHolder.getProperty(SettingsEnum.LOGIN_BG_URL));
        customProperties.setOperBizDetailBgUrl(SettingsHolder.getProperty(SettingsEnum.OPER_BIZ_DETAIL_BG_URL));

        Map map= getOtherCustomProperties();
        customProperties.setOtherProperties(map);
        return customProperties;
    }

    private Map getOtherCustomProperties() {
        try{
            String str=SettingsHolder.getProperty(SettingsEnum.OTHER_CUSTOM_PROPERTIES);
            if(!StringUtils.isEmpty(str)) {
                return JSON.parseObject(str, Map.class);
            }
        }catch(Exception e){
            logger.error("getOtherCustomProperties parse error");
        }
        return Collections.EMPTY_MAP;
    }

    @Override
    public PsPage<OperateBiz> getOperateList(OperListQueryParam param) {
        List<OperateBiz> listBiz=Collections.EMPTY_LIST;
        long totalCou=0L;
        AssertUtil.isTrueParam(param.getCurrPage()<=0,"当前页不可为空");
        try{
             totalCou= getOperBizTotalCou(param);
             initOffsetSizeInfo(param, Math.toIntExact(totalCou));
        if(EnvType.SIT.getValue().equals(param.getEnvType())) {
//            redis的lrange  右边end是包含自身的 所以需要减去1   0 10 会返回11调数据  第一页用0 9  第二页 10 19   起始位置要加上偏移量
            listBiz = getOperListByParam(param);

        }
        }catch(Exception e){
            logger.error(MyMarker.getInstance("cc-test"), "!!!getOperateList json转换失败",e);
        }

        return new PsPage<>(listBiz, Math.toIntExact(totalCou),param.getSize(),param.getCurrPage());
    }

    private List<OperateBiz> getOperListByParam(OperListQueryParam param) {
//        List<String> list= redisManager.lrange(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),param.getOffset(),param.getOffset()+param.getSize()-1,null);
//        List<OperateBiz> listBiz=new ArrayList<>();

        List<OperateBiz> listBiz= operateBizManager.getOperBizList(param);
       /* for (int i = 0; i <list.size() ; i++) {
            listBiz.add(JSON.parseObject(list.get(i),OperateBiz.class));
        }*/

        return listBiz;
    }

    private long getOperBizTotalCou(OperListQueryParam param) {
//        return redisManager.llen(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),null);
        return operateBizManager.getOperBizTotalCou(param);
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
        if(userInfo==null||!SecurityUtil.verify(param.getPwd(),userInfo.getSalty(),userInfo.getPwd())){
            throw new BusinessException("用户名或密码不正确");
        }
        String sid=userManager.addSession(userInfo.getUid(),"1001","android");
        userInfo.setSid(sid);
        userInfo.setSalty("");
        userInfo.setPwd("");
        return userInfo;
    }

    @Override
    public int updateUserInfo(User pojo){
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
        User user = new User();
        user.setUid(userId);
        user.setHeadImage(headImage);
        userManager.updateUserInfoSelective(user);
        return headImage;
    }

    @Override
    public UserInfo getUserInfoByUid(String userId) {
        AssertUtil.isNullParamStr(userId,"用户id不可为空");
        return userManager.getUserInfoByUid(userId);
    }

    @Override
    public String modifyBgImg(String userId, MultipartFile file) {
        String bgImg ;
        try {
            bgImg = UploadUtil.upload("/user/bgImg/", file.getBytes(), userId);
        } catch (Exception e) {
            logger.error("上传mainBg失败!!!",e);
            throw  new BusinessException("mainBg上传失败");
        }
        UserAttachDO user = new UserAttachDO();
        user.setUid(userId);
        user.setMainBgUrl(bgImg);
        userManager.updateUserAttachInfoSelective(user);
        return bgImg;
    }

    @Override
    public void initOperList() {
        List<String> list= redisManager.lrange(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),0,20,null);
        List<OperateBiz> listBiz=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            listBiz.add(JSON.parseObject(list.get(i),OperateBiz.class));
        }
        operateBizManager.insertList(listBiz);

    }

    @Override
    public void regist(RegistParam param) {
        verifyParams(param);
        String nickName=param.getNickName();
        if(!StringUtils.isEmpty(param.getNickName())){
            AssertUtil.isTrueParam( !RegUtil.nickNameCheck(param.getPwd()),"昵称长度需在10位以内,且不可含特殊字符");
            if(userManager.getUserInfo(param.getNickName())!=null){
                throw new BusinessException("该昵称已被占用！");
            }
        }else{
            nickName= RandomStringUtil.generateString(7);
        }
        User user=new User();
        String uid= String.valueOf(IdGen.genId());
        user.setUid(uid);

        if(userManager.getUserInfo(param.getPhone())!=null){
            throw new BusinessException("用户已存在！");
        }
        if(userManager.getUserInfo(param.getMail())!=null){
            throw new BusinessException("用户已存在！");
        }


        user.setNickName(nickName);
        user.setPhone(param.getPhone());
        user.setMail(param.getMail());
        user.setSalty(RandomStringUtil.generateString(6));
        user.setPwd(SecurityUtil.MD5(param.getPwd(),user.getSalty()));
        userManager.addUser(user);


//        userAttach.setAnswer(param.getAnswer());
//        user.setCreateTime(new Date());
    }

    private void verifyParams(RegistParam param) {
        AssertUtil.isNullParamStr(param.getPhone(),"手机号不可为空");
        AssertUtil.isNullParamStr(param.getPwd(),"密码不可为空");
        AssertUtil.isNullParamStr(param.getMail(),"邮箱不可为空");
//        AssertUtil.isNullParamStr(param.getAnswer(),"请输入问题答案");
        AssertUtil.isTrueParam( !RegUtil.isPhoneValid(param.getPhone()),"非法手机号");
        AssertUtil.isTrueParam( !RegUtil.mailCheckPass(param.getMail()),"非法邮箱");
//        AssertUtil.isTrueParam( !RegUtil.passwordCheck(param.getPwd()),"密码格式不符,6-16位必须包含数字和字母");


    }

    @Override
    public void forgetPwd(String mail) {
        AssertUtil.isNullParamStr(mail, "邮箱不可为空!");
        AssertUtil.isTrueParam(!RegxUtil.isEmail(mail),"邮箱格式非法");
        UserInfo info=userManager.getUserInfoByMail(mail);
        AssertUtil.isTrueBIZ(info==null,"该邮箱尚未注册");
        String verifyCode = RandomStringUtil.generateNum(6);
        try {
            mailManager.sendMail( organizeMailInfo(info, verifyCode));
            assert info != null;
            redisManager.hset(RedisKeyEnum.MAIL_RESET_PWD_VERIFY_CODE.getValue(),
                    getMailPwdResetVerifyCodeKey(info, verifyCode), String.valueOf(System.currentTimeMillis()));
        } catch (MessagingException e) {
            logger.error("邮箱验证码发送失败",e);
            throw new BusinessException("验证码发送失败");
        }

    }

    private String getMailPwdResetVerifyCodeKey(UserInfo info, String verifyCode) {
        return info.getUid()+"-"+verifyCode;
    }

    private MailInfo organizeMailInfo(UserInfo info, String verifyCode) {
        MailInfo mailInfo=new MailInfo();
        mailInfo.setSubject("您此次的验证码是："+verifyCode);
        mailInfo.setTo(new String[]{info.getMail()});
        mailInfo.setContent("您此次的验证码是："+verifyCode+",有效期15分钟，请尽快使用！");
        return mailInfo;
    }

    @Override
    public void resetPwd(ResetPwdParam resetParam) {
        AssertUtil.isNullParamStr(resetParam.getMail(), "邮箱不可为空!");
        AssertUtil.isNullParamStr(resetParam.getPwd(), "重置密码不可为空!");
        AssertUtil.isNullParamStr(resetParam.getVerifyCode(), "验证码不可为空!");
        AssertUtil.isTrueParam(!RegxUtil.isEmail(resetParam.getMail()),"邮箱格式非法!");
//        AssertUtil.isTrueBIZ(!RegUtil.passwordCheck(resetParam.getPwd()),"密码格式非法!");
        /*
         1、邮箱校验
         */
        UserInfo info=userManager.getUserInfoByMail(resetParam.getMail());
        AssertUtil.isTrueBIZ(info==null,"该邮箱尚未注册");
        assert info != null;
        /*
         *2、 验证码校验
         */
        String createMills= redisManager.hget(RedisKeyEnum.MAIL_RESET_PWD_VERIFY_CODE.getValue(),
                getMailPwdResetVerifyCodeKey(info, resetParam.getVerifyCode()));
        AssertUtil.isTrueBIZ(StringUtils.isEmpty(createMills),"验证码错误");
        int passMinute= Math.toIntExact((System.currentTimeMillis() - Long.parseLong(createMills)) / (60 * 1000));
        AssertUtil.isTrueBIZ(passMinute>=15,"验证码已失效");
        /*
         * 3、修改密码
         */
        User u=new User();
        u.setPwd(SecurityUtil.MD5(resetParam.getPwd(),info.getSalty()));
        u.setUid(info.getUid());
        userManager.updateUserInfoSelective(u);

    }
}
