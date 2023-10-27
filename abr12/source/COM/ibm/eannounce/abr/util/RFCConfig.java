/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.CountryPlantTax;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.Generalarea;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.HSN;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*     */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*     */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*     */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RFCConfig
/*     */ {
/*  24 */   static List<CountryPlantTax> countryPlantTaxs = new ArrayList<>();
/*  25 */   static Map<String, String> orgplntMap = new HashMap<>();
/*  26 */   static Map<String, String> geneMap = new HashMap<>();
/*  27 */   static Map<String, Map<String, String>> orgpntMaps = new HashMap<>();
/*  28 */   static List<CountryPlantTax> taxs = new ArrayList<>();
/*  29 */   static List<Generalarea> generalareas = new ArrayList<>();
/*  30 */   static Set<String> bhplnts = new HashSet<>();
/*  31 */   static List<HSN> hsns = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*  35 */     System.out.println(hsns);
/*     */   }
/*     */   static {
/*  38 */     loadHSN();
/*  39 */     loadCountryPlantTax();
/*  40 */     loadGeneralarea();
/*  41 */     loadBHPlnt();
/*     */   }
/*     */   public static void loadHSN() {
/*  44 */     System.out.println("Loading HSN Lookup File!");
/*  45 */     File excel = new File("HSN_LOOKUP.xls");
/*     */ 
/*     */     
/*     */     try {
/*  49 */       if (excel.isFile() && excel.exists()) {
/*  50 */         System.out.println("HSN Lookup File Found!");
/*  51 */         String[] split = excel.getName().split("\\.");
/*     */         
/*  53 */         if ("xls".equals(split[1])) {
/*  54 */           FileInputStream fis = new FileInputStream(excel);
/*  55 */           HSSFWorkbook wb = new HSSFWorkbook(fis);
/*     */           
/*  57 */           HSSFSheet hssfSheet = wb.getSheetAt(0);
/*  58 */           int lastRow = hssfSheet.getLastRowNum();
/*  59 */           for (int i = 1; i <= lastRow; i++) {
/*  60 */             HSSFRow row = hssfSheet.getRow(i);
/*  61 */             HSN hsn = new HSN();
/*  62 */             if (row == null)
/*     */               break; 
/*  64 */             hsn.setCountry(getCellData(row.getCell(0)));
/*  65 */             hsn.setaLand(getCellData(row.getCell(1)));
/*  66 */             hsn.setMachType(getCellData(row.getCell(2)));
/*  67 */             hsn.setSteuc(getCellData(row.getCell(3)));
/*     */ 
/*     */ 
/*     */             
/*  71 */             hsns.add(hsn);
/*     */           } 
/*     */         } else {
/*     */           
/*  75 */           System.out.println("File type error!");
/*     */           return;
/*     */         } 
/*     */       } 
/*  79 */       System.out.println("HSN Lookup Data Loaded!");
/*     */ 
/*     */     
/*     */     }
/*  83 */     catch (Exception e) {
/*  84 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<HSN> getHsns() {
/*  92 */     return hsns;
/*     */   }
/*     */   public static void loadCountryPlantTax() {
/*  95 */     File excel = new File("COUNTRY_PLANT_TAX.xls");
/*     */ 
/*     */     
/*     */     try {
/*  99 */       if (excel.isFile() && excel.exists()) {
/*     */         
/* 101 */         String[] split = excel.getName().split("\\.");
/*     */         
/* 103 */         if ("xls".equals(split[1])) {
/* 104 */           FileInputStream fis = new FileInputStream(excel);
/* 105 */           HSSFWorkbook wb = new HSSFWorkbook(fis);
/*     */           
/* 107 */           HSSFSheet hssfSheet = wb.getSheetAt(0);
/* 108 */           int lastRow = hssfSheet.getLastRowNum();
/* 109 */           for (int i = 1; i <= lastRow; i++) {
/* 110 */             HSSFRow row = hssfSheet.getRow(i);
/* 111 */             CountryPlantTax tax = new CountryPlantTax();
/* 112 */             if (row == null)
/*     */               break; 
/* 114 */             tax.setINTERFACE_ID(getCellData(row.getCell(0)));
/* 115 */             tax.setSALES_ORG(getCellData(row.getCell(1)));
/* 116 */             tax.setPLNT_CD(getCellData(row.getCell(2)));
/* 117 */             tax.setDEL_PLNT(getCellData(row.getCell(3)));
/* 118 */             tax.setTAX_COUNTRY(getCellData(row.getCell(4)));
/* 119 */             tax.setTAX_CD(getCellData(row.getCell(6)));
/* 120 */             tax.setTAX_CAT(getCellData(row.getCell(7)));
/* 121 */             tax.setTAX_CLAS(getCellData(row.getCell(8)));
/* 122 */             if (tax.getINTERFACE_ID() == null || "".equals(tax.getINTERFACE_ID()))
/*     */               break; 
/* 124 */             orgplntMap = orgpntMaps.get(tax.getINTERFACE_ID());
/* 125 */             if (orgplntMap == null) {
/* 126 */               orgplntMap = new HashMap<>();
/* 127 */               orgpntMaps.put(tax.getINTERFACE_ID(), orgplntMap);
/*     */             } 
/* 129 */             orgplntMap.put(tax.getSALES_ORG(), tax.getDEL_PLNT());
/* 130 */             taxs.add(tax);
/*     */           } 
/*     */         } else {
/*     */           
/* 134 */           System.out.println("File type error!");
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 141 */     } catch (Exception e) {
/* 142 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadBHPlnt() {
/* 148 */     File excel = new File("BH_ORG_PRD_PROD.xls");
/*     */ 
/*     */     
/*     */     try {
/* 152 */       if (excel.isFile() && excel.exists()) {
/*     */         
/* 154 */         String[] split = excel.getName().split("\\.");
/*     */         
/* 156 */         if ("xls".equals(split[1])) {
/* 157 */           FileInputStream fis = new FileInputStream(excel);
/* 158 */           HSSFWorkbook wb = new HSSFWorkbook(fis);
/*     */           
/* 160 */           HSSFSheet hssfSheet = wb.getSheetAt(0);
/* 161 */           int lastRow = hssfSheet.getLastRowNum();
/* 162 */           for (int i = 1; i <= lastRow; i++) {
/* 163 */             HSSFRow row = hssfSheet.getRow(i);
/* 164 */             if (row == null)
/* 165 */               break;  String cat = getCellData(row.getCell(1));
/* 166 */             String plnt = getCellData(row.getCell(5));
/* 167 */             if ("Hardware".equals(cat.trim())) {
/* 168 */               bhplnts.add(plnt);
/*     */             }
/*     */           } 
/*     */         } else {
/* 172 */           System.out.println("File type error!");
/*     */           return;
/*     */         } 
/*     */       } 
/* 176 */     } catch (Exception e) {
/* 177 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Set<String> getBHPlnts() {
/* 182 */     return bhplnts;
/*     */   }
/*     */   
/*     */   public static String getCellData(HSSFCell cell) {
/* 186 */     if (cell == null)
/* 187 */       return null; 
/* 188 */     if (cell.getCellType() == 1) {
/* 189 */       return cell.getStringCellValue();
/*     */     }
/* 191 */     if (cell.getCellType() == 0) {
/* 192 */       return (new StringBuilder(String.valueOf((int)cell.getNumericCellValue()))).toString();
/*     */     }
/*     */     
/* 195 */     return null;
/*     */   }
/*     */   public static String getDwerk(String intID, String salesOrg) {
/* 198 */     return (orgpntMaps.get(intID) == null) ? null : (String)((Map)orgpntMaps.get(intID)).get(salesOrg);
/*     */   }
/*     */   public static String getPlant(String intID, String plant) {
/* 201 */     Iterator<Map.Entry> iter = ((Map)orgpntMaps.get(intID)).entrySet().iterator();
/* 202 */     while (iter.hasNext()) {
/* 203 */       Map.Entry entry = iter.next();
/* 204 */       String key = (String)entry.getKey();
/* 205 */       String value = (String)entry.getValue();
/* 206 */       if (plant.equals(value)) return key; 
/*     */     } 
/* 208 */     return null;
/*     */   }
/*     */   public static String getAland(String key) {
/* 211 */     return geneMap.get(key);
/*     */   }
/*     */   public static List<CountryPlantTax> getTaxs() {
/* 214 */     return taxs;
/*     */   }
/*     */   public static List<Generalarea> getGeneralareas() {
/* 217 */     return generalareas;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadGeneralarea() {
/*     */     try {
/* 223 */       File excel = new File("GENERALAREA_UPDATE_CBSE.xls");
/* 224 */       if (excel.isFile() && excel.exists()) {
/*     */         
/* 226 */         String[] split = excel.getName().split("\\.");
/*     */         
/* 228 */         if ("xls".equals(split[1])) {
/* 229 */           FileInputStream fis = new FileInputStream(excel);
/* 230 */           HSSFWorkbook wb = new HSSFWorkbook(fis);
/* 231 */           HSSFSheet hssfSheet = wb.getSheetAt(0);
/* 232 */           int lastRow = hssfSheet.getLastRowNum();
/* 233 */           for (int i = 1; i <= lastRow; i++) {
/* 234 */             HSSFRow row = hssfSheet.getRow(i);
/* 235 */             Generalarea generalarea = new Generalarea();
/* 236 */             if (row == null)
/*     */               break; 
/* 238 */             generalarea.setGENAREANAME_FC(getCellData(row.getCell(3)));
/* 239 */             generalarea.setGENAREACODE(getCellData(row.getCell(2)));
/* 240 */             if (generalarea.getGENAREACODE() == null || "".equals(generalarea.getGENAREACODE())) {
/*     */               break;
/*     */             }
/* 243 */             geneMap.put(generalarea.getGENAREANAME_FC(), generalarea.getGENAREACODE());
/* 244 */             generalareas.add(generalarea);
/*     */           } 
/*     */         } else {
/* 247 */           System.out.println("File type error!");
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 254 */     } catch (Exception e) {
/* 255 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\RFCConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */