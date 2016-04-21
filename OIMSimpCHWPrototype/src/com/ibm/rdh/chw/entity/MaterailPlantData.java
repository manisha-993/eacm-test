package com.ibm.rdh.chw.entity;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class MaterailPlantData {

	String plant = "";
	String profitCenter = "";

	public MaterailPlantData() {
		super();
	}

	public String getPlant() {
		return plant;
	}

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setPlant(String newPlant) {
		plant = newPlant;
	}

	public void setProfitCenter(String newProfitCenter) {
		profitCenter = newProfitCenter;
	}

	public MaterailPlantData(String strPlant, String strProfitCenter) {
		plant = strPlant;
		profitCenter = strProfitCenter;
	}

}