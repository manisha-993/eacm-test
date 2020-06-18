--
-- $Log: eccm.psbfbase.sql,v $
-- Revision 1.4  2005/03/16 20:17:43  dave
-- new pcd segmentation scripts
--
-- Revision 1.1.2.2  2004/03/24 00:57:24  dave
-- final changes for the delete timing problem in the ECCMODS
--
-- Revision 1.1.2.1  2004/03/12 00:04:48  dave
-- for the projbrandfamily split effort
--
--
-- New table to replace proj...
--
DROP TABLE ECCM.PSBFBASE;
CREATE TABLE ECCM.PSBFBASE (
 NLSID INT NOT NULL,
 PROJECTID INT NOT NULL,
 SERIESID INT NOT NULL,
 BRANDID INT NOT NULL,
 FAMILYID INT NOT NULL,
 DIVISIONCODE_FC CHAR(3),
 DIV_IND CHAR(3),
 DIVISIONCODE VARCHAR(50),
 SERIESNAME VARCHAR(50),
 BRANDNAME VARCHAR(50),
 FAMILYNAME VARCHAR(50),
 FAMSERNAME VARCHAR (100)
);
CREATE INDEX ECCM.PSBFBINDEX ON ECCM.PSBFBASE(PROJECTID ASC, NLSID ASC);

----------------------------------------------------------------------------------------
-- Re implementation of the summary table
--
----------------------------------------------------------------------------------------
DROP TABLE ECCM.PROJSERBRANDFAM;
CREATE SUMMARY TABLE ECCM.PROJSERBRANDFAM AS (SELECT * FROM ECCM.PSBFBASE)
DATA INITIALLY DEFERRED REFRESH DEFERRED;

REFRESH TABLE ECCM.PROJSERBRANDFAM;
CREATE INDEX ECCM.PSBFSINDEX ON ECCM.PROJSERBRANDFAM(PROJECTID ASC, NLSID ASC);

