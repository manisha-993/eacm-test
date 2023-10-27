/*     */ package COM.ibm.eannounce.darule;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.objects.Blob;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ public class DARuleEngineMgr
/*     */ {
/*     */   private static final String RULE_KEY = "DARULES";
/*  93 */   private static Hashtable metaTbl = new Hashtable<>();
/*     */   
/*  95 */   private static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("DARULE");
/*     */   
/*  97 */   private static boolean USE_CACHE = Boolean.valueOf(ABRServerProperties.getValue("DARULE", "_useCache", "true"))
/*  98 */     .booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String DTS_SQL = "select entityid from opicm.entity where enterprise=? and entitytype=? and valto>current timestamp and effto>current timestamp and valfrom >? with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean updateCatData(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws Exception {
/* 117 */     boolean bool = false;
/*     */     
/* 119 */     DARuleEngine dARuleEngine = getEngine(paramDatabase, paramProfile, paramEntityItem.getEntityType(), paramStringBuffer);
/* 120 */     if (dARuleEngine != null) {
/* 121 */       addDebugComment(3, paramStringBuffer, "updateCatData: " + dARuleEngine);
/*     */ 
/*     */       
/*     */       try {
/* 125 */         bool = dARuleEngine.updateCatData(paramDatabase, paramProfile, paramEntityItem, paramStringBuffer);
/*     */       } finally {
/* 127 */         dARuleEngine.dereference();
/*     */       } 
/*     */     } else {
/* 130 */       throw new DARuleException("Unable to find DARules for " + paramEntityItem.getEntityType());
/*     */     } 
/* 132 */     return bool;
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
/*     */   public static DARuleGroup getDARuleGroup(Database paramDatabase, Profile paramProfile, String paramString1, String paramString2, StringBuffer paramStringBuffer) throws Exception {
/* 153 */     expireCache(paramDatabase, paramProfile, paramString1, paramStringBuffer);
/*     */ 
/*     */     
/* 156 */     return new DARuleGroup(paramDatabase, paramProfile, paramString1, paramString2, paramStringBuffer);
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
/*     */   public static void clearEntityType(Database paramDatabase, Profile paramProfile, String paramString, StringBuffer paramStringBuffer) {
/* 172 */     expireCache(paramDatabase, paramProfile, paramString, paramStringBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addDebugComment(int paramInt, StringBuffer paramStringBuffer, String paramString) {
/* 182 */     if (paramInt <= DEBUG_LVL) {
/* 183 */       paramStringBuffer.append("<!-- " + paramString + " -->\n");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void addInformation(StringBuffer paramStringBuffer, String paramString) {
/* 192 */     paramStringBuffer.append("<p>" + paramString + "</p>\n");
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
/*     */   protected static String getNavigationName(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 205 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 208 */     EANList eANList = (EANList)metaTbl.get(paramEntityItem.getEntityType());
/* 209 */     if (eANList == null) {
/* 210 */       EntityGroup entityGroup = new EntityGroup(null, paramDatabase, paramProfile, paramEntityItem.getEntityType(), "Navigate");
/* 211 */       eANList = entityGroup.getMetaAttribute();
/* 212 */       metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 214 */     stringBuffer.append(paramEntityItem.getEntityGroup().getLongDescription() + ": ");
/* 215 */     for (byte b = 0; b < eANList.size(); b++) {
/* 216 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 217 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 218 */       if (b + 1 < eANList.size()) {
/* 219 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/*     */     
/* 223 */     return stringBuffer.toString().trim();
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
/*     */   private static DARuleEngine getEngine(Database paramDatabase, Profile paramProfile, String paramString, StringBuffer paramStringBuffer) throws Exception {
/* 241 */     DARuleEngine dARuleEngine = getCache(paramDatabase, paramProfile, paramString, paramStringBuffer);
/*     */ 
/*     */     
/* 244 */     if (dARuleEngine == null) {
/* 245 */       dARuleEngine = new DARuleEngine(paramDatabase, paramProfile, paramString, paramStringBuffer);
/*     */       
/* 247 */       putCache(paramDatabase, paramProfile, dARuleEngine, paramString, paramStringBuffer);
/*     */     } 
/* 249 */     return dARuleEngine;
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
/*     */   private static DARuleEngine getCache(Database paramDatabase, Profile paramProfile, String paramString, StringBuffer paramStringBuffer) {
/* 263 */     byte[] arrayOfByte = null;
/* 264 */     ByteArrayInputStream byteArrayInputStream = null;
/* 265 */     ObjectInputStream objectInputStream = null;
/* 266 */     DARuleEngine dARuleEngine = null;
/*     */ 
/*     */     
/* 269 */     boolean bool = true;
/*     */     
/* 271 */     if (!USE_CACHE) {
/* 272 */       addDebugComment(0, paramStringBuffer, "DARuleEngineMgr.getCache() is bypassed");
/* 273 */       return dARuleEngine;
/*     */     } 
/*     */     
/*     */     try {
/* 277 */       Blob blob = paramDatabase.getBlobNow(paramProfile, paramString, 0, "DARULES", bool);
/*     */       
/* 279 */       paramDatabase.commit();
/* 280 */       paramDatabase.freeStatement();
/* 281 */       paramDatabase.isPending();
/* 282 */       arrayOfByte = blob.getBAAttributeValue();
/* 283 */       if (arrayOfByte == null || arrayOfByte.length == 0) {
/* 284 */         paramDatabase.debug(1, "DARuleEngineMgr.getCache()  ** No Cache Object Found for " + paramString + ":" + "DARULES" + " **");
/*     */         
/* 286 */         addDebugComment(2, paramStringBuffer, "DARuleEngineMgr.getCache()  ** No Cache Object Found for " + paramString + ":" + "DARULES" + " **");
/* 287 */         return dARuleEngine;
/*     */       } 
/*     */       
/*     */       try {
/* 291 */         byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/* 292 */         objectInputStream = new ObjectInputStream(byteArrayInputStream);
/* 293 */         dARuleEngine = (DARuleEngine)objectInputStream.readObject();
/*     */       } finally {
/* 295 */         if (objectInputStream != null) {
/* 296 */           objectInputStream.close();
/* 297 */           objectInputStream = null;
/*     */         } 
/* 299 */         if (byteArrayInputStream != null) {
/* 300 */           byteArrayInputStream.close();
/* 301 */           byteArrayInputStream = null;
/*     */         } 
/*     */       } 
/*     */       
/* 305 */       arrayOfByte = null;
/*     */ 
/*     */ 
/*     */       
/* 309 */       if (updatesFound(paramDatabase, paramProfile, dARuleEngine, paramStringBuffer)) {
/*     */         
/* 311 */         expireCache(paramDatabase, paramProfile, paramString, paramStringBuffer);
/* 312 */         dARuleEngine.dereference();
/*     */         
/* 314 */         dARuleEngine = null;
/*     */       } 
/* 316 */       return dARuleEngine;
/* 317 */     } catch (Exception exception) {
/* 318 */       paramDatabase.debug(0, "DARuleEngineMgr.getCache() " + exception.getMessage());
/* 319 */       addDebugComment(0, paramStringBuffer, "DARuleEngineMgr.getCache() Error: " + exception.getMessage());
/* 320 */       exception.printStackTrace();
/* 321 */       if (exception instanceof java.io.InvalidClassException)
/*     */       {
/* 323 */         expireCache(paramDatabase, paramProfile, paramString, paramStringBuffer);
/*     */       }
/*     */     } finally {
/* 326 */       paramDatabase.freeStatement();
/* 327 */       paramDatabase.isPending();
/*     */     } 
/*     */     
/* 330 */     return dARuleEngine;
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
/*     */   private static boolean updatesFound(Database paramDatabase, Profile paramProfile, DARuleEngine paramDARuleEngine, StringBuffer paramStringBuffer) {
/* 344 */     boolean bool = false;
/*     */ 
/*     */     
/* 347 */     Vector vector = getChangedDARULEids(paramDatabase, paramProfile, paramDARuleEngine.getCreateDTS(), paramStringBuffer);
/* 348 */     if (vector.size() > 0) {
/*     */       
/* 350 */       Vector<Integer> vector1 = paramDARuleEngine.getAllDARULEids();
/* 351 */       for (byte b = 0; b < vector1.size(); b++) {
/* 352 */         Integer integer = vector1.elementAt(b);
/* 353 */         if (vector.contains(integer)) {
/* 354 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 358 */       vector1.clear();
/* 359 */       vector.clear();
/*     */     } 
/*     */     
/* 362 */     return bool;
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
/*     */   private static Vector getChangedDARULEids(Database paramDatabase, Profile paramProfile, String paramString, StringBuffer paramStringBuffer) {
/* 378 */     PreparedStatement preparedStatement = null;
/* 379 */     ResultSet resultSet = null;
/* 380 */     Vector<Integer> vector = new Vector();
/* 381 */     String str = paramProfile.getEnterprise();
/*     */     
/* 383 */     addDebugComment(1, paramStringBuffer, "getChangedDARULEids look for DARULE chgs after " + paramString);
/*     */     try {
/* 385 */       preparedStatement = paramDatabase.getPDHConnection().prepareStatement("select entityid from opicm.entity where enterprise=? and entitytype=? and valto>current timestamp and effto>current timestamp and valfrom >? with ur");
/* 386 */       preparedStatement.clearParameters();
/* 387 */       preparedStatement.setString(1, str);
/* 388 */       preparedStatement.setString(2, "DARULE");
/* 389 */       preparedStatement.setString(3, paramString);
/*     */       
/* 391 */       resultSet = preparedStatement.executeQuery();
/* 392 */       while (resultSet.next()) {
/* 393 */         int i = resultSet.getInt(1);
/* 394 */         vector.add(new Integer(i));
/* 395 */         addDebugComment(1, paramStringBuffer, "getChangedDARULEids found DARULE" + i);
/*     */       }
/*     */     
/* 398 */     } catch (Exception exception) {
/* 399 */       addDebugComment(0, paramStringBuffer, "getChangedDARULEids exception: " + exception);
/*     */     } finally {
/*     */       
/* 402 */       if (resultSet != null) {
/*     */         try {
/* 404 */           resultSet.close();
/* 405 */         } catch (SQLException sQLException) {
/* 406 */           sQLException.printStackTrace();
/*     */         } 
/* 408 */         resultSet = null;
/*     */       } 
/* 410 */       if (preparedStatement != null) {
/*     */         try {
/* 412 */           preparedStatement.close();
/* 413 */         } catch (SQLException sQLException) {
/* 414 */           sQLException.printStackTrace();
/*     */         } 
/*     */       }
/*     */       try {
/* 418 */         paramDatabase.commit();
/* 419 */       } catch (SQLException sQLException) {
/* 420 */         sQLException.printStackTrace();
/*     */       } 
/* 422 */       paramDatabase.freeStatement();
/* 423 */       paramDatabase.isPending();
/*     */     } 
/* 425 */     return vector;
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
/*     */   private static void putCache(Database paramDatabase, Profile paramProfile, DARuleEngine paramDARuleEngine, String paramString, StringBuffer paramStringBuffer) {
/* 438 */     ByteArrayOutputStream byteArrayOutputStream = null;
/* 439 */     ObjectOutputStream objectOutputStream = null;
/*     */     
/* 441 */     if (!USE_CACHE) {
/* 442 */       addDebugComment(0, paramStringBuffer, "DARuleEngineMgr.putCache() is bypassed");
/*     */       return;
/*     */     } 
/*     */     try {
/* 446 */       DatePackage datePackage = paramDatabase.getDates();
/* 447 */       String str1 = datePackage.getNow();
/* 448 */       String str2 = datePackage.getForever();
/*     */ 
/*     */       
/* 451 */       boolean bool = true;
/*     */ 
/*     */       
/*     */       try {
/* 455 */         byte[] arrayOfByte = null;
/* 456 */         ByteArrayOutputStream byteArrayOutputStream1 = null;
/* 457 */         ObjectOutputStream objectOutputStream1 = null;
/*     */         
/*     */         try {
/*     */           try {
/* 461 */             byteArrayOutputStream1 = new ByteArrayOutputStream();
/* 462 */             objectOutputStream1 = new ObjectOutputStream(byteArrayOutputStream1);
/* 463 */             objectOutputStream1.writeObject(paramDARuleEngine);
/* 464 */             objectOutputStream1.flush();
/* 465 */             objectOutputStream1.reset();
/*     */           } finally {
/*     */             
/* 468 */             if (objectOutputStream1 != null) {
/* 469 */               objectOutputStream1.close();
/*     */             }
/* 471 */             if (byteArrayOutputStream1 != null) {
/* 472 */               byteArrayOutputStream1.close();
/*     */             }
/*     */           } 
/* 475 */           arrayOfByte = byteArrayOutputStream1.toByteArray();
/*     */         }
/* 477 */         catch (Exception exception) {
/* 478 */           exception.printStackTrace();
/*     */         } 
/*     */         
/* 481 */         paramDatabase.callGBL9974(new ReturnStatus(-1), paramDatabase.getInstanceName(), "pc");
/* 482 */         paramDatabase.freeStatement();
/* 483 */         paramDatabase.isPending();
/* 484 */         paramDatabase.putBlob(paramProfile, paramString, 0, "DARULES", "CACHE", str1, str2, arrayOfByte, bool);
/*     */       } finally {
/*     */         
/* 487 */         paramDatabase.commit();
/*     */         
/* 489 */         if (objectOutputStream != null) {
/* 490 */           objectOutputStream.close();
/*     */         }
/* 492 */         if (byteArrayOutputStream != null) {
/* 493 */           byteArrayOutputStream.close();
/*     */         }
/*     */       }
/*     */     
/* 497 */     } catch (Exception exception) {
/* 498 */       paramDatabase.debug(0, "DARuleEngineMgr.putCache() " + exception.getMessage());
/* 499 */       addDebugComment(0, paramStringBuffer, "DARuleEngineMgr.putCache() " + exception.getMessage());
/* 500 */       exception.printStackTrace();
/* 501 */       paramDatabase.freeStatement();
/* 502 */       paramDatabase.isPending();
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
/*     */   private static void expireCache(Database paramDatabase, Profile paramProfile, String paramString, StringBuffer paramStringBuffer) {
/*     */     try {
/* 519 */       boolean bool = true;
/* 520 */       addDebugComment(4, paramStringBuffer, "DARuleEngineMgr: Expire rules for " + paramString);
/*     */       
/*     */       try {
/* 523 */         paramDatabase.deactivateBlob(paramProfile, paramString, 0, "DARULES", bool);
/*     */       } finally {
/*     */         
/* 526 */         paramDatabase.commit();
/*     */       }
/*     */     
/* 529 */     } catch (Exception exception) {
/* 530 */       paramDatabase.debug(0, "DARuleEngineMgr.expireCache() " + exception.getMessage());
/* 531 */       addDebugComment(0, paramStringBuffer, "DARuleEngineMgr.expireCache() " + exception.getMessage());
/* 532 */       exception.printStackTrace();
/* 533 */       paramDatabase.freeStatement();
/* 534 */       paramDatabase.isPending();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 545 */     return "$Revision: 1.4 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\DARuleEngineMgr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */