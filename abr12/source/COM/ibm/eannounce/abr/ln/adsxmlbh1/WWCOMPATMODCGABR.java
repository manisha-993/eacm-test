/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WWCOMPATMODCGABR
/*     */   extends ADSCOMPATGEN
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  99 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {
/* 108 */     Vector vector = new Vector();
/* 109 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("ROOT");
/* 110 */     DiffEntity diffEntity = vector1.firstElement();
/* 111 */     EntityItem entityItem = diffEntity.getCurrentEntityItem();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (diffEntity.isNew()) {
/* 119 */       paramADSABRSTATUS.addXMLGenMsg("NEW_ENTITY", diffEntity.getKey());
/* 120 */       populateCompat(paramADSABRSTATUS, vector, entityItem, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       String str1 = getValue(diffEntity, "OKTOPUB");
/* 144 */       String str2 = getValue(diffEntity, "BRANDCD");
/* 145 */       if (str1 != null || str2 != null) {
/* 146 */         if ("Delete".equals(str1)) {
/* 147 */           paramADSABRSTATUS.addXMLGenMsg("OKTOPUB_CHANGE_FOUND", str1);
/* 148 */           deActivateCG(paramADSABRSTATUS, entityItem.getEntityType(), entityItem.getEntityID(), paramString1, paramString2);
/*     */         } else {
/* 150 */           paramADSABRSTATUS.addXMLGenMsg("INTEREST_CHANGE_FOUND", diffEntity.getKey());
/* 151 */           String str3 = PokUtils.getAttributeValue(entityItem, "OKTOPUB", ", ", "", false);
/* 152 */           String str4 = PokUtils.getAttributeFlagValue(entityItem, "BRANDCD");
/* 153 */           if (str4 == null) {
/* 154 */             str4 = "";
/*     */           }
/* 156 */           updateCGOKTOPUB(paramADSABRSTATUS, entityItem.getEntityType(), entityItem.getEntityID(), str3, str4, paramString1, paramString2);
/*     */         } 
/*     */       }
/* 159 */       Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("MDLCGMDLCGOS");
/*     */       
/* 161 */       for (byte b = 0; b < vector2.size(); b++) {
/* 162 */         DiffEntity diffEntity1 = vector2.elementAt(b);
/* 163 */         if (diffEntity1.isDeleted()) {
/* 164 */           paramADSABRSTATUS.addXMLGenMsg("DELETE_ENTITY", diffEntity1.getKey());
/* 165 */           EntityItem entityItem1 = (EntityItem)diffEntity1.getPriorEntityItem().getDownLink(0);
/* 166 */           if (entityItem1 != null) {
/* 167 */             deActivateCGOS(paramADSABRSTATUS, entityItem.getEntityType(), entityItem.getEntityID(), entityItem1.getEntityType(), entityItem1.getEntityID(), paramString1, paramString2);
/*     */           }
/* 169 */         } else if (diffEntity1.isNew()) {
/* 170 */           paramADSABRSTATUS.addXMLGenMsg("NEW_ENTITY", diffEntity1.getKey());
/* 171 */           EntityItem entityItem1 = (EntityItem)diffEntity1.getCurrentEntityItem().getDownLink(0);
/* 172 */           if (entityItem1 != null) {
/* 173 */             populateCompat2(paramADSABRSTATUS, vector, entityItem, entityItem1, paramString1, paramString2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 178 */     vector.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateCompat2(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector, EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, String paramString2) throws SQLException {
/* 188 */     String str1 = null;
/* 189 */     String str2 = "    MODELCGOS.entityid = ? and \r\n    MODELCG.entityid   = ? with ur \r\n";
/*     */     
/* 191 */     str1 = getCommMODELCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     Vector vector1 = new Vector();
/* 204 */     Vector vector2 = new Vector();
/* 205 */     Vector vector3 = new Vector();
/*     */     
/* 207 */     PreparedStatement preparedStatement = null;
/* 208 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 212 */       Connection connection = getConnection();
/* 213 */       preparedStatement = connection.prepareStatement(str1);
/* 214 */       preparedStatement.setInt(1, paramEntityItem2.getEntityID());
/* 215 */       preparedStatement.setInt(2, paramEntityItem1.getEntityID());
/* 216 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 218 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector, paramEntityItem1, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 264 */       vector2.clear();
/* 265 */       vector3.clear();
/* 266 */       vector1.clear();
/* 267 */       if (resultSet != null) {
/*     */         try {
/* 269 */           resultSet.close();
/* 270 */         } catch (Exception exception) {
/* 271 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 273 */         resultSet = null;
/*     */       } 
/*     */       
/* 276 */       if (preparedStatement != null) {
/*     */         try {
/* 278 */           preparedStatement.close();
/* 279 */         } catch (Exception exception) {
/* 280 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 282 */         preparedStatement = null;
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
/*     */   private void populateCompat(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector, EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException {
/* 294 */     String str1 = null;
/* 295 */     String str2 = "    MODELCG.entityid                = ? with ur \r\n";
/* 296 */     str1 = getCommMODELCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     Vector vector1 = new Vector();
/* 309 */     Vector vector2 = new Vector();
/* 310 */     Vector vector3 = new Vector();
/*     */     
/* 312 */     PreparedStatement preparedStatement = null;
/* 313 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 317 */       Connection connection = getConnection();
/* 318 */       preparedStatement = connection.prepareStatement(str1);
/* 319 */       preparedStatement.setInt(1, paramEntityItem.getEntityID());
/* 320 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 322 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector, paramEntityItem, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 368 */       vector2.clear();
/* 369 */       vector3.clear();
/* 370 */       vector1.clear();
/* 371 */       if (resultSet != null) {
/*     */         try {
/* 373 */           resultSet.close();
/* 374 */         } catch (Exception exception) {
/* 375 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 377 */         resultSet = null;
/*     */       } 
/*     */       
/* 380 */       if (preparedStatement != null) {
/*     */         try {
/* 382 */           preparedStatement.close();
/* 383 */         } catch (Exception exception) {
/* 384 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 386 */         preparedStatement = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 394 */     return "ADSWWCOMPATMODCG";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 403 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\WWCOMPATMODCGABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */