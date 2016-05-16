package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;
import com.ibm.rdh.rfc.Zdm_mat_psales_statusTable;
import com.ibm.rdh.rfc.Zdm_mat_psales_statusTableRow;

public class R179ReadPlannedChangeForTypeModelMaterial extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_PLANNED_SALES_STATUS rfc;

	public R179ReadPlannedChangeForTypeModelMaterial(String typemod,
			String annDocNo, String check, String pimsIdentity, String salesOrg)
			throws Exception {

		reInitialize();

		rfcName = "Z_DM_SAP_PLANNED_SALES_STATUS";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_PLANNED_SALES_STATUS();

		// Zdm_mat_psales_status
		Zdm_mat_psales_statusTable r0Table = new Zdm_mat_psales_statusTable();
		Zdm_mat_psales_statusTableRow r0Row = r0Table.createEmptyRow();

		r0Row.setMatnr(typemod);
		r0Row.setVkorg(salesOrg);
		r0Row.setVtweg("00");

		r0Table.appendRow(r0Row);
		rfc.setMaterialsTab(r0Table);

		rfcInfo.append("OBJECTKEY \n");
		rfcInfo.append(Tab + "MATNR>>" + r0Row.getMatnr() 
				+ ", VKORG>>" + r0Row.getVkorg() 
				+ ", VTWEG>>" + r0Row.getVtweg() + "\n");

		// not set in old code
		// ZDM_GEO_TO_CLASS
		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");
		zdmRow.setZClass("MD_CHW_NA");

		zdmTable.appendRow(zdmRow);
		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");
		rfcInfo.append(Tab + "ZCLASS>>" + zdmRow.getZClass() + "\n");
		// end

		// PIMSIdentity
		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + rfc.getPimsIdentity() + "\n");

		// ACTIONCODE
		rfc.setActionCode("R");
		rfcInfo.append("ACTIONCODE \n");
		rfcInfo.append(Tab + "ACTIONCODE>>" + rfc.getActionCode() + "\n");

		// RFANUMBER
		if ("wdfm".equals(check)) {
			rfc.setRfaNum(annDocNo + "_WDFM");
		} else {
			rfc.setRfaNum(annDocNo);
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

	public com.ibm.rdh.rfc.Z_DM_SAP_PLANNED_SALES_STATUS getRfc() {
		return rfc;
	}

	@Override
	protected String getMaterialName() {
		return "Read Planned Change for type model Material";
	}

	public void evaluate() throws Exception {
		execute();
	}
}