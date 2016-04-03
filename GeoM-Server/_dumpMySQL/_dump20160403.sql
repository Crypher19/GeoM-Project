-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 03, 2016 alle 21:03
-- Versione del server: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `geom`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `company_login_table`
--

CREATE TABLE IF NOT EXISTS `company_login_table` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `CodiceUtente` int(10) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Compagnia` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Compagnia` (`Compagnia`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dump dei dati per la tabella `company_login_table`
--

INSERT INTO `company_login_table` (`ID`, `CodiceUtente`, `Password`, `Compagnia`) VALUES
(1, 1234, '$2y$10$PGYqMd0ELVdd8Iy0RxysEehJcAeqoWv6YjM4h3kPBW3CtoZ5LWZ5C', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `company_table`
--

CREATE TABLE IF NOT EXISTS `company_table` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `NumTel` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dump dei dati per la tabella `company_table`
--

INSERT INTO `company_table` (`ID`, `Nome`, `Email`, `NumTel`) VALUES
(1, 'ASF', 'info@asf.it', '');

-- --------------------------------------------------------

--
-- Struttura della tabella `transports_table`
--

CREATE TABLE IF NOT EXISTS `transports_table` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `TipoMezzo` varchar(30) DEFAULT NULL,
  `NomeMezzo` varchar(30) DEFAULT NULL,
  `Tratta` varchar(100) DEFAULT NULL,
  `Attivo` varchar(5) DEFAULT 'false',
  `Compagnia` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Compagnia` (`Compagnia`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dump dei dati per la tabella `transports_table`
--

INSERT INTO `transports_table` (`ID`, `TipoMezzo`, `NomeMezzo`, `Tratta`, `Attivo`, `Compagnia`) VALUES
(1, 'pullman', 'C82', 'cantu-mariano', 'false', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `transport_users_table`
--

CREATE TABLE IF NOT EXISTS `transport_users_table` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Nome` varchar(50) DEFAULT NULL,
  `Cognome` varchar(50) DEFAULT NULL,
  `Compagnia` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Compagnia` (`Compagnia`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dump dei dati per la tabella `transport_users_table`
--

INSERT INTO `transport_users_table` (`ID`, `Username`, `Password`, `Nome`, `Cognome`, `Compagnia`) VALUES
(5, 'test', '$2y$10$4VnHIu2t38d76/nac98XKORhM1bA3jcHj2PMefQlSwnmSvrSAK/ZK', NULL, NULL, 1);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `company_login_table`
--
ALTER TABLE `company_login_table`
  ADD CONSTRAINT `company_login_table_ibfk_1` FOREIGN KEY (`Compagnia`) REFERENCES `company_table` (`ID`);

--
-- Limiti per la tabella `transports_table`
--
ALTER TABLE `transports_table`
  ADD CONSTRAINT `transports_table_ibfk_1` FOREIGN KEY (`Compagnia`) REFERENCES `company_table` (`ID`);

--
-- Limiti per la tabella `transport_users_table`
--
ALTER TABLE `transport_users_table`
  ADD CONSTRAINT `transport_users_table_ibfk_1` FOREIGN KEY (`Compagnia`) REFERENCES `company_table` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
