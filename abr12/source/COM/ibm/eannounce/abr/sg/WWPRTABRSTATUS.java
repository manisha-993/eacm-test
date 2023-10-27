/*    */ package COM.ibm.eannounce.abr.sg;
/*    */ 
/*    */ import java.util.Hashtable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WWPRTABRSTATUS
/*    */   extends EPIMSWWPRTBASE
/*    */ {
/* 41 */   private static final Hashtable ABR_TBL = new Hashtable<>();
/*    */   
/*    */   static {
/* 44 */     ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.WWPRTLSEOABR");
/* 45 */     ABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.WWPRTLSEOBUNDLEABR");
/* 46 */     ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.WWPRTPRODSTRUCTABR");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getSimpleABRName() {
/* 53 */     String str = (String)ABR_TBL.get(getEntityType());
/* 54 */     addDebug("creating instance of WWPRTABR  = '" + str + "'");
/* 55 */     return str;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWPRTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */