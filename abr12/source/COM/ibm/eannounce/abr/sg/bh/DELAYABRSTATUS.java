/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*     */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
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
/*     */ public class DELAYABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  83 */   private StringBuffer rptSb = new StringBuffer();
/*  84 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  85 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  86 */   private Object[] args = (Object[])new String[10];
/*     */   
/*  88 */   private Hashtable metaTbl = new Hashtable<>();
/*  89 */   private String navName = "";
/*     */   
/*  91 */   private Vector vctReturnsEntityKeys = new Vector();
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
/*     */   private static final String ABR_QUEUED = "0020";
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
/*     */   public void execute_run() {
/* 116 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/* 118 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
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
/* 129 */     String str3 = "";
/* 130 */     String str4 = "";
/*     */     
/* 132 */     println(EACustom.getDocTypeHtml());
/*     */ 
/*     */     
/*     */     try {
/* 136 */       long l = System.currentTimeMillis();
/*     */       
/* 138 */       setReturnCode(0);
/*     */       
/* 140 */       start_ABRBuild(false);
/*     */ 
/*     */       
/* 143 */       setCreateDGEntity(false);
/*     */ 
/*     */       
/* 146 */       String str6 = this.m_db.getDates().getNow();
/*     */       
/* 148 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, getEntityType(), "Edit");
/* 149 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, getEntityType(), getEntityID());
/*     */       
/* 151 */       String str7 = PokUtils.getAttributeFlagValue(entityItem, "ABRVALUE");
/*     */       
/* 153 */       String str8 = PokUtils.getAttributeValue(entityItem, "ABRMINDELAY", "", "00:00", false);
/*     */       
/* 155 */       addDebug(getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " abrValue: " + str7 + " abrMinDelay: " + str8 + " strNow " + str6);
/*     */       
/* 157 */       if (str8.length() > 5) {
/* 158 */         addError("ABRMINDELAY is invalid " + str8);
/*     */       }
/* 160 */       if (str7 == null) {
/* 161 */         addError("ABRVALUE is invalid " + str7);
/*     */       }
/*     */       
/* 164 */       if (getReturnCode() == 0) {
/* 165 */         ISOCalendar iSOCalendar = new ISOCalendar(str6);
/*     */         
/* 167 */         String str = iSOCalendar.getAdjustedDate(str8);
/* 168 */         addDebug(" updateMinTime " + str);
/*     */ 
/*     */         
/* 171 */         getDelayedEntities(str7, str);
/*     */         
/* 173 */         addDebug("Time to do getDelayedEntities: " + Stopwatch.format(System.currentTimeMillis() - l));
/*     */ 
/*     */         
/* 176 */         this.navName = getNavigationName(entityItem);
/* 177 */         str3 = entityGroup.getLongDescription() + " &quot;" + this.navName + "&quot;";
/*     */ 
/*     */ 
/*     */         
/* 181 */         setTextValue("ABRLASTRAN", str6, entityItem);
/*     */ 
/*     */         
/* 184 */         updatePDH();
/*     */         
/* 186 */         iSOCalendar.dereference();
/*     */       } 
/*     */       
/* 189 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*     */     }
/* 191 */     catch (Throwable throwable) {
/* 192 */       StringWriter stringWriter = new StringWriter();
/* 193 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 194 */       String str7 = "<pre>{0}</pre>";
/* 195 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/* 196 */       setReturnCode(-3);
/* 197 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 199 */       this.args[0] = throwable.getMessage();
/* 200 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 201 */       messageFormat1 = new MessageFormat(str7);
/* 202 */       this.args[0] = stringWriter.getBuffer().toString();
/* 203 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 204 */       logError("Exception: " + throwable.getMessage());
/* 205 */       logError(stringWriter.getBuffer().toString());
/*     */     }
/*     */     finally {
/*     */       
/* 209 */       setDGTitle(this.navName);
/* 210 */       setDGRptName(getShortClassName(getClass()));
/* 211 */       setDGRptClass(getABRCode());
/*     */       
/* 213 */       if (!isReadOnly())
/*     */       {
/* 215 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 221 */     MessageFormat messageFormat = new MessageFormat(str1);
/* 222 */     this.args[0] = getDescription();
/* 223 */     this.args[1] = this.navName;
/* 224 */     String str5 = messageFormat.format(this.args);
/* 225 */     messageFormat = new MessageFormat(str2);
/* 226 */     this.args[0] = this.m_prof.getOPName();
/* 227 */     this.args[1] = this.m_prof.getRoleDescription();
/* 228 */     this.args[2] = this.m_prof.getWGName();
/* 229 */     this.args[3] = getNow();
/* 230 */     this.args[4] = str3;
/* 231 */     this.args[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/* 232 */     this.args[6] = str4 + " " + getABRVersion();
/*     */     
/* 234 */     this.rptSb.insert(0, str5 + messageFormat.format(this.args) + NEWLINE);
/*     */     
/* 236 */     println(this.rptSb.toString());
/* 237 */     printDGSubmitString();
/* 238 */     println(EACustom.getTOUDiv());
/* 239 */     buildReportFooter();
/*     */     
/* 241 */     this.metaTbl.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dereference() {
/* 248 */     super.dereference();
/*     */     
/* 250 */     this.rptSb = null;
/* 251 */     this.args = null;
/*     */     
/* 253 */     this.metaTbl = null;
/* 254 */     this.navName = null;
/* 255 */     if (this.vctReturnsEntityKeys != null) {
/* 256 */       this.vctReturnsEntityKeys.clear();
/* 257 */       this.vctReturnsEntityKeys = null;
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
/*     */   private void queueABR(String paramString1, String paramString2, int paramInt) {
/* 269 */     addDebug("queueABR entered " + paramString2 + paramInt + " for " + paramString1);
/*     */     
/* 271 */     addOutput("Set " + paramString1 + " on " + paramString2 + paramInt + " to Queued.");
/* 272 */     if (this.m_cbOn == null) {
/* 273 */       setControlBlock();
/*     */     }
/* 275 */     Vector<SingleFlag> vector = null;
/*     */     
/* 277 */     for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 278 */       ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 279 */       if (returnEntityKey.getEntityID() == paramInt && returnEntityKey
/* 280 */         .getEntityType().equals(paramString2)) {
/* 281 */         vector = returnEntityKey.m_vctAttributes;
/*     */         break;
/*     */       } 
/*     */     } 
/* 285 */     if (vector == null) {
/* 286 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramString2, paramInt, true);
/* 287 */       vector = new Vector();
/* 288 */       returnEntityKey.m_vctAttributes = vector;
/* 289 */       this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*     */     } 
/*     */     
/* 292 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramString2, paramInt, paramString1, "0020", 1, this.m_cbOn);
/*     */ 
/*     */     
/* 295 */     vector.addElement(singleFlag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setTextValue(String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 306 */     addDebug("setTextValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*     */ 
/*     */     
/* 309 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 310 */     if (eANMetaAttribute == null) {
/* 311 */       addDebug("setTextValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 312 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 313 */           .getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/* 317 */     if (paramString2 != null) {
/* 318 */       if (this.m_cbOn == null) {
/* 319 */         setControlBlock();
/*     */       }
/* 321 */       ControlBlock controlBlock = this.m_cbOn;
/* 322 */       if (paramString2.length() == 0) {
/* 323 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 324 */         String str = eANAttribute.getEffFrom();
/* 325 */         controlBlock = new ControlBlock(str, str, str, str, this.m_prof.getOPWGID());
/* 326 */         paramString2 = eANAttribute.toString();
/* 327 */         addDebug("setTextValue deactivating " + paramString1);
/*     */       } 
/* 329 */       Vector<Text> vector = null;
/*     */       
/* 331 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 332 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 333 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 334 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 335 */           vector = returnEntityKey.m_vctAttributes;
/*     */           break;
/*     */         } 
/*     */       } 
/* 339 */       if (vector == null) {
/*     */         
/* 341 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 342 */         vector = new Vector();
/* 343 */         returnEntityKey.m_vctAttributes = vector;
/* 344 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*     */       } 
/*     */       
/* 347 */       Text text = new Text(this.m_prof.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, controlBlock);
/* 348 */       vector.addElement(text);
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
/*     */   private void updatePDH() throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 362 */     logMessage(getDescription() + " updating PDH");
/* 363 */     addDebug("updatePDH entered for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys);
/*     */     
/* 365 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*     */       try {
/* 367 */         this.m_db.update(this.m_prof, this.vctReturnsEntityKeys, false, false);
/*     */       } finally {
/*     */         
/* 370 */         this.vctReturnsEntityKeys.clear();
/* 371 */         this.m_db.commit();
/* 372 */         this.m_db.freeStatement();
/* 373 */         this.m_db.isPending("finally after updatePDH");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 382 */     return "$Revision: 1.2 $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 389 */     return "DELAYABRSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addOutput(String paramString) {
/* 395 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */   private void addError(String paramString) {
/* 398 */     setReturnCode(-1);
/* 399 */     addOutput(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addDebug(String paramString) {
/* 406 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 415 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 418 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 419 */     if (eANList == null) {
/*     */       
/* 421 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 422 */       eANList = entityGroup.getMetaAttribute();
/* 423 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 425 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 427 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 428 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 429 */       if (b + 1 < eANList.size()) {
/* 430 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/*     */     
/* 434 */     return stringBuffer.toString();
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
/*     */   private void getDelayedEntities(String paramString1, String paramString2) throws MiddlewareException, SQLException {
/* 453 */     ResultSet resultSet = null;
/*     */     try {
/* 455 */       ReturnStatus returnStatus = new ReturnStatus(-1);
/* 456 */       resultSet = this.m_db.callGBL8992(returnStatus, this.m_prof.getEnterprise(), paramString1, this.m_prof
/* 457 */           .getValOn(), this.m_prof.getEffOn());
/*     */       
/* 459 */       while (resultSet.next()) {
/* 460 */         int i = resultSet.getInt(1);
/* 461 */         String str1 = resultSet.getString(2).trim();
/* 462 */         String str2 = resultSet.getString(3).trim();
/* 463 */         String str3 = resultSet.getString(4).trim();
/* 464 */         str3 = str3.replace(' ', '-');
/* 465 */         str3 = str3.replace(':', '.');
/* 466 */         addDebug("getDelayedEntities callGBL8992 answer: " + i + ":" + str1 + ":" + str2 + ":" + str3);
/* 467 */         if (str3.compareTo(paramString2) <= 0) {
/* 468 */           queueABR(str2, str1, i); continue;
/*     */         } 
/* 470 */         addDebug("getDelayedEntities " + i + ":" + str1 + " has not aged long enough");
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 476 */       if (resultSet != null) {
/* 477 */         resultSet.close();
/* 478 */         resultSet = null;
/*     */       } 
/* 480 */       this.m_db.commit();
/* 481 */       this.m_db.freeStatement();
/* 482 */       this.m_db.isPending();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private class ISOCalendar
/*     */   {
/* 489 */     private GregorianCalendar calendar = new GregorianCalendar();
/*     */     private String microSecStr;
/*     */     
/*     */     ISOCalendar(String param1String) {
/* 493 */       this.calendar.set(1, Integer.parseInt(param1String.substring(0, 4)));
/* 494 */       this.calendar.set(2, Integer.parseInt(param1String.substring(5, 7)) - 1);
/* 495 */       this.calendar.set(5, Integer.parseInt(param1String.substring(8, 10)));
/* 496 */       this.calendar.set(11, Integer.parseInt(param1String.substring(11, 13)));
/* 497 */       this.calendar.set(12, Integer.parseInt(param1String.substring(14, 16)));
/* 498 */       this.calendar.set(13, Integer.parseInt(param1String.substring(17, 19)));
/* 499 */       this.calendar.set(14, 0);
/* 500 */       this.microSecStr = param1String.substring(20);
/*     */     }
/*     */     void dereference() {
/* 503 */       this.microSecStr = null;
/* 504 */       this.calendar = null;
/*     */     }
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
/*     */     String getAdjustedDate(String param1String) {
/* 517 */       int i = 0;
/* 518 */       int j = param1String.indexOf(':');
/* 519 */       if (j == -1) {
/* 520 */         i = Integer.parseInt(param1String);
/* 521 */         DELAYABRSTATUS.this.addDebug("getAdjustedDate minDelay " + param1String + " converted mins " + i);
/*     */       } else {
/* 523 */         int m = Integer.parseInt(param1String.substring(0, j));
/* 524 */         i = Integer.parseInt(param1String.substring(j + 1));
/* 525 */         DELAYABRSTATUS.this.addDebug("getAdjustedDate minDelay " + param1String + " converted hrs " + m + " mins " + i);
/* 526 */         i += m * 60;
/*     */       } 
/*     */ 
/*     */       
/* 530 */       this.calendar.add(12, -i);
/*     */ 
/*     */       
/* 533 */       StringBuffer stringBuffer = new StringBuffer(this.calendar.get(1) + "-");
/* 534 */       int k = this.calendar.get(2) + 1;
/* 535 */       if (k < 10) stringBuffer.append("0"); 
/* 536 */       stringBuffer.append(k + "-");
/* 537 */       k = this.calendar.get(5);
/* 538 */       if (k < 10) stringBuffer.append("0"); 
/* 539 */       stringBuffer.append(k + "-");
/* 540 */       k = this.calendar.get(11);
/* 541 */       if (k < 10) stringBuffer.append("0"); 
/* 542 */       stringBuffer.append(k + ".");
/* 543 */       k = this.calendar.get(12);
/* 544 */       if (k < 10) stringBuffer.append("0"); 
/* 545 */       stringBuffer.append(k + ".");
/* 546 */       k = this.calendar.get(13);
/* 547 */       if (k < 10) stringBuffer.append("0"); 
/* 548 */       stringBuffer.append(k + "." + this.microSecStr);
/*     */       
/* 550 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\DELAYABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */