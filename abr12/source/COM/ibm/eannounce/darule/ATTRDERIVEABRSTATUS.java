/*      */ package COM.ibm.eannounce.darule;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.DeleteActionItem;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Hashtable;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
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
/*      */ public class ATTRDERIVEABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*  121 */   private StringBuffer rptSb = new StringBuffer();
/*      */   
/*  123 */   private StringBuffer xmlgenSb = new StringBuffer();
/*      */   
/*  125 */   private StringBuffer debugSb = new StringBuffer();
/*      */   
/*  127 */   private String deleteActionName = null;
/*      */   
/*  129 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*      */   
/*  131 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*  133 */   private PrintWriter dbgPw = null;
/*      */   
/*  135 */   private String dbgfn = null;
/*      */   
/*  137 */   private int dbgLen = 0;
/*      */   
/*  139 */   private Vector vctReturnsEntityKeys = new Vector();
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
/*  175 */   private EntityItem[] daRules = null;
/*      */   
/*  177 */   private ResourceBundle rsBundle = null;
/*      */   
/*  179 */   private String actionTaken = "";
/*      */   
/*  181 */   private String navName = "";
/*      */   
/*  183 */   private String rootDesc = "";
/*      */   
/*  185 */   private Object[] args = (Object[])new String[10];
/*      */ 
/*      */   
/*  188 */   protected static final Hashtable ITEM_VE_TBL = new Hashtable<>(); static {
/*  189 */     ITEM_VE_TBL.put("WWSEO", "DAVEWWSEO");
/*  190 */     ITEM_VE_TBL.put("FEATURE", "DAVEFEATURE");
/*  191 */     ITEM_VE_TBL.put("MODEL", "DAVEMODEL");
/*  192 */     ITEM_VE_TBL.put("SVCMOD", "DAVESVCMOD");
/*  193 */     ITEM_VE_TBL.put("LSEOBUNDLE", "DAVELSEOBUNDLE");
/*  194 */     ITEM_VE_TBL.put("LSEO", "DAVELSEO");
/*      */   }
/*      */ 
/*      */   
/*  198 */   protected static final Hashtable WTHDRWEFFCTVDATE_Attr_TBL = new Hashtable<>(); static {
/*  199 */     WTHDRWEFFCTVDATE_Attr_TBL.put("FEATURE", "WITHDRAWDATEEFF_T");
/*  200 */     WTHDRWEFFCTVDATE_Attr_TBL.put("LSEO", "LSEOUNPUBDATEMTRGT");
/*  201 */     WTHDRWEFFCTVDATE_Attr_TBL.put("LSEOBUNDLE", "BUNDLUNPUBDATEMTRGT");
/*  202 */     WTHDRWEFFCTVDATE_Attr_TBL.put("MODEL", "WTHDRWEFFCTVDATE");
/*  203 */     WTHDRWEFFCTVDATE_Attr_TBL.put("SVCMOD", "WTHDRWEFFCTVDATE");
/*  204 */     WTHDRWEFFCTVDATE_Attr_TBL.put("SWFEATURE", "WITHDRAWDATEEFF_T");
/*  205 */     WTHDRWEFFCTVDATE_Attr_TBL.put("WWSEO", "WTHDRWEFFCTVDATE");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  210 */   private static final Hashtable CATREL_TBL = new Hashtable<>(); static {
/*  211 */     CATREL_TBL.put("FEATURE", "FEATURECATDATA");
/*  212 */     CATREL_TBL.put("SWFEATURE", "SWFEATURECATDATA");
/*  213 */     CATREL_TBL.put("MODEL", "MODELCATDATA");
/*  214 */     CATREL_TBL.put("WWSEO", "WWSEOCATDATA");
/*  215 */     CATREL_TBL.put("SVCMOD", "SVCMODCATDATA");
/*  216 */     CATREL_TBL.put("LSEO", "LSEOCATDATA");
/*  217 */     CATREL_TBL.put("LSEOBUNDLE", "LSEOBUNDLECATDATA");
/*      */   }
/*      */   private static int MW_VENTITY_LIMIT; private static int RUN_LIMIT; private static final int MAXFILE_SIZE = 5000000; protected static final String ATTRDERIVEABRSTATUS_Passed = "0030"; protected static final String ATTRDERIVEABRSTATUS_Failed = "0040"; protected static final String ATTRDERIVE_ATTR = "ATTRDERIVEABRSTATUS";
/*      */   protected static final String STATUS_FINAL = "0020";
/*      */   protected static final String STATUS_R4REVIEW = "0040";
/*      */   protected static final String DALIFECYCLE_Ready = "20";
/*  223 */   private static final Hashtable CATACTION_TBL = new Hashtable<>(); protected static final String DALIFECYCLE_Retire = "60"; protected static final String DALIFECYCLE_Production = "30"; protected static final String DALIFECYCLE_Change = "50"; protected static final String DALIFECYCLE_Obsolete = "40"; static {
/*  224 */     CATACTION_TBL.put("FEATURE", "DELCATDATA");
/*  225 */     CATACTION_TBL.put("SWFEATURE", "DELCATDATA");
/*  226 */     CATACTION_TBL.put("MODEL", "DELCATDATA");
/*  227 */     CATACTION_TBL.put("WWSEO", "DELCATDATA");
/*  228 */     CATACTION_TBL.put("SVCMOD", "DELCATDATA");
/*  229 */     CATACTION_TBL.put("LSEO", "DELCATDATA");
/*  230 */     CATACTION_TBL.put("LSEOBUNDLE", "DELCATDATA");
/*      */   } private static final String ADS_XMLEED_ATTR = "ADSABRSTATUS"; private static final String DARULE_LIFECYCLE__ATTR = "DALIFECYCLE"; protected static final String LIFECYCLE_Develop = "LF02"; protected static final String LIFECYCLE_Plan = "LF01"; protected static final String LIFECYCLE_Launch = "LF03";
/*      */   private void setupPrintWriter() {
/*  233 */     String str = this.m_abri.getFileName();
/*  234 */     int i = str.lastIndexOf(".");
/*  235 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*      */     try {
/*  237 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  238 */     } catch (Exception exception) {
/*  239 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
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
/*      */   public void execute_run() {
/*  253 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */     
/*  256 */     String str2 = "";
/*  257 */     String str3 = "";
/*  258 */     EntityItem entityItem = null;
/*  259 */     println(EACustom.getDocTypeHtml());
/*  260 */     EntityList entityList = null;
/*      */     try {
/*  262 */       start_ABRBuild(false);
/*  263 */       getPropertiesLimitValues();
/*      */       
/*  265 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  267 */       setupPrintWriter();
/*  268 */       entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  270 */               getEntityType(), getEntityID()) });
/*  271 */       entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*  272 */       this.rootDesc = entityList.getParentEntityGroup().getLongDescription();
/*      */       
/*  274 */       this.navName = getNavigationName(entityItem);
/*  275 */       setReturnCode(0);
/*  276 */       String str7 = PokUtils.getAttributeValue(entityItem, "DAENTITYTYPE", "", null, false);
/*  277 */       String str8 = getAttributeFlagEnabledValue(entityItem, "DAATTRIBUTECODE");
/*  278 */       str3 = getAttributeFlagEnabledValue(entityItem, "DALIFECYCLE");
/*  279 */       this.daRules = DARuleEngine.searchForDARules(this.m_db, this.m_prof, str7, str8, this.rptSb);
/*  280 */       String str9 = (String)ITEM_VE_TBL.get(str7);
/*  281 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " DALifeCycle:" + str3 + " DAattrCode:" + str8 + " extract: " + str9 + " using DTS: " + this.m_prof
/*  282 */           .getValOn() + NEWLINE + 
/*  283 */           PokUtils.outputList(entityList));
/*      */       
/*  285 */       str2 = entityItem.getKey();
/*  286 */       if (str9 != null) {
/*      */         try {
/*  288 */           if ("20".equals(str3)) {
/*  289 */             addGenMsg("DALIFECYCLE_READY", str2);
/*      */             
/*  291 */             long l = System.currentTimeMillis();
/*      */             
/*  293 */             Vector<Integer> vector = getValidEntityIds(str7, entityItem);
/*  294 */             if (vector.size() > 0) {
/*  295 */               String str = getQueuedValue("ADSABRSTATUS");
/*  296 */               boolean bool = (str.trim().length() > 0) ? true : false;
/*  297 */               addOutput(bool ? ("ADSABRSTATUS set to :" + str) : "An empty value found ATTRDERIVEABRSTATUS_ADSABRSTATUS_queuedValue= in properties file,  the attribute (ADSABRSTATUS) not be updated");
/*  298 */               DARuleGroup dARuleGroup = DARuleEngineMgr.getDARuleGroup(this.m_db, this.m_prof, str7, str8, this.rptSb);
/*      */ 
/*      */               
/*  301 */               addDebug("VEENTITY_LIMIT :" + MW_VENTITY_LIMIT + " ID Count : " + vector.size() + " EntityType: " + str7 + " EntityID: " + vector);
/*  302 */               Vector<Integer> vector1 = new Vector();
/*  303 */               if (RUN_LIMIT != 0) {
/*  304 */                 for (byte b = 0; b < vector.size() && 
/*  305 */                   b != RUN_LIMIT; b++)
/*      */                 {
/*  307 */                   vector1.add(vector.elementAt(b));
/*      */                 }
/*  309 */                 addDebug("Run the first " + RUN_LIMIT + " records  EntityID: " + vector1);
/*      */               } else {
/*  311 */                 vector1 = vector;
/*      */               } 
/*  313 */               if (vector1.size() > MW_VENTITY_LIMIT) {
/*  314 */                 int i = 0;
/*  315 */                 if (vector1.size() % MW_VENTITY_LIMIT != 0) {
/*  316 */                   i = vector1.size() / MW_VENTITY_LIMIT + 1;
/*      */                 } else {
/*  318 */                   i = vector1.size() / MW_VENTITY_LIMIT;
/*      */                 } 
/*  320 */                 byte b = 0;
/*  321 */                 Vector<EntityItem> vector2 = new Vector();
/*  322 */                 Integer integer = null; int j;
/*  323 */                 for (j = 0; j < i; j++) {
/*  324 */                   for (byte b1 = 0; b1 < MW_VENTITY_LIMIT && 
/*  325 */                     b != vector1.size(); b1++) {
/*      */ 
/*      */                     
/*  328 */                     integer = vector1.elementAt(b++);
/*  329 */                     EntityItem entityItem1 = new EntityItem(null, this.m_prof, str7, integer.intValue());
/*  330 */                     vector2.add(entityItem1);
/*      */                   } 
/*  332 */                   EntityItem[] arrayOfEntityItem1 = new EntityItem[vector2.size()];
/*  333 */                   vector2.copyInto((Object[])arrayOfEntityItem1);
/*  334 */                   EntityList entityList1 = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str9), arrayOfEntityItem1);
/*      */                   
/*  336 */                   addDebug("Time to instance EntityList LIMIT: " + MW_VENTITY_LIMIT + " EntityType: " + str7 + ": " + 
/*  337 */                       Stopwatch.format(System.currentTimeMillis() - l) + NEWLINE + PokUtils.outputList(entityList1));
/*  338 */                   l = System.currentTimeMillis();
/*      */                   
/*  340 */                   EntityGroup entityGroup = entityList1.getParentEntityGroup();
/*  341 */                   addDebug("count number: " + entityGroup.getEntityItemCount());
/*  342 */                   EntityItem[] arrayOfEntityItem2 = entityGroup.getEntityItemsAsArray();
/*  343 */                   addDebug("Begin to run idlcatData, the itemArrary size: " + arrayOfEntityItem2.length);
/*  344 */                   if (arrayOfEntityItem2.length > 0) {
/*  345 */                     boolean[] arrayOfBoolean = dARuleGroup.idlCatData(this.m_db, this.m_prof, arrayOfEntityItem2, this.rptSb, this.debugSb);
/*  346 */                     addDebug("Time to call idlCatData itemArray : " + arrayOfEntityItem2.length + " EntityType: " + str7 + ": " + 
/*  347 */                         Stopwatch.format(System.currentTimeMillis() - l));
/*  348 */                     l = System.currentTimeMillis();
/*  349 */                     if (bool) {
/*  350 */                       if (arrayOfBoolean.length != arrayOfEntityItem2.length) {
/*  351 */                         addOutput("The length of boolean resulte array is not equal to input Entity array length! ");
/*      */                       }
/*  353 */                       for (byte b2 = 0; b2 < arrayOfBoolean.length; b2++) {
/*  354 */                         if (arrayOfBoolean[b2]) {
/*  355 */                           EntityItem entityItem1 = arrayOfEntityItem2[b2];
/*  356 */                           if ("WWSEO".equals(entityItem1.getEntityType())) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                             
/*  363 */                             Vector<EntityItem> vector3 = getValidLSEO(entityItem1);
/*  364 */                             for (byte b3 = 0; b3 < vector3.size(); b3++) {
/*  365 */                               EntityItem entityItem2 = vector3.elementAt(b3);
/*  366 */                               if (entityItem2 != null && isValidQueueItem(entityItem2)) {
/*  367 */                                 setValues(str, entityItem2.getEntityType(), entityItem2.getEntityID());
/*  368 */                                 addOutput("Set ADSABRSTATUS of " + entityItem2.getKey() + " to Queue.");
/*      */                               } else {
/*  370 */                                 addOutput("There is no valid LSEO for " + entityItem1.getKey() + ", do not need to queue ADSABRSTATUS.");
/*      */                               } 
/*      */                             } 
/*  373 */                             vector3.clear();
/*  374 */                           } else if (isValidQueueItem(entityItem1)) {
/*  375 */                             setValues(str, entityItem1.getEntityType(), entityItem1.getEntityID());
/*  376 */                             addOutput("Set ADSABRSTATUS of " + entityItem1.getKey() + " to Queue.");
/*      */                           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/*  383 */                           if (this.vctReturnsEntityKeys.size() >= 500) {
/*  384 */                             long l1 = System.currentTimeMillis();
/*  385 */                             int k = this.vctReturnsEntityKeys.size();
/*      */                             
/*  387 */                             updatePDH();
/*      */                             
/*  389 */                             addDebug("Time to update " + k + " " + str7 + ": " + 
/*  390 */                                 Stopwatch.format(System.currentTimeMillis() - l1));
/*  391 */                             l = System.currentTimeMillis();
/*      */                           } 
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*  397 */                   vector2.clear();
/*  398 */                   arrayOfEntityItem1 = null;
/*  399 */                   arrayOfEntityItem2 = null;
/*  400 */                   entityList1.dereference();
/*      */                 } 
/*      */                 
/*  403 */                 if (this.vctReturnsEntityKeys.size() > 0) {
/*  404 */                   j = this.vctReturnsEntityKeys.size();
/*      */                   
/*  406 */                   updatePDH();
/*  407 */                   addDebug("Time to update " + j + " " + str7 + ": " + 
/*  408 */                       Stopwatch.format(System.currentTimeMillis() - l));
/*      */                 } 
/*  410 */                 updateDALifeCycle(this.daRules, "30");
/*  411 */                 dARuleGroup.dereference();
/*  412 */                 vector1.clear();
/*  413 */                 addGenMsg("SUCCESS_READY", str2);
/*      */               } else {
/*      */                 
/*  416 */                 EntityItem[] arrayOfEntityItem1 = new EntityItem[vector1.size()];
/*  417 */                 Integer integer = null;
/*  418 */                 for (byte b = 0; b < vector1.size(); b++) {
/*  419 */                   integer = vector1.elementAt(b);
/*  420 */                   arrayOfEntityItem1[b] = new EntityItem(null, this.m_prof, str7, integer.intValue());
/*      */                 } 
/*  422 */                 EntityList entityList1 = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str9), arrayOfEntityItem1);
/*      */                 
/*  424 */                 EntityGroup entityGroup = entityList1.getParentEntityGroup();
/*  425 */                 EntityItem[] arrayOfEntityItem2 = entityGroup.getEntityItemsAsArray();
/*  426 */                 addDebug("Begin to run idlcatData, the itemArrary size: " + arrayOfEntityItem2.length + NEWLINE + PokUtils.outputList(entityList1));
/*  427 */                 if (arrayOfEntityItem2.length > 0) {
/*  428 */                   boolean[] arrayOfBoolean = dARuleGroup.idlCatData(this.m_db, this.m_prof, arrayOfEntityItem2, this.rptSb, this.debugSb);
/*  429 */                   if (bool) {
/*  430 */                     if (arrayOfBoolean.length != arrayOfEntityItem2.length) {
/*  431 */                       addOutput("The length of boolean resulte array is not equal to input Entity array length! ");
/*      */                     }
/*  433 */                     for (byte b1 = 0; b1 < arrayOfBoolean.length; b1++) {
/*  434 */                       if (arrayOfBoolean[b1]) {
/*  435 */                         EntityItem entityItem1 = arrayOfEntityItem2[b1];
/*  436 */                         if ("WWSEO".equals(entityItem1.getEntityType())) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/*  443 */                           Vector<EntityItem> vector2 = getValidLSEO(entityItem1);
/*  444 */                           for (byte b2 = 0; b2 < vector2.size(); b2++) {
/*  445 */                             EntityItem entityItem2 = vector2.elementAt(b2);
/*  446 */                             if (entityItem2 != null && isValidQueueItem(entityItem2)) {
/*  447 */                               setValues(str, entityItem2.getEntityType(), entityItem2.getEntityID());
/*  448 */                               addOutput("Set ADSABRSTATUS of " + entityItem2.getKey() + " to Queue.");
/*      */                             } else {
/*  450 */                               addOutput("There is no valid LSEO for " + entityItem1.getKey() + ", do not need to queue ADSABRSTATUS.");
/*      */                             } 
/*      */                           } 
/*  453 */                           vector2.clear();
/*  454 */                         } else if (isValidQueueItem(entityItem1)) {
/*  455 */                           setValues(str, entityItem1.getEntityType(), entityItem1.getEntityID());
/*  456 */                           addOutput("Set ADSABRSTATUS of " + entityItem1.getKey() + " to Queue.");
/*      */                         } 
/*  458 */                         if (this.vctReturnsEntityKeys.size() >= 500) {
/*  459 */                           int i = this.vctReturnsEntityKeys.size();
/*      */                           
/*  461 */                           updatePDH();
/*  462 */                           long l1 = System.currentTimeMillis();
/*  463 */                           addDebug("Time to update " + i + " " + str7 + ": " + 
/*  464 */                               Stopwatch.format(l1 - l));
/*  465 */                           l = l1;
/*      */                         } 
/*      */                         
/*  468 */                         if (this.vctReturnsEntityKeys.size() > 0) {
/*  469 */                           int i = this.vctReturnsEntityKeys.size();
/*      */                           
/*  471 */                           updatePDH();
/*  472 */                           addDebug("Time to update " + i + " " + str7 + ": " + 
/*  473 */                               Stopwatch.format(System.currentTimeMillis() - l));
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*  477 */                     arrayOfBoolean = null;
/*      */                   } 
/*      */                 } 
/*  480 */                 updateDALifeCycle(this.daRules, "30");
/*  481 */                 dARuleGroup.dereference();
/*  482 */                 entityList1.dereference();
/*  483 */                 arrayOfEntityItem1 = null;
/*  484 */                 arrayOfEntityItem2 = null;
/*  485 */                 vector.clear();
/*  486 */                 vector1.clear();
/*  487 */                 addGenMsg("SUCCESS_READY", str2);
/*      */               }
/*      */             
/*      */             } 
/*  491 */           } else if ("60".equals(str3)) {
/*  492 */             addGenMsg("DALIFECYCLE_RETIRE", str2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  500 */             this.deleteActionName = CATACTION_TBL.get(str7).toString();
/*  501 */             long l = System.currentTimeMillis();
/*      */             
/*  503 */             if (this.daRules != null) {
/*      */               
/*  505 */               verifyDARules(this.daRules, str2);
/*      */               
/*  507 */               Vector<String> vector = new Vector();
/*  508 */               String[] arrayOfString = PokUtils.convertToArray(PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN"));
/*  509 */               for (byte b = 0; b < arrayOfString.length; b++) {
/*  510 */                 vector.add(arrayOfString[b]);
/*      */               }
/*  512 */               String str = getCATDATASql(str8, str7, vector);
/*  513 */               Vector<Integer> vector1 = getMatchingDateIds(str);
/*  514 */               if (vector1.size() > 0) {
/*  515 */                 addDebug("VEENTITY_LIMIT :" + MW_VENTITY_LIMIT + " CATDATA ID Count : " + vector1.size());
/*      */ 
/*      */                 
/*  518 */                 if (vector1.size() > MW_VENTITY_LIMIT) {
/*  519 */                   int i = 0;
/*  520 */                   if (vector1.size() % MW_VENTITY_LIMIT != 0) {
/*  521 */                     i = vector1.size() / MW_VENTITY_LIMIT + 1;
/*      */                   } else {
/*  523 */                     i = vector1.size() / MW_VENTITY_LIMIT;
/*      */                   } 
/*  525 */                   Vector<EntityItem> vector2 = new Vector();
/*  526 */                   byte b1 = 0;
/*  527 */                   Integer integer = null;
/*  528 */                   for (byte b2 = 0; b2 < i; b2++) {
/*  529 */                     for (byte b3 = 0; b3 < MW_VENTITY_LIMIT && 
/*  530 */                       b1 != vector1.size(); b3++) {
/*      */ 
/*      */                       
/*  533 */                       integer = vector1.elementAt(b1++);
/*  534 */                       EntityItem entityItem1 = new EntityItem(null, this.m_prof, "CATDATA", integer.intValue());
/*  535 */                       vector2.add(entityItem1);
/*      */                     } 
/*  537 */                     EntityItem[] arrayOfEntityItem = new EntityItem[vector2.size()];
/*  538 */                     vector2.copyInto((Object[])arrayOfEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  545 */                     for (byte b4 = 0; b4 < arrayOfEntityItem.length; b4++) {
/*  546 */                       addOutput("CATADATA get from EntityGroup CATDATA[" + b4 + " ]" + arrayOfEntityItem[b4].getEntityType() + arrayOfEntityItem[b4].getEntityID() + NEWLINE);
/*      */                     }
/*  548 */                     deleteCATDATAs(arrayOfEntityItem);
/*  549 */                     addDebug("Time to delete CATDATAs LIMIT: " + MW_VENTITY_LIMIT + " EntityType: CATDATA " + 
/*  550 */                         Stopwatch.format(System.currentTimeMillis() - l));
/*  551 */                     l = System.currentTimeMillis();
/*      */                     
/*  553 */                     arrayOfEntityItem = null;
/*  554 */                     vector2.clear();
/*      */                   }
/*      */                 
/*      */                 }
/*      */                 else {
/*      */                   
/*  560 */                   EntityItem[] arrayOfEntityItem = new EntityItem[vector1.size()];
/*  561 */                   addDebug("CATDATA id vector are:" + vector1);
/*  562 */                   Integer integer = null;
/*  563 */                   for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  564 */                     integer = vector1.elementAt(b1);
/*  565 */                     arrayOfEntityItem[b1] = new EntityItem(null, this.m_prof, "CATDATA", integer.intValue());
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  571 */                   deleteCATDATAs(arrayOfEntityItem);
/*      */                   
/*  573 */                   arrayOfEntityItem = null;
/*      */                 } 
/*      */               } 
/*  576 */               addDebug("Time to retire count " + vector1.size() + str7 + " attr " + str8 + ": " + 
/*  577 */                   Stopwatch.format(System.currentTimeMillis() - l));
/*  578 */               DARuleEngineMgr.clearEntityType(this.m_db, this.m_prof, str7, this.rptSb);
/*      */               
/*  580 */               updateDALifeCycle(this.daRules, "40");
/*  581 */               addGenMsg("SUCCESS_RETIRE", str2);
/*  582 */               vector.clear();
/*  583 */               vector1.clear();
/*      */             } 
/*      */           } else {
/*  586 */             addGenMsg("FAILED_DACYCLE", str2);
/*      */             
/*  588 */             addError("Error: " + this.rsBundle.getString("ONLY_RUNING_READY_RETIRE"));
/*      */           } 
/*  590 */         } catch (InvalidDARuleException invalidDARuleException) {
/*  591 */           if ("20".equals(str3)) {
/*  592 */             Vector<EntityItem> vector = invalidDARuleException.getEntityItems();
/*  593 */             EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/*  594 */             vector.copyInto((Object[])arrayOfEntityItem);
/*  595 */             updateDALifeCycle(arrayOfEntityItem, "50");
/*  596 */             for (byte b = 0; b < vector.size(); b++) {
/*  597 */               EntityItem entityItem1 = vector.elementAt(b);
/*  598 */               addGenMsg("FOUND_PRODUCTION_DALIFECYCLE", entityItem1.getKey());
/*      */             } 
/*  600 */           } else if ("60".equals(str3)) {
/*  601 */             Vector<EntityItem> vector = invalidDARuleException.getEntityItems();
/*      */ 
/*      */ 
/*      */             
/*  605 */             for (byte b = 0; b < vector.size(); b++) {
/*  606 */               EntityItem entityItem1 = vector.elementAt(b);
/*  607 */               addGenMsg("FOUND_RETIRE_DALIFECYCLE", entityItem1.getKey());
/*      */             } 
/*      */           } 
/*  610 */           addError("Error: " + invalidDARuleException.getMessage());
/*  611 */         } catch (DARuleException dARuleException) {
/*  612 */           String str = dARuleException.getMessage();
/*  613 */           addError("Error: " + str);
/*  614 */         } catch (Exception exception) {
/*  615 */           setReturnCode(-3);
/*  616 */           throw exception;
/*      */         } 
/*      */       } else {
/*      */         
/*  620 */         addOutput("ATTRDERIVEABR not support this Entity: " + str7);
/*      */       } 
/*  622 */     } catch (Throwable throwable) {
/*  623 */       addGenMsg("Failed", str2);
/*  624 */       StringWriter stringWriter = new StringWriter();
/*  625 */       String str7 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  626 */       String str8 = "<pre>{0}</pre>";
/*  627 */       MessageFormat messageFormat1 = new MessageFormat(str7);
/*  628 */       setReturnCode(-3);
/*  629 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  631 */       this.args[0] = throwable.getMessage();
/*  632 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  633 */       messageFormat1 = new MessageFormat(str8);
/*  634 */       this.args[0] = stringWriter.getBuffer().toString();
/*  635 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  636 */       logError("Exception: " + throwable.getMessage());
/*  637 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*      */       
/*  640 */       if (this.daRules != null && entityItem != null) {
/*      */         try {
/*  642 */           if (0 != getReturnCode()) {
/*  643 */             if ("60".equals(str3)) {
/*      */               
/*  645 */               addGenMsg("SET_BACK_DACYCLE_PRODUCTION", str2);
/*  646 */               updateDALifeCycle(this.daRules, "30");
/*      */             }
/*  648 */             else if ("20".equals(str3)) {
/*  649 */               addGenMsg("SET_BACK_DACYCLE_CHANGE", str2);
/*  650 */               updateDALifeCycle(new EntityItem[] { entityItem }, "50");
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  656 */           addDebug("Set other DARULES consistent with the results");
/*      */           
/*  658 */           for (byte b = 0; b < this.daRules.length; b++) {
/*  659 */             if (!this.daRules[b].getKey().equals(entityItem.getKey())) {
/*  660 */               if (0 == getReturnCode()) {
/*  661 */                 setFlagValue("ATTRDERIVEABRSTATUS", "0030", this.daRules[b]);
/*      */               } else {
/*  663 */                 setFlagValue("ATTRDERIVEABRSTATUS", "0040", this.daRules[b]);
/*      */               }
/*      */             
/*      */             }
/*      */           } 
/*  668 */         } catch (Exception exception) {
/*  669 */           addDebug("ATTRDERIVEABRSTATUS, unable to update DALIFECYCLE. " + exception);
/*      */         } 
/*      */       }
/*  672 */       if (entityList != null)
/*  673 */         entityList.dereference(); 
/*  674 */       this.daRules = null;
/*  675 */       setDGTitle(this.navName);
/*  676 */       setDGRptName(getShortClassName(getClass()));
/*  677 */       setDGRptClass(getABRCode());
/*      */       
/*  679 */       if (!isReadOnly()) {
/*  680 */         clearSoftLock();
/*      */       }
/*  682 */       closePrintWriters();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  687 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  688 */     this.args[0] = getShortClassName(getClass());
/*  689 */     this.args[1] = this.navName;
/*  690 */     String str4 = messageFormat.format(this.args);
/*      */     
/*  692 */     String str5 = null;
/*  693 */     str5 = buildDQTriggeredRptHeader();
/*  694 */     restoreXtraContent();
/*      */     
/*  696 */     String str6 = str4 + str5 + "<pre><br />" + this.rptSb.toString() + "</pre>" + NEWLINE;
/*  697 */     this.debugSb.insert(0, str6);
/*      */     
/*  699 */     println(this.debugSb.toString());
/*  700 */     printDGSubmitString();
/*  701 */     println(EACustom.getTOUDiv());
/*  702 */     buildReportFooter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getCATDATASql(String paramString1, String paramString2, Vector<E> paramVector) {
/*  713 */     StringBuffer stringBuffer1 = new StringBuffer();
/*  714 */     for (byte b = 0; b < paramVector.size(); b++) {
/*  715 */       if (stringBuffer1.length() > 0) {
/*  716 */         stringBuffer1.append(',');
/*      */       }
/*  718 */       stringBuffer1.append("'" + paramVector.elementAt(b).toString() + "'");
/*      */     } 
/*  720 */     StringBuffer stringBuffer2 = new StringBuffer("select distinct root.entityid from opicm.entity root ");
/*  721 */     stringBuffer2.append("join opicm.relator r1 on root.entitytype=r1.entity2type and root.entityid=r1.entity2id ");
/*  722 */     stringBuffer2.append("join opicm.flag f on r1.entity1type=f.entitytype and r1.entity1id=f.entityid ");
/*      */ 
/*      */ 
/*      */     
/*  726 */     stringBuffer2.append("join opicm.flag f2 on r1.entity2type=f2.entitytype and r1.entity2id=f2.entityid ");
/*      */ 
/*      */     
/*  729 */     stringBuffer2.append("where root.entitytype='CATDATA'");
/*  730 */     stringBuffer2.append("and root.enterprise='" + this.m_prof.getEnterprise() + "' ");
/*  731 */     stringBuffer2.append("and root.valto>current timestamp ");
/*  732 */     stringBuffer2.append("and root.effto>current timestamp ");
/*  733 */     stringBuffer2.append("and r1.entity1type='" + paramString2 + "'");
/*  734 */     stringBuffer2.append("and r1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/*  735 */     stringBuffer2.append("and r1.valto>current timestamp ");
/*  736 */     stringBuffer2.append("and r1.effto>current timestamp ");
/*  737 */     stringBuffer2.append("and f.enterprise='" + this.m_prof.getEnterprise() + "' ");
/*  738 */     stringBuffer2.append("and f.valto>current timestamp ");
/*  739 */     stringBuffer2.append("and f.effto>current timestamp ");
/*  740 */     stringBuffer2.append("and f.attributecode='PDHDOMAIN' ");
/*  741 */     stringBuffer2.append("and f.attributevalue in (" + stringBuffer1.toString() + ") ");
/*  742 */     stringBuffer2.append("and f2.enterprise='" + this.m_prof.getEnterprise() + "' ");
/*  743 */     stringBuffer2.append("and f2.valto>current timestamp ");
/*  744 */     stringBuffer2.append("and f2.effto>current timestamp ");
/*  745 */     stringBuffer2.append("and f2.attributecode='DAATTRIBUTECODE' ");
/*  746 */     stringBuffer2.append("and f2.attributevalue='" + paramString1 + "' with ur");
/*  747 */     return stringBuffer2.toString();
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
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteCATDATAs(EntityItem[] paramArrayOfEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  763 */     addDebug("deletecatdatas cnt " + paramArrayOfEntityItem.length);
/*  764 */     if (paramArrayOfEntityItem.length == 0) {
/*      */       return;
/*      */     }
/*  767 */     DeleteActionItem deleteActionItem = new DeleteActionItem(null, this.m_db, this.m_prof, this.deleteActionName);
/*      */ 
/*      */     
/*  770 */     for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/*  771 */       EntityItem entityItem = paramArrayOfEntityItem[b];
/*      */ 
/*      */       
/*  774 */       EntityGroup entityGroup = (EntityGroup)entityItem.getParent();
/*  775 */       addDebug("delete " + entityGroup + entityItem.getKey());
/*      */     } 
/*      */     
/*  778 */     long l = System.currentTimeMillis();
/*      */ 
/*      */     
/*  781 */     deleteActionItem.setEntityItems(paramArrayOfEntityItem);
/*  782 */     this.m_db.executeAction(this.m_prof, deleteActionItem);
/*  783 */     int i = paramArrayOfEntityItem.length;
/*      */     
/*  785 */     addDebug("Time to delete unmatched catdatas: " + Stopwatch.format(System.currentTimeMillis() - l) + ", total delete count: " + i);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifyDARules(EntityItem[] paramArrayOfEntityItem, String paramString) throws InvalidDARuleException {
/*  807 */     Vector<EntityItem> vector1 = null;
/*  808 */     Vector<EntityItem> vector2 = null;
/*  809 */     for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/*  810 */       EntityItem entityItem = paramArrayOfEntityItem[b];
/*  811 */       String str = getAttributeFlagEnabledValue(entityItem, "DALIFECYCLE");
/*  812 */       if ("30".equals(str)) {
/*  813 */         if (vector1 == null) {
/*  814 */           vector1 = new Vector();
/*      */         }
/*  816 */         vector1.add(entityItem);
/*      */       }
/*  818 */       else if (!paramString.equals(entityItem.getKey()) && "60".equals(str)) {
/*  819 */         if (vector2 == null) {
/*  820 */           vector2 = new Vector();
/*      */         }
/*  822 */         vector2.add(entityItem);
/*      */       } 
/*      */     } 
/*  825 */     if (vector1 != null && vector2 != null) {
/*  826 */       vector2.addAll(vector1);
/*  827 */       throw new InvalidDARuleException("For Retire, the other DARULEs have DALIFECYCLE of 'Production' and there are more than one 'Retire' are found. The ABR failed. set all DARULEs to 'Production'.", vector2);
/*      */     } 
/*  829 */     if (vector2 != null) {
/*  830 */       throw new InvalidDARuleException("For Retire, Only one DARULE in a set can be set to 'Retire'. There are more than one found, the ABR failed. set all DARULEs to 'Production'.", vector2);
/*      */     }
/*  832 */     if (vector1 != null) {
/*  833 */       throw new InvalidDARuleException("For Retire, the other DARULEs have DALIFECYCLE of 'Production' , this is an error. set All DARULEs to 'Production'.", vector1);
/*      */     }
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
/*      */ 
/*      */   
/*      */   private boolean isValidQueueItem(EntityItem paramEntityItem) {
/*  849 */     String str1 = getAttributeFlagEnabledValue(paramEntityItem, "STATUS");
/*  850 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, "LIFECYCLE");
/*  851 */     if (str1 == null) {
/*  852 */       addOutput("Entity attribute Status is null, do not queue ADSABRSTATUS");
/*  853 */       return false;
/*      */     } 
/*  855 */     if ("0020".equals(str1)) {
/*  856 */       addOutput("Entity attribute Status is final, Queue ADSABRSTATUS");
/*  857 */       return true;
/*      */     } 
/*  859 */     if (str2 == null) {
/*  860 */       addOutput("Entity attribute LifeCycle is null, do not queue ADSABRSTATUS");
/*  861 */       return false;
/*      */     } 
/*  863 */     if ("0040".equals(str1) && "LF03".equals(str2)) {
/*  864 */       return false;
/*      */     }
/*  866 */     return true;
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
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getValidLSEO(EntityItem paramEntityItem) {
/*  882 */     Vector<EntityItem> vector1 = new Vector();
/*  883 */     Vector<EntityItem> vector2 = paramEntityItem.getDownLink();
/*  884 */     for (byte b = 0; b < vector2.size(); b++) {
/*  885 */       EntityItem entityItem = vector2.elementAt(b);
/*  886 */       if (entityItem != null && "WWSEOLSEO".equals(entityItem.getEntityType())) {
/*  887 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/*  888 */         if (entityItem1 != null && "LSEO".equals(entityItem1.getEntityType())) {
/*  889 */           String str = getAttributeFlagEnabledValue(entityItem1, "STATUS");
/*  890 */           if ("0020".equals(str) || "0040".equals(str)) {
/*  891 */             vector1.add(entityItem1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  896 */     return vector1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDALifeCycle(EntityItem[] paramArrayOfEntityItem, String paramString) throws SQLException, MiddlewareException {
/*  914 */     if (paramArrayOfEntityItem != null)
/*      */     {
/*  916 */       for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/*  917 */         EntityItem entityItem = paramArrayOfEntityItem[b];
/*  918 */         String str = getAttributeFlagEnabledValue(entityItem, "DALIFECYCLE");
/*  919 */         if ("50".equals(paramString) && !"50".equals(str)) {
/*  920 */           setFlagValue("DALIFECYCLE", paramString, entityItem);
/*  921 */         } else if (!"40".equals(str)) {
/*  922 */           setFlagValue("DALIFECYCLE", paramString, entityItem);
/*      */         } 
/*      */       } 
/*      */     }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFlagValue(String paramString1, String paramString2, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  942 */     addDebug("setFlagValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */     
/*  945 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/*  946 */     if (eANMetaAttribute == null) {
/*  947 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  954 */     if (paramString2 != null) {
/*  955 */       if (paramString2.equals(getAttributeFlagEnabledValue(paramEntityItem, paramString1))) {
/*  956 */         addDebug("setFlagValue " + paramEntityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*      */       } else {
/*      */         
/*      */         try {
/*  960 */           if (this.m_cbOn == null) {
/*  961 */             setControlBlock();
/*      */           }
/*  963 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*      */           
/*  965 */           SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */           
/*  967 */           Vector<SingleFlag> vector = new Vector();
/*  968 */           Vector<ReturnEntityKey> vector1 = new Vector();
/*  969 */           vector.addElement(singleFlag);
/*  970 */           returnEntityKey.m_vctAttributes = vector;
/*  971 */           vector1.addElement(returnEntityKey);
/*      */           
/*  973 */           this.m_db.update(this.m_prof, vector1, false, false);
/*  974 */           addDebug(paramEntityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*      */         } finally {
/*  976 */           this.m_db.commit();
/*  977 */           this.m_db.freeStatement();
/*  978 */           this.m_db.isPending("finally after update in setflag value");
/*      */         } 
/*      */       } 
/*      */     }
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
/*      */   private void setValues(String paramString1, String paramString2, int paramInt) {
/*  993 */     if (this.m_cbOn == null) {
/*  994 */       setControlBlock();
/*      */     }
/*      */     
/*  997 */     ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramString2, paramInt, true);
/*  998 */     Vector<SingleFlag> vector = new Vector();
/*  999 */     returnEntityKey.m_vctAttributes = vector;
/* 1000 */     this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */ 
/*      */     
/* 1003 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramString2, paramInt, "ADSABRSTATUS", paramString1, 1, this.m_cbOn);
/*      */     
/* 1005 */     singleFlag.setDeferredPost(true);
/*      */     
/* 1007 */     vector.addElement(singleFlag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getQueuedValue(String paramString) {
/* 1017 */     return ABRServerProperties.getABRQueuedValue(this.m_abri.getABRCode() + "_" + paramString);
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
/*      */   
/*      */   private Vector getValidEntityIds(String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1031 */     String str1 = null;
/* 1032 */     Vector vector = new Vector(1);
/* 1033 */     String str2 = "STATUS";
/* 1034 */     StringBuffer stringBuffer = new StringBuffer();
/* 1035 */     stringBuffer.append("'0040'");
/* 1036 */     stringBuffer.append(',');
/* 1037 */     stringBuffer.append("'0020'");
/*      */     
/* 1039 */     Vector<String> vector1 = new Vector();
/* 1040 */     String[] arrayOfString = PokUtils.convertToArray(PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN"));
/* 1041 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1042 */       vector1.add(arrayOfString[b]);
/*      */     }
/* 1044 */     long l = System.currentTimeMillis();
/* 1045 */     String str3 = (String)WTHDRWEFFCTVDATE_Attr_TBL.get(paramString);
/* 1046 */     if (str3 != null) {
/* 1047 */       String str = this.m_prof.getNow().substring(0, 10);
/* 1048 */       if (!"WWSEO".equals(paramString)) {
/* 1049 */         str1 = getFilteredDateSql(paramString, str3, str, str2, stringBuffer, vector1);
/* 1050 */         if (str1 != null) {
/* 1051 */           vector = getMatchingDateIds(str1);
/* 1052 */           addDebug("Time to filter on dates: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */         } 
/*      */       } else {
/* 1055 */         Vector vector2 = null;
/* 1056 */         String str4 = null;
/* 1057 */         str1 = getFilteredWWSEOSql(str2, stringBuffer, vector1);
/* 1058 */         if (str1 != null) {
/* 1059 */           vector2 = getMatchingDateIds(str1);
/* 1060 */           addDebug("Time to filter on dates: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */         } 
/* 1062 */         if (vector2.size() > 0) {
/* 1063 */           str4 = getFilteredLSEOSql(str3, str, str2, stringBuffer);
/* 1064 */           if (str4 != null) {
/* 1065 */             vector = getMatchingDateIds(str4, vector2);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1071 */     return vector;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getFilteredDateSql(String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer, Vector<E> paramVector) {
/* 1090 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 1091 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1092 */       if (stringBuffer1.length() > 0) {
/* 1093 */         stringBuffer1.append(',');
/*      */       }
/* 1095 */       stringBuffer1.append("'" + paramVector.elementAt(b).toString() + "'");
/*      */     } 
/* 1097 */     StringBuffer stringBuffer2 = new StringBuffer("select mdl.entityid from opicm.entity mdl ");
/* 1098 */     stringBuffer2.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
/* 1099 */     stringBuffer2.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
/* 1100 */     stringBuffer2.append("where mdl.entitytype='" + paramString1 + "' ");
/* 1101 */     stringBuffer2.append("and mdl.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1102 */     stringBuffer2.append("and mdl.valto>current timestamp ");
/* 1103 */     stringBuffer2.append("and mdl.effto>current timestamp ");
/* 1104 */     stringBuffer2.append("and f.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1105 */     stringBuffer2.append("and f.valto>current timestamp ");
/* 1106 */     stringBuffer2.append("and f.effto>current timestamp ");
/* 1107 */     stringBuffer2.append("and f.attributecode='PDHDOMAIN' ");
/* 1108 */     stringBuffer2.append("and f.attributevalue in (" + stringBuffer1.toString() + ") ");
/* 1109 */     stringBuffer2.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1110 */     stringBuffer2.append("and f1.valto>current timestamp ");
/* 1111 */     stringBuffer2.append("and f1.effto>current timestamp ");
/* 1112 */     stringBuffer2.append("and f1.attributecode='" + paramString4 + "'");
/* 1113 */     stringBuffer2.append("and f1.attributevalue in (" + paramStringBuffer.toString() + ") ");
/* 1114 */     stringBuffer2.append("and not exists ");
/* 1115 */     stringBuffer2.append("(select t.entityid from opicm.text t where ");
/* 1116 */     stringBuffer2.append("t.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1117 */     stringBuffer2.append("and t.entitytype='" + paramString1 + "' ");
/* 1118 */     stringBuffer2.append("and t.entityid=mdl.entityid ");
/* 1119 */     stringBuffer2.append("and t.attributecode='" + paramString2 + "' ");
/* 1120 */     stringBuffer2.append("and t.valto>current timestamp ");
/* 1121 */     stringBuffer2.append("and t.effto>current timestamp) ");
/* 1122 */     stringBuffer2.append("union ");
/* 1123 */     stringBuffer2.append("select mdl.entityid from opicm.entity mdl ");
/* 1124 */     stringBuffer2.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
/* 1125 */     stringBuffer2.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
/* 1126 */     stringBuffer2.append("join opicm.text t on t.entitytype=mdl.entitytype and t.entityid=mdl.entityid ");
/* 1127 */     stringBuffer2.append("where mdl.entitytype='" + paramString1 + "' ");
/* 1128 */     stringBuffer2.append("and mdl.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1129 */     stringBuffer2.append("and mdl.valto>current timestamp ");
/* 1130 */     stringBuffer2.append("and mdl.effto>current timestamp ");
/* 1131 */     stringBuffer2.append("and f.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1132 */     stringBuffer2.append("and f.valto>current timestamp ");
/* 1133 */     stringBuffer2.append("and f.effto>current timestamp ");
/* 1134 */     stringBuffer2.append("and f.attributecode='PDHDOMAIN' ");
/* 1135 */     stringBuffer2.append("and f.attributevalue in (" + stringBuffer1.toString() + ") ");
/* 1136 */     stringBuffer2.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1137 */     stringBuffer2.append("and f1.valto>current timestamp ");
/* 1138 */     stringBuffer2.append("and f1.effto>current timestamp ");
/* 1139 */     stringBuffer2.append("and f1.attributecode='" + paramString4 + "'");
/* 1140 */     stringBuffer2.append("and f1.attributevalue in (" + paramStringBuffer.toString() + ") ");
/* 1141 */     stringBuffer2.append("and t.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1142 */     stringBuffer2.append("and t.attributecode='" + paramString2 + "' ");
/* 1143 */     stringBuffer2.append("and t.valto>current timestamp ");
/* 1144 */     stringBuffer2.append("and t.effto>current timestamp ");
/* 1145 */     stringBuffer2.append("and t.attributevalue>='" + paramString3 + "' with ur");
/*      */     
/* 1147 */     return stringBuffer2.toString();
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
/*      */ 
/*      */ 
/*      */   
/*      */   private String getFilteredLSEOSql(String paramString1, String paramString2, String paramString3, StringBuffer paramStringBuffer) {
/* 1163 */     StringBuffer stringBuffer = new StringBuffer("select distinct wwseo.entityid from opicm.entity wwseo ");
/* 1164 */     stringBuffer.append("join opicm.relator r1 on wwseo.entitytype=r1.entity1type and wwseo.entityid=r1.entity1id ");
/*      */ 
/*      */     
/* 1167 */     stringBuffer.append("join opicm.relator r2 on wwseo.entitytype=r2.entity2type and wwseo.entityid=r2.entity2id ");
/*      */ 
/*      */     
/* 1170 */     stringBuffer.append("join opicm.flag f1 on r1.entity2type=f1.entitytype and r1.entity2id=f1.entityid ");
/*      */ 
/*      */     
/* 1173 */     stringBuffer.append("where wwseo.entitytype='WWSEO' ");
/* 1174 */     stringBuffer.append("and wwseo.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1175 */     stringBuffer.append("and wwseo.valto>current timestamp ");
/* 1176 */     stringBuffer.append("and wwseo.effto>current timestamp ");
/* 1177 */     stringBuffer.append("and r1.entitytype='WWSEOLSEO' ");
/* 1178 */     stringBuffer.append("and r1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1179 */     stringBuffer.append("and r1.valto>current timestamp ");
/* 1180 */     stringBuffer.append("and r1.effto>current timestamp ");
/* 1181 */     stringBuffer.append("and r2.entitytype='MODELWWSEO' ");
/* 1182 */     stringBuffer.append("and r2.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1183 */     stringBuffer.append("and r2.valto>current timestamp ");
/* 1184 */     stringBuffer.append("and r2.effto>current timestamp ");
/* 1185 */     stringBuffer.append("and f1.attributecode='" + paramString3 + "' ");
/* 1186 */     stringBuffer.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1187 */     stringBuffer.append("and f1.valto>current timestamp ");
/* 1188 */     stringBuffer.append("and f1.effto>current timestamp ");
/* 1189 */     stringBuffer.append("and f1.attributevalue in (" + paramStringBuffer.toString() + ") ");
/* 1190 */     stringBuffer.append("and not exists  ");
/* 1191 */     stringBuffer.append("(select t.entityid from opicm.text t where ");
/* 1192 */     stringBuffer.append("t.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1193 */     stringBuffer.append("and t.entitytype='MODEL' ");
/* 1194 */     stringBuffer.append("and t.entityid=r2.entity1id ");
/* 1195 */     stringBuffer.append("and t.attributecode='" + paramString1 + "' ");
/* 1196 */     stringBuffer.append("and t.valto>current timestamp  ");
/* 1197 */     stringBuffer.append("and t.effto>current timestamp) ");
/* 1198 */     stringBuffer.append("union  ");
/* 1199 */     stringBuffer.append("select distinct wwseo.entityid from opicm.entity wwseo ");
/* 1200 */     stringBuffer.append("join opicm.relator r1 on wwseo.entitytype=r1.entity1type and wwseo.entityid=r1.entity1id ");
/*      */ 
/*      */     
/* 1203 */     stringBuffer.append("join opicm.relator r2 on wwseo.entitytype=r2.entity2type and wwseo.entityid=r2.entity2id ");
/*      */ 
/*      */     
/* 1206 */     stringBuffer.append("join opicm.flag f1 on r1.entity2type=f1.entitytype and r1.entity2id=f1.entityid ");
/*      */ 
/*      */     
/* 1209 */     stringBuffer.append("join opicm.text t on r2.entity1type=t.entitytype and r2.entity1id=t.entityid ");
/*      */ 
/*      */     
/* 1212 */     stringBuffer.append("where wwseo.entitytype='WWSEO' ");
/* 1213 */     stringBuffer.append("and wwseo.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1214 */     stringBuffer.append("and wwseo.valto>current timestamp ");
/* 1215 */     stringBuffer.append("and wwseo.effto>current timestamp ");
/* 1216 */     stringBuffer.append("and r1.entitytype='WWSEOLSEO' ");
/* 1217 */     stringBuffer.append("and r1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1218 */     stringBuffer.append("and r1.valto>current timestamp ");
/* 1219 */     stringBuffer.append("and r1.effto>current timestamp ");
/* 1220 */     stringBuffer.append("and r2.entitytype='MODELWWSEO' ");
/* 1221 */     stringBuffer.append("and r2.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1222 */     stringBuffer.append("and r2.valto>current timestamp ");
/* 1223 */     stringBuffer.append("and r2.effto>current timestamp ");
/* 1224 */     stringBuffer.append("and f1.attributecode='" + paramString3 + "' ");
/* 1225 */     stringBuffer.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1226 */     stringBuffer.append("and f1.valto>current timestamp ");
/* 1227 */     stringBuffer.append("and f1.effto>current timestamp ");
/* 1228 */     stringBuffer.append("and f1.attributevalue in (" + paramStringBuffer.toString() + ") ");
/* 1229 */     stringBuffer.append("and t.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1230 */     stringBuffer.append("and t.attributecode='" + paramString1 + "' ");
/* 1231 */     stringBuffer.append("and t.valto>current timestamp  ");
/* 1232 */     stringBuffer.append("and t.effto>current timestamp ");
/* 1233 */     stringBuffer.append("and t.attributevalue >='" + paramString2 + "' with ur");
/* 1234 */     return stringBuffer.toString();
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
/*      */ 
/*      */   
/*      */   private String getFilteredWWSEOSql(String paramString, StringBuffer paramStringBuffer, Vector<E> paramVector) {
/* 1249 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 1250 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1251 */       if (stringBuffer1.length() > 0) {
/* 1252 */         stringBuffer1.append(',');
/*      */       }
/* 1254 */       stringBuffer1.append("'" + paramVector.elementAt(b).toString() + "'");
/*      */     } 
/* 1256 */     StringBuffer stringBuffer2 = new StringBuffer("select distinct wwseo.entityid from opicm.entity wwseo ");
/* 1257 */     stringBuffer2.append("join opicm.flag f2 on wwseo.entitytype=f2.entitytype and wwseo.entityid=f2.entityid ");
/*      */ 
/*      */     
/* 1260 */     stringBuffer2.append("where wwseo.entitytype='WWSEO' ");
/* 1261 */     stringBuffer2.append("and wwseo.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1262 */     stringBuffer2.append("and wwseo.valto>current timestamp ");
/* 1263 */     stringBuffer2.append("and wwseo.effto>current timestamp ");
/* 1264 */     stringBuffer2.append("and f2.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1265 */     stringBuffer2.append("and f2.valto>current timestamp ");
/* 1266 */     stringBuffer2.append("and f2.effto>current timestamp ");
/* 1267 */     stringBuffer2.append("and f2.attributecode='PDHDOMAIN' ");
/* 1268 */     stringBuffer2.append("and f2.attributevalue in (" + stringBuffer1.toString() + ") ");
/* 1269 */     stringBuffer2.append("intersect  ");
/* 1270 */     stringBuffer2.append("select distinct wwseo.entityid from opicm.entity wwseo ");
/* 1271 */     stringBuffer2.append("join opicm.flag f1 on wwseo.entitytype=f1.entitytype and wwseo.entityid=f1.entityid ");
/*      */ 
/*      */     
/* 1274 */     stringBuffer2.append("where wwseo.entitytype='WWSEO' ");
/* 1275 */     stringBuffer2.append("and wwseo.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1276 */     stringBuffer2.append("and wwseo.valto>current timestamp ");
/* 1277 */     stringBuffer2.append("and wwseo.effto>current timestamp ");
/* 1278 */     stringBuffer2.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1279 */     stringBuffer2.append("and f1.valto>current timestamp ");
/* 1280 */     stringBuffer2.append("and f1.effto>current timestamp ");
/* 1281 */     stringBuffer2.append("and f1.attributecode='STATUS' ");
/* 1282 */     stringBuffer2.append("and f1.attributevalue in (" + paramStringBuffer.toString() + ") with ur");
/* 1283 */     return stringBuffer2.toString();
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
/*      */ 
/*      */   
/*      */   private Vector getMatchingDateIds(String paramString) throws SQLException, MiddlewareException {
/* 1298 */     Vector<Integer> vector = new Vector();
/*      */     
/* 1300 */     addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(paramString));
/* 1301 */     PreparedStatement preparedStatement = null;
/* 1302 */     ResultSet resultSet = null;
/*      */     
/*      */     try {
/* 1305 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(paramString);
/*      */       
/* 1307 */       resultSet = preparedStatement.executeQuery();
/*      */       
/* 1309 */       while (resultSet.next()) {
/* 1310 */         int i = resultSet.getInt(1);
/* 1311 */         if (i > 0) {
/* 1312 */           vector.add(new Integer(i));
/*      */         }
/*      */       } 
/* 1315 */       addDebug("getMatchingDateIds all matchIdVct.cnt " + vector.size());
/*      */     } finally {
/* 1317 */       if (resultSet != null) {
/*      */         try {
/* 1319 */           resultSet.close();
/* 1320 */         } catch (Exception exception) {
/* 1321 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*      */         } 
/* 1323 */         resultSet = null;
/*      */       } 
/*      */       
/* 1326 */       if (preparedStatement != null) {
/*      */         try {
/* 1328 */           preparedStatement.close();
/* 1329 */         } catch (Exception exception) {
/* 1330 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*      */         } 
/* 1332 */         preparedStatement = null;
/*      */       } 
/*      */       
/* 1335 */       this.m_db.commit();
/* 1336 */       this.m_db.freeStatement();
/* 1337 */       this.m_db.isPending();
/*      */     } 
/*      */     
/* 1340 */     return vector;
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
/*      */   private Vector getMatchingDateIds(String paramString, Vector<?> paramVector) throws SQLException, MiddlewareException {
/* 1353 */     Vector<Integer> vector = new Vector();
/*      */     
/* 1355 */     addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(paramString));
/* 1356 */     PreparedStatement preparedStatement = null;
/* 1357 */     ResultSet resultSet = null;
/*      */     
/*      */     try {
/* 1360 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(paramString);
/*      */       
/* 1362 */       resultSet = preparedStatement.executeQuery();
/*      */       
/* 1364 */       while (resultSet.next()) {
/* 1365 */         int i = resultSet.getInt(1);
/* 1366 */         if (i > 0) {
/* 1367 */           vector.add(new Integer(i));
/*      */         }
/*      */       } 
/* 1370 */       addDebug("getMatchingDateIds all matchIdVct.cnt " + vector.size());
/*      */       
/* 1372 */       vector.retainAll(paramVector);
/* 1373 */       addDebug("getMatchingDateIds after retainall matchIdVct " + vector.size());
/*      */     } finally {
/* 1375 */       if (resultSet != null) {
/*      */         try {
/* 1377 */           resultSet.close();
/* 1378 */         } catch (Exception exception) {
/* 1379 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*      */         } 
/* 1381 */         resultSet = null;
/*      */       } 
/*      */       
/* 1384 */       if (preparedStatement != null) {
/*      */         try {
/* 1386 */           preparedStatement.close();
/* 1387 */         } catch (Exception exception) {
/* 1388 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*      */         } 
/* 1390 */         preparedStatement = null;
/*      */       } 
/*      */       
/* 1393 */       this.m_db.commit();
/* 1394 */       this.m_db.freeStatement();
/* 1395 */       this.m_db.isPending();
/*      */     } 
/*      */     
/* 1398 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addGenMsg(String paramString1, String paramString2) {
/* 1405 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/* 1406 */     Object[] arrayOfObject = { paramString2 };
/* 1407 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String buildDQTriggeredRptHeader() {
/* 1417 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1424 */     MessageFormat messageFormat = new MessageFormat(str);
/* 1425 */     this.args[0] = this.m_prof.getOPName();
/* 1426 */     this.args[1] = this.m_prof.getRoleDescription();
/* 1427 */     this.args[2] = this.m_prof.getWGName();
/* 1428 */     this.args[3] = this.m_prof.getNow();
/* 1429 */     this.args[4] = this.rootDesc + ": " + this.navName;
/* 1430 */     this.args[5] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/* 1431 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1441 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 1443 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1444 */     EANList eANList = entityGroup.getMetaAttribute();
/* 1445 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1447 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1448 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1449 */       stringBuffer.append(" ");
/*      */     } 
/*      */     
/* 1452 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private void closePrintWriters() {
/* 1456 */     if (this.dbgPw != null) {
/* 1457 */       this.dbgPw.flush();
/* 1458 */       this.dbgPw.close();
/* 1459 */       this.dbgPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 1467 */     return this.m_db;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getABRAttrCode() {
/* 1474 */     return this.m_abri.getABRCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1481 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1488 */     if (this.dbgPw != null) {
/* 1489 */       this.dbgLen += paramString.length();
/* 1490 */       this.dbgPw.println(paramString);
/* 1491 */       this.dbgPw.flush();
/*      */     } else {
/* 1493 */       this.debugSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1501 */     addOutput(paramString);
/* 1502 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1512 */     return "1.12";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1521 */     return "ATTRDERIVEABRSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updatePDH() throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 1531 */     logMessage(getDescription() + " updating PDH with " + this.vctReturnsEntityKeys.size() + " entitykeys");
/* 1532 */     addDebug("updatePDH entered for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys.size());
/* 1533 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */       try {
/* 1535 */         this.m_db.update(this.m_prof, this.vctReturnsEntityKeys, false, false);
/*      */       } finally {
/* 1537 */         this.vctReturnsEntityKeys.clear();
/* 1538 */         this.m_db.commit();
/* 1539 */         this.m_db.freeStatement();
/* 1540 */         this.m_db.isPending("finally after updatePDH");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getPropertiesLimitValues() {
/* 1549 */     String str1 = ABRServerProperties.getValue("ATTRDERIVEABRSTATUS", "_velimit", "5");
/*      */     
/* 1551 */     if (isDigit(str1)) {
/*      */       
/* 1553 */       MW_VENTITY_LIMIT = Integer.parseInt(str1);
/*      */     } else {
/* 1555 */       MW_VENTITY_LIMIT = 5;
/*      */     } 
/*      */     
/* 1558 */     String str2 = ABRServerProperties.getValue("ATTRDERIVEABRSTATUS", "_runlimit", "0");
/*      */     
/* 1560 */     if (isDigit(str2)) {
/*      */       
/* 1562 */       RUN_LIMIT = Integer.parseInt(str2);
/*      */     } else {
/* 1564 */       RUN_LIMIT = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/* 1572 */     if (this.dbgLen + this.rptSb.length() < 5000000) {
/*      */       
/* 1574 */       BufferedInputStream bufferedInputStream = null;
/* 1575 */       FileInputStream fileInputStream = null;
/* 1576 */       BufferedReader bufferedReader = null;
/*      */       try {
/* 1578 */         fileInputStream = new FileInputStream(this.dbgfn);
/* 1579 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/* 1581 */         String str = null;
/* 1582 */         StringBuffer stringBuffer = new StringBuffer();
/* 1583 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/* 1585 */         while ((str = bufferedReader.readLine()) != null) {
/* 1586 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/* 1588 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/* 1591 */         File file = new File(this.dbgfn);
/* 1592 */         if (file.exists()) {
/* 1593 */           file.delete();
/*      */         }
/* 1595 */       } catch (Exception exception) {
/* 1596 */         exception.printStackTrace();
/*      */       } finally {
/* 1598 */         if (bufferedInputStream != null) {
/*      */           try {
/* 1600 */             bufferedInputStream.close();
/* 1601 */           } catch (Exception exception) {
/* 1602 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/* 1605 */         if (fileInputStream != null)
/*      */           try {
/* 1607 */             fileInputStream.close();
/* 1608 */           } catch (Exception exception) {
/* 1609 */             exception.printStackTrace();
/*      */           }  
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\ATTRDERIVEABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */