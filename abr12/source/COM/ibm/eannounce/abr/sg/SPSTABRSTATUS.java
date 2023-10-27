/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.objects.Attribute;
/*     */ import COM.ibm.opicmpdh.objects.Blob;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.rmi.RemoteException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSTABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  94 */   private static final Hashtable ABR_TBL = new Hashtable<>(); static {
/*  95 */     ABR_TBL.put("SPSTAVAILMODEL", "COM.ibm.eannounce.abr.sg.SPSTAVAILMODELABR");
/*  96 */     ABR_TBL.put("SPSTAVAILBUNDLE", "COM.ibm.eannounce.abr.sg.SPSTAVAILBUNDLEABR");
/*  97 */     ABR_TBL.put("SPSTWDAVAIL", "COM.ibm.eannounce.abr.sg.SPSTWDAVAILABR");
/*     */   }
/*  99 */   private StringBuffer rptSb = new StringBuffer();
/* 100 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/* 101 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*     */   public static final String TITLE = "Service Pacs from SPST";
/* 104 */   private ResourceBundle rsBundle = null;
/* 105 */   private String navName = "Service Pacs from SPST";
/* 106 */   private SPSTABR spstAbr = null;
/* 107 */   private Vector vctReturnsEntityKeys = new Vector();
/* 108 */   private Hashtable updatedTbl = new Hashtable<>();
/*     */   
/*     */   protected static final String PROJCDNAM_ATTR = "PROJCDNAM";
/*     */   
/* 112 */   private String projcdname = null; protected String getPROJCDNAME() {
/* 113 */     return this.projcdname;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dereference() {
/* 119 */     super.dereference();
/* 120 */     this.rptSb = null;
/*     */     
/* 122 */     this.rsBundle = null;
/* 123 */     this.navName = null;
/* 124 */     if (this.spstAbr != null) {
/* 125 */       this.spstAbr.dereference();
/* 126 */       this.spstAbr = null;
/*     */     } 
/* 128 */     this.vctReturnsEntityKeys.clear();
/* 129 */     this.vctReturnsEntityKeys = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceBundle getBundle() {
/* 136 */     return this.rsBundle;
/*     */   }
/*     */   
/*     */   private String getBlobAttr(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, Exception {
/* 140 */     String str1 = paramEntityItem.getKey();
/* 141 */     Blob blob = getDatabase().getBlob(getProfile(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString);
/* 142 */     String str2 = "";
/* 143 */     byte[] arrayOfByte = blob.getBAAttributeValue();
/* 144 */     if (arrayOfByte == null || arrayOfByte.length == 0) {
/* 145 */       addDebug("getBlobAttr ** No  Object Found for " + str1 + " **");
/* 146 */       return null;
/*     */     } 
/*     */     
/* 149 */     ByteArrayInputStream byteArrayInputStream = null;
/* 150 */     ObjectInputStream objectInputStream = null;
/* 151 */     BufferedInputStream bufferedInputStream = null;
/*     */     try {
/* 153 */       byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/* 154 */       bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
/* 155 */       objectInputStream = new ObjectInputStream(bufferedInputStream);
/* 156 */       str2 = (String)objectInputStream.readObject();
/* 157 */       return str2;
/*     */     }
/* 159 */     catch (Exception exception) {
/* 160 */       addError("getBlobAttr " + str1 + " ERROR " + exception.getMessage());
/* 161 */       exception.printStackTrace();
/*     */     } finally {
/*     */       
/* 164 */       if (objectInputStream != null) {
/*     */         try {
/* 166 */           objectInputStream.close();
/* 167 */         } catch (IOException iOException) {
/* 168 */           addDebug("getBlobAttr: " + str1 + " ERROR failure closing ObjectInputStream " + iOException);
/*     */         } 
/* 170 */         objectInputStream = null;
/*     */       } 
/* 172 */       if (bufferedInputStream != null) {
/*     */         try {
/* 174 */           bufferedInputStream.close();
/* 175 */         } catch (IOException iOException) {
/* 176 */           addDebug("getBlobAttr: " + str1 + " ERROR failure closing BufferedInputStream " + iOException);
/*     */         } 
/* 178 */         bufferedInputStream = null;
/*     */       } 
/* 180 */       if (byteArrayInputStream != null) {
/*     */         try {
/* 182 */           byteArrayInputStream.close();
/* 183 */         } catch (IOException iOException) {
/* 184 */           addDebug("getBlobAttr: " + str1 + " ERROR failure closing ByteArrayInputStream " + iOException);
/*     */         } 
/* 186 */         byteArrayInputStream = null;
/*     */       } 
/* 188 */       arrayOfByte = null;
/*     */     } 
/* 190 */     return str2;
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
/* 214 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/* 216 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     String str3 = "";
/*     */     
/* 229 */     String[] arrayOfString = new String[10];
/* 230 */     Document document = null;
/* 231 */     EntityItem entityItem = null;
/* 232 */     Element element = null;
/*     */ 
/*     */     
/*     */     try {
/* 236 */       setReturnCode(0);
/*     */       
/* 238 */       start_ABRBuild();
/*     */ 
/*     */       
/* 241 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */       
/* 243 */       setControlBlock();
/*     */       
/* 245 */       entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */       
/* 247 */       addDebug("execute: " + entityItem.getKey());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 252 */       String str5 = "SPSTMSG";
/*     */ 
/*     */       
/* 255 */       String str6 = getBlobAttr(entityItem, str5);
/* 256 */       addDebug(str5 + " : " + str6);
/*     */ 
/*     */ 
/*     */       
/* 260 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*     */       
/* 262 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 263 */       StringReader stringReader = new StringReader(str6);
/* 264 */       InputSource inputSource = new InputSource(stringReader);
/*     */       
/* 266 */       document = documentBuilder.parse(inputSource);
/* 267 */       element = document.getDocumentElement();
/* 268 */       String str7 = element.getNodeName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       String str8 = (String)ABR_TBL.get(str7);
/* 275 */       addDebug("creating instance of SPSTABR  = '" + str8 + "'");
/* 276 */       if (str8 != null) {
/*     */ 
/*     */ 
/*     */         
/* 280 */         this.spstAbr = (SPSTABR)Class.forName(str8).newInstance();
/* 281 */         str3 = getShortClassName(this.spstAbr.getClass()) + " " + this.spstAbr.getVersion();
/*     */         
/* 283 */         this.spstAbr.validateData(this, element);
/*     */ 
/*     */         
/* 286 */         if (getReturnCode() == 0) {
/* 287 */           this.spstAbr.execute();
/*     */         }
/*     */       } else {
/* 290 */         addError(getShortClassName(getClass()) + " does not support " + str7);
/*     */       }
/*     */     
/* 293 */     } catch (Throwable throwable) {
/*     */       try {
/* 295 */         if (document != null)
/*     */         {
/* 297 */           TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 298 */           Transformer transformer = transformerFactory.newTransformer();
/* 299 */           transformer.setOutputProperty("omit-xml-declaration", "yes");
/* 300 */           transformer.setOutputProperty("indent", "yes");
/* 301 */           transformer.setOutputProperty("method", "xml");
/* 302 */           transformer.setOutputProperty("encoding", "UTF-8");
/*     */ 
/*     */           
/* 305 */           StringWriter stringWriter1 = new StringWriter();
/* 306 */           StreamResult streamResult = new StreamResult(stringWriter1);
/* 307 */           DOMSource dOMSource = new DOMSource(document);
/* 308 */           transformer.transform(dOMSource, streamResult);
/* 309 */           this.rptSb.append("<pre>Error executing: <br />" + 
/* 310 */               PokUtils.convertToHTML(stringWriter1.toString()) + "</pre>");
/*     */         }
/*     */       
/* 313 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 316 */       StringWriter stringWriter = new StringWriter();
/* 317 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 318 */       String str6 = "<pre>{0}</pre>";
/* 319 */       MessageFormat messageFormat1 = new MessageFormat(str5);
/* 320 */       setReturnCode(-3);
/* 321 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 323 */       arrayOfString[0] = throwable.getMessage();
/* 324 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 325 */       messageFormat1 = new MessageFormat(str6);
/* 326 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 327 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 328 */       logError("Exception: " + throwable.getMessage());
/* 329 */       logError(stringWriter.getBuffer().toString());
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/* 334 */         if (entityItem != null && element != null) {
/* 335 */           String str = getNodeValue(element, "SPSTSHEETNUM", (String)null);
/* 336 */           if (str != null) {
/* 337 */             String str5 = PokUtils.getAttributeValue(entityItem, "SPSTSHEETNUM", "", "", false);
/* 338 */             if (!str5.equals(str)) {
/* 339 */               setTextValue(this.m_prof, "SPSTSHEETNUM", str, entityItem);
/*     */             } else {
/* 341 */               addDebug("SPSTSHEETNUM already set to " + str + " for " + entityItem.getKey());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 346 */         updatePDH(this.m_prof);
/* 347 */       } catch (Exception exception) {
/* 348 */         exception.printStackTrace();
/*     */       } 
/* 350 */       setDGTitle(this.navName);
/* 351 */       setDGRptName(getShortClassName(getClass()));
/* 352 */       setDGRptClass(getABRCode());
/*     */       
/* 354 */       if (!isReadOnly()) {
/* 355 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 360 */     MessageFormat messageFormat = new MessageFormat(str1);
/* 361 */     if (this.spstAbr != null) {
/* 362 */       arrayOfString[0] = getShortClassName(this.spstAbr.getClass());
/* 363 */       this.navName = this.spstAbr.getTitle();
/* 364 */       String str = this.spstAbr.getHeader();
/* 365 */       if (str != null && str.length() > 0) {
/* 366 */         this.rptSb.insert(0, "<p>" + str + "</p>\n");
/*     */       }
/*     */     } else {
/* 369 */       arrayOfString[0] = getShortClassName(getClass());
/*     */     } 
/* 371 */     arrayOfString[1] = this.navName;
/* 372 */     String str4 = messageFormat.format(arrayOfString);
/*     */     
/* 374 */     messageFormat = new MessageFormat(str2);
/* 375 */     arrayOfString[0] = this.m_prof.getOPName();
/* 376 */     arrayOfString[1] = this.m_prof.getRoleDescription();
/* 377 */     arrayOfString[2] = this.m_prof.getWGName();
/* 378 */     arrayOfString[3] = getNow();
/* 379 */     if (this.spstAbr != null) {
/* 380 */       arrayOfString[4] = this.spstAbr.getDescription();
/*     */     } else {
/* 382 */       arrayOfString[4] = "";
/*     */     } 
/* 384 */     arrayOfString[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/* 385 */     arrayOfString[6] = str3 + " " + getABRVersion();
/*     */     
/* 387 */     this.rptSb.insert(0, str4 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */     
/* 389 */     println(EACustom.getDocTypeHtml());
/* 390 */     println(this.rptSb.toString());
/* 391 */     printDGSubmitString();
/*     */     
/* 393 */     println(EACustom.getTOUDiv());
/* 394 */     buildReportFooter();
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
/*     */   public String getNodeValue(Element paramElement, String paramString, boolean paramBoolean) {
/* 477 */     String str = null;
/* 478 */     Node node = null;
/* 479 */     byte b = 0;
/* 480 */     if (!paramString.equals(paramElement.getTagName())) {
/* 481 */       NodeList nodeList = paramElement.getElementsByTagName(paramString);
/* 482 */       if (nodeList != null) {
/* 483 */         for (byte b1 = 0; b1 < nodeList.getLength(); b1++) {
/* 484 */           Node node1 = nodeList.item(b1);
/* 485 */           if (node1.getParentNode() == paramElement) {
/* 486 */             node = node1;
/* 487 */             b++;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 492 */       if (nodeList == null || b != 1) {
/* 493 */         addError("ERROR_INVALIDXML", paramString);
/*     */       }
/*     */     } else {
/* 496 */       node = paramElement;
/*     */     } 
/* 498 */     if (node != null && node.hasChildNodes()) {
/* 499 */       str = node.getFirstChild().getNodeValue();
/*     */     } else {
/* 501 */       addDebug("getNodeValue: " + paramString + " has no child nodes");
/*     */     } 
/*     */     
/* 504 */     addDebug("getNodeValue: " + paramString + " " + str);
/* 505 */     if (str == null) {
/* 506 */       if (paramBoolean)
/*     */       {
/* 508 */         addError("ERROR_MISSINGXML", paramString);
/*     */       }
/* 510 */       addDebug("getNodeValue: " + paramString + " is null");
/* 511 */       str = "";
/*     */     } 
/* 513 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyChildNodes(Element paramElement, String paramString1, String paramString2, int paramInt) {
/* 523 */     NodeList nodeList = paramElement.getElementsByTagName(paramString1);
/* 524 */     byte b = 0;
/* 525 */     if (nodeList != null) {
/* 526 */       for (byte b1 = 0; b1 < nodeList.getLength(); b1++) {
/* 527 */         Node node = nodeList.item(b1);
/* 528 */         if (node.getParentNode() == paramElement) {
/* 529 */           NodeList nodeList1 = ((Element)node).getElementsByTagName(paramString2);
/* 530 */           addDebug("childlist length = " + nodeList1.getLength());
/*     */           
/* 532 */           if (nodeList1.getLength() < paramInt) {
/* 533 */             addError("ERROR_INVALIDXMLSTRUCTURE", new Object[] { paramString1, "" + paramInt, paramString2 });
/*     */           } else {
/* 535 */             addDebug("item.getchildnodes.item[0] = " + node.getChildNodes().item(0).getNodeName());
/*     */           } 
/* 537 */           b++;
/*     */         } else {
/* 539 */           addDebug("this tag is not correct one because of the parent is not elem: " + node.getParentNode().getNodeName() + " : " + node.getNodeName());
/*     */         } 
/*     */       } 
/*     */     }
/* 543 */     if (nodeList == null || b != 1) {
/* 544 */       addError("ERROR_INVALIDXML", paramString1);
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
/* 555 */     String str = paramString2;
/*     */     
/* 557 */     NodeList nodeList = paramElement.getElementsByTagName(paramString1);
/* 558 */     if (nodeList != null && nodeList.getLength() == 1) {
/*     */       
/* 560 */       Node node = nodeList.item(0);
/* 561 */       if (node.hasChildNodes()) {
/* 562 */         str = node.getFirstChild().getNodeValue();
/*     */       } else {
/* 564 */         addDebug("getNodeValue: " + paramString1 + " has no child nodes");
/*     */       } 
/* 566 */       addDebug("getNodeValue: " + paramString1 + " " + str);
/* 567 */       if (str == null) {
/* 568 */         str = paramString2;
/*     */       }
/*     */     } 
/*     */     
/* 572 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOutput(String paramString) {
/* 578 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 588 */     String str = getBundle().getString(paramString);
/* 589 */     if (paramArrayOfObject != null) {
/* 590 */       MessageFormat messageFormat = new MessageFormat(str);
/* 591 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 594 */     addOutput(str);
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
/* 605 */     String str = getBundle().getString(paramString);
/* 606 */     if (paramArrayOfObject != null) {
/* 607 */       MessageFormat messageFormat = new MessageFormat(str);
/* 608 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 611 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDebug(String paramString) {
/* 617 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addError(String paramString) {
/* 624 */     addOutput(paramString);
/* 625 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addError(String paramString, Object paramObject) {
/* 635 */     addError(paramString, new Object[] { paramObject });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addError(String paramString, Object[] paramArrayOfObject) {
/* 644 */     setReturnCode(-1);
/* 645 */     String str = getBundle().getString(paramString);
/*     */     
/* 647 */     if (paramArrayOfObject != null) {
/* 648 */       MessageFormat messageFormat = new MessageFormat(str);
/* 649 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/* 651 */     addOutput(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 661 */     return "1.10";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 670 */     return "LXABRSTATUS";
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
/*     */   public void setTextValue(Profile paramProfile, String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 684 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 685 */     addDebug("setTextValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*     */ 
/*     */     
/* 688 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 689 */     if (eANMetaAttribute == null) {
/* 690 */       addDebug("setTextValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 691 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 692 */           .getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/* 696 */     if (paramString2 != null) {
/* 697 */       if (this.m_cbOn == null) {
/* 698 */         setControlBlock();
/*     */       }
/* 700 */       ControlBlock controlBlock = this.m_cbOn;
/* 701 */       if (paramString2.length() == 0) {
/* 702 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 703 */         String str = eANAttribute.getEffFrom();
/* 704 */         controlBlock = new ControlBlock(str, str, str, str, paramProfile.getOPWGID());
/* 705 */         paramString2 = eANAttribute.toString();
/* 706 */         addDebug("setTextValue deactivating " + paramString1);
/*     */       } 
/* 708 */       Vector<Text> vector = null;
/*     */       
/* 710 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 711 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 712 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 713 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 714 */           vector = returnEntityKey.m_vctAttributes;
/*     */           break;
/*     */         } 
/*     */       } 
/* 718 */       if (vector == null) {
/*     */         
/* 720 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 721 */         vector = new Vector();
/* 722 */         returnEntityKey.m_vctAttributes = vector;
/* 723 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/* 724 */         this.updatedTbl.put(returnEntityKey.getEntityType() + returnEntityKey.getEntityID(), paramEntityItem);
/*     */       } 
/*     */       
/* 727 */       Text text = new Text(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, controlBlock);
/* 728 */       vector.addElement(text);
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
/*     */   public void setFlagValue(Profile paramProfile, String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 741 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 742 */     addDebug("setFlagValue entered " + paramEntityItem.getKey() + " for " + paramString1 + " set to: " + paramString2);
/*     */ 
/*     */     
/* 745 */     if (paramString2 != null && paramString2.trim().length() == 0) {
/* 746 */       addDebug("setFlagValue: " + paramString1 + " was blank for " + paramEntityItem.getKey() + ", it will be ignored");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 751 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 752 */     if (eANMetaAttribute == null) {
/* 753 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 754 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 755 */           .getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/* 759 */     if (paramString2 != null) {
/*     */ 
/*     */       
/* 762 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/* 763 */       if (paramString2.equals(str)) {
/* 764 */         addDebug("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/* 765 */         logMessage("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*     */         
/*     */         return;
/*     */       } 
/* 769 */       if (this.m_cbOn == null) {
/* 770 */         setControlBlock();
/*     */       }
/* 772 */       Vector<Attribute> vector = null;
/*     */       
/* 774 */       for (byte b1 = 0; b1 < this.vctReturnsEntityKeys.size(); b1++) {
/* 775 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b1);
/* 776 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 777 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 778 */           vector = returnEntityKey.m_vctAttributes;
/*     */           break;
/*     */         } 
/*     */       } 
/* 782 */       if (vector == null) {
/* 783 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 784 */         vector = new Vector();
/* 785 */         returnEntityKey.m_vctAttributes = vector;
/* 786 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/* 787 */         this.updatedTbl.put(returnEntityKey.getEntityType() + returnEntityKey.getEntityID(), paramEntityItem);
/*     */       } 
/*     */       
/* 790 */       SingleFlag singleFlag = new SingleFlag(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */       
/* 794 */       for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 795 */         Attribute attribute = vector.elementAt(b2);
/* 796 */         if (attribute.getAttributeCode().equals(paramString1)) {
/* 797 */           singleFlag = null;
/*     */           break;
/*     */         } 
/*     */       } 
/* 801 */       if (singleFlag != null) {
/* 802 */         vector.addElement(singleFlag);
/*     */       } else {
/* 804 */         addDebug("setFlagValue:  " + paramEntityItem.getKey() + " " + paramString1 + " was already added for updates ");
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
/*     */   public void updatePDH(Profile paramProfile) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 821 */     logMessage(getDescription() + " updating PDH");
/* 822 */     addDebug("updatePDH entered for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys);
/* 823 */     if (this.vctReturnsEntityKeys.size() > 0)
/*     */       
/*     */       try {
/*     */         
/* 827 */         this.m_db.update(paramProfile, this.vctReturnsEntityKeys, false, false);
/*     */         
/* 829 */         for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 830 */           ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*     */           
/* 832 */           for (byte b1 = 0; b1 < returnEntityKey.m_vctAttributes.size(); b1++) {
/* 833 */             Attribute attribute = returnEntityKey.m_vctAttributes.elementAt(b1);
/* 834 */             if (attribute instanceof Text) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 847 */               EntityItem entityItem = (EntityItem)this.updatedTbl.get(returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/* 848 */               addDebug("update entity: " + returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/* 849 */               entityItem.commit(this.m_db, null);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } finally {
/*     */         
/* 855 */         this.vctReturnsEntityKeys.clear();
/* 856 */         this.updatedTbl.clear();
/* 857 */         this.m_db.commit();
/* 858 */         this.m_db.freeStatement();
/* 859 */         this.m_db.isPending("finally after updatePDH");
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SPSTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */