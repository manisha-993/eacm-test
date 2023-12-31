package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;
import com.ibm.rdh.rfc.ZdmchwplcTable;
import com.ibm.rdh.rfc.ZdmchwplcTableRow;

public class R198updateLifecycleRow extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_CHW_PRODUCT_CYCLE rfc;

	public R198updateLifecycleRow(String material, String varCond,
			String salesStatus, Date validFrom, Date validTo, String user,
			String annDocNo, String check, String pimsIdentity, String salesOrg)
			throws Exception {
		reInitialize();

		rfcName = "Z_DM_SAP_CHW_PRODUCT_CYCLE";

		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyyMMdd");
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

		tRow.setVmsta(salesStatus);
		rfcInfo.append(Tab + "VMSTA>>" + tRow.getVmsta() + ",");

		tRow.setVarcond(varCond);
		rfcInfo.append(Tab + "VARCOND>>" + tRow.getVarcond() + ",");

		tRow.setDatbiString(sdf.format(validTo));
		rfcInfo.append(Tab + "DATBI>>" + sdf.format(tRow.getDatbi()) + ",");

		tRow.setDatabString(sdf.format(validFrom));
		rfcInfo.append(Tab + "DATAB>>" + sdf.format(tRow.getDatab()) + ",");

		tRow.setZdmUpdateIuser(truncateInternetUser(user));
		rfcInfo.append(Tab + "ZDM_UPDATE_IUSER>>" + tRow.getZdmUpdateIuser()
				+ ",");

	/*	if ("wdfm".equals(check)) {
			tRow.setZdmRfanum(annDocNo + "_W");
		} else {
			tRow.setZdmRfanum(annDocNo);
		}*/
		tRow.setZdmRfanum(annDocNo);
		rfcInfo.append(Tab + "ZDM_RFANUM>>" + tRow.getZdmRfanum() + "\n");

		t.appendRow(tRow);

		rfc.setZdmChwPlc(t);

		rfcInfo.append("DIRECT FIELDS\n");

		// ACTION_CODE
		rfc.setActionCode("U");
		rfcInfo.append(Tab + "ACTION_CODE>>" + rfc.getActionCode() + ",");

		// PIMS_IDENTITY
		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append(Tab + "PIMS_IDENTITY>>" + rfc.getPimsIdentity() + ",");

		// RFANUMBER
		/*if ("wdfm".equals(check)) {
			rfc.setRfaNum(annDocNo + "_W");
		} else {
			rfc.setRfaNum(annDocNo);
		}*/
		rfc.setRfaNum(annDocNo);
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

	private String truncateInternetUser(String userID) {
		String ans = userID;
		if (userID == null) {
			ans = "";
		} else if (userID.length() > 120) {
			ans = userID.substring(0, 120);
		}
		return ans;
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

	public com.ibm.rdh.rfc.Z_DM_SAP_CHW_PRODUCT_CYCLE getRfc() {
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
		return "Update Lifecycle Row";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {
		execute();
	}
}
