package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeFeature;
import com.ibm.rdh.rfc.Zdm_classTable;
import com.ibm.rdh.rfc.Zdm_classTableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R188DeleteTypeFeatureCharacteristicClassificationtoUF extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_CHAR_DELETE rfc;

	public R188DeleteTypeFeatureCharacteristicClassificationtoUF(
			TypeFeature tfObj, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		reInitialize();
		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_CHAR_DELETE";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_CHAR_DELETE();

		String feature = tfObj.getFeature();
		String ff = feature.substring(0, 1);
		String charac = "MK_" + tfObj.getType() + "_UF_" + ff + "000";
		String charname = "MK_" + tfObj.getType() + "_" + tfObj.getFeature();

		// String range = typeFeature.calculateRange1000();

		Zdm_classTable c0Table = new Zdm_classTable();
		Zdm_classTableRow c0Row = c0Table.createEmptyRow();
		c0Row.setClass(charac);
		c0Row.setKlart("300");
		c0Table.appendRow(c0Row);
		rfc.setIClass(c0Table);

		rfcInfo.append("CHARACTS \n");
		rfcInfo.append(Tab + "CLASS>>" + c0Row.get_Class() + ", KLART>>"
				+ c0Row.getKlart() + "\n");

		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");
		zdmTable.appendRow(zdmRow);

		rfc.setGeoData(zdmTable);
		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + pimsIdentity + "\n");

		// RFANUMBER
		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + chwA.getAnnDocNo() + "\n");
		// ZDMCHAR_NAME
		rfc.setZdmcharName(charname);
		rfcInfo.append("ZDMCHAR_NAME \n");
		rfcInfo.append(Tab + "ZDMCHAR_NAME>>" + rfc.getZdmcharName() + "\n");

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

	private com.ibm.rdh.rfc.Z_DM_SAP_CHAR_DELETE getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}

	@Override
	protected String getMaterialName() {
		return "Delete Type/Feature characteristic classification to UF";
	}

}
