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
import com.ibm.rdh.rfc.Stko_api01Structure;
import com.ibm.rdh.rfc.Stpo_api01Table;
import com.ibm.rdh.rfc.Stpo_api01TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R194CreateRevenueProfile extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_BOM_CREATE rfc;
	protected int r194ItemNumberCounter = 1;

	public void setR194ItemNumberCounter(int newR194ItemNumberCounter) {
		r194ItemNumberCounter = newR194ItemNumberCounter;
	}

	public int getR194ItemNumberCounter() {
		return r194ItemNumberCounter;
	}

	public String generateItemNumberString(String rfcString) {

		String tempString;
		String outString = "0010";
		return outString;
	}

	public R194CreateRevenueProfile(String type, String annDocNo,
			Vector auoMaterials, Vector typeModelRevs, String revProfileName,
			String newFlag, String pimsIdentity, String plant) throws Exception {

		reInitialize();

		setR194ItemNumberCounter(1);

		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_BOM_CREATE";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_BOM_CREATE();

		String itemNumber;
		// Set up the RFC fields
		// CSAP_MBOM - N0
		Csap_mbomStructure n0 = new Csap_mbomStructure();

		if (newFlag.equals("NEW")) {
			n0.setMatnr(type + "NEW");
		} else if (newFlag.equals("UPG")) {
			n0.setMatnr(type + "UPG");
		} else if (newFlag.equals("MTC")) {
			n0.setMatnr(type + "MTC");
		}
		//add 20180330
		else if("MOD".equals(newFlag.substring(0,3))){
			n0.setMatnr(type + newFlag.substring(4));
		}
		//add

		n0.setWerks(plant);
		n0.setStlan("Y");
		n0.setDatuv(sdf.format(curDate));

		rfc.setJCsapMbom(n0);

		rfcInfo.append("CSAP_MBOM \n");
		rfcInfo.append(Tab + "MATNR>>" + n0.getMatnr() + ", WERKS>>"
				+ n0.getWerks() + ", STLAN>>" + n0.getStlan() + ", DATUV>>"
				+ n0.getDatuv() + "\n");

		// STPO_API01 - N1
		Enumeration e = auoMaterials.elements();
		Stpo_api01Table n1Table = new Stpo_api01Table();
		while (e.hasMoreElements()) {
			AUOMaterial auo = (AUOMaterial) e.nextElement();
			Stpo_api01TableRow n1Row = n1Table.createEmptyRow();
			n1Row.setItemCateg("Y");
			itemNumber = generateItemNumberString("r194");
			n1Row.setItemNo(itemNumber);
			n1Row.setComponent(auo.getMaterial());
			System.out.println("AUO Material Component: " + auo.getMaterial());
			n1Row.setCompQty("1");
			n1Row.setRelSales("X");
			// Add one needed values but not set in the epims old code.
			n1Row.setCompUnit("EA");
			// end
			n1Row.setSortstring(auo.getPercentage());
			System.out.println("AUO Material Percentage" + auo.getPercentage());
			n1Table.appendRow(n1Row);

			rfcInfo.append("STPO_API01  \n");
			rfcInfo.append(Tab + "COMP_UNIT>>" + n1Row.getCompUnit() + "\n");
			rfcInfo.append(Tab + "ITEM_CATEG>>" + n1Row.getItemCateg()
					+ ", ITEM_NO>>" + n1Row.getItemNo() + ", COMPONENT>>"
					+ n1Row.getComponent() + ", COMP_QTY>>"
					+ n1Row.getCompQty() + ", REL_SALES>>"
					+ n1Row.getRelSales() + ", SORTSTRING>>"
					+ n1Row.getSortstring() + "\n");
		}

		rfc.setJStpoApi01(n1Table);

		// STKO_API01 - N7
		Stko_api01Structure n7 = new Stko_api01Structure();

		n7.setBaseQuan("1");
		n7.setBomStatusString("03");

		rfc.setJIStko(n7);
		rfcInfo.append("STKO_API01  \n");
		rfcInfo.append(Tab + "BASE_QUAN>>" + n7.getBaseQuan()
				+ ", BOM_STATUS>>" + n7.getBomStatus() + "\n");

		// ZDM_GEO_TO_CLASS
		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");
		zdmTable.appendRow(zdmRow);
		rfc.setGeoData(zdmTable);
		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

		// PIMS_IDENTITY
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
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_BOM_CREATE getRfc() {
		return rfc;
	}

	@Override
	protected String getMaterialName() {
		// TODO Auto-generated method stub
		return "Create Revenue Profile BOM";
	}

	public void evaluate() throws Exception {
		execute();
	}
}