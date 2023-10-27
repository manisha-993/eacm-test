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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLLSEOActivityElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLLSEOActivityElem(String paramString) {
/*  49 */     super(paramString);
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
/*  73 */     Element element = paramDocument.createElement(this.nodeName);
/*  74 */     addXMLAttrs(element);
/*     */     
/*  76 */     element.appendChild(paramDocument.createTextNode("Update"));
/*  77 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/*  80 */     for (byte b = 0; b < this.childVct.size(); b++) {
/*  81 */       XMLElem xMLElem = this.childVct.elementAt(b);
/*  82 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
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
/* 107 */     String str1 = "WARRRELATOR";
/* 108 */     String str2 = "Update";
/* 109 */     Element element = paramDocument.createElement(this.nodeName);
/* 110 */     addXMLAttrs(element);
/*     */     
/* 112 */     String str3 = str1 + paramDiffEntity.getKey();
/* 113 */     String[] arrayOfString = (String[])paramHashtable.get(str3);
/* 114 */     if (arrayOfString != null) {
/* 115 */       str2 = arrayOfString[2];
/* 116 */       ABRUtil.append(paramStringBuffer, "XMLLSEOActivityElem.addElements: get from hashtable: " + str3 + " relator :" + arrayOfString[2] + NEWLINE);
/*     */     } 
/* 118 */     element.appendChild(paramDocument.createTextNode(str2));
/* 119 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/* 122 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 123 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 124 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLLSEOActivityElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */