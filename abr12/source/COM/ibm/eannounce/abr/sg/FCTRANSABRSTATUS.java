/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FCTRANSABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*  67 */   private Object[] args = new Object[5];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/*  73 */     return true;
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  99 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 100 */     addDebug(entityItem.getKey() + " status now final");
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
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 112 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "FEATURETRNAVAIL", "AVAIL");
/*     */ 
/*     */ 
/*     */     
/* 116 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 117 */     addDebug("checkAvailDates " + paramEntityItem.getKey() + " availVct " + vector1.size() + " plannedavailVector " + vector2.size());
/* 118 */     if (vector2.size() == 0) {
/*     */ 
/*     */       
/* 121 */       this.args[0] = "Planned Availability";
/* 122 */       addError("MINIMUM_ERR", this.args);
/*     */     } 
/*     */     
/* 125 */     if ("0040".equals(paramString))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 130 */       checkAvailDates(paramEntityItem, vector1);
/*     */     }
/* 132 */     vector1.clear();
/* 133 */     vector2.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAvailDates(EntityItem paramEntityItem, Vector<EntityItem> paramVector) throws SQLException, MiddlewareException {
/* 144 */     String str = getAttributeValue(paramEntityItem, "ANNDATE", "");
/* 145 */     addDebug("checkAvailDates " + paramEntityItem.getKey() + " ANNDATE: " + str + " availVct: " + paramVector.size());
/*     */     
/* 147 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 148 */       EntityItem entityItem = paramVector.elementAt(b);
/* 149 */       String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 150 */       String str2 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 151 */       addDebug("checkAvailDates " + entityItem.getKey() + " EFFECTIVEDATE: " + str1 + " AVAILTYPE: " + str2);
/*     */ 
/*     */       
/* 154 */       if (("146".equals(str2) || "143".equals(str2)) && str1
/* 155 */         .length() > 0 && str1.compareTo(str) < 0) {
/*     */ 
/*     */         
/* 158 */         this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 159 */         this.args[1] = "";
/* 160 */         this.args[2] = "";
/* 161 */         this.args[3] = entityItem.getEntityGroup().getLongDescription();
/* 162 */         this.args[4] = getNavigationName(entityItem);
/* 163 */         addError("LATER_DATE_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStatusAttrCode() {
/* 171 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 180 */     return "FCTRANSACTION ABR.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 191 */     return "1.5";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FCTRANSABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */