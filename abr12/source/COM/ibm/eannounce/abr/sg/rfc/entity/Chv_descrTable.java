package COM.ibm.eannounce.abr.sg.rfc.entity;

public class Chv_descrTable {
	private String CHARACT;  	//30	Required=Yes
	private String LANGUAGE;	//1  	Required=Yes
	private String VALUE; 		//30 	Required=No
	private String VALDESCR;    //30 	Required=No
	private String FLDELETE;    //1 	Required=No
	
	public String getCHARACT() {
		return CHARACT;
	}
	public void setCHARACT(String cHARACT) {
		CHARACT = cHARACT;
	}
	public String getLANGUAGE() {
		return LANGUAGE;
	}
	public void setLANGUAGE(String lANGUAGE) {
		LANGUAGE = lANGUAGE;
	}
	public String getVALUE() {
		return VALUE;
	}
	public void setVALUE(String vALUE) {
		VALUE = vALUE;
	}
	public String getVALDESCR() {
		return VALDESCR;
	}
	public void setVALDESCR(String vALDESCR) {
		VALDESCR = vALDESCR;
	}
	public String getFLDELETE() {
		return FLDELETE;
	}
	public void setFLDELETE(String fLDELETE) {
		FLDELETE = fLDELETE;
	}
	
	

}
