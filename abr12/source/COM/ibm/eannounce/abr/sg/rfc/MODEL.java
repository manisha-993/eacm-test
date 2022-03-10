package COM.ibm.eannounce.abr.sg.rfc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MODEL_UPDATE")
public class MODEL {
	
	
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
	@XmlElement(name = "PRODHIERCD")
	private String PRODHIERCD;
	@XmlElement(name = "PRFTCTR")
	private String PRFTCTR;
	@XmlElement(name = "ACCTASGNGRP")
	private String ACCTASGNGRP;
	@XmlElement(name = "ORDERCODE")
	private String ORDERCODE;
	@XmlElement(name = "PRPQINDC")
	private String PRPQINDC;
	@XmlElement(name = "PHANTOMMODINDC")
	private String PHANTOMMODINDC;
	@XmlElement(name = "DEFAULTCUSTOMIZEABLE")
	private String DEFAULTCUSTOMIZEABLE;

	//add some element	
	@XmlElement(name = "UNITCLASS")
	private String UNITCLASS;
	@XmlElement(name = "PRICEDIND")
	private String PRICEDIND;
	@XmlElement(name = "ZEROPRICE")
	private String ZEROPRICE;
	@XmlElement(name = "INSTALL")
	private String INSTALL;
	@XmlElement(name = "UNSPSC")
	private String UNSPSC;	
	@XmlElement(name = "AMRTZTNLNGTH")
	private String AMRTZTNLNGTH;
	@XmlElement(name = "AMRTZTNSTRT")
	private String AMRTZTNSTRT;
	@XmlElement(name = "PRODID")
	private String PRODID;	
	@XmlElement(name = "SWROYALBEARING")
	private String SWROYALBEARING;
	@XmlElement(name = "SOMFAMILY")
	private String SOMFAMILY;
	@XmlElement(name = "LIC")
	private String LIC;	
	@XmlElement(name = "BPCERTSPECBID")
	private String BPCERTSPECBID;	
	@XmlElement(name = "PRPQAPPRVTYPE")
	private String PRPQAPPRVTYPE;	
	@XmlElement(name = "SPECBID")
	private String SPECBID;
	@XmlElement(name = "PRODSUPRTCD")
	private String PRODSUPRTCD;	
	@XmlElement(name = "SYSTEMTYPE")
	private String SYSTEMTYPE;		
	@XmlElement(name = "WWOCCODE")
	private String WWOCCODE;
	@XmlElement(name = "ACQRCOCD")
	private String ACQRCOCD;	
	@XmlElement(name = "SDFCD")
	private String SDFCD;	
	@XmlElement(name = "SVCLEVCD")
	private String SVCLEVCD;	
	@XmlElement(name = "SVCPACMACHBRAND")
	private String SVCPACMACHBRAND;	
	@XmlElement(name = "COVRPRIOD")
	private String COVRPRIOD;
	
	@XmlElementWrapper(name = "TAXCODELIST")
	@XmlElement(name = "TAXCODEELEMENT")
	private List<TAXCODE> TAXCODELIST;
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "LANGUAGEELEMENT")
	private List<LANGUAGE> LANGUAGELIST;
	@XmlElementWrapper(name = "AVAILABILITYLIST")
	@XmlElement(name = "AVAILABILITYELEMENT")
	private List<AVAILABILITY> AVAILABILITYLIST;
	@XmlElementWrapper(name = "TAXCATEGORYLIST")
	@XmlElement(name = "TAXCATEGORYELEMENT")
	private List<TAXCATEGORY> TAXCATEGORYLIST;
	@XmlElement(name = "INVNAME")
	private String INVNAME ;
	public String getPDHDOMAIN() {
		return PDHDOMAIN;
	}
	public void setPDHDOMAIN(String pDHDOMAIN) {
		PDHDOMAIN = pDHDOMAIN;
	}
	public String getDTSOFMSG() {
		return DTSOFMSG;
	}
	public void setDTSOFMSG(String dTSOFMSG) {
		DTSOFMSG = dTSOFMSG;
	}
	public String getACTIVITY() {
		return ACTIVITY;
	}
	public void setACTIVITY(String aCTIVITY) {
		ACTIVITY = aCTIVITY;
	}
	public String getMODELENTITYTYPE() {
		return MODELENTITYTYPE;
	}
	public void setMODELENTITYTYPE(String mODELENTITYTYPE) {
		MODELENTITYTYPE = mODELENTITYTYPE;
	}
	public String getMODELENTITYID() {
		return MODELENTITYID;
	}
	public void setMODELENTITYID(String mODELENTITYID) {
		MODELENTITYID = mODELENTITYID;
	}
	public String getMACHTYPE() {
		return MACHTYPE;
	}
	public void setMACHTYPE(String mACHTYPE) {
		MACHTYPE = mACHTYPE;
	}
	public String getMODEL() {
		return MODEL;
	}
	public void setMODEL(String mODEL) {
		MODEL = mODEL;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getCATEGORY() {
		return CATEGORY;
	}
	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}
	public String getSUBCATEGORY() {
		return SUBCATEGORY;
	}
	public void setSUBCATEGORY(String sUBCATEGORY) {
		SUBCATEGORY = sUBCATEGORY;
	}
	public String getGROUP() {
		return GROUP;
	}
	public void setGROUP(String gROUP) {
		GROUP = gROUP;
	}
	public String getSUBGROUP() {
		return SUBGROUP;
	}
	public void setSUBGROUP(String sUBGROUP) {
		SUBGROUP = sUBGROUP;
	}
	public String getPRODHIERCD() {
		return PRODHIERCD;
	}
	public void setPRODHIERCD(String pRODHIERCD) {
		PRODHIERCD = pRODHIERCD;
	}
	public String getPRFTCTR() {
		return PRFTCTR;
	}
	public void setPRFTCTR(String pRFTCTR) {
		PRFTCTR = pRFTCTR;
	}
	public String getACCTASGNGRP() {
		return ACCTASGNGRP;
	}
	public void setACCTASGNGRP(String aCCTASGNGRP) {
		ACCTASGNGRP = aCCTASGNGRP;
	}
	public String getORDERCODE() {
		return ORDERCODE;
	}
	public String getPRPQINDC() {
		return PRPQINDC;
	}
	public void setPRPQINDC(String pRPQINDC) {
		PRPQINDC = pRPQINDC;
	}
	public String getPHANTOMMODINDC() {
		return PHANTOMMODINDC;
	}
	public void setPHANTOMMODINDC(String pHANTOMMODINDC) {
		PHANTOMMODINDC = pHANTOMMODINDC;
	}
	public String getDEFAULTCUSTOMIZEABLE() {
		return DEFAULTCUSTOMIZEABLE;
	}
	public void setDEFAULTCUSTOMIZEABLE(String dEFAULTCUSTOMIZEABLE) {
		DEFAULTCUSTOMIZEABLE = dEFAULTCUSTOMIZEABLE;
	}
	public void setORDERCODE(String oRDERCODE) {
		ORDERCODE = oRDERCODE;
	}
	public String getUNITCLASS() {
		return UNITCLASS;
	}
	public void setUNITCLASS(String uNITCLASS) {
		UNITCLASS = uNITCLASS;
	}
	public String getPRICEDIND() {
		return PRICEDIND;
	}
	public void setPRICEDIND(String pRICEDIND) {
		PRICEDIND = pRICEDIND;
	}
	public String getZEROPRICE() {
		return ZEROPRICE;
	}
	public void setZEROPRICE(String zEROPRICE) {
		ZEROPRICE = zEROPRICE;
	}
	public String getINSTALL() {
		return INSTALL;
	}
	public void setINSTALL(String iNSTALL) {
		INSTALL = iNSTALL;
	}
	public String getUNSPSC() {
		return UNSPSC;
	}
	public void setUNSPSC(String uNSPSC) {
		UNSPSC = uNSPSC;
	}
	public String getAMRTZTNLNGTH() {
		return AMRTZTNLNGTH;
	}
	public void setAMRTZTNLNGTH(String aMRTZTNLNGTH) {
		AMRTZTNLNGTH = aMRTZTNLNGTH;
	}
	public String getAMRTZTNSTRT() {
		return AMRTZTNSTRT;
	}
	public void setAMRTZTNSTRT(String aMRTZTNSTRT) {
		AMRTZTNSTRT = aMRTZTNSTRT;
	}
	public String getPRODID() {
		return PRODID;
	}
	public void setPRODID(String pRODID) {
		PRODID = pRODID;
	}
	public String getSWROYALBEARING() {
		return SWROYALBEARING;
	}
	public void setSWROYALBEARING(String sWROYALBEARING) {
		SWROYALBEARING = sWROYALBEARING;
	}
	public String getSOMFAMILY() {
		return SOMFAMILY;
	}
	public void setSOMFAMILY(String sOMFAMILY) {
		SOMFAMILY = sOMFAMILY;
	}
	public String getLIC() {
		return LIC;
	}
	public void setLIC(String lIC) {
		LIC = lIC;
	}
	public String getBPCERTSPECBID() {
		return BPCERTSPECBID;
	}
	public void setBPCERTSPECBID(String bPCERTSPECBID) {
		BPCERTSPECBID = bPCERTSPECBID;
	}
	public String getPRPQAPPRVTYPE() {
		return PRPQAPPRVTYPE;
	}
	public void setPRPQAPPRVTYPE(String pRPQAPPRVTYPE) {
		PRPQAPPRVTYPE = pRPQAPPRVTYPE;
	}
	public String getSPECBID() {
		return SPECBID;
	}
	public void setSPECBID(String sPECBID) {
		SPECBID = sPECBID;
	}
	public String getPRODSUPRTCD() {
		return PRODSUPRTCD;
	}
	public void setPRODSUPRTCD(String pRODSUPRTCD) {
		PRODSUPRTCD = pRODSUPRTCD;
	}
	public String getSYSTEMTYPE() {
		return SYSTEMTYPE;
	}
	public void setSYSTEMTYPE(String sYSTEMTYPE) {
		SYSTEMTYPE = sYSTEMTYPE;
	}
	public String getWWOCCODE() {
		return WWOCCODE;
	}
	public void setWWOCCODE(String wWOCCODE) {
		WWOCCODE = wWOCCODE;
	}
	public String getACQRCOCD() {
		return ACQRCOCD;
	}
	public void setACQRCOCD(String aCQRCOCD) {
		ACQRCOCD = aCQRCOCD;
	}
	public String getSDFCD() {
		return SDFCD;
	}
	public void setSDFCD(String sDFCD) {
		SDFCD = sDFCD;
	}
	public String getSVCLEVCD() {
		return SVCLEVCD;
	}
	public void setSVCLEVCD(String sVCLEVCD) {
		SVCLEVCD = sVCLEVCD;
	}
	public String getSVCPACMACHBRAND() {
		return SVCPACMACHBRAND;
	}
	public void setSVCPACMACHBRAND(String sVCPACMACHBRAND) {
		SVCPACMACHBRAND = sVCPACMACHBRAND;
	}
	public String getCOVRPRIOD() {
		return COVRPRIOD;
	}
	public void setCOVRPRIOD(String cOVRPRIOD) {
		COVRPRIOD = cOVRPRIOD;
	}
	public List<TAXCODE> getTAXCODELIST() {
		return TAXCODELIST;
	}
	public void setTAXCODELIST(List<TAXCODE> tAXCODELIST) {
		TAXCODELIST = tAXCODELIST;
	}
	public List<LANGUAGE> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public void setLANGUAGELIST(List<LANGUAGE> lANGUAGELIST) {
		LANGUAGELIST = lANGUAGELIST;
	}
	public List<AVAILABILITY> getAVAILABILITYLIST() {
		return AVAILABILITYLIST;
	}
	public void setAVAILABILITYLIST(List<AVAILABILITY> aVAILABILITYLIST) {
		AVAILABILITYLIST = aVAILABILITYLIST;
	}
	public List<TAXCATEGORY> getTAXCATEGORYLIST() {
		return TAXCATEGORYLIST;
	}
	public void setTAXCATEGORYLIST(List<TAXCATEGORY> tAXCATEGORYLIST) {
		TAXCATEGORYLIST = tAXCATEGORYLIST;
	}
	public String getINVNAME() {
		return INVNAME;
	}
	public void setINVNAME(String iNVNAME) {
		INVNAME = iNVNAME;
	}


}
