package COM.ibm.eannounce.abr.sg.rfc;

import COM.ibm.eannounce.abr.sg.rfc.entity.MTCYMDMFCMaint_Model;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MTCYMDMFCMaint extends RdhBase{

    @SerializedName("TBL_MODELCONVERT")
    private List<MTCYMDMFCMaint_Model> tbl_model;
    public MTCYMDMFCMaint(MODELCONVERT modelconvert) {
        super(modelconvert.getFROMMACHTYPE().equals(modelconvert.getTOMACHTYPE())?modelconvert.getTOMACHTYPE()+"UPG":modelconvert.getTOMACHTYPE()+"MTC",
                "RDH_YMDMFC_MTC".toLowerCase(), null);
        this.pims_identity = "H";

        if (modelconvert.getAVAILABILITYLIST()!=null && !modelconvert.getAVAILABILITYLIST().isEmpty()) {
        	if(modelconvert.getAVAILABILITYLIST().get(0).getAVAILABILITYELEMENT()!=null) {
	            for (AVAILABILITYELEMENT avail: modelconvert.getAVAILABILITYLIST().get(0).getAVAILABILITYELEMENT()) {
	            	MTCYMDMFCMaint_Model model = new MTCYMDMFCMaint_Model();
	                model.setModelEntitytype(modelconvert.getMODELUPGRADEENTITYTYPE());
	                model.setModelEntityid(modelconvert.getMODELUPGRADEENTITYID());
	                model.setFromMachtype(modelconvert.getFROMMACHTYPE());
	                model.setToMachtype(modelconvert.getTOMACHTYPE());
	                model.setCountry_fc(RfcConfigProperties
	                        .getCountry(avail.getCOUNTRY_FC()));
	                model.setAvailAction(avail.getAVAILABILITYACTION());
	                model.setAnnDate(getDate(avail.getANNDATE()));
	                model.setFirstOrder(getDate(avail.getFIRSTORDER()));
	                model.setPlanAvail(getDate(avail.getPLANNEDAVAILABILITY()));
	                model.setPubFrom(getDate(avail.getPUBFROM()));
	                model.setPubTo(getDate(avail.getPUBTO()));
	                model.setWdAnndate(getDate(avail.getWDANNDATE()));
	                model.setLastOrder(getDate(avail.getLASTORDER()));
	
	                tbl_model.add(model);
	            }
        	}
        }
    }
    public String getDate(String date) {
    	if(date==null)
    		return date;
    	else {
			return date.replaceAll("-", "");
		}
    }

    @Override
    protected void setDefaultValues() {
        this.tbl_model = new ArrayList<MTCYMDMFCMaint_Model>();
    }

    @Override
    protected boolean isReadyToExecute() {
        return true;
    }
	public List<MTCYMDMFCMaint_Model> getTbl_model() {
		return tbl_model;
	}
	public void setTbl_model(List<MTCYMDMFCMaint_Model> tbl_model) {
		this.tbl_model = tbl_model;
	}
    
    

}


