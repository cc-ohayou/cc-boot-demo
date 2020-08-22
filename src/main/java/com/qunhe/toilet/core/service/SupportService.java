package com.qunhe.toilet.core.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/30 9:52.
 */
public interface SupportService {


    String uploadImg(MultipartFile file,String key,String pwd);
}
