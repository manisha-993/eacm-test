--
-- This script represents all the changes that need to be done to the ECCMODS for the 2.0 release of ECCM.
-- $Log: eccm.20changes.sql,v $
-- Revision 1.1  2004/06/17 17:56:30  dave
-- new script the encasulates the entire 2.0 ECCM
-- changes + compatability and SBB translation
--
--
--
--------------------
--ECCM.LEASECAT
--------------------

CREATE TABLE ECCM.LEASECAT
(
      partnumber        varchar(12),
      cat_identifier   varchar(254),
      countrycode       char(2),
      file_change_ind    char(1),
      igf_family         varchar(54),
      line_change_ind    char(1),
      partnumberdescription  varchar(254),
      ww_mtype           char(4),
      ww_mtype_model     char(3),
      lastupdated      timestamp NOT NULL WITH DEFAULT CURRENT TIMESTAMP);

ALTER TABLE ECCM.LEASECAT PCTFREE 10;

CREATE INDEX ECCM.IND1_LEASECAT ON ECCM.LEASECAT (PARTNUMBER ASC, COUNTRYCODE ASC, CAT_IDENTIFIER ASC);
CREATE UNIQUE INDEX ECCM.IND2_LEASECAT ON ECCM.LEASECAT (PARTNUMBER ASC, COUNTRYCODE ASC)PCTFREE 10 CLUSTER ;

--------------------
--drop view int.search
--INT.SEARCH
--------------------
drop view int.search;
CREATE TABLE int.search(PARTNUMBER       VARCHAR(36),
                        COUNTRYCODE      VARCHAR(6),
                        AUDIENCE         VARCHAR(13),
                        PARTDESCRIPTION  VARCHAR(254),
                        SERIES           VARCHAR(254),
                        FAMILY           VARCHAR(254),
                        STATUS           VARCHAR(75),
                        REGIONCODE       VARCHAR(8),
                        ANNOUNCEDATE     DATE,
                        PRICE            DECIMAL(16, 3),
                        PRICEFROMDATE    DATE,
                        PRICETODATE      DATE,
                        SALESSTATUS      VARCHAR(2),
                        IMAGENAME        VARCHAR(50),
                        USERID           VARCHAR(9),
                        DCRPATH          VARCHAR(512),
                        LASTUPDATED      TIMESTAMP);

-- DDL Statements for indexes on Table INT.SEARCH

CREATE INDEX INT.SEARCH_IX1 ON INT.SEARCH (PARTNUMBER ASC);
CREATE INDEX INT.SEARCH_IX2 ON INT.SEARCH (COUNTRYCODE ASC);
CREATE INDEX INT.SEARCH_IX3 ON INT.SEARCH (AUDIENCE ASC);
CREATE INDEX INT.SEARCH_IX4 ON INT.SEARCH (SERIES ASC);
CREATE INDEX INT.SEARCH_IX5 ON INT.SEARCH (FAMILY ASC);
CREATE INDEX INT.SEARCH_IX6 ON INT.SEARCH (STATUS ASC);
CREATE INDEX INT.SEARCH_IX7 ON INT.SEARCH (REGIONCODE ASC);
CREATE INDEX INT.SEARCH_IX8 ON INT.SEARCH (IMAGENAME ASC);


--------------------
--INT.AVOUTCAT
--------------------
drop view int.avoutcat;
create view int.avoutcat (CATSKU,CATIDENTIFIER,COUNTRYCODE,LANGUAGE,AUDIENCE,
NAME,NAMEDESC,NAMELONGDESC,DISPLAY,SERIESHEADING,IMAGEDISCLAIMER,PRICEDISCLAIMER,
FEATURE,FOOTNOTE,PAGENAME,LEVEL,PUB_FLAG,LASTUPDATED,SEARCHTITLE,SEARCHKEYWORD,
SEARCHABSTRACT,SEARCHDESC,SEARCHCATEGORY,SEARCHDOCTYPE) AS
select CATSKU,CATIDENTIFIER,COUNTRYCODE,LANGUAGE,AUDIENCE,NAME,NAMEDESC,NAMELONGDESC,DISPLAY,
SERIESHEADING,IMAGEDISCLAIMER,PRICEDISCLAIMER,FEATURE,FOOTNOTE,PAGENAME,LEVEL,PUB_FLAG,LASTUPDATED,
SEARCHTITLE,SEARCHKEYWORD,SEARCHABSTRACT,SEARCHDESC,SEARCHCATEGORY,SEARCHDOCTYPE
from int.avwwcat
union
select distinct concat(concat(concat(concat('LEASING@',cat_identifier),'_'),
rtrim(countrycode)),'_en') as CATSKU,concat('LEASING@',cat_identifier), rtrim(countrycode),'en',
'ALL',concat('LEASING@',cat_identifier),concat('LEASING@',cat_identifier),'','','','','','','','',
1,1,'2000-01-01-00.00.00.000000','','','','','',''
from eccm.leasecat
union
select concat(concat('LEASING_',rtrim(countrycode)),'_en') as CATSKU,'LEASING', rtrim(countrycode),
'en','ALL','LEASING','LEASING','','','','','','','','',0,1,'2000-01-01-00.00.00.000000','','','','','',''
from eccm.ccectry
;

--------------------
--ECCM.WWCATCATREL
--------------------
drop view eccm.wwcatcatrel;
create view eccm.wwcatcatrel ( PARENTIDENTIFIER, CHILDIDENTIFIER, COUNTRYCODE, AUDIENCE, MASTNAV, PUB_FLAG, SEQUENCE, SOURCE) as (
select cat.parentidentifier, cat.childidentifier, rtrim(ctry.countrycode), 'LE', 'M', avcat.pub_flag, avcat.sequence, ''
from eccm.catcatrel cat join eccm.ccectry ctry on 1=1 left outer join int.avcatcatseq avcat on cat.parentidentifier = avcat.parentidentifier and cat.childidentifier = avcat.catidentifier and ctry.countrycode = avcat.countrycode and avcat.audience = 'LE' where cat.childidentifier not in (select catidentifier from int.avcatcatrel where mastnav = 'M' and relationshiponly = 'Y')
union
select cat.parentidentifier, cat.catidentifier, rtrim(ctry.countrycode), 'LE', 'M', avcat.pub_flag, avcat.sequence, '' from int.avcatcatrel cat join eccm.ccectry ctry on 1=1 join int.avwwcat catid on cat.catidentifier = catid.catidentifier and catid.countrycode = ctry.countrycode left outer join int.avcatcatseq avcat on cat.parentidentifier = avcat.parentidentifier and cat.catidentifier = avcat.catidentifier and ctry.countrycode = avcat.countrycode and avcat.audience = 'LE' where mastnav = 'M' and relationshiponly = 'Y'
union
select cat.parentidentifier, cat.catidentifier, rtrim(ctry.countrycode), 'LE', cat.mastnav, avcat.pub_flag, avcat.sequence, cat.source from int.avcatcatrel cat join eccm.ccectry ctry on 1=1 left outer join int.avcatcatseq avcat on cat.parentidentifier = avcat.parentidentifier and cat.catidentifier = avcat.catidentifier and ctry.countrycode = avcat.countrycode and avcat.audience = 'LE' where not (cat.mastnav = 'M' and cat.catidentifier in (select childidentifier from eccm.catcatrel))
union
select cat.parentidentifier, cat.childidentifier, rtrim(ctry.countrycode), 'SHOP', 'M', avcat.pub_flag, avcat.sequence, '' from eccm.catcatrel cat join eccm.ccectry ctry on 1=1 left outer join int.avcatcatseq avcat on cat.parentidentifier = avcat.parentidentifier and cat.childidentifier = avcat.catidentifier and ctry.countrycode = avcat.countrycode and avcat.audience = 'SHOP' where cat.childidentifier not in (select catidentifier from int.avcatcatrel where mastnav = 'M' and relationshiponly = 'Y')
union
select cat.parentidentifier, cat.catidentifier, rtrim(ctry.countrycode), 'SHOP', 'M', avcat.pub_flag, avcat.sequence, '' from int.avcatcatrel cat join eccm.ccectry ctry on 1=1 join int.avwwcat catid on cat.catidentifier = catid.catidentifier and catid.countrycode = ctry.countrycode left outer join int.avcatcatseq avcat on cat.parentidentifier = avcat.parentidentifier and cat.catidentifier = avcat.catidentifier and ctry.countrycode = avcat.countrycode and avcat.audience = 'SHOP' where mastnav = 'M' and relationshiponly = 'Y'
union
select cat.parentidentifier, cat.catidentifier, rtrim(ctry.countrycode), 'SHOP', cat.mastnav, avcat.pub_flag, avcat.sequence, cat.source from int.avcatcatrel cat join eccm.ccectry ctry on 1=1 left outer join int.avcatcatseq avcat on cat.parentidentifier = avcat.parentidentifier and cat.catidentifier = avcat.catidentifier and ctry.countrycode = avcat.countrycode and Upper(avcat.audience) = 'SHOP' where not (cat.mastnav = 'M' and cat.catidentifier in (select childidentifier from eccm.catcatrel))
union
select 'PSG', cat.parentidentifier, rtrim(ctry.countrycode), 'SHOP', 'M', 1, 1, 'ECCM' from int.avcatroot cat join eccm.ccectry ctry on 1=1 where cat.parentidentifier <> 'Popular Models' and cat.parentidentifier not in (select catidentifier from int.avcatcatrel where mastnav = 'M')
union
select 'PSG', cat.parentidentifier, rtrim(ctry.countrycode), 'LE', 'M', 1, 1, 'ECCM' from int.avcatroot cat join eccm.ccectry ctry on 1=1 where cat.parentidentifier <> 'Popular Models' and cat.parentidentifier not in (select catidentifier from int.avcatcatrel where mastnav = 'M'))
union
select 'DAC', 'DAC@DAC1', rtrim(ctry.countrycode), 'DACMAX', 'M', 1, 0, 'ECCM' from eccm.ccectry ctry where ctry.countrycode='US'
union select distinct 'LEASING', concat('LEASING@',cat_identifier), rtrim(countrycode), 'ALL', 'L', 1, 0, 'ECCM' from eccm.leasecat
;

--------------------
--ECCM.WWCATPRDREL
--------------------
drop view eccm.wwcatprdrel;
create view eccm.wwcatprdrel
( identifier, partnumber, mastnav, countrycode, language, audience, sequence, pub_flag,
ranking, refreshtime ) as
select
 psbf.famsername,
 prd.partnumber,
 'M',
 prd.countrycode,
 prd.language,
 rtrim(CHAR(multi.longdescription, 13)),
 seq.sequence,
 seq.pub_flag,
 '',
 prd.refreshtime
from
int.avwwprd prd
 join eccm.prdroot root on prd.prdentityid = root.entityid and
  prd.prdentitytype = root.entitytype and
  prd.prdnlsid=root.nlsid
 join eccm.of of on root.ofentityid = of.entityid and root.nlsid=of.nlsid
 join eccm.projserbrandfam psbf on root.projectid = psbf.projectid
 join eccm.audience multi on root.entityid = multi.entityid and
  multi.entityname = root.entitytype and
  root.nlsid=multi.nlsid
 left outer join int.avprdcatseq seq on psbf.famsername = seq.catidentifier and
  prd.partnumber = seq.partnumber and
  prd.countrycode = seq.countrycode and
  prd.language = seq.language and
  multi.longdescription= seq.audience
where
 (prd.productclass = 'MTM' or
 prd.productclass = 'SERVICE' or
 prd.productclass = 'SO') and
 multi.longdescription in('LE','SHOP') and
 of.optgroupname is null and
 of.subgroup is null
union
select
 psbf.famsername,
 prd.partnumber,
 'M',
 prd.countrycode,
 prd.language,
 rtrim(CHAR(multi.longdescription, 13)),
 seq.sequence,
 seq.pub_flag,
 '',
 prd.refreshtime
from   int.avwwprd prd
 join eccm.prdroot root on prd.prdentityid = root.entityid and
  prd.prdentitytype = root.entitytype and
  prd.prdnlsid=root.nlsid
 join eccm.projserbrandfam psbf on root.projectid = psbf.projectid
 join eccm.audience multi on root.entityid = multi.entityid and
  multi.entityname = root.entitytype and
  multi.nlsid=root.nlsid
 left outer join int.avprdcatseq seq on psbf.famsername= seq.catidentifier and
  prd.partnumber = seq.partnumber and
  prd.countrycode = seq.countrycode and
  prd.language = seq.language and
  multi.longdescription= seq.audience
where  (prd.productclass='V' or prd.productclass='CTO')
union
select
 psbf.famsername concat '@' concat of.optgroupname,
 prd.partnumber,
 'M',
 prd.countrycode,
 prd.language ,
 rtrim(CHAR(multi.longdescription, 13)),
 seq.sequence,
 seq.pub_flag,
 '',
 prd.refreshtime
from int.avwwprd prd
 join eccm.prdroot root on prd.prdentityid = root.entityid and
  prd.prdentitytype = root.entitytype and
  prd.prdnlsid=root.nlsid
 join eccm.of of on root.ofentityid = of.entityid and root.nlsid=of.nlsid
 join eccm.projserbrandfam psbf on root.projectid = psbf.projectid
 join eccm.audience multi on root.entityid = multi.entityid and
  multi.entityname = root.entitytype and
  multi.nlsid=root.nlsid
 left outer join int.avprdcatseq seq on psbf.famsername
  concat '@' concat of.optgroupname = seq.catidentifier and
  prd.partnumber = seq.partnumber and
  prd.countrycode = seq.countrycode and
  prd.language = seq.language and
  multi.longdescription = seq.audience
where
 (prd.productclass = 'MTM' or prd.productclass = 'SERVICE' or
 prd.productclass = 'SO') and
 multi.longdescription in('LE','SHOP') and
 of.optgroupname is not null and
 of.subgroup is null
union
select
 psbf.famsername concat '@' concat of.optgroupname concat '@' concat of.subgroup,
 prd.partnumber,
 'M',
 prd.countrycode,
 prd.language,
 rtrim(CHAR(multi.longdescription, 13)),
 seq.sequence,
 seq.pub_flag,
 '',
 prd.refreshtime
from   int.avwwprd prd
 join eccm.prdroot root on prd.prdentityid = root.entityid and
  prd.prdentitytype = root.entitytype and
  prd.prdnlsid=root.nlsid
 join eccm.of of on root.ofentityid = of.entityid and root.nlsid=of.nlsid
 join eccm.projserbrandfam psbf on root.projectid = psbf.projectid
 join eccm.audience multi on root.entityid = multi.entityid and
  multi.entityname = root.entitytype and
  root.nlsid=multi.nlsid
 left outer join int.avprdcatseq seq on psbf.famsername
  concat '@' concat of.optgroupname concat '@' concat of.subgroup =
  seq.catidentifier and
  prd.partnumber = seq.partnumber and
  prd.countrycode = seq.countrycode and
  prd.language = seq.language and
  multi.longdescription = seq.audience
where
 (prd.productclass = 'MTM' or
 prd.productclass = 'SERVICE' or prd.productclass = 'SO')
 and multi.longdescription in('LE','SHOP') and
 of.optgroupname is not null and
 of.subgroup is not null
union
select
 cb.catid,
 prd.partnumber,
 'M',
 prd.countrycode,
 prd.language,
 rtrim(CHAR(multi.longdescription, 13)),
 seq.sequence,
 seq.pub_flag,
 '',
 prd.refreshtime
from int.avwwprd prd
 join eccm.prdroot root on prd.prdentityid = root.entityid and
  prd.prdentitytype = root.entitytype and
  prd.prdnlsid=root.nlsid
 join eccm.cbparts cb on root.entitytype = cb.entitytype and
  root. entityid=cb.entityid and
  root.nlsid=cb.nlsid
 join eccm.audience multi on root.entityid = multi.entityid and
  multi.entityname = root.entitytype and
  multi.nlsid=root.nlsid
 left outer join int.avprdcatseq seq on cb .catid = seq.catidentifier and
  prd.partnumber = seq.partnumber and
  prd.countrycode = seq.countrycode and
  prd.language = seq.language and
  multi.longdescription = seq.audience
where multi.longdescription in('LE','SHOP')
union
select
 prd.catidentifier,
 prd.partnumber,
 'N',
 prd.countrycode,
 prd.language,
 rtrim(CHAR(prd.audience, 13)),
 seq.sequence,
 seq.pub_flag,
 '',
 avwwprd.refreshtime
from  int.avprdcat prd
 left outer join int.avprdcatseq seq on prd.catidentifier = seq.catidentifier and
  prd.partnumber = seq.partnumber and
  prd.countrycode = seq.countrycode and
  prd.language = seq.language and
  prd.audience = seq.audience
 join int.avwwprd avwwprd on avwwprd.partnumber=prd.partnumber and
  avwwprd.countrycode=prd.countrycode and
  avwwprd.language = prd.language
 join eccm.audience multi on prd.prdentityid = multi.entityid and
  prd.prdentitytype = multi.entityname and
  prd.audience = multi.longdescription
where multi.longdescription in('LE','SHOP')
union
select
 evp.catidentifier,
 evp.partnumber,
 'N',
 evp.countrycode,
 evp.language,
 rtrim(CHAR(evp.audience, 13)),
 1,
 1,
 evp.evp_indicator,
 prd.refreshtime
from   int.avprdevp evp
 join int.avwwprd prd on evp.partnumber = prd.partnumber and
  evp.countrycode = prd.countrycode and
  evp.language = prd.language
 join eccm.audience multi on prd.prdentityid = multi.entityid and
 prd.prdentitytype = multi.entityname and
 evp.audience = multi.longdescription
where multi.longdescription in('LE','SHOP')
union
select 'DAC@DAC1',
 prd.partnumber,
 'M',
 prd.countrycode,
 prd.language,
 rtrim(CHAR(multi.longdescription,13)),
 0,
 1,
 '',
 prd.refreshtime
from int.avwwprd prd
 join eccm.audience multi on prd.prdentityid = multi.entityid and
  multi.entityname = prd.prdentitytype
where multi.longdescription in('DACMAX','LE and DACMAX')
union
select
distinct concat('LEASING@',cat_identifier),
prd.partnumber,
'L',
rtrim(prd.countrycode),
'en',
'ALL',
0,
1,
'',
prd.refreshtime
from int.avwwprd prd
join eccm.leasecat lease on prd.partnumber = lease.partnumber and prd.countrycode = lease.countrycode
;

----------------------------------------------
-- This piece is for the Compatability changes
----------------------------------------------

drop table eccm.compat;
------------------------------------------------
-- DDL Statements for table "ECCM    "."COMPAT"
------------------------------------------------
create summary table eccm.compat
( entitytype, wwpartnum, optionpnumb, mtm, operatingsys, exceptmodel, compub, compubmkt )
as
( with avwwprd_tmp (entityid)
 as ( select distinct csol.ofid from ECCM.csol csol
 join int.avwwprd prd
 on prd.prdentityid = csol.entityid and prd.prdnlsid = csol.nlsid and prd.prdentitytype = 'CSOL' ),

 avwwprd_tmp2 (entityid)
 as ( select distinct ccto.ctoid from ECCM.ccto ccto
 join int.avwwprd prd
 on prd.prdentityid = ccto.entityid and prd.prdnlsid = ccto.nlsid and prd.prdentitytype = 'CCTO' ),

 avwwprd_tmp3(entityid)
 as ( select distinct varcvar.id1 from ECCM.cvar cvar
 join int.avwwprd prd
 on prd.prdentityid = cvar.entityid and prd.prdnlsid = cvar.nlsid and prd.prdentitytype = 'CVAR'
 join ECCM.varcvar varcvar
 on varcvar.ID2 = cvar.entityid )

(
select distinct 'PSGOFCMPOF', of1.OFFERINGPNUMB, of2.OFFERINGPNUMB, '-', '-', '-', char(nullif(1,1)), char(nullif(1,1))
from ECCM.ofcmpof ofcmpof
join ECCM.of of1
on of1.entityid = ofcmpof.ID1 and of1.nlsid = 1
join ECCM.of of2
on of2.entityid = ofcmpof.ID2 and of2.nlsid = 1
join avwwprd_tmp tmp1
on tmp1.entityid = of1.entityid
join avwwprd_tmp tmp2
on tmp2.entityid = of2.entityid

UNION ALL

select distinct 'PSGOFCMPOF', of2.OFFERINGPNUMB, of1.OFFERINGPNUMB, '-', '-', '-', char(nullif(1,1)), char(nullif(1,1))
from ECCM.ofcmpof ofcmpof
join ECCM.of of1
on of1.entityid = ofcmpof.ID1 and of1.nlsid = 1
join ECCM.of of2
on of2.entityid = ofcmpof.ID2 and of2.nlsid = 1
join avwwprd_tmp tmp1
on tmp1.entityid = of1.entityid
join avwwprd_tmp tmp2 on tmp2.entityid = of2.entityid
)

UNION ALL

select distinct 'PSGMTOSOF', of.OFFERINGPNUMB, '-', cpg.MACHTYPE, coalesce(cpgos.CPGOPSYS,'-'), '-', ofcpgos.compub, ofcpgos.compubmkt
from ECCM.cpgos cpgos
join ECCM.cpgcpgos cpgcpgos
on cpgcpgos.id2 = cpgos.entityid
join ECCM.cpg cpg
on cpg.entityid = cpgcpgos.id1 and cpg.nlsid = 1
join ECCM.ofcpgos ofcpgos
on ofcpgos.id2 = cpgos.entityid
join ECCM.of of
on of.entityid = ofcpgos.id1 and of.nlsid = 1
join avwwprd_tmp tmp
on tmp.entityid = of.entityid

UNION ALL

select distinct 'PSGMTOSOF', of.OFFERINGPNUMB, '-', cpg.MACHTYPE, '-', '-', char(nullif(1,1)), char(nullif(1,1))
from ECCM.of of
join ECCM.cpgof cpgof
on cpgof.ID2 = of.entityid
join ECCM.cpg cpg
on cpg.entityid = cpgof.id1 and cpg.nlsid = 1
join avwwprd_tmp tmp
on tmp.entityid = of.entityid
where of.entityid not in (select id1 from ECCM.ofcpgos) and of.nlsid = 1

UNION ALL

select distinct 'PSGMTOSOF', cto.COFPNUMB, '-', cpg.MACHTYPE, '-', '-', char(nullif(1,1)), char(nullif(1,1))
from ECCM.cto cto
join ECCM.cpgcto cpgcto
on cpgcto.id2 = cto.entityid
join ECCM.cpg cpg
on cpg.entityid = cpgcto.id1 and cpg.nlsid = 1
join avwwprd_tmp2 tmp
on tmp.entityid = cto.entityid
where cto.nlsid = 1

UNION ALL

select distinct 'PSGMTOSOF', var.offeringPNUMB, '-', cpg.MACHTYPE, '-', '-', char(nullif(1,1)), char(nullif(1,1))
from ECCM.var var
join ECCM.cpgvar cpgvar
on cpgvar.id2 = var.entityid
join ECCM.cpg cpg
on cpg.entityid = cpgvar.id1 and cpg.nlsid = 1
join avwwprd_tmp3 tmp
on tmp.entityid = var.entityid
where var.nlsid = 1
)
DATA INITIALLY DEFERRED REFRESH DEFERRED;



----------------------------------------------
-- This is for the translation piece changes
--
drop view int.avoutprd;
create view int.avoutprd  (  PRDSKU,  PRDENTITYTYPE,  PRDENTITYID,  PRDNLSID,  PARTNUMBER,  COUNTRYCODE,
LANGUAGE,  PRODUCTCLASS,  AUDIENCE,  AUXDESC1,  AUXDESC2,  SHORTDESC,  ENG_SBBDESC, ANNOUNCEDATE,  WITHDRAWDATE,
FOTANNOUNCEDATE,  FOTWITHDRAWDATE,  NAVBARFILENAME,  CONFIGURATORID,  FOTSTATUS,  WWPARTNUM,  INSTALLABLE,
BASESYSTEM,  SERVICETYPE,  SPECIALBID,  SBBTYPE,  UNSPSC,  UNUOM,  PUB_FLAG,  REVIEWPUB_FLAG,  LASTUPDATED,
REFRESHTIME,  VALID_FLAG,  SBBGADATE,  INSTALLABLE_OVERRIDE,  FAMILYNAME )  as (
select  prd.prdsku,  prd.prdentitytype,  prd.prdentityid,  prd.prdnlsid,  prd.partnumber,  prd.countrycode,
prd.language,  prd.productclass,  prd.audience,  prd.auxdesc1,  prd.auxdesc2,  prd.shortdesc, '', prd.announcedate,
prd.withdrawdate,  prd.fotannouncedate,  prd.fotwithdrawdate,  prd.navbarfilename,  prd.configuratorid,
prd.fotstatus,  prd.wwpartnum,  prd.installable,  prd.basesystem,  prd.servicetype,  prd.specialbid,  prd.sbbtype,
prd.unspsc,  prd.unuom,  prd.pub_flag,  prd.reviewpub_flag,  prd.lastupdated,  prd.refreshtime,  prd.valid_flag ,
prd.announcedate,  prd.installable_override,
CASE prd.productclass  WHEN 'CTO'  THEN ''  WHEN 'CB'  THEN '' ELSE coalesce(proj.familyname,'')  END
from    int.avwwprd prd
join eccm.prdroot root on prd.prdentitytype = root.entitytype  and prd.prdentityid = root.entityid
and prd.prdnlsid = root.nlsid
left outer join eccm.projserbrandfam proj on root.projectid = proj.projectid

union  select  rtrim(sbb.sbbpnumb) concat '_' concat rtrim(prd.countrycode) concat '_' concat
rtrim(language.isolangcode2),  'SBB',  sbb.entityid,  sbb.nlsid,  sbb.sbbpnumb,  prd.countrycode,
language.isolangcode2,  'SBB',  'ALL',  '',  '',  sbb.sbbpnumbdesc,  sbb1.sbbpnumbdesc, sbb.sbbannouncetgt,
sbb.sbbwithdrawldate,  date(sbb.sbbannouncetgt) - 28 days,  sbb.sbbwithdrawldate,  '',  '',  sbb.sbbstatus,
sbb.sbbpnumb,  0,  0,  '',  '',  sbb.sbbtype,  '',  '',  1,  1,  sbb.valfrom,  sbb.valfrom,  1,  sbb.sbbgenavaildate,
1,  ''
from    int.avwwprd prd
join eccm.cctosbb cctosbb on prd.prdentityid = cctosbb.ID1
join eccm.ccto ccto on ccto.entityid=cctosbb.ID1
join eccm.sbb sbb on cctosbb.ID2 = sbb.entityid
join eccm.sbb sbb1 on cctosbb.ID2 = sbb1.entityid and sbb1.nlsid = 1
join eccm.psgnls language on sbb.nlsid = language.nlsid
where (cctosbb.sbbpublishctdate is null or cctosbb.sbbpublishctdate <= current date) and
(cctosbb.sbbunpublishctdate is null or cctosbb.sbbunpublishctdate >= current date) and  prd.prdentitytype = 'CCTO'
and  prd.prdnlsid=sbb.nlsid and  prd.prdnlsid=ccto.nlsid

union select  rtrim(sbb.sbbpnumb) concat '_' concat rtrim(prd.countrycode) concat '_' concat
rtrim(language.isolangcode2),  'SBB',  sbb.entityid,  sbb.nlsid,  sbb.sbbpnumb,  prd.countrycode,
language.isolangcode2,  'SBB',  'ALL',  '',  '',  sbb.sbbpnumbdesc,  sbb1.sbbpnumbdesc, sbb.sbbannouncetgt,
sbb.sbbwithdrawldate,  date(sbb.sbbannouncetgt) - 28 days,  sbb.sbbwithdrawldate,  '',  '',  sbb.sbbstatus,
sbb.sbbpnumb,  0,  0,  '',  '',  sbb.sbbtype,  '',  '',  1,  1,  sbb.valfrom,  sbb.valfrom,  1,  sbb.sbbgenavaildate,
1,  ''
from    int.avwwprd prd
join eccm.cvarsbb cvarsbb on prd.prdentityid = cvarsbb.ID1
join eccm.cvar cvar on cvar.entityid=cvarsbb.id1
join eccm.sbb sbb on sbb.entityid=cvarsbb.ID2
join eccm.sbb sbb1 on cvarsbb.ID2 = sbb1.entityid and sbb1.nlsid = 1
join eccm.psgnls language on sbb.nlsid = language.nlsid
where   prd.prdentitytype = 'CVAR' and  prd.prdnlsid=cvar.nlsid and  prd.prdnlsid=sbb.nlsid);

grant control on int.avoutprd to user eccmfeed, int;
