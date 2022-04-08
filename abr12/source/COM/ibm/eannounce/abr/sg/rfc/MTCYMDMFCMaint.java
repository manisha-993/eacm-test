package COM.ibm.eannounce.abr.sg.rfc;

import COM.ibm.eannounce.abr.sg.rfc.entity.MTCYMDMFCMaint_Model;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;
import java.util.ArrayList;
import java.util.List;

public class MTCYMDMFCMaint extends RdhBase{

    @SerializedName("TBL_MODEL")
    private List<MTCYMDMFCMaint_Model> tbl_model;
    public MTCYMDMFCMaint(MODELCONVERT modelconvert) {
        super(modelconvert.getFROMMACHTYPE().equals(modelconvert.getTOMACHTYPE())?modelconvert.getTOMACHTYPE()+"UPG":modelconvert.getTOMACHTYPE()+"MTC",
                "RDH_YMDMFC_MTC".toLowerCase(), null);
        this.pims_identity = "H";

        if (modelconvert.getAVAILABILITYLIST()!=null && !modelconvert.getAVAILABILITYLIST().isEmpty()) {
            for (AVAILABILITYELEMENT avail: modelconvert.getAVAILABILITYLIST().get(0).getAVAILABILITYELEMENT()) {
                MTCYMDMFCMaint_Model model = new MTCYMDMFCMaint_Model();
                model.setModelEntitytype(modelconvert.getMODELUPGRADEENTITYTYPE());
                model.setModelEntityid(modelconvert.getMODELUPGRADEENTITYID());
                model.setFromMachtype(modelconvert.getFROMMACHTYPE());
                model.setToMachtype(modelconvert.getTOMACHTYPE());
                model.setCountry_fc(RfcConfigProperties
                        .getCountry(avail.getCOUNTRY_FC()));
                model.setAvailAction(avail.getAVAILABILITYACTION());
                model.setAnnDate(avail.getANNDATE());
                model.setFirstOrder(avail.getFIRSTORDER());
                model.setPlanAvail(avail.getPLANNEDAVAILABILITY());
                model.setPubFrom(avail.getPUBFROM());
                model.setPubTo(avail.getPUBTO());
                model.setWdAnndate(avail.getWDANNDATE());
                model.setLastOrder(avail.getLASTORDER());

                tbl_model.add(model);
            }
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

}


