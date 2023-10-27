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
/*     */ public class WWCOMPATSEOCGOSABR
/*     */   extends ADSCOMPATGEN
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  57 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {
/*  65 */     Vector vector = new Vector(1);
/*  66 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("ROOT");
/*  67 */     DiffEntity diffEntity = vector1.firstElement();
/*  68 */     EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  69 */     EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*     */ 
/*     */ 
/*     */     
/*  73 */     String str1 = PokUtils.getAttributeFlagValue(entityItem1, "OS");
/*  74 */     String str2 = PokUtils.getAttributeFlagValue(entityItem2, "OS");
/*  75 */     paramADSABRSTATUS.addDebug("current | previous " + entityItem1.getKey() + " OS: " + str1 + " | " + str2);
/*  76 */     if (str1 == null)
/*  77 */       str1 = ""; 
/*  78 */     if (str2 == null)
/*  79 */       str2 = ""; 
/*  80 */     if (!str1.equals(str2)) {
/*  81 */       paramADSABRSTATUS.addXMLGenMsg("OS_CHANGE_FOUND", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  87 */       if (OSIndependent.equals(str2))
/*  88 */         deActivateCGOS(paramADSABRSTATUS, entityItem1.getEntityType(), entityItem1.getEntityID(), str2, paramString1, paramString2); 
/*  89 */       if (OSIndependent.equals(str1)) {
/*  90 */         populateCompat(paramADSABRSTATUS, vector, entityItem1, paramString1, paramString2);
/*     */       }
/*     */     } 
/*  93 */     vector.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateCompat(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector, EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException {
/* 104 */     String str1 = null;
/* 105 */     String str2 = "    where SystemGroup.GroupType = 'SEOCG' and                 \r\n    OSOption.OSType = ? and                        \r\n    OSOption.OSId = ?  with ur                  \r\n";
/*     */ 
/*     */     
/* 108 */     str1 = getCommSEOCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     Vector vector1 = new Vector();
/* 121 */     Vector vector2 = new Vector();
/* 122 */     Vector vector3 = new Vector();
/*     */     
/* 124 */     PreparedStatement preparedStatement = null;
/* 125 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 129 */       Connection connection = getConnection();
/* 130 */       preparedStatement = connection.prepareStatement(str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       preparedStatement.setString(1, paramEntityItem.getEntityType());
/* 137 */       preparedStatement.setInt(2, paramEntityItem.getEntityID());
/* 138 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 140 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector, paramEntityItem, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 186 */       vector2.clear();
/* 187 */       vector3.clear();
/* 188 */       vector1.clear();
/* 189 */       if (resultSet != null) {
/*     */         try {
/* 191 */           resultSet.close();
/* 192 */         } catch (Exception exception) {
/* 193 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 195 */         resultSet = null;
/*     */       } 
/*     */       
/* 198 */       if (preparedStatement != null) {
/*     */         try {
/* 200 */           preparedStatement.close();
/* 201 */         } catch (Exception exception) {
/* 202 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 204 */         preparedStatement = null;
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
/* 222 */     return "dummy";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 231 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\WWCOMPATSEOCGOSABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */