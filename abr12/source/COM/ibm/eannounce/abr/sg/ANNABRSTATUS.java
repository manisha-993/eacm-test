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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ANNABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/* 143 */   private static final String[] ETYPES_CHKD = new String[] { "PRODSTRUCT", "LSEO", "LSEOBUNDLE", "AVAIL" };
/* 144 */   private Object[] args = new Object[5];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/* 150 */     return "0040".equals(paramString);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 172 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 173 */     String str = getAttributeFlagEnabledValue(entityItem, "ANNTYPE");
/* 174 */     addDebug(entityItem.getKey() + " status now final type " + str);
/*     */     
/* 176 */     if ("19".equals(str)) {
/* 177 */       addDebug(entityItem.getKey() + " is Final and New");
/*     */       
/* 179 */       setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020");
/* 180 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 181 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 182 */         EntityItem entityItem1 = entityGroup.getEntityItem(b);
/* 183 */         String str1 = getAttributeFlagEnabledValue(entityItem1, "AVAILTYPE");
/* 184 */         addDebug(entityItem1.getKey() + " type " + str1);
/*     */         
/* 186 */         if ("146".equals(str1)) {
/* 187 */           verifyStatusAndSetEPIMSABRStatus(entityItem1, "LSEOAVAIL", "LSEO");
/* 188 */           verifyStatusAndSetEPIMSABRStatus(entityItem1, "LSEOBUNDLEAVAIL", "LSEOBUNDLE");
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 194 */       setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem);
/*     */     } 
/*     */     
/* 197 */     if ("14".equals(str))
/*     */     {
/*     */ 
/*     */       
/* 201 */       setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void verifyStatusAndSetEPIMSABRStatus(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 209 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, paramString2);
/* 210 */     for (byte b = 0; b < vector.size(); b++) {
/* 211 */       EntityItem entityItem = vector.elementAt(b);
/* 212 */       String str = getAttributeFlagEnabledValue(entityItem, "STATUS");
/* 213 */       addDebug(entityItem.getKey() + " status " + str);
/* 214 */       if (str == null || str.length() == 0) {
/* 215 */         str = "0020";
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 220 */       if ("0020".equals(str)) {
/* 221 */         addDebug(entityItem.getKey() + " is Final");
/*     */ 
/*     */ 
/*     */         
/* 225 */         setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", "0020", entityItem);
/*     */       } 
/*     */     } 
/* 228 */     vector.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 239 */     if ("0010".equals(paramString) || "0050".equals(paramString)) {
/*     */ 
/*     */       
/* 242 */       addDebug("No checking required, status is draft or change request");
/*     */     }
/* 244 */     else if ("0040".equals(paramString)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       for (byte b = 0; b < ETYPES_CHKD.length; b++) {
/* 258 */         checkStatus(ETYPES_CHKD[b]);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 263 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 264 */       Vector vector = PokUtils.getAllLinkedEntities(entityGroup, "MODELAVAIL", "MODEL");
/* 265 */       addDebug("MODEL thru MODELAVAIL size: " + vector.size());
/* 266 */       checkStatus(vector);
/* 267 */       vector.clear();
/*     */ 
/*     */ 
/*     */       
/* 271 */       checkCountry("ANNAVAILA", "D", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 279 */       checkAvailDates(paramEntityItem);
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
/*     */   private void checkAvailDates(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 295 */     String str1 = getAttributeFlagEnabledValue(paramEntityItem, "ANNTYPE");
/* 296 */     addDebug("checkAvailDates " + paramEntityItem.getKey() + " anntype " + str1);
/*     */     
/* 298 */     if (!"19".equals(str1)) {
/*     */       return;
/*     */     }
/* 301 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "ANNAVAILA", "AVAIL");
/* 302 */     String str2 = getAttributeValue(paramEntityItem, "ANNDATE", "");
/* 303 */     addDebug("checkAvailDates " + paramEntityItem.getKey() + " ANNDATE: " + str2 + " availVct: " + vector.size());
/*     */     
/* 305 */     if (str2.trim().length() == 0) {
/*     */       
/* 307 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 308 */       addError("MUST_HAVE_ERR", this.args);
/*     */       
/*     */       return;
/*     */     } 
/* 312 */     for (byte b = 0; b < vector.size(); b++) {
/* 313 */       EntityItem entityItem = vector.elementAt(b);
/* 314 */       String str3 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 315 */       String str4 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 316 */       addDebug("checkAvailDates " + entityItem.getKey() + " EFFECTIVEDATE: " + str3 + " AVAILTYPE: " + str4);
/*     */ 
/*     */       
/* 319 */       if (("146".equals(str4) || "143".equals(str4)) && str3
/* 320 */         .length() > 0 && str3.compareTo(str2) < 0) {
/*     */ 
/*     */         
/* 323 */         this.args[0] = entityItem.getEntityGroup().getLongDescription() + " " + getNavigationName(entityItem) + " " + 
/* 324 */           PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "EFFECTIVEDATE", "EFFECTIVEDATE");
/* 325 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 326 */         this.args[2] = "";
/* 327 */         this.args[3] = "";
/* 328 */         addError("EARLY_DATE_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStatusAttrCode() {
/* 336 */     return "ANNSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 345 */     return "ANNOUNCEMENT ABR.";
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
/* 356 */     return "1.35";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ANNABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */