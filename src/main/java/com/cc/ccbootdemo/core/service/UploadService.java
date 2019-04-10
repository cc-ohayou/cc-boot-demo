package com.cc.ccbootdemo.core.service;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;
import java.util.Map;

/** 上传服务业务类
 * @AUTHOR CF
 * @DATE Created on 2017/8/6 22:46.
 */
public interface  UploadService {
/**  * describe: 上传图片
	 * @author CAI.F
	 * @date:  日期:2017/8/6 时间:22:48
	 * @param
	 */
     String uploadPic(String params);
    /**  * describe: 批量上传图片
    	 * @author CAI.F
    	 * @date:  日期:2017/8/6 时间:22:49
    	 * @param
    	 */
    void uploadPicturesSSI(List<FileItem> list,List<MultipartFile> files);
    /**  * describe: 上传文件 doc、txt、xmls等等
    	 * @author CAI.F
    	 * @date:  日期:2017/8/6 时间:22:49
    	 * @param
    	 */
    Map<String,Object> uploadFiles(MultipartFile[] files);
    /**  * describe: 上传视频 mp3等等这类较大的文件 采用上传列表形式 异步线程
    	 * @author CAI.F
    	 * @date:  日期:2017/8/6 时间:22:50
    	 * @param
    	 */
    String uploadVideo();
}
