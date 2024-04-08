CREATE DATABASE  IF NOT EXISTS `testing` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `testing`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: testing
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `ward_commune` varchar(255) DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtc8p70rjung1mh2jcis5m0go3` (`employee_id`),
  CONSTRAINT `FKtc8p70rjung1mh2jcis5m0go3` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,'Cần Thơ','Ô Môn','Lê Bình',1),(2,'Cần Thơ','Bình Thủy','Thường Thạnh',2),(3,'Cần Thơ','Ninh Kiều','Hưng Phú',4),(4,'Cần Thơ','Ô Môn','An Cư',5),(5,'Cần Thơ','Bình Thủy','Trường Lạc',6),(6,'Cần Thơ','Bình Thủy','Long Tuyền',7),(7,'Cần Thơ','Ninh Kiều','Long Tuyền',8),(8,'Cần Thơ','Ô Môn','Hưng Thạnh',9),(9,'Cần Thơ','Ninh Kiều','Trường Lạc',15),(10,'Cần Thơ','Cái Răng','Thới Hòa',16),(11,'Cần Thơ','Cái Răng','An Bình',17),(12,'Cần Thơ','Ninh Kiều','An Hội',20),(13,'Cần Thơ','Bình Thủy','Thới Bình',29),(14,'Cần Thơ','Cái Răng','Thới Hòa',31),(15,'Cần Thơ','Bình Thủy','Thới Hòa',33),(16,'Cần Thơ','Ninh Kiều','Cái Khế',36),(17,'Cần Thơ','Ninh Kiều','An Nghiệp',37),(18,'Cần Thơ','Bình Thủy','Long Tuyền',42),(19,'Cần Thơ','Cái Răng','An Cư',43),(20,'Cần Thơ','Bình Thủy','An Hòa',44),(21,'Cần Thơ','Cái Răng','Hưng Thạnh',45),(22,'Cần Thơ','Cái Răng','Hưng Phú',46),(23,'Cần Thơ','Cái Răng','Cái Khế',47),(24,'Cần Thơ','Bình Thủy','Long Tuyền',48),(25,'Cần Thơ','Ô Môn','Long Tuyền',49),(26,'Cần Thơ','Ô Môn','Trường Lạc',49),(27,'Cần Thơ','Ninh Kiều','Thới An Đông',48),(28,'Cần Thơ','Cái Răng','Trà Nóc',47),(29,'Cần Thơ','Ninh Kiều','Thới Hòa',46),(30,'Cần Thơ','Ninh Kiều','Lê Bình',45),(31,'Cần Thơ','Bình Thủy','Long Hòa',44),(32,'Cần Thơ','Bình Thủy','Xuân Khánh',43),(33,'Cần Thơ','Ô Môn','An Lạc',42),(34,'Cần Thơ','Ninh Kiều','Cái Khế',37),(35,'Cần Thơ','Ô Môn','Trà An',36),(36,'Cần Thơ','Cái Răng','An Lạc',33),(37,'Cần Thơ','Cái Răng','Thới Hòa',31),(38,'Cần Thơ','Cái Răng','Phước Thới',29),(39,'Cần Thơ','Cái Răng','Phú Thứ',20),(40,'Cần Thơ','Ô Môn','An Bình',17),(41,'Cần Thơ','Ninh Kiều','Tân Phú',16),(42,'Cần Thơ','Bình Thủy','An Nghiệp',15),(43,'Cần Thơ','Ninh Kiều','Lê Bình',9),(44,'Cần Thơ','Cái Răng','An Nghiệp',8),(45,'Cần Thơ','Bình Thủy','An Hội',7),(46,'Cần Thơ','Bình Thủy','Trường Lạc',6),(47,'Cần Thơ','Ninh Kiều','Xuân Khánh',5),(48,'Cần Thơ','Bình Thủy','An Bình',4),(49,'Cần Thơ','Cái Răng','Ba Láng',2),(50,'Cần Thơ','Ninh Kiều','An Hòa',1);
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount_after_tax` bigint DEFAULT NULL,
  `amount_before_tax` bigint DEFAULT NULL,
  `amount_tax` bigint DEFAULT NULL,
  `bill_code` varchar(255) DEFAULT NULL,
  `consumption` bigint NOT NULL,
  `issue_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tax_rate` double NOT NULL,
  `payment_id` bigint DEFAULT NULL,
  `meter_reading_id` bigint DEFAULT NULL,
  `date_update` date DEFAULT NULL,
  `total` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ktfr93h3u1qlelfgjbg44eq27` (`payment_id`),
  UNIQUE KEY `UK_g91j0gco4fi36gy2khajpxyof` (`meter_reading_id`),
  CONSTRAINT `FKivsv2kk65fwd2wf855n0bjqvw` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`),
  CONSTRAINT `FKk04fhafai4kgwo5v1cy2o0pbc` FOREIGN KEY (`meter_reading_id`) REFERENCES `meter_reading` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (3,36234,33550,2684,'708d87e66b81be77f1cf234ed3ec04d9',11,NULL,NULL,0.08,NULL,25,'2024-04-07',-1),(4,36234,33550,2684,'708d87e66b81be77f1cf234ed3ec04d9',11,NULL,NULL,0.08,NULL,26,'2024-04-07',-1);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `buyer_address` varchar(255) DEFAULT NULL,
  `buyer_email` varchar(255) DEFAULT NULL,
  `buyer_phone_number` varchar(255) DEFAULT NULL,
  `buyer_representation` varchar(255) DEFAULT NULL,
  `contract_code` varchar(255) DEFAULT NULL,
  `customer_code` varchar(255) DEFAULT NULL,
  `meter_installation_location` varchar(255) DEFAULT NULL,
  `seller_representation` varchar(255) DEFAULT NULL,
  `tax_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (1,'An Cư, Ô Môn, Cần Thơ','Prosser93@example.com','(867) 463-1103','Xuân Bình','CONTRACT414117208','CUS954183607','An Cư, Ô Môn, Cần Thơ','Thụy Trinh','8830135166'),(2,'Hưng Thạnh, Ô Môn, Cần Thơ','Hester563@example.com','(660) 431-1545','Yến Trinh','CONTRACT433935809','CUS033029428','Hưng Thạnh, Ô Môn, Cần Thơ','Văn Danh','0952614935'),(3,'An Bình, Cái Răng, Cần Thơ','itettvp5@example.com','(209) 889-1943','Thị Hương Chi','CONTRACT335907973','CUS072761966','An Bình, Cái Răng, Cần Thơ','Giang Lam','0742346152'),(4,'Thới Hòa, Bình Thủy, Cần Thơ','DarylHyman@nowhere.com','(737) 036-2723','Thị Như Hảo','CONTRACT468314161','CUS322344003','Thới Hòa, Bình Thủy, Cần Thơ','Bích Loan','1933050641'),(5,'An Cư, Cái Răng, Cần Thơ','ArdeliaSkaggs839@nowhere.com','(455) 939-2213','Chí Khiêm','CONTRACT893868581','CUS521865192','An Cư, Cái Răng, Cần Thơ','Thanh Trúc','3296643695'),(6,'Thường Thạnh, Bình Thủy, Cần Thơ','Neil719@nowhere.com','(225) 823-2001','Thị Hương Giang','CONTRACT665952130','CUS692144307','Thường Thạnh, Bình Thủy, Cần Thơ','Lan Ngọc','8958185834'),(7,'Trường Lạc, Bình Thủy, Cần Thơ','ValentineV.Gilmore@example.com','(971) 380-8194','Trọng Việt','CONTRACT592558210','CUS601027834','Trường Lạc, Bình Thủy, Cần Thơ','Phú Ân','6785791189'),(8,'Thới Hòa, Cái Răng, Cần Thơ','Boone@example.com','(133) 731-4169','Khương Duy','CONTRACT587866006','CUS693943308','Thới Hòa, Cái Răng, Cần Thơ','Văn Ðạo','4797172139'),(9,'Thới Bình, Bình Thủy, Cần Thơ','Minh_Echevarria@example.com','(190) 806-2163','Thị Như Hoa','CONTRACT981145528','CUS066276263','Thới Bình, Bình Thủy, Cần Thơ','Thanh Tuyền','7768079638'),(10,'An Nghiệp, Ninh Kiều, Cần Thơ','KandraAddison445@example.com','(450) 990-8078','Xuân Cao','CONTRACT093681414','CUS614895322','An Nghiệp, Ninh Kiều, Cần Thơ','Thị Mộng Liễu','0171536806'),(11,'Hưng Phú, Cái Răng, Cần Thơ','Girard@nowhere.com','(968) 374-8637','Thị Hương Lâm','CONTRACT790329999','CUS995805411','Hưng Phú, Cái Răng, Cần Thơ','Thị Thương','1542989009'),(12,'Trường Lạc, Ninh Kiều, Cần Thơ','MafaldaPannell355@nowhere.com','(698) 517-9087','Trọng Vinh','CONTRACT988592532','CUS341823024','Trường Lạc, Ninh Kiều, Cần Thơ','Yến Trinh','1508912404'),(13,'Thới Hòa, Cái Răng, Cần Thơ','Elizondo@example.com','(437) 589-7146','Thị Như Hồng','CONTRACT382693590','CUS064209499','Thới Hòa, Cái Răng, Cần Thơ','Thị Giáng Ngọc','3563706533'),(14,'Long Tuyền, Ô Môn, Cần Thơ','Ogden791@example.com','(231) 419-0565','Khánh Quỳnh','CONTRACT079871429','CUS225863359','Long Tuyền, Ô Môn, Cần Thơ','Bích Nga','5718575907'),(15,'Cái Khế, Ninh Kiều, Cần Thơ','Fine274@example.com','(705) 003-0314','Thị Hương Lan','CONTRACT202226913','CUS254987867','Cái Khế, Ninh Kiều, Cần Thơ','Thùy Uyên','5085453931'),(16,'Long Tuyền, Bình Thủy, Cần Thơ','Leigh_Damon951@nowhere.com','(783) 949-0418','Thị Triều Thanh','CONTRACT552183250','CUS559283874','Long Tuyền, Bình Thủy, Cần Thơ','Giang Nam','1519990074'),(17,'Long Tuyền, Bình Thủy, Cần Thơ','Balderas@example.com','(562) 957-1257','Xuân Cung','CONTRACT817890315','CUS866054232','Long Tuyền, Bình Thủy, Cần Thơ','Thị Mộng Nguyệt','3291753400'),(18,'Long Tuyền, Ninh Kiều, Cần Thơ','SturmO@example.com','(300) 191-1747','Quỳnh Phương','CONTRACT217872361','CUS447734495','Long Tuyền, Ninh Kiều, Cần Thơ','Lan Nhi','3115095870'),(19,'Lê Bình, Ô Môn, Cần Thơ','Gia.B_Bourque676@example.com','(751) 211-6394','Uyển My','CONTRACT139314260','CUS035491023','Lê Bình, Ô Môn, Cần Thơ','Thanh Tuyết','1300841350'),(20,'An Hòa, Bình Thủy, Cần Thơ','DiegoBickford894@example.com','(455) 497-6185','Thị Trinh','CONTRACT873321714','CUS459020495','An Hòa, Bình Thủy, Cần Thơ','Thị Thường','9802987041'),(21,'Cái Khế, Cái Răng, Cần Thơ','Maki@example.com','(889) 952-9273','Trúc Cương','CONTRACT280758457','CUS481148318','Cái Khế, Cái Răng, Cần Thơ','Phú Bình','1257502000'),(22,'An Hội, Ninh Kiều, Cần Thơ','Michal.Washburn588@nowhere.com','(637) 550-3722','Khánh Thi','CONTRACT941702625','CUS535464307','An Hội, Ninh Kiều, Cần Thơ','Giang Sơn','6187458080'),(23,'Hưng Thạnh, Cái Răng, Cần Thơ','SorayaMoon@example.com','(500) 048-6515','Chí Kiên','CONTRACT410863029','CUS428482506','Hưng Thạnh, Cái Răng, Cần Thơ','Thụy Uyên','6439942850'),(24,'Hưng Phú, Ninh Kiều, Cần Thơ','Gussie_O_Roberson@nowhere.com','(785) 291-8118','Khuyến Học','CONTRACT482308284','CUS706156364','Hưng Phú, Ninh Kiều, Cần Thơ','Thị Thương Huyền','0151402424'),(25,'Long Tuyền, Bình Thủy, Cần Thơ','nlbrpufr.akdgpgsktb@example.com','(131) 154-4805','Chí Nam','CONTRACT573033708','CUS734317529','Long Tuyền, Bình Thủy, Cần Thơ','Thị Giang Thanh','6918694065');
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `citizen_id` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  `contract_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_m6ki6ehnic75g94eg8xywe7mn` (`contract_id`),
  CONSTRAINT `FKg2o3t8h0g17smtr9jgypagdtv` FOREIGN KEY (`id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKtrajsv6tt0usxkoekrdw9064i` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('306336648144',3,19),('372385830471',10,6),('158983233552',11,24),('480826039451',12,1),('716921360938',13,7),('974523367107',14,17),('805189717686',18,18),('160741048299',19,2),('453223703686',21,12),('340073150082',22,8),('219138984459',23,3),('268108123475',24,22),('102098289152',25,9),('040246203116',26,13),('832502186427',27,4),('443579843028',28,15),('297054019651',30,10),('891256589193',32,25),('567488166507',34,5),('788333311494',35,20),('442050717255',38,23),('132966494328',39,11),('673762688385',40,21),('763874066580',41,16),('866327642254',50,14);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_code` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKd8il4lxw1wi74qh8b7uoy6e0a` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('NV68326','manager',1),('NV07826','manager',2),('NV57676','manager',4),('NV33485','worker',5),('NV89721','manager',6),('NV77737','manager',7),('NV67421','worker',8),('NV64012','manager',9),('NV31383','worker',15),('NV76997','worker',16),('NV60498','worker',17),('NV12833','manager',20),('NV48212','manager',29),('NV04422','manager',31),('NV84958','manager',33),('NV29570','worker',36),('NV66738','manager',37),('NV56065','manager',42),('NV75178','manager',43),('NV16742','worker',44),('NV90572','manager',45),('NV93188','manager',46),('NV17918','manager',47),('NV24975','worker',48),('NV58052','manager',49);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meter`
--

DROP TABLE IF EXISTS `meter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meter` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `installment_address` varchar(255) DEFAULT NULL,
  `installment_date` date DEFAULT NULL,
  `meter_code` varchar(255) DEFAULT NULL,
  `meter_type` varchar(255) DEFAULT NULL,
  `area_id` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `time_update` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fbkinh64p30ug9eotfjnm8a1p` (`customer_id`),
  KEY `FK9dxl36d3uwfwufy3c6jn1wfgq` (`area_id`),
  CONSTRAINT `FK9dxl36d3uwfwufy3c6jn1wfgq` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`),
  CONSTRAINT `FKdedcfebhg9odtgamk7lk9v3ud` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meter`
--

LOCK TABLES `meter` WRITE;
/*!40000 ALTER TABLE `meter` DISABLE KEYS */;
INSERT INTO `meter` VALUES (1,'Lê Bình, Ô Môn, Cần Thơ','2018-04-01','47828680','3',1,3,'2024-04-07'),(2,'Thường Thạnh, Bình Thủy, Cần Thơ','2020-01-01','24975247','3',2,10,'2024-03-16'),(3,'Hưng Phú, Ninh Kiều, Cần Thơ','2007-05-29','30464938','1',3,11,'2024-03-08'),(4,'An Cư, Ô Môn, Cần Thơ','2017-01-11','52090041','3',4,12,'2024-03-17'),(5,'Trường Lạc, Bình Thủy, Cần Thơ','2009-12-19','17853890','3',5,13,'2024-04-04'),(6,'Long Tuyền, Bình Thủy, Cần Thơ','2002-06-12','64782887','3',6,14,'2024-03-01'),(7,'Long Tuyền, Ninh Kiều, Cần Thơ','2005-03-07','08988556','3',7,18,'2024-03-17'),(8,'Hưng Thạnh, Ô Môn, Cần Thơ','2010-10-31','73173271','1',8,19,'2024-03-16'),(9,'Trường Lạc, Ninh Kiều, Cần Thơ','2000-01-01','27696313','1',9,21,'2024-04-04'),(10,'Thới Hòa, Cái Răng, Cần Thơ','2018-01-14','15111305','3',10,22,'2024-03-02'),(11,'An Bình, Cái Răng, Cần Thơ','2020-01-01','73767224','3',11,23,'2024-03-12'),(12,'An Hội, Ninh Kiều, Cần Thơ','2013-04-22','61760075','3',12,24,'2024-03-01'),(13,'Thới Bình, Bình Thủy, Cần Thơ','2011-08-28','31444492','1',13,25,'2024-04-05'),(14,'Thới Hòa, Cái Răng, Cần Thơ','2011-05-13','23553203','1',14,26,'2024-04-04'),(15,'Thới Hòa, Bình Thủy, Cần Thơ','2008-12-13','80181411','1',15,27,'2024-04-05'),(16,'Cái Khế, Ninh Kiều, Cần Thơ','2005-01-24','06042464','1',16,28,'2024-04-02'),(17,'An Nghiệp, Ninh Kiều, Cần Thơ','2004-03-10','31900210','3',17,30,'2024-03-21'),(18,'Long Tuyền, Bình Thủy, Cần Thơ','2016-11-17','19085905','3',18,32,'2024-03-11'),(19,'An Cư, Cái Răng, Cần Thơ','2008-05-02','41946729','1',19,34,'2024-03-03'),(20,'An Hòa, Bình Thủy, Cần Thơ','2005-02-28','58837941','3',20,35,'2024-03-22'),(21,'Hưng Thạnh, Cái Răng, Cần Thơ','2011-04-26','69766809','3',21,38,'2024-03-27'),(22,'Hưng Phú, Cái Răng, Cần Thơ','2020-01-01','73875727','3',22,39,'2024-03-10'),(23,'Cái Khế, Cái Răng, Cần Thơ','2018-12-11','71640042','1',23,40,'2024-03-08'),(24,'Long Tuyền, Bình Thủy, Cần Thơ','2017-12-20','05088464','3',24,41,'2024-03-19'),(25,'Lê Bình, Ô Môn, Cần Thơ','2011-06-27','26725508','1',1,50,'2024-04-07');
/*!40000 ALTER TABLE `meter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meter_reading`
--

DROP TABLE IF EXISTS `meter_reading`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meter_reading` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `current_reading` double DEFAULT NULL,
  `previous_reading` double DEFAULT NULL,
  `meter_id` bigint DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo49eappp4rr1r0f29jn6hb89x` (`meter_id`),
  CONSTRAINT `FKo49eappp4rr1r0f29jn6hb89x` FOREIGN KEY (`meter_id`) REFERENCES `meter` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meter_reading`
--

LOCK TABLES `meter_reading` WRITE;
/*!40000 ALTER TABLE `meter_reading` DISABLE KEYS */;
INSERT INTO `meter_reading` VALUES (25,11,0,1,'WAITING_FOR_PAYMENT'),(26,11,0,25,'WAITING_FOR_PAYMENT');
/*!40000 ALTER TABLE `meter_reading` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `payment_metod` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `authorization` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'employee','AntionetteGandy449@nowhere.com','Yến Trinh','zN2IdpA9wXB6FrqONOEL3g==','(461) 186-0904','nang2002'),(2,'employee','Allard291@example.com','Thị Hồng Ngân','b68RnryL33JGwIwJTLotOw==','(991) 178-4007',NULL),(3,'customer','SykesE@nowhere.com','Thị Nguyệt Nga','K0onOSj1eS5oBibur6Qmjg==','(977) 242-7073',NULL),(4,'employee','DoylePeltier@example.com','Việt Quốc','NmzGMsQQmjxkRmHH6GEfVg==','(350) 908-8937',NULL),(5,'employee','CarolinaMeredith@example.com','Tiến Võ','07EZFyP27osAIhSnaQmv/w==','(995) 327-2678',NULL),(6,'employee','EleneKincaid@nowhere.com','Thị Hồng Ngọc','xpEPJBPRVVEKe5wIzZsSbg==','(740) 048-2944',NULL),(7,'employee','eknpin600@example.com','Khải Hòa','U1Bwi12ADaNC1Xi0hk4jYw==','(903) 773-8854',NULL),(8,'employee','MarchelleKimball22@example.com','Việt Quyết','787Tpi/p+A1eZhwwL8NuuQ==','(559) 447-0375',NULL),(9,'employee','Altman@nowhere.com','Hoàn Châu','pwqEr9tPQdZ7v0DBp4zRog==','(344) 578-5342',NULL),(10,'customer','Steven.Gunn@example.com','Thị Tuệ Mẫn','llp2Qw6NNGJaekLGW0h3IA==','(265) 165-1497',NULL),(11,'customer','wdgnzbpu56@nowhere.com','Nhật Hạ','yF7DNsukGDc3YbmIzs2lxg==','(259) 435-8803',NULL),(12,'customer','Killian652@nowhere.com','Tiểu Bảo','8DLLPvA+a+2oTIETPmG5IQ==','(743) 833-3078',NULL),(13,'customer','Ivana_Mcdonnell47@example.com','Thị Nguyệt Quế','p38Q3CHGJUAFR5bsFaW+FA==','(268) 087-6236',NULL),(14,'customer','Jessenia.Stokes@example.com','Hoàn Vi','exfXrbnRpKjYTaQOZvyMEg==','(693) 323-8759',NULL),(15,'employee','VertieKimbrough@example.com','Vành Khuyên','AG1Iyb7AIscCxxX0xNtfbQ==','(608) 220-7016',NULL),(16,'employee','HumbertoJordon8@example.com','Thị Hồng Như','JAhAcSpEJV1unVISC5pCqg==','(166) 979-5923',NULL),(17,'employee','Mcintosh@example.com','Nhật Lan','xj5jLKXOEggnE3iNjhxPPg==','(718) 066-3213',NULL),(18,'customer','Mckinney@example.com','Cao Nghiệp','UnBpZGCugtpuKCw8IS8qaQ==','(860) 075-8307',NULL),(19,'customer','qpbhvnwv_cbglpzkztp@nowhere.com','Thị Nguyệt Uyển','NzS/x274AavpwsS+zMPPcA==','(849) 746-0463',NULL),(20,'employee','aavzoc08@example.com','Khai Minh','VqJLmhIsQ/47D+dpp9UH8Q==','(295) 049-5926',NULL),(21,'customer','Bobo@example.com','Thị Hồng Nhung','YD/0XEBJ67bLx9JLQWwztA==','(954) 139-1815',NULL),(22,'customer','Crockett33@nowhere.com','Thị Tuệ Nhi','cN5WvtvGdyH00ioq08lTNw==','(513) 082-5907',NULL),(23,'customer','Johanne_Vazquez591@example.com','Thị Nhã','SsE8StFD/b968sXS8/K6KA==','(976) 328-2305',NULL),(24,'customer','ChesterDuggan@nowhere.com','Thị Hồng Oanh','akb4aLh9HvskMEkaDImivA==','(664) 844-7856',NULL),(25,'customer','BelkisSommers@example.com','Thị Nhã Hồng','6hYQZrtB7wDBzXnTPtJJvQ==','(238) 160-5089',NULL),(26,'customer','LavernRoby@nowhere.com','Hoàng Cúc','/4gs1+szMIvMCZaRxalAnA==','(140) 493-5297',NULL),(27,'customer','Georgina_Flannery65@example.com','Cao Nguyên','yLCkg5uYcCotZACugnnumQ==','(218) 965-9090',NULL),(28,'customer','Paris25@example.com','Thị Hồng Phúc','v1QjFeoBpLCsbFPLx98Seg==','(119) 938-5048',NULL),(29,'employee','ColleneBolden@example.com','Viết Sơn','xr3jeDzEi1Kj5ddWLK4Jzg==','(293) 715-2229',NULL),(30,'customer','Darius_Riggins53@example.com','Toàn Thắng','coFH/RxwSy3SUT+dSUBJBw==','(799) 856-2063',NULL),(31,'employee','Blunt417@nowhere.com','Khải Tâm','0yA2tkppba6njvZqb6J07g==','(334) 627-7401',NULL),(32,'customer','Charolette_Jude4@example.com','Nhật Linh','AHWyFnEjA2fASW3b0rW1sw==','(301) 457-3124',NULL),(33,'employee','DawsonH@nowhere.com','Thị Nhã Hương','ylDgbFW+O7FdFBwtxAE0+A==','(367) 767-6926',NULL),(34,'customer','hzcydeu2980@example.com','Hoàng Hà','+X6D9kSasMCbt26+b0a4WA==','(123) 848-4540',NULL),(35,'customer','Hogg@example.com','Nhật Mai','C53q0VXGys4/oVAjnlYLWQ==','(142) 747-6673',NULL),(36,'employee','Jacobsen34@nowhere.com','Thị Hồng Phương','63tV765a6qDpN6rNerxlpQ==','(419) 626-8499',NULL),(37,'employee','Lumpkin@nowhere.com','Hoàng Kim','itC6fX3oCo//JZr8NkMwMg==','(995) 564-1142',NULL),(38,'customer','Catina_Provost@example.com','Vi Quyên','8HM9rEKjFvO8yWST31+yIQ==','(201) 327-1010',NULL),(39,'customer','Minerva.W.Shepard95@example.com','Thị Nhã Khanh','zrdAHp4NKJpMHtgh+NZzbQ==','(903) 984-5747',NULL),(40,'customer','Broyles156@example.com','Việt Sơn','FaK9cSBzN7iK9MIPQqkisw==','(890) 963-9643',NULL),(41,'customer','TedHill36@nowhere.com','Thị Tuệ Thi','EatuQcD5dKF9HvwJWd3+GQ==','(624) 460-4504',NULL),(42,'employee','Drake895@example.com','Việt Hà','4wFI4LXD7L2gXE8awLsV8w==','(765) 251-2702',NULL),(43,'employee','BranhamS@example.com','Nhật Phương','H6/s/ervds4VKz8PGjp+Eg==','(246) 839-7900',NULL),(44,'employee','GregoryEscalante553@nowhere.com','Tôn Lễ','iRCQiJRWEnZnniPyMZokpw==','(422) 963-6707',NULL),(45,'employee','Waterman@example.com','Thị Tùng Quân','GQh8jAk6TjBzxOzZqJruUw==','(975) 256-9624',NULL),(46,'employee','zvqzmmnt89@example.com','Viết Tân','0KC0PLSwtMLwdz6HU1T0zg==','(865) 251-9173',NULL),(47,'employee','Patrick_Austin54@nowhere.com','Hoàng Lan','6D7jbjo2aOFAVFv4zXimFA==','(346) 092-1997',NULL),(48,'employee','Dion@nowhere.com','Việt Hương','iGfG/K5yaGSan9F4MIiTtA==','(771) 925-8234',NULL),(49,'employee','Summers8@example.com','Cao Nhân','46LXceOX7sGPU2oK8wZBAw==','(739) 271-3463',NULL),(50,'customer','ukibwiwo.laupluxc@example.com','Thị Tường','2cIMOCeGTIoP7jc8nm4yvQ==','(389) 728-2534',NULL);
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

-- Dump completed on 2024-04-08 13:18:08
