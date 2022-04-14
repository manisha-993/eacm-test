/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_FCTRANSACTION;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_FCTRANSACTION_C;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_MODELCONVERT;

import com.google.gson.annotations.SerializedName;


public class ChwFCTYMDMFCMaint extends RdhBase {
	@SerializedName("TBL_FCTRANSACTION_C")
	private List<RdhChwFcProd_FCTRANSACTION_C> tbl_fctransaction_c;
	
	@SerializedName("TBL_FCTRANSACTION")
	private List<RdhChwFcProd_FCTRANSACTION> tbl_fctransaction;
	
	
	
	public ChwFCTYMDMFCMaint(FCTRANSACTION fctransaction) {
		
		super(fctransaction.getFROMMACHTYPE()==fctransaction.getTOMACHTYPE() ? fctransaction.getTOMACHTYPE() + "UPG": fctransaction.getTOMACHTYPE() + "MTC",
				"RDH_YMDMFC_FCT".toLowerCase(), null);
		this.pims_identity = "H";
		
		//TODO tbl_fctransaction_c
		
		
		//set tbl_fctransaction  Structure		
		RdhChwFcProd_FCTRANSACTION rdhChwFcProd_FCTRANSACTION = new RdhChwFcProd_FCTRANSACTION();
		rdhChwFcProd_FCTRANSACTION.setFROMMACHTYPE(fctransaction.getFROMMACHTYPE());
		rdhChwFcProd_FCTRANSACTION.setTOMACHTYPE(fctransaction.getTOMACHTYPE());
		rdhChwFcProd_FCTRANSACTION.setFROMMODEL(fctransaction.getFROMMODEL());
		rdhChwFcProd_FCTRANSACTION.setTOMODEL(fctransaction.getTOMODEL());
		rdhChwFcProd_FCTRANSACTION.setFROMFEATURECODE(fctransaction.getFROMFEATURECODE());
		rdhChwFcProd_FCTRANSACTION.setTOFEATURECODE(fctransaction.getTOFEATURECODE());
		rdhChwFcProd_FCTRANSACTION.setFEATURETRANSCAT(fctransaction.getFEATURETRANSACTIONCATEGORY());
		rdhChwFcProd_FCTRANSACTION.setRETURNEDPARTSMES(fctransaction.getRETURNEDPARTSMES());	
		tbl_fctransaction.add(rdhChwFcProd_FCTRANSACTION);
		
		
		
//		if (fctransaction.getAVAILABILITYLIST()!=null && !fctransaction.getAVAILABILITYLIST().isEmpty())
//		{
//			for (AVAILABILITYLIST availabilitylist : fctransaction.getAVAILABILITYLIST())	
//			{
//				if (availabilitylist.getAVAILABILITYELEMENT()!=null && !availabilitylist.getAVAILABILITYELEMENT().isEmpty())
//				{
//					for (AVAILABILITYELEMENT availabilityElement : availabilitylist.getAVAILABILITYELEMENT())	
//					{
//						RdhChwFcProd_MODELCONVERT rdhChwFcProd_Modelconvert = new RdhChwFcProd_MODELCONVERT();
//						rdhChwFcProd_Modelconvert.setMODELUPGRADEENTITYTYPE(fctransaction.getMODELUPGRADEENTITYTYPE());
//						rdhChwFcProd_Modelconvert.setMODELUPGRADEENTITYID(fctransaction.getMODELUPGRADEENTITYID());
//						rdhChwFcProd_Modelconvert.setFROMMACHTYPE(fctransaction.getFROMMACHTYPE());
//						rdhChwFcProd_Modelconvert.setTOMACHTYPE(fctransaction.getTOMACHTYPE());
//						rdhChwFcProd_Modelconvert.setCOUNTRY_FC(availabilityElement.getCOUNTRY_FC());
//						rdhChwFcProd_Modelconvert.setAVAILABILITYACTION(availabilityElement.getAVAILABILITYACTION());
//						rdhChwFcProd_Modelconvert.setANNDATE(availabilityElement.getANNDATE().replaceAll("-", ""));
//						rdhChwFcProd_Modelconvert.setFIRSTORDER(availabilityElement.getFIRSTORDER().replaceAll("-", ""));
//						rdhChwFcProd_Modelconvert.setPLANNEDAVAILABILITY(availabilityElement.getPLANNEDAVAILABILITY().replaceAll("-", ""));
//						rdhChwFcProd_Modelconvert.setWDANNDATE(availabilityElement.getWDANNDATE().replaceAll("-", ""));
//						rdhChwFcProd_Modelconvert.setLASTORDER(availabilityElement.getLASTORDER().replaceAll("-", ""));
//						rdhChwFcProd_Modelconvert.setPUBFROM(availabilityElement.getPUBFROM().replaceAll("-", ""));
//						rdhChwFcProd_Modelconvert.setPUBTO(availabilityElement.getPUBTO().replaceAll("-", ""));
//						tbl_fctransaction.add(rdhChwFcProd_Modelconvert);
//					}
//				}
//			}
//		}
	}



	@Override
	protected void setDefaultValues() {
		this.tbl_fctransaction = new ArrayList<RdhChwFcProd_FCTRANSACTION>();
	}

	@Override
	protected boolean isReadyToExecute() {
		return true;
	}
}
