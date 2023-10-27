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
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
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
/*      */ 
/*      */ public class BOXERWWSEOALWRABR02
/*      */   extends PokBaseABR
/*      */ {
/*   46 */   private StringBuffer rptSb = new StringBuffer();
/*   47 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   48 */   static final String NEWLINE = new String(FOOL_JTEST);
/*   49 */   private Object[] args = (Object[])new String[10];
/*      */   
/*   51 */   private ResourceBundle rsBundle = null;
/*   52 */   private Hashtable metaTbl = new Hashtable<>();
/*   53 */   private String navName = ""; private EntityItem modelItem;
/*      */   private EntityItem wwseoItem;
/*   55 */   private Vector createdLseoVct = new Vector(1);
/*   56 */   private Vector updatedLseoVct = new Vector(1);
/*   57 */   private LinkActionItem lai = null;
/*   58 */   private DeleteActionItem dai = null;
/*   59 */   private Hashtable fcodePsTbl = new Hashtable<>();
/*      */   private String seoid;
/*   61 */   private Vector fcQtyVct = new Vector(1);
/*      */   
/*      */   private static final String LSEO_CREATEACTION_NAME = "CRPEERLSEO";
/*      */   
/*      */   private static final String LSEO_SRCHACTION_NAME = "SRDLSEO4";
/*      */   private static final String LSEOPS_LINKACTION_NAME = "LINKPRODSTRUCTLSEO";
/*      */   private static final String LSEOPS_DELETEACTION_NAME = "DELLSEOPRODSTRUCT";
/*      */   private static final String STATUS_FINAL = "0020";
/*   69 */   private static final String[] FCLIST_ATTR = new String[] { "CTRYPACKFCLIST", "LANGPACKFCLIST", "OTHERFCLIST", "PACKAGINGFCLIST", "PUBFCLIST", "LINECORDFCLIST", "KEYBRDFCLIST", "POINTDEVFCLIST" };
/*      */ 
/*      */   
/*   72 */   private static final String[] REQ_WWSEOALWR_ATTR = new String[] { "SEOID", "COUNTRYLIST", "GENAREASELECTION" };
/*   73 */   private static final String[] REQ_WWSEO_ATTR = new String[] { "COMNAME" };
/*      */ 
/*      */ 
/*      */   
/*   77 */   private static final Vector AUDIEN_VCT = new Vector(); static {
/*   78 */     AUDIEN_VCT.addElement("10046");
/*   79 */     AUDIEN_VCT.addElement("10048");
/*   80 */     AUDIEN_VCT.addElement("10054");
/*   81 */     AUDIEN_VCT.addElement("10062");
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
/*      */   public void execute_run() {
/*  104 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  106 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  117 */     String str3 = "";
/*  118 */     String str4 = "";
/*      */     
/*  120 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  123 */       start_ABRBuild();
/*      */ 
/*      */       
/*  126 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  128 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  130 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " extract: " + this.m_abri
/*  131 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  134 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  139 */       this.navName = getNavigationName(entityItem);
/*  140 */       str3 = this.m_elist.getParentEntityGroup().getLongDescription() + " &quot;" + this.navName + "&quot;";
/*      */       
/*  142 */       this.modelItem = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*      */       
/*  144 */       this.wwseoItem = this.m_elist.getEntityGroup("WWSEO").getEntityItem(0);
/*      */       
/*  146 */       if (this.wwseoItem == null) {
/*      */         
/*  148 */         this.args[0] = this.m_elist.getEntityGroup("WWSEO").getLongDescription();
/*  149 */         addError("NOT_FOUND_ERR", this.args);
/*      */       } else {
/*  151 */         verifyEntity(this.wwseoItem, REQ_WWSEO_ATTR);
/*  152 */         str3 = str3 + "<br /> for " + this.m_elist.getEntityGroup("WWSEO").getLongDescription() + " &quot;" + getNavigationName(this.wwseoItem) + "&quot;";
/*      */       } 
/*  154 */       if (this.modelItem == null) {
/*      */         
/*  156 */         this.args[0] = this.m_elist.getEntityGroup("MODEL").getLongDescription();
/*  157 */         addError("NOT_FOUND_ERR", this.args);
/*      */       } 
/*      */       
/*  160 */       if (getReturnCode() == 0)
/*      */       {
/*  162 */         EntityItem entityItem1 = checkAlwr(entityItem);
/*      */         
/*  164 */         if (getReturnCode() == 0) {
/*  165 */           if (entityItem1 == null) {
/*  166 */             createLSEO(entityItem);
/*      */           } else {
/*  168 */             updateLSEO(entityItem, entityItem1);
/*      */           } 
/*      */           
/*  171 */           if (this.createdLseoVct.size() > 0) {
/*      */             
/*  173 */             this.args[0] = "";
/*  174 */             for (byte b = 0; b < this.createdLseoVct.size(); b++) {
/*  175 */               this.args[0] = this.args[0] + this.createdLseoVct.elementAt(b).toString();
/*      */             }
/*  177 */             MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("CREATED_MSG"));
/*  178 */             addOutput(messageFormat1.format(this.args));
/*      */           } 
/*  180 */           if (this.updatedLseoVct.size() > 0) {
/*      */             
/*  182 */             this.args[0] = "";
/*  183 */             for (byte b = 0; b < this.updatedLseoVct.size(); b++) {
/*  184 */               this.args[0] = this.args[0] + this.updatedLseoVct.elementAt(b).toString();
/*      */             }
/*  186 */             MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("UPDATED_MSG"));
/*  187 */             addOutput(messageFormat1.format(this.args));
/*      */           } 
/*      */         } 
/*  190 */         if (this.createdLseoVct.size() == 0 && this.updatedLseoVct.size() == 0)
/*      */         {
/*  192 */           addOutput(this.rsBundle.getString("NO_CHGS"));
/*      */         }
/*      */       }
/*      */     
/*  196 */     } catch (Throwable throwable) {
/*  197 */       StringWriter stringWriter = new StringWriter();
/*  198 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  199 */       String str7 = "<pre>{0}</pre>";
/*  200 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  201 */       setReturnCode(-3);
/*  202 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  204 */       this.args[0] = throwable.getMessage();
/*  205 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  206 */       messageFormat1 = new MessageFormat(str7);
/*  207 */       this.args[0] = stringWriter.getBuffer().toString();
/*  208 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  209 */       logError("Exception: " + throwable.getMessage());
/*  210 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  214 */       setDGTitle(this.navName);
/*  215 */       setDGRptName(getShortClassName(getClass()));
/*  216 */       setDGRptClass(getABRCode());
/*      */       
/*  218 */       if (!isReadOnly())
/*      */       {
/*  220 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  226 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  227 */     this.args[0] = getDescription();
/*  228 */     this.args[1] = this.navName;
/*  229 */     String str5 = messageFormat.format(this.args);
/*  230 */     messageFormat = new MessageFormat(str2);
/*  231 */     this.args[0] = this.m_prof.getOPName();
/*  232 */     this.args[1] = this.m_prof.getRoleDescription();
/*  233 */     this.args[2] = this.m_prof.getWGName();
/*  234 */     this.args[3] = getNow();
/*  235 */     this.args[4] = str3;
/*  236 */     this.args[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  237 */     this.args[6] = str4 + " " + getABRVersion();
/*      */     
/*  239 */     this.rptSb.insert(0, str5 + messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  241 */     println(this.rptSb.toString());
/*  242 */     printDGSubmitString();
/*  243 */     println(EACustom.getTOUDiv());
/*  244 */     buildReportFooter();
/*      */     
/*  246 */     this.metaTbl.clear();
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
/*      */   private EntityItem checkAlwr(EntityItem paramEntityItem) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  308 */     EntityItem entityItem = null;
/*      */ 
/*      */     
/*  311 */     this.seoid = PokUtils.getAttributeValue(paramEntityItem, "SEOID", "", "", false);
/*  312 */     addDebug("Checking " + paramEntityItem.getKey() + " seoid " + this.seoid);
/*      */     
/*  314 */     if (verifyEntity(paramEntityItem, REQ_WWSEOALWR_ATTR)) {
/*  315 */       boolean bool = false;
/*  316 */       Vector<String> vector = new Vector(1);
/*      */       
/*  318 */       for (byte b = 0; b < FCLIST_ATTR.length; b++) {
/*  319 */         String str = PokUtils.getAttributeValue(paramEntityItem, FCLIST_ATTR[b], "", "", false);
/*  320 */         EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(FCLIST_ATTR[b]);
/*  321 */         if (eANMetaAttribute == null) {
/*  322 */           setReturnCode(-1);
/*  323 */           addOutput(str);
/*      */         } else {
/*      */           
/*  326 */           addDebug(FCLIST_ATTR[b] + " " + str);
/*  327 */           if (str.length() > 0) {
/*  328 */             StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/*  329 */             while (stringTokenizer.hasMoreTokens()) {
/*  330 */               String str1 = "1";
/*  331 */               String str2 = stringTokenizer.nextToken().trim();
/*      */               
/*  333 */               int i = str2.indexOf(":");
/*  334 */               if (i != -1) {
/*  335 */                 str1 = str2.substring(i + 1).trim();
/*  336 */                 str2 = str2.substring(0, i).trim();
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*  341 */               EntityItem entityItem1 = findFeatureProdstruct(str2);
/*  342 */               if (entityItem1 == null) {
/*  343 */                 if (!vector.contains(str2)) {
/*      */                   
/*  345 */                   this.args[0] = str2;
/*  346 */                   this.args[1] = getNavigationName(this.modelItem);
/*  347 */                   addError("FC_NOTFOUND_ERR", this.args);
/*  348 */                   vector.addElement(str2);
/*      */                 } 
/*  350 */                 bool = true; continue;
/*      */               } 
/*  352 */               FCQty fCQty = new FCQty(str2, str1, entityItem1);
/*  353 */               int j = this.fcQtyVct.indexOf(fCQty);
/*  354 */               if (j != -1) {
/*  355 */                 FCQty fCQty1 = this.fcQtyVct.elementAt(j);
/*      */ 
/*      */                 
/*  358 */                 if (!fCQty1.qty.equals(fCQty.qty)) {
/*      */                   
/*  360 */                   this.args[0] = fCQty1 + " " + fCQty;
/*  361 */                   addError("DUPLICATE_FCQTY_ERR", this.args);
/*  362 */                   bool = true;
/*      */                 
/*      */                 }
/*      */                 else {
/*      */                   
/*  367 */                   this.args[0] = str2;
/*  368 */                   addOutput(getResourceMsg("DUPLICATE_FC_MSG", this.args));
/*      */                 } 
/*  370 */                 fCQty.dereference(); continue;
/*      */               } 
/*  372 */               this.fcQtyVct.addElement(fCQty);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  379 */       if (!bool)
/*      */       {
/*      */         
/*  382 */         if (this.fcQtyVct.size() > 0) {
/*      */           
/*  384 */           entityItem = findLseo();
/*  385 */           if (entityItem != null) {
/*  386 */             String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  387 */             addDebug(entityItem.getKey() + " STATUS: " + PokUtils.getAttributeValue(entityItem, "STATUS", ", ", "", false) + " [" + str + "] ");
/*      */             
/*  389 */             if (null == str || str.length() == 0) {
/*  390 */               str = "0020";
/*      */             }
/*      */ 
/*      */             
/*  394 */             if (str.equals("0020"))
/*      */             {
/*  396 */               this.args[0] = getNavigationName(entityItem);
/*  397 */               addError("LSEO_FINAL_ERR", this.args);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  402 */             entityItem = searchForLSEO();
/*  403 */             if (entityItem != null) {
/*      */               
/*  405 */               this.args[0] = this.seoid;
/*  406 */               addError("LSEO_DUPLICATE_ERR", this.args);
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/*  411 */           this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/*  412 */           this.args[1] = getNavigationName(paramEntityItem);
/*  413 */           addError("NO_VALIDFC_ERR", this.args);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  418 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem findFeatureProdstruct(String paramString) {
/*  428 */     EntityItem entityItem = (EntityItem)this.fcodePsTbl.get(paramString);
/*  429 */     if (entityItem == null) {
/*  430 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("FEATURE");
/*  431 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  432 */         EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*  433 */         String str = PokUtils.getAttributeValue(entityItem1, "FEATURECODE", "", "", false);
/*  434 */         if (str.equals(paramString)) {
/*  435 */           entityItem = (EntityItem)entityItem1.getDownLink(0);
/*  436 */           addDebug("findFeatureProdstruct for " + paramString + " found " + entityItem1.getKey() + " " + entityItem.getKey());
/*  437 */           if (entityItem1.getDownLink().size() > 1) {
/*  438 */             addDebug("Warning: " + entityItem1.getKey() + " had multiple downlinks");
/*  439 */             for (byte b1 = 0; b1 < entityItem1.getDownLink().size(); b1++) {
/*  440 */               addDebug("Warning: downlink[" + b1 + "] " + entityItem1.getDownLink(b1).getKey());
/*      */             }
/*      */           } 
/*  443 */           this.fcodePsTbl.put(paramString, entityItem);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  449 */     return entityItem;
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
/*      */   private EntityItem findLseo() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  462 */     EntityItem entityItem = null;
/*  463 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/*  464 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  465 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*  466 */       String str = PokUtils.getAttributeValue(entityItem1, "SEOID", "", "", false);
/*  467 */       if (str.equals(this.seoid)) {
/*  468 */         entityItem = entityItem1;
/*  469 */         addDebug("findLseo for " + this.seoid + " found " + entityItem.getKey());
/*      */         break;
/*      */       } 
/*  472 */       addDebug("findLseo checking " + entityItem1.getKey() + " sid " + str + " seoid: " + this.seoid);
/*      */     } 
/*      */     
/*  475 */     return entityItem;
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
/*      */   private EntityItem searchForLSEO() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  489 */     EntityItem entityItem = null;
/*  490 */     Vector<String> vector1 = new Vector(1);
/*  491 */     vector1.addElement("SEOID");
/*  492 */     Vector<String> vector2 = new Vector(1);
/*  493 */     vector2.addElement(this.seoid);
/*      */     
/*  495 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/*  497 */       StringBuffer stringBuffer = new StringBuffer();
/*  498 */       arrayOfEntityItem = ABRUtil.doSearch(this.m_db, this.m_prof, "SRDLSEO4", "LSEO", false, vector1, vector2, stringBuffer);
/*      */       
/*  500 */       if (stringBuffer.length() > 0) {
/*  501 */         addDebug(stringBuffer.toString());
/*      */       }
/*  503 */     } catch (SBRException sBRException) {
/*      */       
/*  505 */       StringWriter stringWriter = new StringWriter();
/*  506 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  507 */       addDebug("searchForLSEO SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/*  509 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  510 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  511 */         addDebug("searchForLSEO found " + arrayOfEntityItem[b].getKey());
/*      */       }
/*      */       
/*  514 */       entityItem = arrayOfEntityItem[0];
/*      */     } 
/*  516 */     vector1.clear();
/*  517 */     vector2.clear();
/*  518 */     return entityItem;
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
/*  529 */     boolean bool = true;
/*  530 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  531 */       String str = PokUtils.getAttributeValue(paramEntityItem, paramArrayOfString[b], "", null, false);
/*  532 */       if (str == null) {
/*      */         
/*  534 */         this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/*  535 */         this.args[1] = getNavigationName(paramEntityItem);
/*  536 */         this.args[2] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramArrayOfString[b], paramArrayOfString[b]);
/*  537 */         addError("MISSING_ATTR_ERR", this.args);
/*  538 */         bool = false;
/*      */       } 
/*      */     } 
/*      */     
/*  542 */     return bool;
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
/*      */   private void createLSEO(EntityItem paramEntityItem) throws MiddlewareRequestException, RemoteException, SQLException, MiddlewareException, EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*  567 */     addDebug("createLSEO entered for SEOID " + this.seoid);
/*      */     
/*  569 */     EntityItem entityItem = null;
/*  570 */     AttrSet attrSet = new AttrSet(paramEntityItem);
/*      */     
/*  572 */     StringBuffer stringBuffer = new StringBuffer();
/*  573 */     entityItem = ABRUtil.createEntity(this.m_db, this.m_prof, "CRPEERLSEO", this.wwseoItem, "LSEO", attrSet
/*  574 */         .getAttrCodes(), attrSet.getAttrValues(), stringBuffer);
/*  575 */     if (stringBuffer.length() > 0) {
/*  576 */       addDebug(stringBuffer.toString());
/*      */     }
/*      */     
/*  579 */     attrSet.dereference();
/*      */     
/*  581 */     if (entityItem == null) {
/*      */       
/*  583 */       this.args[0] = this.seoid;
/*  584 */       addError("LSEO_CREATE_ERR", this.args);
/*      */     } else {
/*  586 */       this.createdLseoVct.addElement(new StringBuffer(getNavigationName(entityItem)));
/*      */       
/*  588 */       createFeatureRefs(entityItem);
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
/*      */   private void updateLSEO(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws MiddlewareBusinessRuleException, RemoteException, EANBusinessRuleException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException, LockException, WorkflowException {
/*  612 */     addDebug("updateLSEO entered for SEOID " + this.seoid + " " + paramEntityItem2.getKey());
/*      */     
/*  614 */     boolean bool = updateLSEOAttributes(paramEntityItem1, paramEntityItem2);
/*      */ 
/*      */ 
/*      */     
/*  618 */     String str = updateFeatureRefs(paramEntityItem2);
/*  619 */     if (bool || str.length() > 0) {
/*  620 */       this.updatedLseoVct.addElement(getNavigationName(paramEntityItem2) + str);
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
/*      */   private boolean updateLSEOAttributes(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
/*  639 */     AttrSet attrSet = new AttrSet(paramEntityItem1);
/*  640 */     addDebug("updateLSEOAttributes entered for " + paramEntityItem2.getKey());
/*  641 */     boolean bool = false;
/*      */ 
/*      */     
/*  644 */     Vector<String> vector = attrSet.getAttrCodes();
/*  645 */     for (byte b = 0; b < vector.size(); b++) {
/*  646 */       String str = vector.elementAt(b);
/*  647 */       StringBuffer stringBuffer = new StringBuffer();
/*      */       
/*  649 */       EANMetaAttribute eANMetaAttribute = paramEntityItem2.getEntityGroup().getMetaAttribute(str);
/*  650 */       if (eANMetaAttribute == null) {
/*  651 */         addDebug("MetaAttribute cannot be found " + paramEntityItem2.getEntityGroup().getEntityType() + "." + str + "\n");
/*      */       } else {
/*      */         String str1; boolean bool1;
/*  654 */         Object object = attrSet.getAttrValues().get(str);
/*  655 */         switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 'L':
/*      */           case 'T':
/*      */           case 'X':
/*  662 */             str1 = PokUtils.getAttributeValue(paramEntityItem2, str, "", "", false);
/*  663 */             if (!object.equals(str1)) {
/*  664 */               addDebug("Updating " + str + " was: " + str1 + " newval " + object);
/*      */               
/*  666 */               ABRUtil.setText(paramEntityItem2, str, (String)object, stringBuffer);
/*  667 */               bool = true;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  736 */             if (stringBuffer.length() > 0)
/*  737 */               addDebug(stringBuffer.toString());  break;case 'U': str1 = PokUtils.getAttributeFlagValue(paramEntityItem2, str); if (!object.equals(str1)) { if (str1 == null && object.equals("")) break;  addDebug("Updating " + str + " was: " + str1 + " newval " + object); ABRUtil.setUniqueFlag(paramEntityItem2, str, (String)object, stringBuffer); bool = true; }  if (stringBuffer.length() > 0) addDebug(stringBuffer.toString());  break;case 'F': str1 = PokUtils.getAttributeFlagValue(paramEntityItem2, str); bool1 = false; if (str1 == null) { if (object instanceof String && object.equals("")) break;  addDebug("Updating " + str + " was: " + str1 + " newval " + object); bool1 = true; } else if (object instanceof String) { if (!object.equals(str1)) { addDebug(str + " needs to be updated, " + str1 + " newval " + object); bool1 = true; }  } else { Vector<?> vector1 = (Vector)object; String[] arrayOfString = PokUtils.convertToArray(str1); Vector<String> vector2 = new Vector(arrayOfString.length); for (byte b1 = 0; b1 < arrayOfString.length; b1++) vector2.addElement(arrayOfString[b1]);  if (!vector2.containsAll(vector1) || !vector1.containsAll(vector2)) { addDebug(str + " needs to be updated"); bool1 = true; }  }  if (bool1) { Vector<Object> vector1 = null; if (object instanceof String) { vector1 = new Vector(); if (!object.equals("")) vector1.addElement(object);  } else { vector1 = (Vector<Object>)object; }  ABRUtil.setMultiFlag(paramEntityItem2, str, vector1, stringBuffer); bool = true; }  if (stringBuffer.length() > 0) addDebug(stringBuffer.toString());  break;default: addDebug("MetaAttribute Type=" + eANMetaAttribute.getAttributeType() + " is not supported yet " + paramEntityItem2.getEntityGroup().getEntityType() + "." + str + "\n"); if (stringBuffer.length() > 0) addDebug(stringBuffer.toString());  break;
/*      */         } 
/*      */       } 
/*  740 */     }  if (bool) {
/*  741 */       paramEntityItem2.commit(this.m_db, null);
/*      */     }
/*      */     
/*  744 */     attrSet.dereference();
/*      */     
/*  746 */     return bool;
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
/*      */   private void createFeatureRefs(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/*  765 */     String str1 = "LINKPRODSTRUCTLSEO";
/*  766 */     if (this.lai == null) {
/*  767 */       this.lai = new LinkActionItem(null, this.m_db, this.m_prof, str1);
/*      */     }
/*  769 */     EntityItem[] arrayOfEntityItem1 = { paramEntityItem };
/*  770 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[this.fcQtyVct.size()];
/*      */     
/*  772 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/*  774 */     for (byte b1 = 0; b1 < this.fcQtyVct.size(); b1++) {
/*  775 */       FCQty fCQty = this.fcQtyVct.elementAt(b1);
/*  776 */       arrayOfEntityItem2[b1] = fCQty.prodItem;
/*  777 */       hashtable.put(arrayOfEntityItem2[b1].getKey(), fCQty);
/*      */     } 
/*      */ 
/*      */     
/*  781 */     this.lai.setParentEntityItems(arrayOfEntityItem1);
/*  782 */     this.lai.setChildEntityItems(arrayOfEntityItem2);
/*  783 */     this.m_db.executeAction(this.m_prof, this.lai);
/*      */ 
/*      */ 
/*      */     
/*  787 */     Profile profile = this.m_prof.getNewInstance(this.m_db);
/*  788 */     String str2 = this.m_db.getDates().getNow();
/*  789 */     profile.setValOnEffOn(str2, str2);
/*      */     
/*  791 */     EntityList entityList = this.m_db.getEntityList(profile, new ExtractActionItem(null, this.m_db, profile, "BOXERLSEO2"), arrayOfEntityItem1);
/*      */ 
/*      */ 
/*      */     
/*  795 */     addDebug("createFeatureRefs list using VE BOXERLSEO2 after linkaction: " + str1 + "\n" + 
/*  796 */         PokUtils.outputList(entityList));
/*  797 */     EntityGroup entityGroup = entityList.getEntityGroup("PRODSTRUCT");
/*  798 */     StringBuffer stringBuffer1 = new StringBuffer();
/*  799 */     for (byte b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*  800 */       EntityItem entityItem1 = entityGroup.getEntityItem(b2);
/*  801 */       FCQty fCQty = (FCQty)hashtable.get(entityItem1.getKey());
/*  802 */       String str = fCQty.qty;
/*      */       
/*  804 */       stringBuffer1.append(getResourceMsg("ADDED_REF_MSG", new Object[] { FCQty.access$200(fCQty), FCQty.access$000(fCQty) }));
/*  805 */       EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/*  806 */       addDebug(entityItem1.getKey() + " use qty: " + str + " on " + entityItem2.getKey());
/*  807 */       if (str != null && !str.equals("1")) {
/*  808 */         StringBuffer stringBuffer = new StringBuffer();
/*      */         
/*  810 */         ABRUtil.setText(entityItem2, "CONFQTY", str, stringBuffer);
/*  811 */         if (stringBuffer.length() > 0) {
/*  812 */           addDebug(stringBuffer.toString());
/*      */         }
/*      */         
/*  815 */         entityItem2.commit(this.m_db, null);
/*      */       } 
/*      */     } 
/*  818 */     StringBuffer stringBuffer2 = this.createdLseoVct.lastElement();
/*  819 */     stringBuffer2.append(stringBuffer1.toString());
/*      */     
/*  821 */     hashtable.clear();
/*  822 */     entityList.dereference();
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
/*      */   private String updateFeatureRefs(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/*  843 */     StringBuffer stringBuffer = new StringBuffer();
/*  844 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/*  846 */     for (byte b1 = 0; b1 < this.fcQtyVct.size(); b1++) {
/*  847 */       FCQty fCQty = this.fcQtyVct.elementAt(b1);
/*  848 */       hashtable.put(fCQty.prodItem.getKey(), fCQty);
/*      */     } 
/*      */     
/*  851 */     EntityItem[] arrayOfEntityItem1 = { paramEntityItem };
/*  852 */     EntityList entityList1 = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "BOXERLSEO3"), arrayOfEntityItem1);
/*      */ 
/*      */     
/*  855 */     addDebug("current lseoprodstruct using VE BOXERLSEO3: " + PokUtils.outputList(entityList1));
/*  856 */     paramEntityItem = entityList1.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/*  859 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOPRODSTRUCT", "PRODSTRUCT");
/*  860 */     addDebug("updateFeatureRefs origPsVct " + vector1.size());
/*  861 */     for (byte b2 = 0; b2 < vector1.size(); b2++) {
/*  862 */       addDebug("updateFeatureRefs origPsVct[" + b2 + "] " + ((EntityItem)vector1.elementAt(b2)).getKey());
/*      */     }
/*      */ 
/*      */     
/*  866 */     Vector<EntityItem> vector2 = new Vector();
/*  867 */     for (byte b3 = 0; b3 < this.fcQtyVct.size(); b3++) {
/*  868 */       FCQty fCQty = this.fcQtyVct.elementAt(b3);
/*  869 */       boolean bool = false;
/*  870 */       Iterator<EntityItem> iterator = vector1.iterator();
/*  871 */       while (iterator.hasNext()) {
/*  872 */         EntityItem entityItem = iterator.next();
/*  873 */         if (entityItem.getKey().equals(fCQty.prodItem.getKey())) {
/*  874 */           addDebug("updateFeatureRefs already exists " + fCQty.prodItem.getKey());
/*  875 */           bool = true;
/*  876 */           iterator.remove();
/*      */           break;
/*      */         } 
/*      */       } 
/*  880 */       if (!bool) {
/*  881 */         addDebug("updateFeatureRefs missing " + fCQty.prodItem.getKey());
/*  882 */         vector2.add(fCQty.prodItem);
/*      */         
/*  884 */         stringBuffer.append(getResourceMsg("ADDED_REF_MSG", new Object[] { FCQty.access$200(fCQty), FCQty.access$000(fCQty) }));
/*      */       } 
/*      */     } 
/*      */     
/*  888 */     EntityItem[] arrayOfEntityItem2 = { paramEntityItem };
/*  889 */     String str1 = "LINKPRODSTRUCTLSEO";
/*      */     
/*  891 */     if (vector2.size() > 0) {
/*  892 */       addDebug("updateFeatureRefs  missingPsVct " + vector2.size());
/*  893 */       for (byte b = 0; b < vector2.size(); b++) {
/*  894 */         addDebug("updateFeatureRefs missingPsVct[" + b + "] " + ((EntityItem)vector2.elementAt(b)).getKey());
/*      */       }
/*  896 */       if (this.lai == null) {
/*  897 */         this.lai = new LinkActionItem(null, this.m_db, this.m_prof, str1);
/*      */       }
/*      */       
/*  900 */       EntityItem[] arrayOfEntityItem = new EntityItem[vector2.size()];
/*  901 */       vector2.copyInto((Object[])arrayOfEntityItem);
/*      */ 
/*      */       
/*  904 */       this.lai.setParentEntityItems(arrayOfEntityItem2);
/*  905 */       this.lai.setChildEntityItems(arrayOfEntityItem);
/*  906 */       this.m_db.executeAction(this.m_prof, this.lai);
/*      */     } 
/*      */     
/*  909 */     String str2 = "DELLSEOPRODSTRUCT";
/*  910 */     if (vector1.size() > 0) {
/*  911 */       addDebug("updateFeatureRefs unneeded cnt " + vector1.size());
/*  912 */       for (byte b = 0; b < vector1.size(); b++) {
/*  913 */         EntityItem entityItem = vector1.elementAt(b);
/*  914 */         addDebug("updateFeatureRefs unneeded [" + b + "] " + entityItem.getKey());
/*  915 */         for (byte b5 = 0; b5 < entityItem.getUpLinkCount(); b5++) {
/*  916 */           EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b5);
/*  917 */           if (entityItem1.getEntityType().equals("FEATURE")) {
/*      */             
/*  919 */             stringBuffer.append(getResourceMsg("DELETED_REF_MSG", new Object[] {
/*  920 */                     PokUtils.getAttributeValue(entityItem1, "FEATURECODE", "", "", false) }));
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  925 */       if (this.dai == null) {
/*  926 */         this.dai = new DeleteActionItem(null, this.m_db, this.m_prof, str2);
/*      */       }
/*      */       
/*  929 */       EntityItem[] arrayOfEntityItem = new EntityItem[vector1.size()];
/*  930 */       vector1.copyInto((Object[])arrayOfEntityItem);
/*      */       
/*  932 */       this.dai.setEntityItems(arrayOfEntityItem);
/*  933 */       this.m_db.executeAction(this.m_prof, this.dai);
/*  934 */       vector1.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  939 */     Profile profile = this.m_prof.getNewInstance(this.m_db);
/*  940 */     String str3 = this.m_db.getDates().getNow();
/*  941 */     profile.setValOnEffOn(str3, str3);
/*      */     
/*  943 */     EntityList entityList2 = this.m_db.getEntityList(profile, new ExtractActionItem(null, this.m_db, profile, "BOXERLSEO2"), arrayOfEntityItem2);
/*      */ 
/*      */ 
/*      */     
/*  947 */     addDebug("updateFeatureRefs list using VE BOXERLSEO2 after linkaction: " + str1 + " and deleteaction: " + str2 + "\n" + 
/*  948 */         PokUtils.outputList(entityList2));
/*  949 */     EntityGroup entityGroup = entityList2.getEntityGroup("PRODSTRUCT");
/*      */     
/*  951 */     for (byte b4 = 0; b4 < entityGroup.getEntityItemCount(); b4++) {
/*  952 */       EntityItem entityItem1 = entityGroup.getEntityItem(b4);
/*  953 */       FCQty fCQty = (FCQty)hashtable.get(entityItem1.getKey());
/*  954 */       String str4 = fCQty.qty;
/*  955 */       EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/*  956 */       String str5 = PokUtils.getAttributeValue(entityItem2, "CONFQTY", "", "", false);
/*  957 */       addDebug(entityItem1.getKey() + " needs qty: " + str4 + " on " + entityItem2.getKey() + " has qty: " + str5);
/*  958 */       if (str4 != null && !str4.equals(str5)) {
/*  959 */         StringBuffer stringBuffer1 = new StringBuffer();
/*      */         
/*  961 */         ABRUtil.setText(entityItem2, "CONFQTY", str4, stringBuffer1);
/*  962 */         if (stringBuffer1.length() > 0) {
/*  963 */           addDebug(stringBuffer1.toString());
/*      */         }
/*  965 */         if (!vector2.contains(entityItem1))
/*      */         {
/*  967 */           stringBuffer.append(getResourceMsg("UPDATED_REF_MSG", new Object[] { FCQty.access$200(fCQty), FCQty.access$000(fCQty) }));
/*      */         }
/*      */         
/*  970 */         entityItem2.commit(this.m_db, null);
/*      */       } 
/*      */     } 
/*      */     
/*  974 */     hashtable.clear();
/*  975 */     vector2.clear();
/*  976 */     entityList2.dereference();
/*  977 */     entityList1.dereference();
/*  978 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/*  984 */     super.dereference();
/*      */     
/*  986 */     for (byte b = 0; b < this.fcQtyVct.size(); b++) {
/*  987 */       FCQty fCQty = this.fcQtyVct.elementAt(b);
/*  988 */       fCQty.dereference();
/*      */     } 
/*  990 */     this.fcQtyVct.clear();
/*  991 */     this.fcQtyVct = null;
/*      */     
/*  993 */     this.createdLseoVct.clear();
/*  994 */     this.createdLseoVct = null;
/*  995 */     this.updatedLseoVct.clear();
/*  996 */     this.updatedLseoVct = null;
/*      */     
/*  998 */     this.rsBundle = null;
/*  999 */     this.modelItem = null;
/* 1000 */     this.wwseoItem = null;
/* 1001 */     this.lai = null;
/* 1002 */     this.dai = null;
/*      */     
/* 1004 */     this.rptSb = null;
/* 1005 */     this.args = null;
/*      */     
/* 1007 */     this.metaTbl = null;
/* 1008 */     this.navName = null;
/* 1009 */     this.fcodePsTbl.clear();
/* 1010 */     this.fcodePsTbl = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1016 */     return "1.3";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1023 */     return "WWSEOABRALWR";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 1029 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDebug(String paramString) {
/* 1035 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/* 1046 */     String str = this.rsBundle.getString(paramString);
/* 1047 */     if (paramArrayOfObject != null) {
/* 1048 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1049 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1052 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addError(String paramString, Object[] paramArrayOfObject) {
/* 1059 */     setReturnCode(-1);
/*      */     
/* 1061 */     String str = this.rsBundle.getString(paramString);
/*      */     
/* 1063 */     if (paramArrayOfObject != null) {
/* 1064 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1065 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1068 */     addOutput(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1078 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1081 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1082 */     if (eANList == null) {
/*      */       
/* 1084 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1085 */       eANList = entityGroup.getMetaAttribute();
/* 1086 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1088 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1090 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1091 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1092 */       if (b + 1 < eANList.size()) {
/* 1093 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1097 */     return stringBuffer.toString();
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
/*      */   private class AttrSet
/*      */   {
/* 1124 */     private Vector attrCodeVct = new Vector();
/* 1125 */     private Hashtable attrValTbl = new Hashtable<>();
/*      */     protected void addSingle(EntityItem param1EntityItem, String param1String) {
/* 1127 */       addSingle(param1EntityItem, param1String, param1String);
/*      */     }
/*      */     protected void addSingle(EntityItem param1EntityItem, String param1String1, String param1String2) {
/* 1130 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String1);
/* 1131 */       if (str == null) {
/* 1132 */         str = "";
/*      */       }
/* 1134 */       this.attrCodeVct.addElement(param1String2);
/* 1135 */       this.attrValTbl.put(param1String2, str);
/*      */     }
/*      */     protected void addText(EntityItem param1EntityItem, String param1String) {
/* 1138 */       addText(param1EntityItem, param1String, param1String);
/*      */     }
/*      */     protected void addText(EntityItem param1EntityItem, String param1String1, String param1String2) {
/* 1141 */       String str = PokUtils.getAttributeValue(param1EntityItem, param1String1, "", "", false);
/* 1142 */       this.attrCodeVct.addElement(param1String2);
/* 1143 */       this.attrValTbl.put(param1String2, str);
/*      */     }
/*      */     protected void addMult(EntityItem param1EntityItem, String param1String) {
/* 1146 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String);
/* 1147 */       if (str == null) {
/* 1148 */         str = "";
/*      */       }
/* 1150 */       String[] arrayOfString = PokUtils.convertToArray(str);
/* 1151 */       Vector<String> vector = new Vector(arrayOfString.length);
/* 1152 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1153 */         vector.addElement(arrayOfString[b]);
/*      */       }
/* 1155 */       this.attrCodeVct.addElement(param1String);
/* 1156 */       this.attrValTbl.put(param1String, vector);
/*      */     }
/*      */     
/*      */     AttrSet(EntityItem param1EntityItem) {
/* 1160 */       this.attrCodeVct.addElement("ACCTASGNGRP");
/* 1161 */       this.attrValTbl.put("ACCTASGNGRP", "01");
/*      */       
/* 1163 */       this.attrCodeVct.addElement("AUDIEN");
/* 1164 */       this.attrValTbl.put("AUDIEN", BOXERWWSEOALWRABR02.AUDIEN_VCT);
/*      */ 
/*      */       
/* 1167 */       addMult(param1EntityItem, "GENAREASELECTION");
/*      */       
/* 1169 */       addMult(param1EntityItem, "COUNTRYLIST");
/*      */       
/* 1171 */       addSingle(param1EntityItem, "LANGUAGES");
/*      */       
/* 1173 */       addSingle(param1EntityItem, "OFFCOUNTRY");
/*      */       
/* 1175 */       this.attrCodeVct.addElement("SEOID");
/* 1176 */       this.attrValTbl.put("SEOID", BOXERWWSEOALWRABR02.this.seoid);
/*      */       
/* 1178 */       addText(BOXERWWSEOALWRABR02.this.wwseoItem, "COMNAME");
/*      */       
/* 1180 */       addText(BOXERWWSEOALWRABR02.this.wwseoItem, "MKTGNAME", "LSEOMKTGDESC");
/*      */       
/* 1182 */       addText(BOXERWWSEOALWRABR02.this.wwseoItem, "PRODHIERCD");
/*      */ 
/*      */ 
/*      */       
/* 1186 */       EANMetaAttribute eANMetaAttribute = BOXERWWSEOALWRABR02.this.wwseoItem.getEntityGroup().getMetaAttribute("PDHDOMAIN");
/* 1187 */       if (eANMetaAttribute.getAttributeType().equals("F")) {
/* 1188 */         addMult(BOXERWWSEOALWRABR02.this.wwseoItem, "PDHDOMAIN");
/*      */       } else {
/* 1190 */         addSingle(BOXERWWSEOALWRABR02.this.wwseoItem, "PDHDOMAIN");
/*      */       } 
/*      */     }
/* 1193 */     Vector getAttrCodes() { return this.attrCodeVct; } Hashtable getAttrValues() {
/* 1194 */       return this.attrValTbl;
/*      */     }
/*      */     
/*      */     void dereference() {
/* 1198 */       this.attrCodeVct.clear();
/* 1199 */       this.attrValTbl.clear();
/* 1200 */       this.attrCodeVct = null;
/* 1201 */       this.attrValTbl = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private class FCQty {
/*      */     private String fcode;
/*      */     private String qty;
/*      */     private EntityItem prodItem;
/*      */     
/*      */     FCQty(String param1String1, String param1String2, EntityItem param1EntityItem) {
/* 1211 */       this.fcode = param1String1;
/* 1212 */       this.qty = param1String2;
/* 1213 */       this.prodItem = param1EntityItem;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1218 */       FCQty fCQty = (FCQty)param1Object;
/* 1219 */       return this.fcode.equals(fCQty.fcode);
/*      */     }
/*      */     public String toString() {
/* 1222 */       if (this.qty.equals("1")) {
/* 1223 */         return this.fcode;
/*      */       }
/* 1225 */       return this.fcode + ":" + this.qty;
/*      */     }
/*      */     void dereference() {
/* 1228 */       this.fcode = null;
/* 1229 */       this.qty = null;
/* 1230 */       this.prodItem = null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\BOXERWWSEOALWRABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */