/*
Navicat MySQL Data Transfer

Source Server         : sit-ps1
Source Server Version : 50722
Source Host           : 120.55.50.109:3306
Source Database       : test02

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-02-24 12:16:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '城市编号',
  `province_id` int(10) NOT NULL COMMENT '省份编号',
  `city_name` varchar(25) NOT NULL,
  `description` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('1', '2', 'hz', 'sdfgdfgdfg');

-- ----------------------------
-- Table structure for redis_key
-- ----------------------------
DROP TABLE IF EXISTS `redis_key`;
CREATE TABLE `redis_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `key` varchar(100) NOT NULL COMMENT 'key',
  `name` varchar(32) NOT NULL COMMENT '简短的名称',
  `description` varchar(1000) NOT NULL COMMENT 'key更加全面的描述',
  `parent_key` varchar(32) NOT NULL COMMENT '父级key',
  `type` varchar(10) NOT NULL COMMENT 'key类型 hash  set string zset list',
  `biz_type` varchar(50) NOT NULL DEFAULT 'others' COMMENT '业务关联类型',
  `create_time` datetime NOT NULL COMMENT '记录创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次修改时间',
  `available` char(1) NOT NULL DEFAULT '1' COMMENT '有效与否 默认有效1 0无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key_parent` (`key`,`parent_key`) USING BTREE COMMENT '父子组合必须保持唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='redisKey信息表';

-- ----------------------------
-- Records of redis_key
-- ----------------------------
