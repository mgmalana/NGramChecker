CREATE DATABASE  IF NOT EXISTS `ngramchecker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ngramchecker`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: ngramchecker
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `ngrams`
--

DROP TABLE IF EXISTS `ngrams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ngrams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `surfaceWord` varchar(100) NOT NULL,
  `lemma` varchar(100) DEFAULT NULL,
  `pos` varchar(100) DEFAULT NULL,
  `sentenceNumber` int(11) NOT NULL,
  `wordNumber` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ngrams`
--

LOCK TABLES `ngrams` WRITE;
/*!40000 ALTER TABLE `ngrams` DISABLE KEYS */;
INSERT INTO `ngrams` VALUES (29,'<_>','<_>','<_>',8,0),(30,'Kumain','kain','VB_TS_AF',8,1),(31,'si','si','DTP',8,2),(32,'Mark','Mark','NNP',8,3),(33,'ng','ng','CCB',8,4),(34,'mansanas','mansanas','NNC',8,5),(35,'.','.','.',8,6),(36,'Sumayaw','sayaw','VB_TS_AF',9,1),(37,'ang','ang','DTC',9,2),(38,'babae','babae','NNC',9,3),(39,'cha-cha','cha-cha','NNC',9,5);
/*!40000 ALTER TABLE `ngrams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sentence`
--

DROP TABLE IF EXISTS `sentence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sentence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sentence` varchar(500) NOT NULL,
  `lemmas` varchar(500) NOT NULL,
  `posTags` varchar(500) NOT NULL,
  `sentenceWordLength` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sentence`
--

LOCK TABLES `sentence` WRITE;
/*!40000 ALTER TABLE `sentence` DISABLE KEYS */;
INSERT INTO `sentence` VALUES (8,'<_> Kumain si Mark ng mansanas .','<_> kain si Mark ng mansanas .','<_> VB_TS_AF DTP NNP CCB NNC .',7),(9,'<_> Sumayaw ang babae ng cha-cha .','<_> sayaw ang babae ng cha-cha .','<_> VB_TS_AF DTC NNC CCB NNC .',7);
/*!40000 ALTER TABLE `sentence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ngramchecker'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-07  9:50:22
