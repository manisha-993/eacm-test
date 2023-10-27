/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ class AITFEEDXMLRelator
/*     */ {
/*     */   private String entit1Type;
/*     */   private String entity1ID;
/*     */   private String entity2ID;
/*     */   private String entity2Type;
/*     */   
/*     */   public AITFEEDXMLRelator() {}
/*     */   
/*     */   public AITFEEDXMLRelator(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 151 */     this.entit1Type = paramString1;
/* 152 */     this.entity1ID = paramString2;
/* 153 */     this.entity2ID = paramString4;
/* 154 */     this.entity2Type = paramString3;
/*     */   }
/*     */   
/*     */   public String getEntity1ID() {
/* 158 */     return this.entity1ID;
/*     */   }
/*     */   
/*     */   public void setEntity1ID(String paramString) {
/* 162 */     this.entity1ID = paramString;
/*     */   }
/*     */   
/*     */   public String getEntity2ID() {
/* 166 */     return this.entity2ID;
/*     */   }
/*     */   
/*     */   public void setEntity2ID(String paramString) {
/* 170 */     this.entity2ID = paramString;
/*     */   }
/*     */   
/*     */   public String getEntit1Type() {
/* 174 */     return this.entit1Type;
/*     */   }
/*     */   
/*     */   public void setEntit1Type(String paramString) {
/* 178 */     this.entit1Type = paramString;
/*     */   }
/*     */   
/*     */   public String getEntity2Type() {
/* 182 */     return this.entity2Type;
/*     */   }
/*     */   
/*     */   public void setEntity2Type(String paramString) {
/* 186 */     this.entity2Type = paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\AITFEEDXMLRelator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */