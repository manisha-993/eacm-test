package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhYMDMGars_PRODUCTS {
    @SerializedName("PARTNUM")
    private String partnum;
    @SerializedName("LAND1")
    private String land1;

    public void setPartnum(String partnum) {
        this.partnum = partnum;
    }

    public void setLand1(String land1) {
        this.land1 = land1;
    }
}
