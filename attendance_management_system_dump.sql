-- MySQL dump 10.13  Distrib 5.7.30, for Win64 (x86_64)
--
-- Host: localhost    Database: attendance_management_system
-- ------------------------------------------------------
-- Server version	5.7.30-log

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
-- Table structure for table `attendances`
--

DROP TABLE IF EXISTS `attendances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendances` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `break_finish_time` time NOT NULL,
  `break_time` time NOT NULL,
  `content` longtext NOT NULL,
  `created_at` datetime NOT NULL,
  `finish_time` time NOT NULL,
  `start_time` time NOT NULL,
  `updated_at` datetime NOT NULL,
  `work_date` date NOT NULL,
  `working_hours` time NOT NULL,
  `employee_id` int(11) NOT NULL,
  `approver` varchar(255) NOT NULL,
  `approval_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2mia0pcnmy2nddwedvfrt0w08` (`employee_id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendances`
--

LOCK TABLES `attendances` WRITE;
/*!40000 ALTER TABLE `attendances` DISABLE KEYS */;
INSERT INTO `attendances` VALUES (1,'13:04:00','00:30:00','aaa','2020-07-14 21:34:00','17:33:00','09:00:00','2020-08-10 22:17:35','2020-07-13','08:03:00',2,'未承認','1000-01-01'),(2,'12:55:00','01:30:00','test','2020-07-14 22:15:54','19:00:00','09:00:00','2020-08-05 21:32:43','2020-07-01','08:30:00',3,'青柳管理','2020-08-12'),(3,'14:15:00','01:05:00','test','2020-07-14 22:16:16','20:16:00','08:15:00','2020-08-09 23:51:44','2020-07-02','10:56:00',3,'青柳管理','2020-08-13'),(4,'12:58:00','01:00:00','test','2020-07-14 22:43:52','19:00:00','08:50:00','2020-08-10 22:19:11','2020-07-14','09:10:00',2,'未承認','1000-01-01'),(5,'13:05:00','01:05:00','test','2020-07-14 22:44:10','17:43:00','09:00:00','2020-07-23 22:15:20','2020-07-15','07:38:00',2,'未承認','1000-01-01'),(6,'13:00:00','01:02:00','test','2020-07-14 22:44:26','23:00:00','08:00:00','2020-07-23 22:17:14','2020-07-16','13:58:00',2,'未承認','1000-01-01'),(7,'15:15:00','02:10:00','test','2020-07-14 22:48:22','00:28:00','06:00:00','2020-07-23 22:18:12','2020-07-17','16:18:00',2,'未承認','1000-01-01'),(8,'13:00:00','01:10:00','test','2020-07-15 20:21:20','18:00:00','09:00:00','2020-08-09 18:22:29','2020-07-03','07:50:00',3,'未承認','1000-01-01'),(9,'13:00:00','01:00:00','test','2020-07-15 20:30:00','01:00:00','09:00:00','2020-07-23 22:19:27','2020-07-20','15:00:00',2,'未承認','1000-01-01'),(10,'12:30:00','01:05:00','est','2020-07-15 20:40:59','19:00:00','08:40:00','2020-08-09 23:54:02','2020-07-06','09:15:00',3,'青柳管理','2020-08-13'),(11,'13:00:00','01:00:00','test','2020-07-15 20:53:28','18:00:00','09:58:00','2020-07-23 21:30:10','2020-07-07','07:02:00',3,'未承認','1000-01-01'),(12,'12:30:00','01:00:00','test','2020-07-16 07:35:45','18:00:00','07:32:00','2020-07-23 21:35:59','2020-07-08','09:28:00',3,'未承認','1000-01-01'),(13,'00:00:00','01:10:00','t','2020-07-16 23:20:43','19:00:00','08:24:00','2020-08-09 23:58:28','2020-07-16','09:26:00',3,'未承認','1000-01-01'),(14,'00:00:00','00:10:00','てｓｔ','2020-07-19 15:44:26','18:00:00','13:00:00','2020-08-10 15:02:34','2020-07-09','04:50:00',3,'未承認','1000-01-01'),(15,'00:00:00','00:30:00','てｓｔ','2020-07-19 15:48:01','00:00:00','11:00:00','2020-08-10 14:35:57','2020-07-10','12:30:00',3,'未承認','1000-01-01'),(16,'00:00:00','01:00:00','ter','2020-08-04 23:44:27','14:00:00','09:00:00','2020-08-09 00:09:53','2020-08-03','04:00:00',3,'未承認','1000-01-01'),(17,'00:00:00','01:10:00','test','2020-08-08 15:49:42','20:00:00','08:40:00','2020-08-09 00:22:42','2020-08-04','10:10:00',3,'未承認','1000-01-01'),(19,'00:00:00','00:45:00','test','2020-08-12 16:14:34','20:00:00','10:00:00','2020-08-12 16:16:26','2020-07-21','09:15:00',2,'未承認','1000-01-01');
/*!40000 ALTER TABLE `attendances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `breaks`
--

DROP TABLE IF EXISTS `breaks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `breaks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `break_finish_time` time NOT NULL,
  `break_start_time` time NOT NULL,
  `work_date` date NOT NULL,
  `employee_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi00aorlo0qwjmyhiyy6tyh1mw` (`employee_id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `breaks`
--

LOCK TABLES `breaks` WRITE;
/*!40000 ALTER TABLE `breaks` DISABLE KEYS */;
INSERT INTO `breaks` VALUES (1,'12:00:00','11:30:00','2020-07-13',2),(5,'18:00:00','17:30:00','2020-07-01',3),(3,'13:10:00','12:10:00','2020-07-01',2),(4,'00:00:00','13:00:00','2020-07-02',2),(6,'13:00:00','12:00:00','2020-07-01',3),(7,'13:25:00','12:20:00','2020-07-01',2),(8,'14:00:00','13:35:00','2020-07-02',3),(9,'13:20:00','12:20:00','2020-08-03',3),(10,'13:00:00','12:10:00','2020-08-04',3),(11,'16:50:00','16:30:00','2020-08-04',3),(12,'12:40:00','11:30:00','2020-07-03',3),(13,'17:40:00','17:00:00','2020-07-02',3),(14,'13:00:00','11:55:00','2020-07-06',3),(15,'13:10:00','12:00:00','2020-07-16',3),(16,'14:30:00','14:00:00','2020-07-10',3),(17,'15:10:00','15:00:00','2020-07-09',3),(18,'13:00:00','12:00:00','2020-07-14',2),(19,'13:45:00','13:00:00','2020-07-21',2),(20,'00:00:00','18:00:00','2020-07-21',2);
/*!40000 ALTER TABLE `breaks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_flag` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `delete_flag` int(11) NOT NULL,
  `employeeCode` varchar(255) NOT NULL,
  `employeeName` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `sectionCode` varchar(255) NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2emrcmu9mslnq2xuat7u7jwh2` (`employeeCode`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,0,'2020-07-08 22:35:31',1,'ZZZZ1234','青柳ユーザー','ECA8C0C5F28669EBDF3D26E5C809FFDA3159B0B0841DAC737929C9BAD4BE243A','9999','2020-07-08 22:37:09'),(2,1,'2020-07-08 22:36:23',0,'ZZZZ5678','青柳管理','02484182500DC154D3312B73D4F062C26AEE4D53D7228DEB145C814E755F53A0','9999','2020-07-09 07:28:03'),(3,0,'2020-07-09 20:27:36',0,'AAAA1234','山田太郎','ECA8C0C5F28669EBDF3D26E5C809FFDA3159B0B0841DAC737929C9BAD4BE243A','1000','2020-07-09 22:42:09');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-13 10:29:04
