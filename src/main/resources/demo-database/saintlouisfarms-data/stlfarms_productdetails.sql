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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-15 18:00:12
