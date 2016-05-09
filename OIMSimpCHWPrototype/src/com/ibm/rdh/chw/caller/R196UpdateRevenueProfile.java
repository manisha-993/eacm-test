package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.AUOMaterial;
import com.ibm.rdh.rfc.Csap_mbomStructure;
import com.ibm.rdh.rfc.Stpo_api03Table;
import com.ibm.rdh.rfc.Stpo_api03TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R196UpdateRevenueProfile extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN rfc;
	protected int r196ItemNumberCounter = 1;

	/**
	 * @param type
	 *            - used to create the material number
	 * @param annDocNo
	 *            - the RFA Number. If null, revProfileName used to create the
	 *            RFA number.
	 * @param auoMaterials
	 *            - Vector of AUOMaterials.
	 * @param typeModelRevs
	 *            - not used
	 * @param revProfileName
	 *            - the revenue profile name. Used to create the RFA number when
	 *            used to create a revenue profile only.
	 * @param newFlag
	 *            - used to create the material number
	 */
	public R196UpdateRevenueProfile(String type, String annDocNo,
			Vector auoMaterials, Vector TypeModelRevs, String revProfileName,
			String newFlag, String pimsIdentity, String _plant)
			throws Exception {
		reInitialize();

		setR196ItemNumberCounter(1);

		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_BOM_MAINTAIN";
		String itemNumber;

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN();

		// Set up the RFC fields
		// CSAP_MBOM - M0
		Csap_mbomStructure m0 = new Csap_mbomStructure();

		// Changes Made by Laxmi

		if (newFlag.equals("NEW")) {
			m0.setMatnr(type + "NEW");
		} else if (newFlag.equals("UPG")) {
			m0.setMatnr(type + "UPG");
		} else if (newFlag.equals("MTC")) {
			m0.setMatnr(type + "MTC");
		}

		// m0.setWerks("1222");
		m0.setWerks(_plant);
		m0.setStlan("Y");
		m0.setDatuv(sdf.format(curDate));

		rfc.setJCsapMbom(m0);

		rfcInfo.append("CSAP_MBOM \n");
		rfcInfo.append(Tab + "MATNR>>" + m0.getMatnr() + ", WERKS>>"
				+ m0.getWerks() + ", STLAN>>" + m0.getStlan() + ", DATUV>>"
				+ m0.getDatuv() + "\n");

		// STPO_API03 - M1
		Enumeration e = auoMaterials.elements();
		Stpo_api03Table m1Table = new Stpo_api03Table();
		// int Cnt = 1;
		while (e.hasMoreElements()) {
			AUOMaterial auomat = (AUOMaterial) e.nextElement();
			Stpo_api03TableRow m1Row = m1Table.createEmptyRow();

			m1Row.setItemCateg("Y");

			itemNumber = generateItemNumberString("r196");

			m1Row.setItemNo(itemNumber);
			m1Row.setComponent(auomat.getMaterial());
			System.out.println("R196 Component >>>>>>" + auomat.getMaterial());
			m1Row.setCompQty("1");
			m1Row.setCompUnit("EA");
			m1Row.setRelSales("X");
			System.out.println("R196 Percentage >>>>>>"
					+ auomat.getPercentage());
			m1Row.setSortstring(auomat.getPercentage());
			// Made to ask Greg in Design Document in page 97 it says IDENTIFIER
			// "A1"
			// m1Row.setIdentifier("A" + Cnt);
			// Cnt++;

			m1Table.appendRow(m1Row);

			rfcInfo.append("STPO_API03  \n");
			rfcInfo.append(Tab + "ITEM_CATEG>>" + m1Row.getItemCateg()
					+ ", ITEM_NO>>" + m1Row.getItemNo() + ", COMPONENT>>"
					+ m1Row.getComponent() + ", COMP_QTY>>"
					+ m1Row.getCompQty() + ", COMP_UNIT>>"
					+ m1Row.getCompUnit() + ", REL_SALES>>"
					+ m1Row.getRelSales() + ", SORTSTRING>>"
					+ m1Row.getSortstring() + ", IDENTIFIER>>"
					+ m1Row.getIdentifier() + "\n");
		}

		rfc.setJStpoApi03(m1Table);

		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");

		zdmTable.appendRow(zdmRow);

		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + rfc.getPimsIdentity() + "\n");

		// RFANUMBER
		if (annDocNo != null) {
			rfc.setRfaNum(annDocNo);
		} else {
			rfc.setRfaNum(revProfileName + "_REV");
		}
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + rfc.getRfaNum() + "\n");

	}

	public void setR196ItemNumberCounter(int newR196ItemNumberCounter) {
		r196ItemNumberCounter = newR196ItemNumberCounter;
	}

	public int getR196ItemNumberCounter() {
		return r196ItemNumberCounter;
	}
	
	public String generateItemNumberString(String rfcString) {
		String outString = "0010";
		return outString;
	}

	@Override
	public void execute() throws Exception {
		logExecution();
		getRfc().execute();
		getLog().debug(getErrorInformation());
		if (getSeverity() == ERROR) {
			throw new HWPIMSAbnormalException(getErrorInformation());
		}
	}

	@Override
	public String getTaskDescription() {
		StringBuffer sb = new StringBuffer();
		sb.append(" " + getMaterialName());
		return sb.toString();
	}

	@Override
	protected boolean isSuccessful() {
		boolean ans = false;
		int rc = getRfc().getRfcrc();
		if (0 == rc) {
			ans = true;
		}
		return ans;
	}

	@Override
	protected String getErrorInformation() {
		String ans;
		if (isSuccessful()) {
			ans = RFC_OK_MESSAGE;
		} else {
			ans = formatRfcErrorMessage(getRfc().getRfcrc(), getRfc()
					.getErrorText());
		}
		return ans;
	}

	@Override
	protected String getMaterialName() {
		return "Update Revenue Profile BOM";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}
}
