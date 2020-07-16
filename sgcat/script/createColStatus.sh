#!/bin/ksh
db2 connect to PDHALIAS user OPICMADM using cat9tail
db2 "CREATE TABLE eacm.CollectionStatus(CollectionName VARCHAR(32) NOT NULL, Status INTEGER NOT NULL)" 
db2 terminate
