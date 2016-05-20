package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;

public class R169ReadTypeModelsFromBOM extends Rfc {
	private com.ibm.rdh.rfc.CSAP_MAT_BOM_READ rfc;

	public R169ReadTypeModelsFromBOM(String type, String plant, String newFlag)
			throws Exception {
		reInitialize();

		rfcName = "CSAP_MAT_BOM_READ";
		rfc = new com.ibm.rdh.rfc.CSAP_MAT_BOM_READ();

		if ("NEW".equals(newFlag)) {
			rfc.setMaterial(type + "NEW");
		} else if ("UPG".equals(newFlag)) {
			rfc.setMaterial(type + "UPG");
		} else if ("MTC".equals(newFlag)) {
			rfc.setMaterial(type + "MTC");
		}

		rfc.setPlant(plant);
		rfc.setBomUsage("5");

		rfcInfo.append(Tab + "MATERIAL>>" + rfc.getMaterial() 
				+ ", PLANT>>" + rfc.getPlant() 
				+ ", BOM_USAGE>>" + rfc.getBomUsage() + "\n");

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
		if ("X".equals(type)) {
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
		return "Read Type Models From BOM";
	}

	public void evaluate() throws Exception {
		execute();
	}
}