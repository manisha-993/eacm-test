/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.LinkActionItem;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.eannounce.objects.WorkflowException;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LXLEADSADDBUNDLEABR
/*      */   implements LXABRInterface
/*      */ {
/*      */   private static final String LSEOBDL_SRCHACTION_NAME = "LDSRDLSEOBUNDLE";
/*      */   private static final String LSEOBDL_CREATEACTION_NAME = "LDCRLSEOBUNDLE";
/*      */   private static final String LSEOBDL_LINKACTION_NAME = "LDLINKLSEOLSEOB";
/*  156 */   private LXABRSTATUS lxAbr = null;
/*  157 */   private String dtsOfData = null;
/*  158 */   private Element rootElem = null;
/*  159 */   private String factSheetNum = null;
/*  160 */   private String bdlseoid = null;
/*  161 */   private String bdlPubfrom = null;
/*      */   
/*  163 */   private String bdlMktgDesc = null;
/*  164 */   private EntityItem lseobdlItem = null;
/*      */   
/*  166 */   private Hashtable tmfTbl = new Hashtable<>();
/*  167 */   private Hashtable psQtyTbl = new Hashtable<>();
/*  168 */   private Hashtable mdlTbl = new Hashtable<>();
/*  169 */   private Hashtable ctryTbl = new Hashtable<>();
/*  170 */   private Object domain = null;
/*  171 */   private String domainStr = null;
/*  172 */   private Object audien = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateXML() throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  220 */     this.dtsOfData = this.lxAbr.getNodeValue(this.rootElem, "DTSOFDATA");
/*  221 */     this.factSheetNum = this.lxAbr.getNodeValue(this.rootElem, "FACTSHEETNUM");
/*  222 */     this.bdlseoid = this.lxAbr.getNodeValue(this.rootElem, "BDLSEOID");
/*  223 */     this.bdlPubfrom = this.lxAbr.getNodeValue(this.rootElem, "BUNDLPUBDATEMTRGT", "");
/*  224 */     this.bdlMktgDesc = this.lxAbr.getNodeValue(this.rootElem, "BUNDLMKTGDESC", "");
/*      */     
/*  226 */     this.audien = LXLEADSUtil.deriveAudien(this.lxAbr.getNodeValue(this.rootElem, "ACCOUNTTYPE", ""));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  234 */     NodeList nodeList1 = this.rootElem.getElementsByTagName("COUNTRYLIST");
/*  235 */     if (nodeList1 == null) {
/*      */       
/*  237 */       this.lxAbr.addError("ERROR_MISSINGXML", "COUNTRYLIST");
/*      */     } else {
/*  239 */       this.lxAbr.verifyChildNodes(nodeList1, "COUNTRYLIST", "COUNTRYELEMENT", 1);
/*      */       
/*  241 */       Element element = null;
/*  242 */       for (byte b = 0; b < nodeList1.getLength(); b++) {
/*  243 */         Node node = nodeList1.item(b);
/*  244 */         if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */           
/*  248 */           Element element1 = (Element)nodeList1.item(b);
/*  249 */           if (element1.getParentNode() == this.rootElem) {
/*  250 */             if (element != null) {
/*      */               
/*  252 */               this.lxAbr.addError("ERROR_INVALIDXML", "COUNTRYLIST");
/*      */               break;
/*      */             } 
/*  255 */             element = (Element)nodeList1.item(b);
/*      */           } 
/*      */ 
/*      */           
/*  259 */           findCountry(element1);
/*      */         } 
/*      */       } 
/*  262 */       if (element == null)
/*      */       {
/*  264 */         this.lxAbr.addError("ERROR_MISSINGXML", "COUNTRYLIST");
/*      */       }
/*      */     } 
/*      */     
/*  268 */     NodeList nodeList2 = this.rootElem.getElementsByTagName("SEOLIST");
/*      */ 
/*      */     
/*  271 */     if (nodeList2 == null || nodeList2.getLength() != 1) {
/*  272 */       this.lxAbr.addError("ERROR_INVALIDXML", "SEOLIST");
/*      */     } else {
/*  274 */       this.lxAbr.verifyChildNodes(nodeList2, "SEOLIST", "SEOELEMENT", 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHeader() {
/*  284 */     if (this.dtsOfData == null || this.factSheetNum == null || this.bdlseoid == null) {
/*  285 */       return "";
/*      */     }
/*      */     
/*  288 */     String str = this.lxAbr.getResourceMsg("LSEOBDL_HEADER", new Object[] { this.dtsOfData, this.factSheetNum, this.bdlseoid });
/*  289 */     if (this.domainStr != null) {
/*  290 */       str = str + "<br />Domain = " + this.domainStr;
/*      */     }
/*  292 */     return str;
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
/*      */   private void findCountry(Element paramElement) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  309 */     NodeList nodeList = paramElement.getChildNodes();
/*  310 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/*  311 */       Node node = nodeList.item(b);
/*  312 */       if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */         
/*  316 */         Element element = (Element)nodeList.item(b);
/*  317 */         String str = this.lxAbr.getNodeValue(element, "COUNTRY");
/*  318 */         if (str != null) {
/*  319 */           this.lxAbr.addDebug("findCountry checking " + str);
/*  320 */           String str1 = (String)this.ctryTbl.get(str);
/*  321 */           if (str1 == null) {
/*  322 */             StringBuffer stringBuffer = new StringBuffer();
/*      */             
/*  324 */             EntityItem entityItem = LXLEADSUtil.searchForGENERALAREA(this.lxAbr.getDatabase(), this.lxAbr
/*  325 */                 .getProfile(), str, stringBuffer);
/*  326 */             if (stringBuffer.length() > 0) {
/*  327 */               this.lxAbr.addDebug(stringBuffer.toString());
/*      */             }
/*  329 */             if (entityItem != null) {
/*      */               
/*  331 */               str1 = PokUtils.getAttributeFlagValue(entityItem, "GENAREANAME");
/*  332 */               if (str1 != null) {
/*  333 */                 this.ctryTbl.put(str, str1);
/*      */               } else {
/*  335 */                 String str2 = this.bdlseoid;
/*  336 */                 if (paramElement.getParentNode() != this.rootElem)
/*      */                 {
/*  338 */                   str2 = this.lxAbr.getNodeValue((Element)paramElement.getParentNode(), "SEOID");
/*      */                 }
/*      */ 
/*      */                 
/*  342 */                 this.lxAbr.addError("ERROR_SEO_COUNTRY", new Object[] { str2, str });
/*      */               } 
/*      */             } else {
/*  345 */               this.lxAbr.addError("GENERALAREA item not found for COUNTRY: " + str);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/*  356 */     this.lxAbr = null;
/*  357 */     this.dtsOfData = null;
/*  358 */     this.rootElem = null;
/*  359 */     this.factSheetNum = null;
/*  360 */     this.bdlseoid = null;
/*  361 */     this.bdlPubfrom = null;
/*  362 */     this.bdlMktgDesc = null;
/*  363 */     if (this.lseobdlItem != null) {
/*  364 */       this.lseobdlItem.getEntityGroup().getEntityList().dereference();
/*  365 */       this.lseobdlItem = null;
/*      */     } 
/*      */     
/*  368 */     if (this.tmfTbl != null) {
/*  369 */       for (Enumeration<Vector> enumeration = this.tmfTbl.elements(); enumeration.hasMoreElements(); ) {
/*  370 */         Vector<EntityItem> vector = enumeration.nextElement();
/*  371 */         for (byte b = 0; b < vector.size(); b++) {
/*  372 */           EntityItem entityItem = vector.elementAt(b);
/*      */           try {
/*  374 */             EntityList entityList = entityItem.getEntityGroup().getEntityList();
/*  375 */             if (entityList != null) {
/*  376 */               entityList.dereference();
/*      */             }
/*  378 */           } catch (Exception exception) {}
/*      */         } 
/*  380 */         vector.clear();
/*      */       } 
/*  382 */       this.tmfTbl.clear();
/*  383 */       this.tmfTbl = null;
/*      */     } 
/*      */     
/*  386 */     this.psQtyTbl.clear();
/*  387 */     this.psQtyTbl = null;
/*      */     
/*  389 */     this.ctryTbl.clear();
/*  390 */     this.ctryTbl = null;
/*  391 */     this.mdlTbl.clear();
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
/*      */   public void validateData(LXABRSTATUS paramLXABRSTATUS, Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  424 */     this.lxAbr = paramLXABRSTATUS;
/*  425 */     this.rootElem = paramElement;
/*      */     
/*  427 */     validateXML();
/*      */     
/*  429 */     if (this.lxAbr.getReturnCode() != 0) {
/*      */       return;
/*      */     }
/*      */     
/*  433 */     searchForLseoBundle();
/*      */     
/*  435 */     if (0 == this.lxAbr.getReturnCode()) {
/*      */       
/*  437 */       NodeList nodeList = this.rootElem.getElementsByTagName("SEOLIST");
/*  438 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/*  439 */         Node node = nodeList.item(b);
/*  440 */         if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */           
/*  444 */           NodeList nodeList1 = nodeList.item(b).getChildNodes();
/*  445 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  446 */             node = nodeList1.item(b1);
/*  447 */             if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */               
/*  451 */               Element element = (Element)nodeList1.item(b1);
/*  452 */               String str1 = this.lxAbr.getNodeValue(element, "MT");
/*  453 */               String str2 = this.lxAbr.getNodeValue(element, "MODEL");
/*  454 */               String str3 = this.lxAbr.getNodeValue(element, "SEOID");
/*  455 */               this.lxAbr.addDebug("validateData looking for seoElem[" + b1 + "] seoid: " + str3 + " mt: " + str1 + " model: " + str2);
/*      */               
/*  457 */               NodeList nodeList2 = element.getElementsByTagName("FEATURELIST");
/*      */               
/*  459 */               if (nodeList2 == null || nodeList2.getLength() != 1) {
/*  460 */                 this.lxAbr.addError("ERROR_INVALIDXML", "FEATURELIST");
/*      */               } else {
/*  462 */                 this.lxAbr.verifyChildNodes(nodeList2, "FEATURELIST", "FEATUREELEMENT", 1);
/*  463 */                 EntityItem entityItem = LXLEADSUtil.searchForModel(this.lxAbr, str1, str2);
/*  464 */                 if (entityItem != null) {
/*      */ 
/*      */ 
/*      */                   
/*  468 */                   matchProdstructs(str1, str2, str3, entityItem, nodeList2);
/*  469 */                   this.mdlTbl.put(str1 + ":" + str2, entityItem);
/*      */                 } else {
/*      */                   
/*  472 */                   this.lxAbr.addError("ERROR_LSEOBDL_MODEL", new Object[] { str1, str2 });
/*      */                 } 
/*      */               } 
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
/*      */   private void searchForLseoBundle() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  495 */     Vector<String> vector1 = new Vector(1);
/*  496 */     vector1.addElement("SEOID");
/*  497 */     Vector<String> vector2 = new Vector(1);
/*  498 */     vector2.addElement(this.bdlseoid);
/*      */     
/*  500 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/*  502 */       StringBuffer stringBuffer = new StringBuffer();
/*  503 */       arrayOfEntityItem = ABRUtil.doSearch(this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDSRDLSEOBUNDLE", "LSEOBUNDLE", false, vector1, vector2, stringBuffer);
/*      */       
/*  505 */       if (stringBuffer.length() > 0) {
/*  506 */         this.lxAbr.addDebug(stringBuffer.toString());
/*      */       }
/*  508 */     } catch (SBRException sBRException) {
/*      */       
/*  510 */       StringWriter stringWriter = new StringWriter();
/*  511 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  512 */       this.lxAbr.addDebug("searchForLseoBundle SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/*      */     
/*  515 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  516 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  517 */         this.lxAbr.addDebug("searchForLseoBundle found " + arrayOfEntityItem[b].getKey());
/*      */       }
/*      */       
/*  520 */       this.lxAbr.addError("ERROR_LSEOBDL", (Object[])null);
/*      */     } else {
/*  522 */       this.lxAbr.addDebug("searchForLseoBundle NO LSEOBUNDLE found for " + this.bdlseoid);
/*  523 */       EntityItem entityItem = LXLEADSUtil.searchForLSEO(this.lxAbr, this.bdlseoid);
/*  524 */       if (entityItem != null) {
/*  525 */         this.lxAbr.addDebug("searchForLseo found " + entityItem.getKey());
/*      */         
/*  527 */         this.lxAbr.addError("ERROR_LSEO_LSEOBDL", this.bdlseoid);
/*      */       } else {
/*  529 */         this.lxAbr.addDebug("searchForLseoBundle NO LSEO found for bundleseoid: " + this.bdlseoid);
/*      */       } 
/*      */     } 
/*      */     
/*  533 */     vector1.clear();
/*  534 */     vector2.clear();
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
/*      */   private void matchProdstructs(String paramString1, String paramString2, String paramString3, EntityItem paramEntityItem, NodeList paramNodeList) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  655 */     String str1 = null;
/*  656 */     String str2 = "";
/*  657 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, "COFCAT", "", "", false);
/*  658 */     String str4 = PokUtils.getAttributeFlagValue(paramEntityItem, "COFCAT");
/*  659 */     this.lxAbr.addDebug("matchProdstructs using " + paramEntityItem.getKey() + " cofcatflag:" + str4);
/*      */     
/*  661 */     if ("100".equals(str4)) {
/*  662 */       str2 = "FEATURE";
/*  663 */       str1 = "EXRPT3LEADS";
/*  664 */     } else if ("101".equals(str4)) {
/*  665 */       str2 = "SWFEATURE";
/*  666 */       str1 = "EXRPT3LEADS2";
/*  667 */     } else if ("102".equals(str4)) {
/*  668 */       this.lxAbr.addDebug("matchProdstructs Model is Service, no references needed");
/*      */       
/*      */       return;
/*      */     } 
/*  672 */     EntityList entityList = this.lxAbr.getDatabase().getEntityList(this.lxAbr.getProfile(), new ExtractActionItem(null, this.lxAbr
/*  673 */           .getDatabase(), this.lxAbr.getProfile(), str1), new EntityItem[] { paramEntityItem });
/*      */     
/*  675 */     this.lxAbr.addDebug("matchProdstructs VE:" + str1 + "\n" + PokUtils.outputList(entityList));
/*  676 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  677 */     EntityGroup entityGroup = entityList.getEntityGroup(str2); byte b;
/*  678 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  679 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  680 */       String str = PokUtils.getAttributeValue(entityItem, "FEATURECODE", "", "", false);
/*  681 */       Vector vector = entityItem.getDownLink();
/*  682 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  683 */         this.lxAbr.addDebug("matchProdstructs featurecode: " + str + " on " + entityItem.getKey() + " dnlink[" + b1 + "] " + entityItem
/*  684 */             .getDownLink(b1).getKey());
/*      */       }
/*  686 */       hashtable.put(str, vector);
/*      */     } 
/*      */     
/*  689 */     for (b = 0; b < paramNodeList.getLength(); b++) {
/*  690 */       Node node = paramNodeList.item(b);
/*  691 */       if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */         
/*  695 */         NodeList nodeList = paramNodeList.item(b).getChildNodes();
/*  696 */         for (byte b1 = 0; b1 < nodeList.getLength(); b1++) {
/*  697 */           node = nodeList.item(b1);
/*  698 */           if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */             
/*  702 */             Element element = (Element)nodeList.item(b1);
/*  703 */             String str5 = this.lxAbr.getNodeValue(element, "FEATURECODE");
/*  704 */             String str6 = this.lxAbr.getNodeValue(element, "QTY", "1");
/*  705 */             if (str5 != null) {
/*  706 */               Vector<EntityItem> vector = (Vector)hashtable.get(str5);
/*  707 */               if (vector == null || vector.size() == 0) {
/*      */                 
/*  709 */                 this.lxAbr.addError("ERROR_LSEOBDL_TMF", new Object[] { paramString1, str3, paramString2, paramString3, str5 });
/*      */               } else {
/*  711 */                 String str = paramString1 + ":" + paramString2 + ":" + paramString3;
/*  712 */                 Vector<EntityItem> vector1 = (Vector)this.tmfTbl.get(str);
/*  713 */                 if (vector1 == null) {
/*  714 */                   vector1 = new Vector();
/*  715 */                   this.tmfTbl.put(str, vector1);
/*      */                 } 
/*      */                 
/*  718 */                 for (byte b2 = 0; b2 < vector.size(); b2++) {
/*  719 */                   EntityItem entityItem = vector.elementAt(b2);
/*  720 */                   this.lxAbr.addDebug("matchProdstructs fc " + str5 + " using: " + entityItem.getKey());
/*  721 */                   this.psQtyTbl.put(entityItem.getKey(), str6);
/*  722 */                   vector1.add(entityItem);
/*      */                 } 
/*      */               } 
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
/*      */   public void execute() throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*  773 */     derivePDHDOMAIN();
/*  774 */     if (this.lxAbr.getReturnCode() != 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  779 */     createLSEOBDL();
/*  780 */     if (this.lseobdlItem != null) {
/*      */       
/*  782 */       NodeList nodeList = this.rootElem.getElementsByTagName("SEOLIST");
/*  783 */       Vector<EntityItem> vector = new Vector(1);
/*  784 */       StringBuffer stringBuffer1 = new StringBuffer();
/*  785 */       StringBuffer stringBuffer2 = new StringBuffer();
/*      */       byte b;
/*  787 */       label58: for (b = 0; b < nodeList.getLength(); b++) {
/*  788 */         Node node = nodeList.item(b);
/*  789 */         if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */           
/*  793 */           NodeList nodeList1 = nodeList.item(b).getChildNodes();
/*  794 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  795 */             if (this.lxAbr.getReturnCode() != 0) {
/*      */               break label58;
/*      */             }
/*  798 */             node = nodeList1.item(b1);
/*  799 */             if (node.getNodeType() == 1)
/*      */             
/*      */             { 
/*      */               
/*  803 */               StringBuffer stringBuffer = new StringBuffer();
/*  804 */               Element element = (Element)nodeList1.item(b1);
/*  805 */               String str1 = this.lxAbr.getNodeValue(element, "MT");
/*  806 */               String str2 = this.lxAbr.getNodeValue(element, "MODEL");
/*  807 */               String str3 = this.lxAbr.getNodeValue(element, "SEOID");
/*      */               
/*  809 */               EntityItem entityItem1 = (EntityItem)this.mdlTbl.get(str1 + ":" + str2);
/*      */               
/*  811 */               this.lxAbr.addDebug("execute looking at seoid[" + b1 + "]: " + str3 + " mt: " + str1 + " model: " + str2 + " " + entityItem1.getKey());
/*      */               
/*  813 */               EntityItem entityItem2 = getLSEO(entityItem1, element, str1, str2, str3, stringBuffer);
/*  814 */               if (entityItem2 == null) {
/*      */                 break label58;
/*      */               }
/*  817 */               vector.add(entityItem2);
/*  818 */               stringBuffer2.append(" " + entityItem2.getKey());
/*  819 */               if (stringBuffer1.length() > 0) {
/*  820 */                 stringBuffer1.append("<br />");
/*      */               }
/*  822 */               stringBuffer1.append(stringBuffer.toString()); } 
/*      */           } 
/*      */         } 
/*  825 */       }  if (this.lxAbr.getReturnCode() == 0) {
/*      */         
/*  827 */         LinkActionItem linkActionItem = new LinkActionItem(null, this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDLINKLSEOLSEOB");
/*  828 */         EntityItem[] arrayOfEntityItem1 = { this.lseobdlItem };
/*  829 */         EntityItem[] arrayOfEntityItem2 = new EntityItem[vector.size()];
/*      */ 
/*      */         
/*  832 */         vector.copyInto((Object[])arrayOfEntityItem2);
/*      */         
/*  834 */         this.lxAbr.addDebug("Link " + this.lseobdlItem.getKey() + " to " + stringBuffer2.toString());
/*      */         
/*  836 */         linkActionItem.setParentEntityItems(arrayOfEntityItem1);
/*  837 */         linkActionItem.setChildEntityItems(arrayOfEntityItem2);
/*  838 */         this.lxAbr.getDatabase().executeAction(this.lxAbr.getProfile(), linkActionItem);
/*      */         
/*  840 */         vector.clear();
/*      */         
/*  842 */         if (this.lxAbr.getReturnCode() == 0) {
/*      */           
/*  844 */           StringBuffer stringBuffer = new StringBuffer();
/*  845 */           NodeList nodeList1 = this.rootElem.getElementsByTagName("COUNTRYLIST");
/*  846 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  847 */             Node node = nodeList1.item(b1);
/*  848 */             if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */               
/*  852 */               Element element = (Element)nodeList1.item(b1);
/*  853 */               if (element.getParentNode() == this.rootElem) {
/*  854 */                 Element element1 = (Element)nodeList1.item(b1);
/*  855 */                 NodeList nodeList2 = element1.getChildNodes();
/*  856 */                 for (byte b2 = 0; b2 < nodeList2.getLength(); b2++) {
/*  857 */                   node = nodeList2.item(b2);
/*  858 */                   if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */                     
/*  862 */                     Element element2 = (Element)nodeList2.item(b2);
/*  863 */                     String str = this.lxAbr.getNodeValue(element2, "COUNTRY");
/*  864 */                     stringBuffer.append(str + " ");
/*      */                   } 
/*      */                 }  break;
/*      */               } 
/*      */             } 
/*  869 */           }  this.lxAbr.addOutput("CREATED_SEOBDL_MSG", new Object[] { this.lseobdlItem
/*  870 */                 .getEntityGroup().getLongDescription(), this.bdlseoid, stringBuffer
/*  871 */                 .toString(), stringBuffer1.toString() });
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
/*      */   private void derivePDHDOMAIN() throws SQLException, MiddlewareException {
/*  917 */     EntityItem entityItem = null;
/*  918 */     Collection collection = this.mdlTbl.values();
/*      */     
/*  920 */     Iterator<EntityItem> iterator = collection.iterator();
/*  921 */     if (collection.size() == 1) {
/*  922 */       entityItem = iterator.next();
/*      */     } else {
/*  924 */       EntityItem entityItem1 = null;
/*  925 */       EntityItem entityItem2 = null;
/*  926 */       while (iterator.hasNext()) {
/*  927 */         EntityItem entityItem3 = iterator.next();
/*  928 */         String str = PokUtils.getAttributeFlagValue(entityItem3, "COFCAT");
/*  929 */         this.lxAbr.addDebug("derivePDHDOMAIN checking cofcat:" + str + " " + entityItem3.getKey());
/*      */         
/*  931 */         if ("100".equals(str)) {
/*  932 */           entityItem = entityItem3; break;
/*      */         } 
/*  934 */         if ("101".equals(str)) {
/*  935 */           if (entityItem1 != null) {
/*  936 */             String str1 = PokUtils.getAttributeFlagValue(entityItem3, "COFSUBCAT");
/*  937 */             String str2 = PokUtils.getAttributeFlagValue(entityItem1, "COFSUBCAT");
/*  938 */             this.lxAbr.addDebug("derivePDHDOMAIN SW " + entityItem3.getKey() + " cofsubcatflag " + str1 + " curSW " + entityItem1
/*  939 */                 .getKey() + " curcofsubcatflag " + str2);
/*  940 */             if (LXLEADSUtil.SW_COFSUBCAT_SET.contains(str2)) {
/*  941 */               if (LXLEADSUtil.SW_COFSUBCAT_SET.contains(str1))
/*      */               {
/*      */ 
/*      */ 
/*      */                 
/*  946 */                 if (str1.compareTo(str2) < 0) {
/*  947 */                   this.lxAbr.addDebug("derivePDHDOMAIN SW " + entityItem3.getKey() + " overrides " + entityItem1
/*  948 */                       .getKey());
/*  949 */                   entityItem1 = entityItem3;
/*      */                 }  } 
/*      */               continue;
/*      */             } 
/*  953 */             entityItem1 = entityItem3;
/*      */             continue;
/*      */           } 
/*  956 */           entityItem1 = entityItem3; continue;
/*      */         } 
/*  958 */         if ("102".equals(str)) {
/*  959 */           entityItem2 = entityItem3;
/*      */         }
/*      */       } 
/*      */       
/*  963 */       if (entityItem == null) {
/*  964 */         entityItem = entityItem1;
/*      */       }
/*  966 */       if (entityItem == null) {
/*  967 */         entityItem = entityItem2;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  972 */     EANAttribute eANAttribute = entityItem.getAttribute("PDHDOMAIN");
/*  973 */     HashSet<String> hashSet = new HashSet();
/*  974 */     if (eANAttribute != null) {
/*  975 */       EANMetaAttribute eANMetaAttribute = eANAttribute.getMetaAttribute();
/*  976 */       String str = PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN");
/*  977 */       this.domainStr = PokUtils.getAttributeValue(entityItem, "PDHDOMAIN", ", ", "", false);
/*  978 */       this.lxAbr.addDebug("derivePDHDOMAIN using " + entityItem.getKey() + " PDHDOMAIN " + str);
/*  979 */       if (eANMetaAttribute.getAttributeType().equals("U")) {
/*  980 */         this.domain = str;
/*      */       } else {
/*  982 */         String[] arrayOfString = PokUtils.convertToArray(str);
/*  983 */         Vector<String> vector = new Vector(arrayOfString.length);
/*  984 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*  985 */           vector.add(arrayOfString[b]);
/*  986 */           hashSet.add(arrayOfString[b]);
/*      */         } 
/*  988 */         this.domain = vector;
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
/*      */   private void createLSEOBDL() throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException {
/* 1023 */     Vector<String> vector1 = new Vector();
/* 1024 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1025 */     vector1.addElement("SEOID");
/* 1026 */     hashtable.put("SEOID", this.bdlseoid);
/* 1027 */     vector1.addElement("COMNAME");
/* 1028 */     hashtable.put("COMNAME", this.bdlseoid);
/* 1029 */     vector1.addElement("BUNDLPUBDATEMTRGT");
/* 1030 */     hashtable.put("BUNDLPUBDATEMTRGT", this.bdlPubfrom);
/* 1031 */     vector1.addElement("BUNDLMKTGDESC");
/* 1032 */     hashtable.put("BUNDLMKTGDESC", this.bdlMktgDesc);
/*      */ 
/*      */     
/* 1035 */     String str = "722";
/* 1036 */     if (this.lxAbr.getPROJCDNAME() != null) {
/* 1037 */       str = this.lxAbr.getPROJCDNAME();
/*      */     }
/*      */ 
/*      */     
/* 1041 */     vector1.addElement("PROJCDNAM");
/* 1042 */     hashtable.put("PROJCDNAM", str);
/*      */     
/* 1044 */     vector1.addElement("ACCTASGNGRP");
/* 1045 */     hashtable.put("ACCTASGNGRP", "01");
/* 1046 */     vector1.addElement("SPECBID");
/* 1047 */     hashtable.put("SPECBID", "11458");
/* 1048 */     vector1.addElement("AUDIEN");
/* 1049 */     hashtable.put("AUDIEN", this.audien);
/*      */     
/* 1051 */     vector1.addElement("PDHDOMAIN");
/* 1052 */     hashtable.put("PDHDOMAIN", this.domain);
/* 1053 */     vector1.addElement("COUNTRYLIST");
/* 1054 */     Vector<String> vector2 = new Vector();
/*      */     
/* 1056 */     NodeList nodeList = this.rootElem.getElementsByTagName("COUNTRYLIST");
/* 1057 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 1058 */       Node node = nodeList.item(b);
/* 1059 */       if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */         
/* 1063 */         Element element = (Element)nodeList.item(b);
/* 1064 */         if (element.getParentNode() == this.rootElem) {
/* 1065 */           Element element1 = (Element)nodeList.item(b);
/* 1066 */           NodeList nodeList1 = element1.getChildNodes();
/* 1067 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 1068 */             node = nodeList1.item(b1);
/* 1069 */             if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */               
/* 1073 */               Element element2 = (Element)nodeList1.item(b1);
/* 1074 */               String str1 = this.lxAbr.getNodeValue(element2, "COUNTRY");
/* 1075 */               String str2 = (String)this.ctryTbl.get(str1);
/* 1076 */               vector2.addElement(str2);
/*      */             } 
/*      */           }  break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1082 */     hashtable.put("COUNTRYLIST", vector2);
/*      */     
/* 1084 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*      */     try {
/* 1087 */       EntityItem entityItem = new EntityItem(null, this.lxAbr.getProfile(), "WG", this.lxAbr.getProfile().getWGID());
/* 1088 */       this.lseobdlItem = ABRUtil.createEntity(this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDCRLSEOBUNDLE", entityItem, "LSEOBUNDLE", vector1, hashtable, stringBuffer);
/*      */     }
/* 1090 */     catch (EANBusinessRuleException eANBusinessRuleException) {
/* 1091 */       throw eANBusinessRuleException;
/*      */     } finally {
/* 1093 */       if (stringBuffer.length() > 0) {
/* 1094 */         this.lxAbr.addDebug(stringBuffer.toString());
/*      */       }
/* 1096 */       if (this.lseobdlItem == null) {
/* 1097 */         this.lxAbr.addError("ERROR: Can not create LSEOBUNDLE entity for seoid: " + this.bdlseoid);
/*      */       }
/* 1099 */       vector1.clear();
/* 1100 */       hashtable.clear();
/* 1101 */       vector2.clear();
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
/*      */   private EntityItem getLSEO(EntityItem paramEntityItem, Element paramElement, String paramString1, String paramString2, String paramString3, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException, LockException, WorkflowException {
/* 1145 */     EntityItem entityItem = LXLEADSUtil.searchForLSEO(this.lxAbr, paramString3);
/* 1146 */     if (entityItem != null) {
/*      */       
/* 1148 */       this.lxAbr.addDebug("getLSEO found " + entityItem.getKey());
/*      */       
/* 1150 */       paramStringBuffer.append(this.lxAbr.getResourceMsg("REF_SEO_MSG", (Object[])new String[] { entityItem
/* 1151 */               .getEntityGroup().getLongDescription(), paramString3 }));
/*      */     }
/*      */     else {
/*      */       
/* 1155 */       EntityItem entityItem1 = getWWSEO(paramEntityItem, paramElement, paramString3, paramString1, paramString2, paramStringBuffer);
/* 1156 */       if (entityItem1 != null) {
/* 1157 */         String str = this.lxAbr.getNodeValue(paramElement, "PUBFROM");
/* 1158 */         NodeList nodeList = paramElement.getElementsByTagName("COUNTRYLIST");
/*      */         
/* 1160 */         if (nodeList == null || nodeList.getLength() != 1) {
/* 1161 */           this.lxAbr.addError("ERROR_INVALIDXML", "COUNTRYLIST");
/*      */         }
/*      */         
/* 1164 */         if (this.lxAbr.getReturnCode() == 0) {
/* 1165 */           Vector<String> vector = new Vector(1);
/* 1166 */           for (byte b = 0; b < nodeList.getLength(); b++) {
/* 1167 */             Node node = nodeList.item(b);
/* 1168 */             if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */               
/* 1172 */               NodeList nodeList1 = nodeList.item(b).getChildNodes();
/* 1173 */               for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 1174 */                 node = nodeList1.item(b1);
/* 1175 */                 if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */                   
/* 1179 */                   Element element = (Element)nodeList1.item(b1);
/* 1180 */                   String str1 = this.lxAbr.getNodeValue(element, "COUNTRY");
/* 1181 */                   String str2 = (String)this.ctryTbl.get(str1);
/* 1182 */                   vector.addElement(str2);
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1186 */           }  entityItem = LXLEADSUtil.createLSEO(this.lxAbr, entityItem1, paramString3, str, vector, this.domain, this.audien);
/*      */           
/* 1188 */           if (entityItem != null) {
/* 1189 */             this.lxAbr.addDebug("getLSEO created " + entityItem.getKey());
/*      */             
/* 1191 */             String str1 = PokUtils.getAttributeValue(paramEntityItem, "COFCAT", "", "", false);
/*      */             
/* 1193 */             paramStringBuffer.append(this.lxAbr.getResourceMsg("CREATED_SEO_MSG", new Object[] { entityItem.getEntityGroup().getLongDescription(), paramString3, str1, paramString1, paramString2 }));
/*      */ 
/*      */             
/* 1196 */             String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "COFCAT");
/* 1197 */             if (!"102".equals(str2)) {
/*      */               
/* 1199 */               NodeList nodeList1 = paramElement.getElementsByTagName("FEATURELIST");
/* 1200 */               for (byte b2 = 0; b2 < nodeList1.getLength(); b2++) {
/* 1201 */                 Node node = nodeList1.item(b2);
/* 1202 */                 if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */                   
/* 1206 */                   NodeList nodeList2 = nodeList1.item(b2).getChildNodes();
/* 1207 */                   for (byte b3 = 0; b3 < nodeList2.getLength(); b3++) {
/* 1208 */                     node = nodeList2.item(b3);
/* 1209 */                     if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */                       
/* 1213 */                       Element element = (Element)nodeList2.item(b3);
/* 1214 */                       String str3 = this.lxAbr.getNodeValue(element, "FEATURECODE");
/* 1215 */                       String str4 = this.lxAbr.getNodeValue(element, "QTY", "1");
/*      */                       
/* 1217 */                       paramStringBuffer.append(this.lxAbr.getResourceMsg("REF_FEATURE_MSG", new Object[] { str3, str4 }));
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1223 */             paramStringBuffer.append(this.lxAbr.getResourceMsg("REF_COUNTRY_MSG", (Object[])null));
/* 1224 */             for (byte b1 = 0; b1 < nodeList.getLength(); b1++) {
/* 1225 */               Node node = nodeList.item(b1);
/* 1226 */               if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */                 
/* 1230 */                 NodeList nodeList1 = nodeList.item(b1).getChildNodes();
/* 1231 */                 for (byte b2 = 0; b2 < nodeList1.getLength(); b2++) {
/* 1232 */                   node = nodeList1.item(b2);
/* 1233 */                   if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */                     
/* 1237 */                     Element element = (Element)nodeList1.item(b2);
/* 1238 */                     String str3 = this.lxAbr.getNodeValue(element, "COUNTRY");
/* 1239 */                     paramStringBuffer.append(str3 + " ");
/*      */                   } 
/*      */                 } 
/*      */               } 
/* 1243 */             }  vector.clear();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1249 */     return entityItem;
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
/*      */   private EntityItem getWWSEO(EntityItem paramEntityItem, Element paramElement, String paramString1, String paramString2, String paramString3, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException, LockException, WorkflowException {
/* 1272 */     EntityItem entityItem = LXLEADSUtil.searchForWWSEO(this.lxAbr, paramString1);
/* 1273 */     if (entityItem != null) {
/* 1274 */       this.lxAbr.addDebug("getWWSEO found " + entityItem.getKey());
/*      */     } else {
/*      */       
/* 1277 */       String str = this.lxAbr.getNodeValue(paramElement, "SEOTECHDESC");
/* 1278 */       entityItem = LXLEADSUtil.createWWSEO(this.lxAbr, paramEntityItem, paramString1, str, this.domain);
/* 1279 */       if (entityItem != null) {
/* 1280 */         this.lxAbr.addDebug("getWWSEO created " + entityItem.getKey());
/* 1281 */         String str1 = PokUtils.getAttributeValue(paramEntityItem, "COFCAT", "", "", false);
/*      */         
/* 1283 */         paramStringBuffer.append(this.lxAbr.getResourceMsg("CREATED_SEO_MSG", new Object[] { entityItem
/* 1284 */                 .getEntityGroup().getLongDescription(), paramString1, str1, paramString2, paramString3 }) + "<br />");
/*      */ 
/*      */         
/* 1287 */         String str2 = paramString2 + ":" + paramString3 + ":" + paramString1;
/* 1288 */         Vector<EntityItem> vector = (Vector)this.tmfTbl.get(str2);
/* 1289 */         if (vector != null) {
/* 1290 */           this.lxAbr.addDebug("getWWSEO: tmf key: " + str2 + " size: " + vector.size());
/* 1291 */           for (byte b = 0; b < vector.size(); b++) {
/* 1292 */             this.lxAbr.addDebug("tmf[" + b + "]" + ((EntityItem)vector.elementAt(b)).getKey());
/*      */           }
/* 1294 */           LXLEADSUtil.createFeatureRefs(this.lxAbr, entityItem, paramEntityItem, vector, this.psQtyTbl);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1299 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1305 */     return "Create Special Bid - LSEOBUNDLE";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTitle() {
/* 1312 */     return "LSEOBUNDLE Special Bid from LEADS" + ((this.lxAbr.getReturnCode() == 0) ? " created" : "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/* 1319 */     return "1.11";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\LXLEADSADDBUNDLEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */