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
-- Table structure for table `discounts`
--

DROP TABLE IF EXISTS `discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discounts` (
  `d_id` int(11) NOT NULL AUTO_INCREMENT,
  `d_user` int(11) NOT NULL,
  `d_amount` smallint(6) NOT NULL COMMENT 'Should be restricted to the range like 0 -100%',
  `d_stdate` date NOT NULL,
  `d_sttime` time NOT NULL,
  `d_endate` date NOT NULL,
  `d_entime` time NOT NULL,
  PRIMARY KEY (`d_id`),
  KEY `d_user_idx` (`d_user`),
  CONSTRAINT `d_user` FOREIGN KEY (`d_user`) REFERENCES `users` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounts`
--

LOCK TABLES `discounts` WRITE;
/*!40000 ALTER TABLE `discounts` DISABLE KEYS */;
INSERT INTO `discounts` VALUES (1,1,10,'2016-06-15','08:33:23','2016-06-20','20:00:00'),(2,3,10,'2016-06-15','15:59:35','2016-06-20','06:00:00'),(3,15,15,'2016-07-17','06:17:45','2016-08-17','17:00:00'),(4,21,8,'2016-08-01','06:27:02','2016-09-01','22:00:00'),(5,6,5,'2016-08-27','12:05:04','2016-08-30','14:00:00'),(6,11,8,'2016-08-27','13:05:55','2016-09-05','23:00:00'),(7,22,10,'2016-08-25','12:00:34','2016-08-27','12:30:00'),(8,1,12,'2016-09-01','18:04:33','2016-10-01','03:00:00'),(10,50,11,'2016-09-16','23:53:27','2016-10-01','08:00:00'),(11,52,4,'2016-09-17','00:22:28','2016-11-01','00:00:00'),(12,57,12,'2016-09-20','07:50:56','2016-09-29','15:00:00'),(17,4,20,'2016-09-28','11:32:23','2016-11-01','15:00:00');
/*!40000 ALTER TABLE `discounts` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `filmordering`.`Discounts_BEFORE_INSERT` BEFORE INSERT ON `Discounts` FOR EACH ROW
BEGIN
IF (NEW.`d_amount` < 0 or New.`d_amount` > 100) THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Discount amount value must be in 0-100 range!';
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `filmordering`.`Discounts_BEFORE_UPDATE` BEFORE UPDATE ON `Discounts` FOR EACH ROW
BEGIN
IF (NEW.`d_amount` < 0 or New.`d_amount` > 100) THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Discount amount value must be in 0-100 range!';
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
