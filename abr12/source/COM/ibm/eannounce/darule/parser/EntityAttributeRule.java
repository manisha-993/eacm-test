/*     */ package COM.ibm.eannounce.darule.parser;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANEntity;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAttributeRule
/*     */   extends AttributeRule
/*     */ {
/*     */   private static final long serialVersionUID = -8665974578750568958L;
/*     */   private String entityType;
/*     */   private String attributeCode;
/*     */   
/*     */   public EntityAttributeRule(String paramString1, String paramString2) {
/*  50 */     this.entityType = paramString1;
/*  51 */     this.attributeCode = paramString2;
/*     */   }
/*     */   
/*     */   public String getEntityType() {
/*  55 */     return this.entityType;
/*     */   }
/*     */   
/*     */   public String getAttributeCode() {
/*  59 */     return this.attributeCode;
/*     */   }
/*     */   
/*     */   public void executeRule(AttributeRuleContext paramAttributeRuleContext) throws Exception {
/*  63 */     EntityItem entityItem = paramAttributeRuleContext.getCurrentEntity();
/*  64 */     ArrayList<EntityItem> arrayList = new ArrayList();
/*  65 */     if (entityItem.getEntityType().equals(this.entityType)) {
/*  66 */       arrayList.add(entityItem);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  71 */     char c = paramAttributeRuleContext.getCurrentDirection();
/*     */     
/*  73 */     if (c == 'd' || c == 'b')
/*     */     {
/*     */       
/*  76 */       for (byte b = 0; b < entityItem.getDownLinkCount(); b++) {
/*  77 */         EANEntity eANEntity = entityItem.getDownLink(b);
/*  78 */         if (eANEntity.getEntityType().equals(this.entityType)) {
/*  79 */           arrayList.add(eANEntity);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  84 */     if (c == 'd' || c == 'b')
/*     */     {
/*     */       
/*  87 */       for (byte b = 0; b < entityItem.getUpLinkCount(); b++) {
/*  88 */         EANEntity eANEntity = entityItem.getUpLink(b);
/*  89 */         if (eANEntity.getEntityType().equals(this.entityType)) {
/*  90 */           arrayList.add(eANEntity);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  95 */     if (arrayList.size() > 0) {
/*  96 */       for (byte b = 0; b < arrayList.size(); b++) {
/*  97 */         EntityItem entityItem1 = arrayList.get(b);
/*  98 */         EANMetaAttribute eANMetaAttribute = entityItem1.getEntityGroup().getMetaAttribute(this.attributeCode);
/*  99 */         if (eANMetaAttribute == null) {
/* 100 */           throw new Exception("Attribute " + this.attributeCode + " NOT found in '" + entityItem1
/* 101 */               .getEntityType() + "' META data.");
/*     */         }
/* 103 */         paramAttributeRuleContext.addResult(PokUtils.getAttributeValue(entityItem1, this.attributeCode, ", ", "", false));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 108 */       String str = "EntityAttributeRule: No entity found from " + entityItem.getKey() + " [" + c + "] to '" + this.entityType + "'";
/*     */ 
/*     */       
/* 111 */       paramAttributeRuleContext.log(str);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\parser\EntityAttributeRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */