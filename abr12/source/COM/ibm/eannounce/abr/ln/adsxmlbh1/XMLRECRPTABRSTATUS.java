/*      */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
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
/*  114 */     String str = ABRServerProperties.getValue("XMLRECRPTABRSTATUS", "_XMLGENCOUNT", "3000");
/*  115 */     XMLMSGLOG_ROW_LIMIT = Integer.parseInt(str);
/*      */   }
/*      */   
/*  118 */   private StringBuffer rptSb = new StringBuffer();
/*  119 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  120 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  121 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  123 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  124 */   private ResourceBundle rsBundle = null;
/*  125 */   private Hashtable metaTbl = new Hashtable<>();
/*  126 */   private String navName = "";
/*  127 */   private PrintWriter dbgPw = null;
/*  128 */   private String dbgfn = null;
/*  129 */   private int dbgLen = 0;
/*  130 */   private int abr_debuglvl = 0;
/*  131 */   private Vector vctReturnsEntityKeys = new Vector();
/*  132 */   private String actionTaken = "";
/*  133 */   private PrintWriter userxmlPw = null;
/*  134 */   private String userxmlfn = null;
/*  135 */   private int userxmlLen = 0;
/*  136 */   private StringBuffer userxmlSb = new StringBuffer();
/*  137 */   private String t2DTS = "&nbsp;";
/*  138 */   private String t1DTS = "&nbsp;";
/*      */   protected static final String STATUS_PROCESS = "0050";
/*  140 */   protected String attrXMLABRPROPFILE = "MQPROPFILE";
/*      */   
/*      */   protected static final String CHEAT = "@@";
/*      */   
/*  144 */   protected static final Hashtable SETUP_MSGTYPE_TBL = new Hashtable<>(); static {
/*  145 */     SETUP_MSGTYPE_TBL.put("ADSXMLSETUP", "GENERALAREA_UPDATE");
/*  146 */     SETUP_MSGTYPE_TBL.put("XMLPRODPRICESETUP", "PRODUCT_PRICE_UPDATE");
/*  147 */     SETUP_MSGTYPE_TBL.put("XMLCOMPATSETUP", "WWCOMPAT_UPDATE");
/*  148 */     SETUP_MSGTYPE_TBL.put("XMLXLATESETUP", "XLATE_UPDATE");
/*      */   }
/*      */ 
/*      */   
/*      */   private void setupPrintWriter() {
/*  153 */     String str = this.m_abri.getFileName();
/*  154 */     int i = str.lastIndexOf(".");
/*  155 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*  156 */     this.userxmlfn = str.substring(0, i + 1) + "userxml";
/*      */     try {
/*  158 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  159 */     } catch (Exception exception) {
/*  160 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */     try {
/*  163 */       this.userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userxmlfn, true), "UTF-8"));
/*  164 */     } catch (Exception exception) {
/*  165 */       D.ebug(0, "trouble creating xmlgen PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriter() {
/*  169 */     if (this.dbgPw != null) {
/*  170 */       this.dbgPw.flush();
/*  171 */       this.dbgPw.close();
/*  172 */       this.dbgPw = null;
/*      */     } 
/*  174 */     if (this.userxmlPw != null) {
/*  175 */       this.userxmlPw.flush();
/*  176 */       this.userxmlPw.close();
/*  177 */       this.userxmlPw = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void addUserXML(String paramString) {
/*  182 */     if (this.userxmlPw != null) {
/*  183 */       this.userxmlLen += paramString.length();
/*  184 */       this.userxmlPw.println(paramString);
/*  185 */       this.userxmlPw.flush();
/*      */     } else {
/*  187 */       this.userxmlSb.append(ADSABRSTATUS.convertToHTML(paramString) + NEWLINE);
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
/*  211 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  213 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  226 */     String str3 = "";
/*      */     
/*  228 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */     
/*      */     try {
/*  232 */       start_ABRBuild(false);
/*      */       
/*  234 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */       
/*  236 */       setupPrintWriter();
/*      */ 
/*      */       
/*  239 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  241 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  243 */               getEntityType(), getEntityID()) });
/*  244 */       long l = System.currentTimeMillis();
/*      */       
/*  246 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  248 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " extract: " + this.m_abri
/*  249 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  252 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  257 */       this.navName = getNavigationName(entityItem);
/*      */       
/*  259 */       addDebug("getT1 entered for Periodic XMLRECRPTABR " + entityItem.getKey());
/*      */       
/*  261 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("XMLRECRPTLRDTS");
/*  262 */       if (eANMetaAttribute == null) {
/*  263 */         throw new MiddlewareException("XMLRECRPTLRDTS not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/*      */       
/*  266 */       eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("MQPROPFILE");
/*  267 */       if (eANMetaAttribute == null) {
/*  268 */         throw new MiddlewareException("MQPROPFILE not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/*  270 */       String str = PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE);
/*  271 */       if (str == null) {
/*  272 */         addError("XMLRECRPTABR: No MQ properties files, nothing will be generated.");
/*      */ 
/*      */         
/*  275 */         addXMLGenMsg("NOT_REQUIRED", "XMLRECRPTABRSTATUS");
/*      */       } 
/*  277 */       this.t1DTS = PokUtils.getAttributeValue(entityItem, "XMLRECRPTLRDTS", ", ", this.m_strEpoch, false);
/*  278 */       boolean bool = isTimestamp(this.t1DTS);
/*  279 */       if (bool && getReturnCode() == 0) {
/*  280 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = getSTATUSHistory("XMLRECRPTABRSTATUS");
/*  281 */         setT2DTS(attributeChangeHistoryGroup);
/*  282 */         processThis(entityItem, str);
/*  283 */       } else if (!bool) {
/*  284 */         addError("Invalid DateTime Stamp for XMLRECRPTLRDTS, please put format: yyyy-MM-dd-HH.mm.ss.SSSSSS ");
/*      */       } 
/*  286 */       if (getReturnCode() == 0) {
/*  287 */         PDGUtility pDGUtility = new PDGUtility();
/*  288 */         OPICMList oPICMList = new OPICMList();
/*  289 */         oPICMList.put("XMLRECRPTLRDTS", "XMLRECRPTLRDTS=" + this.t2DTS);
/*  290 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */       } 
/*  292 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  294 */     catch (Throwable throwable) {
/*  295 */       StringWriter stringWriter = new StringWriter();
/*  296 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  297 */       String str7 = "<pre>{0}</pre>";
/*  298 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  299 */       setReturnCode(-3);
/*  300 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  302 */       this.args[0] = throwable.getMessage();
/*  303 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  304 */       messageFormat1 = new MessageFormat(str7);
/*  305 */       this.args[0] = stringWriter.getBuffer().toString();
/*  306 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  307 */       logError("Exception: " + throwable.getMessage());
/*  308 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*      */       
/*  311 */       setDGTitle(this.navName);
/*  312 */       setDGRptName(getShortClassName(getClass()));
/*  313 */       setDGRptClass(getABRCode());
/*      */       
/*  315 */       if (!isReadOnly()) {
/*  316 */         clearSoftLock();
/*      */       }
/*  318 */       closePrintWriter();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  323 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  324 */     this.args[0] = getDescription();
/*  325 */     this.args[1] = this.navName;
/*  326 */     String str4 = messageFormat.format(this.args);
/*  327 */     messageFormat = new MessageFormat(str2);
/*  328 */     this.args[0] = this.m_prof.getOPName();
/*  329 */     this.args[1] = this.m_prof.getRoleDescription();
/*  330 */     this.args[2] = this.m_prof.getWGName();
/*  331 */     this.args[3] = getNow();
/*  332 */     this.args[4] = this.t1DTS;
/*  333 */     this.args[5] = this.navName;
/*  334 */     this.args[6] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  335 */     this.args[7] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/*  336 */     this.args[8] = str3 + " " + getABRVersion();
/*      */     
/*  338 */     restoreXtraContent();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  343 */     String str5 = str4 + messageFormat.format(this.args) + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  344 */     this.rptSb.insert(0, str5 + NEWLINE);
/*      */     
/*  346 */     println(this.rptSb.toString());
/*  347 */     printDGSubmitString();
/*  348 */     println(EACustom.getTOUDiv());
/*  349 */     buildReportFooter();
/*      */     
/*  351 */     this.metaTbl.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processThis(EntityItem paramEntityItem, String paramString) throws Exception {
/*  361 */     addDebug("XMLRECRPTABR.processThis checking between " + this.t1DTS + " and " + this.t2DTS);
/*  362 */     StringBuffer stringBuffer = new StringBuffer();
/*  363 */     stringBuffer.append("select SETUPENTITYTYPE, SETUPENTITYID, SETUPDTS, MSGTYPE, ENTITYTYPE, ENTITYID, DTSOFMSG, MSGCOUNT from cache.xmlmsglog ");
/*  364 */     stringBuffer.append(" where sendmsgdts between '" + this.t1DTS + "' and '" + this.t2DTS + "'");
/*  365 */     if (paramString != null) {
/*  366 */       stringBuffer.append(" and locate('" + paramString + "', MQPROPFILE)>0");
/*      */     }
/*  368 */     stringBuffer.append(" and msgstatus = 'S' order by setupdts, msgtype, setupentityid");
/*  369 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  370 */     ResultSet resultSet = null;
/*  371 */     PreparedStatement preparedStatement = null;
/*      */     try {
/*  373 */       preparedStatement = this.m_db.getODSConnection().prepareStatement(new String(stringBuffer));
/*  374 */       resultSet = preparedStatement.executeQuery();
/*  375 */       byte b1 = 0;
/*  376 */       byte b2 = 0;
/*  377 */       while (resultSet.next()) {
/*  378 */         b2++;
/*  379 */         String str1 = resultSet.getString(1);
/*  380 */         int i = resultSet.getInt(2);
/*  381 */         String str2 = resultSet.getString(3);
/*  382 */         String str3 = resultSet.getString(4);
/*  383 */         String str4 = resultSet.getString(5);
/*  384 */         int j = resultSet.getInt(6);
/*  385 */         String str5 = resultSet.getString(7);
/*  386 */         int k = resultSet.getInt(8);
/*  387 */         if (str1 == null)
/*  388 */           str1 = "@@"; 
/*  389 */         if (str2 == null) {
/*  390 */           str2 = "@@";
/*      */         } else {
/*  392 */           str2 = str2.replace(' ', '-').replace(':', '.');
/*      */         } 
/*  394 */         if (str3 == null)
/*  395 */           str3 = "@@"; 
/*  396 */         if (str4 == null)
/*  397 */           str4 = "@@"; 
/*  398 */         if (str5 == null) {
/*  399 */           str5 = "@@";
/*      */         } else {
/*  401 */           str5 = str5.replace(' ', '-').replace(':', '.');
/*      */         } 
/*  403 */         String str6 = str1 + str2 + str3 + Integer.toString(i);
/*  404 */         if (!treeMap.containsKey(str6)) {
/*  405 */           XMLMSGInfo xMLMSGInfo = new XMLMSGInfo(str1, i, str2, str3, k);
/*  406 */           xMLMSGInfo.getEntitylist_xml().add(new String[] { str4, "0".equals(Integer.toString(j)) ? "@@" : Integer.toString(j), str5 });
/*  407 */           treeMap.put(str6, xMLMSGInfo);
/*      */         } else {
/*      */           
/*  410 */           XMLMSGInfo xMLMSGInfo = (XMLMSGInfo)treeMap.get(str6);
/*  411 */           xMLMSGInfo.setMsgcount_xml(xMLMSGInfo.getMsgcount_xml() + k);
/*  412 */           xMLMSGInfo.getEntitylist_xml().add(new String[] { str4, "0".equals(Integer.toString(j)) ? "@@" : Integer.toString(j), str5 });
/*      */         } 
/*  414 */         if (b2 >= XMLMSGLOG_ROW_LIMIT) {
/*  415 */           addDebug("Chunking size is " + XMLMSGLOG_ROW_LIMIT + ". Start to run chunking " + ++b1 + " times.");
/*  416 */           sentToMQ(treeMap, paramEntityItem);
/*  417 */           b2 = 0;
/*      */         } 
/*      */       } 
/*      */       
/*  421 */       if (b2 > 0) {
/*  422 */         sentToMQ(treeMap, paramEntityItem);
/*  423 */       } else if (XMLMSGLOG_ROW_LIMIT * b1 + b2 == 0) {
/*  424 */         sentToMQ(treeMap, paramEntityItem);
/*      */       } 
/*      */ 
/*      */       
/*  428 */       addOutput("The total number is " + (XMLMSGLOG_ROW_LIMIT * b1 + b2) + " entities");
/*      */     }
/*  430 */     catch (RuntimeException runtimeException) {
/*  431 */       addXMLGenMsg("FAILED", paramEntityItem.getKey());
/*  432 */       addDebug("RuntimeException on ? " + runtimeException);
/*  433 */       runtimeException.printStackTrace();
/*  434 */       throw runtimeException;
/*  435 */     } catch (Exception exception) {
/*  436 */       addXMLGenMsg("FAILED", paramEntityItem.getKey());
/*  437 */       addDebug("Exception on ? " + exception);
/*  438 */       exception.printStackTrace();
/*  439 */       throw exception;
/*      */     } finally {
/*  441 */       if (preparedStatement != null)
/*      */         try {
/*  443 */           preparedStatement.close();
/*  444 */         } catch (SQLException sQLException) {
/*  445 */           sQLException.printStackTrace();
/*      */         }  
/*      */     } 
/*      */   }
/*      */   private void sentToMQ(TreeMap paramTreeMap, EntityItem paramEntityItem) throws DOMException, MissingResourceException, ParserConfigurationException, TransformerException {
/*  450 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*  451 */     Vector<String> vector = new Vector();
/*  452 */     if (str1 != null) {
/*      */       
/*  454 */       StringTokenizer stringTokenizer = new StringTokenizer(str1, "|");
/*  455 */       while (stringTokenizer.hasMoreTokens())
/*      */       {
/*  457 */         vector.addElement(stringTokenizer.nextToken());
/*      */       }
/*      */     } 
/*      */     
/*  461 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "XMLRECRPTOPTION");
/*  462 */     addDebug("XMLRECRPTOPTION = " + str2);
/*  463 */     if (paramTreeMap.size() == 0 && "XRZERO".equals(str2)) {
/*      */       
/*  465 */       addDebug("don't send anything when xmlmsgMap.size is 0 and XMLRECRPTOPTION = " + str2);
/*      */     }
/*  467 */     else if (paramTreeMap.size() == 0 && !"XRZERO".equals(str2)) {
/*      */ 
/*      */       
/*  470 */       processMQZero(paramTreeMap, vector);
/*  471 */       addDebug("send zero report when xmlmsgMap.size is 0 and XMLRECRPTOPTION = " + str2);
/*      */     } else {
/*  473 */       addDebug("send normal report when xmlmsgMap.size > 0 and XMLRECRPTOPTION = " + str2);
/*  474 */       processMQ(paramTreeMap, vector);
/*      */     } 
/*      */ 
/*      */     
/*  478 */     paramTreeMap.clear();
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
/*  492 */     if (paramVector == null) {
/*  493 */       addDebug("XMLRECRPTABR: No MQ properties files, nothing will be generated.");
/*      */       
/*  495 */       addXMLGenMsg("NOT_REQUIRED", "XMLRECRPTABRSTATUS");
/*      */     } else {
/*  497 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  498 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  499 */       Document document = documentBuilder.newDocument();
/*  500 */       String str1 = "RECONCILE_MSGS";
/*  501 */       String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */       
/*  504 */       Element element1 = document.createElementNS(str2, str1);
/*  505 */       element1.appendChild(document.createComment("RECONCILE_MSGS Version 1 Mod 0"));
/*      */       
/*  507 */       document.appendChild(element1);
/*  508 */       element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*      */       
/*  510 */       Element element2 = document.createElement("DTSOFMSG");
/*  511 */       element2.appendChild(document.createTextNode(getNow()));
/*  512 */       element1.appendChild(element2);
/*  513 */       element2 = document.createElement("FROMMSGDTS");
/*  514 */       element2.appendChild(document.createTextNode(this.t1DTS));
/*  515 */       element1.appendChild(element2);
/*  516 */       element2 = document.createElement("TOMSGDTS");
/*  517 */       element2.appendChild(document.createTextNode(this.t2DTS));
/*  518 */       element1.appendChild(element2);
/*  519 */       Element element3 = document.createElement("MSGLIST");
/*  520 */       element1.appendChild(element3);
/*  521 */       Element element4 = document.createElement("MSGELEMENT");
/*  522 */       element3.appendChild(element4);
/*      */       
/*  524 */       element2 = document.createElement("SETUPENTITYTYPE");
/*  525 */       element2.appendChild(document.createTextNode("@@"));
/*  526 */       element4.appendChild(element2);
/*      */       
/*  528 */       element2 = document.createElement("SETUPENTITYID");
/*  529 */       element2.appendChild(document.createTextNode("@@"));
/*  530 */       element4.appendChild(element2);
/*      */       
/*  532 */       element2 = document.createElement("SETUPDTS");
/*  533 */       element2.appendChild(document.createTextNode("@@"));
/*  534 */       element4.appendChild(element2);
/*      */       
/*  536 */       element2 = document.createElement("MSGTYPE");
/*  537 */       element2.appendChild(document.createTextNode("@@"));
/*  538 */       element4.appendChild(element2);
/*      */       
/*  540 */       element2 = document.createElement("MSGCOUNT");
/*  541 */       element2.appendChild(document.createTextNode("0"));
/*  542 */       element4.appendChild(element2);
/*      */       
/*  544 */       Element element5 = document.createElement("ENTITYLIST");
/*  545 */       element4.appendChild(element5);
/*      */       
/*  547 */       String str3 = transformXML(document);
/*      */       
/*  549 */       boolean bool = false;
/*      */       
/*  551 */       String str4 = "XMLRECPT";
/*  552 */       String str5 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDNEEDED", "NO");
/*  553 */       if ("YES".equals(str5.toUpperCase())) {
/*  554 */         String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDFILE", "NONE");
/*  555 */         if ("NONE".equals(str)) {
/*  556 */           addError("there is no xsdfile for " + str4 + " defined in the propertyfile ");
/*      */         } else {
/*  558 */           long l1 = System.currentTimeMillis();
/*  559 */           Class<?> clazz = getClass();
/*  560 */           StringBuffer stringBuffer = new StringBuffer();
/*  561 */           bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/*  562 */           if (stringBuffer.length() > 0) {
/*  563 */             String str6 = stringBuffer.toString();
/*  564 */             if (str6.indexOf("fail") != -1)
/*  565 */               addError(str6); 
/*  566 */             addOutput(str6);
/*      */           } 
/*  568 */           long l2 = System.currentTimeMillis();
/*  569 */           addDebug(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/*  570 */           if (bool) {
/*  571 */             addDebug("the xml for " + str4 + " passed the validation");
/*      */           }
/*      */         } 
/*      */       } else {
/*  575 */         addOutput("the xml for " + str4 + " doesn't need to be validated");
/*  576 */         bool = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  581 */       if (str3 != null && bool)
/*      */       {
/*  583 */         notify("XMLRECPT", str3, paramVector);
/*      */       }
/*  585 */       document = null;
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
/*  601 */     if (paramVector == null) {
/*  602 */       addDebug("XMLRECRPTABR: No MQ properties files, nothing will be generated.");
/*      */       
/*  604 */       addXMLGenMsg("NOT_REQUIRED", "XMLRECRPTABRSTATUS");
/*      */     } else {
/*  606 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  607 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  608 */       Document document = documentBuilder.newDocument();
/*  609 */       String str1 = "RECONCILE_MSGS";
/*  610 */       String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */       
/*  613 */       Element element1 = document.createElementNS(str2, str1);
/*  614 */       element1.appendChild(document.createComment("RECONCILE_MSGS Version 1 Mod 0"));
/*      */       
/*  616 */       document.appendChild(element1);
/*  617 */       element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*      */       
/*  619 */       Element element2 = document.createElement("DTSOFMSG");
/*  620 */       element2.appendChild(document.createTextNode(getNow()));
/*  621 */       element1.appendChild(element2);
/*  622 */       element2 = document.createElement("FROMMSGDTS");
/*  623 */       element2.appendChild(document.createTextNode(this.t1DTS));
/*  624 */       element1.appendChild(element2);
/*  625 */       element2 = document.createElement("TOMSGDTS");
/*  626 */       element2.appendChild(document.createTextNode(this.t2DTS));
/*  627 */       element1.appendChild(element2);
/*  628 */       Element element3 = document.createElement("MSGLIST");
/*  629 */       element1.appendChild(element3);
/*  630 */       Collection collection = paramTreeMap.values();
/*  631 */       Iterator<XMLMSGInfo> iterator = collection.iterator();
/*  632 */       while (iterator.hasNext()) {
/*  633 */         XMLMSGInfo xMLMSGInfo = iterator.next();
/*  634 */         Element element4 = document.createElement("MSGELEMENT");
/*  635 */         element3.appendChild(element4);
/*      */         
/*  637 */         element2 = document.createElement("SETUPENTITYTYPE");
/*  638 */         element2.appendChild(document.createTextNode(xMLMSGInfo.setupentitytype_xml));
/*  639 */         element4.appendChild(element2);
/*      */         
/*  641 */         element2 = document.createElement("SETUPENTITYID");
/*  642 */         element2.appendChild(document.createTextNode(xMLMSGInfo.setupentityid_xml));
/*  643 */         element4.appendChild(element2);
/*      */         
/*  645 */         element2 = document.createElement("SETUPDTS");
/*  646 */         element2.appendChild(document.createTextNode(xMLMSGInfo.setupdts_xml));
/*  647 */         element4.appendChild(element2);
/*      */         
/*  649 */         element2 = document.createElement("MSGTYPE");
/*  650 */         element2.appendChild(document.createTextNode(xMLMSGInfo.msgtype_xml));
/*  651 */         element4.appendChild(element2);
/*      */         
/*  653 */         element2 = document.createElement("MSGCOUNT");
/*  654 */         element2.appendChild(document.createTextNode(Integer.toString(xMLMSGInfo.getMsgcount_xml())));
/*  655 */         element4.appendChild(element2);
/*      */         
/*  657 */         Element element5 = document.createElement("ENTITYLIST");
/*  658 */         element4.appendChild(element5);
/*  659 */         if (!SETUP_MSGTYPE_TBL.containsKey(xMLMSGInfo.setupentitytype_xml)) {
/*  660 */           for (byte b = 0; b < xMLMSGInfo.entitylist_xml.size(); b++) {
/*  661 */             Element element = document.createElement("ENTITYELEMENT");
/*  662 */             element5.appendChild(element);
/*  663 */             String[] arrayOfString = xMLMSGInfo.entitylist_xml.elementAt(b);
/*  664 */             element2 = document.createElement("ENTITYTYPE");
/*  665 */             element2.appendChild(document.createTextNode(arrayOfString[0]));
/*  666 */             element.appendChild(element2);
/*  667 */             element2 = document.createElement("ENTITYID");
/*  668 */             element2.appendChild(document.createTextNode(arrayOfString[1]));
/*  669 */             element.appendChild(element2);
/*  670 */             element2 = document.createElement("DTSOFMSG");
/*  671 */             element2.appendChild(document.createTextNode(arrayOfString[2]));
/*  672 */             element.appendChild(element2);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*  677 */         xMLMSGInfo.dereference();
/*      */       } 
/*      */       
/*  680 */       String str3 = transformXML(document);
/*      */       
/*  682 */       boolean bool = false;
/*      */       
/*  684 */       String str4 = "XMLRECPT";
/*  685 */       String str5 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDNEEDED", "NO");
/*  686 */       if ("YES".equals(str5.toUpperCase())) {
/*  687 */         String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDFILE", "NONE"); if ("NONE".equals(str)) {
/*  688 */           addError("there is no xsdfile for " + str4 + " defined in the propertyfile ");
/*      */         } else {
/*  690 */           long l1 = System.currentTimeMillis();
/*  691 */           Class<?> clazz = getClass();
/*  692 */           StringBuffer stringBuffer = new StringBuffer();
/*  693 */           bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/*  694 */           if (stringBuffer.length() > 0) {
/*  695 */             String str6 = stringBuffer.toString();
/*  696 */             if (str6.indexOf("fail") != -1)
/*  697 */               addError(str6); 
/*  698 */             addOutput(str6);
/*      */           } 
/*  700 */           long l2 = System.currentTimeMillis();
/*  701 */           addDebug(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/*  702 */           if (bool) {
/*  703 */             addDebug("the xml for " + str4 + " passed the validation");
/*      */           }
/*      */         } 
/*      */       } else {
/*  707 */         addOutput("the xml for " + str4 + " doesn't need to be validated");
/*  708 */         bool = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  715 */       if (str3 != null && bool)
/*      */       {
/*  717 */         notify("XMLRECPT", str3, paramVector);
/*      */       }
/*  719 */       document = null;
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
/*  732 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*  733 */     Transformer transformer = transformerFactory.newTransformer();
/*  734 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/*  736 */     transformer.setOutputProperty("indent", "no");
/*  737 */     transformer.setOutputProperty("method", "xml");
/*  738 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/*  741 */     StringWriter stringWriter = new StringWriter();
/*  742 */     StreamResult streamResult = new StreamResult(stringWriter);
/*  743 */     DOMSource dOMSource = new DOMSource(paramDocument);
/*  744 */     transformer.transform(dOMSource, streamResult);
/*  745 */     String str = XMLElem.removeCheat(stringWriter.toString());
/*      */ 
/*      */ 
/*      */     
/*  749 */     transformer.setOutputProperty("indent", "yes");
/*  750 */     stringWriter = new StringWriter();
/*  751 */     streamResult = new StreamResult(stringWriter);
/*  752 */     transformer.transform(dOMSource, streamResult);
/*  753 */     addUserXML(XMLElem.removeCheat(stringWriter.toString()));
/*      */     
/*  755 */     return str;
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
/*  766 */     MessageFormat messageFormat = null;
/*      */     
/*  768 */     byte b1 = 0;
/*  769 */     boolean bool = false;
/*      */ 
/*      */     
/*  772 */     for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/*      */       
/*  774 */       String str = paramVector.elementAt(b2);
/*  775 */       addDebug("in notify looking at prop file " + str);
/*      */       try {
/*  777 */         ResourceBundle resourceBundle = ResourceBundle.getBundle(str, 
/*  778 */             getLocale(getProfile().getReadLanguage().getNLSID()));
/*  779 */         Hashtable<String, String> hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/*  780 */         boolean bool1 = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/*  781 */         hashtable.put("MQCID", getMQCID());
/*  782 */         hashtable.put("XMLTYPE", "ADS");
/*  783 */         Hashtable hashtable1 = MQUsage.getUserProperties(resourceBundle, getMQCID());
/*  784 */         if (bool1) {
/*      */           try {
/*  786 */             addDebug("User infor " + hashtable1);
/*  787 */             MQUsage.putToMQQueueWithRFH2("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString2, hashtable, hashtable1);
/*      */             
/*  789 */             messageFormat = new MessageFormat(this.rsBundle.getString("SENT_SUCCESS"));
/*  790 */             this.args[0] = str;
/*  791 */             this.args[1] = paramString1;
/*  792 */             addOutput(messageFormat.format(this.args));
/*  793 */             b1++;
/*  794 */             if (!bool) {
/*      */               
/*  796 */               addXMLGenMsg("SUCCESS", paramString1);
/*  797 */               addDebug("sent successfully to prop file " + str);
/*      */             } 
/*  799 */           } catch (MQException mQException) {
/*      */ 
/*      */             
/*  802 */             addXMLGenMsg("FAILED", paramString1);
/*  803 */             bool = true;
/*  804 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQ_ERROR"));
/*  805 */             this.args[0] = str + " " + paramString1;
/*  806 */             this.args[1] = "" + mQException.completionCode;
/*  807 */             this.args[2] = "" + mQException.reasonCode;
/*  808 */             addError(messageFormat.format(this.args));
/*  809 */             mQException.printStackTrace(System.out);
/*  810 */             addDebug("failed sending to prop file " + str);
/*  811 */           } catch (IOException iOException) {
/*      */             
/*  813 */             addXMLGenMsg("FAILED", paramString1);
/*  814 */             bool = true;
/*  815 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQIO_ERROR"));
/*  816 */             this.args[0] = str + " " + paramString1;
/*  817 */             this.args[1] = iOException.toString();
/*  818 */             addError(messageFormat.format(this.args));
/*  819 */             iOException.printStackTrace(System.out);
/*  820 */             addDebug("failed sending to prop file " + str);
/*      */           } 
/*      */         } else {
/*      */           
/*  824 */           messageFormat = new MessageFormat(this.rsBundle.getString("NO_NOTIFY"));
/*  825 */           this.args[0] = str;
/*  826 */           addError(messageFormat.format(this.args));
/*      */           
/*  828 */           addXMLGenMsg("NOT_SENT", paramString1);
/*  829 */           addDebug("not sent to prop file " + str + " because Notify not true");
/*      */         }
/*      */       
/*  832 */       } catch (MissingResourceException missingResourceException) {
/*  833 */         addXMLGenMsg("FAILED", str + " " + paramString1);
/*  834 */         bool = true;
/*  835 */         addError("Prop file " + str + " " + paramString1 + " not found");
/*      */       } 
/*      */     } 
/*      */     
/*  839 */     if (b1 > 0 && b1 != paramVector.size()) {
/*  840 */       addXMLGenMsg("ALL_NOT_SENT", paramString1);
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
/*  852 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 1) {
/*      */       
/*  854 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/*      */ 
/*      */       
/*  857 */       AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*  858 */       if (attributeChangeHistoryItem != null) {
/*  859 */         addDebug("getT2Time [" + (i - 1) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  860 */             .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*  861 */         if (attributeChangeHistoryItem.getFlagCode().equals("0050")) {
/*  862 */           this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */         } else {
/*  864 */           addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
/*      */           
/*  866 */           this.t2DTS = getNow();
/*      */         } 
/*      */       } 
/*      */     } else {
/*  870 */       this.t2DTS = getNow();
/*  871 */       addDebug("getT2Time for ADSABRSTATUS changedHistoryGroup has no history, set getNow to t2DTS");
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
/*  882 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  883 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/*  884 */     if (eANAttribute != null) {
/*  885 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/*  887 */     addDebug(paramString + " of " + entityItem.getKey() + "  was null");
/*  888 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTimestamp(String paramString) {
/*  897 */     boolean bool = false;
/*      */     
/*  899 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS", Locale.ENGLISH);
/*      */     try {
/*  901 */       simpleDateFormat.parse(paramString);
/*  902 */       bool = true;
/*  903 */     } catch (ParseException parseException) {
/*  904 */       bool = false;
/*      */     } 
/*  906 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  914 */     if (this.userxmlLen + this.rptSb.length() < 5000000) {
/*      */       
/*  916 */       BufferedInputStream bufferedInputStream = null;
/*  917 */       FileInputStream fileInputStream = null;
/*  918 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  920 */         fileInputStream = new FileInputStream(this.userxmlfn);
/*  921 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  923 */         String str = null;
/*  924 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  926 */         while ((str = bufferedReader.readLine()) != null) {
/*  927 */           this.userxmlSb.append(ADSABRSTATUS.convertToHTML(str) + NEWLINE);
/*      */         }
/*      */         
/*  930 */         File file = new File(this.userxmlfn);
/*  931 */         if (file.exists()) {
/*  932 */           file.delete();
/*      */         }
/*  934 */       } catch (Exception exception) {
/*  935 */         exception.printStackTrace();
/*      */       } finally {
/*  937 */         if (bufferedInputStream != null) {
/*      */           try {
/*  939 */             bufferedInputStream.close();
/*  940 */           } catch (Exception exception) {
/*  941 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  944 */         if (fileInputStream != null) {
/*      */           try {
/*  946 */             fileInputStream.close();
/*  947 */           } catch (Exception exception) {
/*  948 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/*  953 */       this.userxmlSb.append("XML generated was too large for this file");
/*      */     } 
/*      */     
/*  956 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/*  958 */       BufferedInputStream bufferedInputStream = null;
/*  959 */       FileInputStream fileInputStream = null;
/*  960 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  962 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  963 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  965 */         String str = null;
/*  966 */         StringBuffer stringBuffer = new StringBuffer();
/*  967 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  969 */         while ((str = bufferedReader.readLine()) != null) {
/*  970 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  972 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  975 */         File file = new File(this.dbgfn);
/*  976 */         if (file.exists()) {
/*  977 */           file.delete();
/*      */         }
/*  979 */       } catch (Exception exception) {
/*  980 */         exception.printStackTrace();
/*      */       } finally {
/*  982 */         if (bufferedInputStream != null) {
/*      */           try {
/*  984 */             bufferedInputStream.close();
/*  985 */           } catch (Exception exception) {
/*  986 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  989 */         if (fileInputStream != null) {
/*      */           try {
/*  991 */             fileInputStream.close();
/*  992 */           } catch (Exception exception) {
/*  993 */             exception.printStackTrace();
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
/* 1007 */     Locale locale = null;
/* 1008 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 1011 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1035 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addXMLGenMsg(String paramString1, String paramString2) {
/* 1042 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/* 1043 */     Object[] arrayOfObject = { paramString2 };
/* 1044 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 1051 */     super.dereference();
/*      */     
/* 1053 */     this.rsBundle = null;
/* 1054 */     this.rptSb = null;
/* 1055 */     this.args = null;
/*      */     
/* 1057 */     this.metaTbl = null;
/* 1058 */     this.navName = null;
/* 1059 */     this.vctReturnsEntityKeys.clear();
/* 1060 */     this.vctReturnsEntityKeys = null;
/*      */     
/* 1062 */     this.dbgPw = null;
/* 1063 */     this.dbgfn = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1069 */     return "$Revision: 1.2 $";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMQCID() {
/* 1075 */     return "RECONCILE_MSGS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1082 */     return "ADSIDLSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1088 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1095 */     this.dbgLen += paramString.length();
/* 1096 */     this.dbgPw.println(paramString);
/* 1097 */     this.dbgPw.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(int paramInt, String paramString) {
/* 1106 */     if (paramInt <= this.abr_debuglvl) {
/* 1107 */       addDebug(paramString);
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
/* 1119 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1122 */     addMessage(this.rsBundle.getString("ERROR_PREFIX"), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1128 */     addOutput(paramString);
/* 1129 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1139 */     String str = this.rsBundle.getString(paramString2);
/*      */     
/* 1141 */     if (paramArrayOfObject != null) {
/* 1142 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1143 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1146 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1156 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 1160 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1161 */     if (eANList == null) {
/* 1162 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1163 */       eANList = entityGroup.getMetaAttribute();
/* 1164 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1166 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1167 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1168 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1169 */       if (b + 1 < eANList.size()) {
/* 1170 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1174 */     return stringBuffer.toString().trim();
/*      */   }
/*      */   
/*      */   private static class XMLMSGInfo
/*      */   {
/* 1179 */     String setupentitytype_xml = "@@";
/* 1180 */     String setupentityid_xml = "@@";
/* 1181 */     String setupdts_xml = "@@";
/* 1182 */     String msgtype_xml = "@@";
/*      */     int msgcount_xml;
/* 1184 */     Vector entitylist_xml = new Vector();
/*      */ 
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
/* 1196 */       if (param1String1 != null) {
/* 1197 */         this.setupentitytype_xml = param1String1.trim();
/*      */       }
/*      */       
/* 1200 */       if (param1Int1 != 0) {
/* 1201 */         this.setupentityid_xml = Integer.toString(param1Int1);
/*      */       }
/*      */       
/* 1204 */       if (param1String2 != null) {
/* 1205 */         this.setupdts_xml = param1String2.trim();
/*      */       }
/*      */       
/* 1208 */       if (param1String3 != null) {
/* 1209 */         this.msgtype_xml = param1String3.trim();
/*      */       }
/* 1211 */       this.msgcount_xml = param1Int2;
/*      */     }
/*      */ 
/*      */     
/*      */     void dereference() {
/* 1216 */       this.setupentitytype_xml = null;
/* 1217 */       this.setupentityid_xml = null;
/* 1218 */       this.setupdts_xml = null;
/* 1219 */       this.msgtype_xml = null;
/* 1220 */       if (this.entitylist_xml != null)
/* 1221 */         this.entitylist_xml.clear(); 
/* 1222 */       this.entitylist_xml = null;
/*      */     }
/*      */     
/*      */     public Vector getEntitylist_xml() {
/* 1226 */       return this.entitylist_xml;
/*      */     }
/*      */     
/*      */     public void setEntitylist_xml(Vector param1Vector) {
/* 1230 */       this.entitylist_xml = param1Vector;
/*      */     }
/*      */     
/*      */     public int getMsgcount_xml() {
/* 1234 */       return this.msgcount_xml;
/*      */     }
/*      */     
/*      */     public void setMsgcount_xml(int param1Int) {
/* 1238 */       this.msgcount_xml = param1Int;
/*      */     }
/*      */     
/*      */     String getKey() {
/* 1242 */       return this.setupentitytype_xml + this.setupentityid_xml + this.setupdts_xml + this.msgtype_xml;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\XMLRECRPTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */