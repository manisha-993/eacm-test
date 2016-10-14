package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.rfc.Char_descrTable;
import com.ibm.rdh.rfc.Char_descrTableRow;
import com.ibm.rdh.rfc.CharactsTable;
import com.ibm.rdh.rfc.CharactsTableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R109createTypeMCCharacteristic extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN rfc;

	public R109createTypeMCCharacteristic(String type, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		reInitialize();

		rfcName = "Z_DM_SAP_CHAR_MAINTAIN";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN();

		// Set up the RFC fields
		// CHARACTS
		CharactsTable c0Table = new CharactsTable();
		CharactsTableRow c0Row = c0Table.createEmptyRow();
		String charac = "MK_" + type + "_MC";

		c0Row.setCharact(charac);
		c0Row.setDatatype("CHAR");
		c0Row.setCharnumberString("7");
		c0Row.setStatus("1");
		c0Row.setValassignm("S");

		c0Table.appendRow(c0Row);
		rfc.setICharacts(c0Table);

		rfcInfo.append("CHARACTS \n");
		rfcInfo.append(Tab + "CHARACT>>" + c0Row.getCharact() + ", DATATYPE>>"
				+ c0Row.getDatatype() + ", CHARNUMBER>>"
				+ c0Row.getCharnumberString() + ", STATUS>>"
				+ c0Row.getStatus() + ", VALASSIGNM>>" + c0Row.getValassignm()
				+ "\n");

		// CHAR_DESCR - C1
		Char_descrTable c1Table = new Char_descrTable();
		Char_descrTableRow c1Row = c1Table.createEmptyRow();

		c1Row.setCharact(charac);
		c1Row.setLanguage("E");
		c1Row.setChdescr("Model Conversions");

		c1Table.appendRow(c1Row);
		rfc.setICharDescr(c1Table);

		rfcInfo.append("CHAR_DESCR  \n");
		rfcInfo.append(Tab + "CHARACT>>" + c1Row.getCharact() + ", LANGUAGE>>"
				+ c1Row.getLanguage() + ", CHDESCR>>" + c1Row.getChdescr()
				+ "\n");

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
		rfcInfo.append(Tab + "PIMSIdentity>>" + pimsIdentity + "\n");

		// RFANUMBER
		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + chwA.getAnnDocNo() + "\n");

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

	public com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN getRfc() {
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
		return "Create type MC characteristic";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {
		execute();
	}

}
