package com.ibm.transform.oim.eacm.util;

public class SqlConstants {
	
	public static final String SQL_PRE_01   = "select a.PARTNUMBER,a.CTOVARIANTPARTNUMBER,a.DISTRIBUTIONCHANNEL,a.PRICEVALIDFROMDATE,a.PRICEVALIDTODATE,a.PRICEAMOUNT,a.CURRENCYCODE,b.MATERIALSTATUS,a.STATE,CASE a.state WHEN 'zero' then CASE a.callforquote WHEN '0' THEN 'Yes' ELSE 'No' END ELSE 'N/A' END as VALID_ZERO,a.LASTUPDATED from EACM.FINALPRICE a left join EACM.PROD_SALESSTATUS b on a.PARTNUMBER=b.PARTNUMBER and a.COUNTRYCODE=b.COUNTRYCODE where a.PARTNUMBER=? and a.COUNTRYCODE=?";
    public static final String SQL_CCE_01   = "select PARTNUMBER,COUNTRY,LANGUAGE,AUDIENCE,MATERIALSTATUS,ADDTOCARTCUSTOMIZE,BUYABLE,PAGENAME,QUANTITY,VALFROM from eacm.FINAL_PRODCTRY where PARTNUMBER=? and COUNTRY=? and LANGUAGE=?";
	public static final String SQL_ATT_01 	= "select PARTNUMBER,COUNTRYCODE,LANGUAGE,ATTRIBTOKEN,ATTRIBVAL from eacm.PRW_GET_ATTRVALUES where PARTNUMBER=? and COUNTRYCODE=? and LANGUAGE=?";
    public static final String SQL_EVP_01 	= "select Economy,Value,Performance from EACM.PRW_GET_EVP where COUNTRYCODE=? and CATIDENTIFIER=? and AUDIENCE=?";
    public static final String SQL_CTG_01   = "select CATIDENTIFIER,CATNAME,CATNAMEDESC,FEATURE from EACM.PRW_GET_CATEGORY where countrycode=? and CATIDENTIFIER like ?";
    public static final String SQL_MDA_01   = "select FAMILY,ATTRIBGROUPNAME,GRPSEQ as GROUPSEQ,ATTRIBNAME,ATTRSEQ,COUNTRYCODE from EACM.PRW_MODELDETAIL where FAMILY=? and COUNTRYCODE=? order by ATTRIBGROUPNAME";
    public static final String SQL_MCR_01   = "select vam.COUNTRYCODE,vam.LANGUAGE,vam.CATIDENTIFIER,vam.AUDIENCE,vam.ATTRIBTOKEN,vam.ATTRIBNAME, vam.COLSEQUENCE,vam.ROWSEQUENCE,vam.VIEWBY,catprd.partnumber,price.priceamount,attrval.attribval from EACM.PRW_GET_VAM vam left join EACM.FINAL_CATPRODREL catprd on vam.countrycode=catprd.country and vam.language=catprd.language and vam.audience=catprd.audience and vam.catidentifier=catprd.identifier left join EACM.FINALPRICE price on vam.countrycode=price.countrycode and catprd.partnumber=price.partnumber left join EACM.PRW_GET_ATTRVALUES attrval on vam.countrycode=attrval.countrycode and vam.language=attrval.language and catprd.partnumber=attrval.partnumber and vam.attribtoken=attrval.attribtoken where catprd.partnumber=? and vam.countrycode=? and vam.audience=? and vam.catidentifier=? order by 3,7,8 with ur";
    public static final String SQL_MCR_02   = "select a.IDENTIFIER,a.AUDIENCE,a.PARTNUMBER,b.PRICEAMOUNT,c.ATTRIBTOKEN,c.ATTRIBNAME,d.ATTRIBVAL from EACM.FINAL_CATPRODREL a join EACM.FINALPRICE b on a.COUNTRY=b.COUNTRYCODE and a.PARTNUMBER=b.PARTNUMBER join EACM.PRW_GET_VAM c on a.COUNTRY=c.COUNTRYCODE and a.AUDIENCE=c.AUDIENCE and a.IDENTIFIER=c.CATIDENTIFIER join EACM.PRW_GET_ATTRVALUES d on a.COUNTRY=d.COUNTRYCODE and c.ATTRIBTOKEN=d.ATTRIBTOKEN and a.PARTNUMBER=d.PARTNUMBER where a.COUNTRY=? and a.IDENTIFIER=? and a.AUDIENCE=? with ur";
    public static final String SQL_ANN_01   = new StringBuffer()
    .append(" SELECT DISTINCT")	
    .append(" PR.COUNTRYCODE COUNTRYCODE,")
    .append(" RTRIM(PR.PARTNUMBER) PARTNUMBER,")
    .append(" UPPER(AUD.AUDIENCE) AUDIENCE,")
    .append(" case")
    .append(" when PR.LOCENTITYTYPE='CSOL' then")
    .append(" case")
    .append(" when WWPR.CATEGORY='SYSTEM' then 'MTM' ")
    .append(" when WWPR.CATEGORY='SERVICE' then 'SERVICE'")
    .append(" else 'SO'")
    .append(" end ")
    .append(" when PR.LOCENTITYTYPE='CCTO' then 'CTO'")
    .append(" when PR.LOCENTITYTYPE='CVAR' then 'V'")
    .append(" else PR.LOCENTITYTYPE")
    .append(" end PRODUCTCLASS,")
    .append(" PR.ANNDATE ANNOUNCEDATE,")
    .append(" COALESCE(PR.WITHDRAWLDATE,'2999-12-31') WITHDRAWDATE,")
    .append(" CASE WHEN AUD.AUDIENCE='Shop' and PR.COUNTRYCODE='US' THEN")
    .append(" CASE WHEN PR.LOCENTITYTYPE='CCTO' THEN 2")
    .append(" WHEN ((CAT.CATADDTOCART='Yes' AND CAT.CATCUSTIMIZE='Yes')")
    .append(" OR (CAT.CATADDTOCART='Yes' AND CAT.CATCUSTIMIZE IS null)")
    .append(" OR (CAT.CATADDTOCART IS null AND CAT.CATCUSTIMIZE='Yes')) THEN 3 ")
    .append(" WHEN (CAT.CATADDTOCART='Yes' OR CAT.CATADDTOCART IS null) AND CAT.CATCUSTIMIZE='No' THEN 1")
    .append(" WHEN (CAT.CATADDTOCART='No' AND (CAT.CATCUSTIMIZE='Yes' OR CAT.CATCUSTIMIZE IS null)) THEN 2")
    .append(" WHEN CAT.CATADDTOCART='No' AND CAT.CATCUSTIMIZE='No' THEN 0")
    .append(" ELSE CASE WHEN PR.LOCENTITYTYPE='CVAR' OR (PR.LOCENTITYTYPE='CSOL' AND WWPR.CATEGORY='SYSTEM') THEN 3")
    .append(" ELSE 1 END")
    .append(" END")
    .append(" ELSE")
    .append(" PRDCTRY.ADDTOCARTCUSTOMIZE")
    .append(" END ADDTOCARTCUSTOMIZE,")
    .append(" CASE WHEN AUD.AUDIENCE='Shop' and PR.COUNTRYCODE='US' THEN")
    .append(" CASE WHEN PR.LOCENTITYTYPE IN ('CCTO','SBB') OR DAC.PCD_AVAIL_QUANTITY IS null THEN 0")
    .append(" ELSE CASE WHEN (DAC.PCD_AVAIL_QUANTITY >=-2 AND DAC.PCD_AVAIL_QUANTITY != 0")
    .append(" AND (CAT.CATBUYABLE IS null OR CAT.CATBUYABLE='Yes')) THEN 1")
    .append(" WHEN (DAC.PCD_AVAIL_QUANTITY BETWEEN -6 AND -3")
    .append(" AND (CAT.CATBUYABLE IS null OR CAT.CATBUYABLE='No')) THEN 0")
    .append(" ELSE CASE WHEN CAT.CATBUYABLE='Yes' THEN 1 ")
    .append(" WHEN (CAT.CATBUYABLE IS null OR CAT.CATBUYABLE='No') THEN 0 END")
    .append(" END")
    .append(" END")
    .append(" ELSE")
    .append(" PRDCTRY.BUYABLE")
    .append(" END BUYABLE,")
    .append(" CASE WHEN AUD.AUDIENCE='Shop' and PR.COUNTRYCODE='US' THEN")
    .append(" CASE WHEN CAT.CATHIDE='Yes' OR DAC.PCD_AVAIL_QUANTITY IS null THEN ''")
    .append(" ELSE CASE WHEN ((DAC.PCD_AVAIL_QUANTITY >= -2) ")
    .append(" OR ((DAC.PCD_AVAIL_QUANTITY BETWEEN -6 AND -3) AND CAT.CATFOCUS='Yes')) THEN")
    .append(" CASE WHEN PR.LOCENTITYTYPE='CB' THEN 'package_detail.jsp'")
    .append(" WHEN PR.LOCENTITYTYPE IN ('CSOL','CVAR') THEN 'product_detail.jsp'")
    .append(" WHEN PR.LOCENTITYTYPE='CCTO' THEN 'featured_offer.jsp'")
    .append(" ELSE '' END")
    .append(" END")
    .append(" END")
    .append(" ELSE PRDCTRY.PAGENAME")
    .append(" END PAGENAME,")
    .append(" CASE WHEN AUD.AUDIENCE='Shop' and PR.COUNTRYCODE='US' THEN")
    .append(" CASE WHEN DAC.PCD_AVAIL_QUANTITY < -6 THEN '0' ")
    .append(" WHEN DAC.PCD_AVAIL_QUANTITY >= -6 THEN char(DAC.PCD_AVAIL_QUANTITY)")
    .append(" ELSE 'N/A'")
    .append(" END")
    .append(" ELSE 'N/A'")
    .append(" END QUANTITY,")
    .append(" CAT.CATFOCUS FOCUS_FLAG,")
    .append(" CAT.CATHIDE HIDE_FLAG,")
    .append(" CAT.CATBUYABLE BUYABLE_FLAG,")
    .append(" CAT.CATADDTOCART ADDTOCART_FLAG,")
    .append(" CAT.CATCUSTIMIZE CUSTOMIZE_FLAG,")
    .append(" REPLACE(PR.SHORTDESC,',','-') SHORTDESC,")
    .append(" PR.WWPARTNUMBER WWPARTNUM,")
    .append(" CASE WHEN CAT.INSTALLABLE='Yes' THEN '1'")
    .append(" WHEN CAT.INSTALLABLE='No' THEN '0'")
    .append(" ELSE CASE WHEN PR.INSTALLABLE='Yes' OR PR.INSTALLABLE='Installable Option' THEN '1'")
    .append(" ELSE '0' END")
    .append(" END INSTALLABLE,")
    .append(" CAT.INSTALLABLE INSTALLABLE_OVERRIDE,")
    .append(" CAT.CATPUBLISH PUB_FLAG,")
    .append(" CAT.FOTPUBLISH REVIEWPUB_FLAG")
    .append(" FROM bigcat.PRODUCT PR")
    .append(" JOIN bigcat.WWPRODUCT WWPR on PR.WWENTITYTYPE=WWPR.WWENTITYTYPE and PR.WWENTITYID=WWPR.WWENTITYID")
    .append(" and WWPR.NLSID=1")
    .append(" JOIN BIGCAT.AUDIENCE AUD on PR.LOCENTITYTYPE=AUD.LOCENTITYTYPE and PR.LOCENTITYID=AUD.LOCENTITYID")
    .append(" and PR.COUNTRYCODE=AUD.COUNTRYCODE AND PR.NLSID=AUD.NLSID")
    .append(" LEFT OUTER JOIN BIGCAT.PRDCTRY_FLAGS_brio CAT ON PR.LOCENTITYTYPE=CAT.ENTITYTYPE")
    .append(" and PR.LOCENTITYID=CAT.ENTITYID AND AUD.AUDIENCE=CAT.CATAUDIENCE_FC")
    .append(" LEFT OUTER JOIN EACM.FINAL_PRODCTRY prdctry on PR.PARTNUMBER=PRDCTRY.PARTNUMBER and")
    .append(" PR.COUNTRYCODE=prdctry.COUNTRY and PR.LANGUAGECODE=prdctry.LANGUAGE AND UPPER(AUD.AUDIENCE)=PRDCTRY.AUDIENCE")
    .append(" LEFT OUTER JOIN EACM.DACAVAIL DAC ON PR.PARTNUMBER=DAC.PARTNUMBER")
    .append(" AND PR.COUNTRYCODE=DAC.COUNTRYCODE AND PR.COUNTRYCODE='US'")    
    .append(" WHERE WWPR.DIVISION_FC='44'")
    //.append(" and PR.COUNTRYCODE=?")
    //.append(" and PR.PARTNUMBER in(?)")
    //.append(" order by 1,2,3")
    .toString();

}