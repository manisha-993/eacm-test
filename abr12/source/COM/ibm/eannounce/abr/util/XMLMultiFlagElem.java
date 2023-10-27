/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import java.io.IOException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.Text;
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
/*     */ public class XMLMultiFlagElem
/*     */   extends XMLElem
/*     */ {
/*     */   private String actionNodeName;
/*     */   
/*     */   public XMLMultiFlagElem(String paramString1, String paramString2, String paramString3) {
/*  59 */     super(paramString1, paramString2);
/*  60 */     this.actionNodeName = paramString3;
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
/*     */   public XMLMultiFlagElem(String paramString1, String paramString2, String paramString3, int paramInt) {
/*  73 */     super(paramString1, paramString2, paramInt);
/*  74 */     this.actionNodeName = paramString3;
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
/*     */   protected Node getContentNode(Document paramDocument, DiffEntity paramDiffEntity, Element paramElement, StringBuffer paramStringBuffer) throws IOException {
/*  89 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  90 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  91 */     EntityItem entityItem3 = entityItem1;
/*  92 */     if (paramDiffEntity.isDeleted()) {
/*  93 */       entityItem3 = entityItem2;
/*  94 */       entityItem1 = null;
/*     */     } 
/*     */     
/*  97 */     EntityGroup entityGroup = entityItem3.getEntityGroup();
/*  98 */     Text text = null;
/*  99 */     ABRUtil.append(paramStringBuffer, "XMLMultiFlagElem.getContentNode node: " + this.nodeName + " " + paramDiffEntity.getKey() + " attr " + this.attrCode + NEWLINE);
/*     */     
/* 101 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(this.attrCode);
/* 102 */     if (eANMetaAttribute == null) {
/* 103 */       throw new IOException("Error: Attribute " + this.attrCode + " not found in " + paramDiffEntity.getEntityType() + " META data.");
/*     */     }
/*     */     
/* 106 */     if (!eANMetaAttribute.getAttributeType().equals("F")) {
/* 107 */       throw new IOException(this.nodeName + " " + this.attrCode + " is not a MultiFlag attribute in " + paramDiffEntity
/* 108 */           .getEntityType() + " META data");
/*     */     }
/*     */     
/* 111 */     Node node = paramElement.getParentNode();
/* 112 */     HashSet<String> hashSet1 = new HashSet();
/* 113 */     HashSet<String> hashSet2 = new HashSet();
/*     */     
/* 115 */     if (entityItem1 != null) {
/* 116 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(this.attrCode);
/* 117 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 119 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 120 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 122 */           if (arrayOfMetaFlag[b].isSelected())
/*     */           {
/* 124 */             if (this.attrSrc == 1) {
/* 125 */               hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*     */             } else {
/* 127 */               hashSet1.add(arrayOfMetaFlag[b].toString());
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     if (entityItem2 != null) {
/* 135 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(this.attrCode);
/* 136 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 138 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 139 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 141 */           if (arrayOfMetaFlag[b].isSelected())
/*     */           {
/* 143 */             if (this.attrSrc == 1) {
/* 144 */               hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*     */             } else {
/* 146 */               hashSet2.add(arrayOfMetaFlag[b].toString());
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 154 */     if (hashSet2.containsAll(hashSet1) && hashSet1.containsAll(hashSet2)) {
/* 155 */       hashSet1.clear();
/* 156 */       hashSet2.clear();
/* 157 */       return null;
/*     */     } 
/*     */     
/* 160 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*     */     
/* 162 */     Iterator<String> iterator = hashSet1.iterator();
/* 163 */     while (iterator.hasNext()) {
/* 164 */       String str = iterator.next();
/* 165 */       if (!hashSet2.contains(str)) {
/* 166 */         hashtable.put(str, "Update");
/*     */       }
/*     */     } 
/*     */     
/* 170 */     iterator = hashSet2.iterator();
/* 171 */     while (iterator.hasNext()) {
/* 172 */       String str = iterator.next();
/* 173 */       if (!hashSet1.contains(str)) {
/* 174 */         hashtable.put(str, "Delete");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 179 */     iterator = hashtable.keySet().iterator();
/* 180 */     while (iterator.hasNext()) {
/* 181 */       String str1 = iterator.next();
/* 182 */       String str2 = (String)hashtable.get(str1);
/*     */       
/* 184 */       if (iterator.hasNext()) {
/* 185 */         Element element1 = paramDocument.createElement(paramElement.getTagName());
/* 186 */         node.insertBefore(element1, paramElement);
/* 187 */         Element element2 = paramDocument.createElement(this.actionNodeName);
/* 188 */         element1.appendChild(element2);
/* 189 */         element2.appendChild(paramDocument.createTextNode(str2));
/* 190 */         Element element3 = paramDocument.createElement(this.nodeName);
/* 191 */         element1.appendChild(element3);
/* 192 */         element3.appendChild(paramDocument.createTextNode(str1));
/*     */         continue;
/*     */       } 
/* 195 */       Element element = paramDocument.createElement(this.actionNodeName);
/* 196 */       paramElement.insertBefore(element, paramElement.getLastChild());
/* 197 */       element.appendChild(paramDocument.createTextNode(str2));
/* 198 */       text = paramDocument.createTextNode(str1);
/*     */     } 
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
/* 251 */     hashSet1.clear();
/* 252 */     hashSet2.clear();
/* 253 */     hashtable.clear();
/*     */     
/* 255 */     return text;
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
/*     */   protected boolean hasChanges(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 268 */     boolean bool = false;
/*     */     
/* 270 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 271 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 272 */     EntityItem entityItem3 = entityItem1;
/* 273 */     if (paramDiffEntity.isDeleted()) {
/* 274 */       entityItem3 = entityItem2;
/* 275 */       entityItem1 = null;
/*     */     } 
/*     */     
/* 278 */     EntityGroup entityGroup = entityItem3.getEntityGroup();
/* 279 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(this.attrCode);
/* 280 */     if (eANMetaAttribute != null && eANMetaAttribute.getAttributeType().equals("F")) {
/*     */       
/* 282 */       HashSet<String> hashSet1 = new HashSet();
/* 283 */       HashSet<String> hashSet2 = new HashSet();
/*     */       
/* 285 */       if (entityItem1 != null) {
/* 286 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(this.attrCode);
/* 287 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */           
/* 289 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 290 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */             
/* 292 */             if (arrayOfMetaFlag[b].isSelected())
/*     */             {
/* 294 */               if (this.attrSrc == 1) {
/* 295 */                 hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*     */               } else {
/* 297 */                 hashSet1.add(arrayOfMetaFlag[b].toString());
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 304 */       if (entityItem2 != null) {
/* 305 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(this.attrCode);
/* 306 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */           
/* 308 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 309 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */             
/* 311 */             if (arrayOfMetaFlag[b].isSelected())
/*     */             {
/* 313 */               if (this.attrSrc == 1) {
/* 314 */                 hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*     */               } else {
/* 316 */                 hashSet2.add(arrayOfMetaFlag[b].toString());
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 323 */       if (!hashSet2.containsAll(hashSet1) || !hashSet1.containsAll(hashSet2))
/*     */       {
/*     */         
/* 326 */         bool = true;
/*     */       }
/*     */       
/* 329 */       hashSet1.clear();
/* 330 */       hashSet2.clear();
/*     */     } 
/*     */     
/* 333 */     ABRUtil.append(paramStringBuffer, "XMLMultiFlagElem.hasChanges node: " + this.nodeName + " " + paramDiffEntity.getKey() + " attr: " + this.attrCode + " changed: " + bool + NEWLINE);
/*     */     
/* 335 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLMultiFlagElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */