/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
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
/*     */ public class BHCATLGORABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*  84 */   private static final String[] LSEOABRS = new String[] { "ADSABRSTATUS" };
/*  85 */   private static final String[] MODELABRS = new String[] { "ADSABRSTATUS" };
/*  86 */   private static final String[] PRODSTRUCTABRS = new String[] { "ADSABRSTATUS" };
/*  87 */   private static final String[] SWPRODSTRUCTABRS = new String[] { "ADSABRSTATUS" };
/*  88 */   private static final String[] LSEOBUNDLEABRS = new String[] { "ADSABRSTATUS" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 147 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/* 148 */     int i = getCheck_W_E_E(paramString);
/* 149 */     ArrayList arrayList = new ArrayList();
/* 150 */     getCountriesAsList(paramEntityItem, arrayList, i);
/* 151 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/* 152 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MDLBHCATLGOR", "MODEL");
/* 161 */     for (byte b = 0; b < vector.size(); b++) {
/* 162 */       EntityItem entityItem = vector.elementAt(b);
/* 163 */       int j = getCheckLevel(i, entityItem, "ANNDATE");
/* 164 */       addDebug("check model:" + entityItem.getKey());
/* 165 */       checkAvails(paramEntityItem, arrayList, entityItem, "MODELAVAIL", j, true);
/*     */     } 
/* 167 */     vector.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOBHCATLGOR", "LSEO");
/* 181 */     for (EntityItem entityItem1 : vector1) {
/*     */       
/* 183 */       Vector<EntityItem> vector5 = PokUtils.getAllLinkedEntities(entityItem1, "WWSEOLSEO", "WWSEO");
/* 184 */       EntityItem entityItem2 = vector5.get(0);
/* 185 */       String str = PokUtils.getAttributeFlagValue(entityItem2, "SPECBID");
/* 186 */       if ("11457".equals(str)) {
/* 187 */         checkAvails(paramEntityItem, arrayList, entityItem1, "LSEOAVAIL", i, true); continue;
/*     */       } 
/* 189 */       checkCountryList(paramEntityItem, entityItem1, arrayList, i);
/*     */     } 
/*     */     
/* 192 */     vector1.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     Vector vector2 = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOBUNDLEBHCATLGOR", "LSEOBUNDLE");
/* 205 */     for (EntityItem entityItem : vector2) {
/*     */       
/* 207 */       String str = PokUtils.getAttributeFlagValue(entityItem, "SPECBID");
/* 208 */       if ("11457".equals(str)) {
/* 209 */         checkAvails(paramEntityItem, arrayList, entityItem, "LSEOBUNDLEAVAIL", i, true); continue;
/*     */       } 
/* 211 */       checkCountryList(paramEntityItem, entityItem, arrayList, i);
/*     */     } 
/*     */     
/* 214 */     vector2.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     Vector vector3 = PokUtils.getAllLinkedEntities(paramEntityItem, "PRODSTRUCTBHCATLGOR", "PRODSTRUCT");
/* 228 */     for (EntityItem entityItem1 : vector3) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 233 */       EntityItem entityItem2 = getlinkeditem(entityItem1, "FEATURE");
/* 234 */       EntityItem entityItem3 = getlinkeditem(entityItem1, "MODEL");
/* 235 */       addDebug(entityItem2.getEntityType() + entityItem2.getEntityID());
/* 236 */       addDebug(entityItem3.getEntityType() + entityItem3.getEntityID());
/* 237 */       int j = getCheckLevel(i, entityItem3, "ANNDATE");
/* 238 */       addDebug("level1: " + i + " and level2 : " + j);
/* 239 */       boolean bool = isRPQ(entityItem2);
/* 240 */       if (!bool) {
/* 241 */         addDebug("ps - Not RPQ checking");
/* 242 */         checkAvails(paramEntityItem, arrayList, entityItem1, "OOFAVAIL", j, true); continue;
/*     */       } 
/* 244 */       addDebug("ps - RPQ checking");
/* 245 */       checkCountryList(paramEntityItem, entityItem2, arrayList, j);
/*     */     } 
/*     */     
/* 248 */     vector3.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     Vector vector4 = PokUtils.getAllLinkedEntities(paramEntityItem, "SWPRODSTRBHCATLGOR", "SWPRODSTRUCT");
/* 263 */     for (EntityItem entityItem1 : vector4) {
/*     */ 
/*     */       
/* 266 */       EntityItem entityItem2 = getlinkeditem(entityItem1, "MODEL");
/* 267 */       int j = getCheckLevel(i, entityItem2, "ANNDATE");
/*     */       
/* 269 */       String str = PokUtils.getAttributeFlagValue(entityItem2, "SPECBID");
/* 270 */       if ("11457".equals(str)) {
/* 271 */         addDebug("swps - Not specialbid");
/* 272 */         checkAvails(paramEntityItem, arrayList, entityItem1, "SWPRODSTRUCTAVAIL", j, true); continue;
/*     */       } 
/* 274 */       addDebug("swps - specialbid");
/* 275 */       checkAvails(paramEntityItem, arrayList, entityItem2, "MODELAVAIL", j, false);
/*     */     } 
/*     */     
/* 278 */     vector4.clear();
/*     */   }
/*     */   private EntityItem getlinkeditem(EntityItem paramEntityItem, String paramString) {
/*     */     byte b;
/* 282 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 283 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 284 */       if (entityItem.getEntityType().equals(paramString)) {
/* 285 */         return entityItem;
/*     */       }
/*     */     } 
/* 288 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 289 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 290 */       if (entityItem.getEntityType().equals(paramString)) {
/* 291 */         return entityItem;
/*     */       }
/*     */     } 
/* 294 */     return null;
/*     */   }
/*     */   
/*     */   private void checkAvails(EntityItem paramEntityItem1, ArrayList<?> paramArrayList, EntityItem paramEntityItem2, String paramString, int paramInt, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 298 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem2, paramString, "AVAIL");
/* 299 */     ArrayList<?> arrayList = new ArrayList();
/* 300 */     boolean bool = false;
/* 301 */     for (EntityItem entityItem : vector) {
/*     */       
/* 303 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE");
/* 304 */       addDebug(entityItem.getKey() + " availtype " + str);
/* 305 */       if ("146".equals(str)) {
/* 306 */         ArrayList arrayList1 = new ArrayList();
/* 307 */         getCountriesAsList(entityItem, arrayList1, paramInt);
/* 308 */         addDebug(entityItem.getKey() + " countrylist " + arrayList1);
/* 309 */         arrayList.addAll(arrayList1);
/* 310 */         bool = true;
/*     */       } 
/*     */     } 
/* 313 */     if (!paramBoolean && !bool) {
/*     */       return;
/*     */     }
/* 316 */     if (!arrayList.containsAll(paramArrayList)) {
/*     */       
/* 318 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 319 */       addDebug("All Avails' ctrylist: " + arrayList + " | " + paramEntityItem1.getKey() + " catlgorCtry: " + paramArrayList);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 324 */       this.args[0] = getLD_NDN(paramEntityItem1);
/* 325 */       this.args[1] = paramEntityItem2.getEntityGroup().getLongDescription();
/* 326 */       this.args[2] = entityGroup.getLongDescription();
/* 327 */       ArrayList<?> arrayList1 = new ArrayList();
/* 328 */       arrayList1.addAll(paramArrayList);
/* 329 */       arrayList1.removeAll(arrayList);
/* 330 */       this.args[3] = getUnmatchedDescriptions(paramEntityItem1, "COUNTRYLIST", arrayList1);
/* 331 */       createMessage(paramInt, "BH_COUNTRY_MISMATCH_ERR2", this.args);
/* 332 */       arrayList1.clear();
/*     */     } 
/* 334 */     vector.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkCountryList(EntityItem paramEntityItem1, EntityItem paramEntityItem2, ArrayList<?> paramArrayList, int paramInt) throws SQLException, MiddlewareException {
/* 339 */     ArrayList<?> arrayList = new ArrayList();
/* 340 */     getCountriesAsList(paramEntityItem2, arrayList, paramInt);
/* 341 */     if (!arrayList.containsAll(paramArrayList)) {
/* 342 */       addDebug(paramEntityItem2.getKey() + " ctrylist " + arrayList + " catlgorCtry " + paramArrayList);
/*     */ 
/*     */       
/* 345 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*     */       
/* 347 */       this.args[1] = getLD_NDN(paramEntityItem2);
/* 348 */       ArrayList<?> arrayList1 = new ArrayList();
/* 349 */       arrayList1.addAll(paramArrayList);
/* 350 */       arrayList1.removeAll(arrayList);
/* 351 */       this.args[2] = getUnmatchedDescriptions(paramEntityItem1, "COUNTRYLIST", arrayList1);
/* 352 */       createMessage(paramInt, "BH_COUNTRY_MISMATCH_ERR1", this.args);
/* 353 */       arrayList1.clear();
/*     */     } 
/* 355 */     arrayList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 361 */     return "BHCATLGOR ABR.";
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 387 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 388 */     verifyFinalAndQueue(entityItem, "LSEOBUNDLEBHCATLGOR", "LSEOBUNDLE", LSEOBUNDLEABRS);
/* 389 */     verifyFinalAndQueue(entityItem, "LSEOBHCATLGOR", "LSEO", LSEOABRS);
/* 390 */     verifyFinalAndQueue(entityItem, "MDLBHCATLGOR", "MODEL", MODELABRS);
/* 391 */     verifyFinalAndQueue(entityItem, "PRODSTRUCTBHCATLGOR", "PRODSTRUCT", PRODSTRUCTABRS);
/* 392 */     verifyFinalAndQueue(entityItem, "SWPRODSTRBHCATLGOR", "SWPRODSTRUCT", SWPRODSTRUCTABRS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 400 */     super.completeNowR4RProcessing();
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
/*     */   private void verifyFinalAndQueue(EntityItem paramEntityItem, String paramString1, String paramString2, String[] paramArrayOfString) {
/* 412 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, paramString2);
/* 413 */     for (byte b = 0; b < vector.size(); b++) {
/* 414 */       EntityItem entityItem = vector.elementAt(b);
/* 415 */       if (statusIsFinal(entityItem)) {
/* 416 */         for (byte b1 = 0; b1 < paramArrayOfString.length; b1++) {
/* 417 */           setFlagValue(this.m_elist.getProfile(), paramArrayOfString[b1], getQueuedValueForItem(entityItem, paramArrayOfString[b1]), entityItem);
/*     */         }
/*     */       }
/*     */     } 
/* 421 */     vector.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\BHCATLGORABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */