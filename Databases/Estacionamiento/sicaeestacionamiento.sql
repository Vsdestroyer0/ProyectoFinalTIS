-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: localhost    Database: sicaeestacionamiento
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `espacioestacionamiento`
--

DROP TABLE IF EXISTS `espacioestacionamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `espacioestacionamiento` (
  `idEspacio` int NOT NULL AUTO_INCREMENT,
  `claveEspacio` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tipo` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ocupado` bit(1) NOT NULL,
  `estatus` bit(1) NOT NULL,
  PRIMARY KEY (`idEspacio`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `espacioestacionamiento`
--

LOCK TABLES `espacioestacionamiento` WRITE;
/*!40000 ALTER TABLE `espacioestacionamiento` DISABLE KEYS */;
INSERT INTO `espacioestacionamiento` VALUES (1,'A001','A',_binary '\0',_binary ''),(2,'A002','A',_binary '',_binary ''),(3,'A003','A',_binary '\0',_binary ''),(4,'A004','A',_binary '\0',_binary ''),(5,'A005','A',_binary '\0',_binary ''),(6,'A006','A',_binary '\0',_binary ''),(7,'A007','A',_binary '\0',_binary ''),(8,'A008','A',_binary '\0',_binary ''),(9,'A009','A',_binary '\0',_binary ''),(10,'A010','A',_binary '\0',_binary ''),(11,'A011','A',_binary '\0',_binary ''),(12,'A012','A',_binary '\0',_binary ''),(13,'A013','A',_binary '\0',_binary ''),(14,'A014','A',_binary '\0',_binary ''),(15,'A015','A',_binary '\0',_binary ''),(16,'A016','A',_binary '\0',_binary ''),(17,'A017','A',_binary '\0',_binary ''),(18,'A018','A',_binary '\0',_binary ''),(19,'A019','A',_binary '\0',_binary ''),(20,'A020','A',_binary '\0',_binary ''),(21,'A021','A',_binary '\0',_binary ''),(22,'A022','A',_binary '\0',_binary ''),(23,'A023','A',_binary '\0',_binary ''),(24,'A024','A',_binary '\0',_binary ''),(25,'A025','A',_binary '\0',_binary ''),(26,'A026','A',_binary '\0',_binary ''),(27,'A027','A',_binary '\0',_binary ''),(28,'A028','A',_binary '\0',_binary ''),(29,'A029','A',_binary '\0',_binary ''),(30,'A030','A',_binary '\0',_binary ''),(31,'A031','A',_binary '\0',_binary ''),(32,'A032','A',_binary '\0',_binary ''),(33,'A033','A',_binary '\0',_binary ''),(34,'A034','A',_binary '\0',_binary ''),(35,'A035','A',_binary '\0',_binary ''),(36,'A036','A',_binary '\0',_binary ''),(37,'A037','A',_binary '\0',_binary ''),(38,'A038','A',_binary '\0',_binary ''),(39,'A039','A',_binary '\0',_binary ''),(40,'A040','A',_binary '\0',_binary ''),(41,'A041','A',_binary '\0',_binary ''),(42,'A042','A',_binary '\0',_binary ''),(43,'A043','A',_binary '\0',_binary ''),(44,'A044','A',_binary '\0',_binary ''),(45,'A045','A',_binary '\0',_binary ''),(46,'A046','A',_binary '\0',_binary ''),(47,'A047','A',_binary '\0',_binary ''),(48,'A048','A',_binary '\0',_binary ''),(49,'A049','A',_binary '\0',_binary ''),(50,'A050','A',_binary '\0',_binary ''),(51,'A051','A',_binary '\0',_binary ''),(52,'A052','A',_binary '\0',_binary ''),(53,'A053','A',_binary '\0',_binary ''),(54,'A054','A',_binary '\0',_binary ''),(55,'A055','A',_binary '\0',_binary ''),(56,'A056','A',_binary '\0',_binary ''),(57,'A057','A',_binary '\0',_binary ''),(58,'A058','A',_binary '\0',_binary ''),(59,'A059','A',_binary '\0',_binary ''),(60,'A060','A',_binary '\0',_binary ''),(61,'A061','A',_binary '\0',_binary ''),(62,'A062','A',_binary '\0',_binary ''),(63,'A063','A',_binary '\0',_binary ''),(64,'A064','A',_binary '\0',_binary ''),(65,'A065','A',_binary '\0',_binary ''),(66,'A066','A',_binary '\0',_binary ''),(67,'A067','A',_binary '\0',_binary ''),(68,'A068','A',_binary '\0',_binary ''),(69,'A069','A',_binary '\0',_binary ''),(70,'A070','A',_binary '\0',_binary ''),(71,'A071','A',_binary '\0',_binary ''),(72,'A072','A',_binary '\0',_binary ''),(73,'A073','A',_binary '\0',_binary ''),(74,'A074','A',_binary '\0',_binary ''),(75,'A075','A',_binary '\0',_binary ''),(76,'M001','M',_binary '\0',_binary ''),(77,'M002','M',_binary '\0',_binary ''),(78,'M003','M',_binary '\0',_binary ''),(79,'M004','M',_binary '\0',_binary ''),(80,'M005','M',_binary '\0',_binary ''),(81,'M006','M',_binary '\0',_binary ''),(82,'M007','M',_binary '\0',_binary ''),(83,'M008','M',_binary '\0',_binary ''),(84,'M009','M',_binary '\0',_binary ''),(85,'M010','M',_binary '\0',_binary ''),(86,'M011','M',_binary '\0',_binary ''),(87,'M012','M',_binary '\0',_binary ''),(88,'M013','M',_binary '\0',_binary ''),(89,'M014','M',_binary '\0',_binary ''),(90,'M015','M',_binary '\0',_binary ''),(91,'M016','M',_binary '\0',_binary ''),(92,'M017','M',_binary '\0',_binary ''),(93,'M018','M',_binary '\0',_binary ''),(94,'M019','M',_binary '\0',_binary ''),(95,'M020','M',_binary '\0',_binary ''),(96,'M021','M',_binary '\0',_binary ''),(97,'M022','M',_binary '\0',_binary ''),(98,'M023','M',_binary '\0',_binary ''),(99,'M024','M',_binary '\0',_binary ''),(100,'M025','M',_binary '\0',_binary '');
/*!40000 ALTER TABLE `espacioestacionamiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento` (
  `idMovimiento` int NOT NULL AUTO_INCREMENT,
  `idVehiculo` int NOT NULL,
  `tiempoEntrada` timestamp NOT NULL,
  `tiempoSalida` timestamp NULL DEFAULT NULL,
  `minutosEstacionado` int DEFAULT NULL,
  `horasCobradas` int DEFAULT NULL,
  `costoTotal` decimal(10,2) DEFAULT NULL,
  `tarifaHora` decimal(10,2) NOT NULL,
  `tiempoCreacion` timestamp NULL DEFAULT NULL,
  `tiempoActualizacion` timestamp NULL DEFAULT NULL,
  `idEspacio` int NOT NULL,
  PRIMARY KEY (`idMovimiento`) USING BTREE,
  KEY `idEspacio` (`idEspacio`) USING BTREE,
  CONSTRAINT `movimiento_ibfk_1` FOREIGN KEY (`idEspacio`) REFERENCES `espacioestacionamiento` (`idEspacio`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento`
--

LOCK TABLES `movimiento` WRITE;
/*!40000 ALTER TABLE `movimiento` DISABLE KEYS */;
INSERT INTO `movimiento` VALUES (1,1,'2026-06-16 20:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-16 20:00:00','2026-06-16 22:30:00',1),(2,1,'2026-06-16 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-16 10:00:00','2026-06-16 22:30:00',1),(3,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(4,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(5,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(6,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(7,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(8,2,'2026-06-18 10:00:00',NULL,NULL,NULL,NULL,15.00,'2026-06-18 10:00:00',NULL,2),(9,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(10,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(11,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(12,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(13,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(14,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(15,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(16,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(17,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1),(18,1,'2026-06-18 10:00:00','2026-06-16 22:30:00',150,3,45.00,15.00,'2026-06-18 10:00:00','2026-06-16 22:30:00',1);
/*!40000 ALTER TABLE `movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `movimientofullinfo`
--

DROP TABLE IF EXISTS `movimientofullinfo`;
/*!50001 DROP VIEW IF EXISTS `movimientofullinfo`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `movimientofullinfo` AS SELECT 
 1 AS `idMovimiento`,
 1 AS `idVehiculo`,
 1 AS `tiempoEntrada`,
 1 AS `tiempoSalida`,
 1 AS `minutosEstacionado`,
 1 AS `horasCobradas`,
 1 AS `costoTotal`,
 1 AS `tarifaHora`,
 1 AS `tiempoCreacion`,
 1 AS `tiempoActualizacion`,
 1 AS `idEspacio`,
 1 AS `claveEspacio`,
 1 AS `tipoEspacio`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `movimientofullinfo`
--

/*!50001 DROP VIEW IF EXISTS `movimientofullinfo`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `movimientofullinfo` AS select `m`.`idMovimiento` AS `idMovimiento`,`m`.`idVehiculo` AS `idVehiculo`,`m`.`tiempoEntrada` AS `tiempoEntrada`,`m`.`tiempoSalida` AS `tiempoSalida`,`m`.`minutosEstacionado` AS `minutosEstacionado`,`m`.`horasCobradas` AS `horasCobradas`,`m`.`costoTotal` AS `costoTotal`,`m`.`tarifaHora` AS `tarifaHora`,`m`.`tiempoCreacion` AS `tiempoCreacion`,`m`.`tiempoActualizacion` AS `tiempoActualizacion`,`m`.`idEspacio` AS `idEspacio`,`ee`.`claveEspacio` AS `claveEspacio`,`ee`.`tipo` AS `tipoEspacio` from (`movimiento` `m` join `espacioestacionamiento` `ee` on((0 <> `ee`.`idEspacio`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-19  4:39:15
