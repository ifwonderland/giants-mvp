GRANT USAGE ON *.* TO 'vote'@'localhost';
DROP USER 'vote'@'localhost';

CREATE USER 'vote'@'localhost' IDENTIFIED BY 'vote';

DROP DATABASE IF EXISTS vote;

CREATE DATABASE `vote` /*!40100 DEFAULT CHARACTER SET utf8 */;

GRANT ALL ON vote.* TO 'vote'@'localhost';


