DROP TABLE IF EXISTS `user`;

CREATE TABLE appointment_letters.`user` (
  `hospitalId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdOn` datetime NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`hospitalId`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE appointment_letters.`hospital` (
  `hospitalId` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `hospital_name` varchar(255) NOT NULL,
  PRIMARY KEY (`hospitalId`, `user_id`)
);
INSERT INTO `user` VALUES
  (1, 'saverineni', 'suresh.averineni@gmail.com', 'Suresh123', 'Suresh', 'Averineni', TRUE, DEFAULT , NULL);


   CREATE TABLE appointment_letters.`user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdOn` datetime NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE appointment_letters.`hospital` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `hospital_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  FOREIGN KEY fk_user(`user_id`) references user(`id`)
);

CREATE TABLE `hospital` (
  `hospitalId` bigint(20) NOT NULL AUTO_INCREMENT,
  `hospital_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`hospitalId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `user_hospital` (
  `user_id` bigint(20) NOT NULL,
  `hospitalId` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`hospitalId`),
  KEY `fk_userhospital_hospital_id` (`hospitalId`),
  CONSTRAINT `fk_userhospital_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userhospital_hospital` FOREIGN KEY (`hospitalId`) REFERENCES `hospital` (`hospitalId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;