package COM.ibm.eannounce.abr.sg.rfc.entity;

public class ChwBulkYMDMProd_FEATURE {

    @SerializedName("ENTITYTYPE")
    private String entityType;
    @SerializedName("ENTITYID")
    private String entityID;
    @SerializedName("FEATURECODE")
    private String featureCode;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }
}
