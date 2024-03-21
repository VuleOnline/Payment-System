-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema payment
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema payment
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `payment` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `payment` ;

-- -----------------------------------------------------
-- Table `payment`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(20) NULL DEFAULT NULL,
  `lname` VARCHAR(20) NULL DEFAULT NULL,
  `uname` VARCHAR(10) NULL DEFAULT NULL,
  `password` VARCHAR(10) NULL DEFAULT NULL,
  `isAdmin` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `unique_uname` (`uname` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `payment`.`billing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment`.`billing` (
  `SNo` VARCHAR(50) NOT NULL,
  `comm` DECIMAL(10,2) NULL DEFAULT NULL,
  `total` DECIMAL(10,2) NULL DEFAULT NULL,
  `recMoney` DECIMAL(10,2) NULL DEFAULT NULL,
  `changes` DECIMAL(10,2) NULL DEFAULT NULL,
  `id` INT NOT NULL,
  `date_` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SNo`, `date_`, `id`),
  INDEX `user_id` (`id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`id`)
    REFERENCES `payment`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `payment`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment`.`orders` (
  `SNo` INT NOT NULL,
  `fullname` VARCHAR(50) NULL DEFAULT NULL,
  `address` VARCHAR(30) NULL DEFAULT NULL,
  `city` VARCHAR(20) NULL DEFAULT NULL,
  `refNo` VARCHAR(25) NULL DEFAULT NULL,
  `recAcc` VARCHAR(25) NULL DEFAULT NULL,
  `amount` DECIMAL(10,2) NULL DEFAULT NULL,
  `comm` DECIMAL(8,2) NULL DEFAULT NULL,
  `total` DECIMAL(10,2) NULL DEFAULT NULL,
  `date_` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cancelled` TINYINT(1) NULL DEFAULT '0',
  `forwarded` TINYINT(1) NULL DEFAULT '0',
  `id` INT NOT NULL,
  `paid` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`SNo`, `date_`, `id`),
  INDEX `id` (`id` ASC) VISIBLE,
  CONSTRAINT `id`
    FOREIGN KEY (`id`)
    REFERENCES `payment`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
