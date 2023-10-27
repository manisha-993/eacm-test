/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import java.io.IOException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class XMLRelatorElem
/*     */   extends XMLElem
/*     */ {
/*     */   private String destinationType;
/*     */   
/*     */   public XMLRelatorElem(String paramString1, String paramString2, String paramString3) {
/*  50 */     super(paramString1, paramString2);
/*  51 */     this.destinationType = paramString3;
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected Node getContentNode(Document paramDocument, EntityItem paramEntityItem, Element paramElement, StringBuffer paramStringBuffer) throws IOException {
/*  66 */     if (this.attrCode == null || paramEntityItem == null) {
/*  67 */       return null;
/*     */     }
/*     */     
/*  70 */     if (this.attrCode.equals("ENTITY1ID")) {
/*     */       
/*  72 */       String str = "@@";
/*  73 */       if (paramEntityItem.hasUpLinks()) {
/*  74 */         for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*  75 */           EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/*  76 */           if (entityItem.getEntityType().equals(this.destinationType)) {
/*  77 */             str = "" + entityItem.getEntityID();
/*  78 */             ABRUtil.append(paramStringBuffer, "XMLRelatorElem getting " + this.attrCode + " from " + entityItem.getKey() + NEWLINE);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*  83 */       return paramDocument.createTextNode(str);
/*     */     } 
/*  85 */     if (this.attrCode.equals("ENTITY1TYPE")) {
/*     */       
/*  87 */       String str = "@@";
/*  88 */       if (paramEntityItem.hasUpLinks()) {
/*  89 */         for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*  90 */           EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/*  91 */           if (entityItem.getEntityType().equals(this.destinationType)) {
/*  92 */             str = "" + entityItem.getEntityType();
/*  93 */             ABRUtil.append(paramStringBuffer, "XMLRelatorElem getting " + this.attrCode + " from " + entityItem.getKey() + NEWLINE);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*  98 */       return paramDocument.createTextNode(str);
/*     */     } 
/* 100 */     if (this.attrCode.equals("ENTITY2ID")) {
/*     */       
/* 102 */       String str = "@@";
/* 103 */       if (paramEntityItem.hasDownLinks()) {
/* 104 */         for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 105 */           EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 106 */           if (entityItem.getEntityType().equals(this.destinationType)) {
/* 107 */             str = "" + entityItem.getEntityID();
/* 108 */             ABRUtil.append(paramStringBuffer, "XMLRelatorElem getting " + this.attrCode + " from " + entityItem.getKey() + NEWLINE);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 113 */       return paramDocument.createTextNode(str);
/*     */     } 
/* 115 */     if (this.attrCode.equals("ENTITY2TYPE")) {
/*     */       
/* 117 */       String str = "@@";
/* 118 */       if (paramEntityItem.hasDownLinks()) {
/* 119 */         for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 120 */           EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 121 */           if (entityItem.getEntityType().equals(this.destinationType)) {
/* 122 */             str = "" + entityItem.getEntityType();
/* 123 */             ABRUtil.append(paramStringBuffer, "XMLRelatorElem getting " + this.attrCode + " from " + entityItem.getKey() + NEWLINE);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 128 */       return paramDocument.createTextNode(str);
/*     */     } 
/*     */     
/* 131 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLRelatorElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */