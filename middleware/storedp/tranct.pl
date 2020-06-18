#!/usr/bin/perl -w 

#
# Report the logical transactions [SP calls] recorded
#   in the middleware log files
#

$numArgs = $#ARGV + 1;
if ($numArgs < 1) {
  printf "You must specify a directory!\n\nUsage: ./tranct.pl ~/mw.ui\n";
  exit;
}

$dir="$ARGV[0]";
print "examining log files in $dir\n";

printf "output SP calls from compressed log files ...\n";
system("cat -q $dir/rpt/*Z|uncompress 2>/dev/null|grep timing|cut -c1-10,27,53-60|cut -c1-18|grep GBL|sort>trans");

printf "output SP calls from uncompressed log files ...\n";
system("cat -q $dir/rpt/*[!Z]|grep timing|cut -c1-10,27,53-60|cut -c1-18|grep GBL|sort>>trans");

printf "output getBlob transactions from compressed log files ...\n";
system("cat -q $dir/rpt/*Z|uncompress 2>/dev/null|grep \"blob of size\"|cut -c1-10,27,46-53|cut -c1-18|grep Blob|sort|tr '[a-z]' '[A-Z]'>>trans");

printf "output getBlob transactions from uncompressed log files ...\n";
system("cat -q $dir/rpt/*[!Z]|grep \"blob of size\"|cut -c1-10,27,46-53|cut -c1-18|grep Blob|sort|tr '[a-z]' '[A-Z]'>>trans");

printf "output SP calls from CURRENT MW log file ...\n";
system("cat -q $dir/middleware.out|grep timing|cut -c1-10,27,53-60|cut -c1-18|grep GBL|sort>>trans");

printf "output getBlob transactions from CURRENT MW log file ...\n";
system("cat -q $dir/middleware.out|grep \"blob of size\"|cut -c1-10,27,46-53|cut -c1-18|grep Blob|sort|tr '[a-z]' '[A-Z]'>>trans");

printf "output SP calls from CURRENT TM log file ...\n";
system("cat -q $dir/taskmaster.out|grep timing|cut -c1-10,27,53-60|cut -c1-18|grep GBL|sort>>trans");

printf "output getBlob transactions from CURRENT TM log file ...\n";
system("cat -q $dir/taskmaster.out|grep \"blob of size\"|cut -c1-10,27,46-53|cut -c1-18|grep Blob|sort|tr '[a-z]' '[A-Z]'>>trans");

printf "generating total lines ...\n";
system("cat -q trans|sort|cut -c1-11|uniq|sed \"s/ / TOTAL/\">dates");

# the trailing pipe "|" directs command output into this program
if (! open (DUPIPE,"cat -q trans dates|sort|uniq -c|"))  {
  die "Can't run cat -q! $!\n";
}

$total = 0;
$lines = 0;
printf "%-10s %-7s %8s\n", 'Date', 'Tran', 'Count';
while (<DUPIPE>) {
  # parse the grep info
  ($count, $date, $tran) = split;
  if ($tran eq "TOTAL") {
    printf "%-10s %-7s %8s\n", $date, $tran, $total;
    $total = 0;
  } else {
    printf "%-10s %-7s %8s\n", $date, $tran, $count;
    $total = $total + $count;
    ++$lines;
  }
}

if ($lines == 0) {
  printf "no output detected ... check the trace level value for middleware\n";
}

system("rm trans dates 2>/dev/null");
