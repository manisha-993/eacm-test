/*      */ package COM.ibm.eannounce.abr.psg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.sg.LXABRInterface;
/*      */ import COM.ibm.eannounce.abr.sg.LXABRSTATUS;
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.LinkActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.eannounce.objects.WorkflowException;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Hashtable;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LXLEADSADDVARABR
/*      */   implements LXABRInterface
/*      */ {
/*      */   private static final String CTO_SRCHACTION_NAME = "LDSRDCTO";
/*      */   private static final String VAR_SRCHACTION_NAME = "LDSRDVAR";
/*      */   private static final String PR_SRCHACTION_NAME = "LDSRDPR";
/*      */   private static final String CVAR_SRCHACTION_NAME = "LDSRDCVAR";
/*      */   private static final String VAR_CREATEACTION_NAME = "LDCRPRVAR";
/*      */   private static final String CTOVAR_LINKACTION_NAME = "LDLINKCTOVAR";
/*      */   private static final String VARSBB_LINKACTION_NAME = "LDLINKSBBPNUMB";
/*      */   private static final String CVAR_CREATEACTION_NAME = "LDCRVARCVAR";
/*      */   private static final String GENERALAREA_SRCHACTION_NAME = "LDSRDGENAREA";
/*  196 */   private static Hashtable GENAREACODE_TBL = null;
/*  197 */   private Hashtable generalAreaTbl = new Hashtable<>();
/*      */   
/*  199 */   private LXABRSTATUS lxAbr = null;
/*  200 */   private String dtsOfData = null;
/*  201 */   private Element rootElem = null;
/*  202 */   private String factSheetNum = null;
/*  203 */   private String pnumb = null;
/*  204 */   private String pubfrom = null;
/*  205 */   private String ctomodel = null;
/*  206 */   private String techDesc = null;
/*  207 */   private EntityItem ctoItem = null;
/*  208 */   private EntityItem projItem = null;
/*  209 */   private EntityList ctoSbbList = null;
/*  210 */   private Vector sbbVct = new Vector();
/*  211 */   private Object audien = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  250 */   private static final Hashtable AUDIEN_TBL = new Hashtable<>(); static {
/*  251 */     AUDIEN_TBL.put("Enterprise Direct", "10062");
/*  252 */     Vector<String> vector = new Vector(3);
/*  253 */     vector.add("10062");
/*  254 */     vector.add("10067");
/*  255 */     AUDIEN_TBL.put("Odyssey", vector);
/*  256 */     vector = new Vector<>(3);
/*  257 */     vector.add("10062");
/*  258 */     vector.add("10046");
/*  259 */     vector.add("10048");
/*  260 */     AUDIEN_TBL.put("Indirect", vector);
/*      */   }
/*  262 */   private Hashtable sbbQtyTbl = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  288 */   private static final Hashtable VARRATECARDCODE_TBL = new Hashtable<>(); static {
/*  289 */     VARRATECARDCODE_TBL.put("0060", "11452");
/*  290 */     VARRATECARDCODE_TBL.put("0050", "11451");
/*  291 */     VARRATECARDCODE_TBL.put("0010", "11447");
/*  292 */     VARRATECARDCODE_TBL.put("0020", "11448");
/*  293 */     VARRATECARDCODE_TBL.put("0030", "11449");
/*  294 */     VARRATECARDCODE_TBL.put("0080", "11454");
/*  295 */     VARRATECARDCODE_TBL.put("0070", "11453");
/*  296 */     VARRATECARDCODE_TBL.put("0040", "11450");
/*  297 */     VARRATECARDCODE_TBL.put("0090", "11455");
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
/*      */   private void validateXML() throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  332 */     this.dtsOfData = this.lxAbr.getNodeValue(this.rootElem, "DTSOFDATA");
/*  333 */     this.factSheetNum = this.lxAbr.getNodeValue(this.rootElem, "FACTSHEETNUM");
/*  334 */     this.pnumb = this.lxAbr.getNodeValue(this.rootElem, "PNUMB");
/*  335 */     this.pubfrom = this.lxAbr.getNodeValue(this.rootElem, "PUBFROM", "");
/*  336 */     this.techDesc = this.lxAbr.getNodeValue(this.rootElem, "TECHDESC", "");
/*  337 */     this.ctomodel = this.lxAbr.getNodeValue(this.rootElem, "CTOMODEL");
/*      */ 
/*      */     
/*  340 */     deriveAudien(this.lxAbr.getNodeValue(this.rootElem, "ACCOUNTTYPE", ""));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  347 */     NodeList nodeList = this.rootElem.getElementsByTagName("COUNTRYLIST");
/*      */ 
/*      */     
/*  350 */     if (nodeList == null || nodeList.getLength() != 1) {
/*  351 */       this.lxAbr.addError("ERROR_INVALIDXML", "COUNTRYLIST");
/*      */     } else {
/*  353 */       this.lxAbr.verifyChildNodes(nodeList, "COUNTRYLIST", "COUNTRYELEMENT", 1);
/*      */ 
/*      */       
/*  356 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/*  357 */         Node node = nodeList.item(b);
/*  358 */         if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */           
/*  362 */           NodeList nodeList1 = nodeList.item(b).getChildNodes();
/*  363 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  364 */             node = nodeList1.item(b1);
/*  365 */             if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */               
/*  369 */               Element element = (Element)nodeList1.item(b1);
/*  370 */               String str = this.lxAbr.getNodeValue(element, "COUNTRY");
/*  371 */               EntityItem entityItem = searchForGENERALAREA(str);
/*  372 */               if (entityItem == null)
/*      */               {
/*  374 */                 this.lxAbr.addError("ERROR_COUNTRY", str);
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
/*      */   public String getHeader() {
/*  387 */     if (this.dtsOfData == null || this.factSheetNum == null || this.ctomodel == null || this.pnumb == null) {
/*  388 */       return "";
/*      */     }
/*      */     
/*  391 */     return this.lxAbr.getResourceMsg("CTO_HEADER", new Object[] { this.dtsOfData, this.factSheetNum, this.ctomodel, this.pnumb });
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
/*      */   public void validateData(LXABRSTATUS paramLXABRSTATUS, Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  407 */     this.lxAbr = paramLXABRSTATUS;
/*  408 */     this.rootElem = paramElement;
/*      */     
/*  410 */     validateXML();
/*      */     
/*  412 */     if (this.lxAbr.getReturnCode() != 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  418 */     searchForCTO();
/*      */ 
/*      */ 
/*      */     
/*  422 */     searchForVAR();
/*      */ 
/*      */ 
/*      */     
/*  426 */     searchForPROJ();
/*      */ 
/*      */ 
/*      */     
/*  430 */     searchForSBB();
/*      */ 
/*      */ 
/*      */     
/*  434 */     searchForCVARs();
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
/*      */   private void searchForCTO() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  453 */     Vector<String> vector1 = new Vector(1);
/*  454 */     vector1.addElement("COFPNUMB");
/*  455 */     Vector<String> vector2 = new Vector(1);
/*  456 */     vector2.addElement(this.ctomodel);
/*      */     
/*  458 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/*  460 */       StringBuffer stringBuffer = new StringBuffer();
/*  461 */       this.lxAbr.addDebug("Searching for CTO for attrs: " + vector1 + " values: " + vector2);
/*  462 */       arrayOfEntityItem = ABRUtil.doSearch(this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDSRDCTO", "CTO", false, vector1, vector2, stringBuffer);
/*      */       
/*  464 */       if (stringBuffer.length() > 0) {
/*  465 */         this.lxAbr.addDebug(stringBuffer.toString());
/*      */       }
/*  467 */     } catch (SBRException sBRException) {
/*      */       
/*  469 */       StringWriter stringWriter = new StringWriter();
/*  470 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  471 */       this.lxAbr.addDebug("searchForCTO SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/*      */     
/*  474 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  475 */       this.ctoItem = arrayOfEntityItem[0];
/*  476 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  477 */         this.lxAbr.addDebug("searchForCTO found " + arrayOfEntityItem[b].getKey());
/*      */       }
/*  479 */       if (arrayOfEntityItem.length > 1) {
/*  480 */         StringBuffer stringBuffer = new StringBuffer();
/*  481 */         stringBuffer.append("More than one CTO found for " + this.ctomodel);
/*  482 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  483 */           stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*      */         }
/*  485 */         this.lxAbr.addError(stringBuffer.toString());
/*      */       } else {
/*      */         
/*  488 */         this.ctoSbbList = this.lxAbr.getDatabase().getEntityList(this.lxAbr.getProfile(), new ExtractActionItem(null, this.lxAbr
/*  489 */               .getDatabase(), this.lxAbr.getProfile(), "EXRPT0CTO5"), arrayOfEntityItem);
/*      */         
/*  491 */         this.lxAbr.addDebug("searchForCTO SBB VE:\n" + PokUtils.outputList(this.ctoSbbList));
/*      */         
/*  493 */         this.ctoItem = this.ctoSbbList.getParentEntityGroup().getEntityItem(0);
/*      */       } 
/*      */     } else {
/*      */       
/*  497 */       this.lxAbr.addError("ERROR_CTO", null);
/*      */     } 
/*  499 */     vector1.clear();
/*  500 */     vector2.clear();
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
/*      */   private void searchForVAR() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  521 */     Vector<String> vector1 = new Vector(1);
/*  522 */     vector1.addElement("OFFERINGPNUMB");
/*  523 */     Vector<String> vector2 = new Vector(1);
/*  524 */     vector2.addElement(this.pnumb);
/*      */     
/*  526 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/*  528 */       StringBuffer stringBuffer = new StringBuffer();
/*  529 */       this.lxAbr.addDebug("Searching for VAR for attrs: " + vector1 + " values: " + vector2);
/*  530 */       arrayOfEntityItem = ABRUtil.doSearch(this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDSRDVAR", "VAR", false, vector1, vector2, stringBuffer);
/*      */       
/*  532 */       if (stringBuffer.length() > 0) {
/*  533 */         this.lxAbr.addDebug(stringBuffer.toString());
/*      */       }
/*  535 */     } catch (SBRException sBRException) {
/*      */       
/*  537 */       StringWriter stringWriter = new StringWriter();
/*  538 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  539 */       this.lxAbr.addDebug("searchForVAR SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/*      */     
/*  542 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  543 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  544 */         this.lxAbr.addDebug("searchForVAR found " + arrayOfEntityItem[b].getKey());
/*      */       }
/*      */       
/*  547 */       this.lxAbr.addError("ERROR_VAR", null);
/*      */     } 
/*      */     
/*  550 */     vector1.clear();
/*  551 */     vector2.clear();
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
/*      */   private void searchForPROJ() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  572 */     Vector<String> vector1 = new Vector(1);
/*  573 */     vector1.addElement("PROJECTNAME");
/*  574 */     Vector<String> vector2 = new Vector(1);
/*  575 */     String str = "LEADS FEED SPECIAL BID";
/*  576 */     vector2.addElement(str);
/*      */     
/*  578 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/*  580 */       StringBuffer stringBuffer = new StringBuffer();
/*  581 */       this.lxAbr.addDebug("Searching for PR for attrs: " + vector1 + " values: " + vector2);
/*  582 */       arrayOfEntityItem = ABRUtil.doSearch(this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDSRDPR", "PR", false, vector1, vector2, stringBuffer);
/*      */       
/*  584 */       if (stringBuffer.length() > 0) {
/*  585 */         this.lxAbr.addDebug(stringBuffer.toString());
/*      */       }
/*  587 */     } catch (SBRException sBRException) {
/*      */       
/*  589 */       StringWriter stringWriter = new StringWriter();
/*  590 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  591 */       this.lxAbr.addDebug("searchForPROJ SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/*      */     
/*  594 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  595 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  596 */         this.lxAbr.addDebug("searchForPROJ found " + arrayOfEntityItem[b].getKey());
/*      */       }
/*  598 */       if (arrayOfEntityItem.length > 1) {
/*  599 */         StringBuffer stringBuffer = new StringBuffer();
/*  600 */         stringBuffer.append("More than one PR found for " + str);
/*  601 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  602 */           stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*      */         }
/*  604 */         this.lxAbr.addError(stringBuffer.toString());
/*      */       } 
/*  606 */       this.projItem = arrayOfEntityItem[0];
/*      */     }
/*      */     else {
/*      */       
/*  610 */       this.lxAbr.addError("ERROR_PROJ", null);
/*      */     } 
/*      */     
/*  613 */     vector1.clear();
/*  614 */     vector2.clear();
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
/*      */   private void searchForSBB() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  642 */     NodeList nodeList = this.rootElem.getElementsByTagName("SBBLIST");
/*      */ 
/*      */     
/*  645 */     if (nodeList == null || nodeList.getLength() != 1) {
/*  646 */       this.lxAbr.addError("ERROR_INVALIDXML", "SBBLIST");
/*      */     } else {
/*  648 */       this.lxAbr.verifyChildNodes(nodeList, "SBBLIST", "SBBELEMENT", 1);
/*  649 */       if (this.ctoSbbList == null) {
/*      */         return;
/*      */       }
/*  652 */       EntityGroup entityGroup = this.ctoSbbList.getEntityGroup("SBB");
/*  653 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/*  654 */         Node node = nodeList.item(b);
/*  655 */         if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */           
/*  659 */           NodeList nodeList1 = nodeList.item(b).getChildNodes();
/*  660 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  661 */             node = nodeList1.item(b1);
/*  662 */             if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */               
/*  666 */               Element element = (Element)nodeList1.item(b1);
/*  667 */               String str1 = this.lxAbr.getNodeValue(element, "SBB");
/*  668 */               String str2 = this.lxAbr.getNodeValue(element, "QTY", "1");
/*  669 */               if (str1 != null) {
/*  670 */                 boolean bool = false;
/*      */                 
/*  672 */                 for (byte b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*  673 */                   EntityItem entityItem = entityGroup.getEntityItem(b2);
/*  674 */                   String str = PokUtils.getAttributeValue(entityItem, "SBBPNUMB", "", "", false);
/*  675 */                   if (str.equals(str1)) {
/*  676 */                     this.sbbQtyTbl.put(entityItem.getKey(), str2);
/*  677 */                     this.sbbVct.add(entityItem);
/*  678 */                     this.lxAbr.addDebug("searchForSBB found: " + entityItem.getKey() + " for " + str1);
/*  679 */                     bool = true;
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*  683 */                 if (!bool)
/*      */                 {
/*  685 */                   this.lxAbr.addError("ERROR_SBB", str1);
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
/*      */   
/*      */   private void searchForCVARs() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  737 */     Vector<String> vector = new Vector(2);
/*  738 */     vector.addElement("PNUMB_CT");
/*  739 */     vector.addElement("GENAREANAMEREGION");
/*  740 */     vector.addElement("GENAREANAME");
/*      */ 
/*      */     
/*  743 */     NodeList nodeList = this.rootElem.getElementsByTagName("COUNTRYLIST");
/*  744 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/*  745 */       Node node = nodeList.item(b);
/*  746 */       if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */         
/*  750 */         NodeList nodeList1 = nodeList.item(b).getChildNodes();
/*  751 */         for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  752 */           node = nodeList1.item(b1);
/*  753 */           if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */             
/*  757 */             Element element = (Element)nodeList1.item(b1);
/*  758 */             String str1 = this.lxAbr.getNodeValue(element, "PNUMB_CT");
/*  759 */             String str2 = this.lxAbr.getNodeValue(element, "COUNTRY");
/*      */             
/*  761 */             EntityItem entityItem = searchForGENERALAREA(str2);
/*      */             
/*  763 */             Vector<String> vector1 = new Vector(2);
/*  764 */             vector1.addElement(str1);
/*      */ 
/*      */             
/*  767 */             vector1.addElement(PokUtils.getAttributeFlagValue(entityItem, "GENAREAPARENT"));
/*  768 */             vector1.addElement(PokUtils.getAttributeFlagValue(entityItem, "GENAREANAME"));
/*  769 */             this.lxAbr.addDebug("Searching for CVAR for attrs: " + vector + " values: " + vector1);
/*  770 */             EntityItem[] arrayOfEntityItem = null;
/*      */             try {
/*  772 */               StringBuffer stringBuffer = new StringBuffer();
/*  773 */               arrayOfEntityItem = ABRUtil.doSearch(this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDSRDCVAR", "CVAR", false, vector, vector1, stringBuffer);
/*      */               
/*  775 */               if (stringBuffer.length() > 0) {
/*  776 */                 this.lxAbr.addDebug(stringBuffer.toString());
/*      */               }
/*  778 */             } catch (SBRException sBRException) {
/*      */               
/*  780 */               StringWriter stringWriter = new StringWriter();
/*  781 */               sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  782 */               this.lxAbr.addDebug("searchForCVARs SBRException: " + stringWriter.getBuffer().toString());
/*      */             } 
/*  784 */             if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  785 */               for (byte b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/*  786 */                 this.lxAbr.addDebug("searchForCVARs found " + arrayOfEntityItem[b2].getKey());
/*      */               }
/*      */               
/*  789 */               this.lxAbr.addError("ERROR_CVAR", new Object[] { str1, str2 });
/*      */             } 
/*  791 */             vector1.clear();
/*      */           } 
/*      */         } 
/*      */       } 
/*  795 */     }  vector.clear();
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
/*      */   public void execute() throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*  844 */     EntityItem entityItem = createVAR();
/*  845 */     if (entityItem != null) {
/*      */       
/*  847 */       createSBBRefs(entityItem);
/*      */       
/*  849 */       createCVARs(entityItem);
/*      */     } 
/*      */     
/*  852 */     if (this.lxAbr.getReturnCode() == 0) {
/*      */       
/*  854 */       StringBuffer stringBuffer = new StringBuffer();
/*  855 */       stringBuffer.append(this.lxAbr.getResourceMsg("CREATED_VAR_MSG", new Object[] { this.pnumb, this.ctomodel }));
/*      */ 
/*      */       
/*  858 */       NodeList nodeList1 = this.rootElem.getElementsByTagName("SBBLIST");
/*  859 */       for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  860 */         Node node = nodeList1.item(b1);
/*  861 */         if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */           
/*  865 */           NodeList nodeList = nodeList1.item(b1).getChildNodes();
/*  866 */           for (byte b = 0; b < nodeList.getLength(); b++) {
/*  867 */             node = nodeList.item(b);
/*  868 */             if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */               
/*  872 */               Element element = (Element)nodeList.item(b);
/*  873 */               String str1 = this.lxAbr.getNodeValue(element, "SBB");
/*  874 */               String str2 = this.lxAbr.getNodeValue(element, "QTY", "1");
/*      */               
/*  876 */               stringBuffer.append(this.lxAbr.getResourceMsg("REF_SBB_MSG", new Object[] { str1, str2 }));
/*      */             } 
/*      */           } 
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
/*  889 */       stringBuffer.append("&nbsp;&nbsp;&nbsp;in <br />");
/*      */       
/*  891 */       NodeList nodeList2 = this.rootElem.getElementsByTagName("COUNTRYLIST");
/*  892 */       for (byte b2 = 0; b2 < nodeList2.getLength(); b2++) {
/*  893 */         Node node = nodeList2.item(b2);
/*  894 */         if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */           
/*  898 */           NodeList nodeList = nodeList2.item(b2).getChildNodes();
/*  899 */           for (byte b = 0; b < nodeList.getLength(); b++) {
/*  900 */             node = nodeList.item(b);
/*  901 */             if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */               
/*  905 */               Element element = (Element)nodeList.item(b);
/*  906 */               String str1 = this.lxAbr.getNodeValue(element, "PNUMB_CT");
/*  907 */               String str2 = this.lxAbr.getNodeValue(element, "COUNTRY");
/*      */               
/*  909 */               stringBuffer.append(this.lxAbr.getResourceMsg("REF_PNUMBCT_MSG", new Object[] { str2, str1 }));
/*      */             } 
/*      */           } 
/*      */         } 
/*  913 */       }  this.lxAbr.addOutput(stringBuffer.toString());
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
/*      */   private EntityItem createVAR() throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*  952 */     EntityItem entityItem = null;
/*      */     
/*  954 */     Vector<String> vector = new Vector();
/*  955 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  956 */     vector.addElement("OFFERINGPNUMB");
/*  957 */     hashtable.put("OFFERINGPNUMB", this.pnumb);
/*  958 */     vector.addElement("VARMODELNAME");
/*  959 */     hashtable.put("VARMODELNAME", this.techDesc);
/*  960 */     vector.addElement("VARPNUMBDESC");
/*  961 */     hashtable.put("VARPNUMBDESC", this.techDesc);
/*  962 */     vector.addElement("VARANNOUNCETGT");
/*  963 */     hashtable.put("VARANNOUNCETGT", this.pubfrom);
/*      */     
/*  965 */     vector.addElement("VARSPECIALBID");
/*  966 */     hashtable.put("VARSPECIALBID", "11458");
/*      */     
/*  968 */     vector.addElement("PDHDOMAIN");
/*  969 */     hashtable.put("PDHDOMAIN", "PCD");
/*      */     
/*  971 */     vector.addElement("UNSPSC");
/*  972 */     String str1 = PokUtils.getAttributeFlagValue(this.ctoItem, "UNSPSC");
/*  973 */     if (str1 == null) {
/*  974 */       str1 = "43171806";
/*  975 */       this.lxAbr.addDebug(this.ctoItem.getKey() + " did not have UNSPSC attr meta: " + this.ctoItem
/*  976 */           .getEntityGroup().getMetaAttribute("UNSPSC"));
/*      */     } 
/*  978 */     hashtable.put("UNSPSC", str1);
/*  979 */     vector.addElement("VARRATECARDCODE");
/*  980 */     String str2 = PokUtils.getAttributeFlagValue(this.ctoItem, "CVOFRATECARDCODE");
/*  981 */     if (str2 != null) {
/*  982 */       str2 = (String)VARRATECARDCODE_TBL.get(str2);
/*  983 */       this.lxAbr.addDebug("Mapped CVOFRATECARDCODE " + PokUtils.getAttributeFlagValue(this.ctoItem, "CVOFRATECARDCODE") + " to " + str2);
/*      */     } 
/*      */     
/*  986 */     if (str2 == null) {
/*  987 */       str2 = "11453";
/*  988 */       this.lxAbr.addDebug(this.ctoItem.getKey() + " did not have CVOFRATECARDCODE attr meta: " + this.ctoItem
/*  989 */           .getEntityGroup().getMetaAttribute("CVOFRATECARDCODE"));
/*      */     } 
/*      */     
/*  992 */     hashtable.put("VARRATECARDCODE", str2);
/*      */     
/*  994 */     StringBuffer stringBuffer = new StringBuffer();
/*  995 */     entityItem = ABRUtil.createEntity(this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDCRPRVAR", this.projItem, "VAR", vector, hashtable, stringBuffer);
/*      */     
/*  997 */     if (stringBuffer.length() > 0) {
/*  998 */       this.lxAbr.addDebug(stringBuffer.toString());
/*      */     }
/* 1000 */     if (entityItem != null) {
/*      */       
/* 1002 */       LinkActionItem linkActionItem = new LinkActionItem(null, this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDLINKCTOVAR");
/* 1003 */       EntityItem[] arrayOfEntityItem1 = { this.ctoItem };
/* 1004 */       EntityItem[] arrayOfEntityItem2 = { entityItem };
/*      */       
/* 1006 */       linkActionItem.setParentEntityItems(arrayOfEntityItem1);
/* 1007 */       linkActionItem.setChildEntityItems(arrayOfEntityItem2);
/* 1008 */       this.lxAbr.getDatabase().executeAction(this.lxAbr.getProfile(), linkActionItem);
/* 1009 */       this.lxAbr.addDebug("Linked " + entityItem.getKey() + " to " + this.ctoItem.getKey());
/*      */     } else {
/* 1011 */       this.lxAbr.addError("ERROR: Can not create VAR entity for pnumb: " + this.pnumb);
/*      */     } 
/* 1013 */     vector.clear();
/* 1014 */     hashtable.clear();
/*      */     
/* 1016 */     return entityItem;
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
/*      */   private void createSBBRefs(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/* 1037 */     LinkActionItem linkActionItem = new LinkActionItem(null, this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDLINKSBBPNUMB");
/* 1038 */     EntityItem[] arrayOfEntityItem1 = { paramEntityItem };
/* 1039 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[this.sbbVct.size()];
/*      */ 
/*      */     
/* 1042 */     this.sbbVct.copyInto((Object[])arrayOfEntityItem2);
/*      */ 
/*      */     
/* 1045 */     linkActionItem.setParentEntityItems(arrayOfEntityItem1);
/* 1046 */     linkActionItem.setChildEntityItems(arrayOfEntityItem2);
/* 1047 */     this.lxAbr.getDatabase().executeAction(this.lxAbr.getProfile(), linkActionItem);
/*      */ 
/*      */     
/* 1050 */     Profile profile = this.lxAbr.getProfile().getNewInstance(this.lxAbr.getDatabase());
/* 1051 */     String str = this.lxAbr.getDatabase().getDates().getNow();
/* 1052 */     profile.setValOnEffOn(str, str);
/*      */ 
/*      */ 
/*      */     
/* 1056 */     EntityList entityList = this.lxAbr.getDatabase().getEntityList(profile, new ExtractActionItem(null, this.lxAbr
/* 1057 */           .getDatabase(), profile, "EXRPT0VAR5"), arrayOfEntityItem1);
/*      */ 
/*      */     
/* 1060 */     this.lxAbr.addDebug("createSBBRefs list using VE EXRPT0VAR5 after link\n" + PokUtils.outputList(entityList));
/* 1061 */     EntityGroup entityGroup = entityList.getEntityGroup("SBB");
/* 1062 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1063 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/* 1064 */       String str1 = (String)this.sbbQtyTbl.get(entityItem1.getKey());
/* 1065 */       EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 1066 */       this.lxAbr.addDebug(entityItem1.getKey() + " use qty: " + str1 + " on " + entityItem2.getKey());
/* 1067 */       if (str1 != null && !str1.equals("1")) {
/*      */         
/* 1069 */         StringBuffer stringBuffer = new StringBuffer();
/* 1070 */         ABRUtil.setText(entityItem2, "VARSBBQTY", str1, stringBuffer);
/* 1071 */         if (stringBuffer.length() > 0) {
/* 1072 */           this.lxAbr.addDebug(stringBuffer.toString());
/*      */         }
/*      */         
/* 1075 */         entityItem2.commit(this.lxAbr.getDatabase(), null);
/*      */       } 
/*      */     } 
/* 1078 */     entityList.dereference();
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
/*      */   private void createCVARs(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/* 1103 */     NodeList nodeList = this.rootElem.getElementsByTagName("COUNTRYLIST"); byte b;
/* 1104 */     label21: for (b = 0; b < nodeList.getLength(); b++) {
/* 1105 */       Node node = nodeList.item(b);
/* 1106 */       if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */         
/* 1110 */         NodeList nodeList1 = nodeList.item(b).getChildNodes();
/* 1111 */         for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 1112 */           node = nodeList1.item(b1);
/* 1113 */           if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */             
/* 1117 */             Element element = (Element)nodeList1.item(b1);
/* 1118 */             String str1 = this.lxAbr.getNodeValue(element, "PNUMB_CT");
/* 1119 */             String str2 = this.lxAbr.getNodeValue(element, "COUNTRY");
/* 1120 */             this.lxAbr.addDebug("Creating CVAR for pnumbct " + str1 + " ctry " + str2);
/* 1121 */             createCVAR(paramEntityItem, str1, str2);
/* 1122 */             if (this.lxAbr.getReturnCode() != 0) {
/*      */               break label21;
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
/*      */   private void createCVAR(EntityItem paramEntityItem, String paramString1, String paramString2) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException {
/* 1162 */     EntityItem entityItem1 = null;
/*      */     
/* 1164 */     Vector<String> vector = new Vector();
/* 1165 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1166 */     vector.addElement("PNUMB_CT");
/* 1167 */     hashtable.put("PNUMB_CT", paramString1);
/* 1168 */     vector.addElement("PNUMBDESC_CVAR");
/* 1169 */     hashtable.put("PNUMBDESC_CVAR", this.techDesc);
/* 1170 */     vector.addElement("TARGANNDATE_CVAR");
/* 1171 */     hashtable.put("TARGANNDATE_CVAR", this.pubfrom);
/* 1172 */     vector.addElement("DEMANDFLAG_CVAR");
/* 1173 */     hashtable.put("DEMANDFLAG_CVAR", "10681");
/* 1174 */     vector.addElement("PDHDOMAIN");
/* 1175 */     hashtable.put("PDHDOMAIN", "PCD");
/*      */ 
/*      */     
/* 1178 */     if (this.audien != null) {
/* 1179 */       vector.addElement("CATALOGNAME_CVAR");
/* 1180 */       hashtable.put("CATALOGNAME_CVAR", this.audien);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1186 */     EntityItem entityItem2 = searchForGENERALAREA(paramString2);
/* 1187 */     vector.addElement("GENAREANAMEREGION");
/* 1188 */     hashtable.put("GENAREANAMEREGION", PokUtils.getAttributeFlagValue(entityItem2, "GENAREAPARENT"));
/* 1189 */     vector.addElement("GENAREANAME");
/* 1190 */     hashtable.put("GENAREANAME", PokUtils.getAttributeFlagValue(entityItem2, "GENAREANAME"));
/*      */     
/* 1192 */     StringBuffer stringBuffer = new StringBuffer();
/* 1193 */     entityItem1 = ABRUtil.createEntity(this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDCRVARCVAR", paramEntityItem, "CVAR", vector, hashtable, stringBuffer);
/*      */     
/* 1195 */     if (stringBuffer.length() > 0) {
/* 1196 */       this.lxAbr.addDebug(stringBuffer.toString());
/*      */     }
/* 1198 */     if (entityItem1 == null) {
/* 1199 */       this.lxAbr.addError("ERROR: Can not create CVAR entity for pnumb: " + this.pnumb + " ctry " + paramString2);
/*      */     } else {
/* 1201 */       entityItem1.getEntityGroup().getEntityList().dereference();
/*      */     } 
/* 1203 */     vector.clear();
/* 1204 */     hashtable.clear();
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
/*      */   private String getGenAreaCodeForCtry(String paramString) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1218 */     if (GENAREACODE_TBL == null) {
/* 1219 */       GENAREACODE_TBL = new Hashtable<>();
/*      */       
/* 1221 */       EntityGroup entityGroup = new EntityGroup(null, this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "GENERALAREA", "Edit", false);
/* 1222 */       EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)entityGroup.getMetaAttribute("GENAREACODE");
/* 1223 */       if (eANMetaFlagAttribute != null) {
/* 1224 */         for (byte b = 0; b < eANMetaFlagAttribute.getMetaFlagCount(); b++) {
/*      */           
/* 1226 */           MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(b);
/* 1227 */           if (metaFlag.isExpired()) {
/* 1228 */             this.lxAbr.getDatabase().debug(4, "LXLEADSADDVARABR.getGenAreaCodeForCtry skipping expired flag: " + metaFlag + "[" + metaFlag
/* 1229 */                 .getFlagCode() + "] for GENERALAREA.GENAREACODE");
/*      */           } else {
/*      */             
/* 1232 */             GENAREACODE_TBL.put(metaFlag.toString(), metaFlag.getFlagCode());
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1237 */     return (String)GENAREACODE_TBL.get(paramString);
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
/*      */   private EntityItem searchForGENERALAREA(String paramString) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 1257 */     EntityItem entityItem = (EntityItem)this.generalAreaTbl.get(paramString);
/* 1258 */     if (entityItem != null) {
/* 1259 */       return entityItem;
/*      */     }
/*      */ 
/*      */     
/* 1263 */     String str = getGenAreaCodeForCtry(paramString);
/* 1264 */     this.lxAbr.addDebug("searchForGENERALAREA country: " + paramString + " genAreaCodeFlg: " + str + "\n");
/* 1265 */     this.lxAbr.getDatabase().debug(4, "LXLEADSADDVARABR.searchForGENERALAREA country: " + paramString + " genAreaCodeFlg: " + str);
/* 1266 */     if (str != null) {
/* 1267 */       Vector<String> vector1 = new Vector(1);
/* 1268 */       vector1.addElement("GENAREACODE");
/* 1269 */       Vector<String> vector2 = new Vector(1);
/* 1270 */       vector2.addElement(str);
/* 1271 */       EntityItem[] arrayOfEntityItem = null;
/*      */       try {
/* 1273 */         StringBuffer stringBuffer = new StringBuffer();
/* 1274 */         arrayOfEntityItem = ABRUtil.doSearch(this.lxAbr.getDatabase(), this.lxAbr.getProfile(), "LDSRDGENAREA", "GENERALAREA", false, vector1, vector2, stringBuffer);
/*      */         
/* 1276 */         if (stringBuffer.length() > 0) {
/* 1277 */           this.lxAbr.addDebug(stringBuffer.toString());
/*      */         }
/* 1279 */       } catch (SBRException sBRException) {
/*      */         
/* 1281 */         StringWriter stringWriter = new StringWriter();
/* 1282 */         sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 1283 */         this.lxAbr.addDebug("searchForGENERALAREA SBRException: " + stringWriter.getBuffer().toString());
/*      */       } 
/*      */       
/* 1286 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 1287 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1288 */           this.lxAbr.addDebug("searchForGENERALAREA found[" + b + "] " + arrayOfEntityItem[b].getKey() + "\n");
/*      */         }
/*      */         
/* 1291 */         if (arrayOfEntityItem.length > 1) {
/* 1292 */           StringBuffer stringBuffer = new StringBuffer();
/* 1293 */           stringBuffer.append("More than one GENERALAREA found for " + paramString);
/* 1294 */           for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 1295 */             stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*      */           }
/* 1297 */           this.lxAbr.addError(stringBuffer.toString());
/*      */         } 
/* 1299 */         entityItem = arrayOfEntityItem[0];
/* 1300 */         this.generalAreaTbl.put(paramString, entityItem);
/*      */       } 
/* 1302 */       vector1.clear();
/* 1303 */       vector2.clear();
/*      */     } else {
/* 1305 */       this.lxAbr.addDebug("searchForGENERALAREA GENAREACODE table: " + GENAREACODE_TBL + "\n");
/*      */     } 
/*      */     
/* 1308 */     return entityItem;
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
/*      */   private void deriveAudien(String paramString) {
/* 1323 */     this.audien = AUDIEN_TBL.get(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 1330 */     this.lxAbr = null;
/* 1331 */     this.dtsOfData = null;
/* 1332 */     this.rootElem = null;
/* 1333 */     this.factSheetNum = null;
/* 1334 */     this.pnumb = null;
/* 1335 */     this.ctomodel = null;
/* 1336 */     this.pubfrom = null;
/* 1337 */     this.techDesc = null;
/* 1338 */     this.ctoItem = null;
/* 1339 */     this.projItem = null;
/*      */     
/* 1341 */     if (this.sbbVct != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1349 */       this.sbbVct.clear();
/* 1350 */       this.sbbVct = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1360 */     this.generalAreaTbl.clear();
/* 1361 */     this.generalAreaTbl = null;
/*      */     
/* 1363 */     this.sbbQtyTbl.clear();
/* 1364 */     this.sbbQtyTbl = null;
/* 1365 */     if (this.ctoSbbList != null) {
/* 1366 */       this.ctoSbbList.dereference();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/* 1375 */     return "$Revision: 1.10 $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTitle() {
/* 1384 */     return "Variant Offering Special Bid from LEADS" + ((this.lxAbr.getReturnCode() == 0) ? " created" : "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1391 */     return "Create Special Bid - Variant";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\LXLEADSADDVARABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */