/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
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
/*     */ public class SAPLABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  77 */   private StringBuffer rptSb = new StringBuffer();
/*  78 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  79 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */ 
/*     */   
/*     */   private boolean bdomainInList = false;
/*     */ 
/*     */   
/*  85 */   private ResourceBundle saplBundle = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private String xmlgen = "";
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
/* 112 */   private static final Hashtable SAPL_TRANS_TBL = new Hashtable<>(); static {
/* 113 */     SAPL_TRANS_TBL.put("20", "40");
/* 114 */     SAPL_TRANS_TBL.put("30", "40");
/* 115 */     SAPL_TRANS_TBL.put("40", "80");
/* 116 */     SAPL_TRANS_TBL.put("80", "70");
/*     */   }
/* 118 */   private static final Hashtable ABR_TBL = new Hashtable<>(); protected static final String XMLNS_WSNT = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd"; static {
/* 119 */     ABR_TBL.put("ACCTGUSEONLYMATRL", "COM.ibm.eannounce.abr.sg.ACCTGUSEONLYMATRLSAPLXML");
/* 120 */     ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.MODELSAPLXML");
/* 121 */     ABR_TBL.put("ORDABLEPARTNO", "COM.ibm.eannounce.abr.sg.ORDABLEPARTNOSAPLXML");
/* 122 */     ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.PRODSTRUCTSAPLXML");
/* 123 */     ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.SWPRODSTRUCTSAPLXML");
/* 124 */     ABR_TBL.put("LSCC", "COM.ibm.eannounce.abr.ls.LSCCSAPLXML");
/*     */   }
/*     */   protected static final String XMLNS_EBI = "http://ibm.com/esh/ebi";
/*     */   protected static final String ESHMQSERIES = "ESHMQSERIES";
/*     */   protected static final String OIDHMQSERIES = "OIDHMQSERIES";
/*     */   
/*     */   protected ResourceBundle getBundle() {
/* 131 */     return this.saplBundle;
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
/*     */   public void execute_run() {
/* 152 */     String str1 = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/* 161 */     String str3 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>XML generation: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
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
/* 173 */     String str4 = "";
/* 174 */     String str5 = "";
/* 175 */     String[] arrayOfString = new String[10];
/* 176 */     println(EACustom.getDocTypeHtml());
/*     */     
/*     */     try {
/* 179 */       long l = System.currentTimeMillis();
/*     */       
/* 181 */       start_ABRBuild(false);
/*     */ 
/*     */ 
/*     */       
/* 185 */       String str7 = (String)ABR_TBL.get(getEntityType());
/* 186 */       addDebug("creating instance of SAPLXML  = '" + str7 + "'");
/*     */       
/* 188 */       SAPLXMLBase sAPLXMLBase = (SAPLXMLBase)Class.forName(str7).newInstance();
/*     */ 
/*     */       
/* 191 */       String str8 = sAPLXMLBase.getSaplVeName();
/*     */       
/* 193 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str8), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/* 195 */               getEntityType(), getEntityID()) });
/*     */       
/* 197 */       addDebug("Time to get VE: " + (System.currentTimeMillis() - l) + " (mseconds)");
/*     */       
/* 199 */       setControlBlock();
/*     */ 
/*     */       
/* 202 */       this.saplBundle = ResourceBundle.getBundle(SAPLABRSTATUS.class.getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */ 
/*     */       
/* 205 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + getEntityType() + ":" + getEntityID() + " extract: " + str8 + NEWLINE + 
/* 206 */           PokUtils.outputList(this.m_elist));
/*     */ 
/*     */       
/* 209 */       setReturnCode(0);
/*     */ 
/*     */       
/* 212 */       str1 = getNavigationName();
/*     */ 
/*     */       
/* 215 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 216 */       str4 = this.m_elist.getParentEntityGroup().getLongDescription();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 221 */       domainNeedsChecks(entityItem);
/*     */       
/* 223 */       if (this.bdomainInList) {
/*     */         
/* 225 */         boolean bool = sAPLXMLBase.execute_run(this);
/* 226 */         this.xmlgen = sAPLXMLBase.getXMLGenMsg();
/* 227 */         str5 = getShortClassName(sAPLXMLBase.getClass()) + " " + sAPLXMLBase.getVersion();
/*     */         
/* 229 */         if (bool) {
/*     */           
/* 231 */           EANMetaAttribute eANMetaAttribute = getEntityList().getParentEntityGroup().getMetaAttribute("SAPL");
/* 232 */           if (eANMetaAttribute == null) {
/* 233 */             addDebug("SAPL was not in meta, nothing to update");
/*     */           } else {
/*     */             
/* 236 */             String str9 = getAttributeFlagEnabledValue(entityItem, "SAPL");
/* 237 */             String str10 = (String)SAPL_TRANS_TBL.get(str9);
/*     */             
/* 239 */             if (!str9.equals(str10)) {
/*     */               
/* 241 */               setFlagValue(this.m_elist.getProfile(), "SAPL", str10);
/*     */             } else {
/* 243 */               addDebug("SAPL was not updated, it is already set to: " + str10);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 248 */         this.xmlgen = getBundle().getString("DOMAIN_NOT_LISTED");
/*     */       }
/*     */     
/* 251 */     } catch (Throwable throwable) {
/*     */       
/* 253 */       StringWriter stringWriter = new StringWriter();
/* 254 */       String str7 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 255 */       String str8 = "<pre>{0}</pre>";
/* 256 */       MessageFormat messageFormat1 = new MessageFormat(str7);
/* 257 */       setReturnCode(-3);
/* 258 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 260 */       arrayOfString[0] = throwable.getMessage();
/* 261 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 262 */       messageFormat1 = new MessageFormat(str8);
/* 263 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 264 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 265 */       logError("Exception: " + throwable.getMessage());
/* 266 */       logError(stringWriter.getBuffer().toString());
/*     */     }
/*     */     finally {
/*     */       
/* 270 */       setDGTitle(str1);
/* 271 */       setDGRptName(getShortClassName(getClass()));
/* 272 */       setDGRptClass(getABRCode());
/*     */       
/* 274 */       if (!isReadOnly())
/*     */       {
/* 276 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 282 */     MessageFormat messageFormat = new MessageFormat(str2);
/* 283 */     arrayOfString[0] = getShortClassName(getClass());
/* 284 */     arrayOfString[1] = str1;
/* 285 */     String str6 = messageFormat.format(arrayOfString);
/* 286 */     messageFormat = new MessageFormat(str3);
/* 287 */     arrayOfString[0] = this.m_prof.getOPName();
/* 288 */     arrayOfString[1] = this.m_prof.getRoleDescription();
/* 289 */     arrayOfString[2] = this.m_prof.getWGName();
/* 290 */     arrayOfString[3] = getNow();
/* 291 */     arrayOfString[4] = str4;
/* 292 */     arrayOfString[5] = this.xmlgen;
/* 293 */     arrayOfString[6] = str5 + " " + getABRVersion();
/*     */     
/* 295 */     this.rptSb.insert(0, str6 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */     
/* 297 */     println(this.rptSb.toString());
/* 298 */     printDGSubmitString();
/* 299 */     println(EACustom.getTOUDiv());
/* 300 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityList getEntityList() {
/* 306 */     return this.m_elist;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Database getDB() {
/* 311 */     return this.m_db;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 316 */     this.rptSb.append(paramString + NEWLINE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 321 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 330 */     StringBuffer stringBuffer = new StringBuffer();
/* 331 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */     
/* 334 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/* 335 */     EANList eANList = entityGroup.getMetaAttribute();
/* 336 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 338 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 339 */       stringBuffer.append(PokUtils.getAttributeValue(entityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 340 */       stringBuffer.append(" ");
/*     */     } 
/*     */     
/* 343 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 353 */     return "1.16";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 362 */     return "SAPLABRSTATUS";
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
/*     */   private void setFlagValue(Profile paramProfile, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 376 */     logMessage(getDescription() + " ***** " + paramString1 + " set to: " + paramString2);
/* 377 */     addDebug("setFlagValue entered for " + paramString1 + " set to: " + paramString2);
/*     */     
/* 379 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */     
/* 382 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 383 */     if (eANMetaAttribute == null) {
/* 384 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/* 385 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + entityItem
/* 386 */           .getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/* 390 */     if (paramString2 != null) {
/*     */       
/*     */       try {
/*     */         
/* 394 */         if (this.m_cbOn == null) {
/* 395 */           setControlBlock();
/*     */         }
/* 397 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*     */         
/* 399 */         SingleFlag singleFlag = new SingleFlag(paramProfile.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*     */         
/* 401 */         Vector<SingleFlag> vector = new Vector();
/* 402 */         Vector<ReturnEntityKey> vector1 = new Vector();
/* 403 */         vector.addElement(singleFlag);
/* 404 */         returnEntityKey.m_vctAttributes = vector;
/* 405 */         vector1.addElement(returnEntityKey);
/*     */         
/* 407 */         this.m_db.update(paramProfile, vector1, false, false);
/* 408 */         this.m_db.commit();
/*     */       } finally {
/*     */         
/* 411 */         this.m_db.freeStatement();
/* 412 */         this.m_db.isPending("finally after update in setflag value");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale getLocale(int paramInt) {
/* 424 */     Locale locale = null;
/* 425 */     switch (paramInt)
/*     */     
/*     */     { case 1:
/* 428 */         locale = Locale.US;
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
/* 452 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/*     */   private void domainNeedsChecks(EntityItem paramEntityItem) {
/* 465 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 466 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 467 */     if (str.equals("all")) {
/* 468 */       this.bdomainInList = true;
/*     */     } else {
/* 470 */       HashSet<String> hashSet = new HashSet();
/* 471 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 472 */       while (stringTokenizer.hasMoreTokens()) {
/* 473 */         hashSet.add(stringTokenizer.nextToken());
/*     */       }
/* 475 */       this.bdomainInList = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 476 */       hashSet.clear();
/*     */     } 
/*     */     
/* 479 */     if (!this.bdomainInList)
/* 480 */       addDebug("PDHDOMAIN did not include " + str + ", execution is bypassed [" + 
/* 481 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SAPLABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */