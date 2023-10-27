/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANTextAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
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
/*     */ public class XMLLSEOWWSEOLangElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLLSEOWWSEOLangElem(String paramString1, String paramString2) {
/*  37 */     super(paramString1, paramString2);
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
/*     */   protected boolean hasNodeValueChgForNLS(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  49 */     boolean bool = false;
/*     */ 
/*     */     
/*  52 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  53 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  54 */     NLSItem nLSItem = null;
/*  55 */     if (!paramDiffEntity.isDeleted()) {
/*  56 */       nLSItem = entityItem1.getProfile().getReadLanguage();
/*     */     } else {
/*  58 */       nLSItem = entityItem2.getProfile().getReadLanguage();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  63 */     String str1 = "";
/*  64 */     String str2 = "";
/*  65 */     if (entityItem1 != null) {
/*  66 */       str1 = getValue(entityItem1, paramStringBuffer, nLSItem);
/*     */     }
/*  68 */     if (entityItem2 != null) {
/*  69 */       str2 = getValue(entityItem2, paramStringBuffer, nLSItem);
/*     */     }
/*  71 */     ABRUtil.append(paramStringBuffer, "XMLLSEOWWSEOLangElem.hasNodeValueChgForNLS node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage " + nLSItem + " attr " + this.attrCode + "\n currVal: " + str1 + "\n prevVal: " + str2 + NEWLINE);
/*     */ 
/*     */     
/*  74 */     if (!str1.equals(str2)) {
/*  75 */       bool = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getValue(EntityItem paramEntityItem, StringBuffer paramStringBuffer, NLSItem paramNLSItem) {
/*  92 */     ABRUtil.append(paramStringBuffer, "Entering XMLLSEOWWSEOLangElem.getValue" + NEWLINE);
/*     */     
/*  94 */     EntityItem entityItem1 = null;
/*  95 */     EntityItem entityItem2 = null;
/*     */     
/*  97 */     String str = "";
/*  98 */     if (paramEntityItem.hasUpLinks()) {
/*  99 */       for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 100 */         entityItem1 = (EntityItem)paramEntityItem.getUpLink(b);
/* 101 */         if (entityItem1.getEntityType().equals("WWSEOLSEO")) {
/* 102 */           ABRUtil.append(paramStringBuffer, "XMLLSEOWWSEOLangElem.getValue found WWSEOLSEO " + entityItem1.getKey() + NEWLINE);
/* 103 */           if (entityItem1.hasUpLinks()) {
/* 104 */             for (byte b1 = 0; b1 < entityItem1.getUpLinkCount(); b1++) {
/* 105 */               entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/* 106 */               if (entityItem2.getEntityType().equals("WWSEO")) {
/* 107 */                 ABRUtil.append(paramStringBuffer, "XMLLSEOWWSEOLangElem.getValue found WWSEO " + entityItem2.getKey() + NEWLINE);
/*     */                 
/* 109 */                 EANAttribute eANAttribute = entityItem2.getAttribute(this.attrCode);
/* 110 */                 if (eANAttribute instanceof EANTextAttribute) {
/* 111 */                   int i = paramNLSItem.getNLSID();
/*     */                   
/* 113 */                   if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 114 */                     str = eANAttribute.toString();
/*     */                   }
/*     */                 } 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 126 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLLSEOWWSEOLangElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */