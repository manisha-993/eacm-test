package com.ibm.rdh.chw.caller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.DepData;
import com.ibm.rdh.rfc.Csap_mbomStructure;
import com.ibm.rdh.rfc.Stpo_api03Table;
import com.ibm.rdh.rfc.Stpo_api03TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R212DeleteSalesBOMfortypeMTC extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN rfc;
	public R212DeleteSalesBOMfortypeMTC(String type, String sapPlant,
			Vector componentstoDeleteTypeMTC, String newFlag,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		reInitialize();
		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_BOM_MAINTAIN";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN();
		System.out.println("Matches Size ===="
				+ componentstoDeleteTypeMTC.size());

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

		m0.setWerks(sapPlant);

		m0.setStlan("5");
		m0.setDatuv(sdf.format(curDate));

		rfc.setJCsapMbom(m0);

		rfcInfo.append("CSAP_MBOM \n");
		rfcInfo.append(Tab + "MATNR>>" + m0.getMatnr() 
				+ ", WERKS>>" + m0.getWerks() 
				+ ", STLAN>>" + m0.getStlan() 
				+ ", DATUV>>" + m0.getDatuv() + "\n");

		// STPO_API03 - M1

		Stpo_api03Table m1Table = new Stpo_api03Table();
		Enumeration compo = componentstoDeleteTypeMTC.elements();

		while (compo.hasMoreElements()) {

			DepData bc1 = (DepData) compo.nextElement();

			String component = bc1.getComponent();
			String itemcat = bc1.getItem_Categ();
			String itemno = bc1.getItem_No();
			BigInteger itemnode = bc1.getItem_Node();
			BigInteger itemcount = bc1.getItem_Count();

			Stpo_api03TableRow m1Row = m1Table.createEmptyRow();

			m1Row.setItemCateg(itemcat);
			m1Row.setItemNo(itemno);
			m1Row.setComponent(component);
			m1Row.setItemNode(itemnode);
			m1Row.setItemCount(itemcount);

			m1Row.setFldelete("X");

			m1Table.appendRow(m1Row);

			rfcInfo.append("STPO_API03  \n");
			rfcInfo.append(Tab + "ITEM_CATEG>>" + m1Row.getItemCateg()
					+ ", ITEM_NO>>" + m1Row.getItemNo() 
					+ ", COMPONENT>>" + m1Row.getComponent() 
					+ ", ITEM_NODE>>" + m1Row.getItemNode()
					+ ", ITEM_COUNT>>" + m1Row.getItemCount()
					+ ", FLDELETE>>"+ m1Row.getFldelete() + "\n");
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
		rfc.setRfaNum(chwA.getAnnDocNo());
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
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN getRfc() {
		return rfc;
	}

	@Override
	protected String getMaterialName() {
		return "Delete Sales BOM Components from typeMTC";
	}

	public void evaluate() throws Exception {
		execute();
	}

}
