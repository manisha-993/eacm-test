package COM.ibm.eannounce.abr.sg.rfc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TMF_UPDATE")
public class TMF_UPDATE {
	@XmlElement(name = "PDHDOMAIN")
	private String PDHDOMAIN;
	@XmlElement(name = "DTSOFMSG")
	private String DTSOFMSG;
	@XmlElement(name = "ACTIVITY")
	private String ACTIVITY;
	@XmlElement(name = "STATUS")
	private String STATUS;
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "MODELENTITYTYPE")
	private String MODELENTITYTYPE;
	@XmlElement(name = "MODELENTITYID")
	private String MODELENTITYID;
	@XmlElement(name = "MACHTYPE")
	private String MACHTYPE;
	@XmlElement(name = "MODEL")
	private String MODEL;
	@XmlElement(name = "FEATUREENTITYTYPE")
	private String FEATUREENTITYTYPE;
	@XmlElement(name = "FEATUREENTITYID")
	private String FEATUREENTITYID;
	@XmlElement(name = "FEATURECODE")
	private String FEATURECODE;
	@XmlElement(name = "FCCAT")
	private String FCCAT;
	@XmlElement(name = "FCTYPE")
	private String FCTYPE;
	@XmlElement(name = "ORDERCODE")
	private String ORDERCODE;
	@XmlElement(name = "SYSTEMMAX")
	private String SYSTEMMAX;
	@XmlElement(name = "SYSTEMMIN")
	private String SYSTEMMIN;
	@XmlElement(name = "CONFIGURATORFLAG")
	private String CONFIGURATORFLAG;
	@XmlElement(name = "BULKMESINDC")
	private String BULKMESINDC;
	@XmlElement(name = "INSTALL")
	private String INSTALL;
	@XmlElement(name = "NOCSTSHIP")
	private String NOCSTSHIP;
	@XmlElement(name = "WARRSVCCOVR")
	private String WARRSVCCOVR;
	@XmlElement(name = "RETURNEDPARTS")
	private String RETURNEDPARTS;
	
	@XmlElementWrapper(name = "OSLIST")
	@XmlElement(name = "OSELEMENT")
	private List<OSELEMENT_TMF> OSLIST;
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "LANGUAGEELEMENT")
	private List<LANGUAGEELEMENT_TMF> LANGUAGELIST;
	@XmlElementWrapper(name = "AVAILABILITYLIST")
	@XmlElement(name = "AVAILABILITYELEMENT")
	private List<AVAILABILITYELEMENT_TMF> AVAILABILITYLIST;
	@XmlElementWrapper(name = "AUDIENCELIST")
	@XmlElement(name = "AUDIENCEELEMENT")
	private List<AUDIENCEELEMENT_TMF> AUDIENCELIST;
	@XmlElementWrapper(name = "CATALOGOVERRIDELIST")
	@XmlElement(name = "CATALOGOVERRIDEELEMENT")
	private List<CATALOGOVERRIDEELEMENT_TMF> CATALOGOVERRIDELIST;
	@XmlElementWrapper(name = "WARRLIST")
	@XmlElement(name = "WARRELEMENT")
	private List<WARRELEMENT_TMF> WARRLIST;
	
	public String getPDHDOMAIN() {
		return PDHDOMAIN;
	}
	public String getDTSOFMSG() {
		return DTSOFMSG;
	}
	public String getACTIVITY() {
		return ACTIVITY;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
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
	public String getFEATUREENTITYTYPE() {
		return FEATUREENTITYTYPE;
	}
	public String getFEATUREENTITYID() {
		return FEATUREENTITYID;
	}
	public String getFEATURECODE() {
		return FEATURECODE;
	}
	public String getFCCAT() {
		return FCCAT;
	}
	public String getFCTYPE() {
		return FCTYPE;
	}
	public String getORDERCODE() {
		return ORDERCODE;
	}
	public String getSYSTEMMAX() {
		return SYSTEMMAX;
	}
	public String getSYSTEMMIN() {
		return SYSTEMMIN;
	}
	public String getCONFIGURATORFLAG() {
		return CONFIGURATORFLAG;
	}
	public String getBULKMESINDC() {
		return BULKMESINDC;
	}
	public String getINSTALL() {
		return INSTALL;
	}
	public String getNOCSTSHIP() {
		return NOCSTSHIP;
	}
	public String getWARRSVCCOVR() {
		return WARRSVCCOVR;
	}
	public List<OSELEMENT_TMF> getOSLIST() {
		return OSLIST;
	}
	public List<LANGUAGEELEMENT_TMF> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public List<AVAILABILITYELEMENT_TMF> getAVAILABILITYLIST() {
		return AVAILABILITYLIST;
	}
	public List<AUDIENCEELEMENT_TMF> getAUDIENCELIST() {
		return AUDIENCELIST;
	}
	public List<CATALOGOVERRIDEELEMENT_TMF> getCATALOGOVERRIDELIST() {
		return CATALOGOVERRIDELIST;
	}
	public List<WARRELEMENT_TMF> getWARRLIST() {
		return WARRLIST;
	}
	public String getRETURNEDPARTS() {
		return RETURNEDPARTS;
	}
	
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class OSELEMENT_TMF {
	@XmlElement(name = "OSACTION")
	private String OSACTION;
	@XmlElement(name = "OS")
	private String OS;
	
	public String getOSACTION() {
		return OSACTION;
	}
	public String getOS() {
		return OS;
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class LANGUAGEELEMENT_TMF {
	@XmlElement(name = "NLSID")
	private String NLSID;
	@XmlElement(name = "MKTGNAME")
	private String MKTGNAME;
	@XmlElement(name = "INVNAME")
	private String INVNAME;
	
	public String getNLSID() {
		return NLSID;
	}
	public String getMKTGNAME() {
		return MKTGNAME;
	}
	public String getINVNAME() {
		return INVNAME;
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class AVAILABILITYELEMENT_TMF {
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
	@XmlElement(name = "ENDOFSERVICEANNNUMBER")
	private String ENDOFSERVICEANNNUMBER;
	@XmlElement(name = "ENDOFSERVICEDATE")
	private String ENDOFSERVICEDATE;
	
	@XmlElementWrapper(name = "SLEORGGRPLIST")
	@XmlElement(name = "SLEORGGRPELEMENT")
	private List<SLEORGGRPELEMENT_TMF> SLEORGGRPLIST;
	@XmlElementWrapper(name = "SLEORGNPLNTCODELIST")
	@XmlElement(name = "SLEORGNPLNTCODEELEMENT")
	private List<SLEORGNPLNTCODEELEMENT_TMF> SLEORGNPLNTCODELIST;
	
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
	public String getENDOFSERVICEANNNUMBER() {
		return ENDOFSERVICEANNNUMBER;
	}
	public List<SLEORGGRPELEMENT_TMF> getSLEORGGRPLIST() {
		return SLEORGGRPLIST;
	}
	public List<SLEORGNPLNTCODEELEMENT_TMF> getSLEORGNPLNTCODELIST() {
		return SLEORGNPLNTCODELIST;
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class SLEORGGRPELEMENT_TMF {
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
class SLEORGNPLNTCODEELEMENT_TMF {
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
		if (obj instanceof SLEORGNPLNTCODEELEMENT_TMF) {
			SLEORGNPLNTCODEELEMENT_TMF newobj = (SLEORGNPLNTCODEELEMENT_TMF) obj;
			return this.SLEORG.equals(newobj.getSLEORG())&&this.PLNTCD.equals(newobj.getPLNTCD())&&this.getSLEORGNPLNTCODEACTION().equals(newobj.getSLEORGNPLNTCODEACTION());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.SLEORG.hashCode()+this.PLNTCD.hashCode()+this.SLEORGNPLNTCODEACTION.hashCode();
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class AUDIENCEELEMENT_TMF {
	@XmlElement(name = "AUDIENCEACTION")
	private String AUDIENCEACTION;
	@XmlElement(name = "AUDIENCE")
	private String AUDIENCE;
	
	public String getAUDIENCEACTION() {
		return AUDIENCEACTION;
	}
	public String getAUDIENCE() {
		return AUDIENCE;
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class CATALOGOVERRIDEELEMENT_TMF {
	@XmlElement(name = "CATALOGOVERRIDEACTION")
	private String CATALOGOVERRIDEACTION;
	@XmlElement(name = "AUDIENCE")
	private String AUDIENCE;
	@XmlElement(name = "COUNTRY_FC")
	private String COUNTRY_FC;
	@XmlElement(name = "PUBFROM")
	private String PUBFROM;
	@XmlElement(name = "PUBTO")
	private String PUBTO;
	@XmlElement(name = "ADDTOCART")
	private String ADDTOCART;
	@XmlElement(name = "BUYABLE")
	private String BUYABLE;
	@XmlElement(name = "PUBLISH")
	private String PUBLISH;
	@XmlElement(name = "CUSTOMIZEABLE")
	private String CUSTOMIZEABLE;
	@XmlElement(name = "HIDE")
	private String HIDE;
	
	public String getCATALOGOVERRIDEACTION() {
		return CATALOGOVERRIDEACTION;
	}
	public String getAUDIENCE() {
		return AUDIENCE;
	}
	public String getCOUNTRY_FC() {
		return COUNTRY_FC;
	}
	public String getPUBFROM() {
		return PUBFROM;
	}
	public String getPUBTO() {
		return PUBTO;
	}
	public String getADDTOCART() {
		return ADDTOCART;
	}
	public String getBUYABLE() {
		return BUYABLE;
	}
	public String getPUBLISH() {
		return PUBLISH;
	}
	public String getCUSTOMIZEABLE() {
		return CUSTOMIZEABLE;
	}
	public String getHIDE() {
		return HIDE;
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class WARRELEMENT_TMF {
	@XmlElement(name = "WARRACTION")
	private String WARRACTION;
	@XmlElement(name = "WARRENTITYTYPE")
	private String WARRENTITYTYPE;
	@XmlElement(name = "WARRENTITYID")
	private String WARRENTITYID;
	@XmlElement(name = "WARRID")
	private String WARRID;
	@XmlElement(name = "WARRDESC")
	private String WARRDESC;
	@XmlElement(name = "PUBFROM")
	private String PUBFROM;
	@XmlElement(name = "PUBTO")
	private String PUBTO;
	@XmlElement(name = "DEFWARR")
	private String DEFWARR;
	
	@XmlElementWrapper(name = "COUNTRYLIST")
	@XmlElement(name = "COUNTRYELEMENT")
	private List<COUNTRYELEMENT_TMF> COUNTRYLIST;

	public String getWARRACTION() {
		return WARRACTION;
	}
	public String getWARRENTITYTYPE() {
		return WARRENTITYTYPE;
	}
	public String getWARRENTITYID() {
		return WARRENTITYID;
	}
	public String getWARRID() {
		return WARRID;
	}
	public String getWARRDESC() {
		return WARRDESC;
	}
	public String getPUBFROM() {
		return PUBFROM;
	}
	public String getPUBTO() {
		return PUBTO;
	}
	public String getDEFWARR() {
		return DEFWARR;
	}
	public List<COUNTRYELEMENT_TMF> getCOUNTRYLIST() {
		return COUNTRYLIST;
	}
	
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class COUNTRYELEMENT_TMF {
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