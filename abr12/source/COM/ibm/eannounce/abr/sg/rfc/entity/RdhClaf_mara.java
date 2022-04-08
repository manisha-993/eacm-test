/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhClaf_mara
{
    @SerializedName("ERSDA")
    private String ersda;

    /**
     * @return the ersda
     */
    public String getErsda()
    {
        return ersda;
    }

    /**
     * @param ersda the ersda to set
     */
    public void setErsda(String ersda)
    {
        this.ersda = ersda;
    }
    
    
}
