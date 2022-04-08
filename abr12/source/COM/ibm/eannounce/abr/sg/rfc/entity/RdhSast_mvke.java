/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhSast_mvke
{
    @SerializedName("VKORG")
    private String vkorg;
    @SerializedName("VTWEG")
    private String vtweg;
    @SerializedName("VMSTA")
    private String vmsta;
    @SerializedName("VMSTD")
    private String vmstd;
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
     * @return the vmsta
     */
    public String getVmsta()
    {
        return vmsta;
    }
    /**
     * @param vmsta the vmsta to set
     */
    public void setVmsta(String vmsta)
    {
        this.vmsta = vmsta;
    }
    /**
     * @return the vmstd
     */
    public String getVmstd()
    {
        return vmstd;
    }
    /**
     * @param vmstd the vmstd to set
     */
    public void setVmstd(String vmstd)
    {
        this.vmstd = vmstd;
    }
    
    
}
