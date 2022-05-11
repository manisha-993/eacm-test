package COM.ibm.eannounce.abr.sg.rfc.entity;

public class ChwBulkYMDMProd_MLAN {

    @SerializedName("MODELENTITYTYPE")
    private String modelEntitytype;
    @SerializedName("MODELENTITYID")
    private String modelEntityid;
    @SerializedName("TAXCATEGORYACTION")
    private String taxcategoryAction;
    @SerializedName("COUNTRYACTION")
    private String countryAction;
    @SerializedName("COUNTRY_FC")
    private String country_fc;
    @SerializedName("TAXCATEGORYVALUE")
    private String taxcategoryValue;
    @SerializedName("TAXCLASSIFICATION")
    private String taxClassification;

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

    public String getTaxcategoryAction() {
        return taxcategoryAction;
    }

    public void setTaxcategoryAction(String taxcategoryAction) {
        this.taxcategoryAction = taxcategoryAction;
    }

    public String getCountryAction() {
        return countryAction;
    }

    public void setCountryAction(String countryAction) {
        this.countryAction = countryAction;
    }

    public String getCountry_fc() {
        return country_fc;
    }

    public void setCountry_fc(String country_fc) {
        this.country_fc = country_fc;
    }

    public String getTaxcategoryValue() {
        return taxcategoryValue;
    }

    public void setTaxcategoryValue(String taxcategoryValue) {
        this.taxcategoryValue = taxcategoryValue;
    }

    public String getTaxClassification() {
        return taxClassification;
    }

    public void setTaxClassification(String taxClassification) {
        this.taxClassification = taxClassification;
    }
}
