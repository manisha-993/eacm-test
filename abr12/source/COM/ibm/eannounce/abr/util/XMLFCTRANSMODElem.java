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
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMLFCTRANSMODElem
/*     */   extends XMLElem
/*     */ {
/*     */   private String defvalue;
/*     */   private String attrcode;
/*     */   
/*     */   public XMLFCTRANSMODElem(String paramString1, String paramString2, String paramString3) {
/*  27 */     super(paramString1);
/*  28 */     this.attrcode = paramString2;
/*  29 */     this.defvalue = paramString3;
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  54 */     Element element = paramDocument.createElement(this.nodeName);
/*  55 */     String str1 = "@@";
/*  56 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, this.attrcode, ", ", "@@", false);
/*  57 */     if ("@@".equals(str2)) {
/*  58 */       str1 = this.defvalue;
/*     */     } else {
/*  60 */       str1 = str2;
/*     */     } 
/*     */     
/*  63 */     element.appendChild(paramDocument.createTextNode(str1));
/*  64 */     paramElement.appendChild(element);
/*     */     
/*  66 */     for (byte b = 0; b < this.childVct.size(); b++) {
/*  67 */       XMLElem xMLElem = this.childVct.elementAt(b);
/*  68 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
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
/*  93 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  94 */     if (paramDiffEntity.isDeleted()) {
/*  95 */       entityItem = paramDiffEntity.getPriorEntityItem();
/*     */     }
/*  97 */     Element element = paramDocument.createElement(this.nodeName);
/*  98 */     String str1 = "@@";
/*  99 */     String str2 = PokUtils.getAttributeValue(entityItem, this.attrcode, ", ", "@@", false);
/* 100 */     if ("@@".equals(str2)) {
/* 101 */       str1 = this.defvalue;
/*     */     } else {
/* 103 */       str1 = str2;
/*     */     } 
/*     */     
/* 106 */     element.appendChild(paramDocument.createTextNode(str1));
/* 107 */     paramElement.appendChild(element);
/*     */     
/* 109 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 110 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 111 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLFCTRANSMODElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */