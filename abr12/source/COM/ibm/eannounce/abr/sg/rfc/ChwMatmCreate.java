/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import COM.ibm.eannounce.abr.sg.rfc.entity.CountryPlantTax;
import COM.ibm.eannounce.abr.sg.rfc.entity.Generalarea;
import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmm00;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh1;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh2;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh5;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh6;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh7;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_geo;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_plant;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_sales_org;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_tax_country;
import COM.ibm.eannounce.abr.util.DateUtility;
import COM.ibm.eannounce.abr.util.RFCConfig;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;

import com.google.gson.annotations.SerializedName;

/**
 * ChwMatmCreate.
 * 
 * @author wangyul
 *
 */
public class ChwMatmCreate extends RdhBase {
	@SerializedName("TYPE")
	private String type;
	@SerializedName("BMM00")
	private List<RdhMatm_bmm00> bmm00;
	@SerializedName("BMMH1")
	private List<RdhMatm_bmmh1> bmmh1;
	@SerializedName("BMMH2")
	private List<RdhMatm_bmmh2> bmmh2;
	@SerializedName("BMMH5")
	private List<RdhMatm_bmmh5> bmmh5;
	@SerializedName("BMMH6")
	private List<RdhMatm_bmmh6> bmmh6;
	@SerializedName("BMMH7")
	private List<RdhMatm_bmmh7> bmmh7;
	@SerializedName("GEO")
	private List<RdhMatm_geo> geos;
	@SerializedName("IS_MULTI_PLANTS")
	private String is_multi_plants;
	@Foo
	SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
	public ChwMatmCreate(MODEL model, String materialType,String materialID) {
		super(materialID, "Z_DM_SAP_MATM_CREATE".toLowerCase(), null);
		// RdhMatm_bmm00 matnr Copy from <SoftwareProduct.productIdentifier>.
		pims_identity="H";
		bmm00.get(0).setMatnr(materialID);
		bmm00.get(0).setMtart(materialType);
		bmm00.get(0).setMbrsh("M");
		//bmm00.get(0).setWerks("00");
		bmm00.get(0).setVtweg("00");

		bmmh1.get(0).setMatkl("000");
		bmmh1.get(0).setMeins("EA");
		//If <materialID> like "%UPG", then copy from MODEL_UPDATE/AVAILABILITYLIST/AVAILABILITYELEMENT/ANNNUMBER.
		//Else set to blank.
		for(int i=0;i<model.getAVAILABILITYLIST().size();i++) {
			String ann = model.getAVAILABILITYLIST().get(i).getANNNUMBER();
			if(ann!=null&&ann.trim().length()>0) {
				bmmh1.get(0).setZeinr(ann);
				break;
			}
			
		}if(bmmh1.get(0).getZeinr()==null) {
			bmmh1.get(0).setZeinr("");
			bmmh1.get(0).setZeiar("");
		}
		else {
			bmmh1.get(0).setZeiar("RFA");
		}
		if(materialID.endsWith("UPG")) {
			bmmh1.get(0).setStprs("0.00");
		}
		else {
			bmmh1.get(0).setStprs("0.01");

		}
		
		
		/*
		 * If <materialID> like "%UPG", then If there is a value in <ANNNUMBER>, set to
		 * "RFA". else if MODEL_UPDATE/PRPQINDC = "Yes", load "SPB" else set to blank.
		 * Else set to blank.
		 */
		//bmmh1.get(0).setZeiar("RFA");

		bmmh1.get(0).setAeszn(getEarliestAnnDate(model));
		bmmh1.get(0).setGewei("KG");
		bmmh1.get(0).setTragr("0001");
		bmmh1.get(0).setSpart("00");
		//prdha
		//bmmh1.get(0).setPrdha(model.getPRODHIERCD());
		bmmh1.get(0).setMaabc("A");
		bmmh1.get(0).setEkgrp("ZZZ");
		bmmh1.get(0).setDismm("PD");
		bmmh1.get(0).setDispo("000");
		bmmh1.get(0).setPerkz("M");
		bmmh1.get(0).setDisls("EX");
		bmmh1.get(0).setBeskz("X");
		bmmh1.get(0).setSbdkz("1");
		bmmh1.get(0).setFhori("000");
		//If MODEL_UPDATE/CATEGORY='Hardware', then set ladgr = "H001";
		if("Hardware".equals(model.getCATEGORY()))
		{
			bmmh1.get(0).setLadgr("H001");
			if("ZMAT".equals(materialType))
			{
				bmmh1.get(0).setXchpf("X");
			}
			
		}
		bmmh1.get(0).setMtvfp("ZE");
		bmmh1.get(0).setKautb("X");
		bmmh1.get(0).setPrctr(model.getPRFTCTR());
		bmmh1.get(0).setFxhor("0");
		if("ZMAT".equals(materialType))
		{
			bmmh1.get(0).setDisgr("Z025");
			bmmh1.get(0).setVprsv("V");
			bmmh1.get(0).setBklas("7920");
			bmmh1.get(0).setVmvpr("V");
			bmmh1.get(0).setVmbkl("7920");
			bmmh1.get(0).setVjvpr("V");
			bmmh1.get(0).setVjbkl("7920");
			
		}
		else if ("ZPRT".equals(materialType)) {
			bmmh1.get(0).setDisgr("Z010");
			bmmh1.get(0).setVprsv("S");
			bmmh1.get(0).setBklas("7900");
			bmmh1.get(0).setVmvpr("S");
			bmmh1.get(0).setVmbkl("7900");
			bmmh1.get(0).setVjvpr("S");
			bmmh1.get(0).setVjbkl("7900");

		}
		//Todo
		if(materialID.endsWith("NEW")) {
			if("LBS".equals(model.getPHANTOMMODINDC())||"'6661', '6662', '6663', '6664', '6665', '6668', '6669'".contains(model.getMACHTYPE()
					)||"Storage Tier','STORAGE TIER','storage tier'".contains(model.getSUBGROUP())) {
				bmmh1.get(0).setSernp("NONE");
			}else if ("'2063', '2068', '2059', '2057', '2058'".contains(model.getMACHTYPE())) {
				bmmh1.get(0).setSernp("GG01");
			}else {
				bmmh1.get(0).setSernp("ZZ06");
			}
		}else if (materialID.endsWith("MTC")||materialID.endsWith("UPG")) {
			bmmh1.get(0).setSernp("ZZ06");
		}
		else if ("ZPRT".equals(materialType)) {
			bmmh1.get(0).setSernp("NONE");
		}
		bmmh1.get(0).setPeinh("1");
		if("Hardware".equals(model.getCATEGORY())&&"ZMAT".equals(materialType)){
			bmmh1.get(0).setBwtty("X");
		}
		//bmmh1.get(0).bwtty ("V");?
		bmmh1.get(0).setVersg("1");
		bmmh1.get(0).setSktof("X");
		bmmh1.get(0).setAumng("1");
		bmmh1.get(0).setLfmng("1");
		bmmh1.get(0).setScmng("1");
		//todo
		bmmh1.get(0).setMtpos(getMtpos(model));
		bmmh1.get(0).setProdh(model.getPRODHIERCD());
		
		if (materialID.endsWith("NEW")) {
			bmmh1.get(0).setKtgrm("01");
		}else if (materialID.endsWith("UPG")||materialID.endsWith("MTC")) {
			bmmh1.get(0).setKtgrm("29");

		}
		else if ("ZPRT".equals(materialType)) {
			bmmh1.get(0).setKtgrm(model.getACCTASGNGRP());

		}
		

		List<TAXCODE> taxcodes = model.getTAXCODELIST();
		if (taxcodes != null && taxcodes.size() > 0) {
			bmmh1.get(0).setMvgr5(taxcodes.get(0).getTAXCODE());
		}
		//bmmh1.get(0).setXeib1("X");
		// bmmh1.get(0).setp;
		 if ("ZMAT".equals(materialType))
		 {
			bmmh1.get(0).setKzkfg("X");
			if("Y".equals(model.getDEFAULTCUSTOMIZEABLE())) {
				bmmh1.get(0).setZconf("E");
			}
			else if("N".equals(model.getDEFAULTCUSTOMIZEABLE())) {
				bmmh1.get(0).setZconf("F");
			}
			
		}
		 bmm00.get(0).setXeib1("X");
		 bmm00.get(0).setXeid1("X");
		 bmm00.get(0).setXeik1("X");
		 bmm00.get(0).setXeiv1("X");
		 bmm00.get(0).setXeie1("X");
		List<LANGUAGE> languages = model.getLANGUAGELIST();
		if (languages != null && languages.size() > 0) {
			String nlsid = languages.get(0).getNLSID();
			if("1".equals(nlsid)) {
				bmmh5.get(0).setSpras("E");
			}else if ("2".equals(nlsid)) {
				bmmh5.get(0).setSpras("D");
			}
			else if ("11".equals(nlsid)) {
				bmmh5.get(0).setSpras("1");
			}
			
			if (materialID.endsWith("NEW")) {
				bmmh5.get(0).setMaktx("MACHINE TYPE "+model.getMACHTYPE()+" - Model NEW");
				bmmh5.get(0).setTdline("MACHINE TYPE "+model.getMACHTYPE()+" - Model NEW");

			}
			else if (materialID.endsWith("UPG")) {
				bmmh5.get(0).setMaktx("MACHINE TYPE "+model.getMACHTYPE()+" - Model UPG");
				bmmh5.get(0).setTdline("MACHINE TYPE "+model.getMACHTYPE()+" - Model UPG");
			}else if (materialID.endsWith("MTC")) {
				bmmh5.get(0).setMaktx("MACHINE TYPE "+model.getMACHTYPE()+" - Model MTC");
				bmmh5.get(0).setTdline("MACHINE TYPE "+model.getMACHTYPE()+" - Model MTC");
			}else {
				bmmh5.get(0).setMaktx(languages.get(0).getINVNAME());
				bmmh5.get(0).setTdline(languages.get(0).getMKTGNAME());

			}
		} 
		List<AVAILABILITY> availabilities = model.getAVAILABILITYLIST();
		List<TAXCATEGORY> taxcategories = model.getTAXCATEGORYLIST();

		RdhMatm_geo geo = new RdhMatm_geo();
		geo.setName("WW1");
		geo.setVmsta("Z0");
		if(materialID.endsWith("UPG")) {
			String pubfrom = DateUtility.getTodayStringWithSapFormat();
			System.out.println("pubfrom=" + pubfrom);
			geo.setVmstd(pubfrom);
		}else {
			geo.setVmstd(this.getEarliestPUBFROM(model));			
			//geo.setVmstd(pubfrom.replace("-", ""));
		}
		
		Set<SLEORGNPLNTCODE> sset  = new HashSet<SLEORGNPLNTCODE>();
		Set<TAXCATEGORY> cset = new HashSet<TAXCATEGORY>();
		Set<String> slorgSet = new HashSet<String>();
		Set<String> plntSet = new HashSet<String>();
		List<RdhMatm_sales_org> sales_orgList = new ArrayList<RdhMatm_sales_org>();
		List<RdhMatm_plant> plantList = new ArrayList<RdhMatm_plant>();
		Set<String> set = new HashSet<String>();

		/*if (availabilities != null && availabilities.size() > 0) {

			for (int i = 0; i < availabilities.size(); i++) {
				
				List<SLEORGNPLNTCODE> list = availabilities.get(i).getSLEORGNPLNTCODELIST();
				for (int j = 0; j < list.size(); j++) {
					slorgSet.add(list.get(j).getSLEORG());
					plntSet.add(list.get(j).getPLNTCD());
				}
			}
		}
		
		Iterator<String> pIterator = plntSet.iterator();
		while (pIterator.hasNext()) {
			String plntcd = (String) pIterator.next();
			
			RdhMatm_plant plant = new RdhMatm_plant();
			plant.setWerks(plntcd);
			plant.setEkgrp("ZZZ");
			plantList.add(plant);
		}
		Iterator<String> slorgIterator = slorgSet.iterator();
		while (slorgIterator.hasNext()) {
			String slorg = (String) slorgIterator.next();
			
			RdhMatm_sales_org sales_org = new RdhMatm_sales_org();
			sales_org.setVkorg(slorg);
			sales_org.setDwerk(slorg);
			if(RFCConfig.getDwerk("2",sales_org.getVkorg())!=null)
			{
				sales_org.setDwerk(RFCConfig.getDwerk("2",sales_org.getVkorg()));
				
				if("0147".equals(sales_org.getVkorg())){
					sales_org.setZtaxclsf("1");
					sales_org.setZsabrtax("01");
				}else if ("0026".equals(sales_org.getVkorg())) {
					sales_org.setZsabrtax("HW");
				}
				if(!set.contains(sales_org.getVkorg()+sales_org.getDwerk()))
				{
					sales_orgList.add(sales_org);
					set.add(sales_org.getVkorg()+sales_org.getDwerk());
				}
			}
		}*/
		
		List<RdhMatm_tax_country> tax_countries = new  ArrayList<RdhMatm_tax_country>();
		List<CountryPlantTax> taxs = RFCConfig.getTaxs();
		List<Generalarea> generalareas = RFCConfig.getGeneralareas();
		Set<String> plntcdtSet = new HashSet<String>();
		Set<String> saleorgSet = new HashSet<String>();

		for (int i = 0; i < taxs.size(); i++) {
			
			CountryPlantTax tax = taxs.get(i);
			if(!"2".equals(tax.getINTERFACE_ID()))
				continue;
			String plntcd = tax.getPLNT_CD();
			String salesorg = tax.getSALES_ORG();
			if(!plntcdtSet.contains(plntcd)) {
				plntcdtSet.add(plntcd);
				RdhMatm_plant plant = new RdhMatm_plant();
				plant.setWerks(plntcd);
				plant.setEkgrp("ZZZ");
				plantList.add(plant);
			}
			if(!saleorgSet.contains(salesorg)) {
				saleorgSet.add(salesorg);
				RdhMatm_sales_org sales_org = new RdhMatm_sales_org();
				sales_org.setVkorg(salesorg);
				sales_org.setDwerk(salesorg);
				if(RFCConfig.getDwerk("2",sales_org.getVkorg())!=null)
				{
					sales_org.setDwerk(RFCConfig.getDwerk("2",sales_org.getVkorg()));
					
					if("0147".equals(sales_org.getVkorg())){
						sales_org.setZtaxclsf("1");
						sales_org.setZsabrtax("01");
					}else if ("0026".equals(sales_org.getVkorg())) {
						sales_org.setZsabrtax("HW");
					}
					if(!set.contains(sales_org.getVkorg()+sales_org.getDwerk()))
					{
						sales_orgList.add(sales_org);
						set.add(sales_org.getVkorg()+sales_org.getDwerk());
					}
				}
			}
			
		}
		
		if(materialID.endsWith("UPG")||materialID.endsWith("MTC")) {
		for (int i = 0; i < taxs.size(); i++) {
			CountryPlantTax tax = taxs.get(i);
			
			if("2".equals(tax.getINTERFACE_ID())) {
				RdhMatm_tax_country tax_country = new RdhMatm_tax_country();
				tax_country.setAland(tax.getTAX_COUNTRY());
				tax_country.setTaty1(tax.getTAX_CAT());
				tax_country.setTaxm1(tax.getTAX_CLAS());
				tax_country.setTaxm2(tax.getTAX_CLAS());
				tax_country.setTaxm3(tax.getTAX_CLAS());
				tax_country.setTaxm4(tax.getTAX_CLAS());
				tax_country.setTaxm5(tax.getTAX_CLAS());
				tax_country.setTaxm6(tax.getTAX_CLAS());
				tax_country.setTaxm7(tax.getTAX_CLAS());
				tax_country.setTaxm8(tax.getTAX_CLAS());
				tax_country.setTaxm9(tax.getTAX_CLAS());

				if(tax_country.getAland()!=null) {
				tax_countries.add(tax_country);
				}
				
			}
		}
		}else if (materialID.endsWith("UPG")||"ZPRT".equals(type)) {
			Set<String> countrySet = new HashSet<String>();
			
			for (int i = 0; i <taxcategories.size(); i++) {
				TAXCATEGORY taxcategory = taxcategories.get(i);
				List<COUNTRY> countrys = taxcategory.getCOUNTRYLIST();
				for (int j = 0; j < countrys.size(); j++) {
					COUNTRY country = countrys.get(j);
					if(!countrySet.contains(country.getCOUNTRY_FC())) {
						countrySet.add(country.getCOUNTRY_FC());
						taxcategory.getTAXCLASSIFICATION();
						
						RdhMatm_tax_country tax_country = new RdhMatm_tax_country();
						tax_country.setAland(RFCConfig.getAland(country.getCOUNTRY_FC()));
						tax_country.setTaty1(taxcategory.getTAXCATEGORYVALUE());
						tax_country.setTaxm1(taxcategory.getTAXCLASSIFICATION());
						tax_country.setTaxm2(taxcategory.getTAXCLASSIFICATION());
						tax_country.setTaxm3(taxcategory.getTAXCLASSIFICATION());
						tax_country.setTaxm4(taxcategory.getTAXCLASSIFICATION());
						tax_country.setTaxm5(taxcategory.getTAXCLASSIFICATION());
						tax_country.setTaxm6(taxcategory.getTAXCLASSIFICATION());
						tax_country.setTaxm7(taxcategory.getTAXCLASSIFICATION());
						tax_country.setTaxm8(taxcategory.getTAXCLASSIFICATION());
						tax_country.setTaxm9(taxcategory.getTAXCLASSIFICATION());

						if(tax_country.getAland()!=null) {
						tax_countries.add(tax_country);
						}
					}
					
				}
				
				
			}
		}
		
				geo.setPlants(plantList);
				geo.setSales_orgs(sales_orgList);
				geo.setTax_countries(tax_countries);
				geos.add(geo);

			

			

		
	

	bmm00.get(0).setTcode("MM01");
	
	}

	private String getZsabrtax(String ztaxclsf) {
		// TODO Auto-generated method stub
		return RfcConfigProperties.getZsabrtaxPropertys(ztaxclsf);
	}

	private String getZtaxclsf(SVCMOD svcmod, String country) {
		List<TAXCATEGORY> taxs = svcmod.getTAXCATEGORYLIST();
		if (taxs != null && taxs.size() > 0) {
			for (int i = 0; i < taxs.size(); i++) {
				List<SLEORGGRP> list = taxs.get(i).getSLEORGGRPLIST();
				if (list != null && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						if (country.equals(list.get(j).getSLEORGGRP())) {
							return taxs.get(i).getTAXCLASSIFICATION();
						}
					}
				}
			}
		}
		return null;

	}

	private String getMtpos(MODEL model) {
		// TODO Auto-generated method stub
		String result = "";
		String var = model.getPHANTOMMODINDC()==null?"":model.getPHANTOMMODINDC().toUpperCase();
		String materialID = bmm00.get(0).getMatnr();
		
		if(materialID.endsWith("NEW")) {
			if ("NORM".equals(var)) {
				result="ZPT1";
			}else if ("REACH".equals(var)) {
				result ="ZPT2";
			}
		else if ("'6661', '6662', '6663', '6664', '6665', '6668', '6669', '4586', '4663', '4665', '4673', '9255', '9601', '9665')".contains(model.getMACHTYPE())) {
			result="ZPT4";
		}
			else if ("LBS".equals(var)) {
				result="ZPT3";
			}
			else {
				result="Z002";
			}
			if ("P1030".equals(model.getPRFTCTR())) {
				result="ZHCR";
			}
		}else if (materialID.endsWith("UPG")||materialID.endsWith("MTC")) {
			if ("NORM".equals(var)) {
				result="ZPT1";
			}else if ("REACH".equals(var)) {
				result ="ZPT2";
			}
			else if ("LBS".equals(var)) {
				result="ZPT3";
			}
			else {
				result="Z002";
			}	
		}
		else if("ZPRT".equals(bmm00.get(0).getMtart())) {
		   if("Hardware".equals(model.getCATEGORY())) {
			   result ="ZSUP";
		   }
		}
		
		return result;
	}

	public String getMtart(SVCMOD svcmod) {
		String mtart = "ZSV1";
		if (svcmod == null)
			return mtart;
		if ("Service".equals(svcmod.getCATEGORY())) {
			if ("Custom".equals(svcmod.getSUBCATEGORY())) {
				mtart = "ZSV1";
			} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
				mtart = "ZSV2";
			} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())
					&& "Non-Federated".equals(svcmod.getGROUP())) {
				mtart = "ZSV5";
			}
		} else if ("IP".equals(svcmod.getCATEGORY()) && "SC".equals(svcmod.getSUBCATEGORY())) {
			mtart = "ZSV1";
		}
		return mtart;
	}

	public String getMeins(SVCMOD svcmod) {
		String reslut = "EA";
		if (svcmod == null)
			return reslut;
		if ("Service".equals(svcmod.getCATEGORY())) {
			if ("Custom".equals(svcmod.getSUBCATEGORY())) {
				reslut = "EA";
			} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
				reslut = "EA";
			} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())
					&& "Non-Federated".equals(svcmod.getGROUP())) {
				reslut = "EA";
			}
		} else if ("IP".equals(svcmod.getCATEGORY()) && "SC".equals(svcmod.getSUBCATEGORY())) {
			reslut = "EA";
		}
		return reslut;
	}

	/*
	 * public RdhMatmCreate(ProductSchedule productSchedule, SoftwareProduct
	 * softwareProduct, LicensedFunction licensedFunction) {
	 * super(productSchedule.getMainProduct().getProductIdentifier() + "_" +
	 * productSchedule.getId(), "Z_DM_SAP_MATM_CREATE".toLowerCase(),
	 * softwareProduct.getEnablementProcess().getKwValue()); this.type = "ZZEE"; try
	 * { RdhClassDefaultValuesUtility.setDefaultValues(this, type); } catch
	 * (Exception e) { this.setRfcrc(8);
	 * this.setError_text("Get Default value  - Exception " + e.getMessage()); }
	 * bmm00.get(0).setMatnr(licensedFunction.getMaterialID());
	 * bmmh1.get(0).setSpart(softwareProduct.getOwningDivision().getName());
	 * if(softwareProduct.getVendorLogoAAG() != null) {
	 * bmmh1.get(0).setKtgrm(softwareProduct
	 * .getVendorLogoAAG().getEswAccountAssignGroup()); }
	 * bmmh5.get(0).setSpras("E");
	 * bmmh5.get(0).setMaktx(licensedFunction.getShortName());
	 * 
	 * setGeoSalesOrgCountry(softwareProduct,licensedFunction,null,null); }
	 * 
	 * public RdhMatmCreate(ProductSchedule productSchedule, SoftwareProduct
	 * softwareProduct, OrderableSupply orderableSupply, String orderableSupplyName)
	 * { super(productSchedule.getMainProduct().getProductIdentifier() + "_" +
	 * productSchedule.getId(), "Z_DM_SAP_MATM_CREATE".toLowerCase(),
	 * softwareProduct.getEnablementProcess().getKwValue()); this.type = "ZOSP"; try
	 * { RdhClassDefaultValuesUtility.setDefaultValues(this, type); } catch
	 * (Exception e) { this.setRfcrc(8);
	 * this.setError_text("Get Default value  - Exception " + e.getMessage()); }
	 * bmm00.get(0).setMatnr(orderableSupply.getMaterialId());
	 * bmmh1.get(0).setSpart(softwareProduct.getOwningDivision().getName());
	 * bmmh5.get(0).setSpras("E"); bmmh5.get(0).setMaktx(orderableSupplyName);
	 * setGeoSalesOrgCountry(softwareProduct,null,orderableSupply, null); }
	 * 
	 * public RdhMatmCreate(ProductSchedule productSchedule, SoftwareProduct
	 * softwareProduct, SliceGroupEsw sliceGroupEsw){
	 * super(productSchedule.getMainProduct().getProductIdentifier() + "_" +
	 * productSchedule.getId(), "Z_DM_SAP_MATM_CREATE".toLowerCase(),
	 * softwareProduct.getEnablementProcess().getKwValue()); this.type = "ZGRP"; try
	 * { RdhClassDefaultValuesUtility.setDefaultValues(this, type); } catch
	 * (Exception e) { this.setRfcrc(8);
	 * this.setError_text("Get Default value  - Exception " + e.getMessage()); }
	 * bmm00.get(0).setMatnr(sliceGroupEsw.getMaterialId());
	 * bmmh1.get(0).setSpart(softwareProduct.getOwningDivision().getName());
	 * bmmh5.get(0).setSpras("E"); String srel = ""; if(srel != null) { srel =
	 * " SREL" + ("DBS".equals(sliceGroupEsw.getSRel()) ? "DB" :
	 * sliceGroupEsw.getSRel()); }
	 * 
	 * String language = sliceGroupEsw.getLanguageName() != null ? " " +
	 * sliceGroupEsw.getLanguageName() : ""; String mediaTypeCdsId =
	 * sliceGroupEsw.getMediaTypeCdsId() != null ? " " +
	 * sliceGroupEsw.getMediaTypeCdsId().substring(0, 3) +
	 * sliceGroupEsw.getMediaTypeCdsId().substring(8, 11) : "";
	 * bmmh5.get(0).setMaktx("Grp for" + srel + language + mediaTypeCdsId);
	 * setGeoSalesOrgCountry(softwareProduct, null, null, sliceGroupEsw);
	 * 
	 * }
	 * 
	 * 
	 * private void setGeoSalesOrgCountry(SoftwareProduct
	 * softwareProduct,LicensedFunction licensedFunction, OrderableSupply
	 * orderableSupply, SliceGroupEsw sliceGroupEsw) { geos.clear(); String
	 * sapFeedId = "ESW_FEED_ALL";//default for esw -
	 * <SoftwareProduct.enablementProcess> = "SWPIMS" List<SapGeo> sapGeos =
	 * softwareProduct.getSapGeoObjects(); if(this.isAas(softwareProduct)) {
	 * if(softwareProduct.getIsSwmaOffered()) { sapFeedId = "CSW_FEED_IERP_SWMA";
	 * }else { sapFeedId = "CSW_FEED_IERP_LIC"; } sapGeos =
	 * softwareProduct.getSapGeoObjectsBasedOnSapFeedId(sapFeedId); }
	 * 
	 * for(SapGeo sapGeo : sapGeos) { boolean isSetSalesStatus = false;
	 * List<GeoAvailStatus> availStatuses = null;// for different case, will get the
	 * geoavailstatus by different way if(licensedFunction != null) { availStatuses
	 * = licensedFunction.getGeoAvailStatusObjects(); } else if(orderableSupply
	 * !=null) { availStatuses = orderableSupply.getGeoAvailStatusObjects(); } else
	 * if(sliceGroupEsw !=null) { availStatuses =
	 * softwareProduct.getGeoAvailStatusObjects(); } else { availStatuses =
	 * softwareProduct.getGeoAvailStatusObjects(); isSetSalesStatus = true; }
	 * if(availStatuses == null || availStatuses.isEmpty()) { continue; } for
	 * (GeoAvailStatus availStatus : availStatuses) { if
	 * (availStatus.getBusinessGeo().getKwValue().equals(sapGeo
	 * .getBusinessGeo().getKwValue())) { Date withdrawMarketingDate =
	 * softwareProduct.getWithdrawDateAllOSPs() != null ?
	 * softwareProduct.getWithdrawDateAllOSPs() :
	 * availStatus.getWithdrawMarketingDate(); Date announcementDate =
	 * availStatus.getAnnouncementDate(); RdhMatm_geo geo =
	 * generateGeoStructure(softwareProduct, orderableSupply, sliceGroupEsw, sapGeo,
	 * isSetSalesStatus, announcementDate, withdrawMarketingDate); geos.add(geo); }
	 * 
	 * } if("WW".equals(sapGeo.getBusinessGeo().getKwValue())) //no matched GAS for
	 * WW { RdhMatm_geo geo = generateGeoStructure(softwareProduct, orderableSupply,
	 * sliceGroupEsw, sapGeo, isSetSalesStatus,
	 * this.getEarliestAnnDate(softwareProduct),null); geos.add(geo); } } }
	 * 
	 * private RdhMatm_geo generateGeoStructure(SoftwareProduct softwareProduct,
	 * OrderableSupply orderableSupply, SliceGroupEsw sliceGroupEsw, SapGeo sapGeo,
	 * boolean isSetSalesStatus, Date annDate, Date wdDate) { RdhMatm_geo geo = new
	 * RdhMatm_geo();
	 * 
	 * geo.setName(sapGeo.getBusinessGeo().getKwValue());//Copy from
	 * <SapGeo.getBusinessGeo().value>. if(isSetSalesStatus) {
	 * 
	 * if(this.isSWPIMs(softwareProduct)) { if(wdDate != null &&
	 * !DateUtility.isAfterToday(wdDate)) { geo.setVmsta("ZJ"); }else {
	 * geo.setVmsta("Z0"); } if(wdDate != null && !DateUtility.isAfterToday(wdDate))
	 * { geo.setVmstd(DateUtility.getDateStringWithSapFormat(wdDate)); } else
	 * if(annDate != null && DateUtility.isBeforeToday(annDate)) {
	 * geo.setVmstd(DateUtility.getDateStringWithSapFormat(annDate)); } else {
	 * geo.setVmstd(DateUtility.getTodayStringWithSapFormat()); } }else
	 * if(this.isAas(softwareProduct)) { geo.setVmsta("Z0");
	 * if("WW".equals(sapGeo.getBusinessGeo().getKwValue())) { Date earliestDate =
	 * this.getEarliestAnnDate(softwareProduct); if(earliestDate != null) {
	 * geo.setVmstd(DateUtility.getDateStringWithSapFormat(earliestDate)); } }else {
	 * geo.setVmstd(DateUtility.getDateStringWithSapFormat(annDate)); } } } //set
	 * plant List<RdhMatm_plant> plants = new ArrayList<RdhMatm_plant>();
	 * for(SapPlant sapplant : sapGeo.getSapPlants()) { RdhMatm_plant plant = new
	 * RdhMatm_plant(); plant.setWerks(sapplant.getPlantCode());
	 * plant.setLgort(sapplant.getStorageLocation()); plants.add(plant); }
	 * geo.setPlants(plants);
	 * 
	 * List<RdhMatm_sales_org> sales_orgs = new ArrayList<RdhMatm_sales_org>();
	 * for(SapSalesOrg salesorg : sapGeo.getSapSalesOrgs()) { RdhMatm_sales_org
	 * sales_org = new RdhMatm_sales_org(); sales_orgs.add(sales_org);
	 * sales_org.setVkorg(salesorg.getSalesOrg());
	 * sales_org.setDwerk(salesorg.getDeliveryPlant());
	 * if("0147".equals(salesorg.getSalesOrg())) {
	 * sales_org.setZtaxclsf(softwareProduct.getUsTaxClass().getKwValue()); }
	 * 
	 * } geo.setSales_orgs(sales_orgs);
	 * 
	 * List<RdhMatm_tax_country> tax_countries = new
	 * ArrayList<RdhMatm_tax_country>(); for(SapCountry country :
	 * sapGeo.getSapCountries()) { RdhMatm_tax_country tax_cnty = new
	 * RdhMatm_tax_country(); tax_countries.add(tax_cnty);
	 * 
	 * tax_cnty.setAland(country.getIsoCountry());
	 * tax_cnty.setTaxm1(country.getTaxClass()); if(sliceGroupEsw != null) {
	 * if("2".equals(country.getTaxClassRule())) {
	 * tax_cnty.setTaxm1(softwareProduct.getUsTaxClass() != null ?
	 * softwareProduct.getUsTaxClass().getKwValue() : ""); }else {
	 * tax_cnty.setTaxm1(country.getTaxClass()); } } if(orderableSupply != null) {
	 * if("0".equals(country.getTaxClassRule())) {
	 * tax_cnty.setTaxm1(country.getTaxClass()); }else
	 * if("1".equals(country.getTaxClassRule()) ||
	 * "3".equals(country.getTaxClassRule())) {
	 * if("PRINT-PAPER".equals(orderableSupply.getMediaType().getName())) {
	 * tax_cnty.setTaxm1(country.getTaxClassAlt()); }else {
	 * tax_cnty.setTaxm1(country.getTaxClass()); } }else
	 * if("2".equals(country.getTaxClassRule())) {
	 * tax_cnty.setTaxm1(softwareProduct.getUsTaxClass() != null ?
	 * softwareProduct.getUsTaxClass().getKwValue() : ""); }else {
	 * tax_cnty.setTaxm1(country.getTaxClass()); } }
	 * tax_cnty.setTaty1(country.getTaxCategory());
	 * tax_cnty.setTaxm2(country.getTaxm2()); tax_cnty.setTaxm3(country.getTaxm3());
	 * tax_cnty.setTaxm4(country.getTaxm4()); tax_cnty.setTaxm5(country.getTaxm5());
	 * tax_cnty.setTaxm6(country.getTaxm6()); tax_cnty.setTaxm7(country.getTaxm7());
	 * tax_cnty.setTaxm8(country.getTaxm8()); tax_cnty.setTaxm9(country.getTaxm9());
	 * }
	 * 
	 * geo.setTax_countries(tax_countries); return geo; }
	 */
	/**
	 * 
	 * @param product
	 * @return
	 */
	private String getEarliestAnnDate(MODEL model) {
		Date annDate = null;
		String result = "";
		List<AVAILABILITY> list = model.getAVAILABILITYLIST();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				try {
					if (annDate == null) {
						result = list.get(i).getANNDATE();
						 
						 annDate= sdf.parse(result);

					} else {
						annDate = sdf.parse(list.get(i).getANNDATE());;

						if (annDate.before(sdf.parse(result))) {
							result = list.get(i).getANNDATE();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

		if (result != null)
			result = result.replace("-", "");
		if (result != null && result.length() > 6) {
			result = result.substring(result.length() - 6);
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param product
	 * @return
	 */
	private String getEarliestPUBFROM(MODEL model) {
		Date PUBFROM = null;
		String result = "";
		List<AVAILABILITY> list = model.getAVAILABILITYLIST();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				try {
					if (PUBFROM == null) {
						result = list.get(i).getPUBFROM();						 
						PUBFROM= sdf.parse(result);

					} else {
						PUBFROM = sdf.parse(list.get(i).getPUBFROM());
						if (PUBFROM.before(sdf.parse(result))) {
							result = list.get(i).getPUBFROM();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

//		if (result != null)
//			result = result.replace("-", "");
//		if (result != null && result.length() > 6) {
//			result = result.substring(result.length() - 6);
//		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.sdpi.cmd.interfaces.esw.rdh.RdhBase#setDefaultValues()
	 */
	@Override
	protected void setDefaultValues() {
		this.is_multi_plants = "TRUE";
		RdhMatm_bmm00 bm0 = new RdhMatm_bmm00();
		bmm00 = new ArrayList<RdhMatm_bmm00>();
		bmm00.add(bm0);
		RdhMatm_bmmh1 bmh1 = new RdhMatm_bmmh1();
		bmmh1 = new ArrayList<RdhMatm_bmmh1>();
		bmmh1.add(bmh1);
		RdhMatm_bmmh5 bmh5 = new RdhMatm_bmmh5();
		bmmh5 = new ArrayList<RdhMatm_bmmh5>();
		bmmh5.add(bmh5);
	
		bmmh6 = new ArrayList<RdhMatm_bmmh6>();
		
		RdhMatm_bmmh7 bmh7 = new RdhMatm_bmmh7();
		bmmh7 = new ArrayList<RdhMatm_bmmh7>();
		bmmh7.add(bmh7);
		geos = new ArrayList<RdhMatm_geo>();
		// geos.add(new RdhMatm_geo());
		// sales_orgs = new ArrayList<RdhMatm_sales_org>();
		// tax_countries = new ArrayList<RdhMatm_tax_country>();
	}
 
	@Override
	protected boolean isReadyToExecute() {
		if (this.getRfcrc() != 0) {
			return false; 
		} else { 
			return true;
		}
	} 
	/**
	 * Change add_date to yyyy-MM-dd
	 * 
	 * @param time
	 * @return add_date
	 */
	private String transformAddDate(String time) {
		StringBuffer add_date = new StringBuffer(time);
		add_date.insert(6, "-");
		add_date.insert(4, "-");
		return add_date.toString();
	}

}
