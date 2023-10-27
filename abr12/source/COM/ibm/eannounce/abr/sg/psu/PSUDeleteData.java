/*     */ package COM.ibm.eannounce.abr.sg.psu;
/*     */ 
/*     */ import java.util.Vector;
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
/*     */ class PSUDeleteData
/*     */   extends PSUUpdateData
/*     */ {
/*     */   protected String actionName;
/*     */   protected String deleteType;
/*  30 */   protected Vector idVct = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PSUDeleteData(PSUABRSTATUS paramPSUABRSTATUS, String paramString) {
/*  37 */     super(paramPSUABRSTATUS);
/*  38 */     this.deleteType = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getEntityType() {
/*  45 */     return this.deleteType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int getHighEntityId() {
/*  51 */     return ((Integer)this.idVct.lastElement()).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getEntityId() {
/*  58 */     return ((Integer)this.idVct.firstElement()).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setActionName(String paramString) {
/*  64 */     this.actionName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addDeleteId(int paramInt) {
/*  71 */     this.idVct.add(new Integer(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void outputUserInfo() {
/*  79 */     for (byte b = 0; b < this.idVct.size(); b++) {
/*  80 */       this.infoTbl.put("Update Class", "Reference");
/*  81 */       this.infoTbl.put("Entity Type", getEntityType());
/*  82 */       this.infoTbl.put("Entity ID", this.idVct.elementAt(b).toString());
/*  83 */       this.infoTbl.put("Attribute Update Action", "D");
/*  84 */       this.infoTbl.put("Relator Action", this.actionName);
/*  85 */       this.abr.addUpdateInfo(this.infoTbl);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void dereference() {
/*  92 */     super.dereference();
/*  93 */     this.deleteType = null;
/*  94 */     this.idVct.clear();
/*  95 */     this.idVct = null;
/*  96 */     this.actionName = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String hashkey() {
/* 103 */     return this.deleteType + this.idVct.firstElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 109 */     return this.deleteType + ":" + this.idVct;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\psu\PSUDeleteData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */