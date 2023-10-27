/*     */ package COM.ibm.eannounce.darule;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Hashtable;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CATDATAABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  33 */   private StringBuffer rptSb = new StringBuffer();
/*     */   
/*  35 */   private StringBuffer xmlgenSb = new StringBuffer();
/*     */   
/*  37 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*     */   
/*  39 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*  41 */   private PrintWriter dbgPw = null;
/*     */   
/*  43 */   private int dbgLen = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private String actionTaken = "";
/*     */   
/*  67 */   private String navName = "";
/*     */   
/*  69 */   private String rootDesc = "";
/*     */   
/*  71 */   private EntityList m_elist = null;
/*     */   
/*  73 */   private ResourceBundle rsBundle = null;
/*     */   
/*  75 */   private Object[] args = (Object[])new String[10];
/*     */   
/*  77 */   private StringBuffer userxmlSb = new StringBuffer();
/*     */   
/*  79 */   protected static final Hashtable ITEM_VE_TBL = new Hashtable<>(); static {
/*  80 */     ITEM_VE_TBL.put("WWSEO", "DAVEWWSEO");
/*  81 */     ITEM_VE_TBL.put("FEATURE", "DAVEFEATURE");
/*  82 */     ITEM_VE_TBL.put("MODEL", "DAVEMODEL");
/*  83 */     ITEM_VE_TBL.put("SVCMOD", "DAVESVCMOD");
/*  84 */     ITEM_VE_TBL.put("LSEOBUNDLE", "DAVELSEOBUNDLE");
/*  85 */     ITEM_VE_TBL.put("LSEO", "DAVELSEO");
/*     */   }
/*     */   
/*  88 */   protected static final Hashtable WTHDRWEFFCTVDATE_Attr_TBL = new Hashtable<>(); protected static final String STATUS_FINAL = "0020"; static {
/*  89 */     WTHDRWEFFCTVDATE_Attr_TBL.put("FEATURE", "WITHDRAWDATEEFF_T");
/*  90 */     WTHDRWEFFCTVDATE_Attr_TBL.put("LSEO", "LSEOUNPUBDATEMTRGT");
/*  91 */     WTHDRWEFFCTVDATE_Attr_TBL.put("LSEOBUNDLE", "BUNDLUNPUBDATEMTRGT");
/*  92 */     WTHDRWEFFCTVDATE_Attr_TBL.put("MODEL", "WTHDRWEFFCTVDATE");
/*  93 */     WTHDRWEFFCTVDATE_Attr_TBL.put("SVCMOD", "WTHDRWEFFCTVDATE");
/*  94 */     WTHDRWEFFCTVDATE_Attr_TBL.put("SWFEATURE", "WITHDRAWDATEEFF_T");
/*  95 */     WTHDRWEFFCTVDATE_Attr_TBL.put("WWSEO", "WTHDRWEFFCTVDATE");
/*     */   }
/*     */   protected static final String STATUS_R4REVIEW = "0040";
/*     */   protected static final String ABR_INPROCESS = "0050";
/*     */   protected static final String CHEAT = "@@";
/*     */   protected static final String LIFECYCLE_Develop = "LF02";
/*     */   protected static final String LIFECYCLE_Plan = "LF01";
/*     */   protected static final String LIFECYCLE_Launch = "LF03";
/*     */   protected static final String DALIFECYCLE_Change = "50";
/*     */   
/*     */   public void execute_run() {
/* 106 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */ 
/*     */ 
/*     */     
/* 110 */     println(EACustom.getDocTypeHtml());
/*     */     try {
/* 112 */       start_ABRBuild(false);
/*     */       
/* 114 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/* 115 */       setReturnCode(0);
/* 116 */       String str5 = getEntityType();
/* 117 */       String str6 = (String)ITEM_VE_TBL.get(str5);
/* 118 */       if (str6 != null) {
/*     */         
/* 120 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, str6);
/*     */         
/* 122 */         this.m_elist = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, getEntityType(), 
/* 123 */                 getEntityID()) });
/*     */         
/* 125 */         EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 126 */         this.rootDesc = this.m_elist.getParentEntityGroup().getLongDescription();
/* 127 */         String str = getAttributeFlagEnabledValue(entityItem, "STATUS");
/*     */         
/* 129 */         this.navName = getNavigationName(entityItem);
/*     */         try {
/* 131 */           if (!"0020".equals(str) && !"0040".equals(str)) {
/*     */ 
/*     */             
/* 134 */             addError(this.rsBundle.getString("ROOT_NOT_RFRFIANL"));
/* 135 */           } else if ("WWSEO".equals(entityItem.getEntityType())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 141 */             Vector<EntityItem> vector = getValidLSEO(entityItem);
/* 142 */             if (vector.size() > 0) {
/* 143 */               if (verifyMODELWithdrawEffe(entityItem)) {
/* 144 */                 boolean bool = DARuleEngineMgr.updateCatData(this.m_db, this.m_prof, entityItem, this.rptSb);
/* 145 */                 if (bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 152 */                   for (byte b = 0; b < vector.size(); b++) {
/* 153 */                     EntityItem entityItem1 = vector.elementAt(b);
/* 154 */                     if (isValidQueueItem(entityItem1)) {
/* 155 */                       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"), entityItem1);
/*     */                       
/* 157 */                       this.actionTaken = this.rsBundle.getString("SUCCESS");
/*     */                     } else {
/* 159 */                       addDebug(this.rsBundle.getString("STATUS_Final_Before"));
/*     */                     } 
/*     */                   } 
/*     */                 } else {
/* 163 */                   this.actionTaken = this.rsBundle.getString("NO_CHANGE_FOUND");
/*     */                 } 
/*     */               } 
/*     */             } else {
/*     */               
/* 168 */               addDebug(entityItem.getKey() + " have no child LSEO which is not Final or R4R");
/*     */               
/* 170 */               addError(this.rsBundle.getString("NOT_FOUND_LSEORFRFINAL"));
/*     */             } 
/* 172 */             vector.clear();
/*     */           }
/* 174 */           else if (verifyMODELWithdrawEffe(entityItem)) {
/* 175 */             boolean bool = DARuleEngineMgr.updateCatData(this.m_db, this.m_prof, entityItem, this.rptSb);
/* 176 */             if (bool) {
/* 177 */               if (isValidQueueItem(entityItem)) {
/* 178 */                 setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"), entityItem);
/*     */                 
/* 180 */                 this.actionTaken = this.rsBundle.getString("SUCCESS");
/*     */               } else {
/* 182 */                 addDebug(this.rsBundle.getString("STATUS_Final_Before"));
/*     */               } 
/*     */             } else {
/* 185 */               this.actionTaken = this.rsBundle.getString("NO_CHANGE_FOUND");
/*     */             }
/*     */           
/*     */           } 
/* 189 */         } catch (DARuleException dARuleException) {
/* 190 */           String str7 = dARuleException.getMessage();
/* 191 */           addError(str7);
/*     */         }
/*     */       
/*     */       } 
/* 195 */     } catch (Throwable throwable) {
/* 196 */       addXMLGenMsg("Failed Found Exception");
/* 197 */       StringWriter stringWriter = new StringWriter();
/* 198 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 199 */       String str6 = "<pre>{0}</pre>";
/* 200 */       MessageFormat messageFormat1 = new MessageFormat(str5);
/* 201 */       setReturnCode(-3);
/* 202 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 204 */       this.args[0] = throwable.getMessage();
/* 205 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 206 */       messageFormat1 = new MessageFormat(str6);
/* 207 */       this.args[0] = stringWriter.getBuffer().toString();
/* 208 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 209 */       logError("Exception: " + throwable.getMessage());
/* 210 */       logError(stringWriter.getBuffer().toString());
/*     */     } finally {
/* 212 */       if (this.m_elist != null)
/* 213 */         this.m_elist.dereference(); 
/* 214 */       setDGTitle(this.navName);
/* 215 */       setDGRptName(getShortClassName(getClass()));
/* 216 */       setDGRptClass(getABRCode());
/*     */       
/* 218 */       if (!isReadOnly()) {
/* 219 */         clearSoftLock();
/*     */       }
/* 221 */       closePrintWriters();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 226 */     MessageFormat messageFormat = new MessageFormat(str1);
/* 227 */     this.args[0] = getShortClassName(getClass());
/* 228 */     this.args[1] = this.navName;
/* 229 */     String str2 = messageFormat.format(this.args);
/*     */     
/* 231 */     String str3 = null;
/* 232 */     str3 = buildDQTriggeredRptHeader();
/*     */     
/* 234 */     String str4 = str2 + str3 + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/* 235 */     this.rptSb.insert(0, str4);
/*     */     
/* 237 */     println(this.rptSb.toString());
/* 238 */     printDGSubmitString();
/* 239 */     println(EACustom.getTOUDiv());
/* 240 */     buildReportFooter();
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
/*     */   private boolean verifyMODELWithdrawEffe(EntityItem paramEntityItem) throws IOException {
/* 256 */     boolean bool = false;
/* 257 */     if ("WWSEO".equals(paramEntityItem.getEntityType())) {
/* 258 */       Vector<EntityItem> vector = paramEntityItem.getUpLink();
/* 259 */       for (byte b = 0; b < vector.size(); b++) {
/* 260 */         EntityItem entityItem = vector.elementAt(b);
/* 261 */         if (entityItem != null && "MODELWWSEO".equals(entityItem.getEntityType())) {
/* 262 */           EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/* 263 */           if (entityItem1 != null && "MODEL".equals(entityItem1.getEntityType())) {
/* 264 */             String str = (String)WTHDRWEFFCTVDATE_Attr_TBL.get(entityItem1.getEntityType());
/* 265 */             if (str != null) {
/* 266 */               EANMetaAttribute eANMetaAttribute = entityItem1.getEntityGroup().getMetaAttribute(str);
/* 267 */               if (eANMetaAttribute == null) {
/* 268 */                 addOutput("The Global Withdrawal Date Effective:" + str + " not found in Meta data.");
/* 269 */                 return false;
/*     */               } 
/* 271 */               String str1 = PokUtils.getAttributeValue(entityItem1, str, "", "@@", false);
/* 272 */               addDebug("The value of Global Withdrawal Date Effective : " + str1);
/* 273 */               if ("@@".equals(str1)) {
/* 274 */                 addDebug("there is no value for WTHDRWEDDCTVDATE, return ture.");
/* 275 */                 return true;
/*     */               } 
/* 277 */               String str2 = this.m_prof.getNow().substring(0, 10);
/* 278 */               if (str2.compareTo(str1) <= 0) {
/* 279 */                 bool = true; break;
/*     */               } 
/* 281 */               bool = false;
/* 282 */               addError(this.rsBundle.getString("Expire_Withdraw_Effective"));
/*     */               
/*     */               break;
/*     */             } 
/* 286 */             addError(this.rsBundle.getString("NOT_FOUND_Withdraw_Effective_ATTR"));
/* 287 */             addDebug("There is no attribute code specified for Global Withdrawal Date Effective. " + paramEntityItem
/* 288 */                 .getKey());
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 297 */       String str = (String)WTHDRWEFFCTVDATE_Attr_TBL.get(paramEntityItem.getEntityType());
/* 298 */       if (str != null) {
/* 299 */         String str1 = PokUtils.getAttributeValue(paramEntityItem, str, "", "@@", false);
/* 300 */         addDebug("The value of Global Withdrawal Date Effective : " + str1);
/* 301 */         EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str);
/* 302 */         if (eANMetaAttribute == null) {
/* 303 */           addOutput("The Global Withdrawal Date Effective:" + str + " not found in Meta data.");
/* 304 */           return false;
/*     */         } 
/* 306 */         if ("@@".equals(str1)) {
/* 307 */           addDebug("there is no value for WTHDRWEDDCTVDATE, return ture.");
/* 308 */           return true;
/*     */         } 
/* 310 */         String str2 = this.m_prof.getNow().substring(0, 10);
/* 311 */         if (str2.compareTo(str1) <= 0) {
/* 312 */           bool = true;
/*     */         } else {
/* 314 */           bool = false;
/* 315 */           addError(this.rsBundle.getString("Expire_Withdraw_Effective"));
/*     */         } 
/*     */       } else {
/*     */         
/* 319 */         addError(this.rsBundle.getString("NOT_FOUND_Withdraw_Effective_ATTR"));
/* 320 */         addDebug("There is no attribute code specified for Global Withdrawal Date Effective. " + paramEntityItem
/* 321 */             .getKey());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 327 */     return bool;
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
/*     */   private boolean isValidQueueItem(EntityItem paramEntityItem) {
/* 342 */     String str1 = getAttributeFlagEnabledValue(paramEntityItem, "STATUS");
/* 343 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, "LIFECYCLE");
/* 344 */     if (str1 == null) {
/* 345 */       addOutput("Entity attribute Status is null, do not queue ADSABRSTATUS");
/* 346 */       return false;
/*     */     } 
/* 348 */     if ("0020".equals(str1)) {
/* 349 */       addOutput("Entity attribute Status is final, Queue ADSABRSTATUS");
/* 350 */       return true;
/*     */     } 
/* 352 */     if (str2 == null) {
/* 353 */       addOutput("Entity attribute LifeCycle is null, do not queue ADSABRSTATUS");
/* 354 */       return false;
/*     */     } 
/* 356 */     if ("0040".equals(str1) && "LF03".equals(str2)) {
/* 357 */       return false;
/*     */     }
/* 359 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getValidLSEO(EntityItem paramEntityItem) {
/* 367 */     Vector<EntityItem> vector1 = new Vector();
/* 368 */     Vector<EntityItem> vector2 = paramEntityItem.getDownLink();
/* 369 */     for (byte b = 0; b < vector2.size(); b++) {
/* 370 */       EntityItem entityItem = vector2.elementAt(b);
/* 371 */       if (entityItem != null && "WWSEOLSEO".equals(entityItem.getEntityType())) {
/* 372 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 373 */         if (entityItem1 != null && "LSEO".equals(entityItem1.getEntityType())) {
/* 374 */           String str = getAttributeFlagEnabledValue(entityItem1, "STATUS");
/* 375 */           if ("0020".equals(str) || "0040".equals(str)) {
/* 376 */             vector1.add(entityItem1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 381 */     return vector1;
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
/*     */   protected void setFlagValue(Profile paramProfile, String paramString1, String paramString2, EntityItem paramEntityItem) throws MiddlewareBusinessRuleException, SQLException, MiddlewareException {
/* 396 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 397 */     addDebug("setFlagValue entered " + paramEntityItem.getKey() + " for " + paramString1 + " set to: " + paramString2);
/*     */     
/* 399 */     if (paramString2 != null && paramString2.trim().length() == 0) {
/* 400 */       addDebug("setFlagValue: " + paramString1 + " was blank for " + paramEntityItem.getKey() + ", it will be ignored");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 405 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 406 */     if (eANMetaAttribute == null) {
/* 407 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 408 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 413 */     if (paramString2 != null) {
/*     */ 
/*     */       
/* 416 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/* 417 */       if (paramString2.equals(str)) {
/* 418 */         addDebug("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*     */         
/* 420 */         logMessage("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 426 */       if (eANMetaAttribute.getAttributeType().equals("A"))
/*     */       {
/* 428 */         checkForInProcess(paramProfile, paramEntityItem, paramString1);
/*     */       }
/*     */       
/* 431 */       if (this.m_cbOn == null) {
/* 432 */         setControlBlock();
/*     */       }
/* 434 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 435 */       SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*     */       
/* 437 */       Vector<SingleFlag> vector = new Vector();
/* 438 */       Vector<ReturnEntityKey> vector1 = new Vector();
/* 439 */       vector.addElement(singleFlag);
/* 440 */       returnEntityKey.m_vctAttributes = vector;
/* 441 */       vector1.addElement(returnEntityKey);
/* 442 */       this.m_db.update(this.m_prof, vector1, false, false);
/* 443 */       this.m_db.commit();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkForInProcess(Profile paramProfile, EntityItem paramEntityItem, String paramString) {
/*     */     try {
/* 450 */       byte b = 0;
/*     */       
/* 452 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*     */       
/* 454 */       addDebug("checkForInProcess:  entered " + paramEntityItem.getKey() + " " + paramString + " is " + str);
/*     */       
/* 456 */       if ("0050".equals(str)) {
/* 457 */         DatePackage datePackage = this.m_db.getDates();
/*     */         
/* 459 */         paramProfile.setValOnEffOn(datePackage.getEndOfDay(), datePackage.getEndOfDay());
/*     */         
/* 461 */         while ("0050".equals(str) && b < 20) {
/* 462 */           b++;
/* 463 */           addDebug("checkForInProcess: " + paramString + " is " + str + " sleeping 30 secs");
/* 464 */           Thread.sleep(30000L);
/*     */           
/* 466 */           EntityGroup entityGroup = new EntityGroup(null, this.m_db, paramProfile, paramEntityItem.getEntityType(), "Edit", false);
/* 467 */           EntityItem entityItem = new EntityItem(entityGroup, paramProfile, this.m_db, paramEntityItem.getEntityType(), paramEntityItem.getEntityID());
/* 468 */           str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/* 469 */           addDebug("checkForInProcess: " + paramString + " is now " + str + " after sleeping");
/*     */         } 
/*     */       } 
/* 472 */     } catch (Exception exception) {
/* 473 */       System.err.println("Exception in checkForInProcess " + exception);
/* 474 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getQueuedValue(String paramString) {
/* 485 */     return ABRServerProperties.getABRQueuedValue(this.m_abri.getABRCode() + "_" + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addXMLGenMsg(String paramString) {
/* 492 */     this.xmlgenSb.append(paramString + "<br />");
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
/*     */   private String buildDQTriggeredRptHeader() {
/* 509 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 517 */     MessageFormat messageFormat = new MessageFormat(str);
/* 518 */     this.args[0] = this.m_prof.getOPName();
/* 519 */     this.args[1] = this.m_prof.getRoleDescription();
/* 520 */     this.args[2] = this.m_prof.getWGName();
/* 521 */     this.args[3] = this.m_prof.getNow();
/* 522 */     this.args[4] = this.rootDesc + ": " + this.navName;
/* 523 */     this.args[5] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/* 524 */     return messageFormat.format(this.args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 533 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 535 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 536 */     EANList eANList = entityGroup.getMetaAttribute();
/* 537 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 539 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 540 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 541 */       stringBuffer.append(" ");
/*     */     } 
/*     */     
/* 544 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private void closePrintWriters() {
/* 548 */     if (this.dbgPw != null) {
/* 549 */       this.dbgPw.flush();
/* 550 */       this.dbgPw.close();
/* 551 */       this.dbgPw = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Database getDB() {
/* 559 */     return this.m_db;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABRAttrCode() {
/* 566 */     return this.m_abri.getABRCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 573 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 580 */     if (this.dbgPw != null) {
/* 581 */       this.dbgLen += paramString.length();
/* 582 */       this.dbgPw.println(paramString);
/* 583 */       this.dbgPw.flush();
/*     */     } else {
/* 585 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 593 */     addOutput(paramString);
/* 594 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 604 */     return "1.12";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 613 */     return "CATADATAABRSTATUS";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\CATDATAABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */