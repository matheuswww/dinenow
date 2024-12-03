CREATE TABLE IF NOT EXISTS `client_order` (
  `id` VARCHAR(36) PRIMARY KEY,
  `price` INT NOT NULL,
  `freight` INT NOT NULL,
  `quantity` INT NOT NULL,
  `street` VARCHAR(160) NOT NULL,
  `number` INT NOT NULL,
  `complement` VARCHAR(40) NOT NULL,
  `neighborhood` VARCHAR(60) NOT NULL,
  `obs` VARCHAR(150),
  `status` ENUM("waiting","preparing","route","finished"),
  `dish_id` VARCHAR(36) NOT NULL,
  `user_id` VARCHAR(36) NOT NULL
);