package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.rfc.Zdm_geo_to_classStructure;
import com.ibm.rdh.rfc.ZdmprktblTable;
import com.ibm.rdh.rfc.ZdmprktblTableRow;

public class R144updateParkStatus extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_PARK_STATUS rfc;

	public R144updateParkStatus(String annno, String zdmstatus,
			String pimsIdentity) throws Exception {
		reInitialize();

		rfcName = "Z_DM_SAP_PARK_STATUS";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_PARK_STATUS();
		// ZDMPRKTBL
		ZdmprktblTable zdmParkTable = new ZdmprktblTable();
		ZdmprktblTableRow zdmParkRow = zdmParkTable.createEmptyRow();

		zdmParkRow.setZdmrelnum(annno);
		zdmParkRow.setZdmstatus(zdmstatus);
		// This value not set in epims code but we need it.
		zdmParkRow.setZdmclass("MD_CHW_NA");
		// add end
		zdmParkTable.appendRow(zdmParkRow);

		rfc.setIPrktbl(zdmParkTable);

		rfcInfo.append("ZDMPRKTBL \n");
		rfcInfo.append(Tab + "ZDMCLASS>>" + zdmParkRow.getZdmclass() + "\n");
		rfcInfo.append(Tab + "ZDMRELNUM>>" + zdmParkRow.getZdmrelnum()
				+ ", ZDMSTATUS>>" + zdmParkRow.getZdmstatus() + "\n");

		// ZDM_GEO_TO_CLASS
		Zdm_geo_to_classStructure zdmStructure = new Zdm_geo_to_classStructure();
		zdmStructure.setZGeo("US");
		rfc.setGeoData(zdmStructure);
		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmStructure.getZGeo() + "\n");

		// PIMS_IDENTITY not set in the epims code.
		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + rfc.getPimsIdentity() + "\n");
		// add end
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
		return "Update Park Status";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_PARK_STATUS getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}
}
