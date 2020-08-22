package com.qunhe.toilet.facade.domain.common.util.upyun;

import com.qunhe.toilet.core.common.settings.SettingsEnum;
import com.qunhe.toilet.core.common.settings.SettingsHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import java.util.Random;
/**
 * @Author bupo
 * @DATE 2020/8/20 10:33
 * @Description 
 */
public class UploadUtil {

    public static final String VIS_PREFIX = SettingsHolder.getProperty(SettingsEnum.UPYUN_URL);
    private static final String BUCKET_NAME = SettingsHolder.getProperty(SettingsEnum.UPYUN_BUCKET_NAME);
    private static final String OPERATOR_NAME = SettingsHolder.getProperty(SettingsEnum.UPYUN_ACCOUNT);
    private static final String OPERATOR_PWD = SettingsHolder.getProperty(SettingsEnum.UPYUN_PWD);
    private static  com.UpYun upyun ;
    private static Logger logger= LoggerFactory.getLogger(UploadUtil.class);
    static{
        upyun = new com.UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
        logger.info("upyun init instance="+upyun);
    }

    public static void main(String[] args) {
        try {
            String filePath = "/jiaqi/";

            File file = new File("C:\\Users\\admin\\Downloads\\1.jpg");
            upyun.setContentMD5(UpYun.md5(file));
            UploadUtil.upload("/user/bgImg/", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String isSuccess(boolean result) {
        return result ? " 成功" : " 失败";
    }

    public static String upload(String path, File localFile) throws Exception {
        upyun.setDebug(true);
        upyun.setContentMD5(UpYun.md5(localFile));
        String filePath = path + md5(localFile.getName()) + localFile.getName().substring(localFile.getName().lastIndexOf("."));
        boolean ret = upyun.writeFile(filePath, localFile, true);
        System.out.println("上传:" + localFile.getName() + "===>" + filePath + isSuccess(ret));
        return VIS_PREFIX + filePath;
    }

    public static String upload(String path,byte[] bytes) throws Exception {
        String timeTemp = System.currentTimeMillis()+"";
        String realPath = path + timeTemp + ".jpg";
        try{
            upyun.writeFile(realPath,bytes,true);
        }catch(Exception e){
            logger.warn("!!!!upyun upload failed ",e);
        }
        return VIS_PREFIX + realPath;
    }
    public static String upload(String path,byte[] bytes,String userId) throws Exception {
        String timeTemp = System.currentTimeMillis()+"";
        String realPath = path + timeTemp + ".jpg";
        try{
            upyun.writeFile(realPath,bytes,true);
        }catch(Exception e){
            logger.warn("!!!!upyun upload headImg failed userId="+userId,e);
        }
        return VIS_PREFIX + realPath;
    }

    public static String randomImg(String path,String userId){
        String name="";
        try {
            List<com.UpYun.FolderItem> items= upyun.readDir(path,new HashMap(2));
            logger.info("randomImg items size ="+items.size());
            if(CollectionUtils.isEmpty(items)){
                logger.warn("!!!!randomImg generate failed size empty ,userId ="+userId);
                return "";
            }
            Random random = new Random();
            int index = random.nextInt(items.size());
            name = items.get(index).name;
        } catch (Exception e) {
            logger.warn("!!!!randomImg generate failed  ,userId ="+userId,e);
        }
        return VIS_PREFIX+path+"/"+name;
    }
    public static String randomImg(String path){
        String name="";
        try {
            List<UpYun.FolderItem> items= upyun.readDir(path,new HashMap(2));
            logger.info("randomImg items size ="+items.size());
            if(CollectionUtils.isEmpty(items)){
                logger.warn("!!!!randomImg generate failed size empty ");
                return "";
            }
            Random random = new Random();
            int index = random.nextInt(items.size());
            name = items.get(index).name;
        } catch (Exception e) {
            logger.warn("!!!!randomImg generate failed  ",e);
        }
        return VIS_PREFIX+path+"/"+name;
    }
    public static String md5(String content){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuilder md5StrBuff = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }
}
