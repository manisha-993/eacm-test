/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhDepa_cha_gldep
{
    @SerializedName("CHARACT")
    private String charact;
    @SerializedName("DEPENDENCY")
    private String dependency;
    
    /**
     * @return the charact
     */
    public String getCharact()
    {
        return charact;
    }
    /**
     * @param charact the charact to set
     */
    public void setCharact(String charact)
    {
        this.charact = charact;
    }
    /**
     * @return the dependency
     */
    public String getDependency()
    {
        return dependency;
    }
    /**
     * @param dependency the dependency to set
     */
    public void setDependency(String dependency)
    {
        this.dependency = dependency;
    }
    
    
}
