/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*    */ import java.io.IOException;
/*    */ import java.rmi.RemoteException;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RfcAbrFactory
/*    */ {
/*    */   private RFCABRSTATUS rfcAbrStatus;
/*    */   
/*    */   public RfcAbr getRfcTypeAbr(String paramString) throws RfcAbrException, MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/* 18 */     if (this.rfcAbrStatus == null)
/* 19 */       throw new RfcAbrException("Did not set RFCABRSTATUS instance to RfcAbrFactory"); 
/* 20 */     if ("MODEL".equals(paramString))
/* 21 */       return new RFCMODELABR(this.rfcAbrStatus); 
/* 22 */     if ("PRODSTRUCT".equals(paramString))
/* 23 */       return new RFCPRODSTRUCTABR(this.rfcAbrStatus); 
/* 24 */     if ("FCTRANSACTION".equals(paramString))
/* 25 */       return new RFCFCTRANSACTIONABR(this.rfcAbrStatus); 
/* 26 */     if ("MODELCONVERT".equals(paramString))
/* 27 */       return new RFCMODELCONVERTABR(this.rfcAbrStatus); 
/* 28 */     if ("AUOMTRL".equals(paramString))
/* 29 */       return new RFCAUOMTRLABR(this.rfcAbrStatus); 
/* 30 */     if ("SWPRODSTRUCT".equals(paramString)) {
/* 31 */       return new RFCSWPRODSTRUCTABR(this.rfcAbrStatus);
/*    */     }
/* 33 */     throw new RfcAbrException("Can not get instance for entity type:" + paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRfcAbrStatus(RFCABRSTATUS paramRFCABRSTATUS) {
/* 38 */     this.rfcAbrStatus = paramRFCABRSTATUS;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RfcAbrFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */