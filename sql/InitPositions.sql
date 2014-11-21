SET foreign_key_checks = 0;

drop table vote.position;

SET foreign_key_checks = 1;


CREATE TABLE `position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=latin1;

ALTER TABLE vote.position AUTO_INCREMENT = 1;

DELETE FROM vote.position where id > 0;

INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'High pitcher', 'Pitcher of high targeting') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'High pitcher'
) LIMIT 1;

INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Low pitcher', 'Pitcher of Low targeting') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Low pitcher'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Midrange pitcher', 'Pitcher of Midrange targeting') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Midrange pitcher'
) LIMIT 1;

INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'High catcher', 'Catcher of High range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'High catcher'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Low catcher', 'Catcher of Low range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Low catcher'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Midrange catcher', 'Catcher of Midrange') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Midrange catcher'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'First base', 'Base of first position') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'First base'
) LIMIT 1;

INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Second base', 'Base of Second position') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Second base'
) LIMIT 1;

INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Third base', 'Base of Third position') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Third base'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'High left field', 'Left field of high range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'High left field'
) LIMIT 1;

INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Low left field', 'Left field of Low range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Low left field'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Mid left field', 'Left field of Mid range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Mid left field'
) LIMIT 1;

INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'High center field', 'center field of High range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'High center field'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Mid center field', 'center field of Mid range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Mid center field'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Low center field', 'Low field of Mid range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Low center field'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'High right field', 'Right field of High range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'High right field'
) LIMIT 1;

INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Mid right field', 'Right field of Mid range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Mid right field'
) LIMIT 1;

INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Low right field', 'Right field of Low range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Low right field'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'High short stop', 'Short stop of high range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'High short stop'
) LIMIT 1;


INSERT INTO vote.position (name, description)
SELECT * FROM (SELECT 'Low short stop', 'Short stop of Low range') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM vote.position WHERE name = 'Low short stop'
) LIMIT 1;

