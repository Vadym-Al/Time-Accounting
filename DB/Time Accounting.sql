-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema time_accounting
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema time_accounting
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `time_accounting` DEFAULT CHARACTER SET utf8 ;
USE `time_accounting` ;

-- -----------------------------------------------------
-- Table `time_accounting`.`administrator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_accounting`.`administrator` (
  `administrator_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `company` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`administrator_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `time_accounting`.`team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_accounting`.`team` (
  `team_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `company` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `administrator_id` INT NOT NULL,
  PRIMARY KEY (`team_id`),
  INDEX `fk_team_administrator1_idx` (`administrator_id` ASC) VISIBLE,
  CONSTRAINT `fk_team_administrator1`
    FOREIGN KEY (`administrator_id`)
    REFERENCES `time_accounting`.`administrator` (`administrator_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `time_accounting`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_accounting`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NULL,
  `company` VARCHAR(45) NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `team_id` INT NOT NULL,
  `administrator_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_team1_idx` (`team_id` ASC) VISIBLE,
  INDEX `fk_user_administrator1_idx` (`administrator_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_team1`
    FOREIGN KEY (`team_id`)
    REFERENCES `time_accounting`.`team` (`team_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_administrator1`
    FOREIGN KEY (`administrator_id`)
    REFERENCES `time_accounting`.`administrator` (`administrator_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `time_accounting`.`activity_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_accounting`.`activity_type` (
  `activity_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(25) NOT NULL,
  `about` VARCHAR(255) NULL,
  `administrator_id` INT NOT NULL,
  PRIMARY KEY (`activity_id`),
  INDEX `fk_activity_type_administrator1_idx` (`administrator_id` ASC) VISIBLE,
  CONSTRAINT `fk_activity_type_administrator1`
    FOREIGN KEY (`administrator_id`)
    REFERENCES `time_accounting`.`administrator` (`administrator_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `time_accounting`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_accounting`.`task` (
  `task_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `deadline` DATE NOT NULL,
  `about` VARCHAR(45) NULL,
  `wasted_time` TIME NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `activity_type_id` INT NOT NULL,
  `administrator_id` INT NOT NULL,
  PRIMARY KEY (`task_id`),
  INDEX `fk_task_activity_type_idx` (`activity_type_id` ASC) VISIBLE,
  INDEX `fk_task_administrator1_idx` (`administrator_id` ASC) VISIBLE,
  CONSTRAINT `fk_task_activity_type`
    FOREIGN KEY (`activity_type_id`)
    REFERENCES `time_accounting`.`activity_type` (`activity_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_administrator1`
    FOREIGN KEY (`administrator_id`)
    REFERENCES `time_accounting`.`administrator` (`administrator_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `time_accounting`.`request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_accounting`.`request` (
  `request_id` INT NOT NULL AUTO_INCREMENT,
  `about` VARCHAR(255) NULL,
  `activity_type_id` INT NOT NULL,
  `administrator_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`request_id`),
  INDEX `fk_request_activity_type1_idx` (`activity_type_id` ASC) VISIBLE,
  INDEX `fk_request_administrator1_idx` (`administrator_id` ASC) VISIBLE,
  INDEX `fk_request_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_request_activity_type1`
    FOREIGN KEY (`activity_type_id`)
    REFERENCES `time_accounting`.`activity_type` (`activity_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_request_administrator1`
    FOREIGN KEY (`administrator_id`)
    REFERENCES `time_accounting`.`administrator` (`administrator_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_request_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `time_accounting`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `time_accounting`.`user_has_task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_accounting`.`user_has_task` (
  `user_id` INT NOT NULL,
  `task_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `task_id`),
  INDEX `fk_user_has_task_task1_idx` (`task_id` ASC) VISIBLE,
  INDEX `fk_user_has_task_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_task_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `time_accounting`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_task_task1`
    FOREIGN KEY (`task_id`)
    REFERENCES `time_accounting`.`task` (`task_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
