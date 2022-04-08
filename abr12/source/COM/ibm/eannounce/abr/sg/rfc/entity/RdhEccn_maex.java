package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhEccn_maex
{
    @SerializedName("ALAND")
    private String aland;
    @SerializedName("ALNUM")
    private String alnum;
    @SerializedName("EMBGR")
    private String embgr;
    @SerializedName("GEGRU")
    private String gegru;
    @SerializedName("PMAST")
    private String pmast;
    
    /**
     * @return the aland
     */
    public String getAland()
    {
        return aland;
    }
    /**
     * @param aland the aland to set
     */
    public void setAland(String aland)
    {
        this.aland = aland;
    }
    /**
     * @return the alnum
     */
    public String getAlnum()
    {
        return alnum;
    }
    /**
     * @param alnum the alnum to set
     */
    public void setAlnum(String alnum)
    {
        this.alnum = alnum;
    }
    /**
     * @return the embgr
     */
    public String getEmbgr()
    {
        return embgr;
    }
    /**
     * @param embgr the embgr to set
     */
    public void setEmbgr(String embgr)
    {
        this.embgr = embgr;
    }
    /**
     * @return the gegru
     */
    public String getGegru()
    {
        return gegru;
    }
    /**
     * @param gegru the gegru to set
     */
    public void setGegru(String gegru)
    {
        this.gegru = gegru;
    }
    /**
     * @return the pmast
     */
    public String getPmast()
    {
        return pmast;
    }
    /**
     * @param pmast the pmast to set
     */
    public void setPmast(String pmast)
    {
        this.pmast = pmast;
    }
    
    
}
