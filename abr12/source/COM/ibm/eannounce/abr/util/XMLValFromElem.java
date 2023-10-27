/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
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
/*     */ public class XMLValFromElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLValFromElem(String paramString) {
/*  35 */     super(paramString);
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
/*  59 */     Element element = paramDocument.createElement(this.nodeName);
/*  60 */     addXMLAttrs(element);
/*     */     
/*  62 */     String str = paramEntityList.getProfile().getValOn();
/*     */     
/*  64 */     element.appendChild(paramDocument.createTextNode(str));
/*  65 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/*  68 */     for (byte b = 0; b < this.childVct.size(); b++) {
/*  69 */       XMLElem xMLElem = this.childVct.elementAt(b);
/*  70 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
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
/*  96 */     Element element = paramDocument.createElement(this.nodeName);
/*  97 */     addXMLAttrs(element);
/*     */     
/*  99 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 100 */     if (entityItem == null) {
/* 101 */       paramDiffEntity.getPriorEntityItem();
/*     */     }
/*     */     
/* 104 */     String str = entityItem.getEntityGroup().getEntityList().getProfile().getValOn();
/*     */     
/* 106 */     element.appendChild(paramDocument.createTextNode(str));
/* 107 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/* 110 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 111 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 112 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLValFromElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */