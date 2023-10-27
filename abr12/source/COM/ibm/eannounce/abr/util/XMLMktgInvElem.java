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
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLMktgInvElem
/*     */   extends XMLElem
/*     */ {
/*     */   private String destinationPath;
/*     */   private String att2code;
/*     */   
/*     */   public XMLMktgInvElem(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  50 */     super(paramString1, paramString2);
/*  51 */     this.destinationPath = paramString4;
/*  52 */     this.att2code = paramString3;
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
/*  67 */     if (this.attrCode == null || paramEntityItem == null) {
/*  68 */       return null;
/*     */     }
/*  70 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*  71 */     EntityList entityList = entityGroup.getEntityList();
/*     */ 
/*     */     
/*  74 */     String[] arrayOfString = PokUtils.convertToArray(this.attrCode);
/*  75 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  76 */       String str1 = "@@";
/*  77 */       String str2 = arrayOfString[b];
/*  78 */       boolean bool = false;
/*  79 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(str2);
/*     */ 
/*     */ 
/*     */       
/*  83 */       if (eANMetaAttribute == null) {
/*  84 */         if (this.isReq) {
/*  85 */           throw new IOException(this.nodeName + " is required but " + str2 + " is not in " + paramEntityItem.getEntityType() + " META data");
/*     */         }
/*     */ 
/*     */         
/*  89 */         str1 = "Error: Attribute " + str2 + " not found in " + paramEntityItem.getEntityType() + " META data.";
/*     */       } else {
/*  91 */         Profile profile = entityList.getProfile();
/*  92 */         NLSItem nLSItem = profile.getReadLanguage();
/*  93 */         int i = nLSItem.getNLSID();
/*     */ 
/*     */         
/*  96 */         EANAttribute eANAttribute1 = paramEntityItem.getAttribute(str2);
/*  97 */         EANAttribute eANAttribute2 = paramEntityItem.getAttribute(str2);
/*  98 */         if (eANAttribute1 instanceof EANTextAttribute && eANAttribute2 instanceof EANTextAttribute && (
/*  99 */           (EANTextAttribute)eANAttribute1).containsNLS(1) && 
/* 100 */           eANAttribute1 != null && eANAttribute1.toString().length() > 0) {
/* 101 */           bool = true;
/* 102 */           ABRUtil.append(paramStringBuffer, "use the TMF's" + NEWLINE);
/* 103 */           if (((EANTextAttribute)eANAttribute2).containsNLS(i)) {
/* 104 */             str1 = eANAttribute2.toString();
/* 105 */             if (eANMetaAttribute.getAttributeType().equals("T") && 
/* 106 */               str1.length() > getTextLimit()) {
/* 107 */               str1 = str1.substring(0, getTextLimit());
/* 108 */               ABRUtil.append(paramStringBuffer, "XMLElem.getContentNode node:" + this.nodeName + " " + paramEntityItem.getKey() + " value was truncated for attr " + str2 + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 116 */         if (!bool) {
/* 117 */           Vector<EntityItem> vector = new Vector();
/*     */           
/* 119 */           if (this.destinationPath != null && paramEntityItem != null) {
/* 120 */             ABRUtil.append(paramStringBuffer, "use the feature's" + NEWLINE);
/* 121 */             EntityItem entityItem = paramEntityItem;
/* 122 */             Vector<EntityItem> vector1 = new Vector(1);
/* 123 */             Vector<EntityItem> vector2 = new Vector(1);
/* 124 */             vector2.add(entityItem);
/* 125 */             StringTokenizer stringTokenizer = new StringTokenizer(this.destinationPath, ":");
/* 126 */             while (stringTokenizer.hasMoreTokens()) {
/* 127 */               String str3 = stringTokenizer.nextToken();
/* 128 */               String str4 = null;
/* 129 */               if (stringTokenizer.hasMoreTokens()) {
/* 130 */                 str4 = stringTokenizer.nextToken();
/*     */               }
/* 132 */               ABRUtil.append(paramStringBuffer, "XMLMktgInvElem: node:" + this.nodeName + " attrbutecode:" + str2 + " path:" + this.destinationPath + " dir:" + str3 + " destination " + str4 + NEWLINE);
/*     */ 
/*     */               
/* 135 */               Vector<EntityItem> vector3 = new Vector();
/* 136 */               for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 137 */                 EntityItem entityItem1 = vector2.elementAt(b2);
/* 138 */                 ABRUtil.append(paramStringBuffer, "XMLMktgInvElem: loop pitem " + entityItem1.getKey() + NEWLINE);
/* 139 */                 Vector<EntityItem> vector4 = null;
/* 140 */                 if (str3.equals("D")) {
/* 141 */                   vector4 = entityItem1.getDownLink();
/*     */                 } else {
/* 143 */                   vector4 = entityItem1.getUpLink();
/*     */                 } 
/* 145 */                 for (byte b3 = 0; b3 < vector4.size(); b3++) {
/* 146 */                   EntityItem entityItem2 = vector4.elementAt(b3);
/* 147 */                   ABRUtil.append(paramStringBuffer, "XMLMktgInvElem: linkloop entity " + entityItem2.getKey() + NEWLINE);
/* 148 */                   if (entityItem2.getEntityType().equals(str4)) {
/* 149 */                     if (stringTokenizer.hasMoreTokens()) {
/*     */                       
/* 151 */                       vector3.add(entityItem2);
/*     */                     } else {
/* 153 */                       vector1.add(entityItem2);
/*     */                     } 
/*     */                   }
/*     */                 } 
/*     */               } 
/* 158 */               vector2 = vector3;
/*     */             } 
/*     */             
/* 161 */             vector = vector1;
/*     */           } 
/* 163 */           if (vector.size() == 0) {
/* 164 */             paramStringBuffer
/* 165 */               .append("XMLMktgInvElem: node:" + this.nodeName + " No entities found for " + this.destinationPath + NEWLINE);
/*     */ 
/*     */             
/* 168 */             return null;
/*     */           } 
/* 170 */           for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 171 */             EntityItem entityItem = vector.elementAt(b1);
/* 172 */             EntityGroup entityGroup1 = entityItem.getEntityGroup();
/* 173 */             EANMetaAttribute eANMetaAttribute1 = entityGroup1.getMetaAttribute(str2);
/* 174 */             if (eANMetaAttribute1 != null) {
/* 175 */               EANAttribute eANAttribute = entityItem.getAttribute(str2);
/* 176 */               if (eANAttribute instanceof EANTextAttribute)
/*     */               {
/*     */                 
/* 179 */                 if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 180 */                   str1 = eANAttribute.toString();
/* 181 */                   if (eANMetaAttribute1.getAttributeType().equals("T") && 
/* 182 */                     str1.length() > getTextLimit()) {
/* 183 */                     str1 = str1.substring(0, getTextLimit());
/* 184 */                     ABRUtil.append(paramStringBuffer, "XMLElem.getContentNode node:" + this.nodeName + " " + entityItem.getKey() + " value was truncated for attr " + str2 + NEWLINE);
/*     */                   }
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/*     */             } else {
/*     */               
/* 192 */               str1 = "Error: Attribute " + str2 + " not found in " + entityItem.getEntityType() + " META data.";
/*     */             } 
/*     */           } 
/* 195 */           vector.clear();
/*     */         } 
/*     */         
/* 198 */         return paramDocument.createTextNode(str1);
/*     */       } 
/*     */     } 
/* 201 */     return null;
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
/* 214 */     boolean bool1 = false;
/*     */     
/* 216 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 217 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 218 */     NLSItem nLSItem = null;
/* 219 */     if (!paramDiffEntity.isDeleted()) {
/* 220 */       nLSItem = entityItem1.getProfile().getReadLanguage();
/*     */     } else {
/* 222 */       nLSItem = entityItem2.getProfile().getReadLanguage();
/*     */     } 
/* 224 */     int i = nLSItem.getNLSID();
/* 225 */     String str1 = "@@";
/* 226 */     String str2 = "@@";
/* 227 */     boolean bool2 = false;
/* 228 */     boolean bool3 = false;
/*     */     
/* 230 */     String[] arrayOfString = PokUtils.convertToArray(this.attrCode);
/* 231 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 232 */       String str = arrayOfString[b];
/* 233 */       if (!str.equals("ENTITYTYPE") && !str.equals("ENTITYID") && !str.equals("NLSID") && 
/* 234 */         !str.equals("ENTITY1ID") && !str.equals("ENTITY2ID")) {
/*     */ 
/*     */ 
/*     */         
/* 238 */         if (entityItem1 != null) {
/* 239 */           EntityGroup entityGroup = entityItem1.getEntityGroup();
/* 240 */           EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(str);
/* 241 */           if (eANMetaAttribute != null) {
/* 242 */             EANAttribute eANAttribute1 = entityItem1.getAttribute(str);
/* 243 */             EANAttribute eANAttribute2 = entityItem1.getAttribute(str);
/* 244 */             if (eANAttribute1 instanceof EANTextAttribute && eANAttribute2 instanceof EANTextAttribute && (
/* 245 */               (EANTextAttribute)eANAttribute1).containsNLS(1) && 
/* 246 */               eANAttribute1 != null && eANAttribute1.toString().length() > 0) {
/* 247 */               bool2 = true;
/* 248 */               ABRUtil.append(paramStringBuffer, "check the TMF's " + NEWLINE);
/* 249 */               if (((EANTextAttribute)eANAttribute2).containsNLS(i)) {
/* 250 */                 str1 = eANAttribute2.toString();
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 258 */           if (!bool2) {
/* 259 */             ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: geting current 2nd priority attrCode:" + this.att2code + NEWLINE);
/* 260 */             Vector<EntityItem> vector = new Vector();
/*     */             
/* 262 */             if (this.destinationPath != null && entityItem1 != null) {
/* 263 */               ABRUtil.append(paramStringBuffer, "check the feature's" + NEWLINE);
/* 264 */               EntityItem entityItem = entityItem1;
/* 265 */               Vector<EntityItem> vector1 = new Vector(1);
/* 266 */               Vector<EntityItem> vector2 = new Vector(1);
/* 267 */               vector2.add(entityItem);
/* 268 */               StringTokenizer stringTokenizer = new StringTokenizer(this.destinationPath, ":");
/* 269 */               while (stringTokenizer.hasMoreTokens()) {
/* 270 */                 String str3 = stringTokenizer.nextToken();
/* 271 */                 String str4 = null;
/* 272 */                 if (stringTokenizer.hasMoreTokens()) {
/* 273 */                   str4 = stringTokenizer.nextToken();
/*     */                 }
/* 275 */                 ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: node:" + this.nodeName + "attrCode:" + str + " path:" + this.destinationPath + " dir:" + str3 + " destination " + str4 + NEWLINE);
/*     */ 
/*     */                 
/* 278 */                 Vector<EntityItem> vector3 = new Vector();
/* 279 */                 for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 280 */                   EntityItem entityItem3 = vector2.elementAt(b1);
/* 281 */                   ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: loop pitem " + entityItem3.getKey() + NEWLINE);
/* 282 */                   Vector<EntityItem> vector4 = null;
/* 283 */                   if (str3.equals("D")) {
/* 284 */                     vector4 = entityItem3.getDownLink();
/*     */                   } else {
/* 286 */                     vector4 = entityItem3.getUpLink();
/*     */                   } 
/* 288 */                   for (byte b2 = 0; b2 < vector4.size(); b2++) {
/* 289 */                     EntityItem entityItem4 = vector4.elementAt(b2);
/* 290 */                     ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: linkloop entity " + entityItem4.getKey() + NEWLINE);
/* 291 */                     if (entityItem4.getEntityType().equals(str4)) {
/* 292 */                       if (stringTokenizer.hasMoreTokens()) {
/*     */                         
/* 294 */                         vector3.add(entityItem4);
/*     */                       } else {
/* 296 */                         vector1.add(entityItem4);
/*     */                       } 
/*     */                     }
/*     */                   } 
/*     */                 } 
/* 301 */                 vector2 = vector3;
/*     */               } 
/*     */               
/* 304 */               vector = vector1;
/*     */             } 
/* 306 */             if (vector.size() > 0) {
/* 307 */               for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 308 */                 EntityItem entityItem = vector.elementAt(b1);
/* 309 */                 EntityGroup entityGroup1 = entityItem.getEntityGroup();
/* 310 */                 EANMetaAttribute eANMetaAttribute1 = entityGroup1.getMetaAttribute(str);
/* 311 */                 if (eANMetaAttribute1 != null) {
/* 312 */                   EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 313 */                   if (eANAttribute instanceof EANTextAttribute)
/*     */                   {
/*     */                     
/* 316 */                     if (((EANTextAttribute)eANAttribute).containsNLS(i))
/*     */                     {
/* 318 */                       str1 = eANAttribute.toString();
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               
/*     */               } 
/*     */             } else {
/*     */               
/* 326 */               ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: node:" + this.nodeName + " No entities found for " + this.destinationPath + NEWLINE);
/*     */             } 
/*     */ 
/*     */             
/* 330 */             vector.clear();
/*     */           } 
/*     */         } 
/* 333 */         if (entityItem2 != null) {
/* 334 */           EntityGroup entityGroup = entityItem2.getEntityGroup();
/* 335 */           EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(str);
/* 336 */           if (eANMetaAttribute != null) {
/* 337 */             EANAttribute eANAttribute1 = entityItem2.getAttribute(str);
/* 338 */             EANAttribute eANAttribute2 = entityItem2.getAttribute(str);
/* 339 */             if (eANAttribute1 instanceof EANTextAttribute && eANAttribute2 instanceof EANTextAttribute)
/*     */             {
/*     */               
/* 342 */               if (((EANTextAttribute)eANAttribute1).containsNLS(1) && 
/* 343 */                 eANAttribute1 != null && eANAttribute1.toString().length() > 0) {
/* 344 */                 bool3 = true;
/* 345 */                 ABRUtil.append(paramStringBuffer, "check the TMF's " + NEWLINE);
/* 346 */                 if (((EANTextAttribute)eANAttribute2).containsNLS(i)) {
/* 347 */                   str2 = eANAttribute2.toString();
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 354 */           if (!bool3) {
/* 355 */             ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: geting preview 2nd priority attrCode:" + str + NEWLINE);
/* 356 */             Vector<EntityItem> vector = new Vector();
/*     */             
/* 358 */             if (this.destinationPath != null && entityItem2 != null) {
/* 359 */               ABRUtil.append(paramStringBuffer, "check the feature's" + NEWLINE);
/* 360 */               EntityItem entityItem = entityItem2;
/* 361 */               Vector<EntityItem> vector1 = new Vector(1);
/* 362 */               Vector<EntityItem> vector2 = new Vector(1);
/* 363 */               vector2.add(entityItem);
/* 364 */               StringTokenizer stringTokenizer = new StringTokenizer(this.destinationPath, ":");
/* 365 */               while (stringTokenizer.hasMoreTokens()) {
/* 366 */                 String str3 = stringTokenizer.nextToken();
/* 367 */                 String str4 = null;
/* 368 */                 if (stringTokenizer.hasMoreTokens()) {
/* 369 */                   str4 = stringTokenizer.nextToken();
/*     */                 }
/* 371 */                 ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: node:" + this.nodeName + " path:" + this.destinationPath + " dir:" + str3 + " destination " + str4 + NEWLINE);
/*     */ 
/*     */                 
/* 374 */                 Vector<EntityItem> vector3 = new Vector();
/* 375 */                 for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 376 */                   EntityItem entityItem3 = vector2.elementAt(b1);
/* 377 */                   ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: loop pitem " + entityItem3.getKey() + NEWLINE);
/* 378 */                   Vector<EntityItem> vector4 = null;
/* 379 */                   if (str3.equals("D")) {
/* 380 */                     vector4 = entityItem3.getDownLink();
/*     */                   } else {
/* 382 */                     vector4 = entityItem3.getUpLink();
/*     */                   } 
/* 384 */                   for (byte b2 = 0; b2 < vector4.size(); b2++) {
/* 385 */                     EntityItem entityItem4 = vector4.elementAt(b2);
/* 386 */                     ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: linkloop entity " + entityItem4.getKey() + NEWLINE);
/* 387 */                     if (entityItem4.getEntityType().equals(str4)) {
/* 388 */                       if (stringTokenizer.hasMoreTokens()) {
/*     */                         
/* 390 */                         vector3.add(entityItem4);
/*     */                       } else {
/* 392 */                         vector1.add(entityItem4);
/*     */                       } 
/*     */                     }
/*     */                   } 
/*     */                 } 
/* 397 */                 vector2 = vector3;
/*     */               } 
/*     */               
/* 400 */               vector = vector1;
/*     */             } 
/* 402 */             if (vector.size() > 0) {
/* 403 */               for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 404 */                 EntityItem entityItem = vector.elementAt(b1);
/* 405 */                 EntityGroup entityGroup1 = entityItem.getEntityGroup();
/* 406 */                 EANMetaAttribute eANMetaAttribute1 = entityGroup1.getMetaAttribute(str);
/* 407 */                 if (eANMetaAttribute1 != null) {
/* 408 */                   EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 409 */                   if (eANAttribute instanceof EANTextAttribute)
/*     */                   {
/*     */                     
/* 412 */                     if (((EANTextAttribute)eANAttribute).containsNLS(i))
/*     */                     {
/* 414 */                       str2 = eANAttribute.toString();
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               
/*     */               } 
/*     */             } else {
/*     */               
/* 422 */               ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS: node:" + this.nodeName + " No entities found for " + this.destinationPath + NEWLINE);
/*     */             } 
/*     */ 
/*     */             
/* 426 */             vector.clear();
/*     */           } 
/*     */         } 
/*     */       } 
/* 430 */     }  ABRUtil.append(paramStringBuffer, "hasNodeValueChgForNLS.hasNodeValueChgForNLS node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage " + nLSItem + " attr " + this.attrCode + "\n currVal: " + str1 + "\n prevVal: " + str2 + NEWLINE);
/*     */ 
/*     */     
/* 433 */     if (!str1.equals(str2)) {
/* 434 */       bool1 = true;
/*     */     }
/* 436 */     return bool1;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLMktgInvElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */