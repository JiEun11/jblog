-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema jblog
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema jblog
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `jblog` DEFAULT CHARACTER SET utf8 ;
USE `jblog` ;

-- -----------------------------------------------------
-- Table `jblog`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jblog`.`user` (
  `id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jblog`.`blog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jblog`.`blog` (
  `title` VARCHAR(45) NOT NULL,
  `logo` VARCHAR(200) NOT NULL,
  `user_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_blog_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `jblog`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jblog`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jblog`.`category` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `blog_no` INT NOT NULL,
  `blog_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`no`),
  CONSTRAINT `fk_category_blog1`
    FOREIGN KEY (`blog_id`)
    REFERENCES `jblog`.`blog` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jblog`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jblog`.`post` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `contents` TEXT NOT NULL,
  `reg_date` DATETIME NOT NULL,
  `category_no` INT NOT NULL,
  PRIMARY KEY (`no`),
  CONSTRAINT `fk_post_category1`
    FOREIGN KEY (`category_no`)
    REFERENCES `jblog`.`category` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
