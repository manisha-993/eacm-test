--
-- $Log: eccm.cbunspc.update.sql,v $
-- Revision 1.2  2004/05/07 16:33:46  dave
-- New maintenance scripts
--
--

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
select    cast('CB' as varchar(4)),
          cbid,
          cbnlsid,
          of.unspsc
from      tsys  tsys
join      eccm.of of on
          of.entityid =  tsys.ofid and
          of.nlsid = tsys.cbnlsid
union all
-- CBs w/ No MTM but Variant
select    cast('CB' as varchar(4)),
          cbid,
          cbnlsid,
          var.unspsc
from      tvar  tvar
join      eccm.var var on
          var.entityid =  tvar.varid and
          var.nlsid = tvar.cbnlsid

union all

-- OPTIONS ONLY Section
select    cast('CB' as varchar(4)),
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


--
--  Views that need to be recreated
--

GRANT SELECT ON ECCM.CBUNSPSC TO INT;
GRANT CONTROL ON ECCM.CBUNSPSC TO ECCMFEED;
GRANT SELECT ON ECCM.CBUNSPSC TO ECCMBR;
