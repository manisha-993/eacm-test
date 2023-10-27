/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.eacm.AES256Utils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
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
/*     */ public abstract class XMLMQAdapter
/*     */   implements XMLMQ
/*     */ {
/*     */   public Vector getMQPropertiesFN() {
/*  45 */     Vector<String> vector = new Vector(1);
/*  46 */     vector.add("ADSMQSERIES");
/*  47 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean createXML(EntityItem paramEntityItem) {
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  63 */     return "dummy";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRoleCode() {
/*  68 */     return "BHFEED";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/*  73 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getMQCID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/*  86 */     return "";
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
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Connection setupConnection() throws SQLException {
/* 110 */     Connection connection = null;
/*     */     try {
/* 112 */       connection = DriverManager.getConnection(
/* 113 */           MiddlewareServerProperties.getPDHDatabaseURL(), 
/* 114 */           MiddlewareServerProperties.getPDHDatabaseUser(), 
/* 115 */           AES256Utils.decrypt(MiddlewareServerProperties.getPDHDatabasePassword()));
/* 116 */     } catch (SQLException sQLException) {
/*     */       
/* 118 */       throw sQLException;
/* 119 */     } catch (Exception exception) {
/*     */       
/* 121 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 124 */     connection.setAutoCommit(false);
/*     */     
/* 126 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeConnection(Connection paramConnection) throws SQLException {
/* 133 */     if (paramConnection != null)
/*     */       try {
/* 135 */         paramConnection.rollback();
/*     */       }
/* 137 */       catch (Throwable throwable) {
/* 138 */         System.err.println("XMLMQAdapter.closeConnection(), unable to rollback. " + throwable);
/*     */       } finally {
/*     */         
/* 141 */         paramConnection.close();
/* 142 */         paramConnection = null;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\XMLMQAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */