package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.rfc.Zdm_mat_tabTable;
import com.ibm.rdh.rfc.Zdm_mat_tabTableRow;

public class R207ReadPlantViewMaterial extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_READ_MARC rfc;

	public R207ReadPlantViewMaterial(String type, String model, String plant)
			throws Exception {

		reInitialize();
		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_READ_MARC();

		Zdm_mat_tabTable r0Table = new Zdm_mat_tabTable();
		Zdm_mat_tabTableRow r0Row = r0Table.createEmptyRow();
		r0Row.setMatnr(type + model);
		r0Row.setPlant(plant);
		r0Table.appendRow(r0Row);
		rfc.setZdmMatTab(r0Table);
		rfcInfo.append("MATNR \n");
		rfcInfo.append(Tab + "MATNR>>" + r0Row.getMatnr() + "\n");
		rfcInfo.append("PLANT \n");
		rfcInfo.append(Tab + "PLANT>>" + r0Row.getPlant() + "\n");
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
		// TODO Auto-generated method stub
		return "Read Basic View of Material";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_READ_MARC getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}

}
