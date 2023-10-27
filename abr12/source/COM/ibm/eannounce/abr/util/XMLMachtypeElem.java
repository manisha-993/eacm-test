/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
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
/*     */ public class XMLMachtypeElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLMachtypeElem(String paramString1, String paramString2) {
/*  34 */     super(paramString1, paramString2, 0);
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
/*  59 */     Element element = paramDocument.createElement(this.nodeName);
/*  60 */     addXMLAttrs(element);
/*     */     
/*  62 */     if (paramElement == null) {
/*  63 */       paramDocument.appendChild(element);
/*     */     } else {
/*  65 */       paramElement.appendChild(element);
/*     */     } 
/*     */     
/*  68 */     Node node = getContentNode(paramDocument, paramEntityItem, paramElement, paramStringBuffer);
/*  69 */     if (!"@@".equals(node.getNodeValue())) {
/*  70 */       D.ebug(0, "Bing MACHTYPEART is not empty");
/*  71 */       element.appendChild(node);
/*     */     } else {
/*  73 */       D.ebug(0, "Bing MACHTYPEART is empty, try to re-create it");
/*  74 */       Profile profile = paramEntityItem.getEntityGroup().getEntityList().getProfile();
/*  75 */       D.ebug(0, "Bing profile valon : effon : " + profile.getValOn() + " : " + profile.getEffOn());
/*  76 */       EntityList entityList = paramDatabase.getEntityList(profile, new ExtractActionItem(null, paramDatabase, profile, "dummy"), new EntityItem[] { new EntityItem(null, profile, paramEntityItem
/*     */ 
/*     */               
/*  79 */               .getEntityType(), paramEntityItem.getEntityID()) });
/*     */       
/*  81 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*  82 */       D.ebug(0, "Bing get modelEntity " + entityItem.getKey());
/*  83 */       D.ebug(0, "Bing get machtype value : " + PokUtils.getAttributeValue(entityItem, "MACHTYPEATR", ", ", "", false));
/*  84 */       node = getContentNode(paramDocument, entityItem, paramElement, paramStringBuffer);
/*  85 */       if (node != null) {
/*  86 */         element.appendChild(node);
/*     */       }
/*     */     } 
/*     */     
/*  90 */     for (byte b = 0; b < this.childVct.size(); b++) {
/*  91 */       XMLElem xMLElem = this.childVct.elementAt(b);
/*  92 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
/*     */     } 
/*     */     
/*  95 */     if (!element.hasChildNodes())
/*     */     {
/*  97 */       element.appendChild(paramDocument.createTextNode("@@"));
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
/* 123 */     Element element = paramDocument.createElement(this.nodeName);
/* 124 */     addXMLAttrs(element);
/* 125 */     if (paramElement == null) {
/* 126 */       paramDocument.appendChild(element);
/*     */     } else {
/* 128 */       paramElement.appendChild(element);
/*     */     } 
/*     */     
/* 131 */     Node node = getContentNode(paramDocument, paramDiffEntity, paramElement, paramStringBuffer);
/*     */     
/* 133 */     if (!"@@".equals(node.getNodeValue())) {
/* 134 */       D.ebug(0, "Bing MACHTYPEART is not empty");
/* 135 */       element.appendChild(node);
/*     */     } else {
/* 137 */       D.ebug(0, "Bing MACHTYPEART is empty, try to re-create it");
/* 138 */       Profile profile = paramDiffEntity.getCurrentEntityItem().getEntityGroup().getEntityList().getProfile();
/* 139 */       D.ebug(0, "Bing profile valon : effon : " + profile.getValOn() + " : " + profile.getEffOn() + " key:" + paramDiffEntity.getKey());
/* 140 */       EntityList entityList = paramDatabase.getEntityList(profile, new ExtractActionItem(null, paramDatabase, profile, "dummy"), new EntityItem[] { new EntityItem(null, profile, paramDiffEntity
/*     */               
/* 142 */               .getEntityType(), paramDiffEntity.getEntityID()) });
/*     */       
/* 144 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/* 145 */       D.ebug(0, "Bing get modelEntity " + entityItem.getKey());
/* 146 */       D.ebug(0, "Bing get machtype value : " + PokUtils.getAttributeValue(entityItem, "MACHTYPEATR", ", ", "", false));
/* 147 */       node = getContentNode(paramDocument, entityItem, paramElement, paramStringBuffer);
/* 148 */       if (node != null) {
/* 149 */         element.appendChild(node);
/*     */       }
/*     */     } 
/*     */     
/* 153 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 154 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 155 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */     
/* 158 */     if (!element.hasChildNodes())
/*     */     {
/* 160 */       element.appendChild(paramDocument.createTextNode("@@"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLMachtypeElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */