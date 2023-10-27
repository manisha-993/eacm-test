/*      */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
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
/*      */ public abstract class XMLMQRoot
/*      */   extends XMLMQAdapter
/*      */ {
/*      */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  155 */     if (paramADSABRSTATUS.isSystemResendCache() && paramADSABRSTATUS.isSystemResendCacheExist()) {
/*  156 */       processSystemResendCached(paramADSABRSTATUS, paramEntityItem);
/*      */     } else {
/*  158 */       processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, true);
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
/*  171 */     long l1 = System.currentTimeMillis();
/*  172 */     long l2 = 0L;
/*  173 */     String str1 = "10";
/*  174 */     Hashtable hashtable = null;
/*  175 */     hashtable = getMQPropertiesVN(paramEntityItem, paramADSABRSTATUS);
/*      */     
/*  177 */     Vector vector = (Vector)hashtable.get(str1);
/*  178 */     String str2 = paramADSABRSTATUS.getSystemResendCacheXml();
/*  179 */     if (str2 != null && vector != null) {
/*      */       
/*  181 */       paramADSABRSTATUS.addDebug("SystemResendCached generate xml from the cache table");
/*  182 */       paramADSABRSTATUS.notify(this, paramEntityItem.getKey(), str2, vector);
/*      */ 
/*      */       
/*  185 */       Vector vector1 = (Vector)hashtable.get("SETUPARRAY");
/*  186 */       paramADSABRSTATUS.setExtxmlfeedVct(vector1);
/*  187 */       l2 = System.currentTimeMillis();
/*  188 */       paramADSABRSTATUS.addDebugComment(3, " Time for XML MQ diff: " + Stopwatch.format(l2 - l1));
/*      */     } else {
/*  190 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", paramEntityItem.getKey());
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
/*  210 */     long l1 = System.currentTimeMillis();
/*  211 */     long l2 = 0L;
/*  212 */     String str1 = "1980-01-01-00.00.00.000000";
/*  213 */     String str2 = paramProfile2.getEffOn();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  226 */     boolean bool1 = false;
/*  227 */     boolean bool2 = false;
/*  228 */     boolean bool3 = false;
/*  229 */     boolean bool4 = false;
/*  230 */     String str3 = "05";
/*  231 */     String str4 = "10";
/*  232 */     XMLMQ xMLMQ = null;
/*  233 */     String str5 = "dummy";
/*  234 */     String str6 = "dummy";
/*      */     
/*  236 */     Hashtable hashtable = null;
/*  237 */     if (!paramADSABRSTATUS.isXMLCACHE()) {
/*  238 */       hashtable = getMQPropertiesVN(paramEntityItem, paramADSABRSTATUS);
/*  239 */       bool1 = hashtable.containsKey(str3);
/*  240 */       bool2 = hashtable.containsKey(str4);
/*      */     } 
/*  242 */     String str7 = getStatusAttr();
/*  243 */     String str8 = paramADSABRSTATUS.getAttributeFlagEnabledValue(paramEntityItem, str7);
/*      */ 
/*      */ 
/*      */     
/*  247 */     if (!paramADSABRSTATUS.isXMLCACHE()) {
/*  248 */       if (bool1 || bool2) {
/*  249 */         if (bool1)
/*      */         {
/*  251 */           if (paramADSABRSTATUS.isSystemResendRFR() || paramADSABRSTATUS.isSystemResendCache()) {
/*  252 */             bool1 = false;
/*  253 */             paramADSABRSTATUS.addOutput("Warning: For Version 0.5, Final System RESEND RFR and RESEND CACHE is not support " + paramEntityItem.getKey());
/*  254 */           } else if ("0020".equals(str8)) {
/*      */             
/*  256 */             if (checkSVCMOD(paramEntityItem)) {
/*      */               
/*  258 */               bool1 = false;
/*  259 */               if (!bool2) {
/*  260 */                 paramADSABRSTATUS.addError("Error: exclude SVCMOD where SVCMODCATG = 'Productized Service' (SCSC0004) " + paramEntityItem.getKey() + " But it still update cache with full XML Version.");
/*  261 */                 bool4 = true;
/*  262 */                 bool3 = true;
/*  263 */                 paramProfile1.setValOnEffOn(str1, str1);
/*      */               } else {
/*  265 */                 paramADSABRSTATUS.addOutput("Warning: For Release0.5 exclude SVCMOD where SVCMODCATG = 'Productized Service' (SCSC0004) " + paramEntityItem.getKey());
/*  266 */                 paramADSABRSTATUS.addMSGLOGReason("Warning for V0.5: exclude SVCMODCATG = 'Productized Service' (SCSC0004)");
/*      */               } 
/*      */             } else {
/*  269 */               bool3 = true;
/*      */             } 
/*      */           } else {
/*  272 */             bool1 = false;
/*  273 */             if (!bool2) {
/*  274 */               bool4 = true;
/*  275 */               bool3 = true;
/*  276 */               paramProfile1.setValOnEffOn(str1, str1);
/*  277 */               paramADSABRSTATUS.addError("Error: Status is not Final, for Version 0.5 only Final data needs to be sent. " + paramEntityItem.getKey() + " But it still update cache with full XML Version.");
/*      */             } else {
/*  279 */               paramADSABRSTATUS.addOutput("Warning: Status is not Final, for Version 0.5 only Final data needs to be sent. " + paramEntityItem.getKey());
/*  280 */               paramADSABRSTATUS.addMSGLOGReason("Warning for V0.5 Status is not Final.");
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/*  285 */         if (bool2) {
/*  286 */           bool3 = true;
/*      */         }
/*      */       } else {
/*      */         
/*  290 */         bool4 = true;
/*  291 */         bool3 = true;
/*  292 */         paramProfile1.setValOnEffOn(str1, str1);
/*  293 */         paramADSABRSTATUS.addError("Error:  " + paramEntityItem.getKey() + " not valid for the filter(EXTXMLFEED), can not find request Version and Mod! but it still update cache with full XML Version");
/*      */       } 
/*      */     } else {
/*      */       
/*  297 */       bool3 = true;
/*  298 */       paramADSABRSTATUS.addDebug("Running in IDL Initialize Cache!");
/*      */     } 
/*      */     
/*  301 */     if (!paramADSABRSTATUS.isXMLCACHE() && bool1 && !bool2) {
/*  302 */       String str = paramEntityItem.getEntityType() + str3;
/*  303 */       xMLMQ = getXMLMQ(paramADSABRSTATUS, str);
/*  304 */       if (xMLMQ != null) {
/*  305 */         str5 = xMLMQ.getVeName();
/*  306 */         str6 = "dummy";
/*  307 */         paramADSABRSTATUS.addDebug("only generate xml for 0.5, the VE name is :" + str5);
/*      */       } else {
/*  309 */         bool3 = false;
/*      */       } 
/*      */     } else {
/*  312 */       str5 = getVeName();
/*  313 */       str6 = getVeName2();
/*      */     } 
/*  315 */     Hashtable<? extends String, ? extends String> hashtable1 = null;
/*  316 */     EntityList entityList = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, str5, paramEntityItem);
/*  317 */     if (!bool3) {
/*  318 */       paramADSABRSTATUS.addError("Error: Can not find request version, please check EXTXMLFEED.", paramEntityItem);
/*      */     }
/*  320 */     if (entityList != null && bool3) {
/*  321 */       if (!str6.equals("dummy")) {
/*  322 */         EntityList entityList3 = null;
/*  323 */         if (str6.equals("ADSPRODSTRUCT2") || str6.equals("ADSSWPRODSTRUCT2")) {
/*  324 */           EntityGroup entityGroup = entityList.getEntityGroup("MODEL");
/*  325 */           if (entityGroup != null) {
/*  326 */             EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*  327 */             EntityItem entityItem = arrayOfEntityItem[0];
/*  328 */             if (entityItem != null) {
/*  329 */               entityList3 = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, str6, entityItem);
/*      */             }
/*      */           } else {
/*  332 */             paramADSABRSTATUS.addError("there is no modelitem !");
/*      */           } 
/*      */         } else {
/*      */           
/*  336 */           entityList3 = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, str6, paramEntityItem);
/*      */         } 
/*  338 */         if (entityList3 != null) {
/*  339 */           if (str6.equals("ADSLSEO2"))
/*      */           {
/*  341 */             hashtable1 = ((ExtractActionItem)entityList3.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, paramEntityItem.getEntityType());
/*      */           }
/*  343 */           if (str6.equals("ADSPRODSTRUCT2") || str6.equals("ADSSWPRODSTRUCT2"))
/*      */           {
/*  345 */             hashtable1 = ((ExtractActionItem)entityList3.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, "MODEL");
/*      */           }
/*      */         } else {
/*  348 */           paramADSABRSTATUS.addDebug("After extract EntityList at T2, the return value is null");
/*      */           return;
/*      */         } 
/*  351 */         mergeLists(paramADSABRSTATUS, entityList, entityList3);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  356 */       EntityList entityList1 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, str5, paramEntityItem);
/*  357 */       if (!str6.equals("dummy")) {
/*  358 */         EntityList entityList3 = null;
/*  359 */         if (str6.equals("ADSPRODSTRUCT2") || str6.equals("ADSSWPRODSTRUCT2")) {
/*      */           
/*  361 */           EntityGroup entityGroup = entityList1.getEntityGroup("MODEL");
/*  362 */           EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*  363 */           if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  364 */             EntityItem entityItem = arrayOfEntityItem[0];
/*  365 */             entityList3 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, str6, entityItem);
/*  366 */             mergeLists(paramADSABRSTATUS, entityList1, entityList3);
/*      */           } 
/*      */         } else {
/*  369 */           entityList3 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, str6, paramEntityItem);
/*  370 */           mergeLists(paramADSABRSTATUS, entityList1, entityList3);
/*      */         } 
/*      */       } 
/*  373 */       l2 = System.currentTimeMillis();
/*  374 */       paramADSABRSTATUS.addDebugComment(3, "Time for both pulls: " + Stopwatch.format(l2 - l1));
/*  375 */       l1 = l2;
/*      */ 
/*      */       
/*  378 */       Hashtable<String, String> hashtable2 = ((ExtractActionItem)entityList.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, paramEntityItem
/*  379 */           .getEntityType());
/*  380 */       if (str6.equals("ADSLSEO2")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  403 */         hashtable2.putAll(hashtable1);
/*  404 */         hashtable1.clear();
/*      */       } 
/*  406 */       if (str6.equals("ADSPRODSTRUCT2")) {
/*  407 */         hashtable2.put("0FEATUREU", "Hi");
/*  408 */         hashtable2.put("0MODELD", "Hi");
/*  409 */         hashtable2.putAll(hashtable1);
/*  410 */         hashtable1.clear();
/*      */       } 
/*  412 */       if (str6.equals("ADSSWPRODSTRUCT2")) {
/*  413 */         hashtable2.put("0SWFEATUREU", "Hi");
/*  414 */         hashtable2.put("0MODELD", "Hi");
/*  415 */         hashtable2.putAll(hashtable1);
/*  416 */         hashtable1.clear();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  423 */       Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*  424 */       Hashtable<Object, Object> hashtable4 = new Hashtable<>();
/*  425 */       Vector<DiffEntity> vector = new Vector();
/*  426 */       boolean bool5 = false;
/*  427 */       boolean bool6 = false;
/*  428 */       DiffVE diffVE = null;
/*  429 */       EntityList entityList2 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  453 */       diffVE = new DiffVE(entityList1, entityList, hashtable2);
/*  454 */       diffVE.setCheckAllNLS(true);
/*  455 */       paramADSABRSTATUS.addDebug("hshMap: " + hashtable2);
/*  456 */       paramADSABRSTATUS.addDebugComment(3, "time1 flattened VE: " + diffVE.getPriorDiffVE());
/*  457 */       paramADSABRSTATUS.addDebugComment(3, "time2 flattened VE: " + diffVE.getCurrentDiffVE());
/*      */ 
/*      */       
/*  460 */       vector = diffVE.diffVE();
/*  461 */       paramADSABRSTATUS.addDebugComment(4, " diffVE info:\n" + diffVE.getDebug());
/*  462 */       paramADSABRSTATUS.addDebugComment(4, " diffVE flattened VE: " + vector);
/*      */       
/*  464 */       l2 = System.currentTimeMillis();
/*  465 */       paramADSABRSTATUS.addDebugComment(3, " Time for diff: " + Stopwatch.format(l2 - l1));
/*  466 */       l1 = l2;
/*  467 */       for (byte b = 0; b < vector.size(); b++) {
/*  468 */         DiffEntity diffEntity = vector.elementAt(b);
/*      */         
/*  470 */         bool5 = (bool5 || diffEntity.isChanged()) ? true : false;
/*      */       } 
/*  472 */       String str = null;
/*  473 */       if (bool5 && !paramADSABRSTATUS.isPeriodicABR()) {
/*  474 */         if (str1.equals(paramProfile1.getValOn())) {
/*  475 */           bool6 = true;
/*  476 */           paramADSABRSTATUS.addDebugComment(3, "Reuse T1 Entitylist!");
/*      */         } 
/*      */         
/*  479 */         if (paramADSABRSTATUS.isXMLCACHE() || bool4) {
/*  480 */           if (hashtable3.size() <= 0) {
/*  481 */             setdiffTb1(str5, hashtable3, bool6, hashtable2, entityList2, entityList, paramEntityItem, vector, paramADSABRSTATUS, paramProfile1);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  486 */           str = generateMutilXML(paramADSABRSTATUS, this, hashtable3, bool6, (String)null);
/*      */ 
/*      */ 
/*      */           
/*  490 */           boolean bool = false;
/*  491 */           String str9 = paramEntityItem.getEntityType();
/*  492 */           String str10 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str9 + "_XSDNEEDED", "NO");
/*  493 */           if (str10 != null && "YES".equals(str10.toUpperCase())) {
/*  494 */             String str11 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str9 + "_XSDFILE", "NONE");
/*  495 */             if (str11 != null && "NONE".equals(str11)) {
/*  496 */               paramADSABRSTATUS.addError("there is no xsdfile for " + str9 + " defined in the propertyfile ");
/*      */             } else {
/*  498 */               long l3 = System.currentTimeMillis();
/*  499 */               bool = validatexml(paramADSABRSTATUS, str11, str);
/*  500 */               long l4 = System.currentTimeMillis();
/*  501 */               paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l4 - l3));
/*  502 */               if (bool) {
/*  503 */                 paramADSABRSTATUS.addDebug("the xml for " + str9 + " passed the validation");
/*      */               }
/*      */             } 
/*      */           } else {
/*  507 */             paramADSABRSTATUS.addOutput("the xml for " + str9 + " doesn't need to be validated");
/*  508 */             bool = true;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  514 */           if (str != null && bool)
/*      */           {
/*  516 */             paramADSABRSTATUS.putXMLIDLCache(entityList, str, str2);
/*      */           }
/*  518 */           l2 = System.currentTimeMillis();
/*  519 */           paramADSABRSTATUS.addDebugComment(3, " Time for Initialization Cache XML Catch diff: " + 
/*  520 */               Stopwatch.format(l2 - l1));
/*  521 */           l1 = l2;
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  527 */           String str9 = null;
/*  528 */           String str10 = null;
/*  529 */           String str11 = paramProfile1.getEffOn();
/*  530 */           if (bool2) {
/*      */             
/*  532 */             if (hashtable3.size() <= 0) {
/*  533 */               setdiffTb1(str5, hashtable3, bool6, hashtable2, entityList2, entityList, paramEntityItem, vector, paramADSABRSTATUS, paramProfile1);
/*      */             }
/*      */             
/*  536 */             str = generateMutilXML(paramADSABRSTATUS, this, hashtable3, bool6, (String)null);
/*      */ 
/*      */ 
/*      */             
/*  540 */             boolean bool = false;
/*  541 */             String str12 = paramEntityItem.getEntityType();
/*  542 */             String str13 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str12 + "_XSDNEEDED", "NO");
/*  543 */             if (str13 != null && "YES".equals(str13.toUpperCase())) {
/*  544 */               String str14 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str12 + "_XSDFILE", "NONE");
/*  545 */               if (str14 != null && "NONE".equals(str14)) {
/*  546 */                 paramADSABRSTATUS.addError("there is no xsdfile for " + str12 + " defined in the propertyfile ");
/*      */               } else {
/*  548 */                 long l3 = System.currentTimeMillis();
/*  549 */                 bool = validatexml(paramADSABRSTATUS, str14, str);
/*  550 */                 long l4 = System.currentTimeMillis();
/*  551 */                 paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l4 - l3));
/*  552 */                 if (bool) {
/*  553 */                   paramADSABRSTATUS.addDebug("the xml for " + str12 + " passed the validation");
/*      */                 }
/*      */               } 
/*      */             } else {
/*  557 */               paramADSABRSTATUS.addOutput("the xml for " + str12 + " doesn't need to be validated");
/*  558 */               bool = true;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  565 */             if (str != null && bool) {
/*  566 */               paramADSABRSTATUS.putXMLIDLCache(entityList, str, str2);
/*      */             }
/*  568 */             if (bool6) {
/*  569 */               str10 = str;
/*      */             } else {
/*  571 */               paramProfile1.setValOnEffOn(str11, str11); byte b1;
/*  572 */               for (b1 = 0; b1 < vector.size(); b1++) {
/*  573 */                 DiffEntity diffEntity = vector.elementAt(b1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  580 */                 hashtable4.put(diffEntity.getKey(), diffEntity);
/*      */                 
/*  582 */                 String str14 = diffEntity.getEntityType();
/*  583 */                 if (diffEntity.isRoot()) {
/*  584 */                   str14 = "ROOT";
/*      */                 }
/*  586 */                 Vector<DiffEntity> vector3 = (Vector)hashtable4.get(str14);
/*  587 */                 if (vector3 == null) {
/*  588 */                   vector3 = new Vector();
/*  589 */                   hashtable4.put(str14, vector3);
/*      */                 } 
/*  591 */                 vector3.add(diffEntity);
/*      */               } 
/*      */ 
/*      */               
/*  595 */               for (b1 = 0; b1 < entityList.getEntityGroupCount(); b1++) {
/*  596 */                 String str14 = entityList.getEntityGroup(b1).getEntityType();
/*  597 */                 Vector vector3 = (Vector)hashtable4.get(str14);
/*  598 */                 if (vector3 == null) {
/*  599 */                   vector3 = new Vector();
/*  600 */                   hashtable4.put(str14, vector3);
/*      */                 } 
/*      */               } 
/*      */               
/*  604 */               str10 = generateXML(paramADSABRSTATUS, hashtable4);
/*      */             } 
/*      */             
/*  607 */             Vector vector2 = (Vector)hashtable.get(str4);
/*      */             
/*  609 */             if (paramBoolean && str10 != null && vector2 != null && bool)
/*      */             {
/*  611 */               paramADSABRSTATUS.notify(this, paramEntityItem.getKey(), str10, vector2);
/*      */             }
/*      */           } 
/*      */           
/*  615 */           if (bool1) {
/*  616 */             if (hashtable3.size() <= 0) {
/*  617 */               setdiffTb1(str5, hashtable3, bool6, hashtable2, entityList2, entityList, paramEntityItem, vector, paramADSABRSTATUS, paramProfile1);
/*      */             }
/*  619 */             String str12 = paramEntityItem.getEntityType() + str3;
/*  620 */             if (xMLMQ == null) {
/*  621 */               xMLMQ = getXMLMQ(paramADSABRSTATUS, str12);
/*      */             }
/*  623 */             str9 = generateMutilXML(paramADSABRSTATUS, xMLMQ, hashtable3, bool6, str12);
/*  624 */             Vector vector2 = (Vector)hashtable.get(str3);
/*  625 */             if (paramBoolean && str9 != null && vector2 != null)
/*      */             {
/*  627 */               paramADSABRSTATUS.notify(xMLMQ, paramEntityItem.getKey(), str9, vector2);
/*      */             }
/*      */           } 
/*  630 */           Vector vector1 = (Vector)hashtable.get("SETUPARRAY");
/*  631 */           paramADSABRSTATUS.setExtxmlfeedVct(vector1);
/*  632 */           l2 = System.currentTimeMillis();
/*  633 */           paramADSABRSTATUS.addDebugComment(3, " Time for XML MQ diff: " + Stopwatch.format(l2 - l1));
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  638 */         paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", paramEntityItem.getKey());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  644 */       if (entityList1 != null) {
/*  645 */         entityList1.dereference();
/*  646 */         entityList1 = null;
/*      */       } 
/*      */       
/*  649 */       if (entityList != null) {
/*  650 */         entityList.dereference();
/*  651 */         entityList = null;
/*      */       } 
/*      */       
/*  654 */       hashtable2.clear();
/*  655 */       hashtable2 = null;
/*  656 */       vector.clear();
/*  657 */       vector = null;
/*  658 */       if (diffVE != null)
/*  659 */         diffVE.dereference(); 
/*  660 */       diffVE = null;
/*      */       Enumeration<Object> enumeration;
/*  662 */       for (enumeration = hashtable3.elements(); enumeration.hasMoreElements(); ) {
/*  663 */         Vector vector1 = (Vector)enumeration.nextElement();
/*  664 */         if (vector1 instanceof Vector) {
/*  665 */           ((Vector)vector1).clear();
/*      */         }
/*      */       } 
/*  668 */       hashtable3.clear();
/*  669 */       hashtable3 = null;
/*  670 */       for (enumeration = hashtable4.elements(); enumeration.hasMoreElements(); ) {
/*  671 */         Vector vector1 = (Vector)enumeration.nextElement();
/*  672 */         if (vector1 instanceof Vector) {
/*  673 */           ((Vector)vector1).clear();
/*      */         }
/*      */       } 
/*  676 */       hashtable4.clear();
/*  677 */       hashtable4 = null;
/*      */     } else {
/*  679 */       paramADSABRSTATUS.addDebug("After extract EntityList at T2, the return value " + ((entityList == null) ? " is null" : " is not null, but can not find the request version."));
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
/*  690 */     String str = "/COM/ibm/eannounce/abr/sg/adsxmlbh1";
/*  691 */     boolean bool = true;
/*      */     try {
/*  693 */       XMLErrorHandler xMLErrorHandler = new XMLErrorHandler();
/*  694 */       SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
/*  695 */       sAXParserFactory.setValidating(true);
/*  696 */       sAXParserFactory.setNamespaceAware(true);
/*  697 */       SAXParser sAXParser = sAXParserFactory.newSAXParser();
/*  698 */       Document document = DocumentHelper.parseText(paramString2);
/*      */ 
/*      */       
/*  701 */       paramString1 = str + paramString1.substring(paramString1.indexOf("/"));
/*  702 */       InputStream inputStream = getClass().getResourceAsStream(paramString1);
/*      */       
/*  704 */       paramADSABRSTATUS.addDebug("validatexml xsdfile=" + paramString1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  713 */       sAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  720 */       sAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", inputStream);
/*      */ 
/*      */ 
/*      */       
/*  724 */       SAXValidator sAXValidator = new SAXValidator(sAXParser.getXMLReader());
/*  725 */       sAXValidator.setErrorHandler((ErrorHandler)xMLErrorHandler);
/*  726 */       sAXValidator.validate(document);
/*      */       
/*  728 */       if (xMLErrorHandler.getErrors().hasContent()) {
/*  729 */         String str1 = xMLErrorHandler.getErrors().asXML();
/*  730 */         paramADSABRSTATUS.addError("the validation for this xml failed because: " + str1);
/*  731 */         paramADSABRSTATUS.addMSGLOGReason("Failed validate :" + str1);
/*  732 */         bool = false;
/*      */       } else {
/*  734 */         paramADSABRSTATUS.addOutput("the validation for this xml successfully");
/*      */       } 
/*  736 */     } catch (Exception exception) {
/*  737 */       paramADSABRSTATUS.addError("Error:the validation for xml failed,because:" + exception.getMessage());
/*  738 */       paramADSABRSTATUS.addMSGLOGReason("Failed validate :" + exception.getMessage());
/*  739 */       bool = false;
/*      */     } 
/*  741 */     return bool;
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
/*  755 */     String str = (String)ADSABRSTATUS.ABR_VERSION_TBL.get(paramString);
/*  756 */     XMLMQ xMLMQ = null;
/*  757 */     if (str != null) {
/*      */       try {
/*  759 */         xMLMQ = (XMLMQ)Class.forName(str).newInstance();
/*      */       }
/*  761 */       catch (InstantiationException instantiationException) {
/*  762 */         instantiationException.printStackTrace();
/*  763 */         throw new IOException("Can not instance " + str + " class!");
/*  764 */       } catch (IllegalAccessException illegalAccessException) {
/*  765 */         illegalAccessException.printStackTrace();
/*  766 */         throw new IOException("Can not access " + str + " class!");
/*  767 */       } catch (ClassNotFoundException classNotFoundException) {
/*  768 */         classNotFoundException.printStackTrace();
/*  769 */         throw new IOException("Can not find " + str + " class!");
/*      */       } 
/*      */     } else {
/*      */       
/*  773 */       paramADSABRSTATUS.addError("Generated XML does not support version :" + paramString);
/*      */     } 
/*  775 */     return xMLMQ;
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
/*  797 */     String str = "1980-01-01-00.00.00.000000";
/*  798 */     Vector<DiffEntity> vector = new Vector();
/*  799 */     DiffVE diffVE = null;
/*  800 */     if (paramBoolean) {
/*  801 */       vector = paramVector;
/*      */     } else {
/*  803 */       paramProfile.setValOnEffOn(str, str);
/*  804 */       paramEntityList1 = paramADSABRSTATUS.getEntityListForDiff(paramProfile, paramString, paramEntityItem);
/*  805 */       diffVE = new DiffVE(paramEntityList1, paramEntityList2, paramHashtable2);
/*  806 */       diffVE.setCheckAllNLS(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  812 */       vector = diffVE.diffVE();
/*      */     } 
/*      */ 
/*      */     
/*      */     byte b;
/*      */     
/*  818 */     for (b = 0; b < vector.size(); b++) {
/*  819 */       DiffEntity diffEntity = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */       
/*  823 */       paramHashtable1.put(diffEntity.getKey(), diffEntity);
/*      */       
/*  825 */       String str1 = diffEntity.getEntityType();
/*  826 */       if (diffEntity.isRoot()) {
/*  827 */         str1 = "ROOT";
/*      */       }
/*  829 */       Vector<DiffEntity> vector1 = (Vector)paramHashtable1.get(str1);
/*  830 */       if (vector1 == null) {
/*  831 */         vector1 = new Vector();
/*  832 */         paramHashtable1.put(str1, vector1);
/*      */       } 
/*  834 */       vector1.add(diffEntity);
/*      */     } 
/*  836 */     for (b = 0; b < paramEntityList2.getEntityGroupCount(); b++) {
/*  837 */       String str1 = paramEntityList2.getEntityGroup(b).getEntityType();
/*  838 */       Vector vector1 = (Vector)paramHashtable1.get(str1);
/*  839 */       if (vector1 == null) {
/*  840 */         vector1 = new Vector();
/*  841 */         paramHashtable1.put(str1, vector1);
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
/*  861 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  862 */     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*      */     
/*  864 */     Document document = documentBuilder.newDocument();
/*  865 */     XMLElem xMLElem = getXMLMap();
/*      */     
/*  867 */     Element element = null;
/*      */     
/*  869 */     StringBuffer stringBuffer = new StringBuffer();
/*  870 */     xMLElem.addElements(paramADSABRSTATUS.getDB(), paramHashtable, document, element, null, stringBuffer);
/*      */     
/*  872 */     paramADSABRSTATUS.addDebugComment(3, "GenXML debug: " + ADSABRSTATUS.NEWLINE + " debug log size=" + stringBuffer
/*  873 */         .toString().length() + ADSABRSTATUS.NEWLINE + stringBuffer
/*  874 */         .toString());
/*  875 */     stringBuffer = null;
/*      */     
/*  877 */     return paramADSABRSTATUS.transformXML(this, document);
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
/*  892 */     String str = null;
/*  893 */     if (paramString == null) {
/*      */       
/*  895 */       if (paramBoolean) {
/*  896 */         str = generateXML(paramADSABRSTATUS, paramHashtable);
/*      */       } else {
/*  898 */         XMLElem xMLElem = null;
/*  899 */         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  900 */         DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*      */         
/*  902 */         Document document = documentBuilder.newDocument();
/*  903 */         xMLElem = getXMLMap();
/*  904 */         Element element = null;
/*  905 */         StringBuffer stringBuffer = new StringBuffer();
/*  906 */         xMLElem.addElements(paramADSABRSTATUS.getDB(), paramHashtable, document, element, null, stringBuffer);
/*  907 */         paramADSABRSTATUS.addDebugComment(4, "GenCacheXML debug: " + ADSABRSTATUS.NEWLINE + stringBuffer.toString());
/*  908 */         str = paramADSABRSTATUS.transformCacheXML(this, document);
/*  909 */         paramADSABRSTATUS.addDebugComment(4, "Generated Cache xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  914 */       XMLElem xMLElem = null;
/*  915 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  916 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*      */       
/*  918 */       Document document = documentBuilder.newDocument();
/*  919 */       xMLElem = paramXMLMQ.getXMLMap();
/*  920 */       Element element = null;
/*  921 */       StringBuffer stringBuffer = new StringBuffer();
/*  922 */       xMLElem.addElements(paramADSABRSTATUS.getDB(), paramHashtable, document, element, null, stringBuffer);
/*  923 */       paramADSABRSTATUS.addDebugComment(3, "Generated debug infor: Version " + paramString + ADSABRSTATUS.NEWLINE + stringBuffer.toString());
/*  924 */       str = paramADSABRSTATUS.transformXML(this, document);
/*  925 */       paramADSABRSTATUS.addDebugComment(3, "Generated Version " + paramString + " xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/*      */     } 
/*  927 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkSVCMOD(EntityItem paramEntityItem) {
/*  936 */     boolean bool = false;
/*  937 */     String str1 = "";
/*  938 */     String str2 = paramEntityItem.getEntityType();
/*  939 */     if (str2.equals("SVCMOD")) {
/*  940 */       str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "SVCMODSUBCATG");
/*  941 */       if (str1.equals("SCSC0004")) {
/*  942 */         bool = true;
/*      */       }
/*      */     } 
/*  945 */     return bool;
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
/* 1212 */     return "$Revision: 1.1 $";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\XMLMQRoot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */