/*     */ package COM.ibm.eannounce.darule;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DARuleItem
/*     */   implements Comparable, Serializable
/*     */ {
/*  60 */   private EntityItem daRuleItem = null;
/*  61 */   private Set validDomainSet = new HashSet();
/*     */ 
/*     */   
/*     */   public static final String VERSION = "$Revision: 1.2 $";
/*     */ 
/*     */ 
/*     */   
/*     */   protected DARuleItem(EntityItem paramEntityItem) {
/*  69 */     this.daRuleItem = paramEntityItem;
/*     */ 
/*     */     
/*  72 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)this.daRuleItem.getAttribute("PDHDOMAIN");
/*  73 */     MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  74 */     for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */       
/*  76 */       if (arrayOfMetaFlag[b].isSelected() && !this.validDomainSet.contains(arrayOfMetaFlag[b].getFlagCode())) {
/*  77 */         this.validDomainSet.add(arrayOfMetaFlag[b].getFlagCode());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getKey() {
/*  87 */     return this.daRuleItem.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityItem getDARULEEntity() {
/*  93 */     return this.daRuleItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object paramObject) {
/* 100 */     String str1 = PokUtils.getAttributeValue(this.daRuleItem, "RULESEQ", "", "", false);
/* 101 */     String str2 = PokUtils.getAttributeValue(((DARuleItem)paramObject).daRuleItem, "RULESEQ", "", "", false);
/* 102 */     return str1.compareTo(str2);
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
/*     */   protected abstract String getDerivedValue(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String[] getDerivedValues(Database paramDatabase, Profile paramProfile, EntityItem[] paramArrayOfEntityItem, String[] paramArrayOfString, StringBuffer paramStringBuffer) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isApplicable(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 141 */     boolean bool = false;
/* 142 */     if (PokUtils.contains(paramEntityItem, "PDHDOMAIN", this.validDomainSet)) {
/* 143 */       bool = true;
/*     */     } else {
/* 145 */       DARuleEngineMgr.addDebugComment(2, paramStringBuffer, "DARULE PDHDOMAIN: " + this.validDomainSet + " did not have " + paramEntityItem
/* 146 */           .getKey() + " pdhdomain: " + PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN"));
/*     */     } 
/* 148 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dereference() {
/* 155 */     this.daRuleItem = null;
/* 156 */     this.validDomainSet.clear();
/* 157 */     this.validDomainSet = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 164 */     StringBuffer stringBuffer = new StringBuffer(this.daRuleItem.getKey() + " DOMAIN: " + this.validDomainSet);
/* 165 */     for (byte b = 0; b < this.daRuleItem.getAttributeCount(); b++) {
/* 166 */       EANAttribute eANAttribute = this.daRuleItem.getAttribute(b);
/* 167 */       stringBuffer.append(" " + eANAttribute.getAttributeCode() + ": " + 
/* 168 */           PokUtils.getAttributeValue(this.daRuleItem, eANAttribute.getAttributeCode(), ",", "", false));
/*     */     } 
/*     */     
/* 171 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\DARuleItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */