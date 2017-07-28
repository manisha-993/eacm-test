package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.HWPIMSNotFoundInMastException;

public class R193ReadRevenueProfile extends Rfc {
	private com.ibm.rdh.rfc.CSAP_MAT_BOM_READ rfc;

	public R193ReadRevenueProfile(String type, String newFlag, String _plant)
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
			String errMsg = getErrorInformation();
			rfcInfo.append("R193 returned message: " + errMsg);
			// WebService not found in MAST, return errMsg is "Material <material> not found in MAST table."
			// WebService not found in STPO, return errMsg is "Material <material> exists in Mast table but not defined to Stpo table"
			// WebService not found in STKO, return errMsg is "Material <material> exists in Mast table but not defined to Stko table"
			if (errMsg.contains("not found in MAST table")) {
				// the BOM has not created in RDH
				// we need to catch this exception and call BOM create
				throw new HWPIMSNotFoundInMastException(errMsg);
			} else if (errMsg.contains("exists in Mast table but not defined to Stpo table")) {
				// ignore, this error means there is no component in RDH
				// when we ignore this error message, call will return empty component vector
			} else if (errMsg.contains("exists in Mast table but not defined to Stko table")) {
				// when we first time call BOM create, it will create the stko record
				// if we get this error message, this is a data issue in RDH, they need to fix it. 
				throw new HWPIMSAbnormalException(errMsg);
			} else {
				throw new HWPIMSAbnormalException(errMsg);
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
		return "Read Revenue Profile BOM";
	}

	public void evaluate() throws Exception {
		execute();
	}
}