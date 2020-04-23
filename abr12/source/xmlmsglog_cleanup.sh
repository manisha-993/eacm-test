# Scriptname: xmlmsglog_cleanup.sh
# **********************Added by Wei Min 2011-12-06*************************************
# 		Cache.xmlmsglog Table Clean Up
# **********************How to Use*****************************************************
# There needs to be a chron job that runs weekly that removes old data. Old data is defined as rows where:
#	SENDMSGDTS + &VALIDDAYS < NOW()
#where &VALIDDAYS is found in the properties file and specifies the number of days that the data is kept in the Log. 
#There needs to be a ¡°Clear Log¡± job that removes all data. 
#This would only be used if there was a need to totally clear the Log.
# *************************************************************************************

CURRENT_REPORT="./xmlmsglog_cleanup.out"
rptdate=`date +%Y%m%d%H%M%S`
rptname='./rpt/xmlmsglog_cleanup.log.'$rptdate
if [ -f $CURRENT_REPORT ]; then
	cat $CURRENT_REPORT >$rptname
	rm $CURRENT_REPORT
fi

#get the db information from middleware.server.properties
database_url=$(awk -F "=" '$1=="ods_database_url"{print $2}' middleware.server.properties)
database=$(echo $database_url | awk -F ":" '{print $3}')
user=$(awk -F "=" '$1=="ods_database_user"{print $2}' middleware.server.properties)
password=$(awk -F "=" '$1=="ods_database_password"{print $2}' middleware.server.properties)

echo "Firstly get the VALIDDAYS if XMLMSGLOG!" >>$CURRENT_REPORT
validdays=$(awk -F "=" '$1=="XMLIDLABRSTATUS_XMLMSGLOG_VALIDDAYS"{print $2}' abr.server.properties)

if [ -n "$validdays" ]; then
	echo "validdays is $validdays "  >>$CURRENT_REPORT
	echo "connect to database"  >>$CURRENT_REPORT
	db2 connect to $database user $user using $password  >>$CURRENT_REPORT
	echo "--------------------Following records have experied more than $validdays days. Begin deleting from table xmlmsglog ----------------" >>$CURRENT_REPORT

	db2 "select SETUPENTITYTYPE, SETUPENTITYID from cache.xmlmsglog where  ( days( current timestamp) - days (SENDMSGDTS))> $validdays" |
	awk 'NR > 3 {print $1 $2}' |
	  while read SETUPENTITYTYPE  SETUPENTITYID
		do
		echo "$SETUPENTITYTYPE $SETUPENTITYID"  >>$CURRENT_REPORT
	done
	db2 "delete from cache.xmlmsglog where ( days( current timestamp) - days (SENDMSGDTS))> $validdays"
	db2 commit
	db2 terminate
	echo "-----------------------------------------------Successfully end deleting from table xmlmsglog -------------------------------------------------------" >>$CURRENT_REPORT
else
	echo "Failed to delete this time, please set XMLIDLABRSTATUS_XMLMSGLOG_VALIDDAYS in abr.server.properties.  How to use: Records will be deleted where SENDMSGDTS + &VALIDDAYS < NOW() "
fi
echo "==========================================================================================================================================" >>$CURRENT_REPORT
exit


