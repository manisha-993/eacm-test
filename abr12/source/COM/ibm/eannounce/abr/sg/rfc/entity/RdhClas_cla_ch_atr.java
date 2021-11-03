/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhClas_cla_ch_atr
{
    @SerializedName("CLASS")
    private String cLASS;
    @SerializedName("CLASS_TYPE")
    private String class_type;
    @SerializedName("CHARACT")
    private String charact;

    public String get_class()
    {
        return cLASS;
    }

    public void set_class(String _class)
    {
        this.cLASS = _class;
    }

    public String getClass_type()
    {
        return class_type;
    }

    public void setClass_type(String class_type)
    {
        this.class_type = class_type;
    }

    public String getCharact()
    {
        return charact;
    }

    public void setCharact(String charact)
    {
        this.charact = charact;
    }

}
