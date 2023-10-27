/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MODELABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*  147 */   private Vector mdlPlaAvailVctA = null;
/*      */ 
/*      */ 
/*      */   
/*  151 */   private Hashtable mdlPlaAvailCtryTblA = null;
/*      */   
/*  153 */   private Hashtable mdlLOAvailCtryTblC = null;
/*      */   
/*  155 */   private Vector mdlFOAvailVctB = null;
/*      */   
/*  157 */   private Vector mdlLOAvailVctC = null;
/*      */ 
/*      */ 
/*      */   
/*  161 */   private Vector mdlEOMAvailVctP = null;
/*      */   
/*  163 */   private Vector mdlEOSAvailVctR = null;
/*      */   
/*  165 */   private Vector psPlaAvailVctD = null;
/*      */ 
/*      */   
/*  168 */   private Vector psMesPlaAvailVctD = null;
/*      */ 
/*      */   
/*  171 */   private Vector psLOAvailVctE = null;
/*      */   
/*  173 */   private Vector psMesLOAvailVctE = null;
/*      */ 
/*      */ 
/*      */   
/*  177 */   private Vector swpsPlaAvailVctH = null;
/*      */ 
/*      */ 
/*      */   
/*  181 */   private Vector swpsMesPlaAvailVctH = null;
/*      */ 
/*      */ 
/*      */   
/*  185 */   private Vector swpsLOAvailVctK = null;
/*      */ 
/*      */   
/*  188 */   private Vector swpsMesLOAvailVctK = null;
/*      */ 
/*      */   
/*      */   private static final String FEATINDC_No = "FEATN";
/*      */ 
/*      */   
/*      */   private static final String ORDERCODE_INITIAL = "5957";
/*      */ 
/*      */   
/*      */   private static final String WARRSVCCOVR_NOWARR = "WSVC01";
/*      */ 
/*      */   
/*      */   private static final Hashtable ATTR_OF_INTEREST_TBL;
/*      */   
/*      */   private static final String reg = "^\\d+$";
/*      */   
/*  204 */   private static Pattern pattern = Pattern.compile("^\\d+$");
/*  205 */   private static Set set = new HashSet();
/*      */   
/*      */   static {
/*  208 */     set.add("MSWH");
/*  209 */     set.add("MAIN");
/*  210 */     set.add("CABL");
/*  211 */     set.add("PMG");
/*  212 */     set.add("MANL");
/*  213 */     set.add("MATM");
/*  214 */     set.add("PLA");
/*  215 */     set.add("ALP");
/*  216 */     set.add("CSW");
/*  217 */     set.add("EDUC");
/*  218 */     set.add("MNPM");
/*  219 */     set.add("***");
/*      */ 
/*      */     
/*  222 */     ATTR_OF_INTEREST_TBL = new Hashtable<>();
/*  223 */     ATTR_OF_INTEREST_TBL.put("MODELWARR", new String[] { "EFFECTIVEDATE", "ENDDATE", "COUNTRYLIST", "DEFWARR" });
/*      */   }
/*      */   
/*      */   private boolean IMGUniqueCoverageChkDone = false;
/*  227 */   private EntityList lseoList = null;
/*      */   
/*      */   public void dereference() {
/*  230 */     super.dereference();
/*  231 */     if (this.lseoList != null) {
/*  232 */       this.lseoList.dereference();
/*  233 */       this.lseoList = null;
/*      */     } 
/*      */     
/*  236 */     if (this.mdlPlaAvailVctA != null) {
/*  237 */       this.mdlPlaAvailVctA.clear();
/*  238 */       this.mdlPlaAvailVctA = null;
/*      */     } 
/*  240 */     if (this.mdlPlaAvailCtryTblA != null) {
/*  241 */       this.mdlPlaAvailCtryTblA.clear();
/*  242 */       this.mdlPlaAvailCtryTblA = null;
/*      */     } 
/*  244 */     if (this.mdlFOAvailVctB != null) {
/*  245 */       this.mdlFOAvailVctB.clear();
/*  246 */       this.mdlFOAvailVctB = null;
/*      */     } 
/*  248 */     if (this.mdlLOAvailVctC != null) {
/*  249 */       this.mdlLOAvailVctC.clear();
/*  250 */       this.mdlLOAvailVctC = null;
/*      */     } 
/*      */     
/*  253 */     if (this.mdlEOMAvailVctP != null) {
/*  254 */       this.mdlEOMAvailVctP.clear();
/*  255 */       this.mdlEOMAvailVctP = null;
/*      */     } 
/*  257 */     if (this.mdlEOSAvailVctR != null) {
/*  258 */       this.mdlEOSAvailVctR.clear();
/*  259 */       this.mdlEOSAvailVctR = null;
/*      */     } 
/*      */     
/*  262 */     if (this.mdlLOAvailCtryTblC != null) {
/*  263 */       this.mdlLOAvailCtryTblC.clear();
/*  264 */       this.mdlLOAvailCtryTblC = null;
/*      */     } 
/*  266 */     if (this.swpsPlaAvailVctH != null) {
/*  267 */       this.swpsPlaAvailVctH.clear();
/*  268 */       this.swpsPlaAvailVctH = null;
/*      */     } 
/*  270 */     if (this.swpsLOAvailVctK != null) {
/*  271 */       this.swpsLOAvailVctK.clear();
/*  272 */       this.swpsLOAvailVctK = null;
/*      */     } 
/*      */     
/*  275 */     if (this.psPlaAvailVctD != null) {
/*  276 */       this.psPlaAvailVctD.clear();
/*  277 */       this.psPlaAvailVctD = null;
/*      */     } 
/*  279 */     if (this.psLOAvailVctE != null) {
/*  280 */       this.psLOAvailVctE.clear();
/*  281 */       this.psLOAvailVctE = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  290 */     return true;
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
/*      */   protected String getVEName(String paramString1, String paramString2) {
/*  302 */     if (paramString1.equals("0020")) {
/*  303 */       addDebug("Status already final, use diff ve");
/*  304 */       return "EXRPT3MODEL2";
/*  305 */     }  if (paramString1.equals("0040") && paramString2.equals("REVIEW")) {
/*  306 */       addDebug("Status already rfr, use diff ve");
/*  307 */       return "EXRPT3MODEL2";
/*      */     } 
/*  309 */     return this.m_abri.getVEName();
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
/*      */   protected void doAlreadyFinalProcessing(EntityItem paramEntityItem) throws Exception {
/*  327 */     if (doDARULEs()) {
/*  328 */       boolean bool = updateDerivedData(paramEntityItem);
/*  329 */       addDebug("doAlreadyFinalProcessing: " + paramEntityItem.getKey() + " chgsMade " + bool);
/*  330 */       if (bool) {
/*  331 */         boolean bool1 = isOldData(paramEntityItem, "ANNDATE");
/*  332 */         boolean bool2 = domainInRuleList(paramEntityItem, "XCC_LIST");
/*  333 */         addDebug("doAlreadyFinalProcessing: " + paramEntityItem.getKey() + " domain in XCCLIST " + bool2 + " olddata " + bool1);
/*      */ 
/*      */ 
/*      */         
/*  337 */         if (!bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  350 */           doAlreadyFinalADSProcessing(paramEntityItem);
/*      */         }
/*      */         else {
/*      */           
/*  354 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", 
/*  355 */               getQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */ 
/*      */           
/*  358 */           setFlagValue(this.m_elist.getProfile(), "RFCABRSTATUS", 
/*  359 */               getQueuedValueForItem(paramEntityItem, "RFCABRSTATUS"), paramEntityItem);
/*      */         } 
/*      */       } else {
/*      */         
/*  363 */         this.args[0] = this.m_elist.getEntityGroup("CATDATA").getLongDescription();
/*  364 */         addResourceMsg("NO_CHGSFOUND", this.args);
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
/*      */ 
/*      */   
/*      */   private void doAlreadyFinalADSProcessing(EntityItem paramEntityItem) throws MiddlewareRequestException, MiddlewareException, SQLException {
/*  385 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "AVAIL"); byte b;
/*  386 */     label26: for (b = 0; b < vector.size(); b++) {
/*  387 */       EntityItem entityItem = vector.elementAt(b);
/*  388 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/*  389 */       if (str == null) {
/*  390 */         str = "RFA";
/*      */       }
/*      */ 
/*      */       
/*  394 */       addDebug("doAlreadyFinalADSProcessing: " + entityItem.getKey() + " availAnntypeFlag " + str);
/*      */       
/*  396 */       if (statusIsFinal(entityItem)) {
/*      */         
/*  398 */         if ("RFA".equals(str)) {
/*  399 */           Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*  400 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  401 */             EntityItem entityItem1 = vector1.elementAt(b1);
/*  402 */             String str1 = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/*  403 */             addDebug("doAlreadyFinalADSProcessing: " + entityItem1.getKey() + " type " + str1);
/*      */ 
/*      */             
/*  406 */             if (statusIsFinal(entityItem1, "ANNSTATUS")) {
/*      */               
/*  408 */               setSinceFirstFinal(paramEntityItem, "ADSABRSTATUS");
/*      */               
/*      */               break label26;
/*      */             } 
/*      */           } 
/*      */           
/*  414 */           vector1.clear();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  420 */         setSinceFirstFinal(paramEntityItem, "ADSABRSTATUS");
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  428 */     vector.clear();
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
/*      */   protected void doAlreadyRFRProcessing(EntityItem paramEntityItem) throws Exception {
/*  448 */     if (doDARULEs()) {
/*  449 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, "LIFECYCLE");
/*  450 */       boolean bool1 = isOldData(paramEntityItem, "ANNDATE");
/*  451 */       boolean bool2 = domainInRuleList(paramEntityItem, "XCC_LIST");
/*  452 */       addDebug("doAlreadyRFRProcessing: " + paramEntityItem.getKey() + " domain in XCCLIST " + bool2 + " olddata " + bool1 + " lifecycle " + str);
/*      */ 
/*      */       
/*  455 */       if (str == null || str.length() == 0) {
/*  456 */         str = "LF01";
/*      */       }
/*  458 */       boolean bool3 = false;
/*  459 */       if (bool1 || !bool2) {
/*      */         
/*  461 */         bool3 = updateDerivedData(paramEntityItem);
/*  462 */       } else if ("LF01".equals(str) || "LF02"
/*      */         
/*  464 */         .equals(str)) {
/*      */         
/*  466 */         bool3 = updateDerivedData(paramEntityItem);
/*      */       } 
/*      */       
/*  469 */       addDebug("doAlreadyRFRProcessing: " + paramEntityItem.getKey() + " chgsMade " + bool3);
/*  470 */       if (bool3) {
/*      */ 
/*      */         
/*  473 */         if (!bool1)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  489 */           doAlreadyRFRADSProcessing(paramEntityItem);
/*      */         }
/*      */         else
/*      */         {
/*  493 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", 
/*  494 */               getRFRQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  500 */         this.args[0] = this.m_elist.getEntityGroup("CATDATA").getLongDescription();
/*  501 */         addResourceMsg("NO_CHGSFOUND", this.args);
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
/*      */   private void doAlreadyRFRADSProcessing(EntityItem paramEntityItem) {
/*  520 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "AVAIL");
/*  521 */     for (byte b = 0; b < vector.size(); b++) {
/*  522 */       EntityItem entityItem = vector.elementAt(b);
/*  523 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/*  524 */       if (str == null) {
/*  525 */         str = "RFA";
/*      */       }
/*      */       
/*  528 */       addDebug("doAlreadyRFRADSProcessing: " + entityItem.getKey() + " availAnntypeFlag " + str);
/*      */ 
/*      */       
/*  531 */       if (statusIsRFRorFinal(entityItem)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  557 */         doRFR_MODADSXML(paramEntityItem);
/*      */         
/*  559 */         addDebug("doAlreadyRFRADSProcessing: not RFA " + paramEntityItem.getKey() + " ADSABRSTATUS is queued");
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  565 */     vector.clear();
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  634 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  636 */     boolean bool1 = isOldData(entityItem, "ANNDATE");
/*  637 */     boolean bool2 = domainInRuleList(entityItem, "XCC_LIST");
/*  638 */     addDebug("nowRFR: " + entityItem.getKey() + " domain in XCCLIST " + bool2 + " olddata " + bool1);
/*      */ 
/*      */     
/*  641 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", getRFRQueuedValue("COMPATGENABR"));
/*      */ 
/*      */     
/*  644 */     if (bool2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  655 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("WWSEO");
/*  656 */       if (entityGroup.getEntityItemCount() > 0) {
/*  657 */         Vector<EntityItem> vector = new Vector();
/*  658 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  659 */           EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*  660 */           if (statusIsRFRorFinal(entityItem1)) {
/*  661 */             vector.add(entityItem1);
/*      */           }
/*      */         } 
/*  664 */         if (vector.size() > 0) {
/*  665 */           propagateModelWarr(entityItem, vector, false);
/*  666 */           vector.clear();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  675 */     if (!bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  694 */       doR4R_RFAProcessing("MODELAVAIL");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  719 */       setQueueforOldData(entityItem);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  751 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  753 */     boolean bool1 = isOldData(entityItem, "ANNDATE");
/*  754 */     boolean bool2 = domainInRuleList(entityItem, "XCC_LIST");
/*  755 */     addDebug("nowFinal: " + entityItem.getKey() + " domain in XCCLIST " + bool2 + " olddata " + bool1);
/*      */ 
/*      */ 
/*      */     
/*  759 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", getQueuedValue("COMPATGENABR"));
/*      */     
/*  761 */     if (bool2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  767 */       checkAssortModule();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  776 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("WWSEO");
/*  777 */       if (entityGroup.getEntityItemCount() > 0) {
/*  778 */         Vector<EntityItem> vector = new Vector();
/*  779 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  780 */           EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*  781 */           if (statusIsFinal(entityItem1)) {
/*  782 */             vector.add(entityItem1);
/*      */           }
/*      */         } 
/*  785 */         if (vector.size() > 0) {
/*  786 */           propagateModelWarr(entityItem, vector, true);
/*  787 */           vector.clear();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  796 */     if (!bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  814 */       doFinalRFAProcessing("MODELAVAIL");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  839 */       setQueueforOldData(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  848 */       if (isHardwareOrHIPOModel(entityItem)) {
/*  849 */         setFlagValue(this.m_elist.getProfile(), "RFCABRSTATUS", getQueuedValueForItem(entityItem, "RFCABRSTATUS"), entityItem);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setQueueforOldData(EntityItem paramEntityItem) throws MiddlewareRequestException, MiddlewareException, SQLException {
/*  876 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "LIFECYCLE");
/*  877 */     addDebug("setQueueforOldData: " + paramEntityItem.getKey() + " lifecycle " + str);
/*  878 */     if (str == null || str.length() == 0) {
/*  879 */       str = "LF01";
/*      */     }
/*      */ 
/*      */     
/*  883 */     if ("LF01".equals(str) || "LF02"
/*  884 */       .equals(str)) {
/*  885 */       setRFRSinceFirstRFR(paramEntityItem);
/*      */     } else {
/*  887 */       setSinceFirstFinal(paramEntityItem, "ADSABRSTATUS");
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
/*      */   private void doFinalRFAProcessing(String paramString) throws MiddlewareRequestException, MiddlewareException, SQLException {
/*  910 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  912 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, paramString, "AVAIL");
/*      */     
/*  914 */     for (byte b = 0; b < vector.size(); b++) {
/*  915 */       EntityItem entityItem1 = vector.elementAt(b);
/*  916 */       String str = PokUtils.getAttributeFlagValue(entityItem1, "AVAILANNTYPE");
/*  917 */       if (str == null) {
/*  918 */         str = "RFA";
/*      */       }
/*      */ 
/*      */       
/*  922 */       addDebug("doFinal_RFAProcessing: " + entityItem1.getKey() + " availAnntypeFlag " + str);
/*      */       
/*  924 */       if (statusIsFinal(entityItem1)) {
/*  925 */         boolean bool = false;
/*      */         
/*  927 */         if ("RFA".equals(str)) {
/*  928 */           Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "AVAILANNA", "ANNOUNCEMENT");
/*  929 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  930 */             EntityItem entityItem2 = vector1.elementAt(b1);
/*  931 */             String str1 = getAttributeFlagEnabledValue(entityItem2, "ANNTYPE");
/*  932 */             addDebug("doFinal_RFAProcessing: " + entityItem2.getKey() + " type " + str1);
/*      */ 
/*      */             
/*  935 */             if (statusIsFinal(entityItem2, "ANNSTATUS")) {
/*      */ 
/*      */ 
/*      */               
/*  939 */               if ("19".equals(str1) && domainInRuleList(entityItem2, "XCC_LIST")) {
/*  940 */                 addDebug(entityItem2.getKey() + " is Final and New and domain in xcclist");
/*      */ 
/*      */                 
/*  943 */                 setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", 
/*  944 */                     getQueuedValueForItem(entityItem2, "WWPRTABRSTATUS"), entityItem2);
/*      */               } 
/*  946 */               if (!bool) {
/*  947 */                 bool = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  956 */                 setSinceFirstFinal(entityItem, "ADSABRSTATUS");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  965 */                 if (isHardwareOrHIPOModel(entityItem)) {
/*  966 */                   setRFCSinceFirstFinal(entityItem, "RFCABRSTATUS");
/*      */                 }
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  974 */               if (isQsmANNTYPE(str1) && isHardwareOrHIPOModel(entityItem)) {
/*  975 */                 setFlagValue(this.m_elist.getProfile(), "QSMCREFABRSTATUS", 
/*  976 */                     getQueuedValueForItem(entityItem2, "QSMCREFABRSTATUS"), entityItem2);
/*  977 */                 setFlagValue(this.m_elist.getProfile(), "QSMFULLABRSTATUS", 
/*  978 */                     getQueuedValueForItem(entityItem2, "QSMFULLABRSTATUS"), entityItem2);
/*      */               } 
/*      */             } 
/*      */           } 
/*  982 */           vector1.clear();
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  987 */         if (!bool) {
/*  988 */           bool = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  997 */           setSinceFirstFinal(entityItem, "ADSABRSTATUS");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1005 */           if (isHardwareOrHIPOModel(entityItem)) {
/* 1006 */             setRFCSinceFirstFinal(entityItem, "RFCABRSTATUS");
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1014 */     vector.clear();
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
/*      */   private void propagateModelWarr(EntityItem paramEntityItem, Vector<EntityItem> paramVector, boolean paramBoolean) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1054 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramVector.size()];
/* 1055 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1056 */       EntityItem entityItem = paramVector.elementAt(b);
/* 1057 */       arrayOfEntityItem[b] = new EntityItem(null, this.m_prof, entityItem.getEntityType(), entityItem.getEntityID());
/*      */     } 
/*      */     
/* 1060 */     String str = "DQVEWWSEOLSEO";
/* 1061 */     this.lseoList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 1062 */           .getProfile(), str), arrayOfEntityItem);
/* 1063 */     addDebug("propagateModelWarr: strnow: " + getCurrentDate() + " Extract " + str + NEWLINE + 
/* 1064 */         PokUtils.outputList(this.lseoList));
/*      */ 
/*      */     
/* 1067 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1072 */     EntityGroup entityGroup = this.lseoList.getEntityGroup("LSEO");
/* 1073 */     if (paramBoolean) {
/*      */ 
/*      */       
/* 1076 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/* 1077 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/* 1078 */         String str1 = PokUtils.getAttributeValue(entityItem, "LSEOUNPUBDATEMTRGT", "", "9999-12-31", false);
/* 1079 */         addDebug("propagateModelWarr: " + entityItem.getKey() + " unpubdate " + str1);
/*      */ 
/*      */         
/* 1082 */         if (statusIsFinal(entityItem) && str1.compareTo(getCurrentDate()) > 0)
/*      */         {
/* 1084 */           vector.add(entityItem);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1094 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/* 1095 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/* 1096 */         String str1 = PokUtils.getAttributeValue(entityItem, "LSEOUNPUBDATEMTRGT", "", "9999-12-31", false);
/* 1097 */         addDebug("propagateModelWarr: " + entityItem.getKey() + " unpubdate " + str1);
/*      */ 
/*      */         
/* 1100 */         if (statusIsRFR(entityItem) && str1.compareTo(getCurrentDate()) > 0)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1113 */           vector.add(entityItem);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1120 */     if (vector.size() > 0) {
/* 1121 */       propagateModelWarrLseo(paramEntityItem, vector, paramBoolean);
/* 1122 */       vector.clear();
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
/*      */ 
/*      */ 
/*      */   
/*      */   private void propagateModelWarrLseo(EntityItem paramEntityItem, Vector<EntityItem> paramVector, boolean paramBoolean) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 1148 */     boolean bool = false;
/* 1149 */     String str = getPrevPassedDTS(paramEntityItem, "MODELABRSTATUS");
/* 1150 */     if (str != null)
/*      */     {
/*      */       
/* 1153 */       bool = changeOfInterest(paramEntityItem, str, this.m_elist.getProfile().getValOn(), "DQVEMODELWARR", ATTR_OF_INTEREST_TBL);
/*      */     }
/*      */     
/* 1156 */     if (bool) {
/* 1157 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 1158 */         EntityItem entityItem = paramVector.elementAt(b);
/* 1159 */         if (paramBoolean) {
/* 1160 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */         } else {
/*      */           
/* 1163 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", 
/* 1164 */               getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
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
/*      */   protected String generateString(EntityItem paramEntityItem, String[] paramArrayOfString) {
/* 1178 */     if (paramEntityItem.getEntityType().equals("MODELWARR")) {
/*      */       
/* 1180 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, "DEFWARR");
/* 1181 */       addDebug("generateString: " + paramEntityItem.getKey() + " defwarr " + str);
/* 1182 */       if ("Y1".equals(str) || str == null) {
/* 1183 */         StringBuffer stringBuffer = new StringBuffer(paramEntityItem.getKey());
/* 1184 */         for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 1185 */           String str1 = PokUtils.getAttributeValue(paramEntityItem, paramArrayOfString[b], ", ", "", false);
/* 1186 */           if ("Y1".equals(str) && paramArrayOfString[b].equals("COUNTRYLIST"))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1197 */             str1 = "DEFWARR";
/*      */           }
/* 1199 */           if (paramArrayOfString[b].equals("DEFWARR") && str == null)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1207 */             str1 = "No";
/*      */           }
/* 1209 */           stringBuffer.append(":" + str1);
/*      */         } 
/* 1211 */         return stringBuffer.toString();
/*      */       } 
/*      */     } 
/*      */     
/* 1215 */     return super.generateString(paramEntityItem, paramArrayOfString);
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 1524 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*      */     
/* 1526 */     String str1 = getAttributeFlagEnabledValue(paramEntityItem, "COFCAT");
/* 1527 */     if (str1 == null) {
/* 1528 */       str1 = "";
/*      */     }
/* 1530 */     boolean bool = "102".equals(str1);
/* 1531 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "BHPRODHIERCD");
/*      */ 
/*      */     
/* 1534 */     doModelSysteamTypeChecks(paramEntityItem, paramString);
/* 1535 */     addDebug(paramEntityItem.getKey() + " COFCAT: " + str1 + " isSvcModel " + bool + " prodhierFlag " + str2);
/*      */     
/* 1537 */     doWarrantyWTY0000(paramEntityItem);
/*      */ 
/*      */ 
/*      */     
/* 1541 */     checkCanNotBeEarlier(paramEntityItem, "WITHDRAWDATE", "ANNDATE", 4);
/*      */ 
/*      */ 
/*      */     
/* 1545 */     checkCanNotBeEarlier(paramEntityItem, "WTHDRWEFFCTVDATE", "ANNDATE", 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1564 */     int i = getCount("MODTAXRELEVANCE");
/* 1565 */     if (i == 0) {
/*      */ 
/*      */       
/* 1568 */       this.args[0] = this.m_elist.getEntityGroup("TAXCATG").getLongDescription();
/* 1569 */       createMessage(getCheck_W_W_E(paramString), "MINIMUM_ERR", this.args);
/*      */     } 
/*      */ 
/*      */     
/* 1573 */     i = getCount("MODELTAXGRP");
/* 1574 */     if (i == 0) {
/*      */ 
/*      */       
/* 1577 */       this.args[0] = this.m_elist.getEntityGroup("TAXGRP").getLongDescription();
/* 1578 */       createMessage(getCheck_W_W_E(paramString), "MINIMUM_ERR", this.args);
/*      */     } 
/*      */ 
/*      */     
/* 1582 */     getAvails(paramEntityItem, paramString, str1);
/* 1583 */     doSoftwareDataChecks(paramEntityItem, paramString, str1);
/*      */     
/* 1585 */     doModelPLAndMesPLAvailChecks("Model Planned Avail Checks:", this.mdlPlaAvailVctA, "146", paramEntityItem, paramString, str1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1591 */     doModelFOAvailChecks(paramEntityItem, paramString, str1);
/*      */ 
/*      */     
/* 1594 */     doModelLOAndMesLOAvailChecks("Model Last Order Avail Checks:", this.mdlLOAvailVctC, "149", paramEntityItem, paramString, str1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1600 */     doModelEOMAvailChecks(paramEntityItem, paramString, str1);
/*      */     
/* 1602 */     doModelEOSAvailChecks(paramEntityItem, paramString, str1);
/*      */     
/* 1604 */     if ("100".equals(str1)) {
/* 1605 */       doHardwareChecks(paramEntityItem, paramString);
/* 1606 */     } else if ("101".equals(str1)) {
/* 1607 */       doSoftwareChecks(paramEntityItem, paramString);
/* 1608 */     } else if ("102".equals(str1)) {
/* 1609 */       doServiceChecks(paramEntityItem, paramString);
/*      */     } 
/* 1611 */     int j = doCheck_N_W_E(paramString);
/*      */     
/* 1613 */     if (-1 == j) {
/* 1614 */       addDebug("other uniquecoverage checks are bypassed because status is " + paramString);
/*      */     } else {
/* 1616 */       addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Unique Coverage Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1630 */       if (this.IMGUniqueCoverageChkDone) {
/* 1631 */         checkUniqueCoverage(paramEntityItem, "MODELIMG", "IMG", this.mdlPlaAvailVctA, this.mdlLOAvailVctC, j, false);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1645 */       checkUniqueCoverage(paramEntityItem, "MODELMM", "MM", this.mdlPlaAvailVctA, this.mdlLOAvailVctC, j, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1660 */       checkUniqueCoverage(paramEntityItem, "MODELFB", "FB", this.mdlPlaAvailVctA, this.mdlLOAvailVctC, j, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void doModelSysteamTypeChecks(EntityItem paramEntityItem, String paramString) {
/* 1667 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "SYSIDUNIT", "", "");
/* 1668 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "SYSTEMTYPE", "", "");
/* 1669 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, "MACHTYPEATR", "", "");
/* 1670 */     String str4 = PokUtils.getAttributeValue(paramEntityItem, "COFCAT", "", "");
/* 1671 */     addHeading(3, "Model SysteamType Checks:");
/* 1672 */     addDebug("sysidunit=" + str1 + " systemtype=" + str2 + " cofcat=" + str4);
/* 1673 */     if ("Hardware".equals(str4) && 
/* 1674 */       "SIU-CPU".equals(str1) && (
/* 1675 */       str3 == null || str3.equals("") || !str3.equals(str2))) {
/* 1676 */       this.args[0] = "\"" + str2 + "\"";
/* 1677 */       this.args[1] = "System Type. If System Identification Unit = \"SIU-CPU\", System Type should be set to machine type.";
/* 1678 */       createMessage(getCheck_W_W_E(paramString), "INVALID_VALUES_ERR", this.args);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doWarrantyWTY0000(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1686 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Warranty Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1692 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("WARR");
/* 1693 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/* 1694 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1695 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1696 */         String str = PokUtils.getAttributeValue(entityItem, "WARRID", null, null);
/* 1697 */         if ("WTY0000".equals(str)) {
/* 1698 */           EntityItem entityItem1 = (entityItem.getUpLink().size() > 0) ? entityItem.getUpLink().get(0) : null;
/* 1699 */           if (entityItem1 != null) {
/*      */             
/* 1701 */             String str1 = PokUtils.getAttributeValue(entityItem1, "DEFWARR", null, null, true);
/* 1702 */             if (str1 == null || !str1.equals("No")) {
/* 1703 */               this.args[0] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "DEFWARR", "") + ":" + str1 + " on MODELWARR ";
/*      */ 
/*      */ 
/*      */               
/* 1707 */               this.args[1] = "Warranty ID WTY0000," + PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "DEFWARR", "") + ":No is expected";
/* 1708 */               addError("INVALID_VALUES_ERR", this.args);
/*      */             } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean updateDerivedData(EntityItem paramEntityItem) throws Exception {
/* 1769 */     boolean bool = false;
/*      */     
/* 1771 */     String str = PokUtils.getAttributeValue(paramEntityItem, "WTHDRWEFFCTVDATE", "", "9999-12-31", false);
/* 1772 */     addDebug("updateDerivedData wdDate: " + str + " now: " + getCurrentDate());
/* 1773 */     if (getCurrentDate().compareTo(str) <= 0) {
/* 1774 */       bool = execDerivedData(paramEntityItem);
/*      */     }
/* 1776 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLCRFRWFName() {
/* 1786 */     return "WFLCMODELRFR";
/*      */   }
/*      */   
/*      */   protected String getLCFinalWFName() {
/* 1790 */     return "WFLCMODELFINAL";
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
/*      */   private void doModelEOSAvailChecks(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 1836 */     int i = getCheck_W_W_E(paramString1);
/*      */     
/* 1838 */     addHeading(3, "Model End of Service Avail Checks:");
/*      */ 
/*      */     
/* 1841 */     if (this.mdlEOSAvailVctR.size() > 0) {
/* 1842 */       for (byte b1 = 0; b1 < this.mdlEOSAvailVctR.size(); b1++) {
/* 1843 */         EntityItem entityItem = this.mdlEOSAvailVctR.elementAt(b1);
/*      */ 
/*      */ 
/*      */         
/* 1847 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "WTHDRWEFFCTVDATE", i);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1855 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 1856 */       boolean bool1 = getAvailByOSN(hashtable1, this.mdlLOAvailVctC, true, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1861 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 1862 */       boolean bool2 = getAvailByOSN(hashtable2, this.mdlEOSAvailVctR, true, 3);
/* 1863 */       addDebug("mdlEOSAvailChecks eosOsnErrors " + bool2 + " eosAvailOSNTbl.keys " + hashtable2
/* 1864 */           .keySet() + " loOsnErrors " + bool1 + " loAvailOSNTbl.keys " + hashtable1
/* 1865 */           .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1873 */       if (!bool1 && !bool2)
/*      */       {
/*      */         
/* 1876 */         checkAvailCtryByOSN(hashtable2, hashtable1, "MISSING_LO_OSNCTRY_ERR", (EntityItem)null, true, 
/* 1877 */             getCheckLevel(i, paramEntityItem, "ANNDATE"));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1887 */       hashtable1.clear();
/*      */       
/* 1889 */       hashtable2.clear();
/*      */     } 
/*      */ 
/*      */     
/* 1893 */     Vector<EntityItem> vector = new Vector(this.mdlEOSAvailVctR);
/*      */     
/* 1895 */     removeNonRFAAVAIL(vector);
/*      */     
/* 1897 */     for (byte b = 0; b < vector.size(); b++) {
/* 1898 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 1900 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */       
/* 1902 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1903 */         EntityItem entityItem1 = vector1.elementAt(b1);
/*      */         
/* 1905 */         String str = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 1906 */         addDebug("mdlEOSAvailChecks " + entityItem1.getKey() + " anntypeFlag " + str);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1911 */         if (!"13".equals(str)) {
/*      */           
/* 1913 */           this.args[0] = getLD_NDN(entityItem);
/* 1914 */           this.args[1] = getLD_NDN(entityItem1);
/* 1915 */           createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1927 */           checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem, "WITHDRAWDATE", i);
/*      */ 
/*      */ 
/*      */           
/* 1931 */           checkCanNotBeLater4(entityItem1, "ANNDATE", entityItem, "EFFECTIVEDATE", i);
/*      */         } 
/*      */       } 
/* 1934 */       vector1.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1939 */     vector.clear();
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
/*      */   private void getAvails(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 1951 */     boolean bool1 = "101".equals(paramString2);
/* 1952 */     boolean bool2 = "100".equals(paramString2);
/* 1953 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "AVAIL");
/*      */ 
/*      */ 
/*      */     
/* 1957 */     this.mdlPlaAvailVctA = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1963 */     this.mdlFOAvailVctB = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "143");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1968 */     this.mdlLOAvailVctC = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1973 */     this.mdlEOMAvailVctP = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "200");
/*      */ 
/*      */     
/* 1976 */     this.mdlEOSAvailVctR = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "151");
/*      */     
/* 1978 */     addDebug("getAvails MODELAVAIL all availVct: " + vector.size() + " plannedavail: " + this.mdlPlaAvailVctA.size() + " firstorder: " + this.mdlFOAvailVctB
/*      */         
/* 1980 */         .size() + " lastorder: " + this.mdlLOAvailVctC.size() + " eom: " + this.mdlEOMAvailVctP
/*      */         
/* 1982 */         .size() + " eos: " + this.mdlEOSAvailVctR.size());
/*      */     
/* 1984 */     this.mdlPlaAvailCtryTblA = getAvailByCountry(this.mdlPlaAvailVctA, getCheck_W_W_E(paramString1));
/* 1985 */     addDebug("getAvails MODELAVAIL mdlPlaAvailCtryTblA: " + this.mdlPlaAvailCtryTblA.keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1991 */     this.mdlLOAvailCtryTblC = getAvailByCountry(this.mdlLOAvailVctC, getCheck_W_W_E(paramString1));
/* 1992 */     addDebug("getAvails MODELAVAIL mdlLOAvailCtryTblC: " + this.mdlLOAvailCtryTblC.keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1998 */     if (bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2003 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 2004 */       vector = PokUtils.getAllLinkedEntities(entityGroup, "SWPRODSTRUCTAVAIL", "AVAIL");
/* 2005 */       this.swpsPlaAvailVctH = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 2006 */       this.swpsMesPlaAvailVctH = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "171");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2012 */       this.swpsLOAvailVctK = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/* 2013 */       this.swpsMesLOAvailVctK = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "172");
/*      */       
/* 2015 */       addDebug("getAvails SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL all avails:" + vector.size() + " plannedavail: " + this.swpsPlaAvailVctH
/* 2016 */           .size() + " mesplannedavail: " + this.swpsMesPlaAvailVctH.size() + " lastorder: " + this.swpsLOAvailVctK
/* 2017 */           .size() + " meslastorder: " + this.swpsMesLOAvailVctK.size());
/*      */     } 
/*      */     
/* 2020 */     if (bool2) {
/*      */ 
/*      */ 
/*      */       
/* 2024 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 2025 */       vector = PokUtils.getAllLinkedEntities(entityGroup, "OOFAVAIL", "AVAIL");
/* 2026 */       this.psPlaAvailVctD = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 2027 */       this.psMesPlaAvailVctD = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "171");
/*      */ 
/*      */ 
/*      */       
/* 2031 */       this.psLOAvailVctE = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/* 2032 */       this.psMesLOAvailVctE = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "172");
/*      */       
/* 2034 */       addDebug("getAvails PRODSTRUCT: OOFAVAIL-d: AVAIL all avails:" + vector.size() + " plannedavail: " + this.psPlaAvailVctD
/* 2035 */           .size() + " mesplannedavail: " + this.psMesPlaAvailVctD.size() + " lastorder: " + this.psLOAvailVctE
/* 2036 */           .size() + " meslastorder: " + this.psMesLOAvailVctE.size());
/*      */     } 
/*      */     
/* 2039 */     vector.clear();
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
/*      */   private void doModelEOMAvailChecks(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 2075 */     int i = getCheck_W_W_E(paramString1);
/*      */     
/* 2077 */     addHeading(3, "Model End of Marketing Avail Checks:");
/*      */ 
/*      */     
/* 2080 */     if (this.mdlEOMAvailVctP.size() > 0) {
/* 2081 */       for (byte b1 = 0; b1 < this.mdlEOMAvailVctP.size(); b1++) {
/* 2082 */         EntityItem entityItem = this.mdlEOMAvailVctP.elementAt(b1);
/*      */ 
/*      */ 
/*      */         
/* 2086 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem, "WITHDRAWDATE", i);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2096 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 2097 */       boolean bool1 = getAvailByOSN(hashtable1, this.mdlPlaAvailVctA, true, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2102 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 2103 */       boolean bool2 = getAvailByOSN(hashtable2, this.mdlEOMAvailVctP, true, 3);
/* 2104 */       addDebug("mdlEOMAvailChecks eomOsnErrors " + bool2 + " eomAvailOSNTbl.keys " + hashtable2
/* 2105 */           .keySet() + " plaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1
/* 2106 */           .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2115 */       if (!bool1 && !bool2)
/*      */       {
/*      */         
/* 2118 */         checkAvailCtryByOSN(hashtable2, hashtable1, "MISSING_PLA_OSNCTRY_ERR", (EntityItem)null, true, 
/* 2119 */             getCheckLevel(i, paramEntityItem, "ANNDATE"));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2129 */       hashtable1.clear();
/*      */       
/* 2131 */       hashtable2.clear();
/*      */     } 
/*      */ 
/*      */     
/* 2135 */     Vector<EntityItem> vector = new Vector(this.mdlEOMAvailVctP);
/*      */     
/* 2137 */     removeNonRFAAVAIL(vector);
/*      */     
/* 2139 */     for (byte b = 0; b < vector.size(); b++) {
/* 2140 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 2142 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */       
/* 2144 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 2145 */         EntityItem entityItem1 = vector1.elementAt(b1);
/*      */         
/* 2147 */         String str = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 2148 */         addDebug("checkAvails " + entityItem1.getKey() + " anntypeFlag " + str);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2153 */         if (!"14".equals(str)) {
/*      */           
/* 2155 */           this.args[0] = getLD_NDN(entityItem);
/* 2156 */           this.args[1] = getLD_NDN(entityItem1);
/* 2157 */           createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 2163 */           checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem, "WITHDRAWDATE", i);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2168 */           checkCanNotBeLater4(entityItem1, "ANNDATE", entityItem, "EFFECTIVEDATE", i);
/*      */         } 
/*      */       } 
/* 2171 */       vector1.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2176 */     vector.clear();
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
/*      */   private void doModelLOAvailChecks(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 2274 */     int i = getCheck_W_W_E(paramString1);
/*      */ 
/*      */     
/* 2277 */     boolean bool1 = "101".equals(paramString2);
/* 2278 */     boolean bool2 = "100".equals(paramString2);
/*      */     
/* 2280 */     ArrayList arrayList = null;
/* 2281 */     if (bool2) {
/* 2282 */       arrayList = getAttributeAsList(this.psLOAvailVctE, "ANNCODENAME", i);
/* 2283 */     } else if (bool1) {
/* 2284 */       arrayList = getAttributeAsList(this.swpsLOAvailVctK, "ANNCODENAME", i);
/*      */     } 
/*      */ 
/*      */     
/* 2288 */     addHeading(3, "Model Last Order Avail Checks:");
/*      */ 
/*      */     
/* 2291 */     if (this.mdlLOAvailVctC.size() > 0) {
/* 2292 */       for (byte b1 = 0; b1 < this.mdlLOAvailVctC.size(); b1++) {
/* 2293 */         EntityItem entityItem = this.mdlLOAvailVctC.elementAt(b1);
/*      */ 
/*      */ 
/*      */         
/* 2297 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem, "WTHDRWEFFCTVDATE", i);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2305 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 2306 */       boolean bool3 = getAvailByOSN(hashtable1, this.mdlPlaAvailVctA, true, 3);
/* 2307 */       addDebug("doModelLOAvailChecks  plaOsnErrors " + bool3 + " plaAvailOSNTbl.keys " + hashtable1
/* 2308 */           .keySet());
/*      */       
/* 2310 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 2311 */       boolean bool4 = getAvailByOSN(hashtable2, this.mdlLOAvailVctC, true, 3);
/* 2312 */       addDebug("doModelLOAvailChecks  loOsnErrors " + bool4 + " loAvailOSNTbl.keys " + hashtable2
/* 2313 */           .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2318 */       if (!bool3 && !bool4)
/*      */       {
/*      */         
/* 2321 */         checkAvailCtryByOSN(hashtable2, hashtable1, "MISSING_PLA_OSNCTRY_ERR", (EntityItem)null, true, 
/* 2322 */             getCheckLevel(i, paramEntityItem, "ANNDATE"));
/*      */       }
/* 2324 */       hashtable1.clear();
/* 2325 */       hashtable2.clear();
/*      */     } 
/* 2327 */     if (arrayList != null) {
/* 2328 */       arrayList.clear();
/*      */     }
/*      */     
/* 2331 */     if (bool2)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2348 */       matchPsModelLastOrderAvail(paramString1, this.m_elist.getEntityGroup("PRODSTRUCT"), "FEATURE", "OOFAVAIL", false);
/*      */     }
/*      */ 
/*      */     
/* 2352 */     if (bool1)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2365 */       matchPsModelLastOrderAvail(paramString1, this.m_elist.getEntityGroup("SWPRODSTRUCT"), "SWFEATURE", "SWPRODSTRUCTAVAIL", true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2371 */     Vector<EntityItem> vector1 = new Vector(this.mdlLOAvailVctC);
/*      */     
/* 2373 */     removeNonRFAAVAIL(vector1);
/*      */ 
/*      */ 
/*      */     
/* 2377 */     Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "AVAILANNA", "ANNOUNCEMENT");
/* 2378 */     addDebug("doModelLOAvailChecks mdlLOAvailVctC " + this.mdlLOAvailVctC.size() + " tmpMdlLOVctC " + vector1.size() + " annVct: " + vector2
/* 2379 */         .size());
/* 2380 */     vector2 = PokUtils.getEntitiesWithMatchedAttr(vector2, "ANNTYPE", "14");
/* 2381 */     addDebug("doModelLOAvailChecks EOL annVct: " + vector2.size());
/* 2382 */     for (byte b = 0; b < vector2.size(); b++) {
/* 2383 */       EntityItem entityItem = vector2.elementAt(b);
/*      */ 
/*      */ 
/*      */       
/* 2387 */       checkCanNotBeLater(entityItem, "ANNDATE", paramEntityItem, "WITHDRAWDATE", getCheck_W_W_E(paramString1));
/*      */ 
/*      */ 
/*      */       
/* 2391 */       String str = PokUtils.getAttributeFlagValue(entityItem, "ANNCODENAME");
/* 2392 */       ArrayList<?> arrayList1 = new ArrayList();
/* 2393 */       getCountriesAsList(entityItem, arrayList1, 4);
/* 2394 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 2395 */         EntityItem entityItem1 = vector1.elementAt(b1);
/* 2396 */         String str1 = PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME");
/* 2397 */         if (str1 != null && str1.equals(str)) {
/*      */ 
/*      */ 
/*      */           
/* 2401 */           ArrayList arrayList2 = new ArrayList();
/* 2402 */           getCountriesAsList(entityItem1, arrayList2, 4);
/* 2403 */           arrayList2.retainAll(arrayList1);
/* 2404 */           addDebug("doModelLOAvailChecks: Loavail cntry intersect Ann cntry: " + arrayList2);
/* 2405 */           if (arrayList2.size() > 0)
/* 2406 */             checkCanNotBeLater4(entityItem, "ANNDATE", entityItem1, "EFFECTIVEDATE", getCheck_W_W_E(paramString1)); 
/*      */         } 
/*      */       } 
/*      */     } 
/* 2410 */     vector2.clear();
/*      */ 
/*      */ 
/*      */     
/* 2414 */     vector1.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   private void doModelLOAndMesLOAvailChecks(String paramString1, Vector<EntityItem> paramVector, String paramString2, EntityItem paramEntityItem, String paramString3, String paramString4) throws SQLException, MiddlewareException {
/* 2419 */     int i = getCheck_W_W_E(paramString3);
/*      */ 
/*      */     
/* 2422 */     boolean bool1 = "101".equals(paramString4);
/* 2423 */     boolean bool2 = "100".equals(paramString4);
/*      */     
/* 2425 */     ArrayList arrayList1 = null;
/* 2426 */     ArrayList arrayList2 = null;
/* 2427 */     if (bool2) {
/* 2428 */       arrayList1 = getAttributeAsList(this.psLOAvailVctE, "ANNCODENAME", i);
/* 2429 */       arrayList2 = getAttributeAsList(this.psMesLOAvailVctE, "ANNCODENAME", i);
/* 2430 */     } else if (bool1) {
/* 2431 */       arrayList1 = getAttributeAsList(this.swpsLOAvailVctK, "ANNCODENAME", i);
/*      */       
/* 2433 */       arrayList2 = getAttributeAsList(this.swpsMesLOAvailVctK, "ANNCODENAME", i);
/*      */     } 
/*      */ 
/*      */     
/* 2437 */     addHeading(3, paramString1);
/*      */ 
/*      */     
/* 2440 */     if (paramVector.size() > 0) {
/* 2441 */       for (byte b1 = 0; b1 < paramVector.size(); b1++) {
/* 2442 */         EntityItem entityItem = paramVector.elementAt(b1);
/*      */ 
/*      */ 
/*      */         
/* 2446 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem, "WTHDRWEFFCTVDATE", i);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2454 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 2455 */       boolean bool3 = getAvailByOSN(hashtable1, this.mdlPlaAvailVctA, true, 3);
/* 2456 */       addDebug("doModelLOAvailChecks  plaOsnErrors " + bool3 + " plaAvailOSNTbl.keys " + hashtable1
/* 2457 */           .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2465 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 2466 */       boolean bool4 = getAvailByOSN(hashtable2, paramVector, true, 3);
/* 2467 */       addDebug("doModelLOAvailChecks  loOsnErrors " + bool4 + " loAvailOSNTbl.keys " + hashtable2
/* 2468 */           .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2473 */       if (!bool3 && !bool4)
/*      */       {
/*      */         
/* 2476 */         checkAvailCtryByOSN(hashtable2, hashtable1, "MISSING_PLA_OSNCTRY_ERR", (EntityItem)null, true, 
/* 2477 */             getCheckLevel(i, paramEntityItem, "ANNDATE"));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2487 */       hashtable1.clear();
/*      */       
/* 2489 */       hashtable2.clear();
/*      */     } 
/* 2491 */     if (arrayList1 != null) {
/* 2492 */       arrayList1.clear();
/*      */     }
/* 2494 */     if (arrayList2 != null) {
/* 2495 */       arrayList2.clear();
/*      */     }
/* 2497 */     if (bool2)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2516 */       matchPsModelLastOrderAndMesLastOrderAvail(paramString2, paramVector, paramString3, this.m_elist
/* 2517 */           .getEntityGroup("PRODSTRUCT"), "FEATURE", "OOFAVAIL", false);
/*      */     }
/*      */ 
/*      */     
/* 2521 */     if (bool1)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2536 */       matchPsModelLastOrderAndMesLastOrderAvail(paramString2, paramVector, paramString3, this.m_elist
/* 2537 */           .getEntityGroup("SWPRODSTRUCT"), "SWFEATURE", "SWPRODSTRUCTAVAIL", true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2542 */     Vector<EntityItem> vector1 = new Vector<>(paramVector);
/*      */     
/* 2544 */     removeNonRFAAVAIL(vector1);
/*      */ 
/*      */ 
/*      */     
/* 2548 */     Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "AVAILANNA", "ANNOUNCEMENT");
/* 2549 */     addDebug("doModelLOAvailChecks mdlLOAvailVctC " + paramVector.size() + " tmpMdlLOVctC " + vector1
/* 2550 */         .size() + " annVct: " + vector2.size() + " availTYpe:" + paramString2);
/* 2551 */     vector2 = PokUtils.getEntitiesWithMatchedAttr(vector2, "ANNTYPE", "14");
/* 2552 */     addDebug("doModelLOAvailChecks EOL annVct: " + vector2.size());
/* 2553 */     for (byte b = 0; b < vector2.size(); b++) {
/* 2554 */       EntityItem entityItem = vector2.elementAt(b);
/*      */ 
/*      */ 
/*      */       
/* 2558 */       checkCanNotBeLater(entityItem, "ANNDATE", paramEntityItem, "WITHDRAWDATE", getCheck_W_W_E(paramString3));
/*      */ 
/*      */ 
/*      */       
/* 2562 */       String str = PokUtils.getAttributeFlagValue(entityItem, "ANNCODENAME");
/* 2563 */       ArrayList<?> arrayList = new ArrayList();
/* 2564 */       getCountriesAsList(entityItem, arrayList, 4);
/* 2565 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 2566 */         EntityItem entityItem1 = vector1.elementAt(b1);
/* 2567 */         String str1 = PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME");
/* 2568 */         if (str1 != null && str1.equals(str)) {
/*      */ 
/*      */ 
/*      */           
/* 2572 */           ArrayList arrayList3 = new ArrayList();
/* 2573 */           getCountriesAsList(entityItem1, arrayList3, 4);
/* 2574 */           arrayList3.retainAll(arrayList);
/* 2575 */           addDebug("doModelLOAvailChecks: Loavail cntry intersect Ann cntry: " + arrayList3);
/* 2576 */           if (arrayList3.size() > 0)
/* 2577 */             checkCanNotBeLater4(entityItem, "ANNDATE", entityItem1, "EFFECTIVEDATE", getCheck_W_W_E(paramString3)); 
/*      */         } 
/*      */       } 
/*      */     } 
/* 2581 */     vector2.clear();
/*      */ 
/*      */ 
/*      */     
/* 2585 */     vector1.clear();
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
/*      */   private void checkCanNotBeLater4(EntityItem paramEntityItem1, String paramString1, EntityItem paramEntityItem2, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 2601 */     checkCanNotBeLater4((EntityItem)null, paramEntityItem1, paramString1, paramEntityItem2, paramString2, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkCanNotBeLater4(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, EntityItem paramEntityItem3, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 2606 */     String str1 = "";
/* 2607 */     if (paramEntityItem1 != null) {
/* 2608 */       str1 = getLD_NDN(paramEntityItem1) + " ";
/*      */     }
/* 2610 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem2, paramString1, paramInt);
/* 2611 */     String str3 = getAttrValueAndCheckLvl(paramEntityItem3, paramString2, paramInt);
/* 2612 */     addDebug("checkCanNotBeLater4 " + paramEntityItem2.getKey() + " " + paramString1 + ":" + str2 + " " + paramEntityItem3.getKey() + " " + paramString2 + ":" + str3);
/*      */     
/* 2614 */     boolean bool = checkDates(str2, str3, 2);
/* 2615 */     if (bool) {
/* 2616 */       if (str2.length() > 0 && !Character.isDigit(str2.charAt(0))) {
/* 2617 */         bool = false;
/*      */       }
/* 2619 */       if (str3.length() > 0 && !Character.isDigit(str3.charAt(0))) {
/* 2620 */         bool = false;
/*      */       }
/*      */     } 
/* 2623 */     if (!bool) {
/*      */ 
/*      */ 
/*      */       
/* 2627 */       this.args[0] = str1 + getLD_NDN(paramEntityItem2);
/* 2628 */       this.args[1] = getLD_Value(paramEntityItem2, paramString1);
/* 2629 */       this.args[2] = this.m_elist.getParentEntityGroup().getLongDescription() + " " + getLD_NDN(paramEntityItem3);
/* 2630 */       this.args[3] = getLD_Value(paramEntityItem3, paramString2);
/* 2631 */       createMessage(paramInt, "CANNOT_BE_LATER_ERR", this.args);
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
/*      */   
/*      */   private void doSoftwareDataChecks(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 2655 */     addHeading(3, paramEntityItem.getLongDescription() + " SofWare data Checks:");
/*      */     
/* 2657 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "MACHTYPEATR");
/* 2658 */     if (str != null && (str.startsWith("56") || str.startsWith("57")) && "101".equals(paramString2)) {
/*      */ 
/*      */       
/* 2661 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODTAXRELEVANCE", "TAXCATG");
/* 2662 */       if (vector != null && !vector.isEmpty()) {
/* 2663 */         for (byte b = 0; b < vector.size(); b++) {
/* 2664 */           EntityItem entityItem = vector.get(b);
/* 2665 */           String str1 = PokUtils.getAttributeFlagValue(entityItem, "TAXCNTRY");
/* 2666 */           String str2 = PokUtils.getAttributeFlagValue(entityItem, "SLEORGGRP");
/* 2667 */           addDebug("tax" + str1);
/* 2668 */           addDebug("grp" + str2);
/* 2669 */           if ("1652".equals(str1) && "SGSUS".equals(str2)) {
/* 2670 */             EntityItem entityItem1 = entityItem.getUpLink().get(0);
/*      */             
/* 2672 */             String str3 = PokUtils.getAttributeValue(entityItem1, "TAXCLS", "", "");
/* 2673 */             if ("".equals(str3) || pattern.matcher(str3).find()) {
/*      */               
/* 2675 */               this.args[0] = getLD_NDN(entityItem1);
/*      */ 
/*      */ 
/*      */               
/* 2679 */               this.args[1] = "Tax Country US";
/* 2680 */               addError("INVALID_VALUES_ERR", this.args);
/*      */             } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doModelPLAAvailChecks(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 2722 */     boolean bool1 = "102".equals(paramString2);
/* 2723 */     boolean bool2 = "101".equals(paramString2);
/* 2724 */     boolean bool3 = "100".equals(paramString2);
/*      */     
/* 2726 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "FEATINDC");
/* 2727 */     addDebug("doModelPLAAvailChecks " + paramEntityItem.getKey() + " FEATINDC: " + str);
/*      */     
/* 2729 */     addHeading(3, "Model Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2735 */     checkPlannedAvailsExist(this.mdlPlaAvailVctA, getCheckLevel(3, paramEntityItem, "ANNDATE"));
/*      */     
/* 2737 */     int i = getCheck_W_W_E(paramString1);
/*      */     
/* 2739 */     ArrayList arrayList = null;
/* 2740 */     if (bool3) {
/* 2741 */       arrayList = getAttributeAsList(this.psPlaAvailVctD, "ANNCODENAME", i);
/* 2742 */     } else if (bool2) {
/* 2743 */       arrayList = getAttributeAsList(this.swpsPlaAvailVctH, "ANNCODENAME", i);
/*      */     } 
/*      */ 
/*      */     
/* 2747 */     for (byte b1 = 0; b1 < this.mdlPlaAvailVctA.size(); b1++) {
/* 2748 */       EntityItem entityItem = this.mdlPlaAvailVctA.elementAt(b1);
/*      */ 
/*      */ 
/*      */       
/* 2752 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", getCheck_W_E_E(paramString1));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2761 */     checkPlannedAvailsStatus(this.mdlPlaAvailVctA, paramEntityItem, getCheckLevel(3, paramEntityItem, "ANNDATE"));
/*      */     
/* 2763 */     Vector<EntityItem> vector1 = new Vector(this.mdlPlaAvailVctA);
/*      */     
/* 2765 */     removeNonRFAAVAIL(vector1);
/*      */     
/* 2767 */     for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 2768 */       EntityItem entityItem = vector1.elementAt(b2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2776 */       if (!bool1 && !"FEATN".equals(str)) {
/* 2777 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "ANNCODENAME");
/* 2778 */         if (str1 != null && 
/* 2779 */           !arrayList.contains(str1)) {
/*      */ 
/*      */           
/* 2782 */           addDebug("doModelPLAAvailChecks psplannedavails plaCodeNameList: " + arrayList + " did not have mdlplannedavail:" + entityItem
/* 2783 */               .getKey() + " annCodeName: " + str1);
/* 2784 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2789 */           this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/* 2790 */           if (vector.size() > 0) {
/* 2791 */             this.args[1] = getLD_NDN(entityItem) + " " + getLD_NDN(vector.firstElement());
/*      */           } else {
/* 2793 */             this.args[1] = this.m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
/*      */           } 
/*      */           
/* 2796 */           createMessage(i, "MUST_HAVE_FEATURES_ERR2", this.args);
/* 2797 */           vector.clear();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2804 */     if (arrayList != null) {
/* 2805 */       arrayList.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2815 */     Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "AVAILANNA", "ANNOUNCEMENT");
/* 2816 */     addDebug("doModelPLAAvailChecks annVct: " + vector2.size());
/* 2817 */     vector2 = PokUtils.getEntitiesWithMatchedAttr(vector2, "ANNTYPE", "19");
/* 2818 */     addDebug("doModelPLAAvailChecks NEW annVct: " + vector2.size());
/* 2819 */     for (byte b3 = 0; b3 < vector2.size(); b3++) {
/* 2820 */       EntityItem entityItem = vector2.elementAt(b3);
/*      */ 
/*      */       
/* 2823 */       checkCanNotBeEarlier(entityItem, "ANNDATE", paramEntityItem, "ANNDATE", getCheck_W_W_E(paramString1));
/*      */     } 
/* 2825 */     vector2.clear();
/* 2826 */     vector1.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doModelPLAndMesPLAvailChecks(String paramString1, Vector<EntityItem> paramVector, String paramString2, EntityItem paramEntityItem, String paramString3, String paramString4) throws SQLException, MiddlewareException {
/* 2833 */     boolean bool1 = "102".equals(paramString4);
/* 2834 */     boolean bool2 = "101".equals(paramString4);
/* 2835 */     boolean bool3 = "100".equals(paramString4);
/*      */     
/* 2837 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "FEATINDC");
/* 2838 */     addDebug("doModelPLAAvailChecks " + paramEntityItem.getKey() + " FEATINDC: " + str + " AVAILTYPE:" + paramString2);
/*      */ 
/*      */     
/* 2841 */     addHeading(3, paramString1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2847 */     if ("146".equals(paramString2)) {
/* 2848 */       checkPlannedAvailsExist(paramVector, getCheckLevel(3, paramEntityItem, "ANNDATE"));
/*      */     }
/*      */ 
/*      */     
/* 2852 */     int i = getCheck_W_W_E(paramString3);
/*      */     
/* 2854 */     ArrayList arrayList1 = null;
/* 2855 */     ArrayList arrayList2 = null;
/* 2856 */     if (bool3) {
/* 2857 */       arrayList1 = getAttributeAsList(this.psPlaAvailVctD, "ANNCODENAME", i);
/* 2858 */       arrayList2 = getAttributeAsList(this.psMesPlaAvailVctD, "ANNCODENAME", i);
/* 2859 */     } else if (bool2) {
/* 2860 */       arrayList1 = getAttributeAsList(this.swpsPlaAvailVctH, "ANNCODENAME", i);
/*      */       
/* 2862 */       arrayList2 = getAttributeAsList(this.swpsMesPlaAvailVctH, "ANNCODENAME", i);
/*      */     } 
/*      */     
/* 2865 */     for (byte b1 = 0; b1 < paramVector.size(); b1++) {
/* 2866 */       EntityItem entityItem = paramVector.elementAt(b1);
/*      */ 
/*      */ 
/*      */       
/* 2870 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", getCheck_W_E_E(paramString3));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2880 */     if ("146".equals(paramString2)) {
/* 2881 */       checkPlannedAvailsStatus(paramVector, paramEntityItem, 
/* 2882 */           getCheckLevel(3, paramEntityItem, "ANNDATE"));
/*      */     }
/*      */ 
/*      */     
/* 2886 */     Vector<EntityItem> vector1 = new Vector<>(paramVector);
/*      */     
/* 2888 */     removeNonRFAAVAIL(vector1);
/*      */     
/* 2890 */     for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 2891 */       EntityItem entityItem = vector1.elementAt(b2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2899 */       if (!bool1 && !"FEATN".equals(str)) {
/* 2900 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "ANNCODENAME");
/* 2901 */         if (str1 != null && 
/* 2902 */           !arrayList1.contains(str1) && !arrayList2.contains(str1)) {
/*      */ 
/*      */           
/* 2905 */           addDebug("doModelPLAAvailChecks psplannedavails plaCodeNameList: " + arrayList1 + " and psMesPlannedavails mesPlaCodeNameList: " + arrayList2 + " did not have mdlplannedavail:" + entityItem
/*      */               
/* 2907 */               .getKey() + " annCodeName: " + str1);
/* 2908 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2913 */           this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/* 2914 */           if (vector.size() > 0) {
/* 2915 */             this.args[1] = getLD_NDN(entityItem) + " " + getLD_NDN(vector.firstElement());
/*      */           } else {
/* 2917 */             this.args[1] = this.m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
/*      */           } 
/* 2919 */           createMessage(i, "MUST_HAVE_FEATURES_ERR2", this.args);
/* 2920 */           vector.clear();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2927 */     if (arrayList1 != null) {
/* 2928 */       arrayList1.clear();
/*      */     }
/* 2930 */     if (arrayList2 != null) {
/* 2931 */       arrayList2.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2941 */     Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "AVAILANNA", "ANNOUNCEMENT");
/* 2942 */     addDebug("doModelPLAAvailChecks annVct: " + vector2.size());
/* 2943 */     vector2 = PokUtils.getEntitiesWithMatchedAttr(vector2, "ANNTYPE", "19");
/* 2944 */     addDebug("doModelPLAAvailChecks NEW annVct: " + vector2.size());
/* 2945 */     for (byte b3 = 0; b3 < vector2.size(); b3++) {
/* 2946 */       EntityItem entityItem = vector2.elementAt(b3);
/*      */ 
/*      */       
/* 2949 */       checkCanNotBeEarlier(entityItem, "ANNDATE", paramEntityItem, "ANNDATE", getCheck_W_W_E(paramString3));
/*      */     } 
/* 2951 */     vector2.clear();
/* 2952 */     vector1.clear();
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
/*      */   private void doModelFOAvailChecks(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 2987 */     int i = getCheck_W_W_E(paramString1);
/* 2988 */     addHeading(3, "Model First Order Avail Checks:");
/*      */ 
/*      */     
/* 2991 */     if (this.mdlFOAvailVctB.size() > 0) {
/* 2992 */       for (byte b1 = 0; b1 < this.mdlFOAvailVctB.size(); b1++) {
/* 2993 */         EntityItem entityItem = this.mdlFOAvailVctB.elementAt(b1);
/*      */ 
/*      */ 
/*      */         
/* 2997 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", i);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3003 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 3004 */       boolean bool1 = getAvailByOSN(hashtable1, this.mdlPlaAvailVctA, true, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3009 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 3010 */       boolean bool2 = getAvailByOSN(hashtable2, this.mdlFOAvailVctB, true, 3);
/* 3011 */       addDebug("doModelFOAvailChecks foOsnErrors " + bool2 + " foAvailOSNTbl.keys " + hashtable2.keySet() + " plaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1
/*      */           
/* 3013 */           .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3022 */       if (!bool1 && !bool2)
/*      */       {
/*      */         
/* 3025 */         checkAvailCtryByOSN(hashtable2, hashtable1, "MISSING_PLA_OSNCTRY_ERR", (EntityItem)null, true, i);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3034 */       hashtable1.clear();
/*      */       
/* 3036 */       hashtable2.clear();
/*      */     } 
/*      */     
/* 3039 */     Vector<EntityItem> vector1 = new Vector(this.mdlFOAvailVctB);
/*      */     
/* 3041 */     removeNonRFAAVAIL(vector1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3048 */     Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "AVAILANNA", "ANNOUNCEMENT");
/* 3049 */     addDebug("doModelFOAvailChecks mdlFOAvailVctB " + this.mdlFOAvailVctB.size() + " RFA mdlfoavails " + vector1
/* 3050 */         .size() + " annVct: " + vector2.size());
/* 3051 */     vector2 = PokUtils.getEntitiesWithMatchedAttr(vector2, "ANNTYPE", "19");
/* 3052 */     addDebug("doModelFOAvailChecks NEW annVct: " + vector2.size());
/*      */     
/* 3054 */     for (byte b = 0; b < vector2.size(); b++) {
/* 3055 */       EntityItem entityItem = vector2.elementAt(b);
/*      */ 
/*      */       
/* 3058 */       checkCanNotBeEarlier(entityItem, "ANNDATE", paramEntityItem, "ANNDATE", getCheck_W_W_E(paramString1));
/*      */ 
/*      */ 
/*      */       
/* 3062 */       String str = PokUtils.getAttributeFlagValue(entityItem, "ANNCODENAME");
/* 3063 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 3064 */         EntityItem entityItem1 = vector1.elementAt(b1);
/* 3065 */         String str1 = PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME");
/* 3066 */         if (str1 != null && str1.equals(str))
/*      */         {
/*      */ 
/*      */           
/* 3070 */           checkCanNotBeEarlier(entityItem1, "EFFECTIVEDATE", entityItem, "ANNDATE", getCheck_W_E_E(paramString1)); } 
/*      */       } 
/*      */     } 
/* 3073 */     vector2.clear();
/* 3074 */     vector1.clear();
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
/*      */   private void matchPsModelLastOrderAvail(String paramString1, EntityGroup paramEntityGroup, String paramString2, String paramString3, boolean paramBoolean) throws MiddlewareException, SQLException {
/* 3132 */     int i = getCheck_W_RW_RE(paramString1);
/* 3133 */     if (paramBoolean) {
/* 3134 */       i = getCheck_W_RW_RW(paramString1);
/*      */     }
/*      */     
/* 3137 */     if (this.mdlLOAvailVctC.size() > 0) {
/* 3138 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 3139 */       boolean bool = getAvailByOSN(hashtable, this.mdlLOAvailVctC, true, 3);
/* 3140 */       addDebug("matchPsModelLastOrderAvail  mdlloOsnErrors " + bool + " mdlloAvailOSNTbl.keys " + hashtable
/* 3141 */           .keySet());
/* 3142 */       if (bool) {
/*      */         return;
/*      */       }
/*      */       
/* 3146 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 3147 */         EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 3148 */         boolean bool1 = true;
/* 3149 */         if (entityItem.getEntityType().equals("PRODSTRUCT")) {
/*      */           
/* 3151 */           String str = PokUtils.getAttributeFlagValue(entityItem, "ORDERCODE");
/* 3152 */           addDebug("matchPsModelLastOrderAvail " + entityItem.getKey() + " ordercode: " + str);
/* 3153 */           if (!"5957".equals(str)) {
/* 3154 */             bool1 = false;
/* 3155 */             addDebug("     ordercode was not initial, skipping checks");
/*      */           }
/*      */           else {
/*      */             
/* 3159 */             EntityItem entityItem1 = getUpLinkEntityItem(entityItem, "FEATURE");
/* 3160 */             addDebug("matchPsModelLastOrderAvail  " + entityItem.getKey() + " " + entityItem1.getKey());
/* 3161 */             if (isRPQ(entityItem1)) {
/* 3162 */               addDebug(entityItem1.getKey() + " was an RPQ FCTYPE: " + 
/* 3163 */                   getAttributeFlagEnabledValue(entityItem1, "FCTYPE") + " skipping checks");
/* 3164 */               bool1 = false;
/*      */             } 
/*      */           } 
/*      */         } 
/* 3168 */         if (bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3202 */           Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, paramString3, "AVAIL");
/* 3203 */           Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 3204 */           Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "149");
/*      */           
/* 3206 */           addDebug("matchPsModelLastOrderAvail " + entityItem.getKey() + " all avail: " + vector1.size() + " plaAvail: " + vector2
/* 3207 */               .size() + " loAvail: " + vector3.size());
/*      */           
/* 3209 */           Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 3210 */           boolean bool2 = getAvailByOSN(hashtable1, vector3, true, 3);
/*      */           
/* 3212 */           Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 3213 */           boolean bool3 = getAvailByOSN(hashtable2, vector2, true, 3);
/* 3214 */           addDebug("matchPsModelLastOrderAvail " + entityItem.getKey() + " loOsnErrors " + bool2 + " loAvailOSNTbl.keys " + hashtable1
/* 3215 */               .keySet() + " plaOsnErrors " + bool3 + " plaAvailOSNTbl.keys " + hashtable2
/* 3216 */               .keySet());
/*      */           
/* 3218 */           if (!bool3 && !bool2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3229 */             Vector<EntityItem> vector4 = new Vector();
/* 3230 */             Vector<EntityItem> vector5 = new Vector();
/*      */             
/* 3232 */             Set set = hashtable2.keySet();
/* 3233 */             Iterator<String> iterator = set.iterator();
/* 3234 */             while (iterator.hasNext()) {
/*      */               
/* 3236 */               String str = iterator.next();
/*      */               
/* 3238 */               Vector<EntityItem> vector = (Vector)hashtable2.get(str);
/* 3239 */               Vector vector6 = (Vector)hashtable.get(str);
/* 3240 */               if (vector6 == null) {
/* 3241 */                 addDebug("matchPsModelLastOrderAvail no mdl loavails to check for osn " + str);
/*      */                 continue;
/*      */               } 
/* 3244 */               Hashtable hashtable3 = getAvailByCountry(vector6, i);
/* 3245 */               addDebug("matchPsModelLastOrderAvail osn " + str + " mdllastOrderAvlCtryTbl: " + hashtable3
/* 3246 */                   .keySet());
/* 3247 */               Vector vector7 = (Vector)hashtable1.get(str);
/* 3248 */               Hashtable<Object, Object> hashtable4 = null;
/* 3249 */               if (vector7 != null) {
/* 3250 */                 hashtable4 = getAvailByCountry(vector7, i);
/*      */               } else {
/* 3252 */                 hashtable4 = new Hashtable<>();
/*      */               } 
/* 3254 */               addDebug("matchPsModelLastOrderAvail loOsnVct: " + ((vector7 == null) ? "null" : ("" + vector7
/* 3255 */                   .size())) + " PS-lastOrderAvlCtry: " + hashtable4
/* 3256 */                   .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3261 */               for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 3262 */                 EntityItem entityItem1 = vector.elementAt(b1);
/*      */                 
/* 3264 */                 EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(entityItem1, "COUNTRYLIST", i);
/*      */                 
/* 3266 */                 if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */                   
/* 3268 */                   MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 3269 */                   ArrayList<String> arrayList = new ArrayList();
/*      */                   
/* 3271 */                   Vector<EntityItem> vector8 = new Vector();
/*      */ 
/*      */                   
/*      */                   byte b2;
/*      */                   
/* 3276 */                   for (b2 = 0; b2 < arrayOfMetaFlag.length; b2++) {
/* 3277 */                     if (arrayOfMetaFlag[b2].isSelected()) {
/*      */ 
/*      */ 
/*      */                       
/* 3281 */                       EntityItem entityItem2 = (EntityItem)hashtable3.get(arrayOfMetaFlag[b2].getFlagCode());
/* 3282 */                       if (!hashtable4.containsKey(arrayOfMetaFlag[b2].getFlagCode())) {
/* 3283 */                         addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + entityItem1.getKey() + " No PS lastorderavail for osn " + str + " ctry " + arrayOfMetaFlag[b2]
/*      */                             
/* 3285 */                             .getFlagCode());
/* 3286 */                         if (entityItem2 != null) {
/* 3287 */                           addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + entityItem1.getKey() + " MODEL-lastorderavail " + entityItem2
/* 3288 */                               .getKey() + " match for ctry " + arrayOfMetaFlag[b2]
/* 3289 */                               .getFlagCode());
/* 3290 */                           if (vector4.contains(entityItem2)) {
/* 3291 */                             addDebug("already output msg for MODEL-lastorderavail " + entityItem2
/* 3292 */                                 .getKey());
/*      */                           
/*      */                           }
/* 3295 */                           else if (!vector8.contains(entityItem2)) {
/* 3296 */                             vector8.add(entityItem2);
/* 3297 */                             arrayList.add(arrayOfMetaFlag[b2].getFlagCode());
/*      */                           }
/*      */                         
/*      */                         } 
/* 3301 */                       } else if (entityItem2 != null) {
/* 3302 */                         addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + entityItem1.getKey() + " PS lastorder match ctry and osn " + str + " MODEL-lastorderavail " + entityItem2
/*      */                             
/* 3304 */                             .getKey() + " match for ctry " + arrayOfMetaFlag[b2]
/* 3305 */                             .getFlagCode());
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3310 */                         EntityItem entityItem3 = (EntityItem)hashtable4.get(arrayOfMetaFlag[b2].getFlagCode());
/*      */                         
/* 3312 */                         if (vector5.contains(entityItem3)) {
/* 3313 */                           addDebug("already chk dates for ps-lastorderavail " + entityItem3
/* 3314 */                               .getKey());
/*      */                         } else {
/*      */                           
/* 3317 */                           vector5.add(entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 3338 */                           checkCanNotBeLater4(entityItem, entityItem3, "EFFECTIVEDATE", entityItem2, "EFFECTIVEDATE", i);
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3347 */                   for (b2 = 0; b2 < vector8.size(); b2++) {
/* 3348 */                     EntityItem entityItem2 = vector8.elementAt(b2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 3355 */                     this.args[0] = getLD_NDN(getUpLinkEntityItem(entityItem, paramString2));
/* 3356 */                     this.args[1] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 3357 */                     if (str.equals("DOMAIN_NOT_IN_LIST")) {
/* 3358 */                       this.args[2] = getLD_NDN(entityItem2);
/*      */                     } else {
/* 3360 */                       this.args[2] = getLD_NDN(entityItem2) + " for " + 
/* 3361 */                         getLD_Value(entityItem2, "ORDERSYSNAME");
/*      */                     } 
/* 3363 */                     this.args[3] = getUnmatchedDescriptions(entityItem2, "COUNTRYLIST", arrayList);
/* 3364 */                     createMessage(i, "PS_LAST_ORDER_ERR2", this.args);
/*      */                   } 
/* 3366 */                   vector4.addAll(vector8);
/* 3367 */                   vector8.clear();
/* 3368 */                   arrayList.clear();
/*      */                 } 
/*      */               } 
/*      */               
/* 3372 */               hashtable4.clear();
/*      */             } 
/*      */ 
/*      */             
/* 3376 */             vector1.clear();
/* 3377 */             vector2.clear();
/* 3378 */             vector3.clear();
/*      */             
/* 3380 */             vector4.clear();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void matchPsModelLastOrderAndMesLastOrderAvail(String paramString1, Vector paramVector, String paramString2, EntityGroup paramEntityGroup, String paramString3, String paramString4, boolean paramBoolean) throws MiddlewareException, SQLException {
/* 3389 */     int i = getCheck_W_RW_RE(paramString2);
/* 3390 */     if (paramBoolean) {
/* 3391 */       i = getCheck_W_RW_RW(paramString2);
/*      */     }
/* 3393 */     if (paramVector.size() > 0) {
/* 3394 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 3395 */       boolean bool = getAvailByOSN(hashtable, paramVector, true, 3);
/* 3396 */       addDebug("matchPsModelLastOrderAvail  mdlloOsnErrors " + bool + " mdlloAvailOSNTbl.keys " + hashtable
/* 3397 */           .keySet());
/* 3398 */       if (bool) {
/*      */         return;
/*      */       }
/*      */       
/* 3402 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 3403 */         EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 3404 */         boolean bool1 = true;
/* 3405 */         if (entityItem.getEntityType().equals("PRODSTRUCT")) {
/*      */           
/* 3407 */           String str = PokUtils.getAttributeFlagValue(entityItem, "ORDERCODE");
/* 3408 */           addDebug("matchPsModelLastOrderAvail " + entityItem.getKey() + " ordercode: " + str);
/* 3409 */           if (!"5957".equals(str)) {
/* 3410 */             bool1 = false;
/* 3411 */             addDebug("     ordercode was not initial, skipping checks");
/*      */           }
/*      */           else {
/*      */             
/* 3415 */             EntityItem entityItem1 = getUpLinkEntityItem(entityItem, "FEATURE");
/* 3416 */             addDebug("matchPsModelLastOrderAvail  " + entityItem.getKey() + " " + entityItem1.getKey());
/* 3417 */             if (isRPQ(entityItem1)) {
/* 3418 */               addDebug(entityItem1.getKey() + " was an RPQ FCTYPE: " + 
/* 3419 */                   getAttributeFlagEnabledValue(entityItem1, "FCTYPE") + " skipping checks");
/* 3420 */               bool1 = false;
/*      */             } 
/*      */           } 
/*      */         } 
/* 3424 */         if (bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3458 */           Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, paramString4, "AVAIL");
/* 3459 */           Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 3460 */           Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "149");
/*      */           
/* 3462 */           addDebug("matchPsModelLastOrderAvail " + entityItem.getKey() + " all avail: " + vector1.size() + " plaAvail: " + vector2
/* 3463 */               .size() + " loAvail: " + vector3.size());
/*      */           
/* 3465 */           if (!"149".endsWith(paramString1) || !checkMdlLoAvailWithPsAvailDAndAvailE(vector3, vector2, entityItem, hashtable, paramString3, i)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3470 */             Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "171");
/*      */             
/* 3472 */             Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "172");
/*      */             
/* 3474 */             addDebug("matchPsModelLastOrderAvail " + entityItem.getKey() + " all avail: " + vector1.size() + " mesplaAvail: " + vector4
/* 3475 */                 .size() + " mesloAvail: " + vector5
/* 3476 */                 .size());
/*      */             
/* 3478 */             if (!checkMdlLoAvailWithPsAvailDAndAvailE(vector5, vector4, entityItem, hashtable, paramString3, i))
/*      */             {
/*      */ 
/*      */               
/* 3482 */               vector1.clear();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean checkMdlLoAvailWithPsAvailDAndAvailE(Vector paramVector1, Vector paramVector2, EntityItem paramEntityItem, Hashtable paramHashtable, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 3491 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 3492 */     boolean bool1 = getAvailByOSN(hashtable1, paramVector1, true, 3);
/*      */     
/* 3494 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 3495 */     boolean bool2 = getAvailByOSN(hashtable2, paramVector2, true, 3);
/* 3496 */     addDebug("matchPsModelLastOrderAvail " + paramEntityItem.getKey() + " loOsnErrors " + bool1 + " loAvailOSNTbl.keys " + hashtable1
/* 3497 */         .keySet() + " plaOsnErrors " + bool2 + " plaAvailOSNTbl.keys " + hashtable2
/* 3498 */         .keySet());
/*      */     
/* 3500 */     if (bool2 || bool1) {
/* 3501 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3511 */     Vector<EntityItem> vector1 = new Vector();
/* 3512 */     Vector<EntityItem> vector2 = new Vector();
/*      */     
/* 3514 */     Set set = hashtable2.keySet();
/* 3515 */     Iterator<String> iterator = set.iterator();
/* 3516 */     while (iterator.hasNext()) {
/* 3517 */       String str = iterator.next();
/*      */       
/* 3519 */       Vector<EntityItem> vector = (Vector)hashtable2.get(str);
/* 3520 */       Vector vector3 = (Vector)paramHashtable.get(str);
/* 3521 */       if (vector3 == null) {
/* 3522 */         addDebug("matchPsModelLastOrderAvail no mdl loavails to check for osn " + str);
/*      */         continue;
/*      */       } 
/* 3525 */       Hashtable hashtable = getAvailByCountry(vector3, paramInt);
/* 3526 */       addDebug("matchPsModelLastOrderAvail osn " + str + " mdllastOrderAvlCtryTbl: " + hashtable
/* 3527 */           .keySet());
/* 3528 */       Vector vector4 = (Vector)hashtable1.get(str);
/* 3529 */       Hashtable<Object, Object> hashtable3 = null;
/* 3530 */       if (vector4 != null) {
/* 3531 */         hashtable3 = getAvailByCountry(vector4, paramInt);
/*      */       } else {
/* 3533 */         hashtable3 = new Hashtable<>();
/*      */       } 
/* 3535 */       addDebug("matchPsModelLastOrderAvail loOsnVct: " + ((vector4 == null) ? "null" : ("" + vector4.size())) + " PS-lastOrderAvlCtry: " + hashtable3
/* 3536 */           .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3541 */       for (byte b = 0; b < vector.size(); b++) {
/* 3542 */         EntityItem entityItem = vector.elementAt(b);
/*      */         
/* 3544 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(entityItem, "COUNTRYLIST", paramInt);
/* 3545 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */           
/* 3547 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 3548 */           ArrayList<String> arrayList = new ArrayList();
/*      */           
/* 3550 */           Vector<EntityItem> vector5 = new Vector();
/*      */           
/*      */           byte b1;
/* 3553 */           for (b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 3554 */             if (arrayOfMetaFlag[b1].isSelected()) {
/*      */ 
/*      */               
/* 3557 */               EntityItem entityItem1 = (EntityItem)hashtable.get(arrayOfMetaFlag[b1].getFlagCode());
/* 3558 */               if (!hashtable3.containsKey(arrayOfMetaFlag[b1].getFlagCode())) {
/* 3559 */                 addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + entityItem.getKey() + " No PS lastorderavail for osn " + str + " ctry " + arrayOfMetaFlag[b1]
/*      */                     
/* 3561 */                     .getFlagCode());
/* 3562 */                 if (entityItem1 != null) {
/* 3563 */                   addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + entityItem.getKey() + " MODEL-lastorderavail " + entityItem1
/* 3564 */                       .getKey() + " match for ctry " + arrayOfMetaFlag[b1]
/* 3565 */                       .getFlagCode());
/* 3566 */                   if (vector1.contains(entityItem1)) {
/* 3567 */                     addDebug("already output msg for MODEL-lastorderavail " + entityItem1.getKey());
/*      */                   
/*      */                   }
/* 3570 */                   else if (!vector5.contains(entityItem1)) {
/* 3571 */                     vector5.add(entityItem1);
/* 3572 */                     arrayList.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */                   }
/*      */                 
/*      */                 } 
/* 3576 */               } else if (entityItem1 != null) {
/* 3577 */                 addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + entityItem.getKey() + " PS lastorder match ctry and osn " + str + " MODEL-lastorderavail " + entityItem1
/*      */                     
/* 3579 */                     .getKey() + " match for ctry " + arrayOfMetaFlag[b1].getFlagCode());
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 3584 */                 EntityItem entityItem2 = (EntityItem)hashtable3.get(arrayOfMetaFlag[b1].getFlagCode());
/*      */                 
/* 3586 */                 if (vector2.contains(entityItem2)) {
/* 3587 */                   addDebug("already chk dates for ps-lastorderavail " + entityItem2.getKey());
/*      */                 } else {
/*      */                   
/* 3590 */                   vector2.add(entityItem2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3607 */                   checkCanNotBeLater4(paramEntityItem, entityItem2, "EFFECTIVEDATE", entityItem1, "EFFECTIVEDATE", paramInt);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3616 */           for (b1 = 0; b1 < vector5.size(); b1++) {
/* 3617 */             EntityItem entityItem1 = vector5.elementAt(b1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3624 */             this.args[0] = getLD_NDN(getUpLinkEntityItem(paramEntityItem, paramString));
/* 3625 */             this.args[1] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 3626 */             if (str.equals("DOMAIN_NOT_IN_LIST")) {
/* 3627 */               this.args[2] = getLD_NDN(entityItem1);
/*      */             } else {
/* 3629 */               this.args[2] = getLD_NDN(entityItem1) + " for " + getLD_Value(entityItem1, "ORDERSYSNAME");
/*      */             } 
/* 3631 */             this.args[3] = getUnmatchedDescriptions(entityItem1, "COUNTRYLIST", arrayList);
/* 3632 */             createMessage(paramInt, "PS_LAST_ORDER_ERR2", this.args);
/*      */           } 
/* 3634 */           vector1.addAll(vector5);
/* 3635 */           vector5.clear();
/* 3636 */           arrayList.clear();
/*      */         } 
/*      */       } 
/*      */       
/* 3640 */       hashtable3.clear();
/*      */     } 
/*      */ 
/*      */     
/* 3644 */     vector1.clear();
/*      */     
/* 3646 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void matchPsModelLastOrderAvail(Vector paramVector, String paramString1, EntityGroup paramEntityGroup, String paramString2, String paramString3, boolean paramBoolean) throws MiddlewareException, SQLException {
/* 3651 */     int i = getCheck_W_RW_RE(paramString1);
/* 3652 */     if (paramBoolean) {
/* 3653 */       i = getCheck_W_RW_RW(paramString1);
/*      */     }
/* 3655 */     if (paramVector.size() > 0) {
/* 3656 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 3657 */       boolean bool = getAvailByOSN(hashtable, paramVector, true, 3);
/* 3658 */       addDebug("matchPsModelLastOrderAvail  mdlloOrMesloOsnErrors " + bool + " mdlloOrMesloAvailOSNTbl.keys " + hashtable
/* 3659 */           .keySet());
/* 3660 */       if (bool) {
/*      */         return;
/*      */       }
/*      */       
/* 3664 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 3665 */         EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 3666 */         boolean bool1 = true;
/* 3667 */         if (entityItem.getEntityType().equals("PRODSTRUCT")) {
/*      */           
/* 3669 */           String str = PokUtils.getAttributeFlagValue(entityItem, "ORDERCODE");
/* 3670 */           addDebug("matchPsModelLastOrderOrMesLastOrderAvail " + entityItem
/* 3671 */               .getKey() + " ordercode: " + str);
/* 3672 */           if (!"5957".equals(str)) {
/* 3673 */             bool1 = false;
/* 3674 */             addDebug("     ordercode was not initial, skipping checks");
/*      */           }
/*      */           else {
/*      */             
/* 3678 */             EntityItem entityItem1 = getUpLinkEntityItem(entityItem, "FEATURE");
/* 3679 */             addDebug("matchPsModelLastOrderOrMesLastOrderAvail  " + entityItem.getKey() + " " + entityItem1
/* 3680 */                 .getKey());
/* 3681 */             if (isRPQ(entityItem1)) {
/* 3682 */               addDebug(entityItem1.getKey() + " was an RPQ FCTYPE: " + 
/* 3683 */                   getAttributeFlagEnabledValue(entityItem1, "FCTYPE") + " skipping checks");
/* 3684 */               bool1 = false;
/*      */             } 
/*      */           } 
/*      */         } 
/* 3688 */         if (bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3722 */           Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, paramString3, "AVAIL");
/* 3723 */           Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 3724 */           Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "171");
/*      */           
/* 3726 */           Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "149");
/*      */           
/* 3728 */           Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "172");
/*      */           
/* 3730 */           addDebug("matchPsModelLastOrderAvail " + entityItem.getKey() + " all avail: " + vector1.size() + " plaAvail: " + vector2
/* 3731 */               .size() + " loAvail: " + vector4.size() + " mesPlaAvail: " + vector3
/* 3732 */               .size() + " mesloAvail: " + vector5
/* 3733 */               .size());
/*      */           
/* 3735 */           Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 3736 */           boolean bool2 = getAvailByOSN(hashtable1, vector4, true, 3);
/* 3737 */           Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 3738 */           boolean bool3 = getAvailByOSN(hashtable2, vector5, true, 3);
/*      */ 
/*      */           
/* 3741 */           Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/* 3742 */           boolean bool4 = getAvailByOSN(hashtable3, vector2, true, 3);
/* 3743 */           Hashtable<Object, Object> hashtable4 = new Hashtable<>();
/* 3744 */           boolean bool5 = getAvailByOSN(hashtable4, vector3, true, 3);
/*      */ 
/*      */           
/* 3747 */           addDebug("matchPsModelLastOrderAvail " + entityItem.getKey() + " loOsnErrors " + bool2 + " loAvailOSNTbl.keys " + hashtable1
/* 3748 */               .keySet() + " plaOsnErrors " + bool4 + " plaAvailOSNTbl.keys " + hashtable3
/* 3749 */               .keySet() + " mesplaOsnErrors " + bool5 + " mesplaAvailOSNTbl.keys " + hashtable4
/* 3750 */               .keySet() + " mesloOsnErrors " + bool3 + " mesloAvailOSNTbl.keys " + hashtable2
/* 3751 */               .keySet());
/*      */           
/* 3753 */           if (!bool4 && !bool2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3764 */             Vector<EntityItem> vector6 = new Vector();
/* 3765 */             Vector<EntityItem> vector7 = new Vector();
/*      */             
/* 3767 */             Set set = hashtable3.keySet();
/* 3768 */             Iterator<String> iterator = set.iterator();
/* 3769 */             while (iterator.hasNext()) {
/*      */               
/* 3771 */               String str = iterator.next();
/*      */               
/* 3773 */               Vector<EntityItem> vector = (Vector)hashtable3.get(str);
/* 3774 */               Vector vector8 = (Vector)hashtable.get(str);
/* 3775 */               if (vector8 == null) {
/* 3776 */                 addDebug("matchPsModelLastOrderOrMesLastOrderAvail no mdl loavails to check for osn " + str);
/*      */                 
/*      */                 continue;
/*      */               } 
/* 3780 */               Hashtable hashtable5 = getAvailByCountry(vector8, i);
/* 3781 */               addDebug("matchPsModelLastOrderAvail osn " + str + " mdllastOrderAvlCtryTbl: " + hashtable5
/* 3782 */                   .keySet());
/* 3783 */               Vector vector9 = (Vector)hashtable1.get(str);
/* 3784 */               Hashtable<Object, Object> hashtable6 = null;
/* 3785 */               if (vector9 != null) {
/* 3786 */                 hashtable6 = getAvailByCountry(vector9, i);
/*      */               } else {
/* 3788 */                 hashtable6 = new Hashtable<>();
/*      */               } 
/* 3790 */               addDebug("matchPsModelLastOrderAvail loOsnVct: " + ((vector9 == null) ? "null" : ("" + vector9
/* 3791 */                   .size())) + " PS-lastOrderAvlCtry: " + hashtable6
/* 3792 */                   .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3797 */               for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 3798 */                 EntityItem entityItem1 = vector.elementAt(b1);
/*      */                 
/* 3800 */                 EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(entityItem1, "COUNTRYLIST", i);
/*      */                 
/* 3802 */                 if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */                   
/* 3804 */                   MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 3805 */                   ArrayList<String> arrayList = new ArrayList();
/*      */                   
/* 3807 */                   Vector<EntityItem> vector10 = new Vector();
/*      */ 
/*      */                   
/*      */                   byte b2;
/*      */                   
/* 3812 */                   for (b2 = 0; b2 < arrayOfMetaFlag.length; b2++) {
/* 3813 */                     if (arrayOfMetaFlag[b2].isSelected()) {
/*      */ 
/*      */ 
/*      */                       
/* 3817 */                       EntityItem entityItem2 = (EntityItem)hashtable5.get(arrayOfMetaFlag[b2].getFlagCode());
/* 3818 */                       if (!hashtable6.containsKey(arrayOfMetaFlag[b2].getFlagCode())) {
/* 3819 */                         addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + entityItem1.getKey() + " No PS lastorderavail for osn " + str + " ctry " + arrayOfMetaFlag[b2]
/*      */                             
/* 3821 */                             .getFlagCode());
/* 3822 */                         if (entityItem2 != null) {
/* 3823 */                           addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + entityItem1.getKey() + " MODEL-lastorderavail " + entityItem2
/* 3824 */                               .getKey() + " match for ctry " + arrayOfMetaFlag[b2]
/* 3825 */                               .getFlagCode());
/* 3826 */                           if (vector6.contains(entityItem2)) {
/* 3827 */                             addDebug("already output msg for MODEL-lastorderavail " + entityItem2
/* 3828 */                                 .getKey());
/*      */                           
/*      */                           }
/* 3831 */                           else if (!vector10.contains(entityItem2)) {
/* 3832 */                             vector10.add(entityItem2);
/* 3833 */                             arrayList.add(arrayOfMetaFlag[b2].getFlagCode());
/*      */                           }
/*      */                         
/*      */                         } 
/* 3837 */                       } else if (entityItem2 != null) {
/* 3838 */                         addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + entityItem1.getKey() + " PS lastorder match ctry and osn " + str + " MODEL-lastorderavail " + entityItem2
/*      */                             
/* 3840 */                             .getKey() + " match for ctry " + arrayOfMetaFlag[b2]
/* 3841 */                             .getFlagCode());
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3846 */                         EntityItem entityItem3 = (EntityItem)hashtable6.get(arrayOfMetaFlag[b2].getFlagCode());
/*      */                         
/* 3848 */                         if (vector7.contains(entityItem3)) {
/* 3849 */                           addDebug("already chk dates for ps-lastorderavail " + entityItem3
/* 3850 */                               .getKey());
/*      */                         } else {
/*      */                           
/* 3853 */                           vector7.add(entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 3874 */                           checkCanNotBeLater4(entityItem, entityItem3, "EFFECTIVEDATE", entityItem2, "EFFECTIVEDATE", i);
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3883 */                   for (b2 = 0; b2 < vector10.size(); b2++) {
/* 3884 */                     EntityItem entityItem2 = vector10.elementAt(b2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 3891 */                     this.args[0] = getLD_NDN(getUpLinkEntityItem(entityItem, paramString2));
/* 3892 */                     this.args[1] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 3893 */                     if (str.equals("DOMAIN_NOT_IN_LIST")) {
/* 3894 */                       this.args[2] = getLD_NDN(entityItem2);
/*      */                     } else {
/* 3896 */                       this.args[2] = getLD_NDN(entityItem2) + " for " + 
/* 3897 */                         getLD_Value(entityItem2, "ORDERSYSNAME");
/*      */                     } 
/* 3899 */                     this.args[3] = getUnmatchedDescriptions(entityItem2, "COUNTRYLIST", arrayList);
/* 3900 */                     createMessage(i, "PS_LAST_ORDER_ERR2", this.args);
/*      */                   } 
/* 3902 */                   vector6.addAll(vector10);
/* 3903 */                   vector10.clear();
/* 3904 */                   arrayList.clear();
/*      */                 } 
/*      */               } 
/*      */               
/* 3908 */               hashtable6.clear();
/*      */             } 
/*      */ 
/*      */             
/* 3912 */             vector1.clear();
/* 3913 */             vector2.clear();
/* 3914 */             vector4.clear();
/*      */             
/* 3916 */             vector6.clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doHardwareChecks(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 4180 */     int i = getCheck_W_W_E(paramString);
/* 4181 */     String str1 = getAttributeFlagEnabledValue(paramEntityItem, "COFSUBCAT");
/* 4182 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, "FEATINDC");
/* 4183 */     addDebug("doHardwareChecks " + paramEntityItem.getKey() + " COFSUBCAT: " + str1 + " FEATINDC: " + str2);
/*      */ 
/*      */     
/* 4186 */     addHeading(3, "Hardware Model Checks:");
/*      */     
/* 4188 */     int j = 0;
/*      */ 
/*      */ 
/*      */     
/* 4192 */     if (!"FEATN".equals(str2)) {
/* 4193 */       j = getCount("PRODSTRUCT");
/* 4194 */       if (j == 0) {
/*      */ 
/*      */ 
/*      */         
/* 4198 */         this.args[0] = this.m_elist.getEntityGroup("FEATURE").getLongDescription();
/* 4199 */         createMessage(3, "MINIMUM_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4207 */     j = getCount("SWPRODSTRUCT");
/* 4208 */     if (j != 0) {
/*      */       
/* 4210 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("SWFEATURE");
/* 4211 */       this.args[0] = "Hardware";
/* 4212 */       this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 4213 */       this.args[2] = entityGroup.getLongDescription();
/* 4214 */       createMessage(3, "PSLINK_ERR", this.args);
/*      */     } 
/*      */ 
/*      */     
/* 4218 */     String str3 = PokUtils.getAttributeFlagValue(paramEntityItem, "WARRSVCCOVR");
/* 4219 */     addDebug("doHardwareChecks  warrsvccovr: " + str3);
/* 4220 */     j = getCount("MODELWARR");
/*      */     
/* 4222 */     if (!"WSVC01".equals(str3)) {
/* 4223 */       addHeading(3, this.m_elist.getEntityGroup("MODELWARR").getLongDescription() + " Coverage Checks:");
/*      */       
/* 4225 */       if (j == 0)
/*      */       {
/* 4227 */         this.args[0] = this.m_elist.getEntityGroup("WARR").getLongDescription();
/* 4228 */         createMessage(getCheck_RW_RW_RE(paramString), "MINIMUM_ERR", this.args);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4244 */         checkWarrCoverage(paramEntityItem, paramEntityItem, "MODELWARR", "MODELAVAIL", 4, 
/* 4245 */             getCheck_RW_RE_RE(paramString));
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 4251 */     else if (j != 0) {
/*      */       
/* 4253 */       this.args[0] = this.m_elist.getEntityGroup("WARR").getLongDescription();
/* 4254 */       createMessage(4, "MUST_NOT_HAVE_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4259 */     checkProdstructAvails(paramEntityItem, i);
/*      */ 
/*      */ 
/*      */     
/* 4263 */     checkRPQFeatures(paramEntityItem, paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4269 */     if (domainInRuleList(paramEntityItem, "XCC_LIST")) {
/* 4270 */       doHwXCCDomainChecks(paramEntityItem, paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkProdstructAvails(EntityItem paramEntityItem, int paramInt) throws SQLException, MiddlewareException {
/* 4318 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 4319 */     int i = getCheckLevel(paramInt, paramEntityItem, "ANNDATE");
/*      */ 
/*      */     
/* 4322 */     addHeading(3, entityGroup.getLongDescription() + " Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4328 */     addDebug("\ncheckProdstructAvails checking plannedavail ");
/* 4329 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 4330 */     boolean bool1 = getAvailByOSN(hashtable1, this.mdlLOAvailVctC, true, 3);
/* 4331 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 4332 */     boolean bool2 = getAvailByOSN(hashtable2, this.mdlPlaAvailVctA, true, 3);
/* 4333 */     addDebug("checkProdstructAvails  mdlPlaOsnErrors " + bool2 + " mdlPlaAvailOSNTbl.keys " + hashtable2
/* 4334 */         .keySet() + " mdlLoOsnErrors " + bool1 + " mdlLoAvailOSNTbl.keys " + hashtable1
/* 4335 */         .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4348 */     checkPsPlaAvailWithAvailA("146", entityGroup, hashtable2, bool2, paramInt, i);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4353 */     addHeading(3, entityGroup.getLongDescription() + " MES Planned Avail Checks:");
/* 4354 */     addDebug("\ncheckProdstructAvails checking mesplannedavail ");
/* 4355 */     checkPsPlaAvailWithAvailA("171", entityGroup, hashtable2, bool2, paramInt, i);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4360 */     addHeading(3, entityGroup.getLongDescription() + " First Order Avail Checks:");
/*      */ 
/*      */ 
/*      */     
/* 4364 */     checkPsFoAvailWithAvailAAndAvailD(entityGroup, hashtable2, bool2, paramEntityItem, paramInt, i);
/*      */ 
/*      */     
/* 4367 */     hashtable2.clear();
/*      */     
/* 4369 */     addHeading(3, entityGroup.getLongDescription() + " Last Order Avail Checks:");
/* 4370 */     checkPsLoAvailWithAvailDAndAvailC("149", entityGroup, hashtable1, bool1, paramInt, i);
/*      */     
/* 4372 */     addHeading(3, entityGroup.getLongDescription() + " MES Last Order Avail Checks:");
/* 4373 */     checkPsLoAvailWithAvailDAndAvailC("172", entityGroup, hashtable1, bool1, paramInt, i);
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
/*      */   private void checkPsLoAvailWithAvailDAndAvailC(String paramString, EntityGroup paramEntityGroup, Hashtable paramHashtable, boolean paramBoolean, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 4385 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 4386 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 4387 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "AVAIL");
/* 4388 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", paramString);
/* 4389 */       Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 4390 */       Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "171");
/* 4391 */       addDebug("checkProdstructAvails for " + entityItem.getKey() + " psloAvailVct " + vector2.size() + " psplannedAvailVct " + vector3
/* 4392 */           .size() + " psMesplannedAvailVct " + vector4
/* 4393 */           .size());
/* 4394 */       if (vector2.size() > 0) {
/* 4395 */         Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 4396 */         boolean bool1 = getAvailByOSN(hashtable1, vector2, true, 3);
/* 4397 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 4398 */         boolean bool2 = getAvailByOSN(hashtable2, vector3, true, 3);
/* 4399 */         Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/* 4400 */         boolean bool3 = getAvailByOSN(hashtable3, vector4, true, 3);
/* 4401 */         addDebug("checkProdstructAvails " + entityItem.getKey() + " plaOsnErrors " + bool2 + " plaAvailOSNTbl.keys " + hashtable2
/* 4402 */             .keySet() + " mesPlaOsnErrors " + bool3 + " mesPlaAvailOSNTbl.keys " + hashtable3
/* 4403 */             .keySet() + " loOsnErrors " + bool1 + " loAvailOSNTbl.keys " + hashtable1
/* 4404 */             .keySet());
/* 4405 */         if (!bool1 && !bool2)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4413 */           checkAvailCtryByOSN(hashtable1, hashtable2, "MISSING_PLA_OSNCTRY_ERR", entityItem, true, paramInt2);
/*      */         }
/*      */         
/* 4416 */         if (!bool1 && !bool3 && "172".equals(paramString))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4424 */           checkAvailCtryByOSN(hashtable1, hashtable3, "MISSING_PLA_OSNCTRY_ERR", entityItem, true, paramInt2);
/*      */         }
/*      */         
/* 4427 */         if (!bool1 && !paramBoolean) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4432 */           String str = PokUtils.getAttributeFlagValue(entityItem, "ORDERCODE");
/* 4433 */           addDebug("checkProdstructAvails " + entityItem.getKey() + " ordercode " + str);
/* 4434 */           if ("5957".equals(str))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4440 */             checkAvailDatesByCtryByOSN(hashtable1, paramHashtable, entityItem, 2, paramInt1, "Model", true);
/*      */           }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4465 */         hashtable1.clear();
/* 4466 */         hashtable2.clear();
/* 4467 */         hashtable3.clear();
/*      */       } 
/* 4469 */       vector1.clear();
/* 4470 */       vector2.clear();
/* 4471 */       vector3.clear();
/* 4472 */       vector4.clear();
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
/*      */ 
/*      */   
/*      */   private void checkPsFoAvailWithAvailAAndAvailD(EntityGroup paramEntityGroup, Hashtable paramHashtable, boolean paramBoolean, EntityItem paramEntityItem, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 4497 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 4498 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 4499 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "AVAIL");
/* 4500 */       Vector<EntityItem> vector = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "143");
/* 4501 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 4502 */       Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "171");
/* 4503 */       addDebug("checkProdstructAvails for " + entityItem.getKey() + " psfoAvailVct " + vector.size() + " psplannedAvailVct " + vector2
/* 4504 */           .size() + " psMesplannedAvailVct " + vector3
/* 4505 */           .size());
/*      */       
/* 4507 */       if (vector.size() > 0) {
/* 4508 */         Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 4509 */         boolean bool1 = getAvailByOSN(hashtable1, vector, true, 3);
/* 4510 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 4511 */         boolean bool2 = getAvailByOSN(hashtable2, vector2, true, 3);
/* 4512 */         Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/* 4513 */         boolean bool3 = getAvailByOSN(hashtable2, vector3, true, 3);
/* 4514 */         addDebug("checkProdstructAvails " + entityItem.getKey() + " plaOsnErrors " + bool2 + " plaAvailOSNTbl.keys " + hashtable2
/* 4515 */             .keySet() + " mesPlaOsnErrors " + bool3 + " mesPlaAvailOSNTbl.keys " + hashtable3
/* 4516 */             .keySet() + " foOsnErrors " + bool1 + " foAvailOSNTbl.keys " + hashtable1
/* 4517 */             .keySet());
/* 4518 */         if (!bool1 && !bool2)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4526 */           checkAvailCtryByOSN(hashtable1, hashtable2, "MISSING_PLA_OSNCTRY_ERR", entityItem, true, paramInt2);
/*      */         }
/*      */         
/* 4529 */         if (vector3 != null && vector3.size() > 0 && !bool1 && !bool3)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4538 */           checkAvailCtryByOSN(hashtable1, hashtable3, "MISSING_PLA_OSNCTRY_ERR", entityItem, true, paramInt2);
/*      */         }
/*      */ 
/*      */         
/* 4542 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 4543 */           EntityItem entityItem1 = vector.elementAt(b1);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4548 */           checkCanNotBeLater(entityItem1, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", paramInt1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4575 */         hashtable1.clear();
/* 4576 */         hashtable2.clear();
/* 4577 */         hashtable3.clear();
/*      */       } 
/* 4579 */       vector1.clear();
/* 4580 */       vector.clear();
/* 4581 */       vector2.clear();
/* 4582 */       vector3.clear();
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
/*      */   private void checkPsPlaAvailWithAvailA(String paramString, EntityGroup paramEntityGroup, Hashtable paramHashtable, boolean paramBoolean, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 4603 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 4604 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 4605 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "AVAIL");
/* 4606 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", paramString);
/* 4607 */       addDebug("checkProdstructAvails for " + entityItem.getKey() + " psplannedAvailVct " + vector2.size());
/* 4608 */       if (vector2.size() > 0) {
/* 4609 */         Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 4610 */         boolean bool = getAvailByOSN(hashtable, vector2, true, 3);
/* 4611 */         addDebug("checkProdstructAvails " + entityItem.getKey() + " plaOsnErrors " + bool + " plaAvailOSNTbl.keys " + hashtable
/* 4612 */             .keySet());
/* 4613 */         if (!bool && !paramBoolean) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4625 */           checkAvailCtryByOSN(hashtable, paramHashtable, "MODELROOT_AVAIL_OSNCTRY_ERR", entityItem, true, paramInt2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4633 */           checkAvailDatesByCtryByOSN(hashtable, paramHashtable, entityItem, 1, paramInt1, "Model", true);
/*      */         } 
/*      */         
/* 4636 */         hashtable.clear();
/*      */       } 
/* 4638 */       vector1.clear();
/* 4639 */       vector2.clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkRPQFeatures(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/* 4678 */     int i = getCheck_W_W_E(paramString);
/*      */     
/* 4680 */     Vector vector1 = PokUtils.getAllLinkedEntities(this.mdlPlaAvailVctA, "AVAILANNA", "ANNOUNCEMENT");
/* 4681 */     addDebug("checkRPQFeatures mdlplannedavail annVct: " + vector1.size());
/* 4682 */     vector1 = PokUtils.getEntitiesWithMatchedAttr(vector1, "ANNTYPE", "19");
/* 4683 */     addDebug("checkRPQFeatures NEW annVct: " + vector1.size());
/*      */     
/* 4685 */     Vector vector2 = PokUtils.getAllLinkedEntities(this.mdlLOAvailVctC, "AVAILANNA", "ANNOUNCEMENT");
/* 4686 */     addDebug("checkRPQFeatures mdllastorder annVct: " + vector2.size());
/* 4687 */     vector2 = PokUtils.getEntitiesWithMatchedAttr(vector2, "ANNTYPE", "14");
/* 4688 */     addDebug("checkRPQFeatures EOL annVct: " + vector2.size());
/*      */ 
/*      */     
/* 4691 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FEATURE");
/* 4692 */     addHeading(3, entityGroup.getLongDescription() + " RPQ Checks:");
/* 4693 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 4694 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 4696 */       if (isRPQ(entityItem)) {
/* 4697 */         addDebug("checkRPQFeatures " + entityItem.getKey() + " was an RPQ FCTYPE: " + 
/* 4698 */             getAttributeFlagEnabledValue(entityItem, "FCTYPE"));
/*      */         
/* 4700 */         for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 4701 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4752 */           String str = PokUtils.getAttributeFlagValue(entityItem1, "ORDERCODE");
/* 4753 */           addDebug("checkRPQFeatures " + entityItem1.getKey() + " ordercode " + str);
/* 4754 */           if ("5957".equals(str))
/*      */           {
/*      */             
/* 4757 */             addDebug("checkRPQFeatures testing PRODSTRUCT.WITHDRAWDATE cannot be later than MODEL.WITHDRAWDATE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4764 */             checkCanNotBeLater(entityItem1, "WITHDRAWDATE", paramEntityItem, "WITHDRAWDATE", i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4804 */             for (byte b2 = 0; b2 < this.mdlLOAvailVctC.size(); b2++) {
/* 4805 */               EntityItem entityItem2 = this.mdlLOAvailVctC.elementAt(b2);
/* 4806 */               checkCanNotBeLater(entityItem1, "WTHDRWEFFCTVDATE", entityItem2, "EFFECTIVEDATE", i);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 4817 */         addDebug("checkRPQFeatures " + entityItem.getKey() + " was NOT an RPQ FCTYPE: " + 
/* 4818 */             getAttributeFlagEnabledValue(entityItem, "FCTYPE"));
/*      */       } 
/*      */     } 
/*      */     
/* 4822 */     vector1.clear();
/* 4823 */     vector2.clear();
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
/*      */   private void doHwXCCDomainChecks(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 4867 */     addDebug("doHwXCCDomainChecks: entered");
/* 4868 */     addHeading(3, "Hardware Model XCC Checks:");
/*      */     
/* 4870 */     int i = getCheck_RW_RW_RE(paramString);
/*      */ 
/*      */     
/* 4873 */     int j = getCount("MDLCGMDL");
/* 4874 */     if (j == 0) {
/* 4875 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("MODELCG");
/*      */ 
/*      */       
/* 4878 */       this.args[0] = entityGroup.getLongDescription();
/* 4879 */       createMessage(i, "MUST_BE_IN_ATLEAST_ONE_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4896 */     checkUniqueCoverage(paramEntityItem, "MODELIMG", "IMG", this.mdlPlaAvailVctA, this.mdlLOAvailVctC, i, true);
/* 4897 */     this.IMGUniqueCoverageChkDone = true;
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
/*      */   private void doSoftwareChecks(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 5146 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "FEATINDC");
/* 5147 */     addDebug("doSoftwareChecks " + paramEntityItem.getKey() + " FEATINDC: " + str);
/*      */     
/* 5149 */     addHeading(3, " Software Model Checks:");
/* 5150 */     int i = getCheck_W_W_E(paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5156 */     int j = getCount("PRODSTRUCT");
/* 5157 */     if (j != 0) {
/* 5158 */       EntityGroup entityGroup1 = this.m_elist.getEntityGroup("FEATURE");
/* 5159 */       this.args[0] = "Software";
/* 5160 */       this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 5161 */       this.args[2] = entityGroup1.getLongDescription();
/* 5162 */       createMessage(getCheck_RE_RE_E(paramString), "PSLINK_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 5167 */     if (!"FEATN".equals(str)) {
/* 5168 */       j = getCount("SWPRODSTRUCT");
/* 5169 */       if (j == 0) {
/*      */ 
/*      */ 
/*      */         
/* 5173 */         this.args[0] = this.m_elist.getEntityGroup("SWFEATURE").getLongDescription();
/* 5174 */         createMessage(3, "MINIMUM_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5181 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 5182 */     addHeading(3, entityGroup.getLongDescription() + " Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5193 */     checkPsAndModelAvails(paramEntityItem, i, getCheckLevel(i, paramEntityItem, "ANNDATE"), this.mdlPlaAvailCtryTblA, this.swpsPlaAvailVctH, "SWPRODSTRUCTAVAIL", true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5200 */     addHeading(3, entityGroup.getLongDescription() + " MES Planned Avail Checks:");
/* 5201 */     checkPsAndModelAvails(paramEntityItem, i, getCheckLevel(i, paramEntityItem, "ANNDATE"), this.mdlPlaAvailCtryTblA, this.swpsMesPlaAvailVctH, "SWPRODSTRUCTAVAIL", true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5209 */     addHeading(3, entityGroup.getLongDescription() + " First Order Avail Checks:");
/*      */ 
/*      */     
/* 5212 */     checkFirstOrderAvails(paramEntityItem, paramString, entityGroup, "SWPRODSTRUCTAVAIL", i);
/*      */ 
/*      */     
/* 5215 */     addHeading(3, entityGroup.getLongDescription() + " Last Order Avail Checks:");
/*      */ 
/*      */ 
/*      */     
/* 5219 */     checkLastOrderAvails("149", paramString, entityGroup, "SWPRODSTRUCTAVAIL", i);
/*      */ 
/*      */     
/* 5222 */     addHeading(3, entityGroup.getLongDescription() + " MES Last Order Avail Checks:");
/* 5223 */     checkLastOrderAvails("172", paramString, entityGroup, "SWPRODSTRUCTAVAIL", i);
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
/*      */   private void doServiceChecks(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 5254 */     addHeading(3, " Service Model Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5259 */     int i = getCount("MDLCGMDL");
/* 5260 */     if (i != 0) {
/* 5261 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("MODELCG");
/*      */       
/* 5263 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 5264 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 5265 */         this.args[0] = getLD_NDN(entityItem);
/* 5266 */         createMessage(getCheck_RE_RE_E(paramString), "MUST_NOT_BE_IN_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 5272 */     i = getCount("MODELWWSEO");
/* 5273 */     if (i != 0) {
/* 5274 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("WWSEO");
/* 5275 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 5276 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 5277 */         Vector vector = PokUtils.getAllLinkedEntities(entityItem, "SEOCGSEO", "SEOCG");
/* 5278 */         if (vector != null && vector.size() > 0) {
/* 5279 */           for (EntityItem entityItem1 : vector) {
/*      */ 
/*      */             
/* 5282 */             this.args[0] = getLD_NDN(entityItem);
/* 5283 */             this.args[1] = getLD_NDN(entityItem1);
/* 5284 */             createMessage(getCheck_RE_RE_E(paramString), "MUST_NOT_BE_IN_ERR2", this.args);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5294 */     i = getCount("SEOCGMDL");
/* 5295 */     if (i != 0) {
/* 5296 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("SEOCG");
/*      */       
/* 5298 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 5299 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 5300 */         this.args[0] = getLD_NDN(entityItem);
/* 5301 */         createMessage(getCheck_RE_RE_E(paramString), "MUST_NOT_BE_IN_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5310 */     i = getCount("PRODSTRUCT");
/* 5311 */     if (i != 0) {
/* 5312 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("FEATURE");
/* 5313 */       this.args[0] = "Service";
/* 5314 */       this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 5315 */       this.args[2] = entityGroup.getLongDescription();
/* 5316 */       createMessage(getCheck_RE_RE_E(paramString), "PSLINK_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5324 */     i = getCount("SWPRODSTRUCT");
/* 5325 */     if (i != 0) {
/* 5326 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("SWFEATURE");
/* 5327 */       this.args[0] = "Service";
/* 5328 */       this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 5329 */       this.args[2] = entityGroup.getLongDescription();
/* 5330 */       createMessage(getCheck_RE_RE_E(paramString), "PSLINK_ERR", this.args);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkFirstOrderAvails(EntityItem paramEntityItem, String paramString1, EntityGroup paramEntityGroup, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 5360 */     int i = getCheckLevel(paramInt, paramEntityItem, "ANNDATE");
/*      */     
/* 5362 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 5363 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*      */       
/* 5365 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, paramString2, "AVAIL");
/* 5366 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 5367 */       Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "171");
/* 5368 */       Vector<EntityItem> vector = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "143");
/* 5369 */       Hashtable hashtable1 = getAvailByCountry(vector2, getCheck_W_W_E(paramString1));
/* 5370 */       Hashtable hashtable2 = getAvailByCountry(vector3, getCheck_W_W_E(paramString1));
/* 5371 */       addDebug("checkFirstOrderAvails " + entityItem.getKey() + ": " + paramString2 + "-d: AVAIL all avail:" + vector1
/* 5372 */           .size() + " psPla:" + vector2.size() + " psMesPla:" + vector3.size() + " psFO: " + vector
/* 5373 */           .size() + " psPlaAvailCtryTbl " + hashtable1.keySet() + " psMesPlaAvailCtryTbl " + hashtable2
/* 5374 */           .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5379 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 5380 */         EntityItem entityItem1 = vector.elementAt(b1);
/* 5381 */         Vector vector4 = new Vector();
/*      */ 
/*      */         
/* 5384 */         checkCtryMismatch(entityItem1, this.mdlPlaAvailCtryTblA, vector4, paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5389 */         String str = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", paramInt);
/* 5390 */         checkSwpsFoAvailWithAvailA(vector4, str, entityItem, entityItem1, paramInt);
/*      */ 
/*      */         
/* 5393 */         vector4.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5400 */         checkSwpsFoAvailWithAvailH(entityItem, entityItem1, hashtable1, i);
/* 5401 */         if (vector3.size() > 0) {
/* 5402 */           checkSwpsFoAvailWithAvailH(entityItem, entityItem1, hashtable2, i);
/*      */         }
/*      */       } 
/* 5405 */       vector1.clear();
/* 5406 */       vector2.clear();
/* 5407 */       vector3.clear();
/* 5408 */       vector.clear();
/* 5409 */       hashtable1.clear();
/* 5410 */       hashtable2.clear();
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
/*      */   private void checkSwpsFoAvailWithAvailA(Vector<EntityItem> paramVector, String paramString, EntityItem paramEntityItem1, EntityItem paramEntityItem2, int paramInt) throws SQLException, MiddlewareException {
/* 5433 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 5434 */       EntityItem entityItem = paramVector.elementAt(b);
/*      */ 
/*      */       
/* 5437 */       String str = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", paramInt);
/* 5438 */       addDebug("checkFirstOrderAvails  " + paramEntityItem1.getEntityType() + "-firstorder:" + paramEntityItem2.getKey() + " EFFECTIVEDATE:" + paramString + " can not be earlier than mdlplannedavail: " + entityItem
/* 5439 */           .getKey() + " EFFECTIVEDATE:" + str);
/*      */       
/* 5441 */       boolean bool = checkDates(paramString, str, 1);
/* 5442 */       if (!bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5447 */         this.args[0] = getLD_NDN(paramEntityItem1);
/* 5448 */         this.args[1] = getLD_NDN(paramEntityItem2);
/* 5449 */         this.args[2] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 5450 */         this.args[3] = getLD_NDN(entityItem);
/* 5451 */         createMessage(paramInt, "CANNOT_BE_EARLIER_ERR2", this.args);
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
/*      */   
/*      */   private void checkSwpsFoAvailWithAvailH(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Hashtable paramHashtable, int paramInt) throws SQLException, MiddlewareException {
/* 5471 */     String str = checkCtryMismatch(paramEntityItem2, paramHashtable, paramInt);
/* 5472 */     if (str.length() > 0) {
/* 5473 */       addDebug("checkFirstOrderAvails " + paramEntityItem1.getKey() + " firstorder:" + paramEntityItem2.getKey() + " COUNTRYLIST had ctry [" + str + "] that were not in the ps plannedavail");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5479 */       this.args[0] = getLD_NDN(paramEntityItem1) + " " + getLD_NDN(paramEntityItem2);
/* 5480 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 5481 */       this.args[2] = str;
/* 5482 */       createMessage(paramInt, "MISSING_PLA_CTRY_ERR", this.args);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkLastOrderAvails(String paramString1, String paramString2, EntityGroup paramEntityGroup, String paramString3, int paramInt) throws SQLException, MiddlewareException {
/* 5510 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 5511 */     int i = getCheckLevel(paramInt, entityItem, "ANNDATE");
/*      */     
/* 5513 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 5514 */       EntityItem entityItem1 = paramEntityGroup.getEntityItem(b);
/*      */       
/* 5516 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem1, paramString3, "AVAIL");
/* 5517 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 5518 */       Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "171");
/* 5519 */       Vector<EntityItem> vector = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", paramString1);
/* 5520 */       Hashtable hashtable1 = getAvailByCountry(vector2, getCheck_W_W_E(paramString2));
/* 5521 */       Hashtable hashtable2 = getAvailByCountry(vector3, getCheck_W_W_E(paramString2));
/* 5522 */       addDebug("checkLastOrderAvails " + entityItem1.getKey() + ": " + paramString3 + "-d: AVAIL all avail:" + vector1
/* 5523 */           .size() + " psPla:" + vector2.size() + " psMesPla:" + vector3.size() + " psLO: " + vector
/* 5524 */           .size() + " psPlaCtryTbl " + hashtable1.keySet() + " psMesPlaCtryTbl " + hashtable2
/* 5525 */           .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5530 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 5531 */         EntityItem entityItem2 = vector.elementAt(b1);
/* 5532 */         Vector vector4 = new Vector();
/*      */ 
/*      */         
/* 5535 */         checkCtryMismatch(entityItem2, this.mdlLOAvailCtryTblC, vector4, paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5541 */         String str = getAttrValueAndCheckLvl(entityItem2, "EFFECTIVEDATE", paramInt);
/* 5542 */         checkSwpsLoAvailWithAvailA(vector4, entityItem1, str, entityItem2, paramInt);
/*      */ 
/*      */ 
/*      */         
/* 5546 */         vector4.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5552 */         checkSwpsLoAvailWithAvailH(entityItem2, hashtable1, i, entityItem1);
/* 5553 */         if ("172".equals(paramString1)) {
/* 5554 */           checkSwpsLoAvailWithAvailH(entityItem2, hashtable2, i, entityItem1);
/*      */         }
/*      */       } 
/* 5557 */       vector1.clear();
/* 5558 */       vector2.clear();
/* 5559 */       vector.clear();
/* 5560 */       hashtable1.clear();
/* 5561 */       hashtable2.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSwpsLoAvailWithAvailA(Vector<EntityItem> paramVector, EntityItem paramEntityItem1, String paramString, EntityItem paramEntityItem2, int paramInt) throws SQLException, MiddlewareException {
/* 5568 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 5569 */       EntityItem entityItem = paramVector.elementAt(b);
/*      */ 
/*      */ 
/*      */       
/* 5573 */       String str = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", paramInt);
/* 5574 */       addDebug("checkLastOrderAvails " + paramEntityItem1.getEntityType() + "-lastorder:" + paramEntityItem2.getKey() + " EFFECTIVEDATE:" + paramString + " must not be later than mdllastorder: " + entityItem
/* 5575 */           .getKey() + " EFFECTIVEDATE:" + str);
/*      */       
/* 5577 */       boolean bool = checkDates(paramString, str, 2);
/* 5578 */       if (!bool) {
/*      */ 
/*      */         
/* 5581 */         this.args[0] = getLD_NDN(paramEntityItem1);
/* 5582 */         this.args[1] = getLD_NDN(paramEntityItem2);
/* 5583 */         this.args[2] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 5584 */         this.args[3] = getLD_NDN(entityItem);
/* 5585 */         createMessage(paramInt, "CANNOT_BE_LATER_ERR", this.args);
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
/*      */   
/*      */   private void checkSwpsLoAvailWithAvailH(EntityItem paramEntityItem1, Hashtable paramHashtable, int paramInt, EntityItem paramEntityItem2) throws SQLException, MiddlewareException {
/* 5605 */     String str = checkCtryMismatch(paramEntityItem1, paramHashtable, paramInt);
/* 5606 */     if (str.length() > 0) {
/* 5607 */       addDebug("checkLastOrderAvails  " + paramEntityItem2.getKey() + " lastorder:" + paramEntityItem1.getKey() + " COUNTRYLIST had ctry [" + str + "] that were not in the ps plannedavail");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5613 */       this.args[0] = getLD_NDN(paramEntityItem2) + " " + getLD_NDN(paramEntityItem1);
/* 5614 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 5615 */       this.args[2] = str;
/* 5616 */       createMessage(paramInt, "MISSING_PLA_CTRY_ERR", this.args);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 5626 */     return "MODEL ABR";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 5636 */     return "1.35";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\MODELABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */