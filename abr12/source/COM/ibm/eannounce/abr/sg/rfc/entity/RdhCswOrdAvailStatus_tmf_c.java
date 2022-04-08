/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

/**
 * the availability status for an orderable
 * @author will
 *
 */
public class RdhCswOrdAvailStatus_tmf_c
{
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
    @SerializedName("SYSTEMMAX")
    private String systemMax;
    
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
     * @return the country_fc
     */
    public String getCountry_fc()
    {
        return country_fc;
    }
    /**
     * @param country_fc the country_fc to set
     */
    public void setCountry_fc(String country_fc)
    {
        this.country_fc = country_fc;
    }
    /**
     * @return the annDate
     */
    public String getAnnDate()
    {
        return annDate;
    }
    /**
     * @param annDate the annDate to set
     */
    public void setAnnDate(String annDate)
    {
        this.annDate = annDate;
    }
    /**
     * @return the firstOrder
     */
    public String getFirstOrder()
    {
        return firstOrder;
    }
    /**
     * @param firstOrder the firstOrder to set
     */
    public void setFirstOrder(String firstOrder)
    {
        this.firstOrder = firstOrder;
    }
    /**
     * @return the plannedAvailability
     */
    public String getPlannedAvailability()
    {
        return plannedAvailability;
    }
    /**
     * @param plannedAvailability the plannedAvailability to set
     */
    public void setPlannedAvailability(String plannedAvailability)
    {
        this.plannedAvailability = plannedAvailability;
    }
    /**
     * @return the pubFrom
     */
    public String getPubFrom()
    {
        return pubFrom;
    }
    /**
     * @param pubFrom the pubFrom to set
     */
    public void setPubFrom(String pubFrom)
    {
        this.pubFrom = pubFrom;
    }
    /**
     * @return the pubTo
     */
    public String getPubTo()
    {
        return pubTo;
    }
    /**
     * @param pubTo the pubTo to set
     */
    public void setPubTo(String pubTo)
    {
        this.pubTo = pubTo;
    }
    /**
     * @return the wdAnnDate
     */
    public String getWdAnnDate()
    {
        return wdAnnDate;
    }
    /**
     * @param wdAnnDate the wdAnnDate to set
     */
    public void setWdAnnDate(String wdAnnDate)
    {
        this.wdAnnDate = wdAnnDate;
    }
    /**
     * @return the lastOrder
     */
    public String getLastOrder()
    {
        return lastOrder;
    }
    /**
     * @param lastOrder the lastOrder to set
     */
    public void setLastOrder(String lastOrder)
    {
        this.lastOrder = lastOrder;
    }
    /**
     * @return the systemMax
     */
    public String getSystemMax()
    {
        return systemMax;
    }
    /**
     * @param systemMax the systemMax to set
     */
    public void setSystemMax(String systemMax)
    {
        this.systemMax = systemMax;
    }
    
}
