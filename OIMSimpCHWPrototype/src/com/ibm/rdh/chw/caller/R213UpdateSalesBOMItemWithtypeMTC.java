package com.ibm.rdh.chw.caller;

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
import com.ibm.rdh.rfc.CsdataStructure;
import com.ibm.rdh.rfc.Csdep_datTable;
import com.ibm.rdh.rfc.Csdep_datTableRow;
import com.ibm.rdh.rfc.Stpo_api03Table;
import com.ibm.rdh.rfc.Stpo_api03TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R213UpdateSalesBOMItemWithtypeMTC extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_BOM_MAINTAIN rfc;
	public R213UpdateSalesBOMItemWithtypeMTC(String type, String sapPlant,
			Vector geoV, String newFlag, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		reInitialize();

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

		if (newFlag.equals("NEW")) {
			m0.setMatnr(type + "NEW");
		} else if (newFlag.equals("UPG")) {
			m0.setMatnr(type + "UPG");
		} else if (newFlag.equals("MTC")) {
			m0.setMatnr(type + "MTC");
		}

		m0.setWerks(sapPlant);
		m0.setStlan("5");
		m0.setDatuv(sdf.format(curDate));

		rfc.setJCsapMbom(m0);

		rfcInfo.append("CSAP_MBOM \n");
		rfcInfo.append(Tab + "MATNR>>" + m0.getMatnr() + ", WERKS>>"
				+ m0.getWerks() + ", STLAN>>" + m0.getStlan() + ", DATUV>>"
				+ m0.getDatuv() + "\n");

		// STPO_API03 - M1
		if (newFlag.equals("MTC")) {
			Enumeration e = geoV.elements();
			Stpo_api03Table m1Table = new Stpo_api03Table();
			// int Cnt = 1;
			while (e.hasMoreElements()) {
				DepData tmg = (DepData) e.nextElement();
				Stpo_api03TableRow m1Row = m1Table.createEmptyRow();

				m1Row.setItemCateg(tmg.getItem_Categ());

				// itemNumber = generateItemNumberString("r143");

				//add 20160510
				m1Row.setCompUnit("EA");
				m1Row.setSortstring("57");
				//add end
				m1Row.setItemNo(tmg.getItem_No());
				m1Row.setComponent(tmg.getComponent());
				m1Row.setCompQty("1");
				m1Row.setRelSales("X");
				// Made to ask Greg in Design Document in page 97 it says
				// IDENTIFIER "A1"
				m1Row.setIdentifier(tmg.getItem_Node().toString());
				m1Table.appendRow(m1Row);

				rfcInfo.append("STPO_API03  \n");
				rfcInfo.append(Tab + "ITEM_CATEG>>" + m1Row.getItemCateg()
						+ ", ITEM_NO>>" + m1Row.getItemNo() + ", COMPONENT>>"
						+ m1Row.getComponent() + ", COMP_QTY>>"
						+ m1Row.getCompQty() + ", REL_SALES>>"
						+ m1Row.getRelSales() + ", IDENTIFIER>>"
						+ m1Row.getIdentifier() + "\n");
			}

			rfc.setJStpoApi03(m1Table);

			// CSDEP_DAT - M2
			e = geoV.elements();
			Csdep_datTable m2Table = new Csdep_datTable();

			// Cnt = 1;
			while (e.hasMoreElements()) {
				DepData tmg = (DepData) e.nextElement();

				Csdep_datTableRow m2Row = m2Table.createEmptyRow();

				m2Row.setDepIntern(tmg.getDep_Intern());
				m2Row.setStatus("1");
				// Based on Above Answer I have set whether it is "A" or "A1"
				m2Row.setIdentifier(tmg.getItem_Node().toString());
				// Cnt++;
				m2Row.setObjectId("2");

				m2Table.appendRow(m2Row);

				rfcInfo.append("CSDEP_DAT Row \n");
				rfcInfo.append(Tab + "DEP_INTERN>>" + m2Row.getDepIntern()
						+ ", STATUS>>" + m2Row.getStatus() + ", IDENTIFIER>>"
						+ m2Row.getIdentifier() + ", OBJECT_ID>>"
						+ m2Row.getObjectId() + "\n");
			}

			rfc.setJCsdepDat(m2Table);

		}
		CsdataStructure m8 = new CsdataStructure();

		m8.setChar1("X");

		rfcInfo.append("CSDATASTRUCTURE \n");
		rfcInfo.append(Tab + "CHAR1>>" + m8.getChar1() + "\n");

		rfc.setJCsdata(m8);

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
		return "Update Sales BOM for MTC";
	}

	public void evaluate() throws Exception {
		execute();
	}

}
