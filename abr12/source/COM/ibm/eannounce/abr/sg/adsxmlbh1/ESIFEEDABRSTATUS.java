/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ 
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
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ 
/*      */ public class ESIFEEDABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*   46 */   private StringBuffer rptSb = new StringBuffer();
/*   47 */   private StringBuffer xmlgenSb = new StringBuffer();
/*   48 */   private StringBuffer userxmlSb = new StringBuffer();
/*   49 */   private String t2DTS = "&nbsp;";
/*   50 */   private Object[] args = (Object[])new String[10];
/*   51 */   private String uniqueKey = String.valueOf(System.currentTimeMillis());
/*      */   
/*   53 */   private static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("ESIFEEDABRSTATUS");
/*   54 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   55 */   protected static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*      */   protected static final String OLDEFFECTDATE = "2010-03-01";
/*      */   protected static final String STATUS_FINAL = "Final";
/*      */   protected static final String CHEAT = "@@";
/*      */   public static final String RPTPATH = "_rptpath";
/*   61 */   private HashMap allRecords = new HashMap<>();
/*   62 */   private String lineStr = "";
/*      */   
/*      */   private static final String INIPATH = "_inipath";
/*      */   
/*      */   private static final String FTPSCRPATH = "_script";
/*      */   
/*      */   private static final String TARGETFILENAME = "_targetfilename";
/*      */   private static final String LOGPATH = "_logpath";
/*      */   private static final String BACKUPPATH = "_backuppath";
/*      */   private static final int MAXSIZE = 100000;
/*   72 */   protected static final Set FCTYPE_SET = new HashSet(); static {
/*   73 */     FCTYPE_SET.add("RPQ-ILISTED");
/*   74 */     FCTYPE_SET.add("RPQ-PLISTED");
/*   75 */     FCTYPE_SET.add("RPQ-RLISTED");
/*      */   }
/*      */   
/*   78 */   protected static final String[][] country_convert_Array = new String[][] { { "1447", "624" }, { "1626", "838" }, { "1600", "822" }, { "1632", "846" }, { "1484", "678" }, { "1588", "806" }, { "1500", "702" }, { "1532", "756" }, { "1533", "758" }, { "1651", "866" }, { "1531", "754" }, { "1578", "788" }, { "1624", "864" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*   96 */     String str1 = "";
/*      */ 
/*      */     
/*      */     try {
/*  100 */       long l = System.currentTimeMillis();
/*      */       
/*  102 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*  105 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */       
/*  109 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*  110 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */       
/*  113 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  115 */       if (isCanRun()) {
/*  116 */         List<String> list = getMachtype();
/*  117 */         for (byte b = 0; b < list.size(); b++) {
/*  118 */           addDebug("Process MACHTYPE: " + list.get(b));
/*      */ 
/*      */           
/*  121 */           if (getReturnCode() == 0)
/*      */           {
/*  123 */             processThis(list.get(b));
/*      */           }
/*      */           
/*  126 */           Iterator<String> iterator = this.allRecords.keySet().iterator();
/*  127 */           while (iterator.hasNext()) {
/*  128 */             String str = iterator.next();
/*  129 */             StringBuffer stringBuffer = (StringBuffer)this.allRecords.get(str);
/*      */             
/*  131 */             if (stringBuffer.length() >= 100000) {
/*  132 */               String str6 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_rptpath", "/Dgq/ESI/");
/*  133 */               if (!str6.endsWith("/")) {
/*  134 */                 str6 = str6 + "/";
/*      */               }
/*  136 */               String str7 = getNow();
/*  137 */               str7 = str7.replace(' ', '_');
/*  138 */               String str8 = str6 + "MSM" + str + str7 + ".FULL";
/*      */               try {
/*  140 */                 generateFile(str8, stringBuffer);
/*  141 */                 addDebug("Write to file " + str8);
/*  142 */                 stringBuffer.setLength(0);
/*  143 */               } catch (Exception exception) {
/*      */                 
/*  145 */                 exception.printStackTrace();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  150 */           addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l) + " for abr:" + getDescription());
/*      */         } 
/*      */         
/*  153 */         Vector<String> vector = genOutputFile();
/*      */         
/*  155 */         if (vector != null) {
/*  156 */           for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  157 */             String str = vector.get(b1);
/*  158 */             exeFtpShell(str);
/*      */           } 
/*      */         }
/*      */       } else {
/*      */         
/*  163 */         this.userxmlSb.append("Can not run at same time since one already run");
/*      */       } 
/*      */ 
/*      */       
/*  167 */       str1 = getNavigationName(entityItem);
/*  168 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*  169 */     } catch (Throwable throwable) {
/*  170 */       StringWriter stringWriter = new StringWriter();
/*  171 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  172 */       String str7 = "<pre>{0}</pre>";
/*  173 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  174 */       setReturnCode(-3);
/*  175 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  177 */       this.args[0] = throwable.getMessage();
/*  178 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  179 */       messageFormat1 = new MessageFormat(str7);
/*  180 */       this.args[0] = stringWriter.getBuffer().toString();
/*  181 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  182 */       logError("Exception: " + throwable.getMessage());
/*  183 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*  185 */       if (this.t2DTS.equals("&nbsp;")) {
/*  186 */         this.t2DTS = getNow();
/*      */       }
/*  188 */       setDGTitle(str1);
/*  189 */       setDGRptName(getShortClassName(getClass()));
/*  190 */       setDGRptClass(getABRCode());
/*      */       
/*  192 */       if (!isReadOnly()) {
/*  193 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  199 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  212 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */     
/*  216 */     MessageFormat messageFormat = new MessageFormat(str2);
/*  217 */     this.args[0] = getShortClassName(getClass());
/*  218 */     this.args[1] = str1;
/*  219 */     String str3 = messageFormat.format(this.args);
/*  220 */     String str4 = buildAitAbrHeader();
/*      */     
/*  222 */     String str5 = str3 + str4 + "<pre><br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  223 */     this.rptSb.insert(0, str5);
/*  224 */     println(this.rptSb.toString());
/*  225 */     printDGSubmitString();
/*  226 */     println(EACustom.getTOUDiv());
/*  227 */     buildReportFooter();
/*      */ 
/*      */     
/*  230 */     this.m_elist.dereference();
/*  231 */     this.m_elist = null;
/*  232 */     this.args = null;
/*  233 */     messageFormat = null;
/*  234 */     this.userxmlSb = null;
/*  235 */     this.rptSb = null;
/*  236 */     this.xmlgenSb = null;
/*      */   }
/*      */   
/*      */   private boolean isCanRun() throws SQLException, MiddlewareException {
/*  240 */     ResultSet resultSet = null;
/*  241 */     PreparedStatement preparedStatement = null;
/*  242 */     boolean bool = false;
/*      */     
/*      */     try {
/*  245 */       Connection connection = this.m_db.getPDHConnection();
/*      */       
/*  247 */       String str = "select count(*) from opicm.flag where entitytype='ESIREPORT' and attributecode='ESIFEEDABRSTATUS' and attributevalue='0050' and valto>current timestamp and effto>current timestamp with ur";
/*      */       
/*  249 */       preparedStatement = connection.prepareStatement(str);
/*  250 */       resultSet = preparedStatement.executeQuery();
/*      */       
/*  252 */       while (resultSet.next()) {
/*      */         
/*  254 */         int i = resultSet.getInt(1);
/*      */         
/*  256 */         if (i == 1) {
/*  257 */           bool = true;
/*      */         }
/*      */       } 
/*      */     } finally {
/*  261 */       if (preparedStatement != null) {
/*      */         try {
/*  263 */           preparedStatement.close();
/*  264 */         } catch (SQLException sQLException) {
/*  265 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*  269 */     return bool;
/*      */   }
/*      */   
/*      */   public double showMemory() {
/*  273 */     System.gc();
/*  274 */     Runtime runtime = Runtime.getRuntime();
/*      */     
/*  276 */     NumberFormat numberFormat = NumberFormat.getPercentInstance();
/*  277 */     numberFormat.setMinimumFractionDigits(0);
/*  278 */     double d = runtime.freeMemory() * 1.0D / runtime.totalMemory();
/*  279 */     addDebug("Free percent: " + numberFormat.format(d));
/*  280 */     return d;
/*      */   }
/*      */ 
/*      */   
/*      */   public void processThis(String paramString) throws MiddlewareException, SQLException, InterruptedException {
/*  285 */     if (paramString != null && !paramString.equals(null)) {
/*  286 */       Iterator<Map.Entry> iterator = getDataList(paramString).entrySet().iterator();
/*  287 */       while (iterator.hasNext()) {
/*  288 */         Map.Entry entry = iterator.next();
/*  289 */         String str1 = (String)entry.getKey();
/*  290 */         String str2 = (String)entry.getValue();
/*      */         
/*  292 */         if (str2.equals("M")) {
/*  293 */           Vector vector = extractMODCtry(str1);
/*  294 */           for (byte b = 0; b < country_convert_Array.length; b++) {
/*  295 */             if (vector.contains(country_convert_Array[b][0]))
/*  296 */               genRecords(str1, country_convert_Array[b][1], "M"); 
/*      */           }  continue;
/*      */         } 
/*  299 */         if (str2.equals("SF")) {
/*  300 */           Vector vector = extractSTMFCtry(str1);
/*  301 */           for (byte b = 0; b < country_convert_Array.length; b++) {
/*  302 */             if (vector.contains(country_convert_Array[b][0]))
/*  303 */               genRecords(str1, country_convert_Array[b][1], "SF"); 
/*      */           }  continue;
/*      */         } 
/*  306 */         if (str2.equals("R")) {
/*  307 */           Vector vector = extractRPQCtry(str1);
/*  308 */           for (byte b = 0; b < country_convert_Array.length; b++) {
/*  309 */             if (vector.contains(country_convert_Array[b][0]))
/*  310 */               genRecords(str1, country_convert_Array[b][1], "R"); 
/*      */           }  continue;
/*      */         } 
/*  313 */         if (str2.equals("HF")) {
/*  314 */           Vector vector = extractTMFCtry(str1);
/*  315 */           for (byte b = 0; b < country_convert_Array.length; b++) {
/*  316 */             if (vector.contains(country_convert_Array[b][0])) {
/*  317 */               genRecords(str1, country_convert_Array[b][1], "F");
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*  322 */       addDebug("Processing machtype: " + paramString);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector extractTMFCtry(String paramString) throws MiddlewareException, SQLException {
/*  328 */     Vector vector = getTMFAvailCty(paramString);
/*  329 */     if (vector.size() == 0) {
/*  330 */       return extractRPQCtry(paramString);
/*      */     }
/*      */ 
/*      */     
/*  334 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector extractRPQCtry(String paramString) throws MiddlewareException, SQLException {
/*  340 */     Vector vector1 = new Vector();
/*      */     
/*  342 */     Vector vector2 = getTMFLMODAvailCty(paramString);
/*  343 */     if (vector2.size() != 0) {
/*      */ 
/*      */ 
/*      */       
/*  347 */       Vector vector = getTMFLFEACty(paramString);
/*  348 */       vector1 = genCtryList(vector, vector2);
/*      */       
/*  350 */       return vector1;
/*      */     } 
/*      */     
/*  353 */     return getTMFLFEACty(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector genCtryList(Vector paramVector1, Vector<String> paramVector2) {
/*  359 */     Vector<String> vector = new Vector();
/*  360 */     for (byte b = 0; b < paramVector2.size(); b++) {
/*  361 */       String str = paramVector2.get(b);
/*  362 */       if (str != null && !str.equals(null) && 
/*  363 */         paramVector1.contains(str)) {
/*  364 */         vector.add(str);
/*      */       }
/*      */     } 
/*      */     
/*  368 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector extractSTMFCtry(String paramString) throws MiddlewareException, SQLException {
/*  374 */     Vector vector = getSTMFAvailCty(paramString);
/*  375 */     if (vector.size() == 0) {
/*      */       
/*  377 */       Vector vector1 = getSTMFLMODAvailCty(paramString);
/*  378 */       if (vector1.size() != 0)
/*      */       {
/*  380 */         return vector1;
/*      */       }
/*      */       
/*  383 */       return genWorldWideCtryList();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  388 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector extractMODCtry(String paramString) throws MiddlewareException, SQLException {
/*  395 */     Vector vector = getMODAvailCty(paramString);
/*  396 */     if (vector.size() == 0)
/*      */     {
/*  398 */       return genWorldWideCtryList();
/*      */     }
/*      */ 
/*      */     
/*  402 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private HashMap getDataList(String paramString) throws MiddlewareException, SQLException {
/*  409 */     ResultSet resultSet = null;
/*  410 */     PreparedStatement preparedStatement = null;
/*  411 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */     
/*      */     try {
/*  414 */       Connection connection = this.m_db.getODS2Connection();
/*      */       
/*  416 */       String str = "select distinct M.entityid as MODID,  case when M.COFCAT='Hardware' then P.entityid end as TMFID, F.FCTYPE,  case when M.COFCAT='Software' then S.entityid end as STMFID  from opicm.machtype T  join opicm.model M on M.machtypeatr=T.machtypeatr and M.nlsid=1 and M.STATUS='Final' and M.COFCAT in ('Hardware','Software')  left join opicm.relator R1 on R1.entity2id=M.entityid and R1.entitytype='PRODSTRUCT'  left join opicm.relator R2 on R2.entity2id=M.entityid and R2.entitytype='SWPRODSTRUCT'  left join opicm.prodstruct P on P.entityid=R1.entityid and P.nlsid=1 and P.STATUS='Final'  left join opicm.feature F on F.entityid=R1.entity1id and F.nlsid=1 and F.STATUS='Final'  left join opicm.swprodstruct S on S.entityid=R2.entityid and S.nlsid=1 and S.STATUS='Final'  where T.machtypeatr='" + paramString + "' with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  428 */       preparedStatement = connection.prepareStatement(str);
/*  429 */       resultSet = preparedStatement.executeQuery();
/*      */ 
/*      */ 
/*      */       
/*  433 */       while (resultSet.next()) {
/*      */         
/*  435 */         if (resultSet.getString(1) != null && !resultSet.getString(1).equals(null)) {
/*  436 */           String str1 = resultSet.getString(1);
/*  437 */           hashMap.put(str1, "M");
/*      */         } 
/*  439 */         if (resultSet.getString(2) != null && !resultSet.getString(2).equals(null) && resultSet.getString(3) != null && !resultSet.getString(3).equals(null)) {
/*  440 */           String str1 = resultSet.getString(2);
/*  441 */           String str2 = resultSet.getString(3);
/*  442 */           if (FCTYPE_SET.contains(str2)) {
/*  443 */             hashMap.put(str1, "R"); continue;
/*      */           } 
/*  445 */           hashMap.put(str1, "HF"); continue;
/*      */         } 
/*  447 */         if (resultSet.getString(4) != null && !resultSet.getString(4).equals(null)) {
/*  448 */           String str1 = resultSet.getString(4);
/*  449 */           hashMap.put(str1, "SF");
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  453 */       if (preparedStatement != null) {
/*      */         try {
/*  455 */           preparedStatement.close();
/*  456 */         } catch (SQLException sQLException) {
/*  457 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*  461 */     return hashMap;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector getTMFAvailCty(String paramString) throws MiddlewareException, SQLException {
/*  466 */     ResultSet resultSet = null;
/*  467 */     PreparedStatement preparedStatement = null;
/*  468 */     Vector<String> vector = new Vector();
/*      */     
/*      */     try {
/*  471 */       Connection connection = this.m_db.getODS2Connection();
/*      */       
/*  473 */       String str = "select  A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country from opicm.prodstruct P join opicm.relator R1 on R1.entity1type = P.entitytype and R1.entity1id = P.entityid join opicm.AVAIL A1 on R1.entity2type = A1.entitytype and R1.entity2id = A1.entityid join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid where P.entityid = " + paramString + " and P.nlsid = 1 and P.STATUS = 'Final' and R1.entitytype = 'OOFAVAIL' and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability'  and A1.STATUS in  ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST' group by a1.entityid with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  486 */       preparedStatement = connection.prepareStatement(str);
/*  487 */       resultSet = preparedStatement.executeQuery();
/*      */ 
/*      */ 
/*      */       
/*  491 */       while (resultSet.next()) {
/*  492 */         String str1 = resultSet.getString(2);
/*  493 */         if (str1 != null && !str1.equals(null)) {
/*  494 */           str1.replaceAll("<A>", "").replaceAll("</A>", "");
/*  495 */           String[] arrayOfString = splitStr(str1, ",");
/*  496 */           for (byte b = 0; b < arrayOfString.length; b++) {
/*  497 */             if (!vector.contains(arrayOfString[b])) {
/*  498 */               vector.add(arrayOfString[b]);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  505 */       if (preparedStatement != null) {
/*      */         try {
/*  507 */           preparedStatement.close();
/*  508 */         } catch (SQLException sQLException) {
/*  509 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  514 */     addDebug("TMF linked avail country: " + vector);
/*      */     
/*  516 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector getTMFLMODAvailCty(String paramString) throws MiddlewareException, SQLException {
/*  521 */     ResultSet resultSet = null;
/*  522 */     PreparedStatement preparedStatement = null;
/*  523 */     Vector<String> vector = new Vector();
/*      */     
/*      */     try {
/*  526 */       Connection connection = this.m_db.getODS2Connection();
/*      */       
/*  528 */       String str = "select A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country from opicm.prodstruct P join opicm.relator R1 on R1.entitytype=P.entitytype and R1.entityid=P.entityid join opicm.model m on m.entitytype=R1.entity2type and m.entityid=R1.entity2id join opicm.relator R2 on R2.entitytype='MODELAVAIL' and R2.entity1id=m.entityid join opicm.AVAIL A1 on R2.entity2type = A1.entitytype and R2.entity2id = A1.entityid join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid where p.entityid= " + paramString + " and P.nlsid = 1 and P.STATUS = 'Final' and m.nlsid = 1 and m.STATUS = 'Final' and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability' and A1.STATUS in ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST' group by a1.entityid with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  544 */       preparedStatement = connection.prepareStatement(str);
/*  545 */       resultSet = preparedStatement.executeQuery();
/*      */ 
/*      */ 
/*      */       
/*  549 */       while (resultSet.next()) {
/*  550 */         String str1 = resultSet.getString(2);
/*  551 */         if (str1 != null && !str1.equals(null)) {
/*  552 */           str1.replaceAll("<A>", "").replaceAll("</A>", "");
/*  553 */           String[] arrayOfString = splitStr(str1, ",");
/*  554 */           for (byte b = 0; b < arrayOfString.length; b++) {
/*  555 */             if (!vector.contains(arrayOfString[b])) {
/*  556 */               vector.add(arrayOfString[b]);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  563 */       if (preparedStatement != null) {
/*      */         try {
/*  565 */           preparedStatement.close();
/*  566 */         } catch (SQLException sQLException) {
/*  567 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  572 */     addDebug("TMF linked Model's avail country: " + vector);
/*      */     
/*  574 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector getTMFLFEACty(String paramString) throws MiddlewareException, SQLException {
/*  579 */     ResultSet resultSet = null;
/*  580 */     PreparedStatement preparedStatement = null;
/*  581 */     Vector<String> vector = new Vector();
/*      */     
/*      */     try {
/*  584 */       Connection connection = this.m_db.getODS2Connection();
/*      */       
/*  586 */       String str = "select F1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country from opicm.prodstruct P join opicm.relator R1 on R1.entitytype=P.entitytype and R1.entityid=P.entityid join opicm.feature F on F.entitytype=R1.entity1type and F.entityid=R1.entity1id and F.nlsid=1 join opicm.flag F1 on  F1.entitytype = F.entitytype and F1.entityid = F.entityid and F1.attributecode = 'COUNTRYLIST'  where p.entityid= " + paramString + " and P.nlsid = 1 and P.STATUS = 'Final' and F.nlsid=1 and F.STATUS='Final' group by F1.entityid with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  593 */       preparedStatement = connection.prepareStatement(str);
/*  594 */       resultSet = preparedStatement.executeQuery();
/*      */ 
/*      */ 
/*      */       
/*  598 */       while (resultSet.next()) {
/*  599 */         String str1 = resultSet.getString(2);
/*  600 */         if (str1 != null && !str1.equals(null)) {
/*  601 */           str1.replaceAll("<A>", "").replaceAll("</A>", "");
/*  602 */           String[] arrayOfString = splitStr(str1, ",");
/*  603 */           for (byte b = 0; b < arrayOfString.length; b++) {
/*  604 */             if (!vector.contains(arrayOfString[b])) {
/*  605 */               vector.add(arrayOfString[b]);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  612 */       if (preparedStatement != null) {
/*      */         try {
/*  614 */           preparedStatement.close();
/*  615 */         } catch (SQLException sQLException) {
/*  616 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  621 */     addDebug("TMF linked Feature's country: " + vector);
/*      */     
/*  623 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector getSTMFAvailCty(String paramString) throws MiddlewareException, SQLException {
/*  628 */     ResultSet resultSet = null;
/*  629 */     PreparedStatement preparedStatement = null;
/*  630 */     Vector<String> vector = new Vector();
/*      */     
/*      */     try {
/*  633 */       Connection connection = this.m_db.getODS2Connection();
/*      */       
/*  635 */       String str = "select A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country from opicm.swprodstruct P join opicm.relator R1 on R1.entity1type = P.entitytype and R1.entity1id = P.entityid join opicm.AVAIL A1 on R1.entity2type = A1.entitytype and R1.entity2id = A1.entityid join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid where P.entityid = " + paramString + " and P.nlsid = 1 and P.STATUS = 'Final' and R1.entitytype = 'SWPRODSTRUCTAVAIL' and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability' and A1.STATUS in ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST' group by a1.entityid with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  648 */       preparedStatement = connection.prepareStatement(str);
/*  649 */       resultSet = preparedStatement.executeQuery();
/*      */ 
/*      */ 
/*      */       
/*  653 */       while (resultSet.next()) {
/*  654 */         String str1 = resultSet.getString(2);
/*  655 */         if (str1 != null && !str1.equals(null)) {
/*  656 */           str1.replaceAll("<A>", "").replaceAll("</A>", "");
/*  657 */           String[] arrayOfString = splitStr(str1, ",");
/*  658 */           for (byte b = 0; b < arrayOfString.length; b++) {
/*  659 */             if (!vector.contains(arrayOfString[b])) {
/*  660 */               vector.add(arrayOfString[b]);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  667 */       if (preparedStatement != null) {
/*      */         try {
/*  669 */           preparedStatement.close();
/*  670 */         } catch (SQLException sQLException) {
/*  671 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  676 */     addDebug("SWTMF linked avail country: " + vector);
/*  677 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector getSTMFLMODAvailCty(String paramString) throws MiddlewareException, SQLException {
/*  682 */     ResultSet resultSet = null;
/*  683 */     PreparedStatement preparedStatement = null;
/*  684 */     Vector<String> vector = new Vector();
/*      */     
/*      */     try {
/*  687 */       Connection connection = this.m_db.getODS2Connection();
/*      */       
/*  689 */       String str = "select A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country from opicm.swprodstruct P join opicm.relator R1 on R1.entitytype=P.entitytype and R1.entityid=P.entityid join opicm.model m on m.entitytype=R1.entity2type and m.entityid=R1.entity2id join opicm.relator R2 on R2.entitytype='MODELAVAIL' and R2.entity1id=m.entityid join opicm.AVAIL A1 on R2.entity2type = A1.entitytype and R2.entity2id = A1.entityid join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid where p.entityid= " + paramString + " and P.nlsid = 1 and P.STATUS = 'Final' and m.nlsid = 1 and m.STATUS = 'Final' and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability' and A1.STATUS in ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST' group by a1.entityid with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  705 */       preparedStatement = connection.prepareStatement(str);
/*  706 */       resultSet = preparedStatement.executeQuery();
/*      */ 
/*      */       
/*  709 */       while (resultSet.next()) {
/*  710 */         String str1 = resultSet.getString(2);
/*  711 */         if (str1 != null && !str1.equals(null)) {
/*  712 */           str1.replaceAll("<A>", "").replaceAll("</A>", "");
/*  713 */           String[] arrayOfString = splitStr(str1, ",");
/*  714 */           for (byte b = 0; b < arrayOfString.length; b++) {
/*  715 */             if (!vector.contains(arrayOfString[b])) {
/*  716 */               vector.add(arrayOfString[b]);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  723 */       if (preparedStatement != null) {
/*      */         try {
/*  725 */           preparedStatement.close();
/*  726 */         } catch (SQLException sQLException) {
/*  727 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  732 */     addDebug("SWTMF linked Model's avail country: " + vector);
/*  733 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector getMODAvailCty(String paramString) throws MiddlewareException, SQLException {
/*  738 */     ResultSet resultSet = null;
/*  739 */     PreparedStatement preparedStatement = null;
/*  740 */     Vector<String> vector = new Vector();
/*      */     
/*      */     try {
/*  743 */       Connection connection = this.m_db.getODS2Connection();
/*      */       
/*  745 */       String str = "select A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country from opicm.model m join opicm.relator R2 on R2.entitytype='MODELAVAIL' and R2.entity1id=m.entityid join opicm.AVAIL A1 on R2.entity2type = A1.entitytype and R2.entity2id = A1.entityid join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid where m.entityid= " + paramString + " and m.nlsid = 1 and m.STATUS = 'Final' and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability' and A1.STATUS in ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST' group by a1.entityid with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  754 */       preparedStatement = connection.prepareStatement(str);
/*  755 */       resultSet = preparedStatement.executeQuery();
/*      */ 
/*      */       
/*  758 */       while (resultSet.next()) {
/*  759 */         String str1 = resultSet.getString(2);
/*  760 */         if (str1 != null && !str1.equals(null)) {
/*  761 */           str1.replaceAll("<A>", "").replaceAll("</A>", "");
/*  762 */           String[] arrayOfString = splitStr(str1, ",");
/*  763 */           for (byte b = 0; b < arrayOfString.length; b++) {
/*  764 */             if (!vector.contains(arrayOfString[b])) {
/*  765 */               vector.add(arrayOfString[b]);
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  773 */       if (preparedStatement != null) {
/*      */         try {
/*  775 */           preparedStatement.close();
/*  776 */         } catch (SQLException sQLException) {
/*  777 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  782 */     addDebug("Model's avail country: " + vector);
/*  783 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private List getMachtype() throws MiddlewareException, SQLException {
/*  788 */     ResultSet resultSet = null;
/*  789 */     PreparedStatement preparedStatement = null;
/*  790 */     ArrayList<String> arrayList = new ArrayList();
/*      */     
/*      */     try {
/*  793 */       Connection connection = this.m_db.getPDHConnection();
/*      */       
/*  795 */       String str = "select attributevalue from opicm.flag where entitytype='MACHTYPE' and attributecode='MACHTYPEATR' and valto>current timestamp and effto>current timestamp order by attributevalue with ur";
/*      */ 
/*      */       
/*  798 */       preparedStatement = connection.prepareStatement(str);
/*  799 */       resultSet = preparedStatement.executeQuery();
/*      */       
/*  801 */       while (resultSet.next()) {
/*      */         
/*  803 */         String str1 = resultSet.getString(1);
/*  804 */         arrayList.add(str1.trim());
/*      */       } 
/*      */     } finally {
/*  807 */       if (preparedStatement != null) {
/*      */         try {
/*  809 */           preparedStatement.close();
/*  810 */         } catch (SQLException sQLException) {
/*  811 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  816 */     addDebug("all machtype: " + arrayList.toString());
/*  817 */     return arrayList;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector genOutputFile() {
/*  822 */     if (this.allRecords.size() == 0) {
/*  823 */       this.userxmlSb.append("File generate failed, for there is no qualified data related");
/*  824 */       return null;
/*      */     } 
/*      */     
/*      */     try {
/*  828 */       Iterator<Map.Entry> iterator = this.allRecords.entrySet().iterator();
/*  829 */       Vector<String> vector = new Vector(1);
/*  830 */       while (iterator.hasNext()) {
/*  831 */         Map.Entry entry = iterator.next();
/*  832 */         StringBuffer stringBuffer = (StringBuffer)entry.getValue();
/*      */         
/*  834 */         String str1 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_rptpath", "/Dgq/ESI/");
/*  835 */         if (!str1.endsWith("/")) {
/*  836 */           str1 = str1 + "/";
/*      */         }
/*  838 */         String str2 = getNow();
/*  839 */         str2 = str2.replace(' ', '_');
/*  840 */         String str3 = str1 + "MSM" + entry.getKey() + str2 + ".FULL";
/*  841 */         addDebug("filename:" + str3);
/*  842 */         boolean bool1 = generateFile(str3, stringBuffer);
/*  843 */         boolean bool2 = generatedummyRecords(str3, entry.getKey().toString());
/*  844 */         if (bool1 && bool2) {
/*  845 */           vector.add(str3);
/*      */         }
/*      */       } 
/*      */       
/*  849 */       return vector;
/*      */     }
/*  851 */     catch (Exception exception) {
/*  852 */       exception.printStackTrace();
/*  853 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean generatedummyRecords(String paramString1, String paramString2) throws Exception {
/*  859 */     File file = new File(paramString1);
/*  860 */     if (file.exists()) {
/*  861 */       FileWriter fileWriter = new FileWriter(file, true);
/*  862 */       fileWriter.write(paramString2 + "SOFTADV   MSOFTWARE CONTRACT             2        ");
/*  863 */       fileWriter.close();
/*  864 */       return true;
/*      */     } 
/*  866 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean generateFile(String paramString, StringBuffer paramStringBuffer) throws Exception {
/*  871 */     File file = new File(paramString);
/*  872 */     if (file.exists()) {
/*  873 */       FileWriter fileWriter = new FileWriter(file, true);
/*  874 */       fileWriter.write(paramStringBuffer.toString());
/*  875 */       fileWriter.close();
/*  876 */       return true;
/*      */     } 
/*  878 */     file.createNewFile();
/*  879 */     FileOutputStream fileOutputStream = new FileOutputStream(file, true);
/*      */     try {
/*  881 */       fileOutputStream.write(paramStringBuffer.toString().getBytes("UTF-8"));
/*  882 */       this.userxmlSb.append(paramString + " generate success \n");
/*  883 */       return true;
/*  884 */     } catch (IOException iOException) {
/*  885 */       this.userxmlSb.append(iOException);
/*  886 */       throw new Exception("File create failed " + paramString);
/*      */     } finally {
/*  888 */       fileOutputStream.flush();
/*  889 */       fileOutputStream.close();
/*      */       try {
/*  891 */         if (fileOutputStream != null)
/*  892 */           fileOutputStream.close(); 
/*  893 */       } catch (Exception exception) {
/*  894 */         throw new Exception("File create failed " + paramString);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void genRecords(String paramString1, String paramString2, String paramString3) throws MiddlewareException, SQLException {
/*  902 */     String str1 = null;
/*  903 */     StringBuffer stringBuffer = new StringBuffer();
/*  904 */     String str2 = paramString2;
/*  905 */     String str3 = null;
/*  906 */     String str4 = null;
/*  907 */     String str5 = null;
/*  908 */     String str6 = null;
/*  909 */     String str7 = " ";
/*  910 */     String str8 = " ";
/*  911 */     String str9 = "      ";
/*      */     
/*  913 */     ResultSet resultSet = null;
/*  914 */     PreparedStatement preparedStatement = null;
/*      */     
/*      */     try {
/*  917 */       Connection connection = this.m_db.getODS2Connection();
/*      */       
/*  919 */       if ("F".equals(paramString3) || "R".equals(paramString3)) {
/*      */         
/*  921 */         str1 = "select M.machtypeatr, F.featurecode, F.invname from opicm.relator R join opicm.feature F on F.entitytype=R.entity1type and F.entityid=R.entity1id and F.nlsid=1 join opicm.model M on  M.entitytype = R.entity2type and M.entityid = R.entity2id and M.nlsid=1 where R.entityid=" + paramString1 + " and R.entitytype='PRODSTRUCT' and M.STATUS = 'Final' and F.STATUS='Final' with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  927 */         preparedStatement = connection.prepareStatement(str1);
/*  928 */         resultSet = preparedStatement.executeQuery();
/*  929 */         while (resultSet.next()) {
/*      */           
/*  931 */           str3 = fixLength(resultSet.getString(1), 4);
/*  932 */           str4 = fixLength(resultSet.getString(2), 6);
/*  933 */           String str = resultSet.getString(3);
/*  934 */           if (str != null && !str.equals(null))
/*  935 */             str = str.replaceAll("\r|\n|\t", ""); 
/*  936 */           str5 = fixLength(str, 30);
/*      */         } 
/*      */         
/*  939 */         str6 = " ";
/*      */       }
/*  941 */       else if ("M".equals(paramString3)) {
/*      */         
/*  943 */         str1 = "select m.machtypeatr, m.modelatr,m.invname,m.cofcat from opicm.model m where m.entityid= " + paramString1 + " and m.nlsid=1 with ur";
/*      */ 
/*      */         
/*  946 */         preparedStatement = connection.prepareStatement(str1);
/*  947 */         resultSet = preparedStatement.executeQuery();
/*  948 */         while (resultSet.next())
/*      */         {
/*  950 */           str3 = fixLength(resultSet.getString(1), 4);
/*  951 */           str4 = fixLength(resultSet.getString(2), 6);
/*  952 */           String str10 = resultSet.getString(3);
/*  953 */           if (str10 != null && !str10.equals(null))
/*  954 */             str10 = str10.replaceAll("\r|\n|\t", ""); 
/*  955 */           str5 = fixLength(str10, 30);
/*  956 */           String str11 = resultSet.getString(4);
/*  957 */           if ("Software".equals(str11)) {
/*  958 */             str6 = "2"; continue;
/*  959 */           }  if ("Hardware".equals(str11)) {
/*  960 */             str6 = "5";
/*      */           }
/*      */         }
/*      */       
/*  964 */       } else if ("SF".equals(paramString3)) {
/*  965 */         str1 = "select M.machtypeatr, F.featurecode, F.invname from opicm.relator R join opicm.swfeature F on F.entitytype=R.entity1type and F.entityid=R.entity1id and F.nlsid=1 join opicm.model M on  M.entitytype = R.entity2type and M.entityid = R.entity2id and M.nlsid=1 where R.entityid= " + paramString1 + " and R.entitytype='SWPRODSTRUCT' and M.STATUS = 'Final' and F.STATUS='Final' with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  971 */         preparedStatement = connection.prepareStatement(str1);
/*  972 */         resultSet = preparedStatement.executeQuery();
/*      */         
/*  974 */         while (resultSet.next()) {
/*      */           
/*  976 */           str3 = fixLength(resultSet.getString(1), 4);
/*  977 */           str4 = fixLength(resultSet.getString(2), 6);
/*  978 */           String str = resultSet.getString(3);
/*  979 */           if (str != null && !str.equals(null))
/*  980 */             str = str.replaceAll("\r|\n|\t", ""); 
/*  981 */           str5 = fixLength(str, 30);
/*      */         } 
/*  983 */         str6 = " ";
/*  984 */         paramString3 = "F";
/*      */       } 
/*      */     } finally {
/*  987 */       if (preparedStatement != null) {
/*      */         try {
/*  989 */           preparedStatement.close();
/*  990 */         } catch (SQLException sQLException) {
/*  991 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  996 */     if (str3 != null && !str3.equals(null)) {
/*  997 */       stringBuffer.append(str2);
/*  998 */       stringBuffer.append(str3);
/*  999 */       stringBuffer.append(str4);
/* 1000 */       stringBuffer.append(paramString3);
/* 1001 */       stringBuffer.append(str5);
/* 1002 */       stringBuffer.append(str6);
/* 1003 */       stringBuffer.append(str7);
/* 1004 */       stringBuffer.append(str8);
/* 1005 */       stringBuffer.append(str9);
/* 1006 */       stringBuffer.append(NEWLINE);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1012 */     if (this.allRecords.get(paramString2) != null) {
/* 1013 */       StringBuffer stringBuffer1 = (StringBuffer)this.allRecords.get(paramString2);
/*      */       
/* 1015 */       if (stringBuffer1.toString().indexOf(stringBuffer.toString()) <= -1) {
/* 1016 */         stringBuffer1.append(stringBuffer);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1021 */       this.allRecords.put(paramString2, stringBuffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private String fixLength(String paramString, int paramInt) {
/* 1027 */     if (paramString == null) {
/* 1028 */       paramString = "";
/*      */     }
/* 1030 */     if (paramString.length() >= paramInt) {
/* 1031 */       return paramString.substring(0, paramInt);
/*      */     }
/* 1033 */     String str = "";
/* 1034 */     for (byte b = 0; b < paramInt - paramString.length(); b++) {
/* 1035 */       str = str + " ";
/*      */     }
/* 1037 */     paramString = paramString + str;
/*      */     
/* 1039 */     return paramString;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector genWorldWideCtryList() {
/* 1044 */     Vector<String> vector = new Vector();
/* 1045 */     for (byte b = 0; b < country_convert_Array.length; b++) {
/* 1046 */       vector.add(country_convert_Array[b][0]);
/*      */     }
/* 1048 */     return vector;
/*      */   }
/*      */   
/*      */   public String[] splitStr(String paramString1, String paramString2) {
/* 1052 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, paramString2);
/* 1053 */     String[] arrayOfString = new String[stringTokenizer.countTokens()];
/* 1054 */     byte b = 0;
/* 1055 */     while (stringTokenizer.hasMoreTokens()) {
/* 1056 */       arrayOfString[b] = stringTokenizer.nextToken();
/* 1057 */       b++;
/*      */     } 
/* 1059 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public String getDescription() {
/* 1063 */     return "ESIFEEDABRSTATUS";
/*      */   }
/*      */   
/*      */   public String getABRVersion() {
/* 1067 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1074 */     addOutput(paramString);
/* 1075 */     setReturnCode(-1);
/*      */   }
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1079 */     if (3 <= DEBUG_LVL) {
/* 1080 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebugComment(int paramInt, String paramString) {
/* 1090 */     if (paramInt <= DEBUG_LVL) {
/* 1091 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     }
/*      */   }
/*      */   
/*      */   protected EntityList getEntityList(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1096 */     return this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, paramString), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/* 1097 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 1105 */     return this.m_db;
/*      */   }
/*      */   
/*      */   protected String getABRTime() {
/* 1109 */     return this.uniqueKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1118 */     String str = getLD_NDN(paramEntityItem);
/* 1119 */     addOutput(str + " " + paramString);
/* 1120 */     setReturnCode(-1);
/*      */   }
/*      */   
/*      */   protected String getLD_NDN(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1124 */     return paramEntityItem.getEntityGroup().getLongDescription() + " &quot;" + getNavigationName(paramEntityItem) + "&quot;";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 1130 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
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
/*      */   private String buildAitAbrHeader() {
/* 1145 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1153 */     MessageFormat messageFormat = new MessageFormat(str);
/* 1154 */     this.args[0] = this.m_prof.getOPName();
/* 1155 */     this.args[1] = this.m_prof.getRoleDescription();
/* 1156 */     this.args[2] = this.m_prof.getWGName();
/* 1157 */     this.args[3] = this.t2DTS;
/* 1158 */     this.args[4] = "SOF feed trigger<br/>" + this.xmlgenSb.toString();
/* 1159 */     this.args[5] = getABRVersion();
/* 1160 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1169 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 1171 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1172 */     EANList eANList = entityGroup.getMetaAttribute();
/* 1173 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1174 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1175 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1176 */       stringBuffer.append(" ");
/*      */     } 
/* 1178 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exeFtpShell(String paramString) {
/* 1186 */     String str1 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "/Dgq");
/* 1187 */     String str2 = ABRServerProperties.getFilePrefix(this.m_abri.getABRCode());
/* 1188 */     String str3 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_script", null) + " -f " + paramString;
/* 1189 */     String str4 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_inipath", null);
/*      */     
/* 1191 */     if (str4 != null)
/* 1192 */       str3 = str3 + " -i " + str4; 
/* 1193 */     if (str1 != null)
/* 1194 */       str3 = str3 + " -d " + str1; 
/* 1195 */     if (str2 != null)
/* 1196 */       str3 = str3 + " -p " + str2; 
/* 1197 */     String str5 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_targetfilename", null);
/* 1198 */     if (str5 != null)
/* 1199 */       str3 = str3 + " -t " + str5; 
/* 1200 */     String str6 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_logpath", null);
/* 1201 */     if (str6 != null)
/* 1202 */       str3 = str3 + " -l " + str6; 
/* 1203 */     String str7 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_backuppath", null);
/* 1204 */     if (str7 != null)
/* 1205 */       str3 = str3 + " -b " + str7; 
/* 1206 */     Runtime runtime = Runtime.getRuntime();
/* 1207 */     String str8 = "";
/* 1208 */     BufferedReader bufferedReader = null;
/* 1209 */     BufferedInputStream bufferedInputStream = null;
/*      */     
/*      */     try {
/* 1212 */       Process process = runtime.exec(str3);
/* 1213 */       if (process.waitFor() != 0) {
/* 1214 */         return false;
/*      */       }
/* 1216 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 1217 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 1218 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 1219 */         str8 = str8 + this.lineStr;
/* 1220 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 1221 */           return false;
/*      */         }
/*      */       } 
/* 1224 */     } catch (Exception exception) {
/* 1225 */       exception.printStackTrace();
/* 1226 */       return false;
/*      */     } finally {
/* 1228 */       if (bufferedReader != null) {
/*      */         try {
/* 1230 */           bufferedReader.close();
/* 1231 */           bufferedInputStream.close();
/* 1232 */         } catch (IOException iOException) {
/* 1233 */           iOException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 1237 */     return !(str8 == null);
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ESIFEEDABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */