/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.1.49-community : Database - smartnew
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`smartnew` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `smartnew`;

/*Table structure for table `configuration` */

DROP TABLE IF EXISTS `configuration`;

CREATE TABLE `configuration` (
  `ConfigNo` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `Active` binary(1) NOT NULL DEFAULT '1',
  `HwNo` smallint(5) unsigned NOT NULL DEFAULT '0',
  `AvMinMax` enum('Average','Maximum','Minimum','Rate','Period','Cumulative') DEFAULT NULL,
  `ChannelID` varchar(50) DEFAULT NULL,
  `StartDate` datetime DEFAULT NULL,
  `EndDate` datetime DEFAULT NULL,
  PRIMARY KEY (`ConfigNo`),
  KEY `HwNo` (`HwNo`),
  KEY `HwNo_2` (`HwNo`),
  CONSTRAINT `configuration_ibfk_1` FOREIGN KEY (`HwNo`) REFERENCES `hardware` (`HwNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `configuration` */

/*Table structure for table `customlist` */

DROP TABLE IF EXISTS `customlist`;

CREATE TABLE `customlist` (
  `CustomListNo` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `Code` smallint(5) unsigned DEFAULT NULL,
  `Description` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`CustomListNo`),
  KEY `CustomListNo` (`CustomListNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `customlist` */

/*Table structure for table `datalog` */

DROP TABLE IF EXISTS `datalog`;

CREATE TABLE `datalog` (
  `LogTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ConfigNo` smallint(6) unsigned NOT NULL DEFAULT '0',
  `LogData` float NOT NULL DEFAULT '0',
  KEY `ndxConfigTime` (`ConfigNo`,`LogTime`) USING BTREE,
  KEY `ConfigNo` (`ConfigNo`),
  CONSTRAINT `datalog_ibfk_1` FOREIGN KEY (`ConfigNo`) REFERENCES `configuration` (`ConfigNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `datalog` */

/*Table structure for table `eventlog` */

DROP TABLE IF EXISTS `eventlog`;

CREATE TABLE `eventlog` (
  `EventNo` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `EventTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `UntilTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CustomListNo` smallint(5) unsigned DEFAULT '0',
  `Value` varchar(50) DEFAULT NULL,
  `HwNo` smallint(6) unsigned DEFAULT '0',
  PRIMARY KEY (`EventNo`),
  KEY `CustomListNo` (`CustomListNo`,`HwNo`),
  KEY `HwNo` (`HwNo`),
  CONSTRAINT `eventlog_ibfk_1` FOREIGN KEY (`HwNo`) REFERENCES `hardware` (`HwNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `eventlog_ibfk_2` FOREIGN KEY (`CustomListNo`) REFERENCES `customlist` (`Code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `eventlog` */

/*Table structure for table `hardware` */

DROP TABLE IF EXISTS `hardware`;

CREATE TABLE `hardware` (
  `HwNo` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `Machine` varchar(50) NOT NULL,
  PRIMARY KEY (`HwNo`),
  KEY `HwNo` (`HwNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `hardware` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
