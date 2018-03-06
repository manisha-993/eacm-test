package com.ibm.rdh.chw.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LifecycleData {
	
	protected String varCond;
	protected Date preAnnounceValidFrom;
	protected Date preAnnounceValidTo;
	protected Date announceValidFrom;
	protected Date announceValidTo;
	protected Date wdfmValidFrom;
	protected Date wdfmValidTo;
	private List<LifecycleAnnounceData> announceDataList = new ArrayList<>();

	/**
	 * Constructor for LifecycleData.
	 */
	public LifecycleData() {
		super();
	}
	
	/**
	 * Constructor for LifecycleData.
	 */
	public LifecycleData(String aVarCond) {
		super();
		this.setVarCond(aVarCond);
	}
	

	/**
	 * Returns the announceValidFrom.
	 * @return Date
	 */
	public Date getAnnounceValidFrom() {
		return announceValidFrom;
	}

	/**
	 * Returns the announceValidTo.
	 * @return Date
	 */
	public Date getAnnounceValidTo() {
		return announceValidTo;
	}

	/**
	 * Returns the preAnnounceValidFrom.
	 * @return Date
	 */
	public Date getPreAnnounceValidFrom() {
		return preAnnounceValidFrom;
	}

	/**
	 * Returns the preAnnounceValidTo.
	 * @return Date
	 */
	public Date getPreAnnounceValidTo() {
		return preAnnounceValidTo;
	}

	/**
	 * Returns the wdfmValidFrom.
	 * @return Date
	 */
	public Date getWdfmValidFrom() {
		return wdfmValidFrom;
	}

	/**
	 * Returns the wdfmValidTo.
	 * @return Date
	 */
	public Date getWdfmValidTo() {
		return wdfmValidTo;
	}

	/**
	 * Sets the announceValidFrom.
	 * @param announceValidFrom The announceValidFrom to set
	 */
	public void setAnnounceValidFrom(Date announceValidFrom) {
		this.announceValidFrom = announceValidFrom;
	}

	/**
	 * Sets the announceValidTo.
	 * @param announceValidTo The announceValidTo to set
	 */
	public void setAnnounceValidTo(Date announceValidTo) {
		this.announceValidTo = announceValidTo;
	}

	/**
	 * Sets the preAnnounceValidTo.
	 * @param preAnnounceValidTo The preAnnounceValidTo to set
	 */
	public void setPreAnnounceValidTo(Date preAnnounceValidTo) {
		this.preAnnounceValidTo = preAnnounceValidTo;
	}

	/**

	/**
	 * Sets the wdfmValidFrom.
	 * @param wdfmValidFrom The wdfmValidFrom to set
	 */
	public void setWdfmValidFrom(Date wdfmValidFrom) {
		this.wdfmValidFrom = wdfmValidFrom;
	}

	/**
	 * Sets the wdfmValidTo.
	 * @param wdfmValidTo The wdfmValidTo to set
	 */
	public void setWdfmValidTo(Date wdfmValidTo) {
		this.wdfmValidTo = wdfmValidTo;
	}

	/**
	 * Returns the varCond.
	 * @return String
	 */
	public String getVarCond() {
		return varCond;
	}

	/**
	 * Sets the varCond.
	 * @param varCond The varCond to set
	 */
	public void setVarCond(String varCond) {
		this.varCond = varCond;
	}

	/**
	 * Sets the preAnnounceValidFrom.
	 * @param preAnnounceValidFrom The preAnnounceValidFrom to set
	 */
	public void setPreAnnounceValidFrom(Date preAnnounceValidFrom) {
		this.preAnnounceValidFrom = preAnnounceValidFrom;
	}

	public List<LifecycleAnnounceData> getAnnounceDataList() {
		return announceDataList;
	}

	@Override
	public String toString() {
		return "LifecycleData [varCond=" + varCond + ", preAnnounceValidFrom=" + preAnnounceValidFrom
				+ ", preAnnounceValidTo=" + preAnnounceValidTo + ", announceValidFrom=" + announceValidFrom
				+ ", announceValidTo=" + announceValidTo + ", wdfmValidFrom=" + wdfmValidFrom + ", wdfmValidTo="
				+ wdfmValidTo + "]";
	}
	
}