package com.cc.ccbootdemo.web.controller;



import com.cc.ccbootdemo.core.common.settings.SettingsEnum;
import com.cc.ccbootdemo.core.common.settings.SettingsHolder;
import com.cc.ccbootdemo.core.service.UploadService;
import com.cc.ccbootdemo.facade.domain.common.annotation.InterceptRequired;
import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.fasterxml.jackson.databind.ser.impl.MapEntrySerializer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;


/**
 * 文件上传
 *
 * @AUTHOR CF
 * @DATE Created on 2017/8/6 22:43.
 */
@Controller
@RequestMapping(value = "/{ver}/upload", produces = "application/json;charset=utf-8")
public class UploadController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UploadController.class);
    @Resource
//  (name="uploadTest")
    private UploadService uploadService;
    @Resource(name="uploadTest")
    private UploadService uploadServiceTest;


    /**
     * describe: 批量上传文件
     *
     * @param files
     * @author CAI.F
     * @date: 日期:2017/4/12 时间:11:42
     */
    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/uploadFiles")
    public Map<String, Object> uploadFiles( CommonsMultipartFile[] files) {
        return uploadService.uploadFiles(files);
    }

    /**
     *  describe: SSI组件上传图片
     *  利用spring的上传组件内进行文件上传
     * @param request
     * @author CAI.F
     * @date: 日期:2017/8/6 时间:22:55
     *
     */
    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/uploadFilesSSI")
    public void uploadFilesSSI(HttpServletRequest request) throws FileUploadException {
        List<FileItem> list = getListFilesFromReq(request);
        uploadService.uploadPicturesSSI(list,getMultiFilesFromRequest(request));
    }

    private List<MultipartFile> getMultiFilesFromRequest(HttpServletRequest request) {

    if(request instanceof StandardMultipartHttpServletRequest){
       Map<String,MultipartFile>  fileMap=((StandardMultipartHttpServletRequest) request).getFileMap();
       return   new ArrayList<MultipartFile>(fileMap.values());

    }else{
        return Collections.EMPTY_LIST;
    }

    }

    private List<FileItem> getListFilesFromReq(HttpServletRequest request) throws FileUploadException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设定使用内存超过限定值时，将产生临时文件并存储于临时目录中。
        factory.setSizeThreshold(20 * 1024 * 1024);
        String tempPath = String.valueOf( SettingsHolder.getProperty(SettingsEnum.UPLOAD_FILE_PATH));
        //创建临时文件夹 tempPath += "/" + ymd + "/";
        File dirTempFile = new File(tempPath);
        if (dirTempFile.exists()||dirTempFile.mkdirs()) {
            //设定存储临时文件的目录。
            factory.setRepository(new File(tempPath));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            return upload.parseRequest(request);
        }else{
            logger.warn("#####!!!makeDir failed");
            throw new BusinessException("上传失败！");
        }
    }

}
