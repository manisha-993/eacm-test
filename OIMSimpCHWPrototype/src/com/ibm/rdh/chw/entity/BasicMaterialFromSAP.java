package com.ibm.rdh.chw.entity;

public class BasicMaterialFromSAP {
	private String matlType;
	private String prodHier;

	public BasicMaterialFromSAP() {
		super();
	}

	public void setMatlType(String matlType) {
		this.matlType = matlType;
	}

	public void setProdHier(String prodHier) {
		this.prodHier = prodHier;
	}

	public String getMatlType() {
		return matlType;
	}

	public String getProdHier() {
		return prodHier;
	}
}
