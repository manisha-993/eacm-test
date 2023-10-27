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
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
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
/*     */ public class XMLNotificationElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLNotificationElem(String paramString) {
/*  48 */     super(paramString);
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  72 */     D.ebug(0, "Working on the item:" + this.nodeName);
/*  73 */     Element element = paramDocument.createElement(this.nodeName);
/*  74 */     addXMLAttrs(element);
/*     */     
/*  76 */     String str = paramEntityList.getProfile().getLoginTime();
/*     */     
/*  78 */     element.appendChild(paramDocument.createTextNode(str));
/*  79 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/*  82 */     for (byte b = 0; b < this.childVct.size(); b++) {
/*  83 */       XMLElem xMLElem = this.childVct.elementAt(b);
/*  84 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
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
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 110 */     D.ebug(0, "Working on the item:" + this.nodeName);
/* 111 */     Element element = paramDocument.createElement(this.nodeName);
/* 112 */     addXMLAttrs(element);
/*     */     
/* 114 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 115 */     if (entityItem == null) {
/* 116 */       entityItem = paramDiffEntity.getPriorEntityItem();
/*     */     }
/*     */     
/* 119 */     String str = entityItem.getProfile().getLoginTime();
/*     */ 
/*     */ 
/*     */     
/* 123 */     element.appendChild(paramDocument.createTextNode(str));
/* 124 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/* 127 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 128 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 129 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLNotificationElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */