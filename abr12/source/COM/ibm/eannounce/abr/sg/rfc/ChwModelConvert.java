/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.ModelConvertTable;

import com.google.gson.annotations.SerializedName;


public class ChwModelConvert extends RdhBase {
	@SerializedName("TBL_MODELCONVERT")
	private List<ModelConvertTable>  tbl_modelconvert ;

	public ChwModelConvert(MODELCONVERT modelConvert) {	
		
		super(modelConvert.getTOMACHTYPE().equals(modelConvert.getFROMMACHTYPE())?modelConvert.getTOMACHTYPE() + "UPG": modelConvert.getTOMACHTYPE() + "MTC"
				, "RDH_YMDMFC_MTC"
				.toLowerCase(), null);
		this.pims_identity = "H";
		
		List<AVAILABILITYLIST> availablitylist = modelConvert.getAVAILABILITYLIST();
		
		if (availablitylist!=null && !availablitylist.isEmpty())
		{
			List<AVAILABILITYELEMENT> availablityelements = availablitylist.get(0).getAVAILABILITYELEMENT();
			if(availablityelements!=null && !availablityelements.isEmpty()){
				for(AVAILABILITYELEMENT availablityelement : availablityelements){
					ModelConvertTable modelConvertTable = new ModelConvertTable();
					modelConvertTable.setMODELUPGRADEENTITYTYPE(modelConvert.getMODELUPGRADEENTITYTYPE());
					modelConvertTable.setMODELUPGRADEENTITYID(modelConvert.getMODELUPGRADEENTITYID());
					modelConvertTable.setFROMMACHTYPE(modelConvert.getFROMMACHTYPE());
					modelConvertTable.setTOMACHTYPE(modelConvert.getTOMACHTYPE());
					modelConvertTable.setCOUNTRY_FC(availablityelement.getCOUNTRY_FC());
					modelConvertTable.setAVAILABILITYACTION(availablityelement.getAVAILABILITYACTION());
					modelConvertTable.setANNDATE(availablityelement.getANNDATE());
					modelConvertTable.setFIRSTORDER(availablityelement.getFIRSTORDER());
					modelConvertTable.setPLANNEDAVAILABILITY(availablityelement.getPLANNEDAVAILABILITY());
					modelConvertTable.setPUBFROM(availablityelement.getPUBFROM());
					modelConvertTable.setPUBTO(availablityelement.getPUBTO());
					modelConvertTable.setWDANNDATE(availablityelement.getWDANNDATE());					
					modelConvertTable.setLASTORDER(availablityelement.getLASTORDER());	
					tbl_modelconvert.add(modelConvertTable);
				}
			}
			
		}
		

	}

	@Override
	protected void setDefaultValues() {
		this.tbl_modelconvert = new ArrayList<ModelConvertTable>();
	}

	@Override
	protected boolean isReadyToExecute() {
		return true;
	}
}
