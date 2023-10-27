/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import COM.ibm.opicmpdh.middleware.Database;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*    */ import COM.ibm.opicmpdh.middleware.Profile;
/*    */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*    */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*    */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XLABRQueueUtil
/*    */ {
/*    */   protected static final String ABR_QUEUED = "0020";
/*    */   protected static final String XL_POST_PROCESS_ATTRIBUTE = "XLPOSTPROCABR";
/*    */   
/*    */   public static void queueTranslationPostProcessABR(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, ControlBlock paramControlBlock) throws MiddlewareBusinessRuleException, SQLException, MiddlewareException {
/* 24 */     queueABR(paramDatabase, paramProfile, paramEntityItem, paramControlBlock, "XLPOSTPROCABR");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void queueABR(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, ControlBlock paramControlBlock, String paramString) throws MiddlewareBusinessRuleException, SQLException, MiddlewareException {
/* 30 */     if (paramControlBlock == null) {
/* 31 */       throw new IllegalArgumentException("Control Block was not initialized by ABR. Call setControlBlock();");
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 36 */     ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*    */ 
/*    */     
/* 39 */     SingleFlag singleFlag = new SingleFlag(paramProfile.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, "0020", 1, paramControlBlock);
/*    */ 
/*    */     
/* 42 */     Vector<SingleFlag> vector = new Vector();
/* 43 */     Vector<ReturnEntityKey> vector1 = new Vector();
/* 44 */     if (singleFlag != null) {
/* 45 */       vector.addElement(singleFlag);
/* 46 */       returnEntityKey.m_vctAttributes = vector;
/* 47 */       vector1.addElement(returnEntityKey);
/*    */       
/* 49 */       paramDatabase.update(paramProfile, vector1, false, false);
/* 50 */       paramDatabase.commit();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XLABRQueueUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */