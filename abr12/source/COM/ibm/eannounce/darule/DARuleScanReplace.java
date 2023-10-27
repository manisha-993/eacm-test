/*     */ package COM.ibm.eannounce.darule;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.StringUtils;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
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
/*     */ public class DARuleScanReplace
/*     */   extends DARuleItem
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   protected DARuleScanReplace(EntityItem paramEntityItem) {
/*  58 */     super(paramEntityItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDerivedValue(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws Exception {
/*  68 */     String str = paramString;
/*     */     
/*  70 */     if (isApplicable(paramEntityItem, paramStringBuffer)) {
/*     */       
/*  72 */       String str1 = PokUtils.getAttributeValue(getDARULEEntity(), "RULEPASS", null, null, false);
/*     */       
/*  74 */       String str2 = PokUtils.getAttributeValue(getDARULEEntity(), "RULEFAIL", null, null, false);
/*     */       
/*  76 */       if (paramString != null) {
/*     */ 
/*     */ 
/*     */         
/*  80 */         str = derive(paramEntityItem, str1, str2, paramString, paramStringBuffer);
/*     */       } else {
/*  82 */         str = null;
/*     */       } 
/*     */     } 
/*  85 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getDerivedValues(Database paramDatabase, Profile paramProfile, EntityItem[] paramArrayOfEntityItem, String[] paramArrayOfString, StringBuffer paramStringBuffer) throws Exception {
/*  95 */     String[] arrayOfString = new String[paramArrayOfEntityItem.length];
/*  96 */     String str1 = PokUtils.getAttributeValue(getDARULEEntity(), "RULEPASS", null, null, false);
/*     */     
/*  98 */     String str2 = PokUtils.getAttributeValue(getDARULEEntity(), "RULEFAIL", null, null, false);
/*     */     
/* 100 */     for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/* 101 */       EntityItem entityItem = paramArrayOfEntityItem[b];
/*     */       
/* 103 */       if (isApplicable(entityItem, paramStringBuffer)) {
/*     */         
/* 105 */         if (paramArrayOfString[b] != null) {
/*     */ 
/*     */ 
/*     */           
/* 109 */           arrayOfString[b] = derive(entityItem, str1, str2, paramArrayOfString[b], paramStringBuffer);
/*     */         } else {
/* 111 */           arrayOfString[b] = null;
/*     */         }
/*     */       
/* 114 */       } else if (paramArrayOfString != null) {
/* 115 */         arrayOfString[b] = paramArrayOfString[b];
/*     */       } else {
/* 117 */         arrayOfString[b] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return arrayOfString;
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
/*     */   private String derive(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3, StringBuffer paramStringBuffer) throws InvalidDARuleException {
/* 134 */     String str = "DARuleScanReplace errors:";
/* 135 */     boolean bool = false;
/* 136 */     if (paramString1 == null) {
/* 137 */       str = str + "<br>\nRule pass (RULEPASS) cannot be null for Scan Replace Rule";
/* 138 */       bool = true;
/*     */     } 
/* 140 */     if (paramString2 == null) {
/* 141 */       str = str + "<br>\nRule fail (RULEFAIL) cannot be null for Scan Replace Rule";
/* 142 */       bool = true;
/*     */     } 
/* 144 */     if (bool) {
/* 145 */       Vector<EntityItem> vector = new Vector();
/* 146 */       vector.add(getDARULEEntity());
/* 147 */       throw new InvalidDARuleException(str, vector);
/*     */     } 
/* 149 */     return StringUtils.replace(paramString3, paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 158 */     return "$Revision: 1.8 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\DARuleScanReplace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */