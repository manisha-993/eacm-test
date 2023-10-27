/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.DeleteActionItem;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.LinkActionItem;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.eannounce.objects.WorkflowException;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BOXERLSEOBDLALWRABR01
/*      */   extends PokBaseABR
/*      */ {
/*   44 */   private StringBuffer rptSb = new StringBuffer();
/*   45 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   46 */   static final String NEWLINE = new String(FOOL_JTEST);
/*   47 */   private Object[] args = (Object[])new String[10];
/*      */   
/*   49 */   private ResourceBundle rsBundle = null;
/*   50 */   private Hashtable metaTbl = new Hashtable<>();
/*   51 */   private String navName = "";
/*   52 */   private LinkActionItem lai = null;
/*   53 */   private DeleteActionItem dai = null;
/*   54 */   private Vector createdLseoBdlVct = new Vector(1);
/*   55 */   private Vector updatedLseoBdlVct = new Vector(1);
/*      */   private String bdlseoid;
/*   57 */   private Vector lseoQtyVct = new Vector();
/*   58 */   private Hashtable lseoTbl = new Hashtable<>();
/*      */   
/*      */   private static final String LSEOBDL_SRCHACTION_NAME = "SRDLSEOBDL4";
/*      */   
/*      */   private static final String LSEOBDL_CREATEACTION_NAME = "CRLSEOBUNDLE";
/*      */   private static final String LSEOBDLLSEO_LINKACTION_NAME = "LINKLSEOLSEOBUNDLE";
/*      */   private static final String LSEO_SRCHACTION_NAME = "SRDLSEO3";
/*      */   private static final String LSEOBDLLSEO_DELETEACTION_NAME = "DELLSEOBUNDLELSEO";
/*      */   private static final String STATUS_FINAL = "0020";
/*   67 */   private static final String[] LSEOLIST_ATTR = new String[] { "HIPOLSEOLIST", "HWLSEOLIST", "SERVLSEOLIST", "SWLSEOLIST" };
/*      */   
/*   69 */   private static final String[] REQ_ALWR_ATTR = new String[] { "COUNTRYLIST", "CD", "XXPARTNO" };
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
/*      */   public void execute_run() {
/*   91 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*   93 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
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
/*  104 */     String str3 = "";
/*  105 */     String str4 = "";
/*      */     
/*  107 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  110 */       start_ABRBuild();
/*      */       
/*  112 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  114 */       EntityItem entityItem1 = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  116 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem1.getKey() + " extract: " + this.m_abri
/*  117 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  120 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  125 */       this.navName = getNavigationName(entityItem1);
/*      */ 
/*      */       
/*  128 */       str3 = PokUtils.getAttributeDescription(this.m_elist.getParentEntityGroup(), this.m_abri.getABRCode(), this.m_abri.getABRCode()) + " on " + this.m_elist.getParentEntityGroup().getLongDescription() + " &quot;" + this.navName + "&quot; ";
/*      */       
/*  130 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEOBUNDLE");
/*  131 */       EntityItem entityItem2 = null;
/*  132 */       if (entityGroup.getEntityItemCount() == 0) {
/*      */         
/*  134 */         this.args[0] = entityGroup.getLongDescription();
/*  135 */         addError("NOT_FOUND_ERR", this.args);
/*      */       } else {
/*  137 */         entityItem2 = entityGroup.getEntityItem(0);
/*  138 */         str3 = str3 + "<br />for " + entityGroup.getLongDescription() + " &quot;" + getNavigationName(entityItem2) + "&quot;";
/*      */       } 
/*  140 */       if (getReturnCode() == 0) {
/*      */         
/*  142 */         EntityItem entityItem = checkAlwr(entityItem1, entityItem2);
/*  143 */         if (getReturnCode() == 0) {
/*  144 */           if (entityItem == null) {
/*  145 */             createLSEOBundle(entityItem1, entityItem2);
/*      */           }
/*      */           else {
/*      */             
/*  149 */             String str = updateLseoRefs(entityItem);
/*  150 */             if (str.length() > 0) {
/*  151 */               this.updatedLseoBdlVct.addElement(getNavigationName(entityItem) + str);
/*      */             }
/*      */           } 
/*      */           
/*  155 */           if (this.createdLseoBdlVct.size() > 0) {
/*      */             
/*  157 */             this.args[0] = "";
/*  158 */             for (byte b = 0; b < this.createdLseoBdlVct.size(); b++) {
/*  159 */               this.args[0] = this.args[0] + this.createdLseoBdlVct.elementAt(b).toString();
/*      */             }
/*  161 */             MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("CREATED_MSG"));
/*  162 */             addOutput(messageFormat1.format(this.args));
/*      */           } 
/*  164 */           if (this.updatedLseoBdlVct.size() > 0) {
/*      */             
/*  166 */             this.args[0] = "";
/*  167 */             for (byte b = 0; b < this.updatedLseoBdlVct.size(); b++) {
/*  168 */               this.args[0] = this.args[0] + this.updatedLseoBdlVct.elementAt(b).toString();
/*      */             }
/*  170 */             MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("UPDATED_MSG"));
/*  171 */             addOutput(messageFormat1.format(this.args));
/*      */           } 
/*  173 */           if (this.createdLseoBdlVct.size() == 0 && this.updatedLseoBdlVct.size() == 0)
/*      */           {
/*  175 */             addOutput(this.rsBundle.getString("NO_CHGS"));
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/*  180 */     } catch (Throwable throwable) {
/*  181 */       StringWriter stringWriter = new StringWriter();
/*  182 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  183 */       String str7 = "<pre>{0}</pre>";
/*  184 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  185 */       setReturnCode(-3);
/*  186 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  188 */       this.args[0] = throwable.getMessage();
/*  189 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  190 */       messageFormat1 = new MessageFormat(str7);
/*  191 */       this.args[0] = stringWriter.getBuffer().toString();
/*  192 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  193 */       logError("Exception: " + throwable.getMessage());
/*  194 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  198 */       setDGTitle(this.navName);
/*  199 */       setDGRptName(getShortClassName(getClass()));
/*  200 */       setDGRptClass(getABRCode());
/*      */       
/*  202 */       if (!isReadOnly())
/*      */       {
/*  204 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  210 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  211 */     this.args[0] = getDescription();
/*  212 */     this.args[1] = this.navName;
/*  213 */     String str5 = messageFormat.format(this.args);
/*  214 */     messageFormat = new MessageFormat(str2);
/*  215 */     this.args[0] = this.m_prof.getOPName();
/*  216 */     this.args[1] = this.m_prof.getRoleDescription();
/*  217 */     this.args[2] = this.m_prof.getWGName();
/*  218 */     this.args[3] = getNow();
/*  219 */     this.args[4] = str3;
/*  220 */     this.args[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  221 */     this.args[6] = str4 + " " + getABRVersion();
/*      */     
/*  223 */     this.rptSb.insert(0, str5 + messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  225 */     println(this.rptSb.toString());
/*  226 */     printDGSubmitString();
/*  227 */     println(EACustom.getTOUDiv());
/*  228 */     buildReportFooter();
/*      */     
/*  230 */     this.metaTbl.clear();
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
/*      */   private EntityItem checkAlwr(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  300 */     EntityItem entityItem = null;
/*      */ 
/*      */ 
/*      */     
/*  304 */     if (verifyEntity(paramEntityItem1, REQ_ALWR_ATTR)) {
/*      */       
/*  306 */       String str = PokUtils.getAttributeValue(paramEntityItem1, "CD", "", "", false);
/*  307 */       addDebug("Checking " + paramEntityItem1.getKey() + " CD: " + str);
/*  308 */       int i = str.length();
/*  309 */       if (i != 2) {
/*      */         
/*  311 */         this.args[0] = paramEntityItem1.getEntityGroup().getLongDescription();
/*  312 */         this.args[1] = getNavigationName(paramEntityItem1);
/*  313 */         this.args[2] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "CD", "CD");
/*  314 */         this.args[3] = str;
/*  315 */         addError("INVALID_CD_ERR", this.args);
/*  316 */         return entityItem;
/*      */       } 
/*      */ 
/*      */       
/*  320 */       this.bdlseoid = PokUtils.getAttributeValue(paramEntityItem1, "XXPARTNO", "", "", false);
/*  321 */       if (this.bdlseoid.length() > 5) {
/*  322 */         this.bdlseoid = this.bdlseoid.substring(0, 5);
/*      */       }
/*  324 */       this.bdlseoid += str;
/*  325 */       addDebug("derived bdlseoid " + this.bdlseoid);
/*      */       
/*  327 */       Vector<String> vector = new Vector(1);
/*  328 */       boolean bool = false;
/*      */       
/*  330 */       for (byte b = 0; b < LSEOLIST_ATTR.length; b++) {
/*  331 */         String str1 = PokUtils.getAttributeValue(paramEntityItem1, LSEOLIST_ATTR[b], "", "", false);
/*  332 */         addDebug(LSEOLIST_ATTR[b] + " " + str1);
/*  333 */         if (str1.length() > 0) {
/*  334 */           StringTokenizer stringTokenizer = new StringTokenizer(str1, ",");
/*  335 */           while (stringTokenizer.hasMoreTokens()) {
/*  336 */             String str2 = "1";
/*  337 */             String str3 = stringTokenizer.nextToken().trim();
/*      */             
/*  339 */             int j = str3.indexOf(":");
/*  340 */             if (j != -1) {
/*  341 */               str2 = str3.substring(j + 1).trim();
/*  342 */               str3 = str3.substring(0, j).trim();
/*      */             } 
/*      */ 
/*      */             
/*  346 */             EntityItem entityItem1 = searchForLSEO(str3);
/*  347 */             if (entityItem1 == null) {
/*  348 */               if (!vector.contains(str3)) {
/*      */                 
/*  350 */                 this.args[0] = str3;
/*  351 */                 this.args[1] = PokUtils.getAttributeValue(paramEntityItem2, "PDHDOMAIN", ", ", "", false);
/*  352 */                 addError("LSEO_NOTFOUND_ERR", this.args);
/*  353 */                 vector.addElement(str3);
/*      */               } 
/*  355 */               bool = true; continue;
/*      */             } 
/*  357 */             LseoQty lseoQty = new LseoQty(str3, str2, entityItem1);
/*  358 */             int k = this.lseoQtyVct.indexOf(lseoQty);
/*  359 */             if (k != -1) {
/*  360 */               LseoQty lseoQty1 = this.lseoQtyVct.elementAt(k);
/*      */               
/*  362 */               if (!lseoQty1.qty.equals(lseoQty.qty)) {
/*      */                 
/*  364 */                 this.args[0] = paramEntityItem1.getEntityGroup().getLongDescription();
/*  365 */                 this.args[1] = getNavigationName(paramEntityItem1);
/*  366 */                 this.args[2] = lseoQty1 + " " + lseoQty;
/*  367 */                 addError("DUPLICATE_LSEOQTY_ERR", this.args);
/*  368 */                 bool = true;
/*      */               
/*      */               }
/*      */               else {
/*      */                 
/*  373 */                 this.args[0] = str3;
/*  374 */                 this.args[1] = paramEntityItem1.getEntityGroup().getLongDescription();
/*  375 */                 this.args[2] = getNavigationName(paramEntityItem1);
/*  376 */                 addOutput(getResourceMsg("DUPLICATE_LSEO_MSG", this.args));
/*      */               } 
/*  378 */               lseoQty.dereference(); continue;
/*      */             } 
/*  380 */             this.lseoQtyVct.addElement(lseoQty);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  387 */       if (!bool)
/*      */       {
/*      */         
/*  390 */         if (this.lseoQtyVct.size() > 0) {
/*      */           
/*  392 */           entityItem = searchForLseoBundle();
/*  393 */           if (entityItem != null) {
/*  394 */             String str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  395 */             addDebug(entityItem.getKey() + " STATUS: " + PokUtils.getAttributeValue(entityItem, "STATUS", ", ", "", false) + " [" + str1 + "] ");
/*      */             
/*  397 */             if (null == str1 || str1.length() == 0) {
/*  398 */               str1 = "0020";
/*      */             }
/*  400 */             if (str1.equals("0020")) {
/*      */               
/*  402 */               this.args[0] = this.bdlseoid;
/*  403 */               addError("LSEOBDL_FINAL_ERR", this.args);
/*      */             } else {
/*      */               
/*  406 */               String[] arrayOfString1 = PokUtils.convertToArray(PokUtils.getAttributeFlagValue(paramEntityItem2, "PDHDOMAIN"));
/*  407 */               String[] arrayOfString2 = PokUtils.convertToArray(PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN"));
/*  408 */               Vector<String> vector1 = new Vector();
/*  409 */               Vector<String> vector2 = new Vector(); byte b1;
/*  410 */               for (b1 = 0; b1 < arrayOfString2.length; b1++) {
/*  411 */                 vector2.addElement(arrayOfString2[b1]);
/*      */               }
/*  413 */               for (b1 = 0; b1 < arrayOfString1.length; b1++) {
/*  414 */                 vector1.addElement(arrayOfString1[b1]);
/*      */               }
/*  416 */               if (!vector1.containsAll(vector2) || !vector2.containsAll(vector1)) {
/*      */ 
/*      */ 
/*      */                 
/*  420 */                 this.args[0] = this.bdlseoid;
/*  421 */                 addError("LSEOBDL_EXISTS_ERR", this.args);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/*  427 */           this.args[0] = paramEntityItem1.getEntityGroup().getLongDescription();
/*  428 */           this.args[1] = getNavigationName(paramEntityItem1);
/*  429 */           addError("NO_ALWRLSEO_ERR", this.args);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  434 */     return entityItem;
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
/*      */   private EntityItem searchForLseoBundle() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  449 */     EntityItem entityItem = null;
/*      */ 
/*      */     
/*  452 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*  453 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  454 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*  455 */       String str = PokUtils.getAttributeValue(entityItem1, "SEOID", "", "", false);
/*  456 */       if (str.equals(this.bdlseoid)) {
/*  457 */         entityItem = entityItem1;
/*  458 */         addDebug("searchForLseoBundle using " + entityItem.getKey());
/*      */         break;
/*      */       } 
/*      */     } 
/*  462 */     if (entityItem == null) {
/*  463 */       Vector<String> vector1 = new Vector(1);
/*  464 */       vector1.addElement("SEOID");
/*  465 */       Vector<String> vector2 = new Vector(1);
/*  466 */       vector2.addElement(this.bdlseoid);
/*      */       
/*  468 */       EntityItem[] arrayOfEntityItem = null;
/*      */       try {
/*  470 */         StringBuffer stringBuffer = new StringBuffer();
/*  471 */         arrayOfEntityItem = ABRUtil.doSearch(this.m_db, this.m_prof, "SRDLSEOBDL4", "LSEOBUNDLE", false, vector1, vector2, stringBuffer);
/*      */         
/*  473 */         if (stringBuffer.length() > 0) {
/*  474 */           addDebug(stringBuffer.toString());
/*      */         }
/*  476 */       } catch (SBRException sBRException) {
/*      */         
/*  478 */         StringWriter stringWriter = new StringWriter();
/*  479 */         sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  480 */         addDebug("searchForLseoBundle SBRException: " + stringWriter.getBuffer().toString());
/*      */       } 
/*  482 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  483 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  484 */           addDebug("searchForLseoBundle found " + arrayOfEntityItem[b1].getKey());
/*      */         }
/*      */         
/*  487 */         entityItem = arrayOfEntityItem[0];
/*      */       } 
/*  489 */       vector1.clear();
/*  490 */       vector2.clear();
/*      */     } 
/*      */     
/*  493 */     return entityItem;
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
/*      */   private EntityItem searchForLSEO(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  508 */     EntityItem entityItem = (EntityItem)this.lseoTbl.get(paramString);
/*  509 */     if (entityItem == null) {
/*  510 */       Vector<String> vector1 = new Vector(1);
/*  511 */       vector1.addElement("SEOID");
/*  512 */       Vector<String> vector2 = new Vector(1);
/*  513 */       vector2.addElement(paramString);
/*      */       
/*  515 */       EntityItem[] arrayOfEntityItem = null;
/*      */       try {
/*  517 */         StringBuffer stringBuffer = new StringBuffer();
/*  518 */         arrayOfEntityItem = ABRUtil.doSearch(this.m_db, this.m_prof, "SRDLSEO3", "LSEO", false, vector1, vector2, stringBuffer);
/*      */         
/*  520 */         if (stringBuffer.length() > 0) {
/*  521 */           addDebug(stringBuffer.toString());
/*      */         }
/*  523 */       } catch (SBRException sBRException) {
/*      */         
/*  525 */         StringWriter stringWriter = new StringWriter();
/*  526 */         sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  527 */         addDebug("searchForLSEO SBRException: " + stringWriter.getBuffer().toString());
/*      */       } 
/*  529 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  530 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  531 */           addDebug("searchForLSEO found " + arrayOfEntityItem[b].getKey());
/*      */         }
/*      */         
/*  534 */         entityItem = arrayOfEntityItem[0];
/*  535 */         this.lseoTbl.put(paramString, entityItem);
/*      */       } 
/*  537 */       vector1.clear();
/*  538 */       vector2.clear();
/*      */     } 
/*      */     
/*  541 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean verifyEntity(EntityItem paramEntityItem, String[] paramArrayOfString) throws SQLException, MiddlewareException {
/*  552 */     boolean bool = true;
/*  553 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  554 */       String str = PokUtils.getAttributeValue(paramEntityItem, paramArrayOfString[b], "", null, false);
/*  555 */       if (str == null) {
/*      */         
/*  557 */         this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/*  558 */         this.args[1] = getNavigationName(paramEntityItem);
/*  559 */         this.args[2] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramArrayOfString[b], paramArrayOfString[b]);
/*  560 */         addError("MISSING_ATTR_ERR", this.args);
/*  561 */         bool = false;
/*      */       } 
/*      */     } 
/*      */     
/*  565 */     return bool;
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
/*      */   private void createLSEOBundle(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws MiddlewareRequestException, RemoteException, SQLException, MiddlewareException, EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*  585 */     addDebug("createLSEOBundle entered for SEOID " + this.bdlseoid + " curlseoBundleItem " + paramEntityItem2.getKey());
/*      */     
/*  587 */     EntityItem entityItem1 = null;
/*  588 */     AttrSet attrSet = new AttrSet(paramEntityItem1, paramEntityItem2);
/*      */     
/*  590 */     StringBuffer stringBuffer = new StringBuffer();
/*  591 */     EntityItem entityItem2 = new EntityItem(null, this.m_prof, "WG", this.m_prof.getWGID());
/*  592 */     entityItem1 = ABRUtil.createEntity(this.m_db, this.m_prof, "CRLSEOBUNDLE", entityItem2, "LSEOBUNDLE", attrSet
/*  593 */         .getAttrCodes(), attrSet.getAttrValues(), stringBuffer);
/*  594 */     if (stringBuffer.length() > 0) {
/*  595 */       addDebug(stringBuffer.toString());
/*      */     }
/*      */ 
/*      */     
/*  599 */     attrSet.dereference();
/*      */     
/*  601 */     if (entityItem1 == null) {
/*      */       
/*  603 */       this.args[0] = this.bdlseoid;
/*  604 */       addError("LSEOBDL_CREATE_ERR", this.args);
/*      */     } else {
/*  606 */       this.createdLseoBdlVct.addElement(new StringBuffer(getNavigationName(entityItem1)));
/*      */       
/*  608 */       createLseoRefs(entityItem1);
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
/*      */   private void createLseoRefs(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/*  628 */     String str1 = "LINKLSEOLSEOBUNDLE";
/*  629 */     if (this.lai == null) {
/*  630 */       this.lai = new LinkActionItem(null, this.m_db, this.m_prof, str1);
/*      */     }
/*  632 */     EntityItem[] arrayOfEntityItem1 = { paramEntityItem };
/*  633 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[this.lseoQtyVct.size()];
/*      */     
/*  635 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/*  637 */     for (byte b1 = 0; b1 < this.lseoQtyVct.size(); b1++) {
/*  638 */       LseoQty lseoQty = this.lseoQtyVct.elementAt(b1);
/*  639 */       arrayOfEntityItem2[b1] = lseoQty.lseoItem;
/*  640 */       hashtable.put(arrayOfEntityItem2[b1].getKey(), lseoQty);
/*      */     } 
/*      */ 
/*      */     
/*  644 */     this.lai.setParentEntityItems(arrayOfEntityItem1);
/*  645 */     this.lai.setChildEntityItems(arrayOfEntityItem2);
/*  646 */     this.m_db.executeAction(this.m_prof, this.lai);
/*      */ 
/*      */ 
/*      */     
/*  650 */     Profile profile = this.m_prof.getNewInstance(this.m_db);
/*  651 */     String str2 = this.m_db.getDates().getNow();
/*  652 */     profile.setValOnEffOn(str2, str2);
/*      */     
/*  654 */     EntityList entityList = this.m_db.getEntityList(profile, new ExtractActionItem(null, this.m_db, profile, "BOXERLSEOBDL2"), arrayOfEntityItem1);
/*      */ 
/*      */ 
/*      */     
/*  658 */     addDebug("createLseoRefs list using VE BOXERLSEOBDL2 after linkaction: " + str1 + "\n" + 
/*  659 */         PokUtils.outputList(entityList));
/*  660 */     EntityGroup entityGroup = entityList.getEntityGroup("LSEO");
/*  661 */     StringBuffer stringBuffer1 = new StringBuffer();
/*  662 */     for (byte b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*  663 */       EntityItem entityItem1 = entityGroup.getEntityItem(b2);
/*  664 */       LseoQty lseoQty = (LseoQty)hashtable.get(entityItem1.getKey());
/*  665 */       String str = lseoQty.qty;
/*      */       
/*  667 */       stringBuffer1.append(getResourceMsg("ADDED_REF_MSG", new Object[] { LseoQty.access$200(lseoQty), LseoQty.access$000(lseoQty) }));
/*  668 */       EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/*  669 */       addDebug(entityItem1.getKey() + " use qty: " + str + " on " + entityItem2.getKey());
/*  670 */       if (str != null && !str.equals("1")) {
/*  671 */         StringBuffer stringBuffer = new StringBuffer();
/*      */         
/*  673 */         ABRUtil.setText(entityItem2, "LSEOQTY", str, stringBuffer);
/*  674 */         if (stringBuffer.length() > 0) {
/*  675 */           addDebug(stringBuffer.toString());
/*      */         }
/*      */         
/*  678 */         entityItem2.commit(this.m_db, null);
/*      */       } 
/*      */     } 
/*  681 */     StringBuffer stringBuffer2 = this.createdLseoBdlVct.lastElement();
/*  682 */     stringBuffer2.append(stringBuffer1.toString());
/*      */     
/*  684 */     hashtable.clear();
/*  685 */     entityList.dereference();
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
/*      */   private String updateLseoRefs(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/*  706 */     StringBuffer stringBuffer = new StringBuffer();
/*  707 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/*  709 */     for (byte b1 = 0; b1 < this.lseoQtyVct.size(); b1++) {
/*  710 */       LseoQty lseoQty = this.lseoQtyVct.elementAt(b1);
/*  711 */       hashtable.put(lseoQty.lseoItem.getKey(), lseoQty);
/*      */     } 
/*      */     
/*  714 */     EntityItem[] arrayOfEntityItem = { paramEntityItem };
/*      */ 
/*      */     
/*  717 */     EntityList entityList1 = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "BOXERLSEOBDL2"), arrayOfEntityItem);
/*      */ 
/*      */     
/*  720 */     addDebug("updateLseoRefs list using VE BOXERLSEOBDL2 current list: \n" + 
/*  721 */         PokUtils.outputList(entityList1));
/*  722 */     paramEntityItem = entityList1.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  724 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOBUNDLELSEO", "LSEO");
/*  725 */     addDebug("updateLseoRefs origLsVct " + vector1.size());
/*  726 */     for (byte b2 = 0; b2 < vector1.size(); b2++) {
/*  727 */       addDebug("updateLseoRefs origLsVct[" + b2 + "] " + ((EntityItem)vector1.elementAt(b2)).getKey());
/*      */     }
/*      */ 
/*      */     
/*  731 */     Vector<EntityItem> vector2 = new Vector();
/*  732 */     for (byte b3 = 0; b3 < this.lseoQtyVct.size(); b3++) {
/*  733 */       LseoQty lseoQty = this.lseoQtyVct.elementAt(b3);
/*  734 */       boolean bool = false;
/*  735 */       Iterator<EntityItem> iterator = vector1.iterator();
/*  736 */       while (iterator.hasNext()) {
/*  737 */         EntityItem entityItem = iterator.next();
/*  738 */         if (entityItem.getKey().equals(lseoQty.lseoItem.getKey())) {
/*  739 */           addDebug("updateLseoRefs already exists " + lseoQty.lseoItem.getKey());
/*  740 */           bool = true;
/*  741 */           iterator.remove();
/*      */           break;
/*      */         } 
/*      */       } 
/*  745 */       if (!bool) {
/*  746 */         addDebug("updateLseoRefs missing " + lseoQty.lseoItem.getKey());
/*  747 */         vector2.add(lseoQty.lseoItem);
/*      */         
/*  749 */         stringBuffer.append(getResourceMsg("ADDED_REF_MSG", new Object[] { LseoQty.access$200(lseoQty), LseoQty.access$000(lseoQty) }));
/*      */       } 
/*      */     } 
/*      */     
/*  753 */     String str1 = "LINKLSEOLSEOBUNDLE";
/*      */     
/*  755 */     if (vector2.size() > 0) {
/*  756 */       addDebug("updateLseoRefs  missingLsVct " + vector2.size());
/*  757 */       for (byte b = 0; b < vector2.size(); b++) {
/*  758 */         addDebug("updateLseoRefs missingLsVct[" + b + "] " + ((EntityItem)vector2.elementAt(b)).getKey());
/*      */       }
/*  760 */       if (this.lai == null) {
/*  761 */         this.lai = new LinkActionItem(null, this.m_db, this.m_prof, str1);
/*      */       }
/*      */       
/*  764 */       EntityItem[] arrayOfEntityItem1 = new EntityItem[vector2.size()];
/*  765 */       vector2.copyInto((Object[])arrayOfEntityItem1);
/*      */ 
/*      */       
/*  768 */       this.lai.setParentEntityItems(arrayOfEntityItem);
/*  769 */       this.lai.setChildEntityItems(arrayOfEntityItem1);
/*  770 */       this.m_db.executeAction(this.m_prof, this.lai);
/*      */     } 
/*      */     
/*  773 */     String str2 = "DELLSEOBUNDLELSEO";
/*  774 */     if (vector1.size() > 0) {
/*  775 */       addDebug("updateLseoRefs unneeded cnt " + vector1.size());
/*  776 */       if (this.dai == null) {
/*  777 */         this.dai = new DeleteActionItem(null, this.m_db, this.m_prof, str2);
/*      */       }
/*  779 */       EntityItem[] arrayOfEntityItem1 = new EntityItem[vector1.size()];
/*  780 */       for (byte b = 0; b < vector1.size(); b++) {
/*  781 */         EntityItem entityItem = vector1.elementAt(b);
/*  782 */         addDebug("updateLseoRefs unneeded [" + b + "] " + entityItem.getKey());
/*      */         
/*  784 */         stringBuffer.append(getResourceMsg("DELETED_REF_MSG", new Object[] {
/*  785 */                 PokUtils.getAttributeValue(entityItem, "SEOID", "", "", false) }));
/*  786 */         arrayOfEntityItem1[b] = (EntityItem)entityItem.getUpLink(0);
/*      */       } 
/*      */ 
/*      */       
/*  790 */       this.dai.setEntityItems(arrayOfEntityItem1);
/*  791 */       this.m_db.executeAction(this.m_prof, this.dai);
/*  792 */       vector1.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  797 */     Profile profile = this.m_prof.getNewInstance(this.m_db);
/*  798 */     String str3 = this.m_db.getDates().getNow();
/*  799 */     profile.setValOnEffOn(str3, str3);
/*      */     
/*  801 */     EntityList entityList2 = this.m_db.getEntityList(profile, new ExtractActionItem(null, this.m_db, profile, "BOXERLSEOBDL2"), arrayOfEntityItem);
/*      */ 
/*      */ 
/*      */     
/*  805 */     addDebug("updateLseoRefs list using VE BOXERLSEOBDL2 after linkaction: " + str1 + " and deleteaction: " + str2 + "\n" + 
/*  806 */         PokUtils.outputList(entityList2));
/*  807 */     EntityGroup entityGroup = entityList2.getEntityGroup("LSEO");
/*      */     
/*  809 */     for (byte b4 = 0; b4 < entityGroup.getEntityItemCount(); b4++) {
/*  810 */       EntityItem entityItem1 = entityGroup.getEntityItem(b4);
/*  811 */       LseoQty lseoQty = (LseoQty)hashtable.get(entityItem1.getKey());
/*  812 */       String str4 = lseoQty.qty;
/*  813 */       EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/*  814 */       String str5 = PokUtils.getAttributeValue(entityItem2, "LSEOQTY", "", "", false);
/*  815 */       addDebug(entityItem1.getKey() + " needs qty: " + str4 + " on " + entityItem2.getKey() + " has qty: " + str5);
/*  816 */       if (str4 != null && !str4.equals(str5)) {
/*  817 */         StringBuffer stringBuffer1 = new StringBuffer();
/*      */         
/*  819 */         ABRUtil.setText(entityItem2, "LSEOQTY", str4, stringBuffer1);
/*  820 */         if (stringBuffer1.length() > 0) {
/*  821 */           addDebug(stringBuffer1.toString());
/*      */         }
/*  823 */         if (!vector2.contains(entityItem1))
/*      */         {
/*  825 */           stringBuffer.append(getResourceMsg("UPDATED_REF_MSG", new Object[] { LseoQty.access$200(lseoQty), LseoQty.access$000(lseoQty) }));
/*      */         }
/*      */         
/*  828 */         entityItem2.commit(this.m_db, null);
/*      */       } 
/*      */     } 
/*      */     
/*  832 */     hashtable.clear();
/*  833 */     vector2.clear();
/*  834 */     entityList2.dereference();
/*  835 */     entityList1.dereference();
/*  836 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/*  842 */     super.dereference();
/*      */     
/*  844 */     for (byte b = 0; b < this.lseoQtyVct.size(); b++) {
/*  845 */       LseoQty lseoQty = this.lseoQtyVct.elementAt(b);
/*  846 */       lseoQty.dereference();
/*      */     } 
/*  848 */     this.lseoQtyVct.clear();
/*  849 */     this.lseoQtyVct = null;
/*      */     
/*  851 */     this.createdLseoBdlVct.clear();
/*  852 */     this.createdLseoBdlVct = null;
/*  853 */     this.updatedLseoBdlVct.clear();
/*  854 */     this.updatedLseoBdlVct = null;
/*      */     
/*  856 */     this.rsBundle = null;
/*  857 */     this.lai = null;
/*  858 */     this.dai = null;
/*      */     
/*  860 */     this.rptSb = null;
/*  861 */     this.args = null;
/*  862 */     this.bdlseoid = null;
/*      */     
/*  864 */     this.metaTbl = null;
/*  865 */     this.navName = null;
/*  866 */     this.lseoTbl.clear();
/*  867 */     this.lseoTbl = null;
/*      */   }
/*      */   public String getABRVersion() {
/*  870 */     return "1.3";
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*  874 */     return "LSEOBDLALWRABR01";
/*      */   }
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/*  879 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */   
/*      */   private void addDebug(String paramString) {
/*  884 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getResourceMsg(String paramString, Object[] paramArrayOfObject) {
/*  895 */     String str = this.rsBundle.getString(paramString);
/*  896 */     if (paramArrayOfObject != null) {
/*  897 */       MessageFormat messageFormat = new MessageFormat(str);
/*  898 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/*  901 */     return str;
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
/*      */   private void addError(String paramString, Object[] paramArrayOfObject) {
/*  913 */     setReturnCode(-1);
/*      */     
/*  915 */     String str = this.rsBundle.getString(paramString);
/*      */     
/*  917 */     if (paramArrayOfObject != null) {
/*  918 */       MessageFormat messageFormat = new MessageFormat(str);
/*  919 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/*  922 */     addOutput(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  932 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/*  935 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/*  936 */     if (eANList == null) {
/*      */       
/*  938 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/*  939 */       eANList = entityGroup.getMetaAttribute();
/*  940 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/*  942 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/*  944 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/*  945 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/*  946 */       if (b + 1 < eANList.size()) {
/*  947 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/*  951 */     return stringBuffer.toString();
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
/*      */   private class AttrSet
/*      */   {
/*  997 */     private Vector attrCodeVct = new Vector();
/*  998 */     private Hashtable attrValTbl = new Hashtable<>();
/*      */     protected void addSingle(EntityItem param1EntityItem, String param1String) {
/* 1000 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String);
/* 1001 */       if (str == null) {
/* 1002 */         str = "";
/*      */       }
/* 1004 */       this.attrCodeVct.addElement(param1String);
/* 1005 */       this.attrValTbl.put(param1String, str);
/*      */     }
/*      */     protected void addText(EntityItem param1EntityItem, String param1String) {
/* 1008 */       String str = PokUtils.getAttributeValue(param1EntityItem, param1String, "", "", false);
/* 1009 */       this.attrCodeVct.addElement(param1String);
/* 1010 */       this.attrValTbl.put(param1String, str);
/*      */     }
/*      */     protected void addMult(EntityItem param1EntityItem, String param1String) {
/* 1013 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String);
/* 1014 */       if (str == null) {
/* 1015 */         str = "";
/*      */       }
/* 1017 */       String[] arrayOfString = PokUtils.convertToArray(str);
/* 1018 */       Vector<String> vector = new Vector(arrayOfString.length);
/* 1019 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1020 */         vector.addElement(arrayOfString[b]);
/*      */       }
/* 1022 */       this.attrCodeVct.addElement(param1String);
/* 1023 */       this.attrValTbl.put(param1String, vector);
/*      */     }
/*      */     
/*      */     AttrSet(EntityItem param1EntityItem1, EntityItem param1EntityItem2) {
/* 1027 */       addSingle(param1EntityItem2, "ACCTASGNGRP");
/*      */       
/* 1029 */       addMult(param1EntityItem2, "AUDIEN");
/*      */       
/* 1031 */       addMult(param1EntityItem2, "BUNDLETYPE");
/*      */       
/* 1033 */       addText(param1EntityItem2, "BUNDLMKTGDESC");
/*      */       
/* 1035 */       addText(param1EntityItem2, "BUNDLPUBDATEMTRGT");
/*      */       
/* 1037 */       addText(param1EntityItem2, "BUNDLUNPUBDATEMTRGT");
/*      */       
/* 1039 */       addMult(param1EntityItem2, "CNTRYOFMFR");
/*      */       
/* 1041 */       addText(param1EntityItem2, "COMNAME");
/*      */       
/* 1043 */       addMult(param1EntityItem2, "GENAREASELECTION");
/*      */       
/* 1045 */       addMult(param1EntityItem2, "COUNTRYLIST");
/*      */       
/* 1047 */       addText(param1EntityItem2, "EANCD");
/*      */       
/* 1049 */       addMult(param1EntityItem2, "FLFILSYSINDC");
/*      */       
/* 1051 */       addText(param1EntityItem2, "FUNCCLS");
/*      */       
/* 1053 */       addSingle(param1EntityItem2, "INDDEFNCATG");
/*      */       
/* 1055 */       addText(param1EntityItem2, "JANCD");
/*      */       
/* 1057 */       addSingle(param1EntityItem2, "KANGCD");
/*      */       
/* 1059 */       addMult(param1EntityItem2, "OSLEVEL");
/*      */ 
/*      */       
/* 1062 */       EANMetaAttribute eANMetaAttribute = param1EntityItem2.getEntityGroup().getMetaAttribute("PDHDOMAIN");
/* 1063 */       if (eANMetaAttribute.getAttributeType().equals("F")) {
/* 1064 */         addMult(param1EntityItem2, "PDHDOMAIN");
/*      */       } else {
/* 1066 */         addSingle(param1EntityItem2, "PDHDOMAIN");
/*      */       } 
/*      */       
/* 1069 */       addSingle(param1EntityItem2, "PLANRELEVANT");
/*      */       
/* 1071 */       addText(param1EntityItem2, "PRCFILENAM");
/*      */       
/* 1073 */       addText(param1EntityItem2, "PRODHIERCD");
/*      */       
/* 1075 */       addText(param1EntityItem2, "PRODID");
/*      */       
/* 1077 */       addSingle(param1EntityItem2, "PROJCDNAM");
/*      */       
/* 1079 */       addSingle(param1EntityItem2, "PROMO");
/*      */       
/* 1081 */       addSingle(param1EntityItem2, "RPTEXCELVE");
/*      */       
/* 1083 */       addText(param1EntityItem2, "SAPASSORTMODULE");
/*      */       
/* 1085 */       addText(param1EntityItem2, "SHIPGRPDESCLNGTXT");
/*      */       
/* 1087 */       addSingle(param1EntityItem2, "SPECBID");
/*      */       
/* 1089 */       addSingle(param1EntityItem2, "SPECMODDESGN");
/*      */       
/* 1091 */       addText(param1EntityItem2, "TAXCD");
/*      */       
/* 1093 */       addText(param1EntityItem2, "UPCCD");
/*      */       
/* 1095 */       addSingle(param1EntityItem1, "LANGUAGES");
/*      */       
/* 1097 */       addSingle(param1EntityItem1, "OFFCOUNTRY");
/*      */       
/* 1099 */       this.attrCodeVct.addElement("SEOID");
/* 1100 */       this.attrValTbl.put("SEOID", BOXERLSEOBDLALWRABR01.this.bdlseoid);
/*      */       
/* 1102 */       addText(param1EntityItem1, "XXPARTNO");
/*      */     }
/* 1104 */     Vector getAttrCodes() { return this.attrCodeVct; } Hashtable getAttrValues() {
/* 1105 */       return this.attrValTbl;
/*      */     }
/*      */     
/*      */     void dereference() {
/* 1109 */       this.attrCodeVct.clear();
/* 1110 */       this.attrValTbl.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   private class LseoQty {
/*      */     private String seoid;
/*      */     private String qty;
/*      */     private EntityItem lseoItem;
/*      */     
/*      */     LseoQty(String param1String1, String param1String2, EntityItem param1EntityItem) {
/* 1120 */       this.seoid = param1String1;
/* 1121 */       this.qty = param1String2;
/* 1122 */       this.lseoItem = param1EntityItem;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1127 */       LseoQty lseoQty = (LseoQty)param1Object;
/* 1128 */       return this.seoid.equals(lseoQty.seoid);
/*      */     }
/*      */     public String toString() {
/* 1131 */       if (this.qty.equals("1")) {
/* 1132 */         return this.seoid;
/*      */       }
/* 1134 */       return this.seoid + ":" + this.qty;
/*      */     }
/*      */     void dereference() {
/* 1137 */       this.seoid = null;
/* 1138 */       this.qty = null;
/* 1139 */       this.lseoItem = null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\BOXERLSEOBDLALWRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */