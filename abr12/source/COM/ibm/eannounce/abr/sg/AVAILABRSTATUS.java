/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
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
/*     */ public class AVAILABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   protected boolean isVEneeded(String paramString) {
/*  95 */     return "0040".equals(paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 135 */     verifyStatusAndSetABRStatus("OOFAVAIL", "PRODSTRUCT", "PRODSTRUCTABRSTATUS");
/* 136 */     verifyStatusAndSetABRStatus("SWPRODSTRUCTAVAIL", "SWPRODSTRUCT", "SWPRODSTRUCTABRSTATUS");
/* 137 */     verifyStatusAndSetABRStatus("SVCPRODSTRUCTAVAIL", "SVCPRODSTRUCT", "SVCPRODSTRUCTABRSTATUS");
/* 138 */     verifyStatusAndSetABRStatus("MODELAVAIL", "MODEL", "MODELABRSTATUS");
/* 139 */     verifyStatusAndSetABRStatus("LSEOAVAIL", "LSEO", "LSEOABRSTATUS");
/* 140 */     verifyStatusAndSetABRStatus("LSEOBUNDLEAVAIL", "LSEOBUNDLE", "LSEOBDLABRSTATUS");
/*     */     
/* 142 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 143 */     String str = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 144 */     addDebug(entityItem.getKey() + " type " + str);
/*     */     
/* 146 */     if ("146".equals(str) || "149".equals(str)) {
/* 147 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/* 148 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 149 */         EntityItem entityItem1 = entityGroup.getEntityItem(b);
/* 150 */         String str1 = getAttributeFlagEnabledValue(entityItem1, "ANNSTATUS");
/* 151 */         String str2 = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/* 152 */         addDebug(entityItem1.getKey() + " status " + str1 + " type " + str2);
/* 153 */         if (str1 == null || str1.length() == 0) {
/* 154 */           str1 = "0020";
/*     */         }
/*     */ 
/*     */         
/* 158 */         if ("0020".equals(str1)) {
/* 159 */           if ("146".equals(str) && "19".equals(str2)) {
/* 160 */             addDebug(entityItem1.getKey() + " is Final and New");
/* 161 */             setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020", entityItem1);
/*     */ 
/*     */             
/* 164 */             setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem1);
/*     */           } 
/* 166 */           if ("149".equals(str) && "14".equals(str2)) {
/* 167 */             addDebug(entityItem1.getKey() + " is Final and EOL");
/*     */ 
/*     */             
/* 170 */             setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void verifyStatusAndSetABRStatus(String paramString1, String paramString2, String paramString3) {
/* 181 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 182 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, paramString1, paramString2);
/* 183 */     for (byte b = 0; b < vector.size(); b++) {
/* 184 */       EntityItem entityItem1 = vector.elementAt(b);
/* 185 */       String str = getAttributeFlagEnabledValue(entityItem1, "STATUS");
/* 186 */       addDebug(entityItem1.getKey() + " status " + str);
/* 187 */       if (str == null || str.length() == 0) {
/* 188 */         str = "0020";
/*     */       }
/* 190 */       if ("0020".equals(str)) {
/* 191 */         setFlagValue(this.m_elist.getProfile(), paramString3, "0020", entityItem1);
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
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 204 */     if ("0010".equals(paramString) || "0050".equals(paramString)) {
/*     */ 
/*     */       
/* 207 */       addDebug("No checking required, status is draft or change request");
/*     */     }
/* 209 */     else if ("0040".equals(paramString)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       addDebug("Checking LSEOAVAIL countries");
/* 215 */       checkCountry("LSEOAVAIL", "U", false);
/*     */ 
/*     */       
/* 218 */       addDebug("Checking LSEOBUNDLEAVAIL countries");
/* 219 */       checkCountry("LSEOBUNDLEAVAIL", "U", false);
/*     */ 
/*     */       
/* 222 */       checkFeatureCountry();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkFeatureCountry() throws SQLException, MiddlewareException {
/* 233 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */     
/* 235 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/* 237 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 238 */     if (eANFlagAttribute != null) {
/*     */       
/* 240 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 241 */       for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */         
/* 243 */         if (arrayOfMetaFlag[b1].isSelected()) {
/* 244 */           arrayList.add(arrayOfMetaFlag[b1].getFlagCode());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 249 */     addDebug("checkFeatureCountry " + entityItem.getKey() + " countries " + arrayList);
/*     */ 
/*     */     
/* 252 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(this.m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
/* 253 */     for (byte b = 0; b < vector.size(); b++) {
/* 254 */       EntityItem entityItem1 = vector.elementAt(b);
/* 255 */       checkCountry(entityItem, entityItem1, arrayList);
/*     */     } 
/*     */     
/* 258 */     vector.clear();
/*     */     
/* 260 */     arrayList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkCountry(EntityItem paramEntityItem1, EntityItem paramEntityItem2, ArrayList<?> paramArrayList) throws SQLException, MiddlewareException {
/* 266 */     String[] arrayOfString = new String[6];
/* 267 */     EntityGroup entityGroup = paramEntityItem2.getEntityGroup();
/* 268 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/* 270 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem2.getAttribute("COUNTRYLIST");
/* 271 */     if (eANFlagAttribute != null) {
/*     */       
/* 273 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 274 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */         
/* 276 */         if (arrayOfMetaFlag[b].isSelected()) {
/* 277 */           arrayList.add(arrayOfMetaFlag[b].getFlagCode());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 282 */     addDebug("checkCountry " + paramEntityItem2.getKey() + " countries " + arrayList);
/*     */ 
/*     */ 
/*     */     
/* 286 */     boolean bool = arrayList.containsAll(paramArrayList);
/*     */     
/* 288 */     if (!bool) {
/*     */       
/* 290 */       arrayOfString[0] = "";
/* 291 */       arrayOfString[1] = "";
/* 292 */       arrayOfString[2] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 293 */       arrayOfString[3] = entityGroup.getLongDescription();
/* 294 */       arrayOfString[4] = getNavigationName(paramEntityItem2);
/* 295 */       arrayOfString[5] = PokUtils.getAttributeDescription(entityGroup, "COUNTRYLIST", "COUNTRYLIST");
/*     */       
/* 297 */       addError("COUNTRY_MISMATCH_ERR", (Object[])arrayOfString);
/*     */     } 
/*     */     
/* 300 */     arrayList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 310 */     return "AVAIL ABR.";
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
/* 322 */     return "1.13";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AVAILABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */