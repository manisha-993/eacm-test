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
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
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
/*      */ public class ADSDELXMLABR
/*      */   extends PokBaseABR
/*      */ {
/*      */   private static final int MAXFILE_SIZE = 5000000;
/*  108 */   private StringBuffer rptSb = new StringBuffer();
/*  109 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  110 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  111 */   private int abr_debuglvl = 0;
/*  112 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  113 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  115 */   private Hashtable metaTbl = new Hashtable<>();
/*  116 */   private ResourceBundle rsBundle = null;
/*  117 */   private String attrXMLABRPROPFILE = "XMLABRPROPFILE";
/*      */   
/*  119 */   private String abrName = "ADSDELXMLABR";
/*  120 */   private String rootEntityType = "ADSDELXMLSETUP";
/*  121 */   private String entityType = "";
/*  122 */   private String navName = "";
/*  123 */   private String pdhdomain = "";
/*  124 */   private String actionTaken = "";
/*  125 */   private int dbgLen = 0;
/*  126 */   private PrintWriter dbgPw = null;
/*  127 */   private String dbgfn = null;
/*  128 */   private PrintWriter userxmlPw = null;
/*  129 */   private String userxmlfn = null;
/*  130 */   private StringBuffer userxmlSb = new StringBuffer();
/*  131 */   private String t2DTS = "&nbsp;";
/*  132 */   private String t1DTS = "&nbsp;";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String STATUS_QUEUE = "0020";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String CHEAT = "@@";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  156 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */     
/*  160 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  172 */     String str3 = "";
/*      */     
/*  174 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  177 */       start_ABRBuild(false);
/*      */       
/*  179 */       this
/*  180 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */       
/*  182 */       setupPrintWriter();
/*      */ 
/*      */       
/*  185 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), 
/*  186 */           ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  189 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  191 */               getEntityType(), getEntityID()) });
/*  192 */       long l = System.currentTimeMillis();
/*      */ 
/*      */       
/*  195 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  197 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem
/*  198 */           .getKey() + " extract: " + this.m_abri
/*  199 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + 
/*  200 */           PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  203 */       setReturnCode(0);
/*      */ 
/*      */       
/*  206 */       this.navName = getNavigationName(entityItem);
/*      */ 
/*      */       
/*  209 */       this.entityType = PokUtils.getAttributeValue(entityItem, "XMLENTITYTYPEDEL", "", null, false);
/*      */       
/*  211 */       addDebug("Executing for entityType: " + this.entityType);
/*      */       
/*  213 */       this.pdhdomain = PokUtils.getAttributeValue(entityItem, "PDHDOMAIN", "", null, false);
/*      */       
/*  215 */       addDebug("Entity's PDHDOMAIN: " + this.pdhdomain);
/*      */       
/*  217 */       if (this.entityType == null) {
/*      */         
/*  219 */         this.args[0] = PokUtils.getAttributeDescription(entityItem
/*  220 */             .getEntityGroup(), "XMLENTITYTYPEDEL", "XMLENTITYTYPEDEL");
/*      */         
/*  222 */         addError("INVALID_ATTR_ERR", this.args);
/*      */       } 
/*  224 */       addDebug("getT1 entered for Periodic ADSDELXMLSETUP " + entityItem
/*  225 */           .getKey());
/*      */ 
/*      */       
/*  228 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("ADSDTS");
/*  229 */       if (eANMetaAttribute == null) {
/*  230 */         throw new MiddlewareException("ADSDTS not in meta for Periodic ABR " + entityItem
/*      */             
/*  232 */             .getKey());
/*      */       }
/*  234 */       eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("XMLABRPROPFILE");
/*      */       
/*  236 */       if (eANMetaAttribute == null) {
/*  237 */         throw new MiddlewareException("XMLABRPROPFILE not in meta for Periodic ABR " + entityItem
/*      */             
/*  239 */             .getKey());
/*      */       }
/*  241 */       String str = PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE);
/*      */       
/*  243 */       if (str == null) {
/*  244 */         addError(this.rootEntityType + ": No MQ properties files, nothing will be generated.");
/*      */ 
/*      */ 
/*      */         
/*  248 */         addXMLGenMsg("NOT_REQUIRED", this.rootEntityType);
/*      */       } 
/*  250 */       this.t1DTS = PokUtils.getAttributeValue(entityItem, "ADSDTS", ", ", this.m_strEpoch, false);
/*      */       
/*  252 */       boolean bool = isTimestamp(this.t1DTS);
/*  253 */       if (bool && getReturnCode() == 0) {
/*  254 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = getSTATUSHistory(this.abrName);
/*  255 */         setT2DTS(attributeChangeHistoryGroup);
/*  256 */         processThis(entityItem, str);
/*  257 */       } else if (!bool) {
/*  258 */         addError("Invalid DateTime Stamp for ADSDTS, please put format: yyyy-MM-dd-HH.mm.ss.SSSSSS ");
/*      */       } 
/*  260 */       if (getReturnCode() == 0) {
/*  261 */         PDGUtility pDGUtility = new PDGUtility();
/*  262 */         OPICMList oPICMList = new OPICMList();
/*  263 */         oPICMList.put("ADSDTS", "ADSDTS=" + this.t2DTS);
/*  264 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */       } 
/*  266 */       addDebug("Total Time: " + 
/*  267 */           Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  269 */     catch (Throwable throwable) {
/*  270 */       StringWriter stringWriter = new StringWriter();
/*  271 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  272 */       String str7 = "<pre>{0}</pre>";
/*  273 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  274 */       setReturnCode(-3);
/*  275 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  277 */       this.args[0] = throwable.getMessage();
/*  278 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  279 */       messageFormat1 = new MessageFormat(str7);
/*  280 */       this.args[0] = stringWriter.getBuffer().toString();
/*  281 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  282 */       logError("Exception: " + throwable.getMessage());
/*  283 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*  285 */       setDGTitle(this.navName);
/*  286 */       setDGRptName(getShortClassName(getClass()));
/*  287 */       setDGRptClass(getABRCode());
/*      */       
/*  289 */       if (!isReadOnly()) {
/*  290 */         clearSoftLock();
/*      */       }
/*  292 */       closePrintWriter();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  297 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  298 */     this.args[0] = getDescription();
/*  299 */     this.args[1] = this.navName;
/*  300 */     String str4 = messageFormat.format(this.args);
/*  301 */     messageFormat = new MessageFormat(str2);
/*  302 */     this.args[0] = this.m_prof.getOPName();
/*  303 */     this.args[1] = this.m_prof.getRoleDescription();
/*  304 */     this.args[2] = this.m_prof.getWGName();
/*  305 */     this.args[3] = getNow();
/*  306 */     this.args[4] = this.t1DTS;
/*  307 */     this.args[5] = this.navName;
/*  308 */     this.args[6] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*      */     
/*  310 */     this.args[7] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/*  311 */     this.args[8] = str3 + " " + getABRVersion();
/*      */     
/*  313 */     restoreXtraContent();
/*      */ 
/*      */     
/*  316 */     String str5 = str4 + messageFormat.format(this.args) + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  317 */     this.rptSb.insert(0, str5 + NEWLINE);
/*      */     
/*  319 */     println(this.rptSb.toString());
/*  320 */     printDGSubmitString();
/*  321 */     println(EACustom.getTOUDiv());
/*  322 */     buildReportFooter();
/*      */     
/*  324 */     this.metaTbl.clear();
/*      */   }
/*      */   
/*      */   private void setupPrintWriter() {
/*  328 */     String str = this.m_abri.getFileName();
/*  329 */     int i = str.lastIndexOf(".");
/*  330 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*  331 */     this.userxmlfn = str.substring(0, i + 1) + "userxml";
/*      */     try {
/*  333 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*      */     }
/*  335 */     catch (Exception exception) {
/*  336 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */     try {
/*  339 */       this.userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userxmlfn, true), "UTF-8"));
/*      */     }
/*  341 */     catch (Exception exception) {
/*  342 */       D.ebug(0, "trouble creating xmlgen PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void closePrintWriter() {
/*  347 */     if (this.dbgPw != null) {
/*  348 */       this.dbgPw.flush();
/*  349 */       this.dbgPw.close();
/*  350 */       this.dbgPw = null;
/*      */     } 
/*  352 */     if (this.userxmlPw != null) {
/*  353 */       this.userxmlPw.flush();
/*  354 */       this.userxmlPw.close();
/*  355 */       this.userxmlPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  366 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/*  370 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/*  371 */     if (eANList == null) {
/*      */       
/*  373 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/*  374 */       eANList = entityGroup.getMetaAttribute();
/*      */       
/*  376 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/*  378 */     for (byte b = 0; b < eANList.size(); b++) {
/*  379 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/*  380 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute
/*  381 */             .getAttributeCode(), ", ", "", false));
/*  382 */       if (b + 1 < eANList.size()) {
/*  383 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/*  387 */     return stringBuffer.toString().trim();
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
/*      */   private void setT2DTS(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) throws MiddlewareException {
/*  399 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup
/*  400 */       .getChangeHistoryItemCount() > 1) {
/*      */       
/*  402 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/*      */ 
/*      */ 
/*      */       
/*  406 */       AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 2);
/*  407 */       if (attributeChangeHistoryItem != null) {
/*  408 */         addDebug("getT2Time [" + (i - 2) + "] isActive: " + attributeChangeHistoryItem
/*  409 */             .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  410 */             .getChangeDate() + " flagcode: " + attributeChangeHistoryItem
/*  411 */             .getFlagCode());
/*  412 */         if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/*  413 */           this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */         } else {
/*  415 */           addDebug("getT2Time for the value of " + attributeChangeHistoryItem
/*  416 */               .getFlagCode() + "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
/*      */           
/*  418 */           this.t2DTS = getNow();
/*      */         } 
/*      */       } 
/*      */     } else {
/*  422 */       this.t2DTS = getNow();
/*  423 */       addDebug("getT2Time for " + this.abrName + " changedHistoryGroup has no history, set getNow to t2DTS");
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
/*      */   private AttributeChangeHistoryGroup getSTATUSHistory(String paramString) throws MiddlewareException {
/*  439 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  440 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/*  441 */     if (eANAttribute != null) {
/*  442 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/*  444 */     addDebug(paramString + " of " + entityItem.getKey() + "  was null");
/*  445 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTimestamp(String paramString) {
/*  456 */     boolean bool = false;
/*      */     
/*  458 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS", Locale.ENGLISH);
/*      */     
/*      */     try {
/*  461 */       simpleDateFormat.parse(paramString);
/*  462 */       bool = true;
/*  463 */     } catch (ParseException parseException) {
/*  464 */       bool = false;
/*      */     } 
/*  466 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addXMLGenMsg(String paramString1, String paramString2) {
/*  473 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/*  474 */     Object[] arrayOfObject = { paramString2 };
/*  475 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/*  484 */     this.dbgLen += paramString.length();
/*  485 */     this.dbgPw.println(paramString);
/*  486 */     this.dbgPw.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(int paramInt, String paramString) {
/*  497 */     if (paramInt <= this.abr_debuglvl) {
/*  498 */       addDebug(paramString);
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
/*  510 */     setReturnCode(-1);
/*      */ 
/*      */     
/*  513 */     addMessage(this.rsBundle.getString("ERROR_PREFIX"), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/*  520 */     addOutput(paramString);
/*  521 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/*  529 */     String str = this.rsBundle.getString(paramString2);
/*      */     
/*  531 */     if (paramArrayOfObject != null) {
/*  532 */       MessageFormat messageFormat = new MessageFormat(str);
/*  533 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/*  536 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/*  545 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  554 */     if (this.rptSb.length() < 5000000) {
/*      */       
/*  556 */       BufferedInputStream bufferedInputStream = null;
/*  557 */       FileInputStream fileInputStream = null;
/*  558 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  560 */         fileInputStream = new FileInputStream(this.userxmlfn);
/*  561 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  563 */         String str = null;
/*  564 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  566 */         while ((str = bufferedReader.readLine()) != null) {
/*  567 */           this.userxmlSb.append(ADSABRSTATUS.convertToHTML(str) + NEWLINE);
/*      */         }
/*      */         
/*  570 */         File file = new File(this.userxmlfn);
/*  571 */         if (file.exists()) {
/*  572 */           file.delete();
/*      */         }
/*  574 */       } catch (Exception exception) {
/*  575 */         exception.printStackTrace();
/*      */       } finally {
/*  577 */         if (bufferedInputStream != null) {
/*      */           try {
/*  579 */             bufferedInputStream.close();
/*  580 */           } catch (Exception exception) {
/*  581 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  584 */         if (fileInputStream != null) {
/*      */           try {
/*  586 */             fileInputStream.close();
/*  587 */           } catch (Exception exception) {
/*  588 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/*  593 */       this.userxmlSb.append("XML generated was too large for this file");
/*      */     } 
/*      */ 
/*      */     
/*  597 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/*  599 */       BufferedInputStream bufferedInputStream = null;
/*  600 */       FileInputStream fileInputStream = null;
/*  601 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  603 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  604 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  606 */         String str = null;
/*  607 */         StringBuffer stringBuffer = new StringBuffer();
/*  608 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  610 */         while ((str = bufferedReader.readLine()) != null) {
/*  611 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  613 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  616 */         File file = new File(this.dbgfn);
/*  617 */         if (file.exists()) {
/*  618 */           file.delete();
/*      */         }
/*  620 */       } catch (Exception exception) {
/*  621 */         exception.printStackTrace();
/*      */       } finally {
/*  623 */         if (bufferedInputStream != null) {
/*      */           try {
/*  625 */             bufferedInputStream.close();
/*  626 */           } catch (Exception exception) {
/*  627 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  630 */         if (fileInputStream != null) {
/*      */           try {
/*  632 */             fileInputStream.close();
/*  633 */           } catch (Exception exception) {
/*  634 */             exception.printStackTrace();
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
/*      */   public List getDeleteEntity() throws SQLException, MiddlewareException {
/*  648 */     ArrayList<ADSDELENTITY> arrayList = new ArrayList();
/*      */     
/*  650 */     addDebug(this.abrName + ".processThis checking between " + this.t1DTS + " and " + this.t2DTS);
/*      */     
/*  652 */     addDebug("get all detlete entitys---------------");
/*  653 */     StringBuffer stringBuffer = new StringBuffer();
/*  654 */     stringBuffer.append("select e.entityid as id, e.entitytype as type from opicm.entity e where e.valto>current timestamp");
/*  655 */     stringBuffer.append(" and e.valfrom BETWEEN '" + this.t1DTS + "' AND '" + this.t2DTS + "' and e.effto<current timestamp");
/*      */     
/*  657 */     stringBuffer.append(" and EXISTS (select entityid from opicm.flag f1 where f1.entityid=e.entityid and f1.entitytype=e.entitytype and f1.attributecode='ADSABRSTATUS' and f1.attributevalue='0030')");
/*  658 */     stringBuffer.append(" and EXISTS (select entityid from opicm.flag f2 where f2.entityid=e.entityid and f2.entitytype=e.entitytype and f2.attributecode='STATUS' and f2.attributevalue in ('0020','0040')) ");
/*  659 */     addDebug("SQL:" + stringBuffer);
/*  660 */     ResultSet resultSet = null;
/*  661 */     PreparedStatement preparedStatement = null;
/*      */     try {
/*  663 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(stringBuffer
/*  664 */           .toString());
/*  665 */       resultSet = preparedStatement.executeQuery();
/*  666 */       while (resultSet.next()) {
/*  667 */         int i = resultSet.getInt(1);
/*  668 */         String str = resultSet.getString(2);
/*  669 */         ADSDELENTITY aDSDELENTITY = new ADSDELENTITY();
/*  670 */         aDSDELENTITY.setId(i);
/*  671 */         aDSDELENTITY.setType(str);
/*  672 */         arrayList.add(aDSDELENTITY);
/*      */       } 
/*  674 */       return arrayList;
/*      */     } finally {
/*      */       
/*  677 */       if (preparedStatement != null) {
/*      */         try {
/*  679 */           preparedStatement.close();
/*  680 */         } catch (SQLException sQLException) {
/*  681 */           sQLException.printStackTrace();
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
/*      */   public void processThis(EntityItem paramEntityItem, String paramString) throws Exception {
/*  694 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  695 */     List<ADSDELENTITY> list = getDeleteEntity();
/*  696 */     byte b = 0;
/*      */     try {
/*  698 */       for (byte b1 = 0; b1 < list.size(); b1++) {
/*  699 */         ADSDELENTITY aDSDELENTITY = list.get(b1);
/*  700 */         if (this.entityType.equals("PRODSTRUCT") && aDSDELENTITY
/*  701 */           .getType().equals("PRODSTRUCT")) {
/*  702 */           b++;
/*  703 */           String str = aDSDELENTITY.getType();
/*  704 */           if (!hashMap.containsKey(str)) {
/*  705 */             Vector<ADSDELPRODSTRUCT> vector = new Vector();
/*  706 */             vector.add(getDelTMFInfo(aDSDELENTITY.getType(), aDSDELENTITY
/*  707 */                   .getId()));
/*  708 */             hashMap.put(str, vector);
/*      */           } else {
/*  710 */             Vector<ADSDELPRODSTRUCT> vector = (Vector)hashMap.get(str);
/*  711 */             vector.add(getDelTMFInfo(aDSDELENTITY.getType(), aDSDELENTITY.getId()));
/*      */           } 
/*  713 */           delCache(aDSDELENTITY.getType(), aDSDELENTITY.getId());
/*      */         } 
/*      */       } 
/*      */       
/*  717 */       sentToMQ(hashMap, paramEntityItem);
/*  718 */       addOutput("The total number is " + b + " entities");
/*      */     }
/*  720 */     catch (RuntimeException runtimeException) {
/*  721 */       addXMLGenMsg("FAILED", paramEntityItem.getKey());
/*  722 */       addDebug("RuntimeException on ? " + runtimeException);
/*  723 */       runtimeException.printStackTrace();
/*  724 */       throw runtimeException;
/*  725 */     } catch (Exception exception) {
/*  726 */       addXMLGenMsg("FAILED", paramEntityItem.getKey());
/*  727 */       addDebug("Exception on ? " + exception);
/*  728 */       exception.printStackTrace();
/*  729 */       throw exception;
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
/*      */   private ADSDELPRODSTRUCT getDelTMFInfo(String paramString, int paramInt) throws SQLException, MiddlewareException {
/*  744 */     ADSDELPRODSTRUCT aDSDELPRODSTRUCT = new ADSDELPRODSTRUCT();
/*      */     
/*  746 */     addDebug("Get Delete PRODSTRUCT info---------------");
/*  747 */     StringBuffer stringBuffer = new StringBuffer();
/*  748 */     stringBuffer.append("select r.entity2id as MODELID, r.entity2type as MODELTYPE,(select f.attributevalue from opicm.flag f where f.entitytype=r.entity2type and f.entityid=r.entity2id and f.attributecode='MACHTYPEATR' order by valfrom desc fetch first 1 row only ) as MACHTYPEATR ,(select t1.attributevalue from opicm.text t1 where t1.entitytype=r.entity2type and t1.entityid=r.entity2id and t1.attributecode='MODELATR' order by valfrom desc fetch first 1 row only ) as MODELATR ,  r.entity1id as FEATUREID, r.entity1type as FEATURETYPE, (select t2.attributevalue from opicm.text t2 where t2.entitytype=r.entity1type and t2.entityid=r.entity1id and t2.attributecode='FEATURECODE' order by valfrom desc fetch first 1 row only ) as FEATURECODE  from opicm.relator r where r.entitytype='" + paramString + "' and r.entityid=" + paramInt + " and r.valfrom>'" + this.t1DTS + "' and r.effto<current timestamp");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  760 */     addDebug("SQL:" + stringBuffer);
/*  761 */     ResultSet resultSet = null;
/*  762 */     PreparedStatement preparedStatement = null;
/*      */     try {
/*  764 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(stringBuffer
/*  765 */           .toString());
/*  766 */       resultSet = preparedStatement.executeQuery();
/*  767 */       while (resultSet.next()) {
/*      */         
/*  769 */         int i = resultSet.getInt(1);
/*  770 */         String str1 = resultSet.getString(2);
/*  771 */         String str2 = resultSet.getString(3);
/*  772 */         String str3 = resultSet.getString(4);
/*  773 */         int j = resultSet.getInt(5);
/*  774 */         String str4 = resultSet.getString(6);
/*  775 */         String str5 = resultSet.getString(7);
/*      */         
/*  777 */         addDebug("DelProdStructInfo MODELENTITYID:" + i + " MODELENTITYTYPE:" + str1 + " MACHTYPE:" + str2 + " MODEL:" + str3 + " FEATUREENTITYTYPE:" + str4 + " FEATUREENTITYID:" + j + " FEATURECODE:" + str5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  784 */         aDSDELPRODSTRUCT.setEntityType(paramString);
/*  785 */         aDSDELPRODSTRUCT.setEntityId("0".equals(Integer.toString(paramInt)) ? "@@" : 
/*  786 */             Integer.toString(paramInt));
/*  787 */         aDSDELPRODSTRUCT.setModelType(str1);
/*  788 */         aDSDELPRODSTRUCT.setModelId("0".equals(Integer.toString(paramInt)) ? "@@" : 
/*  789 */             Integer.toString(i));
/*  790 */         aDSDELPRODSTRUCT.setMachtypeatr(str2);
/*  791 */         aDSDELPRODSTRUCT.setModelatr(str3);
/*  792 */         aDSDELPRODSTRUCT.setFeatureType(str4);
/*  793 */         aDSDELPRODSTRUCT.setFeatureId("0".equals(Integer.toString(paramInt)) ? "@@" : 
/*  794 */             Integer.toString(j));
/*  795 */         aDSDELPRODSTRUCT.setFeatureCode(str5);
/*      */       } 
/*  797 */       return aDSDELPRODSTRUCT;
/*      */     } finally {
/*      */       
/*  800 */       if (preparedStatement != null) {
/*      */         try {
/*  802 */           preparedStatement.close();
/*  803 */         } catch (SQLException sQLException) {
/*  804 */           sQLException.printStackTrace();
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
/*      */   private void delCache(String paramString, int paramInt) throws SQLException, MiddlewareException {
/*  820 */     addDebug("update cache table---------------");
/*  821 */     StringBuffer stringBuffer = new StringBuffer();
/*  822 */     stringBuffer.append("update cache.xmlidlcache set xmlcachevalidto=current timestamp where xmlentitytype ='" + paramString + "' and xmlentityid=" + paramInt + " and xmlcachevalidto>current timestamp");
/*      */ 
/*      */     
/*  825 */     addDebug("SQL:" + stringBuffer);
/*  826 */     PreparedStatement preparedStatement = null;
/*      */     try {
/*  828 */       preparedStatement = this.m_db.getODSConnection().prepareStatement(stringBuffer
/*  829 */           .toString());
/*  830 */       int i = preparedStatement.executeUpdate();
/*  831 */       if (i == 1) {
/*  832 */         addDebug("cache table update success");
/*  833 */       } else if (i == 0) {
/*  834 */         addDebug("No row was found for the xml in cache table");
/*      */       } else {
/*  836 */         addDebug("cache table update failed");
/*      */       } 
/*      */     } finally {
/*      */       
/*  840 */       if (preparedStatement != null) {
/*      */         try {
/*  842 */           preparedStatement.close();
/*  843 */         } catch (SQLException sQLException) {
/*  844 */           sQLException.printStackTrace();
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
/*      */   private void sentToMQ(HashMap paramHashMap, EntityItem paramEntityItem) throws DOMException, MissingResourceException, ParserConfigurationException, TransformerException {
/*  861 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*      */     
/*  863 */     Vector<String> vector = new Vector();
/*  864 */     if (str != null) {
/*      */       
/*  866 */       StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  867 */       while (stringTokenizer.hasMoreTokens()) {
/*  868 */         vector.addElement(stringTokenizer.nextToken());
/*      */       }
/*      */     } 
/*      */     
/*  872 */     if (paramHashMap.size() == 0) {
/*  873 */       processMQZero(paramHashMap, vector);
/*  874 */       addDebug("send zero report when xmlmsgMap.size is 0");
/*      */     } else {
/*  876 */       addDebug("send normal report when xmlmsgMap.size > 0");
/*  877 */       processMQ(paramHashMap, vector);
/*      */     } 
/*      */ 
/*      */     
/*  881 */     paramHashMap.clear();
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
/*      */   private void processMQZero(HashMap paramHashMap, Vector paramVector) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
/*  895 */     if (paramVector == null) {
/*  896 */       addDebug(this.rootEntityType + ": No MQ properties files, nothing will be generated.");
/*      */ 
/*      */       
/*  899 */       addXMLGenMsg("NOT_REQUIRED", this.rootEntityType);
/*      */     } else {
/*      */       
/*  902 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  903 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  904 */       Document document = documentBuilder.newDocument();
/*  905 */       String str1 = "DELETEXML_MSGS";
/*  906 */       String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */ 
/*      */       
/*  910 */       Element element1 = document.createElementNS(str2, str1);
/*  911 */       element1.appendChild(document.createComment("DELETEXML_MSGS Version 1 Mod 0"));
/*      */ 
/*      */ 
/*      */       
/*  915 */       document.appendChild(element1);
/*  916 */       element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*      */ 
/*      */       
/*  919 */       Element element2 = document.createElement("DTSOFMSG");
/*  920 */       element2.appendChild(document.createTextNode(getNow()));
/*  921 */       element1.appendChild(element2);
/*  922 */       element2 = document.createElement("FROMMSGDTS");
/*  923 */       element2.appendChild(document.createTextNode(this.t1DTS));
/*  924 */       element1.appendChild(element2);
/*  925 */       element2 = document.createElement("TOMSGDTS");
/*  926 */       element2.appendChild(document.createTextNode(this.t2DTS));
/*  927 */       element1.appendChild(element2);
/*  928 */       element2 = document.createElement("PDHDOMAIN");
/*  929 */       element2.appendChild(document.createTextNode(this.pdhdomain));
/*  930 */       element1.appendChild(element2);
/*  931 */       Element element3 = document.createElement("ENTITYLIST");
/*  932 */       element1.appendChild(element3);
/*      */       
/*  934 */       String str3 = transformXML(document);
/*  935 */       addDebug("the final xml:" + str3);
/*  936 */       boolean bool = false;
/*      */ 
/*      */       
/*  939 */       String str4 = ABRServerProperties.getValue(this.abrName, "_" + this.rootEntityType + "_XSDNEEDED", "NO");
/*      */       
/*  941 */       if ("YES".equals(str4.toUpperCase())) {
/*      */         
/*  943 */         String str = ABRServerProperties.getValue(this.abrName, "_" + this.rootEntityType + "_XSDFILE", "NONE");
/*      */         
/*  945 */         if ("NONE".equals(str)) {
/*  946 */           addError("there is no xsdfile for " + this.rootEntityType + " defined in the propertyfile ");
/*      */         } else {
/*      */           
/*  949 */           long l1 = System.currentTimeMillis();
/*  950 */           Class<?> clazz = getClass();
/*  951 */           StringBuffer stringBuffer = new StringBuffer();
/*  952 */           bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/*  953 */           if (stringBuffer.length() > 0) {
/*  954 */             String str5 = stringBuffer.toString();
/*  955 */             if (str5.indexOf("fail") != -1)
/*  956 */               addError(str5); 
/*  957 */             addOutput(str5);
/*      */           } 
/*  959 */           long l2 = System.currentTimeMillis();
/*  960 */           addDebug(3, "Time for validation: " + 
/*      */ 
/*      */               
/*  963 */               Stopwatch.format(l2 - l1));
/*  964 */           if (bool) {
/*  965 */             addDebug("the xml for " + this.rootEntityType + " passed the validation");
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*  970 */         addOutput("the xml for " + this.rootEntityType + " doesn't need to be validated");
/*      */         
/*  972 */         bool = true;
/*      */       } 
/*      */       
/*  975 */       if (str3 != null && bool) {
/*  976 */         notify(this.rootEntityType, str3, paramVector);
/*      */       }
/*  978 */       document = null;
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
/*      */   private void processMQ(HashMap paramHashMap, Vector paramVector) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
/*  993 */     if (paramVector == null) {
/*  994 */       addDebug(this.rootEntityType + ": No MQ properties files, nothing will be generated.");
/*      */ 
/*      */       
/*  997 */       addXMLGenMsg("NOT_REQUIRED", this.rootEntityType);
/*      */     } else {
/*      */       
/* 1000 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 1001 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 1002 */       Document document = documentBuilder.newDocument();
/* 1003 */       String str1 = "DELETEXML_MSGS";
/* 1004 */       String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1010 */       Element element1 = document.createElementNS(str2, str1);
/* 1011 */       element1.appendChild(document.createComment("DELETEXML_MSGS Version 1 Mod 0"));
/*      */ 
/*      */ 
/*      */       
/* 1015 */       document.appendChild(element1);
/* 1016 */       element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*      */ 
/*      */       
/* 1019 */       Element element2 = document.createElement("DTSOFMSG");
/* 1020 */       element2.appendChild(document.createTextNode(getNow()));
/* 1021 */       element1.appendChild(element2);
/* 1022 */       element2 = document.createElement("FROMMSGDTS");
/* 1023 */       element2.appendChild(document.createTextNode(this.t1DTS));
/* 1024 */       element1.appendChild(element2);
/* 1025 */       element2 = document.createElement("TOMSGDTS");
/* 1026 */       element2.appendChild(document.createTextNode(this.t2DTS));
/* 1027 */       element1.appendChild(element2);
/* 1028 */       element2 = document.createElement("PDHDOMAIN");
/* 1029 */       element2.appendChild(document.createTextNode(this.pdhdomain));
/* 1030 */       element1.appendChild(element2);
/*      */       
/* 1032 */       Element element3 = document.createElement("ENTITYLIST");
/* 1033 */       element1.appendChild(element3);
/* 1034 */       if (paramHashMap.size() > 0 && 
/* 1035 */         paramHashMap.containsKey("PRODSTRUCT")) {
/*      */         
/* 1037 */         Vector<ADSDELPRODSTRUCT> vector = (Vector)paramHashMap.get("PRODSTRUCT");
/* 1038 */         for (byte b = 0; b < vector.size(); b++) {
/*      */           
/* 1040 */           ADSDELPRODSTRUCT aDSDELPRODSTRUCT = vector.elementAt(b);
/*      */ 
/*      */           
/* 1043 */           Element element = document.createElement("ENTITYELEMENT");
/* 1044 */           element3.appendChild(element);
/*      */           
/* 1046 */           element2 = document.createElement("ENTITYTYPE");
/* 1047 */           element2.appendChild(document.createTextNode(aDSDELPRODSTRUCT
/* 1048 */                 .getEntityType()));
/* 1049 */           element.appendChild(element2);
/* 1050 */           element2 = document.createElement("ENTITYID");
/* 1051 */           element2.appendChild(document.createTextNode(aDSDELPRODSTRUCT
/* 1052 */                 .getEntityId()));
/* 1053 */           element.appendChild(element2);
/* 1054 */           element2 = document.createElement("ACTIVITY");
/* 1055 */           element2.appendChild(document.createTextNode("Delete"));
/* 1056 */           element.appendChild(element2);
/*      */           
/* 1058 */           element2 = document.createElement("MODELENTITYTYPE");
/* 1059 */           element2.appendChild(document.createTextNode(aDSDELPRODSTRUCT
/* 1060 */                 .getModelType()));
/* 1061 */           element.appendChild(element2);
/*      */           
/* 1063 */           element2 = document.createElement("MODELENTITYID");
/* 1064 */           element2.appendChild(document.createTextNode(aDSDELPRODSTRUCT
/* 1065 */                 .getModelId()));
/* 1066 */           element.appendChild(element2);
/* 1067 */           element2 = document.createElement("MACHTYPE");
/* 1068 */           element2.appendChild(document.createTextNode(aDSDELPRODSTRUCT
/* 1069 */                 .getMachtypeatr()));
/* 1070 */           element.appendChild(element2);
/* 1071 */           element2 = document.createElement("MODEL");
/* 1072 */           element2.appendChild(document.createTextNode(aDSDELPRODSTRUCT
/* 1073 */                 .getModelatr()));
/* 1074 */           element.appendChild(element2);
/*      */           
/* 1076 */           element2 = document.createElement("FEATUREENTITYTYPE");
/* 1077 */           element2.appendChild(document.createTextNode(aDSDELPRODSTRUCT
/* 1078 */                 .getFeatureType()));
/* 1079 */           element.appendChild(element2);
/*      */           
/* 1081 */           element2 = document.createElement("FEATUREENTITYID");
/* 1082 */           element2.appendChild(document.createTextNode(aDSDELPRODSTRUCT
/* 1083 */                 .getFeatureId()));
/* 1084 */           element.appendChild(element2);
/* 1085 */           element2 = document.createElement("FEATURECODE");
/* 1086 */           element2.appendChild(document.createTextNode(aDSDELPRODSTRUCT
/* 1087 */                 .getFeatureCode()));
/* 1088 */           element.appendChild(element2);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1095 */       String str3 = transformXML(document);
/*      */       
/* 1097 */       boolean bool = false;
/*      */ 
/*      */       
/* 1100 */       String str4 = ABRServerProperties.getValue(this.abrName, "_" + this.rootEntityType + "_XSDNEEDED", "NO");
/*      */       
/* 1102 */       if ("YES".equals(str4.toUpperCase())) {
/*      */         
/* 1104 */         String str = ABRServerProperties.getValue(this.abrName, "_" + this.rootEntityType + "_XSDFILE", "NONE");
/*      */         
/* 1106 */         if ("NONE".equals(str)) {
/* 1107 */           addError("there is no xsdfile for " + this.rootEntityType + " defined in the propertyfile ");
/*      */         } else {
/*      */           
/* 1110 */           long l1 = System.currentTimeMillis();
/* 1111 */           Class<?> clazz = getClass();
/* 1112 */           StringBuffer stringBuffer = new StringBuffer();
/* 1113 */           bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/* 1114 */           if (stringBuffer.length() > 0) {
/* 1115 */             String str5 = stringBuffer.toString();
/* 1116 */             if (str5.indexOf("fail") != -1)
/* 1117 */               addError(str5); 
/* 1118 */             addOutput(str5);
/*      */           } 
/* 1120 */           long l2 = System.currentTimeMillis();
/* 1121 */           addDebug(3, "Time for validation: " + 
/*      */ 
/*      */               
/* 1124 */               Stopwatch.format(l2 - l1));
/* 1125 */           if (bool) {
/* 1126 */             addDebug("the xml for " + this.rootEntityType + " passed the validation");
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/* 1131 */         addOutput("the xml for " + this.rootEntityType + " doesn't need to be validated");
/*      */         
/* 1133 */         bool = true;
/*      */       } 
/*      */       
/* 1136 */       if (str3 != null && bool) {
/* 1137 */         notify(this.rootEntityType, str3, paramVector);
/*      */       }
/* 1139 */       document = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String transformXML(Document paramDocument) throws ParserConfigurationException, TransformerException {
/* 1150 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 1151 */     Transformer transformer = transformerFactory.newTransformer();
/* 1152 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */ 
/*      */     
/* 1155 */     transformer.setOutputProperty("indent", "no");
/* 1156 */     transformer.setOutputProperty("method", "xml");
/* 1157 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/* 1160 */     StringWriter stringWriter = new StringWriter();
/* 1161 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 1162 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 1163 */     transformer.transform(dOMSource, streamResult);
/* 1164 */     String str = XMLElem.removeCheat(stringWriter.toString());
/*      */ 
/*      */ 
/*      */     
/* 1168 */     transformer.setOutputProperty("indent", "yes");
/* 1169 */     stringWriter = new StringWriter();
/* 1170 */     streamResult = new StreamResult(stringWriter);
/* 1171 */     transformer.transform(dOMSource, streamResult);
/* 1172 */     addUserXML(XMLElem.removeCheat(stringWriter.toString()));
/*      */     
/* 1174 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void notify(String paramString1, String paramString2, Vector<String> paramVector) throws MissingResourceException {
/* 1182 */     MessageFormat messageFormat = null;
/*      */     
/* 1184 */     byte b1 = 0;
/* 1185 */     boolean bool = false;
/*      */ 
/*      */     
/* 1188 */     for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/*      */       
/* 1190 */       String str = paramVector.elementAt(b2);
/* 1191 */       addDebug("in notify looking at prop file " + str);
/*      */       try {
/* 1193 */         ResourceBundle resourceBundle = ResourceBundle.getBundle(str, 
/* 1194 */             getLocale(getProfile().getReadLanguage()
/* 1195 */               .getNLSID()));
/* 1196 */         Hashtable<String, String> hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/*      */         
/* 1198 */         boolean bool1 = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/* 1199 */         hashtable.put("MQCID", getMQCID());
/*      */         
/* 1201 */         hashtable.put("XMLTYPE", "ADS");
/*      */         
/* 1203 */         Hashtable hashtable1 = MQUsage.getUserProperties(resourceBundle, 
/* 1204 */             getMQCID());
/* 1205 */         if (bool1) {
/*      */           try {
/* 1207 */             addDebug("User infor " + hashtable1);
/* 1208 */             MQUsage.putToMQQueueWithRFH2("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString2, hashtable, hashtable1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1214 */             messageFormat = new MessageFormat(this.rsBundle.getString("SENT_SUCCESS"));
/* 1215 */             this.args[0] = str;
/* 1216 */             this.args[1] = paramString1;
/* 1217 */             addOutput(messageFormat.format(this.args));
/* 1218 */             b1++;
/* 1219 */             if (!bool)
/*      */             {
/*      */               
/* 1222 */               addXMLGenMsg("SUCCESS", paramString1);
/* 1223 */               addDebug("sent successfully to prop file " + str);
/*      */             }
/*      */           
/* 1226 */           } catch (MQException mQException) {
/*      */ 
/*      */ 
/*      */             
/* 1230 */             addXMLGenMsg("FAILED", paramString1);
/* 1231 */             bool = true;
/* 1232 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQ_ERROR"));
/* 1233 */             this.args[0] = str + " " + paramString1;
/* 1234 */             this.args[1] = "" + mQException.completionCode;
/* 1235 */             this.args[2] = "" + mQException.reasonCode;
/* 1236 */             addError(messageFormat.format(this.args));
/* 1237 */             mQException.printStackTrace(System.out);
/* 1238 */             addDebug("failed sending to prop file " + str);
/* 1239 */           } catch (IOException iOException) {
/*      */ 
/*      */             
/* 1242 */             addXMLGenMsg("FAILED", paramString1);
/* 1243 */             bool = true;
/*      */             
/* 1245 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQIO_ERROR"));
/* 1246 */             this.args[0] = str + " " + paramString1;
/* 1247 */             this.args[1] = iOException.toString();
/* 1248 */             addError(messageFormat.format(this.args));
/* 1249 */             iOException.printStackTrace(System.out);
/* 1250 */             addDebug("failed sending to prop file " + str);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1255 */           messageFormat = new MessageFormat(this.rsBundle.getString("NO_NOTIFY"));
/* 1256 */           this.args[0] = str;
/* 1257 */           addError(messageFormat.format(this.args));
/*      */           
/* 1259 */           addXMLGenMsg("NOT_SENT", paramString1);
/* 1260 */           addDebug("not sent to prop file " + str + " because Notify not true");
/*      */         }
/*      */       
/*      */       }
/* 1264 */       catch (MissingResourceException missingResourceException) {
/* 1265 */         addXMLGenMsg("FAILED", str + " " + paramString1);
/* 1266 */         bool = true;
/* 1267 */         addError("Prop file " + str + " " + paramString1 + " not found");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1272 */     if (b1 > 0 && b1 != paramVector.size()) {
/* 1273 */       addXMLGenMsg("ALL_NOT_SENT", paramString1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Locale getLocale(int paramInt) {
/* 1283 */     Locale locale = null;
/* 1284 */     switch (paramInt)
/*      */     { case 1:
/* 1286 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1310 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */   
/*      */   protected void addUserXML(String paramString) {
/* 1314 */     if (this.userxmlPw != null) {
/* 1315 */       this.userxmlPw.println(paramString);
/* 1316 */       this.userxmlPw.flush();
/*      */     } else {
/* 1318 */       this.userxmlSb.append(ADSABRSTATUS.convertToHTML(paramString) + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMQCID() {
/* 1326 */     return "DELETEXML_MSGS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1335 */     return "ADSDELXMLABR";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1344 */     return "$Revision: 1.4 $";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSDELXMLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */