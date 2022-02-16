package COM.ibm.eannounce.abr.sg.rfc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MODELCONVERT_UPDATE")
public class MODELCONVERT {
	
	@XmlElement(name = "MODELUPGRADEENTITYTYPE")
	private String MODELUPGRADEENTITYTYPE;
	
	@XmlElement(name = "MODELUPGRADEENTITYID")
	private String MODELUPGRADEENTITYID;
	
	@XmlElement(name = "FROMMACHTYPE")
	private String FROMMACHTYPE;
	
	@XmlElement(name = "FROMMODEL")
	private String FROMMODEL;
	
	@XmlElement(name = "TOMODEL")
	private String TOMODEL;
	
	@XmlElement(name = "TOMODELTYPE")
	private String TOMODELTYPE;
	
	@XmlElement(name = "TOMACHTYPE")
	private String TOMACHTYPE;
	
	@XmlElement(name = "AVAILABILITYLIST")
	private List<AVAILABILITYLIST> AVAILABILITYLIST;

	public String getMODELUPGRADEENTITYTYPE() {
		return MODELUPGRADEENTITYTYPE;
	}

	public void setMODELUPGRADEENTITYTYPE(String mODELUPGRADEENTITYTYPE) {
		MODELUPGRADEENTITYTYPE = mODELUPGRADEENTITYTYPE;
	}

	public String getMODELUPGRADEENTITYID() {
		return MODELUPGRADEENTITYID;
	}

	public void setMODELUPGRADEENTITYID(String mODELUPGRADEENTITYID) {
		MODELUPGRADEENTITYID = mODELUPGRADEENTITYID;
	}

	public String getFROMMACHTYPE() {
		return FROMMACHTYPE;
	}

	public void setFROMMACHTYPE(String fROMMACHTYPE) {
		FROMMACHTYPE = fROMMACHTYPE;
	}

	public String getTOMACHTYPE() {
		return TOMACHTYPE;
	}

	public void setTOMACHTYPE(String tOMACHTYPE) {
		TOMACHTYPE = tOMACHTYPE;
	}

	public List<AVAILABILITYLIST> getAVAILABILITYLIST() {
		return AVAILABILITYLIST;
	}

	public void setAVAILABILITYLIST(List<AVAILABILITYLIST> aVAILABILITYLIST) {
		AVAILABILITYLIST = aVAILABILITYLIST;
	}

	public String getFROMMODEL() {
		return FROMMODEL;
	}

	public void setFROMMODEL(String fROMMODEL) {
		FROMMODEL = fROMMODEL;
	}

	public String getTOMODEL() {
		return TOMODEL;
	}

	public void setTOMODEL(String tOMODEL) {
		TOMODEL = tOMODEL;
	}

	public String getTOMODELTYPE() {
		return TOMODELTYPE;
	}

	public void setTOMODELTYPE(String tOMODELTYPE) {
		TOMODELTYPE = tOMODELTYPE;
	}
	
	
	

}



@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class AVAILABILITYLIST {
	@XmlElement(name = "AVAILABILITYELEMENT")
	private List<AVAILABILITYELEMENT> AVAILABILITYELEMENT;

	public List<AVAILABILITYELEMENT> getAVAILABILITYELEMENT() {
		return AVAILABILITYELEMENT;
	}

	public void setAVAILABILITYELEMENT(List<AVAILABILITYELEMENT> aVAILABILITYELEMENT) {
		AVAILABILITYELEMENT = aVAILABILITYELEMENT;
	}
	
	
}

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
class AVAILABILITYELEMENT {
	@XmlElement(name = "COUNTRY_FC")
	private String COUNTRY_FC;
	
	@XmlElement(name = "AVAILABILITYACTION")
	private String AVAILABILITYACTION;
	
	@XmlElement(name = "ANNDATE")
	private String ANNDATE;
	
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

	public String getCOUNTRY_FC() {
		return COUNTRY_FC;
	}

	public void setCOUNTRY_FC(String cOUNTRY_FC) {
		COUNTRY_FC = cOUNTRY_FC;
	}

	public String getAVAILABILITYACTION() {
		return AVAILABILITYACTION;
	}

	public void setAVAILABILITYACTION(String aVAILABILITYACTION) {
		AVAILABILITYACTION = aVAILABILITYACTION;
	}

	public String getANNDATE() {
		return ANNDATE;
	}

	public void setANNDATE(String aNNDATE) {
		ANNDATE = aNNDATE;
	}

	public String getFIRSTORDER() {
		return FIRSTORDER;
	}

	public void setFIRSTORDER(String fIRSTORDER) {
		FIRSTORDER = fIRSTORDER;
	}

	public String getPLANNEDAVAILABILITY() {
		return PLANNEDAVAILABILITY;
	}

	public void setPLANNEDAVAILABILITY(String pLANNEDAVAILABILITY) {
		PLANNEDAVAILABILITY = pLANNEDAVAILABILITY;
	}

	public String getPUBFROM() {
		return PUBFROM;
	}

	public void setPUBFROM(String pUBFROM) {
		PUBFROM = pUBFROM;
	}

	public String getPUBTO() {
		return PUBTO;
	}

	public void setPUBTO(String pUBTO) {
		PUBTO = pUBTO;
	}

	public String getWDANNDATE() {
		return WDANNDATE;
	}

	public void setWDANNDATE(String wDANNDATE) {
		WDANNDATE = wDANNDATE;
	}

	public String getLASTORDER() {
		return LASTORDER;
	}

	public void setLASTORDER(String lASTORDER) {
		LASTORDER = lASTORDER;
	}
	
	
	
}

