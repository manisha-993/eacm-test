/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.CATLGPUBLSEOPDG;
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
/*     */ public class CATLGPUBABR03
/*     */   extends PokBaseABR
/*     */ {
/*     */   private static final String ABR = "CATLGPUBABR03";
/*  91 */   private PDGUtility m_utility = new PDGUtility();
/*  92 */   private EntityItem m_ei = null;
/*     */ 
/*     */   
/*     */   private static final int I64 = 64;
/*     */   
/*     */   private static final String STARTDATE = "1980-01-01-00.00.00.000000";
/*     */   
/*  99 */   private int m_iDays = 0;
/* 100 */   private int m_iChunk = 100;
/*     */ 
/*     */   
/*     */   private boolean m_bSearch = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 108 */     String str1 = null;
/* 109 */     String str2 = null;
/* 110 */     String str3 = null;
/* 111 */     EntityGroup entityGroup = null;
/* 112 */     CATLGPUBLSEOPDG cATLGPUBLSEOPDG = null;
/*     */     try {
/* 114 */       readPropertyFile();
/* 115 */       start_ABRBuild(false);
/* 116 */       println(EACustom.getDocTypeHtml());
/*     */       
/* 118 */       println("<head>" + EACustom.NEWLINE + 
/* 119 */           EACustom.getMetaTags(getDescription()) + EACustom.NEWLINE + 
/* 120 */           EACustom.getCSS() + EACustom.NEWLINE + 
/* 121 */           EACustom.getTitle(getDescription()) + EACustom.NEWLINE + "</head>" + EACustom.NEWLINE + "<body id=\"ibm-com\">");
/*     */ 
/*     */ 
/*     */       
/* 125 */       println(EACustom.getMastheadDiv());
/*     */       
/* 127 */       setReturnCode(0);
/*     */       
/* 129 */       str1 = this.m_db.getDates().getNow();
/* 130 */       printHeader(str1);
/*     */       
/* 132 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/* 133 */       this.m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 135 */       println("<p class=\"ibm-intro ibm-alternate-three\"><em>Catalog Country: " + this.m_ei.getKey() + "</em></p>");
/*     */       
/* 137 */       printNavigateAttributes(this.m_ei, entityGroup);
/*     */ 
/*     */       
/* 140 */       str2 = this.m_utility.getAttrValue(this.m_ei, "CATLGPUBLSEOLASTRUN");
/* 141 */       if (str2 == null || str2.length() <= 0) {
/* 142 */         OPICMList oPICMList = new OPICMList();
/* 143 */         str2 = "1980-01-01-00.00.00.000000";
/* 144 */         oPICMList.put("CATLGPUBLSEOLASTRUN", "CATLGPUBLSEOLASTRUN=" + str2);
/* 145 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/* 147 */       else if (!this.m_utility.isDateFormat(str2)) {
/* 148 */         println("<p>CATLGPUBLSEOLASTRUN is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000</p>");
/* 149 */         setReturnCode(-1);
/*     */       }
/* 151 */       else if (str2.length() == 10) {
/* 152 */         OPICMList oPICMList = new OPICMList();
/* 153 */         str2 = str2 + "-00.00.00.000000";
/* 154 */         oPICMList.put("CATLGPUBLSEOLASTRUN", "CATLGPUBLSEOLASTRUN=" + str2);
/* 155 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 160 */       str3 = this.m_utility.getAttrValue(this.m_ei, "OFFCOUNTRY");
/* 161 */       if (str3 == null || str3.length() <= 0) {
/* 162 */         println("<p>OFFCOUNTRY is blank.</p>");
/* 163 */         setReturnCode(-1);
/*     */       } 
/* 165 */       if (getReturnCode() == 0) {
/* 166 */         String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 167 */         log("CATLGPUBABR03 generating data using domains: " + str + " chunk: " + this.m_iChunk + " days: " + this.m_iDays + " useSrch: " + this.m_bSearch);
/*     */         
/* 169 */         cATLGPUBLSEOPDG = new CATLGPUBLSEOPDG(null, this.m_db, this.m_prof, str);
/* 170 */         cATLGPUBLSEOPDG.m_strEntityType = "LSEO";
/* 171 */         cATLGPUBLSEOPDG.setUpdatedBy("CATLGPUBABR03" + getRevision() + str1);
/* 172 */         cATLGPUBLSEOPDG.setEntityItem(this.m_ei);
/* 173 */         cATLGPUBLSEOPDG.setChunkSize(this.m_iChunk);
/* 174 */         cATLGPUBLSEOPDG.setDoSearch(this.m_bSearch);
/* 175 */         if (this.m_iDays <= 0) {
/* 176 */           OPICMList oPICMList = new OPICMList();
/* 177 */           cATLGPUBLSEOPDG.executeAction(this.m_db, this.m_prof);
/* 178 */           log("CATLGPUBABR03 finish generating data");
/* 179 */           println("<!-- " + cATLGPUBLSEOPDG.getDebugInfo() + "-->");
/*     */           
/* 181 */           oPICMList.put("CATLGPUBLSEOLASTRUN", "CATLGPUBLSEOLASTRUN=" + str1);
/* 182 */           this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */         } else {
/* 184 */           boolean bool = false;
/* 185 */           int i = 0;
/*     */           
/* 187 */           String str7 = "CATLGPUBLSEOLASTRUN";
/* 188 */           String str8 = str2;
/* 189 */           log("CATLGPUBABR03 in using date range mode");
/* 190 */           println("<!--CATLGPUBABR03 in using date range mode-->");
/* 191 */           if (str8.substring(0, 4).equals("1980")) {
/* 192 */             str8 = this.m_utility.getEarliestTime(this.m_db, this.m_prof, "LSEO");
/*     */           }
/* 194 */           cATLGPUBLSEOPDG.setUseDateRange(true);
/* 195 */           cATLGPUBLSEOPDG.setScanAll(false);
/*     */           
/* 197 */           String str6 = this.m_utility.getDate(str8.substring(0, 10), this.m_iDays) + "-23.59.59.000000";
/* 198 */           i = this.m_utility.longDateCompare(str6, str1);
/* 199 */           if (i == 1 && !bool) {
/* 200 */             log("CATLGPUBABR03 setting end time to now");
/* 201 */             println("<!-- CATLGPUBABR03 setting end time to now-->");
/* 202 */             str6 = str1;
/* 203 */             bool = true;
/* 204 */             i = 0;
/* 205 */             cATLGPUBLSEOPDG.setScanAll(true);
/*     */           } 
/*     */           
/* 208 */           while (i != 1) {
/* 209 */             OPICMList oPICMList = new OPICMList();
/* 210 */             cATLGPUBLSEOPDG.setStartTime(str8);
/* 211 */             cATLGPUBLSEOPDG.setEndTime(str6);
/*     */             
/* 213 */             log("CATLGPUBABR03 last run time: " + str8 + ", end time: " + str6);
/* 214 */             println("<!-- CATLGPUBABR03 last run time: " + str8 + ", end time: " + str6 + "-->");
/*     */             
/* 216 */             cATLGPUBLSEOPDG.executeAction(this.m_db, this.m_prof);
/* 217 */             println("<!-- " + cATLGPUBLSEOPDG.getDebugInfo() + "-->");
/*     */             
/* 219 */             oPICMList.put(str7, str7 + "=" + str6);
/* 220 */             this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */             
/* 222 */             str8 = str6;
/* 223 */             str6 = this.m_utility.getDate(str8.substring(0, 10), this.m_iDays) + "-23.59.59.000000";
/*     */             
/* 225 */             i = this.m_utility.longDateCompare(str6, str1);
/* 226 */             if (i == 1 && !bool) {
/* 227 */               str6 = str1;
/* 228 */               bool = true;
/* 229 */               i = 0;
/* 230 */               cATLGPUBLSEOPDG.setScanAll(true);
/*     */             } 
/* 232 */             this.m_utility.memory(true);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 237 */     } catch (SBRException sBRException) {
/* 238 */       String str5 = sBRException.toString();
/* 239 */       int i = str5.indexOf("(ok)");
/* 240 */       if (i < 0) {
/* 241 */         setReturnCode(-2);
/* 242 */         println("<h3><span style=\"color:#c00; font-weight:bold;\">Generate Data error: <pre>" + str5 + "</pre></span></h3>");
/*     */         
/* 244 */         logError(sBRException.toString());
/*     */       } else {
/* 246 */         str5 = str5.substring(0, i);
/* 247 */         println("<pre>" + str5 + "</pre>");
/*     */       } 
/* 249 */       if (cATLGPUBLSEOPDG != null) {
/* 250 */         println("<!-- " + cATLGPUBLSEOPDG.getDebugInfo() + "-->");
/*     */       }
/* 252 */     } catch (Throwable throwable) {
/* 253 */       String[] arrayOfString = new String[1];
/* 254 */       StringWriter stringWriter = new StringWriter();
/* 255 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 256 */       String str7 = "<pre>{0}</pre>";
/* 257 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 258 */       setReturnCode(-1);
/* 259 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 261 */       arrayOfString[0] = throwable.getMessage();
/* 262 */       println(messageFormat.format(arrayOfString));
/* 263 */       messageFormat = new MessageFormat(str7);
/* 264 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 265 */       println(messageFormat.format(arrayOfString));
/* 266 */       logError("Exception: " + throwable.getMessage());
/* 267 */       logError(stringWriter.getBuffer().toString());
/* 268 */       if (cATLGPUBLSEOPDG != null) {
/* 269 */         println("<!-- " + cATLGPUBLSEOPDG.getDebugInfo() + "-->");
/*     */       }
/*     */     } finally {
/*     */       
/* 273 */       String str4 = null;
/* 274 */       String str5 = buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */             
/* 276 */             getABRDescription(), (getReturnCode() == 0) ? "Passed" : "Failed" });
/* 277 */       println("<br /><b>" + str5 + "</b>");
/* 278 */       log(str5);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 283 */       str4 = getABRDescription() + ":" + getEntityType() + ":" + getEntityID();
/* 284 */       if (str4.length() > 64) {
/* 285 */         str4 = str4.substring(0, 64);
/*     */       }
/* 287 */       setDGTitle(str4);
/* 288 */       setDGRptName("CATLGPUBABR03");
/*     */ 
/*     */       
/* 291 */       setDGString(getABRReturnCode());
/*     */       
/* 293 */       println(EACustom.getTOUDiv());
/*     */ 
/*     */       
/* 296 */       printDGSubmitString();
/*     */       
/* 298 */       buildReportFooter();
/*     */ 
/*     */       
/* 301 */       if (!isReadOnly()) {
/* 302 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printNavigateAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 312 */     byte b1 = 0;
/* 313 */     println("<table width=\"100%\">");
/* 314 */     println("<tr style=\"background-color:#cef;\"><th width=\"50%\">Navigation Attribute</th><th width=\"50%\">Attribute Value</th></tr>");
/*     */     
/* 316 */     for (byte b2 = 0; b2 < paramEntityGroup.getMetaAttributeCount(); b2++) {
/* 317 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b2);
/* 318 */       if (eANMetaAttribute.isNavigate()) {
/* 319 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 320 */         println("<tr class=\"" + ((b1++ % 2 != 0) ? "even" : "odd") + "\"><td width=\"50%\" >" + eANMetaAttribute
/*     */ 
/*     */             
/* 323 */             .getLongDescription() + "</td><td width=\"50%\">" + ((eANAttribute == null || eANAttribute
/*     */             
/* 325 */             .toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       } 
/*     */     } 
/*     */     
/* 329 */     println("</table>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printHeader(String paramString) {
/* 336 */     println("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\">");
/* 337 */     println("<tr><th width=\"25%\">ABRName: </th><td>" + 
/* 338 */         getABRCode() + "</td></tr>");
/* 339 */     println("<tr><th width=\"25%\">Abr Version: </th><td>" + 
/* 340 */         getABRVersion() + "</td></tr>");
/* 341 */     println("<tr><th width=\"25%\">Enterprise: </th><td>" + 
/* 342 */         getEnterprise() + "</td></tr>");
/* 343 */     println("<tr><th width=\"25%\">Valid Date: </th><td>" + this.m_prof
/* 344 */         .getValOn() + "</td></tr>");
/* 345 */     println("<tr><th width=\"25%\">Effective Date: </th><td>" + this.m_prof
/* 346 */         .getEffOn() + "</td></tr>");
/* 347 */     println("<tr><th width=\"25%\">Date Generated: </th><td>" + paramString + "</td></tr></table>");
/*     */ 
/*     */     
/* 350 */     println("<h3>Description: </h3><p>" + getDescription() + "</p><hr>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 361 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 370 */     return "Catalog Offering Publication For LSEO ABR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 380 */     return "1.17";
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
/* 400 */     return "CATLGPUBABR03.java " + getRevision();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readPropertyFile() {
/* 407 */     Properties properties = new Properties();
/* 408 */     boolean bool = true;
/* 409 */     D.ebug("Reading catlgpubabr.properties");
/*     */     try {
/* 411 */       File file = new File("./catlgpubabr.properties");
/* 412 */       if (!file.exists() || !file.canRead()) {
/* 413 */         System.out.println("Can't read " + file.getAbsolutePath());
/*     */         
/* 415 */         bool = false;
/*     */       } else {
/* 417 */         properties.load(new FileInputStream(file));
/*     */       } 
/* 419 */     } catch (IOException iOException) {
/* 420 */       System.out.println("Error reading catlgpubabr.properties " + iOException);
/* 421 */       bool = false;
/*     */     } 
/*     */     
/* 424 */     if (bool) {
/* 425 */       this.m_iDays = Integer.parseInt(properties.getProperty("CATLGPUBABR03.DAYS", "0"));
/* 426 */       this.m_iChunk = Integer.parseInt(properties.getProperty("CATLGPUBABR03.CHUNK", "100"));
/* 427 */       this.m_bSearch = properties.getProperty("CATLGPUBABR03.SEARCH", "false").equalsIgnoreCase("true");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CATLGPUBABR03.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */