#!/usr/bin/ksh
################################################################################################
# Print script usage information                                                               #
################################################################################################
printUsage()
{

	echo
        echo " Usage        : $0 -i inifile"
        echo "   inifile      : QSM FTP configure information, "
	echo "		           If it is not specified, then search the current directory for qsm.ini"
}
################################################################################################
# Write log                                                                                    #
################################################################################################
logMessages()
{
        timestamp=`date "+%Y-%m-%d-%H.%M.%S"`
        echo "$timestamp : $1 " >> $logfile
}
################################################################################################
# Check whether process is running or not                                                       #
################################################################################################
checkIfCanRun(){
        tmp=`ps -ef | grep qsm_ftp | grep -v 'grep' | wc -l | tr -d " "`
        if [ $tmp -gt 1 ];then 
                logMessages "QSM-FTP job is running now, can not run at this time."
                logMessages "Terminating process"
                exit 100
        fi
}
################################################################################################
# Upload file to QSM server                                                                    #
################################################################################################
uploadFile(){
lfname="$1"
rfname="'QSM.FTP.MJ00946.PART.INP(+1)'"
ftp -i -n <<!EOF
open $ftp_host
user $ftp_user $ftp_passwd
put $lfname $rfname
quit
!EOF
}

################################################################################################
#  MAIN                                                                                        #
################################################################################################
inifile="qsm.ini"
filedir="undef"
ftp_host="undef"
ftp_user="undef"
ftp_passwd="undef"
backupdir="undef"
logdir="undef"
limit_count=255
days=10

timestamp=`date "+%Y%m%d%H%M%S"`
logfile=""

################################################################################################
echo "setting parameters ..."
################################################################################################
while getopts "i:f" OPTION  2>/dev/null
  do
   case "$OPTION" in
       i)  inifile=$OPTARG;;
   esac
  done
if [ -f $inifile ];then
     ftp_host=`cat $inifile | grep "ftp_host=" | awk -F\= '{print $2}'`
     ftp_user=`cat $inifile | grep "ftp_user=" | awk -F\= '{print $2}'`
     ftp_passwd=`cat $inifile | grep "ftp_passwd=" | awk -F\= '{print $2}'`
     logdir=`cat $inifile | grep "logdir=" | awk -F\= '{print $2}'`
     limit_count=`cat $inifile | grep "limit_count=" | awk -F\= '{print $2}'`
     backupdir=`cat $inifile | grep "backupdir=" | awk -F\= '{print $2}'`
     filedir=`cat $inifile | grep "filedir=" | awk -F\= '{print $2}'`
     days=`cat $inifile | grep "days=" | awk -F\= '{print $2}'`
  else
     printUsage
     exit 1
fi

################################################################################################
echo "checking parameters ..."
################################################################################################
if [[ -z "$logdir" || $logdir = "undef" ]];then
     echo "No logdir is specified in config file, will use current directory instead."
     logfile=qsm_ftp_${timestamp}.log
     logdir=.
else
     logfile=${logdir}/qsm_ftp_${timestamp}.log
fi

if [ ! -x "$logdir" ]; then
     echo "logdir=$logdir dosen't exist, process will create logdir."
     mkdir -p $logdir

     if [ $? -ne 0 ];then
          echo "can not create logdir, process will exit."
          exit 1
     fi
fi

if [[ -z "$ftp_host" ||  $ftp_host = "undef" ]];then
     logMessages "No ftp_host is specified in config file, process will exit."
     exit 1
fi

if [[ -z "$ftp_user" ||  $ftp_user = "undef" ]];then
     logMessages "No ftp_user is specified in config file, process will exit."
     exit 1
fi

if [[ -z "$ftp_passwd" ||  $ftp_passwd = "undef" ]];then
     logMessages "No ftp_passwd is specified in config file, process will exit."
     exit 1
fi

if [[ -z "$filedir" || $filedir = "undef" ]];then
     logMessages "No filedir is specified in config file, process will exit."
     exit 1
fi

if [[ -z "$backupdir" || $backupdir = "undef" ]];then
     logMessages "No backupdir is specified in config file, process will exit."
     exit 1
fi

if [ -z "$limit_count" ];then
     logMessages "No limit_count is specified in config file, 255 is as default value."
     limit_count=255
fi

if [ -z "$days" ];then
     logMessages "No days is specified in config file, 10 is as default value."
     days=10
fi

if [ $backupdir = $filedir ];then
     logMessages "backupdir can not be the same directory as filedir."
     exit 1
fi

if [ ! -x "$filedir" ]; then
     logMessages "filedir=$filedir dosen't exist, process will exit."
     exit 1
fi

if [ ! -x "$backupdir" ]; then
     logMessages "backupdir=$backupdir dosen't exist, process will create backupdir."
     mkdir -p $backupdir

     if [ $? -ne 0 ];then
          logMessages "can not create backupdir, process will exit."
          exit 1
     fi
fi

################################################################################################
echo "checking whether process is running or not"
################################################################################################
logMessages "Check if $0 has started already ..."
checkIfCanRun
logMessages "It isn't started yet, we can run it ..."

logMessages "Parameters: filedir=$filedir backupdir=$backupdir ftp_host=$ftp_host limit_count=$limit_count"

################################################################################################
echo "uploading files to QSM server"
################################################################################################
count=0

tmp=`ls $filedir | grep '^EACM_TO_QSM_' | wc -l | tr -d " "`
if [ $tmp -eq 0 ];then 
  logMessages "No reprot file is found in $filedir, process will exit." 
  exit 0;
fi

logMessages "Start to upload files to QSM server, there are $tmp files need to be uploaded in $filedir"
for file in `ls -lrt $filedir | grep "EACM_TO_QSM" | awk -F ' ' '{print $9}'`
     do
     if [ $count -lt $limit_count ];then
          logMessages "uploading $filedir/$file to QSM"
          cd $filedir
          uploadFile $file
          if [ $? -ne 0 ];then
               logMessages "Failed to upload file $file"
          else
               mv $file $backupdir
               count=`expr $count + 1`
               tmp=`expr $tmp - 1`
          fi
     else
          logMessages "Max file limit is reached, limit_count = $limit_count, current count = $count, remaining $tmp"
          break
     fi
     done
logMessages "Done! $count files has been sent to QSM"

#########################################################################
echo "deleting old data files ......"
#########################################################################
logMessages "Removing old data files"
days=`expr $days + 1`
days=`expr 24 \* $days`
templatefile=`TZ=aaa$days date "+EACM_TO_QSM_%Y-%m-%d-00.00.00.000000.txt"`

for file in `ls $backupdir | grep "EACM_TO_QSM"`
     do
     if [ $templatefile \> $file ];then
          logMessages "deleting $file"
          rm $backupdir/$file
     fi
     done
exit 0
