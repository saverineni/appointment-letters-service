DROP TABLE IF EXISTS `user`;

CREATE TABLE appointment_letters.`user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdOn` datetime NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `resetToken` varchar(255) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
);

INSERT INTO `user` VALUES
  (1, 'saverineni', 'suresh.averineni@gmail.com', 'Suresh123', 'Suresh', 'Averineni', TRUE, DEFAULT , NULL);