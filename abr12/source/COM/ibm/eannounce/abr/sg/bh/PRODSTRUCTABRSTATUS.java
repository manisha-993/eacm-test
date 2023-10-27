/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.AttrComparator;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.eannounce.objects.TestPDGII;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.objects.Attribute;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PRODSTRUCTABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*      */   private static final String PS_SRCHACTION_NAME = "SRDPRODSTRUCTV";
/*      */   private static final String ORDERCODE_BOTH = "5955";
/*      */   private static final String ORDERCODE_MES = "5956";
/*      */   private static final String ORDERCODE_INITIAL = "5957";
/*      */   private static final String PRICEDFEATURE_YES = "100";
/*      */   private static final String MAINTPRICE_YES = "Yes";
/*      */   private static final String WARRSVCCOVR_WARRANTY = "WSVC02";
/*  137 */   private static final Hashtable ATTR_OF_INTEREST_TBL = new Hashtable<>(); static {
/*  138 */     ATTR_OF_INTEREST_TBL.put("PRODSTRUCTWARR", new String[] { "EFFECTIVEDATE", "ENDDATE", "COUNTRYLIST", "DEFWARR" });
/*      */   }
/*      */   
/*  141 */   private EntityList wwseoList = null;
/*  142 */   private EntityList mtmList = null;
/*  143 */   private EntityList mdlList = null;
/*      */   
/*      */   private boolean isRPQ = false;
/*      */   
/*      */   private boolean isInitialOrderCode = false;
/*      */ 
/*      */   
/*      */   public void dereference() {
/*  151 */     super.dereference();
/*  152 */     if (this.wwseoList != null) {
/*  153 */       this.wwseoList.dereference();
/*  154 */       this.wwseoList = null;
/*      */     } 
/*  156 */     if (this.mtmList != null) {
/*  157 */       this.mtmList.dereference();
/*  158 */       this.mtmList = null;
/*      */     } 
/*  160 */     if (this.mdlList != null) {
/*  161 */       this.mdlList.dereference();
/*  162 */       this.mdlList = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  170 */     return true;
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  275 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FEATURE");
/*  276 */     EntityItem entityItem1 = entityGroup.getEntityItem(0);
/*      */     
/*  278 */     EntityItem entityItem2 = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  279 */     EntityItem entityItem3 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*      */     
/*  281 */     boolean bool = isOldData(entityItem3, "ANNDATE");
/*      */     
/*  283 */     addDebug("nowRFR: " + entityItem3.getKey() + " olddata " + bool);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  290 */     propagateProdstructWarr(entityItem2, entityItem3, false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  295 */     if (!bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  307 */       if (this.isRPQ) {
/*  308 */         addDebug(entityItem1.getKey() + " was an RPQ FCTYPE: " + getAttributeFlagEnabledValue(entityItem1, "FCTYPE"));
/*      */         
/*  310 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
/*      */       } else {
/*  312 */         addDebug(entityItem1.getKey() + " was not an RPQ FCTYPE: " + getAttributeFlagEnabledValue(entityItem1, "FCTYPE"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  318 */         doR4R_R10Processing(entityItem2, "OOFAVAIL");
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  329 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLCRFRWFName() {
/*  339 */     return "WFLCPRODSTRFR"; } protected String getLCFinalWFName() {
/*  340 */     return "WFLCPRODSTFINAL";
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
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  398 */     EntityItem entityItem1 = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  399 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FEATURE");
/*  400 */     EntityItem entityItem2 = entityGroup.getEntityItem(0);
/*      */     
/*  402 */     EntityItem entityItem3 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*      */     
/*  404 */     boolean bool1 = isOldData(entityItem3, "ANNDATE");
/*      */     
/*  406 */     addDebug("nowFinal: " + entityItem3.getKey() + " olddata " + bool1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  412 */     propagateProdstructWarr(entityItem1, entityItem3, true);
/*      */ 
/*      */ 
/*      */     
/*  416 */     if (!bool1) {
/*      */ 
/*      */       
/*  419 */       if (this.isRPQ) {
/*  420 */         addDebug(entityItem2.getKey() + " was an RPQ FCTYPE: " + getAttributeFlagEnabledValue(entityItem2, "FCTYPE"));
/*      */ 
/*      */         
/*  423 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*      */ 
/*      */         
/*  426 */         setFlagValue(this.m_elist.getProfile(), "RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
/*      */       }
/*      */       else {
/*      */         
/*  430 */         addDebug(entityItem2.getKey() + " was NOT an RPQ FCTYPE: " + getAttributeFlagEnabledValue(entityItem2, "FCTYPE"));
/*      */ 
/*      */         
/*  433 */         Vector vector = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "AVAIL");
/*  434 */         Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "STATUS", "0020");
/*  435 */         addDebug("nowFinal:  availVct " + vector.size() + " finalAvailVct " + vector1.size());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  440 */         if (vector1.size() > 0) {
/*  441 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*      */ 
/*      */           
/*  444 */           setFlagValue(this.m_elist.getProfile(), "RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  451 */         for (byte b = 0; b < vector1.size(); b++) {
/*  452 */           EntityItem entityItem = vector1.elementAt(b);
/*  453 */           String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/*  454 */           if (str == null) {
/*  455 */             str = "RFA";
/*      */           }
/*  457 */           addDebug("nowfinal: final " + entityItem.getKey() + " availanntype " + str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  467 */           Vector vector2 = PokUtils.getAllLinkedEntities(vector1, "AVAILANNA", "ANNOUNCEMENT");
/*  468 */           Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector2, "ANNSTATUS", "0020");
/*  469 */           addDebug("nowfinal:  annVct " + vector2.size() + " finalAnnVct " + vector3.size());
/*  470 */           for (byte b1 = 0; b1 < vector3.size(); b1++) {
/*  471 */             EntityItem entityItem4 = vector3.elementAt(b1);
/*  472 */             String str1 = getAttributeFlagEnabledValue(entityItem4, "ANNTYPE");
/*      */ 
/*      */             
/*  475 */             if ("19".equals(str1) && 
/*  476 */               domainInRuleList(entityItem1, "XCC_LIST")) {
/*  477 */               addDebug("nowFinal " + entityItem4.getKey() + " is Final and New and prodstruct.domain in xcc");
/*      */               
/*  479 */               setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", getQueuedValueForItem(entityItem4, "WWPRTABRSTATUS"), entityItem4);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  489 */             if (isQsmANNTYPE(str1)) {
/*  490 */               setFlagValue(this.m_elist.getProfile(), "QSMCREFABRSTATUS", getQueuedValueForItem(entityItem4, "QSMCREFABRSTATUS"), entityItem4);
/*  491 */               setFlagValue(this.m_elist.getProfile(), "QSMFULLABRSTATUS", getQueuedValueForItem(entityItem4, "QSMFULLABRSTATUS"), entityItem4);
/*      */             } 
/*      */           } 
/*  494 */           vector2.clear();
/*  495 */           vector3.clear();
/*      */         } 
/*  497 */         vector.clear();
/*  498 */         vector1.clear();
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  506 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*      */ 
/*      */       
/*  509 */       setFlagValue(this.m_elist.getProfile(), "RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
/*      */     } 
/*      */     
/*  512 */     if (this.isRPQ)
/*      */     {
/*      */       
/*  515 */       if (domainInRuleList(entityItem1, "XCC_LIST")) {
/*      */         
/*  517 */         setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", getQueuedValue("WWPRTABRSTATUS"));
/*      */         
/*  519 */         deriveEXTRACTRPQ(entityItem2);
/*      */       } 
/*      */     }
/*      */     
/*  523 */     boolean bool2 = isQSMRPQ(entityItem2);
/*  524 */     if (bool2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  533 */       addDebug("completeNowFinalProcessing - isQSMRPQ " + bool2);
/*  534 */       setFlagValue(this.m_elist.getProfile(), "QSMRPQCREFABRSTATUS", getQueuedValueForItem(entityItem2, "QSMRPQCREFABRSTATUS"), entityItem2);
/*  535 */       setFlagValue(this.m_elist.getProfile(), "QSMRPQFULLABRSTATUS", getQueuedValueForItem(entityItem2, "QSMRPQFULLABRSTATUS"), entityItem2);
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
/*      */   private void propagateProdstructWarr(EntityItem paramEntityItem1, EntityItem paramEntityItem2, boolean paramBoolean) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  574 */     String str = "DQVEMDLLSEO";
/*  575 */     this.wwseoList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/*  576 */           .getProfile(), str), new EntityItem[] { new EntityItem(null, this.m_elist
/*  577 */             .getProfile(), paramEntityItem2.getEntityType(), paramEntityItem2.getEntityID()) });
/*  578 */     addDebug("propagateProdstructWarr: strnow: " + getCurrentDate() + " Extract " + str + NEWLINE + PokUtils.outputList(this.wwseoList));
/*  579 */     EntityGroup entityGroup = this.wwseoList.getEntityGroup("WWSEO");
/*  580 */     Vector<EntityItem> vector = new Vector();
/*  581 */     if (paramBoolean) {
/*      */       
/*  583 */       if (entityGroup.getEntityItemCount() > 0) {
/*  584 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  585 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*  586 */           String str1 = getAttributeFlagEnabledValue(entityItem, "SEOORDERCODE");
/*  587 */           addDebug(entityItem.getKey() + " SEOORDERCODE: " + str1);
/*      */ 
/*      */ 
/*      */           
/*  591 */           if (statusIsFinal(entityItem)) {
/*  592 */             Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "LSEO");
/*  593 */             for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  594 */               EntityItem entityItem1 = vector1.elementAt(b1);
/*  595 */               String str2 = PokUtils.getAttributeValue(entityItem1, "LSEOUNPUBDATEMTRGT", "", "9999-12-31", false);
/*  596 */               addDebug("propagateProdstructWarr: " + entityItem1.getKey() + " unpubdate " + str2);
/*      */ 
/*      */               
/*  599 */               if (statusIsFinal(entityItem1) && str2.compareTo(getCurrentDate()) > 0)
/*      */               {
/*  601 */                 vector.add(entityItem1);
/*      */               }
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } 
/*      */       }
/*  609 */     } else if (entityGroup.getEntityItemCount() > 0) {
/*  610 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  611 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  612 */         String str1 = getAttributeFlagEnabledValue(entityItem, "SEOORDERCODE");
/*  613 */         addDebug(entityItem.getKey() + " SEOORDERCODE: " + str1);
/*      */ 
/*      */ 
/*      */         
/*  617 */         if (statusIsRFRorFinal(entityItem)) {
/*  618 */           Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "LSEO");
/*  619 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  620 */             EntityItem entityItem1 = vector1.elementAt(b1);
/*  621 */             String str2 = PokUtils.getAttributeValue(entityItem1, "LSEOUNPUBDATEMTRGT", "", "9999-12-31", false);
/*  622 */             addDebug("propagateProdstructWarr: " + entityItem1.getKey() + " unpubdate " + str2);
/*      */ 
/*      */             
/*  625 */             if (statusIsRFR(entityItem1) && str2.compareTo(getCurrentDate()) > 0)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  635 */               vector.add(entityItem1);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  644 */     if (vector.size() > 0) {
/*  645 */       propagateProdstructWarr(paramEntityItem1, vector, paramBoolean);
/*  646 */       vector.clear();
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
/*      */   private void propagateProdstructWarr(EntityItem paramEntityItem, Vector<EntityItem> paramVector, boolean paramBoolean) throws MiddlewareRequestException, MiddlewareException, SQLException {
/*  672 */     boolean bool = false;
/*  673 */     String str = getPrevPassedDTS(paramEntityItem, "PRODSTRUCTABRSTATUS");
/*  674 */     if (str != null)
/*      */     {
/*  676 */       bool = changeOfInterest(paramEntityItem, str, this.m_elist.getProfile().getValOn(), "DQVEPSWARR", ATTR_OF_INTEREST_TBL);
/*      */     }
/*      */     
/*  679 */     if (bool) {
/*  680 */       for (byte b = 0; b < paramVector.size(); b++) {
/*  681 */         EntityItem entityItem = paramVector.elementAt(b);
/*  682 */         if (paramBoolean) {
/*  683 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */         } else {
/*  685 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
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
/*      */   protected String generateString(EntityItem paramEntityItem, String[] paramArrayOfString) {
/*  698 */     if (paramEntityItem.getEntityType().equals("PRODSTRUCTWARR")) {
/*      */       
/*  700 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, "DEFWARR");
/*  701 */       addDebug("generateString: " + paramEntityItem.getKey() + " defwarr " + str);
/*  702 */       if ("Y1".equals(str) || str == null) {
/*  703 */         StringBuffer stringBuffer = new StringBuffer(paramEntityItem.getKey());
/*  704 */         for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  705 */           String str1 = PokUtils.getAttributeValue(paramEntityItem, paramArrayOfString[b], ", ", "", false);
/*  706 */           if ("Y1".equals(str) && paramArrayOfString[b].equals("COUNTRYLIST")) {
/*  707 */             str1 = "DEFWARR";
/*      */           }
/*  709 */           if (paramArrayOfString[b].equals("DEFWARR") && str == null) {
/*  710 */             str1 = "No";
/*      */           }
/*  712 */           stringBuffer.append(":" + str1);
/*      */         } 
/*  714 */         return stringBuffer.toString();
/*      */       } 
/*      */     } 
/*      */     
/*  718 */     return super.generateString(paramEntityItem, paramArrayOfString);
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
/*      */   private void setBulkMesIndc(EntityItem paramEntityItem) {
/*  739 */     String str = getDomainList(paramEntityItem, "MESIND_List");
/*  740 */     if (str.trim().length() > 0 && !str.equals("all") && domainInList(paramEntityItem, str)) {
/*  741 */       String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "BULKMESINDC");
/*  742 */       if (str1 == null) {
/*  743 */         String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "ORDERCODE");
/*  744 */         addDebug(paramEntityItem.getKey() + " ORDERCODE " + str2);
/*  745 */         if ("5955".equals(str2) || "5956".equals(str2)) {
/*      */           
/*  747 */           addDebug(paramEntityItem.getKey() + " set BULKMESINDC = âYesâ (MES0001) ");
/*  748 */           setFlagValue(this.m_elist.getProfile(), "BULKMESINDC", "MES0001", paramEntityItem);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeAttrBeforeCommit(ReturnEntityKey paramReturnEntityKey) {
/*  759 */     Attribute attribute = null;
/*  760 */     for (byte b = 0; b < paramReturnEntityKey.m_vctAttributes.size(); b++) {
/*  761 */       Attribute attribute1 = paramReturnEntityKey.m_vctAttributes.elementAt(b);
/*  762 */       if (attribute1.getAttributeCode().equals("BULKMESINDC")) {
/*  763 */         attribute = attribute1;
/*      */         break;
/*      */       } 
/*      */     } 
/*  767 */     paramReturnEntityKey.m_vctAttributes.clear();
/*      */     
/*  769 */     if (attribute != null) {
/*  770 */       addDebug("add BUILKMESINDC back");
/*  771 */       paramReturnEntityKey.m_vctAttributes.addElement(attribute);
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  997 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*      */ 
/*      */     
/* 1000 */     setBulkMesIndc(paramEntityItem);
/*      */     
/* 1002 */     int i = getCheck_W_W_E(paramString);
/* 1003 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("FEATURE").getEntityItem(0);
/* 1004 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*      */     
/* 1006 */     addDebug("checking " + paramEntityItem.getKey() + " internal dates keys 3,4,5,7");
/*      */ 
/*      */     
/* 1009 */     checkCanNotBeEarlier(paramEntityItem, "GENAVAILDATE", "ANNDATE", i);
/*      */ 
/*      */ 
/*      */     
/* 1013 */     checkCanNotBeEarlier(paramEntityItem, "WITHDRAWDATE", "ANNDATE", i);
/*      */ 
/*      */ 
/*      */     
/* 1017 */     checkCanNotBeEarlier(paramEntityItem, "WTHDRWEFFCTVDATE", "WITHDRAWDATE", i);
/*      */     
/* 1019 */     String str1 = getAttributeFlagEnabledValue(entityItem2, "COFCAT");
/* 1020 */     if (str1 == null) {
/* 1021 */       str1 = "";
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
/* 1049 */     checkStatusVsDQ(entityItem1, "STATUS", paramEntityItem, 4);
/* 1050 */     this.isRPQ = isRPQ(entityItem1);
/*      */ 
/*      */     
/* 1053 */     this.mdlList = getModelVE(entityItem2);
/*      */     
/* 1055 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "ORDERCODE");
/* 1056 */     addDebug(paramEntityItem.getKey() + " ORDERCODE " + str2);
/* 1057 */     this.isInitialOrderCode = "5957".equals(str2);
/*      */     
/* 1059 */     if (this.isRPQ) {
/*      */       
/* 1061 */       checkRPQFeature(paramEntityItem, entityItem1, i);
/*      */     } else {
/*      */       
/* 1064 */       addDebug(entityItem1.getKey() + " was NOT an RPQ FCTYPE: " + getAttributeFlagEnabledValue(entityItem1, "FCTYPE"));
/* 1065 */       addHeading(3, entityItem1.getEntityGroup().getLongDescription() + " and " + entityItem2
/* 1066 */           .getEntityGroup().getLongDescription() + " Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1074 */       checkOOFAVAILAvails(paramEntityItem, entityItem1, entityItem2, paramString);
/*      */     } 
/*      */     
/* 1077 */     addHeading(3, "TMF Error Checks:");
/*      */     
/* 1079 */     addDebug("checking " + paramEntityItem.getKey() + " against " + entityItem2.getKey() + " dates keys 84,85,86,87,88");
/*      */ 
/*      */ 
/*      */     
/* 1083 */     checkStatusVsDQ(entityItem2, "STATUS", paramEntityItem, getCheckLevel(4, entityItem2, "ANNDATE"));
/*      */ 
/*      */ 
/*      */     
/* 1087 */     checkCanNotBeEarlier(paramEntityItem, "ANNDATE", entityItem2, "ANNDATE", getCheck_W_E_E(paramString));
/*      */ 
/*      */     
/* 1090 */     checkCanNotBeEarlier(paramEntityItem, "GENAVAILDATE", entityItem2, "GENAVAILDATE", getCheck_W_E_E(paramString));
/*      */ 
/*      */     
/* 1093 */     if (this.isInitialOrderCode) {
/*      */       
/* 1095 */       checkCanNotBeLater(paramEntityItem, "WITHDRAWDATE", entityItem2, "WITHDRAWDATE", getCheck_W_E_E(paramString));
/*      */       
/* 1097 */       checkCanNotBeLater(paramEntityItem, "WTHDRWEFFCTVDATE", entityItem2, "WTHDRWEFFCTVDATE", getCheck_W_E_E(paramString));
/*      */     } else {
/* 1099 */       addDebug("BYPASSING MODEL.WTHDRWEFFCTVDATE and WITHDRAWDATE checks ordercode not initial");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1108 */     checkAllFeatures(entityItem1, entityItem2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1134 */     if ("100".equals(str1)) {
/*      */       try {
/* 1136 */         checkWARR(paramEntityItem, entityItem1, entityItem2, paramString);
/* 1137 */       } catch (StopWarrException stopWarrException) {}
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
/*      */   private void checkFeatureMaintPriceAndTMFWarrSvcCovr(EntityItem paramEntityItem1, EntityItem paramEntityItem2, int paramInt) throws SQLException, MiddlewareException {
/* 1153 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem2, "MAINTPRICE");
/* 1154 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem1, "WARRSVCCOVR");
/* 1155 */     addDebug("checkFeatureMaintPriceAndTMFWarrSvcCovr " + paramEntityItem2.getKey() + " MAINTPRICE " + str1 + " " + paramEntityItem1
/* 1156 */         .getKey() + " WARRSVCCOVR " + str2);
/* 1157 */     if ("Yes".equals(str1) && !"WSVC02".equals(str2)) {
/* 1158 */       this.args[0] = getLD_NDN(paramEntityItem2);
/* 1159 */       createMessage(paramInt, "MAINTPRICE_WARRSVCCOVR_ERR", this.args);
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
/*      */   private void checkWARR(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, String paramString) throws MiddlewareException, SQLException, MiddlewareShutdownInProgressException, StopWarrException {
/* 1198 */     int i = getCount("PRODSTRUCTWARR");
/*      */ 
/*      */ 
/*      */     
/* 1202 */     addHeading(3, "Identical " + this.m_elist.getEntityGroup("WARR").getLongDescription() + " Checks:");
/*      */     
/* 1204 */     identicalWARR(paramEntityItem1, paramEntityItem2, paramEntityItem3, 4);
/*      */     
/* 1206 */     if (i > 0) {
/* 1207 */       addHeading(3, this.m_elist.getEntityGroup("PRODSTRUCTWARR").getLongDescription() + " Coverage Checks:");
/*      */       
/* 1209 */       if (!this.isRPQ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1215 */         checkWarrCoverage(paramEntityItem1, paramEntityItem1, "PRODSTRUCTWARR", "OOFAVAIL", 4, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1227 */         checkWarrCoverage(paramEntityItem2, paramEntityItem1, paramEntityItem3);
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
/*      */   private void checkWarrCoverage(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3) throws SQLException, MiddlewareException {
/* 1290 */     if (!doWARRChecks()) {
/* 1291 */       addOutput("Bypassing Warranty coverage checks for now.");
/*      */       
/*      */       return;
/*      */     } 
/* 1295 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PRODSTRUCTWARR");
/* 1296 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("WARR");
/* 1297 */     addDebug("checkWarrCoverage entered featItem " + paramEntityItem1.getKey() + " psItem " + paramEntityItem2.getKey());
/*      */     
/* 1299 */     Vector vector = getDownLinkEntityItems(paramEntityItem2, "PRODSTRUCTWARR");
/*      */     
/* 1301 */     ArrayList<?> arrayList1 = new ArrayList();
/* 1302 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1303 */     Vector<DQABRSTATUS.CoverageData> vector1 = new Vector();
/*      */     
/* 1305 */     String str1 = prodstructValidFrom(paramEntityItem2, paramEntityItem1, paramEntityItem3);
/* 1306 */     String str2 = prodstructValidTo(paramEntityItem2, paramEntityItem1, paramEntityItem3);
/*      */     
/* 1308 */     ArrayList<?> arrayList2 = new ArrayList();
/* 1309 */     getCountriesAsList(paramEntityItem1, arrayList2, 4);
/* 1310 */     addDebug("checkWarrCoverage " + paramEntityItem1.getKey() + " from " + str1 + " to " + str2 + " offeringCtryList " + arrayList2);
/*      */ 
/*      */ 
/*      */     
/* 1314 */     findWarrByCtry(paramEntityItem2, "PRODSTRUCTWARR", vector, arrayList2, arrayList1, hashtable, vector1, 4);
/*      */     
/* 1316 */     addDebug("checkWarrCoverage all PRODSTRUCTWARR warrCtryList " + arrayList1 + " warrDefRelVct  ucVct " + vector1
/* 1317 */         .size());
/*      */     
/* 1319 */     if (vector1.size() == 0) {
/* 1320 */       arrayList2.clear();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1325 */     if (!arrayList1.containsAll(arrayList2)) {
/*      */       
/* 1327 */       this.args[0] = entityGroup2.getLongDescription();
/* 1328 */       this.args[1] = getLD_NDN(paramEntityItem1);
/* 1329 */       this.args[2] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */       
/* 1331 */       ArrayList arrayList = new ArrayList();
/* 1332 */       getCountriesAsList(paramEntityItem1, arrayList, 4);
/* 1333 */       arrayList.removeAll(arrayList1);
/* 1334 */       this.args[3] = getUnmatchedDescriptions(paramEntityItem1, "COUNTRYLIST", arrayList);
/* 1335 */       createMessage(4, "MISSING_CTRY_ERR2", this.args);
/* 1336 */       arrayList.clear();
/*      */     } 
/*      */ 
/*      */     
/* 1340 */     boolean bool = true;
/* 1341 */     Iterator<?> iterator = arrayList2.iterator();
/* 1342 */     while (iterator.hasNext() && bool) {
/* 1343 */       String str = (String)iterator.next();
/* 1344 */       Vector<DQABRSTATUS.CoverageData> vector2 = (Vector)hashtable.get(str);
/* 1345 */       if (vector2 != null) {
/* 1346 */         DQABRSTATUS.CoverageData coverageData = vector2.firstElement();
/* 1347 */         String str3 = coverageData.pubfrom;
/* 1348 */         addDebug("checkWarrCoverage ctry " + str + " minEffDate " + str3 + " found on " + coverageData.item.getKey());
/*      */ 
/*      */         
/* 1351 */         bool = checkDates(str3, str1, 2);
/* 1352 */         if (!bool) {
/*      */           
/* 1354 */           this.args[0] = entityGroup2.getLongDescription();
/* 1355 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup1, "EFFECTIVEDATE", "EFFECTIVEDATE");
/* 1356 */           this.args[2] = paramEntityItem2.getEntityGroup().getLongDescription();
/* 1357 */           this.args[3] = str1 + " for Country " + 
/* 1358 */             getUnmatchedDescriptions(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", str);
/* 1359 */           createMessage(4, "EFF_FROM_ERR", this.args);
/*      */         }  continue;
/*      */       } 
/* 1362 */       addDebug("checkWarrCoverage: No PRODSTRUCTWARR found for country " + str);
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
/* 1375 */     HashSet<EntityItem> hashSet = new HashSet();
/*      */     
/*      */     Enumeration<String> enumeration;
/* 1378 */     for (enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/* 1379 */       String str = enumeration.nextElement();
/* 1380 */       Vector<DQABRSTATUS.CoverageData> vector2 = (Vector)hashtable.get(str);
/*      */       
/* 1382 */       DQABRSTATUS.CoverageData coverageData = null;
/*      */       
/* 1384 */       for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 1385 */         DQABRSTATUS.CoverageData coverageData1 = vector2.elementAt(b1);
/* 1386 */         if (b1 != 0)
/*      */         {
/* 1388 */           if (coverageData1.pubto.compareTo(str1) < 0 || coverageData1.pubfrom
/* 1389 */             .compareTo(str2) > 0) {
/* 1390 */             addDebug("checkWarrCoverage " + coverageData1 + " is outside offering range for " + str + " fromdate " + str1 + " todate " + str2);
/*      */           }
/*      */           else {
/*      */             
/* 1394 */             Date date1 = Date.valueOf(coverageData.pubto);
/* 1395 */             long l1 = date1.getTime();
/* 1396 */             long l2 = l1 + 86400000L;
/* 1397 */             Date date2 = new Date(l2);
/* 1398 */             Date date3 = Date.valueOf(coverageData1.pubfrom);
/* 1399 */             addDebug("GAPTEST[" + b1 + "] ctry " + str + " prev pubto " + coverageData.pubto + " pubtoPlus1Date " + date2 + " cur pubfrom " + coverageData1.pubfrom);
/*      */             
/* 1401 */             if (date2.compareTo(date3) < 0) {
/* 1402 */               if (!hashSet.contains(coverageData.item) || 
/* 1403 */                 !hashSet.contains(coverageData1.item)) {
/* 1404 */                 hashSet.add(coverageData.item);
/* 1405 */                 hashSet.add(coverageData1.item);
/* 1406 */                 addDebug("checkWarrCoverage output date range msg for " + coverageData.item.getKey() + " and " + coverageData1.item
/* 1407 */                     .getKey());
/*      */ 
/*      */                 
/* 1410 */                 this.args[0] = getLD_NDN(coverageData.item) + " for " + getLD_NDN(coverageData.warritem);
/* 1411 */                 this.args[1] = getLD_NDN(coverageData1.item) + " for " + getLD_NDN(coverageData1.warritem);
/* 1412 */                 createMessage(4, "DATE_RANGE_ERR2", this.args);
/*      */               } else {
/* 1414 */                 addDebug("checkWarrCoverage already output date range msg for " + coverageData.item.getKey() + " and " + coverageData1.item
/* 1415 */                     .getKey());
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/*      */         
/* 1421 */         if (coverageData == null || coverageData.pubto.compareTo(coverageData1.pubto) < 0) {
/* 1422 */           coverageData = coverageData1;
/*      */         }
/*      */       } 
/*      */     } 
/* 1426 */     hashSet.clear();
/*      */ 
/*      */ 
/*      */     
/* 1430 */     for (enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/* 1431 */       String str = enumeration.nextElement();
/* 1432 */       Vector<DQABRSTATUS.CoverageData> vector2 = (Vector)hashtable.get(str);
/* 1433 */       for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 1434 */         DQABRSTATUS.CoverageData coverageData = vector2.elementAt(b1);
/* 1435 */         coverageData.setPubFromSort(false);
/*      */       } 
/* 1437 */       Collections.sort(vector2);
/*      */     } 
/*      */ 
/*      */     
/* 1441 */     bool = true;
/* 1442 */     iterator = arrayList2.iterator();
/* 1443 */     while (iterator.hasNext() && bool) {
/* 1444 */       String str = (String)iterator.next();
/* 1445 */       Vector<DQABRSTATUS.CoverageData> vector2 = (Vector)hashtable.get(str);
/* 1446 */       if (vector2 != null) {
/* 1447 */         DQABRSTATUS.CoverageData coverageData = vector2.lastElement();
/* 1448 */         String str3 = coverageData.pubto;
/* 1449 */         addDebug("checkWarrCoverage ctry " + str + " maxEndDate " + str3 + " found on " + coverageData.item.getKey());
/*      */ 
/*      */         
/* 1452 */         bool = checkDates(str3, str2, 1);
/* 1453 */         if (!bool) {
/*      */           
/* 1455 */           this.args[0] = entityGroup2.getLongDescription();
/* 1456 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup1, "ENDDATE", "ENDDATE");
/* 1457 */           this.args[2] = paramEntityItem2.getEntityGroup().getLongDescription();
/* 1458 */           this.args[3] = str2 + " for Country " + 
/* 1459 */             getUnmatchedDescriptions(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", str);
/* 1460 */           createMessage(4, "EFF_TO_ERR", this.args);
/*      */         }  continue;
/*      */       } 
/* 1463 */       addDebug("checkWarrCoverage: No PRODSTRUCTWARR found for country " + str);
/*      */     } 
/*      */ 
/*      */     
/* 1467 */     arrayList2.clear();
/*      */ 
/*      */ 
/*      */     
/* 1471 */     for (enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/* 1472 */       String str = enumeration.nextElement();
/* 1473 */       Vector vector2 = (Vector)hashtable.get(str);
/* 1474 */       vector2.clear();
/*      */     } 
/* 1476 */     for (byte b = 0; b < vector1.size(); b++) {
/* 1477 */       DQABRSTATUS.CoverageData coverageData = vector1.elementAt(b);
/* 1478 */       coverageData.dereference();
/*      */     } 
/* 1480 */     hashtable.clear();
/* 1481 */     vector1.clear();
/* 1482 */     vector.clear();
/* 1483 */     arrayList1.clear();
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
/*      */   private String prodstructValidFrom(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3) throws SQLException, MiddlewareException {
/* 1507 */     String str1 = PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", "", null, false);
/* 1508 */     String str2 = PokUtils.getAttributeValue(paramEntityItem3, "ANNDATE", "", null, false);
/* 1509 */     String str3 = PokUtils.getAttributeValue(paramEntityItem2, "FIRSTANNDATE", "", null, false);
/* 1510 */     addDebug("prodstructValidFrom " + paramEntityItem1.getKey() + " ANNDATE: " + str1 + " " + paramEntityItem2
/* 1511 */         .getKey() + " FIRSTANNDATE: " + str3 + " " + paramEntityItem3
/* 1512 */         .getKey() + " ANNDATE: " + str2);
/* 1513 */     if (str1 != null) {
/* 1514 */       return str1;
/*      */     }
/* 1516 */     if (str3 != null && str2 != null) {
/* 1517 */       if (str3.compareTo(str2) < 0) {
/* 1518 */         return str2;
/*      */       }
/* 1520 */       return str3;
/*      */     } 
/* 1522 */     if (str2 != null) {
/* 1523 */       return str2;
/*      */     }
/* 1525 */     if (str3 != null) {
/* 1526 */       return str3;
/*      */     }
/*      */ 
/*      */     
/* 1530 */     this.args[0] = getLD_NDN(paramEntityItem2);
/* 1531 */     this.args[1] = getLD_NDN(paramEntityItem3);
/* 1532 */     this.args[2] = PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "FIRSTANNDATE", "FIRSTANNDATE");
/* 1533 */     addWarning("NO_VALUE_MSG", this.args);
/*      */     
/* 1535 */     return "9999-12-31";
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
/*      */   private String prodstructValidTo(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3) {
/* 1552 */     String str1 = PokUtils.getAttributeValue(paramEntityItem1, "WTHDRWEFFCTVDATE", "", null, false);
/* 1553 */     String str2 = PokUtils.getAttributeValue(paramEntityItem3, "WTHDRWEFFCTVDATE", "", "9999-12-31", false);
/* 1554 */     String str3 = PokUtils.getAttributeValue(paramEntityItem2, "WITHDRAWDATEEFF_T", "", "9999-12-31", false);
/* 1555 */     addDebug("prodstructValidTo " + paramEntityItem1.getKey() + " WTHDRWEFFCTVDATE: " + str1 + " " + paramEntityItem3
/* 1556 */         .getKey() + " WTHDRWEFFCTVDATE: " + str2 + " " + paramEntityItem2
/* 1557 */         .getKey() + " WITHDRAWDATEEFF_T: " + str3);
/* 1558 */     if (str1 != null) {
/* 1559 */       return str1;
/*      */     }
/* 1561 */     if (str3.compareTo(str2) > 0) {
/* 1562 */       return str2;
/*      */     }
/* 1564 */     return str3;
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
/*      */   private String oldGaValidFrom(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3) throws SQLException, MiddlewareException {
/* 1580 */     String str1 = PokUtils.getAttributeValue(paramEntityItem1, "GENAVAILDATE", "", null, false);
/* 1581 */     String str2 = PokUtils.getAttributeValue(paramEntityItem3, "ANNDATE", "", null, false);
/* 1582 */     String str3 = PokUtils.getAttributeValue(paramEntityItem2, "GENAVAILDATE", "", null, false);
/* 1583 */     addDebug("oldGaValidFrom " + paramEntityItem1.getKey() + " GENAVAILDATE: " + str1 + " " + paramEntityItem2
/* 1584 */         .getKey() + " GENAVAILDATE: " + str3 + " " + paramEntityItem3
/* 1585 */         .getKey() + " ANNDATE: " + str2);
/* 1586 */     if (str1 != null) {
/* 1587 */       return str1;
/*      */     }
/* 1589 */     if (str3 != null && str2 != null) {
/* 1590 */       if (str3.compareTo(str2) < 0) {
/* 1591 */         return str2;
/*      */       }
/* 1593 */       return str3;
/*      */     } 
/* 1595 */     if (str2 != null) {
/* 1596 */       return str2;
/*      */     }
/* 1598 */     if (str3 != null) {
/* 1599 */       return str3;
/*      */     }
/*      */     
/* 1602 */     return "9999-12-31";
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
/*      */   private void identicalWARR(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, int paramInt) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, StopWarrException {
/* 1899 */     if (!doWARRChecks()) {
/* 1900 */       addOutput("Bypassing Identical Warranty checks for now.");
/*      */       
/*      */       return;
/*      */     } 
/* 1904 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PRODSTRUCTWARR");
/* 1905 */     addDebug("identicalWARR: root pswarrGrp " + entityGroup1.getEntityItemCount());
/*      */ 
/*      */     
/* 1908 */     if (this.mtmList == null) {
/*      */ 
/*      */       
/* 1911 */       searchForProdstructs(paramEntityItem2, paramEntityItem3);
/* 1912 */       if (this.mtmList == null) {
/*      */         return;
/*      */       }
/* 1915 */       addDebug("identicalWARR search: " + NEWLINE + PokUtils.outputList(this.mtmList));
/*      */     } 
/*      */     
/* 1918 */     EntityGroup entityGroup2 = this.mtmList.getEntityGroup("PRODSTRUCT");
/* 1919 */     if (this.mtmList.getEntityGroup("FEATURE").getEntityItemCount() != 1) {
/* 1920 */       String str1 = PokUtils.getAttributeValue(paramEntityItem2, "FEATURECODE", ", ", "", false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1926 */       for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 1927 */         EntityItem entityItem = entityGroup2.getEntityItem(b);
/* 1928 */         if (entityItem.getEntityID() != paramEntityItem1.getEntityID()) {
/*      */ 
/*      */ 
/*      */           
/* 1932 */           this.args[0] = getLD_NDN(entityItem);
/* 1933 */           this.args[1] = str1;
/* 1934 */           createMessage(4, "DUPE_DATA_ERR", this.args);
/*      */         } 
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1940 */     if (entityGroup2.getEntityItemCount() <= 1) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1946 */     EntityItem[] arrayOfEntityItem = new EntityItem[entityGroup2.getEntityItemCount() - 1];
/* 1947 */     byte b1 = 0;
/* 1948 */     for (byte b2 = 0; b2 < entityGroup2.getEntityItemCount(); b2++) {
/* 1949 */       EntityItem entityItem = entityGroup2.getEntityItem(b2);
/* 1950 */       if (entityItem.getEntityID() != paramEntityItem1.getEntityID())
/*      */       {
/*      */         
/* 1953 */         arrayOfEntityItem[b1++] = entityItem; } 
/*      */     } 
/* 1955 */     String str = "DQVEPSIDENTWARR";
/* 1956 */     EntityList entityList1 = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 1957 */           .getProfile(), str), arrayOfEntityItem);
/*      */ 
/*      */     
/* 1960 */     addDebug("identicalWARR extract (otherps): " + str + NEWLINE + PokUtils.outputList(entityList1));
/*      */     
/* 1962 */     EntityGroup entityGroup3 = entityList1.getEntityGroup("PRODSTRUCTWARR");
/* 1963 */     addDebug("identicalWARR: otherpswarrGrp " + entityGroup3.getEntityItemCount());
/* 1964 */     if (entityGroup3.getEntityItemCount() == 0 && entityGroup1.getEntityItemCount() == 0) {
/* 1965 */       addDebug("identicalWARR: no WARRs found, nothing to check");
/*      */       
/* 1967 */       entityList1.dereference();
/*      */       return;
/*      */     } 
/* 1970 */     EntityList entityList2 = getModelsVE(entityList1.getEntityGroup("MODEL"));
/*      */ 
/*      */     
/* 1973 */     paramEntityItem3 = this.mdlList.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 1975 */     EntityGroup entityGroup4 = entityList1.getParentEntityGroup();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2008 */     Hashtable hashtable1 = getTpicByCtry(paramEntityItem1, paramEntityItem2, paramEntityItem3);
/* 2009 */     addDebug(3, "identicalWARR root psitem " + paramEntityItem1.getKey() + " tpicByCtryTbl: " + hashtable1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2016 */     Hashtable hashtable2 = getWarrTpicByCtry(paramEntityItem1, hashtable1.keySet());
/* 2017 */     addDebug(3, "identicalWARR root psitem " + paramEntityItem1.getKey() + " warrTpicTbl: " + hashtable2);
/* 2018 */     for (byte b3 = 0; b3 < entityGroup4.getEntityItemCount(); b3++) {
/* 2019 */       EntityItem entityItem1 = entityGroup4.getEntityItem(b3);
/* 2020 */       EntityItem entityItem2 = getUpLinkEntityItem(entityItem1, "FEATURE");
/* 2021 */       EntityItem entityItem3 = getDownLinkEntityItem(entityItem1, "MODEL");
/* 2022 */       addDebug("\nidenticalWARR: checking otherPsItem " + entityItem1.getKey() + " " + entityItem3.getKey());
/*      */ 
/*      */       
/* 2025 */       entityItem3 = entityList2.getParentEntityGroup().getEntityItem(entityItem3.getKey());
/*      */ 
/*      */       
/* 2028 */       Hashtable hashtable3 = getTpicByCtry(entityItem1, entityItem2, entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2033 */       Hashtable hashtable4 = getWarrTpicByCtry(entityItem1, hashtable3.keySet());
/* 2034 */       addDebug(3, "identicalWARR: otherPsItem " + entityItem1.getKey() + " " + entityItem3.getKey() + " otherTpicByCtryTbl: " + hashtable3);
/*      */       
/* 2036 */       addDebug(3, "identicalWARR otherPsItem " + entityItem1.getKey() + " otherWarrTpicTbl: " + hashtable4);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2041 */       Iterator<String> iterator1 = hashtable1.keySet().iterator();
/* 2042 */       boolean bool = false;
/* 2043 */       while (iterator1.hasNext()) {
/* 2044 */         String str1 = iterator1.next();
/* 2045 */         DQABRSTATUS.TPIC tPIC1 = (DQABRSTATUS.TPIC)hashtable1.get(str1);
/* 2046 */         DQABRSTATUS.TPIC tPIC2 = (DQABRSTATUS.TPIC)hashtable3.get(str1);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2051 */         DQABRSTATUS.TPIC tPIC3 = tPIC1.getOverlay(tPIC2);
/* 2052 */         addDebug(3, "identicalWARR tpic: " + tPIC1 + "\n othertpic " + tPIC2 + "\n overlaid " + tPIC3);
/* 2053 */         if (tPIC3 != null) {
/*      */ 
/*      */           
/* 2056 */           Vector<WarrTPIC> vector1 = (Vector)hashtable2.get(str1);
/* 2057 */           addDebug("identicalWARR ctryflag " + str1 + " root " + paramEntityItem1.getKey() + " idvct " + vector1);
/* 2058 */           if (vector1 == null) {
/* 2059 */             tPIC3.dereference();
/* 2060 */             addDebug("identicalWARR NO WARR found for roots avail ctryflag " + str1 + ", error flagged in warrcoverage chk");
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 2067 */           Vector<WarrTPIC> vector2 = new Vector();
/* 2068 */           for (byte b = 0; b < vector1.size(); b++) {
/* 2069 */             WarrTPIC warrTPIC = vector1.elementAt(b);
/* 2070 */             if (warrTPIC.hasOverlay(tPIC3)) {
/*      */               
/* 2072 */               vector2.add(warrTPIC);
/*      */             } else {
/*      */               
/* 2075 */               addDebug("identicalWARR ctryflag " + str1 + " root warrtpic " + warrTPIC + " does not overlap availtpic " + tPIC3);
/*      */             } 
/*      */           } 
/* 2078 */           if (vector2.size() == 0) {
/*      */             
/* 2080 */             addDebug("identicalWARR ctryflag " + str1 + " did not have any overlap in root avail and warr TPIC");
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 2087 */           Vector<WarrTPIC> vector3 = (Vector)hashtable4.get(str1);
/* 2088 */           addDebug("identicalWARR ctryflag " + str1 + " " + entityItem1.getKey() + " b4 overlay otheridvct " + vector3);
/*      */           
/* 2090 */           if (vector3 != null) {
/*      */             
/* 2092 */             Vector<WarrTPIC> vector4 = new Vector();
/* 2093 */             for (byte b4 = 0; b4 < vector3.size(); b4++) {
/* 2094 */               WarrTPIC warrTPIC = vector3.elementAt(b4);
/* 2095 */               if (warrTPIC.hasOverlay(tPIC3)) {
/*      */                 
/* 2097 */                 vector4.add(warrTPIC);
/*      */               } else {
/* 2099 */                 addDebug("identicalWARR ctryflag " + str1 + " other warrtpic " + warrTPIC + " does not overlap root availtpic " + tPIC3);
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2109 */             Vector<WarrTPIC> vector5 = new Vector();
/* 2110 */             vector5.addAll(vector2);
/* 2111 */             vector5.removeAll(vector4);
/*      */             
/* 2113 */             Vector<WarrTPIC> vector6 = new Vector();
/* 2114 */             vector6.addAll(vector4);
/* 2115 */             vector6.removeAll(vector2);
/*      */             
/* 2117 */             Vector<WarrTPIC> vector7 = new Vector();
/* 2118 */             vector7.addAll(vector2);
/* 2119 */             vector7.retainAll(vector4);
/*      */             
/* 2121 */             addDebug("identicalWARR ctryflag " + str1 + " myWarrIdVct " + vector2);
/* 2122 */             addDebug("identicalWARR  otherWarrIdVct " + vector4);
/* 2123 */             addDebug("identicalWARR  missingVct (data linked only to root) " + vector5);
/* 2124 */             addDebug("identicalWARR  extraVct (data linked to otherps) " + vector6);
/* 2125 */             addDebug("identicalWARR  sharedVct (this data is from root with id match only) " + vector7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2132 */             if (vector7.size() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2147 */               boolean bool1 = false; byte b5;
/* 2148 */               for (b5 = 0; b5 < vector7.size(); b5++) {
/* 2149 */                 WarrTPIC warrTPIC = vector7.elementAt(b5);
/*      */                 
/* 2151 */                 for (byte b6 = 0; b6 < vector4.size(); b6++) {
/* 2152 */                   WarrTPIC warrTPIC1 = vector4.elementAt(b6);
/* 2153 */                   if (warrTPIC1.equals(warrTPIC)) {
/* 2154 */                     if (warrTPIC1.isDefWarr == warrTPIC.isDefWarr) {
/*      */                       break;
/*      */                     }
/* 2157 */                     bool1 = true;
/*      */ 
/*      */                     
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */               
/* 2165 */               if (bool1) {
/* 2166 */                 addDebug("identicalWARR  WARR exist for ctryflag " + str1 + " on root " + paramEntityItem1
/* 2167 */                     .getKey() + " and " + entityItem1.getKey() + " but diff deffwarr");
/*      */ 
/*      */ 
/*      */                 
/* 2171 */                 this.args[0] = getLD_NDN(entityItem1);
/* 2172 */                 this.args[1] = entityList1.getEntityGroup("WARR").getLongDescription();
/* 2173 */                 this.args[2] = tPIC3.fromDate;
/* 2174 */                 this.args[3] = tPIC3.toDate;
/* 2175 */                 createMessage(paramInt, "IDENTICAL_DEFWARR_ERR", this.args);
/*      */                 
/* 2177 */                 outputWarrError(paramEntityItem1, paramEntityItem3, tPIC1, vector2);
/* 2178 */                 outputWarrError(entityItem1, entityItem3, tPIC2, vector4);
/* 2179 */                 bool = true;
/*      */               } else {
/* 2181 */                 for (b5 = 0; b5 < vector7.size(); b5++) {
/* 2182 */                   WarrTPIC warrTPIC = vector7.elementAt(b5);
/* 2183 */                   for (byte b6 = 0; b6 < vector4.size(); b6++) {
/* 2184 */                     WarrTPIC warrTPIC1 = vector4.elementAt(b6);
/* 2185 */                     if (warrTPIC.equals(warrTPIC1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/* 2190 */                       if (!warrTPIC.maxFromDate.equals(warrTPIC1.maxFromDate) || 
/* 2191 */                         !warrTPIC.minToDate.equals(warrTPIC1.minToDate)) {
/* 2192 */                         addDebug("identicalWARR  WARR exist for ctryflag " + str1 + " on root " + paramEntityItem1
/* 2193 */                             .getKey() + " and " + entityItem1.getKey() + " but diff dates");
/* 2194 */                         addDebug("identicalWARR  wt " + warrTPIC + " otherwt " + warrTPIC1);
/*      */ 
/*      */ 
/*      */                         
/* 2198 */                         this.args[0] = getLD_NDN(entityItem1);
/* 2199 */                         this.args[1] = entityList1.getEntityGroup("WARR").getLongDescription();
/* 2200 */                         this.args[2] = tPIC3.fromDate;
/* 2201 */                         this.args[3] = tPIC3.toDate;
/*      */                         
/* 2203 */                         createMessage(paramInt, "IDENTICAL_WARR_ERR", this.args);
/*      */                         
/* 2205 */                         outputWarrError(paramEntityItem1, paramEntityItem3, tPIC1, vector2);
/* 2206 */                         outputWarrError(entityItem1, entityItem3, tPIC2, vector4);
/* 2207 */                         bool = true;
/*      */                       } 
/*      */                       
/*      */                       break;
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */               
/* 2216 */               vector7.clear();
/*      */             } 
/*      */             
/* 2219 */             if (!bool && vector5.size() > 0) {
/* 2220 */               addDebug("identicalWARR  WARR exist for ctryflag " + str1 + " on root " + paramEntityItem1
/* 2221 */                   .getKey() + " but not " + entityItem1.getKey());
/*      */ 
/*      */               
/* 2224 */               this.args[0] = getLD_NDN(entityItem1);
/* 2225 */               this.args[1] = entityList1.getEntityGroup("WARR").getLongDescription();
/* 2226 */               this.args[2] = tPIC3.fromDate;
/* 2227 */               this.args[3] = tPIC3.toDate;
/*      */               
/* 2229 */               createMessage(paramInt, "IDENTICAL_WARR_ERR", this.args);
/*      */               
/* 2231 */               outputWarrError(paramEntityItem1, paramEntityItem3, tPIC1, vector2);
/* 2232 */               outputWarrError(entityItem1, entityItem3, tPIC2, vector4);
/* 2233 */               bool = true;
/*      */             } 
/* 2235 */             vector5.clear();
/*      */             
/* 2237 */             if (!bool && vector6.size() > 0) {
/* 2238 */               addDebug("identicalWARR  WARR exist for ctryflag " + str1 + " on " + entityItem1
/* 2239 */                   .getKey() + " but not root " + paramEntityItem1.getKey());
/*      */ 
/*      */ 
/*      */               
/* 2243 */               this.args[0] = getLD_NDN(entityItem1);
/* 2244 */               this.args[1] = entityList1.getEntityGroup("WARR").getLongDescription();
/* 2245 */               this.args[2] = tPIC3.fromDate;
/* 2246 */               this.args[3] = tPIC3.toDate;
/* 2247 */               createMessage(paramInt, "IDENTICAL_WARR_ERR", this.args);
/*      */               
/* 2249 */               outputWarrError(paramEntityItem1, paramEntityItem3, tPIC1, vector2);
/* 2250 */               outputWarrError(entityItem1, entityItem3, tPIC2, vector4);
/* 2251 */               bool = true;
/*      */             } 
/* 2253 */             vector6.clear();
/* 2254 */             vector4.clear();
/*      */           } else {
/*      */             
/* 2257 */             addDebug("identicalWARR NO WARR found for ctryflag " + str1 + " on " + entityItem1.getKey());
/*      */ 
/*      */ 
/*      */             
/* 2261 */             this.args[0] = getLD_NDN(entityItem1);
/* 2262 */             this.args[1] = entityList1.getEntityGroup("WARR").getLongDescription();
/* 2263 */             this.args[2] = tPIC3.fromDate;
/* 2264 */             this.args[3] = tPIC3.toDate;
/* 2265 */             createMessage(paramInt, "IDENTICAL_WARR_ERR", this.args);
/*      */             
/* 2267 */             outputWarrError(paramEntityItem1, paramEntityItem3, tPIC1, vector2);
/* 2268 */             outputWarrError(entityItem1, entityItem3, tPIC2, (Vector)null);
/* 2269 */             bool = true;
/*      */           } 
/*      */           
/* 2272 */           vector2.clear();
/* 2273 */           tPIC3.dereference();
/*      */         } 
/*      */         
/* 2276 */         if (bool) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2282 */       Iterator iterator2 = hashtable3.keySet().iterator();
/* 2283 */       while (iterator2.hasNext()) {
/* 2284 */         DQABRSTATUS.TPIC tPIC = (DQABRSTATUS.TPIC)hashtable3.get(iterator2.next());
/* 2285 */         tPIC.dereference();
/*      */       } 
/* 2287 */       hashtable3.clear();
/*      */       
/* 2289 */       iterator2 = hashtable4.keySet().iterator();
/* 2290 */       while (iterator2.hasNext()) {
/* 2291 */         Vector<WarrTPIC> vector = (Vector)hashtable4.get(iterator2.next());
/* 2292 */         for (byte b = 0; b < vector.size(); b++) {
/* 2293 */           WarrTPIC warrTPIC = vector.elementAt(b);
/* 2294 */           warrTPIC.dereference();
/*      */         } 
/* 2296 */         vector.clear();
/*      */       } 
/* 2298 */       hashtable4.clear();
/*      */     } 
/*      */     
/* 2301 */     entityList1.dereference();
/* 2302 */     entityList2.dereference();
/*      */ 
/*      */     
/* 2305 */     Iterator iterator = hashtable2.keySet().iterator();
/* 2306 */     while (iterator.hasNext()) {
/* 2307 */       Vector<WarrTPIC> vector = (Vector)hashtable2.get(iterator.next());
/* 2308 */       for (byte b = 0; b < vector.size(); b++) {
/* 2309 */         WarrTPIC warrTPIC = vector.elementAt(b);
/* 2310 */         warrTPIC.dereference();
/*      */       } 
/* 2312 */       vector.clear();
/*      */     } 
/* 2314 */     hashtable2.clear();
/*      */     
/* 2316 */     iterator = hashtable1.keySet().iterator();
/* 2317 */     while (iterator.hasNext()) {
/* 2318 */       DQABRSTATUS.TPIC tPIC = (DQABRSTATUS.TPIC)hashtable1.get(iterator.next());
/* 2319 */       tPIC.dereference();
/*      */     } 
/* 2321 */     hashtable1.clear();
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
/*      */   private void outputWarrError(EntityItem paramEntityItem1, EntityItem paramEntityItem2, DQABRSTATUS.TPIC paramTPIC, Vector<WarrTPIC> paramVector) throws SQLException, MiddlewareException, StopWarrException {
/* 2361 */     boolean bool = (paramEntityItem1.getEntityID() == getEntityID()) ? true : false;
/* 2362 */     addHeading(3, this.m_elist.getEntityGroup("WARR").getLongDescription() + " check information for: " + (bool ? "Root " : "") + 
/* 2363 */         getLD_NDN(paramEntityItem1));
/* 2364 */     StringBuffer stringBuffer = new StringBuffer("<table width='100%' border='1'><colgroup><col width='20%'><col width='80%'/></colgroup>" + NEWLINE);
/* 2365 */     EntityItem entityItem = getUpLinkEntityItem(paramEntityItem1, "FEATURE");
/*      */     
/* 2367 */     if (this.isRPQ) {
/* 2368 */       stringBuffer.append("<tr><td colspan='2'><b>RPQ product</b></td></tr>" + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2378 */       stringBuffer.append("<tr><td title='" + paramEntityItem1.getKey() + "'>" + paramEntityItem1.getEntityGroup().getLongDescription() + ": " + 
/* 2379 */           PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "ANNDATE", "ANNDATE") + ": </td><td>" + 
/* 2380 */           PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2381 */       stringBuffer.append("<tr><td title='" + paramEntityItem2.getKey() + "'>" + paramEntityItem2.getEntityGroup().getLongDescription() + ": " + 
/* 2382 */           PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "ANNDATE", "ANNDATE") + ": </td><td>" + 
/* 2383 */           PokUtils.getAttributeValue(paramEntityItem2, "ANNDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2384 */       stringBuffer.append("<tr><td title='" + entityItem.getKey() + "'>" + entityItem.getEntityGroup().getLongDescription() + ": " + 
/* 2385 */           PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "FIRSTANNDATE", "FIRSTANNDATE") + ": </td><td title='" + entityItem
/* 2386 */           .getKey() + "'>" + PokUtils.getAttributeValue(entityItem, "FIRSTANNDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2387 */       stringBuffer.append("<tr><td title='" + paramEntityItem1.getKey() + "'>" + paramEntityItem1.getEntityGroup().getLongDescription() + ": " + 
/* 2388 */           PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE") + ": </td><td>" + 
/* 2389 */           PokUtils.getAttributeValue(paramEntityItem1, "WTHDRWEFFCTVDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2390 */       stringBuffer.append("<tr><td title='" + paramEntityItem2.getKey() + "'>" + paramEntityItem2.getEntityGroup().getLongDescription() + ": " + 
/* 2391 */           PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE") + ": </td><td>" + 
/* 2392 */           PokUtils.getAttributeValue(paramEntityItem2, "WTHDRWEFFCTVDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2393 */       stringBuffer.append("<tr><td title='" + entityItem.getKey() + "'>" + entityItem.getEntityGroup().getLongDescription() + ": " + 
/* 2394 */           PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "WITHDRAWDATEEFF_T", "WITHDRAWDATEEFF_T") + ": </td><td>" + 
/* 2395 */           PokUtils.getAttributeValue(entityItem, "WITHDRAWDATEEFF_T", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2396 */       stringBuffer.append("<tr><td title='" + entityItem.getKey() + "'>" + entityItem.getEntityGroup().getLongDescription() + ": " + 
/* 2397 */           PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST") + ": </td><td>" + 
/* 2398 */           PokUtils.getAttributeValue(entityItem, "COUNTRYLIST", ", ", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/*      */       
/* 2400 */       Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELAVAIL", "AVAIL");
/* 2401 */       Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/*      */ 
/*      */       
/* 2404 */       if (vector1.size() > 0) {
/* 2405 */         ArrayList<?> arrayList1 = new ArrayList();
/* 2406 */         getCountriesAsList(entityItem, arrayList1, -1);
/*      */ 
/*      */ 
/*      */         
/* 2410 */         ArrayList<?> arrayList2 = getCountriesAsList(vector1, -1);
/* 2411 */         arrayList1.retainAll(arrayList2);
/*      */         
/* 2413 */         if (arrayList1.size() > 0) {
/* 2414 */           for (byte b = 0; b < vector1.size(); b++) {
/* 2415 */             EntityItem entityItem1 = vector1.elementAt(b);
/* 2416 */             ArrayList arrayList = new ArrayList();
/* 2417 */             getCountriesAsList(entityItem1, arrayList, -1);
/* 2418 */             arrayList.retainAll(arrayList1);
/* 2419 */             if (arrayList.size() > 0) {
/* 2420 */               stringBuffer.append("<tr><td colspan='2' title='" + paramEntityItem2.getKey() + ":" + entityItem1
/* 2421 */                   .getKey() + "'>Model " + getLD_NDN(entityItem1) + "</td></tr>" + NEWLINE);
/* 2422 */               stringBuffer.append("<tr><td title='" + entityItem1.getKey() + "'>" + entityItem1.getEntityGroup().getLongDescription() + ": " + 
/* 2423 */                   PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST") + ": </td><td>" + 
/* 2424 */                   PokUtils.getAttributeValue(entityItem1, "COUNTRYLIST", ", ", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } else {
/* 2428 */           stringBuffer.append("<tr><td colspan='2'>No intersection in Model Planned Availability CountryList and the Feature</td></tr>" + NEWLINE);
/*      */         } 
/*      */       } 
/*      */       
/* 2432 */       vector1.clear();
/* 2433 */       vector.clear();
/*      */     
/*      */     }
/* 2436 */     else if (isOldGAProduct(paramEntityItem1, paramEntityItem2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2454 */       Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELAVAIL", "AVAIL");
/* 2455 */       Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/*      */ 
/*      */       
/* 2458 */       if (vector1.size() > 0) {
/* 2459 */         Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/*      */         
/* 2461 */         stringBuffer.append("<tr><td colspan='2'><b>Old GA product with Model Planned Availability</b></td></tr>" + NEWLINE);
/* 2462 */         ArrayList<?> arrayList1 = new ArrayList();
/* 2463 */         getCountriesAsList(entityItem, arrayList1, -1);
/*      */ 
/*      */ 
/*      */         
/* 2467 */         ArrayList<?> arrayList2 = getCountriesAsList(vector1, -1);
/* 2468 */         arrayList1.retainAll(arrayList2);
/*      */ 
/*      */         
/* 2471 */         stringBuffer.append("<tr><td title='" + paramEntityItem1.getKey() + "'>" + paramEntityItem1.getEntityGroup().getLongDescription() + ": " + 
/* 2472 */             PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "GENAVAILDATE", "GENAVAILDATE") + ": </td><td>" + 
/* 2473 */             PokUtils.getAttributeValue(paramEntityItem1, "GENAVAILDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2474 */         stringBuffer.append("<tr><td title='" + entityItem.getKey() + "'>" + entityItem.getEntityGroup().getLongDescription() + ": " + 
/* 2475 */             PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "GENAVAILDATE", "GENAVAILDATE") + ": </td><td>" + 
/* 2476 */             PokUtils.getAttributeValue(entityItem, "GENAVAILDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2477 */         stringBuffer.append("<tr><td title='" + entityItem.getKey() + "'>" + entityItem.getEntityGroup().getLongDescription() + ": " + 
/* 2478 */             PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST") + ": </td><td>" + 
/* 2479 */             PokUtils.getAttributeValue(entityItem, "COUNTRYLIST", ", ", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/*      */         
/* 2481 */         if (arrayList1.size() > 0) {
/* 2482 */           for (byte b = 0; b < vector1.size(); b++) {
/* 2483 */             EntityItem entityItem1 = vector1.elementAt(b);
/* 2484 */             ArrayList arrayList = new ArrayList();
/* 2485 */             getCountriesAsList(entityItem1, arrayList, -1);
/* 2486 */             arrayList.retainAll(arrayList1);
/* 2487 */             if (arrayList.size() > 0) {
/* 2488 */               stringBuffer.append("<tr><td colspan='2' title='" + paramEntityItem2.getKey() + ":" + entityItem1
/* 2489 */                   .getKey() + "'>Model " + getLD_NDN(entityItem1) + "</td></tr>" + NEWLINE);
/* 2490 */               stringBuffer.append("<tr><td title='" + entityItem1.getKey() + "'>" + entityItem1.getEntityGroup().getLongDescription() + ": " + 
/* 2491 */                   PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST") + ": </td><td>" + 
/* 2492 */                   PokUtils.getAttributeValue(entityItem1, "COUNTRYLIST", ", ", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2493 */               arrayList.clear();
/*      */             } 
/*      */           } 
/*      */         } else {
/* 2497 */           stringBuffer.append("<tr><td colspan='2'>No intersection in Model Planned Availability CountryList and the Feature</td></tr>" + NEWLINE);
/*      */         } 
/* 2499 */         stringBuffer.append("<tr><td title='" + paramEntityItem1.getKey() + "'>" + paramEntityItem1.getEntityGroup().getLongDescription() + ": " + 
/* 2500 */             PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE") + ": </td><td>" + 
/* 2501 */             PokUtils.getAttributeValue(paramEntityItem1, "WTHDRWEFFCTVDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2502 */         stringBuffer.append("<tr><td title='" + entityItem.getKey() + "'>" + entityItem.getEntityGroup().getLongDescription() + ": " + 
/* 2503 */             PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "WITHDRAWDATEEFF_T", "WITHDRAWDATEEFF_T") + ": </td><td>" + 
/* 2504 */             PokUtils.getAttributeValue(entityItem, "WITHDRAWDATEEFF_T", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2505 */         stringBuffer.append("<tr><td title='" + paramEntityItem2.getKey() + "'>" + paramEntityItem2.getEntityGroup().getLongDescription() + ": " + 
/* 2506 */             PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE") + ": </td><td>" + 
/* 2507 */             PokUtils.getAttributeValue(paramEntityItem2, "WTHDRWEFFCTVDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/*      */         
/* 2509 */         if (arrayList1.size() > 0) {
/* 2510 */           for (byte b = 0; b < vector2.size(); b++) {
/* 2511 */             EntityItem entityItem1 = vector2.elementAt(b);
/* 2512 */             ArrayList arrayList = new ArrayList();
/* 2513 */             getCountriesAsList(entityItem1, arrayList, -1);
/* 2514 */             arrayList.retainAll(arrayList1);
/* 2515 */             if (arrayList.size() > 0) {
/* 2516 */               stringBuffer.append("<tr><td colspan='2' title='" + paramEntityItem2.getKey() + ":" + entityItem1
/* 2517 */                   .getKey() + "'>Model " + getLD_NDN(entityItem1) + "</td></tr>" + NEWLINE);
/* 2518 */               stringBuffer.append("<tr><td title='" + entityItem1.getKey() + "'>" + entityItem1.getEntityGroup().getLongDescription() + ": " + 
/* 2519 */                   PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST") + ": </td><td>" + 
/* 2520 */                   PokUtils.getAttributeValue(entityItem1, "COUNTRYLIST", ", ", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2521 */               arrayList.clear();
/*      */             } 
/*      */           } 
/*      */         }
/* 2525 */         vector1.clear();
/* 2526 */         vector2.clear();
/* 2527 */         arrayList1.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2540 */         stringBuffer.append("<tr><td colspan='2'><b>Old GA product with no Model Planned Availability</b></td></tr>" + NEWLINE);
/* 2541 */         stringBuffer.append("<tr><td title='" + paramEntityItem1.getKey() + "'>" + paramEntityItem1.getEntityGroup().getLongDescription() + ": " + 
/* 2542 */             PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "GENAVAILDATE", "GENAVAILDATE") + ": </td><td>" + 
/* 2543 */             PokUtils.getAttributeValue(paramEntityItem1, "GENAVAILDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2544 */         stringBuffer.append("<tr><td title='" + entityItem.getKey() + "'>" + entityItem.getEntityGroup().getLongDescription() + ": " + 
/* 2545 */             PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "GENAVAILDATE", "GENAVAILDATE") + ": </td><td>" + 
/* 2546 */             PokUtils.getAttributeValue(entityItem, "GENAVAILDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2547 */         stringBuffer.append("<tr><td title='" + paramEntityItem2.getKey() + "'>" + paramEntityItem2.getEntityGroup().getLongDescription() + ": " + 
/* 2548 */             PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "ANNDATE", "ANNDATE") + ": </td><td>" + 
/* 2549 */             PokUtils.getAttributeValue(paramEntityItem2, "ANNDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2550 */         stringBuffer.append("<tr><td title='" + paramEntityItem1.getKey() + "'>" + paramEntityItem1.getEntityGroup().getLongDescription() + ": " + 
/* 2551 */             PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE") + ": </td><td>" + 
/* 2552 */             PokUtils.getAttributeValue(paramEntityItem1, "WTHDRWEFFCTVDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2553 */         stringBuffer.append("<tr><td title='" + entityItem.getKey() + "'>" + entityItem.getEntityGroup().getLongDescription() + ": " + 
/* 2554 */             PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "WITHDRAWDATEEFF_T", "WITHDRAWDATEEFF_T") + ": </td><td>" + 
/* 2555 */             PokUtils.getAttributeValue(entityItem, "WITHDRAWDATEEFF_T", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2556 */         stringBuffer.append("<tr><td title='" + paramEntityItem2.getKey() + "'>" + paramEntityItem2.getEntityGroup().getLongDescription() + ": " + 
/* 2557 */             PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE") + ": </td><td>" + 
/* 2558 */             PokUtils.getAttributeValue(paramEntityItem2, "WTHDRWEFFCTVDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/*      */         
/* 2560 */         stringBuffer.append("<tr><td title='" + entityItem.getKey() + "'>" + entityItem.getEntityGroup().getLongDescription() + ": " + 
/* 2561 */             PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST") + ": </td><td>" + 
/* 2562 */             PokUtils.getAttributeValue(entityItem, "COUNTRYLIST", ", ", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/*      */       } 
/* 2564 */       vector.clear();
/*      */     } else {
/* 2566 */       stringBuffer.append("<tr><td colspan='2'><b>GA product</b></td></tr>" + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2572 */       String str = "No Last Order " + paramTPIC.fromItem.getEntityGroup().getLongDescription();
/* 2573 */       if (paramTPIC.toItem != null) {
/* 2574 */         str = getLD_NDN(paramTPIC.toItem);
/*      */       }
/* 2576 */       stringBuffer.append("<tr><td colspan='2' title='" + paramTPIC.fromItem.getKey() + "'>" + getLD_NDN(paramTPIC.fromItem) + "</td></tr>" + NEWLINE);
/* 2577 */       stringBuffer.append("<tr><td title='" + paramTPIC.fromItem.getKey() + "'>" + paramTPIC.fromItem.getEntityGroup().getLongDescription() + ": " + 
/* 2578 */           PokUtils.getAttributeDescription(paramTPIC.fromItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST") + ": </td><td>" + 
/* 2579 */           PokUtils.getAttributeValue(paramTPIC.fromItem, "COUNTRYLIST", ", ", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2580 */       stringBuffer.append("<tr><td colspan='2'>" + str + "</td></tr>" + NEWLINE);
/* 2581 */       if (paramTPIC.toItem != null) {
/* 2582 */         stringBuffer.append("<tr><td title='" + paramTPIC.toItem.getKey() + "'>" + paramTPIC.toItem.getEntityGroup().getLongDescription() + ": " + 
/* 2583 */             PokUtils.getAttributeDescription(paramTPIC.toItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST") + ": </td><td>" + 
/* 2584 */             PokUtils.getAttributeValue(paramTPIC.toItem, "COUNTRYLIST", ", ", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
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
/*      */     
/* 2596 */     if (paramVector != null && paramVector.size() > 0) {
/* 2597 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 2598 */         WarrTPIC warrTPIC = paramVector.elementAt(b);
/* 2599 */         stringBuffer.append("<tr><td colspan='2' title='" + warrTPIC.warrItem.getKey() + "'><b>" + getLD_NDN(warrTPIC.warrItem) + "</b></td></tr>" + NEWLINE);
/* 2600 */         stringBuffer.append("<tr><td title='" + warrTPIC.warrRel.getKey() + "'>" + warrTPIC.warrRel.getEntityGroup().getLongDescription() + ": " + 
/* 2601 */             PokUtils.getAttributeDescription(warrTPIC.warrRel.getEntityGroup(), "EFFECTIVEDATE", "EFFECTIVEDATE") + ": </td><td>" + 
/* 2602 */             PokUtils.getAttributeValue(warrTPIC.warrRel, "EFFECTIVEDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2603 */         stringBuffer.append("<tr><td>" + warrTPIC.warrRel.getEntityGroup().getLongDescription() + ": " + 
/* 2604 */             PokUtils.getAttributeDescription(warrTPIC.warrRel.getEntityGroup(), "ENDDATE", "ENDDATE") + ": </td><td>" + 
/* 2605 */             PokUtils.getAttributeValue(warrTPIC.warrRel, "ENDDATE", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2606 */         stringBuffer.append("<tr><td>" + warrTPIC.warrRel.getEntityGroup().getLongDescription() + ": " + 
/* 2607 */             PokUtils.getAttributeDescription(warrTPIC.warrRel.getEntityGroup(), "DEFWARR", "DEFWARR") + ": </td><td>" + 
/* 2608 */             PokUtils.getAttributeValue(warrTPIC.warrRel, "DEFWARR", "", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/* 2609 */         stringBuffer.append("<tr><td>" + warrTPIC.warrRel.getEntityGroup().getLongDescription() + ": " + 
/* 2610 */             PokUtils.getAttributeDescription(warrTPIC.warrRel.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST") + ": </td><td>" + 
/* 2611 */             PokUtils.getAttributeValue(warrTPIC.warrRel, "COUNTRYLIST", ", ", "<em>** Not Populated **</em>") + "</td></tr>" + NEWLINE);
/*      */       } 
/*      */     } else {
/*      */       
/* 2615 */       stringBuffer.append("<tr><td colspan='2'><b>No " + this.m_elist.getEntityGroup("WARR").getLongDescription() + " found</b></td></tr>" + NEWLINE);
/*      */     } 
/*      */     
/* 2618 */     stringBuffer.append("</table>" + NEWLINE);
/*      */     
/* 2620 */     addUserAndErrorMsg(stringBuffer.toString(), (String)null);
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
/*      */   private Hashtable getWarrTpicByCtry(EntityItem paramEntityItem, Set paramSet) throws SQLException, MiddlewareException {
/* 2643 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 2644 */     Vector<EntityItem> vector = getDownLinkEntityItems(paramEntityItem, "PRODSTRUCTWARR");
/* 2645 */     for (byte b = 0; b < vector.size(); b++) {
/* 2646 */       EntityItem entityItem1 = vector.elementAt(b);
/* 2647 */       String str = PokUtils.getAttributeFlagValue(entityItem1, "DEFWARR");
/* 2648 */       EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/* 2649 */       ArrayList<String> arrayList = new ArrayList();
/* 2650 */       getCountriesAsList(entityItem1, arrayList, -1);
/* 2651 */       addDebug("getWarrTpicByCtry " + entityItem1.getKey() + " " + entityItem2.getKey() + " defwarr " + str);
/*      */ 
/*      */       
/* 2654 */       if ("Y1".equals(str)) {
/*      */         
/* 2656 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 2657 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 2658 */         for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 2659 */           if (!arrayOfMetaFlag[b1].isExpired() && !arrayList.contains(arrayOfMetaFlag[b1].getFlagCode()))
/*      */           {
/* 2661 */             if (paramSet.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/* 2662 */               arrayList.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */             } else {
/*      */               
/* 2665 */               addDebug("getWarrTpicByCtry bypassing " + arrayOfMetaFlag[b1].getFlagCode() + " ps not offered there");
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 2671 */       Iterator<String> iterator = arrayList.iterator();
/* 2672 */       while (iterator.hasNext()) {
/* 2673 */         String str1 = iterator.next();
/* 2674 */         Vector<WarrTPIC> vector1 = (Vector)hashtable.get(str1);
/* 2675 */         if (vector1 == null) {
/* 2676 */           vector1 = new Vector();
/* 2677 */           hashtable.put(str1, vector1);
/*      */         } 
/* 2679 */         vector1.add(new WarrTPIC(str1, entityItem2, entityItem1));
/*      */       } 
/*      */     } 
/*      */     
/* 2683 */     return hashtable;
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
/*      */   private Hashtable getTpicByCtry(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3) throws SQLException, MiddlewareException, StopWarrException {
/* 2701 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 2702 */     if (this.isRPQ) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2707 */       String str1 = prodstructValidFrom(paramEntityItem1, paramEntityItem2, paramEntityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2713 */       String str2 = prodstructValidTo(paramEntityItem1, paramEntityItem2, paramEntityItem3);
/* 2714 */       addDebug("getTpicByCtry isRPQ " + paramEntityItem1.getKey() + " " + paramEntityItem2.getKey() + " " + paramEntityItem3.getKey() + " fromdate: " + str1 + " todate " + str2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2719 */       ArrayList arrayList = new ArrayList();
/* 2720 */       getCountriesAsList(paramEntityItem2, arrayList, -1);
/* 2721 */       addDebug("getTpicByCtry isRPQ feature ctrylist " + arrayList);
/*      */       
/* 2723 */       Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem3, "MODELAVAIL", "AVAIL");
/* 2724 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 2725 */       addDebug("getTpicByCtry isRPQ " + paramEntityItem3.getKey() + " availVct " + vector1.size() + " plannedavailVector " + vector2
/* 2726 */           .size());
/* 2727 */       if (vector2.size() > 0) {
/* 2728 */         ArrayList<?> arrayList1 = getCountriesAsList(vector2, -1);
/* 2729 */         addDebug("getTpicByCtry isRPQ model ctrylist " + arrayList1);
/* 2730 */         arrayList.retainAll(arrayList1);
/* 2731 */         addDebug("getTpicByCtry isRPQ intersection ctrylist " + arrayList);
/*      */       } 
/*      */       
/* 2734 */       Iterator<String> iterator = arrayList.iterator();
/* 2735 */       while (iterator.hasNext()) {
/* 2736 */         String str = iterator.next();
/* 2737 */         hashtable.put(str, new DQABRSTATUS.TPIC(str, str1, str2));
/*      */       } 
/* 2739 */       arrayList.clear();
/* 2740 */       vector1.clear();
/* 2741 */       vector2.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 2750 */     else if (!isOldGAProduct(paramEntityItem1, paramEntityItem3)) {
/* 2751 */       getGATpicByCtry(hashtable, paramEntityItem1, paramEntityItem2, paramEntityItem3);
/*      */     } else {
/*      */       
/* 2754 */       Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem3, "MODELAVAIL", "AVAIL");
/* 2755 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 2756 */       Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "149");
/* 2757 */       addDebug("getTpicByCtry isoldGA " + paramEntityItem1.getKey() + " " + paramEntityItem3.getKey() + " mdlavailVct " + vector1.size() + " mdlPlaAvailVct " + vector2
/* 2758 */           .size() + " mdlLoAvailVct " + vector3.size());
/*      */ 
/*      */       
/* 2761 */       if (vector2.size() > 0) {
/* 2762 */         getOldGATpicByCtry(hashtable, paramEntityItem1, paramEntityItem2, paramEntityItem3, vector2, vector3);
/*      */       } else {
/*      */         
/* 2765 */         ArrayList arrayList = new ArrayList();
/* 2766 */         getCountriesAsList(paramEntityItem2, arrayList, -1);
/* 2767 */         addDebug("getTpicByCtry oldGA feature ctrylist " + arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2772 */         String str1 = oldGaValidFrom(paramEntityItem1, paramEntityItem2, paramEntityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2778 */         String str2 = prodstructValidTo(paramEntityItem1, paramEntityItem2, paramEntityItem3);
/* 2779 */         addDebug("getTpicByCtry oldGA " + paramEntityItem1.getKey() + " " + paramEntityItem2.getKey() + " " + paramEntityItem3.getKey() + " fromdate: " + str1 + " todate " + str2);
/*      */ 
/*      */         
/* 2782 */         Iterator<String> iterator = arrayList.iterator();
/* 2783 */         while (iterator.hasNext()) {
/* 2784 */           String str = iterator.next();
/* 2785 */           hashtable.put(str, new DQABRSTATUS.TPIC(str, str1, str2));
/*      */         } 
/* 2787 */         arrayList.clear();
/*      */       } 
/* 2789 */       vector1.clear();
/* 2790 */       vector2.clear();
/* 2791 */       vector3.clear();
/*      */     } 
/*      */ 
/*      */     
/* 2795 */     return hashtable;
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
/*      */   private boolean isOldGAProduct(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws StopWarrException, SQLException, MiddlewareException {
/* 2817 */     boolean bool = false;
/* 2818 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem1, "OOFAVAIL", "AVAIL");
/* 2819 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 2820 */     if (vector2.size() == 0) {
/*      */       
/* 2822 */       String str = PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", "", "", false);
/* 2823 */       if (str.length() == 0) {
/*      */         
/* 2825 */         str = PokUtils.getAttributeValue(paramEntityItem2, "ANNDATE", "", "", false);
/* 2826 */         addDebug("isOldGAProduct " + paramEntityItem2.getKey() + " anndate " + str);
/*      */       } else {
/* 2828 */         addDebug("isOldGAProduct " + paramEntityItem1.getKey() + " anndate " + str);
/*      */       } 
/* 2830 */       if (str.length() > 0 && 
/* 2831 */         "2010-03-01".compareTo(str) >= 0) {
/* 2832 */         bool = true;
/*      */       }
/*      */       
/* 2835 */       if (!bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2841 */         if (paramEntityItem1.getEntityID() == getEntityID()) {
/* 2842 */           this.args[0] = "Planned Availability. Old data criteria was not met.";
/* 2843 */           createMessage(4, "MINIMUM_ERR", this.args);
/*      */         } else {
/* 2845 */           this.args[0] = getLD_NDN(paramEntityItem1);
/* 2846 */           this.args[1] = "Planned Availability. Old data criteria was not met.";
/* 2847 */           createMessage(4, "MINIMUM2_ERR", this.args);
/*      */         } 
/* 2849 */         vector1.clear();
/* 2850 */         vector2.clear();
/* 2851 */         throw new StopWarrException();
/*      */       } 
/*      */     } 
/* 2854 */     vector1.clear();
/* 2855 */     vector2.clear();
/* 2856 */     return bool;
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
/*      */   private void getOldGATpicByCtry(Hashtable<String, DQABRSTATUS.TPIC> paramHashtable, EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, Vector<?> paramVector1, Vector<?> paramVector2) throws SQLException, MiddlewareException {
/* 2885 */     ArrayList<?> arrayList1 = new ArrayList();
/* 2886 */     getCountriesAsList(paramEntityItem2, arrayList1, -1);
/* 2887 */     addDebug("getOldGATpicByCtry feature ctrylist " + arrayList1);
/*      */ 
/*      */ 
/*      */     
/* 2891 */     ArrayList<?> arrayList2 = getCountriesAsList(paramVector1, -1);
/* 2892 */     ArrayList arrayList = getCountriesAsList(paramVector2, -1);
/* 2893 */     addDebug("getOldGATpicByCtry model plactrylist " + arrayList2 + " mdlloctryList " + arrayList);
/* 2894 */     arrayList1.retainAll(arrayList2);
/* 2895 */     addDebug("getOldGATpicByCtry intersection ctrylist " + arrayList1);
/* 2896 */     if (arrayList1.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2901 */     String str1 = PokUtils.getAttributeValue(paramEntityItem1, "GENAVAILDATE", "", null, false);
/* 2902 */     String str2 = PokUtils.getAttributeValue(paramEntityItem1, "WTHDRWEFFCTVDATE", "", null, false);
/* 2903 */     addDebug("getOldGATpicByCtry " + paramEntityItem1.getKey() + " psfromdate " + str1 + " pstodate " + str2);
/* 2904 */     String str3 = PokUtils.getAttributeValue(paramEntityItem2, "GENAVAILDATE", "", "1980-01-01", false);
/* 2905 */     String str4 = PokUtils.getAttributeValue(paramEntityItem2, "WITHDRAWDATEEFF_T", "", null, false);
/* 2906 */     String str5 = PokUtils.getAttributeValue(paramEntityItem3, "WTHDRWEFFCTVDATE", "", null, false);
/* 2907 */     addDebug("getOldGATpicByCtry " + paramEntityItem2.getKey() + " fcfromdate " + str3 + " fctodate " + str4 + " mdltodate " + str5);
/*      */ 
/*      */ 
/*      */     
/* 2911 */     AttrComparator attrComparator = new AttrComparator("EFFECTIVEDATE");
/* 2912 */     Collections.sort(paramVector1, (Comparator<?>)attrComparator);
/* 2913 */     if (paramVector2.size() > 0) {
/* 2914 */       Collections.sort(paramVector2, (Comparator<?>)attrComparator);
/*      */     }
/*      */     int i;
/* 2917 */     for (i = paramVector1.size() - 1; i >= 0; i--) {
/* 2918 */       ArrayList arrayList3 = new ArrayList();
/* 2919 */       EntityItem entityItem = (EntityItem)paramVector1.elementAt(i);
/* 2920 */       getCountriesAsList(entityItem, arrayList3, -1);
/* 2921 */       addDebug("getOldGATpicByCtry mdl pla " + entityItem.getKey() + " ctrylist " + arrayList3);
/*      */       
/* 2923 */       arrayList3.retainAll(arrayList1);
/*      */       
/* 2925 */       if (arrayList3.size() == 0) {
/* 2926 */         addDebug("getOldGATpicByCtry no ctry matches found in " + entityItem.getKey() + " ctrylist and fcctryList " + arrayList1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2937 */         String str6 = str1;
/* 2938 */         if (str6 == null) {
/* 2939 */           String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 2940 */           addDebug("getOldGATpicByCtry mdl " + entityItem.getKey() + " fcfromdate " + str3 + " plafromdate " + str);
/* 2941 */           if (str.compareTo(str3) >= 0) {
/* 2942 */             str6 = str;
/*      */           } else {
/* 2944 */             str6 = str3;
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
/* 2956 */         String str7 = str2;
/* 2957 */         if (str7 == null) {
/* 2958 */           if (str5 != null && str4 != null) {
/* 2959 */             if (str5.compareTo(str4) < 0) {
/* 2960 */               str7 = str5;
/*      */             } else {
/* 2962 */               str7 = str4;
/*      */             } 
/* 2964 */           } else if (str5 != null) {
/* 2965 */             str7 = str5;
/*      */           }
/* 2967 */           else if (str4 != null) {
/* 2968 */             str7 = str4;
/*      */           } else {
/* 2970 */             str7 = "9999-12-31";
/*      */           } 
/*      */         }
/*      */         
/* 2974 */         Iterator<String> iterator = arrayList3.iterator();
/* 2975 */         while (iterator.hasNext()) {
/* 2976 */           String str8 = iterator.next();
/* 2977 */           DQABRSTATUS.TPIC tPIC = (DQABRSTATUS.TPIC)paramHashtable.get(str8);
/* 2978 */           if (tPIC != null) {
/* 2979 */             tPIC.fromDate = str6;
/* 2980 */             tPIC.fromItem = entityItem; continue;
/*      */           } 
/* 2982 */           String str9 = "9999-12-31";
/* 2983 */           if (!arrayList.contains(str8)) {
/* 2984 */             str9 = str7;
/*      */           }
/* 2986 */           tPIC = new DQABRSTATUS.TPIC(str8, str6, str9);
/* 2987 */           tPIC.fromItem = entityItem;
/* 2988 */           paramHashtable.put(str8, tPIC);
/*      */         } 
/*      */         
/* 2991 */         arrayList3.clear();
/*      */       } 
/* 2993 */     }  for (i = 0; i < paramVector2.size(); i++) {
/* 2994 */       ArrayList arrayList3 = new ArrayList();
/* 2995 */       EntityItem entityItem = (EntityItem)paramVector2.elementAt(i);
/* 2996 */       getCountriesAsList(entityItem, arrayList3, -1);
/*      */       
/* 2998 */       addDebug("getOldGATpicByCtry mdl lo " + entityItem.getKey() + " ctrylist " + arrayList3);
/*      */       
/* 3000 */       arrayList3.retainAll(arrayList1);
/*      */       
/* 3002 */       if (arrayList3.size() == 0) {
/* 3003 */         addDebug("getOldGATpicByCtry no ctry matches found in " + entityItem.getKey() + " ctrylist and fcctryList " + arrayList1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3014 */         String str = str2;
/* 3015 */         if (str == null) {
/* 3016 */           String str6 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "", false);
/* 3017 */           addDebug("getOldGATpicByCtry mdlloavail " + entityItem.getKey() + " fctodate " + str4 + " lotodate " + str6);
/* 3018 */           if (str4 != null) {
/* 3019 */             if (str6.compareTo(str4) < 0) {
/* 3020 */               str = str6;
/*      */             } else {
/* 3022 */               str = str4;
/*      */             } 
/*      */           } else {
/* 3025 */             str = str6;
/*      */           } 
/*      */         } 
/*      */         
/* 3029 */         Iterator<String> iterator = arrayList3.iterator();
/* 3030 */         while (iterator.hasNext()) {
/* 3031 */           String str6 = iterator.next();
/* 3032 */           DQABRSTATUS.TPIC tPIC = paramHashtable.get(str6);
/* 3033 */           if (tPIC != null) {
/* 3034 */             if (!tPIC.toDate.equals(str2)) {
/* 3035 */               tPIC.toDate = str;
/*      */             }
/* 3037 */             tPIC.toItem = entityItem;
/*      */           } 
/*      */         } 
/* 3040 */         arrayList3.clear();
/*      */       } 
/*      */     } 
/*      */     
/* 3044 */     arrayList1.clear();
/* 3045 */     arrayList2.clear();
/* 3046 */     arrayList.clear();
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
/*      */   private void getGATpicByCtry(Hashtable<String, DQABRSTATUS.TPIC> paramHashtable, EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3) throws SQLException, MiddlewareException {
/* 3069 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem1, "OOFAVAIL", "AVAIL");
/* 3070 */     Vector<?> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 3071 */     Vector<?> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/*      */     
/* 3073 */     addDebug("getTpicByCtry  " + paramEntityItem1.getKey() + " availVct: " + vector
/* 3074 */         .size() + " plaAvailVct: " + vector1.size() + " loAvailVct " + vector2.size());
/*      */     
/* 3076 */     AttrComparator attrComparator = new AttrComparator("EFFECTIVEDATE");
/* 3077 */     Collections.sort(vector1, (Comparator<?>)attrComparator);
/* 3078 */     if (vector2.size() > 0) {
/* 3079 */       Collections.sort(vector2, (Comparator<?>)attrComparator);
/*      */     }
/*      */     int i;
/* 3082 */     for (i = vector1.size() - 1; i >= 0; i--) {
/* 3083 */       ArrayList arrayList = new ArrayList();
/* 3084 */       EntityItem entityItem = (EntityItem)vector1.elementAt(i);
/* 3085 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "", false);
/* 3086 */       getCountriesAsList(entityItem, arrayList, -1);
/*      */       
/* 3088 */       Iterator<String> iterator = arrayList.iterator();
/* 3089 */       while (iterator.hasNext()) {
/* 3090 */         String str1 = iterator.next();
/* 3091 */         DQABRSTATUS.TPIC tPIC = (DQABRSTATUS.TPIC)paramHashtable.get(str1);
/* 3092 */         if (tPIC != null) {
/* 3093 */           tPIC.fromDate = str;
/* 3094 */           tPIC.fromItem = entityItem; continue;
/*      */         } 
/* 3096 */         tPIC = new DQABRSTATUS.TPIC(str1, str);
/* 3097 */         tPIC.fromItem = entityItem;
/* 3098 */         paramHashtable.put(str1, tPIC);
/*      */       } 
/*      */       
/* 3101 */       arrayList.clear();
/*      */     } 
/* 3103 */     for (i = 0; i < vector2.size(); i++) {
/* 3104 */       ArrayList arrayList = new ArrayList();
/* 3105 */       EntityItem entityItem = (EntityItem)vector2.elementAt(i);
/* 3106 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "", false);
/* 3107 */       getCountriesAsList(entityItem, arrayList, -1);
/*      */       
/* 3109 */       Iterator<String> iterator = arrayList.iterator();
/* 3110 */       while (iterator.hasNext()) {
/* 3111 */         String str1 = iterator.next();
/* 3112 */         DQABRSTATUS.TPIC tPIC = paramHashtable.get(str1);
/* 3113 */         if (tPIC != null) {
/* 3114 */           tPIC.toDate = str;
/* 3115 */           tPIC.toItem = entityItem;
/*      */         } 
/*      */       } 
/* 3118 */       arrayList.clear();
/*      */     } 
/*      */ 
/*      */     
/* 3122 */     vector.clear();
/* 3123 */     vector1.clear();
/* 3124 */     vector2.clear();
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
/*      */   private void checkRPQFeature(EntityItem paramEntityItem1, EntityItem paramEntityItem2, int paramInt) throws SQLException, MiddlewareException {
/* 3148 */     addHeading(3, paramEntityItem2.getEntityGroup().getLongDescription() + " RPQ Checks:");
/*      */ 
/*      */     
/* 3151 */     addDebug(paramEntityItem2.getKey() + " was an RPQ FCTYPE: " + getAttributeFlagEnabledValue(paramEntityItem2, "FCTYPE"));
/*      */ 
/*      */ 
/*      */     
/* 3155 */     checkCanNotBeEarlier(paramEntityItem1, "ANNDATE", paramEntityItem2, "FIRSTANNDATE", paramInt);
/*      */ 
/*      */ 
/*      */     
/* 3159 */     checkCanNotBeEarlier(paramEntityItem1, "GENAVAILDATE", paramEntityItem2, "GENAVAILDATE", paramInt);
/*      */ 
/*      */ 
/*      */     
/* 3163 */     checkCanNotBeLater(paramEntityItem1, "WITHDRAWDATE", paramEntityItem2, "WITHDRAWANNDATE_T", paramInt);
/*      */ 
/*      */ 
/*      */     
/* 3167 */     checkCanNotBeLater(paramEntityItem1, "WTHDRWEFFCTVDATE", paramEntityItem2, "WITHDRAWDATEEFF_T", paramInt);
/*      */     
/* 3169 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem1, "ORDERCODE");
/* 3170 */     addDebug("checkRPQFeatures " + paramEntityItem1.getKey() + " ordercode " + str);
/* 3171 */     if ("5957".equals(str)) {
/* 3172 */       addDebug("checkRPQFeatures testing PRODSTRUCT.WITHDRAWDATE cannot be later than MODEL.WITHDRAWDATE");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3177 */       EntityItem entityItem = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/* 3178 */       checkCanNotBeLater(paramEntityItem1, "WITHDRAWDATE", entityItem, "WITHDRAWDATE", paramInt);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3183 */     int i = getCount("OOFAVAIL");
/* 3184 */     if (i > 0) {
/* 3185 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 3186 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3187 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */         
/* 3189 */         this.args[0] = getLD_NDN(entityItem);
/* 3190 */         createMessage(4, "RPQ_AVAIL_ERR", this.args);
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
/*      */   private void checkOOFAVAILAvails(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, String paramString) throws SQLException, MiddlewareException {
/* 3338 */     int i = getCheck_W_W_E(paramString);
/*      */ 
/*      */     
/* 3341 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */ 
/*      */     
/* 3344 */     Vector vector1 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "146");
/* 3345 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "171");
/*      */ 
/*      */     
/* 3348 */     Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "149");
/* 3349 */     Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "172");
/*      */     
/* 3351 */     Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "143");
/* 3352 */     Vector vector6 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "200");
/* 3353 */     Vector vector7 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "151");
/* 3354 */     ArrayList arrayList = new ArrayList();
/*      */     
/* 3356 */     getCountriesAsList(paramEntityItem2, arrayList, i);
/* 3357 */     addDebug("checkOOFAVAILAvails lastOrderAvailVct: " + vector3.size() + " mesLastOrderAvailVct: " + vector4
/* 3358 */         .size() + " plannedAvailVct: " + vector1
/* 3359 */         .size() + " mesPlannedAvailVct: " + vector2
/* 3360 */         .size() + " firstOrderAvailVct: " + vector5
/* 3361 */         .size() + " eomAvailVct: " + vector6
/* 3362 */         .size() + " eosAvailVct: " + vector7
/* 3363 */         .size() + " featCtrylist " + arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3368 */     addHeading(3, entityGroup.getLongDescription() + " Planned Avail and First Order Avail count Checks:");
/* 3369 */     checkPlannedAvailsOrFirstOrderAvailsExist(vector1, vector5, getCheckLevel(3, paramEntityItem3, "ANNDATE"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3376 */     addHeading(3, entityGroup.getLongDescription() + " Planned Avail Checks:");
/* 3377 */     checkOOFMesPlaAndPlaAvails(paramEntityItem1, paramEntityItem2, paramEntityItem3, paramString, vector1, "146", arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3382 */     addHeading(3, entityGroup.getLongDescription() + " MES Planned Avail Checks:");
/* 3383 */     checkOOFMesPlaAndPlaAvails(paramEntityItem1, paramEntityItem2, paramEntityItem3, paramString, vector2, "171", arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3391 */     addHeading(3, entityGroup.getLongDescription() + " First Order Avail Checks:");
/* 3392 */     checkOOFAVAILFOAvails(paramEntityItem1, paramEntityItem2, paramEntityItem3, vector5, vector1, vector2, i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3398 */     addHeading(3, entityGroup.getLongDescription() + " Last Order Avail Checks:");
/* 3399 */     checkOOFAVAILLOAvails(paramEntityItem1, paramEntityItem2, paramEntityItem3, vector3, vector1, arrayList, "149", paramString);
/*      */ 
/*      */     
/* 3402 */     addHeading(3, entityGroup.getLongDescription() + " MES Last Order Avail Checks:");
/* 3403 */     checkOOFAVAILLOAvails(paramEntityItem1, paramEntityItem2, paramEntityItem3, vector4, vector1, arrayList, "172", paramString);
/*      */     
/* 3405 */     checkOOFAVAILLOAvails(paramEntityItem1, paramEntityItem2, paramEntityItem3, vector4, vector2, arrayList, "172", paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3412 */     addHeading(3, entityGroup.getLongDescription() + " End of Marketing Avail Checks:");
/* 3413 */     checkOOFAVAILEOMAvails(paramEntityItem1, paramEntityItem2, paramEntityItem3, vector6, vector1, vector2, paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3421 */     addHeading(3, entityGroup.getLongDescription() + " End of Service Avail Checks:");
/* 3422 */     checkOOFAVAILEOSAvails(paramEntityItem2, paramEntityItem3, vector7, vector3, vector4, paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3427 */     addHeading(3, entityGroup.getLongDescription() + " Model Avail Checks:");
/* 3428 */     checkModelAvails(paramEntityItem1, paramEntityItem3, vector1, vector2, vector3, vector4, paramString);
/*      */     
/* 3430 */     vector3.clear();
/* 3431 */     vector1.clear();
/* 3432 */     vector6.clear();
/* 3433 */     vector7.clear();
/* 3434 */     vector5.clear();
/* 3435 */     arrayList.clear();
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
/*      */   private void checkOOFMesPlaAndPlaAvails(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, String paramString1, Vector<EntityItem> paramVector, String paramString2, ArrayList paramArrayList) throws SQLException, MiddlewareException {
/* 3482 */     int i = getCheck_W_W_E(paramString1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3493 */     if ("146".equals(paramString2)) {
/* 3494 */       checkPlannedAvailsStatus(paramVector, paramEntityItem1, getCheckLevel(3, paramEntityItem3, "ANNDATE"));
/*      */     }
/*      */     
/* 3497 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 3498 */       EntityItem entityItem = paramVector.elementAt(b);
/*      */ 
/*      */       
/* 3501 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem2, "FIRSTANNDATE", i);
/*      */ 
/*      */       
/* 3504 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem2, "GENAVAILDATE", i);
/*      */ 
/*      */       
/* 3507 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem1, "ANNDATE", i);
/*      */ 
/*      */       
/* 3510 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem1, "GENAVAILDATE", i);
/*      */ 
/*      */       
/* 3513 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem3, "ANNDATE", i);
/*      */ 
/*      */       
/* 3516 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem3, "GENAVAILDATE", i);
/*      */ 
/*      */       
/* 3519 */       checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem2, paramArrayList, i);
/*      */ 
/*      */ 
/*      */       
/* 3523 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 3524 */       addDebug("checkOOFAVAILPLAAvails " + entityItem.getKey() + " annVct " + vector.size());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3529 */       if (checkAvailAnnType(entityItem, vector, 4))
/*      */       {
/* 3531 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 3532 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 3533 */           String str = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 3534 */           addDebug("checkOOFAVAILPLAAvails " + entityItem1.getKey() + " anntypeFlag " + str);
/* 3535 */           if (!"19".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3540 */             this.args[0] = getLD_NDN(entityItem);
/* 3541 */             this.args[1] = getLD_NDN(entityItem1);
/* 3542 */             createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */           }
/*      */           else {
/*      */             
/* 3546 */             checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem2, "FIRSTANNDATE", i);
/*      */             
/* 3548 */             checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem1, "ANNDATE", i);
/*      */             
/* 3550 */             checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem3, "ANNDATE", i);
/*      */           } 
/*      */         }  } 
/* 3553 */       vector.clear();
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
/*      */   private void checkOOFAVAILFOAvails(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, Vector<EntityItem> paramVector1, Vector paramVector2, Vector paramVector3, int paramInt) throws SQLException, MiddlewareException {
/* 3593 */     if (paramVector1.size() > 0) {
/* 3594 */       for (byte b = 0; b < paramVector1.size(); b++) {
/* 3595 */         EntityItem entityItem = paramVector1.elementAt(b);
/*      */ 
/*      */         
/* 3598 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem2, "FIRSTANNDATE", paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3604 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem1, "ANNDATE", paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3610 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem3, "ANNDATE", paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3619 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 3620 */         addDebug("checkOOFAVAILFOAvails " + entityItem.getKey() + " annVct " + vector.size());
/* 3621 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 3622 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 3623 */           String str = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 3624 */           addDebug("checkOOFAVAILFOAvails " + entityItem1.getKey() + " anntypeFlag " + str);
/* 3625 */           if (!"19".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3630 */             this.args[0] = getLD_NDN(entityItem);
/* 3631 */             this.args[1] = getLD_NDN(entityItem1);
/* 3632 */             createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 3637 */             checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem2, "FIRSTANNDATE", paramInt);
/*      */             
/* 3639 */             checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem1, "ANNDATE", paramInt);
/*      */             
/* 3641 */             checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem3, "ANNDATE", paramInt);
/*      */           } 
/* 3643 */         }  vector.clear();
/*      */       } 
/*      */ 
/*      */       
/* 3647 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 3648 */       boolean bool1 = getAvailByOSN(hashtable1, paramVector2, true, 3);
/* 3649 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 3650 */       boolean bool2 = getAvailByOSN(hashtable2, paramVector3, true, 3);
/* 3651 */       Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/* 3652 */       boolean bool3 = getAvailByOSN(hashtable3, paramVector1, true, 3);
/* 3653 */       addDebug("checkOOFAVAILFOAvails foOsnErrors " + bool3 + " foAvailOSNTbl.keys " + hashtable3
/* 3654 */           .keySet() + " plaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1
/* 3655 */           .keySet() + " mesPlaOsnErrors " + bool2 + " mesPlaAvailOSNTbl.keys " + hashtable2
/* 3656 */           .keySet());
/*      */ 
/*      */       
/* 3659 */       if (paramVector2 != null && paramVector2.size() > 0 && 
/* 3660 */         !bool1 && !bool3)
/*      */       {
/* 3662 */         checkAvailCtryByOSN(hashtable3, hashtable1, "PS_MISSING_PLA_OSNCTRY_ERR", (EntityItem)null, true, paramInt);
/*      */       }
/*      */       
/* 3665 */       if (paramVector3 != null && paramVector3.size() > 0 && 
/* 3666 */         !bool2 && !bool3)
/*      */       {
/* 3668 */         checkAvailCtryByOSN(hashtable3, hashtable2, "MISSING_MES_PLA_OSNCTRY_ERR", (EntityItem)null, true, paramInt);
/*      */       }
/*      */       
/* 3671 */       hashtable1.clear();
/* 3672 */       hashtable2.clear();
/* 3673 */       hashtable3.clear();
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
/*      */   private void checkOOFAVAILLOAvails(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, Vector<EntityItem> paramVector1, Vector paramVector2, ArrayList paramArrayList, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 3729 */     int i = getCheck_W_W_E(paramString2);
/*      */     
/* 3731 */     if (paramVector1.size() > 0) {
/* 3732 */       for (byte b = 0; b < paramVector1.size(); b++) {
/* 3733 */         EntityItem entityItem = paramVector1.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3739 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem2, "WITHDRAWDATEEFF_T", i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3745 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem1, "WTHDRWEFFCTVDATE", i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3751 */         if (this.isInitialOrderCode) {
/*      */           
/* 3753 */           checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem3, "WTHDRWEFFCTVDATE", i);
/*      */         } else {
/* 3755 */           addDebug("checkOOFAVAILLOAvails BYPASSING AVAIL and MODEL.WTHDRWEFFCTVDATE ordercode not initial");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 3760 */         checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem2, paramArrayList, i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3774 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 3775 */         addDebug("checkOOFAVAILLOAvails " + entityItem.getKey() + " annVct " + vector.size());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3781 */         if (checkAvailAnnType(entityItem, vector, 4))
/*      */         {
/* 3783 */           for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 3784 */             EntityItem entityItem1 = vector.elementAt(b1);
/* 3785 */             String str = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 3786 */             addDebug("checkOOFAVAILLOAvails " + entityItem1.getKey() + " anntypeFlag " + str);
/* 3787 */             if (!"14".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3792 */               this.args[0] = getLD_NDN(entityItem);
/* 3793 */               this.args[1] = getLD_NDN(entityItem1);
/* 3794 */               createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 3799 */               checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem2, "WITHDRAWANNDATE_T", i);
/*      */               
/* 3801 */               checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem1, "WITHDRAWDATE", i);
/*      */               
/* 3803 */               if (this.isInitialOrderCode) {
/*      */ 
/*      */                 
/* 3806 */                 checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem3, "WITHDRAWDATE", i);
/*      */               } else {
/*      */                 
/* 3809 */                 addDebug("checkOOFAVAILLOAvails BYPASSING ANNDATE and MODEL.WITHDRAWDATE ordercode not initial");
/*      */               } 
/*      */             } 
/*      */           }  } 
/* 3813 */         vector.clear();
/*      */       } 
/*      */ 
/*      */       
/* 3817 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 3818 */       boolean bool1 = getAvailByOSN(hashtable1, paramVector2, true, 3);
/*      */       
/* 3820 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 3821 */       boolean bool2 = getAvailByOSN(hashtable2, paramVector1, true, 3);
/* 3822 */       if ("149".equals(paramString1)) {
/* 3823 */         addDebug("checkOOFAVAILLOAvails LoOsnErrors " + bool2 + " LoAvailOSNTbl.keys " + hashtable2
/* 3824 */             .keySet() + " plaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable2
/* 3825 */             .keySet());
/*      */       } else {
/* 3827 */         addDebug("checkOOFAVAILLOAvails mesLoOsnErrors " + bool2 + " mesLoAvailOSNTbl.keys " + hashtable2
/* 3828 */             .keySet() + " mesPlaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1
/* 3829 */             .keySet());
/*      */       } 
/*      */ 
/*      */       
/* 3833 */       if (!bool1 && !bool2)
/*      */       {
/* 3835 */         if ("149".equals(paramString1)) {
/* 3836 */           checkAvailCtryByOSN(hashtable2, hashtable1, "PS_MISSING_PLA_OSNCTRY_ERR", (EntityItem)null, true, getCheckLevel(i, paramEntityItem3, "ANNDATE"));
/*      */         } else {
/* 3838 */           checkAvailCtryByOSN(hashtable2, hashtable1, "MISSING_MES_PLA_OSNCTRY_ERR", (EntityItem)null, true, getCheckLevel(i, paramEntityItem3, "ANNDATE"));
/*      */         } 
/*      */       }
/* 3841 */       hashtable1.clear();
/* 3842 */       hashtable2.clear();
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
/*      */   private void checkOOFAVAILEOMAvails(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, Vector<EntityItem> paramVector1, Vector paramVector2, Vector paramVector3, String paramString) throws SQLException, MiddlewareException {
/* 3958 */     int i = getCheck_W_W_E(paramString);
/*      */     
/* 3960 */     if (paramVector1.size() > 0) {
/* 3961 */       for (byte b = 0; b < paramVector1.size(); b++) {
/* 3962 */         EntityItem entityItem = paramVector1.elementAt(b);
/*      */         
/* 3964 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem2, "WITHDRAWANNDATE_T", i);
/*      */ 
/*      */         
/* 3967 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem1, "WITHDRAWDATE", i);
/*      */         
/* 3969 */         if (this.isInitialOrderCode) {
/*      */ 
/*      */           
/* 3972 */           checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem3, "WITHDRAWDATE", i);
/*      */         } else {
/*      */           
/* 3975 */           addDebug("checkOOFAVAILEOMAvails BYPASSING AVAIL and MODEL.WITHDRAWDATE ordercode not initial");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3981 */         String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 3982 */         addDebug("checkOOFAVAILEOMAvails " + entityItem.getKey() + " availAnntypeFlag " + str);
/* 3983 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */         
/* 3985 */         if (str == null) {
/* 3986 */           str = "RFA";
/*      */         }
/* 3988 */         if ("RFA".equals(str)) {
/*      */ 
/*      */           
/* 3991 */           addDebug("checkOOFAVAILEOMAvails " + entityItem.getKey() + " annVct " + vector.size());
/* 3992 */           for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 3993 */             EntityItem entityItem1 = vector.elementAt(b1);
/* 3994 */             String str1 = PokUtils.getAttributeFlagValue(entityItem1, "ANNTYPE");
/* 3995 */             addDebug("checkOOFAVAILEOMAvails " + entityItem1.getKey() + " anntypeFlag " + str1);
/* 3996 */             if (!"14".equals(str1))
/*      */             {
/*      */ 
/*      */               
/* 4000 */               this.args[0] = getLD_NDN(entityItem);
/* 4001 */               this.args[1] = getLD_NDN(entityItem1);
/* 4002 */               createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 4007 */               checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem2, "WITHDRAWANNDATE_T", i);
/*      */               
/* 4009 */               if (this.isInitialOrderCode) {
/*      */ 
/*      */                 
/* 4012 */                 checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem3, "WITHDRAWDATE", i);
/*      */               } else {
/*      */                 
/* 4015 */                 addDebug("checkOOFAVAILEOMAvails BYPASSING ANNDATE and MODEL.WITHDRAWDATE ordercode not initial");
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           } 
/*      */         } else {
/*      */           
/* 4023 */           checkAvailAnnType(entityItem, vector, 4);
/*      */         } 
/*      */         
/* 4026 */         vector.clear();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4031 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 4032 */       boolean bool1 = getAvailByOSN(hashtable1, paramVector2, true, 3);
/*      */       
/* 4034 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 4035 */       boolean bool2 = getAvailByOSN(hashtable2, paramVector3, true, 3);
/* 4036 */       Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/* 4037 */       boolean bool3 = getAvailByOSN(hashtable3, paramVector1, true, 3);
/* 4038 */       addDebug("checkOOFAVAILEOMAvails eomOsnErrors " + bool3 + " eomAvailOSNTbl.keys " + hashtable3
/* 4039 */           .keySet() + " plaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1
/* 4040 */           .keySet() + " mesPlaOsnErrors " + bool2 + " mesPlaAvailOSNTbl.keys " + hashtable2
/* 4041 */           .keySet());
/*      */ 
/*      */       
/* 4044 */       if (!bool1 && !bool3)
/*      */       {
/* 4046 */         checkAvailCtryByOSN(hashtable3, hashtable1, "PS_MISSING_PLA_OSNCTRY_ERR", (EntityItem)null, true, getCheckLevel(i, paramEntityItem3, "ANNDATE"));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 4051 */       if (paramVector3 != null && paramVector3.size() > 0 && !bool2 && !bool3) {
/* 4052 */         checkAvailCtryByOSN(hashtable3, hashtable2, "MISSING_MES_PLA_OSNCTRY_ERR", (EntityItem)null, true, getCheckLevel(i, paramEntityItem3, "ANNDATE"));
/*      */       }
/* 4054 */       hashtable1.clear();
/* 4055 */       hashtable2.clear();
/* 4056 */       hashtable3.clear();
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
/*      */   private void checkOOFAVAILEOSAvails(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector<EntityItem> paramVector1, Vector paramVector2, Vector paramVector3, String paramString) throws SQLException, MiddlewareException {
/* 4100 */     int i = getCheck_W_W_E(paramString);
/*      */     
/* 4102 */     if (paramVector1.size() > 0) {
/* 4103 */       int j = getCheckLevel(i, paramEntityItem2, "ANNDATE");
/* 4104 */       for (byte b = 0; b < paramVector1.size(); b++) {
/* 4105 */         EntityItem entityItem = paramVector1.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4130 */         String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 4131 */         addDebug("checkOOFAVAILEOSAvails " + entityItem.getKey() + " availAnntypeFlag " + str);
/* 4132 */         Vector vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*      */         
/* 4134 */         if (str == null) {
/* 4135 */           str = "RFA";
/*      */         }
/*      */ 
/*      */         
/* 4139 */         if (!"RFA".equals(str))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4179 */           checkAvailAnnType(entityItem, vector, 4);
/*      */         }
/*      */         
/* 4182 */         vector.clear();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4187 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 4188 */       boolean bool1 = getAvailByOSN(hashtable1, paramVector2, true, 3);
/* 4189 */       addDebug("checkOOFAVAILEOSAvails loOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1
/* 4190 */           .keySet());
/*      */ 
/*      */       
/* 4193 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 4194 */       boolean bool2 = getAvailByOSN(hashtable2, paramVector3, true, 3);
/* 4195 */       addDebug("checkOOFAVAILEOSAvails mesLoOsnErrors " + bool2 + " mesLoAvailOSNTbl.keys " + hashtable2
/* 4196 */           .keySet());
/*      */       
/* 4198 */       Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/* 4199 */       boolean bool3 = getAvailByOSN(hashtable3, paramVector1, true, 3);
/* 4200 */       addDebug("checkOOFAVAILEOSAvails  eosOsnErrors " + bool3 + " eosAvailOSNTbl.keys " + hashtable3
/* 4201 */           .keySet());
/*      */       
/* 4203 */       if (paramVector2 != null && paramVector2.size() > 0 && !bool1 && !bool3) {
/*      */         
/* 4205 */         checkAvailDatesByCtryByOSN(hashtable3, hashtable1, (EntityItem)null, 1, j, "", false);
/*      */         
/* 4207 */         checkAvailCtryByOSN(hashtable3, hashtable1, "PS_MISSING_LO_OSNCTRY_ERR", (EntityItem)null, true, j);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4213 */       if (paramVector3 != null && paramVector3.size() > 0 && !bool2 && !bool3) {
/* 4214 */         checkAvailDatesByCtryByOSN(hashtable3, hashtable2, (EntityItem)null, 1, j, "", false);
/* 4215 */         checkAvailCtryByOSN(hashtable3, hashtable2, "MISSING_MES_LO_OSNCTRY_ERR", (EntityItem)null, true, j);
/*      */       } 
/* 4217 */       hashtable1.clear();
/* 4218 */       hashtable2.clear();
/* 4219 */       hashtable3.clear();
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
/*      */   private void checkModelAvails(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector paramVector1, Vector paramVector2, Vector paramVector3, Vector paramVector4, String paramString) throws MiddlewareException, SQLException {
/* 4329 */     checkPsModelPlaFOAvail(paramEntityItem2, paramString, paramVector1, paramVector2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4334 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem1, "ORDERCODE");
/* 4335 */     addDebug("(76.4) ORDERCODE " + str);
/* 4336 */     if ("5957".equals(str))
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
/* 4349 */       checkPsModelLastOrderAvail(paramEntityItem2, paramString, paramVector3, paramVector4, paramVector1, paramVector2);
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
/*      */   private void checkPsModelLastOrderAvail(EntityItem paramEntityItem, String paramString, Vector paramVector1, Vector paramVector2, Vector paramVector3, Vector paramVector4) throws MiddlewareException, SQLException {
/* 4375 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Model Last Order Avail Checks:");
/*      */     
/* 4377 */     EntityGroup entityGroup = this.mdlList.getEntityGroup("AVAIL");
/* 4378 */     Vector vector = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "149");
/*      */     
/* 4380 */     if (paramVector1.size() > 0 && vector.size() > 0) {
/*      */       
/* 4382 */       int i = getCheckLevel(getCheck_W_W_E(paramString), paramEntityItem, "ANNDATE");
/* 4383 */       int j = getCheckLevel(4, paramEntityItem, "ANNDATE");
/*      */       
/* 4385 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 4386 */       boolean bool1 = getAvailByOSN(hashtable1, paramVector1, true, 3);
/*      */       
/* 4388 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 4389 */       boolean bool2 = getAvailByOSN(hashtable2, vector, true, 3);
/* 4390 */       addDebug("checkPsModelLastOrderAvail  loOsnErrors " + bool1 + " loAvailOSNTbl.keys " + hashtable1
/* 4391 */           .keySet() + " mdlLoOsnErrors " + bool2 + " mdlLoAvailOSNTbl.keys " + hashtable2
/* 4392 */           .keySet());
/*      */       
/* 4394 */       if (!bool2 && !bool1)
/*      */       {
/*      */ 
/*      */         
/* 4398 */         checkAvailDatesByCtryByOSN(hashtable1, hashtable2, (EntityItem)null, 2, i, getLD_NDN(paramEntityItem), false);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4408 */       checkModelAvailMatchPsAvailCtry(paramEntityItem, paramVector3, true, hashtable1, true, hashtable2, true, j);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4413 */       hashtable1.clear();
/* 4414 */       hashtable2.clear();
/*      */     } else {
/* 4416 */       addDebug("checkPsModelLastOrderAvail no PS-LastorderAvailVct to check");
/*      */     } 
/* 4418 */     if (paramVector2.size() > 0 && vector.size() > 0) {
/*      */       
/* 4420 */       int i = getCheckLevel(getCheck_W_W_E(paramString), paramEntityItem, "ANNDATE");
/* 4421 */       int j = getCheckLevel(4, paramEntityItem, "ANNDATE");
/*      */       
/* 4423 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 4424 */       boolean bool1 = getAvailByOSN(hashtable1, paramVector2, true, 3);
/*      */       
/* 4426 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 4427 */       boolean bool2 = getAvailByOSN(hashtable2, vector, true, 3);
/* 4428 */       addDebug("checkPsModelLastOrderAvail  mesLoOsnErrors " + bool1 + " mesLoAvailOSNTbl.keys " + hashtable1
/* 4429 */           .keySet() + " mdlLoOsnErrors " + bool2 + " mdlLoAvailOSNTbl.keys " + hashtable2
/* 4430 */           .keySet());
/*      */       
/* 4432 */       if (hashtable1.size() > 0 && !bool2 && !bool1)
/*      */       {
/*      */ 
/*      */         
/* 4436 */         checkAvailDatesByCtryByOSN(hashtable1, hashtable2, (EntityItem)null, 2, i, getLD_NDN(paramEntityItem), false);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4445 */       if (hashtable1.size() > 0) {
/* 4446 */         checkModelAvailMatchPsAvailCtry(paramEntityItem, paramVector3, true, hashtable1, true, hashtable2, true, j);
/*      */       }
/*      */ 
/*      */       
/* 4450 */       hashtable1.clear();
/* 4451 */       hashtable2.clear();
/*      */     } else {
/* 4453 */       addDebug("checkPsModelLastOrderAvail no PS-lastorderAvailVct to check");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4521 */     vector.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkModelAvailMatchPsAvailCtry(EntityItem paramEntityItem, Vector paramVector, boolean paramBoolean1, Hashtable paramHashtable1, boolean paramBoolean2, Hashtable paramHashtable2, boolean paramBoolean3, int paramInt) throws MiddlewareException, SQLException {
/* 4527 */     if (paramVector.size() < 1) {
/*      */       return;
/*      */     }
/* 4530 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 4531 */     boolean bool = getAvailByOSN(hashtable, paramVector, true, 3);
/*      */     
/* 4533 */     if (paramBoolean1) {
/* 4534 */       addDebug("checkPsModelLastOrderAvail  plaOsnErrors " + bool + " plaAvailOSNTbl.keys " + hashtable
/* 4535 */           .keySet());
/*      */     } else {
/* 4537 */       addDebug("checkPsModelLastOrderAvail  mesPlaOsnErrors " + bool + " mesPlaAvailOSNTbl.keys " + hashtable
/* 4538 */           .keySet());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4548 */     Set set = hashtable.keySet();
/* 4549 */     Iterator<String> iterator = set.iterator();
/* 4550 */     while (iterator.hasNext()) {
/* 4551 */       String str = iterator.next();
/*      */       
/* 4553 */       Vector<EntityItem> vector = (Vector)hashtable.get(str);
/* 4554 */       Vector vector1 = (Vector)paramHashtable2.get(str);
/* 4555 */       if (vector1 == null) {
/* 4556 */         addDebug("checkPsModelLastOrderAvail no mdllo avails to check for osn " + str);
/*      */         continue;
/*      */       } 
/* 4559 */       Vector vector2 = (Vector)paramHashtable1.get(str);
/* 4560 */       if (vector2 == null) {
/* 4561 */         vector2 = new Vector();
/*      */       }
/* 4563 */       ArrayList arrayList = getCountriesAsList(vector2, paramInt);
/* 4564 */       if (paramBoolean2) {
/* 4565 */         addDebug("checkPsModelLastOrderAvail osn " + str + " PS-lastOrderAvlCtry " + arrayList);
/*      */       } else {
/* 4567 */         addDebug("checkPsModelLastOrderAvail osn " + str + " PS-mesLastOrderAvlCtry " + arrayList);
/*      */       } 
/*      */       
/* 4570 */       Hashtable hashtable1 = getAvailByCountry(vector1, paramInt);
/* 4571 */       if (paramBoolean3) {
/* 4572 */         addDebug("checkPsModelLastOrderAvail mdlLoAvailCtryTbl: " + hashtable1);
/*      */       } else {
/* 4574 */         addDebug("checkPsModelLastOrderAvail mdlMesLoAvailCtryTbl: " + hashtable1);
/*      */       } 
/*      */ 
/*      */       
/* 4578 */       for (byte b = 0; b < vector.size(); b++) {
/* 4579 */         EntityItem entityItem = vector.elementAt(b);
/* 4580 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(entityItem, "COUNTRYLIST", paramInt);
/* 4581 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */           
/* 4583 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*      */           
/* 4585 */           Vector<EntityItem> vector3 = new Vector(); byte b1;
/* 4586 */           for (b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 4587 */             if (arrayOfMetaFlag[b1].isSelected() && 
/* 4588 */               !arrayList.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/* 4589 */               if (paramBoolean1) {
/* 4590 */                 if (paramBoolean2) {
/* 4591 */                   addDebug("checkPsModelLastOrderAvail PS-plannedavail:" + entityItem.getKey() + " No PS lastorderavail for ctry " + arrayOfMetaFlag[b1]
/* 4592 */                       .getFlagCode());
/*      */                 } else {
/* 4594 */                   addDebug("checkPsModelLastOrderAvail PS-plannedavail:" + entityItem.getKey() + " No PS meslastorderavail for ctry " + arrayOfMetaFlag[b1]
/* 4595 */                       .getFlagCode());
/*      */                 }
/*      */               
/*      */               }
/* 4599 */               else if (paramBoolean2) {
/* 4600 */                 addDebug("checkPsModelLastOrderAvail PS-mesplannedavail:" + entityItem.getKey() + " No PS lastorderavail for ctry " + arrayOfMetaFlag[b1]
/* 4601 */                     .getFlagCode());
/*      */               } else {
/* 4603 */                 addDebug("checkPsModelLastOrderAvail PS-mesplannedavail:" + entityItem.getKey() + " No PS meslastorderavail for ctry " + arrayOfMetaFlag[b1]
/* 4604 */                     .getFlagCode());
/*      */               } 
/*      */ 
/*      */               
/* 4608 */               EntityItem entityItem1 = (EntityItem)hashtable1.get(arrayOfMetaFlag[b1].getFlagCode());
/* 4609 */               if (entityItem1 != null) {
/* 4610 */                 if (paramBoolean1) {
/* 4611 */                   if (paramBoolean3) {
/* 4612 */                     addDebug("checkPsModelLastOrderAvail PS-plannedavail:" + entityItem.getKey() + " MODEL-lastorderavail for ctry " + arrayOfMetaFlag[b1]
/* 4613 */                         .getFlagCode());
/*      */                   } else {
/* 4615 */                     addDebug("checkPsModelLastOrderAvail PS-plannedavail:" + entityItem.getKey() + " MODEL-meslastorderavail for ctry " + arrayOfMetaFlag[b1]
/* 4616 */                         .getFlagCode());
/*      */                   }
/*      */                 
/* 4619 */                 } else if (paramBoolean3) {
/* 4620 */                   addDebug("checkPsModelLastOrderAvail PS-mesplannedavail:" + entityItem.getKey() + " MODEL-lastorderavail for ctry " + arrayOfMetaFlag[b1]
/* 4621 */                       .getFlagCode());
/*      */                 } else {
/* 4623 */                   addDebug("checkPsModelLastOrderAvail PS-mesplannedavail:" + entityItem.getKey() + " MODEL-meslastorderavail for ctry " + arrayOfMetaFlag[b1]
/* 4624 */                       .getFlagCode());
/*      */                 } 
/*      */                 
/* 4627 */                 if (!vector3.contains(entityItem1)) {
/* 4628 */                   vector3.add(entityItem1);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/* 4634 */           for (b1 = 0; b1 < vector3.size(); b1++) {
/* 4635 */             EntityItem entityItem1 = vector3.elementAt(b1);
/*      */ 
/*      */             
/* 4638 */             this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 4639 */             this.args[1] = getLD_NDN(paramEntityItem);
/* 4640 */             this.args[2] = getLD_NDN(entityItem1);
/* 4641 */             if (paramBoolean3) {
/* 4642 */               createMessage(paramInt, "PS_LAST_ORDER_ERR", this.args);
/*      */             } else {
/* 4644 */               createMessage(paramInt, "PS_MES_LAST_ORDER_ERR", this.args);
/*      */             } 
/*      */           } 
/* 4647 */           vector3.clear();
/*      */         } 
/*      */       } 
/* 4650 */       arrayList.clear();
/* 4651 */       hashtable1.clear();
/*      */     } 
/*      */     
/* 4654 */     hashtable.clear();
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
/*      */   private void checkPsModelPlaFOAvail(EntityItem paramEntityItem, String paramString, Vector paramVector1, Vector paramVector2) throws MiddlewareException, SQLException {
/* 4677 */     EntityGroup entityGroup = this.mdlList.getEntityGroup("AVAIL");
/* 4678 */     if (entityGroup == null) {
/* 4679 */       throw new MiddlewareException("AVAIL is missing from extract for " + this.mdlList.getParentActionItem().getActionItemKey());
/*      */     }
/*      */     
/* 4682 */     int i = getCheckLevel(getCheck_W_W_E(paramString), paramEntityItem, "ANNDATE");
/* 4683 */     int j = getCheckLevel(getCheck_W_RW_RE(paramString), paramEntityItem, "ANNDATE");
/*      */     
/* 4685 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Model Planned Avail Checks:");
/* 4686 */     Vector vector1 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "146");
/*      */     
/* 4688 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "143");
/* 4689 */     addDebug("checkPsModelPlaFOAvail mdlPlannedAvailVct " + vector1.size() + " mdlFoAvailVct " + vector2
/*      */         
/* 4691 */         .size());
/* 4692 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 4693 */     boolean bool1 = getAvailByOSN(hashtable1, paramVector1, true, 3);
/* 4694 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 4695 */     boolean bool2 = getAvailByOSN(hashtable2, paramVector2, true, 3);
/* 4696 */     if (vector1.size() > 0) {
/* 4697 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 4698 */       boolean bool = getAvailByOSN(hashtable, vector1, true, 3);
/*      */       
/* 4700 */       addDebug("checkPsModelPlaFOAvail  plaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1.keySet() + " mdlplaOsnErrors " + bool + " mdlplaAvailOSNTbl.keys " + hashtable
/* 4701 */           .keySet());
/*      */ 
/*      */       
/* 4704 */       if (paramVector1 != null && paramVector1.size() > 0 && !bool1 && !bool) {
/*      */ 
/*      */         
/* 4707 */         checkAvailDatesByCtryByOSN(hashtable1, hashtable, (EntityItem)null, 1, j, getLD_NDN(paramEntityItem), false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4713 */         checkAvailCtryByOSN(hashtable1, hashtable, "MODEL_AVAIL_OSNCTRY_ERR", paramEntityItem, true, i);
/*      */       } 
/* 4715 */       if (paramVector2 != null && paramVector2.size() > 0 && !bool1 && !bool) {
/*      */ 
/*      */         
/* 4718 */         checkAvailDatesByCtryByOSN(hashtable2, hashtable, (EntityItem)null, 1, j, getLD_NDN(paramEntityItem), false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4724 */         checkAvailCtryByOSN(hashtable2, hashtable, "MODEL_AVAIL_OSNCTRY_ERR", paramEntityItem, true, i);
/*      */       } 
/*      */       
/* 4727 */       vector1.clear();
/* 4728 */       hashtable.clear();
/*      */     } 
/*      */     
/* 4731 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Model MES Planned Avail Checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4753 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Model First Order Avail Checks:");
/* 4754 */     if (vector2.size() > 0) {
/* 4755 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 4756 */       boolean bool = getAvailByOSN(hashtable, vector2, true, 3);
/* 4757 */       addDebug("checkPsModelPlaFOAvail  mdlfoOsnErrors " + bool + " mdlfoAvailOSNTbl.keys " + hashtable
/* 4758 */           .keySet());
/*      */       
/* 4760 */       if (paramVector1 != null && paramVector1.size() > 0 && !bool1 && !bool) {
/*      */ 
/*      */ 
/*      */         
/* 4764 */         checkAvailDatesByCtryByOSN(hashtable1, hashtable, (EntityItem)null, 1, j, getLD_NDN(paramEntityItem), false);
/*      */ 
/*      */         
/* 4767 */         checkAvailCtryByOSN(hashtable1, hashtable, "MODEL_AVAIL_OSNCTRY_ERR", paramEntityItem, true, i);
/*      */       } 
/* 4769 */       if (paramVector2 != null && paramVector2.size() > 0 && !bool2 && !bool) {
/*      */ 
/*      */         
/* 4772 */         checkAvailDatesByCtryByOSN(hashtable2, hashtable, (EntityItem)null, 1, j, getLD_NDN(paramEntityItem), false);
/*      */ 
/*      */         
/* 4775 */         checkAvailCtryByOSN(hashtable2, hashtable, "MODEL_AVAIL_OSNCTRY_ERR", paramEntityItem, true, i);
/*      */       } 
/*      */       
/* 4778 */       hashtable.clear();
/* 4779 */       vector2.clear();
/*      */     } 
/*      */     
/* 4782 */     hashtable1.clear();
/* 4783 */     hashtable2.clear();
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
/*      */   private EntityList getModelVE(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 4855 */     String str = "DQVEMODELAVAIL";
/*      */     
/* 4857 */     EntityList entityList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 4858 */           .getProfile(), str), new EntityItem[] { new EntityItem(null, this.m_elist
/* 4859 */             .getProfile(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID()) });
/* 4860 */     addDebug("getModelVE: Extract " + str + NEWLINE + PokUtils.outputList(entityList));
/* 4861 */     return entityList;
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
/*      */   private EntityList getModelsVE(EntityGroup paramEntityGroup) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 4873 */     String str = "DQVEMODELAVAIL";
/*      */     
/* 4875 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramEntityGroup.getEntityItemCount()];
/* 4876 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 4877 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 4878 */       arrayOfEntityItem[b] = new EntityItem(null, this.m_elist.getProfile(), entityItem.getEntityType(), entityItem.getEntityID());
/*      */     } 
/* 4880 */     EntityList entityList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 4881 */           .getProfile(), str), arrayOfEntityItem);
/*      */     
/* 4883 */     addDebug("getModelsVE: Extract " + str + NEWLINE + PokUtils.outputList(entityList));
/* 4884 */     return entityList;
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
/*      */   private void checkAllFeatures(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 4912 */     if (this.mtmList == null) {
/* 4913 */       searchForProdstructs(paramEntityItem1, paramEntityItem2);
/*      */     }
/* 4915 */     addDebug("checkAllFeatures search results " + PokUtils.outputList(this.mtmList));
/*      */ 
/*      */     
/* 4918 */     if (this.mtmList != null) {
/* 4919 */       EntityGroup entityGroup = this.mtmList.getEntityGroup("FEATURE");
/* 4920 */       if (entityGroup.getEntityItemCount() > 1) {
/*      */ 
/*      */         
/* 4923 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 4924 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/* 4925 */           addDebug("checkAllFeatures (92) fnd duplicate fc on " + entityItem.getKey());
/*      */           
/* 4927 */           this.args[0] = getLD_NDN(entityItem) + " " + getLD_Value(entityItem, "FEATURECODE");
/* 4928 */           this.args[1] = PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "MACHTYPEATR", "MACHTYPEATR");
/* 4929 */           this.args[2] = entityGroup.getLongDescription();
/* 4930 */           createMessage(4, "NOT_IDENTICAL_ERR", this.args);
/*      */         } 
/*      */       } else {
/*      */         
/* 4934 */         EntityItem entityItem = entityGroup.getEntityItem(0);
/* 4935 */         addDebug("checkAllFeatures featItem " + entityItem.getKey());
/*      */         
/* 4937 */         Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 4938 */         for (byte b = 0; b < entityItem.getDownLinkCount(); b++) {
/* 4939 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b);
/* 4940 */           for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/* 4941 */             EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/* 4942 */             addDebug("checkAllFeatures adding psitem " + entityItem1.getKey() + " to vct for mdlitem " + entityItem2.getKey());
/* 4943 */             Vector<EntityItem> vector = (Vector)hashtable.get(entityItem2.getKey());
/* 4944 */             if (vector == null) {
/* 4945 */               vector = new Vector(1);
/* 4946 */               hashtable.put(entityItem2.getKey(), vector);
/*      */             } 
/* 4948 */             vector.add(entityItem1);
/*      */           } 
/*      */         } 
/* 4951 */         for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/* 4952 */           String str = enumeration.nextElement();
/* 4953 */           Vector<EntityItem> vector = (Vector)hashtable.get(str);
/* 4954 */           EntityItem entityItem1 = this.mtmList.getEntityGroup("MODEL").getEntityItem(str);
/* 4955 */           if (vector.size() > 1) {
/* 4956 */             for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 4957 */               EntityItem entityItem2 = vector.elementAt(b1);
/* 4958 */               addDebug("checkAllFeatures (91) mdlItem " + entityItem1.getKey() + " duplicate psitem " + entityItem2.getKey());
/* 4959 */               if (entityItem2.getEntityType().equals(getEntityType()) && entityItem2
/* 4960 */                 .getEntityID() == getEntityID()) {
/* 4961 */                 addDebug("checkAllFeatures (91)skipping msg root is duplicate psitem " + entityItem2.getKey());
/*      */               
/*      */               }
/*      */               else {
/*      */ 
/*      */                 
/* 4967 */                 String str1 = "";
/* 4968 */                 if (entityItem2.getEntityID() != getEntityID()) {
/* 4969 */                   str1 = getLD_NDN(entityItem2) + " ";
/*      */                 }
/* 4971 */                 this.args[0] = str1 + getLD_Value(entityItem, "FEATURECODE");
/* 4972 */                 this.args[1] = entityItem1.getEntityGroup().getLongDescription();
/* 4973 */                 this.args[2] = getLD_Value(entityItem1, "MODELATR");
/* 4974 */                 createMessage(4, "DUPLICATE_ERR", this.args);
/*      */               } 
/* 4976 */             }  vector.clear();
/*      */           } 
/*      */         } 
/* 4979 */         hashtable.clear();
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
/*      */   private void searchForProdstructs(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 4998 */     Vector<String> vector1 = new Vector(3);
/* 4999 */     vector1.addElement("MODEL:MACHTYPEATR");
/* 5000 */     vector1.addElement("FEATURE:FEATURECODE");
/*      */     
/* 5002 */     Vector<String> vector2 = new Vector(2);
/* 5003 */     vector2.addElement(PokUtils.getAttributeFlagValue(paramEntityItem2, "MACHTYPEATR"));
/* 5004 */     vector2.addElement(PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "", false));
/*      */     
/* 5006 */     addDebug("searchForProdstructs attrVct " + vector1 + " valVct " + vector2);
/*      */     
/* 5008 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/* 5010 */       StringBuffer stringBuffer = new StringBuffer();
/* 5011 */       arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_prof, "SRDPRODSTRUCTV", "PRODSTRUCT", true, vector1, vector2, stringBuffer);
/*      */       
/* 5013 */       if (stringBuffer.length() > 0) {
/* 5014 */         addDebug(stringBuffer.toString());
/*      */       }
/* 5016 */     } catch (SBRException sBRException) {
/*      */       
/* 5018 */       StringWriter stringWriter = new StringWriter();
/* 5019 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 5020 */       addDebug("searchForProdstructs SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/* 5022 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 5023 */       this.mtmList = arrayOfEntityItem[0].getEntityGroup().getEntityList();
/*      */     }
/*      */     
/* 5026 */     vector1.clear();
/* 5027 */     vector2.clear();
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
/*      */   private void deriveEXTRACTRPQ(EntityItem paramEntityItem) throws SQLException, MiddlewareException, MiddlewareRequestException {
/*      */     try {
/* 5062 */       OPICMList oPICMList = new OPICMList();
/* 5063 */       String str1 = "PDGtemplates/PRODSTRUCTABRSTATUS1.txt";
/* 5064 */       PDGUtility pDGUtility = new PDGUtility();
/*      */       
/* 5066 */       EntityItem entityItem1 = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 5067 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/* 5068 */       EntityItem entityItem2 = entityGroup.getEntityItem(0);
/*      */       
/* 5070 */       oPICMList.put("TIMESTAMP", this.m_elist.getProfile().getValOn());
/* 5071 */       oPICMList.put("PRODSTRUCT", entityItem1);
/* 5072 */       oPICMList.put("MODEL", entityItem2);
/* 5073 */       oPICMList.put("FEATURE", paramEntityItem);
/* 5074 */       oPICMList.put("PRODID", entityItem1.getEntityID() + "");
/* 5075 */       oPICMList.put("FEATUREID", paramEntityItem.getEntityID() + "");
/* 5076 */       oPICMList.put("MODELID", entityItem2.getEntityID() + "");
/* 5077 */       String str2 = "00000000000000" + paramEntityItem.getEntityID();
/* 5078 */       int i = str2.length();
/* 5079 */       if (i > 15) {
/* 5080 */         str2 = str2.substring(i - 15);
/*      */       }
/* 5082 */       oPICMList.put("ANN", "RPQ" + str2);
/* 5083 */       this.m_prof = pDGUtility.setProfValOnEffOn(this.m_db, this.m_prof);
/* 5084 */       TestPDGII testPDGII = new TestPDGII(this.m_db, this.m_prof, null, oPICMList, str1);
/* 5085 */       StringBuffer stringBuffer = testPDGII.getMissingEntities();
/* 5086 */       addDebug("deriveEXTRACTRPQ " + paramEntityItem.getKey() + " " + entityItem1.getKey() + " " + entityItem2
/* 5087 */           .getKey() + " sbmissing: " + stringBuffer);
/* 5088 */       pDGUtility.putCreateAction("EXTRACTRPQ", "CREXTRACTRPQ1");
/* 5089 */       pDGUtility.putSearchAction("EXTRACTRPQ", "SRDEXTRACTRPQ1");
/* 5090 */       pDGUtility.generateData(this.m_db, this.m_prof, stringBuffer, entityItem1);
/* 5091 */     } catch (MiddlewareShutdownInProgressException middlewareShutdownInProgressException) {
/* 5092 */       middlewareShutdownInProgressException.printStackTrace(System.out);
/* 5093 */       throw new MiddlewareException(middlewareShutdownInProgressException.getMessage());
/* 5094 */     } catch (SBRException sBRException) {
/* 5095 */       sBRException.printStackTrace(System.out);
/* 5096 */       throw new MiddlewareException(sBRException.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 5106 */     return "PRODSTRUCTABRSTATUS ABR";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/* 5116 */     return "1.18";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 5126 */     return "PRODSTRUCTABRSTATUS.java,v 1.8 2010/01/04 12:48:22 wendy Exp";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 5136 */     return "PRODSTRUCTABRSTATUS.java";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class WarrTPIC
/*      */   {
/* 5143 */     String ctry = null;
/* 5144 */     String effDate = null;
/* 5145 */     String endDate = null;
/* 5146 */     String minToDate = null;
/* 5147 */     String maxFromDate = null;
/* 5148 */     EntityItem warrItem = null;
/* 5149 */     EntityItem warrRel = null;
/*      */     boolean isDefWarr = false;
/*      */     
/*      */     WarrTPIC(String param1String, EntityItem param1EntityItem1, EntityItem param1EntityItem2) {
/* 5153 */       this.ctry = param1String;
/* 5154 */       this.warrRel = param1EntityItem2;
/* 5155 */       this.effDate = PokUtils.getAttributeValue(this.warrRel, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 5156 */       this.endDate = PokUtils.getAttributeValue(this.warrRel, "ENDDATE", "", "9999-12-31", false);
/* 5157 */       String str = PokUtils.getAttributeFlagValue(this.warrRel, "DEFWARR");
/* 5158 */       if (str == null) {
/* 5159 */         str = "N1";
/*      */       }
/* 5161 */       this.isDefWarr = "Y1".equals(str);
/* 5162 */       this.warrItem = param1EntityItem1;
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
/*      */     boolean hasOverlay(DQABRSTATUS.TPIC param1TPIC) {
/* 5174 */       boolean bool = false;
/* 5175 */       if (param1TPIC != null && param1TPIC.ctry.equals(this.ctry))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 5180 */         if (this.effDate.compareTo(param1TPIC.toDate) < 0 && this.endDate
/* 5181 */           .compareTo(param1TPIC.fromDate) > 0) {
/*      */ 
/*      */           
/* 5184 */           if (this.effDate.compareTo(param1TPIC.fromDate) >= 0) {
/* 5185 */             this.maxFromDate = this.effDate;
/*      */           } else {
/* 5187 */             this.maxFromDate = param1TPIC.fromDate;
/*      */           } 
/*      */           
/* 5190 */           if (this.endDate.compareTo(param1TPIC.toDate) <= 0) {
/* 5191 */             this.minToDate = this.endDate;
/*      */           } else {
/* 5193 */             this.minToDate = param1TPIC.toDate;
/*      */           } 
/*      */           
/* 5196 */           bool = true;
/*      */         } 
/*      */       }
/* 5199 */       return bool;
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/* 5202 */       if (param1Object instanceof WarrTPIC) {
/* 5203 */         return (this.warrItem.getEntityID() == ((WarrTPIC)param1Object).warrItem.getEntityID());
/*      */       }
/* 5205 */       return false;
/*      */     }
/*      */     public String toString() {
/* 5208 */       String str = "";
/* 5209 */       if (this.minToDate != null) {
/* 5210 */         str = " maxFromDate:" + this.maxFromDate + " minToDate:" + this.minToDate;
/*      */       }
/* 5212 */       return "\nctry:" + this.ctry + " effDate:" + this.effDate + " toDate:" + this.endDate + str + " " + this.warrItem
/* 5213 */         .getKey() + " thru " + this.warrRel.getKey() + " defWarr " + this.isDefWarr;
/*      */     }
/*      */     void dereference() {
/* 5216 */       this.ctry = null;
/* 5217 */       this.effDate = null;
/* 5218 */       this.endDate = null;
/* 5219 */       this.warrItem = null;
/* 5220 */       this.warrRel = null;
/* 5221 */       this.minToDate = null;
/* 5222 */       this.maxFromDate = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private class StopWarrException extends Exception {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     private StopWarrException() {}
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\PRODSTRUCTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */