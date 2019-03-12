CREATE DATABASE IF NOT EXISTS `testdb`;
USE `testdb`;

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` varchar(20) NOT NULL,
  `account_name` varchar(32) NOT NULL,
  `balance` DECIMAL(18,2) NOT NULL,
  `currency_code` char(3) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `account` VALUES ('1', 'USD Account', 1000, 'USD');
INSERT INTO `account` VALUES ('2', 'HKD Account', 1000, 'HKD');
INSERT INTO `account` VALUES ('3', 'HKD Account', 1000, 'HKD');