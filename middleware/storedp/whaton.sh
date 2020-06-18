#!/bin/ksh

lsvg |
while read VG
  do
  lsvg -l $VG
  done

#
# end of script
#
