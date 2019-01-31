package com.cc.ccbootdemo.core.service.impl;

import com.cc.ccbootdemo.core.common.settings.RedisChannel;
import com.cc.ccbootdemo.core.common.settings.SettingsEnum;
import com.cc.ccbootdemo.core.common.settings.SettingsHolder;
import com.cc.ccbootdemo.core.service.SupportService;
import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.cc.ccbootdemo.facade.domain.common.param.GateWayReqParam;
import com.cc.ccbootdemo.facade.domain.common.util.AssertUtil;
import com.cc.ccbootdemo.facade.domain.common.util.RetryUtil;
import com.cc.ccbootdemo.facade.domain.common.util.upyun.UploadUtil;
import com.cc.ccbootdemo.facade.domain.dataobject.OperateBiz;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/30 9:53.
 */
@Slf4j
@Service
public class SupportServiceImpl extends BaseServiceImpl implements SupportService{


//    static ExecutorService specialPool = initPool(2,4,"specialPool",log);


    @Override
    public void gateWayReq(GateWayReqParam param) {
        AssertUtil.isNullParamStr(param.getOperId(),"非法请求！");
        OperateBiz bizDetail=operateBizManager.getOperBizByPrimary(param.getOperId());
        AssertUtil.isTrueBIZ(bizDetail==null,"操作对象不存在！");
        //TODO 校验请求人员权限

        //发起请求
        try {
            RetryUtil.execute(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    postReq(bizDetail);
                    return true;
                }
            },2000,1000,0);
        } catch (ExecutionException |InterruptedException e) {
           throw new BusinessException("请求异常");
        } catch (TimeoutException e) {
            throw new BusinessException("请求超时,请稍后重试!");
        }


    }

    @Override
    public String test() throws ExecutionException, InterruptedException {
        Long startTime=System.currentTimeMillis();
       Map<String,FutureTask<Map<String,String>>> result=new HashMap<>();

        FutureTask<Map<String,String>> task =new FutureTask<>(() -> {
                Map<String,String> res=new HashMap<>();
                try{
                    int i=0;
                    while((System.currentTimeMillis()-startTime)<5000){

                        if(!Thread.interrupted()){
                            i++;
                        }else{
                            System.out.println("子线程被中断");
                            break;
                        }
                    }
                    System.out.println("子线程计算任务:  执行完成! i="+i);

                }catch(Exception e){
                    log.error("邮箱验证码发送失败",e);
                    res.put("res","error");
                    return  res;
                }
                res.put("res", "ok") ;
                return  res;
            });
        result.put("test",task );
        specialPoolBase.execute(task);
        System.out.println("子线程提交完毕，主线程继续执行！");


//          超时时间小于2s或者是返回结果是error则一直等待 get方法是阻塞的
            try {
                String res = task.get(2000, TimeUnit.MILLISECONDS).get("res");
                if(!"ok".equals(res)){
                    throw new BusinessException("发送邮件超时");

                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                task.cancel(true);
                throw e;
            }catch(TimeoutException e) {
                System.out.println("子线程超时,取消执行");
                task.cancel(true);
            }

//            log.info(Thread.currentThread().getName()+" futureTask.get()"+ task.get().get("res"));


        return "ok";
    }

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
