package com.cc.ccbootdemo.core.service;

import com.cc.ccbootdemo.facade.domain.bizobject.CustomProperties;
import com.cc.ccbootdemo.facade.domain.bizobject.Manga;
import com.cc.ccbootdemo.facade.domain.dataobject.OperateBiz;
import com.cc.ccbootdemo.facade.domain.bizobject.UserInfo;
import com.cc.ccbootdemo.facade.domain.bizobject.param.LoginParam;
import com.cc.ccbootdemo.facade.domain.bizobject.param.OperListQueryParam;
import com.cc.ccbootdemo.facade.domain.bizobject.param.SearchBaseParam;
import com.cc.ccbootdemo.facade.domain.common.param.MQProducerParam;
import com.cc.ccbootdemo.facade.domain.common.util.PsPage;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:42.
 */
public interface UserService {
    List<UserInfo> getUserList(User params);
    void addUser(User params);

    void pushMsg();

    /**
     * @description 发送消息 返回msgId
     * @author CF create on 2018/12/7 17:02
     */
    String  produce(MQProducerParam param);

    String getDownloadUrl();

    List<Manga> getMangaList(SearchBaseParam param);

    CustomProperties getCustomProperties(SearchBaseParam param);

    PsPage<OperateBiz> getOperateList(OperListQueryParam param);

    UserInfo login(LoginParam param);

    /**
     * @description
     * @author CF create on 2019/1/14 11:58
     */
    int updateUserInfo(UserInfo param);

    String updateHeadImg(String userId, MultipartFile file);

    UserInfo getUserInfoByUid(String userId);

    String modifyBgImg(String userId, MultipartFile file);

    void initOperList();
}
