package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;

public class R193ReadRevenueProfile extends Rfc {
	private com.ibm.rdh.rfc.CSAP_MAT_BOM_READ rfc;

	public R193ReadRevenueProfile(String type, String newFlag, String _plant)
			throws Exception {
		reInitialize();
		Date curDate = new Date();
		rfcName = "CSAP_MAT_BOM_READ";
		rfc = new com.ibm.rdh.rfc.CSAP_MAT_BOM_READ();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);

		if (newFlag.equals("NEW")) {
			rfc.setMaterial(type + "NEW");
		} else if (newFlag.equals("UPG")) {
			rfc.setMaterial(type + "UPG");
		} else if (newFlag.equals("MTC")) {
			rfc.setMaterial(type + "MTC");
		}

		// rfc.setPlant("1222");
		rfc.setPlant(_plant);
		rfc.setBomUsage("Y");

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
		return "Read Revenue Profile BOM";
	}

	public void evaluate() throws Exception {
		execute();
	}
}