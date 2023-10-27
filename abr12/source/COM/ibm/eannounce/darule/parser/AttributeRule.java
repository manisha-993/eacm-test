/*    */ package COM.ibm.eannounce.darule.parser;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AttributeRule
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4496111977016801915L;
/*    */   private String key;
/*    */   private AttributeRule nextRule;
/*    */   
/*    */   protected void executeNextRule(AttributeRuleContext paramAttributeRuleContext) throws Exception {
/* 39 */     if (this.nextRule != null) {
/* 40 */       this.nextRule.executeRule(paramAttributeRuleContext);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setNextRule(AttributeRule paramAttributeRule) {
/* 45 */     this.nextRule = paramAttributeRule;
/*    */   }
/*    */   
/*    */   public abstract void executeRule(AttributeRuleContext paramAttributeRuleContext) throws Exception;
/*    */   
/*    */   public String getKey() {
/* 51 */     return this.key;
/*    */   }
/*    */   
/*    */   public void setKey(String paramString) {
/* 55 */     this.key = paramString;
/*    */   }
/*    */   
/*    */   public AttributeRule getLastRule() {
/* 59 */     if (this.nextRule != null) {
/* 60 */       return this.nextRule.getLastRule();
/*    */     }
/* 62 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\parser\AttributeRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */