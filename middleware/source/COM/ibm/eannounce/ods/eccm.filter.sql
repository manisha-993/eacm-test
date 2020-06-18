--
-- $Log: eccm.filter.sql,v $
-- Revision 1.2  2004/05/07 16:33:46  dave
-- New maintenance scripts
--
--
--
-- CSOL status:
-- CSOLSTATUS:0020 - Final
-- CSOLSTATUS:0040 - Ready For Review
--------------------
insert into opicm.metalinkattr values ('L2','Entity/Status/ODSFilter','CSOL','ECCM','CSOLSTATUS','0020','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',777,777);
insert into opicm.metalinkattr values ('L2','Entity/Status/ODSFilter','CSOL','ECCM','CSOLSTATUS','0040','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',777,777);

-- CVAR status
---------------
-- STATUS_CVAR:0020 - Final
-- STATUS_CVAR:0040 - Ready For Review
----------------------------------
insert into opicm.metalinkattr values ('L2','Entity/Status/ODSFilter','CVAR','ECCM','STATUS_CVAR','0020','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',777,777);
insert into opicm.metalinkattr values ('L2','Entity/Status/ODSFilter','CVAR','ECCM','STATUS_CVAR','0040','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',777,777);

-- CCTO status
-- CCOSOLSTATUS:0020 - Final
-- CCOSOLSTATUS:0040 - Ready For Review
-----------------------------------------
insert into opicm.metalinkattr values ('L2','Entity/Status/ODSFilter','CCTO','ECCM','CCOSOLSTATUS','0020','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',777,777);
insert into opicm.metalinkattr values ('L2','Entity/Status/ODSFilter','CCTO','ECCM','CCOSOLSTATUS','0040','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',777,777);

-- CB status
-- CBSOLSTATUS:0010 Final
---------------------------
insert into opicm.metalinkattr values ('L2','Entity/Status/ODSFilter','CB','ECCM','CBSOLSTATUS','0010','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',777,777);

-- SSB status
-- SBBSTATUS:0010 Final
-- SBBSTATUS:0040:Ready For review
-----------------------------------
insert into opicm.metalinkattr values ('L2','Entity/Status/ODSFilter','SBB','ECCM','SBBSTATUS','0010','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',777,777);
insert into opicm.metalinkattr values ('L2','Entity/Status/ODSFilter','SBB','ECCM','SBBSTATUS','0040','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',777,777);
