/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhBomm_mbom
{
    @SerializedName("MATNR")
    private String matnr; 
    
    @SerializedName("WERKS")
    private String werks; 
    
    @SerializedName("STLAN")
    private String stlan;
    
    @SerializedName("DATUV")
    private String datuv;

    /**
     * @return the matnr
     */
    public String getMatnr()
    {
        return matnr;
    }

    /**
     * @param matnr the matnr to set
     */
    public void setMatnr(String matnr)
    {
        this.matnr = matnr;
    }

    /**
     * @return the werks
     */
    public String getWerks()
    {
        return werks;
    }

    /**
     * @param werks the werks to set
     */
    public void setWerks(String werks)
    {
        this.werks = werks;
    }

    /**
     * @return the stlan
     */
    public String getStlan()
    {
        return stlan;
    }

    /**
     * @param stlan the stlan to set
     */
    public void setStlan(String stlan)
    {
        this.stlan = stlan;
    }

    /**
     * @return the datuv
     */
    public String getDatuv()
    {
        return datuv;
    }

    /**
     * @param datuv the datuv to set
     */
    public void setDatuv(String datuv)
    {
        this.datuv = datuv;
    }
    
    
}
