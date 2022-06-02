/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhBomm_csdep_dat
{
    @SerializedName("OBJECT_ID")
    private String  object_id;
    
    @SerializedName("IDENTIFIER")
    private String  identifier;
    
    @SerializedName("DEP_INTERN")
    private String  dep_intern;
    
    @SerializedName("STATUS")
    private String  status;
    
    /**
     * @return the object_id
     */
    public String getObject_id()
    {
        return object_id;
    }
    /**
     * @param object_id the object_id to set
     */
    public void setObject_id(String object_id)
    {
        this.object_id = object_id;
    }
    /**
     * @return the identifier
     */
    public String getIdentifier()
    {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
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
    
    
}
