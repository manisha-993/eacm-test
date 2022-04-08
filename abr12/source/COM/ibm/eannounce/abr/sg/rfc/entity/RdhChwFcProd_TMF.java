package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhChwFcProd_TMF {
	@SerializedName("MACHTYPE")
    private String machType;
    @SerializedName("MODEL")
    private String model;
    @SerializedName("FEATURECODE")
    private String featureCode;
    @SerializedName("FEATUREENTITYTYPE")
    private String featureEntityType;
    @SerializedName("COUNTRY_FC")
    private String country_fc;
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
    @SerializedName("NOCSTSHIP")
    private String noCstShip;
    @SerializedName("INSTALL")
    private String install;
    @SerializedName("CONFIGURATORFLAG")
    private String configuratorFlag;
    @SerializedName("BULKMESINDC")
    private String bulkMesIndc;
    @SerializedName("ORDERCODE")
    private String orderCode;
    @SerializedName("SYSTEMMAX")
    private String systemMax;
    
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
	public String getCountry_fc() {
		return country_fc;
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
	public String getNoCstShip() {
		return noCstShip;
	}
	public String getInstall() {
		return install;
	}
	public String getConfiguratorFlag() {
		return configuratorFlag;
	}
	public String getBulkMesIndc() {
		return bulkMesIndc;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public String getSystemMax() {
		return systemMax;
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
	public void setCountry_fc(String country_fc) {
		this.country_fc = country_fc;
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
	public void setNoCstShip(String noCstShip) {
		this.noCstShip = noCstShip;
	}
	public void setInstall(String install) {
		this.install = install;
	}
	public void setConfiguratorFlag(String configuratorFlag) {
		this.configuratorFlag = configuratorFlag;
	}
	public void setBulkMesIndc(String bulkMesIndc) {
		this.bulkMesIndc = bulkMesIndc;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public void setSystemMax(String systemMax) {
		this.systemMax = systemMax;
	}
}