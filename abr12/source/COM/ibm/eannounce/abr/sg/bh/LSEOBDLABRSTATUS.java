/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EANTextAttribute;
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
/*      */ public class LSEOBDLABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*      */   private EntityList mdlList;
/*  123 */   private static final Set TESTSET = new HashSet(); static {
/*  124 */     TESTSET.add("100");
/*  125 */     TESTSET.add("101");
/*  126 */   } private static final Set HWTESTSET = new HashSet(); static {
/*  127 */     HWTESTSET.add("100");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  134 */     return true;
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  216 */     doLSEOBDLSectionOne(this.m_elist.getParentEntityGroup().getEntityItem(0));
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
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  230 */     checkAssortModule();
/*      */ 
/*      */     
/*  233 */     doLSEOBDLSectionOne(this.m_elist.getParentEntityGroup().getEntityItem(0));
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
/*      */   protected boolean updateDerivedData(EntityItem paramEntityItem) throws Exception {
/*  251 */     boolean bool = false;
/*      */     
/*  253 */     String str = PokUtils.getAttributeValue(paramEntityItem, "BUNDLUNPUBDATEMTRGT", "", "9999-12-31", false);
/*  254 */     addDebug("updateDerivedData wdDate: " + str + " now: " + getCurrentDate());
/*  255 */     if (getCurrentDate().compareTo(str) <= 0) {
/*  256 */       bool = execDerivedData(paramEntityItem);
/*      */     }
/*  258 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLCRFRWFName() {
/*  265 */     return "WFLCLSEOBDLRFR"; } protected String getLCFinalWFName() {
/*  266 */     return "WFLCLSEOBDLFINAL";
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  421 */     int i = getCheck_W_W_E(paramString);
/*      */     
/*  423 */     addDebug("checking " + paramEntityItem.getKey() + " (3)");
/*  424 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*      */ 
/*      */     
/*  427 */     checkCanNotBeEarlier(paramEntityItem, "BUNDLUNPUBDATEMTRGT", "BUNDLPUBDATEMTRGT", i);
/*      */ 
/*      */     
/*  430 */     getModelVE(paramEntityItem);
/*      */ 
/*      */ 
/*      */     
/*  434 */     validateOS(paramEntityItem, i);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  439 */     validateBUNDLETYPE(paramEntityItem, i);
/*      */     
/*  441 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/*      */     
/*  443 */     ArrayList<?> arrayList = new ArrayList();
/*      */     
/*  445 */     getCountriesAsList(paramEntityItem, arrayList, i);
/*      */     
/*  447 */     addHeading(2, entityGroup.getLongDescription() + " Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  452 */     int j = getCount("LSEOBUNDLELSEO");
/*  453 */     if (j < 2) {
/*      */       
/*  455 */       this.args[0] = entityGroup.getLongDescription();
/*  456 */       createMessage(4, "MINIMUM_TWO_ERR", this.args);
/*      */     } else {
/*      */       
/*  459 */       j = getCount("LSEO");
/*  460 */       if (j < 2) {
/*      */         
/*  462 */         this.args[0] = entityGroup.getLongDescription();
/*  463 */         createMessage(4, "MINIMUM_TWO_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */     
/*  467 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  468 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  470 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*  471 */       ArrayList arrayList1 = new ArrayList();
/*      */       
/*  473 */       getCountriesAsList(entityItem, arrayList1, i);
/*      */ 
/*      */       
/*  476 */       if (!arrayList1.containsAll(arrayList)) {
/*  477 */         addDebug("lseo (45)  " + entityItem.getKey() + " ctrylist " + arrayList1 + " did not contain lseobdl list " + arrayList);
/*      */         
/*  479 */         this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*  480 */         this.args[1] = getLD_NDN(entityItem);
/*  481 */         createMessage(i, "MUST_INCLUDE_ERR", this.args);
/*      */       } 
/*      */       
/*  484 */       arrayList1.clear();
/*      */     } 
/*      */     
/*  487 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "SPECBID");
/*  488 */     addDebug(paramEntityItem.getKey() + " SPECBID: " + str);
/*      */ 
/*      */ 
/*      */     
/*  492 */     if ("11457".equals(str)) {
/*  493 */       addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " GA Checks:");
/*  494 */       doNotSpecBidChecks(paramEntityItem, paramString);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  500 */       addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Special Bid Checks:");
/*  501 */       doSpecBidChecks(paramEntityItem, paramString);
/*      */     } 
/*      */ 
/*      */     
/*  505 */     addHeading(2, this.m_elist.getEntityGroup("LSEO").getLongDescription() + " " + 
/*  506 */         PokUtils.getAttributeDescription(this.m_elist.getEntityGroup("MODEL"), "COFCAT", "COFCAT") + " Check:");
/*      */     
/*  508 */     checkModels();
/*      */     
/*  510 */     this.mdlList.dereference();
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
/*      */   private void checkModels() throws SQLException, MiddlewareException {
/*  537 */     EntityGroup entityGroup = this.mdlList.getEntityGroup("LSEOBUNDLELSEO");
/*      */ 
/*      */     
/*  540 */     Vector<EntityItem> vector1 = new Vector(1);
/*  541 */     Vector<EntityItem> vector2 = new Vector(1);
/*  542 */     Vector<EntityItem> vector3 = new Vector(1);
/*      */     byte b;
/*  544 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  545 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  546 */       for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*  547 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/*  548 */         if (entityItem1.getEntityType().equals("LSEO")) {
/*  549 */           Vector vector = PokUtils.getAllLinkedEntities(entityItem1, "WWSEOLSEO", "WWSEO");
/*  550 */           Vector<EntityItem> vector4 = PokUtils.getAllLinkedEntities(vector, "MODELWWSEO", "MODEL");
/*  551 */           addDebug("checkModels " + entityItem.getKey() + " " + entityItem1
/*  552 */               .getKey() + " wwseoVct: " + vector.size() + " mdlVct: " + vector4.size());
/*  553 */           for (byte b2 = 0; b2 < vector4.size(); b2++) {
/*  554 */             EntityItem entityItem2 = vector4.elementAt(b2);
/*  555 */             String str = getAttributeFlagEnabledValue(entityItem2, "COFCAT");
/*  556 */             addDebug("checkModels " + entityItem2.getKey() + " COFCAT: " + str);
/*  557 */             if ("100".equals(str)) {
/*  558 */               vector1.add(entityItem1);
/*  559 */             } else if ("101".equals(str)) {
/*  560 */               vector2.add(entityItem1);
/*  561 */             } else if ("102".equals(str)) {
/*  562 */               vector3.add(entityItem1);
/*      */             } 
/*      */           } 
/*      */           
/*  566 */           vector.clear();
/*  567 */           vector4.clear();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  572 */     addDebug("checkModels hwlseoVct " + vector1.size() + " swlseoVct: " + vector2.size() + " svclseoVct: " + vector3.size());
/*      */ 
/*      */ 
/*      */     
/*  576 */     if (vector1.size() > 0) {
/*  577 */       if (vector1.size() > 1)
/*      */       {
/*  579 */         for (b = 0; b < vector1.size(); b++) {
/*  580 */           EntityItem entityItem = vector1.elementAt(b);
/*  581 */           this.args[0] = getLD_NDN(entityItem);
/*  582 */           createMessage(4, "LSEO_HWMDL_ERR", this.args);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  587 */       if (vector2.size() == 0)
/*      */       {
/*  589 */         this.args[0] = this.m_elist.getEntityGroup("LSEO").getLongDescription();
/*  590 */         createMessage(3, "LSEO_SWMDL_ERR", this.args);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  598 */       if (vector2.size() > 0 && vector2.size() < 2) {
/*      */         
/*  600 */         this.args[0] = this.m_elist.getEntityGroup("LSEO").getLongDescription();
/*  601 */         createMessage(4, "LSEO_SWMDL2_ERR", this.args);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  607 */       if (vector3.size() > 0 && vector3.size() < 2) {
/*      */         
/*  609 */         this.args[0] = this.m_elist.getEntityGroup("LSEO").getLongDescription();
/*  610 */         createMessage(4, "LSEO_SVCMDL_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  618 */     vector1.clear();
/*  619 */     vector2.clear();
/*  620 */     vector3.clear();
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
/*      */   private void doSpecBidChecks(EntityItem paramEntityItem, String paramString) throws Exception {
/*  674 */     int i = getCheck_W_W_E(paramString);
/*      */     
/*  676 */     ArrayList<?> arrayList = new ArrayList();
/*  677 */     getCountriesAsList(paramEntityItem, arrayList, i);
/*  678 */     addDebug("doSpecBidChecks " + paramEntityItem.getKey() + " bdlCtry:" + arrayList);
/*      */ 
/*      */     
/*  681 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/*  682 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  683 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  684 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "WWSEO");
/*  685 */       if (vector.size() == 0) {
/*  686 */         addDebug("doNotSpecBidLSEOChecks " + entityItem.getKey() + " does not have a WWSEO parent");
/*      */         
/*  688 */         this.args[0] = getLD_NDN(entityItem);
/*  689 */         this.args[1] = this.m_elist.getEntityGroup("WWSEO").getLongDescription();
/*  690 */         createMessage(3, "MISSING_PARENT_ERR2", this.args);
/*      */       } else {
/*      */         
/*  693 */         EntityItem entityItem1 = vector.elementAt(0);
/*  694 */         String str = getAttributeFlagEnabledValue(entityItem1, "SPECBID");
/*  695 */         addDebug("doSpecBidChecks " + entityItem.getKey() + " " + entityItem1.getKey() + " SPECBID: " + str);
/*      */ 
/*      */ 
/*      */         
/*  699 */         if ("11457".equals(str)) {
/*  700 */           addHeading(3, entityItem1.getEntityGroup().getLongDescription() + " " + entityItem1 + " " + entityGroup
/*  701 */               .getLongDescription() + " GA Checks:");
/*  702 */           Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "LSEOAVAIL", "AVAIL");
/*  703 */           Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/*  704 */           Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "149");
/*      */           
/*  706 */           ArrayList arrayList1 = getCountriesAsList(vector2, getCheck_W_RE_RE(paramString));
/*  707 */           ArrayList arrayList2 = getCountriesAsList(vector3, getCheck_W_RE_RE(paramString));
/*      */           
/*  709 */           addDebug("doSpecBidChecks (65,66,67) " + entityItem.getKey() + " " + entityItem1.getKey() + " NO=SPECBID: " + str + " lseoAvailVct:" + vector1
/*  710 */               .size() + " lseoPlaAvailVct:" + vector2.size() + " allLseoPlaAvailCtry " + arrayList1);
/*      */ 
/*      */           
/*  713 */           addHeading(4, entityItem1.getEntityGroup().getLongDescription() + " " + entityItem1 + " " + entityGroup
/*  714 */               .getLongDescription() + " Planned Avail Checks:");
/*      */           
/*  716 */           if (vector2.size() > 0) {
/*      */             byte b1;
/*      */             
/*  719 */             for (b1 = 0; b1 < vector2.size(); b1++) {
/*  720 */               EntityItem entityItem2 = vector2.elementAt(b1);
/*      */               
/*  722 */               if (hasAnyCountryMatch(entityItem2, arrayList)) {
/*      */ 
/*      */                 
/*  725 */                 checkCanNotBeLater(entityItem, entityItem2, "EFFECTIVEDATE", paramEntityItem, "BUNDLPUBDATEMTRGT", getCheck_W_E_E(paramString));
/*      */               } else {
/*  727 */                 ArrayList arrayList3 = new ArrayList();
/*      */                 
/*  729 */                 getCountriesAsList(entityItem2, arrayList3, -1);
/*  730 */                 addDebug("doSpecBidChecks (66) skipping date check " + entityItem2.getKey() + " availCtrylist: " + arrayList3 + " did not contain any bdlCtry: " + arrayList);
/*      */                 
/*  732 */                 arrayList3.clear();
/*      */               } 
/*      */             } 
/*      */ 
/*      */             
/*  737 */             if (!arrayList1.containsAll(arrayList)) {
/*  738 */               addDebug("doSpecBidChecks (67) allLseoPlaAvailCtry:" + arrayList1 + " does not contain all bdlCtry: " + arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  748 */               for (b1 = 0; b1 < vector2.size(); b1++) {
/*  749 */                 EntityItem entityItem2 = vector2.elementAt(b1);
/*      */                 
/*  751 */                 ArrayList arrayList3 = new ArrayList();
/*      */                 
/*  753 */                 getCountriesAsList(entityItem2, arrayList3, -1);
/*  754 */                 if (!arrayList3.containsAll(arrayList)) {
/*  755 */                   addDebug("doSpecBidChecks (67) " + entityItem2.getKey() + " availCtrylist: " + arrayList3 + " did not contain all bdlCtry: " + arrayList);
/*      */                   
/*  757 */                   this.args[0] = getLD_NDN(entityItem);
/*  758 */                   this.args[1] = getLD_NDN(entityItem2);
/*  759 */                   this.args[2] = this.m_elist.getParentEntityGroup().getLongDescription();
/*  760 */                   this.args[3] = PokUtils.getAttributeDescription(entityGroup, "COUNTRYLIST", "COUNTRYLIST");
/*  761 */                   createMessage(getCheck_W_RE_RE(paramString), "MUST_INCLUDE_CTRY_ERR", this.args);
/*      */                 } 
/*  763 */                 arrayList3.clear();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  770 */           addHeading(4, entityItem1.getEntityGroup().getLongDescription() + " " + entityItem1 + " " + entityGroup
/*  771 */               .getLongDescription() + " Last Order Avail Checks:");
/*      */           
/*  773 */           addDebug("doSpecBidChecks (70,71,72) " + entityItem.getKey() + " " + entityItem1.getKey() + " SPECBID: " + str + " lseoAvailVct:" + vector1
/*  774 */               .size() + " lseoLOAvailVct:" + vector3.size() + " allLseoLOAvailCtry " + arrayList2);
/*      */ 
/*      */           
/*  777 */           if (vector3.size() > 0) {
/*      */             byte b1;
/*      */             
/*  780 */             for (b1 = 0; b1 < vector3.size(); b1++) {
/*  781 */               EntityItem entityItem2 = vector3.elementAt(b1);
/*      */               
/*  783 */               if (hasAnyCountryMatch(entityItem2, arrayList)) {
/*      */ 
/*      */                 
/*  786 */                 checkCanNotBeEarlier(entityItem, entityItem2, "EFFECTIVEDATE", paramEntityItem, "BUNDLUNPUBDATEMTRGT", getCheck_W_W_E(paramString));
/*      */               } else {
/*  788 */                 ArrayList arrayList3 = new ArrayList();
/*      */                 
/*  790 */                 getCountriesAsList(entityItem2, arrayList3, -1);
/*  791 */                 addDebug("doSpecBidChecks (71) skipping date check " + entityItem2.getKey() + " availCtrylist: " + arrayList3 + " did not contain any bdlCtry: " + arrayList);
/*      */                 
/*  793 */                 arrayList3.clear();
/*      */               } 
/*      */             } 
/*      */ 
/*      */             
/*  798 */             if (!arrayList2.containsAll(arrayList)) {
/*  799 */               addDebug("doSpecBidChecks (72) allLseoLOAvailCtry:" + arrayList2 + " does not contain all bdlCtry: " + arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  809 */               for (b1 = 0; b1 < vector3.size(); b1++) {
/*  810 */                 EntityItem entityItem2 = vector3.elementAt(b1);
/*      */                 
/*  812 */                 ArrayList arrayList3 = new ArrayList();
/*      */                 
/*  814 */                 getCountriesAsList(entityItem2, arrayList3, -1);
/*  815 */                 if (!arrayList3.containsAll(arrayList)) {
/*  816 */                   addDebug("doSpecBidChecks (72) " + entityItem2.getKey() + " availCtrylist: " + arrayList3 + " did not contain all bdlCtry: " + arrayList);
/*      */                   
/*  818 */                   this.args[0] = getLD_NDN(entityItem);
/*  819 */                   this.args[1] = getLD_NDN(entityItem2);
/*  820 */                   this.args[2] = this.m_elist.getParentEntityGroup().getLongDescription();
/*  821 */                   this.args[3] = PokUtils.getAttributeDescription(entityGroup, "COUNTRYLIST", "COUNTRYLIST");
/*  822 */                   createMessage(getCheck_W_RE_RE(paramString), "MUST_INCLUDE_CTRY_ERR", this.args);
/*      */                 } 
/*  824 */                 arrayList3.clear();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  831 */           vector1.clear();
/*  832 */           vector2.clear();
/*  833 */           vector3.clear();
/*  834 */           arrayList1.clear();
/*  835 */           arrayList2.clear();
/*      */         } else {
/*  837 */           addDebug("doSpecBidChecks (58)SPECBID=Yes " + entityItem.getKey());
/*  838 */           addHeading(3, entityItem1.getEntityGroup().getLongDescription() + " " + entityItem1 + " " + entityGroup
/*  839 */               .getLongDescription() + " Special Bid Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  844 */           checkCanNotBeLater(entityItem, "LSEOPUBDATEMTRGT", paramEntityItem, "BUNDLPUBDATEMTRGT", i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  850 */           addDebug("doSpecBidChecks (60) ");
/*      */ 
/*      */           
/*  853 */           checkCanNotBeEarlier(entityItem, "LSEOUNPUBDATEMTRGT", paramEntityItem, "BUNDLUNPUBDATEMTRGT", i);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  858 */         vector.clear();
/*      */       } 
/*      */     } 
/*  861 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Unique Coverage Checks:");
/*      */     
/*  863 */     i = doCheck_N_W_E(paramString);
/*  864 */     if (-1 != i) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  871 */       checkUniqueCoverage(paramEntityItem, "LSEOBUNDLEIMG", "IMG", paramEntityItem, "BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT", i, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  880 */       checkUniqueCoverage(paramEntityItem, "LSEOBUNDLEMM", "MM", paramEntityItem, "BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT", i, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  888 */       checkUniqueCoverage(paramEntityItem, "LSEOBUNDLEFB", "FB", paramEntityItem, "BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT", i, false);
/*      */     } 
/*      */ 
/*      */     
/*  892 */     arrayList.clear();
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
/*      */   private void doNotSpecBidChecks(EntityItem paramEntityItem, String paramString) throws Exception {
/*  972 */     addDebug(paramEntityItem.getKey() + " is not a SPECBID  BUNDLETYPE[" + 
/*  973 */         PokUtils.getAttributeFlagValue(paramEntityItem, "BUNDLETYPE") + "]");
/*      */     
/*  975 */     int i = getCheck_W_W_E(paramString);
/*      */ 
/*      */ 
/*      */     
/*  979 */     EANTextAttribute eANTextAttribute = (EANTextAttribute)paramEntityItem.getAttribute("SAPASSORTMODULE");
/*      */     
/*  981 */     if (eANTextAttribute == null || !eANTextAttribute.containsNLS(1)) {
/*  982 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "SAPASSORTMODULE", "SAPASSORTMODULE");
/*  983 */       createMessage(4, "NOT_SPECBID_VALUE_ERR", this.args);
/*      */     } 
/*      */     
/*  986 */     int j = getCount("SEOCGBDL");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  991 */     if (!PokUtils.contains(paramEntityItem, "BUNDLETYPE", TESTSET)) {
/*  992 */       if (j != 0)
/*      */       {
/*  994 */         this.args[0] = this.m_elist.getEntityGroup("SEOCG").getLongDescription();
/*  995 */         createMessage(i, "MUST_NOT_BE_INCLUDED_ERR", this.args);
/*      */       }
/*      */     
/*  998 */     } else if (j == 0) {
/*      */       
/* 1000 */       this.args[0] = this.m_elist.getEntityGroup("SEOCG").getLongDescription();
/* 1001 */       createMessage(i, "MUST_BE_INCLUDED_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1010 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOBUNDLEAVAIL", "AVAIL");
/* 1011 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/* 1012 */     Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 1013 */     Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "143");
/* 1014 */     addDebug("doNotSpecBidChecks bdlAvailVct: " + vector.size() + " plannedAvailVct: " + vector2.size() + " firstOrderAvailVct: " + vector3
/* 1015 */         .size() + " lastOrderAvailVct: " + vector1.size());
/*      */ 
/*      */ 
/*      */     
/* 1019 */     checkPlannedAvailsExist(vector2, getCheck_RW_RW_RE(paramString));
/*      */     
/* 1021 */     ArrayList arrayList = new ArrayList();
/* 1022 */     getCountriesAsList(paramEntityItem, arrayList, i);
/* 1023 */     addDebug("doNotSpecBidChecks (16) bdlCtry: " + arrayList);
/*      */     
/* 1025 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Planned Avail Checks:");
/* 1026 */     for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 1027 */       EntityItem entityItem = vector2.elementAt(b1);
/*      */ 
/*      */ 
/*      */       
/* 1031 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "BUNDLPUBDATEMTRGT", i);
/*      */ 
/*      */ 
/*      */       
/* 1035 */       checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem, arrayList, i);
/*      */     } 
/* 1037 */     arrayList.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     checkPlannedAvailsStatus(vector2, paramEntityItem, 3);
/*      */ 
/*      */     
/* 1046 */     Hashtable hashtable = getAvailByCountry(vector2, i);
/* 1047 */     addDebug("doNotSpecBidChecks bdl plaAvailCtry " + hashtable);
/*      */     
/* 1049 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " First Order Avail Checks:");
/*      */     
/*      */     byte b2;
/* 1052 */     for (b2 = 0; b2 < vector3.size(); b2++) {
/* 1053 */       EntityItem entityItem = vector3.elementAt(b2);
/*      */ 
/*      */ 
/*      */       
/* 1057 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "BUNDLPUBDATEMTRGT", i);
/*      */ 
/*      */ 
/*      */       
/* 1061 */       checkPlannedAvailForCtryExists(entityItem, hashtable.keySet(), i);
/*      */     } 
/*      */ 
/*      */     
/* 1065 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Last Order Avail Checks:");
/*      */ 
/*      */     
/* 1068 */     for (b2 = 0; b2 < vector1.size(); b2++) {
/* 1069 */       EntityItem entityItem = vector1.elementAt(b2);
/*      */ 
/*      */       
/* 1072 */       checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem, "BUNDLUNPUBDATEMTRGT", i);
/*      */       
/* 1074 */       Vector<EntityItem> vector4 = new Vector();
/* 1075 */       checkCtryMismatch(entityItem, hashtable, vector4, i);
/*      */ 
/*      */       
/* 1078 */       for (byte b = 0; b < vector4.size(); b++) {
/* 1079 */         EntityItem entityItem1 = vector4.elementAt(b);
/*      */ 
/*      */         
/* 1082 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", entityItem1, "EFFECTIVEDATE", i);
/*      */       } 
/* 1084 */       vector4.clear();
/*      */ 
/*      */       
/* 1087 */       checkPlannedAvailForCtryExists(entityItem, hashtable.keySet(), i);
/*      */     } 
/*      */ 
/*      */     
/* 1091 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Unique Coverage Checks:");
/*      */ 
/*      */     
/* 1094 */     if (domainInRuleList(paramEntityItem, "XCC_LIST2")) {
/* 1095 */       addDebug(paramEntityItem.getKey() + " and domain in XCCLIST2");
/*      */       
/* 1097 */       if (PokUtils.contains(paramEntityItem, "BUNDLETYPE", HWTESTSET)) {
/*      */         
/* 1099 */         j = getCount("LSEOBUNDLEIMG");
/* 1100 */         if (j == 0) {
/*      */           
/* 1102 */           this.args[0] = this.m_elist.getEntityGroup("IMG").getLongDescription();
/* 1103 */           createMessage(i, "MINIMUM_ERR", this.args);
/*      */         } 
/*      */       } else {
/*      */         
/* 1107 */         addDebug(paramEntityItem.getKey() + " BUNDLETYPE did not contain Hardware- IMG not checked");
/*      */       } 
/*      */     } 
/*      */     
/* 1111 */     i = doCheck_N_W_E(paramString);
/* 1112 */     if (-1 != i) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1117 */       checkUniqueCoverage(paramEntityItem, "LSEOBUNDLEIMG", "IMG", vector2, vector1, i, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1123 */       checkUniqueCoverage(paramEntityItem, "LSEOBUNDLEMM", "MM", vector2, vector1, i, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1130 */       checkUniqueCoverage(paramEntityItem, "LSEOBUNDLEFB", "FB", vector2, vector1, i, false);
/*      */     } 
/*      */ 
/*      */     
/* 1134 */     doNotSpecBidLSEOChecks(paramEntityItem, vector2, vector1, paramString);
/*      */     
/* 1136 */     vector.clear();
/* 1137 */     vector1.clear();
/* 1138 */     vector2.clear();
/* 1139 */     vector3.clear();
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
/*      */   private void doNotSpecBidLSEOChecks(EntityItem paramEntityItem, Vector paramVector1, Vector paramVector2, String paramString) throws Exception {
/* 1177 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/* 1178 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1179 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1180 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "WWSEO");
/* 1181 */       if (vector.size() == 0) {
/* 1182 */         addDebug("doNotSpecBidLSEOChecks " + entityItem.getKey() + " does not have a WWSEO parent");
/*      */ 
/*      */         
/* 1185 */         this.args[0] = getLD_NDN(entityItem);
/* 1186 */         this.args[1] = this.m_elist.getEntityGroup("WWSEO").getLongDescription();
/* 1187 */         createMessage(3, "MISSING_PARENT_ERR2", this.args);
/*      */       } else {
/*      */         
/* 1190 */         EntityItem entityItem1 = vector.elementAt(0);
/* 1191 */         String str = getAttributeFlagEnabledValue(entityItem1, "SPECBID");
/* 1192 */         addDebug("doNotSpecBidLSEOChecks " + entityItem.getKey() + " " + entityItem1.getKey() + " SPECBID: " + str);
/*      */ 
/*      */ 
/*      */         
/* 1196 */         if ("11457".equals(str)) {
/* 1197 */           addHeading(3, entityItem1.getEntityGroup().getLongDescription() + " " + entityItem1 + " " + entityGroup
/* 1198 */               .getLongDescription() + " GA Checks:");
/* 1199 */           addHeading(4, entityItem.getEntityGroup().getLongDescription() + " " + entityItem + " Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1204 */           checkLseoPlannedAvail(paramVector1, entityItem, paramString);
/*      */ 
/*      */           
/* 1207 */           addHeading(4, entityItem.getEntityGroup().getLongDescription() + " " + entityItem + " Last Order Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1213 */           matchLseoLastOrderAvail(paramVector2, paramVector1, entityItem);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1218 */           addHeading(3, entityItem1.getEntityGroup().getLongDescription() + " " + entityItem1 + " " + entityGroup
/* 1219 */               .getLongDescription() + " Special Bid Checks:");
/*      */ 
/*      */ 
/*      */           
/* 1223 */           this.args[0] = getLD_NDN(entityItem);
/* 1224 */           createMessage(4, "GABDL_SPECBID_ERR", this.args);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1281 */         vector.clear();
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
/*      */   private void checkLseoPlannedAvail(Vector<EntityItem> paramVector, EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 1304 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOAVAIL", "AVAIL");
/* 1305 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 1306 */     ArrayList arrayList = getCountriesAsList(vector1, getCheck_W_RE_RE(paramString));
/*      */     
/* 1308 */     addDebug("checkLseoPlannedAvail " + paramEntityItem.getKey() + " lseoAvailVct: " + vector
/* 1309 */         .size() + " lseoPlaAvailVct: " + vector1.size() + " allLseoPlaAvailCtry " + arrayList);
/*      */ 
/*      */ 
/*      */     
/* 1313 */     Hashtable hashtable = getAvailByCountry(paramVector, getCheck_W_RE_RE(paramString));
/* 1314 */     addDebug("checkLseoPlannedAvail lseoBdlPlaAvailCtryTblA: " + hashtable.keySet());
/*      */     
/* 1316 */     if (vector1.size() > 0) {
/*      */       byte b;
/*      */       
/* 1319 */       for (b = 0; b < vector1.size(); b++) {
/* 1320 */         EntityItem entityItem = vector1.elementAt(b);
/* 1321 */         Vector<EntityItem> vector2 = new Vector();
/* 1322 */         checkCtryMismatch(entityItem, hashtable, vector2, getCheck_W_RE_RE(paramString));
/*      */         
/* 1324 */         for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 1325 */           EntityItem entityItem1 = vector2.elementAt(b1);
/*      */ 
/*      */           
/* 1328 */           checkDates(paramEntityItem, entityItem, entityItem1, getCheck_W_E_E(paramString), 2);
/*      */         } 
/* 1330 */         vector2.clear();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1336 */       for (b = 0; b < paramVector.size(); b++) {
/*      */         
/* 1338 */         EntityItem entityItem = paramVector.elementAt(b);
/* 1339 */         ArrayList<?> arrayList1 = new ArrayList();
/* 1340 */         getCountriesAsList(entityItem, arrayList1, getCheck_W_RE_RE(paramString));
/* 1341 */         if (!arrayList.containsAll(arrayList1)) {
/* 1342 */           addDebug("checkLseoPlannedAvail (34) lseoPlaAvail allLseoPlaAvailCtry: " + arrayList + " does not contain all bdlPlaAvail " + entityItem
/* 1343 */               .getKey() + " bdlCtry " + arrayList1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1353 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1354 */             EntityItem entityItem1 = vector1.elementAt(b1);
/* 1355 */             ArrayList arrayList2 = new ArrayList();
/*      */             
/* 1357 */             getCountriesAsList(entityItem1, arrayList2, -1);
/* 1358 */             if (!arrayList2.containsAll(arrayList1)) {
/* 1359 */               addDebug("checkLseoPlannedAvail (34) " + entityItem1.getKey() + " availCtrylist: " + arrayList2 + " did not contain all lseoCtry: " + arrayList1);
/*      */ 
/*      */               
/* 1362 */               this.args[0] = getLD_NDN(paramEntityItem);
/* 1363 */               this.args[1] = getLD_NDN(entityItem1);
/* 1364 */               this.args[2] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 1365 */               this.args[3] = getLD_NDN(entityItem);
/* 1366 */               createMessage(getCheck_W_RE_RE(paramString), "MUST_INCLUDE_CTRY_ERR", this.args);
/*      */             } 
/* 1368 */             arrayList2.clear();
/*      */           } 
/*      */         } 
/*      */         
/* 1372 */         arrayList1.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1377 */     vector.clear();
/* 1378 */     vector1.clear();
/* 1379 */     arrayList.clear();
/* 1380 */     hashtable.clear();
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
/*      */   private void matchLseoLastOrderAvail(Vector paramVector1, Vector<EntityItem> paramVector2, EntityItem paramEntityItem) throws MiddlewareException, SQLException {
/* 1403 */     byte b1 = 3;
/* 1404 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOAVAIL", "AVAIL");
/* 1405 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/*      */     
/* 1407 */     addDebug("matchLseoLastOrderAvail lseoAvailVct: " + vector
/* 1408 */         .size() + " lseoLOAvailVct: " + vector1.size());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1413 */     Hashtable hashtable1 = getAvailByCountry(paramVector1, b1);
/* 1414 */     addDebug("matchLseoLastOrderAvail lseoBdlLOAvailCtryTblB: " + hashtable1);
/*      */ 
/*      */     
/* 1417 */     Hashtable hashtable2 = getAvailByCountry(vector1, b1);
/*      */     
/*      */     byte b2;
/*      */     
/* 1421 */     for (b2 = 0; b2 < paramVector2.size(); b2++) {
/* 1422 */       EntityItem entityItem = paramVector2.elementAt(b2);
/*      */       
/* 1424 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(entityItem, "COUNTRYLIST", b1);
/* 1425 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/* 1427 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*      */         
/* 1429 */         Vector<EntityItem> vector2 = new Vector(); byte b;
/* 1430 */         for (b = 0; b < arrayOfMetaFlag.length; b++) {
/* 1431 */           if (arrayOfMetaFlag[b].isSelected() && 
/* 1432 */             !hashtable1.containsKey(arrayOfMetaFlag[b].getFlagCode())) {
/* 1433 */             addDebug("matchLseoLastOrderAvail (38,39) lseobdl-plannedavail:" + entityItem.getKey() + " No lseobdl lastorderavail for ctry " + arrayOfMetaFlag[b]
/* 1434 */                 .getFlagCode());
/*      */             
/* 1436 */             EntityItem entityItem1 = (EntityItem)hashtable2.get(arrayOfMetaFlag[b].getFlagCode());
/* 1437 */             if (entityItem1 != null) {
/* 1438 */               addDebug("matchLseoLastOrderAvail (38,39) lseobdl-plannedavail:" + entityItem.getKey() + " LSEO-lastorderavail " + entityItem1
/* 1439 */                   .getKey() + " for ctry " + arrayOfMetaFlag[b]
/* 1440 */                   .getFlagCode());
/* 1441 */               if (!vector2.contains(entityItem1)) {
/* 1442 */                 vector2.add(entityItem1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1449 */         for (b = 0; b < vector2.size(); b++) {
/* 1450 */           EntityItem entityItem1 = vector2.elementAt(b);
/*      */ 
/*      */           
/* 1453 */           this.args[0] = getLD_NDN(paramEntityItem);
/* 1454 */           this.args[1] = getLD_NDN(entityItem1);
/* 1455 */           this.args[2] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 1456 */           this.args[3] = entityItem1.getEntityGroup().getLongDescription();
/* 1457 */           createMessage(b1, "LOAVAIL_MATCH_ERR", this.args);
/*      */         } 
/* 1459 */         vector2.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1465 */     for (b2 = 0; b2 < vector1.size(); b2++) {
/* 1466 */       EntityItem entityItem = vector1.elementAt(b2);
/* 1467 */       Vector<EntityItem> vector2 = new Vector();
/* 1468 */       checkCtryMismatch(entityItem, hashtable1, vector2, b1);
/*      */       
/* 1470 */       for (byte b = 0; b < vector2.size(); b++) {
/* 1471 */         EntityItem entityItem1 = vector2.elementAt(b);
/*      */ 
/*      */         
/* 1474 */         checkDates(paramEntityItem, entityItem, entityItem1, b1, 1);
/*      */       } 
/* 1476 */       vector2.clear();
/*      */     } 
/*      */     
/* 1479 */     vector.clear();
/* 1480 */     vector1.clear();
/* 1481 */     hashtable1.clear();
/* 1482 */     hashtable2.clear();
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
/*      */   private void checkDates(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 1497 */     String str1 = getAttrValueAndCheckLvl(paramEntityItem2, "EFFECTIVEDATE", paramInt1);
/* 1498 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem3, "EFFECTIVEDATE", paramInt1);
/* 1499 */     addDebug("checkDates lseo:" + paramEntityItem1
/* 1500 */         .getKey() + " " + paramEntityItem2.getKey() + " EFFECTIVEDATE:" + str1 + " can not be " + ((paramInt2 == 1) ? "earlier" : "later") + " than bdlavail: " + paramEntityItem3
/* 1501 */         .getKey() + " EFFECTIVEDATE:" + str2);
/* 1502 */     boolean bool = checkDates(str1, str2, paramInt2);
/* 1503 */     if (!bool) {
/*      */       
/* 1505 */       String str = "CANNOT_BE_LATER_ERR";
/* 1506 */       if (paramInt2 == 1)
/*      */       {
/* 1508 */         str = "CANNOT_BE_EARLIER_ERR2";
/*      */       }
/*      */       
/* 1511 */       this.args[0] = getLD_NDN(paramEntityItem1);
/* 1512 */       this.args[1] = getLD_NDN(paramEntityItem2);
/* 1513 */       this.args[2] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 1514 */       this.args[3] = getLD_NDN(paramEntityItem3);
/* 1515 */       createMessage(paramInt1, str, this.args);
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
/*      */   private void getModelVE(EntityItem paramEntityItem) throws Exception {
/* 1562 */     String str = "EXRPT3LSEOBDL2";
/*      */     
/* 1564 */     this.mdlList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 1565 */           .getProfile(), str), new EntityItem[] { new EntityItem(null, this.m_elist
/* 1566 */             .getProfile(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID()) });
/* 1567 */     addDebug("getModelVE:: Extract " + str + NEWLINE + PokUtils.outputList(this.mdlList));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1577 */     return "LSEOBUNDLE ABR.";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1588 */     return "1.18";
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
/*      */   private void validateOS(EntityItem paramEntityItem, int paramInt) throws MiddlewareException, SQLException {
/* 1617 */     String str = "OSLEVEL";
/* 1618 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str);
/* 1619 */     if (eANMetaAttribute == null) {
/* 1620 */       addDebug("validateOS ERROR:Attribute " + str + " NOT found in LSEOBUNDLE META data.");
/*      */       
/*      */       return;
/*      */     } 
/* 1624 */     ArrayList<String> arrayList = new ArrayList();
/*      */     
/* 1626 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str);
/* 1627 */     if (eANFlagAttribute != null) {
/*      */       
/* 1629 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1630 */       for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */         
/* 1632 */         if (arrayOfMetaFlag[b1].isSelected()) {
/* 1633 */           arrayList.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1638 */     addDebug("validateOS (5) " + paramEntityItem.getKey() + " oslevel " + arrayList);
/*      */ 
/*      */     
/* 1641 */     EntityGroup entityGroup = this.mdlList.getEntityGroup("MODEL");
/*      */     
/* 1643 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1644 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1645 */       String str1 = getAttributeFlagEnabledValue(entityItem, "COFCAT");
/* 1646 */       String str2 = getAttributeFlagEnabledValue(entityItem, "APPLICATIONTYPE");
/* 1647 */       addDebug("validateOS (5) " + entityItem.getKey() + " COFCAT: " + str1 + " APPLICATIONTYPE: " + str2);
/* 1648 */       if ("101".equals(str1) && "33".equals(str2)) {
/*      */         
/* 1650 */         Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "MODELWWSEO", "WWSEO");
/*      */         
/* 1652 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("WWSEO");
/* 1653 */         Vector<EntityItem> vector2 = new Vector(1);
/* 1654 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1655 */           EntityItem entityItem1 = vector1.elementAt(b1);
/* 1656 */           EntityItem entityItem2 = entityGroup1.getEntityItem(entityItem1.getKey());
/* 1657 */           vector2.add(entityItem2);
/*      */         } 
/* 1659 */         vector1.clear();
/* 1660 */         Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(vector2, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
/* 1661 */         addDebug("validateOS " + entityItem.getKey() + " wwseoVct " + vector2.size() + " swpsVct: " + vector3.size());
/*      */         
/* 1663 */         for (byte b2 = 0; b2 < vector3.size(); b2++) {
/* 1664 */           EntityItem entityItem1 = vector3.elementAt(b2);
/* 1665 */           addDebug("validateOS " + entityItem1.getKey() + " uplinkcnt: " + entityItem1.getUpLinkCount());
/* 1666 */           for (byte b3 = 0; b3 < entityItem1.getUpLinkCount(); b3++) {
/* 1667 */             EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(b3);
/* 1668 */             if (entityItem2.getEntityType().equals("SWFEATURE")) {
/* 1669 */               String str3 = getAttributeFlagEnabledValue(entityItem2, "SWFCCAT");
/* 1670 */               addDebug("validateOS " + entityItem2.getKey() + " SWFCCAT: " + str3);
/* 1671 */               if ("319".equals(str3)) {
/* 1672 */                 ArrayList<String> arrayList1 = new ArrayList();
/*      */                 
/* 1674 */                 EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 1675 */                 if (eANFlagAttribute1 != null) {
/*      */                   
/* 1677 */                   MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute1.get();
/* 1678 */                   for (byte b4 = 0; b4 < arrayOfMetaFlag.length; b4++) {
/*      */                     
/* 1680 */                     if (arrayOfMetaFlag[b4].isSelected()) {
/* 1681 */                       arrayList1.add(arrayOfMetaFlag[b4].getFlagCode());
/*      */                     }
/*      */                   } 
/*      */                 } 
/* 1685 */                 addDebug("validateOS ValueMetric " + entityItem2.getKey() + " oslevel " + arrayList1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1692 */                 if (!arrayList.containsAll(arrayList1)) {
/*      */                   
/* 1694 */                   this.args[0] = getLD_Value(paramEntityItem, str);
/* 1695 */                   this.args[1] = getLD_NDN(entityItem2);
/* 1696 */                   this.args[2] = getLD_Value(entityItem2, str);
/* 1697 */                   createMessage(paramInt, "OSLEVEL_ERR", this.args);
/*      */                 } 
/* 1699 */                 arrayList1.clear();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 1704 */         vector3.clear();
/* 1705 */         vector2.clear();
/*      */       } 
/*      */     } 
/* 1708 */     arrayList.clear();
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
/*      */   private void validateBUNDLETYPE(EntityItem paramEntityItem, int paramInt) throws SQLException, MiddlewareException {
/* 1730 */     String str1 = "BUNDLETYPE";
/* 1731 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str1);
/* 1732 */     if (eANMetaAttribute == null) {
/* 1733 */       addDebug("validateBUNDLETYPE ERROR:Attribute " + str1 + " NOT found in LSEOBUNDLE META data.");
/*      */       
/*      */       return;
/*      */     } 
/* 1737 */     ArrayList<String> arrayList1 = new ArrayList();
/* 1738 */     ArrayList<String> arrayList2 = new ArrayList();
/*      */     
/* 1740 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str1);
/* 1741 */     if (eANFlagAttribute != null) {
/*      */       
/* 1743 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1744 */       for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */         
/* 1746 */         if (arrayOfMetaFlag[b1].isSelected()) {
/* 1747 */           arrayList2.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1752 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "BHPRODHIERCD");
/*      */     
/* 1754 */     addDebug("validateBUNDLETYPE (6-2) " + paramEntityItem.getKey() + " bdlTypes " + arrayList2 + " prodhierFlag " + str2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1769 */     EntityGroup entityGroup = this.mdlList.getEntityGroup("MODEL");
/* 1770 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1771 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 1773 */       String str = getAttributeFlagEnabledValue(entityItem, "COFCAT");
/* 1774 */       if (str == null) {
/* 1775 */         str = "";
/*      */       }
/* 1777 */       addDebug("validateBUNDLETYPE (6-1) " + entityItem.getKey() + " COFCAT: " + str);
/* 1778 */       if (!arrayList2.contains(str))
/*      */       {
/*      */ 
/*      */         
/* 1782 */         if (!arrayList1.contains(str)) {
/* 1783 */           this.args[0] = getLD_Value(paramEntityItem, str1);
/* 1784 */           this.args[1] = getLD_Value(entityItem, "COFCAT");
/* 1785 */           createMessage(paramInt, "MODEL_TYPE_ERR", this.args);
/*      */         } 
/*      */       }
/* 1788 */       if (!arrayList1.contains(str)) {
/* 1789 */         arrayList1.add(str);
/*      */       }
/*      */     } 
/*      */     
/* 1793 */     addDebug("validateBUNDLETYPE (6-2) all mdlTypes " + arrayList1);
/* 1794 */     if (!arrayList1.containsAll(arrayList2)) {
/*      */ 
/*      */ 
/*      */       
/* 1798 */       this.args[0] = getLD_Value(paramEntityItem, str1);
/* 1799 */       this.args[1] = PokUtils.getAttributeDescription(entityGroup, "COFCAT", "COFCAT");
/* 1800 */       createMessage(paramInt, "BDLE_TYPE_ERR", this.args);
/*      */     } 
/* 1802 */     arrayList1.clear();
/* 1803 */     arrayList2.clear();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\LSEOBDLABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */