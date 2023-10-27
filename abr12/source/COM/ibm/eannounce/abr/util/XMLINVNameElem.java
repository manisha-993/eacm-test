/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EANTextAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import java.io.IOException;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
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
/*     */ public class XMLINVNameElem
/*     */   extends XMLElem
/*     */ {
/*     */   private String destinationPath;
/*     */   private String att2code;
/*     */   
/*     */   public XMLINVNameElem(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  41 */     super(paramString1, paramString2);
/*  42 */     this.destinationPath = paramString4;
/*  43 */     this.att2code = paramString3;
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
/*  58 */     if (this.attrCode == null || paramEntityItem == null) {
/*  59 */       return null;
/*     */     }
/*  61 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*  62 */     EntityList entityList = entityGroup.getEntityList();
/*     */     
/*  64 */     String str = "@@";
/*  65 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(this.attrCode);
/*  66 */     if (eANMetaAttribute == null) {
/*  67 */       if (this.isReq) {
/*  68 */         throw new IOException(this.nodeName + " is required but " + this.attrCode + " is not in " + paramEntityItem.getEntityType() + " META data");
/*     */       }
/*     */ 
/*     */       
/*  72 */       str = "Error: Attribute " + this.attrCode + " not found in " + paramEntityItem.getEntityType() + " META data.";
/*     */     } else {
/*  74 */       Profile profile = entityList.getProfile();
/*  75 */       NLSItem nLSItem = profile.getReadLanguage();
/*  76 */       int i = nLSItem.getNLSID();
/*     */ 
/*     */       
/*  79 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(this.attrCode);
/*  80 */       if (eANAttribute instanceof EANTextAttribute)
/*     */       {
/*     */         
/*  83 */         if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/*  84 */           if (eANAttribute != null && eANAttribute.toString().length() > 0) {
/*  85 */             str = eANAttribute.toString();
/*  86 */             if (eANMetaAttribute.getAttributeType().equals("T") && 
/*  87 */               str.length() > getTextLimit()) {
/*  88 */               str = str.substring(0, getTextLimit());
/*  89 */               ABRUtil.append(paramStringBuffer, "XMLElem.getContentNode node:" + this.nodeName + " " + paramEntityItem.getKey() + " value was truncated for attr " + this.attrCode + NEWLINE);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/*  96 */           str = "@@";
/*     */         } 
/*     */       }
/*  99 */       if ("@@".equals(str)) {
/* 100 */         Vector<EntityItem> vector = new Vector();
/*     */         
/* 102 */         if (this.destinationPath != null && paramEntityItem != null) {
/* 103 */           EntityItem entityItem = paramEntityItem;
/* 104 */           Vector<EntityItem> vector1 = new Vector(1);
/* 105 */           Vector<EntityItem> vector2 = new Vector(1);
/* 106 */           vector2.add(entityItem);
/* 107 */           StringTokenizer stringTokenizer = new StringTokenizer(this.destinationPath, ":");
/* 108 */           while (stringTokenizer.hasMoreTokens()) {
/* 109 */             String str1 = stringTokenizer.nextToken();
/* 110 */             String str2 = null;
/* 111 */             if (stringTokenizer.hasMoreTokens()) {
/* 112 */               str2 = stringTokenizer.nextToken();
/*     */             }
/* 114 */             ABRUtil.append(paramStringBuffer, "XMLINVNameElem: node:" + this.nodeName + " attrbutecode:" + this.att2code + " path:" + this.destinationPath + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */             
/* 117 */             Vector<EntityItem> vector3 = new Vector();
/* 118 */             for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 119 */               EntityItem entityItem1 = vector2.elementAt(b1);
/* 120 */               ABRUtil.append(paramStringBuffer, "XMLINVNameElem: loop pitem " + entityItem1.getKey() + NEWLINE);
/* 121 */               Vector<EntityItem> vector4 = null;
/* 122 */               if (str1.equals("D")) {
/* 123 */                 vector4 = entityItem1.getDownLink();
/*     */               } else {
/* 125 */                 vector4 = entityItem1.getUpLink();
/*     */               } 
/* 127 */               for (byte b2 = 0; b2 < vector4.size(); b2++) {
/* 128 */                 EntityItem entityItem2 = vector4.elementAt(b2);
/* 129 */                 ABRUtil.append(paramStringBuffer, "XMLINVNameElem: linkloop entity " + entityItem2.getKey() + NEWLINE);
/* 130 */                 if (entityItem2.getEntityType().equals(str2)) {
/* 131 */                   if (stringTokenizer.hasMoreTokens()) {
/*     */                     
/* 133 */                     vector3.add(entityItem2);
/*     */                   } else {
/* 135 */                     vector1.add(entityItem2);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/* 140 */             vector2 = vector3;
/*     */           } 
/*     */           
/* 143 */           vector = vector1;
/*     */         } 
/* 145 */         if (vector.size() == 0) {
/* 146 */           ABRUtil.append(paramStringBuffer, "XMLINVNameElem: node:" + this.nodeName + " No entities found for " + this.destinationPath + NEWLINE);
/*     */ 
/*     */           
/* 149 */           return null;
/*     */         } 
/* 151 */         for (byte b = 0; b < vector.size(); b++) {
/* 152 */           EntityItem entityItem = vector.elementAt(b);
/* 153 */           EntityGroup entityGroup1 = entityItem.getEntityGroup();
/* 154 */           EANMetaAttribute eANMetaAttribute1 = entityGroup1.getMetaAttribute(this.att2code);
/* 155 */           if (eANMetaAttribute1 != null) {
/* 156 */             EANAttribute eANAttribute1 = entityItem.getAttribute(this.att2code);
/* 157 */             if (eANAttribute1 instanceof EANTextAttribute)
/*     */             {
/*     */               
/* 160 */               if (((EANTextAttribute)eANAttribute1).containsNLS(i)) {
/* 161 */                 if (eANAttribute1 != null && eANAttribute1.toString().length() > 0) {
/* 162 */                   str = eANAttribute1.toString();
/* 163 */                   if (eANMetaAttribute1.getAttributeType().equals("T") && 
/* 164 */                     str.length() > getTextLimit()) {
/* 165 */                     str = str.substring(0, getTextLimit());
/* 166 */                     ABRUtil.append(paramStringBuffer, "XMLElem.getContentNode node:" + this.nodeName + " " + entityItem.getKey() + " value was truncated for attr " + this.att2code + NEWLINE);
/*     */                   }
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/*     */               else {
/*     */                 
/* 174 */                 str = "@@";
/*     */               } 
/*     */             }
/*     */           } else {
/*     */             
/* 179 */             str = "Error: Attribute " + this.att2code + " not found in " + entityItem.getEntityType() + " META data.";
/*     */           } 
/*     */         } 
/* 182 */         vector.clear();
/*     */       } 
/*     */       
/* 185 */       return paramDocument.createTextNode(str);
/*     */     } 
/* 187 */     return null;
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
/*     */   protected boolean hasNodeValueChgForNLS(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 200 */     boolean bool = false;
/*     */     
/* 202 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 203 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 204 */     NLSItem nLSItem = null;
/* 205 */     if (!paramDiffEntity.isDeleted()) {
/* 206 */       nLSItem = entityItem1.getProfile().getReadLanguage();
/*     */     } else {
/* 208 */       nLSItem = entityItem2.getProfile().getReadLanguage();
/*     */     } 
/* 210 */     int i = nLSItem.getNLSID();
/*     */ 
/*     */     
/* 213 */     String str1 = "@@";
/* 214 */     String str2 = "@@";
/* 215 */     if (entityItem1 != null) {
/* 216 */       EntityGroup entityGroup = entityItem1.getEntityGroup();
/* 217 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(this.attrCode);
/* 218 */       if (eANMetaAttribute != null) {
/* 219 */         EANAttribute eANAttribute = entityItem1.getAttribute(this.attrCode);
/* 220 */         if (eANAttribute instanceof EANTextAttribute)
/*     */         {
/*     */           
/* 223 */           if (((EANTextAttribute)eANAttribute).containsNLS(i) && 
/* 224 */             eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 225 */             str1 = eANAttribute.toString();
/*     */           }
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 231 */       if ("@@".equals(str1)) {
/* 232 */         ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: geting current 2nd priority attrCode:" + this.att2code + NEWLINE);
/* 233 */         Vector<EntityItem> vector = new Vector();
/*     */         
/* 235 */         if (this.destinationPath != null && entityItem1 != null) {
/* 236 */           EntityItem entityItem = entityItem1;
/* 237 */           Vector<EntityItem> vector1 = new Vector(1);
/* 238 */           Vector<EntityItem> vector2 = new Vector(1);
/* 239 */           vector2.add(entityItem);
/* 240 */           StringTokenizer stringTokenizer = new StringTokenizer(this.destinationPath, ":");
/* 241 */           while (stringTokenizer.hasMoreTokens()) {
/* 242 */             String str3 = stringTokenizer.nextToken();
/* 243 */             String str4 = null;
/* 244 */             if (stringTokenizer.hasMoreTokens()) {
/* 245 */               str4 = stringTokenizer.nextToken();
/*     */             }
/* 247 */             ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: node:" + this.nodeName + "attrCode:" + this.att2code + " path:" + this.destinationPath + " dir:" + str3 + " destination " + str4 + NEWLINE);
/*     */ 
/*     */             
/* 250 */             Vector<EntityItem> vector3 = new Vector();
/* 251 */             for (byte b = 0; b < vector2.size(); b++) {
/* 252 */               EntityItem entityItem3 = vector2.elementAt(b);
/* 253 */               ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: loop pitem " + entityItem3.getKey() + NEWLINE);
/* 254 */               Vector<EntityItem> vector4 = null;
/* 255 */               if (str3.equals("D")) {
/* 256 */                 vector4 = entityItem3.getDownLink();
/*     */               } else {
/* 258 */                 vector4 = entityItem3.getUpLink();
/*     */               } 
/* 260 */               for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 261 */                 EntityItem entityItem4 = vector4.elementAt(b1);
/* 262 */                 ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: linkloop entity " + entityItem4.getKey() + NEWLINE);
/* 263 */                 if (entityItem4.getEntityType().equals(str4)) {
/* 264 */                   if (stringTokenizer.hasMoreTokens()) {
/*     */                     
/* 266 */                     vector3.add(entityItem4);
/*     */                   } else {
/* 268 */                     vector1.add(entityItem4);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/* 273 */             vector2 = vector3;
/*     */           } 
/*     */           
/* 276 */           vector = vector1;
/*     */         } 
/* 278 */         if (vector.size() > 0) {
/* 279 */           for (byte b = 0; b < vector.size(); b++) {
/* 280 */             EntityItem entityItem = vector.elementAt(b);
/* 281 */             EntityGroup entityGroup1 = entityItem.getEntityGroup();
/* 282 */             EANMetaAttribute eANMetaAttribute1 = entityGroup1.getMetaAttribute(this.att2code);
/* 283 */             if (eANMetaAttribute1 != null) {
/* 284 */               EANAttribute eANAttribute = entityItem.getAttribute(this.att2code);
/* 285 */               if (eANAttribute instanceof EANTextAttribute)
/*     */               {
/*     */                 
/* 288 */                 if (((EANTextAttribute)eANAttribute).containsNLS(i) && 
/* 289 */                   eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 290 */                   str1 = eANAttribute.toString();
/*     */                 }
/*     */               }
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/* 298 */           ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: node:" + this.nodeName + " No entities found for " + this.destinationPath + NEWLINE);
/*     */         } 
/*     */ 
/*     */         
/* 302 */         vector.clear();
/*     */       } 
/*     */     } 
/* 305 */     if (entityItem2 != null) {
/* 306 */       EntityGroup entityGroup = entityItem2.getEntityGroup();
/* 307 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(this.attrCode);
/* 308 */       if (eANMetaAttribute != null) {
/* 309 */         EANAttribute eANAttribute = entityItem2.getAttribute(this.attrCode);
/* 310 */         if (eANAttribute instanceof EANTextAttribute)
/*     */         {
/* 312 */           if (((EANTextAttribute)eANAttribute).containsNLS(i) && 
/* 313 */             eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 314 */             str2 = eANAttribute.toString();
/*     */           }
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 320 */       if ("@@".equals(str2)) {
/* 321 */         ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: geting preview 2nd priority attrCode:" + this.att2code + NEWLINE);
/* 322 */         Vector<EntityItem> vector = new Vector();
/*     */         
/* 324 */         if (this.destinationPath != null && entityItem2 != null) {
/* 325 */           EntityItem entityItem = entityItem2;
/* 326 */           Vector<EntityItem> vector1 = new Vector(1);
/* 327 */           Vector<EntityItem> vector2 = new Vector(1);
/* 328 */           vector2.add(entityItem);
/* 329 */           StringTokenizer stringTokenizer = new StringTokenizer(this.destinationPath, ":");
/* 330 */           while (stringTokenizer.hasMoreTokens()) {
/* 331 */             String str3 = stringTokenizer.nextToken();
/* 332 */             String str4 = null;
/* 333 */             if (stringTokenizer.hasMoreTokens()) {
/* 334 */               str4 = stringTokenizer.nextToken();
/*     */             }
/* 336 */             ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: node:" + this.nodeName + " path:" + this.destinationPath + " dir:" + str3 + " destination " + str4 + NEWLINE);
/*     */ 
/*     */             
/* 339 */             Vector<EntityItem> vector3 = new Vector();
/* 340 */             for (byte b = 0; b < vector2.size(); b++) {
/* 341 */               EntityItem entityItem3 = vector2.elementAt(b);
/* 342 */               ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: loop pitem " + entityItem3.getKey() + NEWLINE);
/* 343 */               Vector<EntityItem> vector4 = null;
/* 344 */               if (str3.equals("D")) {
/* 345 */                 vector4 = entityItem3.getDownLink();
/*     */               } else {
/* 347 */                 vector4 = entityItem3.getUpLink();
/*     */               } 
/* 349 */               for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 350 */                 EntityItem entityItem4 = vector4.elementAt(b1);
/* 351 */                 ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: linkloop entity " + entityItem4.getKey() + NEWLINE);
/* 352 */                 if (entityItem4.getEntityType().equals(str4)) {
/* 353 */                   if (stringTokenizer.hasMoreTokens()) {
/*     */                     
/* 355 */                     vector3.add(entityItem4);
/*     */                   } else {
/* 357 */                     vector1.add(entityItem4);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/* 362 */             vector2 = vector3;
/*     */           } 
/*     */           
/* 365 */           vector = vector1;
/*     */         } 
/* 367 */         if (vector.size() > 0) {
/* 368 */           for (byte b = 0; b < vector.size(); b++) {
/* 369 */             EntityItem entityItem = vector.elementAt(b);
/* 370 */             EntityGroup entityGroup1 = entityItem.getEntityGroup();
/* 371 */             EANMetaAttribute eANMetaAttribute1 = entityGroup1.getMetaAttribute(this.att2code);
/* 372 */             if (eANMetaAttribute1 != null) {
/* 373 */               EANAttribute eANAttribute = entityItem.getAttribute(this.att2code);
/* 374 */               if (eANAttribute instanceof EANTextAttribute)
/*     */               {
/*     */                 
/* 377 */                 if (((EANTextAttribute)eANAttribute).containsNLS(i) && 
/* 378 */                   eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 379 */                   str2 = eANAttribute.toString();
/*     */                 }
/*     */               }
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/* 387 */           ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: node:" + this.nodeName + " No entities found for " + this.destinationPath + NEWLINE);
/*     */         } 
/*     */ 
/*     */         
/* 391 */         vector.clear();
/*     */       } 
/*     */     } 
/* 394 */     ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS.hasNodeValueChgForNLS node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage " + nLSItem + " attr " + this.attrCode + "\n currVal: " + str1 + "\n prevVal: " + str2 + NEWLINE);
/*     */ 
/*     */     
/* 397 */     if (!str1.equals(str2)) {
/* 398 */       bool = true;
/*     */     }
/* 400 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLINVNameElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */