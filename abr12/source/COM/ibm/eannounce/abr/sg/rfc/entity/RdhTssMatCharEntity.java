/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhTssMatCharEntity
{
    @SerializedName("MATERIAL_NUMBER")
    private String material_number;
    @SerializedName("FEATURE_ID")
    private String feature_id;
    @SerializedName("FEATURE_NAME")
    private String feature_name;
    @SerializedName("MANOPT_FLAG")
    private String manopt_flag;
    @SerializedName("BUKRS")
    private String bukrs;
    @SerializedName("WITHDRAWDATE")
    private String withdrawdate;
    @SerializedName("WTHDRWEFFCTVDATE")
    private String wthdrweffctvdate;
    @SerializedName("EFFECTIVE_DATE")
    private String effective_date;
    @SerializedName("END_DATE")
    private String end_date;
    
    
	public String getMaterial_number() {
		return material_number;
	}
	public void setMaterial_number(String material_number) {
		this.material_number = material_number;
	}
	public String getFeature_id() {
		return feature_id;
	}
	public void setFeature_id(String feature_id) {
		this.feature_id = feature_id;
	}
	public String getFeature_name() {
		return feature_name;
	}
	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}
	public String getManopt_flag() {
		return manopt_flag;
	}
	public void setManopt_flag(String manopt_flag) {
		this.manopt_flag = manopt_flag;
	}
	public String getBukrs() {
		return bukrs;
	}
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	public String getWithdrawdate() {
		return withdrawdate;
	}
	public void setWithdrawdate(String withdrawdate) {
		this.withdrawdate = withdrawdate;
	}
	public String getWthdrweffctvdate() {
		return wthdrweffctvdate;
	}
	public void setWthdrweffctvdate(String wthdrweffctvdate) {
		this.wthdrweffctvdate = wthdrweffctvdate;
	}
	public String getEffective_date() {
		return effective_date;
	}
	public void setEffective_date(String effective_date) {
		this.effective_date = effective_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
    
}
