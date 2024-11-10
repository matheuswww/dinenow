CREATE TABLE IF NOT EXISTS `order` (
  `id` VARCHAR NOT NULL PRIMARY KEY,
  `price` INT NOT NULL,
  `freight` INT NOT NULL,
  `quantity` INT NOT NULL,
  `street` VARCHAR NOT NULL,
  `number` INT NOT NULL,
  `neighborhood` BIGINT NOT NULL,
  `obs` VARCHAR,
  `status` VARCHAR NOT NULL,
  `dish_id` VARCHAR NOT NULL,
  FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`)
);