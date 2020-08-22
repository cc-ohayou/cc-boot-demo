package com.qunhe.toilet.core.manager.impl;


import com.qunhe.toilet.core.manager.UploadManager;
import com.qunhe.toilet.facade.domain.common.exception.BusinessException;
import com.qunhe.toilet.facade.domain.common.util.FileUploadUtil;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/8 13:43.
 */
@Component
public class UploadManagerImpl implements UploadManager {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * @description
     *  FileItem类的常用方法
    1.boolean  isFormField()。isFormField方法用来判断FileItem对象里面封装的数据是一个普通文本表单字段，
    还是一个文件表单字段。如果是普通文本表单字段，返回一个true否则返回一个false。
    因此可以用该方法判断是否是普通表单域还是文件上传表单域。
    2.String getName（）。getName方法用来获得文件上传字段中的文件名。
    3.String getFieldName（）。getFieldName方法用来返回表单标签的name属性的值。
    4.void write(File file)。write方法将FileItem对象中的内容保存到某个指定的文件中。
    如果FileItem对象中的内容是保存在某个临时文件中，该方法完成后，临时文件可以会被删除。
    该方法也可以将普通表单字段保存在一个文件中，但最主要的用途是把上传的文件内容保存在本地文件系统中。
    5.String getString（）。
    getString（）方法将FileItem对象中保存的数据流内容以一个字符串返回。它有两个重载形式。
    前者使用缺省的字符集编码将主体内容转换成字符串，后者使用参数指定的字符集编码。
    如果在读取普通表单字段元素的内容时，出现了乱码现象，可以调用第二个方法，并传入 正确的字符集编码名称。
    6.String getContentType()。此方法用来获得上传文件的类型，
    即标段字段元素描述头属性“content-type”的值，如image/jpeg。
    如果FileItem对象对应的是普通的表单字段，将返回null。
    7.boolean isInMemory()。判断FileItem对象封装的数据是保存在内存中还是硬盘中。
    8.void  delete（）
    此方法用来清空FileItem对象中封装的主体内容，如果内容是被保存在临时文件中，该方法会把临时文件删除。
    9.InputStream  getInputStream()。以流的形式返回上传文件的主体内容。
    10.long  getSize()。返回上传文件的大小。
     * @author CF create on 2018/4/8 13:37
     */
    @Override
    public void doUpload(List<FileItem> items, String savePath) throws IOException {
        //创建文件夹
        File dirFile = new File(savePath);
        if (!dirFile.exists()&&!dirFile.mkdirs()) {
            logger.warn("!!!doUpload failed,dir make fail");
            throw new BusinessException("上传失败");
         }
         logger.info("items.size="+items.size());
        for (FileItem item : items) {
//            如果是普通文本表单字段，返回一个true否则返回一个false
            if (!item.isFormField()) {
                FileUploadUtil.writeFileToPath(savePath, item);
            } else {
                String filedName = item.getFieldName();
                if ("customData".equals(filedName)) {
                    logger.info("用户自定义参数===============");
                    logger.info("FieldName：" + filedName);
                    logger.info(URLDecoder.decode(URLDecoder.decode(item.getString(), "utf-8"), "utf-8"));
                } else if ("tailor".equals(filedName)) {
                    logger.info("裁剪后的参数===============");
                    logger.info("FieldName：" + filedName);
                    logger.info(new String(item.getString().getBytes("iso-8859-1"), "utf-8"));
                    // 获得裁剪部分的坐标和宽高
                    logger.info("String：" + item.getString());
                }
            }
        }
    }


}
