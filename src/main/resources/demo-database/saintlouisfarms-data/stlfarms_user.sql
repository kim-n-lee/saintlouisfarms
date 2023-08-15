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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pwHash` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `farmName` varchar(45) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'123 No Place Dr.','St. Louis','farmer1@farmer.com','Seth','Palmer','(314)-123-4567','$2a$10$xNnir4s0/zYZJNVxXTM/BuvYWMRh4dqSYIvHXj.2QjfE5tcCPzO2q','63117','Silver Spring Farms','images/1Silver Spring Farmsedited.jpg'),(24,'123 No Place Dr.','St. Louis','farmer2@farmer.com','Thomas','Johnson','(314)-123-4567','$2a$10$wn6Qjmk38z/1d31GFiJYF.18fWhNqMSopP/J6brTyGVEJx5cMUClS','63117','Johnson Family Farms','images/24Johnson Family Farmsedited.jpg'),(44,'123 No Place Dr.','St. Louis','farmer3@farmer.com','Judith','Houston','(314)-123-4567','$2a$10$n4TeSRwiqY4zSNt07y6rKuPNuY6TXk7quFIStWQh.43/keEhiQkfG','63117','Independence Farms','images/44Independence Farms.jpg'),(64,'123 No Place Dr.','St. Louis','farmer4@farmer.com','Eugene','Davis','(314)-123-4567','$2a$10$4WMDJ/6L9TYmDul44VIZze3G7bSeCrVFPufkyEszyR43GvWoj971y','63117','Davis Farms','images/64Davis Farms.jpg'),(228,'123 No Place Dr.','St. Louis','farmer5@farmer.com','Hunter','Hoskins','314-123-4567','$2a$10$LOzqIJj/ADZTY0q.x.Fqs.HrAypKHOAmt60tHsu54/G/DeRkuvqPK','63126','Aetolia Farms','images/228Aetolia Farms.jpg'),(238,'123 No Place Dr.','St. Louis','farmer6@farmer.com','Marcus','Bowman','314-123-4567','$2a$10$9VHAqo/WErLI5X/DE3pdQOH6SwvK.czI4hVclRf21qM4/sSLe0H4i','63126','Peachtree Farms','images/238Peachtree Farms.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-15 18:00:11
