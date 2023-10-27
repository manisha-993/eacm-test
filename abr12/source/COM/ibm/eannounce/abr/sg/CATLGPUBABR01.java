/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.CATLGPUBMDLPDG;
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
/*     */ 
/*     */ 
/*     */ public class CATLGPUBABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   private static final String ABR = "CATLGPUBABR01";
/*  93 */   private PDGUtility m_utility = new PDGUtility();
/*     */   private static final String STARTDATE = "1980-01-01-00.00.00.000000";
/*  95 */   private int m_iDays = 0;
/*  96 */   private int m_iChunk = 100;
/*     */ 
/*     */   
/*     */   private static final int I64 = 64;
/*     */ 
/*     */   
/*     */   private boolean m_bSearch = false;
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 106 */     String str1 = null;
/* 107 */     EntityGroup entityGroup = null;
/* 108 */     EntityItem entityItem = null;
/* 109 */     String str2 = null;
/* 110 */     String str3 = null;
/* 111 */     CATLGPUBMDLPDG cATLGPUBMDLPDG = null;
/*     */     try {
/* 113 */       readPropertyFile();
/* 114 */       start_ABRBuild(false);
/* 115 */       println(EACustom.getDocTypeHtml());
/*     */       
/* 117 */       println("<head>" + EACustom.NEWLINE + 
/* 118 */           EACustom.getMetaTags(getDescription()) + EACustom.NEWLINE + 
/* 119 */           EACustom.getCSS() + EACustom.NEWLINE + 
/* 120 */           EACustom.getTitle(getDescription()) + EACustom.NEWLINE + "</head>" + EACustom.NEWLINE + "<body id=\"ibm-com\">");
/*     */ 
/*     */ 
/*     */       
/* 124 */       println(EACustom.getMastheadDiv());
/*     */       
/* 126 */       setReturnCode(0);
/*     */       
/* 128 */       str1 = this.m_db.getDates().getNow();
/* 129 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/* 130 */       entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */ 
/*     */       
/* 133 */       println("<p class=\"ibm-intro ibm-alternate-three\"><em>Catalog Country: " + entityItem.getKey() + "</em></p>");
/* 134 */       printNavigateAttributes(entityItem, entityGroup);
/*     */ 
/*     */ 
/*     */       
/* 138 */       str3 = this.m_utility.getAttrValue(entityItem, "CATLGPUBMDLLASTRUN");
/* 139 */       if (str3 == null || str3.length() <= 0) {
/* 140 */         OPICMList oPICMList = new OPICMList();
/* 141 */         oPICMList.put("CATLGPUBMDLLASTRUN", "CATLGPUBMDLLASTRUN=1980-01-01-00.00.00.000000");
/* 142 */         str3 = "1980-01-01-00.00.00.000000";
/* 143 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*     */       }
/* 145 */       else if (!this.m_utility.isDateFormat(str3)) {
/* 146 */         println("<p>CATLGPUBMDLLASTRUN is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000</p>");
/* 147 */         setReturnCode(-1);
/*     */       }
/* 149 */       else if (str3.length() == 10) {
/* 150 */         OPICMList oPICMList = new OPICMList();
/* 151 */         oPICMList.put("CATLGPUBMDLLASTRUN", "CATLGPUBMDLLASTRUN=" + str3 + "-00.00.00.000000");
/* 152 */         str3 = str3 + "-00.00.00.000000";
/* 153 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 158 */       str2 = this.m_utility.getAttrValue(entityItem, "OFFCOUNTRY");
/* 159 */       if (str2 == null || str2.length() <= 0) {
/* 160 */         println("<p>OFFCOUNTRY is blank.</p>");
/* 161 */         setReturnCode(-1);
/*     */       } 
/* 163 */       if (getReturnCode() == 0) {
/* 164 */         String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 165 */         log("CATLGPUBABR01 generating data using domains: " + str + " chunk: " + this.m_iChunk + " days: " + this.m_iDays + " useSrch: " + this.m_bSearch);
/*     */         
/* 167 */         cATLGPUBMDLPDG = new CATLGPUBMDLPDG(null, this.m_db, this.m_prof, str);
/* 168 */         cATLGPUBMDLPDG.setUpdatedBy("CATLGPUBABR01" + getRevision() + str1);
/* 169 */         cATLGPUBMDLPDG.m_strEntityType = "MODEL";
/* 170 */         cATLGPUBMDLPDG.setEntityItem(entityItem);
/* 171 */         cATLGPUBMDLPDG.setChunkSize(this.m_iChunk);
/* 172 */         cATLGPUBMDLPDG.setDoSearch(this.m_bSearch);
/* 173 */         if (this.m_iDays <= 0) {
/* 174 */           cATLGPUBMDLPDG.executeAction(this.m_db, this.m_prof);
/* 175 */           log("CATLGPUBABR01 finish generating data");
/* 176 */           println("<!-- " + cATLGPUBMDLPDG.getDebugInfo() + "-->");
/*     */           
/* 178 */           OPICMList oPICMList = new OPICMList();
/*     */ 
/*     */           
/* 181 */           oPICMList.put("CATLGPUBMDLLASTRUN", "CATLGPUBMDLLASTRUN=" + str1);
/*     */           
/* 183 */           this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*     */         } else {
/* 185 */           log("CATLGPUBABR01 in using date range mode");
/* 186 */           println("<!-- CATLGPUBABR01 in using date range mode -->");
/*     */           
/* 188 */           String str6 = "CATLGPUBMDLLASTRUN";
/* 189 */           String str7 = str3;
/* 190 */           if (str7.substring(0, 4).equals("1980")) {
/* 191 */             str7 = this.m_utility.getEarliestTime(this.m_db, this.m_prof, "MODEL");
/*     */           }
/* 193 */           cATLGPUBMDLPDG.setUseDateRange(true);
/* 194 */           cATLGPUBMDLPDG.setScanAll(false);
/*     */           
/* 196 */           String str8 = this.m_utility.getDate(str7.substring(0, 10), this.m_iDays) + "-23.59.59.000000";
/* 197 */           int i = this.m_utility.longDateCompare(str8, str1);
/* 198 */           boolean bool = false;
/* 199 */           if (i == 1 && !bool) {
/* 200 */             log("CATLGPUBABR01 setting end time to now");
/* 201 */             println("<!-- CATLGPUBABR01 setting end time to now -->");
/* 202 */             str8 = str1;
/* 203 */             bool = true;
/* 204 */             i = 0;
/* 205 */             cATLGPUBMDLPDG.setScanAll(true);
/*     */           } 
/*     */           
/* 208 */           while (i != 1) {
/* 209 */             cATLGPUBMDLPDG.setStartTime(str7);
/* 210 */             cATLGPUBMDLPDG.setEndTime(str8);
/*     */             
/* 212 */             log("CATLGPUBABR01 last run time: " + str7 + ", end time: " + str8);
/* 213 */             println("<!-- CATLGPUBABR01 last run time: " + str7 + ", end time: " + str8 + "-->");
/*     */             
/* 215 */             cATLGPUBMDLPDG.executeAction(this.m_db, this.m_prof);
/* 216 */             println("<!-- " + cATLGPUBMDLPDG.getDebugInfo() + "-->");
/*     */ 
/*     */ 
/*     */             
/* 220 */             OPICMList oPICMList = new OPICMList();
/* 221 */             oPICMList.put(str6, str6 + "=" + str8);
/* 222 */             this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*     */             
/* 224 */             str7 = str8;
/* 225 */             str8 = this.m_utility.getDate(str7.substring(0, 10), this.m_iDays) + "-23.59.59.000000";
/*     */             
/* 227 */             i = this.m_utility.longDateCompare(str8, str1);
/* 228 */             if (i == 1 && !bool) {
/* 229 */               str8 = str1;
/* 230 */               bool = true;
/* 231 */               i = 0;
/* 232 */               cATLGPUBMDLPDG.setScanAll(true);
/*     */             } 
/* 234 */             this.m_utility.memory(true);
/*     */           } 
/*     */         } 
/*     */       } 
/* 238 */     } catch (SBRException sBRException) {
/* 239 */       String str5 = sBRException.toString();
/* 240 */       int i = str5.indexOf("(ok)");
/* 241 */       if (i < 0) {
/* 242 */         setReturnCode(-2);
/* 243 */         println("<h3><span style=\"color:#c00; font-weight:bold;\">Generate Data error: <pre>" + str5 + "</pre></span></h3>");
/*     */         
/* 245 */         logError(sBRException.toString());
/*     */       } else {
/* 247 */         str5 = str5.substring(0, i);
/* 248 */         println("<pre>" + str5 + "</pre>");
/*     */       } 
/* 250 */       if (cATLGPUBMDLPDG != null) {
/* 251 */         println("<!-- " + cATLGPUBMDLPDG.getDebugInfo() + "-->");
/*     */       }
/* 253 */     } catch (Throwable throwable) {
/* 254 */       String[] arrayOfString = new String[1];
/* 255 */       StringWriter stringWriter = new StringWriter();
/* 256 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 257 */       String str7 = "<pre>{0}</pre>";
/* 258 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 259 */       setReturnCode(-1);
/* 260 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 262 */       arrayOfString[0] = throwable.getMessage();
/* 263 */       println(messageFormat.format(arrayOfString));
/* 264 */       messageFormat = new MessageFormat(str7);
/* 265 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 266 */       println(messageFormat.format(arrayOfString));
/* 267 */       logError("Exception: " + throwable.getMessage());
/* 268 */       logError(stringWriter.getBuffer().toString());
/* 269 */       if (cATLGPUBMDLPDG != null) {
/* 270 */         println("<!-- " + cATLGPUBMDLPDG.getDebugInfo() + "-->");
/*     */       }
/*     */     } finally {
/*     */       
/* 274 */       String str4 = null;
/* 275 */       String str5 = buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */             
/* 277 */             getABRDescription(), (getReturnCode() == 0) ? "Passed" : "Failed" });
/* 278 */       println("<br /><b>" + str5 + "</b>");
/* 279 */       log(str5);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 284 */       str4 = getABRDescription() + ":" + getEntityType() + ":" + getEntityID();
/* 285 */       if (str4.length() > 64) {
/* 286 */         str4 = str4.substring(0, 64);
/*     */       }
/* 288 */       setDGTitle(str4);
/* 289 */       setDGRptName("CATLGPUBABR01");
/*     */ 
/*     */       
/* 292 */       setDGString(getABRReturnCode());
/*     */       
/* 294 */       println(EACustom.getTOUDiv());
/*     */ 
/*     */       
/* 297 */       printDGSubmitString();
/*     */       
/* 299 */       buildReportFooter();
/*     */ 
/*     */       
/* 302 */       if (!isReadOnly()) {
/* 303 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printNavigateAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 313 */     byte b1 = 0;
/* 314 */     println("<table width=\"100%\">");
/* 315 */     println("<tr style=\"background-color:#cef;\"><th width=\"50%\">Navigation Attribute</th><th width=\"50%\">Attribute Value</th></tr>");
/*     */     
/* 317 */     for (byte b2 = 0; b2 < paramEntityGroup.getMetaAttributeCount(); b2++) {
/* 318 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b2);
/* 319 */       if (eANMetaAttribute.isNavigate()) {
/* 320 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 321 */         println("<tr class=\"" + ((b1++ % 2 != 0) ? "even" : "odd") + "\"><td width=\"50%\" >" + eANMetaAttribute
/*     */ 
/*     */             
/* 324 */             .getLongDescription() + "</td><td width=\"50%\">" + ((eANAttribute == null || eANAttribute
/*     */             
/* 326 */             .toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       } 
/*     */     } 
/*     */     
/* 330 */     println("</table>");
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
/* 362 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 371 */     return "Catalog Offering Publication For Model ABR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 381 */     return "1.23";
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
/* 401 */     return "CATLGPUBABR01.java " + getRevision();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readPropertyFile() {
/* 408 */     D.ebug("Reading catlgpubabr.properties");
/* 409 */     Properties properties = new Properties();
/* 410 */     boolean bool = true;
/*     */     try {
/* 412 */       File file = new File("./catlgpubabr.properties");
/* 413 */       if (!file.exists() || !file.canRead()) {
/* 414 */         System.out.println("Can't read " + file.getAbsolutePath());
/*     */         
/* 416 */         bool = false;
/*     */       } else {
/* 418 */         properties.load(new FileInputStream(file));
/*     */       } 
/* 420 */     } catch (IOException iOException) {
/* 421 */       System.out.println("Error reading catlgpubabr.properties");
/* 422 */       bool = false;
/*     */     } 
/*     */     
/* 425 */     if (bool) {
/* 426 */       this.m_iDays = Integer.parseInt(properties.getProperty("CATLGPUBABR01.DAYS", "0"));
/* 427 */       this.m_iChunk = Integer.parseInt(properties.getProperty("CATLGPUBABR01.CHUNK", "100"));
/* 428 */       this.m_bSearch = properties.getProperty("CATLGPUBABR01.SEARCH", "false").equalsIgnoreCase("true");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CATLGPUBABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */