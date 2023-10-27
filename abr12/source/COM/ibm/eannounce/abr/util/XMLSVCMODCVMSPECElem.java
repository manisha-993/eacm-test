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
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLSVCMODCVMSPECElem
/*     */   extends XMLElem
/*     */ {
/*  59 */   private String path = null;
/*  60 */   private String etype = null;
/*     */   
/*     */   public XMLSVCMODCVMSPECElem(String paramString) {
/*  63 */     super(paramString);
/*  64 */     this.path = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLSVCMODCVMSPECElem(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  69 */     super(paramString1, paramString2);
/*  70 */     this.path = paramString4;
/*  71 */     this.etype = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasChanges(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  82 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  83 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  84 */     boolean bool = false;
/*  85 */     String str1 = "";
/*  86 */     String str2 = "";
/*     */     
/*  88 */     if (entityItem2 != null) {
/*  89 */       str2 = getCONTENTS(entityItem2, paramStringBuffer);
/*     */     }
/*     */     
/*  92 */     if (entityItem1 != null) {
/*  93 */       str1 = getCONTENTS(entityItem1, paramStringBuffer);
/*     */     }
/*  95 */     if (!str1.equals(str2)) {
/*  96 */       bool = true;
/*     */     }
/*     */     
/*  99 */     return bool;
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
/* 123 */     String str1 = "";
/* 124 */     String str2 = paramDiffEntity.getEntityType();
/* 125 */     ABRUtil.append(paramStringBuffer, "XMLSVCMODCVMSPECElem entitytype =" + str2 + NEWLINE);
/* 126 */     if (str2.equals("CVMSPEC")) {
/* 127 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 128 */       str1 = getCONTENTS(entityItem, paramStringBuffer);
/*     */     } 
/*     */     
/* 131 */     createNodeSet(paramDocument, paramElement, str1, paramStringBuffer);
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
/*     */   private String getCONTENTS(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 150 */     boolean bool = false;
/* 151 */     String str1 = "";
/* 152 */     String str2 = "";
/* 153 */     if (paramEntityItem != null) {
/* 154 */       if (this.path != null) {
/* 155 */         Vector<EntityItem> vector = getPathEntity(paramEntityItem, paramStringBuffer);
/* 156 */         for (byte b = 0; b < vector.size(); b++) {
/* 157 */           EntityItem entityItem = vector.elementAt(b);
/* 158 */           str2 = PokUtils.getAttributeFlagValue(entityItem, this.attrCode);
/* 159 */           if (str2.equalsIgnoreCase("C1")) {
/* 160 */             str1 = PokUtils.getAttributeValue(paramEntityItem, "CHARVAL", ", ", "@@", false);
/* 161 */           } else if (str2.equalsIgnoreCase("C2")) {
/* 162 */             str1 = PokUtils.getAttributeValue(paramEntityItem, "VMSPECID", ", ", "@@", false);
/*     */           } 
/*     */         } 
/* 165 */         ABRUtil.append(paramStringBuffer, "XMLSVCMODCVMSPECElem get from Path CVMTYPE:" + str2 + " cvmspecID:" + str1 + NEWLINE);
/*     */       } else {
/* 167 */         Vector<EntityItem> vector = paramEntityItem.getUpLink();
/* 168 */         for (byte b = 0; b < vector.size(); b++) {
/* 169 */           EntityItem entityItem = vector.get(b);
/* 170 */           if (entityItem != null && "CVMCVMSPEC".equals(entityItem.getEntityType())) {
/*     */             
/* 172 */             EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/* 173 */             if (entityItem1 != null) {
/* 174 */               str2 = PokUtils.getAttributeFlagValue(entityItem1, "CVMTYPE");
/* 175 */               if (str2 != null && str2.equalsIgnoreCase("C1")) {
/* 176 */                 str1 = PokUtils.getAttributeValue(paramEntityItem, "CHARACID", ", ", "@@", false);
/* 177 */                 bool = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 183 */         if (!bool) {
/* 184 */           str1 = PokUtils.getAttributeValue(paramEntityItem, "VMSPECID", ", ", "@@", false);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 189 */     return str1;
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
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, String paramString, StringBuffer paramStringBuffer) {
/* 202 */     Element element = paramDocument.createElement(this.nodeName);
/* 203 */     element.appendChild(paramDocument.createTextNode("" + paramString));
/* 204 */     paramElement.appendChild(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getPathEntity(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 214 */     Vector<EntityItem> vector1 = new Vector(1);
/* 215 */     Vector<EntityItem> vector2 = new Vector(1);
/* 216 */     vector1.add(paramEntityItem);
/* 217 */     StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 218 */     while (stringTokenizer.hasMoreTokens()) {
/* 219 */       String str1 = stringTokenizer.nextToken();
/* 220 */       String str2 = this.etype;
/* 221 */       if (stringTokenizer.hasMoreTokens()) {
/* 222 */         str2 = stringTokenizer.nextToken();
/*     */       }
/* 224 */       ABRUtil.append(paramStringBuffer, "XMLSVCMODCVMSPECElem.getItems: node:" + this.nodeName + " path:" + this.path + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */       
/* 227 */       Vector<EntityItem> vector = new Vector();
/* 228 */       for (byte b = 0; b < vector1.size(); b++) {
/* 229 */         EntityItem entityItem = vector1.elementAt(b);
/*     */         
/* 231 */         Vector<EntityItem> vector3 = null;
/* 232 */         if (str1.equals("D")) {
/* 233 */           vector3 = entityItem.getDownLink();
/*     */         } else {
/* 235 */           vector3 = entityItem.getUpLink();
/*     */         } 
/* 237 */         for (byte b1 = 0; b1 < vector3.size(); b1++) {
/* 238 */           EntityItem entityItem1 = vector3.elementAt(b1);
/*     */           
/* 240 */           if (entityItem1.getEntityType().equals(str2)) {
/* 241 */             if (stringTokenizer.hasMoreTokens()) {
/*     */               
/* 243 */               vector.add(entityItem1);
/*     */             } else {
/*     */               
/* 246 */               vector2.add(entityItem1);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 252 */       vector1.clear();
/* 253 */       vector1 = vector;
/*     */     } 
/*     */     
/* 256 */     vector1.clear();
/* 257 */     return vector2;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLSVCMODCVMSPECElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */