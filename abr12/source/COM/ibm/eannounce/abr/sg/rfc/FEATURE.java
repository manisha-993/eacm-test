package COM.ibm.eannounce.abr.sg.rfc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FEATURE_UPDATE")
public class FEATURE {
	@XmlElement(name = "PDHDOMAIN")
	private String PDHDOMAIN;
	@XmlElement(name = "DTSOFMSG")
	private String DTSOFMSG;
	@XmlElement(name = "ACTIVITY")
	private String ACTIVITY;
	@XmlElement(name = "ENTITYTYPE")
	private String ENTITYTYPE;
	@XmlElement(name = "ENTITYID")
	private String ENTITYID;
	@XmlElement(name = "FEATURECODE")
	private String FEATURECODE;
	@XmlElement(name = "FCTYPE")
	private String FCTYPE;
	@XmlElement(name = "STATUS")
	private String STATUS;
	@XmlElement(name = "PRICEDFEATURE")
	private String PRICEDFEATURE;
	@XmlElement(name = "ZEROPRICE")
	private String ZEROPRICE;
	@XmlElement(name = "CHARGEOPTION")
	private String CHARGEOPTION;
	@XmlElement(name = "FCCAT")
	private String FCCAT;
	@XmlElement(name = "FCSUBCAT")
	private String FCSUBCAT;
	@XmlElement(name = "FCGRP")
	private String FCGRP;
	@XmlElement(name = "CONFIGURATORFLAG")
	private String CONFIGURATORFLAG;
	@XmlElement(name = "LICNSOPTTYPE")
	private String LICNSOPTTYPE;
	@XmlElement(name = "MAINTPRICE")
	private String MAINTPRICE;
	@XmlElement(name = "HWORINFOFEATURE")
	private String HWORINFOFEATURE;
	@XmlElement(name = "LICENSETYPE")
	private String LICENSETYPE;
	@XmlElement(name = "FIRSTANNDATE")
	private String FIRSTANNDATE;
	@XmlElement(name = "WTHDRWEFFCTVDATE")
	private String WTHDRWEFFCTVDATE;
	
	@XmlElementWrapper(name = "LANGUAGELIST")
	@XmlElement(name = "LANGUAGEELEMENT")
	private List<LANGUAGEELEMENT_FEATURE> LANGUAGELIST;
	@XmlElementWrapper(name = "COUNTRYLIST")
	@XmlElement(name = "COUNTRYELEMENT")
	private List<COUNTRYELEMENT_FEATURE> COUNTRYLIST;
	@XmlElementWrapper(name = "CATATTRIBUTELIST")
	@XmlElement(name = "CATATTRIBUTEELEMENT")
	private List<CATATTRIBUTEELEMENT_FEATURE> CATATTRIBUTELIST;
	
	public String getPDHDOMAIN() {
		return PDHDOMAIN;
	}
	public String getDTSOFMSG() {
		return DTSOFMSG;
	}
	public String getACTIVITY() {
		return ACTIVITY;
	}
	public String getENTITYTYPE() {
		return ENTITYTYPE;
	}
	public String getENTITYID() {
		return ENTITYID;
	}
	public String getFEATURECODE() {
		return FEATURECODE;
	}
	public String getFCTYPE() {
		return FCTYPE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public String getPRICEDFEATURE() {
		return PRICEDFEATURE;
	}
	public String getZEROPRICE() {
		return ZEROPRICE;
	}
	public String getCHARGEOPTION() {
		return CHARGEOPTION;
	}
	public String getFCCAT() {
		return FCCAT;
	}
	public String getFCSUBCAT() {
		return FCSUBCAT;
	}
	public String getFCGRP() {
		return FCGRP;
	}
	public String getCONFIGURATORFLAG() {
		return CONFIGURATORFLAG;
	}
	public String getLICNSOPTTYPE() {
		return LICNSOPTTYPE;
	}
	public String getMAINTPRICE() {
		return MAINTPRICE;
	}
	public String getHWORINFOFEATURE() {
		return HWORINFOFEATURE;
	}
	public String getLICENSETYPE() {
		return LICENSETYPE;
	}
	public String getFIRSTANNDATE() {
		return FIRSTANNDATE;
	}
	public String getWTHDRWEFFCTVDATE() {
		return WTHDRWEFFCTVDATE;
	}
	public List<LANGUAGEELEMENT_FEATURE> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public List<COUNTRYELEMENT_FEATURE> getCOUNTRYLIST() {
		return COUNTRYLIST;
	}
	public List<CATATTRIBUTEELEMENT_FEATURE> getCATATTRIBUTELIST() {
		return CATATTRIBUTELIST;
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class LANGUAGEELEMENT_FEATURE {
	@XmlElement(name = "NLSID")
	private String NLSID;
	@XmlElement(name = "MKTGDESC")
	private String MKTGDESC;
	@XmlElement(name = "MKTGNAME")
	private String MKTGNAME;
	@XmlElement(name = "INVNAME")
	private String INVNAME;
	@XmlElement(name = "BHINVNAME")
	private String BHINVNAME;
	
	public String getNLSID() {
		return NLSID;
	}
	public String getMKTGDESC() {
		return MKTGDESC;
	}
	public String getMKTGNAME() {
		return MKTGNAME;
	}
	public String getINVNAME() {
		return INVNAME;
	}
	public String getBHINVNAME() {
		return BHINVNAME;
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class COUNTRYELEMENT_FEATURE {
	@XmlElement(name = "ACTIVITY")
	private String ACTIVITY;
	@XmlElement(name = "COUNTRYCODE")
	private String COUNTRYCODE;
	
	public String getACTIVITY() {
		return ACTIVITY;
	}
	public String getCOUNTRYCODE() {
		return COUNTRYCODE;
	}
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class CATATTRIBUTEELEMENT_FEATURE {
	@XmlElement(name = "CATATTRIBUTEACTION")
	private String CATATTRIBUTEACTION;
	@XmlElement(name = "CATATTRIBUTE")
	private String CATATTRIBUTE;
	@XmlElement(name = "NLSID")
	private String NLSID;
	@XmlElement(name = "CATATTRIBUTEVALUE")
	private String CATATTRIBUTEVALUE;
	
	public String getCATATTRIBUTEACTION() {
		return CATATTRIBUTEACTION;
	}
	public String getCATATTRIBUTE() {
		return CATATTRIBUTE;
	}
	public String getNLSID() {
		return NLSID;
	}
	public String getCATATTRIBUTEVALUE() {
		return CATATTRIBUTEVALUE;
	}
}