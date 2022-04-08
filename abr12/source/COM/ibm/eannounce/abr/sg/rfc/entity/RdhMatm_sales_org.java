/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

/**
 * sales_org for MATERIAL CREATION RFC
 * @author will
 *
 */
public class RdhMatm_sales_org
{
    @SerializedName("VKORG")
    private String vkorg;
    @SerializedName("DWERK")
    private String dwerk; 
    @SerializedName("ZTAXCLSF")
    private String ztaxclsf;
    
    @SerializedName("PROVG")
    private String provg;
    @SerializedName("ZZTAXID")
    private String zztaxid;
    @SerializedName("ZSABRTAX")
    private String zsabrtax;
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
     * @return the dwerk
     */
    public String getDwerk()
    {
        return dwerk;
    }
    /**
     * @param dwerk the dwerk to set
     */
    public void setDwerk(String dwerk)
    {
        this.dwerk = dwerk;
    }
    /**
     * @return the ztaxclsf
     */
    public String getZtaxclsf()
    {
        return ztaxclsf;
    }
    /**
     * @param ztaxclsf the ztaxclsf to set
     */
    public void setZtaxclsf(String ztaxclsf)
    {
        this.ztaxclsf = ztaxclsf;
    }
    /**
     * @return the provg
     */
    public String getProvg()
    {
        return provg;
    }
    /**
     * @param provg the provg to set
     */
    public void setProvg(String provg)
    {
        this.provg = provg;
    }
    /**
     * @return the zztaxid
     */
    public String getZztaxid()
    {
        return zztaxid;
    }
    /**
     * @param zztaxid the zztaxid to set
     */
    public void setZztaxid(String zztaxid)
    {
        this.zztaxid = zztaxid;
    }
	public String getZsabrtax() {
		return zsabrtax;
	}
	public void setZsabrtax(String zsabrtax) {
		this.zsabrtax = zsabrtax;
	}
    
}
