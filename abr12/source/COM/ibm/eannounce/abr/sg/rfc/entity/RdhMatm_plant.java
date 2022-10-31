/* Copyright IBM Corp. 2020 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

/**
 * PLANT for MATERIAL CREATION RFC
 * @author will
 *
 */
public class RdhMatm_plant
{
    @SerializedName("WERKS")
    private String werks;
    @SerializedName("LGORT")
    private String lgort;
    
    @SerializedName("STAWN")
    private String stawn;
    @SerializedName("HERKL")
    private String herkl;
    @SerializedName("HERKR")
    private String herkr;
    @SerializedName("EKGRP")
    private String ekgrp;
    @SerializedName("DISMM")
    private String dismm;
    @SerializedName("BESKZ")
    private String beskz;
    @SerializedName("MTVFP")
    private String mtvfp;
    @SerializedName("PLIFZ")
    private String plifz;
    @SerializedName("WEBAZ")
    private String webaz;
    @SerializedName("VPRSV")
    private String vprsv;
    @SerializedName("MTVER")
    private String mtver;
    @SerializedName("PRCTR")
    private String prctr;
    @SerializedName("STEUC")
    private String steuc;
    /**
     * @return the lgort
     */
    public String getLgort()
    {
        return lgort;
    }
    /**
     * @param lgort the lgort to set
     */
    public void setLgort(String lgort)
    {
        this.lgort = lgort;
    }
    /**
     * @return the werks
     */
    public String getWerks()
    {
        return werks;
    }
    /**
     * @param werks the werks to set
     */
    public void setWerks(String werks)
    {
        this.werks = werks;
    }
    /**
     * @return the stawn
     */
    public String getStawn()
    {
        return stawn;
    }
    /**
     * @param stawn the stawn to set
     */
    public void setStawn(String stawn)
    {
        this.stawn = stawn;
    }
    /**
     * @return the herkl
     */
    public String getHerkl()
    {
        return herkl;
    }
    /**
     * @param herkl the herkl to set
     */
    public void setHerkl(String herkl)
    {
        this.herkl = herkl;
    }
    /**
     * @return the herkr
     */
    public String getHerkr()
    {
        return herkr;
    }
    /**
     * @param herkr the herkr to set
     */
    public void setHerkr(String herkr)
    {
        this.herkr = herkr;
    }
    /**
     * @return the ekgrp
     */
    public String getEkgrp()
    {
        return ekgrp;
    }
    /**
     * @param ekgrp the ekgrp to set
     */
    public void setEkgrp(String ekgrp)
    {
        this.ekgrp = ekgrp;
    }
    /**
     * @return the dismm
     */
    public String getDismm()
    {
        return dismm;
    }
    /**
     * @param dismm the dismm to set
     */
    public void setDismm(String dismm)
    {
        this.dismm = dismm;
    }
    /**
     * @return the beskz
     */
    public String getBeskz()
    {
        return beskz;
    }
    /**
     * @param beskz the beskz to set
     */
    public void setBeskz(String beskz)
    {
        this.beskz = beskz;
    }
    /**
     * @return the mtvfp
     */
    public String getMtvfp()
    {
        return mtvfp;
    }
    /**
     * @param mtvfp the mtvfp to set
     */
    public void setMtvfp(String mtvfp)
    {
        this.mtvfp = mtvfp;
    }
    /**
     * @return the plifz
     */
    public String getPlifz()
    {
        return plifz;
    }
    /**
     * @param plifz the plifz to set
     */
    public void setPlifz(String plifz)
    {
        this.plifz = plifz;
    }
    /**
     * @return the webaz
     */
    public String getWebaz()
    {
        return webaz;
    }
    /**
     * @param webaz the webaz to set
     */
    public void setWebaz(String webaz)
    {
        this.webaz = webaz;
    }
    /**
     * @return the vprsv
     */
    public String getVprsv()
    {
        return vprsv;
    }
    /**
     * @param vprsv the vprsv to set
     */
    public void setVprsv(String vprsv)
    {
        this.vprsv = vprsv;
    }
    /**
     * @return the mtver
     */
    public String getMtver()
    {
        return mtver;
    }
    /**
     * @param mtver the mtver to set
     */
    public void setMtver(String mtver)
    {
        this.mtver = mtver;
    }
	public String getPrctr() {
		return prctr;
	}
	public void setPrctr(String prctr) {
		this.prctr = prctr;
	}

    public String getSteuc() {
        return steuc;
    }

    public void setSteuc(String steuc) {
        this.steuc = steuc;
    }
}
