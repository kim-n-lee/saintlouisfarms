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
-- Table structure for table `shoppingbasket_basketitems`
--

DROP TABLE IF EXISTS `shoppingbasket_basketitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shoppingbasket_basketitems` (
  `ShoppingBasket_id` int NOT NULL,
  `basketItems_id` int NOT NULL,
  UNIQUE KEY `UK_kav31iiba3ijdtp5seac7btyn` (`basketItems_id`),
  KEY `FKs83yjc71jrq4frh9rtrihf3ki` (`ShoppingBasket_id`),
  CONSTRAINT `FKs83yjc71jrq4frh9rtrihf3ki` FOREIGN KEY (`ShoppingBasket_id`) REFERENCES `shoppingbasket` (`id`),
  CONSTRAINT `FKslyjpgtw5uw682omv0ywqfv8t` FOREIGN KEY (`basketItems_id`) REFERENCES `basketitem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingbasket_basketitems`
--

LOCK TABLES `shoppingbasket_basketitems` WRITE;
/*!40000 ALTER TABLE `shoppingbasket_basketitems` DISABLE KEYS */;
INSERT INTO `shoppingbasket_basketitems` VALUES (121,122),(121,123),(121,124),(121,125),(121,126),(133,134),(133,135),(133,136),(133,137),(133,138),(139,140),(139,141),(139,142),(139,143),(139,144),(146,147),(146,148),(146,149),(146,150),(146,151),(152,153),(152,154),(152,155),(152,156),(152,157),(252,253),(254,255),(320,321),(320,322),(320,323),(320,324),(320,325),(320,326),(320,327),(320,328),(320,329),(320,330),(320,331),(320,332),(320,333),(320,334),(320,335),(320,336),(320,337),(320,338),(320,339),(320,340);
/*!40000 ALTER TABLE `shoppingbasket_basketitems` ENABLE KEYS */;
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
