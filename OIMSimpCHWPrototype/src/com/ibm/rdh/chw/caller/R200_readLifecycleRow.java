package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;
import com.ibm.rdh.rfc.ZdmchwplcTable;
import com.ibm.rdh.rfc.ZdmchwplcTableRow;

public class R200_readLifecycleRow extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_CHW_PRODUCT_CYCLE rfc;

	public R200_readLifecycleRow(String material, String varCond,
			String annDocNo, String check, String pimsIdentity, String salesOrg)
			throws Exception {

		reInitialize();
		rfcName = "Z_DM_SAP_CHW_PRODUCT_CYCLE";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_CHW_PRODUCT_CYCLE();
		
		// Set up the RFC fields
		// Zdm_chw_plc
		ZdmchwplcTable t = new ZdmchwplcTable();
		ZdmchwplcTableRow tRow = t.createEmptyRow();

		rfcInfo.append("ZDM_CHW_PLC\n");

		tRow.setMandt(ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_SAP_CLIENT, true));
		rfcInfo.append(Tab + "MANDT>>" + tRow.getMandt() + ",");

		tRow.setVkorg(salesOrg);
		rfcInfo.append(Tab + "VKORG>>" + tRow.getVkorg() + ",");

		tRow.setMatnr(material);
		rfcInfo.append(Tab + "MATNR>>" + tRow.getMatnr() + ",");

		tRow.setVarcond(varCond);
		rfcInfo.append(Tab + "VARCOND>>" + tRow.getVarcond() + "\n");

		t.appendRow(tRow);

		rfc.setZdmChwPlc(t);

		rfcInfo.append("DIRECT FIELDS\n");

		// ACTION_CODE
		rfc.setActionCode("R");
		rfcInfo.append(Tab + "ACTION_CODE>>" + rfc.getActionCode() + "\n");

		// PIMS_IDENTITY
		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append(Tab + "PIMS_IDENTITY>>" + rfc.getPimsIdentity() + "\n");

		// RFANUMBER
		if (check.equals("wdfm")) {
			rfc.setRfaNum(annDocNo + "_WDFM");
		} else {
			rfc.setRfaNum(annDocNo);
		}
		rfcInfo.append(Tab + "RFANUM>>" + rfc.getRfaNum() + "\n");

		// ZDM_GEO_TO_CLASS
		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");

		zdmTable.appendRow(zdmRow);
		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

	}

	@Override
	public void execute() throws Exception {
		logExecution();
		getRfc().execute();
		getLog().debug(getErrorInformation());
		if (getSeverity() == ERROR) {
			String errMsg = getErrorInformation();
			//WebService not found, return errMsg is "No rows were found to match search criteria."
			if (errMsg != null && errMsg.contains("No rows were found")) {
				rfcInfo.append(errMsg);
			} else {
				throw new HWPIMSAbnormalException();
			}
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

	public com.ibm.rdh.rfc.Z_DM_SAP_CHW_PRODUCT_CYCLE getRfc() {
		return rfc;
	}

	@Override
	protected String getMaterialName() {
		return "Read Lifecycle Row";
	}

	public void evaluate() throws Exception {
		execute();
	}
}