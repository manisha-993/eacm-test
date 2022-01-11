/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhTssFcProd_B;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhTssFcProd_C;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;

import com.google.gson.annotations.SerializedName;


public class RdhTssFcProd extends RdhBase {
	@SerializedName("Z_GEO")
	private String z_geo;
	@SerializedName("TBL_SVCMOD_B")
	private List<RdhTssFcProd_B> tbl_svcmod_b;
	@SerializedName("TBL_SVCMOD_C")
	private List<RdhTssFcProd_C> tbl_svcmod_c;

	public RdhTssFcProd(SVCMOD svcmod) {	
		super(svcmod.getMACHTYPE() + svcmod.getMODEL(), "RDH_YMDMFC_SVCPROD"
				.toLowerCase(), null);
		
		if (svcmod.getAVAILABILITYLIST()!=null && !svcmod.getAVAILABILITYLIST().isEmpty())
		{
			for (AVAILABILITY availabilityElement : svcmod.getAVAILABILITYLIST())
			{
				RdhTssFcProd_B rdhTssFcProd_b = new RdhTssFcProd_B();
				rdhTssFcProd_b.setMachType(svcmod.getMACHTYPE());
				rdhTssFcProd_b.setModel(svcmod.getMODEL());
				rdhTssFcProd_b.setAvailabilityAction(availabilityElement.getAVAILABILITYACTION());
				rdhTssFcProd_b.setCountry_fc(RfcConfigProperties
						.getCountry(availabilityElement.getCOUNTRY_FC()));
				rdhTssFcProd_b.setAnnDate(availabilityElement.getANNDATE().replaceAll("-", ""));
				rdhTssFcProd_b.setFirstOrder(availabilityElement.getFIRSTORDER().replaceAll("-", ""));
				rdhTssFcProd_b.setPlanedAvailability(availabilityElement.getPLANNEDAVAILABILITY().replaceAll("-", ""));
				rdhTssFcProd_b.setPubFrom(availabilityElement.getPUBFROM().replaceAll("-", ""));
				rdhTssFcProd_b.setPubTo(availabilityElement.getPUBTO().replaceAll("-", ""));
				rdhTssFcProd_b.setWdAnnDate(availabilityElement.getWDANNDATE().replaceAll("-", ""));
				rdhTssFcProd_b.setLastOrder(availabilityElement.getLASTORDER().replaceAll("-", ""));
				rdhTssFcProd_b.setEosAnnDate(availabilityElement.getEOSANNDATE().replaceAll("-", ""));
				rdhTssFcProd_b.setEndOfServiceDate(availabilityElement.getENDOFSERVICEDATE().replaceAll("-", ""));
				tbl_svcmod_b.add(rdhTssFcProd_b);
			}
		}
		
		if (svcmod.getCHRGCOMPLIST()!=null && !svcmod.getCHRGCOMPLIST().isEmpty())
		{
			for (CHRGCOMP chrgComp : svcmod.getCHRGCOMPLIST())
			{
				if (chrgComp.getPRICEPOINTLIST()!=null && !chrgComp.getPRICEPOINTLIST().isEmpty())
				{
					for (PRICEPOINT pricePoint : chrgComp.getPRICEPOINTLIST())
					{
						if (pricePoint.getCNTRYEFFLIST()!=null & !pricePoint.getCNTRYEFFLIST().isEmpty())
						{
							for (CNTRYEFF cntryEff : pricePoint.getCNTRYEFFLIST())
							{
								if (cntryEff.getCOUNTRYLIST()!=null && !cntryEff.getCOUNTRYLIST().isEmpty())
								{
									for (COUNTRY country : cntryEff.getCOUNTRYLIST())
									{
										RdhTssFcProd_C rdhTssFcProd_c = new RdhTssFcProd_C();
										rdhTssFcProd_c.setMachType(svcmod.getMACHTYPE());
										rdhTssFcProd_c.setModel(svcmod.getMODEL());
										rdhTssFcProd_c.setChrgComId(chrgComp.getCHRGCOMPID());
										rdhTssFcProd_c.setEntityType(pricePoint.getENTITYTYPE());
										rdhTssFcProd_c.setEntityId(pricePoint.getENTITYID());
										rdhTssFcProd_c.setPrcPtId(pricePoint.getPRCPTID());
										rdhTssFcProd_c.setCountryList_fc(RfcConfigProperties
												.getCountry(country.getCOUNTRY_FC()));
										rdhTssFcProd_c.setEffectiveDate(cntryEff.getEFFECTIVEDATE().replaceAll("-", ""));
										rdhTssFcProd_c.setEndDate(cntryEff.getENDDATE().replaceAll("-", ""));
										tbl_svcmod_c.add(rdhTssFcProd_c);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public boolean canRun() {
		if(tbl_svcmod_c==null||tbl_svcmod_c.size()==0||tbl_svcmod_b==null||tbl_svcmod_b.size()==0)
			return false;
		return true;
	}

	@Override
	protected void setDefaultValues() {
		this.z_geo = "WW";
		this.tbl_svcmod_b = new ArrayList<RdhTssFcProd_B>();
		this.tbl_svcmod_c = new ArrayList<RdhTssFcProd_C>();
	}

	@Override
	protected boolean isReadyToExecute() {
		return true;
	}
}
