package com.ibm.rdh.chw.caller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.BomComponent;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.rfc.Csap_mbomStructure;
import com.ibm.rdh.rfc.Stpo_api03Table;
import com.ibm.rdh.rfc.Stpo_api03TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R170removeTypeModelsFromBOM extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN rfc;
	protected int r170ItemNumberCounter = 1;
	
	public R170removeTypeModelsFromBOM(String type, Hashtable matches,
			String sapPlant, String newFlag, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		reInitialize();
		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_BOM_MAINTAIN";
		String itemNumber = generateItemNumberString("r170");

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN();
		System.out.println("Matches Size ====" + matches.size());

		// Set up the RFC fields
		// CSAP_MBOM - M0
		Csap_mbomStructure m0 = new Csap_mbomStructure();

		if (newFlag.equals("NEW")) {
			m0.setMatnr(type + "NEW");
		} else if (newFlag.equals("UPG")) {
			m0.setMatnr(type + "UPG");
		} else if (newFlag.equals("MTC")) {
			m0.setMatnr(type + "MTC");
		}

		// m0.setWerks(chwPlant.getSAPPlant());

		m0.setWerks(sapPlant);

		m0.setStlan("5");
		m0.setDatuv(sdf.format(curDate));

		rfc.setJCsapMbom(m0);

		rfcInfo.append("CSAP_MBOM \n");
		rfcInfo.append(Tab + "MATNR>>" + m0.getMatnr() + ", WERKS>>"
				+ m0.getWerks() + ", STLAN>>" + m0.getStlan() + ", DATUV>>"
				+ m0.getDatuv() + "\n");

		// STPO_API03 - M1

		Stpo_api03Table m1Table = new Stpo_api03Table();
		Enumeration compo = matches.elements();

		// Iterator compo = matches.iterator();
		while (compo.hasMoreElements()) {

			// String item = (String)compo.next();
			BomComponent bc1 = (BomComponent) compo.nextElement();

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

			// m1Row.setComponent(item);
			// m1Row.setCompQty("1");
			// m1Row.setCompUnit("EA");
			// m1Row.setRelSales("X");
			// m1Row.setIdentifier("A1");

			m1Table.appendRow(m1Row);

			rfcInfo.append("STPO_API03  \n");
			rfcInfo.append(Tab + "ITEM_CATEG>>" + m1Row.getItemCateg()
					+ ", ITEM_NO>>" + m1Row.getItemNo() + ", COMPONENT>>"
					+ m1Row.getComponent() + ", ITEM_NODE>>"
					+ m1Row.getItemNode() + ", ITEM_COUNT>>"
					+ m1Row.getItemCount() + ", FLDELETE>>"
					+ m1Row.getFldelete() +
					// ", COMP_QTY>>"+m1Row.getCompQty()+
					// ", COMP_UNIT>>"+m1Row.getCompUnit()+
					// ", REL_SALES>>"+m1Row.getRelSales()+
					// ", IDENTIFIER>>"+m1Row.getIdentifier()+
					"\n");
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
		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + rfc.getRfaNum() + "\n");

	}

	public String generateItemNumberString(String rfcString) {

		String tempString;
		String outString = "0010";

		if (rfcString.equals("r170")) {
			tempString = ("0000" + Integer.toString(r170ItemNumberCounter*10));
			outString = tempString.substring(tempString.length()-4);
			r170ItemNumberCounter++;
		}
		return outString;
	}
	
	public int getR170ItemNumberCounter() {
		return r170ItemNumberCounter;
	}
	
	public void setR170ItemNumberCounter(int newR170ItemNumberCounter) {
		r170ItemNumberCounter = newR170ItemNumberCounter;
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
		return "Remove Type Models from BOM";
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
