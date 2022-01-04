package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhChwFcProd_Model {
	@SerializedName("MACHTYPE")
    private String machType;
    @SerializedName("MODEL")
    private String model;
    @SerializedName("COUNTRY_FC")
    private String country_fc;
    @SerializedName("AVAILABILITYACTION")
    private String availabilityAction;
    @SerializedName("ANNDATE")
    private String annDate;
    @SerializedName("FIRSTORDER")
    private String firstOrder;
    @SerializedName("PLANNEDAVAILABILITY")
    private String plannedAvailability;
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
    @SerializedName("CATEGORY")
    private String category;
    @SerializedName("ORDERCODE")
    private String orderCode;
    
	public String getMachType() {
		return machType;
	}
	public String getModel() {
		return model;
	}
	public String getCountry_fc() {
		return country_fc;
	}
	public String getAvailabilityAction() {
		return availabilityAction;
	}
	public String getAnnDate() {
		return annDate;
	}
	public String getFirstOrder() {
		return firstOrder;
	}
	public String getPlannedAvailability() {
		return plannedAvailability;
	}
	public String getPubFrom() {
		return pubFrom;
	}
	public String getPubTo() {
		return pubTo;
	}
	public String getWdAnnDate() {
		return wdAnnDate;
	}
	public String getLastOrder() {
		return lastOrder;
	}
	public String getEosAnnDate() {
		return eosAnnDate;
	}
	public String getEndOfServiceDate() {
		return endOfServiceDate;
	}
	public String getCategory() {
		return category;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setMachType(String machType) {
		this.machType = machType;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setCountry_fc(String country_fc) {
		this.country_fc = country_fc;
	}
	public void setAvailabilityAction(String availabilityAction) {
		this.availabilityAction = availabilityAction;
	}
	public void setAnnDate(String annDate) {
		this.annDate = annDate;
	}
	public void setFirstOrder(String firstOrder) {
		this.firstOrder = firstOrder;
	}
	public void setPlannedAvailability(String plannedAvailability) {
		this.plannedAvailability = plannedAvailability;
	}
	public void setPubFrom(String pubFrom) {
		this.pubFrom = pubFrom;
	}
	public void setPubTo(String pubTo) {
		this.pubTo = pubTo;
	}
	public void setWdAnnDate(String wdAnnDate) {
		this.wdAnnDate = wdAnnDate;
	}
	public void setLastOrder(String lastOrder) {
		this.lastOrder = lastOrder;
	}
	public void setEosAnnDate(String eosAnnDate) {
		this.eosAnnDate = eosAnnDate;
	}
	public void setEndOfServiceDate(String endOfServiceDate) {
		this.endOfServiceDate = endOfServiceDate;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
}