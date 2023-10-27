/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPPSABRGEN
/*     */   extends SVCPACPRESELBase
/*     */ {
/*     */   private static final String CR_CATLGSVCPACPRESEL_ACTION = "CRCATLGSVCPACPRESEL";
/*     */   private static final String ATT_LSEOMKTGDESC = "LSEOMKTGDESC";
/*     */   private static final String ATT_PDHDOMAIN = "PDHDOMAIN";
/*     */   private static final String ATT_CATWORKFLOW = "CATWORKFLOW";
/*     */   private static final String CATWORKFLOW_New = "New";
/* 114 */   private String machtypeatr = null;
/* 115 */   private Vector bdlSeoidsVct = null;
/* 116 */   private Vector lseoSeoidsVct = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeThis() throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException {
/* 131 */     if (this.bdlSeoidsVct != null) {
/* 132 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "SERVPACLSEOBDL");
/* 133 */       addHeading(2, "LSEOBUNDLE ServicePac Generation");
/*     */       
/* 135 */       Vector<E> vector = searchForCATLGSVCPACPRESEL(false);
/*     */       
/* 137 */       for (byte b = 0; b < vector.size(); b++) {
/* 138 */         String str = vector.elementAt(b).toString();
/* 139 */         Vector<EntityItem> vector1 = doLSEOBDLChecks(str, extractActionItem);
/* 140 */         if (vector1.size() > 0) {
/*     */           
/* 142 */           createBdlCATLGSVCPACPRESEL(str, vector1);
/* 143 */           EntityItem entityItem = vector1.firstElement();
/* 144 */           entityItem.getEntityGroup().getEntityList().dereference();
/* 145 */           vector1.clear();
/*     */         } 
/*     */       } 
/* 148 */       addOutput("");
/*     */     } 
/*     */     
/* 151 */     if (this.lseoSeoidsVct != null) {
/* 152 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 153 */       addHeading(2, "LSEO ServicePac Generation");
/*     */       
/* 155 */       Vector<E> vector = searchForCATLGSVCPACPRESEL(true);
/* 156 */       Vector vector1 = new Vector();
/*     */       
/* 158 */       for (byte b = 0; b < vector.size(); b++) {
/* 159 */         String str = vector.elementAt(b).toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 168 */         addHeading(3, "Verify LSEO ServicePac SEOID " + str + " Availability");
/* 169 */         Vector vector2 = findLSEOForCheck1(str);
/* 170 */         if (vector2.size() > 0) {
/*     */           
/* 172 */           hashtable.put(str, vector2);
/* 173 */           vector1.addAll(vector2);
/*     */         } 
/*     */       } 
/*     */       
/* 177 */       if (hashtable.size() > 0) {
/* 178 */         ExtractActionItem extractActionItem1 = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTSERVPACKPDG1");
/*     */         
/* 180 */         EntityItem[] arrayOfEntityItem = null;
/* 181 */         if (vector1.size() > 0) {
/* 182 */           arrayOfEntityItem = new EntityItem[vector1.size()];
/* 183 */           vector1.copyInto((Object[])arrayOfEntityItem);
/* 184 */           vector1.clear();
/*     */         } 
/* 186 */         EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, extractActionItem1, arrayOfEntityItem);
/* 187 */         addDebug("Extract for LSEOs to mdl EXTSERVPACKPDG1 " + PokUtils.outputList(entityList));
/*     */         
/* 189 */         ExtractActionItem extractActionItem2 = new ExtractActionItem(null, this.m_db, this.m_prof, "SERVPACLSEO");
/*     */         
/* 191 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 192 */           String str = vector.elementAt(b1).toString();
/* 193 */           Vector vector2 = (Vector)hashtable.get(str);
/* 194 */           if (vector2 != null) {
/*     */ 
/*     */             
/* 197 */             addHeading(3, "Verify LSEO ServicePac SEOID " + str + " Technical Compatibility");
/* 198 */             boolean bool = doLSEOChecks(str, vector2, entityList, extractActionItem2);
/* 199 */             if (!bool)
/*     */             {
/* 201 */               createLseoCATLGSVCPACPRESEL(str, vector2);
/*     */             }
/*     */             
/* 204 */             vector2.clear();
/*     */           } 
/*     */         } 
/* 207 */         entityList.dereference();
/*     */         
/* 209 */         hashtable.clear();
/* 210 */         vector.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createLseoCATLGSVCPACPRESEL(String paramString, Vector<EntityItem> paramVector) throws MiddlewareRequestException, RemoteException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 243 */     addDebug("createLseoCATLGSVCPACPRESEL entered for seoid " + paramString + " lseovct " + paramVector.size());
/*     */     
/* 245 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 246 */       EntityItem entityItem1 = paramVector.elementAt(b);
/*     */       
/* 248 */       addHeading(3, "Create ServicePac for " + getLD_NDN(entityItem1));
/*     */       
/* 250 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem1, "WWSEOLSEO", "WWSEO");
/* 251 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(vector, "MODELWWSEO", "MODEL");
/* 252 */       EntityItem entityItem2 = vector1.firstElement();
/* 253 */       addDebug("createLseoCATLGSVCPACPRESEL " + entityItem1.getKey() + " " + entityItem2.getKey());
/*     */       
/* 255 */       vector.clear();
/* 256 */       vector1.clear();
/*     */       
/* 258 */       createCATLGSVCPACPRESEL(paramString, PokUtils.getAttributeValue(entityItem1, "LSEOMKTGDESC", "", "", false), 
/* 259 */           PokUtils.getAttributeValue(entityItem2, "COFSUBCAT", ", ", "", false), "PRE1");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createBdlCATLGSVCPACPRESEL(String paramString, Vector<EntityItem> paramVector) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException {
/* 291 */     addDebug("createBdlCATLGSVCPACPRESEL entered for seoid " + paramString + " bdlvct " + paramVector.size());
/*     */     
/* 293 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 294 */       EntityItem entityItem = paramVector.elementAt(b);
/* 295 */       addHeading(3, "Create ServicePac for " + getLD_NDN(entityItem));
/* 296 */       addDebug("createBdlCATLGSVCPACPRESEL " + entityItem.getKey());
/*     */       
/* 298 */       createCATLGSVCPACPRESEL(paramString, PokUtils.getAttributeValue(entityItem, "BUNDLMKTGDESC", "", "", false), 
/* 299 */           PokUtils.getAttributeValue(entityItem, "SVCPACBNDLTYPE", ", ", "", false), "PRE2");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createCATLGSVCPACPRESEL(String paramString1, String paramString2, String paramString3, String paramString4) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException {
/* 332 */     addDebug("createCATLGSVCPACPRESEL entered for seoid " + paramString1 + " LSEOMKTGDESC " + paramString2 + " SVCPACTYPE " + paramString3 + " PRESELSEOTYPE " + paramString4);
/*     */ 
/*     */     
/* 335 */     EntityItem entityItem1 = new EntityItem(null, getProfile(), "WG", getProfile().getWGID());
/*     */     
/* 337 */     Vector<String> vector1 = new Vector();
/* 338 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 339 */     vector1.addElement("LSEOMKTGDESC");
/* 340 */     hashtable.put("LSEOMKTGDESC", paramString2);
/* 341 */     vector1.addElement("CATMACHTYPE");
/* 342 */     hashtable.put("CATMACHTYPE", this.machtypeatr);
/* 343 */     vector1.addElement("CATSEOID");
/* 344 */     hashtable.put("CATSEOID", paramString1);
/* 345 */     vector1.addElement("SVCPACTYPE");
/* 346 */     hashtable.put("SVCPACTYPE", paramString3);
/*     */ 
/*     */     
/* 349 */     vector1.addElement("OFFCOUNTRY");
/* 350 */     hashtable.put("OFFCOUNTRY", this.offCountryFlag);
/* 351 */     vector1.addElement("PRESELINDC");
/* 352 */     hashtable.put("PRESELINDC", "Yes");
/* 353 */     vector1.addElement("CATWORKFLOW");
/* 354 */     hashtable.put("CATWORKFLOW", "New");
/* 355 */     vector1.addElement("PRESELSEOTYPE");
/* 356 */     hashtable.put("PRESELSEOTYPE", paramString4);
/*     */     
/* 358 */     vector1.addElement("PDHDOMAIN");
/*     */     
/* 360 */     String str = getProfile().getPDHDomainFlagCodes();
/* 361 */     StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 362 */     Vector<String> vector2 = new Vector(stringTokenizer.countTokens());
/* 363 */     while (stringTokenizer.hasMoreTokens()) {
/* 364 */       vector2.addElement(stringTokenizer.nextToken().trim());
/*     */     }
/*     */     
/* 367 */     hashtable.put("PDHDOMAIN", vector2);
/*     */     
/* 369 */     StringBuffer stringBuffer = new StringBuffer();
/* 370 */     EntityItem entityItem2 = null;
/*     */     
/*     */     try {
/* 373 */       entityItem2 = ABRUtil.createEntity(getDatabase(), getProfile(), "CRCATLGSVCPACPRESEL", entityItem1, "CATLGSVCPACPRESEL", vector1, hashtable, stringBuffer);
/*     */ 
/*     */       
/* 376 */       this.args[0] = getLD_NDN(entityItem2);
/* 377 */       addMessage("", "CREATE_SUCCESS", this.args);
/* 378 */     } catch (EANBusinessRuleException eANBusinessRuleException) {
/* 379 */       throw eANBusinessRuleException;
/*     */     } finally {
/* 381 */       if (stringBuffer.length() > 0) {
/* 382 */         addDebug(stringBuffer.toString());
/*     */       }
/* 384 */       if (entityItem2 == null) {
/* 385 */         setReturnCode(-1);
/* 386 */         addOutput("ERROR: Can not create CATLGSVCPACPRESEL entity for seoid: " + paramString1);
/*     */       } 
/* 388 */       vector1.clear();
/* 389 */       hashtable.clear();
/* 390 */       vector2.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean doLSEOChecks(String paramString, Vector<EntityItem> paramVector, EntityList paramEntityList, ExtractActionItem paramExtractActionItem) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 426 */     boolean bool = false;
/*     */     
/* 428 */     addDebug("doLSEOChecks seoid: " + paramString);
/*     */     
/* 430 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/*     */     
/* 432 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramVector.size()];
/* 433 */     paramVector.copyInto((Object[])arrayOfEntityItem);
/* 434 */     for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 435 */       EntityItem entityItem = arrayOfEntityItem[b];
/* 436 */       paramVector.remove(entityItem);
/* 437 */       entityItem = entityGroup.getEntityItem(entityItem.getKey());
/* 438 */       paramVector.addElement(entityItem);
/*     */       
/* 440 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "WWSEO");
/* 441 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(vector, "MODELWWSEO", "MODEL");
/* 442 */       addDebug("doLSEOChecks checking " + entityItem.getKey() + " wwseovct " + vector.size() + " mdlvct " + vector1.size());
/*     */ 
/*     */       
/* 445 */       boolean bool1 = false;
/* 446 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 447 */         EntityItem entityItem1 = vector1.elementAt(b1);
/* 448 */         String str = PokUtils.getAttributeFlagValue(entityItem1, "COFCAT");
/* 449 */         addDebug("doLSEOChecks checking " + entityItem1.getKey() + " COFCAT:" + str);
/* 450 */         if ("102".equals(str)) {
/* 451 */           bool1 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 456 */       if (!bool1) {
/* 457 */         bool = true;
/*     */         
/* 459 */         this.args[0] = getLD_NDN(entityItem);
/* 460 */         String str = "Not found";
/* 461 */         if (vector1.size() > 0) {
/* 462 */           str = getLD_NDN(vector1.elementAt(0));
/*     */         }
/* 464 */         this.args[1] = str;
/* 465 */         addError("SVCPAC_ERR2", this.args);
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 472 */         EntityItem[] arrayOfEntityItem1 = new EntityItem[vector.size()];
/* 473 */         vector.copyInto((Object[])arrayOfEntityItem1);
/*     */         
/* 475 */         boolean bool2 = verifySEOtechCompat(arrayOfEntityItem1, this.machtypeatr, paramExtractActionItem);
/* 476 */         if (!bool2) {
/* 477 */           bool = true;
/*     */           
/* 479 */           this.args[0] = getLD_NDN(entityItem);
/* 480 */           this.args[1] = this.machtypeatr;
/* 481 */           addError("MACHTYPE_MSG", this.args);
/*     */         } 
/*     */         
/* 484 */         vector.clear();
/* 485 */         vector1.clear();
/*     */       } 
/*     */     } 
/* 488 */     return bool;
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
/*     */   protected void verifyRequest() {
/* 501 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */     
/* 503 */     this.offCountryFlag = PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY");
/* 504 */     this.offCountry = PokUtils.getAttributeValue(entityItem, "OFFCOUNTRY", "", "<em>** Not Populated **</em>", false);
/*     */     
/* 506 */     this.machtypeatr = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
/*     */     
/* 508 */     String str1 = PokUtils.getAttributeValue(entityItem, "LSEOIDLIST", "", null, false);
/* 509 */     String str2 = PokUtils.getAttributeValue(entityItem, "BDLSEOIDLIST", "", null, false);
/*     */     
/* 511 */     addDebug("verifyRequest: " + entityItem.getKey() + " offCountryFlag " + this.offCountryFlag + " offCountry " + this.offCountry + " machtypeatr " + this.machtypeatr + " lseoList " + str1 + " bdlList " + str2);
/*     */ 
/*     */     
/* 514 */     if (this.offCountryFlag == null) {
/*     */       
/* 516 */       this.args[0] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "OFFCOUNTRY", "OFFCOUNTRY");
/* 517 */       addError("MISSING_ERR", this.args);
/*     */     } 
/* 519 */     if (this.machtypeatr == null) {
/*     */       
/* 521 */       this.args[0] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "MACHTYPEATR", "MACHTYPEATR");
/* 522 */       addError("MISSING_ERR", this.args);
/*     */     } 
/* 524 */     if (str1 == null && str2 == null) {
/*     */       
/* 526 */       this.args[0] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "LSEOIDLIST", "LSEOIDLIST") + " and " + 
/* 527 */         PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "BDLSEOIDLIST", "BDLSEOIDLIST");
/* 528 */       addError("MISSING_ERR", this.args);
/*     */     } 
/*     */     
/* 531 */     if (str1 != null) {
/* 532 */       this.lseoSeoidsVct = verifySeoids("LSEOIDLIST", str1);
/*     */     }
/* 534 */     if (str2 != null) {
/* 535 */       this.bdlSeoidsVct = verifySeoids("BDLSEOIDLIST", str2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector verifySeoids(String paramString1, String paramString2) {
/* 546 */     Vector<String> vector = new Vector();
/* 547 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString2, ",");
/* 548 */     while (stringTokenizer.hasMoreTokens()) {
/* 549 */       String str = stringTokenizer.nextToken().trim();
/* 550 */       if (str.length() == 7) {
/* 551 */         if (vector.contains(str)) {
/*     */           
/* 553 */           this.args[0] = str;
/* 554 */           this.args[1] = PokUtils.getAttributeDescription(this.m_elist.getParentEntityGroup(), paramString1, paramString1);
/* 555 */           addError("DUPLICATE_SEOID_ERR", this.args); continue;
/*     */         } 
/* 557 */         vector.addElement(str);
/*     */         
/*     */         continue;
/*     */       } 
/* 561 */       this.args[0] = str;
/* 562 */       this.args[1] = PokUtils.getAttributeDescription(this.m_elist.getParentEntityGroup(), paramString1, paramString1);
/* 563 */       addError("INVALID_SEOID_ERR", this.args);
/*     */     } 
/*     */ 
/*     */     
/* 567 */     return vector;
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
/*     */   private Vector doLSEOBDLChecks(String paramString, ExtractActionItem paramExtractActionItem) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 602 */     boolean bool = false;
/* 603 */     addHeading(3, "Verify LSEOBUNDLE ServicePac SEOID " + paramString + " Availability");
/*     */ 
/*     */     
/* 606 */     Vector<EntityItem> vector = findLSEOBDLForCheck1(paramString);
/*     */     
/* 608 */     if (vector.size() > 0) {
/* 609 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "dummy");
/* 610 */       EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/* 611 */       vector.copyInto((Object[])arrayOfEntityItem);
/* 612 */       vector.clear();
/*     */       
/* 614 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, extractActionItem, arrayOfEntityItem);
/* 615 */       addDebug("Extract for LSEOBUNDLEs attr chks " + PokUtils.outputList(entityList));
/*     */       
/* 617 */       for (byte b = 0; b < entityList.getParentEntityGroup().getEntityItemCount(); b++) {
/* 618 */         EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(b);
/* 619 */         String str = getAttributeFlagEnabledValue(entityItem, "BUNDLETYPE");
/* 620 */         addDebug("doLSEOBDLChecks " + entityItem.getKey() + " bdltype " + str);
/*     */ 
/*     */         
/* 623 */         if (!"102".equals(str)) {
/* 624 */           bool = true;
/*     */           
/* 626 */           this.args[0] = getLD_NDN(entityItem);
/* 627 */           this.args[1] = getLD_Value(entityItem, "BUNDLETYPE");
/* 628 */           this.args[2] = getLD_Value(entityItem, "SVCPACBNDLTYPE");
/* 629 */           addError("SVCPAC_ERR", this.args);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 634 */           addHeading(3, "Verify LSEOBUNDLE ServicePac SEOID " + paramString + " Technical Compatibility");
/*     */           
/* 636 */           boolean bool1 = verifySEOtechCompat(new EntityItem[] { new EntityItem(null, this.m_prof, entityItem
/* 637 */                   .getEntityType(), entityItem
/* 638 */                   .getEntityID()) }this.machtypeatr, paramExtractActionItem);
/* 639 */           if (!bool1) {
/* 640 */             bool = true;
/*     */             
/* 642 */             this.args[0] = getLD_NDN(entityItem);
/* 643 */             this.args[1] = this.machtypeatr;
/* 644 */             addError("MACHTYPE_MSG", this.args);
/*     */           } else {
/*     */             
/* 647 */             vector.add(entityItem);
/*     */           } 
/*     */         } 
/* 650 */       }  if (bool && 
/* 651 */         vector.size() > 0) {
/* 652 */         EntityItem entityItem = vector.firstElement();
/* 653 */         entityItem.getEntityGroup().getEntityList().dereference();
/* 654 */         vector.clear();
/*     */       } 
/*     */     } else {
/*     */       
/* 658 */       bool = true;
/*     */     } 
/*     */     
/* 661 */     return vector;
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
/*     */   private Vector searchForCATLGSVCPACPRESEL(boolean paramBoolean) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 688 */     Vector<String> vector1 = new Vector();
/*     */     
/* 690 */     Vector<String> vector2 = new Vector(2);
/* 691 */     vector2.addElement("OFFCOUNTRY");
/* 692 */     vector2.addElement("CATMACHTYPE");
/* 693 */     vector2.addElement("CATSEOID");
/*     */     
/* 695 */     Vector<String> vector3 = new Vector(2);
/* 696 */     vector3.addElement(this.offCountryFlag);
/* 697 */     vector3.addElement(this.machtypeatr);
/*     */     
/* 699 */     Vector<E> vector = this.bdlSeoidsVct;
/* 700 */     if (paramBoolean) {
/* 701 */       vector = this.lseoSeoidsVct;
/*     */     }
/* 703 */     for (byte b = 0; b < vector.size(); b++) {
/* 704 */       String str = vector.elementAt(b).toString();
/* 705 */       vector3.addElement(str);
/*     */       
/* 707 */       addDebug("searchForCATLGSVCPACPRESEL searching for OFFCOUNTRY " + this.offCountryFlag + " CATMACHTYPE " + this.machtypeatr + " CATSEOID " + str + " check PRESELSEOTYPE " + (paramBoolean ? "PRE1" : "PRE2"));
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 712 */         StringBuffer stringBuffer = new StringBuffer();
/*     */         
/* 714 */         Profile profile = this.m_prof.getNewInstance(this.m_db);
/* 715 */         String str1 = this.m_db.getDates().getEndOfDay();
/* 716 */         profile.setValOnEffOn(str1, str1);
/* 717 */         addDebug("Set tmp profile to eod " + str1);
/*     */         
/* 719 */         EntityItem[] arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), profile, "SRDCATLGSVCPACPRESEL1", "CATLGSVCPACPRESEL", false, vector2, vector3, stringBuffer);
/*     */         
/* 721 */         if (stringBuffer.length() > 0) {
/* 722 */           addDebug(stringBuffer.toString());
/*     */         }
/* 724 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 725 */           for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*     */             
/* 727 */             String str2 = PokUtils.getAttributeFlagValue(arrayOfEntityItem[b1], "PRESELSEOTYPE");
/* 728 */             addDebug("searchForCATLGSVCPACPRESEL found " + arrayOfEntityItem[b1].getKey() + " for CATSEOID " + str + " with PRESELSEOTYPE " + str2);
/*     */ 
/*     */             
/* 731 */             if (paramBoolean) {
/* 732 */               this.args[0] = getLD_NDN(arrayOfEntityItem[b1]);
/* 733 */               this.args[1] = getLD_Value(arrayOfEntityItem[b1], "PRESELSEOTYPE");
/* 734 */               this.args[2] = str;
/* 735 */               if ("PRE2".equals(str2)) {
/*     */ 
/*     */                 
/* 738 */                 this.args[3] = "LSEO";
/* 739 */                 addError("MISMATCH_ERR", this.args);
/*     */               } else {
/*     */                 
/* 742 */                 addError("EXISTS_ERR", this.args);
/*     */               } 
/*     */             } else {
/* 745 */               this.args[0] = getLD_NDN(arrayOfEntityItem[b1]);
/* 746 */               this.args[1] = getLD_Value(arrayOfEntityItem[b1], "PRESELSEOTYPE");
/* 747 */               this.args[2] = str;
/* 748 */               if ("PRE1".equals(str2) || str2 == null) {
/*     */ 
/*     */                 
/* 751 */                 this.args[3] = "LSEOBUNDLE";
/* 752 */                 addError("MISMATCH_ERR", this.args);
/*     */               } else {
/*     */                 
/* 755 */                 addError("EXISTS_ERR", this.args);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } else {
/* 760 */           vector1.add(str);
/*     */         } 
/* 762 */       } catch (SBRException sBRException) {
/*     */         
/* 764 */         StringWriter stringWriter = new StringWriter();
/* 765 */         sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 766 */         addDebug("searchForCATLGSVCPACPRESEL SBRException: " + stringWriter.getBuffer().toString());
/*     */       } 
/*     */       
/* 769 */       vector3.removeElement(str);
/*     */     } 
/*     */     
/* 772 */     vector2.clear();
/* 773 */     vector3.clear();
/*     */     
/* 775 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dereference() {
/* 782 */     this.machtypeatr = null;
/* 783 */     if (this.bdlSeoidsVct != null) {
/* 784 */       this.bdlSeoidsVct.clear();
/* 785 */       this.bdlSeoidsVct = null;
/*     */     } 
/* 787 */     if (this.lseoSeoidsVct != null) {
/* 788 */       this.lseoSeoidsVct.clear();
/* 789 */       this.lseoSeoidsVct = null;
/*     */     } 
/*     */     
/* 792 */     super.dereference();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 801 */     return "Service Pack Preselect Generator ABR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 810 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 819 */     return "$Id: SPPSABRGEN.java,v 1.1 2010/02/08 14:51:46 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 828 */     return "SPPSABRGEN.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SPPSABRGEN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */