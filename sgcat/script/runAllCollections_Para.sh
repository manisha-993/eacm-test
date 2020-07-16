

#!/bin/bash
# Scriptname: runAllCollection.sh

# **********************Created by Yujiang He 2007-08-02*******************************
# This script will start user specified number of processes to do all collections
# Please adjust this "procnum" to a value suitable for your hardware platform
# *************************************************************************************

# **********************How to Use*****************************************************
# runAllCollection.sh -p -r SYNC [-s state-date -e end-date]
# or just specify these options when you run runCatalogRunner.sh
# Example set procnum=2 ,then we'll set up two processes to run Collections in parallel.
# of course you can set procnum=3 or 4..
#
# *************************************************************************************

# **********************Added by GuoBin 2007-11-16*************************************
# Firstly run temp talbe clear up shell script
# Secondly connect database get current time as parameter which will be passed into CatelogRunner.
# Get database_url,user,password from middleware.server.properties
# Thirdly, if last time the processes were killed by operator,then the files runCategoryCollection
#           or stopCategoryCollection ...which indicate the current running collections,maybe still
#           at there.Thoes files should be deleted. 
# *************************************************************************************

# Firstly run temp talbe clear up shell script
echo "Firstly run temp talbe clear up shell script!"
run.tmptable.cleanup_Para.sh

# Secondly connect to the database
database_url=$(awk -F "=" '$1=="pdh_database_url"{print $2}' middleware.server.properties)
database=$(echo $database_url | awk -F ":" '{print $3}')
user=$(awk -F "=" '$1=="pdh_database_user"{print $2}' middleware.server.properties)
password=$(awk -F "=" '$1=="pdh_database_password"{print $2}' middleware.server.properties)
db2 connect to $database user $user using $password

# Get current timestamp from database
db2 "values current timestamp" |
awk 'NR > 3 && NF == 1 {print $1}' |
  while read time
    do
    echo "Current time is $time"
    currenttime=$time
  done
db2 "select COLLECTIONNAME from eacm.collectionstatus" |
awk 'NR > 3 && NF == 1 {print $1}' |
  while read collectionname
    do
    rm -f run$collectionname
    rm -f stop$collectionname
  done
db2 terminate

# Number of the processes to be run
# Here you can set the number of processes to run Collections in parallel.
procnum=2

echo "\nYou have set process number to : $procnum."

while [ procnum -gt 0 ]
do
	procnum=`expr $procnum \- 1`

# !!!
# You can also change the options here to override what's used outside
	nohup runCatalogRunner_Para.sh -p -n $procnum -t $currenttime &
	echo  "\nProcess $procnum has been started!"

done
echo "\nAll needed processes have been started."
