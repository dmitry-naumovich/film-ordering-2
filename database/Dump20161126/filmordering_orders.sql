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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `o_num` int(11) NOT NULL AUTO_INCREMENT,
  `o_user` int(11) NOT NULL,
  `o_film` int(11) NOT NULL,
  `o_date` date NOT NULL,
  `o_time` time NOT NULL,
  `o_fprice` float NOT NULL,
  `o_discount` int(11) NOT NULL,
  `o_paym` float NOT NULL COMMENT 'SELECT `Films`.`f_price` FROM `Films` join `Orders` ON `Films`.`f_id` = `Orders`.`o_film`;',
  PRIMARY KEY (`o_num`),
  KEY `o_user_idx` (`o_user`),
  KEY `o_film_idx` (`o_film`),
  KEY `idx_odate` (`o_date`),
  CONSTRAINT `o_film` FOREIGN KEY (`o_film`) REFERENCES `films` (`f_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `o_user` FOREIGN KEY (`o_user`) REFERENCES `users` (`u_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,1,'2016-06-15','09:31:57',10,10,9),(2,1,2,'2016-06-15','16:05:35',10,10,9),(3,1,10,'2016-06-15','22:23:18',8,10,7.2),(4,1,21,'2016-06-15','19:54:36',8,10,7.2),(5,2,1,'2016-06-15','23:19:11',10,0,10),(6,2,4,'2016-06-15','16:35:52',6,0,6),(7,2,6,'2016-06-15','08:57:32',6,0,6),(8,2,24,'2016-06-15','15:42:27',8,0,8),(9,3,2,'2016-06-16','14:05:04',10,10,9),(10,3,5,'2016-06-16','01:09:59',5,10,4.5),(11,3,9,'2016-06-17','11:57:08',14,10,12.6),(12,3,12,'2016-06-21','15:57:11',12,0,12),(13,3,17,'2016-06-22','20:19:03',14,0,14),(14,4,2,'2016-06-22','19:38:20',10,0,10),(15,4,22,'2016-06-24','05:27:43',8,0,8),(16,8,20,'2016-06-24','17:04:59',10,0,10),(17,7,12,'2016-06-28','00:40:02',12,0,12),(18,7,13,'2016-06-28','14:46:03',8,0,8),(19,7,14,'2016-06-28','01:08:32',8,0,8),(20,8,21,'2016-06-29','10:51:43',8,0,8),(21,8,23,'2016-07-01','21:11:09',8,0,8),(22,10,26,'2016-07-01','18:41:47',10,0,10),(23,11,19,'2016-07-01','09:15:38',12,0,12),(24,12,12,'2016-07-02','18:34:01',12,0,12),(25,13,1,'2016-07-04','08:24:24',8,0,8),(26,13,3,'2016-07-04','18:43:06',10,0,10),(27,15,15,'2016-07-06','19:43:28',8,0,8),(28,15,14,'2016-07-07','09:45:13',8,0,8),(29,15,7,'2016-07-05','12:02:54',8,0,8),(31,21,15,'2016-07-08','13:17:59',8,0,8),(33,25,13,'2016-07-09','12:40:23',8,0,8),(34,4,11,'2016-07-12','12:50:10',10,0,10),(35,9,9,'2016-07-14','18:28:53',14,0,14),(36,22,8,'2016-07-14','17:15:02',12,0,12),(37,23,14,'2016-07-14','00:09:46',8,0,8),(38,24,17,'2016-07-14','23:20:54',14,0,14),(39,6,18,'2016-07-14','16:37:24',12,0,12),(40,14,5,'2016-07-15','18:25:28',5,0,5),(41,15,4,'2016-07-17','06:40:17',6,15,5.1),(42,41,1,'2016-07-17','14:22:14',10,0,10),(43,41,6,'2016-07-17','16:10:31',6,0,6),(44,41,8,'2016-07-18','11:12:02',6,0,6),(45,41,27,'2016-07-19','17:45:48',8,0,8),(46,6,25,'2016-09-11','11:52:13',7,0,7),(47,1,22,'2016-09-14','13:54:38',8,0,8),(48,1,12,'2016-09-14','13:55:11',12,0,12),(49,1,7,'2016-09-14','13:55:27',8,0,8),(50,1,33,'2016-09-14','13:56:40',11,0,11),(51,1,27,'2016-09-14','14:02:34',8,0,8),(52,52,33,'2016-09-14','14:09:48',11,0,11),(53,52,5,'2016-09-14','14:10:33',5,0,5),(54,52,13,'2016-09-14','14:10:45',8,0,8),(55,52,7,'2016-09-14','14:10:53',8,0,8),(56,52,27,'2016-09-14','14:21:20',8,0,8),(57,52,23,'2016-09-14','14:27:58',8,0,8),(58,52,21,'2016-09-14','14:30:50',8,0,8),(59,1,24,'2016-09-15','20:28:21',8,12,7.04),(60,1,26,'2016-09-18','11:05:00',9,12,7.92),(61,54,25,'2016-09-18','19:08:20',7,0,7),(62,54,27,'2016-09-18','19:08:30',8,0,8),(63,54,24,'2016-09-18','19:10:01',8,0,8),(64,54,23,'2016-09-18','19:10:09',8,0,8),(69,1,17,'2016-09-28','11:29:28',14,12,12.32),(70,4,27,'2016-10-05','10:00:22',8,20,6.4);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-26 12:09:43
