CREATE SCHEMA `chat_users` ;

CREATE TABLE `chat_users`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(20) NOT NULL,
  `login` VARCHAR(20) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE);
  
INSERT INTO `chat_users`.`users` (`nickname`, `login`, `password`) VALUES ('username1', 'login1', 'pass1');
INSERT INTO `chat_users`.`users` (`nickname`, `login`, `password`) VALUES ('username2', 'login2', 'pass2');
INSERT INTO `chat_users`.`users` (`nickname`, `login`, `password`) VALUES ('username3', 'login3', 'pass3');