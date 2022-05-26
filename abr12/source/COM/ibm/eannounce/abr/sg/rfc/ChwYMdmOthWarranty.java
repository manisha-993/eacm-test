/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.WARR.LANGUAGEELEMENT_WARR;
import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
import COM.ibm.eannounce.abr.sg.rfc.entity.ZYTMDMOTHWARRMOD;
import COM.ibm.eannounce.abr.sg.rfc.entity.ZYTMDMOTHWARRTMF;
import COM.ibm.eannounce.abr.sg.rfc.entity.ZYTMDMOTHWARRUPD;

import com.google.gson.annotations.SerializedName;


public class ChwYMdmOthWarranty extends RdhBase {
	@SerializedName("ZYTMDMOTHWARRUPD")
	private List<ZYTMDMOTHWARRUPD> ZYTMDMOTHWARRUPD_LIST;	
	@SerializedName("ZYTMDMOTHWARRMOD")
	private List<ZYTMDMOTHWARRMOD> ZYTMDMOTHWARRMOD_LIST;	
	@SerializedName("ZYTMDMOTHWARRTMF")
	private List<ZYTMDMOTHWARRTMF> ZYTMDMOTHWARRTMF_LIST;
	@SerializedName("MATERIAL_TYPE")
	private String MATERIAL_TYPE;
	
	public ChwYMdmOthWarranty(WARR chwProduct) {
		super(chwProduct.getWARRID(),	"Z_YMDMOTH_WARRANTY".toLowerCase(), null);
		this.pims_identity = "H";
		this.MATERIAL_TYPE = "WARR";
		
		ZYTMDMOTHWARRUPD ZYTMDMOTHWARRUPD = new ZYTMDMOTHWARRUPD();
		ZYTMDMOTHWARRUPD.setZWARRID(chwProduct.getWARRID());
		String WARRPRIOD = chwProduct.getWARRPRIOD();
		if("N/A".equals(WARRPRIOD) ||"NA".equals(WARRPRIOD)){
			ZYTMDMOTHWARRUPD.setZWARRPRIOD("0");
		}else{
			//Todo set it to CAST(chwProduct/WARRPRIOD as decimal)
			ZYTMDMOTHWARRUPD.setZWARRPRIOD(WARRPRIOD);
		}
		ZYTMDMOTHWARRUPD.setZWARRPRIODUM("MONTH");
		ZYTMDMOTHWARRUPD.setZWARRTYPE(chwProduct.getWARRTYPE().toUpperCase());
		ZYTMDMOTHWARRUPD.setZCATEGORY(chwProduct.getBHWARRCATEGORY());
		/**
		 * If chwProduct/WARRTYPE = 'International Warranty Service (IWS)' 
		 * and (chwProduct/COVRHR IS NULL or chwProduct/COVRHR="'') 
		 * AND (chwProduct/RESPROF IS NULL or chwProduct/RESPROF='"'), then
     		set ZCOVRPRIOD to "".
			Else set ZCOVRPRIOD = UPPER(chwProduct/COVRHR);
		 */
		String ZCOVRPRIOD ="";
		if("International Warranty Service (IWS)".equalsIgnoreCase(chwProduct.getWARRTYPE()) 
		&& (chwProduct.getCOVRHR()==null || "".equals(chwProduct.getCOVRHR()))
		&& (chwProduct.getRESPPROF()==null || "".equals(chwProduct.getRESPPROF()))
		){
			ZCOVRPRIOD ="";
		}else{
			if(chwProduct.getCOVRHR()!=null){
				ZCOVRPRIOD = chwProduct.getCOVRHR().toUpperCase();
			}
		}
		
		ZYTMDMOTHWARRUPD.setZCOVRPRIOD(ZCOVRPRIOD);
		//Copy from SUBSTRING(chwProduct/WARRDATERULEKEY from 1 for 12);
		String WARRDATERULEKEY = CommonUtils.getFirstSubString(chwProduct.getWARRDATERULEKEY(), 12);
		ZYTMDMOTHWARRUPD.setZWARRSDATE(WARRDATERULEKEY);
		/**
		 * If chwProduct/WARRTYPE = 'International Warranty Service (IWS)' 
		 * and (chwProduct/COVRHR IS NULL or chwProduct/COVRHR="'') 
		 * AND (chwProduct/RESPROF IS NULL or chwProduct/RESPROF='"'), then
     			set ZRESPTIME to "".
			Else
		     	set pos = POSITION(' ' IN chwProduct/RESPROF);
                If pos <=0 THEN
                    set ZRESPTIME = chwProduct/RESPROF
                else
                    set ZRESPTIME = SUBSTRING(chwProduct/RESPROF from 1 for pos);
		 */
		String ZRESPTIME = "";
		if("International Warranty Service (IWS)".equalsIgnoreCase(chwProduct.getWARRTYPE()) 
				&& (chwProduct.getCOVRHR()==null || "".equals(chwProduct.getCOVRHR()))
				&& (chwProduct.getRESPPROF()==null || "".equals(chwProduct.getRESPPROF()))
		){
			ZRESPTIME ="";
		}else{
			int pos = -1;
			if(chwProduct.getRESPPROF()!=null){
				String RESPPROF = chwProduct.getRESPPROF();
				pos = RESPPROF.indexOf(" ");
				if(pos<=0){
					ZRESPTIME = RESPPROF;
				}else{
					ZRESPTIME = CommonUtils.getFirstSubString(RESPPROF,pos);
				}
			}
		}
		
		ZYTMDMOTHWARRUPD.setZRESPTIME(ZRESPTIME);
		//
		ZYTMDMOTHWARRUPD.setZNLSID("E");
		//Copy from SUBSTRING(chwProduct/INVNAME from 1 for 40);
		if (chwProduct.getLANGUAGELIST()!=null)
		{
			for (LANGUAGEELEMENT_WARR languageElement : chwProduct.getLANGUAGELIST())
			{
				if ("1".equals(languageElement.getNLSID()))
				{
					//Copy from SUBSTRING(chwProduct/INVNAME from 1 for 40);
					ZYTMDMOTHWARRUPD.setZPRODDESC(CommonUtils.getFirstSubString(languageElement.getINVNAME(),40));
					//Copy from chwProduct/MKTGNAME 
					ZYTMDMOTHWARRUPD.setZMKTGNAME(languageElement.getMKTGNAME());
					//Copy from chwProduct/WARRDESC 
					ZYTMDMOTHWARRUPD.setZWARRDESC(languageElement.getWARRDESC());
					
				}
			}
		}
		//chwProduct/OEMESAPRTSLBR 
		ZYTMDMOTHWARRUPD.setZALTWTY1(chwProduct.getOEMESAPRTSLBR());
		//Copy from chwProduct/OEMESAPRTSONY 
		ZYTMDMOTHWARRUPD.setZALTWTY2(chwProduct.getOEMESAPRTSONY());
		//Copy from chwProduct/TIERMAIN
		ZYTMDMOTHWARRUPD.setZTIER_MAIN(chwProduct.getTIERMAIN());
		//Copy from chwProduct/PREDSUPP
		ZYTMDMOTHWARRUPD.setZPRED_SUP(chwProduct.getPREDSUPP());
		//Copy from chwProduct/ENHCOMRES
		ZYTMDMOTHWARRUPD.setZENH_RESP(chwProduct.getENHCOMRES());
		//Copy from chwProduct/REMCODLOAD 
		ZYTMDMOTHWARRUPD.setZREM_CLOAD(chwProduct.getREMCODLOAD());
		//Copy from chwProduct/TIERWSU
		ZYTMDMOTHWARRUPD.setZTECH_ADV(chwProduct.getTIERWSU());
		//Copy from chwProduct/TECHADV
		ZYTMDMOTHWARRUPD.setZTECHADVISOR(chwProduct.getTECHADV());
		//Copy from chwProduct/SVC1-4
		ZYTMDMOTHWARRUPD.setZSVC1(chwProduct.getSVC1());
		ZYTMDMOTHWARRUPD.setZSVC2(chwProduct.getSVC2());
		ZYTMDMOTHWARRUPD.setZSVC3(chwProduct.getSVC3());
		ZYTMDMOTHWARRUPD.setZSVC4(chwProduct.getSVC4());
		ZYTMDMOTHWARRUPD_LIST.add(ZYTMDMOTHWARRUPD);

	}

	public ChwYMdmOthWarranty(MODEL chwProduct) {
		super(chwProduct.getMACHTYPE()+chwProduct.getMODEL(),	"Z_YMDMOTH_WARRANTY".toLowerCase(), null);
		this.pims_identity = "H";
		this.MATERIAL_TYPE = "MODEL";
		ZYTMDMOTHWARRMOD zYTMDMOTHWARRMOD = new ZYTMDMOTHWARRMOD();
		for (WARRELEMENTTYPE WARRELEMENT : chwProduct.getWARRLIST())
		{
			if("Yes".equalsIgnoreCase(WARRELEMENT.getDEFWARR())){
				//If chwProduct/WARRLIST/WARRELEMENT/WARRID <> "",
				if(!"".equals(WARRELEMENT.getWARRID())){
					zYTMDMOTHWARRMOD = setZYTMDMOTHWARRMOD(chwProduct, WARRELEMENT);
					ZYTMDMOTHWARRMOD_LIST.add(zYTMDMOTHWARRMOD);
				}
			}else if("No".equalsIgnoreCase(WARRELEMENT.getDEFWARR())){
				//If chwProduct/WARRLIST/WARRELEMENT/WARRID  = "WTY0000",
				if("WTY0000".equals(WARRELEMENT.getWARRID())){
					zYTMDMOTHWARRMOD = setZYTMDMOTHWARRMOD(chwProduct, WARRELEMENT);
					ZYTMDMOTHWARRMOD_LIST.add(zYTMDMOTHWARRMOD);
				}else if(!"WTY0000".equals(WARRELEMENT.getWARRID())){
					//then for each <COUNTRYELEMENT>
					for(COUNTRY country: WARRELEMENT.getCOUNTRYLIST()){						
					
						ZYTMDMOTHWARRMOD zyTMDMOTHWARRMOD = new ZYTMDMOTHWARRMOD();
						zyTMDMOTHWARRMOD.setZMACHTYP(chwProduct.getMACHTYPE());
						zyTMDMOTHWARRMOD.setZMODEL(chwProduct.getMODEL());
						//Set ZINVNAME =left(chwProduct/MKTGNAME,40) where NLSID = 1;
						for (LANGUAGE languageElement : chwProduct.getLANGUAGELIST())
						{
							if ("1".equals(languageElement.getNLSID()))
							{
								zyTMDMOTHWARRMOD.setZINVNAME(CommonUtils.getFirstSubString(languageElement.getMKTGNAME(), 40));
								
							}
						}
						/**
						 * Set it to GENAREACODE.
							select GENAREACODE from GENERALAREA_UPDATE_CBSE G 
							where G.GENAREANAME_FC = chwProduct/WARRLIST/WARRELEMENT/COUNTRYLIST/COUNTRYELEMENT/COUNTRY_FC.
						 */
						String COUNTRY_FC = country.getCOUNTRY_FC();					
						zyTMDMOTHWARRMOD.setZCOUNTRY(COUNTRY_FC);
						//Copy from chwProduct/WARRLIST/WARRELEMENT/WARRID
						zyTMDMOTHWARRMOD.setZWRTYID(WARRELEMENT.getWARRID());
						//If chwProduct/WARRLIST/WARRELEMENT/PUBFROM is null, then 
						// set it to "19800101".
						// Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBFROM. (Format as YYYYMMDD)
						String ZPUBFROM ="";
						String sPUBFROM = WARRELEMENT.getPUBFROM();
						if(sPUBFROM==null){
							ZPUBFROM="19800101";
						}else{
							ZPUBFROM = sPUBFROM.replaceAll("-", "");
						}
						zyTMDMOTHWARRMOD.setZPUBFROM(ZPUBFROM);
						//If chwProduct/WARRLIST/WARRELEMENT/PUBTO is null, then 
						// set it to "99991231".
						// Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBTO. (Format as YYYYMMDD)
						String ZPUBTO ="";
						String sPUBTO = WARRELEMENT.getPUBTO();
						if(sPUBTO==null){
							ZPUBTO="99991231";
						}else{
							ZPUBTO = sPUBTO.replaceAll("-", "");
						}
						zyTMDMOTHWARRMOD.setZPUBTO(ZPUBTO);
						//Set to first character of chwProduct/WARRLIST/WARRELEMENT/WARRACTION
						zyTMDMOTHWARRMOD.setZWARR_FLAG(CommonUtils.getFirstSubString(WARRELEMENT.getWARRACTION(),1));
						//Set to first character of chwProduct/WARRLIST/WARRELEMENT/COUNTRYLIST/COUNTRYELEMENT/COUNTRYACTION.
						String COUNTRYACTION = country.getCOUNTRYACTION();					
						zyTMDMOTHWARRMOD.setZCOUNTRY_FLAG(CommonUtils.getFirstSubString(COUNTRYACTION,1));
						ZYTMDMOTHWARRMOD_LIST.add(zyTMDMOTHWARRMOD);
					}
				}
				
			}
			
		}
		
	}

	protected ZYTMDMOTHWARRMOD setZYTMDMOTHWARRMOD(MODEL chwProduct,
			WARRELEMENTTYPE WARRELEMENT) {
		ZYTMDMOTHWARRMOD zYTMDMOTHWARRMOD = new ZYTMDMOTHWARRMOD();
		zYTMDMOTHWARRMOD.setZMACHTYP(chwProduct.getMACHTYPE());
		zYTMDMOTHWARRMOD.setZMODEL(chwProduct.getMODEL());
		//Set ZINVNAME =left(chwProduct/MKTGNAME,40) where NLSID = 1;
		for (LANGUAGE languageElement : chwProduct.getLANGUAGELIST())
		{
			if ("1".equals(languageElement.getNLSID()))
			{
				zYTMDMOTHWARRMOD.setZINVNAME(CommonUtils.getFirstSubString(languageElement.getMKTGNAME(), 40));
				
			}
		}
		
		zYTMDMOTHWARRMOD.setZCOUNTRY("**");
		//Copy from chwProduct/WARRLIST/WARRELEMENT/WARRID
		zYTMDMOTHWARRMOD.setZWRTYID(WARRELEMENT.getWARRID());
		//If chwProduct/WARRLIST/WARRELEMENT/PUBFROM is null, then 
		// set it to "19800101".
		// Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBFROM. (Format as YYYYMMDD)
		String ZPUBFROM ="";
		String sPUBFROM = WARRELEMENT.getPUBFROM();
		if(sPUBFROM==null){
			ZPUBFROM="19800101";
		}else{
			ZPUBFROM = sPUBFROM.replaceAll("-", "");
		}
		zYTMDMOTHWARRMOD.setZPUBFROM(ZPUBFROM);
		//If chwProduct/WARRLIST/WARRELEMENT/PUBTO is null, then 
		// set it to "99991231".
		// Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBTO. (Format as YYYYMMDD)
		String ZPUBTO ="";
		String sPUBTO = WARRELEMENT.getPUBTO();
		if(sPUBTO==null){
			ZPUBTO="99991231";
		}else{
			ZPUBTO = sPUBTO.replaceAll("-", "");
		}
		zYTMDMOTHWARRMOD.setZPUBTO(ZPUBTO);
		//Set to first character of chwProduct/WARRLIST/WARRELEMENT/WARRACTION
		zYTMDMOTHWARRMOD.setZWARR_FLAG(CommonUtils.getFirstSubString(WARRELEMENT.getWARRACTION(),1));
		//Default to ""
		zYTMDMOTHWARRMOD.setZCOUNTRY_FLAG("");
		return zYTMDMOTHWARRMOD;
	}
	
	public ChwYMdmOthWarranty(TMF_UPDATE chwProduct) {
		super(chwProduct.getMACHTYPE()+chwProduct.getFEATURECODE(), "Z_YMDMOTH_WARRANTY".toLowerCase(), null);
		this.pims_identity = "H";
		this.MATERIAL_TYPE = "TMF";
		//If no <WARRELEMENT> found
		List<WARRELEMENT_TMF> WARRELEMENT_TMF_list = chwProduct.getWARRLIST();
		if (WARRELEMENT_TMF_list.isEmpty()){
			ZYTMDMOTHWARRTMF ZYTMDMOTHWARRTMF = new ZYTMDMOTHWARRTMF();
			//Copy from chwProduct/MACHTYPE 
			ZYTMDMOTHWARRTMF.setZMACHTYP(chwProduct.getMACHTYPE());
			//Copy from chwProduct/FEATURECODE 
			ZYTMDMOTHWARRTMF.setZFEATURECODE(chwProduct.getFEATURECODE());
			//Set ZINVNAME =left(chwProduct/MKTGNAME,40) where NLSID = 1;
			for (LANGUAGEELEMENT_TMF languageElement : chwProduct.getLANGUAGELIST())
			{
				if ("1".equals(languageElement.getNLSID()))
				{
					ZYTMDMOTHWARRTMF.setZINVNAME(CommonUtils.getFirstSubString(languageElement.getMKTGNAME(), 40));					
				}
			}
			ZYTMDMOTHWARRTMF.setZCOUNTRY("");
			ZYTMDMOTHWARRTMF.setZWRTYID("");
			ZYTMDMOTHWARRTMF.setZPUBFROM("0000/00/00");
			ZYTMDMOTHWARRTMF.setZPUBTO("0000/00/00");
			ZYTMDMOTHWARRTMF.setZWARR_FLAG("");
			ZYTMDMOTHWARRTMF.setZCOUNTRY_FLAG("");
			ZYTMDMOTHWARRTMF_LIST.add(ZYTMDMOTHWARRTMF);
			
		}else{
			//If chwProduct/WARRLIST/WARRELEMENT/DEFWARR = "Yes", then
			for (WARRELEMENT_TMF WARRELEMENT : WARRELEMENT_TMF_list)
			{
				String DEFWARR = WARRELEMENT.getDEFWARR();
				if("Yes".equalsIgnoreCase(DEFWARR)){
					//If chwProduct/WARRLIST/WARRELEMENT/WARRID <> "",
					if(!"".equals(WARRELEMENT.getWARRID())){
						ZYTMDMOTHWARRTMF ZYTMDMOTHWARRTMF = new ZYTMDMOTHWARRTMF();
						//Copy from chwProduct/MACHTYPE 
						ZYTMDMOTHWARRTMF.setZMACHTYP(chwProduct.getMACHTYPE());
						//Copy from chwProduct/FEATURECODE 
						ZYTMDMOTHWARRTMF.setZFEATURECODE(chwProduct.getFEATURECODE());
						//Set ZINVNAME =left(chwProduct/MKTGNAME,40) where NLSID = 1;
						for (LANGUAGEELEMENT_TMF languageElement : chwProduct.getLANGUAGELIST())
						{
							if ("1".equals(languageElement.getNLSID()))
							{
								ZYTMDMOTHWARRTMF.setZINVNAME(CommonUtils.getFirstSubString(languageElement.getMKTGNAME(), 40));					
							}
						}
						ZYTMDMOTHWARRTMF.setZCOUNTRY("**");
						//Copy from chwProduct/WARRLIST/WARRELEMENT/WARRID
						ZYTMDMOTHWARRTMF.setZWRTYID(WARRELEMENT.getWARRID());
						/**
						 * If chwProduct/WARRLIST/WARRELEMENT/PUBFROM is null, then 
     						set it to "19800101".
							Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBFROM. (Format as YYYYMMDD)
						 */
						String ZPUBFROM ="";
						String sPUBFROM = WARRELEMENT.getPUBFROM();
						if(sPUBFROM==null){
							ZPUBFROM="19800101";
						}else{
							ZPUBFROM = sPUBFROM.replaceAll("-", "");
						}
						ZYTMDMOTHWARRTMF.setZPUBFROM(ZPUBFROM);
						/**
						 * If chwProduct/WARRLIST/WARRELEMENT/PUBTO is null, then 
							     set it to "99991231".
							Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBTO. (Format as YYYYMMDD)
						 */
						String ZPUBTO ="";
						String sPUBTO = WARRELEMENT.getPUBTO();
						if(sPUBTO==null){
							ZPUBTO="99991231";
						}else{
							ZPUBTO = sPUBTO.replaceAll("-", "");
						}
						ZYTMDMOTHWARRTMF.setZPUBTO(ZPUBTO);
						//Set to first character of chwProduct/WARRLIST/WARRELEMENT/WARRACTION
						ZYTMDMOTHWARRTMF.setZWARR_FLAG(CommonUtils.getFirstSubString(WARRELEMENT.getWARRACTION(),1));
						ZYTMDMOTHWARRTMF.setZCOUNTRY_FLAG("");
						ZYTMDMOTHWARRTMF_LIST.add(ZYTMDMOTHWARRTMF);						
					}
					
				}else if("No".equalsIgnoreCase(DEFWARR)){
					for(COUNTRYELEMENT_TMF country: WARRELEMENT.getCOUNTRYLIST()){	
						ZYTMDMOTHWARRTMF ZYTMDMOTHWARRTMF = new ZYTMDMOTHWARRTMF();
						//Copy from chwProduct/MACHTYPE 
						ZYTMDMOTHWARRTMF.setZMACHTYP(chwProduct.getMACHTYPE());
						//Copy from chwProduct/FEATURECODE 
						ZYTMDMOTHWARRTMF.setZFEATURECODE(chwProduct.getFEATURECODE());
						//Set ZINVNAME =left(chwProduct/MKTGNAME,40) where NLSID = 1;
						for (LANGUAGEELEMENT_TMF languageElement : chwProduct.getLANGUAGELIST())
						{
							if ("1".equals(languageElement.getNLSID()))
							{
								ZYTMDMOTHWARRTMF.setZINVNAME(CommonUtils.getFirstSubString(languageElement.getMKTGNAME(), 40));					
							}
						}
						/**
						 * If chwProduct/WARRLIST/WARRELEMENT/WARRID = "WTY0000", then set it to "**".
								Else set it to GENAREACODE.
							select GENAREACODE from GENERALAREA_UPDATE_CBSE G where G.GENAREANAME_FC = 
							chwProduct/WARRLIST/WARRELEMENT/COUNTRYLIST/COUNTRYELEMENT/COUNTRY_FC.
						 */
						String ZCOUNTRY ="";
						if("WTY0000".equalsIgnoreCase(WARRELEMENT.getWARRID())){
							ZCOUNTRY = "";
						}else{
							ZCOUNTRY = country.getCOUNTRY_FC();
						}
						ZYTMDMOTHWARRTMF.setZCOUNTRY(ZCOUNTRY);
						//Copy from chwProduct/WARRLIST/WARRELEMENT/WARRID
						ZYTMDMOTHWARRTMF.setZWRTYID(WARRELEMENT.getWARRID());
						/**
						 * If chwProduct/WARRLIST/WARRELEMENT/PUBFROM is null, then 
     						set it to "19800101".
							Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBFROM. (Format as YYYYMMDD)
						 */
						String ZPUBFROM ="";
						String sPUBFROM = WARRELEMENT.getPUBFROM();
						if(sPUBFROM==null){
							ZPUBFROM="19800101";
						}else{
							ZPUBFROM = sPUBFROM.replaceAll("-", "");
						}
						ZYTMDMOTHWARRTMF.setZPUBFROM(ZPUBFROM);
						/**
						 * If chwProduct/WARRLIST/WARRELEMENT/PUBTO is null, then 
							     set it to "99991231".
							Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBTO. (Format as YYYYMMDD)
						 */
						String ZPUBTO ="";
						String sPUBTO = WARRELEMENT.getPUBTO();
						if(sPUBTO==null){
							ZPUBTO="99991231";
						}else{
							ZPUBTO = sPUBTO.replaceAll("-", "");
						}
						ZYTMDMOTHWARRTMF.setZPUBTO(ZPUBTO);
						//Set to first character of chwProduct/WARRLIST/WARRELEMENT/WARRACTION
						ZYTMDMOTHWARRTMF.setZWARR_FLAG(CommonUtils.getFirstSubString(WARRELEMENT.getWARRACTION(),1));
						ZYTMDMOTHWARRTMF.setZCOUNTRY_FLAG(CommonUtils.getFirstSubString(country.getCOUNTRYACTION(),1));
						ZYTMDMOTHWARRTMF_LIST.add(ZYTMDMOTHWARRTMF);
					}
					
					
				}
			}

		}
			
		
		
		
	}

	@Override
	protected void setDefaultValues() {
		this.ZYTMDMOTHWARRUPD_LIST = new ArrayList<ZYTMDMOTHWARRUPD>();
		this.ZYTMDMOTHWARRMOD_LIST = new ArrayList<ZYTMDMOTHWARRMOD>();	
		this.ZYTMDMOTHWARRTMF_LIST = new ArrayList<ZYTMDMOTHWARRTMF>();	
	}

	@Override
	protected boolean isReadyToExecute() {
		return true;
	}
}
