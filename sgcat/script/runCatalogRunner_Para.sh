
CATDIR="/home/opicmadm/YUYA/chinacatdb"
PRJDIR="/home/opicmadm/YUYA/chinacatdb"


SQLLIB=/home/opicmdb2/sqllib
JAVA=$SQLLIB/java

LD_LIBRARY_PATH=/home/opicmdb2/sqllib/lib
export LD_LIBRARY_PATH
LIBPATH=/usr/lib:/lib:/home/opicmdb2/sqllib/lib
export LIBPATH
PATH=/usr/java14/bin:/usr/lpp/internet/server_root/java/bin:/usr/bin:/etc:/usr/sbin:/usr/ucb:/home/opicmadm/bin:/usr/bin/X11:/sbin:.:/home/opicmdb2/sqllib/bin:/home/opicmdb2/sqllib/adm:/home/opicmdb2/sqllib/misc
export PATH
DB2CODEPAGE=1208
export DB2CODEPAGE
DB2DIR=/usr/lpp/db2_07_01
export DB2DIR
DB2INSTANCE=opicmdb2
export DB2INSTANCE

CLASSPATH="/usr/java14/jre/lib/rt.jar:/home/opicmdb2/sqllib/java/sqlj.zip:/home/opicmdb2/sqllib/function:/home/opicmdb2/sqllib/java/db2java.zip:/home/opicmdb2/sqllib/java/runtime.zip:$CATDIR/middleware.jar:$CATDIR/mail.jar:$PRJDIR/sgcat.jar"
#JAVA_OPTIONS="-Djava.library.path=$JAVA_LIB_PATH -Xmx1000M"
JAVA_OPTIONS="-Xmx1000M"

java $JAVA_OPTIONS -classpath $CLASSPATH COM.ibm.eannounce.catalog.CatalogRunner -r SYNC $*

#java $JAVA_OPTIONS -classpath $CLASSPATH COM.ibm.eannounce.catalog.CatalogRunner -r SYNC -s "2007-11-15-01.00.00.000000" -e "2007-11-15-01.59.59.999999" $*
