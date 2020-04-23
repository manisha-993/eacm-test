# Scriptname: xmlmsglog_cleanall.sh
# **********************Added by Wei Min 2011-12-06*************************************
# 		Cache.xmlmsglog Table Clean all records
# **********************How to Use*****************************************************
#There needs to be a ¡°Clear Log¡± job that removes all data. 
#This would only be used if there was a need to totally clear the Log.
# *************************************************************************************

CURRENT_REPORT="./xmlmsglog_cleanall.out"
rptdate=`date +%Y%m%d%H%M%S`
rptname='./rpt/xmlmsglog_cleanall.log.'$rptdate
if [ -f $CURRENT_REPORT ]; then
	cat $CURRENT_REPORT >$rptname
	rm $CURRENT_REPORT
fi

#get the db information from middleware.server.properties
database_url=$(awk -F "=" '$1=="ods_database_url"{print $2}' middleware.server.properties)
database=$(echo $database_url | awk -F ":" '{print $3}')
user=$(awk -F "=" '$1=="ods_database_user"{print $2}' middleware.server.properties)
password=$(awk -F "=" '$1=="ods_database_password"{print $2}' middleware.server.properties)

echo "connect to database"  >>$CURRENT_REPORT
db2 connect to $database user $user using $password  >>$CURRENT_REPORT
echo "--------------------All records should be cleared in the cache.xmlmsglog table. Begin deleting from table xmlmsglog ----------------" >>$CURRENT_REPORT
db2 "delete from cache.xmlmsglog "
db2 commit
db2 terminate
echo "-------------------------------Successfully end deleting all records from table xmlidlcache ------------------------------------" >>$CURRENT_REPORT

