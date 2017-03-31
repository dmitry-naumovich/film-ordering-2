CREATE DATABASE  IF NOT EXISTS `filmordering` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `filmordering`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win32 (AMD64)
--
-- Host: localhost    Database: filmordering
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bans`
--

DROP TABLE IF EXISTS `bans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bans` (
  `b_user` int(11) NOT NULL,
  `b_stdate` date NOT NULL,
  `b_sttime` time NOT NULL,
  `b_length` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'Restriction to be positive and at least 1',
  `b_reason` varchar(500) NOT NULL,
  `b_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`b_user`,`b_stdate`,`b_sttime`),
  CONSTRAINT `b_user` FOREIGN KEY (`b_user`) REFERENCES `users` (`u_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bans`
--

LOCK TABLES `bans` WRITE;
/*!40000 ALTER TABLE `bans` DISABLE KEYS */;
INSERT INTO `bans` VALUES (1,'2016-10-05','10:08:36',5,'Test ban reason',0),(1,'2016-10-05','10:09:39',5,'Test ban reason',0),(1,'2016-10-05','10:18:48',5,'Test ban reason',0),(2,'2016-07-01','12:55:44',360,'Frightening other users',0),(2,'2016-10-05','10:08:33',5,'Test ban reason',0),(2,'2016-10-05','10:09:36',5,'Test ban reason',0),(2,'2016-10-05','10:18:45',5,'Test ban reason',0),(3,'2016-10-05','10:08:35',5,'Test ban reason',0),(3,'2016-10-05','10:09:38',5,'Test ban reason',0),(3,'2016-10-05','10:18:47',5,'Test ban reason',0),(4,'2016-08-27','12:00:00',2,'Spam hint',0),(5,'2016-10-05','10:08:35',5,'Test ban reason',0),(5,'2016-10-05','10:09:38',5,'Test ban reason',0),(5,'2016-10-05','10:18:47',5,'Test ban reason',0),(11,'2016-06-15','13:51:45',2,'Inappropriate behaviour',1),(12,'2016-07-02','21:33:00',30,'Spam',1),(13,'2016-09-15','12:09:25',5,'Spam and provocation',1),(14,'2016-07-17','18:11:46',10,'Spam',1),(22,'2016-08-26','13:05:33',4,'Bot-like actioning',1),(23,'2016-06-15','06:16:47',4,'Inappropriate behaviour',1),(44,'2016-09-17','00:17:48',2,'Spam',1),(45,'2016-09-15','12:45:58',10,'Bad boy back again',0),(47,'2016-09-15','12:12:05',2,'Very suspicious activity',1),(52,'2016-09-15','12:36:37',2,'Spam',0),(52,'2016-09-15','12:56:47',8,'Insults and provocation',0),(52,'2016-09-15','12:57:24',4,'Suspicious activity',0),(60,'2016-09-20','07:50:20',12,'Offensive behaviour',1),(60,'2016-10-05','09:56:36',100,'Offensive behaviour',1);
/*!40000 ALTER TABLE `bans` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `filmordering`.`Bans_BEFORE_INSERT` BEFORE INSERT ON `Bans` FOR EACH ROW
BEGIN
IF (NEW.`b_length` < 1) THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Ban length must be equal or greater than 1';
END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `filmordering`.`Bans_BEFORE_UPDATE` BEFORE UPDATE ON `Bans` FOR EACH ROW
BEGIN
IF (NEW.`b_length` < 1) THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Ban length must be equal or greater than 1';
END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-26 12:09:41
