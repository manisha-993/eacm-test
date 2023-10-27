/*    */ package COM.ibm.eannounce.darule;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import COM.ibm.opicmpdh.middleware.Database;
/*    */ import COM.ibm.opicmpdh.middleware.Profile;
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
/*    */ public class DARuleEquation
/*    */   extends DARuleItem
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   protected DARuleEquation(EntityItem paramEntityItem) {
/* 32 */     super(paramEntityItem);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getDerivedValue(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws Exception {
/* 41 */     String str = paramString;
/*    */     
/* 43 */     if (isApplicable(paramEntityItem, paramStringBuffer));
/*    */ 
/*    */ 
/*    */     
/* 47 */     return str;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String[] getDerivedValues(Database paramDatabase, Profile paramProfile, EntityItem[] paramArrayOfEntityItem, String[] paramArrayOfString, StringBuffer paramStringBuffer) throws Exception {
/* 56 */     String[] arrayOfString = new String[paramArrayOfEntityItem.length];
/* 57 */     for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/* 58 */       EntityItem entityItem = paramArrayOfEntityItem[b];
/*    */       
/* 60 */       if (!isApplicable(entityItem, paramStringBuffer))
/*    */       {
/*    */ 
/*    */         
/* 64 */         if (paramArrayOfString != null) {
/* 65 */           arrayOfString[b] = paramArrayOfString[b];
/*    */         } else {
/* 67 */           arrayOfString[b] = null;
/*    */         } 
/*    */       }
/*    */     } 
/* 71 */     return arrayOfString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getVersion() {
/* 81 */     return "$Revision: 1.1 $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\DARuleEquation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */