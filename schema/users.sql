DROP TABLE IF EXISTS `user`;

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


CREATE TABLE `hospital` (
  `hospital_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hospital_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`hospital_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `user_hospital` (
  `user_id` bigint(20) NOT NULL,
  `hospital_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`hospital_id`),
  KEY `fk_userhospital_hospital_id` (`hospital_id`),
  CONSTRAINT `fk_userhospital_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userhospital_hospital` FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`hospital_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;