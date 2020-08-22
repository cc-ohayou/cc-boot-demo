package com.qunhe.toilet.facade.domain.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToiletPitSubRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ToiletPitSubRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeIsNull() {
            addCriterion("notify_start_time is null");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeIsNotNull() {
            addCriterion("notify_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeEqualTo(Date value) {
            addCriterion("notify_start_time =", value, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeNotEqualTo(Date value) {
            addCriterion("notify_start_time <>", value, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeGreaterThan(Date value) {
            addCriterion("notify_start_time >", value, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("notify_start_time >=", value, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeLessThan(Date value) {
            addCriterion("notify_start_time <", value, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("notify_start_time <=", value, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeIn(List<Date> values) {
            addCriterion("notify_start_time in", values, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeNotIn(List<Date> values) {
            addCriterion("notify_start_time not in", values, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeBetween(Date value1, Date value2) {
            addCriterion("notify_start_time between", value1, value2, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("notify_start_time not between", value1, value2, "notifyStartTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeIsNull() {
            addCriterion("notify_end_time is null");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeIsNotNull() {
            addCriterion("notify_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeEqualTo(Date value) {
            addCriterion("notify_end_time =", value, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeNotEqualTo(Date value) {
            addCriterion("notify_end_time <>", value, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeGreaterThan(Date value) {
            addCriterion("notify_end_time >", value, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("notify_end_time >=", value, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeLessThan(Date value) {
            addCriterion("notify_end_time <", value, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("notify_end_time <=", value, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeIn(List<Date> values) {
            addCriterion("notify_end_time in", values, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeNotIn(List<Date> values) {
            addCriterion("notify_end_time not in", values, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeBetween(Date value1, Date value2) {
            addCriterion("notify_end_time between", value1, value2, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andNotifyEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("notify_end_time not between", value1, value2, "notifyEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorIdIsNull() {
            addCriterion("floor_id is null");
            return (Criteria) this;
        }

        public Criteria andFloorIdIsNotNull() {
            addCriterion("floor_id is not null");
            return (Criteria) this;
        }

        public Criteria andFloorIdEqualTo(Integer value) {
            addCriterion("floor_id =", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdNotEqualTo(Integer value) {
            addCriterion("floor_id <>", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdGreaterThan(Integer value) {
            addCriterion("floor_id >", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("floor_id >=", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdLessThan(Integer value) {
            addCriterion("floor_id <", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdLessThanOrEqualTo(Integer value) {
            addCriterion("floor_id <=", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdIn(List<Integer> values) {
            addCriterion("floor_id in", values, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdNotIn(List<Integer> values) {
            addCriterion("floor_id not in", values, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdBetween(Integer value1, Integer value2) {
            addCriterion("floor_id between", value1, value2, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("floor_id not between", value1, value2, "floorId");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Boolean value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Boolean> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Boolean> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("`uid` is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("`uid` is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Long value) {
            addCriterion("`uid` =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Long value) {
            addCriterion("`uid` <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Long value) {
            addCriterion("`uid` >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Long value) {
            addCriterion("`uid` >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Long value) {
            addCriterion("`uid` <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Long value) {
            addCriterion("`uid` <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Long> values) {
            addCriterion("`uid` in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Long> values) {
            addCriterion("`uid` not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Long value1, Long value2) {
            addCriterion("`uid` between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Long value1, Long value2) {
            addCriterion("`uid` not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andLdapIsNull() {
            addCriterion("ldap is null");
            return (Criteria) this;
        }

        public Criteria andLdapIsNotNull() {
            addCriterion("ldap is not null");
            return (Criteria) this;
        }

        public Criteria andLdapEqualTo(String value) {
            addCriterion("ldap =", value, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapNotEqualTo(String value) {
            addCriterion("ldap <>", value, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapGreaterThan(String value) {
            addCriterion("ldap >", value, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapGreaterThanOrEqualTo(String value) {
            addCriterion("ldap >=", value, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapLessThan(String value) {
            addCriterion("ldap <", value, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapLessThanOrEqualTo(String value) {
            addCriterion("ldap <=", value, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapLike(String value) {
            addCriterion("ldap like", value, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapNotLike(String value) {
            addCriterion("ldap not like", value, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapIn(List<String> values) {
            addCriterion("ldap in", values, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapNotIn(List<String> values) {
            addCriterion("ldap not in", values, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapBetween(String value1, String value2) {
            addCriterion("ldap between", value1, value2, "ldap");
            return (Criteria) this;
        }

        public Criteria andLdapNotBetween(String value1, String value2) {
            addCriterion("ldap not between", value1, value2, "ldap");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}