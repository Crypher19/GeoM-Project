-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2016 alle 11:19
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
(8, 'gino', 'pietra', 'ciao', '', 'false'),
(10, 'pullmano', 'veloce', 'molto', '', 'false'),
(11, 'pullmano', 'veloce', 'molto', '', 'false'),
(12, 'pullmano', 'veloce', 'molto', '', 'false'),
(13, 'pullmano', 'veloce', 'molto', 'test', 'false'),
(14, 'pullmano', 'veloce', 'molto', 'test', 'false'),
(15, 'pullmano', 'veloce', 'molto', 'test', 'false');

-- --------------------------------------------------------

--
-- Struttura della tabella `transport_users_table`
--

CREATE TABLE IF NOT EXISTS `transport_users_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `pass` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dump dei dati per la tabella `transport_users_table`
--

INSERT INTO `transport_users_table` (`id`, `username`, `pass`) VALUES
(1, 'test', 'test');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
