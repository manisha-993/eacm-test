package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.util.HashMap;

public class WTAASAPSWENTITY {

	private String ADOCNO;
	private String ANNDATE;
	private String ANNNUMBER;	
	private String RFASHRTTITLE;
	
	private String AVAILID;
	private String EFFECTIVEDATE;	
	
	private HashMap PROD;
	
	public String getADOCNO() {
		return ADOCNO;
	}
	public void setADOCNO(String aDOCNO) {
		ADOCNO = aDOCNO;
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
	public String getRFASHRTTITLE() {
		return RFASHRTTITLE;
	}
	public void setRFASHRTTITLE(String rFASHRTTITLE) {
		RFASHRTTITLE = rFASHRTTITLE;
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
	
	public HashMap getPROD() {
		return PROD;
	}
	public void setPROD(HashMap pROD) {
		PROD = pROD;
	}
	
}

class PRODINFO{
	
	private String MODELID;
	private String PRODUCTVRM;
	private String MODELATR;
	
	private String MACHTYPEATR;
	private String MINVNAME;
	private String EDUCALLOWMHGHSCH;
	
	private String swFeatureId;
	private String PRICEDFEATURE;
	private String CHARGEOPTION;
	private String SFINVNAME;
	private String FEATURECODE;
	
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
	public String getMINVNAME() {
		return MINVNAME;
	}
	public void setMINVNAME(String mINVNAME) {
		MINVNAME = mINVNAME;
	}
	public String getEDUCALLOWMHGHSCH() {
		return EDUCALLOWMHGHSCH;
	}
	public void setEDUCALLOWMHGHSCH(String eDUCALLOWMHGHSCH) {
		EDUCALLOWMHGHSCH = eDUCALLOWMHGHSCH;
	}
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
	
	public boolean equals(Object obj) {
		if(obj == null){
            return false;
        }
        if (obj == this){
           return true;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
 
        PRODINFO p = (PRODINFO) obj;
        return this.MODELID.equals(p.getMODELID());      
    }	
}
