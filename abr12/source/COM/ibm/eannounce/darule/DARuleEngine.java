/*     */ package COM.ibm.eannounce.darule;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.Serializable;
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
/*     */ public class DARuleEngine
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final String DARULE_SRCHACTION_NAME = "SRDDARULE";
/*  57 */   private Vector daRuleVct = new Vector();
/*  58 */   private String entityType = null;
/*  59 */   private String createDTS = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DALIFECYCLE_Draft = "10";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DALIFECYCLE_Ready = "20";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DALIFECYCLE_Production = "30";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DALIFECYCLE_Change = "50";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DALIFECYCLE_Retire = "60";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DARULETYPE_Substitution = "20";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DARULETYPE_ScanReplace = "30";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DARULETYPE_Equation = "40";
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final Hashtable DAENTITYTYPE_TBL = new Hashtable<>(); static {
/*  97 */     DAENTITYTYPE_TBL.put("FEATURE", "10");
/*  98 */     DAENTITYTYPE_TBL.put("MODEL", "20");
/*  99 */     DAENTITYTYPE_TBL.put("WWSEO", "30");
/* 100 */     DAENTITYTYPE_TBL.put("LSEO", "40");
/* 101 */     DAENTITYTYPE_TBL.put("LSEOBUNDLE", "50");
/* 102 */     DAENTITYTYPE_TBL.put("SVCMOD", "60");
/* 103 */     DAENTITYTYPE_TBL.put("SWFEATURE", "70");
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
/*     */   protected DARuleEngine(Database paramDatabase, Profile paramProfile, String paramString, StringBuffer paramStringBuffer) throws Exception {
/* 121 */     this.entityType = paramString;
/*     */     
/* 123 */     if (DAENTITYTYPE_TBL.get(paramString) == null) {
/* 124 */       throw new MiddlewareException(paramString + " is not supported");
/*     */     }
/*     */ 
/*     */     
/* 128 */     this.createDTS = paramDatabase.getNow(0);
/*     */ 
/*     */     
/* 131 */     loadRules(paramDatabase, paramProfile, paramStringBuffer);
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
/*     */   protected boolean updateCatData(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws Exception {
/* 148 */     boolean bool = false;
/* 149 */     String str = DARuleGroup.CATREL_TBL.get(this.entityType).toString();
/* 150 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, str, "CATDATA");
/* 151 */     if (this.daRuleVct.size() == 0) {
/* 152 */       DARuleEngineMgr.addDebugComment(0, paramStringBuffer, "updateCatData: No Production DARULEs found for " + paramEntityItem.getEntityType());
/*     */ 
/*     */       
/* 155 */       if (vector.size() > 0) {
/*     */         
/* 157 */         bool = true;
/* 158 */         for (byte b = 0; b < vector.size(); b++) {
/* 159 */           EntityItem entityItem = vector.elementAt(b);
/*     */           
/* 161 */           DARuleEngineMgr.addInformation(paramStringBuffer, "Deleted " + 
/* 162 */               DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem) + " for " + 
/* 163 */               DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, paramEntityItem));
/*     */         } 
/* 165 */         EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/* 166 */         vector.copyInto((Object[])arrayOfEntityItem);
/* 167 */         DARuleGroup.deleteCatdata(paramDatabase, paramProfile, arrayOfEntityItem);
/*     */         
/* 169 */         arrayOfEntityItem = null;
/*     */       } 
/*     */     } else {
/* 172 */       Vector<String> vector1 = new Vector();
/*     */       
/* 174 */       for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 175 */         DARuleGroup dARuleGroup = this.daRuleVct.elementAt(b);
/* 176 */         bool = (dARuleGroup.updateCatData(paramDatabase, paramProfile, paramEntityItem, paramStringBuffer) || bool) ? true : false;
/* 177 */         Vector<EntityItem> vector2 = dARuleGroup.getDARULEEntitys();
/* 178 */         for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 179 */           EntityItem entityItem = vector2.elementAt(b1);
/* 180 */           String str1 = PokUtils.getAttributeFlagValue(entityItem, "DAATTRIBUTECODE");
/* 181 */           if (!vector1.contains(str1)) {
/* 182 */             vector1.add(str1);
/*     */           }
/*     */         } 
/* 185 */         vector2.clear();
/*     */       } 
/*     */       
/* 188 */       if (vector.size() > 0) {
/* 189 */         Vector<EntityItem> vector2 = new Vector(); byte b1;
/* 190 */         for (b1 = 0; b1 < vector.size(); b1++) {
/* 191 */           EntityItem entityItem = vector.elementAt(b1);
/* 192 */           String str1 = PokUtils.getAttributeFlagValue(entityItem, "DAATTRIBUTECODE");
/* 193 */           if (!vector1.contains(str1)) {
/*     */ 
/*     */             
/* 196 */             DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "updateCatData: " + entityItem.getKey() + " attrcode " + str1 + " does not have a Production DARULE");
/*     */ 
/*     */             
/* 199 */             bool = true;
/* 200 */             vector2.add(entityItem);
/*     */           } 
/*     */         } 
/* 203 */         if (vector2.size() > 0) {
/* 204 */           for (b1 = 0; b1 < vector2.size(); b1++) {
/* 205 */             EntityItem entityItem = vector2.elementAt(b1);
/*     */             
/* 207 */             DARuleEngineMgr.addInformation(paramStringBuffer, "Deleted " + 
/* 208 */                 DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem) + " for " + 
/* 209 */                 DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, paramEntityItem));
/*     */           } 
/* 211 */           EntityItem[] arrayOfEntityItem = new EntityItem[vector2.size()];
/* 212 */           vector2.copyInto((Object[])arrayOfEntityItem);
/* 213 */           DARuleGroup.deleteCatdata(paramDatabase, paramProfile, arrayOfEntityItem);
/*     */           
/* 215 */           arrayOfEntityItem = null;
/* 216 */           vector2.clear();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     vector.clear();
/*     */     
/* 223 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getAllDARULEids() {
/* 231 */     Vector<Integer> vector = new Vector();
/* 232 */     for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 233 */       DARuleGroup dARuleGroup = this.daRuleVct.elementAt(b);
/* 234 */       Vector<EntityItem> vector1 = dARuleGroup.getDARULEEntitys();
/* 235 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 236 */         EntityItem entityItem = vector1.elementAt(b1);
/* 237 */         vector.add(new Integer(entityItem.getEntityID()));
/*     */       } 
/*     */     } 
/* 240 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dereference() {
/* 247 */     for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 248 */       DARuleGroup dARuleGroup = this.daRuleVct.elementAt(b);
/* 249 */       dARuleGroup.dereference();
/*     */     } 
/* 251 */     this.daRuleVct.clear();
/* 252 */     this.daRuleVct = null;
/* 253 */     this.entityType = null;
/* 254 */     this.createDTS = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getCreateDTS() {
/* 262 */     return this.createDTS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 269 */     StringBuffer stringBuffer = new StringBuffer("DARuleEngine: ");
/* 270 */     stringBuffer.append("DAENTITYTYPE: " + this.entityType + " \n");
/* 271 */     if (this.daRuleVct != null) {
/* 272 */       for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 273 */         DARuleGroup dARuleGroup = this.daRuleVct.elementAt(b);
/* 274 */         stringBuffer.append(dARuleGroup.toString() + "\n");
/*     */       } 
/*     */     } else {
/* 277 */       stringBuffer.append("No DARuleGroups");
/*     */     } 
/*     */     
/* 280 */     return stringBuffer.toString();
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
/*     */   private void loadRules(Database paramDatabase, Profile paramProfile, StringBuffer paramStringBuffer) throws Exception {
/* 295 */     for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 296 */       DARuleGroup dARuleGroup = this.daRuleVct.elementAt(b);
/* 297 */       dARuleGroup.dereference();
/*     */     } 
/* 299 */     this.daRuleVct.clear();
/* 300 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*     */     
/* 302 */     EntityItem[] arrayOfEntityItem = searchForDARules(paramDatabase, paramProfile, this.entityType, null, paramStringBuffer);
/* 303 */     if (arrayOfEntityItem != null) {
/* 304 */       for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 305 */         EntityItem entityItem = arrayOfEntityItem[b1];
/* 306 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "DARULETYPE");
/* 307 */         String str2 = PokUtils.getAttributeFlagValue(entityItem, "DAATTRIBUTECODE");
/* 308 */         String str3 = PokUtils.getAttributeFlagValue(entityItem, "DALIFECYCLE");
/* 309 */         DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "loadRules: " + entityItem.getKey() + " ruletype " + str1 + " attrcode " + str2 + " lifecycle " + str3);
/*     */ 
/*     */         
/* 312 */         if ("30".equals(str3)) {
/* 313 */           DARuleEquation dARuleEquation; DARuleSubstitution dARuleSubstitution = null;
/* 314 */           if ("20".equals(str1)) {
/* 315 */             dARuleSubstitution = new DARuleSubstitution(entityItem);
/* 316 */           } else if ("30".equals(str1)) {
/* 317 */             DARuleScanReplace dARuleScanReplace = new DARuleScanReplace(entityItem);
/* 318 */           } else if ("40".equals(str1)) {
/* 319 */             dARuleEquation = new DARuleEquation(entityItem);
/*     */           } 
/* 321 */           if (dARuleEquation != null) {
/* 322 */             DARuleGroup dARuleGroup = (DARuleGroup)hashtable.get(str2);
/* 323 */             if (dARuleGroup == null) {
/* 324 */               dARuleGroup = new DARuleGroup(str2, this.entityType);
/*     */ 
/*     */ 
/*     */               
/* 328 */               this.daRuleVct.add(dARuleGroup);
/* 329 */               hashtable.put(str2, dARuleGroup);
/*     */             } 
/* 331 */             dARuleGroup.addRuleItem(dARuleEquation);
/*     */           } else {
/* 333 */             DARuleEngineMgr.addDebugComment(0, paramStringBuffer, "loadRules: " + entityItem.getKey() + " ruletype " + str1 + " is not supported");
/*     */           } 
/*     */         } else {
/* 336 */           DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "loadRules: " + entityItem.getKey() + " lifecycle " + str3 + " is not used for offerings");
/*     */         } 
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
/*     */   public static EntityItem[] searchForDARules(Database paramDatabase, Profile paramProfile, String paramString1, String paramString2, StringBuffer paramStringBuffer) throws Exception {
/* 358 */     Vector<String> vector1 = new Vector(1);
/* 359 */     Vector<String> vector2 = new Vector(1);
/*     */     
/* 361 */     if (!DAENTITYTYPE_TBL.containsKey(paramString1)) {
/* 362 */       throw new IllegalArgumentException("Unsupported DAENTITYTYPE: " + paramString1);
/*     */     }
/* 364 */     vector1.addElement("DAENTITYTYPE");
/* 365 */     vector2.addElement(DAENTITYTYPE_TBL.get(paramString1));
/*     */     
/* 367 */     if (paramString2 != null) {
/* 368 */       vector1.addElement("DAATTRIBUTECODE");
/* 369 */       vector2.addElement(paramString2);
/*     */     } 
/*     */     
/* 372 */     EntityItem[] arrayOfEntityItem = null;
/* 373 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/* 375 */       arrayOfEntityItem = ABRUtil.doSearch(paramDatabase, paramProfile, "SRDDARULE", "DARULE", false, vector1, vector2, stringBuffer);
/*     */       
/* 377 */       if (stringBuffer.length() > 0) {
/* 378 */         DARuleEngineMgr.addDebugComment(3, paramStringBuffer, stringBuffer.toString());
/*     */       }
/* 380 */     } catch (Exception exception) {
/* 381 */       if (stringBuffer.length() > 0) {
/* 382 */         DARuleEngineMgr.addDebugComment(3, paramStringBuffer, stringBuffer.toString());
/*     */       }
/* 384 */       throw exception;
/*     */     } 
/*     */     
/* 387 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*     */       
/* 389 */       EntityList entityList = paramDatabase.getEntityList(paramProfile, new ExtractActionItem(null, paramDatabase, paramProfile, "dummy"), arrayOfEntityItem);
/* 390 */       arrayOfEntityItem = entityList.getParentEntityGroup().getEntityItemsAsArray();
/* 391 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 392 */         DARuleEngineMgr.addDebugComment(4, paramStringBuffer, "searchForDARules found " + arrayOfEntityItem[b].getKey());
/*     */       }
/*     */     } 
/*     */     
/* 396 */     vector1.clear();
/* 397 */     vector2.clear();
/* 398 */     return arrayOfEntityItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 407 */     return "$Revision: 1.6 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\DARuleEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */