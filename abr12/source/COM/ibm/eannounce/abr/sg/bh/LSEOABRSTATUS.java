/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
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
/*      */ public class LSEOABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*      */   protected boolean isVEneeded(String paramString) {
/*  106 */     return true;
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  223 */     doLSEOSectionTwo();
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
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  236 */     checkAssortModule();
/*      */     
/*  238 */     doLSEOSectionTwo();
/*      */   }
/*      */   private void doLSEOSectionTwo() throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  241 */     String str = "";
/*  242 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("WWSEO");
/*      */     
/*  244 */     if (entityGroup.getEntityItemCount() > 0) {
/*  245 */       EntityItem entityItem = entityGroup.getEntityItem(0);
/*  246 */       str = getAttributeFlagEnabledValue(entityItem, "SPECBID");
/*  247 */       addDebug(entityItem.getKey() + " SPECBID: " + str);
/*      */     } 
/*  249 */     doLSEOSectionTwo(new EntityItem[] { this.m_elist.getParentEntityGroup().getEntityItem(0) }, str);
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
/*      */   protected boolean updateDerivedData(EntityItem paramEntityItem) throws Exception {
/*  266 */     boolean bool = false;
/*      */     
/*  268 */     String str = PokUtils.getAttributeValue(paramEntityItem, "LSEOUNPUBDATEMTRGT", "", "9999-12-31", false);
/*  269 */     addDebug("updateDerivedData wdDate: " + str + " now: " + getCurrentDate());
/*  270 */     if (getCurrentDate().compareTo(str) <= 0) {
/*  271 */       bool = execDerivedData(paramEntityItem);
/*      */     }
/*  273 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLCRFRWFName() {
/*  280 */     return "WFLCLSEORFR"; } protected String getLCFinalWFName() {
/*  281 */     return "WFLCLSEOFINAL";
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  646 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*      */     
/*  648 */     int i = getCheck_W_W_E(paramString);
/*  649 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "BHPRODHIERCD");
/*      */     
/*  651 */     addDebug("checking " + paramEntityItem.getKey() + " (3) prodhierFlag " + str);
/*      */ 
/*      */ 
/*      */     
/*  655 */     checkCanNotBeEarlier(paramEntityItem, "LSEOUNPUBDATEMTRGT", "LSEOPUBDATEMTRGT", i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  670 */     int j = getCount("WWSEOLSEO");
/*  671 */     if (j == 1) {
/*  672 */       EntityItem entityItem = this.m_elist.getEntityGroup("WWSEO").getEntityItem(0);
/*  673 */       addDebug("checking " + entityItem.getKey() + " (6)");
/*      */ 
/*      */       
/*  676 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */       
/*  678 */       String str1 = getAttributeFlagEnabledValue(entityItem, "SPECBID");
/*  679 */       addDebug(entityItem.getKey() + " SPECBID: " + str1);
/*  680 */       if ("11457".equals(str1)) {
/*  681 */         doGAChecks(paramEntityItem, entityItem, paramString);
/*      */       } else {
/*  683 */         doSpecBidChecks(paramEntityItem, entityItem, paramString);
/*      */       } 
/*      */       
/*  686 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "MODELWWSEO", "MODEL");
/*  687 */       for (byte b = 0; b < vector.size(); b++) {
/*  688 */         EntityItem entityItem1 = vector.elementAt(b);
/*  689 */         String str2 = getAttributeFlagEnabledValue(entityItem1, "COFCAT");
/*      */         
/*  691 */         addDebug(entityItem1.getKey() + " modelCOFCAT: " + str2);
/*      */         
/*  693 */         if ("100".equals(str2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  698 */           checkProdstructs(paramEntityItem, entityItem1, "WWSEOSWPRODSTRUCT", "LSEOSWPRODSTRUCT");
/*      */         }
/*  700 */         else if ("101".equals(str2)) {
/*      */ 
/*      */ 
/*      */           
/*  704 */           checkProdstructs(paramEntityItem, entityItem1, "WWSEOPRODSTRUCT", "LSEOPRODSTRUCT");
/*      */         }
/*  706 */         else if ("102".equals(str2)) {
/*      */ 
/*      */ 
/*      */           
/*  710 */           checkProdstructs(paramEntityItem, entityItem1, "WWSEOPRODSTRUCT", "LSEOPRODSTRUCT");
/*      */ 
/*      */           
/*  713 */           checkProdstructs(paramEntityItem, entityItem1, "WWSEOSWPRODSTRUCT", "LSEOSWPRODSTRUCT");
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  718 */       vector.clear();
/*      */     } else {
/*      */       
/*  721 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("WWSEO");
/*  722 */       this.args[0] = entityGroup.getLongDescription();
/*      */       
/*  724 */       createMessage(4, "REQUIRES_ONE_PARENT_ERR", this.args);
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
/*      */   private void checkProdstructs(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, String paramString2) throws MiddlewareException {
/*  738 */     addHeading(3, this.m_elist.getParentEntityGroup().getLongDescription() + " " + this.m_elist
/*  739 */         .getEntityGroup(paramString2).getLongDescription() + " Checks:");
/*  740 */     int i = getCount(paramString2);
/*  741 */     if (i > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  746 */       EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*  747 */       this.args[0] = PokUtils.getAttributeValue(paramEntityItem2, "COFCAT", "", "", false);
/*  748 */       this.args[1] = entityGroup.getLongDescription();
/*  749 */       this.args[2] = this.m_elist.getEntityGroup(paramString2).getLongDescription();
/*      */       
/*  751 */       createMessage(4, "PSLINK_ERR", this.args);
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
/*      */   private void doSpecBidChecks(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString) throws Exception {
/*  782 */     addHeading(2, paramEntityItem1.getEntityGroup().getLongDescription() + " Special Bid Checks:");
/*      */ 
/*      */ 
/*      */     
/*  786 */     int i = getCount("LSEOAVAIL");
/*  787 */     if (i != 0) {
/*  788 */       this.args[0] = this.m_elist.getEntityGroup("AVAIL").getLongDescription();
/*  789 */       createMessage(3, "SPECBID_AVAIL_ERR", this.args);
/*      */     } 
/*  791 */     int j = getCheck_W_E_E(paramString);
/*      */     
/*  793 */     String str1 = getAttrValueAndCheckLvl(paramEntityItem1, "LSEOPUBDATEMTRGT", j);
/*  794 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem1, "LSEOUNPUBDATEMTRGT", j);
/*      */     
/*  796 */     ArrayList<?> arrayList = new ArrayList();
/*  797 */     getCountriesAsList(paramEntityItem1, arrayList, getCheck_W_RE_RE(paramString));
/*  798 */     addDebug("doSpecBidChecks lseoCtry " + arrayList);
/*      */     
/*  800 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELWWSEO", "MODEL");
/*      */ 
/*      */     
/*  803 */     if (vector.size() > 0) {
/*      */       
/*  805 */       EntityItem entityItem = vector.firstElement();
/*  806 */       EntityList entityList = getModelVE(entityItem);
/*  807 */       entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*  808 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "MODELAVAIL", "AVAIL");
/*      */       
/*  810 */       Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/*  811 */       addDebug("doSpecBidChecks " + entityItem.getKey() + " mdlavailVct: " + vector1.size() + " mdlPlaVct " + vector2
/*  812 */           .size());
/*  813 */       if (vector2.size() > 0) {
/*      */         
/*  815 */         ArrayList<?> arrayList1 = getCountriesAsList(vector2, j);
/*  816 */         addDebug("doSpecBidChecks  mdlplaCtry " + arrayList1);
/*      */ 
/*      */ 
/*      */         
/*  820 */         if (!arrayList1.containsAll(arrayList)) {
/*  821 */           EntityItem entityItem1 = vector2.firstElement();
/*      */           
/*  823 */           ArrayList<?> arrayList2 = new ArrayList();
/*  824 */           arrayList2.addAll(arrayList);
/*  825 */           arrayList2.removeAll(arrayList1);
/*      */           
/*  827 */           this.args[0] = "";
/*  828 */           this.args[1] = "";
/*  829 */           this.args[2] = getLD_NDN(entityItem) + " " + entityItem1
/*  830 */             .getEntityGroup().getLongDescription();
/*  831 */           this.args[3] = "Planned Availability";
/*  832 */           this.args[4] = getUnmatchedDescriptions(entityItem1, "COUNTRYLIST", arrayList2);
/*  833 */           createMessage(j, "INCLUDE_ERR2", this.args);
/*  834 */           arrayList2.clear();
/*      */         } 
/*  836 */         arrayList1.clear();
/*      */       } 
/*      */       
/*  839 */       vector1.clear();
/*  840 */       vector2.clear();
/*  841 */       entityList.dereference();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  846 */     for (byte b = 0; b < vector.size(); b++) {
/*  847 */       EntityItem entityItem = vector.elementAt(b);
/*  848 */       String str = getAttributeFlagEnabledValue(entityItem, "COFCAT");
/*  849 */       addDebug("doSpecBidChecks " + entityItem.getKey() + " SPECBID COFCAT: " + str);
/*      */       
/*  851 */       if ("100".equals(str)) {
/*      */ 
/*      */         
/*  854 */         doHWSpecBidChecks(paramEntityItem1, str1, str2, arrayList, paramEntityItem2, paramString);
/*  855 */       } else if ("101".equals(str)) {
/*      */ 
/*      */         
/*  858 */         doSWSpecBidChecks(paramEntityItem1, str1, str2, arrayList, paramEntityItem2, paramString);
/*  859 */       } else if ("102".equals(str)) {
/*      */       
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  867 */     vector.clear();
/*  868 */     arrayList.clear();
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
/*      */   private void doHWSpecBidChecks(EntityItem paramEntityItem1, String paramString1, String paramString2, ArrayList paramArrayList, EntityItem paramEntityItem2, String paramString3) throws Exception {
/*  981 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem2, "WWSEOPRODSTRUCT", "PRODSTRUCT");
/*  982 */     Vector vector2 = PokUtils.getAllLinkedEntities(paramEntityItem1, "LSEOPRODSTRUCT", "PRODSTRUCT");
/*  983 */     addDebug("doHWSpecBidChecks wwseopsVct " + vector1.size() + " lseopsVct " + vector2.size());
/*      */     
/*  985 */     if (vector1.size() + vector2.size() == 0) {
/*  986 */       this.args[0] = this.m_elist.getEntityGroup("FEATURE").getLongDescription();
/*      */       
/*  988 */       createMessage(getCheck_W_E_E(paramString3), "MINIMUM_ERR", this.args);
/*      */     } 
/*      */     
/*  991 */     addHeading(3, "Hardware LSEO Special Bid Avail Checks:");
/*  992 */     addDebug("\ndoHWSpecBidChecks lseops chks");
/*      */     
/*  994 */     checkHWSpecBidPsAvails(paramEntityItem1, vector2, paramString1, paramString2, paramArrayList, false, paramString3);
/*  995 */     addHeading(3, "Hardware WWSEO Special Bid Avail Checks:");
/*  996 */     addDebug("\ndoHWSpecBidChecks wwseops chks");
/*      */     
/*  998 */     checkHWSpecBidPsAvails(paramEntityItem1, vector1, paramString1, paramString2, paramArrayList, true, paramString3);
/*      */     
/* 1000 */     vector1.clear();
/* 1001 */     vector2.clear();
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
/*      */   private void checkSpecBidPsAvails(EntityItem paramEntityItem, Vector paramVector, String paramString1, String paramString2, String paramString3, String paramString4, ArrayList<?> paramArrayList, String paramString5) throws MiddlewareException, SQLException {
/* 1043 */     Vector vector = PokUtils.getAllLinkedEntities(paramVector, paramString2, "AVAIL");
/*      */ 
/*      */ 
/*      */     
/* 1047 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 1048 */     ArrayList arrayList = getCountriesAsList(vector1, getCheck_W_RE_RE(paramString5));
/*      */     
/* 1050 */     addDebug("checkSpecBidPsAvails " + paramString1 + "-" + paramString2 + " psAvailVct: " + vector
/* 1051 */         .size() + " psPlaAvailVct: " + vector1.size() + " allPsPlaAvailCtry " + arrayList);
/*      */     
/* 1053 */     if (vector1.size() > 0) {
/*      */       byte b;
/*      */ 
/*      */       
/* 1057 */       for (b = 0; b < vector1.size(); b++) {
/* 1058 */         EntityItem entityItem1 = vector1.elementAt(b);
/* 1059 */         EntityItem entityItem2 = getAvailPS(entityItem1, paramString2);
/*      */         
/* 1061 */         if (hasAnyCountryMatch(entityItem1, paramArrayList)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1068 */           checkDateforPlanAvailToLSEO(paramEntityItem, paramString5, entityItem1);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1074 */           ArrayList arrayList1 = new ArrayList();
/*      */           
/* 1076 */           getCountriesAsList(entityItem1, arrayList1, -1);
/* 1077 */           addDebug("checkSpecBidPsAvails skipping date check " + entityItem1.getKey() + " availCtrylist: " + arrayList1 + " did not contain any lseoCtry: " + paramArrayList);
/*      */           
/* 1079 */           arrayList1.clear();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1086 */       if (!arrayList.containsAll(paramArrayList)) {
/* 1087 */         addDebug("checkSpecBidPsAvails  allPsPlaAvailCtry: " + arrayList + " did not contain all lseoCtry: " + paramArrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1092 */         this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 1093 */         for (b = 0; b < vector1.size(); b++) {
/* 1094 */           EntityItem entityItem1 = vector1.elementAt(b);
/* 1095 */           EntityItem entityItem2 = getAvailPS(entityItem1, paramString2);
/* 1096 */           ArrayList<?> arrayList1 = new ArrayList();
/*      */           
/* 1098 */           getCountriesAsList(entityItem1, arrayList1, -1);
/* 1099 */           if (!arrayList1.containsAll(paramArrayList)) {
/* 1100 */             addDebug("checkSpecBidPsAvails psPlaAvail " + entityItem1.getKey() + " availCtrylist: " + arrayList1 + " did not contain all lseoCtry: " + paramArrayList);
/*      */             
/* 1102 */             this.args[1] = getLD_NDN(entityItem2);
/* 1103 */             this.args[2] = getLD_NDN(entityItem1);
/* 1104 */             ArrayList arrayList2 = (ArrayList)paramArrayList.clone();
/* 1105 */             arrayList2.removeAll(arrayList1);
/* 1106 */             this.args[3] = getUnmatchedDescriptions(entityItem1, "COUNTRYLIST", arrayList2);
/* 1107 */             createMessage(getCheck_W_RE_RE(paramString5), "EXTRA_CTRY_ERR", this.args);
/*      */           } 
/* 1109 */           arrayList1.clear();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1115 */       vector.clear();
/* 1116 */       vector1.clear();
/* 1117 */       arrayList.clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1156 */     vector.clear();
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
/*      */   private void checkHWSpecBidPsAvails(EntityItem paramEntityItem, Vector<EntityItem> paramVector, String paramString1, String paramString2, ArrayList paramArrayList, boolean paramBoolean, String paramString3) throws MiddlewareException, SQLException {
/* 1277 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1278 */       EntityItem entityItem1 = paramVector.elementAt(b);
/* 1279 */       EntityItem entityItem2 = getUpLinkEntityItem(entityItem1, "FEATURE");
/* 1280 */       addDebug("checkHWSpecBidPsAvails  " + entityItem1.getKey() + " " + entityItem2.getKey());
/*      */       
/* 1282 */       if (!isRPQ(entityItem2)) {
/* 1283 */         addDebug(entityItem2.getKey() + " was NOT an RPQ FCTYPE: " + getAttributeFlagEnabledValue(entityItem2, "FCTYPE"));
/* 1284 */         checkNonRPQAvails(paramEntityItem, entityItem1, paramString1, paramString2, paramArrayList, paramString3);
/*      */       } else {
/* 1286 */         addDebug(entityItem2.getKey() + " was an RPQ FCTYPE: " + getAttributeFlagEnabledValue(entityItem2, "FCTYPE"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1315 */         checkRPQFeature(paramEntityItem, entityItem1, entityItem2, paramBoolean);
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
/*      */   private void checkNonRPQAvails(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, String paramString2, ArrayList<?> paramArrayList, String paramString3) throws MiddlewareException, SQLException {
/* 1382 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "OOFAVAIL", "AVAIL");
/*      */ 
/*      */ 
/*      */     
/* 1386 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 1387 */     ArrayList arrayList = getCountriesAsList(vector1, getCheck_W_E_E(paramString3));
/*      */     
/* 1389 */     addDebug("checkNonRPQAvails  psAvailVct: " + vector
/* 1390 */         .size() + " psPlaAvailVct: " + vector1.size() + " allPsPlaAvailCtry " + arrayList);
/* 1391 */     if (vector1.size() > 0) {
/*      */       byte b;
/*      */ 
/*      */       
/* 1395 */       for (b = 0; b < vector1.size(); b++) {
/* 1396 */         EntityItem entityItem = vector1.elementAt(b);
/*      */         
/* 1398 */         if (hasAnyCountryMatch(entityItem, paramArrayList)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1405 */           addDebug("checkNonRPQAvails skipping date check: has matched countries ");
/* 1406 */           checkDateforPlanAvailToLSEO(paramEntityItem1, paramString3, entityItem);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1412 */           ArrayList arrayList1 = new ArrayList();
/*      */           
/* 1414 */           getCountriesAsList(entityItem, arrayList1, -1);
/* 1415 */           addDebug("checkNonRPQAvails skipping date check " + entityItem.getKey() + " availCtrylist: " + arrayList1 + " did not contain any lseoCtry: " + paramArrayList);
/*      */           
/* 1417 */           arrayList1.clear();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1424 */       if (!arrayList.containsAll(paramArrayList)) {
/* 1425 */         addDebug("checkNonRPQAvails allPsPlaAvailCtry " + arrayList + " did not contain all lseoCtry: " + paramArrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1430 */         this.args[0] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */         
/* 1432 */         for (b = 0; b < vector1.size(); b++) {
/* 1433 */           EntityItem entityItem = vector1.elementAt(b);
/* 1434 */           ArrayList<?> arrayList1 = new ArrayList();
/*      */           
/* 1436 */           getCountriesAsList(entityItem, arrayList1, -1);
/* 1437 */           if (!arrayList1.containsAll(paramArrayList)) {
/* 1438 */             addDebug("checkNonRPQAvails " + entityItem.getKey() + " availCtrylist: " + arrayList1 + " did not contain all lseoCtry: " + paramArrayList);
/*      */             
/* 1440 */             this.args[1] = getLD_NDN(paramEntityItem2);
/* 1441 */             this.args[2] = getLD_NDN(entityItem);
/* 1442 */             ArrayList arrayList2 = (ArrayList)paramArrayList.clone();
/* 1443 */             arrayList2.removeAll(arrayList1);
/* 1444 */             this.args[3] = getUnmatchedDescriptions(entityItem, "COUNTRYLIST", arrayList2);
/*      */             
/* 1446 */             createMessage(getCheck_W_E_E(paramString3), "EXTRA_CTRY_ERR", this.args);
/*      */           } 
/* 1448 */           arrayList1.clear();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1453 */       vector1.clear();
/*      */     } 
/*      */     
/* 1456 */     vector.clear();
/*      */     
/* 1458 */     Vector<EntityItem> vector2 = new Vector(1);
/* 1459 */     vector2.add(paramEntityItem1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1485 */     checkLseoPSLOAvail(paramEntityItem2, "PRODSTRUCTCATLGOR", "OOFAVAIL", vector2, 4, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1490 */     vector2.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkDateforPlanAvailToLSEO(EntityItem paramEntityItem1, String paramString, EntityItem paramEntityItem2) throws SQLException, MiddlewareException {
/* 1496 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "AVAILANNA", "ANNOUNCEMENT");
/* 1497 */     addDebug("Avail infor: " + paramEntityItem2.getKey() + " ANNOUNCEMENT: annVct.size = " + vector.size());
/* 1498 */     for (byte b = 0; b < vector.size(); b++) {
/* 1499 */       EntityItem entityItem = vector.get(b);
/* 1500 */       String str = getAttributeFlagEnabledValue(entityItem, "ANNTYPE");
/* 1501 */       addDebug("ANNOUNCEMENT: anntype = " + str);
/* 1502 */       if ("19".equalsIgnoreCase(str)) {
/* 1503 */         checkCanNotBeEarlier(paramEntityItem1, "LSEOPUBDATEMTRGT", entityItem, "ANNDATE", getCheck_W_E_E(paramString));
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
/*      */   private void checkRPQFeature(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 1555 */     ArrayList arrayList = new ArrayList();
/*      */     
/* 1557 */     getCountriesAsList(paramEntityItem3, arrayList, 4);
/* 1558 */     addDebug("checkRPQFeature " + paramEntityItem3.getKey() + " featCtrylist " + arrayList);
/*      */ 
/*      */ 
/*      */     
/* 1562 */     checkCanNotBeEarlier(paramEntityItem1, "LSEOPUBDATEMTRGT", paramEntityItem2, "ANNDATE", 4);
/*      */ 
/*      */ 
/*      */     
/* 1566 */     checkCanNotBeLater(paramEntityItem1, "LSEOUNPUBDATEMTRGT", paramEntityItem2, "WTHDRWEFFCTVDATE", 4);
/*      */ 
/*      */ 
/*      */     
/* 1570 */     checkCanNotBeEarlier(paramEntityItem1, "LSEOPUBDATEMTRGT", paramEntityItem3, "FIRSTANNDATE", 4);
/*      */ 
/*      */ 
/*      */     
/* 1574 */     checkCanNotBeLater(paramEntityItem1, "LSEOUNPUBDATEMTRGT", paramEntityItem3, "WITHDRAWDATEEFF_T", 4);
/*      */ 
/*      */ 
/*      */     
/* 1578 */     checkAvailCtryInEntity((EntityItem)null, paramEntityItem1, paramEntityItem3, arrayList, 4);
/*      */     
/* 1580 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "PRODSTRUCTCATLGOR", "CATLGOR");
/* 1581 */     addDebug("checkRPQFeature: catlgorVct " + vector.size());
/* 1582 */     if (vector.size() > 0) {
/* 1583 */       Vector<EntityItem> vector1 = new Vector();
/* 1584 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 1585 */         EntityItem entityItem = vector.elementAt(b1);
/* 1586 */         String str1 = PokUtils.getAttributeValue(entityItem, "PUBTO", "", null, false);
/* 1587 */         if (str1 != null) {
/* 1588 */           vector1.add(entityItem);
/*      */         }
/*      */       } 
/* 1591 */       vector.clear();
/* 1592 */       vector = vector1;
/* 1593 */       addDebug("checkRPQFeature: catlgorVct with pubto values: " + vector.size());
/*      */     } 
/* 1595 */     boolean bool = false;
/* 1596 */     String str = PokUtils.getAttributeValue(paramEntityItem1, "LSEOPUBDATEMTRGT", "", null, false);
/* 1597 */     addDebug("checkRPQFeature  lseopubdate " + str);
/*      */     
/* 1599 */     for (byte b = 0; b < vector.size(); b++) {
/* 1600 */       EntityItem entityItem = vector.elementAt(b);
/* 1601 */       String str1 = PokUtils.getAttributeValue(entityItem, "PUBTO", "", null, false);
/* 1602 */       addDebug("checkRPQFeature  " + entityItem.getKey() + " pubto " + str1);
/*      */ 
/*      */       
/* 1605 */       if (str.compareTo(str1) < 0) {
/* 1606 */         bool = true;
/*      */ 
/*      */         
/* 1609 */         checkCanNotBeLater4(paramEntityItem2, paramEntityItem1, "LSEOUNPUBDATEMTRGT", entityItem, "PUBTO", 4);
/*      */         break;
/*      */       } 
/*      */     } 
/* 1613 */     if (!bool) {
/* 1614 */       addDebug("checkRPQFeature did not use catlgor");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1619 */       checkCanNotBeLater(paramEntityItem1, "LSEOUNPUBDATEMTRGT", paramEntityItem2, "WTHDRWEFFCTVDATE", 4);
/*      */       
/* 1621 */       if (!paramBoolean)
/*      */       {
/* 1623 */         checkCanNotBeLater(paramEntityItem1, "LSEOUNPUBDATEMTRGT", paramEntityItem3, "WITHDRAWDATEEFF_T", 4);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1634 */     if (paramBoolean) {
/* 1635 */       EntityItem entityItem = getDownLinkEntityItem(paramEntityItem2, "MODEL");
/* 1636 */       addDebug("checkRPQFeature thruwwseops " + paramEntityItem2.getKey() + " " + entityItem.getKey());
/*      */       
/* 1638 */       checkCanNotBeEarlier(paramEntityItem1, "LSEOPUBDATEMTRGT", entityItem, "ANNDATE", 4);
/*      */       
/* 1640 */       checkCanNotBeLater(paramEntityItem1, "LSEOUNPUBDATEMTRGT", entityItem, "WTHDRWEFFCTVDATE", 4);
/*      */       
/* 1642 */       checkCanNotBeLater(paramEntityItem1, "LSEOUNPUBDATEMTRGT", paramEntityItem3, "WITHDRAWDATEEFF_T", 4);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1684 */     arrayList.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityList getModelVE(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1695 */     String str = "DQVEMODELAVAIL";
/*      */     
/* 1697 */     EntityList entityList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 1698 */           .getProfile(), str), new EntityItem[] { new EntityItem(null, this.m_elist
/* 1699 */             .getProfile(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID()) });
/* 1700 */     addDebug("getModelVE:: Extract " + str + NEWLINE + PokUtils.outputList(entityList));
/*      */     
/* 1702 */     return entityList;
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
/*      */   private void doSWSpecBidChecks(EntityItem paramEntityItem1, String paramString1, String paramString2, ArrayList paramArrayList, EntityItem paramEntityItem2, String paramString3) throws Exception {
/* 1766 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem2, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
/* 1767 */     Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem1, "LSEOSWPRODSTRUCT", "SWPRODSTRUCT");
/*      */     
/* 1769 */     addDebug("doSWSpecBidChecks wwseoswpsVct " + vector1.size() + " lseoswpsVct " + vector2.size());
/*      */     
/* 1771 */     addHeading(3, "Software LSEO Special Bid Avail Checks:");
/* 1772 */     addDebug("\ndoSWSpecBidChecks lseoswps chks");
/*      */     
/* 1774 */     checkSpecBidPsAvails(paramEntityItem1, vector2, "SWPRODSTRUCT", "SWPRODSTRUCTAVAIL", paramString1, paramString2, paramArrayList, paramString3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1787 */     Vector<EntityItem> vector3 = new Vector(1);
/* 1788 */     vector3.add(paramEntityItem1); byte b;
/* 1789 */     for (b = 0; b < vector2.size(); b++) {
/* 1790 */       EntityItem entityItem = vector2.elementAt(b);
/* 1791 */       checkLseoPSLOAvail(entityItem, "SWPRODSTRCATLGOR", "SWPRODSTRUCTAVAIL", vector3, 3, 4);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1796 */     addHeading(3, "Software WWSEO Special Bid Avail Checks:");
/* 1797 */     addDebug("\ndoSWSpecBidChecks wwseoswps chks");
/*      */     
/* 1799 */     checkSpecBidPsAvails(paramEntityItem1, vector1, "SWPRODSTRUCT", "SWPRODSTRUCTAVAIL", paramString1, paramString2, paramArrayList, paramString3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1813 */     for (b = 0; b < vector1.size(); b++) {
/* 1814 */       EntityItem entityItem = vector1.elementAt(b);
/* 1815 */       checkLseoPSLOAvail(entityItem, "SWPRODSTRCATLGOR", "SWPRODSTRUCTAVAIL", vector3, 3, 4);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1821 */     vector1.clear();
/* 1822 */     vector2.clear();
/* 1823 */     vector3.clear();
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
/*      */   private void doGAChecks(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString) throws Exception {
/* 1943 */     addHeading(2, paramEntityItem1.getEntityGroup().getLongDescription() + " GA Checks:");
/*      */     
/* 1945 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem1, "LSEOAVAIL", "AVAIL");
/*      */ 
/*      */ 
/*      */     
/* 1949 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "149");
/* 1950 */     Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 1951 */     Vector<EntityItem> vector4 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "143");
/* 1952 */     Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "151");
/* 1953 */     Vector vector6 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "200");
/* 1954 */     addDebug("doGAChecks lseo-loAvailVct " + vector2.size() + " lseo-plannedAvailVct " + vector3
/* 1955 */         .size() + " lseo-foAvailVct " + vector4.size() + " lseo-eosAvailVct " + vector5
/* 1956 */         .size() + " lseo-eomAvailVct " + vector6.size());
/*      */     
/* 1958 */     int i = getCheck_W_W_E(paramString);
/*      */     
/* 1960 */     ArrayList arrayList = new ArrayList();
/*      */     
/* 1962 */     getCountriesAsList(paramEntityItem1, arrayList, i);
/*      */     
/* 1964 */     addDebug("doGAChecks " + paramEntityItem1.getKey() + " ctrylist: " + arrayList);
/* 1965 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " GA Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1970 */     checkPlannedAvailsExist(vector3, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1977 */     checkPlannedAvailsStatus(vector3, paramEntityItem1, 3);
/*      */     
/* 1979 */     if (vector3.size() > 0) {
/*      */       
/* 1981 */       ArrayList<?> arrayList1 = null;
/* 1982 */       EntityList entityList = null;
/* 1983 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(this.m_elist.getEntityGroup("WWSEO"), "MODELWWSEO", "MODEL");
/* 1984 */       if (vector.size() > 0) {
/*      */         
/* 1986 */         EntityItem entityItem = vector.firstElement();
/* 1987 */         entityList = getModelVE(entityItem);
/* 1988 */         entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/* 1989 */         Vector vector8 = PokUtils.getAllLinkedEntities(entityItem, "MODELAVAIL", "AVAIL");
/*      */         
/* 1991 */         Vector vector9 = PokUtils.getEntitiesWithMatchedAttr(vector8, "AVAILTYPE", "146");
/*      */         
/* 1993 */         arrayList1 = getCountriesAsList(vector9, i);
/* 1994 */         addDebug("doGAChecks " + entityItem.getKey() + " mdlavailVct: " + vector8.size() + " mdlPlaVct " + vector9
/* 1995 */             .size() + " mdlplaCtry " + arrayList1);
/* 1996 */         vector8.clear();
/* 1997 */         vector9.clear();
/*      */       } 
/* 1999 */       for (byte b = 0; b < vector3.size(); b++) {
/* 2000 */         EntityItem entityItem = vector3.elementAt(b);
/* 2001 */         addDebug("doGAChecks lseo plannedavail " + entityItem.getKey() + " (12) ctrylist: " + 
/* 2002 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST"));
/*      */ 
/*      */         
/* 2005 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem1, "LSEOPUBDATEMTRGT", 4);
/*      */ 
/*      */ 
/*      */         
/* 2009 */         checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem1, arrayList, 4);
/*      */         
/* 2011 */         if (arrayList1 != null && arrayList1.size() > 0) {
/*      */           
/* 2013 */           ArrayList<?> arrayList2 = new ArrayList();
/* 2014 */           getCountriesAsList(entityItem, arrayList2, i);
/*      */ 
/*      */           
/* 2017 */           if (!arrayList1.containsAll(arrayList2)) {
/*      */ 
/*      */             
/* 2020 */             ArrayList<?> arrayList3 = new ArrayList();
/* 2021 */             arrayList3.addAll(arrayList2);
/* 2022 */             arrayList3.removeAll(arrayList1);
/*      */             
/* 2024 */             this.args[0] = "";
/* 2025 */             this.args[1] = getLD_NDN(entityItem);
/* 2026 */             this.args[2] = this.m_elist.getEntityGroup("MODEL").getLongDescription() + " " + entityItem
/* 2027 */               .getEntityGroup().getLongDescription();
/* 2028 */             this.args[3] = "Planned Availability";
/* 2029 */             this.args[4] = getUnmatchedDescriptions(entityItem, "COUNTRYLIST", arrayList3);
/* 2030 */             createMessage(4, "INCLUDE_ERR2", this.args);
/* 2031 */             arrayList3.clear();
/*      */           } 
/*      */           
/* 2034 */           arrayList2.clear();
/*      */         } 
/*      */       } 
/*      */       
/* 2038 */       if (entityList != null) {
/* 2039 */         entityList.dereference();
/*      */       }
/* 2041 */       if (arrayList1 != null) {
/* 2042 */         arrayList1.clear();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2047 */     Hashtable hashtable = getAvailByCountry(vector3, getCheck_W_W_E(paramString));
/* 2048 */     addDebug("doGAChecks lseo plannedAvailCtryTbl: " + hashtable.keySet());
/*      */     
/* 2050 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " GA First Order Avail Checks:");
/*      */ 
/*      */     
/* 2053 */     for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 2054 */       EntityItem entityItem = vector4.elementAt(b1);
/* 2055 */       addDebug("doGAChecks lseo firstorder  " + entityItem.getKey() + " (17) ctrylist: " + 
/* 2056 */           PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST"));
/*      */ 
/*      */       
/* 2059 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem1, "LSEOPUBDATEMTRGT", i);
/*      */ 
/*      */ 
/*      */       
/* 2063 */       checkPlannedAvailForCtryExists(entityItem, hashtable.keySet(), i);
/*      */     } 
/*      */ 
/*      */     
/* 2067 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " GA Last Order Avail Checks:");
/*      */ 
/*      */     
/* 2070 */     checkLseoLoEosEomAvails(paramEntityItem1, hashtable, vector2, i);
/*      */ 
/*      */     
/* 2073 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " GA End of Marketing Avail Checks:");
/*      */ 
/*      */     
/* 2076 */     checkLseoLoEosEomAvails(paramEntityItem1, hashtable, vector6, i);
/*      */ 
/*      */     
/* 2079 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " GA End of Service Avail Checks:");
/*      */ 
/*      */     
/* 2082 */     checkLseoLoEosEomAvails(paramEntityItem1, hashtable, vector5, i);
/*      */ 
/*      */     
/* 2085 */     Vector<EntityItem> vector7 = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELWWSEO", "MODEL");
/* 2086 */     for (byte b2 = 0; b2 < vector7.size(); b2++) {
/* 2087 */       EntityItem entityItem = vector7.elementAt(b2);
/* 2088 */       String str = getAttributeFlagEnabledValue(entityItem, "COFCAT");
/* 2089 */       addDebug(entityItem.getKey() + " NOT SPECBID COFCAT: " + str);
/*      */       
/* 2091 */       if ("100".equals(str)) {
/*      */ 
/*      */         
/* 2094 */         doHWGAChecks(paramEntityItem1, paramEntityItem2, vector3, vector2, paramString);
/* 2095 */       } else if ("101".equals(str)) {
/*      */ 
/*      */         
/* 2098 */         doSWGAChecks(paramEntityItem1, paramEntityItem2, vector3, vector2, paramString);
/* 2099 */       } else if ("102".equals(str)) {
/*      */       
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2107 */     vector7.clear();
/* 2108 */     vector1.clear();
/* 2109 */     vector2.clear();
/* 2110 */     vector3.clear();
/* 2111 */     vector4.clear();
/* 2112 */     vector5.clear();
/* 2113 */     vector6.clear();
/* 2114 */     arrayList.clear();
/* 2115 */     hashtable.clear();
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
/*      */   private void checkLseoLoEosEomAvails(EntityItem paramEntityItem, Hashtable paramHashtable, Vector<EntityItem> paramVector, int paramInt) throws SQLException, MiddlewareException {
/* 2151 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 2152 */       EntityItem entityItem = paramVector.elementAt(b);
/* 2153 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE");
/*      */       
/* 2155 */       addDebug("checkLseoLoEosEomAvails lseo avail " + entityItem.getKey() + " (22) ctrylist: " + 
/* 2156 */           PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + " availType: " + str);
/* 2157 */       if (!"200".equals(str)) {
/* 2158 */         if ("151".equals(str)) {
/*      */ 
/*      */           
/* 2161 */           checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "LSEOUNPUBDATEMTRGT", paramInt);
/*      */         }
/*      */         else {
/*      */           
/* 2165 */           checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem, "LSEOUNPUBDATEMTRGT", paramInt);
/*      */         } 
/*      */       }
/*      */       
/* 2169 */       Vector<EntityItem> vector = new Vector();
/* 2170 */       getMatchingAvails(entityItem, paramHashtable, vector, paramInt);
/*      */       
/* 2172 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 2173 */         EntityItem entityItem1 = vector.elementAt(b1);
/*      */ 
/*      */         
/* 2176 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", entityItem1, "EFFECTIVEDATE", paramInt);
/*      */       } 
/* 2178 */       vector.clear();
/*      */ 
/*      */       
/* 2181 */       checkPlannedAvailForCtryExists(entityItem, paramHashtable.keySet(), paramInt);
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
/*      */   private void doHWGAChecks(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector paramVector1, Vector paramVector2, String paramString) throws Exception {
/* 2257 */     addHeading(3, "Hardware Model GA Checks:");
/* 2258 */     int i = getCheck_W_W_E(paramString);
/*      */     
/* 2260 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem2, "WWSEOPRODSTRUCT", "PRODSTRUCT");
/* 2261 */     Vector vector2 = PokUtils.getAllLinkedEntities(paramEntityItem1, "LSEOPRODSTRUCT", "PRODSTRUCT");
/*      */     
/* 2263 */     addDebug("doHWGAChecks wwseopsVct " + vector1.size() + " lseopsVct " + vector2.size());
/*      */     
/* 2265 */     Hashtable hashtable = getAvailByCountry(paramVector1, getCheck_W_RE_RE(paramString));
/* 2266 */     addDebug("doHWGAChecks  lseoPlaAvailCtryTblA: " + hashtable.keySet());
/*      */     
/* 2268 */     if (vector1.size() + vector2.size() == 0) {
/* 2269 */       this.args[0] = this.m_elist.getEntityGroup("FEATURE").getLongDescription();
/*      */       
/* 2271 */       createMessage(getCheck_W_E_E(paramString), "MINIMUM_ERR", this.args);
/*      */     } 
/*      */     
/* 2274 */     String str1 = getAttrValueAndCheckLvl(paramEntityItem1, "LSEOPUBDATEMTRGT", i);
/* 2275 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem1, "LSEOUNPUBDATEMTRGT", i);
/*      */     
/* 2277 */     checkPsDates(paramEntityItem1, vector1, paramEntityItem2.getEntityGroup(), str1, str2, i);
/*      */ 
/*      */     
/* 2280 */     checkPsDates(paramEntityItem1, vector2, paramEntityItem1.getEntityGroup(), str1, str2, i);
/*      */ 
/*      */     
/* 2283 */     checkSystemMaxAndConfqty(paramEntityItem1, "LSEOPRODSTRUCT", 4);
/*      */     
/* 2285 */     addDebug("\ndoHWGAChecks lseops chks");
/* 2286 */     addHeading(3, "LSEO " + this.m_elist.getEntityGroup("PRODSTRUCT").getLongDescription() + " Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2291 */     checkGAPsPlaAvails(paramEntityItem1, vector2, "OOFAVAIL", paramVector1, paramString);
/*      */ 
/*      */     
/* 2294 */     addHeading(3, "LSEO " + this.m_elist.getEntityGroup("PRODSTRUCT").getLongDescription() + " Last Order Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2314 */     matchPsLOAvail(paramVector2, hashtable, vector2, "OOFAVAIL", "PRODSTRUCTCATLGOR", 3, 4);
/*      */ 
/*      */ 
/*      */     
/* 2318 */     addDebug("\ndoHWGAChecks wwseops chks");
/* 2319 */     addHeading(3, "WWSEO " + this.m_elist.getEntityGroup("PRODSTRUCT").getLongDescription() + " Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2324 */     checkGAPsPlaAvails(paramEntityItem1, vector1, "OOFAVAIL", paramVector1, paramString);
/*      */ 
/*      */     
/* 2327 */     addHeading(3, "WWSEO " + this.m_elist.getEntityGroup("PRODSTRUCT").getLongDescription() + " Last Order Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2347 */     matchPsLOAvail(paramVector2, hashtable, vector2, "OOFAVAIL", "PRODSTRUCTCATLGOR", 3, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2352 */     vector1.clear();
/* 2353 */     vector2.clear();
/* 2354 */     hashtable.clear();
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
/*      */   private void checkGAPsPlaAvails(EntityItem paramEntityItem, Vector<EntityItem> paramVector1, String paramString1, Vector<EntityItem> paramVector2, String paramString2) throws MiddlewareException, SQLException {
/* 2404 */     Vector vector = PokUtils.getAllLinkedEntities(paramVector1, paramString1, "AVAIL");
/* 2405 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/*      */ 
/*      */     
/* 2408 */     Hashtable hashtable = getAvailByCountry(paramVector2, getCheck_W_RE_RE(paramString2));
/* 2409 */     addDebug("checkGAPsPlaAvails " + paramString1 + " allpsAvailVct: " + vector
/* 2410 */         .size() + " allpsPlaAvailVct: " + vector1.size() + " lseoPlaAvailCtryTblA: " + hashtable
/* 2411 */         .keySet());
/*      */     
/* 2413 */     int i = getCheck_W_E_E(paramString2);
/* 2414 */     int j = getCheck_W_RE_RE(paramString2);
/*      */     
/* 2416 */     for (byte b = 0; b < paramVector1.size(); b++) {
/* 2417 */       EntityItem entityItem = paramVector1.elementAt(b);
/* 2418 */       Vector vector2 = PokUtils.getAllLinkedEntities(entityItem, paramString1, "AVAIL");
/* 2419 */       Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "146");
/* 2420 */       addDebug("checkGAPsPlaAvails " + paramString1 + " " + entityItem.getKey() + " psAvailVct: " + vector2
/* 2421 */           .size() + " psPlaAvailVct: " + vector3.size());
/* 2422 */       for (byte b1 = 0; b1 < vector3.size(); b1++) {
/* 2423 */         EntityItem entityItem1 = vector3.elementAt(b1);
/*      */ 
/*      */         
/* 2426 */         Vector<EntityItem> vector4 = new Vector();
/* 2427 */         getMatchingAvails(entityItem1, hashtable, vector4, j);
/* 2428 */         String str = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", i);
/* 2429 */         for (byte b2 = 0; b2 < vector4.size(); b2++) {
/* 2430 */           EntityItem entityItem2 = vector4.elementAt(b2);
/*      */           
/* 2432 */           String str1 = getAttrValueAndCheckLvl(entityItem2, "EFFECTIVEDATE", i);
/* 2433 */           addDebug("checkGAPsPlaAvails root plannedavail: " + entityItem2
/* 2434 */               .getKey() + " EFFECTIVEDATE:" + str1 + " must not be earlier psplannedavail:" + entityItem1
/* 2435 */               .getKey() + " EFFECTIVEDATE:" + str);
/* 2436 */           boolean bool = checkDates(str, str1, 2);
/* 2437 */           if (!bool) {
/*      */ 
/*      */             
/* 2440 */             this.args[0] = getLD_NDN(entityItem2);
/* 2441 */             this.args[1] = getLD_Value(entityItem2, "EFFECTIVEDATE");
/* 2442 */             this.args[2] = getLD_NDN(entityItem);
/* 2443 */             this.args[3] = getLD_NDN(entityItem1);
/* 2444 */             createMessage(i, "CANNOT_BE_EARLIER_ERR2", this.args);
/*      */           } 
/*      */         } 
/* 2447 */         vector4.clear();
/*      */       } 
/*      */       
/* 2450 */       vector2.clear();
/* 2451 */       vector3.clear();
/*      */     } 
/*      */ 
/*      */     
/* 2455 */     ArrayList arrayList = getCountriesAsList(vector1, j);
/* 2456 */     addDebug("checkGAPsPlaAvails allPsPlaAvailCtry " + arrayList);
/* 2457 */     addDebug("checkGAPsPlaAvails root plaAvailCtryTblA.keySet() " + hashtable.keySet());
/* 2458 */     if (!arrayList.containsAll(hashtable.keySet()))
/*      */     {
/*      */       
/* 2461 */       for (byte b1 = 0; b1 < paramVector2.size(); b1++) {
/* 2462 */         EntityItem entityItem = paramVector2.elementAt(b1);
/* 2463 */         ArrayList<?> arrayList1 = new ArrayList();
/* 2464 */         getCountriesAsList(entityItem, arrayList1, j);
/*      */         
/* 2466 */         if (!arrayList.containsAll(arrayList1)) {
/* 2467 */           addDebug("checkGAPsPlaAvails root plannedavailA " + entityItem.getKey() + " plaCtryA " + arrayList1 + " not in psPlaAvailCtry " + arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2472 */           this.args[0] = getLD_NDN(entityItem);
/* 2473 */           this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2481 */           for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 2482 */             EntityItem entityItem1 = vector1.elementAt(b2);
/* 2483 */             ArrayList<?> arrayList2 = new ArrayList();
/*      */             
/* 2485 */             getCountriesAsList(entityItem1, arrayList2, -1);
/* 2486 */             if (!arrayList2.containsAll(arrayList1)) {
/* 2487 */               addDebug("checkGAPsPlaAvails psPlaAvail " + entityItem1.getKey() + " availCtrylist: " + arrayList2 + " did not contain all root " + entityItem
/* 2488 */                   .getKey() + " plaCtryA: " + arrayList1);
/* 2489 */               EntityItem entityItem2 = getAvailPS(entityItem1, paramString1);
/*      */               
/* 2491 */               ArrayList<?> arrayList3 = new ArrayList();
/* 2492 */               arrayList3.addAll(arrayList2);
/* 2493 */               arrayList3.removeAll(arrayList1);
/*      */               
/* 2495 */               if (arrayList3.size() == 0) {
/*      */                 
/* 2497 */                 arrayList3.addAll(arrayList1);
/* 2498 */                 arrayList3.removeAll(arrayList2);
/*      */               } 
/*      */               
/* 2501 */               this.args[2] = getLD_NDN(entityItem2);
/* 2502 */               this.args[3] = getLD_NDN(entityItem1);
/* 2503 */               this.args[4] = getUnmatchedDescriptions(entityItem1, "COUNTRYLIST", arrayList3);
/* 2504 */               createMessage(j, "INCLUDE_ERR2", this.args);
/* 2505 */               arrayList3.clear();
/*      */             } 
/* 2507 */             arrayList2.clear();
/*      */           } 
/*      */         } 
/* 2510 */         arrayList1.clear();
/*      */       } 
/*      */     }
/*      */     
/* 2514 */     arrayList.clear();
/* 2515 */     vector.clear();
/* 2516 */     vector1.clear();
/* 2517 */     hashtable.clear();
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
/*      */   private void matchPsLOAvail(Vector paramVector1, Hashtable paramHashtable, Vector<EntityItem> paramVector2, String paramString1, String paramString2, int paramInt1, int paramInt2) throws MiddlewareException, SQLException {
/* 2581 */     Hashtable hashtable = getAvailByCountry(paramVector1, paramInt1);
/*      */     
/* 2583 */     Set<?> set = paramHashtable.keySet();
/* 2584 */     addDebug("matchPsLOAvail lseoPlaAvailCtrys " + set + " lseoLOAvailCtryTblB: " + hashtable
/* 2585 */         .keySet());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2599 */     for (byte b = 0; b < paramVector2.size(); b++) {
/* 2600 */       EntityItem entityItem = paramVector2.elementAt(b);
/* 2601 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem, paramString1, "AVAIL");
/* 2602 */       Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/*      */       
/* 2604 */       addDebug("matchPsLOAvail " + entityItem.getKey() + " " + paramString1 + " psAvailVct: " + vector
/* 2605 */           .size() + " psLOAvailVct: " + vector1.size()); byte b1;
/* 2606 */       label45: for (b1 = 0; b1 < vector1.size(); b1++) {
/* 2607 */         EntityItem entityItem1 = vector1.elementAt(b1);
/*      */         
/* 2609 */         ArrayList arrayList1 = new ArrayList();
/* 2610 */         getCountriesAsList(entityItem1, arrayList1, paramInt1);
/* 2611 */         addDebug("matchPsLOAvail " + entityItem1.getKey() + " psloCtry " + arrayList1);
/* 2612 */         ArrayList arrayList2 = (ArrayList)arrayList1.clone();
/* 2613 */         arrayList2.retainAll(set);
/* 2614 */         addDebug("matchPsLOAvail lseoplaavail matching psloavail ctrys: " + arrayList2);
/*      */ 
/*      */ 
/*      */         
/* 2618 */         if (arrayList2.size() > 0) {
/* 2619 */           Iterator<E> iterator = arrayList2.iterator();
/* 2620 */           while (iterator.hasNext()) {
/* 2621 */             String str1 = iterator.next().toString();
/*      */ 
/*      */ 
/*      */             
/* 2625 */             if (!hashtable.containsKey(str1)) {
/* 2626 */               EntityItem entityItem2 = (EntityItem)paramHashtable.get(str1);
/* 2627 */               addDebug("matchPsLOAvail lseo-plannedavail:" + entityItem2.getKey() + " PS-lastorderavail " + entityItem1
/* 2628 */                   .getKey() + " No lseo lastorderavail for ctry " + str1);
/*      */ 
/*      */ 
/*      */               
/* 2632 */               this.args[0] = getLD_NDN(entityItem);
/* 2633 */               this.args[1] = getLD_NDN(entityItem1);
/* 2634 */               this.args[2] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 2635 */               this.args[3] = entityItem1.getEntityGroup().getLongDescription();
/* 2636 */               createMessage(paramInt1, "LOAVAIL_MATCH_ERR", this.args);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/* 2642 */         Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem, paramString2, "CATLGOR");
/* 2643 */         String str = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", "", false);
/* 2644 */         addDebug("matchPsLOAvail:  catlgorVct " + vector2.size() + " " + entityItem1.getKey() + " EFFECTIVEDATE " + str);
/* 2645 */         for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 2646 */           EntityItem entityItem2 = vector2.elementAt(b2);
/* 2647 */           String str1 = PokUtils.getAttributeValue(entityItem2, "PUBTO", "", null, false);
/* 2648 */           String str2 = PokUtils.getAttributeFlagValue(entityItem2, "OFFCOUNTRY");
/* 2649 */           addDebug("matchPsLOAvail: " + entityItem2.getKey() + " pubto " + str1 + " offctry " + str2);
/*      */ 
/*      */ 
/*      */           
/* 2653 */           if (str1 != null && str2 != null)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2663 */             if (hashtable.containsKey(str2) && str.compareTo(str1) < 0) {
/* 2664 */               EntityItem entityItem3 = (EntityItem)hashtable.get(str2);
/* 2665 */               addDebug("matchPsLOAvail: chk pubto against " + entityItem3.getKey() + " EFFECTIVEDATE " + 
/* 2666 */                   PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", ", ", "", false));
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2671 */               checkCanNotBeLater4(entityItem, entityItem3, "EFFECTIVEDATE", entityItem2, "PUBTO", paramInt2);
/*      */               continue label45;
/*      */             } 
/*      */           }
/*      */         } 
/* 2676 */         vector2.clear();
/* 2677 */         addDebug("matchPsLOAvail: no catlgor chk done, chk ps loavail against lseo loavail");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2682 */         Vector<EntityItem> vector3 = new Vector();
/* 2683 */         getMatchingAvails(entityItem1, hashtable, vector3, paramInt1);
/*      */         
/* 2685 */         for (byte b3 = 0; b3 < vector3.size(); b3++) {
/* 2686 */           EntityItem entityItem2 = vector3.elementAt(b3);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2691 */           checkCanNotBeLater4(entityItem, entityItem2, "EFFECTIVEDATE", entityItem1, "EFFECTIVEDATE", paramInt1);
/*      */         } 
/* 2693 */         vector3.clear();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2700 */       vector.clear();
/* 2701 */       vector1.clear();
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
/*      */   private void checkCanNotBeLater4(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, EntityItem paramEntityItem3, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 2727 */     String str1 = "";
/* 2728 */     if (paramEntityItem1 != null) {
/* 2729 */       str1 = getLD_NDN(paramEntityItem1) + " ";
/*      */     }
/* 2731 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem2, paramString1, paramInt);
/* 2732 */     String str3 = getAttrValueAndCheckLvl(paramEntityItem3, paramString2, paramInt);
/* 2733 */     addDebug("checkCanNotBeLater4 " + paramEntityItem2.getKey() + " " + paramString1 + ":" + str2 + " " + paramEntityItem3.getKey() + " " + paramString2 + ":" + str3);
/*      */     
/* 2735 */     boolean bool = checkDates(str2, str3, 2);
/* 2736 */     if (bool) {
/* 2737 */       if (str2.length() > 0 && !Character.isDigit(str2.charAt(0))) {
/* 2738 */         bool = false;
/*      */       }
/* 2740 */       if (str3.length() > 0 && !Character.isDigit(str3.charAt(0))) {
/* 2741 */         bool = false;
/*      */       }
/*      */     } 
/* 2744 */     if (!bool) {
/*      */ 
/*      */ 
/*      */       
/* 2748 */       if (paramEntityItem2.getEntityType().equals(getEntityType()) && paramEntityItem2.getEntityID() == getEntityID()) {
/* 2749 */         this.args[0] = "";
/*      */       } else {
/* 2751 */         this.args[0] = getLD_NDN(paramEntityItem2);
/*      */       } 
/* 2753 */       this.args[1] = getLD_Value(paramEntityItem2, paramString1);
/* 2754 */       this.args[2] = str1 + getLD_NDN(paramEntityItem3);
/* 2755 */       this.args[3] = getLD_Value(paramEntityItem3, paramString2);
/* 2756 */       createMessage(paramInt, "CANNOT_BE_LATER_ERR", this.args);
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
/*      */   private void checkDates(EntityItem paramEntityItem1, String paramString1, String paramString2, EntityItem paramEntityItem2, EntityItem paramEntityItem3, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 2915 */     String str = getAttrValueAndCheckLvl(paramEntityItem3, "EFFECTIVEDATE", paramInt1);
/* 2916 */     addDebug("checkDates " + paramString1 + ":" + paramString2 + " can not be " + ((paramInt2 == 1) ? "earlier" : "later") + " than " + paramEntityItem2
/* 2917 */         .getKey() + " psavail: " + paramEntityItem3.getKey() + " EFFECTIVEDATE:" + str);
/* 2918 */     boolean bool = checkDates(paramString2, str, paramInt2);
/* 2919 */     if (!bool) {
/*      */       
/* 2921 */       String str1 = "";
/* 2922 */       if (paramInt2 == 1) {
/*      */         
/* 2924 */         str1 = "CANNOT_BE_EARLIER_ERR1";
/*      */       } else {
/* 2926 */         str1 = "CANNOT_BE_LATER_ERR1";
/*      */       } 
/*      */       
/* 2929 */       this.args[0] = getLD_Value(paramEntityItem1, paramString1, paramString2);
/* 2930 */       this.args[1] = getLD_NDN(paramEntityItem2);
/* 2931 */       this.args[2] = getLD_NDN(paramEntityItem3);
/*      */       
/* 2933 */       createMessage(paramInt1, str1, this.args);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkPsDates(EntityItem paramEntityItem, Vector<EntityItem> paramVector, EntityGroup paramEntityGroup, String paramString1, String paramString2, int paramInt) throws Exception {
/* 2940 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 2941 */       EntityItem entityItem = paramVector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2946 */       String str1 = getAttrValueAndCheckLvl(entityItem, "ANNDATE", paramInt);
/* 2947 */       addDebug("checkPsDates (28,29) " + paramEntityItem.getKey() + " LSEOPUBDATEMTRGT:" + paramString1 + " cannot be earlier " + entityItem
/*      */           
/* 2949 */           .getKey() + " ANNDATE:" + str1);
/* 2950 */       boolean bool = checkDates(str1, paramString1, 2);
/* 2951 */       if (!bool) {
/* 2952 */         this.args[0] = "";
/* 2953 */         this.args[1] = getLD_Value(paramEntityItem, "LSEOPUBDATEMTRGT", paramString1);
/* 2954 */         this.args[2] = paramEntityGroup.getLongDescription();
/* 2955 */         this.args[3] = getLD_NDN(entityItem);
/* 2956 */         this.args[4] = getLD_Value(entityItem, "ANNDATE", str1);
/* 2957 */         createMessage(paramInt, "CANNOT_BE_EARLIER_ERR3", this.args);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2963 */       String str2 = getAttrValueAndCheckLvl(entityItem, "WTHDRWEFFCTVDATE", paramInt);
/* 2964 */       addDebug("checkPsDates (28,29) " + paramEntityItem.getKey() + " LSEOUNPUBDATEMTRGT:" + paramString2 + " cannot be later " + entityItem
/*      */           
/* 2966 */           .getKey() + " WTHDRWEFFCTVDATE:" + str2);
/* 2967 */       bool = checkDates(str2, paramString2, 1);
/* 2968 */       if (!bool) {
/*      */         
/* 2970 */         this.args[0] = "";
/* 2971 */         this.args[1] = getLD_Value(paramEntityItem, "LSEOUNPUBDATEMTRGT", paramString2);
/* 2972 */         this.args[2] = paramEntityGroup.getLongDescription();
/* 2973 */         this.args[3] = getLD_NDN(entityItem);
/* 2974 */         this.args[4] = getLD_Value(entityItem, "WTHDRWEFFCTVDATE", str2);
/* 2975 */         createMessage(paramInt, "CANNOT_BE_LATER_ERR2", this.args);
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
/*      */   private void doSWGAChecks(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector paramVector1, Vector paramVector2, String paramString) throws Exception {
/* 2994 */     addHeading(3, "Software Model GA Checks:");
/*      */ 
/*      */     
/* 2997 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem2, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
/* 2998 */     Vector vector2 = PokUtils.getAllLinkedEntities(paramEntityItem1, "LSEOSWPRODSTRUCT", "SWPRODSTRUCT");
/*      */     
/* 3000 */     addDebug("doSWGAChecks wwseoswpsVct " + vector1.size() + " lseoswpsVct " + vector2.size());
/*      */     
/* 3002 */     Hashtable hashtable = getAvailByCountry(paramVector1, getCheck_W_RE_RE(paramString));
/* 3003 */     addDebug("doSWGAChecks  lseoPlaAvailCtryTblA: " + hashtable.keySet());
/*      */ 
/*      */     
/* 3006 */     if (vector1.size() + vector2.size() == 0) {
/* 3007 */       this.args[0] = this.m_elist.getEntityGroup("SWFEATURE").getLongDescription();
/*      */       
/* 3009 */       createMessage(getCheck_W_E_E(paramString), "MINIMUM_ERR", this.args);
/*      */     } 
/* 3011 */     addDebug("doSWGAChecks checking lseoswps");
/* 3012 */     addHeading(3, "LSEO " + this.m_elist.getEntityGroup("SWPRODSTRUCT").getLongDescription() + " Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3017 */     checkGAPsPlaAvails(paramEntityItem1, vector2, "SWPRODSTRUCTAVAIL", paramVector1, paramString);
/*      */ 
/*      */     
/* 3020 */     addHeading(3, "LSEO " + this.m_elist.getEntityGroup("SWPRODSTRUCT").getLongDescription() + " Last Order Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3040 */     matchPsLOAvail(paramVector2, hashtable, vector2, "SWPRODSTRUCTAVAIL", "SWPRODSTRCATLGOR", 3, 4);
/*      */ 
/*      */ 
/*      */     
/* 3044 */     addDebug("\ndoSWGAChecks checking wweoswps");
/* 3045 */     addHeading(3, "WWSEO " + this.m_elist.getEntityGroup("SWPRODSTRUCT").getLongDescription() + " Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3050 */     checkGAPsPlaAvails(paramEntityItem1, vector1, "SWPRODSTRUCTAVAIL", paramVector1, paramString);
/*      */ 
/*      */     
/* 3053 */     addHeading(3, "WWSEO " + this.m_elist.getEntityGroup("SWPRODSTRUCT").getLongDescription() + " Last Order Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3074 */     matchPsLOAvail(paramVector2, hashtable, vector1, "SWPRODSTRUCTAVAIL", "SWPRODSTRCATLGOR", 3, 4);
/*      */ 
/*      */ 
/*      */     
/* 3078 */     vector1.clear();
/* 3079 */     vector2.clear();
/* 3080 */     hashtable.clear();
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
/*      */   public String getDescription() {
/* 3145 */     return "LSEO ABR.";
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
/*      */   public String getABRVersion() {
/* 3157 */     return "1.12";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\LSEOABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */