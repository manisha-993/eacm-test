package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.util.ArrayList;

public class WTAASCCNSWENTITY {

	private String CDOCNO;
	private String ANNDATE;
	private String ANNNUMBER;	
	private String ANNTYPE;
	private String RFASHRTTITLE;
	private String KEYLCKPROTCT;
	
	private String AVAILID;
	private String EFFECTIVEDATE;
	
	private String MODELID;
	private String PRODUCTVRM;
	private String MODELATR;
	private String MACHTYPEATR;
	private String INVNAME;
	private String MODMKTGDESC;
	private String USGLIAPP;
	private String SFTWARRY;	
	private String VOLUMEDISCOUNTELIG;
	private String EDUCALLOWMHGHSCH;
	//need add new one
	private ArrayList SWFEATURES;
	
	
	public String getCDOCNO() {
		return CDOCNO;
	}
	public void setCDOCNO(String cDOCNO) {
		CDOCNO = cDOCNO;
	}
	public String getANNDATE() {
		return ANNDATE;
	}
	public void setANNDATE(String aNNDATE) {
		ANNDATE = aNNDATE;
	}
	public String getANNNUMBER() {
		return ANNNUMBER;
	}
	public void setANNNUMBER(String aNNNUMBER) {
		ANNNUMBER = aNNNUMBER;
	}
	public String getANNTYPE() {
		return ANNTYPE;
	}
	public void setANNTYPE(String aNNTYPE) {
		ANNTYPE = aNNTYPE;
	}
	public String getRFASHRTTITLE() {
		return RFASHRTTITLE;
	}
	public void setRFASHRTTITLE(String rFASHRTTITLE) {
		RFASHRTTITLE = rFASHRTTITLE;
	}
	public String getKEYLCKPROTCT() {
		return KEYLCKPROTCT;
	}
	public void setKEYLCKPROTCT(String kEYLCKPROTCT) {
		KEYLCKPROTCT = kEYLCKPROTCT;
	}
	public String getAVAILID() {
		return AVAILID;
	}
	public void setAVAILID(String aVAILID) {
		AVAILID = aVAILID;
	}
	public String getEFFECTIVEDATE() {
		return EFFECTIVEDATE;
	}
	public void setEFFECTIVEDATE(String eFFECTIVEDATE) {
		EFFECTIVEDATE = eFFECTIVEDATE;
	}
	public String getMODELID() {
		return MODELID;
	}
	public void setMODELID(String mODELID) {
		MODELID = mODELID;
	}
	public String getPRODUCTVRM() {
		return PRODUCTVRM;
	}
	public void setPRODUCTVRM(String pRODUCTVRM) {
		PRODUCTVRM = pRODUCTVRM;
	}
	public String getMODELATR() {
		return MODELATR;
	}
	public void setMODELATR(String mODELATR) {
		MODELATR = mODELATR;
	}
	public String getMACHTYPEATR() {
		return MACHTYPEATR;
	}
	public void setMACHTYPEATR(String mACHTYPEATR) {
		MACHTYPEATR = mACHTYPEATR;
	}
	public String getINVNAME() {
		return INVNAME;
	}
	public void setINVNAME(String iNVNAME) {
		INVNAME = iNVNAME;
	}
	public String getMODMKTGDESC() {
		return MODMKTGDESC;
	}
	public void setMODMKTGDESC(String mODMKTGDESC) {
		MODMKTGDESC = mODMKTGDESC;
	}
	public String getUSGLIAPP() {
		return USGLIAPP;
	}
	public void setUSGLIAPP(String uSGLIAPP) {
		USGLIAPP = uSGLIAPP;
	}
	public String getSFTWARRY() {
		return SFTWARRY;
	}
	public void setSFTWARRY(String sFTWARRY) {
		SFTWARRY = sFTWARRY;
	}
	
	public String getVOLUMEDISCOUNTELIG() {
		return VOLUMEDISCOUNTELIG;
	}
	public void setVOLUMEDISCOUNTELIG(String vOLUMEDISCOUNTELIG) {
		VOLUMEDISCOUNTELIG = vOLUMEDISCOUNTELIG;
	}
	
	public String getEDUCALLOWMHGHSCH() {
		return EDUCALLOWMHGHSCH;
	}
	public void setEDUCALLOWMHGHSCH(String eDUCALLOWMHGHSCH) {
		EDUCALLOWMHGHSCH = eDUCALLOWMHGHSCH;
	}
	public ArrayList getSWFEATURES() {
		return SWFEATURES;
	}
	public void setSWFEATURES(ArrayList sWFEATURES) {
		SWFEATURES = sWFEATURES;
	}

	
}


class SWFEATURE{
	private String swFeatureId;
	private String PRICEDFEATURE;
	private String CHARGEOPTION;
	private String SFINVNAME;
	private String FEATURECODE;
	private String PRICEVALUE;
	
	public String getSwFeatureId() {
		return swFeatureId;
	}
	public void setSwFeatureId(String swFeatureId) {
		this.swFeatureId = swFeatureId;
	}
	public String getPRICEDFEATURE() {
		return PRICEDFEATURE;
	}
	public void setPRICEDFEATURE(String pRICEDFEATURE) {
		PRICEDFEATURE = pRICEDFEATURE;
	}
	public String getCHARGEOPTION() {
		return CHARGEOPTION;
	}
	public void setCHARGEOPTION(String cHARGEOPTION) {
		CHARGEOPTION = cHARGEOPTION;
	}
	public String getSFINVNAME() {
		return SFINVNAME;
	}
	public void setSFINVNAME(String sFINVNAME) {
		SFINVNAME = sFINVNAME;
	}
	public String getFEATURECODE() {
		return FEATURECODE;
	}
	public void setFEATURECODE(String fEATURECODE) {
		FEATURECODE = fEATURECODE;
	}
	public String getPRICEVALUE() {
		return PRICEVALUE;
	}
	public void setPRICEVALUE(String pRICEVALUE) {
		PRICEVALUE = pRICEVALUE;
	}
}
