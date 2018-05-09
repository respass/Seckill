CREATE TABLE `seckill`.`user` (
  `id` BEGINT(20)  NOT NULL AUTO_INCREMENT,
  `phone` VARCHAR(20) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `salt` VARCHAR(10) NOT NULL COMMENT 'MD5(MD5(pass明文+固定salt) + salt',
  `nickname` VARCHAR(45) NULL,
  `register_date` DATETIME NULL COMMENT '注册时间',
  `last_login_date` DATETIME NULL COMMENT '上一次登入时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `usercol_UNIQUE` (`phone` ASC));


CREATE TABLE `seckill`.`goods` (
  `id` BEGINT(20) NOT NULL COMMENT '商品ID',
  `name` VARCHAR(16) NOT NULL COMMENT '\'商品名称\'',
  `img` VARCHAR(64) NULL,
  `price` DECIMAL(10,2) NULL DEFAULT 0.00 COMMENT '商品单价',
  `stock` INT NULL DEFAULT 0 COMMENT '商品库存, -1代表没有库存',
  PRIMARY KEY (`id`));

CREATE TABLE `seckill`.`seckillGoods` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `goods_id` BIGINT(20) NULL,
  `price` DECIMAL(10,2) NULL DEFAULT 0.00,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  `stock` INT NULL DEFAULT 0 COMMENT '商品库存, -1代表没有库存',
  PRIMARY KEY (`id`));

CREATE TABLE `seckill`.`order` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NULL,
  `goods_id` BIGINT(20) NULL,
  `goods_name` VARCHAR(16) NULL,
  `goods_count` INT NULL,
  `goods_price` DECIMAL(10,2) NULL DEFAULT 0.00,
  `status` TINYINT(4) NULL DEFAULT 0 COMMENT '订单状态,0未支付,1已支付',
  `create_time` DATETIME NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `seckill`.`seckillOrder` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NULL,
  `order_id` BIGINT(20) NULL,
  `goods_id` BIGINT(20) NULL,
  PRIMARY KEY (`id`));
