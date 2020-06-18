------------------------------------------------------------------------------------------
--  Additional Indexes Product Family Structures
--  CHECKED FOR FINAL SCRIPT
------------------------------------------------------------------------------------------
-- $Log: eccm.indices.sql,v $
-- Revision 1.9  2004/01/27 19:23:00  dave
-- more script changes
--
-- Revision 1.8  2004/01/26 19:03:50  dave
-- more ECCM minor updates to script files
--
-- Revision 1.7  2004/01/17 03:17:32  dave
-- back out change
--
-- Revision 1.6  2004/01/06 18:53:27  dave
-- added indexes to help passi and the ProductMarketingData pull
--
-- Revision 1.5  2003/12/31 19:54:56  dave
-- refinements
--
-- Revision 1.4  2003/12/22 21:24:53  dave
-- more primary key fixing up so it is no longer part of the
-- table def
--
-- Revision 1.3  2003/12/22 20:37:14  dave
-- addressed eccm.attribute primary key
--
-- Revision 1.2  2003/12/15 19:20:43  dave
-- more ECCM changes
--
-- Revision 1.1  2003/12/10 20:34:54  dave
-- updating and making final scripts
--

DROP INDEX ECCM.BR_IX2;
CREATE INDEX ECCM.BR_IX2 ON ECCM.BR (BRANDCODE_FC ASC, NLSID ASC);

DROP INDEX ECCM.FAM_IX2;
CREATE INDEX ECCM.FAM_IX2 ON ECCM.FAM (FAMNAMEASSOC_FC ASC,BRID ASC, NLSID ASC);

DROP INDEX ECCM.SE_IX2;
CREATE INDEX ECCM.SE_IX2 ON ECCM.SE (SENAMEASSOC_FC ASC, FAMID, NLSID ASC);

DROP INDEX ECCM.PR_IX2;
CREATE INDEX ECCM.PR_IX2 ON ECCM.PR (BRANDCODE_FC ASC, NLSID ASC);
DROP INDEX ECCM.PR_IX3;
CREATE INDEX ECCM.PR_IX3 ON ECCM.PR (FAMNAMEASSOC_FC ASC, NLSID ASC);
DROP INDEX ECCM.PR_IX4;
CREATE INDEX ECCM.PR_IX4 ON ECCM.PR (SENAMEASSOC_FC ASC, NLSID ASC);

drop index eccm.of_ix1;
create index eccm.of_ix1 on eccm.of(offeringtype,optgroupname,subgroup);

DROP INDEX ECCM.PRODATTRELATOR_IX1;
CREATE INDEX ECCM.PRODATTRELATOR_IX1 ON ECCM.PRODATTRELATOR (ENTITY1ID, ENTITY1TYPE, ENTITY2ID, ENTITY2TYPE);

drop index eccm.prodattrelator_ix2;
create index eccm.prodattrelator_ix2 on eccm.prodattrelator(entity1type);

DROP INDEX ECCM.GENERALAREA_IX1;
CREATE INDEX ECCM.GENERALAREA_IX1 ON ECCM.GENERALAREA(genareaname_fc ASC, NLSID ASC);


DROP INDEX ECCM.CSOL_IX1;
CREATE INDEX ECCM.CSOL_IX1 ON ECCM.CSOL(genareaname_fc ASC, OFID ASC, NLSID ASC);

DROP INDEX ECCM.CSOL_IX2;
CREATE INDEX ECCM.CSOL_IX2 ON ECCM.CSOL(CSOLSTATUS_FC, OFID ASC, NLSID ASC);

DROP INDEX ECCM.csol_ix3;
create index eccm.csol_ix3 on eccm.csol(act_wdrawdate_ct);

DROP INDEX ECCM.csol_ix4;
create index eccm.csol_ix4 on  eccm.csol (PNUMB_CT);

DROP INDEX ECCM.CCTO_IX1;
CREATE INDEX ECCM.CCTO_IX1 ON ECCM.CCTO(genareaname_fc ASC, CTOID ASC, NLSID ASC);

DROP INDEX ECCM.CCTO_IX2;
CREATE INDEX ECCM.CCTO_IX2 ON ECCM.CCTO(CCOSOLSTATUS_FC, CTOID ASC, NLSID ASC);

DROP INDEX ECCM.CVAR_IX1;
CREATE INDEX ECCM.CVAR_IX1 ON ECCM.CVAR(genareaname_fc ASC, VARID ASC, NLSID ASC);

DROP INDEX ECCM.CVAR_IX2;
CREATE INDEX ECCM.CVAR_IX2 ON ECCM.CVAR(STATUS_CVAR_FC ASC, VARID ASC, NLSID ASC);

DROP INDEX ECCM.CB_IX1;
CREATE INDEX ECCM.CB_IX1 ON ECCM.CB(genareaname_fc ASC, NLSID ASC);

DROP INDEX ECCM.CB_IX2;
CREATE INDEX ECCM.CB_IX2 ON ECCM.CB(CBSOLSTATUS_FC ASC, NLSID ASC);

DROP INDEX ECCM.IMG_IX2;
CREATE INDEX ECCM.IMG_IX2 ON ECCM.IMG(IMGSTATUS_FC ASC, ENTITYID ASC, NLSID ASC);

DROP INDEX ECCM.MM_IX2;
CREATE INDEX ECCM.MM_IX2 ON ECCM.MM(MMSTATUS_FC ASC, ENTITYID ASC, NLSID ASC);

DROP INDEX ECCM.FB_IX2;
CREATE INDEX ECCM.FB_IX2 ON ECCM.FB(FBSTATUS_FC ASC, ENTITYID ASC, NLSID ASC);

DROP INDEX ECCM.attribute_pk;
create unique index eccm.attribute_pk on eccm.attribute(attributecode,entitytype);

DROP INDEX ECCM.attribute_ix1;
create index eccm.attribute_ix1 on eccm.attribute(attributetoken, attributecode);

DROP INDEX ECCM.prodattribute_ix1;
create index eccm.prodattribute_ix1 on eccm.prodattribute(attributecode, entityid, entitytype);

DROP INDEX ECCM.cg_ix1;
create index eccm.cg_ix1 on eccm.cg(cgpriced, entityid);

DROP INDEX ECCM.PORT_IX1;
CREATE INDEX ECCM.PORT_IX1 ON ECCM.PORT(PORTCATEGORY_FC, ENTITYID, NLSID);
