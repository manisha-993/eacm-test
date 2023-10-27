/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FCABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   protected boolean isVEneeded(String paramString) {
/* 144 */     return (domainInList() && "0040".equals(paramString));
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
/* 171 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 172 */     addDebug(entityItem.getKey() + " status now final");
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
/* 184 */     if ("0010".equals(paramString) || "0050".equals(paramString)) {
/*     */ 
/*     */       
/* 187 */       addDebug("No checking required, status is draft or change request");
/*     */     }
/* 189 */     else if ("0040".equals(paramString)) {
/*     */ 
/*     */ 
/*     */       
/* 193 */       int i = getCount("FEATUREMONITOR");
/* 194 */       if (i > 1) {
/* 195 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("MONITOR");
/*     */ 
/*     */         
/* 198 */         Object[] arrayOfObject = { entityGroup.getLongDescription() };
/* 199 */         addError("MORE_THAN_ONE_ERR", arrayOfObject);
/*     */       } 
/*     */ 
/*     */       
/* 203 */       HashSet<String> hashSet = new HashSet();
/* 204 */       hashSet.add("120");
/* 205 */       hashSet.add("130");
/* 206 */       hashSet.add("402");
/*     */       
/* 208 */       if (!PokUtils.contains(paramEntityItem, "FCTYPE", hashSet)) {
/* 209 */         addDebug(paramEntityItem.getKey() + " was NOT an RPQ FCTYPE: " + getAttributeFlagEnabledValue(paramEntityItem, "FCTYPE"));
/* 210 */         checkLastOrderAvailCountry(paramEntityItem, "WITHDRAWDATEEFF_T");
/*     */       } else {
/* 212 */         addDebug(paramEntityItem.getKey() + " was an RPQ FCTYPE: " + getAttributeFlagEnabledValue(paramEntityItem, "FCTYPE"));
/*     */       } 
/*     */       
/* 215 */       hashSet.clear();
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
/* 226 */     return "FEATURE ABR.";
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
/*     */   public String getABRVersion() {
/* 238 */     return "1.25";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FCABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */