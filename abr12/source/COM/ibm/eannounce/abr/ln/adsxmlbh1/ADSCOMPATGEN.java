/*      */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*      */ import com.ibm.transform.oim.eacm.diff.DiffVE;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.IOException;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.TransformerException;
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
/*      */ public class ADSCOMPATGEN
/*      */   extends XMLMQRoot
/*      */ {
/*   63 */   static String m_strODSSchema = "GBLI.";
/*   64 */   static String m_tablename = "WWTECHCOMPAT";
/*   65 */   static String OSIndependent = "10589";
/*   66 */   protected Connection m_conODS = null;
/*   67 */   int count = 0;
/*   68 */   int decount = 0;
/*   69 */   int updatecount = 0;
/*   70 */   int errcount = 0;
/*      */ 
/*      */   
/*   73 */   static final Hashtable Attr_OS = new Hashtable<>(); static {
/*   74 */     Attr_OS.put("MODEL", "OSLEVEL");
/*   75 */     Attr_OS.put("LSEOBUNDLE", "OSLEVEL");
/*   76 */     Attr_OS.put("MODELCGOS", "OS");
/*   77 */     Attr_OS.put("SEOCGOS", "OS");
/*   78 */     Attr_OS.put("WWSEO", "OS");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   83 */     String str = ABRServerProperties.getValue("COMPATGENABRSTATUS", "_CHUNKSIZE", "100000");
/*      */     
/*   85 */     WWCOMPAT_ROW_LIMIT = Integer.parseInt(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int WWCOMPAT_ROW_LIMIT;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem, boolean paramBoolean) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  102 */     process(paramADSABRSTATUS, paramProfile1, paramProfile2, getVeName(), paramEntityItem);
/*  103 */     if (!getVeName2().equals("dummy")) {
/*  104 */       process(paramADSABRSTATUS, paramProfile1, paramProfile2, getVeName2(), paramEntityItem);
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
/*      */   public void process(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  119 */     long l1 = System.currentTimeMillis();
/*  120 */     long l2 = 0L;
/*      */ 
/*      */ 
/*      */     
/*  124 */     EntityList entityList1 = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, paramString, paramEntityItem);
/*      */     
/*  126 */     EntityList entityList2 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, paramString, paramEntityItem);
/*  127 */     l2 = System.currentTimeMillis();
/*  128 */     paramADSABRSTATUS.addDebug("Time for both pulls: " + Stopwatch.format(l2 - l1));
/*  129 */     l1 = l2;
/*      */ 
/*      */ 
/*      */     
/*  133 */     Hashtable<String, String> hashtable = ((ExtractActionItem)entityList1.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, paramEntityItem
/*  134 */         .getEntityType());
/*      */     
/*  136 */     if (getVeName().equals("ADSWWCOMPATMDLCGOSMDL1")) {
/*  137 */       hashtable.put("0MODELCGOSU", "Hi");
/*  138 */       hashtable.put("0MODELD", "Hi");
/*      */     } 
/*  140 */     if (getVeName().equals("ADSWWCOMPATSEOCGOSBDL")) {
/*  141 */       hashtable.put("0SEOCGOSU", "Hi");
/*  142 */       hashtable.put("0LSEOBUNDLED", "Hi");
/*      */     } 
/*  144 */     if (getVeName().equals("ADSWWCOMPATSEOCGOSSEO")) {
/*  145 */       hashtable.put("0SEOCGOSU", "Hi");
/*  146 */       hashtable.put("0WWSEOD", "Hi");
/*      */     } 
/*  148 */     if (getVeName().equals("ADSWWCOMPATSEOCGOSSVCSEO")) {
/*  149 */       hashtable.put("0SEOCGOSU", "Hi");
/*  150 */       hashtable.put("0SVCSEOD", "Hi");
/*      */     } 
/*      */     
/*  153 */     DiffVE diffVE = new DiffVE(entityList2, entityList1, hashtable);
/*  154 */     diffVE.setCheckAllNLS(true);
/*  155 */     paramADSABRSTATUS.addDebug("hshMap: " + hashtable);
/*  156 */     paramADSABRSTATUS.addDebug("time1 flattened VE: " + diffVE.getPriorDiffVE());
/*  157 */     paramADSABRSTATUS.addDebug("time2 flattened VE: " + diffVE.getCurrentDiffVE());
/*      */ 
/*      */     
/*  160 */     Vector<DiffEntity> vector = diffVE.diffVE();
/*  161 */     paramADSABRSTATUS.addDebug(" diffVE info:\n" + diffVE.getDebug());
/*  162 */     paramADSABRSTATUS.addDebug(" diffVE flattened VE: " + vector);
/*      */     
/*  164 */     l2 = System.currentTimeMillis();
/*  165 */     paramADSABRSTATUS.addDebug(" Time for diff: " + Stopwatch.format(l2 - l1));
/*  166 */     l1 = l2;
/*      */ 
/*      */     
/*  169 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  170 */     boolean bool = false; byte b;
/*  171 */     for (b = 0; b < vector.size(); b++) {
/*  172 */       DiffEntity diffEntity = vector.elementAt(b);
/*      */       
/*  174 */       bool = (bool || diffEntity.isChanged()) ? true : false;
/*      */     } 
/*  176 */     for (b = 0; b < vector.size(); b++) {
/*  177 */       DiffEntity diffEntity = vector.elementAt(b);
/*      */       
/*  179 */       bool = (bool || diffEntity.isChanged()) ? true : false;
/*      */ 
/*      */ 
/*      */       
/*  183 */       hashtable1.put(diffEntity.getKey(), diffEntity);
/*      */       
/*  185 */       String str = diffEntity.getEntityType();
/*  186 */       if (diffEntity.isRoot()) {
/*  187 */         str = "ROOT";
/*      */       }
/*  189 */       Vector<DiffEntity> vector1 = (Vector)hashtable1.get(str);
/*  190 */       if (vector1 == null) {
/*  191 */         vector1 = new Vector();
/*  192 */         hashtable1.put(str, vector1);
/*      */       } 
/*  194 */       vector1.add(diffEntity);
/*      */     } 
/*      */     
/*  197 */     if (bool) {
/*      */       
/*  199 */       for (b = 0; b < entityList1.getEntityGroupCount(); b++) {
/*  200 */         String str = entityList1.getEntityGroup(b).getEntityType();
/*  201 */         Vector vector1 = (Vector)hashtable1.get(str);
/*  202 */         if (vector1 == null) {
/*  203 */           vector1 = new Vector();
/*  204 */           hashtable1.put(str, vector1);
/*      */         } 
/*      */       } 
/*      */       
/*  208 */       hashtable1.put("VeName", paramString);
/*  209 */       populateTable(paramADSABRSTATUS, paramProfile1, paramProfile2, hashtable1);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  214 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", paramEntityItem.getKey());
/*      */     } 
/*      */ 
/*      */     
/*  218 */     entityList2.dereference();
/*  219 */     entityList1.dereference();
/*  220 */     hashtable.clear();
/*  221 */     vector.clear();
/*  222 */     diffVE.dereference();
/*      */     
/*  224 */     for (Enumeration<Object> enumeration = hashtable1.elements(); enumeration.hasMoreElements(); ) {
/*  225 */       Vector vector1 = (Vector)enumeration.nextElement();
/*  226 */       if (vector1 instanceof Vector) {
/*  227 */         ((Vector)vector1).clear();
/*      */       }
/*      */     } 
/*  230 */     hashtable1.clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void populateTable(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, Hashtable paramHashtable) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException {
/*  258 */     setConnection();
/*  259 */     this.errcount = 0;
/*  260 */     this.decount = 0;
/*  261 */     this.updatecount = 0;
/*  262 */     this.count = 0;
/*  263 */     getModelsByOS(paramADSABRSTATUS, paramHashtable, paramProfile2.getNow(), paramProfile2.getEndOfDay());
/*  264 */     if (this.decount > 0)
/*  265 */       paramADSABRSTATUS.addOutput("ADSWWCOMPATABR found deactivate count:" + this.decount + " records be deactivated from table. Error count :" + this.errcount); 
/*  266 */     if (this.updatecount > 0)
/*  267 */       paramADSABRSTATUS.addOutput("ADSWWCOMPATABR found updated count:" + this.updatecount + " records be updated. Error count :" + this.errcount); 
/*  268 */     if (this.count > 0) {
/*  269 */       paramADSABRSTATUS.addOutput("ADSWWCOMPATABR found count:" + this.count + " records be inserted into table. Error count :" + this.errcount);
/*      */     }
/*  271 */     releaseConn();
/*  272 */     System.gc();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable, String paramString1, String paramString2) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateCompat(ADSABRSTATUS paramADSABRSTATUS, Vector<WWCOMPAT> paramVector, String paramString1, String paramString2) {
/*  285 */     for (byte b = 0; b < paramVector.size(); b++) {
/*  286 */       WWCOMPAT wWCOMPAT = paramVector.elementAt(b);
/*  287 */       UpdateWWTECHCOMPAT(paramADSABRSTATUS, wWCOMPAT, paramString1, paramString2);
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
/*      */ 
/*      */   
/*      */   protected int updateCOMPATPUBFLG(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) {
/*  306 */     PreparedStatement preparedStatement = null;
/*      */     
/*  308 */     int i = 0;
/*  309 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?, CompatibilityPublishingFlag = ?, RelationshipType = ?, PublishFrom = ?, PublishTo = ?  WHERE  OptionEntityType = ?  AND OptionEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  319 */       setConnection();
/*  320 */       paramADSABRSTATUS.addDebug(" Beging to execute updateCOMPATPUBFLG SQL : " + paramString2 + "|" + paramInt2 + "|" + paramString1 + "|" + paramInt1 + "|" + paramString3);
/*  321 */       preparedStatement = null;
/*  322 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  323 */       preparedStatement.setString(1, "C");
/*  324 */       preparedStatement.setString(2, paramString7);
/*  325 */       preparedStatement.setString(3, paramString8);
/*  326 */       preparedStatement.setString(4, paramString3);
/*  327 */       preparedStatement.setString(5, paramString6);
/*  328 */       preparedStatement.setString(6, paramString4);
/*  329 */       preparedStatement.setString(7, paramString5);
/*  330 */       preparedStatement.setString(8, paramString2);
/*  331 */       preparedStatement.setInt(9, paramInt2);
/*  332 */       preparedStatement.setString(10, paramString1);
/*  333 */       preparedStatement.setInt(11, paramInt1);
/*  334 */       preparedStatement.setString(12, paramString8);
/*  335 */       i = preparedStatement.executeUpdate();
/*  336 */       this.updatecount += i;
/*  337 */       paramADSABRSTATUS.addDebug(" end to execute updateCOMPATPUBFLG SQL, the update count is : " + i);
/*  338 */     } catch (SQLException sQLException) {
/*  339 */       this.errcount++;
/*  340 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString2 + "|" + paramInt2 + "|" + paramString1 + "|" + paramInt1 + "|" + paramString3 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  344 */         this.m_conODS.commit();
/*  345 */         if (preparedStatement != null) {
/*  346 */           preparedStatement.close();
/*  347 */           preparedStatement = null;
/*      */         } 
/*  349 */       } catch (Exception exception) {
/*  350 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  353 */     return i;
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
/*      */   protected int deActivateMDLCGOSMDL(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, String paramString4) {
/*  368 */     PreparedStatement preparedStatement = null;
/*      */     
/*  370 */     int i = 0;
/*  371 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ? WHERE  OptionEntityType = ?  AND OptionEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  381 */       setConnection();
/*  382 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateMDLCGOSMDL SQL : " + paramString2 + "|" + paramInt2 + "|" + paramString1 + "|" + paramInt1);
/*  383 */       preparedStatement = null;
/*  384 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  385 */       preparedStatement.setString(1, "D");
/*  386 */       preparedStatement.setString(2, paramString3);
/*  387 */       preparedStatement.setString(3, paramString4);
/*  388 */       preparedStatement.setString(4, paramString2);
/*  389 */       preparedStatement.setInt(5, paramInt2);
/*  390 */       preparedStatement.setString(6, paramString1);
/*  391 */       preparedStatement.setInt(7, paramInt1);
/*  392 */       preparedStatement.setString(8, paramString4);
/*  393 */       i = preparedStatement.executeUpdate();
/*  394 */       this.decount += i;
/*  395 */       paramADSABRSTATUS.addDebug(" end to execute deActivateMDLCGOSMDL SQL, the deactivate count is : " + i);
/*  396 */     } catch (SQLException sQLException) {
/*  397 */       this.errcount++;
/*  398 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString2 + "|" + paramInt2 + "|" + paramString1 + "|" + paramInt1 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  402 */         this.m_conODS.commit();
/*  403 */         if (preparedStatement != null) {
/*  404 */           preparedStatement.close();
/*  405 */           preparedStatement = null;
/*      */         } 
/*  407 */       } catch (Exception exception) {
/*  408 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  411 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int deActivateSystemOS(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt, Vector<String> paramVector, String paramString2, String paramString3) {
/*  416 */     PreparedStatement preparedStatement = null;
/*      */     
/*  418 */     int i = 0;
/*  419 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE  SystemEntityType = ?  AND SystemEntityId = ?  AND SystemOS = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  428 */       setConnection();
/*  429 */       for (byte b = 0; b < paramVector.size(); b++) {
/*  430 */         String str1 = paramVector.elementAt(b);
/*  431 */         paramADSABRSTATUS.addDebug(" Beging to execute deActivateSystemOS SQL : " + paramString1 + "|" + paramInt + "|" + str1);
/*  432 */         preparedStatement = null;
/*  433 */         preparedStatement = this.m_conODS.prepareStatement(str);
/*  434 */         preparedStatement.setString(1, "D");
/*  435 */         preparedStatement.setString(2, paramString2);
/*  436 */         preparedStatement.setString(3, paramString3);
/*  437 */         preparedStatement.setString(4, paramString1);
/*  438 */         preparedStatement.setInt(5, paramInt);
/*  439 */         preparedStatement.setString(6, str1);
/*  440 */         preparedStatement.setString(7, paramString3);
/*  441 */         i = preparedStatement.executeUpdate();
/*  442 */         this.decount += i;
/*  443 */         paramADSABRSTATUS.addDebug(" end to execute deActivateSystemOS SQL, the deactivate count is : " + i);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*  449 */     catch (SQLException sQLException) {
/*  450 */       this.errcount++;
/*  451 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString1 + "|" + paramInt + "|" + paramVector + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  455 */         this.m_conODS.commit();
/*  456 */         if (preparedStatement != null) {
/*  457 */           preparedStatement.close();
/*  458 */           preparedStatement = null;
/*      */         } 
/*  460 */       } catch (Exception exception) {
/*  461 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  464 */     return i;
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
/*      */   protected int deActivateSystem(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, String paramString4) {
/*  479 */     PreparedStatement preparedStatement = null;
/*      */     
/*  481 */     int i = 0;
/*  482 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE  SystemEntityType = ?  AND SystemEntityId = ?  AND GroupEntityType = ?  AND GroupEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  492 */       setConnection();
/*  493 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateSystem SQL : " + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2);
/*  494 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  495 */       preparedStatement.setString(1, "D");
/*  496 */       preparedStatement.setString(2, paramString3);
/*  497 */       preparedStatement.setString(3, paramString4);
/*  498 */       preparedStatement.setString(4, paramString1);
/*  499 */       preparedStatement.setInt(5, paramInt1);
/*  500 */       preparedStatement.setString(6, paramString2);
/*  501 */       preparedStatement.setInt(7, paramInt2);
/*  502 */       preparedStatement.setString(8, paramString4);
/*  503 */       i = preparedStatement.executeUpdate();
/*  504 */       this.decount += i;
/*  505 */       paramADSABRSTATUS.addDebug(" end to execute deActivateSystem SQL, the deactivate count is : " + i);
/*      */     }
/*  507 */     catch (SQLException sQLException) {
/*  508 */       this.errcount++;
/*  509 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  513 */         this.m_conODS.commit();
/*  514 */         if (preparedStatement != null) {
/*  515 */           preparedStatement.close();
/*  516 */           preparedStatement = null;
/*      */         } 
/*  518 */       } catch (Exception exception) {
/*  519 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  522 */     return i;
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
/*      */   protected int deActivateCGOS(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, String paramString4) {
/*  537 */     PreparedStatement preparedStatement = null;
/*      */     
/*  539 */     int i = 0;
/*  540 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE  GroupEntityType = ?  AND GroupEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  550 */       setConnection();
/*  551 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateCGOS SQL : " + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2);
/*  552 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  553 */       preparedStatement.setString(1, "D");
/*  554 */       preparedStatement.setString(2, paramString3);
/*  555 */       preparedStatement.setString(3, paramString4);
/*  556 */       preparedStatement.setString(4, paramString1);
/*  557 */       preparedStatement.setInt(5, paramInt1);
/*  558 */       preparedStatement.setString(6, paramString2);
/*  559 */       preparedStatement.setInt(7, paramInt2);
/*  560 */       preparedStatement.setString(8, paramString4);
/*  561 */       i = preparedStatement.executeUpdate();
/*  562 */       this.decount += i;
/*  563 */       paramADSABRSTATUS.addDebug(" end to execute deActivateCGOS SQL, the deactivate count is : " + i);
/*      */     }
/*  565 */     catch (SQLException sQLException) {
/*  566 */       this.errcount++;
/*  567 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  571 */         this.m_conODS.commit();
/*  572 */         if (preparedStatement != null) {
/*  573 */           preparedStatement.close();
/*  574 */           preparedStatement = null;
/*      */         } 
/*  576 */       } catch (Exception exception) {
/*  577 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  580 */     return i;
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
/*      */   protected int deActivateOption(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, String paramString4) {
/*  594 */     PreparedStatement preparedStatement = null;
/*      */     
/*  596 */     int i = 0;
/*  597 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE OptionEntityType = ?  AND OptionEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  607 */       setConnection();
/*  608 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateOption SQL : " + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2);
/*  609 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  610 */       preparedStatement.setString(1, "D");
/*  611 */       preparedStatement.setString(2, paramString3);
/*  612 */       preparedStatement.setString(3, paramString4);
/*  613 */       preparedStatement.setString(4, paramString1);
/*  614 */       preparedStatement.setInt(5, paramInt1);
/*  615 */       preparedStatement.setString(6, paramString2);
/*  616 */       preparedStatement.setInt(7, paramInt2);
/*  617 */       preparedStatement.setString(8, paramString4);
/*  618 */       i = preparedStatement.executeUpdate();
/*  619 */       this.decount += i;
/*  620 */       paramADSABRSTATUS.addDebug(" end to execute deActivateOption SQL, the deactivate cound is : " + i);
/*      */     }
/*  622 */     catch (SQLException sQLException) {
/*  623 */       this.errcount++;
/*  624 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  628 */         this.m_conODS.commit();
/*  629 */         if (preparedStatement != null) {
/*  630 */           preparedStatement.close();
/*  631 */           preparedStatement = null;
/*      */         } 
/*  633 */       } catch (Exception exception) {
/*  634 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  637 */     return i;
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
/*      */   protected int deActivateCG(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt, String paramString2, String paramString3) {
/*  650 */     PreparedStatement preparedStatement = null;
/*      */     
/*  652 */     int i = 0;
/*  653 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE GroupEntityType = ?  AND GroupEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  661 */       setConnection();
/*  662 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateCG SQL : |" + paramString1 + "|" + paramInt);
/*  663 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  664 */       preparedStatement.setString(1, "D");
/*  665 */       preparedStatement.setString(2, paramString2);
/*  666 */       preparedStatement.setString(3, paramString3);
/*  667 */       preparedStatement.setString(4, paramString1);
/*  668 */       preparedStatement.setInt(5, paramInt);
/*  669 */       preparedStatement.setString(6, paramString3);
/*  670 */       i = preparedStatement.executeUpdate();
/*  671 */       this.decount += i;
/*  672 */       paramADSABRSTATUS.addDebug(" end to execute deActivateCG SQL, the deactivate count is : " + i);
/*      */     }
/*  674 */     catch (SQLException sQLException) {
/*  675 */       this.errcount++;
/*  676 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D|" + paramString1 + "|" + paramInt + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  680 */         this.m_conODS.commit();
/*  681 */         if (preparedStatement != null) {
/*  682 */           preparedStatement.close();
/*  683 */           preparedStatement = null;
/*      */         } 
/*  685 */       } catch (Exception exception) {
/*  686 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  689 */     return i;
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
/*      */   protected int deActivateCGOS(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt, String paramString2, String paramString3, String paramString4) {
/*  702 */     PreparedStatement preparedStatement = null;
/*      */     
/*  704 */     int i = 0;
/*  705 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE OSEntityType = ?  AND OSEntityId = ?  AND OS = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  714 */       setConnection();
/*  715 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateCGOS SQL : |" + paramString1 + "|" + paramInt + "|" + paramString2);
/*  716 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  717 */       preparedStatement.setString(1, "D");
/*  718 */       preparedStatement.setString(2, paramString3);
/*  719 */       preparedStatement.setString(3, paramString4);
/*  720 */       preparedStatement.setString(4, paramString1);
/*  721 */       preparedStatement.setInt(5, paramInt);
/*  722 */       preparedStatement.setString(6, paramString2);
/*  723 */       preparedStatement.setString(7, paramString4);
/*  724 */       i = preparedStatement.executeUpdate();
/*  725 */       this.decount += i;
/*  726 */       paramADSABRSTATUS.addDebug(" end to execute deActivateCGOS SQL, the deactivate count is : " + i);
/*      */     }
/*  728 */     catch (SQLException sQLException) {
/*  729 */       this.errcount++;
/*  730 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D|" + paramString1 + "|" + paramInt + "|" + paramString2 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  734 */         this.m_conODS.commit();
/*  735 */         if (preparedStatement != null) {
/*  736 */           preparedStatement.close();
/*  737 */           preparedStatement = null;
/*      */         } 
/*  739 */       } catch (Exception exception) {
/*  740 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  743 */     return i;
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
/*      */   protected int updateCGOKTOPUB(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5) {
/*  757 */     PreparedStatement preparedStatement = null;
/*      */     
/*  759 */     int i = 0;
/*  760 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?, OKTOPUB = ?, BRANDCD_FC = ? WHERE GroupEntityType = ?  AND GroupEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  768 */       setConnection();
/*  769 */       paramADSABRSTATUS.addDebug(" Beging to execute updateCGOKTOPUB SQL : |" + paramString1 + "|" + paramInt + "|" + paramString2 + "|" + paramString3);
/*  770 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  771 */       preparedStatement.setString(1, "C");
/*  772 */       preparedStatement.setString(2, paramString4);
/*  773 */       preparedStatement.setString(3, paramString5);
/*  774 */       preparedStatement.setString(4, paramString2);
/*  775 */       preparedStatement.setString(5, paramString3);
/*  776 */       preparedStatement.setString(6, paramString1);
/*  777 */       preparedStatement.setInt(7, paramInt);
/*  778 */       preparedStatement.setString(8, paramString5);
/*  779 */       i = preparedStatement.executeUpdate();
/*  780 */       this.updatecount += i;
/*  781 */       paramADSABRSTATUS.addDebug(" end to execute updateCGOKTOPUB SQL, the update count is : " + i);
/*      */     }
/*  783 */     catch (SQLException sQLException) {
/*  784 */       this.errcount++;
/*  785 */       paramADSABRSTATUS.addOutput("Error: SQL updating . Activity C|" + paramString1 + "|" + paramInt + "|" + paramString2 + "|" + paramString3 + "  Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  789 */         this.m_conODS.commit();
/*  790 */         if (preparedStatement != null) {
/*  791 */           preparedStatement.close();
/*  792 */           preparedStatement = null;
/*      */         } 
/*  794 */       } catch (Exception exception) {
/*  795 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  798 */     return i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int UpdateWWTECHCOMPAT(ADSABRSTATUS paramADSABRSTATUS, WWCOMPAT paramWWCOMPAT, String paramString1, String paramString2) {
/*  824 */     PreparedStatement preparedStatement = null;
/*  825 */     int i = 0;
/*      */     
/*  827 */     String str1 = "INSERT  INTO " + m_strODSSchema + m_tablename + " (Activity, Updated, TimeOfChange, BRANDCD_FC, SystemEntityType, SystemEntityId, SystemOS, GroupEntityType, GroupEntityId, OKTOPUB, OSEntityType, OSEntityId, OS, OptionEntityType, OptionEntityId, CompatibilityPublishingFlag, RelationshipType, PublishFrom, PublishTo)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/*      */ 
/*      */     
/*  831 */     String str2 = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?, BRANDCD_FC = ?, OKTOPUB = ?, CompatibilityPublishingFlag = ?, RelationshipType = ?, PublishFrom = ?, PublishTo = ?  WHERE  SystemEntityType = ?  AND SystemEntityId = ?  AND SystemOS = ?  AND GroupEntityType = ?  AND GroupEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND OS = ?  AND OptionEntityType = ?  AND OptionEntityId = ? ";
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
/*      */     try {
/*  843 */       String[] arrayOfString = entityExistsInCOMPAT(paramADSABRSTATUS, paramWWCOMPAT, paramString2);
/*      */       
/*  845 */       if (arrayOfString == null) {
/*      */         
/*  847 */         paramADSABRSTATUS.addDebug(" Beging to execute strInsertWWTECHCOMPATSQL : " + paramWWCOMPAT.getKey());
/*  848 */         preparedStatement = this.m_conODS.prepareStatement(str1);
/*      */         
/*  850 */         preparedStatement.clearParameters();
/*  851 */         preparedStatement.setString(1, "A");
/*  852 */         preparedStatement.setString(2, paramString1);
/*  853 */         preparedStatement.setString(3, paramString2);
/*  854 */         preparedStatement.setString(4, paramWWCOMPAT.getBRANDCD_FC());
/*  855 */         preparedStatement.setString(5, paramWWCOMPAT.getSystemEntityType());
/*  856 */         preparedStatement.setInt(6, paramWWCOMPAT.getSystemEntityId());
/*  857 */         preparedStatement.setString(7, paramWWCOMPAT.getSystemOS());
/*  858 */         preparedStatement.setString(8, paramWWCOMPAT.getGroupEntityType());
/*  859 */         preparedStatement.setInt(9, paramWWCOMPAT.getGroupEntityId());
/*  860 */         preparedStatement.setString(10, paramWWCOMPAT.getOKTOPUB());
/*  861 */         preparedStatement.setString(11, paramWWCOMPAT.getOSEntityType());
/*  862 */         preparedStatement.setInt(12, paramWWCOMPAT.getOSEntityId());
/*  863 */         preparedStatement.setString(13, paramWWCOMPAT.getOS());
/*  864 */         preparedStatement.setString(14, paramWWCOMPAT.getOptionEntityType());
/*  865 */         preparedStatement.setInt(15, paramWWCOMPAT.getOptionEntityId());
/*  866 */         preparedStatement.setString(16, paramWWCOMPAT.getCompatibilityPublishingFlag());
/*  867 */         preparedStatement.setString(17, paramWWCOMPAT.getRelationshipType());
/*  868 */         preparedStatement.setString(18, paramWWCOMPAT.getPublishFrom());
/*  869 */         preparedStatement.setString(19, paramWWCOMPAT.getPublishTo());
/*  870 */         i = preparedStatement.executeUpdate();
/*  871 */         this.count++;
/*  872 */         paramADSABRSTATUS.addDebug("execute INSERT into table WWTECHCOMPAT :COUNT: " + this.count + "\n" + paramWWCOMPAT.getKey());
/*      */       } else {
/*      */         
/*  875 */         String str3 = arrayOfString[0];
/*  876 */         String str4 = arrayOfString[1];
/*  877 */         if (paramString2.compareTo(str4) >= 0) {
/*      */           
/*  879 */           preparedStatement = this.m_conODS.prepareStatement(str2);
/*      */           
/*  881 */           preparedStatement.clearParameters();
/*  882 */           preparedStatement.setString(1, "A");
/*  883 */           preparedStatement.setString(2, paramString1);
/*  884 */           preparedStatement.setString(3, paramString2);
/*  885 */           preparedStatement.setString(4, paramWWCOMPAT.getBRANDCD_FC());
/*  886 */           preparedStatement.setString(5, paramWWCOMPAT.getOKTOPUB());
/*  887 */           preparedStatement.setString(6, paramWWCOMPAT.getCompatibilityPublishingFlag());
/*  888 */           preparedStatement.setString(7, paramWWCOMPAT.getRelationshipType());
/*  889 */           preparedStatement.setString(8, paramWWCOMPAT.getPublishFrom());
/*  890 */           preparedStatement.setString(9, paramWWCOMPAT.getPublishTo());
/*  891 */           preparedStatement.setString(10, paramWWCOMPAT.getSystemEntityType());
/*  892 */           preparedStatement.setInt(11, paramWWCOMPAT.getSystemEntityId());
/*  893 */           preparedStatement.setString(12, paramWWCOMPAT.getSystemOS());
/*  894 */           preparedStatement.setString(13, paramWWCOMPAT.getGroupEntityType());
/*  895 */           preparedStatement.setInt(14, paramWWCOMPAT.getGroupEntityId());
/*  896 */           preparedStatement.setString(15, paramWWCOMPAT.getOSEntityType());
/*  897 */           preparedStatement.setInt(16, paramWWCOMPAT.getOSEntityId());
/*  898 */           preparedStatement.setString(17, paramWWCOMPAT.getOS());
/*  899 */           preparedStatement.setString(18, paramWWCOMPAT.getOptionEntityType());
/*  900 */           preparedStatement.setInt(19, paramWWCOMPAT.getOptionEntityId());
/*  901 */           i = preparedStatement.executeUpdate();
/*  902 */           this.updatecount++;
/*  903 */           paramADSABRSTATUS.addDebug("execute UPDATE into talble WWTECHCOMPAT :UPDATE COUNT: " + this.updatecount + "\n" + paramWWCOMPAT.getKey());
/*      */         } else {
/*      */           
/*  906 */           paramADSABRSTATUS.addDebug("This record has beed update at the same time of " + str4 + "activity" + str3 + "\n" + paramWWCOMPAT.getKey());
/*      */         }
/*      */       
/*      */       } 
/*  910 */     } catch (SQLException sQLException) {
/*  911 */       this.errcount++;
/*  912 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping insert. Activity " + paramWWCOMPAT.getKey() + "\n Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  916 */         this.m_conODS.commit();
/*  917 */         if (preparedStatement != null) {
/*  918 */           preparedStatement.close();
/*  919 */           preparedStatement = null;
/*      */         } 
/*  921 */       } catch (Exception exception) {
/*  922 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  925 */     return i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String[] entityExistsInCOMPAT(ADSABRSTATUS paramADSABRSTATUS, WWCOMPAT paramWWCOMPAT, String paramString) throws SQLException {
/*  948 */     String str = "SELECT Activity, TimeOfChange from " + m_strODSSchema + m_tablename + " WHERE  SystemEntityType = ?  AND SystemEntityId = ?  AND SystemOS = ?  AND GroupEntityType = ?  AND GroupEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND OS = ?  AND OptionEntityType = ?  AND OptionEntityId = ? with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  958 */     String[] arrayOfString = null;
/*      */     
/*  960 */     PreparedStatement preparedStatement = null;
/*  961 */     ResultSet resultSet = null;
/*      */     
/*      */     try {
/*  964 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  965 */       preparedStatement.setString(1, paramWWCOMPAT.getSystemEntityType());
/*  966 */       preparedStatement.setInt(2, paramWWCOMPAT.getSystemEntityId());
/*  967 */       preparedStatement.setString(3, paramWWCOMPAT.getSystemOS());
/*  968 */       preparedStatement.setString(4, paramWWCOMPAT.getGroupEntityType());
/*  969 */       preparedStatement.setInt(5, paramWWCOMPAT.getGroupEntityId());
/*  970 */       preparedStatement.setString(6, paramWWCOMPAT.getOSEntityType());
/*  971 */       preparedStatement.setInt(7, paramWWCOMPAT.getOSEntityId());
/*  972 */       preparedStatement.setString(8, paramWWCOMPAT.getOS());
/*  973 */       preparedStatement.setString(9, paramWWCOMPAT.getOptionEntityType());
/*  974 */       preparedStatement.setInt(10, paramWWCOMPAT.getOptionEntityId());
/*  975 */       resultSet = preparedStatement.executeQuery();
/*  976 */       if (resultSet.next()) {
/*  977 */         String str1 = resultSet.getString("Activity");
/*  978 */         String str2 = resultSet.getString("TimeOfChange");
/*  979 */         arrayOfString = new String[] { str1, str2 };
/*  980 */         paramADSABRSTATUS.addDebug(" reslut Activity : " + str1 + "  TimeOfChange: " + str2);
/*      */       } else {
/*      */         
/*  983 */         paramADSABRSTATUS.addDebug(" could not find reslut Activity");
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  988 */       if (resultSet != null) {
/*  989 */         resultSet.close();
/*  990 */         resultSet = null;
/*      */       } 
/*  992 */       if (preparedStatement != null) {
/*  993 */         preparedStatement.close();
/*  994 */         preparedStatement = null;
/*      */       } 
/*      */     } 
/*  997 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void setSystemSoLvVct(Vector<String> paramVector, EntityItem paramEntityItem) {
/* 1004 */     if (paramEntityItem != null) {
/* 1005 */       String str = paramEntityItem.getEntityType();
/* 1006 */       EANFlagAttribute eANFlagAttribute = null;
/* 1007 */       if ("MODEL".equals(str) || "LSEOBUNDLE".equals(str)) {
/* 1008 */         eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("OSLEVEL");
/*      */       } else {
/* 1010 */         eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("OS");
/*      */       } 
/* 1012 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/* 1014 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1015 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/* 1017 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 1018 */             paramVector.add(arrayOfMetaFlag[b].getFlagCode());
/*      */           }
/*      */         } 
/*      */       } else {
/* 1022 */         paramVector.add("");
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
/*      */   protected static String checkSYSOSValue(DiffEntity paramDiffEntity, String paramString1, String paramString2) {
/* 1035 */     String str = null;
/* 1036 */     if (!"".equals(paramString1)) {
/* 1037 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 1038 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString2);
/* 1039 */       if (eANFlagAttribute == null || !eANFlagAttribute.isSelected(paramString1)) {
/* 1040 */         str = "Delete";
/*      */       } else {
/* 1042 */         entityItem = paramDiffEntity.getPriorEntityItem();
/* 1043 */         eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString2);
/* 1044 */         if (eANFlagAttribute == null || !eANFlagAttribute.isSelected(paramString1)) {
/* 1045 */           str = "Update";
/*      */         }
/*      */       } 
/*      */     } else {
/* 1049 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 1050 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString2);
/* 1051 */       if (eANFlagAttribute != null) {
/* 1052 */         str = "Delete";
/*      */       } else {
/* 1054 */         entityItem = paramDiffEntity.getPriorEntityItem();
/* 1055 */         eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString2);
/* 1056 */         if (eANFlagAttribute != null) {
/* 1057 */           str = "Update";
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1062 */     return str;
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
/*      */   protected static String[] oslvlMatch(String paramString, Vector paramVector) {
/* 1080 */     String[] arrayOfString = null;
/*      */     
/* 1082 */     if (paramString != null && paramVector.contains("10589")) {
/* 1083 */       arrayOfString = new String[] { "10589" };
/* 1084 */     } else if ("10589".equals(paramString)) {
/* 1085 */       arrayOfString = new String[paramVector.size()];
/* 1086 */       paramVector.copyInto((Object[])arrayOfString);
/* 1087 */     } else if (paramString != null && paramVector.contains(paramString)) {
/* 1088 */       arrayOfString = new String[] { paramString };
/* 1089 */     } else if (paramString != null && paramVector.contains("")) {
/* 1090 */       arrayOfString = new String[] { "" };
/*      */     } 
/*      */     
/* 1093 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String getValue(DiffEntity paramDiffEntity, String paramString) {
/* 1102 */     String str1 = null;
/* 1103 */     String str2 = "";
/* 1104 */     String str3 = "";
/* 1105 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 1106 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 1107 */     if (paramDiffEntity.isDeleted()) {
/* 1108 */       str3 = PokUtils.getAttributeValue(entityItem2, paramString, ", ", "", false);
/* 1109 */     } else if (paramDiffEntity.isNew()) {
/* 1110 */       str2 = PokUtils.getAttributeValue(entityItem1, paramString, ", ", "", false);
/*      */     } else {
/* 1112 */       str3 = PokUtils.getAttributeValue(entityItem2, paramString, ", ", "", false);
/* 1113 */       str2 = PokUtils.getAttributeValue(entityItem1, paramString, ", ", "", false);
/*      */     } 
/*      */     
/* 1116 */     if (!str3.equals(str2)) {
/* 1117 */       str1 = str2;
/*      */     }
/* 1119 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String getFlagValue(DiffEntity paramDiffEntity, String paramString) {
/* 1129 */     String str1 = null;
/* 1130 */     String str2 = "";
/* 1131 */     String str3 = "";
/* 1132 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 1133 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 1134 */     if (paramDiffEntity.isDeleted()) {
/* 1135 */       str3 = PokUtils.getAttributeFlagValue(entityItem2, paramString);
/* 1136 */       if (str3 == null) {
/* 1137 */         str3 = "";
/*      */       }
/* 1139 */     } else if (paramDiffEntity.isNew()) {
/* 1140 */       str2 = PokUtils.getAttributeFlagValue(entityItem1, paramString);
/* 1141 */       if (str2 == null) {
/* 1142 */         str2 = "";
/*      */       }
/*      */     } else {
/* 1145 */       str3 = PokUtils.getAttributeFlagValue(entityItem2, paramString);
/* 1146 */       str2 = PokUtils.getAttributeFlagValue(entityItem1, paramString);
/* 1147 */       if (str2 == null) {
/* 1148 */         str2 = "";
/*      */       }
/* 1150 */       if (str3 == null) {
/* 1151 */         str3 = "";
/*      */       }
/*      */     } 
/*      */     
/* 1155 */     if (!str3.equals(str2)) {
/* 1156 */       str1 = str2;
/*      */     }
/*      */     
/* 1159 */     return str1;
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
/*      */   protected boolean putvalidWWCOMPAT(ADSABRSTATUS paramADSABRSTATUS, EntityItem paramEntityItem, Vector paramVector1, Vector paramVector2, Vector paramVector3, String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, int paramInt3, String paramString4, int paramInt4, String paramString5, int paramInt5, String paramString6) throws SQLException {
/* 1191 */     setConnection();
/*      */     
/* 1193 */     String str1 = "";
/*      */     
/* 1195 */     str1 = getEntityStatus(paramString1, paramInt1, paramString6);
/* 1196 */     if (!str1.equals("0020") && !str1.equals("0040") && !str1.equals("")) {
/* 1197 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :SystemEntity " + paramString1 + paramInt1 + " Status : " + str1);
/* 1198 */       return false;
/*      */     } 
/*      */     
/* 1201 */     String str2 = "";
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
/* 1234 */     boolean bool1 = true;
/* 1235 */     String str3 = "";
/* 1236 */     String str4 = "";
/*      */     
/* 1238 */     str1 = getEntityStatus(paramString2, paramInt2, paramString6);
/* 1239 */     if (!str1.equals("0020") && !str1.equals("0040")) {
/* 1240 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :CG " + paramString2 + paramInt2 + " Status : " + str1);
/* 1241 */       return false;
/*      */     } 
/*      */     
/* 1244 */     str3 = getEntityFlagUnique(paramString2, paramInt2, "OKTOPUB", paramString6);
/* 1245 */     bool1 = str3.equalsIgnoreCase("DEL") ? false : true;
/* 1246 */     if (!bool1) {
/* 1247 */       paramADSABRSTATUS.addDebug("isOKTOPUB is delete :CG " + paramString2 + paramInt2 + " Status : " + str1);
/* 1248 */       return false;
/*      */     } 
/*      */     
/* 1251 */     str4 = getEntityFlagUnique(paramString2, paramInt2, "BRANDCD", paramString6);
/*      */ 
/*      */ 
/*      */     
/* 1255 */     boolean bool2 = false;
/* 1256 */     String str5 = "";
/*      */     
/* 1258 */     str1 = getEntityStatus(paramString3, paramInt3, paramString6);
/* 1259 */     if (!str1.equals("0020") && !str1.equals("0040")) {
/* 1260 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :CGOS " + paramString3 + paramInt3 + " Status : " + str1);
/* 1261 */       return false;
/*      */     } 
/*      */     
/* 1264 */     str5 = getEntityFlagUnique(paramString3, paramInt3, "OS", paramString6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1270 */     if (str5.equals(OSIndependent) || str5.equals("")) {
/* 1271 */       bool2 = true;
/*      */     }
/* 1273 */     if (!bool2) {
/* 1274 */       paramADSABRSTATUS.addDebug(paramString3 + paramInt3 + " does not have OS attr equal to OS Independent 10589.");
/* 1275 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1281 */     boolean bool3 = true;
/* 1282 */     String str6 = "";
/* 1283 */     String str7 = "";
/* 1284 */     String str8 = "";
/* 1285 */     String str9 = "";
/*      */     
/* 1287 */     str1 = getEntityStatus(paramString4, paramInt4, paramString6);
/* 1288 */     if (!str1.equals("0020") && !str1.equals("0040")) {
/* 1289 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :" + paramString4 + paramInt4 + " Status : " + str1);
/* 1290 */       return false;
/*      */     } 
/*      */     
/* 1293 */     str6 = getEntityFlagUnique(paramString4, paramInt4, "COMPATPUBFLG", paramString6);
/* 1294 */     bool3 = str6.equalsIgnoreCase("DEL") ? false : true;
/* 1295 */     if (!bool3) {
/* 1296 */       paramADSABRSTATUS.addDebug("COMPATPUBFLG is delete :" + paramString4 + paramInt4 + " Status : " + str1);
/* 1297 */       return false;
/*      */     } 
/* 1299 */     str7 = getEntityTextValue(paramString4, paramInt4, "PUBFROM", paramString6);
/* 1300 */     str8 = getEntityTextValue(paramString4, paramInt4, "PUBTO", paramString6);
/* 1301 */     str9 = getEntityFlagUnique(paramString4, paramInt4, "RELTYPE", paramString6);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1306 */     str1 = getEntityStatus(paramString5, paramInt5, paramString6);
/* 1307 */     if (!str1.equals("0020") && !str1.equals("0040") && !str1.equals("")) {
/* 1308 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :" + paramString5 + paramInt5 + " Status : " + str1);
/* 1309 */       return false;
/*      */     } 
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
/* 1354 */     if ("WWSEO".equals(paramString1) || "LSEOBUNDLE".equals(paramString1) || "MODEL".equals(paramString1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1360 */       setWWCOMPAT(str4, paramString1, paramInt1, str2, paramString2, paramInt2, str3, paramString3, paramInt3, str5, paramString5, paramInt5, str6, str9, str7, str8, paramVector1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1377 */       paramADSABRSTATUS.addDebug("Not support for this release :" + paramString1 + paramInt1);
/* 1378 */       return false;
/*      */     } 
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
/*      */ 
/*      */     
/* 1430 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void putWWCOMPATVector(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector1, EntityItem paramEntityItem, ResultSet paramResultSet, Vector paramVector2, Vector paramVector3, Vector paramVector4, String paramString1, String paramString2) throws SQLException {
/* 1438 */     String str1 = "";
/*      */     
/* 1440 */     String str2 = "";
/*      */     
/* 1442 */     String str3 = "";
/*      */     
/* 1444 */     String str4 = "";
/*      */     
/* 1446 */     String str5 = "";
/*      */     
/* 1448 */     byte b = 1;
/* 1449 */     long l = System.currentTimeMillis();
/* 1450 */     boolean bool1 = (paramVector2.size() == 0) ? true : false;
/* 1451 */     boolean bool2 = (paramVector2.size() == 0) ? true : false;
/* 1452 */     if (paramResultSet != null) {
/* 1453 */       while (paramResultSet.next()) {
/* 1454 */         str1 = paramResultSet.getString("SystemEntityType");
/* 1455 */         int i = paramResultSet.getInt("SystemEntityId");
/* 1456 */         str2 = paramResultSet.getString("GroupEntityType");
/* 1457 */         int j = paramResultSet.getInt("GroupEntityId");
/* 1458 */         str3 = paramResultSet.getString("OSEntityType");
/* 1459 */         int k = paramResultSet.getInt("OSEntityId");
/* 1460 */         str4 = paramResultSet.getString("OSOPTIONType");
/* 1461 */         int m = paramResultSet.getInt("OSOPTIONId");
/* 1462 */         str5 = paramResultSet.getString("OptionEntityType");
/* 1463 */         int n = paramResultSet.getInt("OptionEntityID");
/* 1464 */         paramVector4.clear();
/* 1465 */         if (bool1) {
/* 1466 */           paramVector2.clear();
/*      */         }
/* 1468 */         if (bool2) {
/* 1469 */           paramVector3.clear();
/*      */         }
/* 1471 */         putvalidWWCOMPAT(paramADSABRSTATUS, paramEntityItem, paramVector4, paramVector2, paramVector3, str1, i, str2, j, str3, k, str4, m, str5, n, paramString2);
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
/* 1482 */         paramVector1.addAll(paramVector4);
/*      */         
/* 1484 */         if (paramVector1.size() >= WWCOMPAT_ROW_LIMIT) {
/* 1485 */           paramADSABRSTATUS.addDebug("Chunking size is " + WWCOMPAT_ROW_LIMIT + ". Start to run chunking " + b++ + " times.");
/* 1486 */           updateCompat(paramADSABRSTATUS, paramVector1, paramString1, paramString2);
/*      */           
/* 1488 */           paramVector1.clear();
/*      */         } 
/*      */       } 
/*      */       
/* 1492 */       if (paramVector1.size() > 0) {
/* 1493 */         updateCompat(paramADSABRSTATUS, paramVector1, paramString1, paramString2);
/*      */       }
/* 1495 */       paramADSABRSTATUS.addDebug("Time to getMatchingDateIds all WWCOMPATVector size:" + ((b - 1) * WWCOMPAT_ROW_LIMIT + paramVector1.size()) + "|" + paramEntityItem.getKey() + ": " + 
/* 1496 */           Stopwatch.format(System.currentTimeMillis() - l));
/*      */       
/* 1498 */       paramVector1.clear();
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
/*      */   protected String getEntityStatus(String paramString1, int paramInt, String paramString2) throws SQLException {
/* 1515 */     return getEntityFlagUnique(paramString1, paramInt, "STATUS", paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getEntityFlagUnique(String paramString1, int paramInt, String paramString2, String paramString3) throws SQLException {
/* 1526 */     String str1 = "";
/* 1527 */     PreparedStatement preparedStatement = null;
/* 1528 */     ResultSet resultSet = null;
/* 1529 */     String str2 = " select attributevalue from opicm.flag where entitytype=? and entityid=? and attributecode=?  and effto > ? and valto > ?  and enterprise= 'SG' with ur";
/*      */ 
/*      */     
/* 1532 */     preparedStatement = this.m_conODS.prepareStatement(str2);
/* 1533 */     preparedStatement.setString(1, paramString1);
/* 1534 */     preparedStatement.setInt(2, paramInt);
/* 1535 */     preparedStatement.setString(3, paramString2);
/* 1536 */     preparedStatement.setString(4, paramString3);
/* 1537 */     preparedStatement.setString(5, paramString3);
/* 1538 */     resultSet = preparedStatement.executeQuery();
/* 1539 */     if (resultSet.next()) {
/* 1540 */       str1 = resultSet.getString("attributevalue");
/*      */     }
/* 1542 */     resultSet.close();
/* 1543 */     preparedStatement.close();
/* 1544 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getEntityTextValue(String paramString1, int paramInt, String paramString2, String paramString3) throws SQLException {
/* 1554 */     String str1 = "";
/* 1555 */     PreparedStatement preparedStatement = null;
/* 1556 */     ResultSet resultSet = null;
/* 1557 */     String str2 = " select attributevalue from opicm.text where entitytype=? and entityid=? and attributecode=?  and effto > ? and valto > ?  and enterprise= 'SG' with ur";
/*      */ 
/*      */     
/* 1560 */     preparedStatement = this.m_conODS.prepareStatement(str2);
/* 1561 */     preparedStatement.setString(1, paramString1);
/* 1562 */     preparedStatement.setInt(2, paramInt);
/* 1563 */     preparedStatement.setString(3, paramString2);
/* 1564 */     preparedStatement.setString(4, paramString3);
/* 1565 */     preparedStatement.setString(5, paramString3);
/* 1566 */     resultSet = preparedStatement.executeQuery();
/* 1567 */     if (resultSet.next()) {
/* 1568 */       str1 = resultSet.getString("attributevalue");
/*      */     }
/* 1570 */     resultSet.close();
/* 1571 */     preparedStatement.close();
/* 1572 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Vector getEntityFlagMulti(String paramString1, int paramInt, String paramString2, String paramString3) throws SQLException {
/* 1582 */     Vector<String> vector = new Vector();
/* 1583 */     PreparedStatement preparedStatement = null;
/* 1584 */     ResultSet resultSet = null;
/* 1585 */     String str = " select attributevalue from opicm.flag where entitytype=? and entityid=? and attributecode=?  and effto > ? and valto > ? and enterprise= 'SG' with ur";
/*      */ 
/*      */     
/* 1588 */     preparedStatement = this.m_conODS.prepareStatement(str);
/*      */ 
/*      */     
/* 1591 */     preparedStatement.setString(1, paramString1);
/* 1592 */     preparedStatement.setInt(2, paramInt);
/* 1593 */     preparedStatement.setString(3, paramString2);
/* 1594 */     preparedStatement.setString(4, paramString3);
/* 1595 */     preparedStatement.setString(5, paramString3);
/* 1596 */     resultSet = preparedStatement.executeQuery();
/* 1597 */     while (resultSet.next()) {
/* 1598 */       vector.add(resultSet.getString("attributevalue"));
/*      */     }
/* 1600 */     resultSet.close();
/* 1601 */     preparedStatement.close();
/* 1602 */     return vector;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setWWCOMPAT(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, int paramInt2, String paramString5, String paramString6, int paramInt3, String paramString7, String paramString8, int paramInt4, String paramString9, String paramString10, String paramString11, String paramString12, Vector<WWCOMPAT> paramVector) {
/* 1625 */     WWCOMPAT wWCOMPAT = new WWCOMPAT();
/* 1626 */     wWCOMPAT.setBRANDCD_FC(paramString1);
/* 1627 */     wWCOMPAT.setSystemEntityType(paramString2);
/* 1628 */     wWCOMPAT.setSystemEntityId(paramInt1);
/* 1629 */     wWCOMPAT.setSystemOS(paramString3);
/* 1630 */     wWCOMPAT.setGroupEntityType(paramString4);
/* 1631 */     wWCOMPAT.setGroupEntityId(paramInt2);
/* 1632 */     wWCOMPAT.setOKTOPUB(paramString5);
/* 1633 */     wWCOMPAT.setOSEntityType(paramString6);
/* 1634 */     wWCOMPAT.setOSEntityId(paramInt3);
/* 1635 */     wWCOMPAT.setOS(paramString7);
/* 1636 */     wWCOMPAT.setOptionEntityType(paramString8);
/* 1637 */     wWCOMPAT.setOptionEntityId(paramInt4);
/* 1638 */     wWCOMPAT.setCompatibilityPublishingFlag(paramString9);
/* 1639 */     wWCOMPAT.setRelationshipType(paramString10);
/* 1640 */     wWCOMPAT.setPublishFrom(paramString11);
/* 1641 */     wWCOMPAT.setPublishTo(paramString12);
/* 1642 */     paramVector.add(wWCOMPAT);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStatusAttr() {
/* 1648 */     return "STATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/* 1657 */     return "$Revision: 1.1 $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setConnection() throws SQLException {
/* 1666 */     if (this.m_conODS == null) {
/* 1667 */       this
/* 1668 */         .m_conODS = DriverManager.getConnection(
/* 1669 */           MiddlewareServerProperties.getPDHDatabaseURL(), 
/* 1670 */           MiddlewareServerProperties.getPDHDatabaseUser(), 
/* 1671 */           MiddlewareServerProperties.getPDHDatabasePassword());
/* 1672 */       this.m_conODS.setAutoCommit(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Connection getConnection() throws SQLException {
/* 1682 */     if (this.m_conODS == null) {
/* 1683 */       setConnection();
/*      */     }
/* 1685 */     return this.m_conODS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void releaseConn() {
/* 1693 */     this.m_conODS = null;
/*      */   }
/*      */   public String getMQCID() {
/* 1696 */     return null;
/*      */   }
/*      */   public String getCommMODELCGSql(String paramString) {
/* 1699 */     StringBuffer stringBuffer = new StringBuffer(" select                     \r\n   MODEL.entitytype                 as SystemEntityType,                  \r\n   MODEL.entityid                   as SystemEntityId,                    \r\n   MODELCG.entitytype               as GroupEntityType,                   \r\n   MODELCG.entityid                 as GroupEntityId,                     \r\n   MODELCGOS.entitytype             as OSEntityType,                      \r\n   MODELCGOS.entityid               as OSEntityId,                        \r\n   MDLCGOSMDL.entityid              as OSOPTIONId,                        \r\n   MDLCGOSMDL.entitytype            as OSOPTIONType,                      \r\n   MODEL2.entitytype                as OptionEntityType,                  \r\n   MODEL2.entityid                  as OptionEntityID                     \r\n from opicm.flag MODELCG                                                  \r\n inner join opicm.relator           as MDLCGMDL on                        \r\n    MDLCGMDL.entity1type            = 'MODELCG' and                       \r\n    MDLCGMDL.entity2type            = 'MODEL'  and                        \r\n    MDLCGMDL.entity1id              = MODELCG.entityid and                \r\n    MDLCGMDL.valto                  > current timestamp and               \r\n    MDLCGMDL.effto                  > current timestamp and               \r\n    MDLCGMDL.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as MODEL on                           \r\n    MODEL.entitytype                = 'MODEL' and                         \r\n    MODEL.entityid                  = MDLCGMDL.entity2id and              \r\n    MODEL.valto                     > current timestamp and               \r\n    MODEL.effto                     > current timestamp and               \r\n    MODEL.attributecode             = 'STATUS' and                        \r\n    MODEL.attributevalue            in('0040','0020') and              \r\n    MODEL.ENTERPRISE='SG'                                                 \r\n inner join opicm.relator           as MDLCGMDLCGOS on                    \r\n    MDLCGMDLCGOS.entity1type        = 'MODELCG' and                       \r\n    MDLCGMDLCGOS.entity2type        = 'MODELCGOS' and                     \r\n    MDLCGMDLCGOS.entity1id          = MODELCG.entityid and                \r\n    MDLCGMDLCGOS.valto              > current timestamp and               \r\n    MDLCGMDLCGOS.effto              > current timestamp and               \r\n    MDLCGMDLCGOS.ENTERPRISE         = 'SG'                                \r\n inner join opicm.flag              as MODELCGOS on                       \r\n    MODELCGOS.entitytype            = 'MODELCGOS' and                     \r\n    MODELCGOS.entityid              = MDLCGMDLCGOS.entity2id and          \r\n    MODELCGOS.valto                 > current timestamp and               \r\n    MODELCGOS.effto                 > current timestamp and               \r\n    MODELCGOS.attributecode         = 'STATUS' and                        \r\n    MODELCGOS.attributevalue        in('0040','0020') and                 \r\n    MODELCGOS.ENTERPRISE            = 'SG'                                \r\n inner join opicm.relator            as MDLCGOSMDL on                     \r\n    MDLCGOSMDL.entity1type          = 'MODELCGOS' and                     \r\n    MDLCGOSMDL.entity2type          = 'MODEL' and                         \r\n    MDLCGOSMDL.entity1id            = MODELCGOS.entityid and              \r\n    MDLCGOSMDL.valto                > current timestamp and               \r\n    MDLCGOSMDL.effto                > current timestamp and               \r\n    MDLCGOSMDL.ENTERPRISE           = 'SG'                                \r\n inner join opicm.flag              as OSOPTION  on                       \r\n    OSOPTION.entitytype             = MDLCGOSMDL.entitytype and           \r\n    OSOPTION.entityid               = MDLCGOSMDL.entityid and             \r\n    OSOPTION.valto                  > current timestamp and               \r\n    OSOPTION.effto                  > current timestamp and               \r\n    OSOPTION.attributecode          = 'STATUS' and                        \r\n    OSOPTION.attributevalue         in('0040','0020') and                 \r\n    OSOPTION.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as MODEL2 on                          \r\n    MODEL2.entitytype               = 'MODEL' and                         \r\n    MODEL2.entityid                 = MDLCGOSMDL.entity2id and            \r\n    MODEL2.valto                    > current timestamp and               \r\n    MODEL2.effto                    > current timestamp and               \r\n    MODEL2.attributecode            = 'STATUS' and                        \r\n    MODEL2.attributevalue           in('0040','0020') and              \r\n    MODEL2.ENTERPRISE               = 'SG'                                \r\n where                                                                    \r\n    MODELCG.entitytype              = 'MODELCG' and                       \r\n    MODELCG.valto                   > current timestamp and               \r\n    MODELCG.effto                   > current timestamp and               \r\n    MODELCG.attributecode           = 'STATUS' and                        \r\n    MODELCG.attributevalue          in('0040','0020') and                 \r\n    MODELCG.ENTERPRISE              = 'SG' and                            \r\n");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1771 */     stringBuffer.append(paramString);
/* 1772 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommSEOCGSql(String paramString) {
/* 1781 */     StringBuffer stringBuffer = new StringBuffer(" select                                        \r\n    SystemGroup.SystemId          as SystemEntityId,                                           \r\n    SystemGroup.SystemType        as SystemEntityType,                                         \r\n    SystemGroup.GroupId           as GroupEntityId,                                            \r\n    SystemGroup.GroupType         as GroupEntityType,                                          \r\n    OSOption.OSId                 as OSEntityId,                                               \r\n    OSOption.OSType               as OSEntityType,                                             \r\n    OSOption.OSOPTIONId           as OSOPTIONId,                                               \r\n    OSOption.OSOPTIONType         as OSOPTIONType,                                             \r\n    OSOption.OptionId             as OptionEntityID,                                           \r\n    OSOption.OptionType           as OptionEntityType                                          \r\n from                                                                     \r\n (                                                                        \r\n select                                                                   \r\n   SEOCG.entityid                   as GroupId,                           \r\n   SEOCG.entityType                 as GroupType,                         \r\n   LSEOBUNDLE.entityid              as SystemId,                          \r\n   LSEOBUNDLE.entityType            as SystemType                         \r\n from opicm.flag                    as SEOCG                              \r\n inner join opicm.relator           as SEOCGBDL on                        \r\n    SEOCGBDL.entity1type            = 'SEOCG' and                         \r\n    SEOCGBDL.entity2type            = 'LSEOBUNDLE' and                    \r\n    SEOCGBDL.entity1id              = SEOCG.entityid and                  \r\n    SEOCGBDL.valto                  > current timestamp and               \r\n    SEOCGBDL.effto                  > current timestamp and               \r\n    SEOCGBDL.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as LSEOBUNDLE on                      \r\n    LSEOBUNDLE.entitytype           = 'LSEOBUNDLE' and                    \r\n    LSEOBUNDLE.entityid             = SEOCGBDL.entity2id and              \r\n    LSEOBUNDLE.valto                > current timestamp and               \r\n    LSEOBUNDLE.effto                > current timestamp and               \r\n    LSEOBUNDLE.attributecode        = 'STATUS' and                        \r\n    LSEOBUNDLE.attributevalue       in('0040','0020') and              \r\n    LSEOBUNDLE.ENTERPRISE           = 'SG'                                \r\n where                                                                    \r\n    SEOCG.entitytype                = 'SEOCG' and                         \r\n    SEOCG.valto                     > current timestamp and               \r\n    SEOCG.effto                     > current timestamp and               \r\n    SEOCG.attributecode             = 'STATUS' and                        \r\n    SEOCG.attributevalue            in('0040','0020') and                 \r\n    SEOCG.ENTERPRISE                = 'SG'                                \r\n union all                                                                \r\n select                                                                   \r\n   SEOCG.entityid                   as GroupId,                           \r\n   SEOCG.entityType                 as GroupType,                         \r\n   MODEL.entityid                   as SystemId,                          \r\n   MODEL.entityType                 as SystemType                         \r\n from opicm.flag                    as SEOCG                              \r\n inner join opicm.relator           as SEOCGMDL on                        \r\n    SEOCGMDL.entity1type            = 'SEOCG' and                         \r\n    SEOCGMDL.entity2type            = 'MODEL'  and                        \r\n    SEOCGMDL.entity1id              = SEOCG.entityid and                  \r\n    SEOCGMDL.valto                  > current timestamp and               \r\n    SEOCGMDL.effto                  > current timestamp and               \r\n    SEOCGMDL.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as MODEL on                           \r\n    MODEL.entitytype                = 'MODEL' and                         \r\n    MODEL.entityid                  = SEOCGMDL.entity2id and              \r\n    MODEL.valto                     > current timestamp and               \r\n    MODEL.effto                     > current timestamp and               \r\n    MODEL.attributecode             = 'STATUS' and                        \r\n    MODEL.attributevalue            in('0040','0020') and              \r\n    MODEL.ENTERPRISE                = 'SG'                                \r\n where                                                                    \r\n    SEOCG.entitytype                = 'SEOCG' and                         \r\n    SEOCG.valto                     > current timestamp and               \r\n    SEOCG.effto                     > current timestamp and               \r\n    SEOCG.attributecode             = 'STATUS' and                        \r\n    SEOCG.attributevalue            in('0040','0020') and                 \r\n    SEOCG.ENTERPRISE                = 'SG'                                \r\n union all                                                                \r\n select                                                                   \r\n   SEOCG.entityid                   as GroupId,                           \r\n   SEOCG.entityType                 as GroupType,                         \r\n   WWSEO.entityid                   as SystemId,                          \r\n   WWSEO.entityType                 as SystemType                         \r\n from opicm.flag                    as SEOCG                              \r\n inner join opicm.relator           as SEOCGSEO on                        \r\n    SEOCGSEO.entity1type            = 'SEOCG' and                         \r\n    SEOCGSEO.entity2type            = 'WWSEO'  and                        \r\n    SEOCGSEO.entity1id              = SEOCG.entityid and                  \r\n    SEOCGSEO.valto                  > current timestamp and               \r\n    SEOCGSEO.effto                  > current timestamp and               \r\n    SEOCGSEO.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as WWSEO on                           \r\n    WWSEO.entitytype                = 'WWSEO' and                         \r\n    WWSEO.entityid                  = SEOCGSEO.entity2id and              \r\n    WWSEO.valto                     > current timestamp and               \r\n    WWSEO.effto                     > current timestamp and               \r\n    WWSEO.attributecode             = 'STATUS' and                        \r\n    WWSEO.attributevalue            in('0040','0020') and              \r\n    WWSEO.ENTERPRISE                = 'SG'                                \r\n where                                                                    \r\n    SEOCG.entitytype                = 'SEOCG' and                         \r\n    SEOCG.valto                     > current timestamp and               \r\n    SEOCG.effto                     > current timestamp and               \r\n    SEOCG.attributecode             = 'STATUS' and                        \r\n    SEOCG.attributevalue            in('0040','0020') and                 \r\n    SEOCG.ENTERPRISE                = 'SG'                                \r\n )                                                                        \r\n as SystemGroup                                                           \r\n inner join opicm.relator           as SEOCGSEOCGOS on                    \r\n    SEOCGSEOCGOS.entity1type        = 'SEOCG' and                         \r\n    SEOCGSEOCGOS.entity2type        = 'SEOCGOS'  and                      \r\n    SEOCGSEOCGOS.entity1id          = SystemGroup.GroupId and             \r\n    SEOCGSEOCGOS.valto              > current timestamp and               \r\n    SEOCGSEOCGOS.effto              > current timestamp and               \r\n    SEOCGSEOCGOS.ENTERPRISE         = 'SG'                                \r\n inner join                                                               \r\n (                                                                        \r\n select                                                                   \r\n   SEOCGOS.entityid                 as OSId,                              \r\n   SEOCGOS.entitytype               as OSType,                            \r\n   SEOCGOSBDL.entityid              as OSOPTIONId,                        \r\n   SEOCGOSBDL.entitytype            as OSOPTIONType,                      \r\n   LSEOBUNDLE.entityid              as OptionId,                          \r\n   LSEOBUNDLE.entityType            as OptionType                         \r\n from opicm.flag                    as SEOCGOS                            \r\n inner join opicm.relator           as SEOCGOSBDL on                      \r\n    SEOCGOSBDL.entity1type          = 'SEOCGOS' and                       \r\n    SEOCGOSBDL.entity2type          = 'LSEOBUNDLE'  and                   \r\n    SEOCGOSBDL.entity1id            = SEOCGOS.entityid and                \r\n    SEOCGOSBDL.valto                > current timestamp and               \r\n    SEOCGOSBDL.effto                > current timestamp and               \r\n    SEOCGOSBDL.ENTERPRISE           = 'SG'                                \r\n inner join opicm.flag              as OSOPTION  on                       \r\n    OSOPTION.entitytype             = SEOCGOSBDL.entitytype and           \r\n    OSOPTION.entityid               = SEOCGOSBDL.entityid and             \r\n    OSOPTION.valto                  > current timestamp and               \r\n    OSOPTION.effto                  > current timestamp and               \r\n    OSOPTION.attributecode          = 'STATUS' and                        \r\n    OSOPTION.attributevalue         in('0040','0020') and                 \r\n    OSOPTION.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as LSEOBUNDLE on                      \r\n    LSEOBUNDLE.entitytype           = 'LSEOBUNDLE' and                    \r\n    LSEOBUNDLE.entityid             = SEOCGOSBDL.entity2id and            \r\n    LSEOBUNDLE.valto                > current timestamp and               \r\n    LSEOBUNDLE.effto                > current timestamp and               \r\n    LSEOBUNDLE.attributecode        = 'STATUS' and                        \r\n    LSEOBUNDLE.attributevalue       in('0040','0020') and              \r\n    LSEOBUNDLE.ENTERPRISE           = 'SG'                                \r\n where                                                                    \r\n    SEOCGOS.entitytype              = 'SEOCGOS' and                       \r\n    SEOCGOS.valto                   > current timestamp and               \r\n    SEOCGOS.effto                   > current timestamp and               \r\n    SEOCGOS.attributecode           = 'STATUS' and                        \r\n    SEOCGOS.attributevalue          in('0040','0020') and                 \r\n    SEOCGOS.ENTERPRISE              = 'SG'                                \r\n union all                                                                \r\n select                                                                   \r\n     SEOCGOS.entityid               as OSId,                              \r\n     SEOCGOS.entitytype             as OSType,                            \r\n     SEOCGOSSVCSEO.entityid         as OSOPTIONId,                        \r\n     SEOCGOSSVCSEO.entitytype       as OSOPTIONType,                      \r\n     SVCSEO.entityid                as OptionId,                          \r\n     SVCSEO.entityType              as OptionType                         \r\n from opicm.flag                    as SEOCGOS                            \r\n inner join opicm.relator           as SEOCGOSSVCSEO on                   \r\n    SEOCGOSSVCSEO.entity1type       = 'SEOCGOS' and                       \r\n    SEOCGOSSVCSEO.entity2type       = 'SVCSEO'  and                       \r\n    SEOCGOSSVCSEO.entity1id         = SEOCGOS.entityid and                \r\n    SEOCGOSSVCSEO.valto             > current timestamp and               \r\n    SEOCGOSSVCSEO.effto             > current timestamp and               \r\n    SEOCGOSSVCSEO.ENTERPRISE        = 'SG'                                \r\n  inner join opicm.flag             as OSOPTION  on                       \r\n    OSOPTION.entitytype             = SEOCGOSSVCSEO.entitytype and        \r\n    OSOPTION.entityid               = SEOCGOSSVCSEO.entityid and          \r\n    OSOPTION.valto                  > current timestamp and               \r\n    OSOPTION.effto                  > current timestamp and               \r\n    OSOPTION.attributecode          = 'STATUS' and                        \r\n    OSOPTION.attributevalue         in('0040','0020') and                 \r\n    OSOPTION.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as SVCSEO on                          \r\n    SVCSEO.entitytype               = 'SVCSEO' and                        \r\n    SVCSEO.entityid                 = SEOCGOSSVCSEO.entity2id and         \r\n    SVCSEO.valto                    > current timestamp and               \r\n    SVCSEO.effto                    > current timestamp and               \r\n    SVCSEO.attributecode            = 'STATUS' and                        \r\n    SVCSEO.attributevalue           in('0040','0020') and              \r\n    SVCSEO.ENTERPRISE               = 'SG'                                \r\n where                                                                    \r\n    SEOCGOS.entitytype              = 'SEOCGOS' and                       \r\n    SEOCGOS.valto                   > current timestamp and               \r\n    SEOCGOS.effto                   > current timestamp and               \r\n    SEOCGOS.attributecode           = 'STATUS' and                        \r\n    SEOCGOS.attributevalue          in('0040','0020') and                 \r\n    SEOCGOS.ENTERPRISE              = 'SG'                                \r\n union all                                                                \r\n select                                                                   \r\n     SEOCGOS.entityid               as OSId,                              \r\n     SEOCGOS.entitytype             as OSType,                            \r\n     SEOCGOSSEO.entityid            as OSOPTIONId,                        \r\n     SEOCGOSSEO.entitytype          as OSOPTIONType,                      \r\n     WWSEO.entityid                 as OptionId,                          \r\n     WWSEO.entityType               as OptionType                         \r\n from opicm.flag                    as SEOCGOS                            \r\n inner join opicm.relator           as SEOCGOSSEO on                      \r\n    SEOCGOSSEO.entity1type          = 'SEOCGOS' and                       \r\n    SEOCGOSSEO.entity2type          = 'WWSEO'  and                        \r\n    SEOCGOSSEO.entity1id            = SEOCGOS.entityid and                \r\n    SEOCGOSSEO.valto                > current timestamp and               \r\n    SEOCGOSSEO.effto                > current timestamp and               \r\n    SEOCGOSSEO.ENTERPRISE           = 'SG'                                \r\n inner join opicm.flag              as OSOPTION  on                       \r\n    OSOPTION.entitytype             = SEOCGOSSEO.entitytype and           \r\n    OSOPTION.entityid               = SEOCGOSSEO.entityid and             \r\n    OSOPTION.valto                  > current timestamp and               \r\n    OSOPTION.effto                  > current timestamp and               \r\n    OSOPTION.attributecode          = 'STATUS' and                        \r\n    OSOPTION.attributevalue         in('0040','0020') and                 \r\n    OSOPTION.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as WWSEO on                           \r\n    WWSEO.entitytype                = 'WWSEO' and                         \r\n    WWSEO.entityid                  = SEOCGOSSEO.entity2id and            \r\n    WWSEO.valto                     > current timestamp and               \r\n    WWSEO.effto                     > current timestamp and               \r\n    WWSEO.attributecode             = 'STATUS' and                        \r\n    WWSEO.attributevalue            in('0040','0020') and              \r\n    WWSEO.ENTERPRISE                = 'SG'                                \r\n where                                                                    \r\n    SEOCGOS.entitytype              = 'SEOCGOS' and                       \r\n    SEOCGOS.valto                   > current timestamp and               \r\n    SEOCGOS.effto                   > current timestamp and               \r\n    SEOCGOS.attributecode           = 'STATUS' and                        \r\n    SEOCGOS.attributevalue          in('0040','0020') and                 \r\n    SEOCGOS.ENTERPRISE              = 'SG'                                \r\n                                                                          \r\n ) as OSOption on                                                         \r\n    OSOption.OSType                 = 'SEOCGOS' and                       \r\n    OSOption.OSId                   = SEOCGSEOCGOS.entity2id              \r\n");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2011 */     stringBuffer.append(paramString);
/* 2012 */     return stringBuffer.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSCOMPATGEN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */