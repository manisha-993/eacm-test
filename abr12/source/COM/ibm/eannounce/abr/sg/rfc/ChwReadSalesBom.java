/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import com.google.gson.annotations.SerializedName;


public class ChwReadSalesBom extends RdhBase {
	@SerializedName("MATERIAL") 
	private String material;
	@SerializedName("PLANT")
	private String plant;
	@SerializedName("BOM_USAGE")
	private String bom_usage;
	
	public ChwReadSalesBom (
			String matnr, String plant) {	
		super(null, "CSAP_MAT_BOM_READ".toLowerCase(), null);
		this.pims_identity = "H";
		this.material = matnr;
		this.plant = plant;
		this.bom_usage = "5";
		this.default_mandt ="10H";
	}
	

	@Override
	protected void setDefaultValues() {
	}

	@Override
	protected boolean isReadyToExecute() {
	    return true;
	}
}
