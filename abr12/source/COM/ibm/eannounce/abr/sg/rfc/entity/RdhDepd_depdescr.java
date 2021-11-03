/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhDepd_depdescr
{
    @SerializedName("LANGUAGE")
    private String language;
    @SerializedName("DESCRIPT")
    private String descript;
    /**
     * @return the language
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * @param language the language to set
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }
    /**
     * @return the descript
     */
    public String getDescript()
    {
        return descript;
    }
    /**
     * @param descript the descript to set
     */
    public void setDescript(String descript)
    {
        this.descript = descript;
    }
    
    
}
