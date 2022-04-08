/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhBomm_csdata
{
    @SerializedName("CHAR1")
    private String char1;

    /**
     * @return the char1
     */
    public String getChar1()
    {
        return char1;
    }

    /**
     * @param char1 the char1 to set
     */
    public void setChar1(String char1)
    {
        this.char1 = char1;
    }
    
    
}
