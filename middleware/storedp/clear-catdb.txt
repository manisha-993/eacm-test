#!/bin/ksh

db2 connect to PDHALIAS user OPICMADM using cat9tail

db2 "select tableschema, tablename from gbli.catdb_tables where idl_purge = 'Y' \
order by tableschema, tablename" |
awk 'NR > 3 && NF == 2 {print $1"."$2}' |
while read TABNAME
  do
  echo "Purging table $TABNAME..."
  db2 "import from /dev/null of del replace into $TABNAME"
  done
  
db2 terminate
