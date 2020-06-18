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

  JOIN      eccm.cbcvar rel O
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
