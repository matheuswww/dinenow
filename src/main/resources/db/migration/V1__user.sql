CREATE TABLE IF NOT EXISTS `user` (
  `id` VARCHAR(36) PRIMARY KEY,
  `email` VARCHAR(150) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL
);