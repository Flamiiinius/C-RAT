-- MySQL dump 10.13  Distrib 5.7.23, for Win64 (x86_64)
--
-- Host: localhost    Database: masterthesis
-- ------------------------------------------------------
-- Server version	5.7.23-log

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
-- Table structure for table `risks`
--

DROP TABLE IF EXISTS `risks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `risks` (
  `riskId` int(11) NOT NULL AUTO_INCREMENT,
  `manager` int(11) NOT NULL,
  `riskOwner` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `asset` varchar(255) NOT NULL,
  `creationDate` date NOT NULL,
  `nrOccurYear` double DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `costyear` double DEFAULT NULL,
  PRIMARY KEY (`riskId`),
  UNIQUE KEY `riskId_UNIQUE` (`riskId`)
) ENGINE=InnoDB AUTO_INCREMENT=1032 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risks`
--

LOCK TABLES `risks` WRITE;
/*!40000 ALTER TABLE `risks` DISABLE KEYS */;
INSERT INTO `risks` VALUES (1013,2,1,'Scenario1','Asset1','2018-12-07',4,400,1600),(1017,2,4,'Scenario2','Asset1','2019-01-04',5,250,1250),(1018,8,11,'Scenario2','Asset2','2019-01-04',5.300000190734863,112.75,597.5750122070312),(1019,9,11,'Scenario1','Asset2','2019-01-04',3,150,450),(1020,9,10,'Scenario3','Asset5','2019-01-04',4,100,400),(1021,2,1,'Scenario5','Asset10','2019-01-04',3.5,299.25,1047.375),(1022,2,3,'Scenario1','Asset3','2019-01-04',2,750,1500),(1023,2,1,'Scenario6','Asset6','2019-01-04',70,5000,350000),(1025,2,1,'Scenario6','Asset4','2019-01-04',6,100,600),(1029,8,11,'Scenario5','asset7','2019-01-04',3,6,18),(1030,8,1,'Scenario4','Asset3','2019-01-04',1,23,23),(1031,8,10,'Scenario4','Asset8','2019-01-04',89,89,7921);
/*!40000 ALTER TABLE `risks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `sessionId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `clientPublicKey` blob,
  `sessionHash` blob NOT NULL,
  PRIMARY KEY (`sessionId`),
  UNIQUE KEY `sessionId_UNIQUE` (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userpassword`
--

DROP TABLE IF EXISTS `userpassword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userpassword` (
  `UserId` int(11) NOT NULL,
  `salt` tinyblob NOT NULL,
  `Hash` blob NOT NULL,
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `UserId_UNIQUE` (`UserId`),
  UNIQUE KEY `userpassword_UserId_uindex` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userpassword`
--

LOCK TABLES `userpassword` WRITE;
/*!40000 ALTER TABLE `userpassword` DISABLE KEYS */;
INSERT INTO `userpassword` VALUES (1,_binary 'RÕÅî\rü$(\»´âdy±åû\‚Gà\—í≤º!MRL\Â(V',_binary '@@\≈¸êíı1K6\ &\’] \\Aú˜\Á#û§§J'),(2,_binary 'd\·ô˙3¨!Ø\œ#ﬂû+∏b\Ã%{$\›mº\√˜Z\‰\Èº=\⁄',_binary '\‰\—\Ì@¸\Ï*0aw\Í\⁄Tô\"\÷eLb4åî≠\„’ïoë˜$'),(3,_binary 'J\„C˘Ö3\œREé\È£\Ë∏\‚˘Íôª~sº(m`ì',_binary '@1±`\”/\'xbáwmqï`Sœ™0πµãÛk\"8'),(4,_binary '0Ñ\€\·å\Ïágó} áA≥\\}´î¢^â&∑&\"g+6\ÃgÅ',_binary '2\Î2;Y$ˇ∞ª\œDx¨ÚM[H˛TaËä™ñV%\ '),(8,_binary '\ﬂ\œtZºÇRˇ?t|;’©ÅK™5¢\ƒOKˆ\"§l:ïSga',_binary 'C27Û\‚7¸59\Ê\Ó\ÊΩ\“$©Éı¢F\r¯bm∞\»'),(9,_binary '˙}ü°\Ì#X∑\’$\Â\Ík+Lm\ƒ\r\ÂbKı\¬\Ï[s\r≤',_binary '\Ïo@tF∑$\ÓRFI¿˚\“ˇ¯ó<òƒünv\ '),(10,_binary '˚\Êëb!y$≤\‰˙bIê$$xª\’¯û©⁄®\Â¢>h1',_binary 'ò\ƒ{q¡óc®ﬁÜD:-£Q¥\◊˛\Ëv,≤\ÿ»•*ù\≈\Õ*'),(11,_binary '@mäú°_Û‘õAq´Hç\È≤À©+¨Æê\Â-ÈûÄ%\Ì\Ë',_binary '\Ì3éq©m8\Õ¿\Œ\Ê\\éú]‹™Íº™î.˚]J\œYX\«ra');
/*!40000 ALTER TABLE `userpassword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Children` varchar(255) DEFAULT NULL,
  `Position` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Rodrigo',NULL,'MasterStudent'),(2,'Ketil','1,3,4','Manager'),(3,'Sandra',NULL,'RiskOwner'),(4,'Niels',NULL,'RiskOwner'),(8,'BigBoss','2,9','Owner'),(9,'Diana','10,11','Manager'),(10,'Richard',NULL,'RiskOwner'),(11,'Tian',NULL,'RiskOwner');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-27 10:44:42
