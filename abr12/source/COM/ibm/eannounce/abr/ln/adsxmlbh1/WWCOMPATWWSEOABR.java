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
/*     */ public class WWCOMPATWWSEOABR
/*     */   extends ADSCOMPATGEN
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/* 132 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {
/* 141 */     Vector vector1 = new Vector();
/* 142 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("ROOT");
/* 143 */     DiffEntity diffEntity = vector.firstElement();
/* 144 */     EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 145 */     EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*     */ 
/*     */ 
/*     */     
/* 149 */     Vector vector2 = new Vector();
/* 150 */     Vector vector3 = new Vector();
/* 151 */     String str = (String)paramHashtable.get("VeName");
/*     */     
/* 153 */     Vector vector4 = new Vector(1);
/* 154 */     setSystemSoLvVct(vector4, entityItem1);
/* 155 */     Vector vector5 = new Vector(1);
/* 156 */     setSystemSoLvVct(vector5, entityItem2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     if (getVeName().equals(str)) {
/* 175 */       paramADSABRSTATUS.addOutput("Extract changes as System.");
/*     */ 
/*     */       
/* 178 */       Vector<DiffEntity> vector6 = (Vector)paramHashtable.get("SEOCGSEO");
/*     */       
/* 180 */       if (vector6 != null) {
/* 181 */         for (byte b = 0; b < vector6.size(); b++) {
/* 182 */           DiffEntity diffEntity1 = vector6.elementAt(b);
/* 183 */           if (diffEntity1.isDeleted()) {
/* 184 */             paramADSABRSTATUS.addOutput(diffEntity1.getKey() + " is deleted");
/* 185 */             EntityItem entityItem = (EntityItem)diffEntity1.getPriorEntityItem().getUpLink(0);
/* 186 */             deActivateSystem(paramADSABRSTATUS, entityItem1.getEntityType(), entityItem1.getEntityID(), entityItem.getEntityType(), entityItem.getEntityID(), paramString1, paramString2);
/* 187 */           } else if (diffEntity1.isNew()) {
/* 188 */             paramADSABRSTATUS.addOutput(diffEntity1.getKey() + " is added");
/* 189 */             EntityItem entityItem = (EntityItem)diffEntity1.getCurrentEntityItem().getUpLink(0);
/* 190 */             populateCompat(paramADSABRSTATUS, vector1, entityItem1, entityItem, vector4, paramString1, paramString2);
/*     */           } else {
/* 192 */             paramADSABRSTATUS.addOutput(diffEntity1.getKey() + " already exist. ");
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
/* 203 */         vector6.clear();
/*     */       } else {
/* 205 */         paramADSABRSTATUS.addXMLGenMsg("No_SEOCG_FOUND", "SEOCGSEO");
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
/* 270 */     vector2.clear();
/* 271 */     vector4.clear();
/* 272 */     vector5.clear();
/* 273 */     vector3.clear();
/* 274 */     vector1.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateCompat(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector1, EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector paramVector2, String paramString1, String paramString2) throws SQLException {
/* 280 */     String str1 = null;
/* 281 */     String str2 = "    where SystemGroup.SystemType = ? and SystemGroup.SystemId = ? and SystemGroup.GroupId = ?  with ur                  \r\n";
/* 282 */     str1 = getCommSEOCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     Vector vector1 = new Vector();
/* 296 */     Vector vector2 = new Vector();
/* 297 */     Vector vector3 = new Vector();
/* 298 */     vector1 = paramVector2;
/*     */     
/* 300 */     PreparedStatement preparedStatement = null;
/* 301 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 305 */       Connection connection = getConnection();
/* 306 */       preparedStatement = connection.prepareStatement(str1);
/* 307 */       preparedStatement.setString(1, paramEntityItem1.getEntityType());
/* 308 */       preparedStatement.setInt(2, paramEntityItem1.getEntityID());
/* 309 */       preparedStatement.setInt(3, paramEntityItem2.getEntityID());
/* 310 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 312 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector1, paramEntityItem1, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 359 */       vector2.clear();
/* 360 */       vector2.clear();
/* 361 */       vector3.clear();
/* 362 */       if (resultSet != null) {
/*     */         try {
/* 364 */           resultSet.close();
/* 365 */         } catch (Exception exception) {
/* 366 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 368 */         resultSet = null;
/*     */       } 
/*     */       
/* 371 */       if (preparedStatement != null) {
/*     */         try {
/* 373 */           preparedStatement.close();
/* 374 */         } catch (Exception exception) {
/* 375 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 377 */         preparedStatement = null;
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
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 512 */     return "ADSWWCOMPATWWSEO1";
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
/*     */   public String getVersion() {
/* 525 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\WWCOMPATWWSEOABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */