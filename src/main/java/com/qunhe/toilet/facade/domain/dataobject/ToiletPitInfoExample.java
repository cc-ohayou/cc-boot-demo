package com.qunhe.toilet.facade.domain.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToiletPitInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ToiletPitInfoExample() {
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

        public Criteria andUseStateIsNull() {
            addCriterion("use_state is null");
            return (Criteria) this;
        }

        public Criteria andUseStateIsNotNull() {
            addCriterion("use_state is not null");
            return (Criteria) this;
        }

        public Criteria andUseStateEqualTo(Boolean value) {
            addCriterion("use_state =", value, "useState");
            return (Criteria) this;
        }

        public Criteria andUseStateNotEqualTo(Boolean value) {
            addCriterion("use_state <>", value, "useState");
            return (Criteria) this;
        }

        public Criteria andUseStateGreaterThan(Boolean value) {
            addCriterion("use_state >", value, "useState");
            return (Criteria) this;
        }

        public Criteria andUseStateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("use_state >=", value, "useState");
            return (Criteria) this;
        }

        public Criteria andUseStateLessThan(Boolean value) {
            addCriterion("use_state <", value, "useState");
            return (Criteria) this;
        }

        public Criteria andUseStateLessThanOrEqualTo(Boolean value) {
            addCriterion("use_state <=", value, "useState");
            return (Criteria) this;
        }

        public Criteria andUseStateIn(List<Boolean> values) {
            addCriterion("use_state in", values, "useState");
            return (Criteria) this;
        }

        public Criteria andUseStateNotIn(List<Boolean> values) {
            addCriterion("use_state not in", values, "useState");
            return (Criteria) this;
        }

        public Criteria andUseStateBetween(Boolean value1, Boolean value2) {
            addCriterion("use_state between", value1, value2, "useState");
            return (Criteria) this;
        }

        public Criteria andUseStateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("use_state not between", value1, value2, "useState");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignIsNull() {
            addCriterion("inconvenient_sign is null");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignIsNotNull() {
            addCriterion("inconvenient_sign is not null");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignEqualTo(Boolean value) {
            addCriterion("inconvenient_sign =", value, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignNotEqualTo(Boolean value) {
            addCriterion("inconvenient_sign <>", value, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignGreaterThan(Boolean value) {
            addCriterion("inconvenient_sign >", value, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignGreaterThanOrEqualTo(Boolean value) {
            addCriterion("inconvenient_sign >=", value, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignLessThan(Boolean value) {
            addCriterion("inconvenient_sign <", value, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignLessThanOrEqualTo(Boolean value) {
            addCriterion("inconvenient_sign <=", value, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignIn(List<Boolean> values) {
            addCriterion("inconvenient_sign in", values, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignNotIn(List<Boolean> values) {
            addCriterion("inconvenient_sign not in", values, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignBetween(Boolean value1, Boolean value2) {
            addCriterion("inconvenient_sign between", value1, value2, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andInconvenientSignNotBetween(Boolean value1, Boolean value2) {
            addCriterion("inconvenient_sign not between", value1, value2, "inconvenientSign");
            return (Criteria) this;
        }

        public Criteria andTolietTypeIsNull() {
            addCriterion("toliet_type is null");
            return (Criteria) this;
        }

        public Criteria andTolietTypeIsNotNull() {
            addCriterion("toliet_type is not null");
            return (Criteria) this;
        }

        public Criteria andTolietTypeEqualTo(String value) {
            addCriterion("toliet_type =", value, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeNotEqualTo(String value) {
            addCriterion("toliet_type <>", value, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeGreaterThan(String value) {
            addCriterion("toliet_type >", value, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeGreaterThanOrEqualTo(String value) {
            addCriterion("toliet_type >=", value, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeLessThan(String value) {
            addCriterion("toliet_type <", value, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeLessThanOrEqualTo(String value) {
            addCriterion("toliet_type <=", value, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeLike(String value) {
            addCriterion("toliet_type like", value, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeNotLike(String value) {
            addCriterion("toliet_type not like", value, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeIn(List<String> values) {
            addCriterion("toliet_type in", values, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeNotIn(List<String> values) {
            addCriterion("toliet_type not in", values, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeBetween(String value1, String value2) {
            addCriterion("toliet_type between", value1, value2, "tolietType");
            return (Criteria) this;
        }

        public Criteria andTolietTypeNotBetween(String value1, String value2) {
            addCriterion("toliet_type not between", value1, value2, "tolietType");
            return (Criteria) this;
        }

        public Criteria andBuildingNoIsNull() {
            addCriterion("building_no is null");
            return (Criteria) this;
        }

        public Criteria andBuildingNoIsNotNull() {
            addCriterion("building_no is not null");
            return (Criteria) this;
        }

        public Criteria andBuildingNoEqualTo(Long value) {
            addCriterion("building_no =", value, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andBuildingNoNotEqualTo(Long value) {
            addCriterion("building_no <>", value, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andBuildingNoGreaterThan(Long value) {
            addCriterion("building_no >", value, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andBuildingNoGreaterThanOrEqualTo(Long value) {
            addCriterion("building_no >=", value, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andBuildingNoLessThan(Long value) {
            addCriterion("building_no <", value, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andBuildingNoLessThanOrEqualTo(Long value) {
            addCriterion("building_no <=", value, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andBuildingNoIn(List<Long> values) {
            addCriterion("building_no in", values, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andBuildingNoNotIn(List<Long> values) {
            addCriterion("building_no not in", values, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andBuildingNoBetween(Long value1, Long value2) {
            addCriterion("building_no between", value1, value2, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andBuildingNoNotBetween(Long value1, Long value2) {
            addCriterion("building_no not between", value1, value2, "buildingNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoIsNull() {
            addCriterion("floor_no is null");
            return (Criteria) this;
        }

        public Criteria andFloorNoIsNotNull() {
            addCriterion("floor_no is not null");
            return (Criteria) this;
        }

        public Criteria andFloorNoEqualTo(Integer value) {
            addCriterion("floor_no =", value, "floorNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoNotEqualTo(Integer value) {
            addCriterion("floor_no <>", value, "floorNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoGreaterThan(Integer value) {
            addCriterion("floor_no >", value, "floorNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("floor_no >=", value, "floorNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoLessThan(Integer value) {
            addCriterion("floor_no <", value, "floorNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoLessThanOrEqualTo(Integer value) {
            addCriterion("floor_no <=", value, "floorNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoIn(List<Integer> values) {
            addCriterion("floor_no in", values, "floorNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoNotIn(List<Integer> values) {
            addCriterion("floor_no not in", values, "floorNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoBetween(Integer value1, Integer value2) {
            addCriterion("floor_no between", value1, value2, "floorNo");
            return (Criteria) this;
        }

        public Criteria andFloorNoNotBetween(Integer value1, Integer value2) {
            addCriterion("floor_no not between", value1, value2, "floorNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoIsNull() {
            addCriterion("room_no is null");
            return (Criteria) this;
        }

        public Criteria andRoomNoIsNotNull() {
            addCriterion("room_no is not null");
            return (Criteria) this;
        }

        public Criteria andRoomNoEqualTo(Integer value) {
            addCriterion("room_no =", value, "roomNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoNotEqualTo(Integer value) {
            addCriterion("room_no <>", value, "roomNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoGreaterThan(Integer value) {
            addCriterion("room_no >", value, "roomNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("room_no >=", value, "roomNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoLessThan(Integer value) {
            addCriterion("room_no <", value, "roomNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoLessThanOrEqualTo(Integer value) {
            addCriterion("room_no <=", value, "roomNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoIn(List<Integer> values) {
            addCriterion("room_no in", values, "roomNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoNotIn(List<Integer> values) {
            addCriterion("room_no not in", values, "roomNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoBetween(Integer value1, Integer value2) {
            addCriterion("room_no between", value1, value2, "roomNo");
            return (Criteria) this;
        }

        public Criteria andRoomNoNotBetween(Integer value1, Integer value2) {
            addCriterion("room_no not between", value1, value2, "roomNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoIsNull() {
            addCriterion("pit_position_no is null");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoIsNotNull() {
            addCriterion("pit_position_no is not null");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoEqualTo(Integer value) {
            addCriterion("pit_position_no =", value, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoNotEqualTo(Integer value) {
            addCriterion("pit_position_no <>", value, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoGreaterThan(Integer value) {
            addCriterion("pit_position_no >", value, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("pit_position_no >=", value, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoLessThan(Integer value) {
            addCriterion("pit_position_no <", value, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoLessThanOrEqualTo(Integer value) {
            addCriterion("pit_position_no <=", value, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoIn(List<Integer> values) {
            addCriterion("pit_position_no in", values, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoNotIn(List<Integer> values) {
            addCriterion("pit_position_no not in", values, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoBetween(Integer value1, Integer value2) {
            addCriterion("pit_position_no between", value1, value2, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andPitPositionNoNotBetween(Integer value1, Integer value2) {
            addCriterion("pit_position_no not between", value1, value2, "pitPositionNo");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusIsNull() {
            addCriterion("available_status is null");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusIsNotNull() {
            addCriterion("available_status is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusEqualTo(String value) {
            addCriterion("available_status =", value, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusNotEqualTo(String value) {
            addCriterion("available_status <>", value, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusGreaterThan(String value) {
            addCriterion("available_status >", value, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusGreaterThanOrEqualTo(String value) {
            addCriterion("available_status >=", value, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusLessThan(String value) {
            addCriterion("available_status <", value, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusLessThanOrEqualTo(String value) {
            addCriterion("available_status <=", value, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusLike(String value) {
            addCriterion("available_status like", value, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusNotLike(String value) {
            addCriterion("available_status not like", value, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusIn(List<String> values) {
            addCriterion("available_status in", values, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusNotIn(List<String> values) {
            addCriterion("available_status not in", values, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusBetween(String value1, String value2) {
            addCriterion("available_status between", value1, value2, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableStatusNotBetween(String value1, String value2) {
            addCriterion("available_status not between", value1, value2, "availableStatus");
            return (Criteria) this;
        }

        public Criteria andAddressInfoIsNull() {
            addCriterion("address_info is null");
            return (Criteria) this;
        }

        public Criteria andAddressInfoIsNotNull() {
            addCriterion("address_info is not null");
            return (Criteria) this;
        }

        public Criteria andAddressInfoEqualTo(String value) {
            addCriterion("address_info =", value, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoNotEqualTo(String value) {
            addCriterion("address_info <>", value, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoGreaterThan(String value) {
            addCriterion("address_info >", value, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoGreaterThanOrEqualTo(String value) {
            addCriterion("address_info >=", value, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoLessThan(String value) {
            addCriterion("address_info <", value, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoLessThanOrEqualTo(String value) {
            addCriterion("address_info <=", value, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoLike(String value) {
            addCriterion("address_info like", value, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoNotLike(String value) {
            addCriterion("address_info not like", value, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoIn(List<String> values) {
            addCriterion("address_info in", values, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoNotIn(List<String> values) {
            addCriterion("address_info not in", values, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoBetween(String value1, String value2) {
            addCriterion("address_info between", value1, value2, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andAddressInfoNotBetween(String value1, String value2) {
            addCriterion("address_info not between", value1, value2, "addressInfo");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
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