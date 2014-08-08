-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.49-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema smartnew
--

CREATE DATABASE IF NOT EXISTS smartnew;
USE smartnew;

--
-- Definition of table `configuration`
--

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

--
-- Dumping data for table `configuration`
--

/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;


--
-- Definition of table `customlist`
--

DROP TABLE IF EXISTS `customlist`;
CREATE TABLE `customlist` (
  `CustomListNo` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `Code` smallint(5) unsigned DEFAULT NULL,
  `Description` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`CustomListNo`),
  KEY `CustomListNo` (`CustomListNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customlist`
--

/*!40000 ALTER TABLE `customlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `customlist` ENABLE KEYS */;


--
-- Definition of table `datalog`
--

DROP TABLE IF EXISTS `datalog`;
CREATE TABLE `datalog` (
  `LogTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ConfigNo` smallint(6) unsigned NOT NULL DEFAULT '0',
  `LogData` float NOT NULL DEFAULT '0',
  KEY `ndxConfigTime` (`ConfigNo`,`LogTime`) USING BTREE,
  KEY `ConfigNo` (`ConfigNo`),
  CONSTRAINT `datalog_ibfk_1` FOREIGN KEY (`ConfigNo`) REFERENCES `configuration` (`ConfigNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `datalog`
--

/*!40000 ALTER TABLE `datalog` DISABLE KEYS */;
/*!40000 ALTER TABLE `datalog` ENABLE KEYS */;


--
-- Definition of table `eventlog`
--

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

--
-- Dumping data for table `eventlog`
--

/*!40000 ALTER TABLE `eventlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventlog` ENABLE KEYS */;


--
-- Definition of table `hardware`
--

DROP TABLE IF EXISTS `hardware`;
CREATE TABLE `hardware` (
  `HwNo` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `Machine` varchar(50) NOT NULL,
  PRIMARY KEY (`HwNo`),
  KEY `HwNo` (`HwNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hardware`
--

/*!40000 ALTER TABLE `hardware` DISABLE KEYS */;
/*!40000 ALTER TABLE `hardware` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
