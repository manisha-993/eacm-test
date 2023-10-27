/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CHRGCOMPABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  77 */     int i = getCount("CHRGCOMPCVM");
/*  78 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CVM");
/*     */ 
/*     */     
/*  81 */     if (i == 0) {
/*     */       
/*  83 */       this.args[0] = entityGroup1.getLongDescription();
/*  84 */       createMessage(3, "MINIMUM_ERR", this.args);
/*     */     } 
/*  86 */     for (byte b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++) {
/*  87 */       EntityItem entityItem = entityGroup1.getEntityItem(b1);
/*     */       
/*  89 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*     */     } 
/*     */ 
/*     */     
/*  93 */     i = getCount("CHRGCOMPPRCPT");
/*  94 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("PRCPT");
/*     */     
/*  96 */     if (i == 0) {
/*     */       
/*  98 */       this.args[0] = entityGroup2.getLongDescription();
/*  99 */       createMessage(3, "MINIMUM_ERR", this.args);
/*     */     } 
/* 101 */     for (byte b2 = 0; b2 < entityGroup2.getEntityItemCount(); b2++) {
/* 102 */       EntityItem entityItem = entityGroup2.getEntityItem(b2);
/*     */       
/* 104 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*     */     } 
/* 106 */     if (getReturnCode() == 0)
/*     */     {
/* 108 */       setCHRGCOMPID(paramEntityItem);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     oneValidOverTime(paramEntityItem, new String[] { "CHRGCOMPCVM", "CVM" }, 4);
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
/*     */   
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 139 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SVCMOD");
/* 140 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 141 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       if (statusIsFinal(entityItem)) {
/*     */         
/* 150 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*     */ 
/*     */       
/*     */       }
/* 154 */       else if (statusIsRFR(entityItem)) {
/*     */ 
/*     */         
/* 157 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 183 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SVCMOD");
/* 184 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 185 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       if (statusIsRFR(entityItem))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 198 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setCHRGCOMPID(EntityItem paramEntityItem) {
/* 221 */     String str = PokUtils.getAttributeValue(paramEntityItem, "CHRGCOMPID", "", null, false);
/* 222 */     addDebug("setCHRGCOMPID: chrgcompid " + str);
/* 223 */     if (str == null) {
/* 224 */       String str1 = "0000" + paramEntityItem.getEntityID();
/* 225 */       int i = str1.length();
/* 226 */       if (i > 5) {
/* 227 */         str1 = str1.substring(i - 5);
/*     */       }
/* 229 */       str1 = "CC" + str1;
/* 230 */       addDebug("setCHRGCOMPID: new chrgcompid " + str1);
/* 231 */       setTextValue(this.m_elist.getProfile(), "CHRGCOMPID", str1, paramEntityItem);
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
/* 242 */     return "CHRGCOMP ABR.";
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
/* 254 */     return "$Revision: 1.3 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\CHRGCOMPABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */