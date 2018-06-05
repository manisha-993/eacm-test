package com.ibm.rdh.chw.entity;

import java.util.Date;
import java.util.Vector;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class AUOMaterial implements java.io.Serializable {
	public String material;
	public String percentage;
	public String div;
	public String CHWProdHierarchy;
	public String shortName;
	//ACCTASGNGRP
	public String acctAsgnGrp;
	public Vector countryList;
	//AMRTZTLNSTRT
	public String amrtztlnstrt;
	//AMRTZTLNLNGTH
	public String amrtztlnlngth;
	//MATERIALGROUP1
	public String materialGroup1;
	//EFFECTIVEDATE 
	public Date effectiveDate;
	public String rfaNum = null;
	
    public String getRfaNum() {
		return rfaNum;
	}

	public void setRfaNum(String rfaNum) {
		this.rfaNum = rfaNum;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public static final String CHW_PROD_HIERARCHY = "0900B00002";
    public static final String SAP_MATERIAL_GRP = "SWC";
    
    public AUOMaterial() {}
    
	public AUOMaterial(String material, String percentage) {
		this.setMaterial(material);
		this.setPercentage(percentage);
	}

	
	public void setPercentage(String value) {
		this.percentage = value;
	}

	public String getPercentage() {
		return this.percentage;
	}

	public void setMaterial(String value) {
		this.material = value;
	}

	public String getMaterial() {
		return this.material;
	}
	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getCHWProdHierarchy() {
		return CHWProdHierarchy;
	}

	public void setCHWProdHierarchy(String cHWProdHierarchy) {
		CHWProdHierarchy = cHWProdHierarchy;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getAcctAsgnGrp() {
		return acctAsgnGrp;
	}

	public void setAcctAsgnGrp(String acctAsgnGrp) {
		this.acctAsgnGrp = acctAsgnGrp;
	}

	public Vector getCountryList() {
		return countryList;
	}

	public void setCountryList(Vector countryList) {
		this.countryList = countryList;
	}

	public String getAmrtztlnstrt() {
		return amrtztlnstrt;
	}

	public void setAmrtztlnstrt(String amrtztlnstrt) {
		this.amrtztlnstrt = amrtztlnstrt;
	}

	public String getAmrtztlnlngth() {
		return amrtztlnlngth;
	}

	public void setAmrtztlnlngth(String amrtztlnlngth) {
		this.amrtztlnlngth = amrtztlnlngth;
	}

	public String getMaterialGroup1() {
		return materialGroup1;
	}

	public void setMaterialGroup1(String materialGroup1) {
		this.materialGroup1 = materialGroup1;
	}

	public boolean equals(AUOMaterial aUOMaterialTmp) {
		boolean equals = false;
		if ((this.material == aUOMaterialTmp.material)
				&& (this.percentage == aUOMaterialTmp.percentage))
			equals = true;
		return equals;

	}

	@Override
	public String toString() {
		return "AUOMaterial [material=" + material + ", percentage="
				+ percentage + ", div=" + div + ", CHWProdHierarchy="
				+ CHWProdHierarchy + ", shortName=" + shortName
				+ ", acctAsgnGrp=" + acctAsgnGrp + ", countryList="
				+ countryList + ", amrtztlnstrt=" + amrtztlnstrt
				+ ", amrtztlnlngth=" + amrtztlnlngth + ", materialGroup1="
				+ materialGroup1 + ", effectiveDate=" + effectiveDate + "]";
	}
}
