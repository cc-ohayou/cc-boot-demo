/*
Navicat MySQL Data Transfer

Source Server         : sit-ps1
Source Server Version : 50722
Source Host           : 120.55.50.109:3306
Source Database       : test01

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-02-24 12:16:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for operate_biz
-- ----------------------------
DROP TABLE IF EXISTS `operate_biz`;
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

-- ----------------------------
-- Records of operate_biz
-- ----------------------------
INSERT INTO `operate_biz` VALUES ('1', '递延diyan', '定时任务', 'ETF-TASK', '递延操作 请求后执行的是14:00递延定时逻辑', '定时任务', 'sit', 'http://47.96.9.161:8090/v1/support/defer/manual', '2019-01-14 12:15:50', '2019-01-25 10:54:34', '001', '');
INSERT INTO `operate_biz` VALUES ('2', '强平', '定时任务', 'ETF-TASK', '定时任务强平 点击后进行到期日强平逻辑操作', '定时任务', 'sit', 'http://47.96.9.161:8090/v1/support/forceSell/manual', '2019-01-14 12:15:51', '2019-01-25 10:23:37', '001', '');
INSERT INTO `operate_biz` VALUES ('3', '推送配置更新', '功能接口', 'phoenix-ps', 'phoenix ps清空原有商户个推推送配置', '推送', 'dev', 'http://118.31.74.51:8180/v1/support/clean/pushConfig', '2019-01-21 15:02:11', '2019-01-25 10:23:38', '001', '');
INSERT INTO `operate_biz` VALUES ('4', '全局配置获取', '配置', 'phoenix-ps', 'phoenix ps获取全局配置 推送配置消息订阅配置等', 'ps全局配置', 'sit', 'http://118.31.74.51:8180/v1/support/get/settings', '2019-01-21 15:02:11', '2019-01-25 10:23:39', '001', '');
INSERT INTO `operate_biz` VALUES ('5', '投资人账号预热', '功能接口', 'phoenix-task', 'phoenix task预热资金账号', 'ps-task', 'dev', 'http://116.62.163.220:8280/hello/warmUp', '2019-01-21 15:05:13', '2019-01-25 10:23:39', '001', '');
INSERT INTO `operate_biz` VALUES ('6', '消息订阅配置刷新', '配置', 'phoenix-ps', 'etf清空刷新订阅配置', '消息配置', 'dev', 'http://118.31.74.51:8180/v1/msg/clear/subConfigMap', '2019-01-21 15:05:13', '2019-01-25 10:23:40', '001', '');
INSERT INTO `operate_biz` VALUES ('7', '消息配置刷新', '配置', 'etf-server', 'phoenix task预热资金账号', '消息配置', 'sit', 'http://47.96.9.161:8109/v1/msg/clear/subConfigMap', '2019-01-21 15:07:43', '2019-01-25 10:23:42', '001', '');

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session` (
  `session_id` varchar(30) NOT NULL COMMENT 'Session表ID',
  `merchant_id` varchar(30) NOT NULL DEFAULT '1001' COMMENT '商户号,关联merchant_info表merchant_id',
  `user_id` varchar(30) NOT NULL COMMENT '用户ID,关联user表user_id',
  `sid` varchar(30) DEFAULT NULL COMMENT 'Session ID',
  `expire_time` varchar(20) DEFAULT NULL COMMENT '失效时间',
  `source` varchar(20) DEFAULT '' COMMENT '来源 web:web,ios:ios,android:android weixin:weixin',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`session_id`),
  KEY `idx_sid_userid_merchantid` (`sid`,`user_id`,`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录Session表';

-- ----------------------------
-- Records of session
-- ----------------------------
INSERT INTO `session` VALUES ('1085173518146342912', '1001', '1', '1097410264797220864', '1550477970681', 'android', '2019-01-15 07:55:03', '2019-02-18 02:09:38');
INSERT INTO `session` VALUES ('1088341918596403200', '1001', '1088341760072683520', '1088347153834381312', '1548921956647', 'android', '2019-01-24 01:45:08', '2019-01-24 02:05:57');
INSERT INTO `session` VALUES ('1088380540154482688', '1001', '1088378869277986816', '1088612465611837440', '1548985211903', 'android', '2019-01-24 04:18:37', '2019-01-24 19:40:12');
INSERT INTO `session` VALUES ('1088652437832273920', '1001', '1088652412691615744', '1088680966976376832', '1549001543902', 'android', '2019-01-24 22:19:02', '2019-01-25 00:12:24');
INSERT INTO `session` VALUES ('1089839815414452224', '1001', '1089839776306761728', '1090804851427446784', '1549507917414', 'android', '2019-01-28 04:57:15', '2019-01-30 20:51:57');
INSERT INTO `session` VALUES ('1090536381024505856', '1001', '1090536356643016704', '1090536842167259136', '1548839219024', 'android', '2019-01-30 03:05:09', '2019-01-30 03:06:48');
INSERT INTO `session` VALUES ('1090842353974579200', '1001', '1090842274257637376', '1090842353957801984', '1549516858716', 'android', '2019-01-30 23:20:59', '2019-01-30 23:20:59');
INSERT INTO `session` VALUES ('1091165336509288448', '1001', '1091165241709629440', '1093701373706047488', '1549593702138', 'android', '2019-01-31 20:44:24', '2019-02-05 18:44:27');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `description` varchar(11) DEFAULT '' COMMENT '描述',
  `user_name` varchar(11) DEFAULT '' COMMENT '注册用户名',
  `nick_name` varchar(11) DEFAULT '' COMMENT '昵称',
  `mail` varchar(30) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '电话',
  `pwd` varchar(50) DEFAULT '' COMMENT '密码',
  `head_image` varchar(500) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `salty` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`uid`),
  KEY `phone_index` (`phone`) USING BTREE,
  KEY `nick_index` (`nick_name`) USING BTREE,
  KEY `mail` (`mail`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1097411124860227585 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '', 'cc', 'ccの微笑', '13758080693@163.com', '13758080693', '82d10ffbcc5e1d5873863da5d8f6d497', 'https://ddy98.b0.upaiyun.com/user/headImages/1548908543647.jpg', '2019-01-22 18:17:51', '2019-01-31 12:22:24', '123123');
INSERT INTO `user` VALUES ('1089839776306761728', '', '', 'cc02 lala😋', '840794748@qq.com', '13758080692', 'f88bf297e6d589b19afa3724d4c68c85', 'https://ddy98.b0.upaiyun.com/user/headImages/1548673044924.jpg', '2019-01-28 18:57:05', '2019-01-30 17:45:01', 'vje67j');
INSERT INTO `user` VALUES ('1090842274257637376', '', '', 'Maggie', 'liting@ddy98.com', '18814883904', '5b3b54f38953130dd1e4460161224347', 'https://ddy98.b0.upaiyun.com/user/headImages/1548912238885.jpg', '2019-01-31 13:20:39', '2019-01-31 13:23:59', 'an3p6b');
INSERT INTO `user` VALUES ('1091165241709629440', '', '', 'cc003', '840794749@qq.com', '13758080995', '8a972e138f730612751fc58d551f5f76', 'https://ddy98.b0.upaiyun.com/user/headImages/1548989122326.jpg', '2019-02-01 10:44:01', '2019-02-01 10:45:22', 'ah1z6o');
INSERT INTO `user` VALUES ('1097411124860227584', '', '', 'wwh5jzx', '13758080693@169.com', '13758080696', '042c1199d952b2a503ca69a7224431a7', '', '2019-02-18 16:22:56', '2019-02-18 16:22:56', 'efnllh');

-- ----------------------------
-- Table structure for user_attach
-- ----------------------------
DROP TABLE IF EXISTS `user_attach`;
CREATE TABLE `user_attach` (
  `uid` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `main_bg_url` varchar(200) NOT NULL DEFAULT '' COMMENT '主页背景图url',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `role_codes` varchar(50) NOT NULL DEFAULT '001,' COMMENT '角色 用于区别人员所拥有的权限',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1097411124860227585 DEFAULT CHARSET=utf8mb4 COMMENT='用户附属信息表';

-- ----------------------------
-- Records of user_attach
-- ----------------------------
INSERT INTO `user_attach` VALUES ('1', 'https://ddy98.b0.upaiyun.com/user/bgImg/1548908593854.jpg', '2019-01-20 00:12:05', '2019-01-31 12:23:14', '001,');
INSERT INTO `user_attach` VALUES ('1088022822041030656', '', '2019-01-23 18:37:10', '2019-01-23 18:37:10', '001,');
INSERT INTO `user_attach` VALUES ('1088317655256600576', '', '2019-01-24 14:08:43', '2019-01-24 14:08:43', '001,');
INSERT INTO `user_attach` VALUES ('1088319266087440384', '', '2019-01-24 14:15:07', '2019-01-24 14:15:07', '001,');
INSERT INTO `user_attach` VALUES ('1088333977138958336', '', '2019-01-24 15:13:35', '2019-01-24 15:13:35', '001,');
INSERT INTO `user_attach` VALUES ('1088341760072683520', '', '2019-01-24 15:44:30', '2019-01-24 15:44:30', '001,');
INSERT INTO `user_attach` VALUES ('1088347103242686464', '', '2019-01-24 16:05:44', '2019-01-24 16:05:44', '001,');
INSERT INTO `user_attach` VALUES ('1088378080966938624', '', '2019-01-24 18:08:50', '2019-01-24 18:08:50', '001,');
INSERT INTO `user_attach` VALUES ('1088378869277986816', '', '2019-01-24 18:11:58', '2019-01-24 18:11:58', '001,');
INSERT INTO `user_attach` VALUES ('1088652412691615744', '', '2019-01-25 12:18:56', '2019-01-25 12:18:56', '001,');
INSERT INTO `user_attach` VALUES ('1089829424261173248', '', '2019-01-28 18:15:57', '2019-01-28 18:15:57', '001,');
INSERT INTO `user_attach` VALUES ('1089839776306761728', '', '2019-01-28 18:57:05', '2019-01-28 18:57:05', '000');
INSERT INTO `user_attach` VALUES ('1090536356643016704', '', '2019-01-30 17:05:03', '2019-01-30 17:05:03', '000');
INSERT INTO `user_attach` VALUES ('1090842274257637376', '', '2019-01-31 13:20:39', '2019-01-31 13:25:27', '001,');
INSERT INTO `user_attach` VALUES ('1091165241709629440', 'https://ddy98.b0.upaiyun.com/user/bgImg/1548992537404.jpg', '2019-02-01 10:44:01', '2019-02-01 11:42:17', '000');
INSERT INTO `user_attach` VALUES ('1097411124860227584', '', '2019-02-18 16:22:56', '2019-02-18 16:22:56', '000');












CREATE TABLE `feed_sub` (
  `id` bigint(30) NOT NULL DEFAULT 0 COMMENT 'operId',
  `feed_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'feed流id',
  `uid` varchar(50) NOT NULL DEFAULT '' COMMENT '用户id 对应钉钉群的id 每个用户对应一个钉钉机器人',

  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
  PRIMARY KEY (`id`),
  uk_uf(feedd_id,uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='feed订阅关系表';


CREATE TABLE `feed_info` (
  `id` bigint(30) NOT NULL DEFAULT '0' COMMENT 'operId',
  `feed_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'feed流id',
  `uid` varchar(50) NOT NULL DEFAULT '' COMMENT '用户id 对应钉钉群的id 每个用户对应一个钉钉机器人',

  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
  PRIMARY KEY (`id`),
  uk_uf(feedd_id,uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='operate_biz';