/*    */ package COM.ibm.eannounce.abr.sg.translation;
/*    */ 
/*    */ import COM.ibm.opicmpdh.middleware.D;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*    */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
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
/*    */ public abstract class RelatorHandler
/*    */ {
/*    */   private String entityFromType;
/*    */   private EntityHandler parentEntity;
/*    */   
/*    */   public RelatorHandler(String paramString) {
/* 22 */     this.entityFromType = paramString;
/*    */   }
/*    */   
/*    */   public abstract EntityHandler createParentEntity() throws MiddlewareException;
/*    */   
/*    */   public ReturnStatus addChild(String paramString, int paramInt) throws MiddlewareException {
/* 28 */     ReturnStatus returnStatus = new ReturnStatus();
/*    */     try {
/* 30 */       if (this.parentEntity == null) {
/* 31 */         this.parentEntity = createParentEntity();
/*    */       }
/* 33 */       String str = this.parentEntity.getEntityType() + paramString;
/* 34 */       D.ebug("RelatorHandler: Creating relator [" + str + "] from " + this.parentEntity
/* 35 */           .getEntityType() + " to " + paramString);
/*    */       
/* 37 */       this.parentEntity.addRelator(str, paramString, paramInt);
/*    */     }
/* 39 */     catch (MiddlewareException middlewareException) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 45 */       D.ebug("RelatorHandler: [MiddlewareException] " + middlewareException.getMessage());
/*    */       
/* 47 */       middlewareException.printStackTrace();
/*    */       
/* 49 */       throw middlewareException;
/*    */     } 
/* 51 */     return returnStatus;
/*    */   }
/*    */   
/*    */   public String getEntityFromType() {
/* 55 */     return this.entityFromType;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\translation\RelatorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */