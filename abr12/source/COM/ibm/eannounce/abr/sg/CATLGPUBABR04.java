/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.CATLGPUBLSEOBDLPDG;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Properties;
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
/*     */ public class CATLGPUBABR04
/*     */   extends PokBaseABR
/*     */ {
/*     */   private static final String ABR = "CATLGPUBABR04";
/*  91 */   private PDGUtility m_utility = new PDGUtility();
/*     */   private static final String STARTDATE = "1980-01-01-00.00.00.000000";
/*  93 */   private int m_iDays = 0;
/*  94 */   private int m_iChunk = 100;
/*     */ 
/*     */   
/*     */   private boolean m_bSearch = false;
/*     */ 
/*     */   
/*     */   private static final int I64 = 64;
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 105 */     String str = null;
/* 106 */     EntityGroup entityGroup = null;
/* 107 */     EntityItem entityItem = null;
/* 108 */     CATLGPUBLSEOBDLPDG cATLGPUBLSEOBDLPDG = null;
/*     */     try {
/* 110 */       readPropertyFile();
/* 111 */       start_ABRBuild(false);
/* 112 */       println(EACustom.getDocTypeHtml());
/*     */       
/* 114 */       println("<head>" + EACustom.NEWLINE + 
/* 115 */           EACustom.getMetaTags(getDescription()) + EACustom.NEWLINE + 
/* 116 */           EACustom.getCSS() + EACustom.NEWLINE + 
/* 117 */           EACustom.getTitle(getDescription()) + EACustom.NEWLINE + "</head>" + EACustom.NEWLINE + "<body id=\"ibm-com\">");
/*     */ 
/*     */ 
/*     */       
/* 121 */       println(EACustom.getMastheadDiv());
/*     */       
/* 123 */       setReturnCode(0);
/*     */       
/* 125 */       str = this.m_db.getDates().getNow();
/* 126 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/* 127 */       entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 129 */       println("<p class=\"ibm-intro ibm-alternate-three\"><em>Catalog Country: " + entityItem.getKey() + "</em></p>");
/*     */       
/* 131 */       printNavigateAttributes(entityItem, entityGroup);
/*     */ 
/*     */       
/* 134 */       String str1 = this.m_utility.getAttrValue(entityItem, "CATLGPUBBNDLLASTRUN");
/* 135 */       if (str1 == null || str1.length() <= 0) {
/* 136 */         OPICMList oPICMList = new OPICMList();
/* 137 */         oPICMList.put("CATLGPUBBNDLLASTRUN", "CATLGPUBBNDLLASTRUN=1980-01-01-00.00.00.000000");
/* 138 */         str1 = "1980-01-01-00.00.00.000000";
/* 139 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*     */       }
/* 141 */       else if (!this.m_utility.isDateFormat(str1)) {
/* 142 */         println("<p>CATLGPUBBNDLLASTRUN is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000</p>");
/* 143 */         setReturnCode(-1);
/*     */       }
/* 145 */       else if (str1.length() == 10) {
/* 146 */         OPICMList oPICMList = new OPICMList();
/* 147 */         oPICMList.put("CATLGPUBBNDLLASTRUN", "CATLGPUBBNDLLASTRUN=" + str1 + "-00.00.00.000000");
/* 148 */         str1 = str1 + "-00.00.00.000000";
/* 149 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 154 */       String str2 = this.m_utility.getAttrValue(entityItem, "OFFCOUNTRY");
/* 155 */       if (str2 == null || str2.length() <= 0) {
/* 156 */         println("<p>OFFCOUNTRY is blank.</p>");
/* 157 */         setReturnCode(-1);
/*     */       } 
/* 159 */       if (getReturnCode() == 0) {
/* 160 */         String str3 = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 161 */         log("CATLGPUBABR04 generating data using domains: " + str3 + " chunk: " + this.m_iChunk + " days: " + this.m_iDays + " useSrch: " + this.m_bSearch);
/*     */         
/* 163 */         cATLGPUBLSEOBDLPDG = new CATLGPUBLSEOBDLPDG(null, this.m_db, this.m_prof, str3);
/*     */         
/* 165 */         cATLGPUBLSEOBDLPDG.setUpdatedBy("CATLGPUBABR04" + getRevision() + str);
/* 166 */         cATLGPUBLSEOBDLPDG.m_strEntityType = "LSEOBUNDLE";
/* 167 */         cATLGPUBLSEOBDLPDG.setEntityItem(entityItem);
/* 168 */         cATLGPUBLSEOBDLPDG.setChunkSize(this.m_iChunk);
/* 169 */         cATLGPUBLSEOBDLPDG.setDoSearch(this.m_bSearch);
/* 170 */         if (this.m_iDays <= 0) {
/* 171 */           cATLGPUBLSEOBDLPDG.executeAction(this.m_db, this.m_prof);
/* 172 */           log("CATLGPUBABR04 finish generating data");
/* 173 */           println("<!-- " + cATLGPUBLSEOBDLPDG.getDebugInfo() + "-->");
/*     */           
/* 175 */           OPICMList oPICMList = new OPICMList();
/* 176 */           oPICMList.put("CATLGPUBBNDLLASTRUN", "CATLGPUBBNDLLASTRUN=" + str);
/*     */           
/* 178 */           this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*     */         } else {
/* 180 */           println("<!-- CATLGPUBABR04 in using date range mode -->");
/* 181 */           log("CATLGPUBABR04 in using date range mode");
/* 182 */           String str4 = "CATLGPUBBNDLLASTRUN";
/* 183 */           String str5 = str1;
/* 184 */           if (str5.substring(0, 4).equals("1980")) {
/* 185 */             str5 = this.m_utility.getEarliestTime(this.m_db, this.m_prof, "LSEOBUNDLE");
/*     */           }
/* 187 */           cATLGPUBLSEOBDLPDG.setUseDateRange(true);
/* 188 */           cATLGPUBLSEOBDLPDG.setScanAll(false);
/*     */           
/* 190 */           String str6 = this.m_utility.getDate(str5.substring(0, 10), this.m_iDays) + "-23.59.59.000000";
/* 191 */           int i = this.m_utility.longDateCompare(str6, str);
/* 192 */           boolean bool = false;
/* 193 */           if (i == 1 && !bool) {
/* 194 */             println("<!-- CATLGPUBABR04 setting end time to now -->");
/* 195 */             log("CATLGPUBABR04 setting end time to now");
/* 196 */             str6 = str;
/* 197 */             bool = true;
/* 198 */             i = 0;
/* 199 */             cATLGPUBLSEOBDLPDG.setScanAll(true);
/*     */           } 
/*     */           
/* 202 */           while (i != 1) {
/* 203 */             cATLGPUBLSEOBDLPDG.setStartTime(str5);
/* 204 */             cATLGPUBLSEOBDLPDG.setEndTime(str6);
/*     */             
/* 206 */             log("CATLGPUBABR04 last run time: " + str5 + ", end time: " + str6);
/* 207 */             println("<!--CATLGPUBABR04 last run time: " + str5 + ", end time: " + str6 + "-->");
/*     */             
/* 209 */             cATLGPUBLSEOBDLPDG.executeAction(this.m_db, this.m_prof);
/* 210 */             println("<!-- " + cATLGPUBLSEOBDLPDG.getDebugInfo() + "-->");
/*     */             
/* 212 */             OPICMList oPICMList = new OPICMList();
/* 213 */             oPICMList.put(str4, str4 + "=" + str6);
/* 214 */             this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*     */             
/* 216 */             str5 = str6;
/* 217 */             str6 = this.m_utility.getDate(str5.substring(0, 10), this.m_iDays) + "-23.59.59.000000";
/*     */             
/* 219 */             i = this.m_utility.longDateCompare(str6, str);
/* 220 */             if (i == 1 && !bool) {
/* 221 */               str6 = str;
/* 222 */               bool = true;
/* 223 */               i = 0;
/* 224 */               cATLGPUBLSEOBDLPDG.setScanAll(true);
/*     */             } 
/* 226 */             this.m_utility.memory(true);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 231 */     } catch (SBRException sBRException) {
/* 232 */       String str2 = sBRException.toString();
/* 233 */       int i = str2.indexOf("(ok)");
/* 234 */       if (i < 0) {
/* 235 */         setReturnCode(-2);
/* 236 */         println("<h3><span style=\"color:#c00; font-weight:bold;\">Generate Data error: <pre>" + str2 + "</pre></span></h3>");
/*     */         
/* 238 */         logError(sBRException.toString());
/*     */       } else {
/* 240 */         str2 = str2.substring(0, i);
/* 241 */         println("<pre>" + str2 + "</pre>");
/*     */       } 
/* 243 */       if (cATLGPUBLSEOBDLPDG != null) {
/* 244 */         println("<!-- " + cATLGPUBLSEOBDLPDG.getDebugInfo() + "-->");
/*     */       }
/* 246 */     } catch (Throwable throwable) {
/* 247 */       String[] arrayOfString = new String[1];
/* 248 */       StringWriter stringWriter = new StringWriter();
/* 249 */       String str3 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 250 */       String str4 = "<pre>{0}</pre>";
/* 251 */       MessageFormat messageFormat = new MessageFormat(str3);
/* 252 */       setReturnCode(-1);
/* 253 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 255 */       arrayOfString[0] = throwable.getMessage();
/* 256 */       println(messageFormat.format(arrayOfString));
/* 257 */       messageFormat = new MessageFormat(str4);
/* 258 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 259 */       println(messageFormat.format(arrayOfString));
/* 260 */       logError("Exception: " + throwable.getMessage());
/* 261 */       logError(stringWriter.getBuffer().toString());
/* 262 */       if (cATLGPUBLSEOBDLPDG != null) {
/* 263 */         println("<!-- " + cATLGPUBLSEOBDLPDG.getDebugInfo() + "-->");
/*     */       }
/*     */     } finally {
/*     */       
/* 267 */       String str1 = null;
/* 268 */       String str2 = buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */             
/* 270 */             getABRDescription(), (getReturnCode() == 0) ? "Passed" : "Failed" });
/* 271 */       println("<br /><b>" + str2 + "</b>");
/* 272 */       log(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 277 */       str1 = getABRDescription() + ":" + getEntityType() + ":" + getEntityID();
/* 278 */       if (str1.length() > 64) {
/* 279 */         str1 = str1.substring(0, 64);
/*     */       }
/* 281 */       setDGTitle(str1);
/* 282 */       setDGRptName("CATLGPUBABR04");
/*     */ 
/*     */       
/* 285 */       setDGString(getABRReturnCode());
/*     */       
/* 287 */       println(EACustom.getTOUDiv());
/*     */ 
/*     */       
/* 290 */       printDGSubmitString();
/*     */       
/* 292 */       buildReportFooter();
/*     */ 
/*     */       
/* 295 */       if (!isReadOnly()) {
/* 296 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printNavigateAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 306 */     byte b1 = 0;
/* 307 */     println("<table width=\"100%\">");
/* 308 */     println("<tr style=\"background-color:#cef;\"><th width=\"50%\">Navigation Attribute</th><th width=\"50%\">Attribute Value</th></tr>");
/*     */     
/* 310 */     for (byte b2 = 0; b2 < paramEntityGroup.getMetaAttributeCount(); b2++) {
/* 311 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b2);
/* 312 */       if (eANMetaAttribute.isNavigate()) {
/* 313 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 314 */         println("<tr class=\"" + ((b1++ % 2 != 0) ? "even" : "odd") + "\"><td width=\"50%\" >" + eANMetaAttribute
/*     */ 
/*     */             
/* 317 */             .getLongDescription() + "</td><td width=\"50%\">" + ((eANAttribute == null || eANAttribute
/*     */             
/* 319 */             .toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     println("</table>");
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
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 355 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 364 */     return "Catalog Offering Publication For LSEOBUNDLE ABR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 374 */     return "1.17";
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 394 */     return "CATLGPUBABR04.java " + getRevision();
/*     */   }
/*     */   
/*     */   private void readPropertyFile() {
/* 398 */     D.ebug("Reading catlgpubabr.properties");
/* 399 */     Properties properties = new Properties();
/* 400 */     boolean bool = true;
/*     */     try {
/* 402 */       File file = new File("./catlgpubabr.properties");
/* 403 */       if (!file.exists() || !file.canRead()) {
/* 404 */         System.out.println("Can't read " + file.getAbsolutePath());
/*     */         
/* 406 */         bool = false;
/*     */       } else {
/* 408 */         properties.load(new FileInputStream(file));
/*     */       } 
/* 410 */     } catch (IOException iOException) {
/* 411 */       System.out.println("Error reading catlgpubabr.properties");
/* 412 */       bool = false;
/*     */     } 
/*     */     
/* 415 */     if (bool) {
/* 416 */       this.m_iDays = Integer.parseInt(properties.getProperty("CATLGPUBABR04.DAYS", "0"));
/* 417 */       this.m_iChunk = Integer.parseInt(properties.getProperty("CATLGPUBABR04.CHUNK", "100"));
/* 418 */       this.m_bSearch = properties.getProperty("CATLGPUBABR04.SEARCH", "false").equalsIgnoreCase("true");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CATLGPUBABR04.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */