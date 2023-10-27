/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLNLSElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLNLSElem(String paramString) {
/*  69 */     super(paramString);
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  92 */     D.ebug(0, "Working on the item:" + this.nodeName);
/*  93 */     Profile profile = paramEntityList.getProfile();
/*     */     
/*  95 */     profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*  96 */     ABRUtil.append(paramStringBuffer, "XMLNLSElem.addElements node:" + this.nodeName + " " + paramEntityItem.getKey() + " ReadLanguage() " + profile.getReadLanguage() + NEWLINE);
/*     */     
/*  98 */     super.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, paramEntityItem, paramStringBuffer);
/*     */ 
/*     */     
/* 101 */     for (byte b = 0; b < profile.getReadLanguages().size(); b++) {
/* 102 */       NLSItem nLSItem = profile.getReadLanguage(b);
/* 103 */       if (nLSItem.getNLSID() != 1) {
/*     */ 
/*     */ 
/*     */         
/* 107 */         ABRUtil.append(paramStringBuffer, "XMLNLSElem.addElements node:" + this.nodeName + " " + paramEntityItem.getKey() + " ReadLanguage[" + b + "] " + nLSItem + NEWLINE);
/* 108 */         profile.setReadLanguage(b);
/*     */         
/* 110 */         if (hasNodeValueForNLS(paramEntityItem, paramStringBuffer)) {
/*     */           
/* 112 */           super.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, paramEntityItem, paramStringBuffer);
/*     */         } else {
/* 114 */           ABRUtil.append(paramStringBuffer, "XMLNLSElem.addElements node:" + this.nodeName + " " + paramEntityItem.getKey() + " ReadLanguage[" + b + "] " + nLSItem + " does not have any node values" + NEWLINE);
/*     */         } 
/*     */       } 
/*     */     } 
/* 118 */     profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
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
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 143 */     D.ebug(0, "Working on the item:" + this.nodeName);
/* 144 */     Profile profile1 = null;
/* 145 */     Profile profile2 = null;
/* 146 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 147 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 148 */     if (entityItem1 != null) {
/* 149 */       profile1 = entityItem1.getProfile();
/*     */     }
/* 151 */     if (entityItem2 != null) {
/* 152 */       profile2 = entityItem2.getProfile();
/*     */     }
/*     */     
/* 155 */     Profile profile3 = (profile1 == null) ? profile2 : profile1;
/*     */ 
/*     */     
/* 158 */     for (byte b = 0; b < profile3.getReadLanguages().size(); b++) {
/* 159 */       NLSItem nLSItem = profile3.getReadLanguage(b);
/*     */       
/* 161 */       ABRUtil.append(paramStringBuffer, "XMLNLSElem.addElements node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage[" + b + "] " + nLSItem + NEWLINE);
/* 162 */       if (profile1 != null) {
/* 163 */         profile1.setReadLanguage(b);
/*     */       }
/* 165 */       if (profile2 != null) {
/* 166 */         profile2.setReadLanguage(b);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 171 */       if (hasNodeValueChgForNLS(paramDiffEntity, paramStringBuffer)) {
/*     */         
/* 173 */         Element element = paramDocument.createElement(this.nodeName);
/* 174 */         addXMLAttrs(element);
/* 175 */         if (paramElement == null) {
/* 176 */           paramDocument.appendChild(element);
/*     */         } else {
/* 178 */           paramElement.appendChild(element);
/*     */         } 
/*     */         
/* 181 */         Node node = getContentNode(paramDocument, paramDiffEntity, paramElement, paramStringBuffer);
/* 182 */         if (node != null) {
/* 183 */           element.appendChild(node);
/*     */         }
/*     */ 
/*     */         
/* 187 */         for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 188 */           XMLElem xMLElem = this.childVct.elementAt(b1);
/* 189 */           xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */         } 
/*     */       } else {
/* 192 */         ABRUtil.append(paramStringBuffer, "XMLNLSElem.addElements node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage[" + b + "] " + nLSItem + " does not have any node value chgs" + NEWLINE);
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     if (profile1 != null) {
/* 197 */       profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*     */     }
/* 199 */     if (profile2 != null) {
/* 200 */       profile2.setReadLanguage(Profile.ENGLISH_LANGUAGE);
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
/*     */   protected boolean hasChanges(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 214 */     boolean bool = false;
/* 215 */     Profile profile1 = null;
/* 216 */     Profile profile2 = null;
/* 217 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 218 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 219 */     if (entityItem1 != null) {
/* 220 */       profile1 = entityItem1.getProfile();
/*     */     }
/* 222 */     if (entityItem2 != null) {
/* 223 */       profile2 = entityItem2.getProfile();
/*     */     }
/*     */     
/* 226 */     Profile profile3 = (profile1 == null) ? profile2 : profile1;
/*     */ 
/*     */     
/* 229 */     for (byte b = 0; b < profile3.getReadLanguages().size(); b++) {
/* 230 */       NLSItem nLSItem = profile3.getReadLanguage(b);
/*     */       
/* 232 */       ABRUtil.append(paramStringBuffer, "XMLNLSElem.hasChanges node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage[" + b + "] " + nLSItem + NEWLINE);
/* 233 */       if (profile1 != null) {
/* 234 */         profile1.setReadLanguage(b);
/*     */       }
/* 236 */       if (profile2 != null) {
/* 237 */         profile2.setReadLanguage(b);
/*     */       }
/*     */ 
/*     */       
/* 241 */       if (hasNodeValueChgForNLS(paramDiffEntity, paramStringBuffer)) {
/* 242 */         bool = true;
/*     */         break;
/*     */       } 
/* 245 */       ABRUtil.append(paramStringBuffer, "XMLNLSElem.hasChanges node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage[" + b + "] " + nLSItem + " does not have any changed node values" + NEWLINE);
/*     */     } 
/*     */ 
/*     */     
/* 249 */     if (profile1 != null) {
/* 250 */       profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*     */     }
/* 252 */     if (profile2 != null) {
/* 253 */       profile2.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*     */     }
/*     */     
/* 256 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasChanges(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 267 */     boolean bool = false;
/* 268 */     Profile profile1 = null;
/* 269 */     Profile profile2 = null;
/* 270 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 271 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 272 */     if (entityItem1 != null) {
/* 273 */       profile1 = entityItem1.getProfile();
/*     */     }
/* 275 */     if (entityItem2 != null) {
/* 276 */       profile2 = entityItem2.getProfile();
/*     */     }
/*     */     
/* 279 */     Profile profile3 = (profile1 == null) ? profile2 : profile1;
/*     */ 
/*     */     
/* 282 */     for (byte b = 0; b < profile3.getReadLanguages().size(); b++) {
/* 283 */       NLSItem nLSItem = profile3.getReadLanguage(b);
/*     */       
/* 285 */       ABRUtil.append(paramStringBuffer, "XMLNLSElem.hasChanges node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage[" + b + "] " + nLSItem + NEWLINE);
/* 286 */       if (profile1 != null) {
/* 287 */         profile1.setReadLanguage(b);
/*     */       }
/* 289 */       if (profile2 != null) {
/* 290 */         profile2.setReadLanguage(b);
/*     */       }
/*     */ 
/*     */       
/* 294 */       if (hasNodeValueChgForNLS(paramDiffEntity, paramStringBuffer)) {
/* 295 */         bool = true;
/*     */         break;
/*     */       } 
/* 298 */       ABRUtil.append(paramStringBuffer, "XMLNLSElem.hasChanges node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage[" + b + "] " + nLSItem + " does not have any changed node values" + NEWLINE);
/*     */     } 
/*     */ 
/*     */     
/* 302 */     if (profile1 != null) {
/* 303 */       profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*     */     }
/* 305 */     if (profile2 != null) {
/* 306 */       profile2.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*     */     }
/*     */     
/* 309 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLNLSElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */