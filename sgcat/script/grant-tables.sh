#!/bin/ksh

db2 connect to PDHALIAS user OPICMADM using cat9tail

db2 "select tableschema, tablename from gbli.catdb_tables where GRANT_ACCESS = 'Y' \
order by tableschema, tablename" |
awk 'NR > 3 && NF == 2 {print $1"."$2}' |
while read TABNAME
  do
  
  echo "GRANT SELECT ON $TABNAME TO EACMFEED"
  db2 "GRANT SELECT ON $TABNAME TO EACMFEED"
  
  echo "GRANT SELECT ON $TABNAME TO ZAHIDA"
  db2 "GRANT SELECT ON $TABNAME TO ZAHIDA"
  
  done
  
db2 terminate
