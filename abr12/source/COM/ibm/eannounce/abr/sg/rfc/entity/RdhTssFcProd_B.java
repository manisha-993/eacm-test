package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhTssFcProd_B {
	@SerializedName("MACHTYPE")
    private String machType;
    @SerializedName("MODEL")
    private String model;
    @SerializedName("AVAILABILITYACTION")
    private String availabilityAction;
    @SerializedName("COUNTRY_FC")
    private String country_fc;
    @SerializedName("ANNDATE")
    private String annDate;
    @SerializedName("FIRSTORDER")
    private String firstOrder;
    @SerializedName("PLANEDAVAILABILITY")
    private String planedAvailability;
    @SerializedName("PUBFROM")
    private String pubFrom;
    @SerializedName("PUBTO")
    private String pubTo;
    @SerializedName("WDANNDATE")
    private String wdAnnDate;
    @SerializedName("LASTORDER")
    private String lastOrder;
    @SerializedName("EOSANNDATE")
    private String eosAnnDate;
    @SerializedName("ENDOFSERVICEDATE")
    private String endOfServiceDate;
    
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
	public String getAvailabilityAction() {
		return availabilityAction;
	}
	public void setAvailabilityAction(String availabilityAction) {
		this.availabilityAction = availabilityAction;
	}
	public String getCountry_fc() {
		return country_fc;
	}
	public void setCountry_fc(String country_fc) {
		this.country_fc = country_fc;
	}
	public String getAnnDate() {
		return annDate;
	}
	public void setAnnDate(String annDate) {
		this.annDate = annDate;
	}
	public String getFirstOrder() {
		return firstOrder;
	}
	public void setFirstOrder(String firstOrder) {
		this.firstOrder = firstOrder;
	}
	public String getPlanedAvailability() {
		return planedAvailability;
	}
	public void setPlanedAvailability(String planedAvailability) {
		this.planedAvailability = planedAvailability;
	}
	public String getPubFrom() {
		return pubFrom;
	}
	public void setPubFrom(String pubFrom) {
		this.pubFrom = pubFrom;
	}
	public String getPubTo() {
		return pubTo;
	}
	public void setPubTo(String pubTo) {
		this.pubTo = pubTo;
	}
	public String getWdAnnDate() {
		return wdAnnDate;
	}
	public void setWdAnnDate(String wdAnnDate) {
		this.wdAnnDate = wdAnnDate;
	}
	public String getLastOrder() {
		return lastOrder;
	}
	public void setLastOrder(String lastOrder) {
		this.lastOrder = lastOrder;
	}
	public String getEosAnnDate() {
		return eosAnnDate;
	}
	public void setEosAnnDate(String eosAnnDate) {
		this.eosAnnDate = eosAnnDate;
	}
	public String getEndOfServiceDate() {
		return endOfServiceDate;
	}
	public void setEndOfServiceDate(String endOfServiceDate) {
		this.endOfServiceDate = endOfServiceDate;
	}
}