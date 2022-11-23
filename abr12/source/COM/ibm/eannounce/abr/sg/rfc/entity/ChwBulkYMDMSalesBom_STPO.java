package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class ChwBulkYMDMSalesBom_STPO {
	@SerializedName("MATNR")
    private String matnr;
	@SerializedName("WERKS")
    private String werks;
	@SerializedName("IDNRK")
    private String idnrk;
	
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
	public String getIdnrk() {
		return idnrk;
	}
	public void setIdnrk(String idnrk) {
		this.idnrk = idnrk;
	}
	
}
