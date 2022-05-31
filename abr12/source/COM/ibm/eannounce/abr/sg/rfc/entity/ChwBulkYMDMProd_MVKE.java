package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class ChwBulkYMDMProd_MVKE {

    @SerializedName("MODELENTITYTYPE")
    private String modelEntitytype;
    @SerializedName("MODELENTITYID")
    private String modelEntityid;
    @SerializedName("COUNTRY_FC")
    private String country_fc;
    @SerializedName("SLEORG")
    private String sleorg;
    @SerializedName("PLNTCD")
    private String plntCd;
    @SerializedName("PLNTDEL")
    private String plntDel;

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

    public String getCountry_fc() {
        return country_fc;
    }

    public void setCountry_fc(String country_fc) {
        this.country_fc = country_fc;
    }

    public String getSleorg() {
        return sleorg;
    }

    public void setSleorg(String sleorg) {
        this.sleorg = sleorg;
    }

    public String getPlntCd() {
        return plntCd;
    }

    public void setPlntCd(String plntCd) {
        this.plntCd = plntCd;
    }

    public String getPlntDel() {
        return plntDel;
    }

    public void setPlntDel(String plntDel) {
        this.plntDel = plntDel;
    }
}
