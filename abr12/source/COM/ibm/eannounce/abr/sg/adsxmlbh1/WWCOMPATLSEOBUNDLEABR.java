/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class WWCOMPATLSEOBUNDLEABR
/*     */   extends ADSCOMPATGEN
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  37 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {
/*  46 */     Vector vector1 = new Vector();
/*  47 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("ROOT");
/*  48 */     DiffEntity diffEntity = vector.firstElement();
/*  49 */     EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  50 */     EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*     */ 
/*     */ 
/*     */     
/*  54 */     Vector vector2 = new Vector();
/*  55 */     Vector vector3 = new Vector();
/*  56 */     String str = (String)paramHashtable.get("VeName");
/*     */     
/*  58 */     Vector vector4 = new Vector(1);
/*  59 */     setSystemSoLvVct(vector4, entityItem1);
/*  60 */     Vector vector5 = new Vector(1);
/*  61 */     setSystemSoLvVct(vector5, entityItem2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     if (getVeName().equals(str)) {
/*  80 */       paramADSABRSTATUS.addOutput("Extract changes as System.");
/*     */ 
/*     */       
/*  83 */       Vector<DiffEntity> vector6 = (Vector)paramHashtable.get("SEOCGBDL");
/*     */       
/*  85 */       if (vector6 != null) {
/*  86 */         for (byte b = 0; b < vector6.size(); b++) {
/*  87 */           DiffEntity diffEntity1 = vector6.elementAt(b);
/*  88 */           if (diffEntity1.isDeleted()) {
/*  89 */             paramADSABRSTATUS.addOutput(diffEntity1.getKey() + " is deleted");
/*  90 */             EntityItem entityItem = (EntityItem)diffEntity1.getPriorEntityItem().getUpLink(0);
/*  91 */             deActivateSystem(paramADSABRSTATUS, entityItem1.getEntityType(), entityItem1.getEntityID(), entityItem.getEntityType(), entityItem.getEntityID(), paramString1, paramString2);
/*  92 */           } else if (diffEntity1.isNew()) {
/*  93 */             paramADSABRSTATUS.addOutput(diffEntity1.getKey() + " is added");
/*  94 */             EntityItem entityItem = (EntityItem)diffEntity1.getCurrentEntityItem().getUpLink(0);
/*  95 */             populateCompat(paramADSABRSTATUS, vector1, entityItem1, entityItem, vector4, paramString1, paramString2);
/*     */           } else {
/*  97 */             paramADSABRSTATUS.addOutput(diffEntity1.getKey() + " already exist. ");
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
/* 108 */         vector6.clear();
/*     */       } else {
/* 110 */         paramADSABRSTATUS.addXMLGenMsg("No_SEOCG_FOUND", "SEOCGBDL");
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
/* 176 */     vector2.clear();
/* 177 */     vector4.clear();
/* 178 */     vector5.clear();
/* 179 */     vector3.clear();
/* 180 */     vector1.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateCompat(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector1, EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector paramVector2, String paramString1, String paramString2) throws SQLException {
/* 187 */     String str1 = null;
/* 188 */     String str2 = "    where SystemGroup.SystemType = ? and SystemGroup.SystemId = ? and SystemGroup.GroupId = ? with ur                  \r\n";
/* 189 */     str1 = getCommSEOCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     Vector vector1 = new Vector();
/* 203 */     Vector vector2 = new Vector();
/* 204 */     Vector vector3 = new Vector();
/* 205 */     vector1 = paramVector2;
/*     */     
/* 207 */     PreparedStatement preparedStatement = null;
/* 208 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 212 */       Connection connection = getConnection();
/* 213 */       preparedStatement = connection.prepareStatement(str1);
/* 214 */       preparedStatement.setString(1, paramEntityItem1.getEntityType());
/* 215 */       preparedStatement.setInt(2, paramEntityItem1.getEntityID());
/* 216 */       preparedStatement.setInt(3, paramEntityItem2.getEntityID());
/* 217 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 219 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector1, paramEntityItem1, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 266 */       vector2.clear();
/* 267 */       vector3.clear();
/* 268 */       if (resultSet != null) {
/*     */         try {
/* 270 */           resultSet.close();
/* 271 */         } catch (Exception exception) {
/* 272 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 274 */         resultSet = null;
/*     */       } 
/*     */       
/* 277 */       if (preparedStatement != null) {
/*     */         try {
/* 279 */           preparedStatement.close();
/* 280 */         } catch (Exception exception) {
/* 281 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 283 */         preparedStatement = null;
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
/*     */ 
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
/* 416 */     return "ADSWWCOMPATLSEOBUNDLE1";
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
/*     */   public String getVersion() {
/* 435 */     return "$Revision: 1.7 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WWCOMPATLSEOBUNDLEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */