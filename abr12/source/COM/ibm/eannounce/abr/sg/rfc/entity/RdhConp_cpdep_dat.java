/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhConp_cpdep_dat
{
    @SerializedName("C_PROFILE")
    private String  c_profile;
    @SerializedName("DEP_INTERN")
    private String dep_intern;
    @SerializedName("STATUS")
    private String status;
    @SerializedName("FLDELETE")
    private String fldelete;
    /**
     * @return the c_profile
     */
    public String getC_profile()
    {
        return c_profile;
    }
    /**
     * @param c_profile the c_profile to set
     */
    public void setC_profile(String c_profile)
    {
        this.c_profile = c_profile;
    }
    /**
     * @return the dep_intern
     */
    public String getDep_intern()
    {
        return dep_intern;
    }
    /**
     * @param dep_intern the dep_intern to set
     */
    public void setDep_intern(String dep_intern)
    {
        this.dep_intern = dep_intern;
    }
    /**
     * @return the status
     */
    public String getStatus()
    {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
    /**
     * @return the fldelete
     */
    public String getFldelete()
    {
        return fldelete;
    }
    /**
     * @param fldelete the fldelete to set
     */
    public void setFldelete(String fldelete)
    {
        this.fldelete = fldelete;
    }
    
    
}
