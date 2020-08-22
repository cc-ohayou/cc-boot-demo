package com.qunhe.toilet.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.qunhe.toilet.core.common.settings.SettingsEnum;
import com.qunhe.toilet.core.common.settings.SettingsHolder;
import com.qunhe.toilet.core.service.UserService;
import com.qunhe.toilet.facade.domain.bizobject.CustomProperties;
import com.qunhe.toilet.facade.domain.bizobject.UserInfo;
import com.qunhe.toilet.facade.domain.bizobject.param.LoginParam;
import com.qunhe.toilet.facade.domain.bizobject.param.RegistParam;
import com.qunhe.toilet.facade.domain.bizobject.param.SearchBaseParam;
import com.qunhe.toilet.facade.domain.common.dataobject.mail.MailInfo;
import com.qunhe.toilet.facade.domain.common.enums.redis.RedisKeyEnum;
import com.qunhe.toilet.facade.domain.common.exception.BusinessException;
import com.qunhe.toilet.facade.domain.common.param.ResetPwdParam;
import com.qunhe.toilet.facade.domain.common.util.*;
import com.qunhe.toilet.facade.domain.common.util.*;
import com.qunhe.toilet.facade.domain.common.util.upyun.UploadUtil;
import com.qunhe.toilet.facade.domain.dataobject.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.awt.SystemColor.info;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:43.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{
    private Logger logger= LoggerFactory.getLogger(this.getClass());


    private CustomProperties customProperties=new CustomProperties();


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
    public void regist(RegistParam param) {
        verifyParams(param);
        String nickName=param.getNickName();
        if(!StringUtils.isEmpty(param.getNickName())){
            AssertUtil.isTrueParam( !RegUtil.nickNameCheck(param.getNickName()),"昵称长度需在10位以内,且不可含特殊字符");
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

        verifyMailValidity(param);

        user.setNickName(nickName);
        user.setPhone(param.getPhone());
        user.setMail(param.getMail());
        user.setSalty(RandomStringUtil.generateString(6));
        user.setPwd(SecurityUtil.MD5(param.getPwd(),user.getSalty()));
        userManager.addUser(user);


//        userAttach.setAnswer(param.getAnswer());
//        user.setCreateTime(new Date());
    }

    private void verifyMailValidity(final RegistParam param) {
        boolean sendFlag=false;
        try {

            MailInfo mailInfo = new MailInfo();
            mailInfo.setSubject("注册验证");
            mailInfo.setTo(new String[]{param.getMail()});
            mailInfo.setContent("注册验证邮件，可忽略");
            mailManager.sendMail(mailInfo);
            sendFlag = true;
        } catch (MessagingException e) {
            logger.error("####邮箱验证失败!",e);
        }
        AssertUtil.isTrueParam(!sendFlag,"无效邮箱");
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
            RetryUtil.execute(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    mailManager.sendMail( organizeMailInfo(info, verifyCode));
                    assert info != null;
                    redisManager.hset(RedisKeyEnum.MAIL_RESET_PWD_VERIFY_CODE.getValue(),
                            getMailPwdResetVerifyCodeKey(info, verifyCode), String.valueOf(System.currentTimeMillis()));
                    return true;
                }
            },2000,1000,0);
        } catch (ExecutionException |InterruptedException e) {
            throw new BusinessException("验证码发送失败");
        } catch (TimeoutException e) {
            throw new BusinessException("验证码发送失败(请求超时)!");
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
//      用后即删除
        redisManager.hdel(RedisKeyEnum.MAIL_RESET_PWD_VERIFY_CODE.getValue(),
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

    @Override
    public void loginOut(String userId) {
        userManager.loginOut(userId);
    }

    @Override
    public void sendEmail(String email) {
        AssertUtil.isTrueParam(!RegxUtil.isEmail(email),"邮箱格式非法!");
        try {
            RetryUtil.execute(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    String content="account：visitor ,pass:cc1234";
                    String title="elk登录账号信息";
                    mailManager.sendMail(organizeMailInfo(email,title,content));
                    assert info != null;
                       return true;
                }
            },3000,1000,0);
        } catch (ExecutionException |InterruptedException e) {
            throw new BusinessException("邮件发送失败");
        } catch (TimeoutException e) {
            throw new BusinessException("邮件发送失败(请求超时)!");
        }
    }

    private MailInfo organizeMailInfo(String email,String title,String content) {

        MailInfo mailInfo=new MailInfo();
        mailInfo.setSubject(title);
        mailInfo.setTo(new String[]{email});
        mailInfo.setContent(content);
        return mailInfo;
    }
}
