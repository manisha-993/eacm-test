/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
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
/*     */ public class XMLZCONFElem
/*     */   extends XMLElem
/*     */ {
/*  58 */   String DEFAULTHIDE = "@@";
/*  59 */   String DEFAULTBUYABLE = "@@";
/*  60 */   String DEFAULTADDTOCART = "@@";
/*  61 */   String DEFAULTCUSTOMIZEABLE = "@@";
/*     */   
/*  63 */   String Hardware = "Hardware";
/*  64 */   String Software = "Software";
/*  65 */   String Service = "Service";
/*     */   
/*     */   private String getDEFAULTADDTOCART() {
/*  68 */     return this.DEFAULTADDTOCART;
/*     */   }
/*     */   
/*     */   private String getDEFAULTBUYABLE() {
/*  72 */     return this.DEFAULTBUYABLE;
/*     */   }
/*     */   
/*     */   private String getDEFAULTCUSTOMIZEABLE() {
/*  76 */     return this.DEFAULTCUSTOMIZEABLE;
/*     */   }
/*     */   private String getDEFAULTHIDE() {
/*  79 */     return this.DEFAULTHIDE;
/*     */   }
/*     */   
/*     */   private void setAllFields(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  83 */     this.DEFAULTHIDE = paramString1;
/*  84 */     this.DEFAULTBUYABLE = paramString2;
/*  85 */     this.DEFAULTADDTOCART = paramString3;
/*  86 */     this.DEFAULTCUSTOMIZEABLE = paramString4;
/*     */   }
/*     */   
/*     */   public XMLZCONFElem() {
/*  90 */     super(null);
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
/* 114 */     String str = paramDiffEntity.getEntityType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*     */     
/* 164 */     if ("MODEL".equals(str)) {
/*     */ 
/*     */       
/* 167 */       setMODELZCONF(entityItem, paramStringBuffer);
/* 168 */     } else if ("SVCMOD".equals(str)) {
/*     */ 
/*     */       
/* 171 */       setSVCMODZCONF(entityItem, paramStringBuffer);
/* 172 */     } else if ("LSEOBUNDLE".equals(str)) {
/* 173 */       setLSEOBUNDLEZCONF(entityItem, paramStringBuffer);
/* 174 */     } else if ("LSEO".equals(str)) {
/* 175 */       setLSEOZCONF(entityItem, paramStringBuffer);
/* 176 */     } else if ("SVCSEO".equals(str)) {
/*     */       
/* 178 */       setSVCSEO(entityItem, paramStringBuffer);
/*     */     } 
/* 180 */     createNodeSet(paramDocument, paramElement, paramStringBuffer);
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
/*     */   private void setSVCSEO(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLSEOZCONF(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 205 */     String str1 = "@@";
/* 206 */     String str2 = "@@";
/* 207 */     String str3 = "@@";
/* 208 */     String str4 = "@@";
/*     */     
/* 210 */     EntityItem entityItem1 = findCOFCAT(paramEntityItem);
/* 211 */     if (entityItem1 != null) {
/* 212 */       str1 = PokUtils.getAttributeValue(entityItem1, "COFCAT", ", ", "@@", false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 217 */     String str5 = PokUtils.getAttributeValue(paramEntityItem, "PRCINDC", ", ", "@@", false);
/* 218 */     String str6 = PokUtils.getAttributeValue(paramEntityItem, "ZEROPRICE", ", ", "@@", false);
/* 219 */     if ("No".equalsIgnoreCase(str5)) {
/* 220 */       str2 = "NotPriced";
/* 221 */     } else if ("Yes".equalsIgnoreCase(str5)) {
/* 222 */       if ("Yes".equalsIgnoreCase(str6)) {
/* 223 */         str2 = "NotPriced";
/*     */       } else {
/* 225 */         str2 = "Priced";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 230 */     String str7 = PokUtils.getAttributeValue(paramEntityItem, "SPECBID", ", ", "@@", false);
/*     */     
/* 232 */     if (!"Yes".equalsIgnoreCase(str7)) {
/* 233 */       str3 = "CustomSEO";
/*     */     } else {
/* 235 */       str3 = "GASEO";
/*     */     } 
/*     */     
/* 238 */     String str8 = "";
/* 239 */     EntityItem entityItem2 = null;
/* 240 */     Vector<EntityItem> vector = paramEntityItem.getUpLink();
/* 241 */     for (byte b = 0; b < vector.size(); b++) {
/* 242 */       EntityItem entityItem = vector.get(b);
/* 243 */       if (entityItem != null && "WWSEOLSEO".equals(entityItem.getEntityType())) {
/* 244 */         entityItem2 = (EntityItem)entityItem.getUpLink(0);
/*     */       }
/*     */     } 
/* 247 */     if (entityItem2 != null) {
/* 248 */       str8 = PokUtils.getAttributeValue(entityItem2, "SEOORDERCODE", ", ", "@@", false);
/*     */     }
/*     */     
/* 251 */     if ("Initial".equalsIgnoreCase(str8)) {
/* 252 */       str4 = "SystemSEO";
/* 253 */     } else if ("MES".equalsIgnoreCase(str8)) {
/* 254 */       str4 = "OptionsSEO";
/*     */     } 
/* 256 */     ABRUtil.append(paramStringBuffer, "XMLZCONFElem.setLSEOZCONF getAttributeValue note1: " + str1 + " COFCAT: " + str1 + NEWLINE);
/* 257 */     ABRUtil.append(paramStringBuffer, "XMLZCONFElem.setLSEOZCONF getAttributeValue note2: " + str2 + " PRCINDC: " + str5 + " ZEROPRICE:" + str6 + NEWLINE);
/* 258 */     ABRUtil.append(paramStringBuffer, "XMLZCONFElem.setLSEOZCONF getAttributeValue note3: " + str3 + " SPECBID: " + str7 + NEWLINE);
/* 259 */     ABRUtil.append(paramStringBuffer, "XMLZCONFElem.setLSEOZCONF getAttributeValue note4: " + str4 + " SEOORDERCODE: " + str8 + NEWLINE);
/* 260 */     if (this.Hardware.equalsIgnoreCase(str1)) {
/* 261 */       if ("OptionsSEO".equalsIgnoreCase(str4)) {
/*     */         
/* 263 */         setAllFields("N", "Y", "Y", "N");
/* 264 */       } else if ("SystemSEO".equalsIgnoreCase(str4)) {
/*     */ 
/*     */         
/* 267 */         if ("Priced".equalsIgnoreCase(str2)) {
/*     */           
/* 269 */           setAllFields("N", "Y", "Y", "Y");
/*     */         } else {
/*     */           
/* 272 */           setAllFields("Y", "N", "N", "N");
/*     */         } 
/*     */       } 
/* 275 */     } else if (this.Software.equalsIgnoreCase(str1)) {
/* 276 */       if ("OptionsSEO".equalsIgnoreCase(str4)) {
/*     */         
/* 278 */         setAllFields("N", "Y", "Y", "N");
/*     */ 
/*     */       
/*     */       }
/* 282 */       else if ("Priced".equalsIgnoreCase(str2)) {
/*     */         
/* 284 */         setAllFields("N", "Y", "Y", "N");
/*     */       } else {
/*     */         
/* 287 */         setAllFields("Y", "N", "N", "N");
/*     */       }
/*     */     
/* 290 */     } else if (this.Service.equalsIgnoreCase(str1)) {
/* 291 */       if ("Priced".equalsIgnoreCase(str2)) {
/*     */         
/* 293 */         setAllFields("N", "Y", "Y", "N");
/*     */       } else {
/*     */         
/* 296 */         setAllFields("Y", "N", "N", "N");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityItem findCOFCAT(EntityItem paramEntityItem) {
/* 306 */     EntityItem entityItem = null;
/* 307 */     if (paramEntityItem != null) {
/*     */       
/* 309 */       Vector<EntityItem> vector = paramEntityItem.getUpLink();
/* 310 */       for (byte b = 0; b < vector.size(); b++) {
/* 311 */         EntityItem entityItem1 = vector.get(b);
/* 312 */         if (entityItem1 != null && "WWSEOLSEO".equalsIgnoreCase(entityItem1.getEntityType())) {
/* 313 */           EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 314 */           if (entityItem2 != null && "WWSEO".equalsIgnoreCase(entityItem2.getEntityType())) {
/* 315 */             Vector<EntityItem> vector1 = entityItem2.getUpLink();
/* 316 */             for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 317 */               EntityItem entityItem3 = vector1.get(b1);
/* 318 */               if (entityItem3 != null && "MODELWWSEO".equalsIgnoreCase(entityItem3.getEntityType())) {
/* 319 */                 entityItem = (EntityItem)entityItem3.getUpLink(0);
/*     */                 // Byte code: goto -> 156
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 327 */     return entityItem;
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
/*     */   private void setLSEOBUNDLEZCONF(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 342 */     String str1, str2 = PokUtils.getAttributeValue(paramEntityItem, "SPECBID", ", ", "@@", false);
/*     */     
/* 344 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, "BUNDLETYPE", ",", "@@", false);
/* 345 */     if ("CHEAT".equals(str3) || str3.indexOf(this.Hardware) > -1) {
/* 346 */       str1 = this.Hardware;
/* 347 */     } else if (str3.indexOf(this.Software) > -1) {
/* 348 */       str1 = this.Software;
/*     */     } else {
/* 350 */       str1 = this.Service;
/*     */     } 
/*     */     
/* 353 */     ABRUtil.append(paramStringBuffer, "XMLZCONFElem.setLSEOBUNDLEZCONF getAttributeValue note1: " + str1 + " BUNDLETYPE: " + str3 + NEWLINE);
/* 354 */     ABRUtil.append(paramStringBuffer, "XMLZCONFElem.setLSEOBUNDLEZCONF getAttributeValue SPECBID: " + str2 + NEWLINE);
/*     */     
/* 356 */     if (this.Hardware.equalsIgnoreCase(str1)) {
/*     */       
/* 358 */       setAllFields("N", "Y", "Y", "Y");
/*     */     } else {
/*     */       
/* 361 */       setAllFields("N", "Y", "Y", "N");
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
/*     */   private void setSVCMODZCONF(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 374 */     String str = PokUtils.getAttributeValue(paramEntityItem, "OFERCONFIGTYPE", ", ", "@@", false);
/* 375 */     ABRUtil.append(paramStringBuffer, "XMLZCONFElem.setSVCMODZCONF getAttributeValue note7: " + str + " OFERCONFIGTYPE: " + str + NEWLINE);
/* 376 */     setAllFields("N", "Y", "Y", "N");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setMODELZCONF(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 420 */     String str2, str1 = PokUtils.getAttributeValue(paramEntityItem, "COFCAT", ", ", "@@", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 427 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("SUPRTCONFIGURATOR");
/* 428 */     if (eANFlagAttribute != null && eANFlagAttribute.isSelected("SPTC3")) {
/* 429 */       str2 = "InIPC";
/*     */     } else {
/* 431 */       str2 = "NotInIPC";
/*     */     } 
/*     */ 
/*     */     
/* 435 */     ABRUtil.append(paramStringBuffer, "XMLZCONFElem.setMODELZCONF getAttributeValue note1: " + str1 + " COFCAT: " + str1 + NEWLINE);
/* 436 */     ABRUtil.append(paramStringBuffer, "XMLZCONFElem.setMODELZCONF getAttributeValue note5: " + str2 + NEWLINE);
/*     */     
/* 438 */     if (this.Hardware.equalsIgnoreCase(str1) || this.Software.equalsIgnoreCase(str1)) {
/* 439 */       if ("InIPC".equalsIgnoreCase(str2)) {
/*     */         
/* 441 */         setAllFields("N", "N", "N", "Y");
/*     */       } else {
/*     */         
/* 444 */         setAllFields("N", "N", "N", "N");
/*     */       }
/*     */     
/* 447 */     } else if (this.Service.equalsIgnoreCase(str1)) {
/*     */       
/* 449 */       setAllFields("Y", "N", "N", "N");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 531 */     Element element = paramDocument.createElement("DEFAULTADDTOCART");
/* 532 */     element.appendChild(paramDocument.createTextNode("" + getDEFAULTADDTOCART()));
/* 533 */     paramElement.appendChild(element);
/* 534 */     element = paramDocument.createElement("DEFAULTBUYABLE");
/* 535 */     element.appendChild(paramDocument.createTextNode("" + getDEFAULTBUYABLE()));
/* 536 */     paramElement.appendChild(element);
/* 537 */     element = paramDocument.createElement("DEFAULTCUSTOMIZEABLE");
/* 538 */     element.appendChild(paramDocument.createTextNode("" + getDEFAULTCUSTOMIZEABLE()));
/* 539 */     paramElement.appendChild(element);
/* 540 */     element = paramDocument.createElement("DEFAULTHIDE");
/* 541 */     element.appendChild(paramDocument.createTextNode("" + getDEFAULTHIDE()));
/* 542 */     paramElement.appendChild(element);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLZCONFElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */