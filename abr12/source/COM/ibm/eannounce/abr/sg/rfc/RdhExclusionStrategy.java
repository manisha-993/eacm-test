/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import com.google.gson.ExclusionStrategy;
/*    */ import com.google.gson.FieldAttributes;
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
/*    */ public class RdhExclusionStrategy
/*    */   implements ExclusionStrategy
/*    */ {
/*    */   public boolean shouldSkipClass(Class<?> paramClass) {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldSkipField(FieldAttributes paramFieldAttributes) {
/* 26 */     return (paramFieldAttributes.getAnnotation(Foo.class) != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhExclusionStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */