package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class ChwBulkYMDMProd_MAKT {

    @SerializedName("ENTITYTYPE")
    private String entitytype;
    @SerializedName("ENTITYID")
    private String entityID;
    @SerializedName("NLSID")
    private String nlsid;
    @SerializedName("MKTGDESC")
    private String mktgDesc;
    @SerializedName("MKTGNAME")
    private String mktgName;
    @SerializedName("INVNAME")
    private String invName;
    @SerializedName("BHINVNAME")
    private String bhInvName;

    public String getEntitytype() {
        return entitytype;
    }

    public void setEntitytype(String entitytype) {
        this.entitytype = entitytype;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getNlsid() {
        return nlsid;
    }

    public void setNlsid(String nlsid) {
        this.nlsid = nlsid;
    }

    public String getMktgDesc() {
        return mktgDesc;
    }

    public void setMktgDesc(String mktgDesc) {
        this.mktgDesc = mktgDesc;
    }

    public String getMktgName() {
        return mktgName;
    }

    public void setMktgName(String mktgName) {
        this.mktgName = mktgName;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public String getBhInvName() {
        return bhInvName;
    }

    public void setBhInvName(String bhInvName) {
        this.bhInvName = bhInvName;
    }
}
