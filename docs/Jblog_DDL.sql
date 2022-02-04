CREATE TABLE IF NOT EXISTS `jblog`.`user` (
  `id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

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
ENGINE = InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

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
ENGINE = InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

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
ENGINE = InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

