#
# Script called from the Java IDL program on every x passes of the main IDL loop
#
db2 connect to opicmpdh user opicmadm using cat9tail

# WWProdCompatCollection - EXTCOMPONENT02
echo Clear up temp tables for WWProdCompatCollection
db2 "import from /dev/null of del replace into WWPCCOL.trsNetterPass1"
db2 "import from /dev/null of del replace into WWPCCOL.trsNetterPass2"
db2 "import from /dev/null of del replace into WWPCCOL.trsNetterFinal"
db2 "import from /dev/null of del replace into WWPCCOL.trsNetterAtts"
db2 "insert into WWPCCOL.trsnetteratts select distinct 0,entityid, entitytype, enterprise from opicm.entity where entityid < 10"

db2 "reorg table WWPCCOL.trsNetterPass1"
db2 "reorg table WWPCCOL.trsNetterPass2"
db2 "reorg table WWPCCOL.trsNetterFinal"
db2 "reorg table WWPCCOL.trsNetterAtts"

db2 "runstats on table WWPCCOL.trsNetterPass1 and detailed indexes all"
db2 "runstats on table WWPCCOL.trsNetterPass2 and detailed indexes all"
db2 "runstats on table WWPCCOL.trsNetterFinal and detailed indexes all"
db2 "runstats on table WWPCCOL.trsNetterAtts and detailed indexes all"

db2 "rebind opicmadm.gbl8181G"
db2 "rebind opicmadm.gbl8184G"
db2 "rebind opicmadm.gbl9012G"
