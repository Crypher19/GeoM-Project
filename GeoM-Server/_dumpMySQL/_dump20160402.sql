-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 02, 2016 alle 21:21
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
-- Struttura della tabella `company_table`
--

CREATE TABLE IF NOT EXISTS `company_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `transports_table`
--

CREATE TABLE IF NOT EXISTS `transports_table` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TipoMezzo` varchar(20) DEFAULT NULL,
  `Compagnia` varchar(20) NOT NULL,
  `NomeMezzo` varchar(20) DEFAULT NULL,
  `Tratta` varchar(60) NOT NULL,
  `Attivo` varchar(5) DEFAULT 'false',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dump dei dati per la tabella `transports_table`
--

INSERT INTO `transports_table` (`ID`, `TipoMezzo`, `Compagnia`, `NomeMezzo`, `Tratta`, `Attivo`) VALUES
(8, 'treno', 'Trenord', 'ETR501', 'qui-li', 'false'),
(10, 'treno', 'Trenord', 'ETR502', 'qua-la', 'false'),
(11, 'pullman', 'ASF', 'C82', 'li-la', 'false'),
(12, 'pullman', 'ASF', 'C81', 'qui-la', 'false'),
(14, 'pullman', 'FNMA', 'z130', 'y-u', 'false'),
(15, 'pullman', 'FNMA', 'z144', 'p-o', 'false');

-- --------------------------------------------------------

--
-- Struttura della tabella `transport_users_table`
--

CREATE TABLE IF NOT EXISTS `transport_users_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dump dei dati per la tabella `transport_users_table`
--

INSERT INTO `transport_users_table` (`id`, `username`, `password`) VALUES
(1, 'test', 'test');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
