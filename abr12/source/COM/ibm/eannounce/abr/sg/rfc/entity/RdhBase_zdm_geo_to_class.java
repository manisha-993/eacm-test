/* Copyright IBM Corp. 2015 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

/**
 * GEO entity
 * 
 * @author will
 * 
 */
public class RdhBase_zdm_geo_to_class
{
    @SerializedName("Z_GEO")
    private String z_geo;
    @SerializedName("Z_CLASS")
    private String z_class;

    /**
     * @param z_geo
     */
    public RdhBase_zdm_geo_to_class(String z_geo)
    {
        super();
        this.z_geo = z_geo;
    }

    public String getZ_geo()
    {
        return z_geo;
    }

    public void setZ_geo(String z_geo)
    {
        this.z_geo = z_geo;
    }

    /**
     * @return the z_class
     */
    public String getZ_class()
    {
        return z_class;
    }

    /**
     * @param z_class the z_class to set
     */
    public void setZ_class(String z_class)
    {
        this.z_class = z_class;
    }
    
    
}
