/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhClaf_kssk
{
    @SerializedName("KLART")
    private String klart;

    /**
     * @return the klart
     */
    public String getKlart()
    {
        return klart;
    }

    /**
     * @param klart the klart to set
     */
    public void setKlart(String klart)
    {
        this.klart = klart;
    }
    
    
}
