CREATE TABLE IF NOT EXISTS `user` (
  `id` VARCHAR NOT NULL PRIMARY KEY,
  `email` VARCHAR NOT NULL,
  `name` VARCHAR NOT NULL,
  `password` VARCHAR NOT NULL
);