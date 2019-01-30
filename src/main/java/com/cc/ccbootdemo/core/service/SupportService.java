package com.cc.ccbootdemo.core.service;

import com.cc.ccbootdemo.facade.domain.common.param.GateWayReqParam;

import java.util.concurrent.ExecutionException;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/30 9:52.
 */
public interface SupportService {

    void gateWayReq(GateWayReqParam param);

    String test() throws ExecutionException, InterruptedException;
}
