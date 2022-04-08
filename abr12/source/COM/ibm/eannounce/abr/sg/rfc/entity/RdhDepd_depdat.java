/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhDepd_depdat
{
    @SerializedName("DEP_TYPE")
    private String dep_type;
    @SerializedName("STATUS")
    private String status;
    /**
     * @return the dep_type
     */
    public String getDep_type()
    {
        return dep_type;
    }
    /**
     * @param dep_type the dep_type to set
     */
    public void setDep_type(String dep_type)
    {
        this.dep_type = dep_type;
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
    
    
}
