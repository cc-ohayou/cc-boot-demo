package com.qunhe.toilet.core.service.impl;

import com.qunhe.toilet.core.common.settings.RedisChannel;
import com.qunhe.toilet.core.common.settings.SettingsEnum;
import com.qunhe.toilet.core.common.settings.SettingsHolder;
import com.qunhe.toilet.core.service.SupportService;
import com.qunhe.toilet.facade.domain.common.exception.BusinessException;
import com.qunhe.toilet.facade.domain.common.util.AssertUtil;
import com.qunhe.toilet.facade.domain.common.util.upyun.UploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/30 9:53.
 */
@Slf4j
@Service
public class SupportServiceImpl extends BaseServiceImpl implements SupportService{


//    static ExecutorService specialPool = initPool(2,4,"specialPool",log);



    @Override
    public String uploadImg(MultipartFile file,String key,String pwd) {
        AssertUtil.isNullParamStr(pwd,"非法请求");
        AssertUtil.isTrueParam(!pwd.equals(SettingsHolder.getProperty(SettingsEnum.UPLOAD_PWD)),"非法请求");
        String bgImg ;
        try {
            bgImg = UploadUtil.upload("/user/bgImg/", file.getBytes(), "000000");
        } catch (Exception e) {
            log.error("uploadImg失败!!!",e);
            throw  new BusinessException("uploadImg上传失败");
        }
        log.info("####uploadImg url="+bgImg);

        if(!StringUtils.isEmpty(key)){
            redisManager.hset(SettingsHolder.GLOBAL_SETTINGS, key,
                    bgImg);
            //通过发布命令动态更新全局配置中的值  或者直接更改本地缓存也可
            redisManager.publish(RedisChannel.TEST_CHANNEL.getValue(), key);
            log.info("uploadImg redis update success");
        }

        return bgImg;
    }


}
