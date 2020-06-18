#!/bin/ksh

TOTAL=0
SIZE=0

echo "vg     free space"

lsvg |
while read VG
  do
    SIZE=`lsvg $VG|grep FREE|cut -d"(" -f2|cut -d")" -f1|sed -e 's/  / /g'|sed -e 's/ megabytes//'`
    echo "$VG $SIZE MB"
    TOTAL=`expr $TOTAL + $SIZE`
  done

echo "TOTAL" $TOTAL "MB"

#
# end of script
#
