/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANFoundation;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnID;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MXABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*     */   private static final String DQ_FINAL = "FINAL";
/*     */   private static final String DQ_DRAFT = "DRAFT";
/*     */   private static final String STATUS_DRAFT = "0010";
/*     */   private static final String STATUS_FINAL = "0020";
/*     */   private static final String STATUS_CHANGE_REQUEST = "0050";
/*     */   private static final String ABR_QUEUE = "0020";
/*     */   static final String RPT_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*     */   static final String RPT_STACKTRACE = "<pre>{0}</pre>";
/*     */   private static final String MIW_PDHDOMAIN = "MIW";
/* 106 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/* 107 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/* 109 */   private StringBuffer rptSb = new StringBuffer();
/*     */   private String SUBSCRVE;
/*     */   
/*     */   public void execute_run() {
/* 113 */     setDGTitle("MXABRSTATUS");
/* 114 */     setDGRptName(getShortClassName(getClass()));
/* 115 */     setDGRptClass(getABRCode());
/* 116 */     String str1 = getShortClassName(getClass()) + " - MIW Inbound";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle(str1) + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>" + str1 + "</em></p>" + NEWLINE;
/*     */ 
/*     */     
/* 124 */     println(EACustom.getDocTypeHtml());
/* 125 */     println(str2);
/* 126 */     this.SUBSCRVE = "UNDEFINED";
/*     */     try {
/* 128 */       start_ABRBuild(false);
/* 129 */       setNow();
/* 130 */       setControlBlock();
/*     */       
/* 132 */       EntityList entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/* 133 */               getEntityType(), 
/* 134 */               getEntityID()) });
/* 135 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*     */       
/* 137 */       String str3 = PokUtils.getAttributeValue(entityItem, "MXTYPE", null, null);
/*     */       
/* 139 */       String str4 = PokUtils.getAttributeValue(entityItem, "MXMSG", null, null, false);
/* 140 */       addDebug(str4);
/* 141 */       Document document = parseXML(str4);
/* 142 */       Element element = document.getDocumentElement();
/*     */       
/* 144 */       if ("REFOFER".equals(str3)) {
/* 145 */         this.SUBSCRVE = "REFOFERVE";
/* 146 */         handleRefofer(entityItem, element);
/* 147 */       } else if ("REFOFERFEAT".equals(str3)) {
/* 148 */         this.SUBSCRVE = "REFOFERFEATVE";
/* 149 */         handleRefoferFeat(entityItem, element);
/*     */       } 
/* 151 */     } catch (Throwable throwable) {
/* 152 */       StringWriter stringWriter = new StringWriter();
/* 153 */       MessageFormat messageFormat = new MessageFormat("<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>");
/* 154 */       setReturnCode(-3);
/* 155 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */ 
/*     */       
/* 158 */       String[] arrayOfString1 = { throwable.getMessage() };
/* 159 */       this.rptSb.append(messageFormat.format(arrayOfString1));
/* 160 */       this.rptSb.append("\n");
/*     */       
/* 162 */       messageFormat = new MessageFormat("<pre>{0}</pre>");
/* 163 */       String[] arrayOfString2 = { stringWriter.getBuffer().toString() };
/* 164 */       this.rptSb.append(messageFormat.format(arrayOfString2));
/* 165 */       this.rptSb.append("\n");
/* 166 */       logError("Exception: " + throwable.getMessage());
/* 167 */       logError(stringWriter.getBuffer().toString());
/*     */     } 
/*     */     
/* 170 */     println(this.rptSb.toString());
/* 171 */     printDGSubmitString();
/* 172 */     println(EACustom.getTOUDiv());
/* 173 */     buildReportFooter();
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
/*     */   private void handleRefofer(EntityItem paramEntityItem, Element paramElement) throws Throwable {
/* 189 */     String str = PokUtils.getAttributeValue(paramEntityItem, "MXPRODUCTID", null, null);
/*     */     
/* 191 */     addDebug("MIW MESSAGE TYPE: REFOFER, PRODUCTID = " + str);
/*     */ 
/*     */ 
/*     */     
/* 195 */     EntityItem entityItem = findRefofer(str);
/* 196 */     EntityWrapper entityWrapper = null;
/*     */     
/* 198 */     if (entityItem != null) {
/* 199 */       addDebug("REFOFER found: " + entityItem.getKey());
/*     */     } else {
/* 201 */       addDebug("REFOFER not found!");
/*     */     } 
/*     */     
/* 204 */     RefoferModel refoferModel = new RefoferModel(paramElement);
/*     */     try {
/* 206 */       refoferModel.validate();
/* 207 */       if ("Delete".equalsIgnoreCase(refoferModel.ACTIVITY)) {
/* 208 */         if (entityItem == null) {
/*     */           
/* 210 */           addError(refoferModel.toString());
/* 211 */           fail("Reference Offering was marked for Delete; however, the Reference Offering does not exist in the PDH");
/*     */         } else {
/*     */           
/* 214 */           entityWrapper = new EntityWrapper(entityItem);
/*     */           
/* 216 */           String str1 = refoferModel.DTSOFMSG.substring(0, 10);
/* 217 */           entityWrapper.text("ENDOFSVC", str1);
/* 218 */           entityWrapper.flag("DATAQUALITY", "FINAL");
/* 219 */           entityWrapper.flag("STATUS", "0020");
/* 220 */           entityWrapper.flag("ADSABRSTATUS", "0020");
/* 221 */           entityWrapper.end();
/* 222 */           addOutput(refoferModel.toString());
/* 223 */           addOutput("Reference Offering was marked for Delete; however, the Reference Offering was not deleted. The End of Service was updated to match this change (" + str1 + "). This change was feed to downstream systems.");
/*     */         } 
/* 225 */       } else if ("Update".equalsIgnoreCase(refoferModel.ACTIVITY)) {
/* 226 */         if (entityItem == null) {
/*     */           
/* 228 */           EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, "REFOFER", "Edit");
/* 229 */           entityItem = new EntityItem((EANFoundation)entityGroup, this.m_prof, "REFOFER", 0);
/* 230 */           addOutput("New REFOFER created");
/*     */         } 
/*     */         
/* 233 */         entityWrapper = new EntityWrapper(entityItem);
/* 234 */         entityWrapper.text("PRODUCTID", refoferModel.PRODUCTID);
/* 235 */         entityWrapper.text("DTSMIWCREATE", refoferModel.DTSMIWCREATE);
/* 236 */         entityWrapper.flag("PDHDOMAIN", "MIW");
/* 237 */         entityWrapper.text("MFRPRODTYPE", refoferModel.MFRPRODTYPE, 30);
/* 238 */         entityWrapper.text("MFRPRODDESC", refoferModel.MFRPRODDESC, 32);
/* 239 */         entityWrapper.text("MKTGDIV", refoferModel.MKTGDIV, 2);
/* 240 */         entityWrapper.flag("PRFTCTR", refoferModel.PRFTCTR);
/* 241 */         entityWrapper.text("CATGSHRTDESC", refoferModel.CATGSHRTDESC, 30);
/* 242 */         entityWrapper.text("STRTOFSVC", refoferModel.STRTOSVC);
/* 243 */         entityWrapper.text("ENDOFSVC", refoferModel.ENDOFSVC);
/* 244 */         entityWrapper.text("VENDNAM", refoferModel.VENDNAM, 30);
/* 245 */         entityWrapper.text("MACHRATECATG", refoferModel.MACHRATECATG, 1);
/* 246 */         entityWrapper.text("CECSPRODKEY", refoferModel.CECSPRODKEY, 1);
/*     */         
/* 248 */         entityWrapper.flag("MAINTANNBILLELIGINDC", "Y".equals(refoferModel.MAINTANNBILLELIGINDC) ? "MAINY" : "MAINN");
/*     */ 
/*     */         
/* 251 */         entityWrapper.flag("SYSIDUNIT", "Y".equals(refoferModel.FSLMCPU) ? "S00010" : "S00020");
/* 252 */         entityWrapper.text("PRODSUPRTCD", refoferModel.PRODSUPRTCD, 3);
/* 253 */         if (refoferModel.DOMAIN != null || !"".equals(refoferModel.DOMAIN)) {
/* 254 */           entityWrapper.text("DOMAIN", refoferModel.DOMAIN);
/*     */         }
/* 256 */         entityWrapper.flag("DATAQUALITY", "FINAL");
/* 257 */         entityWrapper.flag("STATUS", "0020");
/* 258 */         entityWrapper.flag("ADSABRSTATUS", "0020");
/* 259 */         entityWrapper.end();
/* 260 */         setReturnCode(0);
/* 261 */         addOutput("REFOFER attributes updated");
/*     */       }
/*     */     
/* 264 */     } catch (Exception exception) {
/* 265 */       fail("Invalid REFOFER message: " + exception.getMessage());
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
/*     */   private void handleRefoferFeat(EntityItem paramEntityItem, Element paramElement) throws Throwable {
/* 315 */     addDebug("MIW MESSAGE TYPE: REFOFERFEAT");
/*     */     
/* 317 */     RefoferFeatModel refoferFeatModel = new RefoferFeatModel(paramElement);
/*     */     try {
/* 319 */       refoferFeatModel.validate();
/*     */ 
/*     */       
/* 322 */       String str = PokUtils.getAttributeValue(paramEntityItem, "MXFEATID", null, null);
/*     */       
/* 324 */       EntityItem entityItem1 = findRefoferFeat(str);
/* 325 */       if (entityItem1 != null) {
/* 326 */         addDebug("Found REFOFERFEAT for FEATID = " + str + ": " + entityItem1.getKey());
/*     */       } else {
/* 328 */         addDebug("REFOFERFEAT *not found* for FEATID = " + str);
/*     */       } 
/*     */       
/* 331 */       EntityWrapper entityWrapper = null;
/* 332 */       int i = 0;
/*     */       
/* 334 */       if ("Update".equalsIgnoreCase(refoferFeatModel.ACTIVITY)) {
/*     */         
/* 336 */         if (entityItem1 != null) {
/*     */           
/* 338 */           i = entityItem1.getEntityID();
/*     */         } else {
/*     */           
/* 341 */           i = this.m_db.getNextEntityID(this.m_prof, "REFOFERFEAT");
/* 342 */           EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, "REFOFERFEAT", "Edit");
/* 343 */           entityItem1 = new EntityItem((EANFoundation)entityGroup, this.m_prof, "REFOFERFEAT", i);
/* 344 */           addOutput("New REFOFERFEAT created " + entityItem1.getKey());
/*     */         } 
/*     */         
/* 347 */         entityWrapper = new EntityWrapper(entityItem1);
/* 348 */         entityWrapper.text("FEATID", refoferFeatModel.FEATID, 40);
/* 349 */         entityWrapper.text("DTSMIWCREATE", refoferFeatModel.DTSMIWCREATE);
/* 350 */         entityWrapper.text("MFRFEATID", refoferFeatModel.MFRFEATID, 30);
/* 351 */         entityWrapper.text("MFRFEATDESC", refoferFeatModel.MFRFEATDESC, 128);
/* 352 */         entityWrapper.text("MFRFEATLNGDESC", refoferFeatModel.MFRFEATLNGDESC, 128);
/* 353 */         entityWrapper.text("MKTGDIV", refoferFeatModel.MKTGDIV, 2);
/* 354 */         entityWrapper.flag("PRFTCTR", refoferFeatModel.PRFTCTR);
/* 355 */         entityWrapper.flag("PDHDOMAIN", "MIW");
/* 356 */         addOutput("REFOFERFEAT atributes updated");
/* 357 */       } else if ("Delete".equalsIgnoreCase(refoferFeatModel.ACTIVITY)) {
/*     */         
/* 359 */         if (entityItem1 != null) {
/*     */           
/* 361 */           setFlagValue(entityItem1, "STATUS", "0050");
/* 362 */           addError(refoferFeatModel.toString());
/* 363 */           fail("Reference Offering Feature was marked for Delete. The STATUS was set to 'Change Request'. Please review and decide how to proceed.");
/*     */         } else {
/*     */           
/* 366 */           addError(refoferFeatModel.toString());
/* 367 */           fail("Reference Offering Feature was marked for Delete; however, the Reference Offering Feature does not exist in the PDH");
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 375 */       EntityItem entityItem2 = findRefofer(refoferFeatModel.PRODUCTID);
/* 376 */       if (entityItem2 != null) {
/* 377 */         addDebug("Found REFOFER for PRODUCTID = " + refoferFeatModel.PRODUCTID + ": " + entityItem2
/* 378 */             .getKey());
/*     */       } else {
/* 380 */         addDebug("REFOFER *not found* for PRODUCTID = " + refoferFeatModel.PRODUCTID);
/*     */       } 
/* 382 */       if ("".equals(refoferFeatModel.PRODUCTACTIVITY) || "Update"
/* 383 */         .equalsIgnoreCase(refoferFeatModel.PRODUCTACTIVITY)) {
/*     */         
/* 385 */         if (entityItem2 != null) {
/*     */           
/* 387 */           EntityItem entityItem = findReofferFeatRelator(refoferFeatModel.PRODUCTID, entityItem1);
/* 388 */           if (entityItem == null) {
/* 389 */             addDebug("Creating new relator REFOFERREFOFERFEAT");
/*     */             
/* 391 */             createRelator("REFOFERREFOFERFEAT", "REFOFER", entityItem2.getEntityID(), "REFOFERFEAT", entityItem1
/* 392 */                 .getEntityID());
/* 393 */             if (entityWrapper == null) {
/* 394 */               entityWrapper = new EntityWrapper(entityItem1);
/*     */             }
/* 396 */             entityWrapper.flag("DATAQUALITY", "FINAL");
/* 397 */             entityWrapper.flag("STATUS", "0020");
/* 398 */             entityWrapper.flag("ADSABRSTATUS", "0020");
/*     */           } 
/* 400 */           addOutput("REFOFERFEAT was updated succesfully");
/*     */         } else {
/*     */           
/* 403 */           int j = this.m_db.getNextEntityID(this.m_prof, "REFOFER");
/* 404 */           EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, "REFOFER", "Edit");
/* 405 */           entityItem2 = new EntityItem((EANFoundation)entityGroup, this.m_prof, "REFOFER", j);
/* 406 */           EntityWrapper entityWrapper1 = new EntityWrapper(entityItem2);
/* 407 */           entityWrapper1.text("PRODUCTID", refoferFeatModel.PRODUCTID);
/* 408 */           entityWrapper1.text("MKTGNAME", "Error ï¿½ not received from MIW");
/* 409 */           entityWrapper1.flag("PDHDOMAIN", "MIW");
/* 410 */           entityWrapper1.flag("STATUS", "0010");
/* 411 */           entityWrapper1.flag("DATAQUALITY", "DRAFT");
/* 412 */           entityWrapper1.end();
/* 413 */           createRelator("REFOFERREFOFERFEAT", "REFOFER", j, "REFOFERFEAT", i);
/*     */           
/* 415 */           addDebug("Created skeleton for REFOFER: " + j + " and relator REFOFERREFOFERFEAT for REFOFERFEAT " + i);
/*     */           
/* 417 */           addError(refoferFeatModel.toString());
/* 418 */           if (entityWrapper != null) {
/* 419 */             entityWrapper.end();
/*     */           }
/* 421 */           fail("Referenced Reference Offering does not exist but skeleton REFOFER was created");
/*     */           return;
/*     */         } 
/* 424 */       } else if ("Remove".equalsIgnoreCase(refoferFeatModel.PRODUCTACTIVITY) || "Delete"
/* 425 */         .equalsIgnoreCase(refoferFeatModel.PRODUCTACTIVITY)) {
/*     */         
/* 427 */         if (entityItem2 != null) {
/*     */           
/* 429 */           EntityItem entityItem = findReofferFeatRelator(refoferFeatModel.PRODUCTID, entityItem1);
/* 430 */           if (entityItem == null) {
/* 431 */             addError(refoferFeatModel.toString());
/* 432 */             fail("the relator \"Reference Offering to Reference Feature\" (REFOFERREFOFERFEAT) does not exist and hence there is nothing to delete.");
/*     */             return;
/*     */           } 
/* 435 */           addDebug("Deleting relator REFOFERREFOFERFEAT " + entityItem.getKey());
/* 436 */           ReturnStatus returnStatus = new ReturnStatus();
/* 437 */           this.m_db.callGBL2099(returnStatus, this.m_cbOn.m_iOPWGID, this.m_prof.getEnterprise(), "REFOFERREFOFERFEAT", entityItem
/* 438 */               .getEntityID(), this.m_cbOn.m_iTRANID);
/* 439 */           this.m_db.commit();
/* 440 */           this.m_db.freeStatement();
/* 441 */           this.m_db.isPending();
/* 442 */           addOutput("REFOFERFEAT's relator was deactivated succesfully");
/*     */ 
/*     */           
/* 445 */           if (entityWrapper == null) {
/* 446 */             entityWrapper = new EntityWrapper(entityItem1);
/*     */           }
/*     */         } else {
/*     */           
/* 450 */           addError(refoferFeatModel.toString());
/* 451 */           fail("Reference Offering does not exist and hence there isn't a 'Reference Offering to Reference Feature' to delete.");
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 456 */       if (entityWrapper != null) {
/*     */         
/* 458 */         entityWrapper.flag("DATAQUALITY", "FINAL");
/* 459 */         entityWrapper.flag("STATUS", "0020");
/* 460 */         entityWrapper.flag("ADSABRSTATUS", "0020");
/* 461 */         entityWrapper.end();
/*     */       } 
/* 463 */       setReturnCode(0);
/*     */     }
/* 465 */     catch (Exception exception) {
/* 466 */       fail("Invalid REFOFERFEAT message: " + exception.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityItem findRefofer(String paramString) throws Exception {
/* 473 */     Vector<String> vector1 = new Vector();
/* 474 */     vector1.addElement("PRODUCTID");
/* 475 */     Vector<String> vector2 = new Vector();
/* 476 */     vector2.addElement(paramString);
/* 477 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/* 479 */       EntityItem[] arrayOfEntityItem = ABRUtil.doSearch(this.m_db, this.m_prof, "SRDREFOFER1", "REFOFER", false, vector1, vector2, stringBuffer);
/*     */       
/* 481 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 482 */         EntityItem entityItem = arrayOfEntityItem[b];
/* 483 */         String str1 = PokUtils.getAttributeValue(entityItem, "PRODUCTID", null, null);
/* 484 */         String str2 = PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN");
/* 485 */         addDebug("Looking at " + entityItem.getKey() + " [" + str1 + "," + str2 + "]");
/* 486 */         if (paramString.equalsIgnoreCase(str1) && "MIW".equals(str2))
/*     */         {
/* 488 */           return entityItem;
/*     */         }
/*     */       } 
/* 491 */     } catch (Exception exception) {
/* 492 */       addError(stringBuffer.toString());
/* 493 */       throw exception;
/*     */     } 
/* 495 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityItem findRefoferFeat(String paramString) throws Exception {
/* 500 */     Vector<String> vector1 = new Vector();
/* 501 */     vector1.addElement("FEATID");
/* 502 */     Vector<String> vector2 = new Vector();
/* 503 */     vector2.addElement(paramString);
/* 504 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/* 506 */       EntityItem[] arrayOfEntityItem = ABRUtil.doSearch(this.m_db, this.m_prof, "SRDREFOFERFEAT", "REFOFERFEAT", false, vector1, vector2, stringBuffer);
/*     */       
/* 508 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 509 */         EntityItem entityItem = arrayOfEntityItem[b];
/* 510 */         String str = PokUtils.getAttributeValue(entityItem, "FEATID", null, null);
/* 511 */         if (paramString.equalsIgnoreCase(str))
/*     */         {
/* 513 */           return entityItem;
/*     */         }
/*     */       } 
/* 516 */     } catch (Exception exception) {
/* 517 */       addError(stringBuffer.toString());
/* 518 */       throw exception;
/*     */     } 
/* 520 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityItem findReofferFeatRelator(String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 525 */     if (paramEntityItem.getEntityID() <= 0) {
/* 526 */       return null;
/*     */     }
/* 528 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "BHREFOFERFEATEXT"), new EntityItem[] { new EntityItem(paramEntityItem) });
/*     */     
/* 530 */     EntityGroup entityGroup = entityList.getEntityGroup("REFOFERREFOFERFEAT");
/* 531 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 532 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 533 */       if (entityItem.getUpLinkCount() > 0 && entityItem.getDownLinkCount() > 0) {
/* 534 */         EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/* 535 */         String str = PokUtils.getAttributeValue(entityItem1, "PRODUCTID", null, null);
/* 536 */         addDebug("Looking for relator with " + entityItem1.getKey() + " PRODUCTID " + str + "=" + paramString);
/* 537 */         if (paramString.equals(str)) {
/* 538 */           addDebug("Relator found for " + paramEntityItem.getKey() + " + " + paramString);
/* 539 */           return entityItem;
/*     */         } 
/*     */       } 
/*     */     } 
/* 543 */     return null;
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/* 547 */     return "1.0";
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 551 */     return "MXABRSTATUS";
/*     */   }
/*     */   
/*     */   private Document parseXML(String paramString) throws Exception {
/*     */     try {
/* 556 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 557 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 558 */       StringReader stringReader = new StringReader(paramString);
/* 559 */       InputSource inputSource = new InputSource(stringReader);
/* 560 */       return documentBuilder.parse(inputSource);
/* 561 */     } catch (SAXParseException sAXParseException) {
/* 562 */       addError(paramString);
/* 563 */       throw sAXParseException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNodeValue(Element paramElement, String paramString) {
/* 573 */     String str = null;
/* 574 */     NodeList nodeList = paramElement.getElementsByTagName(paramString);
/* 575 */     if (nodeList == null) {
/* 576 */       addDebug("getNodeValue: " + paramString + " element returned null");
/* 577 */     } else if (nodeList.getLength() == 0) {
/* 578 */       addDebug("getNodeValue: " + paramString + " was not found in the XML");
/* 579 */     } else if (nodeList.getLength() > 1) {
/* 580 */       addDebug("getNodeValue: " + paramString + " has more then 1 element");
/*     */     } else {
/* 582 */       Element element1 = (Element)nodeList.item(0);
/* 583 */       Element element2 = (Element)nodeList.item(0);
/* 584 */       if (element1.hasChildNodes()) {
/* 585 */         str = element2.getFirstChild().getNodeValue();
/*     */       } else {
/* 587 */         str = element2.getNodeValue();
/*     */       } 
/*     */     } 
/*     */     
/* 591 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public ReturnStatus createRelator(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2) throws MiddlewareException {
/* 596 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 597 */     String str1 = this.m_prof.getEnterprise();
/* 598 */     int i = this.m_cbOn.getOPENID();
/* 599 */     int j = this.m_cbOn.getTranID();
/* 600 */     String str2 = this.m_cbOn.getEffFrom();
/* 601 */     String str3 = this.m_cbOn.getEffTo();
/* 602 */     int k = this.m_prof.getSessionID();
/* 603 */     this.m_db.callGBL2098(returnStatus, i, k, str1, paramString1, new ReturnID(), paramString2, paramInt1, paramString3, paramInt2, j, str2, str3, 1);
/*     */     
/* 605 */     this.m_db.freeStatement();
/* 606 */     this.m_db.isPending();
/* 607 */     return returnStatus;
/*     */   }
/*     */   
/*     */   public void addDebug(String paramString) {
/* 611 */     this.rptSb.append("<!-- " + paramString + " -->\n");
/*     */   }
/*     */   
/*     */   public void addOutput(String paramString) {
/* 615 */     this.rptSb.append("<p>" + paramString + "</p>\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addError(String paramString) {
/* 623 */     addOutput(paramString);
/*     */   }
/*     */   
/*     */   public void fail(String paramString) {
/* 627 */     addError(paramString);
/* 628 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */   
/*     */   class Model
/*     */   {
/*     */     public String ACTIVITY;
/*     */     
/*     */     public String DTSOFMSG;
/*     */     public String DTSMIWCREATE;
/*     */     
/*     */     public void rejectIfNullOrEmpty(String param1String1, String param1String2) throws Exception {
/* 640 */       if (param1String2 == null || param1String2.length() == 0) {
/* 641 */         throw new Exception("Element '" + param1String1 + "' cannot be empty.");
/*     */       }
/*     */     }
/*     */     
/*     */     public String parseTimestampAndRejectIfInvalid(String param1String1, String param1String2) throws Exception {
/*     */       try {
/* 647 */         return MXABRSTATUS.timestamp(param1String2);
/* 648 */       } catch (ParseException parseException) {
/* 649 */         throw new Exception("Element '" + param1String1 + "' timestamp '" + param1String2 + "' is invalid: " + parseException
/* 650 */             .getMessage());
/*     */       } 
/*     */     }
/*     */     
/*     */     public String parseDateAndRejectIfInvalid(String param1String1, String param1String2) throws Exception {
/*     */       try {
/* 656 */         return MXABRSTATUS.date(param1String2);
/* 657 */       } catch (ParseException parseException) {
/* 658 */         throw new Exception("Element '" + param1String1 + "' date '" + param1String2 + "' is invalid: " + parseException
/* 659 */             .getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   class RefoferModel
/*     */     extends Model {
/*     */     public String PRODUCTID;
/*     */     public String MFRPRODTYPE;
/*     */     public String MFRPRODDESC;
/*     */     public String MKTGDIV;
/*     */     public String PRFTCTR;
/*     */     public String CATGSHRTDESC;
/*     */     public String STRTOSVC;
/*     */     public String ENDOFSVC;
/*     */     public String VENDNAM;
/*     */     public String MACHRATECATG;
/*     */     public String CECSPRODKEY;
/*     */     public String MAINTANNBILLELIGINDC;
/*     */     public String FSLMCPU;
/*     */     public String PRODSUPRTCD;
/*     */     public String DOMAIN;
/*     */     
/*     */     public RefoferModel(Element param1Element) {
/* 683 */       this.ACTIVITY = MXABRSTATUS.this.getNodeValue(param1Element, "ACTIVITY");
/* 684 */       this.DTSOFMSG = MXABRSTATUS.this.getNodeValue(param1Element, "DTSOFMSG");
/* 685 */       this.DTSMIWCREATE = MXABRSTATUS.this.getNodeValue(param1Element, "DTSMIWCREATE");
/* 686 */       this.PRODUCTID = MXABRSTATUS.this.getNodeValue(param1Element, "PRODUCTID");
/* 687 */       this.MFRPRODTYPE = MXABRSTATUS.this.getNodeValue(param1Element, "MFRPRODTYPE");
/* 688 */       this.MFRPRODDESC = MXABRSTATUS.this.getNodeValue(param1Element, "MFRPRODDESC");
/* 689 */       this.MKTGDIV = MXABRSTATUS.this.getNodeValue(param1Element, "MKTGDIV");
/* 690 */       this.PRFTCTR = MXABRSTATUS.this.getNodeValue(param1Element, "PRFTCTR");
/* 691 */       this.CATGSHRTDESC = MXABRSTATUS.this.getNodeValue(param1Element, "CATGSHRTDESC");
/* 692 */       this.STRTOSVC = MXABRSTATUS.this.getNodeValue(param1Element, "STRTOSVC");
/* 693 */       this.ENDOFSVC = MXABRSTATUS.this.getNodeValue(param1Element, "ENDOFSVC");
/* 694 */       this.VENDNAM = MXABRSTATUS.this.getNodeValue(param1Element, "VENDNAM");
/* 695 */       this.MACHRATECATG = MXABRSTATUS.this.getNodeValue(param1Element, "MACHRATECATG");
/* 696 */       this.CECSPRODKEY = MXABRSTATUS.this.getNodeValue(param1Element, "CECSPRODKEY");
/* 697 */       this.MAINTANNBILLELIGINDC = MXABRSTATUS.this.getNodeValue(param1Element, "MAINTANNBILLELIGINDC");
/* 698 */       this.FSLMCPU = MXABRSTATUS.this.getNodeValue(param1Element, "FSLMCPU");
/* 699 */       this.PRODSUPRTCD = MXABRSTATUS.this.getNodeValue(param1Element, "PRODSUPRTCD");
/* 700 */       this.DOMAIN = MXABRSTATUS.this.getNodeValue(param1Element, "DOMAIN");
/*     */     }
/*     */ 
/*     */     
/*     */     public void validate() throws Exception {
/* 705 */       rejectIfNullOrEmpty("ACTIVITY", this.ACTIVITY);
/* 706 */       rejectIfNullOrEmpty("DTSOFMSG", this.DTSOFMSG);
/* 707 */       rejectIfNullOrEmpty("DTSMIWCREATE", this.DTSMIWCREATE);
/* 708 */       rejectIfNullOrEmpty("PRODUCTID", this.PRODUCTID);
/* 709 */       rejectIfNullOrEmpty("MFRPRODTYPE", this.MFRPRODTYPE);
/* 710 */       rejectIfNullOrEmpty("MFRPRODDESC", this.MFRPRODDESC);
/* 711 */       rejectIfNullOrEmpty("MKTGDIV", this.MKTGDIV);
/* 712 */       rejectIfNullOrEmpty("PRFTCTR", this.PRFTCTR);
/* 713 */       rejectIfNullOrEmpty("CATGSHRTDESC", this.CATGSHRTDESC);
/* 714 */       rejectIfNullOrEmpty("STRTOSVC", this.STRTOSVC);
/* 715 */       rejectIfNullOrEmpty("ENDOFSVC", this.ENDOFSVC);
/* 716 */       rejectIfNullOrEmpty("VENDNAM", this.VENDNAM);
/* 717 */       rejectIfNullOrEmpty("MACHRATECATG", this.MACHRATECATG);
/* 718 */       rejectIfNullOrEmpty("CECSPRODKEY", this.CECSPRODKEY);
/* 719 */       rejectIfNullOrEmpty("MAINTANNBILLELIGINDC", this.MAINTANNBILLELIGINDC);
/* 720 */       rejectIfNullOrEmpty("FSLMCPU", this.FSLMCPU);
/* 721 */       rejectIfNullOrEmpty("PRODSUPRTCD", this.PRODSUPRTCD);
/*     */       
/* 723 */       this.DTSOFMSG = parseTimestampAndRejectIfInvalid("DTSOFMSG", this.DTSOFMSG);
/* 724 */       this.DTSMIWCREATE = parseTimestampAndRejectIfInvalid("DTSMIWCREATE", this.DTSMIWCREATE);
/* 725 */       this.STRTOSVC = parseDateAndRejectIfInvalid("STRTOSVC", this.STRTOSVC);
/* 726 */       this.ENDOFSVC = parseDateAndRejectIfInvalid("ENDOFSVC", this.ENDOFSVC);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 730 */       StringBuffer stringBuffer = new StringBuffer();
/* 731 */       stringBuffer.append("Date/Time=" + this.DTSOFMSG);
/* 732 */       stringBuffer.append(" \nMIW Create=" + this.DTSMIWCREATE);
/* 733 */       stringBuffer.append(" \nMessage Type=REFOFER");
/* 734 */       stringBuffer.append(" \nProduct ID=" + this.PRODUCTID);
/* 735 */       stringBuffer.append("\n");
/* 736 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   class RefoferFeatModel
/*     */     extends Model {
/*     */     public String PRODUCTID;
/*     */     public String PRODUCTACTIVITY;
/*     */     public String FEATID;
/*     */     public String MFRFEATID;
/*     */     public String MFRFEATDESC;
/*     */     public String MFRFEATLNGDESC;
/*     */     public String MKTGDIV;
/*     */     public String PRFTCTR;
/*     */     
/*     */     public RefoferFeatModel(Element param1Element) {
/* 752 */       this.ACTIVITY = MXABRSTATUS.this.getNodeValue(param1Element, "ACTIVITY");
/* 753 */       String str = MXABRSTATUS.this.getNodeValue(param1Element, "PRODUCTACTIVITY");
/* 754 */       if (str == null || str.length() == 0) {
/* 755 */         this.PRODUCTACTIVITY = "";
/*     */       } else {
/* 757 */         this.PRODUCTACTIVITY = str;
/*     */       } 
/* 759 */       this.DTSOFMSG = MXABRSTATUS.this.getNodeValue(param1Element, "DTSOFMSG");
/* 760 */       this.DTSMIWCREATE = MXABRSTATUS.this.getNodeValue(param1Element, "DTSMIWCREATE");
/* 761 */       this.FEATID = MXABRSTATUS.this.getNodeValue(param1Element, "FEATID");
/* 762 */       this.PRODUCTID = MXABRSTATUS.this.getNodeValue(param1Element, "PRODUCTID");
/* 763 */       this.MFRFEATID = MXABRSTATUS.this.getNodeValue(param1Element, "MFRFEATID");
/* 764 */       this.MFRFEATDESC = MXABRSTATUS.this.getNodeValue(param1Element, "MFRFEATDESC");
/* 765 */       this.MFRFEATLNGDESC = MXABRSTATUS.this.getNodeValue(param1Element, "MFRFEATLNGDESC");
/* 766 */       this.MKTGDIV = MXABRSTATUS.this.getNodeValue(param1Element, "MKTGDIV");
/* 767 */       this.PRFTCTR = MXABRSTATUS.this.getNodeValue(param1Element, "PRFTCTR");
/*     */     }
/*     */     
/*     */     public void validate() throws Exception {
/* 771 */       rejectIfNullOrEmpty("ACTIVITY", this.ACTIVITY);
/* 772 */       rejectIfNullOrEmpty("DTSOFMSG", this.DTSOFMSG);
/* 773 */       rejectIfNullOrEmpty("DTSMIWCREATE", this.DTSMIWCREATE);
/* 774 */       rejectIfNullOrEmpty("PRODUCTID", this.PRODUCTID);
/* 775 */       rejectIfNullOrEmpty("FEATID", this.FEATID);
/* 776 */       rejectIfNullOrEmpty("MFRFEATID", this.MFRFEATID);
/* 777 */       rejectIfNullOrEmpty("MFRFEATDESC", this.MFRFEATDESC);
/* 778 */       rejectIfNullOrEmpty("MFRFEATLNGDESC", this.MFRFEATLNGDESC);
/* 779 */       rejectIfNullOrEmpty("MKTGDIV", this.MKTGDIV);
/* 780 */       rejectIfNullOrEmpty("PRFTCTR", this.PRFTCTR);
/* 781 */       this.DTSOFMSG = parseTimestampAndRejectIfInvalid("DTSOFMSG", this.DTSOFMSG);
/* 782 */       this.DTSMIWCREATE = parseTimestampAndRejectIfInvalid("DTSMIWCREATE", this.DTSMIWCREATE);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 786 */       StringBuffer stringBuffer = new StringBuffer();
/* 787 */       stringBuffer.append("Message Type=REFOFERFEAT");
/* 788 */       stringBuffer.append(" \nDate/Time=" + this.DTSOFMSG);
/* 789 */       stringBuffer.append(" \nMIW Create=" + this.DTSMIWCREATE);
/* 790 */       stringBuffer.append(" \nFeature Id=" + this.FEATID);
/* 791 */       stringBuffer.append(" \nProduct Id=" + this.PRODUCTID);
/* 792 */       stringBuffer.append("\n");
/* 793 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class EntityWrapper
/*     */   {
/*     */     EntityItem ei;
/*     */     
/*     */     ReturnEntityKey rek;
/*     */     Vector attrs;
/*     */     Map map;
/*     */     
/*     */     public EntityWrapper(EntityItem param1EntityItem) {
/* 807 */       this.ei = param1EntityItem;
/* 808 */       this.rek = new ReturnEntityKey(param1EntityItem.getEntityType(), param1EntityItem.getEntityID(), true);
/* 809 */       this.map = new HashMap<>();
/* 810 */       this.attrs = new Vector();
/*     */     }
/*     */     
/*     */     private void put(String param1String, Object param1Object) {
/* 814 */       Object object = this.map.get(param1String);
/* 815 */       if (object != null) {
/*     */         
/* 817 */         int i = this.attrs.indexOf(object);
/* 818 */         if (i >= 0) {
/* 819 */           this.attrs.remove(i);
/* 820 */           this.attrs.insertElementAt(param1Object, i);
/*     */         } 
/*     */       } else {
/* 823 */         this.attrs.add(param1Object);
/*     */       } 
/* 825 */       this.map.put(param1String, param1Object);
/*     */     }
/*     */ 
/*     */     
/*     */     public void flag(String param1String1, String param1String2) throws Exception {
/* 830 */       SingleFlag singleFlag = new SingleFlag(MXABRSTATUS.this.m_prof.getEnterprise(), this.rek.getEntityType(), this.rek.getEntityID(), param1String1, param1String2, 1, MXABRSTATUS.this.m_cbOn);
/* 831 */       put(param1String1, singleFlag);
/*     */     }
/*     */ 
/*     */     
/*     */     public void text(String param1String1, String param1String2) {
/* 836 */       Text text = new Text(MXABRSTATUS.this.m_prof.getEnterprise(), this.rek.getEntityType(), this.rek.getEntityID(), param1String1, param1String2, 1, MXABRSTATUS.this.m_cbOn);
/* 837 */       put(param1String1, text);
/*     */     }
/*     */     
/*     */     public void text(String param1String1, String param1String2, int param1Int) throws Exception {
/* 841 */       if (param1String2.length() > param1Int) {
/* 842 */         param1String2 = param1String2.substring(0, param1Int);
/*     */       }
/* 844 */       text(param1String1, param1String2);
/*     */     }
/*     */     
/*     */     public void end() throws Exception {
/* 848 */       Vector<ReturnEntityKey> vector = new Vector();
/* 849 */       this.rek.m_vctAttributes = this.attrs;
/* 850 */       vector.addElement(this.rek);
/*     */       try {
/* 852 */         MXABRSTATUS.this.m_db.update(MXABRSTATUS.this.m_prof, vector, false, false);
/* 853 */         MXABRSTATUS.this.m_db.commit();
/* 854 */       } catch (Exception exception) {
/* 855 */         throw new Exception("Unable to set text attributes for " + this.ei.getKey() + ": " + exception
/* 856 */             .getClass().getName() + " " + exception.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 863 */   static String sDateFormat = "yyyy-MM-dd";
/* 864 */   static DateFormat inDateFormat = new SimpleDateFormat(sDateFormat);
/* 865 */   static DateFormat outFormat = new SimpleDateFormat(sDateFormat);
/* 866 */   static int shortDateLength = sDateFormat.length();
/*     */   
/* 868 */   static String sDateTimeFormat = "yyyy-MM-dd hh:mm:ss.SSSSSS";
/* 869 */   static DateFormat inDateTimeFormat = new SimpleDateFormat(sDateTimeFormat);
/* 870 */   static int shortTimestampLength = sDateTimeFormat.length();
/*     */   
/*     */   public static String date(String paramString) throws ParseException {
/*     */     Date date;
/* 874 */     if (paramString.length() == shortDateLength) {
/* 875 */       date = inDateFormat.parse(paramString);
/*     */     } else {
/* 877 */       if (paramString.length() > shortDateLength) {
/* 878 */         paramString = paramString.substring(0, paramString.length());
/*     */       }
/* 880 */       date = inDateFormat.parse(paramString);
/*     */     } 
/* 882 */     return outFormat.format(date);
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
/*     */   public static String timestamp(String paramString) throws ParseException {
/* 894 */     int i = paramString.length();
/* 895 */     if (i == shortTimestampLength) {
/* 896 */       inDateTimeFormat.parse(paramString);
/*     */     } else {
/* 898 */       if (i > shortTimestampLength) {
/* 899 */         paramString = paramString.substring(0, shortTimestampLength);
/*     */       }
/* 901 */       inDateTimeFormat.parse(paramString);
/*     */     } 
/* 903 */     return paramString;
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
/*     */   public void printDGSubmitString() {
/* 919 */     String str1 = null;
/* 920 */     String str2 = this.m_abri.getABRCode();
/* 921 */     println("<!--DGSUBMITBEGIN");
/* 922 */     StringBuffer stringBuffer = new StringBuffer("<!--DGSUBMITBEGIN\n");
/* 923 */     print("TASKSTATUS=");
/* 924 */     stringBuffer.append("TASKSTATUS=");
/*     */     
/* 926 */     if (getReturnCode() == 0) {
/* 927 */       println("TSPA");
/* 928 */       stringBuffer.append("TSPA\n");
/*     */     } else {
/* 930 */       println("TSFAIL");
/* 931 */       stringBuffer.append("TSFAIL\n");
/*     */     } 
/*     */ 
/*     */     
/* 935 */     println("SUBSCRVE=" + this.SUBSCRVE);
/* 936 */     stringBuffer.append("SUBSCRVE=" + this.SUBSCRVE + "\n");
/*     */     
/* 938 */     str1 = ABRServerProperties.getCategory(str2, "CAT1");
/* 939 */     if (str1 != null) {
/* 940 */       println("CAT1=" + str1);
/* 941 */       stringBuffer.append("CAT1=" + str1 + "\n");
/*     */     } 
/* 943 */     str1 = ABRServerProperties.getCategory(str2, "CAT2");
/* 944 */     if (str1 != null) {
/* 945 */       println("CAT2=" + str1);
/* 946 */       stringBuffer.append("CAT2=" + str1 + "\n");
/*     */     } 
/*     */     
/* 949 */     str1 = ABRServerProperties.getCategory(str2, "CAT3");
/* 950 */     if (str1 != null) {
/* 951 */       println("CAT3=" + str1);
/* 952 */       stringBuffer.append("CAT3=" + str1 + "\n");
/*     */     } 
/*     */     
/* 955 */     str1 = ABRServerProperties.getCategory(str2, "CAT4");
/* 956 */     if (str1 != null) {
/* 957 */       println("CAT4=" + str1);
/* 958 */       stringBuffer.append("CAT4=" + str1 + "\n");
/*     */     } 
/*     */     
/* 961 */     str1 = ABRServerProperties.getCategory(str2, "CAT5");
/* 962 */     if (str1 != null) {
/* 963 */       println("CAT5=" + str1);
/* 964 */       stringBuffer.append("CAT5=" + str1 + "\n");
/*     */     } 
/*     */     
/* 967 */     str1 = ABRServerProperties.getCategory(str2, "CAT6");
/* 968 */     if (str1 != null) {
/* 969 */       println("CAT6=" + str1);
/* 970 */       stringBuffer.append("CAT6=" + str1 + "\n");
/*     */     } 
/*     */     
/* 973 */     str1 = ABRServerProperties.getExtMail(str2);
/* 974 */     if (str1 != null) {
/* 975 */       println("EXTMAIL=" + str1);
/* 976 */       stringBuffer.append("EXTMAIL=" + str1 + "\n");
/*     */     } 
/*     */     
/* 979 */     str1 = ABRServerProperties.getIntMail(str2);
/* 980 */     if (str1 != null) {
/* 981 */       println("INTMAIL=" + str1);
/* 982 */       stringBuffer.append("INTMAIL=" + str1 + "\n");
/*     */     } 
/*     */     
/* 985 */     str1 = ABRServerProperties.getSubscrEnabled(str2);
/* 986 */     if (str1 != null) {
/* 987 */       println("SUBSCR_ENABLED=" + str1);
/* 988 */       stringBuffer.append("SUBSCR_ENABLED=" + str1 + "\n");
/*     */     } 
/*     */     
/* 991 */     str1 = ABRServerProperties.getSubscrNotifyOnError(str2);
/* 992 */     if (str1 != null) {
/* 993 */       println("SUBSCR_NOTIFY_ON_ERROR=" + str1);
/* 994 */       stringBuffer.append("SUBSCR_NOTIFY_ON_ERROR=" + str1 + "\n");
/*     */     } 
/*     */     
/* 997 */     println("DGSUBMITEND-->");
/* 998 */     stringBuffer.append("DGSUBMITEND-->\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MXABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */