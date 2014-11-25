#!/bin/bash
. db.properties
mysql --user=${root_user} --password=${root_password} < ./sql/initUserSchema.sql
mysql ${dbname} --user=${user} --password=${password} < ./sql/initPositions.sql
mysql ${dbname} --user=${user} --password=${password} < ./sql/initPlayers.sql
mysql ${dbname} --user=${user} --password=${password} < ./sql/initVotes.sql
