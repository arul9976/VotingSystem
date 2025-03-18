-- MySQL dump 10.13  Distrib 8.0.40, for Linux (x86_64)
--
-- Host: localhost    Database: VotingSystem
-- ------------------------------------------------------
-- Server version	8.0.40-0ubuntu0.24.04.1

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
-- Table structure for table `Candidate`
--

DROP TABLE IF EXISTS `Candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Candidate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `candidateId` varchar(50) DEFAULT NULL,
  `party` varchar(100) DEFAULT NULL,
  `manifesto` text,
  `voterId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `candidateId` (`candidateId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Candidate`
--

LOCK TABLES `Candidate` WRITE;
/*!40000 ALTER TABLE `Candidate` DISABLE KEYS */;
INSERT INTO `Candidate` VALUES (1,'CANDIDATE2025_01','DKE','Free Hospitality',2),(2,'CANDIDATE2025_02','SKJ','Advancement in Medical',3),(3,'CANDIDATE2025_03','ESM','Digital Advancement',4);
/*!40000 ALTER TABLE `Candidate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Election`
--

DROP TABLE IF EXISTS `Election`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Election` (
  `id` int NOT NULL AUTO_INCREMENT,
  `electionId` varchar(50) DEFAULT NULL,
  `electionName` varchar(50) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `status` enum('UPCOMING','ONGOING','ENDED') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `electionId` (`electionId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Election`
--

LOCK TABLES `Election` WRITE;
/*!40000 ALTER TABLE `Election` DISABLE KEYS */;
INSERT INTO `Election` VALUES (1,'Election2025_01','TN2025','2025-01-29 15:50:00','2025-01-29 16:10:00','ENDED');
/*!40000 ALTER TABLE `Election` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Voter`
--

DROP TABLE IF EXISTS `Voter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Voter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `age` smallint NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `voterId` varchar(50) DEFAULT NULL,
  `isEligible` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `voterId` (`voterId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Voter`
--

LOCK TABLES `Voter` WRITE;
/*!40000 ALTER TABLE `Voter` DISABLE KEYS */;
INSERT INTO `Voter` VALUES (1,'Arulkumar',20,'arul@gmail.com','$2a$12$Ms1g3r5mzNhXrZ.WGA6Ea.yW3/V/RIfMCKm4e8irZzV1CE/Xu4tAq','VOTERID_2025_01',1),(2,'Ramu',45,'ramu@gmail.com','$2a$12$1ViNxiLSpiQq5HKnOYnNiuV2yaN5jBMD9duDjvbIZCvgZ.4pRyEp.','VOTERID_2025_02',1),(3,'Somu',37,'somu@gmail.com','$2a$12$Q.PTuFvKnHrgVsRKz4.ojevKVvZnh3XK03pxHqUqBnqec1VPXFlqm','VOTERID_2025_03',1),(4,'Remo',33,'remu@gmail.com','$2a$12$2NuNdpmnUFLkJfyaInbC3.OrbSMkEGdDo6k34fYPejwHW2wrrMy2a','VOTERID_2025_04',1),(5,'Kavi',19,'kavi@gmail.com','$2a$12$EBCO.9ifvH65MrHWTnYrfeBc53YqQ0dGJPkF74wDuZZe8qp/rAMbS','VOTERID_2025_05',1),(6,'Kabil',19,'kabil@gmail.com','$2a$12$EwGIze45PMsecpvyw4srrO7ZJtJJBVOZt3Qik0wiNTM9GFQ.yQxvy','VOTERID_2025_06',1);
/*!40000 ALTER TABLE `Voter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Voter_Election`
--

DROP TABLE IF EXISTS `Voter_Election`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Voter_Election` (
  `id` int NOT NULL AUTO_INCREMENT,
  `voterId` int DEFAULT NULL,
  `candidateId` int DEFAULT NULL,
  `electionId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `voterId` (`voterId`),
  KEY `candidateId` (`candidateId`),
  KEY `electionId` (`electionId`),
  CONSTRAINT `Voter_Election_ibfk_1` FOREIGN KEY (`voterId`) REFERENCES `Voter` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Voter_Election_ibfk_2` FOREIGN KEY (`candidateId`) REFERENCES `Candidate` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Voter_Election_ibfk_3` FOREIGN KEY (`electionId`) REFERENCES `Election` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Voter_Election`
--

LOCK TABLES `Voter_Election` WRITE;
/*!40000 ALTER TABLE `Voter_Election` DISABLE KEYS */;
INSERT INTO `Voter_Election` VALUES (1,2,1,1),(2,3,2,1),(3,4,3,1),(4,1,1,1),(5,5,3,1);
/*!40000 ALTER TABLE `Voter_Election` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Votes`
--

DROP TABLE IF EXISTS `Votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Votes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `veId` int DEFAULT NULL,
  `totalVotes` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `veId` (`veId`),
  CONSTRAINT `Votes_ibfk_1` FOREIGN KEY (`veId`) REFERENCES `Voter_Election` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Votes`
--

LOCK TABLES `Votes` WRITE;
/*!40000 ALTER TABLE `Votes` DISABLE KEYS */;
INSERT INTO `Votes` VALUES (1,1,1),(2,2,0),(3,3,1);
/*!40000 ALTER TABLE `Votes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-01 10:04:23
