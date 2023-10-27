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
/*     */ public class XMLVMElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLVMElem(String paramString1, String paramString2) {
/*  47 */     super(paramString1, paramString2);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  75 */     StringBuffer stringBuffer = new StringBuffer();
/*  76 */     if (this.attrCode.equals("1")) {
/*  77 */       stringBuffer.append(this.nodeName);
/*  78 */       stringBuffer.append(" Version ");
/*  79 */       stringBuffer.append("1");
/*  80 */       stringBuffer.append(" Mod ");
/*  81 */       stringBuffer.append("0");
/*  82 */     } else if (this.attrCode.equals("0")) {
/*  83 */       stringBuffer.append(this.nodeName);
/*  84 */       stringBuffer.append(" Version ");
/*  85 */       stringBuffer.append("0");
/*  86 */       stringBuffer.append(" Mod ");
/*  87 */       stringBuffer.append("5");
/*     */     } 
/*  89 */     paramElement.appendChild(paramDocument.createComment(stringBuffer.toString()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 119 */     StringBuffer stringBuffer = new StringBuffer();
/* 120 */     if (this.attrCode.equals("1")) {
/* 121 */       stringBuffer.append(this.nodeName);
/* 122 */       stringBuffer.append(" Version ");
/* 123 */       stringBuffer.append("1");
/* 124 */       stringBuffer.append(" Mod ");
/* 125 */       stringBuffer.append("0");
/* 126 */     } else if (this.attrCode.equals("0")) {
/* 127 */       stringBuffer.append(this.nodeName);
/* 128 */       stringBuffer.append(" Version ");
/* 129 */       stringBuffer.append("0");
/* 130 */       stringBuffer.append(" Mod ");
/* 131 */       stringBuffer.append("5");
/*     */     } 
/* 133 */     paramElement.appendChild(paramDocument.createComment(stringBuffer.toString()));
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLVMElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */