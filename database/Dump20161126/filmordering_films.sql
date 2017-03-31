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
-- Table structure for table `films`
--

DROP TABLE IF EXISTS `films`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `films` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_name` varchar(30) NOT NULL,
  `f_year` int(4) NOT NULL,
  `f_direct` varchar(40) NOT NULL,
  `f_country` set('USA','UK','Argentina','Australia','Austria','Belarus','Belgium','Brazil','Bulgaria','Canada','Chile','China','Colombia','Croatia','Cuba','Czech Republic','Egypt','Finland','France','Georgia','Germany','Greece','Hungary','Iceland','India','Indonesia','Iran','Iraq','Ireland','Israel','Italy','Japan','South Korea','Latvia','Lebanon','Lithuania','Madagascar','Mexico','Moldova','Monaco','Netherlands','New Zealand','Norway','Peru','Philippines','Poland','Portugal','Romania','Rome','Russia','Serbia','Singapore','Slovakia','Slovenia','South Africa','Spain','Sweden','Switzerland','Syria','Turkey','Ukraine','United Kingdom','United States of America','Vietnam') DEFAULT NULL,
  `f_genre` set('Action','Adventure','Animation','Biography','Comedy','Crime','Detective','Documentary','Drama','Family','Fantasy','Film-Noir','History','Horror','Music','Musical','Mystery','Romance','Sci-Fi','Sport','Thriller','War','Western') DEFAULT NULL,
  `f_actors` varchar(120) DEFAULT NULL,
  `f_composer` varchar(75) DEFAULT NULL,
  `f_description` varchar(2000) DEFAULT NULL,
  `f_length` int(11) NOT NULL,
  `f_rating` float(5,2) DEFAULT NULL,
  `f_price` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`f_id`),
  KEY `idx_fdirect` (`f_direct`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `films`
--

LOCK TABLES `films` WRITE;
/*!40000 ALTER TABLE `films` DISABLE KEYS */;
INSERT INTO `films` VALUES (1,'The Prestige',2006,'Christopher Nolan','USA','Drama,Mystery,Sci-Fi','Christian Bale, Hugh Jackman, Scarlett Johannson, Rebecca Hall, David Bowie','David Juylan','Two stage magicians engage in competitive one-upmanship in an attempt to create the ultimate stage illusion.',125,4.80,10),(2,'Scoop',2006,'Woody Allen','USA,UK','Comedy,Crime,Mystery','Hugh Jackman, Scarlett Johansson',NULL,'An American journalism student in London scoops a big story, and begins an affair with an aristocrat as the incident unfurls.',96,1.50,10),(3,'The Number 23',2007,'Joel Schumacher','USA,Germany','Mystery,Thriller','Jim Carrey','Gergory Gregson-Williams','Walter Sparrow becomes obsessed with a novel that he believes was written about him. As his obsession increases, more and more similarities seem to arise.',101,4.00,10),(4,'On the Waterfront',1954,'Elia Kazan','USA','Crime,Drama,Romance','Marlon Brando, Karl Malden, Lee J. Cobb','Leonard Bernstein','An ex-prize fighter turned longshoreman struggles to stand up to his corrupt union bosses.',108,NULL,6),(5,'The Shop Around the Corner',1940,'Ernst Lubitsch','USA','Comedy,Drama,Romance','Margaret Sullavan, James Stewart, etc.','Werner R. Heymann','Two employees at a gift shop can barely stand one another, without realizing that they\'re falling in love through the post as each other\'s anonymous pen pal.',99,NULL,5),(6,'Scarecrow',1973,'Jerry Schatzberg','USA','Drama','Al Pacino, Gene Hackman','Fred Myrow','Max, an ex-con drifter with a penchant for brawling is amused by Lion, a homeless ex-sailor, and they partner up as they head east together.',112,3.50,6),(7,'Capote',2005,'Bennet Miller','USA,Canada','Biography,Crime,Drama',' Philip Seymour Hoffman, Clifton Collins Jr., Catherine Keener','Michael Denna','In 1959, Truman Capote learns of the murder of a Kansas family and decides to write a book about the case. While researching for his novel In Cold Blood, Capote forms a relationship with one of the killers, Perry Smith, who is on death row.',114,3.00,8),(8,'Into the Wild',2007,'Sean Penn','USA','Adventure,Biography,Drama',' Emile Hirsch, Vince Vaughn, Catherine Keener','Michael Brook','After graduating from Emory University, top student and athlete Christopher McCandless abandons his possessions, gives his entire $24,000 savings account to charity and hitchhikes to Alaska to live in the wilderness. Along the way, Christopher encounters a series of characters that shape his life.',148,3.00,12),(9,'Lucy',2014,'Luc Besson','USA,France','Action,Sci-Fi,Thriller',' Scarlett Johansson, Morgan Freeman, Min-sik Choi','Eric Serra','A woman, accidentally caught in a dark deal, turns the tables on her captors and transforms into a merciless warrior evolved beyond human logic.',89,2.00,14),(10,'Apollo 13',1995,'Ron Howard','USA','Adventure,Drama,History','Tom Hanks, Bill Paxton, Kevin Bacon',NULL,'NASA must devise a strategy to return Apollo 13 to Earth safely after the spacecraft undergoes massive internal damage putting the lives of the three astronauts on board in jeopardy.',140,4.00,8),(11,'I Am Legend',2007,'Francis Lawrence','USA','Drama,Horror,Sci-Fi','Will Smith, Alice Braga, Charlie Tahan',NULL,'Years after a plague kills most of humanity and transforms the rest into monsters, the sole survivor in New York City struggles valiantly to find a cure.',101,3.00,10),(12,'Intouchables',2011,'Olivier Nakache, Eric Toledano','France','Biography,Comedy,Drama','François Cluzet, Omar Sy, Anne Le Ny','Ludovico Einaudi','After he becomes a quadriplegic from a paragliding accident, an aristocrat hires a young man from the projects to be his caregiver.',112,4.67,12),(13,'American History X',1998,'Tony Kaye','USA','Crime,Drama','Edward Norton, Edward Furlong, Beverly D\'Angelo','Anne Dudley','A former neo-nazi skinhead tries to prevent his younger brother from going down the same wrong path that he did.',119,3.00,8),(14,'Silence of the Lambs',1991,'Jonathan Demme','USA','Crime,Drama,Horror','Jodie Foster, Anthony Hopkins, Lawrence A. Bonney','Howard Shore	','A young F.B.I. cadet must confide in an incarcerated and manipulative killer to receive his help on catching another serial killer who skins his victims.',118,3.67,8),(15,'Pulp Fiction',1994,'Quentin Tarantino','USA','Crime,Drama','John Travolta, Uma Thurman, Samuel L. Jackson',NULL,'The lives of two mob hit men, a boxer, a gangster\'s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.',154,4.00,8),(16,'Inglourious Basterds',2009,'Quentin Tarantino, Eli Roth','USA,Germany','Action,Adventure,War','Brad Pitt, Christoph Waltz, Diane Kruger, Eli Roth ',NULL,'In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner\'s vengeful plans for the same.',153,3.00,12),(17,'The Wolf of Wall Street',2013,'Martin Scorsese','USA','Biography,Comedy,Crime','Leonardo DiCaprio, Jonah Hill, Margot Robbie',NULL,'Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.',180,2.50,14),(18,'Inception',2010,'Christopher Nolan','USA,UK','Action,Adventure,Crime','Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page, Tom Hardy, Ken Watanabe, Cillian Murfy','Hans Zimmer','A thief, who steals corporate secrets through use of dream-sharing technology, is given the inverse task of planting an idea into the mind of a CEO.',148,3.67,12),(19,'The Dark Knight',2008,'Christopher Nolan','USA,UK','Action,Adventure,Crime','Christian Bale, Heath Ledger, Morgan Freeman, Aaron Echkart, Michael Cane','Hans Zimmer','When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.',152,3.67,12),(20,'Fight Club',1999,'David Fincher','USA,Germany','Drama','Edward Norton, Brad Pitt, Meat Loaf, Helena Bonham Carter','Dust Brothers','An insomniac office worker, looking for a way to change his life, crosses paths with a devil-may-care soap maker, forming an underground fight club that evolves into something much, much more',139,4.20,10),(21,'12 Angry Men',1957,'Sidney Lumet','USA','Crime,Drama','Henry Fonda, Lee J. Cobb',NULL,'A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.',96,4.67,8),(22,'The Godfather',1972,'Francis Ford Coppola','USA','Crime,Drama','Marlon Brando, Al Pacino, James Caan','Nino Rota','The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.',175,4.83,9),(23,'The Shawshank Redemption',1994,'Frank Darabont','USA','Crime,Drama','Tim Robbins, Morgan Freeman, Bob Gunton','Thomas Newman','Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency',142,4.50,8),(24,'Forrest Gump',1994,'Robert Zemeckis','USA','Drama,Romance','Tom Hanks, Robin Wright, Gary Sinise','Alan Silvestri','Forrest Gump, while not intelligent, has accidentally been present at many historic moments, but his true love, Jenny Curran, eludes him.',142,4.50,8),(25,'Matrix',1999,'Andy Wachowski, Lana Wachowski','USA','Action,Sci-Fi','Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss','Don Davis','A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.',136,3.50,7),(26,'Cloud Atlas',2012,'Andy Wachowski, Lana Wachowski','USA,Germany,Singapore','Drama,Sci-Fi','Tom Hanks, Halle Berry, Hugh Grant',NULL,'An exploration of how the actions of individual lives impact one another in the past, present and future, as one soul is shaped from a killer into a hero, and an act of kindness ripples across centuries to inspire a revolution.',172,3.00,9),(27,'War of the worlds',2005,'Steven Spielberg','USA','Adventure,Sci-Fi,Thriller','Tom Cruise, Dakota Fanning, Tim Robbins','John Williams','As Earth is invaded by alien tripod fighting machines, one family fights for survival.',116,4.50,8),(33,'Up',2009,'Pete Docter, Bob Peterson','USA','Adventure,Animation,Comedy','Edward Asner, Jordan Nagai, John Ratzenberger ','Michael Giacchino	','Seventy-eight year old Carl Fredricksen travels to Paradise Falls in his home equipped with balloons, inadvertently taking a young stowaway.',96,4.50,10.5);
/*!40000 ALTER TABLE `films` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `filmordering`.`Films_BEFORE_INSERT` BEFORE INSERT ON `Films` FOR EACH ROW
BEGIN
IF (NEW.`f_year` < 1895 OR NEW.`f_year` > 2030) THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Wrong film year!';
END IF;
IF (NEW.`f_price` < 0) THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Price must has positive value!';
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `filmordering`.`Films_BEFORE_UPDATE` BEFORE UPDATE ON `Films` FOR EACH ROW
BEGIN
IF (NEW.`f_year` < 1895 OR NEW.`f_year` > 2030) THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Wrong film year!';
END IF;
IF (NEW.`f_price` < 0) THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Price must has positive value!';
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

-- Dump completed on 2016-11-26 12:09:42
