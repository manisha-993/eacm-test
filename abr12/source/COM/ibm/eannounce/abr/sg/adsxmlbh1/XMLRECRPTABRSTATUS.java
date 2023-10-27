/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.XMLElem;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MQUsage;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*      */ import com.ibm.mq.MQException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Collection;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLRECRPTABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*      */   private static final int XMLMSGLOG_ROW_LIMIT;
/*      */   private static final int MAXFILE_SIZE = 5000000;
/*      */   
/*      */   static {
/*  111 */     String str = ABRServerProperties.getValue("XMLRECRPTABRSTATUS", "_XMLGENCOUNT", "3000");
/*  112 */     XMLMSGLOG_ROW_LIMIT = Integer.parseInt(str);
/*      */   }
/*      */   
/*  115 */   private StringBuffer rptSb = new StringBuffer();
/*  116 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  117 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  118 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  120 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  121 */   private ResourceBundle rsBundle = null;
/*  122 */   private Hashtable metaTbl = new Hashtable<>();
/*  123 */   private String navName = "";
/*  124 */   private PrintWriter dbgPw = null;
/*  125 */   private String dbgfn = null;
/*  126 */   private int dbgLen = 0;
/*  127 */   private int abr_debuglvl = 0;
/*  128 */   private Vector vctReturnsEntityKeys = new Vector();
/*  129 */   private String actionTaken = "";
/*  130 */   private PrintWriter userxmlPw = null;
/*  131 */   private String userxmlfn = null;
/*  132 */   private int userxmlLen = 0;
/*  133 */   private StringBuffer userxmlSb = new StringBuffer();
/*  134 */   private String t2DTS = "&nbsp;";
/*  135 */   private String t1DTS = "&nbsp;";
/*      */   protected static final String STATUS_PROCESS = "0050";
/*  137 */   protected String attrXMLABRPROPFILE = "MQPROPFILE";
/*      */   
/*      */   protected static final String CHEAT = "@@";
/*      */   
/*  141 */   protected static final Hashtable SETUP_MSGTYPE_TBL = new Hashtable<>(); static {
/*  142 */     SETUP_MSGTYPE_TBL.put("ADSXMLSETUP", "GENERALAREA_UPDATE");
/*  143 */     SETUP_MSGTYPE_TBL.put("XMLPRODPRICESETUP", "PRODUCT_PRICE_UPDATE");
/*  144 */     SETUP_MSGTYPE_TBL.put("XMLCOMPATSETUP", "WWCOMPAT_UPDATE");
/*  145 */     SETUP_MSGTYPE_TBL.put("XMLXLATESETUP", "XLATE_UPDATE");
/*      */   }
/*      */ 
/*      */   
/*      */   private void setupPrintWriter() {
/*  150 */     String str = this.m_abri.getFileName();
/*  151 */     int i = str.lastIndexOf(".");
/*  152 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*  153 */     this.userxmlfn = str.substring(0, i + 1) + "userxml";
/*      */     try {
/*  155 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  156 */     } catch (Exception exception) {
/*  157 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */     try {
/*  160 */       this.userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userxmlfn, true), "UTF-8"));
/*  161 */     } catch (Exception exception) {
/*  162 */       D.ebug(0, "trouble creating xmlgen PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriter() {
/*  166 */     if (this.dbgPw != null) {
/*  167 */       this.dbgPw.flush();
/*  168 */       this.dbgPw.close();
/*  169 */       this.dbgPw = null;
/*      */     } 
/*  171 */     if (this.userxmlPw != null) {
/*  172 */       this.userxmlPw.flush();
/*  173 */       this.userxmlPw.close();
/*  174 */       this.userxmlPw = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void addUserXML(String paramString) {
/*  179 */     if (this.userxmlPw != null) {
/*  180 */       this.userxmlLen += paramString.length();
/*  181 */       this.userxmlPw.println(paramString);
/*  182 */       this.userxmlPw.flush();
/*      */     } else {
/*  184 */       this.userxmlSb.append(ADSABRSTATUS.convertToHTML(paramString) + NEWLINE);
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
/*      */   public void execute_run() {
/*  208 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  210 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  223 */     String str3 = "";
/*      */     
/*  225 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */     
/*      */     try {
/*  229 */       start_ABRBuild(false);
/*      */       
/*  231 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */       
/*  233 */       setupPrintWriter();
/*      */ 
/*      */       
/*  236 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  238 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  240 */               getEntityType(), getEntityID()) });
/*  241 */       long l = System.currentTimeMillis();
/*      */       
/*  243 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  245 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " extract: " + this.m_abri
/*  246 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  249 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  254 */       this.navName = getNavigationName(entityItem);
/*      */       
/*  256 */       addDebug("getT1 entered for Periodic XMLRECRPTABR " + entityItem.getKey());
/*      */       
/*  258 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("XMLRECRPTLRDTS");
/*  259 */       if (eANMetaAttribute == null) {
/*  260 */         throw new MiddlewareException("XMLRECRPTLRDTS not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/*      */       
/*  263 */       eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("MQPROPFILE");
/*  264 */       if (eANMetaAttribute == null) {
/*  265 */         throw new MiddlewareException("MQPROPFILE not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/*  267 */       String str = PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE);
/*  268 */       if (str == null) {
/*  269 */         addError("XMLRECRPTABR: No MQ properties files, nothing will be generated.");
/*      */ 
/*      */         
/*  272 */         addXMLGenMsg("NOT_REQUIRED", "XMLRECRPTABRSTATUS");
/*      */       } 
/*  274 */       this.t1DTS = PokUtils.getAttributeValue(entityItem, "XMLRECRPTLRDTS", ", ", this.m_strEpoch, false);
/*  275 */       boolean bool = isTimestamp(this.t1DTS);
/*  276 */       if (bool && getReturnCode() == 0) {
/*  277 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = getSTATUSHistory("XMLRECRPTABRSTATUS");
/*  278 */         setT2DTS(attributeChangeHistoryGroup);
/*  279 */         processThis(entityItem, str);
/*  280 */       } else if (!bool) {
/*  281 */         addError("Invalid DateTime Stamp for XMLRECRPTLRDTS, please put format: yyyy-MM-dd-HH.mm.ss.SSSSSS ");
/*      */       } 
/*  283 */       if (getReturnCode() == 0) {
/*  284 */         PDGUtility pDGUtility = new PDGUtility();
/*  285 */         OPICMList oPICMList = new OPICMList();
/*  286 */         oPICMList.put("XMLRECRPTLRDTS", "XMLRECRPTLRDTS=" + this.t2DTS);
/*  287 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */       } 
/*  289 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  291 */     catch (Throwable throwable) {
/*  292 */       StringWriter stringWriter = new StringWriter();
/*  293 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  294 */       String str7 = "<pre>{0}</pre>";
/*  295 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  296 */       setReturnCode(-3);
/*  297 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  299 */       this.args[0] = throwable.getMessage();
/*  300 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  301 */       messageFormat1 = new MessageFormat(str7);
/*  302 */       this.args[0] = stringWriter.getBuffer().toString();
/*  303 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  304 */       logError("Exception: " + throwable.getMessage());
/*  305 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*      */       
/*  308 */       setDGTitle(this.navName);
/*  309 */       setDGRptName(getShortClassName(getClass()));
/*  310 */       setDGRptClass(getABRCode());
/*      */       
/*  312 */       if (!isReadOnly()) {
/*  313 */         clearSoftLock();
/*      */       }
/*  315 */       closePrintWriter();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  320 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  321 */     this.args[0] = getDescription();
/*  322 */     this.args[1] = this.navName;
/*  323 */     String str4 = messageFormat.format(this.args);
/*  324 */     messageFormat = new MessageFormat(str2);
/*  325 */     this.args[0] = this.m_prof.getOPName();
/*  326 */     this.args[1] = this.m_prof.getRoleDescription();
/*  327 */     this.args[2] = this.m_prof.getWGName();
/*  328 */     this.args[3] = getNow();
/*  329 */     this.args[4] = this.t1DTS;
/*  330 */     this.args[5] = this.navName;
/*  331 */     this.args[6] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  332 */     this.args[7] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/*  333 */     this.args[8] = str3 + " " + getABRVersion();
/*      */     
/*  335 */     restoreXtraContent();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  340 */     String str5 = str4 + messageFormat.format(this.args) + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  341 */     this.rptSb.insert(0, str5 + NEWLINE);
/*      */     
/*  343 */     println(this.rptSb.toString());
/*  344 */     printDGSubmitString();
/*  345 */     println(EACustom.getTOUDiv());
/*  346 */     buildReportFooter();
/*      */     
/*  348 */     this.metaTbl.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processThis(EntityItem paramEntityItem, String paramString) throws Exception {
/*  358 */     addDebug("XMLRECRPTABR.processThis checking between " + this.t1DTS + " and " + this.t2DTS);
/*  359 */     StringBuffer stringBuffer = new StringBuffer();
/*  360 */     stringBuffer.append("select SETUPENTITYTYPE, SETUPENTITYID, SETUPDTS, MSGTYPE, ENTITYTYPE, ENTITYID, DTSOFMSG, MSGCOUNT from cache.xmlmsglog ");
/*  361 */     stringBuffer.append(" where sendmsgdts between '" + this.t1DTS + "' and '" + this.t2DTS + "'");
/*  362 */     if (paramString != null) {
/*  363 */       stringBuffer.append(" and locate('" + paramString + "', MQPROPFILE)>0");
/*      */     }
/*  365 */     stringBuffer.append(" and msgstatus = 'S' order by setupdts, msgtype, setupentityid");
/*  366 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  367 */     ResultSet resultSet = null;
/*  368 */     PreparedStatement preparedStatement = null;
/*      */     try {
/*  370 */       preparedStatement = this.m_db.getODSConnection().prepareStatement(new String(stringBuffer));
/*  371 */       resultSet = preparedStatement.executeQuery();
/*  372 */       byte b1 = 0;
/*  373 */       byte b2 = 0;
/*  374 */       while (resultSet.next()) {
/*  375 */         b2++;
/*  376 */         String str1 = resultSet.getString(1);
/*  377 */         int i = resultSet.getInt(2);
/*  378 */         String str2 = resultSet.getString(3);
/*  379 */         String str3 = resultSet.getString(4);
/*  380 */         String str4 = resultSet.getString(5);
/*  381 */         int j = resultSet.getInt(6);
/*  382 */         String str5 = resultSet.getString(7);
/*  383 */         int k = resultSet.getInt(8);
/*  384 */         if (str1 == null)
/*  385 */           str1 = "@@"; 
/*  386 */         if (str2 == null) {
/*  387 */           str2 = "@@";
/*      */         } else {
/*  389 */           str2 = str2.replace(' ', '-').replace(':', '.');
/*      */         } 
/*  391 */         if (str3 == null)
/*  392 */           str3 = "@@"; 
/*  393 */         if (str4 == null)
/*  394 */           str4 = "@@"; 
/*  395 */         if (str5 == null) {
/*  396 */           str5 = "@@";
/*      */         } else {
/*  398 */           str5 = str5.replace(' ', '-').replace(':', '.');
/*      */         } 
/*  400 */         String str6 = str1 + str2 + str3 + Integer.toString(i);
/*  401 */         if (!treeMap.containsKey(str6)) {
/*  402 */           XMLMSGInfo xMLMSGInfo = new XMLMSGInfo(str1, i, str2, str3, k);
/*  403 */           xMLMSGInfo.getEntitylist_xml().add(new String[] { str4, "0".equals(Integer.toString(j)) ? "@@" : Integer.toString(j), str5 });
/*  404 */           treeMap.put(str6, xMLMSGInfo);
/*      */         } else {
/*      */           
/*  407 */           XMLMSGInfo xMLMSGInfo = (XMLMSGInfo)treeMap.get(str6);
/*  408 */           xMLMSGInfo.setMsgcount_xml(xMLMSGInfo.getMsgcount_xml() + k);
/*  409 */           xMLMSGInfo.getEntitylist_xml().add(new String[] { str4, "0".equals(Integer.toString(j)) ? "@@" : Integer.toString(j), str5 });
/*      */         } 
/*  411 */         if (b2 >= XMLMSGLOG_ROW_LIMIT) {
/*  412 */           addDebug("Chunking size is " + XMLMSGLOG_ROW_LIMIT + ". Start to run chunking " + ++b1 + " times.");
/*  413 */           sentToMQ(treeMap, paramEntityItem);
/*  414 */           b2 = 0;
/*      */         } 
/*      */       } 
/*      */       
/*  418 */       if (b2 > 0) {
/*  419 */         sentToMQ(treeMap, paramEntityItem);
/*  420 */       } else if (XMLMSGLOG_ROW_LIMIT * b1 + b2 == 0) {
/*  421 */         sentToMQ(treeMap, paramEntityItem);
/*      */       } 
/*      */ 
/*      */       
/*  425 */       addOutput("The total number is " + (XMLMSGLOG_ROW_LIMIT * b1 + b2) + " entities");
/*      */     }
/*  427 */     catch (RuntimeException runtimeException) {
/*  428 */       addXMLGenMsg("FAILED", paramEntityItem.getKey());
/*  429 */       addDebug("RuntimeException on ? " + runtimeException);
/*  430 */       runtimeException.printStackTrace();
/*  431 */       throw runtimeException;
/*  432 */     } catch (Exception exception) {
/*  433 */       addXMLGenMsg("FAILED", paramEntityItem.getKey());
/*  434 */       addDebug("Exception on ? " + exception);
/*  435 */       exception.printStackTrace();
/*  436 */       throw exception;
/*      */     } finally {
/*  438 */       if (preparedStatement != null)
/*      */         try {
/*  440 */           preparedStatement.close();
/*  441 */         } catch (SQLException sQLException) {
/*  442 */           sQLException.printStackTrace();
/*      */         }  
/*      */     } 
/*      */   }
/*      */   private void sentToMQ(TreeMap paramTreeMap, EntityItem paramEntityItem) throws DOMException, MissingResourceException, ParserConfigurationException, TransformerException {
/*  447 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*  448 */     Vector<String> vector = new Vector();
/*  449 */     if (str1 != null) {
/*      */       
/*  451 */       StringTokenizer stringTokenizer = new StringTokenizer(str1, "|");
/*  452 */       while (stringTokenizer.hasMoreTokens())
/*      */       {
/*  454 */         vector.addElement(stringTokenizer.nextToken());
/*      */       }
/*      */     } 
/*      */     
/*  458 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "XMLRECRPTOPTION");
/*  459 */     addDebug("XMLRECRPTOPTION = " + str2);
/*  460 */     if (paramTreeMap.size() == 0 && "XRZERO".equals(str2)) {
/*      */       
/*  462 */       addDebug("don't send anything when xmlmsgMap.size is 0 and XMLRECRPTOPTION = " + str2);
/*      */     }
/*  464 */     else if (paramTreeMap.size() == 0 && !"XRZERO".equals(str2)) {
/*      */ 
/*      */       
/*  467 */       processMQZero(paramTreeMap, vector);
/*  468 */       addDebug("send zero report when xmlmsgMap.size is 0 and XMLRECRPTOPTION = " + str2);
/*      */     } else {
/*  470 */       addDebug("send normal report when xmlmsgMap.size > 0 and XMLRECRPTOPTION = " + str2);
/*  471 */       processMQ(paramTreeMap, vector);
/*      */     } 
/*      */ 
/*      */     
/*  475 */     paramTreeMap.clear();
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
/*      */   private void processMQZero(TreeMap paramTreeMap, Vector paramVector) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
/*  489 */     if (paramVector == null) {
/*  490 */       addDebug("XMLRECRPTABR: No MQ properties files, nothing will be generated.");
/*      */       
/*  492 */       addXMLGenMsg("NOT_REQUIRED", "XMLRECRPTABRSTATUS");
/*      */     } else {
/*  494 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  495 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  496 */       Document document = documentBuilder.newDocument();
/*  497 */       String str1 = "RECONCILE_MSGS";
/*  498 */       String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */       
/*  501 */       Element element1 = document.createElementNS(str2, str1);
/*  502 */       element1.appendChild(document.createComment("RECONCILE_MSGS Version 1 Mod 0"));
/*      */       
/*  504 */       document.appendChild(element1);
/*  505 */       element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*      */       
/*  507 */       Element element2 = document.createElement("DTSOFMSG");
/*  508 */       element2.appendChild(document.createTextNode(getNow()));
/*  509 */       element1.appendChild(element2);
/*  510 */       element2 = document.createElement("FROMMSGDTS");
/*  511 */       element2.appendChild(document.createTextNode(this.t1DTS));
/*  512 */       element1.appendChild(element2);
/*  513 */       element2 = document.createElement("TOMSGDTS");
/*  514 */       element2.appendChild(document.createTextNode(this.t2DTS));
/*  515 */       element1.appendChild(element2);
/*  516 */       Element element3 = document.createElement("MSGLIST");
/*  517 */       element1.appendChild(element3);
/*  518 */       Element element4 = document.createElement("MSGELEMENT");
/*  519 */       element3.appendChild(element4);
/*      */       
/*  521 */       element2 = document.createElement("SETUPENTITYTYPE");
/*  522 */       element2.appendChild(document.createTextNode("@@"));
/*  523 */       element4.appendChild(element2);
/*      */       
/*  525 */       element2 = document.createElement("SETUPENTITYID");
/*  526 */       element2.appendChild(document.createTextNode("@@"));
/*  527 */       element4.appendChild(element2);
/*      */       
/*  529 */       element2 = document.createElement("SETUPDTS");
/*  530 */       element2.appendChild(document.createTextNode("@@"));
/*  531 */       element4.appendChild(element2);
/*      */       
/*  533 */       element2 = document.createElement("MSGTYPE");
/*  534 */       element2.appendChild(document.createTextNode("@@"));
/*  535 */       element4.appendChild(element2);
/*      */       
/*  537 */       element2 = document.createElement("MSGCOUNT");
/*  538 */       element2.appendChild(document.createTextNode("0"));
/*  539 */       element4.appendChild(element2);
/*      */       
/*  541 */       Element element5 = document.createElement("ENTITYLIST");
/*  542 */       element4.appendChild(element5);
/*      */       
/*  544 */       String str3 = transformXML(document);
/*      */       
/*  546 */       boolean bool = false;
/*      */       
/*  548 */       String str4 = "XMLRECPT";
/*  549 */       String str5 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDNEEDED", "NO");
/*  550 */       if ("YES".equals(str5.toUpperCase())) {
/*  551 */         String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDFILE", "NONE");
/*  552 */         if ("NONE".equals(str)) {
/*  553 */           addError("there is no xsdfile for " + str4 + " defined in the propertyfile ");
/*      */         } else {
/*  555 */           long l1 = System.currentTimeMillis();
/*  556 */           Class<?> clazz = getClass();
/*  557 */           StringBuffer stringBuffer = new StringBuffer();
/*  558 */           bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/*  559 */           if (stringBuffer.length() > 0) {
/*  560 */             String str6 = stringBuffer.toString();
/*  561 */             if (str6.indexOf("fail") != -1)
/*  562 */               addError(str6); 
/*  563 */             addOutput(str6);
/*      */           } 
/*  565 */           long l2 = System.currentTimeMillis();
/*  566 */           addDebug(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/*  567 */           if (bool) {
/*  568 */             addDebug("the xml for " + str4 + " passed the validation");
/*      */           }
/*      */         } 
/*      */       } else {
/*  572 */         addOutput("the xml for " + str4 + " doesn't need to be validated");
/*  573 */         bool = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  578 */       if (str3 != null && bool)
/*      */       {
/*  580 */         notify("XMLRECPT", str3, paramVector);
/*      */       }
/*  582 */       document = null;
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
/*      */   private void processMQ(TreeMap paramTreeMap, Vector paramVector) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
/*  598 */     if (paramVector == null) {
/*  599 */       addDebug("XMLRECRPTABR: No MQ properties files, nothing will be generated.");
/*      */       
/*  601 */       addXMLGenMsg("NOT_REQUIRED", "XMLRECRPTABRSTATUS");
/*      */     } else {
/*  603 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  604 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  605 */       Document document = documentBuilder.newDocument();
/*  606 */       String str1 = "RECONCILE_MSGS";
/*  607 */       String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */       
/*  610 */       Element element1 = document.createElementNS(str2, str1);
/*  611 */       element1.appendChild(document.createComment("RECONCILE_MSGS Version 1 Mod 0"));
/*      */       
/*  613 */       document.appendChild(element1);
/*  614 */       element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*      */       
/*  616 */       Element element2 = document.createElement("DTSOFMSG");
/*  617 */       element2.appendChild(document.createTextNode(getNow()));
/*  618 */       element1.appendChild(element2);
/*  619 */       element2 = document.createElement("FROMMSGDTS");
/*  620 */       element2.appendChild(document.createTextNode(this.t1DTS));
/*  621 */       element1.appendChild(element2);
/*  622 */       element2 = document.createElement("TOMSGDTS");
/*  623 */       element2.appendChild(document.createTextNode(this.t2DTS));
/*  624 */       element1.appendChild(element2);
/*  625 */       Element element3 = document.createElement("MSGLIST");
/*  626 */       element1.appendChild(element3);
/*  627 */       Collection collection = paramTreeMap.values();
/*  628 */       Iterator<XMLMSGInfo> iterator = collection.iterator();
/*  629 */       while (iterator.hasNext()) {
/*  630 */         XMLMSGInfo xMLMSGInfo = iterator.next();
/*  631 */         Element element4 = document.createElement("MSGELEMENT");
/*  632 */         element3.appendChild(element4);
/*      */         
/*  634 */         element2 = document.createElement("SETUPENTITYTYPE");
/*  635 */         element2.appendChild(document.createTextNode(xMLMSGInfo.setupentitytype_xml));
/*  636 */         element4.appendChild(element2);
/*      */         
/*  638 */         element2 = document.createElement("SETUPENTITYID");
/*  639 */         element2.appendChild(document.createTextNode(xMLMSGInfo.setupentityid_xml));
/*  640 */         element4.appendChild(element2);
/*      */         
/*  642 */         element2 = document.createElement("SETUPDTS");
/*  643 */         element2.appendChild(document.createTextNode(xMLMSGInfo.setupdts_xml));
/*  644 */         element4.appendChild(element2);
/*      */         
/*  646 */         element2 = document.createElement("MSGTYPE");
/*  647 */         element2.appendChild(document.createTextNode(xMLMSGInfo.msgtype_xml));
/*  648 */         element4.appendChild(element2);
/*      */         
/*  650 */         element2 = document.createElement("MSGCOUNT");
/*  651 */         element2.appendChild(document.createTextNode(Integer.toString(xMLMSGInfo.getMsgcount_xml())));
/*  652 */         element4.appendChild(element2);
/*      */         
/*  654 */         Element element5 = document.createElement("ENTITYLIST");
/*  655 */         element4.appendChild(element5);
/*  656 */         if (!SETUP_MSGTYPE_TBL.containsKey(xMLMSGInfo.setupentitytype_xml)) {
/*  657 */           for (byte b = 0; b < xMLMSGInfo.entitylist_xml.size(); b++) {
/*  658 */             Element element = document.createElement("ENTITYELEMENT");
/*  659 */             element5.appendChild(element);
/*  660 */             String[] arrayOfString = xMLMSGInfo.entitylist_xml.elementAt(b);
/*  661 */             element2 = document.createElement("ENTITYTYPE");
/*  662 */             element2.appendChild(document.createTextNode(arrayOfString[0]));
/*  663 */             element.appendChild(element2);
/*  664 */             element2 = document.createElement("ENTITYID");
/*  665 */             element2.appendChild(document.createTextNode(arrayOfString[1]));
/*  666 */             element.appendChild(element2);
/*  667 */             element2 = document.createElement("DTSOFMSG");
/*  668 */             element2.appendChild(document.createTextNode(arrayOfString[2]));
/*  669 */             element.appendChild(element2);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*  674 */         xMLMSGInfo.dereference();
/*      */       } 
/*      */       
/*  677 */       String str3 = transformXML(document);
/*      */       
/*  679 */       boolean bool = false;
/*      */       
/*  681 */       String str4 = "XMLRECPT";
/*  682 */       String str5 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDNEEDED", "NO");
/*  683 */       if ("YES".equals(str5.toUpperCase())) {
/*  684 */         String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDFILE", "NONE"); if ("NONE".equals(str)) {
/*  685 */           addError("there is no xsdfile for " + str4 + " defined in the propertyfile ");
/*      */         } else {
/*  687 */           long l1 = System.currentTimeMillis();
/*  688 */           Class<?> clazz = getClass();
/*  689 */           StringBuffer stringBuffer = new StringBuffer();
/*  690 */           bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/*  691 */           if (stringBuffer.length() > 0) {
/*  692 */             String str6 = stringBuffer.toString();
/*  693 */             if (str6.indexOf("fail") != -1)
/*  694 */               addError(str6); 
/*  695 */             addOutput(str6);
/*      */           } 
/*  697 */           long l2 = System.currentTimeMillis();
/*  698 */           addDebug(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/*  699 */           if (bool) {
/*  700 */             addDebug("the xml for " + str4 + " passed the validation");
/*      */           }
/*      */         } 
/*      */       } else {
/*  704 */         addOutput("the xml for " + str4 + " doesn't need to be validated");
/*  705 */         bool = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  712 */       if (str3 != null && bool)
/*      */       {
/*  714 */         notify("XMLRECPT", str3, paramVector);
/*      */       }
/*  716 */       document = null;
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
/*      */   protected String transformXML(Document paramDocument) throws ParserConfigurationException, TransformerException {
/*  729 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*  730 */     Transformer transformer = transformerFactory.newTransformer();
/*  731 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/*  733 */     transformer.setOutputProperty("indent", "no");
/*  734 */     transformer.setOutputProperty("method", "xml");
/*  735 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/*  738 */     StringWriter stringWriter = new StringWriter();
/*  739 */     StreamResult streamResult = new StreamResult(stringWriter);
/*  740 */     DOMSource dOMSource = new DOMSource(paramDocument);
/*  741 */     transformer.transform(dOMSource, streamResult);
/*  742 */     String str = XMLElem.removeCheat(stringWriter.toString());
/*      */ 
/*      */ 
/*      */     
/*  746 */     transformer.setOutputProperty("indent", "yes");
/*  747 */     stringWriter = new StringWriter();
/*  748 */     streamResult = new StreamResult(stringWriter);
/*  749 */     transformer.transform(dOMSource, streamResult);
/*  750 */     addUserXML(XMLElem.removeCheat(stringWriter.toString()));
/*      */     
/*  752 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void notify(String paramString1, String paramString2, Vector<String> paramVector) throws MissingResourceException {
/*  763 */     MessageFormat messageFormat = null;
/*      */     
/*  765 */     byte b1 = 0;
/*  766 */     boolean bool = false;
/*      */ 
/*      */     
/*  769 */     for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/*      */       
/*  771 */       String str = paramVector.elementAt(b2);
/*  772 */       addDebug("in notify looking at prop file " + str);
/*      */       try {
/*  774 */         ResourceBundle resourceBundle = ResourceBundle.getBundle(str, 
/*  775 */             getLocale(getProfile().getReadLanguage().getNLSID()));
/*  776 */         Hashtable<String, String> hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/*  777 */         boolean bool1 = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/*  778 */         hashtable.put("MQCID", getMQCID());
/*  779 */         hashtable.put("XMLTYPE", "ADS");
/*  780 */         Hashtable hashtable1 = MQUsage.getUserProperties(resourceBundle, getMQCID());
/*  781 */         if (bool1) {
/*      */           try {
/*  783 */             addDebug("User infor " + hashtable1);
/*  784 */             MQUsage.putToMQQueueWithRFH2("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString2, hashtable, hashtable1);
/*      */             
/*  786 */             messageFormat = new MessageFormat(this.rsBundle.getString("SENT_SUCCESS"));
/*  787 */             this.args[0] = str;
/*  788 */             this.args[1] = paramString1;
/*  789 */             addOutput(messageFormat.format(this.args));
/*  790 */             b1++;
/*  791 */             if (!bool) {
/*      */               
/*  793 */               addXMLGenMsg("SUCCESS", paramString1);
/*  794 */               addDebug("sent successfully to prop file " + str);
/*      */             } 
/*  796 */           } catch (MQException mQException) {
/*      */ 
/*      */             
/*  799 */             addXMLGenMsg("FAILED", paramString1);
/*  800 */             bool = true;
/*  801 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQ_ERROR"));
/*  802 */             this.args[0] = str + " " + paramString1;
/*  803 */             this.args[1] = "" + mQException.completionCode;
/*  804 */             this.args[2] = "" + mQException.reasonCode;
/*  805 */             addError(messageFormat.format(this.args));
/*  806 */             mQException.printStackTrace(System.out);
/*  807 */             addDebug("failed sending to prop file " + str);
/*  808 */           } catch (IOException iOException) {
/*      */             
/*  810 */             addXMLGenMsg("FAILED", paramString1);
/*  811 */             bool = true;
/*  812 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQIO_ERROR"));
/*  813 */             this.args[0] = str + " " + paramString1;
/*  814 */             this.args[1] = iOException.toString();
/*  815 */             addError(messageFormat.format(this.args));
/*  816 */             iOException.printStackTrace(System.out);
/*  817 */             addDebug("failed sending to prop file " + str);
/*      */           } 
/*      */         } else {
/*      */           
/*  821 */           messageFormat = new MessageFormat(this.rsBundle.getString("NO_NOTIFY"));
/*  822 */           this.args[0] = str;
/*  823 */           addError(messageFormat.format(this.args));
/*      */           
/*  825 */           addXMLGenMsg("NOT_SENT", paramString1);
/*  826 */           addDebug("not sent to prop file " + str + " because Notify not true");
/*      */         }
/*      */       
/*  829 */       } catch (MissingResourceException missingResourceException) {
/*  830 */         addXMLGenMsg("FAILED", str + " " + paramString1);
/*  831 */         bool = true;
/*  832 */         addError("Prop file " + str + " " + paramString1 + " not found");
/*      */       } 
/*      */     } 
/*      */     
/*  836 */     if (b1 > 0 && b1 != paramVector.size()) {
/*  837 */       addXMLGenMsg("ALL_NOT_SENT", paramString1);
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
/*      */   private void setT2DTS(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) throws MiddlewareException {
/*  849 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 1) {
/*      */       
/*  851 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/*      */ 
/*      */       
/*  854 */       AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*  855 */       if (attributeChangeHistoryItem != null) {
/*  856 */         addDebug("getT2Time [" + (i - 1) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  857 */             .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*  858 */         if (attributeChangeHistoryItem.getFlagCode().equals("0050")) {
/*  859 */           this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */         } else {
/*  861 */           addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
/*      */           
/*  863 */           this.t2DTS = getNow();
/*      */         } 
/*      */       } 
/*      */     } else {
/*  867 */       this.t2DTS = getNow();
/*  868 */       addDebug("getT2Time for ADSABRSTATUS changedHistoryGroup has no history, set getNow to t2DTS");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getSTATUSHistory(String paramString) throws MiddlewareException {
/*  879 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  880 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/*  881 */     if (eANAttribute != null) {
/*  882 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/*  884 */     addDebug(paramString + " of " + entityItem.getKey() + "  was null");
/*  885 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTimestamp(String paramString) {
/*  894 */     boolean bool = false;
/*      */     
/*  896 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS", Locale.ENGLISH);
/*      */     try {
/*  898 */       simpleDateFormat.parse(paramString);
/*  899 */       bool = true;
/*  900 */     } catch (ParseException parseException) {
/*  901 */       bool = false;
/*      */     } 
/*  903 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  911 */     if (this.userxmlLen + this.rptSb.length() < 5000000) {
/*      */       
/*  913 */       BufferedInputStream bufferedInputStream = null;
/*  914 */       FileInputStream fileInputStream = null;
/*  915 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  917 */         fileInputStream = new FileInputStream(this.userxmlfn);
/*  918 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  920 */         String str = null;
/*  921 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  923 */         while ((str = bufferedReader.readLine()) != null) {
/*  924 */           this.userxmlSb.append(ADSABRSTATUS.convertToHTML(str) + NEWLINE);
/*      */         }
/*      */         
/*  927 */         File file = new File(this.userxmlfn);
/*  928 */         if (file.exists()) {
/*  929 */           file.delete();
/*      */         }
/*  931 */       } catch (Exception exception) {
/*  932 */         exception.printStackTrace();
/*      */       } finally {
/*  934 */         if (bufferedInputStream != null) {
/*      */           try {
/*  936 */             bufferedInputStream.close();
/*  937 */           } catch (Exception exception) {
/*  938 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  941 */         if (fileInputStream != null) {
/*      */           try {
/*  943 */             fileInputStream.close();
/*  944 */           } catch (Exception exception) {
/*  945 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/*  950 */       this.userxmlSb.append("XML generated was too large for this file");
/*      */     } 
/*      */     
/*  953 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/*  955 */       BufferedInputStream bufferedInputStream = null;
/*  956 */       FileInputStream fileInputStream = null;
/*  957 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  959 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  960 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  962 */         String str = null;
/*  963 */         StringBuffer stringBuffer = new StringBuffer();
/*  964 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  966 */         while ((str = bufferedReader.readLine()) != null) {
/*  967 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  969 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  972 */         File file = new File(this.dbgfn);
/*  973 */         if (file.exists()) {
/*  974 */           file.delete();
/*      */         }
/*  976 */       } catch (Exception exception) {
/*  977 */         exception.printStackTrace();
/*      */       } finally {
/*  979 */         if (bufferedInputStream != null) {
/*      */           try {
/*  981 */             bufferedInputStream.close();
/*  982 */           } catch (Exception exception) {
/*  983 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  986 */         if (fileInputStream != null) {
/*      */           try {
/*  988 */             fileInputStream.close();
/*  989 */           } catch (Exception exception) {
/*  990 */             exception.printStackTrace();
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
/*      */   public static Locale getLocale(int paramInt) {
/* 1004 */     Locale locale = null;
/* 1005 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 1008 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1032 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addXMLGenMsg(String paramString1, String paramString2) {
/* 1039 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/* 1040 */     Object[] arrayOfObject = { paramString2 };
/* 1041 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 1048 */     super.dereference();
/*      */     
/* 1050 */     this.rsBundle = null;
/* 1051 */     this.rptSb = null;
/* 1052 */     this.args = null;
/*      */     
/* 1054 */     this.metaTbl = null;
/* 1055 */     this.navName = null;
/* 1056 */     this.vctReturnsEntityKeys.clear();
/* 1057 */     this.vctReturnsEntityKeys = null;
/*      */     
/* 1059 */     this.dbgPw = null;
/* 1060 */     this.dbgfn = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1066 */     return "$Revision: 1.12 $";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMQCID() {
/* 1072 */     return "RECONCILE_MSGS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1079 */     return "ADSIDLSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1085 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1092 */     this.dbgLen += paramString.length();
/* 1093 */     this.dbgPw.println(paramString);
/* 1094 */     this.dbgPw.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(int paramInt, String paramString) {
/* 1103 */     if (paramInt <= this.abr_debuglvl) {
/* 1104 */       addDebug(paramString);
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
/*      */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 1116 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1119 */     addMessage(this.rsBundle.getString("ERROR_PREFIX"), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1125 */     addOutput(paramString);
/* 1126 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1136 */     String str = this.rsBundle.getString(paramString2);
/*      */     
/* 1138 */     if (paramArrayOfObject != null) {
/* 1139 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1140 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1143 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1153 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 1157 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1158 */     if (eANList == null) {
/* 1159 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1160 */       eANList = entityGroup.getMetaAttribute();
/* 1161 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1163 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1164 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1165 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1166 */       if (b + 1 < eANList.size()) {
/* 1167 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1171 */     return stringBuffer.toString().trim();
/*      */   }
/*      */   
/*      */   private static class XMLMSGInfo
/*      */   {
/* 1176 */     String setupentitytype_xml = "@@";
/* 1177 */     String setupentityid_xml = "@@";
/* 1178 */     String setupdts_xml = "@@";
/* 1179 */     String msgtype_xml = "@@";
/*      */     int msgcount_xml;
/* 1181 */     Vector entitylist_xml = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     XMLMSGInfo(String param1String1, int param1Int1, String param1String2, String param1String3, int param1Int2) {
/* 1193 */       if (param1String1 != null) {
/* 1194 */         this.setupentitytype_xml = param1String1.trim();
/*      */       }
/*      */       
/* 1197 */       if (param1Int1 != 0) {
/* 1198 */         this.setupentityid_xml = Integer.toString(param1Int1);
/*      */       }
/*      */       
/* 1201 */       if (param1String2 != null) {
/* 1202 */         this.setupdts_xml = param1String2.trim();
/*      */       }
/*      */       
/* 1205 */       if (param1String3 != null) {
/* 1206 */         this.msgtype_xml = param1String3.trim();
/*      */       }
/* 1208 */       this.msgcount_xml = param1Int2;
/*      */     }
/*      */ 
/*      */     
/*      */     void dereference() {
/* 1213 */       this.setupentitytype_xml = null;
/* 1214 */       this.setupentityid_xml = null;
/* 1215 */       this.setupdts_xml = null;
/* 1216 */       this.msgtype_xml = null;
/* 1217 */       if (this.entitylist_xml != null)
/* 1218 */         this.entitylist_xml.clear(); 
/* 1219 */       this.entitylist_xml = null;
/*      */     }
/*      */     
/*      */     public Vector getEntitylist_xml() {
/* 1223 */       return this.entitylist_xml;
/*      */     }
/*      */     
/*      */     public void setEntitylist_xml(Vector param1Vector) {
/* 1227 */       this.entitylist_xml = param1Vector;
/*      */     }
/*      */     
/*      */     public int getMsgcount_xml() {
/* 1231 */       return this.msgcount_xml;
/*      */     }
/*      */     
/*      */     public void setMsgcount_xml(int param1Int) {
/* 1235 */       this.msgcount_xml = param1Int;
/*      */     }
/*      */     
/*      */     String getKey() {
/* 1239 */       return this.setupentitytype_xml + this.setupentityid_xml + this.setupdts_xml + this.msgtype_xml;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\XMLRECRPTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */