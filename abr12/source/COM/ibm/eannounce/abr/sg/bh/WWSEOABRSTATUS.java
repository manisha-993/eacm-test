/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.sql.SQLException;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
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
/*      */ public class WWSEOABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*   89 */   private EntityList mdlList = null;
/*      */   
/*      */   private static final String SEOORDERCODE_Initial = "10";
/*      */   private static final String SEOORDERCODE_MES = "20";
/*   93 */   private EntityList lseoList = null;
/*      */   private boolean IMGUniqueCoverageChkDone = false;
/*      */   
/*      */   public void dereference() {
/*   97 */     super.dereference();
/*   98 */     if (this.mdlList != null) {
/*   99 */       this.mdlList.dereference();
/*  100 */       this.mdlList = null;
/*      */     } 
/*  102 */     if (this.lseoList != null) {
/*  103 */       this.lseoList.dereference();
/*  104 */       this.lseoList = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  111 */     return true;
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
/*  123 */     if (paramString1.equals("0020")) {
/*  124 */       addDebug("Status already final, use diff ve");
/*  125 */       return "EXRPT3WWSEO4";
/*  126 */     }  if (paramString1.equals("0040") && paramString2.equals("REVIEW")) {
/*  127 */       addDebug("Status already rfr, use diff ve");
/*  128 */       return "EXRPT3WWSEO4";
/*      */     } 
/*  130 */     return this.m_abri.getVEName();
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
/*      */   protected void doAlreadyFinalProcessing(EntityItem paramEntityItem) throws Exception {
/*  145 */     if (doDARULEs()) {
/*  146 */       boolean bool = updateDerivedData(paramEntityItem);
/*  147 */       addDebug("doAlreadyFinalProcessing: " + paramEntityItem.getKey() + " chgsMade " + bool);
/*  148 */       if (bool) {
/*      */         
/*  150 */         Vector<EntityItem> vector = new Vector();
/*  151 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/*  152 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  153 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/*  155 */           if (statusIsFinal(entityItem)) {
/*  156 */             vector.add(entityItem);
/*      */           }
/*      */         } 
/*      */         
/*  160 */         if (vector.size() > 0)
/*      */         {
/*  162 */           EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  163 */           String str1 = getAttributeFlagEnabledValue(entityItem, "SPECBID");
/*  164 */           addDebug("doAlreadyFinalProcessing: " + entityItem.getKey() + " SPECBID: " + str1);
/*      */           
/*  166 */           EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/*  167 */           vector.copyInto((Object[])arrayOfEntityItem);
/*      */           
/*  169 */           String str2 = "EXRPT3LSEO2";
/*  170 */           this.lseoList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/*  171 */                 .getProfile(), str2), arrayOfEntityItem);
/*      */           
/*  173 */           addDebug("doAlreadyFinalProcessing: Extract " + str2 + NEWLINE + PokUtils.outputList(this.lseoList));
/*  174 */           EntityGroup entityGroup1 = this.lseoList.getParentEntityGroup();
/*      */ 
/*      */           
/*  177 */           if ("11457".equals(str1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  182 */             doADSLSEOSectionTwo(filterLSEOs(entityGroup1), str1);
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/*  188 */             doADSLSEOSectionTwo(entityGroup1.getEntityItemsAsArray(), str1);
/*      */           } 
/*      */ 
/*      */           
/*  192 */           arrayOfEntityItem = null;
/*  193 */           vector.clear();
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  199 */         this.args[0] = this.m_elist.getEntityGroup("CATDATA").getLongDescription();
/*  200 */         addResourceMsg("NO_CHGSFOUND", this.args);
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
/*      */   private void doADSLSEOSectionTwo(EntityItem[] paramArrayOfEntityItem, String paramString) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  248 */     if (paramArrayOfEntityItem == null || paramArrayOfEntityItem.length == 0) {
/*  249 */       addDebug("doADSLSEOSectionTwo: entered with no LSEOs");
/*      */       
/*      */       return;
/*      */     } 
/*  253 */     for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/*  254 */       EntityItem entityItem = paramArrayOfEntityItem[b];
/*      */       
/*  256 */       String str = PokUtils.getAttributeValue(entityItem, "LSEOUNPUBDATEMTRGT", "", "9999-12-31", false);
/*  257 */       addDebug("doADSLSEOSectionTwo " + entityItem.getKey() + " wdDate: " + str + " now: " + 
/*  258 */           getCurrentDate() + " SPECBID: " + paramString);
/*  259 */       if (getCurrentDate().compareTo(str) >= 0) {
/*  260 */         addDebug("doADSLSEOSectionTwo skipping " + entityItem.getKey() + " it is withdrawn");
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  265 */         if (statusIsFinal(entityItem)) {
/*  266 */           if ("11457".equals(paramString)) {
/*      */ 
/*      */             
/*  269 */             addDebug("doADSLSEOSectionTwo: lseo.status=final specbid=no");
/*      */             
/*  271 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */           } else {
/*      */             
/*  274 */             addDebug("doADSLSEOSectionTwo: lseo.status=final specbid=yes");
/*      */             
/*  276 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*  281 */         if (statusIsRFR(entityItem)) {
/*  282 */           if ("11457".equals(paramString)) {
/*      */ 
/*      */             
/*  285 */             addDebug("doADSLSEOSectionTwo: lseo.status=rfr specbid=no");
/*      */             
/*  287 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  292 */             addDebug("doADSLSEOSectionTwo: lseo.status=rfr specbid=yes");
/*      */             
/*  294 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
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
/*      */   protected void doAlreadyRFRProcessing(EntityItem paramEntityItem) throws Exception {
/*  316 */     if (doDARULEs()) {
/*  317 */       boolean bool = updateDerivedData(paramEntityItem);
/*  318 */       addDebug("doAlreadyRFRProcessing: " + paramEntityItem.getKey() + " chgsMade " + bool);
/*  319 */       if (bool) {
/*      */         
/*  321 */         Vector<EntityItem> vector1 = new Vector();
/*  322 */         EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  323 */         String str = getAttributeFlagEnabledValue(entityItem, "SPECBID");
/*  324 */         addDebug("doAlreadyRFRProcessing: " + entityItem.getKey() + " wwseoSPECBID " + str);
/*  325 */         Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "LSEO");
/*  326 */         for (byte b = 0; b < vector2.size(); b++) {
/*  327 */           EntityItem entityItem1 = vector2.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  336 */           if (statusIsRFR(entityItem1))
/*      */           {
/*      */             
/*  339 */             if (str.equals("11457")) {
/*  340 */               addDebug("doAlreadyRFRProcessing: specbid=no ");
/*      */               
/*  342 */               Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem1, "LSEOAVAIL", "AVAIL");
/*  343 */               for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  344 */                 EntityItem entityItem2 = vector.elementAt(b1);
/*      */                 
/*  346 */                 String str1 = PokUtils.getAttributeFlagValue(entityItem2, "AVAILANNTYPE");
/*  347 */                 if (str1 == null) {
/*  348 */                   str1 = "RFA";
/*      */                 }
/*  350 */                 addDebug("doAlreadyRFRProcessing: " + entityItem2.getKey() + " availanntype " + str1);
/*      */ 
/*      */                 
/*  353 */                 if (statusIsRFRorFinal(entityItem2)) {
/*      */                   
/*  355 */                   vector1.add(entityItem1);
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*  360 */               vector.clear();
/*      */             }
/*      */             else {
/*      */               
/*  364 */               addDebug("doAlreadyRFRProcessing: specbid=yes ");
/*  365 */               vector1.add(entityItem1);
/*      */             } 
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  372 */         EntityItem[] arrayOfEntityItem = null;
/*  373 */         if (vector1.size() > 0) {
/*  374 */           arrayOfEntityItem = new EntityItem[vector1.size()];
/*  375 */           vector1.copyInto((Object[])arrayOfEntityItem);
/*  376 */           doADSLSEOSectionTwo(arrayOfEntityItem, str);
/*  377 */           arrayOfEntityItem = null;
/*      */         } 
/*      */         
/*  380 */         vector1.clear();
/*      */       }
/*      */       else {
/*      */         
/*  384 */         this.args[0] = this.m_elist.getEntityGroup("CATDATA").getLongDescription();
/*  385 */         addResourceMsg("NO_CHGSFOUND", this.args);
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  461 */     if (this.mdlList == null) {
/*      */       
/*      */       try {
/*  464 */         getModelVE(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*  465 */       } catch (Exception exception) {
/*  466 */         exception.printStackTrace();
/*  467 */         addDebug("Exception getting model ve " + exception.getMessage());
/*  468 */         throw new MiddlewareException(exception.getMessage());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  476 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", getRFRQueuedValue("COMPATGENABR"));
/*      */     
/*  478 */     if (doR10processing()) {
/*      */       
/*  480 */       Vector<EntityItem> vector1 = new Vector();
/*  481 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  482 */       String str = getAttributeFlagEnabledValue(entityItem, "SPECBID");
/*  483 */       addDebug("completeNowR4RProcessing: " + entityItem.getKey() + " wwseoSPECBID " + str);
/*  484 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "LSEO");
/*  485 */       for (byte b = 0; b < vector2.size(); b++) {
/*  486 */         EntityItem entityItem1 = vector2.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  495 */         if (statusIsRFR(entityItem1))
/*      */         {
/*  497 */           if (str.equals("11457")) {
/*  498 */             addDebug("completeNowR4RProcessing: specbid=no ");
/*      */             
/*  500 */             Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem1, "LSEOAVAIL", "AVAIL");
/*  501 */             for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  502 */               EntityItem entityItem2 = vector.elementAt(b1);
/*      */               
/*  504 */               String str1 = PokUtils.getAttributeFlagValue(entityItem2, "AVAILANNTYPE");
/*  505 */               if (str1 == null) {
/*  506 */                 str1 = "RFA";
/*      */               }
/*  508 */               addDebug("completeNowR4RProcessing: " + entityItem2.getKey() + " availanntype " + str1);
/*      */ 
/*      */               
/*  511 */               if (statusIsRFRorFinal(entityItem2)) {
/*      */ 
/*      */ 
/*      */                 
/*  515 */                 vector1.add(entityItem1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  535 */             vector.clear();
/*      */           }
/*      */           else {
/*      */             
/*  539 */             addDebug("completeNowR4RProcessing: specbid=yes ");
/*  540 */             vector1.add(entityItem1);
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  547 */       EntityItem[] arrayOfEntityItem = null;
/*  548 */       if (vector1.size() > 0) {
/*  549 */         arrayOfEntityItem = new EntityItem[vector1.size()];
/*  550 */         vector1.copyInto((Object[])arrayOfEntityItem);
/*  551 */         doLSEOSectionTwo(arrayOfEntityItem, str);
/*  552 */         arrayOfEntityItem = null;
/*      */       } 
/*      */       
/*  555 */       vector1.clear();
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
/*  574 */     boolean bool = false;
/*      */ 
/*      */     
/*  577 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/*  578 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  579 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  580 */       if (statusIsRFRorFinal(entityItem)) {
/*  581 */         if (this.mdlList == null) {
/*      */           
/*      */           try {
/*  584 */             getModelVE(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*  585 */           } catch (Exception exception) {
/*  586 */             exception.printStackTrace();
/*  587 */             addDebug("Exception getting model ve " + exception.getMessage());
/*  588 */             throw new MiddlewareException(exception.getMessage());
/*      */           } 
/*      */         }
/*      */         
/*  592 */         EntityItem entityItem1 = this.mdlList.getEntityGroup("MODEL").getEntityItem(0);
/*      */         
/*  594 */         String str = PokUtils.getAttributeValue(entityItem1, "WTHDRWEFFCTVDATE", "", "9999-12-31", false);
/*  595 */         addDebug("updateDerivedData " + entityItem1.getKey() + " wdDate: " + str + " now: " + getCurrentDate());
/*  596 */         if (getCurrentDate().compareTo(str) <= 0) {
/*  597 */           bool = execDerivedData(paramEntityItem);
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/*  602 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLCRFRWFName() {
/*  608 */     return "WFLCWWSEORFR"; } protected String getLCFinalWFName() {
/*  609 */     return "WFLCWWSEOFINAL";
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
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  635 */     if (this.mdlList == null) {
/*      */       
/*      */       try {
/*  638 */         getModelVE(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*  639 */       } catch (Exception exception) {
/*  640 */         exception.printStackTrace();
/*  641 */         addDebug("Exception getting model ve " + exception.getMessage());
/*  642 */         throw new MiddlewareException(exception.getMessage());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  650 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", getQueuedValue("COMPATGENABR"));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  655 */     Vector<EntityItem> vector = new Vector();
/*  656 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/*  657 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  658 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  660 */       if (statusIsFinal(entityItem)) {
/*  661 */         vector.add(entityItem);
/*      */       }
/*      */     } 
/*      */     
/*  665 */     if (vector.size() > 0) {
/*      */       
/*  667 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  668 */       String str1 = getAttributeFlagEnabledValue(entityItem, "SPECBID");
/*  669 */       addDebug("nowfinal: " + entityItem.getKey() + " SPECBID: " + str1);
/*      */       
/*  671 */       EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/*  672 */       vector.copyInto((Object[])arrayOfEntityItem);
/*      */       
/*  674 */       String str2 = "EXRPT3LSEO2";
/*  675 */       this.lseoList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/*  676 */             .getProfile(), str2), arrayOfEntityItem);
/*      */       
/*  678 */       addDebug("nowfinal: Extract " + str2 + NEWLINE + PokUtils.outputList(this.lseoList));
/*  679 */       EntityGroup entityGroup1 = this.lseoList.getParentEntityGroup();
/*      */ 
/*      */       
/*  682 */       if ("11457".equals(str1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  687 */         doLSEOSectionTwo(filterLSEOs(entityGroup1), str1);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  693 */         doLSEOSectionTwo(entityGroup1.getEntityItemsAsArray(), str1);
/*      */       } 
/*      */ 
/*      */       
/*  697 */       arrayOfEntityItem = null;
/*  698 */       vector.clear();
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
/*      */   private EntityItem[] filterLSEOs(EntityGroup paramEntityGroup) {
/*  717 */     Vector<EntityItem> vector = new Vector();
/*  718 */     EntityItem[] arrayOfEntityItem = null;
/*      */     
/*  720 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  721 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  722 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "LSEOAVAIL", "AVAIL");
/*  723 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "STATUS", "0020");
/*  724 */       addDebug("filterLSEOs: " + entityItem.getKey() + " availVct " + vector1.size() + " finalAvailVct " + vector2
/*  725 */           .size());
/*  726 */       if (vector2.size() > 0)
/*      */       {
/*  728 */         vector.add(entityItem);
/*      */       }
/*  730 */       vector1.clear();
/*  731 */       vector2.clear();
/*      */     } 
/*      */ 
/*      */     
/*  735 */     if (vector.size() > 0) {
/*  736 */       arrayOfEntityItem = new EntityItem[vector.size()];
/*  737 */       vector.copyInto((Object[])arrayOfEntityItem);
/*      */     } 
/*      */     
/*  740 */     return arrayOfEntityItem;
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  921 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*      */ 
/*      */     
/*  924 */     getModelVE(paramEntityItem);
/*      */     
/*  926 */     EntityGroup entityGroup1 = this.mdlList.getEntityGroup("MODEL");
/*      */ 
/*      */     
/*  929 */     EntityGroup entityGroup2 = this.mdlList.getEntityGroup("MODELWWSEO");
/*  930 */     if (entityGroup2.getEntityItemCount() != 1) {
/*      */       
/*  932 */       if (entityGroup2.getEntityItemCount() == 0) {
/*  933 */         this.args[0] = entityGroup1.getLongDescription();
/*  934 */         createMessage(getCheck_RW_RE_RE(paramString), "REQUIRES_ONE_PARENT_ERR", this.args);
/*      */       } else {
/*      */         
/*  937 */         for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/*  938 */           EntityItem entityItem1 = entityGroup2.getEntityItem(b);
/*  939 */           EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/*  940 */           this.args[0] = getLD_NDN(entityItem2);
/*  941 */           createMessage(getCheck_RW_RE_RE(paramString), "REQUIRES_ONE_PARENT_ERR", this.args);
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  946 */       EntityItem entityItem = entityGroup1.getEntityItem(0);
/*      */ 
/*      */       
/*  949 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */       
/*  951 */       String str1 = getAttributeFlagEnabledValue(entityItem, "COFCAT");
/*  952 */       addDebug(entityItem.getKey() + " COFCAT: " + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  964 */       if ("100".equals(str1)) {
/*  965 */         doHardwareChecks(paramEntityItem, entityItem, paramString);
/*  966 */       } else if ("101".equals(str1)) {
/*  967 */         doSoftwareChecks(paramEntityItem, paramString);
/*  968 */       } else if ("102".equals(str1)) {
/*  969 */         doServiceChecks(paramEntityItem, entityItem, paramString);
/*      */       } 
/*      */       
/*  972 */       int i = doCheck_N_W_E(paramString);
/*      */       
/*  974 */       String str2 = getAttributeFlagEnabledValue(paramEntityItem, "SEOORDERCODE");
/*  975 */       Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOPRODSTRUCT", "PRODSTRUCT");
/*  976 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOLSEO", "LSEO");
/*  977 */       addDebug(paramEntityItem.getKey() + " SEOORDERCODE: " + str2 + " wwseo-ps psvct " + vector.size() + " lseoVct " + vector1.size());
/*      */ 
/*      */       
/*  980 */       if ("20".equals(str2)) {
/*  981 */         if (-1 != i) {
/*  982 */           if (!this.IMGUniqueCoverageChkDone) {
/*  983 */             addHeading(3, this.m_elist.getEntityGroup("IMG").getLongDescription() + " Unique Coverage Checks:");
/*  984 */             for (byte b = 0; b < vector1.size(); b++) {
/*  985 */               EntityItem entityItem1 = vector1.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  991 */               checkUniqueCoverage(paramEntityItem, "WWSEOIMG", "IMG", entityItem1, "LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT", i, false);
/*      */             } 
/*      */             
/*  994 */             this.IMGUniqueCoverageChkDone = true;
/*      */           } 
/*      */         } else {
/*  997 */           addDebug("img coverage checks are bypassed because status is " + paramString);
/*      */         } 
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
/* 1010 */       vector1.clear();
/* 1011 */       vector.clear();
/*      */     } 
/*      */ 
/*      */     
/* 1015 */     if (getReturnCode() != 0) {
/* 1016 */       this.mdlList.dereference();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getModelVE(EntityItem paramEntityItem) throws Exception {
/* 1026 */     String str = "EXRPT3WWSEO2";
/*      */     
/* 1028 */     if ("EXRPT3WWSEO4".equals(this.m_elist.getParentActionItem().getActionItemKey()))
/*      */     {
/* 1030 */       str = "EXRPT3WWSEO5";
/*      */     }
/* 1032 */     this.mdlList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 1033 */           .getProfile(), str), new EntityItem[] { new EntityItem(null, this.m_elist
/* 1034 */             .getProfile(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID()) });
/* 1035 */     addDebug("getModelVE:: Extract " + str + NEWLINE + PokUtils.outputList(this.mdlList));
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
/*      */   private void doHardwareChecks(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString) throws MiddlewareException, SQLException {
/* 1078 */     addHeading(3, "Hardware Model Checks:");
/* 1079 */     addDebug("doHardwareChecks: entered");
/*      */     
/* 1081 */     String str = getAttributeFlagEnabledValue(paramEntityItem2, "COFSUBCAT");
/* 1082 */     addDebug(paramEntityItem2.getKey() + " COFSUBCAT: " + str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1088 */     int i = getCount("WWSEOPRODSTRUCT");
/* 1089 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 1090 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem1, "WWSEOPRODSTRUCT", "PRODSTRUCT");
/* 1091 */     if (i == 0) {
/*      */       
/* 1093 */       this.args[0] = entityGroup.getLongDescription();
/* 1094 */       createMessage(3, "MINIMUM_ERR", this.args);
/*      */     } else {
/* 1096 */       addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */       
/* 1098 */       checkSystemMaxAndConfqty(paramEntityItem1, "WWSEOPRODSTRUCT", 4);
/*      */ 
/*      */       
/* 1101 */       Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem1, "WWSEOLSEO", "LSEO");
/* 1102 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "STATUS", "0020");
/* 1103 */       addDebug("doHardwareChecks: lseovct " + vector1.size() + " finallseoVct " + vector2.size());
/* 1104 */       for (byte b = 0; b < vector.size(); b++) {
/* 1105 */         EntityItem entityItem = vector.elementAt(b);
/*      */         
/* 1107 */         checkStatusVsDQ(entityItem, "STATUS", paramEntityItem1, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1122 */         checkLseoPSLOAvail(entityItem, "PRODSTRUCTCATLGOR", "OOFAVAIL", vector2, 4, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1130 */         checkWDFeatures(entityItem, "FEATURE", 4);
/*      */       } 
/*      */       
/* 1133 */       vector2.clear();
/* 1134 */       vector1.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1140 */     i = getCount("WWSEOSWPRODSTRUCT");
/* 1141 */     if (i != 0) {
/* 1142 */       entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 1143 */       this.args[0] = "Hardware";
/* 1144 */       this.args[1] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 1145 */       this.args[2] = entityGroup.getLongDescription();
/* 1146 */       createMessage(4, "PSLINK_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1151 */     if ("126".equals(str) && domainInRuleList(paramEntityItem1, "XCC_LIST2")) {
/* 1152 */       doHwXCCDomainChecks(paramEntityItem1, paramString);
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
/*      */   private void doHwXCCDomainChecks(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 1244 */     addHeading(3, "Hardware Model XCC Checks:");
/* 1245 */     addDebug("doHwXCCDomainChecks: entered");
/* 1246 */     int i = getCheck_RW_RE_RE(paramString);
/*      */ 
/*      */ 
/*      */     
/* 1250 */     checkExactlyOne("WWSEODERIVEDDATA", "DERIVEDDATA", i);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1255 */     int j = getCount("SEOCGSEO");
/* 1256 */     if (j == 0) {
/* 1257 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("SEOCG");
/*      */ 
/*      */       
/* 1260 */       this.args[0] = entityGroup.getLongDescription();
/* 1261 */       createMessage(i, "MUST_BE_IN_ATLEAST_ONE_ERR", this.args);
/*      */     } 
/*      */ 
/*      */     
/* 1265 */     String str1 = getAttributeFlagEnabledValue(paramEntityItem, "SEOORDERCODE");
/* 1266 */     addDebug(paramEntityItem.getKey() + " SEOORDERCODE: " + str1);
/*      */     
/* 1268 */     if (str1 != null && !str1.equals("10"))
/*      */     {
/*      */       
/* 1271 */       checkExactlyOne("WWSEOWEIGHTNDIMN", "WEIGHTNDIMN", 3);
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
/* 1284 */     checkCount("FEATUREPLANAR", "MODELPLANAR", "PLANAR", i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1294 */     checkExists("FEATUREMECHPKG", "MODELMECHPKG", "MECHPKG", i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1308 */     checkBays(getCheck_W_E_E(paramString));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1333 */     checkSlots(getCheck_W_E_E(paramString));
/*      */     
/* 1335 */     i = doCheck_N_RW_RE(paramString);
/*      */     
/* 1337 */     if (-1 == i) {
/* 1338 */       addDebug("doHwXCCDomainChecks: checks are bypassed because status is " + paramString);
/*      */       return;
/*      */     } 
/* 1341 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, "SPECBID");
/*      */     
/* 1343 */     if (str2.equals("11457")) {
/* 1344 */       addDebug("doHwXCCDomainChecks: " + paramEntityItem.getKey() + " specbid=no");
/*      */       
/* 1346 */       addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Unique Coverage Checks:");
/*      */       
/* 1348 */       int k = doCheck_N_W_E(paramString);
/* 1349 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOLSEO", "LSEO");
/* 1350 */       for (byte b = 0; b < vector.size(); b++) {
/* 1351 */         EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1367 */         checkUniqueCoverage(paramEntityItem, "WWSEOMM", "MM", entityItem, "LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT", k, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1374 */         checkUniqueCoverage(paramEntityItem, "WWSEOFB", "FB", entityItem, "LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT", k, false);
/*      */       } 
/*      */       
/* 1377 */       vector.clear();
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
/*      */   private void checkExactlyOne(String paramString1, String paramString2, int paramInt) throws MiddlewareException, SQLException {
/* 1394 */     int i = getCount(paramString1);
/* 1395 */     if (i != 1) {
/* 1396 */       EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString2);
/*      */       
/* 1398 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1399 */         this.args[0] = entityGroup.getLongDescription();
/* 1400 */         createMessage(paramInt, "NEED_ONLY_ONE_ERR", this.args);
/*      */       } else {
/* 1402 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1403 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1404 */           this.args[0] = getLD_NDN(entityItem);
/* 1405 */           createMessage(paramInt, "NEED_ONLY_ONE_ERR", this.args);
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
/*      */   private void doSoftwareChecks(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 1451 */     addHeading(3, "Software Model Checks:");
/* 1452 */     addDebug("doSoftwareChecks: entered");
/* 1453 */     int i = getCheck_W_E_E(paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1458 */     int j = getCount("WWSEOSWPRODSTRUCT");
/* 1459 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 1460 */     if (j == 0) {
/*      */       
/* 1462 */       this.args[0] = entityGroup.getLongDescription();
/* 1463 */       createMessage(3, "MINIMUM_ERR", this.args);
/*      */     } else {
/* 1465 */       addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */       
/* 1467 */       Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOLSEO", "LSEO");
/* 1468 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "STATUS", "0020");
/* 1469 */       addDebug("doSoftwareChecks: lseovct " + vector1.size() + " finallseoVct " + vector2.size());
/* 1470 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1471 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */         
/* 1473 */         checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1490 */         checkLseoPSLOAvail(entityItem, "SWPRODSTRCATLGOR", "SWPRODSTRUCTAVAIL", vector2, 4, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1498 */         checkWDFeatures(entityItem, "SWFEATURE", 4);
/*      */       } 
/* 1500 */       vector2.clear();
/* 1501 */       vector1.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1507 */     j = getCount("WWSEOPRODSTRUCT");
/* 1508 */     if (j != 0) {
/* 1509 */       entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 1510 */       this.args[0] = "Software";
/* 1511 */       this.args[1] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 1512 */       this.args[2] = entityGroup.getLongDescription();
/* 1513 */       createMessage(4, "PSLINK_ERR", this.args);
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
/*      */   private void doServiceChecks(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString) throws MiddlewareException, SQLException {
/* 1552 */     addHeading(3, "Service Model Checks:");
/* 1553 */     addDebug("doServiceChecks: entered");
/*      */     
/* 1555 */     String str1 = getAttributeFlagEnabledValue(paramEntityItem1, "SPECBID");
/* 1556 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem2, "SPECBID");
/*      */ 
/*      */     
/* 1559 */     addDebug(paramEntityItem2.getKey() + " SPECBID: " + str2 + " " + paramEntityItem1.getKey() + " wwseoSPECBID " + str1);
/*      */     
/* 1561 */     if (str2 != null && !str2.equals(str1)) {
/*      */       
/* 1563 */       this.args[0] = getLD_Value(paramEntityItem1, "SPECBID");
/* 1564 */       this.args[1] = getLD_NDN(paramEntityItem2);
/* 1565 */       this.args[2] = getLD_Value(paramEntityItem2, "SPECBID");
/* 1566 */       createMessage(4, "NO_SPECBID_MATCH", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1571 */     int i = getCount("SEOCGSEO");
/* 1572 */     if (i != 0) {
/* 1573 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("SEOCG");
/*      */       
/* 1575 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1576 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1577 */         this.args[0] = getLD_NDN(entityItem);
/* 1578 */         createMessage(4, "MUST_NOT_BE_IN_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1585 */     i = getCount("WWSEOPRODSTRUCT");
/* 1586 */     if (i != 0) {
/* 1587 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 1588 */       this.args[0] = "Service";
/* 1589 */       this.args[1] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 1590 */       this.args[2] = entityGroup.getLongDescription();
/* 1591 */       createMessage(4, "PSLINK_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1596 */     i = getCount("WWSEOSWPRODSTRUCT");
/* 1597 */     if (i != 0) {
/* 1598 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 1599 */       this.args[0] = "Service";
/* 1600 */       this.args[1] = this.m_elist.getParentEntityGroup().getLongDescription();
/* 1601 */       this.args[2] = entityGroup.getLongDescription();
/* 1602 */       createMessage(4, "PSLINK_ERR", this.args);
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
/*      */   private void checkWDFeatures(EntityItem paramEntityItem, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 1614 */     addDebug("checkWDFeatures checking entity: " + paramEntityItem.getKey());
/* 1615 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 1616 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 1617 */       if (entityItem.getEntityType().equals(paramString)) {
/* 1618 */         String str = PokUtils.getAttributeValue(entityItem, "WITHDRAWDATEEFF_T", ", ", "", false);
/* 1619 */         addDebug("checkWDFeatures checking " + entityItem.getKey() + " WITHDRAWDATEEFF_T: " + str);
/* 1620 */         if (str.length() > 0 && str.compareTo(getCurrentDate()) <= 0) {
/*      */ 
/*      */           
/* 1623 */           this.args[0] = getLD_NDN(paramEntityItem) + " " + getLD_NDN(entityItem);
/* 1624 */           this.args[1] = getLD_Value(entityItem, "WITHDRAWDATEEFF_T");
/* 1625 */           createMessage(paramInt, "WITHDRAWN_ERR", this.args);
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
/*      */   public String getDescription() {
/* 1639 */     return "WWSEO ABR.";
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
/* 1651 */     return "1.21";
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
/*      */   private int getCount(EntityList paramEntityList, String paramString) throws MiddlewareException {
/* 1669 */     int i = 0;
/*      */     
/* 1671 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString);
/* 1672 */     if (entityGroup == null) {
/* 1673 */       throw new MiddlewareException(paramString + " is missing from extract");
/*      */     }
/* 1675 */     if (entityGroup.getEntityItemCount() > 0) {
/* 1676 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1677 */         int j = 1;
/* 1678 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1679 */         EANAttribute eANAttribute = entityItem.getAttribute("QTY");
/* 1680 */         if (eANAttribute != null) {
/* 1681 */           j = Integer.parseInt(eANAttribute.get().toString());
/*      */         }
/* 1683 */         i += j;
/* 1684 */         addDebug("getCount " + entityItem.getKey() + " qty " + j);
/*      */       } 
/*      */     }
/*      */     
/* 1688 */     addDebug("getCount Total count found for " + paramString + " = " + i);
/* 1689 */     return i;
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
/*      */   private void checkCount(String paramString1, String paramString2, String paramString3, int paramInt) throws MiddlewareException {
/* 1706 */     addDebug("checkCount entered for " + paramString1 + " " + paramString2 + " " + paramString3);
/* 1707 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString3);
/* 1708 */     int i = getCount(this.m_elist, paramString1);
/* 1709 */     int j = getCount(this.mdlList, paramString2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1722 */     if (i + j == 0) {
/*      */       
/* 1724 */       this.args[0] = entityGroup.getLongDescription();
/* 1725 */       createMessage(paramInt, "MINIMUM_ERR", this.args);
/* 1726 */     } else if (i > 0) {
/* 1727 */       int k = 0;
/*      */       
/* 1729 */       EntityItem entityItem = this.m_elist.getEntityGroup(paramString1).getEntityItem(0);
/* 1730 */       for (byte b = 0; b < entityItem.getUpLinkCount(); b++) {
/*      */         
/* 1732 */         EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b);
/* 1733 */         if (entityItem1.getEntityType().equals("FEATURE")) {
/*      */           
/* 1735 */           addDebug("checkCount " + entityItem.getKey() + " uplink[" + b + "]: " + entityItem1.getKey());
/* 1736 */           for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/*      */             
/* 1738 */             EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/* 1739 */             if (entityItem2.getEntityType().equals("PRODSTRUCT")) {
/* 1740 */               addDebug("checkCount " + entityItem1.getKey() + " dnlink[" + b1 + "]: " + entityItem2.getKey());
/*      */               
/* 1742 */               for (byte b2 = 0; b2 < entityItem2.getUpLinkCount(); b2++) {
/*      */                 
/* 1744 */                 EntityItem entityItem3 = (EntityItem)entityItem2.getUpLink(b2);
/* 1745 */                 if (entityItem3.getEntityType().equals("WWSEOPRODSTRUCT")) {
/*      */                   
/* 1747 */                   addDebug("checkCount " + entityItem2.getKey() + " uplink[" + b2 + "]: " + entityItem3.getKey());
/* 1748 */                   String str = PokUtils.getAttributeValue(entityItem3, "CONFQTY", ", ", "1", false);
/* 1749 */                   addDebug("checkCount " + entityItem3.getKey() + " CONFQTY: " + str);
/* 1750 */                   int m = Integer.parseInt(str);
/* 1751 */                   k += m;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1758 */       addDebug("checkCount totalConfQty: " + k);
/* 1759 */       if (k == 0) {
/*      */         
/* 1761 */         this.args[0] = entityGroup.getLongDescription();
/* 1762 */         createMessage(paramInt, "MINIMUM_ERR", this.args);
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
/*      */   private void checkExists(String paramString1, String paramString2, String paramString3, int paramInt) throws MiddlewareException {
/* 1781 */     addDebug("checkExists entered for " + paramString1 + " " + paramString2 + " " + paramString3);
/* 1782 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString3);
/* 1783 */     int i = getCount(this.m_elist, paramString1);
/* 1784 */     if (i > 0) {
/* 1785 */       addDebug("checkExists fccnt " + i);
/*      */     } else {
/* 1787 */       int j = getCount(this.mdlList, paramString2);
/* 1788 */       if (j > 0) {
/* 1789 */         addDebug("checkExists mdlcnt " + j);
/*      */       } else {
/*      */         
/* 1792 */         this.args[0] = entityGroup.getLongDescription();
/* 1793 */         createMessage(paramInt, "MINIMUM_ERR", this.args);
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
/*      */   private void checkBays(int paramInt) {
/* 1842 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 1843 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1848 */     addDebug("checkBays: entered");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1853 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("MECHPKG"); byte b1;
/* 1854 */     for (b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++) {
/* 1855 */       EntityItem entityItem = entityGroup1.getEntityItem(b1);
/* 1856 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem, "MECHPKGBAY", "BAY");
/* 1857 */       addDebug("checkBays: FEATURE " + entityItem.getKey() + " bayVector.size " + vector.size());
/*      */       
/* 1859 */       Hashtable<?, ?> hashtable = checkBays(vector, paramInt);
/* 1860 */       hashtable1.putAll(hashtable);
/* 1861 */       hashtable.clear();
/*      */     } 
/*      */     
/* 1864 */     entityGroup1 = this.mdlList.getEntityGroup("MECHPKG");
/* 1865 */     for (b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++) {
/* 1866 */       EntityItem entityItem = entityGroup1.getEntityItem(b1);
/* 1867 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem, "MECHPKGBAY", "BAY");
/* 1868 */       addDebug("checkBays: MODEL " + entityItem.getKey() + " bayVector.size " + vector.size());
/*      */       
/* 1870 */       Hashtable<?, ?> hashtable = checkBays(vector, paramInt);
/* 1871 */       hashtable1.putAll(hashtable);
/* 1872 */       hashtable.clear();
/*      */     } 
/*      */     
/* 1875 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("BAY");
/* 1876 */     EntityGroup entityGroup3 = this.m_elist.getEntityGroup("BAYSAVAIL");
/* 1877 */     addHeading(4, entityGroup1.getLongDescription() + " " + entityGroup2.getLongDescription() + " and " + entityGroup3
/* 1878 */         .getLongDescription() + " Checks:");
/*      */     
/* 1880 */     for (byte b2 = 0; b2 < entityGroup3.getEntityItemCount(); b2++) {
/*      */       
/* 1882 */       EntityItem entityItem = entityGroup3.getEntityItem(b2);
/* 1883 */       String str1 = PokUtils.getAttributeValue(entityItem, "BAYAVAILTYPE", ", ", "", false);
/* 1884 */       String str2 = getAttributeFlagEnabledValue(entityItem, "BAYAVAILTYPE");
/* 1885 */       String str3 = PokUtils.getAttributeValue(entityItem, "ACCSS", ", ", "", false);
/* 1886 */       String str4 = getAttributeFlagEnabledValue(entityItem, "ACCSS");
/* 1887 */       String str5 = PokUtils.getAttributeValue(entityItem, "BAYFF", ", ", "", false);
/* 1888 */       String str6 = getAttributeFlagEnabledValue(entityItem, "BAYFF");
/* 1889 */       String str7 = str1 + ", " + str3 + ", " + str5;
/* 1890 */       String str8 = str2 + "," + str4 + "," + str6;
/* 1891 */       addDebug("checkBays: " + entityItem.getKey() + " bayInfo: " + str7 + "[" + str8 + "]");
/* 1892 */       if (hashtable2.containsKey(str8)) {
/*      */ 
/*      */         
/* 1895 */         this.args[0] = entityGroup3.getLongDescription();
/* 1896 */         this.args[1] = getLD_Value(entityItem, "BAYAVAILTYPE");
/* 1897 */         this.args[2] = getLD_Value(entityItem, "ACCSS");
/* 1898 */         this.args[3] = getLD_Value(entityItem, "BAYFF");
/* 1899 */         createMessage(paramInt, "MUST_HAVE_UNIQUE_ERR", this.args);
/*      */       } else {
/* 1901 */         hashtable2.put(str8, entityItem);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1906 */     Iterator<String> iterator = hashtable1.keySet().iterator();
/* 1907 */     while (iterator.hasNext()) {
/*      */       
/* 1909 */       String str = iterator.next();
/*      */ 
/*      */ 
/*      */       
/* 1913 */       if (!hashtable2.containsKey(str)) {
/*      */         
/* 1915 */         EntityItem entityItem = (EntityItem)hashtable1.get(str);
/*      */ 
/*      */         
/* 1918 */         this.args[0] = entityGroup2.getLongDescription();
/* 1919 */         this.args[1] = getLD_Value(entityItem, "BAYTYPE");
/* 1920 */         this.args[2] = getLD_Value(entityItem, "ACCSS");
/* 1921 */         this.args[3] = getLD_Value(entityItem, "BAYFF");
/* 1922 */         this.args[4] = entityGroup3.getLongDescription();
/* 1923 */         createMessage(paramInt, "CORRESPONDING_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1928 */     iterator = hashtable2.keySet().iterator();
/* 1929 */     while (iterator.hasNext()) {
/*      */       
/* 1931 */       String str = iterator.next();
/*      */ 
/*      */       
/* 1934 */       if (!hashtable1.containsKey(str)) {
/*      */         
/* 1936 */         EntityItem entityItem = (EntityItem)hashtable2.get(str);
/*      */ 
/*      */         
/* 1939 */         this.args[0] = entityGroup3.getLongDescription();
/* 1940 */         this.args[1] = getLD_Value(entityItem, "BAYAVAILTYPE");
/* 1941 */         this.args[2] = getLD_Value(entityItem, "ACCSS");
/* 1942 */         this.args[3] = getLD_Value(entityItem, "BAYFF");
/* 1943 */         this.args[4] = entityGroup2.getLongDescription();
/* 1944 */         createMessage(paramInt, "CORRESPONDING_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */     
/* 1948 */     hashtable1.clear();
/* 1949 */     hashtable1 = null;
/* 1950 */     hashtable2.clear();
/* 1951 */     hashtable2 = null;
/*      */   }
/*      */   
/*      */   private Hashtable checkBays(Vector<EntityItem> paramVector, int paramInt) {
/* 1955 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1956 */     for (byte b = 0; b < paramVector.size(); b++) {
/*      */       
/* 1958 */       EntityItem entityItem = paramVector.elementAt(b);
/* 1959 */       String str1 = PokUtils.getAttributeValue(entityItem, "BAYTYPE", ", ", "", false);
/* 1960 */       String str2 = getAttributeFlagEnabledValue(entityItem, "BAYTYPE");
/* 1961 */       String str3 = PokUtils.getAttributeValue(entityItem, "ACCSS", ", ", "", false);
/* 1962 */       String str4 = getAttributeFlagEnabledValue(entityItem, "ACCSS");
/* 1963 */       String str5 = PokUtils.getAttributeValue(entityItem, "BAYFF", ", ", "", false);
/* 1964 */       String str6 = getAttributeFlagEnabledValue(entityItem, "BAYFF");
/* 1965 */       String str7 = str1 + ", " + str3 + ", " + str5;
/* 1966 */       String str8 = str2 + "," + str4 + "," + str6;
/* 1967 */       addDebug("checkBays: " + entityItem.getKey() + " bayInfo: " + str7 + "[" + str8 + "]");
/* 1968 */       if (hashtable.containsKey(str8)) {
/* 1969 */         EntityGroup entityGroup = entityItem.getEntityGroup();
/*      */ 
/*      */         
/* 1972 */         this.args[0] = entityGroup.getLongDescription();
/* 1973 */         this.args[1] = getLD_Value(entityItem, "BAYTYPE");
/* 1974 */         this.args[2] = getLD_Value(entityItem, "ACCSS");
/* 1975 */         this.args[3] = getLD_Value(entityItem, "BAYFF");
/* 1976 */         createMessage(paramInt, "MUST_HAVE_UNIQUE_ERR", this.args);
/*      */       } else {
/* 1978 */         hashtable.put(str8, entityItem);
/*      */       } 
/*      */     } 
/* 1981 */     return hashtable;
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
/*      */   private void checkSlots(int paramInt) {
/* 2013 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 2014 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 2015 */     Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2021 */     String str1 = "0010";
/* 2022 */     String str2 = "0020";
/* 2023 */     String str3 = "0030";
/*      */     
/* 2025 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SLOTSAVAIL");
/* 2026 */     addHeading(4, entityGroup.getLongDescription() + " Checks:");
/*      */ 
/*      */     
/* 2029 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */       
/* 2031 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2032 */       String str4 = PokUtils.getAttributeValue(entityItem, "SLOTTYPE", ", ", "", false);
/* 2033 */       String str5 = getAttributeFlagEnabledValue(entityItem, "SLOTTYPE");
/* 2034 */       String str6 = PokUtils.getAttributeValue(entityItem, "SLOTSZE", ", ", "", false);
/* 2035 */       String str7 = getAttributeFlagEnabledValue(entityItem, "SLOTSZE");
/* 2036 */       String str8 = PokUtils.getAttributeValue(entityItem, "ELEMENTTYPE", ", ", "", false);
/* 2037 */       String str9 = getAttributeFlagEnabledValue(entityItem, "ELEMENTTYPE");
/* 2038 */       addDebug("checkSlots: " + entityItem.getKey() + " slotType: " + str4 + "[" + str5 + "] slotSze: " + str6 + "[" + str7 + "] elementType: " + str8);
/*      */       
/* 2040 */       if (str9 == null || str9.length() == 0) {
/* 2041 */         addDebug("checkSlots: skipping " + entityItem.getKey() + " slotType: " + str4 + " does not have ELEMENTTYPE defined.");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 2048 */       else if (str1.equals(str9)) {
/* 2049 */         if (hashtable1.containsKey(str5 + str7)) {
/* 2050 */           this.args[0] = entityGroup.getLongDescription();
/* 2051 */           this.args[1] = str8;
/* 2052 */           this.args[2] = getLD_Value(entityItem, "SLOTTYPE");
/* 2053 */           this.args[3] = getLD_Value(entityItem, "SLOTSZE");
/* 2054 */           createMessage(paramInt, "MUST_HAVE_UNIQUE_ERR", this.args);
/*      */         } else {
/* 2056 */           hashtable1.put(str5 + str7, entityItem);
/*      */         } 
/* 2058 */       } else if (str2.equals(str9)) {
/* 2059 */         if (hashtable2.containsKey(str5 + str7)) {
/* 2060 */           this.args[0] = entityGroup.getLongDescription();
/* 2061 */           this.args[1] = str8;
/* 2062 */           this.args[2] = getLD_Value(entityItem, "SLOTTYPE");
/* 2063 */           this.args[3] = getLD_Value(entityItem, "SLOTSZE");
/* 2064 */           createMessage(paramInt, "MUST_HAVE_UNIQUE_ERR", this.args);
/*      */         } else {
/* 2066 */           hashtable2.put(str5 + str7, entityItem);
/*      */         } 
/* 2068 */       } else if (str3.equals(str9)) {
/* 2069 */         if (hashtable3.containsKey(str5 + str7)) {
/* 2070 */           this.args[0] = entityGroup.getLongDescription();
/* 2071 */           this.args[1] = str8;
/* 2072 */           this.args[2] = getLD_Value(entityItem, "SLOTTYPE");
/* 2073 */           this.args[3] = getLD_Value(entityItem, "SLOTSZE");
/* 2074 */           createMessage(paramInt, "MUST_HAVE_UNIQUE_ERR", this.args);
/*      */         } else {
/* 2076 */           hashtable3.put(str5 + str7, entityItem);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2081 */     addDebug("slotsavailMemCardSet " + hashtable1);
/* 2082 */     addDebug("slotsavailPlanarSet " + hashtable2);
/* 2083 */     addDebug("slotsavailExpUnitSet " + hashtable3);
/*      */ 
/*      */     
/* 2086 */     checkElementSlots(hashtable1, "MEMORYCARD", "FEATUREMEMORYCARD", "MEMORYCARDSLOT", paramInt);
/*      */ 
/*      */     
/* 2089 */     checkElementSlots(hashtable2, "PLANAR", "FEATUREPLANAR", "PLANARSLOT", paramInt);
/*      */ 
/*      */     
/* 2092 */     checkElementSlots(hashtable3, "EXPDUNIT", "FEATUREEXPDUNIT", "EXPDUNITSLOT", paramInt);
/*      */     
/* 2094 */     hashtable1.clear();
/* 2095 */     hashtable1 = null;
/* 2096 */     hashtable2.clear();
/* 2097 */     hashtable2 = null;
/* 2098 */     hashtable3.clear();
/* 2099 */     hashtable3 = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkElementSlots(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3, int paramInt) {
/* 2110 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 2111 */     HashSet<String> hashSet = new HashSet();
/*      */ 
/*      */ 
/*      */     
/* 2115 */     addDebug("checkElementSlots: entered " + paramString1 + " " + paramString2 + " " + paramString3);
/*      */     
/* 2117 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("SLOT");
/* 2118 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("FEATURE");
/* 2119 */     EntityGroup entityGroup3 = this.m_elist.getEntityGroup(paramString1);
/* 2120 */     EntityGroup entityGroup4 = this.m_elist.getEntityGroup("SLOTSAVAIL");
/* 2121 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityGroup2, paramString2, paramString1);
/*      */     
/* 2123 */     addHeading(4, entityGroup2.getLongDescription() + " " + entityGroup3.getLongDescription() + " " + entityGroup1
/* 2124 */         .getLongDescription() + " Checks:");
/*      */     
/* 2126 */     for (byte b = 0; b < vector.size(); b++) {
/* 2127 */       EntityItem entityItem = vector.get(b);
/* 2128 */       int i = entityItem.getEntityID();
/* 2129 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, paramString3, "SLOT");
/* 2130 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*      */         
/* 2132 */         EntityItem entityItem1 = vector1.get(b1);
/* 2133 */         String str1 = PokUtils.getAttributeValue(entityItem1, "SLOTTYPE", ", ", "", false);
/* 2134 */         String str2 = PokUtils.getAttributeValue(entityItem1, "SLOTSZE", ", ", "", false);
/* 2135 */         String str3 = getAttributeFlagEnabledValue(entityItem1, "SLOTTYPE");
/* 2136 */         String str4 = getAttributeFlagEnabledValue(entityItem1, "SLOTSZE");
/* 2137 */         addDebug("checkElementSlots: " + paramString1 + i + " " + entityItem1.getKey() + " slotType: " + str1 + "[" + str3 + "] slotSze: " + str2 + "[" + str4 + "]");
/*      */ 
/*      */         
/* 2140 */         if (hashSet.contains(i + str3 + str4)) {
/*      */ 
/*      */ 
/*      */           
/* 2144 */           this.args[0] = entityGroup1.getLongDescription();
/* 2145 */           this.args[1] = getLD_Value(entityItem1, "SLOTTYPE");
/* 2146 */           this.args[2] = getLD_Value(entityItem1, "SLOTSZE");
/* 2147 */           this.args[3] = "";
/* 2148 */           createMessage(paramInt, "MUST_HAVE_UNIQUE_ERR", this.args);
/*      */         } else {
/* 2150 */           hashSet.add(i + str3 + str4);
/* 2151 */           hashtable.put(str3 + str4, entityItem1);
/*      */         } 
/*      */       } 
/* 2154 */       vector1.clear();
/* 2155 */       vector1 = null;
/*      */     } 
/* 2157 */     hashSet.clear();
/*      */ 
/*      */ 
/*      */     
/* 2161 */     Iterator<String> iterator = hashtable.keySet().iterator();
/* 2162 */     while (iterator.hasNext()) {
/*      */       
/* 2164 */       String str = iterator.next();
/* 2165 */       if (!paramHashtable.containsKey(str)) {
/*      */         
/* 2167 */         EntityItem entityItem = (EntityItem)hashtable.get(str);
/*      */ 
/*      */         
/* 2170 */         this.args[0] = entityGroup3.getLongDescription();
/* 2171 */         this.args[1] = entityGroup1.getLongDescription();
/* 2172 */         this.args[2] = getLD_Value(entityItem, "SLOTTYPE");
/* 2173 */         this.args[3] = getLD_Value(entityItem, "SLOTSZE");
/* 2174 */         this.args[4] = entityGroup4.getLongDescription();
/* 2175 */         createMessage(paramInt, "CORRESPONDING_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2180 */     iterator = paramHashtable.keySet().iterator();
/* 2181 */     while (iterator.hasNext()) {
/*      */       
/* 2183 */       String str = iterator.next();
/* 2184 */       if (!hashtable.containsKey(str)) {
/*      */         
/* 2186 */         EntityItem entityItem = (EntityItem)paramHashtable.get(str);
/*      */ 
/*      */         
/* 2189 */         this.args[0] = entityGroup4.getLongDescription();
/* 2190 */         this.args[1] = entityGroup3.getLongDescription();
/* 2191 */         this.args[2] = getLD_Value(entityItem, "SLOTTYPE");
/* 2192 */         this.args[3] = getLD_Value(entityItem, "SLOTSZE");
/* 2193 */         this.args[4] = entityGroup1.getLongDescription() + " " + 
/* 2194 */           PokUtils.getAttributeDescription(entityGroup1, "SLOTTYPE", "SLOTTYPE");
/* 2195 */         createMessage(paramInt, "CORRESPONDING_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */     
/* 2199 */     hashtable.clear();
/* 2200 */     hashtable = null;
/* 2201 */     vector.clear();
/* 2202 */     vector = null;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\WWSEOABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */