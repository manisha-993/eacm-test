--
--
-- $Id: setupdata.sql,v 1.5 2005/10/04 17:36:45 dave Exp $
--
-- $Log: setupdata.sql,v $
-- Revision 1.5  2005/10/04 17:36:45  dave
-- Category
--
-- Revision 1.4  2005/09/13 16:35:26  dave
-- more VE defs
--
-- Revision 1.3  2005/09/13 04:14:32  dave
-- ok.. lets add the PROJCDNAM, and lets take a hard look at
-- WWSEO and how to get prod structures
--
-- Revision 1.2  2005/09/12 00:28:09  dave
-- adding netter files
--
-- Revision 1.1  2005/09/12 00:12:24  dave
-- new sql file to quickly ensure everything is accurate
-- in our catdb setup for data
--
--
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- This is all the setup data we will need to ensure the 
-- CatDb functions properly
--
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
--  Gami Mapping Information
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
delete from gbli.gami;
insert into gbli.gamap values('SG','WW','ca','fr',5,'1501');
insert into gbli.gamap values('SG','WW','ja','jp',4,'1535');
insert into gbli.gamap values('SG','WW','uk','en',7,'1651');
insert into gbli.gamap values('SG','WW','us','en',1,'1552');

--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
--  Net Change Processing  Mapping Information
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
drop table opicm.trsNetterPass1;
create table opicm.trsNetterPass1  
(
Marker INT NOT NULL,
Enterprise char(8) not null,
Sessionid INT not null, 
TRAN CHAR(3) NOT NULL,
Level INT NOT NULL,
Entity1type CHAR(32) NOT NULL,
Entity1id INT NOT NULL,
EntityType CHAR(32) NOT NULL,
EntityID INT NOT NULL,
Entity2Type CHAR(32) NOT NULL,
Entity2ID INT NOT NULL,
Direction Char(1) Not Null,
CoreType CHAR(32) NOT NULL,
CoreID INT NOT NULL,
CoreLevel INT NOT NULL,  
CoreTran Char(3) not null, 
CoreDirection Char(1) not null, 
Type CHAR(2) NOT NULL, 
Valfrom TIMESTAMP NOT NULL,
CorePath VARCHAR(254) NOT NULL
)

;

create index opicm.trsNetterPass1_ix1 on opicm.trsNetterPass1(sessionid, type, enterprise);
create index opicm.trsNetterPass1_ix2 on opicm.trsNetterPass1(SessionID,Marker,TRAN,Enterprise);
create index opicm.trsNetterPass1_ix3 on opicm.trsNetterPass1(sessionid,level, entity2type, entity2id);
create index opicm.trsNetterPass1_ix4 on opicm.trsNetterPass1(sessionid,level, entitytype, entityid);
create index opicm.trsNetterPass1_ix5 on opicm.trsNetterPass1(sessionid,level, entity1type, entity1id);
create index opicm.trsNetterpass1_ix6 on opicm.trsNetterPass1(coreid, coretype,sessionid, marker);
runstats on table opicm.trsNetterPass1 and detailed indexes all;


DROP TABLE OPICM.TRSNETTERPASS2;
CREATE TABLE OPICM.TRSNETTERPASS2(
  Enterprise CHAR(8) NOT NULL
, SessionidINTEGER NOT NULL
, ActionType Char(32) Not Null
, Level INTEGER NOT NULL
, MyDirection CHAR(1) NOT NULL
, ParentDirection CHAR(1) NOT NULL
, ENTITY1TYPE CHAR(32) NOT NULL
, ENTITY1ID INTEGER NOT NULL
, ENTITYTYPE CHAR(32) NOT NULL
, ENTITYID INTEGER NOT NULL
, ENTITY2TYPE CHAR(32) NOT NULL
, ENTITY2ID INTEGER NOT NULL
, Tran CHAR(3) NOT NULL 
, ValFrom TIMESTAMP NOT NULL
, RootType CHAR(32) NOT NULL
, RootID INT NOT NULL
, RootTran CHAR(3) NOT NULL
, Path VARCHAR(254) NOT NULL
)
;

CREATE index OPICM.TRSNETTERPASS2_IX1 on OPICM.TRSNETTERPASS2(SESSIONID,level, ENTITY1TYPE,ENTITY1ID);
CREATE index OPICM.TRSNETTERPASS2_IX2 on OPICM.TRSNETTERPASS2(SESSIONID,level, ENTITY2TYPE,ENTITY2ID);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
CREATE index OPICM.TRSNETTERPASS2_IX3 on OPICM.TRSNETTERPASS2(SESSIONID,level, ENTITYTYPE, ENTITYID);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  

DROP TABLE OPICM.TRSNETTERFINAL;
CREATE TABLE OPICM.TRSNETTERFINAL(
  Enterprise CHAR(8) NOT NULL
, SessionId INTEGER NOT NULL
, ActionType CHAR(32) NOT NULL
, StartDate TIMESTAMP NOT NULL
, EndDate TIMESTAMP NOT NULL
, Valfrom TimeStamp NOT NULL
, RootType CHAR(32) NOT NULL
, RootID INT NOT NULL
, RootTran CHAR(3) NOT NULL
, ChildType CHAR(32) NOT NULL
, ChildID INT NOT NULL
, ChildTran CHAR(3) NOT NULL
, ChildLevel INT NOT NULL
, ChildClass CHAR(1) NOT NULL
, ChildPath VARCHAR(254) NOT NULL
, Entity1type CHAR(32) NOT NULL
, Entity1id INT NOT NULL
, Entity2Type CHAR(32) NOT NULL
, Entity2ID INT NOT NULL
)
;
create index opicm.trsNetterFinal_ix1 on opicm.trsNetterFinal(SessionID, RootID,RootType, Enterprise);
create index opicm.trsNetterFinal_ix2 on opicm.trsNetterFinal(SessionID, ChildID,ChildType, Enterprise);

--
-- ALL VE related information
--
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- Collateral Collection VE's
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- =========================
-- DB2 Inserts for the VEMM1
-- =========================
delete from opicm.metalinkattr where enterprise = 'SG' and linktype1= 'VEMM1';
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','WWSEOMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','MODELMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','SERMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','FMLYMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','LSEOBUNDLEMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','MODELWWSEO','U','1','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','MODELWWSEO','D','1','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
-- =========================
--DB2 Inserts for the VEIMG1
--==========================
delete from opicm.metalinkattr where enterprise = 'SG' and linktype1= 'VEIMG1';
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','WWSEOIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','MODELIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','SERIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','FMLYIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','LSEOBUNDLEIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
-- =========================
--DB2 Inserts for the VEFB1
--==========================
delete from opicm.metalinkattr where enterprise = 'SG' and linktype1= 'VEFB1';
insert into opicm.metalinkattr values ('SG','Action/Entity','VEFB1','WWSEOFB','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEFB1','MODELFB','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEFB1','LSEOBUNDLEFB','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- WorldWideProduct Collection VE's
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- =========================
--DB2 Inserts for the MODEL	
--==========================
DELETE FROM OPICM.metalinkattr where enterprise = 'SG' and linktype1 = 'VETMF1';
INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VETMF1','PRODSTRUCT','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
--INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VETMF1','PROJMODEL','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
--INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VETMF1','COFTECHCAP','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
--=========================
--DB2 Inserts for the WWSEO
--==========================
 DELETE FROM OPICM.metalinkattr where enterprise = 'SG' and linktype1 = 'VEWWSEO1';
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','WWSEOBAY','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','WWSEODERIVEDDATA','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 --INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','WWSEOFB','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 --INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','WWSEOIMG','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 --INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','WWSEOMM','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','WWSEOPRODSTRUCT','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','WWSEOSLOT','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 --INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','WWSEOWARR','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','MODELWWSEO','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 --INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','PRODSTRUCT','U','1','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 --INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','PROJWWSEO','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 --INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','COFTECHCAP','D','1','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- Product Collection VE's
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
--=========================
--DB2 Inserts for the MODEL (DERIVED LCTO)	
--==========================
DELETE FROM OPICM.metalinkattr where enteprise = 'SG' and linktype1 = 'VEMODELPRODUCT1';
INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEMODELPRODUCT1','PRODSTRUCT','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEMODELPRODUCT1','OOFAVAIL','D','1','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEMODELPRODUCT1','AVAILANNA','D','2','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
--=========================
--DB2 Inserts for the SEO
--==========================
 DELETE FROM OPICM.metalinkattr where enteprise = 'SG' and linktype1 = 'VELSEO1';
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VELSEO1','LSEOAVAIL','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VELSEO1','LSEOPRODSTRUCT','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','WWSEOLSEO','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VEWWSEO1','AVAILANNA','D','1','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','VELSEO1','PRODSTRUCT','D','1','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- Feature Collection VE's
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
--=========================
--DB2 Inserts for the EXTCOMPONENT01
--==========================
 DELETE FROM OPICM.metalinkattr where enteprise = 'SG' and linktype1 = 'EXTCOMPONENT01';
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREAUD','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATURECABLE','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATURECNTRYPACK','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREENVIRNMTLINFO','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREFAXMODM','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREGRPHADAPTER','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREHDC','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREHDD','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREINPUTDVC','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREKEYBD','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATURELANGPACK','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREMECHPKG','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREMECHRACKCAB','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREMEMORY','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREMONITOR','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATURENIC','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREOPTCALDVC','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREPLANAR','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATPORT','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREPRN','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREPROC','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATUREPWRSUPPLY','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATURESPEAKER','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATURETAPEDRIVE','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATURETECHINFO','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATURETECHINFOFEAT','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FCTECHCAP','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);
 INSERT INTO OPICM.metalinkattr values('SG','Action/Entity','EXTCOMPONENT01','FEATURECCIN','D','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', 7, 7);

--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- Collateral Collection VE's
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
--=========================
--DB2 Inserts for the VEMM1
--=========================
delete from opicm.metalinkattr where enterprise = 'SG' and linktype1= 'VEMM1';
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','WWSEOMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','MODELMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','SERMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','FMLYMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','LSEOBUNDLEMM','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','MODELWWSEO','U','1','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEMM1','MODELWWSEO','D','1','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
--=========================
--DB2 Inserts for the VEIMG1
--=========================
delete from opicm.metalinkattr where enterprise = 'SG' and linktype1= 'VEIMG1';
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','WWSEOIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','MODELIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','SERIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','FMLYIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEIMG1','LSEOBUNDLEIMG','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
--=========================
--DB2 Inserts for the VEFB
--=========================
delete from opicm.metalinkattr where enterprise = 'SG' and linktype1= 'VEFB1';
insert into opicm.metalinkattr values ('SG','Action/Entity','VEFB1','WWSEOFB','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEFB1','MODELFB','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
insert into opicm.metalinkattr values ('SG','Action/Entity','VEFB1','LSEOBUNDLEFB','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7,7);
    
