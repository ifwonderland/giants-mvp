SET foreign_key_checks = 0;

drop table if exists vote.vote;

SET foreign_key_checks = 1;

CREATE TABLE `vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `position_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `player_id_FK_idx` (`player_id`),
  KEY `position_id_FK_idx` (`position_id`),
  CONSTRAINT `player_id_FK` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `position_id_FK` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



ALTER TABLE vote.vote AUTO_INCREMENT = 1;

