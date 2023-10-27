/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLBundleTypeElem
/*     */   extends XMLElem
/*     */ {
/*  36 */   String BUNDLETYPE = "@@";
/*     */   
/*  38 */   String Hardware = "Hardware";
/*  39 */   String Software = "Software";
/*  40 */   String Service = "Service";
/*     */   
/*     */   private String getBUNDLETYPE() {
/*  43 */     return this.BUNDLETYPE;
/*     */   }
/*     */   
/*     */   private void setBUNDLETYPE(String paramString) {
/*  47 */     this.BUNDLETYPE = paramString;
/*     */   }
/*     */   
/*     */   public XMLBundleTypeElem() {
/*  51 */     super(null);
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
/*  75 */     String str = paramDiffEntity.getEntityType();
/*     */     
/*  77 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  78 */     if ("LSEOBUNDLE".equals(str)) {
/*  79 */       setBundleType(entityItem, paramStringBuffer);
/*     */     }
/*  81 */     createNodeSet(paramDocument, paramElement, paramStringBuffer);
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
/*     */   private void setBundleType(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 104 */     String str1, str2 = PokUtils.getAttributeValue(paramEntityItem, "BUNDLETYPE", ",", "@@", false);
/* 105 */     if (str2.indexOf(this.Hardware) > -1) {
/* 106 */       str1 = this.Hardware;
/* 107 */     } else if (str2.indexOf(this.Software) > -1) {
/* 108 */       str1 = this.Software;
/*     */     } else {
/* 110 */       str1 = this.Service;
/*     */     } 
/* 112 */     setBUNDLETYPE(str1);
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
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) {
/* 124 */     Element element = paramDocument.createElement("BUNDLETYPE");
/* 125 */     element.appendChild(paramDocument.createTextNode("" + getBUNDLETYPE()));
/* 126 */     paramElement.appendChild(element);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLBundleTypeElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */