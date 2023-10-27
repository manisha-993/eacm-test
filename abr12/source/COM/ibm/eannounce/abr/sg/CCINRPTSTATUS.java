/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*     */ import org.apache.poi.hssf.usermodel.HSSFCellStyle;
/*     */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*     */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*     */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*     */ import org.apache.poi.ss.usermodel.VerticalAlignment;
/*     */ 
/*     */ public class CCINRPTSTATUS extends PokBaseABR {
/*  28 */   private StringBuffer rptSb = new StringBuffer();
/*  29 */   private String ffFileName = null;
/*  30 */   private String ffPathName = null;
/*  31 */   private String dir = null;
/*  32 */   private String lineStr = null;
/*  33 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  34 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  35 */   private Object[] args = (Object[])new String[10];
/*     */   private static final String SUCCESS = "SUCCESS";
/*     */   private static final String FAILD = "FAILD";
/*     */   private static final String CCINPRTPATH = "ccinrptpath";
/*     */   private static final String CCININIPATH = "ccininipath";
/*     */   private static final String SFTPSCRPATH = "sftpscrpath";
/*     */   private static final String CCINATR = "CCINATR";
/*     */   private static final String CCIN_CODENAME = "CCIN_CODENAME";
/*     */   private static final String CCIN_INTERNALNAME = "CCIN_INTERNALNAME";
/*     */   private static final String FEATURECODE = "FEATURECODE";
/*     */   private static final String FC_COMNAME = "FC_COMNAME";
/*     */   private static final String FC_HWFCSUBCAT = "FC_HWFCSUBCAT";
/*  47 */   private static final String[] COLUMN_ARR = new String[] { "CCINATR", "CCIN_CODENAME", "CCIN_INTERNALNAME", "FEATURECODE", "FC_COMNAME", "FC_HWFCSUBCAT" };
/*     */ 
/*     */   
/*     */   private static final String QUERY = "select * from OPICM.CCIN_FEATURE_V with ur";
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFileName() throws IOException {
/*  55 */     String str1 = ABRServerProperties.getFilePrefix(this.m_abri
/*  56 */         .getABRCode());
/*     */ 
/*     */     
/*  59 */     StringBuffer stringBuffer = new StringBuffer(str1.trim());
/*  60 */     String str2 = getNow();
/*     */     
/*  62 */     str2 = str2.replace(' ', '_');
/*  63 */     stringBuffer.append(str2 + ".xls");
/*  64 */     this.dir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "ccinrptpath", "/Dgq");
/*     */     
/*  66 */     if (!this.dir.endsWith("/")) {
/*  67 */       this.dir += "/";
/*     */     }
/*  69 */     this.ffFileName = stringBuffer.toString();
/*  70 */     this.ffPathName = this.dir + this.ffFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  86 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */ 
/*     */ 
/*     */     
/*  90 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Result: </th><td>{4}</td></tr>" + NEWLINE + "</table><!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     String str3 = "";
/*  99 */     String[] arrayOfString = new String[10];
/* 100 */     String str4 = "";
/* 101 */     boolean bool1 = true;
/* 102 */     boolean bool2 = true;
/*     */     
/*     */     try {
/* 105 */       MessageFormat messageFormat = new MessageFormat(str1);
/* 106 */       arrayOfString[0] = getShortClassName(getClass());
/* 107 */       arrayOfString[1] = "ABR";
/* 108 */       str4 = messageFormat.format(arrayOfString);
/* 109 */       start_ABRBuild(false);
/* 110 */       setDGTitle("CCINRPTSTATUS report");
/* 111 */       setDGString(getABRReturnCode());
/* 112 */       setDGRptName("CCINRPTSTATUS");
/* 113 */       setDGRptClass("CCINRPTSTATUS");
/*     */       
/* 115 */       setFileName();
/* 116 */       bool1 = createExcel(getRecords(), this.ffPathName);
/* 117 */       bool2 = exeFtpShell(this.ffPathName);
/*     */       
/* 119 */       setReturnCode(0);
/*     */     }
/* 121 */     catch (Exception exception) {
/*     */       
/* 123 */       exception.printStackTrace();
/*     */       
/* 125 */       setReturnCode(-1);
/* 126 */       StringWriter stringWriter = new StringWriter();
/* 127 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 128 */       String str6 = "<pre>{0}</pre>";
/* 129 */       MessageFormat messageFormat = new MessageFormat(str5);
/* 130 */       setReturnCode(-3);
/* 131 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 133 */       arrayOfString[0] = exception.getMessage();
/* 134 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 135 */       messageFormat = new MessageFormat(str6);
/* 136 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 137 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 138 */       logError("Exception: " + exception.getMessage());
/* 139 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 141 */       setCreateDGEntity(true);
/* 142 */       bool1 = false;
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 148 */       StringBuffer stringBuffer = new StringBuffer();
/* 149 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 150 */       arrayOfString[0] = this.m_prof.getOPName();
/* 151 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 152 */       arrayOfString[2] = this.m_prof.getWGName();
/* 153 */       arrayOfString[3] = getNow();
/* 154 */       stringBuffer.append(bool1 ? "generated the CCIN report file successful " : "generated the CCIN report file faild");
/*     */       
/* 156 */       stringBuffer.append(",");
/* 157 */       stringBuffer.append(bool2 ? "send the CCIN report file successful " : "sent the CCIN report file faild");
/*     */       
/* 159 */       if (!bool2 && this.lineStr != null)
/* 160 */         stringBuffer.append(this.lineStr.replaceFirst("FAILD", "")); 
/* 161 */       arrayOfString[4] = stringBuffer.toString();
/* 162 */       arrayOfString[5] = str3 + " " + getABRVersion();
/*     */       
/* 164 */       this.rptSb.insert(0, str4 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 166 */       println(EACustom.getDocTypeHtml());
/* 167 */       println(this.rptSb.toString());
/* 168 */       printDGSubmitString();
/*     */       
/* 170 */       println(EACustom.getTOUDiv());
/* 171 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getRecords() throws MiddlewareException, Exception {
/* 178 */     ArrayList<String[]> arrayList = new ArrayList();
/* 179 */     Connection connection = this.m_db.getODS2Connection();
/*     */     
/* 181 */     PreparedStatement preparedStatement = connection.prepareStatement("select * from OPICM.CCIN_FEATURE_V with ur", 1004, 1007);
/*     */     
/* 183 */     ResultSet resultSet = preparedStatement.executeQuery();
/*     */     
/* 185 */     while (resultSet.next()) {
/* 186 */       String[] arrayOfString = new String[6];
/* 187 */       arrayOfString[0] = resultSet.getString(1);
/* 188 */       arrayOfString[1] = resultSet.getString(2);
/* 189 */       arrayOfString[2] = resultSet.getString(3);
/* 190 */       arrayOfString[3] = resultSet.getString(4);
/* 191 */       arrayOfString[4] = resultSet.getString(5);
/* 192 */       arrayOfString[5] = resultSet.getString(6);
/* 193 */       arrayList.add(arrayOfString);
/*     */     } 
/* 195 */     return arrayList;
/*     */   }
/*     */   
/*     */   public static boolean createExcel(List<String[]> paramList, String paramString) {
/* 199 */     HSSFWorkbook hSSFWorkbook = new HSSFWorkbook();
/* 200 */     HSSFSheet hSSFSheet = hSSFWorkbook.createSheet("sheet1");
/* 201 */     HSSFRow hSSFRow = hSSFSheet.createRow(0);
/* 202 */     HSSFCellStyle hSSFCellStyle = hSSFWorkbook.createCellStyle();
/*     */     
/* 204 */     hSSFCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
/* 205 */     HSSFCell hSSFCell = null; byte b;
/* 206 */     for (b = 0; b < COLUMN_ARR.length; b++) {
/* 207 */       hSSFCell = hSSFRow.createCell((short)b);
/* 208 */       hSSFCell.setCellValue(COLUMN_ARR[b]);
/* 209 */       hSSFCell.setCellStyle(hSSFCellStyle);
/*     */     } 
/*     */     try {
/* 212 */       for (b = 0; b < paramList.size(); b++) {
/* 213 */         hSSFRow = hSSFSheet.createRow(b + 1);
/* 214 */         String[] arrayOfString = paramList.get(b);
/* 215 */         for (byte b1 = 0; b1 < COLUMN_ARR.length; b1++) {
/* 216 */           hSSFRow.createCell((short)b1).setCellValue(arrayOfString[b1]);
/*     */         }
/* 218 */         FileOutputStream fileOutputStream = new FileOutputStream(paramString);
/* 219 */         hSSFWorkbook.write(fileOutputStream);
/* 220 */         fileOutputStream.close();
/*     */       } 
/* 222 */       return true;
/* 223 */     } catch (Exception exception) {
/* 224 */       exception.printStackTrace();
/* 225 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getBlank(int paramInt) {
/* 230 */     StringBuffer stringBuffer = new StringBuffer();
/* 231 */     while (paramInt > 0) {
/* 232 */       stringBuffer.append(" ");
/* 233 */       paramInt--;
/*     */     } 
/* 235 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exeFtpShell(String paramString) {
/* 242 */     String str1 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "sftpscrpath", null) + " -f " + paramString;
/*     */     
/* 244 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "ccininipath", null);
/*     */     
/* 246 */     if (str2 != null)
/* 247 */       str1 = str1 + " -i " + str2; 
/* 248 */     Runtime runtime = Runtime.getRuntime();
/* 249 */     String str3 = "";
/* 250 */     BufferedReader bufferedReader = null;
/* 251 */     BufferedInputStream bufferedInputStream = null;
/*     */     try {
/* 253 */       Process process = runtime.exec(str1);
/* 254 */       if (process.waitFor() != 0) {
/* 255 */         return false;
/*     */       }
/* 257 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 258 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 259 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 260 */         str3 = str3 + this.lineStr;
/* 261 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 262 */           return false;
/*     */         }
/*     */       } 
/* 265 */     } catch (Exception exception) {
/* 266 */       exception.printStackTrace();
/* 267 */       return false;
/*     */     } finally {
/* 269 */       if (bufferedReader != null) {
/*     */         try {
/* 271 */           bufferedReader.close();
/* 272 */           bufferedInputStream.close();
/* 273 */         } catch (IOException iOException) {
/* 274 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 278 */     return !(str3 == null);
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 282 */     return "CCINRPTSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 291 */     return "1.0";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CCINRPTSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */