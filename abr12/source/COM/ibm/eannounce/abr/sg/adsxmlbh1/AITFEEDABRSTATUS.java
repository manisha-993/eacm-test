/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Hashtable;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AITFEEDABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  59 */   private ResourceBundle rsBundle = null;
/*  60 */   private StringBuffer rptSb = new StringBuffer();
/*  61 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  62 */   private StringBuffer userxmlSb = new StringBuffer();
/*  63 */   private String t2DTS = "&nbsp;";
/*  64 */   private Object[] args = (Object[])new String[10];
/*  65 */   private String uniqueKey = String.valueOf(System.currentTimeMillis());
/*  66 */   private Hashtable countryCodeMapping = null;
/*     */   
/*     */   private boolean isZipMailNeeded = true;
/*  69 */   private static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("AITFEEDABRSTATUS");
/*  70 */   private static final String NOT_GENERATE_FC_XML_DOMAINS_STRING = ABRServerProperties.getValue("AITFEEDABRSTATUS", "_notGenerateANNFCTRANSACTION_Domains");
/*  71 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  72 */   protected static final String NEWLINE = new String(FOOL_JTEST);
/*     */   private static final Hashtable READ_LANGS_TBL;
/*  74 */   private static final String[] AITFEEDABR_STRINGS = new String[] { "COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNTMFABR", "COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNFCTRANSACTIONABR", "COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNMODELCONVERTABR", "COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNCOMPATABR" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String ANNFCTRANSACTION_ABR_CLASSNAME = "COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNFCTRANSACTIONABR";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final Vector ANNTYPE_FOR_XML = new Vector(); static {
/*  86 */     ANNTYPE_FOR_XML.add("19");
/*  87 */     ANNTYPE_FOR_XML.add("14");
/*     */ 
/*     */     
/*  90 */     READ_LANGS_TBL = new Hashtable<>();
/*     */     
/*  92 */     READ_LANGS_TBL.put("" + Profile.ENGLISH_LANGUAGE.getNLSID(), Profile.ENGLISH_LANGUAGE);
/*  93 */     READ_LANGS_TBL.put("" + Profile.GERMAN_LANGUAGE.getNLSID(), Profile.GERMAN_LANGUAGE);
/*  94 */     READ_LANGS_TBL.put("" + Profile.ITALIAN_LANGUAGE.getNLSID(), Profile.ITALIAN_LANGUAGE);
/*  95 */     READ_LANGS_TBL.put("" + Profile.JAPANESE_LANGUAGE.getNLSID(), Profile.JAPANESE_LANGUAGE);
/*  96 */     READ_LANGS_TBL.put("" + Profile.FRENCH_LANGUAGE.getNLSID(), Profile.FRENCH_LANGUAGE);
/*  97 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LANGUAGE.getNLSID(), Profile.SPANISH_LANGUAGE);
/*  98 */     READ_LANGS_TBL.put("" + Profile.UK_ENGLISH_LANGUAGE.getNLSID(), Profile.UK_ENGLISH_LANGUAGE);
/*  99 */     READ_LANGS_TBL.put("" + Profile.KOREAN_LANGUAGE.getNLSID(), Profile.KOREAN_LANGUAGE);
/* 100 */     READ_LANGS_TBL.put("" + Profile.CHINESE_LANGUAGE.getNLSID(), Profile.CHINESE_LANGUAGE);
/* 101 */     READ_LANGS_TBL.put("" + Profile.FRENCH_CANADIAN_LANGUAGE.getNLSID(), Profile.FRENCH_CANADIAN_LANGUAGE);
/* 102 */     READ_LANGS_TBL.put("" + Profile.CHINESE_SIMPLIFIED_LANGUAGE.getNLSID(), Profile.CHINESE_SIMPLIFIED_LANGUAGE);
/* 103 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LATINAMERICAN_LANGUAGE.getNLSID(), Profile.SPANISH_LATINAMERICAN_LANGUAGE);
/* 104 */     READ_LANGS_TBL.put("" + Profile.PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID(), Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 111 */     String str1 = "";
/*     */ 
/*     */     
/*     */     try {
/* 115 */       long l = System.currentTimeMillis();
/*     */       
/* 117 */       start_ABRBuild(false);
/*     */ 
/*     */       
/* 120 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */ 
/*     */       
/* 123 */       setReturnCode(0);
/*     */ 
/*     */       
/* 126 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/* 127 */               getEntityType(), getEntityID()) });
/*     */ 
/*     */       
/* 130 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */       
/* 133 */       this.uniqueKey = entityItem.getKey() + String.valueOf(System.currentTimeMillis());
/*     */ 
/*     */       
/* 136 */       String str6 = PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN");
/* 137 */       addDebug("RootEntity:" + entityItem.getKey() + " PDHDOMAIN:" + str6);
/* 138 */       addDebug("RootEntity:" + entityItem.getKey() + " Not generate feature transaction xml domians:" + NOT_GENERATE_FC_XML_DOMAINS_STRING);
/* 139 */       Vector vector = getDomainVector(NOT_GENERATE_FC_XML_DOMAINS_STRING);
/*     */ 
/*     */       
/* 142 */       String str7 = PokUtils.getAttributeFlagValue(entityItem, "ANNTYPE");
/* 143 */       addDebug("RootEntity:" + entityItem.getKey() + " ANNTYPE:" + str7);
/* 144 */       String str8 = entityItem.getKey();
/* 145 */       if (ANNTYPE_FOR_XML.contains(str7)) {
/* 146 */         Vector<String> vector1 = new Vector(1);
/* 147 */         for (byte b = 0; b < AITFEEDABR_STRINGS.length; b++) {
/* 148 */           long l1 = System.currentTimeMillis();
/* 149 */           String str = AITFEEDABR_STRINGS[b];
/*     */           
/* 151 */           if ("COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNFCTRANSACTIONABR".equals(str) && vector.contains(str6)) {
/* 152 */             addOutput("Not generate ANNFCTRANSACTION xml for domian : " + str6);
/* 153 */             addDebug("Not generate ANNFCTRANSACTION xml for domian : " + str6);
/*     */           } else {
/* 155 */             addDebug("creating instance of AIT feed ABR  = " + str);
/*     */             
/* 157 */             AITFEEDXML aITFEEDXML = (AITFEEDXML)Class.forName(str).newInstance();
/* 158 */             addDebug(getShortClassName(aITFEEDXML.getClass()) + " " + aITFEEDXML.getVersion());
/*     */ 
/*     */ 
/*     */             
/* 162 */             if (getReturnCode() == 0) {
/*     */               
/*     */               try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 171 */                 aITFEEDXML.processThis(this, this.m_prof, entityItem);
/* 172 */                 vector1.add(aITFEEDXML.getXMLFileName());
/* 173 */               } catch (FileNotFoundException fileNotFoundException) {
/* 174 */                 addXMLGenMsg("FAILED", str8);
/* 175 */                 throw fileNotFoundException;
/* 176 */               } catch (TransformerConfigurationException transformerConfigurationException) {
/* 177 */                 addXMLGenMsg("FAILED", str8);
/* 178 */                 throw transformerConfigurationException;
/* 179 */               } catch (MiddlewareRequestException middlewareRequestException) {
/* 180 */                 addXMLGenMsg("FAILED", str8);
/* 181 */                 throw middlewareRequestException;
/* 182 */               } catch (SAXException sAXException) {
/* 183 */                 addXMLGenMsg("FAILED", str8);
/* 184 */                 throw sAXException;
/* 185 */               } catch (SQLException sQLException) {
/* 186 */                 addXMLGenMsg("FAILED", str8);
/* 187 */                 throw sQLException;
/* 188 */               } catch (MiddlewareException middlewareException) {
/* 189 */                 addXMLGenMsg("FAILED", str8);
/* 190 */                 throw middlewareException;
/* 191 */               } catch (Exception exception) {
/* 192 */                 addXMLGenMsg("FAILED", str8);
/* 193 */                 throw exception;
/*     */               } 
/*     */             }
/* 196 */             addOutput("Generate XML " + aITFEEDXML.getXMLFileName() + " successfully");
/* 197 */             addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l1) + " for abr:" + str);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 202 */         FileOutputStream fileOutputStream = null;
/* 203 */         ZipOutputStream zipOutputStream = null;
/* 204 */         String str9 = this.m_abri.getFileName();
/* 205 */         int i = str9.lastIndexOf(".");
/* 206 */         String str10 = str9.substring(0, i + 1) + "zip";
/* 207 */         File file = new File(str10);
/*     */         try {
/* 209 */           char c = 'ࠀ';
/* 210 */           fileOutputStream = new FileOutputStream(file);
/* 211 */           zipOutputStream = new ZipOutputStream(fileOutputStream);
/* 212 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 213 */             String str = vector1.get(b1);
/* 214 */             File file1 = new File(ABRServerProperties.getOutputPath() + str + getABRTime());
/* 215 */             if (file1.exists()) {
/* 216 */               zipOutputStream.putNextEntry(new ZipEntry(str));
/* 217 */               BufferedInputStream bufferedInputStream = null;
/*     */               try {
/* 219 */                 bufferedInputStream = new BufferedInputStream(new FileInputStream(file1));
/*     */                 
/* 221 */                 byte[] arrayOfByte = new byte[c]; int j;
/* 222 */                 while ((j = bufferedInputStream.read(arrayOfByte, 0, c)) != -1) {
/* 223 */                   zipOutputStream.write(arrayOfByte, 0, j);
/*     */                 }
/* 225 */                 zipOutputStream.closeEntry();
/* 226 */                 zipOutputStream.flush();
/* 227 */                 addDebug("Zip file " + str + getABRTime() + " successfully");
/*     */               } finally {
/* 229 */                 if (bufferedInputStream != null) {
/* 230 */                   bufferedInputStream.close();
/*     */                 }
/*     */               } 
/*     */               
/* 234 */               file1.delete();
/*     */             } else {
/* 236 */               addError("Zip file..., Missing XML file " + str);
/* 237 */               this.isZipMailNeeded = false;
/*     */             } 
/*     */           } 
/*     */         } finally {
/* 241 */           if (zipOutputStream != null) {
/* 242 */             zipOutputStream.flush();
/* 243 */             zipOutputStream.close();
/*     */           } 
/* 245 */           if (fileOutputStream != null) {
/* 246 */             fileOutputStream.flush();
/* 247 */             fileOutputStream.close();
/*     */           } 
/*     */         } 
/* 250 */         vector1.clear();
/* 251 */         vector1 = null;
/*     */ 
/*     */         
/* 254 */         if (this.isZipMailNeeded) {
/* 255 */           FileInputStream fileInputStream = null;
/*     */           try {
/* 257 */             fileInputStream = new FileInputStream(file);
/* 258 */             int j = fileInputStream.available();
/* 259 */             byte[] arrayOfByte = new byte[j];
/* 260 */             fileInputStream.read(arrayOfByte);
/* 261 */             setAttachByteForDG(arrayOfByte);
/* 262 */             getABRItem().setFileExtension("zip");
/*     */           } finally {
/* 264 */             if (fileInputStream != null) {
/* 265 */               fileInputStream.close();
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 271 */         if (!getABRItem().getKeepFile() && 
/* 272 */           file.exists()) {
/* 273 */           file.delete();
/* 274 */           addDebug("Check the keep file is false, delete the zip file");
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 279 */         addXMLGenMsg("ANNTYPE_NOT_LISTED", str8);
/*     */       } 
/*     */ 
/*     */       
/* 283 */       str1 = getNavigationName(entityItem);
/* 284 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/* 285 */     } catch (Throwable throwable) {
/* 286 */       StringWriter stringWriter = new StringWriter();
/* 287 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 288 */       String str7 = "<pre>{0}</pre>";
/* 289 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/* 290 */       setReturnCode(-3);
/* 291 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 293 */       this.args[0] = throwable.getMessage();
/* 294 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 295 */       messageFormat1 = new MessageFormat(str7);
/* 296 */       this.args[0] = stringWriter.getBuffer().toString();
/* 297 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 298 */       logError("Exception: " + throwable.getMessage());
/* 299 */       logError(stringWriter.getBuffer().toString());
/*     */     } finally {
/* 301 */       if (this.t2DTS.equals("&nbsp;")) {
/* 302 */         this.t2DTS = getNow();
/*     */       }
/* 304 */       setDGTitle(str1);
/* 305 */       setDGRptName(getShortClassName(getClass()));
/* 306 */       setDGRptClass(getABRCode());
/*     */       
/* 308 */       if (!isReadOnly()) {
/* 309 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 315 */     println(EACustom.getDocTypeHtml());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */ 
/*     */ 
/*     */     
/* 332 */     MessageFormat messageFormat = new MessageFormat(str2);
/* 333 */     this.args[0] = getShortClassName(getClass());
/* 334 */     this.args[1] = str1;
/* 335 */     String str3 = messageFormat.format(this.args);
/* 336 */     String str4 = buildAitAbrHeader();
/*     */ 
/*     */ 
/*     */     
/* 340 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("RESULT_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/* 341 */     this.rptSb.insert(0, str5);
/* 342 */     println(this.rptSb.toString());
/* 343 */     printDGSubmitString();
/* 344 */     println(EACustom.getTOUDiv());
/* 345 */     buildReportFooter();
/*     */ 
/*     */     
/* 348 */     this.m_elist.dereference();
/* 349 */     this.m_elist = null;
/* 350 */     this.rsBundle = null;
/* 351 */     this.args = null;
/* 352 */     messageFormat = null;
/* 353 */     this.userxmlSb = null;
/* 354 */     this.rptSb = null;
/* 355 */     this.xmlgenSb = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 361 */     return "AITFEEDABRSTATUS";
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/* 365 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 372 */     addOutput(paramString);
/* 373 */     setReturnCode(-1);
/*     */   }
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 377 */     if (3 <= DEBUG_LVL) {
/* 378 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addDebugComment(int paramInt, String paramString) {
/* 388 */     if (paramInt <= DEBUG_LVL) {
/* 389 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */   
/*     */   protected EntityList getEntityList(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 394 */     return this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, paramString), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/* 395 */             .getEntityType(), paramEntityItem.getEntityID()) });
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
/*     */   protected EntityItem getEntityItem(Profile paramProfile, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 410 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/* 411 */             .getEntityType(), paramEntityItem.getEntityID()) });
/* 412 */     return entityList.getParentEntityGroup().getEntityItem(0);
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
/*     */   protected EntityItem getEntityItem(Profile paramProfile, String paramString, int paramInt) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 428 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramString, paramInt) });
/*     */     
/* 430 */     return entityList.getParentEntityGroup().getEntityItem(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Database getDB() {
/* 437 */     return this.m_db;
/*     */   }
/*     */   
/*     */   protected String getABRTime() {
/* 441 */     return this.uniqueKey;
/*     */   }
/*     */   
/*     */   protected String getGenareaName(String paramString) {
/* 445 */     if (this.countryCodeMapping == null) {
/* 446 */       this.countryCodeMapping = getCountryCodeMapping();
/*     */     }
/* 448 */     return (String)this.countryCodeMapping.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addOutput(String paramString) {
/* 455 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addXMLGenMsg(String paramString1, String paramString2) {
/* 462 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/* 463 */     Object[] arrayOfObject = { paramString2 };
/* 464 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
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
/*     */   private String buildAitAbrHeader() {
/* 478 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 486 */     MessageFormat messageFormat = new MessageFormat(str);
/* 487 */     this.args[0] = this.m_prof.getOPName();
/* 488 */     this.args[1] = this.m_prof.getRoleDescription();
/* 489 */     this.args[2] = this.m_prof.getWGName();
/* 490 */     this.args[3] = this.t2DTS;
/* 491 */     this.args[4] = "AIT feed trigger<br/>" + this.xmlgenSb.toString();
/* 492 */     this.args[5] = getABRVersion();
/* 493 */     return messageFormat.format(this.args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 502 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 504 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 505 */     EANList eANList = entityGroup.getMetaAttribute();
/* 506 */     for (byte b = 0; b < eANList.size(); b++) {
/* 507 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 508 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 509 */       stringBuffer.append(" ");
/*     */     } 
/* 511 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private Hashtable getCountryCodeMapping() {
/* 515 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 516 */     Statement statement = null;
/* 517 */     String str = "SELECT genareaname_fc,genareacode FROM price.generalarea WHERE genareatype='Country' AND nlsid=1 WITH UR";
/*     */     try {
/* 519 */       statement = this.m_db.getPDHConnection().createStatement();
/* 520 */       ResultSet resultSet = statement.executeQuery(str);
/* 521 */       while (resultSet.next()) {
/* 522 */         hashtable.put(resultSet.getString(1).trim(), resultSet.getString(2).trim());
/*     */       }
/* 524 */     } catch (MiddlewareException middlewareException) {
/* 525 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 526 */       middlewareException.printStackTrace();
/* 527 */     } catch (SQLException sQLException) {
/* 528 */       addDebug("SQLException on ? " + sQLException);
/* 529 */       sQLException.printStackTrace();
/*     */     } finally {
/* 531 */       if (statement != null) {
/*     */         try {
/* 533 */           statement.close();
/* 534 */         } catch (SQLException sQLException) {
/* 535 */           sQLException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 539 */     addDebug("Get country mapping records:" + hashtable.size());
/* 540 */     return hashtable;
/*     */   }
/*     */   
/*     */   private Vector getDomainVector(String paramString) {
/* 544 */     Vector<String> vector = new Vector();
/* 545 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/* 546 */     while (stringTokenizer.hasMoreTokens()) {
/* 547 */       String str = stringTokenizer.nextToken().trim();
/* 548 */       vector.add(str);
/*     */     } 
/* 550 */     return vector;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\AITFEEDABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */