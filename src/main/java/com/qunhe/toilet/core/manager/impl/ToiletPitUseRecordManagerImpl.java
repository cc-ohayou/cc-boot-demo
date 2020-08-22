package com.qunhe.toilet.core.manager.impl;

import com.qunhe.toilet.core.manager.ToiletPitUseRecordManager;
import com.qunhe.toilet.core.mapper.master.ToiletPitUseRecordMapper;
import com.qunhe.toilet.facade.domain.common.condition.ToiletPitInfoCondition;
import com.qunhe.toilet.facade.domain.common.condition.ToiletPitUseRecordCondition;
import com.qunhe.toilet.facade.domain.common.enums.toilet.SexTypeEnum;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitInfoExample;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitUseRecord;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitUseRecordExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author bupo
 * @DATE 2020/8/21 15:58
 * @Description
 */
@Slf4j
@Component
public class ToiletPitUseRecordManagerImpl extends BaseManagerImpl  implements ToiletPitUseRecordManager {


    @Resource
    ToiletPitUseRecordMapper  toiletPitUseRecordMapper;

    @Override
    public boolean addRecord(ToiletPitUseRecord record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setDeleted(false);
        return toiletPitUseRecordMapper.insert(record)>0;
    }

    @Override
    public List<ToiletPitUseRecord> selectByCondition(ToiletPitUseRecordCondition condition) {
        ToiletPitUseRecordExample  example = getGeneralExample(condition);
        return toiletPitUseRecordMapper.selectByExample(example);
    }

    @Override
    public boolean updateByCondition(ToiletPitUseRecord  target,ToiletPitUseRecordCondition condition) {
        ToiletPitUseRecordExample  example = getGeneralExample(condition);
        target.setUpdateTime(new Date());
        return toiletPitUseRecordMapper.updateByExampleSelective(target,example)>0;
    }

    private ToiletPitUseRecordExample getGeneralExample(ToiletPitUseRecordCondition condition) {

        ToiletPitUseRecordExample  example =new ToiletPitUseRecordExample();
        ToiletPitUseRecordExample.Criteria  criteria =  example.createCriteria();

        if(nonNull(condition.getIsDelete())){
            criteria.andDeletedEqualTo(condition.getIsDelete());
        }
        if(nonNull(condition.getPitId())){
            criteria.andTolietPitIdEqualTo(condition.getPitId());
        }

        return example;
    }
}
