/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.MQUsage;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.mq.MQException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Hashtable;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SAPLXMLBase
/*     */ {
/*  45 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  46 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private String failedStr = "Failed";
/*  55 */   private String xmlgen = "";
/*     */   
/*     */   private boolean checkSAPLattr = true;
/*  58 */   protected SAPLABRSTATUS saplAbr = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private static final Hashtable SAPL_TRANS_TBL = new Hashtable<>(); protected static final String XMLNS_WSNT = "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd"; protected static final String XMLNS_EBI = "http://ibm.com/esh/ebi"; static {
/*  77 */     SAPL_TRANS_TBL.put("20", "40");
/*  78 */     SAPL_TRANS_TBL.put("30", "40");
/*  79 */     SAPL_TRANS_TBL.put("40", "80");
/*  80 */     SAPL_TRANS_TBL.put("80", "70");
/*     */   }
/*     */   protected static final String ESHMQSERIES = "ESHMQSERIES"; protected static final String OIDHMQSERIES = "OIDHMQSERIES";
/*     */   protected SAPLXMLBase(boolean paramBoolean) {
/*  84 */     this.checkSAPLattr = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute_run(SAPLABRSTATUS paramSAPLABRSTATUS) throws Exception {
/*  92 */     this.saplAbr = paramSAPLABRSTATUS;
/*  93 */     this.failedStr = this.saplAbr.getBundle().getString("FAILED");
/*  94 */     return checkSaplAndSendXML();
/*     */   } public String getXMLGenMsg() {
/*  96 */     return this.xmlgen;
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
/*     */   protected void addOutput(String paramString) {
/* 116 */     this.saplAbr.addOutput(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 121 */     this.saplAbr.addDebug(paramString);
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
/*     */   private boolean checkSaplAndSendXML() throws SQLException, MiddlewareException, MiddlewareRequestException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 145 */     boolean bool = false;
/* 146 */     if (this.checkSAPLattr) {
/* 147 */       EntityItem entityItem = this.saplAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
/*     */       
/* 149 */       EANMetaAttribute eANMetaAttribute = this.saplAbr.getEntityList().getParentEntityGroup().getMetaAttribute("SAPL");
/* 150 */       if (eANMetaAttribute == null) {
/* 151 */         addDebug("SAPL was not in meta, no xml generation done");
/*     */       } else {
/*     */         
/* 154 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "SAPL");
/* 155 */         if (str1 == null) {
/* 156 */           str1 = "10";
/*     */         }
/*     */ 
/*     */         
/* 160 */         String str2 = (String)SAPL_TRANS_TBL.get(str1);
/* 161 */         if (str2 == null) {
/* 162 */           String[] arrayOfString = new String[1];
/* 163 */           addDebug("SAPL current value is not in list [" + str1 + "]");
/*     */           
/* 165 */           MessageFormat messageFormat = new MessageFormat(this.saplAbr.getBundle().getString("SAPL_WRONG_VALUE"));
/* 166 */           arrayOfString[0] = PokUtils.getAttributeValue(entityItem, "SAPL", ", ", "", false);
/* 167 */           addOutput("<p>" + messageFormat.format(arrayOfString) + "</p>");
/* 168 */           this.xmlgen = this.failedStr;
/*     */         } else {
/* 170 */           bool = doSaplXmlFeed();
/*     */         } 
/*     */       } 
/*     */     } else {
/* 174 */       bool = doSaplXmlFeed();
/*     */     } 
/*     */     
/* 177 */     return bool;
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
/*     */   protected void mergeLists(EntityList paramEntityList) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean doSaplXmlFeed() throws SQLException, MiddlewareException, MiddlewareRequestException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 205 */     boolean bool = false;
/* 206 */     String[] arrayOfString = new String[3];
/* 207 */     MessageFormat messageFormat = null;
/*     */     try {
/* 209 */       Vector<String> vector = getMQPropertiesFN();
/* 210 */       Vector vector1 = getSAPLXMLMap();
/* 211 */       if (vector == null) {
/* 212 */         addDebug("No MQ properties files, nothing will be generated.");
/* 213 */       } else if (vector1 == null) {
/* 214 */         addDebug("No SAPL XML Mappings, nothing will be generated.");
/*     */       } else {
/* 216 */         String str = generateXML();
/* 217 */         byte b1 = 0;
/* 218 */         addDebug("Generated xml:" + NEWLINE + str + NEWLINE);
/*     */ 
/*     */         
/* 221 */         for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 222 */           String str1 = vector.elementAt(b2);
/* 223 */           ResourceBundle resourceBundle = ResourceBundle.getBundle(str1, 
/* 224 */               SAPLABRSTATUS.getLocale(this.saplAbr.getEntityList().getProfile().getReadLanguage().getNLSID()));
/* 225 */           Hashtable hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/* 226 */           boolean bool1 = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/*     */           
/* 228 */           if (bool1) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 268 */               MQUsage.putToMQQueue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + str, hashtable);
/*     */               
/* 270 */               messageFormat = new MessageFormat(this.saplAbr.getBundle().getString("SENT_SUCCESS"));
/* 271 */               arrayOfString[0] = str1;
/* 272 */               addOutput("<p>" + messageFormat.format(arrayOfString) + "</p>");
/* 273 */               b1++;
/* 274 */               if (!this.xmlgen.equals(this.failedStr)) {
/* 275 */                 this.xmlgen = this.saplAbr.getBundle().getString("SUCCESS");
/*     */               }
/* 277 */             } catch (MQException mQException) {
/*     */               
/* 279 */               this.xmlgen = this.failedStr;
/* 280 */               messageFormat = new MessageFormat(this.saplAbr.getBundle().getString("MQ_ERROR"));
/* 281 */               arrayOfString[0] = str1;
/* 282 */               arrayOfString[1] = "" + mQException.completionCode;
/* 283 */               arrayOfString[2] = "" + mQException.reasonCode;
/* 284 */               addOutput("<p>" + messageFormat.format(arrayOfString) + "</p>");
/* 285 */               mQException.printStackTrace(System.out);
/* 286 */             } catch (IOException iOException) {
/*     */               
/* 288 */               this.xmlgen = this.failedStr;
/* 289 */               messageFormat = new MessageFormat(this.saplAbr.getBundle().getString("MQIO_ERROR"));
/* 290 */               arrayOfString[0] = str1;
/* 291 */               arrayOfString[1] = iOException.toString();
/* 292 */               addOutput("<p>" + messageFormat.format(arrayOfString) + "</p>");
/* 293 */               iOException.printStackTrace(System.out);
/*     */             } 
/*     */           } else {
/*     */             
/* 297 */             messageFormat = new MessageFormat(this.saplAbr.getBundle().getString("NO_NOTIFY"));
/* 298 */             arrayOfString[0] = str1;
/* 299 */             addOutput("<p>" + messageFormat.format(arrayOfString) + "</p>");
/* 300 */             this.xmlgen = this.saplAbr.getBundle().getString("NOT_SENT");
/*     */           } 
/*     */         } 
/* 303 */         if (b1 == vector.size()) {
/* 304 */           bool = true;
/*     */         } else {
/* 306 */           if (b1 > 0) {
/* 307 */             this.xmlgen = this.saplAbr.getBundle().getString("ALL_NOT_SENT");
/*     */           }
/* 309 */           if (this.checkSAPLattr) {
/* 310 */             addDebug("SAPL was not updated because all MQ msgs were not sent successfully");
/*     */           }
/*     */         } 
/*     */       } 
/* 314 */     } catch (IOException iOException) {
/*     */ 
/*     */       
/* 317 */       messageFormat = new MessageFormat(this.saplAbr.getBundle().getString("REQ_ERROR"));
/* 318 */       arrayOfString[0] = iOException.getMessage();
/* 319 */       addOutput("<p>" + messageFormat.format(arrayOfString) + "</p>");
/* 320 */       this.xmlgen = this.failedStr;
/* 321 */     } catch (SQLException sQLException) {
/* 322 */       this.xmlgen = this.failedStr;
/* 323 */       throw sQLException;
/* 324 */     } catch (MiddlewareRequestException middlewareRequestException) {
/* 325 */       this.xmlgen = this.failedStr;
/* 326 */       throw middlewareRequestException;
/* 327 */     } catch (MiddlewareException middlewareException) {
/* 328 */       this.xmlgen = this.failedStr;
/* 329 */       throw middlewareException;
/* 330 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 331 */       this.xmlgen = this.failedStr;
/* 332 */       throw parserConfigurationException;
/* 333 */     } catch (TransformerException transformerException) {
/* 334 */       this.xmlgen = this.failedStr;
/* 335 */       throw transformerException;
/*     */     } 
/* 337 */     return bool;
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
/*     */   private String generateXML() throws SQLException, MiddlewareException, MiddlewareRequestException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 358 */     EntityList entityList = this.saplAbr.getEntityList();
/* 359 */     Profile profile = this.saplAbr.getEntityList().getProfile();
/*     */     
/* 361 */     Vector<SAPLElem> vector = getSAPLXMLMap();
/*     */ 
/*     */ 
/*     */     
/* 365 */     mergeLists(entityList);
/*     */ 
/*     */     
/* 368 */     profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*     */     
/* 370 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 371 */     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 372 */     Document document = documentBuilder.newDocument();
/*     */     
/* 374 */     Element element = null;
/*     */     
/* 376 */     StringBuffer stringBuffer = new StringBuffer();
/* 377 */     for (byte b = 0; b < vector.size(); b++) {
/* 378 */       SAPLElem sAPLElem = vector.elementAt(b);
/*     */       
/* 380 */       sAPLElem.addElements(this.saplAbr.getDB(), entityList, document, element, stringBuffer);
/*     */     } 
/* 382 */     addDebug("GenXML debug: " + NEWLINE + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 387 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 388 */     Transformer transformer = transformerFactory.newTransformer();
/* 389 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*     */     
/* 391 */     transformer.setOutputProperty("indent", "no");
/* 392 */     transformer.setOutputProperty("method", "xml");
/* 393 */     transformer.setOutputProperty("encoding", "UTF-8");
/*     */ 
/*     */     
/* 396 */     StringWriter stringWriter = new StringWriter();
/* 397 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 398 */     DOMSource dOMSource = new DOMSource(document);
/* 399 */     transformer.transform(dOMSource, streamResult);
/* 400 */     String str = SAPLElem.removeCheat(stringWriter.toString());
/*     */     
/* 402 */     if (entityList != this.saplAbr.getEntityList()) {
/* 403 */       entityList.dereference();
/*     */     }
/*     */     
/* 406 */     return str;
/*     */   }
/*     */   
/*     */   protected SAPLXMLBase() {}
/*     */   
/*     */   protected abstract Vector getMQPropertiesFN();
/*     */   
/*     */   protected abstract String getSaplVeName();
/*     */   
/*     */   protected abstract Vector getSAPLXMLMap();
/*     */   
/*     */   public abstract String getVersion();
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SAPLXMLBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */