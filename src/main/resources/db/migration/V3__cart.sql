CREATE TABLE IF NOT EXISTS `cart` (
  `id` VARCHAR(36) PRIMARY KEY,
  `quantity` INT NOT NULL,
  `price` INT NOT NULL,
  `dish_id` VARCHAR(36) NOT NULL,
  `user_id` VARCHAR(36),
  FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);