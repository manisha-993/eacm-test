/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.MQUsage;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.mq.MQException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EPIMSABRBase
/*     */ {
/*  88 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  89 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private String failedStr = "Failed";
/*  96 */   private String xmlgen = "Not Required";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   protected EPIMSWWPRTBASE epimsAbr = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   private static final Hashtable EPIMS_TRANS_TBL = new Hashtable<>(); static {
/* 131 */     EPIMS_TRANS_TBL.put("EPNOT", "Promoted:Released");
/* 132 */     EPIMS_TRANS_TBL.put("EPWAIT", "Promoted:Released");
/* 133 */     EPIMS_TRANS_TBL.put("Promoted", "Released");
/*     */   }
/*     */   protected static final void loadAttrOfInterest(String paramString, Hashtable<String, String[]> paramHashtable) {
/* 136 */     Properties properties = loadProperties(paramString);
/* 137 */     for (Enumeration<?> enumeration = properties.propertyNames(); enumeration.hasMoreElements(); ) {
/* 138 */       String str1 = (String)enumeration.nextElement();
/* 139 */       String str2 = (String)properties.get(str1);
/* 140 */       StringTokenizer stringTokenizer = new StringTokenizer(str2, ",");
/* 141 */       Vector<String> vector = new Vector();
/* 142 */       while (stringTokenizer.hasMoreTokens()) {
/* 143 */         vector.add(stringTokenizer.nextToken().trim());
/*     */       }
/* 145 */       String[] arrayOfString = new String[vector.size()];
/* 146 */       vector.copyInto((Object[])arrayOfString);
/* 147 */       paramHashtable.put(str1, arrayOfString);
/* 148 */       vector.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final Properties loadProperties(String paramString) {
/* 153 */     Properties properties = null;
/* 154 */     InputStream inputStream = null;
/*     */     try {
/* 156 */       EPIMSLSEOBUNDLEABR ePIMSLSEOBUNDLEABR = new EPIMSLSEOBUNDLEABR();
/* 157 */       inputStream = ePIMSLSEOBUNDLEABR.getClass().getResourceAsStream(paramString);
/*     */       
/* 159 */       if (inputStream != null) {
/* 160 */         properties = new Properties();
/* 161 */         properties.load(inputStream);
/*     */       } else {
/* 163 */         System.err.println("EPIMSABRBase failure to loadProperties " + paramString);
/*     */       } 
/* 165 */     } catch (Exception exception) {
/* 166 */       System.err.println("EPIMSABRBase Unable to loadProperties " + paramString + " " + exception);
/*     */     } finally {
/*     */       
/* 169 */       if (inputStream != null) {
/*     */         try {
/* 171 */           inputStream.close();
/* 172 */         } catch (IOException iOException) {
/* 173 */           System.out.println("I/O Exception " + iOException.getMessage());
/*     */         } 
/*     */       }
/*     */     } 
/* 177 */     return properties;
/*     */   }
/* 179 */   protected static final String EPIMSMQSERIES = "EPIMSMQSERIES"; protected static final String WWPRTMQSERIES = "WWPRTMQSERIES"; protected static final String STATUS_FINAL = "0020"; protected static final String SPECBID_YES = "11458"; protected static final String SYSFEEDRESEND_YES = "Yes"; protected static final String SYSFEEDRESEND_NO = "No"; protected static final String NOT_READY = "EPNOT"; protected PDGUtility pdgUtility = new PDGUtility(); protected static final String SENT_AGAIN = "EPSENTAGAIN";
/*     */   protected static final String WAITING = "EPWAIT";
/*     */   protected static final String WAIT_AGAIN = "EPWAITAGAIN";
/*     */   protected static final String PROMOTED = "Promoted";
/*     */   protected static final String RELEASED = "Released";
/*     */   protected static final String SENT = "Sent";
/*     */   
/*     */   public void execute_run(EPIMSWWPRTBASE paramEPIMSWWPRTBASE) throws Exception {
/* 187 */     this.epimsAbr = paramEPIMSWWPRTBASE;
/* 188 */     this.failedStr = this.epimsAbr.getBundle().getString("FAILED");
/* 189 */     execute();
/*     */   } public String getXMLGenMsg() {
/* 191 */     return this.xmlgen;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector getMQPropertiesFN() {
/* 196 */     return null;
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
/*     */   protected void resendSystemFeed(EntityItem paramEntityItem, String paramString) throws Exception {
/*     */     try {
/* 215 */       handleResend(paramEntityItem, paramString);
/* 216 */     } catch (Exception exception) {
/* 217 */       throw exception;
/*     */     } finally {
/* 219 */       this.epimsAbr.setFlagValue("SYSFEEDRESEND", "No");
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
/*     */ 
/*     */   
/*     */   private Vector getXMLMap() throws MiddlewareRequestException {
/* 237 */     return getXMLMap(this.epimsAbr.isFirstFinal());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getXMLMap(boolean paramBoolean) {
/* 244 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 250 */     this.epimsAbr.addOutput(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 255 */     this.epimsAbr.addDebug(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 260 */     this.epimsAbr.addError(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 265 */     this.epimsAbr.addError(paramString, paramArrayOfObject);
/*     */   }
/*     */   
/*     */   protected void addWarning(String paramString, Object[] paramArrayOfObject) {
/* 269 */     this.epimsAbr.addWarning(paramString, paramArrayOfObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyAndSetStatus(String paramString) throws SQLException, MiddlewareException, MiddlewareRequestException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 589 */     String[] arrayOfString = new String[3];
/* 590 */     MessageFormat messageFormat = null;
/*     */     try {
/* 592 */       Vector<String> vector = getMQPropertiesFN();
/* 593 */       if (vector == null) {
/* 594 */         addDebug("No MQ properties files, nothing will be generated.");
/*     */       } else {
/* 596 */         String str = generateXML();
/* 597 */         byte b1 = 0;
/* 598 */         addDebug("Generated xml:" + NEWLINE + str + NEWLINE);
/*     */ 
/*     */         
/* 601 */         for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 602 */           String str1 = vector.elementAt(b2);
/* 603 */           ResourceBundle resourceBundle = ResourceBundle.getBundle(str1, 
/* 604 */               EPIMSABRSTATUS.getLocale(this.epimsAbr.getProfile().getReadLanguage().getNLSID()));
/* 605 */           Hashtable hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/* 606 */           boolean bool = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/*     */           
/* 608 */           if (bool) {
/*     */             try {
/* 610 */               MQUsage.putToMQQueue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + str, hashtable);
/*     */               
/* 612 */               messageFormat = new MessageFormat(this.epimsAbr.getBundle().getString("SENT_SUCCESS"));
/* 613 */               arrayOfString[0] = str1;
/* 614 */               addOutput(messageFormat.format(arrayOfString));
/* 615 */               b1++;
/* 616 */               if (!this.xmlgen.equals(this.failedStr)) {
/* 617 */                 this.xmlgen = this.epimsAbr.getBundle().getString("SUCCESS");
/*     */               }
/* 619 */             } catch (MQException mQException) {
/*     */               
/* 621 */               this.xmlgen = this.failedStr;
/* 622 */               messageFormat = new MessageFormat(this.epimsAbr.getBundle().getString("MQ_ERROR"));
/* 623 */               arrayOfString[0] = str1;
/* 624 */               arrayOfString[1] = "" + mQException.completionCode;
/* 625 */               arrayOfString[2] = "" + mQException.reasonCode;
/* 626 */               addError(messageFormat.format(arrayOfString));
/* 627 */               mQException.printStackTrace(System.out);
/* 628 */             } catch (IOException iOException) {
/*     */               
/* 630 */               this.xmlgen = this.failedStr;
/* 631 */               messageFormat = new MessageFormat(this.epimsAbr.getBundle().getString("MQIO_ERROR"));
/* 632 */               arrayOfString[0] = str1;
/* 633 */               arrayOfString[1] = iOException.toString();
/* 634 */               addError(messageFormat.format(arrayOfString));
/* 635 */               iOException.printStackTrace(System.out);
/*     */             } 
/*     */           } else {
/*     */             
/* 639 */             messageFormat = new MessageFormat(this.epimsAbr.getBundle().getString("NO_NOTIFY"));
/* 640 */             arrayOfString[0] = str1;
/* 641 */             addError(messageFormat.format(arrayOfString));
/* 642 */             this.xmlgen = this.epimsAbr.getBundle().getString("NOT_SENT");
/*     */           } 
/*     */         } 
/* 645 */         if (b1 > 0 && b1 != vector.size()) {
/* 646 */           this.xmlgen = this.epimsAbr.getBundle().getString("ALL_NOT_SENT");
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 658 */     catch (IOException iOException) {
/*     */ 
/*     */       
/* 661 */       messageFormat = new MessageFormat(this.epimsAbr.getBundle().getString("REQ_ERROR"));
/* 662 */       arrayOfString[0] = iOException.getMessage();
/* 663 */       addError(messageFormat.format(arrayOfString));
/* 664 */       this.xmlgen = this.failedStr;
/* 665 */     } catch (SQLException sQLException) {
/* 666 */       this.xmlgen = this.failedStr;
/* 667 */       throw sQLException;
/* 668 */     } catch (MiddlewareRequestException middlewareRequestException) {
/* 669 */       this.xmlgen = this.failedStr;
/* 670 */       throw middlewareRequestException;
/* 671 */     } catch (MiddlewareException middlewareException) {
/* 672 */       this.xmlgen = this.failedStr;
/* 673 */       throw middlewareException;
/* 674 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 675 */       this.xmlgen = this.failedStr;
/* 676 */       throw parserConfigurationException;
/* 677 */     } catch (TransformerException transformerException) {
/* 678 */       this.xmlgen = this.failedStr;
/* 679 */       throw transformerException;
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
/*     */   
/*     */   private String generateXML() throws SQLException, MiddlewareException, MiddlewareRequestException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 696 */     EntityList entityList = this.epimsAbr.getEntityList();
/* 697 */     Profile profile = this.epimsAbr.getProfile();
/* 698 */     Vector<SAPLElem> vector = getXMLMap();
/*     */ 
/*     */     
/* 701 */     profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*     */     
/* 703 */     profile.setValOnEffOn(this.epimsAbr.getLastFinalDTS(), this.epimsAbr.getLastFinalDTS());
/*     */     
/* 705 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 706 */     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 707 */     Document document = documentBuilder.newDocument();
/*     */     
/* 709 */     Element element = null;
/*     */     
/* 711 */     StringBuffer stringBuffer = new StringBuffer();
/* 712 */     for (byte b = 0; b < vector.size(); b++) {
/* 713 */       SAPLElem sAPLElem = vector.elementAt(b);
/*     */       
/* 715 */       sAPLElem.addElements(this.epimsAbr.getDB(), entityList, document, element, stringBuffer);
/*     */     } 
/* 717 */     addDebug("GenXML debug: " + NEWLINE + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 722 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 723 */     Transformer transformer = transformerFactory.newTransformer();
/* 724 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*     */     
/* 726 */     transformer.setOutputProperty("indent", "no");
/* 727 */     transformer.setOutputProperty("method", "xml");
/* 728 */     transformer.setOutputProperty("encoding", "UTF-8");
/*     */ 
/*     */     
/* 731 */     StringWriter stringWriter = new StringWriter();
/* 732 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 733 */     DOMSource dOMSource = new DOMSource(document);
/* 734 */     transformer.transform(dOMSource, streamResult);
/* 735 */     return SAPLElem.removeCheat(stringWriter.toString());
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
/*     */   protected boolean changeOfInterest() throws SQLException, MiddlewareException {
/* 748 */     boolean bool = false;
/* 749 */     String str1 = this.epimsAbr.getLastFinalDTS();
/* 750 */     String str2 = this.epimsAbr.getPriorFinalDTS();
/*     */     
/* 752 */     if (str1.equals(str2)) {
/* 753 */       addDebug("changeOfInterest Only one transition to Final found, no changes can exist.");
/* 754 */       return bool;
/*     */     } 
/*     */ 
/*     */     
/* 758 */     Profile profile1 = this.epimsAbr.getProfile().getNewInstance(this.epimsAbr.getDB());
/* 759 */     profile1.setValOnEffOn(str1, str1);
/*     */ 
/*     */ 
/*     */     
/* 763 */     String str3 = getVeName();
/*     */     
/* 765 */     EntityList entityList1 = this.epimsAbr.getDB().getEntityList(profile1, new ExtractActionItem(null, this.epimsAbr
/* 766 */           .getDB(), profile1, str3), new EntityItem[] { new EntityItem(null, profile1, this.epimsAbr
/* 767 */             .getEntityType(), this.epimsAbr.getEntityID()) });
/*     */ 
/*     */     
/* 770 */     addDebug("changeOfInterest dts: " + str1 + " extract: " + str3 + NEWLINE + 
/* 771 */         PokUtils.outputList(entityList1));
/*     */ 
/*     */     
/* 774 */     Profile profile2 = this.epimsAbr.getProfile().getNewInstance(this.epimsAbr.getDB());
/* 775 */     profile2.setValOnEffOn(str2, str2);
/*     */ 
/*     */     
/* 778 */     EntityList entityList2 = this.epimsAbr.getDB().getEntityList(profile2, new ExtractActionItem(null, this.epimsAbr
/* 779 */           .getDB(), profile2, str3), new EntityItem[] { new EntityItem(null, profile2, this.epimsAbr
/* 780 */             .getEntityType(), this.epimsAbr.getEntityID()) });
/*     */ 
/*     */     
/* 783 */     addDebug("changeOfInterest dts: " + str2 + " extract: " + str3 + NEWLINE + 
/* 784 */         PokUtils.outputList(entityList2));
/*     */ 
/*     */     
/* 787 */     Hashtable hashtable1 = getStringRep(entityList1, getAttrOfInterest());
/* 788 */     Hashtable hashtable2 = getStringRep(entityList2, getAttrOfInterest());
/*     */     
/* 790 */     bool = changeOfInterest(hashtable1, hashtable2);
/*     */     
/* 792 */     entityList2.dereference();
/* 793 */     entityList1.dereference();
/* 794 */     hashtable1.clear();
/* 795 */     hashtable2.clear();
/*     */     
/* 797 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Hashtable getAttrOfInterest() {
/* 803 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean changeOfInterest(Hashtable paramHashtable1, Hashtable paramHashtable2) {
/* 810 */     boolean bool = false;
/* 811 */     if (paramHashtable1.keySet().containsAll(paramHashtable2.keySet()) && paramHashtable2
/* 812 */       .keySet().containsAll(paramHashtable1.keySet())) {
/*     */       
/* 814 */       addDebug("changeOfInterest: no change in structure found");
/*     */ 
/*     */       
/* 817 */       if (!paramHashtable1.values().containsAll(paramHashtable2.values()) || 
/* 818 */         !paramHashtable2.values().containsAll(paramHashtable1.values())) {
/* 819 */         bool = true;
/* 820 */         addDebug("changeOfInterest: difference in values found: " + NEWLINE + "prev " + paramHashtable2.values() + NEWLINE + "curr " + paramHashtable1
/* 821 */             .values());
/*     */       } else {
/* 823 */         addDebug("changeOfInterest: no change in values found");
/*     */       } 
/*     */     } else {
/*     */       
/* 827 */       bool = true;
/* 828 */       addDebug("changeOfInterest: difference in keysets(structure) found: " + NEWLINE + "prev " + paramHashtable2.keySet() + NEWLINE + "curr " + paramHashtable1
/* 829 */           .keySet());
/*     */     } 
/*     */     
/* 832 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Hashtable getStringRep(EntityList paramEntityList, Hashtable paramHashtable) {
/* 840 */     addDebug(NEWLINE + "getStringRep: entered for " + paramEntityList.getProfile().getValOn());
/* 841 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 842 */     if (paramHashtable == null) {
/* 843 */       addDebug("getStringRep: coding ERROR attrOfInterest hashtable was null");
/* 844 */       return hashtable;
/*     */     } 
/* 846 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 847 */     String[] arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 848 */     if (arrayOfString == null)
/* 849 */       addDebug("getStringRep: No list of 'attr of interest' found for " + entityGroup.getEntityType()); 
/*     */     byte b;
/* 851 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */       
/* 853 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 854 */       String str = this.epimsAbr.generateString(entityItem, arrayOfString);
/*     */ 
/*     */       
/* 857 */       addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 858 */       hashtable.put(entityItem.getKey(), str);
/*     */     } 
/*     */ 
/*     */     
/* 862 */     for (b = 0; b < paramEntityList.getEntityGroupCount(); b++) {
/*     */       
/* 864 */       entityGroup = paramEntityList.getEntityGroup(b);
/* 865 */       arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 866 */       if (arrayOfString == null) {
/* 867 */         addDebug("getStringRep: No list of 'attr of interest' found for " + entityGroup.getEntityType());
/*     */       }
/* 869 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*     */         
/* 871 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/* 872 */         String str = this.epimsAbr.generateString(entityItem, arrayOfString);
/*     */ 
/*     */         
/* 875 */         addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 876 */         hashtable.put(entityItem.getKey(), str);
/*     */       } 
/*     */     } 
/* 879 */     return hashtable;
/*     */   }
/*     */   
/*     */   protected abstract String getVeName();
/*     */   
/*     */   protected abstract void execute() throws Exception;
/*     */   
/*     */   protected abstract void handleResend(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException;
/*     */   
/*     */   public abstract String getVersion();
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\EPIMSABRBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */