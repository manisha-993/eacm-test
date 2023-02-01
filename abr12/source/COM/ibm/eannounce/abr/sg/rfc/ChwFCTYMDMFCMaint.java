/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_FCTRANSACTION;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_FCTRANSACTION_C;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;

import com.google.gson.annotations.SerializedName;


public class ChwFCTYMDMFCMaint extends RdhBase {
	//@SerializedName("TBL_FCTRANSACTION_C")
	//private List<RdhChwFcProd_FCTRANSACTION_C> tbl_fctransaction_c;
	
	@SerializedName("TBL_FCTRANSACTION")
	private List<RdhChwFcProd_FCTRANSACTION> tbl_fctransaction;
	
	
	
	public ChwFCTYMDMFCMaint(FCTRANSACTION fctransaction) {
		
		super(fctransaction.getFROMMACHTYPE()  + fctransaction.getTOMACHTYPE() + fctransaction.getTOFEATURECODE(),
				"RDH_YMDMFC_FCT".toLowerCase(), null);
		this.pims_identity = "H";
		
		//tbl_fctransaction_c
//		if(tmf!=null){
//			if (tmf.getAVAILABILITYLIST()!=null && !tmf.getAVAILABILITYLIST().isEmpty())
//			{
//				for (AVAILABILITYELEMENT_TMF availabilityElement : tmf.getAVAILABILITYLIST())
//				{
//					if(RfcConfigProperties.getCountry(availabilityElement.getCOUNTRY_FC())==null)
//						continue;
//					RdhChwFcProd_FCTRANSACTION_C rdhChwFcProd_FCTRANSACTION_C = new RdhChwFcProd_FCTRANSACTION_C();
//					rdhChwFcProd_FCTRANSACTION_C.setFROMMACHTYPE(fctransaction.getFROMMACHTYPE());
//					rdhChwFcProd_FCTRANSACTION_C.setTOMACHTYPE(fctransaction.getTOMACHTYPE());
//					rdhChwFcProd_FCTRANSACTION_C.setCOUNTRY_FC(RfcConfigProperties.getCountry(availabilityElement.getCOUNTRY_FC()));
//					rdhChwFcProd_FCTRANSACTION_C.setAVAILABILITYACTION(availabilityElement.getAVAILABILITYACTION());
//					rdhChwFcProd_FCTRANSACTION_C.setANNDATE(availabilityElement.getANNDATE().replaceAll("-", ""));
//					rdhChwFcProd_FCTRANSACTION_C.setFIRSTORDER(availabilityElement.getFIRSTORDER().replaceAll("-", ""));
//					rdhChwFcProd_FCTRANSACTION_C.setPLANNEDAVAILABILITY(availabilityElement.getPLANNEDAVAILABILITY().replaceAll("-", ""));
//					rdhChwFcProd_FCTRANSACTION_C.setPUBFROM(availabilityElement.getPUBFROM().replaceAll("-", ""));
//					rdhChwFcProd_FCTRANSACTION_C.setPUBTO(availabilityElement.getPUBTO().replaceAll("-", ""));
//					rdhChwFcProd_FCTRANSACTION_C.setWDANNDATE(availabilityElement.getWDANNDATE().replaceAll("-", ""));
//					rdhChwFcProd_FCTRANSACTION_C.setPUBTO(availabilityElement.getPUBTO().replaceAll("-", ""));
//					rdhChwFcProd_FCTRANSACTION_C.setLASTORDER(availabilityElement.getLASTORDER().replaceAll("-", ""));
//					rdhChwFcProd_FCTRANSACTION_C.setEOSANNDATE(availabilityElement.getEOSANNDATE().replaceAll("-", ""));
//					rdhChwFcProd_FCTRANSACTION_C.setENDOFSERVICEDATE(availabilityElement.getENDOFSERVICEDATE().replaceAll("-", ""));
//					tbl_fctransaction_c.add(rdhChwFcProd_FCTRANSACTION_C);
//				}
//			}
//		}
		
		
		//set tbl_fctransaction  Structure		
		RdhChwFcProd_FCTRANSACTION rdhChwFcProd_FCTRANSACTION = new RdhChwFcProd_FCTRANSACTION();
		rdhChwFcProd_FCTRANSACTION.setFROMMACHTYPE(fctransaction.getFROMMACHTYPE());
		rdhChwFcProd_FCTRANSACTION.setTOMACHTYPE(fctransaction.getTOMACHTYPE());
		rdhChwFcProd_FCTRANSACTION.setFROMMODEL(fctransaction.getFROMMODEL());
		rdhChwFcProd_FCTRANSACTION.setTOMODEL(fctransaction.getTOMODEL());
		rdhChwFcProd_FCTRANSACTION.setFROMFEATURECODE(fctransaction.getFROMFEATURECODE());
		rdhChwFcProd_FCTRANSACTION.setTOFEATURECODE(fctransaction.getTOFEATURECODE());
		rdhChwFcProd_FCTRANSACTION.setFEATURETRANSCAT(fctransaction.getFEATURETRANSACTIONCATEGORY());
		if ("Yes".equals(fctransaction.getRETURNEDPARTSMES())){
			rdhChwFcProd_FCTRANSACTION.setRETURNEDPARTSMES("1");
		}else if ("Feature conversion only".equals(fctransaction.getRETURNEDPARTSMES())){
			rdhChwFcProd_FCTRANSACTION.setRETURNEDPARTSMES("1");
		}else if(fctransaction.getRETURNEDPARTSMES()==null||"".equals(fctransaction.getRETURNEDPARTSMES())){
			rdhChwFcProd_FCTRANSACTION.setRETURNEDPARTSMES("");
		}else{
			rdhChwFcProd_FCTRANSACTION.setRETURNEDPARTSMES("0");
		}
		tbl_fctransaction.add(rdhChwFcProd_FCTRANSACTION);

	}



	@Override
	protected void setDefaultValues() {
		this.tbl_fctransaction = new ArrayList<RdhChwFcProd_FCTRANSACTION>();
		//this.tbl_fctransaction_c = new ArrayList<RdhChwFcProd_FCTRANSACTION_C>();
	}

	@Override
	protected boolean isReadyToExecute() {
		return true;
	}
}
