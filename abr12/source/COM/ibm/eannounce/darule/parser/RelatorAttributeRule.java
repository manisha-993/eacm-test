/*    */ package COM.ibm.eannounce.darule.parser;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EANEntity;
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import COM.ibm.opicmpdh.middleware.D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RelatorAttributeRule
/*    */   extends AttributeRule
/*    */ {
/*    */   private static final long serialVersionUID = 1676051960908659011L;
/*    */   private String relatorName;
/*    */   private char direction;
/*    */   
/*    */   public RelatorAttributeRule(String paramString, char paramChar) {
/* 36 */     this.relatorName = paramString;
/* 37 */     this.direction = paramChar;
/*    */   }
/*    */   
/*    */   public void executeRule(AttributeRuleContext paramAttributeRuleContext) throws Exception {
/* 41 */     EntityItem entityItem = paramAttributeRuleContext.getCurrentEntity();
/*    */     
/* 43 */     char c = paramAttributeRuleContext.getCurrentDirection();
/*    */     
/* 45 */     paramAttributeRuleContext.setCurrentDirection(this.direction);
/* 46 */     boolean bool = false;
/* 47 */     if (c == 'd' || c == 'b') {
/*    */       
/* 49 */       for (byte b = 0; b < entityItem.getDownLinkCount(); b++) {
/* 50 */         EANEntity eANEntity = entityItem.getDownLink(b);
/* 51 */         if (eANEntity.getEntityType().equals(this.relatorName)) {
/* 52 */           bool = true;
/* 53 */           D.ebug(4, "RelatorAttributeRule: going from " + entityItem.getKey() + " down to " + eANEntity
/* 54 */               .getKey());
/*    */           
/* 56 */           paramAttributeRuleContext.setCurrentEntity((EntityItem)eANEntity);
/* 57 */           executeNextRule(paramAttributeRuleContext);
/*    */         } 
/*    */       } 
/* 60 */     } else if (c == 'u' || c == 'b') {
/*    */       
/* 62 */       for (byte b = 0; b < entityItem.getUpLinkCount(); b++) {
/* 63 */         EANEntity eANEntity = entityItem.getUpLink(b);
/* 64 */         if (eANEntity.getEntityType().equals(this.relatorName)) {
/* 65 */           bool = true;
/* 66 */           D.ebug(4, "RelatorAttributeRule: going from " + entityItem.getKey() + " up to " + eANEntity
/* 67 */               .getKey());
/*    */           
/* 69 */           paramAttributeRuleContext.setCurrentEntity((EntityItem)eANEntity);
/* 70 */           executeNextRule(paramAttributeRuleContext);
/*    */         } 
/*    */       } 
/*    */     } 
/* 74 */     if (!bool) {
/*    */       
/* 76 */       String str = "RelatorAttributeRule: No link found from " + entityItem.getKey() + " [" + c + "] to '" + this.relatorName + "'";
/*    */ 
/*    */       
/* 79 */       paramAttributeRuleContext.log(str);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\parser\RelatorAttributeRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */