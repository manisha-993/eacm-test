/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

/**
 * the availability status for an offer
 * @author will
 *
 */
public class RdhCswOrdAvailStatus_model
{
    @SerializedName("MACHTYPE")
    private String machType;
    @SerializedName("MODEL")
    private String model;
    @SerializedName("COUNTRY_FC")
    private String country_fc;
    @SerializedName("ANNDATE")
    private String annDate;
    @SerializedName("FIRSTORDER")
    private String firstOrder;
    @SerializedName("PLANNEDAVAILABILITY")
    private String plannedAvailability;
    @SerializedName("WDANNDATE")
    private String wdAnnDate;
    @SerializedName("LASTORDER")
    private String lastOrder;
    @SerializedName("EOSANNDATE")
    private String eosAnnDate;
    @SerializedName("ENDOFSERVICEDATE")
    private String endOfServiceDate;
    @SerializedName("PUBFROM")
    private String pubFrom;
    @SerializedName("PUBTO")
    private String pubTo;
    
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
     * @return the eosAnnDate
     */
    public String getEosAnnDate()
    {
        return eosAnnDate;
    }
    /**
     * @param eosAnnDate the eosAnnDate to set
     */
    public void setEosAnnDate(String eosAnnDate)
    {
        this.eosAnnDate = eosAnnDate;
    }
    /**
     * @return the endOfServiceDate
     */
    public String getEndOfServiceDate()
    {
        return endOfServiceDate;
    }
    /**
     * @param endOfServiceDate the endOfServiceDate to set
     */
    public void setEndOfServiceDate(String endOfServiceDate)
    {
        this.endOfServiceDate = endOfServiceDate;
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
    
    
}
