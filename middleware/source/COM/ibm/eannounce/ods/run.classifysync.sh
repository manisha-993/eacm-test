rptdate=`date +%Y%m%d%H%M`
. /home/opicmdb2/sqllib/db2profile

cd /home/opicmadm/sg.classify

RPTNAME='./rpt/sg.classify.sync.log'$rptdate


java -ms128M -mx768M -ss2M -oss2M -classpath :/usr/java_dev2/jre/lib/rt.jar:/home/opicmdb2/sqllib/java/sqlj.zip:/home/opicmdb2/sqllib/function:/home/opicmdb2/sqllib/java/db2java.zip:/home/opicmdb2/sqllib/java/runtime.zip:.:./mail.jar:./middleware.jar COM/ibm/eannounce/ods/SynchronizeClassifications > $RPTNAME 2>$RPTNAME.err

./classifications.permissions >>$RPTNAME 2>> $RPTNAME.err

find ./rpt -type f -mtime +7 -exec rm -f {} \;


