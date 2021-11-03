/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;


import com.google.gson.annotations.SerializedName;

/**
 * AssortmentModule
 * 
 * @author jilichao@cn.ibm.com
 *
 */
public class RdhPrdl_asmod
{
    @SerializedName("SPOSI")
    private String sposi;
    @SerializedName("DATUV")
    private String datuv;
    @SerializedName("DATUB")
    private String datub;
    @SerializedName("LVORM")
    private String lvorm;
    
    public String getSposi()
    {
        return sposi;
    }
    public void setSposi(String sposi)
    {
        this.sposi = sposi;
    }
    public String getDatuv()
    {
        return datuv;
    }
    public void setDatuv(String datuv)
    {
        this.datuv = datuv;
    }
    public String getDatub()
    {
        return datub;
    }
    public void setDatub(String datub)
    {
        this.datub = datub;
    }
    public String getLvorm()
    {
        return lvorm;
    }
    public void setLvorm(String lvorm)
    {
        this.lvorm = lvorm;
    }
}
