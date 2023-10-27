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
/*     */ public class ECCMCATLGPUBABR02
/*     */   extends PokBaseABR
/*     */ {
/*  71 */   public static final String ABR = new String("ECCMCATLGPUBABR02");
/*  72 */   private static final String ATT_LASTRUN = new String("CATLGCSOLPUBLASTRUN");
/*  73 */   private static final String ATT_OFFCOUNTRY = new String("OFFCOUNTRY");
/*  74 */   private static final String QUEUE = new String("ECCMCATLGPUBABR05");
/*  75 */   private PDGUtility m_utility = new PDGUtility();
/*  76 */   private EntityItem m_ei = null;
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
/*  89 */     DatePackage datePackage = null;
/*  90 */     String str1 = null;
/*     */     
/*  92 */     EntityGroup entityGroup = null;
/*  93 */     String str2 = null;
/*  94 */     String str3 = null;
/*  95 */     String str4 = null;
/*  96 */     String str5 = System.getProperty("line.separator");
/*     */     try {
/*  98 */       start_ABRBuild(false);
/*  99 */       setReturnCode(0);
/* 100 */       buildReportHeaderII();
/*     */       
/* 102 */       datePackage = this.m_db.getDates();
/* 103 */       str1 = datePackage.getNow();
/*     */       
/* 105 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/* 106 */       this.m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 108 */       println("<br><b>Catalog Country: " + this.m_ei.getKey() + "</b>");
/* 109 */       printNavigateAttributes(this.m_ei, entityGroup, true);
/*     */ 
/*     */       
/* 112 */       str4 = this.m_utility.getAttrValue(this.m_ei, ATT_LASTRUN);
/* 113 */       if (str4 == null || str4.length() <= 0) {
/* 114 */         OPICMList oPICMList = new OPICMList();
/* 115 */         oPICMList.put(ATT_LASTRUN, ATT_LASTRUN + "=" + "1980-01-01-00.00.00.000000");
/* 116 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/* 118 */       else if (!this.m_utility.isDateFormat(str4)) {
/* 119 */         println(ATT_LASTRUN + " is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000");
/* 120 */         setReturnCode(-1);
/*     */       }
/* 122 */       else if (str4.length() == 10) {
/* 123 */         OPICMList oPICMList = new OPICMList();
/* 124 */         oPICMList.put(ATT_LASTRUN, ATT_LASTRUN + "=" + str4 + "-00.00.00.000000");
/* 125 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/*     */ 
/*     */       
/* 129 */       this.m_prof = this.m_utility.setProfValOnEffOn(this.m_db, this.m_prof);
/* 130 */       this.m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/* 131 */       str2 = this.m_utility.getAttrValue(this.m_ei, "OFFCOUNTRY");
/* 132 */       if (str2 == null || str2.length() <= 0) {
/* 133 */         println("OFFCOUNTRY is blank.");
/* 134 */         setReturnCode(-1);
/*     */       } 
/*     */       
/* 137 */       String str = this.m_utility.getAttrValueDesc(this.m_ei, ATT_OFFCOUNTRY);
/*     */       
/* 139 */       String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(this.m_db, this.m_prof, "GENAREANAME", str);
/* 140 */       if (arrayOfString == null || arrayOfString.length <= 0) {
/* 141 */         D.ebug(4, getABRVersion() + " unable to find GENAREANAME for desc: " + str);
/* 142 */         println("Unable to find GENAREANAME for desc: " + str);
/* 143 */         setReturnCode(-1);
/*     */       } 
/*     */       
/* 146 */       if (getReturnCode() == 0) {
/* 147 */         String str6 = arrayOfString[0];
/*     */         
/* 149 */         String[] arrayOfString1 = getCSOLs(this.m_db, this.m_prof, str6);
/*     */         
/* 151 */         for (byte b = 0; b < arrayOfString1.length; b++) {
/* 152 */           String str7 = arrayOfString1[b];
/* 153 */           StringTokenizer stringTokenizer = new StringTokenizer(str7, ":");
/* 154 */           if (stringTokenizer.countTokens() == 2) {
/* 155 */             String str8 = stringTokenizer.nextToken();
/* 156 */             int i = Integer.parseInt(stringTokenizer.nextToken().trim());
/* 157 */             this.m_utility.queueEI(this.m_db, this.m_prof, str8, i, QUEUE);
/*     */           } 
/*     */         } 
/*     */         
/* 161 */         removeCATCSOL(this.m_db, this.m_prof, "CATCSOL");
/* 162 */         OPICMList oPICMList = new OPICMList();
/* 163 */         oPICMList.put(ATT_LASTRUN, ATT_LASTRUN + "=" + str1);
/* 164 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/* 166 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 167 */       setReturnCode(-2);
/* 168 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 172 */           .getMessage() + "</font></h3>");
/*     */       
/* 174 */       logError(lockPDHEntityException.getMessage());
/* 175 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 176 */       setReturnCode(-2);
/* 177 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 179 */           .getMessage() + "</font></h3>");
/*     */       
/* 181 */       logError(updatePDHEntityException.getMessage());
/* 182 */     } catch (SBRException sBRException) {
/* 183 */       String str = sBRException.toString();
/* 184 */       int i = str.indexOf("(ok)");
/* 185 */       if (i < 0) {
/* 186 */         setReturnCode(-2);
/* 187 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 189 */             replace(str, str5, "<br>") + "</font></h3>");
/*     */         
/* 191 */         logError(sBRException.toString());
/*     */       } else {
/* 193 */         str = str.substring(0, i);
/* 194 */         println(replace(str, str5, "<br>"));
/*     */       } 
/* 196 */     } catch (Exception exception) {
/*     */       
/* 198 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 199 */       println("" + exception);
/* 200 */       exception.printStackTrace();
/*     */       
/* 202 */       if (getABRReturnCode() != -2) {
/* 203 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 206 */       println("<br /><b>" + 
/*     */           
/* 208 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 211 */               getABRDescription(), 
/* 212 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 215 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 218 */               getABRDescription(), 
/* 219 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 226 */       str3 = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
/* 227 */       if (str3.length() > 64) {
/* 228 */         str3 = str3.substring(0, 64);
/*     */       }
/* 230 */       setDGTitle(str3);
/* 231 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 234 */       setDGString(getABRReturnCode());
/* 235 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 241 */       if (!isReadOnly()) {
/* 242 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getCSOLs(Database paramDatabase, Profile paramProfile, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 249 */     String str1 = " ECCMCATLGPUBPDG getCSOLs method";
/* 250 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/* 253 */     String str2 = paramProfile.getEnterprise();
/*     */     
/* 255 */     DatePackage datePackage = paramDatabase.getDates();
/* 256 */     String str3 = datePackage.getNow();
/* 257 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 258 */     Vector<String> vector = new Vector();
/* 259 */     String[] arrayOfString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     String str4 = "SELECT  RTRIM(F.EntityType)  as EntityType ,F.EntityID  as EntityID FROM opicm.FLAG F INNER JOIN opicm.Entity E ON     E.Enterprise= ? AND E.EntityType = F.EntityType AND E.EntityID = F.entityID AND E.ValFrom >= ? AND E.ValTo > current timestamp AND E.EffFrom >= ? AND E.EffTo > current timestamp WHERE F.Enterprise = ? AND F.EntityType = 'CSOL' AND F.AttributeCode = 'GENAREANAME' AND F.AttributeValue = ? AND F.ValFrom <= current timestamp AND  current timestamp < F.ValTo AND F.EffFrom <= current timestamp AND current timestamp < F.EffTo ";
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
/* 287 */     PreparedStatement preparedStatement = null;
/*     */     
/* 289 */     Connection connection = paramDatabase.getPDHConnection();
/* 290 */     String str5 = this.m_utility.getDate(str3.substring(0, 10), 15);
/* 291 */     String str6 = this.m_utility.getAttrValue(this.m_ei, "CATLGCSOLPUBLASTRUN");
/*     */     try {
/* 293 */       D.ebug(3, str1 + " setting:" + str2 + ":" + str5 + ":" + paramString + ":" + str6);
/* 294 */       D.ebug(3, str1 + " strSQL1:" + str4);
/* 295 */       connection = paramDatabase.getPDHConnection();
/* 296 */       preparedStatement = connection.prepareStatement(str4);
/*     */ 
/*     */       
/* 299 */       preparedStatement.setString(1, str2);
/* 300 */       preparedStatement.setString(2, str6);
/* 301 */       preparedStatement.setString(3, str6);
/* 302 */       preparedStatement.setString(4, str2);
/* 303 */       preparedStatement.setString(5, paramString);
/*     */       
/* 305 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 307 */       D.ebug(3, str1 + " executed SQL1.");
/*     */       
/* 309 */       while (resultSet.next()) {
/* 310 */         String str7 = resultSet.getString(1).trim();
/* 311 */         int i = resultSet.getInt(2);
/*     */         
/* 313 */         String str8 = str7 + ":" + i;
/* 314 */         if (hashtable.get(str8) == null) {
/* 315 */           hashtable.put(str8, str8);
/* 316 */           vector.addElement(str8);
/*     */         } 
/*     */       } 
/* 319 */       D.ebug(3, str1 + " vReturn 1 size: " + vector.size());
/*     */     } finally {
/*     */       
/* 322 */       if (resultSet != null) {
/* 323 */         resultSet.close();
/* 324 */         resultSet = null;
/*     */       } 
/* 326 */       if (preparedStatement != null) {
/* 327 */         preparedStatement.close();
/* 328 */         preparedStatement = null;
/*     */       } 
/* 330 */       paramDatabase.commit();
/* 331 */       paramDatabase.freeStatement();
/* 332 */       paramDatabase.isPending();
/*     */     } 
/*     */ 
/*     */     
/* 336 */     arrayOfString = new String[vector.size()];
/* 337 */     vector.toArray(arrayOfString);
/* 338 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeCATCSOL(Database paramDatabase, Profile paramProfile, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 347 */     String str1 = "ECCMCATLGPUBPDG removeCATCSOL method " + paramString;
/* 348 */     DatePackage datePackage = paramDatabase.getDates();
/* 349 */     String str2 = datePackage.getNow();
/* 350 */     String str3 = str2.substring(0, 10);
/*     */ 
/*     */     
/* 353 */     StringBuffer stringBuffer = new StringBuffer();
/* 354 */     String str4 = this.m_utility.getAttrValueDesc(this.m_ei, ATT_OFFCOUNTRY);
/*     */     
/* 356 */     String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(paramDatabase, paramProfile, "GENAREANAME", str4);
/* 357 */     if (arrayOfString == null || arrayOfString.length <= 0) {
/* 358 */       D.ebug(4, str1 + " unable to find GENAREANAME for desc: " + str4);
/*     */       
/*     */       return;
/*     */     } 
/* 362 */     String str5 = arrayOfString[0];
/*     */     
/* 364 */     stringBuffer.append("map_GENAREANAME=" + str5);
/*     */     
/* 366 */     String str6 = "SRDCATCSOL1";
/* 367 */     EntityItem[] arrayOfEntityItem = this.m_utility.dynaSearch(paramDatabase, paramProfile, null, str6, paramString, stringBuffer.toString());
/* 368 */     if (arrayOfEntityItem != null) {
/* 369 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 370 */         EntityItem entityItem = arrayOfEntityItem[b];
/*     */         
/* 372 */         String str = this.m_utility.getAttrValue(entityItem, "PUBTO");
/* 373 */         if (str.length() > 0) {
/* 374 */           int i = this.m_utility.dateCompare(str, this.m_utility.getDate(str3, 30));
/* 375 */           if (i == 2) {
/* 376 */             paramProfile = this.m_utility.setProfValOnEffOn(paramDatabase, paramProfile);
/*     */             
/* 378 */             EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(paramDatabase, paramProfile, entityItem);
/* 379 */             EntityChangeHistoryItem entityChangeHistoryItem = (EntityChangeHistoryItem)this.m_utility.getCurrentChangeItem((ChangeHistoryGroup)entityChangeHistoryGroup);
/* 380 */             String str7 = entityChangeHistoryItem.getChangeDate();
/*     */             
/* 382 */             D.ebug(4, str1 + " checking last update CAT: " + entityItem.getKey());
/* 383 */             int j = this.m_utility.dateCompare(this.m_utility.getDate(str7.substring(0, 10), 30), str3);
/* 384 */             if (j == 2) {
/* 385 */               D.ebug(4, str1 + " deactivate : " + entityItem.getKey());
/* 386 */               paramProfile = this.m_utility.setProfValOnEffOn(paramDatabase, paramProfile);
/* 387 */               EANUtility.deactivateEntity(paramDatabase, paramProfile, entityItem);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 397 */     String str = "";
/* 398 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 400 */     while (paramString1.length() > 0 && i >= 0) {
/* 401 */       str = str + paramString1.substring(0, i) + paramString3;
/* 402 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 403 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 405 */     str = str + paramString1;
/* 406 */     return str;
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
/* 417 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 426 */     return "Catalog Offering Publication For CSOL ABR";
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
/* 437 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 447 */     return new String("$Revision: 1.13 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 457 */     return "$Id: ECCMCATLGPUBABR02.java,v 1.13 2008/01/30 19:27:19 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 467 */     return "ECCMCATLGPUBABR02.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ECCMCATLGPUBABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */