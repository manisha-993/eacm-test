/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLANNElem
/*     */   extends XMLElem
/*     */ {
/*     */   private static final String AVAIL_PA = "146";
/*     */   private static final String AVAIL_LO = "149";
/*     */   private static final String ANNTYPE_NEW = "19";
/*     */   private static final String ANNTYPE_EndOfLife = "14";
/*     */   
/*     */   public XMLANNElem() {
/*  34 */     super(null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  58 */     String str1 = "@@";
/*  59 */     String str2 = "@@";
/*  60 */     String str3 = "@@";
/*  61 */     String str4 = "@@";
/*  62 */     boolean bool = isDerivefromModel(paramHashtable, paramDiffEntity, paramStringBuffer);
/*  63 */     if (bool == true) {
/*  64 */       if (paramDiffEntity != null) {
/*  65 */         EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  66 */         if (entityItem != null) {
/*  67 */           str1 = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "@@", false);
/*  68 */           str3 = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", ", ", "@@", false);
/*  69 */           ABRUtil.append(paramStringBuffer, "XMLANNElem.addElements" + entityItem.getKey() + " thedate: " + str1 + " withdrawanndate: " + str3 + NEWLINE);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/*  74 */       Vector<EntityItem> vector1 = getAnnouncement(paramHashtable, "146", "19", paramStringBuffer);
/*  75 */       if (vector1 != null) {
/*     */         
/*  77 */         if (vector1.size() > 0) {
/*     */ 
/*     */           
/*  80 */           for (byte b = 0; b < vector1.size(); b++) {
/*     */             
/*  82 */             EntityItem entityItem = vector1.elementAt(b);
/*  83 */             if (entityItem != null) {
/*  84 */               String str5 = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "@@", false);
/*  85 */               String str6 = PokUtils.getAttributeValue(entityItem, "ANNNUMBER", ", ", "@@", false);
/*  86 */               ABRUtil.append(paramStringBuffer, "XMLANNElem.addElements checking[" + b + "]:" + entityItem.getKey() + " thedate: " + str5 + " thenumber: " + str6 + NEWLINE);
/*     */ 
/*     */               
/*  89 */               if (!"@@".equals(str5))
/*     */               {
/*  91 */                 if (str1.equals("@@")) {
/*     */                   
/*  93 */                   str1 = str5;
/*  94 */                   str2 = str6;
/*     */                 
/*     */                 }
/*  97 */                 else if (str5.compareTo(str1) < 0) {
/*  98 */                   str1 = str5;
/*  99 */                   str2 = str6;
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 106 */           ABRUtil.append(paramStringBuffer, "XMLANNElem.addElements new anndate: " + str1 + " annnumber: " + str2 + NEWLINE);
/*     */         } 
/*     */       } else {
/*     */         
/* 110 */         ABRUtil.append(paramStringBuffer, "XMLANNElem.addElements no NEW ANNOUNCEMENT(ANNTYPE =19) found. newAnnVct.size:" + ((vector1 == null) ? "null" : ("" + vector1
/* 111 */             .size())) + NEWLINE);
/*     */       } 
/*     */       
/* 114 */       Vector<EntityItem> vector2 = getAnnouncement(paramHashtable, "149", "14", paramStringBuffer);
/* 115 */       if (vector2 != null) {
/* 116 */         if (vector2.size() > 0)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 122 */           for (byte b = 0; b < vector2.size(); b++) {
/*     */             
/* 124 */             EntityItem entityItem = vector2.elementAt(b);
/* 125 */             if (entityItem != null) {
/* 126 */               String str5 = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "@@", false);
/* 127 */               String str6 = PokUtils.getAttributeValue(entityItem, "ANNNUMBER", ", ", "@@", false);
/* 128 */               ABRUtil.append(paramStringBuffer, "XMLANNElem.addElements latest checking[" + b + "]:" + entityItem.getKey() + " thedate: " + str5 + " thenumber: " + str6 + NEWLINE);
/*     */               
/* 130 */               if (!"@@".equals(str5))
/*     */               {
/* 132 */                 if (str3.equals("@@")) {
/* 133 */                   str3 = str5;
/* 134 */                   str4 = str6;
/*     */                 
/*     */                 }
/* 137 */                 else if (str5.compareTo(str3) > 0) {
/* 138 */                   str3 = str5;
/* 139 */                   str4 = str6;
/*     */                 } 
/*     */               }
/*     */               
/* 143 */               ABRUtil.append(paramStringBuffer, "XMLANNElem.addElements latest anndate: " + str3 + " annnumber: " + str4 + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } else {
/*     */         
/* 149 */         ABRUtil.append(paramStringBuffer, "XMLANNElem.addElements no end ANNOUNCEMENT(ANNTYPE =14) found. AnnVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/* 150 */             .size())) + NEWLINE);
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     createNodeSet(paramDocument, paramElement, str1, str2, str3, str4, paramStringBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer) {
/* 159 */     Element element = paramDocument.createElement("ANNOUNCEDATE");
/* 160 */     element.appendChild(paramDocument.createTextNode("" + paramString1));
/* 161 */     paramElement.appendChild(element);
/* 162 */     element = paramDocument.createElement("ANNOUNCENUMBER");
/* 163 */     element.appendChild(paramDocument.createTextNode("" + paramString2));
/* 164 */     paramElement.appendChild(element);
/* 165 */     element = paramDocument.createElement("WITHDRAWANNOUNCEDATE");
/* 166 */     element.appendChild(paramDocument.createTextNode("" + paramString3));
/* 167 */     paramElement.appendChild(element);
/* 168 */     element = paramDocument.createElement("WITHDRAWANNOUNCENUMBER");
/* 169 */     element.appendChild(paramDocument.createTextNode("" + paramString4));
/* 170 */     paramElement.appendChild(element);
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
/*     */   private Vector getAnnouncement(Hashtable paramHashtable, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/* 187 */     Vector<EntityItem> vector = new Vector(1);
/* 188 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("AVAIL");
/*     */     
/* 190 */     ABRUtil.append(paramStringBuffer, "getANNOUNCEMENT.getAvails looking for AVAILTYPE: " + paramString1 + "in AVAIL allVct.size:" + ((vector1 == null) ? "null" : ("" + vector1
/* 191 */         .size())) + NEWLINE);
/* 192 */     if (vector1 == null) {
/* 193 */       return vector;
/*     */     }
/*     */ 
/*     */     
/* 197 */     for (byte b = 0; b < vector1.size(); b++) {
/* 198 */       DiffEntity diffEntity = vector1.elementAt(b);
/* 199 */       EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 200 */       if (!diffEntity.isDeleted()) {
/* 201 */         ABRUtil.append(paramStringBuffer, "XMLANNElem.getANNOUNCEMENT.getAvails checking[" + b + "]:New or Update" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 202 */             PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/* 203 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 204 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString1)) {
/* 205 */           Vector<EntityItem> vector2 = entityItem.getDownLink();
/*     */           
/* 207 */           ABRUtil.append(paramStringBuffer, "XMLANNElem.getANNOUNCEMENT looking for downlink of AVAIL annVct.size: " + ((vector2 == null) ? "null" : ("" + vector2
/* 208 */               .size())) + "Downlinkcount: " + entityItem
/* 209 */               .getDownLinkCount() + NEWLINE);
/* 210 */           for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 211 */             EntityItem entityItem1 = vector2.elementAt(b1);
/*     */             
/* 213 */             ABRUtil.append(paramStringBuffer, "XMLANNElem.getANNOUNCEMENT looking for downlink of AVAIL " + entityItem1.getKey() + "entitytype is: " + entityItem1
/* 214 */                 .getEntityType() + NEWLINE);
/*     */             
/* 216 */             if (entityItem1.getEntityType().equals("AVAILANNA") && entityItem1.hasDownLinks()) {
/*     */               
/* 218 */               Vector<EntityItem> vector3 = entityItem1.getDownLink();
/* 219 */               for (byte b2 = 0; b2 < vector3.size(); b2++) {
/* 220 */                 EntityItem entityItem2 = vector3.elementAt(b2);
/* 221 */                 ABRUtil.append(paramStringBuffer, "XMLANNElem.getANNOUNCEMENT looking for downlink of AVAILANNA " + entityItem2.getKey() + "entitytype is: " + entityItem2
/* 222 */                     .getEntityType() + "Attriubte ANNTYPE is: " + 
/* 223 */                     PokUtils.getAttributeFlagValue(entityItem2, "ANNTYPE") + NEWLINE);
/* 224 */                 EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute("ANNTYPE");
/* 225 */                 if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(paramString2)) {
/* 226 */                   vector.add(entityItem2);
/*     */                 } else {
/* 228 */                   ABRUtil.append(paramStringBuffer, "XMLANNElem.getANNOUNCEMENT ANNTYPE: " + 
/* 229 */                       PokUtils.getAttributeFlagValue(entityItem2, "ANNTYPE") + "is not equal " + paramString2 + NEWLINE);
/*     */                 } 
/*     */               } 
/*     */             } else {
/* 233 */               ABRUtil.append(paramStringBuffer, "XMLANNElem.getANNOUNCEMENT no downlink of AVAILANNA was found" + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 241 */     ABRUtil.append(paramStringBuffer, "XMLANNElem.getANNOUNCEMENT return:  avlVct.size:" + ((vector == null) ? "null" : ("" + vector.size())) + NEWLINE);
/*     */     
/* 243 */     return vector;
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
/*     */   private boolean isDerivefromModel(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 262 */     boolean bool = true;
/*     */     
/* 264 */     if (paramDiffEntity != null && (
/* 265 */       paramDiffEntity.getEntityType().equals("MODEL") || paramDiffEntity.getEntityType().equals("SVCMOD"))) {
/* 266 */       String str = "20100301";
/* 267 */       Vector<DiffEntity> vector = (Vector)paramHashtable.get("AVAIL");
/* 268 */       ABRUtil.append(paramStringBuffer, "DerivefromModel.getAvails looking for AVAILTYPE: 146in AVAIL allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 269 */           .size())) + NEWLINE);
/* 270 */       if (vector != null)
/*     */       {
/* 272 */         for (byte b = 0; b < vector.size(); b++) {
/* 273 */           DiffEntity diffEntity = vector.elementAt(b);
/* 274 */           EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 275 */           if (!diffEntity.isDeleted()) {
/* 276 */             ABRUtil.append(paramStringBuffer, "XMLANNElem.DerivefromModel.getAvails checking[" + b + "]:New or Update" + diffEntity
/* 277 */                 .getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/*     */             
/* 279 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 280 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 281 */               bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLANNElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */