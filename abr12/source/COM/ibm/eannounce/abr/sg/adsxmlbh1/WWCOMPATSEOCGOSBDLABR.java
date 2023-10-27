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
/*     */ 
/*     */ public class WWCOMPATSEOCGOSBDLABR
/*     */   extends ADSCOMPATGEN
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/* 105 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, false);
/*     */   }
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
/*     */       
/* 123 */       String str = PokUtils.getAttributeValue(entityItem, "COMPATPUBFLG", ", ", "", false);
/* 124 */       if ("Delete".equals(str)) {
/* 125 */         paramADSABRSTATUS.addOutput("The value of COMPATPUBFLG is Delete, However this is new SEOCGOSSEO, there is nothing to be deleted.");
/*     */       } else {
/* 127 */         paramADSABRSTATUS.addXMLGenMsg("NEW_ENTITY", diffEntity.getKey());
/* 128 */         populateCompat(paramADSABRSTATUS, vector, entityItem, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
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
/* 144 */       String str1 = getValue(diffEntity, "COMPATPUBFLG");
/* 145 */       String str2 = getValue(diffEntity, "PUBFROM");
/* 146 */       String str3 = getValue(diffEntity, "PUBTO");
/* 147 */       String str4 = getFlagValue(diffEntity, "RELTYPE");
/* 148 */       if (str1 != null || str2 != null || str3 != null || str4 != null) {
/*     */         
/* 150 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 151 */         EntityItem entityItem2 = (EntityItem)entityItem.getUpLink(0);
/* 152 */         if (entityItem1 != null && entityItem2 != null) {
/* 153 */           if ("Delete".equals(str1)) {
/* 154 */             paramADSABRSTATUS.addXMLGenMsg("COMPATPUBFLG_CHANGE_FOUND", str1);
/* 155 */             deActivateMDLCGOSMDL(paramADSABRSTATUS, entityItem2.getEntityType(), entityItem2.getEntityID(), entityItem1.getEntityType(), entityItem1.getEntityID(), paramString1, paramString2);
/*     */           } else {
/* 157 */             paramADSABRSTATUS.addXMLGenMsg("INTEREST_CHANGE_FOUND", diffEntity.getKey());
/* 158 */             String str5 = PokUtils.getAttributeValue(entityItem, "COMPATPUBFLG", ", ", "", false);
/* 159 */             String str6 = PokUtils.getAttributeValue(entityItem, "PUBFROM", ", ", "", false);
/* 160 */             String str7 = PokUtils.getAttributeValue(entityItem, "PUBTO", ", ", "", false);
/* 161 */             String str8 = PokUtils.getAttributeFlagValue(entityItem, "RELTYPE");
/* 162 */             if (str8 == null) {
/* 163 */               str8 = "";
/*     */             }
/* 165 */             updateCOMPATPUBFLG(paramADSABRSTATUS, entityItem2.getEntityType(), entityItem2.getEntityID(), entityItem1.getEntityType(), entityItem1.getEntityID(), str5, str6, str7, str8, paramString1, paramString2);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     vector.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateCompat(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector, EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException {
/* 181 */     String str1 = null;
/* 182 */     String str2 = "    where OSOPTIONType = ? and OSOPTIONId= ? with ur                  \r\n";
/* 183 */     str1 = getCommSEOCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     Vector vector1 = new Vector();
/* 196 */     Vector vector2 = new Vector();
/* 197 */     Vector vector3 = new Vector();
/*     */     
/* 199 */     PreparedStatement preparedStatement = null;
/* 200 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 204 */       Connection connection = getConnection();
/* 205 */       preparedStatement = connection.prepareStatement(str1);
/*     */ 
/*     */ 
/*     */       
/* 209 */       preparedStatement.setString(1, paramEntityItem.getEntityType());
/* 210 */       preparedStatement.setInt(2, paramEntityItem.getEntityID());
/* 211 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 213 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector, paramEntityItem, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 259 */       vector2.clear();
/* 260 */       vector3.clear();
/* 261 */       vector1.clear();
/* 262 */       if (resultSet != null) {
/*     */         try {
/* 264 */           resultSet.close();
/* 265 */         } catch (Exception exception) {
/* 266 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 268 */         resultSet = null;
/*     */       } 
/*     */       
/* 271 */       if (preparedStatement != null) {
/*     */         try {
/* 273 */           preparedStatement.close();
/* 274 */         } catch (Exception exception) {
/* 275 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 277 */         preparedStatement = null;
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
/* 295 */     return "ADSWWCOMPATSEOCGOSBDL";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 303 */     return "$Revision: 1.6 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WWCOMPATSEOCGOSBDLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */