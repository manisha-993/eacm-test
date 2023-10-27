/*      */ package COM.ibm.eannounce.abr.sg;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  162 */   private Object[] args = (Object[])new String[3];
/*      */ 
/*      */   
/*      */   private EntityList mdlList;
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  169 */     return true;
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  184 */     if (this.mdlList == null) {
/*      */       
/*      */       try {
/*  187 */         getModelVE(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*  188 */       } catch (Exception exception) {
/*  189 */         exception.printStackTrace();
/*  190 */         addDebug("Exception getting model ve " + exception.getMessage());
/*  191 */         throw new MiddlewareException(exception.getMessage());
/*      */       } 
/*      */     }
/*  194 */     propagateOStoWWSEO(this.mdlList.getEntityGroup("MODEL").getEntityItem(0), this.m_elist.getParentEntityGroup());
/*  195 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", "0020");
/*  196 */     this.mdlList.dereference();
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
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  214 */     if (this.mdlList == null) {
/*      */       
/*      */       try {
/*  217 */         getModelVE(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*  218 */       } catch (Exception exception) {
/*  219 */         exception.printStackTrace();
/*  220 */         addDebug("Exception getting model ve " + exception.getMessage());
/*  221 */         throw new MiddlewareException(exception.getMessage());
/*      */       } 
/*      */     }
/*  224 */     propagateOStoWWSEO(this.mdlList.getEntityGroup("MODEL").getEntityItem(0), this.m_elist.getParentEntityGroup());
/*  225 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", "0020");
/*  226 */     setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", "0020");
/*      */     
/*  228 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/*  229 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  230 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  231 */       String str = getAttributeFlagEnabledValue(entityItem, "STATUS");
/*  232 */       addDebug(entityItem.getKey() + " status " + str);
/*  233 */       if (str == null || str.length() == 0) {
/*  234 */         str = "0020";
/*      */       }
/*  236 */       if ("0020".equals(str))
/*      */       {
/*      */         
/*  239 */         setFlagValue(this.m_elist.getProfile(), "LSEOABRSTATUS", "0020", entityItem);
/*      */       }
/*      */     } 
/*  242 */     this.mdlList.dereference();
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  320 */     getModelVE(paramEntityItem);
/*      */ 
/*      */     
/*  323 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "SPECBID");
/*      */     
/*  325 */     EntityGroup entityGroup1 = this.mdlList.getEntityGroup("MODEL");
/*      */ 
/*      */     
/*  328 */     EntityGroup entityGroup2 = this.mdlList.getEntityGroup("MODELWWSEO");
/*  329 */     if (entityGroup2.getEntityItemCount() != 1) {
/*      */       
/*  331 */       Object[] arrayOfObject = { entityGroup1.getLongDescription() };
/*  332 */       addError("REQUIRES_ONE_PARENT_ERR", arrayOfObject);
/*      */     } else {
/*  334 */       EntityItem entityItem = entityGroup1.getEntityItem(0);
/*  335 */       String str1 = getAttributeFlagEnabledValue(entityItem, "COFCAT");
/*  336 */       String str2 = getAttributeFlagEnabledValue(entityItem, "COFSUBCAT");
/*  337 */       addDebug(entityItem.getKey() + " COFCAT: " + str1 + " COFSUBCAT: " + str2);
/*      */ 
/*      */       
/*  340 */       if (PokUtils.isSelected(paramEntityItem, "PDHDOMAIN", "0050")) {
/*      */ 
/*      */         
/*  343 */         if ("100".equals(str1) && "126".equals(str2)) {
/*      */ 
/*      */           
/*  346 */           int i = getCount("WWSEODERIVEDDATA");
/*  347 */           if (i != 1) {
/*  348 */             EntityGroup entityGroup = this.m_elist.getEntityGroup("DERIVEDDATA");
/*      */             
/*  350 */             Object[] arrayOfObject = { entityGroup.getLongDescription() };
/*  351 */             addError("NEED_ONLY_ONE_ERR", arrayOfObject);
/*      */           } 
/*      */ 
/*      */           
/*  355 */           i = getCount("SEOCGSEO");
/*  356 */           if (i != 1) {
/*  357 */             EntityGroup entityGroup = this.m_elist.getEntityGroup("SEOCG");
/*      */             
/*  359 */             Object[] arrayOfObject = { entityGroup.getLongDescription() };
/*  360 */             addError("MUST_BE_IN_ONE_ERR", arrayOfObject);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  367 */           checkCount("FEATUREPLANAR", "MODELPLANAR", "PLANAR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  373 */           checkExists("FEATUREMECHPKG", "MODELMECHPKG", "MECHPKG");
/*      */ 
/*      */ 
/*      */           
/*  377 */           i = getCount("WWSEOPRODSTRUCT");
/*  378 */           if (i == 0) {
/*  379 */             EntityGroup entityGroup3 = this.m_elist.getEntityGroup("PRODSTRUCT");
/*  380 */             EntityGroup entityGroup4 = this.m_elist.getEntityGroup("FEATURE");
/*      */ 
/*      */             
/*  383 */             Object[] arrayOfObject = { entityGroup3.getLongDescription() + " " + entityGroup4.getLongDescription() };
/*  384 */             addError("MINIMUM_ERR", arrayOfObject);
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
/*  398 */         if ("101".equals(str1)) {
/*      */ 
/*      */           
/*  401 */           int i = getCount("WWSEOSWPRODSTRUCT");
/*  402 */           if (i == 0) {
/*  403 */             EntityGroup entityGroup3 = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/*  404 */             EntityGroup entityGroup4 = this.m_elist.getEntityGroup("SWFEATURE");
/*      */ 
/*      */             
/*  407 */             Object[] arrayOfObject = { entityGroup3.getLongDescription() + " " + entityGroup4.getLongDescription() };
/*  408 */             addError("MINIMUM_ERR", arrayOfObject);
/*      */           } 
/*      */         } 
/*      */         
/*  412 */         if ("102".equals(str1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  417 */           String str3 = getAttributeFlagEnabledValue(entityItem, "SPECBID");
/*  418 */           addDebug(entityItem.getKey() + " SPECBID: " + str3 + " " + paramEntityItem.getKey() + " wwseoSPECBID " + str);
/*      */           
/*  420 */           if (str3 != null && !str3.equals(str))
/*      */           {
/*  422 */             addError("NO_SPECBID_MATCH", (Object[])null);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  427 */           int i = getCount("SEOCGSEO");
/*  428 */           if (i != 0) {
/*  429 */             EntityGroup entityGroup = this.m_elist.getEntityGroup("SEOCG");
/*      */             
/*  431 */             Object[] arrayOfObject = { entityGroup.getLongDescription() };
/*  432 */             addError("SVC_SEOCG_ERR", arrayOfObject);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  437 */       if ("0040".equals(paramString)) {
/*  438 */         String str3 = this.m_db.getDates().getNow().substring(0, 10);
/*      */         
/*  440 */         if (PokUtils.isSelected(paramEntityItem, "PDHDOMAIN", "0050"))
/*      */         {
/*      */           
/*  443 */           if ("100".equals(str1) && "126".equals(str2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  450 */             checkBays();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  468 */             checkSlots();
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  474 */         if ("100".equals(str1)) {
/*  475 */           int i = getCount("WWSEOWARR");
/*  476 */           if (i == 0) {
/*  477 */             i = getCount(this.mdlList, "MODELWARR");
/*  478 */             if (i == 0) {
/*  479 */               EntityGroup entityGroup = this.m_elist.getEntityGroup("WARR");
/*      */               
/*  481 */               Object[] arrayOfObject = { entityGroup.getLongDescription() };
/*  482 */               addError("MINIMUM_ERR", arrayOfObject);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  489 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOPRODSTRUCT", "PRODSTRUCT");
/*  490 */         for (byte b = 0; b < vector.size(); b++) {
/*  491 */           EntityItem entityItem1 = vector.elementAt(b);
/*  492 */           String str4 = PokUtils.getAttributeValue(entityItem1, "WITHDRAWDATE", ", ", "", false);
/*  493 */           addDebug(entityItem1.getKey() + " WITHDRAWDATE: " + str4 + " strNow: " + str3);
/*  494 */           if (str4.length() > 0 && str4.compareTo(str3) <= 0) {
/*  495 */             this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/*  496 */             this.args[1] = getNavigationName(entityItem1);
/*  497 */             addError("WITHDRAWN_ERR", this.args);
/*      */           } 
/*      */         } 
/*  500 */         vector.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  505 */         checkWDAvails(paramEntityItem, "WWSEOPRODSTRUCT", "PRODSTRUCT", "OOFAVAIL", str3);
/*      */ 
/*      */ 
/*      */         
/*  509 */         checkWDAvails(paramEntityItem, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT", "SWPRODSTRUCTAVAIL", str3);
/*      */ 
/*      */ 
/*      */         
/*  513 */         checkWDFeatures(paramEntityItem, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT", "SWFEATURE", str3);
/*      */ 
/*      */ 
/*      */         
/*  517 */         checkWDFeatures(paramEntityItem, "WWSEOPRODSTRUCT", "PRODSTRUCT", "FEATURE", str3);
/*      */ 
/*      */ 
/*      */         
/*  521 */         Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOPRODSTRUCT", "PRODSTRUCT");
/*  522 */         addDebug("PRODSTRUCT from WWSEOPRODSTRUCT found: " + vector1.size());
/*  523 */         checkStatus(vector1);
/*  524 */         vector1.clear();
/*      */ 
/*      */ 
/*      */         
/*  528 */         vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
/*  529 */         addDebug("SWPRODSTRUCT from WWSEOSWPRODSTRUCT found: " + vector1.size());
/*  530 */         checkStatus(vector1);
/*  531 */         vector1.clear();
/*      */       } 
/*      */     } 
/*      */     
/*  535 */     if (getReturnCode() != 0) {
/*  536 */       this.mdlList.dereference();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getModelVE(EntityItem paramEntityItem) throws Exception {
/*  546 */     String str = "EXRPT3WWSEO2";
/*      */     
/*  548 */     this.mdlList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/*  549 */           .getProfile(), str), new EntityItem[] { new EntityItem(null, this.m_elist
/*  550 */             .getProfile(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID()) });
/*  551 */     addDebug("getModelVE:: Extract " + str + NEWLINE + PokUtils.outputList(this.mdlList));
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
/*      */   private void checkWDFeatures(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException, MiddlewareException {
/*  564 */     String[] arrayOfString = new String[2];
/*      */     
/*  566 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, paramString2);
/*  567 */     addDebug("checkWDFeatures entered go thru: " + paramString1 + " to " + paramString2 + " then to " + paramString3 + " found: " + vector.size() + " " + paramString2);
/*  568 */     for (byte b = 0; b < vector.size(); b++) {
/*  569 */       EntityItem entityItem = vector.elementAt(b);
/*  570 */       addDebug("checkWDFeatures checking entity: " + entityItem.getKey());
/*  571 */       for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/*  572 */         EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b1);
/*  573 */         if (entityItem1.getEntityType().equals(paramString3)) {
/*  574 */           String str = PokUtils.getAttributeValue(entityItem1, "WITHDRAWDATEEFF_T", ", ", "", false);
/*  575 */           addDebug("checkWDFeatures checking " + entityItem1.getKey() + " WITHDRAWDATEEFF_T: " + str);
/*  576 */           if (str.length() > 0 && str.compareTo(paramString4) <= 0) {
/*      */             
/*  578 */             arrayOfString[0] = entityItem1.getEntityGroup().getLongDescription();
/*  579 */             arrayOfString[1] = getNavigationName(entityItem);
/*  580 */             addError("WITHDRAWN_ERR", (Object[])arrayOfString);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  585 */     vector.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  595 */     return "WWSEO ABR.";
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
/*  607 */     return "1.42";
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
/*  625 */     int i = 0;
/*      */     
/*  627 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString);
/*  628 */     if (entityGroup == null) {
/*  629 */       throw new MiddlewareException(paramString + " is missing from extract");
/*      */     }
/*  631 */     if (entityGroup.getEntityItemCount() > 0) {
/*  632 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  633 */         int j = 1;
/*  634 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  635 */         EANAttribute eANAttribute = entityItem.getAttribute("QTY");
/*  636 */         if (eANAttribute != null) {
/*  637 */           j = Integer.parseInt(eANAttribute.get().toString());
/*      */         }
/*  639 */         i += j;
/*  640 */         addDebug("getCount " + entityItem.getKey() + " qty " + j);
/*      */       } 
/*      */     }
/*      */     
/*  644 */     addDebug("getCount Total count found for " + paramString + " = " + i);
/*  645 */     return i;
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
/*      */   private void checkCount(String paramString1, String paramString2, String paramString3) throws MiddlewareException {
/*  663 */     addDebug("checkCount entered for " + paramString1 + " " + paramString2 + " " + paramString3);
/*  664 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString3);
/*  665 */     int i = getCount(this.m_elist, paramString1);
/*  666 */     if (i > 1) {
/*      */       
/*  668 */       Object[] arrayOfObject = { entityGroup.getLongDescription() };
/*  669 */       addError("NEED_ONLY_ONE_ERR", arrayOfObject);
/*      */     } else {
/*  671 */       int j = getCount(this.mdlList, paramString2);
/*  672 */       if (j > 1) {
/*      */         
/*  674 */         Object[] arrayOfObject = { entityGroup.getLongDescription() };
/*  675 */         addError("NEED_ONLY_ONE_ERR", arrayOfObject);
/*      */       
/*      */       }
/*  678 */       else if (i + j > 1 || i + j == 0) {
/*      */         
/*  680 */         Object[] arrayOfObject = { entityGroup.getLongDescription() };
/*  681 */         addError("NEED_ONLY_ONE_ERR", arrayOfObject);
/*  682 */       } else if (i > 0) {
/*  683 */         int k = 0;
/*      */         
/*  685 */         EntityItem entityItem = this.m_elist.getEntityGroup(paramString1).getEntityItem(0);
/*  686 */         for (byte b = 0; b < entityItem.getUpLinkCount(); b++) {
/*      */           
/*  688 */           EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b);
/*  689 */           if (entityItem1.getEntityType().equals("FEATURE")) {
/*      */             
/*  691 */             addDebug("checkCount " + entityItem.getKey() + " uplink[" + b + "]: " + entityItem1.getKey());
/*  692 */             for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/*      */               
/*  694 */               EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/*  695 */               if (entityItem2.getEntityType().equals("PRODSTRUCT")) {
/*  696 */                 addDebug("checkCount " + entityItem1.getKey() + " dnlink[" + b1 + "]: " + entityItem2.getKey());
/*      */                 
/*  698 */                 for (byte b2 = 0; b2 < entityItem2.getUpLinkCount(); b2++) {
/*      */                   
/*  700 */                   EntityItem entityItem3 = (EntityItem)entityItem2.getUpLink(b2);
/*  701 */                   if (entityItem3.getEntityType().equals("WWSEOPRODSTRUCT")) {
/*      */                     
/*  703 */                     addDebug("checkCount " + entityItem2.getKey() + " uplink[" + b2 + "]: " + entityItem3.getKey());
/*  704 */                     String str = PokUtils.getAttributeValue(entityItem3, "CONFQTY", ", ", "1", false);
/*  705 */                     addDebug("checkCount " + entityItem3.getKey() + " CONFQTY: " + str);
/*  706 */                     int m = Integer.parseInt(str);
/*  707 */                     k += m;
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  714 */         addDebug("checkCount totalConfQty: " + k);
/*  715 */         if (k > 1) {
/*      */           
/*  717 */           Object[] arrayOfObject = { entityGroup.getLongDescription() };
/*  718 */           addError("NEED_ONLY_ONE_ERR", arrayOfObject);
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
/*      */   private void checkExists(String paramString1, String paramString2, String paramString3) throws MiddlewareException {
/*  733 */     addDebug("checkExists entered for " + paramString1 + " " + paramString2 + " " + paramString3);
/*  734 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString3);
/*  735 */     int i = getCount(this.m_elist, paramString1);
/*  736 */     if (i > 0) {
/*  737 */       addDebug("checkExists fccnt " + i);
/*      */     } else {
/*  739 */       int j = getCount(this.mdlList, paramString2);
/*  740 */       if (j > 0) {
/*  741 */         addDebug("checkExists mdlcnt " + j);
/*      */       } else {
/*      */         
/*  744 */         Object[] arrayOfObject = { entityGroup.getLongDescription() };
/*  745 */         addError("MINIMUM_ERR", arrayOfObject);
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
/*      */   private void checkBays() {
/*  761 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  762 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  767 */     addDebug("checkBays: entered");
/*      */ 
/*      */     
/*  770 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("BAY");
/*  771 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("FEATURE");
/*  772 */     Vector vector = PokUtils.getAllLinkedEntities(entityGroup2, "FEATUREMECHPKG", "MECHPKG");
/*  773 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(vector, "MECHPKGBAY", "BAY");
/*      */     
/*  775 */     addDebug("checkBays: bayVector.size " + vector1.size());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  787 */     for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*      */       
/*  789 */       EntityItem entityItem = vector1.elementAt(b1);
/*  790 */       String str1 = PokUtils.getAttributeValue(entityItem, "BAYTYPE", ", ", "", false);
/*  791 */       String str2 = getAttributeFlagEnabledValue(entityItem, "BAYTYPE");
/*  792 */       String str3 = PokUtils.getAttributeValue(entityItem, "ACCSS", ", ", "", false);
/*  793 */       String str4 = getAttributeFlagEnabledValue(entityItem, "ACCSS");
/*  794 */       String str5 = PokUtils.getAttributeValue(entityItem, "BAYFF", ", ", "", false);
/*  795 */       String str6 = getAttributeFlagEnabledValue(entityItem, "BAYFF");
/*  796 */       String str7 = str1 + ", " + str3 + ", " + str5;
/*  797 */       String str8 = str2 + "," + str4 + "," + str6;
/*  798 */       addDebug("checkBays: " + entityItem.getKey() + " bayInfo: " + str7 + "[" + str8 + "]");
/*  799 */       if (hashtable1.containsKey(str8)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  805 */         Object[] arrayOfObject = new Object[2];
/*  806 */         arrayOfObject[0] = entityGroup1.getLongDescription();
/*  807 */         arrayOfObject[1] = str7;
/*  808 */         addError("MUST_HAVE_UNIQUE_ERR", arrayOfObject);
/*      */       } else {
/*      */         
/*  811 */         hashtable1.put(str8, str7);
/*      */       } 
/*      */     } 
/*      */     
/*  815 */     EntityGroup entityGroup3 = this.m_elist.getEntityGroup("BAYSAVAIL");
/*  816 */     for (byte b2 = 0; b2 < entityGroup3.getEntityItemCount(); b2++) {
/*      */       
/*  818 */       EntityItem entityItem = entityGroup3.getEntityItem(b2);
/*  819 */       String str1 = PokUtils.getAttributeValue(entityItem, "BAYAVAILTYPE", ", ", "", false);
/*  820 */       String str2 = getAttributeFlagEnabledValue(entityItem, "BAYAVAILTYPE");
/*  821 */       String str3 = PokUtils.getAttributeValue(entityItem, "ACCSS", ", ", "", false);
/*  822 */       String str4 = getAttributeFlagEnabledValue(entityItem, "ACCSS");
/*  823 */       String str5 = PokUtils.getAttributeValue(entityItem, "BAYFF", ", ", "", false);
/*  824 */       String str6 = getAttributeFlagEnabledValue(entityItem, "BAYFF");
/*  825 */       String str7 = str1 + ", " + str3 + ", " + str5;
/*  826 */       String str8 = str2 + "," + str4 + "," + str6;
/*  827 */       addDebug("checkBays: " + entityItem.getKey() + " bayInfo: " + str7 + "[" + str8 + "]");
/*      */ 
/*      */       
/*  830 */       hashtable2.put(str8, str7);
/*      */     } 
/*      */ 
/*      */     
/*  834 */     Iterator<String> iterator = hashtable1.keySet().iterator();
/*  835 */     while (iterator.hasNext()) {
/*      */       
/*  837 */       String str = iterator.next();
/*      */ 
/*      */ 
/*      */       
/*  841 */       if (!hashtable2.containsKey(str)) {
/*      */ 
/*      */         
/*  844 */         Object[] arrayOfObject = new Object[4];
/*  845 */         arrayOfObject[0] = entityGroup1.getLongDescription();
/*  846 */         arrayOfObject[1] = "Type";
/*  847 */         arrayOfObject[2] = entityGroup3.getLongDescription();
/*  848 */         arrayOfObject[3] = hashtable1.get(str);
/*  849 */         addError("CORRESPONDING_ERR", arrayOfObject);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  854 */     iterator = hashtable2.keySet().iterator();
/*  855 */     while (iterator.hasNext()) {
/*      */       
/*  857 */       String str = iterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  862 */       if (!hashtable1.containsKey(str)) {
/*      */ 
/*      */         
/*  865 */         Object[] arrayOfObject = new Object[4];
/*  866 */         arrayOfObject[0] = entityGroup3.getLongDescription();
/*  867 */         arrayOfObject[1] = "Type";
/*  868 */         arrayOfObject[2] = entityGroup1.getLongDescription();
/*  869 */         arrayOfObject[3] = hashtable2.get(str);
/*  870 */         addError("CORRESPONDING_ERR", arrayOfObject);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  875 */     hashtable1.clear();
/*  876 */     hashtable1 = null;
/*  877 */     hashtable2.clear();
/*  878 */     hashtable2 = null;
/*      */ 
/*      */     
/*  881 */     vector.clear();
/*  882 */     vector1.clear();
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
/*      */   private void checkSlots() {
/*  921 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  922 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  923 */     Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  929 */     String str1 = "0010";
/*  930 */     String str2 = "0020";
/*  931 */     String str3 = "0030";
/*      */     
/*  933 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SLOTSAVAIL");
/*      */ 
/*      */     
/*  936 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */       
/*  938 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  939 */       String str4 = PokUtils.getAttributeValue(entityItem, "SLOTTYPE", ", ", "", false);
/*  940 */       String str5 = getAttributeFlagEnabledValue(entityItem, "SLOTTYPE");
/*  941 */       String str6 = PokUtils.getAttributeValue(entityItem, "ELEMENTTYPE", ", ", "", false);
/*  942 */       String str7 = getAttributeFlagEnabledValue(entityItem, "ELEMENTTYPE");
/*  943 */       addDebug("checkSlots: " + entityItem.getKey() + " slotType: " + str4 + "[" + str5 + "] elementType: " + str6);
/*      */       
/*  945 */       if (str7 == null || str7.length() == 0) {
/*  946 */         addDebug("checkSlots: skipping " + entityItem.getKey() + " slotType: " + str4 + " does not have ELEMENTTYPE defined.");
/*      */ 
/*      */       
/*      */       }
/*  950 */       else if (str1.equals(str7)) {
/*  951 */         hashtable1.put(str5, str4);
/*  952 */       } else if (str2.equals(str7)) {
/*  953 */         hashtable2.put(str5, str4);
/*  954 */       } else if (str3.equals(str7)) {
/*  955 */         hashtable3.put(str5, str4);
/*      */       } 
/*      */     } 
/*      */     
/*  959 */     addDebug("slotsavailMemCardSet " + hashtable1);
/*  960 */     addDebug("slotsavailPlanarSet " + hashtable2);
/*  961 */     addDebug("slotsavailExpUnitSet " + hashtable3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  971 */     checkElementSlots(hashtable1, "MEMORYCARD", "FEATUREMEMORYCARD", "MEMORYCARDSLOT", true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  977 */     checkElementSlots(hashtable2, "PLANAR", "FEATUREPLANAR", "PLANARSLOT", false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  984 */     checkElementSlots(hashtable3, "EXPDUNIT", "FEATUREEXPDUNIT", "EXPDUNITSLOT", false);
/*      */     
/*  986 */     hashtable1.clear();
/*  987 */     hashtable1 = null;
/*  988 */     hashtable2.clear();
/*  989 */     hashtable2 = null;
/*  990 */     hashtable3.clear();
/*  991 */     hashtable3 = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkElementSlots(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1002 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1003 */     HashSet<String> hashSet = new HashSet();
/* 1004 */     Object[] arrayOfObject = new Object[4];
/*      */ 
/*      */ 
/*      */     
/* 1008 */     addDebug("checkElementSlots: entered " + paramString1 + " " + paramString2 + " " + paramString3);
/*      */     
/* 1010 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("SLOT");
/* 1011 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("FEATURE");
/* 1012 */     EntityGroup entityGroup3 = this.m_elist.getEntityGroup(paramString1);
/* 1013 */     EntityGroup entityGroup4 = this.m_elist.getEntityGroup("SLOTSAVAIL");
/* 1014 */     Vector vector = PokUtils.getAllLinkedEntities(entityGroup2, paramString2, paramString1);
/* 1015 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(vector, paramString3, "SLOT");
/*      */     
/* 1017 */     for (byte b = 0; b < vector1.size(); b++) {
/*      */       
/* 1019 */       EntityItem entityItem = vector1.get(b);
/* 1020 */       String str1 = PokUtils.getAttributeValue(entityItem, "SLOTTYPE", ", ", "", false);
/* 1021 */       String str2 = PokUtils.getAttributeValue(entityItem, "SLOTSZE", ", ", "", false);
/* 1022 */       String str3 = getAttributeFlagEnabledValue(entityItem, "SLOTTYPE");
/* 1023 */       String str4 = getAttributeFlagEnabledValue(entityItem, "SLOTSZE");
/* 1024 */       addDebug("checkElementSlots: " + paramString1 + " " + entityItem.getKey() + " slotType: " + str1 + "[" + str3 + "] slotSze: " + str2 + "[" + str4 + "]");
/*      */       
/* 1026 */       hashtable.put(str3, str1);
/* 1027 */       if (hashSet.contains(str3 + str4)) {
/*      */ 
/*      */ 
/*      */         
/* 1031 */         arrayOfObject[0] = entityGroup1.getLongDescription();
/* 1032 */         arrayOfObject[1] = str1 + " " + str2;
/* 1033 */         addError("MUST_HAVE_UNIQUE_ERR", arrayOfObject);
/*      */       } else {
/* 1035 */         hashSet.add(str3 + str4);
/*      */       } 
/*      */     } 
/* 1038 */     hashSet.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     Iterator<String> iterator = hashtable.keySet().iterator();
/* 1044 */     while (iterator.hasNext()) {
/*      */       
/* 1046 */       String str = iterator.next();
/* 1047 */       if (!paramHashtable.containsKey(str)) {
/*      */ 
/*      */         
/* 1050 */         arrayOfObject[0] = entityGroup3.getLongDescription();
/* 1051 */         arrayOfObject[1] = entityGroup1.getLongDescription();
/* 1052 */         arrayOfObject[2] = entityGroup4.getLongDescription();
/* 1053 */         arrayOfObject[3] = hashtable.get(str);
/* 1054 */         addError("CORRESPONDING_ERR", arrayOfObject);
/*      */       } 
/*      */     } 
/*      */     
/* 1058 */     if (paramBoolean) {
/*      */       
/* 1060 */       iterator = paramHashtable.keySet().iterator();
/* 1061 */       while (iterator.hasNext()) {
/*      */         
/* 1063 */         String str = iterator.next();
/* 1064 */         if (!hashtable.containsKey(str)) {
/*      */ 
/*      */           
/* 1067 */           arrayOfObject[0] = entityGroup4.getLongDescription();
/* 1068 */           arrayOfObject[1] = "Type";
/* 1069 */           arrayOfObject[2] = entityGroup1.getLongDescription();
/* 1070 */           arrayOfObject[3] = paramHashtable.get(str);
/* 1071 */           addError("CORRESPONDING_ERR", arrayOfObject);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1076 */     hashtable.clear();
/* 1077 */     hashtable = null;
/* 1078 */     vector.clear();
/* 1079 */     vector1.clear();
/* 1080 */     vector1 = null;
/* 1081 */     vector = null;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWSEOABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */