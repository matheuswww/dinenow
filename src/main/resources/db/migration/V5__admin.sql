CREATE TABLE IF NOT EXISTS `admin` (
  `id` VARCHAR(36) PRIMARY KEY,
  `email` VARCHAR(150) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(152) NOT NULL
);