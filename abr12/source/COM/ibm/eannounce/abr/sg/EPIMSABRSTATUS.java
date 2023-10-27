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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EPIMSABRSTATUS
/*    */   extends EPIMSWWPRTBASE
/*    */ {
/* 48 */   private static final Hashtable ABR_TBL = new Hashtable<>(); static {
/* 49 */     ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.EPIMSLSEOABR");
/* 50 */     ABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.EPIMSLSEOBUNDLEABR");
/* 51 */     ABR_TBL.put("WWSEO", "COM.ibm.eannounce.abr.sg.EPIMSWWSEOABR");
/* 52 */     ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.EPIMSPRODSTRUCTABR");
/* 53 */     ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.EPIMSSWPRODSTRUCTABR");
/* 54 */     ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.EPIMSMODELABR");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getSimpleABRName() {
/* 61 */     String str = (String)ABR_TBL.get(getEntityType());
/* 62 */     addDebug("creating instance of EPIMSABR  = '" + str + "'");
/* 63 */     return str;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\EPIMSABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */