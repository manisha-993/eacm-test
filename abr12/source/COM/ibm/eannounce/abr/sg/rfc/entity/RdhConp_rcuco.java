/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhConp_rcuco
{
    @SerializedName("OBTAB")
    private String obtab;

    /**
     * @return the obtab
     */
    public String getObtab()
    {
        return obtab;
    }

    /**
     * @param obtab the obtab to set
     */
    public void setObtab(String obtab)
    {
        this.obtab = obtab;
    }

    
}
