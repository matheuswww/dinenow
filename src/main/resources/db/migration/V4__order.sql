CREATE TABLE IF NOT EXISTS `order` (
  `id` VARCHAR(36) PRIMARY KEY,
  `price` INT NOT NULL,
  `freight` INT NOT NULL,
  `quantity` INT NOT NULL,
  `street` VARCHAR(50) NOT NULL,
  `number` INT NOT NULL,
  `neighborhood` BIGINT NOT NULL,
  `obs` VARCHAR(100),
  `status` VARCHAR(15) NOT NULL,
  `dish_id` VARCHAR(36) NOT NULL,
  FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`)
);