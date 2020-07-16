. /home/opicmadm/.profile > /dev/null

#this check eacm.ifmlock first and determins if CATPOP is active
#if active do nothing else excute dmnet/catnet
db2 "connect to pdhalias user opicmadm using cat9tail"

db2 "select status from eacm.ifmlock where PROCESS_NAME = 'CATPOP'" |
awk 'NR > 3 && NF == 1 {print $1}' |
 while read status
   do
if [ "$status" -eq 0 ]
  then
        echo "Starting CATPOP"
        #!/bin/bash
        cd /home/opicmadm/XccCatdb/catods
        run.dmnet
        cd /home/opicmadm/XccCatdb
	runAllCollections_com.sh
        # Delete files older than 14 days old.
        /home/opicmadm/maint/clear.directory.sh ./rpt 14

  else
        echo "CATPOP is currently running"
fi
done
db2 terminate

