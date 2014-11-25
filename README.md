Part 1. Setup 

The following gives instructions on how to setup to run the program 

1. Database setup 
After installing MySQL database server, change db.properties under /db folder, with correct root username and password, and leave the rest as default.

Open a console, and run 
sh db/createDB.sh 

this script will create user vote and schema vote with proper tables (and prepopulated data to start with), and it will grant proper privileges to the user vote. 

2. Run or Debug

2.1 Change logging level 
You can change log at src/main/config/log4j.properties if you want to enable more detailed logging

2.2 Run/Debug
To run the problem, you can simple run junit test cases in VoteTest.java, this will kick off the voting process. 

To see more detailed information of how clients and servers produce and consume votes, please enabled DEBUG level logging in log4j properties. 

To see ranked by player or ranked by positions result, simple run VoteDaoImplTest.java

To see more detailed information of the rank results, enable log4j DEBUG level logging. 


2.3 Advanced and throughput measurement
You can also change the values in Constants.java to tweak the number to see how the system scale. 







Part 2. Design 

1. Database schema design 

Three tables are created (during the setup process) as such:

1.1 Position table 

CREATE TABLE `position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

This table contains main information for position, it has no foreign key, and indexed on id only as this is the only joined column


1.2 Player table

CREATE TABLE `player` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `position_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `positionFK_idx` (`position_id`),
  CONSTRAINT `positionFK` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;


This table contains information for Players, including a time varying column position_id, which references the id column in position table, if need to retrieve info from position, indexed on id and position id

1.3 Vote table

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
) ENGINE=InnoDB AUTO_INCREMENT=23388 DEFAULT CHARSET=utf8;


Vote table contains historical vote results.

This table is referencing both playerId and positionId, for quick ranking queries. 

Notice positionId is a duplicate column as in player table, this little denormalization is for performance consideration, and also because positionId of player table can be changing during the vote process. So consider positionId here as historical values, while the positionId in Player table as current value. 



2. Voting process design 

2.1 Client and Server

A typical producer-consumer problem, where voting client (producer) is producing votes at a much higher rate than server (consumer) is able to produce this. To solve this problem, I used java blocking queue, which blocks in two scenarios: blocks if producer tries to put to queue that is at capacitiy, and blocks if consumer is  trying to consume when queue is empty. 

2.2 Caching 

With the assumption that vote client only waits to see the current top N players/positions (per program run, or per current session), caches are designed such that all created votes (of current session) is in one cache and the voting results are in another cache. Client keeps pinging result cache to get corresponding results. 

Java ConcurrentHashMap is used for non-blocking thread-safe caching retrieval and update. 

Singleton design pattern is implemented with class registery (factory) to ganrantee caches are singleton per session. 

2.3 Transaction management

Spring Transaction and DAO support is used to manage transaction, the key here is that when insert data to Vote table, this insertion should be visible cross all parties needed such information. 



Part 3. Performance measurements


Below are some of the throughput numbers from local running 

num of votes: 100
num of servers: 2
total processing time: 475 miliseconds (0.475 seconds)

num of votes: 1000
num of servers: 2
total processing time: 4194 miliseonds (4.2 seconds)

num of votes: 10000
num of servers: 2
total processing time: 48284 miliseconds (48 seconds)


num of votes: 10000
num of servers: 20
total processing time: 32625 miliseconds (33 seconds)

num of votes: 10000
num of servers: 200
total processing time: 35384 miliseconds (35 seconds)

num of votes: 10000
num of servers: 2000
total processing time: 33654 miliseconds (33 seconds)


Observations: 

-input size, num of votes, and time to process is scaling roughly on linearly, thanks to the almost linear proximy complexity of ConcurrentHashMap

-increasing number of server threads improves performance only to a point, this is mainly because of the blocking queue bottleneck.




Part 4. Further improvements 

To help enhance performance even more, we can also add more blocking queues so that load can be balanced across queues. 



Part 5. Some answers and commments to questions in requirements

 "Every vote should count once. You should not lose a vote unless there is a crash."

 -no vote will be lost as each of them is persisted to MySQL DB once created. And they are counted only once per session and if ran by rank query, they are also only inserted once due to the nature of transaction management.

 "Measure the throughput and response time of the votes. Explain how your design (server and database) affects performance in positive and negative ways."

 -throughput and response data above, with some observations. The design uses caching (ConcurrentHashMap) to optimize communication between client and server (with no communication between servers. ) so performance is optimized in that sense. But for simplicity, only one blocking queue is used, therefore load is heavily concentrated in this one queue, but if we can add more queues, performance can be enhanced even more. 

  " After the program completes, you will run the following 2 queries on the resulting database:
 Q1: vote count by player
 Q2: vote count by position

Explain how your schema design affects the result of Q2 if a play is allowed to change position during the voting process. Specifically, what is the most suitable schema design if the business analysis assumes the fans mostly vote for positions instead for specific players, and vice versa? "


-the queries running test is listed in instructions above, the schema designed has both player id and position id in it, so query running should be about same response time .

-the implemention/design in the current project is assuming player does not change position, so before running the program, it retrieves all players into memory, if this is violated and if we need to rank by position, then we need to query player table every time a new vote is inserted in vote table

-the design would change mostly on what key to hash/indexed based on whether or not fans like to vote for player versus position












