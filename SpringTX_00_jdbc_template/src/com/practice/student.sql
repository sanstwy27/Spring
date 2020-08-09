CREATE DATABASE `student`;

USE `student`;

CREATE TABLE `student` (
  `stuid` int(11) NOT NULL AUTO_INCREMENT,
  `stuname` varchar(50) NOT NULL,
  `stuscore` double DEFAULT NULL,
  `stunum` int(11) DEFAULT NULL,
  `stubirth` date DEFAULT NULL,
  PRIMARY KEY (`stuid`)
) ENGINE=InnoDB;