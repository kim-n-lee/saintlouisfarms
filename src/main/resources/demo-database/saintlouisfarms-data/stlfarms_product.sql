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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `measurementCategory_id` int NOT NULL,
  `productCategory_id` int NOT NULL,
  `productDetails_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `isDeleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkg11ydju0snu3ad0pm5nf6sc5` (`measurementCategory_id`),
  KEY `FK8nutm44ujutsbg3pesy3milmh` (`productCategory_id`),
  KEY `FKn3bxg3j6il3tk7fhdvhy5pc8q` (`productDetails_id`),
  KEY `FKt9ajreq5lrb0b89vhnrpq7kcs` (`user_id`),
  CONSTRAINT `FK8nutm44ujutsbg3pesy3milmh` FOREIGN KEY (`productCategory_id`) REFERENCES `productcategory` (`id`),
  CONSTRAINT `FKkg11ydju0snu3ad0pm5nf6sc5` FOREIGN KEY (`measurementCategory_id`) REFERENCES `measurementcategory` (`id`),
  CONSTRAINT `FKn3bxg3j6il3tk7fhdvhy5pc8q` FOREIGN KEY (`productDetails_id`) REFERENCES `productdetails` (`id`),
  CONSTRAINT `FKt9ajreq5lrb0b89vhnrpq7kcs` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (12,'Whole Milk',5,11,13,1,_binary '\0'),(14,'Strawberries',3,8,15,1,_binary '\0'),(17,'French Bread',2,16,18,1,_binary '\0'),(20,'10oz. Ribeye',2,19,21,1,_binary '\0'),(22,'Apple Cider',6,11,23,1,_binary '\0'),(34,'Fresh Milk',29,33,35,24,_binary '\0'),(36,'12oz. Ribeye',25,33,37,24,_binary '\0'),(38,'Baguette',25,33,39,24,_binary '\0'),(40,'Apple Juice',29,31,41,24,_binary '\0'),(42,'Fresh Strawberries',26,31,43,24,_binary '\0'),(54,'Cinnamon Cider',48,53,55,44,_binary '\0'),(56,'Fresh Bread',45,53,57,44,_binary '\0'),(58,'Whole Milk',49,53,59,44,_binary '\0'),(60,'Strawberries',46,51,61,44,_binary '\0'),(62,'New York Strip',45,53,63,44,_binary '\0'),(74,'12oz. Strip Steak',65,73,75,64,_binary '\0'),(76,'French Bread',65,72,77,64,_binary '\0'),(78,'Fresh Cider',69,73,79,64,_binary '\0'),(80,'Fresh Strawberries',66,71,81,64,_binary '\0'),(82,'Whole Milk',68,73,83,64,_binary '\0'),(166,'Cross Cut Beef Shank',3,19,167,1,_binary '\0'),(170,'Onions',3,7,171,1,_binary '\0'),(172,'Brussels Sprouts',3,7,173,1,_binary '\0'),(174,'Beef Bones',3,19,175,1,_binary '\0'),(176,'Mild Cheddar',3,10,177,1,_binary '\0'),(178,'Shallots',3,7,179,1,_binary '\0'),(180,'Beef Ribs',3,19,181,1,_binary '\0'),(182,'Bacon',3,19,183,1,_binary '\0'),(184,'Brioche',2,16,185,1,_binary '\0'),(186,'Ground Beef',3,19,187,1,_binary '\0'),(188,'Sweet Corn',3,7,189,1,_binary '\0'),(190,'Carrots',3,7,191,1,_binary '\0'),(192,'Kale',3,7,193,1,_binary '\0'),(194,'Beets',3,7,195,1,_binary '\0'),(196,'Sourdough',2,16,197,1,_binary '\0'),(198,'Radishes',3,7,199,1,_binary '\0'),(200,'Celery',3,7,201,1,_binary '\0'),(220,'Strip Steak',3,19,221,1,_binary '\0'),(222,'Ground Lean Beef',3,19,223,1,_binary '\0'),(224,'Fresh Oranges',3,8,225,1,_binary '\0'),(226,'Beef Bacon',3,19,227,1,_binary '\0'),(248,'Radishes',230,235,249,228,_binary '\0'),(250,'Bacon',240,247,251,238,_binary '\0');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
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
