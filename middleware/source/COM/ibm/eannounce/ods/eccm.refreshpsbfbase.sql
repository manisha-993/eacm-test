--
-- Script for refreshing the PROJSERVBRANDFAM
-- $Log: eccm.refreshpsbfbase.sql,v $
-- Revision 1.5  2005/03/16 20:17:43  dave
-- new pcd segmentation scripts
--
-- Revision 1.1.2.2  2004/03/24 00:57:25  dave
-- final changes for the delete timing problem in the ECCMODS
--
-- Revision 1.1.2.1  2004/03/12 19:46:43  dave
-- fixing refresh script to reflect new base table
--
-- Revision 1.1.2.2  2004/03/01 21:25:39  dave
-- lock/unlock/netfinity
--
-- Revision 1.1.2.1  2004/02/27 17:49:12  dave
-- removed Netfinity from projtable
--
--
LOCK table ECCM.PSBFBASE in exclusive mode;
DELETE FROM ECCM.PSBFBASE;
INSERT INTO ECCM.PSBFBASE
SELECT
 PR.NLSID as NLSID
,PR.ENTITYID AS PROJECTID
,SE.ENTITYID AS SERIESID
,BR.ENTITYID  AS BRANDID
,FAM.ENTITYID AS FAMILYID
,PR.DIVISIONCODE_FC 
,CASE PR.DIVISIONCODE_FC WHEN '44' THEN 'PCD' ELSE 'IBM' END
,PR.DIVISIONCODE
,SE.SERIESNAME AS SERIESNAME
,BR.SUBGROUPNAME AS BRANDNAME
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
'NetVista Thin Client','Appliance Division',
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
WHEN
  fam.familyname='Netfinity' and se.seriesname in ('1000','3000','3500','5000',
 '5100R','5500', '5500-M10','5500-M20','5600','56XX','5XXX','5XXXR','7100','6000R',
 '7000','7000-M10','7600', '7XXX','8500R','64EP','4000R','3500 M20','3500 M10','XXXX',
 '5100','4500R','4100R','A100', '3600','A200','A500') 
THEN 0
ELSE 1
END
;

