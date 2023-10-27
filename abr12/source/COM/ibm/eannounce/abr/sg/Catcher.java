/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.objects.Blob;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*     */ import com.ibm.mq.MQException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.Connection;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ public class Catcher {
/*     */   ProfileSet psLogin;
/*  38 */   private static Connection m_conPDH = null; public Profile m_prof; private Database m_dbPDH;
/*     */   private String m_strNow;
/*     */   private String m_strTimeStampForever;
/*  41 */   private static SimpleDateFormat c_sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
/*     */   
/*  43 */   String UPDATEAL = null;
/*     */   EntityItem m_pdhi;
/*     */   String m_strSchemaPDH;
/*     */   String m_strEnterprise;
/*     */   String m_strRunMode;
/*     */   String m_strLdapId;
/*     */   String m_strLdapPwd;
/*     */   String m_strOPICMVersion;
/*     */   String m_domainAttr;
/*     */   String EntityType;
/*     */   String EntityIDs;
/*     */   String LockAction;
/*     */   String FlagAttr;
/*     */   String QueueABRSTATUS;
/*     */   String abrValue;
/*     */   String LongMSG;
/*     */   
/*     */   public Catcher() {
/*  61 */     this.psLogin = null;
/*  62 */     this.m_prof = null;
/*  63 */     this.m_dbPDH = null;
/*  64 */     this.m_strNow = null;
/*  65 */     this.m_strTimeStampForever = null;
/*  66 */     this.m_pdhi = null;
/*  67 */     this.m_strSchemaPDH = null;
/*  68 */     this.m_strEnterprise = null;
/*  69 */     this.m_strRunMode = null;
/*  70 */     this.m_strLdapId = null;
/*  71 */     this.m_strLdapPwd = null;
/*  72 */     this.m_strOPICMVersion = null;
/*  73 */     this.m_domainAttr = null;
/*  74 */     this.EntityType = null;
/*  75 */     this.EntityIDs = null;
/*  76 */     this.LockAction = null;
/*  77 */     this.FlagAttr = "PDHDOMAIN";
/*  78 */     this.LongMSG = null;
/*  79 */     this.QueueABRSTATUS = null;
/*  80 */     this.abrValue = null;
/*     */     
/*  82 */     System.out.println("HI");
/*     */   }
/*     */   
/*     */   public void readPropertyFile() {
/*  86 */     printOK("Reading Catcher.properties");
/*  87 */     Properties properties = new Properties();
/*     */     try {
/*  89 */       properties.load(new FileInputStream("Catcher.properties"));
/*     */     }
/*  91 */     catch (IOException iOException) {
/*  92 */       printErr("Error reading Catcher.properties");
/*  93 */       System.exit(1);
/*     */     } 
/*  95 */     this.m_strSchemaPDH = properties.getProperty("PDHSCHEMA");
/*  96 */     this.m_strEnterprise = properties.getProperty("ENTERPRISE");
/*  97 */     this.m_strRunMode = properties.getProperty("RUNMODE");
/*  98 */     this.m_strLdapId = properties.getProperty("LDAPID", null);
/*  99 */     this.m_strLdapPwd = properties.getProperty("LDAPPWD", null);
/* 100 */     this.m_strOPICMVersion = properties.getProperty("OPICMVERSION", null);
/* 101 */     String str1 = properties.getProperty("ENTITYTYPE", null);
/* 102 */     String str2 = properties.getProperty("ENTITYID", null);
/* 103 */     this.m_domainAttr = properties.getProperty("PDHDOMAIN", null);
/* 104 */     this.QueueABRSTATUS = properties.getProperty("ABRQUEUESTATUS", null);
/* 105 */     this.LongMSG = properties.getProperty("LONGMSG", null);
/* 106 */     this.UPDATEAL = properties.getProperty("ROLECODE", null);
/* 107 */     this.abrValue = properties.getProperty("QUEUEVALUE", null);
/*     */     
/* 109 */     printOK("****Catcher Properties are:" + this.m_strSchemaPDH + " " + this.m_strEnterprise + " " + this.m_strRunMode + " " + this.m_strLdapId + " " + this.m_strLdapPwd + " " + this.m_strOPICMVersion + " " + str1 + " " + str2 + " " + this.m_domainAttr + " " + this.QueueABRSTATUS + " " + this.LongMSG + " " + this.UPDATEAL + " " + this.abrValue);
/* 110 */     this.EntityType = str1;
/* 111 */     this.EntityIDs = str2;
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws Exception {
/* 115 */     createRptFolder();
/* 116 */     adjustSystemOut();
/* 117 */     long l1 = System.currentTimeMillis();
/* 118 */     System.out.println("**** 11: " + System.currentTimeMillis());
/* 119 */     Catcher catcher = new Catcher();
/* 120 */     if (paramArrayOfString != null && paramArrayOfString.length > 0) {
/* 121 */       if ("testDB".equals(paramArrayOfString[0])) {
/* 122 */         catcher.connectPDH();
/* 123 */         printOK("testDB done!");
/* 124 */       } else if ("testMQ".equals(paramArrayOfString[0])) {
/* 125 */         Vector vector = catcher.getAllMQMessage();
/* 126 */         printOK("testMQ" + vector.toString());
/* 127 */         vector.clear();
/* 128 */       } else if ("test".equals(paramArrayOfString[0])) {
/* 129 */         catcher.connectPDH();
/* 130 */         catcher.test();
/*     */       } 
/*     */     } else {
/* 133 */       catcher.connectPDH();
/* 134 */       Vector vector = catcher.getAllMQMessage();
/* 135 */       catcher.createXMLMSG(vector);
/* 136 */       vector.clear();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 141 */     long l2 = System.currentTimeMillis();
/* 142 */     System.out.println("**** 12: " + System.currentTimeMillis());
/* 143 */     long l3 = Math.abs(l1 - l2);
/* 144 */     System.out.println("**** diff: " + l3);
/* 145 */     long l4 = Math.round((float)(l3 / 1000L));
/* 146 */     System.out.println("number of seconds has passed: " + l4);
/*     */   }
/*     */   
/*     */   private void connectPDH() {
/* 150 */     printOK("getconnection to PDH");
/*     */     try {
/* 152 */       readPropertyFile();
/* 153 */       Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
/* 154 */       System.out.println("***DB settings:" + this.m_strLdapId + this.m_strLdapPwd + this.m_strOPICMVersion);
/* 155 */       this.m_dbPDH = new Database();
/* 156 */       this.m_dbPDH.connect();
/* 157 */       if (this.m_dbPDH == null) {
/* 158 */         System.out.println("bad db" + this.m_dbPDH);
/*     */       }
/* 160 */       this.m_dbPDH.getNow();
/* 161 */       DatePackage datePackage = this.m_dbPDH.getDates();
/* 162 */       this.m_strNow = datePackage.getNow();
/* 163 */       this.m_strTimeStampForever = datePackage.getForever();
/* 164 */       this.psLogin = this.m_dbPDH.login(this.m_strLdapId, this.m_strLdapPwd, this.m_strOPICMVersion);
/* 165 */       if (this.psLogin == null) {
/* 166 */         m_conPDH.close();
/* 167 */         System.exit(1);
/*     */       } 
/* 169 */       printOK("Logged in via ldap....Profile set is " + this.psLogin.size() + " RoleCode:" + this.UPDATEAL + " Enterprice :" + this.m_strEnterprise);
/* 170 */       for (byte b = 0; b < this.psLogin.size(); b++) {
/* 171 */         Profile profile = this.psLogin.elementAt(b);
/* 172 */         if (profile.getRoleCode().trim().equals((this.UPDATEAL != null) ? this.UPDATEAL.trim() : null) && profile.getEnterprise().trim().equals(this.m_strEnterprise.trim())) {
/* 173 */           this.psLogin.setActiveProfile(b);
/* 174 */           this.m_prof = this.psLogin.getActiveProfile();
/* 175 */           this.m_prof.setValOn(this.m_strNow);
/* 176 */           this.m_prof.setEffOn(this.m_strNow);
/* 177 */           printOK("set role: " + profile.getRoleCode() + ", OPWGID: " + profile.getOPWGID() + ", enterprise: " + profile.getEnterprise() + ", at i " + b);
/*     */           break;
/*     */         } 
/* 180 */         printOK("role: " + profile.getRoleCode() + ", OPWGID: " + profile.getOPWGID() + ", enterprise: " + profile.getEnterprise() + ", at i " + b);
/*     */       } 
/*     */       
/* 183 */       printOK("Logged in via ldap: " + this.m_strLdapId + ", role: " + this.m_prof.getRoleCode() + ", OPWGID: " + this.m_prof.getOPWGID() + ", OP: " + this.m_prof.getOPID() + ", enterprise: " + this.m_prof.getEnterprise());
/* 184 */       printOK("TimeStampNow is " + this.m_strNow + ", TimeStampForever is " + this.m_strTimeStampForever);
/* 185 */       System.out.println("***" + this.m_strNow + this.m_strTimeStampForever + this.m_prof.getOPWGID() + this.m_prof.getTranID());
/* 186 */     } catch (Exception exception) {
/* 187 */       printErr("closeConnection Exception " + exception.getMessage());
/* 188 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createXMLMSG(Vector<E> paramVector) throws MiddlewareException {
/* 193 */     if (paramVector != null) {
/*     */       try {
/* 195 */         System.out.println("Creating XML************************************");
/* 196 */         ControlBlock controlBlock = new ControlBlock(this.m_strNow, this.m_strTimeStampForever, this.m_strNow, this.m_strTimeStampForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/* 197 */         EntityItem entityItem = new EntityItem(null, this.m_prof, this.EntityType, 0);
/* 198 */         System.out.println("eiParm:" + entityItem.getEntityType() + entityItem.getEntityID());
/* 199 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(this.EntityType, 0, true);
/* 200 */         System.out.println("rek:" + returnEntityKey);
/*     */         
/* 202 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 203 */           String str1 = paramVector.get(b).toString();
/*     */           
/* 205 */           String str2 = str1;
/*     */           
/* 207 */           String str3 = "SPSTinbound.xml";
/* 208 */           Blob blob = new Blob(this.m_strEnterprise, this.EntityType, 0, this.LongMSG, str2, str3, 1, controlBlock);
/* 209 */           SingleFlag singleFlag1 = new SingleFlag(this.m_strEnterprise, this.EntityType, returnEntityKey.getEntityID(), this.FlagAttr, this.m_domainAttr, 1, controlBlock);
/*     */           
/* 211 */           SingleFlag singleFlag2 = new SingleFlag(this.m_strEnterprise, this.EntityType, returnEntityKey.getEntityID(), this.QueueABRSTATUS, this.abrValue, 1, controlBlock);
/* 212 */           System.out.println("lt:" + blob.getEnterprise() + "|" + blob.getEntityType() + "|" + blob.getEntityID() + "|" + blob.getAttributeCode());
/* 213 */           System.out.println("sf:" + singleFlag1.getEnterprise() + "|" + singleFlag1.getEntityType() + "|" + singleFlag1.getEntityID() + "|" + singleFlag1.getAttributeCode() + "|" + singleFlag1.getAttributeValue());
/* 214 */           System.out.println("sf1:" + singleFlag2.getEnterprise() + "|" + singleFlag2.getEntityType() + "|" + singleFlag2.getEntityID() + "|" + singleFlag2.getAttributeCode() + "|" + singleFlag2.getAttributeValue());
/* 215 */           Vector<Blob> vector = new Vector();
/* 216 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 217 */           if (blob != null) {
/* 218 */             vector.addElement(blob);
/* 219 */             vector.addElement(singleFlag1);
/* 220 */             vector.addElement(singleFlag2);
/*     */             
/* 222 */             returnEntityKey.m_vctAttributes = vector;
/*     */             
/* 224 */             vector1.addElement(returnEntityKey);
/* 225 */             OPICMList oPICMList = this.m_dbPDH.update(this.m_prof, vector1, false, false);
/*     */             
/* 227 */             returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/*     */ 
/*     */             
/* 230 */             System.out.println("***Entityid: " + returnEntityKey.getReturnID());
/* 231 */             this.m_dbPDH.commit();
/*     */           } 
/*     */         } 
/*     */         
/* 235 */         System.out.println("End************************************");
/*     */       
/*     */       }
/* 238 */       catch (Exception exception) {
/* 239 */         printErr("closeConnection Exception " + exception.getMessage());
/* 240 */         exception.printStackTrace();
/* 241 */         printErr("Get message from MQ error:" + paramVector.toString());
/*     */       } 
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
/* 261 */       System.out.println("Closing Connection");
/* 262 */       this.m_dbPDH.close();
/* 263 */       this.m_dbPDH = null;
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
/*     */   private static void printOK(String paramString) {
/* 288 */     System.out.println("ok: " + paramString);
/*     */   }
/*     */   
/*     */   private static void printErr(String paramString) {
/* 292 */     System.err.println("Err: " + paramString);
/*     */   }
/*     */   
/*     */   private static void createRptFolder() {
/* 296 */     File file = new File("rpt");
/* 297 */     if (file.exists()) {
/*     */       return;
/*     */     }
/* 300 */     file.mkdir();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void adjustSystemOut() {
/* 306 */     String str1 = new String();
/* 307 */     String str2 = c_sdfTimestamp.format(new Date());
/* 308 */     str1 = "rpt/Catcher" + str2 + ".log";
/* 309 */     String str3 = new String();
/* 310 */     String str4 = c_sdfTimestamp.format(new Date());
/* 311 */     str3 = "rpt/Catcher" + str4 + ".error.log";
/*     */     try {
/* 313 */       FileOutputStream fileOutputStream1 = new FileOutputStream(str1, true);
/* 314 */       PrintStream printStream1 = new PrintStream(fileOutputStream1);
/* 315 */       System.setOut(printStream1);
/* 316 */       FileOutputStream fileOutputStream2 = new FileOutputStream(str3, true);
/* 317 */       PrintStream printStream2 = new PrintStream(fileOutputStream2);
/* 318 */       System.setErr(printStream2);
/*     */     }
/* 320 */     catch (Exception exception) {}
/*     */   }
/*     */   
/*     */   public static final String getVersion() {
/* 324 */     return new String("$Tester$");
/*     */   }
/*     */   
/*     */   static {
/* 328 */     D.isplay("Catcher Class Engine:" + getVersion());
/*     */   }
/*     */   
/*     */   public Vector getAllMQMessage() {
/* 332 */     Vector vector = new Vector();
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
/* 348 */     String str = new String("MQSERIES.properties");
/* 349 */     Properties properties = null;
/*     */     try {
/* 351 */       if (properties == null) {
/* 352 */         properties = new Properties();
/* 353 */         FileInputStream fileInputStream = new FileInputStream("./" + str);
/*     */         
/* 355 */         properties.load(fileInputStream);
/* 356 */         fileInputStream.close();
/*     */       } 
/* 358 */     } catch (Exception exception) {
/*     */       
/* 360 */       printErr("Unable to loadProperties for " + str + " " + exception);
/*     */     } 
/*     */     
/* 363 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 364 */     hashtable.put("NOTIFY", new Boolean(properties.getProperty("NOTIFY", "")));
/* 365 */     hashtable.put("MQUSERID", properties.getProperty("MQUSERID", ""));
/* 366 */     hashtable.put("MQPASSWORD", properties.getProperty("MQPASSWORD", ""));
/* 367 */     hashtable.put("MQPORT", properties.getProperty("MQPORT", ""));
/* 368 */     hashtable.put("MQHOSTNAME", properties.getProperty("MQHOSTNAME", ""));
/* 369 */     hashtable.put("MQCHANNEL", properties.getProperty("MQCHANNEL", ""));
/* 370 */     hashtable.put("MQMANAGER", properties.getProperty("MQMANAGER", ""));
/* 371 */     hashtable.put("MQQUEUE", properties.getProperty("MQQUEUE", ""));
/* 372 */     hashtable.put("MQSSL", properties.getProperty("MQSSL", ""));
/* 373 */     hashtable.put("KSTORE", properties.getProperty("KSTORE", ""));
/* 374 */     hashtable.put("KSPASSWORD", properties.getProperty("KSPASSWORD", ""));
/* 375 */     hashtable.put("TSTORE", properties.getProperty("TSTORE", ""));
/* 376 */     hashtable.put("TSPASSWORD", properties.getProperty("TSPASSWORD", ""));
/* 377 */     System.out.println("MQPORT:" + properties.getProperty("MQPORT", "").toString() + "MQHOSTNAME:" + properties
/* 378 */         .getProperty("MQHOSTNAME", "").toString() + "MQCHANNEL:" + properties
/* 379 */         .getProperty("MQCHANNEL", "").toString() + "MQMANAGER:" + properties
/* 380 */         .getProperty("MQMANAGER", "").toString() + "MQQUEUE:" + properties
/* 381 */         .getProperty("MQQUEUE", "").toString());
/*     */     try {
/* 383 */       vector = MQUsage.GetAllMQQueue(hashtable);
/*     */     }
/* 385 */     catch (MQException mQException) {
/* 386 */       printErr("Get message from MQ error:" + vector.toString());
/* 387 */       mQException.printStackTrace();
/* 388 */     } catch (IOException iOException) {
/*     */       
/* 390 */       iOException.printStackTrace();
/* 391 */       printErr("Get message from MQ error:" + vector.toString());
/*     */     } 
/*     */     
/* 394 */     return vector;
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
/*     */   public static String TransformerXML(Document paramDocument) {
/* 407 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */     
/* 409 */     String str = "";
/*     */     try {
/* 411 */       Transformer transformer = transformerFactory.newTransformer();
/* 412 */       transformer.setOutputProperty("omit-xml-declaration", "yes");
/*     */       
/* 414 */       transformer.setOutputProperty("indent", "no");
/* 415 */       transformer.setOutputProperty("method", "xml");
/* 416 */       transformer.setOutputProperty("encoding", "UTF-8");
/*     */ 
/*     */       
/* 419 */       StringWriter stringWriter = new StringWriter();
/* 420 */       StreamResult streamResult = new StreamResult(stringWriter);
/* 421 */       DOMSource dOMSource = new DOMSource(paramDocument);
/* 422 */       transformer.transform(dOMSource, streamResult);
/* 423 */       str = stringWriter.toString();
/* 424 */       str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + str;
/* 425 */       printOK("XMLString: " + str);
/*     */     }
/* 427 */     catch (TransformerConfigurationException transformerConfigurationException) {
/*     */       
/* 429 */       transformerConfigurationException.printStackTrace();
/* 430 */     } catch (TransformerException transformerException) {
/*     */       
/* 432 */       transformerException.printStackTrace();
/*     */     } 
/* 434 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   void test() throws SAXException, IOException, ParserConfigurationException, MiddlewareException {
/* 439 */     String str = null;
/* 440 */     Document document = null;
/* 441 */     Vector<String> vector = new Vector();
/* 442 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 443 */     File file = new File("test.xml");
/* 444 */     printOK("beging test read test.xml is :" + ((file == null) ? "null" : "in not null"));
/* 445 */     document = documentBuilderFactory.newDocumentBuilder().parse(file);
/* 446 */     str = TransformerXML(document);
/* 447 */     vector.add(str);
/* 448 */     createXMLMSG(vector);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\Catcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */