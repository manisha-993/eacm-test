/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.text.StringCharacterIterator;
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
/*     */ public class CVMABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   private static final String CVMTYPE_Characteristic = "C1";
/*     */   
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  87 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "CHARACID");
/*  88 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "VMID", "", "", false);
/*  89 */     String str3 = PokUtils.getAttributeFlagValue(paramEntityItem, "CVMTYPE");
/*  90 */     addDebug("doDQChecking: " + paramEntityItem.getKey() + " characid: " + str1 + " cvmtype: " + str3 + " vmid: " + str2);
/*  91 */     if (str3 == null) str3 = "";
/*     */ 
/*     */     
/*  94 */     int i = getCount("CVMCVMSPEC");
/*  95 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CVMSPEC");
/*     */     
/*  97 */     if (i == 0) {
/*     */       
/*  99 */       this.args[0] = entityGroup.getLongDescription();
/* 100 */       createMessage(3, "MINIMUM_ERR", this.args);
/*     */     } 
/*     */     
/* 103 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 104 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 105 */       String str4 = PokUtils.getAttributeFlagValue(entityItem, "CVMTYPE");
/* 106 */       String str5 = PokUtils.getAttributeFlagValue(entityItem, "CHARACID");
/* 107 */       addDebug("doDQChecking: " + entityItem.getKey() + " cvmspecCvmtype: " + str4 + " cvmcharacid: " + str5);
/*     */       
/* 109 */       if (str3.equals(str4)) {
/*     */         
/* 111 */         checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 117 */         this.args[0] = "";
/* 118 */         this.args[1] = getLD_Value(paramEntityItem, "CVMTYPE");
/* 119 */         this.args[2] = getLD_NDN(entityItem);
/* 120 */         this.args[3] = getLD_Value(entityItem, "CVMTYPE");
/* 121 */         createMessage(4, "NOT_EQUAL_ERR", this.args);
/*     */       } 
/*     */ 
/*     */       
/* 125 */       if ("C1".equals(str3))
/*     */       {
/*     */         
/* 128 */         if (!str1.equals(str5)) {
/*     */           
/* 130 */           this.args[0] = "";
/* 131 */           this.args[1] = getLD_Value(paramEntityItem, "CHARACID");
/* 132 */           this.args[2] = getLD_NDN(entityItem);
/* 133 */           this.args[3] = getLD_Value(entityItem, "CHARACID");
/* 134 */           createMessage(4, "NOT_EQUAL_ERR", this.args);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (!"C1".equals(str3)) {
/*     */       
/* 143 */       if (!str2.startsWith("V")) {
/*     */         
/* 145 */         this.args[0] = getLD_Value(paramEntityItem, "VMID");
/* 146 */         createMessage(4, "MUST_STARTWITH_ERR", this.args);
/*     */       } 
/*     */ 
/*     */       
/* 150 */       if (str2.length() != 6) {
/*     */         
/* 152 */         this.args[0] = getLD_Value(paramEntityItem, "VMID");
/* 153 */         this.args[1] = "6";
/* 154 */         createMessage(4, "LENGTH_ERR", this.args);
/*     */       } else {
/* 156 */         StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(str2.substring(1));
/* 157 */         char c = stringCharacterIterator.first();
/* 158 */         while (c != Character.MAX_VALUE) {
/* 159 */           if (!Character.isDigit(c)) {
/*     */             
/* 161 */             this.args[0] = getLD_Value(paramEntityItem, "VMID");
/* 162 */             createMessage(4, "INTEGER_ERR", this.args);
/*     */             
/*     */             break;
/*     */           } 
/* 166 */           c = stringCharacterIterator.next();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     oneValidOverTime(paramEntityItem, new String[] { "CVMCVMSPEC", "CVMSPEC" }, 4);
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 202 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CHRGCOMP");
/* 203 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 204 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 205 */       addDebug("completeNowFinalProcessing: " + entityItem.getKey());
/*     */       
/* 207 */       if (statusIsRFRorFinal(entityItem)) {
/* 208 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SVCMODCHRGCOMP", "SVCMOD");
/* 209 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 210 */           EntityItem entityItem1 = vector.elementAt(b1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 217 */           if (statusIsFinal(entityItem1)) {
/*     */             
/* 219 */             if (statusIsFinal(entityItem))
/*     */             {
/*     */               
/* 222 */               setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*     */ 
/*     */             
/*     */             }
/*     */ 
/*     */           
/*     */           }
/* 229 */           else if (statusIsRFR(entityItem1)) {
/*     */             
/* 231 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*     */           } 
/*     */         } 
/*     */         
/* 235 */         vector.clear();
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
/* 261 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CHRGCOMP");
/* 262 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 263 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 264 */       addDebug("completeNowR4RProcessing: " + entityItem.getKey());
/*     */       
/* 266 */       if (statusIsRFRorFinal(entityItem)) {
/* 267 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SVCMODCHRGCOMP", "SVCMOD");
/* 268 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 269 */           EntityItem entityItem1 = vector.elementAt(b1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 277 */           if (statusIsRFR(entityItem1))
/*     */           {
/* 279 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*     */           }
/*     */         } 
/* 282 */         vector.clear();
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
/* 294 */     return "CVM ABR.";
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
/* 306 */     return "$Revision: 1.4 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\CVMABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */