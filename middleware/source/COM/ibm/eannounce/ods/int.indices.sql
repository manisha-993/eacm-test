--
-- $Log: int.indices.sql,v $
-- Revision 1.1  2004/01/07 00:21:03  dave
-- more scripting
--
--

--
-- Indexes for INT.avprdat
--
DROP INDEX INT.AVPRDAT_IX1;
CREATE INDEX INT.AVPRDAT_IX1 ON INT.AVPRDAT(PRDENTITYID,PRDENTITYTYPE, PRDNLSID);
