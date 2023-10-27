/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SharedProductComponents
/*    */   implements Serializable {
/*    */   private boolean SharedProduct = false;
/*  8 */   private String SharedProductMaterialType = null;
/*    */   private boolean SharedProductIn0147 = false;
/*    */   
/*    */   public boolean getSharedProduct() {
/* 12 */     return this.SharedProduct;
/*    */   }
/*    */   
/*    */   public boolean getSharedProductIn0147() {
/* 16 */     return this.SharedProductIn0147;
/*    */   }
/*    */   
/*    */   public String getSharedProductMaterialType() {
/* 20 */     return this.SharedProductMaterialType;
/*    */   }
/*    */   
/*    */   public void setSharedProduct(boolean paramBoolean) {
/* 24 */     this.SharedProduct = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSharedProductMaterailType(String paramString) {
/* 29 */     this.SharedProductMaterialType = paramString;
/*    */   }
/*    */   
/*    */   public void setSharedProductIn0147(boolean paramBoolean) {
/* 33 */     this.SharedProductIn0147 = paramBoolean;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\SharedProductComponents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */