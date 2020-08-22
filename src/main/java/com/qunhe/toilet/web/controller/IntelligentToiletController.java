package com.qunhe.toilet.web.controller;

import com.qunhe.toilet.core.service.IntelligentToiletService;
import com.qunhe.toilet.facade.domain.common.annotation.InterceptRequired;
import com.qunhe.toilet.facade.domain.common.exception.BusinessException;
import com.qunhe.toilet.facade.domain.common.param.ResetPwdParam;
import com.qunhe.toilet.facade.domain.common.param.TolietParam;
import com.qunhe.toilet.facade.domain.common.util.JsonUtil;
import com.qunhe.toilet.facade.domain.common.util.RandomStringUtil;
import com.qunhe.toilet.facade.domain.vo.FloorPitInfoVo;
import com.qunhe.toilet.web.aop.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/14/014 18:31.
 */
@RestController
@RequestMapping(value = "/intelligent/toilet/")
public class IntelligentToiletController {
    @Resource
    IntelligentToiletService  intelligentToiletService;

    @CrossOrigin(origins = "*")
    @InterceptRequired(required = false)
    @RequestMapping(value = "/pit/change")
    public Long pitChangeEvent(@RequestBody(required=false) TolietParam param) {
        return intelligentToiletService.dealToiletPitChangeAction(param);
    }
    @CrossOrigin(origins = "*")
    @InterceptRequired(required = false)
    @RequestMapping(value = "/pit/list")
    public List<FloorPitInfoVo> getPitList(@RequestBody(required=false) TolietParam param) {
        return intelligentToiletService.getPitList(param);
    }


    @CrossOrigin(origins = "*")
    @InterceptRequired(required = false)
    @RequestMapping(value = "/pit/time")
    public long getPitSleepTime(@RequestBody(required=false) TolietParam param) {
        return intelligentToiletService.getPitTime(param);
    }

    @CrossOrigin(origins = "*")
    @InterceptRequired(required = false)
    @RequestMapping(value = "/pit/sub")
    public boolean subPit(@RequestBody(required=false) TolietParam param) {
        return intelligentToiletService.subPit(param);
    }





}