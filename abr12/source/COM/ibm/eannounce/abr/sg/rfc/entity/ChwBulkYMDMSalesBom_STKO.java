package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class ChwBulkYMDMSalesBom_STKO {
	@SerializedName("MATNR")
    private String matnr;
	@SerializedName("WERKS")
    private String werks;
	@SerializedName("DATUV")
    private String datuv;
	
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getDatuv() {
		return datuv;
	}
	public void setDatuv(String datuv) {
		this.datuv = datuv;
	}
	
}
