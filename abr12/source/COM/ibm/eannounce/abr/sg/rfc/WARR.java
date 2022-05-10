package COM.ibm.eannounce.abr.sg.rfc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "WARR_UPDATE")
public class WARR {
	
	
	@XmlElement(name = "DTSOFMSG")
	private String DTSOFMSG;
	@XmlElement(name = "ACTIVITY")
	private String ACTIVITY;
	@XmlElement(name = "STATUS")		
	private String STATUS;	
	@XmlElement(name = "WARRENTITYTYPE")
	private String WARRENTITYTYPE;	
	@XmlElement(name = "WARRENTITYID")	
	private String WARRENTITYID;
	@XmlElement(name = "WARRID")	
	private String WARRID;
	@XmlElement(name = "WARRDATERULEKEY")	
	private String WARRDATERULEKEY;
	@XmlElement(name = "COVRHR")	
	private String COVRHR;
	@XmlElement(name = "RESPPROF")	
	private String RESPPROF;
	@XmlElement(name = "WARRPRIOD")	
	private String WARRPRIOD;
	@XmlElement(name = "WARRPRIODUOM")	
	private String WARRPRIODUOM;
	@XmlElement(name = "WARRTYPE")	
	private String WARRTYPE;
	@XmlElement(name = "WARRCATG")	
	private String WARRCATG;
	@XmlElement(name = "BHWARRCATEGORY")	
	private String BHWARRCATEGORY;
	@XmlElement(name = "OEMESAPRTSLBR")	
	private String OEMESAPRTSLBR;
	@XmlElement(name = "OEMESAPRTSONY")	
	private String OEMESAPRTSONY;
	
	@XmlElement(name = "TIERWSU")	
	private String TIERWSU;
	@XmlElement(name = "TECHADV")	
	private String TECHADV;
	@XmlElement(name = "REMCODLOAD")	
	private String REMCODLOAD;
	@XmlElement(name = "ENHCOMRES")	
	private String ENHCOMRES;
	@XmlElement(name = "PREDSUPP")	
	private String PREDSUPP;
	
	@XmlElement(name = "TIERMAIN")	
	private String TIERMAIN;
	@XmlElement(name = "SVC1")	
	private String SVC1;
	@XmlElement(name = "SVC2")	
	private String SVC2;
	@XmlElement(name = "SVC3")	
	private String SVC3;
	@XmlElement(name = "SVC4")	
	private String SVC4;
	
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "LANGUAGEELEMENT")
	private List<LANGUAGEELEMENT_WARR> LANGUAGELIST;
	
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

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getWARRENTITYTYPE() {
		return WARRENTITYTYPE;
	}

	public void setWARRENTITYTYPE(String wARRENTITYTYPE) {
		WARRENTITYTYPE = wARRENTITYTYPE;
	}

	public String getWARRENTITYID() {
		return WARRENTITYID;
	}

	public void setWARRENTITYID(String wARRENTITYID) {
		WARRENTITYID = wARRENTITYID;
	}

	public String getWARRID() {
		return WARRID;
	}

	public void setWARRID(String wARRID) {
		WARRID = wARRID;
	}

	public String getWARRDATERULEKEY() {
		return WARRDATERULEKEY;
	}

	public void setWARRDATERULEKEY(String wARRDATERULEKEY) {
		WARRDATERULEKEY = wARRDATERULEKEY;
	}

	public String getCOVRHR() {
		return COVRHR;
	}

	public void setCOVRHR(String cOVRHR) {
		COVRHR = cOVRHR;
	}

	public String getRESPPROF() {
		return RESPPROF;
	}

	public void setRESPPROF(String rESPPROF) {
		RESPPROF = rESPPROF;
	}

	public String getWARRPRIOD() {
		return WARRPRIOD;
	}

	public void setWARRPRIOD(String wARRPRIOD) {
		WARRPRIOD = wARRPRIOD;
	}

	public String getWARRPRIODUOM() {
		return WARRPRIODUOM;
	}

	public void setWARRPRIODUOM(String wARRPRIODUOM) {
		WARRPRIODUOM = wARRPRIODUOM;
	}

	public String getWARRTYPE() {
		return WARRTYPE;
	}

	public void setWARRTYPE(String wARRTYPE) {
		WARRTYPE = wARRTYPE;
	}

	public String getWARRCATG() {
		return WARRCATG;
	}

	public void setWARRCATG(String wARRCATG) {
		WARRCATG = wARRCATG;
	}

	public String getBHWARRCATEGORY() {
		return BHWARRCATEGORY;
	}

	public void setBHWARRCATEGORY(String bHWARRCATEGORY) {
		BHWARRCATEGORY = bHWARRCATEGORY;
	}

	public String getOEMESAPRTSLBR() {
		return OEMESAPRTSLBR;
	}

	public void setOEMESAPRTSLBR(String oEMESAPRTSLBR) {
		OEMESAPRTSLBR = oEMESAPRTSLBR;
	}

	public String getOEMESAPRTSONY() {
		return OEMESAPRTSONY;
	}

	public void setOEMESAPRTSONY(String oEMESAPRTSONY) {
		OEMESAPRTSONY = oEMESAPRTSONY;
	}

	public String getTIERWSU() {
		return TIERWSU;
	}

	public void setTIERWSU(String tIERWSU) {
		TIERWSU = tIERWSU;
	}

	public String getTECHADV() {
		return TECHADV;
	}

	public void setTECHADV(String tECHADV) {
		TECHADV = tECHADV;
	}

	public String getREMCODLOAD() {
		return REMCODLOAD;
	}

	public void setREMCODLOAD(String rEMCODLOAD) {
		REMCODLOAD = rEMCODLOAD;
	}

	public String getENHCOMRES() {
		return ENHCOMRES;
	}

	public void setENHCOMRES(String eNHCOMRES) {
		ENHCOMRES = eNHCOMRES;
	}

	public String getPREDSUPP() {
		return PREDSUPP;
	}

	public void setPREDSUPP(String pREDSUPP) {
		PREDSUPP = pREDSUPP;
	}

	public String getTIERMAIN() {
		return TIERMAIN;
	}

	public void setTIERMAIN(String tIERMAIN) {
		TIERMAIN = tIERMAIN;
	}

	public List<LANGUAGEELEMENT_WARR> getLANGUAGELIST() {
		return LANGUAGELIST;
	}

	public void setLANGUAGELIST(List<LANGUAGEELEMENT_WARR> lANGUAGELIST) {
		LANGUAGELIST = lANGUAGELIST;
	}	

	public String getSVC1() {
		return SVC1;
	}

	public void setSVC1(String sVC1) {
		SVC1 = sVC1;
	}

	public String getSVC2() {
		return SVC2;
	}

	public void setSVC2(String sVC2) {
		SVC2 = sVC2;
	}

	public String getSVC3() {
		return SVC3;
	}

	public void setSVC3(String sVC3) {
		SVC3 = sVC3;
	}

	public String getSVC4() {
		return SVC4;
	}

	public void setSVC4(String sVC4) {
		SVC4 = sVC4;
	}



	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	@XmlAccessorType(XmlAccessType.FIELD)
	class LANGUAGEELEMENT_WARR {
		@XmlElement(name = "NLSID")
		private String NLSID;
		@XmlElement(name = "INVNAME")
		private String INVNAME;
		@XmlElement(name = "MKTGNAME")
		private String MKTGNAME;
		@XmlElement(name = "WARRDESC")
		private String WARRDESC;
		public String getNLSID() {
			return NLSID;
		}
		public void setNLSID(String nLSID) {
			NLSID = nLSID;
		}
		public String getINVNAME() {
			return INVNAME;
		}
		public void setINVNAME(String iNVNAME) {
			INVNAME = iNVNAME;
		}
		public String getMKTGNAME() {
			return MKTGNAME;
		}
		public void setMKTGNAME(String mKTGNAME) {
			MKTGNAME = mKTGNAME;
		}
		public String getWARRDESC() {
			return WARRDESC;
		}
		public void setWARRDESC(String wARRDESC) {
			WARRDESC = wARRDESC;
		}
		
		
	}
	
		
		
}
