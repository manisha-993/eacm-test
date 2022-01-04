package COM.ibm.eannounce.abr.sg.rfc;



import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "SVCMOD_UPDATE", namespace="http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/SVCMOD_UPDATE")
@XmlRootElement(name = "SVCMOD_UPDATE")
public class SVCMOD {

	@XmlElement(name = "PDHDOMAIN")
	private String PDHDOMAIN;
	@XmlElement(name = "DTSOFMSG")
	private String DTSOFMSG;
	@XmlElement(name = "ACTIVITY")
	private String ACTIVITY;
	@XmlElement(name = "MODELENTITYTYPE")
	private String MODELENTITYTYPE;
	@XmlElement(name = "MODELENTITYID")
	private String MODELENTITYID;
	@XmlElement(name = "MACHTYPE")
	private String MACHTYPE;
	@XmlElement(name = "MODEL")
	private String MODEL;
	@XmlElement(name = "STATUS")
	private String STATUS;
	@XmlElement(name = "CATEGORY")
	private String CATEGORY;
	@XmlElement(name = "SUBCATEGORY")
	private String SUBCATEGORY;
	@XmlElement(name = "GROUP")
	private String GROUP;
	@XmlElement(name = "SUBGROUP")
	private String SUBGROUP;
	@XmlElement(name = "PROJECT")
	private String PROJECT;
	@XmlElement(name = "DIVISION")
	private String DIVISION;
	@XmlElement(name = "PRFTCTR")
	private String PRFTCTR;
	@XmlElement(name = "PRODHIERCD")
	private String PRODHIERCD;
	@XmlElement(name = "ACCTASGNGRP")
	private String ACCTASGNGRP;
	@XmlElement(name = "NEC")
	private String NEC;
	@XmlElement(name = "TOS")
	private String TOS;
	@XmlElement(name = "SVCFFTYPE")
	private String SVCFFTYPE;
	@XmlElement(name = "OFERCONFIGTYPE")
	private String OFERCONFIGTYPE;
	@XmlElement(name = "ENDCUSTIDREQ")
	private String ENDCUSTIDREQ;
	@XmlElement(name = "FIXTERMPRIOD")
	private String FIXTERMPRIOD;
	@XmlElement(name = "PRORATEDOTCALLOW")
	private String PRORATEDOTCALLOW;
	@XmlElement(name = "SNGLLNEITEM")
	private String SNGLLNEITEM;
	@XmlElement(name = "SVCCHRGOPT")
	private String SVCCHRGOPT;
	@XmlElement(name = "TYPEOFWRK")
	private String TYPEOFWRK;
	@XmlElement(name = "UOMSI")
	private String UOMSI;
	@XmlElement(name = "UPGRADEYN")
	private String UPGRADEYN;
	@XmlElement(name = "WWOCCODE")
	private String WWOCCODE;
	@XmlElement(name = "UNSPSC")
	private String UNSPSC;
	@XmlElement(name = "UNUOM")
	private String UNUOM;
	@XmlElement(name = "PCTOFCMPLTINDC")
	private String PCTOFCMPLTINDC;
	@XmlElement(name = "SOPRELEVANT")
	private String SOPRELEVANT;
	@XmlElement(name = "SOPTASKTYPE")
	private String SOPTASKTYPE;
	@XmlElement(name = "ALTID")
	private String ALTID; 
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "LANGUAGEELEMENT")
	private List<LANGUAGE> LANGUAGELIST;
	@XmlElementWrapper(name = "AVAILABILITYLIST")
	@XmlElement(name = "AVAILABILITYELEMENT")
	private List<AVAILABILITY> AVAILABILITYLIST;
	@XmlElementWrapper(name = "TAXCATEGORYLIST")
	@XmlElement(name = "TAXCATEGORYELEMENT")
	private List<TAXCATEGORY> TAXCATEGORYLIST;
	@XmlElementWrapper(name = "TAXCODELIST")
	@XmlElement(name = "TAXCODEELEMENT")
	private List<TAXCODE> TAXCODELIST;
	@XmlElementWrapper(name = "CATATTRIBUTELIST")
	@XmlElement(name = "CATATTRIBUTEELEMENT")
	private List<CATATTRIBUTE> CATATTRIBUTELIST;
	@XmlElementWrapper(name = "UNBUNDCOMPLIST")
	@XmlElement(name = "UNBUNDCOMPELEMENT")
	private List<UNBUNDCOMP> UNBUNDCOMPLIST;
	@XmlElementWrapper(name = "CHRGCOMPLIST")
	@XmlElement(name = "CHRGCOMPELEMENT")
	private List<CHRGCOMP> CHRGCOMPLIST;
	@XmlElementWrapper(name = "SVCEXECTMELIST")
	@XmlElement(name = "SVCEXECTMEELEMENT")
	private List<SVCEXECTME> SVCEXECTMELIST;
	@XmlElementWrapper(name = "SVCSEOREFLIST")
	@XmlElement(name = "SVCSEOREFELEMENT")
	private List<SVCSEOREF> SVCSEOREFLIST;
	@XmlElementWrapper(name = "SVCMODREFLIST")
	@XmlElement(name = "SVCMODREFELEMENT")
	private List<SVCMODRE> SVCMODREFLIST;
	@XmlElementWrapper(name = "SVCSEOLIST")
	@XmlElement(name = "SVCSEOELEMENT")
	private List<SVCSEO> SVCSEOLIST;
	@XmlElementWrapper(name = "PRODSTRUCTLIST")
	@XmlElement(name = "PRODSTRUCTELEMENT")
	private List<PRODSTRUCT> PRODSTRUCTLIST;

	public String getPDHDOMAIN() {
		return PDHDOMAIN;
	}
	public String getMODELENTITYTYPE() {
		return MODELENTITYTYPE;
	}
	public String getMODELENTITYID() {
		return MODELENTITYID;
	}
	public String getMACHTYPE() {
		return MACHTYPE;
	}
	public String getMODEL() {
		return MODEL;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public String getSUBCATEGORY() {
		return SUBCATEGORY;
	}
	public String getSUBGROUP() {
		return SUBGROUP;
	}
	public String getPROJECT() {
		return PROJECT;
	}
	public String getPRFTCTR() {
		return PRFTCTR;
	}
	public String getPRODHIERCD() {
		return PRODHIERCD;
	}
	public String getNEC() {
		return NEC;
	}
	public String getTOS() {
		return TOS;
	}
	public String getSVCFFTYPE() {
		return SVCFFTYPE;
	}
	public String getOFERCONFIGTYPE() {
		return OFERCONFIGTYPE;
	}
	public String getPRORATEDOTCALLOW() {
		return PRORATEDOTCALLOW;
	}
	public String getSNGLLNEITEM() {
		return SNGLLNEITEM;
	}
	public String getSVCCHRGOPT() {
		return SVCCHRGOPT;
	}
	public String getTYPEOFWRK() {
		return TYPEOFWRK;
	}
	public String getUOMSI() {
		return UOMSI;
	}
	public String getUPGRADEYN() {
		return UPGRADEYN;
	}
	public String getWWOCCODE() {
		return WWOCCODE;
	}
	public String getUNSPSC() {
		return UNSPSC;
	}
	public String getUNUOM() {
		return UNUOM;
	}
	public String getPCTOFCMPLTINDC() {
		return PCTOFCMPLTINDC;
	}
	public String getSOPRELEVANT() {
		return SOPRELEVANT;
	}
	public String getSOPTASKTYPE() {
		return SOPTASKTYPE;
	}
	public List<LANGUAGE> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public List<TAXCATEGORY> getTAXCATEGORYLIST() {
		return TAXCATEGORYLIST;
	}
	public List<TAXCODE> getTAXCODELIST() {
		return TAXCODELIST;
	}
	public List<UNBUNDCOMP> getUNBUNDCOMPLIST() {
		return UNBUNDCOMPLIST;
	}
	public List<SVCEXECTME> getSVCEXECTMELIST() {
		return SVCEXECTMELIST;
	}
	public List<SVCSEOREF> getSVCSEOREFLIST() {
		return SVCSEOREFLIST;
	}
	public List<SVCMODRE> getSVCMODREFLIST() {
		return SVCMODREFLIST;
	}
	public List<SVCSEO> getSVCSEOLIST() {
		return SVCSEOLIST;
	}
	public List<PRODSTRUCT> getPRODSTRUCTLIST() {
		return PRODSTRUCTLIST;
	}
	public String getDTSOFMSG() {
		return DTSOFMSG;
	}
	public String getACTIVITY() {
		return ACTIVITY;
	}
	public String getCATEGORY() {
		return CATEGORY;
	}
	public String getGROUP() {
		return GROUP;
	}
	public String getDIVISION() {
		return DIVISION;
	}
	public String getACCTASGNGRP() {
		return ACCTASGNGRP;
	}
	public String getENDCUSTIDREQ() {
		return ENDCUSTIDREQ;
	}
	public String getFIXTERMPRIOD() {
		return FIXTERMPRIOD;
	}
	public String getALTID() {
		return ALTID;
	}
	public List<AVAILABILITY> getAVAILABILITYLIST() {
		return AVAILABILITYLIST;
	}
	public List<CATATTRIBUTE> getCATATTRIBUTELIST() {
		return CATATTRIBUTELIST;
	}
	public List<CHRGCOMP> getCHRGCOMPLIST() {
		return CHRGCOMPLIST;
	}
	public boolean hasProds() {
		if(this.getPRODSTRUCTLIST()!=null&&this.getPRODSTRUCTLIST().size()>0)
			return true;
		return false;
	}
	
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class PRODSTRUCT {
	@XmlElement(name = "ACTIVITY")
	private String ACTIVITY;
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "STATUS")
	private String STATUS;
	@XmlElement(name = "MKTGNAME")
	private String MKTGNAME;
	@XmlElement(name = "MNATORYOPT")
	private String MNATORYOPT;
	@XmlElement(name = "OWNERID")
	private String OWNERID;
	@XmlElement(name = "WITHDRAWDATE")
	private String WITHDRAWDATE;
	@XmlElement(name = "WTHDRWEFFCTVDATE")
	private String WTHDRWEFFCTVDATE;
	@XmlElement(name = "FEATURECODE")
	private String FEATURECODE;
	@XmlElement(name = "FCMKTGSHRTDESC")
	private String FCMKTGSHRTDESC;
	@XmlElement(name = "SVCCAT")
	private String SVCCAT;
	@XmlElement(name = "SVCFCSUBCAT")
	private String SVCFCSUBCAT;
	@XmlElement(name = "EFFECTIVEDATE")
	private String EFFECTIVEDATE;
	@XmlElement(name = "ENDDATE")
	private String ENDDATE;
	
	public String getACTIVITY() {
		return ACTIVITY;
	}
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public String getMKTGNAME() {
		return MKTGNAME;
	}
	public String getMNATORYOPT() {
		return MNATORYOPT;
	}
	public String getOWNERID() {
		return OWNERID;
	}
	public String getWITHDRAWDATE() {
		return WITHDRAWDATE;
	}
	public String getWTHDRWEFFCTVDATE() {
		return WTHDRWEFFCTVDATE;
	}
	public String getFEATURECODE() {
		return FEATURECODE;
	}
	public String getFCMKTGSHRTDESC() {
		return FCMKTGSHRTDESC;
	}
	public String getSVCCAT() {
		return SVCCAT;
	}
	public String getSVCFCSUBCAT() {
		return SVCFCSUBCAT;
	}
	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class SVCSEO {
	@XmlElement(name = "ACTIVITY")
	private String ACTIVITY;
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "STATUS")
	private String STATUS;
	@XmlElement(name = "SEOID")
	private String SEOID;
	@XmlElement(name = "BHPRDHIERCD")
	private String BHPRDHIERCD;
	@XmlElement(name = "PRFTCTR")
	private String PRFTCTR;
	@XmlElement(name = "ACCTASGNGRP")
	private String ACCTASGNGRP;
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "LANGUAGEELEMENT")
	private List<LANGUAGE> LANGUAGELIST;
	@XmlElementWrapper(name = "AVAILABILITYLIST")
	@XmlElement(name = "AVAILABILITYELEMENT")
	private List<SEOAVAILABILITY> AVAILABILITYLIST;
	@XmlElementWrapper(name = "PRCPTLIST")
	@XmlElement(name = "PRCPTELEMENT")
	private List<PRCPT> PRCPTLIST;
	
	public String getACTIVITY() {
		return ACTIVITY;
	}
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public String getSEOID() {
		return SEOID;
	}
	public String getBHPRDHIERCD() {
		return BHPRDHIERCD;
	}
	public String getPRFTCTR() {
		return PRFTCTR;
	}
	public String getACCTASGNGRP() {
		return ACCTASGNGRP;
	}
	public List<LANGUAGE> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public List<SEOAVAILABILITY> getAVAILABILITYLIST() {
		return AVAILABILITYLIST;
	}
	public List<PRCPT> getPRCPTLIST() {
		return PRCPTLIST;
	}
	
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class PRCPT {
	@XmlElement(name = "ACTIVITY")
	private String ACTIVITY;
	@XmlElement(name = "EFFECTIVEDATE")
	private String EFFECTIVEDATE;
	@XmlElement(name = "ENDDATE")
	private String ENDDATE;
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "PRCPTID")
	private String PRCPTID;
	
	public String getACTIVITY() {
		return ACTIVITY;
	}
	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public String getPRCPTID() {
		return PRCPTID;
	}
	
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class SEOAVAILABILITY {
	@XmlElement(name = "AVAILABILITYACTION")
	private String AVAILABILITYACTION;
	@XmlElement(name = "STATUS")
	private String STATUS;
	@XmlElement(name = "COUNTRY_FC")
	private String COUNTRY_FC;
	@XmlElement(name = "ANNDATE")
	private String ANNDATE;
	@XmlElement(name = "ANNNUMBER")
	private String ANNNUMBER;
	@XmlElement(name = "FIRSTORDER")
	private String FIRSTORDER;
	@XmlElement(name = "PLANNEDAVAILABILITY")
	private String PLANNEDAVAILABILITY;
	@XmlElement(name = "PUBFROM")
	private String PUBFROM;
	@XmlElement(name = "PUBTO")
	private String PUBTO;
	@XmlElement(name = "WDANNDATE")
	private String WDANNDATE;
	@XmlElement(name = "LASTORDER")
	private String LASTORDER;
	@XmlElement(name = "ENDOFSERVICEDATE")
	private String ENDOFSERVICEDATE;
	@XmlElement(name = "EOSANNDATE")
	private String EOSANNDATE;
	@XmlElementWrapper(name = "SLEORGGRPLIST")
	@XmlElement(name = "SLEORGGRPELEMENT")
	private List<SLEORGGRP> SLEORGGRPLIST;
	@XmlElementWrapper(name = "SLEORGNPLNTCODELIST")
	@XmlElement(name = "SLEORGNPLNTCODEELEMENT")
	private List<SLEORGNPLNTCODE> SLEORGNPLNTCODELIST;
	
	public String getAVAILABILITYACTION() {
		return AVAILABILITYACTION;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public String getCOUNTRY_FC() {
		return COUNTRY_FC;
	}
	public String getANNDATE() {
		return ANNDATE;
	}
	public String getANNNUMBER() {
		return ANNNUMBER;
	}
	public String getFIRSTORDER() {
		return FIRSTORDER;
	}
	public String getPLANNEDAVAILABILITY() {
		return PLANNEDAVAILABILITY;
	}
	public String getPUBFROM() {
		return PUBFROM;
	}
	public String getPUBTO() {
		return PUBTO;
	}
	public String getWDANNDATE() {
		return WDANNDATE;
	}
	public String getLASTORDER() {
		return LASTORDER;
	}
	public String getENDOFSERVICEDATE() {
		return ENDOFSERVICEDATE;
	}
	public String getEOSANNDATE() {
		return EOSANNDATE;
	}
	public List<SLEORGGRP> getSLEORGGRPLIST() {
		return SLEORGGRPLIST;
	}
	public List<SLEORGNPLNTCODE> getSLEORGNPLNTCODELIST() {
		return SLEORGNPLNTCODELIST;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class LANGUAGE {
	@XmlElement(name = "NLSID")
	private String NLSID;
	@XmlElement(name = "INVNAME")
	private String INVNAME;
	@XmlElement(name = "MKTGNAME")
	private String MKTGNAME;
	public String getNLSID() {
		return NLSID;
	}
	public String getINVNAME() {
		return INVNAME;
	}
	public String getMKTGNAME() {
		return MKTGNAME;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class LANGUAGESHRTONLY {
	@XmlElement(name = "NLSID")
	private String NLSID;
	@XmlElement(name = "SHRTNAM")
	private String SHRTNAM;
	public String getNLSID() {
		return NLSID;
	}
	public String getSHRTNAM() {
		return SHRTNAM;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class SVCMODRE {
	@XmlElement(name = "SVCMODEENTITYTYPE")
	private String SVCMODEENTITYTYPE;
	@XmlElement(name = "SVCMODENTITYID")
	private String SVCMODENTITYID;
	@XmlElement(name = "MACHTYPEATR")
	private String MACHTYPEATR;
	@XmlElement(name = "MODELATR")
	private String MODELATR;
	public String getSVCMODEENTITYTYPE() {
		return SVCMODEENTITYTYPE;
	}
	public String getSVCMODENTITYID() {
		return SVCMODENTITYID;
	}
	public String getMACHTYPEATR() {
		return MACHTYPEATR;
	}
	public String getMODELATR() {
		return MODELATR;
	}
	
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class AVAILABILITY {
	@XmlElement(name = "AVAILABILITYACTION")
	private String AVAILABILITYACTION;
	@XmlElement(name = "STATUS")
	private String STATUS;
	@XmlElement(name = "COUNTRY_FC")
	private String COUNTRY_FC;
	@XmlElement(name = "ANNDATE")
	private String ANNDATE;
	@XmlElement(name = "ANNNUMBER")
	private String ANNNUMBER;
	@XmlElement(name = "FIRSTORDER")
	private String FIRSTORDER;
	@XmlElement(name = "PLANNEDAVAILABILITY")
	private String PLANNEDAVAILABILITY;
	@XmlElement(name = "PUBFROM")
	private String PUBFROM;
	@XmlElement(name = "PUBTO")
	private String PUBTO;
	@XmlElement(name = "WDANNDATE")
	private String WDANNDATE;
	@XmlElement(name = "ENDOFMARKETANNNUMBER")
	private String ENDOFMARKETANNNUMBER;
	@XmlElement(name = "LASTORDER")
	private String LASTORDER;
	@XmlElement(name = "EOSANNDATE")
	private String EOSANNDATE;
	@XmlElement(name = "ENDOFSERVICEDATE")
	private String ENDOFSERVICEDATE;
	@XmlElementWrapper(name = "SLEORGGRPLIST")
	@XmlElement(name = "SLEORGGRPELEMENT")
	private List<SLEORGGRP> SLEORGGRPLIST;
	@XmlElementWrapper(name = "SLEORGNPLNTCODELIST")
	@XmlElement(name = "SLEORGNPLNTCODEELEMENT")
	private List<SLEORGNPLNTCODE> SLEORGNPLNTCODELIST;
	
	public String getAVAILABILITYACTION() {
		return AVAILABILITYACTION;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public String getCOUNTRY_FC() {
		return COUNTRY_FC;
	}
	public String getANNDATE() {
		return ANNDATE;
	}
	public String getANNNUMBER() {
		return ANNNUMBER;
	}
	public String getFIRSTORDER() {
		return FIRSTORDER;
	}
	public String getPLANNEDAVAILABILITY() {
		return PLANNEDAVAILABILITY;
	}
	public String getPUBFROM() {
		return PUBFROM;
	}
	public String getPUBTO() {
		return PUBTO;
	}
	public String getWDANNDATE() {
		return WDANNDATE;
	}
	public String getENDOFMARKETANNNUMBER() {
		return ENDOFMARKETANNNUMBER;
	}
	public String getLASTORDER() {
		return LASTORDER;
	}
	public String getEOSANNDATE() {
		return EOSANNDATE;
	}
	public String getENDOFSERVICEDATE() {
		return ENDOFSERVICEDATE;
	}
	public List<SLEORGGRP> getSLEORGGRPLIST() {
		return SLEORGGRPLIST;
	}
	public List<SLEORGNPLNTCODE> getSLEORGNPLNTCODELIST() {
		return SLEORGNPLNTCODELIST;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class SVCSEOREF {
	@XmlElement(name = "SVCSEOENTITYTYPE")
	private String SVCSEOENTITYTYPE;
	@XmlElement(name = "SVCSEOENTITYID")
	private String SVCSEOENTITYID;
	@XmlElement(name = "SEOID")
	private String SEOID;
	public String getSVCSEOENTITYTYPE() {
		return SVCSEOENTITYTYPE;
	}
	public String getSVCSEOENTITYID() {
		return SVCSEOENTITYID;
	}
	public String getSEOID() {
		return SEOID;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class TAXCATEGORY {
	@XmlElement(name = "TAXCATEGORYACTION")
	private String TAXCATEGORYACTION;
	@XmlElement(name = "TAXCATEGORYVALUE")
	private String TAXCATEGORYVALUE;
	/*@XmlElement(name = "COUNTRYLIST")
	private String COUNTRYLIST;*/
	@XmlElementWrapper(name = "COUNTRYLIST")
	@XmlElement(name = "COUNTRYELEMENT")
	private List<COUNTRY> COUNTRYLIST;
	@XmlElement(name = "TAXCLASSIFICATION")
	private String TAXCLASSIFICATION;
	@XmlElementWrapper(name = "SLEORGGRPLIST")
	@XmlElement(name = "SLEORGGRPELEMENT")
	private List<SLEORGGRP> SLEORGGRPLIST;
	@XmlElementWrapper(name = "SLEORGNPLNTCODELIST")
	@XmlElement(name = "SLEORGNPLNTCODEELEMENT")
	private List<SLEORGNPLNTCODE> SLEORGNPLNTCODELIST;
	public String getTAXCATEGORYACTION() {
		return TAXCATEGORYACTION;
	}
	public List<COUNTRY> getCOUNTRYLIST() {
		return COUNTRYLIST;
	}
	public String getTAXCLASSIFICATION() {
		return TAXCLASSIFICATION;
	}
	public String getTAXCATEGORYVALUE() {
		return TAXCATEGORYVALUE;
	}
	public List<SLEORGGRP> getSLEORGGRPLIST() {
		return SLEORGGRPLIST;
	}
	public List<SLEORGNPLNTCODE> getSLEORGNPLNTCODELIST() {
		return SLEORGNPLNTCODELIST;
	}
@Override
public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	if(obj instanceof TAXCATEGORY) {
		TAXCATEGORY taxcategory = (TAXCATEGORY)obj;
		if(obj!=null&&this.getCOUNTRYLIST()!=null&&taxcategory.getCOUNTRYLIST()!=null) {
			if(this.getCOUNTRYLIST().size()>0&&taxcategory.getCOUNTRYLIST().size()>0) {
				return this.getCOUNTRYLIST().get(0).getCOUNTRY_FC().equals(taxcategory.getCOUNTRYLIST().get(0).getCOUNTRY_FC());
			}
		}
	}
	
	return false;
}
@Override
public int hashCode() {
	// TODO Auto-generated method stub
	if(this.getCOUNTRYLIST()!=null) {
		if(this.getCOUNTRYLIST().size()>0) {
			return this.getCOUNTRYLIST().get(0).getCOUNTRY_FC().hashCode();
		}
	}
	return super.hashCode();
}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class TAXCODE {
	@XmlElement(name = "TAXCODEACTION")
	private String TAXCODEACTION;
	@XmlElement(name = "TAXCODEDESCRIPTION")
	private String TAXCODEDESCRIPTION;
	@XmlElementWrapper(name = "COUNTRYLISTLIST")
	@XmlElement(name = "COUNTRYLISTELEMENT")
	private List<COUNTRY> COUNTRYLIST;
	@XmlElement(name = "TAXCODE")
	private String TAXCODE;
	@XmlElementWrapper(name = "SLEORGGRPLIST")
	@XmlElement(name = "SLEORGGRPELEMENT")
	private List<SLEORGGRP> SLEORGGRPLIST;
	@XmlElementWrapper(name = "SLEORGNPLNTCODELIST")
	@XmlElement(name = "SLEORGNPLNTCODEELEMENT")
	private List<SLEORGNPLNTCODE> SLEORGNPLNTCODELIST;
	public String getTAXCODEACTION() {
		return TAXCODEACTION;
	}
	public String getTAXCODEDESCRIPTION() {
		return TAXCODEDESCRIPTION;
	}
	public List<COUNTRY> getCOUNTRYLIST() {
		return COUNTRYLIST;
	}
	public String getTAXCODE() {
		return TAXCODE;
	}
	public List<SLEORGGRP> getSLEORGGRPLIST() {
		return SLEORGGRPLIST;
	}
	public List<SLEORGNPLNTCODE> getSLEORGNPLNTCODELIST() {
		return SLEORGNPLNTCODELIST;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class COUNTRY {
	@XmlElement(name = "COUNTRYACTION")
	private String COUNTRYACTION;
	@XmlElement(name = "COUNTRY_FC")
	private String COUNTRY_FC;
	public String getCOUNTRYACTION() {
		return COUNTRYACTION;
	}
	public String getCOUNTRY_FC() {
		return COUNTRY_FC;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class CATATTRIBUTE {
	@XmlElement(name = "CATATTRIBUTEACTION")
	private String CATATTRIBUTEACTION;
	@XmlElement(name = "CATATTRIBUTE")
	private String CATATTRIBUTE;
	@XmlElement(name = "NLSID")
	private String NLSID;
	@XmlElement(name = "CATATTRIUBTEVALUE")
	private String CATATTRIUBTEVALUE;
	public String getCATATTRIBUTEACTION() {
		return CATATTRIBUTEACTION;
	}
	public String getCATATTRIBUTE() {
		return CATATTRIBUTE;
	}
	public String getNLSID() {
		return NLSID;
	}
	public String getCATATTRIUBTEVALUE() {
		return CATATTRIUBTEVALUE;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class UNBUNDCOMP {
	@XmlElement(name = "UNBUNDCOMPACTION")
	private String UNBUNDCOMPACTION;
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "UNBUNDCOMPID")
	private String UNBUNDCOMPID;
	@XmlElement(name = "EFFECTIVEDATE")
	private String EFFECTIVEDATE;
	@XmlElement(name = "ENDDATE")
	private String ENDDATE;
	@XmlElement(name = "UNBUNDTYPE")
	private String UNBUNDTYPE;
	public String getUNBUNDCOMPACTION() {
		return UNBUNDCOMPACTION;
	}
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public String getUNBUNDCOMPID() {
		return UNBUNDCOMPID;
	}
	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
	public String getUNBUNDTYPE() {
		return UNBUNDTYPE;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class CHRGCOMP {
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "CHRGCOMPID")
	private String CHRGCOMPID;
	@XmlElement(name = "BHACCTASGNGRP")
	private String BHACCTASGNGRP;
	@XmlElement(name = "REFSELCONDTN")
	private String REFSELCONDTN;
	@XmlElement(name = "SVCCHRGOPT")
	private String SVCCHRGOPT;
	@XmlElement(name = "EFFECTIVEDATE")
	private String EFFECTIVEDATE;
	@XmlElement(name = "ENDDATE")
	private String ENDDATE;
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "SLEORGGRPELEMENT")
	private List<LANGUAGE> LANGUAGELIST;
	@XmlElementWrapper(name = "PRICEPOINTLIST")
	@XmlElement(name = "PRICEPOINTELEMENT")
	private List<PRICEPOINT> PRICEPOINTLIST;
	@XmlElementWrapper(name = "CHARVALUEMETRICLIST")
	@XmlElement(name = "CHARVALUEMETRICELEMENT")
	private List<CHARVALUEMETRIC> CHARVALUEMETRICLIST;
	
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public String getCHRGCOMPID() {
		return CHRGCOMPID;
	}
	public String getBHACCTASGNGRP() {
		return BHACCTASGNGRP;
	}
	public String getREFSELCONDTN() {
		return REFSELCONDTN;
	}
	public String getSVCCHRGOPT() {
		return SVCCHRGOPT;
	}
	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
	public List<LANGUAGE> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public List<PRICEPOINT> getPRICEPOINTLIST() {
		return PRICEPOINTLIST;
	}
	public List<CHARVALUEMETRIC> getCHARVALUEMETRICLIST() {
		return CHARVALUEMETRICLIST;
	}
	
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class PRICEPOINT {
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "PRCPTID")
	private String PRCPTID;
	@XmlElement(name = "PRCMETH")
	private String PRCMETH;
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "SLEORGGRPELEMENT")
	private List<LANGUAGE> LANGUAGELIST;
	@XmlElementWrapper(name = "CNTRYEFFLIST")
	@XmlElement(name = "CNTRYEFFELEMENT")
	private List<CNTRYEFF> CNTRYEFFLIST;
	@XmlElementWrapper(name = "REFCVMSPECLIST")
	@XmlElement(name = "REFCVMSPECELEMENT")
	private List<REFCVMSPEC> REFCVMSPECLIST;
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public String getPRCPTID() {
		return PRCPTID;
	}
	public String getPRCMETH() {
		return PRCMETH;
	}
	public List<LANGUAGE> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public List<CNTRYEFF> getCNTRYEFFLIST() {
		return CNTRYEFFLIST;
	}
	public List<REFCVMSPEC> getREFCVMSPECLIST() {
		return REFCVMSPECLIST;
	}
	
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class REFCVMSPEC {
	@XmlElement(name = "EFFECTIVEDATE")
	private String EFFECTIVEDATE;
	@XmlElement(name = "ENDDATE")
	private String ENDDATE;
	@XmlElement(name = "REFCVMSPECENTITYTYPE")
	private String REFCVMSPECENTITYTYPE;
	@XmlElement(name = "REFCVMSPECENTITYID")
	private String REFCVMSPECENTITYID;
	@XmlElement(name = "REFCVMSPECTYPE")
	private String REFCVMSPECTYPE;
	@XmlElement(name = "REFCVMSPECID")
	private String REFCVMSPECID;
	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
	public String getREFCVMSPECENTITYTYPE() {
		return REFCVMSPECENTITYTYPE;
	}
	public String getREFCVMSPECENTITYID() {
		return REFCVMSPECENTITYID;
	}
	public String getREFCVMSPECTYPE() {
		return REFCVMSPECTYPE;
	}
	public String getREFCVMSPECID() {
		return REFCVMSPECID;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class CNTRYEFF {
	@XmlElement(name = "EFFECTIVEDATE")
	private String EFFECTIVEDATE;
	@XmlElement(name = "ENDDATE")
	private String ENDDATE;
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElementWrapper(name = "COUNTRYLISTLIST")
	@XmlElement(name = "COUNTRYLISTELEMENT")
	private List<COUNTRY> COUNTRYLIST;
	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public List<COUNTRY> getCOUNTRYLIST() {
		return COUNTRYLIST;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class CHARVALUEMETRIC {
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "CVMTYPE")
	private String CVMTYPE;
	@XmlElement(name = "VMID")
	private String VMID;
	@XmlElement(name = "CHARACID")
	private String CHARACID;
	@XmlElement(name = "EFFECTIVEDATE")
	private String EFFECTIVEDATE;
	@XmlElement(name = "ENDDATE")
	private String ENDDATE;
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "LANGUAGEELEMENT")
	private List<LANGUAGESHRTONLY> LANGUAGELIST;
	@XmlElement(name = "CVMDATATYPE")
	private String CVMDATATYPE;
	@XmlElement(name = "CVMSELTYPE")
	private String CVMSELTYPE;
	@XmlElementWrapper(name = "CVMSPECLIST")
	@XmlElement(name = "CVMSPECELEMENT")
	private List<CVMSPEC> CVMSPECLIST;
	
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public String getCVMTYPE() {
		return CVMTYPE;
	}
	public String getVMID() {
		return VMID;
	}
	public String getCHARACID() {
		return CHARACID;
	}
	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
	public List<LANGUAGESHRTONLY> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public String getCVMDATATYPE() {
		return CVMDATATYPE;
	}
	public String getCVMSELTYPE() {
		return CVMSELTYPE;
	}
	public List<CVMSPEC> getCVMSPECLIST() {
		return CVMSPECLIST;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class CVMSPEC {
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "CVMSPECID")
	private String CVMSPECID;
	@XmlElement(name = "EFFECTIVEDATE")
	private String EFFECTIVEDATE;
	@XmlElement(name = "ENDDATE")
	private String ENDDATE;
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "LANGUAGEELEMENT")
	private List<LANGUAGESHRTONLY> LANGUAGELIST;
	@XmlElement(name = "LOWLIMT")
	private String LOWLIMT;
	@XmlElement(name = "HIGHLIMT")
	private String HIGHLIMT;
	@XmlElement(name = "SPECTYPE")
	private String SPECTYPE;
	@XmlElement(name = "RNGETBLOPT")
	private String RNGETBLOPT;
	
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public String getCVMSPECID() {
		return CVMSPECID;
	}
	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
	public List<LANGUAGESHRTONLY> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public String getLOWLIMT() {
		return LOWLIMT;
	}
	public String getHIGHLIMT() {
		return HIGHLIMT;
	}
	public String getSPECTYPE() {
		return SPECTYPE;
	}
	public String getRNGETBLOPT() {
		return RNGETBLOPT;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class SVCEXECTME {
	@XmlElement(name = "COMPETENCECD")
	private String COMPETENCECD;
	@XmlElement(name = "HR")
	private String HR;
	@XmlElement(name = "IMPLEMENTRBN")
	private String IMPLEMENTRBN;
	@XmlElement(name = "MACHTYPEATR")
	private String MACHTYPEATR;
	@XmlElement(name = "MODELATR")
	private String MODELATR;
	public String getCOMPETENCECD() {
		return COMPETENCECD;
	}
	public String getHR() {
		return HR;
	}
	public String getIMPLEMENTRBN() {
		return IMPLEMENTRBN;
	}
	public String getMACHTYPEATR() {
		return MACHTYPEATR;
	}
	public String getMODELATR() {
		return MODELATR;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class SLEORGGRP {
	@XmlElement(name = "SLEOORGGRPACTION")
	private String SLEOORGGRPACTION;
	@XmlElement(name = "SLEORGGRP")
	private String SLEORGGRP;
	public String getSLEOORGGRPACTION() {
		return SLEOORGGRPACTION;
	}
	public String getSLEORGGRP() {
		return SLEORGGRP;
	}

}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class SLEORGNPLNTCODE {
	@XmlElement(name = "SLEORGNPLNTCODEACTION")
	private String SLEORGNPLNTCODEACTION;
	@XmlElement(name = "SLEORG")
	private String SLEORG;
	@XmlElement(name = "PLNTCD")
	private String PLNTCD;
	public String getSLEORGNPLNTCODEACTION() {
		return SLEORGNPLNTCODEACTION;
	}
	public String getSLEORG() {
		return SLEORG;
	}
	public String getPLNTCD() {
		return PLNTCD;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof SLEORGNPLNTCODE) {
			SLEORGNPLNTCODE newobj = (SLEORGNPLNTCODE) obj;
			return this.SLEORG.equals(newobj.getSLEORG())&&this.PLNTCD.equals(newobj.getPLNTCD())&&this.getSLEORGNPLNTCODEACTION().equals(newobj.getSLEORGNPLNTCODEACTION());
			//
		}
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.SLEORG.hashCode()+this.PLNTCD.hashCode()+this.SLEORGNPLNTCODEACTION.hashCode();
	}

}