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
/*     */ public class WWCOMPATMODCGOSABR
/*     */   extends ADSCOMPATGEN
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  60 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {
/*  69 */     Vector vector = new Vector(1);
/*  70 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("ROOT");
/*  71 */     DiffEntity diffEntity = vector1.firstElement();
/*  72 */     EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  73 */     EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*     */ 
/*     */ 
/*     */     
/*  77 */     String str1 = PokUtils.getAttributeFlagValue(entityItem1, "OS");
/*  78 */     String str2 = PokUtils.getAttributeFlagValue(entityItem2, "OS");
/*  79 */     paramADSABRSTATUS.addDebug("current | previous " + entityItem1.getKey() + " OS: " + str1 + " | " + str2);
/*  80 */     if (str1 == null)
/*  81 */       str1 = ""; 
/*  82 */     if (str2 == null)
/*  83 */       str2 = ""; 
/*  84 */     if (!str1.equals(str2)) {
/*  85 */       paramADSABRSTATUS.addXMLGenMsg("OS_CHANGE_FOUND", str1);
/*     */       
/*  87 */       if (OSIndependent.equals(str2))
/*  88 */         deActivateCGOS(paramADSABRSTATUS, entityItem1.getEntityType(), entityItem1.getEntityID(), str2, paramString1, paramString2); 
/*  89 */       if (OSIndependent.equals(str1)) {
/*  90 */         populateCompat(paramADSABRSTATUS, vector, entityItem1, paramString1, paramString2);
/*     */       }
/*     */     } 
/*     */     
/*  94 */     vector.clear();
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
/* 105 */     String str1 = null;
/* 106 */     String str2 = "    MODELCGOS.entityid = ? with ur \r\n";
/* 107 */     str1 = getCommMODELCGSql(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     Vector vector1 = new Vector();
/* 120 */     Vector vector2 = new Vector();
/* 121 */     Vector vector3 = new Vector();
/*     */     
/* 123 */     PreparedStatement preparedStatement = null;
/* 124 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 128 */       Connection connection = getConnection();
/* 129 */       preparedStatement = connection.prepareStatement(str1);
/* 130 */       preparedStatement.setInt(1, paramEntityItem.getEntityID());
/*     */       
/* 132 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 134 */       putWWCOMPATVector(paramADSABRSTATUS, paramVector, paramEntityItem, resultSet, vector1, vector2, vector3, paramString1, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 180 */       vector2.clear();
/* 181 */       vector3.clear();
/* 182 */       vector1.clear();
/* 183 */       if (resultSet != null) {
/*     */         try {
/* 185 */           resultSet.close();
/* 186 */         } catch (Exception exception) {
/* 187 */           System.err.println("getMatchingDateIds(), unable to close result. " + exception);
/*     */         } 
/* 189 */         resultSet = null;
/*     */       } 
/*     */       
/* 192 */       if (preparedStatement != null) {
/*     */         try {
/* 194 */           preparedStatement.close();
/* 195 */         } catch (Exception exception) {
/* 196 */           System.err.println("getMatchingDateIds(), unable to close ps. " + exception);
/*     */         } 
/* 198 */         preparedStatement = null;
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
/* 216 */     return "dummy";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 225 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\WWCOMPATMODCGOSABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */