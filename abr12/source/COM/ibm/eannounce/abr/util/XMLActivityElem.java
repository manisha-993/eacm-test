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
/*     */ public class XMLActivityElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLActivityElem(String paramString) {
/*  46 */     super(paramString);
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
/*  70 */     D.ebug(0, "Working on the item:" + this.nodeName);
/*  71 */     Element element = paramDocument.createElement(this.nodeName);
/*  72 */     addXMLAttrs(element);
/*     */     
/*  74 */     element.appendChild(paramDocument.createTextNode("Update"));
/*  75 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/*  78 */     for (byte b = 0; b < this.childVct.size(); b++) {
/*  79 */       XMLElem xMLElem = this.childVct.elementAt(b);
/*  80 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
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
/* 105 */     D.ebug(0, "Working on the item:" + this.nodeName);
/* 106 */     Element element = paramDocument.createElement(this.nodeName);
/* 107 */     addXMLAttrs(element);
/*     */     
/* 109 */     element.appendChild(paramDocument.createTextNode(paramDiffEntity.isDeleted() ? "Delete" : "Update"));
/* 110 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/* 113 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 114 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 115 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLActivityElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */