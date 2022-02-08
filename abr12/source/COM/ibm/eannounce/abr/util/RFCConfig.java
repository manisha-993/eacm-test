package COM.ibm.eannounce.abr.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class RFCConfig {

	static List<CountryPlantTax> countryPlantTaxs = new ArrayList<CountryPlantTax>();
	static Map<String, String> orgplntMap = new HashMap<String, String>();
	static Map<String, String> geneMap = new HashMap<String, String>();
	static Map<String, Map<String, String>> orgpntMaps = new HashMap<String, Map<String,String>>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(RFCConfig.getDwerk("6","0684"));;
	}static {
		loadCountryPlantTax();
		loadGeneralarea();
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
					for(int i = 2;i<lastRow;i++) {
					HSSFRow row = 	hssfSheet.getRow(i);
					CountryPlantTax tax = new CountryPlantTax();
					if(row==null)
						break;
					tax.setINTERFACE_ID(getCellData(row.getCell((short)0)));
					tax.setSALES_ORG(getCellData(row.getCell((short)1)));
					tax.setPLNT_CD(getCellData(row.getCell((short)2)));
					tax.setDEL_PLNT(getCellData(row.getCell((short)3)));
					if(tax.getINTERFACE_ID()==null||"".equals(tax.getINTERFACE_ID()))
						break;
					orgplntMap = orgpntMaps.get(tax.getINTERFACE_ID());
					if(orgplntMap==null) {
						orgplntMap = new HashMap<String, String>();
						orgpntMaps.put(tax.getINTERFACE_ID(), orgplntMap);
					}
					orgplntMap.put(tax.getSALES_ORG(), tax.getDEL_PLNT());
					
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
	public static String getAland(String key) {
		return geneMap.get(key);
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
					for(int i = 2;i<lastRow;i++) {
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
	static class CountryPlantTax{
		String INTERFACE_ID = null;
		String SALES_ORG = null;
		String PLNT_CD = null;
		String DEL_PLNT = null;
		String TAX_CAT = null;
		String TAX_CLAS = null;
		public String getINTERFACE_ID() {
			return INTERFACE_ID;
		}
		public void setINTERFACE_ID(String iNTERFACE_ID) {
			INTERFACE_ID = iNTERFACE_ID;
		}
		public String getSALES_ORG() {
			return SALES_ORG;
		}
		public void setSALES_ORG(String sALES_ORG) {
			SALES_ORG = sALES_ORG;
		}
		public String getPLNT_CD() {
			return PLNT_CD;
		}
		public void setPLNT_CD(String pLNT_CD) {
			PLNT_CD = pLNT_CD;
		}
		public String getDEL_PLNT() {
			return DEL_PLNT;
		}
		public void setDEL_PLNT(String dEL_PLNT) {
			DEL_PLNT = dEL_PLNT;
		}
		public String getTAX_CAT() {
			return TAX_CAT;
		}
		public void setTAX_CAT(String tAX_CAT) {
			TAX_CAT = tAX_CAT;
		}
		public String getTAX_CLAS() {
			return TAX_CLAS;
		}
		public void setTAX_CLAS(String tAX_CLAS) {
			TAX_CLAS = tAX_CLAS;
		}
	}
	static class Generalarea{
		String GENAREACODE;
		String GENAREANAME_FC;
		public String getGENAREACODE() {
			return GENAREACODE;
		}
		public void setGENAREACODE(String gENAREACODE) {
			GENAREACODE = gENAREACODE;
		}
		public String getGENAREANAME_FC() {
			return GENAREANAME_FC;
		}
		public void setGENAREANAME_FC(String gENAREANAME_FC) {
			GENAREANAME_FC = gENAREANAME_FC;
		}
		
	}
}
