package COM.ibm.eannounce.abr.sg.rfc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FCTRANSACTION_UPDATE")
public class FCTRANSACTION {
	
	
	@XmlElement(name = "PDHDOMAIN")
	private String PDHDOMAIN;
	@XmlElement(name = "DTSOFMSG")
	private String DTSOFMSG;
	@XmlElement(name = "ACTIVITY")
	private String ACTIVITY;
	@XmlElement(name = "FEATURECONVERSIONENTITYTYPE")
	private String FEATURECONVERSIONENTITYTYPE;	
	@XmlElement(name = "FEATURECONVERSIONENTITYID")
	private String FEATURECONVERSIONENTITYID;
	
	@XmlElement(name = "FROMMACHTYPE")
	private String FROMMACHTYPE;
	@XmlElement(name = "FROMMODEL")
	private String FROMMODEL;
	@XmlElement(name = "FROMFEATURECODE")
	private String FROMFEATURECODE;			
	@XmlElement(name = "FROMMODELENTITYTYPE")
	private String FROMMODELENTITYTYPE;	
	@XmlElement(name = "FROMMODELENTITYID")	
	private String FROMMODELENTITYID;	
	
	@XmlElement(name = "FROMFEATUREENTITYID")
	private String FROMFEATUREENTITYID;	
	@XmlElement(name = "FROMFEATUREENTITYTYPE")
	private String FROMFEATUREENTITYTYPE;
	@XmlElement(name = "TOMACHTYPE")
	private String TOMACHTYPE;	
	@XmlElement(name = "TOMODEL")
	private String TOMODEL;	
	@XmlElement(name = "TOFEATURECODE")		
	private String TOFEATURECODE;	
	
	@XmlElement(name = "TOMODELENTITYTYPE")
	private String TOMODELENTITYTYPE;	
	@XmlElement(name = "TOMODELENTITYID")
	private String TOMODELENTITYID;
	@XmlElement(name = "TOFEATUREENTITYID")
	private String TOFEATUREENTITYID;	
	@XmlElement(name = "TOFEATUREENTITYTYPE")
	private String TOFEATUREENTITYTYPE;	
	@XmlElement(name = "STATUS")		
	private String STATUS;	
	
	@XmlElement(name = "ANNRFANUMBER")
	private String ANNRFANUMBER;	
	@XmlElement(name = "WDRFANUMBER")
	private String WDRFANUMBER;
	@XmlElement(name = "ANNDATE")
	private String ANNDATE;	
	@XmlElement(name = "WTHDRWEFFCTVDATE")
	private String WTHDRWEFFCTVDATE;	
	@XmlElement(name = "BOXSWAPREQUIREDFORUPGRADES")		
	private String BOXSWAPREQUIREDFORUPGRADES;	
	
	@XmlElement(name = "CUSTOMERSETUP")
	private String CUSTOMERSETUP;	
	@XmlElement(name = "FEATURETRANSACTIONCATEGORY")
	private String FEATURETRANSACTIONCATEGORY;
	@XmlElement(name = "FEATURETRANSACTIONSUBCATEGORY")
	private String FEATURETRANSACTIONSUBCATEGORY;	
	@XmlElement(name = "INSTALLABILITY")
	private String INSTALLABILITY;	
	@XmlElement(name = "INTERNALNOTES")		
	private String INTERNALNOTES;	
	
	@XmlElement(name = "PARTSSHIPPEDINDICATOR")
	private String PARTSSHIPPEDINDICATOR;	
	@XmlElement(name = "QUANTITY")
	private String QUANTITY;
	@XmlElement(name = "REMOVEQUANTITY")
	private String REMOVEQUANTITY;	
	@XmlElement(name = "RETURNEDPARTSMES")
	private String RETURNEDPARTSMES;	
	@XmlElement(name = "UPGRADETYPE")		
	private String UPGRADETYPE;
	
	@XmlElement(name = "ZEROPRICEDINDICATOR")		
	private String ZEROPRICEDINDICATOR;
	
	@XmlElementWrapper(name = "COUNTRYLIST")
	@XmlElement(name = "COUNTRYELEMENT")
	private List<COUNTRY> COUNTRYLIST;

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

	public String getFEATURECONVERSIONENTITYTYPE() {
		return FEATURECONVERSIONENTITYTYPE;
	}

	public void setFEATURECONVERSIONENTITYTYPE(String fEATURECONVERSIONENTITYTYPE) {
		FEATURECONVERSIONENTITYTYPE = fEATURECONVERSIONENTITYTYPE;
	}

	public String getFEATURECONVERSIONENTITYID() {
		return FEATURECONVERSIONENTITYID;
	}

	public void setFEATURECONVERSIONENTITYID(String fEATURECONVERSIONENTITYID) {
		FEATURECONVERSIONENTITYID = fEATURECONVERSIONENTITYID;
	}

	public String getFROMMACHTYPE() {
		return FROMMACHTYPE;
	}

	public void setFROMMACHTYPE(String fROMMACHTYPE) {
		FROMMACHTYPE = fROMMACHTYPE;
	}

	public String getFROMMODEL() {
		return FROMMODEL;
	}

	public void setFROMMODEL(String fROMMODEL) {
		FROMMODEL = fROMMODEL;
	}

	public String getFROMFEATURECODE() {
		return FROMFEATURECODE;
	}

	public void setFROMFEATURECODE(String fROMFEATURECODE) {
		FROMFEATURECODE = fROMFEATURECODE;
	}

	public String getFROMMODELENTITYTYPE() {
		return FROMMODELENTITYTYPE;
	}

	public void setFROMMODELENTITYTYPE(String fROMMODELENTITYTYPE) {
		FROMMODELENTITYTYPE = fROMMODELENTITYTYPE;
	}

	public String getFROMMODELENTITYID() {
		return FROMMODELENTITYID;
	}

	public void setFROMMODELENTITYID(String fROMMODELENTITYID) {
		FROMMODELENTITYID = fROMMODELENTITYID;
	}

	public String getFROMFEATUREENTITYID() {
		return FROMFEATUREENTITYID;
	}

	public void setFROMFEATUREENTITYID(String fROMFEATUREENTITYID) {
		FROMFEATUREENTITYID = fROMFEATUREENTITYID;
	}

	public String getFROMFEATUREENTITYTYPE() {
		return FROMFEATUREENTITYTYPE;
	}

	public void setFROMFEATUREENTITYTYPE(String fROMFEATUREENTITYTYPE) {
		FROMFEATUREENTITYTYPE = fROMFEATUREENTITYTYPE;
	}

	public String getTOMACHTYPE() {
		return TOMACHTYPE;
	}

	public void setTOMACHTYPE(String tOMACHTYPE) {
		TOMACHTYPE = tOMACHTYPE;
	}

	public String getTOMODEL() {
		return TOMODEL;
	}

	public void setTOMODEL(String tOMODEL) {
		TOMODEL = tOMODEL;
	}

	public String getTOFEATURECODE() {
		return TOFEATURECODE;
	}

	public void setTOFEATURECODE(String tOFEATURECODE) {
		TOFEATURECODE = tOFEATURECODE;
	}

	public String getTOMODELENTITYTYPE() {
		return TOMODELENTITYTYPE;
	}

	public void setTOMODELENTITYTYPE(String tOMODELENTITYTYPE) {
		TOMODELENTITYTYPE = tOMODELENTITYTYPE;
	}

	public String getTOMODELENTITYID() {
		return TOMODELENTITYID;
	}

	public void setTOMODELENTITYID(String tOMODELENTITYID) {
		TOMODELENTITYID = tOMODELENTITYID;
	}

	public String getTOFEATUREENTITYID() {
		return TOFEATUREENTITYID;
	}

	public void setTOFEATUREENTITYID(String tOFEATUREENTITYID) {
		TOFEATUREENTITYID = tOFEATUREENTITYID;
	}

	public String getTOFEATUREENTITYTYPE() {
		return TOFEATUREENTITYTYPE;
	}

	public void setTOFEATUREENTITYTYPE(String tOFEATUREENTITYTYPE) {
		TOFEATUREENTITYTYPE = tOFEATUREENTITYTYPE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getANNRFANUMBER() {
		return ANNRFANUMBER;
	}

	public void setANNRFANUMBER(String aNNRFANUMBER) {
		ANNRFANUMBER = aNNRFANUMBER;
	}

	public String getWDRFANUMBER() {
		return WDRFANUMBER;
	}

	public void setWDRFANUMBER(String wDRFANUMBER) {
		WDRFANUMBER = wDRFANUMBER;
	}

	public String getANNDATE() {
		return ANNDATE;
	}

	public void setANNDATE(String aNNDATE) {
		ANNDATE = aNNDATE;
	}

	public String getWTHDRWEFFCTVDATE() {
		return WTHDRWEFFCTVDATE;
	}

	public void setWTHDRWEFFCTVDATE(String wTHDRWEFFCTVDATE) {
		WTHDRWEFFCTVDATE = wTHDRWEFFCTVDATE;
	}

	public String getBOXSWAPREQUIREDFORUPGRADES() {
		return BOXSWAPREQUIREDFORUPGRADES;
	}

	public void setBOXSWAPREQUIREDFORUPGRADES(String bOXSWAPREQUIREDFORUPGRADES) {
		BOXSWAPREQUIREDFORUPGRADES = bOXSWAPREQUIREDFORUPGRADES;
	}

	public String getCUSTOMERSETUP() {
		return CUSTOMERSETUP;
	}

	public void setCUSTOMERSETUP(String cUSTOMERSETUP) {
		CUSTOMERSETUP = cUSTOMERSETUP;
	}

	public String getFEATURETRANSACTIONCATEGORY() {
		return FEATURETRANSACTIONCATEGORY;
	}

	public void setFEATURETRANSACTIONCATEGORY(String fEATURETRANSACTIONCATEGORY) {
		FEATURETRANSACTIONCATEGORY = fEATURETRANSACTIONCATEGORY;
	}

	public String getFEATURETRANSACTIONSUBCATEGORY() {
		return FEATURETRANSACTIONSUBCATEGORY;
	}

	public void setFEATURETRANSACTIONSUBCATEGORY(
			String fEATURETRANSACTIONSUBCATEGORY) {
		FEATURETRANSACTIONSUBCATEGORY = fEATURETRANSACTIONSUBCATEGORY;
	}

	public String getINSTALLABILITY() {
		return INSTALLABILITY;
	}

	public void setINSTALLABILITY(String iNSTALLABILITY) {
		INSTALLABILITY = iNSTALLABILITY;
	}

	public String getINTERNALNOTES() {
		return INTERNALNOTES;
	}

	public void setINTERNALNOTES(String iNTERNALNOTES) {
		INTERNALNOTES = iNTERNALNOTES;
	}

	public String getPARTSSHIPPEDINDICATOR() {
		return PARTSSHIPPEDINDICATOR;
	}

	public void setPARTSSHIPPEDINDICATOR(String pARTSSHIPPEDINDICATOR) {
		PARTSSHIPPEDINDICATOR = pARTSSHIPPEDINDICATOR;
	}

	public String getQUANTITY() {
		return QUANTITY;
	}

	public void setQUANTITY(String qUANTITY) {
		QUANTITY = qUANTITY;
	}

	public String getREMOVEQUANTITY() {
		return REMOVEQUANTITY;
	}

	public void setREMOVEQUANTITY(String rEMOVEQUANTITY) {
		REMOVEQUANTITY = rEMOVEQUANTITY;
	}

	public String getRETURNEDPARTSMES() {
		return RETURNEDPARTSMES;
	}

	public void setRETURNEDPARTSMES(String rETURNEDPARTSMES) {
		RETURNEDPARTSMES = rETURNEDPARTSMES;
	}

	public String getUPGRADETYPE() {
		return UPGRADETYPE;
	}

	public void setUPGRADETYPE(String uPGRADETYPE) {
		UPGRADETYPE = uPGRADETYPE;
	}

	public String getZEROPRICEDINDICATOR() {
		return ZEROPRICEDINDICATOR;
	}

	public void setZEROPRICEDINDICATOR(String zEROPRICEDINDICATOR) {
		ZEROPRICEDINDICATOR = zEROPRICEDINDICATOR;
	}

	public List<COUNTRY> getCOUNTRYLIST() {
		return COUNTRYLIST;
	}

	public void setCOUNTRYLIST(List<COUNTRY> cOUNTRYLIST) {
		COUNTRYLIST = cOUNTRYLIST;
	}
	
	
		
		
}
