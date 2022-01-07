package COM.ibm.eannounce.abr.sg.rfc.entity;

public class CharactsTable {
	
	private String CHARACT;
	private String DATATYPE;
	private String CHARNUMBER;
	private String DECPLACES;
	//new add CASESENS  no(required)
	private String CASESENS;
	private String NEG_VALS = "";
	private String STATUS;
	private String GROUP = "";
	private String VALASSIGNM;
	private String NO_ENTRY = "";
	private String NO_DISPLAY = "";
	private String ADDIT_VALS = "";
	public String getCHARACT() {
		return CHARACT;
	}
	public void setCHARACT(String cHARACT) {
		CHARACT = cHARACT;
	}
	public String getDATATYPE() {
		return DATATYPE;
	}
	public void setDATATYPE(String dATATYPE) {
		DATATYPE = dATATYPE;
	}
	public String getCHARNUMBER() {
		return CHARNUMBER;
	}
	public void setCHARNUMBER(String cHARNUMBER) {
		CHARNUMBER = cHARNUMBER;
	}	
	public String getDECPLACES() {
		return DECPLACES;
	}
	public void setDECPLACES(String dECPLACES) {
		DECPLACES = dECPLACES;
	}
	
	public String getCASESENS() {
		return CASESENS;
	}
	public void setCASESENS(String cASESENS) {
		CASESENS = cASESENS;
	}
	public String getNEG_VALS() {
		return NEG_VALS;
	}
	public void setNEG_VALS(String nEG_VALS) {
		NEG_VALS = nEG_VALS;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getGROUP() {
		return GROUP;
	}
	public void setGROUP(String gROUP) {
		GROUP = gROUP;
	}
	public String getVALASSIGNM() {
		return VALASSIGNM;
	}
	public void setVALASSIGNM(String vALASSIGNM) {
		VALASSIGNM = vALASSIGNM;
	}
	public String getNO_ENTRY() {
		return NO_ENTRY;
	}
	public void setNO_ENTRY(String nO_ENTRY) {
		NO_ENTRY = nO_ENTRY;
	}
	public String getNO_DISPLAY() {
		return NO_DISPLAY;
	}
	public void setNO_DISPLAY(String nO_DISPLAY) {
		NO_DISPLAY = nO_DISPLAY;
	}
	public String getADDIT_VALS() {
		return ADDIT_VALS;
	}
	public void setADDIT_VALS(String aDDIT_VALS) {
		ADDIT_VALS = aDDIT_VALS;
	}

}
