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
/*     */ public class SLCNTRYABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  74 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  75 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SVCLEVSLCNTRYCOND", "SVCLEV");
/*  76 */     addDebug("nowRFR: " + entityItem.getKey() + " svclevVct.size " + vector.size());
/*  77 */     for (byte b = 0; b < vector.size(); b++) {
/*  78 */       EntityItem entityItem1 = vector.elementAt(b);
/*     */ 
/*     */ 
/*     */       
/*  82 */       doRFR_ADSXML(entityItem1);
/*     */     } 
/*  84 */     vector.clear();
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 101 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 102 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SVCLEVSLCNTRYCOND", "SVCLEV");
/* 103 */     addDebug("nowFinal: " + entityItem.getKey() + " svclevVct.size " + vector.size());
/* 104 */     for (byte b = 0; b < vector.size(); b++) {
/* 105 */       EntityItem entityItem1 = vector.elementAt(b);
/*     */       
/* 107 */       if (statusIsFinal(entityItem1))
/*     */       {
/* 109 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*     */       }
/*     */     } 
/* 112 */     vector.clear();
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
/* 123 */     addDebug("No checking required");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 133 */     return "SVCLEV ABR.";
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
/* 144 */     return "$Revision: 1.2 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SLCNTRYABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */