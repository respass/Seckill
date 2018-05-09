CREATE TABLE `seckill`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `phone` VARCHAR(20) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `salt` VARCHAR(10) NOT NULL COMMENT 'MD5(MD5(pass明文+固定salt) + salt',
  `nickname` VARCHAR(45) NULL,
  `register_date` DATETIME NULL COMMENT '注册时间',
  `last_login_date` DATETIME NULL COMMENT '上一次登入时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `usercol_UNIQUE` (`phone` ASC));

