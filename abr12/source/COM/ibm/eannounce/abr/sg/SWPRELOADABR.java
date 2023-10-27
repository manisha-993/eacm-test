/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.StringUtils;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
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
/*     */ public class SWPRELOADABR extends PokBaseABR {
/*  21 */   private StringBuffer rptSb = new StringBuffer();
/*  22 */   private String ffFileName = null;
/*  23 */   private String ffPathName = null;
/*  24 */   private String dir = null;
/*  25 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  26 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*     */   private static final String PRTPATH = "prtpath";
/*     */   private static final String MACHTYPEATR = "MACHTYPEATR";
/*     */   private static final String MODELATR = "MODELATR";
/*     */   private static final String MKTGNAME = "MKTGNAME";
/*     */   private static final String COFCAT = "COFCAT";
/*     */   private static final String COFSUBCAT = "COFSUBCAT";
/*     */   private static final String COFGRP = "COFGRP";
/*     */   private static final String COFSUBGRP = "COFSUBGRP";
/*     */   private static final String PRELOADSWINDC = "PRELOADSWINDC";
/*     */   private static final String ANNDATE = "ANNDATE";
/*     */   private static final String WITHDRAWDATE = "WITHDRAWDATE";
/*     */   boolean hasData = false;
/*  40 */   private static final String[] COLUMN_ARR = new String[] { "MACHTYPEATR", "MODELATR", "MKTGNAME", "COFCAT", "COFSUBCAT", "COFGRP", "COFSUBGRP", "PRELOADSWINDC", "ANNDATE", "WITHDRAWDATE" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static final Hashtable COLUMN_LENGTH = new Hashtable<>(); static {
/*  46 */     COLUMN_LENGTH.put("MACHTYPEATR", "4");
/*  47 */     COLUMN_LENGTH.put("MODELATR", "3");
/*  48 */     COLUMN_LENGTH.put("MKTGNAME", "260");
/*  49 */     COLUMN_LENGTH.put("COFCAT", "10");
/*  50 */     COLUMN_LENGTH.put("COFSUBCAT", "50");
/*  51 */     COLUMN_LENGTH.put("COFGRP", "10");
/*  52 */     COLUMN_LENGTH.put("COFSUBGRP", "35");
/*  53 */     COLUMN_LENGTH.put("PRELOADSWINDC", "5");
/*  54 */     COLUMN_LENGTH.put("ANNDATE", "10");
/*  55 */     COLUMN_LENGTH.put("WITHDRAWDATE", "10");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String QUERY = "select * from price.VPRELOADEDSW";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFileName() {
/*  69 */     String str1 = ABRServerProperties.getFilePrefix(this.m_abri
/*  70 */         .getABRCode());
/*     */     
/*  72 */     StringBuffer stringBuffer = new StringBuffer(str1.trim());
/*  73 */     String str2 = getNow();
/*     */     
/*  75 */     str2 = str2.replace(' ', '_');
/*  76 */     stringBuffer.append(str2 + ".csv");
/*  77 */     this
/*  78 */       .dir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "prtpath", "/Dgq");
/*  79 */     if (!this.dir.endsWith("/")) {
/*  80 */       this.dir += "/";
/*     */     }
/*  82 */     this.ffFileName = stringBuffer.toString();
/*  83 */     this.ffPathName = this.dir + this.ffFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  89 */     addDebug("---------------ABR started run ");
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
/* 100 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */ 
/*     */ 
/*     */     
/* 104 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     String str3 = "";
/* 113 */     boolean bool = true;
/*     */ 
/*     */     
/* 116 */     String str4 = "";
/*     */     
/* 118 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/* 121 */       MessageFormat messageFormat = new MessageFormat(str1);
/* 122 */       arrayOfString[0] = getShortClassName(getClass());
/* 123 */       arrayOfString[1] = "ABR";
/* 124 */       str3 = messageFormat.format(arrayOfString);
/* 125 */       start_ABRBuild(false);
/* 126 */       setDGTitle("SWPRELOADABR report");
/* 127 */       setDGString(getABRReturnCode());
/* 128 */       setDGRptName("SWPRELOADABR");
/* 129 */       setDGRptClass("SWPRELOADABR");
/*     */       
/* 131 */       setReturnCode(0);
/* 132 */       generateFlatFile();
/* 133 */       sendMail(this.ffPathName);
/*     */     }
/* 135 */     catch (Exception exception) {
/* 136 */       exception.printStackTrace();
/*     */       
/* 138 */       setReturnCode(-1);
/* 139 */       StringWriter stringWriter = new StringWriter();
/* 140 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 141 */       String str6 = "<pre>{0}</pre>";
/* 142 */       MessageFormat messageFormat = new MessageFormat(str5);
/* 143 */       setReturnCode(-3);
/* 144 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 146 */       arrayOfString[0] = exception.getMessage();
/* 147 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 148 */       messageFormat = new MessageFormat(str6);
/* 149 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 150 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 151 */       logError("Exception: " + exception.getMessage());
/* 152 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 154 */       setCreateDGEntity(true);
/* 155 */       bool = false;
/*     */     } finally {
/*     */       
/* 158 */       StringBuffer stringBuffer = new StringBuffer();
/* 159 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 160 */       arrayOfString[0] = this.m_prof.getOPName();
/* 161 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 162 */       arrayOfString[2] = this.m_prof.getWGName();
/* 163 */       arrayOfString[3] = getNow();
/* 164 */       stringBuffer.append(bool ? "generated the SWPRELOADABR report file successful " : "generated the SWPRELOADABR report file faild");
/*     */       
/* 166 */       stringBuffer.append(",");
/* 167 */       if (!this.hasData) {
/* 168 */         stringBuffer.append("No Such Data!");
/*     */       } else {
/* 170 */         stringBuffer.append("Generated the SWPRELOADABR report file successful ");
/* 171 */       }  arrayOfString[4] = stringBuffer.toString();
/* 172 */       arrayOfString[5] = str4 + " " + getABRVersion();
/*     */       
/* 174 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 176 */       println(EACustom.getDocTypeHtml());
/* 177 */       println(this.rptSb.toString());
/* 178 */       printDGSubmitString();
/*     */       
/* 180 */       println(EACustom.getTOUDiv());
/* 181 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateFlatFile() throws IOException, MiddlewareException, SQLException {
/* 188 */     setFileName();
/* 189 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*     */ 
/*     */ 
/*     */     
/* 193 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*     */ 
/*     */     
/* 196 */     String str1 = getNow();
/* 197 */     String str2 = str1.substring(0, 10);
/* 198 */     String str3 = str1.substring(11, 19);
/* 199 */     String str4 = str1.substring(19);
/* 200 */     str3 = str3.replace('.', ':');
/* 201 */     String str5 = str2 + " " + str3 + str4;
/*     */     
/* 203 */     Connection connection = this.m_db.getPDHConnection();
/* 204 */     PreparedStatement preparedStatement = connection.prepareStatement("select * from price.VPRELOADEDSW", 1004, 1007);
/*     */     
/* 206 */     ResultSet resultSet = preparedStatement.executeQuery();
/*     */     
/* 208 */     if (resultSet.last()) {
/* 209 */       int i = resultSet.getRow();
/* 210 */       outputStreamWriter.write("EACM" + str5 + " " + i);
/* 211 */       outputStreamWriter.write("\n");
/* 212 */       resultSet.first();
/*     */     } 
/*     */     
/*     */     byte b;
/* 216 */     for (b = 0; b < COLUMN_ARR.length; b++) {
/* 217 */       outputStreamWriter.write(COLUMN_ARR[b]);
/* 218 */       outputStreamWriter.write(",");
/*     */     } 
/* 220 */     outputStreamWriter.write("\n");
/*     */     
/* 222 */     if (resultSet.getRow() > 0) {
/* 223 */       this.hasData = true;
/*     */       do {
/* 225 */         for (b = 0; b < COLUMN_ARR.length; b++) {
/* 226 */           if (b < 2) {
/* 227 */             outputStreamWriter.write("\t");
/*     */           }
/* 229 */           outputStreamWriter.write(getValue(resultSet.getString(COLUMN_ARR[b]), COLUMN_ARR[b]));
/*     */           
/* 231 */           outputStreamWriter.write(",");
/*     */         } 
/* 233 */         outputStreamWriter.write("\n");
/* 234 */         outputStreamWriter.flush();
/* 235 */       } while (resultSet.next());
/*     */     } 
/* 237 */     outputStreamWriter.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getValue(String paramString1, String paramString2) {
/* 242 */     if (paramString1 == null) {
/* 243 */       paramString1 = "";
/*     */     }
/*     */     
/* 246 */     if (paramString1.indexOf(",") > -1) {
/* 247 */       if (paramString1.indexOf("\"") > -1) {
/* 248 */         paramString1 = StringUtils.replace(paramString1, "\"", "\"\"");
/*     */       }
/* 250 */       paramString1 = "\"" + paramString1 + "\"";
/*     */     } 
/*     */     
/* 253 */     int i = (paramString1 == null) ? 0 : paramString1.length();
/* 254 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString2)
/* 255 */         .toString());
/* 256 */     if (i == j)
/* 257 */       return paramString1; 
/* 258 */     if (i > j) {
/* 259 */       return paramString1.substring(0, j);
/*     */     }
/* 261 */     return paramString1 + getBlank(j - i);
/*     */   }
/*     */   
/*     */   protected String getBlank(int paramInt) {
/* 265 */     StringBuffer stringBuffer = new StringBuffer();
/* 266 */     while (paramInt > 0) {
/* 267 */       stringBuffer.append(" ");
/* 268 */       paramInt--;
/*     */     } 
/* 270 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 274 */     return "SWPRELOADABR";
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/* 278 */     return "1.1";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMail(String paramString) throws Exception {
/* 284 */     FileInputStream fileInputStream = null;
/*     */     try {
/* 286 */       fileInputStream = new FileInputStream(paramString);
/* 287 */       int i = fileInputStream.available();
/* 288 */       byte[] arrayOfByte = new byte[i];
/* 289 */       fileInputStream.read(arrayOfByte);
/* 290 */       setAttachByteForDG(arrayOfByte);
/* 291 */       getABRItem().setFileExtension("csv");
/* 292 */       addDebug("Sending mail for file " + paramString);
/* 293 */     } catch (IOException iOException) {
/* 294 */       addDebug("sendMail IO Exception");
/*     */     } finally {
/*     */       
/* 297 */       if (fileInputStream != null) {
/* 298 */         fileInputStream.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 305 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SWPRELOADABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */