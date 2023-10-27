/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
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
/*  136 */   protected Hashtable wwcompMQTable = new Hashtable<>();
/*  137 */   private String attrXMLABRPROPFILE = "XMLABRPROPFILE";
/*  138 */   private Profile _swProfile = null;
/*      */   protected static final String KEY_SETUPArry = "SETUPARRAY";
/*  140 */   private EntityList mf_elist = null;
/*      */   static {
/*  142 */     ADSTYPES_TBL = new Hashtable<>();
/*  143 */     ADSTYPES_TBL.put("20", "GENERALAREA");
/*  144 */     ADSTYPES_TBL.put("30", "Deletes");
/*  145 */     ADSTYPES_TBL.put("40", "XLATE");
/*  146 */     ADSTYPES_TBL.put("50", "WWCOMPAT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  154 */     FILTER_TBL = new Hashtable<>();
/*      */ 
/*      */     
/*  157 */     FILTER_TBL.put("FEATURE", new String[] { "STATUS", "FCTYPE", "COUNTRYLIST", "PDHDOMAIN" });
/*  158 */     FILTER_TBL.put("MODEL", new String[] { "STATUS", "SPECBID", "COFCAT", "COFSUBCAT", "COFGRP", "COFSUBGRP", "COUNTRYLIST", "FLFILSYSINDC", "PDHDOMAIN", "DIVTEXT", "OLDINDC" });
/*  159 */     FILTER_TBL.put("SVCMOD", new String[] { "STATUS", "SVCMODCATG", "SVCMODGRP", "SVCMODSUBCATG", "SVCMODSUBGRP", "COUNTRYLIST", "PDHDOMAIN", "DIVTEXT" });
/*  160 */     FILTER_TBL.put("LSEOBUNDLE", new String[] { "STATUS", "SPECBID", "BUNDLETYPE", "COUNTRYLIST", "FLFILSYSINDC", "PDHDOMAIN", "DIVTEXT" });
/*  161 */     FILTER_TBL.put("LSEO", new String[] { "STATUS", "SPECBID", "COFCAT", "COFSUBCAT", "COFGRP", "COFSUBGRP", "COUNTRYLIST", "FLFILSYSINDC", "PDHDOMAIN", "DIVTEXT" });
/*      */     
/*  163 */     FILTER_TBL.put("PRODSTRUCT", new String[] { "STATUS", "FCTYPE", "MACHTYPEATR", "MODELATR", "COUNTRYLIST", "FLFILSYSINDC", "PDHDOMAIN", "OLDINDC" });
/*  164 */     FILTER_TBL.put("SWPRODSTRUCT", new String[] { "STATUS", "FCTYPE", "MACHTYPEATR", "MODELATR", "COUNTRYLIST", "PDHDOMAIN" });
/*      */     
/*  166 */     FILTER_TBL.put("MODELCONVERT", new String[] { "STATUS", "MACHTYPEATR", "MODELATR", "COUNTRYLIST", "PDHDOMAIN" });
/*  167 */     FILTER_TBL.put("FCTRANSACTION", new String[] { "STATUS", "MACHTYPEATR", "MODELATR", "PDHDOMAIN" });
/*  168 */     FILTER_TBL.put("IMG", new String[] { "STATUS", "COUNTRYLIST", "PDHDOMAIN" });
/*      */ 
/*      */     
/*  171 */     FILTER_TBL.put("CATNAV", new String[] { "STATUS", "FLFILSYSINDC" });
/*  172 */     FILTER_TBL.put("SWFEATURE", new String[] { "STATUS", "FCTYPE", "PDHDOMAIN" });
/*  173 */     FILTER_TBL.put("GBT", new String[] { "STATUS" });
/*  174 */     FILTER_TBL.put("REVUNBUNDCOMP", new String[] { "STATUS" });
/*  175 */     FILTER_TBL.put("SLEORGNPLNTCODE", new String[] { "STATUS" });
/*      */ 
/*      */     
/*  178 */     FILTER_TBL.put("SVCLEV", new String[] { "STATUS" });
/*  179 */     FILTER_TBL.put("WARR", new String[] { "STATUS" });
/*      */     
/*  181 */     FILTER_TBL.put("WWCOMPAT", new String[] { "BRANDCD" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  186 */     FILTER_TBL.put("REFOFER", new String[] { "STATUS", "COUNTRYLIST", "ENDOFSVC", "DCG" });
/*  187 */     FILTER_TBL.put("REFOFERFEAT", new String[] { "STATUS", "COUNTRYLIST", "ENDOFSVC" });
/*      */ 
/*      */     
/*  190 */     FILTER_TBL.put("SWSPRODSTRUCT", new String[] { "STATUS" });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Hashtable FILTER_TBL;
/*      */ 
/*      */   
/*      */   private static final String XMLSTATUS = "XMLSTATUS";
/*      */ 
/*      */   
/*      */   public Vector getMQPropertiesFN(EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS) {
/*  202 */     paramADSABRSTATUS.addDebug("countryfilter start");
/*  203 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*  204 */     Vector<String> vector = new Vector();
/*  205 */     if (str != null) {
/*      */       
/*  207 */       StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  208 */       while (stringTokenizer.hasMoreTokens())
/*      */       {
/*  210 */         vector.addElement(stringTokenizer.nextToken());
/*      */       }
/*      */     } 
/*      */     
/*  214 */     Vector vector1 = new Vector();
/*      */     try {
/*  216 */       vector1 = getMQPropertiesFilter(paramEntityItem, paramADSABRSTATUS);
/*  217 */     } catch (Exception exception) {
/*  218 */       paramADSABRSTATUS.addDebug("getMQPropertiesFN error=" + exception.getMessage());
/*  219 */       exception.printStackTrace();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  227 */     if (!this.isVaildREFOFERFEAT) {
/*  228 */       return new Vector();
/*      */     }
/*      */     
/*  231 */     addAllMq(vector, vector1, paramADSABRSTATUS);
/*      */     
/*  233 */     if (vector.size() == 0)
/*      */     {
/*      */       
/*  236 */       vector.add("ADSMQSERIES");
/*      */     }
/*      */     
/*  239 */     paramADSABRSTATUS.addDebug("countryfilter end");
/*  240 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hashtable getMQPropertiesVN(EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS) throws SQLException, MiddlewareException {
/*  248 */     paramADSABRSTATUS.addDebug("countryfilter start");
/*  249 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*  250 */     Vector<String> vector = new Vector();
/*  251 */     if (str != null && paramEntityItem.getEntityType().equals("ADSXMLSETUP")) {
/*      */       
/*  253 */       StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  254 */       while (stringTokenizer.hasMoreTokens())
/*      */       {
/*  256 */         vector.addElement(stringTokenizer.nextToken());
/*      */       }
/*      */     } 
/*  259 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/*  261 */     boolean bool = true;
/*      */     try {
/*  263 */       bool = checkModelVaild(paramEntityItem, paramADSABRSTATUS);
/*  264 */     } catch (SQLException sQLException) {
/*  265 */       paramADSABRSTATUS.addDebug("getMQPropertiesVN error=" + sQLException.getMessage());
/*  266 */       sQLException.printStackTrace();
/*  267 */       throw sQLException;
/*      */     } 
/*  269 */     if (!bool) {
/*  270 */       paramADSABRSTATUS.addOutput("Data is not valid for filter of MODEL based on classification attributes (COFCAT,COFSUBCAT,COFGRP) ");
/*  271 */       return hashtable;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  276 */       hashtable = getMQPropertiesFilterVN(paramEntityItem, paramADSABRSTATUS, vector);
/*      */ 
/*      */       
/*  279 */       hashtable = shadowFilter(paramEntityItem, paramADSABRSTATUS, hashtable);
/*  280 */     } catch (Exception exception) {
/*  281 */       if (exception instanceof SBRException) {
/*  282 */         SBRException sBRException = (SBRException)exception;
/*  283 */         paramADSABRSTATUS.addError("getMQPropertiesVN error=" + sBRException.toString());
/*  284 */         exception.printStackTrace();
/*  285 */         MiddlewareException middlewareException1 = new MiddlewareException("getMQPropertiesVN error=" + sBRException.toString());
/*  286 */         throw middlewareException1;
/*      */       } 
/*  288 */       paramADSABRSTATUS.addError("getMQPropertiesVN error=" + exception.getMessage());
/*  289 */       exception.printStackTrace();
/*  290 */       MiddlewareException middlewareException = new MiddlewareException("getMQPropertiesVN error=" + exception.getMessage());
/*  291 */       throw middlewareException;
/*      */     } finally {
/*      */       
/*  294 */       if (this.mf_elist != null) {
/*  295 */         this.mf_elist = null;
/*      */       }
/*      */     } 
/*  298 */     return hashtable;
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
/*      */   private Hashtable shadowFilter(EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable) {
/*  313 */     boolean bool = false;
/*      */     
/*  315 */     String str1 = "10";
/*  316 */     Vector<String> vector1 = (Vector)paramHashtable.get(str1);
/*  317 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  318 */     Vector<String> vector2 = new Vector();
/*  319 */     hashtable.put(str1, vector2);
/*  320 */     hashtable.put("SETUPARRAY", paramHashtable.get("SETUPARRAY"));
/*  321 */     String str2 = null;
/*  322 */     if (paramEntityItem.getEntityType().equals("MODEL")) {
/*  323 */       str2 = PokUtils.getAttributeValue(paramEntityItem, "COFSUBCAT", "|", "");
/*  324 */       paramADSABRSTATUS.addDebug("shadowFilter MODEL.COFSUBCAT=" + str2);
/*  325 */     } else if (paramEntityItem.getEntityType().equals("FCTRANSACTION")) {
/*  326 */       str2 = PokUtils.getAttributeValue(paramEntityItem, "FTSUBCAT", "|", "");
/*  327 */       paramADSABRSTATUS.addDebug("shadowFilter FCTRANSACTION.FTSUBCAT=" + str2);
/*      */     } 
/*  329 */     if ("Shadow".equals(str2)) {
/*  330 */       bool = true;
/*  331 */       paramADSABRSTATUS.addDebug("shadowFilter isShadowFilter=" + bool);
/*  332 */       String str = ABRServerProperties.getValue("ADSABRSTATUS", "_shadow_filter");
/*  333 */       if (str != null && str.length() > 0) {
/*  334 */         paramADSABRSTATUS.addDebug("shadowFilter ADSABRSTATUS_shadow_filter=" + str);
/*  335 */         String[] arrayOfString = str.trim().split(",");
/*  336 */         paramADSABRSTATUS.addDebug("shadowFilter found " + arrayOfString.length + " configs from file");
/*  337 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*  338 */           String str3 = arrayOfString[b].trim();
/*  339 */           if (vector1 != null && vector1.contains(str3)) {
/*  340 */             vector2.add(str3);
/*      */           }
/*      */         } 
/*      */       } else {
/*  344 */         paramADSABRSTATUS.addDebug("shadowFilter not found in abr.server.properties for ADSABRSTATUS");
/*      */       } 
/*      */     } 
/*      */     
/*  348 */     if (bool) {
/*  349 */       Vector<String> vector = (Vector)hashtable.get(str1);
/*  350 */       if (vector.size() > 0) {
/*      */         
/*  352 */         StringBuffer stringBuffer = new StringBuffer();
/*  353 */         stringBuffer.append("There is a shadow filter matched, the original MQ:"); byte b;
/*  354 */         for (b = 0; b < vector1.size(); b++) {
/*  355 */           stringBuffer.append(" ");
/*  356 */           stringBuffer.append(vector1.get(b));
/*      */         } 
/*  358 */         stringBuffer.append(", we will only send to MQ:");
/*  359 */         for (b = 0; b < vector.size(); b++) {
/*  360 */           stringBuffer.append(" ");
/*  361 */           stringBuffer.append(vector.get(b));
/*      */         } 
/*  363 */         paramADSABRSTATUS.addOutput(stringBuffer.toString());
/*      */       } else {
/*  365 */         paramADSABRSTATUS.addOutput("The " + paramEntityItem.getKey() + " matched shadow filter, but not found matched MQ.");
/*      */       } 
/*  367 */       return hashtable;
/*      */     } 
/*  369 */     return paramHashtable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addAllMq(Vector<String> paramVector1, Vector<String> paramVector2, ADSABRSTATUS paramADSABRSTATUS) {
/*  379 */     String str = ""; byte b;
/*  380 */     for (b = 0; b < paramVector2.size(); b++) {
/*  381 */       str = paramVector2.get(b);
/*  382 */       if (!paramVector1.contains(str)) {
/*  383 */         paramVector1.add(str);
/*      */       }
/*      */     } 
/*      */     
/*  387 */     for (b = 0; b < paramVector1.size(); b++) {
/*  388 */       paramADSABRSTATUS.addDebug("Print MQ[" + b + "]= " + paramVector1.get(b));
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
/*  402 */     EntityList entityList = paramDatabase.getEntityList(this._swProfile, new ExtractActionItem(null, paramDatabase, this._swProfile, "dummy"), new EntityItem[] { new EntityItem(null, this._swProfile, paramEntityItem
/*      */             
/*  404 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*  405 */     return entityList.getParentEntityGroup().getEntityItem(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String convertValue(String paramString) {
/*  414 */     return (paramString == null) ? "" : paramString;
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
/*  428 */     String str1 = paramEntityItem.getEntityType();
/*  429 */     String[] arrayOfString = (String[])FILTER_TBL.get(str1);
/*      */     
/*  431 */     String str2 = "";
/*  432 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  433 */       String str = arrayOfString[b];
/*  434 */       if (str.equals("FCTYPE")) {
/*  435 */         if (str1.equals("PRODSTRUCT") || str1.equals("SWPRODSTRUCT")) {
/*  436 */           str2 = getFCTYPE(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } else {
/*  438 */           str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, str));
/*      */         } 
/*  440 */         paramHashtable.put(str, str2);
/*  441 */       } else if (str.equals("STATUS") || str.equals("BUNDLETYPE") || str
/*  442 */         .equals("SVCMODCATG") || str.equals("SVCMODGRP") || str
/*  443 */         .equals("SVCMODSUBCATG") || str.equals("SVCMODSUBGRP") || str
/*  444 */         .equals("FLFILSYSINDC") || str.equals("PDHDOMAIN")) {
/*  445 */         str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, str));
/*  446 */         paramHashtable.put(str, str2);
/*  447 */       } else if (str.equals("COFCAT") || str.equals("COFSUBCAT") || str
/*  448 */         .equals("COFGRP") || str.equals("COFSUBGRP")) {
/*  449 */         if (str1.equals("MODEL")) {
/*  450 */           str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, str));
/*      */         } else {
/*      */           
/*  453 */           str2 = getCOFMODEL(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } 
/*  455 */         if (str.equals("COFCAT") && 
/*  456 */           str2.equals("102")) {
/*  457 */           this.isService = true;
/*      */         }
/*      */         
/*  460 */         paramHashtable.put(str, str2);
/*  461 */       } else if (str.equals("SPECBID")) {
/*  462 */         str2 = convertValue(getSPECBID(paramEntityItem, str1, str, paramADSABRSTATUS));
/*  463 */         paramHashtable.put(str, str2);
/*  464 */       } else if (str.equals("MACHTYPEATR")) {
/*      */         
/*  466 */         if (str1.equals("MODELCONVERT") || str1.equals("FCTRANSACTION")) {
/*  467 */           str2 = convertValue(PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", null, false));
/*      */         } else {
/*      */           
/*  470 */           str2 = getMACHTYPEATR(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } 
/*  472 */         paramHashtable.put(str, str2);
/*  473 */       } else if (str.equals("MODELATR")) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  478 */         if (str1.equals("MODELCONVERT") || str1.equals("FCTRANSACTION")) {
/*  479 */           str2 = getMACHTYPEATR(paramEntityItem, str1, "TOMODEL", paramADSABRSTATUS);
/*      */         } else {
/*  481 */           str2 = getMACHTYPEATR(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } 
/*      */         
/*  484 */         paramHashtable.put(str, str2);
/*  485 */       } else if (str.equals("COUNTRYLIST")) {
/*  486 */         str2 = convertValue(getCOUNTRYLIST(paramEntityItem, str1, str, paramADSABRSTATUS));
/*  487 */         paramHashtable.put(str, str2);
/*  488 */       } else if (str.equals("ENDOFSVC")) {
/*  489 */         if (str1.equals("REFOFER")) {
/*  490 */           str2 = convertValue(PokUtils.getAttributeValue(paramEntityItem, str, "", null, false));
/*  491 */         } else if (str1.equals("REFOFERFEAT")) {
/*  492 */           str2 = getENDOFSVC(paramEntityItem, str1, str, paramADSABRSTATUS);
/*      */         } 
/*  494 */         paramHashtable.put(str, str2);
/*      */       
/*      */       }
/*  497 */       else if (str.equals("DIVTEXT")) {
/*  498 */         str2 = getDIVISION(paramEntityItem, str1, "DIV", paramADSABRSTATUS);
/*  499 */         paramHashtable.put(str, str2);
/*  500 */       } else if (str.equals("DCG")) {
/*  501 */         if (str1.equals("REFOFER")) {
/*  502 */           str2 = convertValue(PokUtils.getAttributeValue(paramEntityItem, str, "", null, false));
/*      */         }
/*  504 */         paramHashtable.put(str, str2);
/*  505 */       } else if (str.equals("OLDINDC")) {
/*  506 */         str2 = convertValue(PokUtils.getAttributeValue(paramEntityItem, str, "", null, false));
/*  507 */         paramHashtable.put(str, str2);
/*      */       } 
/*      */     } 
/*      */     
/*  511 */     Iterator<String> iterator = paramHashtable.keySet().iterator();
/*  512 */     while (iterator.hasNext()) {
/*  513 */       String str = iterator.next();
/*  514 */       paramADSABRSTATUS.addDebug("rootTable:key=" + str + ";value=" + paramHashtable.get(str));
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
/*  546 */     EntityItem[] arrayOfEntityItem = null;
/*      */     
/*  548 */     StringBuffer stringBuffer = new StringBuffer();
/*  549 */     Database database = paramADSABRSTATUS.getDB();
/*      */     
/*  551 */     String str1 = paramADSABRSTATUS.getSimpleABRName(paramString1);
/*  552 */     XMLMQ xMLMQ = (XMLMQ)Class.forName(str1).newInstance();
/*      */     
/*  554 */     Profile profile = null;
/*      */     
/*  556 */     profile = paramADSABRSTATUS.switchRoles(xMLMQ.getRoleCode());
/*  557 */     setSwitchProfile(profile);
/*  558 */     String str2 = "SRDEXTXMLFEED";
/*  559 */     String str3 = "EXTXMLFEED";
/*  560 */     Vector<String> vector1 = new Vector();
/*  561 */     Vector<String> vector2 = new Vector();
/*      */     
/*  563 */     vector1.add("XMLENTITYTYPE");
/*  564 */     vector1.add("XMLSETUPTYPE");
/*  565 */     vector1.add("PDHDOMAIN");
/*  566 */     vector2.add(paramString1);
/*  567 */     vector2.add("Production");
/*  568 */     vector2.add(paramString2);
/*      */     
/*  570 */     paramADSABRSTATUS.addDebug("XMLENTITYTYPE2=" + paramString1);
/*  571 */     paramADSABRSTATUS.addDebug("XMLSETUPTYPE2=Production");
/*      */     try {
/*  573 */       arrayOfEntityItem = ABRUtil.doSearch(database, profile, str2, str3, false, vector1, vector2, stringBuffer);
/*      */       
/*  575 */       paramADSABRSTATUS.addDebug("ABRUtil.doSearch with domain message:" + stringBuffer.toString());
/*  576 */     } catch (Exception exception) {
/*  577 */       paramADSABRSTATUS.addDebug("ABRUtil.doSearch with domain error:" + stringBuffer.toString());
/*  578 */       paramADSABRSTATUS.addDebug("doSearch error=" + exception.getMessage());
/*  579 */       exception.printStackTrace();
/*      */     } 
/*  581 */     paramADSABRSTATUS.addDebug("EXTXMLFEEDArray=" + arrayOfEntityItem.length);
/*  582 */     return arrayOfEntityItem;
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
/*  615 */     EntityItem[] arrayOfEntityItem = null;
/*      */     
/*  617 */     StringBuffer stringBuffer = new StringBuffer();
/*  618 */     Database database = paramADSABRSTATUS.getDB();
/*      */     
/*  620 */     String str1 = paramADSABRSTATUS.getSimpleABRName(paramString);
/*  621 */     XMLMQ xMLMQ = (XMLMQ)Class.forName(str1).newInstance();
/*      */     
/*  623 */     Profile profile = null;
/*      */     
/*  625 */     profile = paramADSABRSTATUS.switchRoles(xMLMQ.getRoleCode());
/*  626 */     setSwitchProfile(profile);
/*  627 */     String str2 = "SRDEXTXMLFEED";
/*  628 */     String str3 = "EXTXMLFEED";
/*  629 */     Vector<String> vector1 = new Vector();
/*  630 */     Vector<String> vector2 = new Vector();
/*      */     
/*  632 */     vector1.add("XMLENTITYTYPE");
/*  633 */     vector1.add("XMLSETUPTYPE");
/*  634 */     vector2.add(paramString);
/*  635 */     vector2.add("Production");
/*      */     
/*  637 */     paramADSABRSTATUS.addDebug("XMLENTITYTYPE2=" + paramString);
/*  638 */     paramADSABRSTATUS.addDebug("XMLSETUPTYPE2=Production");
/*      */     try {
/*  640 */       arrayOfEntityItem = ABRUtil.doSearch(database, profile, str2, str3, false, vector1, vector2, stringBuffer);
/*      */       
/*  642 */       paramADSABRSTATUS.addDebug("ABRUtil.doSearch no domain message:" + stringBuffer.toString());
/*  643 */     } catch (Exception exception) {
/*  644 */       paramADSABRSTATUS.addDebug("ABRUtil.doSearch no domain error:" + stringBuffer.toString());
/*  645 */       paramADSABRSTATUS.addDebug("doSearch error=" + exception.getMessage());
/*  646 */       exception.printStackTrace();
/*      */     } 
/*  648 */     paramADSABRSTATUS.addDebug("EXTXMLFEEDArray=" + arrayOfEntityItem.length);
/*  649 */     return arrayOfEntityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSwitchProfile(Profile paramProfile) {
/*  656 */     this._swProfile = paramProfile;
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
/*  670 */     String[] arrayOfString = (String[])FILTER_TBL.get(paramString);
/*  671 */     String str1 = "";
/*  672 */     String str2 = "";
/*  673 */     String str3 = "";
/*  674 */     String str4 = "";
/*  675 */     String str5 = "";
/*  676 */     String str6 = "";
/*  677 */     String str7 = "";
/*      */     
/*  679 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  680 */       str1 = arrayOfString[b];
/*  681 */       paramADSABRSTATUS.addDebug("attrcode=" + str1);
/*      */       
/*  683 */       if (str1.equals("MODELATR") || str1.equals("ENDOFSVC") || str1.equals("DIVTEXT")) {
/*  684 */         str2 = convertValue(PokUtils.getAttributeValue(paramEntityItem, str1, "", null, false));
/*  685 */         paramHashtable.put(str1, str2);
/*  686 */       } else if (str1.equals("STATUS")) {
/*      */         
/*  688 */         str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "XMLSTATUS"));
/*  689 */         if (str2.equals("XSTATUS02")) {
/*  690 */           str2 = "0020";
/*      */         } else {
/*  692 */           str2 = "";
/*      */         } 
/*  694 */         paramHashtable.put(str1, str2);
/*  695 */       } else if (str1.equals("DCG")) {
/*  696 */         str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, str1));
/*  697 */         if (str1.length() > 0)
/*  698 */           paramHashtable.put(str1, str2); 
/*      */       } else {
/*  700 */         str2 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, str1));
/*  701 */         paramHashtable.put(str1, str2);
/*      */       } 
/*      */     } 
/*  704 */     if (paramString.equals("MODEL") || paramString.equals("LSEO")) {
/*      */       
/*  706 */       str3 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFCAT"));
/*  707 */       str4 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFSUBCAT"));
/*  708 */       str5 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFGRP"));
/*  709 */       str6 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFSUBGRP"));
/*      */ 
/*      */       
/*  712 */       if (this.isService) {
/*  713 */         str5 = "";
/*  714 */         str6 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  722 */       else if ("".equals(str5)) {
/*  723 */         str5 = "150";
/*      */       } 
/*      */       
/*  726 */       paramHashtable.put("COFCAT", str3);
/*  727 */       paramHashtable.put("COFSUBCAT", str4);
/*  728 */       paramHashtable.put("COFGRP", str5);
/*  729 */       paramHashtable.put("COFSUBGRP", str6);
/*      */     } 
/*      */     
/*  732 */     Iterator<String> iterator = paramHashtable.keySet().iterator();
/*  733 */     while (iterator.hasNext()) {
/*  734 */       String str = iterator.next();
/*  735 */       paramADSABRSTATUS.addDebug("EXTXMLFEED SetupEntity filterTable:key=" + str + ";value=" + paramHashtable.get(str));
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
/*  760 */     Vector vector = new Vector();
/*      */     
/*  762 */     String str1 = paramEntityItem.getEntityType();
/*  763 */     String[] arrayOfString = (String[])FILTER_TBL.get(str1);
/*  764 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  765 */     String str2 = "";
/*  766 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*      */ 
/*      */ 
/*      */     
/*  770 */     Database database = paramADSABRSTATUS.getDB();
/*  771 */     if (arrayOfString != null) {
/*  772 */       EntityItem[] arrayOfEntityItem = doSearch(str1, paramADSABRSTATUS);
/*      */       
/*  774 */       setRootEntity(paramEntityItem, hashtable1, paramADSABRSTATUS);
/*  775 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  776 */         EntityItem entityItem = arrayOfEntityItem[b];
/*  777 */         entityItem = getEntityItem(database, entityItem);
/*      */ 
/*      */ 
/*      */         
/*  781 */         setFilterTable(str1, hashtable2, entityItem, paramADSABRSTATUS);
/*  782 */         str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*      */ 
/*      */ 
/*      */         
/*  786 */         boolean bool = false;
/*      */         
/*  788 */         String str = "";
/*  789 */         boolean bool1 = true;
/*  790 */         boolean bool2 = true;
/*  791 */         for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*  792 */           str = arrayOfString[b1];
/*  793 */           if (str.equals("BUNDLETYPE") || str.equals("COUNTRYLIST") || str
/*  794 */             .equals("FLFILSYSINDC") || str.equals("PDHDOMAIN") || str.equals("DIVTEXT")) {
/*  795 */             bool1 = false;
/*      */           }
/*  797 */           paramADSABRSTATUS.addDebug("match" + (String)hashtable1.get(str) + ":" + (String)hashtable2.get(str) + ":" + str + ":" + bool1);
/*      */           
/*  799 */           bool = isVailidCompare((String)hashtable1.get(str), (String)hashtable2.get(str), str, bool1);
/*  800 */           paramADSABRSTATUS.addDebug("match" + bool);
/*  801 */           if (!bool) {
/*  802 */             bool2 = false;
/*      */             break;
/*      */           } 
/*      */         } 
/*  806 */         if (bool2) {
/*  807 */           addXMLPropfile(vector, str2);
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  814 */       EntityItem[] arrayOfEntityItem = null;
/*  815 */       if (str1.equals("ADSXMLSETUP")) {
/*  816 */         String str = PokUtils.getAttributeFlagValue(paramEntityItem, "ADSTYPE");
/*  817 */         if (str != null) {
/*  818 */           str1 = (String)ADSTYPES_TBL.get(str);
/*  819 */           if (str1 == null) {
/*  820 */             str1 = "@@@";
/*      */           }
/*      */         } 
/*  823 */       } else if (str1.equals("XMLPRODPRICESETUP")) {
/*  824 */         str1 = "@@@";
/*      */       } 
/*  826 */       if (str1.equals("@@@")) {
/*  827 */         arrayOfEntityItem = null;
/*      */       } else {
/*  829 */         arrayOfEntityItem = doSearch(str1, paramADSABRSTATUS);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  834 */       if (str1.equals("WWCOMPAT")) {
/*  835 */         this.wwcompMQTable = new Hashtable<>();
/*  836 */         if (arrayOfEntityItem.length > 0) {
/*  837 */           isFilterWWCOMPAT = true;
/*  838 */           for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  839 */             EntityItem entityItem = arrayOfEntityItem[b];
/*  840 */             entityItem = getEntityItem(database, entityItem);
/*      */             
/*  842 */             str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*  843 */             String str3 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "BRANDCD"));
/*  844 */             paramADSABRSTATUS.addDebug("fBRANDCD=" + str3);
/*  845 */             paramADSABRSTATUS.addDebug("wwcompat role code=" + this._swProfile.getRoleCode());
/*      */ 
/*      */             
/*  848 */             if (str3.equals("")) str3 = "@@@";
/*      */ 
/*      */             
/*  851 */             Vector<String> vector1 = (Vector)this.wwcompMQTable.get(str3);
/*  852 */             if (vector1 == null) vector1 = new Vector(); 
/*  853 */             StringTokenizer stringTokenizer = new StringTokenizer(str2, "|");
/*  854 */             String str4 = "";
/*  855 */             while (stringTokenizer.hasMoreTokens()) {
/*  856 */               str4 = stringTokenizer.nextToken();
/*  857 */               if (!vector1.contains(str4)) {
/*  858 */                 vector1.add(str4);
/*      */               }
/*      */             } 
/*      */             
/*  862 */             String str5 = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/*  863 */             if (str5 != null) {
/*      */               
/*  865 */               StringTokenizer stringTokenizer1 = new StringTokenizer(str5, "|");
/*  866 */               while (stringTokenizer1.hasMoreTokens()) {
/*      */                 
/*  868 */                 str4 = stringTokenizer1.nextToken();
/*  869 */                 if (!vector1.contains(str4)) {
/*  870 */                   vector1.add(str4);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             
/*  875 */             if (vector1.size() == 0) {
/*  876 */               vector1.add("ADSMQSERIES");
/*      */             }
/*  878 */             this.wwcompMQTable.put(str3, vector1);
/*      */           } 
/*      */         } else {
/*      */           
/*  882 */           isFilterWWCOMPAT = false;
/*      */         }
/*      */       
/*  885 */       } else if (arrayOfEntityItem != null) {
/*  886 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  887 */           EntityItem entityItem = arrayOfEntityItem[b];
/*  888 */           entityItem = getEntityItem(database, entityItem);
/*  889 */           String str = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*  890 */           addXMLPropfile(vector, str);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  895 */     paramADSABRSTATUS.addDebug("MQ size =" + vector.size());
/*  896 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Hashtable getMQPropertiesFilterVN(EntityItem paramEntityItem, ADSABRSTATUS paramADSABRSTATUS, Vector paramVector) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, InstantiationException, IllegalAccessException, ClassNotFoundException, RemoteException, EANBusinessRuleException, IOException {
/*  904 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*      */ 
/*      */ 
/*      */     
/*  908 */     String str1 = paramEntityItem.getEntityType();
/*  909 */     String[] arrayOfString = (String[])FILTER_TBL.get(str1);
/*  910 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  911 */     String str2 = "";
/*  912 */     Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*      */ 
/*      */     
/*  915 */     String str3 = "";
/*  916 */     String str4 = "";
/*  917 */     String str5 = "";
/*      */ 
/*      */     
/*  920 */     EntityItem[] arrayOfEntityItem = null;
/*  921 */     Vector<EntityItem> vector = new Vector();
/*      */     
/*  923 */     Database database = paramADSABRSTATUS.getDB();
/*  924 */     if (arrayOfString != null) {
/*      */       
/*  926 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN");
/*  927 */       paramADSABRSTATUS.addDebug(paramEntityItem.getKey() + " pdhdomain: " + str);
/*  928 */       if (str != null) {
/*  929 */         arrayOfEntityItem = doSearch(str1, str, paramADSABRSTATUS);
/*      */       } else {
/*  931 */         arrayOfEntityItem = doSearch(str1, paramADSABRSTATUS);
/*      */       } 
/*      */       
/*  934 */       setRootEntity(paramEntityItem, hashtable2, paramADSABRSTATUS);
/*  935 */       if (arrayOfEntityItem != null) {
/*  936 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  937 */           EntityItem entityItem = arrayOfEntityItem[b];
/*  938 */           entityItem = getEntityItem(database, entityItem);
/*      */ 
/*      */ 
/*      */           
/*  942 */           setFilterTable(str1, hashtable3, entityItem, paramADSABRSTATUS);
/*  943 */           str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/*      */ 
/*      */ 
/*      */           
/*  947 */           boolean bool = false;
/*      */           
/*  949 */           String str6 = "";
/*  950 */           boolean bool1 = true;
/*  951 */           boolean bool2 = true;
/*  952 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*  953 */             str6 = arrayOfString[b1];
/*  954 */             if (str6.equals("BUNDLETYPE") || str6.equals("COUNTRYLIST") || str6
/*  955 */               .equals("FLFILSYSINDC") || str6.equals("PDHDOMAIN") || str6.equals("DIVTEXT")) {
/*  956 */               bool1 = false;
/*      */             }
/*  958 */             paramADSABRSTATUS.addDebug("match" + (String)hashtable2.get(str6) + ":" + (String)hashtable3.get(str6) + ":" + str6 + ":" + bool1);
/*  959 */             bool = isVailidCompare((String)hashtable2.get(str6), (String)hashtable3.get(str6), str6, bool1);
/*  960 */             paramADSABRSTATUS.addDebug("match" + bool);
/*  961 */             if (!bool) {
/*  962 */               bool2 = false;
/*      */               break;
/*      */             } 
/*      */           } 
/*  966 */           if (bool2) {
/*  967 */             str3 = convertValue(PokUtils.getAttributeValue(entityItem, "XMLVERSION", "", null, false));
/*  968 */             str4 = convertValue(PokUtils.getAttributeValue(entityItem, "XMLMOD", "", null, false));
/*  969 */             if (!"".equals(str3) && !"".equals(str4) && this.isVaildREFOFERFEAT) {
/*  970 */               str5 = str3 + str4;
/*  971 */               if (hashtable1.containsKey(str5)) {
/*  972 */                 paramVector = (Vector)hashtable1.get(str5);
/*  973 */                 addXMLPropfile(paramVector, str2);
/*  974 */                 hashtable1.put(str5, paramVector);
/*      */               } else {
/*  976 */                 paramVector = new Vector();
/*  977 */                 addXMLPropfile(paramVector, str2);
/*  978 */                 hashtable1.put(str5, paramVector);
/*      */               } 
/*      */               
/*  981 */               vector.add(entityItem);
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
/*  992 */       if (str1.equals("ADSXMLSETUP")) {
/*  993 */         String str = PokUtils.getAttributeFlagValue(paramEntityItem, "ADSTYPE");
/*  994 */         if (str != null) {
/*  995 */           str1 = (String)ADSTYPES_TBL.get(str);
/*  996 */           if (str1 == null) {
/*  997 */             str1 = "@@@";
/*      */           }
/*      */         } 
/* 1000 */       } else if (str1.equals("XMLPRODPRICESETUP")) {
/* 1001 */         str1 = "@@@";
/*      */       } 
/* 1003 */       if (str1.equals("@@@")) {
/* 1004 */         arrayOfEntityItem = null;
/*      */       } else {
/* 1006 */         arrayOfEntityItem = doSearch(str1, paramADSABRSTATUS);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1013 */       if (str1.equals("WWCOMPAT")) {
/* 1014 */         this.wwcompMQTable = new Hashtable<>();
/* 1015 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 1016 */           isFilterWWCOMPAT = true;
/* 1017 */           for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1018 */             EntityItem entityItem = arrayOfEntityItem[b];
/* 1019 */             entityItem = getEntityItem(database, entityItem);
/*      */             
/* 1021 */             str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/* 1022 */             String str6 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "BRANDCD"));
/* 1023 */             paramADSABRSTATUS.addDebug("fBRANDCD=" + str6);
/* 1024 */             paramADSABRSTATUS.addDebug("wwcompat role code=" + this._swProfile.getRoleCode());
/*      */ 
/*      */             
/* 1027 */             if (str6.equals("")) str6 = "@@@";
/*      */ 
/*      */             
/* 1030 */             Vector<String> vector1 = (Vector)this.wwcompMQTable.get(str6);
/* 1031 */             if (vector1 == null) vector1 = new Vector(); 
/* 1032 */             StringTokenizer stringTokenizer = new StringTokenizer(str2, "|");
/* 1033 */             String str7 = "";
/* 1034 */             while (stringTokenizer.hasMoreTokens()) {
/* 1035 */               str7 = stringTokenizer.nextToken();
/* 1036 */               if (!vector1.contains(str7)) {
/* 1037 */                 vector1.add(str7);
/*      */               }
/*      */             } 
/*      */             
/* 1041 */             String str8 = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/* 1042 */             if (str8 != null) {
/*      */               
/* 1044 */               StringTokenizer stringTokenizer1 = new StringTokenizer(str8, "|");
/* 1045 */               while (stringTokenizer1.hasMoreTokens()) {
/*      */                 
/* 1047 */                 str7 = stringTokenizer1.nextToken();
/* 1048 */                 if (!vector1.contains(str7)) {
/* 1049 */                   vector1.add(str7);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             
/* 1054 */             if (vector1.size() == 0) {
/* 1055 */               vector1.add("ADSMQSERIES");
/*      */             }
/* 1057 */             this.wwcompMQTable.put(str6, vector1);
/*      */           } 
/*      */         } else {
/*      */           
/* 1061 */           isFilterWWCOMPAT = false;
/*      */         }
/*      */       
/* 1064 */       } else if (arrayOfEntityItem != null) {
/* 1065 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1066 */           EntityItem entityItem = arrayOfEntityItem[b];
/* 1067 */           entityItem = getEntityItem(database, entityItem);
/* 1068 */           String str = convertValue(PokUtils.getAttributeFlagValue(entityItem, this.attrXMLABRPROPFILE));
/* 1069 */           str3 = convertValue(PokUtils.getAttributeValue(entityItem, "XMLVERSION", "", null, false));
/* 1070 */           str4 = convertValue(PokUtils.getAttributeValue(entityItem, "XMLMOD", "", null, false));
/* 1071 */           if (!"".equals(str3) && !"".equals(str4) && this.isVaildREFOFERFEAT) {
/* 1072 */             str5 = str3 + str4;
/* 1073 */             if (hashtable1.containsKey(str5)) {
/* 1074 */               paramVector = (Vector)hashtable1.get(str5);
/* 1075 */               addXMLPropfile(paramVector, str);
/* 1076 */               hashtable1.put(str5, paramVector);
/*      */             } else {
/*      */               
/* 1079 */               addXMLPropfile(paramVector, str);
/* 1080 */               hashtable1.put(str5, paramVector);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1089 */     hashtable1.put("SETUPARRAY", vector);
/*      */ 
/*      */     
/* 1092 */     paramADSABRSTATUS.addDebug("MQ Table =" + hashtable1.size());
/* 1093 */     if (hashtable1.containsKey("10")) {
/* 1094 */       paramADSABRSTATUS.addDebug("MQ Table 10 =" + hashtable1.get("10").toString());
/*      */     } else {
/* 1096 */       paramADSABRSTATUS.addDebug("MQ Table 10 is null ");
/*      */     } 
/* 1098 */     if (hashtable1.containsKey("05")) {
/* 1099 */       paramADSABRSTATUS.addDebug("MQ Table 05 =" + hashtable1.get("05").toString());
/*      */     } else {
/* 1101 */       paramADSABRSTATUS.addDebug("MQ Table 05 is null ");
/*      */     } 
/* 1103 */     return hashtable1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addXMLPropfile(Vector<String> paramVector, String paramString) {
/* 1112 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "|");
/* 1113 */     String str = "";
/* 1114 */     while (stringTokenizer.hasMoreTokens()) {
/* 1115 */       str = stringTokenizer.nextToken();
/* 1116 */       if (!paramVector.contains(str)) {
/* 1117 */         paramVector.add(str);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVailidCompare(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1126 */     boolean bool = false;
/* 1127 */     if (paramString1 == null) paramString1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1132 */     if (paramString1.equals("@@@")) return true; 
/* 1133 */     if (paramString2 != null && !"".equals(paramString2)) {
/* 1134 */       if ("ENDOFSVC".equals(paramString3)) {
/*      */         
/* 1136 */         if ("".equals(paramString1))
/* 1137 */           return true; 
/* 1138 */         if (paramString1.compareTo(paramString2) >= 0) {
/* 1139 */           return true;
/*      */         }
/*      */       }
/* 1142 */       else if (paramBoolean) {
/* 1143 */         if ("DCG".equals(paramString3) && (paramString1 == null || "".equals(paramString1)))
/* 1144 */           return true; 
/* 1145 */         if (paramString2.equals(paramString1)) {
/* 1146 */           bool = true;
/*      */         } else {
/* 1148 */           bool = false;
/*      */         } 
/*      */       } else {
/*      */         
/* 1152 */         String str1 = "";
/* 1153 */         if ("DIVTEXT".equals(paramString3)) {
/* 1154 */           str1 = ",";
/*      */         } else {
/* 1156 */           str1 = "|";
/*      */         } 
/* 1158 */         StringTokenizer stringTokenizer = new StringTokenizer(paramString2, str1);
/* 1159 */         String str2 = "";
/* 1160 */         String str3 = "";
/* 1161 */         while (stringTokenizer.hasMoreTokens()) {
/* 1162 */           str2 = stringTokenizer.nextToken();
/* 1163 */           if ("".equals(str2)) return true; 
/* 1164 */           StringTokenizer stringTokenizer1 = new StringTokenizer(paramString1, "|");
/* 1165 */           while (stringTokenizer1.hasMoreTokens()) {
/* 1166 */             str3 = stringTokenizer1.nextToken();
/* 1167 */             if (str3.equals(str2)) {
/* 1168 */               return true;
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1176 */       bool = true;
/*      */     } 
/* 1178 */     if ("OLDINDC".equals(paramString3)) {
/* 1179 */       if (paramString2 != null && !"".equals(paramString2.trim())) {
/* 1180 */         if ("".equals(paramString1))
/* 1181 */           return true; 
/* 1182 */         if (paramString2.equals(paramString1)) {
/* 1183 */           bool = true;
/*      */         } else {
/* 1185 */           bool = false;
/*      */         }
/*      */       
/* 1188 */       } else if ("Y".equals(paramString1)) {
/* 1189 */         bool = false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1194 */     return bool;
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
/* 1207 */     String str = "";
/* 1208 */     if (paramString1.equals("FEATURE") || paramString1.equals("IMG") || paramString1
/* 1209 */       .equals("LSEOBUNDLE") || paramString1.equals("LSEO")) {
/* 1210 */       str = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, paramString2));
/* 1211 */     } else if (paramString1.equals("REFOFER")) {
/* 1212 */       str = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, paramString2));
/*      */       
/* 1214 */       if (str.equals("")) {
/* 1215 */         str = "@@@";
/*      */       
/*      */       }
/*      */     }
/* 1219 */     else if (paramString1.equals("REFOFERFEAT")) {
/* 1220 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/* 1221 */       EntityGroup entityGroup = entityList.getEntityGroup("REFOFER");
/* 1222 */       EntityItem[] arrayOfEntityItem = null;
/* 1223 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */       
/* 1225 */       if (arrayOfEntityItem != null) {
/* 1226 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1227 */           EntityItem entityItem = arrayOfEntityItem[b];
/* 1228 */           if (entityItem != null && "REFOFER".equals(entityItem.getEntityType())) {
/* 1229 */             String str1 = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */             
/* 1231 */             if (str1.equals("")) {
/* 1232 */               str = "@@@";
/*      */               break;
/*      */             } 
/* 1235 */             if ("".equals(str)) {
/* 1236 */               str = str1;
/*      */             } else {
/* 1238 */               str = str + "|" + str1;
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } 
/*      */       }
/* 1245 */     } else if (paramString1.equals("PRODSTRUCT")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1253 */       EntityItem entityItem = null;
/* 1254 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */       
/* 1256 */       EntityGroup entityGroup = entityList.getEntityGroup("FEATURE");
/* 1257 */       EntityItem[] arrayOfEntityItem = null;
/* 1258 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1259 */       if (arrayOfEntityItem != null) {
/* 1260 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1261 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1262 */           if (entityItem1 != null && "FEATURE".equals(entityItem1.getEntityType())) {
/* 1263 */             entityItem = entityItem1;
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1271 */       String str1 = "";
/* 1272 */       if (entityItem != null) {
/* 1273 */         str1 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "FCTYPE"));
/* 1274 */         if (str1.equals("100") || str1.equals("110")) {
/* 1275 */           str = getCountryList(paramEntityItem, paramString1, paramString2, paramADSABRSTATUS);
/*      */         } else {
/* 1277 */           str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/* 1283 */       str = getCountryList(paramEntityItem, paramString1, paramString2, paramADSABRSTATUS);
/*      */     } 
/* 1285 */     return str;
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
/* 1305 */     String str = "";
/* 1306 */     EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */     
/* 1308 */     EntityGroup entityGroup = entityList.getEntityGroup("AVAIL");
/* 1309 */     EntityItem[] arrayOfEntityItem = null;
/* 1310 */     if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1311 */     if (arrayOfEntityItem == null || arrayOfEntityItem.length == 0) {
/* 1312 */       paramADSABRSTATUS.addDebug("avail is null");
/* 1313 */       str = "@@@";
/*      */     } else {
/*      */       
/* 1316 */       paramADSABRSTATUS.addDebug("avail is not null");
/* 1317 */       EntityItem entityItem = null;
/* 1318 */       StringBuffer stringBuffer = new StringBuffer();
/* 1319 */       String str1 = "";
/* 1320 */       String str2 = "";
/* 1321 */       String str3 = "";
/* 1322 */       boolean bool1 = false;
/* 1323 */       boolean bool2 = false;
/* 1324 */       byte b1 = 0;
/* 1325 */       for (byte b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/* 1326 */         entityItem = arrayOfEntityItem[b2];
/*      */         
/* 1328 */         str1 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE"));
/*      */         
/* 1330 */         str2 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "STATUS"));
/*      */         
/* 1332 */         if (str1.equals("146")) {
/*      */           
/* 1334 */           str3 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "@@@", false);
/* 1335 */           if (str3.compareTo("2010-03-01") <= 0) {
/* 1336 */             bool1 = true;
/*      */           } else {
/* 1338 */             bool1 = false;
/*      */           } 
/* 1340 */           if (bool1) {
/* 1341 */             bool2 = true;
/* 1342 */           } else if ("0020".equals(str2) || "0040".equals(str2)) {
/* 1343 */             bool2 = true;
/*      */           } else {
/* 1345 */             bool2 = false;
/*      */           } 
/* 1347 */           if (bool2) {
/* 1348 */             if (b1) stringBuffer.append("|"); 
/* 1349 */             stringBuffer.append(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/* 1350 */             b1++;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1354 */       str = stringBuffer.toString();
/* 1355 */       if (str.equals("")) {
/* 1356 */         str = "@@@";
/*      */       }
/*      */     } 
/* 1359 */     return str;
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
/* 1374 */     String str = "";
/*      */     
/* 1376 */     EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */     
/* 1378 */     paramADSABRSTATUS.addDebug("getDIVISION: m_elist=" + entityList);
/*      */     
/* 1380 */     EntityGroup entityGroup = entityList.getEntityGroup("SGMNTACRNYM");
/*      */     
/* 1382 */     EntityItem[] arrayOfEntityItem = null;
/* 1383 */     if (entityGroup != null) {
/* 1384 */       arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/* 1385 */       paramADSABRSTATUS.addDebug("getDIVISIONÃ©âÂ¿Ã¦Â¶Â³Ã¦â¹Â· itemArray=" + arrayOfEntityItem.length);
/*      */     } 
/* 1387 */     byte b1 = 0;
/* 1388 */     for (byte b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/* 1389 */       EntityItem entityItem = arrayOfEntityItem[b2];
/* 1390 */       if (entityItem != null) {
/* 1391 */         b1++;
/* 1392 */         if (b1 == 1) {
/* 1393 */           str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */         } else {
/* 1395 */           str = str + "|" + convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */         } 
/*      */       } 
/*      */     } 
/* 1399 */     paramADSABRSTATUS.addDebug("getDIVISIONÃ©âÂ¿Ã¦Â¶â¢Ã§âÂ±nd");
/* 1400 */     return str;
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
/* 1415 */     String str = "";
/* 1416 */     if (paramString1.equals("PRODSTRUCT") || paramString1.equals("SWPRODSTRUCT")) {
/*      */       
/* 1418 */       EntityItem entityItem = null;
/* 1419 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */       
/* 1421 */       EntityGroup entityGroup = entityList.getEntityGroup("MODEL");
/* 1422 */       paramADSABRSTATUS.addDebug("mdlGrp=" + entityGroup);
/* 1423 */       EntityItem[] arrayOfEntityItem = null;
/* 1424 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1425 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1426 */         EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1427 */         if (entityItem1 != null && "MODEL".equals(entityItem1.getEntityType())) {
/* 1428 */           entityItem = entityItem1;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1432 */       if (entityItem != null) {
/* 1433 */         if (paramString2.equals("MODELATR")) {
/* 1434 */           str = convertValue(PokUtils.getAttributeValue(entityItem, paramString2, "", null, false));
/*      */         } else {
/* 1436 */           str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */         } 
/*      */         
/* 1439 */         paramADSABRSTATUS.addDebug("get model attrcode=" + paramString2 + "modelItem =" + entityItem.getEntityID() + ";attrvalue=" + str);
/*      */       } 
/*      */     } else {
/* 1442 */       str = "";
/*      */     } 
/* 1444 */     return str;
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
/* 1459 */     String str = "@@@";
/* 1460 */     if (paramString1.equals("REFOFERFEAT")) {
/*      */       
/* 1462 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/* 1463 */       EntityGroup entityGroup = entityList.getEntityGroup("REFOFER");
/* 1464 */       EntityItem[] arrayOfEntityItem = null;
/* 1465 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1477 */       if (arrayOfEntityItem != null) {
/* 1478 */         this.isVaildREFOFERFEAT = true;
/* 1479 */         String str1 = "";
/* 1480 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1481 */           EntityItem entityItem = arrayOfEntityItem[b];
/* 1482 */           if (entityItem != null && "REFOFER".equals(entityItem.getEntityType())) {
/* 1483 */             str1 = convertValue(PokUtils.getAttributeValue(entityItem, "ENDOFSVC", "", null, false));
/* 1484 */             if (str.equals("@@@")) {
/* 1485 */               str = str1;
/*      */             }
/* 1487 */             if ("".equals(str) || "".equals(str1)) {
/* 1488 */               str = "@@@"; break;
/*      */             } 
/* 1490 */             if (str.compareTo(str1) <= 0)
/*      */             {
/* 1492 */               if (str.compareTo(str1) <= 0)
/* 1493 */                 str = str1; 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1498 */         str = "";
/* 1499 */         this.isVaildREFOFERFEAT = false;
/*      */       } 
/*      */     } else {
/* 1502 */       str = "";
/*      */     } 
/* 1504 */     return str;
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
/* 1517 */     if (this.mf_elist != null) {
/* 1518 */       return this.mf_elist;
/*      */     }
/* 1520 */     String str = "ADF" + paramString;
/* 1521 */     Database database = paramADSABRSTATUS.getDB();
/* 1522 */     Profile profile = paramADSABRSTATUS.getProfile();
/* 1523 */     this.mf_elist = database.getEntityList(profile, new ExtractActionItem(null, database, profile, str), new EntityItem[] { new EntityItem(null, profile, paramEntityItem
/*      */             
/* 1525 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */     
/* 1527 */     return this.mf_elist;
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
/* 1542 */     String str1 = "";
/* 1543 */     EntityItem entityItem = null;
/* 1544 */     EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */     
/* 1546 */     String str2 = paramString1.equals("PRODSTRUCT") ? "FEATURE" : "SWFEATURE";
/*      */     
/* 1548 */     EntityGroup entityGroup = entityList.getEntityGroup(str2);
/* 1549 */     EntityItem[] arrayOfEntityItem = null;
/* 1550 */     if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1551 */     for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1552 */       EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1553 */       if (entityItem1 != null && str2.equals(entityItem1.getEntityType())) {
/* 1554 */         entityItem = entityItem1;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1558 */     if (entityItem != null) {
/* 1559 */       str1 = convertValue(PokUtils.getAttributeFlagValue(entityItem, "FCTYPE"));
/*      */     }
/* 1561 */     return str1;
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
/* 1574 */     String str = "";
/* 1575 */     EntityItem entityItem = null;
/* 1576 */     if (paramString1.equals("LSEO")) {
/* 1577 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */       
/* 1579 */       EntityGroup entityGroup = entityList.getEntityGroup("MODEL");
/* 1580 */       paramADSABRSTATUS.addDebug("mdlGrp=" + entityGroup);
/* 1581 */       EntityItem[] arrayOfEntityItem = null;
/* 1582 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1583 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1584 */         EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1585 */         if (entityItem1 != null && "MODEL".equals(entityItem1.getEntityType())) {
/* 1586 */           entityItem = entityItem1;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1590 */       if (entityItem != null) {
/* 1591 */         str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */       }
/*      */     } else {
/*      */       
/* 1595 */       str = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, paramString2));
/*      */     } 
/* 1597 */     return str;
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
/* 1610 */     String str = "";
/* 1611 */     if (paramString1.equals("LSEO")) {
/*      */       
/* 1613 */       EntityItem entityItem = null;
/* 1614 */       EntityList entityList = getEntityList(paramEntityItem, paramString1, paramADSABRSTATUS);
/*      */       
/* 1616 */       EntityGroup entityGroup = entityList.getEntityGroup("WWSEO");
/* 1617 */       paramADSABRSTATUS.addDebug("mdlGrp=" + entityGroup);
/* 1618 */       EntityItem[] arrayOfEntityItem = null;
/* 1619 */       if (entityGroup != null) arrayOfEntityItem = entityGroup.getEntityItemsAsArray(); 
/* 1620 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1621 */         EntityItem entityItem1 = arrayOfEntityItem[b];
/* 1622 */         if (entityItem1 != null && "WWSEO".equals(entityItem1.getEntityType())) {
/* 1623 */           entityItem = entityItem1;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1627 */       if (entityItem != null) {
/* 1628 */         str = convertValue(PokUtils.getAttributeFlagValue(entityItem, paramString2));
/*      */       }
/*      */     } else {
/*      */       
/* 1632 */       str = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, paramString2));
/*      */     } 
/* 1634 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean checkIDLMQPropertiesFN(EntityItem paramEntityItem) {
/* 1639 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE);
/* 1640 */     return (str != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getPeriodicMQ(EntityItem paramEntityItem) {
/* 1649 */     Vector<String> vector = new Vector();
/* 1650 */     String str1 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, this.attrXMLABRPROPFILE));
/* 1651 */     StringTokenizer stringTokenizer = new StringTokenizer(str1, "|");
/* 1652 */     String str2 = "";
/* 1653 */     while (stringTokenizer.hasMoreTokens()) {
/* 1654 */       str2 = stringTokenizer.nextToken();
/* 1655 */       vector.add(str2);
/*      */     } 
/* 1657 */     if (vector.size() == 0) vector = null; 
/* 1658 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean createXML(EntityItem paramEntityItem) {
/* 1667 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLElem getXMLMap() {
/* 1672 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVeName() {
/* 1677 */     return "dummy";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVeName2() {
/* 1682 */     return "dummy";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRoleCode() {
/* 1688 */     return "BHFEED";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getStatusAttr() {
/* 1693 */     return "";
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
/* 1706 */     return "";
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
/*      */   
/*      */   protected Connection setupConnection() throws SQLException {
/*      */     try {
/* 1733 */       Connection connection = DriverManager.getConnection(
/* 1734 */           MiddlewareServerProperties.getPDHDatabaseURL(), 
/* 1735 */           MiddlewareServerProperties.getPDHDatabaseUser(), 
/* 1736 */           AES256Utils.decrypt(MiddlewareServerProperties.getPDHDatabasePassword()));
/* 1737 */       connection.setAutoCommit(false);
/* 1738 */       return connection;
/* 1739 */     } catch (SQLException sQLException) {
/*      */       
/* 1741 */       throw sQLException;
/* 1742 */     } catch (Exception exception) {
/*      */       
/* 1744 */       exception.printStackTrace();
/*      */       
/* 1746 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeConnection(Connection paramConnection) throws SQLException {
/* 1757 */     if (paramConnection != null) {
/*      */       try {
/* 1759 */         paramConnection.rollback();
/*      */       }
/* 1761 */       catch (Throwable throwable) {
/* 1762 */         System.err.println("XMLMQAdapter.closeConnection(), unable to rollback. " + throwable);
/*      */       } finally {
/*      */         
/* 1765 */         paramConnection.close();
/* 1766 */         paramConnection = null;
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
/* 1801 */     boolean bool = false;
/* 1802 */     String str1 = paramEntityItem.getEntityType();
/* 1803 */     if (!"MODEL".equals(str1)) {
/* 1804 */       return true;
/*      */     }
/* 1806 */     String str2 = " select count(*) as count from opicm.filter_model where \r\n (cofcat=? or cofcat='*') and                         \r\n (cofsubcat=? or cofsubcat='*') and                   \r\n (cofgrp=? or cofgrp='*') with ur                     \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1811 */     ResultSet resultSet = null;
/* 1812 */     Connection connection = null;
/* 1813 */     PreparedStatement preparedStatement = null;
/* 1814 */     int i = 0;
/*      */     try {
/* 1816 */       connection = setupConnection();
/* 1817 */       preparedStatement = connection.prepareStatement(str2);
/* 1818 */       preparedStatement.setString(1, convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFCAT")));
/* 1819 */       preparedStatement.setString(2, convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFSUBCAT")));
/* 1820 */       preparedStatement.setString(3, convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "COFGRP")));
/* 1821 */       resultSet = preparedStatement.executeQuery();
/* 1822 */       if (resultSet.next()) {
/* 1823 */         i = resultSet.getInt("count");
/* 1824 */         if (i > 0) {
/* 1825 */           bool = true;
/*      */         } else {
/* 1827 */           bool = false;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       try {
/* 1832 */         if (preparedStatement != null) {
/* 1833 */           preparedStatement.close();
/* 1834 */           preparedStatement = null;
/*      */         } 
/* 1836 */       } catch (Exception exception) {
/* 1837 */         paramADSABRSTATUS.addDebug("getPriced unable to close statement. " + exception);
/*      */       } 
/* 1839 */       if (resultSet != null) {
/* 1840 */         resultSet.close();
/*      */       }
/* 1842 */       closeConnection(connection);
/*      */     } 
/* 1844 */     return bool;
/*      */   }
/*      */   
/*      */   public abstract String getMQCID();
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\XMLMQAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */