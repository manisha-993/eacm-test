--------------------------------------------------------------------------------------
-- VIEWS
--------------------------------------------------------------------------------------
--
-- $Log: eccm.views.sql,v $
-- Revision 1.32  2004/09/29 19:21:47  yang
-- minor change to  wwmvattr
--
-- Revision 1.31  2004/09/29 18:52:50  yang
-- updated ctmvattr & wwmvattr for CR7659
--
-- Revision 1.30  2004/06/09 16:10:14  dave
-- fix for praveen ECCM pack_complete
--
-- Revision 1.29  2004/04/19 15:47:55  dave
-- GAV fixes for eCCM
--
-- Revision 1.28  2004/02/27 21:14:05  dave
-- bad syntax o should have been on
--
-- Revision 1.27  2004/02/27 19:44:32  dave
-- view change for ECCM fixed CB
--
-- Revision 1.26  2004/02/05 19:27:59  dave
-- more minor changes
--
-- Revision 1.25  2004/01/28 21:36:58  dave
-- more script changes
--
-- Revision 1.24  2004/01/27 19:23:00  dave
-- more script changes
--
-- Revision 1.23  2004/01/23 17:32:37  dave
-- Added second predicate to the where clause
-- of the second union in the eccm.attributeview
--
-- Revision 1.22  2004/01/20 18:53:30  dave
-- removing of the echo and some not null required fields
--
-- Revision 1.21  2004/01/17 04:21:16  dave
-- when a relator changes gets added or deleted.. we
-- need to update the parent entity if a prodattrelator
--
-- Revision 1.20  2004/01/17 00:11:12  dave
-- per ECCM .. added a concat to attributegroup
--
-- Revision 1.19  2004/01/09 20:35:33  dave
-- adding ; to end
--
-- Revision 1.18  2004/01/09 17:48:02  dave
-- removed the echo statements
--
-- Revision 1.17  2004/01/08 19:16:06  dave
-- more eccm scripting changes
--
-- Revision 1.16  2004/01/01 19:36:04  dave
-- eccm.variantattr fix
--
-- Revision 1.15  2004/01/01 19:33:08  dave
-- more changes for eccm AT
--
-- Revision 1.14  2003/12/31 20:37:38  dave
-- more refinement
--
-- Revision 1.13  2003/12/31 19:54:56  dave
-- refinements
--
-- Revision 1.12  2003/12/29 20:59:17  dave
-- ECCM SQL Fixes for eccm.prodattrelator
--
-- Revision 1.11  2003/12/22 20:38:21  dave
-- making changes to experimental dwb.prodattr
--
-- Revision 1.10  2003/12/22 19:16:35  dave
-- fixes for target to actual withdrawl
--
-- Revision 1.9  2003/12/19 18:42:30  dave
-- added distinct. but taking a perf hit
--
-- Revision 1.8  2003/12/17 19:18:35  dave
-- rtrim the CHAR
--
-- Revision 1.7  2003/12/17 17:28:18  dave
-- added rtrim to some views
--
-- Revision 1.6  2003/12/16 22:56:18  dave
-- fix and more CHAR 13 changes
--
-- Revision 1.5  2003/12/16 19:51:13  dave
-- upped to 13 for CHAR(8) -- > CHAR (13)
--
-- Revision 1.4  2003/12/16 19:40:15  dave
-- added CHAR(8) to Audience and ww view
--
-- Revision 1.3  2003/12/15 19:20:43  dave
-- more ECCM changes
--
-- Revision 1.2  2003/12/11 06:37:49  dave
-- more updates to the scripts
--
-- Revision 1.1  2003/12/10 20:34:54  dave
-- updating and making final scripts
--

----------------------------------
-- SUPPORTS OTHER TABLE SPEC NEEDS
-- CONVERTED DWB
-- COMPILED DWB
-- ADDED NLSID
-- CHECKED FOR FINAL SCRIPT
----------------------------------
DROP VIEW eccm.package;
CREATE VIEW eccm.package
----------------------------------
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
 ) AS

    SELECT    entitytype,
              entityid,
              nlsid,
              MAX(wunits),
              MAX(weight),
              MAX(width),
              MAX(height),
              MAX(dunits),
              MAX(depth)

    FROM      eccm.package1

    GROUP BY  entitytype,
              entityid,
              nlsid
;

----------------------------------------
-- CALLED OUT IN TABLE SPEC
-- CONVERTED DWB
-- COMPILED DWB
-- CHECKED FOR FINAL SCRIPT
----------------------------------------
DROP VIEW eccm.wwcat;
CREATE VIEW eccm.wwcat
(
  countrycode,
  language,
  categoryidentifier,
  parentidentifier,
  source
) AS (

  SELECT      cat.countrycode,
              cat.language,
              cat.catidentifier,
              cat.catidentifier,
             'ECCM'

   FROM      int.avwwcat cat

   WHERE     posstr(cat.catidentifier, '@') <> 0
 );


----------------------------------------
-- CALLED OUT IN TABLE SPEC
-- CONVERTED DWB
-- COMPILED DWB
-- CHECKED FOR FINAL SCRIPT
----------------------------------------
DROP VIEW eccm.ratecarddesc_vw;
CREATE VIEW eccm.ratecarddesc_vw
AS
    SELECT
    DISTINCT    rc.countrycode,
                isolangcode2 AS language,
                shippingcondition

    FROM        eccm.prod_rate_card rc,
                eccm.psgctnls ctnls,
                eccm.psgnls nls

    WHERE       ctnls.countrycode = rc.countrycode and
                ctnls.nlsid = nls.nlsid
 ;


-----------------------------------------
-- CALLED OUT IN THE TABLE SPEC
-- CONVERTED DWB
-- COMPILED DWB
-- CHECKED FOR FINAL SCRIPT
-----------------------------------------
DROP VIEW eccm.wwprdctry;
CREATE VIEW eccm.wwprdctry
-----------------------------------------
(
  PARTNUMBER,
  ENTITYTYPE,
  ENTITYID,
  NLSID,
  COUNTRYCODE,
  LANGUAGE,
  AUDIENCE,
  QUANTITY,
  NEW_FLAG,
  BUYABLE_FLAG,
  ADDTOCART_FLAG,
  CUSTOMIZE_FLAG,
  FOCUS_FLAG,
  HIDE_FLAG,
  REFRESHTIME,
  PRODUCTCLASS,
  RATETYPE,
  COUNTRY_NEWFLAG,
  LASTUPDATED
) AS

  SELECT      prd.partnumber,
              prd.prdentitytype,
              prd.prdentityid,
              prd.prdnlsid,
              prd.countrycode,
              prd.language,
              rtrim(CHAR(multi.longdescription,13)),
              dac.available_quantity,
              prdavail.new_flag,
              prdavail.buyable_flag,
              prdavail.addtocart_flag,
              prdavail.customize_flag,
              prdavail.focus_flag,
              prdavail.hide_flag ,
              prd.refreshtime,
              prd.productclass,
              prdavail.ratetype,
              CASE WHEN
                current date - date(prd.announcedate) <= newdate.numberofdays THEN 1 ELSE 0
              END,
              dac.lastupdated

  FROM        int.avwwprd prd

  JOIN        eccm.audience multi on
              multi.entityid = prd.prdentityid and
              multi.entityname = prd.prdentitytype and
              multi.nlsid = prd.prdnlsid

  LEFT JOIN   int.dacavail dac on
              dac.partnumber = prd.partnumber and
              dac.countrycode = prd.countrycode and
              multi.longdescription = 'SHOP'

  LEFT JOIN   int.avprdavail prdavail on
              prd.partnumber = prdavail.partnumber and
              prd.countrycode = prdavail.countrycode and
              prd.language = prdavail.language and
              multi.longdescription = prdavail.audience

  LEFT JOIN   int.newflagdates newdate on
              prd.countrycode = newdate.countrycode

;


-------------------------------------------
-- USED IN SUPPORT OF THE TABLE SPEC
-- CONVERTED DWB
-- COMPILED DWB
-- CHECKED FOR FINAL SCRIPT
-------------------------------------------
DROP VIEW eccm.catcatrel;
CREATE VIEW eccm.catcatrel
(
  parentidentifier,
  childidentifier
) AS (

      SELECT
      DISTINCT      'PSG',
                    fam.familyname
      FROM          eccm.projserbrandfam fam

      UNION ALL

      SELECT        fam.familyname,
                    fam.famsername

      FROM          eccm.projserbrandfam fam

      UNION ALL

      SELECT
      DISTINCT      fam.famsername,
                    fam.famsername || '@' || of.OPTGROUPNAME

      FROM          ECCM.of of

      join          eccm.projserbrandfam fam on
                    of.prid = fam.projectid and
                    of.nlsid = fam.nlsid

      WHERE         (of.OPTGROUPNAME <> '' or of.OPTGROUPNAME IS NOT NULL) and
                    of.nlsid <> 8

      UNION ALL

      SELECT
      DISTINCT      fam.famsername || '@' || of.OPTGROUPNAME,
                    fam.famsername || '@' || of.OPTGROUPNAME || '@' || of.subgroup

      FROM          ECCM.of of

      join          eccm.projserbrandfam fam on
                    of.prid = fam.projectid and
                    of.nlsid = fam.nlsid

      WHERE         of.subgroup <> ''  and
                    of.OPTGROUPNAME <> '' and
                    of.nlsid <> 8
 );

-------------------------------------------------
-- CALLED OUT IN THE TABLE SPEC
-- TOOK OUT THE REFERENCE TO BR
-- CONVERTED DWB
-- COMPILED DWB
-- MAKING SURE WE USE NLSID From ctnls
-- CHECKED FOR FINAL SCRIPT
-------------------------------------------------
-------------------------------------------------
-- CALLED OUT IN THE TABLE SPEC
-- TOOK OUT THE REFERENCE TO BR
-- CONVERTED DWB
-- COMPILED DWB
-- MAKING SURE WE USE NLSID From ctnls
-- CHECKED FOR FINAL SCRIPT
-- New version per FB53296
-------------------------------------------------
DROP VIEW eccm.catroot;
CREATE VIEW eccm.catroot
-------------------------------------------------
(
  CATEGORYIDENTIFIER,
  COUNTRYCODE,
  LANGUAGE,
  PARENTIDENTIFIER,
  NAMEDESC,
  ENTITYTYPE,
  ENTITYID,
  NLSID,
  SOURCE,
  LASTUPDATED
) AS

  SELECT
  DISTINCT        fam.familyname,
                  ctnls.countrycode,
                  nls.isolangcode2,
                  '',
                  fam.familyname,
                  'FAM',
                  fam.familyid,
                  ctnls.nlsid,
                  '',
                  current timestamp

  FROM            eccm.psgctnls ctnls,
                  eccm.projserbrandfam fam,
                  eccm.psgnls nls

  WHERE           ctnls.nlsid = nls.nlsid and
                  fam.nlsid = 1

  UNION ALL

  SELECT
  DISTINCT        fam.famsername,
                  ctnls.countrycode,
                  nls.isolangcode2,
                  fam.familyname,
                  fam.seriesname,
                  'SE',
                  fam.seriesid,
                  ctnls.nlsid,
                  '',
                  current timestamp

  FROM            eccm.psgctnls ctnls,
                  eccm.projserbrandfam fam,
                  eccm.psgnls nls

  WHERE           ctnls.nlsid = nls.nlsid and
                  fam.nlsid= 1

  UNION ALL

  SELECT
  DISTINCT        cat.catidentifier,
                  ctnls.countrycode,
                  nls.isolangcode2,
                  cat.parentidentifier,
                  '',
                  '',
                  0,
                  1,
                  'ECCM',
                  current timestamp

   FROM           eccm.psgctnls ctnls,
                  int.avcatcatrel cat,
                  eccm.psgnls nls

   WHERE          ctnls.nlsid = nls.nlsid and
                  cat.relationshiponly = 'N'

   UNION ALL

   SELECT
   DISTINCT         cat.parentidentifier,
                  ctnls.countrycode,
                  nls.isolangcode2,
                  '',
                  '',
                  '',
                  0,
                  1,
                  'ECCM',
                  current timestamp

 FROM             eccm.psgctnls ctnls,
                  int.avcatcatrel cat,
                  eccm.psgnls nls

 WHERE            ctnls.nlsid = nls.nlsid and
                  cat.parentidentifier NOT IN (
                      SELECT    catidentifier
                      FROM      int.avcatcatrel
                      WHERE     relationshiponly = 'N'
                  )
 UNION ALL

 SELECT
 DISTINCT         psbf.famsername ||'@'|| of.OPTGROUPNAME,
                  ctnls.countrycode,
                  nls.isolangcode2,
                  psbf.famsername,
                  of.OPTGROUPNAME,
                  '',
                  0,
                  of.nlsid,
                  '',
                  current timestamp

 FROM             ECCM.of of

 join             eccm.projserbrandfam psbf on
                  of.prid = psbf.projectid and
                  of.nlsid = psbf.nlsid

                  ,
                  eccm.psgctnls ctnls,
                  eccm.psgnls nls

  WHERE           ctnls.nlsid = nls.nlsid and
                  of.OPTGROUPNAME IS NOT NULL and
                  of.OPTGROUPNAME <> ''
  UNION ALL

  SELECT
  DISTINCT        psbf.famsername || '@'||  of.OPTGROUPNAME ||  '@' || of.subgroup,
                  ctnls.countrycode,
                  nls.isolangcode2,
                  psbf.famsername || '@'||  of.OPTGROUPNAME,
                  of.subgroup,
                  '',
                  0,
                  of.nlsid,
                  '',
                  current timestamp

 FROM             ECCM.of of

 join             eccm.projserbrandfam psbf on
                  of.prid = psbf.projectid and
                  of.nlsid = psbf.nlsid

                  ,
                  eccm.psgctnls ctnls,
                  eccm.psgnls nls

  WHERE           ctnls.nlsid = nls.nlsid and
                  of.OPTGROUPNAME IS NOT NULL and
                  of.OPTGROUPNAME <> '' and
                  of.SUBGROUP IS NOT NULL and
                  of.SUBGROUP <> ''
;

-------------------------------------------
-- CALLED OUT IN TABLE SPEC
-- CONVERTED DWB
-- COMPILED DWB
-- CHECKED FOR FINAL SCRIPT
-- ADDED RTRIM to countrycode
-------------------------------------------
DROP VIEW eccm.wwcatcatrel;
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
;

--( PARENTIDENTIFIER,
--  CHILDIDENTIFIER,
--  COUNTRYCODE,
--  AUDIENCE,
--  MASTNAV,
--  PUB_FLAG,
--  SEQUENCE,
--  SOURCE ) AS (
--
--    SELECT      COALESCE(avcatrel.parentidentifier,cat.parentidentifier),
--                cat.childidentifier,
--                rtrim(ctry.countrycode),
--                'LE',
--                'M',
--                avcat.pub_flag,
--                avcat.sequence,
--                ''
--    FROM        eccm.catcatrel cat
--
--    JOIN        eccm.ccectry ctry on 1 = 1
--
--    LEFT JOIN   int.avcatcatseq avcat on
--                cat.parentidentifier = avcat.parentidentifier and
--                cat.childidentifier =avcat.catidentifier and
--                ctry.countrycode = avcat.countrycode and
--                avcat.audience = 'LE'
--
--    LEFT JOIN   int.avcatcatrel avcatrel on
--                cat.childidentifier = avcatrel.catidentifier
--                and avcatrel.mastnav = 'M'
--
--    UNION ALL
--
--    SELECT      COALESCE(avcatrel.parentidentifier, cat.parentidentifier),
--                cat.childidentifier,
--                rtrim(ctry.countrycode),
--                'SHOP',
--                'M',
--                avcat.pub_flag,
--                avcat.sequence,
--                ''
--
--    FROM        eccm.catcatrel cat
--
--    JOIN        eccm.ccectry ctry on 1 = 1
--
--    LEFT JOIN   int.avcatcatseq avcat on
--                cat.parentidentifier = avcat.parentidentifier and
--                cat.childidentifier = avcat.catidentifier and
--                ctry.countrycode = avcat.countrycode and
--                avcat.audience = 'SHOP'
--
--    LEFT JOIN   int.avcatcatrel avcatrel on
--                cat.childidentifier = avcatrel.catidentifier and
--                avcatrel.mastnav = 'M'
--
--    UNION ALL
--
--    SELECT      cat.parentidentifier,
--                cat.catidentifier,
--                rtrim(ctry.countrycode),
--                'LE',
--                cat.mastnav,
--                avcat.pub_flag,
--                avcat.sequence,
--                cat.source
--
--    FROM        int.avcatcatrel cat
--
--    JOIN        eccm.ccectry ctry on
--                1 = 1
--
--    LEFT JOIN   int.avcatcatseq avcat on
--                cat.parentidentifier = avcat.parentidentifier and
--                cat.catidentifier = avcat.catidentifier
--                and ctry.countrycode = avcat.countrycode and
--                avcat.audience = 'LE'
--    WHERE       NOT ( cat.mastnav = 'M' and cat.catidentifier IN
--                  (SELECT DISTINCT childidentifier FROM eccm.catcatrel ))
--
-- UNION
--
-- SELECT      cat.parentidentifier,
--             cat.catidentifier,
--             rtrim(ctry.countrycode),
--             'SHOP',
--             cat.mastnav,
--             avcat.pub_flag,
--             avcat.sequence,
--             cat.source
--
--    FROM        int.avcatcatrel cat
--
--    JOIN        eccm.ccectry ctry on1 = 1
--
--    LEFT JOIN   int.avcatcatseq avcat on
--                cat.parentidentifier = avcat.parentidentifier and
--                cat.catidentifier = avcat.catidentifier and
--                ctry.countrycode = avcat.countrycode and
--                Upper(avcat.audience) = 'SHOP'
--
--    WHERE       NOT (cat.mastnav = 'M'AND cat.catidentifier IN
--                (SELECT DISTINCT childidentifier FROM eccm.catcatrel))
--
--    UNION ALL
--
--    SELECT      'PSG',
---                cat.parentidentifier,
--                rtrim(ctry.countrycode),
--                'SHOP',
--                'M',
--                1,
--                1,
--                'ECCM'
--
--    FROM        int.avcatroot cat
--
--    JOIN        eccm.ccectry ctry on
--                1 = 1
--
--    WHERE       cat.parentidentifier <> 'Popular Models' and cat.parentidentifier
--                NOT IN (SELECT catidentifier FROM int.avcatcatrel WHERE     mastnav = 'M')
--
--    UNION ALL
--
--    SELECT      'PSG',
--                cat.parentidentifier,
--               rtrim(ctry.countrycode),
--               'LE',
--                'M',
--                1,
--                1,
--                'ECCM'
--
--    FROM        int.avcatroot cat
--
--    JOIN        eccm.ccectry ctry on
--                1 = 1
--
--    WHERE       cat.parentidentifier <> 'Popular Models' AND cat.parentidentifier NOT IN
--                (SELECT catidentifier FROM int.avcatcatrel WHERE  mastnav = 'M')
--
--    UNION ALL
--
--    select      'DAC',
--                'DAC@DAC1',
--                rtrim(ctry.countrycode),
--                'DACMAX',
--                'M',
--                1,
--                0,
--                'ECCM'
--
--    from        eccm.ccectry ctry
--
--    where       ctry.countrycode='US'
--
--    );



--------------------------------------------
-- CALLED OUT IN TABLE SPEC
-- CONVERTED DWB
-- COMPILED DWB
-- DWB NEEDS TO BE REVIEWED
-- Received update from MT
-- Removed the nLSID
-- Added a join on entitytype for the 2 CB selects
--------------------------------------------
DROP VIEW eccm.wwprdprdrel;
CREATE VIEW  eccm.wwprdprdrel
(
  PARENTPARTNUMBER,
  CHILDPARTNUMBER,
  COUNTRYCODE,
  AUDIENCE,
  PRODUCTCLASS,
  SCHEDULERELEV,
  SBBANNOUNCEDATE,
  SBBWITHDRAWDATE,
  SEQUENCE,
  PUB_FLAG,
  REFRESHTIME,
  CHILDSTATUS
) AS (

  SELECT
  DISTINCT  prd.partnumber,
            sbb.SBBPNUMB,
            prd.countrycode,
            rtrim(CHAR(multi.longdescription,13)),
            prd.productclass,
            sbb.SBBPLANNINGRELEV,
            a.anndate,
            a.wdate,
            seq.sequence,
            seq.pub_flag,
            prd.refreshtime,
            sbb.SBBSTATUS

  FROM      int.avwwprd prd

  JOIN      eccm.sbballdates a on
            a.entityid = prd.prdentityid and
            a.entitytype = prd.prdentitytype and
            a.nlsid = prd.prdnlsid

  JOIN      eccm.sbb sbb on
            sbb.entityid = a.sbbid and
            sbb.nlsid = a.nlsid

  JOIN      eccm.audience multi on
            multi.entityid = prd.prdentityid and
            multi.entityname = prd.prdentitytype and
            multi.nlsid = prd.prdnlsid

  LEFT
  JOIN      int.avprdprdseq seq on
            seq.parentpartnumber = prd.partnumber  and
            seq.childpartnumber = sbb.SBBPNUMB and
            seq.countrycode = prd.countrycode

  WHERE     ((prd.prdentitytype IN ('CCTO') and prd.productclass = 'CTO') or
            (prd.prdentitytype IN ('CVAR') and prd.productclass = 'V'))
            and ((a.wdate is NULL) OR
            (a.wdate >=current date - 14 days))

  UNION ALL

  SELECT
  DISTINCT  prd.partnumber,
            childprd.partnumber,
            prd.countrycode,
            rtrim(CHAR(multi.longdescription,13)),
            prd.productclass,
            '',
            CAST(NULL AS DATE),
            CAST(NULL AS DATE),
            seq.sequence,
            seq.pub_flag,
            prd.refreshtime,
            childprd.fotstatus

  FROM      int.avwwprd prd

  JOIN      eccm.cbparts cbs ON
            prd.prdentitytype = cbs.entitytype and
            prd.prdentityid = cbs.entityid and
            cbs.nlsid = prd.prdnlsid

  JOIN      eccm.cbcsol rel ON
            prd.prdentityid=rel.ID1

  JOIN      int.avwwprd childprd ON
            childprd.prdentityid = rel.ID2 and
            childprd.prdnlsid = prd.prdnlsid and
            childprd.prdentitytype = 'CSOL'

  JOIN      int.effectiveprice price ON
            price.parentpartnumber = childprd.partnumber and
            price.countrycode = childprd.countrycode

  JOIN      eccm.audience multi on
            multi.entityid = prd.prdentityid and
            multi.entityname = prd.prdentitytype and
            multi.nlsid = prd.prdnlsid

  LEFT
  JOIN      int.avprdprdseq seq on
            seq.parentpartnumber = prd.partnumber and
            seq.childpartnumber = childprd.partnumber and
            seq.countrycode = prd.countrycode

  WHERE     (prd.prdentitytype IN ('CB') and prd.productclass = 'CB')

  UNION ALL

  SELECT
  DISTINCT  prd.partnumber,
            childprd.partnumber,
            prd.countrycode,
            rtrim(CHAR(multi.longdescription,13)),
            prd.productclass,
            '',
            CAST(NULL AS DATE),
            CAST(NULL AS DATE),
            seq.sequence,
            seq.pub_flag,
            prd.refreshtime,
            childprd.fotstatus

  FROM      int.avwwprd prd

  JOIN      eccm.cbparts cbs ON
            prd.prdentitytype = cbs.entitytype and
            prd.prdentityid = cbs.entityid and
            cbs.nlsid = prd.prdnlsid

  JOIN      eccm.cbcvar rel ON
            prd.prdentityid=rel.ID1

  JOIN      int.avwwprd childprd ON
            childprd.prdentityid = rel.ID2 and
            childprd.prdnlsid = prd.prdnlsid and
            childprd.prdentitytype = 'CVAR'

  JOIN      int.effectiveprice price ON
            price.parentpartnumber = childprd.partnumber and
            price.countrycode = childprd.countrycode

  JOIN      eccm.audience multi on
            multi.entityid = prd.prdentityid and
            multi.entityname = prd.prdentitytype and
            multi.nlsid = prd.prdnlsid
  LEFT
  JOIN      int.avprdprdseq seq on
            seq.parentpartnumber = prd.partnumber and
            seq.childpartnumber = childprd.partnumber and
            seq.countrycode = prd.countrycode

  WHERE     (prd.prdentitytype IN ('CB') and prd.productclass = 'CB'))

;


--------------------------------------------
-- CALLED OUT IN TABLE SPEC
-- CONVERTED DWB
-- COMPILED DWB
-- CHECKED FOR FINAL SCRIPT
--------------------------------------------
DROP VIEW eccm.wwcatmtmrel;
CREATE VIEW eccm.wwcatmtmrel
--------------------------------------------
(
    identifier,
    partnumber,
    mastnav,
    countrycode,
    language,
    audience,
    sequence
)
  AS (

      SELECT      psbf.FAMSERNAME,
                  prd.partnumber,
                  'M',
                  prd.countrycode,
                  prd.language,
                  rtrim(CHAR(multi.longdescription,13)),
                  seq.sequence

      FROM        int.avwwprd prd

      JOIN        eccm.prdroot root on
                  prd.prdentityid = root.entityid and
                  prd.prdentitytype = root.entitytype and
                  prd.prdnlsid = root.nlsid

      JOIN        ECCM.of of on
                  root.ofentityid = of.entityid and
                  root.nlsid =of.nlsid

      JOIN        eccm.projserbrandfam psbf on
                  psbf.projectid =root.projectid

      JOIN        eccm.audience multi on
                  multi.entityid = root.entityid and
                  multi.entityname = root.entitytype and
                  multi.nlsid = root.nlsid

      LEFT JOIN   int.avprdcatseq seq on
                  seq.catidentifier = psbf.famsername and
                  seq.partnumber = prd.partnumber and
                  seq.countrycode = prd.countrycode and
                  seq.language = prd.language and
                  multi.longdescription = Upper(seq.audience)

      WHERE       prd.productclass = 'MTM' AND
                  of.OPTGROUPNAME is NULL AND
                  of.subgroup is NULL AND
                  (seq.pub_flag is NULL OR seq.pub_flag = 1)
);



------------------------------------------
-- CONVERTED DWB
-- COMPILED DWB
-- UNIT TESTED DWB
-- CALLED OUT IN TABLE SPEC DWB
-- CHECKED FOR FINAL SCRIPT
------------------------------------------
drop view eccm.compat_deltaforce;
create view eccm.compat_deltaforce
(
    ENTITYTYPE,
    WWPARTNUM,
    OPTIONPNUMB,
    MTM,
    OPERATINGSYS,
    EXCEPTMODEL,
    DELTA_INDICATOR
) as

  with compat_delta_indicator (delta_indicator) as (

    select    max(delta_indicator)

    from      int.teamcatalog_deltaforce

    where     catalog='compat'
  )

  select    ENTITYTYPE,
            WWPARTNUM,
            OPTIONPNUMB,
            MTM,
            OPERATINGSYS,
            EXCEPTMODEL,
            DELTA_INDICATOR

  from      eccm.compat

  join      compat_delta_indicator on 1=1

 ;



------------------------------------------
-- CONVERTED DWB
-- COMPILED DWB
-- UNIT TESTED DWB
-- GOT FROM MTM
------------------------------------------
drop view eccm.translated_attr;
------------------------------------------
create view eccm.translated_attr
(
  attribtoken,
  language,
  isocode,
  ibmlangcode,
  attrsourcename,
  attribname,
  attrgroup,
  grouptoken,
  attrsort,
  comparisononly,
  lastupdated,
  default_value

) as

  with eccm.attrnls (
    attributetoken,
    language,
    isocode,
    ibmlangcode,
    attrsourcename,
    grouptoken,
    groupname,
    attrsort,
    comparisononly,
    lastupdated
  ) as (

    select  attr.attributetoken,
            nls.nlsid,
            nls.isolangcode2,
            nls.ibmlangcode,
            attr.description,
            group.grouptoken,
            group.description,
            attr.attrsortorder,
            'N',
            attr.valfrom

    from    eccm.psgnls nls,
            eccm.attribute attr,
            eccm.attrgroup group

    where   attr.entitytype = group.entitytype

    union

    select  eccmattr.attributetoken,
            nls.nlsid,
            nls.isolangcode2,
            nls.ibmlangcode,
            eccmattr.attributename,
            coalesce(group.grouptoken,eccmatgp.grouptoken),
            coalesce(group.description,eccmatgp.groupname),
            eccmattr.attrsort, eccmattr.comparisononly,
            eccmattr.lastupdated

    from    eccm.psgnls nls,
            int.eccmattr eccmattr

    left    outer join eccm.attrgroup group on
            eccmattr.grouptoken = group.grouptoken

    left    outer join int.eccmatgp eccmatgp on
            eccmattr.grouptoken= eccmatgp.grouptoken

    where   eccmattr.comparisononly='N'

  )

  select
  distinct  attr.attributetoken,
            language,
            isocode,
            ibmlangcode,
            attrsourcename,
            coalesce(label.attributelabel, attrsourcename),
            groupname,
            attr.grouptoken,
            attrsort,
            comparisononly,
            attr.lastupdated,
            coalesce(label.attributelabel, 'FALSE')

  from      eccm.attrnls attr

  left      outer join int.attrlabel label on
            attr.attributetoken=label.attributetoken and
            attr.language=label.nlsid
;

------------------------------------------
-- CONVERTED DWB
-- COMPILED DWB
-- UNIT TESTED DWB
-- GOT FROM MTM
-- REMOVED REDUNDANT UNION IN WITH table
------------------------------------------
drop view eccm.translated_attrgroup;
create view eccm.translated_attrgroup as
with eccm.attrnls (
  grouptoken,
  language,
  isocode,
  ibmlangcode,
  groupsourcename,
  groupsort

  ) as (

    select      ag.grouptoken,
                pn.nlsid,
                pn.isolangcode2,
                pn.ibmlangcode,
                ag.description,
                ag.groupsort

    from        eccm.psgnls pn,
                eccm.attrgroup ag
    )

    select    attr.grouptoken,
              language,
              isocode,
              ibmlangcode,
              groupsourcename,
              groupsort,
              coalesce(al.attributelabel, groupsourcename) as groupname,
              lastupdated,
              coalesce(al.attributelabel, 'FALSE') as default_value

    from      eccm.attrnls attr

    left      join int.attrgrouplabel al on
              attr.grouptoken=al.grouptoken and
              attr.language=al.nlsid

;

------------------------------------------
-- CONVERTED DWB
-- COMPILED DWB
-- UNIT TESTED DWB
-- GOT FROM MTM
-- ADDED 'PSG'
------------------------------------------
drop view eccm.attrgroupview;
create view eccm.attrgroupview as
------------------------------------------
select
distinct    coalesce(tg.groupname,groupsourcename) as groupname,
            countrycode,
            isocode as language,
            attributetoken

from        eccm.psgctnls nls,
            eccm.translated_attrgroup tg,
            eccm.attrgroup ag,
            eccm.attribute at

where       tg.language=nls.nlsid and
            ag.grouptoken=tg.grouptoken and
            ag.grouptoken='PSG' || at.entitytype

union

select
distinct    coalesce(tg.groupname,groupsourcename) as groupname,
            countrycode,
            isocode as language,
            attributetoken

from        eccm.psgctnls nls,
            eccm.translated_attrgroup tg,
            int.eccmattr at

where       tg.language=nls.nlsid and
            at.grouptoken=tg.grouptoken
;

------------------------------------------
-- CONVERTED DWB
-- COMPILED DWB
-- UNIT TESTED DWB
-- GOT FROM MTM
-- ADDED EXTRA PREDICATE TO THE WHERE CLAUSE
-- OF THE SECOND UNION
------------------------------------------
drop view eccm.attributeview;
create view eccm.attributeview as
------------------------------------------
select
distinct    coalesce(ta.attribname,attrsourcename) as attribname,
            countrycode,
            isocode as language,
            attributetoken,
            comparisononly

from        eccm.psgctnls ctnls,
            eccm.translated_attr ta,
            eccm.attribute at

where       ta.language=ctnls.nlsid and
            at.attributetoken=ta.attribtoken

union

select
distinct    coalesce(ta.attribname,attrsourcename) as attribname,
            countrycode,
            isocode as language,
            attributetoken,
            at.comparisononly

from        eccm.psgctnls ctnls,
            eccm.translated_attr ta,
            int.eccmattr at

where       ta.language=ctnls.nlsid and
            at.attributetoken=ta.attribtoken
            and at.comparisononly='N'
union

select
distinct    attributename as attribname,
            countrycode,
            isolangcode2 as language,
            attributetoken,
            comparisononly

from        eccm.psgctnls,
            eccm.psgnls,
            int.eccmattr

where       comparisononly='Y' and
            eccm.psgctnls.nlsid=eccm.psgnls.nlsid and 
            int.eccmattr.language=isolangcode2
;

------------------------------------------
-- CONVERTED DWB
-- COMPILED DWB
-- UNIT TESTED DWB
-- GOT FROM MTM
------------------------------------------
drop view eccm.finalimage;
create view eccm.finalimage
(
  parententitytype,
  parententityid,
  nlsid,
  imageid,
  colorimagename,
  datefrom,
  dateto
) as
  with tmp (parenttype, parentid, parentnlsid, datefrom)
  as (

  select    parententitytype ,
            parententityid ,
            parentnlsid,
            max(datefrom)

  from      eccm.prdimage

  where     datefrom <= date(current timestamp) and
            (dateto >= date(current timestamp) or dateto is null)

  group
  by        parententitytype,parententityid,parentnlsid

  )

  select    a.parententitytype,
            a.parententityid,
            a.parentnlsid,
            a.imageid,
            a.colorimagename,
            a.datefrom,
            a.dateto

  from      eccm.prdimage a,
            tmp

  where     a.parententitytype=tmp.parenttype and
            a.parententityid=tmp.parentid and a.parentnlsid=tmp.parentnlsid and
            a.datefrom=tmp.datefrom
  ;

------------------------------------------
-- Called out in Table SPec
-- Converted DWB
-- COMPILED DWB
-- Added NLS ID
-- CHANGES PER MT
-- MADE CHANGES FOR ATTRIBUTETOKEN
-- CHECKED FOR FINAL SCRIPT
-- ADDED SOFTWARE UNION ALL
-- CHANGED PER CR1105032935
-- ANOTHER CHANGE PER MTM
-- ADDED RTRIM TO VALUE
-- VARIANT CHANGES
------------------------------------------
drop view eccm.ctattr;
create view eccm.ctattr
------------------------------------------
(
    parententitytype,
    parententityid,
    nlsid,
    attributetoken,
    value
) as (

  select
  distinct  c.entity1type,
            c.entity1id,
            a.nlsid,
            rtrim('CT_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = a.attributecode and att.entitytype = a.entitytype)),
            rtrim(a.attributevalue)

  from      ECCM.prodattribute a

  join      ECCM.prodattrelator c on
            c.entity2type=a.entitytype and
            c.entity2id=a.entityid and
            c.entity1type='CSOL'

  join      eccm.prdroot prd on
            prd.entitytype='CSOL' and
            prd.entityid=c.entity1id and
            prd.nlsid=a.nlsid

union all
--
--  COUNTRY VARIANT ADDITION
--
select
distinct    'CVAR',
            cvar.entityid,
            cvar.nlsid,
            rtrim('CT_' concat (select  att.attributetoken from eccm.attribute att where att.attributecode = a.attributecode and att.entitytype = a.entitytype)),
            rtrim(a.attributevalue)

from        eccm.cvar cvar

join        ECCM.cvarsbb cvarsbb on
            cvarsbb.ID1 = cvar.entityid and
            cvarsbb.nlsid =1

join        ECCM.prodattrelator c on
            c.entity1id=cvarsbb.id2 and
            c.entity1type='SBB'

join        ECCM.prodattribute a on
            a.entitytype = c.entity2type and
            a.entityid = c.entity2id and
            a.nlsid = cvar.nlsid

union all

--
--  ADDED FROM CTATTR VIEW
--
select
distinct  c.entity1type,
          c.entity1id,
          a.nlsid,
          rtrim('CT_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = a.attributecode and att.entitytype = a.entitytype)),
          rtrim(a.attributevalue)

from      ECCM.prodattribute a

join      ECCM.prodattrelator c on
          c.entity2type=a.entitytype and
          c.entity2id=a.entityid and
          c.entity1type='CVAR'

join      eccm.prdroot prd on
          prd.entitytype='CVAR' and
          prd.entityid=c.entity1id and
          prd.nlsid=a.nlsid

union all

--
--  ADDED COUNTRY LEVEL SOFTWARE
--
select    software.entitytype,
          software.entityid,
          software.nlsid,
          CASE software.category_fc
              When '0010' Then 'CT_SODEVICE'
              When '0020' Then 'CT_SOMAIL'
              When '0030' Then 'CT_SOMULTI'
              When '0040' Then 'CT_SONET'
              When '0060' Then 'CT_SOOPT'
              When '0070' Then 'CT_SOOTH'
              When '0080' Then 'CT_SOPRD'
           END,
          rtrim(software.value)

  from     eccm.software software

  where   software.entitytype = 'CVAR' and
          category_FC in ( '0010','0020','0030','0040','0060','0070','0080')
          
  union all

  select
  distinct  'CSOL',
            csolsbb.id1,
            a.nlsid,
            rtrim('CT_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = a.attributecode and att.entitytype = a.entitytype)),
            rtrim(a.attributevalue)

  from      ECCM.prodattribute a

  join      ECCM.prodattrelator c on
            c.entity2type=a.entitytype and
            c.entity2id=a.entityid and
            c.entity1type='SBB'

  join     eccm.csolsbb csolsbb on
            csolsbb.id2=c.entity1id

  join      eccm.prdroot prd on
            prd.entitytype='CSOL' and
            prd.entityid=csolsbb.id1 and
            prd.nlsid=a.nlsid

  union all

  select    software.entitytype,
            software.entityid,
            software.nlsid,
            CASE software.category_fc
              When '0010' Then 'CT_SODEVICE'
              When '0020' Then 'CT_SOMAIL'
              When '0030' Then 'CT_SOMULTI'
              When '0040' Then 'CT_SONET'
              When '0060' Then 'CT_SOOPT'
              When '0070' Then 'CT_SOOTH'
              When '0080' Then 'CT_SOPRD'
            END,
            rtrim(software.value)

  from      eccm.software software

  where     software.entitytype = 'CSOL' and
            category_FC in ( '0010','0020','0030','0040','0060','0070','0080')

  );

-----------------------------------------
-- Called out in Table Spec
-- Converted DWB
-- Added NLS ID
-- COMPILED DWB
-- CONVERTED W MARY
-- CONVERTED FOR ATTIRB
-- CHECKED FOR FINAL SCRIPT
-- ADDED SOFTWARE UNION ALL
-- CHANGED PER CR1105032935
-- ADDED RTRIM
-- ADDED DISTINCT
-- REMOVED DISTINCT AND FIXED ATTRIBUTE TABLE
-- SIMPLIFED MORE WW_ *Changed solution
-- ADDED VARIANT LOGIC
-- CHANGED FOR VARIANT REQUEST CHANGE
----------------------------------------
drop view eccm.wwattr;
create view eccm.wwattr
----------------------------------------
(
  parententitytype,
  parententityid,
  nlsid,
  attributetoken,
  value

) as (

  select
  distinct  c.entity1type,
            c.entity1id,
            a.nlsid,
            rtrim('WW_' || (select att.attributetoken from eccm.attribute att where att.attributecode = a.attributecode and att.entitytype = a.entitytype)),
            rtrim(a.attributevalue)

  from      ECCM.prodattribute a

  join ECCM.prodattrelator c on
            c.entity2type= a.entitytype and
            c.entity2id=a.entityid and
            c.entity1type='OF'

  join eccm.prdroot prd on
            prd.entitytype='CSOL' and
            prd.ofentityid=c.entity1id and
            prd.ofentitytype = c.entity1type and
            prd.nlsid=a.nlsid

  --union all
--
  --
  -- REMOVE THE CTO STUFF
  --
  
  --select
  --distinct  c.entity1type,
  --          c.entity1id,
  --          a.nlsid,
  --          rtrim('WW_' concat att.attributetoken),
  --          rtrim(a.attributevalue)
--
  --from      ECCM.prodattribute a
--
  --join      ECCM.prodattrelator c on
  --          c.entity2type=a.entitytype and
  --          c.entity2id=a.entityid and
  --          c.entity1type='CTO'
--
  --join      eccm.prdroot prd on
  --          prd.entitytype='CCTO' and
  --          prd.ofentityid=c.entity1id and
  --          prd.nlsid=a.nlsid
--
  --left join eccm.attribute att on
  --          att.attributecode = a.attributecode and
  --          att.entitytype = a.entitytype

  union all

  select
  distinct
            'OF',
            ofsbb.id1,
            a.nlsid,
            rtrim('WW_' concat att.attributetoken),
            rtrim(a.attributevalue)

  from      ECCM.prodattribute a


  join ECCM.prodattrelator c on
            c.entity2type= a.entitytype and
            c.entity2id=a.entityid and
            c.entity1type='SBB'

  join eccm.ofsbb ofsbb on
       ofsbb.id2 = c.entity1id and
       ofsbb.nlsid = 1

  join eccm.prdroot prd on
            prd.entitytype='CSOL' and
            prd.ofentityid=ofsbb.id1 and
            prd.nlsid=a.nlsid

  left join eccm.attribute att on
            att.attributecode = a.attributecode and
            att.entitytype = a.entitytype

union all

select
distinct    c.entity1type,
            c.entity1id,
            a.nlsid,
            rtrim('WW_' concat att.attributetoken),
            rtrim(a.attributevalue)

from        ECCM.prodattribute a

join        ECCM.prodattrelator c on

            c.entity2type=a.entitytype and
            c.entity2id=a.entityid and
            c.entity1type='VAR'

join        eccm.prdroot prd on
            prd.entitytype='CVAR' and
            prd.ofentityid=c.entity1id and
            prd.nlsid=a.nlsid

left join eccm.attribute att on
            att.attributecode = a.attributecode and
            att.entitytype = a.entitytype

union all
select
            software.entitytype,
            software.entityid,
            software.nlsid,
            CASE software.category_fc
              When '0010' Then 'WW_SODEVICE'
              When '0020' Then 'WW_SOMAIL'
              When '0030' Then 'WW_SOMULTI'
              When '0040' Then 'WW_SONET'
              When '0060' Then 'WW_SOOPT'
              When '0070' Then 'WW_SOOTH'
              When '0080' Then 'WW_SOPRD'
            END,
            rtrim(software.value)

  from      eccm.software software

  where     software.entitytype in ('OF','VAR') and
            category_FC in ( '0010','0020','0030','0040','0060','0070','0080' )

)
;


--------------------------------------
-- CALLED OUT IN THE TABLE SPEC
-- CONVERTED DWB (ADDED NLS)
-- COMPILED DWB
-- REVIEWED W MTM
-- MADE CHANGES FOR ATTRIBUTETOKEN
-- CHECKED FOR FINAL SCRIPT
-- ADDED RTRIM
-- ADDED NLSID AND DISTINCT
-- MADE CHANGE CVAR.ENTITYID to CVAR.VARID
-- ADDED Distinct
-- REMOVED DISTINCT IN FAVOR OF TWEEKING ATTRIBUTE TABLE
-- ADD VARIANT LOGIC
-- COMPILED
--------------------------------------
drop view eccm.variantattr;
--------------------------------------
create view eccm.variantattr (
    parententitytype,
    parententityid,
    nlsid,
    attributetoken,
    value) as

select
distinct    'CVAR',
            cvar.entityid,
            cvar.nlsid,
            rtrim('WW_' concat (select  att.attributetoken from eccm.attribute att where att.attributecode = a.attributecode and att.entitytype = a.entitytype)),
            rtrim(a.attributevalue)

from        eccm.cvar cvar

join        ECCM.varsbb varsbb on
            varsbb.ID1 = cvar.varid and
            varsbb.nlsid =1

join        ECCM.prodattrelator c on
            c.entity1id=varsbb.id2 and
            c.entity1type='SBB'

join        ECCM.prodattribute a on
            a.entitytype = c.entity2type and
            a.entityid = c.entity2id and
            a.nlsid = cvar.nlsid
;


------------------------------------------
-- New View per MTM and CR7659
-- ADDED RTRIM
-- VARIANT CHANGE
-- COMPILED OK
------------------------------------------
DROP VIEW eccm.ctmvattr;
CREATE VIEW eccm.ctmvattr
------------------------------------------
(entitytype
,entityid
,nlsid
,columnname
,longdescription
) AS (

select    'CSOL',
          csol.entityid,
          f.nlsid,
          'CT_PSGWARRANTYTYPE',
          rtrim(f.flagdescription)

from     eccm.csol csol


join 	eccm.csolwar csolwar on 
	csolwar.id1 = csol.entityid and 
	csolwar.nlsid = 1 

join 	eccm.flag f on 
	f.entitytype = 'WAR' and 
	f.entityid = csolwar.id2 and 
	f.sfvalue = 1 and 
	f.nlsid = csol.nlsid and 
	f.attributecode='WARRANTYTYPE' 

union all

select 	'CVAR', 
	cvar.entityid, 
	f.nlsid,'CT_PSGWARRANTYTYPE', 
	rtrim(f.flagdescription) 
	
from 	eccm.cvar cvar 

join 	eccm.cvarwar cvarwar on 
	cvarwar.id1 = cvar.entityid and 
	cvarwar.nlsid = 1 
	
join 	eccm.flag f on 
	f.entitytype = 'WAR' and 
	f.entityid = cvarwar.id2 and 
	f.sfvalue = 1 and 
	f.nlsid = cvar.nlsid and 
	f.attributecode='WARRANTYTYPE' 
	
union all

select 	'CVAR', 
	cvar.entityid, 
	f.nlsid,'CT_PSGWSSPEED', 
	rtrim(f.flagdescription) 

from 	eccm.cvar cvar 

join  	eccm.cvarsbb cvarsbb on 
	cvarsbb.id1= cvar.entityid  
	
join 	eccm.sbbws sbbws on 
	sbbws.id1 = cvarsbb.id2 and 
	sbbws.nlsid = 1 
	
join	eccm.flag f on 
	f.entitytype ='WS' and 
	f.entityid = sbbws.id2 and 
	f.sfvalue = 1  and 
	f.nlsid=cvar.nlsid and 
	f.attributecode='WSSPEED'
	
union all 

select 	'CVAR', 
	cvar.entityid, 
	f.nlsid , 
	concat('CT_PSG', f.attributecode), 
	rtrim(f.flagdescription) 

from 	eccm.cvar cvar 

join	eccm.cvarsbb cvarsbb on 
	cvarsbb.id1= cvar.entityid  

join 	eccm.sbbfm sbbfm on
	sbbfm.id1 = cvarsbb.id2 and 
	sbbfm.nlsid = 1 
	
join 	eccm.flag f on 
	f.entitytype ='FM' and 
	f.entityid = sbbfm.id2 and 
	f.sfvalue = 1 and 
	f.nlsid=cvar.nlsid and 
	f.attributecode in ('DATARATEFAX','DATARATEMODEM','DATARATE')

);



------------------------------------------
-- CONVERTED DWB
-- COMPILED DWB
-- UNIT TESTED DWB
-- CALLED OUT IN TABLE SPEC DWB
-- CONVERTED FOR ATTRIBUTE TOKEN
-- CHECKED FOR FINAL SCRIPT
-- ADDED NLSID JOIN
-- ADDED RTRIM
-- ADDED PORT INFO
-- ADDED FIX FOR MTM
-- CHANGED FOR VARIANTS
------------------------------------------
DROP VIEW eccm.wwmvattr;
CREATE VIEW eccm.wwmvattr
------------------------------------------
(entitytype
,entityid
,nlsid
,columnname
,longdescription
) AS (

select 
	'OF', 
	pad.entity1id, 
	f.nlsid, 
	rtrim('WW_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), 
	rtrim(f.flagdescription) from eccm.prodattrelator pad 
	
join	eccm.flag f on 
	f.entitytype = pad.entity2type and 
	f.entityid = pad.entity2id and 
	f.sfvalue = 1 

where	pad.entity1type = 'OF' 

union all 

SELECT	'OF', 
	of.entityid, 
	of.nlsid, 
	rtrim('WW_PSG' || f.attributecode), (select rtrim(longdescription) 

from	eccm.metaflagtable 

where	attributecode = f.attributecode and ATTRIBUTEVALUE= f.flagcode and nlsid =of.nlsid) 

FROM	eccm.of of 

join	eccm.flag f on 
	f.entitytype = 'OF' and 
	f.entityid = of.entityid and 
	f.attributecode in ('OFAPPROVALS_CERTS') and 
	f.nlsid = of.nlsid 

union all

select	'OF', 
	ofport.id1, 
	p.nlsid, 
	case p.PORTCATEGORY_FC 
		when '11108' THEN 'WW_PSGSERIALPORTTYPE_PL' 
		when '11107' THEN 'WW_PSGPARA_PORTTYPE_PL' 
		when '11106' THEN 'WW_PSGEXPANPORTTYPE' 
		when '11105' THEN 'WW_PSGAUDIOPORTNAME' 
	end case, 
	p.PortType 

from	eccm.ofport ofport 

join	eccm.port p on 
	p.entityid = ofport.id2 
	
where	p.portcategory_fc in ('11108','11107','11106','11105')

union all 

select	'OF', 
	ofport.id1, 
	p.nlsid, 
	'WW_PSGEXPANSIONPORTTYPE', 
	p.PortType 

from	eccm.ofport ofport 

join	eccm.port p on 
	p.entityid = ofport.id2 

where	p.portcategory_fc in ('11106')

union all 

select	'OF', 
	ofsbb.id1, 
	p.nlsid, 
	case p.PORTCATEGORY_FC 
		when '11108' THEN 'WW_PSGSERIALPORTTYPE_PL' 
		when '11107' THEN 'WW_PSGPARA_PORTTYPE_PL' 
		when '11106' THEN 'WW_PSGEXPANPORTTYPE' 
		when '11105' THEN 'WW_PSGAUDIOPORTNAME' 
	end case, 
	p.PortType 

from	eccm.ofsbb ofsbb 

join	eccm.sbbport sbbport on
	sbbport.id1 = ofsbb.id2 
	
join	eccm.port p on
	p.entityid = sbbport.id2 

where	p.portcategory_fc in ('11108','11107','11106','11105') 

union all 

select	'OF',
	ofsbb.id1,
	p.nlsid,
	'WW_PSGEXPANSIONPORTTYPE',
	p.PortType 

from	eccm.ofsbb ofsbb 

join	eccm.sbbport sbbport on
	sbbport.id1 = ofsbb.id2 
	
join	eccm.port p on
	p.entityid = sbbport.id2 
	
where	p.portcategory_fc in ('11106')

union all 

select	'OF', 
	ofsbb.ID1, 
	f.nlsid, 
	rtrim('WW_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), 
	rtrim(f.flagdescription) 
	
from	eccm.ofsbb ofsbb 

join	eccm.prodattrelator pr on
	pr.entity1type = 'SBB' and
	pr.entity1id = ofsbb.id2 
	
join	eccm.flag f on
	f.entitytype = pr.entity2type and
	f.entityid = pr.entity2id and 
	f.sfvalue = 1 
	
where	ofsbb.nlsid = 1

union all 

select	'VAR', 
	pad.entity1id,
	f.nlsid, 
	rtrim('WW_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), 
	rtrim(f.flagdescription) 
	
from	eccm.prodattrelator pad 

join	eccm.flag f on
	f.entitytype = pad.entity2type and
	f.entityid = pad.entity2id and
	f.sfvalue = 1 
	
where	pad.entity1type = 'VAR'

union all 

select	'VAR', 
	varsbb.ID1, 
	f.nlsid, 
	rtrim('WW_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), 
	rtrim(f.flagdescription) 
	
from	eccm.varsbb varsbb 

join	eccm.prodattrelator pr on
	pr.entity1type = 'SBB' and
	pr.entity1id = varsbb.id2 

join	eccm.flag f on
	f.entitytype = pr.entity2type and
	f.entityid = pr.entity2id and 
	f.sfvalue = 1 
	
where	varsbb.nlsid = 1

union all 

select	'VAR', 
	varsbb.id1, 
	p.nlsid, 
	case p.PORTCATEGORY_FC 
		when '11108' THEN 'WW_PSGSERIALPORTTYPE_PL' 
		when '11107' THEN 'WW_PSGPARA_PORTTYPE_PL' 
		when '11106' THEN 'WW_PSGEXPANPORTTYPE' 
		when '11105' THEN 'WW_PSGAUDIOPORTNAME' 
	end case, 
	p.PortType 

from	eccm.varsbb varsbb 

join	eccm.sbbport sbbport on 
	sbbport.id1 = varsbb.id2 
	
join	eccm.port p on 
	p.entityid = sbbport.id2 
	
where	p.portcategory_fc in ('11108','11107','11106','11105') 

union all 

select	'VAR', 
	varsbb.id1, 
	p.nlsid, 
	'WW_PSGEXPANSIONPORTTYPE', 
	p.PortType 

from	eccm.varsbb varsbb 

join	eccm.sbbport sbbport on 
	sbbport.id1 = varsbb.id2 
	
join	eccm.port p on
	p.entityid = sbbport.id2 
	
where	p.portcategory_fc in ('11106') 
);

drop view eccm.pack_complete;
create view eccm.pack_complete 
( 
  partnumber, 
  country, 
  nlsid, 
  wunits, 
  weight, 
  width, 
  height, 
  dunits, 
  depth 
) as 
select      csol.pnumb_ct, 
            ga.genareacode, 
            csol.nlsid, 
            max(COALESCE(pk.wunits,pk1.wunits)), 
            max(COALESCE(pk.weight,pk1.weight)), 
            max(COALESCE(pk.width,pk1.width)), 
            max(COALESCE(pk.height,pk1.height)), 
            max(COALESCE(pk.dunits,pk1.dunits)), 
            max(COALESCE(pk.depth,pk1.depth)) 

from        eccm.csol csol 

left join   eccm.package1 pk on  
            pk.entityid = csol.entityid and 
            pk.entitytype = 'CSOL' and 
            pk.nlsid = csol.nlsid 

join        eccm.of of on 
            of.entityid = csol.ofid and 
            of.nlsid = csol.nlsid
            
left join   eccm.package1 pk1 on  
            pk1.entityid = of.entityid and 
            pk1.entitytype = 'OF' and 
            pk1.nlsid = of.nlsid    

join        eccm.generalarea ga on 
            ga.genareaname_fc= csol.genareaname_fc and 
            ga.genareatype='Cty' 

join        int.avprice_state pp on 
            pp.partnumber=csol.pnumb_ct and 
            pp.countrycode= ga.genareacode and 
            pp.distributionchannel = '00' and 
            (pp.callforquote ='0' or pp.callforquote = '1') and 
            current date between date(pp.pricevalidfromdate) and date(pp.pricevalidtodate) 

join        eccm.psgctnls ctnls on 
            ctnls.countrycode = ga.genareacode and 
            ctnls.nlsid = pk1.nlsid 

join        int.avwwprd prd on prd.prdentitytype='CSOL' and 
            prd.prdentityid=csol.entityid and 
            prd.countrycode=ga.genareacode and 
            prd.prdnlsid=pk1.nlsid 

group by    csol.pnumb_ct, ga.genareacode, csol.nlsid 

union all   select cvar.pnumb_ct, 
            ga.genareacode, 
            cvar.nlsid, 
            max(COALESCE(pk.wunits,pk1.wunits)), 
            max(COALESCE(pk.weight,pk1.weight)), 
            max(COALESCE(pk.width,pk1.width)), 
            max(COALESCE(pk.height,pk1.height)), 
            max(COALESCE(pk.dunits,pk1.dunits)), 
            max(COALESCE(pk.depth,pk1.depth)) 

from        eccm.cvar cvar 

left join   eccm.package1 pk on  
            pk.entityid = cvar.entityid and 
            pk.entitytype = 'CVAR'and 
            pk.nlsid = cvar.nlsid 

join        eccm.var var on 
            var.entityid = cvar.varid and 
            var.nlsid = cvar.nlsid 

left        join eccm.package1 pk1 on  
            pk1.entityid = var.entityid and 
            pk1.entitytype = 'VAR'and 
            pk1.nlsid = var.nlsid 

join        eccm.generalarea ga on 
            ga.genareaname_fc= cvar.genareaname_fc and 
            ga.genareatype='Cty' 

join        int.avprice_state pp on 
            pp.partnumber=cvar.pnumb_ct and 
            pp.countrycode= ga.genareacode and 
            pp.distributionchannel = '00' and 
            (pp.callforquote ='0' or pp.callforquote = '1') and 
            current date between date(pp.pricevalidfromdate) and date(pp.pricevalidtodate) 

join        eccm.psgctnls ctnls on 
            ctnls.countrycode = ga.genareacode and 
            ctnls.nlsid = pk1.nlsid 

join        int.avwwprd prd on 
            prd.prdentitytype='CVAR' and 
            prd.prdentityid=cvar.entityid and 
            prd.countrycode=ga.genareacode and 
            prd.prdnlsid=pk1.nlsid 

group by  cvar.pnumb_ct, ga.genareacode, cvar.nlsid
;

------------------------------------------
-- CALLED OUT IN THE TABLE SPEC
-- CONVERTED DWB
-- COMPILED
-- CHECKED FOR FINAL SCRIPT
-- CHANGED FOR MTM
-- ADDED CHAR(8) and removed Upper
-- Fix for MN  18607849  - Install and test instructions (wwcatprdrel view ddl change)
------------------------------------------
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

;



--
--  Lets see what views are still marked as inoperative
--
select VIEWSCHEMA, viewname from syscat.views where valid!='Y';
