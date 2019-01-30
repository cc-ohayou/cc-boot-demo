package com.cc.ccbootdemo.core.manager.impl;

import com.cc.ccbootdemo.core.manager.OperateBizManager;
import com.cc.ccbootdemo.core.mapper.master.OperateBizDao;
import com.cc.ccbootdemo.facade.domain.bizobject.param.OperListQueryParam;
import com.cc.ccbootdemo.facade.domain.dataobject.OperateBiz;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OperateBizManagerImpl implements OperateBizManager {

    @Resource
    private OperateBizDao operateBizDao;

    public int insert(OperateBiz pojo){
        return operateBizDao.insert(pojo);
    }

    @Override
    public int insertList(List< OperateBiz> pojos){
        return operateBizDao.insertList(pojos);
    }

    @Override
    public List<OperateBiz> selectBySelective(OperateBiz pojo){
        return operateBizDao.selectBySelective(pojo);
    }

    @Override
    public long getOperBizTotalCou(OperListQueryParam param) {
        return operateBizDao.getOperBizTotalCou(param);
    }

    @Override
    public List<OperateBiz> getOperBizList(OperListQueryParam param) {
        return operateBizDao.getOperBizList(param);
    }

    @Override
    public OperateBiz getOperBizByPrimary(String operId) {
        OperateBiz biz=new OperateBiz();
        biz.setOperId(operId);
        List<OperateBiz> list=operateBizDao.selectBySelective(biz);
        return list.isEmpty()?null:list.get(0);
    }

    public int update(OperateBiz pojo){
        return operateBizDao.update(pojo);
    }

}
