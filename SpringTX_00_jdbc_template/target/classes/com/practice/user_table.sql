CREATE DATABASE `user_table`;

USE `user_table`;

CREATE TABLE `user_table` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB;