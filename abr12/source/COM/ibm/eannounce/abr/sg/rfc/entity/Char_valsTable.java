package COM.ibm.eannounce.abr.sg.rfc.entity;

public class Char_valsTable
{
	private String CHARACT;  //30	Required=Yes
	private String VALUE;	 //30	Required=Yes
	private String FLDELETE; //1	Required=No
	
	public String getCHARACT() {
		return CHARACT;
	}
	public void setCHARACT(String cHARACT) {
		CHARACT = cHARACT;
	}
	public String getVALUE() {
		return VALUE;
	}
	public void setVALUE(String vALUE) {
		VALUE = vALUE;
	}
	public String getFLDELETE() {
		return FLDELETE;
	}
	public void setFLDELETE(String fLDELETE) {
		FLDELETE = fLDELETE;
	}
	
	
}
