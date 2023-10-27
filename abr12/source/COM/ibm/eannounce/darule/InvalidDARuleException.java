/*    */ package COM.ibm.eannounce.darule;
/*    */ 
/*    */ import java.util.Vector;
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
/*    */ public class InvalidDARuleException
/*    */   extends DARuleException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   private Vector entityItemVct = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidDARuleException(String paramString, Vector paramVector) {
/* 27 */     super(paramString);
/* 28 */     this.entityItemVct = paramVector;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector getEntityItems() {
/* 35 */     return this.entityItemVct;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void dereference() {
/* 41 */     this.entityItemVct.clear();
/* 42 */     this.entityItemVct = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\InvalidDARuleException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */