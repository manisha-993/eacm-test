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
/*     */ public class ECCMCATLGPUBABR04
/*     */   extends PokBaseABR
/*     */ {
/*  74 */   public static final String ABR = new String("ECCMCATLGPUBABR04");
/*  75 */   private static final String ATT_LASTRUN = new String("CATLGCBPUBLASTRUN");
/*  76 */   private static final String ATT_OFFCOUNTRY = new String("OFFCOUNTRY");
/*  77 */   private static final String QUEUE = new String("ECCMCATLGPUBABR08");
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
/* 140 */       String str = this.m_utility.getAttrValueDesc(this.m_ei, ATT_OFFCOUNTRY);
/*     */       
/* 142 */       String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(this.m_db, this.m_prof, "GENAREANAME", str);
/* 143 */       if (arrayOfString == null || arrayOfString.length <= 0) {
/* 144 */         D.ebug(4, getABRVersion() + " unable to find GENAREANAME for desc: " + str);
/* 145 */         println("Unable to find GENAREANAME for desc: " + str);
/* 146 */         setReturnCode(-1);
/*     */       } 
/*     */       
/* 149 */       if (getReturnCode() == 0) {
/* 150 */         String str6 = arrayOfString[0];
/*     */         
/* 152 */         String[] arrayOfString1 = getCBs(this.m_db, this.m_prof, str6);
/*     */         
/* 154 */         for (byte b = 0; b < arrayOfString1.length; b++) {
/* 155 */           String str7 = arrayOfString1[b];
/* 156 */           StringTokenizer stringTokenizer = new StringTokenizer(str7, ":");
/* 157 */           if (stringTokenizer.countTokens() == 2) {
/* 158 */             String str8 = stringTokenizer.nextToken();
/* 159 */             int i = Integer.parseInt(stringTokenizer.nextToken().trim());
/* 160 */             this.m_utility.queueEI(this.m_db, this.m_prof, str8, i, QUEUE);
/*     */           } 
/*     */         } 
/* 163 */         removeCATCB(this.m_db, this.m_prof, "CATCB");
/* 164 */         OPICMList oPICMList = new OPICMList();
/* 165 */         oPICMList.put(ATT_LASTRUN, ATT_LASTRUN + "=" + str1);
/* 166 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/* 168 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 169 */       setReturnCode(-2);
/* 170 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 174 */           .getMessage() + "</font></h3>");
/*     */       
/* 176 */       logError(lockPDHEntityException.getMessage());
/* 177 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 178 */       setReturnCode(-2);
/* 179 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 181 */           .getMessage() + "</font></h3>");
/*     */       
/* 183 */       logError(updatePDHEntityException.getMessage());
/* 184 */     } catch (SBRException sBRException) {
/* 185 */       String str = sBRException.toString();
/* 186 */       int i = str.indexOf("(ok)");
/* 187 */       if (i < 0) {
/* 188 */         setReturnCode(-2);
/* 189 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 191 */             replace(str, str5, "<br>") + "</font></h3>");
/*     */         
/* 193 */         logError(sBRException.toString());
/*     */       } else {
/* 195 */         str = str.substring(0, i);
/* 196 */         println(replace(str, str5, "<br>"));
/*     */       } 
/* 198 */     } catch (Exception exception) {
/*     */       
/* 200 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 201 */       println("" + exception);
/* 202 */       exception.printStackTrace();
/*     */       
/* 204 */       if (getABRReturnCode() != -2) {
/* 205 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 208 */       println("<br /><b>" + 
/*     */           
/* 210 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 213 */               getABRDescription(), 
/* 214 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 217 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 220 */               getABRDescription(), 
/* 221 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 228 */       str3 = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
/* 229 */       if (str3.length() > 64) {
/* 230 */         str3 = str3.substring(0, 64);
/*     */       }
/* 232 */       setDGTitle(str3);
/* 233 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 236 */       setDGString(getABRReturnCode());
/* 237 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 243 */       if (!isReadOnly()) {
/* 244 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getCBs(Database paramDatabase, Profile paramProfile, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 251 */     String str1 = " ECCMCATLGPUBPDG getCBs method";
/* 252 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/* 255 */     String str2 = paramProfile.getEnterprise();
/*     */     
/* 257 */     DatePackage datePackage = paramDatabase.getDates();
/* 258 */     String str3 = datePackage.getNow();
/* 259 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 260 */     Vector<String> vector = new Vector();
/* 261 */     String[] arrayOfString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     String str4 = "SELECT  F.EntityType  as EntityType ,F.EntityID  as EntityID FROM opicm.FLAG F INNER JOIN opicm.Entity E ON     E.Enterprise= ? AND E.EntityType = F.EntityType AND E.EntityID = F.entityID AND E.ValFrom >= ? AND E.ValTo > current timestamp AND E.EffFrom >= ? AND E.EffTo > current timestamp WHERE F.Enterprise = ? AND F.EntityType = 'CB' AND F.AttributeCode = 'GENAREANAME' AND F.AttributeValue = ? AND F.ValFrom <= current timestamp AND  current timestamp < F.ValTo AND F.EffFrom <= current timestamp AND current timestamp < F.EffTo ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     PreparedStatement preparedStatement = null;
/*     */     
/* 293 */     Connection connection = paramDatabase.getPDHConnection();
/* 294 */     String str5 = this.m_utility.getDate(str3.substring(0, 10), 15);
/* 295 */     String str6 = this.m_utility.getAttrValue(this.m_ei, "CATLGCBPUBLASTRUN");
/*     */     try {
/* 297 */       D.ebug(3, str1 + " setting:" + str2 + ":" + str5 + ":" + paramString + ":" + str6);
/* 298 */       D.ebug(3, str1 + " strSQL1:" + str4);
/* 299 */       preparedStatement = connection.prepareStatement(str4);
/*     */       
/* 301 */       preparedStatement.setString(1, str2);
/* 302 */       preparedStatement.setString(2, str6);
/* 303 */       preparedStatement.setString(3, str6);
/* 304 */       preparedStatement.setString(4, str2);
/* 305 */       preparedStatement.setString(5, paramString);
/*     */       
/* 307 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 309 */       D.ebug(3, str1 + " executed SQL1.");
/*     */       
/* 311 */       while (resultSet.next()) {
/* 312 */         String str7 = resultSet.getString(1).trim();
/* 313 */         int i = resultSet.getInt(2);
/*     */         
/* 315 */         String str8 = str7 + ":" + i;
/* 316 */         if (hashtable.get(str8) == null) {
/* 317 */           hashtable.put(str8, str8);
/* 318 */           vector.addElement(str8);
/*     */         } 
/*     */       } 
/* 321 */       D.ebug(3, str1 + " vReturn 1 size: " + vector.size());
/*     */     } finally {
/*     */       
/* 324 */       if (resultSet != null) {
/* 325 */         resultSet.close();
/* 326 */         resultSet = null;
/*     */       } 
/* 328 */       if (preparedStatement != null) {
/* 329 */         preparedStatement.close();
/* 330 */         preparedStatement = null;
/*     */       } 
/* 332 */       paramDatabase.commit();
/* 333 */       paramDatabase.freeStatement();
/* 334 */       paramDatabase.isPending();
/*     */     } 
/*     */ 
/*     */     
/* 338 */     arrayOfString = new String[vector.size()];
/* 339 */     vector.toArray(arrayOfString);
/* 340 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeCATCB(Database paramDatabase, Profile paramProfile, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 349 */     String str1 = "ECCMCATLGPUBPDG removeCATCB method " + paramString;
/* 350 */     DatePackage datePackage = paramDatabase.getDates();
/* 351 */     String str2 = datePackage.getNow();
/* 352 */     String str3 = str2.substring(0, 10);
/*     */     
/* 354 */     StringBuffer stringBuffer = new StringBuffer();
/* 355 */     String str4 = this.m_utility.getAttrValueDesc(this.m_ei, ATT_OFFCOUNTRY);
/*     */     
/* 357 */     String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(paramDatabase, paramProfile, "GENAREANAME", str4);
/* 358 */     if (arrayOfString == null || arrayOfString.length <= 0) {
/* 359 */       D.ebug(4, str1 + " unable to find GENAREANAME for desc: " + str4);
/*     */       
/*     */       return;
/*     */     } 
/* 363 */     String str5 = arrayOfString[0];
/*     */     
/* 365 */     stringBuffer.append("map_GENAREANAME=" + str5);
/* 366 */     String str6 = "SRDCATCB1";
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
/* 426 */     return "Catalog Offering Publication For CB ABR";
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
/* 447 */     return new String("$Revision: 1.14 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 457 */     return "$Id: ECCMCATLGPUBABR04.java,v 1.14 2008/01/30 19:27:19 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 467 */     return "ECCMCATLGPUBABR04.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ECCMCATLGPUBABR04.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */