-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: LMS
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `contact` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `userID_UNIQUE` (`userID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  CONSTRAINT `fk_user_admin` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (101,'Sarthak','Gupta','98989989','sarthak');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `Bookid` int NOT NULL,
  `Title` varchar(45) DEFAULT 'Null',
  `ISBN` varchar(45) DEFAULT 'Null',
  `AvailableCopies` int DEFAULT NULL,
  `Status` varchar(45) DEFAULT 'Not Issued',
  `Author` varchar(45) DEFAULT 'Null',
  `PublicationYear` int DEFAULT NULL,
  `Rack_No.` int DEFAULT NULL,
  `Genre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Bookid`),
  UNIQUE KEY `Title_UNIQUE` (`Title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (100,'Tinkling of the bells before it rings','112223344-0',9,'Issued','Sonika Shandilya',2017,4,'Fiction'),(111,'Discovery of India','817525766-0',5,'Not Issued','Jawahar Lal Nehru',1969,7,'History'),(200,'Fundamentals of Computer','232324256-0',2,'Issued','Aanand Jain',2019,3,'Computer'),(222,'Polynomials','717662345-0',4,'Issued','R.D. Sharma',2004,1,'Mathematics'),(300,'Business Communication','777777777-0',3,'Issued','B.M.Shaikh',2013,3,'Business'),(333,'Computer Oriented Statistical Methods','331144567-0',2,'Issued','G.S.Makkar',2016,3,'Statistics'),(400,'Data Structures and Algorithms-I','555555532-0',2,'Issued','Dr.Poonam Ponde',2020,3,'Computer Science'),(444,'Multimedia System','233367111-0',1,'Not Issued','M.Prakash',2018,2,'Multimedia'),(500,'Operations Research','222222222-0',3,'Issued','Prof.Arun Pandhari',2021,1,'Mathematics'),(555,'Financial Accounting','111122333-0',6,'Issued','S.P.Gupta',2017,5,'Accounts'),(600,'Program Solving and C Programming','111111111-0',2,'Not Issued','Poonam Ponde',2019,3,'Programming'),(666,'You Only Live Once','777723456-0',3,'Issued','Stuti Changle',2021,4,'Fiction'),(700,'Business and Management','222222222-0',1,'Issued','Dr.R.L.Nolakha',2011,6,'Business'),(777,'Sherlock Holmes','333456723-0',7,'Issued','Arthur Conan Doyle',2017,4,'Mystery'),(800,'Business Economics','333333333-0',2,'Issued','Dr.M.D.Agarwal',2014,6,'Business'),(888,'Think Like A Monk','222334611-0',8,'Not Issued','Jay Shetty',2020,4,'Fiction'),(900,'Principles Of Management','444444444-0',3,'Issued','J.Jayashankar',2018,6,'Business'),(999,'The Alchemist','111333444-0',5,'Not Isued','Pauelo Choelo',2015,4,'Fiction');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `librarian`
--

DROP TABLE IF EXISTS `librarian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `librarian` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `Contact` varchar(45) NOT NULL,
  `Username` varchar(45) NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `userid_UNIQUE` (`userid`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  CONSTRAINT `fk_user_librarian` FOREIGN KEY (`Username`) REFERENCES `user` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarian`
--

LOCK TABLES `librarian` WRITE;
/*!40000 ALTER TABLE `librarian` DISABLE KEYS */;
INSERT INTO `librarian` VALUES (111,'Shyam','Solanki','78787878','shyam');
/*!40000 ALTER TABLE `librarian` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `password` varchar(45) NOT NULL DEFAULT 'pass123',
  `username` varchar(45) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `userid_UNIQUE` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'pass123','sarthak','admin'),(2,'pass123','shyam','librarian'),(3,'pass123','Karan_Garg001','librarian'),(4,'pass123','Shubham_Gill','librarian'),(5,'pass123','sarin@gupta','admin'),(6,'pass123','kaushik@123','admin'),(7,'pass123','khandelwal.naina','admin'),(8,'pass123','modi@111','admin'),(9,'pass123','rastogi@raman','admin');
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

-- Dump completed on 2024-03-19 17:26:23
