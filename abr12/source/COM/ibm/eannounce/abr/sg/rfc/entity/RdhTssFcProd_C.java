package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhTssFcProd_C {
	@SerializedName("MACHTYPE")
    private String machType;
    @SerializedName("MODEL")
    private String model;
    @SerializedName("CHRGCOMID")
    private String chrgComId;
    @SerializedName("ENTITYTYPE")
    private String entityType;
    @SerializedName("ENTITYID")
    private String entityId;
    @SerializedName("PRCPTID")
    private String prcPtId;
    @SerializedName("COUNTRYLIST_FC")
    private String countryList_fc;
    @SerializedName("EFFECTIVEDATE")
    private String effectiveDate;
    @SerializedName("ENDDATE")
    private String endDate;
    
	public String getMachType() {
		return machType;
	}
	public void setMachType(String machType) {
		this.machType = machType;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getChrgComId() {
		return chrgComId;
	}
	public void setChrgComId(String chrgComId) {
		this.chrgComId = chrgComId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getPrcPtId() {
		return prcPtId;
	}
	public void setPrcPtId(String prcPtId) {
		this.prcPtId = prcPtId;
	}
	public String getCountryList_fc() {
		return countryList_fc;
	}
	public void setCountryList_fc(String countryList_fc) {
		this.countryList_fc = countryList_fc;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
