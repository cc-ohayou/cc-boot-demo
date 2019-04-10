package com.cc.ccbootdemo.core.service.impl;



import com.cc.ccbootdemo.core.common.settings.SettingsEnum;
import com.cc.ccbootdemo.core.common.settings.SettingsHolder;
import com.cc.ccbootdemo.core.manager.RedisManager;
import com.cc.ccbootdemo.core.manager.UploadManager;
import com.cc.ccbootdemo.core.service.UploadService;
import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.cc.ccbootdemo.facade.domain.common.util.FileUploadUtil;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/8/6 22:46.
 */
@Service("uploadService")
public class UploadServiceImpl extends BaseServiceImpl implements UploadService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    RedisManager redisManager;
    @Resource
    UploadManager uploadManager;
    @Override
    public String uploadPic(String params) {

        return null;
    }

    //  批量上传图片
    @Override
    public void uploadPicturesSSI(List<FileItem> items,List<MultipartFile> files) {
        try {
            uploadManager.doUpload(items, SettingsHolder.getProperty(SettingsEnum.UPLOAD_FILE_PATH));
            uploadFiles(files);

        } catch (UnsupportedEncodingException e) {
            logger.warn("upload faied encoding error", e);
            throw new BusinessException("上传失败");
        } catch (IOException e) {
            logger.warn("upload faied io error", e);
            throw new BusinessException("上传失败");
        }
    }

    private void uploadFiles(List<MultipartFile> files) {
        logger.info("##uploadFiles list size="+files.size());
        String basePath= String.valueOf(SettingsHolder.getProperty(SettingsEnum.UPLOAD_FILE_PATH));
        if (files.size() > 0) {
            for (MultipartFile file1 : files) {
                if (!file1.isEmpty()) {
                    try {
//                        Map<String, Object> file = new HashMap<>();
                        FileUploadUtil.saveFile(basePath,file1);
                    } catch (Exception e) {
                        logger.warn(" !!uploadFiles failed,fileName="+file1.getOriginalFilename(), e);
                    }
                }
            }
        }
    }


    @Override
    public Map<String, Object> uploadFiles(MultipartFile[] files) {
        logger.info("##uploadFiles size="+files.length);
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Map<String, Object>> fileMess = new ArrayList<>();
        String basePath= String.valueOf(SettingsHolder.getProperty(SettingsEnum.UPLOAD_FILE_PATH));
        if (files != null && files.length > 0) {
            for (MultipartFile file1 : files) {
                if (!file1.isEmpty()) {
                    try {
                        Map<String, Object> file = new HashMap<>();
                        file = FileUploadUtil.saveFile(file1, file,basePath);
                        fileMess.add(file);
                    } catch (Exception e) {
                        logger.warn(" !!uploadFiles failed,fileName="+file1.getOriginalFilename(), e);
                    }
                }
            }
        }
        resultMap.put("fileMess", fileMess);
        return resultMap;
    }

    @Override
    public String uploadVideo() {
        System.err.println("multiply imply service method execute test 1 ");
        return null;
    }
}
