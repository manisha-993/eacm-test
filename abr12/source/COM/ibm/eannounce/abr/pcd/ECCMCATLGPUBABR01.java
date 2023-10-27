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
/*     */ public class ECCMCATLGPUBABR01
/*     */   extends PokBaseABR
/*     */ {
/*  71 */   public static final String ABR = new String("ECCMCATLGPUBABR01");
/*  72 */   private static final String ATT_LASTRUN = new String("CATLGCCTOPUBLASTRUN");
/*  73 */   private static final String ATT_OFFCOUNTRY = new String("OFFCOUNTRY");
/*  74 */   private static final String QUEUE = new String("ECCMCATLGPUBABR06");
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
/* 149 */         String[] arrayOfString1 = getCCTOs(this.m_db, this.m_prof, str6);
/* 150 */         for (byte b = 0; b < arrayOfString1.length; b++) {
/* 151 */           String str7 = arrayOfString1[b];
/* 152 */           StringTokenizer stringTokenizer = new StringTokenizer(str7, ":");
/* 153 */           if (stringTokenizer.countTokens() == 2) {
/* 154 */             String str8 = stringTokenizer.nextToken();
/* 155 */             int i = Integer.parseInt(stringTokenizer.nextToken().trim());
/* 156 */             this.m_utility.queueEI(this.m_db, this.m_prof, str8, i, QUEUE);
/*     */           } 
/*     */         } 
/*     */         
/* 160 */         removeCATCCTO(this.m_db, this.m_prof, "CATCCTO");
/* 161 */         OPICMList oPICMList = new OPICMList();
/* 162 */         oPICMList.put(ATT_LASTRUN, ATT_LASTRUN + "=" + str1);
/* 163 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/* 165 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 166 */       setReturnCode(-2);
/* 167 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 171 */           .getMessage() + "</font></h3>");
/*     */       
/* 173 */       logError(lockPDHEntityException.getMessage());
/* 174 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 175 */       setReturnCode(-2);
/* 176 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 178 */           .getMessage() + "</font></h3>");
/*     */       
/* 180 */       logError(updatePDHEntityException.getMessage());
/* 181 */     } catch (SBRException sBRException) {
/* 182 */       String str = sBRException.toString();
/* 183 */       int i = str.indexOf("(ok)");
/* 184 */       if (i < 0) {
/* 185 */         setReturnCode(-2);
/* 186 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 188 */             replace(str, str5, "<br>") + "</font></h3>");
/*     */         
/* 190 */         logError(sBRException.toString());
/*     */       } else {
/* 192 */         str = str.substring(0, i);
/* 193 */         println(replace(str, str5, "<br>"));
/*     */       } 
/* 195 */     } catch (Exception exception) {
/*     */       
/* 197 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 198 */       println("" + exception);
/* 199 */       exception.printStackTrace();
/*     */       
/* 201 */       if (getABRReturnCode() != -2) {
/* 202 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 205 */       println("<br /><b>" + 
/*     */           
/* 207 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 210 */               getABRDescription(), 
/* 211 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 214 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 217 */               getABRDescription(), 
/* 218 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 225 */       str3 = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
/* 226 */       if (str3.length() > 64) {
/* 227 */         str3 = str3.substring(0, 64);
/*     */       }
/* 229 */       setDGTitle(str3);
/* 230 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 233 */       setDGString(getABRReturnCode());
/* 234 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 240 */       if (!isReadOnly()) {
/* 241 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getCCTOs(Database paramDatabase, Profile paramProfile, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 248 */     String str1 = " ECCMCATLGPUBPDG getCCTO method";
/* 249 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/* 252 */     String str2 = paramProfile.getEnterprise();
/*     */     
/* 254 */     DatePackage datePackage = paramDatabase.getDates();
/* 255 */     String str3 = datePackage.getNow();
/* 256 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 257 */     Vector<String> vector = new Vector();
/* 258 */     String[] arrayOfString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     String str4 = "SELECT  F.EntityType  as EntityType ,F.EntityID  as EntityID FROM opicm.FLAG F INNER JOIN opicm.Entity E ON     E.Enterprise= ? AND E.EntityType = F.EntityType AND E.EntityID = F.entityID AND E.ValFrom >= ? AND E.ValTo > current timestamp AND E.EffFrom >= ? AND E.EffTo > current timestamp WHERE F.Enterprise = ? AND F.EntityType = 'CCTO' AND F.AttributeCode = 'GENAREANAME' AND F.AttributeValue = ? AND F.ValFrom <= current timestamp AND  current timestamp < F.ValTo AND F.EffFrom <= current timestamp AND current timestamp < F.EffTo ";
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
/* 290 */     PreparedStatement preparedStatement = null;
/*     */     
/* 292 */     Connection connection = paramDatabase.getPDHConnection();
/* 293 */     String str5 = this.m_utility.getDate(str3.substring(0, 10), 15);
/* 294 */     String str6 = this.m_utility.getAttrValue(this.m_ei, "CATLGCCTOPUBLASTRUN");
/*     */     try {
/* 296 */       D.ebug(3, str1 + " setting:" + str2 + ":" + str5 + ":" + paramString + ":" + str6);
/* 297 */       D.ebug(3, str1 + " strSQL1:" + str4);
/* 298 */       preparedStatement = connection.prepareStatement(str4);
/*     */       
/* 300 */       preparedStatement.setString(1, str2);
/* 301 */       preparedStatement.setString(2, str6);
/* 302 */       preparedStatement.setString(3, str6);
/* 303 */       preparedStatement.setString(4, str2);
/* 304 */       preparedStatement.setString(5, paramString);
/*     */       
/* 306 */       resultSet = preparedStatement.executeQuery();
/* 307 */       D.ebug(3, str1 + " executed SQL1.");
/* 308 */       while (resultSet.next()) {
/* 309 */         String str7 = resultSet.getString(1).trim();
/* 310 */         int i = resultSet.getInt(2);
/*     */         
/* 312 */         String str8 = str7 + ":" + i;
/* 313 */         if (hashtable.get(str8) == null) {
/* 314 */           hashtable.put(str8, str8);
/* 315 */           vector.addElement(str8);
/*     */         } 
/*     */       } 
/* 318 */       D.ebug(3, str1 + " vReturn 1 size: " + vector.size());
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 323 */       if (resultSet != null) {
/* 324 */         resultSet.close();
/* 325 */         resultSet = null;
/*     */       } 
/* 327 */       if (preparedStatement != null) {
/* 328 */         preparedStatement.close();
/* 329 */         preparedStatement = null;
/*     */       } 
/* 331 */       paramDatabase.commit();
/* 332 */       paramDatabase.freeStatement();
/* 333 */       paramDatabase.isPending();
/*     */     } 
/*     */ 
/*     */     
/* 337 */     arrayOfString = new String[vector.size()];
/* 338 */     vector.toArray(arrayOfString);
/* 339 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeCATCCTO(Database paramDatabase, Profile paramProfile, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 348 */     String str1 = "ECCMCATLGPUBPDG removeCATLGPUB method " + paramString;
/* 349 */     DatePackage datePackage = paramDatabase.getDates();
/* 350 */     String str2 = datePackage.getNow();
/* 351 */     String str3 = str2.substring(0, 10);
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
/* 365 */     String str6 = "SRDCATCCTO1";
/* 366 */     EntityItem[] arrayOfEntityItem = this.m_utility.dynaSearch(paramDatabase, paramProfile, null, str6, paramString, stringBuffer.toString());
/* 367 */     if (arrayOfEntityItem != null) {
/* 368 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 369 */         EntityItem entityItem = arrayOfEntityItem[b];
/*     */         
/* 371 */         String str = this.m_utility.getAttrValue(entityItem, "PUBTO");
/* 372 */         if (str.length() > 0) {
/* 373 */           int i = this.m_utility.dateCompare(str, this.m_utility.getDate(str3, 30));
/* 374 */           if (i == 2) {
/* 375 */             paramProfile = this.m_utility.setProfValOnEffOn(paramDatabase, paramProfile);
/*     */             
/* 377 */             EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(paramDatabase, paramProfile, entityItem);
/* 378 */             EntityChangeHistoryItem entityChangeHistoryItem = (EntityChangeHistoryItem)this.m_utility.getCurrentChangeItem((ChangeHistoryGroup)entityChangeHistoryGroup);
/* 379 */             String str7 = entityChangeHistoryItem.getChangeDate();
/*     */             
/* 381 */             D.ebug(4, str1 + " checking last update CAT: " + entityItem.getKey());
/* 382 */             int j = this.m_utility.dateCompare(this.m_utility.getDate(str7.substring(0, 10), 30), str3);
/* 383 */             if (j == 2) {
/* 384 */               D.ebug(4, str1 + " deactivate : " + entityItem.getKey());
/* 385 */               paramProfile = this.m_utility.setProfValOnEffOn(paramDatabase, paramProfile);
/* 386 */               EANUtility.deactivateEntity(paramDatabase, paramProfile, entityItem);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 396 */     String str = "";
/* 397 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 399 */     while (paramString1.length() > 0 && i >= 0) {
/* 400 */       str = str + paramString1.substring(0, i) + paramString3;
/* 401 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 402 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 404 */     str = str + paramString1;
/* 405 */     return str;
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
/* 416 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 425 */     return "Catalog Offering Publication For CCTO ABR";
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
/* 436 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 446 */     return new String("$Revision: 1.13 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 456 */     return "$Id: ECCMCATLGPUBABR01.java,v 1.13 2008/01/30 19:27:20 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 466 */     return "ECCMCATLGPUBABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ECCMCATLGPUBABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */