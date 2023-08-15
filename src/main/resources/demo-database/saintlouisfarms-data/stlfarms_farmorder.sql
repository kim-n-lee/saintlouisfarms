-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: stlfarms
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `farmorder`
--

DROP TABLE IF EXISTS `farmorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farmorder` (
  `id` int NOT NULL,
  `confirmed` bit(1) DEFAULT NULL,
  `fulfilled` bit(1) DEFAULT NULL,
  `localDateTime` datetime(6) DEFAULT NULL,
  `sent` bit(1) DEFAULT NULL,
  `totalAmount` decimal(19,2) DEFAULT NULL,
  `client_id` int DEFAULT NULL,
  `farmer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2ydtj5v3l7asx2ojw201gdv3i` (`client_id`),
  KEY `FK264e3ewmwoko3kl9b918vdnmf` (`farmer_id`),
  CONSTRAINT `FK264e3ewmwoko3kl9b918vdnmf` FOREIGN KEY (`farmer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK2ydtj5v3l7asx2ojw201gdv3i` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farmorder`
--

LOCK TABLES `farmorder` WRITE;
/*!40000 ALTER TABLE `farmorder` DISABLE KEYS */;
INSERT INTO `farmorder` VALUES (97,_binary '',_binary '','2023-08-13 21:09:30.807580',_binary '',214.95,84,1),(109,_binary '\0',_binary '\0','2023-08-13 21:09:41.714961',_binary '',18.00,84,44),(111,_binary '',_binary '','2023-08-13 21:09:55.089276',_binary '',137.50,84,24),(119,_binary '\0',_binary '\0','2023-08-13 21:10:10.158122',_binary '',125.00,84,64),(164,_binary '\0',_binary '\0','2023-08-13 21:43:15.933497',_binary '',12.00,145,44),(219,_binary '\0',_binary '\0','2023-08-14 11:53:31.926649',_binary '\0',0.00,84,1),(288,_binary '\0',_binary '\0','2023-08-14 13:37:33.009280',_binary '',310.00,260,1),(295,_binary '\0',_binary '\0','2023-08-14 18:38:27.350680',_binary '',80.00,84,1),(318,_binary '\0',_binary '\0','2023-08-15 12:31:33.396249',_binary '',70.00,84,1);
/*!40000 ALTER TABLE `farmorder` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-15 18:00:12
