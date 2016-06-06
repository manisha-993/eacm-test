package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;

public class R214ReadMCclass extends Rfc{

	private com.ibm.rdh.rfc.BAPI_CLASS_READ rfc;
	
	public R214ReadMCclass(String type)throws Exception{
		reInitialize();
		rfcName = "BAPI_CLASS_READ";

		reInitialize();
		// First check and see if tfc is empty and we do not need to do anything.

		rfc = new com.ibm.rdh.rfc.BAPI_CLASS_READ();

		rfc.setClasstype("300");
		rfc.setClassnum("MK_"+type+"_MC");
		rfcInfo.append("Class Type \n");
		rfcInfo.append(Tab+"Type>>"+rfc.getClasstype()+
			"\n");
		rfcInfo.append("Class Number \n");
		rfcInfo.append(Tab+"Class Num>>"+rfc.getClassnum()+
			"\n");


	}
	
	public com.ibm.rdh.rfc.BAPI_CLASS_READ getRfc() {
		return rfc;
	}

	@Override
	public void execute() throws Exception {
		logExecution();
		getRfc().execute();
		getLog().debug(getErrorInformation());
		if (getSeverity() == ERROR) {
			String errMsg = getErrorInformation();
			//WebService not found, return errMsg is "Class type <300> and Class name <MK_+type+_MC> combination does not exist."
			if(errMsg != null && errMsg.contains("combination does not exist")){
				rfcInfo.append(errMsg);
			}else{
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
		return "Read MC Class";
	}
	
	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}
	
	public void evaluate() throws Exception { 
		execute() ; 
	}

}
