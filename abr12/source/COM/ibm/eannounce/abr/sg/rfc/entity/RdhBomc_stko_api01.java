/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhBomc_stko_api01
{
    @SerializedName("BASE_QUAN")
    private String base_quan;
    
    @SerializedName("BOM_STATUS")
    private String bom_status;
    
    /**
     * @return the base_quan
     */
    public String getBase_quan()
    {
        return base_quan;
    }
    /**
     * @param base_quan the base_quan to set
     */
    public void setBase_quan(String base_quan)
    {
        this.base_quan = base_quan;
    }
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
