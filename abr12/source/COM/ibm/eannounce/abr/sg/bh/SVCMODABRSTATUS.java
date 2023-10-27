/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
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
/*      */ public class SVCMODABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*      */   private Vector svcmodAvailVct;
/*      */   private Vector svcmodLOAvailVctE;
/*      */   private Vector svcmodPlaAvailVctA;
/*      */   private Vector svcmodFOAvailVctC;
/*      */   private Vector svcmodEOSAvailVctG;
/*      */   private Vector svcmodEOMAvailVctM;
/*   87 */   private Hashtable svcmdlPlaAvailCtryTblA = null;
/*   88 */   private Hashtable svcmdlFOAvailCtryTblC = null;
/*   89 */   private Hashtable svcmdlLOAvailCtryTblE = null;
/*   90 */   private Hashtable svcmdlEOSAvailCtryTblG = null;
/*   91 */   private Vector mdlPlaAnnVct = new Vector();
/*      */   
/*   93 */   private String SVCSEOFO = null;
/*   94 */   private String SVCSEOPA = null;
/*   95 */   private String SVCSEOAD = null;
/*   96 */   private String SVCMODFO = null;
/*   97 */   private String SVCMODPA = null;
/*   98 */   private String SVCMODAD = null;
/*      */   
/*  100 */   private static Set set = new HashSet();
/*      */   
/*      */   static {
/*  103 */     set.add("MSWH");
/*  104 */     set.add("MAIN");
/*  105 */     set.add("CABL");
/*  106 */     set.add("PMG");
/*  107 */     set.add("MANL");
/*  108 */     set.add("MATM");
/*  109 */     set.add("PLA");
/*  110 */     set.add("ALP");
/*  111 */     set.add("CSW");
/*  112 */     set.add("EDUC");
/*  113 */     set.add("MNPM");
/*  114 */     set.add("***");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String OFERCONFIGTYPE_Configurable = "CNFIG";
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  123 */     return domainInList();
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  165 */     if (doR10processing()) {
/*  166 */       doR4R_RFAProcessing("SVCMODAVAIL");
/*      */     } else {
/*  168 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
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
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  188 */     if (doR10processing()) {
/*  189 */       doFinal_RFAProcessing("SVCMODAVAIL");
/*      */     } else {
/*  191 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
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
/*      */   private void doFinal_RFAProcessing(String paramString) throws MiddlewareRequestException, MiddlewareException, SQLException {
/*  214 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  216 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, paramString, "AVAIL");
/*      */     
/*  218 */     for (byte b = 0; b < vector.size(); b++) {
/*  219 */       EntityItem entityItem1 = vector.elementAt(b);
/*  220 */       String str = PokUtils.getAttributeFlagValue(entityItem1, "AVAILANNTYPE");
/*  221 */       if (str == null) {
/*  222 */         str = "RFA";
/*      */       }
/*      */ 
/*      */       
/*  226 */       addDebug("doFinal_RFAProcessing: " + entityItem1.getKey() + " availAnntypeFlag " + str);
/*      */ 
/*      */       
/*  229 */       if (statusIsFinal(entityItem1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  274 */         setSinceFirstFinal(entityItem, "ADSABRSTATUS");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  286 */     vector.clear();
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  469 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*      */     
/*  471 */     int i = getCheck_W_E_E(paramString);
/*      */     
/*  473 */     checkPrftctr(paramEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  478 */     checkCanNotBeEarlier(paramEntityItem, "WITHDRAWDATE", "ANNDATE", 4);
/*      */ 
/*      */ 
/*      */     
/*  482 */     int j = getCount("SVCMODTAXRELEVANCE");
/*  483 */     if (j == 0) {
/*      */ 
/*      */       
/*  486 */       this.args[0] = this.m_elist.getEntityGroup("TAXCATG").getLongDescription();
/*  487 */       createMessage(getCheck_W_W_E(paramString), "MINIMUM_ERR", this.args);
/*      */     } 
/*      */ 
/*      */     
/*  491 */     j = getCount("SVCMODTAXGRP");
/*  492 */     if (j == 0) {
/*      */ 
/*      */       
/*  495 */       this.args[0] = this.m_elist.getEntityGroup("TAXGRP").getLongDescription();
/*  496 */       createMessage(getCheck_W_W_E(paramString), "MINIMUM_ERR", this.args);
/*      */     } 
/*      */     
/*  499 */     getAvails(paramEntityItem, paramString);
/*      */     
/*  501 */     checkAvails(paramEntityItem, paramString, i);
/*      */     
/*  503 */     checkConfig(paramEntityItem);
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
/*      */   private void checkConfig(EntityItem paramEntityItem) throws MiddlewareException, SQLException {
/*  567 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "OFERCONFIGTYPE");
/*  568 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " " + 
/*  569 */         PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "OFERCONFIGTYPE", "OFERCONFIGTYPE") + " checks:");
/*      */ 
/*      */     
/*  572 */     int i = getCount("SVCMODCHRGCOMP");
/*  573 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CHRGCOMP");
/*  574 */     addDebug("checkConfig " + paramEntityItem.getKey() + " oferconfigtype " + str);
/*      */     
/*  576 */     if ("CNFIG".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  589 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*  590 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/*      */ 
/*      */ 
/*      */         
/*  594 */         checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */       } 
/*      */       
/*  597 */       addHeading(3, this.m_elist.getEntityGroup("PRCPT").getLongDescription() + " checks:");
/*      */ 
/*      */       
/*  600 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityGroup, "CHRGCOMPPRCPT", "PRCPT");
/*  601 */       addDebug("checkConfig prcptVctQ " + vector1.size());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  623 */       checkSVCMODAvails(paramEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  636 */       checkSVCSEOAvails(paramEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  652 */       checkFODates(paramEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  664 */       for (byte b2 = 0; b2 < vector1.size(); b2++) {
/*  665 */         EntityItem entityItem = vector1.elementAt(b2);
/*  666 */         String str1 = checkCtryMismatch(entityItem, this.svcmdlPlaAvailCtryTblA, 4);
/*  667 */         if (str1.length() > 0) {
/*  668 */           addDebug(entityItem.getKey() + " COUNTRYLIST had extra [" + str1 + "]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  677 */           this.args[0] = getLD_NDN(entityItem);
/*  678 */           this.args[1] = PokUtils.getAttributeDescription(this.m_elist.getEntityGroup("AVAIL"), "COUNTRYLIST", "COUNTRYLIST");
/*      */           
/*  680 */           this.args[2] = this.m_elist.getEntityGroup("SVCMOD").getLongDescription() + " " + this.m_elist
/*  681 */             .getEntityGroup("AVAIL").getLongDescription();
/*  682 */           this.args[3] = PokUtils.getAttributeDescription(this.m_elist.getEntityGroup("AVAIL"), "COUNTRYLIST", "COUNTRYLIST");
/*      */           
/*  684 */           this.args[4] = str1;
/*  685 */           createMessage(4, "INCLUDE_ERR2", this.args);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  690 */       addHeading(3, "Referenced " + paramEntityItem.getEntityGroup().getLongDescription() + " checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  698 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODSVCMOD", "SVCMOD");
/*  699 */       Vector vector = PokUtils.getAllLinkedEntities(vector2, "SVCMODAVAIL", "AVAIL");
/*  700 */       Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/*  701 */       addDebug("svcmodVctT " + vector2.size() + " availVctU " + vector.size() + " plaAvailVctU " + vector3
/*  702 */           .size());
/*  703 */       if (this.svcmdlPlaAvailCtryTblA.size() > 0 && vector3
/*      */ 
/*      */         
/*  706 */         .size() > 0)
/*      */       {
/*  708 */         boolean bool = false;
/*      */         
/*      */         byte b;
/*  711 */         label62: for (b = 0; b < vector3.size(); b++) {
/*  712 */           EntityItem entityItem = vector3.elementAt(b);
/*  713 */           addDebug("referenced svcmod plannedavail " + entityItem.getKey() + " COUNTRYLIST [" + 
/*  714 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + "]");
/*  715 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(entityItem, "COUNTRYLIST", 4);
/*      */           
/*  717 */           if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */             
/*  719 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  720 */             for (byte b3 = 0; b3 < arrayOfMetaFlag.length; b3++) {
/*  721 */               if (arrayOfMetaFlag[b3].isSelected() && 
/*  722 */                 this.svcmdlPlaAvailCtryTblA.keySet().contains(arrayOfMetaFlag[b3].getFlagCode())) {
/*  723 */                 bool = true;
/*      */                 
/*      */                 break label62;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  730 */         if (!bool) {
/*  731 */           for (b = 0; b < this.svcmodPlaAvailVctA.size(); b++) {
/*  732 */             EntityItem entityItem = this.svcmodPlaAvailVctA.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  741 */             addDebug(" nointersection root plannedavail " + entityItem.getKey());
/*  742 */             for (byte b3 = 0; b3 < vector2.size(); b3++) {
/*  743 */               EntityItem entityItem1 = vector2.elementAt(b3);
/*  744 */               addDebug("nointersection referenced " + entityItem1.getKey());
/*      */ 
/*      */               
/*  747 */               this.args[0] = getLD_NDN(entityItem);
/*  748 */               this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */               
/*  750 */               this.args[2] = getLD_NDN(entityItem1);
/*  751 */               this.args[3] = entityItem.getEntityGroup().getLongDescription();
/*  752 */               this.args[4] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */               
/*  754 */               createMessage(4, "INTERSECT_ERR", this.args);
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  769 */       if (i != 0) {
/*      */         
/*  771 */         this.args[0] = getLD_Value(paramEntityItem, "OFERCONFIGTYPE");
/*  772 */         this.args[1] = entityGroup.getLongDescription();
/*  773 */         createMessage(4, "MUST_NOT_HAVE2_ERR", this.args);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  779 */       i = getCount("SVCMODSVCSEO");
/*  780 */       if (i != 0) {
/*      */         
/*  782 */         this.args[0] = getLD_Value(paramEntityItem, "OFERCONFIGTYPE");
/*  783 */         this.args[1] = this.m_elist.getEntityGroup("SVCSEO").getLongDescription();
/*  784 */         createMessage(4, "MUST_NOT_HAVE2_ERR", this.args);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  790 */       i = getCount("SVCMODSVCSEOREF");
/*  791 */       if (i != 0) {
/*      */         
/*  793 */         this.args[0] = getLD_Value(paramEntityItem, "OFERCONFIGTYPE");
/*  794 */         this.args[1] = this.m_elist.getEntityGroup("SVCMODSVCSEOREF").getLongDescription() + " " + this.m_elist
/*  795 */           .getEntityGroup("SVCSEO").getLongDescription();
/*  796 */         createMessage(4, "MUST_NOT_HAVE2_ERR", this.args);
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
/*      */   private void checkFODates(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  820 */     String str1 = null;
/*  821 */     String str2 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  826 */     if (this.SVCSEOFO != null) {
/*  827 */       str1 = this.SVCSEOFO;
/*  828 */     } else if (this.SVCSEOAD != null) {
/*  829 */       str1 = this.SVCSEOAD;
/*  830 */     } else if (this.SVCSEOPA != null) {
/*  831 */       str1 = this.SVCSEOPA;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  838 */     if (this.SVCMODFO != null) {
/*  839 */       str2 = this.SVCMODFO;
/*  840 */     } else if (this.SVCMODAD != null) {
/*  841 */       str2 = this.SVCMODAD;
/*  842 */     } else if (this.SVCMODPA != null) {
/*  843 */       str2 = this.SVCMODPA;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  852 */     if (str2 != null && str1 != null && 
/*  853 */       str2.compareTo(str1) > 0) {
/*  854 */       this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/*  855 */       this.args[1] = str2;
/*  856 */       this.args[3] = str1;
/*      */       
/*  858 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODSVCSEO", "SVCSEO");
/*  859 */       for (byte b = 0; b < vector.size(); b++) {
/*  860 */         EntityItem entityItem = vector.elementAt(b);
/*  861 */         this.args[2] = getLD_NDN(entityItem);
/*  862 */         createMessage(3, "FODATE_ERR", this.args);
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
/*      */   private void checkSVCSEOAvails(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  883 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODSVCSEO", "SVCSEO");
/*  884 */     addDebug("svcseoVct " + vector.size());
/*  885 */     for (byte b = 0; b < vector.size(); b++) {
/*  886 */       EntityItem entityItem = vector.elementAt(b);
/*  887 */       addHeading(3, getLD_NDN(entityItem) + " Planned Availability checks:");
/*      */       
/*  889 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "SVCSEOAVAIL", "AVAIL");
/*      */       
/*  891 */       Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/*      */       
/*  893 */       Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "143");
/*      */ 
/*      */       
/*  896 */       addDebug("svcseo " + entityItem.getKey() + " svcseoAvailVctW " + vector1.size() + " svcseoplaAvailVctX " + vector2
/*  897 */           .size() + " svcseofoAvailVct " + vector3.size());
/*      */       byte b1;
/*  899 */       for (b1 = 0; b1 < vector2.size(); b1++) {
/*  900 */         EntityItem entityItem1 = vector2.elementAt(b1);
/*  901 */         String str = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", "", null, false);
/*      */         
/*  903 */         if (this.SVCSEOPA == null) {
/*  904 */           this.SVCSEOPA = str;
/*      */         }
/*  906 */         else if (this.SVCSEOPA.compareTo(str) > 0) {
/*  907 */           this.SVCSEOPA = str;
/*      */         } 
/*      */ 
/*      */         
/*  911 */         Vector<EntityItem> vector4 = PokUtils.getAllLinkedEntities(entityItem1, "AVAILANNA", "ANNOUNCEMENT");
/*  912 */         addDebug(entityItem1.getKey() + " annVct " + vector4.size());
/*  913 */         for (byte b2 = 0; b2 < vector4.size(); b2++) {
/*  914 */           EntityItem entityItem2 = vector4.elementAt(b2);
/*  915 */           String str1 = getAttributeFlagEnabledValue(entityItem2, "ANNTYPE");
/*  916 */           addDebug(entityItem2.getKey() + " type " + str1);
/*      */           
/*  918 */           if ("19".equals(str1)) {
/*      */             
/*  920 */             String str2 = PokUtils.getAttributeValue(entityItem2, "ANNDATE", "", null, false);
/*  921 */             if (this.SVCSEOAD == null) {
/*  922 */               this.SVCSEOAD = str2;
/*      */             }
/*  924 */             else if (this.SVCSEOAD.compareTo(str2) > 0) {
/*      */ 
/*      */               
/*  927 */               this.SVCSEOAD = str2;
/*      */             } 
/*      */             
/*  930 */             addDebug("svcseo pla avail ann " + entityItem2.getKey() + " annDate " + str2 + " SVCSEOAD " + this.SVCSEOAD);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  935 */         vector4.clear();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  941 */       for (b1 = 0; b1 < vector3.size(); b1++) {
/*  942 */         EntityItem entityItem1 = vector3.elementAt(b1);
/*  943 */         String str = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", "", null, false);
/*      */         
/*  945 */         if (this.SVCSEOFO == null) {
/*  946 */           this.SVCSEOFO = str;
/*      */         }
/*  948 */         else if (this.SVCSEOFO.compareTo(str) > 0) {
/*  949 */           this.SVCSEOFO = str;
/*      */         } 
/*      */         
/*  952 */         addDebug("svcseo fo avail " + entityItem1.getKey() + " effDate " + str + " SVCSEOFO " + this.SVCSEOFO);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  957 */     if (this.SVCSEOFO == null) {
/*  958 */       this.args[0] = "No Date";
/*      */     } else {
/*  960 */       this.args[0] = this.SVCSEOFO;
/*      */     } 
/*  962 */     this.args[1] = this.m_elist.getEntityGroup("SVCSEO").getLongDescription();
/*  963 */     this.args[2] = "First Order date";
/*      */     
/*  965 */     addResourceMsg("VALUE_FND", this.args);
/*      */     
/*  967 */     if (this.SVCSEOPA == null) {
/*  968 */       this.args[0] = "No Date";
/*      */     } else {
/*  970 */       this.args[0] = this.SVCSEOPA;
/*      */     } 
/*  972 */     this.args[1] = this.m_elist.getEntityGroup("SVCSEO").getLongDescription();
/*  973 */     this.args[2] = "Planned Availability date";
/*  974 */     addResourceMsg("VALUE_FND", this.args);
/*      */ 
/*      */     
/*  977 */     if (this.SVCSEOAD == null) {
/*  978 */       this.args[0] = "No Date";
/*      */     } else {
/*  980 */       this.args[0] = this.SVCSEOAD;
/*      */     } 
/*  982 */     this.args[1] = this.m_elist.getEntityGroup("SVCSEO").getLongDescription();
/*  983 */     this.args[2] = "New Announcement date";
/*  984 */     addResourceMsg("VALUE_FND", this.args);
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
/*      */   private void checkSVCMODAvails(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1008 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PRCPT");
/*      */ 
/*      */     
/* 1011 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODAVAIL", "AVAIL");
/*      */ 
/*      */     
/* 1014 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/*      */     
/* 1016 */     Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "143");
/*      */     
/* 1018 */     ArrayList arrayList = getCountriesAsList(vector1, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1024 */     addDebug("root availVctS " + vector.size() + " plaAvailVctR " + vector1.size() + " foAvailVct " + vector2
/* 1025 */         .size() + " plaavailCtrys " + arrayList);
/*      */ 
/*      */ 
/*      */     
/* 1029 */     for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1030 */       EntityItem entityItem = vector1.elementAt(b1);
/* 1031 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", null, false);
/*      */       
/* 1033 */       if (this.SVCMODPA == null) {
/* 1034 */         this.SVCMODPA = str;
/*      */       }
/* 1036 */       else if (this.SVCMODPA.compareTo(str) > 0) {
/* 1037 */         this.SVCMODPA = str;
/*      */       } 
/*      */ 
/*      */       
/* 1041 */       addDebug("svcmod pla avail " + entityItem.getKey() + " effDate " + str + " SVCMODPA " + this.SVCMODPA);
/*      */ 
/*      */       
/* 1044 */       Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 1045 */       addDebug(entityItem.getKey() + " annVct " + vector3.size());
/* 1046 */       for (byte b = 0; b < vector3.size(); b++) {
/* 1047 */         EntityItem entityItem1 = vector3.elementAt(b);
/* 1048 */         String str1 = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/* 1049 */         addDebug(entityItem1.getKey() + " type " + str1);
/*      */         
/* 1051 */         if ("19".equals(str1)) {
/*      */           
/* 1053 */           String str2 = PokUtils.getAttributeValue(entityItem1, "ANNDATE", "", null, false);
/* 1054 */           if (this.SVCMODAD == null) {
/* 1055 */             this.SVCMODAD = str2;
/*      */           }
/* 1057 */           else if (this.SVCMODAD.compareTo(str2) > 0) {
/*      */             
/* 1059 */             this.SVCMODAD = str2;
/*      */           } 
/*      */           
/* 1062 */           addDebug("svcmod pla avail ann " + entityItem1.getKey() + " annDate " + str2 + " SVCMODAD " + this.SVCMODAD);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1067 */       vector3.clear();
/*      */     } 
/*      */ 
/*      */     
/* 1071 */     if (this.SVCMODPA == null) {
/* 1072 */       this.args[0] = "No Date";
/*      */     } else {
/* 1074 */       this.args[0] = this.SVCMODPA;
/*      */     } 
/* 1076 */     this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 1077 */     this.args[2] = "Planned Availability date";
/* 1078 */     addResourceMsg("VALUE_FND", this.args);
/*      */     
/* 1080 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("CNTRYEFF");
/* 1081 */     addHeading(3, entityGroup2.getLongDescription() + " and Planned Availability checks:");
/*      */     byte b2;
/* 1083 */     for (b2 = 0; b2 < entityGroup1.getEntityItemCount(); b2++) {
/* 1084 */       EntityItem entityItem = entityGroup1.getEntityItem(b2);
/* 1085 */       Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem, "PRCPTCNTRYEFF", "CNTRYEFF");
/* 1086 */       addDebug(" " + entityItem.getKey() + " cntryeffVct " + vector3.size());
/* 1087 */       for (byte b = 0; b < vector3.size(); b++) {
/* 1088 */         EntityItem entityItem1 = vector3.elementAt(b);
/* 1089 */         String str = checkCtryMismatch(entityItem1, arrayList, 4);
/* 1090 */         if (str.length() > 0) {
/* 1091 */           addDebug(entityItem1.getKey() + " COUNTRYLIST had extra [" + str + "]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1101 */           this.args[0] = getLD_NDN(entityItem);
/* 1102 */           this.args[1] = getLD_NDN(entityItem1);
/*      */           
/* 1104 */           this.args[2] = paramEntityItem.getEntityGroup().getLongDescription() + " " + this.m_elist
/* 1105 */             .getEntityGroup("AVAIL").getLongDescription();
/* 1106 */           this.args[3] = PokUtils.getAttributeDescription(entityGroup2, "COUNTRYLIST", "COUNTRYLIST");
/* 1107 */           this.args[4] = str;
/* 1108 */           createMessage(4, "INCLUDE_ERR2", this.args);
/*      */         } 
/*      */       } 
/* 1111 */       vector3.clear();
/*      */     } 
/*      */ 
/*      */     
/* 1115 */     if (this.SVCMODAD == null) {
/* 1116 */       this.args[0] = "No Date";
/*      */     } else {
/* 1118 */       this.args[0] = this.SVCMODAD;
/*      */     } 
/* 1120 */     this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 1121 */     this.args[2] = "New Announcement date";
/* 1122 */     addResourceMsg("VALUE_FND", this.args);
/*      */ 
/*      */     
/* 1125 */     for (b2 = 0; b2 < vector2.size(); b2++) {
/* 1126 */       EntityItem entityItem = vector2.elementAt(b2);
/* 1127 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", null, false);
/*      */       
/* 1129 */       if (this.SVCMODFO == null) {
/* 1130 */         this.SVCMODFO = str;
/*      */       }
/* 1132 */       else if (this.SVCMODFO.compareTo(str) > 0) {
/* 1133 */         this.SVCMODFO = str;
/*      */       } 
/*      */       
/* 1136 */       addDebug("svcmod fo avail " + entityItem.getKey() + " effDate " + str + " SVCMODFO " + this.SVCMODFO);
/*      */     } 
/*      */ 
/*      */     
/* 1140 */     if (this.SVCMODFO == null) {
/* 1141 */       this.args[0] = "No Date";
/*      */     } else {
/* 1143 */       this.args[0] = this.SVCMODFO;
/*      */     } 
/* 1145 */     this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 1146 */     this.args[2] = "First Order date";
/* 1147 */     addResourceMsg("VALUE_FND", this.args);
/*      */ 
/*      */     
/* 1150 */     arrayList.clear();
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
/*      */   private void getAvails(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 1162 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 1163 */     if (entityGroup == null) {
/* 1164 */       throw new MiddlewareException("AVAIL is missing from extract for " + this.m_abri.getVEName());
/*      */     }
/*      */     
/* 1167 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Availability RFA checks:");
/* 1168 */     checkAvailAnnType();
/*      */ 
/*      */     
/* 1171 */     this.svcmodAvailVct = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODAVAIL", "AVAIL");
/*      */ 
/*      */ 
/*      */     
/* 1175 */     this.svcmodPlaAvailVctA = PokUtils.getEntitiesWithMatchedAttr(this.svcmodAvailVct, "AVAILTYPE", "146");
/*      */ 
/*      */     
/* 1178 */     this.svcmodFOAvailVctC = PokUtils.getEntitiesWithMatchedAttr(this.svcmodAvailVct, "AVAILTYPE", "143");
/*      */ 
/*      */     
/* 1181 */     this.svcmodLOAvailVctE = PokUtils.getEntitiesWithMatchedAttr(this.svcmodAvailVct, "AVAILTYPE", "149");
/*      */ 
/*      */     
/* 1184 */     this.svcmodEOSAvailVctG = PokUtils.getEntitiesWithMatchedAttr(this.svcmodAvailVct, "AVAILTYPE", "151");
/*      */     
/* 1186 */     this.svcmodEOMAvailVctM = PokUtils.getEntitiesWithMatchedAttr(this.svcmodAvailVct, "AVAILTYPE", "200");
/* 1187 */     addDebug("getAvails SVCMODAVAIL svcmodPlaAvailVctA: " + this.svcmodPlaAvailVctA.size() + " svcmodFOAvailVctC: " + this.svcmodFOAvailVctC
/* 1188 */         .size() + " svcmodLOAvailVctE: " + this.svcmodLOAvailVctE.size() + " svcmodEOSAvailVctG: " + this.svcmodEOSAvailVctG
/* 1189 */         .size() + " svcmodEOMAvailVctM: " + this.svcmodEOMAvailVctM.size());
/*      */     
/* 1191 */     this.svcmdlPlaAvailCtryTblA = getAvailByCountry(this.svcmodPlaAvailVctA, getCheck_W_W_E(paramString));
/* 1192 */     addDebug("getAvails SVCMODAVAIL svcmdlPlaAvailCtryTblA " + this.svcmdlPlaAvailCtryTblA.keySet());
/*      */     
/* 1194 */     this.svcmdlFOAvailCtryTblC = getAvailByCountry(this.svcmodFOAvailVctC, getCheck_W_W_E(paramString));
/* 1195 */     addDebug("getAvails SVCMODAVAIL svcmdlFOAvailCtryTblC: " + this.svcmdlFOAvailCtryTblC.keySet());
/*      */     
/* 1197 */     this.svcmdlLOAvailCtryTblE = getAvailByCountry(this.svcmodLOAvailVctE, getCheck_W_W_E(paramString));
/* 1198 */     addDebug("getAvails SVCMODAVAIL svcmdlLOAvailCtryTblE: " + this.svcmdlLOAvailCtryTblE.keySet());
/*      */     
/* 1200 */     this.svcmdlEOSAvailCtryTblG = getAvailByCountry(this.svcmodEOSAvailVctG, getCheck_W_W_E(paramString));
/* 1201 */     addDebug("getAvails SVCMODAVAIL svcmdlEOSAvailCtryTblG: " + this.svcmdlEOSAvailCtryTblG.keySet());
/*      */   }
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 1206 */     super.dereference();
/*      */     
/* 1208 */     this.SVCSEOFO = null;
/* 1209 */     this.SVCSEOPA = null;
/* 1210 */     this.SVCSEOAD = null;
/* 1211 */     this.SVCMODFO = null;
/* 1212 */     this.SVCMODPA = null;
/* 1213 */     this.SVCMODAD = null;
/*      */     
/* 1215 */     this.mdlPlaAnnVct.clear();
/* 1216 */     this.mdlPlaAnnVct = null;
/* 1217 */     if (this.svcmodAvailVct != null) {
/* 1218 */       this.svcmodAvailVct.clear();
/* 1219 */       this.svcmodAvailVct = null;
/*      */     } 
/* 1221 */     if (this.svcmodLOAvailVctE != null) {
/* 1222 */       this.svcmodLOAvailVctE.clear();
/* 1223 */       this.svcmodLOAvailVctE = null;
/*      */     } 
/* 1225 */     if (this.svcmodPlaAvailVctA != null) {
/* 1226 */       this.svcmodPlaAvailVctA.clear();
/* 1227 */       this.svcmodPlaAvailVctA = null;
/*      */     } 
/* 1229 */     if (this.svcmodFOAvailVctC != null) {
/* 1230 */       this.svcmodFOAvailVctC.clear();
/* 1231 */       this.svcmodFOAvailVctC = null;
/*      */     } 
/* 1233 */     if (this.svcmodEOSAvailVctG != null) {
/* 1234 */       this.svcmodEOSAvailVctG.clear();
/* 1235 */       this.svcmodEOSAvailVctG = null;
/*      */     } 
/* 1237 */     if (this.svcmdlPlaAvailCtryTblA != null) {
/* 1238 */       this.svcmdlPlaAvailCtryTblA.clear();
/* 1239 */       this.svcmdlPlaAvailCtryTblA = null;
/*      */     } 
/* 1241 */     if (this.svcmdlFOAvailCtryTblC != null) {
/* 1242 */       this.svcmdlFOAvailCtryTblC.clear();
/* 1243 */       this.svcmdlFOAvailCtryTblC = null;
/*      */     } 
/* 1245 */     if (this.svcmdlLOAvailCtryTblE != null) {
/* 1246 */       this.svcmdlLOAvailCtryTblE.clear();
/* 1247 */       this.svcmdlLOAvailCtryTblE = null;
/*      */     } 
/* 1249 */     if (this.svcmdlEOSAvailCtryTblG != null) {
/* 1250 */       this.svcmdlEOSAvailCtryTblG.clear();
/* 1251 */       this.svcmdlEOSAvailCtryTblG = null;
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
/*      */   private void checkPrftctr(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1265 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " SVCMODTAXRELEVANCE Checks:");
/* 1266 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "PRFTCTR");
/* 1267 */     if ("P4022".equals(str) || "P4016".equals(str)) {
/* 1268 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODTAXRELEVANCE", "TAXCATG");
/* 1269 */       if (vector != null && !vector.isEmpty()) {
/* 1270 */         for (byte b = 0; b < vector.size(); b++) {
/* 1271 */           EntityItem entityItem = vector.get(b);
/* 1272 */           String str1 = PokUtils.getAttributeFlagValue(entityItem, "TAXCNTRY");
/* 1273 */           if ("1464".equals(str1)) {
/* 1274 */             EntityItem entityItem1 = entityItem.getUpLink().get(0);
/*      */             
/* 1276 */             String str2 = PokUtils.getAttributeValue(entityItem1, "TAXCLS", "", "");
/* 1277 */             if (!set.contains(str2)) {
/* 1278 */               this.args[0] = getLD_NDN(entityItem1);
/*      */ 
/*      */               
/* 1281 */               this.args[1] = "Tax Country Canada";
/* 1282 */               addError("INVALID_VALUES_ERR", this.args);
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
/*      */   private void checkAvails(EntityItem paramEntityItem, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 1305 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */ 
/*      */     
/* 1308 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " " + entityGroup.getLongDescription() + " Planned Availability Checks:");
/*      */     
/* 1310 */     checkSvcmodPlaAvails(paramEntityItem, paramString);
/*      */     
/* 1312 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " " + entityGroup.getLongDescription() + " First Order Checks:");
/*      */     
/* 1314 */     checkSvcmodFOAvails(paramEntityItem, paramString);
/*      */     
/* 1316 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " " + entityGroup.getLongDescription() + " Last Order Checks:");
/*      */     
/* 1318 */     checkSvcmodLOAvails(paramEntityItem, paramString);
/*      */     
/* 1320 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " " + entityGroup.getLongDescription() + " End of Marketing Checks:");
/*      */     
/* 1322 */     checkSvcmodEOMAvails(paramEntityItem, paramString);
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
/*      */   private void checkSvcmodPlaAvails(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/* 1349 */     addDebug("checkSvcmodPlaAvails svcmodPlaAvailVctA " + this.svcmodPlaAvailVctA.size());
/*      */ 
/*      */ 
/*      */     
/* 1353 */     checkPlannedAvailsExist(this.svcmodPlaAvailVctA, getCheck_RW_RW_RE(paramString));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1361 */     checkPlannedAvailsStatus(this.svcmodPlaAvailVctA, paramEntityItem, 3);
/*      */     
/* 1363 */     for (byte b = 0; b < this.svcmodPlaAvailVctA.size(); b++) {
/* 1364 */       EntityItem entityItem = this.svcmodPlaAvailVctA.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1370 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", getCheck_W_E_E(paramString));
/*      */ 
/*      */ 
/*      */       
/* 1374 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 1375 */       addDebug("checkSvcmodPlaAvails " + entityItem.getKey() + " availAnntypeFlag " + str);
/* 1376 */       if (str == null) {
/* 1377 */         str = "RFA";
/*      */       }
/*      */       
/* 1380 */       if ("RFA".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1392 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1398 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 1399 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 1400 */           String str1 = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 1401 */           addDebug("checkSvcmodPlaAvails " + entityItem1.getKey() + " anntypeFlag " + str1);
/* 1402 */           if (!"19".equals(str1)) {
/*      */             
/* 1404 */             this.args[0] = getLD_NDN(entityItem);
/* 1405 */             this.args[1] = getLD_NDN(entityItem1);
/* 1406 */             createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 1413 */             checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem, "ANNDATE", getCheck_W_W_E(paramString));
/* 1414 */             this.mdlPlaAnnVct.add(entityItem1);
/*      */           } 
/*      */         } 
/*      */         
/* 1418 */         vector.clear();
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
/*      */   private void checkSvcmodFOAvails(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/* 1446 */     addDebug("checkSvcmodFOAvails svcmodFOAvailVctC " + this.svcmodFOAvailVctC.size());
/*      */     
/* 1448 */     for (byte b = 0; b < this.svcmodFOAvailVctC.size(); b++) {
/* 1449 */       EntityItem entityItem = this.svcmodFOAvailVctC.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1455 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", getCheck_W_W_E(paramString));
/*      */       
/* 1457 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 1458 */       addDebug("checkSvcmodFOAvails " + entityItem.getKey() + " availAnntypeFlag " + str);
/* 1459 */       if (str == null) {
/* 1460 */         str = "RFA";
/*      */       }
/*      */       
/* 1463 */       if ("RFA".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1473 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1479 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 1480 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 1481 */           String str1 = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 1482 */           addDebug("checkSvcmodFOAvails " + entityItem1.getKey() + " anntypeFlag " + str1);
/* 1483 */           if (!"19".equals(str1)) {
/*      */             
/* 1485 */             this.args[0] = getLD_NDN(entityItem);
/* 1486 */             this.args[1] = getLD_NDN(entityItem1);
/* 1487 */             createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */             
/* 1495 */             checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem, "ANNDATE", getCheck_W_W_E(paramString));
/*      */           } 
/*      */         } 
/* 1498 */         vector.clear();
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
/*      */   private void checkSvcmodLOAvails(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/* 1534 */     addDebug("checkSvcmodLOAvails svcmodLOAvailVctE " + this.svcmodLOAvailVctE.size());
/*      */     
/* 1536 */     for (byte b = 0; b < this.svcmodLOAvailVctE.size(); b++) {
/* 1537 */       EntityItem entityItem = this.svcmodLOAvailVctE.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1542 */       checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem, "WTHDRWEFFCTVDATE", getCheck_W_W_E(paramString));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1547 */       checkPlannedAvailForCtryExists(entityItem, this.svcmdlPlaAvailCtryTblA.keySet(), getCheck_W_W_E(paramString));
/*      */       
/* 1549 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 1550 */       addDebug("checkSvcmodLOAvails " + entityItem.getKey() + " availAnntypeFlag " + str);
/* 1551 */       if (str == null) {
/* 1552 */         str = "RFA";
/*      */       }
/*      */       
/* 1555 */       if ("RFA".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1565 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1570 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 1571 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 1572 */           String str1 = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 1573 */           addDebug("checkSvcmodLOAvails " + entityItem1.getKey() + " anntypeFlag " + str1);
/* 1574 */           if (!"14".equals(str1)) {
/*      */             
/* 1576 */             this.args[0] = getLD_NDN(entityItem);
/* 1577 */             this.args[1] = getLD_NDN(entityItem1);
/* 1578 */             createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 1585 */             checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem, "WITHDRAWDATE", getCheck_W_W_E(paramString));
/*      */           } 
/*      */         } 
/* 1588 */         vector.clear();
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
/*      */   private void checkSvcmodEOMAvails(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/* 1619 */     addDebug("checkSvcmodEOMAvails svcmodEOMAvailVctM " + this.svcmodEOMAvailVctM.size());
/* 1620 */     for (byte b = 0; b < this.svcmodEOMAvailVctM.size(); b++) {
/* 1621 */       EntityItem entityItem = this.svcmodEOMAvailVctM.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1626 */       checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem, "WITHDRAWDATE", getCheck_W_W_E(paramString));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1631 */       checkPlannedAvailForCtryExists(entityItem, this.svcmdlPlaAvailCtryTblA.keySet(), getCheck_W_W_E(paramString));
/*      */       
/* 1633 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 1634 */       addDebug("checkSvcmodEOMAvails " + entityItem.getKey() + " availAnntypeFlag " + str);
/* 1635 */       if (str == null) {
/* 1636 */         str = "RFA";
/*      */       }
/*      */       
/* 1639 */       if ("RFA".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1647 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1652 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 1653 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 1654 */           String str1 = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 1655 */           addDebug("checkSvcmodEOMAvails " + entityItem1.getKey() + " anntypeFlag " + str1);
/* 1656 */           if (!"14".equals(str1)) {
/*      */             
/* 1658 */             this.args[0] = getLD_NDN(entityItem);
/* 1659 */             this.args[1] = getLD_NDN(entityItem1);
/* 1660 */             createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */             
/* 1668 */             checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem, "WITHDRAWDATE", getCheck_W_W_E(paramString));
/*      */           } 
/*      */         } 
/* 1671 */         vector.clear();
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
/*      */   protected boolean updateDerivedData(EntityItem paramEntityItem) throws Exception {
/* 1691 */     boolean bool = false;
/*      */     
/* 1693 */     String str = PokUtils.getAttributeValue(paramEntityItem, "WTHDRWEFFCTVDATE", "", "9999-12-31", false);
/* 1694 */     addDebug("updateDerivedData wdDate: " + str + " now: " + getCurrentDate());
/* 1695 */     if (getCurrentDate().compareTo(str) <= 0) {
/* 1696 */       bool = execDerivedData(paramEntityItem);
/*      */     }
/* 1698 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLCRFRWFName() {
/* 1708 */     return "WFLCSVCMODRFR";
/*      */   }
/*      */   
/*      */   protected String getLCFinalWFName() {
/* 1712 */     return "WFLCSVCMODFINAL";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1721 */     return "SVCMOD ABR.";
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
/* 1732 */     return "1.12";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SVCMODABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */