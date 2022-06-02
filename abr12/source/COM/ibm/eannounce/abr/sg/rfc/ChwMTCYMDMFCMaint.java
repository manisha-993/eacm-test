/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_MODELCONVERT;

import com.google.gson.annotations.SerializedName;


public class ChwMTCYMDMFCMaint extends RdhBase {
	@SerializedName("TBL_MODELCONVERT")
	private List<RdhChwFcProd_MODELCONVERT> tbl_modelconvert;
	
	
	public ChwMTCYMDMFCMaint(MODELCONVERT modelconvert) {
		
		super(modelconvert.getFROMMACHTYPE()==modelconvert.getTOMACHTYPE() ? modelconvert.getTOMACHTYPE() + "UPG": modelconvert.getTOMACHTYPE() + "MTC",
				"RDH_YMDMFC_MTC".toLowerCase(), null);
		this.pims_identity = "H";
		
		if (modelconvert.getAVAILABILITYLIST()!=null && !modelconvert.getAVAILABILITYLIST().isEmpty())
		{
			for (AVAILABILITYLIST availabilitylist : modelconvert.getAVAILABILITYLIST())	
			{
				if (availabilitylist.getAVAILABILITYELEMENT()!=null && !availabilitylist.getAVAILABILITYELEMENT().isEmpty())
				{
					for (AVAILABILITYELEMENT availabilityElement : availabilitylist.getAVAILABILITYELEMENT())	
					{
						RdhChwFcProd_MODELCONVERT rdhChwFcProd_Modelconvert = new RdhChwFcProd_MODELCONVERT();
						rdhChwFcProd_Modelconvert.setMODELUPGRADEENTITYTYPE(modelconvert.getMODELUPGRADEENTITYTYPE());
						rdhChwFcProd_Modelconvert.setMODELUPGRADEENTITYID(modelconvert.getMODELUPGRADEENTITYID());
						rdhChwFcProd_Modelconvert.setFROMMACHTYPE(modelconvert.getFROMMACHTYPE());
						rdhChwFcProd_Modelconvert.setTOMACHTYPE(modelconvert.getTOMACHTYPE());
						rdhChwFcProd_Modelconvert.setCOUNTRY_FC(availabilityElement.getCOUNTRY_FC());
						rdhChwFcProd_Modelconvert.setAVAILABILITYACTION(availabilityElement.getAVAILABILITYACTION());
						rdhChwFcProd_Modelconvert.setANNDATE(availabilityElement.getANNDATE().replaceAll("-", ""));
						rdhChwFcProd_Modelconvert.setFIRSTORDER(availabilityElement.getFIRSTORDER().replaceAll("-", ""));
						rdhChwFcProd_Modelconvert.setPLANNEDAVAILABILITY(availabilityElement.getPLANNEDAVAILABILITY().replaceAll("-", ""));
						rdhChwFcProd_Modelconvert.setWDANNDATE(availabilityElement.getWDANNDATE().replaceAll("-", ""));
						rdhChwFcProd_Modelconvert.setLASTORDER(availabilityElement.getLASTORDER().replaceAll("-", ""));
						rdhChwFcProd_Modelconvert.setPUBFROM(availabilityElement.getPUBFROM().replaceAll("-", ""));
						rdhChwFcProd_Modelconvert.setPUBTO(availabilityElement.getPUBTO().replaceAll("-", ""));
						tbl_modelconvert.add(rdhChwFcProd_Modelconvert);
					}
				}
			}
		}
	}



	@Override
	protected void setDefaultValues() {
		this.tbl_modelconvert = new ArrayList<RdhChwFcProd_MODELCONVERT>();
	}

	@Override
	protected boolean isReadyToExecute() {
		return true;
	}
}
