package com.qunhe.toilet.core.manager.impl;

import com.qunhe.toilet.core.manager.ToiletSubRecordManager;
import com.qunhe.toilet.core.mapper.master.ToiletPitSubRecordMapper;
import com.qunhe.toilet.facade.domain.common.condition.ToiletPitSubRecordCondition;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitSubRecord;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitSubRecordExample;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author bupo
 * @DATE 2020/8/21 16:23
 * @Description
 */
@Component
public class ToiletSubRecordManagerImpl  extends BaseManagerImpl implements ToiletSubRecordManager {

    @Resource
    ToiletPitSubRecordMapper  toiletPitSubRecordMapper;

    @Override
    public boolean addRecord(ToiletPitSubRecord record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setDeleted(false);
        return toiletPitSubRecordMapper.insert(record)>0;
    }

    @Override
    public List<ToiletPitSubRecord> selectByCOndition(ToiletPitSubRecordCondition condition) {
        ToiletPitSubRecordExample  example= getGeneralExample(condition);
        return toiletPitSubRecordMapper.selectByExample(example);
    }

    @Override
    public boolean updateByCondition(ToiletPitSubRecord record,ToiletPitSubRecordCondition condition) {
        ToiletPitSubRecordExample  example= getGeneralExample(condition);
        return toiletPitSubRecordMapper.updateByExampleSelective(record,example)>0;
    }


    private ToiletPitSubRecordExample getGeneralExample(ToiletPitSubRecordCondition condition) {

        ToiletPitSubRecordExample  example =new ToiletPitSubRecordExample();
        ToiletPitSubRecordExample.Criteria  criteria =  example.createCriteria();

        if(nonNull(condition.getDeleted())){
            criteria.andDeletedEqualTo(condition.getDeleted());
        }
        if(nonNull(condition.getFloorId())){
            criteria.andFloorIdEqualTo(condition.getFloorId());
        }

        if(nonNull(condition.getNotifyEndTimeGt())){
            criteria.andNotifyEndTimeGreaterThan(condition.getNotifyEndTimeGt());
        }
        if(nonNull(condition.getNotifyEndTimeLt())){
            criteria.andNotifyEndTimeLessThanOrEqualTo(condition.getNotifyEndTimeLt());
        }

        return example;
    }
}
