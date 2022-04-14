package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhChwFcProd_FCTRANSACTION {
	@SerializedName("FROMMACHTYPE")
    private String FROMMACHTYPE;
    @SerializedName("TOMACHTYPE")
    private String TOMACHTYPE;
    @SerializedName("FROMMODEL")
    private String FROMMODEL;
    @SerializedName("TOMODEL")
    private String TOMODEL;
    @SerializedName("FROMFEATURECODE")
    private String FROMFEATURECODE;
    @SerializedName("TOFEATURECODE")
    private String TOFEATURECODE;
    @SerializedName("FEATURETRANSCAT")
    private String FEATURETRANSCAT;
    @SerializedName("RETURNEDPARTSMES")
    private String RETURNEDPARTSMES;
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
	public String getFROMFEATURECODE() {
		return FROMFEATURECODE;
	}
	public void setFROMFEATURECODE(String fROMFEATURECODE) {
		FROMFEATURECODE = fROMFEATURECODE;
	}
	public String getTOFEATURECODE() {
		return TOFEATURECODE;
	}
	public void setTOFEATURECODE(String tOFEATURECODE) {
		TOFEATURECODE = tOFEATURECODE;
	}
	public String getFEATURETRANSCAT() {
		return FEATURETRANSCAT;
	}
	public void setFEATURETRANSCAT(String fEATURETRANSCAT) {
		FEATURETRANSCAT = fEATURETRANSCAT;
	}
	public String getRETURNEDPARTSMES() {
		return RETURNEDPARTSMES;
	}
	public void setRETURNEDPARTSMES(String rETURNEDPARTSMES) {
		RETURNEDPARTSMES = rETURNEDPARTSMES;
	}
    
    
}