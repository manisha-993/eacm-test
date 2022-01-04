package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhChwFcProd_FEATURE {
	@SerializedName("MACHTYPE")
    private String machType;
    @SerializedName("MODEL")
    private String model;
    @SerializedName("FEATURECODE")
    private String featureCode;
    @SerializedName("FEATUREENTITYTYPE")
    private String featureEntityType;
    @SerializedName("FEATUREENTITYID")
    private String featureEntityID;
    @SerializedName("MKTGDESC")
    private String mktgDesc;
    @SerializedName("FCTYPE")
    private String fcType;
    @SerializedName("FCSUBCAT")
    private String fcSubcat;
    @SerializedName("PRICEDFEATURE")
    private String pricedFeature;
    @SerializedName("FCCAT")
    private String fcCat;
    @SerializedName("CONFIGURATORFLAG")
    private String configuratorFlag;
    @SerializedName("CHARGEOPTION")
    private String chargeOption;
    @SerializedName("LCNSOPTTYPE")
    private String lcnsOptType;
    @SerializedName("CHAR_TYPE")
    private String char_type;
    
	public String getMachType() {
		return machType;
	}
	public String getModel() {
		return model;
	}
	public String getFeatureCode() {
		return featureCode;
	}
	public String getFeatureEntityType() {
		return featureEntityType;
	}
	public String getFeatureEntityID() {
		return featureEntityID;
	}
	public String getMktgDesc() {
		return mktgDesc;
	}
	public String getFcType() {
		return fcType;
	}
	public String getFcSubcat() {
		return fcSubcat;
	}
	public String getPricedFeature() {
		return pricedFeature;
	}
	public String getFcCat() {
		return fcCat;
	}
	public String getConfiguratorFlag() {
		return configuratorFlag;
	}
	public String getChargeOption() {
		return chargeOption;
	}
	public String getLcnsOptType() {
		return lcnsOptType;
	}
	public String getChar_type() {
		return char_type;
	}
	public void setMachType(String machType) {
		this.machType = machType;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}
	public void setFeatureEntityType(String featureEntityType) {
		this.featureEntityType = featureEntityType;
	}
	public void setFeatureEntityID(String featureEntityID) {
		this.featureEntityID = featureEntityID;
	}
	public void setMktgDesc(String mktgDesc) {
		this.mktgDesc = mktgDesc;
	}
	public void setFcType(String fcType) {
		this.fcType = fcType;
	}
	public void setFcSubcat(String fcSubcat) {
		this.fcSubcat = fcSubcat;
	}
	public void setPricedFeature(String pricedFeature) {
		this.pricedFeature = pricedFeature;
	}
	public void setFcCat(String fcCat) {
		this.fcCat = fcCat;
	}
	public void setConfiguratorFlag(String configuratorFlag) {
		this.configuratorFlag = configuratorFlag;
	}
	public void setChargeOption(String chargeOption) {
		this.chargeOption = chargeOption;
	}
	public void setLcnsOptType(String lcnsOptType) {
		this.lcnsOptType = lcnsOptType;
	}
	public void setChar_type(String char_type) {
		this.char_type = char_type;
	}
}