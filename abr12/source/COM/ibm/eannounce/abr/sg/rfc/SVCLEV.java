package COM.ibm.eannounce.abr.sg.rfc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SVCLEV_UPDATE")
public class SVCLEV {
	
	
	@XmlElement(name = "DTSOFMSG")
	private String DTSOFMSG;
	@XmlElement(name = "SVCLEVCD")
	private String SVCLEVCD;
	@XmlElement(name = "COVRSHRTDESC")
	private String COVRSHRTDESC;
	@XmlElement(name = "EFFECTIVEDATE")
	private String EFFECTIVEDATE;
	@XmlElement(name = "ENDDATE")
	private String ENDDATE;
	
	@XmlElement(name = "SVCDELIVMETH")
	private String SVCDELIVMETH;
	@XmlElement(name = "FIXTME")
	private String FIXTME;
	@XmlElement(name = "FIXTMEUOM")
	private String FIXTMEUOM;
	@XmlElement(name = "FIXTMEOBJIVE")
	private String FIXTMEOBJIVE;
	@XmlElement(name = "ONSITERESPTME")
	private String ONSITERESPTME;
	
	@XmlElement(name = "ONSITERESPTMEUOM")
	private String ONSITERESPTMEUOM;
	@XmlElement(name = "ONSITERESPTMEOBJIVE")
	private String ONSITERESPTMEOBJIVE;
	@XmlElement(name = "CONTTME")
	private String CONTTME;
	@XmlElement(name = "CONTTMEUOM")
	private String CONTTMEUOM;
	@XmlElement(name = "CONTTMEOBJIVE")
	private String CONTTMEOBJIVE;
	
	@XmlElement(name = "TRNARNDTME")
	private String TRNARNDTME;
	@XmlElement(name = "TRNARNDTMEUOM")
	private String TRNARNDTMEUOM;
	@XmlElement(name = "TRNARNDTMEOBJIVE")
	private String TRNARNDTMEOBJIVE;
	@XmlElement(name = "PARTARRVTME")
	private String PARTARRVTME;
	@XmlElement(name = "PARTARRVTMEUOM")
	private String PARTARRVTMEUOM;
	
	@XmlElement(name = "PARTARRVTMEOBJIVE")
	private String PARTARRVTMEOBJIVE;
	@XmlElement(name = "ONSITERESP")
	private String ONSITERESP;
	@XmlElement(name = "ONSITERESPUOM")
	private String ONSITERESPUOM;
	@XmlElement(name = "ONSITERESPOBJIVE")
	private String ONSITERESPOBJIVE;
	
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "LANGUAGEELEMENT")
	private List<LANGUAGE> LANGUAGELIST;
	
//	@XmlElementWrapper(name = "SLCNTRYCONDLIST")
//	@XmlElement(name = "SLCNTRYCONDELEMENT")
//	private List<SLCNTRYCOND> SLCNTRYCONDLIST;
	
	
	public String getDTSOFMSG() {
		return DTSOFMSG;
	}


	public void setDTSOFMSG(String dTSOFMSG) {
		DTSOFMSG = dTSOFMSG;
	}


	public String getSVCLEVCD() {
		return SVCLEVCD;
	}


	public void setSVCLEVCD(String sVCLEVCD) {
		SVCLEVCD = sVCLEVCD;
	}


	public String getCOVRSHRTDESC() {
		return COVRSHRTDESC;
	}


	public void setCOVRSHRTDESC(String cOVRSHRTDESC) {
		COVRSHRTDESC = cOVRSHRTDESC;
	}


	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}


	public void setEFFECTIVEDATE(String eFFECTIVEDATE) {
		EFFECTIVEDATE = eFFECTIVEDATE;
	}


	public String getENDDATE() {
		return ENDDATE;
	}


	public void setENDDATE(String eNDDATE) {
		ENDDATE = eNDDATE;
	}


	public String getSVCDELIVMETH() {
		return SVCDELIVMETH;
	}


	public void setSVCDELIVMETH(String sVCDELIVMETH) {
		SVCDELIVMETH = sVCDELIVMETH;
	}


	public String getFIXTME() {
		return FIXTME;
	}


	public void setFIXTME(String fIXTME) {
		FIXTME = fIXTME;
	}


	public String getFIXTMEUOM() {
		return FIXTMEUOM;
	}


	public void setFIXTMEUOM(String fIXTMEUOM) {
		FIXTMEUOM = fIXTMEUOM;
	}


	public String getFIXTMEOBJIVE() {
		return FIXTMEOBJIVE;
	}


	public void setFIXTMEOBJIVE(String fIXTMEOBJIVE) {
		FIXTMEOBJIVE = fIXTMEOBJIVE;
	}


	public String getONSITERESPTME() {
		return ONSITERESPTME;
	}


	public void setONSITERESPTME(String oNSITERESPTME) {
		ONSITERESPTME = oNSITERESPTME;
	}


	public String getONSITERESPTMEUOM() {
		return ONSITERESPTMEUOM;
	}


	public void setONSITERESPTMEUOM(String oNSITERESPTMEUOM) {
		ONSITERESPTMEUOM = oNSITERESPTMEUOM;
	}


	public String getONSITERESPTMEOBJIVE() {
		return ONSITERESPTMEOBJIVE;
	}


	public void setONSITERESPTMEOBJIVE(String oNSITERESPTMEOBJIVE) {
		ONSITERESPTMEOBJIVE = oNSITERESPTMEOBJIVE;
	}


	public String getCONTTME() {
		return CONTTME;
	}


	public void setCONTTME(String cONTTME) {
		CONTTME = cONTTME;
	}


	public String getCONTTMEUOM() {
		return CONTTMEUOM;
	}


	public void setCONTTMEUOM(String cONTTMEUOM) {
		CONTTMEUOM = cONTTMEUOM;
	}


	public String getCONTTMEOBJIVE() {
		return CONTTMEOBJIVE;
	}


	public void setCONTTMEOBJIVE(String cONTTMEOBJIVE) {
		CONTTMEOBJIVE = cONTTMEOBJIVE;
	}


	public String getTRNARNDTME() {
		return TRNARNDTME;
	}


	public void setTRNARNDTME(String tRNARNDTME) {
		TRNARNDTME = tRNARNDTME;
	}


	public String getTRNARNDTMEUOM() {
		return TRNARNDTMEUOM;
	}


	public void setTRNARNDTMEUOM(String tRNARNDTMEUOM) {
		TRNARNDTMEUOM = tRNARNDTMEUOM;
	}


	public String getTRNARNDTMEOBJIVE() {
		return TRNARNDTMEOBJIVE;
	}


	public void setTRNARNDTMEOBJIVE(String tRNARNDTMEOBJIVE) {
		TRNARNDTMEOBJIVE = tRNARNDTMEOBJIVE;
	}


	public String getPARTARRVTME() {
		return PARTARRVTME;
	}


	public void setPARTARRVTME(String pARTARRVTME) {
		PARTARRVTME = pARTARRVTME;
	}


	public String getPARTARRVTMEUOM() {
		return PARTARRVTMEUOM;
	}


	public void setPARTARRVTMEUOM(String pARTARRVTMEUOM) {
		PARTARRVTMEUOM = pARTARRVTMEUOM;
	}


	public String getPARTARRVTMEOBJIVE() {
		return PARTARRVTMEOBJIVE;
	}


	public void setPARTARRVTMEOBJIVE(String pARTARRVTMEOBJIVE) {
		PARTARRVTMEOBJIVE = pARTARRVTMEOBJIVE;
	}


	public List<LANGUAGE> getLANGUAGELIST() {
		return LANGUAGELIST;
	}


	public void setLANGUAGELIST(List<LANGUAGE> lANGUAGELIST) {
		LANGUAGELIST = lANGUAGELIST;
	}


//	public List<SLCNTRYCOND> getSLCNTRYCONDLIST() {
//		return SLCNTRYCONDLIST;
//	}
//
//
//	public void setSLCNTRYCONDLIST(List<SLCNTRYCOND> sLCNTRYCONDLIST) {
//		SLCNTRYCONDLIST = sLCNTRYCONDLIST;
//	}
	
	


	public String getONSITERESP() {
		return ONSITERESP;
	}


	public void setONSITERESP(String oNSITERESP) {
		ONSITERESP = oNSITERESP;
	}


	public String getONSITERESPUOM() {
		return ONSITERESPUOM;
	}


	public void setONSITERESPUOM(String oNSITERESPUOM) {
		ONSITERESPUOM = oNSITERESPUOM;
	}


	public String getONSITERESPOBJIVE() {
		return ONSITERESPOBJIVE;
	}


	public void setONSITERESPOBJIVE(String oNSITERESPOBJIVE) {
		ONSITERESPOBJIVE = oNSITERESPOBJIVE;
	}




	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	@XmlAccessorType(XmlAccessType.FIELD)
	class SLCNTRYCOND {
		@XmlElement(name = "SLCNTRYCONDACTION")
		private String SLCNTRYCONDACTION;
		@XmlElement(name = "ENTITYTYPE")
		private String ENTITYTYPE;
		@XmlElement(name = "ENTITYID")
		private String ENTITYID;
		public String getSLCNTRYCONDACTION() {
			return SLCNTRYCONDACTION;
		}
		public void setSLCNTRYCONDACTION(String sLCNTRYCONDACTION) {
			SLCNTRYCONDACTION = sLCNTRYCONDACTION;
		}
		public String getENTITYTYPE() {
			return ENTITYTYPE;
		}
		public void setENTITYTYPE(String eNTITYTYPE) {
			ENTITYTYPE = eNTITYTYPE;
		}
		public String getENTITYID() {
			return ENTITYID;
		}
		public void setENTITYID(String eNTITYID) {
			ENTITYID = eNTITYID;
		}

	}
	

}
