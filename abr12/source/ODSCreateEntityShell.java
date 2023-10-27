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
/*     */ public class ODSCreateEntityShell
/*     */ {
/*     */   public static void main(String[] paramArrayOfString) {
/* 129 */     System.out.println("No. of parameters are :" + paramArrayOfString.length);
/*     */     try {
/* 131 */       if (paramArrayOfString.length != 2) {
/* 132 */         System.err.println("Error!! Command Line Parameters required as : \n       1. Initialize mode - \"ALL\" for all entities, or EntityType for specific one    \n       2. Create Empty Database (NODATA)");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 138 */       System.out.println("----------------Starting ODSCreateEntities (OCE)-------------------");
/* 139 */       ODSCreateEntities oDSCreateEntities = new ODSCreateEntities(paramArrayOfString[0], paramArrayOfString[1]);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 144 */     catch (Exception exception) {
/* 145 */       System.out.println(exception.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\ODSCreateEntityShell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */