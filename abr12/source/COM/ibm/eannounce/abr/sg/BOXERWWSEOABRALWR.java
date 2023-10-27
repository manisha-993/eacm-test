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
/*      */ public class BOXERWWSEOABRALWR
/*      */   extends PokBaseABR
/*      */ {
/*   61 */   private StringBuffer rptSb = new StringBuffer();
/*   62 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   63 */   static final String NEWLINE = new String(FOOL_JTEST);
/*   64 */   private Object[] args = (Object[])new String[10];
/*      */   
/*   66 */   private ResourceBundle rsBundle = null;
/*   67 */   private Hashtable metaTbl = new Hashtable<>();
/*   68 */   private String navName = ""; private EntityItem modelItem;
/*      */   private EntityItem wwseoItem;
/*   70 */   private Vector cdentityVct = new Vector();
/*   71 */   private Vector createdLseoVct = new Vector(1);
/*   72 */   private Vector updatedLseoVct = new Vector(1);
/*   73 */   private LinkActionItem lai = null;
/*   74 */   private DeleteActionItem dai = null;
/*   75 */   private Hashtable fcodePsTbl = new Hashtable<>();
/*   76 */   private Vector seoidVct = new Vector();
/*      */   
/*      */   private static final String LSEO_CREATEACTION_NAME = "CRPEERLSEO";
/*      */   
/*      */   private static final String LSEO_SRCHACTION_NAME = "SRDLSEO4";
/*      */   private static final String LSEOPS_LINKACTION_NAME = "LINKPRODSTRUCTLSEO";
/*      */   private static final String LSEOPS_DELETEACTION_NAME = "DELLSEOPRODSTRUCT";
/*      */   private static final String STATUS_FINAL = "0020";
/*   84 */   private static final String[] FCLIST_ATTR = new String[] { "LINECORDFCLIST", "KEYBRDFCLIST", "POINTDEVFCLIST", "CTRYPACKFCLIST", "LANGPACKFCLIST", "PACKAGINGFCLIST", "PUBFCLIST", "OTHERFCLIST" };
/*      */ 
/*      */   
/*   87 */   private static final String[] REQ_CDENTITY_ATTR = new String[] { "COUNTRYLIST", "CD", "GENAREASELECTION" };
/*   88 */   private static final String[] REQ_WWSEO_ATTR = new String[] { "SEOID", "XXPARTNO" };
/*   89 */   private static final String[] REQ_LSEO_ATTR = new String[] { "XXPARTNO" };
/*      */ 
/*      */   
/*   92 */   private static final Vector AUDIEN_VCT = new Vector(); static {
/*   93 */     AUDIEN_VCT.addElement("10046");
/*   94 */     AUDIEN_VCT.addElement("10048");
/*   95 */     AUDIEN_VCT.addElement("10054");
/*   96 */     AUDIEN_VCT.addElement("10062");
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
/*  119 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  121 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  132 */     String str3 = "";
/*  133 */     String str4 = "";
/*      */     
/*  135 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  138 */       start_ABRBuild(false);
/*      */       
/*  140 */       String str = "BOXERWWSEO";
/*  141 */       if (getEntityType().equals("LSEO")) {
/*  142 */         str = "BOXERLSEO";
/*      */       }
/*      */ 
/*      */       
/*  146 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  148 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */       
/*  151 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  153 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  155 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " extract: " + str + " using DTS: " + this.m_prof
/*  156 */           .getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  159 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  164 */       this.navName = getNavigationName(entityItem);
/*  165 */       str3 = "&quot;" + this.m_elist.getParentEntityGroup().getLongDescription() + "&quot; " + this.navName;
/*      */       
/*  167 */       this.modelItem = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*      */       
/*  169 */       if (getEntityType().equals("WWSEO")) {
/*  170 */         this.wwseoItem = entityItem;
/*  171 */         verifyEntity(entityItem, REQ_WWSEO_ATTR);
/*      */       } else {
/*  173 */         this.wwseoItem = this.m_elist.getEntityGroup("WWSEO").getEntityItem(0);
/*  174 */         verifyEntity(entityItem, REQ_LSEO_ATTR);
/*      */       } 
/*      */       
/*  177 */       if (this.wwseoItem == null) {
/*      */         
/*  179 */         this.args[0] = this.m_elist.getEntityGroup("WWSEO").getLongDescription();
/*  180 */         addError("NOT_FOUND_ERR", this.args);
/*      */       } 
/*  182 */       if (this.modelItem == null) {
/*      */         
/*  184 */         this.args[0] = this.m_elist.getEntityGroup("MODEL").getLongDescription();
/*  185 */         addError("NOT_FOUND_ERR", this.args);
/*      */       } 
/*  187 */       if (this.m_elist.getEntityGroup("CDG").getEntityItemCount() == 0) {
/*      */         
/*  189 */         this.args[0] = this.m_elist.getEntityGroup("CDG").getLongDescription();
/*  190 */         addError("NOT_FOUND_ERR", this.args);
/*      */       } 
/*  192 */       if (this.m_elist.getEntityGroup("CDG").getEntityItemCount() > 1) {
/*      */         
/*  194 */         this.args[0] = this.m_elist.getEntityGroup("CDG").getLongDescription();
/*  195 */         addError("MULTIPLE_ERR", this.args);
/*      */       } 
/*  197 */       if (getReturnCode() == 0) {
/*  198 */         EntityItem entityItem1 = this.m_elist.getEntityGroup("CDG").getEntityItem(0);
/*      */ 
/*      */         
/*  201 */         Vector vector = checkCDEntity(entityItem, entityItem1);
/*  202 */         EntityList entityList = null;
/*  203 */         if (vector.size() > 0) {
/*      */           
/*  205 */           EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/*  206 */           vector.copyInto((Object[])arrayOfEntityItem);
/*      */           
/*  208 */           entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "BOXERLSEO2"), arrayOfEntityItem);
/*      */ 
/*      */           
/*  211 */           addDebug("current lseoprodstruct using VE BOXERLSEO2: " + PokUtils.outputList(entityList));
/*  212 */           vector.clear();
/*      */         } 
/*      */         byte b;
/*  215 */         for (b = 0; b < this.cdentityVct.size(); b++) {
/*  216 */           CDEntityLseo cDEntityLseo = this.cdentityVct.elementAt(b);
/*  217 */           if (cDEntityLseo.lseoItem == null) {
/*  218 */             createLSEO(cDEntityLseo, entityItem);
/*      */           } else {
/*  220 */             updateLSEO(cDEntityLseo, entityItem, entityList);
/*      */           } 
/*      */         } 
/*      */         
/*  224 */         if (this.createdLseoVct.size() > 0) {
/*      */           
/*  226 */           this.args[0] = "" + this.createdLseoVct.size();
/*  227 */           this.args[1] = "";
/*  228 */           for (b = 0; b < this.createdLseoVct.size(); b++) {
/*  229 */             this.args[1] = this.args[1] + this.createdLseoVct.elementAt(b).toString();
/*      */           }
/*  231 */           MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("CREATED_MSG"));
/*  232 */           addOutput(messageFormat1.format(this.args));
/*      */         } 
/*  234 */         if (this.updatedLseoVct.size() > 0) {
/*      */           
/*  236 */           this.args[0] = "" + this.updatedLseoVct.size();
/*  237 */           this.args[1] = "";
/*  238 */           for (b = 0; b < this.updatedLseoVct.size(); b++) {
/*  239 */             this.args[1] = this.args[1] + this.updatedLseoVct.elementAt(b).toString();
/*      */           }
/*  241 */           MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("UPDATED_MSG"));
/*  242 */           addOutput(messageFormat1.format(this.args));
/*      */         } 
/*  244 */         if (this.createdLseoVct.size() == 0 && this.updatedLseoVct.size() == 0)
/*      */         {
/*  246 */           addOutput(this.rsBundle.getString("NO_CHGS"));
/*      */         }
/*  248 */         if (entityList != null) {
/*  249 */           entityList.dereference();
/*      */         }
/*      */       }
/*      */     
/*  253 */     } catch (Throwable throwable) {
/*  254 */       StringWriter stringWriter = new StringWriter();
/*  255 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  256 */       String str7 = "<pre>{0}</pre>";
/*  257 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  258 */       setReturnCode(-3);
/*  259 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  261 */       this.args[0] = throwable.getMessage();
/*  262 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  263 */       messageFormat1 = new MessageFormat(str7);
/*  264 */       this.args[0] = stringWriter.getBuffer().toString();
/*  265 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  266 */       logError("Exception: " + throwable.getMessage());
/*  267 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  271 */       setDGTitle(this.navName);
/*  272 */       setDGRptName(getShortClassName(getClass()));
/*  273 */       setDGRptClass(getABRCode());
/*      */       
/*  275 */       if (!isReadOnly())
/*      */       {
/*  277 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  283 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  284 */     this.args[0] = getDescription();
/*  285 */     this.args[1] = this.navName;
/*  286 */     String str5 = messageFormat.format(this.args);
/*  287 */     messageFormat = new MessageFormat(str2);
/*  288 */     this.args[0] = this.m_prof.getOPName();
/*  289 */     this.args[1] = this.m_prof.getRoleDescription();
/*  290 */     this.args[2] = this.m_prof.getWGName();
/*  291 */     this.args[3] = getNow();
/*  292 */     this.args[4] = str3;
/*  293 */     this.args[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  294 */     this.args[6] = str4 + " " + getABRVersion();
/*      */     
/*  296 */     this.rptSb.insert(0, str5 + messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  298 */     println(this.rptSb.toString());
/*  299 */     printDGSubmitString();
/*  300 */     println(EACustom.getTOUDiv());
/*  301 */     buildReportFooter();
/*      */     
/*  303 */     this.metaTbl.clear();
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
/*      */   
/*      */   private Vector checkCDEntity(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  374 */     Vector vector = new Vector(1);
/*  375 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem2, "CDGCDENTITY", "CDENTITY");
/*  376 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CDENTITY");
/*      */     
/*  378 */     if (vector1.size() == 0) {
/*      */       
/*  380 */       this.args[0] = entityGroup.getLongDescription();
/*  381 */       this.args[1] = paramEntityItem2.getEntityGroup().getLongDescription();
/*  382 */       this.args[2] = getNavigationName(paramEntityItem2);
/*  383 */       addError("NOT_FOUND_ERR2", this.args);
/*  384 */       return vector;
/*      */     } 
/*      */     
/*  387 */     byte b = 0; while (true) { if (b < vector1.size()) {
/*  388 */         EntityItem entityItem = vector1.elementAt(b);
/*      */ 
/*      */         
/*  391 */         String str = PokUtils.getAttributeValue(entityItem, "CD", "", "", false);
/*  392 */         addDebug("Checking " + entityItem.getKey() + " CD: " + str);
/*  393 */         int i = str.length();
/*  394 */         if (paramEntityItem1.getEntityType().equals("WWSEO"))
/*      */         
/*      */         { 
/*      */           
/*  398 */           if (i != 1)
/*      */           
/*  400 */           { this.args[0] = PokUtils.getAttributeDescription(entityGroup, "CD", "CD");
/*  401 */             this.args[1] = str;
/*  402 */             this.args[2] = getNavigationName(entityItem);
/*  403 */             addError("INVALID_CD_ERR", this.args);
/*  404 */             outputSkipErrMsg(entityItem, paramEntityItem2);
/*  405 */             addDebug("Skipping " + entityItem.getKey() + " because wrong CD.len"); }
/*      */           
/*      */           else
/*      */           
/*  409 */           { String str1 = PokUtils.getAttributeValue(paramEntityItem1, "SEOID", "", "", false);
/*  410 */             if (str1.length() > 6) {
/*  411 */               str1 = str1.substring(0, 6);
/*      */             }
/*  413 */             str1 = str1 + str;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  436 */             addDebug("derived seoid " + str1); }  } else if (i != 2) { this.args[0] = PokUtils.getAttributeDescription(entityGroup, "CD", "CD"); this.args[1] = str; this.args[2] = getNavigationName(entityItem); addError("INVALID_CD_ERR", this.args); outputSkipErrMsg(entityItem, paramEntityItem2); addDebug("Skipping " + entityItem.getKey() + " because wrong CD.len"); } else { String str1 = PokUtils.getAttributeValue(paramEntityItem1, "XXPARTNO", "", "", false); if (str1.length() > 5) str1 = str1.substring(0, 5);  str1 = str1 + str; addDebug("derived seoid " + str1); }
/*      */       
/*      */       } else {
/*      */         break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       b++; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  572 */     return vector;
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
/*      */   private void outputSkipErrMsg(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws SQLException, MiddlewareException {
/*  584 */     this.args[0] = paramEntityItem1.getEntityGroup().getLongDescription();
/*  585 */     this.args[1] = getNavigationName(paramEntityItem1);
/*  586 */     this.args[2] = paramEntityItem2.getEntityGroup().getLongDescription();
/*  587 */     this.args[3] = getNavigationName(paramEntityItem2);
/*  588 */     addError("SKIPPING_MSG", this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem findFeatureProdstruct(String paramString) {
/*  598 */     EntityItem entityItem = (EntityItem)this.fcodePsTbl.get(paramString);
/*  599 */     if (entityItem == null) {
/*  600 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("FEATURE");
/*  601 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  602 */         EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*  603 */         String str = PokUtils.getAttributeValue(entityItem1, "FEATURECODE", "", "", false);
/*  604 */         if (str.equals(paramString)) {
/*  605 */           entityItem = (EntityItem)entityItem1.getDownLink(0);
/*  606 */           addDebug("findFeatureProdstruct for " + paramString + " found " + entityItem1.getKey() + " " + entityItem.getKey());
/*  607 */           if (entityItem1.getDownLink().size() > 1) {
/*  608 */             addDebug("Warning: " + entityItem1.getKey() + " had multiple downlinks");
/*  609 */             for (byte b1 = 0; b1 < entityItem1.getDownLink().size(); b1++) {
/*  610 */               addDebug("Warning: downlink[" + b1 + "] " + entityItem1.getDownLink(b1).getKey());
/*      */             }
/*      */           } 
/*  613 */           this.fcodePsTbl.put(paramString, entityItem);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  619 */     return entityItem;
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
/*      */   private EntityItem findLseo(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  633 */     EntityItem entityItem = null;
/*  634 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/*  635 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  636 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*  637 */       String str = PokUtils.getAttributeValue(entityItem1, "SEOID", "", "", false);
/*  638 */       if (str.equals(paramString)) {
/*  639 */         entityItem = entityItem1;
/*  640 */         addDebug("findLseo for " + paramString + " found " + entityItem.getKey());
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  645 */     return entityItem;
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
/*  660 */     EntityItem entityItem = null;
/*  661 */     Vector<String> vector1 = new Vector(1);
/*  662 */     vector1.addElement("SEOID");
/*  663 */     Vector<String> vector2 = new Vector(1);
/*  664 */     vector2.addElement(paramString);
/*      */     
/*  666 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/*  668 */       StringBuffer stringBuffer = new StringBuffer();
/*  669 */       arrayOfEntityItem = ABRUtil.doSearch(this.m_db, this.m_prof, "SRDLSEO4", "LSEO", false, vector1, vector2, stringBuffer);
/*      */       
/*  671 */       if (stringBuffer.length() > 0) {
/*  672 */         addDebug(stringBuffer.toString());
/*      */       }
/*  674 */     } catch (SBRException sBRException) {
/*      */       
/*  676 */       StringWriter stringWriter = new StringWriter();
/*  677 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  678 */       addDebug("searchForLSEO SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/*  680 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  681 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  682 */         addDebug("searchForLSEO found " + arrayOfEntityItem[b].getKey());
/*      */       }
/*      */       
/*  685 */       entityItem = arrayOfEntityItem[0];
/*      */     } 
/*  687 */     vector1.clear();
/*  688 */     vector2.clear();
/*  689 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean verifyEntity(EntityItem paramEntityItem, String[] paramArrayOfString) throws SQLException, MiddlewareException {
/*  697 */     boolean bool = true;
/*  698 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  699 */       String str = PokUtils.getAttributeValue(paramEntityItem, paramArrayOfString[b], "", null, false);
/*  700 */       if (str == null) {
/*      */         
/*  702 */         this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/*  703 */         this.args[1] = getNavigationName(paramEntityItem);
/*  704 */         this.args[2] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramArrayOfString[b], paramArrayOfString[b]);
/*  705 */         addError("MISSING_ATTR_ERR", this.args);
/*  706 */         bool = false;
/*      */       } 
/*      */     } 
/*      */     
/*  710 */     return bool;
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
/*      */   private void createLSEO(CDEntityLseo paramCDEntityLseo, EntityItem paramEntityItem) throws MiddlewareRequestException, RemoteException, SQLException, MiddlewareException, EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*      */     LSEOAttrSet lSEOAttrSet;
/*  736 */     addDebug("createLSEO entered for SEOID " + paramCDEntityLseo.seoid);
/*      */     
/*  738 */     EntityItem entityItem = null;
/*  739 */     WWSEOAttrSet wWSEOAttrSet = null;
/*  740 */     if (paramEntityItem.getEntityType().equals("WWSEO")) {
/*  741 */       wWSEOAttrSet = new WWSEOAttrSet(paramCDEntityLseo, paramEntityItem);
/*      */     } else {
/*  743 */       lSEOAttrSet = new LSEOAttrSet(paramCDEntityLseo, paramEntityItem);
/*      */     } 
/*      */     
/*  746 */     StringBuffer stringBuffer = new StringBuffer();
/*  747 */     entityItem = ABRUtil.createEntity(this.m_db, this.m_prof, "CRPEERLSEO", this.wwseoItem, "LSEO", lSEOAttrSet
/*  748 */         .getAttrCodes(), lSEOAttrSet.getAttrValues(), stringBuffer);
/*  749 */     if (stringBuffer.length() > 0) {
/*  750 */       addDebug(stringBuffer.toString());
/*      */     }
/*      */     
/*  753 */     lSEOAttrSet.dereference();
/*      */     
/*  755 */     if (entityItem == null) {
/*      */       
/*  757 */       this.args[0] = paramCDEntityLseo.seoid;
/*  758 */       addError("LSEO_CREATE_ERR", this.args);
/*      */     } else {
/*  760 */       this.createdLseoVct.addElement(new StringBuffer(getNavigationName(entityItem)));
/*      */       
/*  762 */       createFeatureRefs(paramCDEntityLseo, entityItem);
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
/*      */   private void updateLSEO(CDEntityLseo paramCDEntityLseo, EntityItem paramEntityItem, EntityList paramEntityList) throws MiddlewareBusinessRuleException, RemoteException, EANBusinessRuleException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException, LockException, WorkflowException {
/*  787 */     EntityItem entityItem = paramEntityList.getParentEntityGroup().getEntityItem(paramCDEntityLseo.lseoItem.getKey());
/*  788 */     addDebug("updateLSEO entered for SEOID " + paramCDEntityLseo.seoid + " " + entityItem.getKey());
/*      */     
/*  790 */     boolean bool = updateLSEOAttributes(paramCDEntityLseo, paramEntityItem, entityItem);
/*      */ 
/*      */ 
/*      */     
/*  794 */     String str = updateFeatureRefs(paramCDEntityLseo, entityItem);
/*  795 */     if (bool || str.length() > 0) {
/*  796 */       this.updatedLseoVct.addElement(getNavigationName(entityItem) + str);
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
/*      */   private boolean updateLSEOAttributes(CDEntityLseo paramCDEntityLseo, EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
/*      */     LSEOAttrSet lSEOAttrSet;
/*  815 */     WWSEOAttrSet wWSEOAttrSet = null;
/*  816 */     addDebug("updateLSEOAttributes entered for " + paramEntityItem2.getKey());
/*  817 */     if (paramEntityItem1.getEntityType().equals("WWSEO")) {
/*  818 */       wWSEOAttrSet = new WWSEOAttrSet(paramCDEntityLseo, paramEntityItem1);
/*      */     } else {
/*  820 */       lSEOAttrSet = new LSEOAttrSet(paramCDEntityLseo, paramEntityItem1);
/*      */     } 
/*  822 */     boolean bool = false;
/*      */ 
/*      */     
/*  825 */     Vector<String> vector = lSEOAttrSet.getAttrCodes();
/*  826 */     for (byte b = 0; b < vector.size(); b++) {
/*  827 */       String str = vector.elementAt(b);
/*  828 */       StringBuffer stringBuffer = new StringBuffer();
/*      */       
/*  830 */       EANMetaAttribute eANMetaAttribute = paramEntityItem2.getEntityGroup().getMetaAttribute(str);
/*  831 */       if (eANMetaAttribute == null) {
/*  832 */         addDebug("MetaAttribute cannot be found " + paramEntityItem2.getEntityGroup().getEntityType() + "." + str + "\n");
/*      */       } else {
/*      */         String str1; boolean bool1;
/*  835 */         Object object = lSEOAttrSet.getAttrValues().get(str);
/*  836 */         switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 'L':
/*      */           case 'T':
/*      */           case 'X':
/*  843 */             str1 = PokUtils.getAttributeValue(paramEntityItem2, str, "", "", false);
/*  844 */             if (!object.equals(str1)) {
/*  845 */               addDebug("Updating " + str + " was: " + str1 + " newval " + object);
/*      */               
/*  847 */               ABRUtil.setText(paramEntityItem2, str, (String)object, stringBuffer);
/*  848 */               bool = true;
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
/*  917 */             if (stringBuffer.length() > 0)
/*  918 */               addDebug(stringBuffer.toString());  break;case 'U': str1 = PokUtils.getAttributeFlagValue(paramEntityItem2, str); if (!object.equals(str1)) { if (str1 == null && object.equals("")) break;  addDebug("Updating " + str + " was: " + str1 + " newval " + object); ABRUtil.setUniqueFlag(paramEntityItem2, str, (String)object, stringBuffer); bool = true; }  if (stringBuffer.length() > 0) addDebug(stringBuffer.toString());  break;case 'F': str1 = PokUtils.getAttributeFlagValue(paramEntityItem2, str); bool1 = false; if (str1 == null) { if (object instanceof String && object.equals("")) break;  addDebug("Updating " + str + " was: " + str1 + " newval " + object); bool1 = true; } else if (object instanceof String) { if (!object.equals(str1)) { addDebug(str + " needs to be updated, " + str1 + " newval " + object); bool1 = true; }  } else { Vector<?> vector1 = (Vector)object; String[] arrayOfString = PokUtils.convertToArray(str1); Vector<String> vector2 = new Vector(arrayOfString.length); for (byte b1 = 0; b1 < arrayOfString.length; b1++) vector2.addElement(arrayOfString[b1]);  if (!vector2.containsAll(vector1) || !vector1.containsAll(vector2)) { addDebug(str + " needs to be updated"); bool1 = true; }  }  if (bool1) { Vector<Object> vector1 = null; if (object instanceof String) { vector1 = new Vector(); if (!object.equals("")) vector1.addElement(object);  } else { vector1 = (Vector<Object>)object; }  ABRUtil.setMultiFlag(paramEntityItem2, str, vector1, stringBuffer); bool = true; }  if (stringBuffer.length() > 0) addDebug(stringBuffer.toString());  break;default: addDebug("MetaAttribute Type=" + eANMetaAttribute.getAttributeType() + " is not supported yet " + paramEntityItem2.getEntityGroup().getEntityType() + "." + str + "\n"); if (stringBuffer.length() > 0) addDebug(stringBuffer.toString());  break;
/*      */         } 
/*      */       } 
/*  921 */     }  if (bool) {
/*  922 */       paramEntityItem2.commit(this.m_db, null);
/*      */     }
/*      */     
/*  925 */     lSEOAttrSet.dereference();
/*      */     
/*  927 */     return bool;
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
/*      */   private void createFeatureRefs(CDEntityLseo paramCDEntityLseo, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/*  947 */     String str1 = "LINKPRODSTRUCTLSEO";
/*  948 */     if (this.lai == null) {
/*  949 */       this.lai = new LinkActionItem(null, this.m_db, this.m_prof, str1);
/*      */     }
/*  951 */     EntityItem[] arrayOfEntityItem1 = { paramEntityItem };
/*  952 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[paramCDEntityLseo.fcQtyVct.size()];
/*      */     
/*  954 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/*  956 */     for (byte b1 = 0; b1 < paramCDEntityLseo.fcQtyVct.size(); b1++) {
/*  957 */       FCQty fCQty = paramCDEntityLseo.fcQtyVct.elementAt(b1);
/*  958 */       arrayOfEntityItem2[b1] = fCQty.prodItem;
/*  959 */       hashtable.put(arrayOfEntityItem2[b1].getKey(), fCQty);
/*      */     } 
/*      */ 
/*      */     
/*  963 */     this.lai.setParentEntityItems(arrayOfEntityItem1);
/*  964 */     this.lai.setChildEntityItems(arrayOfEntityItem2);
/*  965 */     this.m_db.executeAction(this.m_prof, this.lai);
/*      */ 
/*      */ 
/*      */     
/*  969 */     Profile profile = this.m_prof.getNewInstance(this.m_db);
/*  970 */     String str2 = this.m_db.getDates().getNow();
/*  971 */     profile.setValOnEffOn(str2, str2);
/*      */     
/*  973 */     EntityList entityList = this.m_db.getEntityList(profile, new ExtractActionItem(null, this.m_db, profile, "BOXERLSEO2"), arrayOfEntityItem1);
/*      */ 
/*      */ 
/*      */     
/*  977 */     addDebug("createFeatureRefs list using VE BOXERLSEO2 after linkaction: " + str1 + "\n" + 
/*  978 */         PokUtils.outputList(entityList));
/*  979 */     EntityGroup entityGroup = entityList.getEntityGroup("PRODSTRUCT");
/*  980 */     StringBuffer stringBuffer1 = new StringBuffer();
/*  981 */     for (byte b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*  982 */       EntityItem entityItem1 = entityGroup.getEntityItem(b2);
/*  983 */       FCQty fCQty = (FCQty)hashtable.get(entityItem1.getKey());
/*  984 */       String str = fCQty.qty;
/*      */       
/*  986 */       stringBuffer1.append(getResourceMsg("ADDED_REF_MSG", new Object[] { FCQty.access$500(fCQty), FCQty.access$200(fCQty) }));
/*  987 */       EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/*  988 */       addDebug(entityItem1.getKey() + " use qty: " + str + " on " + entityItem2.getKey());
/*  989 */       if (str != null && !str.equals("1")) {
/*  990 */         StringBuffer stringBuffer = new StringBuffer();
/*      */         
/*  992 */         ABRUtil.setText(entityItem2, "CONFQTY", str, stringBuffer);
/*  993 */         if (stringBuffer.length() > 0) {
/*  994 */           addDebug(stringBuffer.toString());
/*      */         }
/*      */         
/*  997 */         entityItem2.commit(this.m_db, null);
/*      */       } 
/*      */     } 
/* 1000 */     StringBuffer stringBuffer2 = this.createdLseoVct.lastElement();
/* 1001 */     stringBuffer2.append(stringBuffer1.toString());
/*      */     
/* 1003 */     hashtable.clear();
/* 1004 */     entityList.dereference();
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
/*      */   private String updateFeatureRefs(CDEntityLseo paramCDEntityLseo, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/* 1026 */     StringBuffer stringBuffer = new StringBuffer();
/* 1027 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/* 1029 */     for (byte b1 = 0; b1 < paramCDEntityLseo.fcQtyVct.size(); b1++) {
/* 1030 */       FCQty fCQty = paramCDEntityLseo.fcQtyVct.elementAt(b1);
/* 1031 */       hashtable.put(fCQty.prodItem.getKey(), fCQty);
/*      */     } 
/*      */ 
/*      */     
/* 1035 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOPRODSTRUCT", "PRODSTRUCT");
/* 1036 */     addDebug("updateFeatureRefs origPsVct " + vector1.size());
/* 1037 */     for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 1038 */       addDebug("updateFeatureRefs origPsVct[" + b2 + "] " + ((EntityItem)vector1.elementAt(b2)).getKey());
/*      */     }
/*      */ 
/*      */     
/* 1042 */     Vector<EntityItem> vector2 = new Vector();
/* 1043 */     for (byte b3 = 0; b3 < paramCDEntityLseo.fcQtyVct.size(); b3++) {
/* 1044 */       FCQty fCQty = paramCDEntityLseo.fcQtyVct.elementAt(b3);
/* 1045 */       boolean bool = false;
/* 1046 */       Iterator<EntityItem> iterator = vector1.iterator();
/* 1047 */       while (iterator.hasNext()) {
/* 1048 */         EntityItem entityItem = iterator.next();
/* 1049 */         if (entityItem.getKey().equals(fCQty.prodItem.getKey())) {
/* 1050 */           addDebug("updateFeatureRefs already exists " + fCQty.prodItem.getKey());
/* 1051 */           bool = true;
/* 1052 */           iterator.remove();
/*      */           break;
/*      */         } 
/*      */       } 
/* 1056 */       if (!bool) {
/* 1057 */         addDebug("updateFeatureRefs missing " + fCQty.prodItem.getKey());
/* 1058 */         vector2.add(fCQty.prodItem);
/*      */         
/* 1060 */         stringBuffer.append(getResourceMsg("ADDED_REF_MSG", new Object[] { FCQty.access$500(fCQty), FCQty.access$200(fCQty) }));
/*      */       } 
/*      */     } 
/*      */     
/* 1064 */     EntityItem[] arrayOfEntityItem = { paramEntityItem };
/* 1065 */     String str1 = "LINKPRODSTRUCTLSEO";
/*      */     
/* 1067 */     if (vector2.size() > 0) {
/* 1068 */       addDebug("updateFeatureRefs  missingPsVct " + vector2.size());
/* 1069 */       for (byte b = 0; b < vector2.size(); b++) {
/* 1070 */         addDebug("updateFeatureRefs missingPsVct[" + b + "] " + ((EntityItem)vector2.elementAt(b)).getKey());
/*      */       }
/* 1072 */       if (this.lai == null) {
/* 1073 */         this.lai = new LinkActionItem(null, this.m_db, this.m_prof, str1);
/*      */       }
/*      */       
/* 1076 */       EntityItem[] arrayOfEntityItem1 = new EntityItem[vector2.size()];
/* 1077 */       vector2.copyInto((Object[])arrayOfEntityItem1);
/*      */ 
/*      */       
/* 1080 */       this.lai.setParentEntityItems(arrayOfEntityItem);
/* 1081 */       this.lai.setChildEntityItems(arrayOfEntityItem1);
/* 1082 */       this.m_db.executeAction(this.m_prof, this.lai);
/*      */     } 
/*      */     
/* 1085 */     String str2 = "DELLSEOPRODSTRUCT";
/* 1086 */     if (vector1.size() > 0) {
/* 1087 */       addDebug("updateFeatureRefs unneeded cnt " + vector1.size());
/* 1088 */       for (byte b = 0; b < vector1.size(); b++) {
/* 1089 */         EntityItem entityItem = vector1.elementAt(b);
/* 1090 */         addDebug("updateFeatureRefs unneeded [" + b + "] " + entityItem.getKey());
/* 1091 */         for (byte b5 = 0; b5 < entityItem.getUpLinkCount(); b5++) {
/* 1092 */           EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b5);
/* 1093 */           if (entityItem1.getEntityType().equals("FEATURE")) {
/*      */             
/* 1095 */             stringBuffer.append(getResourceMsg("DELETED_REF_MSG", new Object[] {
/* 1096 */                     PokUtils.getAttributeValue(entityItem1, "FEATURECODE", "", "", false) }));
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1101 */       if (this.dai == null) {
/* 1102 */         this.dai = new DeleteActionItem(null, this.m_db, this.m_prof, str2);
/*      */       }
/*      */       
/* 1105 */       EntityItem[] arrayOfEntityItem1 = new EntityItem[vector1.size()];
/* 1106 */       vector1.copyInto((Object[])arrayOfEntityItem1);
/*      */       
/* 1108 */       this.dai.setEntityItems(arrayOfEntityItem1);
/* 1109 */       this.m_db.executeAction(this.m_prof, this.dai);
/* 1110 */       vector1.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1115 */     Profile profile = this.m_prof.getNewInstance(this.m_db);
/* 1116 */     String str3 = this.m_db.getDates().getNow();
/* 1117 */     profile.setValOnEffOn(str3, str3);
/*      */     
/* 1119 */     EntityList entityList = this.m_db.getEntityList(profile, new ExtractActionItem(null, this.m_db, profile, "BOXERLSEO2"), arrayOfEntityItem);
/*      */ 
/*      */ 
/*      */     
/* 1123 */     addDebug("updateFeatureRefs list using VE BOXERLSEO2 after linkaction: " + str1 + " and deleteaction: " + str2 + "\n" + 
/* 1124 */         PokUtils.outputList(entityList));
/* 1125 */     EntityGroup entityGroup = entityList.getEntityGroup("PRODSTRUCT");
/*      */     
/* 1127 */     for (byte b4 = 0; b4 < entityGroup.getEntityItemCount(); b4++) {
/* 1128 */       EntityItem entityItem1 = entityGroup.getEntityItem(b4);
/* 1129 */       FCQty fCQty = (FCQty)hashtable.get(entityItem1.getKey());
/* 1130 */       String str4 = fCQty.qty;
/* 1131 */       EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 1132 */       String str5 = PokUtils.getAttributeValue(entityItem2, "CONFQTY", "", "", false);
/* 1133 */       addDebug(entityItem1.getKey() + " needs qty: " + str4 + " on " + entityItem2.getKey() + " has qty: " + str5);
/* 1134 */       if (str4 != null && !str4.equals(str5)) {
/* 1135 */         StringBuffer stringBuffer1 = new StringBuffer();
/*      */         
/* 1137 */         ABRUtil.setText(entityItem2, "CONFQTY", str4, stringBuffer1);
/* 1138 */         if (stringBuffer1.length() > 0) {
/* 1139 */           addDebug(stringBuffer1.toString());
/*      */         }
/* 1141 */         if (!vector2.contains(entityItem1))
/*      */         {
/* 1143 */           stringBuffer.append(getResourceMsg("UPDATED_REF_MSG", new Object[] { FCQty.access$500(fCQty), FCQty.access$200(fCQty) }));
/*      */         }
/*      */         
/* 1146 */         entityItem2.commit(this.m_db, null);
/*      */       } 
/*      */     } 
/*      */     
/* 1150 */     hashtable.clear();
/* 1151 */     vector2.clear();
/* 1152 */     entityList.dereference();
/* 1153 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 1159 */     super.dereference();
/*      */     
/* 1161 */     for (byte b = 0; b < this.cdentityVct.size(); b++) {
/* 1162 */       CDEntityLseo cDEntityLseo = this.cdentityVct.elementAt(b);
/* 1163 */       cDEntityLseo.dereference();
/*      */     } 
/* 1165 */     this.cdentityVct.clear();
/* 1166 */     this.cdentityVct = null;
/*      */     
/* 1168 */     this.createdLseoVct.clear();
/* 1169 */     this.createdLseoVct = null;
/* 1170 */     this.updatedLseoVct.clear();
/* 1171 */     this.updatedLseoVct = null;
/*      */     
/* 1173 */     this.rsBundle = null;
/* 1174 */     this.modelItem = null;
/* 1175 */     this.wwseoItem = null;
/* 1176 */     this.lai = null;
/* 1177 */     this.dai = null;
/*      */     
/* 1179 */     this.rptSb = null;
/* 1180 */     this.args = null;
/*      */     
/* 1182 */     this.metaTbl = null;
/* 1183 */     this.navName = null;
/* 1184 */     this.fcodePsTbl.clear();
/* 1185 */     this.fcodePsTbl = null;
/* 1186 */     this.seoidVct.clear();
/* 1187 */     this.seoidVct = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1193 */     return "1.4";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1200 */     return "WWSEOABRALWR";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 1206 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDebug(String paramString) {
/* 1212 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/* 1223 */     String str = this.rsBundle.getString(paramString);
/* 1224 */     if (paramArrayOfObject != null) {
/* 1225 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1226 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1229 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addError(String paramString, Object[] paramArrayOfObject) {
/* 1236 */     setReturnCode(-1);
/*      */     
/* 1238 */     String str = this.rsBundle.getString(paramString);
/*      */     
/* 1240 */     if (paramArrayOfObject != null) {
/* 1241 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1242 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1245 */     addOutput(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1255 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1258 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1259 */     if (eANList == null) {
/*      */       
/* 1261 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1262 */       eANList = entityGroup.getMetaAttribute();
/* 1263 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1265 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1267 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1268 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1269 */       if (b + 1 < eANList.size()) {
/* 1270 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1274 */     return stringBuffer.toString();
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
/*      */   private class AttrSet
/*      */   {
/* 1312 */     private Vector attrCodeVct = new Vector();
/* 1313 */     private Hashtable attrValTbl = new Hashtable<>();
/*      */     protected void addSingle(EntityItem param1EntityItem, String param1String) {
/* 1315 */       addSingle(param1EntityItem, param1String, param1String);
/*      */     }
/*      */     protected void addSingle(EntityItem param1EntityItem, String param1String1, String param1String2) {
/* 1318 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String1);
/* 1319 */       if (str == null) {
/* 1320 */         str = "";
/*      */       }
/* 1322 */       this.attrCodeVct.addElement(param1String2);
/* 1323 */       this.attrValTbl.put(param1String2, str);
/*      */     }
/*      */     protected void addText(EntityItem param1EntityItem, String param1String) {
/* 1326 */       addText(param1EntityItem, param1String, param1String);
/*      */     }
/*      */     protected void addText(EntityItem param1EntityItem, String param1String1, String param1String2) {
/* 1329 */       String str = PokUtils.getAttributeValue(param1EntityItem, param1String1, "", "", false);
/* 1330 */       this.attrCodeVct.addElement(param1String2);
/* 1331 */       this.attrValTbl.put(param1String2, str);
/*      */     }
/*      */     protected void addMult(EntityItem param1EntityItem, String param1String) {
/* 1334 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String);
/* 1335 */       if (str == null) {
/* 1336 */         str = "";
/*      */       }
/* 1338 */       String[] arrayOfString = PokUtils.convertToArray(str);
/* 1339 */       Vector<String> vector = new Vector(arrayOfString.length);
/* 1340 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1341 */         vector.addElement(arrayOfString[b]);
/*      */       }
/* 1343 */       this.attrCodeVct.addElement(param1String);
/* 1344 */       this.attrValTbl.put(param1String, vector);
/*      */     }
/*      */     
/*      */     AttrSet(BOXERWWSEOABRALWR.CDEntityLseo param1CDEntityLseo) {
/* 1348 */       this.attrCodeVct.addElement("ACCTASGNGRP");
/* 1349 */       this.attrValTbl.put("ACCTASGNGRP", "01");
/*      */       
/* 1351 */       this.attrCodeVct.addElement("AUDIEN");
/* 1352 */       this.attrValTbl.put("AUDIEN", BOXERWWSEOABRALWR.AUDIEN_VCT);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1357 */       this.attrCodeVct.addElement("SEOID");
/* 1358 */       this.attrValTbl.put("SEOID", param1CDEntityLseo.seoid);
/* 1359 */       this.attrCodeVct.addElement("COMNAME");
/* 1360 */       this.attrValTbl.put("COMNAME", param1CDEntityLseo.seoid);
/*      */       
/* 1362 */       addSingle(param1CDEntityLseo.cdEntity, "LANGUAGES");
/*      */       
/* 1364 */       addSingle(param1CDEntityLseo.cdEntity, "OFFCOUNTRY");
/*      */       
/* 1366 */       addMult(param1CDEntityLseo.cdEntity, "GENAREASELECTION");
/*      */       
/* 1368 */       addMult(param1CDEntityLseo.cdEntity, "COUNTRYLIST");
/*      */     }
/* 1370 */     Vector getAttrCodes() { return this.attrCodeVct; } Hashtable getAttrValues() {
/* 1371 */       return this.attrValTbl;
/*      */     }
/*      */     
/*      */     void dereference() {
/* 1375 */       this.attrCodeVct.clear();
/* 1376 */       this.attrValTbl.clear();
/* 1377 */       this.attrCodeVct = null;
/* 1378 */       this.attrValTbl = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class WWSEOAttrSet
/*      */     extends AttrSet
/*      */   {
/*      */     WWSEOAttrSet(BOXERWWSEOABRALWR.CDEntityLseo param1CDEntityLseo, EntityItem param1EntityItem) {
/* 1392 */       super(param1CDEntityLseo);
/*      */       
/* 1394 */       addText(param1EntityItem, "XXPARTNO");
/*      */       
/* 1396 */       addText(param1EntityItem, "MKTGNAME", "LSEOMKTGDESC");
/*      */       
/* 1398 */       addText(param1EntityItem, "PRODHIERCD");
/*      */ 
/*      */       
/* 1401 */       EANMetaAttribute eANMetaAttribute = param1EntityItem.getEntityGroup().getMetaAttribute("PDHDOMAIN");
/* 1402 */       if (eANMetaAttribute.getAttributeType().equals("F")) {
/* 1403 */         addMult(param1EntityItem, "PDHDOMAIN");
/*      */       } else {
/* 1405 */         addSingle(param1EntityItem, "PDHDOMAIN");
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
/*      */   private class LSEOAttrSet
/*      */     extends AttrSet
/*      */   {
/*      */     LSEOAttrSet(BOXERWWSEOABRALWR.CDEntityLseo param1CDEntityLseo, EntityItem param1EntityItem) {
/* 1420 */       super(param1CDEntityLseo);
/*      */       
/* 1422 */       addText(param1EntityItem, "XXPARTNO");
/*      */       
/* 1424 */       addText(param1EntityItem, "LSEOMKTGDESC");
/*      */       
/* 1426 */       addText(param1EntityItem, "PRODHIERCD");
/*      */       
/* 1428 */       addText(param1EntityItem, "LSEOPUBDATEMTRGT");
/*      */       
/* 1430 */       addText(param1EntityItem, "LSEOUNPUBDATEMTRGT");
/*      */ 
/*      */       
/* 1433 */       EANMetaAttribute eANMetaAttribute = param1EntityItem.getEntityGroup().getMetaAttribute("PDHDOMAIN");
/* 1434 */       if (eANMetaAttribute.getAttributeType().equals("F")) {
/* 1435 */         addMult(param1EntityItem, "PDHDOMAIN");
/*      */       } else {
/* 1437 */         addSingle(param1EntityItem, "PDHDOMAIN");
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class CDEntityLseo { private EntityItem cdEntity;
/*      */     private String seoid;
/* 1444 */     private Vector fcQtyVct = new Vector(); private EntityItem lseoItem;
/*      */     
/*      */     CDEntityLseo(EntityItem param1EntityItem, String param1String) {
/* 1447 */       this.cdEntity = param1EntityItem;
/* 1448 */       this.seoid = param1String;
/*      */     }
/*      */     void addFcQty(BOXERWWSEOABRALWR.FCQty param1FCQty) {
/* 1451 */       this.fcQtyVct.add(param1FCQty);
/*      */     }
/* 1453 */     boolean hasFeatures() { return (this.fcQtyVct.size() > 0); } void setLseo(EntityItem param1EntityItem) {
/* 1454 */       this.lseoItem = param1EntityItem;
/*      */     } void dereference() {
/* 1456 */       for (byte b = 0; b < this.fcQtyVct.size(); b++) {
/* 1457 */         BOXERWWSEOABRALWR.FCQty fCQty = this.fcQtyVct.elementAt(b);
/* 1458 */         fCQty.dereference();
/*      */       } 
/* 1460 */       this.fcQtyVct.clear();
/* 1461 */       this.fcQtyVct = null;
/*      */       
/* 1463 */       this.lseoItem = null;
/* 1464 */       this.cdEntity = null;
/* 1465 */       this.seoid = null;
/*      */     } }
/*      */   
/*      */   private class FCQty {
/*      */     private String fcode;
/*      */     private String qty;
/*      */     private EntityItem prodItem;
/*      */     
/*      */     FCQty(String param1String1, String param1String2, EntityItem param1EntityItem) {
/* 1474 */       this.fcode = param1String1;
/* 1475 */       this.qty = param1String2;
/* 1476 */       this.prodItem = param1EntityItem;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1481 */       FCQty fCQty = (FCQty)param1Object;
/* 1482 */       return this.fcode.equals(fCQty.fcode);
/*      */     }
/*      */     public String toString() {
/* 1485 */       if (this.qty.equals("1")) {
/* 1486 */         return this.fcode;
/*      */       }
/* 1488 */       return this.fcode + ":" + this.qty;
/*      */     }
/*      */     void dereference() {
/* 1491 */       this.fcode = null;
/* 1492 */       this.qty = null;
/* 1493 */       this.prodItem = null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\BOXERWWSEOABRALWR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */