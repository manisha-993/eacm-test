/*     */ package COM.ibm.eannounce.abr;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
/*     */ import com.ibm.eacm.AES256Utils;
/*     */ import com.ibm.transform.oim.eacm.util.Log;
/*     */ import com.ibm.transform.oim.eacm.xalan.BinaryReport;
/*     */ import com.ibm.transform.oim.eacm.xalan.DataView;
/*     */ import com.ibm.transform.oim.eacm.xalan.EntityParam;
/*     */ import com.ibm.transform.oim.eacm.xalan.Init;
/*     */ import com.ibm.transform.oim.eacm.xalan.JarURIResolver;
/*     */ import com.ibm.transform.oim.eacm.xalan.ODSConnection;
/*     */ import com.ibm.transform.oim.eacm.xalan.PDHAccess;
/*     */ import com.ibm.transform.oim.eacm.xalan.ReturnCode;
/*     */ import com.ibm.transform.oim.eacm.xalan.table.EntityTable;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.URIResolver;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XSLReportABR
/*     */   extends PokBaseABR
/*     */ {
/* 119 */   private final JarURIResolver resolver = new JarURIResolver();
/* 120 */   private String configError = null;
/* 121 */   private String xslFilename = null;
/* 122 */   private MessageFormat mf = null;
/* 123 */   private final String[] mfArgs = new String[10];
/* 124 */   private EntityItem ei = null;
/* 125 */   private Vector returnCodes = new Vector();
/* 126 */   private BinaryReport binaryReport = null;
/* 127 */   private Connection conODS = null;
/* 128 */   private String dgReportNameOveride = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 135 */     boolean bool = true;
/* 136 */     String str = "XSL Report ABR";
/* 137 */     PrintWriter printWriter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 146 */       start_ABRBuild(false);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 152 */         printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getABRItem().getFileName(), false), "UTF-8"));
/*     */         
/* 154 */         setPrintWriter(printWriter);
/* 155 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 156 */         logError(unsupportedEncodingException, "Unable to override PrintWriter for UTF-8 output");
/* 157 */       } catch (FileNotFoundException fileNotFoundException) {
/* 158 */         logError(fileNotFoundException, "Unable to override PrintWriter for UTF-8 output");
/*     */       } 
/*     */       
/* 161 */       setReturnCode(0);
/* 162 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Navigate");
/* 163 */       this.ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/* 164 */       entityGroup.put(this.ei.getKey(), this.ei);
/* 165 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 166 */       if (documentBuilderFactory == null) {
/* 167 */         throw new Exception("XSLReportABR.execute_run(): could not create document builder factory");
/*     */       }
/* 169 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 170 */       if (documentBuilder == null) {
/* 171 */         throw new Exception("XSLReportABR.execute_run(): could not create document builder");
/*     */       }
/*     */ 
/*     */       
/* 175 */       Document document = (Document)initializeABRTemplate(documentBuilder);
/* 176 */       DOMSource dOMSource = new DOMSource(document);
/*     */       
/* 178 */       StreamResult streamResult = new StreamResult(getPrintWriter());
/* 179 */       Transformer transformer = initializeStyleSheet(documentBuilder);
/*     */       
/* 181 */       for (byte b = 0; b < this.returnCodes.size(); b++) {
/* 182 */         ReturnCode returnCode = this.returnCodes.elementAt(b);
/* 183 */         bool &= returnCode.hasPassed();
/*     */       } 
/* 185 */       if (bool) {
/* 186 */         setReturnCode(0);
/* 187 */         if (this.binaryReport != null) {
/* 188 */           byte[] arrayOfByte = this.binaryReport.getBytes();
/* 189 */           if (arrayOfByte == null || arrayOfByte.length == 0);
/*     */ 
/*     */           
/* 192 */           setAttachByteForDG(arrayOfByte);
/*     */         } 
/*     */       } else {
/*     */         
/* 196 */         setReturnCode(-1);
/*     */       } 
/* 198 */       updateABRTemplate(document);
/*     */       
/* 200 */       transformer.transform(dOMSource, streamResult);
/*     */       
/* 202 */       str = getDGTitle(false);
/* 203 */     } catch (Exception exception) {
/* 204 */       String str1 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 205 */       String str2 = "<pre>{0}</pre>";
/* 206 */       StringWriter stringWriter = new StringWriter();
/* 207 */       setReturnCode(-1);
/* 208 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 210 */       this.mf = new MessageFormat(str1);
/* 211 */       this.mfArgs[0] = exception.getMessage();
/* 212 */       println(this.mf.format(this.mfArgs));
/* 213 */       this.mf = new MessageFormat(str2);
/* 214 */       this.mfArgs[0] = stringWriter.getBuffer().toString();
/* 215 */       println(this.mf.format(this.mfArgs));
/* 216 */       logError("Exception: " + exception.getMessage());
/* 217 */       logError(stringWriter.getBuffer().toString());
/*     */     } finally {
/*     */       
/* 220 */       setDGName(this.ei, this.m_abri.getABRCode());
/* 221 */       if (this.dgReportNameOveride != null) {
/* 222 */         str = this.dgReportNameOveride + ((getReturnCode() == 0) ? " has Passed" : " has Failed");
/*     */       }
/*     */       
/* 225 */       setDGTitle(str);
/* 226 */       setDGRptName(str);
/* 227 */       setDGRptClass(this.m_abri.getABRCode());
/*     */ 
/*     */       
/* 230 */       if (str.equals("XSL Report ABR")) {
/* 231 */         printDGSubmitString();
/*     */       }
/*     */ 
/*     */       
/* 235 */       if (!isReadOnly()) {
/* 236 */         clearSoftLock();
/*     */       }
/*     */       
/* 239 */       if (printWriter != null) {
/* 240 */         printWriter.close();
/*     */       }
/*     */       
/* 243 */       if (this.conODS != null) {
/*     */         try {
/* 245 */           this.conODS.close();
/* 246 */         } catch (SQLException sQLException) {
/*     */           
/* 248 */           sQLException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Element getStyleSheet(Document paramDocument) throws TransformerException, SAXException, IOException, Exception {
/* 256 */     Element element = null;
/* 257 */     NodeList nodeList = paramDocument.getElementsByTagName(getEntityType());
/* 258 */     if (nodeList == null || nodeList.getLength() == 0) {
/* 259 */       this.mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): Could not find config for Entity Type: {0}");
/* 260 */       this.mfArgs[0] = getEntityType();
/* 261 */       setConfigError(this.mf.format(this.mfArgs));
/*     */     }
/* 263 */     else if (nodeList.getLength() > 1) {
/* 264 */       this.mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): More than one config for Entity Type: {0}");
/* 265 */       this.mfArgs[0] = getEntityType();
/* 266 */       setConfigError(this.mf.format(this.mfArgs));
/*     */     } else {
/*     */       
/* 269 */       element = (Element)nodeList.item(0);
/* 270 */       nodeList = element.getElementsByTagName(this.m_abri.getABRCode());
/* 271 */       if (nodeList == null || nodeList.getLength() == 0) {
/* 272 */         this.mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): Could not find config for ABR Code: {0}");
/* 273 */         this.mfArgs[0] = this.m_abri.getABRCode();
/* 274 */         setConfigError(this.mf.format(this.mfArgs));
/*     */       }
/* 276 */       else if (nodeList.getLength() > 1) {
/* 277 */         this.mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): More than one config for ABR Code: {0}");
/* 278 */         this.mfArgs[0] = getEntityType();
/* 279 */         setConfigError(this.mf.format(this.mfArgs));
/*     */       } else {
/*     */         
/* 282 */         element = (Element)nodeList.item(0);
/* 283 */         nodeList = element.getElementsByTagName("style");
/* 284 */         if (nodeList == null || nodeList.getLength() == 0) {
/* 285 */           this.mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): Could not find style for: Entity Type {0}, Attribute Code {1}");
/* 286 */           this.mfArgs[0] = getEntityType();
/* 287 */           this.mfArgs[1] = this.m_abri.getABRCode();
/* 288 */           setConfigError(this.mf.format(this.mfArgs));
/*     */         }
/* 290 */         else if (nodeList.getLength() > 1) {
/* 291 */           this.mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): More than one style for: Entity Type {0}, Attribute Code {1}");
/* 292 */           this.mfArgs[0] = getEntityType();
/* 293 */           this.mfArgs[1] = this.m_abri.getABRCode();
/* 294 */           setConfigError(this.mf.format(this.mfArgs));
/*     */         } else {
/*     */           
/* 297 */           Element element1 = (Element)nodeList.item(0);
/* 298 */           setXslFilename(element1.getAttribute("xsl"));
/*     */         } 
/*     */       } 
/*     */     } 
/* 302 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Document getConfiguration(DocumentBuilder paramDocumentBuilder) throws TransformerException, Exception, SAXException, IOException {
/* 308 */     StreamSource streamSource = (StreamSource)this.resolver.resolve("/com/ibm/transform/oim/eacm/xalan/XSLReportABRConfiguration.xml", "");
/*     */     
/* 310 */     if (streamSource == null) {
/* 311 */       setReturnCode(-1);
/* 312 */       throw new Exception("XSLReportABR.getConfiguration(DocumentBuilder): could not resolve XSLReportABRConfiguration.xml");
/*     */     } 
/* 314 */     Document document = paramDocumentBuilder.parse(streamSource.getInputStream());
/* 315 */     if (document == null) {
/* 316 */       setReturnCode(-1);
/* 317 */       throw new Exception("XSLReportABR.getConfiguration(DocumentBuilder): could not create config document");
/*     */     } 
/* 319 */     return document;
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
/*     */   private Transformer initializeStyleSheet(DocumentBuilder paramDocumentBuilder) throws TransformerException, Exception, TransformerConfigurationException, InstantiationException, IllegalAccessException, ClassNotFoundException {
/* 331 */     Document document = getConfiguration(paramDocumentBuilder);
/* 332 */     Element element = getStyleSheet(document);
/*     */ 
/*     */     
/* 335 */     Source source = null;
/* 336 */     Transformer transformer = null;
/* 337 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 338 */     if (transformerFactory == null) {
/* 339 */       throw new Exception("XSLReportABR.initializeStyleSheet(DocumentBuilder): could not create transformer factory");
/*     */     }
/* 341 */     transformerFactory.setURIResolver((URIResolver)this.resolver);
/* 342 */     if (getConfigError() != null) {
/* 343 */       source = this.resolver.resolve(getXslFilename(), "");
/* 344 */       if (source == null) {
/* 345 */         this.mf = new MessageFormat("XSLReportABR.initializeStyleSheet(DocumentBuilder): could not resolve configuration exception style sheet: {0}");
/* 346 */         this.mfArgs[0] = getXslFilename();
/* 347 */         throw new Exception(this.mf.format(this.mfArgs));
/*     */       } 
/* 349 */       transformer = transformerFactory.newTransformer(source);
/* 350 */       transformer.setParameter("configError", getConfigError());
/*     */     } else {
/*     */       
/* 353 */       source = this.resolver.resolve(getXslFilename(), "");
/* 354 */       if (source == null) {
/* 355 */         this.mf = new MessageFormat("Could not resolve: {0} for Entity Type {1}, Attribute Code {2}");
/* 356 */         this.mfArgs[0] = getXslFilename();
/* 357 */         this.mfArgs[1] = getEntityType();
/* 358 */         this.mfArgs[2] = this.m_abri.getABRCode();
/* 359 */         setConfigError(this.mf.format(this.mfArgs));
/* 360 */         source = this.resolver.resolve(getXslFilename(), "");
/* 361 */         if (source == null) {
/* 362 */           this.mf = new MessageFormat("XSLReportABR.initializeStyleSheet(DocumentBuilder): could not resolve configuration exception style sheet: {0}");
/* 363 */           this.mfArgs[0] = getXslFilename();
/* 364 */           throw new Exception(this.mf.format(this.mfArgs));
/*     */         } 
/* 366 */         transformer = transformerFactory.newTransformer(source);
/* 367 */         transformer.setParameter("configError", getConfigError());
/*     */       } else {
/*     */         
/* 370 */         transformer = transformerFactory.newTransformer(source);
/*     */         
/* 372 */         NodeList nodeList = element.getElementsByTagName("xslparam");
/* 373 */         for (byte b = 0; b < nodeList.getLength(); b++) {
/* 374 */           Element element1 = (Element)nodeList.item(b);
/* 375 */           String str = element1.getAttribute("name");
/* 376 */           Object object = null;
/* 377 */           if (element1.hasAttribute("class")) {
/* 378 */             object = initilizeClass(element1);
/*     */           }
/*     */           else {
/*     */             
/* 382 */             object = element1.getFirstChild().getNodeValue();
/*     */           } 
/* 384 */           transformer.setParameter(str, object);
/*     */         } 
/* 386 */         nodeList = element.getElementsByTagName("dgTitle");
/* 387 */         if (nodeList != null && nodeList.getLength() > 0)
/*     */         {
/* 389 */           this.dgReportNameOveride = nodeList.item(0).getFirstChild().getNodeValue();
/*     */         }
/*     */       } 
/*     */     } 
/* 393 */     return transformer;
/*     */   }
/*     */   
/*     */   private Object initilizeClass(Element paramElement) throws Exception {
/* 397 */     String str1 = getClass().getName() + ".initializeClass(Element): ";
/* 398 */     Object object = null;
/* 399 */     String str2 = paramElement.getAttribute("class");
/*     */     
/*     */     try {
/* 402 */       Class<?> clazz = Class.forName(str2);
/* 403 */       object = clazz.newInstance();
/* 404 */       if (object instanceof EntityParam) {
/* 405 */         EntityParam entityParam = (EntityParam)object;
/* 406 */         entityParam.setEntityType(this.m_abri.getEntityType());
/* 407 */         entityParam.setEntityID(this.m_abri.getEntityID());
/*     */       } 
/* 409 */       if (object instanceof BinaryReport) {
/* 410 */         this.binaryReport = (BinaryReport)object;
/*     */       }
/* 412 */       if (object instanceof ODSConnection) {
/* 413 */         Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
/* 414 */         if (this.conODS == null) {
/* 415 */           this.conODS = DriverManager.getConnection(
/* 416 */               MiddlewareServerProperties.getPDHDatabaseURL(), 
/* 417 */               MiddlewareServerProperties.getPDHDatabaseUser(), 
/* 418 */               AES256Utils.decrypt(MiddlewareServerProperties.getPDHDatabasePassword()));
/*     */         }
/*     */         
/* 421 */         ODSConnection oDSConnection = (ODSConnection)object;
/* 422 */         oDSConnection.setODSconnection(this.conODS);
/*     */       } 
/* 424 */       if (object instanceof PDHAccess) {
/* 425 */         PDHAccess pDHAccess = (PDHAccess)object;
/* 426 */         pDHAccess.setDatabase(this.m_db);
/* 427 */         pDHAccess.setProfile(this.m_prof);
/*     */       } 
/* 429 */       if (object instanceof Log) {
/* 430 */         Log log = (Log)object;
/* 431 */         log.setIdentifier(toString());
/*     */       } 
/* 433 */       if (object instanceof ReturnCode && 
/* 434 */         !paramElement.hasAttribute("excludeReturnCode")) {
/* 435 */         ReturnCode returnCode = (ReturnCode)object;
/* 436 */         this.returnCodes.add(returnCode);
/*     */       } 
/*     */       
/* 439 */       if (object instanceof DataView) {
/*     */         
/* 441 */         DataView dataView = (DataView)object;
/* 442 */         dataView.setAttributeCodeOfABR(this.m_abri.getABRCode());
/* 443 */         if (paramElement.hasAttribute("veName")) {
/* 444 */           dataView.setVEName(paramElement.getAttribute("veName"));
/*     */         }
/*     */       } 
/*     */       
/* 448 */       if (paramElement.hasChildNodes()) {
/* 449 */         Node node = paramElement.getFirstChild();
/*     */         do {
/* 451 */           if (node.getNodeType() == 1) {
/* 452 */             Element element = (Element)node;
/* 453 */             if (element.getTagName().equals("objparam")) {
/* 454 */               if (element.hasAttribute("method")) {
/* 455 */                 String str = element.getAttribute("method");
/* 456 */                 if (element.hasAttribute("class")) {
/*     */ 
/*     */                   
/* 459 */                   Object[] arrayOfObject = { initilizeClass(element) };
/*     */ 
/*     */ 
/*     */                   
/* 463 */                   Class[] arrayOfClass = { arrayOfObject[0].getClass() };
/*     */ 
/*     */                   
/* 466 */                   Method method = null;
/*     */                   
/*     */                   try {
/* 469 */                     method = clazz.getMethod(str, arrayOfClass);
/*     */                   
/*     */                   }
/* 472 */                   catch (NoSuchMethodException noSuchMethodException) {
/*     */                     
/* 474 */                     Method[] arrayOfMethod = clazz.getMethods();
/* 475 */                     for (byte b = 0; b < arrayOfMethod.length; b++) {
/* 476 */                       if (arrayOfMethod[b].getName().equals(str)) {
/* 477 */                         method = arrayOfMethod[b];
/*     */                       }
/*     */                     } 
/*     */                     
/* 481 */                     if (method == null) {
/* 482 */                       throw noSuchMethodException;
/*     */                     }
/*     */                     
/* 485 */                     Class[] arrayOfClass1 = method.getParameterTypes();
/* 486 */                     if (!arrayOfClass1[0].isInstance(arrayOfObject[0])) {
/* 487 */                       System.err.print(str1);
/* 488 */                       System.err.print(object.getClass().getName());
/* 489 */                       System.err.print(" method ");
/* 490 */                       System.err.print(str);
/* 491 */                       System.err.print(" requires ");
/* 492 */                       System.err.print(arrayOfClass1[0].getName());
/* 493 */                       System.err.print(" not ");
/* 494 */                       System.err.println(arrayOfObject[0].getClass().getName());
/* 495 */                       noSuchMethodException.printStackTrace();
/*     */                     } 
/*     */                   } 
/*     */ 
/*     */                   
/* 500 */                   Object object1 = method.invoke(object, arrayOfObject);
/* 501 */                   if (object1 != null && object1 instanceof Boolean && 
/* 502 */                     Boolean.FALSE.equals(object1))
/*     */                   {
/* 504 */                     System.err.print(str1);
/* 505 */                     System.err.print(method.toString());
/* 506 */                     System.err.println(" returned false");
/*     */                   }
/*     */                 
/*     */                 } else {
/*     */                   
/* 511 */                   System.err.print(str1);
/* 512 */                   System.err.println(" class is a required attribute of objparam");
/*     */                 } 
/*     */               } else {
/*     */                 
/* 516 */                 System.err.print(str1);
/* 517 */                 System.err.println(" method is a required attribute of objparam");
/*     */               } 
/*     */             } else {
/*     */               
/* 521 */               System.err.print(str1);
/* 522 */               System.err.println("ignored unrecognized tag: " + element.getTagName());
/*     */             } 
/*     */           } 
/* 525 */           node = node.getNextSibling();
/*     */         }
/* 527 */         while (node != null);
/*     */       } 
/* 529 */       if (object instanceof Init) {
/* 530 */         Init init = (Init)object;
/* 531 */         if (paramElement.hasAttribute("initBefore") && "true".equals(paramElement.getAttribute("initBefore")))
/*     */         {
/* 533 */           if (!init.initialize()) {
/* 534 */             System.err.print(str1);
/* 535 */             System.err.println("initialization of " + str2 + " failed");
/*     */           } 
/*     */         }
/*     */       } 
/* 539 */       if (object instanceof EntityTable) {
/*     */         
/* 541 */         NodeList nodeList = paramElement.getElementsByTagName("column");
/* 542 */         String[] arrayOfString = new String[nodeList.getLength()];
/* 543 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 544 */           Element element = (Element)nodeList.item(b);
/* 545 */           arrayOfString[b] = element.getAttribute("code");
/*     */         } 
/* 547 */         if (arrayOfString.length > 0) {
/* 548 */           EntityTable entityTable = (EntityTable)object;
/* 549 */           entityTable.setColumnAttributeCodes(arrayOfString);
/*     */         } else {
/*     */           
/* 552 */           throw new Exception(str1 + "no attribute codes specified for the EntityTable in the configuration");
/*     */         } 
/*     */       } 
/* 555 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */       
/* 557 */       classNotFoundException.printStackTrace();
/* 558 */     } catch (InstantiationException instantiationException) {
/*     */       
/* 560 */       instantiationException.printStackTrace();
/* 561 */     } catch (IllegalAccessException illegalAccessException) {
/*     */       
/* 563 */       illegalAccessException.printStackTrace();
/* 564 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 566 */       illegalArgumentException.printStackTrace();
/* 567 */     } catch (InvocationTargetException invocationTargetException) {
/*     */       
/* 569 */       invocationTargetException.printStackTrace();
/*     */     } 
/* 571 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateABRTemplate(Document paramDocument) throws Exception {
/* 578 */     Element element = paramDocument.getDocumentElement();
/*     */     
/* 580 */     PrintWriter printWriter1 = getPrintWriter();
/* 581 */     StringWriter stringWriter = new StringWriter();
/* 582 */     PrintWriter printWriter2 = new PrintWriter(stringWriter);
/*     */     try {
/* 584 */       setPrintWriter(printWriter2);
/* 585 */       printDGSubmitString();
/*     */       
/* 587 */       String str = stringWriter.toString();
/*     */       
/* 589 */       setElementValue(element, "dgsubmit", str);
/* 590 */       setPrintWriter(printWriter1);
/* 591 */     } catch (Exception exception) {
/* 592 */       throw exception;
/*     */     } finally {
/*     */       
/* 595 */       printWriter2.close();
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
/*     */   private Node initializeABRTemplate(DocumentBuilder paramDocumentBuilder) throws Exception, ParserConfigurationException, TransformerException, SAXException, IOException {
/* 611 */     InputStream inputStream = this.resolver.getInputStream("/com/ibm/transform/oim/eacm/xalan/input/template/abr.xml");
/* 612 */     if (inputStream == null) {
/* 613 */       throw new Exception("XSLReportABR.initializeABRTemplate(DocumentBuilder): could not resolve abr.xml template");
/*     */     }
/* 615 */     Document document = paramDocumentBuilder.parse(inputStream);
/* 616 */     Element element1 = document.getDocumentElement();
/* 617 */     if (document == null) {
/* 618 */       throw new Exception("XSLReportABR.initializeABRTemplate(DocumentBuilder): could not create abr document");
/*     */     }
/*     */     
/* 621 */     setElementValue(element1, "class", getClass().getName());
/*     */ 
/*     */     
/* 624 */     setElementValue(element1, "code", getABRCode());
/*     */ 
/*     */     
/* 627 */     Element element2 = setElementValue(element1, "entity", this.ei.getLongDescription());
/* 628 */     element2.setAttribute("type", getEntityType());
/* 629 */     element2.setAttribute("id", Integer.toString(getEntityID()));
/*     */ 
/*     */     
/* 632 */     setElementValue(element1, "revision", getABRVersion());
/*     */ 
/*     */     
/* 635 */     setElementValue(element1, "job-id", toString());
/*     */ 
/*     */     
/* 638 */     setElementValue(element1, "timestamp", this.m_prof.getNow());
/*     */ 
/*     */     
/* 641 */     setElementValue(element1, "user-token", this.m_prof.getEmailAddress());
/*     */ 
/*     */ 
/*     */     
/* 645 */     element2 = setElementValue(element1, "workgroup", this.m_prof.getWGName());
/* 646 */     element2.setAttribute("id", Integer.toString(this.m_prof.getWGID()));
/*     */ 
/*     */     
/* 649 */     element2 = setElementValue(element1, "read", this.m_prof.getReadLanguage().getNLSDescription());
/* 650 */     element2.setAttribute("id", Integer.toString(this.m_prof.getReadLanguage().getNLSID()));
/*     */ 
/*     */     
/* 653 */     element2 = setElementValue(element1, "write", this.m_prof.getWriteLanguage().getNLSDescription());
/* 654 */     element2.setAttribute("id", Integer.toString(this.m_prof.getWriteLanguage().getNLSID()));
/*     */ 
/*     */     
/* 657 */     setElementValue(element1, "enterprise", this.m_prof.getEnterprise());
/*     */ 
/*     */     
/* 660 */     element2 = setElementValue(element1, "role", this.m_prof.getRoleDescription());
/* 661 */     element2.setAttribute("code", this.m_prof.getRoleCode());
/* 662 */     return document;
/*     */   }
/*     */   private Element setElementValue(Element paramElement, String paramString1, String paramString2) throws Exception {
/* 665 */     NodeList nodeList = paramElement.getElementsByTagName(paramString1);
/*     */ 
/*     */     
/* 668 */     if (nodeList == null || nodeList.getLength() == 0) {
/* 669 */       this.mf = new MessageFormat("XSLReportABR.setElementValue(Element,String,String): could not find {0}");
/* 670 */       this.mfArgs[0] = paramString1;
/* 671 */       throw new Exception(this.mf.format(this.mfArgs));
/*     */     } 
/* 673 */     if (nodeList.getLength() > 1) {
/* 674 */       this.mf = new MessageFormat("XSLReportABR.setElementValue(Element,String,String): only one {0} allowed in abr template");
/* 675 */       this.mfArgs[0] = paramString1;
/* 676 */       throw new Exception(this.mf.format(this.mfArgs));
/*     */     } 
/* 678 */     Element element = (Element)nodeList.item(0);
/* 679 */     Node node = element.getFirstChild();
/* 680 */     node.setNodeValue(paramString2);
/* 681 */     return element;
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
/*     */   private String getDGTitle(boolean paramBoolean) throws SQLException, MiddlewareException {
/* 697 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 699 */     EntityGroup entityGroup = this.ei.getEntityGroup();
/* 700 */     Iterator<EANMetaAttribute> iterator = entityGroup.getMetaAttribute().values().iterator();
/* 701 */     while (iterator.hasNext()) {
/* 702 */       EANMetaAttribute eANMetaAttribute = iterator.next();
/* 703 */       if (!paramBoolean && eANMetaAttribute.getAttributeCode().equals(getShortClassName(getClass()))) {
/* 704 */         stringBuffer.append((getReturnCode() == 0) ? "Passed" : "Failed");
/*     */       } else {
/*     */         
/* 707 */         stringBuffer.append(getAttributeValue(this.ei, eANMetaAttribute.getAttributeCode()));
/*     */       } 
/* 709 */       if (iterator.hasNext()) {
/* 710 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 713 */     stringBuffer.append(" ");
/* 714 */     stringBuffer.append(
/* 715 */         getMetaAttributeDescription(new EntityGroup(null, this.m_db, this.m_prof, this.m_abri
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 720 */             .getEntityType(), "Edit"), this.m_abri
/*     */           
/* 722 */           .getABRCode()));
/* 723 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 731 */     return "XSL Report ABR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 739 */     return "$Revision: 1.13 $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConfigError(String paramString) {
/* 747 */     setReturnCode(-1);
/* 748 */     this.configError = paramString;
/* 749 */     logError(paramString);
/* 750 */     this.xslFilename = "/com/ibm/transform/oim/eacm/xalan/style/XSLReportABRConfigurationError.xsl";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getConfigError() {
/* 758 */     return this.configError;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXslFilename(String paramString) {
/* 766 */     this.xslFilename = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXslFilename() {
/* 774 */     return this.xslFilename;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\XSLReportABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */