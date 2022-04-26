/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_FEATURE;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_Model;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_TMF;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;

import com.google.gson.annotations.SerializedName;


public class RdhChwFcProd extends RdhBase {
	@SerializedName("TBL_MODEL")
	private List<RdhChwFcProd_Model> tbl_model;
	@SerializedName("TBL_TMF_C")
	private List<RdhChwFcProd_TMF> tbl_tmf_c;
	@SerializedName("TBL_FEATURE")
	private List<RdhChwFcProd_FEATURE> tbl_feature;
	
	public RdhChwFcProd(MODEL model) {
		super(model.getMACHTYPE() + model.getMODEL(), "RDH_YMDMFC_PROD"
				.toLowerCase(), null);
		this.pims_identity = "H";
		
		if (model.getAVAILABILITYLIST()!=null && !model.getAVAILABILITYLIST().isEmpty())
		{
			for (AVAILABILITY availabilityElement : model.getAVAILABILITYLIST())
			{
				RdhChwFcProd_Model rdhChwFcProd_Model = new RdhChwFcProd_Model();
				rdhChwFcProd_Model.setMachType(model.getMACHTYPE());
				rdhChwFcProd_Model.setModel(model.getMODEL());
				rdhChwFcProd_Model.setCountry_fc(RfcConfigProperties
						.getCountry(availabilityElement.getCOUNTRY_FC()));
				rdhChwFcProd_Model.setAvailabilityAction(availabilityElement.getAVAILABILITYACTION());
				rdhChwFcProd_Model.setAnnDate(availabilityElement.getANNDATE().replaceAll("-", ""));
				rdhChwFcProd_Model.setFirstOrder(availabilityElement.getFIRSTORDER().replaceAll("-", ""));
				rdhChwFcProd_Model.setPlannedAvailability(availabilityElement.getPLANNEDAVAILABILITY().replaceAll("-", ""));
				rdhChwFcProd_Model.setPubFrom(availabilityElement.getPUBFROM().replaceAll("-", ""));
				rdhChwFcProd_Model.setPubTo(availabilityElement.getPUBTO().replaceAll("-", ""));
				rdhChwFcProd_Model.setWdAnnDate(availabilityElement.getWDANNDATE().replaceAll("-", ""));
				rdhChwFcProd_Model.setLastOrder(availabilityElement.getLASTORDER().replaceAll("-", ""));
				rdhChwFcProd_Model.setEosAnnDate(availabilityElement.getEOSANNDATE().replaceAll("-", ""));
				rdhChwFcProd_Model.setEndOfServiceDate(availabilityElement.getENDOFSERVICEDATE().replaceAll("-", ""));
				rdhChwFcProd_Model.setCategory(model.getCATEGORY());
				rdhChwFcProd_Model.setOrderCode(model.getORDERCODE());
				tbl_model.add(rdhChwFcProd_Model);
			}
		}
	}

	public RdhChwFcProd(TMF_UPDATE tmf) {
		super(tmf.getMACHTYPE() + tmf.getMODEL(), "RDH_YMDMFC_PROD"
				.toLowerCase(), null);
		this.pims_identity = "H";
		
		if (tmf.getAVAILABILITYLIST()!=null && !tmf.getAVAILABILITYLIST().isEmpty())
		{
			for (AVAILABILITYELEMENT_TMF availabilityElement : tmf.getAVAILABILITYLIST())
			{
				RdhChwFcProd_TMF rdhChwFcProd_TMF = new RdhChwFcProd_TMF();
				rdhChwFcProd_TMF.setMachType(tmf.getMACHTYPE());
				rdhChwFcProd_TMF.setModel(tmf.getMODEL());
				rdhChwFcProd_TMF.setFeatureCode(tmf.getFEATURECODE());
				rdhChwFcProd_TMF.setFeatureEntityType(tmf.getFEATUREENTITYTYPE());
				rdhChwFcProd_TMF.setCountry_fc(RfcConfigProperties
						.getCountry(availabilityElement.getCOUNTRY_FC()));
				rdhChwFcProd_TMF.setAnnDate(availabilityElement.getANNDATE().replaceAll("-", ""));
				rdhChwFcProd_TMF.setFirstOrder(availabilityElement.getFIRSTORDER().replaceAll("-", ""));
				rdhChwFcProd_TMF.setPlannedAvailability(availabilityElement.getPLANNEDAVAILABILITY().replaceAll("-", ""));
				rdhChwFcProd_TMF.setPubFrom(availabilityElement.getPUBFROM().replaceAll("-", ""));
				rdhChwFcProd_TMF.setPubTo(availabilityElement.getPUBTO().replaceAll("-", ""));
				rdhChwFcProd_TMF.setWdAnnDate(availabilityElement.getWDANNDATE().replaceAll("-", ""));
				rdhChwFcProd_TMF.setLastOrder(availabilityElement.getLASTORDER().replaceAll("-", ""));
				rdhChwFcProd_TMF.setEosAnnDate(availabilityElement.getEOSANNDATE().replaceAll("-", ""));
				rdhChwFcProd_TMF.setEndOfServiceDate(availabilityElement.getENDOFSERVICEDATE().replaceAll("-", ""));
				rdhChwFcProd_TMF.setNoCstShip(tmf.getNOCSTSHIP());
				rdhChwFcProd_TMF.setInstall(tmf.getINSTALL());
				rdhChwFcProd_TMF.setConfiguratorFlag(tmf.getCONFIGURATORFLAG());
				rdhChwFcProd_TMF.setBulkMesIndc(CommonUtils.getFirstSubString(tmf.getBULKMESINDC(),1));
				rdhChwFcProd_TMF.setOrderCode(tmf.getORDERCODE());
				rdhChwFcProd_TMF.setSystemMax(tmf.getSYSTEMMAX());
				tbl_tmf_c.add(rdhChwFcProd_TMF);
			}
		}
	}
	
	public RdhChwFcProd(FEATURE feature) {
		super(feature.getENTITYTYPE() + feature.getENTITYID(), "RDH_YMDMFC_PROD"
				.toLowerCase(), null);
		this.pims_identity = "H";

		RdhChwFcProd_FEATURE rdhChwFcProd_FEATURE = new RdhChwFcProd_FEATURE();
		rdhChwFcProd_FEATURE.setFeatureCode(feature.getFEATURECODE());
		rdhChwFcProd_FEATURE.setFeatureEntityType(feature.getENTITYTYPE());
		rdhChwFcProd_FEATURE.setFeatureEntityID(feature.getENTITYID());
		if (feature.getLANGUAGELIST()!=null)
		{
			for (LANGUAGEELEMENT_FEATURE languageElement : feature.getLANGUAGELIST())
			{
				if ("1".equals(languageElement.getNLSID()))
				{
					rdhChwFcProd_FEATURE.setMktgDesc(languageElement.getMKTGDESC());
				}
			}
		}
		String priced = feature.getPRICEDFEATURE()==null||feature.getPRICEDFEATURE().length()<1?
				"Y":feature.getPRICEDFEATURE().substring(0, 1).toUpperCase();
		
		rdhChwFcProd_FEATURE.setFcType(feature.getFCTYPE());
		rdhChwFcProd_FEATURE.setFcSubcat(feature.getFCSUBCAT());
		rdhChwFcProd_FEATURE.setPricedFeature(priced);
		rdhChwFcProd_FEATURE.setFcCat(feature.getFCCAT());
		rdhChwFcProd_FEATURE.setConfiguratorFlag(feature.getCONFIGURATORFLAG());
		rdhChwFcProd_FEATURE.setChargeOption(feature.getCHARGEOPTION());
		rdhChwFcProd_FEATURE.setLcnsOptType(feature.getLICNSOPTTYPE());
		rdhChwFcProd_FEATURE.setChar_type(feature.getFCTYPE());
		tbl_feature.add(rdhChwFcProd_FEATURE);
	}

	@Override
	protected void setDefaultValues() {
		this.tbl_model = new ArrayList<RdhChwFcProd_Model>();
		this.tbl_tmf_c = new ArrayList<RdhChwFcProd_TMF>();
		this.tbl_feature = new ArrayList<RdhChwFcProd_FEATURE>();
	}

	@Override
	protected boolean isReadyToExecute() {
		return true;
	}
}
