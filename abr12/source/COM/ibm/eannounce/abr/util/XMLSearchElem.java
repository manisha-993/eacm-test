/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
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
/*     */ public class XMLSearchElem
/*     */   extends XMLElem
/*     */ {
/*     */   private String searchAction;
/*  39 */   private Vector srcVct = new Vector(1);
/*  40 */   private Vector sinkVct = new Vector(1);
/*     */ 
/*     */ 
/*     */   
/*     */   private String srchType;
/*     */ 
/*     */   
/*     */   private boolean useListSrch = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSearchElem(String paramString1, String paramString2, String paramString3) {
/*  52 */     this(paramString1, paramString2, paramString3, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLSearchElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  59 */     super(paramString1);
/*  60 */     this.searchAction = paramString2;
/*  61 */     this.srchType = paramString3;
/*  62 */     this.useListSrch = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSearchAttr(String paramString1, String paramString2) {
/*  71 */     this.srcVct.addElement(paramString1);
/*  72 */     this.sinkVct.addElement(paramString2);
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
/*  95 */     Element element = paramDocument.createElement(this.nodeName);
/*  96 */     addXMLAttrs(element);
/*     */     
/*  98 */     String str = "@@";
/*     */     
/*     */     try {
/* 101 */       EntityItem[] arrayOfEntityItem = doSearch(paramDatabase, paramEntityItem, paramStringBuffer);
/* 102 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 103 */         str = "" + arrayOfEntityItem[0].getEntityID();
/*     */       }
/* 105 */     } catch (SBRException sBRException) {
/* 106 */       throw new MiddlewareException(sBRException.toString());
/*     */     } 
/*     */     
/* 109 */     element.appendChild(paramDocument.createTextNode(str));
/* 110 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/* 113 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 114 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 115 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
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
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 140 */     Element element = paramDocument.createElement(this.nodeName);
/* 141 */     addXMLAttrs(element);
/*     */     
/* 143 */     String str = "@@";
/* 144 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 145 */     if (paramDiffEntity.isDeleted()) {
/* 146 */       entityItem = paramDiffEntity.getPriorEntityItem();
/*     */     }
/*     */     
/*     */     try {
/* 150 */       EntityItem[] arrayOfEntityItem = doSearch(paramDatabase, entityItem, paramStringBuffer);
/* 151 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 152 */         str = "" + arrayOfEntityItem[0].getEntityID();
/*     */       }
/* 154 */     } catch (SBRException sBRException) {
/* 155 */       throw new MiddlewareException(sBRException.toString());
/*     */     } 
/*     */     
/* 158 */     element.appendChild(paramDocument.createTextNode(str));
/* 159 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/* 162 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 163 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 164 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
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
/*     */   protected EntityItem[] doSearch(Database paramDatabase, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 180 */     PDGUtility pDGUtility = new PDGUtility();
/*     */     
/* 182 */     StringBuffer stringBuffer = new StringBuffer();
/* 183 */     for (byte b = 0; b < this.srcVct.size(); b++) {
/* 184 */       if (stringBuffer.length() > 0) {
/* 185 */         stringBuffer.append(";");
/*     */       }
/* 187 */       String str1 = this.srcVct.elementAt(b).toString();
/* 188 */       String str2 = this.sinkVct.elementAt(b).toString();
/* 189 */       stringBuffer.append("map_" + str2 + "=" + PokUtils.getAttributeValue(paramEntityItem, str1, ", ", "", false));
/*     */     } 
/*     */     
/* 192 */     ABRUtil.append(paramStringBuffer, "XMLSearchElem.doSearch Using " + this.searchAction + ", useListSrch: " + this.useListSrch + " to search for " + this.srchType + " using " + stringBuffer.toString() + NEWLINE);
/*     */     
/* 194 */     EntityItem[] arrayOfEntityItem = null;
/* 195 */     if (!this.useListSrch) {
/* 196 */       arrayOfEntityItem = pDGUtility.dynaSearch(paramDatabase, paramEntityItem.getProfile(), null, this.searchAction, this.srchType, stringBuffer.toString());
/*     */     } else {
/* 198 */       EntityList entityList = pDGUtility.dynaSearchIIForEntityList(paramDatabase, paramEntityItem.getProfile(), null, this.searchAction, this.srchType, stringBuffer
/* 199 */           .toString());
/*     */       
/* 201 */       EntityGroup entityGroup = entityList.getEntityGroup(this.srchType);
/* 202 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/* 203 */         arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*     */       } else {
/* 205 */         ABRUtil.append(paramStringBuffer, "XMLSearchElem.doSearch No " + this.srchType + " found" + NEWLINE);
/*     */       } 
/*     */     } 
/* 208 */     return arrayOfEntityItem;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLSearchElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */