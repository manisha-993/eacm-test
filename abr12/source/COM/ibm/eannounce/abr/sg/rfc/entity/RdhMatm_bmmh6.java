/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

/**
 * BMMH6 for MATERIAL CREATION RFC
 * @author will
 * @since 07/28/2021
 */
public class RdhMatm_bmmh6
{
    @SerializedName("MEINH")
    private String meinh;
    @SerializedName("UMREZ")
    private String umrez;
    /**
     * @return the meinh
     */
    public String getMeinh()
    {
        return meinh;
    }
    /**
     * @param meinh the meinh to set
     */
    public void setMeinh(String meinh)
    {
        this.meinh = meinh;
    }
    /**
     * @return the umrez
     */
    public String getUmrez()
    {
        return umrez;
    }
    /**
     * @param umrez the umrez to set
     */
    public void setUmrez(String umrez)
    {
        this.umrez = umrez;
    }
    
    
    
}
