/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*      */ import org.apache.poi.hssf.usermodel.HSSFCellStyle;
/*      */ import org.apache.poi.hssf.usermodel.HSSFDateUtil;
/*      */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*      */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*      */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*      */ import org.apache.poi.hssf.util.HSSFColor;
/*      */ import org.apache.poi.ss.usermodel.Cell;
/*      */ import org.apache.poi.ss.usermodel.FillPatternType;
/*      */ 
/*      */ public class COMPARIREPORTSTATUS extends PokBaseABR {
/*   40 */   private StringBuffer rptSb = new StringBuffer();
/*   41 */   private String reportFileName = null;
/*   42 */   private String reportDir = null;
/*      */   
/*   44 */   private String dataDir = null;
/*   45 */   private String backuptDir = null;
/*   46 */   private String lineStr = null;
/*   47 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   48 */   static final String NEWLINE = new String(FOOL_JTEST);
/*   49 */   private Object[] args = (Object[])new String[10];
/*      */   private static final String SUCCESS = "SUCCESS";
/*      */   private static final String FAILD = "FAILD";
/*      */   private static final String DATAPATH = "_datapath";
/*      */   private static final String BACKUPPATH = "_backuppath";
/*      */   private static final String REPORTPATH = "_reportpath";
/*   55 */   private String dataPath = null;
/*      */   
/*   57 */   private String annNumber = null;
/*   58 */   int rows = 0;
/*   59 */   int maxlen = 0;
/*   60 */   private static Map sheetMap = null;
/*   61 */   private Map sheetDataMap = null;
/*   62 */   private Map dbDataMap = null;
/*      */   public static final String MODEL = "MODEL";
/*      */   public static final String FEATURE = "FEATURE";
/*      */   public static final String MCONV = "MCONV";
/*      */   public static final String FCONV = "FCONV";
/*      */   public static final String UPDATEFC = "UPDATEFC";
/*   68 */   public static Map mappingMap = null;
/*   69 */   public static Map keysMap = null;
/*      */   
/*   71 */   private String annDate = null;
/*   72 */   private String annWDDate = null;
/*   73 */   private String gaDate = null;
/*      */   
/*   75 */   HSSFWorkbook workbook = new HSSFWorkbook();
/*   76 */   HSSFSheet sheet = this.workbook.createSheet("Comparison Result");
/*   77 */   private String[] sheets = new String[] { "MODEL", "FEATURE", "UPDATEFC", "MCONV", "FCONV" };
/*   78 */   Map map = new HashMap<>();
/*      */   static {
/*   80 */     keysMap = new HashMap<>();
/*   81 */     ArrayList<String> arrayList = new ArrayList();
/*   82 */     arrayList.add("MT");
/*   83 */     arrayList.add("Model");
/*   84 */     keysMap.put("MODEL", arrayList);
/*   85 */     arrayList = new ArrayList<>();
/*   86 */     arrayList.add("MTM");
/*   87 */     arrayList.add("Feature");
/*   88 */     keysMap.put("FEATURE", arrayList);
/*   89 */     arrayList = new ArrayList<>();
/*   90 */     arrayList.add("MTM");
/*   91 */     arrayList.add("Feature");
/*   92 */     keysMap.put("UPDATEFC", arrayList);
/*   93 */     arrayList = new ArrayList<>();
/*   94 */     arrayList.add("From MT");
/*   95 */     arrayList.add("From Model");
/*   96 */     arrayList.add("To MT");
/*   97 */     arrayList.add("To Model");
/*   98 */     keysMap.put("MCONV", arrayList);
/*   99 */     arrayList = new ArrayList<>();
/*  100 */     arrayList.add("From MT");
/*  101 */     arrayList.add("From Model");
/*  102 */     arrayList.add("To MT");
/*  103 */     arrayList.add("To Model");
/*  104 */     arrayList.add("From Feature");
/*  105 */     arrayList.add("To Feature");
/*  106 */     arrayList.add("From FC");
/*  107 */     arrayList.add("To FC");
/*  108 */     keysMap.put("FCONV", arrayList);
/*      */     
/*  110 */     sheetMap = new HashMap<>();
/*  111 */     sheetMap.put("Model data", "MODEL");
/*  112 */     sheetMap.put("Model Data", "MODEL");
/*  113 */     sheetMap.put("Updates to FCs", "UPDATEFC");
/*  114 */     sheetMap.put("Feature data", "FEATURE");
/*  115 */     sheetMap.put("Feature Data", "FEATURE");
/*      */     
/*  117 */     sheetMap.put("MConv", "MCONV");
/*      */ 
/*      */     
/*  120 */     sheetMap.put("Model Conv", "MCONV");
/*  121 */     sheetMap.put("FConv", "FCONV");
/*      */ 
/*      */     
/*  124 */     sheetMap.put("FC Conv", "FCONV");
/*      */ 
/*      */     
/*  127 */     mappingMap = new HashMap<>();
/*  128 */     HashMap<Object, Object> hashMap1 = new HashMap<>();
/*  129 */     mappingMap.put("MODEL", hashMap1);
/*  130 */     hashMap1.put("MT", "MACHTYPEATR");
/*  131 */     hashMap1.put("Model", "MODELATR");
/*      */ 
/*      */     
/*  134 */     hashMap1.put("Marketing Name", "MKTGNAME");
/*  135 */     hashMap1.put("Machine type name", "MODMKTGDESC");
/*  136 */     hashMap1.put("Price File Name", "INVNAME");
/*  137 */     hashMap1.put("Profit Center", "PRFTCTR");
/*  138 */     hashMap1.put("BH Prod Hier", "BHPRODHIERCD");
/*  139 */     hashMap1.put("BH AAG", "BHACCTASGNGRP");
/*  140 */     hashMap1.put("Priced", "PRCINDC");
/*  141 */     hashMap1.put("Special Bid", "SPECBID");
/*  142 */     hashMap1.put("Order Code", "MODELORDERCODE");
/*  143 */     hashMap1.put("Customer Setup", "INSTALL");
/*  144 */     hashMap1.put("LIC", "LICNSINTERCD");
/*  145 */     hashMap1.put("MLC", "MACHLVLCNTRL");
/*      */     
/*  147 */     hashMap1.put("GA Date", "GENAVAILDATE");
/*  148 */     hashMap1.put("Ann date", "ANNDATE");
/*  149 */     hashMap1.put("Ann WD date", "WITHDRAWDATE");
/*      */ 
/*      */ 
/*      */     
/*  153 */     hashMap1.put("Product ID", "PRODID");
/*  154 */     hashMap1.put("IBM Global Financing", "IBMCREDIT");
/*  155 */     hashMap1.put("ICR Category", "ICRCATEGORY");
/*  156 */     hashMap1.put("Volume Discount Eligible (IVO)", "VOLUMEDISCOUNTELIG");
/*  157 */     hashMap1.put("Function Class", "FUNCCLS");
/*  158 */     hashMap1.put("Education Purchase Eligibility", "EDUCPURCHELIG");
/*  159 */     hashMap1.put("Integrated Product", "INTEGRATEDMODEL");
/*  160 */     hashMap1.put("Plan of Manufacture", "PLNTOFMFR");
/*  161 */     hashMap1.put("Graduated Charge", "GRADUATEDCHARGE");
/*  162 */     hashMap1.put("Annual Maintenance (Maint. Billing period)", "ANNUALMAINT");
/*  163 */     hashMap1.put("EMEA Brand Code", "EMEABRANDCD");
/*  164 */     hashMap1.put("IBM Hourly Service Rate Classification", "CECSPRODKEY");
/*      */ 
/*      */     
/*  167 */     hashMap1.put("SW preload", "PRELOADSWINDC");
/*  168 */     hashMap1.put("Machine Rate Category", "MACHRATECATG");
/*  169 */     hashMap1.put("Maintenance Billing Indicator", "MAINTANNBILLELIGINDC");
/*  170 */     hashMap1.put("No Charge Maintenance", "NOCHRGMAINTINDC");
/*  171 */     hashMap1.put("CE / CSC Product Key", "CECSPRODKEY");
/*  172 */     hashMap1.put("Product Support Code", "PRODSUPRTCD");
/*  173 */     hashMap1.put("Retain Indicator", "RETANINDC");
/*  174 */     hashMap1.put("System Identification Unit", "SYSIDUNIT");
/*  175 */     hashMap1.put("WWOCCODE (GBT)", "WWOCCODE");
/*      */     
/*  177 */     hashMap1.put("UNSPSC", "UNSPSCCD");
/*  178 */     hashMap1.put("UNSPSC UOM", "UNSPSCCDUOM");
/*  179 */     hashMap1.put("Planning Relevant", "PLANRELEVANT");
/*  180 */     hashMap1.put("Project Code Name", "PROJCDNAM");
/*  181 */     hashMap1.put("Category", "COFCAT");
/*  182 */     hashMap1.put("Subcategory", "COFSUBCAT");
/*  183 */     hashMap1.put("Group", "COFGRP");
/*  184 */     hashMap1.put("Subgroup", "COFSUBGRP");
/*  185 */     hashMap1.put("Comp Dev Cat", "COMPATDVCCAT");
/*  186 */     hashMap1.put("Comp Dev Subcat", "COMPATDVCSUBCAT");
/*  187 */     hashMap1.put("Internal Name", "INTERNALNAME");
/*  188 */     hashMap1.put("WD Effect Date", "WTHDRWEFFCTVDATE");
/*      */     
/*  190 */     HashMap<Object, Object> hashMap2 = new HashMap<>();
/*  191 */     mappingMap.put("FEATURE", hashMap2);
/*      */     
/*  193 */     hashMap2.put("MTM", "MTM");
/*  194 */     hashMap2.put("Feature", "FEATURECODE");
/*  195 */     hashMap2.put("GEO/Country restriction", "COUNTRYLIST");
/*  196 */     hashMap2.put("GA date (if different)", "EFFECTIVEDATE");
/*  197 */     hashMap2.put("HW Feat Cat", "HWFCCAT");
/*  198 */     hashMap2.put("HW Feat Subcat", "HWFCSUBCAT");
/*  199 */     hashMap2.put("Price File Name (28 chars. Max)", "INVNAME");
/*  200 */     hashMap2.put("Marketing Name", "MKTGNAME");
/*  201 */     hashMap2.put("Maintenance Price", "MAINTPRICE");
/*  202 */     hashMap2.put("Priced", "PRICEDFEATURE");
/*  203 */     hashMap2.put("FC Marketing Description", "FCMKTGDESC");
/*  204 */     hashMap2.put("Ordercode", "ORDERCODE");
/*  205 */     hashMap2.put("Customer Setup", "INSTALL");
/*  206 */     hashMap2.put("Returned Parts MES", "RETURNEDPARTS");
/*  207 */     hashMap2.put("Initial Order Max", "INITORDERMAX");
/*  208 */     hashMap2.put("System Max", "SYSTEMMAX");
/*  209 */     hashMap2.put("System Min", "SYSTEMMIN");
/*  210 */     hashMap2.put("Prereq", "PREREQ");
/*  211 */     hashMap2.put("Coreq", "COREQUISITE");
/*  212 */     hashMap2.put("Limitations", "LMTATION");
/*  213 */     hashMap2.put("Compatibility", "COMPATIBILITY");
/*  214 */     hashMap2.put("Comments", "COMMENTS");
/*  215 */     hashMap2.put("Cable Order", "CBLORD");
/*  216 */     hashMap2.put("Configurator Flag Override", "CONFIGURATORFLAG");
/*  217 */     hashMap2.put("Bulk MES", "BULKMESINDC");
/*  218 */     hashMap2.put("Warranty", "WARRSVCCOVR");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  223 */     hashMap2.put("GA date", "GENAVAILDATE");
/*  224 */     hashMap2.put("Ann date", "ANNDATE");
/*  225 */     hashMap2.put("WD Effect Date", "WTHDRWEFFCTVDATE");
/*  226 */     hashMap2.put("Ann WD date", "WITHDRAWDATE");
/*      */     
/*  228 */     HashMap<Object, Object> hashMap3 = new HashMap<>();
/*  229 */     mappingMap.put("UPDATEFC", hashMap3);
/*  230 */     hashMap3.put("MTM", "MTM");
/*  231 */     hashMap3.put("Feature", "FEATURECODE");
/*  232 */     hashMap3.put("Returned Parts MES", "RETURNEDPARTS");
/*  233 */     hashMap3.put("Initial Order Max", "INITORDERMAX");
/*  234 */     hashMap3.put("System Max", "SYSTEMMAX");
/*  235 */     hashMap3.put("System Min", "SYSTEMMIN");
/*  236 */     hashMap3.put("Prereq", "PREREQ");
/*  237 */     hashMap3.put("Coreq", "COREQUISITE");
/*  238 */     hashMap3.put("Limitations", "LMTATION");
/*  239 */     hashMap3.put("Compatibility", "COMPATIBILITY");
/*  240 */     hashMap3.put("Comment", "COMMENTS");
/*  241 */     hashMap3.put("Cable Order", "CBLORD");
/*  242 */     hashMap3.put("Bulk MES", "BULKMESINDC");
/*      */     
/*  244 */     HashMap<Object, Object> hashMap4 = new HashMap<>();
/*      */     
/*  246 */     hashMap4.put("From MT", "FROMMACHTYPE");
/*  247 */     hashMap4.put("From Model", "FROMMODEL");
/*  248 */     hashMap4.put("From Feature", "FROMFEATURECODE");
/*  249 */     hashMap4.put("From FC", "FROMFEATURECODE");
/*  250 */     hashMap4.put("To MT", "TOMACHTYPE");
/*  251 */     hashMap4.put("To Model", "TOMODEL");
/*  252 */     hashMap4.put("To Feature", "TOFEATURECODE");
/*  253 */     hashMap4.put("To FC", "TOFEATURECODE");
/*  254 */     hashMap4.put("Feature Transaction Category", "FTCAT");
/*  255 */     hashMap4.put("Returned Parts MES", "RETURNEDPARTS");
/*  256 */     hashMap4.put("Zero Price", "ZEROPRICE");
/*  257 */     hashMap4.put("WD Effect Date", "WTHDRWEFFCTVDATE");
/*      */     
/*  259 */     hashMap4.put("Ann WD date", "WITHDRAWDATE");
/*  260 */     hashMap4.put("Ann date", "ANNDATE");
/*  261 */     hashMap4.put("GA date", "GENAVAILDATE");
/*  262 */     mappingMap.put("FCONV", hashMap4);
/*  263 */     HashMap<Object, Object> hashMap5 = new HashMap<>();
/*  264 */     mappingMap.put("MCONV", hashMap5);
/*  265 */     hashMap5.put("From MT", "FROMMACHTYPE");
/*  266 */     hashMap5.put("From Model", "FROMMODEL");
/*  267 */     hashMap5.put("To MT", "TOMACHTYPE");
/*  268 */     hashMap5.put("To Model", "TOMODEL");
/*  269 */     hashMap5.put("Returned Parts MES", "RETURNEDPARTS");
/*  270 */     hashMap5.put("WD Effect Date", "WTHDRWEFFCTVDATE");
/*      */     
/*  272 */     hashMap5.put("WD Effect Date", "WTHDRWEFFCTVDATE");
/*  273 */     hashMap5.put("Ann WD date", "WITHDRAWDATE");
/*  274 */     hashMap5.put("Ann date", "ANNDATE");
/*  275 */     hashMap5.put("GA date", "GENAVAILDATE");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFileName() throws IOException {
/*  282 */     this.reportDir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_reportpath", "/Dgq");
/*  283 */     if (!this.reportDir.endsWith("/")) {
/*  284 */       this.reportDir += "/";
/*      */     }
/*  286 */     this.dataDir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_datapath", "/Dgq");
/*  287 */     if (!this.reportDir.endsWith("/")) {
/*  288 */       this.reportDir += "/";
/*      */     }
/*  290 */     String str = getNow();
/*      */     
/*  292 */     str = str.replace(' ', '_');
/*  293 */     this.backuptDir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_backuppath", "/Dgq");
/*  294 */     this.reportFileName = this.reportDir + "dbdata" + str + ".xls";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  302 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  304 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Result: </th><td>{4}</td></tr>" + NEWLINE + "</table><!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  309 */     String str3 = "";
/*  310 */     String[] arrayOfString = new String[10];
/*  311 */     String str4 = "";
/*  312 */     boolean bool1 = true;
/*  313 */     boolean bool2 = true;
/*      */     
/*  315 */     this.dbDataMap = new HashMap<>();
/*  316 */     this.sheetDataMap = new HashMap<>();
/*      */     
/*      */     try {
/*  319 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  320 */       arrayOfString[0] = getShortClassName(getClass());
/*  321 */       arrayOfString[1] = "ABR";
/*  322 */       str4 = messageFormat.format(arrayOfString);
/*  323 */       start_ABRBuild(false);
/*  324 */       setDGTitle(getDescription() + " report");
/*  325 */       setDGString(getABRReturnCode());
/*  326 */       setDGRptName(getDescription());
/*  327 */       setDGRptClass(getDescription());
/*      */       
/*  329 */       generateFlatFile();
/*  330 */       sendMail(this.reportFileName);
/*  331 */       setReturnCode(0);
/*      */     }
/*  333 */     catch (Exception exception) {
/*      */       
/*  335 */       exception.printStackTrace();
/*      */       
/*  337 */       setReturnCode(-1);
/*  338 */       StringWriter stringWriter = new StringWriter();
/*  339 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  340 */       String str6 = "<pre>{0}</pre>";
/*  341 */       MessageFormat messageFormat = new MessageFormat(str5);
/*  342 */       setReturnCode(-3);
/*  343 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  345 */       arrayOfString[0] = exception.getMessage();
/*  346 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/*  347 */       messageFormat = new MessageFormat(str6);
/*  348 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  349 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/*  350 */       logError("Exception: " + exception.getMessage());
/*  351 */       logError(stringWriter.getBuffer().toString());
/*      */       
/*  353 */       setCreateDGEntity(true);
/*  354 */       bool1 = false;
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/*  360 */       StringBuffer stringBuffer = new StringBuffer();
/*  361 */       MessageFormat messageFormat = new MessageFormat(str2);
/*  362 */       arrayOfString[0] = this.m_prof.getOPName();
/*  363 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/*  364 */       arrayOfString[2] = this.m_prof.getWGName();
/*  365 */       arrayOfString[3] = getNow();
/*  366 */       stringBuffer.append(bool1 ? "generated the COMPARIREPORTSTATUS report file successful " : "generated the  COMPARIREPORTSTATUS file faild");
/*      */       
/*  368 */       stringBuffer.append(",");
/*  369 */       stringBuffer.append(bool2 ? "send the COMPARIREPORTSTATUS report file successful " : "sent the COMPARIREPORTSTATUS report file faild");
/*      */       
/*  371 */       if (!bool2)
/*  372 */         stringBuffer.append(this.lineStr.replaceFirst("FAILD", "")); 
/*  373 */       arrayOfString[4] = stringBuffer.toString();
/*  374 */       arrayOfString[5] = str3 + " " + getABRVersion();
/*      */       
/*  376 */       this.rptSb.insert(0, str4 + messageFormat.format(arrayOfString) + NEWLINE);
/*      */       
/*  378 */       println(EACustom.getDocTypeHtml());
/*  379 */       println(this.rptSb.toString());
/*  380 */       printDGSubmitString();
/*      */       
/*  382 */       println(EACustom.getTOUDiv());
/*  383 */       buildReportFooter();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void test() throws IOException, MiddlewareException, SQLException {
/*  391 */     this.sheetDataMap = new HashMap<>();
/*  392 */     this.backuptDir = "C:\\Users\\JianBoXu\\Desktop\\test\\backup\\";
/*  393 */     this.dataDir = "C:\\Users\\JianBoXu\\Desktop\\test\\data\\";
/*  394 */     this.reportFileName = "C:\\Users\\JianBoXu\\Desktop\\test\\report\\report.xls";
/*  395 */     FileOutputStream fileOutputStream = new FileOutputStream(this.reportFileName);
/*      */ 
/*      */ 
/*      */     
/*  399 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*  400 */     File file = new File(this.dataDir);
/*  401 */     Pattern pattern = Pattern.compile("(\\d{5})");
/*  402 */     if (file != null && file.isDirectory()) {
/*  403 */       File[] arrayOfFile = file.listFiles();
/*  404 */       for (byte b = 0; b < arrayOfFile.length; b++) {
/*  405 */         Matcher matcher = pattern.matcher(arrayOfFile[b].getName());
/*  406 */         if (matcher.find()) {
/*  407 */           this.annNumber = matcher.group(1);
/*      */           
/*  409 */           testData();
/*  410 */           loadDataFromExcel(arrayOfFile[b]);
/*      */           
/*  412 */           String str = compare();
/*      */           
/*  414 */           File file1 = new File(this.backuptDir + arrayOfFile[b].getName());
/*  415 */           arrayOfFile[b].renameTo(file1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  423 */     this.workbook.write(fileOutputStream);
/*  424 */     outputStreamWriter.write("\n");
/*  425 */     outputStreamWriter.flush();
/*      */     
/*  427 */     outputStreamWriter.flush();
/*  428 */     System.out.println("DONE!");
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateFlatFile() throws IOException, MiddlewareException, SQLException {
/*  433 */     setFileName();
/*  434 */     FileOutputStream fileOutputStream = new FileOutputStream(this.reportFileName);
/*      */ 
/*      */ 
/*      */     
/*  438 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*  439 */     File file = new File(this.dataDir);
/*  440 */     Pattern pattern = Pattern.compile("(\\d{5})");
/*  441 */     if (file != null && file.isDirectory()) {
/*  442 */       File[] arrayOfFile = file.listFiles();
/*  443 */       addDebug("File number:" + arrayOfFile.length);
/*  444 */       for (byte b = 0; b < arrayOfFile.length; b++) {
/*  445 */         addDebug("processing:" + arrayOfFile[b]);
/*  446 */         Matcher matcher = pattern.matcher(arrayOfFile[b].getName());
/*  447 */         if (matcher.find()) {
/*  448 */           this.annNumber = matcher.group(1);
/*  449 */           loadDateFormDB();
/*  450 */           loadDataFromExcel(arrayOfFile[b]);
/*  451 */           String str = compare();
/*      */           
/*  453 */           File file1 = new File(this.backuptDir + arrayOfFile[b].getName());
/*  454 */           arrayOfFile[b].renameTo(file1);
/*  455 */           addDebug("processed:" + arrayOfFile[b]);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  463 */     this.workbook.write(fileOutputStream);
/*  464 */     outputStreamWriter.write("\n");
/*  465 */     outputStreamWriter.flush();
/*      */     
/*  467 */     outputStreamWriter.flush();
/*      */   }
/*      */   
/*      */   protected String getBlank(int paramInt) {
/*  471 */     StringBuffer stringBuffer = new StringBuffer();
/*  472 */     while (paramInt > 0) {
/*  473 */       stringBuffer.append(" ");
/*  474 */       paramInt--;
/*      */     } 
/*  476 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*  480 */     return "COMPARIREPORTSTATUS";
/*      */   }
/*      */ 
/*      */   
/*      */   public String compare() {
/*  485 */     StringBuffer stringBuffer = new StringBuffer();
/*  486 */     HSSFRow hSSFRow = this.sheet.createRow(this.rows++);
/*  487 */     HSSFCellStyle hSSFCellStyle1 = this.workbook.createCellStyle();
/*  488 */     hSSFCellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
/*  489 */     hSSFCellStyle1.setFillForegroundColor(HSSFColor.YELLOW.index);
/*  490 */     HSSFCellStyle hSSFCellStyle2 = this.workbook.createCellStyle();
/*  491 */     hSSFCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
/*  492 */     hSSFCellStyle2.setFillForegroundColor(HSSFColor.GREEN.index);
/*  493 */     HSSFCellStyle hSSFCellStyle3 = this.workbook.createCellStyle();
/*  494 */     hSSFCellStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
/*  495 */     hSSFCellStyle3.setFillForegroundColor(HSSFColor.RED.index);
/*  496 */     HSSFCellStyle hSSFCellStyle4 = this.workbook.createCellStyle();
/*  497 */     hSSFCellStyle4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
/*  498 */     hSSFCellStyle4.setFillForegroundColor(HSSFColor.BLUE.index);
/*      */     
/*  500 */     HSSFCell hSSFCell = hSSFRow.createCell(0);
/*  501 */     hSSFCell.setCellValue("AnnNumber:" + this.annNumber);
/*  502 */     hSSFCell.setCellStyle(hSSFCellStyle1); byte b;
/*  503 */     for (b = 1; b < this.maxlen; b++) {
/*  504 */       hSSFRow.createCell((short)b).setCellStyle(hSSFCellStyle1);
/*      */     }
/*      */     
/*  507 */     for (b = 0; b < this.sheets.length; b++) {
/*  508 */       String[][] arrayOfString1 = (String[][])this.sheetDataMap.get(this.sheets[b]);
/*  509 */       String[][] arrayOfString2 = (String[][])this.dbDataMap.get(this.sheets[b]);
/*  510 */       Map map = (Map)mappingMap.get(this.sheets[b]);
/*  511 */       HashMap<Object, Object> hashMap1 = new HashMap<>();
/*  512 */       HashMap<Object, Object> hashMap2 = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  520 */       if (arrayOfString1 != null && arrayOfString2 != null) {
/*      */ 
/*      */         
/*  523 */         stringBuffer.append("Type:" + this.sheets[b] + "\r\n");
/*  524 */         hSSFRow = this.sheet.createRow(this.rows++);
/*  525 */         hSSFRow.createCell(0).setCellValue("Type:" + this.sheets[b]);
/*  526 */         byte b1 = 0;
/*  527 */         hSSFRow = this.sheet.createRow(this.rows++);
/*      */         
/*  529 */         for (byte b2 = 0; b2 < (arrayOfString1[0]).length; b2++) {
/*  530 */           String str = arrayOfString1[0][b2];
/*  531 */           if (str != null && !str.trim().equals("") && str.indexOf("Action") == -1) {
/*      */             
/*  533 */             stringBuffer.append(str);
/*      */ 
/*      */             
/*  536 */             hSSFRow.createCell((short)b1++).setCellValue(str);
/*  537 */             if (b2 == (arrayOfString1[0]).length - 1) {
/*  538 */               hSSFRow.createCell((short)b1++).setCellValue("Result");
/*  539 */               stringBuffer.append(",Result\r\n");
/*      */             } 
/*      */ 
/*      */             
/*  543 */             hashMap1.put(str, b2 + "");
/*  544 */             for (byte b4 = 0; b4 < (arrayOfString2[0]).length; b4++) {
/*  545 */               String str1 = arrayOfString2[0][b4];
/*  546 */               if (str1 != null && !str1.trim().equals(""))
/*      */               {
/*  548 */                 if (str1.trim().equals(map.get(str))) {
/*  549 */                   hashMap2.put(str, b4 + "");
/*      */                   break;
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*  556 */         List list = (List)keysMap.get(this.sheets[b]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  563 */         for (byte b3 = 1; b3 < arrayOfString1.length; b3++) {
/*      */           
/*  565 */           boolean bool = true;
/*      */           byte b4;
/*  567 */           for (b4 = 0; b4 < (arrayOfString1[b3]).length; b4++) {
/*  568 */             if (arrayOfString1[b3][b4] != null && !arrayOfString1[b3][b4].trim().equals("")) {
/*  569 */               bool = false;
/*      */               break;
/*      */             } 
/*      */           } 
/*  573 */           if (this.sheets[b].equals("MODEL")) {
/*  574 */             addDebug("l:" + b3);
/*      */           }
/*  576 */           if (!bool) {
/*      */ 
/*      */             
/*  579 */             hSSFRow = this.sheet.createRow(this.rows++);
/*  580 */             b1 = 0;
/*      */             
/*  582 */             for (b4 = 1; b4 < arrayOfString2.length; b4++) {
/*      */               
/*  584 */               byte b5 = 0;
/*  585 */               byte b6 = 0;
/*  586 */               if (this.sheets[b].equals("MODEL")) {
/*  587 */                 addDebug("m:" + b4);
/*      */               }
/*  589 */               for (Iterator<E> iterator = list.iterator(); iterator.hasNext(); ) {
/*      */                 
/*  591 */                 String str = iterator.next().toString();
/*  592 */                 if (hashMap1.get(str) == null)
/*      */                   continue; 
/*  594 */                 int i = Integer.parseInt(hashMap1.get(str).toString());
/*  595 */                 int j = Integer.parseInt(hashMap2.get(str).toString());
/*      */                 
/*  597 */                 if (arrayOfString1[b3][i] != null && !arrayOfString1[b3][i].trim().equals("")) {
/*      */ 
/*      */                   
/*  600 */                   String str1 = arrayOfString1[b3][i];
/*  601 */                   String str2 = arrayOfString2[b4][j];
/*  602 */                   b6++;
/*  603 */                   if (str1 != null && str2 != null && !str1.trim().equals("") && str1.trim().equals(str2.trim())) {
/*  604 */                     b5++;
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/*  610 */               if (b6 > 0 && b6 == b5) {
/*      */ 
/*      */                 
/*  613 */                 boolean bool1 = true;
/*  614 */                 String str = null;
/*      */                 
/*  616 */                 for (byte b7 = 1; b7 < (arrayOfString1[0]).length; b7++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  625 */                   if (arrayOfString1[0][b7] != null && !arrayOfString1[0][b7].trim().equals("")) {
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  630 */                     String str1 = arrayOfString1[b3][b7];
/*      */                     
/*  632 */                     if (hashMap2.get(arrayOfString1[0][b7]) == null)
/*  633 */                     { if (this.sheets[b].equals("MODEL")) {
/*  634 */                         addDebug(arrayOfString1[0][b7] + ":all null:" + b1);
/*      */                       }
/*  636 */                       hSSFRow.createCell((short)b1++).setCellValue((str1 == null) ? "" : (str1 + ""));
/*  637 */                       stringBuffer.append((str1 == null) ? "," : (str1 + ",")); }
/*      */                     else
/*      */                     
/*  640 */                     { int i = Integer.parseInt(hashMap2.get(arrayOfString1[0][b7]).toString());
/*  641 */                       String str2 = arrayOfString2[b4][i];
/*      */                       
/*  643 */                       if (this.sheets[b].equals("MODEL")) {
/*  644 */                         addDebug("Compare:Data:" + arrayOfString1[0][b7] + ":" + str1 + " dbData:" + arrayOfString2[b4][i] + ":" + str2);
/*      */                       }
/*      */ 
/*      */                       
/*  648 */                       if (str1 == null || str2 == null || str2.trim().equals("") || 
/*  649 */                         !str2.trim().equals(str1.trim())) {
/*      */ 
/*      */ 
/*      */                         
/*  653 */                         if (str == null) {
/*  654 */                           str = arrayOfString1[0][b7];
/*      */                         } else {
/*  656 */                           str = str + "|" + arrayOfString1[0][b7];
/*      */                         } 
/*  658 */                         bool1 = false;
/*      */                       } 
/*  660 */                       if (this.sheets[b].equals("MODEL")) {
/*  661 */                         addDebug(arrayOfString1[0][b7] + "setdata" + b1);
/*      */                       }
/*  663 */                       hSSFRow.createCell((short)b1++).setCellValue(((str1 == null) ? "" : str1) + "");
/*  664 */                       stringBuffer.append(((str1 == null) ? "" : str1) + ","); } 
/*      */                   } 
/*  666 */                 }  if (bool1) {
/*  667 */                   if (this.sheets[b].equals("MODEL")) {
/*  668 */                     addDebug("Match");
/*      */                   }
/*      */                   
/*  671 */                   hSSFCell = hSSFRow.createCell((short)b1++);
/*  672 */                   hSSFCell.setCellStyle(hSSFCellStyle2);
/*  673 */                   hSSFCell.setCellValue("All Match");
/*  674 */                   stringBuffer.append("All Match");
/*      */                 } else {
/*  676 */                   if (this.sheets[b].equals("MODEL")) {
/*  677 */                     addDebug("Mis Matchï¼" + str);
/*      */                   }
/*      */                   
/*  680 */                   hSSFCell = hSSFRow.createCell((short)b1++);
/*  681 */                   hSSFCell.setCellStyle(hSSFCellStyle3);
/*      */                   
/*  683 */                   hSSFCell.setCellValue(str);
/*  684 */                   stringBuffer.append(str);
/*      */                 } 
/*  686 */                 stringBuffer.append("\r\n");
/*      */                 
/*      */                 break;
/*      */               } 
/*  690 */               if (b4 == arrayOfString2.length - 1) {
/*      */ 
/*      */                 
/*  693 */                 for (byte b7 = 1; b7 < (arrayOfString1[0]).length; b7++) {
/*  694 */                   if (arrayOfString1[0][b7] != null && !arrayOfString1[0][b7].trim().equals("")) {
/*      */                     
/*  696 */                     String str = arrayOfString1[b3][b7];
/*  697 */                     stringBuffer.append(((str == null) ? "" : str) + ",");
/*  698 */                     hSSFRow.createCell((short)b1++).setCellValue(((str == null) ? "" : str) + "");
/*      */                   } 
/*  700 */                 }  stringBuffer.append("Not found\r\n");
/*  701 */                 hSSFCell = hSSFRow.createCell((short)b1++);
/*  702 */                 hSSFCell.setCellStyle(hSSFCellStyle4);
/*  703 */                 hSSFCell.setCellValue("Not found");
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  709 */         this.rows++;
/*      */       } 
/*      */     } 
/*  712 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadDataFromExcel(File paramFile) {
/*      */     try {
/*  721 */       if (paramFile.isFile() && paramFile.exists()) {
/*      */         
/*  723 */         String[] arrayOfString = paramFile.getName().split("\\.");
/*      */         
/*  725 */         if ("xls".equals(arrayOfString[1])) {
/*  726 */           FileInputStream fileInputStream = new FileInputStream(paramFile);
/*  727 */           HSSFWorkbook hSSFWorkbook = new HSSFWorkbook(fileInputStream);
/*  728 */           int i = hSSFWorkbook.getNumberOfSheets();
/*      */           byte b;
/*  730 */           for (b = 0; b < i; b++) {
/*      */             
/*  732 */             String str = hSSFWorkbook.getSheetName(b);
/*  733 */             addDebug("sheetName:" + str);
/*  734 */             if (str != null && str.indexOf("Announcement Info") != -1) {
/*      */               
/*  736 */               HSSFSheet hSSFSheet = hSSFWorkbook.getSheetAt(b);
/*  737 */               String str1 = hSSFSheet.getRow(0).getCell(0).getStringCellValue();
/*      */               
/*  739 */               byte b1 = 0;
/*  740 */               if (str1 != null && str1.indexOf("Withdrawal") != -1) {
/*  741 */                 b1 = 2;
/*      */               } else {
/*      */                 
/*  744 */                 b1 = 1;
/*      */               } 
/*  746 */               for (byte b2 = 1; b2 < hSSFSheet.getLastRowNum(); b2++) {
/*      */                 
/*  748 */                 if (hSSFSheet.getRow(b2) != null) {
/*      */                   
/*  750 */                   String str2 = getCellData(hSSFSheet.getRow(b2).getCell(0)).toString();
/*  751 */                   addDebug("key:" + str2);
/*  752 */                   if (str2.toLowerCase().trim().equals("ann date:")) {
/*  753 */                     this.annDate = getCellData(hSSFSheet.getRow(b2).getCell((short)b1)).toString();
/*  754 */                     addDebug("annDate:" + this.annDate);
/*  755 */                   } else if (str2.toLowerCase().trim().equals("ann wd date:")) {
/*  756 */                     this.annWDDate = getCellData(hSSFSheet.getRow(b2).getCell((short)b1)).toString();
/*  757 */                     addDebug("annWDDate:" + this.annWDDate);
/*  758 */                   } else if (str2.toLowerCase().trim().equals("ga date:")) {
/*  759 */                     this.gaDate = getCellData(hSSFSheet.getRow(b2).getCell((short)b1)).toString();
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */           
/*  768 */           for (b = 0; b < i; b++)
/*      */           {
/*  770 */             String str = getType(hSSFWorkbook.getSheetName(b));
/*      */ 
/*      */             
/*  773 */             if (str != null)
/*      */             {
/*  775 */               HSSFSheet hSSFSheet = hSSFWorkbook.getSheetAt(b);
/*  776 */               if (hSSFSheet != null)
/*      */               {
/*  778 */                 int j = hSSFSheet.getFirstRowNum();
/*  779 */                 int k = hSSFSheet.getLastRowNum();
/*  780 */                 short s1 = hSSFSheet.getRow(j).getFirstCellNum();
/*  781 */                 short s2 = hSSFSheet.getRow(j).getLastCellNum();
/*  782 */                 int m = k - j + 1;
/*  783 */                 int n = s2 - s1;
/*  784 */                 if (str.equals("MODEL")) {
/*  785 */                   n++;
/*  786 */                 } else if (str.equals("MCONV") || str.equals("FCONV")) {
/*  787 */                   n += 3;
/*  788 */                 } else if (str.equals("FEATURE")) {
/*  789 */                   n += 2;
/*      */                 } 
/*  791 */                 if (m >= 2)
/*      */                 {
/*      */                   
/*  794 */                   String[][] arrayOfString1 = new String[m][n];
/*  795 */                   for (int i1 = j; i1 <= k; i1++) {
/*  796 */                     for (short s = s1; s < s2; s++) {
/*      */                       
/*  798 */                       HSSFCell hSSFCell = (hSSFSheet.getRow(i1) == null) ? null : hSSFSheet.getRow(i1).getCell((short)s);
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  803 */                       String str1 = getCellData(hSSFCell);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  813 */                       arrayOfString1[i1 - j][s - s1] = str1;
/*      */                     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  820 */                     if (!isAllNull(arrayOfString1[i1 - j]))
/*      */                     {
/*  822 */                       if (str.equals("MODEL")) {
/*  823 */                         if (i1 == j) {
/*  824 */                           arrayOfString1[i1 - j][n - 1] = "Ann date";
/*      */                         }
/*  826 */                         else if (!isAllNull(arrayOfString1[i1 - j])) {
/*  827 */                           arrayOfString1[i1 - j][n - 1] = this.annDate;
/*      */                         }
/*      */                       
/*  830 */                       } else if (str.equals("FEATURE")) {
/*      */                         
/*  832 */                         if (i1 == j) {
/*  833 */                           arrayOfString1[i1 - j][n - 1] = "Ann WD date";
/*  834 */                           arrayOfString1[i1 - j][n - 2] = "Ann date";
/*      */                         
/*      */                         }
/*  837 */                         else if (!isAllNull(arrayOfString1[i1 - j])) {
/*  838 */                           arrayOfString1[i1 - j][n - 1] = this.annWDDate;
/*  839 */                           arrayOfString1[i1 - j][n - 2] = this.annDate;
/*      */                         }
/*      */                       
/*      */                       }
/*  843 */                       else if (str.equals("MCONV") || str.equals("FCONV")) {
/*  844 */                         if (i1 == j) {
/*  845 */                           arrayOfString1[i1 - j][n - 1] = "Ann WD date";
/*  846 */                           arrayOfString1[i1 - j][n - 2] = "Ann date";
/*  847 */                           arrayOfString1[i1 - j][n - 3] = "GA date";
/*      */                         
/*      */                         }
/*  850 */                         else if (!isAllNull(arrayOfString1[i1 - j])) {
/*  851 */                           arrayOfString1[i1 - j][n - 1] = this.annWDDate;
/*  852 */                           arrayOfString1[i1 - j][n - 2] = this.annDate;
/*  853 */                           arrayOfString1[i1 - j][n - 3] = this.gaDate;
/*      */                         } 
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  862 */                   this.sheetDataMap.put(str, arrayOfString1);
/*  863 */                   this.maxlen = Math.max(this.maxlen, (arrayOfString1[0]).length);
/*      */                 
/*      */                 }
/*      */ 
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  877 */           System.out.println("File type error!");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  908 */     } catch (Exception exception) {
/*  909 */       exception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAllNull(String[] paramArrayOfString) {
/*  915 */     if (paramArrayOfString == null || paramArrayOfString.length < 2)
/*  916 */       return true; 
/*  917 */     for (byte b = 1; b < paramArrayOfString.length; b++) {
/*  918 */       if (paramArrayOfString[b] != null && !paramArrayOfString[b].trim().equals("")) {
/*  919 */         return false;
/*      */       }
/*      */     } 
/*  922 */     return true;
/*      */   }
/*      */   
/*      */   private static String getCellData(HSSFCell paramHSSFCell) {
/*      */     short s;
/*  927 */     if (paramHSSFCell == null)
/*  928 */       return ""; 
/*  929 */     String str = "";
/*  930 */     DecimalFormat decimalFormat = new DecimalFormat("0");
/*  931 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*  932 */     switch (paramHSSFCell.getCellType()) {
/*      */       
/*      */       case 1:
/*  935 */         str = paramHSSFCell.getStringCellValue();
/*      */         break;
/*      */       case 0:
/*  938 */         s = paramHSSFCell.getCellStyle().getDataFormat();
/*      */         
/*  940 */         if (HSSFDateUtil.isCellDateFormatted((Cell)paramHSSFCell)) {
/*  941 */           Date date = paramHSSFCell.getDateCellValue();
/*  942 */           if (date != null) {
/*  943 */             str = (new SimpleDateFormat("yyyy-MM-dd")).format(date); break;
/*      */           } 
/*  945 */           str = "";
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/*  950 */         str = (new DecimalFormat("0")).format(paramHSSFCell.getNumericCellValue());
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/*  955 */         str = "";
/*      */         break;
/*      */       default:
/*  958 */         str = paramHSSFCell.toString();
/*      */         break;
/*      */     } 
/*  961 */     return (str == null) ? "" : str.trim();
/*      */   }
/*      */   
/*      */   public void loadDateFormDB() {
/*  965 */     loadFeature();
/*  966 */     loadModel();
/*  967 */     loadFeatureTrans();
/*      */     
/*  969 */     loadModelCovert();
/*  970 */     loadUpdate();
/*      */   }
/*      */ 
/*      */   
/*      */   public void testData() {
/*  975 */     this.dbDataMap = new HashMap<>();
/*  976 */     String[][] arrayOfString = { { "MACHTYPEATR", "MODELATR", "COUNTRYLIST", "EFFECTIVEDATE", "MKTGNAME", "MODMKTGDESC", "INVNAME", "PRFTCTR", "BHPRODHIERCD", "BHACCTASGNGRP", "PRCINDC", "SPECBID", "MODELORDERCODE", "INSTALL", "LICNSINTERCD", "MACHLVLCNTRL", "PRODID", "IBMCREDIT", "ICRCATEGORY", "VOLUMEDISCOUNTELIG", "FUNCCLS", "EDUCPURCHELIG", "INTEGRATEDMODEL", "PLNTOFMFR", "GRADUATEDCHARGE", "ANNUALMAINT", "EMEABRANDCD", "CECSPRODKEY", "PRELOADSWINDC", "MACHRATECATG", "MAINTANNBILLELIGINDC", "NOCHRGMAINTINDC", "CECSPRODKEY", "PRODSUPRTCD", "RETANINDC", "SYSIDUNIT", "WWOCCODE", "UNSPSCCD", "UNSPSCCDUOM", "PLANRELEVANT", "PROJCDNAM", "COFCAT", "COFSUBCAT", "COFGRP", "COFSUBGRP", "COMPATDVCCAT", "COMPATDVCSUBCAT", "INTERNALNAME", "WTHDRWEFFCTVDATE", "ANNDATE" }, { "3592", "E08", "COUNTRYLIST0", "EFFECTIVEDATE0", "MKTGNAME0", "MODMKTGDESC0", "INVNAME0", "PRFTCTR0", "BHPRODHIERCD0", "BHACCTASGNGRP0", "PRCINDC0", "SPECBID0", "MODELORDERCODE0", "INSTALL0", "LICNSINTERCD0", "MACHLVLCNTRL0", "PRODID0", "IBMCREDIT0", "ICRCATEGORY0", "VOLUMEDISCOUNTELIG0", "FUNCCLS0", "EDUCPURCHELIG0", "INTEGRATEDMODEL0", "PLNTOFMFR0", "GRADUATEDCHARGE0", "ANNUALMAINT0", "EMEABRANDCD0", "CECSPRODKEY0", "PRELOADSWINDC0", "MACHRATECATG0", "MAINTANNBILLELIGINDC0", "NOCHRGMAINTINDC0", "CECSPRODKEY0", "PRODSUPRTCD0", "RETANINDC0", "SYSIDUNIT0", "WWOCCODE0", "UNSPSCCD0", "UNSPSCCDUOM0", "PLANRELEVANT0", "PROJCDNAM0", "COFCAT0", "COFSUBCAT0", "COFGRP0", "COFSUBGRP0", "COMPATDVCCAT0", "COMPATDVCSUBCAT0", "INTERNALNAME0", "WTHDRWEFFCTVDATE0", "2018-10-09" }, { "MACHTYPEATR1", "MODELATR1", "COUNTRYLIST1", "EFFECTIVEDATE1", "MKTGNAME1", "MODMKTGDESC1", "INVNAME1", "PRFTCTR1", "BHPRODHIERCD1", "BHACCTASGNGRP1", "PRCINDC1", "SPECBID1", "MODELORDERCODE1", "INSTALL1", "LICNSINTERCD1", "MACHLVLCNTRL1", "PRODID1", "IBMCREDIT1", "ICRCATEGORY1", "VOLUMEDISCOUNTELIG1", "FUNCCLS1", "EDUCPURCHELIG1", "INTEGRATEDMODEL1", "PLNTOFMFR1", "GRADUATEDCHARGE1", "ANNUALMAINT1", "EMEABRANDCD1", "CECSPRODKEY1", "PRELOADSWINDC1", "MACHRATECATG1", "MAINTANNBILLELIGINDC1", "NOCHRGMAINTINDC1", "CECSPRODKEY1", "PRODSUPRTCD1", "RETANINDC1", "SYSIDUNIT1", "WWOCCODE1", "UNSPSCCD1", "UNSPSCCDUOM1", "PLANRELEVANT1", "PROJCDNAM1", "COFCAT1", "COFSUBCAT1", "COFGRP1", "COFSUBGRP1", "COMPATDVCCAT1", "COMPATDVCSUBCAT1", "INTERNALNAME1", "WTHDRWEFFCTVDATE1", "2018-09-11" }, { "MACHTYPEATR2", "MODELATR2", "COUNTRYLIST2", "EFFECTIVEDATE2", "MKTGNAME2", "MODMKTGDESC2", "INVNAME2", "PRFTCTR2", "BHPRODHIERCD2", "BHACCTASGNGRP2", "PRCINDC2", "SPECBID2", "MODELORDERCODE2", "INSTALL2", "LICNSINTERCD2", "MACHLVLCNTRL2", "PRODID2", "IBMCREDIT2", "ICRCATEGORY2", "VOLUMEDISCOUNTELIG2", "FUNCCLS2", "EDUCPURCHELIG2", "INTEGRATEDMODEL2", "PLNTOFMFR2", "GRADUATEDCHARGE2", "ANNUALMAINT2", "EMEABRANDCD2", "CECSPRODKEY2", "PRELOADSWINDC2", "MACHRATECATG2", "MAINTANNBILLELIGINDC2", "NOCHRGMAINTINDC2", "CECSPRODKEY2", "PRODSUPRTCD2", "RETANINDC2", "SYSIDUNIT2", "WWOCCODE2", "UNSPSCCD2", "UNSPSCCDUOM2", "PLANRELEVANT2", "PROJCDNAM2", "COFCAT2", "COFSUBCAT2", "COFGRP2", "COFSUBGRP2", "COMPATDVCCAT2", "COMPATDVCSUBCAT2", "INTERNALNAME2", "WTHDRWEFFCTVDATE2", "2018-09-11" }, { "MACHTYPEATR3", "MODELATR3", "COUNTRYLIST3", "EFFECTIVEDATE3", "MKTGNAME3", "MODMKTGDESC3", "INVNAME3", "PRFTCTR3", "BHPRODHIERCD3", "BHACCTASGNGRP3", "PRCINDC3", "SPECBID3", "MODELORDERCODE3", "INSTALL3", "LICNSINTERCD3", "MACHLVLCNTRL3", "PRODID3", "IBMCREDIT3", "ICRCATEGORY3", "VOLUMEDISCOUNTELIG3", "FUNCCLS3", "EDUCPURCHELIG3", "INTEGRATEDMODEL3", "PLNTOFMFR3", "GRADUATEDCHARGE3", "ANNUALMAINT3", "EMEABRANDCD3", "CECSPRODKEY3", "PRELOADSWINDC3", "MACHRATECATG3", "MAINTANNBILLELIGINDC3", "NOCHRGMAINTINDC3", "CECSPRODKEY3", "PRODSUPRTCD3", "RETANINDC3", "SYSIDUNIT3", "WWOCCODE3", "UNSPSCCD3", "UNSPSCCDUOM3", "PLANRELEVANT3", "PROJCDNAM3", "COFCAT3", "COFSUBCAT3", "COFGRP3", "COFSUBGRP3", "COMPATDVCCAT3", "COMPATDVCSUBCAT3", "INTERNALNAME3", "WTHDRWEFFCTVDATE3", "2018-09-11" }, { "MACHTYPEATR4", "MODELATR4", "COUNTRYLIST4", "EFFECTIVEDATE4", "MKTGNAME4", "MODMKTGDESC4", "INVNAME4", "PRFTCTR4", "BHPRODHIERCD4", "BHACCTASGNGRP4", "PRCINDC4", "SPECBID4", "MODELORDERCODE4", "INSTALL4", "LICNSINTERCD4", "MACHLVLCNTRL4", "PRODID4", "IBMCREDIT4", "ICRCATEGORY4", "VOLUMEDISCOUNTELIG4", "FUNCCLS4", "EDUCPURCHELIG4", "INTEGRATEDMODEL4", "PLNTOFMFR4", "GRADUATEDCHARGE4", "ANNUALMAINT4", "EMEABRANDCD4", "CECSPRODKEY4", "PRELOADSWINDC4", "MACHRATECATG4", "MAINTANNBILLELIGINDC4", "NOCHRGMAINTINDC4", "CECSPRODKEY4", "PRODSUPRTCD4", "RETANINDC4", "SYSIDUNIT4", "WWOCCODE4", "UNSPSCCD4", "UNSPSCCDUOM4", "PLANRELEVANT4", "PROJCDNAM4", "COFCAT4", "COFSUBCAT4", "COFGRP4", "COFSUBGRP4", "COMPATDVCCAT4", "COMPATDVCSUBCAT4", "INTERNALNAME4", "WTHDRWEFFCTVDATE4", "2018-09-11" }, { "9846", "AC3", "COUNTRYLIST4", "EFFECTIVEDATE4", "IBM FlashSystem 9110 Utility Model SFF NVMe Control Enclosure", "IBM FlashSystem 9100", "INVNAME4", "PRFTCTR4", "BHPRODHIERCD4", "BHACCTASGNGRP4", "PRCINDC4", "SPECBID4", "MODELORDERCODE4", "INSTALL4", "LICNSINTERCD4", "MACHLVLCNTRL4", "PRODID4", "IBMCREDIT4", "ICRCATEGORY4", "VOLUMEDISCOUNTELIG4", "FUNCCLS4", "EDUCPURCHELIG4", "INTEGRATEDMODEL4", "PLNTOFMFR4", "GRADUATEDCHARGE4", "ANNUALMAINT4", "EMEABRANDCD4", "CECSPRODKEY4", "PRELOADSWINDC4", "MACHRATECATG4", "MAINTANNBILLELIGINDC4", "NOCHRGMAINTINDC4", "CECSPRODKEY4", "PRODSUPRTCD4", "RETANINDC4", "SYSIDUNIT4", "WWOCCODE4", "UNSPSCCD4", "UNSPSCCDUOM4", "PLANRELEVANT4", "PROJCDNAM4", "COFCAT4", "COFSUBCAT4", "COFGRP4", "COFSUBGRP4", "COMPATDVCCAT4", "COMPATDVCSUBCAT4", "INTERNALNAME4", "1/18/2020", "2018-09-11" }, { "9846", "AE3", "COUNTRYLIST4", "EFFECTIVEDATE4", "IBM FlashSystem 9110 Utility Model SFF NVMe Control Enclosure", "IBM FlashSystem 9100", "INVNAME4", "PRFTCTR4", "BHPRODHIERCD4", "BHACCTASGNGRP4", "PRCINDC4", "SPECBID4", "MODELORDERCODE4", "INSTALL4", "LICNSINTERCD4", "MACHLVLCNTRL4", "PRODID4", "IBMCREDIT4", "ICRCATEGORY4", "VOLUMEDISCOUNTELIG4", "FUNCCLS4", "EDUCPURCHELIG4", "INTEGRATEDMODEL4", "PLNTOFMFR4", "GRADUATEDCHARGE4", "ANNUALMAINT4", "EMEABRANDCD4", "CECSPRODKEY4", "PRELOADSWINDC4", "MACHRATECATG4", "MAINTANNBILLELIGINDC4", "NOCHRGMAINTINDC4", "CECSPRODKEY4", "PRODSUPRTCD4", "RETANINDC4", "SYSIDUNIT4", "WWOCCODE4", "UNSPSCCD4", "UNSPSCCDUOM4", "PLANRELEVANT4", "PROJCDNAM4", "COFCAT4", "COFSUBCAT4", "COFGRP4", "COFSUBGRP4", "COMPATDVCCAT4", "COMPATDVCSUBCAT4", "INTERNALNAME4", "2018-09-11", "1/18/2020" }, { "9848", "AE3", "COUNTRYLIST4", "EFFECTIVEDATE4", "IBM FlashSystem Utility Model SFF NVMe Control Enclosure", "IBM FlashSystem", "INVNAME4", "PRFTCTR4", "BHPRODHIERCD4", "BHACCTASGNGRP4", "PRCINDC4", "SPECBID4", "MODELORDERCODE4", "INSTALL4", "LICNSINTERCD4", "MACHLVLCNTRL4", "PRODID4", "IBMCREDIT4", "ICRCATEGORY4", "VOLUMEDISCOUNTELIG4", "FUNCCLS4", "EDUCPURCHELIG4", "INTEGRATEDMODEL4", "PLNTOFMFR4", "GRADUATEDCHARGE4", "ANNUALMAINT4", "EMEABRANDCD4", "CECSPRODKEY4", "PRELOADSWINDC4", "MACHRATECATG4", "MAINTANNBILLELIGINDC4", "NOCHRGMAINTINDC4", "CECSPRODKEY4", "PRODSUPRTCD4", "RETANINDC4", "SYSIDUNIT4", "WWOCCODE4", "UNSPSCCD4", "UNSPSCCDUOM4", "PLANRELEVANT4", "PROJCDNAM4", "COFCAT4", "COFSUBCAT4", "COFGRP4", "COFSUBGRP4", "COMPATDVCCAT4", "COMPATDVCSUBCAT4", "INTERNALNAME4", "1/18/2020", "2018-09-11" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1061 */     this.dbDataMap.put("MODEL", arrayOfString);
/*      */     
/* 1063 */     arrayOfString = new String[][] { { "MTM", "FEATURECODE", "COUNTRYLIST", "EFFECTIVEDATE", "HWFCCAT", "HWFCSUBCAT", "INVNAME", "MKTGNAME", "MAINTPRICE", "PRICEDFEATURE", "FCMKTGDESC", "ORDERCODE", "INSTALL", "RETURNEDPARTS", "INITORDERMAX", "SYSTEMMAX", "SYSTEMMIN", "PREREQ", "COREQUISITE", "LMTATION", "COMPATIBILITY", "COMMENTS", "CBLORD", "CONFIGURATORFLAG", "BULKMESINDC", "WARRSVCCOVR", "EFFECTIVEDATE" }, { "MTM0", "FEATURECODE0", "COUNTRYLIST0", "EFFECTIVEDATE0", "HWFCCAT0", "HWFCSUBCAT0", "INVNAME0", "MKTGNAME0", "MAINTPRICE0", "PRICEDFEATURE0", "FCMKTGDESC0", "ORDERCODE0", "INSTALL0", "RETURNEDPARTS0", "INITORDERMAX0", "SYSTEMMAX0", "SYSTEMMIN0", "PREREQ0", "COREQUISITE0", "LMTATION0", "COMPATIBILITY0", "COMMENTS0", "CBLORD0", "CONFIGURATORFLAG0", "BULKMESINDC0", "WARRSVCCOVR0", "EFFECTIVEDATE0" }, { "MTM1", "FEATURECODE1", "COUNTRYLIST1", "EFFECTIVEDATE1", "HWFCCAT1", "HWFCSUBCAT1", "INVNAME1", "MKTGNAME1", "MAINTPRICE1", "PRICEDFEATURE1", "FCMKTGDESC1", "ORDERCODE1", "INSTALL1", "RETURNEDPARTS1", "INITORDERMAX1", "SYSTEMMAX1", "SYSTEMMIN1", "PREREQ1", "COREQUISITE1", "LMTATION1", "COMPATIBILITY1", "COMMENTS1", "CBLORD1", "CONFIGURATORFLAG1", "BULKMESINDC1", "WARRSVCCOVR1", "EFFECTIVEDATE1" }, { "MTM2", "FEATURECODE2", "COUNTRYLIST2", "EFFECTIVEDATE2", "HWFCCAT2", "HWFCSUBCAT2", "INVNAME2", "MKTGNAME2", "MAINTPRICE2", "PRICEDFEATURE2", "FCMKTGDESC2", "ORDERCODE2", "INSTALL2", "RETURNEDPARTS2", "INITORDERMAX2", "SYSTEMMAX2", "SYSTEMMIN2", "PREREQ2", "COREQUISITE2", "LMTATION2", "COMPATIBILITY2", "COMMENTS2", "CBLORD2", "CONFIGURATORFLAG2", "BULKMESINDC2", "WARRSVCCOVR2", "EFFECTIVEDATE2" }, { "MTM3", "FEATURECODE3", "COUNTRYLIST3", "EFFECTIVEDATE3", "HWFCCAT3", "HWFCSUBCAT3", "INVNAME3", "MKTGNAME3", "MAINTPRICE3", "PRICEDFEATURE3", "FCMKTGDESC3", "ORDERCODE3", "INSTALL3", "RETURNEDPARTS3", "INITORDERMAX3", "SYSTEMMAX3", "SYSTEMMIN3", "PREREQ3", "COREQUISITE3", "LMTATION3", "COMPATIBILITY3", "COMMENTS3", "CBLORD3", "CONFIGURATORFLAG3", "BULKMESINDC3", "WARRSVCCOVR3", "EFFECTIVEDATE3" }, { "MTM4", "FEATURECODE4", "COUNTRYLIST4", "EFFECTIVEDATE4", "HWFCCAT4", "HWFCSUBCAT4", "INVNAME4", "MKTGNAME4", "MAINTPRICE4", "PRICEDFEATURE4", "FCMKTGDESC4", "ORDERCODE4", "INSTALL4", "RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4", "COREQUISITE4", "LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "CONFIGURATORFLAG4", "BULKMESINDC4", "WARRSVCCOVR4", "EFFECTIVEDATE4" }, { "MTM4", "FEATURECODE4", "COUNTRYLIST4", "EFFECTIVEDATE4", "HWFCCAT4", "HWFCSUBCAT4", "INVNAME4", "MKTGNAME4", "MAINTPRICE4", "PRICEDFEATURE4", "FCMKTGDESC4", "ORDERCODE4", "INSTALL4", "RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4", "COREQUISITE4", "LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "CONFIGURATORFLAG4", "BULKMESINDC4", "WARRSVCCOVR4", "EFFECTIVEDATE4" }, { "9846-AC3", "0983", "COUNTRYLIST4", "EFFECTIVEDATE4", "HWFCCAT4", "HWFCSUBCAT4", "INVNAME4", "MKTGNAME4", "MAINTPRICE4", "PRICEDFEATURE4", "FCMKTGDESC4", "ORDERCODE4", "INSTALL4", "RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4", "COREQUISITE4", "LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "CONFIGURATORFLAG4", "BULKMESINDC4", "WARRSVCCOVR4", "EFFECTIVEDATE4" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1105 */     this.dbDataMap.put("FEATURE", arrayOfString);
/* 1106 */     arrayOfString = new String[][] { { "MTM", "FEATURECODE", "RETURNEDPARTS", "INITORDERMAX", "SYSTEMMAX", "SYSTEMMIN", "PREREQ", "COREQUISITE", "LMTATION", "COMPATIBILITY", "COMMENTS", "CBLORD", "BULKMESINDC" }, { "MTM0", "FEATURECODE0", "RETURNEDPARTS0", "INITORDERMAX0", "SYSTEMMAX0", "SYSTEMMIN0", "PREREQ0", "COREQUISITE0", "LMTATION0", "COMPATIBILITY0", "COMMENTS0", "CBLORD0", "BULKMESINDC0" }, { "MTM1", "FEATURECODE1", "RETURNEDPARTS1", "INITORDERMAX1", "SYSTEMMAX1", "SYSTEMMIN1", "PREREQ1", "COREQUISITE1", "LMTATION1", "COMPATIBILITY1", "COMMENTS1", "CBLORD1", "BULKMESINDC1" }, { "MTM2", "FEATURECODE2", "RETURNEDPARTS2", "INITORDERMAX2", "SYSTEMMAX2", "SYSTEMMIN2", "PREREQ2", "COREQUISITE2", "LMTATION2", "COMPATIBILITY2", "COMMENTS2", "CBLORD2", "BULKMESINDC2" }, { "MTM3", "FEATURECODE3", "RETURNEDPARTS3", "INITORDERMAX3", "SYSTEMMAX3", "SYSTEMMIN3", "PREREQ3", "COREQUISITE3", "LMTATION3", "COMPATIBILITY3", "COMMENTS3", "CBLORD3", "BULKMESINDC3" }, { "MTM4", "FEATURECODE4", "RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4", "COREQUISITE4", "LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "BULKMESINDC4" }, { "MTM4", "FEATURECODE4", "RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4", "COREQUISITE4", "LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "BULKMESINDC4" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1121 */     this.dbDataMap.put("UPDATEFC", arrayOfString);
/* 1122 */     arrayOfString = new String[][] { { "FROMMACHTYPE", "FROMMODEL", "FROMFEATURECODE", "TOMACHTYPE", "TOMODEL", "TOFEATURECODE", "FTCAT", "RETURNEDPARTS", "ZEROPRICE", "WTHDRWEFFCTVDATE" }, { "FROMMACHTYPE0", "FROMMODEL0", "FROMFEATURECODE0", "TOMACHTYPE0", "TOMODEL0", "TOFEATURECODE0", "FTCAT0", "RETURNEDPARTS0", "ZEROPRICE0", "WTHDRWEFFCTVDATE0" }, { "FROMMACHTYPE1", "FROMMODEL1", "FROMFEATURECODE1", "TOMACHTYPE1", "TOMODEL1", "TOFEATURECODE1", "FTCAT1", "RETURNEDPARTS1", "ZEROPRICE1", "WTHDRWEFFCTVDATE1" }, { "FROMMACHTYPE2", "FROMMODEL2", "FROMFEATURECODE2", "TOMACHTYPE2", "TOMODEL2", "TOFEATURECODE2", "FTCAT2", "RETURNEDPARTS2", "ZEROPRICE2", "WTHDRWEFFCTVDATE2" }, { "FROMMACHTYPE3", "FROMMODEL3", "FROMFEATURECODE3", "TOMACHTYPE3", "TOMODEL3", "TOFEATURECODE3", "FTCAT3", "RETURNEDPARTS3", "ZEROPRICE3", "WTHDRWEFFCTVDATE3" }, { "FROMMACHTYPE4", "FROMMODEL4", "FROMFEATURECODE4", "TOMACHTYPE4", "TOMODEL4", "TOFEATURECODE4", "FTCAT4", "RETURNEDPARTS4", "ZEROPRICE4", "WTHDRWEFFCTVDATE4" }, { "FROMMACHTYPE4", "FROMMODEL4", "FROMFEATURECODE4", "TOMACHTYPE4", "TOMODEL4", "TOFEATURECODE4", "FTCAT4", "RETURNEDPARTS4", "ZEROPRICE4", "WTHDRWEFFCTVDATE4" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1137 */     this.dbDataMap.put("FCONV", arrayOfString);
/*      */     
/* 1139 */     arrayOfString = new String[][] { { "FROMMACHTYPE", "FROMMODEL", "TOMACHTYPE", "TOMODEL", "RETURNEDPARTS", "WTHDRWEFFCTVDATE" }, { "FROMMACHTYPE0", "FROMMODEL0", "TOMACHTYPE0", "TOMODEL0", "RETURNEDPARTS0", "WTHDRWEFFCTVDATE0" }, { "FROMMACHTYPE1", "FROMMODEL1", "TOMACHTYPE1", "TOMODEL1", "RETURNEDPARTS1", "WTHDRWEFFCTVDATE1" }, { "FROMMACHTYPE2", "FROMMODEL2", "TOMACHTYPE2", "TOMODEL2", "RETURNEDPARTS2", "WTHDRWEFFCTVDATE2" }, { "FROMMACHTYPE3", "FROMMODEL3", "TOMACHTYPE3", "TOMODEL3", "RETURNEDPARTS3", "WTHDRWEFFCTVDATE3" }, { "FROMMACHTYPE4", "FROMMODEL4", "TOMACHTYPE4", "TOMODEL4", "RETURNEDPARTS4", "WTHDRWEFFCTVDATE4" }, { "FROMMACHTYPE4", "FROMMODEL4", "TOMACHTYPE4", "TOMODEL4", "RETURNEDPARTS4", "WTHDRWEFFCTVDATE4" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1148 */     this.dbDataMap.put("MCONV", arrayOfString);
/*      */   }
/*      */   
/*      */   public void loadModel() {
/*      */     try {
/* 1153 */       String str = "select distinct av.entityid as AVAILID,m.MACHTYPEATR,m.MODELATR,av.EFFECTIVEDATE,m.MKTGNAME,m.INVNAME,m.PRFTCTR,m.BHPRODHIERCD,m.BHACCTASGNGRP,m.PRCINDC,m.SPECBID,m.MODELORDERCODE,m.INSTALL,m.LICNSINTERCD,m.MACHLVLCNTRL,m.PRODID,m.IBMCREDIT,m.ICRCATEGORY,m.VOLUMEDISCOUNTELIG,m.FUNCCLS,m.WITHDRAWDATE,m.GENAVAILDATE,m.ANNDATE,m.MODMKTGDESC,m.WTHDRWEFFCTVDATE,m.WWOCCODE,m.UNSPSCCDUOM,gm.EDUCPURCHELIG,gm.INTEGRATEDMODEL,gm.PLNTOFMFR,gm.GRADUATEDCHARGE,gm.ANNUALMAINT,gm.EMEABRANDCD,m.CECSPRODKEY,m.PRELOADSWINDC,m.MACHRATECATG,m.MAINTANNBILLELIGINDC,m.NOCHRGMAINTINDC,m.CECSPRODKEY,m.PRODSUPRTCD,m.RETANINDC,m.SYSIDUNIT,m.UNSPSCCD,m.PLANRELEVANT,m.PROJCDNAM,m.COFCAT,m.COFSUBCAT,m.COFGRP,m.COFSUBGRP,m.COMPATDVCCAT,m.COMPATDVCSUBCAT,m.INTERNALNAME from opicm.announcement ann join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=av.entityid join opicm.model m on m.entityid=r.entity1id and m.nlsid=1 left join opicm.relator r3 on r3.entitytype='MODELGEOMOD' and r3.entity1id=m.entityid left join opicm.geomod gm on gm.entityid=r3.entity2id and gm.nlsid=1 where ann.annnumber= ?  and ann.nlsid=1 with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1168 */       Connection connection = getDatabase().getODS2Connection();
/* 1169 */       PreparedStatement preparedStatement = connection.prepareStatement(str);
/* 1170 */       preparedStatement.setString(1, this.annNumber);
/* 1171 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 1172 */       String[][] arrayOfString = processResult(resultSet);
/* 1173 */       this.dbDataMap.put("MODEL", arrayOfString);
/* 1174 */       addDebug("loadModel sql:" + str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1195 */     catch (MiddlewareException middlewareException) {
/*      */       
/* 1197 */       middlewareException.printStackTrace();
/* 1198 */     } catch (SQLException sQLException) {
/*      */       
/* 1200 */       sQLException.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void loadFeature() {
/*      */     try {
/* 1206 */       String str = "select concat(concat(m.MACHTYPEATR,'-'),m.MODELATR) as MTM,f.FEATURECODE,av.EFFECTIVEDATE,f.HWFCCAT,f.HWFCSUBCAT,f.INVNAME,f.MKTGNAME,f.MAINTPRICE,f.PRICEDFEATURE,lf.attributevalue as FCMKTGDESC,ps.ORDERCODE,ps.INSTALL,ps.RETURNEDPARTS,ps.INITORDERMAX,ps.SYSTEMMAX,ps.SYSTEMMIN,ps.GENAVAILDATE,ps.ANNDATE,ps.WTHDRWEFFCTVDATE,ps.WITHDRAWDATE,ps.WARRSVCCOVR,p1.attributevalue as PREREQ,p2.attributevalue as COREQUISITE,p3.attributevalue as LMTATION,p4.attributevalue as COMPATIBILITY,p5.attributevalue as COMMENTS,p6.attributevalue as CBLORD,f.CONFIGURATORFLAG,ps.BULKMESINDC from opicm.announcement ann join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=av.entityid join opicm.model m on m.entityid=r.entity1id and m.nlsid=1 join opicm.relator r2 on r2.entitytype='PRODSTRUCT' and r2.entity2id=m.entityid join opicm.feature f on f.entityid=r2.entity1id and f.nlsid=1 join opicm.prodstruct ps on ps.entityid=r2.entityid and ps.nlsid=1 left join opicm.longtext lf on lf.entityid=f.entityid and lf.attributecode='FCMKTGDESC' and lf.entitytype='FEATURE' left join opicm.longtext p1 on p1.entityid=ps.entityid and p1.attributecode='PREREQ' and p1.entitytype='PRODSTRUCT' left join opicm.longtext p2 on p2.entityid=ps.entityid and p2.attributecode='COREQUISITE' and p2.entitytype='PRODSTRUCT' left join opicm.longtext p3 on p3.entityid=ps.entityid and p3.attributecode='LMTATION' and p3.entitytype='PRODSTRUCT' left join opicm.longtext p4 on p4.entityid=ps.entityid and p4.attributecode='COMPATIBILITY' and p4.entitytype='PRODSTRUCT' left join opicm.longtext p5 on p5.entityid=ps.entityid and p5.attributecode='COMMENTS' and p5.entitytype='PRODSTRUCT' left join opicm.longtext p6 on p6.entityid=ps.entityid and p6.attributecode='CBLORD' and p6.entitytype='PRODSTRUCT' where ann.annnumber=? with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1225 */       Connection connection = getDatabase().getODS2Connection();
/* 1226 */       PreparedStatement preparedStatement = connection.prepareStatement(str);
/* 1227 */       preparedStatement.setString(1, this.annNumber);
/* 1228 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 1229 */       String[][] arrayOfString = processResult(resultSet);
/* 1230 */       this.dbDataMap.put("FEATURE", arrayOfString);
/* 1231 */       addDebug("loadFeature sql:" + str);
/* 1232 */     } catch (MiddlewareException middlewareException) {
/*      */       
/* 1234 */       middlewareException.printStackTrace();
/* 1235 */     } catch (SQLException sQLException) {
/*      */       
/* 1237 */       sQLException.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void loadTMF111() {
/*      */     try {
/* 1243 */       String str = "select concat(m.MACHTYPEATR,m.MODELATR),f.FEATURECODE,ps.RETURNEDPARTS,ps.INITORDERMAX,ps.SYSTEMMAX,ps.SYSTEMMIN,ps.BULKMESINDC from opicm.announcement ann join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=av.entityid join opicm.model m on m.entityid=r.entity1id and m.nlsid=1 join opicm.relator r2 on r2.entitytype='PRODSTRUCT' and r2.entity2id=m.entityid join opicm.feature f on f.entityid=r2.entity1id  and f.nlsid=1 join opicm.prodstruct ps on ps.entityid=r2.entityid and ps.nlsid=1 where ann.annnumber=? with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1254 */       Connection connection = getDatabase().getODS2Connection();
/* 1255 */       PreparedStatement preparedStatement = connection.prepareStatement(str);
/* 1256 */       preparedStatement.setString(1, this.annNumber);
/* 1257 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 1258 */       String[][] arrayOfString = processResult(resultSet);
/* 1259 */       this.dbDataMap.put("", arrayOfString);
/*      */     }
/* 1261 */     catch (MiddlewareException middlewareException) {
/*      */       
/* 1263 */       middlewareException.printStackTrace();
/* 1264 */     } catch (SQLException sQLException) {
/*      */       
/* 1266 */       sQLException.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void loadUpdate() {
/*      */     try {
/* 1272 */       String str = "select concat(concat(m.MACHTYPEATR,'-'),m.MODELATR) as MTM,f.FEATURECODE,ps.RETURNEDPARTS,ps.INITORDERMAX,ps.SYSTEMMAX,ps.SYSTEMMIN,p1.attributevalue as PREREQ,p2.attributevalue as COREQUISITE,p3.attributevalue as LMTATION,p4.attributevalue as COMPATIBILITY,p5.attributevalue as COMMENTS,p6.attributevalue as CBLORD,ps.BULKMESINDC from opicm.announcement ann join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=av.entityid join opicm.model m on m.entityid=r.entity1id and m.nlsid=1 join opicm.relator r2 on r2.entitytype='PRODSTRUCT' and r2.entity2id=m.entityid join opicm.feature f on f.entityid=r2.entity1id  and f.nlsid=1 join opicm.prodstruct ps on ps.entityid=r2.entityid and ps.nlsid=1 left join opicm.longtext p1 on p1.entityid=ps.entityid and p1.attributecode='PREREQ' and p1.entitytype='PRODSTRUCT' left join opicm.longtext p2 on p2.entityid=ps.entityid and p2.attributecode='COREQUISITE' and p2.entitytype='PRODSTRUCT' left join opicm.longtext p3 on p3.entityid=ps.entityid and p3.attributecode='LMTATION' and p3.entitytype='PRODSTRUCT' left join opicm.longtext p4 on p4.entityid=ps.entityid and p4.attributecode='COMPATIBILITY' and p4.entitytype='PRODSTRUCT' left join opicm.longtext p5 on p5.entityid=ps.entityid and p5.attributecode='COMMENTS' and p5.entitytype='PRODSTRUCT' left join opicm.longtext p6 on p6.entityid=ps.entityid and p6.attributecode='CBLORD' and p6.entitytype='PRODSTRUCT' where ann.annnumber=? with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1289 */       Connection connection = getDatabase().getODS2Connection();
/* 1290 */       PreparedStatement preparedStatement = connection.prepareStatement(str);
/* 1291 */       preparedStatement.setString(1, this.annNumber);
/* 1292 */       ResultSet resultSet = preparedStatement.executeQuery();
/*      */ 
/*      */       
/* 1295 */       String[][] arrayOfString = processResult(resultSet);
/* 1296 */       this.dbDataMap.put("UPDATEFC", arrayOfString);
/* 1297 */       addDebug("loadUpdate sql:" + str);
/* 1298 */     } catch (MiddlewareException middlewareException) {
/*      */       
/* 1300 */       middlewareException.printStackTrace();
/* 1301 */     } catch (SQLException sQLException) {
/*      */       
/* 1303 */       sQLException.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String[][] processResult(ResultSet paramResultSet) throws SQLException {
/* 1309 */     int i = paramResultSet.getMetaData().getColumnCount();
/*      */     
/* 1311 */     String[][] arrayOfString = (String[][])null;
/* 1312 */     String[] arrayOfString1 = new String[i];
/* 1313 */     ArrayList<String[]> arrayList = new ArrayList(); int j;
/* 1314 */     for (j = 1; j <= i; j++) {
/* 1315 */       arrayOfString1[j - 1] = paramResultSet.getMetaData().getColumnName(j);
/*      */     }
/* 1317 */     j = 0;
/* 1318 */     arrayList.add(arrayOfString1);
/* 1319 */     while (paramResultSet.next()) {
/* 1320 */       arrayOfString1 = new String[i];
/* 1321 */       for (byte b1 = 1; b1 <= i; b1++) {
/* 1322 */         arrayOfString1[b1 - 1] = paramResultSet.getString(b1);
/*      */       }
/* 1324 */       arrayList.add(arrayOfString1);
/*      */     } 
/* 1326 */     j = arrayList.size();
/* 1327 */     arrayOfString = new String[j + 1][i];
/* 1328 */     for (byte b = 0; b < arrayList.size(); b++) {
/* 1329 */       arrayOfString[b] = arrayList.get(b);
/*      */     }
/*      */     
/* 1332 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public void loadModelCovert() {
/*      */     try {
/* 1337 */       String str = "select mc.FROMMACHTYPE,mc.FROMMODEL,mc.TOMACHTYPE,mc.TOMODEL,mc.RETURNEDPARTS,mc.WTHDRWEFFCTVDATE,mc.WITHDRAWDATE,mc.ANNDATE,mc.GENAVAILDATE from opicm.announcement ann join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 join opicm.relator r on r.entity1type='MODELCONVERT' and r.entity2type='AVAIL' and r.entity2id=av.entityid join opicm.modelconvert mc on mc.entityid=r.entity1id and mc.nlsid=1 where ann.annnumber=? with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1343 */       Connection connection = getDatabase().getODS2Connection();
/* 1344 */       PreparedStatement preparedStatement = connection.prepareStatement(str);
/* 1345 */       preparedStatement.setString(1, this.annNumber);
/* 1346 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 1347 */       String[][] arrayOfString = processResult(resultSet);
/* 1348 */       this.dbDataMap.put("MCONV", arrayOfString);
/* 1349 */       addDebug("loadModelCovert sql:" + str);
/* 1350 */     } catch (MiddlewareException middlewareException) {
/*      */       
/* 1352 */       middlewareException.printStackTrace();
/* 1353 */     } catch (SQLException sQLException) {
/*      */       
/* 1355 */       sQLException.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void loadFeatureTrans() {
/*      */     try {
/* 1361 */       String str = "select ft.FROMMACHTYPE,ft.FROMMODEL,ft.FROMFEATURECODE,ft.TOMACHTYPE,ft.TOMODEL,ft.TOFEATURECODE,ft.FTCAT,ft.RETURNEDPARTS,ft.ZEROPRICE,ft.WITHDRAWDATE,ft.ANNDATE,ft.GENAVAILDATE,ft.WTHDRWEFFCTVDATE from opicm.announcement ann join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 join opicm.relator r on r.entity1type='FCTRANSACTION' and r.entity2type='AVAIL' and r.entity2id=av.entityid join opicm.fctransaction ft on ft.entityid=r.entity1id and ft.nlsid=1 where ann.annnumber=? with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1367 */       Connection connection = getDatabase().getODS2Connection();
/* 1368 */       PreparedStatement preparedStatement = connection.prepareStatement(str);
/* 1369 */       preparedStatement.setString(1, this.annNumber);
/* 1370 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 1371 */       String[][] arrayOfString = processResult(resultSet);
/* 1372 */       this.dbDataMap.put("FCONV", arrayOfString);
/* 1373 */       addDebug("loadFeatureTrans sql:" + str);
/* 1374 */     } catch (MiddlewareException middlewareException) {
/*      */       
/* 1376 */       middlewareException.printStackTrace();
/* 1377 */     } catch (SQLException sQLException) {
/*      */       
/* 1379 */       sQLException.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String getType(String paramString) {
/* 1384 */     return (sheetMap.get(paramString) == null) ? null : sheetMap.get(paramString).toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMail(String paramString) throws Exception {
/* 1389 */     FileInputStream fileInputStream = null;
/*      */     try {
/* 1391 */       fileInputStream = new FileInputStream(paramString);
/* 1392 */       int i = fileInputStream.available();
/* 1393 */       byte[] arrayOfByte = new byte[i];
/* 1394 */       fileInputStream.read(arrayOfByte);
/* 1395 */       setAttachByteForDG(arrayOfByte);
/* 1396 */       getABRItem().setFileExtension("csv");
/* 1397 */       addDebug("Sending mail for file " + paramString);
/* 1398 */     } catch (IOException iOException) {
/*      */       
/* 1400 */       addDebug("sendMail IO Exception");
/*      */     }
/*      */     finally {
/*      */       
/* 1404 */       if (fileInputStream != null) {
/* 1405 */         fileInputStream.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1415 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] paramArrayOfString) throws IOException, MiddlewareException, SQLException {
/* 1428 */     (new COMPARIREPORTSTATUS()).test();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1437 */     return "1.0";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\COMPARIREPORTSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */