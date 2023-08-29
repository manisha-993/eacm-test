package COM.ibm.eannounce.abr.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import COM.ibm.eannounce.abr.sg.rfc.entity.HSN;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import COM.ibm.eannounce.abr.sg.rfc.entity.CountryPlantTax;
import COM.ibm.eannounce.abr.sg.rfc.entity.Generalarea;

public class RFCConfig {

	static List<CountryPlantTax> countryPlantTaxs = new ArrayList<CountryPlantTax>();
	static Map<String, String> orgplntMap = new HashMap<String, String>();
	static Map<String, String> geneMap = new HashMap<String, String>();
	static Map<String, Map<String, String>> orgpntMaps = new HashMap<String, Map<String,String>>();
	static List<CountryPlantTax> taxs = new ArrayList<CountryPlantTax>();
	static List<Generalarea> generalareas = new ArrayList<Generalarea>();
	static Set<String> bhplnts = new HashSet<String>();
	static List<HSN> hsns = new ArrayList<HSN>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(hsns);
		System.out.println(RFCConfig.getDwerk("6","0684"));;
	}static {
		loadHSN();
		loadCountryPlantTax();
		loadGeneralarea();
		loadBHPlnt();
	}
	public static void loadHSN() {
		System.out.println("Loading HSN Lookup File!");
		File excel = new File("HSN_LOOKUP.xls");

		try {
			// String encoding = "GBK";
			if (excel.isFile() && excel.exists()) {
				System.out.println("HSN Lookup File Found!");
				String[] split = excel.getName().split("\\.");
				HSSFWorkbook wb;
				if ("xls".equals(split[1])) {
					FileInputStream fis = new FileInputStream(excel);
					wb = new HSSFWorkbook(fis);

					HSSFSheet hssfSheet = wb.getSheetAt(0);
					int lastRow = hssfSheet.getLastRowNum();
					for(int i = 1;i<=lastRow;i++) {
						HSSFRow row = 	hssfSheet.getRow(i);
						HSN hsn = new HSN();
						if(row==null)
							break;
						hsn.setCountry(getCellData(row.getCell((short)0)));
						hsn.setaLand(getCellData(row.getCell((short)1)));
						hsn.setMachType(getCellData(row.getCell((short)2)));
						hsn.setSteuc(getCellData(row.getCell((short)3)));
						hsn.setAvailStatus(getCellData(row.getCell((short)4)));


						hsns.add(hsn);

					}
				}else {
					System.out.println("File type error!");
					return;
				}
			}
			System.out.println("HSN Lookup Data Loaded!");
		}


		catch (Exception e) {
			e.printStackTrace();
		}



	}

	public static List<HSN> getHsns() {
		return hsns;
	}
	public static void loadCountryPlantTax() {
		File excel = new File("COUNTRY_PLANT_TAX.xls");

		try {
			// String encoding = "GBK";
			if (excel.isFile() && excel.exists()) {

				String[] split = excel.getName().split("\\.");
				HSSFWorkbook wb;
				if ("xls".equals(split[1])) {
					FileInputStream fis = new FileInputStream(excel);
					wb = new HSSFWorkbook(fis);

					HSSFSheet hssfSheet = wb.getSheetAt(0);
					int lastRow = hssfSheet.getLastRowNum();
					for(int i = 1;i<=lastRow;i++) {
						HSSFRow row = 	hssfSheet.getRow(i);
						CountryPlantTax tax = new CountryPlantTax();
						if(row==null)
							break;
						tax.setINTERFACE_ID(getCellData(row.getCell((short)0)));
						tax.setSALES_ORG(getCellData(row.getCell((short)1)));
						tax.setPLNT_CD(getCellData(row.getCell((short)2)));
						tax.setDEL_PLNT(getCellData(row.getCell((short)3)));
						tax.setTAX_COUNTRY(getCellData(row.getCell((short)4)));
						tax.setTAX_CD(getCellData(row.getCell((short)6)));
						tax.setTAX_CAT((getCellData(row.getCell((short)7))));
						tax.setTAX_CLAS((getCellData(row.getCell((short)8))));
						if(tax.getINTERFACE_ID()==null||"".equals(tax.getINTERFACE_ID()))
							break;
						orgplntMap = orgpntMaps.get(tax.getINTERFACE_ID());
						if(orgplntMap==null) {
							orgplntMap = new HashMap<String, String>();
							orgpntMaps.put(tax.getINTERFACE_ID(), orgplntMap);
						}
						orgplntMap.put(tax.getSALES_ORG(), tax.getDEL_PLNT());
						taxs.add(tax);

					}
				}else {
					System.out.println("File type error!");
					return;
				}
			}
		}


		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void loadBHPlnt() {
		File excel = new File("BH_ORG_PRD_PROD.xls");

		try {
			// String encoding = "GBK";
			if (excel.isFile() && excel.exists()) {

				String[] split = excel.getName().split("\\.");
				HSSFWorkbook wb;
				if ("xls".equals(split[1])) {
					FileInputStream fis = new FileInputStream(excel);
					wb = new HSSFWorkbook(fis);

					HSSFSheet hssfSheet = wb.getSheetAt(0);
					int lastRow = hssfSheet.getLastRowNum();
					for(int i = 1;i<=lastRow;i++) {
						HSSFRow row = hssfSheet.getRow(i);
						if(row==null) break;
						String cat = getCellData(row.getCell((short)1));
						String plnt = getCellData(row.getCell((short)5));
						if("Hardware".equals(cat.trim())) {
							bhplnts.add(plnt);
						}
					}
				}else {
					System.out.println("File type error!");
					return;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static Set<String> getBHPlnts() {
		return bhplnts;
	}

	public static String getCellData(HSSFCell cell) {
		if(cell==null)
			return null;
		if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		}
		else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
			return (int)cell.getNumericCellValue()+"";
		}

		return null;
	}
	public static String getDwerk(String  intID,String salesOrg ) {
		return orgpntMaps.get(intID)==null?null:orgpntMaps.get(intID).get(salesOrg);
	}
	public static String getPlant(String intID, String plant) {
		Iterator iter = orgpntMaps.get(intID).entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if(plant.equals(value)) return key;
		}
		return null;
	}
	public static String getAland(String key) {
		return geneMap.get(key);
	}
	public static List<CountryPlantTax> getTaxs(){
		return taxs;
	}
	public static List<Generalarea> getGeneralareas() {
		return generalareas;
	}
	public static void loadGeneralarea() {
		//GENERALAREA_UPDATE_CBSE
		try {
			// String encoding = "GBK";
			File excel = new File("GENERALAREA_UPDATE_CBSE.xls");
			if (excel.isFile() && excel.exists()) {

				String[] split = excel.getName().split("\\.");
				HSSFWorkbook wb;
				if ("xls".equals(split[1])) {
					FileInputStream fis = new FileInputStream(excel);
					wb = new HSSFWorkbook(fis);
					HSSFSheet hssfSheet = wb.getSheetAt(0);
					int lastRow = hssfSheet.getLastRowNum();
					for(int i = 1;i<=lastRow;i++) {
						HSSFRow row = 	hssfSheet.getRow(i);
						Generalarea generalarea = new Generalarea();
						if(row==null)
							break;
						generalarea.setGENAREANAME_FC(getCellData(row.getCell((short)3)));
						generalarea.setGENAREACODE(getCellData(row.getCell((short)2)));
						if(generalarea.getGENAREACODE()==null||"".equals(generalarea.getGENAREACODE()))
							break;
						//tax.setSALES_ORG(row.getCell((short)1).getStringCellValue());
						geneMap.put(generalarea.getGENAREANAME_FC(), generalarea.getGENAREACODE());
						generalareas.add(generalarea);
					}
				}else {
					System.out.println("File type error!");
					return;
				}
			}
		}


		catch (Exception e) {
			e.printStackTrace();
		}

	}


}
