/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhClaa_rmclm
{
    @SerializedName("MERKMA")
    private String merkma;
    @SerializedName("ABTEI")
    private String abtei;
    /**
     * @return the merkma
     */
    public String getMerkma()
    {
        return merkma;
    }
    /**
     * @param merkma the merkma to set
     */
    public void setMerkma(String merkma)
    {
        this.merkma = merkma;
    }
    /**
     * @return the abtei
     */
    public String getAbtei()
    {
        return abtei;
    }
    /**
     * @param abtei the abtei to set
     */
    public void setAbtei(String abtei)
    {
        this.abtei = abtei;
    }
    
    
}
