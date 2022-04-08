/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhClaf_object_key
{
    @SerializedName("KEY_FELD")
    private String  key_feld;   
    @SerializedName("KPARA_VALU")
    private String  kpara_valu;
    /**
     * @return the key_feld
     */
    public String getKey_feld()
    {
        return key_feld;
    }
    /**
     * @param key_feld the key_feld to set
     */
    public void setKey_feld(String key_feld)
    {
        this.key_feld = key_feld;
    }
    /**
     * @return the kpara_valu
     */
    public String getKpara_valu()
    {
        return kpara_valu;
    }
    /**
     * @param kpara_valu the kpara_valu to set
     */
    public void setKpara_valu(String kpara_valu)
    {
        this.kpara_valu = kpara_valu;
    }
    
    
}
