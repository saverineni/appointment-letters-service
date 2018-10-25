DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(255) UNIQUE,
  `email` VARCHAR(255) UNIQUE,
  `password` CHAR(60),
  `firstName` VARCHAR(50),
  `lastName` VARCHAR(50),
  `enabled` BOOLEAN,
  `createdOn` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `resetToken` CHAR(36)
);

INSERT INTO `user` VALUES
  (1, 'saverineni', 'suresh.averineni@gmail.com', 'Suresh123', 'Suresh', 'Averineni', TRUE, DEFAULT , NULL);