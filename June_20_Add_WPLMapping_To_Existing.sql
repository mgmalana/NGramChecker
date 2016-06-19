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
) ENGINE=InnoDB AUTO_INCREMENT=44044 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bigram`
--

LOCK TABLES `bigram` WRITE;
/*!40000 ALTER TABLE `bigram` DISABLE KEYS */;
/*!40000 ALTER TABLE `bigram` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=4752 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bigram_pos_frequency`
--

LOCK TABLES `bigram_pos_frequency` WRITE;
/*!40000 ALTER TABLE `bigram_pos_frequency` DISABLE KEYS */;
/*!40000 ALTER TABLE `bigram_pos_frequency` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=31373 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fivegram`
--

LOCK TABLES `fivegram` WRITE;
/*!40000 ALTER TABLE `fivegram` DISABLE KEYS */;
/*!40000 ALTER TABLE `fivegram` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=25577 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fivegram_pos_frequency`
--

LOCK TABLES `fivegram_pos_frequency` WRITE;
/*!40000 ALTER TABLE `fivegram_pos_frequency` DISABLE KEYS */;
/*!40000 ALTER TABLE `fivegram_pos_frequency` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=31429 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fourgram`
--

LOCK TABLES `fourgram` WRITE;
/*!40000 ALTER TABLE `fourgram` DISABLE KEYS */;
/*!40000 ALTER TABLE `fourgram` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=20509 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fourgram_pos_frequency`
--

LOCK TABLES `fourgram_pos_frequency` WRITE;
/*!40000 ALTER TABLE `fourgram_pos_frequency` DISABLE KEYS */;
/*!40000 ALTER TABLE `fourgram_pos_frequency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_ngram_bigram`
--

DROP TABLE IF EXISTS `hybrid_ngram_bigram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_ngram_bigram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `posTags` varchar(100) NOT NULL,
  `isHybrid` varchar(100) NOT NULL,
  `frequency` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hybridngram_UNIQUE` (`posTags`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_ngram_bigram`
--

LOCK TABLES `hybrid_ngram_bigram` WRITE;
/*!40000 ALTER TABLE `hybrid_ngram_bigram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_ngram_bigram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_ngram_fivegram`
--

DROP TABLE IF EXISTS `hybrid_ngram_fivegram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_ngram_fivegram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `posTags` varchar(100) NOT NULL,
  `isHybrid` varchar(100) NOT NULL,
  `frequency` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hybridngram_UNIQUE` (`posTags`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_ngram_fivegram`
--

LOCK TABLES `hybrid_ngram_fivegram` WRITE;
/*!40000 ALTER TABLE `hybrid_ngram_fivegram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_ngram_fivegram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_ngram_fourgram`
--

DROP TABLE IF EXISTS `hybrid_ngram_fourgram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_ngram_fourgram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `posTags` varchar(100) NOT NULL,
  `isHybrid` varchar(100) NOT NULL,
  `frequency` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hybridngram_UNIQUE` (`posTags`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_ngram_fourgram`
--

LOCK TABLES `hybrid_ngram_fourgram` WRITE;
/*!40000 ALTER TABLE `hybrid_ngram_fourgram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_ngram_fourgram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_ngram_sevengram`
--

DROP TABLE IF EXISTS `hybrid_ngram_sevengram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_ngram_sevengram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `posTags` varchar(100) NOT NULL,
  `isHybrid` varchar(100) NOT NULL,
  `frequency` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hybridngram_UNIQUE` (`posTags`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_ngram_sevengram`
--

LOCK TABLES `hybrid_ngram_sevengram` WRITE;
/*!40000 ALTER TABLE `hybrid_ngram_sevengram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_ngram_sevengram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_ngram_sixgram`
--

DROP TABLE IF EXISTS `hybrid_ngram_sixgram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_ngram_sixgram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `posTags` varchar(100) NOT NULL,
  `isHybrid` varchar(100) NOT NULL,
  `frequency` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hybridngram_UNIQUE` (`posTags`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_ngram_sixgram`
--

LOCK TABLES `hybrid_ngram_sixgram` WRITE;
/*!40000 ALTER TABLE `hybrid_ngram_sixgram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_ngram_sixgram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_ngram_trigram`
--

DROP TABLE IF EXISTS `hybrid_ngram_trigram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_ngram_trigram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `posTags` varchar(100) NOT NULL,
  `isHybrid` varchar(100) NOT NULL,
  `frequency` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hybridngram_UNIQUE` (`posTags`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_ngram_trigram`
--

LOCK TABLES `hybrid_ngram_trigram` WRITE;
/*!40000 ALTER TABLE `hybrid_ngram_trigram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_ngram_trigram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_pos_index_bigram`
--

DROP TABLE IF EXISTS `hybrid_pos_index_bigram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_pos_index_bigram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hybrid_id` int(11) NOT NULL,
  `pos_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`pos_id`,`hybrid_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_pos_index_bigram`
--

LOCK TABLES `hybrid_pos_index_bigram` WRITE;
/*!40000 ALTER TABLE `hybrid_pos_index_bigram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_pos_index_bigram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_pos_index_fivegram`
--

DROP TABLE IF EXISTS `hybrid_pos_index_fivegram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_pos_index_fivegram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hybrid_id` int(11) NOT NULL,
  `pos_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`hybrid_id`,`pos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_pos_index_fivegram`
--

LOCK TABLES `hybrid_pos_index_fivegram` WRITE;
/*!40000 ALTER TABLE `hybrid_pos_index_fivegram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_pos_index_fivegram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_pos_index_fourgram`
--

DROP TABLE IF EXISTS `hybrid_pos_index_fourgram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_pos_index_fourgram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hybrid_id` int(11) NOT NULL,
  `pos_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`hybrid_id`,`pos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_pos_index_fourgram`
--

LOCK TABLES `hybrid_pos_index_fourgram` WRITE;
/*!40000 ALTER TABLE `hybrid_pos_index_fourgram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_pos_index_fourgram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_pos_index_sevengram`
--

DROP TABLE IF EXISTS `hybrid_pos_index_sevengram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_pos_index_sevengram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hybrid_id` int(11) NOT NULL,
  `pos_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`hybrid_id`,`pos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_pos_index_sevengram`
--

LOCK TABLES `hybrid_pos_index_sevengram` WRITE;
/*!40000 ALTER TABLE `hybrid_pos_index_sevengram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_pos_index_sevengram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_pos_index_sixgram`
--

DROP TABLE IF EXISTS `hybrid_pos_index_sixgram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_pos_index_sixgram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hybrid_id` int(11) NOT NULL,
  `pos_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`hybrid_id`,`pos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_pos_index_sixgram`
--

LOCK TABLES `hybrid_pos_index_sixgram` WRITE;
/*!40000 ALTER TABLE `hybrid_pos_index_sixgram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_pos_index_sixgram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hybrid_pos_index_trigram`
--

DROP TABLE IF EXISTS `hybrid_pos_index_trigram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hybrid_pos_index_trigram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hybrid_id` int(11) NOT NULL,
  `pos_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`hybrid_id`,`pos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hybrid_pos_index_trigram`
--

LOCK TABLES `hybrid_pos_index_trigram` WRITE;
/*!40000 ALTER TABLE `hybrid_pos_index_trigram` DISABLE KEYS */;
/*!40000 ALTER TABLE `hybrid_pos_index_trigram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pos`
--

DROP TABLE IF EXISTS `pos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pos` (`pos`)
) ENGINE=InnoDB AUTO_INCREMENT=18430 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos`
--

LOCK TABLES `pos` WRITE;
/*!40000 ALTER TABLE `pos` DISABLE KEYS */;
INSERT INTO `pos` VALUES (624,''),(17657,'BK'),(27,'CCA'),(3,'CCB'),(13253,'CCB_CCP'),(40,'CCP'),(1834,'CCR'),(8762,'CCR_CCA'),(18,'CCT'),(6638,'CCT_CCP'),(379,'CCU'),(4,'CDB'),(8800,'DCP'),(7,'DTC'),(165,'DTCP'),(1498,'DTP'),(2195,'DTPP'),(10,'FW'),(750,'JJC'),(12112,'JJCA_JJD_CCP'),(775,'JJCC'),(9239,'JJCC_CCP'),(17299,'JJCC_JJD'),(3972,'JJCS'),(2774,'JJCS_CCP'),(7288,'JJCS_JJC'),(82,'JJCS_JJD'),(1419,'JJCS_JJD_CCP'),(15149,'JJCS_JJS'),(3089,'JJC_CCP'),(17323,'JJC_JJD'),(2,'JJD'),(17780,'JJD_CCA'),(31,'JJD_CCP'),(16804,'JJD_CPP'),(135,'JJN'),(15567,'JJN_CCA'),(46,'JJN_CCP'),(16724,'JJN_NNC'),(11876,'JJN_PMS_NNC_CCP'),(5491,'JJW'),(284,'LM'),(3389,'NC'),(8470,'NC_CCP'),(17,'NNC'),(1809,'NNCA'),(22,'NNC_CCP'),(5,'NNP'),(57,'NNPA'),(5057,'NNPD'),(15552,'NNP_CCA'),(126,'NNP_CCP'),(3351,'NP'),(72,'PMC'),(11,'PMP'),(53,'PMS'),(3709,'PMSC'),(3034,'PRC'),(3170,'PRC_CCP'),(159,'PRI'),(94,'PRI_CCP'),(484,'PRL'),(97,'PRO'),(160,'PRO_CCP'),(699,'PRP'),(642,'PRP_CCP'),(3095,'PRQ_CCP'),(924,'PRS'),(805,'PRSP'),(156,'PRSP_CCP'),(6436,'PRS_CCP'),(2080,'PR_CCP'),(21,'RBB'),(60,'RBD'),(41,'RBD_CCP'),(84,'RBF'),(11241,'RBF_CCP'),(4915,'RBF_JJD_CCP'),(6,'RBI'),(751,'RBI_CCP'),(162,'RBK'),(24,'RBL'),(1406,'RBL_CCP'),(50,'RBL_CCP_NNP'),(187,'RBL_NNP'),(630,'RBL_NNPA'),(510,'RBL_NNP_NNP'),(1605,'RBM'),(10611,'RBN'),(4450,'RBN_CCP'),(734,'RBP'),(163,'RBQ'),(3648,'RBQ_CCB'),(3189,'RBQ_CCP'),(1768,'RBR'),(16561,'RBR_DTP'),(12309,'RBT'),(176,'RBT_CCP'),(5005,'RBT_LM'),(96,'RBW'),(9788,'RBW_CCA'),(3216,'RBW_CCB'),(1,'RBW_CCP'),(16743,'RBW_DTP'),(5058,'TCP'),(92,'VBAF'),(18146,'VBF'),(386,'VBH'),(1682,'VBH_CCP'),(12273,'VBN'),(214,'VBN_CCP'),(37,'VBOF'),(3124,'VBOF_CCP'),(3142,'VBRF'),(7578,'VBRF_CCP'),(2122,'VBS'),(4701,'VBS_CCP'),(9897,'VBT'),(4099,'VBTC'),(552,'VBTF'),(12469,'VBTP'),(62,'VBTR'),(561,'VBTR_CCP'),(8,'VBTS'),(14223,'VBTS_CCA'),(2801,'VBTS_CCP'),(212,'VBW'),(85,'VBW_CCP'),(513,'VB_TR_AF'),(1651,'VB_TR_RF'),(9560,'VB_TS_AF');
/*!40000 ALTER TABLE `pos` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=122800 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_bigram_index`
--

LOCK TABLES `pos_bigram_index` WRITE;
/*!40000 ALTER TABLE `pos_bigram_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_bigram_index` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=183578 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_fivegram_index`
--

LOCK TABLES `pos_fivegram_index` WRITE;
/*!40000 ALTER TABLE `pos_fivegram_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_fivegram_index` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=157136 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_fourgram_index`
--

LOCK TABLES `pos_fourgram_index` WRITE;
/*!40000 ALTER TABLE `pos_fourgram_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_fourgram_index` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=181601 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_sevengram_index`
--

LOCK TABLES `pos_sevengram_index` WRITE;
/*!40000 ALTER TABLE `pos_sevengram_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_sevengram_index` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=171914 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_sixgram_index`
--

LOCK TABLES `pos_sixgram_index` WRITE;
/*!40000 ALTER TABLE `pos_sixgram_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_sixgram_index` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=127449 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_trigram_index`
--

LOCK TABLES `pos_trigram_index` WRITE;
/*!40000 ALTER TABLE `pos_trigram_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_trigram_index` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pos_unigram_index`
--

DROP TABLE IF EXISTS `pos_unigram_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pos_unigram_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(45) NOT NULL,
  `ngramID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133883 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_unigram_index`
--

LOCK TABLES `pos_unigram_index` WRITE;
/*!40000 ALTER TABLE `pos_unigram_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_unigram_index` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=22701 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sevengram`
--

LOCK TABLES `sevengram` WRITE;
/*!40000 ALTER TABLE `sevengram` DISABLE KEYS */;
/*!40000 ALTER TABLE `sevengram` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=22393 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sevengram_pos_frequency`
--

LOCK TABLES `sevengram_pos_frequency` WRITE;
/*!40000 ALTER TABLE `sevengram_pos_frequency` DISABLE KEYS */;
/*!40000 ALTER TABLE `sevengram_pos_frequency` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=24560 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sixgram`
--

LOCK TABLES `sixgram` WRITE;
/*!40000 ALTER TABLE `sixgram` DISABLE KEYS */;
/*!40000 ALTER TABLE `sixgram` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=23427 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sixgram_pos_frequency`
--

LOCK TABLES `sixgram_pos_frequency` WRITE;
/*!40000 ALTER TABLE `sixgram_pos_frequency` DISABLE KEYS */;
/*!40000 ALTER TABLE `sixgram_pos_frequency` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=31863 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trigram`
--

LOCK TABLES `trigram` WRITE;
/*!40000 ALTER TABLE `trigram` DISABLE KEYS */;
/*!40000 ALTER TABLE `trigram` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=11235 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trigram_pos_frequency`
--

LOCK TABLES `trigram_pos_frequency` WRITE;
/*!40000 ALTER TABLE `trigram_pos_frequency` DISABLE KEYS */;
/*!40000 ALTER TABLE `trigram_pos_frequency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unigram`
--

DROP TABLE IF EXISTS `unigram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unigram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `words` varchar(200) NOT NULL,
  `lemmas` varchar(200) NOT NULL,
  `posID` int(11) NOT NULL,
  `isPOSGeneralized` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66944 DEFAULT CHARSET=utf8 COMMENT='			';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unigram`
--

LOCK TABLES `unigram` WRITE;
/*!40000 ALTER TABLE `unigram` DISABLE KEYS */;
/*!40000 ALTER TABLE `unigram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unigram_pos_frequency`
--

DROP TABLE IF EXISTS `unigram_pos_frequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unigram_pos_frequency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos` varchar(200) NOT NULL,
  `frequency` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=428 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unigram_pos_frequency`
--

LOCK TABLES `unigram_pos_frequency` WRITE;
/*!40000 ALTER TABLE `unigram_pos_frequency` DISABLE KEYS */;
/*!40000 ALTER TABLE `unigram_pos_frequency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wordposlemmamap`
--

DROP TABLE IF EXISTS `wordposlemmamap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wordposlemmamap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(45) NOT NULL,
  `posID` int(11) NOT NULL,
  `lemma` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`word`,`posID`,`lemma`)
) ENGINE=InnoDB AUTO_INCREMENT=18430 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wordposlemmamap`
--

LOCK TABLES `wordposlemmamap` WRITE;
/*!40000 ALTER TABLE `wordposlemmamap` DISABLE KEYS */;
INSERT INTO `wordposlemmamap` VALUES (3242,'',53,''),(624,'',624,''),(1997,'\"',53,'\"'),(10540,'$',53,'$'),(7157,'%',53,'%'),(15033,'\'',53,'\''),(56,'(',53,'('),(59,')',53,')'),(4382,')',72,')'),(4337,'),',53,'),'),(1548,'+',53,'+'),(17701,',',2,','),(17715,',',7,','),(17723,',',10,','),(14868,',',11,','),(4893,',',17,','),(17756,',',27,','),(17705,',',40,','),(72,',',72,','),(17736,',',94,','),(436,'-',53,'-'),(14384,'-',72,'-'),(17692,'.',7,'.'),(11,'.',11,'.'),(4343,'.',17,'.'),(16772,'.',72,'.'),(849,'/',53,'/'),(9726,'0',4,'0'),(2435,'1',4,'1'),(12871,'1,000',4,'1,000'),(12638,'1,000,000',4,'1,000,000'),(12766,'1,001',4,'1,001'),(10123,'1,541',4,'1,541'),(11020,'1.3',4,'1.3'),(10950,'1.5',4,'1.5'),(12554,'10,000',4,'10,000'),(8310,'10-11',4,'10-11'),(9473,'100',4,'100'),(12515,'100,000',4,'100,000'),(12788,'100,001',4,'100,001'),(9475,'1000',4,'1000'),(3874,'1025',4,'1025'),(10330,'11.8',4,'11.8'),(437,'12',4,'12'),(2163,'12,000',4,'12,000'),(580,'12,400',4,'12,400'),(58,'129',4,'129'),(118,'13',4,'13'),(989,'134',4,'134'),(1087,'14',4,'14'),(553,'1400',4,'1400'),(1806,'15',4,'15'),(11131,'15.2',4,'15.2'),(2339,'150',4,'150'),(13222,'150,000',4,'150,000'),(12503,'1500',4,'1500'),(16102,'151',4,'151'),(606,'154',4,'154'),(15828,'1545',4,'1545'),(10361,'160',4,'160'),(1482,'170',4,'170'),(12522,'1700',4,'1700'),(4724,'1740s',4,'1740s'),(4676,'1751',4,'1751'),(4834,'1753',4,'1753'),(4848,'1758',4,'1758'),(848,'18',4,'18'),(11970,'180,000',4,'80,000'),(5526,'1800’s',4,'1800’s'),(525,'1812',4,'1812'),(5836,'1830’s',4,'1830’s'),(13367,'1836',4,'1836'),(5845,'1859',4,'1859'),(5684,'1865',4,'1865'),(18173,'1883',4,'1883'),(850,'19',4,'19'),(3905,'190',4,'190'),(12539,'1900',4,'1900'),(5887,'1906',4,'1906'),(5916,'1910',4,'1910'),(5947,'1913',4,'1913'),(3217,'1917',4,'1917'),(6017,'1928',4,'1928'),(32,'1930s',4,'1930s'),(4,'1930’s',4,'1930’s'),(64,'1936',4,'1936'),(79,'1937',4,'1937'),(239,'1939',4,'1939'),(256,'1940',4,'1940'),(340,'1941',4,'1941'),(432,'1942',4,'1942'),(852,'1943',4,'1943'),(1097,'1944',4,'1944'),(604,'1945',4,'1945'),(6081,'1952',4,'1952'),(6129,'1953',4,'1953'),(5376,'1960’s',4,'1960’s'),(11134,'1962',4,'1962'),(6332,'1977',4,'1977'),(15259,'1978',4,'1978'),(17882,'1981',4,'1981'),(6356,'1983',4,'1983'),(10391,'1990s',4,'1990s'),(12685,'1998',4,'1998'),(430,'2',4,'2'),(12860,'2,000',4,'2,000'),(12736,'2,001',4,'2,001'),(13996,'2,500',4,'2,500'),(1808,'20',4,'20'),(2166,'20,000',4,'20,000'),(12610,'200,000',4,'200,000'),(10423,'2000',4,'2000'),(6401,'2001',4,'2001'),(2069,'2002',4,'2002'),(1376,'2004',4,'2004'),(2099,'2005',4,'2005'),(1386,'2006',4,'2006'),(1580,'2007',4,'2007'),(2356,'2008',4,'2008'),(870,'21',4,'21'),(2623,'21st',4,'21st'),(121,'22',4,'22'),(1018,'24',4,'24'),(3237,'24,000',4,'24,000'),(7509,'247',4,'247'),(12892,'250',4,'250'),(1929,'25714',4,'25714'),(15735,'27',4,'27'),(2206,'3',4,'3'),(4105,'3.7',4,'3.7'),(3890,'30',4,'30'),(10961,'30%',4,'30%'),(10793,'30,000',4,'30,000'),(12536,'300',4,'300'),(558,'3000',4,'3000'),(1311,'31',4,'31'),(453,'32',4,'32'),(13666,'326',4,'326'),(1319,'33',4,'33'),(10923,'33,333,216',4,'33,333,216'),(11052,'350,000',4,'350,000'),(115,'36',4,'36'),(1547,'380',4,'380'),(15584,'39',4,'39'),(15754,'393',4,'393'),(446,'4',4,'4'),(10618,'4.3',4,'4.3'),(3884,'4.5',4,'4.5'),(10541,'4.74',4,'4.74'),(15758,'400',4,'400'),(1441,'40D',4,'40D'),(1255,'41',4,'41'),(1259,'42',4,'42'),(577,'430',4,'430'),(550,'44',4,'44'),(11044,'45,000',4,'45,000'),(2154,'450',4,'450'),(15725,'46',4,'46'),(10110,'48',4,'48'),(2299,'5',4,'5'),(12720,'5,000',4,'5,000'),(12983,'50,000',4,'50,000'),(2150,'500',4,'500'),(13234,'500,00',4,'500,00'),(12624,'500,000',4,'500,000'),(12822,'500,001',4,'500,001'),(1224,'51',4,'51'),(723,'532',4,'532'),(10117,'553',4,'553'),(1965,'57',4,'57'),(100,'6',4,'6'),(10301,'60',4,'60'),(1363,'600',4,'600'),(7192,'62',4,'62'),(15830,'63',4,'63'),(16478,'650',4,'650'),(12688,'668',4,'668'),(338,'7',4,'7'),(10790,'7,500,000',4,'7,500,000'),(10574,'7.5',4,'7.5'),(10930,'70',4,'70'),(16497,'716',4,'716'),(14271,'72',4,'72'),(3240,'7300',4,'7300'),(862,'74',4,'74'),(13643,'755,785',4,'755,785'),(10614,'8',4,'8'),(3137,'80',4,'80'),(14011,'800',4,'800'),(3878,'802',4,'802'),(1134,'881',4,'881'),(7156,'89',4,'89'),(748,'89,000',4,'89,000'),(10689,'9.4',4,'9.4'),(1963,'91',4,'91'),(10314,'95',4,'95'),(111,'97',4,'97'),(4354,':',17,':'),(117,':',53,':'),(4995,';',53,';'),(3709,';',3709,';'),(1480,'A',57,'A'),(11192,'Abbes',5,'Abbes'),(8320,'abilidad',17,'abilidad'),(2747,'Abril',5,'Abril'),(1962,'AC',5,'AC'),(9958,'accretion',10,'accretion'),(10791,'acres',17,'acres'),(5309,'Actinobacteria',5,'Actinobacteria'),(15920,'Acts_of_Peter',5,'Acts_of_Peter'),(5342,'Acytota',5,'Acytota'),(15493,'AD',57,'AD'),(5806,'Adam',5,'Adam'),(7226,'adenine',10,'adenine'),(11982,'administrasyon',17,'administrasyon'),(10236,'administrasyong',22,'administrasyon'),(12669,'administratibong',31,'administratibo'),(10351,'Administration',5,'Administration'),(2107,'Advanced',5,'Advanced'),(2013,'Aereon',5,'Aereon'),(1857,'Aerial',5,'Aerial'),(1873,'aerial',10,'aerial'),(2792,'Aerobots',5,'Aerobots'),(2016,'aerodynamic',10,'aerodynamic'),(2844,'aerodynamics',10,'aerodynamics'),(669,'aerophoto',10,'aerophoto'),(1440,'Aeros',5,'Aeros'),(2802,'Aeroscraft',5,'Aeroscraft'),(2394,'Aerospace',5,'Aerospace'),(2014,'aerostatic',10,'aerostatic'),(16967,'aesthetics',17,'aesthetics'),(16314,'Aethelhere',5,'Aethelhere'),(10894,'Africa',5,'Africa'),(10721,'agad',60,'agad'),(5076,'agad',96,'agad'),(6700,'agad-agad',60,'agad'),(2110,'Agency',5,'Agency'),(16838,'agency',10,'agency'),(12160,'Ages',5,'Ages'),(14756,'Aggadic',5,'Aggadic'),(5513,'agham',17,'agham'),(1017,'Agosto',5,'Agosto'),(12029,'agrikultura',17,'agrikultura'),(10696,'agrikultural',17,'agrikultura'),(15139,'ahensyang',22,'ahensya'),(2892,'ahente',17,'ahente'),(3598,'ahenteng',22,'ahente'),(17730,'Ai',2,'Ai'),(17724,'Ai',94,'Ai'),(16823,'Aifor',5,'Aifor'),(1510,'Air',5,'Air'),(10581,'air',10,'air'),(122,'aircrew',10,'aircrew'),(716,'airforce',10,'airforce'),(1032,'Airship',5,'Airship'),(10,'airship',10,'airship'),(2200,'Airships',5,'Airships'),(262,'airships',10,'airships'),(16220,'Aithelwald',5,'Aithelwald'),(3487,'akademikong',31,'akademiko'),(8562,'aksidental',2,'aksidental'),(11251,'aksidental',17,'aksidental'),(86,'aksidente',17,'aksidente'),(2045,'aksis',17,'aksis'),(1072,'aksyon',17,'aksyon'),(11198,'aktibidad',17,'aktibidad'),(13730,'aktibong',31,'aktibo'),(4369,'alakdan',17,'alakdan'),(12234,'alam',212,'alam'),(6228,'alam',2122,'alam'),(14253,'alamat',17,'alamat'),(1128,'Aleman',5,'Aleman'),(76,'Alemanya',5,'Alemanya'),(11817,'Alexandria',5,'Alexandria'),(5955,'Alfred',5,'Alfred'),(11743,'algebraically',10,'algebraically'),(10105,'Algeria',5,'Algeria'),(10963,'Algerians',3,'Algerians'),(10692,'Algerians',5,'Algerians'),(10546,'Algeria’s',5,'Algeria’s'),(11195,'Algiers',5,'Algiers'),(9867,'alikabok',17,'alikabok'),(4196,'alinman',159,'alinman'),(4012,'alinmang',94,'alinman'),(13850,'aliwan',17,'aliw'),(13145,'alkalde',17,'alkalde'),(6529,'allele',10,'allele'),(7649,'allele',17,'allele'),(6500,'alleles',10,'alleles'),(1565,'Allen',5,'Allen'),(15646,'All_Scripture_is_inspired_of_God',10,'All_Scripture_is_inspired_of_God'),(10266,'alpabeto',17,'alpabeto'),(2279,'Altitude',5,'Altitude'),(4372,'alupihan',17,'alupihan'),(1477,'American',5,'American'),(12452,'Amerika',5,'Amerika'),(15252,'Amerikano',5,'Amerikano'),(126,'Amerikanong',126,'Amerikano'),(6295,'aminong',22,'amino'),(4336,'ampibyo',17,'ampibyo'),(6718,'anak',17,'anak'),(4603,'anatomikal',2,'anatomikal'),(4437,'anatomya',17,'anatomya'),(982,'anf',7,'anf'),(17693,'Ang',2,'Ang'),(17878,'Ang',5,'Ang'),(7,'ang',7,'ang'),(17765,'Ang',17,'Ang'),(17261,'ang',92,'ang'),(17716,'ang',94,'ang'),(17267,'ang',212,'ang'),(13657,'Angelus',5,'Angelus'),(17296,'anggulo',17,'anggulo'),(9202,'angkan',17,'angkan'),(8314,'angkop',2,'angkop'),(18313,'angkop',17,'angkop'),(9374,'angkop',60,'angkop'),(3374,'Angkor',5,'Angkor'),(16313,'Anglian',5,'Anglian'),(16638,'Anglo-Saxon',5,'Anglo-Saxon'),(4349,'ang_mga',17,'ang_mga'),(282,'ang_mga',165,'ang_mga'),(5058,'ang_mga',5058,'ang_mga'),(10841,'ani',17,'ani'),(13454,'anibersaryo',17,'anibersaryo'),(5240,'anim',135,'anim'),(5064,'Animalia',5,'Animalia'),(3520,'Ankor',5,'Ankor'),(1314,'Anna',5,'Anna'),(14628,'ano',163,'ano'),(3095,'anong',3095,'ano'),(14709,'anong',3189,'ano'),(14951,'anong',3189,'anong'),(709,'antas',17,'antas'),(413,'anti-submarino',2,'submarino'),(11819,'Antioch',5,'Antioch'),(14326,'anumang',94,'anuman'),(3970,'anyo',17,'anyo'),(7222,'apat',135,'apat'),(12528,'apatnapu',135,'apatnapu'),(5344,'Aphanobionta',5,'Aphanobionta'),(11539,'aplikasyon',17,'aplikasyon'),(2411,'aplikasyong',22,'aplikasyon'),(2649,'aplikasyong',31,'aplikasyon'),(16090,'Apocalypse',5,'Apocalypse'),(15342,'apocryphal',10,'apocryphal'),(15922,'apocryphal',17,'apocryphal'),(15908,'apokripos',17,'apokripos'),(16493,'apong',22,'apo'),(17846,'Aprikano',5,'Aprikano'),(3746,'apsaras',10,'apsaras'),(11004,'Arab',5,'Arab'),(9120,'Arabidopsis',10,'Arabidopsis'),(12109,'Arabo',5,'Arabo'),(8962,'aral',17,'aral'),(5147,'aralin',37,'aral'),(14174,'Arameik',5,'Arameik'),(173,'araw',17,'araw'),(5235,'Archaea',5,'Archaea'),(13052,'aree',10,'aree'),(1066,'Argentia',5,'Argentia'),(17174,'ari-arian',17,'ari'),(14260,'Aristeas',5,'Aristeas'),(4248,'Aristotle',5,'Aristotle'),(12066,'arkeolohikal',2,'arkeolohikal'),(3551,'arkitektong',22,'arkitekto'),(3633,'arkitektura',17,'arkitektura'),(3528,'arkitekturang',22,'arkitektura'),(530,'armado',2,'armado'),(783,'armas',17,'armas'),(10598,'arms',10,'arms'),(1544,'Army',5,'Army'),(10828,'artesian',10,'artesian'),(6896,'artikulong',22,'artikulo'),(17680,'artist',17,'artist'),(17624,'asawa',17,'asawa'),(7810,'asekswal',17,'asekswal'),(6294,'asidong',22,'asido'),(17633,'aspekto',17,'aspekto'),(10168,'Assembly',5,'Assembly'),(10511,'Association',5,'Association'),(1266,'Assu',5,'Assu'),(1338,'Astoria',5,'Astoria'),(9713,'astronomo',17,'astronomo'),(6916,'asul',2,'asul'),(17719,'at',2,'at'),(4340,'at',17,'at'),(27,'at',27,'at'),(17757,'at',94,'at'),(899,'atakeng',22,'atake'),(13571,'atbp.',1809,'atbp.'),(1143,'Atherton',5,'Atherton'),(5145,'atin',805,'atin'),(11394,'ating',156,'atin'),(12396,'Atlantic',5,'Atlantic'),(2777,'atmosperang',22,'atmospera'),(17987,'au',10,'au'),(2687,'Au-12',5,'Au-12'),(2689,'Au-30',5,'Au-30'),(17985,'Äúbeautiful',10,'Äúbeautiful'),(2669,'AUGUR-RosAerosystems',5,'AUGUR-RosAerosystems'),(5646,'Augustnyano',5,'Augustnyano'),(15275,'autographic',10,'autographic'),(1351,'auxiliary',10,'auxiliary'),(18257,'avereyj',17,'avereyj'),(6053,'Avery',5,'Avery'),(15204,'awa',17,'awa'),(12131,'Awdaghust',5,'Awdaghust'),(14033,'awtomatikong',41,'awtomatiko'),(13839,'awtoridad',17,'awtoridad'),(11769,'awtput',17,'awtput'),(3720,'axial',10,'axial'),(1105,'Axis',5,'Axis'),(10964,'ay',5,'ay'),(17697,'ay',37,'ay'),(284,'ay',284,'ay'),(15788,'ay',750,'ay'),(16253,'ayaw',2122,'ayaw'),(9735,'ayon',1768,'ayon'),(3640,'Ayon_kay',1768,'Ayon_kay'),(4202,'Ayon_sa',1768,'Ayon_sa'),(5975,'ayos',17,'ayos'),(15193,'A_General_Introduction_to_the_Bible',5,'A_General_Introduction_to_the_Bible'),(13834,'ba',6,'ba'),(1820,'bababa',552,'baba'),(7733,'babae',17,'babae'),(3164,'babala',17,'babala'),(14404,'babasahing',22,'basa'),(14790,'Babylonian',5,'Babylonian'),(5232,'Bacteria',5,'Bacteria'),(9114,'bacterium',10,'bacterium'),(10303,'badyet',17,'badyet'),(4087,'Bagaman',3,'Baga'),(3605,'bagaman',18,'baga'),(9283,'Bagaman',18,'Bagaman'),(5511,'Bagamat',3,'Baga'),(5717,'Bagamat',18,'Baga'),(8205,'Bagamat',18,'Bagamat'),(6215,'Bagama’t',18,'Baga'),(369,'bagay',2,'bagay'),(15269,'bagay',17,'bagay'),(11999,'Baghdad',5,'Baghdad'),(7121,'bagkus',18,'bagkus'),(15057,'Bago',5,'Bago'),(96,'bago',96,'bago'),(15797,'bagong',2,'bago'),(15393,'Bagong',5,'Bago'),(423,'bagong',31,'bago'),(3409,'bagong',31,'bagong'),(4706,'bagong',41,'bago'),(14085,'Bagong',126,'Bago'),(4799,'baguhin',37,'bagu'),(9069,'bahagi',2,'bahagi'),(1578,'bahagi',17,'bahagi'),(10957,'bahagyang',31,'bahagya'),(5092,'bahagyang',41,'bahagya'),(9150,'bahay',17,'bahay'),(17643,'bahid',17,'bahid'),(7092,'baitang',17,'baitang'),(9952,'bakas',17,'bakas'),(11450,'bakit',163,'bakit'),(7454,'bakterya',17,'bakterya'),(8055,'bakteryum',17,'bakteryum'),(10128,'baladiyahs',10,'baladiyahs'),(14986,'balangkas',17,'balangkas'),(3666,'balanseng',31,'balanse'),(2180,'balasto',17,'balasto'),(7055,'balat',17,'balat'),(143,'balita',17,'balita'),(8613,'baliwalang',31,'baliwala'),(1639,'Balloons',5,'Balloons'),(12371,'Baltic',5,'Baltic'),(4342,'balyena',165,'balyena'),(3434,'banal',2,'banal'),(15137,'Banal',5,'Banal'),(14130,'banda',17,'banda'),(12402,'bandang',31,'banda'),(17171,'bang',751,'ba'),(960,'bangka',17,'bangka'),(6353,'Banks',5,'Banks'),(10871,'bansa',17,'bansa'),(4747,'bansag',17,'bansag'),(10325,'bansang',22,'bansa'),(14661,'bantas',17,'bantas'),(440,'bantay',17,'bantay'),(3835,'Banteay',5,'Banteay'),(2733,'bantog',2,'bantog'),(1056,'Bar',5,'Bar'),(390,'barko',17,'barko'),(10845,'barley',10,'barley'),(7175,'baryante',17,'baryante'),(7138,'baryasyon',17,'baryasyon'),(3749,'Bas-reliefs',5,'Bas-reliefs'),(3397,'bas-reliefs',10,'bas-reliefs'),(15335,'basahin',37,'basa'),(480,'base',10,'base'),(467,'base',17,'base'),(7199,'basehan',17,'base'),(8401,'basehang',22,'base'),(1768,'base_sa',1768,'base_sa'),(9814,'bata',2,'bata'),(17573,'bata',17,'bata'),(6847,'Batas',5,'Batas'),(4933,'batas',17,'batas'),(17405,'Batay',2,'Batay'),(18003,'batay',17,'batay'),(4220,'batay',1768,'batay'),(4605,'batayan',17,'batay'),(4546,'batay_sa',1768,'batay_sa'),(5791,'Bateson',5,'Bateson'),(16206,'battle-shirker',10,'battle-shirker'),(3915,'batubahanging',31,'batubahangin'),(3556,'batubuhangin',17,'batubuhangin'),(3611,'bawahang',31,'bawaha'),(7790,'bawat',2,'bawat'),(2437,'bawat',159,'bawat'),(1204,'Bay',5,'Bay'),(12163,'bayan',17,'bayan'),(12255,'bayan',3389,'bayan'),(10610,'bayaran',37,'bayaran'),(10937,'baybaying',31,'baybay'),(3819,'Bayon',5,'Bayon'),(11900,'BC',57,'BC'),(11915,'BCE',57,'BCE'),(16211,'Bede',5,'Bede'),(12439,'Beijing',5,'Beijing'),(2848,'bektor',17,'bektor'),(11190,'Bel',5,'Bel'),(1336,'Bend',5,'Bend'),(3451,'benditadong',31,'benditado'),(8644,'benepisyal',2,'benepisyo'),(3858,'Beng',5,'Beng'),(2973,'bentahe',17,'bentahe'),(11002,'Berber',5,'Berber'),(6812,'berdeng',31,'berde'),(2096,'Berlin',5,'Berlin'),(2381,'bersyon',17,'bersyon'),(16696,'bersyong',22,'bersyon'),(10904,'Bertolli’s',5,'Bertolli’s'),(10243,'beses',17,'beses'),(17755,'bibig',72,'bibig'),(15085,'Biblical',5,'Biblical'),(14883,'biblical',17,'biblical'),(14728,'Bibliya',5,'Bibliya'),(14808,'bibliya',17,'bibliya'),(14214,'Bibliyang',126,'Bibliya'),(14907,'Bibliyang',126,'Bibliyang'),(1875,'bidyo',17,'bidyo'),(13870,'big',10,'big'),(3282,'bigat',17,'bigat'),(14316,'Biglang',41,'Bigla'),(3547,'bihasang',31,'bihasa'),(18373,'bihira',2,'bihira'),(692,'bihirang',31,'bihira'),(17699,'bilang',2,'bilang'),(4474,'bilang',17,'bilang'),(377,'bilang',18,'bilang'),(10564,'Bilang_kapalit',162,'Bilang_kapalit'),(798,'bilis',17,'bilis'),(10542,'bilyon',17,'bilyon'),(4106,'bilyong',22,'bilyon'),(5631,'binabakas',60,'bakas'),(13802,'binabanggit',62,'banggit'),(10739,'binago',37,'bago'),(5691,'binakas',37,'bakas'),(17055,'Binanggit',37,'Banggit'),(9979,'binary',10,'binary'),(3124,'binasang',3124,'basa'),(14515,'binase',37,'base'),(8002,'binawasan',37,'bawas'),(13754,'binibigay',37,'bigay'),(3098,'binigay',37,'bigay'),(297,'binigyan',37,'bigay'),(16409,'binigyang-diin',37,'bigay-diin'),(10185,'binoboto',62,'boto'),(4963,'binomial',10,'binomial'),(4544,'binomyong',31,'binomyo'),(4640,'binubuo',62,'buo'),(15154,'Binuhay',37,'Buhay'),(3432,'binuo',8,'buo'),(4542,'binuo',37,'buo'),(9360,'biological',2,'biological'),(5842,'biological',10,'biological'),(18341,'Biology',5,'Biology'),(5168,'biology',10,'biology'),(5315,'biolohiyang',22,'biolohiya'),(9469,'bituin',17,'bituin'),(18023,'biyaya',17,'biyaya'),(5036,'biyolohikong',22,'biyolohiko'),(4414,'biyolohikong',31,'biyolohiko'),(1478,'Blimp',5,'Blimp'),(501,'blimp',10,'blimp'),(756,'blimps',10,'blimps'),(1219,'blimps_of_Fleet_Airship_Wing_Five',10,'blimps_of_Fleet_Airship_Wing_Five'),(3570,'bloke',17,'bloke'),(17879,'Bluest',5,'Bluest'),(10615,'bn',1809,'bn'),(14149,'board_of_aldermen',10,'board_of_aldermen'),(13619,'board_of_trustees',10,'board_of_trustees'),(985,'Boat',5,'Boat'),(843,'boat',10,'boat'),(15727,'book',17,'book'),(14613,'Books',5,'Books'),(1564,'Booz',5,'Booz'),(4955,'botanical',10,'botanical'),(4998,'botanika',17,'botanika'),(4674,'Botonica',5,'Botonica'),(10571,'Bouteflika',5,'Bouteflika'),(28,'Brazil',5,'Brazil'),(17932,'Breedlove',5,'Breedlove'),(11474,'Brendan',5,'Brendan'),(1021,'British',5,'British'),(16179,'Brittonum',5,'Brittonum'),(1184,'Brownsville',5,'Brownsville'),(5686,'Brunn',5,'Brunn'),(1053,'Brunswick',5,'Brunswick'),(3708,'buds',10,'buds'),(5827,'bugyang',31,'bugya'),(3258,'buhat',17,'buhat'),(13369,'buhat',96,'buhat'),(5331,'buhay',2,'buhay'),(953,'buhay',17,'buhay'),(17714,'buhok',72,'buhok'),(3894,'bukas',2,'bukas'),(1827,'bukid',17,'bukid'),(6099,'bukod',18,'bukod'),(9435,'bukol',17,'bukol'),(10708,'bulak',17,'bulak'),(6457,'bulaklak',17,'bulaklak'),(5070,'bulati',17,'bulati'),(1775,'bumaba',92,'baba'),(16302,'bumagsak',92,'bagsak'),(4621,'bumalangkas',92,'balangkas'),(1007,'bumalik',92,'balik'),(10568,'bumili',92,'bili'),(11286,'bumubunga',62,'bunga'),(2372,'Bumubuo',62,'Buo'),(9653,'bumubuo',513,'buo'),(7939,'bumuo',92,'buo'),(18146,'bumuo',18146,'buo'),(9138,'bunga',17,'bunga'),(138,'bunton',17,'bunton'),(1677,'buntot',17,'buntot'),(322,'buo',2,'buo'),(17709,'buong',17,'buo'),(1422,'buong',31,'buo'),(7567,'buong',31,'buong'),(3348,'buong',94,'buo'),(714,'buong',94,'buong'),(10538,'burahin',37,'bura'),(13150,'burmistrz',10,'burmistrz'),(1744,'butas',17,'butas'),(11288,'butil',17,'butil'),(11228,'buto',17,'buto'),(465,'buwan',17,'buwan'),(14850,'Byzantine',5,'Byzantine'),(7233,'C',57,'C'),(15757,'c.',1809,'c.'),(14991,'C.S.',57,'C.S.'),(1333,'CA',57,'CA'),(16181,'Cadafael',5,'Cadafael'),(16202,'Cadomedd',5,'Cadomedd'),(9133,'Caenorhabditis',10,'Caenorhabditis'),(474,'California',5,'California'),(1638,'Cameron',5,'Cameron'),(15321,'cane',10,'cane'),(15874,'Canon',5,'Canon'),(15373,'canon',10,'canon'),(15312,'canon',17,'canon'),(15975,'Canonical',5,'Canonical'),(15459,'canonized',10,'canonized'),(12382,'capital',17,'capital'),(1281,'Caravellas',5,'Caravellas'),(2062,'Cargolifter',5,'Cargolifter'),(1195,'Caribbean',5,'Caribbean'),(4611,'Carolus',5,'Carolus'),(11844,'Carthage',5,'Carthage'),(11040,'Catholic',5,'Catholic'),(5273,'Cavalier-Smith',5,'Cavalier-Smith'),(11966,'CE',57,'CE'),(6391,'Celera',5,'Celera'),(5167,'cell',10,'cell'),(9322,'cell',17,'cell'),(3012,'cells',10,'cells'),(11961,'census',17,'census'),(2624,'century',17,'century'),(16495,'Ceolred’s',5,'Ceolred’s'),(4356,'cephalopods',10,'cephalopods'),(10801,'cereal',10,'cereal'),(10850,'cereals',10,'cereals'),(9127,'cerevisiae',10,'cerevisiae'),(1439,'Certified',5,'Certified'),(7269,'chain',10,'chain'),(6327,'chain-termination',10,'chain-termination'),(11837,'Chan’an',5,'Chan’an'),(3841,'Chao',5,'Chao'),(8872,'characterizing',10,'characterizing'),(5850,'Charles',5,'Charles'),(2273,'CHHAPP',57,'CHHAPP'),(15256,'Chicago',5,'Chicago'),(12706,'Chile',5,'Chile'),(12574,'China',5,'China'),(11055,'Christians',5,'Christians'),(7645,'chromatids',10,'chromatids'),(7544,'chromatin',10,'chromatin'),(8473,'chromosomal',10,'chromosomal'),(10861,'citrus',17,'citrus'),(13002,'citta',10,'citta'),(11468,'City',5,'City'),(12620,'city',10,'city'),(12362,'city-states',10,'city-states'),(10716,'Civil',5,'Civil'),(11825,'civilization',10,'civilization'),(13012,'civitas',10,'civitas'),(2055,'CL160',57,'CL160'),(5291,'clade',10,'clade'),(5264,'clades',10,'clades'),(5382,'cladistics',10,'cladistics'),(5363,'Class',5,'Class'),(618,'class',10,'class'),(8849,'clock',10,'clock'),(7856,'clones',10,'clones'),(10415,'Club',5,'Club'),(729,'coast',10,'coast'),(631,'coastline',10,'coastline'),(13086,'coat_of_arms',10,'coat_of_arms'),(6694,'codominance',10,'codominance'),(7331,'COILED',5,'COILED'),(9113,'coli',10,'coli'),(6055,'Colin',5,'Colin'),(11126,'colonial',10,'colonial'),(10578,'combat',10,'combat'),(1512,'Command',5,'Command'),(10126,'communes',10,'communes'),(11041,'Community',5,'Community'),(1479,'Company',5,'Company'),(15,'company',10,'company'),(2564,'competitor',17,'competitor'),(2276,'Composite',5,'Composite'),(11185,'Cone',5,'Cone'),(5881,'Conference_on_Plan_Hybridization',5,'Conference_on_Plan_Hybridization'),(11853,'Constaninopole',5,'Constaninopole'),(1848,'controlled',10,'controlled'),(648,'convoys',10,'convoys'),(9755,'cooler',10,'cooler'),(5084,'Copeland',5,'Copeland'),(10886,'cork',10,'cork'),(1447,'Corp',5,'Corp'),(1555,'Corporation',5,'Corporation'),(14625,'corpus',10,'corpus'),(15820,'Council',5,'Council'),(15825,'Council_of_Trent',5,'Council_of_Trent'),(12250,'countryside',10,'countryside'),(2017,'craft',10,'craft'),(11406,'craftsman',17,'craftsman'),(10629,'creditor',10,'creditor'),(562,'crew',10,'crew'),(129,'crewman',10,'crewman'),(6123,'Crick',5,'Crick'),(17489,'cross-cultural',10,'cross-cultural'),(8474,'crossover',10,'crossover'),(13093,'crown',10,'crown'),(3730,'cruciform',10,'cruciform'),(2884,'cruise',10,'cruise'),(6134,'crystallography',10,'crystallography'),(1210,'Cuba',5,'Cuba'),(10363,'cubic',17,'cubic'),(622,'customers',17,'customers'),(2036,'Cyclocrane',5,'Cyclocrane'),(2021,'Cyclorane',5,'Cyclorane'),(16687,'Cyneburg',5,'Cyneburg'),(16689,'Cyneswith',5,'Cyneswith'),(10986,'Cyprus',5,'Cyprus'),(7231,'cytosine',10,'cytosine'),(6119,'D.',57,'D.'),(2555,'daan',17,'daan'),(3716,'daanan',17,'daan'),(542,'dagat',17,'dagat'),(17256,'Dagdag',379,'Dagdag'),(4894,'dahan-dahan',72,'dahan'),(3662,'dahan-dahanang',31,'dahan'),(4588,'dahan-dahang',41,'dahan'),(1834,'dahil',1834,'dahil'),(4186,'dahilan',17,'dahil'),(12042,'Dahil_dito',734,'Dahil_dito'),(734,'dahil_sa',734,'dahil_sa'),(4499,'dahil_sa',1768,'dahil_sa'),(10765,'dahon',17,'dahon'),(10120,'daïras',10,'daïras'),(16632,'dakilang',31,'dakila'),(12340,'dako',17,'dako'),(13707,'Dakota',5,'Dakota'),(13741,'dala',2,'dala'),(8744,'dalas',17,'dalas'),(9984,'dalawa',135,'dalawa'),(252,'dalawang',46,'dalawa'),(15567,'dalawangpu\'t',15567,'dalawangpu_at'),(1781,'dalhin',37,'dala'),(1587,'dalhin',37,'dalhin'),(15090,'dalisay',2,'dalisay'),(9665,'daloy',17,'daloy'),(5649,'dalubhasa',2,'dalubhasa'),(3997,'dambana',17,'dambana'),(13406,'danay',17,'danay'),(14166,'Daniel',5,'Daniel'),(10562,'dantaon',17,'taon'),(4783,'dapat',2122,'dapat'),(293,'darating',17,'dating'),(2112,'DARPA',57,'DARPA'),(5851,'Darwin',5,'Darwin'),(5028,'Darwinian',5,'Darwinian'),(10881,'dates',10,'dates'),(1622,'dating',31,'dating'),(13419,'David',5,'David'),(11954,'daw',6,'daw'),(3966,'dayandang',22,'dayandang'),(3612,'dayap',17,'dayap'),(10461,'dayuhan',17,'dayuhan'),(14416,'Dead',5,'Dead'),(13971,'debeloper',17,'debeloper'),(10410,'debt',10,'debt'),(13029,'Decree',5,'Decree'),(10797,'dedicado',2,'dedicado'),(2106,'Defense',5,'Defense'),(2270,'defense',10,'defense'),(16222,'Deira',5,'Deira'),(1535,'dekada',17,'dekada'),(1324,'Del',5,'Del'),(7204,'deoxyribonucleic',10,'deoxyribonucleic'),(12769,'departmento',17,'departmento'),(12704,'Department_of_National_Statistics',5,'Department_of_National_Statistics'),(17476,'depekto',17,'depekto'),(12570,'depinisyon',17,'depinisyon'),(682,'deployment',10,'deployment'),(3047,'Desbentahe',2,'Desbentahe'),(12756,'designasyon',17,'designasyon'),(10231,'desisyon',17,'desisyon'),(1141,'destroyers',10,'destroyers'),(14574,'detalye',17,'detalye'),(6336,'determinasyon',17,'determinasyon'),(15898,'Deuterocanonical',5,'Deuterocanonical'),(3743,'devatas',10,'devatas'),(6684,'di',84,'di'),(16837,'diabolical',10,'diabolical'),(15397,'dibisyon',17,'dibisyon'),(13673,'dibisyong',22,'dibisyon'),(4647,'differentia',10,'differentia'),(247,'digmaan',17,'digma'),(347,'Digmaang',5,'Digma'),(417,'digmaang',17,'digma'),(524,'digmaang',22,'digma'),(7024,'dihybrid',10,'dihybrid'),(7214,'dikit-dikit',2,'dikit'),(6810,'dilaw',2,'dilaw'),(147,'din',6,'din'),(1708,'dinadala',62,'dala'),(14462,'dinagdag',8,'dagdag'),(8211,'dinamika',2,'dinamika'),(1507,'Dinerekta',8,'Direkta'),(9295,'ding',751,'din'),(4051,'dingding',17,'dingding'),(3585,'dingding',3389,'dingding'),(3490,'Dinistansya',8,'Distansya'),(7410,'dinodoble',62,'doble'),(7615,'diploid',10,'diploid'),(8728,'direksyon',17,'direksyon'),(650,'direktang',31,'direkta'),(5974,'diretsong',31,'diretso'),(2005,'disenyo',17,'disenyo'),(16569,'disidong',31,'disido'),(16963,'disiplina',17,'disiplina'),(9936,'disk',10,'disk'),(2535,'diskarte',17,'diskarte'),(6415,'diskretong',31,'diskreto'),(2998,'distansiya',17,'distansiya'),(9517,'distansya',17,'distansya'),(8661,'distribyusyon',17,'distribyusyon'),(10118,'distrito',17,'distrito'),(339,'Disyembre',5,'Disyembre'),(6539,'dito',484,'dito'),(14555,'diwa',17,'diwa'),(14189,'diyalekto',17,'diyalekto'),(12193,'Diyos',5,'Diyos'),(3469,'diyos',17,'diyos'),(13374,'Diyosesis',5,'Diyosesis'),(5914,'DNA',57,'DNA'),(11759,'doble',2,'doble'),(7328,'dobleng',31,'doble'),(1924,'Docket',5,'Docket'),(14806,'doktrina',17,'doktrina'),(324,'doktrinang',22,'doktrina'),(12116,'dokumentado',17,'dokumentado'),(2208,'dolyar',17,'dolyar'),(5357,'Domain',5,'Domain'),(10484,'domestikong',22,'domestiko'),(5335,'dominyo',2,'dominyo'),(5247,'dominyong',22,'dominyon'),(13250,'dong',10,'dong'),(484,'doon',484,'doon'),(12506,'dosenang',22,'dosena'),(6155,'double-helix',10,'double-helix'),(3517,'Draco',5,'Draco'),(1443,'Dragon',5,'Dragon'),(9142,'Drosophila',5,'Drosophila'),(1407,'Dubai',5,'Dubai'),(4280,'dugo',17,'dugo'),(4353,'dugo',214,'dugo'),(16355,'dumating',92,'dating'),(8341,'dumedepende',62,'depende'),(11693,'dumidipende',62,'depende'),(8552,'duplikasyon',17,'duplikasyon'),(1237,'Dutch',5,'Dutch'),(10767,'dwarf',10,'dwarf'),(2586,'Dynalifter',5,'Dynalifter'),(10318,'earnings',10,'earnings'),(4103,'Earth',5,'Earth'),(10982,'East',5,'East'),(4094,'ebidensiya',17,'ebidensiya'),(6102,'ebidensya',17,'ebidensya'),(12090,'ebidensyang',22,'ebidensya'),(5594,'ebolusyon',17,'ebolusyon'),(5391,'ebolusyonaryo',17,'ebolusyonaryo'),(18274,'ebolusyonaryong',22,'ebolusyonaryo'),(16791,'Ecclesiastica',5,'Ecclesiastica'),(4393,'echinoderms',10,'echinoderms'),(10382,'economic',10,'economic'),(11469,'Economics',5,'Economics'),(11519,'economies',10,'economies'),(11570,'economies_of_scale',10,'economies_of_scale'),(15819,'Ecumenical',5,'Ecumenical'),(4833,'edisyon',17,'edisyon'),(12151,'Egypt',5,'Egypt'),(14136,'ehekutibo',17,'ehekutibo'),(14111,'ehekutibong',31,'ehekutibo'),(13928,'ehemplo',17,'ehemplo'),(10163,'ekonomikong',22,'ekonomiya'),(10478,'ekonomiya',17,'ekonomiya'),(4090,'eksakto',2,'eksakto'),(12058,'Eksaktong',31,'Eksakto'),(2726,'ekspedisyon',17,'ekspedisyon'),(6036,'eksperimento',17,'eksperimento'),(6078,'eksperimentong',22,'eksperimento'),(6181,'eksplanasyon',17,'eksplanasyon'),(11677,'ekwasyon',17,'ekwasyon'),(3379,'Eleanor',5,'Eleanor'),(234,'electronic',2,'electronic'),(9134,'elegans',10,'elegans'),(3667,'elemento',17,'elemento'),(4022,'elephant',10,'elephant'),(4143,'emergence',10,'emergence'),(11087,'emigrasyon',17,'emigrasyon'),(12279,'emperador',17,'emperador'),(11785,'emperyo',17,'emperyo'),(12267,'Empire',5,'Empire'),(17590,'empirikal',2,'empirikal'),(10349,'Energy',5,'Energy'),(9839,'energy',10,'energy'),(10490,'enerhiya',17,'enerhiya'),(431,'Enero',5,'Enero'),(16872,'Englad',5,'Englad'),(5884,'England',5,'England'),(16060,'Enoch',5,'Enoch'),(10295,'enonomiya',17,'ekonomiya'),(12804,'entities',10,'entities'),(12715,'entity',10,'entity'),(16505,'Eowa',5,'Eowa'),(15795,'epektibo',2,'epektibo'),(7378,'epektibong',41,'epektibo'),(8614,'epekto',17,'epekto'),(8159,'epigenetic',10,'epigenetic'),(6999,'epistalsis',10,'epistalsis'),(7007,'epistatik',10,'epistatik'),(304,'eroplano',17,'eroplano'),(9607,'erupsyon',17,'erupsyon'),(9115,'Escherichia',5,'Escherichia'),(733,'escort',10,'escort'),(16083,'Esdras',5,'Esdras'),(12450,'Espanya',5,'Espanya'),(11152,'Espanyol',5,'Espanyol'),(10883,'esparto',10,'esparto'),(3436,'espasyo',17,'espasyo'),(3497,'espekulasyon',17,'espekulasyon'),(4498,'espesimen',17,'espesimen'),(5100,'espesyal',2,'espesyal'),(18407,'espesyal',60,'espesyal'),(4627,'espesye',17,'espesye'),(15096,'espiritwal',2,'espiritwal'),(10927,'est.',1809,'est.'),(10595,'estado',17,'estado'),(220,'Estados',5,'Estados'),(3772,'estatika',2,'estatika'),(2954,'Estatikang',31,'Estatika'),(3766,'estatwaryo',17,'estatwaryo'),(17068,'estetika',17,'estetika'),(3526,'estilo',17,'estilo'),(3532,'estilong',22,'estilo'),(9515,'estimasyon',17,'estimasyon'),(6125,'estraktura',17,'estraktura'),(2709,'estratosperikong',31,'estratosperiko'),(6150,'estruktura',17,'estruktura'),(6172,'estrukturang',22,'estruktura'),(5952,'estudyante',17,'estudyante'),(3798,'estuko',17,'estuko'),(16046,'Ethiopian',5,'Ethiopian'),(2741,'Etienne',5,'Etienne'),(5299,'Eukarya',5,'Eukarya'),(7547,'eukaryotes',10,'eukaryotes'),(7470,'eukaryotic',10,'eukaryotic'),(7871,'eukaryotikong',22,'eukaryotiko'),(1331,'Eureka',5,'Eureka'),(11098,'Europa',5,'Europa'),(10515,'European',5,'European'),(15650,'Every_God-inspired_Scripture_is_profitable_fo',10,'Every_God-inspired_Scripture_is_profitable_fo'),(10453,'exchange',10,'exchange'),(11113,'exclusibong',41,'exclusibo'),(9522,'expansion',10,'expansion'),(5673,'experimento',17,'experimento'),(10317,'export',10,'export'),(10599,'exporter',10,'exporter'),(12660,'Extra',5,'Extra'),(17880,'Eye',5,'Eye'),(14168,'Ezra',5,'Ezra'),(1925,'FAA',5,'FAA'),(1437,'FAA',57,'FAA'),(13495,'Fair_City_of_Perth',5,'Fair_City_of_Perth'),(5367,'Family',5,'Family'),(17485,'fashion',10,'fashion'),(1191,'FAW',57,'FAW'),(3238,'feet',17,'feet'),(1273,'Fernando_de_Noronha',5,'Fernando_de_Noronha'),(10977,'fertility',10,'fertility'),(10759,'fiber',17,'fiber'),(471,'Field',5,'Field'),(1352,'fields',10,'fields'),(10879,'figs',10,'figs'),(10380,'financial',10,'financial'),(11533,'firms',10,'firms'),(14064,'first',10,'first'),(10436,'fiscal',10,'fiscal'),(5119,'five-kingdom',10,'five-kingdom'),(1173,'FL',57,'FL'),(11476,'Flaherty',5,'Flaherty'),(1031,'Fleet',5,'Fleet'),(3225,'Flemming',5,'Flemming'),(13877,'flip',10,'flip'),(1163,'Florida',5,'Florida'),(534,'flown',10,'flown'),(9140,'fly',10,'fly'),(11342,'food',10,'food'),(2477,'Force',5,'Force'),(10452,'foreign',10,'foreign'),(1271,'Fortaleza',5,'Fortaleza'),(10287,'fossil',10,'fossil'),(1245,'Four',5,'Four'),(8066,'fragments',10,'fragments'),(3246,'France',5,'France'),(6122,'Francis',5,'Francis'),(6139,'Franklin',5,'Franklin'),(6020,'Fredderick',5,'Fredderick'),(12201,'frei',10,'frei'),(10288,'fuels',10,'fuels'),(10404,'Fund',5,'Fund'),(9823,'fusion',10,'fusion'),(7238,'G',57,'G'),(1042,'GA',57,'GA'),(3189,'gaanong',3189,'gaanon'),(846,'gabi',96,'gabi'),(4367,'gagamba',17,'gagamba'),(2057,'gagawin',552,'gagawin'),(6235,'galaw',17,'galaw'),(3712,'galeriya',17,'galeriya'),(3719,'galeriyang',22,'galeriya'),(187,'galing_Brasil',187,'galing_Brasil'),(16743,'galing_kay',16743,'galing_kay'),(1306,'galing_Moffett',187,'galing_Moffett'),(1168,'galing_Richmond',187,'galing_Richmond'),(456,'galing_sa',24,'galing_sa'),(1196,'galing_San_Julian',510,'galing_San_Julian'),(18179,'Galton',5,'Galton'),(8006,'gametes',10,'gametes'),(877,'Gamit',212,'Gamit'),(14553,'gamit',2122,'gamit'),(9191,'gamitin',37,'gamit'),(13386,'gampanin',37,'ganap'),(13298,'ganap',2,'ganap'),(1081,'Ganap',60,'Ganap'),(3170,'ganitong',3170,'ganito'),(8534,'ganitong',3170,'ganitong'),(14956,'ganoon',3034,'ganoon'),(12153,'Gao',5,'Gao'),(2073,'garaheng',22,'garahe'),(941,'gas',10,'gas'),(2852,'gas',17,'gas'),(11580,'gastusin',17,'gastos'),(9112,'gat',10,'gat'),(4023,'gates',10,'gates'),(5519,'gawa',17,'gawa'),(6136,'gawa',2122,'gawa'),(636,'gawain',17,'gawa'),(704,'gawin',37,'gawin'),(11483,'gaya_ng',750,'gaya_ng'),(3034,'gayon',3034,'gayon'),(5003,'Gayong',3170,'Gayon'),(12172,'gayundin',3,'gayundin'),(2528,'Gayunman',3,'Gayunman'),(15080,'Gayunpaman',3,'Gayunpaman'),(10310,'GDP',57,'GDP'),(15186,'Geisler',5,'Geisler'),(6495,'gene',10,'gene'),(9182,'gene',17,'gene'),(4912,'generikong',22,'generiko'),(5977,'genes',10,'genes'),(6778,'genes',17,'genes'),(4170,'genes-first',10,'genes-first'),(8862,'genetic',10,'genetic'),(8980,'genetika',17,'genetika'),(9033,'genetikang',2,'genetika'),(9169,'genetikang',31,'genetika'),(9199,'genetikong',31,'genetiko'),(5822,'genno',10,'genno'),(12301,'Genoa',5,'Genoa'),(6382,'Genome',5,'Genome'),(6399,'genome',10,'genome'),(8528,'genomes',10,'genomes'),(6392,'Genomics',5,'Genomics'),(6591,'genotype',10,'genotype'),(8588,'genotypes',10,'genotypes'),(5369,'Genus',5,'Genus'),(887,'German',5,'German'),(5645,'German-Czech',5,'German-Czech'),(5,'Germany',5,'Germany'),(12138,'Ghana',5,'Ghana'),(1101,'Gibraltar',5,'Gibraltar'),(18027,'gilas',17,'gilas'),(4013,'gilid',2,'gilid'),(1812,'ginagamit',62,'gamit'),(2691,'Ginagawa',8,'Gawa'),(9265,'ginagawa',62,'gawa'),(3599,'ginamit',8,'gamit'),(655,'ginamit',37,'gamit'),(2202,'ginantimpalaan',37,'gantimpala'),(2755,'ginawa',8,'gawa'),(1425,'ginawa',37,'gawa'),(146,'ginawan',37,'gawa'),(5314,'ginawang',37,'gawa'),(4061,'Ginayakan',8,'Gayak'),(3797,'ginintuang',31,'ginto'),(5609,'ginugunita',62,'gunita'),(812,'gipit',2,'gipit'),(3757,'girnalda',17,'girnalda'),(6445,'gisantes',10,'gisantes'),(16178,'Gistoria',5,'Gistoria'),(2,'gitna',2,'gitna'),(31,'gitnang',31,'gitna'),(3642,'Glaize',5,'Glaize'),(1040,'Glynco',5,'Glynco'),(10210,'gobernador',17,'gobernador'),(2248,'gobyerno',17,'gobyerno'),(13092,'golden',10,'golden'),(1705,'gondola',17,'gondola'),(500,'Goodyear',5,'Goodyear'),(3968,'gopura',2,'gopura'),(3942,'gopura',17,'gopura'),(19,'Graf',5,'Graf'),(3351,'Graf',3351,'Graf'),(3503,'Graham',5,'Graham'),(10808,'grain-growing',10,'grain-growing'),(10802,'grains',10,'grains'),(10884,'grass',10,'grass'),(9837,'gravitational',10,'gravitational'),(10980,'Greater',5,'Greater'),(3636,'Greece',5,'Greece'),(5820,'Greeko',5,'Greeko'),(13941,'Greenwich',5,'Greenwich'),(5521,'Gregor',5,'Gregor'),(6021,'Griffith',5,'Griffith'),(15565,'grinupo',37,'grupo'),(14470,'Griyego',5,'Griyego'),(14824,'Griyegong',5,'Griyegong'),(4244,'Griyegong',126,'Griyego'),(15671,'Griyegong',126,'Griyegong'),(14188,'Griyeong',126,'Griyeong'),(12932,'Großstadt',5,'Großstadt'),(2504,'Group',5,'Group'),(1500,'Grumman',5,'Grumman'),(3488,'grupo',17,'grupo'),(4348,'grupo',46,'grupo'),(5418,'grupong',22,'grupo'),(7236,'guanine',10,'guanine'),(1203,'Guantanamo',5,'Guantanamo'),(1233,'Guiana',5,'Guiana'),(9810,'gulang',22,'gulang'),(10751,'gulay',17,'gulay'),(1167,'Gulf_of_Mexico',5,'Gulf_of_Mexico'),(4306,'gulugod',17,'gulugod'),(9636,'gumagalaw',62,'galaw'),(1472,'Gumagamit',62,'Gamit'),(17260,'gumaling',1605,'galing'),(2118,'gumalugad',92,'galugad'),(5586,'gumamit',62,'gamit'),(2546,'gumamit',92,'gamit'),(232,'gumawa',92,'gawa'),(2210,'gumawa',92,'mawa'),(1562,'Gumman',5,'Gumman'),(13452,'gumunita',92,'gunita'),(13306,'gunita',17,'gunita'),(3567,'gusali',17,'gusali'),(17787,'guwapong',31,'guwapo'),(16183,'Gwynedd',5,'Gwynedd'),(2319,'HAA',57,'HAA'),(4691,'haba',17,'haba'),(18211,'Habang',1,'Habang'),(277,'Habang',96,'Habang'),(12855,'habitasyon',17,'habitasyon'),(5198,'Haeckel',5,'Haeckel'),(5238,'hahantong',552,'hantong'),(15635,'haka-hakang',31,'haka'),(11627,'hal',1809,'hal'),(7033,'hal.',1809,'hal.'),(14754,'Halachic',5,'Halachic'),(2337,'halaga',17,'halaga'),(2434,'halagang',22,'halaga'),(10197,'halalan',17,'halal'),(4214,'halaman',17,'halaman'),(6444,'halamang',22,'halaman'),(1368,'Halimbawa',17,'Halimbawa'),(11615,'halimbawang',22,'halimbawa'),(18025,'halinahin',17,'halina'),(1698,'halip',18,'halip'),(1467,'halos',2,'halos'),(10299,'halos',96,'halos'),(1566,'Hamilton',5,'Hamilton'),(13551,'hamlet',10,'hamlet'),(2730,'hamon',17,'hamon'),(8969,'hanay',17,'hanay'),(3504,'Hancock',5,'Hancock'),(712,'handa',2,'handa'),(2090,'hangar',17,'hangar'),(1286,'hanger',10,'hanger'),(12672,'hangganan',17,'hanggan'),(12789,'hanggang',96,'hanggan'),(237,'hanggang',96,'hanggang'),(3040,'hanggang_sa',96,'hanggang_sa'),(9788,'hangga’t',9788,'hangga’t'),(1685,'hangin',17,'hangin'),(2829,'hanging',31,'hangin'),(7596,'haploid',10,'haploid'),(7956,'haploids',10,'haploids'),(334,'Hapon',5,'Hapon'),(336,'Harbor',5,'Harbor'),(3460,'hari',17,'hari'),(3415,'haring',22,'hari'),(10237,'hati-hati',2,'hati'),(9376,'hatiin',37,'hati'),(3344,'haydrodyen',17,'haydrodyen'),(1832,'hayop',17,'hayop'),(4350,'hayop',165,'hayop'),(2560,'head-start',10,'head-start'),(1159,'Headquartered',5,'Headquartered'),(14155,'Hebreo',5,'Hebreo'),(2007,'Heli-Stat',5,'Heli-Stat'),(6148,'helikal',2,'helikal'),(215,'helium',10,'helium'),(7336,'helix',10,'helix'),(11824,'Hellenistic',5,'Hellenistic'),(1700,'helya',17,'helya'),(3327,'helyo',17,'helyo'),(13634,'Hempstead',5,'Hempstead'),(2854,'henerasyon',17,'henerasyon'),(18306,'henerasyon',3389,'henerasyon'),(8231,'henerasyong',22,'henerasyon'),(4643,'henerikong',31,'heneriko'),(6093,'henetik',2,'henetik'),(5515,'henetika',17,'henetika'),(6281,'henetikang',31,'henetika'),(8665,'henetiko',17,'henetiko'),(7864,'henetikong',22,'henetiko'),(7299,'henetikong',31,'henetiko'),(16411,'Henry',5,'Henry'),(8817,'heograpiko',17,'heograpiko'),(9685,'Herbig-Haro',5,'Herbig-Haro'),(6079,'Hershey-Chase',5,'Hershey-Chase'),(6615,'heterozygous',10,'heterozygous'),(6576,'hetterozygous',10,'hetterozygous'),(9457,'HH',57,'HH'),(6169,'hibla',17,'hibla'),(7371,'hiblang',22,'hibla'),(2004,'hibridong',31,'hibrido'),(4441,'hibya',17,'hibya'),(54,'Hidenbrug',5,'Hidenbrug'),(106,'Hidenburg',5,'Hidenburg'),(2278,'High',5,'High'),(2291,'High-Altitude',5,'High-Altitude'),(2316,'high-altitude',10,'high-altitude'),(16642,'Higham',5,'Higham'),(10450,'highs',10,'highs'),(7173,'higit',2,'higit'),(750,'higit',750,'higit'),(12652,'higit',775,'higit'),(11576,'hihigit',552,'higit'),(13474,'Hilagang',31,'Hilagang'),(8064,'hilaw',2,'hilaw'),(5378,'hilig',17,'hilig'),(14710,'hinahanay',62,'hanay'),(17860,'Hinahangad',62,'Hangad'),(11256,'hinahasik',62,'hasik'),(151,'hinati',37,'hati'),(84,'hindi',84,'hindi'),(16857,'hindi',163,'hindi'),(4915,'hindi-mahalagang',4915,'hindi-halaga'),(9003,'hinihikayat',62,'hikayat'),(421,'hiningan',37,'hingi'),(2465,'hinirang',37,'hirang'),(17214,'hinuhusgahan',62,'husga'),(4147,'hipotesis',10,'hipotesis'),(15753,'HippoAD',5,'HippoAD'),(2290,'HiSentinel',5,'HiSentinel'),(7562,'histone',10,'histone'),(16790,'Historia',5,'Historia'),(12068,'historikal',2,'historikal'),(5688,'History',5,'History'),(11949,'historyador',17,'historyador'),(1182,'Hitchcock',5,'Hitchcock'),(17785,'hitsura',17,'hitsura'),(4996,'hiwalay',2,'hiwalay'),(12265,'Holy',5,'Holy'),(6562,'homozygous',10,'homozygous'),(10755,'horsehair',10,'horsehair'),(1175,'Houma',5,'Houma'),(9488,'Hubble',5,'Hubble'),(14713,'Hudaismo',5,'Hudaismo'),(14221,'Hudyo',5,'Hudyo'),(16067,'hudyo',17,'hudyo'),(15105,'Hudyong',126,'Hudyo'),(3706,'hugis',2,'hugis'),(1666,'hugis',17,'hugis'),(16193,'hukbo',17,'hukbo'),(404,'hukbong',17,'hukbo'),(2116,'HULA',57,'HULA'),(5830,'hulaan',92,'hula'),(3920,'huli',96,'huli'),(16998,'huli',135,'huli'),(4558,'huling',1,'huli'),(253,'huling',31,'huli'),(2277,'Hull',5,'Hull'),(851,'Hulyo',5,'Hulyo'),(7262,'humaba',92,'humaba'),(3280,'humalili',92,'halili'),(6381,'Human',5,'Human'),(6398,'human',10,'human'),(7506,'humigit',92,'humigit'),(5922,'humimok',92,'himok'),(16169,'Humina',92,'Hina'),(4488,'Humirap',92,'Hirap'),(5920,'Hunt',5,'Hunt'),(16413,'Huntingdon',5,'Huntingdon'),(9396,'huwag',84,'huwag'),(17799,'huwaran',17,'huwad'),(2581,'hybrid',10,'hybrid'),(5675,'hybridization',10,'hybridization'),(5072,'hyphae',10,'hyphae'),(418,'I',4,'I'),(1941,'iba',2,'iba'),(2544,'iba',159,'iba'),(16263,'iba\'t_iba',2,'iba'),(9444,'iba\'t_ibang',31,'iba'),(17609,'iba\'t_ibang',31,'iba\'t_ibang'),(15021,'iba\'t_ibang',94,'iba'),(10521,'ibababa',552,'baba'),(9873,'ibabaw',2,'babaw'),(3125,'ibabaw',17,'babaw'),(11019,'Ibadis',5,'Ibadis'),(780,'ibang',31,'iba'),(2563,'ibang',94,'iba'),(1149,'ibang',94,'ibang'),(6902,'iba’t_ibang',2,'iba'),(4146,'iba’t_ibang',31,'iba'),(218,'ibenta',37,'benta'),(11664,'ibig',2122,'ibig'),(5681,'ibinahagi',8,'bahagi'),(15010,'ibinatay',37,'batay'),(9369,'ibinibigay',62,'bigay'),(3538,'ibinigay',37,'bigay'),(9739,'ibinigay',37,'ibinigay'),(4591,'ibinuo',37,'buo'),(4327,'ibon',17,'ibon'),(6065,'ibukod',37,'bukod'),(5460,'ICPN',57,'ICPN'),(18159,'ideal',10,'ideal'),(16658,'ideolohiya',17,'ideolohiya'),(16415,'ideya',17,'ideya'),(15014,'ideyang',22,'ideya'),(14337,'Idinagdag',8,'dagdag'),(4580,'idinerekta',8,'direkta'),(1265,'Igarape',5,'Igarape'),(208,'II',4,'II'),(9733,'III',4,'III'),(8220,'iilan',2,'ilan'),(10052,'iilang',31,'ilan'),(1791,'iimbak',552,'imbak'),(13771,'iisa',2,'iisa'),(14308,'iisa',2,'isa'),(9638,'iisang',31,'iisang'),(740,'Iisang',31,'Isa'),(16406,'ika-12',135,'ika-12'),(14287,'ika-14',135,'ika-14'),(12405,'ika-18',135,'ika-18'),(17894,'ika-19',135,'ika-19'),(2445,'ika-31',135,'31'),(16510,'ika-8',135,'ika-8'),(15710,'ika-apat',135,'apat'),(3651,'ika-dalawampung',46,'dalawampu'),(4842,'ika-sampung',46,'sampu'),(4940,'ika-unang',46,'una'),(10374,'ika-walong',46,'ika-walo'),(12021,'ika-walong',46,'walo'),(15366,'ikaapat',135,'apat'),(5758,'ikadalawampu',135,'dalawampu'),(10731,'ikadalawampung',46,'dalawampu'),(4561,'ikalabing-anim',135,'labing-anim'),(3544,'ikalabing-dalawang',46,'labing-dalawa'),(4567,'ikalabing-pitong',46,'labing-pito'),(4921,'ikalabing-siyam',46,'labing-siyam'),(4877,'ikalabing-walong',46,'labing-walo'),(11926,'ikalabinsiyam',135,'labinsiyam'),(14179,'ikalawa',135,'dalawa'),(2312,'ikalawang',46,'ikalawa'),(7004,'ikalawang',135,'dalawa'),(16884,'ikapitong',46,'pito'),(11908,'ikatlo',135,'tatlo'),(16784,'ikatlong',46,'ikatlo'),(14181,'ikatlong',46,'tatlo'),(11763,'ikaw',924,'ikaw'),(14285,'ikawalo',135,'walo'),(10966,'ilalim',24,'lalim'),(2786,'ilan',159,'ilan'),(94,'ilang',94,'ilan'),(9024,'ilang',3095,'ilan'),(11059,'ilang',3095,'ilang'),(9903,'ilaw',17,'ilaw'),(1911,'iligal',17,'iligal'),(17746,'ilong',27,'ilong'),(18140,'imahe',17,'imahe'),(8554,'imbersyon',17,'imbersyon'),(12069,'imbestigasyon',17,'imbestigasyon'),(10406,'IMF',57,'IMF'),(2667,'iminamanupaktura',62,'manupaktura'),(17179,'Iminumungkahi',62,'mungkahi'),(3377,'iminungkahi',8,'mungkahi'),(17524,'Iminungkahi',37,'mungkahi'),(2801,'iminungkahing',2801,'mungkahi'),(17059,'Immanuel',5,'Immanuel'),(12330,'imperyo',17,'imperyo'),(14588,'impluwensiya',17,'impluwensiya'),(7073,'impluwensya',17,'impluwensya'),(7249,'impormasyon',17,'impormasyon'),(6183,'impormasyong',22,'impormasyon'),(3147,'importansya',17,'importansya'),(11643,'importanteng',31,'importante'),(11381,'imports',10,'imports'),(3316,'imprakttikal',2,'impraktikal'),(10059,'inaakala',62,'akala'),(409,'inaalala',62,'alala'),(2519,'inaangkin',62,'angkin'),(18396,'inaasahang',41,'asa'),(5386,'inaayos',62,'ayos'),(13954,'inakap',37,'akap'),(5405,'inanak',37,'anak'),(6358,'inaprubahan',37,'apruba'),(997,'inatake',8,'atake'),(2626,'Inc.',5,'Inc.'),(6884,'independe',2,'independe'),(6796,'independe',17,'independe'),(6849,'independyenteng',31,'independyente'),(11835,'India',5,'India'),(11867,'Indian',5,'Indian'),(9552,'indibiduwal',17,'indibiduwal'),(18226,'indibidwal',2,'indibidwal'),(5559,'indibidwal',17,'indibidwal'),(10383,'indicators',10,'indicators'),(11813,'industriya',17,'industriya'),(11986,'industriyal',17,'industriyal'),(11799,'industriyalisasyon',17,'industriyalisasyon'),(10724,'industrya',17,'industrya'),(15262,'Inerrancy',5,'Inerrancy'),(754,'inescort',8,'escort'),(15086,'infallibility',10,'infallibility'),(10350,'Information',5,'Information'),(9741,'infrared',10,'infrared'),(13444,'Inglatera',5,'Inglatera'),(16652,'Ingles',3351,'Ingles'),(15238,'iniangkla',8,'angkla'),(3441,'iniduong',2801,'iniduo'),(10193,'inihahalal',62,'halal'),(3631,'inihambing',8,'hambing'),(2226,'inihayag',37,'hayag'),(5468,'iniilaan',62,'laan'),(913,'inilabas',8,'labas'),(5055,'inilagay',8,'lagay'),(6894,'inilahad',37,'lahad'),(563,'inilahok',8,'lahok'),(11502,'Inilalarawan',62,'Larawan'),(4802,'inilapat',37,'lapat'),(5174,'inilarawan',8,'larawan'),(16776,'inilarawan',37,'larawan'),(17673,'inilatag',37,'latag'),(15537,'Inilista',37,'lista'),(16271,'inimungkahi',8,'mungkahi'),(2263,'Inisponsor',62,'Sponsor'),(2323,'inisponsoran',8,'sponsor'),(5082,'Iniuri',8,'uri'),(13404,'iniuugnay',62,'ugnay'),(13790,'inkorporadong',31,'inkorporado'),(3994,'inokupahan',8,'okupa'),(11565,'input',10,'input'),(11619,'input',17,'input'),(11714,'Inputs',5,'Inputs'),(11761,'inputs',17,'inputs'),(4362,'insekto',17,'insekto'),(15637,'inspirasyon',17,'inspirasyon'),(10088,'interactions',10,'interactions'),(7014,'Interaksyon',5,'Interaksyon'),(7067,'interaksyon',17,'interaksyon'),(6990,'interaksyong',22,'interaksyon'),(7020,'interaskyon',17,'interaskyon'),(17008,'interbensyon',17,'interbensyon'),(191,'intercontinental',10,'intercontinental'),(3483,'interes',17,'interes'),(6481,'intermidyet',2,'intermidyet'),(5880,'International',5,'International'),(5457,'International_Code_of_Phylogenetic_Nomenclatu',5,'International_Code_of_Phylogenetic_Nomenclatu'),(3372,'interpretasyon',17,'interpretasyon'),(15052,'interpretasyong',22,'interpretasyon'),(14955,'intindihin',37,'intindi'),(9923,'inuubos',62,'ubos'),(11530,'inuugnay',62,'ugnay'),(7555,'inuulit',62,'ulit'),(8476,'ipagpalit',37,'palit'),(6737,'ipakita',37,'kita'),(5592,'ipaliwanag',37,'liwanag'),(5735,'ipamungkahi',37,'mungkahi'),(5497,'ipatupad',37,'tupad'),(241,'ipinagbawal',37,'bawal'),(14291,'ipinaghambing',8,'hambing'),(16969,'Ipinagkaiba',37,'iba'),(217,'ipinagkakait',60,'kait'),(13327,'ipinagkakaloob',62,'loob'),(13021,'ipinagkaloob',8,'loob'),(258,'Ipinagpatuloy',37,'patuloy'),(17082,'Ipinagtatanggol',37,'tanggol'),(9493,'ipinahayag',37,'payag'),(13774,'ipinahihiwatig',62,'hiwatig'),(5709,'ipinakita',37,'kita'),(4589,'ipinalago',37,'lago'),(1370,'ipinalipad',37,'lipad'),(758,'ipinalunod',37,'lunod'),(14927,'ipinapahiwatig',62,'hiwatig'),(5723,'ipinapakita',62,'kita'),(17850,'ipinapakitang',561,'kita'),(11422,'ipinasok',37,'pasok'),(1216,'Ipinatakbo',8,'takbo'),(12,'Ipinatuloy',8,'tuloy'),(1517,'ipinatupad',37,'tupad'),(1279,'Ipitanga',5,'Ipitanga'),(13475,'Ireland',5,'Ireland'),(1716,'isa',135,'isa'),(159,'Isa',159,'Isa'),(16752,'isadiwa',37,'diwa'),(970,'Isadore',5,'Isadore'),(37,'isagawa',37,'gawa'),(17717,'isang',2,'isa'),(17725,'isang',17,'isa'),(46,'isang',46,'isa'),(125,'isang',94,'isa'),(1687,'isang',94,'isang'),(2981,'Isang',159,'Isa'),(17751,'isang',775,'isa'),(2080,'isang',2080,'isa'),(8857,'isa’t_isa',2,'isa'),(5701,'isa’t_isa',159,'isa’t'),(4339,'isda',165,'isda'),(482,'isinagawa',8,'gawa'),(449,'Isinagawa',62,'gawa'),(376,'isinama',37,'sama'),(13860,'isinasaalang-alang',62,'alang'),(2596,'isinasagawa',62,'gawa'),(3829,'isinasakripisyo',62,'sakripisyo'),(1984,'isinasama',62,'sama'),(14092,'isinasangguni',62,'sangguni'),(14740,'isinisiwalat',62,'siwalat'),(14468,'isinulat',8,'sulat'),(15019,'isinulat',37,'sulat'),(10633,'iskedyul',17,'iskedyul'),(14293,'iskolar',17,'iskolar'),(1199,'Isla',5,'Isla'),(1420,'isla',17,'isla'),(11031,'Islam',5,'Islam'),(1416,'Islands',5,'Islands'),(9585,'ISM',57,'ISM'),(11094,'Israel',5,'Israel'),(1897,'istadyum',17,'istadyum'),(8399,'istaktura',17,'istaktura'),(11856,'Istanbul',5,'Istanbul'),(8415,'istraktura',17,'istraktura'),(7523,'istraktural',17,'istraktural'),(7393,'istrakturang',22,'istraktura'),(1655,'istruktura',17,'istruktura'),(9424,'isulong',37,'sulong'),(14256,'itala',37,'tala'),(12282,'Italya',5,'Italya'),(11157,'Italyano',5,'Italyano'),(10913,'Italyanong',126,'Italyano'),(9364,'itigil',37,'tigil'),(17903,'itim',17,'itim'),(3215,'itinakda',8,'takda'),(3958,'Itinala',8,'tala'),(11963,'itinalang',8,'tala'),(249,'Itinanggal',37,'tanggal'),(12464,'Itinatag',37,'tatag'),(5348,'itinatagtag',62,'tagtag'),(14595,'itinatanggi',62,'tanggi'),(11606,'itinatayo',62,'tayo'),(610,'itinayo',8,'tayo'),(1288,'itinayo',37,'tayo'),(278,'itiniyak',37,'tiyak'),(306,'itinuloy',37,'tuloy'),(15653,'Itinuring',37,'turing'),(3762,'Itinuturing',62,'turing'),(97,'ito',97,'ito'),(4895,'itong',60,'ito'),(160,'itong',160,'ito'),(17869,'iwaksi',37,'waksi'),(18397,'iwasan',60,'iwas'),(12212,'iyo',805,'iyo'),(4262,'iyon',97,'iyon'),(6118,'James',5,'James'),(12357,'Japan',5,'Japan'),(5582,'Jean-Baptiste',5,'Jean-Baptiste'),(2740,'Jean-Louis',5,'Jean-Louis'),(13199,'Jeju',5,'Jeju'),(15762,'Jerome',5,'Jerome'),(74,'Jersey',5,'Jersey'),(14792,'Jerusalem',5,'Jerusalem'),(9590,'jet',10,'jet'),(9632,'jets',10,'jets'),(11062,'Jewish',5,'Jewish'),(5641,'Johann',5,'Johann'),(15611,'Josephus',5,'Josephus'),(2393,'JP',5,'JP'),(16062,'Jubiless',5,'Jubiless'),(14585,'Judaism',5,'Judaism'),(1096,'June',5,'June'),(362,'K',57,'K'),(17395,'kaakit-akit',2,'akit'),(17532,'kaakit-akit',60,'akit'),(4599,'kaalaman',17,'alam'),(8623,'kaangkupang',31,'angkop'),(18295,'kaanyuan',17,'anyo'),(15023,'kaaya-ayang',31,'aya'),(16124,'kaayusan',17,'ayos'),(17518,'kababaihan',17,'babae'),(6030,'kababalaghan',17,'balaghan'),(7352,'kabaligtaran',2,'baligtad'),(7011,'Kabanata',17,'Kabanata'),(17550,'kabataan',17,'bata'),(5067,'kabatiran',17,'batid'),(14758,'Kabbalah',5,'Kabbalah'),(4387,'kabibi',17,'kabibi'),(8213,'kabila',18,'kabila'),(9100,'Kabilang',2,'bilang'),(13006,'kabilang',2,'kabilang'),(564,'kabilang',3,'kabilang'),(2685,'kabilang',18,'bilang'),(14129,'kabilang',18,'kabila'),(12955,'kabilang',18,'kabilang'),(6168,'kabilang',31,'kabila'),(4521,'kabisaduhin',37,'kabisado'),(15180,'kabutihan',17,'buti'),(5786,'kabuuan',159,'buo'),(5542,'kabuuang',94,'buo'),(244,'kadahilanang',22,'dahil'),(17768,'kadahilanang',40,'dahil'),(11035,'Kadalasan',96,'Dalas'),(7576,'kadalasan',96,'kadalasan'),(9883,'kadalasang',1,'dalas'),(7550,'kadalasang',41,'dalas'),(5010,'kadalian',17,'dali'),(17782,'kagalang-galang',2,'galang'),(2182,'kagamitang',22,'gamit'),(9314,'kaganapan',17,'ganap'),(15518,'kaganapang',22,'ganap'),(16906,'kagandahan',17,'ganda'),(17271,'kagandahan',18,'ganda'),(2833,'kagaya_ng',750,'gaya_ng'),(2030,'Kagiliw-giliw',2,'giliw'),(9073,'kaginhawaan',17,'ginhawa'),(17561,'kagustuhan',17,'gusto'),(5747,'kahalagahan',17,'halaga'),(14833,'kahalintulad',750,'tulad'),(17780,'kahanga-hanga\'t',17780,'hanga'),(5959,'kahanga-hangang',31,'hanga'),(5117,'kaharian',17,'hari'),(5201,'kahariang',22,'hari'),(318,'kahit',18,'kahit'),(6944,'kahit',162,'kahit'),(3810,'kahoy',2,'kahoy'),(190,'kahulihang',31,'huli'),(4714,'kahulugan',17,'hulog'),(16148,'kahulugang',22,'hulog'),(15941,'kaiba',2,'iba'),(16131,'kaiba',60,'iba'),(9604,'kaibahan',17,'iba'),(9857,'kaibuturan',17,'kaibuturan'),(703,'kailangan',17,'kailangan'),(4701,'kailangang',4701,'kailangan'),(6479,'kailanman',96,'kailanman'),(6617,'kailimitan',2,'limit'),(447,'Kairships',5,'Kairships'),(4753,'kakaibang',31,'iba'),(12469,'kakasakop',12469,'sakop'),(13700,'kakatwang',31,'kakatwa'),(10941,'kakaunting',4450,'unti'),(17262,'kakayahan',7,'kaya'),(4224,'kakayahan',17,'kaya'),(17291,'kakayahang',22,'kaya'),(7261,'kakayanan',17,'kaya'),(8348,'kakulangan',17,'kulang'),(736,'kalaban',17,'laban'),(11680,'kalabasan',17,'labas'),(10389,'kalagitnaan',2,'gitna'),(3649,'kalagitnaan',96,'gitna'),(2988,'kalagtitnaan',2,'gitna'),(10560,'kalahati',2,'hati'),(17301,'kalahati',159,'hati'),(3710,'kalahating',31,'hati'),(11876,'kalahating-milyong',11876,'hati-milyon'),(10527,'kalakalan',17,'kalakal'),(4184,'kalakaran',17,'kalakal'),(6731,'kalalabasan',212,'labas'),(6717,'kalalabasang',85,'labas'),(17516,'kalalakihan',17,'lalaki'),(11497,'kalamangan',17,'kalamangan'),(11510,'kalamangan',17,'lamang'),(885,'kalatagan',17,'latag'),(10164,'kalayaan',17,'laya'),(3831,'kalidad',17,'kalidad'),(1382,'kaligtasan',17,'ligtas'),(5657,'kalikasan',17,'likas'),(8132,'kalimitan',2,'limit'),(6899,'Kalimitan',96,'limit'),(7495,'kalimitang',41,'limit'),(8632,'kalimitang',60,'limit'),(7185,'kalusugan',17,'lusog'),(13389,'kamaharlikahan',17,'maharlika'),(5283,'kamakailan',96,'kailan'),(16552,'kamalayan',17,'malay'),(14805,'kamali-maling',31,'mali'),(15302,'kamalian',17,'mali'),(15224,'kamalian',3389,'mali'),(4428,'kamatayan',17,'patay'),(16650,'kamatayan-plegarya',17,'patay-plegarya'),(12956,'kamay',17,'kamay'),(17824,'kanais-nais',2,'nais'),(13569,'kanayunan',17,'nayon'),(9261,'kandidatong',22,'kandidato'),(805,'kanila',805,'kanila'),(17268,'kanilang',7,'kanila'),(536,'kanilang',156,'kanila'),(9738,'kanilang',156,'kanilang'),(10140,'kaniyang',156,'kaniya'),(3956,'kanluran',2,'kanluran'),(17690,'kanlurang',17,'kanluran'),(4072,'kanlurang',31,'kanluran'),(9063,'kanser',17,'kanser'),(17060,'Kant',5,'Kant'),(3833,'kantidad',17,'kantidad'),(156,'kanyang',156,'kanya'),(3493,'kanyang',156,'kanyang'),(3231,'kapag',162,'kapag'),(6611,'Kapag',1834,'Kapag'),(9240,'kapaki-pakinabang',31,'kinabang'),(2775,'kapal',2,'kapal'),(16419,'kapalaran',17,'palad'),(7084,'kapaligiran',17,'ligid'),(8648,'kapaligirang',22,'ligid'),(2644,'kapaligirang',31,'ligid'),(2804,'kapalit',17,'palit'),(66,'kapanahunang',22,'panahon'),(5828,'kapanganakan',17,'anak'),(1960,'kapangyarihan',17,'yari'),(2970,'kapangyarihang',22,'yari'),(10159,'kapanhalatang',41,'lahat'),(16913,'kapansin-pansin',2,'pansin'),(7389,'kaparehang',31,'pareha'),(10753,'kapareho',750,'pareho'),(6208,'kaparehong',31,'pareho'),(7640,'kaparehong',3089,'pareho'),(7945,'kapares',2,'kapares'),(7348,'kapares',750,'pares'),(8512,'kaparis',17,'kaparis'),(7643,'kapatid',17,'patid'),(3412,'kapayapaan',17,'payapa'),(11805,'kapital',17,'kapital'),(11435,'kapitbahay',17,'kapitbahay'),(17854,'kapootang',22,'poot'),(10189,'kapulungan',17,'pulong'),(15750,'kapulungang',22,'pulong'),(12807,'kapuna-puna',2,'puna'),(6165,'kapupunan',17,'kapupunan'),(17857,'kapwa',159,'kapwa'),(3925,'karagdagan',17,'dagdag'),(3371,'karagdagang',31,'dagdag'),(7775,'karamdaman',17,'dama'),(9172,'karamdaman',17,'damdam'),(4122,'karamihan',159,'dami'),(3569,'Karamihang',31,'Dami'),(3190,'karaming',31,'dami'),(4459,'karanasan',17,'danas'),(18292,'karaniwan',2,'karaniwan'),(18418,'karaniwan',17,'karaniwan'),(1801,'karaniwang',31,'karaniwan'),(1793,'Karaniwang',41,'Karaniwan'),(3945,'kardinal',2,'kardinal'),(2703,'kargada',17,'karga'),(1589,'kargamento',17,'kargamento'),(2159,'kargamentong',22,'kargamento'),(684,'kargo',17,'kargo'),(16523,'karimlan',17,'dilim'),(17277,'karpintero',17,'karpintero'),(6352,'Kary',5,'Kary'),(3082,'kasabay',2,'sabay'),(6386,'kasabay',3,'sabay'),(12216,'kasabihan',17,'sabi'),(16225,'kasabik-sabik',2,'sabik'),(16702,'kasal',17,'kasal'),(8646,'kasalukuyan',17,'salukoy'),(10107,'kasalukuyang',1,'kasalukuyan'),(2239,'kasalukuyang',1,'salukoy'),(11815,'kasama',3,'kasama'),(778,'kasama',3,'sama'),(7046,'kasama',212,'sama'),(16258,'kasamahan',17,'sama'),(769,'kasamang',31,'sama'),(13253,'kasamang',13253,'sama'),(9094,'kasangkapan',17,'sangkap'),(9185,'kasangkot',212,'sangkot'),(1597,'kasarinlang',22,'kasarinlan'),(16740,'kasaysayan',17,'saysay'),(12519,'kasing',750,'kasing'),(12763,'kasing',3089,'kasing'),(17299,'kasingliit',17299,'liit'),(17323,'kasingliit',17323,'liit'),(2907,'kasko',17,'kasko'),(2872,'kaso',17,'kaso'),(15281,'Kasulatan',5,'sulat'),(15113,'kasulatan',17,'sulat'),(1126,'kasunduan',17,'sndo'),(16848,'kataas-taasang',31,'taas'),(2824,'kataasang',22,'taas'),(4762,'katabi',2,'tabi'),(18021,'katalinuhan',17,'talino'),(17728,'katalinuhan',72,'talino'),(7657,'kataliwasan',17,'taliwas'),(18287,'katambal',17,'tambal'),(18294,'katampalang',31,'tampal'),(18129,'katampatan',17,'tampat'),(18051,'katampatan',17,'tapat'),(18420,'katampatang',31,'tapat'),(5421,'katangian',17,'tangi'),(3693,'katangiang',22,'tangi'),(14871,'katanungang',22,'tanong'),(112,'kataong',22,'tao'),(14689,'katapusan',17,'tapos'),(10648,'katapusan',96,'tapos'),(3200,'katatagan',17,'tatag'),(4867,'katawagan',17,'tawag'),(4597,'katawan',17,'katawan'),(5102,'katayuan',17,'tayo'),(13352,'katedral',17,'katedral'),(4168,'kategoryang',22,'kategorya'),(17557,'katibayan',17,'tibay'),(3982,'katimugang',31,'timog'),(14362,'katinig',17,'katinig'),(14215,'Katoliko',5,'Katoliko'),(15998,'katoliko',17,'katoliko'),(8990,'katotohanan',17,'totoo'),(5141,'katuklasan',17,'tuklas'),(4553,'katulad',2,'tulad'),(17340,'katulad',750,'tulad'),(15789,'katulad_ng',3,'tulad_ng'),(2482,'katulad_ng',750,'tulad_ng'),(12941,'katumbas',2,'tumbas'),(18324,'katumbas',17,'tumbas'),(15724,'katumbas_ng',750,'tumbas_ng'),(4718,'katumpakan',17,'tumpak'),(9977,'katunayan',17,'tunay'),(17036,'katuwaan',17,'tuwa'),(4203,'kaugalian',17,'ugali'),(12188,'kaugaliang',22,'ugali'),(2522,'kaugnay',2,'ugnay'),(15142,'kaugnayan',17,'ugnay'),(13336,'kaugnayang',22,'ugnay'),(5265,'kaugnay_sa',750,'ugnay_sa'),(2454,'kauna-unahang',31,'una'),(12525,'kaunti',2,'kaunti'),(12561,'kaunti',159,'unti'),(10467,'kaunting',31,'unti'),(7698,'kaunting',94,'unti'),(11499,'kawalan',17,'wala'),(7443,'kawing',17,'kawing'),(1560,'kay',1498,'kay'),(17538,'kaya',162,'kaya'),(2146,'kaya',1834,'kaya'),(2423,'kayang',41,'kaya'),(8762,'kaya’t',8762,'kaya'),(11277,'kaysa',750,'kaysa'),(1763,'kaysa_sa',750,'kaysa_sa'),(8385,'kemikal',17,'kemikal'),(1170,'Key',5,'Key'),(3529,'Khmer',5,'Khmer'),(5097,'kikilalanin',552,'kilala'),(3985,'kilala',2,'kilala'),(14297,'kilala',18,'kilala'),(4231,'kilalang',31,'kilala'),(6067,'kilalanin',37,'kilala'),(4032,'kilalanin',62,'kilala'),(13433,'kilalaning',561,'kilala'),(17740,'kilay',72,'kilay'),(2167,'kilometro',17,'kilometro'),(10657,'kilos',17,'kilos'),(17864,'kilusang',22,'kilos'),(15429,'kinabibilangan',62,'bilang'),(3691,'kinabibilangan',62,'kabilang'),(17975,'kinabubuhayan',62,'buhay'),(992,'kinabukasan',96,'bukas'),(3297,'kinailangan',8,'kailangan'),(3105,'kinailangang',561,'kailangan'),(3063,'kinakailangan',62,'kailangan'),(7383,'kinakailangang',561,'kailangan'),(12428,'kinakaribal',62,'karibal'),(8952,'kinakatawan',62,'katawan'),(16227,'kinalabasan',37,'labas'),(13952,'kinalaunan',8,'laon'),(14877,'kinalaunang',31,'laon'),(15489,'kinalaunang',2801,'laon'),(15876,'Kinalauna’y',9788,'Laon'),(14223,'kinalauna’y',14223,'laon_ay'),(15467,'kinanonisa',8,'kanon'),(2806,'kinanselang',2801,'kansela'),(10141,'kinauupuan',2,'upo'),(12348,'kinawilihan',37,'wili'),(14810,'King',5,'King'),(1643,'Kingdom',5,'Kingdom'),(1933,'Kinikilala',62,'Kilala'),(6757,'kinikilalang',561,'kilala'),(6082,'kinilala',2,'kilala'),(4273,'Kinilala',8,'Kilala'),(15451,'kinilala',62,'kilala'),(4925,'kinodipika',62,'kodipika'),(4978,'kinokontrol',62,'kontrol'),(4678,'kinuha',37,'kuha'),(16237,'Kirby',5,'Kirby'),(3812,'kisame',17,'kisame'),(10305,'kita',17,'kita'),(360,'klase',17,'klase'),(3655,'klasiko',2,'klasiko'),(3525,'klasikong',31,'klasiko'),(5022,'klasipikasyon',17,'klasipikasyon'),(9477,'km/s',1809,'km/s'),(10794,'km2',1809,'km2'),(9553,'knot',10,'knot'),(9570,'knots',10,'knots'),(4981,'kodigo',17,'kodigo'),(17104,'kognitibong',22,'kognitibo'),(14191,'Koine',5,'Koine'),(18134,'koinophilia',10,'koinophilia'),(12175,'koleksyon',17,'koleksyon'),(1609,'koleksyong',22,'koleksyon'),(12448,'kolonisasyon',17,'kolonisasyon'),(4967,'kombensiyon',17,'kombensiyon'),(5484,'kombensyunal',2,'kombensyunal'),(6872,'kombinasyon',17,'kombinasyon'),(1867,'komersyal',2,'komersyal'),(11984,'komersyal',17,'komersyal'),(11797,'komersyo',17,'komersyo'),(2359,'kompanya',17,'kompanya'),(1430,'kompleho',2,'kompleho'),(7102,'kompleks',2,'kompleks'),(7060,'kompleksadong',31,'kompleksado'),(4687,'komposisyon',17,'komposisyon'),(16971,'kompositor',17,'kompositor'),(18252,'kompyuter',17,'kompyuter'),(12196,'komunidad',17,'komunidad'),(2404,'komunikasyong',22,'komunikasyon'),(18241,'kondisyon',17,'kondisyon'),(16391,'koneksyon',17,'koneksyon'),(16540,'konektado',2,'konektado'),(8042,'konjugasyon',17,'konjugasyon'),(13605,'konseho',17,'konseho'),(12853,'konsentrasyon',22,'konsentrasyon'),(3016,'konsepto',17,'konsepto'),(11526,'konseptong',22,'konsepto'),(3764,'konserbatibo',2,'konserbatibo'),(3645,'konserbator',17,'konserbator'),(3515,'konstelasyong',22,'konstelasyon'),(10150,'konstitusyon',17,'konstitusyon'),(8649,'konteksto',17,'konteksto'),(12552,'konti',2,'konti'),(12222,'kontinental',17,'kontinental'),(425,'kontra',17,'kontra'),(16782,'kontrabida',17,'kontrabida'),(1559,'kontrata',17,'kontrata'),(2335,'kontratang',22,'kontrata'),(2194,'kontratistang',22,'kontratista'),(17793,'kontribusyon',17,'kontribusyon'),(12323,'kontrol',17,'kontrol'),(18240,'kontroladong',31,'kontrolado'),(6554,'kopya',17,'kopya'),(13175,'Korea',5,'Korea'),(12992,'kreisfreie',10,'kreisfreie'),(18197,'kriminal',17,'kriminal'),(15125,'Kristiano',5,'Kristiano'),(14890,'Kristiyanismo',5,'Kristiyanismo'),(14226,'Kristiyano',5,'Kristiyano'),(15552,'Kristiyano\'t',15552,'Kristiyano\'t'),(16764,'Kristiyanong',5,'Kristiyano'),(14921,'Kristiyanong',126,'Kristiyano'),(3174,'kritikal',2,'kritikal'),(16973,'kritikong',22,'kritiko'),(7451,'kromosom',17,'kromosom'),(5979,'kromosoma',17,'kromosoma'),(5929,'kromosomas',17,'kromosomas'),(4359,'krustasya',17,'krustasya'),(4443,'krustasyo',17,'krustasyo'),(3069,'kubo',17,'kubo'),(2904,'kubyerta',17,'kubyerta'),(6956,'kulay',2,'kulay'),(6455,'kulay',17,'kulay'),(12107,'kultura',17,'kultura'),(4181,'kumakailan',8,'kailan'),(15391,'Kumakatawan',62,'katawan'),(2530,'kumakatawan',92,'katawan'),(12133,'Kumbi-Saleh',5,'Kumbi-Saleh'),(2842,'kumbinasyon',17,'kumbinasyon'),(17766,'kumbinasyon',165,'kumbinasyon'),(15039,'kumikilala',62,'kilala'),(1635,'kumpanya',17,'kumpanya'),(1940,'kumpanyang',22,'kumpanya'),(18224,'kumpara_sa',750,'kumpara_sa'),(1674,'kumpleto',2,'kumpleto'),(6678,'kumpletong',31,'kumpleto'),(14509,'kumpletong',41,'kumpleto'),(5267,'kumplikadong',31,'komplikado'),(8062,'kumuha',92,'kuha'),(7507,'kumulang',92,'kumulang'),(9562,'kumupas',92,'kupas'),(6191,'kundi',3,'kundi'),(2413,'kundi',18,'kundi'),(162,'kung',162,'kung'),(5326,'kung',1834,'kung'),(17657,'kung',17657,'kung'),(3224,'Kurt',5,'Kurt'),(17511,'kutis',17,'kutis'),(12443,'Kyoto',5,'Kyoto'),(616,'L',57,'L'),(3219,'L-55',5,'L-55'),(16216,'labanan',17,'laban'),(379,'laban_sa',379,'laban_sa'),(6043,'labing-anim',135,'labing-anim'),(2187,'labor',17,'labor'),(3559,'ladrilyo',17,'ladrilyo'),(7827,'laging',41,'lagi'),(4253,'lahat',159,'lahat'),(17797,'lahi',17,'lahi'),(461,'Lairships',5,'Lairships'),(1693,'lakas',17,'lakas'),(2125,'lakbay',17,'lakbay'),(2882,'lakbayang',22,'lakbay'),(13656,'Lake',5,'Lake'),(71,'Lakehurst',5,'Lakehurst'),(4030,'laki',17,'laki'),(10332,'lalagyan',552,'lagay'),(17688,'lalaki',17,'laki'),(7743,'lalaki',17,'lalaki'),(7705,'lalaking',22,'lalaki'),(770,'lalim',17,'lalim'),(11144,'lalo',750,'lalo'),(8285,'lalo',775,'lalo'),(18152,'lalong',3089,'lalo'),(9239,'lalong',9239,'lalo'),(14801,'lalong_lalo',3972,'lalo'),(14864,'laman',17,'laman'),(6,'lamang',6,'lamang'),(5583,'Lamarck',5,'Lamarck'),(17208,'lamog',2,'lamog'),(10809,'land',10,'land'),(4149,'landas',17,'landas'),(12969,'Landkreis',5,'Landkreis'),(18278,'lang',6,'lang'),(5942,'langaw',17,'langaw'),(10431,'langis',17,'langis'),(3471,'langit',17,'langit'),(14594,'lantarang',41,'lantad'),(285,'laos',2,'laos'),(2261,'larangan',17,'larang'),(12644,'Large',5,'Large'),(12661,'large',10,'large'),(3561,'laterite',10,'laterite'),(15360,'Latin',5,'Latin'),(13011,'latin',10,'latin'),(13030,'Law',5,'Law'),(396,'lawak',17,'lawak'),(2161,'layo',17,'layo'),(1591,'layog',17,'layog'),(291,'layunin',17,'layon'),(1585,'layunin',17,'layunin'),(16265,'layunin',3389,'layon'),(1882,'layuning',22,'layon'),(11877,'lebel',17,'lebel'),(12680,'legal',2,'legal'),(14715,'Legal',5,'Legal'),(1955,'legal',60,'legal'),(12229,'lehislatura',17,'lehislatura'),(14119,'lehislaturang',31,'lehislatura'),(14361,'letrang',22,'letra'),(13320,'letters',10,'letters'),(14992,'Lewis',5,'Lewis'),(2920,'libangan',17,'libang'),(9808,'libong',22,'libo'),(6777,'libong',31,'libo'),(15027,'libong',46,'libo'),(8333,'libong-tuping',31,'libo-tupi'),(14203,'libro',17,'libro'),(11466,'librong',22,'libro'),(11488,'libu-libong',46,'libo'),(10558,'lider',17,'lider'),(1529,'lighter-than-air',10,'lighter-than-air'),(127,'ligis',2,'ligis'),(5804,'liham',17,'liham'),(12764,'liit',2,'liit'),(16947,'likas',2,'likas'),(8426,'likas',60,'likas'),(6473,'lila',2,'lila'),(10027,'lilikha',552,'likha'),(14612,'Limang',5,'Lima'),(4347,'limang',18,'lima'),(4316,'limang',46,'lima'),(615,'limang',135,'lima'),(795,'limitado',2,'limita'),(3134,'limitasyon',17,'limita'),(12682,'limitasyon',17,'limitasyon'),(16573,'linabanan',17,'laban'),(5960,'lingkahe',17,'lingkahe'),(5788,'linikha',8,'likha'),(5487,'Linnaean',5,'Linnaean'),(4612,'Linnaeus',5,'Linnaeus'),(5057,'Linnaeus',5057,'Linnaeus'),(17761,'linya',3,'linya'),(8519,'linya',17,'linya'),(7439,'linyar',2,'linyar'),(186,'lipad',17,'lipad'),(13470,'Lisburn',5,'Lisburn'),(15331,'listahan',17,'lista'),(9971,'litaw',17,'litaw'),(14546,'literal',2,'literal'),(9544,'liwanag',17,'liwanag'),(1657,'lobo',17,'lobo'),(7641,'loci',10,'loci'),(2196,'Lockheed-Martin',5,'Lockheed-Martin'),(17111,'lohikal',17,'lohikal'),(11304,'lokal',2,'lokal'),(2173,'lokasyon',17,'lokasyon'),(1327,'Lompoc',5,'Lompoc'),(11930,'London',5,'London'),(3707,'lotus',10,'lotus'),(1177,'Louisiana',5,'Louisiana'),(12305,'Lubeck',5,'Lubeck'),(4430,'Lubhang',2774,'Lubha'),(17833,'Lubos',3972,'Lubos'),(16286,'ludeu',10,'ludeu'),(1841,'lugar',17,'lugar'),(1269,'Luiz',5,'Luiz'),(16254,'lumaban',92,'laban'),(7821,'lumabas',92,'labas'),(7848,'lumabas',92,'lumabas'),(108,'lumagpak',92,'lagpak'),(12014,'lumagpas',92,'lagpas'),(17463,'lumaki',92,'laki'),(5122,'Lumalabas',62,'Labas'),(17559,'lumalabas',62,'lalabas'),(16373,'lumalakbay',513,'lakbay'),(9440,'lumalaki',62,'laki'),(15576,'Lumang',5,'Luma'),(10259,'lumang',31,'luma'),(14211,'Lumang',126,'Luma'),(9020,'lumaon',92,'laon'),(7881,'lumikha',92,'likha'),(8583,'lumilikha',62,'likha'),(513,'lumilikha',513,'likha'),(3732,'lumilitaw',62,'litaw'),(9574,'lumilitaw',513,'litaw'),(226,'lumipad',92,'lipad'),(5889,'Lumipas',92,'Lipas'),(5384,'lumitaw',92,'litaw'),(9560,'lumiwanag',9560,'liwanag'),(1260,'lumpiad',92,'lumpiad'),(726,'lumubog',92,'lubog'),(388,'lumulubog',92,'lubog'),(98,'lumunsad',92,'lunsad'),(13287,'lungsod',17,'lungsod'),(2183,'lupa',17,'lupa'),(2952,'lupain',2,'lupa'),(3910,'lupang',22,'lupa'),(2782,'lutang',17,'lutang'),(57,'LZ',57,'LZ'),(3875,'m',1809,'m'),(3241,'m)',1809,'m)'),(9906,'ma-o-obserbahan',552,'obserba'),(13304,'maaabot',552,'abot'),(3035,'maaari',2122,'maaari'),(17273,'Maaaring',11,'Maaari'),(17259,'maaaring',72,'maaari'),(1772,'maaaring',1605,'maaari'),(14142,'maaaring',4701,'maaari'),(3198,'maabot',212,'abot'),(3778,'maaga',2,'aga'),(3163,'maagang',31,'aga'),(18280,'maakit',212,'akit'),(9341,'maapektuhan',212,'apekto'),(2122,'maari',2122,'maari'),(1605,'maaring',1605,'maari'),(10049,'maaring',1605,'maaring'),(8441,'maayos',212,'ayos'),(8304,'mababa',2,'baba'),(17193,'mababait',2,'bait'),(2428,'mababang',31,'baba'),(1794,'mabagal',2,'bagal'),(17229,'mabait',2,'bait'),(16759,'mabangis',2,'bangis'),(3024,'mabawasan',212,'bawas'),(2700,'mabibigat',2,'bigat'),(2592,'mabigat',2,'bigat'),(2127,'mabigatang',31,'bigat'),(9079,'mabilis',2,'bilis'),(1773,'mabilis',60,'bilis'),(11270,'mabilisang',85,'bilis'),(5501,'mabubuhay',552,'buhay'),(8902,'mabuo',212,'buo'),(11415,'mabuti',2,'buti'),(7181,'mabuting',31,'buti'),(16121,'Maccabees',5,'Maccabees'),(1277,'Maceiro',5,'Maceiro'),(12200,'macht',10,'macht'),(6058,'Maclyn',5,'Maclyn'),(2943,'madalang',2,'dalang'),(776,'madalas',2,'dalas'),(3827,'madalas',60,'dalas'),(1835,'madali',2,'dali'),(420,'madaling',41,'dali'),(1780,'Madaling',41,'Madali'),(9754,'madaming',31,'dami'),(9787,'madidiskubre',552,'diskubre'),(9790,'madiskubre',212,'diskubre'),(14390,'mag-iba',212,'iba'),(1957,'mag-opera',212,'opera'),(818,'magagamit',552,'gamit'),(17564,'magagandang',31,'ganda'),(1606,'magamit',212,'gamit'),(15643,'maganap',212,'ganap'),(17143,'maganda',2,'ganda'),(10758,'magandang',31,'ganda'),(4456,'magawa',212,'gawa'),(17266,'magbago',27,'bago'),(8780,'magbago',212,'bago'),(8278,'magbigay',60,'bigay'),(1691,'magbigay',212,'bigay'),(2424,'magbuhat',212,'buhat'),(11202,'magdadala',552,'dala'),(6938,'magenta',2,'magenta'),(6986,'magentang',31,'magenta'),(9226,'maghanap',212,'hanap'),(13976,'magiliw',2,'giliw'),(3659,'maging',212,'maging'),(14597,'magkaiba',2,'iba'),(17481,'magkaiba',212,'iba'),(6570,'magkaibang',85,'iba'),(8826,'magkakaiba',62,'iba'),(2922,'Magkakaroon',552,'Mayroon'),(5503,'magkakasama',60,'sama'),(8516,'magkamit',212,'kamit'),(11507,'magkaparehong',41,'pareho'),(8507,'magkaparehong',85,'pareho'),(3028,'magkarga',212,'karga'),(2897,'magkaroon',212,'mayroon'),(7077,'magkasama',212,'sama'),(15989,'magkasingkahulugan',212,'hulog'),(14279,'magkatulad',2,'tulad'),(17577,'magkatulad',212,'tulad'),(5966,'magkaugnay',212,'ugnay'),(15823,'maglabas',212,'labas'),(4601,'maglingkod',212,'lingkod'),(6720,'magmamana',552,'mana'),(13973,'magmukhang',41,'mukha'),(1600,'Magpapakita',552,'Kita'),(10635,'Magreresulta',552,'resulta'),(8495,'magresulta',212,'resulta'),(18081,'magsagawa',212,'gawa'),(12759,'magsama',212,'sama'),(11458,'magsamasama',552,'sama'),(2877,'magsilbing',85,'silbi'),(2601,'Magsisimula',552,'Mula'),(10525,'magtataas',552,'taas'),(5569,'magulang',17,'gulang'),(2880,'mahaba',2,'haba'),(2397,'mahabaang',31,'haba'),(2492,'mahabang',31,'haba'),(4635,'mahahabang',31,'haba'),(16589,'mahahalagang',31,'halaga'),(13238,'mahahati',212,'hati'),(5224,'mahahati',552,'hati'),(8772,'mahalaga',2,'halaga'),(17632,'mahalagang',31,'halaga'),(16804,'mahalagang',16804,'halaga'),(4540,'mahanap',212,'hanap'),(4991,'mahati',212,'mahati'),(10313,'mahigit',2,'higit'),(12875,'mahigit',212,'higit'),(2205,'mahigit',750,'higit'),(10929,'Mahigit',775,'higit'),(10434,'mahigpit',2,'higpit'),(17901,'mahihirap',2,'hirap'),(17923,'mahirap',2,'hirap'),(12430,'mahuhusay',60,'husay'),(785,'Mahusay',2,'husay'),(10785,'mahusay',60,'husay'),(2538,'maiangkop',212,'angkop'),(8822,'maiba',212,'iba'),(3065,'maibunsod',212,'bunsod'),(4532,'maigrupo',212,'grupo'),(14644,'maiintindihan',552,'intindi'),(4785,'maikli',2,'ikli'),(5052,'maikling',31,'ikli'),(2102,'maiksing',31,'iksi'),(4298,'maikukumpara',552,'kumpara'),(5712,'mailarawan',212,'larawan'),(1837,'mailipat',212,'lipat'),(8692,'maimpluwensyahan',212,'impluwensiya'),(17694,'mainam',40,'inam'),(4572,'maingat',2,'ingat'),(1683,'mainit',2,'init'),(5754,'maintindihan',212,'intindi'),(5970,'maipakita',212,'kita'),(228,'maisaproseso',212,'proseso'),(17826,'maitim',2,'itim'),(17842,'maitim',60,'itim'),(5093,'maiwan',212,'iwan'),(14830,'Majority',5,'Majority'),(4768,'maka-agham',2,'agham'),(11402,'maka-barteran',212,'barteran'),(13514,'makaabot',212,'abot'),(13931,'makabagong',31,'bago'),(14491,'makabagong',31,'bagong'),(8993,'makabuluhang',31,'kabuluhan'),(2576,'makabuo',212,'buo'),(11765,'makakagawa',212,'gawa'),(830,'makakalubog',212,'lubog'),(12210,'makakapagpalaya',212,'laya'),(8200,'makakapagpanatili',212,'tili'),(6905,'makakapagsalamuha',212,'salamuha'),(2557,'makakuha',212,'kuha'),(8787,'makaligtas',212,'ligtas'),(212,'makalipad',212,'lipad'),(15491,'makalipas',212,'lipas'),(8851,'makalkulahin',212,'kalkula'),(4501,'makalumang',31,'luma'),(11588,'makapagbigay',212,'bigay'),(12048,'makapagbuo',212,'buo'),(9380,'makapagdudulot',552,'dulot'),(17712,'makapal',40,'kapal'),(17064,'makapangyarihan',2,'yari'),(12312,'makapangyarihang',31,'yari'),(6041,'makaraan',212,'daan'),(14914,'makasaysayang',31,'saysay'),(18389,'makatagpo',212,'tagpo'),(2724,'makikibahagi',552,'bahagi'),(15586,'makikita',212,'kita'),(3979,'makikita',552,'kita'),(17203,'makikitang',212,'kita'),(8530,'makiling',31,'ikli'),(2971,'makina',2,'makina'),(945,'makina',17,'makina'),(17292,'makita',212,'kita'),(5470,'makitungo',212,'tungo'),(12287,'mala-estadong',31,'mala-estado'),(2050,'mala-matibay',2,'tibay'),(16557,'malabo',60,'labo'),(16295,'malakas',2,'lakas'),(17743,'malakas',40,'lakas'),(2048,'malaki',2,'laki'),(137,'malaking',31,'laki'),(12381,'malalaking',31,'laki'),(14685,'malalaman',552,'laman'),(10091,'malalapit',2,'lapit'),(12329,'malalawak',2,'lawak'),(17237,'malalim',2,'lalim'),(10026,'malamang',2,'lamang'),(17539,'malamang',212,'lamang'),(90,'Malapit',2,'lapit'),(2901,'malawak',2,'lawak'),(17720,'malawak',40,'lawak'),(12454,'malawak',60,'lawak'),(5278,'malawakan',31,'lawak'),(12120,'malayo',2,'layo'),(1840,'malayong',31,'layo'),(2124,'malayuang',31,'layo'),(12247,'maliban',18,'liban'),(269,'maliit',2,'liit'),(17753,'maliit',40,'liit'),(17007,'malikhaing',31,'likha'),(1659,'maliliit',2,'liit'),(85,'malilimutang',85,'limot'),(13883,'malimit',60,'limit'),(320,'malinaw',2,'linaw'),(9247,'malinaw',60,'linaw'),(1952,'maling',31,'mali'),(17461,'maliwanag',60,'liwanag'),(5491,'maliwanag',5491,'liwanag'),(11159,'Maltese',5,'Maltese'),(13974,'malugod',2,'lugod'),(17468,'malusog',2,'lusog'),(16866,'maluwag',2,'luwag'),(10228,'mamahala',212,'bahala'),(15024,'mamamayan',17,'bayan'),(13617,'mamamayan',17,'mamamayan'),(11972,'mamamayang',22,'bayan'),(7690,'mammals',17,'mammals'),(16452,'mamuno',212,'puno'),(5494,'man',6,'man'),(8964,'mana',17,'mana'),(17528,'mananaliksik',17,'salik'),(8974,'mananaliksik',17,'saliksik'),(16697,'mananatili',212,'tili'),(3037,'manatili',212,'tili'),(16635,'mandirigma-hari',17,'digma-hari'),(6631,'mangibabaw',212,'babaw'),(2735,'manlalakbay',17,'lakbay'),(3380,'Mannikka',5,'Mannikka'),(17201,'mansanas',17,'mansanas'),(15628,'manunulat',17,'sulat'),(4323,'manunuso',17,'manunuso'),(2715,'manupaktura',17,'manupaktura'),(14845,'manuskrito',17,'manuskrito'),(6603,'maobserbahan',212,'obserba'),(5972,'mapa',17,'mapa'),(4685,'mapabuti',212,'buti'),(9167,'mapag-aralan',212,'aral'),(4707,'mapaglarawang',85,'larawan'),(17971,'mapagmahal',212,'mahal'),(3714,'mapalaki',212,'laki'),(1762,'mapanatili',212,'tili'),(15381,'mapang-utos',2,'utos'),(8884,'mapanlinlang',212,'linlang'),(18345,'mapapanatilihing-buhay',212,'tili-buhay'),(17510,'maputing',31,'puti'),(1325,'Mar',5,'Mar'),(8161,'marahil',1605,'dahil'),(10345,'marami',2,'dami'),(4636,'maramihang',31,'dami'),(1359,'maraming',31,'dami'),(2358,'Maraming',46,'Dami'),(15000,'Maraming',94,'Dami'),(17287,'maraming',1605,'dami'),(12140,'Maranda',5,'Maranda'),(5018,'marapat',60,'dapat'),(4759,'marhen',17,'marhen'),(18084,'maringal',2,'ringal'),(17312,'marinig',212,'dinig'),(4449,'marinong',22,'marino'),(12332,'maritime',17,'maritime'),(16519,'marka',17,'marka'),(10892,'market',10,'market'),(521,'Marque',5,'Marque'),(2223,'Marso',5,'Marso'),(6918,'Mary',5,'Mary'),(17752,'mas',2,'mas'),(4902,'mas',165,'mas'),(775,'mas',775,'mas'),(10009,'masa',17,'masa'),(552,'Masasabing',552,'sabi'),(16402,'Maserfield',5,'Maserfield'),(17702,'maskulado',72,'maskulado'),(14299,'Masorete',5,'Masorete'),(14331,'Masoretic',5,'Masoretic'),(14240,'masoretic',10,'masoretic'),(1051,'Massachusetts',5,'Massachusetts'),(18061,'masukat',212,'sukat'),(4518,'masyadong',41,'masyado'),(5938,'mata',17,'mata'),(17735,'mata',72,'mata'),(10000,'mataas',2,'mataas'),(2257,'mataas',2,'taas'),(17718,'mataas',27,'taas'),(2076,'Matagal',60,'tagal'),(9258,'matagpuan',212,'tagpo'),(698,'matagumpay',2,'tagumpay'),(60,'matagumpay',60,'tagumpay'),(17966,'matakasan',212,'takas'),(6915,'matang',22,'mata'),(17700,'matangkad',72,'tangkad'),(1462,'matapos',212,'tapos'),(16990,'matatagpuan',62,'tagpo'),(8070,'matatagpuan',552,'matatagpuan'),(7638,'matatagpuan',552,'tagpo'),(11860,'matatagumpay',552,'tagumpay'),(12897,'matawag',212,'tawag'),(3018,'matayog',2,'tayog'),(5715,'Matematika',5,'Matematika'),(17385,'matematika',17,'matematika'),(18255,'matematikal',17,'matematikal'),(9756,'material',10,'material'),(3565,'material',17,'material'),(6095,'materyal',17,'materyal'),(11389,'materyales',17,'materyales'),(7540,'mateyal',17,'mateyal'),(10055,'matibag',212,'tibag'),(355,'matibay',2,'tibay'),(3775,'matikas',750,'tikas'),(16116,'matulig',212,'tulig'),(3041,'maubos',212,'ubos'),(6251,'maunawaan',212,'awa'),(3641,'Maurice',5,'Maurice'),(9564,'mawala',212,'wala'),(3043,'mawalan',212,'wala'),(3193,'mawawala',552,'wala'),(14672,'mawawalan',552,'wala'),(17707,'may',94,'may'),(386,'may',386,'may'),(11392,'mayaman',2,'yaman'),(10628,'mayayamang',31,'yaman'),(15206,'Maykapal',5,'Maykapal'),(14897,'maykapangyarihan',17,'yari'),(101,'Mayo',5,'Mayo'),(6544,'mayroon',386,'mayroon'),(1682,'mayroong',1682,'mayroon'),(13704,'Maza',5,'Maza'),(6059,'McCarty',5,'McCarty'),(6056,'McLeod',5,'McLeod'),(1058,'ME',57,'ME'),(3859,'Mealea',5,'Mealea'),(14785,'Mechilta',5,'Mechilta'),(12290,'Medieval',5,'Medieval'),(12356,'medieval',10,'medieval'),(9159,'medikal',2,'medikal'),(1093,'Mediterranean',5,'Mediterranean'),(12633,'Medium',5,'Medium'),(980,'Medyo',2,'Medyo'),(12284,'medyo',60,'medyo'),(2253,'medyor',2,'medyor'),(8491,'meiosis',10,'meiosis'),(1890,'mekanismo',17,'mekanismo'),(9362,'mekanismong',22,'mekanismo'),(9143,'melanogaster',10,'melanogaster'),(5522,'Mendel',5,'Mendel'),(5727,'Mendelian',5,'Mendelian'),(14929,'mensahe',17,'mensahe'),(9399,'mensaheng',22,'mensahe'),(16098,'Meqabyan',5,'Meqabyan'),(16440,'Mercia',5,'Mercia'),(2940,'merkado',17,'merkado'),(16175,'Mersyano',5,'Mersyano'),(12083,'Mesopotamia',5,'Mesopotamia'),(4160,'metabolism',10,'metabolism'),(4176,'metabolism-first',10,'metabolism-first'),(9524,'method',10,'method'),(2156,'metro',17,'metro'),(12827,'Metropolis',5,'Metropolis'),(13274,'metropolitan',10,'metropolitan'),(13055,'metropolitan',17,'metropolitan'),(13053,'metropolitane',10,'metropolitane'),(4341,'mga',27,'mga'),(4338,'mga',72,'mga'),(288,'mga',165,'mga'),(8800,'mga',8800,'mga'),(13102,'miasto',10,'miasto'),(13659,'Michigan',5,'Michigan'),(10981,'Middle',5,'Middle'),(14752,'Midrash',5,'Midrash'),(17817,'midya',17,'midya'),(5170,'mikrobyolohiya',17,'mikrobyolohiya'),(5152,'mikroorganismo',17,'mikroorganismo'),(13044,'Milan',5,'Milan'),(289,'militar',2,'militar'),(584,'militar',17,'militar'),(325,'military',2,'military'),(568,'military',10,'military'),(12039,'millennium',10,'millennium'),(12421,'million',17,'million'),(2164,'milya',17,'milya'),(10951,'milyon',17,'milyon'),(2207,'milyong',22,'milyon'),(7510,'milyong',22,'milyong'),(375,'minadaling',41,'dali'),(7890,'minana',37,'mana'),(9291,'minanang',3124,'mana'),(674,'mine-laying',10,'mine-laying'),(676,'mine-sweeping',10,'mine-sweeping'),(13067,'minor',2,'minor'),(5327,'minsan',96,'minsan'),(1714,'minsan',163,'minsan'),(1864,'minsang',1,'minsan'),(14504,'minsa’y',9788,'minsan_ay'),(95,'minuto',17,'minuto'),(14777,'Mishnah',5,'Mishnah'),(12309,'mismo',12309,'mismo'),(176,'mismong',176,'mismo'),(18089,'Miss',5,'Miss'),(2269,'Missile',5,'Missile'),(15202,'misteryo',17,'misteryo'),(1729,'mitsero',17,'mitsero'),(559,'miyembro',17,'miyembro'),(3899,'moat',10,'moat'),(1145,'Mobery',5,'Mobery'),(6546,'modelo',2,'modelo'),(4115,'modelo',17,'modelo'),(2702,'modelong',22,'modelo'),(6154,'modelong',31,'modelo'),(424,'modernong',31,'moderno'),(8136,'modipikasyon',17,'modipikasyon'),(470,'Moffett',5,'Moffett'),(14615,'Moises',5,'Moises'),(5316,'molecular',2,'molecular'),(8848,'molecular',10,'molecular'),(4155,'molekulang',22,'molekula'),(7290,'molekyul',2,'molekyul'),(6069,'molekyul',17,'molekyul'),(7197,'molekyular',2,'molekyular'),(6302,'molekyular',17,'molekyular'),(5215,'Monera',5,'Monera'),(10403,'Monetary',5,'Monetary'),(5647,'monghe',17,'monghe'),(5412,'monophyletic',10,'monophyletic'),(5921,'Morgan',5,'Morgan'),(4547,'morpolohiya',17,'morpolohiya'),(17888,'Morrison',5,'Morrison'),(15420,'Moses',5,'Moses'),(9496,'mosyon',17,'mosyon'),(9151,'mouse',10,'mouse'),(652,'movements',10,'movements'),(1809,'mph',1809,'mph'),(14334,'MT',57,'MT'),(4073,'mukha',17,'mukha'),(17446,'mukhang',22,'mukha'),(444,'mula',24,'mula'),(522,'mula',96,'mula'),(1405,'mula_London',187,'mula_London'),(415,'mula_sa',24,'mula_sa'),(3029,'muli',96,'muli'),(1455,'muling',1,'muli'),(6354,'Mullis',5,'Mullis'),(917,'multa',17,'multa'),(2673,'multi-functional',10,'multi-functional'),(1610,'Multi-Intelligence',5,'Multi-Intelligence'),(9243,'multigenic',10,'multigenic'),(9981,'multiple',10,'multiple'),(4391,'mulusko',17,'mulusko'),(1423,'mundo',17,'mundo'),(3476,'mungkahi',17,'mungkahi'),(10124,'munisipyo',17,'munisipyo'),(1752,'mura',2,'mura'),(1790,'murang',41,'mura'),(9153,'Mus',5,'Mus'),(9154,'musculus',10,'musculus'),(17019,'musika',17,'musika'),(17307,'musikero',17,'musikero'),(11008,'Muslim',5,'Muslim'),(8379,'mutageneic',10,'mutageneic'),(8383,'mutagenic',10,'mutagenic'),(8274,'mutasyon',17,'mutasyon'),(9333,'mutasyong',22,'mutasyon'),(5939,'mutation',10,'mutation'),(9319,'mutatsyon',17,'mutatsyon'),(11024,'M’Zab',5,'M’Zab'),(8421,'na',3,'na'),(17695,'na',5,'na'),(250,'na',6,'na'),(4351,'na',17,'na'),(17427,'na',18,'na'),(17744,'na',22,'na'),(7707,'na',27,'na'),(40,'na',40,'na'),(17531,'na',60,'na'),(17706,'na',386,'na'),(17350,'na-master',8,'master'),(3129,'naaabot',62,'abot'),(18409,'naaakit',60,'akit'),(433,'naasagawa',8,'gawa'),(4669,'naayos',8,'ayos'),(15056,'nabahirang',2801,'bahid'),(17391,'nabanggit',8,'banggit'),(4513,'nabibigyan',62,'bigay'),(667,'Nabibilang',62,'Bilang'),(1598,'nabigasyon',2,'nabigasyon'),(6923,'nabubuhay',62,'buhay'),(7251,'nabubuo',62,'buo'),(8119,'nabubuong',561,'buo'),(16720,'nabuhay',8,'buhay'),(5163,'nabuo',8,'buo'),(2074,'nabuo',8,'nabuo'),(12432,'nabuong',8,'buo'),(6271,'nadiskubreng',2801,'diskubre'),(11559,'nadodoble',62,'doble'),(1519,'NAES',57,'NAES'),(11508,'nag-aakit',62,'aakit'),(15843,'nag-alok',8,'alok'),(6437,'nag-aral',8,'aral'),(9542,'nag-iiba-iba',62,'iba'),(6909,'nag-iimpluwensya',62,'impluwensya'),(16069,'nag-iisang',41,'isa'),(9949,'nag-iiwan',62,'iwan'),(9991,'nag-o-orbit',8,'orbit'),(5006,'nag-uri',8,'uri'),(7701,'nag-uudyok',62,'udyok'),(7763,'nag-uugat',62,'ugat'),(6286,'nag-uugnay',62,'ugnay'),(7527,'nagaayos',62,'ayos'),(8846,'nagagamit',62,'gamit'),(8763,'nagaganap',62,'ganap'),(4651,'nagamit',8,'gamit'),(11484,'nagawa',8,'gawa'),(12047,'nagawang',41,'gawa'),(8675,'nagbabago',62,'bago'),(7100,'nagbabahagi',62,'bahagi'),(9970,'nagbibigay',62,'bigay'),(4660,'nagbigay',8,'bigay'),(9510,'nagbigay',8,'nagbigay'),(15264,'nagbigay-diin',8,'bigay-diin'),(15073,'nagbigkas',8,'bigkas'),(908,'nagbukas',8,'bukas'),(14277,'nagbunga',8,'bunga'),(9767,'nagco-coalescing',62,'coalescing'),(11487,'nagdaang',8,'daan'),(575,'nagdagdag',8,'dagdag'),(342,'nagdala',8,'dala'),(1541,'nagdala',8,'nagdala'),(10604,'Nagdesisyon',8,'desisyon'),(8405,'nagdudulot',62,'dulot'),(5143,'nagdulot',8,'dulot'),(6861,'naghahalo',62,'halo'),(9177,'naghahanap',62,'hanap'),(6249,'naghangad',60,'hangad'),(13674,'naghihiwalay',62,'hiwalay'),(14034,'nagiging',62,'maging'),(3315,'naging',8,'maging'),(697,'Naging',60,'Maging'),(8640,'nagingibabaw',62,'babaw'),(6881,'nagkakaiba',62,'iba'),(3935,'nagkakaroon',62,'mayroon'),(17314,'nagkakasalungatan',62,'salungat'),(14973,'nagkakasundo',62,'sundo'),(4194,'nagkakasya',62,'kasya'),(12285,'nagkaroon',8,'mayoon'),(350,'nagkaroon',8,'mayroon'),(6364,'nagkataong',2801,'taon'),(6948,'nagkokontrol',62,'kontrol'),(2494,'nagkulang',8,'kulang'),(6394,'nagkulmina',8,'kulmina'),(5863,'naglaganap',8,'ganap'),(6141,'naglahad',8,'lahad'),(10328,'naglalaman',62,'laman'),(5276,'naglathala',8,'lathala'),(6023,'naglimbag',8,'limbag'),(5561,'nagmamana',62,'mana'),(16240,'nagmamartsa',62,'martsa'),(1944,'nagmomodelo',62,'modelo'),(80,'nagmula',8,'mula'),(16738,'nagmula',60,'mula'),(4096,'nagmumungkahi',62,'mungkahi'),(5285,'nagmungkahi',8,'mungkahi'),(18387,'nagnanais',62,'nais'),(6334,'nagpagana',8,'gana'),(10661,'nagpakita',8,'kita'),(5543,'nagpalagay',8,'lagay'),(6219,'nagpaliwanag',8,'liwanag'),(4986,'nagpapahintulot',62,'hintulot'),(16443,'nagpapahintulot',62,'pahintulot'),(3402,'nagpapahiwatig',62,'hiwatig'),(9751,'nagpapahiwatig',62,'pahiwatig'),(10250,'nagpapakilala',62,'kilala'),(16936,'nagpapakita',62,'kita'),(13739,'nagpapalit',62,'palit'),(13805,'nagpapaliwanag',62,'liwanag'),(6709,'nagpaparami',62,'dami'),(962,'nagpapatrolya',62,'patrolya'),(10741,'Nagpapatubo',62,'Tubo'),(14888,'nagpapatuloy',62,'tuloy'),(6157,'nagpares',8,'pares'),(1188,'Nagpatrolyo',8,'patrolyo'),(15702,'nagpatuloy',8,'patuloy'),(8,'nagpatuloy',8,'tuloy'),(2629,'nagpaunlad',8,'unlad'),(2000,'Nagpunyagi',8,'punyagi'),(166,'nagpupunta',62,'punta'),(8090,'nagreresulta',62,'resulta'),(10042,'nagsasabi',62,'nagsasabi'),(10019,'nagsasabi',62,'sabi'),(9763,'nagsasabing',62,'sabi'),(7904,'nagsasalit',62,'salit'),(17166,'nagsasalita',62,'salita'),(10160,'nagsasaya',62,'saya'),(33,'nagsimula',8,'mula'),(4099,'nagsimula',4099,'nmula'),(7529,'nagsisiksik',62,'siksik'),(640,'Nagsisilbi',62,'silbi'),(17960,'nagsisilbing',62,'silbi'),(14523,'nagsisimbolo',62,'simbolo'),(9855,'nagsisimula',62,'mula'),(16799,'nagsulat',8,'sulat'),(8387,'nagsusulong',62,'sulong'),(2961,'nagsusupling',62,'nagsusupling'),(8368,'nagtaas',8,'taas'),(13312,'nagtamo',8,'tamo'),(11514,'nagtataasang',62,'taas'),(7910,'nagtatagal',62,'tagal'),(5985,'nagtataglay',62,'taglay'),(3964,'nagtatago',62,'tago'),(9363,'nagtatangkang',41,'tangka'),(2361,'nagtatrabaho',62,'trabaho'),(1001,'nagtutukod',62,'tukod'),(14922,'nagtuturo',62,'turo'),(5580,'nahahanay',62,'hanay'),(9326,'nahahati',62,'hati'),(10005,'nahanap',8,'hanap'),(5212,'nahati',8,'hati'),(4344,'Nahati',11,'hati'),(9847,'nahuhulog',62,'hulog'),(16086,'nahuli',8,'huli'),(4791,'naibigay',8,'bigay'),(5160,'naibunyag',8,'bunyag'),(13688,'naiiba',62,'iba'),(9417,'naiipon',62,'ipon'),(955,'Nailigtas',8,'Ligtas'),(15662,'Nailimbag',8,'Limbag'),(6231,'naimpluwensyahan',8,'impluwensiya'),(14184,'naisalin',37,'salin'),(15871,'naisama',8,'sama'),(15076,'naisulat',8,'sulat'),(14248,'naisulong',8,'sulong'),(2490,'naitaguyod',8,'taguyod'),(18231,'Naiulit',8,'ulit'),(4250,'Naiuri',8,'uri'),(16509,'naiwang',3124,'iwan'),(11239,'naka-imbak',2,'imbak'),(14705,'Nakaabot',8,'abot'),(15103,'Nakaayon',2,'Ayon'),(13574,'nakaayon',8,'ayon'),(10264,'nakaayos',2,'ayos'),(7484,'nakaayos',8,'ayos'),(7437,'nakaayos',60,'ayos'),(9884,'nakabalot',8,'balot'),(441,'nakabase',2,'nakabase'),(1365,'nakabase',8,'base'),(13821,'nakadepende',2,'depende'),(3180,'Nakadepende',8,'depende'),(1747,'nakadila',2,'dila'),(17487,'nakahanap',8,'hanap'),(10108,'nakahati',8,'hati'),(8633,'nakakapahamak',60,'hamak'),(7170,'nakakaranas',62,'danas'),(17835,'nakakasira',60,'sira'),(1070,'Nakakita',8,'kita'),(3616,'Nakakuha',8,'kuha'),(62,'nakakumpleto',62,'kumpleto'),(881,'nakalagay',8,'lagay'),(9432,'nakalilikha',62,'likha'),(4138,'nakalista',8,'lista'),(16284,'nakamit',8,'kamit'),(1355,'Nakapagbigay',8,'bigay'),(9085,'nakapagdulot',8,'dulot'),(10239,'nakapagpalit',8,'palit'),(17659,'nakapagproproseso',62,'proseso'),(1672,'nakapalibot',2,'libot'),(9707,'nakapaligid',2,'ligid'),(16575,'nakapaligid',60,'ligid'),(16542,'nakapaligid',60,'nakapaligid'),(2655,'Nakapatala',8,'tala'),(1534,'nakaraang',31,'daan'),(14945,'nakasaad',8,'saad'),(113,'nakasakay',2,'sakay'),(14562,'nakasalin',8,'salin'),(16073,'nakasipi',2,'sipi'),(14151,'Nakasulat',2,'sulat'),(14607,'nakasulat',60,'sulat'),(3589,'nakatagong',31,'tago'),(16189,'nakatakas',60,'takas'),(12476,'nakatakda',8,'takda'),(930,'nakatikim',8,'tikim'),(16757,'nakatuon',2,'tuon'),(11787,'nakatuon',8,'tuon'),(1737,'naka_suspendido',2,'naka_suspendido'),(3596,'nakikilala',62,'kikila'),(7675,'nakikisalo',62,'salo'),(169,'nakikita',62,'kita'),(17474,'nakikitang',62,'kita'),(9902,'nakikitang',561,'kita'),(3574,'nakikitang',2801,'kita'),(14209,'nakilala',8,'kilala'),(16830,'nakimakapanaig',8,'naig'),(12386,'nakinabang',8,'kinabang'),(10536,'nakipagsundo',8,'sundo'),(14494,'nakitang',8,'kita'),(17375,'nakitang',2801,'kita'),(6255,'nakokontrol',62,'kontrol'),(7787,'nakokopya',8,'kopya'),(4152,'nakuhaan',8,'kuha'),(1967,'Nalalapat',62,'lapat'),(18243,'nalaman',8,'laman'),(16371,'nalunod',8,'lunod'),(5104,'Nalutas',8,'lutas'),(5546,'namalagi',8,'lagi'),(16290,'namamaga',62,'maga'),(10173,'namamahala',62,'bahala'),(17000,'namamalagi',62,'lagi'),(11329,'namamalayan',62,'malay'),(7190,'namamana',62,'mana'),(6441,'namamanang',2801,'mana'),(895,'naman',6,'naman'),(7651,'namana',8,'mana'),(4110,'namang',751,'naman'),(116,'namatay',8,'patay'),(16770,'namumunong',31,'puno'),(16468,'namuno',8,'puno'),(8173,'nananatili',62,'tili'),(2993,'nanatili',8,'tili'),(16070,'nanatili',60,'tili'),(10953,'nanatiling',8,'tili'),(4418,'nanatiling',41,'tili'),(14848,'nang',3,'nang'),(4533,'nang',60,'nang'),(240,'nang',96,'nang'),(8505,'nang',751,'nang'),(5817,'nangaling',8,'galing'),(6803,'nangangahulugan',62,'hulog'),(15351,'nangangahulugang',62,'hulog'),(17118,'nangangahulugang',561,'hulog'),(2968,'nangangailangan',62,'kailangan'),(3399,'nangatwiran',8,'katwiran'),(15315,'nanggaling',8,'galing'),(12122,'nanggaling_sa',24,'galing_sa'),(17149,'nanghuhusga',62,'husga'),(6625,'nangingibabaw',62,'babaw'),(131,'Nangyari',8,'yari'),(9317,'nangyayari',62,'yari'),(10611,'nang_buo',10611,'nang_buo'),(9504,'nang_hiwalay',60,'nang_hiwalay'),(10682,'nang_mabuti',60,'nang_buti'),(9565,'nang_tuluyan',60,'nang_tuloy'),(10935,'naninirahan',62,'tira'),(14008,'naninirahang',561,'tira'),(2542,'Naniniwala',62,'tiwala'),(14522,'naniniwalang',41,'tiwala'),(10942,'nanirahan',8,'tira'),(6430,'naobserbahan',8,'obserba'),(17534,'naonatal',10,'naonatal'),(6657,'naoobserbahan',62,'obserba'),(947,'napadpad',8,'padpad'),(14505,'napag-isipan',8,'isip'),(9820,'napagdadaanan',62,'daan'),(11456,'napagdesisyonan',8,'desisyon'),(9573,'napagkitaang',41,'kita'),(10681,'napagmarkahan',8,'marka'),(9688,'napakabata',82,'bata'),(5794,'napakaimportante',82,'importante'),(2072,'napakalaking',1419,'laki'),(2983,'napakalawak',82,'lawak'),(13650,'napakaliit',82,'liit'),(12135,'napakalumang',1419,'luma'),(2657,'napakaraming',31,'dami'),(10417,'Napakinabangan',8,'pakinabang'),(16197,'napalitan',8,'palit'),(11290,'napansin',8,'pansin'),(15579,'napantay',8,'pantay'),(3888,'napapaligiran',62,'ligid'),(8761,'napapalitan',62,'palit'),(13161,'napapansin',62,'pansin'),(14427,'napatunayan',8,'tunay'),(4938,'napili',37,'pili'),(981,'napinsala',8,'pinsala'),(13047,'Naples',5,'Naples'),(7086,'nararanasan',62,'danas'),(13532,'Nararapat',62,'dapat'),(3759,'naratiobong',31,'naratiobo'),(164,'naririnig',62,'dinig'),(469,'NAS',57,'NAS'),(1323,'Nasa',24,'Nasa'),(10965,'nasa',284,'sa'),(11308,'nasabi',8,'nasabi'),(13607,'nasabing',8,'sabi'),(498,'Nasagawa',8,'gawa'),(517,'nasailalim',8,'lalim'),(153,'nasakupang',31,'sakop'),(7277,'nasasali',62,'sali'),(592,'nasa_Akron',187,'nasa_Akron'),(10549,'nasa_bansa',24,'nasa_bansa'),(11178,'nasa_baybayin',24,'sa_baybay'),(11953,'nasa_estima',10611,'nasa_estima'),(11279,'nasa_kagubatan',24,'nasa_gubat'),(12820,'nasa_kalagitnaan',24,'nasa_gitna'),(9681,'nasa_likod',24,'nasa_likod'),(14454,'nasa_loob',24,'nasa_loob'),(10946,'nasa_oases',24,'sa_oases'),(4003,'nasa_pagitan',24,'nasa_pagitan'),(839,'nasira',8,'sira'),(17945,'nasisiraan',62,'sira'),(16275,'nasisiyahan',62,'siya'),(5767,'natagpuan',8,'tagpo'),(3474,'Natanggap',8,'tanggap'),(4787,'natatangi',62,'tangi'),(12296,'natatanging',31,'tangi'),(7275,'natatanging',561,'tangi'),(4377,'natin',699,'natin'),(10630,'nations',10,'nations'),(3272,'natirang',8,'tira'),(10078,'natitibag',62,'tibag'),(5254,'natitirang',62,'natitirang'),(5320,'natuklasan',8,'tuklas'),(4846,'Naturae',5,'Naturae'),(3607,'natural',2,'natural'),(5687,'Natural',5,'Natural'),(9407,'natural',10,'natural'),(5613,'natural',60,'natural'),(14942,'naturang',31,'tuod'),(9249,'natutukoy',62,'tukoy'),(11492,'natutumbasan',62,'tumbas'),(2065,'naubusan',8,'ubos'),(16080,'nauna',2,'una'),(7009,'nauna',8,'una'),(6428,'nauna',60,'una'),(15790,'naunang',31,'una'),(14255,'Naunang',41,'una'),(5127,'nauugnay',62,'ugnay'),(1509,'Naval',5,'Naval'),(659,'naval',10,'naval'),(613,'Navy',5,'Navy'),(939,'nawala',8,'wala'),(3256,'Nawalaan',8,'Wala'),(951,'nawalan',8,'wala'),(3782,'Nawasak',8,'wasak'),(17049,'nawawala',62,'wala'),(12907,'nayon',17,'nayon'),(1046,'NC',57,'NC'),(9878,'nebula',10,'nebula'),(13970,'negosyanteng',22,'negosyante'),(11396,'neighbouring',10,'neighbouring'),(9131,'nematode',10,'nematode'),(5288,'Neomura',5,'Neomura'),(8607,'neutral',10,'neutral'),(73,'New',5,'New'),(1068,'Newfoundland',5,'Newfoundland'),(13461,'Newport',5,'Newport'),(13472,'Newry',5,'Newry'),(14879,'ng',1,'ng'),(17711,'ng',2,'ng'),(3,'ng',3,'ng'),(4899,'ng',17,'ng'),(17275,'ng',37,'ng'),(6547,'ng',40,'ng'),(10962,'ng',53,'ng'),(3508,'ng',1498,'ng'),(1412,'ngalan',17,'ngalan'),(15320,'ngangahulugang',62,'hulog'),(4378,'ngayon',96,'ngayon'),(2498,'ngayong',1,'ngayon'),(78,'Ngunit',18,'Ngunit'),(17767,'ng_mga',17,'ng_mga'),(4891,'ng_mga',18,'ng_mga'),(165,'ng_mga',165,'ng_mga'),(13154,'ni',6,'ni'),(3223,'ni',1498,'ni'),(7163,'Nigeria',5,'Nigeria'),(14345,'niggud',10,'niggud'),(2693,'nila',699,'nila'),(9721,'nilagay',37,'nilagay'),(3393,'nilalaman',17,'laman'),(15951,'nilalaman',62,'laman'),(15151,'nilalang',17,'nilalang'),(15910,'Nilalarawan',8,'Larawan'),(9157,'Nilalayon',62,'layon'),(41,'nilalayong',41,'layon'),(9340,'nilang',642,'nila'),(3454,'nilayon',8,'layon'),(10783,'nililinang',62,'linang'),(8044,'nililipat',62,'lipat'),(1011,'Nilunod',8,'lunod'),(6117,'nina',2195,'nina'),(17951,'ninanais',60,'nais'),(16531,'ninuno',17,'ninuno'),(5431,'ninuno',17,'nuno'),(5407,'ninunong',22,'nuno'),(6116,'Niresolba',8,'Resolba'),(540,'nironda',8,'ronda'),(1916,'nito',97,'nito'),(5587,'nitong',160,'nito'),(15189,'Nix',5,'Nix'),(3233,'niya',924,'niya'),(17950,'niyang',6436,'niya'),(1038,'NJ',57,'NJ'),(17913,'nobela',17,'nobela'),(17877,'nobelang',22,'nobela'),(1385,'Nobyembre',5,'Nobyembre'),(10954,'nomadic',10,'nomadic'),(4749,'Nomen',5,'Nomen'),(4935,'nomenklatura',17,'nomenklatura'),(4980,'nomenklaturang',22,'nomenklatura'),(4739,'nomina',10,'nomina'),(5337,'non-celluar',10,'non-celluar'),(5322,'non-cellular',10,'non-cellular'),(2672,'non-rigid',10,'non-rigid'),(11015,'non-Sunni_Muslims',10,'non-Sunni_Muslims'),(17722,'noo',72,'noo'),(11403,'noon',96,'noon'),(718,'Noong',1,'Noo'),(1,'Noong',1,'Noon'),(5683,'noong',1,'noong'),(12445,'Noong',96,'Noon'),(16477,'noOng',96,'noOng'),(3216,'noong',3216,'noon'),(3648,'noong',3648,'noon'),(1664,'Normal',2,'Normal'),(15185,'Norman',5,'Norman'),(1335,'North',5,'North'),(1499,'Northrop',5,'Northrop'),(16476,'Northumbian',5,'Northumbian'),(16744,'Northumbrian',5,'Northumbrian'),(16851,'Northumbrians',5,'Northumbrians'),(1062,'Nova',5,'Nova'),(9822,'nuclear',10,'nuclear'),(7553,'nucleosomes',10,'nucleosomes'),(6213,'nucleotide',10,'nucleotide'),(6161,'nucleotides',10,'nucleotides'),(9771,'numbering',17,'numbering'),(7761,'numero',17,'numero'),(7182,'nutrisyon',17,'nutrisyon'),(749,'o',18,'o'),(11654,'O',57,'O'),(10748,'Oases',5,'Oases'),(10891,'oat',10,'oat'),(10847,'oats',10,'oats'),(17076,'obhetibong',22,'obhetibo'),(9557,'object',10,'object'),(9458,'objects',10,'objects'),(12191,'obligasyon',17,'obligasyon'),(6746,'obserbasyon',17,'obserba'),(4241,'obserbasyon',17,'obserbasyon'),(9501,'obserbasyong',22,'obserbasyon'),(9507,'obserbasyong',22,'obserbasyong'),(9491,'observation',10,'observation'),(11377,'Obsidian',5,'Obsidian'),(11311,'Obsidians',5,'Obsidians'),(9633,'of',10,'of'),(3699,'ogival',10,'ogival'),(594,'Ohio',5,'Ohio'),(10335,'oil',10,'oil'),(15976,'Old',5,'Old'),(10905,'olive',10,'olive'),(10771,'olive',17,'olive'),(1378,'Olympics',5,'Olympics'),(781,'on-board',10,'on-board'),(1034,'One',5,'One'),(5086,'Onggo',5,'Onggo'),(5039,'onggo',17,'onggo'),(15295,'Only',5,'Only'),(1532,'ooperasyon',17,'ooperasyon'),(663,'operasyon',17,'operasyon'),(18077,'opinyon',17,'opinyon'),(10273,'opisyal',17,'opisyal'),(9485,'optical',10,'optical'),(11187,'Oran',5,'Oran'),(2300,'oras',17,'oras'),(2429,'orbita',17,'orbita'),(2419,'orbital',10,'orbital'),(5365,'Order',5,'Order'),(1321,'Oregon',5,'Oregon'),(4156,'organiko',17,'organiko'),(9091,'organimso',17,'organimso'),(645,'organisasyon',17,'organisasyon'),(7599,'organism',17,'organism'),(4209,'organismo',17,'organismo'),(6551,'organismong',22,'organismo'),(8470,'organismong',8470,'organismo'),(1523,'orihinal',2,'orihinal'),(11454,'orihinal',60,'orihinal'),(14584,'Orthodox',5,'Orthodox'),(9275,'orthologous',10,'orthologous'),(14712,'Ortodoksia',5,'Ortodoksia'),(6051,'Oswald',5,'Oswald'),(16436,'Oswiu',5,'Oswiu'),(17282,'out-of-true',10,'out-of-true'),(10047,'outflows',10,'outflows'),(11561,'output',10,'output'),(11634,'output',17,'output'),(4329,'oviparous',10,'oviparous'),(11475,'O’',5,'O’'),(11504,'O’Flaherty',5,'O’Flaherty'),(2467,'P-791',5,'P-791'),(10967,'pa',2,'pa'),(995,'pa',6,'pa'),(5500,'paano',163,'paano'),(17380,'paaralan',17,'aral'),(791,'pababa',2,'baba'),(5078,'pabalik',212,'balik'),(2385,'pabilog',2,'bilog'),(18246,'pabor',2,'pabor'),(3872,'pader',17,'pader'),(11278,'pag',1834,'pag'),(10480,'pag-aakit',17,'akit'),(3485,'pag-aalinlangan',17,'alangan'),(2894,'pag-aangat',17,'angat'),(1816,'pag-aanunsyo',17,'anunsyo'),(1892,'pag-aanunsyo',17,'nunsyo'),(1391,'pag-aanunsyong',22,'anunsyo'),(4490,'pag-aaral',17,'aral'),(332,'pag-aatake',17,'atake'),(3672,'pag-aayos',17,'ayos'),(17588,'pag-analisa',17,'analisa'),(2955,'pag-angat',17,'angat'),(8737,'pag-asa',17,'pag-asa'),(976,'pag-atake',17,'atake'),(566,'pag-eensayo',17,'ensayo'),(236,'pag-eespiya',17,'espiya'),(573,'pag-ensayo',17,'ensayo'),(10476,'pag-iba-ibahin',212,'iba'),(17844,'pag-iisip',17,'isip'),(2040,'pag-ikot',17,'ikot'),(10485,'pag-imbak',17,'imbak'),(14961,'pag-intindi',17,'intindi'),(6304,'pag-unawa',17,'awa'),(2617,'pag-unlad',17,'unlad'),(2915,'pag-upo',17,'upo'),(9343,'pag-uugali',17,'ugali'),(7386,'pag-uulit',17,'ulit'),(1953,'pag-uunawa',17,'unawa'),(2345,'pag-uunlad',17,'unlad'),(4486,'pag-uuri',17,'uri'),(15385,'pag-uusapan',212,'usap'),(15480,'pag-uusig',17,'usig'),(16653,'paganismo',17,'paganismo'),(16633,'pagano',17,'pagano'),(16754,'paganong',22,'pagano'),(11085,'pagbaba',17,'baba'),(14895,'pagbabadya',17,'badya'),(8372,'pagbabago',17,'bago'),(5604,'pagbabagong',22,'bago'),(15958,'pagbabahagi',17,'bahagi'),(11736,'pagbabalik',17,'balik'),(13478,'pagbabalik-tanaw',17,'balik-tanaw'),(10492,'pagbabawas',17,'bawas'),(17616,'pagbagay',17,'bagay'),(7022,'pagbago',17,'bago'),(15496,'pagbagsak',17,'bagsak'),(8460,'pagbalik',17,'balik'),(15356,'pagbasa',17,'basa'),(10457,'pagbawas',17,'bawas'),(10638,'pagbawas',17,'pagbawas'),(4969,'pagbibigay',17,'bigay'),(15108,'pagbibigay-halaga',17,'bigay-halaga'),(13139,'pagbibigay-pansin',17,'bigay-pansin'),(1695,'pagbubuhat',17,'buhat'),(2128,'pagbubuhat',17,'pagbubuhat'),(2141,'pagbubuo',17,'buo'),(3294,'pagbuhat',17,'buhat'),(2963,'pagbuhat',17,'pagbuhat'),(302,'pagbuo',17,'buo'),(18304,'pagdaan',17,'daan'),(2148,'pagdadala',17,'dala'),(1612,'pagdala',17,'dala'),(3067,'pagdaragdag',17,'dagdag'),(157,'pagdating',17,'dating'),(11563,'pagdoble',17,'doble'),(11582,'pagdodoble',17,'doble'),(6200,'pagduplika',17,'duplika'),(628,'pageskort',17,'eskort'),(10818,'paggagawa',17,'gawa'),(4226,'paggalaw',17,'galaw'),(2761,'paggalugad',17,'galugad'),(8397,'paggambala',17,'gambala'),(4900,'paggamit',3,'gamit'),(327,'paggamit',17,'gamit'),(4854,'paggamit',212,'gamit'),(16396,'paggamot',17,'gamot'),(313,'paggawa',17,'gawa'),(15784,'paggigiit',17,'giit'),(5295,'paggrupo-grupo',17,'grupo'),(3383,'pagguhit',17,'guhit'),(8886,'paghahambing',17,'hambing'),(8860,'paghahambing',22,'hambing'),(16626,'paghahara',17,'hara'),(18416,'paghahari',17,'hari'),(7958,'paghahati',17,'hati'),(13180,'paghahati-hati',17,'hati'),(8480,'paghaluin',17,'halo'),(4493,'paghanap',17,'hanap'),(17095,'paghatol',17,'hatol'),(16421,'paghihirap',17,'hirap'),(6202,'paghihiwalay',17,'hiwalay'),(3050,'paghila',17,'hila'),(9917,'paghulog',17,'hulog'),(711,'pagiging',60,'maging'),(13491,'pagiging',62,'maging'),(8855,'pagitan',17,'pagitan'),(10663,'pagkabawi',17,'bawi'),(16700,'pagkabirhen',17,'birhen'),(16924,'pagkabuhay',17,'buhay'),(9356,'pagkahati',17,'hati'),(11337,'pagkain',17,'kain'),(11302,'pagkaka-utang',17,'utang'),(17618,'pagkakaakit',17,'akit'),(17581,'pagkakaakit-akit',17,'akit'),(6396,'pagkakaayos',17,'ayos'),(3385,'pagkakahanay',17,'hanay'),(6489,'pagkakaiba',17,'iba'),(13118,'pagkakaiba-iba',17,'iba'),(8591,'pagkakaibang',22,'iba'),(3683,'pagkakaisa',17,'isa'),(5012,'pagkakakilanlan',17,'kilala'),(11293,'pagkakalakal',17,'kalakal'),(4403,'pagkakamali',17,'mali'),(8269,'pagkakamaling',22,'mali'),(16171,'pagkakanulo',17,'kanulo'),(17418,'pagkakapantay-pantay',17,'pantay'),(13372,'pagkakaroon',17,'mayoon'),(13845,'pagkakaroon',17,'mayroon'),(7265,'pagkakasunod',17,'sunod'),(7579,'pagkakasunod-sunod',17,'pagkakasunod-sunod'),(4303,'pagkakasunod-sunod',17,'sunod'),(9512,'pagkakataon',17,'pagkakataon'),(828,'pagkakataon',17,'taon'),(4461,'pagkakatay',17,'katay'),(3623,'pagkakatugma',17,'tugma'),(5699,'pagkakatulad',17,'tulad'),(6888,'pagkakaugnay',17,'ugnay'),(3395,'pagkakayos',17,'ayos'),(17788,'pagkalalaki',17,'laki'),(10902,'pagkalat',17,'kalat'),(16579,'pagkalito',17,'lito'),(10684,'pagkamayabong',22,'yabong'),(13735,'pagkamayorya',17,'mayorya'),(7861,'pagkamit',17,'kamit'),(11417,'pagkaroon',17,'mayroon'),(15528,'pagkasara',17,'sara'),(8422,'pagkasira',17,'sira'),(16430,'pagkatalo',17,'talo'),(17254,'pagkatao',17,'tao'),(1776,'pagkatapos',96,'tapos'),(3290,'pagkawala',17,'wala'),(16647,'pagkawasak',17,'wasak'),(13365,'pagkilala',17,'kilala'),(13753,'pagkilalang',22,'kilala'),(1797,'pagkilos',17,'kilos'),(3722,'pagkonekta',17,'konekta'),(2038,'pagkubkob',17,'kubkob'),(7179,'pagkuha',17,'kuha'),(2237,'pagkumpleto',17,'kumpleto'),(8435,'pagkumpuni',17,'kumpuni'),(9354,'paglago',17,'lago'),(9057,'paglahok',17,'lahok'),(6362,'paglaki',17,'laki'),(6688,'paglalahad',17,'lahad'),(197,'paglalakbay',17,'lakbay'),(4484,'paglalarawan',17,'larawan'),(2949,'paglalayag',17,'layag'),(9683,'paglikha',17,'likha'),(665,'pagliligtas',17,'ligtas'),(10737,'paglilinang',17,'linang'),(8926,'paglilipat',17,'lipat'),(2653,'pagliliwaliw',17,'liwaliw'),(10799,'paglinang',17,'linang'),(2304,'paglipad',17,'lipad'),(3791,'paglipas',17,'lipas'),(8094,'paglipat',17,'lipat'),(4188,'paglitaw',17,'litaw'),(1117,'paglubog',17,'lubog'),(7115,'pagmama',17,'mama'),(5531,'pagmamana',17,'mana'),(7308,'pagmamanang',22,'mana'),(7570,'pagmamanang',22,'pagmamanang'),(788,'pagmamaneho',17,'maneho'),(9081,'pagmamanipula',17,'manipula'),(1829,'pagmamasid',17,'masid'),(3157,'pagmamatyag',2,'matyag'),(1594,'pagmamatyag',17,'matyag'),(8203,'pagmamay-ari',17,'ari'),(5728,'pagmana',17,'mana'),(16992,'pagmumuni-muni',17,'muni'),(16514,'pagmuna',17,'muna'),(16918,'pagmuni-muni',17,'muni'),(16484,'pagmuno',17,'puno'),(17964,'pagnanais',17,'nais'),(5025,'pagpanaog',17,'panaog'),(8880,'pagpapabuti',17,'buti'),(8098,'pagpapadala',17,'dala'),(6696,'pagpapahayag',17,'hayag'),(4615,'pagpapakilala',17,'kilala'),(260,'pagpapalago',17,'lago'),(3196,'pagpapalawak',17,'lawak'),(230,'pagpapalipad',17,'lipad'),(3928,'pagpapalit',17,'palit'),(4735,'pagpapangalan',17,'ngalan'),(6784,'pagpaparami',17,'dami'),(6211,'pagpaparis',17,'paris'),(5588,'pagpaparisan',17,'paris'),(4704,'pagpapasok',212,'pasok'),(17,'pagpapatakbo',17,'takbo'),(15473,'Pagpapatapon',5,'tapon'),(16618,'pagpapatatag',17,'tatag'),(2795,'pagpapatuloy',17,'tuloy'),(3663,'pagpigil',17,'pigil'),(8770,'pagpili',17,'pili'),(2574,'pagpopondo',17,'pondo'),(154,'pagrerecord',17,'record'),(10442,'pagreresultuhan',62,'resulta'),(6309,'pagsabog',17,'sabog'),(3252,'pagsalakay',17,'salakay'),(3601,'pagsalihin',37,'sali'),(16846,'pagsalungat',17,'salungat'),(8073,'pagsamahin',17,'sama'),(13329,'pagsasaalang-alang',22,'alang'),(44,'pagsasagawa',17,'gawa'),(2615,'pagsasaliksik',17,'saliksik'),(14244,'pagsasalin',17,'salin'),(14206,'pagsasaling',22,'salin'),(15178,'pagsasanay',17,'anay'),(1491,'pagsasanay',17,'sanay'),(4898,'pagsasanay',18,'sanay'),(12351,'pagsasarili',17,'sarili'),(3299,'pagsasayang',22,'sayang'),(5898,'pagsikat',17,'sikat'),(245,'pagsimula',17,'mula'),(8413,'pagsira',17,'sira'),(6379,'pagsisikap',17,'sikap'),(2302,'pagsusubok',17,'subok'),(16404,'Pagsusulat',17,'sulat'),(4683,'pagsusumikap',17,'sikap'),(2212,'pagsusuri',17,'suri'),(10427,'pagtaas',17,'taas'),(414,'pagtagumpay',17,'tagumpay'),(4698,'pagtanggal',17,'tanggal'),(11548,'Pagtataas',17,'taas'),(5935,'pagtatalik',17,'talik'),(15174,'pagtatama',17,'tama'),(18086,'pagtatanghal',17,'tanghal'),(13818,'pagtatanging',22,'tangi'),(4232,'pagtatangkang',17,'tangka'),(11896,'pagtatapos',17,'tapos'),(17412,'pagtingin',17,'tingin'),(11306,'pagtitinda',17,'tinda'),(13626,'pagtitipon',17,'tipon'),(35,'pagtrabaho',17,'trabaho'),(3801,'pagtubog',17,'tubog'),(8444,'pagtugma',17,'tugma'),(5894,'pagtuklas',17,'tuklas'),(2138,'pagtukoy',17,'tukoy'),(2917,'pagtulog',17,'tulog'),(16859,'pagtutol',17,'pagtutol'),(14492,'pagtutuklas',17,'tuklas'),(4135,'pagtutuklas',212,'tuklas'),(15169,'pagtuturo',17,'turo'),(2230,'pagwawakas',17,'wakas'),(15171,'pagwiwika',17,'wika'),(3456,'pagyamanin',17,'yaman'),(2044,'pahabang',31,'haba'),(8092,'pahalang',2,'halang'),(8940,'pahalang',31,'halang'),(3467,'pahayag',17,'hayag'),(4880,'pahilerang',41,'hilera'),(16163,'pahina',17,'pahina'),(4761,'pahinang',22,'hina'),(1464,'pahingang',22,'hinga'),(5176,'pahiwalay',60,'hiwalay'),(13580,'paiba-iba',2,'iba'),(13558,'pakahulugan',17,'hulog'),(14893,'pakahulugang',22,'hulog'),(8796,'pakikibagay',17,'bagay'),(8874,'pakikikapwa',17,'kapwa'),(371,'pakikipagbaka',17,'pakibaka'),(12398,'pakikipagpalitan',17,'palit'),(18309,'pakinabang',17,'kinabang'),(6892,'paksa',17,'paksa'),(1504,'palatapormang',22,'palataporma'),(9465,'palayo',60,'layo'),(9529,'palayo_sa',24,'palayo_sa'),(3900,'palibot-bambang',2,'libot-bambang'),(1679,'palikpik',17,'palikpik'),(16359,'paliparin',24,'lipad'),(11379,'palitan',37,'palit'),(1415,'Palm',5,'Palm'),(10768,'palm',10,'palm'),(2475,'Palmdale',5,'Palmdale'),(17034,'pam-ibabaw',2,'babaw'),(2647,'pamamahala',17,'bahala'),(6376,'pamamaraan',17,'daan'),(17892,'pamana',17,'mana'),(10500,'pamantayan',17,'pantay'),(3911,'pamapang',17,'pamapang'),(17927,'pamilya',17,'pamilya'),(4583,'pamilyar',2,'pamilyar'),(5615,'pamimili',212,'bili'),(1901,'paminsan',96,'minsan'),(8636,'paminsan-minsan',96,'minsan'),(9316,'Paminsang',41,'Minsan'),(9124,'pampaalsa',2,'pampaalsa'),(23,'pampasahero',2,'pasahero'),(67,'pampasaherong',31,'pasahero'),(686,'pampersonal',2,'pampersonal'),(16660,'pampublikong',31,'publiko'),(10502,'pamumuhay',17,'buhay'),(15210,'pamumukaw',17,'pukaw'),(5848,'pamumuno',17,'puno'),(9123,'panaderya',17,'panaderya'),(719,'panahon',17,'panahon'),(15503,'panahong',17,'panahon'),(2493,'panahong',22,'panahon'),(17426,'pananaliksik',17,'salik'),(1495,'pananaliksik',17,'saliksik'),(13504,'pananaw',17,'tanaw'),(17269,'pananaw',156,'tanaw'),(15045,'panatiko',17,'panatiko'),(402,'Pandagat',2,'dagat'),(346,'Pandaigdigang',5,'daigdig'),(416,'pandaigdigang',31,'daigdig'),(3789,'pandarambong',17,'darambong'),(3741,'pandekorasyong',31,'dekorasyon'),(15219,'pandiwang',22,'diwa'),(751,'pang',751,'pa'),(11164,'pang',751,'pang'),(17795,'pang-aapi',17,'api'),(12054,'pang-agrikultura',2,'agrikultura'),(12599,'pang-agrikulturang',31,'agrikultura'),(10974,'pang-apat',135,'apat'),(13625,'pang-bayang',31,'bayan'),(11544,'pang-ekonomiyang',31,'ekonomiya'),(13577,'pang-estadong',31,'estado'),(13748,'pang-lungsod',2,'lungsod'),(1995,'pang-negosyo',2,'negosyo'),(17704,'pang-paa',72,'paa'),(17763,'panga',11,'panga'),(7184,'pangalagang',22,'ngalan'),(3540,'pangalan',17,'ngalan'),(4989,'pangalan',17,'pangalan'),(4892,'pangalan',750,'ngalan'),(11910,'pangalawa',135,'dalawa'),(6839,'Pangalawang',46,'dalawa'),(3026,'pangangailangan',17,'kailangan'),(16730,'pangangaral',17,'aral'),(13105,'pangbayan',31,'bayan'),(13337,'panghan',2,'panghan'),(1435,'panghimpapawid',2,'himpapawid'),(6679,'pangingibabaw',17,'babaw'),(15156,'Panginoon',5,'Panginoon'),(17279,'pangit',2,'pangit'),(14975,'pangkabuuan',17,'buo'),(14985,'pangkabuuang',31,'buo'),(1629,'pangkalahatan',159,'lahat'),(6795,'pangkalahatang',31,'lahat'),(16586,'pangkalahatang',94,'lahat'),(11224,'pangkalakalan',2,'kalakal'),(12148,'pangkalakalan',159,'kalakal'),(18403,'pangkaraniwang',31,'karaniwan'),(438,'pangkat',17,'pangkat'),(17862,'pangkultural',2,'kultura'),(395,'pangmatang',31,'mata'),(3331,'pangmilitar',2,'militar'),(1584,'pangunahing',31,'una'),(16737,'pangunahing',41,'una'),(14682,'pangungusap',17,'usap'),(10459,'pangungutang',22,'utang'),(5811,'panguring',31,'uri'),(12343,'pangyayari',17,'yari'),(4927,'panibagong',31,'bago'),(11349,'panimbang',17,'timbang'),(4952,'panimulang',31,'mula'),(2061,'panimulang',31,'panimulang'),(13950,'paninirahan',212,'tira'),(15106,'paniniwala',17,'paniniwala'),(5555,'paniniwala',17,'tiwala'),(15118,'paniniwalang',22,'tiwala'),(17871,'paniwalang',22,'tiwala'),(2178,'panlabas',2,'labas'),(12376,'panlabing-anim',46,'labing-anim'),(10320,'Panlabing-apat',135,'labing-apat'),(15221,'panlahatan',17,'lahat'),(17855,'panlahi',17,'lahi'),(17097,'panlasa',17,'lasa'),(1883,'panlibangan',2,'libang'),(1895,'panloob',2,'loob'),(15751,'pansimbahan',2,'simba'),(299,'pansin',17,'pansin'),(9397,'pansinin',37,'pansin'),(17129,'pantukoy',2,'tukoy'),(2753,'panukala',17,'panukala'),(1380,'panukatan',17,'sukat'),(8240,'panuntunan',17,'tunton'),(15786,'Papa',5,'Papa'),(6945,'papaano',163,'paano'),(11870,'papalapit',2,'lapit'),(5452,'papalitan',552,'palit'),(5666,'papel',17,'papel'),(1406,'papuntang',1406,'punta'),(649,'papunta_sa',24,'punta_sa'),(3621,'papuri',17,'puri'),(3713,'para',18,'para'),(2878,'paraan',17,'daan'),(271,'paraan',17,'paraan'),(14237,'paraang',22,'daan'),(678,'parachute',10,'parachute'),(4732,'paralelong',31,'paralelo'),(9523,'parallax',10,'parallax'),(8225,'paramutasyon',17,'paramutasyon'),(3705,'parang',18,'parang'),(3463,'parangalan',37,'dangal'),(5415,'paraphyletic',10,'paraphyletic'),(21,'para_sa',21,'para_sa'),(3362,'para_sa',1768,'para_sa'),(3089,'pare-parehong',3089,'pareho'),(15545,'parehas',750,'parehas'),(7714,'pareho',750,'pareho'),(3962,'parehong',3089,'pareho'),(7344,'pares',17,'pares'),(16748,'pari',17,'pari'),(10414,'Paris',5,'Paris'),(6705,'paris',17,'paris'),(14591,'Pariseo',5,'Pariseo'),(3054,'parisukat',2,'parisukat'),(1235,'Parmaribo',5,'Parmaribo'),(8567,'parte',17,'parte'),(10393,'partida',17,'partida'),(1886,'partikular',2,'partikular'),(8983,'partikular',17,'partikular'),(119,'pasahero',17,'pasahero'),(14721,'pasalaysay',2,'salaysay'),(14579,'pasalaysay',17,'salaysay'),(14739,'pasalitang',41,'salita'),(14601,'pasalysay',2,'salysay'),(2244,'pase',17,'pase'),(13864,'pasidhiin',17,'sidhi'),(2934,'pasilidad',17,'pasilidad'),(3452,'pasilyo',17,'pasilyo'),(4010,'pasukan',17,'pasok'),(3924,'pasukang',22,'pasok'),(14599,'pasulat',2,'sulat'),(3120,'patag',2,'patag'),(3449,'patakaran',17,'patakaran'),(16875,'patakaran',17,'takad'),(10397,'patakarang',22,'patakaran'),(11827,'Pataliputra',5,'Pataliputra'),(13321,'patent',10,'patent'),(7769,'patern',17,'patern'),(1179,'pati',27,'pati'),(978,'pating',17,'pating'),(14343,'patinig',17,'patinig'),(11832,'Patna',5,'Patna'),(454,'patrol',10,'patrol'),(626,'patrolya',17,'patrolya'),(2683,'patrolyang',31,'patrolya'),(16985,'patula',2,'tula'),(17045,'patulang',31,'tula'),(4808,'Patuloy',2,'tuloy'),(4852,'patuloy',60,'tuloy'),(4148,'patungkol_sa',1768,'tungkol_sa'),(50,'patungong_Atlantiko',50,'tungo_Atlantiko'),(2727,'patungong_North',187,'tungo_North'),(824,'pauwing',31,'uwi'),(6346,'PCR',57,'PCR'),(5706,'pea',10,'pea'),(6815,'pea',17,'pea'),(16450,'Peada',5,'Peada'),(17931,'Pecola',5,'Pecola'),(3752,'pediments',10,'pediments'),(149,'pelikula',17,'pelikula'),(16245,'Penda',5,'Penda'),(16516,'Pendo',5,'Pendo'),(12337,'penomena',17,'penomena'),(8080,'penomenon',17,'penomenon'),(15423,'Pentateuch',5,'Pentateuch'),(14413,'Pentatyuk',5,'Pentatyuk'),(2496,'pera',17,'pera'),(11697,'perimeter',17,'perimeter'),(11127,'period',10,'period'),(902,'pero',18,'pero'),(3660,'perpekto',2,'perpekto'),(17745,'perpektong',17,'perpekto'),(15075,'perpektong',41,'perpekto'),(745,'Persephone',5,'Persephone'),(4239,'personal',2,'personal'),(15087,'perspective',10,'perspective'),(10322,'petroleum',10,'petroleum'),(5834,'petsa',17,'petsa'),(16091,'pf',1809,'pf'),(3864,'Phanom',5,'Phanom'),(6832,'PHENOMENON',5,'PHENOMENON'),(6608,'phenotype',10,'phenotype'),(8598,'phenotypes',10,'phenotypes'),(8889,'phenotypic',10,'phenotypic'),(15609,'Philo',5,'Philo'),(4673,'Philosophica',5,'Philosophica'),(3867,'Phimai',5,'Phimai'),(3011,'photovoltaic',10,'photovoltaic'),(5449,'PhyloCode',5,'PhyloCode'),(5393,'phylogenetic',10,'phylogenetic'),(5361,'Phylum',5,'Phylum'),(11175,'pieds-noirs',10,'pieds-noirs'),(3805,'pigura',17,'pigura'),(4041,'pilar',17,'pilar'),(9009,'piliin',37,'pili'),(13591,'piling',31,'pili'),(16961,'pilosopikal',2,'pilosopikal'),(4245,'pilosopo',17,'pilosopo'),(17057,'pilosopong',22,'pilosopo'),(554,'piloto',17,'piloto'),(1240,'Pinaandar',62,'andar'),(8659,'pinag-aaralan',3142,'aral'),(10998,'pinag-uuri-uri',3142,'uri'),(13396,'pinag-uusapan',1651,'usap'),(16472,'pinagbagsak',3142,'bagsak'),(15595,'pinagbuklod-buklod',3142,'buklod'),(9530,'pinaggalingang',37,'galing'),(7934,'pinaghahalo',1651,'halo'),(7887,'pinaghalong',7578,'halo'),(4863,'pinaghiwalay',3142,'hiwalay'),(3142,'pinagkakatiwalaan',3142,'tiwala'),(17997,'pinagkasunduan',3142,'sundo'),(14269,'pinagkukunan',3142,'kuha'),(12966,'pinagmamay-ari',3142,'ari'),(4117,'pinagmulan',3142,'mula'),(12031,'pinagpapraktisan',3142,'praktis'),(4910,'pinagsama',3142,'sama'),(7578,'pinagsamang',7578,'pinagsamang'),(18149,'pinagsamang',7578,'sama'),(1651,'Pinagsasama',1651,'sama'),(5565,'pinagtibay',3142,'tibay'),(6667,'pinaiikli',62,'ikli'),(11285,'pinaka',3972,'pinaka'),(4413,'pinaka-dakilang',1419,'dakila'),(8945,'pinaka-karaniwang',1419,'karaniwan'),(16901,'pinaka-malalim',2,'lalim'),(16567,'pinaka-memorable',82,'memorable'),(8868,'pinaka-tama',3972,'tama'),(9693,'pinakabata',82,'bata'),(16841,'pinakaimportante',82,'importante'),(13035,'pinakaimportanteng',82,'importante'),(15748,'pinakakapansin-pansin',82,'pansin'),(5430,'pinakakaraniwang',1419,'karaniwan'),(11602,'pinakalumang',1419,'luma'),(12112,'pinakalumang',12112,'luma'),(17356,'pinakamaagang',82,'aga'),(14072,'pinakamababa',82,'baba'),(10976,'pinakamababang',1419,'baba'),(11318,'pinakamahusay',82,'husay'),(3949,'pinakamalaki',82,'laki'),(11922,'pinakamalaking',82,'laki'),(1419,'pinakamalaking',1419,'laki'),(7499,'pinakamalaking',1419,'pinakamalaking'),(13710,'pinakamaliit',82,'liit'),(15149,'pinakamalinaw',15149,'linaw'),(707,'pinakamataas',82,'taas'),(11996,'pinakapansin',82,'pansin'),(7288,'pinakapareho',7288,'pareho'),(6404,'pinakasaligang',3972,'saligan'),(12940,'pinakasapat',82,'sapat'),(15453,'pinakasikat',82,'sikat'),(7802,'pinakasimpleng',1419,'simple'),(82,'pinakaspektakular',82,'spektakular'),(8917,'pinakatama',3972,'tama'),(3972,'pinakatemplo',3972,'templo'),(9835,'pinalakas',82,'lakas'),(14568,'pinalaki',37,'laki'),(2078,'pinalitan',37,'palit'),(4896,'pinalitan',160,'palit'),(15191,'pinamagatang',3124,'pamagat'),(13615,'pinamumunuan',62,'puno'),(11116,'pinanakamalalaking',1419,'laki'),(10255,'pinananatili',62,'tili'),(10138,'pinangalan',37,'ngalan'),(5341,'pinangalanang',3124,'ngalan'),(16717,'pinangalangang',3124,'ngalan'),(11370,'pinanggagalingan',62,'galing'),(9612,'pinanggalingang',3124,'galing'),(10877,'pinangkakalakal',62,'kalakal'),(11218,'pinangkakalakalan',62,'kalakal'),(14858,'pinaniniwalaan',62,'tiwala'),(17244,'pinaniwalaang',561,'tiwala'),(12086,'pinapahiwatig',62,'hiwatig'),(9842,'pinapakawalan',62,'wala'),(9460,'pinapakita',62,'kita'),(17815,'pinapalaganap',62,'ganap'),(1900,'pinapalapad',62,'lapad'),(1947,'pinapalipad',62,'lipad'),(9442,'pinapasok',62,'pasok'),(13597,'pinapatakbo',62,'takbo'),(10840,'pinapatubong',561,'tubo'),(10836,'pinapayabong',62,'yabong'),(11339,'pinapayagang',41,'payag'),(11374,'pinapayagang',561,'payag'),(1029,'Pinatakbo',8,'takbo'),(16365,'pinatay',37,'patay'),(15607,'Pinatutunayan',62,'tunay'),(6350,'pinaunlad',37,'unlad'),(1201,'Pines',5,'Pines'),(9065,'Pinili',37,'Pili'),(3232,'pinilit',8,'pilit'),(10222,'pinipili',62,'pili'),(2349,'Pinlano',37,'Plano'),(2246,'Pinopondohan',62,'Pondo'),(1003,'pinsala',17,'pinsala'),(18181,'pinsan',17,'pinsan'),(3814,'pinto',17,'pinto'),(17710,'pinuno',3,'puno'),(15877,'pinuntahan',37,'punta'),(16383,'pinutol',37,'putol'),(6179,'pisikal',2,'pisikal'),(18037,'pisikal',17,'pisikal'),(3850,'Pithu',5,'Pithu'),(14263,'pitumpung',46,'pitumpu'),(10579,'plane',10,'plane'),(2763,'planeta',17,'planeta'),(2398,'plano',17,'plano'),(2478,'Plant',5,'Plant'),(9118,'planta',10,'planta'),(4267,'planta',17,'planta'),(5080,'Plantae',5,'Plantae'),(588,'plantang',22,'planta'),(4830,'Plantarum',5,'Plantarum'),(5707,'plants',10,'plants'),(1616,'plataporma',17,'plataporma'),(2281,'Platform',5,'Platform'),(2728,'Pole',5,'Pole'),(12366,'poleis',10,'poleis'),(10437,'policy',10,'policy'),(11789,'politikal',17,'politikal'),(8323,'polymerases',10,'polymerases'),(8263,'polymerisasyion',17,'polymerisasyion'),(5438,'polyphyletic',10,'polyphyletic'),(2067,'pondo',17,'pondo'),(13847,'pook-pamilihan',17,'pook-bili'),(9088,'popular',2,'popular'),(8655,'populasyon',17,'populasyon'),(7372,'porma',17,'porma'),(12568,'pormal',2,'pormal'),(5442,'pormal',17,'pormal'),(11782,'pormasyon',17,'pormasyon'),(17326,'porsiyento',17,'porsiyento'),(6322,'posible',2,'posible'),(3927,'posibleng',31,'posible'),(2140,'posibleng',176,'posible'),(3149,'posisyon',17,'posisyon'),(16917,'positibong',31,'positibo'),(4142,'postulated',10,'postulated'),(2120,'potensyal',17,'potensyal'),(9838,'potential',10,'potential'),(18187,'potograpikong',22,'potograpiko'),(1877,'potograpiya',17,'potograpiya'),(2280,'Powered',5,'Powered'),(13132,'powiat',10,'powiat'),(10233,'PPA',57,'PPA'),(10076,'praksyon',17,'praksyon'),(3132,'praktikal',2,'praktikal'),(2737,'Pranses',5,'Pranses'),(17368,'pre-Socratic',17,'pre-Socratic'),(3849,'Preah',5,'Preah'),(2204,'premyong',22,'premyo'),(10208,'Prepekto',5,'Prepekto'),(10224,'Presidente',5,'Presidente'),(10181,'presidente',17,'presidente'),(10551,'presidenteng',22,'presidente'),(13028,'Presidential',5,'Presidential'),(942,'pressure',10,'pressure'),(13449,'Preston',5,'Preston'),(10429,'presyo',17,'presyo'),(3360,'presyon',17,'presyon'),(13143,'prezydent',10,'prezydent'),(2573,'pribadong',31,'pribado'),(5027,'prinsipyong',22,'prinsipyo'),(507,'privateer',10,'privateer'),(10112,'probinsya',17,'probinsya'),(12778,'probinsyal',2,'probinsyal'),(13197,'probinsyang',22,'probinsya'),(5095,'problema',17,'problema'),(5033,'problematikong',31,'problema'),(6261,'produksyon',17,'produksyon'),(5611,'produkto',17,'produkto'),(11223,'produktong',22,'produkto'),(17748,'profile',72,'profile'),(311,'programa',17,'programa'),(1514,'programang',22,'programa'),(6383,'Project',5,'Project'),(2109,'Projects',5,'Projects'),(5210,'prokaryotes',10,'prokaryotes'),(8317,'proofreading',10,'proofreading'),(14969,'propesiya',17,'propesiya'),(15145,'propesiyang',22,'propesiya'),(14619,'propeta',17,'propeta'),(10002,'proporsyon',17,'proporsyon'),(11682,'proporsyonal',17,'proporsyonal'),(6014,'proseso',17,'proseso'),(8087,'prosesong',22,'proseso'),(11625,'proteksyon',17,'proteksyon'),(11610,'proteksyong',22,'proteksyon'),(11637,'protektadong',31,'protektado'),(11054,'Protestant',5,'Protestant'),(15914,'Protestante',5,'Protestante'),(15838,'protestante',17,'protestante'),(15903,'Protestanteng',5,'Protestante'),(15251,'Protestanteng',126,'Protestante'),(5995,'protina',17,'protina'),(8293,'protinang',22,'protina'),(5202,'Protista',5,'Protista'),(5256,'protists',10,'protists'),(4158,'protocells',10,'protocells'),(5089,'Protoctista',5,'Protoctista'),(5188,'Protophyta',5,'Protophyta'),(5178,'Protosoa',5,'Protosoa'),(9698,'protostar',10,'protostar'),(2347,'prototipo',17,'prototipo'),(2306,'prototipong',31,'prototipo'),(10334,'proven',10,'proven'),(10167,'Provincial',5,'Provincial'),(2103,'proyekto',17,'proyekto'),(2514,'proyektong',22,'proyekto'),(14875,'prumeserba',92,'preserba'),(5944,'prutas',17,'prutas'),(16101,'Psalm',5,'Psalm'),(16107,'Psalter',5,'Psalter'),(5861,'publiko',17,'publiko'),(1402,'publisidad',17,'publisidad'),(12728,'Pueblo',5,'Pueblo'),(4439,'pugita',17,'pugita'),(4293,'pulang',31,'pula'),(12169,'pulitikal',2,'pulitikal'),(9617,'pulso',17,'pulso'),(16672,'pumalit',92,'palit'),(92,'pumalo',92,'palo'),(9775,'pumapailanglang',62,'pumapailanglang'),(18318,'pumapalit',92,'palit'),(8458,'pumapalya',62,'palya'),(8040,'pumapasailalim',62,'lalim'),(8821,'pumapayag',60,'payag'),(872,'pumatrolya',92,'patrolya'),(1147,'Pumatrolyo',92,'Patrolyo'),(10566,'pumayag',60,'payag'),(803,'pumigil',92,'pigil'),(10507,'pumirma',92,'pirma'),(11091,'pumupunta',62,'punta'),(17671,'pundasyon',17,'pundasyon'),(7511,'pundasyong',22,'pundasyong'),(6741,'Punnet',5,'Punnet'),(3342,'puno',2,'puno'),(5395,'puno',17,'puno'),(7303,'punong',31,'puno'),(11117,'punong-lungsod',17,'puno-lungsod'),(3947,'punto',17,'punto'),(6826,'purple',2,'purple'),(7034,'purple',17,'purple'),(6475,'puti',2,'puti'),(17937,'puti',17,'puti'),(10554,'Putin',5,'Putin'),(7036,'putin',10,'putin'),(5937,'puting',31,'puti'),(18330,'puwersa',17,'puwersa'),(5711,'pwedeng',176,'pwede'),(8050,'pyesa',17,'pyesa'),(17372,'Pythagoras',5,'Pythagoras'),(17382,'Pythagorean',5,'Pythagorean'),(4320,'quadrupeds',10,'quadrupeds'),(11767,'quadruple',17,'quadruple'),(11712,'Quantity',5,'Quantity'),(1348,'Quillayute',5,'Quillayute'),(14581,'Rabbi',5,'Rabbi'),(879,'radar',17,'radar'),(9742,'radiation',10,'radiation'),(9912,'radio',10,'radio'),(8407,'radyasyon',17,'radyasyon'),(145,'radyo',17,'radyo'),(10706,'rami',17,'dami'),(9224,'randomization',10,'randomization'),(4994,'rango',17,'rango'),(17973,'rasistang',31,'rasista'),(10978,'rate',10,'rate'),(17403,'ratio',10,'ratio'),(7025,'ratios',10,'ratios'),(1850,'RC',57,'RC'),(3988,'Reach',5,'Reach'),(9824,'reactions',10,'reactions'),(9540,'rebolusyon',17,'rebolusyon'),(3975,'rebulto',17,'rebulto'),(14822,'Receptus',5,'Receptus'),(1275,'Recife',5,'Recife'),(671,'reconnaissance',10,'reconnaissance'),(10449,'record',10,'record'),(2658,'record',17,'record'),(3702,'redented',10,'redented'),(15323,'reed',10,'reed'),(15562,'reglong',22,'reglo'),(9054,'regulasyon',17,'regulasyon'),(8526,'rehiyon',17,'rehiyon'),(12781,'rehiyunal',2,'rehiyunal'),(5968,'rekombinasyon',17,'rekombinasyon'),(6206,'rekonstruksyon',17,'rekonstruksyon'),(15228,'rekord',17,'rekord'),(7119,'relatibo',2,'relatibo'),(16661,'relihiyon',17,'relihiyon'),(1847,'remote',10,'remote'),(7406,'replikasyon',17,'replikasyon'),(10398,'reporma',17,'reporma'),(15836,'repormasyon',17,'repormasyon'),(3513,'representasyon',17,'representasyon'),(4333,'reptile',10,'reptile'),(13025,'republika',17,'republika'),(10411,'rescheduling',10,'rescheduling'),(2108,'Research',5,'Research'),(660,'research',10,'research'),(10323,'reserves',10,'reserves'),(12180,'residente',17,'residente'),(3609,'resina',17,'resina'),(9486,'resolusyon',10,'resolusyon'),(502,'Resolute',5,'Resolute'),(2081,'resort',17,'resort'),(6071,'responsable',2,'responsable'),(1627,'responsibilidad',17,'responsibilidad'),(2929,'restawran',17,'restawran'),(15214,'resulta',17,'resulta'),(18235,'resultang',22,'resulta'),(4702,'retoriko',17,'retoriko'),(13456,'Reyna',5,'Reyna'),(13283,'Reyno',5,'Reyno'),(6266,'ribosomes',10,'ribosomes'),(1161,'Richmond',5,'Richmond'),(483,'rin',6,'rin'),(4345,'rin',8,'rin'),(8061,'ring',751,'rin'),(7820,'ring',751,'ring'),(13360,'Ripon',5,'Ripon'),(532,'ripple',10,'ripple'),(13407,'rito',484,'rito'),(17258,'rito,',484,'rito,'),(6277,'RNA',57,'RNA'),(16975,'Robert',5,'Robert'),(3638,'Roma',5,'Roma'),(11039,'Roman',5,'Roman'),(15965,'Romano',5,'Romano'),(17696,'Romano',284,'Romano'),(12461,'Romanong',126,'Romano'),(13042,'Rome',5,'Rome'),(6138,'Rosalind',5,'Rosalind'),(4064,'rosettes',10,'rosettes'),(10602,'Rosoboronexport',5,'Rosoboronexport'),(11952,'Rostovtzeff',5,'Rostovtzeff'),(2027,'rotorcraft',10,'rotorcraft'),(16718,'Rumwold',5,'Rumwold'),(3865,'Rung',5,'Rung'),(12189,'rural',2,'rural'),(2665,'Russia',5,'Russia'),(12146,'ruta',17,'ruta'),(11668,'s',1809,'s'),(4346,'sa',6,'sa'),(17270,'sa',17,'sa'),(18,'sa',18,'sa'),(17689,'sa',1406,'sa'),(163,'saan',163,'saan'),(7266,'sabay',212,'sabay'),(16379,'Sabi',212,'Sabi'),(11665,'sabihin',37,'sabi'),(16210,'sabi_ni',1768,'sabi_ni'),(9126,'Saccharomyces',5,'Saccharomyces'),(13427,'sadyang',176,'sadya'),(4113,'sagisag',17,'sagisag'),(12099,'Sahara',5,'Sahara'),(11150,'saka',27,'saka'),(12345,'Sakai',5,'Sakai'),(47,'sakay',17,'sakay'),(9188,'sakit',17,'sakit'),(14775,'saklaw',2,'saklaw'),(801,'saklaw',17,'saklaw'),(12484,'salapi',17,'salapi'),(14911,'salaysay',17,'salaysay'),(18016,'salik',17,'salik'),(5773,'saliksik',17,'saliksik'),(15064,'Salita',5,'Salita'),(4637,'salita',17,'salita'),(4746,'salitang',22,'salita'),(15446,'Salmo',5,'Salmo'),(16761,'salungat',17,'salungat'),(4534,'sama-sama',60,'sama'),(6756,'sama-samang',41,'sama'),(1979,'samakatuwid',18,'samakatuwid'),(6638,'samantalang',6638,'samantala'),(14412,'Samaritan',5,'Samaritan'),(353,'sampung',46,'sampu'),(3836,'Samré',5,'Samré'),(866,'sangay',17,'sangay'),(16711,'sanggol',2,'sanggol'),(8776,'sanhi',17,'sanhi'),(1313,'Santa',5,'Santa'),(1268,'Sao',5,'Sao'),(8741,'sapalarang',31,'palad'),(7971,'sapalarang',41,'palad'),(2778,'sapat',2,'sapat'),(1005,'sapilitang',41,'pilit'),(15379,'sarado',2,'sarado'),(3724,'saradong',31,'sarado'),(5528,'sari-saring',31,'sari'),(3494,'sarili',17,'sarili'),(4458,'sariling',31,'sarili'),(5116,'sariling',156,'sarili'),(2307,'sasakyan',17,'sakay'),(1434,'sasakyang',22,'sakay'),(2620,'sasakyang',22,'sasakyan'),(5217,'sasapit',552,'sapit'),(3842,'Say',5,'Say'),(4081,'sayawan',17,'sayaw'),(14674,'saysay',17,'saysay'),(11977,'sa_Alexandria',187,'sa_Alexandria'),(11032,'sa_Algeria',187,'sa_Algeria'),(1263,'sa_Amapá',187,'sa_Amapá'),(1372,'sa_Athens',24,'sa_Athens'),(16154,'sa_baba',24,'sa_baba'),(16241,'sa_bahay',24,'sa_bahay'),(397,'sa_baybayin',24,'sa_baybay'),(1640,'sa_Bristol',187,'sa_Bristol'),(13755,'sa_California',187,'sa_California'),(1152,'sa_Caribbean',187,'sa_Caribbean'),(1246,'sa_costa',24,'sa_costa'),(93,'sa_daungan',24,'sa_daungan'),(183,'sa_dulo',24,'sa_dulo'),(263,'sa_Estados',187,'sa_Estados'),(12081,'sa_Europa',187,'sa_Europa'),(1073,'sa_Europe',187,'sa_Europe'),(875,'sa_Florida',187,'sa_Florida'),(16187,'sa_gabi',96,'sa_gabi'),(3268,'sa_Germany',24,'sa_Germany'),(16071,'sa_Ge’ez',24,'sa_Ge’ez'),(8903,'sa_gitna',24,'sa_gitna'),(3799,'sa_gusali',24,'sa_gusali'),(1193,'sa_hilaga',24,'sa_hilaga'),(10936,'sa_hilagang',1406,'sa_hilaga'),(3229,'sa_Hindenburg',24,'sa_Hindenburg'),(8166,'sa_ibabaw',24,'sa_babaw'),(8881,'sa_ibabaw',24,'sa_ibabaw'),(14014,'Sa_Idaho',187,'Sa_Idaho'),(2790,'sa_ilalaim',24,'sa_lalaim'),(1738,'sa_ilalim',24,'sa_ilalim'),(1742,'sa_ilalim',24,'sa_lalim'),(13982,'Sa_Illinois',187,'Sa_Illinois'),(13424,'sa_Inglatera',187,'sa_Inglatera'),(12996,'Sa_Italia',187,'Sa_Italia'),(1214,'sa_Jamaica',187,'sa_Jamaica'),(8108,'sa_kabilang_dako',24,'sa_kabila_dako'),(12467,'sa_kalagitnaan',96,'sa_gitna'),(3917,'sa_kanluran',24,'sa_kanluran'),(8071,'sa_kapaligiran',24,'sa_ligid'),(16453,'sa_katimugang',1406,'sa_timog'),(14117,'sa_komunidad',24,'sa_komunidad'),(1907,'sa_labas',24,'sa_labas'),(3254,'sa_London',24,'sa_London'),(2905,'sa_loob',24,'sa_loob'),(1450,'sa_Los_Angeles',187,'sa_Los_Angeles'),(510,'sa_Los_Angheles',510,'sa_Los_Angeles'),(1091,'sa_lugar',24,'sa_lugar'),(12701,'sa_mundo',24,'sa_mundo'),(1160,'sa_NAS',630,'sa_NAS'),(13998,'sa_Nebraska',24,'sa_Nebraska'),(14055,'Sa_Nebraska',187,'Sa_Nebraska'),(14027,'Sa_Ohio',187,'Sa_Ohio'),(24,'sa_pagitan',24,'sa_pagitan'),(3361,'sa_paligid',24,'sa_ligid'),(6300,'Sa_pamamagitan',18,'Sa_pamamagitan'),(958,'sa_pamamagitan_ng',18,'sa_pamamagitan_ng'),(4897,'sa_pamamagitan_ng',37,'sa_pamamagitan_ng'),(4042,'sa_panlabas',24,'sa_labas'),(4052,'sa_panloob',24,'sa_loob'),(335,'sa_Pearl',187,'sa_Pearl'),(13099,'Sa_Poland',187,'Sa_Poland'),(11092,'sa_Pransya',187,'sa_Pransya'),(10943,'sa_Sahara',187,'sa_Sahara'),(1292,'sa_Santa_Cruz',510,'sa_Santa_Cruz'),(3912,'sa_silangan',24,'sa_silang'),(11160,'sa_silangan',24,'sa_silangan'),(11183,'sa_siyudad',24,'sa_siyudad'),(472,'sa_Sunnyvale',187,'sa_Sunnyvale'),(1366,'sa_Switzerland',187,'sa_Switzerland'),(7440,'sa_tabi',24,'sa_tabi'),(167,'sa_teatro',24,'sa_teatro'),(3938,'sa_templo',24,'sa_templo'),(1320,'sa_Tillamook',187,'sa_Tillamook'),(12097,'sa_timog',24,'sa_timog'),(12691,'sa_Tsina',187,'sa_Tsina'),(948,'sa_tubig',24,'sa_tubig'),(728,'sa_US',187,'sa_US'),(630,'sa_US',630,'sa_US'),(1025,'sa_Vigo',187,'sa_Vigo'),(1349,'sa_Washington',187,'sa_Washington'),(11517,'scale',10,'scale'),(12075,'scholarship',10,'scholarship'),(16976,'Schumann',5,'Schumann'),(1063,'Scotia',5,'Scotia'),(13467,'Scotland',5,'Scotland'),(13482,'Scotland',3351,'Scotland'),(14664,'Scroll',5,'Scroll'),(14418,'scrolls',10,'scrolls'),(12372,'Sea',5,'Sea'),(14077,'second',10,'second'),(10290,'sector',10,'sector'),(5807,'Sedgwick',5,'Sedgwick'),(6365,'segmento',17,'segmento'),(6439,'segregasyon',17,'segregasyon'),(2463,'seguridad',17,'seguridad'),(6782,'seksualidad',17,'seksualidad'),(6711,'sekswal',2,'sekswal'),(7706,'sekswal',17,'sekswal'),(7661,'sekswalidad',17,'sekswalidad'),(14696,'seksyon',17,'seksyon'),(10488,'sektor',17,'sektor'),(3276,'selda',17,'selda'),(9408,'selection',10,'selection'),(8696,'seleksyon',17,'seleksyon'),(11822,'Seleucia',5,'Seleucia'),(5149,'selula',17,'selula'),(7968,'selulang',22,'selula'),(7941,'selulang',22,'selulang'),(5843,'sense',10,'sense'),(13892,'sensitibong',31,'sensitibo'),(2412,'sensoryo',2,'sensoryo'),(643,'sentro',2,'sentro'),(1525,'sentro',17,'sentro'),(3996,'sentrong',31,'sentro'),(9371,'senyales',17,'senyales'),(13193,'Seoul',5,'Seoul'),(14449,'Septuagint',5,'Septuagint'),(378,'serbisyo',17,'serbisyo'),(22,'serbisyong',22,'serbisyo'),(11988,'seremonyal',17,'seremonyal'),(14319,'serye',17,'serye'),(932,'seryosong',31,'seryoso'),(17733,'set',40,'set'),(11397,'settlements',10,'settlements'),(2296,'Setyembre',5,'Setyembre'),(1346,'Shelton',5,'Shelton'),(651,'ship',10,'ship'),(9675,'shock',10,'shock'),(3516,'si',3,'si'),(1498,'si',1498,'si'),(17691,'sibilisasyon',11,'sibilisasyon'),(11779,'sibilisasyon',17,'sibilisasyon'),(537,'sibilyang',31,'sibilyan'),(13878,'side',10,'side'),(11189,'Sidi',5,'Sidi'),(6247,'sientipiko',17,'sientipiko'),(14783,'Sifra',5,'Sifra'),(14781,'Sifre',5,'Sifre'),(3545,'siglo',17,'siglo'),(16879,'siguro',1605,'siguro'),(3426,'siklo',17,'siklo'),(18018,'sikolohiya',17,'sikolohiya'),(2550,'sikretong',31,'sikreto'),(16868,'siksik',17,'siksik'),(699,'sila',699,'sila'),(642,'silang',642,'sila'),(4054,'silangan',2,'silangan'),(11850,'silanganang',31,'silanganan'),(16312,'silangang',31,'silangan'),(2913,'silid',17,'silid'),(13535,'simbahan',17,'simba'),(15964,'Simbahang',5,'Simba'),(17639,'simbolo',17,'simbolo'),(17435,'simetriko',2,'simetriko'),(2931,'similar',2,'similar'),(4154,'simpleng',31,'simple'),(14687,'simula',17,'mula'),(600,'Simula',96,'mula'),(2195,'sina',2195,'sina'),(17014,'Sinabi',37,'Sabi'),(5069,'sinabing',8,'sabi'),(1922,'sinasabi',62,'sabi'),(9905,'sinasabing',62,'sabi'),(10338,'sinasabing',561,'sabi'),(11327,'sinasadya',62,'sadya'),(17916,'sinasaloob',62,'loob'),(12316,'sinasama',62,'sama'),(9219,'sinasamantala',62,'samantala'),(3635,'sinaunang',31,'una'),(772,'singil',17,'singil'),(3270,'siniksik',8,'siksik'),(4726,'sinimulan',37,'mula'),(17024,'sining',17,'sining'),(4415,'sintesis',17,'sintesis'),(15196,'sinulat',37,'sulat'),(3816,'Sinundan',8,'Sunod'),(3243,'sinusubukang',41,'subok'),(14443,'sipi',17,'sipi'),(933,'sira',17,'sira'),(4502,'sistema',17,'sistema'),(1558,'sistemang',22,'sistema'),(200,'sistership',10,'sistership'),(924,'siya',924,'siya'),(15572,'siyam',135,'siyam'),(6436,'siyang',6436,'siya'),(15101,'siyentipiko',2,'siyentipiko'),(4128,'siyentipikong',22,'siyentipiko'),(4623,'siyentipikong',31,'siyentipiko'),(10147,'siyudad',17,'siyudad'),(1442,'Sky',5,'Sky'),(2483,'SkyCat',5,'SkyCat'),(1362,'Skyship',5,'Skyship'),(12619,'Small',5,'Small'),(13866,'small',10,'small'),(811,'Sobrang',41,'Sobra'),(2774,'sobrang',2774,'sobra'),(5689,'Society',5,'Society'),(3424,'solar',10,'solar'),(4743,'solo',2,'solo'),(9252,'solong',31,'solo'),(4409,'soolohiya',17,'soolohiya'),(1048,'South',5,'South'),(10747,'Southern',5,'Southern'),(274,'Soviet',5,'Soviet'),(10547,'Soviet-era',5,'Soviet-era'),(2267,'Space',5,'Space'),(1027,'Spain',5,'Spain'),(8810,'speciation',10,'speciation'),(4829,'Species',5,'Species'),(4648,'specifica',10,'specifica'),(9454,'spectroscopic',10,'spectroscopic'),(7665,'spesyalisadong',31,'spesyalisado'),(1251,'squadrons',10,'squadrons'),(6742,'square',10,'square'),(11684,'square',17,'square'),(13418,'St.',57,'St.'),(12916,'Stadt',5,'Stadt'),(12993,'Städte',10,'Städte'),(12199,'Stadtluft',10,'Stadtluft'),(15257,'statement',17,'statement'),(10715,'States’',5,'States’'),(5740,'statistics',17,'statistics'),(9623,'steady',10,'steady'),(9624,'steam',10,'steam'),(16854,'Stenton',5,'Stenton'),(971,'Stessel',5,'Stessel'),(13465,'Stirling',5,'Stirling'),(1102,'Straits',5,'Straits'),(7353,'strand',10,'strand'),(5956,'Sturtevant',5,'Sturtevant'),(13244,'sub-communities',17,'sub-communities'),(12033,'sub-Saharan',17,'sub-Saharan'),(7039,'subalit',18,'subalit'),(17074,'subhetibo',17,'subhetibo'),(17084,'subhetibong',22,'subhetibo'),(426,'submarine',10,'submarine'),(738,'submarines',10,'submarines'),(383,'submarino',17,'submarino'),(2469,'subok',2,'subok'),(1570,'subok',17,'subok'),(8985,'subset',17,'subset'),(3387,'sukat',17,'sukat'),(2743,'sukatan',37,'sukat'),(519,'Sulat',5,'Sulat'),(14258,'sulat',17,'sulat'),(14425,'sulatin',17,'sulat'),(762,'Sumagupa',92,'Sagupa'),(3286,'Sumalpok',92,'Salpok'),(5463,'sumasailalim',552,'lalim'),(5020,'sumasalamin',62,'salamin'),(9669,'sumasalpok',62,'salpok'),(104,'sumilakbo',92,'silakbo'),(1377,'Summer',5,'Summer'),(5908,'sumubok',92,'subok'),(1124,'sumuko',92,'suko'),(5859,'sumulong',92,'sulong'),(171,'sumunod',2,'sunod'),(6241,'sumunod',92,'sunod'),(14996,'sumusunod',17,'sunod'),(4133,'sumusunod',62,'sunod'),(561,'sumusuportang',561,'suporta'),(11007,'Sunni',5,'Sunni'),(910,'sunog',17,'sunog'),(4140,'sunud-sunod',2,'sunod'),(10400,'supportado',2,'supportado'),(9659,'surfaces',10,'surfaces'),(10447,'surplus',10,'surplus'),(3416,'Suryavarman',5,'Suryavarman'),(17330,'susunod',2,'sunod'),(8182,'susunod',62,'sunod'),(13246,'susunod',552,'sunod'),(15791,'Synods',5,'Synods'),(15717,'synods',17,'synods'),(7426,'syntesis',17,'syntesis'),(15485,'Syria',5,'Syria'),(1858,'System',5,'System'),(5120,'system',10,'system'),(4845,'Systema',5,'Systema'),(1511,'Systems',5,'Systems'),(9982,'systems',10,'systems'),(11481,'syudad',17,'syudad'),(7244,'T',57,'T'),(3987,'Ta',5,'Ta'),(2850,'taas',17,'taas'),(1669,'tabako',17,'tabako'),(11673,'tabi',17,'tabi'),(11097,'taga',2,'taga'),(1502,'tagabuo',17,'buo'),(11851,'tagapagmana',17,'mana'),(18122,'tagapagpahiwatig',17,'hiwatig'),(14275,'tagasalin',17,'salin'),(16277,'tagataguyod',17,'taguyod'),(5927,'taglay',2,'taglay'),(5550,'taglay',17,'taglay'),(2607,'tagsibol',17,'sibol'),(10468,'tagumpay',17,'tagumpay'),(3250,'tahimik',2,'tahimik'),(13948,'tahimik',60,'tahimik'),(16728,'taimtim',2,'taimtim'),(17949,'Taimtim',60,'Taimtim'),(900,'takbo',2,'takbo'),(13517,'takdang',31,'takda'),(5061,'taksong',22,'taksong'),(5353,'taksonomikal',17,'taksonomo'),(5486,'taksonomiyang',22,'taksonomiya'),(17332,'tala',17,'tala'),(3205,'talaan',17,'tala'),(15617,'Talmud',5,'Talmud'),(14787,'Talmuds',5,'Talmuds'),(1718,'talong',46,'talong'),(5877,'talumpati',17,'talumpati'),(8279,'tama',2,'tama'),(11975,'tamang',31,'tama'),(9495,'tamang',176,'tama'),(8122,'tampok',17,'tampok'),(11809,'tampukan',17,'tampok'),(14157,'Tanakh',5,'Tanakh'),(3760,'tanawin',17,'tanaw'),(2951,'tanawing',22,'tanaw'),(17726,'tanda',3,'tanda'),(17961,'tanda',17,'tanda'),(4774,'tanging',31,'tangi'),(7188,'tangkad',17,'tangkad'),(744,'tanker',10,'tanker'),(17264,'tao',3,'tao'),(140,'tao',17,'tao'),(4107,'taon',5,'taon'),(1470,'taon',17,'taon'),(10219,'taong',22,'tao'),(487,'taong',22,'taon'),(3892,'tapis',17,'tapis'),(14559,'Targum',5,'Targum'),(10523,'tariffs',10,'tariffs'),(8329,'tasahan',17,'tasa'),(12533,'tataas',552,'tataas'),(13528,'tatakbo',552,'takbo'),(17598,'tatlo',4,'tatlo'),(5246,'tatlong',46,'tatlo'),(16724,'tatlong-araw',16724,'tatlo-araw'),(15571,'tatlumpu\'t',15567,'tatlumpu_at'),(16348,'tatlumpung',46,'tatlumpg'),(957,'tauhan',17,'tao'),(13278,'tawag',17,'tawag'),(8794,'tawag',2122,'tawag'),(15896,'tawagin',37,'tawag'),(5388,'taxa',17,'taxa'),(5401,'taxon',17,'taxon'),(4869,'taxonomy',17,'taxonomy'),(2642,'tayog',17,'tayog'),(364,'TC',57,'TC'),(10366,'Tcf',1809,'Tcf'),(2503,'Technology',5,'Technology'),(2374,'Techsphere',5,'Techsphere'),(2619,'teknolohiyang',22,'teknolohiya'),(14281,'teksto',17,'teksto'),(14327,'tekstong',22,'teksto'),(14528,'tekstwal',17,'tekstwal'),(9490,'Telescope',5,'Telescope'),(10805,'Tell',5,'Tell'),(16805,'tema',17,'tema'),(7424,'templeyt',17,'templeyt'),(3657,'templo',17,'templo'),(3389,'templo',3389,'templo'),(11445,'teorista',17,'teorista'),(5529,'teorya',17,'teorya'),(5539,'teoryang',22,'teorya'),(3729,'terasang',22,'terasa'),(10158,'teritoryang',22,'teritorya'),(12473,'teritoryo',17,'teritoryo'),(13863,'terminong',22,'termino'),(15977,'Testament',5,'Testament'),(3843,'Tevoda',5,'Tevoda'),(1186,'Texas',5,'Texas'),(14457,'Text',5,'Text'),(14332,'Texts',5,'Texts'),(14488,'texts',17,'texts'),(14821,'Textus',5,'Textus'),(9119,'thaliana',10,'thaliana'),(5190,'Thallophyta',5,'Thallophyta'),(1414,'The',5,'The'),(1075,'theatre',10,'theatre'),(6052,'Theodore',5,'Theodore'),(16030,'The_Canon_of_the_Ethiopian_Orthodox_Tewado_Ch',5,'The_Canon_of_the_Ethiopian_Orthodox_Tewado_Ch'),(5853,'The_Origin_of_Species',5,'The_Origin_of_Species'),(1395,'The_Spirit_of_Dubai',5,'The_Spirit_of_Dubai'),(5879,'Third',5,'Third'),(12038,'third',10,'third'),(5272,'Thomas',5,'Thomas'),(3838,'Thommanon',5,'Thommanon'),(1299,'Three',5,'Three'),(7242,'thymine',10,'thymine'),(3155,'tibay',17,'tibay'),(11028,'Tignan',37,'Tingin'),(17071,'tila',1605,'tila'),(9546,'timescales',10,'timescales'),(3481,'timpla',17,'timpla'),(1083,'tinaggihan',37,'tanggi'),(14330,'tinaguriang',8,'taguri'),(1165,'tinakpan',37,'takip'),(2391,'Tinalakay',37,'Talakay'),(18138,'tinampat',37,'tapat'),(11437,'tinanggap',37,'tanggap'),(10720,'tinanggihan',37,'tanggi'),(13841,'tinataglay',62,'taglay'),(9897,'tinatakpan',9897,'takip'),(2784,'Tinatalakay',62,'Talakay'),(4125,'tinatanggap',62,'tanggap'),(5014,'tinatanngap',62,'tanngap'),(11881,'Tinatantiya',8,'Tantiya'),(1392,'tinatawag',62,'tawag'),(7588,'tinatawag',62,'tinatawag'),(17141,'tinawag',37,'tawag'),(6034,'tingan',37,'tingin'),(17274,'tingnan',1605,'tingin'),(17698,'tinukoy',18,'tukoy'),(4019,'tinutukoy',62,'tukoy'),(2846,'tinutulak',62,'tulak'),(9294,'tinuturing',62,'turing'),(14212,'Tipan',5,'Tipan'),(14445,'tipan',17,'tipan'),(1429,'tirahang',22,'tiraha'),(9446,'tisyu',17,'tisyu'),(2770,'Titan',5,'Titan'),(6671,'titik',17,'titik'),(13141,'titulo',17,'titulo'),(13314,'titulong',22,'titulo'),(3549,'tiwala',2,'tiwala'),(4856,'tiyak',2,'tiyak'),(15765,'tiyak',12309,'tiyak'),(42,'tiyakin',37,'tiyak'),(10781,'tobacco',17,'tobacco'),(2152,'tonelada',17,'tonelada'),(2155,'toneladang',22,'tonelada'),(17887,'Toni',5,'Toni'),(17316,'tono',17,'tono'),(14186,'Torah',5,'Torah'),(825,'torpedo',17,'torpedo'),(14779,'Tosefta',5,'Tosefta'),(15015,'totoo',12309,'totoo'),(16375,'totoong',176,'totoo'),(5005,'totoo’y',5005,'totoo_ay'),(13094,'tower',10,'tower'),(3703,'tower',17,'tower'),(13867,'town',10,'town'),(582,'trabahador',17,'trabaho'),(2232,'trabaho',17,'trabaho'),(10446,'trade',10,'trade'),(14799,'tradisyon',17,'tradisyon'),(13502,'tradisyonal',2,'tradisyonal'),(14578,'tradisyong',22,'tradisyon'),(14527,'tradisyong',31,'tradisyon'),(133,'trahedya',17,'trahedya'),(479,'training',10,'training'),(1788,'trak',17,'trak'),(6273,'transkripsyon',17,'transkripsyon'),(8576,'translocation',10,'translocation'),(6032,'transpormasyon',17,'transpormasyon'),(688,'transportasyon',17,'transportasyon'),(680,'transpot',10,'transpot'),(10512,'Treaty',5,'Treaty'),(1786,'treyler',17,'treyler'),(10362,'trillion',17,'trillion'),(1230,'Trinidad',5,'Trinidad'),(538,'tripulante',17,'tripulante'),(4750,'triviale',10,'triviale'),(4740,'trivialia',10,'trivialia'),(2086,'Tropical',5,'Tropical'),(9200,'tsart',17,'tsart'),(11842,'Tsina',5,'Tsina'),(11865,'Tsino',5,'Tsino'),(10833,'tubig',17,'tubig'),(1802,'tuktok',17,'tuktok'),(12589,'tukuying',3124,'tukoy'),(14691,'tula',17,'tula'),(3501,'tulad',750,'tulad'),(1637,'tulad_ng',750,'tulad_ng'),(3933,'tulay',17,'tulay'),(2827,'tulong',17,'tulong'),(11904,'tuloy-tuloy',2,'tuloy'),(7044,'tuloy-tuloy',60,'tuloy'),(8144,'tuluyang',41,'tuloy'),(3102,'tumaas',92,'taas'),(14407,'tumagal',92,'tagal'),(3081,'tumataas',62,'taas'),(1090,'tumatakbo',62,'takbo'),(923,'tumawid',92,'tawid'),(16218,'tumigil',92,'tigil'),(3670,'tumpak',2,'tumpak'),(10710,'tumubo',8,'tubo'),(1108,'Tumulong',92,'Tulong'),(12077,'tumuon',92,'tuon'),(1487,'Tumutok',92,'Tutok'),(6930,'tumutukoy',62,'tukoy'),(3203,'Tunay',2,'Tunay'),(15242,'tunay',12309,'tunay'),(16561,'tungkol_kay',16561,'tungkol_kay'),(3354,'Tungkol_sa',1768,'Tungkol_sa'),(1360,'tungkulin',17,'tungkol'),(6293,'tungo_sa',24,'tungo_sa'),(10988,'Tunisia',5,'Tunisia'),(3116,'tunog',17,'tunog'),(4709,'tuntunin',17,'tunton'),(6760,'tuntunin',17,'tuntunin'),(7279,'tuntuning',22,'tuntuning'),(10991,'Turkey',5,'Turkey'),(11555,'tuwing',96,'tuwing'),(1157,'Two',5,'Two'),(1438,'Type',5,'Type'),(841,'U',57,'U'),(10348,'U.S.',57,'U.S.'),(1410,'UAE',57,'UAE'),(1860,'UAS',57,'UAS'),(9195,'ugnayan',17,'ugnay'),(5656,'ukol_sa',1768,'ukol_sa'),(16297,'ulan',17,'ulan'),(9889,'ulap',17,'ulap'),(4717,'ulirang',31,'uliran'),(9077,'ulit',96,'ulit'),(10593,'ulo',17,'ulo'),(9758,'umaaligid',62,'aligid'),(9592,'umaandar',62,'andar'),(16186,'umaangat',62,'angat'),(14841,'umaasa',62,'asa'),(1465,'umabot',92,'abot'),(964,'umaga',96,'umaga'),(15880,'umalis',92,'alis'),(3465,'umamo',92,'amo'),(10387,'umangat',92,'angat'),(13370,'umano',18,'ano'),(2936,'Umiiral',62,'iral'),(17185,'umiiral',513,'iral'),(14401,'umiral',92,'iral'),(4565,'umpisa',96,'umpisa'),(7673,'umunlad',60,'unlad'),(5423,'umunlad',92,'unlad'),(14317,'umusbong',92,'usbong'),(13958,'umuunlad',62,'unlad'),(6647,'umuurong',62,'urong'),(9527,'umuusog',513,'usog'),(5042,'una',4,'una'),(5756,'unahan',2,'una'),(135,'unahan',135,'una'),(493,'unan',46,'unan'),(634,'unang',46,'una'),(12011,'unang',46,'unang'),(16945,'Unawa',92,'awa'),(10496,'unemployment',10,'unemployment'),(10195,'unibersal',2,'unibersal'),(13284,'Unido',5,'Unido'),(221,'Unidos',5,'Unidos'),(275,'Union',5,'Union'),(455,'unit',10,'unit'),(1642,'United',5,'United'),(18090,'Universe',5,'Universe'),(1856,'Unmanned',5,'Unmanned'),(15506,'unti-unting',41,'unti'),(36,'upang',3,'upang'),(12005,'urban',2,'urban'),(12714,'urban',10,'urban'),(12105,'urban',17,'urban'),(12094,'urbanisasyon',17,'urbanisasyon'),(12486,'urbanismo',17,'urbanismo'),(1854,'uri',17,'uri'),(4233,'uri-uriin',2,'uri'),(6653,'urong',17,'urong'),(597,'US',57,'US'),(13833,'usapin',17,'usap'),(17665,'utak',17,'utak'),(10544,'utang',17,'utang'),(16464,'Üthelred',5,'Üthelred'),(3222,'utos',17,'utos'),(14734,'uunawain',552,'awa'),(5301,'uunlad',552,'unlad'),(8409,'UV',57,'UV'),(11025,'valley',10,'valley'),(7125,'varyabol',2,'varyabol'),(18194,'vegetarian',17,'vegetarian'),(12299,'Venice',5,'Venice'),(5668,'Verchue_über_Pflanzenhybriden',5,'Verchue_über_Pflanzenhybriden'),(5062,'Vermes',5,'Vermes'),(1212,'Vernam',5,'Vernam'),(1022,'Vickers',5,'Vickers'),(13942,'Village',5,'Village'),(5318,'virology',2,'virology'),(7272,'virus',10,'virus'),(6097,'virus',17,'virus'),(3977,'Vishnu',5,'Vishnu'),(1283,'Vitoria',5,'Vitoria'),(4319,'viviparous',10,'viviparous'),(10553,'Vladimir',5,'Vladimir'),(14530,'Vorlage',5,'Vorlage'),(1253,'VP',57,'VP'),(15773,'Vulgate',5,'Vulgate'),(13421,'Waales',5,'Waales'),(1015,'wakas',17,'wakas'),(4109,'Wala',84,'Wala'),(12273,'wala',12273,'wala'),(4352,'walang',40,'wala'),(950,'walang',84,'wala'),(731,'walang',214,'wala'),(214,'walang',214,'walang'),(4450,'walang',4450,'wala'),(11241,'walang',11241,'wala'),(13463,'Wales',5,'Wales'),(10205,'Wali',5,'Wali'),(2115,'WALRUS',57,'WALRUS'),(10717,'War',5,'War'),(1074,'war',10,'war'),(3952,'wasak',2,'wasak'),(3375,'Wat',5,'Wat'),(6120,'Watson',5,'Watson'),(1329,'Watsonville',5,'Watsonville'),(9913,'wavelengths',10,'wavelengths'),(9676,'waves',10,'waves'),(1044,'Weeksville',5,'Weeksville'),(1023,'Wellington',5,'Wellington'),(13423,'Wells',5,'Wells'),(10829,'wells',10,'wells'),(1171,'West',5,'West'),(12074,'western',10,'western'),(1623,'Westinghouse',5,'Westinghouse'),(1049,'Weymouth',5,'Weymouth'),(10843,'wheat',10,'wheat'),(5108,'Whittaker',5,'Whittaker'),(14438,'wika',17,'wika'),(10114,'wilayas',10,'wilayas'),(5790,'William',5,'William'),(1033,'Wing',5,'Wing'),(16292,'Winwaed',5,'Winwaed'),(9658,'working',10,'working'),(4469,'World',5,'World'),(1445,'Worldwide',5,'Worldwide'),(14622,'Writings',5,'Writings'),(16462,'Wulfhere',5,'Wulfhere'),(2990,'WW',57,'WW'),(7711,'X',57,'X'),(6133,'X-ray',5,'X-ray'),(11839,'Xi’an',5,'Xi’an'),(7693,'Y',57,'Y'),(3678,'Yari',212,'Yari'),(1060,'Yarmouth',5,'Yarmouth'),(7747,'Yat',5,'Yat'),(2745,'yelo',17,'yelo'),(5824,'yEvvw',10,'yEvvw'),(13637,'York',5,'York'),(15465,'yugto',17,'yugto'),(15704,'yumabong',92,'yabong'),(1115,'yunit',10,'yunit'),(7557,'yunit',17,'yunit'),(14,'Zeppelin',5,'Zeppelin'),(4957,'zoological',10,'zoological'),(4397,'zoophytes',10,'zoophytes'),(435,'ZP',57,'ZP'),(11566,'[',53,'['),(11568,']',53,']'),(10617,'£',53,'£'),(5998,'–',53,'–'),(1668,'‘',53,'‘'),(1670,'’',53,'’'),(53,'“',53,'“'),(55,'”',53,'”');
/*!40000 ALTER TABLE `wordposlemmamap` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-20  0:11:28
