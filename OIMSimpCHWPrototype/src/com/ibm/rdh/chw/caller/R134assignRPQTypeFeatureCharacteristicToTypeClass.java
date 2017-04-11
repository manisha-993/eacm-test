package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeFeature;
import com.ibm.rdh.rfc.RmclmTable;
import com.ibm.rdh.rfc.RmclmTableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R134assignRPQTypeFeatureCharacteristicToTypeClass extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_ASSIGN_CHAR_TO_CLASS rfc;

	public R134assignRPQTypeFeatureCharacteristicToTypeClass(
			TypeFeature typeFeature, String model, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		reInitialize();
		rfcName = "Z_DM_SAP_ASSIGN_CHAR_TO_CLASS";
		String charac;

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_ASSIGN_CHAR_TO_CLASS();

		// Set up the RFC fields
		// C0
		String className;
		String range = typeFeature.calculateRange100();
		// [Work Item 1681790] New: ESW - unique CLASS (range) and featurenaming support needed for CHW EACM HIPO materials
		className = generateClassName(typeFeature.getType(), model, range);
//		className = "MK_" + typeFeature.getType() + "_FEAT" + formatRange(range);
		rfc.setJClass(className);
		rfc.setJKlart("300");

		rfcInfo.append("Direct fields \n");
		rfcInfo.append(Tab + "CLASS>>" + rfc.getJClass() + ", KLART>>"
				+ rfc.getJKlart() + "\n");

		// RMCLM - V1
		RmclmTable c1Table = new RmclmTable();
		RmclmTableRow c1Row = c1Table.createEmptyRow();

		charac = generateCharact(typeFeature.getType(), model, typeFeature.getFeature());
//		charac = "MK_" + typeFeature.getType() + "_" + typeFeature.getFeature();
		c1Row.setMerkma(charac);
		c1Table.appendRow(c1Row);

		rfc.setJMultichar(c1Table);
		rfcInfo.append("RMCLM \n");
		rfcInfo.append(Tab + "MERKMA>>" + c1Row.getMerkma() + "\n");

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
		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + rfc.getRfaNum() + "\n");
	}
	
	public R134assignRPQTypeFeatureCharacteristicToTypeClass(
			TypeFeature typeFeature, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		this(typeFeature, null, chwA, pimsIdentity);
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @return java.lang.String
	 * @param range
	 *            java.lang.String
	 */
	protected String formatRange(String range) {

		int count;

		count = range.length();

		if (count > 0) {
			return "_" + range;
		} else {
			return range;
		}

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
		return "Assign RPQ Type/Feature char to TYPE feat range class";
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
