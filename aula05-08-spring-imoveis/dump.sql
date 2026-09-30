-- MySQL dump 10.13  Distrib 9.7.1, for macos26.6 (arm64)
--
-- Host: localhost    Database: restaurante
-- ------------------------------------------------------
-- Server version	9.7.1

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '58e9ac4a-7590-11f1-aaf5-dfd176dced0f:1-163';

--
-- Table structure for table `bairros`
--

DROP TABLE IF EXISTS `bairros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bairros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cep_inicial` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cidade` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cep_final` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bairros`
--

LOCK TABLES `bairros` WRITE;
/*!40000 ALTER TABLE `bairros` DISABLE KEYS */;
INSERT INTO `bairros` VALUES (1,'98280-000','Panambi','RS','Centro',NULL,'2026-09-29 21:20:01.888593','2026-09-29 21:20:01.888593'),(2,'98280-000','Panambi','RS','Centro',NULL,'2026-09-29 21:20:01.888593','2026-09-29 21:20:01.888593'),(3,'98280-000','Panambi','RS','Centro',NULL,'2026-09-29 21:20:01.888593','2026-09-29 21:20:01.888593'),(4,'98280-000','Panambi','RS','Centro',NULL,'2026-09-29 21:20:01.888593','2026-09-29 21:20:01.888593');
/*!40000 ALTER TABLE `bairros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imoveis`
--

DROP TABLE IF EXISTS `imoveis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imoveis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `area_construida` decimal(10,2) DEFAULT NULL,
  `area_total` decimal(10,2) DEFAULT NULL,
  `banheiros` int DEFAULT NULL,
  `caracteristicas` text COLLATE utf8mb4_unicode_ci,
  `cep` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `complemento` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `descricao` text COLLATE utf8mb4_unicode_ci,
  `destaque` bit(1) DEFAULT NULL,
  `dormitorios` int DEFAULT NULL,
  `endereco` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `finalidade` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `garagem` int DEFAULT NULL,
  `numero` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `preco_aluguel` decimal(10,2) DEFAULT NULL,
  `preco_venda` decimal(10,2) DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `titulo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `bairro_id` int DEFAULT NULL,
  `tipo_imovel_id` int DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlbe75ud15gr1jhci27a5febtj` (`bairro_id`),
  KEY `FK8l4n1619tyasatoa0a7051ins` (`tipo_imovel_id`),
  KEY `FK2ay3ugbb29heqhnjceltmh8ay` (`usuario_id`),
  CONSTRAINT `FK2ay3ugbb29heqhnjceltmh8ay` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FK8l4n1619tyasatoa0a7051ins` FOREIGN KEY (`tipo_imovel_id`) REFERENCES `tipos_imoveis` (`id`),
  CONSTRAINT `FKlbe75ud15gr1jhci27a5febtj` FOREIGN KEY (`bairro_id`) REFERENCES `bairros` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imoveis`
--

LOCK TABLES `imoveis` WRITE;
/*!40000 ALTER TABLE `imoveis` DISABLE KEYS */;
INSERT INTO `imoveis` VALUES (1,180.75,300.50,2,'Piscina, Churrasqueira, Jardim','12345-678','Casa 02','2026-09-29 21:20:11.828087','Excelente casa em bairro tranquilo, próximo a escolas e comércios.',_binary '',3,'Rua das Flores','Residencial',2,'123',3500.00,850000.00,'Disponível','Casa ampla com piscina','2026-09-29 21:20:11.828135',1,1,2);
/*!40000 ALTER TABLE `imoveis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imovel`
--

DROP TABLE IF EXISTS `imovel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imovel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `area_construida` double DEFAULT NULL,
  `area_total` double DEFAULT NULL,
  `banheiros` int DEFAULT NULL,
  `caracteristicas` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cep` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `complemento` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `descricao` text COLLATE utf8mb4_unicode_ci,
  `destaque` bit(1) DEFAULT NULL,
  `dormitorios` int DEFAULT NULL,
  `endereco` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `finalidade` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `garagem` int DEFAULT NULL,
  `numero` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `preco_aluguel` decimal(38,2) DEFAULT NULL,
  `preco_venda` decimal(38,2) DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `titulo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bairro_id` int DEFAULT NULL,
  `tipo_imovel_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK755ou89fmdigoyhobcocqb81i` (`bairro_id`),
  KEY `FKkflajyafc1kqbqivw6jwoy93k` (`tipo_imovel_id`),
  CONSTRAINT `FK755ou89fmdigoyhobcocqb81i` FOREIGN KEY (`bairro_id`) REFERENCES `bairros` (`id`),
  CONSTRAINT `FKkflajyafc1kqbqivw6jwoy93k` FOREIGN KEY (`tipo_imovel_id`) REFERENCES `tipos_imoveis` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imovel`
--

LOCK TABLES `imovel` WRITE;
/*!40000 ALTER TABLE `imovel` DISABLE KEYS */;
INSERT INTO `imovel` VALUES (1,180.75,300.5,2,'Piscina, Churrasqueira, Jardim','12345-678','Casa 02','Excelente casa em bairro tranquilo, próximo a escolas e comércios.',_binary '',3,'Rua das Flores','Residencial',2,'123',3500.00,850000.00,'Disponível','Casa ampla com piscina',1,1);
/*!40000 ALTER TABLE `imovel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_imoveis`
--

DROP TABLE IF EXISTS `tipos_imoveis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipos_imoveis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_imoveis`
--

LOCK TABLES `tipos_imoveis` WRITE;
/*!40000 ALTER TABLE `tipos_imoveis` DISABLE KEYS */;
INSERT INTO `tipos_imoveis` VALUES (1,'Casa residencial','Casa','2026-09-29 21:20:01.928745','2026-09-29 21:20:01.928745');
/*!40000 ALTER TABLE `tipos_imoveis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `senha` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipo` enum('ADMIN','COMPRADOR','VENDEDOR') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'2026-09-08 19:49:18.000000','john.doe@example.com','John Doe','changeme123','COMPRADOR','2026-09-08 19:49:18.000000'),(2,'2026-09-08 19:49:18.000000','jane.smith@example.com','Jane Smith','changeme123','VENDEDOR','2026-09-08 20:20:30.044707'),(3,'2026-09-08 19:49:18.000000','ariel.dss@example.com','Ariel Dornelles','changeme123','ADMIN','2026-09-08 19:49:18.000000'),(4,'2026-09-22 20:00:32.261993','teste@ex.com','Teste','123456','COMPRADOR','2026-09-22 20:00:32.262100'),(5,'2026-09-22 20:02:17.356097','teste2@ex.com','Teste2','123456','COMPRADOR','2026-09-22 20:02:17.356145');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-29 21:25:37
