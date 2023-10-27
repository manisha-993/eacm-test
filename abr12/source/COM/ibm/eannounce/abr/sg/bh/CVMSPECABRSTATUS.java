/*     */ package COM.ibm.eannounce.abr.sg.bh;
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
/*     */ public class CVMSPECABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  55 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "CHARACID");
/*  56 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "CVMTYPE");
/*  57 */     addDebug("doDQChecking: " + paramEntityItem.getKey() + " characid " + str1 + " cvmtype " + str2);
/*  58 */     if (str1 == null) {
/*  59 */       str1 = "";
/*     */     }
/*  61 */     if (str2 == null) {
/*  62 */       str2 = "";
/*     */     }
/*     */ 
/*     */     
/*  66 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CVM");
/*  67 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  68 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  69 */       String str3 = PokUtils.getAttributeFlagValue(entityItem, "CHARACID");
/*  70 */       String str4 = PokUtils.getAttributeFlagValue(entityItem, "CVMTYPE");
/*  71 */       addDebug("doDQChecking: " + entityItem.getKey() + " cvmcharacid " + str3 + " cvmcvmtype " + str4);
/*  72 */       if (str3 == null) {
/*  73 */         str3 = "";
/*     */       }
/*  75 */       if (str4 == null) {
/*  76 */         str4 = "";
/*     */       }
/*     */ 
/*     */       
/*  80 */       if (!str1.equals(str3)) {
/*     */         
/*  82 */         this.args[0] = getLD_NDN(entityItem);
/*  83 */         this.args[1] = getLD_Value(entityItem, "CHARACID");
/*  84 */         this.args[2] = getLD_NDN(paramEntityItem);
/*  85 */         this.args[3] = getLD_Value(paramEntityItem, "CHARACID");
/*  86 */         createMessage(4, "NOT_EQUAL_ERR", this.args);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  91 */       if (!str2.equals(str4)) {
/*     */         
/*  93 */         this.args[0] = getLD_NDN(entityItem);
/*  94 */         this.args[1] = getLD_Value(entityItem, "CVMTYPE");
/*  95 */         this.args[2] = getLD_NDN(paramEntityItem);
/*  96 */         this.args[3] = getLD_Value(paramEntityItem, "CVMTYPE");
/*  97 */         createMessage(4, "NOT_EQUAL_ERR", this.args);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 126 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CVM");
/* 127 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 128 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 129 */       addDebug("completeNowFinalProcessing: " + entityItem.getKey());
/*     */       
/* 131 */       if (statusIsRFRorFinal(entityItem)) {
/* 132 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "CHRGCOMPCVM", "CHRGCOMP");
/* 133 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 134 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 135 */           addDebug("completeNowFinalProcessing: " + entityItem1.getKey());
/*     */           
/* 137 */           if (statusIsRFRorFinal(entityItem1)) {
/* 138 */             Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "SVCMODCHRGCOMP", "SVCMOD");
/* 139 */             for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 140 */               EntityItem entityItem2 = vector1.elementAt(b2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 147 */               if (statusIsFinal(entityItem2)) {
/*     */ 
/*     */                 
/* 150 */                 if (statusIsFinal(entityItem) && statusIsFinal(entityItem1))
/*     */                 {
/*     */                   
/* 153 */                   setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem2, "ADSABRSTATUS"), entityItem2);
/*     */ 
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/* 159 */               else if (statusIsRFR(entityItem2)) {
/*     */                 
/* 161 */                 setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem2, "ADSABRSTATUS"), entityItem2);
/*     */               } 
/*     */             } 
/*     */             
/* 165 */             vector1.clear();
/*     */           } 
/*     */         } 
/* 168 */         vector.clear();
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 197 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CVM");
/* 198 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 199 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 200 */       addDebug("completeNowR4RProcessing: " + entityItem.getKey());
/*     */       
/* 202 */       if (statusIsRFRorFinal(entityItem)) {
/* 203 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "CHRGCOMPCVM", "CHRGCOMP");
/* 204 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 205 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 206 */           addDebug("completeNowR4RProcessing: " + entityItem1.getKey());
/*     */           
/* 208 */           if (statusIsRFRorFinal(entityItem1)) {
/* 209 */             Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "SVCMODCHRGCOMP", "SVCMOD");
/* 210 */             for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 211 */               EntityItem entityItem2 = vector1.elementAt(b2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 221 */               if (statusIsRFR(entityItem2))
/*     */               {
/* 223 */                 setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem2, "ADSABRSTATUS"), entityItem2);
/*     */               }
/*     */             } 
/* 226 */             vector1.clear();
/*     */           } 
/*     */         } 
/* 229 */         vector.clear();
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
/* 241 */     return "CVMSPEC ABR.";
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
/* 253 */     return "$Revision: 1.2 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\CVMSPECABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */