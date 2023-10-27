/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SWFCRENAME30A
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String ABR = "SWFCRENAME30A";
/*     */   
/*     */   public static String getVersion() {
/*  48 */     return "SWFCRENAME30A.java,v 1.4 2008/01/30 19:39:16 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/*  58 */     return getVersion();
/*     */   }
/*     */   
/*  61 */   private EntityGroup m_egSWFeature = null;
/*  62 */   private EntityItem m_eiSWfeature = null;
/*     */   
/*  64 */   private EntityGroup m_egSWProdstruct = null;
/*  65 */   private EntityItem m_eiSWProdStruct = null;
/*  66 */   private StringBuffer errMessage = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  74 */     String str1 = null;
/*  75 */     String str2 = null;
/*     */     
/*  77 */     String str3 = null;
/*     */     
/*     */     try {
/*  80 */       start_ABRBuild();
/*     */ 
/*     */       
/*  83 */       this.m_egSWFeature = this.m_elist.getParentEntityGroup();
/*  84 */       this
/*  85 */         .m_eiSWfeature = (this.m_egSWFeature == null) ? null : this.m_egSWFeature.getEntityItem(0);
/*     */       
/*  87 */       this.m_egSWProdstruct = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/*     */       
/*  89 */       if (this.m_egSWFeature == null) {
/*  90 */         logMessage("SWFCRENAME30A:" + 
/*     */ 
/*     */             
/*  93 */             getVersion() + ":ERROR:1: m_egSWFeature cannot be established.");
/*     */         
/*  95 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/*  98 */       if (this.m_eiSWfeature == null) {
/*  99 */         logMessage("SWFCRENAME30A:" + 
/*     */ 
/*     */             
/* 102 */             getVersion() + ":ERROR:2: m_eiSWfeature cannot be established.");
/*     */         
/* 104 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 107 */       logMessage("SWFCRENAME30A:" + 
/*     */ 
/*     */           
/* 110 */           getVersion() + ":Request to Work on Entity:" + this.m_eiSWfeature
/*     */           
/* 112 */           .getEntityType() + ":" + this.m_eiSWfeature
/*     */           
/* 114 */           .getEntityID());
/*     */       
/* 116 */       setControlBlock();
/*     */       
/* 118 */       logMessage("SWFCRENAME30A:" + 
/*     */ 
/*     */           
/* 121 */           getVersion() + ":Setup Complete:" + this.m_eiSWfeature
/*     */           
/* 123 */           .getEntityType() + ":" + this.m_eiSWfeature
/*     */           
/* 125 */           .getEntityID());
/*     */       
/* 127 */       setDGTitle(setDGName(this.m_eiSWfeature, "SWFCRENAME30A"));
/* 128 */       buildReportHeader();
/*     */ 
/*     */       
/* 131 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */       
/* 135 */       str1 = (getAttributeValue(this.m_eiSWfeature, "FEATURECODE").length() > 0) ? getAttributeValue(this.m_eiSWfeature, "FEATURECODE") : "";
/*     */ 
/*     */       
/* 138 */       if (str1.indexOf("#") >= 0) {
/* 139 */         logMessage("SWFCRENAME30A:" + 
/*     */ 
/*     */             
/* 142 */             getVersion() + ":ERROR:3:dummy featurecode EXISTS and not changed to real FC");
/*     */         
/* 144 */         setReturnCode(-1);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 150 */       if (getReturnCode() == 0) {
/*     */ 
/*     */ 
/*     */         
/* 154 */         String str = (getAttributeValue(this.m_eiSWfeature, "COMNAME").length() > 0) ? getAttributeValue(this.m_eiSWfeature, "COMNAME") : "";
/*     */ 
/*     */         
/* 157 */         str = replaceFCDummy(str1, str);
/*     */         
/* 159 */         setAttrValue(this.m_eiSWfeature, "COMNAME", str);
/*     */ 
/*     */         
/* 162 */         logMessage("Checking SWPRODStruct: found " + this.m_egSWProdstruct
/*     */             
/* 164 */             .getEntityItemCount());
/* 165 */         byte b = 0;
/* 166 */         for (; b < this.m_egSWProdstruct.getEntityItemCount(); 
/* 167 */           b++) {
/* 168 */           this.m_eiSWProdStruct = this.m_egSWProdstruct.getEntityItem(b);
/*     */ 
/*     */ 
/*     */           
/* 172 */           str2 = (getAttributeValue(this.m_eiSWProdStruct, "COMNAME").length() > 0) ? getAttributeValue(this.m_eiSWProdStruct, "COMNAME") : "";
/*     */ 
/*     */           
/* 175 */           str2 = replaceFCDummy(str1, str2);
/*     */ 
/*     */           
/* 178 */           setAttrValue(this.m_eiSWProdStruct, "COMNAME", str2);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 184 */         println("<br><br><FONT SIZE=4>" + 
/*     */             
/* 186 */             getABRDescription() + " has Passed, no report provided");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 191 */       println("<br><br>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       if (getReturnCode() == -1) {
/* 198 */         displayHeader(this.m_egSWFeature, this.m_eiSWfeature);
/* 199 */         if (this.m_eiSWProdStruct != null) {
/* 200 */           println(
/* 201 */               displayStatuses(this.m_eiSWProdStruct, this.m_eiSWProdStruct
/*     */                 
/* 203 */                 .getEntityGroup()));
/* 204 */           println(
/* 205 */               displayNavAttributes(this.m_eiSWProdStruct, this.m_eiSWProdStruct
/*     */                 
/* 207 */                 .getEntityGroup()));
/*     */         } 
/* 209 */         println(this.errMessage.toString());
/*     */         
/* 211 */         println("<BR><br /><b>" + 
/*     */             
/* 213 */             buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */                 
/* 216 */                 getABRDescription(), 
/* 217 */                 (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */               }) + "</b>");
/*     */ 
/*     */         
/* 221 */         log(
/* 222 */             buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */                 
/* 225 */                 getABRDescription(), 
/* 226 */                 (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */               }));
/*     */       } 
/* 229 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 230 */       setReturnCode(-2);
/* 231 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 235 */           .getMessage() + "</font></h3>");
/*     */       
/* 237 */       logError(lockPDHEntityException.getMessage());
/* 238 */       lockPDHEntityException.printStackTrace();
/* 239 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 240 */       setReturnCode(-2);
/* 241 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 243 */           .getMessage() + "</font></h3>");
/*     */       
/* 245 */       logError(updatePDHEntityException.getMessage());
/* 246 */       updatePDHEntityException.printStackTrace();
/* 247 */     } catch (Exception exception) {
/* 248 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 249 */       println("" + exception);
/*     */ 
/*     */       
/* 252 */       if (getABRReturnCode() != -2) {
/* 253 */         setReturnCode(-3);
/*     */       }
/* 255 */       exception.printStackTrace();
/*     */       
/* 257 */       StringWriter stringWriter = new StringWriter();
/* 258 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 259 */       str3 = stringWriter.toString();
/* 260 */       println(str3);
/*     */       
/* 262 */       logMessage("Error in " + this.m_abri
/* 263 */           .getABRCode() + ":" + exception.getMessage());
/* 264 */       logMessage("" + exception);
/*     */     }
/*     */     finally {
/*     */       
/* 268 */       setDGString(getABRReturnCode());
/* 269 */       setDGRptName("SWFCRENAME30A");
/* 270 */       setDGRptClass("SWFCRENAME30A");
/* 271 */       printDGSubmitString();
/*     */ 
/*     */       
/* 274 */       buildReportFooter();
/*     */       
/* 276 */       if (!isReadOnly()) {
/* 277 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String replaceFCDummy(String paramString1, String paramString2) {
/* 285 */     String str = paramString2;
/* 286 */     if (paramString2.indexOf("#") > -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 291 */       String str1 = paramString2.substring(0, paramString2.indexOf("#")).trim();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       String str2 = paramString2.substring(paramString2.indexOf("#")).trim();
/*     */       
/* 298 */       String str3 = str2.substring(str2.indexOf(" ")).trim();
/*     */       
/* 300 */       str = str1 + " " + paramString1 + " " + str3;
/*     */     } else {
/*     */       
/* 303 */       logMessage(this.m_abri
/* 304 */           .getABRCode() + " Dummy FC not found to replace in " + paramString2);
/*     */     } 
/*     */ 
/*     */     
/* 308 */     return str;
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
/*     */   public void setAttrValue(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/*     */     try {
/* 332 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*     */ 
/*     */       
/* 335 */       Vector<Text> vector = new Vector();
/* 336 */       Vector<ReturnEntityKey> vector1 = new Vector();
/* 337 */       Text text = null;
/*     */       
/* 339 */       EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 340 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString1);
/*     */       
/* 342 */       switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*     */         case 'T':
/* 344 */           if (paramString2.length() == 0) {
/* 345 */             EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 346 */             if (eANAttribute.toString() != null && eANAttribute
/* 347 */               .toString().length() > 0)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 355 */               ControlBlock controlBlock = new ControlBlock(this.m_strNow, this.m_strNow, this.m_strNow, this.m_strNow, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 362 */               text = new Text(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, eANAttribute.toString(), 1, controlBlock);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 367 */             if (paramString2.lastIndexOf('-') > 0)
/*     */             {
/* 369 */               paramString2 = paramString2.substring(0, paramString2
/*     */                   
/* 371 */                   .length() - 1);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 380 */             ControlBlock controlBlock = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 385 */             text = new Text(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, controlBlock);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 391 */           vector.addElement(text);
/*     */           break;
/*     */       } 
/* 394 */       returnEntityKey.m_vctAttributes = vector;
/* 395 */       vector1.addElement(returnEntityKey);
/*     */       
/* 397 */       this.m_db.update(this.m_prof, vector1);
/* 398 */       this.m_db.commit();
/* 399 */       this.m_db.freeStatement();
/* 400 */       this.m_db.isPending();
/*     */     }
/* 402 */     catch (MiddlewareException middlewareException) {
/* 403 */       logMessage("setAttrValue: " + middlewareException.getMessage());
/* 404 */     } catch (Exception exception) {
/* 405 */       logMessage("setAttrValue: " + exception.getMessage());
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
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 419 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 429 */     return "This ABR is used to search/copy the FEATURECODE value from the selected SWFEATURE  to COMNAME on SWFEATURE and SWPRODSTRUCT respectively";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 438 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 448 */     return new String("1.4");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SWFCRENAME30A.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */