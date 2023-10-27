/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SVCPRODSTRUCTABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*  78 */   private Object[] args = new Object[3];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/*  84 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 136 */     if ("0010".equals(paramString) || "0050".equals(paramString)) {
/*     */       
/* 138 */       EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/* 139 */       EntityItem entityItem2 = this.m_elist.getEntityGroup("SVCFEATURE").getEntityItem(0);
/*     */ 
/*     */ 
/*     */       
/* 143 */       String str = getAttributeFlagEnabledValue(entityItem1, "STATUS");
/* 144 */       addDebug(entityItem1.getKey() + " check status " + str);
/* 145 */       if (str == null) {
/* 146 */         str = "0020";
/*     */       }
/*     */       
/* 149 */       if (!"0020".equals(str) && !"0040".equals(str)) {
/* 150 */         addDebug(entityItem1.getKey() + " is not Final or R4R");
/*     */         
/* 152 */         this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 153 */         this.args[1] = getNavigationName(entityItem1);
/* 154 */         addError("NOT_R4R_FINAL_ERR", this.args);
/*     */       } 
/*     */ 
/*     */       
/* 158 */       str = getAttributeFlagEnabledValue(entityItem2, "STATUS");
/* 159 */       addDebug(entityItem2.getKey() + " check status " + str);
/* 160 */       if (str == null) {
/* 161 */         str = "0020";
/*     */       }
/*     */       
/* 164 */       if (!"0020".equals(str) && !"0040".equals(str)) {
/* 165 */         addDebug(entityItem2.getKey() + " is not Final or R4R");
/*     */         
/* 167 */         this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/* 168 */         this.args[1] = getNavigationName(entityItem2);
/* 169 */         addError("NOT_R4R_FINAL_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     if ("0040".equals(paramString)) {
/*     */ 
/*     */ 
/*     */       
/* 177 */       checkStatus("MODEL");
/*     */ 
/*     */ 
/*     */       
/* 181 */       checkStatus("SVCFEATURE");
/*     */ 
/*     */ 
/*     */       
/* 185 */       checkAnnDate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAnnDate() throws SQLException, MiddlewareException {
/* 196 */     EntityItem entityItem = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/* 197 */     String str = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "", false);
/* 198 */     addDebug("checkAnnDate " + entityItem.getKey() + " (" + str + ")");
/*     */     
/* 200 */     if (str.length() > 0) {
/*     */       
/* 202 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 203 */       Vector<EntityItem> vector = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "146");
/*     */       
/* 205 */       for (byte b = 0; b < vector.size(); b++) {
/* 206 */         EntityItem entityItem1 = vector.elementAt(b);
/* 207 */         addDebug("checkAnnDate looking for planned avail; checking: " + entityItem1.getKey());
/* 208 */         String str1 = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", "", false);
/* 209 */         addDebug("checkAnnDate plannedavail " + entityItem1.getKey() + " EFFECTIVEDATE: " + str1);
/* 210 */         if (str1.length() > 0 && str.compareTo(str1) > 0) {
/*     */           
/* 212 */           this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 213 */           this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 214 */           addError("EARLY_PLANNEDAVAIL_ERR", this.args);
/*     */         } 
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
/*     */   public String getDescription() {
/* 227 */     return "SVCPRODSTRUCT ABR";
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
/* 238 */     return "1.7";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SVCPRODSTRUCTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */