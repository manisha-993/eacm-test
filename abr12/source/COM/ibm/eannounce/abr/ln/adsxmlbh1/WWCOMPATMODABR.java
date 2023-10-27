/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WWCOMPATMODABR
/*     */   extends ADSCOMPATGEN
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/* 180 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {
/* 190 */     Vector vector1 = new Vector();
/* 191 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("ROOT");
/* 192 */     DiffEntity diffEntity = vector.firstElement();
/* 193 */     EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 194 */     EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*     */ 
/*     */ 
/*     */     
/* 198 */     Vector vector2 = new Vector();
/* 199 */     Vector vector3 = new Vector();
/* 200 */     String str = (String)paramHashtable.get("VeName");
/*     */     
/* 202 */     Vector vector4 = new Vector(1);
/* 203 */     setSystemSoLvVct(vector4, entityItem1);
/* 204 */     Vector vector5 = new Vector(1);
/* 205 */     setSystemSoLvVct(vector5, entityItem2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     if (getVeName().equals(str)) {
/* 226 */       paramADSABRSTATUS.addOutput("Extract changes as System MODEL.");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       Vector<DiffEntity> vector6 = (Vector)paramHashtable.get("SEOCGMDL");
/*     */       
/* 260 */       if (vector6 != null) {
/*     */         
/* 262 */         for (byte b = 0; b < vector6.size(); b++) {
/* 263 */           DiffEntity diffEntity1 = vector6.elementAt(b);
/* 264 */           if (diffEntity1.isDeleted()) {
/* 265 */             paramADSABRSTATUS.addOutput(diffEntity1.getKey() + " is deleted");
/* 266 */             EntityItem entityItem = (EntityItem)diffEntity1.getPriorEntityItem().getUpLink(0);
/* 267 */             deActivateSystem(paramADSABRSTATUS, entityItem1.getEntityType(), entityItem1.getEntityID(), entityItem.getEntityType(), entityItem.getEntityID(), paramString1, paramString2);
/* 268 */           } else if (diffEntity1.isNew()) {
/* 269 */             paramADSABRSTATUS.addOutput(diffEntity1.getKey() + " is added");
/* 270 */             EntityItem entityItem = (EntityItem)diffEntity1.getCurrentEntityItem().getUpLink(0);
/* 271 */             populateCompat(paramADSABRSTATUS, vector1, entityItem1, entityItem, vector4, paramString1, paramString2);
/*     */           } else {
/* 273 */             paramADSABRSTATUS.addOutput(diffEntity1.getKey() + " already exist. ");
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 284 */         vector6.clear();
/*     */       } else {
/* 286 */         paramADSABRSTATUS.addXMLGenMsg("No_SEOCG_FOUND", "SEOCGMDL");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     vector2.clear();
/* 357 */     vector4.clear();
/* 358 */     vector5.clear();
/* 359 */     vector3.clear();
/* 360 */     vector1.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateCompat(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector1, EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector paramVector2, String paramString1, String paramString2) throws SQLException {
/* 366 */     String str = null;
/* 367 */     if ("MODELCG".equals(paramEntityItem2.getEntityType())) {
/* 368 */       String str1 = "    MODELCG.entityid = ?  and MODEL.entitytype= ? and MODEL.entityid = ? with ur \r\n";
/* 369 */       str = getCommMODELCGSql(str1);
/*     */     } else {
/* 371 */       String str1 = "    where SystemGroup.GroupId = ? and SystemGroup.SystemType= ? and SystemGroup.SystemId = ? with ur                  \r\n";
/* 372 */       str = getCommSEOCGSql(str1);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     Vector vector1 = new Vector();
/* 386 */     Vector vector2 = new Vector();
/* 387 */     Vector vector3 = new Vector();
/* 388 */     vector1 = paramVector2;
/*     */     
/* 390 */     PreparedStatement preparedStatement = null;
/* 391 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 395 */       Connection connection = getConnection();
/* 396 */       preparedStatement = connection.prepareStatement(str);
/* 397 */       preparedStatement.setInt(1, paramEntityItem2.getEntityID());
/* 398 */       preparedStatement.setString(2, paramEntityItem1.getEntityType());
/* 399 */       preparedStatement.setInt(3, paramEntityItem1.getEntityID());
/* 400 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 402 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector1, paramEntityItem1, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 448 */       vector1.clear();
/* 449 */       vector2.clear();
/* 450 */       vector3.clear();
/* 451 */       if (resultSet != null) {
/*     */         try {
/* 453 */           resultSet.close();
/* 454 */         } catch (Exception exception) {
/* 455 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 457 */         resultSet = null;
/*     */       } 
/*     */       
/* 460 */       if (preparedStatement != null) {
/*     */         try {
/* 462 */           preparedStatement.close();
/* 463 */         } catch (Exception exception) {
/* 464 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 466 */         preparedStatement = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 589 */     return "ADSWWCOMPATMOD";
/*     */   }
/*     */   public String getStatusAttr() {
/* 592 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 601 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\WWCOMPATMODABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */