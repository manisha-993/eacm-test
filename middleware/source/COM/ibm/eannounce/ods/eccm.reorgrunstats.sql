##-----------------------------------------------------------------------------------------
##  REORG INFORMATIION
##  CHECKED FOR FINAL SCRIPT
##-----------------------------------------------------------------------------------------
##
## $Log: eccm.reorgrunstats.sql,v $
## Revision 1.17  2004/01/28 21:36:58  dave
## more script changes
##
## Revision 1.16  2004/01/27 19:23:00  dave
## more script changes
##
## Revision 1.15  2004/01/26 19:03:50  dave
## more ECCM minor updates to script files
##
## Revision 1.14  2004/01/20 18:32:44  dave
## more minor eccm script changes
##
## Revision 1.13  2004/01/17 00:10:51  dave
## more refinement
##
## Revision 1.12  2004/01/12 19:15:31  dave
## more reorg adds/ changes
##
## Revision 1.11  2004/01/07 00:21:03  dave
## more scripting
##
## Revision 1.10  2004/01/06 19:06:48  dave
## more reorgs on relators
##
## Revision 1.9  2004/01/06 18:53:27  dave
## added indexes to help passi and the ProductMarketingData pull
##
## Revision 1.8  2003/12/31 19:54:56  dave
## refinements
##
## Revision 1.7  2003/12/22 21:29:44  dave
## another typo
##
## Revision 1.6  2003/12/22 21:28:32  dave
## typo in schema name
##
## Revision 1.5  2003/12/22 21:24:53  dave
## more primary key fixing up so it is no longer part of the
## table def
##
## Revision 1.4  2003/12/22 20:37:14  dave
## addressed eccm.attribute primary key
##
## Revision 1.3  2003/12/15 19:20:43  dave
## more ECCM changes
##
## Revision 1.2  2003/12/11 06:37:48  dave
## more updates to the scripts
##
## Revision 1.1  2003/12/10 20:34:54  dave
## updating and making final scripts
##

db2 connect to eccmods user opicmadm using cat9tail;

echo db2 reorg table ECCM.br index ECCM.br_pk;
db2 reorg table ECCM.br index ECCM.br_pk;
db2 runstats on table ECCM.br and detailed indexes all;

echo db2 reorg table ECCM.brfam index ECCM.brfam_pk;
db2 reorg table ECCM.brfam index ECCM.brfam_pk;
db2 runstats on table ECCM.brfam and detailed indexes all;

echo db2 reorg table ECCM.famse index ECCM.famse_pk;
db2 reorg table ECCM.famse index ECCM.famse_pk;
db2 runstats on table ECCM.famse and detailed indexes all;

echo db2 reorg table ECCM.fam index ECCM.fam_pk;
db2 reorg table ECCM.fam index ECCM.fam_pk;
db2 runstats on table ECCM.fam and detailed indexes all;

echo db2 reorg table ECCM.se index ECCM.se_pk;
db2 reorg table ECCM.se index ECCM.se_pk;
db2 runstats on table ECCM.se and detailed indexes all;

echo db2 reorg table ECCM.pr index ECCM.pr_pk;
db2 reorg table ECCM.pr index ECCM.pr_pk;
db2 runstats on table ECCM.pr and detailed indexes all;

echo db2 reorg table ECCM.prof index ECCM.prof_pk;
db2 reorg table ECCM.prof index ECCM.prof_pk;
db2 runstats on table ECCM.prof and detailed indexes all;

echo db2 reorg table ECCM.of index ECCM.of_pk use TEMP16k;
db2 reorg table ECCM.of index ECCM.of_pk use TEMP16k;
db2 runstats on table ECCM.of and detailed indexes all;

echo db2 reorg table ECCM.ofcsol index ECCM.ofcsol_pk;
db2 reorg table ECCM.ofcsol index ECCM.ofcsol_pk;
db2 runstats on table ECCM.ofcsol and detailed indexes all;

echo db2 reorg table ECCM.ofso index ECCM.ofso_ix1;
db2 reorg table ECCM.ofso index ECCM.ofso_ix1;
db2 runstats on table ECCM.ofso and detailed indexes all;

echo db2 reorg table ECCM.offb index ECCM.offb_ix1;
db2 reorg table ECCM.offb index ECCM.offb_ix1;
db2 runstats on table ECCM.offb and detailed indexes all;

echo db2 reorg table ECCM.so index ECCM.so_pk;
db2 reorg table ECCM.so index ECCM.so_pk;
db2 runstats on table ECCM.so and detailed indexes all;

echo db2 reorg table ECCM.csolso index ECCM.csolso_ix1;
db2 reorg table ECCM.csolso index ECCM.csolso_ix1;
db2 runstats on table ECCM.csolso and detailed indexes all;

echo db2 reorg table ECCM.csolfb index ECCM.csolfb_ix1;
db2 reorg table ECCM.csolfb index ECCM.csolfb_ix1;
db2 runstats on table ECCM.csolfb and detailed indexes all;

echo db2 reorg table ECCM.so index ECCM.so_pk;
db2 reorg table ECCM.so index ECCM.so_pk;
db2 runstats on table ECCM.so and detailed indexes all;

echo db2 reorg table ECCM.csol index ECCM.csol_pk;
db2 reorg table ECCM.csol index ECCM.csol_pk;
db2 runstats on table ECCM.csol and detailed indexes all;

echo db2 reorg table ECCM.cto index ECCM.cto_pk;
db2 reorg table ECCM.cto index ECCM.cto_pk;
db2 runstats on table ECCM.cto and detailed indexes all;

echo db2 reorg table ECCM.ctoccto index ECCM.ctoccto_pk;
db2 reorg table ECCM.ctoccto index ECCM.ctoccto_pk;
db2 runstats on table ECCM.ctoccto and detailed indexes all;

echo db2 reorg table ECCM.ccto index ECCM.ccto_pk;
db2 reorg table ECCM.ccto index ECCM.ccto_pk;
db2 runstats on table ECCM.ccto and detailed indexes all;

echo db2 reorg table ECCM.var index ECCM.var_pk;
db2 reorg table ECCM.var index ECCM.var_pk;
db2 runstats on table ECCM.var and detailed indexes all;

echo db2 reorg table ECCM.prvar index ECCM.prvar_pk;
db2 reorg table ECCM.prvar index ECCM.prvar_pk;
db2 runstats on table ECCM.prvar and detailed indexes all;

echo db2 reorg table ECCM.varcvar index ECCM.varcvar_pk;
db2 reorg table ECCM.varcvar index ECCM.varcvar_pk;
db2 runstats on table ECCM.varcvar and detailed indexes all;

echo db2 reorg table ECCM.cvar index ECCM.cvar_pk;
db2 reorg table ECCM.cvar index ECCM.cvar_pk;
db2 runstats on table ECCM.cvar and detailed indexes all;

echo db2 reorg table ECCM.sbb index ECCM.sbb_pk;
db2 reorg table ECCM.sbb index ECCM.sbb_pk;
db2 runstats on table ECCM.sbb and detailed indexes all;

echo db2 reorg table ECCM.cb index ECCM.cb_pk;
db2 reorg table ECCM.cb index ECCM.cb_pk;
db2 runstats on table ECCM.cb and detailed indexes all;

echo db2 reorg table ECCM.cbcsol index ECCM.cbcsol_pk;
db2 reorg table ECCM.cbcsol index ECCM.cbcsol_pk;
db2 runstats on table ECCM.cbcsol and detailed indexes all;

echo db2 reorg table ECCM.ctocg index ECCM.ctocg_ix1;
db2 reorg table ECCM.ctocg index ECCM.ctocg_ix1;
db2 runstats on table ECCM.ctocg and detailed indexes all;

echo db2 reorg table ECCM.cg index ECCM.cg_pk;
db2 reorg table ECCM.cg index ECCM.cg_pk;
db2 runstats on table ECCM.cg and detailed indexes all;

echo db2 reorg table ECCM.cctosbb index ECCM.cctosbb_ix1;
db2 reorg table ECCM.cctosbb index ECCM.cctosbb_ix1;
db2 runstats on table ECCM.cctosbb and detailed indexes all;

echo db2 reorg table ECCM.cctomm index ECCM.cctomm_pk;
db2 reorg table ECCM.cctomm index ECCM.cctomm_pk;
db2 runstats on table ECCM.cctomm and detailed indexes all;

echo db2 reorg table ECCM.csolmm index ECCM.csolmm_ix1;
db2 reorg table ECCM.csolmm index ECCM.csolmm_ix1;
db2 runstats on table ECCM.csolmm and detailed indexes all;

echo db2 reorg table ECCM.ofmm index ECCM.ofmm_ix1;
db2 reorg table ECCM.ofmm index ECCM.ofmm_ix1;
db2 runstats on table ECCM.ofmm and detailed indexes all;

echo db2 reorg table ECCM.ofimg index ECCM.ofimg_ix1;
db2 reorg table ECCM.ofimg index ECCM.ofimg_ix1;
db2 runstats on table ECCM.ofimg and detailed indexes all;

echo db2 reorg table ECCM.csolimg index ECCM.csolimg_ix1;
db2 reorg table ECCM.csolimg index ECCM.csolimg_ix1;
db2 runstats on table ECCM.csolimg and detailed indexes all;

echo db2 reorg table ECCM.ctomm index ECCM.ctomm_pk;
db2 reorg table ECCM.ctomm index ECCM.ctomm_pk;
db2 runstats on table ECCM.ctomm and detailed indexes all;

echo db2 reorg table ECCM.ctoimg index ECCM.ctoimg_pk;
db2 reorg table ECCM.ctoimg index ECCM.ctoimg_pk;
db2 runstats on table ECCM.ctoimg and detailed indexes all;

echo db2 reorg table ECCM.mm index ECCM.mm_pk;
db2 reorg table ECCM.mm index ECCM.mm_pk;
db2 runstats on table ECCM.mm and detailed indexes all;

echo db2 reorg table ECCM.img index ECCM.img_pk;
db2 reorg table ECCM.img index ECCM.img_pk;
db2 runstats on table ECCM.img and detailed indexes all;

echo db2 reorg table ECCM.fb index ECCM.fb_pk;
db2 reorg table ECCM.fb index ECCM.fb_pk;
db2 runstats on table ECCM.fb and detailed indexes all;

echo db2 reorg table ECCM.cvarsbb index ECCM.cvarsbb_pk;
db2 reorg table ECCM.cvarsbb index ECCM.cvarsbb_pk;
db2 runstats on table ECCM.cvarsbb and detailed indexes all;

echo db2 reorg table ECCM.ctosbb index ECCM.ctosbb_pk;
db2 reorg table ECCM.ctosbb index ECCM.ctosbb_pk;
db2 runstats on table ECCM.ctosbb and detailed indexes all;

echo db2 reorg table ECCM.varsbb index ECCM.varsbb_pk;
db2 reorg table ECCM.varsbb index ECCM.varsbb_pk;
db2 runstats on table ECCM.varsbb and detailed indexes all;

echo db2 reorg table ECCM.attribute index eccm.attribute_pk;
db2 reorg table ECCM.attribute index eccm.attribute_pk;
db2 runstats on table ECCM.attribute and detailed indexes all;

echo db2 reorg table ECCM.prodattribute index ECCM.PRODATTRIBUTE_PK use tempspace1;
db2 reorg table ECCM.prodattribute index ECCM.PRODATTRIBUTE_PK use tempspace1;
db2 runstats on table ECCM.prodattribute and detailed indexes all;

echo db2 reorg table ECCM.prodattrelator index eccm.PRODATTRELATOR_PK use temp1;
db2 reorg table ECCM.prodattrelator index eccm.PRODATTRELATOR_PK use temp1;
db2 runstats on table ECCM.prodattrelator and detailed indexes all;

echo db2 reorg table ECCM.flag index ECCM.FLAG_PK;
db2 reorg table ECCM.flag index ECCM.FLAG_PK;
db2 runstats on table ECCM.flag and detailed indexes all;

echo db2 reorg table ECCM.generalarea index ECCM.GENERALAREA_IX1;
db2 reorg table ECCM.generalarea index ECCM.GENERALAREA_IX1;
db2 runstats on table ECCM.generalarea and detailed indexes all;

echo db2 reorg table ECCM.ofcmpof index ECCM.ofcmpof_PK;
db2 reorg table ECCM.ofcmpof index ECCM.ofcmpof_PK;
db2 runstats on table ECCM.ofcmpof and detailed indexes all;

echo db2 reorg table ECCM.cpgvar index ECCM.cpgvar_PK;
db2 reorg table ECCM.cpgvar index ECCM.cpgvar_PK;
db2 runstats on table ECCM.cpgvar and detailed indexes all;

echo db2 reorg table ECCM.cpg index ECCM.cpg_PK;
db2 reorg table ECCM.cpg index ECCM.cpg_PK;
db2 runstats on table ECCM.cpg and detailed indexes all;

echo db2 reorg table ECCM.cpgcto index ECCM.cpgcto_PK;
db2 reorg table ECCM.cpgcto index ECCM.cpgcto_PK;
db2 runstats on table ECCM.cpgcto and detailed indexes all;

echo db2 reorg table ECCM.cpgof index ECCM.cpgof_PK;
db2 reorg table ECCM.cpgof index ECCM.cpgof_PK;
db2 runstats on table ECCM.cpgof and detailed indexes all;

echo db2 reorg table ECCM.ofcpgos index ECCM.ofcpgos_PK;
db2 reorg table ECCM.ofcpgos index ECCM.ofcpgos_PK;
db2 runstats on table ECCM.ofcpgos and detailed indexes all;

echo db2 reorg table ECCM.cpgos index ECCM.cpgos_PK;
db2 reorg table ECCM.cpgos index ECCM.cpgos_PK;
db2 runstats on table ECCM.cpgos and detailed indexes all;

##
## NEW ADDITIONS
##

echo db2 reorg table eccm.attrunits index eccm.attrunits_pk;
db2 reorg table eccm.attrunits index eccm.attrunits_pk;
db2 runstats on table eccm.attrunits and detailed indexes all;

echo db2 reorg table ECCM.CSOLsbb index ECCM.csolsbb_ix1;
db2 reorg table ECCM.CSOLsbb index ECCM.CSOLSBB_ix1;
db2 runstats on table ECCM.CSOLsbb and detailed indexes all;

echo db2 reorg table ECCM.OFsbb index ECCM.OFsbb_ix1;
db2 reorg table ECCM.OFsbb index ECCM.OFSBB_ix1;
db2 runstats on table ECCM.OFsbb and detailed indexes all;

echo db2 reorg table ECCM.PSLAVAIL index ECCM.PSLAVAIL_PK;
db2 reorg table ECCM.PSLAVAIL index ECCM.PSLAVAIL_PK;
db2 runstats on table ECCM.PSLAVAIL and detailed indexes all;

echo db2 reorg table ECCM.PBYAVAIL index ECCM.PBYAVAIL_PK;
db2 reorg table ECCM.PBYAVAIL index ECCM.PBYAVAIL_PK;
db2 runstats on table ECCM.PBYAVAIL and detailed indexes all;

echo db2 reorg table ECCM.OFPORT index ECCM.OFPORT_IX2;
db2 reorg table ECCM.OFPORT index ECCM.OFPORT_IX2;
db2 runstats on table ECCM.OFPORT and detailed indexes all;

echo db2 reorg table ECCM.PORT index ECCM.PORT_PK;
db2 reorg table ECCM.PORT index ECCM.PORT_PK;
db2 runstats on table ECCM.PORT and detailed indexes all;



db2 terminate all;
