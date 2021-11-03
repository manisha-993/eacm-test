/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhBomm_stko_api01
{
    @SerializedName("BOM_STATUS")
    private String bom_status;

    /**
     * @return the bom_status
     */
    public String getBom_status()
    {
        return bom_status;
    }

    /**
     * @param bom_status the bom_status to set
     */
    public void setBom_status(String bom_status)
    {
        this.bom_status = bom_status;
    }
    
    
}
