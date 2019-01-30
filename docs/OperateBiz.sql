-- auto Generated on 2019-01-22 14:05:19 
-- DROP TABLE IF EXISTS `operate_biz`; 
CREATE TABLE `operate_biz` (
  `oper_id` bigint(30) NOT NULL DEFAULT '0' COMMENT 'operId',
  `oper_name` varchar(50) NOT NULL DEFAULT '' COMMENT '操作简称 比如 递延',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `project` varchar(50) NOT NULL DEFAULT '' COMMENT '项目归属',
  `desc` varchar(50) NOT NULL DEFAULT '' COMMENT 'desc',
  `label` varchar(50) NOT NULL DEFAULT '' COMMENT '标签',
  `env_type` varchar(50) NOT NULL DEFAULT '' COMMENT '环境类型',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT 'url',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
  `role_code` varchar(30) DEFAULT '001' COMMENT '此操作所需要的权限代码',
  `req_param_str` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='operate_biz';
