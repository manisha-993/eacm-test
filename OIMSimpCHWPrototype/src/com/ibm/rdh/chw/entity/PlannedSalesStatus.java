package com.ibm.rdh.chw.entity;

import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;

public class PlannedSalesStatus implements java.io.Serializable {
	private String MATNR = null;

	private String ZDMCSTATUS = null;

	private Date ZDMCDAT = null;

	private String ZDMPSTATUS = null;

	private Date ZDMFRDAT = null;

	private String SALESORG = null;

	private String currentSaleStatus = null;

	private Date currentEffectiveDate = null;

	private String plannedSalesStatus = null;

	private String origSalesStatus = null;

	private String origPlannedSalesStatus = null;

	private Date plannedEffectiveDate = null;

	private Date origPlannedEffectiveDate = null;

	private String plannedSalesStatusOld = null;

	private String origPlannedSalesStatusOld = null;

	private String origSalesStatusOld = null;

	private String origCurrentSalesStatus = null;

	public PlannedSalesStatus() {
		super();
	}

	public PlannedSalesStatus(String type, String model, Date annDate,
			boolean CustomSEO) throws HWPIMSAbnormalException {

	}

	/*
	 * public PlannedSalesStatus(String material,Date annDate,Date
	 * wdfmDate,boolean isCSEO) {//GX1 code drop A
	 * 
	 * }
	 */

	public java.lang.String getMatnr() {
		return MATNR;
	}

	public java.lang.String getCurrentSalesStatus() {
		return currentSaleStatus;
	}

	public java.util.Date getCurrentEffectiveDate() {
		return currentEffectiveDate;
	}

	public java.lang.String getPlannedChangeSalesStatus() {
		return plannedSalesStatus;
	}

	public java.lang.String getOrigSalesStatus() {
		return origSalesStatus;
	}

	public java.lang.String getOrigPlannedSalesStatus() {
		return origPlannedSalesStatus;
	}

	public Date getPlannedEffectiveDate() {
		return plannedEffectiveDate;
	}

	public Date getOrigPlannedEffectiveDate() {
		return origPlannedEffectiveDate;
	}

	public java.lang.String getVkorg() {
		return SALESORG;
	}

	public java.lang.String getPlannedChangeSalesStatusOld() {
		return plannedSalesStatusOld;
	}

	public java.lang.String getOrigPlannedSalesStatusOld() {
		return origPlannedSalesStatusOld;
	}

	public java.lang.String getOrigSalesStatusOld() {
		return origSalesStatusOld;
	}

	public void setMatnr(String mtnr) {
		MATNR = mtnr;
	}

	public void setCurrentSalesStatus(String status) {
		currentSaleStatus = status;
	}

	public void setCurrentEffectiveDate(Date zdmcdat) {
		currentEffectiveDate = zdmcdat;
	}

	public void setPlannedChangeSalesStatus(String zdmpstatus) {
		plannedSalesStatus = zdmpstatus;
	}

	public void setOriginalCurrentSalesStatus(String currentSalesStatus) {
		this.origCurrentSalesStatus = currentSalesStatus;
	}

	public void setOrigSalesStatus(String origstatus) {
		origSalesStatus = origstatus;
	}

	public void setOrigPlannedSalesStatus(String origplanned) {
		origPlannedSalesStatus = origplanned;
	}

	public void setPlannedEffectiveDate(Date zdmfrdat) {
		plannedEffectiveDate = zdmfrdat;
	}

	public void setOrigPlannedEffectiveDate(Date origDate) {
		origPlannedEffectiveDate = origDate;
	}

	public void setVkorg(String vkorg) {
		SALESORG = vkorg;
	}

	public void setPlannedChangeSalesStatusOld(String zdmpstatus) {
		plannedSalesStatusOld = zdmpstatus;
	}

	public void setOrigPlannedSalesStatusOld(String origplanned) {
		origPlannedSalesStatusOld = origplanned;
	}

	public void setOrigSalesStatusOld(String origstatus) {
		origSalesStatusOld = origstatus;
	}

	public void setPlannedSalesStatus(String wdfmStatus) {// GX1
		this.plannedSalesStatus = wdfmStatus;
	}

}
