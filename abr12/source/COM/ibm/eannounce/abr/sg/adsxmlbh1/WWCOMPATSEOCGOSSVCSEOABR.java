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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WWCOMPATSEOCGOSSVCSEOABR
/*     */   extends ADSCOMPATGEN
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/* 104 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, false);
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
/*     */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {
/* 119 */     Vector vector = new Vector();
/* 120 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("ROOT");
/* 121 */     DiffEntity diffEntity = vector1.firstElement();
/* 122 */     EntityItem entityItem = diffEntity.getCurrentEntityItem();
/*     */     
/* 124 */     if (diffEntity.isNew()) {
/* 125 */       paramADSABRSTATUS.addXMLGenMsg("NEW_ENTITY", diffEntity.getKey());
/* 126 */       populateCompat(paramADSABRSTATUS, vector, entityItem, paramString1, paramString2);
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
/* 141 */       String str1 = getValue(diffEntity, "COMPATPUBFLG");
/* 142 */       String str2 = getValue(diffEntity, "PUBFROM");
/* 143 */       String str3 = getValue(diffEntity, "PUBTO");
/* 144 */       String str4 = getFlagValue(diffEntity, "RELTYPE");
/* 145 */       if (str1 != null || str2 != null || str3 != null || str4 != null) {
/*     */         
/* 147 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 148 */         EntityItem entityItem2 = (EntityItem)entityItem.getUpLink(0);
/* 149 */         if (entityItem1 != null && entityItem2 != null) {
/* 150 */           if ("Delete".equals(str1)) {
/* 151 */             paramADSABRSTATUS.addXMLGenMsg("COMPATPUBFLG_CHANGE_FOUND", str1);
/* 152 */             deActivateMDLCGOSMDL(paramADSABRSTATUS, entityItem2.getEntityType(), entityItem2.getEntityID(), entityItem1.getEntityType(), entityItem1.getEntityID(), paramString1, paramString2);
/*     */           } else {
/* 154 */             paramADSABRSTATUS.addXMLGenMsg("INTEREST_CHANGE_FOUND", diffEntity.getKey());
/* 155 */             String str5 = PokUtils.getAttributeValue(entityItem, "COMPATPUBFLG", ", ", "", false);
/* 156 */             String str6 = PokUtils.getAttributeValue(entityItem, "PUBFROM", ", ", "", false);
/* 157 */             String str7 = PokUtils.getAttributeValue(entityItem, "PUBTO", ", ", "", false);
/* 158 */             String str8 = PokUtils.getAttributeFlagValue(entityItem, "RELTYPE");
/* 159 */             if (str8 == null) {
/* 160 */               str8 = "";
/*     */             }
/* 162 */             updateCOMPATPUBFLG(paramADSABRSTATUS, entityItem2.getEntityType(), entityItem2.getEntityID(), entityItem1.getEntityType(), entityItem1.getEntityID(), str5, str6, str7, str8, paramString1, paramString2);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     vector.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateCompat(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector, EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException {
/* 178 */     String str1 = null;
/* 179 */     String str2 = "    where OSOPTIONType = ? and OSOPTIONId= ? with ur                  \r\n";
/* 180 */     str1 = getCommSEOCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     Vector vector1 = new Vector();
/* 193 */     Vector vector2 = new Vector();
/* 194 */     Vector vector3 = new Vector();
/*     */     
/* 196 */     PreparedStatement preparedStatement = null;
/* 197 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 201 */       Connection connection = getConnection();
/* 202 */       preparedStatement = connection.prepareStatement(str1);
/*     */ 
/*     */ 
/*     */       
/* 206 */       preparedStatement.setString(1, paramEntityItem.getEntityType());
/* 207 */       preparedStatement.setInt(2, paramEntityItem.getEntityID());
/* 208 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 210 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector, paramEntityItem, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public String getVeName() {
/* 289 */     return "ADSWWCOMPATSEOCGOSSVCSEO";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 298 */     return "$Revision: 1.6 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WWCOMPATSEOCGOSSVCSEOABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */