/*     */ package COM.ibm.eannounce.abr.sg.psu;
/*     */ 
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.objects.Attribute;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.MultipleFlag;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMObject;
/*     */ import java.util.Hashtable;
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
/*     */ class PSUUpdateData
/*     */   implements OPICMObject
/*     */ {
/*     */   protected ReturnEntityKey rek;
/*     */   protected PSUABRSTATUS abr;
/*  31 */   protected Hashtable infoTbl = new Hashtable<>();
/*  32 */   protected String relatorKey = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector removeAttrVct;
/*     */ 
/*     */ 
/*     */   
/*     */   PSUUpdateData(PSUABRSTATUS paramPSUABRSTATUS) {
/*  41 */     this.abr = paramPSUABRSTATUS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PSUUpdateData(PSUABRSTATUS paramPSUABRSTATUS, String paramString, int paramInt) {
/*  50 */     this.rek = new ReturnEntityKey(paramString, paramInt, true);
/*  51 */     this.abr = paramPSUABRSTATUS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getHighEntityId() {
/*  59 */     return getEntityId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getEntityType() {
/*  66 */     return this.rek.m_strEntityType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getEntityId() {
/*  73 */     return this.rek.m_iEntityID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addRemoveAttr(Attribute paramAttribute) {
/*  81 */     if (this.removeAttrVct == null) {
/*  82 */       this.removeAttrVct = new Vector();
/*     */     }
/*  84 */     this.removeAttrVct.add(paramAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeAttrs() {
/*  91 */     if (this.removeAttrVct != null && this.rek != null) {
/*  92 */       this.abr.addDebug(3, "PSUUpdateData.removeAttrs: removing " + this.removeAttrVct);
/*  93 */       this.rek.m_vctAttributes.removeAll(this.removeAttrVct);
/*  94 */       this.removeAttrVct.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void dereference() {
/* 101 */     if (this.rek != null) {
/* 102 */       this.rek.m_strEntityType = null;
/* 103 */       if (this.rek.m_vctAttributes != null) {
/* 104 */         this.rek.m_vctAttributes.clear();
/* 105 */         this.rek.m_vctAttributes = null;
/*     */       } 
/* 107 */       this.rek = null;
/*     */     } 
/* 109 */     if (this.removeAttrVct != null) {
/* 110 */       this.removeAttrVct.clear();
/* 111 */       this.removeAttrVct = null;
/*     */     } 
/* 113 */     this.abr = null;
/* 114 */     this.infoTbl.clear();
/* 115 */     this.infoTbl = null;
/* 116 */     this.relatorKey = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setRelatorKey(String paramString) {
/* 124 */     this.relatorKey = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setUniqueFlagValue(String paramString1, String paramString2, ControlBlock paramControlBlock) {
/* 134 */     this.abr.addDebug(4, "PSUUpdateData.setUniqueFlagValue " + this.rek.hashkey() + " " + paramString1 + " set to: " + paramString2 + " cb.valto: " + paramControlBlock
/* 135 */         .getValTo());
/*     */     
/* 137 */     if (!isAttrAdded(paramString1, paramString2)) {
/* 138 */       SingleFlag singleFlag = new SingleFlag(this.abr.getProfile().getEnterprise(), this.rek.getEntityType(), this.rek.getEntityID(), paramString1, paramString2, 1, paramControlBlock);
/*     */       
/* 140 */       this.rek.m_vctAttributes.addElement(singleFlag);
/*     */     } else {
/* 142 */       this.abr.addDebug("PSUUpdateData.setUniqueFlagValue: " + this.rek.hashkey() + " " + paramString1 + " attrvalue:" + paramString2 + " was already added for updates ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTextValue(String paramString1, String paramString2, ControlBlock paramControlBlock) {
/* 154 */     this.abr.addDebug(4, "PSUUpdateData.setTextValue " + this.rek.hashkey() + " " + paramString1 + " set to: " + paramString2 + " cb.valto: " + paramControlBlock
/* 155 */         .getValTo());
/*     */     
/* 157 */     if (!isAttrAdded(paramString1, paramString2)) {
/*     */       
/* 159 */       Text text = new Text(this.abr.getProfile().getEnterprise(), this.rek.getEntityType(), this.rek.getEntityID(), paramString1, paramString2, 1, paramControlBlock);
/* 160 */       this.rek.m_vctAttributes.addElement(text);
/*     */     } else {
/* 162 */       this.abr.addDebug("PSUUpdateData.setTextValue: " + this.rek.hashkey() + " " + paramString1 + " attrvalue:" + paramString2 + " was already added for updates ");
/*     */     } 
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
/*     */   void setMultiFlagValue(String paramString1, String paramString2, ControlBlock paramControlBlock) {
/* 175 */     this.abr.addDebug(4, "PSUUpdateData.setMultiFlagValue " + this.rek.hashkey() + " " + paramString1 + " set to: " + paramString2 + " cb.valto: " + paramControlBlock
/* 176 */         .getValTo());
/* 177 */     if (!isAttrAdded(paramString1, paramString2)) {
/*     */       
/* 179 */       MultipleFlag multipleFlag = new MultipleFlag(this.abr.getProfile().getEnterprise(), this.rek.getEntityType(), this.rek.getEntityID(), paramString1, paramString2, 1, paramControlBlock);
/* 180 */       this.rek.m_vctAttributes.addElement(multipleFlag);
/*     */     } else {
/* 182 */       this.abr.addDebug("PSUUpdateData.setMultiFlagValue: " + this.rek.hashkey() + " " + paramString1 + " attrvalue:" + paramString2 + " was already added for updates ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAttrAdded(String paramString1, String paramString2) {
/* 194 */     if (this.rek.m_vctAttributes == null) {
/* 195 */       this.rek.m_vctAttributes = new Vector();
/*     */     }
/*     */     
/* 198 */     boolean bool = false;
/*     */     
/* 200 */     for (byte b = 0; b < this.rek.m_vctAttributes.size(); b++) {
/* 201 */       Attribute attribute = this.rek.m_vctAttributes.elementAt(b);
/* 202 */       if (attribute.getAttributeCode().equals(paramString1) && attribute
/* 203 */         .getAttributeValue().equals(paramString2)) {
/* 204 */         bool = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 208 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String hashkey() {
/* 216 */     return this.rek.hashkey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void outputUserInfo() {
/* 223 */     this.infoTbl.put("Update Class", "Update");
/* 224 */     this.infoTbl.put("Entity Type", getEntityType());
/* 225 */     this.infoTbl.put("Entity ID", "" + getEntityId());
/*     */     
/* 227 */     fillInAttributeInfo(this.infoTbl);
/*     */     
/* 229 */     this.abr.addUpdateInfo(this.infoTbl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillInAttributeInfo(Hashtable<String, String> paramHashtable) {
/* 237 */     if (this.rek != null && this.rek.m_vctAttributes != null) {
/*     */       
/* 239 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 240 */       StringBuffer stringBuffer2 = new StringBuffer();
/* 241 */       StringBuffer stringBuffer3 = new StringBuffer();
/* 242 */       StringBuffer stringBuffer4 = new StringBuffer();
/* 243 */       for (byte b = 0; b < this.rek.m_vctAttributes.size(); b++) {
/* 244 */         Attribute attribute = this.rek.m_vctAttributes.elementAt(b);
/*     */         
/* 246 */         String str1 = this.abr.getAttrType(attribute.getAttributeCode());
/* 247 */         String str2 = this.abr.getAttrAction(attribute.m_cbControlBlock);
/* 248 */         if (stringBuffer1.length() > 0) {
/* 249 */           stringBuffer1.append("<br />");
/* 250 */           stringBuffer2.append("<br />");
/* 251 */           stringBuffer4.append("<br />");
/* 252 */           stringBuffer3.append("<br />");
/*     */         } 
/* 254 */         stringBuffer1.append(attribute.getAttributeCode());
/* 255 */         stringBuffer2.append(attribute.getAttributeValue());
/* 256 */         stringBuffer4.append(str2);
/* 257 */         stringBuffer3.append(str1);
/*     */       } 
/* 259 */       paramHashtable.put("Update Attribute Code", stringBuffer1.toString());
/* 260 */       paramHashtable.put("Attribute Type", stringBuffer3.toString());
/* 261 */       paramHashtable.put("Attribute Update Action", stringBuffer4.toString());
/* 262 */       paramHashtable.put("Update Attribute Value", stringBuffer2.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 269 */     StringBuffer stringBuffer = new StringBuffer(hashkey());
/* 270 */     if (this.rek != null && this.rek.m_vctAttributes != null) {
/* 271 */       for (byte b = 0; b < this.rek.m_vctAttributes.size(); b++) {
/* 272 */         Attribute attribute = this.rek.m_vctAttributes.elementAt(b);
/* 273 */         stringBuffer.append(":" + attribute.getAttributeCode() + " " + attribute.getAttributeValue());
/*     */       } 
/*     */     }
/* 276 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\psu\PSUUpdateData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */