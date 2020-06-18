--
--  Tables and summary tables go here
--
-- $Log: eccm.sumtables.sql,v $
-- Revision 1.28  2004/04/19 15:47:55  dave
-- GAV fixes for eCCM
--
-- Revision 1.27  2004/04/07 18:51:29  dave
-- removing Netfinity from pbfbase
--
-- Revision 1.26  2004/03/25 23:22:44  dave
-- adding version visibility for clients
--
-- Revision 1.25  2004/03/12 22:51:14  dave
-- added index to SERVICETYPE
--
-- Revision 1.24  2004/03/03 22:36:11  dave
-- adding cto.COFPNUMB on else in prdroot
--
-- Revision 1.23  2004/03/03 21:20:08  dave
-- MN# is 18061344.  added case for CVAR
--
-- Revision 1.22  2004/03/01 21:35:02  dave
-- lock/unlock/netfinity back
--
-- Revision 1.21  2004/02/27 17:45:58  dave
-- PROJSERVBRANDFAM change to remove NetFinity
--
-- Revision 1.20  2004/02/19 20:18:27  dave
-- fix to PRDROOT cvar.ccto
--
-- Revision 1.19  2004/02/18 23:30:14  dave
-- -- FB 53654  coalesce(cpgos.CPGOSNAME,'-') change to CPGOPSYS
--
-- Revision 1.18  2004/01/28 21:36:58  dave
-- more script changes
--
-- Revision 1.17  2004/01/20 18:53:30  dave
-- removing of the echo and some not null required fields
--
-- Revision 1.16  2004/01/17 01:02:36  dave
-- more streamlining
--
-- Revision 1.15  2004/01/16 17:59:49  dave
-- RTRIM on PSGSERVICETYPE sumtable
--
-- Revision 1.14  2004/01/15 20:59:49  dave
-- minor syntax fix
--
-- Revision 1.13  2004/01/15 20:57:23  dave
-- added special bid for CVAR
--
-- Revision 1.12  2004/01/15 18:11:06  dave
-- adding RTRIM
--
-- Revision 1.11  2004/01/09 17:44:27  dave
-- changes specialbid to specialbid_fc
--
-- Revision 1.10  2004/01/08 19:16:06  dave
-- more eccm scripting changes
--
-- Revision 1.9  2004/01/07 00:21:03  dave
-- more scripting
--
-- Revision 1.8  2004/01/01 19:12:05  dave
-- more fixes
--
-- Revision 1.7  2003/12/31 19:54:56  dave
-- refinements
--
-- Revision 1.6  2003/12/22 19:16:35  dave
-- fixes for target to actual withdrawl
--
-- Revision 1.5  2003/12/16 19:51:13  dave
-- upped to 13 for CHAR(8) -- > CHAR (13)
--
-- Revision 1.4  2003/12/16 19:40:15  dave
-- added CHAR(8) to Audience and ww view
--
-- Revision 1.3  2003/12/15 21:07:19  dave
-- more ECCM updates
--
-- Revision 1.2  2003/12/12 16:06:33  dave
-- more eccm updates remove the MARY schema
--
-- Revision 1.1  2003/12/10 20:34:54  dave
-- updating and making final scripts
--

------------------------------------------------
-- CALLED OUT IN SPECFICICATION DOC
-- CONVERTED DWB
-- COMPILED DWB
-- TESTED
-- CHECKED FOR FINAL SCRIPT
-- MADE DROP IMMUNE FROM ODSINIT
------------------------------------------------
-- DDL Statements for table ECCM.PSBFBASE
------------------------------------------------
DROP TABLE ECCM.PSBFBASE;
CREATE TABLE ECCM.PSBFBASE (
 NLSID INT NOT NULL,
 PROJECTID INT NOT NULL,
 SERIESID INT NOT NULL,
 BRANDID INT NOT NULL,
 FAMILYID INT NOT NULL,
 SERIESNAME VARCHAR(50),
 BRANDNAME VARCHAR(50),
 FAMILYNAME VARCHAR(50),
 FAMSERNAME VARCHAR (100)
);
CREATE INDEX ECCM.PSBFBINDEX ON ECCM.PSBFBASE(PROJECTID ASC, NLSID ASC);

DELETE FROM ECCM.PSBFBASE;
INSERT INTO ECCM.PSBFBASE
SELECT
 PR.NLSID as NLSID
,PR.ENTITYID AS PROJECTID
,SE.ENTITYID AS SERIESID
,BR.ENTITYID  AS BRANDID
,FAM.ENTITYID AS FAMILYID
,SE.SERIESNAME AS SERIESNAME
,BR.SUBGROUPNAME AS BRANDNAME4
,FAM.FAMILYNAME AS FAMILYNAME
,FAM.Familyname || '@' || SE.SeriesName as FAMSERNAME
FROM ECCM.pr pr
join ECCM.br br on
    br.brandcode = pr.brandcode and
    br.nlsid = pr.nlsid
join ECCM.fam fam on
    fam.FAMNAMEASSOC_FC = pr.FAMNAMEASSOC_FC and
   fam.nlsid = pr.nlsid
join ECCM.se se on
    se.SENAMEASSOC_FC = pr.SENAMEASSOC_FC and
   se.nlsid = pr.nlsid
where
   pr.nlsid = 1 and
   pr.senameassoc is not null and
   pr.senameassoc != '' and
  1 =  CASE WHEN  fam.familyname in (
'PS/55 Systems','RS/6000 Client','WorkPad','Misc PI','Orphan','NetVista Internet Appliance',
'NetVista Thin Client','Appliance Division','Netfinity',
'Aptiva', 'PC Server','Desktop')
THEN 0
WHEN
  fam.familyname='NetVista' and se.seriesname in ('A60p','A40i','A40','A40p','S40',
'S40p','X40', 'A20','A60','A60i','X40i','A20i','S40i','A10','M41','M41z','A21','A21i',
'M40','M40i','X41', 'X41z','X42z','X42','M42','M42z','A22','A22i','A25','A22p',
'X Series')
THEN 0
WHEN
  fam.familyname='ThinkPad' and se.seriesname in ('235','240','315','365','380','390',
'4XX','535', '560','570','600','730','760','765','770','i Series','MOB Other',
'ThinkPad TransNote','130 Series')
THEN 0
WHEN br.subgroupname = 'Archive' 
THEN 0
ELSE 1
END
;

DROP TABLE ECCM.PROJSERBRANDFAM;
CREATE SUMMARY TABLE ECCM.PROJSERBRANDFAM AS (SELECT * FROM ECCM.PSBFBASE)
DATA INITIALLY DEFERRED REFRESH DEFERRED;
REFRESH TABLE ECCM.PROJSERBRANDFAM;

CREATE INDEX ECCM.PSBFSINDEX ON ECCM.PROJSERBRANDFAM(PROJECTID ASC, NLSID ASC);


------------------------------------------------
-- DOES NOT APPEAR TO BE CALLED OUT IN ANY TABLE SPEC
-- NOR SUPPORTING TABLE
-- Converted DWB
-- COMPILED DWB
-- MADE DROP IMMUNE FROM ODSINIT
-- CHECKED FOR FINAL SCRIPT
-- Added distinct - per MTM Feedback
-- ADDED CVAR SELECT - per MTM fb 53343
------------------------------------------------
-- DDL Statements for table ECCM.PRDMESSAGE
------------------------------------------------
drop table eccm.prdmessage;
create summary table eccm.prdmessage (
  parententitytype
, parententityid
, nlsid
, mmentitytype
, mmentityid
, datefrom
, dateto
, status) AS
(SELECT DISTINCT
 'CSOL'
, csol.entityID
, csol.nlsid
,'MM'
, COALESCE(mm.entityid,mm2.entityid)
, COALESCE(mm.mmfrom,mm2.mmfrom)
, COALESCE(mm.mmto, mm2.mmto)
, COALESCE(mm.mmstatus,mm2.mmstatus)
FROM ECCM.csol csol
LEFT JOIN  ECCM.csolmm csolmm on
    csol.entityid = csolmm.ID1
LEFT JOIN ECCM.mm mm on
    mm.EntityID = csolmm.ID2
and mm.nlsid = csol.nlsid
LEFT JOIN ECCM.ofmm ofmm on
    ofmm.ID1 = csol.ofid
LEFT JOIN ECCM.mm mm2 on
    mm2.EntityID =ofmm.ID2
and mm2.nlsid = csol.nlsid
UNION ALL
SELECT DISTINCT
'CCTO'
,CCTO.EntityID
,CCTO.nlsid
,'MM'
,COALESCE(mm.entityid,mm2.entityid)
,COALESCE(mm.mmfrom,mm2.mmfrom)
,COALESCE(mm.mmto, mm2.mmto)
,COALESCE(mm.mmstatus,mm2.mmstatus)
FROM ECCM.ccto ccto
LEFT JOIN  ECCM.cctomm cctomm on
    ccto.entityid = cctomm.ID1
LEFT JOIN ECCM.mm mm on
    mm.EntityID = cctomm.ID2
and mm.nlsid = ccto.nlsid
LEFT JOIN ECCM.ctomm ctomm on
    ctomm.ID1 = ccto.ctoid
LEFT JOIN ECCM.mm mm2 on
    mm2.EntityID =ctomm.ID2
and mm2.nlsid = ccto.nlsid
UNION ALL
SELECT DISTINCT 'CVAR' ,
 CVAR.EntityID ,
 CVAR.nlsid ,
 'MM' ,
 COALESCE(mm.entityid,mm2.entityid) ,
 COALESCE(mm.mmfrom,mm2.mmfrom) ,
 COALESCE(mm.mmto, mm2.mmto) ,
 COALESCE(mm.mmstatus,mm2.mmstatus)
FROM ECCM.cvar cvar
LEFT JOIN  ECCM.cvarmm cvarmm on
    cvar.entityid = cvarmm.ID1
LEFT JOIN ECCM.mm mm on
    mm.EntityID = cvarmm.ID2 and
    mm.nlsid = cvar.nlsid
LEFT JOIN ECCM.varmm varmm on
    varmm.ID1 = cvar.varid
LEFT JOIN ECCM.mm mm2 on
    mm2.EntityID =varmm.ID2 and
    mm2.nlsid = cvar.nlsid
UNION ALL
SELECT
'CB'
,CB.EntityID
,CB.nlsid
,'MM'
,mm.entityid
,mm.mmfrom
,mm.mmto
,mm.mmstatus
FROM ECCM.cb cb
INNER JOIN  ECCM.cbmm cbmm on
    cb.entityid = cbmm.ID1
INNER JOIN ECCM.mm mm on
    mm.EntityID = cbmm.ID2
and mm.nlsid = cb.nlsid
) DATA INITIALLY DEFERRED REFRESH DEFERRED;
REFRESH TABLE ECCM.PRDMESSAGE;

CREATE INDEX ECCM.MMINDEX001 ON ECCM.PRDMESSAGE (PARENTENTITYID ASC, PARENTENTITYTYPE ASC, NLSID ASC,MMENTITYID ASC) PCTFREE 10 ;


------------------------------------------------
-- CALLED OUT IN THE TABLE SPEC - AND USED IN OTHER VIEWS
--
-- Converted DWB
-- Compiled DWB
-- MADE DROP IMMUNE FROM ODSINIT
-- CHECKED FOR FINAL SCRIPT
------------------------------------------------
-- DDL Statements for table ECCM.PRODUCTCOUNTRY
------------------------------------------------
drop table eccm.productcountry;
create summary table eccm.productcountry
(parententitytype, parententityid, nlsid,ctid, countrycode) as
(SELECT 'CSOL' ,
  csol.entityid ,
  csol.nlsid ,
  ga.entityid ,
  ga.genareacode
 FROM ECCM.csol csol
 JOIN ECCM.generalarea ga on ga.genareaname = csol.genareaname and ga.genareatype='Cty'
 JOIN ECCM.psgctnls ct on ct.countrycode=ga.genareacode
 UNION ALL
 SELECT 'CVAR' ,
  cvar.entityid ,
  cvar.nlsid ,
  ga.entityid ,
  ga.genareacode
 FROM ECCM.cvar cvar
 JOIN ECCM.generalarea ga on ga.genareaname = cvar.genareaname and ga.genareatype='Cty'
 JOIN ECCM.psgctnls ct on ct.countrycode=ga.genareacode
 UNION ALL
 SELECT 'CCTO' ,
  ccto.entityid ,
  ccto.nlsid ,
  ga.entityid ,
  ga.genareacode
 FROM ECCM.ccto ccto
 JOIN ECCM.generalarea ga on ga.genareaname = ccto.genareaname and ga.genareatype='Cty'
 JOIN ECCM.psgctnls ct on ct.countrycode=ga.genareacode
 UNION ALL
 SELECT 'CB ' ,
  cb.entityid ,
  cb.nlsid ,
  ga.entityid ,
  ga.genareacode
 FROM ECCM.cb cb
 JOIN ECCM.generalarea ga on ga.genareaname = cb.genareaname and ga.genareatype='Cty'
 JOIN ECCM.psgctnls ct on ct.countrycode=ga.genareacode
)
DATA INITIALLY DEFERRED REFRESH DEFERRED;
REFRESH TABLE ECCM.PRODUCTCOUNTRY;

CREATE INDEX ECCM.PCINDEX001 ON ECCM.PRODUCTCOUNTRY (PARENTENTITYID ASC, PARENTENTITYTYPE ASC, NLSID ASC) PCTFREE 10;
CREATE INDEX ECCM.PRODUCTCOUNTRY_I2 ON ECCM.PRODUCTCOUNTRY (CTID ASC) PCTFREE 10 CLUSTER;
CREATE INDEX ECCM.XIE3PRODUCTCOUNTRY ON ECCM.PRODUCTCOUNTRY (PARENTENTITYID ASC, NLSID ASC, COUNTRYCODE ASC, PARENTENTITYTYPE ASC);


------------------------------------------------
-- CALLED OUT IN THE TABLE SPEC
-- Converted DWB
-- Compiled DWB
-- MADE DROP IMMUNE FROM ODSINIT
-- CHECKED FOR FINAL SCRIPT
------------------------------------------------
-- DDL Statements for table ECCM.CATIMAGE
------------------------------------------------
drop table eccm.catimage;
create summary table eccm.catimage (
   parententitytype
 , parententityid
 , nlsid
 , imageid
 , displayname
 , colorimagename
 , datefrom
 , dateto
 , status ) as
 (
  SELECT
   'FAM'
   ,fam.entityid
   ,fam.nlsid
   ,img.entityid
   ,img.IMAGEDESCRIPTION
   ,img.MKT_IMG_FILENAME
   ,img.imgfrom
   ,img.imgto
   ,img.imgstatus
   from ECCM.fam fam
   join ECCM.famimg famimg on
       famimg.ID1 = fam.entityid
   join ECCM.img img on
       img.EntityID = famimg.ID2
   and  img.nlsid = fam.nlsid

   where       img.imgstatus_fc ='0020' and
              img.MKT_IMG_FILENAME is not null
   UNION ALL

    select
   'SE'
   ,se.entityid
   ,se.nlsid
   ,img.entityid
   ,img.IMAGEDESCRIPTION
   ,img.MKT_IMG_FILENAME
   ,img.imgfrom
   ,img.imgto
   ,img.imgstatus
   from ECCM.se se
   join ECCM.seimg seimg on
       seimg.ID1 = se.entityid
   join ECCM.img img on
       img.EntityID = seimg.ID2
   and  img.nlsid = se.nlsid
   where       img.imgstatus_fc ='0020' and
              img.MKT_IMG_FILENAME is not null

  ) DATA INITIALLY DEFERRED REFRESH DEFERRED ;
REFRESH TABLE ECCM.CATIMAGE;
CREATE INDEX ECCM.CATIMINDEX001 ON ECCM.CATIMAGE (PARENTENTITYTYPE ASC, PARENTENTITYID ASC, IMAGEID ASC) PCTFREE 10;


------------------------------------------------
-- CALLED OUT IN THE TABLE SPEC
-- CONVERTED DWB
-- COMPILED DWB
-- MADE DROP IMMUNE FROM ODSINIT
-- CHECKED FOR FINAL SCRIPT
-- GAB : Added per FB#52929
-----------------------------------------------
-- DDL Statements for table ECCM.ATTRWITHUNIT
------------------------------------------------
DROP TABLE ECCM.ATTRWITHUNIT;
 create summary table eccm.attrwithunit as
 ( select
    attr1.attributecode as attrcode
  , attr1.attributetoken as attrtoken
  , attr2.attributecode as attrunitcode
  , attr2.attributetoken as attrunittoken
  from eccm.attribute attr1
  left outer join eccm.attrunits attrunits on
      attrunits.attributetoken = attr1.attributetoken
  left join eccm.attribute attr2 on
      attr2.attributetoken  = attrunits.attributeunittoken
where attr1.attributetoken not in (select attributeunittoken from eccm.attrunits)
) Data initially deferred refresh deferred;
commit work;
refresh table eccm.attrwithunit;

-- DDL Statements for indexes on Table ECCM.ATTRWITHUNIT

CREATE INDEX ECCM.ATTRWITHUNIT_IX1 ON ECCM.ATTRWITHUNIT(ATTRTOKEN,ATTRUNITTOKEN);
create index eccm.attrwithunit_ix2 on eccm.attrwithunit(ATTRCODE);

------------------------------------------------
-- NOT USED FROM WHAT I CAN GATHER
-- Converted DWB
-- Compiled DWB
-- MADE DROP IMMUNE FROM ODSINIT
-- CHECKED FOR FINAL SCRIPT
------------------------------------------------
-- DDL Statements for table ECCM.COUNTRYREGION
------------------------------------------------
drop table eccm.countryregion;
create summary table eccm.countryregion (ctid, nlsid ,countrycode ,regioncode) as (
select
 ga.entityid
,ga.nlsid
,ga.GENAREACODE
,ga.GENAREAPARENT
FROM ECCM.generalarea ga
WHERE
  ga.genareatype = 'Cty'

) DATA INITIALLY DEFERRED REFRESH DEFERRED;
refresh table eccm.countryregion;
CREATE INDEX ECCM.CRINDEX001 ON ECCM.COUNTRYREGION(CTID ASC);


------------------------------------------------
-- CALLED OUT IN THE TABLE SPEC
-- Converted DWB
-- COMPILED DWB
-- MTM CHANGED
-- MADE DROP IMMUNE FROM ODSINIT
-- CHECKED FOR FINAL SCRIPT
------------------------------------------------
-- DDL Statements for table ECCM.CBUNSPSC
------------------------------------------------
drop table eccm.cbunspsc;
create summary table eccm.cbunspsc (entitytype, entityid,nlsid, unspsc) as
(
with tof (cbid, cbnlsid, type, ofid, count) as (

select    cb.entityid,
          cb.nlsid,
          case of.offeringtype when 'SYSTEM' then 'SYSTEM' else 'OPTION' end,
          max(of.entityid),
          count(distinct of.entityid)

from      eccm.cb cb

join      eccm.cbcsol cbcsol on
          cbcsol.id1 = cb.entityid

join      eccm.csol csol on
          csol.entityid = cbcsol.id2 and
          csol.nlsid = cb.nlsid

join      eccm.of of on
          of.entityid = csol.ofid and
          of.nlsid = csol.nlsid

group by  cb.entityid,
          cb.nlsid,
          of.offeringtype

)
--
-- Produces SYSTEM or OPTION recs
--
, t1of (cbid, cbnlsid, type, ofid, count) as
(
select    cbid,
          cbnlsid,
          type,
          max(ofid),
          sum(count)

from      tof

group
by        cbid,
          cbnlsid,
          type
)
--
--  CB's w/ Systems on them
--
,tsys (cbid, cbnlsid, type, ofid)  as (

select
distinct  cbid,
          cbnlsid,
          type,
          ofid

from      t1of t1

where t1.type = 'SYSTEM'
)

,tvar (cbid, cbnlsid, varid,count) as (

select    cb.entityid,
          cb.nlsid,
          max(var.entityid),
          count(distinct var.entityid)
from      eccm.cb cb

join      eccm.cbcvar cbcvar on
          cbcvar.id1 = cb.entityid

join      eccm.cvar cvar on
          cvar.entityid = cbcvar.id2 and
          cvar.nlsid = cb.nlsid

join      eccm.var var on
          var.entityid = cvar.varid and
          var.nlsid = cvar.nlsid
where     (cb.entityid, cb.nlsid) not in (select cbid, cbnlsid from tsys)

group by  cb.entityid,
          cb.nlsid
)

-- CBs WITH AT LEAST ONE SYSTEM
select    'CB',
          cbid,
          cbnlsid,
          of.unspsc
from      tsys  tsys
join      eccm.of of on
          of.entityid =  tsys.ofid and
          of.nlsid = tsys.cbnlsid
union all
-- CBs w/ No MTM but Variant
select    'CB',
          cbid,
          cbnlsid,
          var.unspsc
from      tvar  tvar
join      eccm.var var on
          var.entityid =  tvar.varid and
          var.nlsid = tvar.cbnlsid

union all

-- OPTIONS ONLY Section
select    'CB',
          cbid,
          cbnlsid,
          coalesce(of.unspsc,'43171800')
from      t1of t1
join      eccm.of of on
          of.entityid =  t1.ofid and
          of.nlsid = t1.cbnlsid
where    (t1.cbid, t1.cbnlsid) not in (select cbid, cbnlsid from tsys) and
         (t1.cbid, t1.cbnlsid) not in (select cbid, cbnlsid from tvar)

) DATA INITIALLY DEFERRED REFRESH DEFERRED;
refresh table eccm.cbunspsc;

CREATE INDEX ECCM.CBUX0001 ON ECCM.CBUNSPSC (ENTITYID ASC,ENTITYTYPE ASC, nlsid ASC);


-----------------------------------------------
-- CALLED OUT IN THE SPEC
-- ADDED NLSID
-- CONVERTED DWB
-- COMPILED DWB
-- MADE DROP IMMUNE FROM ODSINIT
-- CHECKED FOR FINAL SCRIPT
-- ADDED RTRIMS
------------------------------------------------
-- DDL Statements for table ECCM.PSGSERVICETYPE
------------------------------------------------
drop table eccm.psgservicetype;
create summary table eccm.psgservicetype
(  entitytype,
   entityid,
   nlsid,
   flagcode

) AS (

select 'CSOL',
        csolser.ID1,
        ser.nlsid,
        rtrim(SER.SERVICETYPE_FC)

from    ECCM.csolser csolser

join    ECCM.ser ser on
        ser.entityid = csolser.ID2

union all

select 'OF',
        ofser.ID1,
        ser.nlsid,
        rtrim(SER.SERVICETYPE_FC)

from    ECCM.ofser ofser

join    ECCM.ser ser on
        ser.entityid = ofser.ID2

union all

select 'SBB',
        sbbser.ID1,
        ser.nlsid,
        rtrim(SER.SERVICETYPE_FC)

from    ECCM.sbbser sbbser

join    ECCM.ser ser on
        ser.entityid = sbbser.ID2

) DATA INITIALLY DEFERRED REFRESH DEFERRED;
REFRESH TABLE ECCM.PSGSERVICETYPE;

CREATE INDEX ECCM.PSTYPE ON ECCM.PSGSERVICETYPE (ENTITYID ASC, ENTITYTYPE ASC, NLSID ASC);

------------------------------------------------
-- USED TO SUPPORT OTHER TABLES
-- Converted DWB
-- Compiled DWB
-- MADE DROP IMMUNE FROM ODSINIT
-- CHECKED FOR FINAL SCRIPT
-- New version from MTM
-- CHANGED 0120 to 10054 and 0200 to 10062 for
-- CVAR catalog
-- ADDED CHAR 8
------------------------------------------------
-- DDL Statements for table ECCM.AUDIENCE
------------------------------------------------
DROP TABLE eccm.AUDIENCE;
CREATE SUMMARY TABLE eccm.AUDIENCE
(
  nlsid,
  entityname,
  entityid,
  columnname,
  longdescription
) AS (

  select    csol.nlsid as nlsid,
            'CSOL' as entityname,
            csol.entityid as entityid,
            f.attributecode as columnname,
            CHAR(case f.flagcode
              when '0120' then 'SHOP'
              when '0200' then 'LE'
            end,13) as longdescription

  from      ECCM.csol csol

  join      ECCM.FLAG f on
            f.entityid = csol.entityid and
            f.entitytype = 'CSOL' and
            f.attributecode = 'CATALOG_NAME_CT' and
            f.flagcode in ('0120','0200') and
            f.nlsid = csol.nlsid

  UNION
  ALL

  select    ccto.nlsid as nlsid,
            'CCTO' as entityname,
            ccto.entityid as entityid,
            f.attributecode as columnname,
            CHAR (case f.flagcode
              when '0120' then 'SHOP'
              when '0200' then 'LE'
            end,13) as longdescription

  from      ECCM.ccto ccto

  join      ECCM.FLAG f on
            f.entityid = ccto.entityid and
            f.entitytype = 'CCTO' and
            f.attributecode = 'CCOSOLCATALOGNAME' and
            f.flagcode in ('0120','0200') and
            f.nlsid = ccto.nlsid

  UNION
  ALL

  select    cb.nlsid as nlsid,
            'CB' as entityname,
            cb.entityid as entityid,
            f.attributecode as columnname,
            CHAR(case f.flagcode
              when '0120' then 'SHOP'
              when '0200' then 'LE'
            end,13) as longdescription

  from      ECCM.cb cb

  join      ECCM.FLAG f on
            f.entityid = cb.entityid and
            f.entitytype = 'CB' and
            f.attributecode = 'CATALOG_NAME_CB' and
            f.flagcode in ('0120','0200') and
            f.nlsid = cb.nlsid

  UNION
  ALL

  select    cvar.nlsid as nlsid,
            'CVAR' as entityname,
            cvar.entityid as entityid,
            f.attributecode as columnname,
            CHAR(case f.flagcode
              when '10054' then 'SHOP'
              when '10062' then 'LE'
            end,13) as longdescription

  from      ECCM.cvar cvar

  join      ECCM.FLAG f on
            f.entityid = cvar.entityid and
            f.entitytype = 'CVAR' and
            f.attributecode = 'CATALOGNAME_CVAR' and
            f.flagcode in ('10054','10062') and
            f.nlsid = cvar.nlsid
  UNION
  ALL

  select    csol.nlsid as nlsid,
            'CSOL' as entityname,
            csol.entityid as entityid,
            f.attributecode as columnname,
            CHAR(CASE f.flagcode
              when '0250' THEN 'DACMAX'
            END,13)

  from      eccm.csol csol

  join      eccm.flag f on
            f.entityid=csol.entityid and
            f.entitytype='CSOL' and
            f.attributecode='CATALOG_NAME_CT' and
            f.flagcode='0250' and
            csol.nlsid=1

  where     csol.entityid not in
            (select entityid from eccm.flag where flagcode in('0120','0200')
            and entitytype='CSOL' and attributecode='CATALOG_NAME_CT' and nlsid=1)

  UNION ALL

  select    csol.nlsid as nlsid,
            'CSOL' as entityname,
            csol.entityid as entityid,
            f.attributecode as columnname,
            CHAR(case f.flagcode
              when '0250' then 'LE and DACMAX'
            end,13)

  from      eccm.csol csol

  join      eccm.flag f on
            f.entityid=csol.entityid and
            f.entitytype='CSOL' and
            f.attributecode='CATALOG_NAME_CT' and
            f.flagcode='0250' and
            csol.nlsid=1

  where     csol.entityid not in
            (select entityid from eccm.flag where flagcode in('0120') and
            entitytype='CSOL' and attributecode='CATALOG_NAME_CT' and nlsid=1)
            and csol.entityid in
            (select entityid from eccm.flag
            where flagcode in('0200') and entitytype='CSOL'
            and attributecode='CATALOG_NAME_CT' and nlsid=1)

) DATA INITIALLY DEFERRED REFRESH DEFERRED ;

REFRESH TABLE eccm.AUDIENCE;

-- DDL Statements for indexes on Table ECCM.AUDIENCE

CREATE INDEX eccm.AUDIENCEIX1 ON eccm.AUDIENCE
  (ENTITYID ASC,
   ENTITYNAME ASC,
   NLSID ASC)
  PCTFREE 10 ;
  



-------------------------------------------------------------
-- CONVERTED DWB
-- COMPILED DWB
-- DOCUMENTATION REFERS TO DELTAFORCE?
-- CANNOT FIND THAT VIEW HERE
-- MADE DROP IMMUNE FROM ODSINIT
-- CHECKED FOR FINAL SCRIPT
-- New version from MTM -cleaned up 53185
-- More changes from MTM - missing prefix - sub select wrong
-- FB 53654  coalesce(cpgos.CPGOSNAME,'-') change to CPGOPSYS
------------------------------------------------
-- DDL Statements for table ECCM.COMPAT
-- This should be NLSID 1 only because we are only
-- Reoporting on partnumbers
--
-- What is the purpose of joining data to the int table avwwprd
------------------------------------------------
DROP TABLE ECCM.COMPAT;
create summary table eccm.compat
(
  entitytype,
  wwpartnum,
  optionpnumb,
  mtm,
  operatingsys,
  exceptmodel
)
  as
    (
with avwwprd_tmp (entityid) as
 (
    select
    distinct    csol.ofid

    from        ECCM.csol csol

    join        int.avwwprd prd on
                prd.prdentityid = csol.entityid and
                prd.prdnlsid = csol.nlsid and 
                prd.prdentitytype = 'CSOL'

 ),

 avwwprd_tmp2 (entityid) as
  (
    select
    distinct    ccto.ctoid

    from        ECCM.ccto ccto 
    
    join        int.avwwprd prd on
                prd.prdentityid = ccto.entityid and
                prd.prdnlsid = ccto.nlsid and
                prd.prdentitytype = 'CCTO'


 ),

 avwwprd_tmp3(entityid) as
  (
    select
    distinct    varcvar.id1

    from        ECCM.cvar cvar
    
    join        int.avwwprd prd on
                prd.prdentityid = cvar.entityid and
                prd.prdnlsid = cvar.nlsid and 
                prd.prdentitytype = 'CVAR'

    join        ECCM.varcvar varcvar on
                varcvar.ID2 = cvar.entityid

 )
(

  select
  distinct      'PSGOFCMPOF',
                of1.OFFERINGPNUMB,
                of2.OFFERINGPNUMB,
                '-',
                '-',
                '-'

  from          ECCM.ofcmpof ofcmpof

  join          ECCM.of of1 on
                of1.entityid = ofcmpof.ID1 and
                of1.nlsid = 1

  join          ECCM.of of2 on
                of2.entityid = ofcmpof.ID2 and
                of2.nlsid = 1

  join          avwwprd_tmp tmp1 on
                tmp1.entityid = of1.entityid

  join          avwwprd_tmp tmp2 on
                tmp2.entityid = of2.entityid

  UNION ALL

  select
  distinct      'PSGOFCMPOF',
                of2.OFFERINGPNUMB,
                of1.OFFERINGPNUMB,
                '-',
                '-',
                '-'

  from          ECCM.ofcmpof ofcmpof

  join          ECCM.of of1 on
                of1.entityid = ofcmpof.ID1 and
                of1.nlsid = 1

  join          ECCM.of of2 on
                of2.entityid = ofcmpof.ID2 and
                of2.nlsid = 1

  join          avwwprd_tmp tmp1 on
                tmp1.entityid = of1.entityid

  join          avwwprd_tmp tmp2 on
                tmp2.entityid = of2.entityid

  )

UNION ALL

select
distinct    'PSGMTOSOF',
            of.OFFERINGPNUMB,
            '-',
            cpg.MACHTYPE,
            coalesce(cpgos.CPGOPSYS,'-'),
            '-'

from        ECCM.cpgos cpgos

join        ECCM.cpgcpgos cpgcpgos on
            cpgcpgos.id2 = cpgos.entityid

join        ECCM.cpg cpg on
            cpg.entityid = cpgcpgos.id1 and
            cpg.nlsid = 1

join        ECCM.ofcpgos ofcpgos on
            ofcpgos.id2 = cpgos.entityid

join        ECCM.of of on
            of.entityid = ofcpgos.id1 and
            of.nlsid = 1

join        avwwprd_tmp tmp on
            tmp.entityid = of.entityid

UNION
ALL

select
distinct    'PSGMTOSOF',
            of.OFFERINGPNUMB,
            '-',
            cpg.MACHTYPE,
            '-',
            '-'

from        ECCM.of of

join        ECCM.cpgof cpgof on
            cpgof.ID2 = of.entityid

join        ECCM.cpg cpg on
            cpg.entityid = cpgof.id1 and
            cpg.nlsid = 1

join        avwwprd_tmp tmp on
            tmp.entityid = of.entityid

where       of.entityid not in (select id1 from ECCM.ofcpgos) and
            of.nlsid = 1

UNION
ALL

select
distinct    'PSGMTOSOF',
            cto.COFPNUMB,
            '-',
            cpg.MACHTYPE,
            '-',
            '-'

from        ECCM.cto cto

join        ECCM.cpgcto cpgcto on
            cpgcto.id2 = cto.entityid

join        ECCM.cpg cpg on
            cpg.entityid = cpgcto.id1 and
            cpg.nlsid = 1

join        avwwprd_tmp2 tmp on
            tmp.entityid = cto.entityid

where       cto.nlsid = 1

UNION
ALL

select
distinct    'PSGMTOSOF',
            var.offeringPNUMB,
            '-',
            cpg.MACHTYPE,
            '-',
            '-'

from        ECCM.var var

join        ECCM.cpgvar cpgvar on
            cpgvar.id2 = var.entityid

join        ECCM.cpg cpg on
            cpg.entityid = cpgvar.id1 and
            cpg.nlsid = 1
            
join        avwwprd_tmp3 tmp on
            tmp.entityid = var.entityid

where       var.nlsid = 1

  ) DATA INITIALLY DEFERRED REFRESH DEFERRED
  ;
  REFRESH TABLE ECCM.COMPAT;


-----------------------------------------------
-- Used to support SPECT TABLES
-- Converted DWB
-- COMPILED DWB
-- CHECKED FOR FINAL SCRIPT
-- MADE DROP IMMUNE FROM ODSINIT
-- Mod from MTM
-- CVAR DATE INHERITECE FIX FROM MTM
------------------------------------------------
-- DDL Statements for table ECCM.SBBALLDATES
------------------------------------------------
 DROP TABLE ECCM.sbbAllDates;
 CREATE SUMMARY TABLE eccm.sbballdates
 (
   entitytype,
   entityid,
   nlsid,
   sbbid,
   anndate,
   wdate
) as (

  with sbbctydate_tmp (entitytype,entityid,nlsid,sbbid,anndate,wdate)
  as (

    select      'CCTO',
                a.entityid,
                a.nlsid,
                b.id2,
                b.sbbpublishctdate,
                b.sbbunpublishctdate

    from        eccm.ccto a,
                eccm.cctosbb b ,
                eccm.sbb c

    where       a.entityid=b.id1 and
                b.id2=c.entityid and
                c.nlsid=a.nlsid

    union all

    select      'CVAR',
                a.entityid,
                a.nlsid,
                b.entityid,
                b.sbbannouncetgt,
                b.sbbwithdrawldate

    from        eccm.cvar a,
                eccm.varsbb d,
                eccm.sbb b

    where       d.id1=a.varid and
                d.id2=b.entityid and
                a.nlsid=b.nlsid
  ),

  sbbwwdate_tmp (entitytype,entityid,nlsid, sbbid,anndate,wdate)
  as (

    select      'CCTO',
                a.entityid,
                a.nlsid,
                f.id2,
                b.sbbpublishwwdate,
                b.sbbunpublishwwdate

    from        eccm.ccto a,
                eccm.cto c,
                eccm.ctosbb b,
                eccm.cctosbb f ,
                eccm.sbb g

    where       a.ctoid = c.entityid and
                a.nlsid=c.nlsid and
                a.entityid=f.id1 and
                f.id2=b.id2 and
                c.entityid=b.id1 and
                g.entityid=b.id2 and
                g.nlsid=c.nlsid

  ),

  sbbdate_tmp (entitytype,entityid,nlsid,sbbid,anndate,wdate)
  as (

    select      'CCTO',
                a.entityid,
                a.nlsid,
                b.entityid,
                b.sbbannouncetgt,
                b.sbbwithdrawldate

    from        eccm.ccto a,
                eccm.cctosbb c,
                eccm.sbb b

    where       a.entityid=c.id1 and
                b.entityid=c.id2
                and a.nlsid=b.nlsid
  )

  select
  distinct    a.entitytype,
              a.entityid,
              a.nlsid,
              a.sbbid,
              coalesce(a.anndate,c.anndate,d.anndate),
              coalesce(a.wdate,c.wdate,d.wdate)

  from        sbbctydate_tmp a

  left join   sbbwwdate_tmp c on
              a.entitytype=c.entitytype and
              a.entityid=c.entityid and
              a.nlsid=c.nlsid and
              a.sbbid=c.sbbid

  left join   sbbdate_tmp d on
              a.entitytype=d.entitytype and
              a.entityid=d.entityid and
              a.nlsid=d.nlsid and
              a.sbbid=d.sbbid

)
DATA INITIALLY DEFERRED REFRESH DEFERRED
;
refresh table eccm.sbballdates;
CREATE INDEX ECCM.SBBALLDATESIDX ON ECCM.SBBALLDATES(ENTITYID ASC, ENTITYTYPE ASC, NLSID ASC, SBBID ASC) PCTFREE 10 ;


------------------------------------------------
-- CONVERTED MTM
-- COMPILED DWB
-- CHECKED FOR FINAL SCRIPT
-- MADE DROP IMMUNE FROM ODSINIT
-- ADDED MORE UNION FOR CVAR MTM
------------------------------------------------
-- DDL Statements for table ECCM.PRDIMAGE
------------------------------------------------
drop table eccm.prdimage;
create summary table eccm.prdimage
(
  parententitytype,
  parententityid,
  parentnlsid,
  imageid,
  displayname,
  colorimagename,
  datefrom,
  dateto,
  status
) as (

  (select      'CSOL',
              csol.entityid,
              csol.nlsid,
              img.entityid,
              img.name,
              img.MKT_IMG_FILENAME,
              img.imgfrom,
              img.imgto,
              img.imgstatus

  from        eccm.csol csol

  join        eccm.csolimg csolimg on
              csol.entityid=csolimg.ID1

  join        eccm.img img on
              img.entityid=csolimg.ID2 and
              img.nlsid=csolimg.nlsid

  where       img.imgstatus_fc ='0020' and
              img.MKT_IMG_FILENAME is not null

  union

  select      'CSOL',
              csol.entityid,
              csol.nlsid,
              img.entityid,
              img.name,
              img.MKT_IMG_FILENAME,
              img.imgfrom,
              img.imgto,
              img.imgstatus

  from        eccm.csol csol

  join        eccm.ofimg ofimg on
              csol.ofid=ofimg.ID1

  join        eccm.img img on
              img.entityid=ofimg.ID2 and
              img.nlsid=csol.nlsid

  where       img.imgstatus_fc ='0020' and
              img.MKT_IMG_FILENAME is not null

  )
  UNION ALL

  (
    select      'CCTO',
              ccto.entityid,
              ccto.nlsid,
              img.entityid,
              img.name,
              img.MKT_IMG_FILENAME,
              img.imgfrom,
              img.imgto,
              img.imgstatus

  from        eccm.ccto ccto

  join        eccm.cctoimg cctoimg on
              ccto.entityid=cctoimg.ID1

  join        eccm.img img on
              img.entityid=cctoimg.ID2 and
              img.nlsid=cctoimg.nlsid

  where       img.imgstatus_fc='0020' and
              img.MKT_IMG_FILENAME is not null

  union

  select      'CCTO',
              ccto.entityid,
              ccto.nlsid,
              img.entityid,
              img.name,
              img.MKT_IMG_FILENAME,
              img.imgfrom,
              img.imgto,
              img.imgstatus

  from        eccm.ccto ccto

  join        eccm.ctoimg ctoimg on
              ccto.ctoid=ctoimg.ID1

  join        eccm.img img on
              img.entityid=ctoimg.ID2 and img.nlsid=ccto.nlsid

  where       img.imgstatus_fc='0020' and
              img.MKT_IMG_FILENAME is not null

  )
  UNION ALL

  (
    select      'CVAR',
              cvar.entityid,
              cvar.nlsid,
              img.entityid,
              img.name,
              img.MKT_IMG_FILENAME,
              img.imgfrom,
              img.imgto,
              img.imgstatus

  from        eccm.cvar cvar

  join        eccm.cvarimg cvarimg on
              cvarimg.ID1=cvar.entityid

  join        eccm.img img on
              img.entityid=cvarimg.ID2 and
              img.nlsid=cvar.nlsid

  where       img.imgstatus_fc='0020' and
              img.MKT_IMG_FILENAME is not null

  UNION

  select      'CVAR',
              cvar.entityid,
              cvar.nlsid,
              img.entityid,
              img.name,
              img.MKT_IMG_FILENAME,
              img.imgfrom,
              img.imgto,
              img.imgstatus

  from        eccm.cvar cvar

  join        eccm.varimg varimg on
              varimg.ID1=cvar.varid

  join        eccm.img img on
              img.entityid=varimg.ID2 and
              img.nlsid=cvar.nlsid

  where       img.imgstatus_fc ='0020' and
              img.MKT_IMG_FILENAME is not null
  
  UNION
  
  select      'CVAR',
              cvar.entityid,
              cvar.nlsid,
              img.entityid,
              img.name,
              img.MKT_IMG_FILENAME,
              img.imgfrom,
              img.imgto,
              img.imgstatus
  
  from        eccm.cvar cvar
  
  join        eccm.ctovar ctovar on
              id2=cvar.varid
  
  join        eccm.ctoimg ctoimg on
              ctoimg.ID1=ctovar.id1
  
  join        eccm.img img on
              img.entityid=ctoimg.ID2 and
              img.nlsid=cvar.nlsid
  
  where       img.imgstatus_fc ='0020' and
              img.MKT_IMG_FILENAME is not null
  )

  UNION ALL

  select      'CB',
              cb.entityid,
              cb.nlsid,
              img.entityid,
              img.name,
              img.MKT_IMG_FILENAME,
              img.imgfrom,
              img.imgto,
              img.imgstatus

  from        eccm.cb cb

  join        eccm.cbimg cbimg on
              cb.entityid = cbimg.ID1

  join        eccm.img img on
              img.entityid = cbimg.ID2 and
              img.nlsid = cb.nlsid

  where       img.imgstatus_fc ='0020' and
              img.MKT_IMG_FILENAME is not null
 ) DATA INITIALLY DEFERRED REFRESH DEFERRED ;
refresh table eccm.prdimage;

CREATE INDEX ECCM.WZ2PRDIMAGE ON ECCM.PRDIMAGE (PARENTENTITYTYPE DESC, PARENTENTITYID DESC);

-------------------------------------------------
-- Used to support Other required views and tables
-- Converted DWB
-- Compiled DWB
-- ADDed CHildType, CHild ID to help out other views
-- CHANGED BY MTM
-- NEED TO SEE HOW TO SIMPLIFY THIS
-- CHECKED FOR FINAL SCRIPT
-- MADE DROP IMMUNE FROM ODSINIT
-- REMOVED NLSID JOIN PREDICATE ON ECCM.PROJSERBRANDFAM PER MTM
-- Implementing FB 53305
-----------------------------------------------
-- DDL Statements for table ECCM.CBPARTS
------------------------------------------------
drop table eccm.cbparts;
create summary table eccm.cbparts (
  entitytype,
  entityid,
  nlsid,
  catid,
  PROJECTID,
  BRANDNAME,
  FAMILYNAME,
  SERIESNAME

) as (

with badparts (cbid) as (
select    cb.entityid
from      eccm.csol csol join eccm.cbcsol cbcsol on csol.entityid=cbcsol.id2
join   eccm.cb cb on cb.entityid=cbcsol.id1 and
   cb.nlsid=csol.nlsid
where     (csol.act_wdrawdate_ct is not null and
   csol.act_wdrawdate_ct < date(current timestamp))
union
select    cb.entityid
from   eccm.cvar cvar join eccm.cbcvar cbcvar on cvar.entityid=cbcvar.id2
join   eccm.cb cb on cb.entityid=cbcvar.id1 and
   cb.nlsid=cvar.nlsid
where     (cvar.actwdrawdate_cvar is not null and
   cvar.actwdrawdate_cvar < date(current timestamp)))

, tof (cbid, cbnlsid, type, ofid, count) as (

select    cb.entityid,
          cb.nlsid,
          case of.offeringtype when 'SYSTEM' then 'SYSTEM' else 'OPTION' end,

          max(of.entityid),
          count(distinct of.entityid)

from      eccm.cb cb

join      eccm.cbcsol cbcsol on
          cbcsol.id1 = cb.entityid

join      eccm.csol csol on
          csol.entityid = cbcsol.id2 and
          csol.nlsid = cb.nlsid

join      eccm.of of on
          of.entityid = csol.ofid and
          of.nlsid = csol.nlsid

group by  cb.entityid,
          cb.nlsid,
          of.offeringtype




)
--
-- Produces SYSTEM or OPTION recs
--
, t1of (cbid, cbnlsid, type, ofid, count) as
(
select    cbid,
          cbnlsid,
          type,
          max(ofid),
          sum(count)

from      tof

group
by        cbid,
          cbnlsid,
          type
)
--
--  CB's w/ Systems on them
--
,tsys (cbid, cbnlsid, type, ofid)  as (

select
distinct  cbid,
          cbnlsid,
          type,
          ofid

from      t1of t1

where t1.type = 'SYSTEM'
)

--
--  CB's w/ Variants (but no MTMs)
--
,tvar (cbid, cbnlsid, varid,count) as (

select    cb.entityid,
          cb.nlsid,
          max(var.entityid),
          count(distinct var.entityid)
from      eccm.cb cb

join      eccm.cbcvar cbcvar on
          cbcvar.id1 = cb.entityid

join      eccm.cvar cvar on
          cvar.entityid = cbcvar.id2 and
          cvar.nlsid = cb.nlsid

join      eccm.var var on
          var.entityid = cvar.varid and
          var.nlsid = cvar.nlsid
where     (cb.entityid, cb.nlsid) not in (select cbid, cbnlsid from tsys)


group by  cb.entityid,
          cb.nlsid
)

-- CBs WITH AT LEAST ONE SYSTEM
select    'CB',
          cbid,
          cbnlsid,
          fam.famsername,
          fam.projectid,
          fam.brandname,
          fam.familyname,
          fam.seriesname
from      tsys  tsys
join      eccm.of of on
          of.entityid =  tsys.ofid and
          of.nlsid = tsys.cbnlsid
join      eccm.psbfbase fam on
          fam.projectid = of.prid

where   cbid not in (select cbid from badparts)

union all

-- CBs w/ No MTM but Variant
select    'CB',
          cbid,
          cbnlsid,
          fam.famsername,
          fam.projectid,
          fam.brandname,
          fam.familyname,
          fam.seriesname
from      tvar  tvar
join      eccm.var var on
          var.entityid =  tvar.varid and
          var.nlsid = tvar.cbnlsid
join      eccm.psbfbase fam on
          fam.projectid = var.prid

where   cbid not in (select cbid from badparts)

union all
-- OPTIONS ONLY Section
select        'CB',
          cbid,
          cbnlsid,
          case when of.subgroup is null then fam.famsername || '@' || of.optgroupname
               else fam.famsername || '@' || of.optgroupname || '@' || of.subgroup
          end,
          fam.projectid,
          fam.brandname,
          fam.familyname,
          fam.seriesname
from      t1of t1
join      eccm.of of on
          of.entityid =  t1.ofid and
          of.nlsid = t1.cbnlsid
join      eccm.psbfbase fam on
          fam.projectid = of.prid

where     (cbid,cbnlsid) not in (select cbid, cbnlsid from tsys) and
          (cbid,cbnlsid) not in (select cbid, cbnlsid from tvar) and
   cbid not in (select cbid from badparts) and
          of.optgroupname is not null
--
union all
-- OPTIONS THAT HAVE NULL OPTGROUPNAME
select        'CB',
          cbid,
          cbnlsid,
          fam.famsername,
          fam.projectid,
          fam.brandname,
          fam.familyname,
          fam.seriesname
from      t1of t1
join      eccm.of of on
          of.entityid =  t1.ofid and
          of.nlsid = t1.cbnlsid
join      eccm.psbfbase fam on
          fam.projectid = of.prid

where     (cbid,cbnlsid) not in (select cbid, cbnlsid from tsys) and
          (cbid,cbnlsid) not in (select cbid, cbnlsid from tvar) and
   cbid not in (select cbid from badparts) and
          of.optgroupname is null


)DATA INITIALLY DEFERRED REFRESH DEFERRED;
commit work;
refresh table eccm.cbparts;

CREATE INDEX ECCM.CBPARTSIDX ON ECCM.CBPARTS  (ENTITYID ASC, ENTITYTYPE ASC, NLSID ASC) PCTFREE 10;

------------------------------------------------
-- SUPPORTS THE TABLE SPEC TABLES
-- CONVERTED DWB
-- COMPILED DWB
-- FIX FROM MTM
-- ADDED PUBLISH FLAG
-- CHECKED FOR FINAL SCRIPT
-- MADE DROP IMMUNE FROM ODSINIT
-- CHANGED TARGET TO ACT FOR CCTO AND CSOL
-- NEW VIEW UPDATE FROM MARY TAYLOR
-- NEW VIEW TO FIX NLS PROBLEM MT
-- ADDED GRANT STATEMENT TO THE END
-- Removed the join to Project DWB
-- Refixed target to Act for CCTO and CSOL
-- CHANGED SPECIALBID TO SPECIALBID_FC
-- ADDING RTRIM TO SPECIALBID_FC
-- SPECIAL VARSPECIALBID_FC logic
-- Added the CTO to CVAR STUFF
-- Needed different part number based upon special bid or not
-- removed the I_PRDROOT_OFENTYPE
------------------------------------------------
-- DDL Statements for table ECCM.PRDROOT
------------------------------------------------
DROP TABLE eccm.prdroot;
create summary table eccm.prdroot
(
  entitytype,
  entityid,
  nlsid,
  genareaname,
  genareacode,
  partnumber,
  partnumberdescription,
  announcedate,
  withdrawaldate,
  specialbid,
  status,
  solentitytype,
  solentityid,
  modelname,
  ofentitytype,
  ofentityid,
  wwpartnumber,
  contractinvtitle,
  type,
  installopt,
  ratecardcode,
  unspsc,
  unuom,
  projectid,
  lastupdated,
  rglastupdated,
  wwlastupdated,
  publishflag

) as
    (
 select   'CSOL',
          csol.entityid,
          csol.nlsid,
          csol.GENAREANAME,
          ga.GENAREACODE,
          csol.PNUMB_CT,
          csol.PNUMB_DESC_CT,
          csol.TARG_ANN_DATE_CT,
          CSOL.ACT_WDRAWDATE_CT,
          RTRIM(of.BAVLFORSPECIALBID_FC),
          csol.CSOLSTATUS,
          'SOL',
          of.entityid,
          of.OFMODELNAME,
          'OF',
          of.entityid,
          of.OFFERINGPNUMB,
          of.OFCONTRCTINVTITLE,
          of.OFFERINGTYPE,
          of.OFINSTOPT,
          of.OFRATECARDCODE,
          of.UNSPSC,
          of.UNUOM,
          of.prid,
          csol.VALFROM,
          of.VALFROM,
          of.VALFROM,
          csol.publishflag

from      ECCM.csol csol

join      ECCM.generalarea ga on
          ga.genareaname_fc=csol.genareaname_fc and
          ga.genareatype='Cty'

join      eccm.ccectry ccectry on
          ga.genareacode=ccectry.countrycode

join      ECCM.of of on
          of.entityid = csol.ofid and
          of.nlsid = csol.nlsid

where     csol.CSOLSTATUS_FC in ('0020','0040')

UNION ALL

select    'CCTO',
          ccto.entityid,
          ccto.nlsid,
          ccto.GENAREANAME,
          ga.GENAREACODE,
          ccto.CCOSOLPNUMB,
          ccto.CCOSOLPNUMBDESC,
          ccto.CCOSOLTARGANNDATE,
          ccto.CCOSOLACTWDRAWDATE,
          '',
          ccto.CCOSOLSTATUS,
          'CTO',
          cto.entityid,
          cto.COFMODELNAME,
          'CTO',
          cto.entityid,
          cto.COFPNUMB,
          '',
          '',
          '',
          cto.CVOFRATECARDCODE,
          cto.UNSPSC,
          cto.UNUOM,
          cto.prid,
          ccto.VALFROM,
          cto.VALFROM,
          cto.VALFROM,
          ccto.publishflag

from      ECCM.ccto ccto

join      ECCM.generalarea ga on
          ga.genareaname_fc= ccto.genareaname_fc and
          ga.nlsid=1 and
          ga.genareatype='Cty'

join      eccm.ccectry ccectry on
          ga.genareacode=ccectry.countrycode

join      ECCM.cto cto on
          cto.entityid = ccto.ctoid and
          cto.nlsid = ccto.nlsid

where     ccto.CCOSOLSTATUS_FC in ('0020','0040')

UNION ALL

select    'CVAR',
          cvar.entityid,
          cvar.nlsid,
          cvar.GENAREANAME,
          ga.GENAREACODE,
          cvar.PNUMB_CT,
          cvar.PNUMBDESC_CVAR,
          cvar.TARGANNDATE_CVAR,
          cvar.ACTWDRAWDATE_CVAR,
          rtrim(case var.varspecialbid_fc when '11457' then '0010' when '11458' then '0020' else '' end),
          cvar.STATUS_CVAR,
          'VAR',
          var.entityid,
          var.VARMODELNAME,
          'VAR',
          var.entityid,
          rtrim(case var.varspecialbid_fc when '11457' then var.offeringpnumb when '11458' then cto.COFPNUMB else cto.COFPNUMB end),
          '', 
          '',
          '',
          var.VARRATECARDCODE,
          var.UNSPSC,
          var.UNUOM,
          var.prid,
          cvar.VALFROM,
          var.VALFROM,
          var.VALFROM,
          cvar.publishflag

from      ECCM.cvar cvar

join      ECCM.generalarea ga on
          ga.genareaname_fc=cvar.genareaname_fc and
          ga.nlsid=1 and
          ga.genareatype='Cty'

join      eccm.ccectry ccectry on
          ga.genareacode=ccectry.countrycode

join      ECCM.var var on
          var.entityid = cvar.varid and
          var.nlsid = cvar.nlsid

join      ECCM.ctovar ctovar on 
          var.entityid = ctovar.id2
          
join      ECCM.cto cto on 
          ctovar.id1 = cto.entityid and 
          cto.nlsid = cvar.nlsid 

where     cvar.STATUS_CVAR_FC in ('0020','0040')

UNION ALL

select    'CB',
          cb.entityid,
          cb.nlsid,
          cb.GENAREANAME,
          ga.GENAREACODE,
          cb.PNUMB_CT,
          cb.PNUMB_DESC_CB,
          cb.TARG_ANN_DATE_CB,
          cb.ACT_WDRAWDATE_CB,
          '',
          cb.CBSOLSTATUS,
          'CB',
          0,
          cb.PNUMB_DESC_CB,
          'CB',
          0,
          cb.PNUMB_CT,
          '',
          '',
          '',
          cb.CBRATECARDCODE,
          '',
          '',
          0,
          cb.valfrom,
          cb.valfrom,
          cb.valfrom,
          cb.publishflag

from      ECCM.cb cb

join      ECCM.generalarea ga on
          ga.genareaname_fc =cb.genareaname_fc and
          ga.nlsid=1 and
          ga.genareatype='Cty'

join      eccm.ccectry ccectry on
          ga.genareacode=ccectry.countrycode

where     cb.CBSOLSTATUS_FC in ('0010')


) DATA INITIALLY DEFERRED REFRESH DEFERRED
-- IN ECCMSUMMTS INDEX IN ECCMSUMMTS
;
REFRESH TABLE ECCM.PRDROOT;
create index eccm.I_PRDROOT_OFENID on eccm.prdroot(ofentityid, ofentitytype, nlsid);
--CREATE INDEX ECCM.I_PRDROOT_OFENID ON ECCM.PRDROOT (OFENTITYID ASC);
--CREATE INDEX ECCM.I_PRDROOT_OFENTYPE ON ECCM.PRDROOT (OFENTITYTYPE ASC);
CREATE INDEX ECCM.PRDRINDEX001 ON ECCM.PRDROOT (ENTITYID ASC, ENTITYTYPE ASC, NLSID ASC, PARTNUMBER ASC, PROJECTID ASC) PCTFREE 10;
CREATE INDEX ECCM.WZ2APRDROOT ON ECCM.PRDROOT (PARTNUMBER ASC, LASTUPDATED ASC, ENTITYTYPE ASC, ENTITYID ASC);
--CREATE INDEX ECCM.WZ2PRDROOT ON ECCM.PRDROOT(NLSID DESC, PARTNUMBER ASC);
CREATE INDEX INST1.WIZ4836 ON ECCM.PRDROOT (PROJECTID ASC, OFENTITYID ASC, ENTITYTYPE ASC, ENTITYID ASC);


--
-- Summary Tables
--
------------------------------------------------
-- USED TO SUPPORT VIEWS THAT ARE IN THE TABLE SPEc
-- CONVERTED DWB
-- COMPILED DWB
-- CHECKED FOR FINAL SCRIPT
-- MADE DROP IMMUNE FROM ODSINIT
-- UPDATE FROM MTM
-- NEED TO MAKE SURE WE REORG eccm.prod_product_price and other
-- Large tables to make this thing refresh fast
-- Changes for Variant
------------------------------------------------
-- DDL Statements for table ECCM.PACKAGE1
------------------------------------------------
drop table eccm.package1;
create summary table eccm.package1
 (
   entitytype,
   entityid,
   nlsid,
   wunits,
   weight,
   width,
   height,
   dunits,
   depth
 )  AS (

  SELECT
  distinct    par.entity1type,
              par.entity1id,
              p.nlsid,
              case a.attributetoken  when 'PSGB1WEIGHTUNITS_US' then substr(p.attributevalue,1,5) else ' '  end,
              case a.attributetoken when 'PSGB1WEIGHT_US' then substr(p.attributevalue,1,5) else ' ' end,
              case a.attributetoken when 'PSGB1WIDTH_US' then substr(p.attributevalue,1,5) else ' ' end,
              case a.attributetoken when 'PSGB1HEIGHT_US' then substr(p.attributevalue,1,5) else ' ' end,
              case a.attributetoken when 'PSGB1DEPTHUNITS_US' then substr(p.attributevalue,1,5) else ' ' end,
              case a.attributetoken when 'PSGB1DEPTH_US' then substr(p.attributevalue,1,5) else ' ' end

  FROM        ECCM.attribute a

  JOIN        ECCM.prodattribute p on
              p.attributecode = a.attributecode

  JOIN        ECCM.prodattrelator par on
              p.entitytype = par.entity2type and
              p.entityid = par.entity2id

  where       a.attributetoken IN
              ( 'PSGB1WEIGHTUNITS_US',
                'PSGB1WEIGHT_US',
                'PSGB1WIDTH_US',
                'PSGB1HEIGHT_US',
                'PSGB1DEPTHUNITS_US',
                'PSGB1DEPTH_US'
              ) and 
              par.entity1type not in ('SBB')
  union all
  
  --
  -- SBB STUFF VAR
  --
  
    SELECT
    distinct    'VAR',
                varsbb.id1,
                p.nlsid,
                case a.attributetoken  when 'PSGB1WEIGHTUNITS_US' then substr(p.attributevalue,1,5) else ' '  end,
                case a.attributetoken when 'PSGB1WEIGHT_US' then substr(p.attributevalue,1,5) else ' ' end,
                case a.attributetoken when 'PSGB1WIDTH_US' then substr(p.attributevalue,1,5) else ' ' end,
                case a.attributetoken when 'PSGB1HEIGHT_US' then substr(p.attributevalue,1,5) else ' ' end,
                case a.attributetoken when 'PSGB1DEPTHUNITS_US' then substr(p.attributevalue,1,5) else ' ' end,
                case a.attributetoken when 'PSGB1DEPTH_US' then substr(p.attributevalue,1,5) else ' ' end
  
    FROM        ECCM.attribute a
  
    JOIN        ECCM.prodattribute p on
                p.attributecode = a.attributecode
  
    JOIN        ECCM.sbbpk sbbpk on
                p.entitytype = 'PK' and
                p.entityid = sbbpk.id2 
             
    JOIN        ECCM.varsbb varsbb on
                sbbpk.id1 = varsbb.id2 
  
    where       a.attributetoken IN
                ( 'PSGB1WEIGHTUNITS_US',
                  'PSGB1WEIGHT_US',
                  'PSGB1WIDTH_US',
                  'PSGB1HEIGHT_US',
                  'PSGB1DEPTHUNITS_US',
                  'PSGB1DEPTH_US'
                )
        
  union all
  --
  -- SBB STUFF CVAR
  --
  
  SELECT
  distinct    'CVAR',
                cvar.entityid,
                p.nlsid,
                case a.attributetoken  when 'PSGB1WEIGHTUNITS_US' then substr(p.attributevalue,1,5) else ' '  end,
                case a.attributetoken when 'PSGB1WEIGHT_US' then substr(p.attributevalue,1,5) else ' ' end,
                case a.attributetoken when 'PSGB1WIDTH_US' then substr(p.attributevalue,1,5) else ' ' end,
                case a.attributetoken when 'PSGB1HEIGHT_US' then substr(p.attributevalue,1,5) else ' ' end,
                case a.attributetoken when 'PSGB1DEPTHUNITS_US' then substr(p.attributevalue,1,5) else ' ' end,
                case a.attributetoken when 'PSGB1DEPTH_US' then substr(p.attributevalue,1,5) else ' ' end
  
    FROM        ECCM.attribute a
  
    JOIN        ECCM.prodattribute p on
                p.attributecode = a.attributecode
  
    JOIN        ECCM.sbbpk sbbpk on
                p.entitytype = 'PK' and
                p.entityid = sbbpk.id2 
             
    JOIN        ECCM.cvarsbb cvarsbb on
                cvarsbb.id2 = sbbpk.id1   

    JOIN        ECCM.cvar cvar on
                cvar.entityid = cvarsbb.id1 and
                cvar.nlsid = p.nlsid
  
    where       a.attributetoken IN
                ( 'PSGB1WEIGHTUNITS_US',
                  'PSGB1WEIGHT_US',
                  'PSGB1WIDTH_US',
                  'PSGB1HEIGHT_US',
                  'PSGB1DEPTHUNITS_US',
                  'PSGB1DEPTH_US'
                )  
              
              
              
) DATA INITIALLY DEFERRED REFRESH DEFERRED;
refresh table eccm.package1;

-- DDL Statements for indexes on Table ECCM.PACKAGE1

CREATE INDEX ECCM.PK1INDEX ON ECCM.PACKAGE1
  (ENTITYID ASC,
   ENTITYTYPE ASC,
   NLSID ASC);
   
   
-------------------------------------------------
-- TOTALLY NEW SUMMARY TABLE FROM MTM (REDESIGN)
-- ADDED NLSID PREDICATE TO JOIN
------------------------------------------------
-- DDL Statements for table ECCM.PRODPRICE1SUMM
------------------------------------------------
DROP TABLE eccm.prodprice1summ;
 CREATE SUMMARY TABLE eccm.prodprice1summ (
  partnumber,
  sbbpartnumber,
  country,
  nls,
  family,
  price,
  usdprice,
  currency,
  fromdate,
  todate,
  pricetype,
  callforquote,
  salesstatus,
  precedence,
  sourcesystem,
  announcedate,
  timecode,
  materialstatusdate,
  markedfordeletion,
  lastupdated
) as  (
    with nonctoprice1_tmp (partnumber,family,countrycode,nls,timecode,announcedate) as (

    select      csol.pnumb_ct,
                psbf.familyname,
                gen.genareacode,
                csol.nlsid,
                tc.timecode,
                csol.TARG_ANN_DATE_CT

    from        eccm.generalarea gen

    join        eccm.csol csol on
                csol.genareaname_fc =gen.genareaname_fc

    join        eccm.of of on
                csol.ofid = of.entityid and
                csol.nlsid=of.nlsid

    join        eccm.psbfbase psbf on
                of.prid = psbf.projectid

    join        int.timecode tc on
                tc.entitytype='CSOL' and
                csol.entityid=tc.entityid and
                tc.nlsid=csol.nlsid

    where       gen.genareatype='Cty'

    UNION ALL

    select      cvar.pnumb_ct,
                psbf.familyname,
                gen.genareacode,
                cvar.nlsid,
                tc.timecode,
                cvar.TARGANNDATE_CVAR

    from        eccm.generalarea gen

    join        eccm.cvar cvar on
                cvar.genareaname_fc =gen.genareaname_fc

    join        eccm.var var on
                cvar.varid=var.entityid and
                cvar.nlsid=var.nlsid

    join        eccm.psbfbase psbf on
                var.prid = psbf.projectid

    join        int.timecode tc on
                'CVAR'=tc.entitytype and
                cvar.entityid=tc.entityid and
                tc.nlsid=cvar.nlsid

    where       gen.genareatype='Cty'

    UNION ALL

    select      cb.pnumb_ct,
                '-',
                gen.genareacode,
                cb.nlsid,
                tc.timecode,
                cb.TARG_ANN_DATE_CB

    from        eccm.cb cb

    join        eccm.generalarea gen on
                cb.genareaname_fc=gen.genareaname_fc and
                gen.genareatype='Cty'

    join        int.timecode tc on
                'CB'=tc.entitytype and
                cb.entityid=tc.entityid and tc.nlsid=cb.nlsid

    )

    ,ctoprice1_tmp
      (
        ctopartnumber,
        sbbpartnumber,
        countrycode,
        nlsid,
        timecode,
        announcedate
      ) as (

      select    ccto.ccosolpnumb,
                sbb.sbbpnumb,
                gen.genareacode,
                ccto.nlsid,
                case
                  when (ccto.CCOSOLTARGANNDATE > (current date + 1 day)) then
                    case
                      when ((days(ccto.CCOSOLTARGANNDATE) - days(current date)) <= 0) then 0
                      when ((days(ccto.CCOSOLTARGANNDATE) - days(current date)) <= 1) then 1
                      when ((days(ccto.CCOSOLTARGANNDATE) - days(current date)) <= 7) then 7
                      when ((days(ccto.CCOSOLTARGANNDATE) - days(current date)) <= 21) then 21
                      when ((days(ccto.CCOSOLTARGANNDATE) - days(current date)) <= 35) then 35
                      when ((days(ccto.CCOSOLTARGANNDATE) - days(current date)) <= 56) then 56
                      else 99
                    end
                  else
                    case
                      when (cctosbb.SBBPUBLISHCTDATE is not null) then
                        case
                          when ((days(cctosbb.SBBPUBLISHCTDATE) - days(current date)) <= 0) then 0
                          when ((days(cctosbb.SBBPUBLISHCTDATE) - days(current date)) <= 1) then 1
                          when ((days(cctosbb.SBBPUBLISHCTDATE) - days(current date)) <= 7) then 7
                          when ((days(cctosbb.SBBPUBLISHCTDATE) - days(current date)) <= 21) then 21
                          when ((days(cctosbb.SBBPUBLISHCTDATE) - days(current date)) <= 35) then 35
                          when ((days(cctosbb.SBBPUBLISHCTDATE) - days(current date)) <= 56) then 56
                          else 99
                        end
                      when (ctosbb.SBBPUBLISHWWDATE is not null) then
                        case
                          when ((days(ctosbb.SBBPUBLISHWWDATE) - days(current date)) <= 0) then 0
                          when ((days(ctosbb.SBBPUBLISHWWDATE) - days(current date)) <= 1) then 1
                          when ((days(ctosbb.SBBPUBLISHWWDATE) - days(current date)) <= 7) then 7
                          when ((days(ctosbb.SBBPUBLISHWWDATE) - days(current date)) <= 21) then 21
                          when ((days(ctosbb.SBBPUBLISHWWDATE) - days(current date)) <= 35) then 35
                          when ((days(ctosbb.SBBPUBLISHWWDATE) - days(current date)) <= 56) then 56
                          else 99
                        end
                      else
                        case
                          when ((days(sbb.SBBANNOUNCETGT) - days(current date)) <= 0) then 0
                          when ((days(sbb.SBBANNOUNCETGT) - days(current date)) <= 1) then 1
                          when ((days(sbb.SBBANNOUNCETGT) - days(current date)) <= 7) then 7
                          when ((days(sbb.SBBANNOUNCETGT) - days(current date)) <= 21) then 21
                          when ((days(sbb.SBBANNOUNCETGT) - days(current date)) <= 35) then 35
                          when ((days(sbb.SBBANNOUNCETGT) - days(current date)) <= 56) then 56
                          else 99
                        end
                    end
                end ,
                case
                  when (ccto.CCOSOLTARGANNDATE > (current date + 1 day)) then ccto.CCOSOLTARGANNDATE
                  else coalesce(cctosbb.SBBPUBLISHCTDATE,ctosbb.SBBPUBLISHWWDATE,sbb.SBBANNOUNCETGT)
                end

      from      eccm.ccto ccto

      join      eccm.cto cto on
                ccto.ctoid=cto.entityid and
                ccto.nlsid=cto.nlsid

      join      eccm.cctosbb cctosbb on
                ccto.entityid = cctosbb.ID1 and
                cctosbb.nlsid = 1

      join      eccm.ctocg ctocg on
                ctocg.ID1=cto.entityid

      join      eccm.cg cg on
                ctocg.ID2=cg.entityid and
                ccto.nlsid=cg.nlsid

      join      eccm.sbb sbb on
                sbb.entityid=cctosbb.ID2 and
                sbb.sbbtype=cg.cgtype and
                sbb.nlsid = ccto.nlsid

      join      eccm.generalarea gen on
                ccto.genareaname_fc=gen.genareaname_fc and
                gen.genareatype='Cty'

--
      join      eccm.ctosbb ctosbb on
                ctosbb.id1=cto.entityid and
                ctosbb.id2=sbb.entityid
--
      join      int.timecode tc on
                'CCTO'=tc.entitytype and
                ccto.entityid=tc.entityid and
                tc.nlsid=ccto.nlsid

      where     cg.cgpriced='Yes'
    )

  select        substr(nonctotmp.partnumber,1,7) as partnumber,
                '-' as sbbpartnumber,
                substr(nonctotmp.countrycode,1,2) as country,
                nonctotmp.nls,
                rtrim(nonctotmp.family) as family,
                decimal(round(priceamount,2),16,2) as price,
                decimal(round(priceamount*xchgratemul,2),16,2) as usdprice,
                substr(price.currencycode,1,3) as currency,
                coalesce(pricevalidfromdate,date('1900-01-01')) as fromdate,
                coalesce(pricevalidtodate,date('1900-01-01')) as todate,
                coalesce(distributionchannel,'-') as pricetype,
                callforquote,
                materialstatus,
                precedence,
                sourcesystem,
                announcedate,
                timecode,
                materialstatusdate,
                markedfordeletion,
                price.lastupdated

  from          nonctoprice1_tmp nonctotmp

  left join     eccm.prod_product_price price on
                (nonctotmp.partnumber=price.partnumber and nonctotmp.countrycode=price.countrycode)

  left join     int.currency_xchg curr on
                (price.currencycode=curr.currencycode)

  UNION ALL

  select        substr(ctopartnumber,1,7) as partnumber,
                substr(sbbpartnumber,1,7) as sbbpartnumber,
                substr(ctotmp.countrycode,1,2) as country,
                nlsid,
                '-' as family,
                decimal(round(priceamount,2),16,2) as price,
                decimal(round(priceamount*xchgratemul,2),16,2) as usdprice,
                substr(price.currencycode,1,3) as currency,
                coalesce(pricevalidfromdate,date('1900-01-01')) as fromdate,
                coalesce(pricevalidtodate,date('1900-01-01')) as todate,
                coalesce(distributionchannel,'-') as pricetype,
                callforquote,
                materialstatus,
                precedence,
                sourcesystem,
                announcedate,
                timecode,
                materialstatusdate,
                markedfordeletion,
                price.lastupdated

  from          ctoprice1_tmp ctotmp

  left join     eccm.prod_product_price price on
                (ctopartnumber=partnumber and
                sbbpartnumber=ctovariantpartnumber and
                ctotmp.countrycode=price.countrycode)

  left join     int.currency_xchg curr on
                (price.currencycode=curr.currencycode)
  ) DATA INITIALLY DEFERRED REFRESH DEFERRED

  ;
commit work;
refresh table eccm.prodprice1summ;

-- DDL Statements for indexes on Table ECCM.PRODPRICE1SUMM

CREATE INDEX INT .PRPRSUMMINDEX001 ON ECCM.PRODPRICE1SUMM
  (PARTNUMBER ASC,
   SBBPARTNUMBER ASC,
   COUNTRY ASC,
   PRICETYPE ASC,
   TODATE ASC)
  PCTFREE 10 ;

-- DDL Statements for indexes on Table ECCM.PRODPRICE1SUMM

CREATE INDEX INT .PRPRSUMMINDEX002 ON ECCM.PRODPRICE1SUMM
  (FAMILY ASC)
  PCTFREE 10 ;
