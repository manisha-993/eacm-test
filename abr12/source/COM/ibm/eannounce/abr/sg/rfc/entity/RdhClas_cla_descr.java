/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhClas_cla_descr
{
    @SerializedName("CLASS")
    private String cLASS;
    @SerializedName("CLASS_TYPE")
    private String class_type;
    @SerializedName("LANGUAGE")
    private String language;
    @SerializedName("CATCHWORD")
    private String catchword;

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

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getCatchword()
    {
        return catchword;
    }

    public void setCatchword(String catchword)
    {
        this.catchword = catchword;
    }

}
