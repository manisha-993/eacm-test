/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhClas_clclasses
{
    @SerializedName("CLASS")
    private String cLASS;
    @SerializedName("CLASS_TYPE")
    private String class_type;
    @SerializedName("STATUS")
    private String status;
    @SerializedName("VAL_FROM")
    private String val_from;
    @SerializedName("VAL_TO")
    private String val_to;
    @SerializedName("CHECK_NO")
    private String check_no;
    @SerializedName("ORG_AREA")
    private String org_area;

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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getVal_from()
    {
        return val_from;
    }

    public void setVal_from(String val_from)
    {
        this.val_from = val_from;
    }

    public String getVal_to()
    {
        return val_to;
    }

    public void setVal_to(String val_to)
    {
        this.val_to = val_to;
    }

    public String getCheck_no()
    {
        return check_no;
    }

    public void setCheck_no(String check_no)
    {
        this.check_no = check_no;
    }

    public String getOrg_area()
    {
        return org_area;
    }

    public void setOrg_area(String org_area)
    {
        this.org_area = org_area;
    }

}
