CREATE DATABASE  IF NOT EXISTS `web_installation_tracker_stag` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `web_installation_tracker_stag`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 10.30.32.83    Database: web_installation_tracker_stag
-- ------------------------------------------------------
-- Server version	5.1.61

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
-- Table structure for table `installation_user_details`
--

DROP TABLE IF EXISTS `installation_user_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `installation_user_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `installation_id` int(11) NOT NULL,
  `installation_user` varchar(50) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `lastupdated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `installation_user_id_user` (`installation_id`,`installation_user`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `installation_user_details`
--

LOCK TABLES `installation_user_details` WRITE;
/*!40000 ALTER TABLE `installation_user_details` DISABLE KEYS */;
INSERT INTO `installation_user_details` VALUES (25,8,'varun.kumar@bcone.com','2017-07-11 12:27:02','2017-07-11 12:27:02'),(26,12,'azam.raza@bcone.com','2017-07-12 14:08:49','2017-07-12 14:08:49'),(23,9,'vijay.nagulakonda@bcone.com','2017-07-10 11:59:09','2017-07-10 11:59:09'),(16,1,'bhargav.thouti@bcone.com','2017-06-27 13:25:13','2017-06-27 13:25:13'),(19,4,'divyanshu.dutt@bcone.com','2017-07-06 16:06:27','2017-07-06 16:06:27'),(21,10,'sandhya.shukla@bcone.com','2017-07-07 17:20:16','2017-07-07 17:20:16');
/*!40000 ALTER TABLE `installation_user_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `installations`
--

DROP TABLE IF EXISTS `installations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `installations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(45) NOT NULL,
  `environment_type` varchar(45) NOT NULL,
  `version` varchar(45) NOT NULL,
  `middleware_location` varchar(500) NOT NULL,
  `schema_prefix` varchar(45) NOT NULL,
  `admin_server_HTTPPort` varchar(45) NOT NULL,
  `admin_server_HTTPSPort` varchar(45) NOT NULL,
  `managed_server_HTTPPort` varchar(45) NOT NULL,
  `managed_server_HTTPSPort` varchar(45) NOT NULL,
  `managed_server_2_HTTPPort` varchar(45) DEFAULT NULL,
  `managed_server_2_HTTPSPort` varchar(45) DEFAULT NULL,
  `status` char(1) NOT NULL,
  `installed_by` varchar(500) NOT NULL,
  `vncport` varchar(45) NOT NULL,
  `bits_location` varchar(400) NOT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `created_date` datetime NOT NULL,
  `lastupdated_date` datetime DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `installations`
--

LOCK TABLES `installations` WRITE;
/*!40000 ALTER TABLE `installations` DISABLE KEYS */;
INSERT INTO `installations` VALUES (1,'10.30.32.16','ICS IC','17.2.5.0.0 (170518.0327.0908)','/ICS/Oracle/Middleware_1725jun12','R1725S ','1916','1917','1918','6859',NULL,NULL,'A','meenakshi.l@bcone.com','2','10.30.32.123/ICS/ICS_SETUP',0,'2017-06-20 11:11:58','2017-06-21 12:31:30',NULL),(2,'10.30.32.16','ICS IC','17.2.5.0.0 (170518.0327.0908)','/ICS/Oracle/Middleware_RC51725','RC51725','1316','1317','1318','4356',NULL,NULL,'A','vijay.nagulakonda@bcone.com','1','10.30.32.123/ICS/ICS_SETUP',0,'2017-06-20 12:06:03',NULL,NULL),(4,'10.30.32.101','ICS IC','17.2.5.0.0 (170518.0327.0908)','/ICS/Oracle/Middleware1725RC5','RC5D1725','7990','7991','7992','7015',NULL,NULL,'A','meenakshi.l@bcone.com','1','10.30.32.101/ICS/ICS_SETUP',0,'2017-06-21 12:38:51',NULL,NULL),(5,'10.30.32.167','SOA 12C','12.2.1.3.0','/ICS/Oracle/Middleware_PS2_0220','SOA1213','1212','1213','1215','8002','1214','8001','A','meenakshi.l@bcone.com','1','10.30.32.83//ICS/Installer/Installers',0,'2017-06-23 16:08:28','2017-06-23 16:08:28',NULL),(6,'10.30.32.166','SOA 12C','12.2.1.0.0','/ICS/Oracle/Middleware_12210_jun30','SOA1221B','1993','1994','1995','1996','1997','1998','A','vijay.nagulakonda@bcone.com','1','/ICS/INSTALLERS',0,'2017-06-29 16:51:14','2017-07-03 10:56:06','vijay.nagulakonda@bcone.com'),(7,'10.30.32.168','ICS EC','17.3.3 - 170612.1816.0956','/ICS/Oracle/EC_1733Debug','ECA173','1618','1619','1620','8001','','','A','vijay.nagulakonda@bcone.com','1','10.30.32.83:/ICS/EC_SETUP',1,'2017-06-30 16:05:42','2017-07-11 11:11:25','meenakshi.l@bcone.com'),(8,'10.30.32.168','ICS EC','17.3.3 (170628.2106.1044)','/ICS/Oracle/EC_1733New','ECG173','1919','1920','1921','7201','','','A','vijay.nagulakonda@bcone.com','1','10.30.32.83:/ICS/EC_SETUP',0,'2017-06-30 17:08:26','2017-07-11 11:11:33','meenakshi.l@bcone.com'),(9,'10.30.32.101','ICS EC','17.3.3.0.0','/ICS/Oracle/EC_Middleware1733S','E1733CT1','1716','1717','1718','8081','','','A','himanshu.grover@bcone.com','2','10.30.32.101/ICS/EC_SETUP',0,'2017-07-04 14:38:45','2017-07-12 02:17:20','himanshu.grover@bcone.com'),(10,'10.30.32.167','ICS EC','17.3.3 (170628.2106.1044','/ICS/Oracle/MiddAS_17331','E1733CT2','1896','1897','1898','8003','','','A','meenakshi.l@bcone.com','2','/ICS/EC_SETUP/',0,'2017-07-06 20:17:20','2017-07-10 18:52:53','meenakshi.l@bcone.com'),(11,'10.30.32.16','ICS EC','17.3.3 (170628.2106.1044','/ICS/Oracle/EC_Mid1733AC','E1733AC','1896','1897','1898','9821','','','A','meenakshi.l@bcone.com','3','/ICS/EC_SETUP',0,'2017-07-07 18:24:32','2017-07-12 14:48:13','meenakshi.l@bcone.com'),(12,'10.30.32.16','ICS IC','17.2.5.0.0','/ICS/Oracle/Midd_1725_170417','OR1725AP17','7166','7167','7168','4358','','','A','azam.raza@bcone.com','3','10.30.32.16/ICS/ICS_SETUP',0,'2017-07-12 12:16:28','2017-07-12 12:16:54','azam.raza@bcone.com');
/*!40000 ALTER TABLE `installations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ip_info_details`
--

DROP TABLE IF EXISTS `ip_info_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_info_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(45) NOT NULL,
  `public_ip` varchar(45) DEFAULT NULL,
  `public_ports` varchar(4000) DEFAULT NULL,
  `public_domain_name` varchar(45) DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `created_date` datetime NOT NULL,
  `lastupdated_date` datetime DEFAULT NULL,
  `created_by` varchar(45) NOT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ip_UNIQUE` (`ip`),
  UNIQUE KEY `public_domain_name_UNIQUE` (`public_domain_name`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_info_details`
--

LOCK TABLES `ip_info_details` WRITE;
/*!40000 ALTER TABLE `ip_info_details` DISABLE KEYS */;
INSERT INTO `ip_info_details` VALUES (1,'10.30.32.101','180.179.99.139','9026,7015,7016,8025,8081,8026,8027,9037,9038,9039,7208','soasfdc.bcone.com',0,'2017-07-03 08:15:10','2017-07-03 08:27:05','himanshu.grover@bcone.com','himanshu.grover@bcone.com'),(2,'10.30.32.83','180.179.99.138','9055,9054,9013,9053,9014,9015,8923,8731,8619','soasf.bcone.com',0,'2017-07-03 08:15:53','2017-07-03 08:25:51','himanshu.grover@bcone.com','himanshu.grover@bcone.com'),(3,'10.30.32.147','180.179.99.147','8004,3060,3061,3062,3063,3064,3065,3066,3067,9035,9036','soaconcur.bcone.com',0,'2017-07-03 08:17:02','2017-07-03 08:17:02','himanshu.grover@bcone.com',NULL),(4,'10.30.32.167','180.179.99.129','7001,7002,7003,7004,8001,8002,8003,8004','soa.bcone.com',0,'2017-07-03 08:17:56','2017-07-03 08:17:56','himanshu.grover@bcone.com',NULL),(5,'10.30.32.168','180.179.99.153','8001,7123,7201,7389,7901,5554,9295,7901,9293,9294','adpterdemo.bcone.com',0,'2017-07-03 08:19:03','2017-07-03 08:19:03','himanshu.grover@bcone.com',NULL),(6,'10.30.32.16','180.179.99.133','6890,9821,4352,7512,7513,7514,4356,4357,4358,4359,6859,7167','soasn.bcone.com',0,'2017-07-03 08:20:22','2017-07-03 08:20:22','himanshu.grover@bcone.com',NULL),(7,'10.30.32.76','N/A on 10.30.32.76','','N/A on 10.30.32.76',0,'2017-07-11 22:40:44','2017-07-11 22:45:17','himanshu.grover@bcone.com','himanshu.grover@bcone.com'),(8,'10.30.32.166','N/A on 10.30.32.166','','N/A on 10.30.32.166',0,'2017-07-11 22:45:48','2017-07-11 22:45:48','himanshu.grover@bcone.com',NULL),(9,'10.30.32.126','N/A on 10.30.32.126','','N/A on 10.30.32.126',0,'2017-07-11 22:46:28','2017-07-11 22:46:28','himanshu.grover@bcone.com',NULL);
/*!40000 ALTER TABLE `ip_info_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-12 23:51:26
