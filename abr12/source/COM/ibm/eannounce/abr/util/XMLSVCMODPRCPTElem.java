/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLSVCMODPRCPTElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLSVCMODPRCPTElem() {
/*  59 */     super("PRCPTELEMENT");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasChanges(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  65 */     boolean bool = false;
/*  66 */     Vector<DiffEntity> vector = getPRCPTs(paramHashtable, paramDiffEntity, paramStringBuffer);
/*  67 */     for (byte b = 0; b < vector.size(); b++) {
/*  68 */       DiffEntity diffEntity = vector.elementAt(b);
/*  69 */       if (diffEntity.isDeleted()) {
/*  70 */         bool = true;
/*  71 */       } else if (diffEntity.isNew()) {
/*  72 */         bool = true;
/*     */       } else {
/*  74 */         EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  75 */         EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*  76 */         bool = hasChangedValue(bool, entityItem1, entityItem2, "PRCPTID");
/*  77 */         if (bool)
/*     */           break; 
/*  79 */         EntityItem entityItem3 = getRelator(paramDiffEntity, entityItem1, paramStringBuffer);
/*  80 */         EntityItem entityItem4 = getRelator(paramDiffEntity, entityItem2, paramStringBuffer);
/*  81 */         bool = hasChangedValue(bool, entityItem3, entityItem4, "EFFECTIVEDATE");
/*  82 */         if (bool)
/*  83 */           break;  bool = hasChangedValue(bool, entityItem3, entityItem4, "ENDDATE");
/*  84 */         if (bool)
/*     */           break; 
/*     */       } 
/*     */     } 
/*  88 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasChangedValue(boolean paramBoolean, EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString) {
/*  98 */     String str1 = "";
/*  99 */     String str2 = "";
/* 100 */     if (paramEntityItem1 != null) {
/* 101 */       str1 = PokUtils.getAttributeValue(paramEntityItem1, paramString, ", ", "@@", false);
/*     */     }
/* 103 */     if (paramEntityItem2 != null) {
/* 104 */       str2 = PokUtils.getAttributeValue(paramEntityItem2, paramString, ", ", "@@", false);
/*     */     }
/* 106 */     if (!str1.equals(str2)) {
/* 107 */       paramBoolean = true;
/*     */     }
/* 109 */     return paramBoolean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 141 */     ABRUtil.append(paramStringBuffer, "XMLSVCMODPRCPTElem:parentItem: " + paramDiffEntity.getKey() + NEWLINE);
/* 142 */     Vector<DiffEntity> vector = getPRCPTs(paramHashtable, paramDiffEntity, paramStringBuffer);
/* 143 */     for (byte b = 0; b < vector.size(); b++) {
/* 144 */       DiffEntity diffEntity = vector.elementAt(b);
/* 145 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 146 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/* 147 */       String str = "";
/* 148 */       if (diffEntity.isDeleted()) {
/* 149 */         str = "Delete";
/* 150 */         createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, entityItem2, str, paramStringBuffer);
/* 151 */       } else if (diffEntity.isNew()) {
/* 152 */         str = "Update";
/* 153 */         createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, entityItem1, str, paramStringBuffer);
/*     */       } else {
/* 155 */         str = "Update";
/* 156 */         createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, entityItem1, str, paramStringBuffer);
/*     */       } 
/*     */     } 
/* 159 */     vector.clear();
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
/*     */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) {
/* 173 */     Element element1 = paramDocument.createElement(this.nodeName);
/* 174 */     addXMLAttrs(element1);
/* 175 */     paramElement.appendChild(element1);
/*     */ 
/*     */ 
/*     */     
/* 179 */     Element element2 = paramDocument.createElement("ACTIVITY");
/* 180 */     element2.appendChild(paramDocument.createTextNode("" + paramString));
/* 181 */     element1.appendChild(element2);
/*     */     
/* 183 */     EntityItem entityItem = getRelator(paramDiffEntity, paramEntityItem, paramStringBuffer);
/*     */     
/* 185 */     element2 = paramDocument.createElement("EFFECTIVEDATE");
/* 186 */     element2.appendChild(paramDocument.createTextNode("" + PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false)));
/* 187 */     element1.appendChild(element2);
/*     */ 
/*     */     
/* 190 */     element2 = paramDocument.createElement("ENDDATE");
/* 191 */     element2.appendChild(paramDocument.createTextNode("" + PokUtils.getAttributeValue(entityItem, "ENDDATE", ", ", "@@", false)));
/* 192 */     element1.appendChild(element2);
/*     */     
/* 194 */     element2 = paramDocument.createElement("ENTITYTYPE");
/* 195 */     element2.appendChild(paramDocument.createTextNode("" + paramEntityItem.getEntityType()));
/* 196 */     element1.appendChild(element2);
/*     */     
/* 198 */     element2 = paramDocument.createElement("ENTITYID");
/* 199 */     element2.appendChild(paramDocument.createTextNode("" + paramEntityItem.getEntityID()));
/* 200 */     element1.appendChild(element2);
/*     */     
/* 202 */     element2 = paramDocument.createElement("PRCPTID");
/* 203 */     element2.appendChild(paramDocument.createTextNode("" + PokUtils.getAttributeValue(paramEntityItem, "PRCPTID", ", ", "@@", false)));
/* 204 */     element1.appendChild(element2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityItem getRelator(DiffEntity paramDiffEntity, EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 213 */     EntityItem entityItem = null;
/* 214 */     Vector<EntityItem> vector = paramEntityItem.getUpLink();
/* 215 */     for (byte b = 0; b < vector.size(); b++) {
/* 216 */       EntityItem entityItem1 = vector.get(b);
/* 217 */       if (entityItem1 != null && "SVCSEOPRCPT".equals(entityItem1.getEntityType())) {
/* 218 */         EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 219 */         if (entityItem2.getKey().equals(paramDiffEntity.getKey())) {
/* 220 */           entityItem = entityItem1;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 225 */     return entityItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getPRCPTs(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 235 */     Vector<DiffEntity> vector1 = new Vector(1);
/* 236 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("PRCPT");
/* 237 */     if (vector2 == null) {
/* 238 */       return vector1;
/*     */     }
/* 240 */     Vector<String> vector = new Vector();
/* 241 */     for (byte b = 0; b < vector2.size(); b++) {
/* 242 */       DiffEntity diffEntity = vector2.elementAt(b);
/* 243 */       if (!vector.contains(diffEntity.getKey()))
/*     */       {
/*     */         
/* 246 */         if (deriveTheSameEntry(diffEntity, paramDiffEntity, paramStringBuffer) && 
/* 247 */           !vector.contains(diffEntity.getKey())) {
/* 248 */           ABRUtil.append(paramStringBuffer, "XMLSVCMODPRCPTElem.getPRCPTs find PRCPT key=" + diffEntity.getKey() + NEWLINE);
/* 249 */           vector1.add(diffEntity);
/* 250 */           vector.add(diffEntity.getKey());
/*     */         } 
/*     */       }
/*     */     } 
/* 254 */     vector.clear();
/* 255 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deriveTheSameEntry(DiffEntity paramDiffEntity1, DiffEntity paramDiffEntity2, StringBuffer paramStringBuffer) {
/* 265 */     boolean bool = false;
/* 266 */     String str = "";
/* 267 */     if (paramDiffEntity1 != null) {
/* 268 */       str = paramDiffEntity1.toString();
/* 269 */       if (str.indexOf("SVCSEOPRCPT") > -1) {
/* 270 */         ABRUtil.append(paramStringBuffer, "XMLSVCMODPRCPTElem.deriveTheSameEntry path=" + str + NEWLINE);
/* 271 */         EntityItem entityItem = null;
/* 272 */         if (paramDiffEntity1.isDeleted()) {
/* 273 */           entityItem = paramDiffEntity1.getPriorEntityItem();
/*     */         } else {
/* 275 */           entityItem = paramDiffEntity1.getCurrentEntityItem();
/*     */         } 
/* 277 */         Vector<EntityItem> vector = entityItem.getUpLink();
/* 278 */         for (byte b = 0; b < vector.size(); b++) {
/* 279 */           EntityItem entityItem1 = vector.get(b);
/* 280 */           if (entityItem1 != null && "SVCSEOPRCPT".equals(entityItem1.getEntityType())) {
/* 281 */             EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 282 */             if (entityItem2.getKey().equals(paramDiffEntity2.getKey())) {
/* 283 */               bool = true;
/* 284 */               ABRUtil.append(paramStringBuffer, "XMLSVCMODPRCPTElem.deriveTheSameEntry is true and path=" + str + NEWLINE);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 291 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLSVCMODPRCPTElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */