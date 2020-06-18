-- $Log: int.sumtables.sql,v $
-- Revision 1.3  2004/05/24 22:34:18  dave
-- int cleanup in prep for ECCM 2.0
--
-- Revision 1.2  2004/03/02 22:07:34  yang
-- updating and making final scripts
--

----------------------------
-- DDL Statements for Summary Tables
----------------------------
------------------------------------------------
-- FORMATED FOR CVS
------------------------------------------------
-- DDL Statements for table INT.CHARVALUE
------------------------------------------------
create summary table int.charvalue 
(
 PARTNUMBER, 
 SBBPARTNUMBER, 
 CHARACTERISTIC, 
 CHARACTERISTIC_QTY, 
 LOWERLIMIT, 
 UPPERLIMIT
) as ( 

  select    cv.partnumber, 
            cv.characteristicvalue, 
            cv.characteristicname, 
            cvq.characteristicname,
            cvq.lowerlimit, 
            cvq.upperlimit 
            
  from      eccm.prod_char_value cv 
 
  left 
  join      eccm.prod_char_value cvq on 
            cv.partnumber = cvq.partnumber and 
            rtrim(cv.characteristicname) || '_' || rtrim(cv.characteristicvalue) = 
            rtrim(SUBSTR(cvq.characteristicname,1,(POSSTR(cvq.characteristicname,'_QTY')-1))) and 
            rtrim(cvq.characteristicname) like '%_QTY' where rtrim(cv.characteristicname) not like '%_QTY' 
) DATA INITIALLY DEFERRED REFRESH DEFERRED;

refresh table int.charvalue;

CREATE INDEX INT.CHARVALUEIDX1 ON INT.CHARVALUE (PARTNUMBER ASC, SBBPARTNUMBER ASC) PCTFREE 10 ;

------------------------------------------------
-- FORMATED FOR CVS
------------------------------------------------
-- DDL Statements for table INT.EFFECTIVEPRICE
------------------------------------------------
create summary table INT.EFFECTIVEPRICE 
(
  PARENTPARTNUMBER, 
  CHILDPARTNUMBER, 
  COUNTRYCODE, 
  DISTRIBUTIONCHANNEL, 
  PRICETYPE, 
  PRICEAMOUNT, 
  CURRENCYCODE, 
  MATERIALSTATUS, 
  MATERIALSTATUSDATE, 
  CALLFORQUOTE, 
  PRECEDENCE, 
  PRICEVALIDTODATE, 
  PRICEVALIDFROMDATE 
) as ( 

select    a.partnumber as parentpartnumber , 
          a.ctovariantpartnumber as childpartnumber , 
          a.countrycode as countrycode , 
          a.distributionchannel as distributionchannel , 
          a.pricetype as pricetype , 
          a.priceamount as priceamount , 
          a.currencycode as currencycode , 
          a.materialstatus as materialstatus , 
          a.materialstatusdate as materialstatusdate , 
          a.callforquote as callforquote , 
          a.precedence as precedence , 
          a.pricevalidtodate as pricevalidtodate , 
          a.pricevalidfromdate as pricevalidfromdate 

from      int.avprice_state a, 
          int.avprice_state b 

where     a.partnumber = b.partnumber and 
          a.countrycode=b.countrycode and 
          a.ctovariantpartnumber = b.ctovariantpartnumber and 
          a.distributionchannel = b.distributionchannel and 
          a.pricevalidtodate != b.pricevalidtodate and 
          (a.precedence > b.precedence) and 
          current date between (a.pricevalidfromdate - 1 day) and a.pricevalidtodate and 
          current date between (b.pricevalidfromdate - 1 day) and b.pricevalidtodate and 
          a.distributionchannel = '00' and 
          a.callforquote in ('0','1') and 
          b.callforquote in ('0','1') 
union 

select    a.partnumber as parentpartnumber , 
          a.ctovariantpartnumber as childpartnumber , 
          a.countrycode as countrycode , 
          a.distributionchannel as distributionchannel , 
          a.pricetype as pricetype , 
          a.priceamount as priceamount , 
          a.currencycode as currencycode , 
          a.materialstatus as materialstatus , 
          a.materialstatusdate as materialstatusdate , 
          a.callforquote as callforquote , 
          a.precedence as precedence , 
          a.pricevalidtodate as pricevalidtodate , 
          a.pricevalidfromdate as pricevalidfromdate 

from      int.avprice_state a 

where     current date between (a.pricevalidfromdate - 1 day) and a.pricevalidtodate and 
          a.distributionchannel = '00' and 
          a.callforquote in ('0','1') and 
          partnumber||ctovariantpartnumber||countrycode not in 
          (
          select    a.partnumber||a.ctovariantpartnumber||a.countrycode 
          
          from      int.avprice_state a, 
                    int.avprice_state b 
          
          where     a.partnumber = b.partnumber and 
                    a.countrycode=b.countrycode and 
                    a.ctovariantpartnumber = b.ctovariantpartnumber and 
                    a.distributionchannel = b.distributionchannel and 
                    a.pricevalidtodate != b.pricevalidtodate and 
                    (a.precedence > b.precedence) and 
                    current date between (a.pricevalidfromdate - 1 day) and a.pricevalidtodate and 
                    current date between (b.pricevalidfromdate - 1 day) and b.pricevalidtodate and 
                    a.distributionchannel = '00' and 
                    a.callforquote in ('0','1') and 
                    b.callforquote in ('0','1') 
          ) 
) DATA INITIALLY DEFERRED REFRESH DEFERRED;

refresh table int.effectiveprice;

CREATE INDEX ECCM.I_EFP_DISTCHAN ON INT.EFFECTIVEPRICE (DISTRIBUTIONCHANNEL ASC);
CREATE INDEX INST1   .WIZ1912 ON INT.EFFECTIVEPRICE (PARENTPARTNUMBER DESC, COUNTRYCODE DESC);
CREATE INDEX INT.EFPINDEX001 ON INT.EFFECTIVEPRICE (PARENTPARTNUMBER ASC,CHILDPARTNUMBER ASC,COUNTRYCODE ASC) PCTFREE 10 ;



