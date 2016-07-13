#!/bin/sh

#
# This batch file runs the middleware compile script
#

USER=`whoami`

cd /home/$USER/abrgenerator/oims

RPTDATE=`date +%Y%m%d%H%M%S`
RPTNAME="/home/$USER/abrgenerator/oims/rpt/compileoimsimpchw.log."$RPTDATE
LOCK_FILE="/home/$USER/abrgenerator/oims/locks/lock"

trap "echo 'Removing lock file ...'; rm $LOCK_FILE; exit 1" 2

if [ -f $LOCK_FILE ]; then
  cat $LOCK_FILE >>$RPTNAME
  cat $LOCK_FILE
  exit 0
fi

echo "Sorry, you are blocked.  The compile is running ..." >$LOCK_FILE

date>>$RPTNAME

PATH=/usr/bin:/etc:/usr/sbin:/usr/ucb:$HOME/bin:/usr/bin/X11:/sbin:.
export PATH=/usr/java71/bin:$PATH
cd /home/$USER/abrgenerator/oims/workspace/OIMSimpCHWPrototype
rm -rf abrsrc
rm OIMSimpCHW.jar
find . -name "*class" -print -exec rm {} \; 
rm files.tmp
find . -name "com.ibm*java" -print|sort >>files.tmp



cd /home/$USER/abrgenerator/oims

export CLASSPATH=".:/home/opicmdb2/sqllib/java/db2java.zip:/usr/WebSphere/AppServer/lib/xalan.jar:/usr/WebSphere/AppServer/lib/xerces.jar:/usr/mqm/java/lib/com.ibm.mq.jar:/usr/mqm/java/lib/com.ibm.mq.jmqi.jar:/usr/mqm/java/lib/connector.jar:/usr/mqm/java/lib/base:/home/opicmadm/taskmaster.sg/poi-3.0.1-FINAL-20070705.jar:/home/opicmadm/abrgenerator/script/xercesImpl.jar:/home/opicmadm/abrgenerator/script/xml-apis.jar:/home/opicmadm/abrgenerator/script/dom4j-1.6.1.jar:../lib/cwa.jar:../lib/ibmjndi.jar:../lib/jndi.jar:../lib/jcert.jar:../lib/jnet.jar:../lib/jsse.jar:../lib/OIMJavaClient.jar"

echo compile begins
echo $CLASSPATH
#javac -deprecation @files.tmp 2>>$RPTNAME

javac -J-Xmx512m -deprecation @files.tmp 2>>$RPTNAME
#echo javadoc begins
#javadoc -d doc -version -author COM.ibm.eannounce.abr.ls COM.ibm.eannounce.abr.pcd COM.ibm.eannounce.abr.psg COM.ibm.eannounce.abr.rfa COM.ibm.eannounce.abr.sg COM.ibm.eannounce.abr.util 1>>$RPTNAME 2>>$RPTNAME 
jar cvf OIMSimpCHW.jar ./*.class >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.pprds.epimshw/*.class >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.pprds.epimshw/*.pr* >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.pprds.epimshw.revenuel/*.class >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.pprds.epimshw.revenue/*.pr* >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.pprds.epimshw.util/*.class >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.pprds.epimshw.util/*.pr* >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.rdh.chw.entity/*.class >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.rdh.chw.entity/*.pr* >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.rdh.chw.caller/*.class >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.rdh.chw.caller/*.pr* >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.rdh.rfc.proxy/*.class >>$RPTNAME
jar uvf OIMSimpCHW.jar com.ibm.rdh.rfc.proxy/*.pr* >>$RPTNAME



date>>$RPTNAME

#
# delete reports more than 7 days old
#
find /home/$USER/abrgenerator/oims/rpt -name "compile*" -mtime +7 -exec rm -Rf {} \;

if [ -f $LOCK_FILE ]; then
  rm $LOCK_FILE
fi

#display the output file
more $RPTNAME

#
# end of script
#
clear
