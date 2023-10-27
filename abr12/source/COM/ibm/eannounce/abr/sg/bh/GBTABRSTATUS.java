/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collections;
/*     */ import java.util.Hashtable;
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
/*     */ public class GBTABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   private static final String SIEBELPRODLEV_10 = "PL0010";
/*  99 */   private static final Hashtable SIEBELPRODLEV_TBL = new Hashtable<>(); static {
/* 100 */     SIEBELPRODLEV_TBL.put("PL0035", "PL0030");
/* 101 */     SIEBELPRODLEV_TBL.put("PL0030", "PL0020");
/* 102 */     SIEBELPRODLEV_TBL.put("PL0020", "PL0010");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String DEL_RECTYPE = "RT0030";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String GBT_SRCHACTION_NAME = "SRDOIMDSGBT";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/* 123 */     return false;
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 155 */     setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
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
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 174 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "RECTYPE");
/* 175 */     addDebug(paramEntityItem.getKey() + " RECTYPE: " + str1);
/* 176 */     if ("RT0030".equals(str1)) {
/*     */       
/* 178 */       this.args[0] = getLD_Value(paramEntityItem, "RECTYPE");
/* 179 */       addResourceMsg("NOT_CHECKED", this.args);
/*     */       
/*     */       return;
/*     */     } 
/* 183 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "WWOCCODE");
/* 184 */     addDebug(paramEntityItem.getKey() + " wwoccode: " + str2);
/* 185 */     if (str2 == null) {
/*     */       
/* 187 */       this.args[0] = "";
/* 188 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "WWOCCODE", "WWOCCODE");
/* 189 */       addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 195 */     verifyUniqueWWOCCODE(str2, paramString);
/*     */ 
/*     */ 
/*     */     
/* 199 */     verifyParent(paramEntityItem, paramString);
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
/*     */   private void verifyUniqueWWOCCODE(String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 222 */     EntityItem[] arrayOfEntityItem = searchForGBT(paramString1, (String)null);
/*     */     
/* 224 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 1) {
/* 225 */       checkDateOverlap(arrayOfEntityItem, paramString2);
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
/*     */   private EntityItem[] searchForGBT(String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 240 */     Vector<String> vector1 = new Vector(1);
/* 241 */     vector1.addElement("WWOCCODE");
/* 242 */     Vector<String> vector2 = new Vector(1);
/* 243 */     vector2.addElement(paramString1);
/* 244 */     if (paramString2 != null) {
/* 245 */       vector1.addElement("SIEBELPRODLEV");
/* 246 */       vector2.addElement(paramString2);
/*     */     } 
/*     */     
/* 249 */     EntityItem[] arrayOfEntityItem = null;
/*     */     try {
/* 251 */       StringBuffer stringBuffer = new StringBuffer();
/* 252 */       addDebug("Searching for GBT for attrs: " + vector1 + " values: " + vector2);
/* 253 */       arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_prof, "SRDOIMDSGBT", "GBT", false, vector1, vector2, stringBuffer);
/*     */       
/* 255 */       if (stringBuffer.length() > 0) {
/* 256 */         addDebug(stringBuffer.toString());
/*     */       }
/* 258 */     } catch (SBRException sBRException) {
/*     */       
/* 260 */       StringWriter stringWriter = new StringWriter();
/* 261 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 262 */       addDebug("searchForGBT SBRException: " + stringWriter.getBuffer().toString());
/*     */     } 
/*     */     
/* 265 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 266 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 267 */         addDebug("searchForGBT found " + arrayOfEntityItem[b].getKey());
/*     */       }
/*     */     } else {
/* 270 */       addDebug("searchForGBT returned 0 ");
/*     */     } 
/*     */     
/* 273 */     vector1.clear();
/* 274 */     vector2.clear();
/* 275 */     return arrayOfEntityItem;
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
/*     */   private void checkDateOverlap(EntityItem[] paramArrayOfEntityItem, String paramString) throws SQLException, MiddlewareException {
/* 294 */     addDebug("checkDateOverlap entered for GBT eia.length: " + paramArrayOfEntityItem.length);
/* 295 */     Vector<UniqueCoverage> vector = new Vector();
/*     */     
/* 297 */     for (byte b1 = 0; b1 < paramArrayOfEntityItem.length; b1++) {
/* 298 */       EntityItem entityItem = paramArrayOfEntityItem[b1];
/* 299 */       String str1 = PokUtils.getAttributeValue(entityItem, "DELDATE", "", "9999-12-31", false);
/* 300 */       String str2 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 301 */       if (str2.compareTo(str1) > 0) {
/*     */         
/* 303 */         this.args[0] = getLD_NDN(entityItem);
/* 304 */         if (entityItem.getEntityType().equals(getEntityType()) && entityItem.getEntityID() == getEntityID()) {
/* 305 */           this.args[0] = "";
/*     */         }
/* 307 */         this.args[1] = getLD_Value(entityItem, "EFFECTIVEDATE");
/* 308 */         this.args[2] = getLD_Value(entityItem, "DELDATE", str1);
/* 309 */         addError("DATE_ERR", this.args);
/*     */       }
/*     */       else {
/*     */         
/* 313 */         UniqueCoverage uniqueCoverage1 = new UniqueCoverage(entityItem);
/* 314 */         vector.add(uniqueCoverage1);
/*     */       } 
/*     */     } 
/*     */     
/* 318 */     Collections.sort(vector);
/*     */     
/* 320 */     UniqueCoverage uniqueCoverage = vector.firstElement();
/* 321 */     addDebug("checkDateOverlap prevUc " + uniqueCoverage);
/* 322 */     Vector<String> vector1 = new Vector();
/*     */     byte b2;
/* 324 */     for (b2 = 1; b2 < vector.size(); b2++) {
/* 325 */       UniqueCoverage uniqueCoverage1 = vector.elementAt(b2);
/* 326 */       addDebug("   checkDateOverlap uc " + uniqueCoverage1);
/* 327 */       if (uniqueCoverage.deldate.compareTo(uniqueCoverage1.effdate) > 0) {
/*     */         
/* 329 */         if (!vector1.contains(uniqueCoverage1.item.getKey())) {
/* 330 */           this.args[0] = getLD_NDN(uniqueCoverage1.item);
/* 331 */           if (uniqueCoverage1.item.getEntityID() == getEntityID()) {
/* 332 */             this.args[0] = "";
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 337 */           createMessage(getCheck_W_W_E(paramString), "NOT_UNIQUE_ERR2", this.args);
/* 338 */           vector1.add(uniqueCoverage1.item.getKey());
/*     */         } 
/* 340 */         if (!vector1.contains(uniqueCoverage.item.getKey())) {
/* 341 */           this.args[0] = getLD_NDN(uniqueCoverage.item);
/* 342 */           if (uniqueCoverage.item.getEntityID() == getEntityID()) {
/* 343 */             this.args[0] = "";
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 348 */           createMessage(getCheck_W_W_E(paramString), "NOT_UNIQUE_ERR2", this.args);
/* 349 */           vector1.add(uniqueCoverage.item.getKey());
/*     */         } 
/*     */         
/* 352 */         this.args[0] = getLD_NDN(uniqueCoverage.item);
/* 353 */         this.args[1] = getLD_Value(uniqueCoverage.item, "DELDATE", uniqueCoverage.deldate);
/* 354 */         this.args[2] = getLD_NDN(uniqueCoverage1.item);
/* 355 */         this.args[3] = getLD_Value(uniqueCoverage1.item, "EFFECTIVEDATE");
/* 356 */         createMessage(getCheck_W_W_E(paramString), "DATE_RANGE_OVERLAP_ERR", this.args);
/*     */       } 
/* 358 */       uniqueCoverage = uniqueCoverage1;
/*     */     } 
/*     */ 
/*     */     
/* 362 */     for (b2 = 0; b2 < vector.size(); b2++) {
/* 363 */       UniqueCoverage uniqueCoverage1 = vector.elementAt(b2);
/* 364 */       uniqueCoverage1.dereference();
/*     */     } 
/*     */     
/* 367 */     vector.clear();
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
/*     */   private void verifyParent(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 396 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "SIEBELPRODLEV");
/* 397 */     addDebug("verifyParent SIEBELPRODLEV: " + str);
/* 398 */     if (str != null) {
/* 399 */       if (!"PL0010".equals(str)) {
/*     */         
/* 401 */         String str1 = PokUtils.getAttributeValue(paramEntityItem, "WWOCPARNTS", "", null, false);
/* 402 */         addDebug("verifyParent WWOCPARNTS: " + str1);
/* 403 */         if (str1 == null) {
/*     */           
/* 405 */           this.args[0] = "";
/* 406 */           this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "WWOCPARNTS", "WWOCPARNTS");
/* 407 */           addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 413 */         str1 = getFlagCodeForDesc(paramEntityItem, str1);
/* 414 */         addDebug("verifyParent wwocparnts after mapping: " + str1);
/* 415 */         if (str1 == null) {
/*     */           
/* 417 */           addError("MISSING_PARENT_ERR", (Object[])null);
/*     */           
/*     */           return;
/*     */         } 
/* 421 */         String str2 = (String)SIEBELPRODLEV_TBL.get(str);
/* 422 */         addDebug("verifyParent SIEBELPRODLEV parentvalue: " + str2);
/*     */ 
/*     */ 
/*     */         
/* 426 */         EntityItem[] arrayOfEntityItem = searchForGBT(str1, str2);
/*     */         
/* 428 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 429 */           boolean bool = false;
/* 430 */           for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 431 */             EntityItem entityItem = arrayOfEntityItem[b];
/* 432 */             String str3 = PokUtils.getAttributeValue(entityItem, "DELDATE", "", "9999-12-31", false);
/* 433 */             addDebug("verifyParent " + entityItem.getKey() + " deldate: " + str3);
/*     */             
/* 435 */             if (str3.equals("9999-12-31")) {
/* 436 */               bool = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 441 */           if (!bool)
/*     */           {
/*     */ 
/*     */             
/* 445 */             this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "DELDATE", "DELDATE");
/* 446 */             this.args[1] = "9999-12-31";
/* 447 */             createMessage(1, "MISSING_PARENT_DATE_ERR", this.args);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 452 */           createMessage(1, "MISSING_PARENT_ERR", (Object[])null);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 457 */       this.args[0] = "";
/* 458 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "SIEBELPRODLEV", "SIEBELPRODLEV");
/* 459 */       addError("REQ_NOTPOPULATED_ERR", this.args);
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
/*     */   private String getFlagCodeForDesc(EntityItem paramEntityItem, String paramString) {
/* 473 */     String str = null;
/*     */     
/* 475 */     EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)paramEntityItem.getEntityGroup().getMetaAttribute("WWOCCODE");
/* 476 */     for (byte b = 0; b < eANMetaFlagAttribute.getMetaFlagCount(); b++) {
/* 477 */       MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(b);
/* 478 */       if (metaFlag.toString().equals(paramString)) {
/* 479 */         str = metaFlag.getFlagCode();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 484 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 493 */     return "GBT ABR.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 504 */     return "$Revision: 1.2 $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class UniqueCoverage
/*     */     implements Comparable
/*     */   {
/* 516 */     EntityItem item = null;
/* 517 */     String deldate = "9999-12-31";
/* 518 */     String effdate = "1980-01-01";
/*     */     
/*     */     UniqueCoverage(EntityItem param1EntityItem) {
/* 521 */       this.item = param1EntityItem;
/* 522 */       this.deldate = PokUtils.getAttributeValue(this.item, "DELDATE", "", this.deldate, false);
/* 523 */       this.effdate = PokUtils.getAttributeValue(this.item, "EFFECTIVEDATE", "", this.effdate, false);
/* 524 */       GBTABRSTATUS.this.addDebug("UniqueCoverage " + this);
/*     */     }
/*     */     
/*     */     void dereference() {
/* 528 */       this.item = null;
/* 529 */       this.effdate = null;
/* 530 */       this.deldate = null;
/*     */     }
/*     */     
/*     */     public int compareTo(Object param1Object) {
/* 534 */       return this.effdate.compareTo(((UniqueCoverage)param1Object).effdate);
/*     */     }
/*     */     public String toString() {
/* 537 */       return this.item.getKey() + " effdate " + this.effdate + " deldate " + this.deldate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\GBTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */