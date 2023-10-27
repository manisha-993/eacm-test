/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class IBIDATAABRSTATUS extends PokBaseABR {
/*  41 */   private StringBuffer rptSb = new StringBuffer();
/*  42 */   private String ffFileName = null;
/*  43 */   private String modelFileName = null;
/*  44 */   private String prodFileName = null;
/*  45 */   private String swFileName = null;
/*  46 */   private String dir = null;
/*  47 */   private String lineStr = null;
/*  48 */   public String filepath = null;
/*     */   
/*  50 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  51 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  52 */   private Object[] args = (Object[])new String[10];
/*     */   
/*     */   private static final String SUCCESS = "SUCCESS";
/*     */   
/*     */   private static final String FAILD = "FAILD";
/*     */   
/*     */   private static final String IBIPRTPATH = "_ibiprtpath";
/*     */   
/*     */   private static final String IBIINIPATH = "_ibiinipath";
/*     */   
/*     */   private static final String SFTPSCRPATH = "_sftpscrpath";
/*     */   private static final String TMODNAME = "_tmodname";
/*     */   private static final String TPRODNAME = "_tprodname";
/*     */   private static final String TSWPRODNAME = "_tswprodname";
/*     */   private static final String CODEFILENAME = "_codefilename";
/*     */   public static final String US_ASCII = "ASCII";
/*  68 */   String t1 = null;
/*  69 */   String t2 = null;
/*  70 */   String tmodel = null;
/*  71 */   String tprod = null;
/*  72 */   String tswprod = null;
/*  73 */   String type = "FULL";
/*     */   private static final String ENTITYTYPE = "ENTITYTYPE";
/*     */   private static final String MACHTYPEATR = "MACHTYPEATR";
/*     */   private static final String MODELATR = "MODELATR";
/*     */   private static final String MKTGNAME = "MKTGNAME";
/*     */   private static final String ANNDATE = "ANNDATE";
/*     */   private static final String WITHDRAWDATE = "WITHDRAWDATE";
/*     */   private static final String WTHDRWEFFCTVDATE = "WTHDRWEFFCTVDATE";
/*     */   private static final String INSTALL = "INSTALL";
/*     */   private static final String STATUS = "STATUS";
/*     */   private static final String VALFROM = "VALFROM";
/*     */   private static final String FEATURECODE = "FEATURECODE";
/*     */   private static final String EFFECTIVEDATE = "EFFECTIVEDATE";
/*     */   private static final String PANNDATE = "PANNDATE";
/*     */   private static final String LANNDATE = "LANNDATE";
/*     */   boolean sentFile = true;
/*  89 */   EntityItem rootEntity = null;
/*     */   private static final String COFCAT = "COFCAT";
/*     */   private static final String DIV = "DIV";
/*     */   private static final String COFSUBCAT = "COFSUBCAT";
/*     */   private static final String COFSUBGRP = "COFSUBGRP";
/*  94 */   Pattern p = Pattern.compile("\t|\r|\n");
/*  95 */   byte[] bytes = new byte[] { -62, -96 };
/*  96 */   private String UTFSpace = null;
/*  97 */   private static final String[] MODELCOLUMNS = new String[] { "ENTITYTYPE", "MACHTYPEATR", "MODELATR", "MKTGNAME", "ANNDATE", "WITHDRAWDATE", "WTHDRWEFFCTVDATE", "INSTALL", "STATUS", "VALFROM" };
/*     */   
/*  99 */   private static final String[] PRODCOLUMNS = new String[] { "ENTITYTYPE", "MACHTYPEATR", "MODELATR", "FEATURECODE", "MKTGNAME", "ANNDATE", "WITHDRAWDATE", "WTHDRWEFFCTVDATE", "INSTALL", "STATUS", "VALFROM" };
/*     */   
/* 101 */   private static final String[] SWPRODCOLUMNS = new String[] { "ENTITYTYPE", "MACHTYPEATR", "MODELATR", "FEATURECODE", "MKTGNAME", "PANNDATE", "LANNDATE", "EFFECTIVEDATE", "STATUS", "VALFROM" };
/*     */   
/*     */   public static final String ASCII = "ASCII";
/*     */   
/*     */   public static final String ISO_8859_1 = "ISO-8859-1";
/*     */   
/*     */   public static final String UTF_8 = "UTF-8";
/*     */   
/* 109 */   private Map<String, String> mapping = null;
/*     */ 
/*     */   
/* 112 */   private static final Hashtable COLUMN_LENGTH = new Hashtable<>(); static {
/* 113 */     COLUMN_LENGTH.put("ENTITYTYPE", "15");
/* 114 */     COLUMN_LENGTH.put("MACHTYPEATR", "4");
/* 115 */     COLUMN_LENGTH.put("MODELATR", "3");
/* 116 */     COLUMN_LENGTH.put("MKTGNAME", "128");
/* 117 */     COLUMN_LENGTH.put("ANNDATE", "10");
/* 118 */     COLUMN_LENGTH.put("PANNDATE", "10");
/* 119 */     COLUMN_LENGTH.put("LANNDATE", "10");
/* 120 */     COLUMN_LENGTH.put("WITHDRAWDATE", "10");
/* 121 */     COLUMN_LENGTH.put("WTHDRWEFFCTVDATE", "10");
/* 122 */     COLUMN_LENGTH.put("INSTALL", "5");
/* 123 */     COLUMN_LENGTH.put("STATUS", "6");
/* 124 */     COLUMN_LENGTH.put("VALFROM", "26");
/* 125 */     COLUMN_LENGTH.put("FEATURECODE", "6");
/* 126 */     COLUMN_LENGTH.put("EFFECTIVEDATE", "10");
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
/*     */   private static final String QUERY = "select m.machtypeatr,m.modelatr,m.mktgname,m.install,m.cofcat,m.cofsubcat,m.cofsubgrp,m.anndate,m.withdrawdate,m.WTHDRWEFFCTVDATE,S.div,m.status from price.model m join price.SGMNTACRNYM S on m.PRFTCTR=S.PRFTCTR where m.nlsid=1  and m.cofcat='Hardware' and m.status in ('Final','Ready for Review') and s.nlsid=1 and S.DIV<>'71 Retail Store Systems' and m.INSTALL='CE' with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String QUERY_MODEL = "with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status in ('Final','Ready for Review')  group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in  (select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status  in ('Final','Ready for Review') and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and m.nlsid=1 ))  select distinct m.ENTITYTYPE,m.MACHTYPEATR,m.MODELATR,m.MKTGNAME,m.ANNDATE,m.WITHDRAWDATE,m.WTHDRWEFFCTVDATE,m.INSTALL,m.STATUS,m.VALFROM from opicm.model m  left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1  where   m.status in ('Final','Ready for Review') and m.nlsid=1 and (S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) and m.entityid not in (select id from dump1)and  m.VALFROM>= ? and m.VALFROM < ?  with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String QUERY_PRODSTRUCT = "with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status in ('Final','Ready for Review') group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in (select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status  in ('Final','Ready for Review') and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and valto > current timestamp ))select  r.entitytype,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,p.MKTGNAME,f.MKTGNAME as FMKTGNAME,p.ANNDATE,p.WITHDRAWDATE,p.WTHDRWEFFCTVDATE,p.INSTALL,p.STATUS,p.VALFROM from OPICM.MODEL m join opicm.relator r on r.entitytype='PRODSTRUCT' and r.entity2id=m.entityid join opicm.feature f on f.entityid=r.entity1id and f.status in ('Final','Ready for Review') and f.nlsid=1 join opicm.prodstruct p on p.entityid=r.entityid and p.nlsid=1 and p.status in ('Final','Ready for Review') left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1 where  p.VALFROM>= ? and p.VALFROM < ? and m.status in ('Final','Ready for Review') and  m.nlsid=1  and m.entityid not in (select id from dump1)  and (S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String QUERY_SWPRODSTRUCT = "with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status in ('Final','Ready for Review') group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in (select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status  in ('Final','Ready for Review') and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and valto > current timestamp )),temp as (select sw.entitytype ,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,sw.MKTGNAME,f.INVNAME as FMKTGNAME,coalesce(annp.ANNDATE,'9999-12-32') AS PANNDATE,coalesce( annl.ANNDATE,'9999-12-32') as LANNDATE,coalesce(a1.EFFECTIVEDATE,'9999-12-32') as EFFECTIVEDATE,sw.STATUS,sw.VALFROM,max(coalesce(sw.VALFROM,'1980-01-01 00:00:00.000000') ,coalesce(a1.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(a.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annp.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annl.VALFROM,'1980-01-01 00:00:00.000000')) as MAXDATA from opicm.SWPRODSTRUCT sw join opicm.relator r2 on r2.entitytype = 'SWPRODSTRUCT' and r2.entityid = sw.entityid join opicm.model m on m.nlsid =1 and m.entityid = r2.entity2id and m.status in ('Final','Ready for Review') join opicm.swfeature f on f.nlsid =1 and f.entityid = r2.entity1id and f.status in ('Final','Ready for Review') left join  opicm.relator r on  r.entitytype = 'SWPRODSTRUCTAVAIL' and r.entity1id = sw.entityid left join  opicm.avail a on a.entityid = r.entity2id and a.nlsid=1 and  a.AVAILTYPE='Planned Availability' and a.status in ('Final','Ready for Review') left join opicm.announcement annp on  a.anncodename=annp.anncodename and annp.nlsid=1 and annp.ANNSTATUS  in ('Final','Ready for Review') left join  opicm.relator r1 on  r1.entitytype = 'SWPRODSTRUCTAVAIL' and r1.entity1id = sw.entityid left join  opicm.avail a1 on a1.entityid = r1.entity2id and a1.nlsid=1 and  a1.AVAILTYPE='Last Order'  AND a1.status in ('Final','Ready for Review')  left join opicm.announcement annl on  a1.anncodename=annl.anncodename and annl.nlsid=1  and annl.ANNSTATUS  in ('Final','Ready for Review') left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1 where  sw.status in ('Final','Ready for Review') and sw.nlsid=1  and (S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) and m.entityid not in (select id from dump1) )  select distinct entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,FMKTGNAME,(case min(PANNDATE) when '9999-12-32' then '' else min(PANNDATE) end )as PANNDATE,(case min(LANNDATE) when '9999-12-32' then '' else min(LANNDATE) end ) as LANNDATE,(case min(EFFECTIVEDATE) when '9999-12-32' then '' else min(EFFECTIVEDATE) end ) as EFFECTIVEDATE,STATUS,VALFROM from temp group by entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,FMKTGNAME,VALFROM,STATUS having max(MAXDATA) >= ? and max(MAXDATA) < ?  with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFileName() throws IOException {
/* 201 */     String str1 = ABRServerProperties.getFilePrefix(this.m_abri.getABRCode());
/*     */ 
/*     */     
/* 204 */     StringBuffer stringBuffer = new StringBuffer();
/* 205 */     String str2 = getNow();
/*     */     
/* 207 */     str2 = str2.replace(' ', '_');
/* 208 */     stringBuffer.append(str2 + ".txt");
/* 209 */     this.dir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_ibiprtpath", "/Dgq");
/* 210 */     this.tmodel = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_tmodname");
/* 211 */     this.tprod = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_tprodname");
/* 212 */     this.tswprod = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_tswprodname");
/* 213 */     this.filepath = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_codefilename");
/* 214 */     if (!this.dir.endsWith("/")) {
/* 215 */       this.dir += "/";
/*     */     }
/* 217 */     this.ffFileName = stringBuffer.toString();
/* 218 */     this.modelFileName = this.dir + str1.trim() + "MODEL" + this.ffFileName;
/* 219 */     this.prodFileName = this.dir + str1.trim() + "PRODSTRUCT" + this.ffFileName;
/* 220 */     this.swFileName = this.dir + str1.trim() + "SWPRODSTRUCT" + this.ffFileName;
/* 221 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws IOException {
/* 226 */     (new IBIDATAABRSTATUS()).test();
/*     */   }
/*     */   public void test() throws IOException {
/* 229 */     init();
/* 230 */     String str1 = "C:\\Users\\JianBoXu\\Downloads\\masscode.txt";
/*     */ 
/*     */     
/* 233 */     String str2 = "C:\\Users\\JianBoXu\\Downloads\\MODELIBI_2020-12-15-01.32.13.072315.txt";
/* 234 */     String str3 = "C:\\Users\\JianBoXu\\Downloads\\PRODSTRUCTIBI_2020-12-15-01.32.13.072315.txt";
/* 235 */     String str4 = "C:\\Users\\JianBoXu\\Downloads\\SWPRODSTRUCTIBI_2020-12-15-01.32.13.072315.txt";
/* 236 */     String str5 = "C:\\Users\\JianBoXu\\Downloads\\SWPRODSTRUCT.txt";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(str4), "utf-8");
/* 247 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(str5), "ASCII");
/* 248 */     String str6 = null;
/* 249 */     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
/* 250 */     BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     String str7 = new String(this.bytes, "utf-8");
/* 267 */     while ((str6 = bufferedReader.readLine()) != null) {
/* 268 */       if (str6.contains("6756N89RTS for VMware ESXi")) {
/* 269 */         System.out.println(new String(str6.getBytes("UTF-8"), "ASCII"));
/*     */       }
/*     */       
/* 272 */       str6 = remove(str6);
/*     */ 
/*     */ 
/*     */       
/* 276 */       bufferedWriter.write(str6);
/* 277 */       bufferedWriter.newLine();
/*     */     } 
/*     */     
/* 280 */     System.out.println(inputStreamReader.getEncoding());
/* 281 */     System.out.println(outputStreamWriter.getEncoding());
/* 282 */     bufferedWriter.close();
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
/*     */   public void init() throws UnsupportedEncodingException {
/* 298 */     this.UTFSpace = new String(this.bytes, "utf-8");
/* 299 */     this.mapping = new Hashtable<>();
/*     */ 
/*     */     
/*     */     try {
/* 303 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.filepath), "UTF-8"));
/*     */       
/* 305 */       String str = null;
/* 306 */       while ((str = bufferedReader.readLine()) != null) {
/* 307 */         String[] arrayOfString = str.split("%");
/* 308 */         this.mapping.put(arrayOfString[0].trim(), arrayOfString[1].trim());
/*     */       } 
/*     */       
/* 311 */       bufferedReader.close();
/*     */     }
/* 313 */     catch (IOException iOException) {
/*     */       
/* 315 */       iOException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 324 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/* 326 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Result: </th><td>{4}</td></tr>" + NEWLINE + "</table><!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     String str3 = "";
/* 332 */     String[] arrayOfString = new String[10];
/* 333 */     String str4 = "";
/* 334 */     boolean bool = true;
/*     */     
/*     */     try {
/* 337 */       start_ABRBuild(false);
/* 338 */       setReturnCode(0);
/* 339 */       this.UTFSpace = new String(this.bytes, "utf-8");
/*     */       
/* 341 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/* 342 */               getEntityType(), getEntityID()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 351 */       this.rootEntity = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */       
/* 353 */       this.t1 = PokUtils.getAttributeValue(this.rootEntity, "IBIDATADTS", "", "1980-01-01-00.00.00.000000");
/*     */       
/* 355 */       if ("1980-01-01-00.00.00.000000".equals(this.t1)) {
/* 356 */         this.type = "FULL";
/*     */       } else {
/* 358 */         this.type = "DELTA";
/*     */       } 
/* 360 */       this.t2 = getNow();
/* 361 */       MessageFormat messageFormat = new MessageFormat(str1);
/* 362 */       arrayOfString[0] = getShortClassName(getClass());
/* 363 */       arrayOfString[1] = "ABR";
/* 364 */       str4 = messageFormat.format(arrayOfString);
/*     */       
/* 366 */       setDGTitle("IBIDATAABRSTATUS report");
/* 367 */       setDGString(getABRReturnCode());
/* 368 */       setDGRptName("IBIDATAABRSTATUS");
/* 369 */       setDGRptClass("IBIDATAABRSTATUS");
/* 370 */       setFileName();
/* 371 */       generateFlatFile();
/*     */     }
/* 373 */     catch (Exception exception) {
/*     */       
/* 375 */       exception.printStackTrace();
/*     */       
/* 377 */       setReturnCode(-1);
/* 378 */       StringWriter stringWriter = new StringWriter();
/* 379 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 380 */       String str6 = "<pre>{0}</pre>";
/* 381 */       MessageFormat messageFormat = new MessageFormat(str5);
/* 382 */       setReturnCode(-3);
/* 383 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 385 */       arrayOfString[0] = exception.getMessage();
/* 386 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 387 */       messageFormat = new MessageFormat(str6);
/* 388 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 389 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 390 */       logError("Exception: " + exception.getMessage());
/* 391 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 393 */       setCreateDGEntity(true);
/* 394 */       bool = false;
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 399 */       StringBuffer stringBuffer = new StringBuffer();
/* 400 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 401 */       arrayOfString[0] = this.m_prof.getOPName();
/* 402 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 403 */       arrayOfString[2] = this.m_prof.getWGName();
/* 404 */       arrayOfString[3] = getNow();
/* 405 */       stringBuffer.append("Time start from " + this.t1 + " to " + this.t2 + "<br>");
/* 406 */       stringBuffer.append(bool ? "generated the IBI data report file successful " : "generated the IBI data report file faild");
/*     */       
/* 408 */       stringBuffer.append(",");
/* 409 */       stringBuffer.append(this.sentFile ? "send the IBI data report file successful " : "sent the IBI data report file faild");
/*     */ 
/*     */ 
/*     */       
/* 413 */       arrayOfString[4] = stringBuffer.toString();
/* 414 */       arrayOfString[5] = str3 + " " + getABRVersion();
/*     */       
/* 416 */       this.rptSb.insert(0, str4 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 418 */       println(EACustom.getDocTypeHtml());
/* 419 */       println(this.rptSb.toString());
/* 420 */       printDGSubmitString();
/* 421 */       println(EACustom.getTOUDiv());
/* 422 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateModel() throws IOException, MiddlewareException, SQLException {
/* 430 */     FileOutputStream fileOutputStream = new FileOutputStream(this.modelFileName);
/*     */ 
/*     */ 
/*     */     
/* 434 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "ASCII");
/*     */ 
/*     */     
/* 437 */     String str1 = getNow();
/* 438 */     String str2 = str1.substring(0, 10);
/* 439 */     String str3 = str1.substring(11, 19);
/* 440 */     String str4 = str1.substring(19);
/* 441 */     str3 = str3.replace('.', ':');
/* 442 */     String str5 = str2 + " " + str3 + str4;
/* 443 */     Connection connection = this.m_db.getODS2Connection();
/* 444 */     PreparedStatement preparedStatement = connection.prepareStatement("with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status in ('Final','Ready for Review')  group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in  (select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status  in ('Final','Ready for Review') and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and m.nlsid=1 ))  select distinct m.ENTITYTYPE,m.MACHTYPEATR,m.MODELATR,m.MKTGNAME,m.ANNDATE,m.WITHDRAWDATE,m.WTHDRWEFFCTVDATE,m.INSTALL,m.STATUS,m.VALFROM from opicm.model m  left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1  where   m.status in ('Final','Ready for Review') and m.nlsid=1 and (S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) and m.entityid not in (select id from dump1)and  m.VALFROM>= ? and m.VALFROM < ?  with ur", 1004, 1007);
/*     */     
/* 446 */     preparedStatement.setString(1, this.t1);
/* 447 */     preparedStatement.setString(2, this.t2);
/* 448 */     System.out.println("query:with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status in ('Final','Ready for Review')  group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in  (select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status  in ('Final','Ready for Review') and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and m.nlsid=1 ))  select distinct m.ENTITYTYPE,m.MACHTYPEATR,m.MODELATR,m.MKTGNAME,m.ANNDATE,m.WITHDRAWDATE,m.WTHDRWEFFCTVDATE,m.INSTALL,m.STATUS,m.VALFROM from opicm.model m  left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1  where   m.status in ('Final','Ready for Review') and m.nlsid=1 and (S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) and m.entityid not in (select id from dump1)and  m.VALFROM>= ? and m.VALFROM < ?  with ur:" + this.t1 + ":" + this.t2);
/* 449 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 450 */     int i = 0;
/*     */     
/* 452 */     if (resultSet.last()) {
/* 453 */       i = resultSet.getRow();
/* 454 */       outputStreamWriter.write(this.type + "EACM" + str5 + i);
/* 455 */       outputStreamWriter.write("\n");
/* 456 */       resultSet.first();
/*     */     } 
/* 458 */     if (i == 0) {
/* 459 */       outputStreamWriter.write(this.type + "EACM" + str5 + i);
/* 460 */       outputStreamWriter.write("\n");
/* 461 */       outputStreamWriter.close();
/*     */       
/*     */       return;
/*     */     } 
/*     */     while (true) {
/* 466 */       for (byte b = 0; b < MODELCOLUMNS.length; b++) {
/* 467 */         String str = resultSet.getString(MODELCOLUMNS[b]);
/* 468 */         str = (str == null) ? "" : str.trim();
/* 469 */         outputStreamWriter.write(getValue(str, MODELCOLUMNS[b]));
/* 470 */         if ("INSTALL".equals(MODELCOLUMNS[b])) {
/* 471 */           if ("CE".equals(str)) {
/* 472 */             outputStreamWriter.write("IBI");
/* 473 */           } else if ("CIF".equals(str)) {
/*     */             
/* 475 */             outputStreamWriter.write("CSU");
/*     */           } else {
/* 477 */             outputStreamWriter.write("   ");
/*     */           } 
/*     */         }
/*     */       } 
/* 481 */       outputStreamWriter.write("\n");
/* 482 */       outputStreamWriter.flush();
/* 483 */       if (!resultSet.next()) {
/* 484 */         outputStreamWriter.flush();
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void generateProd() throws IOException, MiddlewareException, SQLException {
/* 490 */     FileOutputStream fileOutputStream = new FileOutputStream(this.prodFileName);
/*     */ 
/*     */ 
/*     */     
/* 494 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "ASCII");
/*     */ 
/*     */     
/* 497 */     String str1 = getNow();
/* 498 */     String str2 = str1.substring(0, 10);
/* 499 */     String str3 = str1.substring(11, 19);
/* 500 */     String str4 = str1.substring(19);
/* 501 */     str3 = str3.replace('.', ':');
/* 502 */     String str5 = str2 + " " + str3 + str4;
/*     */     
/* 504 */     Connection connection = this.m_db.getODS2Connection();
/* 505 */     PreparedStatement preparedStatement = connection.prepareStatement("with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status in ('Final','Ready for Review') group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in (select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status  in ('Final','Ready for Review') and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and valto > current timestamp ))select  r.entitytype,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,p.MKTGNAME,f.MKTGNAME as FMKTGNAME,p.ANNDATE,p.WITHDRAWDATE,p.WTHDRWEFFCTVDATE,p.INSTALL,p.STATUS,p.VALFROM from OPICM.MODEL m join opicm.relator r on r.entitytype='PRODSTRUCT' and r.entity2id=m.entityid join opicm.feature f on f.entityid=r.entity1id and f.status in ('Final','Ready for Review') and f.nlsid=1 join opicm.prodstruct p on p.entityid=r.entityid and p.nlsid=1 and p.status in ('Final','Ready for Review') left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1 where  p.VALFROM>= ? and p.VALFROM < ? and m.status in ('Final','Ready for Review') and  m.nlsid=1  and m.entityid not in (select id from dump1)  and (S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) with ur", 1004, 1007);
/*     */     
/* 507 */     preparedStatement.setString(1, this.t1);
/* 508 */     preparedStatement.setString(2, this.t2);
/* 509 */     System.out.println("query:with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status in ('Final','Ready for Review') group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in (select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status  in ('Final','Ready for Review') and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and valto > current timestamp ))select  r.entitytype,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,p.MKTGNAME,f.MKTGNAME as FMKTGNAME,p.ANNDATE,p.WITHDRAWDATE,p.WTHDRWEFFCTVDATE,p.INSTALL,p.STATUS,p.VALFROM from OPICM.MODEL m join opicm.relator r on r.entitytype='PRODSTRUCT' and r.entity2id=m.entityid join opicm.feature f on f.entityid=r.entity1id and f.status in ('Final','Ready for Review') and f.nlsid=1 join opicm.prodstruct p on p.entityid=r.entityid and p.nlsid=1 and p.status in ('Final','Ready for Review') left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1 where  p.VALFROM>= ? and p.VALFROM < ? and m.status in ('Final','Ready for Review') and  m.nlsid=1  and m.entityid not in (select id from dump1)  and (S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) with ur:" + this.t1 + ":" + this.t2);
/* 510 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 511 */     int i = 0;
/* 512 */     if (resultSet.last()) {
/* 513 */       i = resultSet.getRow();
/* 514 */       outputStreamWriter.write(this.type + "EACM" + str5 + i);
/* 515 */       outputStreamWriter.write("\n");
/*     */ 
/*     */       
/* 518 */       resultSet.first();
/*     */     } 
/* 520 */     if (i == 0) {
/* 521 */       outputStreamWriter.write(this.type + "EACM" + str5 + i);
/* 522 */       outputStreamWriter.write("\n");
/* 523 */       outputStreamWriter.close();
/*     */       
/*     */       return;
/*     */     } 
/*     */     while (true) {
/* 528 */       for (byte b = 0; b < PRODCOLUMNS.length; b++) {
/* 529 */         String str = resultSet.getString(PRODCOLUMNS[b]);
/* 530 */         if ("MKTGNAME".equals(PRODCOLUMNS[b]) && (
/* 531 */           str == null || str.equals(""))) {
/* 532 */           str = resultSet.getString("FMKTGNAME");
/*     */         }
/*     */         
/* 535 */         outputStreamWriter.write(getValue(str, PRODCOLUMNS[b]));
/* 536 */         if ("INSTALL".equals(PRODCOLUMNS[b])) {
/* 537 */           if ("CE".equals(str)) {
/* 538 */             outputStreamWriter.write("IBI");
/* 539 */           } else if ("CIF".equals(str)) {
/*     */             
/* 541 */             outputStreamWriter.write("CSU");
/*     */           } else {
/* 543 */             outputStreamWriter.write("   ");
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 548 */       outputStreamWriter.write("\n");
/* 549 */       outputStreamWriter.flush();
/* 550 */       if (!resultSet.next()) {
/* 551 */         outputStreamWriter.flush();
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void generateSWProd() throws IOException, MiddlewareException, SQLException {
/* 557 */     FileOutputStream fileOutputStream = new FileOutputStream(this.swFileName);
/*     */ 
/*     */ 
/*     */     
/* 561 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "ASCII");
/*     */ 
/*     */     
/* 564 */     String str1 = getNow();
/* 565 */     String str2 = str1.substring(0, 10);
/* 566 */     String str3 = str1.substring(11, 19);
/* 567 */     String str4 = str1.substring(19);
/* 568 */     str3 = str3.replace('.', ':');
/* 569 */     String str5 = str2 + " " + str3 + str4;
/*     */     
/* 571 */     Connection connection = this.m_db.getODS2Connection();
/* 572 */     PreparedStatement preparedStatement = connection.prepareStatement("with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status in ('Final','Ready for Review') group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in (select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status  in ('Final','Ready for Review') and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and valto > current timestamp )),temp as (select sw.entitytype ,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,sw.MKTGNAME,f.INVNAME as FMKTGNAME,coalesce(annp.ANNDATE,'9999-12-32') AS PANNDATE,coalesce( annl.ANNDATE,'9999-12-32') as LANNDATE,coalesce(a1.EFFECTIVEDATE,'9999-12-32') as EFFECTIVEDATE,sw.STATUS,sw.VALFROM,max(coalesce(sw.VALFROM,'1980-01-01 00:00:00.000000') ,coalesce(a1.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(a.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annp.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annl.VALFROM,'1980-01-01 00:00:00.000000')) as MAXDATA from opicm.SWPRODSTRUCT sw join opicm.relator r2 on r2.entitytype = 'SWPRODSTRUCT' and r2.entityid = sw.entityid join opicm.model m on m.nlsid =1 and m.entityid = r2.entity2id and m.status in ('Final','Ready for Review') join opicm.swfeature f on f.nlsid =1 and f.entityid = r2.entity1id and f.status in ('Final','Ready for Review') left join  opicm.relator r on  r.entitytype = 'SWPRODSTRUCTAVAIL' and r.entity1id = sw.entityid left join  opicm.avail a on a.entityid = r.entity2id and a.nlsid=1 and  a.AVAILTYPE='Planned Availability' and a.status in ('Final','Ready for Review') left join opicm.announcement annp on  a.anncodename=annp.anncodename and annp.nlsid=1 and annp.ANNSTATUS  in ('Final','Ready for Review') left join  opicm.relator r1 on  r1.entitytype = 'SWPRODSTRUCTAVAIL' and r1.entity1id = sw.entityid left join  opicm.avail a1 on a1.entityid = r1.entity2id and a1.nlsid=1 and  a1.AVAILTYPE='Last Order'  AND a1.status in ('Final','Ready for Review')  left join opicm.announcement annl on  a1.anncodename=annl.anncodename and annl.nlsid=1  and annl.ANNSTATUS  in ('Final','Ready for Review') left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1 where  sw.status in ('Final','Ready for Review') and sw.nlsid=1  and (S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) and m.entityid not in (select id from dump1) )  select distinct entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,FMKTGNAME,(case min(PANNDATE) when '9999-12-32' then '' else min(PANNDATE) end )as PANNDATE,(case min(LANNDATE) when '9999-12-32' then '' else min(LANNDATE) end ) as LANNDATE,(case min(EFFECTIVEDATE) when '9999-12-32' then '' else min(EFFECTIVEDATE) end ) as EFFECTIVEDATE,STATUS,VALFROM from temp group by entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,FMKTGNAME,VALFROM,STATUS having max(MAXDATA) >= ? and max(MAXDATA) < ?  with ur", 1004, 1007);
/*     */     
/* 574 */     preparedStatement.setString(1, this.t1);
/* 575 */     preparedStatement.setString(2, this.t2);
/* 576 */     System.out.println("query:with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status in ('Final','Ready for Review') group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in (select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status  in ('Final','Ready for Review') and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and valto > current timestamp )),temp as (select sw.entitytype ,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,sw.MKTGNAME,f.INVNAME as FMKTGNAME,coalesce(annp.ANNDATE,'9999-12-32') AS PANNDATE,coalesce( annl.ANNDATE,'9999-12-32') as LANNDATE,coalesce(a1.EFFECTIVEDATE,'9999-12-32') as EFFECTIVEDATE,sw.STATUS,sw.VALFROM,max(coalesce(sw.VALFROM,'1980-01-01 00:00:00.000000') ,coalesce(a1.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(a.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annp.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annl.VALFROM,'1980-01-01 00:00:00.000000')) as MAXDATA from opicm.SWPRODSTRUCT sw join opicm.relator r2 on r2.entitytype = 'SWPRODSTRUCT' and r2.entityid = sw.entityid join opicm.model m on m.nlsid =1 and m.entityid = r2.entity2id and m.status in ('Final','Ready for Review') join opicm.swfeature f on f.nlsid =1 and f.entityid = r2.entity1id and f.status in ('Final','Ready for Review') left join  opicm.relator r on  r.entitytype = 'SWPRODSTRUCTAVAIL' and r.entity1id = sw.entityid left join  opicm.avail a on a.entityid = r.entity2id and a.nlsid=1 and  a.AVAILTYPE='Planned Availability' and a.status in ('Final','Ready for Review') left join opicm.announcement annp on  a.anncodename=annp.anncodename and annp.nlsid=1 and annp.ANNSTATUS  in ('Final','Ready for Review') left join  opicm.relator r1 on  r1.entitytype = 'SWPRODSTRUCTAVAIL' and r1.entity1id = sw.entityid left join  opicm.avail a1 on a1.entityid = r1.entity2id and a1.nlsid=1 and  a1.AVAILTYPE='Last Order'  AND a1.status in ('Final','Ready for Review')  left join opicm.announcement annl on  a1.anncodename=annl.anncodename and annl.nlsid=1  and annl.ANNSTATUS  in ('Final','Ready for Review') left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1 where  sw.status in ('Final','Ready for Review') and sw.nlsid=1  and (S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) and m.entityid not in (select id from dump1) )  select distinct entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,FMKTGNAME,(case min(PANNDATE) when '9999-12-32' then '' else min(PANNDATE) end )as PANNDATE,(case min(LANNDATE) when '9999-12-32' then '' else min(LANNDATE) end ) as LANNDATE,(case min(EFFECTIVEDATE) when '9999-12-32' then '' else min(EFFECTIVEDATE) end ) as EFFECTIVEDATE,STATUS,VALFROM from temp group by entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,FMKTGNAME,VALFROM,STATUS having max(MAXDATA) >= ? and max(MAXDATA) < ?  with ur:" + this.t1 + ":" + this.t2);
/* 577 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 578 */     int i = 0;
/* 579 */     if (resultSet.last()) {
/* 580 */       i = resultSet.getRow();
/* 581 */       outputStreamWriter.write(this.type + "EACM" + str5 + i);
/* 582 */       outputStreamWriter.write("\n");
/*     */       
/* 584 */       resultSet.first();
/*     */     } 
/* 586 */     if (i == 0) {
/* 587 */       outputStreamWriter.write(this.type + "EACM" + str5 + i);
/* 588 */       outputStreamWriter.write("\n");
/* 589 */       outputStreamWriter.close();
/*     */       
/*     */       return;
/*     */     } 
/*     */     while (true) {
/* 594 */       for (byte b = 0; b < SWPRODCOLUMNS.length; b++) {
/* 595 */         String str = resultSet.getString(SWPRODCOLUMNS[b]);
/* 596 */         if ("MKTGNAME".equals(SWPRODCOLUMNS[b]) && (
/* 597 */           str == null || str.equals(""))) {
/* 598 */           str = resultSet.getString("FMKTGNAME");
/*     */         }
/*     */         
/* 601 */         outputStreamWriter.write(getValue(str, SWPRODCOLUMNS[b]));
/*     */       } 
/* 603 */       outputStreamWriter.write("\n");
/* 604 */       outputStreamWriter.flush();
/* 605 */       if (!resultSet.next()) {
/* 606 */         outputStreamWriter.flush();
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   } private void generateFlatFile() throws IOException, MiddlewareException, SQLException {
/*     */     try {
/* 612 */       generateModel();
/* 613 */       this.sentFile = exeFtpShell(this.modelFileName, this.tmodel, "'SMS.UTIL.CNTL(CSF0005)'");
/* 614 */       generateProd();
/* 615 */       this.sentFile = exeFtpShell(this.prodFileName, this.tprod, "'SMS.UTIL.CNTL(CSF0006)'");
/* 616 */       generateSWProd();
/* 617 */       this.sentFile = exeFtpShell(this.swFileName, this.tswprod, "'SMS.UTIL.CNTL(CSF0007)'");
/* 618 */       setTextValue(this.m_elist.getProfile(), "IBIDATADTS", this.t2, this.rootEntity);
/* 619 */     } catch (Exception exception) {
/* 620 */       exception.printStackTrace();
/* 621 */       this.sentFile = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getValue(String paramString1, String paramString2) {
/* 628 */     paramString1 = remove(paramString1);
/*     */     try {
/* 630 */       paramString1 = new String(paramString1.getBytes("UTF-8"), "ASCII");
/* 631 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       
/* 633 */       unsupportedEncodingException.printStackTrace();
/*     */     } 
/* 635 */     int i = (paramString1 == null) ? 0 : paramString1.length();
/* 636 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString2).toString());
/* 637 */     if (i == j)
/* 638 */       return paramString1; 
/* 639 */     if (i > j) {
/* 640 */       return paramString1.substring(0, j);
/*     */     }
/* 642 */     return paramString1 + getBlank(j - i);
/*     */   }
/*     */   
/*     */   public String replaceBlank(String paramString) {
/* 646 */     String str = "";
/* 647 */     if (paramString != null) {
/*     */       
/* 649 */       Matcher matcher = this.p.matcher(paramString);
/* 650 */       str = matcher.replaceAll("");
/*     */     } 
/* 652 */     return str;
/*     */   }
/*     */   
/*     */   protected String getBlank(int paramInt) {
/* 656 */     StringBuffer stringBuffer = new StringBuffer();
/* 657 */     while (paramInt > 0) {
/* 658 */       stringBuffer.append(" ");
/* 659 */       paramInt--;
/*     */     } 
/* 661 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exeFtpShell(String paramString1, String paramString2, String paramString3) {
/* 669 */     boolean bool = false;
/*     */     
/* 671 */     if (!bool)
/*     */     {
/*     */       
/* 674 */       return true;
/*     */     }
/*     */     
/* 677 */     String str1 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_sftpscrpath", null) + " -f " + paramString1 + " -t '" + paramString2 + "' -c " + paramString3;
/* 678 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_ibiinipath", null);
/* 679 */     if (str2 != null)
/* 680 */       str1 = str1 + " -i " + str2; 
/* 681 */     Runtime runtime = Runtime.getRuntime();
/* 682 */     String str3 = "";
/* 683 */     BufferedReader bufferedReader = null;
/* 684 */     BufferedInputStream bufferedInputStream = null;
/* 685 */     System.out.println("cmd:" + str1);
/*     */     try {
/* 687 */       Process process = runtime.exec(str1);
/* 688 */       if (process.waitFor() != 0) {
/* 689 */         return false;
/*     */       }
/* 691 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 692 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 693 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 694 */         str3 = str3 + this.lineStr;
/* 695 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 696 */           return false;
/*     */         }
/*     */       } 
/* 699 */     } catch (Exception exception) {
/* 700 */       exception.printStackTrace();
/* 701 */       return false;
/*     */     } finally {
/* 703 */       if (bufferedReader != null) {
/*     */         try {
/* 705 */           bufferedReader.close();
/* 706 */           bufferedInputStream.close();
/* 707 */         } catch (IOException iOException) {
/* 708 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 712 */     return !(str3 == null);
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 716 */     return "IBIDATAABRSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTextValue(Profile paramProfile, String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 725 */     logMessage(
/* 726 */         getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*     */ 
/*     */     
/* 729 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 730 */     if (eANMetaAttribute == null) {
/* 731 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 736 */     if (paramString2 != null) {
/* 737 */       if (this.m_cbOn == null) {
/* 738 */         setControlBlock();
/*     */       }
/* 740 */       ControlBlock controlBlock = this.m_cbOn;
/* 741 */       if (paramString2.length() == 0) {
/* 742 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 743 */         String str = eANAttribute.getEffFrom();
/* 744 */         controlBlock = new ControlBlock(str, str, str, str, paramProfile.getOPWGID());
/* 745 */         paramString2 = eANAttribute.toString();
/*     */       } 
/* 747 */       Vector<Text> vector = new Vector();
/* 748 */       Vector<ReturnEntityKey> vector1 = new Vector();
/*     */       
/* 750 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 751 */       returnEntityKey.m_vctAttributes = vector;
/* 752 */       vector1.addElement(returnEntityKey);
/*     */ 
/*     */       
/* 755 */       Text text = new Text(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, controlBlock);
/* 756 */       vector.addElement(text);
/*     */       try {
/* 758 */         this.m_db.update(paramProfile, vector1);
/* 759 */       } catch (MiddlewareBusinessRuleException middlewareBusinessRuleException) {
/*     */         
/* 761 */         middlewareBusinessRuleException.printStackTrace();
/* 762 */       } catch (SQLException sQLException) {
/*     */         
/* 764 */         sQLException.printStackTrace();
/* 765 */       } catch (MiddlewareException middlewareException) {
/*     */         
/* 767 */         middlewareException.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/* 775 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*     */     
/* 777 */     return this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, 
/* 778 */             getEntityType(), getEntityID()) });
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
/*     */   public String getABRVersion() {
/* 791 */     return "1.0";
/*     */   }
/*     */ 
/*     */   
/*     */   public String remove(String paramString) {
/* 796 */     paramString = replaceBlank(paramString).trim().replace(this.UTFSpace, " ");
/* 797 */     paramString = replaceBlank(paramString).trim().replace(this.UTFSpace, " ");
/* 798 */     Iterator<E> iterator = this.mapping.keySet().iterator();
/*     */     
/* 800 */     while (iterator.hasNext()) {
/* 801 */       String str = iterator.next().toString();
/* 802 */       paramString = paramString.replace(str, ((String)this.mapping.get(str)).toString());
/*     */     } 
/*     */     
/* 805 */     return paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\IBIDATAABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */