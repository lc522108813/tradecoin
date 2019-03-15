/*
 Navicat Premium Data Transfer

 Source Server         : huobi
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : huobi

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 15/03/2019 17:13:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(11) DEFAULT NULL COMMENT '交易类型： 1限价买 2限价卖 3市价买 4市价卖',
  `order_price` decimal(12,6) DEFAULT NULL COMMENT '委托价格',
  `order_amount` decimal(12,6) DEFAULT NULL COMMENT '委托数量',
  `processed_amount` decimal(12,6) DEFAULT NULL COMMENT '已完成数量',
  `vot` decimal(12,6) DEFAULT NULL COMMENT '交易额',
  `fee` decimal(12,6) DEFAULT NULL COMMENT '手续费',
  `total` decimal(12,6) DEFAULT NULL COMMENT '交易总额',
  `status` int(11) DEFAULT NULL COMMENT '状态 0未成交　1部分成交　2已完成　3已取消 4废弃（该状态已不再使用） 5异常 6部分成交已取消 7队列中',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `huobi_order_id` bigint(20) DEFAULT NULL COMMENT '火币订单号',
  `platform_order_id` bigint(20) DEFAULT NULL COMMENT '平台订单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(11) DEFAULT NULL COMMENT '属性类型',
  `attr_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性名',
  `attr_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `account_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `login_pwd` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录密码',
  `total` decimal(12,6) DEFAULT NULL COMMENT '总资产',
  `net_asset` decimal(12,6) DEFAULT NULL COMMENT '净资产',
  `available_cny_display` decimal(12,6) DEFAULT NULL COMMENT '可用人民币',
  `available_btc_display` decimal(12,6) DEFAULT NULL COMMENT '可用比特币',
  `available_ltc_display` decimal(12,6) DEFAULT NULL COMMENT '可用莱特币',
  `frozen_cny_display` decimal(12,6) DEFAULT NULL COMMENT '冻结人民币',
  `frozen_btc_display` decimal(12,6) DEFAULT NULL COMMENT '冻结比特币',
  `frozen_ltc_display` decimal(12,6) DEFAULT NULL COMMENT '冻结莱特币',
  `loan_cny_display` decimal(12,6) DEFAULT NULL COMMENT '待还人民币',
  `loan_btc_display` decimal(12,6) DEFAULT NULL COMMENT '待还比特币',
  `loan_ltc_display` decimal(12,6) DEFAULT NULL COMMENT '待还莱特币',
  `huobi_access_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '火币访问KEY',
  `huobi_secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '火币安全KEY',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
