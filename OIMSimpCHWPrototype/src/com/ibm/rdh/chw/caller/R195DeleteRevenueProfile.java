package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.RevData;
import com.ibm.rdh.rfc.Csap_mbomStructure;
import com.ibm.rdh.rfc.Stpo_api03Table;
import com.ibm.rdh.rfc.Stpo_api03TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R195DeleteRevenueProfile extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN rfc;

	/**
	 * @param type
	 *            - used to create the material number
	 * @param annDocNo
	 *            - the RFA Number. If null, revProfileName used to create the
	 *            RFA number.
	 * @param revData
	 *            - Vector of RevData.
	 * @param typeModelRevs
	 *            - not used
	 * @param revProfileName
	 *            - the revenue profile name. Used to create the RFA number when
	 *            used to create a revenue profile only.
	 * @param newFlag
	 *            - used to create the material number
	 */
	public R195DeleteRevenueProfile(String type, String annDocNo,
			Vector revData, Vector typeModelRevs, String revProfileName,
			String newFlag, String pimsIdentity, String _plant)
			throws Exception {
		reInitialize();

		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_BOM_MAINTAIN";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN();

		// Set up the RFC fields
		// CSAP_MBOM - M0
		Csap_mbomStructure m0 = new Csap_mbomStructure();

		if ("NEW".equals(newFlag)) {
			m0.setMatnr(type + "NEW");
		} else if ("UPG".equals(newFlag)) {
			m0.setMatnr(type + "UPG");
		} else if ("MTC".equals(newFlag)) {
			m0.setMatnr(type + "MTC");
		}
		//add 20180330
		else if("MOD".equals(newFlag.sustring(0,3))){
			m0.setMatnr(type + newFlag.sustring(4));
		}
		//add

		m0.setWerks(_plant);
		m0.setStlan("Y");
		m0.setDatuv(sdf.format(curDate));

		rfc.setJCsapMbom(m0);

		rfcInfo.append("CSAP_MBOM \n");
		rfcInfo.append(Tab + "MATNR>>" + m0.getMatnr() 
				+ ", WERKS>>" + m0.getWerks() 
				+ ", STLAN>>" + m0.getStlan()
				+ ", DATUV>>" + m0.getDatuv() + "\n");

		// STPO_API03 - M1
		Enumeration e = revData.elements();
		Stpo_api03Table m1Table = new Stpo_api03Table();

		while (e.hasMoreElements()) {
			RevData revdata = (RevData) e.nextElement();
			Stpo_api03TableRow m1Row = m1Table.createEmptyRow();

			// As per the Design.
			m1Row.setItemCateg("Y");

			m1Row.setItemNo(revdata.getItem_No());
			m1Row.setComponent(revdata.getComponent());
			m1Row.setItemNode(revdata.getItem_Node());
			m1Row.setItemCount(revdata.getItem_Count());

			m1Row.setFldelete("X");
			m1Table.appendRow(m1Row);

			rfcInfo.append("STPO_API03  \n");
			rfcInfo.append(Tab + "ITEM_CATEG>>" + m1Row.getItemCateg()
					+ ", ITEM_NO>>" + m1Row.getItemNo()
					+ ", COMPONENT>>" + m1Row.getComponent() 
					+ ", ITEMNODE>>" + m1Row.getItemNode() 
					+ ", ITEMCOUNT>>" + m1Row.getItemCount()
					+ ", FLDELETE>>" + m1Row.getFldelete() + "\n");
		}

		rfc.setJStpoApi03(m1Table);

		// ZDM_GEO_TO_CLASS
		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");

		zdmTable.appendRow(zdmRow);

		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

		// PIMSIdentity
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
		return "Delete Revenue Profile BOM Components";
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
