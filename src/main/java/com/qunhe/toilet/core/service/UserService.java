package com.qunhe.toilet.core.service;

import com.qunhe.toilet.facade.domain.bizobject.CustomProperties;
import com.qunhe.toilet.facade.domain.bizobject.param.RegistParam;
import com.qunhe.toilet.facade.domain.common.param.ResetPwdParam;
import com.qunhe.toilet.facade.domain.bizobject.UserInfo;
import com.qunhe.toilet.facade.domain.bizobject.param.LoginParam;
import com.qunhe.toilet.facade.domain.bizobject.param.SearchBaseParam;
import com.qunhe.toilet.facade.domain.dataobject.User;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:42.
 */
public interface UserService {


    CustomProperties getCustomProperties(SearchBaseParam param);


    UserInfo login(LoginParam param);

    /**
     * @description
     * @author CF create on 2019/1/14 11:58
     */
    int updateUserInfo(User param);

    String updateHeadImg(String userId, MultipartFile file);

    UserInfo getUserInfoByUid(String userId);


    /**
     * @description
     * @author CF create on 2019/1/23 10:24
     */
    void regist(RegistParam param);

    void forgetPwd(String uid) throws MessagingException;

    void resetPwd(ResetPwdParam resetParam);

    void loginOut(String userId);

    void sendEmail(String email);
}
