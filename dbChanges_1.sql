-- MySQL Script generated by MySQL Workbench
-- Wed Jun  5 03:17:21 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema InvoiceManagment
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema InvoiceManagment
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `InvoiceManagment` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `InvoiceManagment` ;

 /**/
SET @idSchemaVersion = '1';
SET @change_number = 1;
SET @version = '1.0.0';
SET @applied = now();
SET @description = 'kreiranje tabel';
SET @applied_by = current_user();
SET @file = 'dbChanges_1.sql';

-- -----------------------------------------------------
-- Table `InvoiceManagment`.`Article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InvoiceManagment`.`Article` (
  `idArticle` BINARY(16) NOT NULL,
  `barcode` VARCHAR(14) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `vat` DECIMAL(5,2) NOT NULL,
  `stock` INT NOT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created` TIMESTAMP NOT NULL,
  `modified` TIMESTAMP NULL,
  PRIMARY KEY (`idArticle`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InvoiceManagment`.`Company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InvoiceManagment`.`Company` (
  `idCompany` BINARY(16) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `tax_number` VARCHAR(45) NOT NULL,
  `registration_number` VARCHAR(45) NOT NULL,
  `taxpayer` TINYINT(1) NOT NULL,
  `phone_number` VARCHAR(30) NOT NULL,
  `adress` VARCHAR(150) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `modified` TIMESTAMP NULL,
  PRIMARY KEY (`idCompany`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InvoiceManagment`.`Invoice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InvoiceManagment`.`Invoice` (
  `idInvoice` BINARY(16) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  `total_vat` DECIMAL(10,2) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `modified` TIMESTAMP NULL,
  `issuer_id` BINARY(16) NOT NULL,
  `customer_id` BINARY(16) NULL,
  PRIMARY KEY (`idInvoice`),
  INDEX `fk_Invoice_Company1_idx` (`customer_id` ASC),
  INDEX `fk_Invoice_Company2_idx` (`issuer_id` ASC),
  CONSTRAINT `fk_Invoice_Company1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `InvoiceManagment`.`Company` (`idCompany`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Invoice_Company2`
    FOREIGN KEY (`issuer_id`)
    REFERENCES `InvoiceManagment`.`Company` (`idCompany`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InvoiceManagment`.`Invoice_has_Article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InvoiceManagment`.`Invoice_has_Article` (
  `quantity` INT NOT NULL,
  `Article_idArticle` BINARY(16) NOT NULL,
  `Invoice_idInvoice` BINARY(16) NOT NULL,
  PRIMARY KEY (`Article_idArticle`, `Invoice_idInvoice`),
  INDEX `fk_Invoice_has_Article_Article1_idx` (`Article_idArticle` ASC),
  INDEX `fk_Invoice_has_Article_Invoice1_idx` (`Invoice_idInvoice` ASC),
  CONSTRAINT `fk_Invoice_has_Article_Article1`
    FOREIGN KEY (`Article_idArticle`)
    REFERENCES `InvoiceManagment`.`Article` (`idArticle`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Invoice_has_Article_Invoice1`
    FOREIGN KEY (`Invoice_idInvoice`)
    REFERENCES `InvoiceManagment`.`Invoice` (`idInvoice`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InvoiceManagment`.`InternalArticle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InvoiceManagment`.`InternalArticle` (
  `idInternalArticle` BINARY(16) NOT NULL,
  `ime` VARCHAR(45) NOT NULL,
  `cena` DECIMAL(10,2) NOT NULL,
  `vat` DECIMAL(5,2) NOT NULL,
  `stock` INT NOT NULL,
  `internal_id` VARCHAR(4) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `modified` TIMESTAMP NULL,
  PRIMARY KEY (`idInternalArticle`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InvoiceManagment`.`Invoice_has_InternalArticle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InvoiceManagment`.`Invoice_has_InternalArticle` (
  `quantity` VARCHAR(45) NOT NULL,
  `InternalArticle_idInternalArticle` BINARY(16) NOT NULL,
  `Invoice_idInvoice` BINARY(16) NOT NULL,
  PRIMARY KEY (`InternalArticle_idInternalArticle`, `Invoice_idInvoice`),
  INDEX `fk_Invoice_has_InternalArticle_Invoice1_idx` (`Invoice_idInvoice` ASC),
  CONSTRAINT `fk_Invoice_has_InternalArticle_InternalArticle1`
    FOREIGN KEY (`InternalArticle_idInternalArticle`)
    REFERENCES `InvoiceManagment`.`InternalArticle` (`idInternalArticle`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Invoice_has_InternalArticle_Invoice1`
    FOREIGN KEY (`Invoice_idInvoice`)
    REFERENCES `InvoiceManagment`.`Invoice` (`idInvoice`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InvoiceManagment`.`SchemaVersion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InvoiceManagment`.`SchemaVersion` (
  `idSchemaVersion` BINARY(16) NOT NULL, 
  `change_number` INT NOT NULL,
  `version` VARCHAR(30) NOT NULL,
  `applied` DATETIME NOT NULL,
  `applied_by` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `file` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idSchemaVersion`))
ENGINE = InnoDB;

USE `InvoiceManagment`;

/*
INSERT INTO SchemaVersion (change_number, version, applied, applied_by, file)
VALUES (@change_number, @version, @applied, @applied_by, @file);
*/
INSERT INTO SchemaVersion (idSchemaVersion, change_number, version, applied, applied_by, description, file)
VALUES (@idSchemaVersion, @change_number, @version, @applied, @applied_by, @description, @file);

DELIMITER $$
USE `InvoiceManagment`$$
CREATE DEFINER = CURRENT_USER TRIGGER `InvoiceManagment`.`Article_BEFORE_UPDATE` BEFORE UPDATE ON `Article` FOR EACH ROW
BEGIN
INSERT INTO modified VALUES(current_timestamp());
END$$

USE `InvoiceManagment`$$
CREATE DEFINER = CURRENT_USER TRIGGER `InvoiceManagment`.`Company_BEFORE_UPDATE` BEFORE UPDATE ON `Company` FOR EACH ROW
BEGIN
INSERT INTO modified VALUES(current_timestamp());
END$$

USE `InvoiceManagment`$$
CREATE DEFINER = CURRENT_USER TRIGGER `InvoiceManagment`.`Invoice_BEFORE_UPDATE` BEFORE UPDATE ON `Invoice` FOR EACH ROW
BEGIN
INSERT INTO modified VALUES(current_timestamp());
END$$

USE `InvoiceManagment`$$
CREATE DEFINER = CURRENT_USER TRIGGER `InvoiceManagment`.`InternalArticle_BEFORE_UPDATE` BEFORE UPDATE ON `InternalArticle` FOR EACH ROW
BEGIN
INSERT INTO modified VALUES(current_timestamp());
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
