package com.qunhe.toilet.core.manager;

import org.apache.commons.fileupload.FileItem;

import java.io.IOException;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/8 13:42.
 */
public interface UploadManager {
   void doUpload(List<FileItem> files, String savePath) throws IOException;
}
