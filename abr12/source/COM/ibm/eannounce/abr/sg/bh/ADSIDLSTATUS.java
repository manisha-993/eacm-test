/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.Validate;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.Attribute;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import COM.ibm.opicmpdh.objects.Text;
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
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
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
/*      */ public class ADSIDLSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*      */   private static final String ADSIDLSTATUS = "ADSIDLSTATUS";
/*      */   private static final String ADSIDLLASTRANDTS = "ADSIDLLASTRANDTS";
/*      */   private static final int MAXFILE_SIZE = 5000000;
/*  167 */   private StringBuffer rptSb = new StringBuffer();
/*  168 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  169 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  170 */   private Object[] args = (Object[])new String[10];
/*      */   
/*      */   private static final String ADS_XMLEED_ATTR = "XMLIDLABRSTATUS";
/*      */   
/*      */   private static final String QUEUE_ATTR = "SYSFEEDRESEND";
/*      */   
/*      */   private static final String QUEUE_VALUE = "CUR";
/*      */   
/*      */   private static final String MQUEUE_ATTR = "XMLABRPROPFILE";
/*      */   
/*      */   private static boolean IS_SYSFEEDRESEND_CUR = false;
/*      */   
/*      */   private static final String SEARCH_KEY = "ADSIDL";
/*      */   
/*  184 */   private ResourceBundle rsBundle = null;
/*  185 */   private Hashtable metaTbl = new Hashtable<>();
/*  186 */   private String navName = "";
/*  187 */   private PrintWriter dbgPw = null;
/*  188 */   private String dbgfn = null;
/*  189 */   private int dbgLen = 0;
/*  190 */   private int abr_debuglvl = 0;
/*  191 */   private Vector vctReturnsEntityKeys = new Vector();
/*  192 */   private Vector vctReturnsQueueKeys = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Set TBD_SET;
/*      */ 
/*      */ 
/*      */   
/*  201 */   private static final Hashtable FILTER_TBL = new Hashtable<>();
/*  202 */   private static final Hashtable FILTERCACHE_TBL = new Hashtable<>();
/*  203 */   private static final Hashtable REQFILTER_TBL = new Hashtable<>();
/*  204 */   private static final Hashtable PERIODIC_TBL = new Hashtable<>(); static {
/*  205 */     TBD_SET = new HashSet();
/*      */ 
/*      */     
/*  208 */     FILTER_TBL.put("FEATURE", new String[] { "FCTYPE|U", "COUNTRYLIST|F", "WITHDRAWDATEEFF_T|D", "WITHDRAWDATEMIN|D" });
/*  209 */     FILTER_TBL.put("MODEL", new String[] { "SPECBID|U", "COFCAT|U", "COFSUBCAT|U", "COFGRP|U", "COFSUBGRP|U", "COUNTRYLIST|F", "FLFILSYSINDC|F", "WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|D", "DIVTEXT|T" });
/*  210 */     FILTER_TBL.put("SVCMOD", new String[] { "SVCMODCATG|U", "SVCMODGRP|U", "SVCMODSUBCATG|U", "SVCMODSUBGRP|U", "COUNTRYLIST|F", "WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|D", "DIVTEXT|T" });
/*  211 */     FILTER_TBL.put("LSEOBUNDLE", new String[] { "SPECBID|U", "BUNDLETYPE|F", "COUNTRYLIST|F", "FLFILSYSINDC|F", "BUNDLUNPUBDATEMTRGT|D", "WITHDRAWDATEMIN|D", "DIVTEXT|T" });
/*  212 */     FILTER_TBL.put("SWFEATURE", new String[] { "FCTYPE|U", "WITHDRAWDATEEFF_T|D", "WITHDRAWDATEMIN|D" });
/*      */ 
/*      */ 
/*      */     
/*  216 */     FILTER_TBL.put("LSEO", new String[] { "SPECBID|U", "COFCAT|U", "COFSUBCAT|U", "COFGRP|U", "COFSUBGRP|U", "COUNTRYLIST|F", "FLFILSYSINDC|F", "LSEOUNPUBDATEMTRGT|D", "WITHDRAWDATEMIN|D", "DIVTEXT|T" });
/*      */ 
/*      */     
/*  219 */     FILTER_TBL.put("MODELCONVERT", new String[] { "MACHTYPEATR|U", "MODELATR|T", "COUNTRYLIST|F", "WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|D" });
/*  220 */     FILTER_TBL.put("FCTRANSACTION", new String[] { "MACHTYPEATR|U", "MODELATR|T", "WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|D" });
/*      */ 
/*      */     
/*  223 */     FILTER_TBL.put("PRODSTRUCT", new String[] { "FCTYPE|U", "MACHTYPEATR|U", "MODELATR|T", "COUNTRYLIST|F", "FLFILSYSINDC|F", "WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|D" });
/*  224 */     FILTER_TBL.put("SWPRODSTRUCT", new String[] { "FCTYPE|U", "MACHTYPEATR|U", "MODELATR|T", "COUNTRYLIST|F", "WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|D" });
/*      */     
/*  226 */     FILTER_TBL.put("IMG", new String[] { "COUNTRYLIST|F" });
/*  227 */     FILTER_TBL.put("CATNAV", new String[] { "FLFILSYSINDC|F" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  235 */     FILTERCACHE_TBL.put("FEATURE", new String[] { "ADSIDLLASTRANDTS|T" });
/*  236 */     FILTERCACHE_TBL.put("MODEL", new String[] { "ADSIDLLASTRANDTS|T" });
/*  237 */     FILTERCACHE_TBL.put("SVCMOD", new String[] { "ADSIDLLASTRANDTS|T" });
/*  238 */     FILTERCACHE_TBL.put("LSEOBUNDLE", new String[] { "ADSIDLLASTRANDTS|T" });
/*  239 */     FILTERCACHE_TBL.put("SWFEATURE", new String[] { "ADSIDLLASTRANDTS|T" });
/*  240 */     FILTERCACHE_TBL.put("LSEO", new String[] { "ADSIDLLASTRANDTS|T" });
/*  241 */     FILTERCACHE_TBL.put("MODELCONVERT", new String[] { "ADSIDLLASTRANDTS|T" });
/*  242 */     FILTERCACHE_TBL.put("FCTRANSACTION", new String[] { "ADSIDLLASTRANDTS|T" });
/*  243 */     FILTERCACHE_TBL.put("PRODSTRUCT", new String[] { "ADSIDLLASTRANDTS|T" });
/*  244 */     FILTERCACHE_TBL.put("SWPRODSTRUCT", new String[] { "ADSIDLLASTRANDTS|T" });
/*  245 */     FILTERCACHE_TBL.put("SLEORGNPLNTCODE", new String[] { "ADSIDLLASTRANDTS|T" });
/*  246 */     FILTERCACHE_TBL.put("CATNAV", new String[] { "ADSIDLLASTRANDTS|T" });
/*  247 */     FILTERCACHE_TBL.put("GBT", new String[] { "ADSIDLLASTRANDTS|T" });
/*  248 */     FILTERCACHE_TBL.put("REVUNBUNDCOMP", new String[] { "ADSIDLLASTRANDTS|T" });
/*  249 */     FILTERCACHE_TBL.put("SVCLEV", new String[] { "ADSIDLLASTRANDTS|T" });
/*  250 */     FILTERCACHE_TBL.put("WARR", new String[] { "ADSIDLLASTRANDTS|T" });
/*  251 */     FILTERCACHE_TBL.put("IMG", new String[] { "ADSIDLLASTRANDTS|T" });
/*      */     
/*  253 */     FILTERCACHE_TBL.put("REFOFER", new String[] { "ENDOFSVC|D", "ADSIDLLASTRANDTS|T" });
/*  254 */     FILTERCACHE_TBL.put("REFOFERFEAT", new String[] { "ENDOFSVC|D", "ADSIDLLASTRANDTS|T" });
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
/*  269 */     TBD_SET.add("WWCOMPAT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  276 */     PERIODIC_TBL.put("GENERALAREA", "20");
/*  277 */     PERIODIC_TBL.put("Deletes", "30");
/*  278 */     PERIODIC_TBL.put("XLATE", "40");
/*      */   }
/*      */   
/*      */   private void setupPrintWriter() {
/*  282 */     String str = this.m_abri.getFileName();
/*  283 */     int i = str.lastIndexOf(".");
/*  284 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*      */     try {
/*  286 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  287 */     } catch (Exception exception) {
/*  288 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriter() {
/*  292 */     if (this.dbgPw != null) {
/*  293 */       this.dbgPw.flush();
/*  294 */       this.dbgPw.close();
/*  295 */       this.dbgPw = null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  318 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  320 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
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
/*  331 */     String str3 = "";
/*      */     
/*  333 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */     
/*      */     try {
/*  337 */       long l = System.currentTimeMillis();
/*  338 */       start_ABRBuild();
/*      */       
/*  340 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */       
/*  342 */       setupPrintWriter();
/*      */ 
/*      */       
/*  345 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  347 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  349 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " extract: " + this.m_abri
/*  350 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  353 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  358 */       this.navName = getNavigationName(entityItem);
/*      */ 
/*      */       
/*  361 */       String str5 = PokUtils.getAttributeValue(entityItem, "XMLENTITYTYPE", "", null, false);
/*  362 */       String str6 = PokUtils.getAttributeValue(entityItem, "XMLSETUPTYPE", "", null, false);
/*  363 */       String str7 = PokUtils.getAttributeFlagValue(entityItem, "XMLABRPROPFILE");
/*      */       
/*  365 */       addDebug("Executing for entityType: " + str5 + " mqueue: " + str7 + " setupType: " + str6);
/*      */ 
/*      */       
/*  368 */       if (str6 == null) {
/*  369 */         this.args[0] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "XMLSETUPTYPE", "XMLSETUPTYPE");
/*  370 */         addError("INVALID_ATTR_ERR", this.args);
/*      */       }
/*  372 */       else if ("Production".equals(str6)) {
/*  373 */         this.args[0] = "Production";
/*  374 */         addError("INVALID_SETUPTYPE_ERR", this.args);
/*  375 */       } else if (str5 == null) {
/*      */         
/*  377 */         this.args[0] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "XMLENTITYTYPE", "XMLENTITYTYPE");
/*  378 */         addError("INVALID_ATTR_ERR", this.args);
/*  379 */       } else if (TBD_SET.contains(str5)) {
/*      */         
/*  381 */         this.args[0] = getLD_Value(entityItem, "XMLENTITYTYPE");
/*  382 */         addError("NOT_SUPPORTED", this.args);
/*      */       } else {
/*  384 */         String str8 = (String)PERIODIC_TBL.get(str5);
/*  385 */         String str9 = str5;
/*  386 */         String str10 = "";
/*  387 */         if (str8 != null) {
/*  388 */           str9 = "ADSXMLSETUP";
/*  389 */           str10 = str9 + " for " + str5;
/*      */         } else {
/*  391 */           str10 = getLD_Value(entityItem, "XMLENTITYTYPE");
/*      */         } 
/*      */         
/*  394 */         EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, str9, "Edit", false);
/*      */ 
/*      */ 
/*      */         
/*  398 */         EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute("XMLIDLABRSTATUS");
/*  399 */         if (eANMetaAttribute == null) {
/*      */           
/*  401 */           this.args[0] = str10;
/*  402 */           this.args[1] = "XMLIDLABRSTATUS";
/*  403 */           addError("INVALID_META_ERR", this.args);
/*      */         } 
/*  405 */         eANMetaAttribute = entityGroup.getMetaAttribute("XMLABRPROPFILE");
/*  406 */         if (eANMetaAttribute == null) {
/*      */           
/*  408 */           this.args[0] = str10;
/*  409 */           this.args[1] = "XMLABRPROPFILE";
/*  410 */           addError("INVALID_META_ERR", this.args);
/*      */         } 
/*      */       } 
/*      */       
/*  414 */       if ("IDL".equals(str6) && str7 == null) {
/*      */         
/*  416 */         this.args[0] = getLD_Value(entityItem, "XMLABRPROPFILE");
/*  417 */         addError("INVALID_ATTR_ERR", this.args);
/*      */       } 
/*      */       
/*  420 */       if ("Cache".equals(str6) && (String)PERIODIC_TBL.get(str5) != null) {
/*      */         
/*  422 */         this.args[0] = str5;
/*  423 */         addError("INVALID_PERIODIC_ERR", this.args);
/*      */       } 
/*      */       
/*  426 */       if ("Cache current".equals(str6) && (String)PERIODIC_TBL.get(str5) != null) {
/*      */         
/*  428 */         this.args[0] = str5;
/*  429 */         addError("INVALID_PERIODIC_ERR", this.args);
/*      */       } 
/*      */       
/*  432 */       if (getReturnCode() == 0) {
/*  433 */         if ("Cache".equals(str6) || "Cache current".equals(str6)) {
/*  434 */           if ("Cache current".equals(str6)) {
/*  435 */             IS_SYSFEEDRESEND_CUR = true;
/*  436 */             addDebug("case 1 IS_SYSFEEDRESEND_CUR= " + IS_SYSFEEDRESEND_CUR + ";setupType=" + str6);
/*      */           } else {
/*  438 */             IS_SYSFEEDRESEND_CUR = false;
/*  439 */             addDebug("case 2 IS_SYSFEEDRESEND_CUR= " + IS_SYSFEEDRESEND_CUR + ";setupType=" + str6);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  444 */           long l1 = System.currentTimeMillis();
/*      */           
/*  446 */           Vector<Integer> vector = getEntityIds(entityItem, str5);
/*  447 */           if (getReturnCode() == 0) {
/*  448 */             String str8 = (String)PERIODIC_TBL.get(str5);
/*  449 */             String str9 = str5;
/*  450 */             if (str8 != null) {
/*  451 */               str9 = "ADSXMLSETUP with " + str5;
/*  452 */               str5 = "ADSXMLSETUP";
/*      */             } 
/*  454 */             addDebug("Time to find entity ids: " + Stopwatch.format(System.currentTimeMillis() - l1));
/*  455 */             addDebug("Update these entity ids.cnt: " + ((vector == null) ? "null" : ("" + vector.size())));
/*  456 */             addDebug(2, "Update these ids: " + vector);
/*  457 */             l1 = System.currentTimeMillis();
/*  458 */             String[] arrayOfString = PokUtils.convertToArray(str7);
/*      */             
/*  460 */             if (vector != null && vector.size() > 0) {
/*  461 */               String str = getQueuedValue("XMLIDLABRSTATUS"); int i;
/*  462 */               for (i = 0; i < vector.size(); i++) {
/*  463 */                 Integer integer = vector.elementAt(i);
/*      */                 
/*  465 */                 if (IS_SYSFEEDRESEND_CUR) setQueueValues(str5, integer.intValue()); 
/*  466 */                 setValues(arrayOfString, str, str5, integer.intValue());
/*      */ 
/*      */ 
/*      */                 
/*  470 */                 if (this.vctReturnsEntityKeys.size() >= 500) {
/*  471 */                   int j = this.vctReturnsEntityKeys.size();
/*      */                   
/*  473 */                   if (IS_SYSFEEDRESEND_CUR) updatePDHQueue();
/*      */                   
/*  475 */                   updatePDH();
/*      */                   
/*  477 */                   long l2 = System.currentTimeMillis();
/*  478 */                   addDebug("Time to update " + j + " " + str5 + ": " + Stopwatch.format(l2 - l1));
/*  479 */                   l1 = l2;
/*      */                 } 
/*      */               } 
/*      */               
/*  483 */               if (this.vctReturnsEntityKeys.size() > 0) {
/*  484 */                 i = this.vctReturnsEntityKeys.size();
/*      */                 
/*  486 */                 if (IS_SYSFEEDRESEND_CUR) updatePDHQueue();
/*      */                 
/*  488 */                 updatePDH();
/*  489 */                 addDebug("Time to update " + i + " " + str5 + ": " + 
/*  490 */                     Stopwatch.format(System.currentTimeMillis() - l1));
/*      */               } 
/*  492 */               vector.clear();
/*      */             } else {
/*      */               
/*  495 */               this.args[0] = str9;
/*  496 */               addMessage("", "NONE_FOUND", this.args);
/*      */             } 
/*      */             
/*  499 */             EANAttribute eANAttribute = entityItem.getAttribute("ADSIDLSTATUS");
/*  500 */             if (eANAttribute != null) {
/*  501 */               AttributeChangeHistoryGroup attributeChangeHistoryGroup = new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*  502 */               if (attributeChangeHistoryGroup != null && attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*  503 */                 int i = attributeChangeHistoryGroup.getChangeHistoryItemCount();
/*  504 */                 AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*  505 */                 if (attributeChangeHistoryItem != null) {
/*  506 */                   String str = attributeChangeHistoryItem.getFlagCode();
/*  507 */                   addDebug("checking ADSIDLSTATUS :" + str);
/*  508 */                   if ("0050".equals(str)) {
/*  509 */                     String str10 = attributeChangeHistoryItem.getChangeDate();
/*  510 */                     setTextValue(entityItem, "ADSIDLLASTRANDTS", str10);
/*  511 */                     addDebug("set lastrunning time back to setup entity :" + str10);
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*  517 */         } else if ("IDL".equals(str6)) {
/*  518 */           Vector<String> vector = new Vector();
/*  519 */           vector.add("PDHDOMAIN");
/*  520 */           vector.add("ADSIDLSTATUS");
/*  521 */           vector.add("XMLENTITYTYPE");
/*  522 */           vector.add("XMLABRPROPFILE");
/*  523 */           vector.add("XMLSETUPTYPE");
/*  524 */           vector.add("XMLSETUPDESC");
/*  525 */           vector.add("XMLTARGETSYSTEM");
/*  526 */           vector.add("XMLVERSION");
/*  527 */           vector.add("XMLMOD");
/*  528 */           vector.add("XMLSTATUS");
/*  529 */           vector.add("XMLIDLREQDTS");
/*  530 */           vector.add("XMLIDLMAXMSG");
/*  531 */           vector.add("OLDINDC");
/*      */           
/*  533 */           String[] arrayOfString = (String[])FILTER_TBL.get(str5);
/*  534 */           if (arrayOfString != null) {
/*      */             
/*  536 */             for (byte b = 0; b < arrayOfString.length; b++) {
/*  537 */               String[] arrayOfString1 = PokUtils.convertToArray(arrayOfString[b]);
/*  538 */               String str = arrayOfString1[0];
/*      */ 
/*      */               
/*  541 */               vector.add(str);
/*      */             } 
/*      */             
/*  544 */             checkExtraAttrs(entityItem, str5, vector);
/*      */           } 
/*  546 */           if (getReturnCode() == 0) {
/*      */             
/*  548 */             XMLFiterMQIDL xMLFiterMQIDL = new XMLFiterMQIDL(this);
/*  549 */             xMLFiterMQIDL.getFullXmlAndSendToQue(entityItem);
/*  550 */             String str = xMLFiterMQIDL.getReport();
/*  551 */             addOutput(str);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  556 */         addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */       }
/*      */     
/*  559 */     } catch (Throwable throwable) {
/*  560 */       StringWriter stringWriter = new StringWriter();
/*  561 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  562 */       String str6 = "<pre>{0}</pre>";
/*  563 */       MessageFormat messageFormat1 = new MessageFormat(str5);
/*  564 */       setReturnCode(-3);
/*  565 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  567 */       this.args[0] = throwable.getMessage();
/*  568 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  569 */       messageFormat1 = new MessageFormat(str6);
/*  570 */       this.args[0] = stringWriter.getBuffer().toString();
/*  571 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  572 */       logError("Exception: " + throwable.getMessage());
/*  573 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*      */       
/*  576 */       setDGTitle(this.navName);
/*  577 */       setDGRptName(getShortClassName(getClass()));
/*  578 */       setDGRptClass(getABRCode());
/*      */       
/*  580 */       if (!isReadOnly()) {
/*  581 */         clearSoftLock();
/*      */       }
/*  583 */       closePrintWriter();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  588 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  589 */     this.args[0] = getDescription();
/*  590 */     this.args[1] = this.navName;
/*  591 */     String str4 = messageFormat.format(this.args);
/*  592 */     messageFormat = new MessageFormat(str2);
/*  593 */     this.args[0] = this.m_prof.getOPName();
/*  594 */     this.args[1] = this.m_prof.getRoleDescription();
/*  595 */     this.args[2] = this.m_prof.getWGName();
/*  596 */     this.args[3] = getNow();
/*  597 */     this.args[4] = this.navName;
/*  598 */     this.args[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  599 */     this.args[6] = str3 + " " + getABRVersion();
/*      */     
/*  601 */     restoreXtraContent();
/*      */     
/*  603 */     this.rptSb.insert(0, str4 + messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  605 */     println(this.rptSb.toString());
/*  606 */     printDGSubmitString();
/*  607 */     println(EACustom.getTOUDiv());
/*  608 */     buildReportFooter();
/*      */     
/*  610 */     this.metaTbl.clear();
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
/*      */   private Vector getEntityIds(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException, MiddlewareShutdownInProgressException {
/*  646 */     Vector vector = null;
/*  647 */     Vector<String> vector1 = new Vector();
/*  648 */     vector1.add("PDHDOMAIN");
/*  649 */     vector1.add("ADSIDLSTATUS");
/*  650 */     vector1.add("XMLENTITYTYPE");
/*  651 */     vector1.add("XMLABRPROPFILE");
/*  652 */     vector1.add("XMLSETUPTYPE");
/*  653 */     vector1.add("XMLSETUPDESC");
/*  654 */     vector1.add("XMLTARGETSYSTEM");
/*  655 */     vector1.add("XMLVERSION");
/*  656 */     vector1.add("XMLMOD");
/*      */     
/*  658 */     Vector<String> vector2 = new Vector();
/*  659 */     Vector<String> vector3 = new Vector();
/*  660 */     Vector<String> vector4 = new Vector();
/*  661 */     Vector<String> vector5 = new Vector();
/*      */ 
/*      */     
/*  664 */     Vector<String> vector6 = new Vector();
/*  665 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN");
/*  666 */     if (str1 != null) {
/*  667 */       String[] arrayOfString = PokUtils.convertToArray(str1);
/*  668 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  669 */         vector6.add(arrayOfString[b]);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  674 */     addDebug("domainVct " + vector6);
/*      */     
/*  676 */     String str2 = (String)PERIODIC_TBL.get(paramString);
/*  677 */     if (str2 != null) {
/*      */       
/*  679 */       checkExtraAttrs(paramEntityItem, paramString, vector1);
/*  680 */       if (getReturnCode() == 0) {
/*  681 */         vector4.add(str2);
/*  682 */         vector5.add("ADSTYPE");
/*  683 */         addDebug("executing on entityType ADSXMLSETUP for " + paramString + " with flagAttrVct " + vector5 + " flagValuesVct " + vector4);
/*      */ 
/*      */         
/*  686 */         this.rptSb.append("<br /><h2>Looking for ADSXMLSETUP with the following filters:<br />");
/*  687 */         this.rptSb.append("ADSTYPE = " + str2 + " for " + paramString + "<br />");
/*  688 */         String str = PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", " or ", null, false);
/*  689 */         this.rptSb.append(PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PDHDOMAIN", "PDHDOMAIN") + " = " + str + "<br />");
/*      */ 
/*      */         
/*  692 */         this.rptSb.append("</h2>" + NEWLINE);
/*      */         
/*  694 */         vector = new Vector();
/*      */         
/*  696 */         for (byte b = 0; b < vector6.size(); b++) {
/*  697 */           getMatchingTextAndFlagIds("ADSXMLSETUP", vector2, vector3, vector4, vector5, vector, vector6
/*  698 */               .elementAt(b).toString());
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  703 */       String[] arrayOfString = (String[])FILTERCACHE_TBL.get(paramString);
/*      */       
/*  705 */       String str3 = null;
/*  706 */       String str4 = null;
/*      */ 
/*      */       
/*  709 */       if (arrayOfString != null)
/*      */       {
/*  711 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*  712 */           String[] arrayOfString1 = PokUtils.convertToArray(arrayOfString[b]);
/*  713 */           String str5 = arrayOfString1[0];
/*  714 */           String str6 = arrayOfString1[1];
/*  715 */           String str7 = null;
/*  716 */           vector1.add(str5);
/*      */           
/*  718 */           if (str6.equals("T")) {
/*  719 */             str7 = PokUtils.getAttributeValue(paramEntityItem, str5, "", null, false);
/*  720 */             if (str7 != null && str7.length() > 0) {
/*  721 */               vector3.add(str5);
/*  722 */               vector2.add(str7);
/*  723 */             } else if (isRequired(paramString, str5)) {
/*      */               
/*  725 */               this.args[0] = str5;
/*  726 */               this.args[1] = paramString;
/*  727 */               addError("INVALID_FILTER_ERR", this.args);
/*      */             } 
/*  729 */           } else if (str6.equals("D")) {
/*      */             
/*  731 */             str7 = PokUtils.getAttributeValue(paramEntityItem, str5, "", null, false);
/*  732 */             str3 = str5;
/*  733 */             if (str7 == null || str7.length() == 0) {
/*  734 */               str7 = this.m_strEpoch.substring(0, 10);
/*      */             }
/*  736 */             str4 = str7;
/*      */           } else {
/*  738 */             str7 = PokUtils.getAttributeFlagValue(paramEntityItem, str5);
/*  739 */             if (str7 != null && str7.length() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  747 */               vector5.add(str5);
/*  748 */               vector4.add(str7);
/*      */             }
/*  750 */             else if (isRequired(paramString, str5)) {
/*      */               
/*  752 */               this.args[0] = str5;
/*  753 */               this.args[1] = paramString;
/*  754 */               addError("INVALID_FILTER_ERR", this.args);
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  763 */           addDebug("entitytype " + paramString + " attrcode " + str5 + " value " + str7);
/*      */         } 
/*      */       }
/*      */       
/*  767 */       checkExtraAttrs(paramEntityItem, paramString, vector1);
/*      */       
/*  769 */       if (getReturnCode() == 0) {
/*      */         
/*  771 */         addDebug("executing on entityType " + paramString + " with flagAttrVct " + vector5 + " flagValuesVct " + vector4 + " txtAttrVct " + vector3 + " txtValuesVct " + vector2 + " dateAttr " + str3 + " dateValue " + str4);
/*      */ 
/*      */ 
/*      */         
/*  775 */         addHeadingInfo(paramEntityItem, paramString, vector3, vector5, str3, str4);
/*      */         
/*  777 */         String str5 = PokUtils.getAttributeValue(paramEntityItem, "ADSIDLLASTRANDTS", "|", null, false);
/*      */         
/*  779 */         if (str5 != null && !Validate.isoDate(str5)) {
/*      */           
/*  781 */           this.args[0] = "ADSIDLLASTRANDTS";
/*  782 */           addError("INVAILD_DATE_ERR", this.args);
/*      */         } 
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
/*  796 */         long l1 = System.currentTimeMillis();
/*  797 */         String str6 = "Time to filter " + paramString + ": ";
/*  798 */         if ("MODEL".equals(paramString)) {
/*  799 */           String str = null;
/*  800 */           str = getFilteredModelSql(paramString, vector6, str5);
/*  801 */           if (str != null) {
/*  802 */             vector = getMatchingIds(str);
/*      */           }
/*      */           
/*  805 */           str6 = "Time to filter on " + paramString + " with MODEL text and flags: ";
/*  806 */         } else if (paramString.equals("REFOFER")) {
/*  807 */           if (str3 != null) {
/*  808 */             String str = null;
/*  809 */             str = getFilteredDateSql(paramString, str3, str4, vector6, str5);
/*  810 */             if (str != null) {
/*  811 */               vector = getMatchingIds(str);
/*      */             }
/*      */           } 
/*  814 */           str6 = "Time to filter on " + paramString + " with REFOFER text and flags: ";
/*  815 */         } else if (paramString.equals("REFOFERFEAT")) {
/*      */           
/*  817 */           if (str3 != null) {
/*  818 */             String str = null;
/*  819 */             str = getFilteredREFOFERFEATDateSql(paramString, str3, str4, vector6, str5);
/*  820 */             if (str != null) {
/*  821 */               vector = getMatchingIds(str);
/*      */             }
/*      */           } 
/*  824 */           str6 = "Time to filter on " + paramString + " with reference REFOFER text and flags: ";
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/*  832 */           String str = null;
/*  833 */           str = getFilteredSql(paramString, vector6, str5);
/*  834 */           if (str != null) {
/*  835 */             vector = getMatchingIds(str);
/*      */           }
/*  837 */           str6 = "Time to filter on root " + paramString + " PDHDomain and WithdrawEffectiveDate: ";
/*      */         } 
/*      */         
/*  840 */         long l2 = System.currentTimeMillis();
/*  841 */         addDebug(str6 + Stopwatch.format(l2 - l1));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  846 */     vector2.clear();
/*  847 */     vector3.clear();
/*  848 */     vector4.clear();
/*  849 */     vector5.clear();
/*  850 */     vector6.clear();
/*      */     
/*  852 */     return vector;
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
/*      */   private void checkExtraAttrs(EntityItem paramEntityItem, String paramString, Vector paramVector) {
/*  869 */     for (byte b = 0; b < paramEntityItem.getAttributeCount(); b++) {
/*  870 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(b);
/*  871 */       String str = eANAttribute.getAttributeCode();
/*  872 */       if (!paramVector.contains(str)) {
/*  873 */         String str1 = PokUtils.getAttributeValue(paramEntityItem, str, "", null, false);
/*  874 */         addDebug("checkExtraAttrs attrcode " + str + " value " + str1);
/*  875 */         if (str1 != null && str1.length() > 0) {
/*      */           
/*  877 */           this.args[0] = str;
/*  878 */           this.args[1] = paramString;
/*  879 */           addError("EXTRA_FILTER_ERR", this.args);
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
/*      */   private void addHeadingInfo(EntityItem paramEntityItem, String paramString1, Vector<E> paramVector1, Vector<E> paramVector2, String paramString2, String paramString3) {
/*  896 */     this.rptSb.append("<br /><h2>Looking for " + paramString1 + " with the following filters:<br />");
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
/*      */     byte b;
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
/*  961 */     for (b = 0; b < paramVector1.size(); b++) {
/*  962 */       String str1 = paramVector1.elementAt(b).toString();
/*  963 */       String str2 = PokUtils.getAttributeValue(paramEntityItem, str1, "", null, false);
/*  964 */       this.rptSb.append(paramString1 + " with " + 
/*  965 */           PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), str1, str1) + " = " + str2 + "<br />");
/*      */     } 
/*      */     
/*  968 */     for (b = 0; b < paramVector2.size(); b++) {
/*  969 */       String str1 = paramVector2.elementAt(b).toString();
/*  970 */       String str2 = PokUtils.getAttributeValue(paramEntityItem, str1, "", null, false);
/*  971 */       this.rptSb.append(paramString1 + " with " + 
/*  972 */           PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), str1, str1) + " = " + str2 + "<br />");
/*      */     } 
/*      */     
/*  975 */     if (paramString2 != null) {
/*  976 */       this.rptSb.append(paramString1 + " with " + 
/*  977 */           PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString2, paramString2) + " &gt;= " + paramString3 + " or not populated<br />");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  982 */     String str = PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", " or ", null, false);
/*  983 */     this.rptSb.append(PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PDHDOMAIN", "PDHDOMAIN") + " = " + str + "<br />");
/*      */ 
/*      */     
/*  986 */     this.rptSb.append("</h2>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isRequired(String paramString1, String paramString2) {
/*  996 */     String[] arrayOfString = (String[])REQFILTER_TBL.get(paramString1);
/*  997 */     boolean bool = false;
/*  998 */     if (arrayOfString != null) {
/*  999 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1000 */         if (paramString2.equals(arrayOfString[b])) {
/* 1001 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1006 */     return bool;
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
/*      */   private void getMatchingTextAndFlagIds(String paramString1, Vector<E> paramVector1, Vector<E> paramVector2, Vector<E> paramVector3, Vector<E> paramVector4, Vector<Integer> paramVector5, String paramString2) throws SQLException, MiddlewareException {
/* 1531 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 1532 */     int i = this.m_db.getNewSessionID();
/*      */     
/*      */     try {
/* 1535 */       byte b1 = 0;
/* 1536 */       ResultSet resultSet = null;
/* 1537 */       ReturnDataResultSet returnDataResultSet = null;
/*      */       
/*      */       byte b2;
/* 1540 */       for (b2 = 0; b2 < paramVector2.size(); b2++) {
/* 1541 */         b1++;
/* 1542 */         this.m_db.callGBL8119(returnStatus, i, b1, this.m_prof.getEnterprise(), paramString1, paramVector2
/* 1543 */             .elementAt(b2).toString(), paramVector1.elementAt(b2).toString());
/* 1544 */         this.m_db.commit();
/* 1545 */         this.m_db.freeStatement();
/* 1546 */         this.m_db.isPending();
/*      */       } 
/*      */ 
/*      */       
/* 1550 */       b1++;
/* 1551 */       this.m_db.callGBL8119(returnStatus, i, b1, this.m_prof.getEnterprise(), paramString1, "PDHDOMAIN", paramString2);
/*      */       
/* 1553 */       this.m_db.commit();
/* 1554 */       this.m_db.freeStatement();
/* 1555 */       this.m_db.isPending();
/*      */ 
/*      */       
/* 1558 */       for (b2 = 0; b2 < paramVector4.size(); b2++) {
/* 1559 */         b1++;
/*      */         
/* 1561 */         this.m_db.callGBL8119(returnStatus, i, b1, this.m_prof.getEnterprise(), paramString1, paramVector4
/* 1562 */             .elementAt(b2).toString(), paramVector3.elementAt(b2).toString());
/* 1563 */         this.m_db.commit();
/* 1564 */         this.m_db.freeStatement();
/* 1565 */         this.m_db.isPending();
/*      */       } 
/*      */       
/*      */       try {
/* 1569 */         resultSet = this.m_db.callGBL9200(returnStatus, i, this.m_prof.getEnterprise(), "ADSIDL" + paramString1, 0, this.m_prof
/* 1570 */             .getValOn(), this.m_prof.getEffOn(), getSPLimit());
/* 1571 */         returnDataResultSet = new ReturnDataResultSet(resultSet);
/*      */       } finally {
/*      */         
/* 1574 */         if (resultSet != null) {
/* 1575 */           resultSet.close();
/* 1576 */           resultSet = null;
/*      */         } 
/* 1578 */         this.m_db.commit();
/* 1579 */         this.m_db.freeStatement();
/* 1580 */         this.m_db.isPending();
/*      */       } 
/* 1582 */       for (b2 = 0; b2 < returnDataResultSet.size(); b2++) {
/* 1583 */         String str = returnDataResultSet.getColumn(b2, 0);
/* 1584 */         int j = returnDataResultSet.getColumnInt(b2, 1);
/* 1585 */         if (str.equals(paramString1) && j > 0) {
/* 1586 */           Integer integer = new Integer(j);
/* 1587 */           if (!paramVector5.contains(integer)) {
/* 1588 */             paramVector5.add(integer);
/*      */           }
/*      */         } else {
/* 1591 */           addDebug("getMatchingTextAndFlagIds skipping strEntityType " + str + " iEntityID " + j);
/*      */         } 
/* 1593 */         this.m_db.debug(4, "ADSIDLSTATUS.getMatchingTextAndFlagIds gbl9200:answer:" + str + ":" + j);
/*      */       } 
/*      */     } finally {
/*      */       
/* 1597 */       this.m_db.commit();
/* 1598 */       this.m_db.freeStatement();
/* 1599 */       this.m_db.isPending();
/*      */       
/* 1601 */       D.ebug(4, "ADSIDLSTATUS cleanup session id's: " + i);
/* 1602 */       byte b = 3;
/*      */       do {
/* 1604 */         returnStatus = new ReturnStatus(-1);
/* 1605 */         this.m_db.callGBL8105(returnStatus, i);
/* 1606 */         this.m_db.commit();
/* 1607 */         this.m_db.freeStatement();
/* 1608 */         this.m_db.isPending();
/* 1609 */         if (returnStatus.intValue() == 0)
/* 1610 */           continue;  D.ebug(3, "GBL8105 did not return SP_OK");
/*      */         try {
/* 1612 */           Thread.sleep(1000L);
/* 1613 */         } catch (InterruptedException interruptedException) {
/* 1614 */           D.ebug(3, interruptedException.getMessage());
/*      */         }
/*      */       
/* 1617 */       } while (returnStatus.intValue() != 0 && b-- > 0);
/*      */     } 
/*      */     
/* 1620 */     addDebug("getMatchingTextAndFlagIds domain " + paramString2 + " entitytype " + paramString1 + " idVct.cnt: " + paramVector5.size());
/* 1621 */     addDebug(2, "getMatchingTextAndFlagIds entitytype " + paramString1 + " idVct: " + paramVector5);
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
/*      */   private String getFilteredDateSql(String paramString1, String paramString2, String paramString3, Vector<E> paramVector, String paramString4) {
/* 1841 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 1842 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1843 */       if (stringBuffer1.length() > 0) {
/* 1844 */         stringBuffer1.append(',');
/*      */       }
/* 1846 */       stringBuffer1.append("'" + paramVector.elementAt(b).toString() + "'");
/*      */     } 
/* 1848 */     StringBuffer stringBuffer2 = new StringBuffer("select distinct mdl.entityid from opicm.entity mdl ");
/* 1849 */     stringBuffer2.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
/* 1850 */     stringBuffer2.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
/* 1851 */     stringBuffer2.append("where mdl.entitytype='" + paramString1 + "' ");
/* 1852 */     stringBuffer2.append("and mdl.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1853 */     stringBuffer2.append("and mdl.valto>current timestamp ");
/* 1854 */     stringBuffer2.append("and mdl.effto>current timestamp ");
/* 1855 */     stringBuffer2.append("and f.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1856 */     stringBuffer2.append("and f.valto>current timestamp ");
/* 1857 */     stringBuffer2.append("and f.effto>current timestamp ");
/* 1858 */     stringBuffer2.append("and f.attributecode='PDHDOMAIN' ");
/* 1859 */     stringBuffer2.append("and f.attributevalue in (" + stringBuffer1.toString() + ") ");
/* 1860 */     stringBuffer2.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1861 */     stringBuffer2.append("and f1.valto>current timestamp ");
/* 1862 */     stringBuffer2.append("and f1.effto>current timestamp ");
/* 1863 */     stringBuffer2.append("and f1.attributecode='STATUS' ");
/* 1864 */     stringBuffer2.append("and f1.attributevalue <> '0010' ");
/* 1865 */     if (paramString4 != null) {
/* 1866 */       stringBuffer2.append("and f1.valfrom>'" + paramString4 + "' ");
/*      */     }
/* 1868 */     stringBuffer2.append("and not exists ");
/* 1869 */     stringBuffer2.append("(select t.entityid from opicm.text t where ");
/* 1870 */     stringBuffer2.append("t.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1871 */     stringBuffer2.append("and t.entitytype='" + paramString1 + "' ");
/* 1872 */     stringBuffer2.append("and t.entityid=mdl.entityid ");
/* 1873 */     stringBuffer2.append("and t.attributecode='" + paramString2 + "' ");
/* 1874 */     stringBuffer2.append("and t.valto>current timestamp ");
/* 1875 */     stringBuffer2.append("and t.effto>current timestamp) ");
/* 1876 */     stringBuffer2.append("union ");
/* 1877 */     stringBuffer2.append("select distinct mdl.entityid from opicm.entity mdl ");
/* 1878 */     stringBuffer2.append("join opicm.text t on t.entitytype=mdl.entitytype and t.entityid=mdl.entityid ");
/* 1879 */     stringBuffer2.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
/* 1880 */     stringBuffer2.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
/* 1881 */     stringBuffer2.append("where mdl.entitytype='" + paramString1 + "' ");
/* 1882 */     stringBuffer2.append("and mdl.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1883 */     stringBuffer2.append("and mdl.valto>current timestamp ");
/* 1884 */     stringBuffer2.append("and mdl.effto>current timestamp ");
/* 1885 */     stringBuffer2.append("and f.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1886 */     stringBuffer2.append("and f.valto>current timestamp ");
/* 1887 */     stringBuffer2.append("and f.effto>current timestamp ");
/* 1888 */     stringBuffer2.append("and f.attributecode='PDHDOMAIN' ");
/* 1889 */     stringBuffer2.append("and f.attributevalue in (" + stringBuffer1.toString() + ") ");
/* 1890 */     stringBuffer2.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1891 */     stringBuffer2.append("and f1.valto>current timestamp ");
/* 1892 */     stringBuffer2.append("and f1.effto>current timestamp ");
/* 1893 */     stringBuffer2.append("and f1.attributecode='STATUS' ");
/* 1894 */     stringBuffer2.append("and f1.attributevalue <> '0010' ");
/* 1895 */     if (paramString4 != null) {
/* 1896 */       stringBuffer2.append("and f1.valfrom>'" + paramString4 + "' ");
/*      */     }
/* 1898 */     stringBuffer2.append("and t.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1899 */     stringBuffer2.append("and t.attributecode='" + paramString2 + "' ");
/* 1900 */     stringBuffer2.append("and t.valto>current timestamp ");
/* 1901 */     stringBuffer2.append("and t.effto>current timestamp ");
/* 1902 */     stringBuffer2.append("and t.attributevalue>='" + paramString3 + "' with ur");
/*      */     
/* 1904 */     return stringBuffer2.toString();
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
/*      */   private String getFilteredSql(String paramString1, Vector<E> paramVector, String paramString2) {
/* 1921 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 1922 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1923 */       if (stringBuffer1.length() > 0) {
/* 1924 */         stringBuffer1.append(',');
/*      */       }
/* 1926 */       stringBuffer1.append("'" + paramVector.elementAt(b).toString() + "'");
/*      */     } 
/* 1928 */     StringBuffer stringBuffer2 = new StringBuffer("select distinct mdl.entityid from opicm.entity mdl ");
/* 1929 */     stringBuffer2.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
/* 1930 */     stringBuffer2.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
/* 1931 */     stringBuffer2.append("where mdl.entitytype='" + paramString1 + "' ");
/* 1932 */     stringBuffer2.append("and mdl.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1933 */     stringBuffer2.append("and mdl.valto>current timestamp ");
/* 1934 */     stringBuffer2.append("and mdl.effto>current timestamp ");
/* 1935 */     stringBuffer2.append("and f.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1936 */     stringBuffer2.append("and f.valto>current timestamp ");
/* 1937 */     stringBuffer2.append("and f.effto>current timestamp ");
/* 1938 */     stringBuffer2.append("and f.attributecode='PDHDOMAIN' ");
/* 1939 */     stringBuffer2.append("and f.attributevalue in (" + stringBuffer1.toString() + ") ");
/* 1940 */     stringBuffer2.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 1941 */     stringBuffer2.append("and f1.valto>current timestamp ");
/* 1942 */     stringBuffer2.append("and f1.effto>current timestamp ");
/* 1943 */     stringBuffer2.append("and f1.attributecode='STATUS' ");
/* 1944 */     stringBuffer2.append("and f1.attributevalue <> '0010' ");
/* 1945 */     if (paramString2 != null) {
/* 1946 */       stringBuffer2.append("and f1.valfrom>'" + paramString2 + "' ");
/*      */     }
/* 1948 */     stringBuffer2.append(" with ur");
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
/* 1991 */     return stringBuffer2.toString();
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
/*      */   private String getFilteredREFOFERFEATDateSql(String paramString1, String paramString2, String paramString3, Vector<E> paramVector, String paramString4) {
/* 2009 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 2010 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 2011 */       if (stringBuffer1.length() > 0) {
/* 2012 */         stringBuffer1.append(',');
/*      */       }
/* 2014 */       stringBuffer1.append("'" + paramVector.elementAt(b).toString() + "'");
/*      */     } 
/* 2016 */     StringBuffer stringBuffer2 = new StringBuffer("select distinct reffeature.entityid from opicm.entity reffeature ");
/* 2017 */     stringBuffer2.append("join opicm.flag f on reffeature.entitytype=f.entitytype and reffeature.entityid=f.entityid ");
/* 2018 */     stringBuffer2.append("join opicm.flag f1 on reffeature.entitytype=f1.entitytype and reffeature.entityid=f1.entityid ");
/* 2019 */     stringBuffer2.append("join opicm.relator r on reffeature.entitytype=r.entity2type and reffeature.entityid=r.entity2id ");
/* 2020 */     stringBuffer2.append("where reffeature.entitytype='" + paramString1 + "' ");
/* 2021 */     stringBuffer2.append("and reffeature.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2022 */     stringBuffer2.append("and reffeature.valto>current timestamp ");
/* 2023 */     stringBuffer2.append("and reffeature.effto>current timestamp ");
/* 2024 */     stringBuffer2.append("and f.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2025 */     stringBuffer2.append("and f.valto>current timestamp ");
/* 2026 */     stringBuffer2.append("and f.effto>current timestamp ");
/* 2027 */     stringBuffer2.append("and f.attributecode='PDHDOMAIN' ");
/* 2028 */     stringBuffer2.append("and f.attributevalue in (" + stringBuffer1.toString() + ") ");
/* 2029 */     stringBuffer2.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2030 */     stringBuffer2.append("and f1.valto>current timestamp ");
/* 2031 */     stringBuffer2.append("and f1.effto>current timestamp ");
/* 2032 */     stringBuffer2.append("and f1.attributecode='STATUS' ");
/* 2033 */     stringBuffer2.append("and f1.attributevalue <> '0010' ");
/* 2034 */     stringBuffer2.append("and r.enterprise ='" + this.m_prof.getEnterprise() + "' ");
/* 2035 */     stringBuffer2.append("and r.entitytype='REFOFERREFOFERFEAT' ");
/* 2036 */     stringBuffer2.append("and r.valto>current timestamp ");
/* 2037 */     stringBuffer2.append("and r.effto>current timestamp ");
/* 2038 */     if (paramString4 != null) {
/* 2039 */       stringBuffer2.append("and f1.valfrom>'" + paramString4 + "' ");
/*      */     }
/* 2041 */     stringBuffer2.append("and not exists ");
/* 2042 */     stringBuffer2.append("(select distinct t.entityid from opicm.text t where ");
/* 2043 */     stringBuffer2.append("t.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2044 */     stringBuffer2.append("and t.entitytype=r.entity1type ");
/* 2045 */     stringBuffer2.append("and t.entityid=r.entity1id ");
/* 2046 */     stringBuffer2.append("and t.attributecode='" + paramString2 + "' ");
/* 2047 */     stringBuffer2.append("and t.valto>current timestamp ");
/* 2048 */     stringBuffer2.append("and t.effto>current timestamp) ");
/* 2049 */     stringBuffer2.append("union ");
/* 2050 */     stringBuffer2.append("select distinct reffeature.entityid from opicm.entity reffeature ");
/* 2051 */     stringBuffer2.append("join opicm.flag f on reffeature.entitytype=f.entitytype and reffeature.entityid=f.entityid ");
/* 2052 */     stringBuffer2.append("join opicm.flag f1 on reffeature.entitytype=f1.entitytype and reffeature.entityid=f1.entityid ");
/* 2053 */     stringBuffer2.append("join opicm.relator r on reffeature.entitytype=r.entity2type and reffeature.entityid=r.entity2id ");
/* 2054 */     stringBuffer2.append("join opicm.text t on t.entitytype=r.entity1type and t.entityid=r.entity1id ");
/* 2055 */     stringBuffer2.append("where reffeature.entitytype='" + paramString1 + "' ");
/* 2056 */     stringBuffer2.append("and reffeature.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2057 */     stringBuffer2.append("and reffeature.valto>current timestamp ");
/* 2058 */     stringBuffer2.append("and reffeature.effto>current timestamp ");
/* 2059 */     stringBuffer2.append("and f.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2060 */     stringBuffer2.append("and f.valto>current timestamp ");
/* 2061 */     stringBuffer2.append("and f.effto>current timestamp ");
/* 2062 */     stringBuffer2.append("and f.attributecode='PDHDOMAIN' ");
/* 2063 */     stringBuffer2.append("and f.attributevalue in (" + stringBuffer1.toString() + ") ");
/* 2064 */     stringBuffer2.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2065 */     stringBuffer2.append("and f1.valto>current timestamp ");
/* 2066 */     stringBuffer2.append("and f1.effto>current timestamp ");
/* 2067 */     stringBuffer2.append("and f1.attributecode='STATUS' ");
/* 2068 */     stringBuffer2.append("and f1.attributevalue <> '0010' ");
/* 2069 */     stringBuffer2.append("and r.enterprise ='" + this.m_prof.getEnterprise() + "' ");
/* 2070 */     stringBuffer2.append("and r.entitytype='REFOFERREFOFERFEAT' ");
/* 2071 */     stringBuffer2.append("and r.valto>current timestamp ");
/* 2072 */     stringBuffer2.append("and r.effto>current timestamp ");
/* 2073 */     if (paramString4 != null) {
/* 2074 */       stringBuffer2.append("and f1.valfrom>'" + paramString4 + "' ");
/*      */     }
/* 2076 */     stringBuffer2.append("and t.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2077 */     stringBuffer2.append("and t.attributecode='" + paramString2 + "' ");
/* 2078 */     stringBuffer2.append("and t.valto>current timestamp ");
/* 2079 */     stringBuffer2.append("and t.effto>current timestamp ");
/* 2080 */     stringBuffer2.append("and t.attributevalue>='" + paramString3 + "' with ur");
/* 2081 */     return stringBuffer2.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private String getFilteredModelSql(String paramString1, Vector<E> paramVector, String paramString2) {
/* 2086 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 2087 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 2088 */       if (stringBuffer1.length() > 0) {
/* 2089 */         stringBuffer1.append(',');
/*      */       }
/* 2091 */       stringBuffer1.append("'" + paramVector.elementAt(b).toString() + "'");
/*      */     } 
/* 2093 */     StringBuffer stringBuffer2 = new StringBuffer("select distinct mdl.entityid from opicm.entity mdl ");
/* 2094 */     stringBuffer2.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
/* 2095 */     stringBuffer2.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
/* 2096 */     stringBuffer2.append("join opicm.flag f2 on mdl.entitytype=f2.entitytype and mdl.entityid=f2.entityid and f2.attributecode = 'COFCAT' ");
/* 2097 */     stringBuffer2.append("join opicm.flag f3 on mdl.entitytype=f3.entitytype and mdl.entityid=f3.entityid and f3.attributecode = 'COFSUBCAT' ");
/* 2098 */     stringBuffer2.append("join opicm.flag f4 on mdl.entitytype=f4.entitytype and mdl.entityid=f4.entityid and f4.attributecode = 'COFGRP' ");
/* 2099 */     stringBuffer2.append("join opicm.filter_model filter on (f2.attributevalue = filter.cofcat or filter.cofcat = '*') and (f3.attributevalue = filter.cofsubcat or filter.cofsubcat = '*') and (f4.attributevalue = filter.cofgrp or filter.cofgrp = '*') ");
/*      */ 
/*      */     
/* 2102 */     stringBuffer2.append("where mdl.entitytype='" + paramString1 + "' ");
/* 2103 */     stringBuffer2.append("and mdl.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2104 */     stringBuffer2.append("and mdl.valto>current timestamp ");
/* 2105 */     stringBuffer2.append("and mdl.effto>current timestamp ");
/* 2106 */     stringBuffer2.append("and f.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2107 */     stringBuffer2.append("and f.valto>current timestamp ");
/* 2108 */     stringBuffer2.append("and f.effto>current timestamp ");
/* 2109 */     stringBuffer2.append("and f.attributecode='PDHDOMAIN' ");
/* 2110 */     stringBuffer2.append("and f.attributevalue in (" + stringBuffer1.toString() + ") ");
/* 2111 */     stringBuffer2.append("and f1.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2112 */     stringBuffer2.append("and f1.valto>current timestamp ");
/* 2113 */     stringBuffer2.append("and f1.effto>current timestamp ");
/* 2114 */     stringBuffer2.append("and f1.attributecode='STATUS' ");
/* 2115 */     stringBuffer2.append("and f1.attributevalue <> '0010' ");
/* 2116 */     stringBuffer2.append("and f2.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2117 */     stringBuffer2.append("and f2.valto>current timestamp ");
/* 2118 */     stringBuffer2.append("and f2.effto>current timestamp ");
/* 2119 */     stringBuffer2.append("and f3.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2120 */     stringBuffer2.append("and f3.valto>current timestamp ");
/* 2121 */     stringBuffer2.append("and f3.effto>current timestamp ");
/* 2122 */     stringBuffer2.append("and f4.enterprise='" + this.m_prof.getEnterprise() + "' ");
/* 2123 */     stringBuffer2.append("and f4.valto>current timestamp ");
/* 2124 */     stringBuffer2.append("and f4.effto>current timestamp ");
/* 2125 */     if (paramString2 != null) {
/* 2126 */       stringBuffer2.append("and f1.valfrom>'" + paramString2 + "' ");
/*      */     }
/* 2128 */     stringBuffer2.append(" with ur");
/* 2129 */     return stringBuffer2.toString();
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
/*      */   private Vector getMatchingIds(String paramString) throws SQLException, MiddlewareException {
/* 2364 */     Vector<Integer> vector = new Vector();
/*      */     
/* 2366 */     addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(paramString));
/* 2367 */     PreparedStatement preparedStatement = null;
/* 2368 */     ResultSet resultSet = null;
/*      */     
/*      */     try {
/* 2371 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(paramString);
/*      */       
/* 2373 */       resultSet = preparedStatement.executeQuery();
/* 2374 */       HashSet<Integer> hashSet = new HashSet();
/* 2375 */       while (resultSet.next()) {
/* 2376 */         int i = resultSet.getInt(1);
/* 2377 */         if (i > 0) {
/* 2378 */           hashSet.add(new Integer(i));
/*      */         }
/*      */       } 
/* 2381 */       vector.addAll(hashSet);
/* 2382 */       addDebug("getMatchingDateIds all matchIdVct.cnt " + vector.size());
/* 2383 */       addDebug(2, "getMatchingDateIds all matchIdVct " + vector);
/*      */     } finally {
/* 2385 */       if (resultSet != null) {
/*      */         try {
/* 2387 */           resultSet.close();
/* 2388 */         } catch (Exception exception) {
/* 2389 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*      */         } 
/* 2391 */         resultSet = null;
/*      */       } 
/*      */       
/* 2394 */       if (preparedStatement != null) {
/*      */         try {
/* 2396 */           preparedStatement.close();
/* 2397 */         } catch (Exception exception) {
/* 2398 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*      */         } 
/* 2400 */         preparedStatement = null;
/*      */       } 
/*      */       
/* 2403 */       this.m_db.commit();
/* 2404 */       this.m_db.freeStatement();
/* 2405 */       this.m_db.isPending();
/*      */     } 
/*      */     
/* 2408 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getQueuedValue(String paramString) {
/* 2416 */     return ABRServerProperties.getABRQueuedValue(this.m_abri
/* 2417 */         .getABRCode() + "_" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/* 2425 */     if (this.dbgLen + this.rptSb.length() < 5000000) {
/*      */       
/* 2427 */       BufferedInputStream bufferedInputStream = null;
/* 2428 */       FileInputStream fileInputStream = null;
/* 2429 */       BufferedReader bufferedReader = null;
/*      */       try {
/* 2431 */         fileInputStream = new FileInputStream(this.dbgfn);
/* 2432 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/* 2434 */         String str = null;
/* 2435 */         StringBuffer stringBuffer = new StringBuffer();
/* 2436 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/* 2438 */         while ((str = bufferedReader.readLine()) != null) {
/* 2439 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/* 2441 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/* 2444 */         File file = new File(this.dbgfn);
/* 2445 */         if (file.exists()) {
/* 2446 */           file.delete();
/*      */         }
/* 2448 */       } catch (Exception exception) {
/* 2449 */         exception.printStackTrace();
/*      */       } finally {
/* 2451 */         if (bufferedInputStream != null) {
/*      */           try {
/* 2453 */             bufferedInputStream.close();
/* 2454 */           } catch (Exception exception) {
/* 2455 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/* 2458 */         if (fileInputStream != null) {
/*      */           try {
/* 2460 */             fileInputStream.close();
/* 2461 */           } catch (Exception exception) {
/* 2462 */             exception.printStackTrace();
/*      */           } 
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
/*      */   private String getLD_Value(EntityItem paramEntityItem, String paramString) {
/* 2475 */     return PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString) + ": " + 
/* 2476 */       PokUtils.getAttributeValue(paramEntityItem, paramString, ",", "<em>** Not Populated **</em>", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 2483 */     super.dereference();
/*      */     
/* 2485 */     this.rsBundle = null;
/* 2486 */     this.rptSb = null;
/* 2487 */     this.args = null;
/*      */     
/* 2489 */     this.metaTbl = null;
/* 2490 */     this.navName = null;
/* 2491 */     this.vctReturnsEntityKeys.clear();
/* 2492 */     this.vctReturnsEntityKeys = null;
/*      */     
/* 2494 */     this.vctReturnsQueueKeys.clear();
/* 2495 */     this.vctReturnsQueueKeys = null;
/*      */     
/* 2497 */     this.dbgPw = null;
/* 2498 */     this.dbgfn = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 2504 */     return "$Revision: 1.27 $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 2511 */     return "ADSIDLSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 2517 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 2524 */     this.dbgLen += paramString.length();
/* 2525 */     this.dbgPw.println(paramString);
/* 2526 */     this.dbgPw.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(int paramInt, String paramString) {
/* 2535 */     if (paramInt <= this.abr_debuglvl) {
/* 2536 */       addDebug(paramString);
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
/*      */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 2548 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 2551 */     addMessage(this.rsBundle.getString("ERROR_PREFIX"), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 2557 */     addOutput(paramString);
/* 2558 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getSPLimit() {
/* 2567 */     String str = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_splimit", "200000");
/*      */ 
/*      */     
/* 2570 */     return Integer.parseInt(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isSampleMode() {
/* 2578 */     return Boolean.valueOf(ABRServerProperties.getValue(this.m_abri.getABRCode(), "_sampleMode", "false"))
/* 2579 */       .booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 2587 */     String str = this.rsBundle.getString(paramString2);
/*      */     
/* 2589 */     if (paramArrayOfObject != null) {
/* 2590 */       MessageFormat messageFormat = new MessageFormat(str);
/* 2591 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 2594 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 2604 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 2608 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 2609 */     if (eANList == null) {
/* 2610 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 2611 */       eANList = entityGroup.getMetaAttribute();
/* 2612 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 2614 */     for (byte b = 0; b < eANList.size(); b++) {
/* 2615 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 2616 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 2617 */       if (b + 1 < eANList.size()) {
/* 2618 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 2622 */     return stringBuffer.toString().trim();
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
/*      */   private void setValues(String[] paramArrayOfString, String paramString1, String paramString2, int paramInt) {
/* 2635 */     if (this.m_cbOn == null) {
/* 2636 */       setControlBlock();
/*      */     }
/*      */     
/* 2639 */     ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramString2, paramInt, true);
/* 2640 */     Vector<SingleFlag> vector = new Vector();
/* 2641 */     returnEntityKey.m_vctAttributes = vector;
/* 2642 */     this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */ 
/*      */ 
/*      */     
/* 2646 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramString2, paramInt, "XMLIDLABRSTATUS", paramString1, 1, this.m_cbOn);
/*      */ 
/*      */     
/* 2649 */     singleFlag.setDeferredPost(true);
/*      */     
/* 2651 */     vector.addElement(singleFlag);
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
/*      */   private void setQueueValues(String paramString, int paramInt) {
/* 2672 */     if (this.m_cbOn == null) {
/* 2673 */       setControlBlock();
/*      */     }
/*      */     
/* 2676 */     ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramString, paramInt, true);
/* 2677 */     Vector<SingleFlag> vector = new Vector();
/* 2678 */     returnEntityKey.m_vctAttributes = vector;
/* 2679 */     this.vctReturnsQueueKeys.addElement(returnEntityKey);
/*      */ 
/*      */ 
/*      */     
/* 2683 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramString, paramInt, "SYSFEEDRESEND", "CUR", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2688 */     vector.addElement(singleFlag);
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
/*      */   private void updatePDH() throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 2710 */     int i = this.vctReturnsEntityKeys.size();
/* 2711 */     logMessage(getDescription() + " updating PDH with " + this.vctReturnsEntityKeys.size() + " entitykeys");
/* 2712 */     addDebug("updatePDH entered for vctReturnsEntityKeys: " + i);
/* 2713 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */       try {
/* 2715 */         if (isSampleMode()) {
/* 2716 */           addOutput("<b>WARNING: Running in sample mode, not queueing all entities!</b>");
/* 2717 */           ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.firstElement();
/* 2718 */           addOutput("<b>WARNING: Queued " + returnEntityKey.getEntityType() + returnEntityKey.getEntityID() + "</b>");
/* 2719 */           Vector<ReturnEntityKey> vector = new Vector(1);
/* 2720 */           vector.add(returnEntityKey);
/* 2721 */           this.m_db.update(this.m_prof, vector, false, false);
/* 2722 */           vector.clear();
/*      */         } else {
/* 2724 */           this.m_db.update(this.m_prof, this.vctReturnsEntityKeys, false, false);
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/* 2729 */           ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.firstElement();
/* 2730 */           if (returnEntityKey.m_vctAttributes.size() == 1) {
/* 2731 */             Attribute attribute = returnEntityKey.m_vctAttributes.elementAt(0);
/*      */             
/* 2733 */             this.args[0] = attribute.getAttributeCode();
/* 2734 */             this.args[1] = attribute.getAttributeValue();
/* 2735 */             this.args[2] = "" + i;
/* 2736 */             this.args[3] = returnEntityKey.getEntityType();
/* 2737 */             addMessage("", "ATTRS_SET", this.args);
/*      */           } else {
/* 2739 */             addDebug("no attribute value update!");
/*      */           }
/*      */         
/* 2742 */         } catch (Exception exception) {
/* 2743 */           exception.printStackTrace();
/* 2744 */           addDebug("exception trying to output msg " + exception);
/*      */         } 
/*      */       } finally {
/*      */         
/* 2748 */         this.vctReturnsEntityKeys.clear();
/* 2749 */         this.m_db.commit();
/* 2750 */         this.m_db.freeStatement();
/* 2751 */         this.m_db.isPending("finally after updatePDH");
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
/*      */   private void updatePDHQueue() throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 2767 */     int i = this.vctReturnsQueueKeys.size();
/* 2768 */     logMessage(getDescription() + " updating PDH with " + this.vctReturnsQueueKeys.size() + " entitykeys");
/* 2769 */     addDebug("updatePDH entered for vctReturnsQueueKeys: " + i);
/* 2770 */     if (this.vctReturnsQueueKeys.size() > 0) {
/*      */       try {
/* 2772 */         if (isSampleMode()) {
/* 2773 */           addOutput("<b>WARNING: Running in sample mode, not queueing all entities!</b>");
/* 2774 */           ReturnEntityKey returnEntityKey = this.vctReturnsQueueKeys.firstElement();
/* 2775 */           addOutput("<b>WARNING: Queued " + returnEntityKey.getEntityType() + returnEntityKey.getEntityID() + "</b>");
/* 2776 */           Vector<ReturnEntityKey> vector = new Vector(1);
/* 2777 */           vector.add(returnEntityKey);
/* 2778 */           this.m_db.update(this.m_prof, vector, false, false);
/* 2779 */           vector.clear();
/*      */         } else {
/* 2781 */           this.m_db.update(this.m_prof, this.vctReturnsQueueKeys, false, false);
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/* 2786 */           ReturnEntityKey returnEntityKey = this.vctReturnsQueueKeys.firstElement();
/* 2787 */           if (returnEntityKey.m_vctAttributes.size() == 1) {
/* 2788 */             Attribute attribute = returnEntityKey.m_vctAttributes.elementAt(0);
/*      */             
/* 2790 */             this.args[0] = attribute.getAttributeCode();
/* 2791 */             this.args[1] = attribute.getAttributeValue();
/* 2792 */             this.args[2] = "" + i;
/* 2793 */             this.args[3] = returnEntityKey.getEntityType();
/* 2794 */             addMessage("", "ATTRS_SET", this.args);
/*      */           } else {
/* 2796 */             addDebug("no attribute value update!");
/*      */           }
/*      */         
/* 2799 */         } catch (Exception exception) {
/* 2800 */           exception.printStackTrace();
/* 2801 */           addDebug("exception trying to output msg " + exception);
/*      */         } 
/*      */       } finally {
/*      */         
/* 2805 */         this.vctReturnsQueueKeys.clear();
/* 2806 */         this.m_db.commit();
/* 2807 */         this.m_db.freeStatement();
/* 2808 */         this.m_db.isPending("finally after updatePDH");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setTextValue(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 2817 */     log(" ADSIDLSTATUS ***** " + paramString1 + " set to: " + paramString2);
/* 2818 */     log("setTextValue entered for " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */     
/* 2821 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 2822 */     if (eANMetaAttribute == null) {
/* 2823 */       log("setTextValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 2824 */       log("ADSIDLSTATUS ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 2825 */           .getEntityType() + ", nothing to do");
/*      */       return;
/*      */     } 
/* 2828 */     if (paramString2 != null)
/*      */       
/*      */       try {
/* 2831 */         if (this.m_cbOn == null) {
/* 2832 */           setControlBlock();
/*      */         }
/*      */         
/* 2835 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*      */ 
/*      */         
/* 2838 */         Text text = new Text(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */         
/* 2842 */         Vector<Text> vector = new Vector();
/* 2843 */         Vector<ReturnEntityKey> vector1 = new Vector();
/* 2844 */         vector.addElement(text);
/* 2845 */         returnEntityKey.m_vctAttributes = vector;
/* 2846 */         vector1.addElement(returnEntityKey);
/*      */         
/* 2848 */         this.m_db.update(this.m_prof, vector1, false, false);
/* 2849 */         addDebug(paramEntityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*      */       } finally {
/*      */         
/* 2852 */         this.m_db.commit();
/* 2853 */         this.m_db.freeStatement();
/* 2854 */         this.m_db.isPending("finally after update in setTextValue ");
/*      */       }  
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\ADSIDLSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */