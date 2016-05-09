package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;

public class R210ReadSalesBom extends Rfc {
	private com.ibm.rdh.rfc.CSAP_MAT_BOM_READ rfc;

	public R210ReadSalesBom(String type, String newFlag, String _plant)
			throws Exception {
		reInitialize();
		Date curDate = new Date();
		rfcName = "CSAP_MAT_BOM_READ";
		rfc = new com.ibm.rdh.rfc.CSAP_MAT_BOM_READ();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);

		if ("NEW".equals(newFlag)) {
			rfc.setMaterial(type + "NEW");
		} else if ("UPG".equals(newFlag)) {
			rfc.setMaterial(type + "UPG");
		} else if ("MTC".equals(newFlag)) {
			rfc.setMaterial(type + "MTC");
		}

		rfc.setPlant(_plant);
		rfc.setBomUsage("5");

		System.out.println("***" + Tab + "MATERIAL>>" + rfc.getMaterial()
				+ ", PLANT>>" + rfc.getPlant() + ", BOM_USAGE>>"
				+ rfc.getBomUsage());
		rfcInfo.append(Tab + "MATERIAL>>" + rfc.getMaterial() + ", PLANT>>"
				+ rfc.getPlant() + ", BOM_USAGE>>" + rfc.getBomUsage() + "\n");

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
		boolean ans = true;
		String type = getRfc().getFlWarning();
		if (type.equals("X")) {
			ans = false;
		}
		return ans;
	}

	@Override
	protected String getErrorInformation() {
		String ans;
		if (isSuccessful()) {
			ans = RFC_OK_MESSAGE;
		} else {
			ans = getRfc().getErrorText();
		}
		return ans;
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.CSAP_MAT_BOM_READ getRfc() {
		return rfc;
	}

	@Override
	protected String getMaterialName() {
		// TODO Auto-generated method stub
		return "Read CHW Sales BOM";
	}

	public void evaluate() throws Exception {
		execute();
	}
}