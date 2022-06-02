package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class ChwBulkYMDMProd_TMF {
    @SerializedName("ENTITYTYPE")
    private String entityType;
    @SerializedName("ENTITYID")
    private String entityID;
    @SerializedName("MODELENTITYTYPE")
    private String modelEntityType;
    @SerializedName("MODELENTITYID")
    private String modelEntityID;
    @SerializedName("FEATUREENTITYTYPE")
    private String featureEntityType;
    @SerializedName("FEATUREENTITYID")
    private String featureEntityID;
    @SerializedName("MACHTYPE")
    private String machType;
    @SerializedName("MODEL")
    private String model;
    @SerializedName("FEATURECODE")
    private String featureCode;
    @SerializedName("PUBFROMANNDATE")
    private String pubFromAnnDate;
    @SerializedName("SYSTEMMAX")
    private String systemMax;
    @SerializedName("SYSTEMMAX")
    private String fcType;
    @SerializedName("BLKMESINDC")
    private String blkMesIndc;

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

    public String getModelEntityType() {
        return modelEntityType;
    }

    public void setModelEntityType(String modelEntityType) {
        this.modelEntityType = modelEntityType;
    }

    public String getModelEntityID() {
        return modelEntityID;
    }

    public void setModelEntityID(String modelEntityID) {
        this.modelEntityID = modelEntityID;
    }

    public String getFeatureEntityType() {
        return featureEntityType;
    }

    public void setFeatureEntityType(String featureEntityType) {
        this.featureEntityType = featureEntityType;
    }

    public String getFeatureEntityID() {
        return featureEntityID;
    }

    public void setFeatureEntityID(String featureEntityID) {
        this.featureEntityID = featureEntityID;
    }

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

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getPubFromAnnDate() {
        return pubFromAnnDate;
    }

    public void setPubFromAnnDate(String pubFromAnnDate) {
        this.pubFromAnnDate = pubFromAnnDate;
    }

    public String getSystemMax() {
        return systemMax;
    }

    public void setSystemMax(String systemMax) {
        this.systemMax = systemMax;
    }

    public String getFcType() {
        return fcType;
    }

    public void setFcType(String fcType) {
        this.fcType = fcType;
    }

    public String getBlkMesIndc() {
        return blkMesIndc;
    }

    public void setBlkMesIndc(String blkMesIndc) {
        this.blkMesIndc = blkMesIndc;
    }
}
