CREATE DATABASE  IF NOT EXISTS `evidencia_tovarov_sprostredkovatela` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `evidencia_tovarov_sprostredkovatela`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: evidencia_tovarov_sprostredkovatela
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `email` varchar(60) DEFAULT NULL,
  `number` varchar(45) DEFAULT NULL,
  `more_details` varchar(45) DEFAULT 'zakaznik',
  `enable` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Maroš','Čekan','maros.cekan@gmail.com',NULL,'kolega',1),(2,'Ladislav','Pčola','ladislav.pcola@gmail.com','0908354827','neviem',1),(3,'Anna','Kmecová',NULL,'123','zakaznik',1),(4,'Slavomír','Pagáč',NULL,NULL,'zakaznik',1),(5,'Miloš','Martončík',NULL,NULL,'zakaznik',0),(6,'Eva','Adamová',NULL,NULL,'kamaratka',1),(7,'Marko','Soták',NULL,NULL,'zakaznik',1),(8,'Ladislav','Harvanek',NULL,NULL,'svagor',0),(9,'Ladislav','Vašinský',NULL,'','zakaznik',1),(10,'Maroš','Čekan',NULL,NULL,'zakaznik',1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `validity` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (1,'Prípravky so soľou z Mŕtveho mora',1),(2,'Rad Tea Tree s Manukou a Rosalinou',1),(3,'Bylinné špeciality',1),(4,'Starostlivosť o telo',1),(5,'Intímna starostlivosť',1);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `shipping_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_objednavky_zakaznici1` (`customer_id`),
  CONSTRAINT `fk_objednavky_zakaznici1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,1,1,'2018-11-20 00:00:00','2018-12-17 00:00:00'),(2,9,NULL,'2018-12-16 14:14:14','2018-12-16 14:15:14'),(3,4,NULL,'2018-12-16 18:01:25','2018-12-17 18:02:25'),(4,1,NULL,'2018-12-17 16:17:52',NULL);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price_piece` decimal(6,2) DEFAULT NULL,
  `price_total` decimal(6,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_item_order1_idx` (`order_id`),
  KEY `fk_order_item_product1_idx` (`product_id`),
  CONSTRAINT `fk_order_item_order1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `fk_order_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,3,1,24.65,24.65),(2,1,4,2,20.25,40.50),(3,2,3,1,24.65,24.65),(4,3,3,1,24.65,24.65),(5,4,3,1,24.65,24.65),(6,4,4,1,15.00,15.00);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `price` decimal(6,2) NOT NULL,
  `group_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `validity` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_tovary_skupiny_idx` (`group_id`),
  CONSTRAINT `fk_tovary_skupiny` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'5067','San´Aktiv Šampón',23.10,1,10,1),(2,'5140','San´Aktiv Kúpeľ',24.75,1,9,0),(3,'5122','Tea Tree Šampón  ',24.65,2,4,1),(4,'5055','Žeruchový krém  ',15.00,4,8,1),(5,'5058','Lamelloderm krém',40.80,4,10,0),(6,'5013','Gél po holení',13.65,5,10,1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale`
--

DROP TABLE IF EXISTS `sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `sale_date` datetime NOT NULL,
  `total_price` decimal(8,2) DEFAULT NULL,
  `discount` decimal(8,2) DEFAULT NULL,
  `final_price` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_poziadavky_zakaznici1_idx` (`customer_id`),
  CONSTRAINT `fk_poziadavky_zakaznici1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` VALUES (1,1,'2018-11-20 00:00:00',23.10,0.00,23.10),(2,3,'2018-12-10 00:00:00',44.90,0.00,44.90),(3,9,'2018-12-13 14:37:17',24.65,0.00,24.65),(4,9,'2018-12-13 16:44:37',24.75,0.00,24.75),(5,9,'2018-12-13 16:49:56',20.25,0.00,20.25),(6,8,'2018-12-13 17:42:59',231.00,0.00,231.00),(7,10,'2018-12-14 13:15:57',33.90,0.00,33.90),(8,10,'2018-12-14 13:58:10',49.40,0.40,49.00),(9,7,'2018-12-16 13:51:29',24.65,0.00,24.65),(10,3,'2018-12-16 18:02:22',24.65,0.00,24.65),(11,2,'2018-12-16 18:11:20',0.00,0.00,0.00),(12,9,'2018-12-17 16:09:26',15.00,2.00,13.00);
/*!40000 ALTER TABLE `sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_item`
--

DROP TABLE IF EXISTS `sale_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sale_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sale_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price_piece` decimal(6,2) DEFAULT NULL,
  `price_total` decimal(6,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_predaj_poziadavky1_idx` (`sale_id`),
  KEY `fk_predaj_tovary1_idx` (`product_id`),
  CONSTRAINT `fk_predaj_poziadavky1` FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`),
  CONSTRAINT `fk_predaj_tovary1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_item`
--

LOCK TABLES `sale_item` WRITE;
/*!40000 ALTER TABLE `sale_item` DISABLE KEYS */;
INSERT INTO `sale_item` VALUES (1,1,1,1,23.10,23.10),(2,2,3,1,24.65,24.65),(3,2,4,1,20.25,20.25),(4,3,3,1,24.65,24.65),(5,4,2,2,24.75,24.75),(6,5,4,5,20.25,20.25),(7,6,1,10,23.10,231.00),(8,7,6,1,13.65,13.65),(9,7,4,1,20.25,20.25),(10,8,3,1,24.65,24.65),(11,8,2,1,24.75,24.75),(12,9,3,1,24.65,24.65),(13,10,3,1,24.65,24.65),(14,12,4,1,15.00,15.00);
/*!40000 ALTER TABLE `sale_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-10 11:31:05
