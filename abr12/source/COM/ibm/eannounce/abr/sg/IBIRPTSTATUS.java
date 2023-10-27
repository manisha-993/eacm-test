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
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Hashtable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IBIRPTSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  41 */   private StringBuffer rptSb = new StringBuffer();
/*  42 */   private String ffFileName = null;
/*  43 */   private String ffPathName = null;
/*  44 */   private String dir = null;
/*  45 */   private String lineStr = null;
/*  46 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  47 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  48 */   private Object[] args = (Object[])new String[10];
/*     */   private static final String SUCCESS = "SUCCESS";
/*     */   private static final String FAILD = "FAILD";
/*     */   private static final String IBIPRTPATH = "ibiprtpath";
/*     */   private static final String IBIINIPATH = "ibiinipath";
/*     */   private static final String SFTPSCRPATH = "sftpscrpath";
/*     */   private static final String MACHTYPEATR = "MACHTYPEATR";
/*     */   private static final String MODELATR = "MODELATR";
/*     */   private static final String MKTGNAME = "MKTGNAME";
/*     */   private static final String INSTALL = "INSTALL";
/*     */   private static final String COFCAT = "COFCAT";
/*     */   private static final String COFSUBCAT = "COFSUBCAT";
/*     */   private static final String COFSUBGRP = "COFSUBGRP";
/*     */   private static final String ANNDATE = "ANNDATE";
/*     */   private static final String WITHDRAWDATE = "WITHDRAWDATE";
/*     */   private static final String WTHDRWEFFCTVDATE = "WTHDRWEFFCTVDATE";
/*     */   private static final String DIV = "DIV";
/*     */   private static final String STATUS = "STATUS";
/*  66 */   private static final String[] COLUMN_ARR = new String[] { "MACHTYPEATR", "MODELATR", "MKTGNAME", "INSTALL", "COFCAT", "COFSUBCAT", "COFSUBGRP", "ANNDATE", "WITHDRAWDATE", "WTHDRWEFFCTVDATE", "DIV", "STATUS" };
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final Hashtable COLUMN_LENGTH = new Hashtable<>(); static {
/*  71 */     COLUMN_LENGTH.put("MACHTYPEATR", "4");
/*  72 */     COLUMN_LENGTH.put("MODELATR", "3");
/*  73 */     COLUMN_LENGTH.put("MKTGNAME", "128");
/*  74 */     COLUMN_LENGTH.put("INSTALL", "128");
/*  75 */     COLUMN_LENGTH.put("COFCAT", "30");
/*  76 */     COLUMN_LENGTH.put("COFSUBCAT", "128");
/*  77 */     COLUMN_LENGTH.put("COFSUBGRP", "128");
/*  78 */     COLUMN_LENGTH.put("ANNDATE", "10");
/*  79 */     COLUMN_LENGTH.put("WITHDRAWDATE", "10");
/*  80 */     COLUMN_LENGTH.put("WTHDRWEFFCTVDATE", "10");
/*  81 */     COLUMN_LENGTH.put("DIV", "128");
/*  82 */     COLUMN_LENGTH.put("STATUS", "20");
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String QUERY = "select m.machtypeatr,m.modelatr,m.mktgname,m.install,m.cofcat,m.cofsubcat,m.cofsubgrp,m.anndate,m.withdrawdate,m.WTHDRWEFFCTVDATE,S.div,m.status from price.model m join price.SGMNTACRNYM S on m.PRFTCTR=S.PRFTCTR where m.nlsid=1  and m.cofcat='Hardware' and m.status in ('Final','Ready for Review') and s.nlsid=1 and  S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9')  and m.isactive=1 and m.INSTALL='CE' with ur";
/*     */ 
/*     */   
/*     */   private void setFileName() throws IOException {
/*  90 */     String str1 = ABRServerProperties.getFilePrefix(this.m_abri.getABRCode());
/*     */ 
/*     */     
/*  93 */     StringBuffer stringBuffer = new StringBuffer(str1.trim());
/*  94 */     String str2 = getNow();
/*     */     
/*  96 */     str2 = str2.replace(' ', '_');
/*  97 */     stringBuffer.append(str2 + ".txt");
/*  98 */     this.dir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "ibiprtpath", "/Dgq");
/*  99 */     if (!this.dir.endsWith("/")) {
/* 100 */       this.dir += "/";
/*     */     }
/* 102 */     this.ffFileName = stringBuffer.toString();
/* 103 */     this.ffPathName = this.dir + this.ffFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 111 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/* 113 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Result: </th><td>{4}</td></tr>" + NEWLINE + "</table><!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     String str3 = "";
/* 119 */     String[] arrayOfString = new String[10];
/* 120 */     String str4 = "";
/* 121 */     boolean bool = true;
/* 122 */     boolean bool1 = true;
/*     */     
/*     */     try {
/* 125 */       MessageFormat messageFormat = new MessageFormat(str1);
/* 126 */       arrayOfString[0] = getShortClassName(getClass());
/* 127 */       arrayOfString[1] = "ABR";
/* 128 */       str4 = messageFormat.format(arrayOfString);
/* 129 */       start_ABRBuild(false);
/* 130 */       setDGTitle("IBIRPTSTATUS report");
/* 131 */       setDGString(getABRReturnCode());
/* 132 */       setDGRptName("IBIRPTSTATUS");
/* 133 */       setDGRptClass("IBIRPTSTATUS");
/* 134 */       generateFlatFile();
/* 135 */       bool1 = exeFtpShell(this.ffPathName);
/* 136 */       setReturnCode(0);
/*     */     }
/* 138 */     catch (Exception exception) {
/*     */       
/* 140 */       exception.printStackTrace();
/*     */       
/* 142 */       setReturnCode(-1);
/* 143 */       StringWriter stringWriter = new StringWriter();
/* 144 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 145 */       String str6 = "<pre>{0}</pre>";
/* 146 */       MessageFormat messageFormat = new MessageFormat(str5);
/* 147 */       setReturnCode(-3);
/* 148 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 150 */       arrayOfString[0] = exception.getMessage();
/* 151 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 152 */       messageFormat = new MessageFormat(str6);
/* 153 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 154 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 155 */       logError("Exception: " + exception.getMessage());
/* 156 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 158 */       setCreateDGEntity(true);
/* 159 */       bool = false;
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 165 */       StringBuffer stringBuffer = new StringBuffer();
/* 166 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 167 */       arrayOfString[0] = this.m_prof.getOPName();
/* 168 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 169 */       arrayOfString[2] = this.m_prof.getWGName();
/* 170 */       arrayOfString[3] = getNow();
/* 171 */       stringBuffer.append(bool ? "generated the IBI report file successful " : "generated the IBI report file faild");
/* 172 */       stringBuffer.append(",");
/* 173 */       stringBuffer.append(bool1 ? "send the IBI report file successful " : "sent the IBI report file faild");
/* 174 */       if (!bool1)
/* 175 */         stringBuffer.append(this.lineStr.replaceFirst("FAILD", "")); 
/* 176 */       arrayOfString[4] = stringBuffer.toString();
/* 177 */       arrayOfString[5] = str3 + " " + getABRVersion();
/*     */       
/* 179 */       this.rptSb.insert(0, str4 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 181 */       println(EACustom.getDocTypeHtml());
/* 182 */       println(this.rptSb.toString());
/* 183 */       printDGSubmitString();
/*     */       
/* 185 */       println(EACustom.getTOUDiv());
/* 186 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateFlatFile() throws IOException, MiddlewareException, SQLException
/*     */   {
/* 193 */     setFileName();
/* 194 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*     */ 
/*     */ 
/*     */     
/* 198 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*     */ 
/*     */     
/* 201 */     String str1 = getNow();
/* 202 */     String str2 = str1.substring(0, 10);
/* 203 */     String str3 = str1.substring(11, 19);
/* 204 */     String str4 = str1.substring(19);
/* 205 */     str3 = str3.replace('.', ':');
/* 206 */     String str5 = str2 + " " + str3 + str4;
/*     */     
/* 208 */     Connection connection = this.m_db.getPDHConnection();
/* 209 */     PreparedStatement preparedStatement = connection.prepareStatement("select m.machtypeatr,m.modelatr,m.mktgname,m.install,m.cofcat,m.cofsubcat,m.cofsubgrp,m.anndate,m.withdrawdate,m.WTHDRWEFFCTVDATE,S.div,m.status from price.model m join price.SGMNTACRNYM S on m.PRFTCTR=S.PRFTCTR where m.nlsid=1  and m.cofcat='Hardware' and m.status in ('Final','Ready for Review') and s.nlsid=1 and  S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9')  and m.isactive=1 and m.INSTALL='CE' with ur", 1004, 1007);
/*     */     
/* 211 */     ResultSet resultSet = preparedStatement.executeQuery();
/*     */     
/* 213 */     if (resultSet.last()) {
/* 214 */       int i = resultSet.getRow();
/* 215 */       outputStreamWriter.write("EACM" + str5 + i);
/* 216 */       outputStreamWriter.write("\n");
/*     */       
/* 218 */       resultSet.first();
/*     */     } 
/*     */     
/*     */     while (true) {
/* 222 */       for (byte b = 0; b < COLUMN_ARR.length; b++)
/*     */       {
/* 224 */         outputStreamWriter.write(getValue(resultSet.getString(COLUMN_ARR[b]), COLUMN_ARR[b]));
/*     */       }
/* 226 */       outputStreamWriter.write("\n");
/* 227 */       outputStreamWriter.flush();
/* 228 */       if (!resultSet.next()) {
/* 229 */         outputStreamWriter.flush();
/*     */         return;
/*     */       } 
/*     */     }  } protected String getValue(String paramString1, String paramString2) {
/* 233 */     if (paramString1 == null)
/* 234 */       paramString1 = ""; 
/* 235 */     int i = (paramString1 == null) ? 0 : paramString1.length();
/* 236 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString2).toString());
/* 237 */     if (i == j)
/* 238 */       return paramString1; 
/* 239 */     if (i > j) {
/* 240 */       return paramString1.substring(0, j);
/*     */     }
/* 242 */     return paramString1 + getBlank(j - i);
/*     */   }
/*     */   
/*     */   protected String getBlank(int paramInt) {
/* 246 */     StringBuffer stringBuffer = new StringBuffer();
/* 247 */     while (paramInt > 0) {
/* 248 */       stringBuffer.append(" ");
/* 249 */       paramInt--;
/*     */     } 
/* 251 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exeFtpShell(String paramString) {
/* 259 */     String str1 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "sftpscrpath", null) + " -f " + paramString;
/* 260 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "ibiinipath", null);
/* 261 */     if (str2 != null)
/* 262 */       str1 = str1 + " -i " + str2; 
/* 263 */     Runtime runtime = Runtime.getRuntime();
/* 264 */     String str3 = "";
/* 265 */     BufferedReader bufferedReader = null;
/* 266 */     BufferedInputStream bufferedInputStream = null;
/*     */     try {
/* 268 */       Process process = runtime.exec(str1);
/* 269 */       if (process.waitFor() != 0) {
/* 270 */         return false;
/*     */       }
/* 272 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 273 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 274 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 275 */         str3 = str3 + this.lineStr;
/* 276 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 277 */           return false;
/*     */         }
/*     */       } 
/* 280 */     } catch (Exception exception) {
/* 281 */       exception.printStackTrace();
/* 282 */       return false;
/*     */     } finally {
/* 284 */       if (bufferedReader != null) {
/*     */         try {
/* 286 */           bufferedReader.close();
/* 287 */           bufferedInputStream.close();
/* 288 */         } catch (IOException iOException) {
/* 289 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 293 */     return !(str3 == null);
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 297 */     return "IBIRPTSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 306 */     return "1.14";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\IBIRPTSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */