CREATE DATABASE  IF NOT EXISTS `signalz` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `signalz`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: signalz
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Table structure for table `measurement`
--

DROP TABLE IF EXISTS `measurement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `measurement` (
  `idMeasurement` int(11) NOT NULL,
  `yValue` double NOT NULL,
  PRIMARY KEY (`idMeasurement`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measurement`
--

LOCK TABLES `measurement` WRITE;
/*!40000 ALTER TABLE `measurement` DISABLE KEYS */;
INSERT INTO `measurement` VALUES (1,5.45),(2,-14.65),(3,-4.29),(4,1.06),(5,-9.21),(6,-2.04),(7,5.27),(8,4.73),(9,1.44),(10,3.99),(11,-5.69),(12,-15.17),(13,-7.28),(14,4.26),(15,-7.11),(16,-17.6),(17,-18.17),(18,-8.1),(19,1.15),(20,-18.13),(21,9.44),(22,6.91),(23,-10.03),(24,3.59),(25,-7.24),(26,9.69),(27,-10.15),(28,-19.08),(29,-11.38),(30,-10.52),(31,7.16),(32,-6.57),(33,-2.07),(34,-3.78),(35,9),(36,-6.71),(37,-11.5),(38,-13.87),(39,4.85),(40,-8.93),(41,-5.22),(42,5.43),(43,-2.02),(44,-17.08),(45,-15.65),(46,8.8),(47,-11.62),(48,3.69),(49,-19.69),(50,4.08),(51,1.73),(52,-5.29),(53,-5.74),(54,0.73),(55,-2.83),(56,-6.96),(57,-11.23),(58,-9.09),(59,-12.04),(60,-2.73),(61,-18.74),(62,5),(63,-16.11),(64,-6),(65,-8.06),(66,6.04),(67,-4.85),(68,-5.26),(69,-16.68),(70,5.29),(71,5.4),(72,-1.93),(73,7.83),(74,4.4),(75,6.82),(76,0.23),(77,-9.06),(78,-4.99),(79,-6.05),(80,-0.27),(81,9.58),(82,-4.39),(83,-16.79),(84,0.57),(85,-6.71),(86,4.47),(87,-12.03),(88,3.98),(89,8.83),(90,-10.13),(91,-11.31),(92,3.52),(93,-5.57),(94,9.19),(95,-1.78),(96,-18.97),(97,-14.32),(98,-13.15),(99,-17.68),(100,-12.6);
/*!40000 ALTER TABLE `measurement` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-01 16:51:46
