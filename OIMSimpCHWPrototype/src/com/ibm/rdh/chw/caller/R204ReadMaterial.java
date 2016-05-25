package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;

public class R204ReadMaterial extends Rfc {

	private com.ibm.rdh.rfc.BAPI_MATERIAL_GET_DETAIL rfc;

	public R204ReadMaterial(String material) throws Exception {
		reInitialize();
		rfcName = "BAPI_MATERIAL_GET_DETAIL";

		reInitialize();
		// First check and see if tfc is empty and we do not need to do
		// anything.

		rfc = new com.ibm.rdh.rfc.BAPI_MATERIAL_GET_DETAIL();

		rfc.setMaterial(material);
		rfcInfo.append("MATERIAL \n");
		rfcInfo.append(Tab + "MATERIAL>>" + rfc.getMaterial() + "\n");

	}


	@Override
	public void execute() throws Exception {
		logExecution();
		getRfc().execute();
		getLog().debug(getErrorInformation());
		if (getSeverity() == ERROR) {
			String errMsg = getErrorInformation();
			// WebService not found, return errMsg is "Material <material> not found in MARA table."
			if (errMsg != null
					&& errMsg.contains("not found in MARA table")) {
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
		String rc = getRfc().getReturn().getType();
		if ("S".equals(rc)) {
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
			ans = getRfc().getReturn().getMessage();
		}
		return ans;
	}

	@Override
	protected String getMaterialName() {
		return "Read Material";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.BAPI_MATERIAL_GET_DETAIL getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}
}
