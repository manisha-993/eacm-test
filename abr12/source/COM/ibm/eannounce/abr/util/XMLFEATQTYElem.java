/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
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
/*     */ public class XMLFEATQTYElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLFEATQTYElem(String paramString1, String paramString2) {
/*  50 */     super(paramString1, paramString2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Node getContentNode(Document paramDocument, EntityItem paramEntityItem, Element paramElement, StringBuffer paramStringBuffer) throws IOException {
/*  69 */     ABRUtil.append(paramStringBuffer, "XMLFEATQTYElem.getContentNode entered for: " + paramEntityItem.getKey() + NEWLINE);
/*  70 */     return paramDocument.createTextNode(getQTYValue(paramEntityItem, paramStringBuffer));
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
/*     */   protected boolean hasChanges(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  83 */     boolean bool = false;
/*  84 */     ABRUtil.append(paramStringBuffer, "XMLFEATQTYElem.hasChanges entered for: " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */     
/*  87 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  88 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*     */     
/*  90 */     String str1 = "";
/*  91 */     String str2 = "";
/*  92 */     if (entityItem1 != null) {
/*  93 */       ABRUtil.append(paramStringBuffer, "XMLFEATQTYElem.hasChanges getting currVal" + NEWLINE);
/*  94 */       str1 = getQTYValue(entityItem1, paramStringBuffer);
/*     */     } 
/*     */     
/*  97 */     if (entityItem2 != null) {
/*  98 */       ABRUtil.append(paramStringBuffer, "XMLFEATQTYElem.hasChanges getting prevVal" + NEWLINE);
/*  99 */       str2 = getQTYValue(entityItem2, paramStringBuffer);
/*     */     } 
/*     */     
/* 102 */     ABRUtil.append(paramStringBuffer, "XMLFEATQTYElem.hasChanges node:" + this.nodeName + " " + paramDiffEntity.getKey() + " attr CONFQTY or SWCONFQTY \n currVal: " + str1 + "\n prevVal: " + str2 + NEWLINE);
/*     */ 
/*     */     
/* 105 */     if (!str1.equals(str2)) {
/* 106 */       bool = true;
/*     */     }
/*     */     
/* 109 */     return bool;
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
/*     */   
/*     */   protected String getQTYValue(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 125 */     ABRUtil.append(paramStringBuffer, "Entering XMLFEATQTYElem.getQTYValue" + NEWLINE);
/*     */     
/* 127 */     EntityItem entityItem1 = null;
/* 128 */     EntityItem entityItem2 = null;
/*     */     
/* 130 */     String str = "1";
/* 131 */     if (paramEntityItem.hasDownLinks()) {
/* 132 */       for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 133 */         entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 134 */         if (entityItem1.getEntityType().equals("PRODSTRUCT") || entityItem1
/* 135 */           .getEntityType().equals("SWPRODSTRUCT")) {
/* 136 */           ABRUtil.append(paramStringBuffer, "XMLFEATQTYElem.getQTYValue found PRODSTRUCT or SWPRODSTRUCT " + entityItem1.getKey() + NEWLINE);
/* 137 */           if (entityItem1.hasUpLinks()) {
/* 138 */             for (byte b1 = 0; b1 < entityItem1.getUpLinkCount(); b1++) {
/* 139 */               entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/* 140 */               if (entityItem2.getEntityType().equals("LSEOPRODSTRUCT") || entityItem2
/* 141 */                 .getEntityType().equals("LSEOSWPRODSTRUCT") || entityItem2
/* 142 */                 .getEntityType().equals("WWSEOPRODSTRUCT") || entityItem2
/* 143 */                 .getEntityType().equals("WWSEOSWPRODSTRUCT")) {
/* 144 */                 ABRUtil.append(paramStringBuffer, "XMLFEATQTYElem.getQTYValue found PRODSTRUCT or SWPRODSTRUCT uplink " + entityItem2.getKey() + NEWLINE);
/* 145 */                 if (entityItem1.getEntityType().equals("PRODSTRUCT")) {
/* 146 */                   str = PokUtils.getAttributeValue(entityItem2, this.attrCode, ", ", "1", false);
/*     */                   break;
/*     */                 } 
/* 149 */                 str = PokUtils.getAttributeValue(entityItem2, "SWCONFQTY", ", ", "1", false);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 159 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLFEATQTYElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */