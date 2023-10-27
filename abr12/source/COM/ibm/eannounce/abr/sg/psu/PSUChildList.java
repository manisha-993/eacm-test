/*    */ package COM.ibm.eannounce.abr.sg.psu;
/*    */ 
/*    */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*    */ import COM.ibm.opicmpdh.transactions.OPICMObject;
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
/*    */ class PSUChildList
/*    */   implements OPICMObject
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String actionName;
/*    */   private OPICMList childrenList;
/*    */   
/*    */   PSUChildList(String paramString) {
/* 30 */     this.actionName = paramString;
/* 31 */     this.childrenList = new OPICMList();
/*    */   }
/*    */   
/*    */   String getActionName() {
/* 35 */     return this.actionName;
/*    */   }
/*    */   
/*    */   OPICMList getChildrenList() {
/* 39 */     return this.childrenList;
/*    */   }
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
/*    */   PSUUpdateData addChild(PSUABRSTATUS paramPSUABRSTATUS, String paramString, int paramInt) {
/* 52 */     PSUUpdateData pSUUpdateData = (PSUUpdateData)this.childrenList.get(paramString + paramInt);
/* 53 */     if (pSUUpdateData == null) {
/* 54 */       pSUUpdateData = new PSUUpdateData(paramPSUABRSTATUS, paramString, paramInt);
/* 55 */       this.childrenList.put(pSUUpdateData);
/*    */     } 
/*    */     
/* 58 */     return pSUUpdateData;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   void dereference() {
/* 64 */     while (this.childrenList.size() > 0) {
/* 65 */       PSUUpdateData pSUUpdateData = (PSUUpdateData)this.childrenList.remove(0);
/* 66 */       pSUUpdateData.dereference();
/*    */     } 
/* 68 */     this.childrenList = null;
/* 69 */     this.actionName = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String hashkey() {
/* 76 */     return this.actionName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 82 */     StringBuffer stringBuffer = new StringBuffer(hashkey());
/* 83 */     for (byte b = 0; b < this.childrenList.size(); b++) {
/* 84 */       PSUUpdateData pSUUpdateData = (PSUUpdateData)this.childrenList.getAt(b);
/* 85 */       stringBuffer.append(":[" + b + "]:" + pSUUpdateData);
/*    */     } 
/* 87 */     return stringBuffer.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\psu\PSUChildList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */