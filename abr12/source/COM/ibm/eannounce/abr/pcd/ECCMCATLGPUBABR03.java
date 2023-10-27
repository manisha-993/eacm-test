/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.ChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.EANUtility;
/*     */ import COM.ibm.eannounce.objects.EntityChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.EntityChangeHistoryItem;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class ECCMCATLGPUBABR03
/*     */   extends PokBaseABR
/*     */ {
/*  74 */   public static final String ABR = new String("ECCMCATLGPUBABR03");
/*  75 */   private static final String ATT_LASTRUN = new String("CATLGCVARPUBLASTRUN");
/*  76 */   private static final String ATT_OFFCOUNTRY = new String("OFFCOUNTRY");
/*  77 */   private static final String QUEUE = new String("ECCMCATLGPUBABR07");
/*  78 */   private PDGUtility m_utility = new PDGUtility();
/*  79 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String STARTDATE = "1980-01-01-00.00.00.000000";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String FOREVER = "9999-12-31-00.00.00.000000";
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  92 */     DatePackage datePackage = null;
/*  93 */     String str1 = null;
/*     */     
/*  95 */     EntityGroup entityGroup = null;
/*  96 */     String str2 = null;
/*  97 */     String str3 = null;
/*  98 */     String str4 = null;
/*  99 */     String str5 = System.getProperty("line.separator");
/*     */     try {
/* 101 */       start_ABRBuild(false);
/* 102 */       setReturnCode(0);
/* 103 */       buildReportHeaderII();
/*     */       
/* 105 */       datePackage = this.m_db.getDates();
/* 106 */       str1 = datePackage.getNow();
/*     */       
/* 108 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/* 109 */       this.m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 111 */       println("<br><b>Catalog Country: " + this.m_ei.getKey() + "</b>");
/* 112 */       printNavigateAttributes(this.m_ei, entityGroup, true);
/*     */ 
/*     */       
/* 115 */       str4 = this.m_utility.getAttrValue(this.m_ei, ATT_LASTRUN);
/* 116 */       if (str4 == null || str4.length() <= 0) {
/* 117 */         OPICMList oPICMList = new OPICMList();
/* 118 */         oPICMList.put(ATT_LASTRUN, ATT_LASTRUN + "=" + "1980-01-01-00.00.00.000000");
/* 119 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/* 121 */       else if (!this.m_utility.isDateFormat(str4)) {
/* 122 */         println(ATT_LASTRUN + " is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000");
/* 123 */         setReturnCode(-1);
/*     */       }
/* 125 */       else if (str4.length() == 10) {
/* 126 */         OPICMList oPICMList = new OPICMList();
/* 127 */         oPICMList.put(ATT_LASTRUN, ATT_LASTRUN + "=" + str4 + "-00.00.00.000000");
/* 128 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/*     */ 
/*     */       
/* 132 */       this.m_prof = this.m_utility.setProfValOnEffOn(this.m_db, this.m_prof);
/* 133 */       this.m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/* 134 */       str2 = this.m_utility.getAttrValue(this.m_ei, "OFFCOUNTRY");
/* 135 */       if (str2 == null || str2.length() <= 0) {
/* 136 */         println("OFFCOUNTRY is blank.");
/* 137 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 141 */       String str = this.m_utility.getAttrValueDesc(this.m_ei, ATT_OFFCOUNTRY);
/*     */       
/* 143 */       String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(this.m_db, this.m_prof, "GENAREANAME", str);
/* 144 */       if (arrayOfString == null || arrayOfString.length <= 0) {
/* 145 */         D.ebug(4, getABRVersion() + " unable to find GENAREANAME for desc: " + str);
/* 146 */         println("Unable to find GENAREANAME for desc: " + str);
/* 147 */         setReturnCode(-1);
/*     */       } 
/*     */       
/* 150 */       if (getReturnCode() == 0) {
/* 151 */         String str6 = arrayOfString[0];
/*     */         
/* 153 */         String[] arrayOfString1 = getCVARs(this.m_db, this.m_prof, str6);
/*     */         
/* 155 */         for (byte b = 0; b < arrayOfString1.length; b++) {
/* 156 */           String str7 = arrayOfString1[b];
/* 157 */           StringTokenizer stringTokenizer = new StringTokenizer(str7, ":");
/* 158 */           if (stringTokenizer.countTokens() == 2) {
/* 159 */             String str8 = stringTokenizer.nextToken();
/* 160 */             int i = Integer.parseInt(stringTokenizer.nextToken().trim());
/* 161 */             this.m_utility.queueEI(this.m_db, this.m_prof, str8, i, QUEUE);
/*     */           } 
/*     */         } 
/*     */         
/* 165 */         removeCATCVAR(this.m_db, this.m_prof, "CATCVAR");
/* 166 */         OPICMList oPICMList = new OPICMList();
/* 167 */         oPICMList.put(ATT_LASTRUN, ATT_LASTRUN + "=" + str1);
/*     */         
/* 169 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/* 171 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 172 */       setReturnCode(-2);
/* 173 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 177 */           .getMessage() + "</font></h3>");
/*     */       
/* 179 */       logError(lockPDHEntityException.getMessage());
/* 180 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 181 */       setReturnCode(-2);
/* 182 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 184 */           .getMessage() + "</font></h3>");
/*     */       
/* 186 */       logError(updatePDHEntityException.getMessage());
/* 187 */     } catch (SBRException sBRException) {
/* 188 */       String str = sBRException.toString();
/* 189 */       int i = str.indexOf("(ok)");
/* 190 */       if (i < 0) {
/* 191 */         setReturnCode(-2);
/* 192 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 194 */             replace(str, str5, "<br>") + "</font></h3>");
/*     */         
/* 196 */         logError(sBRException.toString());
/*     */       } else {
/* 198 */         str = str.substring(0, i);
/* 199 */         println(replace(str, str5, "<br>"));
/*     */       } 
/* 201 */     } catch (Exception exception) {
/*     */       
/* 203 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 204 */       println("" + exception);
/* 205 */       exception.printStackTrace();
/*     */       
/* 207 */       if (getABRReturnCode() != -2) {
/* 208 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 211 */       println("<br /><b>" + 
/*     */           
/* 213 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 216 */               getABRDescription(), 
/* 217 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 220 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 223 */               getABRDescription(), 
/* 224 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       str3 = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
/* 232 */       if (str3.length() > 64) {
/* 233 */         str3 = str3.substring(0, 64);
/*     */       }
/* 235 */       setDGTitle(str3);
/* 236 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 239 */       setDGString(getABRReturnCode());
/* 240 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 246 */       if (!isReadOnly()) {
/* 247 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getCVARs(Database paramDatabase, Profile paramProfile, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 254 */     String str1 = " ECCMCATLGPUBPDG getCVARs method";
/* 255 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/* 258 */     String str2 = paramProfile.getEnterprise();
/*     */     
/* 260 */     DatePackage datePackage = paramDatabase.getDates();
/* 261 */     String str3 = datePackage.getNow();
/* 262 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 263 */     Vector<String> vector = new Vector();
/* 264 */     String[] arrayOfString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     String str4 = "SELECT  F.EntityType  as EntityType ,F.EntityID  as EntityID FROM opicm.FLAG F INNER JOIN opicm.Entity E ON     E.Enterprise= ? AND E.EntityType = F.EntityType AND E.EntityID = F.entityID AND E.ValFrom >= ? AND E.ValTo > current timestamp AND E.EffFrom >= ? AND E.EffTo > current timestamp WHERE F.Enterprise = ? AND F.EntityType = 'CVAR' AND F.AttributeCode = 'GENAREANAME' AND F.AttributeValue = ? AND F.ValFrom <= current timestamp AND  current timestamp < F.ValTo AND F.EffFrom <= current timestamp AND current timestamp < F.EffTo ";
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
/* 293 */     PreparedStatement preparedStatement = null;
/*     */     
/* 295 */     Connection connection = paramDatabase.getPDHConnection();
/* 296 */     String str5 = this.m_utility.getDate(str3.substring(0, 10), 15);
/* 297 */     String str6 = this.m_utility.getAttrValue(this.m_ei, "CATLGCVARPUBLASTRUN");
/*     */     try {
/* 299 */       D.ebug(3, str1 + " setting :" + str2 + ":" + str5 + ":" + paramString + ":" + str6);
/* 300 */       D.ebug(3, str1 + " strSQL1:" + str4);
/* 301 */       preparedStatement = connection.prepareStatement(str4);
/*     */       
/* 303 */       preparedStatement.setString(1, str2);
/* 304 */       preparedStatement.setString(2, str6);
/* 305 */       preparedStatement.setString(3, str6);
/* 306 */       preparedStatement.setString(4, str2);
/* 307 */       preparedStatement.setString(5, paramString);
/*     */       
/* 309 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 311 */       D.ebug(3, str1 + " executed SQL1.");
/*     */       
/* 313 */       while (resultSet.next()) {
/* 314 */         String str7 = resultSet.getString(1).trim();
/* 315 */         int i = resultSet.getInt(2);
/*     */ 
/*     */         
/* 318 */         String str8 = str7 + ":" + i;
/* 319 */         if (hashtable.get(str8) == null) {
/* 320 */           hashtable.put(str8, str8);
/* 321 */           vector.addElement(str8);
/*     */         } 
/*     */       } 
/* 324 */       D.ebug(3, str1 + " vReturn 1 size: " + vector.size());
/*     */     } finally {
/*     */       
/* 327 */       if (resultSet != null) {
/* 328 */         resultSet.close();
/* 329 */         resultSet = null;
/*     */       } 
/* 331 */       if (preparedStatement != null) {
/* 332 */         preparedStatement.close();
/* 333 */         preparedStatement = null;
/*     */       } 
/* 335 */       paramDatabase.commit();
/* 336 */       paramDatabase.freeStatement();
/* 337 */       paramDatabase.isPending();
/*     */     } 
/*     */ 
/*     */     
/* 341 */     arrayOfString = new String[hashtable.size()];
/* 342 */     vector.toArray(arrayOfString);
/* 343 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeCATCVAR(Database paramDatabase, Profile paramProfile, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 352 */     String str1 = "ECCMCATLGPUBPDG removeCATCVAR method " + paramString;
/* 353 */     DatePackage datePackage = paramDatabase.getDates();
/* 354 */     String str2 = datePackage.getNow();
/* 355 */     String str3 = str2.substring(0, 10);
/*     */     
/* 357 */     StringBuffer stringBuffer = new StringBuffer();
/* 358 */     String str4 = this.m_utility.getAttrValueDesc(this.m_ei, ATT_OFFCOUNTRY);
/*     */     
/* 360 */     String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(paramDatabase, paramProfile, "GENAREANAME", str4);
/* 361 */     if (arrayOfString == null || arrayOfString.length <= 0) {
/* 362 */       D.ebug(4, str1 + " unable to find GENAREANAME for desc: " + str4);
/*     */       
/*     */       return;
/*     */     } 
/* 366 */     String str5 = arrayOfString[0];
/*     */     
/* 368 */     stringBuffer.append("map_GENAREANAME=" + str5);
/* 369 */     String str6 = "SRDCATCVAR1";
/* 370 */     EntityItem[] arrayOfEntityItem = this.m_utility.dynaSearch(paramDatabase, paramProfile, null, str6, paramString, stringBuffer.toString());
/* 371 */     if (arrayOfEntityItem != null) {
/* 372 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 373 */         EntityItem entityItem = arrayOfEntityItem[b];
/*     */         
/* 375 */         String str = this.m_utility.getAttrValue(entityItem, "PUBTO");
/* 376 */         if (str.length() > 0) {
/* 377 */           int i = this.m_utility.dateCompare(str, this.m_utility.getDate(str3, 30));
/* 378 */           if (i == 2) {
/* 379 */             paramProfile = this.m_utility.setProfValOnEffOn(paramDatabase, paramProfile);
/*     */             
/* 381 */             EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(paramDatabase, paramProfile, entityItem);
/* 382 */             EntityChangeHistoryItem entityChangeHistoryItem = (EntityChangeHistoryItem)this.m_utility.getCurrentChangeItem((ChangeHistoryGroup)entityChangeHistoryGroup);
/* 383 */             String str7 = entityChangeHistoryItem.getChangeDate();
/*     */             
/* 385 */             D.ebug(4, str1 + " checking last update CAT: " + entityItem.getKey());
/* 386 */             int j = this.m_utility.dateCompare(this.m_utility.getDate(str7.substring(0, 10), 30), str3);
/* 387 */             if (j == 2) {
/* 388 */               D.ebug(4, str1 + " deactivate : " + entityItem.getKey());
/* 389 */               paramProfile = this.m_utility.setProfValOnEffOn(paramDatabase, paramProfile);
/* 390 */               EANUtility.deactivateEntity(paramDatabase, paramProfile, entityItem);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 400 */     String str = "";
/* 401 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 403 */     while (paramString1.length() > 0 && i >= 0) {
/* 404 */       str = str + paramString1.substring(0, i) + paramString3;
/* 405 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 406 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 408 */     str = str + paramString1;
/* 409 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 420 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 429 */     return "Catalog Offering Publication For CVAR ABR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 440 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 450 */     return new String("$Revision: 1.14 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 460 */     return "$Id: ECCMCATLGPUBABR03.java,v 1.14 2008/01/30 19:27:19 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 470 */     return "ECCMCATLGPUBABR03.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ECCMCATLGPUBABR03.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */