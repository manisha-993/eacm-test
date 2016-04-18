package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.rfc.RmclmTable;
import com.ibm.rdh.rfc.RmclmTableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R111assignMODCharacteristicToModelsClass extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_ASSIGN_CHAR_TO_CLASS rfc;

	public R111assignMODCharacteristicToModelsClass(String type,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		reInitialize();
		rfcName = "Z_DM_SAP_ASSIGN_CHAR_TO_CLASS";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_ASSIGN_CHAR_TO_CLASS();

		// Set up the RFC fields
		// C0
		rfc.setJClass("MK_" + type + "_MC");
		rfc.setJKlart("300");

		rfcInfo.append("Direct fields \n");
		rfcInfo.append(Tab + "CLASS>>" + rfc.getJClass() + ", KLART>>"
				+ rfc.getJKlart() + "\n");

		// RMCLM - V1
		RmclmTable c1Table = new RmclmTable();
		RmclmTableRow c1Row = c1Table.createEmptyRow();

		c1Row.setMerkma("MK_" + type + "_MC");

		c1Table.appendRow(c1Row);

		rfc.setJMultichar(c1Table);

		rfcInfo.append("RMCLM \n");
		rfcInfo.append(Tab + "MERKMA>>" + c1Row.getMerkma() + "\n");

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
		return "Assign MC characteristic to MC class";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	private com.ibm.rdh.rfc.Z_DM_SAP_ASSIGN_CHAR_TO_CLASS getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}
}
