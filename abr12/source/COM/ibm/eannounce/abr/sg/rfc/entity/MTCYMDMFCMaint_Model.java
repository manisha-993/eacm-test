package COM.ibm.eannounce.abr.sg.rfc.entity;

public class MTCYMDMFCMaint_Model {

    @SerializedName("MODELUPGRADEENTITYTYPE")
    private String modelEntitytype;
    @SerializedName("MODELUPGRADEENTITYID")
    private String modelEntityid;
    @SerializedName("FROMMACHTYPE")
    private String fromMachtype;
    @SerializedName("TOMACHTYPE")
    private String toMachtype;
    @SerializedName("COUNTRY_FC")
    private String country_fc;
    @SerializedName("AVAILABILITYACTION")
    private String availAction;
    @SerializedName("ANNDATE")
    private String annDate;
    @SerializedName("FIRSTORDER")
    private String firstOrder;
    @SerializedName("PLANEDAVAILABILITY")
    private String planAvail;
    @SerializedName("PUBFROM")
    private String pubFrom;
    @SerializedName("PUBTO")
    private String pubTo;
    @SerializedName("WDANNDATE")
    private String wdAnndate;
    @SerializedName("LASTORDER")
    private String LastOrder;

    public String getModelEntitytype() {
        return modelEntitytype;
    }

    public void setModelEntitytype(String modelEntitytype) {
        this.modelEntitytype = modelEntitytype;
    }

    public String getModelEntityid() {
        return modelEntityid;
    }

    public void setModelEntityid(String modelEntityid) {
        this.modelEntityid = modelEntityid;
    }

    public String getFromMachtype() {
        return fromMachtype;
    }

    public void setFromMachtype(String fromMachtype) {
        this.fromMachtype = fromMachtype;
    }

    public String getToMachtype() {
        return toMachtype;
    }

    public void setToMachtype(String toMachtype) {
        this.toMachtype = toMachtype;
    }

    public String getCountry_fc() {
        return country_fc;
    }

    public void setCountry_fc(String country_fc) {
        this.country_fc = country_fc;
    }

    public String getAvailAction() {
        return availAction;
    }

    public void setAvailAction(String availAction) {
        this.availAction = availAction;
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

    public String getPlanAvail() {
        return planAvail;
    }

    public void setPlanAvail(String planAvail) {
        this.planAvail = planAvail;
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

    public String getWdAnndate() {
        return wdAnndate;
    }

    public void setWdAnndate(String wdAnndate) {
        this.wdAnndate = wdAnndate;
    }

    public String getLastOrder() {
        return LastOrder;
    }

    public void setLastOrder(String lastOrder) {
        LastOrder = lastOrder;
    }
}
