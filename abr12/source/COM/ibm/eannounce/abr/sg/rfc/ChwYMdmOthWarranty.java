/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.*;
import java.util.*;

import com.google.gson.annotations.SerializedName;

import COM.ibm.eannounce.abr.sg.rfc.WARR.LANGUAGEELEMENT_WARR;
import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
import COM.ibm.eannounce.abr.sg.rfc.entity.ZYTMDMOTHWARRMOD;
import COM.ibm.eannounce.abr.sg.rfc.entity.ZYTMDMOTHWARRTMF;
import COM.ibm.eannounce.abr.sg.rfc.entity.ZYTMDMOTHWARRUPD;
import COM.ibm.eannounce.abr.util.RFCConfig;


public class ChwYMdmOthWarranty extends RdhBase {
	@SerializedName("ZYTMDMOTHWARRUPD")
	private List<ZYTMDMOTHWARRUPD> ZYTMDMOTHWARRUPD_LIST;
	@SerializedName("ZYTMDMOTHWARRMOD")
	private List<ZYTMDMOTHWARRMOD> ZYTMDMOTHWARRMOD_LIST;
	@SerializedName("ZYTMDMOTHWARRTMF")
	private List<ZYTMDMOTHWARRTMF> ZYTMDMOTHWARRTMF_LIST;
	@SerializedName("MATERIAL_TYPE")
	private String MATERIAL_TYPE;
	private   List<String> excludedCountryList=new ArrayList<>();

	public ChwYMdmOthWarranty(WARR chwProduct) {
		super(chwProduct.getWARRID(),	"Z_YMDMOTH_WARRANTY".toLowerCase(), null);
		this.pims_identity = "H";
		this.MATERIAL_TYPE = "WARR";
		//EACM-6768 Truncate values for WARR UPD CommonUtils.getFirstSubString(xxx,size)

		ZYTMDMOTHWARRUPD ZYTMDMOTHWARRUPD = new ZYTMDMOTHWARRUPD();
		ZYTMDMOTHWARRUPD.setZWARRID(chwProduct.getWARRID());
		String WARRPRIOD = chwProduct.getWARRPRIOD();
		if("N/A".equals(WARRPRIOD) ||"NA".equals(WARRPRIOD)){
			ZYTMDMOTHWARRUPD.setZWARRPRIOD("0");
		}else{
			//set it to CAST(chwProduct/WARRPRIOD as decimal)
			//if WARRPRIOD is not number, return
			if(CommonUtils.isNumber(WARRPRIOD)) {
				ZYTMDMOTHWARRUPD.setZWARRPRIOD(WARRPRIOD);
			}else {
				return;
			}
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
					ZYTMDMOTHWARRUPD.setZMKTGNAME(CommonUtils.getFirstSubString(languageElement.getMKTGNAME(),132));
					//Copy from chwProduct/WARRDESC
					ZYTMDMOTHWARRUPD.setZWARRDESC(CommonUtils.getFirstSubString(languageElement.getWARRDESC(),1000));

				}
			}
		}
		//chwProduct/OEMESAPRTSLBR length 10
		ZYTMDMOTHWARRUPD.setZALTWTY1(CommonUtils.getFirstSubString(chwProduct.getOEMESAPRTSLBR(),10));
		//Copy from chwProduct/OEMESAPRTSONY  length 10
		ZYTMDMOTHWARRUPD.setZALTWTY2(CommonUtils.getFirstSubString(chwProduct.getOEMESAPRTSONY(),10));
		//Copy from chwProduct/TIERMAIN
		ZYTMDMOTHWARRUPD.setZTIER_MAIN(CommonUtils.getFirstSubString(chwProduct.getTIERMAIN(),50));
		//Copy from chwProduct/PREDSUPP
		ZYTMDMOTHWARRUPD.setZPRED_SUP(CommonUtils.getFirstSubString(chwProduct.getPREDSUPP(),50));
		//Copy from chwProduct/ENHCOMRES
		ZYTMDMOTHWARRUPD.setZENH_RESP(CommonUtils.getFirstSubString(chwProduct.getENHCOMRES(),50));
		//Copy from chwProduct/REMCODLOAD
		ZYTMDMOTHWARRUPD.setZREM_CLOAD(CommonUtils.getFirstSubString(chwProduct.getREMCODLOAD(),50));
		//Copy from chwProduct/TIERWSU
		ZYTMDMOTHWARRUPD.setZTECH_ADV(CommonUtils.getFirstSubString(chwProduct.getTIERWSU(),50));
		//Copy from chwProduct/TECHADV
		ZYTMDMOTHWARRUPD.setZTECHADVISOR(CommonUtils.getFirstSubString(chwProduct.getTECHADV(),50));
		//Copy from chwProduct/SVC1-4
		ZYTMDMOTHWARRUPD.setZSVC1(CommonUtils.getFirstSubString(chwProduct.getSVC1(),50));
		ZYTMDMOTHWARRUPD.setZSVC2(CommonUtils.getFirstSubString(chwProduct.getSVC2(),50));
		ZYTMDMOTHWARRUPD.setZSVC3(CommonUtils.getFirstSubString(chwProduct.getSVC3(),50));
		ZYTMDMOTHWARRUPD.setZSVC4(CommonUtils.getFirstSubString(chwProduct.getSVC4(),50));
		ZYTMDMOTHWARRUPD_LIST.add(ZYTMDMOTHWARRUPD);

	}

	public ChwYMdmOthWarranty(MODEL chwProduct,Connection pdhConnection) throws SQLException {
		super(chwProduct.getMACHTYPE()+chwProduct.getMODEL(),	"Z_YMDMOTH_WARRANTY".toLowerCase(), null);
		this.pims_identity = "H";
		this.MATERIAL_TYPE = "MODEL";
		ZYTMDMOTHWARRMOD zYTMDMOTHWARRMOD = new ZYTMDMOTHWARRMOD();
		boolean existGENAREASELECTION6400 = existGENAREASELECTION6400(chwProduct.getMODELENTITYID(),pdhConnection);

		boolean ctryWarr=false;
		for (WARRELEMENTTYPE WARRELEMENT : chwProduct.getWARRLIST()) {
			if("No".equalsIgnoreCase(WARRELEMENT.getDEFWARR()) && !"WTY0000".equals(WARRELEMENT.getWARRID())){
				for(COUNTRY country: WARRELEMENT.getCOUNTRYLIST())
				{          excludedCountryList.add(country.getCOUNTRY_FC());
				}

				 ctryWarr=true;
			}
		}




		for (WARRELEMENTTYPE WARRELEMENT : chwProduct.getWARRLIST())
		{

			if("Yes".equalsIgnoreCase(WARRELEMENT.getDEFWARR())){
				//If chwProduct/WARRLIST/WARRELEMENT/WARRID <> "",
				if(!"".equals(WARRELEMENT.getWARRID()) && !ctryWarr){
					zYTMDMOTHWARRMOD = setZYTMDMOTHWARRMOD(chwProduct, WARRELEMENT);
					ZYTMDMOTHWARRMOD_LIST.add(zYTMDMOTHWARRMOD);
				} else if (!"".equals(WARRELEMENT.getWARRID()) && ctryWarr){
					ZYTMDMOTHWARRMOD_LIST.addAll(setZYTMDMOTHWARRMODs(chwProduct, WARRELEMENT,pdhConnection));
				}
			}else {
				//If chwProduct/WARRLIST/WARRELEMENT/WARRID  = "WTY0000",
				if("WTY0000".equals(WARRELEMENT.getWARRID())){
					if(!existGENAREASELECTION6400) {
						zYTMDMOTHWARRMOD = setZYTMDMOTHWARRMOD(chwProduct, WARRELEMENT);
						ZYTMDMOTHWARRMOD_LIST.add(zYTMDMOTHWARRMOD);
					}else {
						ZYTMDMOTHWARRMOD_LIST.addAll(setZYTMDMOTHWARRMODs(chwProduct, WARRELEMENT,pdhConnection));
					}
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
						String GENAREANAME_FC = RFCConfig.getAland(COUNTRY_FC);
						if(GENAREANAME_FC==null || "".equals(GENAREANAME_FC)){
							continue;
						}
						zyTMDMOTHWARRMOD.setZCOUNTRY(GENAREANAME_FC);
						//Copy from chwProduct/WARRLIST/WARRELEMENT/WARRID
						zyTMDMOTHWARRMOD.setZWRTYID(WARRELEMENT.getWARRID());
						//If chwProduct/WARRLIST/WARRELEMENT/PUBFROM is null, then
						// set it to "19800101".
						// Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBFROM. (Format as YYYYMMDD)
						String ZPUBFROM ="";
						String sPUBFROM = WARRELEMENT.getPUBFROM();
						if(sPUBFROM==null || "".equals(sPUBFROM)){
							ZPUBFROM="19800101";
						}else{
							ZPUBFROM = sPUBFROM.replaceAll("-", "");
						}
						zyTMDMOTHWARRMOD.setZPUBFROM(ZPUBFROM);
						zyTMDMOTHWARRMOD.setZWTYDESC(CommonUtils.getFirstSubString(WARRELEMENT.getWARRDESC(),40));
						//If chwProduct/WARRLIST/WARRELEMENT/PUBTO is null, then
						// set it to "99991231".
						// Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBTO. (Format as YYYYMMDD)
						String ZPUBTO ="";
						String sPUBTO = WARRELEMENT.getPUBTO();
						if(sPUBTO==null || "".equals(sPUBTO)){
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

	public boolean existGENAREASELECTION6400(String entityid,Connection connection){
		String sql = "SELECT DISTINCT f.ATTRIBUTEVALUE  FROM OPICM.RELATOR r " +
				"JOIN OPICM.TEXT t ON r.ENTITY2TYPE =t.ENTITYTYPE AND r.ENTITY2ID =t.ENTITYID AND t.ATTRIBUTECODE ='WARRID'  AND t.ATTRIBUTEVALUE ='WTY0000' AND t.VALTO > CURRENT timestamp AND t.EFFTO > CURRENT timestamp " +
				"JOIN OPICM.FLAG f ON f.ENTITYTYPE =r.ENTITYTYPE AND f.ENTITYID =r.ENTITYID AND f.ATTRIBUTECODE ='GENAREASELECTION' and f.attributevalue='6400' AND f.VALTO > CURRENT timestamp AND f.EFFTO > CURRENT timestamp " +
				"WHERE r.ENTITYTYPE ='MODELWARR' AND r.ENTITY1ID =?" +
				"AND r.VALTO > CURRENT timestamp AND r.EFFTO > CURRENT timestamp WITH ur";
		try {
			PreparedStatement prepareStatement=	connection.prepareStatement(sql
			);
			prepareStatement.setString(1,entityid);
			ResultSet resultSet= prepareStatement.executeQuery();
			while (resultSet.next()){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
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
		zYTMDMOTHWARRMOD.setZWTYDESC(CommonUtils.getFirstSubString(WARRELEMENT.getWARRDESC(),40));
		//If chwProduct/WARRLIST/WARRELEMENT/PUBFROM is null, then
		// set it to "19800101".
		// Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBFROM. (Format as YYYYMMDD)
		String ZPUBFROM ="";
		String sPUBFROM = WARRELEMENT.getPUBFROM();
		if(sPUBFROM==null || "".equals(sPUBFROM)){
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
		if(sPUBTO==null || "".equals(sPUBTO)){
			ZPUBTO="99991231";
		}else{
			ZPUBTO = sPUBTO.replaceAll("-", "");
		}
		zYTMDMOTHWARRMOD.setZPUBTO(ZPUBTO);
		//Set to first character of chwProduct/WARRLIST/WARRELEMENT/WARRACTION
		zYTMDMOTHWARRMOD.setZWARR_FLAG(CommonUtils.getFirstSubString(WARRELEMENT.getWARRACTION(),1));
		//Default to ""

		if("Yes".equalsIgnoreCase(WARRELEMENT.getDEFWARR())){
			zYTMDMOTHWARRMOD.setZCOUNTRY_FLAG("");
		}else {
			COUNTRY country = WARRELEMENT.getCOUNTRYLIST().get(0);
			String COUNTRYACTION = country.getCOUNTRYACTION();
			zYTMDMOTHWARRMOD.setZCOUNTRY_FLAG(CommonUtils.getFirstSubString(COUNTRYACTION,1));
		}


		return zYTMDMOTHWARRMOD;
	}
	protected List<ZYTMDMOTHWARRMOD> setZYTMDMOTHWARRMODs(MODEL chwProduct,
														  WARRELEMENTTYPE WARRELEMENT,Connection pdhConnection) throws SQLException {
		List<ZYTMDMOTHWARRMOD> list = new ArrayList<>();
		String GETCOUNTYNAME = "select GENAREANAME_FC,GENAREACODE from price.generalarea  where GENAREACODE in (select GENAREACODE from price.generalarea) and NLSID = 1 and ISACTIVE = 1 WITH UR";
		Set<String> countrySet = new HashSet<>();
		Hashtable<String,String> countryName = new Hashtable<>();
		Statement statement = pdhConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(GETCOUNTYNAME);
		while(resultSet.next()) {
			countryName.put(resultSet.getString("GENAREANAME_FC").trim(),resultSet.getString("GENAREACODE").trim());
		}
		statement.close();
		resultSet.close();
		for (COUNTRY country:WARRELEMENT.getCOUNTRYLIST()) {
			if(existInExcludedCountryList(country.getCOUNTRY_FC()))
				continue;
			ZYTMDMOTHWARRMOD zYTMDMOTHWARRMOD = new ZYTMDMOTHWARRMOD();
			zYTMDMOTHWARRMOD.setZMACHTYP(chwProduct.getMACHTYPE());
			zYTMDMOTHWARRMOD.setZMODEL(chwProduct.getMODEL());
			//Set ZINVNAME =left(chwProduct/MKTGNAME,40) where NLSID = 1;
			for (LANGUAGE languageElement : chwProduct.getLANGUAGELIST()) {
				if ("1".equals(languageElement.getNLSID())) {
					zYTMDMOTHWARRMOD.setZINVNAME(CommonUtils.getFirstSubString(languageElement.getMKTGNAME(), 40));
					break;
				}
			}

			zYTMDMOTHWARRMOD.setZCOUNTRY(countryName.get(country.getCOUNTRY_FC()));
			//Copy from chwProduct/WARRLIST/WARRELEMENT/WARRID
			zYTMDMOTHWARRMOD.setZWRTYID(WARRELEMENT.getWARRID());
			zYTMDMOTHWARRMOD.setZWTYDESC(CommonUtils.getFirstSubString(WARRELEMENT.getWARRDESC(), 40));
			//If chwProduct/WARRLIST/WARRELEMENT/PUBFROM is null, then
			// set it to "19800101".
			// Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBFROM. (Format as YYYYMMDD)
			String ZPUBFROM = "";
			String sPUBFROM = WARRELEMENT.getPUBFROM();
			if (sPUBFROM == null || "".equals(sPUBFROM)) {
				ZPUBFROM = "19800101";
			} else {
				ZPUBFROM = sPUBFROM.replaceAll("-", "");
			}
			zYTMDMOTHWARRMOD.setZPUBFROM(ZPUBFROM);
			//If chwProduct/WARRLIST/WARRELEMENT/PUBTO is null, then
			// set it to "99991231".
			// Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBTO. (Format as YYYYMMDD)
			String ZPUBTO = "";
			String sPUBTO = WARRELEMENT.getPUBTO();
			if (sPUBTO == null || "".equals(sPUBTO)) {
				ZPUBTO = "99991231";
			} else {
				ZPUBTO = sPUBTO.replaceAll("-", "");
			}
			zYTMDMOTHWARRMOD.setZPUBTO(ZPUBTO);
			//Set to first character of chwProduct/WARRLIST/WARRELEMENT/WARRACTION
			zYTMDMOTHWARRMOD.setZWARR_FLAG(CommonUtils.getFirstSubString(WARRELEMENT.getWARRACTION(), 1));
			//Default to ""

			if ("Yes".equalsIgnoreCase(WARRELEMENT.getDEFWARR())) {
				zYTMDMOTHWARRMOD.setZCOUNTRY_FLAG("");
			} else {
				COUNTRY c = WARRELEMENT.getCOUNTRYLIST().get(0);
				String COUNTRYACTION = c.getCOUNTRYACTION();
				zYTMDMOTHWARRMOD.setZCOUNTRY_FLAG(CommonUtils.getFirstSubString(COUNTRYACTION, 1));
			}

			list.add(zYTMDMOTHWARRMOD);
		}

		return list;
	}

	private boolean existInExcludedCountryList(String countryFc) {
		boolean existInExcludedCountries=false;
		for (int i=0;i<excludedCountryList.size();i++){
			if(countryFc.equals(excludedCountryList.get(i)))
				existInExcludedCountries=true;
		}
		return existInExcludedCountries;
	}

	public ChwYMdmOthWarranty(TMF_UPDATE chwProduct, String attributevalue) {
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
			ZYTMDMOTHWARRTMF.setZWTYDESC("");
			ZYTMDMOTHWARRTMF.setZPUBFROM("00000000");
			ZYTMDMOTHWARRTMF.setZPUBTO("00000000");
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
						//Set ZINVNAME =left(FEATURE/MKTGNAME,40) where NLSID = 1 and FEATURE is derived from TMF
						ZYTMDMOTHWARRTMF.setZINVNAME(CommonUtils.getFirstSubString(attributevalue, 40));
						ZYTMDMOTHWARRTMF.setZCOUNTRY("**");
						//Copy from chwProduct/WARRLIST/WARRELEMENT/WARRID
						ZYTMDMOTHWARRTMF.setZWRTYID(WARRELEMENT.getWARRID());
						ZYTMDMOTHWARRTMF.setZWTYDESC(CommonUtils.getFirstSubString(WARRELEMENT.getWARRDESC(),40));
						/**
						 * If chwProduct/WARRLIST/WARRELEMENT/PUBFROM is null, then
						 set it to "19800101".
						 Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBFROM. (Format as YYYYMMDD)
						 */
						String ZPUBFROM ="";
						String sPUBFROM = WARRELEMENT.getPUBFROM();
						if(sPUBFROM==null || "".equals(sPUBFROM)){
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
						if(sPUBTO==null || "".equals(sPUBTO)){
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

				}else {
					if("WTY0000".equalsIgnoreCase(WARRELEMENT.getWARRID())){
						COUNTRYELEMENT_TMF country = WARRELEMENT.getCOUNTRYLIST().get(0);
						setZYTMDMOTHWARRTMF(chwProduct, WARRELEMENT, country, attributevalue);
					}else {
						for(COUNTRYELEMENT_TMF country: WARRELEMENT.getCOUNTRYLIST()){
							setZYTMDMOTHWARRTMF(chwProduct, WARRELEMENT, country, attributevalue);
						}
					}

				}
			}

		}




	}

	private void setZYTMDMOTHWARRTMF(TMF_UPDATE chwProduct, WARRELEMENT_TMF WARRELEMENT, COUNTRYELEMENT_TMF country,String attributevalue) {
		ZYTMDMOTHWARRTMF ZYTMDMOTHWARRTMF = new ZYTMDMOTHWARRTMF();
		//Copy from chwProduct/MACHTYPE
		ZYTMDMOTHWARRTMF.setZMACHTYP(chwProduct.getMACHTYPE());
		//Copy from chwProduct/FEATURECODE
		ZYTMDMOTHWARRTMF.setZFEATURECODE(chwProduct.getFEATURECODE());
		//Set ZINVNAME =left(FEATURE/MKTGNAME,40) where NLSID = 1 and FEATURE is derived from TMF
		ZYTMDMOTHWARRTMF.setZINVNAME(CommonUtils.getFirstSubString(attributevalue, 40));
		/**
		 * If chwProduct/WARRLIST/WARRELEMENT/WARRID = "WTY0000", then set it to "**".
		 Else set it to GENAREACODE.
		 select GENAREACODE from GENERALAREA_UPDATE_CBSE G where G.GENAREANAME_FC =
		 chwProduct/WARRLIST/WARRELEMENT/COUNTRYLIST/COUNTRYELEMENT/COUNTRY_FC.
		 */
		String ZCOUNTRY ="";
		if("WTY0000".equalsIgnoreCase(WARRELEMENT.getWARRID()) || country==null){
			ZCOUNTRY = "**";
			ZYTMDMOTHWARRTMF.setZCOUNTRY(ZCOUNTRY);
		}else{
			ZCOUNTRY = country.getCOUNTRY_FC();
			String GENAREANAME_FC = RFCConfig.getAland(ZCOUNTRY);
			if(GENAREANAME_FC==null || "".equals(GENAREANAME_FC)){
				return;
			}
			ZYTMDMOTHWARRTMF.setZCOUNTRY(GENAREANAME_FC);
		}
		//ZYTMDMOTHWARRTMF.setZCOUNTRY(RfcConfigProperties.getCountry(ZCOUNTRY));
		//Copy from chwProduct/WARRLIST/WARRELEMENT/WARRID
		ZYTMDMOTHWARRTMF.setZWRTYID(WARRELEMENT.getWARRID());
		ZYTMDMOTHWARRTMF.setZWTYDESC(CommonUtils.getFirstSubString(WARRELEMENT.getWARRDESC(),40));
		/**
		 * If chwProduct/WARRLIST/WARRELEMENT/PUBFROM is null, then
		 set it to "19800101".
		 Else set it to chwProduct/WARRLIST/WARRELEMENT/PUBFROM. (Format as YYYYMMDD)
		 */
		String ZPUBFROM ="";
		String sPUBFROM = WARRELEMENT.getPUBFROM();
		if(sPUBFROM==null || "".equals(sPUBFROM)){
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
		if(sPUBTO==null || "".equals(sPUBTO)){
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

	public List<ZYTMDMOTHWARRUPD> getZYTMDMOTHWARRUPD_LIST() {
		return ZYTMDMOTHWARRUPD_LIST;
	}

	public void setZYTMDMOTHWARRUPD_LIST(List<ZYTMDMOTHWARRUPD> zYTMDMOTHWARRUPD_LIST) {
		ZYTMDMOTHWARRUPD_LIST = zYTMDMOTHWARRUPD_LIST;
	}

	public List<ZYTMDMOTHWARRMOD> getZYTMDMOTHWARRMOD_LIST() {
		return ZYTMDMOTHWARRMOD_LIST;
	}

	public void setZYTMDMOTHWARRMOD_LIST(List<ZYTMDMOTHWARRMOD> zYTMDMOTHWARRMOD_LIST) {
		ZYTMDMOTHWARRMOD_LIST = zYTMDMOTHWARRMOD_LIST;
	}

	public List<ZYTMDMOTHWARRTMF> getZYTMDMOTHWARRTMF_LIST() {
		return ZYTMDMOTHWARRTMF_LIST;
	}

	public void setZYTMDMOTHWARRTMF_LIST(List<ZYTMDMOTHWARRTMF> zYTMDMOTHWARRTMF_LIST) {
		ZYTMDMOTHWARRTMF_LIST = zYTMDMOTHWARRTMF_LIST;
	}


}
