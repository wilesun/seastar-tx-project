/*
Navicat MySQL Data Transfer

Source Server         : mysql_localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : seastar_tx

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-08-30 16:25:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app1_tx1
-- ----------------------------
DROP TABLE IF EXISTS `app1_tx1`;
CREATE TABLE `app1_tx1` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app1_tx1
-- ----------------------------
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');
INSERT INTO `app1_tx1` VALUES ('2', 'aaa');

-- ----------------------------
-- Table structure for app1_tx2
-- ----------------------------
DROP TABLE IF EXISTS `app1_tx2`;
CREATE TABLE `app1_tx2` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app1_tx2
-- ----------------------------
INSERT INTO `app1_tx2` VALUES ('2', 'aaa');

-- ----------------------------
-- Table structure for app1_tx3
-- ----------------------------
DROP TABLE IF EXISTS `app1_tx3`;
CREATE TABLE `app1_tx3` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app1_tx3
-- ----------------------------
INSERT INTO `app1_tx3` VALUES ('2', 'aaa');

-- ----------------------------
-- Table structure for tx1
-- ----------------------------
DROP TABLE IF EXISTS `tx1`;
CREATE TABLE `tx1` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tx1
-- ----------------------------
INSERT INTO `tx1` VALUES ('1', null);
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');
INSERT INTO `tx1` VALUES ('2', 'aaa');

-- ----------------------------
-- Table structure for tx2
-- ----------------------------
DROP TABLE IF EXISTS `tx2`;
CREATE TABLE `tx2` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tx2
-- ----------------------------
INSERT INTO `tx2` VALUES ('2', 'aaa');
