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
import com.ibm.rdh.rfc.Csdep_datTable;
import com.ibm.rdh.rfc.Stko_api01Structure;
import com.ibm.rdh.rfc.Stpo_api01Table;
import com.ibm.rdh.rfc.Stpo_api01TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;
import com.ibm.rdh.rfc.Csdep_datTableRow;

public class R211CreateSalesBOMfortypeMTC extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_BOM_CREATE rfc;

	public R211CreateSalesBOMfortypeMTC(String type, String sapPlant,
			Vector geoV, String newFlag, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		reInitialize();

		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_BOM_CREATE";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_BOM_CREATE();

		// Set up the RFC fields
		// CSAP_MBOM - N0
		Csap_mbomStructure n0 = new Csap_mbomStructure();

		if ("NEW".equals(newFlag)) {
			n0.setMatnr(type + "NEW");
		} else if ("UPG".equals(newFlag)) {
			n0.setMatnr(type + "UPG");
		} else if ("MTC".equals(newFlag)) {
			n0.setMatnr(type + "MTC");
		}

		n0.setWerks(sapPlant);
		n0.setStlan("5");
		n0.setDatuv(sdf.format(curDate));

		rfc.setJCsapMbom(n0);

		rfcInfo.append("CSAP_MBOM \n");
		rfcInfo.append(Tab + "MATNR>>" + n0.getMatnr() 
				+ ", WERKS>>" + n0.getWerks()
				+ ", STLAN>>" + n0.getStlan() 
				+ ", DATUV>>" + n0.getDatuv() + "\n");

		// STPO_API01 - N1
		if ("MTC".equals(newFlag)) {
			Enumeration e = geoV.elements();
			Stpo_api01Table n1Table = new Stpo_api01Table();
			while (e.hasMoreElements()) {

				DepData tmg = (DepData) e.nextElement();
				Stpo_api01TableRow n1Row = n1Table.createEmptyRow();

				n1Row.setItemCateg(tmg.getItem_Categ());
				n1Row.setItemNo(tmg.getItem_No());
				n1Row.setComponent(tmg.getComponent());
				n1Row.setCompQty("1");
				n1Row.setRelSales("X");
				n1Row.setIdentifier(tmg.getItem_Node().toString());

				// add 20160517
				n1Row.setCompUnit("EA");
				n1Row.setSortstring("57");
				// add end
				n1Table.appendRow(n1Row);

				rfcInfo.append("STPO_API01  \n");
				rfcInfo.append(Tab + "ITEM_CATEG>>" + n1Row.getItemCateg()
						+ ", ITEM_NO>>" + n1Row.getItemNo() 
						+ ", COMPONENT>>" + n1Row.getComponent() 
						+ ", COMP_QTY>>" + n1Row.getCompQty() 
						+ ", REL_SALES>>" + n1Row.getRelSales() 
						+ ", IDENTIFIER>>" + n1Row.getIdentifier() + "\n");
				rfcInfo.append(Tab + "COMPUNIT>>" + n1Row.getCompUnit()
						+ ", SORTSTRING>>" + n1Row.getSortstring() + "\n");
			}

			rfc.setJStpoApi01(n1Table);

			// CSDEP_DAT - N2 STRUCTURE
			// wyl this part is not finished in the RFC
//			e = geoV.elements();
//			Csdep_datTable n2Table = new Csdep_datTable();
//
//			while (e.hasMoreElements()) {
//				DepData tmg = (DepData) e.nextElement();
//
//				Csdep_datTableRow n2Row = n2Table.createEmptyRow();
//
//				n2Row.setDepIntern(tmg.getDep_Intern());
//				n2Row.setStatus("1");
//				n2Row.setIdentifier((tmg.getItem_Node()).toString());
//				n2Row.setObjectId("2");
//
//				n2Table.appendRow(n2Row);
//
//				rfcInfo.append("CSDEP_DAT  \n");
//				rfcInfo.append(Tab + "DEP_INTERN>>" + n2Row.getDepIntern()
//						+ ", STATUS>>" + n2Row.getStatus() 
//						+ ", IDENTIFIER>>" + n2Row.getIdentifier() 
//						+ ", OBJECT_ID>>" + n2Row.getObjectId() + "\n");
//			}
//			rfc.setJCsdepDat(n2Table);
		}

		// STKO_API01 - N7
//		Stko_api01Structure n7 = new Stko_api01Structure();
//
//		n7.setBaseQuan("1");
//		n7.setBomStatusString("03");
//
//		rfc.setJIStko(n7);
//
//		rfcInfo.append("STPO_API01  \n");
//		rfcInfo.append(Tab + "BASE_QUAN>>" + n7.getBaseQuan()
//				+ ", BOM_STATUS>>" + n7.getBomStatus() + "\n");

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

	public com.ibm.rdh.rfc.Z_DM_SAP_BOM_CREATE getRfc() {
		return rfc;
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
		return "Create Sales BOM for typeMTC";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {
		execute();
	}

}
