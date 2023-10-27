/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*     */ import COM.ibm.eannounce.objects.ChangeHistoryItem;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
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
/*     */ public abstract class EPIMSWWPRTBASE
/*     */   extends PokBaseABR
/*     */ {
/*  59 */   private StringBuffer rptSb = new StringBuffer();
/*  60 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  61 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*     */   protected static final String STATUS_FINAL = "0020";
/*     */   
/*     */   private Vector finalDtsVct;
/*     */   private boolean bdomainInList = false;
/*  67 */   private ResourceBundle rsBundle = null;
/*  68 */   private Hashtable metaTbl = new Hashtable<>();
/*  69 */   private String navName = "";
/*     */   
/*  71 */   private String xmlgen = "Not required";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private static final Hashtable NDN_TBL = new Hashtable<>();
/*  77 */   private static final Hashtable STATUSATTR_TBL = new Hashtable<>(); static {
/*  78 */     STATUSATTR_TBL.put("ANNOUNCEMENT", "ANNSTATUS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     NDN nDN1 = new NDN("MODEL", "(TM)");
/*  91 */     nDN1.addAttr("MACHTYPEATR");
/*  92 */     nDN1.addAttr("MODELATR");
/*  93 */     nDN1.addAttr("COFCAT");
/*  94 */     nDN1.addAttr("COFSUBCAT");
/*  95 */     nDN1.addAttr("COFGRP");
/*  96 */     nDN1.addAttr("COFSUBGRP");
/*  97 */     NDN nDN2 = new NDN("FEATURE", "(FC)");
/*  98 */     nDN2.addAttr("FEATURECODE");
/*  99 */     nDN1.setNext(nDN2);
/* 100 */     NDN_TBL.put("PRODSTRUCT", nDN1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     nDN1 = new NDN("MODEL", "(TM)");
/* 113 */     nDN1.addAttr("MACHTYPEATR");
/* 114 */     nDN1.addAttr("MODELATR");
/* 115 */     nDN1.addAttr("COFCAT");
/* 116 */     nDN1.addAttr("COFSUBCAT");
/* 117 */     nDN1.addAttr("COFGRP");
/* 118 */     nDN1.addAttr("COFSUBGRP");
/* 119 */     nDN2 = new NDN("SWFEATURE", "(FC)");
/* 120 */     nDN2.addAttr("FEATURECODE");
/* 121 */     nDN1.setNext(nDN2);
/* 122 */     NDN_TBL.put("SWPRODSTRUCT", nDN1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceBundle getBundle() {
/* 129 */     return this.rsBundle;
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
/*     */   public void execute_run() {
/* 156 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/* 158 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>XML generation: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     String str3 = "";
/* 170 */     String str4 = "";
/* 171 */     EPIMSABRBase ePIMSABRBase = null;
/* 172 */     String[] arrayOfString = new String[10];
/* 173 */     println(EACustom.getDocTypeHtml());
/*     */     
/*     */     try {
/* 176 */       long l = System.currentTimeMillis();
/*     */       
/* 178 */       start_ABRBuild(false);
/*     */ 
/*     */       
/* 181 */       String str6 = "dummy";
/*     */       
/* 183 */       if (getEntityType().equals("PRODSTRUCT")) {
/* 184 */         str6 = "EXRPT3FM";
/* 185 */       } else if (getEntityType().equals("SWPRODSTRUCT")) {
/* 186 */         str6 = "DQVESWPRODSTRUCT2";
/*     */       } 
/*     */ 
/*     */       
/* 190 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str6), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/* 192 */               getEntityType(), getEntityID()) });
/*     */       
/* 194 */       addDebug("Time to get VE: " + (System.currentTimeMillis() - l) + " (mseconds)");
/*     */       
/* 196 */       String str7 = (String)STATUSATTR_TBL.get(getEntityType());
/* 197 */       if (str7 == null) {
/* 198 */         str7 = "STATUS";
/*     */       }
/*     */       
/* 201 */       this.finalDtsVct = getChangeTimes(str7, "0020");
/*     */       
/* 203 */       setControlBlock();
/*     */ 
/*     */       
/* 206 */       this.rsBundle = ResourceBundle.getBundle(EPIMSWWPRTBASE.class.getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */ 
/*     */       
/* 209 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + getEntityType() + getEntityID() + " extract: " + str6 + " using DTS: " + this.m_prof
/* 210 */           .getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*     */ 
/*     */       
/* 213 */       setReturnCode(0);
/*     */ 
/*     */       
/* 216 */       this.navName = getNavigationName();
/*     */ 
/*     */       
/* 219 */       str3 = this.m_elist.getParentEntityGroup().getLongDescription();
/*     */ 
/*     */ 
/*     */       
/* 223 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */       
/* 226 */       domainNeedsChecks(entityItem);
/*     */       
/* 228 */       if (this.bdomainInList) {
/*     */ 
/*     */         
/* 231 */         String str = getSimpleABRName();
/* 232 */         if (str != null) {
/* 233 */           ePIMSABRBase = (EPIMSABRBase)Class.forName(str).newInstance();
/*     */ 
/*     */           
/* 236 */           ePIMSABRBase.execute_run(this);
/* 237 */           this.xmlgen = ePIMSABRBase.getXMLGenMsg();
/* 238 */           str4 = getShortClassName(ePIMSABRBase.getClass()) + " " + ePIMSABRBase.getVersion();
/*     */         } else {
/* 240 */           addError(getShortClassName(getClass()) + " does not support " + getEntityType());
/*     */         } 
/*     */ 
/*     */         
/* 244 */         if (getReturnCode() == 0) {
/* 245 */           setCreateDGEntity(false);
/*     */         }
/*     */       } else {
/* 248 */         this.xmlgen = getBundle().getString("DOMAIN_NOT_LISTED");
/*     */       } 
/*     */       
/* 251 */       this.finalDtsVct.clear();
/*     */     }
/* 253 */     catch (Throwable throwable) {
/*     */       
/* 255 */       StringWriter stringWriter = new StringWriter();
/* 256 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 257 */       String str7 = "<pre>{0}</pre>";
/* 258 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/* 259 */       setReturnCode(-3);
/* 260 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 262 */       arrayOfString[0] = throwable.getMessage();
/* 263 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 264 */       messageFormat1 = new MessageFormat(str7);
/* 265 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 266 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 267 */       logError("Exception: " + throwable.getMessage());
/* 268 */       logError(stringWriter.getBuffer().toString());
/*     */     }
/*     */     finally {
/*     */       
/* 272 */       setDGTitle(this.navName);
/* 273 */       setDGRptName(getShortClassName(getClass()));
/* 274 */       setDGRptClass(getABRCode());
/*     */       
/* 276 */       if (!isReadOnly())
/*     */       {
/* 278 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 284 */     MessageFormat messageFormat = new MessageFormat(str1);
/* 285 */     if (ePIMSABRBase != null) {
/* 286 */       arrayOfString[0] = getShortClassName(ePIMSABRBase.getClass());
/*     */     } else {
/* 288 */       arrayOfString[0] = getShortClassName(getClass());
/*     */     } 
/* 290 */     arrayOfString[1] = this.navName;
/* 291 */     String str5 = messageFormat.format(arrayOfString);
/* 292 */     messageFormat = new MessageFormat(str2);
/* 293 */     arrayOfString[0] = this.m_prof.getOPName();
/* 294 */     arrayOfString[1] = this.m_prof.getRoleDescription();
/* 295 */     arrayOfString[2] = this.m_prof.getWGName();
/* 296 */     arrayOfString[3] = getNow();
/* 297 */     arrayOfString[4] = str3;
/* 298 */     arrayOfString[5] = this.xmlgen;
/* 299 */     arrayOfString[6] = str4 + " " + getABRVersion();
/*     */     
/* 301 */     this.rptSb.insert(0, str5 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */     
/* 303 */     println(this.rptSb.toString());
/* 304 */     printDGSubmitString();
/* 305 */     println(EACustom.getTOUDiv());
/* 306 */     buildReportFooter();
/*     */     
/* 308 */     this.metaTbl.clear();
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
/*     */   private String getABRQueuedTime() throws MiddlewareRequestException {
/* 322 */     String str = this.m_abri.getABRCode();
/* 323 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */     
/* 325 */     addDebug("getABRQueuedTime entered for " + entityItem.getKey() + " " + str);
/* 326 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 327 */     if (eANAttribute != null) {
/* 328 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/* 329 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 1) {
/*     */         
/* 331 */         int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 2;
/* 332 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 333 */         addDebug("getABRQueuedTime [" + i + "] isActive: " + attributeChangeHistoryItem
/* 334 */             .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 335 */             .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*     */         
/* 337 */         return attributeChangeHistoryItem.getChangeDate();
/*     */       } 
/*     */       
/* 340 */       addDebug("getABRQueuedTime for " + entityItem.getKey() + " " + str + " has no history");
/*     */     } else {
/*     */       
/* 343 */       addDebug("getABRQueuedTime for " + entityItem.getKey() + " " + str + " was null");
/*     */     } 
/*     */     
/* 346 */     return getNow();
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
/*     */   protected String generateString(EntityItem paramEntityItem, String[] paramArrayOfString) {
/* 358 */     StringBuffer stringBuffer = new StringBuffer(paramEntityItem.getKey());
/* 359 */     if (paramArrayOfString != null) {
/* 360 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 361 */         stringBuffer.append(":" + PokUtils.getAttributeValue(paramEntityItem, paramArrayOfString[b], ", ", "", false));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 366 */     return stringBuffer.toString();
/*     */   }
/*     */   protected boolean isFirstFinal() {
/* 369 */     return this.firstFinal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean firstFinal = true;
/*     */ 
/*     */   
/*     */   protected Vector getChangeTimes(String paramString1, String paramString2) throws MiddlewareRequestException {
/* 378 */     Vector<String> vector = new Vector(1);
/* 379 */     Vector<ChangeHistoryItem> vector1 = new Vector(1);
/*     */     
/* 381 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 382 */     addDebug("getChangeTimes entered for " + entityItem.getKey() + " " + paramString1 + " flag: " + paramString2);
/* 383 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString1);
/* 384 */     if (eANAttribute != null) {
/*     */       
/* 386 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = new AttributeChangeHistoryGroup(this.m_db, this.m_elist.getProfile(), eANAttribute);
/* 387 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 388 */         int i; for (i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--)
/*     */         {
/* 390 */           vector1.add(attributeChangeHistoryGroup.getChangeHistoryItem(i));
/*     */         }
/*     */         
/* 393 */         Collections.sort(vector1, new ChiComparator());
/* 394 */         for (i = 0; i < vector1.size(); i++) {
/* 395 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)vector1.elementAt(i);
/* 396 */           addDebug("getChangeTimes " + paramString1 + "[" + i + "] isActive: " + attributeChangeHistoryItem
/* 397 */               .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 398 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 399 */           if (paramString2 == null) {
/* 400 */             vector.add(attributeChangeHistoryItem.getChangeDate());
/*     */             break;
/*     */           } 
/* 403 */           if (paramString2.equals(attributeChangeHistoryItem.getFlagCode())) {
/* 404 */             vector.add(attributeChangeHistoryItem.getChangeDate());
/* 405 */             if (vector.size() == 2) {
/* 406 */               this.firstFinal = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 412 */         vector1.clear();
/*     */       } 
/* 414 */       addDebug("getChangeTimes Before using queued dts " + entityItem.getKey() + " " + vector);
/* 415 */       if (vector.size() > 0) {
/* 416 */         vector.remove(0);
/* 417 */         vector.insertElementAt(getABRQueuedTime(), 0);
/*     */       } else {
/* 419 */         vector.add(getABRQueuedTime());
/*     */       } 
/* 421 */       addDebug("getChangeTimes using queued dts " + entityItem.getKey() + " " + vector);
/*     */     } else {
/* 423 */       addDebug("ERROR: getChangeTimes for " + entityItem.getKey() + " " + paramString1 + "  was null use queued time");
/* 424 */       vector.add(getABRQueuedTime());
/*     */     } 
/*     */     
/* 427 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityList getEntityList() {
/* 433 */     return this.m_elist;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLastFinalDTS() {
/* 438 */     return this.finalDtsVct.firstElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPriorFinalDTS() {
/* 444 */     String str = getLastFinalDTS();
/* 445 */     if (this.finalDtsVct.size() > 1) {
/* 446 */       str = this.finalDtsVct.lastElement();
/*     */     }
/* 448 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Database getDB() {
/* 454 */     return this.m_db;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 459 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDebug(String paramString) {
/* 464 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addError(String paramString) {
/* 470 */     addOutput(paramString);
/* 471 */     setReturnCode(-1);
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
/*     */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 484 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 485 */     setReturnCode(-1);
/*     */ 
/*     */     
/* 488 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 489 */     Object[] arrayOfObject = new Object[2];
/* 490 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 491 */     arrayOfObject[1] = this.navName;
/*     */     
/* 493 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
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
/*     */   protected void addWarning(String paramString, Object[] paramArrayOfObject) {
/* 507 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*     */ 
/*     */     
/* 510 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("WARNING_PREFIX"));
/* 511 */     Object[] arrayOfObject = new Object[2];
/* 512 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 513 */     arrayOfObject[1] = this.navName;
/*     */     
/* 515 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 524 */     String str = getBundle().getString(paramString2);
/*     */     
/* 526 */     if (paramArrayOfObject != null) {
/* 527 */       MessageFormat messageFormat = new MessageFormat(str);
/* 528 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 531 */     addOutput(paramString1 + " " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 541 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 551 */     StringBuffer stringBuffer = new StringBuffer();
/* 552 */     NDN nDN = (NDN)NDN_TBL.get(paramEntityItem.getEntityType());
/*     */ 
/*     */     
/* 555 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 556 */     if (eANList == null) {
/*     */       
/* 558 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 559 */       eANList = entityGroup.getMetaAttribute();
/* 560 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 562 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 564 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 565 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 566 */       if (b + 1 < eANList.size()) {
/* 567 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 570 */     if (nDN != null) {
/* 571 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 572 */       EntityItem entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/* 573 */       if (entityItem != null) {
/* 574 */         stringBuffer1.append("(" + nDN.getTag());
/* 575 */         for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 576 */           String str = nDN.getAttr().elementAt(b1).toString();
/* 577 */           stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 578 */           if (b1 + 1 < nDN.getAttr().size()) {
/* 579 */             stringBuffer1.append(" ");
/*     */           }
/*     */         } 
/* 582 */         stringBuffer1.append(") ");
/*     */       } else {
/* 584 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType());
/*     */       } 
/* 586 */       nDN = nDN.getNext();
/* 587 */       if (nDN != null) {
/* 588 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/* 589 */         if (entityItem != null) {
/* 590 */           stringBuffer1.append("(" + nDN.getTag());
/* 591 */           for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 592 */             String str = nDN.getAttr().elementAt(b1).toString();
/* 593 */             stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 594 */             if (b1 + 1 < nDN.getAttr().size()) {
/* 595 */               stringBuffer1.append(" ");
/*     */             }
/*     */           } 
/* 598 */           stringBuffer1.append(") ");
/*     */         } else {
/* 600 */           addDebug("NO entity found for next ndn.getEntityType(): " + nDN.getEntityType());
/*     */         } 
/*     */       } 
/* 603 */       stringBuffer.insert(0, stringBuffer1.toString());
/*     */     } 
/*     */     
/* 606 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityItem getNDNitem(EntityItem paramEntityItem, String paramString) {
/*     */     byte b;
/* 616 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 617 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 618 */       if (entityItem.getEntityType().equals(paramString)) {
/* 619 */         return entityItem;
/*     */       }
/*     */     } 
/* 622 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 623 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 624 */       if (entityItem.getEntityType().equals(paramString)) {
/* 625 */         return entityItem;
/*     */       }
/*     */     } 
/* 628 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 638 */     return "1.9";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 647 */     return "EPIMSWWPRTBASE";
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
/*     */   protected void setFlagValue(String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 661 */     logMessage(getDescription() + " ***** " + paramString1 + " set to: " + paramString2);
/* 662 */     addDebug("setFlagValue entered for " + paramString1 + " set to: " + paramString2);
/* 663 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */     
/* 666 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 667 */     if (eANMetaAttribute == null) {
/* 668 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/* 669 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + entityItem
/* 670 */           .getEntityType() + ", nothing to do");
/*     */       return;
/*     */     } 
/* 673 */     if (paramString2 != null)
/*     */     {
/* 675 */       if (paramString2.equals(getAttributeFlagEnabledValue(entityItem, paramString1))) {
/* 676 */         addDebug("setFlagValue " + entityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*     */       } else {
/*     */ 
/*     */         
/*     */         try {
/* 681 */           if (this.m_cbOn == null) {
/* 682 */             setControlBlock();
/*     */           }
/* 684 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*     */           
/* 686 */           SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*     */           
/* 688 */           Vector<SingleFlag> vector = new Vector();
/* 689 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 690 */           vector.addElement(singleFlag);
/* 691 */           returnEntityKey.m_vctAttributes = vector;
/* 692 */           vector1.addElement(returnEntityKey);
/*     */           
/* 694 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 695 */           addDebug(entityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*     */         } finally {
/*     */           
/* 698 */           this.m_db.commit();
/* 699 */           this.m_db.freeStatement();
/* 700 */           this.m_db.isPending("finally after update in setflag value");
/*     */         } 
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
/* 713 */     Locale locale = null;
/* 714 */     switch (paramInt)
/*     */     
/*     */     { case 1:
/* 717 */         locale = Locale.US;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 741 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/* 754 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 755 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 756 */     if (str.equals("all")) {
/* 757 */       this.bdomainInList = true;
/*     */     } else {
/* 759 */       HashSet<String> hashSet = new HashSet();
/* 760 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 761 */       while (stringTokenizer.hasMoreTokens()) {
/* 762 */         hashSet.add(stringTokenizer.nextToken());
/*     */       }
/* 764 */       this.bdomainInList = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 765 */       hashSet.clear();
/*     */     } 
/*     */     
/* 768 */     if (!this.bdomainInList) {
/* 769 */       addDebug("PDHDOMAIN did not include " + str + ", execution is bypassed [" + 
/* 770 */           PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN"));
/*     */     }
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
/*     */   protected boolean checkABRCountryList(EntityItem paramEntityItem) {
/* 788 */     boolean bool = false;
/* 789 */     String str = ABRServerProperties.getCountryList(this.m_abri.getABRCode());
/* 790 */     addDebug("checkABRCountryList countrylist: " + str);
/* 791 */     if (str.equals("all")) {
/* 792 */       bool = true;
/*     */     } else {
/* 794 */       HashSet<String> hashSet = new HashSet();
/* 795 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 796 */       while (stringTokenizer.hasMoreTokens()) {
/* 797 */         hashSet.add(stringTokenizer.nextToken());
/*     */       }
/* 799 */       bool = PokUtils.contains(paramEntityItem, "COUNTRYLIST", hashSet);
/* 800 */       hashSet.clear();
/*     */     } 
/*     */     
/* 803 */     if (!bool) {
/* 804 */       addDebug(paramEntityItem.getKey() + ".COUNTRYLIST [" + 
/* 805 */           PokUtils.getAttributeValue(paramEntityItem, "COUNTRYLIST", ", ", "", false) + "] did not include " + str + ", notification will not be sent ");
/*     */     }
/*     */     
/* 808 */     return bool;
/*     */   }
/*     */   
/*     */   protected abstract String getSimpleABRName();
/*     */   
/*     */   private class ChiComparator implements Comparator { private ChiComparator() {}
/*     */     
/*     */     public int compare(Object param1Object1, Object param1Object2) {
/* 816 */       ChangeHistoryItem changeHistoryItem1 = (ChangeHistoryItem)param1Object1;
/* 817 */       ChangeHistoryItem changeHistoryItem2 = (ChangeHistoryItem)param1Object2;
/* 818 */       return changeHistoryItem2.getChangeDate().compareTo(changeHistoryItem1.getChangeDate());
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class NDN {
/*     */     private String etype;
/*     */     private String tag;
/*     */     private NDN next;
/* 826 */     private Vector attrVct = new Vector();
/*     */     NDN(String param1String1, String param1String2) {
/* 828 */       this.etype = param1String1;
/* 829 */       this.tag = param1String2;
/*     */     }
/* 831 */     String getTag() { return this.tag; }
/* 832 */     String getEntityType() { return this.etype; } Vector getAttr() {
/* 833 */       return this.attrVct;
/*     */     } void addAttr(String param1String) {
/* 835 */       this.attrVct.addElement(param1String);
/*     */     }
/* 837 */     void setNext(NDN param1NDN) { this.next = param1NDN; } NDN getNext() {
/* 838 */       return this.next;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\EPIMSWWPRTBASE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */