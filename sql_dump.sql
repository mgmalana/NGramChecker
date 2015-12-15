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
) ENGINE=InnoDB AUTO_INCREMENT=5334 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=679 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=3134 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=1418 DEFAULT CHARSET=utf8;
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
  `bigramID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10666 DEFAULT CHARSET=utf8;
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
  `fivegramID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15663 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=1021 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tokens`
--

DROP TABLE IF EXISTS `tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `surfaceWord` varchar(100) NOT NULL,
  `lemma` varchar(100) DEFAULT NULL,
  `pos` varchar(100) DEFAULT NULL,
  `sentenceNumber` int(11) NOT NULL,
  `wordNumber` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7066 DEFAULT CHARSET=utf8;
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

-- Dump completed on 2015-12-15 11:10:22
