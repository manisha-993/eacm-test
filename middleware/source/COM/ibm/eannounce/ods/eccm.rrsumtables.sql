##
##  $Log: eccm.rrsumtables.sql,v $
##  Revision 1.4  2004/01/28 21:36:58  dave
##  more script changes
##
##  Revision 1.3  2004/01/12 19:15:31  dave
##  more reorg adds/ changes
##
##  Revision 1.2  2004/01/08 19:16:06  dave
##  more eccm scripting changes
##
##  Revision 1.1  2004/01/07 00:21:03  dave
##  more scripting
##
##
##
## SUMMARY TABLE REORG AND RUNSTATS FOR ECCM & INT MAINTENANCE
##
echo db2 reorg table eccm.attrwithunit index ECCM.ATTRWITHUNIT_IX1;
db2 reorg table eccm.attrwithunit index ECCM.ATTRWITHUNIT_IX1;
db2 runstats on table eccm.attrwithunit and detailed indexes all;

reorg table eccm.prod_product_price index eccm.prod_product_p_pk;
runstats on table eccm.prod_product_price and detailed indexes all;


##
## This needs to be a real index
##
echo db2 reorg table int.familygroupattrseq index SYSIBM.SQL031211014142140
db2 reorg table int.familygroupattrseq index SYSIBM.SQL031211014142140
db2 runstats on table int.familygroupattrseq and detailed indexes all

# ECCM.PRDROOT
echo db2 reorg table eccm.prdroot index ECCM.PRDRINDEX001;
db2 reorg table eccm.prdroot index ECCM.PRDRINDEX001;
db2 runstats on table eccm.prdroot and detailed indexes all;

# INT.AVPRDAT - just a runstats for now
db2 runstats on table INT.AVPRDAT and detailed indexes all;

# INT.ECCMATTR - just a runstats and reorg
db2 reorg table int.eccmattr index SYSIBM.SQL031211014142060;
db2 runstats on table int.eccmattr and detailed indexes all;

# 
db2 reorg table  int.timecode index sysibm.SQL031120093735160;
db2 runstats on  table  int.timecode and detailed indexes all;

#
db2 "create unique index int.currency_xchg_pk on int.currency_xchg(currencycode)";
db2 reorg table  int.currency_xchg index int.currency_xchg_pk;
db2 runstats on  table  int.currency_xchg and detailed indexes all;
