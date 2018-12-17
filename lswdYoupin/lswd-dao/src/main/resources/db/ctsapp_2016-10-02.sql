# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 192.168.8.220 (MySQL 5.1.73)
# Database: ctsapp
# Generation Time: 2016-10-02 06:27:27 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

# Dump of table ctsappbk_captcha
# ------------------------------------------------------------

CREATE TABLE `ctsappbk_captcha` (
  `id`           BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `phone`        VARCHAR(32)                  DEFAULT NULL
  COMMENT '手机号',
  `captcha`      VARCHAR(16)                  DEFAULT NULL
  COMMENT '验证码',
  `expired_time` BIGINT(20)                   DEFAULT NULL
  COMMENT '过期时间',
  `used`         TINYINT(4)                   DEFAULT '0'
  COMMENT '是否使用过 （防止验证码重复使用） 0 未使用  1 已使用',
  `create_time`  DATETIME                     DEFAULT NULL
  COMMENT '创建时间',
  `update_time`  DATETIME                     DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# Dump of table ctsappbk_order
# ------------------------------------------------------------

CREATE TABLE `ctsappbk_order` (
  `id`               BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `member_no`        BIGINT(11)                   DEFAULT NULL
  COMMENT '会员编号（返回值有此字段  电商用户ID）',
  `order_code`       VARCHAR(21)                  DEFAULT ''
  COMMENT '订单编号（返回值有此字段）',
  `order_type`       TINYINT(4)                   DEFAULT NULL
  COMMENT '订单类型',
  `channel`          VARCHAR(16)                  DEFAULT NULL
  COMMENT '订单渠道（存中文）',
  `product_no`       BIGINT(11)                   DEFAULT NULL
  COMMENT '产品ID',
  `product_name`     VARCHAR(300)                 DEFAULT ''
  COMMENT '产品名称',
  `total_money`      INT(11)                      DEFAULT NULL
  COMMENT '订单金额（单位：分  待定）',
  `should_pay_money` INT(11)                      DEFAULT NULL
  COMMENT '应付金额（单位：分  待定）',
  `book_time`        VARCHAR(32)                  DEFAULT NULL
  COMMENT '预订时间',
  `status`           TINYINT(4)                   DEFAULT NULL
  COMMENT '订单状态（待定）',
  `create_time`      DATETIME                     DEFAULT NULL
  COMMENT '创建时间',
  `update_time`      DATETIME                     DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# Dump of table ctsappbk_user
# ------------------------------------------------------------

CREATE TABLE `ctsappbk_user` (
  `id`          BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `phone`       VARCHAR(20)                  DEFAULT NULL
  COMMENT '手机号',
  `email`       VARCHAR(128)                 DEFAULT NULL
  COMMENT '邮箱',
  `password`    VARCHAR(256)                 DEFAULT NULL
  COMMENT '密码',
  `user_name`   VARCHAR(64)                  DEFAULT NULL
  COMMENT 'app用户名（预留 昵称）',
  `real_name`   VARCHAR(32)                  DEFAULT NULL
  COMMENT '真实姓名（实名认证预留）',
  `status`      TINYINT(4)                   DEFAULT NULL
  COMMENT '会员状态：0未开启 1开启',
  `uuid`        VARCHAR(64)                  DEFAULT NULL
  COMMENT '用户uuid（该列值为uuid然后再md5）',
  `member_no`   BIGINT(11)                   DEFAULT NULL
  COMMENT '会员编号（电商用户ID）（有唯一性约束，防止重复插入）',
  `card_number` VARCHAR(32)                  DEFAULT NULL
  COMMENT '会员卡号',
  `reg_source`  TINYINT(4)                   DEFAULT NULL
  COMMENT '注册来源  0 APP',
  `create_time` DATETIME                     DEFAULT NULL
  COMMENT '注册时间',
  `update_time` DATETIME                     DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_no` (`member_no`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
