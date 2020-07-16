#!/bin/ksh

db2 connect to PDHALIAS user OPICMADM using cat9tail
db2 "delete from eacm.CollectionStatus"
db2 "insert into eacm.CollectionStatus values('CategoryCollection',0)"
db2 "insert into eacm.CollectionStatus values('CollateralCollection',0)"
db2 "insert into eacm.CollectionStatus values('ComponentGroupCollection',0)"
db2 "insert into eacm.CollectionStatus values('FeatureCollection',0)"
db2 "insert into eacm.CollectionStatus values('WorldWideProductCollection',0)"
db2 "insert into eacm.CollectionStatus values('ProductCollection',0)"
db2 "insert into eacm.CollectionStatus values('WWProdCompatCollection',0)"
db2 "insert into eacm.CollectionStatus values('ProdStructCollections',0)"
db2 commit
  
db2 terminate
