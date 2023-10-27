/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
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
/*     */ public class XMLChgSetElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLChgSetElem(String paramString) {
/*  54 */     super(paramString);
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
/*  79 */     D.ebug(0, "Working on the item:" + this.nodeName);
/*     */ 
/*     */     
/*  82 */     if (hasChanges(paramHashtable, paramDiffEntity, paramStringBuffer)) {
/*     */       
/*  84 */       Element element = paramDocument.createElement(this.nodeName);
/*  85 */       addXMLAttrs(element);
/*  86 */       if (paramElement == null) {
/*  87 */         paramDocument.appendChild(element);
/*     */       } else {
/*  89 */         paramElement.appendChild(element);
/*     */       } 
/*     */       
/*  92 */       Node node = getContentNode(paramDocument, paramDiffEntity, paramElement, paramStringBuffer);
/*  93 */       if (node != null) {
/*  94 */         element.appendChild(node);
/*     */       }
/*     */ 
/*     */       
/*  98 */       for (byte b = 0; b < this.childVct.size(); b++) {
/*  99 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 100 */         xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */       } 
/*     */     } else {
/* 103 */       ABRUtil.append(paramStringBuffer, "XMLChgSetElem.addElements node:" + this.nodeName + " " + paramDiffEntity.getKey() + " does not have any changed node values" + NEWLINE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLChgSetElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */