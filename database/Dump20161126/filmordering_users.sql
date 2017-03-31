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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_login` varchar(30) NOT NULL,
  `u_name` varchar(45) NOT NULL,
  `u_surname` varchar(45) NOT NULL,
  `u_passw` varchar(40) NOT NULL,
  `u_sex` enum('f','m','u') NOT NULL DEFAULT 'u',
  `u_type` enum('c','a') NOT NULL,
  `u_regdate` date DEFAULT NULL,
  `u_regtime` time DEFAULT NULL,
  `u_bdate` date DEFAULT NULL,
  `u_phone` char(9) DEFAULT NULL,
  `u_email` varchar(45) NOT NULL,
  `u_about` varchar(600) DEFAULT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `idx_ulogin` (`u_login`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'socar','Жоржик','Милославский','hash1','m','c','2016-06-14','19:19:51','1991-08-08','298843358','socar@gmail.com','Я парень не промах, пойду помогать жене делать рагу!'),(2,'isolated','Иван','Бунин','hash2','m','c','2016-06-14','20:12:21','1994-12-19','445667712','isolated.d@gmail.com','Стихи - моя жизнь, я романтик, сделано все, как фантик'),(3,'subzan','Сергей','Катерский','hash3','m','c','2016-06-14','17:07:32','1995-12-19','296010574','subroman@mail.com','Кит-кат'),(4,'reec','Александр','Головный','hash4','m','c','2016-06-14','03:25:50','1990-01-29','293350304','r@mail.ru','My name is Alex and I am red, I\'ve got some snuff and got a hat'),(5,'logicnologic','Дмитрий','Наумович','hash5','m','a','2016-06-14','00:49:33','1994-11-28','447286098','dmitry.nmv@gmail.com','Я обычный студент из маленькой деревни близ Нижней Тунгуски'),(6,'madamone','Madam','Binister','hash6','f','c','2016-06-14','04:07:09','1980-08-08',NULL,'ganna-ger@gmail.com',NULL),(7,'so33iso','Леша','Сигурский','hash17','m','c','2016-06-14','16:01:53','1975-09-01','338809980','sopushes32@tut.by',NULL),(8,'andre-septic','Алексей','Нижнебродский','hash18','m','c','2016-06-14','19:28:08','1981-05-05','291090676','uberblack@gtut.by',NULL),(9,'onocolis','Екатерина','Петренко','hash7','f','c','2016-06-14','15:47:36',NULL,'443545091','sasha-sups@mail.com',NULL),(10,'verinto-osolo-molea','Вероника','Сикляева','hash8','f','c','2016-06-14','15:37:07','1999-04-01',NULL,'v3sipor@gmail.com',NULL),(11,'hahes','Броль','Креонов','hash9','m','c','2016-06-14','22:35:36',NULL,'299919495','hahes-g-h@ukr.net',NULL),(12,'lola-iam','Ольга','Вустич','hash10','f','c','2016-06-14','14:33:20',NULL,NULL,'lola-iam@mail.ru',NULL),(13,'b_index','Федор','Индектис','hash11','m','c','2016-06-14','18:06:51','2001-04-02',NULL,'indegora@gmail.com',NULL),(14,'saphire','Светлана','Ниокоротаева','hash12','f','c','2016-06-14','12:54:50','2002-03-10','172020204','saphire@maz.by',NULL),(15,'rubinus','Геннадий','Подворотнев','hash13','m','c','2016-06-14','17:11:26','1994-03-31','172345698','rubic.ivan@tut.by',NULL),(21,'diamond.o','Сергей','Алвласов','hash21','m','c','2016-06-14','11:23:39','1992-02-02','336047889','velio_rocco@epam.com',NULL),(22,'interestno','Иннокентий','Смоктуновский','hash19','m','c','2016-06-14','16:31:46',NULL,'447170885','peter.ponson@epam.com','Интересно знать'),(23,'anferna','Юлия','Тимопетрич','hash14','f','c','2016-06-14','05:23:28','1956-05-08',NULL,'anfeius@bsu.by',NULL),(24,'lucke.skywalker','Люк','Скайуокер','hash20','m','c','2016-06-14','07:47:32','1960-05-04',NULL,'lucke.skywalker@starwars.com','Я узнал, что у меня есть хорошая семья. Папа дарт, сестра принцесса, друг Хан Соло, интересно.'),(25,'fiona_sh','Фиона','Шоу','hash20','f','c','2016-06-15','03:16:39','1979-07-29',NULL,'fiona.show@inbox.com','Обо мне много чего говорят, я актриса и люблю свое дело'),(26,'calona_ovec','Колонна','Овец','hash171','m','c','2016-06-15','18:57:58','1981-09-09',NULL,'ovcy-ovcy@starbux.eu',NULL),(27,'semantics.vida','Виктория','Чмыренко','hash172','f','c','2016-06-15','07:52:20',NULL,'298171103','sema.str@yahoo.com',NULL),(41,'coreapi','Виктор','Викторович','hash164','m','c','2016-06-16','04:47:59','1999-12-07','447226654','coreapijaja@yahoo.com',NULL),(42,'suchi','Елена','Долгобродская','hash21','f','c','2016-06-16','05:28:21','2001-10-08','296521615','suchijapa@mail.ru','Лорем ипсум'),(43,'fiestaford','Лейтенант','Кижеватов','hash24','m','c','2016-06-16','19:46:06',NULL,NULL,'ford.fiesta@ford.com',NULL),(44,'lanosdae','Владимир','Московский','hash25','m','c','2016-06-16','11:21:46','1990-11-05','297170859','lanosdaega@bsu.by',NULL),(45,'jeepcola','Сергей','Моисеенко','hash26','m','c','2016-06-16','03:46:22','1987-11-18',NULL,'jeepcola@epam.com','Джипкола'),(46,'toyhighlan','Андрей','Архечин','hash23','m','c','2016-06-16','13:20:33','1964-01-03','336150801','highlander@toyta.com',NULL),(47,'rangeland','Рэнж','Ровер','hash33','m','c','2016-06-17','01:25:38','2002-03-05','291858686','roverhover@inbox.com',NULL),(48,'zoolander','Дерек','Зулендер','hash30','f','c','2016-06-17','00:01:32','1995-01-12','296848451','shpaker@gmail.com','Я не хочу рассказывать о себе'),(49,'quandropica','Oleg','Krasnov','12345','m','c','2016-09-02','10:54:22','1996-06-03','445553311','ququ@gmail.com','i am mm'),(50,'sdsfsdf','dsdf','sdfsdf','12345','m','c','2016-09-02','17:13:02','2004-05-05','447071717','sssdfsd@mail.com','dfsdf'),(51,'vicalinko','Анбен','Тогуард','123456','u','c','2016-09-02','17:17:25','1990-08-07',NULL,'vicaa@mail.com',NULL),(52,'ponnipocko','Жорик','Семенович','12300','m','c','2016-09-02','17:27:55','1990-08-01','297070711','gorik.semenovich@gmail.com',NULL),(53,'aerta','Oleg','Visslich','qwerty','m','c','2016-09-18','18:42:55',NULL,NULL,'qwerty109@mail.com',NULL),(54,'pholophos','Dmitry','Igbert','qwerty','m','c','2016-09-18','19:01:59',NULL,NULL,'qsa99@mail.com',NULL),(55,'socarala','Bob','Billyvie','1234','m','c','2016-09-20','07:20:15',NULL,NULL,'booby@gmail.com',NULL),(56,'chariotte','Clara','Novachek','1234o','f','c','2016-09-20','07:23:43','2000-08-08',NULL,'clara.novachek@mail.ru','I am lady and I know what do I need'),(57,'master','Oleg','Krasnov','qazwsx','m','c','2016-09-20','07:25:47',NULL,'447079891','krasniy@gmail.com',NULL),(58,'lobbybo','Sanec','Kiraten','hash58','u','c','2016-09-20','07:28:52',NULL,NULL,'zara@mail.com',NULL),(59,'enumeration','Enum','Eration','java59','m','c','2016-09-20','07:33:18',NULL,'447010859','enumeration@epam.com',NULL),(60,'dmitryle','Dmitry','Legible','hash60','m','c','2016-09-20','07:43:17','1993-12-31','291010203','dmitry.le@ex.ua',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `filmordering`.`Users_BEFORE_INSERT` BEFORE INSERT ON `Users` FOR EACH ROW
BEGIN
IF (NEW.`u_email` REGEXP '^[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$') = 0 THEN 
  SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'Wrong email format!';
END IF;
IF (NEW.`u_phone` REGEXP '^[0-9]{9}$') = 0 THEN 
  SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'Wrong phone number format! Input 9 digits!';
END IF;
IF (Year(NEW.`u_regdate`) < Year(NEW.`u_bdate`)) THEN
	SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Registration date can be less than birth date!';
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `filmordering`.`Users_BEFORE_UPDATE` BEFORE UPDATE ON `Users` FOR EACH ROW
BEGIN
IF (NEW.`u_email` REGEXP '^[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$') = 0 THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Wrong email format!';
END IF;
IF (NEW.`u_phone` REGEXP '^[0-9]{9}$') = 0 THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Wrong phone number format! Input 9 digits!';
END IF;
IF (Year(NEW.`u_regdate`) < Year(NEW.`u_bdate`)) THEN
	SIGNAL SQLSTATE '12345'
		SET MESSAGE_TEXT = 'Registration date can be less than birth date!';
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

-- Dump completed on 2016-11-26 12:09:40
