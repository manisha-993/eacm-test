/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

/**
 * BMMH5 for MATERIAL CREATION RFC
 * @author will
 *
 */
public class RdhMatm_bmmh5
{
    @SerializedName("SPRAS")
    private String spras;
    @SerializedName("MAKTX")
    private String maktx;
    @SerializedName("TDLINE")
    private String tdline;
    /**
     * @return the spras
     */
    public String getSpras()
    {
        return spras;
    }
    /**
     * @param spras the spras to set
     */
    public void setSpras(String spras)
    {
        this.spras = spras;
    }
    /**
     * @return the maktx
     */
    public String getMaktx()
    {
        return maktx;
    }
    /**
     * @param maktx the maktx to set
     */
    public void setMaktx(String maktx)
    {
        this.maktx = maktx;
    }
    /**
     * @return the tdline
     */
    public String getTdline()
    {
        return tdline;
    }
    /**
     * @param tdline the tdline to set
     */
    public void setTdline(String tdline)
    {
        this.tdline = tdline;
    }
    
    
}
