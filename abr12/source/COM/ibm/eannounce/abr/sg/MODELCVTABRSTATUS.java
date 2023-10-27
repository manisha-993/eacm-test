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
/*     */ public class MODELCVTABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*  63 */   private Object[] args = new Object[5];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/*  69 */     return true;
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
/*  96 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  97 */     addDebug(entityItem.getKey() + " status now final");
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
/* 109 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELCONVERTAVAIL", "AVAIL");
/*     */ 
/*     */ 
/*     */     
/* 113 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 114 */     addDebug("checkAvailDates " + paramEntityItem.getKey() + " availVct " + vector1.size() + " plannedavailVector " + vector2.size());
/* 115 */     if (vector2.size() == 0) {
/*     */ 
/*     */       
/* 118 */       this.args[0] = "Planned Availability";
/* 119 */       addError("MINIMUM_ERR", this.args);
/*     */     } 
/*     */     
/* 122 */     if ("0040".equals(paramString))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 127 */       checkAvailDates(paramEntityItem, vector1);
/*     */     }
/* 129 */     vector1.clear();
/* 130 */     vector2.clear();
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
/* 141 */     String str = getAttributeValue(paramEntityItem, "ANNDATE", "");
/* 142 */     addDebug("checkAvailDates " + paramEntityItem.getKey() + " ANNDATE: " + str + " availVct: " + paramVector.size());
/*     */     
/* 144 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 145 */       EntityItem entityItem = paramVector.elementAt(b);
/* 146 */       String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 147 */       String str2 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 148 */       addDebug("checkAvailDates " + entityItem.getKey() + " EFFECTIVEDATE: " + str1 + " AVAILTYPE: " + str2);
/*     */ 
/*     */       
/* 151 */       if (("146".equals(str2) || "143".equals(str2)) && str1
/* 152 */         .length() > 0 && str1.compareTo(str) < 0) {
/*     */ 
/*     */         
/* 155 */         this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 156 */         this.args[1] = "";
/* 157 */         this.args[2] = "";
/* 158 */         this.args[3] = entityItem.getEntityGroup().getLongDescription();
/* 159 */         this.args[4] = getNavigationName(entityItem);
/* 160 */         addError("LATER_DATE_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStatusAttrCode() {
/* 168 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 177 */     return "MODELCONVERT ABR.";
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
/* 188 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MODELCVTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */