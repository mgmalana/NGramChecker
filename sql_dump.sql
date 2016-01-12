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
-- Table structure for table `bigram`
--

DROP TABLE IF EXISTS `bigram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bigram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `words` varchar(200) NOT NULL,
  `lemmas` varchar(200) NOT NULL,
  `posID` int(11) NOT NULL,
  `isPOSGeneralized` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `posID_idx` (`posID`)
) ENGINE=InnoDB AUTO_INCREMENT=13321 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bigram_pos_frequency`
--

DROP TABLE IF EXISTS `bigram_pos_frequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bigram_pos_frequency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(200) NOT NULL,
  `frequency` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2036 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fivegram`
--

DROP TABLE IF EXISTS `fivegram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fivegram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `words` varchar(200) NOT NULL,
  `lemmas` varchar(200) NOT NULL,
  `posID` int(11) NOT NULL,
  `isPOSGeneralized` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `posID_fivegramidx` (`posID`),
  CONSTRAINT `posID` FOREIGN KEY (`posID`) REFERENCES `fivegram_pos_frequency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6172 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fivegram_pos_frequency`
--

DROP TABLE IF EXISTS `fivegram_pos_frequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fivegram_pos_frequency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(200) NOT NULL,
  `frequency` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4070 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fourgram`
--

DROP TABLE IF EXISTS `fourgram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fourgram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `words` varchar(200) NOT NULL,
  `lemmas` varchar(200) NOT NULL,
  `posID` int(11) NOT NULL,
  `isPOSGeneralized` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4488 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fourgram_pos_frequency`
--

DROP TABLE IF EXISTS `fourgram_pos_frequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fourgram_pos_frequency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(200) NOT NULL,
  `frequency` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3371 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pos_bigram_index`
--

DROP TABLE IF EXISTS `pos_bigram_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pos_bigram_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(45) NOT NULL,
  `ngramID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30631 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pos_fivegram_index`
--

DROP TABLE IF EXISTS `pos_fivegram_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pos_fivegram_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(30) NOT NULL,
  `ngramID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32372 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pos_fourgram_index`
--

DROP TABLE IF EXISTS `pos_fourgram_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pos_fourgram_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(45) NOT NULL,
  `ngramID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22431 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pos_sevengram_index`
--

DROP TABLE IF EXISTS `pos_sevengram_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pos_sevengram_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(20) NOT NULL,
  `ngramID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4177 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pos_sixgram_index`
--

DROP TABLE IF EXISTS `pos_sixgram_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pos_sixgram_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(20) NOT NULL,
  `ngramID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6616 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pos_trigram_index`
--

DROP TABLE IF EXISTS `pos_trigram_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pos_trigram_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(20) NOT NULL,
  `ngramID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12297 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sevengram`
--

DROP TABLE IF EXISTS `sevengram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sevengram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `words` varchar(200) NOT NULL,
  `lemmas` varchar(200) NOT NULL,
  `posID` int(11) NOT NULL,
  `isPOSGeneralized` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=523 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sevengram_pos_frequency`
--

DROP TABLE IF EXISTS `sevengram_pos_frequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sevengram_pos_frequency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(200) NOT NULL,
  `frequency` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=505 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sixgram`
--

DROP TABLE IF EXISTS `sixgram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sixgram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `words` varchar(200) NOT NULL,
  `lemmas` varchar(200) NOT NULL,
  `posID` int(11) NOT NULL,
  `isPOSGeneralized` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=946 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sixgram_pos_frequency`
--

DROP TABLE IF EXISTS `sixgram_pos_frequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sixgram_pos_frequency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(200) NOT NULL,
  `frequency` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=888 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trigram`
--

DROP TABLE IF EXISTS `trigram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trigram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `words` varchar(200) NOT NULL,
  `lemmas` varchar(200) NOT NULL,
  `posID` int(11) NOT NULL,
  `isPOSGeneralized` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3075 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trigram_pos_frequency`
--

DROP TABLE IF EXISTS `trigram_pos_frequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trigram_pos_frequency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(200) NOT NULL,
  `frequency` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1540 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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

-- Dump completed on 2016-01-12 15:31:03
