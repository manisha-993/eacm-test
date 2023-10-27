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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WWCOMPATMDLCGOSMDLABR
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
/*     */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {
/* 116 */     Vector vector = new Vector();
/* 117 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("ROOT");
/* 118 */     DiffEntity diffEntity = vector1.firstElement();
/* 119 */     EntityItem entityItem = diffEntity.getCurrentEntityItem();
/*     */     
/* 121 */     if (diffEntity.isNew()) {
/* 122 */       paramADSABRSTATUS.addXMLGenMsg("NEW_ENTITY", diffEntity.getKey());
/* 123 */       populateCompat(paramADSABRSTATUS, vector, entityItem, paramString1, paramString2);
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
/* 138 */       String str1 = getValue(diffEntity, "COMPATPUBFLG");
/* 139 */       String str2 = getValue(diffEntity, "PUBFROM");
/* 140 */       String str3 = getValue(diffEntity, "PUBTO");
/* 141 */       String str4 = getFlagValue(diffEntity, "RELTYPE");
/*     */       
/* 143 */       if (str1 != null || str2 != null || str3 != null || str4 != null) {
/*     */         
/* 145 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 146 */         EntityItem entityItem2 = (EntityItem)entityItem.getUpLink(0);
/* 147 */         if (entityItem1 != null && entityItem2 != null) {
/* 148 */           if ("Delete".equals(str1)) {
/* 149 */             paramADSABRSTATUS.addXMLGenMsg("COMPATPUBFLG_CHANGE_FOUND", str1);
/* 150 */             deActivateMDLCGOSMDL(paramADSABRSTATUS, entityItem2.getEntityType(), entityItem2.getEntityID(), entityItem1.getEntityType(), entityItem1
/* 151 */                 .getEntityID(), paramString1, paramString2);
/*     */           } else {
/* 153 */             paramADSABRSTATUS.addXMLGenMsg("INTEREST_CHANGE_FOUND", diffEntity.getKey());
/* 154 */             String str5 = PokUtils.getAttributeValue(entityItem, "COMPATPUBFLG", ", ", "", false);
/* 155 */             String str6 = PokUtils.getAttributeValue(entityItem, "PUBFROM", ", ", "", false);
/* 156 */             String str7 = PokUtils.getAttributeValue(entityItem, "PUBTO", ", ", "", false);
/* 157 */             String str8 = PokUtils.getAttributeFlagValue(entityItem, "RELTYPE");
/* 158 */             if (str8 == null) {
/* 159 */               str8 = "";
/*     */             }
/* 161 */             updateCOMPATPUBFLG(paramADSABRSTATUS, entityItem2.getEntityType(), entityItem2.getEntityID(), entityItem1.getEntityType(), entityItem1
/* 162 */                 .getEntityID(), str5, str6, str7, str8, paramString1, paramString2);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 167 */     vector.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateCompat(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector, EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException {
/* 177 */     String str1 = null;
/* 178 */     String str2 = "    MDLCGOSMDL.entityid = ? with ur \r\n";
/* 179 */     str1 = getCommMODELCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     Vector vector1 = new Vector();
/* 192 */     Vector vector2 = new Vector();
/* 193 */     Vector vector3 = new Vector();
/*     */     
/* 195 */     PreparedStatement preparedStatement = null;
/* 196 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 200 */       Connection connection = getConnection();
/* 201 */       preparedStatement = connection.prepareStatement(str1);
/* 202 */       preparedStatement.setInt(1, paramEntityItem.getEntityID());
/* 203 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 205 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector, paramEntityItem, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 252 */       vector2.clear();
/* 253 */       vector3.clear();
/* 254 */       vector1.clear();
/* 255 */       if (resultSet != null) {
/*     */         try {
/* 257 */           resultSet.close();
/* 258 */         } catch (Exception exception) {
/* 259 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 261 */         resultSet = null;
/*     */       } 
/*     */       
/* 264 */       if (preparedStatement != null) {
/*     */         try {
/* 266 */           preparedStatement.close();
/* 267 */         } catch (Exception exception) {
/* 268 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 270 */         preparedStatement = null;
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
/*     */   public String getVeName() {
/* 288 */     return "ADSWWCOMPATMDLCGOSMDL1";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 296 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\WWCOMPATMDLCGOSMDLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */