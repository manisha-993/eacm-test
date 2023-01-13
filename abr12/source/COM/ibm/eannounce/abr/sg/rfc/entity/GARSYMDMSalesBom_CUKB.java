package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class GARSYMDMSalesBom_CUKB {
    @SerializedName("MATNR")
    private String matnr;
    @SerializedName("WERKS")
    private String werks;
    @SerializedName("IDNRK")
    private String idnrk;
    @SerializedName("DEP_INTERN")
    private String dep_intern;
    @SerializedName("DEP_TYPE")
    private String dep_type;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getIdnrk() {
        return idnrk;
    }

    public void setIdnrk(String idnrk) {
        this.idnrk = idnrk;
    }

    public String getDep_intern() {
        return dep_intern;
    }

    public void setDep_intern(String dep_intern) {
        this.dep_intern = dep_intern;
    }

    public String getDep_type() {
        return dep_type;
    }

    public void setDep_type(String dep_type) {
        this.dep_type = dep_type;
    }
}
