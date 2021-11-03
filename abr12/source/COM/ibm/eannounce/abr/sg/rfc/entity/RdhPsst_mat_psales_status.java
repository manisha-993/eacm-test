/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

/**
 * entity class for planned sales status
 * @author will
 *
 */
public class RdhPsst_mat_psales_status
{
    @SerializedName("MATNR")
    private String matnr;
    @SerializedName("VKORG")
    private String vkorg;
    @SerializedName("VTWEG")
    private String vtweg;
    @SerializedName("ZDM_PSTATUS")
    private String zdm_pstatus;
    @SerializedName("ZDM_FRDAT")
    private String zdm_frdat;
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
     * @return the vkorg
     */
    public String getVkorg()
    {
        return vkorg;
    }
    /**
     * @param vkorg the vkorg to set
     */
    public void setVkorg(String vkorg)
    {
        this.vkorg = vkorg;
    }
    /**
     * @return the vtweg
     */
    public String getVtweg()
    {
        return vtweg;
    }
    /**
     * @param vtweg the vtweg to set
     */
    public void setVtweg(String vtweg)
    {
        this.vtweg = vtweg;
    }
    /**
     * @return the zdm_pstatus
     */
    public String getZdm_pstatus()
    {
        return zdm_pstatus;
    }
    /**
     * @param zdm_pstatus the zdm_pstatus to set
     */
    public void setZdm_pstatus(String zdm_pstatus)
    {
        this.zdm_pstatus = zdm_pstatus;
    }
    /**
     * @return the zdm_frdat
     */
    public String getZdm_frdat()
    {
        return zdm_frdat;
    }
    /**
     * @param zdm_frdat the zdm_frdat to set
     */
    public void setZdm_frdat(String zdm_frdat)
    {
        this.zdm_frdat = zdm_frdat;
    }
    
    
}
