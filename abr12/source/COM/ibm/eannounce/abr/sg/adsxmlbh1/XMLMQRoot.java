/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.XMLElem;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*      */ import com.ibm.transform.oim.eacm.diff.DiffVE;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.parsers.SAXParser;
/*      */ import javax.xml.parsers.SAXParserFactory;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import org.dom4j.Document;
/*      */ import org.dom4j.DocumentHelper;
/*      */ import org.dom4j.io.SAXValidator;
/*      */ import org.dom4j.util.XMLErrorHandler;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class XMLMQRoot
/*      */   extends XMLMQAdapter
/*      */ {
/*      */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  158 */     if (paramADSABRSTATUS.isSystemResendCache() && paramADSABRSTATUS.isSystemResendCacheExist()) {
/*  159 */       processSystemResendCached(paramADSABRSTATUS, paramEntityItem);
/*      */     } else {
/*  161 */       processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, true);
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
/*      */   private void processSystemResendCached(ADSABRSTATUS paramADSABRSTATUS, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  174 */     long l1 = System.currentTimeMillis();
/*  175 */     long l2 = 0L;
/*  176 */     String str1 = "10";
/*  177 */     Hashtable hashtable = null;
/*  178 */     hashtable = getMQPropertiesVN(paramEntityItem, paramADSABRSTATUS);
/*      */     
/*  180 */     Vector vector = (Vector)hashtable.get(str1);
/*  181 */     String str2 = paramADSABRSTATUS.getSystemResendCacheXml();
/*  182 */     if (str2 != null && vector != null) {
/*      */       
/*  184 */       paramADSABRSTATUS.addDebug("SystemResendCached generate xml from the cache table");
/*  185 */       paramADSABRSTATUS.notify(this, paramEntityItem.getKey(), str2, vector);
/*      */ 
/*      */       
/*  188 */       Vector vector1 = (Vector)hashtable.get("SETUPARRAY");
/*  189 */       paramADSABRSTATUS.setExtxmlfeedVct(vector1);
/*  190 */       l2 = System.currentTimeMillis();
/*  191 */       paramADSABRSTATUS.addDebugComment(3, " Time for XML MQ diff: " + Stopwatch.format(l2 - l1));
/*      */     } else {
/*  193 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", paramEntityItem.getKey());
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
/*      */   protected void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem, boolean paramBoolean) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  213 */     long l1 = System.currentTimeMillis();
/*  214 */     long l2 = 0L;
/*  215 */     String str1 = "1980-01-01-00.00.00.000000";
/*  216 */     String str2 = paramProfile2.getEffOn();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  229 */     boolean bool1 = false;
/*  230 */     boolean bool2 = false;
/*  231 */     boolean bool3 = false;
/*  232 */     boolean bool4 = false;
/*  233 */     String str3 = "05";
/*  234 */     String str4 = "10";
/*  235 */     XMLMQ xMLMQ = null;
/*  236 */     String str5 = "dummy";
/*  237 */     String str6 = "dummy";
/*      */     
/*  239 */     Hashtable hashtable = null;
/*  240 */     if (!paramADSABRSTATUS.isXMLCACHE()) {
/*  241 */       hashtable = getMQPropertiesVN(paramEntityItem, paramADSABRSTATUS);
/*  242 */       bool1 = hashtable.containsKey(str3);
/*  243 */       bool2 = hashtable.containsKey(str4);
/*      */     } 
/*  245 */     String str7 = getStatusAttr();
/*  246 */     String str8 = paramADSABRSTATUS.getAttributeFlagEnabledValue(paramEntityItem, str7);
/*      */ 
/*      */ 
/*      */     
/*  250 */     if (!paramADSABRSTATUS.isXMLCACHE()) {
/*  251 */       if (bool1 || bool2) {
/*  252 */         if (bool1)
/*      */         {
/*  254 */           if (paramADSABRSTATUS.isSystemResendRFR() || paramADSABRSTATUS.isSystemResendCache()) {
/*  255 */             bool1 = false;
/*  256 */             paramADSABRSTATUS.addOutput("Warning: For Version 0.5, Final System RESEND RFR and RESEND CACHE is not support " + paramEntityItem.getKey());
/*  257 */           } else if ("0020".equals(str8)) {
/*      */             
/*  259 */             if (checkSVCMOD(paramEntityItem)) {
/*      */               
/*  261 */               bool1 = false;
/*  262 */               if (!bool2) {
/*  263 */                 paramADSABRSTATUS.addError("Error: exclude SVCMOD where SVCMODCATG = 'Productized Service' (SCSC0004) " + paramEntityItem.getKey() + " But it still update cache with full XML Version.");
/*  264 */                 bool4 = true;
/*  265 */                 bool3 = true;
/*  266 */                 paramProfile1.setValOnEffOn(str1, str1);
/*      */               } else {
/*  268 */                 paramADSABRSTATUS.addOutput("Warning: For Release0.5 exclude SVCMOD where SVCMODCATG = 'Productized Service' (SCSC0004) " + paramEntityItem.getKey());
/*  269 */                 paramADSABRSTATUS.addMSGLOGReason("Warning for V0.5: exclude SVCMODCATG = 'Productized Service' (SCSC0004)");
/*      */               } 
/*      */             } else {
/*  272 */               bool3 = true;
/*      */             } 
/*      */           } else {
/*  275 */             bool1 = false;
/*  276 */             if (!bool2) {
/*  277 */               bool4 = true;
/*  278 */               bool3 = true;
/*  279 */               paramProfile1.setValOnEffOn(str1, str1);
/*  280 */               paramADSABRSTATUS.addError("Error: Status is not Final, for Version 0.5 only Final data needs to be sent. " + paramEntityItem.getKey() + " But it still update cache with full XML Version.");
/*      */             } else {
/*  282 */               paramADSABRSTATUS.addOutput("Warning: Status is not Final, for Version 0.5 only Final data needs to be sent. " + paramEntityItem.getKey());
/*  283 */               paramADSABRSTATUS.addMSGLOGReason("Warning for V0.5 Status is not Final.");
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/*  288 */         if (bool2) {
/*  289 */           bool3 = true;
/*      */         }
/*      */       } else {
/*      */         
/*  293 */         bool4 = true;
/*  294 */         bool3 = true;
/*  295 */         paramProfile1.setValOnEffOn(str1, str1);
/*  296 */         paramADSABRSTATUS.addError("Error:  " + paramEntityItem.getKey() + " not valid for the filter(EXTXMLFEED), can not find request Version and Mod! but it still update cache with full XML Version");
/*      */       } 
/*      */     } else {
/*      */       
/*  300 */       bool3 = true;
/*  301 */       paramADSABRSTATUS.addDebug("Running in IDL Initialize Cache!");
/*      */     } 
/*      */     
/*  304 */     if (!paramADSABRSTATUS.isXMLCACHE() && bool1 && !bool2) {
/*  305 */       String str = paramEntityItem.getEntityType() + str3;
/*  306 */       xMLMQ = getXMLMQ(paramADSABRSTATUS, str);
/*  307 */       if (xMLMQ != null) {
/*  308 */         str5 = xMLMQ.getVeName();
/*  309 */         str6 = "dummy";
/*  310 */         paramADSABRSTATUS.addDebug("only generate xml for 0.5, the VE name is :" + str5);
/*      */       } else {
/*  312 */         bool3 = false;
/*      */       } 
/*      */     } else {
/*  315 */       str5 = getVeName();
/*  316 */       str6 = getVeName2();
/*      */     } 
/*  318 */     Hashtable<? extends String, ? extends String> hashtable1 = null;
/*  319 */     EntityList entityList = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, str5, paramEntityItem);
/*  320 */     if (!bool3) {
/*  321 */       paramADSABRSTATUS.addError("Error: Can not find request version, please check EXTXMLFEED.", paramEntityItem);
/*      */     }
/*  323 */     if (entityList != null && bool3) {
/*  324 */       if (!str6.equals("dummy")) {
/*  325 */         EntityList entityList3 = null;
/*  326 */         if (str6.equals("ADSPRODSTRUCT2") || str6.equals("ADSSWPRODSTRUCT2")) {
/*  327 */           EntityGroup entityGroup = entityList.getEntityGroup("MODEL");
/*  328 */           if (entityGroup != null) {
/*  329 */             EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*  330 */             EntityItem entityItem = arrayOfEntityItem[0];
/*  331 */             if (entityItem != null) {
/*  332 */               entityList3 = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, str6, entityItem);
/*      */             }
/*      */           } else {
/*  335 */             paramADSABRSTATUS.addError("there is no modelitem !");
/*      */           } 
/*      */         } else {
/*      */           
/*  339 */           entityList3 = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, str6, paramEntityItem);
/*      */         } 
/*  341 */         if (entityList3 != null) {
/*  342 */           if (str6.equals("ADSLSEO2"))
/*      */           {
/*  344 */             hashtable1 = ((ExtractActionItem)entityList3.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, paramEntityItem.getEntityType());
/*      */           }
/*  346 */           if (str6.equals("ADSPRODSTRUCT2") || str6.equals("ADSSWPRODSTRUCT2"))
/*      */           {
/*  348 */             hashtable1 = ((ExtractActionItem)entityList3.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, "MODEL");
/*      */           }
/*      */         } else {
/*  351 */           paramADSABRSTATUS.addDebug("After extract EntityList at T2, the return value is null");
/*      */           return;
/*      */         } 
/*  354 */         mergeLists(paramADSABRSTATUS, entityList, entityList3);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  359 */       EntityList entityList1 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, str5, paramEntityItem);
/*  360 */       if (!str6.equals("dummy")) {
/*  361 */         EntityList entityList3 = null;
/*  362 */         if (str6.equals("ADSPRODSTRUCT2") || str6.equals("ADSSWPRODSTRUCT2")) {
/*      */           
/*  364 */           EntityGroup entityGroup = entityList1.getEntityGroup("MODEL");
/*  365 */           EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*  366 */           if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  367 */             EntityItem entityItem = arrayOfEntityItem[0];
/*  368 */             entityList3 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, str6, entityItem);
/*  369 */             mergeLists(paramADSABRSTATUS, entityList1, entityList3);
/*      */           } 
/*      */         } else {
/*  372 */           entityList3 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, str6, paramEntityItem);
/*  373 */           mergeLists(paramADSABRSTATUS, entityList1, entityList3);
/*      */         } 
/*      */       } 
/*  376 */       l2 = System.currentTimeMillis();
/*  377 */       paramADSABRSTATUS.addDebugComment(3, "Time for both pulls: " + Stopwatch.format(l2 - l1));
/*  378 */       l1 = l2;
/*      */ 
/*      */       
/*  381 */       Hashtable<String, String> hashtable2 = ((ExtractActionItem)entityList.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, paramEntityItem
/*  382 */           .getEntityType());
/*  383 */       if (str6.equals("ADSLSEO2")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  406 */         hashtable2.putAll(hashtable1);
/*  407 */         hashtable1.clear();
/*      */       } 
/*  409 */       if (str6.equals("ADSPRODSTRUCT2")) {
/*  410 */         hashtable2.put("0FEATUREU", "Hi");
/*  411 */         hashtable2.put("0MODELD", "Hi");
/*  412 */         hashtable2.putAll(hashtable1);
/*  413 */         hashtable1.clear();
/*      */       } 
/*  415 */       if (str6.equals("ADSSWPRODSTRUCT2")) {
/*  416 */         hashtable2.put("0SWFEATUREU", "Hi");
/*  417 */         hashtable2.put("0MODELD", "Hi");
/*  418 */         hashtable2.putAll(hashtable1);
/*  419 */         hashtable1.clear();
/*      */       } 
/*  421 */       if (str5.equals("ADSSWSPRODSTRUCT")) {
/*  422 */         hashtable2.put("0SWSFEATUREU", "Hi");
/*  423 */         hashtable2.put("0SVCMODD", "Hi");
/*      */       } 
/*      */       
/*  426 */       Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*  427 */       Hashtable<Object, Object> hashtable4 = new Hashtable<>();
/*  428 */       Vector<DiffEntity> vector = new Vector();
/*  429 */       boolean bool5 = false;
/*  430 */       boolean bool6 = false;
/*  431 */       DiffVE diffVE = null;
/*  432 */       EntityList entityList2 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  456 */       diffVE = new DiffVE(entityList1, entityList, hashtable2);
/*  457 */       diffVE.setCheckAllNLS(true);
/*  458 */       paramADSABRSTATUS.addDebug("hshMap: " + hashtable2);
/*  459 */       paramADSABRSTATUS.addDebugComment(3, "time1 flattened VE: " + diffVE.getPriorDiffVE());
/*  460 */       paramADSABRSTATUS.addDebugComment(3, "time2 flattened VE: " + diffVE.getCurrentDiffVE());
/*      */ 
/*      */       
/*  463 */       vector = diffVE.diffVE();
/*  464 */       paramADSABRSTATUS.addDebugComment(4, " diffVE info:\n" + diffVE.getDebug());
/*  465 */       paramADSABRSTATUS.addDebugComment(4, " diffVE flattened VE: " + vector);
/*      */       
/*  467 */       l2 = System.currentTimeMillis();
/*  468 */       paramADSABRSTATUS.addDebugComment(3, " Time for diff: " + Stopwatch.format(l2 - l1));
/*  469 */       l1 = l2;
/*  470 */       for (byte b = 0; b < vector.size(); b++) {
/*  471 */         DiffEntity diffEntity = vector.elementAt(b);
/*      */         
/*  473 */         bool5 = (bool5 || diffEntity.isChanged()) ? true : false;
/*      */       } 
/*  475 */       String str = null;
/*  476 */       if (bool5 && !paramADSABRSTATUS.isPeriodicABR()) {
/*  477 */         if (str1.equals(paramProfile1.getValOn())) {
/*  478 */           bool6 = true;
/*  479 */           paramADSABRSTATUS.addDebugComment(3, "Reuse T1 Entitylist!");
/*      */         } 
/*      */         
/*  482 */         if (paramADSABRSTATUS.isXMLCACHE() || bool4) {
/*  483 */           if (hashtable3.size() <= 0) {
/*  484 */             setdiffTb1(str5, hashtable3, bool6, hashtable2, entityList2, entityList, paramEntityItem, vector, paramADSABRSTATUS, paramProfile1);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  489 */           str = generateMutilXML(paramADSABRSTATUS, this, hashtable3, bool6, (String)null);
/*      */ 
/*      */ 
/*      */           
/*  493 */           boolean bool = false;
/*  494 */           String str9 = paramEntityItem.getEntityType();
/*  495 */           String str10 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str9 + "_XSDNEEDED", "NO");
/*  496 */           if (str10 != null && "YES".equals(str10.toUpperCase())) {
/*  497 */             String str11 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str9 + "_XSDFILE", "NONE");
/*  498 */             if (str11 != null && "NONE".equals(str11)) {
/*  499 */               paramADSABRSTATUS.addError("there is no xsdfile for " + str9 + " defined in the propertyfile ");
/*      */             } else {
/*  501 */               long l3 = System.currentTimeMillis();
/*  502 */               bool = validatexml(paramADSABRSTATUS, str11, str);
/*  503 */               long l4 = System.currentTimeMillis();
/*  504 */               paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l4 - l3));
/*  505 */               if (bool) {
/*  506 */                 paramADSABRSTATUS.addDebug("the xml for " + str9 + " passed the validation");
/*      */               }
/*      */             } 
/*      */           } else {
/*  510 */             paramADSABRSTATUS.addOutput("the xml for " + str9 + " doesn't need to be validated");
/*  511 */             bool = true;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  517 */           if (str != null && bool)
/*      */           {
/*  519 */             paramADSABRSTATUS.putXMLIDLCache(entityList, str, str2);
/*      */           }
/*  521 */           l2 = System.currentTimeMillis();
/*  522 */           paramADSABRSTATUS.addDebugComment(3, " Time for Initialization Cache XML Catch diff: " + 
/*  523 */               Stopwatch.format(l2 - l1));
/*  524 */           l1 = l2;
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  530 */           String str9 = null;
/*  531 */           String str10 = null;
/*  532 */           String str11 = paramProfile1.getEffOn();
/*  533 */           if (bool2) {
/*      */             
/*  535 */             if (hashtable3.size() <= 0) {
/*  536 */               setdiffTb1(str5, hashtable3, bool6, hashtable2, entityList2, entityList, paramEntityItem, vector, paramADSABRSTATUS, paramProfile1);
/*      */             }
/*      */             
/*  539 */             str = generateMutilXML(paramADSABRSTATUS, this, hashtable3, bool6, (String)null);
/*      */ 
/*      */ 
/*      */             
/*  543 */             boolean bool = false;
/*  544 */             String str12 = paramEntityItem.getEntityType();
/*  545 */             String str13 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str12 + "_XSDNEEDED", "NO");
/*  546 */             if (str13 != null && "YES".equals(str13.toUpperCase())) {
/*  547 */               String str14 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str12 + "_XSDFILE", "NONE");
/*  548 */               if (str14 != null && "NONE".equals(str14)) {
/*  549 */                 paramADSABRSTATUS.addError("there is no xsdfile for " + str12 + " defined in the propertyfile ");
/*      */               } else {
/*  551 */                 long l3 = System.currentTimeMillis();
/*  552 */                 bool = validatexml(paramADSABRSTATUS, str14, str);
/*  553 */                 long l4 = System.currentTimeMillis();
/*  554 */                 paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l4 - l3));
/*  555 */                 if (bool) {
/*  556 */                   paramADSABRSTATUS.addDebug("the xml for " + str12 + " passed the validation");
/*      */                 }
/*      */               } 
/*      */             } else {
/*  560 */               paramADSABRSTATUS.addOutput("the xml for " + str12 + " doesn't need to be validated");
/*  561 */               bool = true;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  568 */             if (str != null && bool) {
/*  569 */               paramADSABRSTATUS.putXMLIDLCache(entityList, str, str2);
/*      */             }
/*  571 */             if (bool6) {
/*  572 */               str10 = str;
/*      */             } else {
/*  574 */               paramProfile1.setValOnEffOn(str11, str11); byte b1;
/*  575 */               for (b1 = 0; b1 < vector.size(); b1++) {
/*  576 */                 DiffEntity diffEntity = vector.elementAt(b1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  583 */                 hashtable4.put(diffEntity.getKey(), diffEntity);
/*      */                 
/*  585 */                 String str14 = diffEntity.getEntityType();
/*  586 */                 if (diffEntity.isRoot()) {
/*  587 */                   str14 = "ROOT";
/*      */                 }
/*  589 */                 Vector<DiffEntity> vector3 = (Vector)hashtable4.get(str14);
/*  590 */                 if (vector3 == null) {
/*  591 */                   vector3 = new Vector();
/*  592 */                   hashtable4.put(str14, vector3);
/*      */                 } 
/*  594 */                 vector3.add(diffEntity);
/*      */               } 
/*      */ 
/*      */               
/*  598 */               for (b1 = 0; b1 < entityList.getEntityGroupCount(); b1++) {
/*  599 */                 String str14 = entityList.getEntityGroup(b1).getEntityType();
/*  600 */                 Vector vector3 = (Vector)hashtable4.get(str14);
/*  601 */                 if (vector3 == null) {
/*  602 */                   vector3 = new Vector();
/*  603 */                   hashtable4.put(str14, vector3);
/*      */                 } 
/*      */               } 
/*      */               
/*  607 */               str10 = generateXML(paramADSABRSTATUS, hashtable4);
/*      */             } 
/*      */             
/*  610 */             Vector vector2 = (Vector)hashtable.get(str4);
/*      */             
/*  612 */             if (paramBoolean && str10 != null && vector2 != null && bool)
/*      */             {
/*  614 */               paramADSABRSTATUS.notify(this, paramEntityItem.getKey(), str10, vector2);
/*      */             }
/*      */           } 
/*      */           
/*  618 */           if (bool1) {
/*  619 */             if (hashtable3.size() <= 0) {
/*  620 */               setdiffTb1(str5, hashtable3, bool6, hashtable2, entityList2, entityList, paramEntityItem, vector, paramADSABRSTATUS, paramProfile1);
/*      */             }
/*  622 */             String str12 = paramEntityItem.getEntityType() + str3;
/*  623 */             if (xMLMQ == null) {
/*  624 */               xMLMQ = getXMLMQ(paramADSABRSTATUS, str12);
/*      */             }
/*  626 */             str9 = generateMutilXML(paramADSABRSTATUS, xMLMQ, hashtable3, bool6, str12);
/*  627 */             Vector vector2 = (Vector)hashtable.get(str3);
/*  628 */             if (paramBoolean && str9 != null && vector2 != null)
/*      */             {
/*  630 */               paramADSABRSTATUS.notify(xMLMQ, paramEntityItem.getKey(), str9, vector2);
/*      */             }
/*      */           } 
/*  633 */           Vector vector1 = (Vector)hashtable.get("SETUPARRAY");
/*  634 */           paramADSABRSTATUS.setExtxmlfeedVct(vector1);
/*  635 */           l2 = System.currentTimeMillis();
/*  636 */           paramADSABRSTATUS.addDebugComment(3, " Time for XML MQ diff: " + Stopwatch.format(l2 - l1));
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  641 */         paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", paramEntityItem.getKey());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  647 */       if (entityList1 != null) {
/*  648 */         entityList1.dereference();
/*  649 */         entityList1 = null;
/*      */       } 
/*      */       
/*  652 */       if (entityList != null) {
/*  653 */         entityList.dereference();
/*  654 */         entityList = null;
/*      */       } 
/*      */       
/*  657 */       hashtable2.clear();
/*  658 */       hashtable2 = null;
/*  659 */       vector.clear();
/*  660 */       vector = null;
/*  661 */       if (diffVE != null)
/*  662 */         diffVE.dereference(); 
/*  663 */       diffVE = null;
/*      */       Enumeration<Object> enumeration;
/*  665 */       for (enumeration = hashtable3.elements(); enumeration.hasMoreElements(); ) {
/*  666 */         Vector vector1 = (Vector)enumeration.nextElement();
/*  667 */         if (vector1 instanceof Vector) {
/*  668 */           ((Vector)vector1).clear();
/*      */         }
/*      */       } 
/*  671 */       hashtable3.clear();
/*  672 */       hashtable3 = null;
/*  673 */       for (enumeration = hashtable4.elements(); enumeration.hasMoreElements(); ) {
/*  674 */         Vector vector1 = (Vector)enumeration.nextElement();
/*  675 */         if (vector1 instanceof Vector) {
/*  676 */           ((Vector)vector1).clear();
/*      */         }
/*      */       } 
/*  679 */       hashtable4.clear();
/*  680 */       hashtable4 = null;
/*      */     } else {
/*  682 */       paramADSABRSTATUS.addDebug("After extract EntityList at T2, the return value " + ((entityList == null) ? " is null" : " is not null, but can not find the request version."));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean validatexml(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2) {
/*  693 */     String str = "/COM/ibm/eannounce/abr/sg/adsxmlbh1";
/*  694 */     boolean bool = true;
/*      */     try {
/*  696 */       XMLErrorHandler xMLErrorHandler = new XMLErrorHandler();
/*  697 */       SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
/*  698 */       sAXParserFactory.setValidating(true);
/*  699 */       sAXParserFactory.setNamespaceAware(true);
/*  700 */       SAXParser sAXParser = sAXParserFactory.newSAXParser();
/*  701 */       Document document = DocumentHelper.parseText(paramString2);
/*      */ 
/*      */       
/*  704 */       paramString1 = str + paramString1.substring(paramString1.indexOf("/"));
/*  705 */       InputStream inputStream = getClass().getResourceAsStream(paramString1);
/*      */       
/*  707 */       paramADSABRSTATUS.addDebug("validatexml xsdfile=" + paramString1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  716 */       sAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  723 */       sAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", inputStream);
/*      */ 
/*      */ 
/*      */       
/*  727 */       SAXValidator sAXValidator = new SAXValidator(sAXParser.getXMLReader());
/*  728 */       sAXValidator.setErrorHandler((ErrorHandler)xMLErrorHandler);
/*  729 */       sAXValidator.validate(document);
/*      */       
/*  731 */       if (xMLErrorHandler.getErrors().hasContent()) {
/*  732 */         String str1 = xMLErrorHandler.getErrors().asXML();
/*  733 */         paramADSABRSTATUS.addError("the validation for this xml failed because: " + str1);
/*  734 */         paramADSABRSTATUS.addMSGLOGReason("Failed validate :" + str1);
/*  735 */         bool = false;
/*      */       } else {
/*  737 */         paramADSABRSTATUS.addOutput("the validation for this xml successfully");
/*      */       } 
/*  739 */     } catch (Exception exception) {
/*  740 */       paramADSABRSTATUS.addError("Error:the validation for xml failed,because:" + exception.getMessage());
/*  741 */       paramADSABRSTATUS.addMSGLOGReason("Failed validate :" + exception.getMessage());
/*  742 */       bool = false;
/*      */     } 
/*  744 */     return bool;
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
/*      */   private XMLMQ getXMLMQ(ADSABRSTATUS paramADSABRSTATUS, String paramString) throws IOException {
/*  758 */     String str = (String)ADSABRSTATUS.ABR_VERSION_TBL.get(paramString);
/*  759 */     XMLMQ xMLMQ = null;
/*  760 */     if (str != null) {
/*      */       try {
/*  762 */         xMLMQ = (XMLMQ)Class.forName(str).newInstance();
/*      */       }
/*  764 */       catch (InstantiationException instantiationException) {
/*  765 */         instantiationException.printStackTrace();
/*  766 */         throw new IOException("Can not instance " + str + " class!");
/*  767 */       } catch (IllegalAccessException illegalAccessException) {
/*  768 */         illegalAccessException.printStackTrace();
/*  769 */         throw new IOException("Can not access " + str + " class!");
/*  770 */       } catch (ClassNotFoundException classNotFoundException) {
/*  771 */         classNotFoundException.printStackTrace();
/*  772 */         throw new IOException("Can not find " + str + " class!");
/*      */       } 
/*      */     } else {
/*      */       
/*  776 */       paramADSABRSTATUS.addError("Generated XML does not support version :" + paramString);
/*      */     } 
/*  778 */     return xMLMQ;
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
/*      */   private void setdiffTb1(String paramString, Hashtable<String, DiffEntity> paramHashtable1, boolean paramBoolean, Hashtable paramHashtable2, EntityList paramEntityList1, EntityList paramEntityList2, EntityItem paramEntityItem, Vector<DiffEntity> paramVector, ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile) throws MiddlewareException, ParserConfigurationException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, SQLException {
/*  800 */     String str = "1980-01-01-00.00.00.000000";
/*  801 */     Vector<DiffEntity> vector = new Vector();
/*  802 */     DiffVE diffVE = null;
/*  803 */     if (paramBoolean) {
/*  804 */       vector = paramVector;
/*      */     } else {
/*  806 */       paramProfile.setValOnEffOn(str, str);
/*  807 */       paramEntityList1 = paramADSABRSTATUS.getEntityListForDiff(paramProfile, paramString, paramEntityItem);
/*  808 */       diffVE = new DiffVE(paramEntityList1, paramEntityList2, paramHashtable2);
/*  809 */       diffVE.setCheckAllNLS(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  815 */       vector = diffVE.diffVE();
/*      */     } 
/*      */ 
/*      */     
/*      */     byte b;
/*      */     
/*  821 */     for (b = 0; b < vector.size(); b++) {
/*  822 */       DiffEntity diffEntity = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */       
/*  826 */       paramHashtable1.put(diffEntity.getKey(), diffEntity);
/*      */       
/*  828 */       String str1 = diffEntity.getEntityType();
/*  829 */       if (diffEntity.isRoot()) {
/*  830 */         str1 = "ROOT";
/*      */       }
/*  832 */       Vector<DiffEntity> vector1 = (Vector)paramHashtable1.get(str1);
/*  833 */       if (vector1 == null) {
/*  834 */         vector1 = new Vector();
/*  835 */         paramHashtable1.put(str1, vector1);
/*      */       } 
/*  837 */       vector1.add(diffEntity);
/*      */     } 
/*  839 */     for (b = 0; b < paramEntityList2.getEntityGroupCount(); b++) {
/*  840 */       String str1 = paramEntityList2.getEntityGroup(b).getEntityType();
/*  841 */       Vector vector1 = (Vector)paramHashtable1.get(str1);
/*  842 */       if (vector1 == null) {
/*  843 */         vector1 = new Vector();
/*  844 */         paramHashtable1.put(str1, vector1);
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
/*      */   protected String generateXML(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException {
/*  864 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  865 */     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*      */     
/*  867 */     Document document = documentBuilder.newDocument();
/*  868 */     XMLElem xMLElem = getXMLMap();
/*      */     
/*  870 */     Element element = null;
/*      */     
/*  872 */     StringBuffer stringBuffer = new StringBuffer();
/*  873 */     xMLElem.addElements(paramADSABRSTATUS.getDB(), paramHashtable, document, element, null, stringBuffer);
/*      */     
/*  875 */     paramADSABRSTATUS.addDebugComment(3, "GenXML debug: " + ADSABRSTATUS.NEWLINE + " debug log size=" + stringBuffer
/*  876 */         .toString().length() + ADSABRSTATUS.NEWLINE + stringBuffer
/*  877 */         .toString());
/*  878 */     stringBuffer = null;
/*      */     
/*  880 */     return paramADSABRSTATUS.transformXML(this, document);
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
/*      */   protected String generateMutilXML(ADSABRSTATUS paramADSABRSTATUS, XMLMQ paramXMLMQ, Hashtable paramHashtable, boolean paramBoolean, String paramString) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException {
/*  895 */     String str = null;
/*  896 */     if (paramString == null) {
/*      */       
/*  898 */       if (paramBoolean) {
/*  899 */         str = generateXML(paramADSABRSTATUS, paramHashtable);
/*      */       } else {
/*  901 */         XMLElem xMLElem = null;
/*  902 */         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  903 */         DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*      */         
/*  905 */         Document document = documentBuilder.newDocument();
/*  906 */         xMLElem = getXMLMap();
/*  907 */         Element element = null;
/*  908 */         StringBuffer stringBuffer = new StringBuffer();
/*  909 */         xMLElem.addElements(paramADSABRSTATUS.getDB(), paramHashtable, document, element, null, stringBuffer);
/*  910 */         paramADSABRSTATUS.addDebugComment(4, "GenCacheXML debug: " + ADSABRSTATUS.NEWLINE + stringBuffer.toString());
/*  911 */         str = paramADSABRSTATUS.transformCacheXML(this, document);
/*  912 */         paramADSABRSTATUS.addDebugComment(4, "Generated Cache xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  917 */       XMLElem xMLElem = null;
/*  918 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  919 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*      */       
/*  921 */       Document document = documentBuilder.newDocument();
/*  922 */       xMLElem = paramXMLMQ.getXMLMap();
/*  923 */       Element element = null;
/*  924 */       StringBuffer stringBuffer = new StringBuffer();
/*  925 */       xMLElem.addElements(paramADSABRSTATUS.getDB(), paramHashtable, document, element, null, stringBuffer);
/*  926 */       paramADSABRSTATUS.addDebugComment(3, "Generated debug infor: Version " + paramString + ADSABRSTATUS.NEWLINE + stringBuffer.toString());
/*  927 */       str = paramADSABRSTATUS.transformXML(this, document);
/*  928 */       paramADSABRSTATUS.addDebugComment(3, "Generated Version " + paramString + " xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/*      */     } 
/*  930 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkSVCMOD(EntityItem paramEntityItem) {
/*  939 */     boolean bool = false;
/*  940 */     String str1 = "";
/*  941 */     String str2 = paramEntityItem.getEntityType();
/*  942 */     if (str2.equals("SVCMOD")) {
/*  943 */       str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "SVCMODSUBCATG");
/*  944 */       if (str1.equals("SCSC0004")) {
/*  945 */         bool = true;
/*      */       }
/*      */     } 
/*  948 */     return bool;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1215 */     return "$Revision: 1.45 $";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\XMLMQRoot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */