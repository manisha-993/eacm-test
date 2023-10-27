/*     */ package COM.ibm.eannounce.abr.sg.translation;
/*     */ 
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnID;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class EntityHandler
/*     */ {
/*     */   private Database database;
/*     */   private Profile profile;
/*     */   private ControlBlock controlBlock;
/*     */   private String entityType;
/*     */   private int entityID;
/*     */   private int nlsID;
/*     */   
/*     */   public EntityHandler(Database paramDatabase, Profile paramProfile, ControlBlock paramControlBlock, String paramString, int paramInt1, int paramInt2) {
/*  46 */     this.database = paramDatabase;
/*  47 */     this.profile = paramProfile;
/*  48 */     this.controlBlock = paramControlBlock;
/*  49 */     this.entityType = paramString;
/*  50 */     this.entityID = paramInt1;
/*  51 */     this.nlsID = paramInt2;
/*     */   }
/*     */   
/*     */   public int getEntityID() {
/*  55 */     return this.entityID;
/*     */   }
/*     */   
/*     */   public String getEntityType() {
/*  59 */     return this.entityType;
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
/*     */   public Map getAttributes(String[] paramArrayOfString) throws MiddlewareException, SQLException {
/*  71 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  72 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  73 */       hashMap.put(paramArrayOfString[b], null);
/*     */     }
/*  75 */     getAttributes(hashMap);
/*  76 */     return hashMap;
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
/*     */   public void getAttributes(Map<String, String> paramMap) throws MiddlewareException, SQLException {
/*  89 */     String str = this.profile.getEnterprise();
/*  90 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/*  91 */     int i = paramMap.size();
/*  92 */     ResultSet resultSet = this.database.callGBL7545(returnStatus, str, this.entityType, this.entityID, this.profile
/*  93 */         .getValOn(), this.profile.getEffOn());
/*     */     try {
/*  95 */       while (resultSet.next()) {
/*  96 */         String str1 = resultSet.getString(1);
/*  97 */         if (paramMap.containsKey(str1)) {
/*  98 */           String str2 = resultSet.getString(3);
/*  99 */           paramMap.put(str1, str2);
/* 100 */           i--;
/*     */           
/* 102 */           if (i <= 0)
/*     */             break; 
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 107 */       resultSet.close();
/* 108 */       this.database.freeStatement();
/* 109 */       this.database.isPending();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextAttribute(String paramString1, String paramString2) throws MiddlewareException {
/* 120 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 121 */     String str1 = this.profile.getEnterprise();
/* 122 */     int i = this.controlBlock.getOPENID();
/* 123 */     int j = this.controlBlock.getTranID();
/* 124 */     String str2 = this.controlBlock.getEffFrom();
/* 125 */     String str3 = this.controlBlock.getEffTo();
/* 126 */     this.database.callGBL2091(returnStatus, i, str1, this.entityType, new ReturnID(this.entityID), paramString1, paramString2, this.nlsID, j, str2, str3);
/*     */ 
/*     */ 
/*     */     
/* 130 */     this.database.freeStatement();
/* 131 */     this.database.isPending();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlagAttribute(String paramString1, String paramString2) throws MiddlewareException {
/* 141 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 142 */     String str1 = this.profile.getEnterprise();
/* 143 */     int i = this.controlBlock.getOPENID();
/* 144 */     int j = this.controlBlock.getTranID();
/* 145 */     String str2 = this.controlBlock.getEffFrom();
/* 146 */     String str3 = this.controlBlock.getEffTo();
/* 147 */     this.database.callGBL2265(returnStatus, i, str1, this.entityType, new ReturnID(this.entityID), paramString1, paramString2, j, str2, str3, this.nlsID);
/*     */ 
/*     */ 
/*     */     
/* 151 */     this.database.freeStatement();
/* 152 */     this.database.isPending();
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
/*     */   public static void withAllEntitiesDo(Database paramDatabase, Profile paramProfile, ControlBlock paramControlBlock, String paramString, int paramInt, Callback paramCallback) throws Exception {
/* 166 */     String str = paramProfile.getEnterprise();
/*     */     
/* 168 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 169 */     ResultSet resultSet = paramDatabase.callGBL0007(returnStatus, str, paramString);
/*     */ 
/*     */     
/* 172 */     Vector<EntityHandler> vector = new Vector();
/*     */     try {
/* 174 */       while (resultSet.next()) {
/* 175 */         int i = resultSet.getInt("entityid");
/* 176 */         EntityHandler entityHandler = new EntityHandler(paramDatabase, paramProfile, paramControlBlock, paramString, i, paramInt);
/*     */         
/* 178 */         vector.add(entityHandler);
/*     */       } 
/*     */     } finally {
/*     */       
/* 182 */       resultSet.close();
/* 183 */       paramDatabase.freeStatement();
/* 184 */       paramDatabase.isPending();
/*     */     } 
/*     */     
/* 187 */     Iterator<EntityHandler> iterator = vector.iterator();
/* 188 */     while (iterator.hasNext()) {
/* 189 */       EntityHandler entityHandler = iterator.next();
/* 190 */       paramCallback.process(entityHandler);
/*     */     } 
/* 192 */     vector.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void withChildEntitiesDo(String paramString, Callback paramCallback) throws Exception {
/* 203 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 204 */     String str = this.profile.getEnterprise();
/* 205 */     int i = this.controlBlock.getOPENID();
/*     */     
/* 207 */     ResultSet resultSet = this.database.callGBL2054(returnStatus, i, str, paramString, this.entityType, this.entityID, this.profile
/*     */         
/* 209 */         .getValOn(), this.profile.getEffOn());
/*     */     
/* 211 */     Vector<EntityHandler> vector = new Vector();
/*     */     try {
/* 213 */       while (resultSet.next()) {
/* 214 */         int j = resultSet.getInt("EntityID");
/* 215 */         String str1 = resultSet.getString("EntityType");
/* 216 */         EntityHandler entityHandler = new EntityHandler(this.database, this.profile, this.controlBlock, str1, j, this.nlsID);
/*     */         
/* 218 */         vector.add(entityHandler);
/*     */       } 
/*     */     } finally {
/* 221 */       resultSet.close();
/* 222 */       this.database.freeStatement();
/* 223 */       this.database.isPending();
/*     */     } 
/*     */     
/* 226 */     Iterator<EntityHandler> iterator = vector.iterator();
/* 227 */     while (iterator.hasNext()) {
/* 228 */       EntityHandler entityHandler = iterator.next();
/* 229 */       paramCallback.process(entityHandler);
/*     */     } 
/* 231 */     vector.clear();
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
/*     */   public void withParentEntitiesDo(String paramString, Callback paramCallback) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReturnStatus insert() throws MiddlewareException {
/* 286 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 287 */     String str1 = this.profile.getEnterprise();
/* 288 */     int i = this.controlBlock.getOPENID();
/* 289 */     int j = this.controlBlock.getTranID();
/* 290 */     String str2 = this.controlBlock.getEffFrom();
/* 291 */     String str3 = this.controlBlock.getEffTo();
/* 292 */     ReturnID returnID = new ReturnID(-1);
/* 293 */     returnID = this.database.callGBL2092(returnStatus, i, this.profile
/* 294 */         .getSessionID(), str1, this.entityType, returnID, j, str2, str3, this.nlsID);
/*     */     
/* 296 */     this.database.freeStatement();
/* 297 */     this.database.isPending();
/* 298 */     this.entityID = returnID.intValue();
/*     */     try {
/* 300 */       this.database.commit();
/* 301 */     } catch (SQLException sQLException) {
/* 302 */       sQLException.printStackTrace();
/*     */     } 
/* 304 */     return returnStatus;
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
/*     */   public ReturnStatus addRelator(String paramString1, String paramString2, int paramInt) throws MiddlewareException {
/* 316 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 317 */     String str1 = this.profile.getEnterprise();
/* 318 */     int i = this.controlBlock.getOPENID();
/* 319 */     int j = this.controlBlock.getTranID();
/* 320 */     String str2 = this.controlBlock.getEffFrom();
/* 321 */     String str3 = this.controlBlock.getEffTo();
/* 322 */     int k = this.profile.getSessionID();
/* 323 */     this.database.callGBL2098(returnStatus, i, k, str1, paramString1, new ReturnID(), this.entityType, this.entityID, paramString2, paramInt, j, str2, str3, this.nlsID);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     this.database.freeStatement();
/* 329 */     this.database.isPending();
/*     */     try {
/* 331 */       this.database.commit();
/* 332 */     } catch (SQLException sQLException) {
/* 333 */       sQLException.printStackTrace();
/*     */     } 
/* 335 */     return returnStatus;
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void process(EntityHandler param1EntityHandler) throws Exception;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\translation\EntityHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */