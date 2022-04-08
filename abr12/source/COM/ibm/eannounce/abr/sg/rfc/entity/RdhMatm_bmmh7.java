/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhMatm_bmmh7
{
    @SerializedName("TDID")
    private String tdid;
    @SerializedName("TDSPRAS")
    private String tdspras;
    @SerializedName("TDLINE")
    private String tdline;
    /**
     * @return the tdid
     */
    public String getTdid()
    {
        return tdid;
    }
    /**
     * @param tdid the tdid to set
     */
    public void setTdid(String tdid)
    {
        this.tdid = tdid;
    }
    /**
     * @return the tdspras
     */
    public String getTdspras()
    {
        return tdspras;
    }
    /**
     * @param tdspras the tdspras to set
     */
    public void setTdspras(String tdspras)
    {
        this.tdspras = tdspras;
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
