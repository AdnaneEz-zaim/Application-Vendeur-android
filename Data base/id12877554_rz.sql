-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 30, 2020 at 05:50 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id12877554_rz`
--

-- --------------------------------------------------------

--
-- Table structure for table `agentdepot`
--

CREATE TABLE `agentdepot` (
  `id_AgentDepot` int(11) NOT NULL,
  `mot_de_pass` varchar(30) DEFAULT NULL,
  `login_agent` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `agentdepot`
--

INSERT INTO `agentdepot` (`id_AgentDepot`, `mot_de_pass`, `login_agent`) VALUES
(1, '123', 'agent_1'),
(2, '124', 'agent_2'),
(3, '555', 'agent_3'),
(4, '999', 'agent_4'),
(5, 'agent_5', '111'),
(6, 'agent_6', '000'),
(7, 'agent_7', '222'),
(8, 'agent_8', '369');

-- --------------------------------------------------------

--
-- Table structure for table `avoir`
--

CREATE TABLE `avoir` (
  `id_Avoir` int(11) NOT NULL,
  `quantite_produit` int(11) NOT NULL,
  `id_Produit` int(11) NOT NULL,
  `id_Vendeur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `avoir`
--

INSERT INTO `avoir` (`id_Avoir`, `quantite_produit`, `id_Produit`, `id_Vendeur`) VALUES
(1, 73, 1, 4),
(3, 72, 4, 4),
(4, 34, 5, 4),
(5, 51, 2, 3),
(6, 18, 3, 4),
(7, 72, 4, 4),
(9, 65, 13, 4),
(10, 75, 23, 4),
(11, 57, 15, 4),
(12, 100, 25, 4),
(13, 105, 13, 4),
(14, 67, 6, 4),
(15, 45, 7, 4),
(16, 75, 8, 4),
(17, 76, 9, 4),
(18, 30, 10, 4),
(19, 32, 1, 3),
(20, 22, 4, 3),
(21, 34, 5, 3),
(22, 44, 22, 3),
(23, 8, 3, 3),
(24, 60, 12, 3),
(25, 65, 13, 3),
(26, 75, 23, 3),
(27, 7, 15, 3),
(28, 100, 25, 3),
(29, 86, 11, 3),
(30, 57, 6, 3),
(31, 45, 20, 3),
(32, 75, 8, 3),
(33, 76, 9, 3),
(34, 30, 10, 3),
(35, 37, 24, 3);

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id_Client` int(11) NOT NULL,
  `adresse_c` text DEFAULT NULL,
  `id_Utilisateur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id_Client`, `adresse_c`, `id_Utilisateur`) VALUES
(3, 'test', 3),
(5, 'yyyt', 6),
(6, 'xxxx', 7),
(7, 'beni ouanjel', 15),
(9, 'aaaa', 20),
(10, 'aaaa', 21),
(11, 'jjhgggc', 22),
(12, 'ouad fes, fes', 23),
(13, 'ouad fes, fes', 24);

-- --------------------------------------------------------

--
-- Table structure for table `demande`
--

CREATE TABLE `demande` (
  `id_Demande` int(11) NOT NULL,
  `quantite_res` int(11) DEFAULT NULL,
  `id_Client` int(11) NOT NULL,
  `id_Vendeur` int(11) NOT NULL,
  `id_Produit` int(11) NOT NULL,
  `id_trajet` int(4) NOT NULL,
  `id_position` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `demande`
--

INSERT INTO `demande` (`id_Demande`, `quantite_res`, `id_Client`, `id_Vendeur`, `id_Produit`, `id_trajet`, `id_position`) VALUES
(70, 50, 6, 2, 10, 1, 1011),
(71, 5, 6, 2, 6, 1, 1012),
(72, 50, 6, 2, 14, 1, 1013),
(77, 10, 6, 2, 13, 1, 1018),
(78, 20, 6, 2, 3, 1, 1019),
(79, 20, 6, 2, 2, 1, 1020),
(81, 50, 6, 2, 12, 1, 1022),
(84, 50, 6, 2, 13, 1, 1025),
(87, 4, 6, 2, 10, 1, 1028),
(88, 50, 6, 2, 13, 1, 1029),
(101, 10, 6, 4, 1, 1, 1055),
(104, 20, 6, 3, 3, 1, 1077),
(107, 50, 6, 4, 1, 2, 1080),
(108, 1, 6, 4, 3, 1, 1083),
(109, 1, 6, 4, 1, 2, 1092),
(110, 10, 6, 4, 12, 2, 1093),
(111, 4, 6, 2, 10, 1, 1121),
(112, 4, 6, 2, 10, 1, 1122),
(114, 100, 6, 2, 3, 1, 1125),
(115, 20, 6, 4, 1, 2, 1126),
(116, 20, 6, 2, 11, 1, 1127),
(117, 10, 7, 2, 1, 1, 1128),
(118, 20, 7, 2, 3, 3, 1135),
(119, 5, 7, 4, 4, 2, 1137),
(121, 20, 7, 4, 4, 2, 1139),
(122, 4, 6, 3, 10, 1, 1140),
(124, 10, 7, 4, 1, 2, 1142),
(125, 20, 7, 3, 7, 1, 1143),
(128, 20, 7, 4, 14, 2, 1146),
(129, 5, 12, 4, 1, 2, 1147),
(131, 20, 13, 3, 13, 1, 1150),
(133, 20, 13, 3, 20, 1, 1152),
(135, 5, 13, 4, 13, 3, 1158),
(136, 50, 13, 4, 18, 3, 1159),
(138, 10, 13, 4, 1, 3, 1164),
(139, 100, 13, 4, 26, 3, 1165),
(140, 20, 13, 3, 3, 1, 1171),
(141, 10, 13, 3, 3, 2, 1182),
(142, 10, 13, 3, 9, 3, 1186),
(143, 10, 13, 2, 18, 1, 1187),
(144, 20, 13, 2, 18, 1, 1188),
(145, 5, 13, 2, 4, 1, 1189),
(146, 10, 13, 2, 12, 14, 1208),
(147, 5, 13, 2, 1, 14, 1209),
(148, 20, 13, 3, 1, 3, 1216),
(149, 10, 13, 3, 12, 3, 1217);

-- --------------------------------------------------------

--
-- Table structure for table `depot`
--

CREATE TABLE `depot` (
  `id_Depot` int(11) NOT NULL,
  `nom_depot` varchar(20) DEFAULT NULL,
  `id_AgentDepot` int(11) NOT NULL,
  `id_position` int(11) NOT NULL,
  `tele_depot` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `depot`
--

INSERT INTO `depot` (`id_Depot`, `nom_depot`, `id_AgentDepot`, `id_position`, `tele_depot`) VALUES
(1, 'dépot_1', 3, 1084, '0666000000'),
(2, 'dépot_2', 5, 1085, '0666000000'),
(3, 'dépot_3', 4, 1086, '0666000000'),
(4, 'dépot_4', 2, 1088, '0666000000'),
(5, 'dépot_5', 1, 1087, '0666000000'),
(6, 'dépot_8', 8, 1089, '0666000000'),
(7, 'dépot_6', 6, 1090, '0666000000'),
(8, 'dépot_7', 7, 1091, '0666000000');

-- --------------------------------------------------------

--
-- Table structure for table `gestionnaire`
--

CREATE TABLE `gestionnaire` (
  `id_ges` int(4) NOT NULL,
  `login_ges` varchar(10) NOT NULL,
  `pass_ges` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `gestionnaire`
--

INSERT INTO `gestionnaire` (`id_ges`, `login_ges`, `pass_ges`) VALUES
(1, 'user1', '0123'),
(2, 'user2', '3210');

-- --------------------------------------------------------

--
-- Table structure for table `logDemande`
--

CREATE TABLE `logDemande` (
  `id_logD` int(4) NOT NULL,
  `id_Demande` int(4) NOT NULL,
  `id_Client` int(11) NOT NULL,
  `id_Vendeur` int(11) NOT NULL,
  `ClientClick` tinyint(1) NOT NULL DEFAULT 0,
  `VendeurConfirmer` tinyint(1) NOT NULL DEFAULT 0,
  `VendeurDecline` tinyint(1) NOT NULL DEFAULT 0,
  `commentD` text NOT NULL DEFAULT '',
  `avisD` varchar(10) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `logDemande`
--

INSERT INTO `logDemande` (`id_logD`, `id_Demande`, `id_Client`, `id_Vendeur`, `ClientClick`, `VendeurConfirmer`, `VendeurDecline`, `commentD`, `avisD`) VALUES
(17, 68, 6, 4, 1, 1, 0, 'no comment', 'no avis'),
(18, 69, 6, 4, 1, 1, 0, 'no comment', 'good'),
(19, 70, 6, 2, 1, 0, 1, 'no comment', 'normal'),
(20, 71, 6, 2, 1, 0, 0, 'no comment', 'very good'),
(21, 72, 6, 2, 1, 0, 0, 'no comment', 'good'),
(22, 73, 6, 2, 1, 1, 0, 'no comment', 'good'),
(26, 77, 6, 2, 1, 0, 0, 'no comment', 'good'),
(27, 78, 6, 2, 1, 0, 0, 'tfooo', 'no avis'),
(28, 79, 6, 2, 1, 0, 0, 'uuuu', 'very good'),
(29, 80, 6, 4, 1, 1, 0, 'no comment', 'no avis'),
(30, 81, 6, 2, 1, 0, 0, 'no comment', 'no avis'),
(31, 82, 6, 3, 1, 0, 0, 'no comment', 'no avis'),
(33, 84, 6, 2, 1, 0, 0, 'no comment', 'bad'),
(34, 85, 6, 3, 1, 0, 0, 'no comment', 'bad'),
(36, 87, 6, 2, 1, 0, 0, 'no comment', 'no avis'),
(37, 88, 6, 2, 1, 0, 0, 'no comment', 'no avis'),
(38, 89, 6, 4, 1, 1, 0, 'mal', 'bad'),
(39, 90, 6, 4, 1, 1, 0, 'yyyy', 'good'),
(40, 91, 6, 4, 1, 1, 0, 'no comment', 'no avis'),
(41, 92, 6, 4, 1, 1, 0, 'walooooo', 'normal'),
(42, 93, 6, 3, 1, 0, 0, 'no comment', 'no avis'),
(43, 94, 6, 2, 1, 0, 0, 'no comment', 'no avis'),
(44, 95, 6, 2, 1, 0, 0, 'no comment', 'no avis'),
(46, 97, 6, 4, 1, 1, 0, 'no comment', 'very bad'),
(48, 99, 6, 4, 1, 1, 0, 'no comment', 'very good'),
(49, 100, 6, 4, 1, 1, 0, 'no comment', 'no avis'),
(50, 101, 6, 4, 1, 0, 0, 'cv', 'very good'),
(51, 102, 6, 3, 1, 0, 0, 'no comment', 'no avis'),
(53, 104, 6, 3, 1, 0, 0, 'merci', 'very good'),
(56, 107, 6, 4, 0, 1, 0, '', ''),
(57, 108, 6, 4, 0, 0, 0, '', ''),
(58, 109, 6, 4, 0, 1, 0, '', ''),
(59, 110, 6, 4, 0, 0, 0, '', ''),
(60, 111, 6, 2, 1, 0, 0, 'no comment', 'no avis'),
(61, 112, 6, 2, 1, 0, 0, 'no comment', 'no avis'),
(63, 114, 6, 2, 0, 0, 0, '', ''),
(64, 115, 6, 4, 0, 1, 0, '', ''),
(65, 116, 6, 2, 0, 0, 0, '', ''),
(66, 117, 7, 2, 0, 0, 0, '', ''),
(67, 118, 7, 2, 0, 0, 0, '', ''),
(68, 119, 7, 4, 0, 0, 0, '', ''),
(70, 121, 7, 4, 0, 1, 0, '', ''),
(71, 122, 6, 3, 0, 0, 0, '', ''),
(73, 124, 7, 4, 0, 1, 0, '', ''),
(74, 125, 7, 3, 0, 0, 0, '', ''),
(77, 128, 7, 4, 0, 0, 0, '', ''),
(78, 129, 12, 4, 1, 0, 0, 'bonne service', 'good'),
(79, 130, 13, 4, 1, 1, 0, 'no comment', 'very good'),
(80, 131, 13, 3, 1, 0, 0, 'no comment', 'no avis'),
(82, 133, 13, 3, 1, 0, 0, 'no comment', 'no avis'),
(83, 134, 13, 4, 1, 1, 1, 'no comment', 'no avis'),
(84, 135, 13, 4, 1, 0, 1, 'no comment', 'no avis'),
(85, 136, 13, 4, 1, 0, 1, 'no comment', 'no avis'),
(87, 138, 13, 4, 1, 0, 0, 'tres boen', 'very good'),
(88, 139, 13, 4, 1, 0, 0, 'no comment', 'very good'),
(89, 140, 13, 3, 1, 0, 0, 'no comment', 'bad'),
(90, 141, 13, 3, 1, 0, 0, 'no comment', 'good'),
(91, 142, 13, 3, 1, 0, 0, 'no comment', 'no avis'),
(92, 143, 13, 2, 1, 0, 0, 'no comment', 'very good'),
(93, 144, 13, 2, 1, 0, 0, 'c bon', 'good'),
(94, 145, 13, 2, 1, 0, 0, 'bien', 'very good'),
(95, 146, 13, 2, 0, 0, 0, '', ''),
(96, 147, 13, 2, 0, 0, 0, '', ''),
(97, 148, 13, 3, 0, 0, 0, '', ''),
(98, 149, 13, 3, 0, 0, 0, '', '');

--
-- Triggers `logDemande`
--
DELIMITER $$
CREATE TRIGGER `endDemande` AFTER UPDATE ON `logDemande` FOR EACH ROW BEGIN
DECLARE finished INTEGER DEFAULT 0;
DECLARE idDemande varchar(4) DEFAULT "";
DECLARE	line CURSOR
    	FOR SELECT id_Demande FROM logDemande WHERE ClientClick=1 and VendeurConfirmer = 1;
DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;
OPEN line;
	getIdDemande:LOOP
    	FETCH line into idDemande;
        IF finished = 1 THEN 
            LEAVE getIdDemande;
        END IF;
        DELETE FROM demande WHERE id_Demande = idDemande;
	END LOOP getIdDemande;
CLOSE line;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `logReserve`
--

CREATE TABLE `logReserve` (
  `id_logR` int(4) NOT NULL,
  `id_Reserve` int(4) NOT NULL,
  `id_Client` int(4) NOT NULL,
  `id_Vendeur` int(4) NOT NULL,
  `ClientConfirmer` tinyint(1) NOT NULL DEFAULT 0,
  `ClientDecline` tinyint(1) NOT NULL DEFAULT 0,
  `VendeurConfirmer` tinyint(1) NOT NULL DEFAULT 0,
  `VendeurDecline` tinyint(1) NOT NULL DEFAULT 0,
  `commentR` text NOT NULL DEFAULT '',
  `avisR` text NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `logReserve`
--

INSERT INTO `logReserve` (`id_logR`, `id_Reserve`, `id_Client`, `id_Vendeur`, `ClientConfirmer`, `ClientDecline`, `VendeurConfirmer`, `VendeurDecline`, `commentR`, `avisR`) VALUES
(7, 44, 6, 2, 0, 0, 0, 0, '', ''),
(8, 45, 6, 2, 0, 0, 0, 0, '', ''),
(9, 46, 6, 3, 0, 0, 0, 0, '', ''),
(10, 47, 6, 3, 0, 0, 0, 0, '', ''),
(11, 48, 6, 4, 0, 0, 0, 0, '', ''),
(12, 49, 6, 4, 0, 0, 0, 0, '', ''),
(13, 50, 6, 3, 0, 0, 0, 0, '', ''),
(14, 51, 6, 4, 0, 0, 0, 0, '', ''),
(15, 52, 6, 2, 0, 0, 0, 0, '', ''),
(16, 53, 6, 2, 0, 0, 0, 0, '', ''),
(17, 54, 6, 4, 0, 0, 0, 0, '', ''),
(18, 55, 7, 4, 0, 0, 0, 0, '', ''),
(19, 56, 7, 4, 0, 0, 0, 0, '', ''),
(20, 57, 7, 4, 0, 0, 0, 0, '', ''),
(21, 58, 7, 4, 0, 0, 0, 0, '', ''),
(22, 59, 7, 3, 0, 0, 0, 0, '', ''),
(23, 60, 7, 4, 0, 0, 0, 0, '', ''),
(24, 61, 12, 2, 0, 1, 0, 0, 'mauvaise service', 'bad'),
(25, 62, 13, 3, 1, 0, 0, 0, 'très bien', 'good'),
(26, 63, 13, 3, 0, 1, 0, 0, 'pas mal', 'normal'),
(28, 65, 13, 4, 0, 1, 0, 0, '', ''),
(29, 66, 13, 4, 1, 0, 0, 0, '', ''),
(32, 69, 13, 4, 1, 0, 0, 0, '', 'bad'),
(34, 71, 13, 4, 1, 0, 0, 0, '', ''),
(35, 72, 13, 4, 0, 1, 0, 0, '', ''),
(36, 73, 13, 4, 0, 1, 0, 0, '', ''),
(37, 74, 13, 3, 1, 0, 0, 0, '', ''),
(38, 75, 13, 3, 0, 1, 0, 0, '', ''),
(39, 76, 13, 3, 1, 0, 0, 0, '', ''),
(40, 77, 13, 3, 1, 0, 0, 0, '', ''),
(42, 79, 13, 3, 0, 1, 0, 0, '', ''),
(43, 80, 13, 3, 1, 0, 0, 0, '', 'good'),
(44, 81, 13, 2, 0, 1, 0, 0, '', ''),
(45, 82, 13, 3, 1, 0, 0, 0, '', 'very good'),
(46, 83, 13, 3, 1, 0, 0, 0, '', ''),
(47, 84, 13, 2, 1, 0, 0, 0, '', 'good'),
(48, 85, 13, 8, 1, 0, 0, 0, 'bien', 'very good'),
(50, 87, 13, 3, 1, 0, 0, 0, '...', 'good'),
(51, 88, 13, 3, 1, 0, 0, 0, '', 'bad'),
(53, 90, 13, 3, 1, 1, 0, 0, 'bon', 'good'),
(54, 91, 13, 3, 0, 0, 0, 0, '', ''),
(55, 92, 13, 3, 0, 0, 0, 0, '', ''),
(56, 93, 13, 3, 0, 0, 0, 0, '', ''),
(57, 94, 13, 3, 0, 0, 0, 0, '', ''),
(58, 95, 13, 4, 0, 0, 0, 0, '', ''),
(62, 99, 13, 3, 0, 0, 0, 0, '', ''),
(63, 100, 13, 8, 0, 0, 0, 0, '', ''),
(64, 101, 13, 8, 0, 0, 0, 0, '', ''),
(65, 102, 13, 8, 0, 0, 0, 0, '', ''),
(66, 103, 13, 3, 0, 0, 0, 0, '', ''),
(67, 104, 13, 3, 0, 0, 0, 0, '', ''),
(68, 105, 13, 3, 0, 0, 0, 0, '', ''),
(69, 106, 13, 8, 0, 0, 0, 0, '', ''),
(70, 107, 13, 8, 0, 0, 0, 0, '', ''),
(71, 108, 13, 3, 0, 0, 0, 0, '', ''),
(72, 109, 13, 3, 0, 0, 0, 0, '', ''),
(73, 110, 13, 3, 0, 0, 0, 0, '', '');

--
-- Triggers `logReserve`
--
DELIMITER $$
CREATE TRIGGER `endReserve` AFTER UPDATE ON `logReserve` FOR EACH ROW BEGIN
DECLARE finished INTEGER DEFAULT 0;
DECLARE idReserve varchar(4) DEFAULT "";
DECLARE	line CURSOR
    	FOR SELECT id_Reserve FROM logReserve WHERE ClientConfirmer=1 and VendeurConfirmer = 1;
DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;
OPEN line;
	getIdReserve:LOOP
    	FETCH line into idReserve;
        IF finished = 1 THEN 
            LEAVE getIdReserve;
        END IF;
        DELETE FROM reservation WHERE id_Reserve = idReserve;
	END LOOP getIdReserve;
CLOSE line;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `position`
--

CREATE TABLE `position` (
  `id_pos` int(11) NOT NULL,
  `x` float DEFAULT NULL,
  `y` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `position`
--

INSERT INTO `position` (`id_pos`, `x`, `y`) VALUES
(1000, 34.0253, -5.00795),
(1001, 34.0175, -4.96853),
(1002, 34.0206, -5.02942),
(1004, 0.154438, -0.00387872),
(1005, 34.0336, -4.99597),
(1006, 34.0056, -5.01196),
(1007, 34.0058, -5.01437),
(1008, 34.0439, -4.99968),
(1009, 34.0128, -4.97029),
(1010, 34.013, -4.96626),
(1011, 34.0222, -4.99712),
(1012, 34.0178, -5.00976),
(1013, 34.0323, -4.99375),
(1014, 34.0372, -4.99769),
(1015, 34.0202, -5.02935),
(1016, 34.0177, -5.03465),
(1017, 34.0138, -5.03453),
(1018, 34.0272, -5.00744),
(1019, 34.0305, -5.00735),
(1020, 34.0216, -5.00311),
(1021, 34.0242, -5.02063),
(1022, 34.0144, -5.01575),
(1023, 34.0172, -4.9693),
(1024, 34.0171, -4.966),
(1025, 34.0243, -5.01046),
(1026, 2, 2),
(1027, 2, 2),
(1028, 34.0246, -5.00989),
(1029, 34.0249, -5.00213),
(1030, 34.0187, -5.03554),
(1031, 34.018, -5.03458),
(1032, 34.0157, -5.02934),
(1033, 34.0137, -5.03734),
(1034, 34.0176, -4.96112),
(1035, 34.0111, -4.95323),
(1036, 34.0272, -5.01719),
(1037, 34.0038, -5.01147),
(1038, 34.0083, -5.00773),
(1039, 34.0286, -5.05275),
(1040, 34.0305, -5.04573),
(1041, 34.0283, -5.0388),
(1042, 34.0269, -5.04387),
(1043, 34.0293, -5.04213),
(1044, 0, 0),
(1045, 34.0229, -5.04815),
(1046, 34.0867, -4.88854),
(1047, 34.0021, -4.96853),
(1048, 34.0135, -5.01686),
(1049, 34.0181, -5.02788),
(1050, 34.0182, -5.00456),
(1051, 34.0243, -5.01173),
(1052, 34.0341, -5.00609),
(1053, 34.0376, -5.00942),
(1054, 34.0597, -4.99005),
(1055, 34.008, -4.99113),
(1056, 34.0095, -5.0427),
(1057, 34.0174, -4.98708),
(1058, 34.0386, -5.00005),
(1059, 34.0343, -4.9945),
(1060, 34.0442, -5.00194),
(1061, 34.0399, -5.0032),
(1062, 34.0405, -5.00807),
(1063, 34.0378, -5.00911),
(1064, 34.0442, -5.00194),
(1065, 34.0442, -5.00194),
(1066, 34.0386, -5.00096),
(1067, 34.0246, -5.00989),
(1068, 34.0246, -5.00989),
(1069, 34.0246, -5.00989),
(1070, 33.9896, -5.0264),
(1071, 34.0395, -5.00962),
(1072, 5, 5),
(1073, 34.0246, -5.00989),
(1074, 34.0246, -5.00989),
(1075, 34.0246, -5.00989),
(1076, 34.0354, -5.00905),
(1077, 34.0281, -5.01161),
(1078, 34.0198, -4.96881),
(1079, 34.011, -4.96164),
(1080, 34.0122, -4.98572),
(1081, 34.0007, -4.9429),
(1082, 34.0089, -4.96327),
(1083, 34.0217, -5.01007),
(1084, 34.0364, -4.99352),
(1085, 34.0289, -4.99575),
(1086, 34.0375, -5.02012),
(1087, 34.0036, -5.01591),
(1088, 34.006, -5.05007),
(1089, 34.0263, -5.01729),
(1090, 34.0235, -4.9936),
(1091, 34.0146, -4.97833),
(1092, 34.0178, -4.96586),
(1093, 34.0173, -4.96471),
(1094, 2.4512, 3.65687),
(1095, 2.36, 2.12327),
(1096, 2.65, 1.25),
(1097, 2.15, 1),
(1098, 2.3, 1.25),
(1099, 5.26263, 5.25252),
(1100, 5.26263, 5.25252),
(1101, 3.3333, 3.333),
(1102, 20.2626, 333.222),
(1103, 34.0151, -5.02985),
(1104, 34.0048, -5.02732),
(1105, 33.9919, -4.98873),
(1106, 34.021, -5.00516),
(1110, 34.0246, -5.00989),
(1111, 34.0246, -5.00989),
(1112, 34.0246, -5.00989),
(1113, 34.0246, -5.00989),
(1114, 34.0246, -5.00989),
(1115, 34.0246, -5.00989),
(1116, 34.0246, -5.00989),
(1117, 34.0246, -5.00989),
(1118, 34.0246, -5.00989),
(1119, 34.0246, -5.00989),
(1120, 34.0246, -5.00989),
(1121, 34.0246, -5.00989),
(1122, 34.0246, -5.00989),
(1123, 1222, 1222),
(1124, 34.0133, -4.99993),
(1125, 34.016, -5.00454),
(1126, 34.0224, -4.9678),
(1127, 34.009, -5.01039),
(1128, 34.043, -5.00005),
(1129, 34.659, -4.29735),
(1130, 34.0179, -4.96846),
(1131, 34.0166, -4.9645),
(1132, 34.0249, -4.97319),
(1133, 1.222, 3.2222),
(1134, 34.0182, -5.00048),
(1135, 34.0072, -5.02095),
(1136, 34.0186, -4.96774),
(1137, 34.0158, -4.97405),
(1138, 34.0152, -4.969),
(1139, 34.6354, -4.38912),
(1140, 34.0246, -5.00989),
(1141, 34.6354, -4.38912),
(1142, 34.5497, -4.363),
(1143, 34.0428, -5.00587),
(1144, 34.6786, -4.59764),
(1145, 34.5354, -4.32754),
(1146, 34.0145, -4.97553),
(1147, 34.0108, -4.98918),
(1148, 34.0209, -5.02007),
(1149, 34.0137, -4.97396),
(1150, 34.0408, -5.0092),
(1151, 34.0091, -5.02697),
(1152, 34.0324, -4.99335),
(1153, 34.0132, -4.99523),
(1154, 34.0051, -5.01386),
(1155, 34.0111, -4.97791),
(1156, 34.0146, -4.96956),
(1157, 34.0241, -5.03287),
(1158, 34.0242, -5.03975),
(1159, 34.023, -5.03353),
(1160, 34.0279, -5.02527),
(1161, 34.0187, -5.03212),
(1162, 34.0195, -5.03848),
(1163, 34.0199, -5.03165),
(1164, 34.0255, -5.02018),
(1165, 34.0283, -5.02323),
(1166, 34.0156, -5.03014),
(1167, 34.0202, -5.02742),
(1168, 34.0259, -5.05103),
(1169, 34.0298, -5.02298),
(1170, 34.0232, -5.02299),
(1171, 34.0149, -4.99284),
(1172, 34.0265, -5.00698),
(1173, 34.0279, -5.0097),
(1174, 34.0283, -5.0116),
(1175, 33.9485, -5.03148),
(1176, 34.0249, -5.02868),
(1177, 34.0249, -5.02868),
(1178, 34.0177, -4.96944),
(1179, 34.02, -4.96883),
(1180, 34.0156, -4.96795),
(1181, 34.0143, -4.96995),
(1182, 34.0128, -4.96695),
(1183, 34.0351, -5.00538),
(1184, 34.0174, -5.03577),
(1185, 34.0223, -5.03021),
(1186, 34.0192, -5.02766),
(1187, 34.0359, -5.00805),
(1188, 34.0373, -5.00493),
(1189, 34.0194, -5.00677),
(1190, 34.0268, -5.01187),
(1191, 34.0064, -4.98278),
(1192, 34.02, -4.96643),
(1193, 34.0052, -4.95405),
(1194, 34.0059, -4.958),
(1195, 34.0156, -4.97106),
(1196, 34.0029, -4.88949),
(1197, 34.0093, -4.96302),
(1198, 34.0161, -4.96106),
(1199, 34.0162, -4.95692),
(1200, 34.014, -4.97212),
(1201, 34.0179, -4.96834),
(1202, 34.0109, -4.96249),
(1203, 34.0195, -4.96658),
(1204, 34.0148, -4.96684),
(1205, 34.4582, -4.5282),
(1206, 34.0449, -5.00418),
(1207, 34.046, -4.99796),
(1208, 34.0427, -4.99776),
(1209, 34.0364, -5.00554),
(1210, 34.0212, -5.02605),
(1211, 34.0227, -5.02366),
(1212, 34.0232, -5.028),
(1213, 34.0192, -5.03398),
(1214, 34.0199, -5.0317),
(1215, 34.0213, -5.03474),
(1216, 34.0167, -5.03519),
(1217, 34.0165, -5.02833),
(1218, 34.0182, -5.02393),
(1219, 34.0251, -5.02812),
(1220, 34.023, -5.02355),
(1221, 34.0228, -5.02932),
(1222, 34.0184, -5.027);

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

CREATE TABLE `produit` (
  `id_Produit` int(11) NOT NULL,
  `nom_produit` varchar(20) DEFAULT NULL,
  `categorie` varchar(20) DEFAULT NULL,
  `prix` float DEFAULT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `produit`
--

INSERT INTO `produit` (`id_Produit`, `nom_produit`, `categorie`, `prix`, `image`) VALUES
(1, 'tomate', 'kg', 4, 'https://rzbusinessma.000webhostapp.com/categs/kg/tomato.png'),
(2, 'limon', 'kg', 3, 'https://rzbusinessma.000webhostapp.com/categs/kg/lemon.png'),
(3, 'pomme de tère', 'kg', 3, 'https://rzbusinessma.000webhostapp.com/categs/kg/potato.png'),
(4, 'carrot', 'kg', 2.5, 'https://rzbusinessma.000webhostapp.com/categs/kg/carrot.png'),
(5, 'cerise', 'kg', 8, 'https://rzbusinessma.000webhostapp.com/categs/kg/cherry.png'),
(6, 'poivre', 'kg', 6, 'https://rzbusinessma.000webhostapp.com/categs/kg/chilli.png'),
(7, 'blé', 'kg', 7, 'https://rzbusinessma.000webhostapp.com/categs/kg/corn.png'),
(8, 'aubergine', 'kg', 5.5, 'https://rzbusinessma.000webhostapp.com/categs/kg/eggplant.png'),
(9, 'les raisins', 'kg', 10, 'https://rzbusinessma.000webhostapp.com/categs/kg/grapes.png'),
(10, 'oignon', 'kg', 9.5, 'https://rzbusinessma.000webhostapp.com/categs/kg/onion.png'),
(11, 'boisson', 'l', 7.5, 'https://rzbusinessma.000webhostapp.com/categs/l/coke.png'),
(12, 'jus', 'l', 15, 'https://rzbusinessma.000webhostapp.com/categs/l/juice.png'),
(13, 'lait', 'l', 6.5, 'https://rzbusinessma.000webhostapp.com/categs/l/milk.png'),
(14, 'œil', 'l', 11, 'https://rzbusinessma.000webhostapp.com/categs/l/oil.png'),
(15, 'eau', 'l', 6, 'https://rzbusinessma.000webhostapp.com/categs/l/water.png'),
(17, 'brosse', 'unité', 6, 'https://rzbusinessma.000webhostapp.com/categs/unite/brush.png'),
(18, 'papier toilette', 'unité', 10, 'https://rzbusinessma.000webhostapp.com/categs/unite/clean.png'),
(19, 'fer à repasser', 'unité', 35, 'https://rzbusinessma.000webhostapp.com/categs/unite/clothes.png'),
(20, 'tasse', 'unité', 2, 'https://rzbusinessma.000webhostapp.com/categs/unite/cup.png'),
(21, 'plat', 'unité', 3, 'https://rzbusinessma.000webhostapp.com/categs/unite/dish.png'),
(22, 'fourchette', 'unité', 1, 'https://rzbusinessma.000webhostapp.com/categs/unite/fork.png'),
(23, 'gant', 'unité', 5, 'https://rzbusinessma.000webhostapp.com/categs/unite/ligue.png'),
(24, 'mouchoir', 'unité', 15, 'https://rzbusinessma.000webhostapp.com/categs/unite/napkin.png'),
(25, 'savon', 'unité', 1, 'https://rzbusinessma.000webhostapp.com/categs/unite/soap.png'),
(26, 'cuillère', 'unité', 1, 'https://rzbusinessma.000webhostapp.com/categs/unite/spoon.png'),
(34, 'produit', 'l', 3, 'https://rzbusinessma.000webhostapp.com/categs/l/mil1k.png'),
(35, 'produit2', 'unite', 12, 'https://rzbusinessma.000webhostapp.com/categs/unite/-البصل-في-المنام.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `promo`
--

CREATE TABLE `promo` (
  `id_promo` int(4) NOT NULL,
  `id_produit` int(4) NOT NULL,
  `percent` int(11) NOT NULL,
  `img_promo` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `promo`
--

INSERT INTO `promo` (`id_promo`, `id_produit`, `percent`, `img_promo`) VALUES
(1, 1, 30, 'https://rzbusinessma.000webhostapp.com/Promo/tomato30.png'),
(2, 3, 40, 'https://rzbusinessma.000webhostapp.com/Promo/potatoe30.png'),
(3, 12, 50, 'https://rzbusinessma.000webhostapp.com/Promo/juice50.png'),
(4, 18, 50, 'https://rzbusinessma.000webhostapp.com/Promo/papier50.png'),
(13, 10, 40, 'https://rzbusinessma.000webhostapp.com/Promo/onion40.png');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `id_Reservation` int(11) NOT NULL,
  `quantite_dem` int(11) DEFAULT NULL,
  `id_Client` int(11) NOT NULL,
  `id_Produit` int(11) NOT NULL,
  `id_Vendeur` int(11) NOT NULL,
  `id_trajet` int(4) NOT NULL,
  `id_position` int(4) NOT NULL,
  `date_reserve` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`id_Reservation`, `quantite_dem`, `id_Client`, `id_Produit`, `id_Vendeur`, `id_trajet`, `id_position`, `date_reserve`) VALUES
(44, 20, 6, 6, 2, 2, 1034, '2020-08-21'),
(45, 20, 6, 11, 2, 2, 1035, '2021-04-21'),
(46, 20, 6, 2, 3, 1, 1036, '2020-04-21'),
(47, 50, 6, 17, 3, 1, 1037, '2020-04-19'),
(48, 1, 6, 4, 4, 1, 1053, '2020-04-22'),
(49, 10, 6, 11, 4, 1, 1062, '2020-04-23'),
(50, 20, 6, 13, 3, 3, 1070, '2020-02-21'),
(51, 10, 6, 17, 4, 1, 1071, '2020-08-23'),
(52, 50, 6, 15, 2, 2, 1081, '2020-04-28'),
(53, 10, 6, 13, 2, 2, 1082, '2020-04-28'),
(54, 10, 6, 11, 4, 2, 1105, '2020-05-04'),
(55, 20, 7, 3, 4, 2, 1129, '2020-06-06'),
(56, 20, 7, 14, 4, 2, 1130, '2020-05-06'),
(57, 2, 7, 19, 4, 2, 1131, '2020-05-06'),
(58, 10, 7, 13, 4, 2, 1132, '2020-05-06'),
(59, 20, 7, 14, 3, 1, 1134, '2020-05-09'),
(60, 1, 7, 9, 4, 2, 1136, '2020-05-09'),
(62, 1, 13, 19, 3, 1, 1153, '2020-06-18'),
(66, 10, 13, 17, 4, 3, 1161, '2020-05-11'),
(71, 20, 13, 14, 4, 3, 1168, '2020-05-11'),
(73, 5, 13, 1, 4, 3, 1170, '2020-05-11'),
(74, 10, 13, 1, 3, 1, 1172, '2020-05-11'),
(76, 20, 13, 22, 3, 1, 1174, '2020-05-11'),
(77, 5, 13, 15, 3, 2, 1178, '2020-05-12'),
(80, 10, 13, 13, 3, 2, 1181, '2020-05-12'),
(82, 10, 13, 13, 3, 3, 1184, '2020-05-13'),
(83, 5, 13, 9, 3, 3, 1185, '2020-05-13'),
(84, 5, 13, 24, 2, 1, 1190, '2020-06-09'),
(85, 5, 13, 24, 8, 2, 1191, '2020-05-16'),
(87, 50, 13, 6, 3, 2, 1193, '2020-05-14'),
(88, 5, 13, 15, 3, 2, 1194, '2020-05-14'),
(91, 20, 13, 4, 3, 2, 1197, '2020-05-14'),
(92, 5, 13, 25, 3, 2, 1198, '2020-05-14'),
(93, 5, 13, 6, 3, 2, 1199, '2020-05-14'),
(94, 2, 13, 11, 3, 2, 1200, '2020-05-14'),
(95, 10, 13, 3, 4, 2, 1201, '2020-05-15'),
(99, 5, 13, 13, 3, 2, 1205, '2020-05-15'),
(100, 10, 13, 2, 8, 13, 1210, '2020-05-27'),
(101, 5, 13, 8, 8, 13, 1211, '2020-05-27'),
(102, 10, 13, 14, 8, 13, 1212, '2020-05-27'),
(103, 20, 13, 15, 3, 3, 1213, '2020-05-27'),
(104, 20, 13, 18, 3, 3, 1214, '2020-05-27'),
(105, 2, 13, 19, 3, 3, 1215, '2020-05-27'),
(106, 10, 13, 4, 8, 13, 1218, '2020-05-27'),
(107, 10, 13, 3, 8, 13, 1219, '2020-05-27'),
(108, 10, 13, 6, 3, 3, 1220, '2020-05-27'),
(109, 20, 13, 9, 3, 3, 1221, '2020-05-27'),
(110, 10, 13, 14, 3, 3, 1222, '2020-05-27');

-- --------------------------------------------------------

--
-- Table structure for table `stocker`
--

CREATE TABLE `stocker` (
  `id_Stocker` int(11) NOT NULL,
  `quantite_stocke` int(11) DEFAULT NULL,
  `id_Depot` int(11) NOT NULL,
  `id_Produit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `trajet`
--

CREATE TABLE `trajet` (
  `id_Trajet` int(11) NOT NULL,
  `pos_debut` int(11) NOT NULL,
  `pos_fin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trajet`
--

INSERT INTO `trajet` (`id_Trajet`, `pos_debut`, `pos_fin`) VALUES
(1, 1000, 1000),
(2, 1001, 1000),
(3, 1002, 1000),
(13, 1175, 1000),
(14, 1206, 1000);

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_Utilisateur` int(11) NOT NULL,
  `email_u` varchar(30) DEFAULT NULL,
  `nom_u` varchar(20) DEFAULT NULL,
  `prenom_u` varchar(20) DEFAULT NULL,
  `passwd_ul` varchar(100) DEFAULT NULL,
  `tele_u` varchar(13) DEFAULT NULL,
  `id_pos` int(11) NOT NULL,
  `profileImg` text NOT NULL DEFAULT 'https://rzbusinessma.000webhostapp.com/profileImgs/Vendeurs/default.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`id_Utilisateur`, `email_u`, `nom_u`, `prenom_u`, `passwd_ul`, `tele_u`, `id_pos`, `profileImg`) VALUES
(2, 'test@gmail.com', 'test', 'test', '$2y$10$ANc.oAQ0aR490tRrc6u67uK7IpBihsK2GAvCA.2/ocS7VY/IP2deS', 'test', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/default.png'),
(3, 'tesot@gmail.com', 'cli', 'client', '$2y$10$YPQHa7fjLg2wT2JMe8QLNO8Dr0hx2fRiDDi2ESQVgzl2iN9cr4mC2', 'testo', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/default.png'),
(6, 'ggh@g.com', 'tyy', 'yyy', '$2y$10$YUJu8ZLq08EboV7aJRdWquYLfgjxmp1geEJbvTI.qcF55KgovA6Zu', '9999', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/default.png'),
(7, 'x@g.com', 'xxxx', 'xxx', '$2y$10$05X6FeplUVTMng3BRdeyiOMV5SBvD/TPhuskp0FF4Tu0zIeRP2xuO', '6464', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/client7.png'),
(8, 'gmail@gmail.com', 'adnane', 'adnane', '0123456789', '0102030405', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Vendeurs/default.png'),
(9, 'vendeur@gmail.com', 'vendeur', 'vendeur', '0123456789', '060102030405', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Vendeurs/vendeur9.png'),
(10, 'adnaneezzaim@gmail.com', 'Ezzaim', 'adnane', '0123456789', '050206070809', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Vendeurs/vendeur10.png'),
(11, 'iliass@gmail.com', 'dahman', 'iliass', '$2y$10$bsjNFu1Szhjj17VhnWgcWe1BHK8gP4VD2IAbu50rJgDOACsVpcV4K', '0685868', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Vendeurs/default.png'),
(15, 'iliass.dahman@usmba.ac.ma', 'dahmaan', 'iliass', '$2y$10$S.uQGvjitwnyw4I6qjCCa.y4NfKi0mYp6pLUz86l/3uXxs8l4J9me', '06986532', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/client15.png'),
(20, 'mitija4716@inbov03.com', 'hhhh', 'jjjj', '$2y$10$DCvXhL9iALV7c/JN9LkPeekGClkq2SaTq.qxJfh45Izeip27Are.e', '66666', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/default.png'),
(21, 'senixi5579@inbov03.com', 'hhhhhh', 'jjjj', '$2y$10$fWdzUA/V76OYrX77CDTwfuWiKz4VBjhQWamwoJYLwvL00LE.QVIKO', '666663', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/default.png'),
(22, 'jetidon628@jupiterm.com', 'trrrrr', 'rrrrr', '$2y$10$Mqfo3iPr8G/MHQJvYC0TFe2OLQkRnVfImJg1Mr5cQ.BpDUNpaaHJq', '222222', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/default.png'),
(23, 'lowiv58960@beiop.com', 'ahmed', 'ahmed', '$2y$10$fIsT4j2eI8hEG4.D7.o0vu36Z1GvphGZ1aRxO8W07PlDN3kl52bHq', '0612365898', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/client23.png'),
(24, 'client.rzdelivering@gmail.com', 'sadoni', 'said', '$2y$10$B1TcEaMV.Dw4VGSgxT67HuTL8LUj2fiT/vdhogjDmYjGnS2zXysw6', '0686532458', 1000, 'https://rzbusinessma.000webhostapp.com/profileImgs/Clients/client24.png'),
(26, 'iliass1@gmail.com', 'iliass', 'iliass', '0000', '0685896523', 1000, 'http://rzbusinessma.000webhostapp.com/profileImgs/Vendeurs/default.png'),
(27, 'vendeur1@gmail.com', 'vendeur1', 'vendeur1', '0000', '0685896523', 1000, 'http://rzbusinessma.000webhostapp.com/profileImgs/Vendeurs/default.png');

-- --------------------------------------------------------

--
-- Table structure for table `vendeur`
--

CREATE TABLE `vendeur` (
  `id_Vendeur` int(11) NOT NULL,
  `solde_v` float DEFAULT NULL,
  `id_Utilisateur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `vendeur`
--

INSERT INTO `vendeur` (`id_Vendeur`, `solde_v`, `id_Utilisateur`) VALUES
(2, 2222, 2),
(3, 2000.66, 9),
(4, 1250, 10),
(8, 2000, 26),
(9, 3000, 27);

-- --------------------------------------------------------

--
-- Table structure for table `vendeurPlan`
--

CREATE TABLE `vendeurPlan` (
  `id_VendeurPlan` int(4) NOT NULL,
  `id_Vendeur` int(4) NOT NULL,
  `id_Trajet` int(4) NOT NULL,
  `day` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `vendeurPlan`
--

INSERT INTO `vendeurPlan` (`id_VendeurPlan`, `id_Vendeur`, `id_Trajet`, `day`) VALUES
(1, 2, 1, 'sunday'),
(2, 3, 2, 'sunday'),
(3, 4, 13, 'sunday'),
(4, 8, 14, 'sunday'),
(5, 9, 3, 'sunday'),
(6, 2, 13, 'monday'),
(7, 3, 1, 'monday'),
(8, 4, 2, 'monday'),
(9, 8, 14, 'monday'),
(10, 9, 3, 'monday'),
(11, 2, 14, 'tuesday'),
(12, 3, 1, 'tuesday'),
(13, 4, 3, 'tuesday'),
(14, 8, 2, 'tuesday'),
(15, 9, 13, 'tuesday'),
(16, 2, 14, 'wednesday'),
(17, 3, 3, 'wednesday'),
(18, 4, 2, 'wednesday'),
(19, 8, 13, 'wednesday'),
(20, 9, 1, 'wednesday'),
(21, 2, 2, 'thursday'),
(22, 3, 14, 'thursday'),
(23, 4, 1, 'thursday'),
(24, 8, 13, 'thursday'),
(25, 9, 3, 'thursday'),
(26, 2, 3, 'friday'),
(27, 3, 2, 'friday'),
(28, 4, 1, 'friday'),
(29, 8, 14, 'friday'),
(30, 9, 13, 'friday'),
(31, 2, 2, 'saturday'),
(32, 3, 3, 'saturday'),
(33, 4, 1, 'saturday'),
(34, 8, 13, 'saturday'),
(35, 9, 14, 'saturday');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agentdepot`
--
ALTER TABLE `agentdepot`
  ADD PRIMARY KEY (`id_AgentDepot`);

--
-- Indexes for table `avoir`
--
ALTER TABLE `avoir`
  ADD PRIMARY KEY (`id_Avoir`,`quantite_produit`),
  ADD KEY `fk_Avoir_Produit1_idx` (`id_Produit`),
  ADD KEY `fk_Avoir_Vendeur1_idx` (`id_Vendeur`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id_Client`),
  ADD KEY `fk_Client_Utilisateur1_idx` (`id_Utilisateur`);

--
-- Indexes for table `demande`
--
ALTER TABLE `demande`
  ADD PRIMARY KEY (`id_Demande`),
  ADD KEY `fk_Demande_Client1_idx` (`id_Client`),
  ADD KEY `fk_Demande_Vendeur1_idx` (`id_Vendeur`),
  ADD KEY `fk_Demande_Produit1_idx` (`id_Produit`),
  ADD KEY `id_trajet` (`id_trajet`,`id_position`),
  ADD KEY `id_position` (`id_position`);

--
-- Indexes for table `depot`
--
ALTER TABLE `depot`
  ADD PRIMARY KEY (`id_Depot`),
  ADD UNIQUE KEY `id_position` (`id_position`),
  ADD KEY `fk_Depot_AgentDepot1_idx` (`id_AgentDepot`);

--
-- Indexes for table `gestionnaire`
--
ALTER TABLE `gestionnaire`
  ADD PRIMARY KEY (`id_ges`);

--
-- Indexes for table `logDemande`
--
ALTER TABLE `logDemande`
  ADD PRIMARY KEY (`id_logD`),
  ADD UNIQUE KEY `id_Demande` (`id_Demande`);

--
-- Indexes for table `logReserve`
--
ALTER TABLE `logReserve`
  ADD PRIMARY KEY (`id_logR`),
  ADD UNIQUE KEY `Unique` (`id_Reserve`);

--
-- Indexes for table `position`
--
ALTER TABLE `position`
  ADD PRIMARY KEY (`id_pos`);

--
-- Indexes for table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_Produit`);

--
-- Indexes for table `promo`
--
ALTER TABLE `promo`
  ADD PRIMARY KEY (`id_promo`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id_Reservation`),
  ADD KEY `fk_Reservation_Client1_idx` (`id_Client`),
  ADD KEY `fk_Reservation_Produit1_idx` (`id_Produit`),
  ADD KEY `fk_Reservation_Vendeur1_idx` (`id_Vendeur`),
  ADD KEY `id_trajet` (`id_trajet`,`id_position`),
  ADD KEY `id_position` (`id_position`);

--
-- Indexes for table `stocker`
--
ALTER TABLE `stocker`
  ADD PRIMARY KEY (`id_Stocker`),
  ADD KEY `fk_Stocker_Depot1_idx` (`id_Depot`),
  ADD KEY `fk_Stocker_Produit1_idx` (`id_Produit`);

--
-- Indexes for table `trajet`
--
ALTER TABLE `trajet`
  ADD PRIMARY KEY (`id_Trajet`),
  ADD KEY `fk_Trajet_Position1_idx` (`pos_debut`),
  ADD KEY `fk_Trajet_Position2_idx` (`pos_fin`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_Utilisateur`),
  ADD KEY `fk_Utilisateur_Position1_idx` (`id_pos`);

--
-- Indexes for table `vendeur`
--
ALTER TABLE `vendeur`
  ADD PRIMARY KEY (`id_Vendeur`),
  ADD KEY `fk_Vendeur_Utilisateur_idx` (`id_Utilisateur`);

--
-- Indexes for table `vendeurPlan`
--
ALTER TABLE `vendeurPlan`
  ADD PRIMARY KEY (`id_VendeurPlan`),
  ADD KEY `indexVendeur` (`id_Vendeur`,`id_Trajet`),
  ADD KEY `id_Trajet` (`id_Trajet`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `agentdepot`
--
ALTER TABLE `agentdepot`
  MODIFY `id_AgentDepot` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `avoir`
--
ALTER TABLE `avoir`
  MODIFY `id_Avoir` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `id_Client` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `demande`
--
ALTER TABLE `demande`
  MODIFY `id_Demande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=150;

--
-- AUTO_INCREMENT for table `depot`
--
ALTER TABLE `depot`
  MODIFY `id_Depot` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `gestionnaire`
--
ALTER TABLE `gestionnaire`
  MODIFY `id_ges` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `logDemande`
--
ALTER TABLE `logDemande`
  MODIFY `id_logD` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;

--
-- AUTO_INCREMENT for table `logReserve`
--
ALTER TABLE `logReserve`
  MODIFY `id_logR` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT for table `position`
--
ALTER TABLE `position`
  MODIFY `id_pos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1223;

--
-- AUTO_INCREMENT for table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_Produit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `promo`
--
ALTER TABLE `promo`
  MODIFY `id_promo` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id_Reservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

--
-- AUTO_INCREMENT for table `stocker`
--
ALTER TABLE `stocker`
  MODIFY `id_Stocker` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `trajet`
--
ALTER TABLE `trajet`
  MODIFY `id_Trajet` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_Utilisateur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `vendeur`
--
ALTER TABLE `vendeur`
  MODIFY `id_Vendeur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `vendeurPlan`
--
ALTER TABLE `vendeurPlan`
  MODIFY `id_VendeurPlan` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `avoir`
--
ALTER TABLE `avoir`
  ADD CONSTRAINT `fk_Avoir_Produit1` FOREIGN KEY (`id_Produit`) REFERENCES `produit` (`id_Produit`),
  ADD CONSTRAINT `fk_Avoir_Vendeur1` FOREIGN KEY (`id_Vendeur`) REFERENCES `vendeur` (`id_Vendeur`);

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `fk_Client_Utilisateur1` FOREIGN KEY (`id_Utilisateur`) REFERENCES `utilisateur` (`id_Utilisateur`);

--
-- Constraints for table `demande`
--
ALTER TABLE `demande`
  ADD CONSTRAINT `demande_ibfk_1` FOREIGN KEY (`id_position`) REFERENCES `position` (`id_pos`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `demande_ibfk_2` FOREIGN KEY (`id_trajet`) REFERENCES `trajet` (`id_Trajet`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Demande_Client1` FOREIGN KEY (`id_Client`) REFERENCES `client` (`id_Client`),
  ADD CONSTRAINT `fk_Demande_Produit1` FOREIGN KEY (`id_Produit`) REFERENCES `produit` (`id_Produit`),
  ADD CONSTRAINT `fk_Demande_Vendeur1` FOREIGN KEY (`id_Vendeur`) REFERENCES `vendeur` (`id_Vendeur`);

--
-- Constraints for table `depot`
--
ALTER TABLE `depot`
  ADD CONSTRAINT `depot_ibfk_1` FOREIGN KEY (`id_position`) REFERENCES `position` (`id_pos`),
  ADD CONSTRAINT `fk_Depot_AgentDepot1` FOREIGN KEY (`id_AgentDepot`) REFERENCES `agentdepot` (`id_AgentDepot`);

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `fk_Reservation_Client1` FOREIGN KEY (`id_Client`) REFERENCES `client` (`id_Client`),
  ADD CONSTRAINT `fk_Reservation_Produit1` FOREIGN KEY (`id_Produit`) REFERENCES `produit` (`id_Produit`),
  ADD CONSTRAINT `fk_Reservation_Vendeur1` FOREIGN KEY (`id_Vendeur`) REFERENCES `vendeur` (`id_Vendeur`),
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`id_position`) REFERENCES `position` (`id_pos`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`id_trajet`) REFERENCES `trajet` (`id_Trajet`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `stocker`
--
ALTER TABLE `stocker`
  ADD CONSTRAINT `fk_Stocker_Depot1` FOREIGN KEY (`id_Depot`) REFERENCES `depot` (`id_Depot`),
  ADD CONSTRAINT `fk_Stocker_Produit1` FOREIGN KEY (`id_Produit`) REFERENCES `produit` (`id_Produit`);

--
-- Constraints for table `trajet`
--
ALTER TABLE `trajet`
  ADD CONSTRAINT `fk_Trajet_Position1` FOREIGN KEY (`pos_debut`) REFERENCES `position` (`id_pos`),
  ADD CONSTRAINT `fk_Trajet_Position2` FOREIGN KEY (`pos_fin`) REFERENCES `position` (`id_pos`);

--
-- Constraints for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `fk_Utilisateur_Position1` FOREIGN KEY (`id_pos`) REFERENCES `position` (`id_pos`);

--
-- Constraints for table `vendeurPlan`
--
ALTER TABLE `vendeurPlan`
  ADD CONSTRAINT `vendeurPlan_ibfk_1` FOREIGN KEY (`id_Vendeur`) REFERENCES `vendeur` (`id_Vendeur`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `vendeurPlan_ibfk_2` FOREIGN KEY (`id_Trajet`) REFERENCES `trajet` (`id_Trajet`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
