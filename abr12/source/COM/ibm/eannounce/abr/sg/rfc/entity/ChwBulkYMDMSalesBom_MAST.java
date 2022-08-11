package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class ChwBulkYMDMSalesBom_MAST {
	@SerializedName("MATNR")
    private String matnr;
	@SerializedName("WERKS")
    private String werks;
	@SerializedName("LOSVN")
    private String losvn;
	@SerializedName("LOSBS")
    private String losbs;
	
	
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
	public String getLosvn() {
		return losvn;
	}
	public void setLosvn(String losvn) {
		this.losvn = losvn;
	}
	public String getLosbs() {
		return losbs;
	}
	public void setLosbs(String losbs) {
		this.losbs = losbs;
	}
	
}
