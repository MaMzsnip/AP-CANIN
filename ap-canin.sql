-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 18 oct. 2022 à 15:12
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `ap-canin`
--

-- --------------------------------------------------------

--
-- Structure de la table `classe`
--

DROP TABLE IF EXISTS `classe`;
CREATE TABLE IF NOT EXISTS `classe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET latin1 NOT NULL,
  `nbExs` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `classe`
--

INSERT INTO `classe` (`id`, `name`, `nbExs`) VALUES
(2, 'Classe 1', 9);

-- --------------------------------------------------------

--
-- Structure de la table `club`
--

DROP TABLE IF EXISTS `club`;
CREATE TABLE IF NOT EXISTS `club` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET latin1 DEFAULT NULL,
  `adress` varchar(150) CHARACTER SET latin1 DEFAULT NULL,
  `postalCode` varchar(10) CHARACTER SET latin1 DEFAULT NULL,
  `city` varchar(150) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `club`
--

INSERT INTO `club` (`id`, `name`, `adress`, `postalCode`, `city`) VALUES
(3, 'AMICALE DES CHIENS DE VILLERS ALLERAND', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `contest`
--

DROP TABLE IF EXISTS `contest`;
CREATE TABLE IF NOT EXISTS `contest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `judge` int(11) DEFAULT NULL,
  `club` int(11) DEFAULT NULL,
  `name` varchar(150) CHARACTER SET latin1 NOT NULL,
  `dateStart` date NOT NULL,
  `dateEnd` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_contest_judge` (`judge`),
  KEY `FK_contest_club` (`club`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `contest`
--

INSERT INTO `contest` (`id`, `judge`, `club`, `name`, `dateStart`, `dateEnd`) VALUES
(3, 2, 3, 'CONCOURS D\'OBEISSANCE', '2022-01-18', '2022-01-19');

-- --------------------------------------------------------

--
-- Structure de la table `dog`
--

DROP TABLE IF EXISTS `dog`;
CREATE TABLE IF NOT EXISTS `dog` (
  `fapac` int(11) NOT NULL,
  `club` int(11) DEFAULT NULL,
  `ct` int(11) DEFAULT NULL,
  `name` varchar(500) CHARACTER SET latin1 DEFAULT NULL,
  `race` varchar(500) CHARACTER SET latin1 DEFAULT NULL,
  `dateBirth` date DEFAULT NULL,
  `gender` char(2) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`fapac`),
  KEY `FK_dog_club` (`club`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `dog`
--

INSERT INTO `dog` (`fapac`, `club`, `ct`, `name`, `race`, `dateBirth`, `gender`) VALUES
(1074074, 3, 127426, 'PETSY DU SERMENT DES BRUMES                             ', 'BERGER DE BEAUCE ', '2019-01-07', 'F');

-- --------------------------------------------------------

--
-- Structure de la table `driver`
--

DROP TABLE IF EXISTS `driver`;
CREATE TABLE IF NOT EXISTS `driver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) CHARACTER SET latin1 NOT NULL,
  `lastName` varchar(500) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `driver`
--

INSERT INTO `driver` (`id`, `name`, `lastName`) VALUES
(2, 'FOURNAISE', 'BRUNO');

-- --------------------------------------------------------

--
-- Structure de la table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE IF NOT EXISTS `evaluation` (
  `inscription` int(11) NOT NULL,
  `exercise` int(11) NOT NULL,
  `note` double NOT NULL,
  `comment` varchar(5000) NOT NULL,
  PRIMARY KEY (`inscription`,`exercise`),
  KEY `FK_evaluation_exercise` (`exercise`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `evaluation`
--

INSERT INTO `evaluation` (`inscription`, `exercise`, `note`, `comment`) VALUES
(4, 1, 9, '* Excellent travail  *'),
(4, 2, 6.5, '* Bon travail        *'),
(4, 3, 6.5, '* Bon travail        *'),
(4, 4, 6.5, '* Bon travail        *'),
(4, 5, 7.5, '* Très-bon travail   *'),
(4, 6, 5, '* Travail suffisant  *'),
(4, 7, 0, '* Travail non classé *'),
(4, 8, 7.5, '* Très-bon travail   *'),
(4, 9, 8, '* Excellent travail  *');

-- --------------------------------------------------------

--
-- Structure de la table `exercise`
--

DROP TABLE IF EXISTS `exercise`;
CREATE TABLE IF NOT EXISTS `exercise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classe` int(11) DEFAULT NULL,
  `name` varchar(150) CHARACTER SET latin1 NOT NULL,
  `coefficient` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_exercise_classe` (`classe`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `exercise`
--

INSERT INTO `exercise` (`id`, `classe`, `name`, `coefficient`) VALUES
(1, 2, 'Absence assise en groupe pendant 1 mn, conducteur à vue', 3),
(2, 2, 'Rappel', 4),
(3, 2, 'Suite au pied', 4),
(4, 2, 'Blocage en marchant, position: debout', 3),
(5, 2, 'En avant dans le carré et couché', 4),
(6, 2, 'Saut de haie avec rapport d\'un haltère bois', 4),
(7, 2, 'Positions à distance', 4),
(8, 2, 'Envoi autour d\'un groupe de cône et retour', 4),
(9, 2, 'Impression générale', 2);

-- --------------------------------------------------------

--
-- Structure de la table `inscription`
--

DROP TABLE IF EXISTS `inscription`;
CREATE TABLE IF NOT EXISTS `inscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contest` int(11) DEFAULT NULL,
  `classe` int(11) DEFAULT NULL,
  `dog` int(11) DEFAULT NULL,
  `driver` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_inscription_contest` (`contest`),
  KEY `FK_inscription_classe` (`classe`),
  KEY `FK_inscription_dog` (`dog`),
  KEY `FK_inscription_driver` (`driver`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `inscription`
--

INSERT INTO `inscription` (`id`, `contest`, `classe`, `dog`, `driver`) VALUES
(4, 3, 2, 1074074, 2);

-- --------------------------------------------------------

--
-- Structure de la table `judge`
--

DROP TABLE IF EXISTS `judge`;
CREATE TABLE IF NOT EXISTS `judge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `lastName` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `judge`
--

INSERT INTO `judge` (`id`, `name`, `lastName`) VALUES
(2, 'ROSER', 'Bernard');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `contest`
--
ALTER TABLE `contest`
  ADD CONSTRAINT `FK_contest_club` FOREIGN KEY (`club`) REFERENCES `club` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_contest_judge` FOREIGN KEY (`judge`) REFERENCES `judge` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `dog`
--
ALTER TABLE `dog`
  ADD CONSTRAINT `FK_dog_club` FOREIGN KEY (`club`) REFERENCES `club` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `evaluation`
--
ALTER TABLE `evaluation`
  ADD CONSTRAINT `FK_evaluation_exercise` FOREIGN KEY (`exercise`) REFERENCES `exercise` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_evaluation_inscription` FOREIGN KEY (`inscription`) REFERENCES `inscription` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `exercise`
--
ALTER TABLE `exercise`
  ADD CONSTRAINT `FK_exercise_classe` FOREIGN KEY (`classe`) REFERENCES `classe` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `inscription`
--
ALTER TABLE `inscription`
  ADD CONSTRAINT `FK_inscription_classe` FOREIGN KEY (`classe`) REFERENCES `classe` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_inscription_contest` FOREIGN KEY (`contest`) REFERENCES `contest` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_inscription_dog` FOREIGN KEY (`dog`) REFERENCES `dog` (`fapac`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_inscription_driver` FOREIGN KEY (`driver`) REFERENCES `driver` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
