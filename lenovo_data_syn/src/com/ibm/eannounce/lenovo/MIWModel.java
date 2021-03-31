package com.ibm.eannounce.lenovo;

public class MIWModel {

	public String DTSOFMSG = "";

	public String ACTIVITY = "";

	public String DTSMIWCREATE = "";

	public String PRODUCTID = "";
	public String MFRPRODTYPE = "";
	public String MFRPRODDESC = "";

	public String MKTGDIV = "";

	public String CATGSHRTDESC = "";

	public String STRTOFSVC = "";

	public String ENDOFSVC = "";

	public String VENDNAM = "";

//	public String MACHRATECATG = "";
	public String CECSPRODKEY = "";
	public String MAINTANNBILLELIGINDC = "";
	public String FSLMCPU = "";

//	public String PRODSUPRTCD = "";
//	public String PRFTCTR = "";
	public String DCG = "";
	
	public String getDTSOFMSG() {
		return DTSOFMSG;
	}

	public void setDTSOFMSG(String dTSOFMSG) {
		DTSOFMSG = dTSOFMSG;
	}

	public String getACTIVITY() {
		return ACTIVITY;
	}

	public void setACTIVITY(String aCTIVITY) {
		ACTIVITY = aCTIVITY;
	}

	public String getDTSMIWCREATE() {
		return DTSMIWCREATE;
	}

	public void setDTSMIWCREATE(String dTSMIWCREATE) {
		DTSMIWCREATE = dTSMIWCREATE;
	}

	public String getMFRPRODTYPE() {
		return MFRPRODTYPE;
	}

	public void setMFRPRODTYPE(String mFRPRODTYPE) {
		MFRPRODTYPE = mFRPRODTYPE;
	}

	public String getMFRPRODDESC() {
		return MFRPRODDESC;
	}

	public void setMFRPRODDESC(String mFRPRODDESC) {
		MFRPRODDESC = mFRPRODDESC;
	}

	public String getMKTGDIV() {
		return MKTGDIV;
	}

	public void setMKTGDIV(String mKTGDIV) {
		MKTGDIV = mKTGDIV;
	}

	public String getCATGSHRTDESC() {
		return CATGSHRTDESC;
	}

	public void setCATGSHRTDESC(String cATGSHRTDESC) {
		CATGSHRTDESC = cATGSHRTDESC;
	}

	public String getSTRTOFSVC() {
		return STRTOFSVC;
	}

	public void setSTRTOFSVC(String sTRTOSVC) {
		STRTOFSVC = sTRTOSVC;
	}

	public String getENDOFSVC() {
		return ENDOFSVC;
	}

	public void setENDOFSVC(String eNDOFSVC) {
		ENDOFSVC = eNDOFSVC;
	}

	public String getVENDNAM() {
		return VENDNAM;
	}

	public void setVENDNAM(String vENDNAM) {
		VENDNAM = vENDNAM;
	}

	public String getPRODUCTID() {
		return PRODUCTID;
	}

	public void setPRODUCTID(String pRODUCTID) {
		PRODUCTID = pRODUCTID;
	}

	public String getCECSPRODKEY() {
		return CECSPRODKEY;
	}

	public void setCECSPRODKEY(String cECSPRODKEY) {
		CECSPRODKEY = cECSPRODKEY;
	}

	public String getMAINTANNBILLELIGINDC() {
		return MAINTANNBILLELIGINDC;
	}

	public void setMAINTANNBILLELIGINDC(String mAINTANNBILLELIGINDC) {
		MAINTANNBILLELIGINDC = mAINTANNBILLELIGINDC;
	}

	public String getFSLMCPU() {
		return FSLMCPU;
	}

	public void setFSLMCPU(String fSLMCPU) {
		FSLMCPU = fSLMCPU;
	}

	public String getDCG() {
		return DCG;
	}

	public void setDCG(String dCG) {
		DCG = dCG;
	}

	@Override
	public String toString() {
		return "MIWModel [DTSOFMSG=" + DTSOFMSG + ", ACTIVITY=" + ACTIVITY + ", DTSMIWCREATE=" + DTSMIWCREATE
				+ ", PRODUCTID=" + PRODUCTID + ", MFRPRODTYPE=" + MFRPRODTYPE + ", MFRPRODDESC=" + MFRPRODDESC
				+ ", MKTGDIV=" + MKTGDIV + ", CATGSHRTDESC=" + CATGSHRTDESC + ", STRTOFSVC=" + STRTOFSVC + ", ENDOFSVC="
				+ ENDOFSVC + ", VENDNAM=" + VENDNAM + ", CECSPRODKEY=" + CECSPRODKEY + ", MAINTANNBILLELIGINDC="
				+ MAINTANNBILLELIGINDC + ", FSLMCPU=" + FSLMCPU + ", DCG=" + DCG + "]";
	}

}
