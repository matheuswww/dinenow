CREATE TABLE IF NOT EXISTS `client_order_dish` (
  `id` VARCHAR(36) PRIMARY KEY,
  `dish_id` VARCHAR(36) NOT NULL,
  `price` INT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `quantity` INT NOT NULL,
  `order_id` VARCHAR(36) NOT NULL,
   FOREIGN KEY (`order_id`) REFERENCES `client_order` (`id`)
);