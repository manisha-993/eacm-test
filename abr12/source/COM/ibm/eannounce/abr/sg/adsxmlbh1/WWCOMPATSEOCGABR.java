/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class WWCOMPATSEOCGABR
/*     */   extends ADSCOMPATGEN
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  80 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {
/*  89 */     Vector vector = new Vector();
/*  90 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("ROOT");
/*  91 */     DiffEntity diffEntity = vector1.firstElement();
/*  92 */     EntityItem entityItem = diffEntity.getCurrentEntityItem();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     String str1 = PokUtils.getAttributeValue(entityItem, "OKTOPUB", ", ", "", false);
/* 125 */     String str2 = getValue(diffEntity, "OKTOPUB");
/* 126 */     String str3 = getValue(diffEntity, "BRANDCD");
/* 127 */     if (str2 != null || str3 != null) {
/* 128 */       if ("Delete".equals(str2)) {
/* 129 */         paramADSABRSTATUS.addXMLGenMsg("OKTOPUB_CHANGE_FOUND", str2);
/* 130 */         deActivateCG(paramADSABRSTATUS, entityItem.getEntityType(), entityItem.getEntityID(), paramString1, paramString2);
/*     */       } else {
/* 132 */         paramADSABRSTATUS.addXMLGenMsg("INTEREST_CHANGE_FOUND", diffEntity.getKey());
/* 133 */         String str = PokUtils.getAttributeFlagValue(entityItem, "BRANDCD");
/* 134 */         if (str == null) {
/* 135 */           str = "";
/*     */         }
/* 137 */         updateCGOKTOPUB(paramADSABRSTATUS, entityItem.getEntityType(), entityItem.getEntityID(), str1, str, paramString1, paramString2);
/*     */       } 
/*     */     }
/* 140 */     if (!"Delete".equals(str1)) {
/* 141 */       Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("SEOCGSEOCGOS");
/* 142 */       if (vector2 != null) {
/* 143 */         for (byte b = 0; b < vector2.size(); b++) {
/* 144 */           DiffEntity diffEntity1 = vector2.elementAt(b);
/* 145 */           if (diffEntity1.isDeleted()) {
/* 146 */             paramADSABRSTATUS.addXMLGenMsg("DELETE_ENTITY", diffEntity1.getKey());
/* 147 */             EntityItem entityItem1 = (EntityItem)diffEntity1.getPriorEntityItem().getDownLink(0);
/* 148 */             if (entityItem1 != null) {
/* 149 */               deActivateCGOS(paramADSABRSTATUS, entityItem.getEntityType(), entityItem.getEntityID(), entityItem1.getEntityType(), entityItem1.getEntityID(), paramString1, paramString2);
/*     */             }
/* 151 */           } else if (diffEntity1.isNew()) {
/* 152 */             paramADSABRSTATUS.addXMLGenMsg("NEW_ENTITY", diffEntity1.getKey());
/* 153 */             EntityItem entityItem1 = (EntityItem)diffEntity1.getCurrentEntityItem().getDownLink(0);
/* 154 */             if (entityItem1 != null)
/* 155 */               populateCompat2(paramADSABRSTATUS, vector, entityItem, entityItem1, paramString1, paramString2); 
/*     */           } 
/*     */         } 
/* 158 */         vector.clear();
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
/*     */   private void populateCompat2(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector, EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, String paramString2) throws SQLException {
/* 170 */     String str1 = null;
/* 171 */     String str2 = "    where SystemGroup.GroupId = ? and                 \r\n    SystemGroup.GroupType = 'SEOCG' and                 \r\n    OSOption.OSType  = ? and                        \r\n    OSOption.OSId = ?  with ur                  \r\n";
/*     */ 
/*     */ 
/*     */     
/* 175 */     str1 = getCommSEOCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     Vector vector1 = new Vector();
/* 188 */     Vector vector2 = new Vector();
/* 189 */     Vector vector3 = new Vector();
/*     */     
/* 191 */     PreparedStatement preparedStatement = null;
/* 192 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 196 */       Connection connection = getConnection();
/* 197 */       preparedStatement = connection.prepareStatement(str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       preparedStatement.setInt(1, paramEntityItem1.getEntityID());
/* 205 */       preparedStatement.setString(2, paramEntityItem2.getEntityType());
/* 206 */       preparedStatement.setInt(3, paramEntityItem2.getEntityID());
/* 207 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 209 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector, paramEntityItem1, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 255 */       vector2.clear();
/* 256 */       vector3.clear();
/* 257 */       vector1.clear();
/* 258 */       if (resultSet != null) {
/*     */         try {
/* 260 */           resultSet.close();
/* 261 */         } catch (Exception exception) {
/* 262 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 264 */         resultSet = null;
/*     */       } 
/*     */       
/* 267 */       if (preparedStatement != null) {
/*     */         try {
/* 269 */           preparedStatement.close();
/* 270 */         } catch (Exception exception) {
/* 271 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 273 */         preparedStatement = null;
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
/*     */   public String getVeName() {
/* 395 */     return "ADSWWCOMPATSEOCG";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 404 */     return "$Revision: 1.8 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WWCOMPATSEOCGABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */