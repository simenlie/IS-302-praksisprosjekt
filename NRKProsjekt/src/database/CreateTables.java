/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Simen
 */
public class CreateTables {

    public String createDatabase() {
        return "CREATE DATABASE `malavi`";
    }

    public String createPicture() {
        return "CREATE TABLE `PICTURE` (`idPIC` int(11) NOT NULL AUTO_INCREMENT,`IPIC` varchar(45) COLLATE latin1_danish_ci NOT NULL,`IIMG` longblob,`IRES` varchar(50) COLLATE latin1_danish_ci DEFAULT NULL, `ISIZ` int(11) DEFAULT NULL,PRIMARY KEY (`idPIC`))";
    }

    public String createMetadata() {
        return "CREATE TABLE `METADATA` (`idMETADATA` int(11) NOT NULL AUTO_INCREMENT,`IMED` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ISRF` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`IENG` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ITCH` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ICOP` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ISFT` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ICRD` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ICMT` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ISBJ` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ISRC` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,`IDIG` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,PRIMARY KEY (`idMETADATA`))";
    }

    public String createSong() {
        return "CREATE TABLE `SONG` (`idSONG` int(11) NOT NULL AUTO_INCREMENT,`ILEN` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`IGNR` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ISON` blob,`INAM` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`IKEY` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`idMETADATA` int(11) DEFAULT NULL,`IDIS` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,`IREG` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,`IPEO` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,`ICOM` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,`IVIL` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,`ILYR` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,PRIMARY KEY (`idSONG`),KEY `idMETADATA_idx` (`idMETADATA`),CONSTRAINT `idMETADATA` FOREIGN KEY (`idMETADATA`) REFERENCES `METADATA` (`idMETADATA`) ON DELETE NO ACTION ON UPDATE NO ACTION)";
    }

    public String createAlbum() {
        return "CREATE TABLE `ALBUM` (`idALBUM` int(11) NOT NULL AUTO_INCREMENT,`IALB` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,PRIMARY KEY (`idALBUM`))";
    }

    public String createArtist() {
        return "CREATE TABLE `ARTIST` (`idARTIST` int(11) NOT NULL AUTO_INCREMENT,`ILAN` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`ICON` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`IART` varchar(45) COLLATE latin1_danish_ci DEFAULT NULL,`IPLA` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,PRIMARY KEY (`idARTIST`))";
    }

    public String createAAS() {
        return "CREATE TABLE `AAS` (`idARTIST` int(11) NOT NULL,`idSONG` int(11) NOT NULL,`idALBUM` int(11) NOT NULL,`idPIC` int(11) DEFAULT NULL,PRIMARY KEY (`idARTIST`,`idSONG`,`idALBUM`),KEY `idSONG_idx` (`idSONG`),KEY `idALBUM_idx` (`idALBUM`),KEY `idPIC` (`idPIC`),CONSTRAINT `AAS_ibfk_1` FOREIGN KEY (`idPIC`) REFERENCES `PICTURE` (`idPIC`),CONSTRAINT `idALBUM` FOREIGN KEY (`idALBUM`) REFERENCES `ALBUM` (`idALBUM`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `idARTIST` FOREIGN KEY (`idARTIST`) REFERENCES `ARTIST` (`idARTIST`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `idSONG` FOREIGN KEY (`idSONG`) REFERENCES `SONG` (`idSONG`) ON DELETE NO ACTION ON UPDATE NO ACTION)";
    }

    public String createUsers() {
        return "CREATE TABLE `users` (`idUSER` int(11) NOT NULL AUTO_INCREMENT,`username` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,`user_password` varchar(90) COLLATE latin1_danish_ci DEFAULT NULL,`admin` tinyint(1) DEFAULT NULL,PRIMARY KEY (`idUSER`))";
    }
}
