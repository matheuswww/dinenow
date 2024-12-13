CREATE TABLE IF NOT EXISTS `client_order` (
  `id` VARCHAR(36) PRIMARY KEY,
  `total_price` INT NOT NULL,
  `freight` INT NOT NULL,
  `street` VARCHAR(50) NOT NULL,
  `number` INT NOT NULL,
  `neighborhood` VARCHAR(60) NOT NULL,
  `obs` VARCHAR(100),
  `complement` VARCHAR(40) NOT NULL,
  `status` ENUM("waiting","preparing","route","finished") DEFAULT "waiting" NOT NULL,
  `user_id` VARCHAR(36) NOT NULL,
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);