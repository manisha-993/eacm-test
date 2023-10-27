/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANUtility;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.LinkActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.eannounce.objects.WorkflowException;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnID;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
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
/*      */ public abstract class SPSTABR
/*      */ {
/*      */   private static final String MODEL_SRCHACTION_NAME = "SRDMODEL4";
/*      */   private static final String WWSEO_SRCHACTION_NAME = "SRDWWSEO5";
/*      */   private static final String LSEO_SRCHACTION_NAME = "SRDLSEO3";
/*      */   private static final String LSEOBUNDLE_SRCHACTION_NAME = "SRDLSEOBUNDLE1";
/*      */   private static final String MODEL_CREATE_ACTION = "CRMODEL";
/*      */   protected static final String MODELAVAIL_LINK_ACTION = "LINKAVAILMODEL";
/*      */   protected static final String MODELAVAIL_CREATE_ACTION = "CRPEERAVAIL";
/*      */   private static final String WWSEO_CREATE_ACTION = "CRPEERWWSEO";
/*      */   private static final String LSEO_CREATE_ACTION = "CRPEERLSEO";
/*      */   protected static final String LSEOAVAIL_CREATE_ACTION = "CRPEERAVAIL9";
/*      */   protected static final String LSEOAVAIL_LINK_ACTION = "LINKAVAILLSEO";
/*      */   private static final String LSEOBUNDLE_CREATE_ACTION = "CRLSEOBUNDLE";
/*      */   protected static final String LSEOBUNDLEAVAIL_CREATE_ACTION = "CRPEERAVAIL10";
/*      */   protected static final String LSEOBUNDLEAVAIL_LINK_ACTION = "LINKAVAILLSEOBUNDLE";
/*      */   protected static final String LSEOBUNDLELSEO_LINK_ACTION = "LINKLSEOLSEOBUNDLE";
/*      */   private static final String GENERALAREA_SRCHACTION_NAME = "LDSRDGENAREA";
/*      */   protected static final String SPST_PROJCDNAM = "SPSTIDL";
/*      */   protected static final String PDHDOMAIN = "0050";
/*      */   protected static final String FLAG_COL_DESC_CLASS = "C";
/*      */   protected static final String FLAG_COL_DESC_LONG = "L";
/*      */   protected static final String FLAG_COL_DESC_SHORT = "S";
/*  137 */   protected Hashtable flag_table = new Hashtable<>();
/*      */   
/*  139 */   private Hashtable SG_GENAREACODE_TBL = null;
/*      */   
/*      */   protected SPSTABRSTATUS spstAbr;
/*      */   
/*      */   protected Element rootElem;
/*  144 */   protected String dtsOfData = null;
/*  145 */   protected String factSheetNum = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String TEMP_STR = "@@";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String NON_SAP = "Non Sap";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   private static final Hashtable AUDIEN_TBL = new Hashtable<>(); static {
/*  170 */     Vector<String> vector1 = new Vector();
/*  171 */     vector1.addElement("10046");
/*  172 */     vector1.addElement("10048");
/*  173 */     vector1.addElement("10054");
/*  174 */     vector1.addElement("10062");
/*  175 */     AUDIEN_TBL.put("ALL", vector1);
/*  176 */     Vector<String> vector2 = new Vector();
/*  177 */     vector2.addElement("10046");
/*  178 */     vector2.addElement("10048");
/*  179 */     AUDIEN_TBL.put("BP Distributor", vector2);
/*  180 */     AUDIEN_TBL.put("Bp Reseller", vector2);
/*  181 */     Vector<String> vector3 = new Vector();
/*  182 */     vector3.addElement("10067");
/*  183 */     AUDIEN_TBL.put("DAC", vector3);
/*  184 */     AUDIEN_TBL.put("IBM.COM", vector1);
/*  185 */     Vector<String> vector4 = new Vector();
/*  186 */     vector4.addElement("10062");
/*  187 */     AUDIEN_TBL.put("LED", vector4);
/*  188 */     AUDIEN_TBL.put("LED Custom Service", vector4);
/*  189 */     Vector<String> vector5 = new Vector();
/*  190 */     vector5.addElement("10055");
/*  191 */     AUDIEN_TBL.put("NONE", vector5);
/*  192 */     Vector<String> vector6 = new Vector();
/*  193 */     vector6.add("@@");
/*  194 */     AUDIEN_TBL.put("Non Sap", vector6);
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
/*  208 */   private static final Hashtable TAXCATG_TBL = new Hashtable<>();
/*      */   static {
/*  210 */     TAXCATG_TBL.put("1652", createSimpleVector(new Integer(9)));
/*  211 */     TAXCATG_TBL.put("1464", createSimpleVector(new Integer(10)));
/*  212 */     Vector<Integer> vector = new Vector();
/*  213 */     vector.add(new Integer(7));
/*  214 */     vector.add(new Integer(2));
/*  215 */     TAXCATG_TBL.put("1470", vector);
/*  216 */     TAXCATG_TBL.put("1508", createSimpleVector(new Integer(6)));
/*  217 */     TAXCATG_TBL.put("1651", createSimpleVector(new Integer(26)));
/*  218 */     TAXCATG_TBL.put("1531", createSimpleVector(new Integer(29)));
/*  219 */     TAXCATG_TBL.put("1635", createSimpleVector(new Integer(33)));
/*  220 */     TAXCATG_TBL.put("1524", createSimpleVector(new Integer(28)));
/*  221 */     TAXCATG_TBL.put("1553", createSimpleVector(new Integer(31)));
/*  222 */     TAXCATG_TBL.put("1456", createSimpleVector(new Integer(27)));
/*      */   }
/*  224 */   private static final Hashtable SALES_ORG_TBL = new Hashtable<>();
/*      */   
/*      */   static {
/*  227 */     Vector<String> vector7 = new Vector();
/*  228 */     vector7.add("CON");
/*  229 */     vector7.add("COF");
/*  230 */     SALES_ORG_TBL.put("1470", vector7);
/*  231 */     SALES_ORG_TBL.put("1508", createSimpleVector("GER"));
/*  232 */     SALES_ORG_TBL.put("1651", createSimpleVector("SGUK"));
/*  233 */     SALES_ORG_TBL.put("1531", createSimpleVector("SGIR"));
/*  234 */     SALES_ORG_TBL.put("1635", createSimpleVector("SGTW"));
/*  235 */     SALES_ORG_TBL.put("1524", createSimpleVector("SGHK"));
/*  236 */     SALES_ORG_TBL.put("1553", createSimpleVector("SGMA"));
/*  237 */     SALES_ORG_TBL.put("1456", createSimpleVector("SGBR"));
/*      */   }
/*      */   
/*      */   private static Vector createSimpleVector(Object paramObject) {
/*  241 */     Vector<Object> vector = new Vector();
/*  242 */     vector.add(paramObject);
/*  243 */     return vector;
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
/*      */   void validateData(SPSTABRSTATUS paramSPSTABRSTATUS, Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  260 */     this.spstAbr = paramSPSTABRSTATUS;
/*  261 */     this.rootElem = paramElement;
/*      */ 
/*      */ 
/*      */     
/*  265 */     this.dtsOfData = this.spstAbr.getNodeValue(paramElement, "DTSOFDATA", true);
/*  266 */     this.factSheetNum = this.spstAbr.getNodeValue(paramElement, "SPSTSHEETNUM", true);
/*      */ 
/*      */     
/*  269 */     NodeList nodeList = paramElement.getElementsByTagName("AVAILLIST");
/*  270 */     this.spstAbr.verifyChildNodes(paramElement, "AVAILLIST", "AVAILELEMENT", 1);
/*  271 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/*  272 */       Node node = nodeList.item(b);
/*  273 */       if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */         
/*  277 */         NodeList nodeList1 = nodeList.item(b).getChildNodes();
/*  278 */         for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  279 */           Node node1 = nodeList1.item(b1);
/*  280 */           if (node1.getNodeType() == 1) {
/*      */ 
/*      */             
/*  283 */             Element element = (Element)node1;
/*  284 */             String str1 = this.spstAbr.getNodeValue(element, "AVAILTYPE", true);
/*  285 */             String str2 = paramElement.getNodeName();
/*      */ 
/*      */             
/*  288 */             boolean bool1 = ("New".equalsIgnoreCase(str1) && ("SPSTAVAILMODEL".equals(str2) || "SPSTAVAILBUNDLE".equals(str2))) ? true : false;
/*  289 */             boolean bool2 = ("Withdrawal".equalsIgnoreCase(str1) && "SPSTWDAVAIL".equals(str2)) ? true : false;
/*  290 */             if (!bool1 && !bool2) {
/*  291 */               this.spstAbr.addError("AVAIL_TYPE_WRONG", (Object[])new String[] { str1, str2 });
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  296 */             this.spstAbr.verifyChildNodes(element, "COUNTRYLIST", "COUNTRY", 1);
/*  297 */             checkMultiFlagAttr(element, "COUNTRYLIST", "COUNTRY", "COUNTRYLIST");
/*  298 */             if (this.spstAbr.getReturnCode() == 0) {
/*  299 */               checkRelatedEntities(element);
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
/*      */   void dereference() {
/*  314 */     this.flag_table.clear();
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
/*      */   protected String getHeader() {
/*  338 */     if (this.dtsOfData == null || this.factSheetNum == null) {
/*  339 */       return "";
/*      */     }
/*      */     
/*  342 */     return this.spstAbr.getResourceMsg("SPST_COMMON_HEADER", new Object[] { this.dtsOfData, this.factSheetNum });
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
/*      */   protected EntityItem searchForModel(String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  371 */     EntityItem entityItem = null;
/*  372 */     if (paramString1 != null && paramString2 != null) {
/*  373 */       Vector<String> vector1 = new Vector(2);
/*  374 */       vector1.addElement("MACHTYPEATR");
/*  375 */       vector1.addElement("MODELATR");
/*  376 */       Vector<String> vector2 = new Vector(2);
/*  377 */       vector2.addElement(paramString1);
/*  378 */       vector2.addElement(paramString2);
/*  379 */       vector1.addElement("PDHDOMAIN");
/*  380 */       vector2.addElement("0050");
/*      */       
/*  382 */       EntityItem[] arrayOfEntityItem = null;
/*      */       
/*      */       try {
/*  385 */         StringBuffer stringBuffer = new StringBuffer();
/*  386 */         arrayOfEntityItem = ABRUtil.doSearch(this.spstAbr.getDatabase(), this.spstAbr
/*  387 */             .getProfile(), "SRDMODEL4", "MODEL", false, vector1, vector2, stringBuffer);
/*      */         
/*  389 */         if (stringBuffer.length() > 0) {
/*  390 */           this.spstAbr.addDebug(stringBuffer.toString());
/*      */         }
/*  392 */       } catch (SBRException sBRException) {
/*      */ 
/*      */         
/*  395 */         StringWriter stringWriter = new StringWriter();
/*  396 */         sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  397 */         this.spstAbr.addDebug("searchForModel SBRException: " + stringWriter
/*  398 */             .getBuffer().toString());
/*      */       } 
/*  400 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  401 */         entityItem = arrayOfEntityItem[0];
/*      */       }
/*  403 */       vector1.clear();
/*  404 */       vector2.clear();
/*      */     } 
/*      */     
/*  407 */     return entityItem;
/*      */   }
/*      */ 
/*      */   
/*      */   protected EntityItem searchForWWSEO(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  412 */     if (paramString == null || paramString.trim().length() == 0) {
/*  413 */       return null;
/*      */     }
/*  415 */     EntityItem entityItem = null;
/*  416 */     Vector<String> vector1 = new Vector(1);
/*  417 */     vector1.addElement("SEOID");
/*  418 */     Vector<String> vector2 = new Vector(1);
/*  419 */     vector2.addElement(paramString);
/*  420 */     vector1.addElement("PDHDOMAIN");
/*  421 */     vector2.addElement("0050");
/*      */     
/*  423 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/*  425 */       StringBuffer stringBuffer = new StringBuffer();
/*  426 */       arrayOfEntityItem = ABRUtil.doSearch(this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "SRDWWSEO5", "WWSEO", false, vector1, vector2, stringBuffer);
/*      */ 
/*      */       
/*  429 */       if (stringBuffer.length() > 0) {
/*  430 */         this.spstAbr.addDebug(stringBuffer.toString());
/*      */       }
/*  432 */     } catch (SBRException sBRException) {
/*      */ 
/*      */       
/*  435 */       StringWriter stringWriter = new StringWriter();
/*  436 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  437 */       this.spstAbr.addDebug("searchForWWSEO SBRException: " + stringWriter
/*  438 */           .getBuffer().toString());
/*      */     } 
/*  440 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  441 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  442 */         this.spstAbr.addDebug("SPSTABR.searchForWWSEO found " + arrayOfEntityItem[b]
/*  443 */             .getKey());
/*      */       }
/*  445 */       if (arrayOfEntityItem.length > 1) {
/*  446 */         StringBuffer stringBuffer = new StringBuffer();
/*  447 */         stringBuffer.append("More than one WWSEO found for " + paramString);
/*  448 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  449 */           stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*      */         }
/*  451 */         this.spstAbr.addError(stringBuffer.toString());
/*      */       } 
/*  453 */       entityItem = arrayOfEntityItem[0];
/*      */     } 
/*      */     
/*  456 */     vector1.clear();
/*  457 */     vector2.clear();
/*  458 */     return entityItem;
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
/*      */   protected EntityItem searchForLSEO(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  472 */     if (paramString == null || paramString.trim().length() == 0) {
/*  473 */       return null;
/*      */     }
/*  475 */     EntityItem entityItem = null;
/*  476 */     Vector<String> vector1 = new Vector(1);
/*  477 */     vector1.addElement("SEOID");
/*  478 */     Vector<String> vector2 = new Vector(1);
/*  479 */     vector2.addElement(paramString);
/*  480 */     vector1.addElement("PDHDOMAIN");
/*  481 */     vector2.addElement("0050");
/*      */     
/*  483 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/*  485 */       StringBuffer stringBuffer = new StringBuffer();
/*  486 */       arrayOfEntityItem = ABRUtil.doSearch(this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "SRDLSEO3", "LSEO", false, vector1, vector2, stringBuffer);
/*      */ 
/*      */       
/*  489 */       if (stringBuffer.length() > 0) {
/*  490 */         this.spstAbr.addDebug(stringBuffer.toString());
/*      */       }
/*  492 */     } catch (SBRException sBRException) {
/*      */ 
/*      */       
/*  495 */       StringWriter stringWriter = new StringWriter();
/*  496 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  497 */       this.spstAbr.addDebug("searchForLSEO SBRException: " + stringWriter
/*  498 */           .getBuffer().toString());
/*      */     } 
/*  500 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  501 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  502 */         this.spstAbr.addDebug("SPSTABR.searchForLSEO found " + arrayOfEntityItem[b]
/*  503 */             .getKey());
/*      */       }
/*  505 */       if (arrayOfEntityItem.length > 1) {
/*  506 */         StringBuffer stringBuffer = new StringBuffer();
/*  507 */         stringBuffer.append("More than one LSEO found for " + paramString);
/*  508 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  509 */           stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*      */         }
/*  511 */         this.spstAbr.addError(stringBuffer.toString());
/*      */       } 
/*  513 */       entityItem = arrayOfEntityItem[0];
/*      */     } 
/*  515 */     vector1.clear();
/*  516 */     vector2.clear();
/*  517 */     return entityItem;
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
/*      */   protected EntityItem searchForLSEOBUNDLE(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  531 */     if (paramString == null || paramString.trim().length() == 0) {
/*  532 */       return null;
/*      */     }
/*  534 */     EntityItem entityItem = null;
/*  535 */     Vector<String> vector1 = new Vector(1);
/*  536 */     vector1.addElement("SEOID");
/*  537 */     Vector<String> vector2 = new Vector(1);
/*  538 */     vector2.addElement(paramString);
/*  539 */     vector1.addElement("PDHDOMAIN");
/*  540 */     vector2.addElement("0050");
/*      */     
/*  542 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/*  544 */       StringBuffer stringBuffer = new StringBuffer();
/*  545 */       arrayOfEntityItem = ABRUtil.doSearch(this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "SRDLSEOBUNDLE1", "LSEOBUNDLE", false, vector1, vector2, stringBuffer);
/*      */ 
/*      */       
/*  548 */       if (stringBuffer.length() > 0) {
/*  549 */         this.spstAbr.addDebug(stringBuffer.toString());
/*      */       }
/*  551 */     } catch (SBRException sBRException) {
/*      */ 
/*      */       
/*  554 */       StringWriter stringWriter = new StringWriter();
/*  555 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  556 */       this.spstAbr.addDebug("searchForLSEO SBRException: " + stringWriter
/*  557 */           .getBuffer().toString());
/*      */     } 
/*  559 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  560 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  561 */         this.spstAbr.addDebug("SPSTABR.searchForLSEOBUNDLE found " + arrayOfEntityItem[b]
/*  562 */             .getKey());
/*      */       }
/*  564 */       if (arrayOfEntityItem.length > 1) {
/*  565 */         StringBuffer stringBuffer = new StringBuffer();
/*  566 */         stringBuffer.append("More than one LSEOBUNDLE found for " + paramString);
/*  567 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  568 */           stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*      */         }
/*  570 */         this.spstAbr.addError(stringBuffer.toString());
/*      */       } 
/*  572 */       entityItem = arrayOfEntityItem[0];
/*      */     } 
/*  574 */     vector1.clear();
/*  575 */     vector2.clear();
/*  576 */     return entityItem;
/*      */   }
/*      */   
/*      */   private String getFlagBaseShortDesc(String paramString1, String paramString2) throws SQLException, MiddlewareException {
/*  580 */     return executeSql("select descriptionclass from opicm.metadescription where DESCRIPTIONTYPE = '" + paramString1 + "' and SHORTDESCRIPTION='" + paramString2 + "'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String executeSql(String paramString) throws SQLException, MiddlewareException {
/*  587 */     Database database = this.spstAbr.getDatabase();
/*  588 */     String str1 = null;
/*  589 */     PreparedStatement preparedStatement = null;
/*  590 */     ResultSet resultSet = null;
/*  591 */     String str2 = database.getNow(0);
/*  592 */     String str3 = "  and valto > '" + str2 + "' and effto > '" + str2 + "' with ur";
/*  593 */     this.spstAbr.addDebug("get the flag code using sql: " + paramString + str3);
/*      */     try {
/*  595 */       preparedStatement = database.getPDHConnection().prepareStatement(paramString + str3);
/*  596 */       resultSet = preparedStatement.executeQuery();
/*  597 */       if (resultSet.next()) {
/*  598 */         str1 = resultSet.getString(1);
/*      */       }
/*      */     } finally {
/*      */       
/*  602 */       if (resultSet != null) {
/*      */         try {
/*  604 */           resultSet.close();
/*  605 */         } catch (Exception exception) {
/*  606 */           System.err.println("getFlagBaseShortDesc(), unable to close result. " + exception);
/*      */         } 
/*  608 */         resultSet = null;
/*      */       } 
/*  610 */       if (preparedStatement != null) {
/*      */         try {
/*  612 */           preparedStatement.close();
/*  613 */         } catch (Exception exception) {
/*  614 */           System.err.println("getFlagBaseShortDesc(), unable to close ps. " + exception);
/*      */         } 
/*  616 */         preparedStatement = null;
/*      */       } 
/*  618 */       database.commit();
/*  619 */       database.freeStatement();
/*  620 */       database.isPending();
/*      */     } 
/*  622 */     this.spstAbr.addDebug("returned flag code is " + str1);
/*  623 */     return str1;
/*      */   }
/*      */   private String getFlagBaseDescClass(String paramString1, String paramString2) throws SQLException, MiddlewareException {
/*  626 */     return executeSql("select descriptionclass from opicm.metadescription where DESCRIPTIONTYPE = '" + paramString1 + "' and descriptionclass='" + paramString2 + "'");
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isValidNewAvail(EntityItem paramEntityItem, Vector paramVector, String paramString) throws SQLException, MiddlewareException {
/*  631 */     Vector<?> vector = getRelatedAvailCnty(paramEntityItem, paramString);
/*  632 */     Vector vector1 = new Vector();
/*  633 */     vector1.addAll(paramVector);
/*  634 */     vector1.retainAll(vector);
/*  635 */     if (vector1.size() > 0) {
/*  636 */       this.spstAbr.addDebug("There are same country in the existed avail: " + vector1);
/*  637 */       return false;
/*      */     } 
/*  639 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector getRelatedAvailCnty(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/*  644 */     Vector<String> vector = new Vector();
/*  645 */     Database database = this.spstAbr.getDatabase();
/*  646 */     PreparedStatement preparedStatement = null;
/*  647 */     ResultSet resultSet = null;
/*  648 */     String str1 = database.getNow(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  658 */     String str2 = "select entityid, attributevalue from opicm.flag where entitytype = 'AVAIL' and attributecode = 'COUNTRYLIST' and entityid in (select entityid from opicm.flag where entitytype = 'AVAIL' and valto > '" + str1 + "' and effto > '" + str1 + "' and attributecode = 'AVAILTYPE' and attributevalue = '" + paramString + "' and entityid in (select entity2id from opicm.relator where entity1id = " + paramEntityItem.getEntityID() + " and entity1type = '" + paramEntityItem.getEntityType() + "' and entity2type = 'AVAIL' and valto > '" + str1 + "' and effto > '" + str1 + "')) and valto> '" + str1 + "' and effto > '" + str1 + "' order by entityid with ur";
/*      */ 
/*      */ 
/*      */     
/*  662 */     this.spstAbr.addDebug("get the flag code using sql: " + str2);
/*      */     try {
/*  664 */       preparedStatement = database.getPDHConnection().prepareStatement(str2);
/*  665 */       resultSet = preparedStatement.executeQuery();
/*  666 */       int i = -1;
/*  667 */       StringBuffer stringBuffer = new StringBuffer();
/*  668 */       while (resultSet.next()) {
/*  669 */         int j = resultSet.getInt(1);
/*  670 */         String str = resultSet.getString(2);
/*  671 */         if (i != j) {
/*  672 */           this.spstAbr.addDebug("AVAIL" + j + "'s country : " + stringBuffer.toString());
/*  673 */           stringBuffer = new StringBuffer();
/*  674 */           i = j;
/*      */         } 
/*  676 */         stringBuffer.append(" " + str + " ");
/*  677 */         vector.add(str);
/*      */       } 
/*  679 */       this.spstAbr.addDebug("AVAIL" + i + "'s country : " + stringBuffer.toString());
/*      */     } finally {
/*  681 */       if (resultSet != null) {
/*      */         try {
/*  683 */           resultSet.close();
/*  684 */         } catch (Exception exception) {
/*  685 */           this.spstAbr.addDebug("getRelatedAvailCnty(), unable to close result. " + exception);
/*      */         } 
/*  687 */         resultSet = null;
/*      */       } 
/*  689 */       if (preparedStatement != null) {
/*      */         try {
/*  691 */           preparedStatement.close();
/*  692 */         } catch (Exception exception) {
/*  693 */           this.spstAbr.addDebug("getRelatedAvailCnty(), unable to close ps. " + exception);
/*      */         } 
/*  695 */         preparedStatement = null;
/*      */       } 
/*  697 */       database.commit();
/*  698 */       database.freeStatement();
/*  699 */       database.isPending();
/*      */     } 
/*  701 */     this.spstAbr.addDebug("returned country list is " + vector);
/*  702 */     return vector;
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
/*      */   protected void checkSingleFlagAttr(Element paramElement, String paramString1, String paramString2, String paramString3) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  716 */     String str = this.spstAbr.getNodeValue(paramElement, paramString1, false);
/*  717 */     if (str.trim().length() == 0) {
/*  718 */       this.spstAbr.addDebug("checkSingleFlagAttr: value is empty, do not do any checking for this flag");
/*      */       return;
/*      */     } 
/*  721 */     if (str != null) {
/*  722 */       if ("COUNTRYLIST".equals(paramString2)) {
/*  723 */         StringBuffer stringBuffer = new StringBuffer();
/*      */         
/*  725 */         EntityItem entityItem = searchForGENERALAREA(this.spstAbr.getDatabase(), this.spstAbr
/*  726 */             .getProfile(), str, stringBuffer);
/*  727 */         if (stringBuffer.length() > 0) {
/*  728 */           this.spstAbr.addDebug(stringBuffer.toString());
/*      */         }
/*  730 */         if (entityItem != null) {
/*      */           
/*  732 */           String str1 = PokUtils.getAttributeFlagValue(entityItem, "GENAREANAME");
/*  733 */           if (str1 != null) {
/*  734 */             this.flag_table.put(paramString1 + str, str1);
/*  735 */             this.spstAbr.addDebug("valid country: " + str1);
/*      */           } else {
/*      */             
/*  738 */             this.spstAbr.addError("ERROR_COUNTRY", str);
/*      */           } 
/*      */         } else {
/*  741 */           this.spstAbr.addError("GENERALAREA item not found for COUNTRY: " + str);
/*      */         } 
/*      */       } else {
/*  744 */         Object object = this.flag_table.get(paramString1 + str);
/*  745 */         if (object == null) {
/*  746 */           if ("AUDIENCE".equalsIgnoreCase(paramString1)) {
/*  747 */             Object object1 = deriveAudien(str);
/*  748 */             this.spstAbr.addDebug("checkSingleFlagAttr " + paramString2 + " desc : " + str + " flagcode " + object1);
/*  749 */             if (object1 != null) {
/*  750 */               this.flag_table.put(paramString1 + str, object1);
/*      */             }
/*      */             
/*      */             return;
/*      */           } 
/*  755 */           if ("S".equals(paramString3)) {
/*  756 */             object = getFlagBaseShortDesc(paramString2, str);
/*  757 */           } else if ("C".equals(paramString3)) {
/*  758 */             object = getFlagBaseDescClass(paramString2, str);
/*      */           } else {
/*  760 */             PDGUtility pDGUtility = new PDGUtility();
/*  761 */             String[] arrayOfString = pDGUtility.getFlagCodeForExactDesc(this.spstAbr.getDatabase(), this.spstAbr.getProfile(), paramString2, str);
/*  762 */             if (arrayOfString != null && arrayOfString.length > 0) {
/*  763 */               object = arrayOfString[0];
/*      */             }
/*  765 */             pDGUtility.dereference();
/*      */           } 
/*  767 */           if (object != null) {
/*  768 */             this.flag_table.put(paramString1 + str, object);
/*  769 */             this.spstAbr.addDebug("checkSingleFlagAttr " + paramString2 + " desc : " + str + " flagcode " + object);
/*      */           } else {
/*      */             
/*  772 */             this.spstAbr.addError("NO_MATCH_FLAGCODE", (Object[])new String[] { paramString2, str });
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
/*      */   protected void checkMultiFlagAttr(Element paramElement, String paramString1, String paramString2, String paramString3) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  790 */     NodeList nodeList = paramElement.getElementsByTagName(paramString1);
/*  791 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/*  792 */       Node node = nodeList.item(b);
/*  793 */       if (node.getNodeType() == 1) {
/*      */ 
/*      */         
/*  796 */         Element element = (Element)node;
/*  797 */         if (element.getParentNode() == paramElement) {
/*  798 */           NodeList nodeList1 = element.getChildNodes();
/*  799 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  800 */             Node node1 = nodeList1.item(b1);
/*  801 */             if (node1.getNodeType() == 1)
/*      */             {
/*      */               
/*  804 */               checkSingleFlagAttr((Element)node1, paramString2, paramString3, "L");
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
/*      */   protected Vector generateMultiFlags(Element paramElement, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/*  818 */     HashSet<String> hashSet = new HashSet();
/*  819 */     NodeList nodeList = paramElement.getElementsByTagName(paramString1);
/*  820 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/*  821 */       Node node = nodeList.item(b);
/*  822 */       if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */         
/*  826 */         Element element = (Element)nodeList.item(b);
/*  827 */         if (element.getParentNode() == paramElement) {
/*  828 */           NodeList nodeList1 = element.getChildNodes();
/*  829 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  830 */             node = nodeList1.item(b1);
/*  831 */             if (node.getNodeType() == 1) {
/*      */ 
/*      */               
/*  834 */               Element element1 = (Element)nodeList1.item(b1);
/*  835 */               String str = this.spstAbr.getNodeValue(element1, paramString2, false);
/*  836 */               if (str.trim().length() == 0)
/*  837 */               { this.spstAbr.addDebug("ctryDesc is empty"); }
/*      */               else
/*      */               
/*  840 */               { paramStringBuffer.append(" " + str + " ");
/*  841 */                 if ("AUDIENCE".equalsIgnoreCase(paramString2))
/*  842 */                 { this.spstAbr.addDebug("AUDIENCE tag will be processed, it is vector, not string");
/*  843 */                   if ("Non Sap".equals(str)) {
/*  844 */                     this.spstAbr.addDebug("will not deal with Non sap there");
/*      */                   } else {
/*  846 */                     hashSet.addAll((Vector)this.flag_table.get(paramString2 + str));
/*      */                   }  }
/*      */                 else
/*  849 */                 { String str1 = (String)this.flag_table.get(paramString2 + str);
/*  850 */                   if (str1 != null)
/*  851 */                   { hashSet.add(str1); }
/*      */                   else
/*  853 */                   { this.spstAbr.addDebug("cannot can get the correct flag code in the hashtable, something wrong when checking"); }  }  } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  858 */     }  Vector<String> vector = new Vector();
/*  859 */     vector.addAll(hashSet);
/*  860 */     this.spstAbr.addDebug("generateMultiFlags() - for  tagname : " + vector);
/*  861 */     return vector;
/*      */   }
/*      */   
/*      */   protected String getSingleFlag(Element paramElement, String paramString) {
/*  865 */     String str1 = this.spstAbr.getNodeValue(paramElement, paramString, false);
/*  866 */     String str2 = (String)this.flag_table.get(paramString + str1);
/*  867 */     if (str2 == null) {
/*  868 */       this.spstAbr.addDebug("cannot can get the correct flag code in the hashtable, something wrong when checking,set it to empty");
/*  869 */       str2 = "";
/*      */     } 
/*  871 */     return str2;
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
/*      */   protected EntityItem createWWSEO(SPSTABRSTATUS paramSPSTABRSTATUS, EntityItem paramEntityItem, Vector paramVector, Hashtable<?, ?> paramHashtable) {
/*  897 */     String str = (String)paramHashtable.get("SEOID");
/*      */     
/*  899 */     EntityItem entityItem = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  906 */     Vector<String> vector1 = new Vector();
/*  907 */     vector1.addAll(paramVector);
/*  908 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  909 */     hashtable.putAll(paramHashtable);
/*      */     
/*  911 */     vector1.addElement("PROJCDNAM");
/*  912 */     hashtable.put("PROJCDNAM", "SPSTIDL");
/*  913 */     vector1.addElement("SEOORDERCODE");
/*  914 */     hashtable.put("SEOORDERCODE", "10");
/*  915 */     vector1.addElement("SPECBID");
/*  916 */     hashtable.put("SPECBID", "11457");
/*      */     
/*  918 */     vector1.addElement("PRODID");
/*  919 */     hashtable.put("PRODID", "PID5");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  924 */     vector1.addElement("RATECARDCD");
/*  925 */     hashtable.put("RATECARDCD", "ZZZ-0000");
/*      */     
/*  927 */     vector1.addElement("UNSPSCCD");
/*  928 */     hashtable.put("UNSPSCCD", "95161200");
/*      */     
/*  930 */     vector1.addElement("PDHDOMAIN");
/*  931 */     Vector<String> vector2 = new Vector();
/*  932 */     vector2.add("0050");
/*  933 */     hashtable.put("PDHDOMAIN", vector2);
/*      */     
/*  935 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     try {
/*  937 */       entityItem = ABRUtil.createEntity(paramSPSTABRSTATUS.getDatabase(), paramSPSTABRSTATUS.getProfile(), "CRPEERWWSEO", paramEntityItem, "WWSEO", vector1, hashtable, stringBuffer);
/*      */       
/*  939 */       stringBuffer.append("wwseo was created. WWSEO.getkey() = " + entityItem.getKey());
/*      */     }
/*  941 */     catch (Exception exception) {
/*  942 */       stringBuffer.append(exception.getMessage());
/*      */     } finally {
/*  944 */       if (stringBuffer.length() > 0) {
/*  945 */         paramSPSTABRSTATUS.addDebug(stringBuffer.toString());
/*      */       }
/*  947 */       if (entityItem == null) {
/*  948 */         paramSPSTABRSTATUS.addError("ERROR: Can not create WWSEO entity for seoid: " + str);
/*      */       }
/*      */ 
/*      */       
/*  952 */       vector1.clear();
/*  953 */       hashtable.clear();
/*      */     } 
/*      */     
/*  956 */     return entityItem;
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
/*      */   protected EntityItem createLSEO(SPSTABRSTATUS paramSPSTABRSTATUS, EntityItem paramEntityItem, Vector paramVector, Hashtable<?, ?> paramHashtable) {
/*  984 */     String str = (String)paramHashtable.get("SEOID");
/*      */     
/*  986 */     EntityItem entityItem = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  993 */     Vector<String> vector1 = new Vector();
/*  994 */     vector1.addAll(paramVector);
/*  995 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  996 */     hashtable.putAll(paramHashtable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1002 */     vector1.addElement("PRCINDC");
/* 1003 */     hashtable.put("PRCINDC", "yes");
/* 1004 */     vector1.addElement("ZEROPRICE");
/* 1005 */     hashtable.put("ZEROPRICE", "120");
/* 1006 */     vector1.addElement("PLANRELEVANT");
/* 1007 */     hashtable.put("PLANRELEVANT", "10");
/* 1008 */     vector1.addElement("FLFILSYSINDC");
/* 1009 */     hashtable.put("FLFILSYSINDC", "F00070");
/*      */     
/* 1011 */     vector1.addElement("PDHDOMAIN");
/* 1012 */     Vector<String> vector2 = new Vector();
/* 1013 */     vector2.add("0050");
/* 1014 */     hashtable.put("PDHDOMAIN", vector2);
/*      */     
/* 1016 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     try {
/* 1018 */       entityItem = ABRUtil.createEntity(paramSPSTABRSTATUS.getDatabase(), paramSPSTABRSTATUS.getProfile(), "CRPEERLSEO", paramEntityItem, "LSEO", vector1, hashtable, stringBuffer);
/*      */       
/* 1020 */       stringBuffer.append("lseo was created. LSEO.getKey() = " + entityItem.getKey());
/*      */     }
/* 1022 */     catch (Exception exception) {
/* 1023 */       stringBuffer.append(exception.getMessage());
/*      */     } finally {
/* 1025 */       if (stringBuffer.length() > 0) {
/* 1026 */         paramSPSTABRSTATUS.addDebug(stringBuffer.toString());
/*      */       }
/* 1028 */       if (entityItem == null) {
/* 1029 */         paramSPSTABRSTATUS.addError("ERROR: Can not create LSEO entity for seoid: " + str);
/*      */       }
/*      */ 
/*      */       
/* 1033 */       vector1.clear();
/* 1034 */       hashtable.clear();
/*      */     } 
/*      */     
/* 1037 */     return entityItem;
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
/*      */   protected EntityItem createMODEL(SPSTABRSTATUS paramSPSTABRSTATUS, Vector paramVector, Hashtable<?, ?> paramHashtable) {
/* 1073 */     String str = (new StringBuilder()).append(paramHashtable.get("MACHTYPEATR")).append("-").append(paramHashtable.get("MODELATR")).toString();
/*      */     
/* 1075 */     EntityItem entityItem = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1082 */     Vector<String> vector1 = new Vector();
/* 1083 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1093 */     vector1.addAll(paramVector);
/* 1094 */     hashtable.putAll(paramHashtable);
/*      */     
/* 1096 */     vector1.addElement("SPECBID");
/* 1097 */     hashtable.put("SPECBID", "11457");
/* 1098 */     vector1.addElement("PRCINDC");
/* 1099 */     hashtable.put("PRCINDC", "yes");
/* 1100 */     vector1.addElement("ZEROPRICE");
/* 1101 */     hashtable.put("ZEROPRICE", "100");
/*      */     
/* 1103 */     vector1.addElement("UNSPSCCD");
/* 1104 */     hashtable.put("UNSPSCCD", "95161200");
/*      */     
/* 1106 */     vector1.addElement("UNSPSCCDUOM");
/* 1107 */     hashtable.put("UNSPSCCDUOM", "0010");
/*      */     
/* 1109 */     vector1.addElement("RATECARDCD");
/* 1110 */     hashtable.put("RATECARDCD", "ZZZ-0000");
/*      */     
/* 1112 */     vector1.addElement("SARINDC");
/* 1113 */     hashtable.put("SARINDC", "S00020");
/*      */     
/* 1115 */     vector1.addElement("SYSIDUNIT");
/* 1116 */     hashtable.put("SYSIDUNIT", "S00030");
/*      */     
/* 1118 */     vector1.addElement("PRODID");
/* 1119 */     hashtable.put("PRODID", "PID5");
/*      */     
/* 1121 */     vector1.addElement("PLANRELEVANT");
/* 1122 */     hashtable.put("PLANRELEVANT", "10");
/*      */     
/* 1124 */     vector1.addElement("FLFILSYSINDC");
/* 1125 */     hashtable.put("FLFILSYSINDC", "F00070");
/*      */     
/* 1127 */     vector1.addElement("PROJCDNAM");
/* 1128 */     hashtable.put("PROJCDNAM", "SPSTIDL");
/*      */ 
/*      */     
/* 1131 */     vector1.addElement("PDHDOMAIN");
/* 1132 */     Vector<String> vector2 = new Vector();
/* 1133 */     vector2.add("0050");
/* 1134 */     hashtable.put("PDHDOMAIN", vector2);
/*      */     
/* 1136 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     try {
/* 1138 */       EntityItem entityItem1 = new EntityItem(null, paramSPSTABRSTATUS.getProfile(), "WG", paramSPSTABRSTATUS.getProfile().getWGID());
/* 1139 */       entityItem = ABRUtil.createEntity(paramSPSTABRSTATUS.getDatabase(), paramSPSTABRSTATUS.getProfile(), "CRMODEL", entityItem1, "MODEL", vector1, hashtable, stringBuffer);
/*      */       
/* 1141 */       stringBuffer.append("model was created. MODEL.getkey() = " + entityItem.getKey());
/*      */ 
/*      */     
/*      */     }
/* 1145 */     catch (Exception exception) {
/* 1146 */       stringBuffer.append(exception.getMessage());
/*      */     } finally {
/* 1148 */       if (stringBuffer.length() > 0) {
/* 1149 */         paramSPSTABRSTATUS.addDebug(stringBuffer.toString());
/*      */       }
/* 1151 */       if (entityItem == null) {
/* 1152 */         paramSPSTABRSTATUS.addError("ERROR: Can not create MODEL entity for MACHTYPEATR-MODELATR: " + str);
/*      */       }
/*      */ 
/*      */       
/* 1156 */       vector1.clear();
/* 1157 */       hashtable.clear();
/*      */     } 
/*      */     
/* 1160 */     return entityItem;
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
/*      */   protected EntityItem createLSEOBUNDLE(SPSTABRSTATUS paramSPSTABRSTATUS, Vector paramVector, Hashtable<?, ?> paramHashtable) {
/* 1180 */     String str = (String)paramHashtable.get("SEOID");
/*      */     
/* 1182 */     EntityItem entityItem = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1189 */     Vector<String> vector1 = new Vector();
/* 1190 */     vector1.addAll(paramVector);
/* 1191 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1192 */     hashtable.putAll(paramHashtable);
/*      */ 
/*      */ 
/*      */     
/* 1196 */     vector1.addElement("BUNDLETYPE");
/* 1197 */     hashtable.put("BUNDLETYPE", "102");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1204 */     vector1.addElement("SPECBID");
/* 1205 */     hashtable.put("SPECBID", "11457");
/* 1206 */     vector1.addElement("PRCINDC");
/* 1207 */     hashtable.put("PRCINDC", "yes");
/* 1208 */     vector1.addElement("ZEROPRICE");
/* 1209 */     hashtable.put("ZEROPRICE", "120");
/* 1210 */     vector1.addElement("OFERCONFIGTYPE");
/* 1211 */     hashtable.put("OFERCONFIGTYPE", "FCNFIG");
/*      */     
/* 1213 */     vector1.addElement("PRODID");
/* 1214 */     hashtable.put("PRODID", "PID5");
/*      */     
/* 1216 */     vector1.addElement("PLANRELEVANT");
/* 1217 */     hashtable.put("PLANRELEVANT", "10");
/*      */     
/* 1219 */     vector1.addElement("FLFILSYSINDC");
/* 1220 */     hashtable.put("FLFILSYSINDC", "F00070");
/*      */     
/* 1222 */     vector1.addElement("PROJCDNAM");
/* 1223 */     hashtable.put("PROJCDNAM", "SPSTIDL");
/*      */ 
/*      */     
/* 1226 */     vector1.addElement("PDHDOMAIN");
/* 1227 */     Vector<String> vector2 = new Vector();
/* 1228 */     vector2.add("0050");
/* 1229 */     hashtable.put("PDHDOMAIN", vector2);
/*      */     
/* 1231 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     try {
/* 1233 */       EntityItem entityItem1 = new EntityItem(null, paramSPSTABRSTATUS.getProfile(), "WG", paramSPSTABRSTATUS.getProfile().getWGID());
/* 1234 */       entityItem = ABRUtil.createEntity(paramSPSTABRSTATUS.getDatabase(), paramSPSTABRSTATUS.getProfile(), "CRLSEOBUNDLE", entityItem1, "LSEOBUNDLE", vector1, hashtable, stringBuffer);
/*      */       
/* 1236 */       stringBuffer.append("lseobundle was created. LSEOBUNDLE.getkey() = " + entityItem.getKey());
/*      */     }
/* 1238 */     catch (Exception exception) {
/* 1239 */       stringBuffer.append(exception.getMessage());
/*      */     } finally {
/* 1241 */       if (stringBuffer.length() > 0) {
/* 1242 */         paramSPSTABRSTATUS.addDebug(stringBuffer.toString());
/*      */       }
/* 1244 */       if (entityItem == null) {
/* 1245 */         paramSPSTABRSTATUS.addError("ERROR: Can not create LSEOBUNDLE entity for seoid: " + str);
/*      */       }
/*      */ 
/*      */       
/* 1249 */       vector1.clear();
/* 1250 */       hashtable.clear();
/*      */     } 
/*      */     
/* 1253 */     return entityItem;
/*      */   }
/*      */ 
/*      */   
/*      */   protected EntityItem createOrRefAvail(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString, Vector paramVector, Hashtable paramHashtable, LinkActionItem paramLinkActionItem) {
/* 1258 */     EntityItem entityItem = paramEntityItem2;
/* 1259 */     if (entityItem == null) {
/* 1260 */       StringBuffer stringBuffer = new StringBuffer();
/*      */       try {
/* 1262 */         EntityGroup entityGroup = new EntityGroup(null, this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "AVAIL", "Edit");
/* 1263 */         EntityItem entityItem1 = new EntityItem(entityGroup, this.spstAbr.getProfile(), this.spstAbr.getDatabase(), "AVAIL", this.spstAbr.getDatabase().getNextEntityID(this.spstAbr.getProfile(), "AVAIL"));
/*      */         
/* 1265 */         ABRUtil.setText(entityItem1, "COMNAME", (String)paramHashtable.get("COMNAME"), stringBuffer);
/* 1266 */         ABRUtil.setUniqueFlag(entityItem1, "AVAILTYPE", (String)paramHashtable.get("AVAILTYPE"), stringBuffer);
/* 1267 */         ABRUtil.setText(entityItem1, "EFFECTIVEDATE", (String)paramHashtable.get("EFFECTIVEDATE"), stringBuffer);
/* 1268 */         ABRUtil.setUniqueFlag(entityItem1, "AVAILANNTYPE", "NORFA", stringBuffer);
/*      */         
/* 1270 */         ABRUtil.setUniqueFlag(entityItem1, "ORDERSYSNAME", "4142", stringBuffer);
/* 1271 */         ABRUtil.setMultiFlag(entityItem1, "COUNTRYLIST", (Vector)paramHashtable.get("COUNTRYLIST"), stringBuffer);
/* 1272 */         Vector<String> vector = new Vector();
/* 1273 */         vector.add("0050");
/* 1274 */         ABRUtil.setMultiFlag(entityItem1, "PDHDOMAIN", vector, stringBuffer);
/* 1275 */         Vector vector1 = generateSalesOrg((Vector)paramHashtable.get("COUNTRYLIST"));
/* 1276 */         ABRUtil.setMultiFlag(entityItem1, "SLEORGGRP", vector1, stringBuffer);
/*      */         
/* 1278 */         entityItem1.commit(this.spstAbr.getDatabase(), null);
/* 1279 */         entityItem = entityItem1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1288 */         stringBuffer.append("Avail was created. AVAIL.getkey() = " + entityItem.getKey());
/* 1289 */       } catch (Exception exception) {
/* 1290 */         stringBuffer.append(exception.getMessage());
/*      */       } finally {
/* 1292 */         if (stringBuffer.length() > 0) {
/* 1293 */           this.spstAbr.addDebug(stringBuffer.toString());
/*      */         }
/* 1295 */         if (entityItem == null) {
/* 1296 */           this.spstAbr.addError("ERROR: Can not create AVAIL entity for COMNAME: " + paramHashtable.get("COMNAME"));
/*      */         }
/*      */       } 
/*      */     } 
/* 1300 */     if (entityItem != null)
/* 1301 */       doReference(paramEntityItem1, paramLinkActionItem, entityItem); 
/* 1302 */     return entityItem;
/*      */   }
/*      */ 
/*      */   
/*      */   protected EntityItem doReference(EntityItem paramEntityItem1, LinkActionItem paramLinkActionItem, EntityItem paramEntityItem2) {
/* 1307 */     if (paramEntityItem1 == null || paramEntityItem2 == null) {
/* 1308 */       this.spstAbr.addDebug("doReference failed, parent or child is null");
/* 1309 */       return null;
/*      */     } 
/* 1311 */     this.spstAbr.addDebug("doReference: referencing " + paramEntityItem1.getKey() + " and " + paramEntityItem2.getKey());
/*      */ 
/*      */     
/* 1314 */     EntityItem entityItem = link(paramEntityItem1, paramEntityItem2, paramLinkActionItem.getMetaLink().getEntityType());
/*      */     
/* 1316 */     this.spstAbr.addDebug("doReference: referenced " + paramEntityItem2.getKey() + " for " + paramEntityItem1.getKey());
/* 1317 */     return entityItem;
/*      */   }
/*      */ 
/*      */   
/*      */   protected EntityItem link(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString) {
/* 1322 */     String str1 = paramString;
/* 1323 */     String str2 = paramEntityItem1.getEntityType();
/* 1324 */     int i = paramEntityItem1.getEntityID();
/* 1325 */     String str3 = paramEntityItem2.getEntityType();
/* 1326 */     int j = paramEntityItem2.getEntityID();
/*      */     
/* 1328 */     return link(str1, str2, i, str3, j);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityItem link(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2) {
/* 1334 */     boolean bool = false;
/* 1335 */     EntityItem entityItem = null;
/*      */     try {
/* 1337 */       if (!checkExist(paramString2, paramInt1)) {
/* 1338 */         this.spstAbr.addError("Can not Link:Entity1 is not existed - " + paramString2 + paramInt1);
/* 1339 */         return null;
/*      */       } 
/* 1341 */       if (!checkExist(paramString3, paramInt2)) {
/* 1342 */         this.spstAbr.addError("Can not Link:Entity2 is not existed - " + paramString3 + paramInt2);
/* 1343 */         return null;
/*      */       } 
/* 1345 */       ReturnStatus returnStatus = new ReturnStatus(-1);
/* 1346 */       Database database = this.spstAbr.getDatabase();
/* 1347 */       DatePackage datePackage = database.getDates();
/* 1348 */       Profile profile = this.spstAbr.getProfile();
/* 1349 */       int i = profile.getOPWGID();
/* 1350 */       int j = profile.getTranID();
/* 1351 */       String str = profile.getEnterprise();
/* 1352 */       int k = profile.getSessionID();
/* 1353 */       int m = profile.getReadLanguage().getNLSID();
/*      */       
/* 1355 */       ReturnID returnID = database.callGBL2098(returnStatus, i, k, str, paramString1, new ReturnID(bool), paramString2, paramInt1, paramString3, paramInt2, j, datePackage.getNow(), datePackage.getForever(), m);
/* 1356 */       this.spstAbr.addDebug("new link was done: returnid is " + paramString1 + returnID.intValue());
/* 1357 */       database.commit();
/* 1358 */       database.freeStatement();
/* 1359 */       database.isPending();
/*      */       
/* 1361 */       int n = returnID.intValue();
/*      */       
/* 1363 */       database.callGBL3001(returnStatus, str, i, paramString1, n, j);
/* 1364 */       database.commit();
/* 1365 */       database.freeStatement();
/* 1366 */       database.isPending();
/*      */       
/* 1368 */       EANUtility.populateDefaultValues(database, profile, null, paramString1, n);
/* 1369 */       EntityGroup entityGroup = new EntityGroup(null, database, profile, paramString1, "Edit");
/* 1370 */       entityItem = new EntityItem(entityGroup, profile, database, paramString1, n);
/*      */     }
/* 1372 */     catch (Exception exception) {
/* 1373 */       exception.printStackTrace();
/* 1374 */       this.spstAbr.addError("Error: when do a link(referenced " + paramString3 + paramInt2 + " for " + paramString2 + paramInt1 + "), some thing wrong - " + exception.getMessage());
/*      */     } 
/* 1376 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkExist(String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 1382 */     Database database = this.spstAbr.getDatabase();
/* 1383 */     int i = 0;
/* 1384 */     PreparedStatement preparedStatement = null;
/* 1385 */     ResultSet resultSet = null;
/* 1386 */     String str1 = database.getNow(0);
/* 1387 */     String str2 = "select count(*) from opicm.entity where entitytype='" + paramString + "' and entityid = " + paramInt;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1392 */     String str3 = "  and valto > '" + str1 + "' and effto > '" + str1 + "' with ur";
/* 1393 */     this.spstAbr.addDebug("get the flag code using sql: " + str2 + str3);
/*      */     try {
/* 1395 */       preparedStatement = database.getPDHConnection().prepareStatement(str2 + str3);
/* 1396 */       resultSet = preparedStatement.executeQuery();
/* 1397 */       if (resultSet.next()) {
/* 1398 */         i = resultSet.getInt(1);
/*      */       }
/*      */     } finally {
/*      */       
/* 1402 */       if (resultSet != null) {
/*      */         try {
/* 1404 */           resultSet.close();
/* 1405 */         } catch (Exception exception) {
/* 1406 */           System.err.println("checkExist(), unable to close result. " + exception);
/*      */         } 
/* 1408 */         resultSet = null;
/*      */       } 
/* 1410 */       if (preparedStatement != null) {
/*      */         try {
/* 1412 */           preparedStatement.close();
/* 1413 */         } catch (Exception exception) {
/* 1414 */           System.err.println("checkExist(), unable to close ps. " + exception);
/*      */         } 
/* 1416 */         preparedStatement = null;
/*      */       } 
/* 1418 */       database.commit();
/* 1419 */       database.freeStatement();
/* 1420 */       database.isPending();
/*      */     } 
/* 1422 */     this.spstAbr.addDebug("returned count is " + i);
/* 1423 */     return (i > 0);
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
/*      */   public void execute() throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/* 1444 */     NodeList nodeList = this.rootElem.getElementsByTagName("AVAILLIST");
/* 1445 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 1446 */       Node node = nodeList.item(b);
/* 1447 */       if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */         
/* 1451 */         NodeList nodeList1 = nodeList.item(b).getChildNodes();
/* 1452 */         for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 1453 */           Node node1 = nodeList1.item(b1);
/* 1454 */           if (node1.getNodeType() == 1) {
/*      */ 
/*      */             
/* 1457 */             Element element = (Element)node1;
/* 1458 */             Vector<String> vector = new Vector();
/* 1459 */             Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */ 
/*      */ 
/*      */             
/* 1463 */             String str1 = this.spstAbr.getNodeValue(element, "COMNAME", true);
/* 1464 */             String str2 = this.spstAbr.getNodeValue(element, "AVAILTYPE", true);
/* 1465 */             if ("New".equalsIgnoreCase(str2)) {
/* 1466 */               str2 = "146";
/* 1467 */             } else if ("Withdrawal".equalsIgnoreCase(str2)) {
/* 1468 */               str2 = "149";
/*      */             } 
/* 1470 */             vector.addElement("COMNAME");
/* 1471 */             hashtable.put("COMNAME", str1);
/* 1472 */             vector.addElement("AVAILTYPE");
/* 1473 */             hashtable.put("AVAILTYPE", str2);
/* 1474 */             String str3 = this.spstAbr.getNodeValue(element, "EFFECTIVEDATE", false);
/* 1475 */             vector.addElement("EFFECTIVEDATE");
/* 1476 */             hashtable.put("EFFECTIVEDATE", str3);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1481 */             StringBuffer stringBuffer = new StringBuffer();
/* 1482 */             Vector vector1 = generateMultiFlags(element, "COUNTRYLIST", "COUNTRY", stringBuffer);
/* 1483 */             vector.addElement("COUNTRYLIST");
/* 1484 */             hashtable.put("COUNTRYLIST", vector1);
/*      */             
/* 1486 */             generateEntities(vector, hashtable, element, stringBuffer);
/*      */             
/* 1488 */             vector.clear();
/* 1489 */             vector = null;
/* 1490 */             hashtable.clear();
/* 1491 */             hashtable = null;
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
/*      */   protected void checkDuplicatedProd(String paramString1, String paramString2, Vector<String> paramVector) {
/* 1509 */     String str = paramString1 + paramString2;
/* 1510 */     if (paramVector.contains(str)) {
/* 1511 */       this.spstAbr.addError("ERROR_DUPLICATE_PROD", (Object[])new String[] { paramString1, paramString2 });
/*      */     } else {
/* 1513 */       paramVector.add(str);
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
/*      */   public EntityItem searchForGENERALAREA(Database paramDatabase, Profile paramProfile, String paramString, StringBuffer paramStringBuffer) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 1535 */     EntityItem entityItem = null;
/*      */ 
/*      */     
/* 1538 */     String str = getGenAreaCodeForCtry(paramDatabase, paramProfile, paramString);
/* 1539 */     paramStringBuffer.append("SPSTABR.searchForGENERALAREA country: " + paramString + " genAreaCodeFlg: " + str + "\n");
/* 1540 */     paramDatabase.debug(4, "SPSTABR.searchForGENERALAREA country: " + paramString + " genAreaCodeFlg: " + str);
/* 1541 */     if (str != null) {
/* 1542 */       Vector<String> vector1 = new Vector(1);
/* 1543 */       vector1.addElement("GENAREACODE");
/* 1544 */       Vector<String> vector2 = new Vector(1);
/* 1545 */       vector2.addElement(str);
/* 1546 */       EntityItem[] arrayOfEntityItem = null;
/*      */       try {
/* 1548 */         arrayOfEntityItem = ABRUtil.doSearch(paramDatabase, paramProfile, "LDSRDGENAREA", "GENERALAREA", false, vector1, vector2, paramStringBuffer);
/*      */       }
/* 1550 */       catch (SBRException sBRException) {
/*      */         
/* 1552 */         StringWriter stringWriter = new StringWriter();
/* 1553 */         sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 1554 */         paramStringBuffer.append("searchForGENERALAREA SBRException: " + stringWriter.getBuffer().toString() + "\n");
/*      */       } 
/* 1556 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 1557 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1558 */           paramStringBuffer.append("SPSTABR.searchForGENERALAREA found " + arrayOfEntityItem[b].getKey() + "\n");
/*      */         }
/* 1560 */         entityItem = arrayOfEntityItem[0];
/*      */       } 
/* 1562 */       vector1.clear();
/* 1563 */       vector2.clear();
/*      */     } else {
/* 1565 */       paramStringBuffer.append("SPSTABR.searchForGENERALAREA GENAREACODE table: " + this.SG_GENAREACODE_TBL + "\n");
/*      */     } 
/*      */     
/* 1568 */     return entityItem;
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
/*      */   private String getGenAreaCodeForCtry(Database paramDatabase, Profile paramProfile, String paramString) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1583 */     if (this.SG_GENAREACODE_TBL == null) {
/* 1584 */       this.SG_GENAREACODE_TBL = new Hashtable<>();
/*      */       
/* 1586 */       EntityGroup entityGroup = new EntityGroup(null, paramDatabase, paramProfile, "GENERALAREA", "Edit", false);
/* 1587 */       EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)entityGroup.getMetaAttribute("GENAREACODE");
/* 1588 */       if (eANMetaFlagAttribute != null) {
/* 1589 */         for (byte b = 0; b < eANMetaFlagAttribute.getMetaFlagCount(); b++) {
/*      */           
/* 1591 */           MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(b);
/* 1592 */           if (metaFlag.isExpired()) {
/* 1593 */             paramDatabase.debug(4, "SPSTABR.getGenAreaCodeForCtry skipping expired flag: " + metaFlag + "[" + metaFlag
/* 1594 */                 .getFlagCode() + "] for GENERALAREA.GENAREACODE");
/*      */           } else {
/*      */             
/* 1597 */             this.SG_GENAREACODE_TBL.put(metaFlag.toString(), metaFlag.getFlagCode());
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1602 */     return (String)this.SG_GENAREACODE_TBL.get(paramString);
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
/*      */   protected Object deriveAudien(String paramString) {
/* 1621 */     Object object = AUDIEN_TBL.get(paramString);
/* 1622 */     if (object == null) {
/* 1623 */       this.spstAbr.addError(paramString + " is not a valid Audience");
/* 1624 */     } else if ("Non Sap".equals(paramString)) {
/* 1625 */       this.spstAbr.addOutput("Warning: " + paramString + " will not be set for Audience currently, please take action if need");
/*      */     } 
/* 1627 */     return object;
/*      */   }
/*      */   
/*      */   private Vector generateSalesOrg(Vector paramVector) {
/* 1631 */     Vector vector = new Vector();
/* 1632 */     if (paramVector == null) {
/* 1633 */       this.spstAbr.addDebug("there is no countrylist");
/*      */     }
/* 1635 */     for (String str : paramVector) {
/*      */       
/* 1637 */       Vector vector1 = (Vector)SALES_ORG_TBL.get(str);
/* 1638 */       if (vector1 != null) {
/* 1639 */         vector.addAll(vector1); continue;
/*      */       } 
/* 1641 */       this.spstAbr.addDebug("there is no sales org to match the country:" + str);
/*      */     } 
/*      */     
/* 1644 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doReferenceModelTaxCatg(EntityItem paramEntityItem, Vector paramVector) {
/* 1649 */     if (paramVector == null) {
/* 1650 */       this.spstAbr.addDebug("there is no countrylist");
/*      */     }
/* 1652 */     for (String str : paramVector) {
/*      */       
/* 1654 */       Vector vector = (Vector)TAXCATG_TBL.get(str);
/* 1655 */       if (vector != null) {
/* 1656 */         Iterator<Integer> iterator = vector.iterator();
/* 1657 */         while (iterator.hasNext()) {
/* 1658 */           Integer integer = iterator.next();
/* 1659 */           link("MODTAXRELEVANCE", paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), "TAXCATG", integer.intValue());
/*      */         }  continue;
/*      */       } 
/* 1662 */       this.spstAbr.addDebug("there is no necessary to match TAXCATG for the country:" + str);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected abstract void checkRelatedEntities(Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException;
/*      */   
/*      */   abstract String getVersion();
/*      */   
/*      */   abstract String getTitle();
/*      */   
/*      */   abstract String getDescription();
/*      */   
/*      */   protected abstract void generateEntities(Vector paramVector, Hashtable paramHashtable, Element paramElement, StringBuffer paramStringBuffer) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException;
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SPSTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */