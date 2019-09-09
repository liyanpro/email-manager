/*
 Navicat MySQL Data Transfer

 Source Server         : email
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : email

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 07/09/2019 14:05:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for email_task
-- ----------------------------
DROP TABLE IF EXISTS `email_task`;
CREATE TABLE `email_task` (
  `id` varchar(200) NOT NULL,
  `title` varchar(500) DEFAULT NULL,
  `count` int(11) DEFAULT NULL COMMENT '邮件发送数量',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态：0未开始，1执行中，2已暂停，3已结束',
  `domain` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '域名',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
