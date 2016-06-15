package com.ibm.rdh.chw.entity;

import java.util.Vector;

public class RevProfile {

	private String revenueProfile;
	private Vector auoMaterials = new Vector();

	public String getRevenueProfile() {
		return revenueProfile;
	}

	public void setRevenueProfile(String revenueProfile) {
		this.revenueProfile = revenueProfile;
	}

	public Vector getAuoMaterials() {
		return auoMaterials;
	}

	public void setAuoMaterials(Vector auoMaterials) {
		this.auoMaterials = auoMaterials;
	}

	@Override
	public String toString() {
		return "RevProfile [revenueProfile=" + revenueProfile
				+ ", auoMaterials=" + auoMaterials + "]";
	}

}
