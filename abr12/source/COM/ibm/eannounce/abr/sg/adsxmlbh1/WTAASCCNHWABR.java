/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.xml.transform.TransformerConfigurationException;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WTAASCCNHWABR
/*      */   extends PokBaseABR
/*      */ {
/*   54 */   private ResourceBundle rsBundle = null;
/*   55 */   private String annDate = "";
/*   56 */   private StringBuffer rptSb = new StringBuffer();
/*   57 */   private StringBuffer xmlgenSb = new StringBuffer();
/*   58 */   private StringBuffer userxmlSb = new StringBuffer();
/*   59 */   private String t2DTS = "&nbsp;";
/*   60 */   private Object[] args = (Object[])new String[10];
/*   61 */   private String pathName = null;
/*   62 */   private Vector finalEntities = new Vector();
/*      */   public static final String RPTPATH = "_rptpath";
/*      */   public static final String STATUS_FINAL = "0020";
/*      */   public static final String MODEL_HARDWARE = "100";
/*   66 */   public static final CharSequence GEO_CCN = "1464";
/*   67 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   68 */   protected static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*   70 */   private static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("WTAASCCNHWABR");
/*      */ 
/*      */   
/*   73 */   protected static final Vector AVAILTYPE_FILTER = new Vector(2); static {
/*   74 */     AVAILTYPE_FILTER.add("146");
/*   75 */     AVAILTYPE_FILTER.add("143");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*   82 */     String str1 = "";
/*      */     
/*      */     try {
/*   85 */       long l = System.currentTimeMillis();
/*      */       
/*   87 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*   90 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), 
/*   91 */           ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*   94 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */       
/*   98 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getVeName());
/*   99 */       this.m_elist = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, 
/*  100 */               getEntityType(), getEntityID()) });
/*      */       
/*  102 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  103 */       addDebug("DEBUG: rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*  104 */       String str = getAttributeValue(entityItem, "ANNNUMBER");
/*  105 */       addDebug("RFA number: " + str);
/*      */       
/*  107 */       str1 = getNavigationName(entityItem);
/*      */       
/*  109 */       if (getReturnCode() == 0) {
/*  110 */         processThis(this, this.m_prof, entityItem);
/*      */         
/*  112 */         boolean bool = generateTXTFile(str, this.finalEntities);
/*  113 */         if (bool)
/*      */         {
/*  115 */           sendMail(this.pathName);
/*      */         }
/*      */       } 
/*      */       
/*  119 */       addDebug("Total Time: " + 
/*  120 */           Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  122 */     catch (Throwable throwable) {
/*  123 */       StringWriter stringWriter = new StringWriter();
/*  124 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  125 */       String str7 = "<pre>{0}</pre>";
/*  126 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  127 */       setReturnCode(-3);
/*  128 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  130 */       this.args[0] = throwable.getMessage();
/*  131 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  132 */       messageFormat1 = new MessageFormat(str7);
/*  133 */       this.args[0] = stringWriter.getBuffer().toString();
/*  134 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  135 */       logError("Exception: " + throwable.getMessage());
/*  136 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*  138 */       if ("&nbsp;".equals(this.t2DTS)) {
/*  139 */         this.t2DTS = getNow();
/*      */       }
/*  141 */       setDGTitle(str1);
/*  142 */       setDGRptName(getShortClassName(getClass()));
/*  143 */       setDGRptClass(getABRCode());
/*      */       
/*  145 */       if (!isReadOnly()) {
/*  146 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  152 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  165 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */     
/*  169 */     MessageFormat messageFormat = new MessageFormat(str2);
/*  170 */     this.args[0] = getShortClassName(getClass());
/*  171 */     this.args[1] = str1;
/*  172 */     String str3 = messageFormat.format(this.args);
/*  173 */     String str4 = buildAbrHeader();
/*      */ 
/*      */     
/*  176 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("RESULT_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  177 */     this.rptSb.insert(0, str5);
/*      */     
/*  179 */     println(this.rptSb.toString());
/*  180 */     printDGSubmitString();
/*  181 */     println(EACustom.getTOUDiv());
/*  182 */     buildReportFooter();
/*      */ 
/*      */     
/*  185 */     this.m_elist.dereference();
/*  186 */     this.m_elist = null;
/*  187 */     this.rsBundle = null;
/*  188 */     this.args = null;
/*  189 */     messageFormat = null;
/*  190 */     this.userxmlSb = null;
/*  191 */     this.rptSb = null;
/*  192 */     this.xmlgenSb = null;
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
/*      */   public void processThis(WTAASCCNHWABR paramWTAASCCNHWABR, Profile paramProfile, EntityItem paramEntityItem) throws TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException, IOException {
/*  211 */     Vector<EntityItem> vector = new Vector();
/*  212 */     Vector vector1 = new Vector();
/*  213 */     Vector vector2 = new Vector();
/*  214 */     Vector vector3 = new Vector();
/*  215 */     Vector vector4 = new Vector();
/*  216 */     Vector vector5 = new Vector();
/*      */     
/*      */     try {
/*  219 */       addDebug("Starting process data from VE");
/*      */       
/*  221 */       EntityList entityList = paramWTAASCCNHWABR.getEntityList(paramProfile, getVeName(), paramEntityItem);
/*  222 */       addDebug("entityList: " + entityList);
/*  223 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  225 */       String str1 = PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN");
/*  226 */       addDebug("RootEntity:" + entityItem.getKey() + " PDHDOMAIN:" + str1);
/*      */       
/*  228 */       String str2 = PokUtils.getAttributeFlagValue(entityItem, "ANNSTATUS");
/*  229 */       if (!"0020".equals(str2)) {
/*  230 */         addDebug("skip " + entityItem.getKey() + " for entity status is not Final");
/*  231 */         this.userxmlSb.append("Entity " + entityItem.getKey() + " status is not Final" + NEWLINE);
/*      */       } else {
/*      */         
/*  234 */         this.annDate = getAttributeValue(entityItem, "ANNDATE");
/*  235 */         addDebug("RootEntity:" + entityItem.getKey() + " ANNDATE:" + this.annDate);
/*      */         
/*  237 */         Vector<EntityItem> vector6 = PokUtils.getAllLinkedEntities(entityItem, "ANNAVAILA", "AVAIL");
/*  238 */         addDebug("avail size " + vector6.size());
/*  239 */         for (byte b = 0; b < vector6.size(); b++) {
/*  240 */           EntityItem entityItem1 = vector6.get(b);
/*  241 */           addDebug("avail number" + b + '\001');
/*      */           
/*  243 */           String str = getAttributeFlagValue(entityItem1, "AVAILTYPE");
/*  244 */           if (!AVAILTYPE_FILTER.contains(str)) {
/*  245 */             addDebug("skip " + entityItem1.getKey() + " for AVAILTYPE " + str);
/*      */           }
/*      */           else {
/*      */             
/*  249 */             String str3 = getAttributeFlagValue(entityItem1, "COUNTRYLIST");
/*  250 */             String[] arrayOfString = splitStr(str3, "|");
/*  251 */             Vector<String> vector7 = new Vector();
/*  252 */             for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*  253 */               vector7.add(arrayOfString[b1]);
/*      */             }
/*      */             
/*  256 */             if (!vector7.contains(GEO_CCN)) {
/*  257 */               addDebug("skip " + entityItem1.getKey() + " for don't have Canada Geo in CountryList");
/*      */             }
/*      */             else {
/*      */               
/*  261 */               String str4 = getAttributeFlagValue(entityItem1, "STATUS");
/*  262 */               if (!"0020".equals(str4)) {
/*  263 */                 addDebug("skip " + entityItem1.getKey() + " for entity status is not Final");
/*      */               }
/*      */               
/*  266 */               Vector vector8 = PokUtils.getAllLinkedEntities(entityItem1, "MODELAVAIL", "MODEL");
/*  267 */               addDebug("model size " + vector8.size());
/*      */               
/*  269 */               Vector vector9 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "PRODSTRUCT");
/*  270 */               addDebug("tmf size " + vector9.size());
/*      */               
/*  272 */               Vector vector10 = PokUtils.getAllLinkedEntities(entityItem1, "MODELCONVERTAVAIL", "MODELCONVERT");
/*  273 */               addDebug("modelconvert size " + vector10.size());
/*      */               
/*  275 */               if (vector8.size() > 0 || vector9.size() > 0 || vector10.size() > 0) {
/*  276 */                 vector.add(entityItem1);
/*      */               } else {
/*  278 */                 addDebug("The " + entityItem1.getKey() + " didn't link model and tmf and modelconvert");
/*      */               } 
/*      */ 
/*      */               
/*  282 */               getModelProdInfo(vector8, vector2, vector1);
/*      */               
/*  284 */               getFeatureProdInfo(vector9, vector3, vector4, vector1);
/*      */               
/*  286 */               getModelconProdInfo(vector10, vector5, vector1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  291 */         long l = System.currentTimeMillis();
/*  292 */         this.finalEntities = getPriceInfo(vector1);
/*  293 */         addDebug("get price info Time: " + 
/*  294 */             Stopwatch.format(System.currentTimeMillis() - l));
/*      */       } 
/*      */     } finally {
/*      */       
/*  298 */       vector.clear();
/*  299 */       vector = null;
/*  300 */       vector1.clear();
/*  301 */       vector1 = null;
/*  302 */       vector2.clear();
/*  303 */       vector2 = null;
/*  304 */       vector3.clear();
/*  305 */       vector3 = null;
/*  306 */       vector4.clear();
/*  307 */       vector4 = null;
/*  308 */       vector5.clear();
/*  309 */       vector5 = null;
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
/*      */   private void getModelProdInfo(Vector<EntityItem> paramVector1, Vector<String> paramVector2, Vector<WTAASCCNHWENTITY> paramVector3) {
/*  321 */     for (byte b = 0; b < paramVector1.size(); b++) {
/*  322 */       EntityItem entityItem = paramVector1.get(b);
/*      */       
/*  324 */       String str1 = getAttributeFlagValue(entityItem, "COFCAT");
/*  325 */       String str2 = getAttributeFlagValue(entityItem, "STATUS");
/*  326 */       if ("100".equals(str1) && "0020".equals(str2)) {
/*  327 */         if (!paramVector2.contains("" + entityItem.getEntityID())) {
/*  328 */           paramVector2.add("" + entityItem.getEntityID());
/*      */           
/*  330 */           addDebug("HW Model: " + entityItem.getKey());
/*  331 */           WTAASCCNHWENTITY wTAASCCNHWENTITY = new WTAASCCNHWENTITY();
/*  332 */           wTAASCCNHWENTITY.setType("MODEL");
/*      */           
/*  334 */           wTAASCCNHWENTITY.setMachtype(getAttributeFlagValue(entityItem, "MACHTYPEATR"));
/*  335 */           addDebug("MODEL MACHTYPEATR: " + 
/*  336 */               getAttributeFlagValue(entityItem, "MACHTYPEATR"));
/*  337 */           wTAASCCNHWENTITY.setModel(getAttributeValue(entityItem, "MODELATR"));
/*  338 */           addDebug("MODEL MACHTYPEATR: " + 
/*  339 */               getAttributeValue(entityItem, "MODELATR"));
/*  340 */           wTAASCCNHWENTITY.setAnnDate(dateFormat(this.annDate));
/*      */           
/*  342 */           paramVector3.add(wTAASCCNHWENTITY);
/*      */         } else {
/*  344 */           addDebug("duplicate " + entityItem.getKey());
/*      */         } 
/*      */       } else {
/*  347 */         addDebug("skip " + entityItem.getKey() + " for not HWï¿½ï¿½MODEL or status is not final");
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
/*      */   private void getFeatureProdInfo(Vector<EntityItem> paramVector1, Vector<String> paramVector2, Vector<String> paramVector3, Vector<WTAASCCNHWENTITY> paramVector4) {
/*  361 */     for (byte b = 0; b < paramVector1.size(); b++) {
/*  362 */       EntityItem entityItem = paramVector1.get(b);
/*      */       
/*  364 */       String str = getAttributeFlagValue(entityItem, "STATUS");
/*  365 */       if ("0020".equals(str)) {
/*  366 */         if (!paramVector2.contains("" + entityItem.getEntityID())) {
/*  367 */           paramVector2.add("" + entityItem.getEntityID());
/*  368 */           addDebug("Prodstruct: " + entityItem.getKey());
/*      */           
/*  370 */           EntityItem entityItem1 = getModelEntityFromTmf(entityItem);
/*  371 */           String str1 = getAttributeFlagValue(entityItem1, "STATUS");
/*  372 */           String str2 = getAttributeFlagValue(entityItem1, "MACHTYPEATR");
/*      */           
/*  374 */           EntityItem entityItem2 = getFeatureEntityFromTmf(entityItem);
/*  375 */           String str3 = getAttributeFlagValue(entityItem2, "STATUS");
/*      */           
/*  377 */           if ("0020".equals(str3) && "0020".equals(str1) && !paramVector3.contains("" + str2 + entityItem2.getEntityID())) {
/*  378 */             paramVector3.add("" + str2 + entityItem2.getEntityID());
/*  379 */             WTAASCCNHWENTITY wTAASCCNHWENTITY = new WTAASCCNHWENTITY();
/*  380 */             wTAASCCNHWENTITY.setType("FEATURE");
/*      */             
/*  382 */             wTAASCCNHWENTITY.setMachtype(str2);
/*  383 */             addDebug("FEATURE MACHTYPEATR: " + getAttributeFlagValue(entityItem1, "MACHTYPEATR"));
/*  384 */             wTAASCCNHWENTITY.setFeatureCode(getAttributeValue(entityItem2, "FEATURECODE"));
/*  385 */             addDebug("FEATURE FEATURECODE: " + getAttributeValue(entityItem2, "FEATURECODE"));
/*  386 */             wTAASCCNHWENTITY.setAnnDate(dateFormat(this.annDate));
/*  387 */             paramVector4.add(wTAASCCNHWENTITY);
/*      */           } else {
/*  389 */             addDebug("skip " + entityItem.getKey() + " for linked MODEL and FEATURE status is not all final");
/*      */           } 
/*      */         } else {
/*  392 */           addDebug("duplicate " + entityItem.getKey());
/*      */         } 
/*      */       } else {
/*  395 */         addDebug("skip " + entityItem.getKey() + " for status is not final");
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
/*      */   private void getModelconProdInfo(Vector<EntityItem> paramVector1, Vector<String> paramVector2, Vector<WTAASCCNHWENTITY> paramVector3) {
/*  409 */     for (byte b = 0; b < paramVector1.size(); b++) {
/*  410 */       EntityItem entityItem = paramVector1.get(b);
/*      */       
/*  412 */       String str = getAttributeFlagValue(entityItem, "STATUS");
/*  413 */       if ("0020".equals(str)) {
/*  414 */         if (!paramVector2.contains("" + entityItem
/*  415 */             .getEntityID())) {
/*  416 */           paramVector2.add("" + entityItem.getEntityID());
/*      */           
/*  418 */           addDebug("Modelconvert: " + entityItem.getKey());
/*  419 */           WTAASCCNHWENTITY wTAASCCNHWENTITY = new WTAASCCNHWENTITY();
/*  420 */           wTAASCCNHWENTITY.setType("MODELCONVERT");
/*      */           
/*  422 */           wTAASCCNHWENTITY.setMachtype(getAttributeValue(entityItem, "FROMMACHTYPE"));
/*  423 */           addDebug("MODELCONVERT FROMMACHTYPE: " + getAttributeValue(entityItem, "FROMMACHTYPE"));
/*  424 */           wTAASCCNHWENTITY.setModel(getAttributeValue(entityItem, "FROMMODEL"));
/*  425 */           addDebug("MODELCONVERT FROMMODEL: " + getAttributeValue(entityItem, "FROMMODEL"));
/*  426 */           wTAASCCNHWENTITY.setToMachtype(getAttributeValue(entityItem, "TOMACHTYPE"));
/*  427 */           addDebug("MODELCONVERT TOMACHTYPE: " + getAttributeValue(entityItem, "TOMACHTYPE"));
/*  428 */           wTAASCCNHWENTITY.setToModel(getAttributeValue(entityItem, "TOMODEL"));
/*  429 */           addDebug("MODELCONVERT TOMODEL: " + getAttributeValue(entityItem, "TOMODEL"));
/*  430 */           wTAASCCNHWENTITY.setAnnDate(dateFormat(this.annDate));
/*      */           
/*  432 */           paramVector3.add(wTAASCCNHWENTITY);
/*      */         } else {
/*  434 */           addDebug("duplicate " + entityItem.getKey());
/*      */         } 
/*      */       } else {
/*  437 */         addDebug("skip " + entityItem.getKey() + " for status is not final");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] splitStr(String paramString1, String paramString2) {
/*  444 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, paramString2);
/*  445 */     String[] arrayOfString = new String[stringTokenizer.countTokens()];
/*  446 */     byte b = 0;
/*  447 */     while (stringTokenizer.hasMoreTokens()) {
/*  448 */       arrayOfString[b] = stringTokenizer.nextToken();
/*  449 */       b++;
/*      */     } 
/*  451 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String setFileName(String paramString) {
/*  460 */     StringBuffer stringBuffer = new StringBuffer(paramString.trim());
/*  461 */     String str1 = getNow();
/*      */     
/*  463 */     str1 = str1.replace(' ', '_');
/*  464 */     stringBuffer.append(str1 + ".txt");
/*  465 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_rptpath", "/Dgq");
/*  466 */     if (!str2.endsWith("/")) {
/*  467 */       str2 = str2 + "/";
/*      */     }
/*  469 */     this.pathName = str2 + stringBuffer.toString();
/*      */     
/*  471 */     addDebug("**** ffPathName: " + this.pathName + " ffFileName: " + stringBuffer.toString());
/*  472 */     return this.pathName;
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
/*      */   private boolean generateTXTFile(String paramString, Vector<WTAASCCNHWENTITY> paramVector) throws IOException {
/*  484 */     if (paramVector.size() == 0) {
/*  485 */       this.userxmlSb.append("File generate failed, for there is no qualified data related, please check ");
/*  486 */       return false;
/*      */     } 
/*      */     try {
/*  489 */       setFileName(paramString);
/*  490 */       FileOutputStream fileOutputStream = new FileOutputStream(this.pathName);
/*  491 */       OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*  492 */       StringBuffer stringBuffer = new StringBuffer();
/*  493 */       for (byte b = 0; b < paramVector.size(); b++) {
/*  494 */         WTAASCCNHWENTITY wTAASCCNHWENTITY = paramVector.elementAt(b);
/*  495 */         Iterator<Map.Entry> iterator = wTAASCCNHWENTITY.getPrice().entrySet().iterator();
/*  496 */         while (iterator.hasNext()) {
/*  497 */           Map.Entry entry = iterator.next();
/*  498 */           String str1 = (String)entry.getKey();
/*  499 */           String str2 = (String)entry.getValue();
/*  500 */           if (str2 == null || str2.length() <= 0) {
/*  501 */             addDebug("filter unhandled pricetype"); continue;
/*      */           } 
/*  503 */           if ("MODEL".equals(wTAASCCNHWENTITY.getType())) {
/*  504 */             stringBuffer.append(getValue(wTAASCCNHWENTITY.getMachtype(), 4));
/*  505 */             stringBuffer.append(" ");
/*  506 */             stringBuffer.append(getValue(wTAASCCNHWENTITY.getModel(), 3));
/*      */             
/*  508 */             stringBuffer.append("            ");
/*      */           }
/*  510 */           else if ("FEATURE".equals(wTAASCCNHWENTITY.getType())) {
/*  511 */             stringBuffer.append(getValue(wTAASCCNHWENTITY.getMachtype(), 4));
/*  512 */             stringBuffer.append(" ");
/*  513 */             stringBuffer.append(getValue(wTAASCCNHWENTITY.getFeatureCode(), 4));
/*      */             
/*  515 */             stringBuffer.append("           ");
/*      */           }
/*  517 */           else if ("MODELCONVERT".equals(wTAASCCNHWENTITY.getType())) {
/*  518 */             stringBuffer.append(getValue(wTAASCCNHWENTITY.getMachtype(), 4));
/*  519 */             stringBuffer.append(" ");
/*  520 */             stringBuffer.append(getValue(wTAASCCNHWENTITY.getModel(), 3));
/*  521 */             stringBuffer.append(" ");
/*  522 */             stringBuffer.append(getValue(wTAASCCNHWENTITY.getToMachtype(), 4));
/*  523 */             stringBuffer.append(" ");
/*  524 */             stringBuffer.append(getValue(wTAASCCNHWENTITY.getToModel(), 3));
/*  525 */             stringBuffer.append("   ");
/*      */           } else {
/*      */             
/*  528 */             addDebug("Entity type not processed");
/*      */           } 
/*      */           
/*  531 */           stringBuffer.append(getValue(str2, 3));
/*  532 */           stringBuffer.append("   ");
/*      */ 
/*      */           
/*  535 */           stringBuffer.append(getValue(str1, 12));
/*  536 */           stringBuffer.append("   ");
/*  537 */           stringBuffer.append(wTAASCCNHWENTITY.getAnnDate());
/*  538 */           stringBuffer.append("\r\n");
/*      */         } 
/*      */       } 
/*      */       
/*  542 */       outputStreamWriter.write(stringBuffer.toString());
/*  543 */       outputStreamWriter.flush();
/*  544 */       outputStreamWriter.close();
/*  545 */       this.userxmlSb.append("File generate success");
/*  546 */       return true;
/*      */     }
/*  548 */     catch (WTAASException wTAASException) {
/*  549 */       this.userxmlSb.append(wTAASException);
/*  550 */       return false;
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
/*      */   public String getValue(String paramString, int paramInt) throws WTAASException {
/*  563 */     StringBuffer stringBuffer = new StringBuffer();
/*  564 */     if (paramString.length() == paramInt)
/*  565 */       return paramString; 
/*  566 */     if (paramString.length() > paramInt) {
/*  567 */       throw new WTAASException(paramString + " length too long");
/*      */     }
/*  569 */     for (byte b = 0; b < paramInt - paramString.length(); b++) {
/*  570 */       stringBuffer.append(" ");
/*      */     }
/*  572 */     stringBuffer.append(paramString);
/*  573 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String dateFormat(String paramString) {
/*  584 */     SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
/*  585 */     SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
/*  586 */     String str = null;
/*      */     try {
/*  588 */       str = simpleDateFormat1.format(simpleDateFormat2.parse(paramString));
/*  589 */     } catch (ParseException parseException) {
/*  590 */       addDebug("date parse Exception: " + parseException);
/*  591 */       parseException.printStackTrace();
/*      */     } 
/*  593 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String doubleFormat(String paramString) {
/*  604 */     if (paramString.equals(null)) {
/*  605 */       return "0.00".toString();
/*      */     }
/*  607 */     BigDecimal bigDecimal = new BigDecimal(paramString);
/*  608 */     bigDecimal = bigDecimal.setScale(2, 4);
/*  609 */     addDebug(paramString + " kep 2 decimal " + bigDecimal.toString());
/*  610 */     return bigDecimal.toString();
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
/*      */   public void sendMail(String paramString) throws Exception {
/*  622 */     FileInputStream fileInputStream = null;
/*      */     try {
/*  624 */       fileInputStream = new FileInputStream(paramString);
/*  625 */       int i = fileInputStream.available();
/*  626 */       byte[] arrayOfByte = new byte[i];
/*  627 */       fileInputStream.read(arrayOfByte);
/*  628 */       setAttachByteForDG(arrayOfByte);
/*  629 */       getABRItem().setFileExtension("txt");
/*  630 */       addDebug("Sending mail for file " + paramString);
/*  631 */     } catch (IOException iOException) {
/*  632 */       addDebug("sendMail IO Exception");
/*      */     }
/*      */     finally {
/*      */       
/*  636 */       if (fileInputStream != null) {
/*  637 */         fileInputStream.close();
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
/*      */   private EntityItem getModelEntityFromTmf(EntityItem paramEntityItem) {
/*  650 */     EntityItem entityItem = null;
/*  651 */     Vector<EntityItem> vector = paramEntityItem.getDownLink();
/*  652 */     if (vector != null && vector.size() > 0) {
/*  653 */       for (byte b = 0; b < vector.size(); b++) {
/*  654 */         EntityItem entityItem1 = vector.get(b);
/*  655 */         if ("MODEL".equals(entityItem1.getEntityType())) {
/*  656 */           entityItem = entityItem1;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*  661 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getFeatureEntityFromTmf(EntityItem paramEntityItem) {
/*  671 */     EntityItem entityItem = null;
/*  672 */     Vector<EntityItem> vector = paramEntityItem.getUpLink();
/*  673 */     if (vector != null && vector.size() > 0) {
/*  674 */       for (byte b = 0; b < vector.size(); b++) {
/*  675 */         EntityItem entityItem1 = vector.get(b);
/*  676 */         if ("FEATURE".equals(entityItem1.getEntityType())) {
/*  677 */           entityItem = entityItem1;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*  682 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getAttributeFlagValue(EntityItem paramEntityItem, String paramString) {
/*  692 */     return PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVeName() {
/*  701 */     return "CCNWTAASHWANN";
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
/*      */   private Vector getPriceInfo(Vector<WTAASCCNHWENTITY> paramVector) throws SQLException, MiddlewareException {
/*  715 */     Vector<WTAASCCNHWENTITY> vector = new Vector();
/*  716 */     ResultSet resultSet = null;
/*  717 */     PreparedStatement preparedStatement = null;
/*      */     
/*      */     try {
/*  720 */       Connection connection = this.m_db.getODSConnection();
/*      */       
/*  722 */       byte b = 0; while (true) { if (b < paramVector.size())
/*  723 */         { WTAASCCNHWENTITY wTAASCCNHWENTITY = paramVector.elementAt(b);
/*  724 */           String str1 = null;
/*  725 */           HashMap<Object, Object> hashMap = new HashMap<>();
/*  726 */           if ("MODEL".equals(wTAASCCNHWENTITY.getType())) {
/*  727 */             str1 = queryForModel(wTAASCCNHWENTITY);
/*  728 */           } else if ("FEATURE".equals(wTAASCCNHWENTITY.getType())) {
/*  729 */             str1 = queryForFeature(wTAASCCNHWENTITY);
/*  730 */           } else if ("MODELCONVERT".equals(wTAASCCNHWENTITY.getType())) {
/*  731 */             str1 = queryForModelconvert(wTAASCCNHWENTITY);
/*      */           } else {
/*  733 */             addDebug("can not handle entitytype " + wTAASCCNHWENTITY.getType());
/*      */             
/*      */             b++;
/*      */           } 
/*  737 */           preparedStatement = connection.prepareStatement(str1);
/*  738 */           resultSet = preparedStatement.executeQuery();
/*      */           
/*  740 */           String str2 = null;
/*  741 */           String str3 = null;
/*      */           
/*  743 */           byte b1 = 0;
/*  744 */           while (resultSet.next()) {
/*  745 */             str2 = resultSet.getString(1);
/*  746 */             str3 = convertPriceType(resultSet.getString(2));
/*  747 */             if (str3.equals(null)) {
/*  748 */               addDebug("skip unsupport price_type: " + resultSet.getString(2));
/*      */             } else {
/*  750 */               addDebug("price info: price_value " + str2 + " price_type " + str3);
/*  751 */               hashMap.put(doubleFormat(str2), str3);
/*      */             } 
/*  753 */             b1++;
/*      */           } 
/*  755 */           if (b1 == 0) {
/*  756 */             str2 = "0.00";
/*  757 */             str3 = "  5";
/*  758 */             addDebug("price info: price_value " + str2 + " price_type " + str3);
/*  759 */             hashMap.put(str2, str3);
/*      */           } 
/*      */           
/*  762 */           wTAASCCNHWENTITY.setPrice(hashMap);
/*  763 */           vector.add(wTAASCCNHWENTITY); } else { break; }
/*      */          b++; }
/*  765 */        return vector;
/*      */     } finally {
/*      */       
/*  768 */       if (preparedStatement != null) {
/*      */         try {
/*  770 */           preparedStatement.close();
/*  771 */         } catch (SQLException sQLException) {
/*  772 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForModelconvert(WTAASCCNHWENTITY paramWTAASCCNHWENTITY) {
/*  783 */     StringBuffer stringBuffer = new StringBuffer();
/*  784 */     String str1 = null;
/*  785 */     String str2 = null;
/*  786 */     if (paramWTAASCCNHWENTITY.getMachtype().equals(paramWTAASCCNHWENTITY.getToMachtype())) {
/*  787 */       str1 = "MUP";
/*  788 */       str2 = paramWTAASCCNHWENTITY.getModel() + "_" + paramWTAASCCNHWENTITY.getToModel();
/*      */     } else {
/*  790 */       str1 = "TMU";
/*  791 */       str2 = paramWTAASCCNHWENTITY.getModel() + "_" + paramWTAASCCNHWENTITY.getToMachtype() + "_" + paramWTAASCCNHWENTITY.getToModel();
/*      */     } 
/*  793 */     addDebug("Get MODELCONVERT price info---------------");
/*  794 */     stringBuffer.append("select  price_value, price_type from price.price where action='I' and currency ='CAD' and offering_type='MODELCONVERT' and machtypeatr='");
/*  795 */     stringBuffer.append(paramWTAASCCNHWENTITY.getToMachtype());
/*  796 */     stringBuffer.append("' and modelatr='");
/*  797 */     stringBuffer.append(paramWTAASCCNHWENTITY.getToModel());
/*  798 */     stringBuffer.append("' and from_machtypeatr='");
/*  799 */     stringBuffer.append(paramWTAASCCNHWENTITY.getMachtype());
/*  800 */     stringBuffer.append("' and from_modelatr='");
/*  801 */     stringBuffer.append(paramWTAASCCNHWENTITY.getModel());
/*  802 */     stringBuffer.append("' and start_date <= '");
/*  803 */     stringBuffer.append(this.annDate);
/*  804 */     stringBuffer.append("' and end_date >'");
/*  805 */     stringBuffer.append(this.annDate);
/*  806 */     stringBuffer.append("' and price_point_type='");
/*  807 */     stringBuffer.append(str1);
/*  808 */     stringBuffer.append("' and offering = '");
/*  809 */     stringBuffer.append(paramWTAASCCNHWENTITY.getMachtype());
/*  810 */     stringBuffer.append("' and PRICE_POINT_VALUE = '");
/*  811 */     stringBuffer.append(str2);
/*  812 */     stringBuffer.append("' and country = 'CA' and onshore='true' with ur");
/*      */     
/*  814 */     addDebug("SQL:" + stringBuffer);
/*  815 */     addDebug("MODELCONVERT info: from_machtypeatr:" + paramWTAASCCNHWENTITY.getMachtype() + ",from_model:" + paramWTAASCCNHWENTITY
/*  816 */         .getModel() + ",to_machtypeatr:" + paramWTAASCCNHWENTITY.getToMachtype() + ",to_modelatr:" + paramWTAASCCNHWENTITY
/*  817 */         .getToModel());
/*  818 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForFeature(WTAASCCNHWENTITY paramWTAASCCNHWENTITY) {
/*  827 */     StringBuffer stringBuffer = new StringBuffer();
/*  828 */     addDebug("Get FEATURE price info---------------");
/*  829 */     stringBuffer.append("select  price_value, price_type from price.price where action='I' and currency ='CAD' and offering_type='FEATURE' and machtypeatr='");
/*  830 */     stringBuffer.append(paramWTAASCCNHWENTITY.getMachtype());
/*  831 */     stringBuffer.append("' and featurecode='");
/*  832 */     stringBuffer.append(paramWTAASCCNHWENTITY.getFeatureCode());
/*  833 */     stringBuffer.append("' and start_date <= '");
/*  834 */     stringBuffer.append(this.annDate);
/*  835 */     stringBuffer.append("' and end_date >'");
/*  836 */     stringBuffer.append(this.annDate);
/*  837 */     stringBuffer.append("' and offering = '");
/*  838 */     stringBuffer.append(paramWTAASCCNHWENTITY.getMachtype());
/*  839 */     stringBuffer.append("' and PRICE_POINT_VALUE = '");
/*  840 */     stringBuffer.append(paramWTAASCCNHWENTITY.getFeatureCode());
/*  841 */     stringBuffer.append("' and price_point_type in ('FEA','RPQ')");
/*  842 */     stringBuffer.append(" and country = 'CA' and onshore='true' with ur");
/*      */     
/*  844 */     addDebug("SQL:" + stringBuffer);
/*  845 */     addDebug("FEATURE info: machtypeatr:" + paramWTAASCCNHWENTITY.getMachtype() + ",featurecode:" + paramWTAASCCNHWENTITY.getFeatureCode());
/*  846 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForModel(WTAASCCNHWENTITY paramWTAASCCNHWENTITY) {
/*  855 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  857 */     addDebug("Get MODEL price info---------------");
/*  858 */     stringBuffer.append("select  price_value, price_type from price.price where action='I' and currency ='CAD' and offering_type='MODEL' and price_point_type='MOD' and machtypeatr='");
/*  859 */     stringBuffer.append(paramWTAASCCNHWENTITY.getMachtype());
/*  860 */     stringBuffer.append("' and modelatr= '");
/*  861 */     stringBuffer.append(paramWTAASCCNHWENTITY.getModel());
/*  862 */     stringBuffer.append("' and start_date <= '");
/*  863 */     stringBuffer.append(this.annDate);
/*  864 */     stringBuffer.append("' and end_date > '");
/*  865 */     stringBuffer.append(this.annDate);
/*  866 */     stringBuffer.append("' and offering = '");
/*  867 */     stringBuffer.append(paramWTAASCCNHWENTITY.getMachtype());
/*  868 */     stringBuffer.append("' and PRICE_POINT_VALUE = '");
/*  869 */     stringBuffer.append(paramWTAASCCNHWENTITY.getModel());
/*  870 */     stringBuffer.append("' and country = 'CA' and onshore='true' with ur");
/*      */     
/*  872 */     addDebug("SQL:" + stringBuffer);
/*  873 */     addDebug("MODEL info: machtypeatr:" + paramWTAASCCNHWENTITY.getMachtype() + ",modelatr:" + paramWTAASCCNHWENTITY.getModel());
/*  874 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String convertPriceType(String paramString) {
/*  885 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  886 */     hashMap.put("PUR", "  5");
/*  887 */     hashMap.put("MES", "  5");
/*  888 */     hashMap.put("WU3", "NRR");
/*  889 */     hashMap.put("WU4", "OR5");
/*  890 */     hashMap.put("WU1", "OR7");
/*  891 */     hashMap.put("EMC", "0BC");
/*  892 */     hashMap.put("EMG", "0HC");
/*  893 */     hashMap.put("EMF", "0MC");
/*  894 */     hashMap.put("EMD", "NOR");
/*  895 */     hashMap.put("EMH", "0RD");
/*  896 */     hashMap.put("EMS", "P01");
/*  897 */     hashMap.put("EMM", "P02");
/*      */     
/*  899 */     if (hashMap.containsKey(paramString)) {
/*  900 */       addDebug("convert WWPRT price type " + paramString + " to WTAAS price type " + hashMap.get(paramString));
/*  901 */       return (String)hashMap.get(paramString);
/*      */     } 
/*  903 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  908 */     return "WATTSSTATUSABR";
/*      */   }
/*      */   
/*      */   public String getABRVersion() {
/*  912 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/*  919 */     addOutput(paramString);
/*  920 */     setReturnCode(-1);
/*      */   }
/*      */   
/*      */   protected void addDebug(String paramString) {
/*  924 */     if (3 <= DEBUG_LVL) {
/*  925 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/*      */   protected void addDebugComment(int paramInt, String paramString) {
/*  937 */     if (paramInt <= DEBUG_LVL) {
/*  938 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/*      */   protected EntityList getEntityList(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  955 */     return this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, paramString), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*      */             
/*  957 */             .getEntityType(), paramEntityItem.getEntityID()) });
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
/*      */   protected EntityItem getEntityItem(Profile paramProfile, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  975 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*      */             
/*  977 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*  978 */     return entityList.getParentEntityGroup().getEntityItem(0);
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
/*      */   protected EntityItem getEntityItem(Profile paramProfile, String paramString, int paramInt) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  996 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramString, paramInt) });
/*      */ 
/*      */     
/*  999 */     return entityList.getParentEntityGroup().getEntityItem(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 1006 */     return this.m_db;
/*      */   }
/*      */   
/*      */   protected String getABRTime() {
/* 1010 */     return String.valueOf(System.currentTimeMillis());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 1017 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String buildAbrHeader() {
/* 1027 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1034 */     MessageFormat messageFormat = new MessageFormat(str);
/* 1035 */     this.args[0] = this.m_prof.getOPName();
/* 1036 */     this.args[1] = this.m_prof.getRoleDescription();
/* 1037 */     this.args[2] = this.m_prof.getWGName();
/* 1038 */     this.args[3] = this.t2DTS;
/* 1039 */     this.args[4] = "WATTS CCN HW ABR feed trigger<br/>" + this.xmlgenSb.toString();
/* 1040 */     this.args[5] = getABRVersion();
/* 1041 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1051 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1054 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1055 */     EANList eANList = entityGroup.getMetaAttribute();
/*      */     
/* 1057 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1058 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1059 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute
/* 1060 */             .getAttributeCode(), ", ", "", false));
/* 1061 */       stringBuffer.append(" ");
/*      */     } 
/* 1063 */     return stringBuffer.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WTAASCCNHWABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */