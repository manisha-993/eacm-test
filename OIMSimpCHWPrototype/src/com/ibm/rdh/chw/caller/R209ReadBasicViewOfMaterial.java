package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.rfc.BAPI_MATERIAL_GET_DETAIL;
import com.ibm.rdh.rfc.BapireturnStructure;

public class R209ReadBasicViewOfMaterial extends Rfc {

	public com.ibm.rdh.rfc.BAPI_MATERIAL_GET_DETAIL rfc;

	public R209ReadBasicViewOfMaterial(String material) {
		reInitialize();
		rfcName = "BAPI_MATERIAL_GET_DETAIL";

		reInitialize();
		// First check and see if tfc is empty and we do not need to do
		// anything.

		rfc = new com.ibm.rdh.rfc.BAPI_MATERIAL_GET_DETAIL();

		rfc.setMaterial(material);
		rfcInfo.append("MATERIAL \n");
		rfcInfo.append(Tab + "MATERIAL>>" + material + "\n");
	}

	@Override
	public void execute() throws Exception{
		
	}
		
	public BapireturnStructure execute1() throws Exception {
		// TODO Auto-generated method stub
		logExecution();
		getRfc().execute();
		getLog().debug(getErrorInformation());
		if (getSeverity() == ERROR) {
			throw new HWPIMSAbnormalException(getErrorInformation());
		}
		return getRfc().getReturn();
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
		String type = getRfc().getReturn().getType();
		if (type.equals("S")) {
			ans = true;
		}
		// rc = getRfc().getRfcrc();
		// if (0 == rc) {
		// ans = true;
		// }
		return ans;
	}

	public BAPI_MATERIAL_GET_DETAIL getRfc() {
		return rfc;
	}

	@Override
	protected String getErrorInformation() {
		String ans;
	if (isSuccessful()) {
			ans = RFC_OK_MESSAGE;
		} else {
			ans = getRfc().getReturn().getMessage();
//			ans = formatRfcErrorMessage(getRfc().getRfcrc(), getRfc()
//					.getErrorText());
		}
		return ans;
	}

	@Override
	protected String getMaterialName() {
		return "Read Basic View of Material";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public BapireturnStructure evaluate() throws Exception {
		return execute1();
	}
}
