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
-- Table structure for table `basketitem`
--

DROP TABLE IF EXISTS `basketitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `basketitem` (
  `id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `shoppingBasket_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8lqxfdk0yv9r6sib2ehubfxin` (`product_id`),
  KEY `FKq0im0s8m67168n69yx1acitoa` (`shoppingBasket_id`),
  CONSTRAINT `FK8lqxfdk0yv9r6sib2ehubfxin` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKq0im0s8m67168n69yx1acitoa` FOREIGN KEY (`shoppingBasket_id`) REFERENCES `shoppingbasket` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `basketitem`
--

LOCK TABLES `basketitem` WRITE;
/*!40000 ALTER TABLE `basketitem` DISABLE KEYS */;
INSERT INTO `basketitem` VALUES (122,0,34,121),(123,45,36,121),(124,0,38,121),(125,0,40,121),(126,0,42,121),(134,3,54,133),(135,0,56,133),(136,0,58,133),(137,55,60,133),(138,0,62,133),(140,0,74,139),(141,0,76,139),(142,0,78,139),(143,0,80,139),(144,0,82,139),(147,0,12,146),(148,0,14,146),(149,0,17,146),(150,0,20,146),(151,0,22,146),(153,0,34,152),(154,0,36,152),(155,0,38,152),(156,0,40,152),(157,0,42,152),(253,0,248,252),(255,0,250,254),(321,0,12,320),(322,0,14,320),(323,0,17,320),(324,0,20,320),(325,0,22,320),(326,0,166,320),(327,0,170,320),(328,0,172,320),(329,0,174,320),(330,0,176,320),(331,0,178,320),(332,0,180,320),(333,0,182,320),(334,0,184,320),(335,0,186,320),(336,0,188,320),(337,0,190,320),(338,0,192,320),(339,0,194,320),(340,0,196,320);
/*!40000 ALTER TABLE `basketitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` int NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pwHash` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (84,'123 No Place Dr.','63117','client@client.com','Kenneth','Newton','(314)-123-4567','$2a$10$SItR/.h/EW/8yOLVxlu3/OxyPOrv1VZYpJr3p0zxYsDfdsTyWvuOe','St. Louis'),(145,'124 Lane','63110','Ryan@gmail.com','Ryan','Ryan','9005559345','$2a$10$uc8ywdWdIZGduKVY.FhWHe7CYbFVu60sorkDetsSA.h5wzB2GqgPm','Stl'),(260,'123 No Place Dr.','63126','client2@client.com','Jonald','Weatherford','314-123-4567','$2a$10$FcJhSmcoLywKaWn1PHT8XO79ODy/1tWy9EMbsPawwgWP7p/UgZswW','St. Louis');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (341);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `measurementcategory`
--

DROP TABLE IF EXISTS `measurementcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `measurementcategory` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2h5enn7g51jj6u9kvtvd0c18y` (`user_id`),
  CONSTRAINT `FK2h5enn7g51jj6u9kvtvd0c18y` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measurementcategory`
--

LOCK TABLES `measurementcategory` WRITE;
/*!40000 ALTER TABLE `measurementcategory` DISABLE KEYS */;
INSERT INTO `measurementcategory` VALUES (2,'each',1),(3,'lbs',1),(4,'cs',1),(5,'pint',1),(6,'qt',1),(25,'each',24),(26,'lbs',24),(27,'cs',24),(28,'pint',24),(29,'qt',24),(45,'each',44),(46,'lbs',44),(47,'cs',44),(48,'pint',44),(49,'qt',44),(65,'each',64),(66,'lbs',64),(67,'cs',64),(68,'pint',64),(69,'qt',64),(229,'each',228),(230,'lbs',228),(231,'cs',228),(232,'pint',228),(233,'qt',228),(239,'each',238),(240,'lbs',238),(241,'cs',238),(242,'pint',238),(243,'qt',238);
/*!40000 ALTER TABLE `measurementcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitem`
--

DROP TABLE IF EXISTS `orderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderitem` (
  `id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `orderItem_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfaufpvvi85t68bgrd11mvfcrl` (`orderItem_id`),
  KEY `FKg23j1vs750x8lkx2aesfk6n2` (`product_id`),
  CONSTRAINT `FKfaufpvvi85t68bgrd11mvfcrl` FOREIGN KEY (`orderItem_id`) REFERENCES `farmorder` (`id`),
  CONSTRAINT `FKg23j1vs750x8lkx2aesfk6n2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitem`
--

LOCK TABLES `orderitem` WRITE;
/*!40000 ALTER TABLE `orderitem` DISABLE KEYS */;
INSERT INTO `orderitem` VALUES (98,5,97,12),(99,5,97,14),(100,5,97,17),(101,5,97,20),(102,5,97,22),(110,2,109,54),(112,25,111,38),(120,25,119,76),(165,1,164,62),(289,5,288,12),(290,5,288,14),(291,5,288,20),(292,10,288,170),(293,5,288,182),(294,10,288,196),(296,10,295,12),(319,10,318,12);
/*!40000 ALTER TABLE `orderitem` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `product` VALUES (12,'Whole Milk',5,11,13,1),(14,'Strawberries',3,8,15,1),(17,'French Bread',2,16,18,1),(20,'10oz. Ribeye',2,19,21,1),(22,'Apple Cider',6,11,23,1),(34,'Fresh Milk',29,33,35,24),(36,'12oz. Ribeye',25,33,37,24),(38,'Baguette',25,33,39,24),(40,'Apple Juice',29,31,41,24),(42,'Fresh Strawberries',26,31,43,24),(54,'Cinnamon Cider',48,53,55,44),(56,'Fresh Bread',45,53,57,44),(58,'Whole Milk',49,53,59,44),(60,'Strawberries',46,51,61,44),(62,'New York Strip',45,53,63,44),(74,'12oz. Strip Steak',65,73,75,64),(76,'French Bread',65,72,77,64),(78,'Fresh Cider',69,73,79,64),(80,'Fresh Strawberries',66,71,81,64),(82,'Whole Milk',68,73,83,64),(166,'Cross Cut Beef Shank',3,19,167,1),(170,'Onions',3,7,171,1),(172,'Brussels Sprouts',3,7,173,1),(174,'Beef Bones',3,19,175,1),(176,'Mild Cheddar',3,10,177,1),(178,'Shallots',3,7,179,1),(180,'Beef Ribs',3,19,181,1),(182,'Bacon',3,19,183,1),(184,'Brioche',2,16,185,1),(186,'Ground Beef',3,19,187,1),(188,'Sweet Corn',3,7,189,1),(190,'Carrots',3,7,191,1),(192,'Kale',3,7,193,1),(194,'Beets',3,7,195,1),(196,'Sourdough',2,16,197,1),(198,'Radishes',3,7,199,1),(200,'Celery',3,7,201,1),(220,'Strip Steak',3,19,221,1),(222,'Ground Lean Beef',3,19,223,1),(224,'Fresh Oranges',3,8,225,1),(226,'Beef Bacon',3,19,227,1),(248,'Radishes',230,235,249,228),(250,'Bacon',240,247,251,238);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productcategory`
--

DROP TABLE IF EXISTS `productcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productcategory` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqpxluula7mqwsmfoib9wovy0m` (`user_id`),
  CONSTRAINT `FKqpxluula7mqwsmfoib9wovy0m` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productcategory`
--

LOCK TABLES `productcategory` WRITE;
/*!40000 ALTER TABLE `productcategory` DISABLE KEYS */;
INSERT INTO `productcategory` VALUES (7,'Vegetables',1),(8,'Fruit',1),(9,'Dry Goods',1),(10,'Featured',1),(11,'Drinks',1),(16,'Bread',1),(19,'Meat',1),(30,'Vegetables',24),(31,'Fruit',24),(32,'Dry Goods',24),(33,'Featured',24),(50,'Vegetables',44),(51,'Fruit',44),(52,'Dry Goods',44),(53,'Featured',44),(70,'Vegetables',64),(71,'Fruit',64),(72,'Dry Goods',64),(73,'Featured',64),(234,'Vegetables',228),(235,'Fruit',228),(236,'Dry Goods',228),(237,'Featured',228),(244,'Vegetables',238),(245,'Fruit',238),(246,'Dry Goods',238),(247,'Featured',238);
/*!40000 ALTER TABLE `productcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productdetails`
--

DROP TABLE IF EXISTS `productdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productdetails` (
  `id` int NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) NOT NULL,
  `quantity` int NOT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productdetails`
--

LOCK TABLES `productdetails` WRITE;
/*!40000 ALTER TABLE `productdetails` DISABLE KEYS */;
INSERT INTO `productdetails` VALUES (13,'Pints of whole milk','images/1Whole Milk0.jpg',7.00,70,_binary ''),(15,'A pound of strawberries','images/1Strawberries0.jpg',12.00,90,_binary ''),(18,'Crusty exterior, soft interior','images/1French Bread0.jpg',4.99,95,_binary ''),(21,'10oz. 1.5 inch ribeye','images/110oz. Ribeye0.jpg',8.00,90,_binary ''),(23,'Fresh pressed apple juice','images/1Apple Cider0.jpg',10.00,95,_binary ''),(35,'Pasteurized whole milk','images/24Fresh Milk0.jpg',7.00,250,_binary ''),(37,'Boneless grass-fed ribeye','images/2412oz. Ribeye0.jpg',10.00,100,_binary ''),(39,'Freshly baked french bread','images/24Baguette0.jpg',5.50,100,_binary ''),(41,'Fresh pressed apple juice','images/24Apple Juice0.jpg',4.99,220,_binary ''),(43,'A pound of strawberries','images/24Fresh Strawberries0.jpg',10.00,75,_binary ''),(55,'Apple juice infused with cinnamon','images/44Cinnamon Apple Cider0.jpg',9.00,120,_binary ''),(57,'Bread baked to order daily','images/44Fresh Bread0.jpg',6.99,100,_binary ''),(59,'Milk from pasture raised cows','images/44Whole Milk0.jpg',4.99,250,_binary ''),(61,'A pound of strawberries','images/44Strawberries0.jpg',8.99,125,_binary ''),(63,'12oz Strip Steak','images/44New York Strip0.jpg',12.00,154,_binary ''),(75,'Grass fed, pasture raised beef','images/6412oz. Strip Steak0.jpg',12.99,200,_binary ''),(77,'Fresh bread baked daily','images/64French Bread0.jpg',5.00,100,_binary ''),(79,'Locally produced cider','images/64Fresh Cider0.jpg',8.99,150,_binary ''),(81,'Pesticide-free, naturally grown','images/64Fresh Strawberries0.jpg',8.99,200,_binary ''),(83,'Hormone-free whole milk','images/64Whole Milk0.jpg',7.99,220,_binary ''),(167,'Also called \"Osso buco\"','images/1Cross Cut Beef Shank0.jpg',9.50,150,_binary ''),(171,'Large white onions','images/1Onions0.jpg',5.00,75,_binary ''),(173,'Churchill Brussels Sprouts','images/1Brussels Sprouts0.jpg',8.50,100,_binary ''),(175,'For stocks and soups','images/1Beef Bones0.jpg',4.00,150,_binary ''),(177,'Aged cheddar from hormone-free cows','images/1Mild Cheddar0.jpg',5.50,100,_binary ''),(179,'Pesticide free and organic','images/1Shallots0.jpg',7.50,45,_binary ''),(181,'Grass-fed, pasture-raised beef ribs','images/1Beef Ribs0.jpg',10.00,50,_binary ''),(183,'Cured with brown sugar and black pepper','images/1Bacon0.jpg',12.00,75,_binary ''),(185,'Rich, soft brioche bread','images/1Brioche0.jpg',6.00,100,_binary ''),(187,'80/20 Ground Beef','images/1Ground Beef0.jpg',10.00,300,_binary ''),(189,'Pesticide-free, organic sweet corn','images/1Sweet Corn0.jpg',6.50,150,_binary ''),(191,'May or may not help vision','images/1Carrots0.jpg',8.00,125,_binary ''),(193,'I\'m getting tired of writing item descriptions','images/1Kale0.jpg',6.00,100,_binary ''),(195,'High in antioxidants','images/1Beets0.jpg',8.00,100,_binary ''),(197,'Made with our own sourdough starter','images/1Sourdough0.jpg',6.00,125,_binary ''),(199,'Certified organic','images/1Radishes0.jpg',7.99,200,_binary ''),(201,'Good for aromatics and absolutely nothing else','images/1Celery0.jpg',5.50,100,_binary ''),(221,'12oz. New York Strip','images/1Strip Steak0.jpg',8.00,100,_binary ''),(223,'90/10 Ground Beef','images/1Ground Lean Beef0.jpg',11.00,100,_binary ''),(225,'Certified Organic Oranges','images/1Fresh Oranges0.jpg',10.00,100,_binary ''),(227,'Cured with brown sugar and black pepper','images/1Beef Bacon0.jpg',12.00,100,_binary ''),(249,'Fresh radishes','images/228Radishes0.jpg',10.00,100,_binary ''),(251,'Naturally cured bacon','images/238Bacon0.jpg',10.00,100,_binary '');
/*!40000 ALTER TABLE `productdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingbasket`
--

DROP TABLE IF EXISTS `shoppingbasket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shoppingbasket` (
  `id` int NOT NULL,
  `localDateTime` datetime(6) DEFAULT NULL,
  `totalAmount` decimal(19,2) DEFAULT NULL,
  `client_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrl9gd5eu7tynmv03lefh4a3yu` (`client_id`),
  CONSTRAINT `FKrl9gd5eu7tynmv03lefh4a3yu` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingbasket`
--

LOCK TABLES `shoppingbasket` WRITE;
/*!40000 ALTER TABLE `shoppingbasket` DISABLE KEYS */;
INSERT INTO `shoppingbasket` VALUES (121,'2023-08-13 21:10:16.923299',450.00,84),(133,'2023-08-13 21:18:00.591301',521.45,84),(139,'2023-08-13 21:31:18.206373',0.00,84),(146,'2023-08-13 21:42:13.459356',0.00,145),(152,'2023-08-13 21:42:28.437974',0.00,145),(252,'2023-08-14 13:33:59.299453',0.00,84),(254,'2023-08-14 13:34:02.450364',0.00,84),(320,'2023-08-15 12:32:02.202503',0.00,84);
/*!40000 ALTER TABLE `shoppingbasket` ENABLE KEYS */;
UNLOCK TABLES;

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

-- Dump completed on 2023-08-15 16:11:03
