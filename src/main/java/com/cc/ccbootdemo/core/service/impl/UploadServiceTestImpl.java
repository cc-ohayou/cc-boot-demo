package com.cc.ccbootdemo.core.service.impl;

import com.cc.ccbootdemo.core.service.UploadService;
import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/4/10/010 21:24.
 */
@Service("uploadTest")
public class UploadServiceTestImpl implements UploadService{
    @Override
    public String uploadPic(String params) {
        return null;
    }

    @Override
    public void uploadPicturesSSI(List<FileItem> list, List<MultipartFile> files) {

    }

    @Override
    public Map<String, Object> uploadFiles(MultipartFile[] files) {
        return null;
    }

    @Override
    public String uploadVideo() {
        System.err.println("multiply imply service method execute test UploadServiceTestImpl ");

        return null;
    }
}
