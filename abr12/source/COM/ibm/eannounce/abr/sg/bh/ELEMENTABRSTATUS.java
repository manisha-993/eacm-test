/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.AttrComparator;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.Attribute;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ELEMENTABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*  175 */   private StringBuffer rptSb = new StringBuffer();
/*  176 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  177 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  178 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  180 */   private ResourceBundle rsBundle = null;
/*  181 */   private Hashtable metaTbl = new Hashtable<>();
/*  182 */   private int abr_debuglvl = 0;
/*  183 */   private String navName = "";
/*  184 */   private Vector skippedStatusVct = new Vector();
/*  185 */   private Hashtable queuedTbl = new Hashtable<>();
/*  186 */   private Vector withdrawnVct = new Vector();
/*  187 */   private Vector skippedABRVct = new Vector();
/*      */   
/*      */   private boolean hasWaitedBefore = false;
/*      */   
/*      */   private static final String ELEMENTCATDATA_SRCHACTION_NAME = "SRDELCATDATA";
/*      */   
/*      */   private static final String ABR_QUEUED = "0020";
/*      */   private static final String ABR_PASSED = "0030";
/*      */   private static final String ABR_INPROCESS = "0050";
/*      */   private static final String ECSACTIVE_Active = "ECS1";
/*      */   private static final String LIFECYCLE_Develop = "LF02";
/*      */   private static final String LIFECYCLE_Plan = "LF01";
/*      */   private static final String STATUS_FINAL = "0020";
/*      */   private static final String STATUS_R4REVIEW = "0040";
/*      */   private static final String FOREVER_DATE = "9999-12-31";
/*  202 */   private StringBuffer summarySb = new StringBuffer();
/*  203 */   private Vector vctReturnsEntityKeys = new Vector();
/*  204 */   private String strNow = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  244 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  246 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Entity Id: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{6}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {7} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  258 */     String str3 = "";
/*  259 */     String str4 = "";
/*      */     
/*  261 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */     
/*      */     try {
/*  265 */       long l = System.currentTimeMillis();
/*  266 */       start_ABRBuild();
/*      */       
/*  268 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */ 
/*      */       
/*  271 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  273 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  275 */       addDebug(0, "DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey());
/*      */ 
/*      */       
/*  278 */       addDebug(4, "Time to pull root VE: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */       
/*  281 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  286 */       this.navName = getNavigationName(entityItem);
/*      */       
/*  288 */       str3 = this.m_elist.getParentEntityGroup().getLongDescription() + ": " + this.navName;
/*      */ 
/*      */       
/*  291 */       Vector<?> vector = searchForELEMENTCATDATA(entityItem.getEntityType());
/*  292 */       if (vector != null) {
/*  293 */         this.strNow = this.m_db.getDates().getNow().substring(0, 10);
/*      */         
/*  295 */         if (vector.size() == 0) {
/*      */           
/*  297 */           addOutput(this.rsBundle.getString("NO_ACTIVE_FND"));
/*      */         } else {
/*      */           
/*  300 */           Collections.sort(vector, (Comparator<?>)new AttrComparator("ECSVENAME"));
/*  301 */           String str6 = null;
/*  302 */           String str7 = null;
/*  303 */           EntityList entityList = null;
/*  304 */           HashSet<String> hashSet = new HashSet();
/*  305 */           String str8 = "";
/*      */ 
/*      */ 
/*      */           
/*  309 */           for (byte b = 0; b < vector.size(); b++) {
/*  310 */             EntityItem entityItem1 = (EntityItem)vector.elementAt(b);
/*      */ 
/*      */             
/*  313 */             String str9 = PokUtils.getAttributeValue(entityItem1, "ECSVENAME", "", "", false);
/*  314 */             String str10 = PokUtils.getAttributeValue(entityItem1, "ECSIMPACTET", "", "", false);
/*  315 */             String str11 = PokUtils.getAttributeValue(entityItem1, "ECSSETATTR", "", "", false);
/*      */             
/*  317 */             addDebug(2, "activeVct[" + b + "] " + entityItem1.getKey() + " vename " + str9 + " impactedType " + str10 + " setAttr " + str11);
/*      */             
/*  319 */             String str12 = str10 + ":" + str11;
/*  320 */             if (hashSet.contains(str12)) {
/*  321 */               addDebug(1, "Skipping " + entityItem1.getKey() + " duplicate key " + str12);
/*      */               continue;
/*      */             } 
/*  324 */             hashSet.add(str12);
/*      */             
/*  326 */             if (str8 == null) {
/*  327 */               str8 = entityItem1.getEntityGroup().getLongDescription() + ": " + getNavigationName(entityItem1);
/*      */             }
/*      */ 
/*      */             
/*  331 */             if (str7 != null) {
/*  332 */               outputSummary(str7, str8);
/*      */             }
/*  334 */             str8 = entityItem1.getEntityGroup().getLongDescription() + ": " + getNavigationName(entityItem1);
/*      */             
/*  336 */             if (!str9.equals(str6)) {
/*  337 */               if (entityList != null) {
/*  338 */                 entityList.dereference();
/*      */               }
/*  340 */               str6 = str9;
/*      */               
/*  342 */               entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str6), new EntityItem[] { entityItem });
/*      */ 
/*      */               
/*  345 */               addDebug(0, "Extract " + str6 + NEWLINE + PokUtils.outputList(entityList));
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  350 */               if (str10.equals("WWSEO") && entityList
/*  351 */                 .getEntityGroup("WWSEO") == null) {
/*  352 */                 EntityList entityList1 = getWWSEOList(entityList);
/*  353 */                 if (entityList1 != null) {
/*  354 */                   entityList.dereference();
/*  355 */                   entityList = entityList1;
/*      */                 } else {
/*      */                   
/*      */                   continue;
/*      */                 } 
/*      */               } 
/*      */             } else {
/*  362 */               addDebug(1, "using same ve " + str9);
/*  363 */               if (!str10.equals(str7) && ("WWSEO"
/*  364 */                 .equals(str10) || "WWSEO".equals(str7))) {
/*  365 */                 if ("WWSEO".equals(str10)) {
/*  366 */                   addDebug(1, "types chgd, now wwseo impactedType " + str10 + " prevType " + str7);
/*  367 */                   if (entityList.getEntityGroup("WWSEO") == null) {
/*      */                     
/*  369 */                     EntityList entityList1 = getWWSEOList(entityList);
/*  370 */                     if (entityList1 != null) {
/*  371 */                       entityList.dereference();
/*  372 */                       entityList = entityList1;
/*      */                     } else {
/*      */                       
/*      */                       continue;
/*      */                     } 
/*      */                   } 
/*      */                 } else {
/*  379 */                   addDebug(1, "types chgd, was wwseo impactedType " + str10 + " prevType " + str7);
/*  380 */                   if (!entityList.getParentActionItem().getActionItemKey().equals(str6)) {
/*  381 */                     entityList.dereference();
/*      */                     
/*  383 */                     entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str6), new EntityItem[] { entityItem });
/*      */ 
/*      */                     
/*  386 */                     addDebug(0, "Extract " + str6 + NEWLINE + PokUtils.outputList(entityList));
/*      */                   } else {
/*  388 */                     addDebug(1, "types chgd, ve was not updated");
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */             } 
/*      */             
/*  394 */             str7 = str10;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  400 */             EntityGroup entityGroup = entityList.getEntityGroup(str10);
/*  401 */             if (entityGroup != null) {
/*      */ 
/*      */               
/*  404 */               EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(str11);
/*  405 */               if (eANMetaAttribute == null) {
/*  406 */                 addDebug(0, str11 + " was not in meta for " + entityGroup.getEntityType());
/*      */                 
/*  408 */                 this.args[0] = str11;
/*  409 */                 this.args[1] = entityGroup.getEntityType();
/*  410 */                 addError("ATTR_ERR", this.args);
/*      */               }
/*      */               else {
/*      */                 
/*  414 */                 String str = eANMetaAttribute.getActualLongDescription();
/*  415 */                 Vector vector1 = (Vector)this.queuedTbl.get(str);
/*  416 */                 if (vector1 == null) {
/*  417 */                   vector1 = new Vector();
/*  418 */                   this.queuedTbl.put(str, vector1);
/*      */                 } 
/*  420 */                 for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*  421 */                   EntityItem entityItem2 = entityGroup.getEntityItem(b1);
/*  422 */                   if (!checkABRStatus(entityItem2, str11)) {
/*      */                     
/*  424 */                     this.skippedABRVct.add(entityItem2.getKey());
/*      */ 
/*      */ 
/*      */                   
/*      */                   }
/*  429 */                   else if (!checkStatus(entityItem2)) {
/*      */                     
/*  431 */                     this.skippedStatusVct.add(entityItem2.getKey());
/*      */ 
/*      */                   
/*      */                   }
/*  435 */                   else if (!checkDates(entityItem2)) {
/*      */                     
/*  437 */                     this.withdrawnVct.add(entityItem2.getKey());
/*      */                   
/*      */                   }
/*      */                   else {
/*      */                     
/*  442 */                     queueABR(entityItem2, str11);
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } else {
/*      */               
/*  448 */               this.args[0] = str10;
/*  449 */               this.args[1] = str6;
/*  450 */               addError("VEDEF_ERR", this.args);
/*      */             } 
/*      */             
/*      */             continue;
/*      */           } 
/*  455 */           if (str7 != null) {
/*  456 */             outputSummary(str7, str8);
/*      */           }
/*      */           
/*  459 */           if (getReturnCode() == 0) {
/*  460 */             updatePDH();
/*      */           } else {
/*  462 */             addDebug(0, "Errors occured. PDH was not updated");
/*  463 */             this.summarySb.setLength(0);
/*      */           } 
/*      */           
/*  466 */           if (entityList != null) {
/*  467 */             entityList.dereference();
/*  468 */             entityList = null;
/*      */           } 
/*      */           
/*  471 */           addDebug(4, "Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*  472 */           hashSet.clear();
/*  473 */           vector.clear();
/*  474 */           this.queuedTbl.clear();
/*  475 */           this.skippedStatusVct.clear();
/*  476 */           this.withdrawnVct.clear();
/*  477 */           this.skippedABRVct.clear();
/*      */         }
/*      */       
/*      */       } 
/*  481 */     } catch (Throwable throwable) {
/*  482 */       StringWriter stringWriter = new StringWriter();
/*  483 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  484 */       String str7 = "<pre>{0}</pre>";
/*  485 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  486 */       setReturnCode(-3);
/*  487 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  489 */       this.args[0] = throwable.getMessage();
/*  490 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  491 */       messageFormat1 = new MessageFormat(str7);
/*  492 */       this.args[0] = stringWriter.getBuffer().toString();
/*  493 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  494 */       logError("Exception: " + throwable.getMessage());
/*  495 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  499 */       setDGTitle(this.navName);
/*  500 */       setDGRptName(getShortClassName(getClass()));
/*  501 */       setDGRptClass(getABRCode());
/*      */       
/*  503 */       if (!isReadOnly())
/*      */       {
/*  505 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  511 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  512 */     this.args[0] = getDescription();
/*  513 */     this.args[1] = str3;
/*  514 */     String str5 = messageFormat.format(this.args);
/*  515 */     messageFormat = new MessageFormat(str2);
/*  516 */     this.args[0] = this.m_prof.getOPName();
/*  517 */     this.args[1] = this.m_prof.getRoleDescription();
/*  518 */     this.args[2] = this.m_prof.getWGName();
/*  519 */     this.args[3] = getNow();
/*  520 */     this.args[4] = str3;
/*  521 */     this.args[5] = "" + this.m_abri.getEntityID();
/*  522 */     this.args[6] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  523 */     this.args[7] = str4 + " " + getABRVersion();
/*      */     
/*  525 */     this.rptSb.insert(0, str5 + messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  527 */     this.rptSb.append(this.summarySb.toString());
/*      */     
/*  529 */     println(this.rptSb.toString());
/*  530 */     printDGSubmitString();
/*  531 */     println(EACustom.getTOUDiv());
/*  532 */     buildReportFooter();
/*      */     
/*  534 */     this.metaTbl.clear();
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
/*      */   private EntityList getWWSEOList(EntityList paramEntityList) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  548 */     EntityGroup entityGroup = paramEntityList.getEntityGroup("PRODSTRUCT");
/*  549 */     if (entityGroup == null) {
/*      */       
/*  551 */       this.args[0] = "WWSEO";
/*  552 */       this.args[1] = "PRODSTRUCT";
/*  553 */       this.args[2] = paramEntityList.getParentActionItem().getActionItemKey();
/*  554 */       addError("VEDEF2_ERR", this.args);
/*  555 */       return null;
/*      */     } 
/*      */     
/*  558 */     EntityItem[] arrayOfEntityItem = null;
/*  559 */     if (entityGroup.getEntityItemCount() > 0) {
/*  560 */       arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */     } else {
/*      */       
/*  563 */       arrayOfEntityItem = new EntityItem[] { new EntityItem(null, this.m_prof, "PRODSTRUCT", 0) };
/*      */     } 
/*      */     
/*  566 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "XCDPRODSTRUCT"), arrayOfEntityItem);
/*      */ 
/*      */     
/*  569 */     addDebug(0, "getWWSEOList Extract XCDPRODSTRUCT" + NEWLINE + PokUtils.outputList(entityList));
/*  570 */     return entityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkABRStatus(EntityItem paramEntityItem, String paramString) {
/*  579 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*  580 */     addDebug(4, "checkABRStatus " + paramEntityItem.getKey() + " " + paramString + " value " + str);
/*      */     
/*  582 */     return ("0030".equals(str) || "0050".equals(str));
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
/*      */   private boolean checkStatus(EntityItem paramEntityItem) {
/*  597 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "STATUS");
/*  598 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "LIFECYCLE");
/*  599 */     addDebug(1, "checkStatus " + paramEntityItem.getKey() + "  status " + str1 + " lifecycle " + str2);
/*  600 */     if (str2 == null || str2.length() == 0) {
/*  601 */       str2 = "LF01";
/*      */     }
/*      */     
/*  604 */     return ("0020".equals(str1) || ("0040"
/*  605 */       .equals(str1) && ("LF01"
/*      */       
/*  607 */       .equals(str2) || "LF02".equals(str2))));
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
/*      */   private boolean checkDates(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  621 */     String str = paramEntityItem.getEntityType();
/*  622 */     if (str.equals("MODEL")) {
/*      */       
/*  624 */       String str1 = PokUtils.getAttributeValue(paramEntityItem, "WTHDRWEFFCTVDATE", "", "9999-12-31", false);
/*  625 */       addDebug(1, "checkDates " + paramEntityItem.getKey() + " wdDate: " + str1 + " now: " + this.strNow);
/*  626 */       return (this.strNow.compareTo(str1) < 0);
/*      */     } 
/*  628 */     if (str.equals("FEATURE")) {
/*      */       
/*  630 */       String str1 = PokUtils.getAttributeValue(paramEntityItem, "WITHDRAWDATEEFF_T", "", "9999-12-31", false);
/*  631 */       addDebug(1, "checkDates " + paramEntityItem.getKey() + " wdDate: " + str1 + " now: " + this.strNow);
/*  632 */       return (this.strNow.compareTo(str1) < 0);
/*      */     } 
/*  634 */     if (str.equals("WWSEO")) {
/*  635 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELWWSEO", "MODEL");
/*  636 */       if (vector.size() == 0) {
/*      */         
/*  638 */         this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + getNavigationName(paramEntityItem);
/*  639 */         addError("MODEL_ERR", this.args);
/*      */       } 
/*  641 */       byte b = 0; if (b < vector.size()) {
/*      */         
/*  643 */         EntityItem entityItem = vector.elementAt(b);
/*  644 */         String str1 = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", "", "9999-12-31", false);
/*  645 */         addDebug(1, "checkDates " + entityItem.getKey() + " wdDate: " + str1 + " now: " + this.strNow);
/*  646 */         return (this.strNow.compareTo(str1) < 0);
/*      */       } 
/*      */     } 
/*  649 */     return false;
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
/*      */   private void outputSummary(String paramString1, String paramString2) {
/*  671 */     this.summarySb.append("<br /><h2>" + paramString2 + "</h2>" + NEWLINE);
/*  672 */     this.summarySb.append("<h3>" + paramString1 + ":</h3>" + NEWLINE);
/*  673 */     this.summarySb.append("<table width='350'>" + NEWLINE);
/*  674 */     this.summarySb.append("<colgroup><col width=\"5%\"><col width=\"60%\"/><col width=\"35%\"/></colgroup>" + NEWLINE);
/*      */     
/*  676 */     String str1 = "";
/*  677 */     for (Enumeration<String> enumeration = this.queuedTbl.keys(); enumeration.hasMoreElements(); ) {
/*  678 */       str1 = enumeration.nextElement();
/*  679 */       Vector vector = (Vector)this.queuedTbl.get(str1);
/*      */       
/*  681 */       String str = this.rsBundle.getString("QUEUED_MSG");
/*  682 */       MessageFormat messageFormat = new MessageFormat(str);
/*  683 */       this.args[0] = str1;
/*  684 */       str = messageFormat.format(this.args);
/*      */       
/*  686 */       this.summarySb.append("<tr><td colspan='2'>" + str + "</td><td>" + vector.size() + "</td></tr>" + NEWLINE);
/*  687 */       vector.clear();
/*      */     } 
/*      */     
/*  690 */     this.summarySb.append("<tr><td colspan='3'>" + NEWLINE);
/*      */     
/*  692 */     String str2 = this.rsBundle.getString("NOT_PROC_MSG");
/*  693 */     this.summarySb.append("<span class=\"orange-med\"><b>" + str2 + "</b></span></td></tr>" + NEWLINE);
/*      */     
/*  695 */     str2 = this.rsBundle.getString("STATUS_MSG");
/*  696 */     this.summarySb.append("<tr><td>&nbsp;</td><td>" + str2 + "</td><td>" + this.skippedStatusVct.size() + "</td></tr>" + NEWLINE);
/*      */     
/*  698 */     str2 = this.rsBundle.getString("WITHDRAWN_MSG");
/*  699 */     this.summarySb.append("<tr><td>&nbsp;</td><td>" + str2 + "</td><td>" + this.withdrawnVct.size() + "</td></tr>" + NEWLINE);
/*  700 */     this.summarySb.append("<tr><td>&nbsp;</td><td>" + str1 + ":</td><td>" + this.skippedABRVct.size() + "</td></tr>" + NEWLINE);
/*      */     
/*  702 */     this.summarySb.append("</table>" + NEWLINE);
/*  703 */     this.queuedTbl.clear();
/*  704 */     this.skippedStatusVct.clear();
/*  705 */     this.withdrawnVct.clear();
/*  706 */     this.skippedABRVct.clear();
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
/*      */   private Vector searchForELEMENTCATDATA(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  722 */     EntityItem[] arrayOfEntityItem = null;
/*  723 */     Vector<EntityItem> vector = new Vector(1);
/*  724 */     Vector<String> vector1 = new Vector(2);
/*  725 */     Vector<String> vector2 = new Vector(2);
/*      */     
/*  727 */     vector2.addElement("ECSCHGET");
/*  728 */     vector2.addElement("ECSACTIVE");
/*      */     
/*  730 */     String str = null;
/*      */     
/*  732 */     PDGUtility pDGUtility = new PDGUtility();
/*      */     
/*  734 */     String[] arrayOfString = pDGUtility.getFlagCodeForExactDesc(this.m_db, this.m_prof, "ECSCHGET", paramString);
/*  735 */     pDGUtility.dereference();
/*  736 */     if (arrayOfString != null && arrayOfString.length > 0) {
/*  737 */       str = arrayOfString[0];
/*  738 */       addDebug(2, "searchForELEMENTCATDATA ECSCHGET desc : " + paramString + " flagcode " + str);
/*      */     } else {
/*  740 */       addDebug(0, "searchForELEMENTCATDATA NO match found for ECSCHGET desc : " + paramString);
/*      */       
/*  742 */       this.args[0] = "ECSCHGET";
/*  743 */       this.args[1] = paramString;
/*  744 */       addError("METADEF_ERR", this.args);
/*  745 */       return null;
/*      */     } 
/*      */     
/*  748 */     vector1.addElement(str);
/*  749 */     vector1.addElement("ECS1");
/*  750 */     addDebug(2, "searchForELEMENTCATDATA attrVct " + vector2 + " valVct " + vector1);
/*      */     try {
/*  752 */       StringBuffer stringBuffer = new StringBuffer();
/*  753 */       arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_prof, "SRDELCATDATA", "ELEMENTCATDATA", false, vector2, vector1, stringBuffer);
/*      */       
/*  755 */       if (stringBuffer.length() > 0) {
/*  756 */         addDebug(2, stringBuffer.toString());
/*      */       }
/*  758 */     } catch (SBRException sBRException) {
/*      */       
/*  760 */       StringWriter stringWriter = new StringWriter();
/*  761 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  762 */       addDebug(1, "searchForELEMENTCATDATA SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/*  764 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  765 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  766 */         addDebug(2, "searchForELEMENTCATDATA found " + arrayOfEntityItem[b].getKey());
/*  767 */         vector.add(arrayOfEntityItem[b]);
/*  768 */         arrayOfEntityItem[b] = null;
/*      */       } 
/*      */     }
/*      */     
/*  772 */     vector2.clear();
/*  773 */     vector1.clear();
/*      */     
/*  775 */     vector2 = null;
/*  776 */     vector1 = null;
/*      */     
/*  778 */     return vector;
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
/*      */   private void updatePDH() throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  792 */     logMessage(getDescription() + " updating PDH");
/*  793 */     addDebug(0, "updatePDH entered for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys.size());
/*      */     
/*  795 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */       try {
/*  797 */         this.m_db.update(this.m_prof, this.vctReturnsEntityKeys, false, false);
/*      */       } finally {
/*      */         
/*  800 */         this.vctReturnsEntityKeys.clear();
/*  801 */         this.m_db.commit();
/*  802 */         this.m_db.freeStatement();
/*  803 */         this.m_db.isPending("finally after updatePDH");
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
/*      */   private void queueABR(EntityItem paramEntityItem, String paramString) {
/*  820 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString + " set to: " + "0020");
/*  821 */     addDebug(2, "queueABR entered " + paramEntityItem.getKey() + " for " + paramString + " set to: " + "0020");
/*      */ 
/*      */     
/*  824 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString);
/*      */ 
/*      */ 
/*      */     
/*  828 */     if (!checkForInProcess(paramEntityItem, paramString)) {
/*      */       
/*  830 */       this.skippedABRVct.add(paramEntityItem);
/*      */       
/*      */       return;
/*      */     } 
/*  834 */     if (this.m_cbOn == null) {
/*  835 */       setControlBlock();
/*      */     }
/*      */     
/*  838 */     Vector<Attribute> vector = null;
/*      */     
/*  840 */     for (byte b1 = 0; b1 < this.vctReturnsEntityKeys.size(); b1++) {
/*  841 */       ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b1);
/*  842 */       if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/*  843 */         .getEntityType().equals(paramEntityItem.getEntityType())) {
/*  844 */         vector = returnEntityKey.m_vctAttributes;
/*      */         break;
/*      */       } 
/*      */     } 
/*  848 */     if (vector == null) {
/*  849 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*  850 */       vector = new Vector();
/*  851 */       returnEntityKey.m_vctAttributes = vector;
/*  852 */       this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */     } 
/*      */     
/*  855 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString, "0020", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */     
/*  859 */     for (byte b2 = 0; b2 < vector.size(); b2++) {
/*  860 */       Attribute attribute = vector.elementAt(b2);
/*  861 */       if (attribute.getAttributeCode().equals(paramString)) {
/*  862 */         singleFlag = null;
/*      */         break;
/*      */       } 
/*      */     } 
/*  866 */     if (singleFlag != null) {
/*  867 */       vector.addElement(singleFlag);
/*  868 */       String str = eANMetaAttribute.getActualLongDescription();
/*  869 */       Vector<EntityItem> vector1 = (Vector)this.queuedTbl.get(str);
/*  870 */       vector1.add(paramEntityItem);
/*      */     } else {
/*  872 */       addDebug(2, "queueABR:  " + paramEntityItem.getKey() + " " + paramString + " was already added for updates ");
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
/*      */   private boolean checkForInProcess(EntityItem paramEntityItem, String paramString) {
/*  886 */     boolean bool = true;
/*      */     try {
/*  888 */       byte b = 0;
/*      */       
/*  890 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*      */       
/*  892 */       addDebug(1, "checkForInProcess:  entered " + paramEntityItem.getKey() + " " + paramString + " is " + str);
/*      */       
/*  894 */       if ("0050".equals(str)) {
/*  895 */         logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString + " INPROCESS");
/*  896 */         DatePackage datePackage = this.m_db.getDates();
/*      */         
/*  898 */         this.m_prof.setValOnEffOn(datePackage.getEndOfDay(), datePackage.getEndOfDay());
/*      */         
/*  900 */         if (this.hasWaitedBefore) {
/*      */           
/*  902 */           EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Edit", false);
/*  903 */           EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, paramEntityItem.getEntityType(), paramEntityItem.getEntityID());
/*  904 */           str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/*  905 */           addDebug(1, "checkForInProcess: haswaitedbefore " + paramString + " is now " + str);
/*  906 */           entityGroup.dereference();
/*      */         } 
/*  908 */         this.hasWaitedBefore = true;
/*  909 */         while ("0050".equals(str) && b < 5) {
/*  910 */           b++;
/*  911 */           addDebug(1, "checkForInProcess: " + paramString + " is " + str + " sleeping 5 mins");
/*  912 */           logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString + " " + str + " sleeping 5 mins");
/*  913 */           Thread.sleep(300000L);
/*      */           
/*  915 */           EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Edit", false);
/*  916 */           EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, paramEntityItem.getEntityType(), paramEntityItem.getEntityID());
/*  917 */           str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/*  918 */           addDebug(1, "checkForInProcess: valon " + this.m_prof.getValOn() + " " + paramString + " is now " + str + " after sleeping");
/*  919 */           logMessage(getDescription() + " ***** valon " + this.m_prof.getValOn() + " " + paramEntityItem.getKey() + " " + paramString + " is now " + str + " after sleeping");
/*  920 */           datePackage = this.m_db.getDates();
/*      */           
/*  922 */           this.m_prof.setValOnEffOn(datePackage.getEndOfDay(), datePackage.getEndOfDay());
/*  923 */           entityGroup.dereference();
/*      */         } 
/*  925 */         bool = "0030".equals(str);
/*      */       } 
/*  927 */     } catch (Exception exception) {
/*  928 */       System.err.println("Exception in checkForInProcess " + exception);
/*  929 */       exception.printStackTrace();
/*      */     } 
/*  931 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/*  937 */     super.dereference();
/*      */     
/*  939 */     this.rsBundle = null;
/*  940 */     this.rptSb = null;
/*  941 */     this.args = null;
/*      */     
/*  943 */     this.metaTbl = null;
/*  944 */     this.navName = null;
/*  945 */     this.vctReturnsEntityKeys = null;
/*  946 */     this.skippedStatusVct.clear();
/*  947 */     this.skippedStatusVct = null;
/*  948 */     this.queuedTbl.clear();
/*  949 */     this.queuedTbl = null;
/*  950 */     this.withdrawnVct.clear();
/*  951 */     this.withdrawnVct = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/*  958 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDebug(int paramInt, String paramString) {
/*  966 */     if (paramInt <= this.abr_debuglvl) {
/*  967 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/*      */   private void addError(String paramString, Object[] paramArrayOfObject) {
/*  981 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*  982 */     setReturnCode(-1);
/*      */ 
/*      */     
/*  985 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString("ERROR_PREFIX"));
/*  986 */     Object[] arrayOfObject = new Object[2];
/*  987 */     arrayOfObject[0] = entityGroup.getLongDescription();
/*  988 */     arrayOfObject[1] = this.navName;
/*      */     
/*  990 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/*  999 */     String str = this.rsBundle.getString(paramString2);
/*      */     
/* 1001 */     if (paramArrayOfObject != null) {
/* 1002 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1003 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1006 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1016 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 1020 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1021 */     if (eANList == null) {
/* 1022 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1023 */       eANList = entityGroup.getMetaAttribute();
/* 1024 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1026 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1027 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1028 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1029 */       if (b + 1 < eANList.size()) {
/* 1030 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1034 */     return stringBuffer.toString().trim();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1040 */     return "$Revision: 1.2 $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1047 */     return "ELEMENTABRSTATUS";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\ELEMENTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */