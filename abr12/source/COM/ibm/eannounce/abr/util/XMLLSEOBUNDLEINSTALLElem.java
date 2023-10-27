/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
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
/*     */ public class XMLLSEOBUNDLEINSTALLElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLLSEOBUNDLEINSTALLElem() {
/*  48 */     super(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeLongFlagDesc(EntityItem paramEntityItem, String paramString) {
/*     */     MetaFlag[] arrayOfMetaFlag;
/*     */     byte b;
/*  57 */     String str1 = null;
/*  58 */     String str2 = "";
/*  59 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/*     */     
/*  61 */     if (eANAttribute == null) {
/*  62 */       D.ebug(0, "getAttributeLongFlagDesc:entityattribute is null");
/*  63 */       return null;
/*     */     } 
/*  65 */     str1 = eANAttribute.toString();
/*  66 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*     */     
/*  68 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString);
/*  69 */     switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*     */       case 'F':
/*     */       case 'S':
/*     */       case 'U':
/*  73 */         arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/*  74 */         for (b = 0; b < arrayOfMetaFlag.length; b++) {
/*  75 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  76 */             if (arrayOfMetaFlag[b].getShortDescription() != null) {
/*  77 */               if (str2.trim().length() > 0) {
/*  78 */                 str2 = str2 + "|" + arrayOfMetaFlag[b].getLongDescription();
/*     */               } else {
/*  80 */                 str2 = arrayOfMetaFlag[b].getLongDescription();
/*     */               } 
/*     */             } else {
/*  83 */               D.ebug(0, "getAttributeLongFlagDesc:NULL returned for " + arrayOfMetaFlag[b].getFlagCode());
/*     */             } 
/*     */           }
/*     */         } 
/*  87 */         str1 = str2;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/*  92 */     D.ebug(4, "getAttributeLongFlagDesc:Attribute values are " + str2);
/*     */     
/*  94 */     return str1;
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
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 127 */     String str1 = "";
/* 128 */     boolean bool = false;
/* 129 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 130 */     if (paramDiffEntity.isDeleted()) {
/* 131 */       entityItem = paramDiffEntity.getPriorEntityItem();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     String str2 = "BUNDLETYPE";
/* 142 */     String str3 = getAttributeLongFlagDesc(entityItem, str2);
/* 143 */     ABRUtil.append(paramStringBuffer, "LSEOBUNDLE BUNDLETYPE=" + str3 + NEWLINE);
/* 144 */     if (str3 == null) {
/* 145 */       ABRUtil.append(paramStringBuffer, "LSEOBUNDLE is null." + NEWLINE);
/* 146 */       str1 = "";
/* 147 */     } else if (str3.indexOf("Hardware") > -1) {
/* 148 */       ABRUtil.append(paramStringBuffer, "LSEOBUNDLE is Hardware ." + NEWLINE);
/* 149 */       bool = true;
/*     */     } else {
/* 151 */       ABRUtil.append(paramStringBuffer, "LSEOBUNDLE is not Hardware ." + NEWLINE);
/* 152 */       str1 = "";
/*     */     } 
/*     */ 
/*     */     
/* 156 */     if (bool) {
/* 157 */       str1 = getModelOfINSTALL(entityItem, paramStringBuffer);
/*     */     } else {
/* 159 */       str1 = "";
/*     */     } 
/* 161 */     if (str1 == null) str1 = ""; 
/* 162 */     createNodeSet(paramDocument, paramElement, str1, paramStringBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getModelOfINSTALL(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 171 */     String str = "";
/* 172 */     Vector<EntityItem> vector = paramEntityItem.getDownLink(); byte b;
/* 173 */     label31: for (b = 0; b < vector.size(); b++) {
/* 174 */       EntityItem entityItem = vector.get(b);
/* 175 */       if (entityItem != null && "LSEOBUNDLELSEO".equals(entityItem.getEntityType())) {
/* 176 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 177 */         Vector<EntityItem> vector1 = entityItem1.getUpLink();
/* 178 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 179 */           EntityItem entityItem2 = vector1.get(b1);
/* 180 */           if (entityItem2 != null && "WWSEOLSEO".equals(entityItem2.getEntityType())) {
/* 181 */             EntityItem entityItem3 = (EntityItem)entityItem2.getUpLink(0);
/* 182 */             Vector<EntityItem> vector2 = entityItem3.getUpLink();
/* 183 */             for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 184 */               EntityItem entityItem4 = vector2.get(b2);
/* 185 */               if (entityItem4 != null && "MODELWWSEO".equals(entityItem4.getEntityType())) {
/* 186 */                 EntityItem entityItem5 = (EntityItem)entityItem4.getUpLink(0);
/* 187 */                 String str1 = getAttributeLongFlagDesc(entityItem5, "COFCAT");
/* 188 */                 ABRUtil.append(paramStringBuffer, "MODEL COFCAT=" + str1 + NEWLINE);
/* 189 */                 if (str1 != null && 
/* 190 */                   str1.indexOf("Hardware") > -1) {
/* 191 */                   str = getAttributeLongFlagDesc(entityItem5, "INSTALL");
/*     */                   
/*     */                   break label31;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     return str;
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
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, String paramString, StringBuffer paramStringBuffer) {
/* 214 */     Element element = paramDocument.createElement("INSTALL");
/* 215 */     element.appendChild(paramDocument.createTextNode("" + paramString));
/* 216 */     paramElement.appendChild(element);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLLSEOBUNDLEINSTALLElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */