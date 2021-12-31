package COM.ibm.eannounce.abr.sg.rfc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmm00;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh1;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh5;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_geo;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_plant;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_sales_org;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_tax_country;
import COM.ibm.eannounce.abr.util.RFCConfig;

public class RdhSvcMatmCreate extends RdhBase {

	@SerializedName("BMM00")
	private List<RdhMatm_bmm00> bmm00;
	@SerializedName("BMMH1")
	private List<RdhMatm_bmmh1> bmmh1;
	@SerializedName("BMMH5")
	private List<RdhMatm_bmmh5> bmmh5;
	@SerializedName("GEO")
	private List<RdhMatm_geo> geos;
	@SerializedName("IS_MULTI_PLANTS")
	private String is_multi_plants;

	public RdhSvcMatmCreate(MODEL model) throws ParseException {
		super(model.getMACHTYPE() + model.getMODEL(), "Z_DM_SAP_MATM_CREATE".toLowerCase(), null);

		bmm00.get(0).setMatnr(model.getMACHTYPE() + model.getMODEL());
		bmm00.get(0).setMtart("ZPRT");
		bmm00.get(0).setMbrsh("M");
		bmm00.get(0).setVtweg("00");
		bmmh1.get(0).setMatkl("000");
		bmmh1.get(0).setMeins("EA");
		bmmh1.get(0).setAeszn(getEarliestAnnDate(model));
		bmmh1.get(0).setGewei("KG");
		bmmh1.get(0).setTragr("0001");
		bmmh1.get(0).setSpart("00");
		bmmh1.get(0).setPrdha(model.getPRODHIERCD());
		bmmh1.get(0).setMaabc("A");
		bmmh1.get(0).setEkgrp("ZZZ");
		bmmh1.get(0).setDismm("PD");
		bmmh1.get(0).setDispo("000");
		bmmh1.get(0).setPerkz("M");
		bmmh1.get(0).setDisls("EX");
		bmmh1.get(0).setBeskz("X");
		bmmh1.get(0).setSbdkz("1");
		bmmh1.get(0).setFhori("000");
		bmmh1.get(0).setMtvfp("KP");
		bmmh1.get(0).setPrctr(model.getPRFTCTR());
		bmmh1.get(0).setFxhor("0");
		bmmh1.get(0).setDisgr("Z010");
		bmmh1.get(0).setSernp("NONE");
		bmmh1.get(0).setVprsv("S");
		bmmh1.get(0).setStprs("0.01");
		bmmh1.get(0).setPeinh("1");
		bmmh1.get(0).setBklas("7900");
		bmmh1.get(0).setVmvpr("S");
		bmmh1.get(0).setVmbkl("7900");
		bmmh1.get(0).setVjvpr("S");
		bmmh1.get(0).setVjbkl("7900");
		bmmh1.get(0).setVersg("1");
		bmmh1.get(0).setSktof("X");
		bmmh1.get(0).setAumng("1");
		bmmh1.get(0).setLfmng("1");
		bmmh1.get(0).setScmng("1");
		bmmh1.get(0).setMtpos(getMtpos(model));
		bmmh1.get(0).setProdh(model.getPRODHIERCD());
		bmmh1.get(0).setKtgrm(model.getACCTASGNGRP());
		bmmh1.get(0).setMvgr5(model.getTAXCODELIST().get(0).getTAXCODE());
		bmm00.get(0).setXeib1("X");
		bmm00.get(0).setXeid1("X");
		bmm00.get(0).setXeik1("X");
		bmm00.get(0).setXeiv1("X");
		bmm00.get(0).setXeie1("X");
		bmm00.get(0).setTcode("MM01");

		List<LANGUAGE> languages = model.getLANGUAGELIST();
		if (languages != null && languages.size() > 0) {
			bmmh5.get(0).setSpras(getSpras(languages.get(0).getNLSID()));
			bmmh5.get(0).setMaktx(languages.get(0).getINVNAME());
			bmmh5.get(0).setTdline(languages.get(0).getMKTGNAME());
		} else {
			bmmh5.get(0).setSpras("E");
		}

		List<AVAILABILITY> availabilities = model.getAVAILABILITYLIST();
		List<Date> dates = new ArrayList<Date>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		if (availabilities != null && availabilities.size() > 0) {
			for (int i = 0; i < availabilities.size(); i++) {
				AVAILABILITY avail = availabilities.get(i);
				dates.add(formatter.parse(avail.getPUBFROM()));

				System.out.println(formatter.parse(avail.getPUBFROM()));
				List<SLEORGNPLNTCODE> sleorggrps = avail.getSLEORGNPLNTCODELIST();
				if (sleorggrps != null && sleorggrps.size() > 0) {
					for (int j = 0; j < sleorggrps.size(); j++) {
						SLEORGNPLNTCODE sleo = sleorggrps.get(j);
						// validate if
						// MODEL_UPDATE/AVAILABILITYLIST/AVAILABILITYELEMENT/SLEORGNPLNTCODELIST/SLEORGNPLNTCODEELEMENT/PLNTCD
						// is a value of colunm PLNT_CD in table country_plant_tax where INTERFACE_ID =
						// "2".

						if (RFCConfig.getDwerk("2", sleo.getPLNTCD()) != null) {
							RdhMatm_plant plant = new RdhMatm_plant();
							plant.setWerks(sleo.getPLNTCD());
							plant.setEkgrp("ZZZ");
						}
						// validate if
						// MODEL_UPDATE/AVAILABILITYLIST/AVAILABILITYELEMENT/SLEORGNPLNTCODELIST/SLEORGNPLNTCODEELEMENT/SLEORG
						// is a value of colunm SALES_ORG in table country_plant_tax where INTERFACE_ID
						// = "2".
						if (RFCConfig.getDwerk("2", sleo.getSLEORG()) != null) {
							RdhMatm_sales_org sales_org = new RdhMatm_sales_org();
							sales_org.setVkorg(sleo.getSLEORG());
							// DEL_PLNT of table COUNTRY_PLANT_TAX matched with the vkorg.
							sales_org.setDwerk(RFCConfig.getDwerk("2", sleo.getSLEORG()));
						}
					}
				}
			}
			SimpleDateFormat formatter2 = new SimpleDateFormat("YYYY-MM-DD");
			geos.get(0).setName("WW1");
			geos.get(0).setVmsta("Z0");
			geos.get(0).setVmstd(formatter2.format(Collections.min(dates)));
		}

		List<TAXCATEGORY> taxcategories = model.getTAXCATEGORYLIST();
		if (taxcategories != null && taxcategories.size() > 0) {
			for (int i = 0; i < taxcategories.size(); i++) {
				TAXCATEGORY taxcat = taxcategories.get(i);
				List<COUNTRY> countries = taxcat.getCOUNTRYLIST();
				for (int j = 0; j < countries.size(); j++) {
					// validate if
					// MODEL_UPDATE/TAXCATEGORYLIST/TAXCATEGORYELEMENT/COUNTRYLIST/COUNTRYELEMENT/COUNTRY_FC
					// is existing in table GENERALAREA_UPDATE_CBSE
					if (RFCConfig.getAland(countries.get(j).getCOUNTRY_FC()) != null) {
						RdhMatm_tax_country tax_country = new RdhMatm_tax_country();
						tax_country.setAland(RFCConfig.getAland(countries.get(j).getCOUNTRY_FC()));
						tax_country.setTaty1(taxcat.getTAXCATEGORYVALUE());
						tax_country.setTaxm1(taxcat.getTAXCLASSIFICATION());
						tax_country.setTaxm2(taxcat.getTAXCLASSIFICATION());
						tax_country.setTaxm3(taxcat.getTAXCLASSIFICATION());
						tax_country.setTaxm4(taxcat.getTAXCLASSIFICATION());
						tax_country.setTaxm5(taxcat.getTAXCLASSIFICATION());
						tax_country.setTaxm6(taxcat.getTAXCLASSIFICATION());
						tax_country.setTaxm7(taxcat.getTAXCLASSIFICATION());
						tax_country.setTaxm8(taxcat.getTAXCLASSIFICATION());
						tax_country.setTaxm9(taxcat.getTAXCLASSIFICATION());
					}
				}
			}
		}
	}

	private String getSpras(String nlsid) {
		String result = null;
		if ("1".equals(nlsid)) {
			result = "E";
		} else if ("2".equals(nlsid)) {
			result = "D";
		} else if ("11".equals(nlsid)) {
			result = "1";
		}
		return result;
	}

	private String getMtpos(MODEL model) {
		String result = null;
		int postn = model.getSUBCATEGORY().indexOf(" ");
		if (model == null)
			return result;
		if (postn > 0) {
			result = MTARTforService(model.getSUBCATEGORY().substring(1, postn).toUpperCase());
		} else {
			result = MTARTforService(model.getSUBCATEGORY().toUpperCase());
		}
		return result;
	}

	private String MTARTforService(String string) {
		String MTART = null;
		if ("RTSXSERIES".equals(string) || "INSTALL".equals(string) || "MAINONLY".equals(string)
				|| "MAINONLYA".equals(string) || "WMAINOCS".equals(string) || "ENSPEURP".equals(string)
				|| "GENERICHW2".equals(string) || "STRTUPSUP".equals(string) || "MEMEAMAP".equals(string)
				|| "MEMEAWMO".equals(string) || "WMAINOCS".equals(string) || "WMAINTOPT".equals(string)
				|| "STRTUPSUP".equals(string) || "STG".equals(string)) {
			MTART = "LEIS";

		} else if ("SS S & S".equals(string) || "ENDUSERSUP".equals(string) || "EURBUNSP".equals(string)
				|| "EUREMIMP".equals(string) || "EURETSSWU".equals(string) || "EUREXSP".equals(string)
				|| "EURMIGSP".equals(string) || "EURMVSCIS".equals(string) || "EURMVSSP".equals(string)
				|| "GENERICHW1".equals(string) || "GENERICHW3".equals(string) || "GENERICHW4".equals(string)
				|| "GENERICHW5".equals(string) || "GENERICHW7".equals(string) || "ITESEDUC".equals(string)
				|| "PSSWSUPP".equals(string) || "RTECHSUPP".equals(string) || "SERVACCT".equals(string)
				|| "SERVHEALTH".equals(string) || "SMOOTHSTRT".equals(string) || "MAINSWSUPP".equals(string)
				|| "SUPLINEEU".equals(string) || "SUPPORTLN".equals(string) || "SYSEXPERT".equals(string)
				|| "TECHSUPPSV".equals(string) || "GTMSEUR".equals(string) || "ETSAAEUR".equals(string)
				|| "PROACTSYS".equals(string) || "GENERICHW6".equals(string)) {
			MTART = "ZPSE";

		} else if ("SBUNDLE1".equals(string) || "SBUNDLE2".equals(string) || "SBUNDLE3".equals(string)
				|| "SBUNDLE4".equals(string) || "SBUNDLE5".equals(string) || "SBUNDLE6".equals(string)
				|| "SBUNDLE".equals(string)) {
			MTART = "ZTSP";

		} else if ("WMAINTOPTA".equals(string)) {
			MTART = "ZCSP";

		}
		return MTART;
	}

	private String getEarliestAnnDate(MODEL model) {
		LocalDate annDate = null;
		String result = "";
		List<AVAILABILITY> list = model.getAVAILABILITYLIST();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				try {
					if (annDate == null) {
						result = list.get(i).getANNDATE();
						annDate = LocalDate.parse(result);

					} else {
						annDate = LocalDate.parse(list.get(i).getANNDATE());

						if (annDate.isAfter(LocalDate.parse(result))) {
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

	@Override
	protected void setDefaultValues() {
		this.pims_identity = "H";
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

		geos = new ArrayList<RdhMatm_geo>();
		geos.add(new RdhMatm_geo());

	}

	@Override
	protected boolean isReadyToExecute() {
		if (this.getRfcrc() != 0) {
			return false;
		} else {
			return true;
		}
	}

}
