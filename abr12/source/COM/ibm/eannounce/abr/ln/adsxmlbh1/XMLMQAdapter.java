/*      */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.Constants;
/*      */ import COM.ibm.eannounce.abr.util.XMLElem;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import com.ibm.eacm.AES256Utils;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.IOException;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.TransformerException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class XMLMQAdapter
/*      */   implements XMLMQ, Constants
/*      */ {
/*      */   protected static final Hashtable ADSTYPES_TBL;
/*      */   private static final boolean isDebug = true;
/*      */   private boolean isVaildREFOFERFEAT = true;
/*      */   private boolean isService = false;
/*      */   protected static final String CHEAT = "@@@";
/*      */   protected static boolean isFilterWWCOMPAT = false;
/*  126 */   protected Hashtable wwcompMQTable = new Hashtable<>();
/*  127 */   private String attrXMLABRPROPFILE = "XMLABRPROPFILE";
/*  128 */   private Profile _swProfile = null;
/*      */   protected static final String KEY_SETUPArry = "SETUPARRAY";
/*  130 */   private EntityList mf_elist = null;
/*      */   static {
/*  132 */     ADSTYPES_TBL = new Hashtable<>();
/*  133 */     ADSTYPES_TBL.put("20", "GENERALAREA");
/*  134 */     ADSTYPES_TBL.put("30", "Deletes");
/*  135 */     ADSTYPES_TBL.put("40", "XLATE");
/*  136 */     ADSTYPES_TBL.put("50", "WWCOMPAT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  144 */     FILTER_TBL = new Hashtable<>();
/*      */ 
/*      */     
/*  147 */     FILTER_TBL.put("FEATURE", new String[] { "STATUS", "FCTYPE", "COUNTRYLIST", "PDHDOMAIN" });
/*  148 */     FILTER_TBL.put("MODEL", new String[] { "STATUS", "SPECBID", "COFCAT", "COFSUBCAT", "COFGRP", "COFSUBGRP", "COUNTRYLIST", "FLFILSYSINDC", "PDHDOMAIN", "DIVTEXT" });
/*  149 */     FILTER_TBL.put("SVCMOD", new String[] { "STATUS", "SVCMODCATG", "SVCMODGRP", "SVCMODSUBCATG", "SVCMODSUBGRP", "COUNTRYLIST", "PDHDOMAIN", "DIVTEXT" });
/*  150 */     FILTER_TBL.put("LSEOBUNDLE", new String[] { "STATUS", "SPECBID", "BUNDLETYPE", "COUNTRYLIST", "FLFILSYSINDC", "PDHDOMAIN", "DIVTEXT" });
/*  151 */     FILTER_TBL.put("LSEO", new String[] { "STATUS", "SPECBID", "COFCAT", "COFSUBCAT", "COFGRP", "COFSUBGRP", "COUNTRYLIST", "FLFILSYSINDC", "PDHDOMAIN", "DIVTEXT" });
/*      */     
/*  153 */     FILTER_TBL.put("PRODSTRUCT", new String[] { "STATUS", "FCTYPE", "MACHTYPEATR", "MODELATR", "COUNTRYLIST", "FLFILSYSINDC", "PDHDOMAIN" });
/*  154 */     FILTER_TBL.put("SWPRODSTRUCT", new String[] { "STATUS", "FCTYPE", "MACHTYPEATR", "MODELATR", "COUNTRYLIST", "PDHDOMAIN" });
/*      */     
/*  156 */     FILTER_TBL.put("MODELCONVERT", new String[] { "STATUS", "MACHTYPEATR", "MODELATR", "COUNTRYLIST", "PDHDOMAIN" });
/*  157 */     FILTER_TBL.put("FCTRANSACTION", new String[] { "STATUS", "MACHTYPEATR", "MODELATR", "PDHDOMAIN" });
/*  158 */     FILTER_TBL.put("IMG", new String[] { "STATUS", "COUNTRYLIST", "PDHDOMAIN" });
/*      */ 
/*      */     
/*  161 */     FILTER_TBL.put("CATNAV", new String[] { "STATUS", "FLFILSYSINDC" });
/*  162 */     FILTER_TBL.put("SWFEATURE", new String[] { "STATUS", "FCTYPE", "PDHDOMAIN" });
/*  163 */     FILTER_TBL.put("GBT", new String[] { "STATUS" });
/*  164 */     FILTER_TBL.put("REVUNBUNDCOMP", new String[] { "STATUS" });
/*  165 */     FILTER_TBL.put("SLEORGNPLNTCODE", new String[] { "STATUS" });
/*      */ 
/*      */     
/*  168 */     FILTER_TBL.put("SVCLEV", new String[] { "STATUS" });
/*  169 */     FILTER_TBL.put("WARR", new String[] { "STATUS" });
/*      */     
/*  171 */     FILTER_TBL.put("WWCOMPAT", new String[] { "BRANDCD" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  176 */     FILTER_TBL.put("REFOFER", new String[] { "STATUS", "COUNTRYLIST", "ENDOFSVC" });
/*  177 */     FILTER_TBL.put("REFOFERFEAT", new String[] { "STATUS", "COUNTRYLIST", "ENDOFSVC" });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Hashtable FILTER_TBL;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String XMLSTATUS = "XMLSTATUS";
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getMQPropertiesFN(EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS) {
/*  192 */     paramADSABRSTATUS.addDebug("countryfilter start");
/*  193 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*  194 */     Vector<String> vector = new Vector();
/*  195 */     if (str != null) {
/*      */       
/*  197 */       StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  198 */       while (stringTokenizer.hasMoreTokens())
/*      */       {
/*  200 */         vector.addElement(stringTokenizer.nextToken());
/*      */       }
/*      */     } 
/*      */     
/*  204 */     Vector vector1 = new Vector();
/*      */     try {
/*  206 */       vector1 = getMQPropertiesFilter(paramEntityItem, paramADSABRSTATUS);
/*  207 */     } catch (Exception exception) {
/*  208 */       paramADSABRSTATUS.addDebug("getMQPropertiesFN error=" + exception.getMessage());
/*  209 */       exception.printStackTrace();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  217 */     if (!this.isVaildREFOFERFEAT) {
/*  218 */       return new Vector();
/*      */     }
/*      */     
/*  221 */     addAllMq(vector, vector1, paramADSABRSTATUS);
/*      */     
/*  223 */     if (vector.size() == 0)
/*      */     {
/*      */       
/*  226 */       vector.add("ADSMQSERIES");
/*      */     }
/*      */     
/*  229 */     paramADSABRSTATUS.addDebug("countryfilter end");
/*  230 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hashtable getMQPropertiesVN(EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS) throws SQLException, MiddlewareException {
/*  238 */     paramADSABRSTATUS.addDebug("countryfilter start");
/*  239 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*  240 */     Vector<String> vector = new Vector();
/*  241 */     if (str != null && paramEntityItem.getEntityType().equals("ADSXMLSETUP")) {
/*      */       
/*  243 */       StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  244 */       while (stringTokenizer.hasMoreTokens())
/*      */       {
/*  246 */         vector.addElement(stringTokenizer.nextToken());
/*      */       }
/*      */     } 
/*  249 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/*  251 */     boolean bool = true;
/*      */     try {
/*  253 */       bool = checkModelVaild(paramEntityItem, paramADSABRSTATUS);
/*  254 */     } catch (SQLException sQLException) {
/*  255 */       paramADSABRSTATUS.addDebug("getMQPropertiesVN error=" + sQLException.getMessage());
/*  256 */       sQLException.printStackTrace();
/*  257 */       throw sQLException;
/*      */     } 
/*  259 */     if (!bool) {
/*  260 */       paramADSABRSTATUS.addOutput("Data is not valid for filter of MODEL based on classification attributes (COFCAT,COFSUBCAT,COFGRP) ");
/*  261 */       return hashtable;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  266 */       hashtable = getMQPropertiesFilterVN(paramEntityItem, paramADSABRSTATUS, vector);
/*  267 */     } catch (Exception exception) {
/*  268 */       if (exception instanceof SBRException) {
/*  269 */         SBRException sBRException = (SBRException)exception;
/*  270 */         paramADSABRSTATUS.addError("getMQPropertiesVN error=" + sBRException.toString());
/*  271 */         exception.printStackTrace();
/*  272 */         MiddlewareException middlewareException1 = new MiddlewareException("getMQPropertiesVN error=" + sBRException.toString());
/*  273 */         throw middlewareException1;
/*      */       } 
/*  275 */       paramADSABRSTATUS.addError("getMQPropertiesVN error=" + exception.getMessage());
/*  276 */       exception.printStackTrace();
/*  277 */       MiddlewareException middlewareException = new MiddlewareException("getMQPropertiesVN error=" + exception.getMessage());
/*  278 */       throw middlewareException;
/*      */     } finally {
/*      */       
/*  281 */       if (this.mf_elist != null) {
/*  282 */         this.mf_elist = null;
/*      */       }
/*      */     } 
/*  285 */     return hashtable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addAllMq(Vector<String> paramVector1, Vector<String> paramVector2, ADSABRSTATUS paramADSABRSTATUS) {
/*  295 */     String str = ""; byte b;
/*  296 */     for (b = 0; b < paramVector2.size(); b++) {
/*  297 */       str = paramVector2.get(b);
/*  298 */       if (!paramVector1.contains(str)) {
/*  299 */         paramVector1.add(str);
/*      */       }
/*      */     } 
/*      */     
/*  303 */     for (b = 0; b < paramVector1.size(); b++) {
/*  304 */       paramADSABRSTATUS.addDebug("Print MQ[" + b + "]= " + paramVector1.get(b));
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
/*      */   private EntityItem getEntityItem(Database paramDatabase, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  318 */     EntityList entityList = paramDatabase.getEntityList(this._swProfile, new ExtractActionItem(null, paramDatabase, this._swProfile, "dummy"), new EntityItem[] { new EntityItem(null, this._swProfile, paramEntityItem
/*      */             
/*  320 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*  321 */     return entityList.getParentEntityGroup().getEntityItem(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String convertValue(String paramString) {
/*  330 */     return (paramString == null) ? "" : paramString;
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
/*      */   private void setRootEntity(EntityItem paramEntityItem, Hashtable<String, String> paramHashtable, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  344 */     String str1 = paramEntityItem.getEntityType();
/*  345 */     String[] arrayOfString = (String[])FILTER_TBL.get(str1);
/*      */     
/*  347 */     String str2 = "";
/*  348 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  349 */       String str = arrayOfString[b];
/*  350 */       if (str.equals("FCTYPE")) {
/*  351 */         if (str1.equals("PRODSTRUCT") || str1.equals("SWPRODSTRUCT")) {
/*  352 */           str2 = getFCTYPE(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } else {
/*  354 */           str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, str));
/*      */         } 
/*  356 */         paramHashtable.put(str, str2);
/*  357 */       } else if (str.equals("STATUS") || str.equals("BUNDLETYPE") || str
/*  358 */         .equals("SVCMODCATG") || str.equals("SVCMODGRP") || str
/*  359 */         .equals("SVCMODSUBCATG") || str.equals("SVCMODSUBGRP") || str
/*  360 */         .equals("FLFILSYSINDC") || str.equals("PDHDOMAIN")) {
/*  361 */         str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, str));
/*  362 */         paramHashtable.put(str, str2);
/*  363 */       } else if (str.equals("COFCAT") || str.equals("COFSUBCAT") || str
/*  364 */         .equals("COFGRP") || str.equals("COFSUBGRP")) {
/*  365 */         if (str1.equals("MODEL")) {
/*  366 */           str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, str));
/*      */         } else {
/*      */           
/*  369 */           str2 = getCOFMODEL(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } 
/*  371 */         if (str.equals("COFCAT") && 
/*  372 */           str2.equals("102")) {
/*  373 */           this.isService = true;
/*      */         }
/*      */         
/*  376 */         paramHashtable.put(str, str2);
/*  377 */       } else if (str.equals("SPECBID")) {
/*  378 */         str2 = convertValue(getSPECBID(paramEntityItem, str1, str, paramADSABRSTATUS));
/*  379 */         paramHashtable.put(str, str2);
/*  380 */       } else if (str.equals("MACHTYPEATR")) {
/*      */         
/*  382 */         if (str1.equals("MODELCONVERT") || str1.equals("FCTRANSACTION")) {
/*  383 */           str2 = convertValue(PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", null, false));
/*      */         } else {
/*      */           
/*  386 */           str2 = getMACHTYPEATR(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } 
/*  388 */         paramHashtable.put(str, str2);
/*  389 */       } else if (str.equals("MODELATR")) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  394 */         if (str1.equals("MODELCONVERT") || str1.equals("FCTRANSACTION")) {
/*  395 */           str2 = getMACHTYPEATR(paramEntityItem, str1, "TOMODEL", paramADSABRSTATUS);
/*      */         } else {
/*  397 */           str2 = getMACHTYPEATR(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } 
/*      */         
/*  400 */         paramHashtable.put(str, str2);
/*  401 */       } else if (str.equals("COUNTRYLIST")) {
/*  402 */         str2 = convertValue(getCOUNTRYLIST(paramEntityItem, str1, str, paramADSABRSTATUS));
/*  403 */         paramHashtable.put(str, str2);
/*  404 */       } else if (str.equals("ENDOFSVC")) {
/*  405 */         if (str1.equals("REFOFER")) {
/*  406 */           str2 = convertValue(PokUtils.getAttributeValue(paramEntityItem, str, "", null, false));
/*  407 */         } else if (str1.equals("REFOFERFEAT")) {
/*  408 */           str2 = getENDOFSVC(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } 
/*  410 */         paramHashtable.put(str, str2);
/*      */       
/*      */       }
/*  413 */       else if (str.equals("DIVTEXT")) {
/*  414 */         str2 = getDIVISION(paramEntityItem, str1, "DIV", paramADSABRSTATUS);
/*  415 */         paramHashtable.put(str, str2);
/*      */       } 
/*      */     } 
/*      */     
/*  419 */     Iterator<String> iterator = paramHashtable.keySet().iterator();
/*  420 */     while (iterator.hasNext()) {
/*  421 */       String str = iterator.next();
/*  422 */       paramADSABRSTATUS.addDebug("rootTable:key=" + str + ";value=" + paramHashtable.get(str));
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
/*      */   private EntityItem[] doSearch(String paramString1, String paramString2, ADSABRSTATUS paramADSABRSTATUS) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MiddlewareBusinessRuleException, MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException, IOException {
/*  454 */     EntityItem[] arrayOfEntityItem = null;
/*      */     
/*  456 */     StringBuffer stringBuffer = new StringBuffer();
/*  457 */     Database database = paramADSABRSTATUS.getDB();
/*      */     
/*  459 */     String str1 = paramADSABRSTATUS.getSimpleABRName(paramString1);
/*  460 */     XMLMQ xMLMQ = (XMLMQ)Class.forName(str1).newInstance();
/*      */     
/*  462 */     Profile profile = null;
/*      */     
/*  464 */     profile = paramADSABRSTATUS.switchRoles(xMLMQ.getRoleCode());
/*  465 */     setSwitchProfile(profile);
/*  466 */     String str2 = "SRDEXTXMLFEED";
/*  467 */     String str3 = "EXTXMLFEED";
/*  468 */     Vector<String> vector1 = new Vector();
/*  469 */     Vector<String> vector2 = new Vector();
/*      */     
/*  471 */     vector1.add("XMLENTITYTYPE");
/*  472 */     vector1.add("XMLSETUPTYPE");
/*  473 */     vector1.add("PDHDOMAIN");
/*  474 */     vector2.add(paramString1);
/*  475 */     vector2.add("Production");
/*  476 */     vector2.add(paramString2);
/*      */     
/*  478 */     paramADSABRSTATUS.addDebug("XMLENTITYTYPE2=" + paramString1);
/*  479 */     paramADSABRSTATUS.addDebug("XMLSETUPTYPE2=Production");
/*      */     try {
/*  481 */       arrayOfEntityItem = ABRUtil.doSearch(database, profile, str2, str3, false, vector1, vector2, stringBuffer);
/*      */       
/*  483 */       paramADSABRSTATUS.addDebug("ABRUtil.doSearch with domain message:" + stringBuffer.toString());
/*  484 */     } catch (Exception exception) {
/*  485 */       paramADSABRSTATUS.addDebug("ABRUtil.doSearch with domain error:" + stringBuffer.toString());
/*  486 */       paramADSABRSTATUS.addDebug("doSearch error=" + exception.getMessage());
/*  487 */       exception.printStackTrace();
/*      */     } 
/*  489 */     paramADSABRSTATUS.addDebug("EXTXMLFEEDArray=" + arrayOfEntityItem.length);
/*  490 */     return arrayOfEntityItem;
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
/*      */   private EntityItem[] doSearch(String paramString, ADSABRSTATUS paramADSABRSTATUS) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MiddlewareBusinessRuleException, MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException, IOException {
/*  523 */     EntityItem[] arrayOfEntityItem = null;
/*      */     
/*  525 */     StringBuffer stringBuffer = new StringBuffer();
/*  526 */     Database database = paramADSABRSTATUS.getDB();
/*      */     
/*  528 */     String str1 = paramADSABRSTATUS.getSimpleABRName(paramString);
/*  529 */     XMLMQ xMLMQ = (XMLMQ)Class.forName(str1).newInstance();
/*      */     
/*  531 */     Profile profile = null;
/*      */     
/*  533 */     profile = paramADSABRSTATUS.switchRoles(xMLMQ.getRoleCode());
/*  534 */     setSwitchProfile(profile);
/*  535 */     String str2 = "SRDEXTXMLFEED";
/*  536 */     String str3 = "EXTXMLFEED";
/*  537 */     Vector<String> vector1 = new Vector();
/*  538 */     Vector<String> vector2 = new Vector();
/*      */     
/*  540 */     vector1.add("XMLENTITYTYPE");
/*  541 */     vector1.add("XMLSETUPTYPE");
/*  542 */     vector2.add(paramString);
/*  543 */     vector2.add("Production");
/*      */     
/*  545 */     paramADSABRSTATUS.addDebug("XMLENTITYTYPE2=" + paramString);
/*  546 */     paramADSABRSTATUS.addDebug("XMLSETUPTYPE2=Production");
/*      */     try {
/*  548 */       arrayOfEntityItem = ABRUtil.doSearch(database, profile, str2, str3, false, vector1, vector2, stringBuffer);
/*      */       
/*  550 */       paramADSABRSTATUS.addDebug("ABRUtil.doSearch no domain message:" + stringBuffer.toString());
/*  551 */     } catch (Exception exception) {
/*  552 */       paramADSABRSTATUS.addDebug("ABRUtil.doSearch no domain error:" + stringBuffer.toString());
/*  553 */       paramADSABRSTATUS.addDebug("doSearch error=" + exception.getMessage());
/*  554 */       exception.printStackTrace();
/*      */     } 
/*  556 */     paramADSABRSTATUS.addDebug("EXTXMLFEEDArray=" + arrayOfEntityItem.length);
/*  557 */     return arrayOfEntityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSwitchProfile(Profile paramProfile) {
/*  564 */     this._swProfile = paramProfile;
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
/*      */   private void setFilterTable(String paramString, Hashtable<String, String> paramHashtable, EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, MiddlewareException {
/*  578 */     String[] arrayOfString = (String[])FILTER_TBL.get(paramString);
/*  579 */     String str1 = "";
/*  580 */     String str2 = "";
/*  581 */     String str3 = "";
/*  582 */     String str4 = "";
/*  583 */     String str5 = "";
/*  584 */     String str6 = "";
/*      */     
/*  586 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  587 */       str1 = arrayOfString[b];
/*  588 */       paramADSABRSTATUS.addDebug("attrcode=" + str1);
/*      */       
/*  590 */       if (str1.equals("MODELATR") || str1.equals("ENDOFSVC") || str1.equals("DIVTEXT")) {
/*  591 */         str2 = convertValue(PokUtils.getAttributeValue(paramEntityItem, str1, "", null, false));
/*  592 */         paramHashtable.put(str1, str2);
/*  593 */       } else if (str1.equals("STATUS")) {
/*      */         
/*  595 */         str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "XMLSTATUS"));
/*  596 */         if (str2.equals("XSTATUS02")) {
/*  597 */           str2 = "0020";
/*      */         } else {
/*  599 */           str2 = "";
/*      */         } 
/*  601 */         paramHashtable.put(str1, str2);
/*      */       } else {
/*  603 */         str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, str1));
/*  604 */         paramHashtable.put(str1, str2);
/*      */       } 
/*      */     } 
/*  607 */     if (paramString.equals("MODEL") || paramString.equals("LSEO")) {
/*      */       
/*  609 */       str3 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFCAT"));
/*  610 */       str4 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFSUBCAT"));
/*  611 */       str5 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFGRP"));
/*  612 */       str6 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFSUBGRP"));
/*      */ 
/*      */       
/*  615 */       if (this.isService) {
/*  616 */         str5 = "";
/*  617 */         str6 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  625 */       else if ("".equals(str5)) {
/*  626 */         str5 = "150";
/*      */       } 
/*      */       
/*  629 */       paramHashtable.put("COFCAT", str3);
/*  630 */       paramHashtable.put("COFSUBCAT", str4);
/*  631 */       paramHashtable.put("COFGRP", str5);
/*  632 */       paramHashtable.put("COFSUBGRP", str6);
/*      */     } 
/*      */     
/*  635 */     Iterator<String> iterator = paramHashtable.keySet().iterator();
/*  636 */     while (iterator.hasNext()) {
/*  637 */       String str = iterator.next();
/*  638 */       paramADSABRSTATUS.addDebug("EXTXMLFEED SetupEntity filterTable:key=" + str + ";value=" + paramHashtable.get(str));
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
/*      */   private Vector getMQPropertiesFilter(EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, InstantiationException, IllegalAccessException, ClassNotFoundException, RemoteException, EANBusinessRuleException, IOException {
/*  663 */     Vector vector = new Vector();
/*      */     
/*  665 */     String str1 = paramEntityItem.getEntityType();
/*  666 */     String[] arrayOfString = (String[])FILTER_TBL.get(str1);
/*  667 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  668 */     String str2 = "";
/*  669 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*      */ 
/*      */ 
/*      */     
/*  673 */     Database database = paramADSABRSTATUS.getDB();
/*  674 */     if (arrayOfString != null) {
/*  675 */       EntityItem[] arrayOfEntityItem = doSearch(str1, paramADSABRSTATUS);
/*      */       
/*  677 */       setRootEntity(paramEntityItem, hashtable1, paramADSABRSTATUS);
/*  678 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  679 */         EntityItem entityItem = arrayOfEntityItem[b];
/*  680 */         entityItem = getEntityItem(database, entityItem);
/*      */ 
/*      */ 
/*      */         
/*  684 */         setFilterTable(str1, hashtable2, entityItem, paramADSABRSTATUS);
/*  685 */         str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*      */ 
/*      */ 
/*      */         
/*  689 */         boolean bool = false;
/*      */         
/*  691 */         String str = "";
/*  692 */         boolean bool1 = true;
/*  693 */         boolean bool2 = true;
/*  694 */         for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*  695 */           str = arrayOfString[b1];
/*  696 */           if (str.equals("BUNDLETYPE") || str.equals("COUNTRYLIST") || str
/*  697 */             .equals("FLFILSYSINDC") || str.equals("PDHDOMAIN") || str.equals("DIVTEXT")) {
/*  698 */             bool1 = false;
/*      */           }
/*  700 */           bool = isVailidCompare((String)hashtable1.get(str), (String)hashtable2.get(str), str, bool1);
/*  701 */           if (!bool) {
/*  702 */             bool2 = false;
/*      */             break;
/*      */           } 
/*      */         } 
/*  706 */         if (bool2) {
/*  707 */           addXMLPropfile(vector, str2);
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  714 */       EntityItem[] arrayOfEntityItem = null;
/*  715 */       if (str1.equals("ADSXMLSETUP")) {
/*  716 */         String str = PokUtils.getAttributeFlagValue(paramEntityItem, "ADSTYPE");
/*  717 */         if (str != null) {
/*  718 */           str1 = (String)ADSTYPES_TBL.get(str);
/*  719 */           if (str1 == null) {
/*  720 */             str1 = "@@@";
/*      */           }
/*      */         } 
/*  723 */       } else if (str1.equals("XMLPRODPRICESETUP")) {
/*  724 */         str1 = "@@@";
/*      */       } 
/*  726 */       if (str1.equals("@@@")) {
/*  727 */         arrayOfEntityItem = null;
/*      */       } else {
/*  729 */         arrayOfEntityItem = doSearch(str1, paramADSABRSTATUS);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  734 */       if (str1.equals("WWCOMPAT")) {
/*  735 */         this.wwcompMQTable = new Hashtable<>();
/*  736 */         if (arrayOfEntityItem.length > 0) {
/*  737 */           isFilterWWCOMPAT = true;
/*  738 */           for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  739 */             EntityItem entityItem = arrayOfEntityItem[b];
/*  740 */             entityItem = getEntityItem(database, entityItem);
/*      */             
/*  742 */             str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*  743 */             String str3 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "BRANDCD"));
/*  744 */             paramADSABRSTATUS.addDebug("fBRANDCD=" + str3);
/*  745 */             paramADSABRSTATUS.addDebug("wwcompat role code=" + this._swProfile.getRoleCode());
/*      */ 
/*      */             
/*  748 */             if (str3.equals("")) str3 = "@@@";
/*      */ 
/*      */             
/*  751 */             Vector<String> vector1 = (Vector)this.wwcompMQTable.get(str3);
/*  752 */             if (vector1 == null) vector1 = new Vector(); 
/*  753 */             StringTokenizer stringTokenizer = new StringTokenizer(str2, "|");
/*  754 */             String str4 = "";
/*  755 */             while (stringTokenizer.hasMoreTokens()) {
/*  756 */               str4 = stringTokenizer.nextToken();
/*  757 */               if (!vector1.contains(str4)) {
/*  758 */                 vector1.add(str4);
/*      */               }
/*      */             } 
/*      */             
/*  762 */             String str5 = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*  763 */             if (str5 != null) {
/*      */               
/*  765 */               StringTokenizer stringTokenizer1 = new StringTokenizer(str5, "|");
/*  766 */               while (stringTokenizer1.hasMoreTokens()) {
/*      */                 
/*  768 */                 str4 = stringTokenizer1.nextToken();
/*  769 */                 if (!vector1.contains(str4)) {
/*  770 */                   vector1.add(str4);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             
/*  775 */             if (vector1.size() == 0) {
/*  776 */               vector1.add("ADSMQSERIES");
/*      */             }
/*  778 */             this.wwcompMQTable.put(str3, vector1);
/*      */           } 
/*      */         } else {
/*      */           
/*  782 */           isFilterWWCOMPAT = false;
/*      */         }
/*      */       
/*  785 */       } else if (arrayOfEntityItem != null) {
/*  786 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  787 */           EntityItem entityItem = arrayOfEntityItem[b];
/*  788 */           entityItem = getEntityItem(database, entityItem);
/*  789 */           String str = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*  790 */           addXMLPropfile(vector, str);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  795 */     paramADSABRSTATUS.addDebug("MQ size =" + vector.size());
/*  796 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Hashtable getMQPropertiesFilterVN(EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS, Vector paramVector) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, InstantiationException, IllegalAccessException, ClassNotFoundException, RemoteException, EANBusinessRuleException, IOException {
/*  804 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*      */ 
/*      */ 
/*      */     
/*  808 */     String str1 = paramEntityItem.getEntityType();
/*  809 */     String[] arrayOfString = (String[])FILTER_TBL.get(str1);
/*  810 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  811 */     String str2 = "";
/*  812 */     Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*      */ 
/*      */     
/*  815 */     String str3 = "";
/*  816 */     String str4 = "";
/*  817 */     String str5 = "";
/*      */ 
/*      */     
/*  820 */     EntityItem[] arrayOfEntityItem = null;
/*  821 */     Vector<EntityItem> vector = new Vector();
/*      */     
/*  823 */     Database database = paramADSABRSTATUS.getDB();
/*  824 */     if (arrayOfString != null) {
/*      */       
/*  826 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN");
/*  827 */       paramADSABRSTATUS.addDebug(paramEntityItem.getKey() + " pdhdomain: " + str);
/*  828 */       if (str != null) {
/*  829 */         arrayOfEntityItem = doSearch(str1, str, paramADSABRSTATUS);
/*      */       } else {
/*  831 */         arrayOfEntityItem = doSearch(str1, paramADSABRSTATUS);
/*      */       } 
/*      */       
/*  834 */       setRootEntity(paramEntityItem, hashtable2, paramADSABRSTATUS);
/*  835 */       if (arrayOfEntityItem != null) {
/*  836 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  837 */           EntityItem entityItem = arrayOfEntityItem[b];
/*  838 */           entityItem = getEntityItem(database, entityItem);
/*      */ 
/*      */ 
/*      */           
/*  842 */           setFilterTable(str1, hashtable3, entityItem, paramADSABRSTATUS);
/*  843 */           str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*      */ 
/*      */ 
/*      */           
/*  847 */           boolean bool = false;
/*      */           
/*  849 */           String str6 = "";
/*  850 */           boolean bool1 = true;
/*  851 */           boolean bool2 = true;
/*  852 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*  853 */             str6 = arrayOfString[b1];
/*  854 */             if (str6.equals("BUNDLETYPE") || str6.equals("COUNTRYLIST") || str6
/*  855 */               .equals("FLFILSYSINDC") || str6.equals("PDHDOMAIN") || str6.equals("DIVTEXT")) {
/*  856 */               bool1 = false;
/*      */             }
/*  858 */             bool = isVailidCompare((String)hashtable2.get(str6), (String)hashtable3.get(str6), str6, bool1);
/*  859 */             if (!bool) {
/*  860 */               bool2 = false;
/*      */               break;
/*      */             } 
/*      */           } 
/*  864 */           if (bool2) {
/*  865 */             str3 = convertValue(PokUtils.getAttributeValue(entityItem, "XMLVERSION", "", null, false));
/*  866 */             str4 = convertValue(PokUtils.getAttributeValue(entityItem, "XMLMOD", "", null, false));
/*  867 */             if (!"".equals(str3) && !"".equals(str4) && this.isVaildREFOFERFEAT) {
/*  868 */               str5 = str3 + str4;
/*  869 */               if (hashtable1.containsKey(str5)) {
/*  870 */                 paramVector = (Vector)hashtable1.get(str5);
/*  871 */                 addXMLPropfile(paramVector, str2);
/*  872 */                 hashtable1.put(str5, paramVector);
/*      */               } else {
/*  874 */                 paramVector = new Vector();
/*  875 */                 addXMLPropfile(paramVector, str2);
/*  876 */                 hashtable1.put(str5, paramVector);
/*      */               } 
/*      */               
/*  879 */               vector.add(entityItem);
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  890 */       if (str1.equals("ADSXMLSETUP")) {
/*  891 */         String str = PokUtils.getAttributeFlagValue(paramEntityItem, "ADSTYPE");
/*  892 */         if (str != null) {
/*  893 */           str1 = (String)ADSTYPES_TBL.get(str);
/*  894 */           if (str1 == null) {
/*  895 */             str1 = "@@@";
/*      */           }
/*      */         } 
/*  898 */       } else if (str1.equals("XMLPRODPRICESETUP")) {
/*  899 */         str1 = "@@@";
/*      */       } 
/*  901 */       if (str1.equals("@@@")) {
/*  902 */         arrayOfEntityItem = null;
/*      */       } else {
/*  904 */         arrayOfEntityItem = doSearch(str1, paramADSABRSTATUS);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  911 */       if (str1.equals("WWCOMPAT")) {
/*  912 */         this.wwcompMQTable = new Hashtable<>();
/*  913 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  914 */           isFilterWWCOMPAT = true;
/*  915 */           for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  916 */             EntityItem entityItem = arrayOfEntityItem[b];
/*  917 */             entityItem = getEntityItem(database, entityItem);
/*      */             
/*  919 */             str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*  920 */             String str6 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "BRANDCD"));
/*  921 */             paramADSABRSTATUS.addDebug("fBRANDCD=" + str6);
/*  922 */             paramADSABRSTATUS.addDebug("wwcompat role code=" + this._swProfile.getRoleCode());
/*      */ 
/*      */             
/*  925 */             if (str6.equals("")) str6 = "@@@";
/*      */ 
/*      */             
/*  928 */             Vector<String> vector1 = (Vector)this.wwcompMQTable.get(str6);
/*  929 */             if (vector1 == null) vector1 = new Vector(); 
/*  930 */             StringTokenizer stringTokenizer = new StringTokenizer(str2, "|");
/*  931 */             String str7 = "";
/*  932 */             while (stringTokenizer.hasMoreTokens()) {
/*  933 */               str7 = stringTokenizer.nextToken();
/*  934 */               if (!vector1.contains(str7)) {
/*  935 */                 vector1.add(str7);
/*      */               }
/*      */             } 
/*      */             
/*  939 */             String str8 = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*  940 */             if (str8 != null) {
/*      */               
/*  942 */               StringTokenizer stringTokenizer1 = new StringTokenizer(str8, "|");
/*  943 */               while (stringTokenizer1.hasMoreTokens()) {
/*      */                 
/*  945 */                 str7 = stringTokenizer1.nextToken();
/*  946 */                 if (!vector1.contains(str7)) {
/*  947 */                   vector1.add(str7);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             
/*  952 */             if (vector1.size() == 0) {
/*  953 */               vector1.add("ADSMQSERIES");
/*      */             }
/*  955 */             this.wwcompMQTable.put(str6, vector1);
/*      */           } 
/*      */         } else {
/*      */           
/*  959 */           isFilterWWCOMPAT = false;
/*      */         }
/*      */       
/*  962 */       } else if (arrayOfEntityItem != null) {
/*  963 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  964 */           EntityItem entityItem = arrayOfEntityItem[b];
/*  965 */           entityItem = getEntityItem(database, entityItem);
/*  966 */           String str = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*  967 */           str3 = convertValue(PokUtils.getAttributeValue(entityItem, "XMLVERSION", "", null, false));
/*  968 */           str4 = convertValue(PokUtils.getAttributeValue(entityItem, "XMLMOD", "", null, false));
/*  969 */           if (!"".equals(str3) && !"".equals(str4) && this.isVaildREFOFERFEAT) {
/*  970 */             str5 = str3 + str4;
/*  971 */             if (hashtable1.containsKey(str5)) {
/*  972 */               paramVector = (Vector)hashtable1.get(str5);
/*  973 */               addXMLPropfile(paramVector, str);
/*  974 */               hashtable1.put(str5, paramVector);
/*      */             } else {
/*      */               
/*  977 */               addXMLPropfile(paramVector, str);
/*  978 */               hashtable1.put(str5, paramVector);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  987 */     hashtable1.put("SETUPARRAY", vector);
/*      */ 
/*      */     
/*  990 */     paramADSABRSTATUS.addDebug("MQ Table =" + hashtable1.size());
/*  991 */     if (hashtable1.containsKey("10")) {
/*  992 */       paramADSABRSTATUS.addDebug("MQ Table 10 =" + hashtable1.get("10").toString());
/*      */     } else {
/*  994 */       paramADSABRSTATUS.addDebug("MQ Table 10 is null ");
/*      */     } 
/*  996 */     if (hashtable1.containsKey("05")) {
/*  997 */       paramADSABRSTATUS.addDebug("MQ Table 05 =" + hashtable1.get("05").toString());
/*      */     } else {
/*  999 */       paramADSABRSTATUS.addDebug("MQ Table 05 is null ");
/*      */     } 
/* 1001 */     return hashtable1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addXMLPropfile(Vector<String> paramVector, String paramString) {
/* 1010 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "|");
/* 1011 */     String str = "";
/* 1012 */     while (stringTokenizer.hasMoreTokens()) {
/* 1013 */       str = stringTokenizer.nextToken();
/* 1014 */       if (!paramVector.contains(str)) {
/* 1015 */         paramVector.add(str);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVailidCompare(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1024 */     boolean bool = false;
/* 1025 */     if (paramString1 == null) paramString1 = "";
/*      */ 
/*      */     
/* 1028 */     if (paramString1.equals("@@@")) return true; 
/* 1029 */     if (paramString2 != null && !"".equals(paramString2)) {
/* 1030 */       if ("ENDOFSVC".equals(paramString3)) {
/*      */         
/* 1032 */         if ("".equals(paramString1))
/* 1033 */           return true; 
/* 1034 */         if (paramString1.compareTo(paramString2) >= 0) {
/* 1035 */           return true;
/*      */         }
/*      */       }
/* 1038 */       else if (paramBoolean) {
/* 1039 */         if (paramString2.equals(paramString1)) {
/* 1040 */           bool = true;
/*      */         } else {
/* 1042 */           bool = false;
/*      */         } 
/*      */       } else {
/*      */         
/* 1046 */         String str1 = "";
/* 1047 */         if ("DIVTEXT".equals(paramString3)) {
/* 1048 */           str1 = ",";
/*      */         } else {
/* 1050 */           str1 = "|";
/*      */         } 
/* 1052 */         StringTokenizer stringTokenizer = new StringTokenizer(paramString2, str1);
/* 1053 */         String str2 = "";
/* 1054 */         String str3 = "";
/* 1055 */         while (stringTokenizer.hasMoreTokens()) {
/* 1056 */           str2 = stringTokenizer.nextToken();
/* 1057 */           if ("".equals(str2)) return true; 
/* 1058 */           StringTokenizer stringTokenizer1 = new StringTokenizer(paramString1, "|");
/* 1059 */           while (stringTokenizer1.hasMoreTokens()) {
/* 1060 */             str3 = stringTokenizer1.nextToken();
/* 1061 */             if (str3.equals(str2)) {
/* 1062 */               return true;
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1070 */       bool = true;
/*      */     } 
/*      */     
/* 1073 */     return bool;
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
/*      */   private String getCOUNTRYLIST(EntityItem paramEntityItem, String paramString1, String paramString2, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1086 */     String str = "";
/* 1087 */     if (paramString1.equals("FEATURE") || paramString1.equals("IMG") || paramString1
/* 1088 */       .equals("LSEOBUNDLE") || paramString1.equals("LSEO")) {
/* 1089 */       str = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, paramString2));
/* 1090 */     } else if (paramString1.equals("REFOFER")) {
/* 1091 */       str = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, paramString2));
/*      */       
/* 1093 */       if (str.equals("")) {
/* 1094 */         str = "@@@";
/*      */       
/*      */       }
/*      */     }
/* 1098 */     else if (paramString1.equals("REFOFERFEAT")) {
/* 1099 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/* 1100 */       EntityGroup entityGroup = entityList.getEntityGroup("REFOFER");
/* 1101 */       EntityItem[] arrayOfEntityItem = null;
/* 1102 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */       
/* 1104 */       if (arrayOfEntityItem != null) {
/* 1105 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1106 */           EntityItem entityItem = arrayOfEntityItem[b];
/* 1107 */           if (entityItem != null && "REFOFER".equals(entityItem.getEntityType())) {
/* 1108 */             String str1 = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */             
/* 1110 */             if (str1.equals("")) {
/* 1111 */               str = "@@@";
/*      */               break;
/*      */             } 
/* 1114 */             if ("".equals(str)) {
/* 1115 */               str = str1;
/*      */             } else {
/* 1117 */               str = str + "|" + str1;
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } 
/*      */       }
/* 1124 */     } else if (paramString1.equals("PRODSTRUCT")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1132 */       EntityItem entityItem = null;
/* 1133 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */       
/* 1135 */       EntityGroup entityGroup = entityList.getEntityGroup("FEATURE");
/* 1136 */       EntityItem[] arrayOfEntityItem = null;
/* 1137 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1138 */       if (arrayOfEntityItem != null) {
/* 1139 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1140 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1141 */           if (entityItem1 != null && "FEATURE".equals(entityItem1.getEntityType())) {
/* 1142 */             entityItem = entityItem1;
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1150 */       String str1 = "";
/* 1151 */       if (entityItem != null) {
/* 1152 */         str1 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "FCTYPE"));
/* 1153 */         if (str1.equals("100") || str1.equals("110")) {
/* 1154 */           str = getCountryList(paramEntityItem, paramString1, paramString2, paramADSABRSTATUS);
/*      */         } else {
/* 1156 */           str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/* 1162 */       str = getCountryList(paramEntityItem, paramString1, paramString2, paramADSABRSTATUS);
/*      */     } 
/* 1164 */     return str;
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
/*      */   private String getCountryList(EntityItem paramEntityItem, String paramString1, String paramString2, ADSABRSTATUS paramADSABRSTATUS) throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 1184 */     String str = "";
/* 1185 */     EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */     
/* 1187 */     EntityGroup entityGroup = entityList.getEntityGroup("AVAIL");
/* 1188 */     EntityItem[] arrayOfEntityItem = null;
/* 1189 */     if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1190 */     if (arrayOfEntityItem == null || arrayOfEntityItem.length == 0) {
/* 1191 */       paramADSABRSTATUS.addDebug("avail is null");
/* 1192 */       str = "@@@";
/*      */     } else {
/*      */       
/* 1195 */       paramADSABRSTATUS.addDebug("avail is not null");
/* 1196 */       EntityItem entityItem = null;
/* 1197 */       StringBuffer stringBuffer = new StringBuffer();
/* 1198 */       String str1 = "";
/* 1199 */       String str2 = "";
/* 1200 */       String str3 = "";
/* 1201 */       boolean bool1 = false;
/* 1202 */       boolean bool2 = false;
/* 1203 */       byte b1 = 0;
/* 1204 */       for (byte b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/* 1205 */         entityItem = arrayOfEntityItem[b2];
/*      */         
/* 1207 */         str1 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE"));
/*      */         
/* 1209 */         str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "STATUS"));
/*      */         
/* 1211 */         if (str1.equals("146")) {
/*      */           
/* 1213 */           str3 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "@@@", false);
/* 1214 */           if (str3.compareTo("2010-03-01") <= 0) {
/* 1215 */             bool1 = true;
/*      */           } else {
/* 1217 */             bool1 = false;
/*      */           } 
/* 1219 */           if (bool1) {
/* 1220 */             bool2 = true;
/* 1221 */           } else if ("0020".equals(str2) || "0040".equals(str2)) {
/* 1222 */             bool2 = true;
/*      */           } else {
/* 1224 */             bool2 = false;
/*      */           } 
/* 1226 */           if (bool2) {
/* 1227 */             if (b1) stringBuffer.append("|"); 
/* 1228 */             stringBuffer.append(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/* 1229 */             b1++;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1233 */       str = stringBuffer.toString();
/* 1234 */       if (str.equals("")) {
/* 1235 */         str = "@@@";
/*      */       }
/*      */     } 
/* 1238 */     return str;
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
/*      */   private String getDIVISION(EntityItem paramEntityItem, String paramString1, String paramString2, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1253 */     String str = "";
/*      */     
/* 1255 */     EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */     
/* 1257 */     paramADSABRSTATUS.addDebug("getDIVISION: m_elist=" + entityList);
/*      */     
/* 1259 */     EntityGroup entityGroup = entityList.getEntityGroup("SGMNTACRNYM");
/*      */     
/* 1261 */     EntityItem[] arrayOfEntityItem = null;
/* 1262 */     if (entityGroup != null) {
/* 1263 */       arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/* 1264 */       paramADSABRSTATUS.addDebug("getDIVISIONÃ¯Â¼Å¡ itemArray=" + arrayOfEntityItem.length);
/*      */     } 
/* 1266 */     byte b1 = 0;
/* 1267 */     for (byte b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/* 1268 */       EntityItem entityItem = arrayOfEntityItem[b2];
/* 1269 */       if (entityItem != null) {
/* 1270 */         b1++;
/* 1271 */         if (b1 == 1) {
/* 1272 */           str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */         } else {
/* 1274 */           str = str + "|" + convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */         } 
/*      */       } 
/*      */     } 
/* 1278 */     paramADSABRSTATUS.addDebug("getDIVISIONÃ¯Â¼Å¡end");
/* 1279 */     return str;
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
/*      */   private String getMACHTYPEATR(EntityItem paramEntityItem, String paramString1, String paramString2, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1294 */     String str = "";
/* 1295 */     if (paramString1.equals("PRODSTRUCT") || paramString1.equals("SWPRODSTRUCT")) {
/*      */       
/* 1297 */       EntityItem entityItem = null;
/* 1298 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */       
/* 1300 */       EntityGroup entityGroup = entityList.getEntityGroup("MODEL");
/* 1301 */       paramADSABRSTATUS.addDebug("mdlGrp=" + entityGroup);
/* 1302 */       EntityItem[] arrayOfEntityItem = null;
/* 1303 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1304 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1305 */         EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1306 */         if (entityItem1 != null && "MODEL".equals(entityItem1.getEntityType())) {
/* 1307 */           entityItem = entityItem1;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1311 */       if (entityItem != null) {
/* 1312 */         if (paramString2.equals("MODELATR")) {
/* 1313 */           str = convertValue(PokUtils.getAttributeValue(entityItem, paramString2, "", null, false));
/*      */         } else {
/* 1315 */           str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */         } 
/*      */         
/* 1318 */         paramADSABRSTATUS.addDebug("get model attrcode=" + paramString2 + "modelItem =" + entityItem.getEntityID() + ";attrvalue=" + str);
/*      */       } 
/*      */     } else {
/* 1321 */       str = "";
/*      */     } 
/* 1323 */     return str;
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
/*      */   private String getENDOFSVC(EntityItem paramEntityItem, String paramString1, String paramString2, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1338 */     String str = "@@@";
/* 1339 */     if (paramString1.equals("REFOFERFEAT")) {
/*      */       
/* 1341 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/* 1342 */       EntityGroup entityGroup = entityList.getEntityGroup("REFOFER");
/* 1343 */       EntityItem[] arrayOfEntityItem = null;
/* 1344 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1356 */       if (arrayOfEntityItem != null) {
/* 1357 */         this.isVaildREFOFERFEAT = true;
/* 1358 */         String str1 = "";
/* 1359 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1360 */           EntityItem entityItem = arrayOfEntityItem[b];
/* 1361 */           if (entityItem != null && "REFOFER".equals(entityItem.getEntityType())) {
/* 1362 */             str1 = convertValue(PokUtils.getAttributeValue(entityItem, "ENDOFSVC", "", null, false));
/* 1363 */             if (str.equals("@@@")) {
/* 1364 */               str = str1;
/*      */             }
/* 1366 */             if ("".equals(str) || "".equals(str1)) {
/* 1367 */               str = "@@@"; break;
/*      */             } 
/* 1369 */             if (str.compareTo(str1) <= 0)
/*      */             {
/* 1371 */               if (str.compareTo(str1) <= 0)
/* 1372 */                 str = str1; 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1377 */         str = "";
/* 1378 */         this.isVaildREFOFERFEAT = false;
/*      */       } 
/*      */     } else {
/* 1381 */       str = "";
/*      */     } 
/* 1383 */     return str;
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
/*      */   private EntityList getEntityList(EntityItem paramEntityItem, String paramString, ADSABRSTATUS paramADSABRSTATUS) throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 1396 */     if (this.mf_elist != null) {
/* 1397 */       return this.mf_elist;
/*      */     }
/* 1399 */     String str = "ADF" + paramString;
/* 1400 */     Database database = paramADSABRSTATUS.getDB();
/* 1401 */     Profile profile = paramADSABRSTATUS.getProfile();
/* 1402 */     this.mf_elist = database.getEntityList(profile, new ExtractActionItem(null, database, profile, str), new EntityItem[] { new EntityItem(null, profile, paramEntityItem
/*      */             
/* 1404 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */     
/* 1406 */     return this.mf_elist;
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
/*      */   private String getFCTYPE(EntityItem paramEntityItem, String paramString1, String paramString2, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1421 */     String str1 = "";
/* 1422 */     EntityItem entityItem = null;
/* 1423 */     EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */     
/* 1425 */     String str2 = paramString1.equals("PRODSTRUCT") ? "FEATURE" : "SWFEATURE";
/*      */     
/* 1427 */     EntityGroup entityGroup = entityList.getEntityGroup(str2);
/* 1428 */     EntityItem[] arrayOfEntityItem = null;
/* 1429 */     if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1430 */     for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1431 */       EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1432 */       if (entityItem1 != null && str2.equals(entityItem1.getEntityType())) {
/* 1433 */         entityItem = entityItem1;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1437 */     if (entityItem != null) {
/* 1438 */       str1 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "FCTYPE"));
/*      */     }
/* 1440 */     return str1;
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
/*      */   private String getCOFMODEL(EntityItem paramEntityItem, String paramString1, String paramString2, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1453 */     String str = "";
/* 1454 */     EntityItem entityItem = null;
/* 1455 */     if (paramString1.equals("LSEO")) {
/* 1456 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */       
/* 1458 */       EntityGroup entityGroup = entityList.getEntityGroup("MODEL");
/* 1459 */       paramADSABRSTATUS.addDebug("mdlGrp=" + entityGroup);
/* 1460 */       EntityItem[] arrayOfEntityItem = null;
/* 1461 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1462 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1463 */         EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1464 */         if (entityItem1 != null && "MODEL".equals(entityItem1.getEntityType())) {
/* 1465 */           entityItem = entityItem1;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1469 */       if (entityItem != null) {
/* 1470 */         str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */       }
/*      */     } else {
/*      */       
/* 1474 */       str = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, paramString2));
/*      */     } 
/* 1476 */     return str;
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
/*      */   private String getSPECBID(EntityItem paramEntityItem, String paramString1, String paramString2, ADSABRSTATUS paramADSABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1489 */     String str = "";
/* 1490 */     if (paramString1.equals("LSEO")) {
/*      */       
/* 1492 */       EntityItem entityItem = null;
/* 1493 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */       
/* 1495 */       EntityGroup entityGroup = entityList.getEntityGroup("WWSEO");
/* 1496 */       paramADSABRSTATUS.addDebug("mdlGrp=" + entityGroup);
/* 1497 */       EntityItem[] arrayOfEntityItem = null;
/* 1498 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1499 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1500 */         EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1501 */         if (entityItem1 != null && "WWSEO".equals(entityItem1.getEntityType())) {
/* 1502 */           entityItem = entityItem1;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1506 */       if (entityItem != null) {
/* 1507 */         str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */       }
/*      */     } else {
/*      */       
/* 1511 */       str = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, paramString2));
/*      */     } 
/* 1513 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean checkIDLMQPropertiesFN(EntityItem paramEntityItem) {
/* 1518 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/* 1519 */     return (str != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getPeriodicMQ(EntityItem paramEntityItem) {
/* 1528 */     Vector<String> vector = new Vector();
/* 1529 */     String str1 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE));
/* 1530 */     StringTokenizer stringTokenizer = new StringTokenizer(str1, "|");
/* 1531 */     String str2 = "";
/* 1532 */     while (stringTokenizer.hasMoreTokens()) {
/* 1533 */       str2 = stringTokenizer.nextToken();
/* 1534 */       vector.add(str2);
/*      */     } 
/* 1536 */     if (vector.size() == 0) vector = null; 
/* 1537 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean createXML(EntityItem paramEntityItem) {
/* 1546 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLElem getXMLMap() {
/* 1551 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVeName() {
/* 1556 */     return "dummy";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVeName2() {
/* 1561 */     return "dummy";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRoleCode() {
/* 1567 */     return "BHFEED";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getStatusAttr() {
/* 1572 */     return "";
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
/*      */   public String getVersion() {
/* 1585 */     return "";
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
/*      */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Connection setupConnection() throws SQLException {
/* 1609 */     Connection connection = null;
/*      */     try {
/* 1611 */       connection = DriverManager.getConnection(
/* 1612 */           MiddlewareServerProperties.getPDHDatabaseURL(), 
/* 1613 */           MiddlewareServerProperties.getPDHDatabaseUser(), 
/* 1614 */           AES256Utils.decrypt(MiddlewareServerProperties.getPDHDatabasePassword()));
/* 1615 */     } catch (SQLException sQLException) {
/*      */       
/* 1617 */       throw sQLException;
/*      */     }
/* 1619 */     catch (Exception exception) {
/*      */       
/* 1621 */       exception.printStackTrace();
/*      */     } 
/* 1623 */     connection.setAutoCommit(false);
/*      */     
/* 1625 */     return connection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeConnection(Connection paramConnection) throws SQLException {
/* 1632 */     if (paramConnection != null) {
/*      */       try {
/* 1634 */         paramConnection.rollback();
/*      */       }
/* 1636 */       catch (Throwable throwable) {
/* 1637 */         System.err.println("XMLMQAdapter.closeConnection(), unable to rollback. " + throwable);
/*      */       } finally {
/*      */         
/* 1640 */         paramConnection.close();
/* 1641 */         paramConnection = null;
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
/*      */   protected void mergeLists(ADSABRSTATUS paramADSABRSTATUS, EntityList paramEntityList1, EntityList paramEntityList2) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkModelVaild(EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS) throws SQLException {
/* 1676 */     boolean bool = false;
/* 1677 */     String str1 = paramEntityItem.getEntityType();
/* 1678 */     if (!"MODEL".equals(str1)) {
/* 1679 */       return true;
/*      */     }
/* 1681 */     String str2 = " select count(*) as count from opicm.filter_model where \r\n (cofcat=? or cofcat='*') and                         \r\n (cofsubcat=? or cofsubcat='*') and                   \r\n (cofgrp=? or cofgrp='*') with ur                     \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1686 */     ResultSet resultSet = null;
/* 1687 */     Connection connection = null;
/* 1688 */     PreparedStatement preparedStatement = null;
/* 1689 */     int i = 0;
/*      */     try {
/* 1691 */       connection = setupConnection();
/* 1692 */       preparedStatement = connection.prepareStatement(str2);
/* 1693 */       preparedStatement.setString(1, convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFCAT")));
/* 1694 */       preparedStatement.setString(2, convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFSUBCAT")));
/* 1695 */       preparedStatement.setString(3, convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFGRP")));
/* 1696 */       resultSet = preparedStatement.executeQuery();
/* 1697 */       if (resultSet.next()) {
/* 1698 */         i = resultSet.getInt("count");
/* 1699 */         if (i > 0) {
/* 1700 */           bool = true;
/*      */         } else {
/* 1702 */           bool = false;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       try {
/* 1707 */         if (preparedStatement != null) {
/* 1708 */           preparedStatement.close();
/* 1709 */           preparedStatement = null;
/*      */         } 
/* 1711 */       } catch (Exception exception) {
/* 1712 */         paramADSABRSTATUS.addDebug("getPriced unable to close statement. " + exception);
/*      */       } 
/* 1714 */       if (resultSet != null) {
/* 1715 */         resultSet.close();
/*      */       }
/* 1717 */       closeConnection(connection);
/*      */     } 
/* 1719 */     return bool;
/*      */   }
/*      */   
/*      */   public abstract String getMQCID();
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\XMLMQAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */