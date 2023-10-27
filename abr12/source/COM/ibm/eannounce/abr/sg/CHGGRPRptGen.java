/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.ChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBlobAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.RowSelectableTable;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.StringCharacterIterator;
/*      */ import java.util.Collections;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CHGGRPRptGen
/*      */ {
/*      */   public static final String VERSION = "1.15";
/*      */   static final String DELIMITER = "|";
/*      */   private Database dbCurrent;
/*      */   private Profile profile;
/*   74 */   private String startTime = null; private String endTime = null;
/*   75 */   private int cgEntityId = 0;
/*   76 */   private String cgIdentifier = null;
/*      */   private Hashtable metaTbl;
/*      */   private static final String DELETED = "Deleted";
/*      */   private static final String NEW = "New";
/*      */   private static final String CHANGED = "Changed";
/*      */   private static final String RESTORED = "Restored";
/*      */   private static final String OTHER_INDICATOR = "*";
/*      */   private static final String BLANK = "&nbsp;";
/*      */   private static final int MW_VENTITY_LIMIT = 100;
/*      */   private static final String OPEN_BRACE_REP = "XOPENBRACEX";
/*   86 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   87 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int L16 = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int BAD_ID = -2147483648;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String CHGGRPSTATUS_OPEN = "Open";
/*      */ 
/*      */ 
/*      */   
/*  105 */   private static final String[] CHGGRP_ATTR = new String[] { "CHGGRPREQDATE", "CHGGRPREQUEST", "CHGGRPCHANGES" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  144 */   private static final String[] MACHTYPE_ATTR = new String[] { "BRAND", "INVENTORYGROUP", "MACHTYPEATR" };
/*      */ 
/*      */ 
/*      */   
/*  148 */   private static final String[] MODEL_ATTR = new String[] { "ANNDATE", "DESCRIPTION", "MACHTYPEATR", "MODELATR", "MKTGNAME", "COFCAT", "COFSUBCAT", "COFGRP", "COFSUBGRP", "COMMENTS" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   private static final String[] PRODSTRUCT_ATTR = new String[] { "ANNDATE", "MKTGNAME", "SYSTEMMIN", "SYSTEMMAX", "ORDERCODE", "COMMENTS" };
/*      */ 
/*      */ 
/*      */   
/*  157 */   private static final String[] FEATURE_ATTR = new String[] { "CONFIGURATORFLAG", "DESCRIPTION", "FEATURECODE", "FCTYPE", "FIRSTANNDATE", "WITHDRAWANNDATE_T", "MKTGNAME", "INVNAME", "HWFCCAT", "HWFCSUBCAT", "COMMENTS" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  173 */   private static final String[] MODELCNV_ATTR = new String[] { "FROMMACHTYPE", "FROMMODEL", "TOMACHTYPE", "TOMODEL", "ANNDATE", "UPGRADETYPE", "WITHDRAWDATE", "COMMENTS" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  192 */   private static final String[] FCTRANS_ATTR = new String[] { "FROMMACHTYPE", "FROMMODEL", "FROMFEATURECODE", "TOMACHTYPE", "TOMODEL", "TOFEATURECODE", "UPGRADETYPE", "ANNDATE", "WITHDRAWDATE", "COMMENTS" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  201 */   private Vector mdlVct = new Vector();
/*  202 */   private Hashtable chgTbl = new Hashtable<>();
/*  203 */   private Vector featVct = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CHGGRPRptGen(Database paramDatabase, Profile paramProfile) {
/*  213 */     this.dbCurrent = paramDatabase;
/*  214 */     this.profile = paramProfile;
/*  215 */     this.metaTbl = new Hashtable<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/*  223 */     this.dbCurrent = null;
/*  224 */     this.profile = null;
/*  225 */     this.metaTbl.clear();
/*  226 */     this.metaTbl = null;
/*      */ 
/*      */     
/*  229 */     this.mdlVct.clear();
/*  230 */     this.mdlVct = null;
/*  231 */     this.chgTbl.clear();
/*  232 */     this.chgTbl = null;
/*  233 */     this.featVct.clear();
/*  234 */     this.featVct = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHeaderAndDTS(EntityList paramEntityList) throws MiddlewareException, SQLException {
/*  250 */     EntityChangeHistoryGroup entityChangeHistoryGroup = null;
/*  251 */     EANAttribute eANAttribute = null;
/*      */ 
/*      */     
/*  254 */     String str = this.dbCurrent.getNow(0);
/*      */     
/*  256 */     StringBuffer stringBuffer = new StringBuffer();
/*  257 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/*  258 */     EntityItem entityItem = entityGroup.getEntityItem(0);
/*      */     
/*  260 */     this.cgEntityId = entityItem.getEntityID();
/*      */ 
/*      */     
/*  263 */     this.cgIdentifier = getAttributeValue(entityItem, "CHGGRPNAME", "", "", false).trim();
/*      */ 
/*      */     
/*  266 */     stringBuffer.append("<table width=\"560\" summary=\"layout\">" + NEWLINE);
/*  267 */     stringBuffer.append("<tr><td>Current Date: </td><td>" + str
/*  268 */         .substring(0, str.length() - 7) + "</td></tr>" + NEWLINE);
/*  269 */     stringBuffer.append("<tr><td>User: </td><td>" + this.profile.getOPName() + "</td></tr>" + NEWLINE);
/*  270 */     stringBuffer.append("<tr><td>User Role: </td><td>" + this.profile.getRoleCode() + "</td></tr>" + NEWLINE);
/*  271 */     stringBuffer.append("<tr><td colspan=\"2\">&nbsp;</td></tr>" + NEWLINE);
/*      */ 
/*      */     
/*  274 */     for (byte b = 0; b < CHGGRP_ATTR.length; b++) {
/*      */       
/*  276 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(CHGGRP_ATTR[b]);
/*  277 */       String str1 = CHGGRP_ATTR[b];
/*  278 */       if (eANMetaAttribute != null)
/*  279 */         str1 = eANMetaAttribute.getActualLongDescription(); 
/*  280 */       stringBuffer.append("<tr><td>" + str1 + ":</td><td>" + 
/*  281 */           getAttributeValue(entityItem, CHGGRP_ATTR[b], "", "&nbsp;") + "</td></tr>" + NEWLINE);
/*      */     } 
/*      */     
/*  284 */     stringBuffer.append("</table>" + NEWLINE);
/*  285 */     stringBuffer.append("<p><b>Note:</b> * denotes changes made outside of this " + entityGroup.getLongDescription() + "</p>");
/*      */ 
/*      */     
/*  288 */     entityChangeHistoryGroup = new EntityChangeHistoryGroup(this.dbCurrent, this.profile, entityItem);
/*  289 */     if (entityChangeHistoryGroup == null || entityChangeHistoryGroup.getChangeHistoryItemCount() == 0) {
/*      */       
/*  291 */       stringBuffer.append("<p><span style=\"color:#c00; font-weight:bold;\">Error: No Change history found for this " + entityGroup.getLongDescription() + "</span></p>");
/*  292 */       this.cgEntityId = Integer.MIN_VALUE;
/*      */     } else {
/*      */       
/*  295 */       this.startTime = entityChangeHistoryGroup.getChangeHistoryItem(0).getChangeDate();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  306 */       this.endTime = str;
/*      */       
/*  308 */       eANAttribute = entityItem.getAttribute("CHGGRPSTATUS");
/*  309 */       stringBuffer.append("<!-- ");
/*  310 */       if (eANAttribute != null) {
/*      */         
/*  312 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.dbCurrent.getAttributeChangeHistoryGroup(this.profile, eANAttribute);
/*      */         
/*  314 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1);
/*  315 */         if (!"Open".equals(attributeChangeHistoryItem.getFlagCode())) {
/*      */           
/*  317 */           this.endTime = attributeChangeHistoryItem.getChangeDate();
/*  318 */           stringBuffer.append("CHGGRPSTATUS was not Open, using flag dts, not curtime" + NEWLINE);
/*      */         } 
/*      */         
/*  321 */         stringBuffer.append("ChangeHistoryGroup for Attribute: CHGGRPSTATUS" + NEWLINE);
/*  322 */         for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*      */           
/*  324 */           AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  325 */           stringBuffer.append("AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem1.getChangeDate() + " user: " + attributeChangeHistoryItem1.getUser() + " isValid: " + attributeChangeHistoryItem1
/*  326 */               .isValid() + " value: " + attributeChangeHistoryItem1.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem1.getFlagCode() + NEWLINE);
/*      */         } 
/*      */       } else {
/*      */         
/*  330 */         stringBuffer.append("Error: Could not get AttributeHistory for CHGGRPSTATUS, it was null" + NEWLINE);
/*  331 */       }  stringBuffer.append("-->" + NEWLINE);
/*      */       
/*  333 */       stringBuffer.append("<!-- Using starttime: " + this.startTime + " endtime: " + this.endTime + " tranId: " + this.profile.getTranID() + " " + entityItem
/*  334 */           .getEntityType() + ":" + entityItem.getEntityID() + " cgIdentifier: *" + this.cgIdentifier + "* -->" + NEWLINE);
/*      */       
/*  336 */       if (this.profile.getTranID() != this.cgEntityId) {
/*  337 */         stringBuffer.append("<!-- WARNING: Using ChangeGroup entityId: " + this.cgEntityId + " instead of tranId: " + this.profile.getTranID() + " -->" + NEWLINE);
/*      */       }
/*      */     } 
/*  340 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMachType() throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, SQLException {
/*  361 */     String str = "";
/*      */     
/*  363 */     if (this.cgEntityId != Integer.MIN_VALUE) {
/*  364 */       StringBuffer stringBuffer = new StringBuffer("<h2>MTM</h2>" + NEWLINE);
/*  365 */       String str1 = "EXRPT3MTM";
/*      */       
/*  367 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.dbCurrent, this.profile, str1);
/*      */ 
/*      */       
/*  370 */       EntityList entityList = new EntityList(this.dbCurrent, this.profile, this.cgEntityId, extractActionItem, "MACHTYPE", this.startTime, this.endTime);
/*      */       
/*  372 */       Vector<SortedCG> vector = new Vector();
/*  373 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/*      */       
/*  375 */       stringBuffer.append("<!-- getMachType()" + NEWLINE + "EntityList for extract " + str1 + " for MACHTYPE contains the following entities: " + NEWLINE);
/*  376 */       stringBuffer.append(outputList(entityList));
/*  377 */       stringBuffer.append(" -->" + NEWLINE);
/*      */       
/*  379 */       stringBuffer.append("<h4>" + entityGroup.getLongDescription() + "</h4>" + NEWLINE); byte b;
/*  380 */       for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */ 
/*      */         
/*  383 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */         
/*  385 */         StringBuffer stringBuffer1 = new StringBuffer();
/*  386 */         EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(this.dbCurrent, this.profile, entityItem);
/*  387 */         boolean bool = getEntityInfo(entityItem, MACHTYPE_ATTR, stringBuffer1, entityChangeHistoryGroup);
/*  388 */         if (bool) {
/*      */           
/*  390 */           String str2 = getSortKey(entityItem, new String[] { "MACHTYPEATR" }, entityChangeHistoryGroup);
/*  391 */           SortedCG sortedCG = new SortedCG(entityItem, str2, stringBuffer1.toString());
/*  392 */           vector.addElement(sortedCG);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  397 */           stringBuffer.append(stringBuffer1.toString());
/*      */         } 
/*      */       } 
/*  400 */       if (vector.size() == 0) {
/*      */         
/*  402 */         stringBuffer.append("<p>No changes found.</p>" + NEWLINE);
/*      */       }
/*      */       else {
/*      */         
/*  406 */         Collections.sort(vector);
/*      */         
/*  408 */         stringBuffer.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Machine type information\">" + NEWLINE + "<colgroup><col width=\"8%\"/><col width=\"10%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"7%\"/><col width=\"15%\"/></colgroup>" + NEWLINE);
/*      */ 
/*      */ 
/*      */         
/*  412 */         for (b = 0; b < vector.size(); b++) {
/*      */           
/*  414 */           SortedCG sortedCG = vector.elementAt(b);
/*  415 */           stringBuffer.append(sortedCG.getStructure(0));
/*      */         } 
/*  417 */         stringBuffer.append("</table>" + NEWLINE);
/*      */         
/*  419 */         for (b = 0; b < vector.size(); b++) {
/*      */           
/*  421 */           SortedCG sortedCG = vector.elementAt(b);
/*  422 */           sortedCG.dereference();
/*      */         } 
/*  424 */         vector.clear();
/*      */       } 
/*  426 */       vector = null;
/*  427 */       entityList.dereference();
/*  428 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/*  431 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getModelAndFeature() throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, SQLException {
/*  449 */     String str = "";
/*      */     
/*  451 */     if (this.cgEntityId != Integer.MIN_VALUE) {
/*  452 */       EntityGroup entityGroup1 = null;
/*  453 */       String str1 = "MODEL";
/*  454 */       StringBuffer stringBuffer = new StringBuffer();
/*      */       
/*  456 */       String str2 = "EXDUMMY";
/*  457 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.dbCurrent, this.profile, str2);
/*  458 */       EntityList entityList = new EntityList(this.dbCurrent, this.profile, this.cgEntityId, extractActionItem, str1, this.startTime, this.endTime);
/*      */ 
/*      */       
/*  461 */       EntityGroup entityGroup2 = entityList.getParentEntityGroup();
/*  462 */       stringBuffer.append("<!-- getModelAndFeature()" + NEWLINE + "EntityList for extract " + str2 + " for " + str1 + " contains the following entities: " + NEWLINE);
/*  463 */       stringBuffer.append(outputList(entityList));
/*  464 */       stringBuffer.append(" -->" + NEWLINE);
/*      */       byte b;
/*  466 */       for (b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/*      */         
/*  468 */         EntityItem entityItem = entityGroup2.getEntityItem(b);
/*  469 */         StringBuffer stringBuffer1 = new StringBuffer();
/*  470 */         EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(this.dbCurrent, this.profile, entityItem);
/*  471 */         boolean bool = getEntityInfo(entityItem, MODEL_ATTR, stringBuffer1, entityChangeHistoryGroup);
/*  472 */         if (!bool) {
/*      */           
/*  474 */           stringBuffer.append(stringBuffer1.toString());
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  479 */           String str3 = getSortKey(entityItem, new String[] { "MACHTYPEATR", "MODELATR" }, entityChangeHistoryGroup);
/*      */           
/*  481 */           SortedCG sortedCG = new SortedCG(entityItem, str3, stringBuffer1.toString());
/*  482 */           String str4 = entityItem.getEntityType() + ":" + entityItem.getEntityID();
/*  483 */           stringBuffer.append("<!-- " + str4 + " had chgs  -->" + NEWLINE);
/*  484 */           this.chgTbl.put(str4, sortedCG);
/*  485 */           this.mdlVct.addElement(sortedCG);
/*      */         } 
/*      */       } 
/*      */       
/*  489 */       entityList.dereference();
/*      */ 
/*      */       
/*  492 */       extractActionItem = new ExtractActionItem(null, this.dbCurrent, this.profile, str2);
/*  493 */       str1 = "FEATURE";
/*  494 */       entityList = new EntityList(this.dbCurrent, this.profile, this.cgEntityId, extractActionItem, str1, this.startTime, this.endTime);
/*  495 */       stringBuffer.append("<!-- getModelAndFeature()" + NEWLINE + "EntityList for extract " + str2 + " for " + str1 + " contains the following entities: " + NEWLINE);
/*  496 */       stringBuffer.append(outputList(entityList));
/*  497 */       stringBuffer.append(" -->" + NEWLINE);
/*      */ 
/*      */       
/*  500 */       entityGroup1 = entityList.getParentEntityGroup();
/*  501 */       for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/*      */         
/*  503 */         EntityItem entityItem = entityGroup1.getEntityItem(b);
/*  504 */         StringBuffer stringBuffer1 = new StringBuffer();
/*  505 */         EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(this.dbCurrent, this.profile, entityItem);
/*  506 */         boolean bool = getEntityInfo(entityItem, FEATURE_ATTR, stringBuffer1, entityChangeHistoryGroup);
/*  507 */         if (!bool) {
/*      */           
/*  509 */           stringBuffer.append(stringBuffer1.toString());
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  514 */           String str3 = entityItem.getEntityType() + ":" + entityItem.getEntityID();
/*  515 */           String str4 = getSortKey(entityItem, new String[] { "FEATURECODE" }, entityChangeHistoryGroup);
/*  516 */           SortedCG sortedCG = new SortedCG(entityItem, str4, stringBuffer1.toString());
/*      */ 
/*      */           
/*  519 */           String str5 = getSortKey(entityItem, new String[] { "INVENTORYGROUP" }, entityChangeHistoryGroup);
/*  520 */           sortedCG.setAlternateSortKey(str5 + str4);
/*      */           
/*  522 */           stringBuffer.append("<!-- " + str3 + " had chgs  -->" + NEWLINE);
/*  523 */           this.chgTbl.put(str3, sortedCG);
/*  524 */           this.featVct.addElement(sortedCG);
/*      */         } 
/*      */       } 
/*      */       
/*  528 */       entityList.dereference();
/*  529 */       str = stringBuffer.toString();
/*      */     } 
/*  531 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProdStruct() throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, SQLException {
/*  554 */     String str = "";
/*  555 */     if (this.cgEntityId != Integer.MIN_VALUE) {
/*  556 */       StringBuffer stringBuffer = new StringBuffer();
/*  557 */       String str1 = "PRODSTRUCT";
/*      */       
/*  559 */       String str2 = "EXRPT3FM";
/*      */       
/*  561 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.dbCurrent, this.profile, str2);
/*  562 */       EntityList entityList = new EntityList(this.dbCurrent, this.profile, this.cgEntityId, extractActionItem, str1, this.startTime, this.endTime);
/*      */ 
/*      */       
/*  565 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/*  566 */       stringBuffer.append("<!-- getProdStruct()" + NEWLINE + "EntityList for extract " + str2 + " for " + str1 + " contains the following entities: " + NEWLINE);
/*  567 */       stringBuffer.append(outputList(entityList));
/*  568 */       stringBuffer.append(" -->" + NEWLINE);
/*      */       
/*  570 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */         
/*  572 */         SortedCG sortedCG = null;
/*  573 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  574 */         String str3 = entityItem.getEntityType() + ":" + entityItem.getEntityID();
/*  575 */         StringBuffer stringBuffer1 = new StringBuffer();
/*  576 */         EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(this.dbCurrent, this.profile, entityItem);
/*  577 */         boolean bool = getEntityInfo(entityItem, PRODSTRUCT_ATTR, stringBuffer1, entityChangeHistoryGroup);
/*  578 */         stringBuffer.append(NEWLINE + "<!-- Root proditem " + str3 + " -->" + NEWLINE);
/*  579 */         if (!bool) {
/*      */           
/*  581 */           stringBuffer.append(stringBuffer1.toString());
/*      */         } else {
/*      */           
/*  584 */           sortedCG = new SortedCG(entityItem, "", stringBuffer1.toString());
/*  585 */           this.chgTbl.put(str3, sortedCG);
/*      */           
/*      */           byte b1;
/*      */           
/*  589 */           for (b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */             
/*  591 */             EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/*  592 */             stringBuffer.append("<!--    dnlink mdlitem " + entityItem1.getEntityType() + ":" + entityItem1.getEntityID() + " -->" + NEWLINE);
/*  593 */             if (entityItem1.getEntityType().equals("MODEL")) {
/*      */               
/*  595 */               SortedCG sortedCG1 = (SortedCG)this.chgTbl.get(entityItem1.getEntityType() + ":" + entityItem1.getEntityID());
/*  596 */               stringBuffer.append("<!--    dnlink model scg " + sortedCG1 + " -->" + NEWLINE);
/*  597 */               if (sortedCG1 == null) {
/*      */ 
/*      */                 
/*  600 */                 String str4 = entityItem1.getEntityType() + ":" + entityItem1.getEntityID();
/*      */                 
/*  602 */                 String str5 = entityItem1.getEntityGroup().getLongDescription() + ": " + getDisplayName(entityItem1, " - ", ", ");
/*  603 */                 String str6 = NEWLINE + "<!-- " + str4 + "  NO CG chgs -->" + NEWLINE + "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">" + str5 + "</th></tr>" + NEWLINE;
/*      */ 
/*      */ 
/*      */                 
/*  607 */                 String str7 = getSortKey(entityItem1, new String[] { "MACHTYPEATR", "MODELATR" }, null);
/*  608 */                 sortedCG1 = new SortedCG(entityItem1, str7, str6);
/*  609 */                 this.mdlVct.addElement(sortedCG1);
/*  610 */                 this.chgTbl.put(str4, sortedCG1);
/*      */               } 
/*  612 */               sortedCG1.addChild(sortedCG);
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  617 */           for (b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/*      */             
/*  619 */             EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b1);
/*  620 */             String str4 = entityItem1.getEntityType() + ":" + entityItem1.getEntityID();
/*  621 */             stringBuffer.append("<!--   uplink featitem " + str4 + " -->" + NEWLINE);
/*  622 */             if (entityItem1.getEntityType().equals("FEATURE")) {
/*      */               
/*  624 */               SortedCG sortedCG1 = (SortedCG)this.chgTbl.get(str4);
/*  625 */               stringBuffer.append("<!--   uplink feat scg " + sortedCG1 + " -->" + NEWLINE);
/*  626 */               if (sortedCG1 == null) {
/*      */ 
/*      */ 
/*      */                 
/*  630 */                 String str5 = entityItem1.getEntityGroup().getLongDescription() + ": " + getDisplayName(entityItem1, " - ", ", ");
/*  631 */                 String str6 = NEWLINE + "<!-- " + str4 + "  NO CG chgs -->" + NEWLINE + "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">" + str5 + "</th></tr>" + NEWLINE;
/*      */ 
/*      */ 
/*      */                 
/*  635 */                 String str7 = getSortKey(entityItem1, new String[] { "FEATURECODE" }, null);
/*  636 */                 sortedCG1 = new SortedCG(entityItem1, str7, str6);
/*  637 */                 this.chgTbl.put(str4, sortedCG1);
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  644 */               sortedCG.setSortKey(sortedCG1.getSortKey());
/*  645 */               sortedCG.addChild(sortedCG1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  650 */       entityList.dereference();
/*  651 */       str = stringBuffer.toString();
/*      */     } 
/*  653 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOtherStructure() throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, SQLException {
/*  672 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/*  675 */     if (this.featVct.size() > 0) {
/*      */ 
/*      */       
/*  678 */       Vector vector = new Vector();
/*      */       
/*  680 */       if (this.featVct.size() > 100) {
/*      */         
/*  682 */         int i = this.featVct.size() / 100;
/*  683 */         byte b1 = 0;
/*  684 */         for (byte b2 = 0; b2 <= i; b2++) {
/*      */           
/*  686 */           vector.clear();
/*  687 */           for (byte b = 0; b < 100; b++) {
/*      */             
/*  689 */             if (b1 == this.featVct.size())
/*      */               break; 
/*  691 */             vector.addElement(this.featVct.elementAt(b1++));
/*      */           } 
/*  693 */           if (vector.size() > 0) {
/*  694 */             findModelForFeature(vector, stringBuffer);
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*  699 */         findModelForFeature(this.featVct, stringBuffer);
/*      */       } 
/*  701 */       vector.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  709 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getSortKey(EntityItem paramEntityItem, String[] paramArrayOfString, EntityChangeHistoryGroup paramEntityChangeHistoryGroup) {
/*  721 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  723 */     ChangeHistoryItem changeHistoryItem = null;
/*  724 */     if (paramEntityChangeHistoryGroup != null)
/*  725 */       changeHistoryItem = paramEntityChangeHistoryGroup.getChangeHistoryItem(paramEntityChangeHistoryGroup.getChangeHistoryItemCount() - 1); 
/*  726 */     if (changeHistoryItem != null && !changeHistoryItem.isValid()) {
/*      */ 
/*      */       
/*  729 */       RowSelectableTable rowSelectableTable = paramEntityItem.getEntityItemTable();
/*  730 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*      */         
/*  732 */         String str = paramEntityItem.getEntityType() + ":" + paramArrayOfString[b];
/*      */         try {
/*  734 */           int i = rowSelectableTable.getRowIndex(str);
/*  735 */           if (i < 0) {
/*  736 */             i = rowSelectableTable.getRowIndex(str + ":C");
/*      */           }
/*  738 */           if (i < 0) {
/*  739 */             i = rowSelectableTable.getRowIndex(str + ":R");
/*      */           }
/*  741 */           if (i != -1) {
/*      */             
/*  743 */             EANAttribute eANAttribute = (EANAttribute)rowSelectableTable.getEANObject(i, 1);
/*  744 */             if (eANAttribute != null) {
/*      */               
/*  746 */               AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.dbCurrent.getAttributeChangeHistoryGroup(this.profile, eANAttribute);
/*  747 */               if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 1) {
/*      */ 
/*      */                 
/*  750 */                 ChangeHistoryItem changeHistoryItem1 = attributeChangeHistoryGroup.getChangeHistoryItem(attributeChangeHistoryGroup.getChangeHistoryItemCount() - 2);
/*  751 */                 if (!changeHistoryItem1.isActive()) {
/*      */                   continue;
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/*  757 */               if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */                 
/*  759 */                 ChangeHistoryItem changeHistoryItem1 = attributeChangeHistoryGroup.getChangeHistoryItem(attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1);
/*  760 */                 stringBuffer.append(changeHistoryItem1.get("ATTVAL", true).toString());
/*      */               } 
/*      */             } 
/*      */           } 
/*  764 */         } catch (Exception exception) {
/*  765 */           if (exception == null) {
/*  766 */             System.out.println(exception.getMessage());
/*      */           }
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/*      */     } else {
/*  773 */       for (byte b = 0; b < paramArrayOfString.length; b++)
/*  774 */         stringBuffer.append(getAttributeValue(paramEntityItem, paramArrayOfString[b], "", "")); 
/*      */     } 
/*  776 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void findModelForFeature(Vector<SortedCG> paramVector, StringBuffer paramStringBuffer) throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, SQLException {
/*  795 */     String str1 = "EXRPT3FM";
/*  796 */     String str2 = "FEATURE";
/*  797 */     ExtractActionItem extractActionItem = null;
/*  798 */     EntityList entityList = null;
/*  799 */     EntityGroup entityGroup = null;
/*  800 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramVector.size()]; byte b;
/*  801 */     for (b = 0; b < paramVector.size(); b++) {
/*      */       
/*  803 */       SortedCG sortedCG = paramVector.elementAt(b);
/*  804 */       arrayOfEntityItem[b] = new EntityItem(null, this.profile, "FEATURE", sortedCG.getEntityId());
/*      */     } 
/*      */     
/*  807 */     extractActionItem = new ExtractActionItem(null, this.dbCurrent, this.profile, str1);
/*      */ 
/*      */     
/*  810 */     entityList = this.dbCurrent.getEntityList(this.profile, extractActionItem, arrayOfEntityItem);
/*      */     
/*  812 */     paramStringBuffer.append("<!-- findModelForFeature()" + NEWLINE + "EntityList for extract " + str1 + " for " + str2 + " contains the following entities: " + NEWLINE);
/*  813 */     paramStringBuffer.append(outputList(entityList));
/*  814 */     paramStringBuffer.append(" -->" + NEWLINE);
/*      */     
/*  816 */     entityGroup = entityList.getParentEntityGroup();
/*  817 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */       
/*  819 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  820 */       String str = entityItem.getEntityType() + ":" + entityItem.getEntityID();
/*  821 */       SortedCG sortedCG = (SortedCG)this.chgTbl.get(str);
/*  822 */       paramStringBuffer.append("<!-- feature root " + str + " scg: " + sortedCG + " -->" + NEWLINE);
/*      */ 
/*      */       
/*  825 */       for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */         
/*  827 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/*  828 */         String str3 = entityItem1.getEntityType() + ":" + entityItem1.getEntityID();
/*  829 */         paramStringBuffer.append("<!--  downlink " + str3 + " -->" + NEWLINE);
/*  830 */         if (entityItem1.getEntityType().equals("PRODSTRUCT")) {
/*      */ 
/*      */ 
/*      */           
/*  834 */           String str4 = entityItem1.getEntityGroup().getLongDescription() + ": " + getDisplayName(entityItem1, " - ", ", ");
/*  835 */           String str5 = NEWLINE + "<!-- " + str3 + "  NO CG chgs -->" + NEWLINE + "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">" + str4 + "</th></tr>" + NEWLINE;
/*      */ 
/*      */ 
/*      */           
/*  839 */           SortedCG sortedCG1 = (SortedCG)this.chgTbl.get(str3);
/*  840 */           if (sortedCG1 == null) {
/*      */             
/*  842 */             sortedCG1 = new SortedCG(entityItem1, "", str5);
/*  843 */             sortedCG1.setSortKey(sortedCG.getSortKey());
/*      */ 
/*      */             
/*  846 */             sortedCG1.addChild(sortedCG);
/*      */ 
/*      */             
/*  849 */             for (byte b2 = 0; b2 < entityItem1.getDownLinkCount(); b2++) {
/*      */               
/*  851 */               EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b2);
/*  852 */               paramStringBuffer.append("<!--      downlink2 " + entityItem2.getEntityType() + ":" + entityItem2.getEntityID() + " -->" + NEWLINE);
/*  853 */               if (entityItem2.getEntityType().equals("MODEL"))
/*      */               {
/*  855 */                 addProdStructToModel(entityItem2, sortedCG1, paramStringBuffer);
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  861 */           this.featVct.remove(sortedCG);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  866 */     entityList.dereference();
/*  867 */     for (b = 0; b < arrayOfEntityItem.length; b++) {
/*  868 */       arrayOfEntityItem[b] = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addProdStructToModel(EntityItem paramEntityItem, SortedCG paramSortedCG, StringBuffer paramStringBuffer) throws MiddlewareRequestException, MiddlewareException, SQLException {
/*  882 */     String str = paramEntityItem.getEntityType() + ":" + paramEntityItem.getEntityID();
/*  883 */     SortedCG sortedCG = (SortedCG)this.chgTbl.get(str);
/*  884 */     paramStringBuffer.append("<!-- mdl " + str + "  -->" + NEWLINE);
/*  885 */     if (sortedCG == null) {
/*      */ 
/*      */ 
/*      */       
/*  889 */       String str1 = paramEntityItem.getEntityGroup().getLongDescription() + ": " + getDisplayName(paramEntityItem, " - ", ", ");
/*  890 */       String str2 = NEWLINE + "<!-- " + str + "  NO CG chgs -->" + NEWLINE + "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">" + str1 + "</th></tr>" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  895 */       String str3 = getSortKey(paramEntityItem, new String[] { "MACHTYPEATR", "MODELATR" }, null);
/*  896 */       paramStringBuffer.append("<!-- no match found in chgTbl for " + str + " -->" + NEWLINE);
/*  897 */       sortedCG = new SortedCG(paramEntityItem, str3, str2);
/*  898 */       this.chgTbl.put(str, sortedCG);
/*  899 */       this.mdlVct.addElement(sortedCG);
/*      */     } 
/*      */     
/*  902 */     sortedCG.addChild(paramSortedCG);
/*  903 */     paramStringBuffer.append("<!-- MDL SCG:: " + sortedCG + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMTM() {
/*  929 */     String str = "";
/*  930 */     if (this.cgEntityId != Integer.MIN_VALUE) {
/*  931 */       StringBuffer stringBuffer = new StringBuffer("<h4>Model</h4>" + NEWLINE);
/*  932 */       if (this.mdlVct.size() == 0) {
/*      */         
/*  934 */         stringBuffer.append("<p>No changes found.</p>" + NEWLINE);
/*      */       } else {
/*      */         byte b1;
/*      */ 
/*      */         
/*  939 */         for (b1 = 0; b1 < this.mdlVct.size(); b1++)
/*      */         {
/*  941 */           ((SortedCG)this.mdlVct.elementAt(b1)).sortIt();
/*      */         }
/*  943 */         Collections.sort(this.mdlVct);
/*      */         
/*  945 */         stringBuffer.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"MTM information\">" + NEWLINE + "<colgroup><col width=\"8%\"/><col width=\"10%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"7%\"/><col width=\"15%\"/></colgroup>" + NEWLINE);
/*      */ 
/*      */ 
/*      */         
/*  949 */         for (b1 = 0; b1 < this.mdlVct.size(); b1++) {
/*      */           
/*  951 */           SortedCG sortedCG = this.mdlVct.elementAt(b1);
/*  952 */           stringBuffer.append(sortedCG.getStructure(0));
/*      */         } 
/*  954 */         stringBuffer.append("</table>" + NEWLINE);
/*      */       } 
/*      */       
/*  957 */       stringBuffer.append("<h4>Stand-alone Feature</h4>" + NEWLINE);
/*  958 */       if (this.featVct.size() == 0) {
/*      */         
/*  960 */         stringBuffer.append("<p>No changes found.</p>" + NEWLINE);
/*      */       } else {
/*      */         byte b1;
/*      */ 
/*      */ 
/*      */         
/*  966 */         for (b1 = 0; b1 < this.featVct.size(); b1++)
/*      */         {
/*  968 */           ((SortedCG)this.featVct.elementAt(b1)).swapSortKey();
/*      */         }
/*      */ 
/*      */         
/*  972 */         Collections.sort(this.featVct);
/*      */         
/*  974 */         stringBuffer.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Feature information\">" + NEWLINE + "<colgroup><col width=\"8%\"/><col width=\"10%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"7%\"/><col width=\"15%\"/></colgroup>" + NEWLINE);
/*      */ 
/*      */         
/*  977 */         for (b1 = 0; b1 < this.featVct.size(); b1++) {
/*      */           
/*  979 */           SortedCG sortedCG = this.featVct.elementAt(b1);
/*  980 */           stringBuffer.append(sortedCG.getStructure(0));
/*      */         } 
/*  982 */         stringBuffer.append("</table>" + NEWLINE);
/*      */       } 
/*      */       
/*      */       byte b;
/*  986 */       for (b = 0; b < this.mdlVct.size(); b++) {
/*      */         
/*  988 */         SortedCG sortedCG = this.mdlVct.elementAt(b);
/*  989 */         sortedCG.dereference();
/*      */       } 
/*      */       
/*  992 */       for (b = 0; b < this.featVct.size(); b++) {
/*      */         
/*  994 */         SortedCG sortedCG = this.featVct.elementAt(b);
/*  995 */         sortedCG.dereference();
/*      */       } 
/*  997 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1000 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getModelConversion() throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, SQLException {
/* 1017 */     String str = "";
/* 1018 */     if (this.cgEntityId != Integer.MIN_VALUE) {
/* 1019 */       StringBuffer stringBuffer = new StringBuffer("<h2>Model Conversion / Upgrade</h2>" + NEWLINE);
/* 1020 */       String str1 = "EXDUMMY";
/*      */       
/* 1022 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.dbCurrent, this.profile, str1);
/*      */ 
/*      */       
/* 1025 */       EntityList entityList = new EntityList(this.dbCurrent, this.profile, this.cgEntityId, extractActionItem, "MODELCONVERT", this.startTime, this.endTime);
/*      */ 
/*      */       
/* 1028 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/* 1029 */       stringBuffer.append("<!-- EntityList for extract " + str1 + " for MODELCONVERT contains the following entities: " + NEWLINE);
/* 1030 */       stringBuffer.append(outputList(entityList));
/* 1031 */       stringBuffer.append(" -->" + NEWLINE);
/* 1032 */       if (entityGroup == null || entityGroup.getEntityItemCount() == 0) {
/*      */         
/* 1034 */         stringBuffer.append("<p>No changes found.</p>" + NEWLINE);
/*      */       } else {
/*      */         
/* 1037 */         stringBuffer.append(fillInTable(entityGroup, MODELCNV_ATTR));
/*      */       } 
/* 1039 */       entityList.dereference();
/* 1040 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1043 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFeatureTrans() throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, SQLException {
/* 1060 */     String str = "";
/* 1061 */     if (this.cgEntityId != Integer.MIN_VALUE) {
/* 1062 */       StringBuffer stringBuffer = new StringBuffer("<h2>Feature Transactions</h2>" + NEWLINE);
/* 1063 */       String str1 = "EXDUMMY";
/*      */       
/* 1065 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.dbCurrent, this.profile, str1);
/*      */       
/* 1067 */       EntityList entityList = new EntityList(this.dbCurrent, this.profile, this.cgEntityId, extractActionItem, "FCTRANSACTION", this.startTime, this.endTime);
/*      */ 
/*      */       
/* 1070 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/* 1071 */       stringBuffer.append("<!-- EntityList for extract " + str1 + " for FCTRANSACTION contains the following entities: " + NEWLINE);
/* 1072 */       stringBuffer.append(outputList(entityList));
/* 1073 */       stringBuffer.append(" -->" + NEWLINE);
/* 1074 */       if (entityGroup == null || entityGroup.getEntityItemCount() == 0) {
/* 1075 */         stringBuffer.append("<p>No changes found.</p>" + NEWLINE);
/*      */       } else {
/*      */         
/* 1078 */         stringBuffer.append(fillInTable(entityGroup, FCTRANS_ATTR));
/*      */       } 
/*      */       
/* 1081 */       entityList.dereference();
/* 1082 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1085 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String fillInTable(EntityGroup paramEntityGroup, String[] paramArrayOfString) throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, SQLException {
/* 1101 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 1103 */     Vector<SortedCG> vector = new Vector(); byte b;
/* 1104 */     for (b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*      */       
/* 1106 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*      */       
/* 1108 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 1109 */       EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(this.dbCurrent, this.profile, entityItem);
/* 1110 */       boolean bool = getEntityInfo(entityItem, paramArrayOfString, stringBuffer1, entityChangeHistoryGroup);
/* 1111 */       if (bool) {
/*      */         
/* 1113 */         SortedCG sortedCG = null;
/* 1114 */         String str = "";
/* 1115 */         ChangeHistoryItem changeHistoryItem = entityChangeHistoryGroup.getChangeHistoryItem(entityChangeHistoryGroup.getChangeHistoryItemCount() - 1);
/* 1116 */         if (!changeHistoryItem.isValid()) {
/* 1117 */           str = getDeletedDisplayName(entityItem, "", true, null, true);
/*      */         } else {
/* 1119 */           str = getDisplayName(entityItem, "", "");
/*      */         } 
/* 1121 */         sortedCG = new SortedCG(entityItem, str, stringBuffer1.toString());
/* 1122 */         vector.addElement(sortedCG);
/*      */       } else {
/*      */         
/* 1125 */         stringBuffer.append(stringBuffer1.toString());
/*      */       } 
/*      */     } 
/* 1128 */     if (vector.size() == 0) {
/*      */       
/* 1130 */       stringBuffer.append("<p>No changes found.</p>" + NEWLINE);
/*      */     }
/*      */     else {
/*      */       
/* 1134 */       Collections.sort(vector);
/*      */       
/* 1136 */       stringBuffer.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Change group information\">" + NEWLINE + "<colgroup><col width=\"8%\"/><col width=\"10%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"7%\"/><col width=\"15%\"/></colgroup>" + NEWLINE);
/*      */ 
/*      */       
/* 1139 */       for (b = 0; b < vector.size(); b++) {
/*      */         
/* 1141 */         SortedCG sortedCG = vector.elementAt(b);
/* 1142 */         stringBuffer.append(sortedCG.getStructure(0));
/*      */       } 
/* 1144 */       stringBuffer.append("</table>" + NEWLINE);
/*      */ 
/*      */       
/* 1147 */       for (b = 0; b < vector.size(); b++) {
/*      */         
/* 1149 */         SortedCG sortedCG = vector.elementAt(b);
/* 1150 */         sortedCG.dereference();
/*      */       } 
/* 1152 */       vector.clear();
/*      */     } 
/* 1154 */     vector = null;
/*      */     
/* 1156 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getEntityInfo(EntityItem paramEntityItem, String[] paramArrayOfString, StringBuffer paramStringBuffer, EntityChangeHistoryGroup paramEntityChangeHistoryGroup) throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, SQLException {
/* 1176 */     String str1 = paramEntityItem.getEntityGroup().getLongDescription() + ": " + getDisplayName(paramEntityItem, " - ", ", ");
/* 1177 */     byte b1 = 0;
/* 1178 */     boolean bool1 = false;
/* 1179 */     StringBuffer stringBuffer = new StringBuffer();
/* 1180 */     String str2 = null;
/*      */     
/* 1182 */     boolean bool2 = false;
/* 1183 */     boolean bool = false;
/*      */     
/* 1185 */     String str3 = "";
/*      */     
/* 1187 */     ChangeHistoryItem changeHistoryItem = paramEntityChangeHistoryGroup.getChangeHistoryItem(paramEntityChangeHistoryGroup.getChangeHistoryItemCount() - 1);
/* 1188 */     paramStringBuffer.append(NEWLINE + "<!-- " + paramEntityItem.getEntityType() + ":" + paramEntityItem.getEntityID() + " -->" + NEWLINE);
/* 1189 */     if (!changeHistoryItem.isValid()) {
/*      */       
/* 1191 */       String str = changeHistoryItem.getChangeDate();
/* 1192 */       str = str.substring(0, str.length() - 16);
/*      */       
/* 1194 */       str3 = (paramEntityItem.getEntityGroup().isRelator() ? "Relator" : "Entity") + " was deleted on " + str + " by " + changeHistoryItem.getUser() + " [" + changeHistoryItem.get("ROLE", true) + "]";
/* 1195 */       paramStringBuffer.append("<!-- ****** Entity " + paramEntityItem.getEntityType() + ":" + paramEntityItem.getEntityID() + " has been deleted by " + changeHistoryItem
/* 1196 */           .getChangeGroup() + "-->" + NEWLINE);
/* 1197 */       paramStringBuffer.append("<!-- ***** ChangeHistoryGroup for " + paramEntityItem.getEntityType() + ":" + paramEntityItem.getEntityID() + NEWLINE);
/* 1198 */       for (int i = paramEntityChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*      */         
/* 1200 */         ChangeHistoryItem changeHistoryItem1 = paramEntityChangeHistoryGroup.getChangeHistoryItem(i);
/* 1201 */         paramStringBuffer.append("ChangeHistoryItem[" + i + "] chgGrp: " + changeHistoryItem1.getChangeGroup() + " chgDate: " + changeHistoryItem1
/* 1202 */             .getChangeDate() + " isValid: " + changeHistoryItem1.isValid() + " isActive: " + changeHistoryItem1.isActive() + NEWLINE);
/*      */       } 
/* 1204 */       paramStringBuffer.append("-->" + NEWLINE);
/*      */       
/* 1206 */       bool = wasChangedByCG(changeHistoryItem);
/*      */       
/* 1208 */       str1 = getDeletedDisplayName(paramEntityItem, " - ", bool, paramStringBuffer, false) + "<br />" + str3;
/*      */       
/* 1210 */       if (bool) {
/* 1211 */         bool2 = true;
/*      */       }
/*      */     } 
/* 1214 */     str2 = "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">" + str1 + "</th></tr>" + NEWLINE + "<tr style=\"background-color:#cef;\"><th style=\"text-align:center;\" id=\"changeType\">Change Type</th><th style=\"text-align:center;\" id=\"attribute\">Attribute</th><th style=\"text-align:center;\" id=\"original\">Original Value</th><th style=\"text-align:center;\" id=\"chgValue\">Change Group Value</th><th style=\"text-align:center;\" id=\"current\">Current Value</th><th style=\"text-align:center;\" id=\"chgDate\">Date Changed</th><th style=\"text-align:center;\" id=\"chgBy\">Changed By</th></tr>" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1227 */     for (byte b2 = 0; b2 < paramArrayOfString.length; b2++) {
/*      */       
/* 1229 */       String str = paramArrayOfString[b2];
/*      */ 
/*      */       
/* 1232 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = hasAttributeChgd(paramEntityItem, str, paramStringBuffer);
/* 1233 */       if (attributeChangeHistoryGroup == null) {
/*      */         
/* 1235 */         paramStringBuffer.append("<!-- " + str + " was not changed in the CHANGEGROUP -->" + NEWLINE);
/*      */       } else {
/*      */         
/* 1238 */         bool1 = true;
/* 1239 */         if (!b1)
/* 1240 */           stringBuffer.append(str2); 
/* 1241 */         stringBuffer.append("<!-- " + str + " was changed by CG -->" + NEWLINE);
/*      */         
/* 1243 */         stringBuffer.append(getAttributeInfo(paramEntityItem, str, b1++, attributeChangeHistoryGroup));
/*      */       } 
/*      */     } 
/* 1246 */     if (!bool1) {
/*      */       
/* 1248 */       paramStringBuffer.append("<!-- ** NO CG chgs found in specified attr set for " + paramEntityItem.getEntityType() + ":" + paramEntityItem
/* 1249 */           .getEntityID() + " -->" + NEWLINE);
/* 1250 */       if (bool2) {
/* 1251 */         paramStringBuffer.append("<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">" + str1 + "</th></tr>" + NEWLINE);
/*      */       }
/*      */     } 
/*      */     
/* 1255 */     paramStringBuffer.append(stringBuffer.toString());
/*      */     
/* 1257 */     return (bool2 || bool1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup hasAttributeChgd(EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
/* 1274 */     AttributeChangeHistoryGroup attributeChangeHistoryGroup = null;
/* 1275 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 1279 */     RowSelectableTable rowSelectableTable = paramEntityItem.getEntityItemTable();
/* 1280 */     String str = paramEntityItem.getEntityType() + ":" + paramString;
/* 1281 */     stringBuffer.append("<!-- ");
/*      */     try {
/* 1283 */       int i = rowSelectableTable.getRowIndex(str);
/* 1284 */       if (i < 0) {
/* 1285 */         i = rowSelectableTable.getRowIndex(str + ":C");
/*      */       }
/* 1287 */       if (i < 0) {
/* 1288 */         i = rowSelectableTable.getRowIndex(str + ":R");
/*      */       }
/* 1290 */       if (i != -1)
/*      */       
/* 1292 */       { EANAttribute eANAttribute = (EANAttribute)rowSelectableTable.getEANObject(i, 1);
/* 1293 */         if (eANAttribute != null) {
/*      */           
/* 1295 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = this.dbCurrent.getAttributeChangeHistoryGroup(this.profile, eANAttribute);
/* 1296 */           stringBuffer.append("ChangeHistoryGroup for Attribute: " + paramString);
/*      */ 
/*      */           
/* 1299 */           for (int j = attributeChangeHistoryGroup1.getChangeHistoryItemCount() - 1; j >= 0; j--) {
/*      */             
/* 1301 */             ChangeHistoryItem changeHistoryItem = attributeChangeHistoryGroup1.getChangeHistoryItem(j);
/* 1302 */             stringBuffer.append(NEWLINE + "AttrChangeHistoryItem[" + j + "] chgDate: " + changeHistoryItem.getChangeDate() + " isValid: " + changeHistoryItem
/* 1303 */                 .isValid() + " isActive: " + changeHistoryItem
/* 1304 */                 .isActive() + " value: " + 
/* 1305 */                 convertToHTML(changeHistoryItem.get("ATTVAL", true).toString(), false) + " chgGrp: *" + changeHistoryItem
/* 1306 */                 .getChangeGroup() + "*  user: " + changeHistoryItem.getUser());
/* 1307 */             if (wasChangedByCG(changeHistoryItem)) {
/* 1308 */               attributeChangeHistoryGroup = attributeChangeHistoryGroup1;
/*      */             }
/*      */           } 
/*      */         } else {
/* 1312 */           stringBuffer.append("EANAttribute was null for " + paramString + " in RowSelectableTable!");
/*      */         }  }
/*      */       else
/* 1315 */       { stringBuffer.append("Row not found for " + paramString + " in RowSelectableTable!"); } 
/* 1316 */     } catch (Exception exception) {
/* 1317 */       stringBuffer.append("Exception getting " + paramString + " from RowSelectableTable: " + exception.getMessage() + "");
/*      */     } 
/*      */     
/* 1320 */     stringBuffer.append(" -->" + NEWLINE);
/* 1321 */     if (attributeChangeHistoryGroup == null) {
/* 1322 */       paramStringBuffer.append(stringBuffer.toString());
/*      */     }
/* 1324 */     return attributeChangeHistoryGroup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean wasChangedByCG(ChangeHistoryItem paramChangeHistoryItem) {
/* 1335 */     return this.cgIdentifier.equals(paramChangeHistoryItem.getChangeGroup().trim());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getFirstChangeId(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) {
/* 1346 */     byte b = -1;
/* 1347 */     for (byte b1 = 0; b1 < paramAttributeChangeHistoryGroup.getChangeHistoryItemCount(); b1++) {
/*      */       
/* 1349 */       ChangeHistoryItem changeHistoryItem = paramAttributeChangeHistoryGroup.getChangeHistoryItem(b1);
/* 1350 */       if (wasChangedByCG(changeHistoryItem)) {
/* 1351 */         b = b1;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1355 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getLastChangeId(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) {
/* 1365 */     int i = -1;
/* 1366 */     for (int j = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; j >= 0; j--) {
/*      */       
/* 1368 */       ChangeHistoryItem changeHistoryItem = paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 1369 */       if (wasChangedByCG(changeHistoryItem)) {
/* 1370 */         i = j;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1374 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getAttributeInfo(EntityItem paramEntityItem, String paramString, int paramInt, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) {
/* 1427 */     StringBuffer stringBuffer = new StringBuffer();
/* 1428 */     String str1 = "Changed";
/* 1429 */     String str2 = null;
/* 1430 */     String str3 = "&nbsp;";
/* 1431 */     String str4 = "&nbsp;";
/* 1432 */     ChangeHistoryItem changeHistoryItem1 = null;
/* 1433 */     int i = 0;
/* 1434 */     int j = 0;
/* 1435 */     ChangeHistoryItem changeHistoryItem2 = null;
/* 1436 */     String str5 = null;
/* 1437 */     String str6 = null;
/*      */     
/* 1439 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString);
/* 1440 */     String str7 = paramString;
/* 1441 */     String str8 = "";
/* 1442 */     if (eANMetaAttribute != null) {
/*      */       
/* 1444 */       str7 = eANMetaAttribute.getActualLongDescription();
/* 1445 */       str8 = eANMetaAttribute.getAttributeType();
/*      */     } 
/*      */ 
/*      */     
/* 1449 */     changeHistoryItem1 = paramAttributeChangeHistoryGroup.getChangeHistoryItem(paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1);
/*      */     
/* 1451 */     i = getFirstChangeId(paramAttributeChangeHistoryGroup);
/* 1452 */     j = getLastChangeId(paramAttributeChangeHistoryGroup);
/*      */     
/* 1454 */     stringBuffer.append("<!-- ChangeHistoryGroup for Attribute: " + paramString);
/* 1455 */     for (int k = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; k >= 0; k--) {
/*      */       
/* 1457 */       ChangeHistoryItem changeHistoryItem = paramAttributeChangeHistoryGroup.getChangeHistoryItem(k);
/* 1458 */       stringBuffer.append(NEWLINE + "AttrChangeHistoryItem[" + k + "] chgDate: " + changeHistoryItem.getChangeDate() + " isValid: " + changeHistoryItem
/* 1459 */           .isValid() + " isActive: " + changeHistoryItem
/* 1460 */           .isActive() + " value: " + 
/* 1461 */           convertToHTML(changeHistoryItem.get("ATTVAL", true).toString(), false) + " chgGrp: *" + changeHistoryItem
/* 1462 */           .getChangeGroup() + "*  user: " + changeHistoryItem.getUser());
/*      */     } 
/* 1464 */     stringBuffer.append(" -->" + NEWLINE);
/* 1465 */     stringBuffer.append("<!-- firstId: " + i + " lastId: " + j + " ");
/* 1466 */     if (i == -1)
/* 1467 */       i = 0; 
/* 1468 */     if (j == -1) {
/* 1469 */       j = 0;
/*      */     }
/*      */     
/* 1472 */     if (i != 0) {
/*      */       
/* 1474 */       ChangeHistoryItem changeHistoryItem = paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*      */       
/* 1476 */       if (!changeHistoryItem.isActive()) {
/*      */         
/* 1478 */         stringBuffer.append("CG recreated this attr at index: " + i + " ");
/* 1479 */         str3 = "&nbsp;";
/* 1480 */         str1 = "Restored";
/*      */ 
/*      */       
/*      */       }
/* 1484 */       else if (str8.equals("X")) {
/* 1485 */         str3 = changeHistoryItem.get("ATTVAL", true).toString();
/*      */       } else {
/* 1487 */         str3 = convertToHTML(changeHistoryItem.get("ATTVAL", true).toString());
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1493 */     else if (j == 0) {
/* 1494 */       str1 = "New";
/*      */     } 
/*      */ 
/*      */     
/* 1498 */     changeHistoryItem2 = paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 1499 */     str5 = changeHistoryItem2.getUser() + " [" + changeHistoryItem2.get("ROLE", true) + "]";
/* 1500 */     str6 = changeHistoryItem2.getChangeDate();
/* 1501 */     str6 = str6.substring(0, str6.length() - 16);
/*      */     
/* 1503 */     if (!changeHistoryItem2.isActive()) {
/*      */       
/* 1505 */       stringBuffer.append(" DELETION by this CG was found at index: " + j + NEWLINE);
/* 1506 */       str2 = "&nbsp;";
/* 1507 */       str1 = "Deleted";
/*      */     }
/*      */     else {
/*      */       
/* 1511 */       if (j != 0) {
/*      */         
/* 1513 */         ChangeHistoryItem changeHistoryItem = paramAttributeChangeHistoryGroup.getChangeHistoryItem(j - 1);
/*      */         
/* 1515 */         if (!changeHistoryItem.isActive()) {
/*      */           
/* 1517 */           stringBuffer.append("CG recreated this attr at index: " + j + " ");
/* 1518 */           str1 = "Restored";
/*      */         } 
/*      */       } 
/*      */       
/* 1522 */       if (str8.equals("X")) {
/* 1523 */         str2 = changeHistoryItem2.get("ATTVAL", true).toString();
/*      */       } else {
/* 1525 */         str2 = convertToHTML(changeHistoryItem2.get("ATTVAL", true).toString());
/*      */       } 
/*      */     } 
/* 1528 */     if (!changeHistoryItem1.isValid()) {
/*      */       
/* 1530 */       stringBuffer.append("Attr is not valid");
/* 1531 */       str4 = "&nbsp;";
/* 1532 */       if (!wasChangedByCG(changeHistoryItem1))
/*      */       {
/* 1534 */         stringBuffer.append(" AND was NOT deleted by this CG (was CG: " + changeHistoryItem1.getChangeGroup() + ")" + NEWLINE);
/* 1535 */         str1 = str1 + "/" + "Deleted" + "*";
/*      */       }
/*      */       else
/*      */       {
/* 1539 */         str2 = "&nbsp;";
/* 1540 */         str1 = "Deleted";
/* 1541 */         stringBuffer.append(" and was deleted by this CG" + NEWLINE);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1546 */       if (str8.equals("X")) {
/* 1547 */         str4 = changeHistoryItem1.get("ATTVAL", true).toString();
/*      */       } else {
/* 1549 */         str4 = convertToHTML(changeHistoryItem1.get("ATTVAL", true).toString());
/* 1550 */       }  if (!str2.equals(str4))
/*      */       {
/* 1552 */         if (!changeHistoryItem2.isActive()) {
/* 1553 */           str1 = str1 + "/" + "Restored" + "*";
/*      */         } else {
/* 1555 */           str1 = str1 + "/" + "Changed" + "*";
/*      */         } 
/*      */       }
/*      */     } 
/* 1559 */     stringBuffer.append(" -->" + NEWLINE);
/*      */     
/* 1561 */     stringBuffer.append("<tr class=\"" + ((paramInt % 2 != 0) ? "even" : "odd") + "\">");
/*      */     
/* 1563 */     stringBuffer.append("<td headers=\"changeType displayName\">" + str1 + "</td>");
/*      */     
/* 1565 */     stringBuffer.append("<td headers=\"attribute displayName\">" + str7 + "</td>");
/*      */     
/* 1567 */     stringBuffer.append("<td headers=\"original displayName\">" + str3 + "</td>");
/*      */     
/* 1569 */     stringBuffer.append("<td headers=\"chgValue displayName\">" + str2 + "</td>");
/*      */     
/* 1571 */     stringBuffer.append("<td headers=\"current displayName\">" + str4 + "</td>");
/*      */     
/* 1573 */     stringBuffer.append("<td nowrap headers=\"chgDate displayName\">" + str6 + "</td>");
/*      */     
/* 1575 */     stringBuffer.append("<td headers=\"chgBy displayName\">" + str5 + "</td>");
/* 1576 */     stringBuffer.append("</tr>" + NEWLINE);
/*      */     
/* 1578 */     replaceBraces(stringBuffer);
/*      */     
/* 1580 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void replaceBraces(StringBuffer paramStringBuffer) {
/* 1586 */     replaceText(paramStringBuffer, "{", "XOPENBRACEX");
/*      */   }
/*      */ 
/*      */   
/*      */   static void restoreBraces(StringBuffer paramStringBuffer) {
/* 1591 */     replaceText(paramStringBuffer, "XOPENBRACEX", "{");
/*      */   }
/*      */   
/*      */   private static void replaceText(StringBuffer paramStringBuffer, String paramString1, String paramString2) {
/* 1595 */     int i = 0;
/* 1596 */     while (i != -1) {
/*      */       
/* 1598 */       i = paramStringBuffer.toString().indexOf(paramString1, i);
/* 1599 */       if (i != -1)
/*      */       {
/* 1601 */         paramStringBuffer.replace(i, i + paramString1.length(), paramString2);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDeletedDisplayName(EntityItem paramEntityItem, String paramString, boolean paramBoolean1, StringBuffer paramStringBuffer, boolean paramBoolean2) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 1627 */     StringBuffer stringBuffer = new StringBuffer();
/* 1628 */     EANList eANList = null;
/* 1629 */     RowSelectableTable rowSelectableTable = null;
/* 1630 */     boolean bool = true;
/*      */     
/* 1632 */     if (!paramBoolean2) {
/* 1633 */       stringBuffer.append("Deleted");
/*      */     }
/*      */     
/* 1636 */     eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1637 */     if (eANList == null) {
/*      */       
/* 1639 */       EntityGroup entityGroup = new EntityGroup(null, this.dbCurrent, this.profile, paramEntityItem.getEntityType(), "Navigate");
/* 1640 */       eANList = entityGroup.getMetaAttribute();
/* 1641 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1643 */     if (!paramBoolean2) {
/*      */       
/* 1645 */       if (!paramBoolean1)
/* 1646 */         stringBuffer.append("*"); 
/* 1647 */       stringBuffer.append(" - " + paramEntityItem.getEntityGroup().getLongDescription() + ": ");
/*      */     } 
/*      */ 
/*      */     
/* 1651 */     rowSelectableTable = paramEntityItem.getEntityItemTable();
/* 1652 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1654 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1655 */       String str = "" + paramEntityItem.getEntityType() + ":" + eANMetaAttribute.getAttributeCode();
/*      */       try {
/* 1657 */         int i = rowSelectableTable.getRowIndex(str);
/* 1658 */         if (i < 0) {
/* 1659 */           i = rowSelectableTable.getRowIndex(str + ":C");
/*      */         }
/* 1661 */         if (i < 0) {
/* 1662 */           i = rowSelectableTable.getRowIndex(str + ":R");
/*      */         }
/* 1664 */         if (i != -1)
/*      */         {
/* 1666 */           EANAttribute eANAttribute = (EANAttribute)rowSelectableTable.getEANObject(i, 1);
/* 1667 */           if (eANAttribute != null) {
/*      */             
/* 1669 */             AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.dbCurrent.getAttributeChangeHistoryGroup(this.profile, eANAttribute);
/* 1670 */             if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 1) {
/*      */ 
/*      */               
/* 1673 */               ChangeHistoryItem changeHistoryItem = attributeChangeHistoryGroup.getChangeHistoryItem(attributeChangeHistoryGroup.getChangeHistoryItemCount() - 2);
/* 1674 */               if (!changeHistoryItem.isActive()) {
/*      */                 
/* 1676 */                 if (paramStringBuffer != null)
/* 1677 */                   paramStringBuffer.append("<!-- " + eANMetaAttribute.getAttributeCode() + " was DELETED before Entity -->" + NEWLINE); 
/*      */                 continue;
/*      */               } 
/*      */             } 
/* 1681 */             if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */               
/* 1683 */               ChangeHistoryItem changeHistoryItem = attributeChangeHistoryGroup.getChangeHistoryItem(attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1);
/* 1684 */               String str1 = changeHistoryItem.get("ATTVAL", true).toString();
/* 1685 */               if (!bool)
/* 1686 */                 stringBuffer.append(paramString); 
/* 1687 */               stringBuffer.append(str1);
/* 1688 */               bool = false;
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1692 */           if (paramStringBuffer != null) {
/* 1693 */             paramStringBuffer.append("<!--getDeletedDisplayName() EANAttribute was null for " + eANMetaAttribute.getAttributeCode() + " in RowSelectableTable!-->" + NEWLINE);
/*      */           }
/*      */         }
/*      */       
/* 1697 */       } catch (Exception exception) {
/* 1698 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 1699 */         PrintWriter printWriter = null;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       continue;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1716 */     replaceBraces(stringBuffer);
/*      */     
/* 1718 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDisplayName(EntityItem paramEntityItem, String paramString1, String paramString2) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 1738 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1741 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1742 */     if (eANList == null) {
/*      */       
/* 1744 */       EntityGroup entityGroup = new EntityGroup(null, this.dbCurrent, paramEntityItem.getProfile(), paramEntityItem.getEntityType(), "Navigate");
/* 1745 */       eANList = entityGroup.getMetaAttribute();
/* 1746 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/*      */     
/* 1749 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1751 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1752 */       String str = getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), paramString2, null);
/* 1753 */       if (str != null) {
/*      */         
/* 1755 */         if (stringBuffer.length() > 0)
/* 1756 */           stringBuffer.append(paramString1); 
/* 1757 */         stringBuffer.append(str);
/*      */       } 
/*      */     } 
/*      */     
/* 1761 */     replaceBraces(stringBuffer);
/*      */     
/* 1763 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAttributeValue(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3) {
/* 1777 */     return getAttributeValue(paramEntityItem, paramString1, paramString2, paramString3, true, "eannounce");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAttributeValue(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1793 */     return getAttributeValue(paramEntityItem, paramString1, paramString2, paramString3, paramBoolean, "eannounce");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAttributeValue(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4) {
/* 1810 */     String str = paramString3;
/* 1811 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1812 */     if (eANMetaAttribute == null) {
/*      */       
/* 1814 */       str = "<span style=\"color:#c00;\">Attribute &quot;" + paramString1 + "&quot; NOT found in &quot;" + paramEntityItem.getEntityType() + "&quot; META data.</span>";
/*      */     } else {
/*      */       
/* 1817 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 1818 */       if (eANAttribute != null) {
/* 1819 */         StringBuffer stringBuffer = new StringBuffer();
/* 1820 */         if (eANAttribute instanceof COM.ibm.eannounce.objects.EANFlagAttribute) {
/*      */ 
/*      */           
/* 1823 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 1824 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */ 
/*      */             
/* 1827 */             if (arrayOfMetaFlag[b].isSelected()) {
/*      */               
/* 1829 */               if (stringBuffer.length() > 0) {
/* 1830 */                 stringBuffer.append(paramString2);
/*      */               }
/* 1832 */               if (paramBoolean) {
/* 1833 */                 stringBuffer.append(convertToHTML(arrayOfMetaFlag[b].toString()));
/*      */               } else {
/* 1835 */                 stringBuffer.append(arrayOfMetaFlag[b].toString());
/*      */               } 
/*      */             } 
/*      */           } 
/* 1839 */         } else if (eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/*      */ 
/*      */           
/* 1842 */           if (eANMetaAttribute.getAttributeType().equals("T") || eANMetaAttribute.getAttributeType().equals("L") || eANMetaAttribute
/* 1843 */             .getAttributeType().equals("I")) {
/*      */ 
/*      */             
/* 1846 */             if (paramBoolean) {
/* 1847 */               stringBuffer.append(convertToHTML(eANAttribute.get().toString()));
/*      */             } else {
/* 1849 */               stringBuffer.append(eANAttribute.get().toString());
/*      */             } 
/*      */           } else {
/* 1852 */             stringBuffer.append(eANAttribute.get().toString());
/*      */           } 
/* 1854 */         } else if (eANAttribute instanceof EANBlobAttribute) {
/*      */ 
/*      */           
/* 1857 */           if (eANMetaAttribute.getAttributeType().equals("B")) {
/*      */             
/* 1859 */             EANBlobAttribute eANBlobAttribute = (EANBlobAttribute)eANAttribute;
/*      */ 
/*      */ 
/*      */             
/* 1863 */             if (eANBlobAttribute.getBlobExtension().toUpperCase().endsWith(".GIF") || eANBlobAttribute
/* 1864 */               .getBlobExtension().toUpperCase().endsWith(".JPG"))
/*      */             {
/* 1866 */               stringBuffer.append("<img src='/" + paramString4 + "/GetBlobAttribute?entityID=" + paramEntityItem.getEntityID() + "&entityType=" + paramEntityItem
/* 1867 */                   .getEntityType() + "&attributeCode=" + paramString1 + "' />");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1885 */               stringBuffer.append("<form action=\"/" + paramString4 + "/PokXMLDownload\" name=\"" + paramEntityItem.getEntityType() + paramEntityItem
/* 1886 */                   .getEntityID() + paramString1 + "\" method=\"post\"> " + NEWLINE);
/* 1887 */               stringBuffer.append("<input type=\"hidden\" name=\"entityType\" value=\"" + paramEntityItem.getEntityType() + "\" />" + NEWLINE);
/* 1888 */               stringBuffer.append("<input type=\"hidden\" name=\"entityID\" value=\"" + paramEntityItem.getEntityID() + "\" />" + NEWLINE);
/* 1889 */               stringBuffer.append("<input type=\"hidden\" name=\"downloadType\" value=\"blob\" />" + NEWLINE);
/* 1890 */               stringBuffer.append("<input type=\"hidden\" name=\"attributeCode\" value=\"" + paramString1 + "\" />" + NEWLINE);
/* 1891 */               stringBuffer.append("<input type=\"submit\" value=\"Down load\" />" + NEWLINE);
/* 1892 */               stringBuffer.append("</form>" + NEWLINE);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1899 */             stringBuffer.append("<span style=\"color:#c00;\">Blob Attribute type &quot;" + eANMetaAttribute.getAttributeType() + "&quot; for " + paramString1 + " NOT yet supported</span>");
/*      */           } 
/*      */         } 
/*      */         
/* 1903 */         if (stringBuffer.length() > 0) {
/* 1904 */           str = stringBuffer.toString();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1909 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String convertToHTML(String paramString) {
/* 1920 */     return convertToHTML(paramString, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String convertToHTML(String paramString, boolean paramBoolean) {
/* 1932 */     String str = paramString;
/* 1933 */     if (paramString != null) {
/*      */       
/* 1935 */       StringBuffer stringBuffer = new StringBuffer();
/* 1936 */       StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
/* 1937 */       char c = stringCharacterIterator.first();
/* 1938 */       while (c != Character.MAX_VALUE) {
/*      */         
/* 1940 */         switch (c) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case '"':
/*      */           case '<':
/*      */           case '>':
/* 1948 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */           case '\n':
/* 1951 */             if (paramBoolean) {
/* 1952 */               stringBuffer.append("<br />"); break;
/*      */             } 
/* 1954 */             stringBuffer.append(c);
/*      */             break;
/*      */           default:
/* 1957 */             if (paramBoolean && Character.isSpaceChar(c)) {
/*      */               
/* 1959 */               stringBuffer.append("&#32;");
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/* 1964 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 1967 */         c = stringCharacterIterator.next();
/*      */       } 
/* 1969 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1972 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAttributeDescription(EntityGroup paramEntityGroup, String paramString1, String paramString2) {
/* 1985 */     String str = paramString2;
/* 1986 */     if (paramEntityGroup != null) {
/* 1987 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(paramString1);
/* 1988 */       if (eANMetaAttribute != null) {
/* 1989 */         str = eANMetaAttribute.getActualLongDescription();
/*      */       }
/*      */     } 
/* 1992 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAttributeFlagValue(EntityItem paramEntityItem, String paramString) {
/* 2004 */     String str = null;
/*      */     
/* 2006 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 2007 */     if (eANAttribute != null && 
/* 2008 */       eANAttribute instanceof COM.ibm.eannounce.objects.EANFlagAttribute) {
/*      */       
/* 2010 */       StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */       
/* 2013 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 2014 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */ 
/*      */         
/* 2017 */         if (arrayOfMetaFlag[b].isSelected()) {
/*      */           
/* 2019 */           if (stringBuffer.length() > 0)
/* 2020 */             stringBuffer.append("|"); 
/* 2021 */           stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/*      */         } 
/*      */       } 
/* 2024 */       str = stringBuffer.toString();
/*      */     } 
/*      */ 
/*      */     
/* 2028 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String outputList(EntityList paramEntityList) {
/* 2039 */     StringBuffer stringBuffer = new StringBuffer();
/* 2040 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 2041 */     if (entityGroup != null) {
/*      */       
/* 2043 */       stringBuffer.append(entityGroup.getEntityType() + " : " + entityGroup.getEntityItemCount() + " Parent entity items. ");
/* 2044 */       if (entityGroup.getEntityItemCount() > 0) {
/*      */         
/* 2046 */         stringBuffer.append("IDs(");
/* 2047 */         for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++)
/* 2048 */           stringBuffer.append(" " + entityGroup.getEntityItem(b1).getEntityID()); 
/* 2049 */         stringBuffer.append(")");
/*      */       } 
/* 2051 */       stringBuffer.append(NEWLINE);
/*      */     } 
/*      */     
/* 2054 */     for (byte b = 0; b < paramEntityList.getEntityGroupCount(); b++) {
/*      */       
/* 2056 */       EntityGroup entityGroup1 = paramEntityList.getEntityGroup(b);
/* 2057 */       stringBuffer.append(entityGroup1.getEntityType() + " : " + entityGroup1.getEntityItemCount() + " entity items. ");
/* 2058 */       if (entityGroup1.getEntityItemCount() > 0) {
/*      */         
/* 2060 */         stringBuffer.append("IDs(");
/* 2061 */         for (byte b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++)
/* 2062 */           stringBuffer.append(" " + entityGroup1.getEntityItem(b1).getEntityID()); 
/* 2063 */         stringBuffer.append(")");
/*      */       } 
/* 2065 */       stringBuffer.append(NEWLINE);
/*      */     } 
/* 2067 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SortedCG
/*      */     implements Comparable
/*      */   {
/*      */     private String eType;
/*      */     
/*      */     private int eId;
/* 2077 */     private Vector children = new Vector(); private String sortKey;
/*      */     private String sortKey2;
/*      */     private String html;
/* 2080 */     private static final String[] CSSLVL = new String[] { "background-color:#69c;", "background-color:#fe2;", "background-color:#bd6;", "background-color:#f90;" };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SortedCG(EntityItem param1EntityItem, String param1String1, String param1String2) {
/* 2086 */       this.eType = param1EntityItem.getEntityType();
/* 2087 */       this.eId = param1EntityItem.getEntityID();
/* 2088 */       this.sortKey = param1String1;
/* 2089 */       this.sortKey2 = param1String1;
/* 2090 */       this.html = param1String2;
/*      */     }
/* 2092 */     String getSortKey() { return this.sortKey; }
/* 2093 */     void setSortKey(String param1String) { this.sortKey = param1String; } void setAlternateSortKey(String param1String) {
/* 2094 */       this.sortKey2 = param1String;
/*      */     }
/*      */     void swapSortKey() {
/* 2097 */       String str = this.sortKey;
/* 2098 */       this.sortKey = this.sortKey2;
/* 2099 */       this.sortKey2 = str;
/*      */     }
/*      */     
/*      */     String getKeys() {
/* 2103 */       StringBuffer stringBuffer = new StringBuffer(this.eType + ":" + this.eId);
/* 2104 */       for (byte b = 0; b < this.children.size(); b++) {
/*      */         
/* 2106 */         if (b == 0)
/* 2107 */           stringBuffer.append(" from ["); 
/* 2108 */         stringBuffer.append(" " + ((SortedCG)this.children.elementAt(b)).getKeys());
/*      */       } 
/* 2110 */       if (this.children.size() > 0) {
/* 2111 */         stringBuffer.append("] ");
/*      */       }
/* 2113 */       return stringBuffer.toString();
/*      */     }
/* 2115 */     int getEntityId() { return this.eId; } void addChild(SortedCG param1SortedCG) {
/* 2116 */       this.children.addElement(param1SortedCG);
/*      */     }
/*      */     String getStructure(int param1Int) {
/* 2119 */       MessageFormat messageFormat = new MessageFormat(this.html);
/* 2120 */       StringBuffer stringBuffer = null;
/* 2121 */       Object[] arrayOfObject = new Object[2];
/* 2122 */       arrayOfObject[0] = CSSLVL[param1Int];
/* 2123 */       arrayOfObject[1] = " title=\"" + getKeys() + "\"";
/*      */       
/* 2125 */       stringBuffer = new StringBuffer(messageFormat.format(arrayOfObject));
/* 2126 */       for (byte b = 0; b < this.children.size(); b++)
/*      */       {
/* 2128 */         stringBuffer.append(((SortedCG)this.children.elementAt(b)).getStructure(param1Int + 1));
/*      */       }
/*      */       
/* 2131 */       if (param1Int == 0) {
/* 2132 */         CHGGRPRptGen.restoreBraces(stringBuffer);
/*      */       }
/* 2134 */       return stringBuffer.toString();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2138 */       StringBuffer stringBuffer = new StringBuffer(this.eType + ":" + this.eId + " [" + this.sortKey + "]");
/* 2139 */       if (!this.sortKey2.equals(this.sortKey))
/* 2140 */         stringBuffer.append("[" + this.sortKey2 + "]"); 
/* 2141 */       if (this.children.size() > 0)
/* 2142 */         stringBuffer.append(" ("); 
/* 2143 */       for (byte b = 0; b < this.children.size(); b++)
/*      */       {
/* 2145 */         stringBuffer.append(((SortedCG)this.children.elementAt(b)).toString());
/*      */       }
/* 2147 */       if (this.children.size() > 0)
/* 2148 */         stringBuffer.append(") "); 
/* 2149 */       return stringBuffer.toString();
/*      */     }
/*      */ 
/*      */     
/*      */     void dereference() {
/* 2154 */       if (this.children == null)
/*      */         return; 
/* 2156 */       for (byte b = 0; b < this.children.size(); b++) {
/*      */         
/* 2158 */         SortedCG sortedCG = this.children.elementAt(b);
/* 2159 */         sortedCG.dereference();
/*      */       } 
/*      */       
/* 2162 */       this.children.clear();
/* 2163 */       this.children = null;
/* 2164 */       this.html = null;
/* 2165 */       this.sortKey = null;
/* 2166 */       this.sortKey2 = null;
/* 2167 */       this.eType = null;
/*      */     }
/*      */     
/*      */     public int compareTo(Object param1Object) {
/* 2171 */       SortedCG sortedCG = (SortedCG)param1Object;
/*      */       
/* 2173 */       return this.sortKey.compareTo(sortedCG.sortKey);
/*      */     }
/*      */     
/*      */     boolean matches(EntityItem param1EntityItem) {
/* 2177 */       return (this.eType.equals(param1EntityItem.getEntityType()) && this.eId == param1EntityItem.getEntityID());
/*      */     }
/*      */     
/*      */     void sortIt() {
/* 2181 */       for (byte b = 0; b < this.children.size(); b++)
/*      */       {
/* 2183 */         ((SortedCG)this.children.elementAt(b)).sortIt();
/*      */       }
/* 2185 */       Collections.sort(this.children);
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2189 */       SortedCG sortedCG = (SortedCG)param1Object;
/* 2190 */       return (this.eType.equals(sortedCG.eType) && this.eId == sortedCG.eId);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 2200 */       return this.eType.hashCode() + this.eId;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CHGGRPRptGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */