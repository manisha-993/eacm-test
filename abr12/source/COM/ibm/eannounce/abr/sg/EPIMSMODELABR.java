/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLIdElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLNotificationElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
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
/*     */ public class EPIMSMODELABR
/*     */   extends EPIMSABRBase
/*     */ {
/*  61 */   private static final Vector FIRSTFINAL_XMLMAP_VCT = new Vector(); static {
/*  62 */     SAPLElem sAPLElem = new SAPLElem("ChangeNotification");
/*     */     
/*  64 */     FIRSTFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/*  66 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityType", "MODEL", "id"));
/*  67 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("Action", "EPIMSMODELSVC"));
/*  68 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("Timestamp"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String SERVICE = "102";
/*     */   
/*     */   protected Vector getMQPropertiesFN() {
/*  75 */     Vector<String> vector = new Vector(1);
/*  76 */     vector.add("EPIMSMQSERIES");
/*  77 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getXMLMap(boolean paramBoolean) {
/*  85 */     return FIRSTFINAL_XMLMAP_VCT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute() throws Exception {
/*  96 */     EntityItem entityItem = this.epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
/*     */     
/*  98 */     String str1 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "STATUS");
/*  99 */     addDebug("execute: " + entityItem.getKey() + " STATUS: " + 
/* 100 */         PokUtils.getAttributeValue(entityItem, "STATUS", ", ", "", false) + " [" + str1 + "] ");
/*     */     
/* 102 */     if (!"0020".equals(str1)) {
/* 103 */       addError("STATUS was not 'Final'");
/*     */       
/*     */       return;
/*     */     } 
/* 107 */     String str2 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "COFCAT");
/* 108 */     if (str2 == null) {
/* 109 */       str2 = "";
/*     */     }
/* 111 */     addDebug(entityItem.getKey() + " COFCAT: " + str2);
/*     */     
/* 113 */     if ("102".equals(str2))
/*     */     {
/* 115 */       notifyAndSetStatus(null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleResend(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 126 */     if (!"0020".equals(paramString)) {
/*     */       
/* 128 */       addError("RESEND_NOT_FINAL", null);
/*     */       
/*     */       return;
/*     */     } 
/* 132 */     String str = this.epimsAbr.getAttributeFlagEnabledValue(paramEntityItem, "COFCAT");
/* 133 */     if (str == null) {
/* 134 */       str = "";
/*     */     }
/* 136 */     addDebug(paramEntityItem.getKey() + " COFCAT: " + str);
/*     */     
/* 138 */     if ("102".equals(str))
/*     */     {
/* 140 */       notifyAndSetStatus(null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getVeName() {
/* 147 */     return "dummy";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 156 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\EPIMSMODELABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */