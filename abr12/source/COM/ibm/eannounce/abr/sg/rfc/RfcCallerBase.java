/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ 
/*    */ public class RfcCallerBase
/*    */ {
/*  6 */   public StringBuffer rptSb = new StringBuffer();
/*  7 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  8 */   private int abr_debuglvl = 0;
/*  9 */   static final String NEWLINE = new String(FOOL_JTEST);
/*    */   
/*    */   public StringBuffer getRptSb() {
/* 12 */     return this.rptSb;
/*    */   }
/*    */   private boolean isdebug = true;
/*    */   protected void addDebug(String paramString) {
/* 16 */     if (3 <= this.abr_debuglvl || this.isdebug)
/*    */     {
/* 18 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void addOutput(String paramString) {
/* 23 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*    */   }
/*    */   
/*    */   protected void addMsg(StringBuffer paramStringBuffer) {
/* 27 */     this.rptSb.append(paramStringBuffer.toString() + NEWLINE);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addRfcName(RdhBase paramRdhBase) {
/* 32 */     addDebug("Calling " + paramRdhBase.getRFCName());
/*    */   }
/*    */   
/*    */   protected void addRfcResult(RdhBase paramRdhBase) {
/* 36 */     addDebug(paramRdhBase.createLogEntry());
/* 37 */     if (paramRdhBase.getRfcrc() == 0) {
/* 38 */       addOutput(paramRdhBase.getRFCName() + " called successfully!");
/*    */     } else {
/* 40 */       addOutput(paramRdhBase.getRFCName() + " called  faild!");
/* 41 */       addOutput("return code is " + paramRdhBase.getRfcrc());
/* 42 */       addOutput(paramRdhBase.getError_text());
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void runRfcCaller(RdhBase paramRdhBase) throws Exception {
/* 47 */     addDebug("Calling " + paramRdhBase.getRFCName());
/* 48 */     paramRdhBase.execute();
/* 49 */     addDebug(paramRdhBase.createLogEntry());
/* 50 */     if (paramRdhBase.getRfcrc() == 0) {
/* 51 */       addOutput(paramRdhBase.getRFCName() + " called successfully!");
/*    */     } else {
/* 53 */       addOutput(paramRdhBase.getRFCName() + " called  faild!");
/* 54 */       addOutput(paramRdhBase.getError_text());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RfcCallerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */