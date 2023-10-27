/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
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
/*     */ public class SAPLGEOElem
/*     */   extends SAPLElem
/*     */ {
/*     */   private String assocName;
/*     */   
/*     */   public SAPLGEOElem(String paramString1, String paramString2, String paramString3) {
/*  63 */     super(paramString1, paramString2, (String)null, false);
/*  64 */     this.assocName = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector getAvailEntities(EntityGroup paramEntityGroup) {
/*  75 */     Vector<EntityItem> vector = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  81 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  82 */       for (byte b1 = 0; b1 < AVAIL_ORDER.length; b1++) {
/*  83 */         if (PokUtils.isSelected(entityItem, "AVAILTYPE", AVAIL_ORDER[b1])) {
/*  84 */           vector.addElement(entityItem);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  90 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getEntities(EntityGroup paramEntityGroup) {
/* 101 */     Vector vector = null;
/* 102 */     if (!paramEntityGroup.getEntityType().equals("AVAIL")) {
/* 103 */       vector = super.getEntities(paramEntityGroup);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 109 */       vector = getAvailEntities(paramEntityGroup);
/*     */     } 
/* 111 */     return vector;
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 136 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(this.etype);
/* 137 */     if (entityGroup == null) {
/* 138 */       Element element = paramDocument.createElement(this.nodeName);
/* 139 */       addXMLAttrs(element);
/* 140 */       paramElement.appendChild(element);
/* 141 */       element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */       
/* 143 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 144 */         SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 145 */         sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
/*     */       } 
/*     */     } else {
/*     */       
/* 149 */       Vector vector = getEntities(entityGroup);
/* 150 */       if (this.etype.equals("AVAIL")) {
/* 151 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 152 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/* 153 */           paramStringBuffer.append("SAPLGEOElem: AVAILs " + entityItem.getKey() + " " + 
/* 154 */               PokUtils.getAttributeValue(entityItem, "AVAILTYPE", ", ", "", false) + NEWLINE);
/*     */         } 
/*     */       }
/* 157 */       paramStringBuffer.append("SAPLGEOElem: entVct " + vector + NEWLINE);
/*     */       
/* 159 */       if (vector.size() == 0) {
/* 160 */         if (entityGroup.getEntityItemCount() > 0) {
/* 161 */           paramStringBuffer.append("SAPLGEOElem: Warning No " + this.etype + " entity met filter criteria. Cannot gen valid geo tags" + NEWLINE);
/* 162 */           for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 163 */             EntityItem entityItem = entityGroup.getEntityItem(b);
/* 164 */             paramStringBuffer.append(entityItem.getKey() + " did not meet filter for SAPLGEOElem: AVAILTYPE=" + 
/* 165 */                 PokUtils.getAttributeValue(entityItem, "AVAILTYPE", ", ", "", false) + NEWLINE);
/*     */           } 
/*     */         } else {
/*     */           
/* 169 */           paramStringBuffer.append("SAPLGEOElem: ERROR No " + this.etype + " entity in extract, generating empty geo tags" + NEWLINE);
/*     */         } 
/*     */         
/* 172 */         Element element = paramDocument.createElement(this.nodeName);
/* 173 */         addXMLAttrs(element);
/* 174 */         paramElement.appendChild(element);
/*     */         
/* 176 */         addChildren(paramDatabase, paramEntityList, paramDocument, element, (EntityItem)null, (Vector)null, "None", paramStringBuffer);
/*     */       }
/* 178 */       else if (paramEntityList.getEntityGroup(this.assocName) == null) {
/* 179 */         paramStringBuffer.append("SAPLGEOElem: ERROR No " + this.assocName + " entity in extract, generating empty geo tags" + NEWLINE);
/*     */         
/* 181 */         Element element = paramDocument.createElement(this.nodeName);
/* 182 */         addXMLAttrs(element);
/* 183 */         paramElement.appendChild(element);
/*     */         
/* 185 */         addChildren(paramDatabase, paramEntityList, paramDocument, element, (EntityItem)null, (Vector)null, "None", paramStringBuffer);
/* 186 */         vector.clear();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       Hashtable hashtable = groupByCtry(vector, paramStringBuffer);
/* 193 */       for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/*     */         
/* 195 */         String str = enumeration.nextElement();
/* 196 */         Vector<EntityItem> vector1 = (Vector)hashtable.get(str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 207 */         EntityItem entityItem = getGeneralArea(vector1.firstElement(), this.assocName, str, paramStringBuffer);
/*     */         
/* 209 */         if (entityItem == null) {
/* 210 */           paramStringBuffer.append("SAPLGEOElem: WARNING no GENERALAREA item found for ctryflag:" + str + NEWLINE);
/*     */         }
/*     */ 
/*     */         
/* 214 */         Element element = paramDocument.createElement(this.nodeName);
/* 215 */         addXMLAttrs(element);
/* 216 */         paramElement.appendChild(element);
/*     */         
/* 218 */         paramStringBuffer.append("SAPLGEOElem: getting children for ctryflag:" + str + NEWLINE);
/* 219 */         addChildren(paramDatabase, paramEntityList, paramDocument, element, entityItem, vector1, str, paramStringBuffer);
/*     */       } 
/* 221 */       vector.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addChildren(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, EntityItem paramEntityItem, Vector<EntityItem> paramVector, String paramString, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 249 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 250 */       SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 251 */       if (sAPLElem instanceof SAPLItemElem) {
/* 252 */         if ("GENERALAREA".equals(sAPLElem.etype)) {
/* 253 */           ((SAPLItemElem)sAPLElem).addElements(paramEntityItem, paramDocument, paramElement, paramStringBuffer);
/*     */         }
/* 255 */         else if ("GEODATE".equals(sAPLElem.etype)) {
/* 256 */           EntityItem entityItem = null;
/* 257 */           if (paramVector != null) {
/* 258 */             if (paramVector.size() > 0) {
/* 259 */               entityItem = paramVector.firstElement();
/*     */             }
/* 261 */             if (paramVector.size() > 1) {
/* 262 */               paramStringBuffer.append("SAPLGEOElem: Error: more than one GEODATE found for ctryflag: " + paramString + " Using first one " + entityItem
/* 263 */                   .getKey() + NEWLINE);
/*     */             }
/*     */           } 
/* 266 */           ((SAPLItemElem)sAPLElem).addElements(entityItem, paramDocument, paramElement, paramStringBuffer);
/*     */         } else {
/*     */           
/* 269 */           paramStringBuffer.append("SAPLGEOElem: Unrecognized SAPLItemElem " + sAPLElem.etype + NEWLINE);
/* 270 */           sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, paramStringBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 288 */         sAPLElem.addGEOElements(paramVector, paramDocument, paramElement, paramStringBuffer);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Hashtable groupByCtry(Vector<EntityItem> paramVector, StringBuffer paramStringBuffer) {
/* 299 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 300 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 301 */       EntityItem entityItem = paramVector.elementAt(b);
/*     */       
/* 303 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 304 */       if (eANFlagAttribute == null) {
/* 305 */         paramStringBuffer.append(entityItem.getKey() + " did not have a value for COUNTRYLIST" + NEWLINE);
/*     */       }
/*     */       else {
/*     */         
/* 309 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 310 */         for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */           
/* 312 */           if (arrayOfMetaFlag[b1].isSelected()) {
/* 313 */             String str = arrayOfMetaFlag[b1].getFlagCode();
/*     */ 
/*     */             
/* 316 */             Vector<EntityItem> vector = (Vector)hashtable.get(str);
/* 317 */             if (vector == null) {
/* 318 */               vector = new Vector();
/* 319 */               hashtable.put(str, vector);
/*     */             } 
/* 321 */             vector.addElement(entityItem);
/*     */           } 
/*     */         } 
/*     */       } 
/* 325 */     }  return hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityItem getGeneralArea(EntityItem paramEntityItem, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/* 336 */     EntityItem entityItem = null;
/* 337 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, "GENERALAREA");
/*     */ 
/*     */     
/* 340 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "GENAREATYPE", "2452");
/*     */ 
/*     */     
/* 343 */     for (byte b = 0; b < vector1.size(); b++) {
/* 344 */       EntityItem entityItem1 = vector1.elementAt(b);
/* 345 */       if (PokUtils.isSelected(entityItem1, "GENAREANAME", paramString2)) {
/* 346 */         entityItem = entityItem1;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 351 */     return entityItem;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLGEOElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */