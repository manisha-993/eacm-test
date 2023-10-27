/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.WorkflowException;
/*     */ import COM.ibm.opicmpdh.middleware.LockException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LXLEADSADDSEOABR
/*     */   implements LXABRInterface
/*     */ {
/* 176 */   private LXABRSTATUS lxAbr = null;
/* 177 */   private String dtsOfData = null;
/* 178 */   private Element rootElem = null;
/* 179 */   private String factSheetNum = null;
/* 180 */   private String seoid = null;
/* 181 */   private String pubfrom = null;
/* 182 */   private String mt = null;
/* 183 */   private String model = null;
/* 184 */   private String cofcat = "";
/* 185 */   private String seoTechDesc = null;
/* 186 */   private EntityItem modelItem = null;
/* 187 */   private Vector tmfVct = new Vector();
/* 188 */   private Hashtable psQtyTbl = new Hashtable<>();
/* 189 */   private Vector countryVct = new Vector(1);
/* 190 */   private EntityList psList = null;
/* 191 */   private Object audien = null;
/* 192 */   private String domainStr = null;
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
/*     */   private void validateXML() throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 247 */     this.dtsOfData = this.lxAbr.getNodeValue(this.rootElem, "DTSOFDATA");
/* 248 */     this.factSheetNum = this.lxAbr.getNodeValue(this.rootElem, "FACTSHEETNUM");
/* 249 */     this.seoid = this.lxAbr.getNodeValue(this.rootElem, "SEOID");
/* 250 */     this.pubfrom = this.lxAbr.getNodeValue(this.rootElem, "PUBFROM", "");
/* 251 */     this.mt = this.lxAbr.getNodeValue(this.rootElem, "MT");
/* 252 */     this.model = this.lxAbr.getNodeValue(this.rootElem, "MODEL");
/* 253 */     this.seoTechDesc = this.lxAbr.getNodeValue(this.rootElem, "SEOTECHDESC", "");
/*     */ 
/*     */     
/* 256 */     this.audien = LXLEADSUtil.deriveAudien(this.lxAbr.getNodeValue(this.rootElem, "ACCOUNTTYPE", ""));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 264 */     NodeList nodeList = this.rootElem.getElementsByTagName("COUNTRYLIST");
/*     */ 
/*     */     
/* 267 */     if (nodeList == null || nodeList.getLength() != 1) {
/* 268 */       this.lxAbr.addError("ERROR_INVALIDXML", "COUNTRYLIST");
/*     */     } else {
/* 270 */       this.lxAbr.verifyChildNodes(nodeList, "COUNTRYLIST", "COUNTRYELEMENT", 1);
/* 271 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 272 */         Node node = nodeList.item(b);
/* 273 */         if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */           
/* 277 */           NodeList nodeList1 = nodeList.item(b).getChildNodes();
/* 278 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 279 */             node = nodeList1.item(b1);
/* 280 */             if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */               
/* 284 */               Element element = (Element)nodeList1.item(b1);
/* 285 */               String str = this.lxAbr.getNodeValue(element, "COUNTRY");
/* 286 */               if (str != null) {
/* 287 */                 StringBuffer stringBuffer = new StringBuffer();
/*     */                 
/* 289 */                 EntityItem entityItem = LXLEADSUtil.searchForGENERALAREA(this.lxAbr.getDatabase(), this.lxAbr
/* 290 */                     .getProfile(), str, stringBuffer);
/* 291 */                 if (stringBuffer.length() > 0) {
/* 292 */                   this.lxAbr.addDebug(stringBuffer.toString());
/*     */                 }
/* 294 */                 if (entityItem != null) {
/*     */                   
/* 296 */                   String str1 = PokUtils.getAttributeFlagValue(entityItem, "GENAREANAME");
/* 297 */                   if (str1 != null) {
/* 298 */                     this.countryVct.add(str1);
/*     */                   } else {
/*     */                     
/* 301 */                     this.lxAbr.addError("ERROR_COUNTRY", str);
/*     */                   } 
/*     */                 } else {
/* 304 */                   this.lxAbr.addError("GENERALAREA item not found for COUNTRY: " + str);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeader() {
/* 318 */     if (this.dtsOfData == null || this.factSheetNum == null || this.mt == null || this.cofcat == null || this.model == null || this.seoid == null) {
/* 319 */       return "";
/*     */     }
/*     */     
/* 322 */     String str = this.lxAbr.getResourceMsg("LSEO_HEADER", new Object[] { this.dtsOfData, this.factSheetNum, this.mt, this.cofcat, this.model, this.seoid });
/* 323 */     if (this.domainStr != null) {
/* 324 */       str = str + "<br />Domain = " + this.domainStr;
/*     */     }
/* 326 */     return str;
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
/*     */   public void validateData(LXABRSTATUS paramLXABRSTATUS, Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 356 */     this.lxAbr = paramLXABRSTATUS;
/* 357 */     this.rootElem = paramElement;
/*     */     
/* 359 */     validateXML();
/*     */     
/* 361 */     if (this.lxAbr.getReturnCode() != 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 367 */     this.modelItem = LXLEADSUtil.searchForModel(this.lxAbr, this.mt, this.model);
/* 368 */     if (this.modelItem == null) {
/*     */       
/* 370 */       this.lxAbr.addError("ERROR_MODEL", (Object[])null);
/*     */     } else {
/* 372 */       this.cofcat = PokUtils.getAttributeValue(this.modelItem, "COFCAT", "", "", false);
/* 373 */       String str1 = PokUtils.getAttributeFlagValue(this.modelItem, "COFCAT");
/* 374 */       this.lxAbr.addDebug("validateData Found " + this.cofcat + " " + this.modelItem.getKey());
/* 375 */       String str2 = null;
/*     */       
/* 377 */       if ("100".equals(str1)) {
/* 378 */         str2 = "EXRPT3LEADS";
/* 379 */       } else if ("101".equals(str1)) {
/* 380 */         str2 = "EXRPT3LEADS2";
/* 381 */       } else if ("102".equals(str1)) {
/* 382 */         this.lxAbr.addDebug("validateData Model is Service, no prodstructs to pull");
/*     */       } 
/* 384 */       if (str2 != null) {
/* 385 */         this.psList = this.lxAbr.getDatabase().getEntityList(this.lxAbr.getProfile(), new ExtractActionItem(null, this.lxAbr
/* 386 */               .getDatabase(), this.lxAbr.getProfile(), str2), new EntityItem[] { this.modelItem });
/*     */         
/* 388 */         this.lxAbr.addDebug("validateData VE:" + str2 + "\n" + PokUtils.outputList(this.psList));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 393 */     EntityItem entityItem1 = LXLEADSUtil.searchForWWSEO(this.lxAbr, this.seoid);
/*     */     
/* 395 */     if (entityItem1 != null)
/*     */     {
/* 397 */       this.lxAbr.addError("ERROR_WWSEO", new Object[] { this.seoid });
/*     */     }
/*     */ 
/*     */     
/* 401 */     EntityItem entityItem2 = LXLEADSUtil.searchForLSEO(this.lxAbr, this.seoid);
/*     */     
/* 403 */     if (entityItem2 != null)
/*     */     {
/* 405 */       this.lxAbr.addError("ERROR_LSEO", (Object[])null);
/*     */     }
/*     */ 
/*     */     
/* 409 */     if (this.modelItem != null)
/*     */     {
/* 411 */       matchProdstructs();
/*     */     }
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
/*     */   private void matchProdstructs() {
/* 554 */     String str1 = PokUtils.getAttributeFlagValue(this.modelItem, "COFCAT");
/* 555 */     String str2 = "";
/* 556 */     this.lxAbr.addDebug("matchProdstructs " + this.modelItem.getKey() + " cofcatflag:" + str1);
/* 557 */     if ("100".equals(str1)) {
/* 558 */       str2 = "FEATURE";
/* 559 */     } else if ("101".equals(str1)) {
/* 560 */       str2 = "SWFEATURE";
/* 561 */     } else if ("102".equals(str1)) {
/* 562 */       this.lxAbr.addDebug("matchProdstructs Model is Service, nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/* 566 */     if (this.psList == null) {
/*     */       return;
/*     */     }
/* 569 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 570 */     EntityGroup entityGroup = this.psList.getEntityGroup(str2);
/* 571 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 572 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 573 */       String str = PokUtils.getAttributeValue(entityItem, "FEATURECODE", "", "", false);
/* 574 */       Vector vector = entityItem.getDownLink();
/* 575 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 576 */         this.lxAbr.addDebug("matchProdstructs featurecode: " + str + " on " + entityItem.getKey() + " dnlink[" + b1 + "] " + entityItem
/* 577 */             .getDownLink(b1).getKey());
/*     */       }
/* 579 */       hashtable.put(str, vector);
/*     */     } 
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
/* 592 */     NodeList nodeList = this.rootElem.getElementsByTagName("FEATURELIST");
/*     */ 
/*     */     
/* 595 */     if (nodeList == null || nodeList.getLength() != 1) {
/* 596 */       this.lxAbr.addError("ERROR_INVALIDXML", "FEATURELIST");
/*     */     } else {
/* 598 */       this.lxAbr.verifyChildNodes(nodeList, "FEATURELIST", "FEATUREELEMENT", 1);
/* 599 */       for (byte b1 = 0; b1 < nodeList.getLength(); b1++) {
/* 600 */         Node node = nodeList.item(b1);
/* 601 */         if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */           
/* 605 */           NodeList nodeList1 = nodeList.item(b1).getChildNodes();
/* 606 */           for (byte b2 = 0; b2 < nodeList1.getLength(); b2++) {
/* 607 */             node = nodeList1.item(b2);
/* 608 */             if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */               
/* 612 */               Element element = (Element)nodeList1.item(b2);
/* 613 */               String str3 = this.lxAbr.getNodeValue(element, "FEATURECODE");
/* 614 */               String str4 = this.lxAbr.getNodeValue(element, "QTY", "1");
/* 615 */               if (str3 != null) {
/* 616 */                 Vector<EntityItem> vector = (Vector)hashtable.get(str3);
/* 617 */                 if (vector == null || vector.size() == 0) {
/*     */                   
/* 619 */                   this.lxAbr.addError("ERROR_TMF", new Object[] { str3 });
/*     */                 } else {
/* 621 */                   for (byte b3 = 0; b3 < vector.size(); b3++)
/* 622 */                   { EntityItem entityItem = vector.elementAt(b3);
/* 623 */                     this.lxAbr.addDebug("matchProdstructs fc " + str3 + " using: " + entityItem.getKey());
/* 624 */                     this.psQtyTbl.put(entityItem.getKey(), str4);
/* 625 */                     this.tmfVct.add(entityItem); } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 632 */     }  hashtable.clear();
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
/*     */   public void execute() throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*     */     Vector<String> vector;
/* 682 */     String str1 = PokUtils.getAttributeFlagValue(this.modelItem, "COFCAT");
/*     */     
/* 684 */     EANAttribute eANAttribute = this.modelItem.getAttribute("PDHDOMAIN");
/* 685 */     String str2 = null;
/* 686 */     if (eANAttribute != null) {
/* 687 */       EANMetaAttribute eANMetaAttribute = eANAttribute.getMetaAttribute();
/* 688 */       String str = PokUtils.getAttributeFlagValue(this.modelItem, "PDHDOMAIN");
/* 689 */       this.lxAbr.addDebug("execute: using " + this.modelItem.getKey() + " PDHDOMAIN " + str);
/* 690 */       this.domainStr = PokUtils.getAttributeValue(this.modelItem, "PDHDOMAIN", ", ", "", false);
/* 691 */       if (eANMetaAttribute.getAttributeType().equals("U")) {
/* 692 */         str2 = str;
/*     */       } else {
/* 694 */         String[] arrayOfString = PokUtils.convertToArray(str);
/* 695 */         Vector<String> vector1 = new Vector(arrayOfString.length);
/* 696 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 697 */           vector1.add(arrayOfString[b]);
/*     */         }
/* 699 */         vector = vector1;
/*     */       } 
/*     */     } 
/*     */     
/* 703 */     EntityItem entityItem1 = LXLEADSUtil.createWWSEO(this.lxAbr, this.modelItem, this.seoid, this.seoTechDesc, vector);
/* 704 */     EntityItem entityItem2 = null;
/* 705 */     if (entityItem1 != null) {
/*     */       
/* 707 */       entityItem2 = LXLEADSUtil.createLSEO(this.lxAbr, entityItem1, this.seoid, this.pubfrom, this.countryVct, vector, this.audien);
/* 708 */       if (this.lxAbr.getReturnCode() == 0) {
/* 709 */         LXLEADSUtil.createFeatureRefs(this.lxAbr, entityItem1, this.modelItem, this.tmfVct, this.psQtyTbl);
/*     */       }
/*     */     } 
/*     */     
/* 713 */     if (this.lxAbr.getReturnCode() == 0) {
/* 714 */       StringBuffer stringBuffer = new StringBuffer();
/*     */       
/* 716 */       stringBuffer.append(this.lxAbr.getResourceMsg("CREATED_SEOS_MSG", new Object[] { entityItem1
/* 717 */               .getEntityGroup().getLongDescription(), entityItem2
/* 718 */               .getEntityGroup().getLongDescription(), this.seoid, this.cofcat, this.mt, this.model }));
/*     */ 
/*     */ 
/*     */       
/* 722 */       if (!"102".equals(str1)) {
/* 723 */         NodeList nodeList1 = this.rootElem.getElementsByTagName("FEATURELIST");
/* 724 */         for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 725 */           Node node = nodeList1.item(b1);
/* 726 */           if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */             
/* 730 */             NodeList nodeList2 = nodeList1.item(b1).getChildNodes();
/* 731 */             for (byte b2 = 0; b2 < nodeList2.getLength(); b2++) {
/* 732 */               node = nodeList2.item(b2);
/* 733 */               if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */                 
/* 737 */                 Element element = (Element)nodeList2.item(b2);
/* 738 */                 String str3 = this.lxAbr.getNodeValue(element, "FEATURECODE");
/* 739 */                 String str4 = this.lxAbr.getNodeValue(element, "QTY", "1");
/*     */ 
/*     */                 
/* 742 */                 stringBuffer.append(this.lxAbr.getResourceMsg("REF_FEATURE_MSG", new Object[] { str3, str4 }));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 747 */       }  NodeList nodeList = this.rootElem.getElementsByTagName("COUNTRYLIST");
/*     */       
/* 749 */       stringBuffer.append(this.lxAbr.getResourceMsg("REF_COUNTRY_MSG", (Object[])null));
/* 750 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 751 */         Node node = nodeList.item(b);
/* 752 */         if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */           
/* 756 */           NodeList nodeList1 = nodeList.item(b).getChildNodes();
/* 757 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 758 */             node = nodeList1.item(b1);
/* 759 */             if (node.getNodeType() == 1)
/*     */             
/*     */             { 
/*     */               
/* 763 */               Element element = (Element)nodeList1.item(b1);
/* 764 */               String str = this.lxAbr.getNodeValue(element, "COUNTRY");
/* 765 */               stringBuffer.append(str + " "); } 
/*     */           } 
/*     */         } 
/* 768 */       }  this.lxAbr.addOutput(stringBuffer.toString());
/*     */     } 
/*     */     
/* 771 */     if (entityItem2 != null) {
/* 772 */       entityItem2.getEntityGroup().getEntityList().dereference();
/* 773 */       entityItem2 = null;
/*     */     } 
/* 775 */     if (entityItem1 != null) {
/* 776 */       entityItem1.getEntityGroup().getEntityList().dereference();
/* 777 */       entityItem1 = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dereference() {
/* 786 */     this.lxAbr = null;
/* 787 */     this.dtsOfData = null;
/* 788 */     this.rootElem = null;
/* 789 */     this.factSheetNum = null;
/* 790 */     this.seoid = null;
/* 791 */     this.model = null;
/* 792 */     this.pubfrom = null;
/* 793 */     this.seoTechDesc = null;
/* 794 */     this.mt = null;
/* 795 */     this.modelItem = null;
/*     */     
/* 797 */     if (this.tmfVct != null) {
/* 798 */       this.tmfVct.clear();
/* 799 */       this.tmfVct = null;
/*     */     } 
/*     */     
/* 802 */     if (this.psList != null) {
/* 803 */       this.psList.dereference();
/* 804 */       this.psList = null;
/*     */     } 
/* 806 */     this.psQtyTbl.clear();
/* 807 */     this.psQtyTbl = null;
/*     */     
/* 809 */     this.countryVct.clear();
/* 810 */     this.countryVct = null;
/* 811 */     this.cofcat = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 819 */     return "1.7";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 828 */     return "LSEO Special Bid from LEADS" + ((this.lxAbr.getReturnCode() == 0) ? " created" : "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 835 */     return "Create Special Bid - SEO";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\LXLEADSADDSEOABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */