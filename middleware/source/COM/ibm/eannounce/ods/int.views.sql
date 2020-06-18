-- $Log: int.views.sql,v $
-- Revision 1.4  2004/06/17 16:27:27  dave
-- adding Paveeen CR 4642 changes
--
-- Revision 1.3  2004/05/24 23:34:43  dave
-- more int cleanup for ECCM 2.0
--
-- Revision 1.2  2004/03/02 22:07:41  yang
-- updating and making final scripts
--

--------------------------------------------------------------------------------------
-- VIEWS
--------------------------------------------------------------------------------------

------------------------------------------
-- INT.ATTRGROUPS
------------------------------------------
drop view int.attrgroups;
------------------------------------------
create view int.attrgroups (
  grouptoken,
  groupname
) as 

select    grouptoken, 
          description 

from      eccm.attrgroup 

union 

select    grouptoken, 
          groupname 

from      int.eccmatgp
;

------------------------------------------
-- INT.PREVIEWATTR
------------------------------------------
drop view int.previewattr;
------------------------------------------
create view int.previewattr 
(
  groupname, 
  attributename, 
  attributevalue, 
  family, 
  countrycode, 
  entitytype, 
  entityid, 
  nlsid, 
  groupsequence, 
  attrsequence 
) as ( 

select    coalesce(agl.attributelabel, ag.description), 
          coalesce(atl.attributelabel, attr.description), 
          coalesce((rtrim(prdattr.attributevalue) concat ' ' 
          concat rtrim(prodattrunit.attributevalue)),  prdattr.attributevalue), 
          fs.catidentifier, 
          fs.countrycode, 
          prdroot.entitytype, 
          prdroot.entityid, 
          prdroot.nlsid, 
          fs.groupsequence, 
          fs.attrsequence 

from      eccm.prdroot prdroot 
  
join      eccm.prodattrelator pr on 
          prdroot.ofentitytype=pr.entity1type and 
          prdroot.ofentityid=pr.entity1id 
  
join      eccm.prodattribute prdattr on 
          pr.entity2type = prdattr.entitytype and 
          pr.entity2id = prdattr.entityid and 
          prdroot.nlsid=prdattr.nlsid 
  
join      eccm.attribute attr on 
          prdattr.attributecode = attr.attributecode 

join      eccm.attrwithunit primattr on 
          prdattr.attributecode = primattr.attrcode 

left join eccm.prodattribute prodattrunit on 
          prodattrunit.entityid = pr.entity2id and 
          prodattrunit.entitytype = pr.entity2type and 
          prodattrunit.attributecode = primattr.attrunitcode 
  
join      int.familygroupattrseq fs on 
          fs.attributetoken = attr.attributetoken 

join      eccm.attrgroup ag on 
          fs.grouptoken = ag.grouptoken 

join      eccm.psgctnls ctnls on 
          fs.countrycode = ctnls.countrycode 

left join int.attrgrouplabel agl on 
          ag.grouptoken = agl.grouptoken and 
          agl.nlsid = ctnls.nlsid 
  
left join int.attrlabel atl on 
          attr.attributetoken = atl.attributetoken and 
          atl.nlsid = ctnls.nlsid 

where     prdattr.attributevalue is not null and 
          prdattr.attributevalue <> '' and 
          fs.groupvisible = 1 and 
          fs.attrvisible = 1 

union 

select    coalesce(agl.attributelabel, ag.description), 
          coalesce(atl.attributelabel, attr.description), 
          coalesce((rtrim(prdattr.attributevalue) concat ' ' concat 
          rtrim(prodattrunit.attributevalue)),  prdattr.attributevalue), 
          fs.catidentifier, 
          fs.countrycode, 
          prdroot.entitytype, 
          prdroot.entityid, 
          prdroot.nlsid, 
          fs.groupsequence, 
          fs.attrsequence 

from      eccm.prdroot prdroot 

join      eccm.ofsbb ofsbb on 
          ofsbb.id1=prdroot.entityid 

join      eccm.prodattrelator pr on 
          'SBB'=pr.entity1type and 
          ofsbb.id2=pr.entity1id 
  
join      eccm.prodattribute prdattr on 
          pr.entity2type = prdattr.entitytype and 
          pr.entity2id = prdattr.entityid and 
          prdroot.nlsid=prdattr.nlsid 
  
join      eccm.attribute attr on 
          prdattr.attributecode = attr.attributecode 

join      eccm.attrwithunit primattr on 
          prdattr.attributecode = primattr.attrcode 

left join eccm.prodattribute prodattrunit on 
          prodattrunit.entityid = pr.entity2id and 
          prodattrunit.entitytype = pr.entity2type and
          prodattrunit.attributecode = primattr.attrunitcode 

join      int.familygroupattrseq fs on 
          fs.attributetoken = attr.attributetoken 

join      eccm.attrgroup ag on 
          fs.grouptoken = ag.grouptoken 

join      eccm.psgctnls ctnls on 
          fs.countrycode = ctnls.countrycode 

left join int.attrgrouplabel agl on 
          ag.grouptoken = agl.grouptoken and 
          agl.nlsid = ctnls.nlsid 

left join int.attrlabel atl on 
          attr.attributetoken = atl.attributetoken and 
          atl.nlsid = ctnls.nlsid 

where     prdattr.attributevalue is not null and 
          prdattr.attributevalue <> '' and 
          fs.groupvisible = 1 and 
          fs.attrvisible = 1 and 
          prdroot.entitytype ='OF' 
          
union 

select    coalesce(agl.attributelabel, ag.description), 
          coalesce(atl.attributelabel, attr.description), 
          coalesce((rtrim(prdattr.attributevalue) concat ' ' concat rtrim(prodattrunit.attributevalue)),  prdattr.attributevalue), 
          fs.catidentifier, 
          fs.countrycode, 
          prdroot.entitytype, 
          prdroot.entityid, 
          prdroot.nlsid, 
          fs.groupsequence, 
          fs.attrsequence 

from      eccm.prdroot prdroot 

join      eccm.csolsbb csolsbb on 
          csolsbb.id1=prdroot.entityid 

join      eccm.prodattrelator pr on 
          'SBB'=pr.entity1type and 
          csolsbb.id2=pr.entity1id 

join      eccm.prodattribute prdattr on
          pr.entity2type = prdattr.entitytype and 
          pr.entity2id = prdattr.entityid and 
          prdroot.nlsid=prdattr.nlsid 
  
join      eccm.attribute attr on 
          prdattr.attributecode = attr.attributecode 

join      eccm.attrwithunit primattr on 
          prdattr.attributecode = primattr.attrcode 

left join eccm.prodattribute prodattrunit on 
          prodattrunit.entityid = pr.entity2id and 
          prodattrunit.entitytype = pr.entity2type and 
          prodattrunit.attributecode = primattr.attrunitcode 

join      int.familygroupattrseq fs on 
          fs.attributetoken = attr.attributetoken 

join      eccm.attrgroup ag on 
          fs.grouptoken = ag.grouptoken 

join      eccm.psgctnls ctnls on 
          fs.countrycode = ctnls.countrycode 

left join int.attrgrouplabel agl on 
          ag.grouptoken = agl.grouptoken and 
          agl.nlsid = ctnls.nlsid 

left join int.attrlabel atl on 
          attr.attributetoken = atl.attributetoken and 
          atl.nlsid = ctnls.nlsid 

where     prdattr.attributevalue is not null and 
          prdattr.attributevalue <> '' and 
          fs.groupvisible = 1 and 
          fs.attrvisible = 1 and 
          prdroot.entitytype ='CSOL' 

union 

select    coalesce(agl.attributelabel, ag.description), 
          coalesce(atl.attributelabel, attr.attributename), 
          prdattr.value, 
          fs.catidentifier, 
          fs.countrycode, 
          prd.prdentitytype, 
          prd.prdentityid, 
          prd.prdnlsid, 
          fs.groupsequence, 
          fs.attrsequence 

from      int.avwwprd prd 
  
join      int.avprdat prdattr on 
          prd.prdsku = prdattr.prdsku 

join      int.eccmattr attr on 
          prdattr.attributetoken = attr.attributetoken 

join      int.familygroupattrseq fs on 
          fs.attributetoken = attr.attributetoken 

join      eccm.attrgroup ag on 
          fs.grouptoken = ag.grouptoken 

join      eccm.psgctnls ctnls on 
          fs.countrycode = ctnls.countrycode
          
left join int.attrgrouplabel agl on 
          ag.grouptoken = agl.grouptoken and 
          agl.nlsid = ctnls.nlsid 

left join int.attrlabel atl on 
          attr.attributetoken = atl.attributetoken and 
          atl.nlsid = ctnls.nlsid 

where     fs.groupvisible = 1 and 
          fs.attrvisible = 1 
union 

select    coalesce(agl.attributelabel, ag.groupname), 
          coalesce(atl.attributelabel, attr.attributename), 
          prdattr.value, 
          fs.catidentifier, 
          fs.countrycode, 
          prd.prdentitytype, 
          prd.prdentityid, 
          prd.prdnlsid, 
          fs.groupsequence, 
          fs.attrsequence 

from      int.avwwprd prd 

join      int.avprdat prdattr on 
          prd.prdsku = prdattr.prdsku 

join      int.eccmattr attr on 
          prdattr.attributetoken = attr.attributetoken 

join      int.familygroupattrseq fs on 
          fs.attributetoken = attr.attributetoken 

join      int.eccmatgp ag on 
          fs.grouptoken = ag.grouptoken 

join      eccm.psgctnls ctnls on 
          fs.countrycode = ctnls.countrycode 

left join int.attrgrouplabel agl on 
          ag.grouptoken = agl.grouptoken and 
          agl.nlsid = ctnls.nlsid
          
left join int.attrlabel atl on 
          attr.attributetoken = atl.attributetoken and 
          atl.nlsid = ctnls.nlsid 

where     fs.groupvisible = 1 and 
          fs.attrvisible = 1 

);


------------------------------------------
-- INT.avoutprd
-- Change from Praveen To deal w/ Translation and SBB
--
------------------------------------------
drop view int.avoutprd;
------------------------------------------
drop view int.avoutprd;
create view int.avoutprd  (  PRDSKU,  PRDENTITYTYPE,  PRDENTITYID,  PRDNLSID,  PARTNUMBER,  COUNTRYCODE, 
LANGUAGE,  PRODUCTCLASS,  AUDIENCE,  AUXDESC1,  AUXDESC2,  SHORTDESC,  ENG_SBBDESC, ANNOUNCEDATE,  WITHDRAWDATE,  
FOTANNOUNCEDATE,  FOTWITHDRAWDATE,  NAVBARFILENAME,  CONFIGURATORID,  FOTSTATUS,  WWPARTNUM,  INSTALLABLE,  
BASESYSTEM,  SERVICETYPE,  SPECIALBID,  SBBTYPE,  UNSPSC,  UNUOM,  PUB_FLAG,  REVIEWPUB_FLAG,  LASTUPDATED,  
REFRESHTIME,  VALID_FLAG,  SBBGADATE,  INSTALLABLE_OVERRIDE,  FAMILYNAME )  as (  
select  prd.prdsku,  prd.prdentitytype,  prd.prdentityid,  prd.prdnlsid,  prd.partnumber,  prd.countrycode,  
prd.language,  prd.productclass,  prd.audience,  prd.auxdesc1,  prd.auxdesc2,  prd.shortdesc, '', prd.announcedate,  
prd.withdrawdate,  prd.fotannouncedate,  prd.fotwithdrawdate,  prd.navbarfilename,  prd.configuratorid,  
prd.fotstatus,  prd.wwpartnum,  prd.installable,  prd.basesystem,  prd.servicetype,  prd.specialbid,  prd.sbbtype,  
prd.unspsc,  prd.unuom,  prd.pub_flag,  prd.reviewpub_flag,  prd.lastupdated,  prd.refreshtime,  prd.valid_flag ,  
prd.announcedate,  prd.installable_override,  
CASE prd.productclass  WHEN 'CTO'  THEN ''  WHEN 'CB'  THEN '' ELSE coalesce(proj.familyname,'')  END  
from    int.avwwprd prd  
join eccm.prdroot root on prd.prdentitytype = root.entitytype  and prd.prdentityid = root.entityid  
and prd.prdnlsid = root.nlsid  
left outer join eccm.projserbrandfam proj on root.projectid = proj.projectid  

union  select  rtrim(sbb.sbbpnumb) concat '_' concat rtrim(prd.countrycode) concat '_' concat 
rtrim(language.isolangcode2),  'SBB',  sbb.entityid,  sbb.nlsid,  sbb.sbbpnumb,  prd.countrycode,  
language.isolangcode2,  'SBB',  'ALL',  '',  '',  sbb.sbbpnumbdesc,  sbb1.sbbpnumbdesc, sbb.sbbannouncetgt,  
sbb.sbbwithdrawldate,  date(sbb.sbbannouncetgt) - 28 days,  sbb.sbbwithdrawldate,  '',  '',  sbb.sbbstatus,  
sbb.sbbpnumb,  0,  0,  '',  '',  sbb.sbbtype,  '',  '',  1,  1,  sbb.valfrom,  sbb.valfrom,  1,  sbb.sbbgenavaildate,  
1,  ''  
from    int.avwwprd prd  
join eccm.cctosbb cctosbb on prd.prdentityid = cctosbb.ID1  
join eccm.ccto ccto on ccto.entityid=cctosbb.ID1  
join eccm.sbb sbb on cctosbb.ID2 = sbb.entityid  
join eccm.sbb sbb1 on cctosbb.ID2 = sbb1.entityid and sbb1.nlsid = 1  
join eccm.psgnls language on sbb.nlsid = language.nlsid  
where (cctosbb.sbbpublishctdate is null or cctosbb.sbbpublishctdate <= current date) and  
(cctosbb.sbbunpublishctdate is null or cctosbb.sbbunpublishctdate >= current date) and  prd.prdentitytype = 'CCTO' 
and  prd.prdnlsid=sbb.nlsid and  prd.prdnlsid=ccto.nlsid  

union select  rtrim(sbb.sbbpnumb) concat '_' concat rtrim(prd.countrycode) concat '_' concat 
rtrim(language.isolangcode2),  'SBB',  sbb.entityid,  sbb.nlsid,  sbb.sbbpnumb,  prd.countrycode,  
language.isolangcode2,  'SBB',  'ALL',  '',  '',  sbb.sbbpnumbdesc,  sbb1.sbbpnumbdesc, sbb.sbbannouncetgt,  
sbb.sbbwithdrawldate,  date(sbb.sbbannouncetgt) - 28 days,  sbb.sbbwithdrawldate,  '',  '',  sbb.sbbstatus,  
sbb.sbbpnumb,  0,  0,  '',  '',  sbb.sbbtype,  '',  '',  1,  1,  sbb.valfrom,  sbb.valfrom,  1,  sbb.sbbgenavaildate,  
1,  ''  
from    int.avwwprd prd 
join eccm.cvarsbb cvarsbb on prd.prdentityid = cvarsbb.ID1  
join eccm.cvar cvar on cvar.entityid=cvarsbb.id1  
join eccm.sbb sbb on sbb.entityid=cvarsbb.ID2  
join eccm.sbb sbb1 on cvarsbb.ID2 = sbb1.entityid and sbb1.nlsid = 1  
join eccm.psgnls language on sbb.nlsid = language.nlsid  
where   prd.prdentitytype = 'CVAR' and  prd.prdnlsid=cvar.nlsid and  prd.prdnlsid=sbb.nlsid);

grant control on int.avoutprd to user eccmfeed, int;

------------------------------------------
-- INT.cmd_src
------------------------------------------
drop view int.cmd_src;
------------------------------------------
create view int.cmd_src 
(
    catidentifier, 
    countrycode, 
    grouptoken, 
    attributetoken, 
    groupname, 
    groupsequence, 
    groupvisible, 
    attrname, 
    attrsequence, 
    attrvisible 
) as ( 

select      cat.catidentifier, 
            cat.countrycode, 
            grp.grouptoken, 
            attr.attributetoken, 
            grp.description, 
            coalesce(grpattr.groupsequence,9999), 
            coalesce(grpattr.groupvisible,0), 
            attr.description, 
            coalesce(grpattr.attrsequence,9999), 
            coalesce(grpattr.attrvisible,0) 

from        int.avwwcat cat

join        eccm.attrgroup grp on 
            1=1 

join        eccm.attribute attr on 
            attr.entitytype = grp.entitytype 

left join   int.familygroupattrseq grpattr on 
            cat.catidentifier = grpattr.catidentifier and 
            cat.countrycode = grpattr.countrycode and 
            grp.grouptoken = grpattr.grouptoken and 
            attr.attributetoken = grpattr.attributetoken 

union 

select      cat.catidentifier, 
            cat.countrycode, 
            grp.grouptoken, 
            attr.attributetoken, 
            grp.description, 
            coalesce(grpattr.groupsequence,9999), 
            coalesce(grpattr.groupvisible,0), 
            attr.attributename, 
            coalesce(grpattr.attrsequence,9999), 
            coalesce(grpattr.attrvisible,0) 

from        int.avwwcat cat 

join        eccm.attrgroup grp on 
            1=1 

join        int.eccmattr attr on 
            attr.grouptoken = grp.grouptoken 

left join   int.familygroupattrseq grpattr on 
            cat.catidentifier = grpattr.catidentifier and 
            cat.countrycode = grpattr.countrycode and 
            grp.grouptoken = grpattr.grouptoken and 
            attr.attributetoken = grpattr.attributetoken 

union 

select      cat.catidentifier, 
            cat.countrycode, 
            grp.grouptoken, 
            attr.attributetoken, 
            grp.groupname, 
            coalesce(grpattr.groupsequence,9999), 
            coalesce(grpattr.groupvisible,0), 
            attr.attributename, 
            coalesce(grpattr.attrsequence,9999), 
            coalesce(grpattr.attrvisible,0) 

from        int.avwwcat cat 
  
join        int.eccmatgp grp on 
            1=1 

join        int.eccmattr attr on 
            attr.grouptoken = grp.grouptoken 

left join   int.familygroupattrseq grpattr on 
            cat.catidentifier = grpattr.catidentifier and 
            cat.countrycode = grpattr.countrycode and 
            grp.grouptoken = grpattr.grouptoken and 
            attr.attributetoken = grpattr.attributetoken 
            
);


------------------------------------------
-- INT.avprice_state_v
------------------------------------------
drop view int.avprice_state_v;
------------------------------------------
create view int.avprice_state_v 
(   
    partnumber, 
    ctovariantpartnumber, 
    countrycode, 
    distributionchannel, 
    pricevalidtodate, 
    pricetype, 
    materialstatus, 
    materialstatusdate, 
    priceamount, 
    currencycode, 
    pricevalidfromdate, 
    callforquote, 
    state, 
    precedence, 
    sourcesystem, 
    markedfordeletion, 
    lastupdated, 
    delta_ts 
) as 

select      price.partnumber, 
            price.ctovariantpartnumber, 
            price.countrycode, 
            price.distributionchannel, 
            price.pricevalidtodate, 
            price.pricetype, 
            price.materialstatus, 
            price.materialstatusdate, 
            price.priceamount, 
            price.currencycode, 
            price.pricevalidfromdate, 
            price.callforquote, 
            price.state, 
            price.precedence, 
            price.sourcesystem, 
            price.markedfordeletion, 
            price.lastupdated, 
            price.delta_ts 

from        int.avprice_state price 

where       price.ctovariantpartnumber='-' 

union 

select      price.partnumber, 
            price.ctovariantpartnumber, 
            price.countrycode, 
            price.distributionchannel, 
            price.pricevalidtodate, 
            price.pricetype, 
            price.materialstatus, 
            price.materialstatusdate, 
            price.priceamount, 
            price.currencycode, 
            price.pricevalidfromdate, 
            price.callforquote, 
            price.state, 
            price.precedence, 
            price.sourcesystem, 
            price.markedfordeletion, 
            price.lastupdated, 
            price.delta_ts 

from        int.avwwprd prd 

join        eccm.sbballdates a on 
            prd.prdentityid=a.entityid and 
            prd.prdentitytype=a.entitytype and 
            prd.prdentitytype='CCTO' and 
            prd.prdnlsid=a.nlsid

join        eccm.sbb sbb on 
            a.sbbid = sbb.entityid 

join        int.avprice_state price on 
            prd.partnumber=price.partnumber and 
            sbb.sbbpnumb=price.ctovariantpartnumber and 
            prd.countrycode=price.countrycode 

where       prd.productclass = 'CTO' and 
            price.ctovariantpartnumber<>'-' and 
            ((a.wdate is null) or (a.wdate >= current date))
;

------------------------------------------
-- INT.search
------------------------------------------
drop view int.search;
-----------------------------------------
create view int.search 
(   
    partnumber, 
    countrycode, 
    audience, 
    partdescription, 
    series, 
    family, 
    status, 
    regioncode, 
    announcedate, 
    price, 
    pricefromdate, 
    pricetodate, 
    salesstatus, 
    imagename, 
    userid, 
    dcrpath, 
    lastupdated 
) as 

select      distinct tr.partnumber, 
            tr.countrycode, 
            multi.longdescription, 
            prd.shortdesc, 
            tr.series, 
            tr.family, 
            prd.fotstatus, 
            CASE cr.regioncode 
              WHEN 'US' Then 'Americas' 
              WHEN 'CANADA' Then 'Americas'
              WHEN 'ANZ' Then 'AP' 
              WHEN 'APGCG' Then 'AP' 
              WHEN 'APJ' Then 'AP' 
              WHEN 'APK' Then 'AP' 
              WHEN 'ASEAN' Then 'AP' 
              WHEN 'EMEA' Then 'EMEA' 
              WHEN 'LAD' Then 'Americas' 
            END, 
            date(prd.announcedate), 
            coalesce(price.priceamount,0.00), 
            price.pricevalidfromdate, 
            price.pricevalidtodate, 
            price.materialstatus, 
            img.colorimagename, 
            'eAnnounce', 
            tr.vpath, 
            tr.lastupdated 

from        int.eccmtracker tr 

join        int.avwwprd prd on 
            tr.partnumber = prd.partnumber and 
            tr.countrycode = prd.countrycode and 
            tr.language = prd.language 

left join   int.effectiveprice price on 
            tr.partnumber = price.parentpartnumber and 
            tr.countrycode = price.countrycode and 
            (price.childpartnumber is null or price.childpartnumber = ' ' or 
             price.childpartnumber='-') 

join        eccm.audience multi on 
            prd.prdentityid = multi.entityid and 
            prd.prdentitytype = multi.entityname and
            prd.prdnlsid=multi.nlsid

join        eccm.prdimage img on 
            prd.prdentitytype=img.parententitytype and 
            prd.prdentityid=img.parententityid and 
            prd.prdnlsid=img.parentnlsid 

left        join eccm.countryregion cr on 
            cr.countrycode=tr.countrycode

where       img.datefrom in (

              select    max(b.datefrom) 
        
              from      eccm.prdimage b
        
              where     b.parententitytype=img.parententitytype and 
                        b.parententityid=img.parententityid and
                        b.parentnlsid=img.parentnlsid and
                        b.datefrom <= date(current timestamp) and 
                        (b.dateto >= date(current timestamp) or b.dateto is null)
            )

union

select 
distinct    tr.partnumber, 
            tr.countrycode, 
            multi.longdescription, 
            prd.shortdesc, 
            tr.series, 
            tr.family, 
            prd.fotstatus, 
            CASE cr.regioncode 
              WHEN 'US' Then 'Americas' 
              WHEN 'CANADA' Then 'Americas'
              WHEN 'ANZ' Then 'AP' 
              WHEN 'APGCG' Then 'AP' 
              WHEN 'APJ' Then 'AP' 
              WHEN 'APK' Then 'AP' 
              WHEN 'ASEAN' Then 'AP' 
              WHEN 'EMEA' Then 'EMEA' 
              WHEN 'LAD' Then 'Americas' 
            END, 
            date(prd.announcedate), 
            coalesce(price.priceamount,0.00), 
            price.pricevalidfromdate, 
            price.pricevalidtodate, 
            price.materialstatus, 
            '', 
            'eAnnounce', 
            tr.vpath, 
            tr.lastupdated 

from        int.eccmtracker tr 

join        int.avwwprd prd on 
            tr.partnumber = prd.partnumber and 
            tr.countrycode = prd.countrycode and 
            tr.language = prd.language
            
left join   int.effectiveprice price on 
            tr.partnumber = price.parentpartnumber and 
            tr.countrycode = price.countrycode and 
            (price.childpartnumber is null or price.childpartnumber = ' ' or 
             price.childpartnumber='-') 

join        eccm.audience multi on 
            prd.prdentityid = multi.entityid and 
            prd.prdentitytype = multi.entityname and
            prd.prdnlsid=multi.nlsid

left join   eccm.countryregion cr on 
            cr.countrycode=tr.countrycode

where       prd.prdentitytype||char(prd.prdentityid)||char(prd.prdnlsid)not in (

              select    parententitytype||char(parententityid)||char(parentnlsid)
              from      eccm.prdimage
            )
;

grant select on int.search to int,eccmfeed;


-------------------------------------------
-- REFERENTIAL INTEGRITY
-------------------------------------------

ALTER TABLE INT.AVPRDEVP ADD CONSTRAINT "SQL031120094103150" FOREIGN KEY (CATIDENTIFIER, COUNTRYCODE, LANGUAGE, AUDIENCE)
REFERENCES INT.AVPRDEVPROOT (CATIDENTIFIER,COUNTRYCODE,LANGUAGE,AUDIENCE)
ON DELETE CASCADE ON UPDATE NO ACTION;


ALTER TABLE INT.AVFAMAT ADD CONSTRAINT "SQL031120094100260" FOREIGN KEY (CATIDENTIFIER, COUNTRYCODE, LANGUAGE,AUDIENCE)
REFERENCES INT.AVFAMATROOT (CATIDENTIFIER,COUNTRYCODE,LANGUAGE,AUDIENCE)
ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE INT.AVPRDCAT ADD CONSTRAINT "SQL031006015950300" FOREIGN KEY (PRDSKU)
REFERENCES INT.AVWWPRD (PRDSKU) ON DELETE CASCADE ON UPDATE NO ACTION;


ALTER TABLE INT.AVPRDINVALIDAT  ADD CONSTRAINT "SQL031120094101060" FOREIGN KEY (PRDSKU)
REFERENCES INT.AVWWPRD (PRDSKU) ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE INT.AVPRDAT  ADD   FOREIGN KEY (PRDSKU)
REFERENCES INT.AVWWPRD (PRDSKU) ON DELETE CASCADE ON UPDATE NO ACTION
DATA CAPTURE NONE  NOT VOLATILE;

ALTER TABLE INT.AVCATIM  ADD   FOREIGN KEY (CATSKU)
REFERENCES INT.AVWWCAT (CATSKU) ON DELETE CASCADE ON UPDATE NO ACTION
DATA CAPTURE NONE  NOT VOLATILE;
