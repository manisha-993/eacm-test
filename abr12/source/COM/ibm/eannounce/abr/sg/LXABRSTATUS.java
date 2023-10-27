/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFoundation;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*     */ import COM.ibm.opicmpdh.objects.Attribute;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Hashtable;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LXABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  79 */   private static final Hashtable ABR_TBL = new Hashtable<>(); static {
/*  80 */     ABR_TBL.put("LEADSADDSEO", "COM.ibm.eannounce.abr.sg.LXLEADSADDSEOABR");
/*  81 */     ABR_TBL.put("LEADSADDBUNDLE", "COM.ibm.eannounce.abr.sg.LXLEADSADDBUNDLEABR");
/*  82 */     ABR_TBL.put("LEADSADDVAR", "COM.ibm.eannounce.abr.psg.LXLEADSADDVARABR");
/*     */   }
/*  84 */   private StringBuffer rptSb = new StringBuffer();
/*  85 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  86 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*     */   public static final String TITLE = "Special Bid from LEADS";
/*  89 */   private ResourceBundle rsBundle = null;
/*  90 */   private String navName = "Special Bid from LEADS";
/*  91 */   private LXABRInterface lxAbr = null;
/*  92 */   private Vector vctReturnsEntityKeys = new Vector();
/*     */   
/*     */   protected static final String PROJCDNAM_ATTR = "PROJCDNAM";
/*     */   
/*  96 */   private String projcdname = null; protected String getPROJCDNAME() {
/*  97 */     return this.projcdname;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dereference() {
/* 103 */     super.dereference();
/* 104 */     this.rptSb = null;
/*     */     
/* 106 */     this.rsBundle = null;
/* 107 */     this.navName = null;
/* 108 */     if (this.lxAbr != null) {
/* 109 */       this.lxAbr.dereference();
/* 110 */       this.lxAbr = null;
/*     */     } 
/* 112 */     this.vctReturnsEntityKeys.clear();
/* 113 */     this.vctReturnsEntityKeys = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceBundle getBundle() {
/* 120 */     return this.rsBundle;
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
/*     */   public void execute_run() {
/* 144 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/* 146 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     String str3 = "";
/*     */     
/* 159 */     String[] arrayOfString = new String[10];
/* 160 */     Document document = null;
/* 161 */     EntityItem entityItem = null;
/* 162 */     Element element = null;
/*     */ 
/*     */     
/*     */     try {
/* 166 */       setReturnCode(0);
/*     */       
/* 168 */       start_ABRBuild();
/*     */ 
/*     */       
/* 171 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */       
/* 173 */       setControlBlock();
/*     */       
/* 175 */       entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */       
/* 177 */       addDebug("execute: " + entityItem.getKey());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       String str5 = "LXMSG";
/*     */       
/* 184 */       String str6 = PokUtils.getAttributeValue(entityItem, str5, "", "", false);
/* 185 */       addDebug(str5 + " : " + str6);
/*     */ 
/*     */ 
/*     */       
/* 189 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*     */       
/* 191 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 192 */       StringReader stringReader = new StringReader(str6);
/* 193 */       InputSource inputSource = new InputSource(stringReader);
/*     */       
/* 195 */       document = documentBuilder.parse(inputSource);
/* 196 */       element = document.getDocumentElement();
/* 197 */       String str7 = element.getNodeName();
/*     */       
/* 199 */       findPrjcdnam(entityItem, element);
/*     */ 
/*     */ 
/*     */       
/* 203 */       String str8 = (String)ABR_TBL.get(str7);
/* 204 */       addDebug("creating instance of LXABR  = '" + str8 + "'");
/* 205 */       if (str8 != null) {
/*     */         
/* 207 */         listDomains();
/*     */         
/* 209 */         this.lxAbr = (LXABRInterface)Class.forName(str8).newInstance();
/* 210 */         str3 = getShortClassName(this.lxAbr.getClass()) + " " + this.lxAbr.getVersion();
/*     */         
/* 212 */         this.lxAbr.validateData(this, element);
/*     */ 
/*     */         
/* 215 */         if (getReturnCode() == 0) {
/* 216 */           this.lxAbr.execute();
/*     */         }
/*     */       } else {
/* 219 */         addError(getShortClassName(getClass()) + " does not support " + str7);
/*     */       }
/*     */     
/* 222 */     } catch (Throwable throwable) {
/*     */       try {
/* 224 */         if (document != null)
/*     */         {
/* 226 */           TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 227 */           Transformer transformer = transformerFactory.newTransformer();
/* 228 */           transformer.setOutputProperty("omit-xml-declaration", "yes");
/* 229 */           transformer.setOutputProperty("indent", "yes");
/* 230 */           transformer.setOutputProperty("method", "xml");
/* 231 */           transformer.setOutputProperty("encoding", "UTF-8");
/*     */ 
/*     */           
/* 234 */           StringWriter stringWriter1 = new StringWriter();
/* 235 */           StreamResult streamResult = new StreamResult(stringWriter1);
/* 236 */           DOMSource dOMSource = new DOMSource(document);
/* 237 */           transformer.transform(dOMSource, streamResult);
/* 238 */           this.rptSb.append("<pre>Error executing: <br />" + 
/* 239 */               PokUtils.convertToHTML(stringWriter1.toString()) + "</pre>");
/*     */         }
/*     */       
/* 242 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 245 */       StringWriter stringWriter = new StringWriter();
/* 246 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 247 */       String str6 = "<pre>{0}</pre>";
/* 248 */       MessageFormat messageFormat1 = new MessageFormat(str5);
/* 249 */       setReturnCode(-3);
/* 250 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 252 */       arrayOfString[0] = throwable.getMessage();
/* 253 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 254 */       messageFormat1 = new MessageFormat(str6);
/* 255 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 256 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 257 */       logError("Exception: " + throwable.getMessage());
/* 258 */       logError(stringWriter.getBuffer().toString());
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/* 263 */         if (entityItem != null && element != null) {
/* 264 */           String str = getNodeValue(element, "FACTSHEETNUM", (String)null);
/* 265 */           if (str != null) {
/* 266 */             String str5 = PokUtils.getAttributeValue(entityItem, "LXFACTSHEETNO", "", "", false);
/* 267 */             if (!str5.equals(str)) {
/* 268 */               setTextValue(this.m_prof, "LXFACTSHEETNO", str, entityItem);
/*     */             } else {
/* 270 */               addDebug("LXFACTSHEETNO already set to " + str + " for " + entityItem.getKey());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 275 */         updatePDH(this.m_prof);
/* 276 */       } catch (Exception exception) {
/* 277 */         exception.printStackTrace();
/*     */       } 
/* 279 */       setDGTitle(this.navName);
/* 280 */       setDGRptName(getShortClassName(getClass()));
/* 281 */       setDGRptClass(getABRCode());
/*     */       
/* 283 */       if (!isReadOnly()) {
/* 284 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 289 */     MessageFormat messageFormat = new MessageFormat(str1);
/* 290 */     if (this.lxAbr != null) {
/* 291 */       arrayOfString[0] = getShortClassName(this.lxAbr.getClass());
/* 292 */       this.navName = this.lxAbr.getTitle();
/* 293 */       String str = this.lxAbr.getHeader();
/* 294 */       if (str != null && str.length() > 0) {
/* 295 */         this.rptSb.insert(0, "<p>" + str + "</p>\n");
/*     */       }
/*     */     } else {
/* 298 */       arrayOfString[0] = getShortClassName(getClass());
/*     */     } 
/* 300 */     arrayOfString[1] = this.navName;
/* 301 */     String str4 = messageFormat.format(arrayOfString);
/*     */     
/* 303 */     messageFormat = new MessageFormat(str2);
/* 304 */     arrayOfString[0] = this.m_prof.getOPName();
/* 305 */     arrayOfString[1] = this.m_prof.getRoleDescription();
/* 306 */     arrayOfString[2] = this.m_prof.getWGName();
/* 307 */     arrayOfString[3] = getNow();
/* 308 */     if (this.lxAbr != null) {
/* 309 */       arrayOfString[4] = this.lxAbr.getDescription();
/*     */     } else {
/* 311 */       arrayOfString[4] = "";
/*     */     } 
/* 313 */     arrayOfString[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/* 314 */     arrayOfString[6] = str3 + " " + getABRVersion();
/*     */     
/* 316 */     this.rptSb.insert(0, str4 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */     
/* 318 */     println(EACustom.getDocTypeHtml());
/* 319 */     println(this.rptSb.toString());
/* 320 */     printDGSubmitString();
/*     */     
/* 322 */     println(EACustom.getTOUDiv());
/* 323 */     buildReportFooter();
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
/*     */   private void findPrjcdnam(EntityItem paramEntityItem, Element paramElement) throws SQLException, MiddlewareException {
/* 336 */     this.projcdname = PokUtils.getAttributeFlagValue(paramEntityItem, "PROJCDNAM");
/* 337 */     addDebug("findPrjcdnam from " + paramEntityItem.getKey() + " " + "PROJCDNAM" + " : " + this.projcdname);
/*     */     
/* 339 */     if (this.projcdname == null) {
/* 340 */       String str = getNodeValue(paramElement, "PROJCDNAM", (String)null);
/*     */       
/* 342 */       addDebug("findPrjcdnam from XML PROJCDNAM desc : " + str);
/* 343 */       if (str != null) {
/* 344 */         PDGUtility pDGUtility = new PDGUtility();
/*     */         
/* 346 */         String[] arrayOfString = pDGUtility.getFlagCodeForExactDesc(this.m_db, this.m_prof, "PROJCDNAM", str);
/* 347 */         if (arrayOfString != null && arrayOfString.length > 0) {
/* 348 */           this.projcdname = arrayOfString[0];
/* 349 */           addDebug("findPrjcdnam PROJCDNAM desc : " + str + " flagcode " + this.projcdname);
/*     */           
/* 351 */           setFlagValue(this.m_prof, "PROJCDNAM", this.projcdname, paramEntityItem);
/*     */         } else {
/* 353 */           addDebug("findPrjcdnam NO match found for PROJCDNAM desc : " + str);
/*     */         } 
/* 355 */         pDGUtility.dereference();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void listDomains() {
/*     */     try {
/* 364 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, "WG", "Edit");
/* 365 */       EntityItem entityItem = new EntityItem((EANFoundation)entityGroup, this.m_prof, "WG", this.m_prof.getWGID());
/* 366 */       ResultSet resultSet = null;
/* 367 */       StringBuffer stringBuffer = new StringBuffer();
/*     */       try {
/* 369 */         ReturnStatus returnStatus = new ReturnStatus(-1);
/* 370 */         ReturnDataResultSet returnDataResultSet = null;
/*     */         
/* 372 */         resultSet = this.m_db.callGBL7065(returnStatus, this.m_prof.getEnterprise(), this.m_prof.getWGID(), this.m_prof
/* 373 */             .getValOn(), this.m_prof.getEffOn());
/* 374 */         returnDataResultSet = new ReturnDataResultSet(resultSet);
/* 375 */         for (byte b = 0; b < returnDataResultSet.size(); b++) {
/*     */           
/* 377 */           String str = returnDataResultSet.getColumn(b, 0);
/* 378 */           if ("PDHDOMAIN".equals(str)) {
/* 379 */             stringBuffer.append(returnDataResultSet.getColumn(b, 1) + "|");
/*     */           }
/*     */         } 
/*     */       } finally {
/*     */         
/* 384 */         if (resultSet != null) {
/* 385 */           resultSet.close();
/* 386 */           resultSet = null;
/*     */         } 
/* 388 */         this.m_db.commit();
/* 389 */         this.m_db.freeStatement();
/* 390 */         this.m_db.isPending();
/*     */       } 
/*     */       
/* 393 */       addDebug("Running with " + entityItem.getKey() + " for domains " + stringBuffer.toString());
/* 394 */     } catch (Exception exception) {
/* 395 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNodeValue(Element paramElement, String paramString) {
/* 405 */     String str = null;
/*     */     
/* 407 */     NodeList nodeList = paramElement.getElementsByTagName(paramString);
/*     */     
/* 409 */     if (nodeList == null || nodeList.getLength() != 1) {
/* 410 */       addError("ERROR_INVALIDXML", paramString);
/*     */     } else {
/* 412 */       Node node = nodeList.item(0);
/* 413 */       if (node.hasChildNodes()) {
/* 414 */         str = node.getFirstChild().getNodeValue();
/*     */       } else {
/* 416 */         addDebug("getNodeValue: " + paramString + " has no child nodes");
/*     */       } 
/* 418 */       addDebug("getNodeValue: " + paramString + " " + str);
/* 419 */       if (str == null)
/*     */       {
/* 421 */         addError("ERROR_MISSINGXML", paramString);
/*     */       }
/*     */     } 
/*     */     
/* 425 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyChildNodes(NodeList paramNodeList, String paramString1, String paramString2, int paramInt) {
/* 435 */     for (byte b = 0; b < paramNodeList.getLength(); b++) {
/* 436 */       NodeList nodeList = paramNodeList.item(b).getChildNodes();
/*     */       
/* 438 */       if (nodeList.getLength() < paramInt) {
/* 439 */         addError("ERROR_INVALIDXMLSTRUCTURE", new Object[] { paramString1, "" + paramInt, paramString2 });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNodeValue(Element paramElement, String paramString1, String paramString2) {
/* 451 */     String str = paramString2;
/*     */     
/* 453 */     NodeList nodeList = paramElement.getElementsByTagName(paramString1);
/* 454 */     if (nodeList != null && nodeList.getLength() == 1) {
/*     */       
/* 456 */       Node node = nodeList.item(0);
/* 457 */       if (node.hasChildNodes()) {
/* 458 */         str = node.getFirstChild().getNodeValue();
/*     */       } else {
/* 460 */         addDebug("getNodeValue: " + paramString1 + " has no child nodes");
/*     */       } 
/* 462 */       addDebug("getNodeValue: " + paramString1 + " " + str);
/* 463 */       if (str == null) {
/* 464 */         str = paramString2;
/*     */       }
/*     */     } 
/*     */     
/* 468 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOutput(String paramString) {
/* 474 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 484 */     String str = getBundle().getString(paramString);
/* 485 */     if (paramArrayOfObject != null) {
/* 486 */       MessageFormat messageFormat = new MessageFormat(str);
/* 487 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 490 */     addOutput(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResourceMsg(String paramString, Object[] paramArrayOfObject) {
/* 501 */     String str = getBundle().getString(paramString);
/* 502 */     if (paramArrayOfObject != null) {
/* 503 */       MessageFormat messageFormat = new MessageFormat(str);
/* 504 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 507 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDebug(String paramString) {
/* 513 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addError(String paramString) {
/* 520 */     addOutput(paramString);
/* 521 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addError(String paramString, Object paramObject) {
/* 531 */     addError(paramString, new Object[] { paramObject });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addError(String paramString, Object[] paramArrayOfObject) {
/* 540 */     setReturnCode(-1);
/* 541 */     String str = getBundle().getString(paramString);
/*     */     
/* 543 */     if (paramArrayOfObject != null) {
/* 544 */       MessageFormat messageFormat = new MessageFormat(str);
/* 545 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/* 547 */     addOutput(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 557 */     return "1.10";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 566 */     return "LXABRSTATUS";
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
/*     */   private void setTextValue(Profile paramProfile, String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 580 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 581 */     addDebug("setTextValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*     */ 
/*     */     
/* 584 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 585 */     if (eANMetaAttribute == null) {
/* 586 */       addDebug("setTextValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 587 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 588 */           .getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/* 592 */     if (paramString2 != null) {
/* 593 */       if (this.m_cbOn == null) {
/* 594 */         setControlBlock();
/*     */       }
/* 596 */       ControlBlock controlBlock = this.m_cbOn;
/* 597 */       if (paramString2.length() == 0) {
/* 598 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 599 */         String str = eANAttribute.getEffFrom();
/* 600 */         controlBlock = new ControlBlock(str, str, str, str, paramProfile.getOPWGID());
/* 601 */         paramString2 = eANAttribute.toString();
/* 602 */         addDebug("setTextValue deactivating " + paramString1);
/*     */       } 
/* 604 */       Vector<Text> vector = null;
/*     */       
/* 606 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 607 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 608 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 609 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 610 */           vector = returnEntityKey.m_vctAttributes;
/*     */           break;
/*     */         } 
/*     */       } 
/* 614 */       if (vector == null) {
/*     */         
/* 616 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 617 */         vector = new Vector();
/* 618 */         returnEntityKey.m_vctAttributes = vector;
/* 619 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*     */       } 
/*     */       
/* 622 */       Text text = new Text(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, controlBlock);
/* 623 */       vector.addElement(text);
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
/*     */   private void setFlagValue(Profile paramProfile, String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 636 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 637 */     addDebug("setFlagValue entered " + paramEntityItem.getKey() + " for " + paramString1 + " set to: " + paramString2);
/*     */ 
/*     */     
/* 640 */     if (paramString2 != null && paramString2.trim().length() == 0) {
/* 641 */       addDebug("setFlagValue: " + paramString1 + " was blank for " + paramEntityItem.getKey() + ", it will be ignored");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 646 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 647 */     if (eANMetaAttribute == null) {
/* 648 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 649 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 650 */           .getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/* 654 */     if (paramString2 != null) {
/*     */ 
/*     */       
/* 657 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/* 658 */       if (paramString2.equals(str)) {
/* 659 */         addDebug("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/* 660 */         logMessage("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*     */         
/*     */         return;
/*     */       } 
/* 664 */       if (this.m_cbOn == null) {
/* 665 */         setControlBlock();
/*     */       }
/* 667 */       Vector<Attribute> vector = null;
/*     */       
/* 669 */       for (byte b1 = 0; b1 < this.vctReturnsEntityKeys.size(); b1++) {
/* 670 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b1);
/* 671 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 672 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 673 */           vector = returnEntityKey.m_vctAttributes;
/*     */           break;
/*     */         } 
/*     */       } 
/* 677 */       if (vector == null) {
/* 678 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 679 */         vector = new Vector();
/* 680 */         returnEntityKey.m_vctAttributes = vector;
/* 681 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*     */       } 
/*     */       
/* 684 */       SingleFlag singleFlag = new SingleFlag(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */       
/* 688 */       for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 689 */         Attribute attribute = vector.elementAt(b2);
/* 690 */         if (attribute.getAttributeCode().equals(paramString1)) {
/* 691 */           singleFlag = null;
/*     */           break;
/*     */         } 
/*     */       } 
/* 695 */       if (singleFlag != null) {
/* 696 */         vector.addElement(singleFlag);
/*     */       } else {
/* 698 */         addDebug("setFlagValue:  " + paramEntityItem.getKey() + " " + paramString1 + " was already added for updates ");
/*     */       } 
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
/*     */   private void updatePDH(Profile paramProfile) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 715 */     logMessage(getDescription() + " updating PDH");
/* 716 */     addDebug("updatePDH entered for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys);
/* 717 */     if (this.vctReturnsEntityKeys.size() > 0)
/*     */       
/*     */       try {
/*     */         
/* 721 */         this.m_db.update(paramProfile, this.vctReturnsEntityKeys, false, false);
/*     */         
/* 723 */         for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 724 */           ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*     */           
/* 726 */           for (byte b1 = 0; b1 < returnEntityKey.m_vctAttributes.size(); b1++) {
/* 727 */             Attribute attribute = returnEntityKey.m_vctAttributes.elementAt(b1);
/* 728 */             if (attribute instanceof Text) {
/* 729 */               EntityGroup entityGroup = null;
/* 730 */               if (returnEntityKey.getEntityType().equals(getEntityType())) {
/* 731 */                 entityGroup = this.m_elist.getParentEntityGroup();
/*     */               } else {
/*     */                 
/* 734 */                 entityGroup = this.m_elist.getEntityGroup(returnEntityKey.getEntityType());
/*     */               } 
/*     */               
/* 737 */               EntityItem entityItem = entityGroup.getEntityItem(returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/*     */               
/* 739 */               entityItem.commit(this.m_db, null);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } finally {
/*     */         
/* 745 */         this.vctReturnsEntityKeys.clear();
/* 746 */         this.m_db.commit();
/* 747 */         this.m_db.freeStatement();
/* 748 */         this.m_db.isPending("finally after updatePDH");
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\LXABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */