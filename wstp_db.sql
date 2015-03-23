-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Lun 23 Mars 2015 à 12:09
-- Version du serveur: 5.5.24-log
-- Version de PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `wstp_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `idCategory` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`idCategory`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26 ;

-- --------------------------------------------------------

--
-- Structure de la table `tag`
--

CREATE TABLE IF NOT EXISTS `tag` (
  `idTag` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `popularity` int(11) NOT NULL,
  PRIMARY KEY (`idTag`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=107 ;

-- --------------------------------------------------------

--
-- Structure de la table `userwsassociation`
--

CREATE TABLE IF NOT EXISTS `userwsassociation` (
  `idUserWSAssociation` int(11) NOT NULL AUTO_INCREMENT,
  `User_idUser` int(11) NOT NULL,
  `WebService_idWebService` int(11) NOT NULL,
  PRIMARY KEY (`idUserWSAssociation`),
  KEY `User_idUser` (`User_idUser`),
  KEY `WebService_idWebService` (`WebService_idWebService`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=44 ;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=24 ;

-- --------------------------------------------------------

--
-- Structure de la table `webservice`
--

CREATE TABLE IF NOT EXISTS `webservice` (
  `idWebService` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `Category_idCategory` int(11) NOT NULL,
  `popularity` int(11) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  `date_dernier_tag` timestamp NULL DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idWebService`),
  KEY `fk_WebService_Category_idx` (`Category_idCategory`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=54 ;

-- --------------------------------------------------------

--
-- Structure de la table `wstagassociation`
--

CREATE TABLE IF NOT EXISTS `wstagassociation` (
  `idWSTagAssociation` int(11) NOT NULL AUTO_INCREMENT,
  `WebService_idWebService` int(11) NOT NULL,
  `Tag_idTag` int(11) NOT NULL,
  `weight_sum` int(11) NOT NULL DEFAULT '0',
  `voters` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idWSTagAssociation`),
  KEY `fk_WebService_has_Tag_Tag1_idx` (`Tag_idTag`),
  KEY `fk_WebService_has_Tag_WebService1_idx` (`WebService_idWebService`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=124 ;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `userwsassociation`
--
ALTER TABLE `userwsassociation`
  ADD CONSTRAINT `userwsassociation_ibfk_3` FOREIGN KEY (`User_idUser`) REFERENCES `utilisateur` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `userwsassociation_ibfk_4` FOREIGN KEY (`WebService_idWebService`) REFERENCES `webservice` (`idWebService`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `webservice`
--
ALTER TABLE `webservice`
  ADD CONSTRAINT `fk_WebService_Category` FOREIGN KEY (`Category_idCategory`) REFERENCES `category` (`idCategory`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `wstagassociation`
--
ALTER TABLE `wstagassociation`
  ADD CONSTRAINT `fk_WebService_has_Tag_Tag1` FOREIGN KEY (`Tag_idTag`) REFERENCES `tag` (`idTag`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_WebService_has_Tag_WebService1` FOREIGN KEY (`WebService_idWebService`) REFERENCES `webservice` (`idWebService`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
