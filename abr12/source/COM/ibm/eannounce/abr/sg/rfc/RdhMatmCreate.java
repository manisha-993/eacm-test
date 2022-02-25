/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import COM.ibm.eannounce.abr.util.RFCConfig;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;
import com.google.gson.annotations.SerializedName;

/**
 * Creates or updates an SAP material master.
 * 
 * @author will
 *
 */
public class RdhMatmCreate extends RdhBase {
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
	@Foo
	SimpleDateFormat sdfANNDATE =   new SimpleDateFormat( "ddMMyy" );
	@Foo
	String annnumber = null;
	public RdhMatmCreate(SVCMOD svcmod) {
		super(svcmod.getMACHTYPE() + svcmod.getMODEL(), "Z_DM_SAP_MATM_CREATE".toLowerCase(), null);
		// RdhMatm_bmm00 matnr Copy from <SoftwareProduct.productIdentifier>.
		bmm00.get(0).setMatnr(svcmod.getMACHTYPE() + svcmod.getMODEL());
		bmm00.get(0).setMtart(getMtart(svcmod));
		bmm00.get(0).setMbrsh("M");
		//bmm00.get(0).setWerks("00");
		bmm00.get(0).setVtweg("00");
		bmm00.get(0).setXeik1("X");
		bmm00.get(0).setXeiv1("X");
		bmmh1.get(0).setMatkl("000");
		bmmh1.get(0).setMeins(getMeins(svcmod));
		
		bmmh1.get(0).setZeiar("RFA");
		bmmh1.get(0).setAeszn(getEarliestAnnDate(svcmod));
		bmmh1.get(0).setZeinr(annnumber);
		bmmh1.get(0).setGewei("");
		bmmh1.get(0).setSpart("00");
		bmmh1.get(0).setProdh(svcmod.getPRODHIERCD());
		bmmh1.get(0).setMtpos(getMtpos(svcmod));
		bmmh1.get(0).setPrctr(svcmod.getPRFTCTR()==null?"":svcmod.getPRFTCTR());
		bmmh1.get(0).setKtgrm(svcmod.getACCTASGNGRP()==null?"":svcmod.getACCTASGNGRP());
//		/PCTOFCMPLTINDC 
		if ("Yes".equals(svcmod.getPCTOFCMPLTINDC()))
			bmmh1.get(0).setPrat1("X");

		List<TAXCODE> taxcodes = svcmod.getTAXCODELIST();
		if (taxcodes != null && taxcodes.size() > 0) {
			bmmh1.get(0).setMvgr5(taxcodes.get(0).getTAXCODE());
		}
		// bmmh1.get(0).setp;

		List<LANGUAGE> languages = svcmod.getLANGUAGELIST();
		if (languages != null && languages.size() > 0) {
			bmmh5.get(0).setSpras("E");
			bmmh5.get(0).setMaktx(languages.get(0).getINVNAME());
			bmmh5.get(0).setTdline(languages.get(0).getMKTGNAME());
		} else {
			bmmh5.get(0).setSpras("E");
		}

		if ("ZSV2".equals(bmm00.get(0).getMtart())) {
			RdhMatm_bmmh6 bmh6 = new RdhMatm_bmmh6();
			bmh6.setMeinh("SMI");
			bmmh6.add(bmh6);
			bmh6 = new RdhMatm_bmmh6();
			bmh6.setMeinh("HUR");
			bmmh6.add(bmh6);
		}
		bmmh7.get(0).setTdid("0001");
		bmmh7.get(0).setTdspras("E");
		// SVCMOD_UPDATE /LANGUAGELIST /LANGUAGEELEMENT /MKTGNAME
		if (svcmod.getLANGUAGELIST() != null) {
			bmmh7.get(0).setTdline(svcmod.getLANGUAGELIST().get(0).getMKTGNAME());
		}
		List<AVAILABILITY> availabilities = svcmod.getAVAILABILITYLIST();
		List<TAXCATEGORY> taxcategories = svcmod.getTAXCATEGORYLIST();
		Set<TAXCATEGORY> allSet = new HashSet<TAXCATEGORY>(taxcategories);
		Set<TAXCATEGORY> groupSet = new HashSet<TAXCATEGORY>();
		String groupName = null;
		int index = 1;
		Set<String> pubfromSet = new HashSet<>();
		if (availabilities != null && availabilities.size() > 0) {
			/**
			 * Group availablities by pubfrom
			 */
			Map<String, Set<SLEORGNPLNTCODE>> sMap = new HashMap<String, Set<SLEORGNPLNTCODE>>();
			Map<String, Set<AVAILABILITY>> aMap = new HashMap<String, Set<AVAILABILITY>>();
			Map<String, Set<TAXCATEGORY>> cMap = new HashMap<String, Set<TAXCATEGORY>>();
			// Map<String, Set<SLEORGNPLNTCODE>> sMap = new HashMap<String,
			// Set<SLEORGNPLNTCODE>>();

			for (int i = 0; i < availabilities.size(); i++) {
				Set<SLEORGNPLNTCODE> sset = sMap.get(availabilities.get(i).getPUBFROM());
				if (sset == null) {
					sset = new HashSet<SLEORGNPLNTCODE>();
					sMap.put(availabilities.get(i).getPUBFROM(), sset);
				}
				sset.addAll(availabilities.get(i).getSLEORGNPLNTCODELIST());

				Set<AVAILABILITY> aset = aMap.get(availabilities.get(i).getPUBFROM());
				if (aset == null) {
					aset = new HashSet<AVAILABILITY>();
					aMap.put(availabilities.get(i).getPUBFROM(), aset);
				}
				aset.add(availabilities.get(i));
				Set<TAXCATEGORY> cset = cMap.get(availabilities.get(i).getPUBFROM());
				if (cset == null) {
					cset = new HashSet<TAXCATEGORY>();
				}
				if (taxcategories != null) {
					for (int j = 0; j < taxcategories.size(); j++) {
						if (taxcategories.get(j) != null && taxcategories.get(j).getCOUNTRYLIST() != null
								&& taxcategories.get(j).getCOUNTRYLIST().size() > 0) {
							if (availabilities.get(i).getCOUNTRY_FC()
									.equals(taxcategories.get(j).getCOUNTRYLIST().get(0).getCOUNTRY_FC())) {
								cset.add(taxcategories.get(j));
								groupSet.add(taxcategories.get(j));
							}

						}
					}
				}
				if(groupName==null)
					groupName = availabilities.get(i).getPUBFROM();
				cMap.put(availabilities.get(i).getPUBFROM(), cset);
				// taxcategories.get(i).getCOUNTRYLIST().contains("");

			}
			if (groupName!=null) {
				allSet.removeAll(groupSet);
				Set<TAXCATEGORY> cset = cMap.get(groupName);
				if(cset!=null) {	
					cset.addAll(allSet);
				}
				cMap.put(groupName, cset);
			}

			Iterator<String> iterator = sMap.keySet().iterator();
			while (iterator.hasNext()) {
				String pubfrom = iterator.next();

				RdhMatm_geo geo = new RdhMatm_geo();
				geo.setName("WW" + index++);
				geo.setVmsta("Z0");
				geo.setVmstd(pubfrom);

				List<SLEORGNPLNTCODE> sleorggrps = new ArrayList(sMap.get(pubfrom));
				List<TAXCATEGORY> taxcList = new ArrayList<TAXCATEGORY>(cMap.get(pubfrom));
				List<RdhMatm_sales_org> sales_orgList = new ArrayList<RdhMatm_sales_org>();
				List<RdhMatm_plant> plantList = new ArrayList<RdhMatm_plant>();
				List<RdhMatm_tax_country> tax_countries = new ArrayList<RdhMatm_tax_country>();

				Set<String> unitSet = new HashSet<String>();
				Set<String> plantSet = new HashSet<String>();
				for (int j = 0; j < sleorggrps.size(); j++) {
					SLEORGNPLNTCODE sleorgnplntcode = sleorggrps.get(j);
					
					RdhMatm_plant plant = new RdhMatm_plant();
					plant.setWerks(sleorgnplntcode.getPLNTCD());
					if (plant.getWerks() != null && plant.getWerks().startsWith("Y")) {
						plant.setPrctr(bmmh1.get(0).getPrctr().replaceFirst("P", "YY"));
					} else {
						plant.setPrctr(bmmh1.get(0).getPrctr());
					}
					RdhMatm_sales_org sales_org = new RdhMatm_sales_org();
					sales_org.setVkorg(sleorggrps.get(j).getSLEORG());
					sales_org.setDwerk(sleorggrps.get(j).getPLNTCD());
					// "Canada, Ireland, US"
					if ("0026".contains(sleorggrps.get(j).getSLEORG())) {
						sales_org.setZtaxclsf(getZtaxclsf(svcmod, "Canada"));
					} else if ("0066".contains(sleorggrps.get(j).getSLEORG())) {
						sales_org.setZtaxclsf(getZtaxclsf(svcmod, "Ireland"));
					} else if ("0147".contains(sleorggrps.get(j).getSLEORG())) {
						sales_org.setZtaxclsf(getZtaxclsf(svcmod, "US"));
					}
					if ("0066".equals(sales_org.getVkorg())) {
						if ("2046".equals(plant.getWerks())) {
							if ("P4016".equals(bmmh1.get(0).getPrctr())) {
								sales_org.setZsabrtax("SWMA");
							} else if ("P4022".equals(bmmh1.get(0).getPrctr())) {
								sales_org.setZsabrtax("HWMA");
							}
						}
					}

					else if ("0026,0147".contains(sales_org.getVkorg())) {
						//sales_org.setZtaxclsf("");
						if (sales_org.getZtaxclsf() != null && !sales_org.getZtaxclsf().equals("")) {
							sales_org.setZsabrtax(getZsabrtax(sales_org.getZtaxclsf()));
						}
					}

					/**
					 * If <geo.sales_org.vkorg> in (“0026”,”0147”) if <geo.sales_org.ztaxclsf> is
					 * set to a value, then { Read the keyword_t table where category == "ZSABRTAX"
					 * and kwvalue == <geo.sales_org.ztaxclsf>. if a row is found, then Copy
					 * <keyword_t.name> to zsabrtax. else { Set error_text to "Error: Unable to set
					 * ZSABRTAX value. ZTAXCLSF value geo.sales_org.ztaxclsf> not found in keyword_t
					 * table for category "ZSABRTAX". } }
					 * 
					 */

					if(RFCConfig.getDwerk("6",sales_org.getVkorg())!=null&&!unitSet.contains(sales_org.getVkorg()))
					{
						sales_org.setDwerk(RFCConfig.getDwerk("6",sales_org.getVkorg()));
						sales_orgList.add(sales_org);
						unitSet.add(sales_org.getVkorg());
					}
					if(plantSet.contains(plant.getWerks())) {
						
					}else {
						plantList.add(plant);
						plantSet.add(plant.getWerks());
					}
					
				}

				/**
				 * e. For each SVCMOD_UPDATE /TAXCATEGORYLIST /TAXCATEGORYELEMENT Associate the
				 * TAXCATEGORYELEMENT to the group on the condition that TAXCATEGORYELEMENT/
				 * COUNTRYLIST /COUNTRYELEMENT /COUNTRY_FC = SVCMOD_UPDATE /AVAILABILITYLIST
				 * /AVAILABILITYELEMENT/COUNTRY_FC Create one row into tax_country structure of
				 * the group as below and then remove the duplidated rows.
				 * 
				 */
				if (taxcList != null) {
					for (int k = 0; k < taxcList.size(); k++) {
						List<COUNTRY> countries = taxcList.get(k).getCOUNTRYLIST();
						if(taxcList.get(k).getCOUNTRYLIST().size()==0)
							continue; 
						RdhMatm_tax_country tax_country = new RdhMatm_tax_country();
						tax_country.setTaty1(taxcList.get(k).getTAXCATEGORYVALUE());
						tax_country.setTaxm1(taxcList.get(k).getTAXCLASSIFICATION());
						// tax_country.setAland("US");
						/*
						 * tax_country.setAland(RfcConfigProperties
						 * .getCountry(taxcList.get(k).getCOUNTRYLIST().get(0).getCOUNTRY_FC()));
						 */
						tax_country.setAland(RFCConfig.getAland(taxcList.get(k).getCOUNTRYLIST().get(0).getCOUNTRY_FC()));
						tax_country.setTaxm2("1");
						tax_country.setTaxm3("1");
						tax_country.setTaxm4("1");
						tax_country.setTaxm5("1");
						tax_country.setTaxm6("1");
						tax_country.setTaxm7("1");
						tax_country.setTaxm8("1");
						tax_country.setTaxm9("1");

						if(tax_country.getAland()!=null) {
						tax_countries.add(tax_country);
						}
					}
				}
				geo.setPlants(plantList);
				geo.setSales_orgs(sales_orgList);
				geo.setTax_countries(tax_countries);
				geos.add(geo);

			}

			/*
			 * for (int i = 0; i <availabilities.size(); i++) {
			 * if(pubfromSet.contains(availabilities.get(i).getPUBFROM())){ continue; }else
			 * {
			 * 
			 * } RdhMatm_geo geo = new RdhMatm_geo(); geo.setName("WW"+index);
			 * geo.setVmsta("Z0"); geo.setVmstd(availabilities.get(i).getPUBFROM());
			 * 
			 * List<SLEORGNPLNTCODE> sleorggrps
			 * =availabilities.get(i).getSLEORGNPLNTCODELIST();
			 * 
			 * List<RdhMatm_sales_org> sales_orgList = new ArrayList<RdhMatm_sales_org>();
			 * List<RdhMatm_plant> plantList = new ArrayList<RdhMatm_plant>();
			 * List<RdhMatm_tax_country> tax_countries = new
			 * ArrayList<RdhMatm_tax_country>(); if(sleorggrps!=null&&sleorggrps.size()>0){
			 * for (int j = 0; j < sleorggrps.size(); j++) { SLEORGNPLNTCODE sleorgnplntcode
			 * = sleorggrps.get(j); RdhMatm_plant plant = new RdhMatm_plant();
			 * plant.setWerks(sleorgnplntcode.getPLNTCD());
			 * if(plant.getWerks()!=null&&plant.getWerks().startsWith("P")){
			 * plant.setPrctr(plant.getWerks().replaceFirst("P", "YY")); } else {
			 * plant.setPrctr(bmmh1.get(0).getPrctr()); } RdhMatm_sales_org sales_org = new
			 * RdhMatm_sales_org(); sales_org.setVkorg(sleorggrps.get(j).getSLEORG());
			 * sales_org.setDwerk(sleorggrps.get(j).getPLNTCD()); //"Canada, Ireland, US"
			 * if("0026".contains(sleorggrps.get(j).getSLEORG())){
			 * sales_org.setZtaxclsf(getZtaxclsf(svcmod,"Canada")); } else
			 * if("0066".contains(sleorggrps.get(j).getSLEORG())){
			 * sales_org.setZtaxclsf(getZtaxclsf(svcmod,"Ireland")); } else
			 * if("0147".contains(sleorggrps.get(j).getSLEORG())){
			 * sales_org.setZtaxclsf(getZtaxclsf(svcmod,"US")); }
			 * if("0066".equals(sales_org.getVkorg())){ if("2046".equals(plant.getWerks())){
			 * if("P4016".equals(bmmh1.get(0).getPrctr())){ sales_org.setZsabrtax("SWMA");
			 * }else if ("P4022".equals(bmmh1.get(0).getPrctr())) {
			 * sales_org.setZsabrtax("HWMA"); } } }
			 * 
			 * else if ("0026,0147".contains(sales_org.getVkorg())) {
			 * sales_org.setZtaxclsf(""); if
			 * (sales_org.getZtaxclsf()!=null&&!sales_org.getZtaxclsf().equals("")) {
			 * sales_org.setZsabrtax(getZsabrtax(sales_org.getZtaxclsf())); } }
			 * 
			 *//**
				 * If <geo.sales_org.vkorg> in (“0026”,”0147”) if <geo.sales_org.ztaxclsf> is
				 * set to a value, then { Read the keyword_t table where category == "ZSABRTAX"
				 * and kwvalue == <geo.sales_org.ztaxclsf>. if a row is found, then Copy
				 * <keyword_t.name> to zsabrtax. else { Set error_text to "Error: Unable to set
				 * ZSABRTAX value. ZTAXCLSF value geo.sales_org.ztaxclsf> not found in keyword_t
				 * table for category "ZSABRTAX". } }
				 * 
				 */
			/*
			 * 
			 * 
			 * 
			 * sales_orgList.add(sales_org); plantList.add(plant); }
			 * 
			 *//**
				 * e. For each SVCMOD_UPDATE /TAXCATEGORYLIST /TAXCATEGORYELEMENT Associate the
				 * TAXCATEGORYELEMENT to the group on the condition that TAXCATEGORYELEMENT/
				 * COUNTRYLIST /COUNTRYELEMENT /COUNTRY_FC = SVCMOD_UPDATE /AVAILABILITYLIST
				 * /AVAILABILITYELEMENT/COUNTRY_FC Create one row into tax_country structure of
				 * the group as below and then remove the duplidated rows.
				 * 
				 *//*
					 * if(taxcategories!=null){ for (int k = 0; k < taxcategories.size(); k++) {
					 * List<COUNTRY> countries = taxcategories.get(k).getCOUNTRYLIST();
					 * if(countries!=null){ for (int j = 0; j < countries.size(); j++) {
					 * if(countries.get(j).getCOUNTRY_FC().equals(availabilities.get(i).
					 * getCOUNTRY_FC())){ RdhMatm_tax_country tax_country = new
					 * RdhMatm_tax_country();
					 * tax_country.setTaty1(taxcategories.get(k).getTAXCATEGORYVALUE());
					 * tax_country.setTaxm1(taxcategories.get(k).getTAXCATEGORYACTION());
					 * tax_country.setTaxm2("1"); tax_country.setTaxm3("1");
					 * tax_country.setTaxm4("1"); tax_country.setTaxm5("1");
					 * tax_country.setTaxm6("1"); tax_country.setTaxm7("1");
					 * tax_country.setTaxm8("1"); tax_country.setTaxm9("1"); } }
					 * 
					 * } } } index++; pubfromSet.add(availabilities.get(i).getPUBFROM());
					 * geo.setPlants(plantList); geo.setSales_orgs(sales_orgList);
					 * geo.setTax_countries(tax_countries); geos.add(geo); }
					 */

		
	}

	bmm00.get(0).setTcode("MM01");
	// geos.get(0);
	// !!!!!
	// bmmh1.get(0).set

	// bmmh1.get(0).setMvgr5(svcmod.getTAXCATEGORYLIST());

	/*
	 * if(!isNullOrBlank(productSchedule.getRfaNumber())) {
	 * bmmh1.get(0).setZeiar("RFA");
	 * bmmh1.get(0).setZeinr(productSchedule.getRfaNumber()); }
	 * 
	 * if(this.isAas(softwareProduct)) { if(!softwareProduct.getIsSwmaOffered()) {
	 * Date annDate = this.getEarliestAnnDate(softwareProduct); if(annDate != null)
	 * { bmmh1. get(0).setAeszn(DateUtility.getDateStringWithAesznFormat(annDate));
	 * } } if(RpqType.NOT_RPQ.name().equals(softwareProduct.getRpqType().getKwValue
	 * ())) { bmmh1.get(0).setZconf("F"); }else { bmmh1.get(0).setZconf("E"); } }
	 */

	// RdhMatm_bmmh5 spras Set to "E" (English).

	// if(softwareProduct.getLicenseAgreementType() == null
	// ||
	// "F".equalsIgnoreCase(softwareProduct.getLicenseAgreementType().getIsSerialized())){
	// bmmh1.get(0).setSernp("");
	// }

	// setGeoSalesOrgCountry(softwareProduct, null, null, null);
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

	private String getMtpos(SVCMOD svcmod) {
		// TODO Auto-generated method stub
		String result = "";
		if (svcmod == null)
			return result;
		if ("Service".equals(svcmod.getCATEGORY())) {
			if ("Custom".equals(svcmod.getSUBCATEGORY())) {
				if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
					result = "ZSV1";
				}

			} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
				/**
				 * SMG0003 (Penalty) SMG0004 (Incident) SMG0005 (Travel) SMG006 (Activity)
				 */
				if ("Penalty".equals(svcmod.getGROUP())) {
					result = "ZSV4";
				} else if ("Incident".equals(svcmod.getGROUP())) {
					result = "ZSV5";
				} else if ("Travel".equals(svcmod.getGROUP())) {
					result = "ZSTE";
				} else if ("Activity".equals(svcmod.getGROUP())) {
					result = "ZSA1";
				}else if ("OEM".equals(svcmod.getGROUP())) {
					result = "ZSOE";
				}
			} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())
					&& "Non-Federated".equals(svcmod.getGROUP())) {
				result = "ZSA1";
			}
		} else if ("IP".equals(svcmod.getCATEGORY()) && "SC".equals(svcmod.getSUBCATEGORY())) {
			result = "ZSV1";
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
	public String getEarliestAnnDate(SVCMOD svcmod) {
		Date annDate = null;
		Date annTemp = null;
		String result = "";
		List<AVAILABILITY> list = svcmod.getAVAILABILITYLIST();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				try {
					if (annDate == null) {
						//result = list.get(i).getANNDATE();
						annnumber= list.get(i).getANNNUMBER();
						 annDate= sdf.parse(list.get(i).getANNDATE());

					} else {
						annTemp = sdf.parse(list.get(i).getANNDATE());;

						if (annTemp.after(annDate)) {
							annDate = annTemp;
							//result = list.get(i).getANNDATE();
							annnumber= list.get(i).getANNNUMBER();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		/*
		 * if (result != null) result = result.replace("-", ""); if (result != null &&
		 * result.length() > 6) { result = result.substring(result.length() - 6); }
		 */
		if(annDate==null)
			return null;
		return sdfANNDATE.format(annDate);
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
