/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * an orderable's long description plus other attributes
 * @author will
 *
 */
public class RdhCswOrdAvailStatus_feature
{
    @SerializedName("MACHTYPE")
    private String machType;
    @SerializedName("MODEL")
    private String model;
    @SerializedName("FEATURECODE")
    private String featureCode;
    @SerializedName("FEATUREENTITYTYPE")
    private String featureEntityType;
    @SerializedName("MKTGDESC")
    private String mktgDesc;
    @SerializedName("FCTYPE")
    private String fcType;
    @SerializedName("FCSUBCAT")
    private String fcSubCat;
    @SerializedName("PRICEDFEATURE")
    private String pricedFeature;
    @SerializedName("CHARGEOPTION")
    private String chargeOption;
    
    @SerializedName("LCNSOPTTYPE")
    private String lcnsOptType;
    
    @SerializedName("FCCAT")
    private String fcCat;
    
    /**
     * @return the machType
     */
    public String getMachType()
    {
        return machType;
    }
    /**
     * @param machType the machType to set
     */
    public void setMachType(String machType)
    {
        this.machType = machType;
    }
    /**
     * @return the model
     */
    public String getModel()
    {
        return model;
    }
    /**
     * @param model the model to set
     */
    public void setModel(String model)
    {
        this.model = model;
    }
    /**
     * @return the featureCode
     */
    public String getFeatureCode()
    {
        return featureCode;
    }
    /**
     * @param featureCode the featureCode to set
     */
    public void setFeatureCode(String featureCode)
    {
        this.featureCode = featureCode;
    }
    /**
     * @return the featureEntityType
     */
    public String getFeatureEntityType()
    {
        return featureEntityType;
    }
    /**
     * @param featureEntityType the featureEntityType to set
     */
    public void setFeatureEntityType(String featureEntityType)
    {
        this.featureEntityType = featureEntityType;
    }
    /**
     * @return the mktgDesc
     */
    public String getMktgDesc()
    {
        return mktgDesc;
    }
    /**
     * @param mktgDesc the mktgDesc to set
     */
    public void setMktgDesc(String mktgDesc)
    {
        this.mktgDesc = mktgDesc;
    }
    /**
     * @return the fcType
     */
    public String getFcType()
    {
        return fcType;
    }
    /**
     * @param fcType the fcType to set
     */
    public void setFcType(String fcType)
    {
        this.fcType = fcType;
    }
    /**
     * @return the fcSubCat
     */
    public String getFcSubCat()
    {
        return fcSubCat;
    }
    /**
     * @param fcSubCat the fcSubCat to set
     */
    public void setFcSubCat(String fcSubCat)
    {
        this.fcSubCat = fcSubCat;
    }
    /**
     * @return the pricedFeature
     */
    public String getPricedFeature()
    {
        return pricedFeature;
    }
    /**
     * @param pricedFeature the pricedFeature to set
     */
    public void setPricedFeature(String pricedFeature)
    {
        this.pricedFeature = pricedFeature;
    }
    /**
     * @return the chargeOption
     */
    public String getChargeOption()
    {
        return chargeOption;
    }
    /**
     * @param chargeOption the chargeOption to set
     */
    public void setChargeOption(String chargeOption)
    {
        this.chargeOption = chargeOption;
    }
    /**
     * @return the lcnsOptType
     */
    public String getLcnsOptType()
    {
        return lcnsOptType;
    }
    /**
     * @param lcnsOptType the lcnsOptType to set
     */
    public void setLcnsOptType(String lcnsOptType)
    {
        this.lcnsOptType = lcnsOptType;
    }
    /**
     * @return the fcCat
     */
    public String getFcCat()
    {
        return fcCat;
    }
    /**
     * @param fcCat the fcCat to set
     */
    public void setFcCat(String fcCat)
    {
        this.fcCat = fcCat;
    }
    
    
}
