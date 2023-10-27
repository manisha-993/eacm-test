/*     */ package COM.ibm.eannounce.abr.sg.psu;
/*     */ 
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PSULinkData
/*     */   extends PSUUpdateData
/*     */ {
/*     */   private Hashtable[] childInfoTbls;
/*     */   protected String entityType1;
/*     */   protected int entityId1;
/*  35 */   private OPICMList childrenList = new OPICMList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PSULinkData(PSUABRSTATUS paramPSUABRSTATUS, String paramString, int paramInt) {
/*  44 */     super(paramPSUABRSTATUS);
/*  45 */     this.entityType1 = paramString;
/*  46 */     this.entityId1 = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void dereference() {
/*  53 */     super.dereference();
/*  54 */     this.entityType1 = null;
/*  55 */     while (this.childrenList.size() > 0) {
/*  56 */       PSUChildList pSUChildList = (PSUChildList)this.childrenList.remove(0);
/*  57 */       pSUChildList.dereference();
/*     */     } 
/*  59 */     this.childrenList = null;
/*  60 */     if (this.childInfoTbls != null) {
/*  61 */       for (byte b = 0; b < this.childInfoTbls.length; b++) {
/*  62 */         this.childInfoTbls[b].clear();
/*     */       }
/*  64 */       this.childInfoTbls = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   OPICMList getChildrenList() {
/*  72 */     return this.childrenList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PSUUpdateData addChild(PSUABRSTATUS paramPSUABRSTATUS, String paramString1, int paramInt, String paramString2) {
/*  84 */     PSUChildList pSUChildList = (PSUChildList)this.childrenList.get(paramString2);
/*  85 */     if (pSUChildList == null) {
/*  86 */       pSUChildList = new PSUChildList(paramString2);
/*  87 */       this.childrenList.put(pSUChildList);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  92 */     PSUUpdateData pSUUpdateData = (PSUUpdateData)pSUChildList.getChildrenList().get(paramString1 + paramInt);
/*  93 */     if (pSUUpdateData == null) {
/*  94 */       pSUUpdateData = new PSUUpdateData(paramPSUABRSTATUS, paramString1, paramInt);
/*  95 */       pSUChildList.getChildrenList().put(pSUUpdateData);
/*     */     } 
/*     */     
/*  98 */     return pSUUpdateData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getEntityType() {
/* 105 */     return this.entityType1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int getEntityId() {
/* 111 */     return this.entityId1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void outputUserInfo() {
/* 119 */     int i = 0; byte b;
/* 120 */     for (b = 0; b < this.childrenList.size(); b++) {
/* 121 */       PSUChildList pSUChildList = (PSUChildList)this.childrenList.getAt(b);
/* 122 */       i += pSUChildList.getChildrenList().size();
/*     */     } 
/*     */     
/* 125 */     this.childInfoTbls = new Hashtable[i];
/* 126 */     i = 0;
/*     */     
/* 128 */     for (b = 0; b < this.childrenList.size(); b++) {
/* 129 */       PSUChildList pSUChildList = (PSUChildList)this.childrenList.getAt(b);
/* 130 */       for (byte b1 = 0; b1 < pSUChildList.getChildrenList().size(); b1++) {
/* 131 */         this.infoTbl.put("Update Class", "Reference");
/* 132 */         this.infoTbl.put("Entity Type", getEntityType());
/* 133 */         this.infoTbl.put("Entity ID", "" + getEntityId());
/*     */         
/* 135 */         PSUUpdateData pSUUpdateData = (PSUUpdateData)pSUChildList.getChildrenList().getAt(b1);
/* 136 */         String str1 = pSUUpdateData.getEntityType();
/* 137 */         String str2 = "" + pSUUpdateData.getEntityId();
/*     */ 
/*     */         
/* 140 */         this.infoTbl.put("Entity Type Referenced", str1);
/* 141 */         this.infoTbl.put("Entity ID Referenced", str2);
/* 142 */         this.infoTbl.put("Relator Action", pSUChildList.getActionName());
/* 143 */         pSUUpdateData.fillInAttributeInfo(this.infoTbl);
/*     */         
/* 145 */         this.childInfoTbls[i++] = (Hashtable)this.infoTbl.clone();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void outputUserInfoWithRelator() {
/* 154 */     if (this.childInfoTbls != null) {
/* 155 */       byte b1 = 0;
/* 156 */       for (byte b2 = 0; b2 < this.childrenList.size(); b2++) {
/* 157 */         PSUChildList pSUChildList = (PSUChildList)this.childrenList.getAt(b2);
/* 158 */         for (byte b = 0; b < pSUChildList.getChildrenList().size(); b++) {
/* 159 */           PSUUpdateData pSUUpdateData = (PSUUpdateData)pSUChildList.getChildrenList().getAt(b);
/* 160 */           Hashtable<String, String> hashtable = this.childInfoTbls[b1++];
/* 161 */           if (pSUUpdateData.relatorKey != null) {
/* 162 */             hashtable.put("Relator", pSUUpdateData.relatorKey);
/*     */           }
/* 164 */           this.abr.addUpdateInfo(hashtable);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String hashkey() {
/* 174 */     return this.entityType1 + this.entityId1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 180 */     StringBuffer stringBuffer = new StringBuffer(super.toString());
/* 181 */     for (byte b = 0; b < this.childrenList.size(); b++) {
/* 182 */       PSUChildList pSUChildList = (PSUChildList)this.childrenList.getAt(b);
/* 183 */       stringBuffer.append(":[" + b + "]:" + pSUChildList);
/*     */     } 
/* 185 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\psu\PSULinkData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */