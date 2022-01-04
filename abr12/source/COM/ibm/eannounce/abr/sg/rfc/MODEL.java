package COM.ibm.eannounce.abr.sg.rfc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

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
	public String getPRODHIERCD() {
		return PRODHIERCD;
	}
	public String getPRFTCTR() {
		return PRFTCTR;
	}
	public String getACCTASGNGRP() {
		return ACCTASGNGRP;
	}
	public List<TAXCODE> getTAXCODELIST() {
		return TAXCODELIST;
	}
	public List<LANGUAGE> getLANGUAGELIST() {
		return LANGUAGELIST;
	}
	public List<AVAILABILITY> getAVAILABILITYLIST() {
		return AVAILABILITYLIST;
	}
	public List<TAXCATEGORY> getTAXCATEGORYLIST() {
		return TAXCATEGORYLIST;
	}
	public String getCATEGORY() {
		return CATEGORY;
	}
	public String getORDERCODE() {
		return ORDERCODE;
	}
}
