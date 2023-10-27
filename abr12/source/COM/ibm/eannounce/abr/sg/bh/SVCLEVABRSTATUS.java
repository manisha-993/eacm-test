/*     */ package COM.ibm.eannounce.abr.sg.bh;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SVCLEVABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  78 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  79 */     String str = PokUtils.getAttributeFlagValue(entityItem, "LIFECYCLE");
/*     */     
/*  81 */     if (str == null || str.length() == 0) {
/*  82 */       str = "LF01";
/*     */     }
/*     */     
/*  85 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SVCLEVSLCNTRYCOND", "SLCNTRYCOND");
/*  86 */     addDebug("nowRFR: " + entityItem.getKey() + " slcntryVct.size " + vector.size() + " lifecycle " + str);
/*  87 */     if (vector.size() > 0) {
/*     */ 
/*     */       
/*  90 */       if ("LF01".equals(str) || "LF02"
/*  91 */         .equals(str))
/*     */       {
/*  93 */         for (byte b = 0; b < vector.size(); b++) {
/*  94 */           EntityItem entityItem1 = vector.elementAt(b);
/*     */           
/*  96 */           if (statusIsRFRorFinal(entityItem1)) {
/*     */             
/*  98 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 103 */         vector.clear();
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 109 */       doRFR_ADSXML(entityItem);
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 124 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 125 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SVCLEVSLCNTRYCOND", "SLCNTRYCOND");
/* 126 */     addDebug("nowFinal: " + entityItem.getKey() + " slcntryVct.size " + vector.size());
/* 127 */     if (vector.size() > 0) {
/*     */       
/* 129 */       for (byte b = 0; b < vector.size(); b++) {
/*     */         
/* 131 */         EntityItem entityItem1 = vector.elementAt(b);
/*     */         
/* 133 */         if (statusIsFinal(entityItem1)) {
/*     */ 
/*     */           
/* 136 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 142 */       vector.clear();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 148 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLCRFRWFName() {
/* 159 */     return "WFLCSVCLEVRFR"; } protected String getLCFinalWFName() {
/* 160 */     return "WFLCSVCLEVFINAL";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 169 */     addDebug("No checking required");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 179 */     return "SVCLEV ABR.";
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
/* 190 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SVCLEVABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */