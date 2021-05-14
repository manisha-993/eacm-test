#
# Script called from the Java IDL program on every x passes of the main IDL loop
#
db2 connect to opicmpdh user opicmadm using mice8chs

# Category - VECATNAV1
echo Clear up temp tables for  Category
db2 "import from /dev/null of del replace into CATCOL.trsNetterPass1"
db2 "import from /dev/null of del replace into CATCOL.trsNetterPass2"
db2 "import from /dev/null of del replace into CATCOL.trsNetterFinal"
db2 "import from /dev/null of del replace into CATCOL.trsNetterAtts"
db2 "insert into CATCOL.trsnetteratts select distinct 0,entityid, entitytype, enterprise from opicm.entity where entityid < 10"

db2 "reorg table CATCOL.trsNetterPass1"
db2 "reorg table CATCOL.trsNetterPass2"
db2 "reorg table CATCOL.trsNetterFinal"
db2 "reorg table CATCOL.trsNetterAtts"

db2 "runstats on table CATCOL.trsNetterPass1 and detailed indexes all"
db2 "runstats on table CATCOL.trsNetterPass2 and detailed indexes all"
db2 "runstats on table CATCOL.trsNetterFinal and detailed indexes all"
db2 "runstats on table CATCOL.trsNetterAtts and detailed indexes all"

db2 "rebind opicmadm.gbl8181A"
db2 "rebind opicmadm.gbl8184A"
db2 "rebind opicmadm.gbl9012A"

# Collateral - VEFB1
#echo Clear up temp tables for Collateral
db2 "import from /dev/null of del replace into COLLCOL.trsNetterPass1"
db2 "import from /dev/null of del replace into COLLCOL.trsNetterPass2"
db2 "import from /dev/null of del replace into COLLCOL.trsNetterFinal"
db2 "import from /dev/null of del replace into COLLCOL.trsNetterAtts"
db2 "insert into COLLCOL.trsnetteratts select distinct 0,entityid, entitytype, enterprise from opicm.entity where entityid < 10"

db2 "reorg table COLLCOL.trsNetterPass1"
db2 "reorg table COLLCOL.trsNetterPass2"
db2 "reorg table COLLCOL.trsNetterFinal"
db2 "reorg table COLLCOL.trsNetterAtts"

db2 "runstats on table COLLCOL.trsNetterPass1 and detailed indexes all"
db2 "runstats on table COLLCOL.trsNetterPass2 and detailed indexes all"
db2 "runstats on table COLLCOL.trsNetterFinal and detailed indexes all"
db2 "runstats on table COLLCOL.trsNetterAtts and detailed indexes all"

db2 "rebind opicmadm.gbl8181B"
db2 "rebind opicmadm.gbl8184B"
db2 "rebind opicmadm.gbl9012B"

# ComponentGroupCollection -
#echo Clear up temp tables for  ComponentGroupCollection
db2 "import from /dev/null of del replace into COMPCOL.trsNetterPass1"
db2 "import from /dev/null of del replace into COMPCOL.trsNetterPass2"
db2 "import from /dev/null of del replace into COMPCOL.trsNetterFinal"
db2 "import from /dev/null of del replace into COMPCOL.trsNetterAtts"
db2 "insert into COMPCOL.trsnetteratts select distinct 0,entityid, entitytype, enterprise from opicm.entity where entityid < 10"

db2 "reorg table COMPCOL.trsNetterPass1"
db2 "reorg table COMPCOL.trsNetterPass2"
db2 "reorg table COMPCOL.trsNetterFinal"
db2 "reorg table COMPCOL.trsNetterAtts"

db2 "runstats on table COMPCOL.trsNetterPass1 and detailed indexes all"
db2 "runstats on table COMPCOL.trsNetterPass2 and detailed indexes all"
db2 "runstats on table COMPCOL.trsNetterFinal and detailed indexes all"
db2 "runstats on table COMPCOL.trsNetterAtts and detailed indexes all"

db2 "rebind opicmadm.gbl8181C"
db2 "rebind opicmadm.gbl8184C"
db2 "rebind opicmadm.gbl9012C"

# Feature - VEMM1
#echo Clear up temp tables for Feature 
db2 "import from /dev/null of del replace into FEATCOL.trsNetterPass1"
db2 "import from /dev/null of del replace into FEATCOL.trsNetterPass2"
db2 "import from /dev/null of del replace into FEATCOL.trsNetterFinal"
db2 "import from /dev/null of del replace into FEATCOL.trsNetterAtts"
db2 "insert into FEATCOL.trsnetteratts select distinct 0,entityid, entitytype, enterprise from opicm.entity where entityid < 10"

db2 "reorg table FEATCOL.trsNetterPass1"
db2 "reorg table FEATCOL.trsNetterPass2"
db2 "reorg table FEATCOL.trsNetterFinal"
db2 "reorg table FEATCOL.trsNetterAtts"

db2 "runstats on table FEATCOL.trsNetterPass1 and detailed indexes all"
db2 "runstats on table FEATCOL.trsNetterPass2 and detailed indexes all"
db2 "runstats on table FEATCOL.trsNetterFinal and detailed indexes all"
db2 "runstats on table FEATCOL.trsNetterAtts and detailed indexes all"

db2 "rebind opicmadm.gbl8181D"
db2 "rebind opicmadm.gbl8184D"
db2 "rebind opicmadm.gbl9012D"

# ProductCollection- VEWWCOMP1
#echo Clear up temp tables for ProductCollection
db2 "import from /dev/null of del replace into PRODCOL.trsNetterPass1"
db2 "import from /dev/null of del replace into PRODCOL.trsNetterPass2"
db2 "import from /dev/null of del replace into PRODCOL.trsNetterFinal"
db2 "import from /dev/null of del replace into PRODCOL.trsNetterAtts"
db2 "insert into PRODCOL.trsnetteratts select distinct 0,entityid, entitytype, enterprise from opicm.entity where entityid < 10"

db2 "reorg table PRODCOL.trsNetterPass1"
db2 "reorg table PRODCOL.trsNetterPass2"
db2 "reorg table PRODCOL.trsNetterFinal"
db2 "reorg table PRODCOL.trsNetterAtts"

db2 "runstats on table PRODCOL.trsNetterPass1 and detailed indexes all"
db2 "runstats on table PRODCOL.trsNetterPass2 and detailed indexes all"
db2 "runstats on table PRODCOL.trsNetterFinal and detailed indexes all"
db2 "runstats on table PRODCOL.trsNetterAtts and detailed indexes all"

db2 "rebind opicmadm.gbl8181E"
db2 "rebind opicmadm.gbl8184E"
db2 "rebind opicmadm.gbl9012E"

# WorldWideProductCollectin - EXTCOMPONENT01
#echo Clear up temp tables for WorldWideProductCollectin 
db2 "import from /dev/null of del replace into WWPRODCOL.trsNetterPass1"
db2 "import from /dev/null of del replace into WWPRODCOL.trsNetterPass2"
db2 "import from /dev/null of del replace into WWPRODCOL.trsNetterFinal"
db2 "import from /dev/null of del replace into WWPRODCOL.trsNetterAtts"
db2 "insert into WWPRODCOL.trsnetteratts select distinct 0,entityid, entitytype, enterprise from opicm.entity where entityid < 10"

db2 "reorg table WWPRODCOL.trsNetterPass1"
db2 "reorg table WWPRODCOL.trsNetterPass2"
db2 "reorg table WWPRODCOL.trsNetterFinal"
db2 "reorg table WWPRODCOL.trsNetterAtts"

db2 "runstats on table WWPRODCOL.trsNetterPass1 and detailed indexes all"
db2 "runstats on table WWPRODCOL.trsNetterPass2 and detailed indexes all"
db2 "runstats on table WWPRODCOL.trsNetterFinal and detailed indexes all"
db2 "runstats on table WWPRODCOL.trsNetterAtts and detailed indexes all"

db2 "rebind opicmadm.gbl8181F"
db2 "rebind opicmadm.gbl8184W"
db2 "rebind opicmadm.gbl9012F"

# WWProdCompatCollection - EXTCOMPONENT02
echo Not clearing temp tables for WWProdCompatCollection 

# ProductStructCollection- EXTCOMPONENT02
echo Clear up temp tables for  ProductStructCollection
db2 "import from /dev/null of del replace into PSCOL.trsNetterPass1"
db2 "import from /dev/null of del replace into PSCOL.trsNetterPass2"
db2 "import from /dev/null of del replace into PSCOL.trsNetterFinal"
db2 "import from /dev/null of del replace into PSCOL.trsNetterAtts"
db2 "insert into PSCOL.trsnetteratts select distinct 0,entityid, entitytype, enterprise from opicm.entity where entityid < 10"

db2 "reorg table PSCOL.trsNetterPass1"
db2 "reorg table PSCOL.trsNetterPass2"
db2 "reorg table PSCOL.trsNetterFinal"
db2 "reorg table PSCOL.trsNetterAtts"

db2 "runstats on table PSCOL.trsNetterPass1 and detailed indexes all"
db2 "runstats on table PSCOL.trsNetterPass2 and detailed indexes all"
db2 "runstats on table PSCOL.trsNetterFinal and detailed indexes all"
db2 "runstats on table PSCOL.trsNetterAtts and detailed indexes all"

db2 "rebind opicmadm.gbl8181H"
db2 "rebind opicmadm.gbl8184H"
db2 "rebind opicmadm.gbl9012H"

# WorldWideProductStrctCollectio- EXTCOMPONENT02
echo Clear up temp tables for WorldWideProductStrctCollectio
db2 "import from /dev/null of del replace into WWPSCOL.trsNetterPass1"
db2 "import from /dev/null of del replace into WWPSCOL.trsNetterPass2"
db2 "import from /dev/null of del replace into WWPSCOL.trsNetterFinal"
db2 "import from /dev/null of del replace into WWPSCOL.trsNetterAtts"
db2 "insert into WWPSCOL.trsnetteratts select distinct 0,entityid, entitytype, enterprise from opicm.entity where entityid < 10"

db2 "reorg table WWPSCOL.trsNetterPass1"
db2 "reorg table WWPSCOL.trsNetterPass2"
db2 "reorg table WWPSCOL.trsNetterFinal"
db2 "reorg table WWPSCOL.trsNetterAtts"

db2 "runstats on table WWPSCOL.trsNetterPass1 and detailed indexes all"
db2 "runstats on table WWPSCOL.trsNetterPass2 and detailed indexes all"
db2 "runstats on table WWPSCOL.trsNetterFinal and detailed indexes all"
db2 "runstats on table WWPSCOL.trsNetterAtts and detailed indexes all"

db2 "rebind opicmadm.gbl8181K"
db2 "rebind opicmadm.gbl8184K"
db2 "rebind opicmadm.gbl9012K"



db2 terminate

