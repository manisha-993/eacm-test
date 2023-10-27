/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*      */ import com.ibm.eacm.AES256Utils;
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
/*   64 */   static String m_strODSSchema = "GBLI.";
/*   65 */   static String m_tablename = "WWTECHCOMPAT";
/*   66 */   static String OSIndependent = "10589";
/*   67 */   protected Connection m_conODS = null;
/*   68 */   int count = 0;
/*   69 */   int decount = 0;
/*   70 */   int updatecount = 0;
/*   71 */   int errcount = 0;
/*      */ 
/*      */   
/*   74 */   static final Hashtable Attr_OS = new Hashtable<>(); static {
/*   75 */     Attr_OS.put("MODEL", "OSLEVEL");
/*   76 */     Attr_OS.put("LSEOBUNDLE", "OSLEVEL");
/*   77 */     Attr_OS.put("MODELCGOS", "OS");
/*   78 */     Attr_OS.put("SEOCGOS", "OS");
/*   79 */     Attr_OS.put("WWSEO", "OS");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   84 */     String str = ABRServerProperties.getValue("COMPATGENABRSTATUS", "_CHUNKSIZE", "100000");
/*      */     
/*   86 */     WWCOMPAT_ROW_LIMIT = Integer.parseInt(str);
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
/*  103 */     process(paramADSABRSTATUS, paramProfile1, paramProfile2, getVeName(), paramEntityItem);
/*  104 */     if (!getVeName2().equals("dummy")) {
/*  105 */       process(paramADSABRSTATUS, paramProfile1, paramProfile2, getVeName2(), paramEntityItem);
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
/*  120 */     long l1 = System.currentTimeMillis();
/*  121 */     long l2 = 0L;
/*      */ 
/*      */ 
/*      */     
/*  125 */     EntityList entityList1 = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, paramString, paramEntityItem);
/*      */     
/*  127 */     EntityList entityList2 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, paramString, paramEntityItem);
/*  128 */     l2 = System.currentTimeMillis();
/*  129 */     paramADSABRSTATUS.addDebug("Time for both pulls: " + Stopwatch.format(l2 - l1));
/*  130 */     l1 = l2;
/*      */ 
/*      */ 
/*      */     
/*  134 */     Hashtable<String, String> hashtable = ((ExtractActionItem)entityList1.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, paramEntityItem
/*  135 */         .getEntityType());
/*      */     
/*  137 */     if (getVeName().equals("ADSWWCOMPATMDLCGOSMDL1")) {
/*  138 */       hashtable.put("0MODELCGOSU", "Hi");
/*  139 */       hashtable.put("0MODELD", "Hi");
/*      */     } 
/*  141 */     if (getVeName().equals("ADSWWCOMPATSEOCGOSBDL")) {
/*  142 */       hashtable.put("0SEOCGOSU", "Hi");
/*  143 */       hashtable.put("0LSEOBUNDLED", "Hi");
/*      */     } 
/*  145 */     if (getVeName().equals("ADSWWCOMPATSEOCGOSSEO")) {
/*  146 */       hashtable.put("0SEOCGOSU", "Hi");
/*  147 */       hashtable.put("0WWSEOD", "Hi");
/*      */     } 
/*  149 */     if (getVeName().equals("ADSWWCOMPATSEOCGOSSVCSEO")) {
/*  150 */       hashtable.put("0SEOCGOSU", "Hi");
/*  151 */       hashtable.put("0SVCSEOD", "Hi");
/*      */     } 
/*      */     
/*  154 */     DiffVE diffVE = new DiffVE(entityList2, entityList1, hashtable);
/*  155 */     diffVE.setCheckAllNLS(true);
/*  156 */     paramADSABRSTATUS.addDebug("hshMap: " + hashtable);
/*  157 */     paramADSABRSTATUS.addDebug("time1 flattened VE: " + diffVE.getPriorDiffVE());
/*  158 */     paramADSABRSTATUS.addDebug("time2 flattened VE: " + diffVE.getCurrentDiffVE());
/*      */ 
/*      */     
/*  161 */     Vector<DiffEntity> vector = diffVE.diffVE();
/*  162 */     paramADSABRSTATUS.addDebug(" diffVE info:\n" + diffVE.getDebug());
/*  163 */     paramADSABRSTATUS.addDebug(" diffVE flattened VE: " + vector);
/*      */     
/*  165 */     l2 = System.currentTimeMillis();
/*  166 */     paramADSABRSTATUS.addDebug(" Time for diff: " + Stopwatch.format(l2 - l1));
/*  167 */     l1 = l2;
/*      */ 
/*      */     
/*  170 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  171 */     boolean bool = false; byte b;
/*  172 */     for (b = 0; b < vector.size(); b++) {
/*  173 */       DiffEntity diffEntity = vector.elementAt(b);
/*      */       
/*  175 */       bool = (bool || diffEntity.isChanged()) ? true : false;
/*      */     } 
/*  177 */     for (b = 0; b < vector.size(); b++) {
/*  178 */       DiffEntity diffEntity = vector.elementAt(b);
/*      */       
/*  180 */       bool = (bool || diffEntity.isChanged()) ? true : false;
/*      */ 
/*      */ 
/*      */       
/*  184 */       hashtable1.put(diffEntity.getKey(), diffEntity);
/*      */       
/*  186 */       String str = diffEntity.getEntityType();
/*  187 */       if (diffEntity.isRoot()) {
/*  188 */         str = "ROOT";
/*      */       }
/*  190 */       Vector<DiffEntity> vector1 = (Vector)hashtable1.get(str);
/*  191 */       if (vector1 == null) {
/*  192 */         vector1 = new Vector();
/*  193 */         hashtable1.put(str, vector1);
/*      */       } 
/*  195 */       vector1.add(diffEntity);
/*      */     } 
/*      */     
/*  198 */     if (bool) {
/*      */       
/*  200 */       for (b = 0; b < entityList1.getEntityGroupCount(); b++) {
/*  201 */         String str = entityList1.getEntityGroup(b).getEntityType();
/*  202 */         Vector vector1 = (Vector)hashtable1.get(str);
/*  203 */         if (vector1 == null) {
/*  204 */           vector1 = new Vector();
/*  205 */           hashtable1.put(str, vector1);
/*      */         } 
/*      */       } 
/*      */       
/*  209 */       hashtable1.put("VeName", paramString);
/*  210 */       populateTable(paramADSABRSTATUS, paramProfile1, paramProfile2, hashtable1);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  215 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", paramEntityItem.getKey());
/*      */     } 
/*      */ 
/*      */     
/*  219 */     entityList2.dereference();
/*  220 */     entityList1.dereference();
/*  221 */     hashtable.clear();
/*  222 */     vector.clear();
/*  223 */     diffVE.dereference();
/*      */     
/*  225 */     for (Enumeration<Object> enumeration = hashtable1.elements(); enumeration.hasMoreElements(); ) {
/*  226 */       Vector vector1 = (Vector)enumeration.nextElement();
/*  227 */       if (vector1 instanceof Vector) {
/*  228 */         ((Vector)vector1).clear();
/*      */       }
/*      */     } 
/*  231 */     hashtable1.clear();
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
/*  259 */     setConnection();
/*  260 */     this.errcount = 0;
/*  261 */     this.decount = 0;
/*  262 */     this.updatecount = 0;
/*  263 */     this.count = 0;
/*  264 */     getModelsByOS(paramADSABRSTATUS, paramHashtable, paramProfile2.getNow(), paramProfile2.getEndOfDay());
/*  265 */     if (this.decount > 0)
/*  266 */       paramADSABRSTATUS.addOutput("ADSWWCOMPATABR found deactivate count:" + this.decount + " records be deactivated from table. Error count :" + this.errcount); 
/*  267 */     if (this.updatecount > 0)
/*  268 */       paramADSABRSTATUS.addOutput("ADSWWCOMPATABR found updated count:" + this.updatecount + " records be updated. Error count :" + this.errcount); 
/*  269 */     if (this.count > 0) {
/*  270 */       paramADSABRSTATUS.addOutput("ADSWWCOMPATABR found count:" + this.count + " records be inserted into table. Error count :" + this.errcount);
/*      */     }
/*  272 */     releaseConn();
/*  273 */     System.gc();
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
/*  286 */     for (byte b = 0; b < paramVector.size(); b++) {
/*  287 */       WWCOMPAT wWCOMPAT = paramVector.elementAt(b);
/*  288 */       UpdateWWTECHCOMPAT(paramADSABRSTATUS, wWCOMPAT, paramString1, paramString2);
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
/*  307 */     PreparedStatement preparedStatement = null;
/*      */     
/*  309 */     int i = 0;
/*  310 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?, CompatibilityPublishingFlag = ?, RelationshipType = ?, PublishFrom = ?, PublishTo = ?  WHERE  OptionEntityType = ?  AND OptionEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  320 */       setConnection();
/*  321 */       paramADSABRSTATUS.addDebug(" Beging to execute updateCOMPATPUBFLG SQL : " + paramString2 + "|" + paramInt2 + "|" + paramString1 + "|" + paramInt1 + "|" + paramString3);
/*  322 */       preparedStatement = null;
/*  323 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  324 */       preparedStatement.setString(1, "C");
/*  325 */       preparedStatement.setString(2, paramString7);
/*  326 */       preparedStatement.setString(3, paramString8);
/*  327 */       preparedStatement.setString(4, paramString3);
/*  328 */       preparedStatement.setString(5, paramString6);
/*  329 */       preparedStatement.setString(6, paramString4);
/*  330 */       preparedStatement.setString(7, paramString5);
/*  331 */       preparedStatement.setString(8, paramString2);
/*  332 */       preparedStatement.setInt(9, paramInt2);
/*  333 */       preparedStatement.setString(10, paramString1);
/*  334 */       preparedStatement.setInt(11, paramInt1);
/*  335 */       preparedStatement.setString(12, paramString8);
/*  336 */       i = preparedStatement.executeUpdate();
/*  337 */       this.updatecount += i;
/*  338 */       paramADSABRSTATUS.addDebug(" end to execute updateCOMPATPUBFLG SQL, the update count is : " + i);
/*  339 */     } catch (SQLException sQLException) {
/*  340 */       this.errcount++;
/*  341 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString2 + "|" + paramInt2 + "|" + paramString1 + "|" + paramInt1 + "|" + paramString3 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  345 */         this.m_conODS.commit();
/*  346 */         if (preparedStatement != null) {
/*  347 */           preparedStatement.close();
/*  348 */           preparedStatement = null;
/*      */         } 
/*  350 */       } catch (Exception exception) {
/*  351 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  354 */     return i;
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
/*  369 */     PreparedStatement preparedStatement = null;
/*      */     
/*  371 */     int i = 0;
/*  372 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ? WHERE  OptionEntityType = ?  AND OptionEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  382 */       setConnection();
/*  383 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateMDLCGOSMDL SQL : " + paramString2 + "|" + paramInt2 + "|" + paramString1 + "|" + paramInt1);
/*  384 */       preparedStatement = null;
/*  385 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  386 */       preparedStatement.setString(1, "D");
/*  387 */       preparedStatement.setString(2, paramString3);
/*  388 */       preparedStatement.setString(3, paramString4);
/*  389 */       preparedStatement.setString(4, paramString2);
/*  390 */       preparedStatement.setInt(5, paramInt2);
/*  391 */       preparedStatement.setString(6, paramString1);
/*  392 */       preparedStatement.setInt(7, paramInt1);
/*  393 */       preparedStatement.setString(8, paramString4);
/*  394 */       i = preparedStatement.executeUpdate();
/*  395 */       this.decount += i;
/*  396 */       paramADSABRSTATUS.addDebug(" end to execute deActivateMDLCGOSMDL SQL, the deactivate count is : " + i);
/*  397 */     } catch (SQLException sQLException) {
/*  398 */       this.errcount++;
/*  399 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString2 + "|" + paramInt2 + "|" + paramString1 + "|" + paramInt1 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  403 */         this.m_conODS.commit();
/*  404 */         if (preparedStatement != null) {
/*  405 */           preparedStatement.close();
/*  406 */           preparedStatement = null;
/*      */         } 
/*  408 */       } catch (Exception exception) {
/*  409 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  412 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int deActivateSystemOS(ADSABRSTATUS paramADSABRSTATUS, String paramString1, int paramInt, Vector<String> paramVector, String paramString2, String paramString3) {
/*  417 */     PreparedStatement preparedStatement = null;
/*      */     
/*  419 */     int i = 0;
/*  420 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE  SystemEntityType = ?  AND SystemEntityId = ?  AND SystemOS = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  429 */       setConnection();
/*  430 */       for (byte b = 0; b < paramVector.size(); b++) {
/*  431 */         String str1 = paramVector.elementAt(b);
/*  432 */         paramADSABRSTATUS.addDebug(" Beging to execute deActivateSystemOS SQL : " + paramString1 + "|" + paramInt + "|" + str1);
/*  433 */         preparedStatement = null;
/*  434 */         preparedStatement = this.m_conODS.prepareStatement(str);
/*  435 */         preparedStatement.setString(1, "D");
/*  436 */         preparedStatement.setString(2, paramString2);
/*  437 */         preparedStatement.setString(3, paramString3);
/*  438 */         preparedStatement.setString(4, paramString1);
/*  439 */         preparedStatement.setInt(5, paramInt);
/*  440 */         preparedStatement.setString(6, str1);
/*  441 */         preparedStatement.setString(7, paramString3);
/*  442 */         i = preparedStatement.executeUpdate();
/*  443 */         this.decount += i;
/*  444 */         paramADSABRSTATUS.addDebug(" end to execute deActivateSystemOS SQL, the deactivate count is : " + i);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*  450 */     catch (SQLException sQLException) {
/*  451 */       this.errcount++;
/*  452 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString1 + "|" + paramInt + "|" + paramVector + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  456 */         this.m_conODS.commit();
/*  457 */         if (preparedStatement != null) {
/*  458 */           preparedStatement.close();
/*  459 */           preparedStatement = null;
/*      */         } 
/*  461 */       } catch (Exception exception) {
/*  462 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  465 */     return i;
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
/*  480 */     PreparedStatement preparedStatement = null;
/*      */     
/*  482 */     int i = 0;
/*  483 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE  SystemEntityType = ?  AND SystemEntityId = ?  AND GroupEntityType = ?  AND GroupEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  493 */       setConnection();
/*  494 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateSystem SQL : " + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2);
/*  495 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  496 */       preparedStatement.setString(1, "D");
/*  497 */       preparedStatement.setString(2, paramString3);
/*  498 */       preparedStatement.setString(3, paramString4);
/*  499 */       preparedStatement.setString(4, paramString1);
/*  500 */       preparedStatement.setInt(5, paramInt1);
/*  501 */       preparedStatement.setString(6, paramString2);
/*  502 */       preparedStatement.setInt(7, paramInt2);
/*  503 */       preparedStatement.setString(8, paramString4);
/*  504 */       i = preparedStatement.executeUpdate();
/*  505 */       this.decount += i;
/*  506 */       paramADSABRSTATUS.addDebug(" end to execute deActivateSystem SQL, the deactivate count is : " + i);
/*      */     }
/*  508 */     catch (SQLException sQLException) {
/*  509 */       this.errcount++;
/*  510 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  514 */         this.m_conODS.commit();
/*  515 */         if (preparedStatement != null) {
/*  516 */           preparedStatement.close();
/*  517 */           preparedStatement = null;
/*      */         } 
/*  519 */       } catch (Exception exception) {
/*  520 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  523 */     return i;
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
/*  538 */     PreparedStatement preparedStatement = null;
/*      */     
/*  540 */     int i = 0;
/*  541 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE  GroupEntityType = ?  AND GroupEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  551 */       setConnection();
/*  552 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateCGOS SQL : " + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2);
/*  553 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  554 */       preparedStatement.setString(1, "D");
/*  555 */       preparedStatement.setString(2, paramString3);
/*  556 */       preparedStatement.setString(3, paramString4);
/*  557 */       preparedStatement.setString(4, paramString1);
/*  558 */       preparedStatement.setInt(5, paramInt1);
/*  559 */       preparedStatement.setString(6, paramString2);
/*  560 */       preparedStatement.setInt(7, paramInt2);
/*  561 */       preparedStatement.setString(8, paramString4);
/*  562 */       i = preparedStatement.executeUpdate();
/*  563 */       this.decount += i;
/*  564 */       paramADSABRSTATUS.addDebug(" end to execute deActivateCGOS SQL, the deactivate count is : " + i);
/*      */     }
/*  566 */     catch (SQLException sQLException) {
/*  567 */       this.errcount++;
/*  568 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  572 */         this.m_conODS.commit();
/*  573 */         if (preparedStatement != null) {
/*  574 */           preparedStatement.close();
/*  575 */           preparedStatement = null;
/*      */         } 
/*  577 */       } catch (Exception exception) {
/*  578 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  581 */     return i;
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
/*  595 */     PreparedStatement preparedStatement = null;
/*      */     
/*  597 */     int i = 0;
/*  598 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE OptionEntityType = ?  AND OptionEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  608 */       setConnection();
/*  609 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateOption SQL : " + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2);
/*  610 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  611 */       preparedStatement.setString(1, "D");
/*  612 */       preparedStatement.setString(2, paramString3);
/*  613 */       preparedStatement.setString(3, paramString4);
/*  614 */       preparedStatement.setString(4, paramString1);
/*  615 */       preparedStatement.setInt(5, paramInt1);
/*  616 */       preparedStatement.setString(6, paramString2);
/*  617 */       preparedStatement.setInt(7, paramInt2);
/*  618 */       preparedStatement.setString(8, paramString4);
/*  619 */       i = preparedStatement.executeUpdate();
/*  620 */       this.decount += i;
/*  621 */       paramADSABRSTATUS.addDebug(" end to execute deActivateOption SQL, the deactivate cound is : " + i);
/*      */     }
/*  623 */     catch (SQLException sQLException) {
/*  624 */       this.errcount++;
/*  625 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D" + paramString1 + "|" + paramInt1 + "|" + paramString2 + "|" + paramInt2 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  629 */         this.m_conODS.commit();
/*  630 */         if (preparedStatement != null) {
/*  631 */           preparedStatement.close();
/*  632 */           preparedStatement = null;
/*      */         } 
/*  634 */       } catch (Exception exception) {
/*  635 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  638 */     return i;
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
/*  651 */     PreparedStatement preparedStatement = null;
/*      */     
/*  653 */     int i = 0;
/*  654 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE GroupEntityType = ?  AND GroupEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  662 */       setConnection();
/*  663 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateCG SQL : |" + paramString1 + "|" + paramInt);
/*  664 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  665 */       preparedStatement.setString(1, "D");
/*  666 */       preparedStatement.setString(2, paramString2);
/*  667 */       preparedStatement.setString(3, paramString3);
/*  668 */       preparedStatement.setString(4, paramString1);
/*  669 */       preparedStatement.setInt(5, paramInt);
/*  670 */       preparedStatement.setString(6, paramString3);
/*  671 */       i = preparedStatement.executeUpdate();
/*  672 */       this.decount += i;
/*  673 */       paramADSABRSTATUS.addDebug(" end to execute deActivateCG SQL, the deactivate count is : " + i);
/*      */     }
/*  675 */     catch (SQLException sQLException) {
/*  676 */       this.errcount++;
/*  677 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D|" + paramString1 + "|" + paramInt + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  681 */         this.m_conODS.commit();
/*  682 */         if (preparedStatement != null) {
/*  683 */           preparedStatement.close();
/*  684 */           preparedStatement = null;
/*      */         } 
/*  686 */       } catch (Exception exception) {
/*  687 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  690 */     return i;
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
/*  703 */     PreparedStatement preparedStatement = null;
/*      */     
/*  705 */     int i = 0;
/*  706 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?  WHERE OSEntityType = ?  AND OSEntityId = ?  AND OS = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  715 */       setConnection();
/*  716 */       paramADSABRSTATUS.addDebug(" Beging to execute deActivateCGOS SQL : |" + paramString1 + "|" + paramInt + "|" + paramString2);
/*  717 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  718 */       preparedStatement.setString(1, "D");
/*  719 */       preparedStatement.setString(2, paramString3);
/*  720 */       preparedStatement.setString(3, paramString4);
/*  721 */       preparedStatement.setString(4, paramString1);
/*  722 */       preparedStatement.setInt(5, paramInt);
/*  723 */       preparedStatement.setString(6, paramString2);
/*  724 */       preparedStatement.setString(7, paramString4);
/*  725 */       i = preparedStatement.executeUpdate();
/*  726 */       this.decount += i;
/*  727 */       paramADSABRSTATUS.addDebug(" end to execute deActivateCGOS SQL, the deactivate count is : " + i);
/*      */     }
/*  729 */     catch (SQLException sQLException) {
/*  730 */       this.errcount++;
/*  731 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping update. Activity D|" + paramString1 + "|" + paramInt + "|" + paramString2 + " Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  735 */         this.m_conODS.commit();
/*  736 */         if (preparedStatement != null) {
/*  737 */           preparedStatement.close();
/*  738 */           preparedStatement = null;
/*      */         } 
/*  740 */       } catch (Exception exception) {
/*  741 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  744 */     return i;
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
/*  758 */     PreparedStatement preparedStatement = null;
/*      */     
/*  760 */     int i = 0;
/*  761 */     String str = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?, OKTOPUB = ?, BRANDCD_FC = ? WHERE GroupEntityType = ?  AND GroupEntityId = ?  AND TimeOfChange < ?  AND Activity <> 'D' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  769 */       setConnection();
/*  770 */       paramADSABRSTATUS.addDebug(" Beging to execute updateCGOKTOPUB SQL : |" + paramString1 + "|" + paramInt + "|" + paramString2 + "|" + paramString3);
/*  771 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  772 */       preparedStatement.setString(1, "C");
/*  773 */       preparedStatement.setString(2, paramString4);
/*  774 */       preparedStatement.setString(3, paramString5);
/*  775 */       preparedStatement.setString(4, paramString2);
/*  776 */       preparedStatement.setString(5, paramString3);
/*  777 */       preparedStatement.setString(6, paramString1);
/*  778 */       preparedStatement.setInt(7, paramInt);
/*  779 */       preparedStatement.setString(8, paramString5);
/*  780 */       i = preparedStatement.executeUpdate();
/*  781 */       this.updatecount += i;
/*  782 */       paramADSABRSTATUS.addDebug(" end to execute updateCGOKTOPUB SQL, the update count is : " + i);
/*      */     }
/*  784 */     catch (SQLException sQLException) {
/*  785 */       this.errcount++;
/*  786 */       paramADSABRSTATUS.addOutput("Error: SQL updating . Activity C|" + paramString1 + "|" + paramInt + "|" + paramString2 + "|" + paramString3 + "  Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  790 */         this.m_conODS.commit();
/*  791 */         if (preparedStatement != null) {
/*  792 */           preparedStatement.close();
/*  793 */           preparedStatement = null;
/*      */         } 
/*  795 */       } catch (Exception exception) {
/*  796 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  799 */     return i;
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
/*  825 */     PreparedStatement preparedStatement = null;
/*  826 */     int i = 0;
/*      */     
/*  828 */     String str1 = "INSERT  INTO " + m_strODSSchema + m_tablename + " (Activity, Updated, TimeOfChange, BRANDCD_FC, SystemEntityType, SystemEntityId, SystemOS, GroupEntityType, GroupEntityId, OKTOPUB, OSEntityType, OSEntityId, OS, OptionEntityType, OptionEntityId, CompatibilityPublishingFlag, RelationshipType, PublishFrom, PublishTo)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/*      */ 
/*      */     
/*  832 */     String str2 = "UPDATE " + m_strODSSchema + m_tablename + " SET Activity =  ?, Updated = ?, TimeOfChange = ?, BRANDCD_FC = ?, OKTOPUB = ?, CompatibilityPublishingFlag = ?, RelationshipType = ?, PublishFrom = ?, PublishTo = ?  WHERE  SystemEntityType = ?  AND SystemEntityId = ?  AND SystemOS = ?  AND GroupEntityType = ?  AND GroupEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND OS = ?  AND OptionEntityType = ?  AND OptionEntityId = ? ";
/*      */ 
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
/*  844 */       String[] arrayOfString = entityExistsInCOMPAT(paramADSABRSTATUS, paramWWCOMPAT, paramString2);
/*      */       
/*  846 */       if (arrayOfString == null) {
/*      */         
/*  848 */         paramADSABRSTATUS.addDebug(" Beging to execute strInsertWWTECHCOMPATSQL : " + paramWWCOMPAT.getKey());
/*  849 */         preparedStatement = this.m_conODS.prepareStatement(str1);
/*      */         
/*  851 */         preparedStatement.clearParameters();
/*  852 */         preparedStatement.setString(1, "A");
/*  853 */         preparedStatement.setString(2, paramString1);
/*  854 */         preparedStatement.setString(3, paramString2);
/*  855 */         preparedStatement.setString(4, paramWWCOMPAT.getBRANDCD_FC());
/*  856 */         preparedStatement.setString(5, paramWWCOMPAT.getSystemEntityType());
/*  857 */         preparedStatement.setInt(6, paramWWCOMPAT.getSystemEntityId());
/*  858 */         preparedStatement.setString(7, paramWWCOMPAT.getSystemOS());
/*  859 */         preparedStatement.setString(8, paramWWCOMPAT.getGroupEntityType());
/*  860 */         preparedStatement.setInt(9, paramWWCOMPAT.getGroupEntityId());
/*  861 */         preparedStatement.setString(10, paramWWCOMPAT.getOKTOPUB());
/*  862 */         preparedStatement.setString(11, paramWWCOMPAT.getOSEntityType());
/*  863 */         preparedStatement.setInt(12, paramWWCOMPAT.getOSEntityId());
/*  864 */         preparedStatement.setString(13, paramWWCOMPAT.getOS());
/*  865 */         preparedStatement.setString(14, paramWWCOMPAT.getOptionEntityType());
/*  866 */         preparedStatement.setInt(15, paramWWCOMPAT.getOptionEntityId());
/*  867 */         preparedStatement.setString(16, paramWWCOMPAT.getCompatibilityPublishingFlag());
/*  868 */         preparedStatement.setString(17, paramWWCOMPAT.getRelationshipType());
/*  869 */         preparedStatement.setString(18, paramWWCOMPAT.getPublishFrom());
/*  870 */         preparedStatement.setString(19, paramWWCOMPAT.getPublishTo());
/*  871 */         i = preparedStatement.executeUpdate();
/*  872 */         this.count++;
/*  873 */         paramADSABRSTATUS.addDebug("execute INSERT into table WWTECHCOMPAT :COUNT: " + this.count + "\n" + paramWWCOMPAT.getKey());
/*      */       } else {
/*      */         
/*  876 */         String str3 = arrayOfString[0];
/*  877 */         String str4 = arrayOfString[1];
/*  878 */         if (paramString2.compareTo(str4) >= 0) {
/*      */           
/*  880 */           preparedStatement = this.m_conODS.prepareStatement(str2);
/*      */           
/*  882 */           preparedStatement.clearParameters();
/*  883 */           preparedStatement.setString(1, "A");
/*  884 */           preparedStatement.setString(2, paramString1);
/*  885 */           preparedStatement.setString(3, paramString2);
/*  886 */           preparedStatement.setString(4, paramWWCOMPAT.getBRANDCD_FC());
/*  887 */           preparedStatement.setString(5, paramWWCOMPAT.getOKTOPUB());
/*  888 */           preparedStatement.setString(6, paramWWCOMPAT.getCompatibilityPublishingFlag());
/*  889 */           preparedStatement.setString(7, paramWWCOMPAT.getRelationshipType());
/*  890 */           preparedStatement.setString(8, paramWWCOMPAT.getPublishFrom());
/*  891 */           preparedStatement.setString(9, paramWWCOMPAT.getPublishTo());
/*  892 */           preparedStatement.setString(10, paramWWCOMPAT.getSystemEntityType());
/*  893 */           preparedStatement.setInt(11, paramWWCOMPAT.getSystemEntityId());
/*  894 */           preparedStatement.setString(12, paramWWCOMPAT.getSystemOS());
/*  895 */           preparedStatement.setString(13, paramWWCOMPAT.getGroupEntityType());
/*  896 */           preparedStatement.setInt(14, paramWWCOMPAT.getGroupEntityId());
/*  897 */           preparedStatement.setString(15, paramWWCOMPAT.getOSEntityType());
/*  898 */           preparedStatement.setInt(16, paramWWCOMPAT.getOSEntityId());
/*  899 */           preparedStatement.setString(17, paramWWCOMPAT.getOS());
/*  900 */           preparedStatement.setString(18, paramWWCOMPAT.getOptionEntityType());
/*  901 */           preparedStatement.setInt(19, paramWWCOMPAT.getOptionEntityId());
/*  902 */           i = preparedStatement.executeUpdate();
/*  903 */           this.updatecount++;
/*  904 */           paramADSABRSTATUS.addDebug("execute UPDATE into talble WWTECHCOMPAT :UPDATE COUNT: " + this.updatecount + "\n" + paramWWCOMPAT.getKey());
/*      */         } else {
/*      */           
/*  907 */           paramADSABRSTATUS.addDebug("This record has beed update at the same time of " + str4 + "activity" + str3 + "\n" + paramWWCOMPAT.getKey());
/*      */         }
/*      */       
/*      */       } 
/*  911 */     } catch (SQLException sQLException) {
/*  912 */       this.errcount++;
/*  913 */       paramADSABRSTATUS.addOutput("Error: SQL Skipping insert. Activity " + paramWWCOMPAT.getKey() + "\n Error MESS:" + sQLException.getMessage());
/*      */     } finally {
/*      */       
/*      */       try {
/*  917 */         this.m_conODS.commit();
/*  918 */         if (preparedStatement != null) {
/*  919 */           preparedStatement.close();
/*  920 */           preparedStatement = null;
/*      */         } 
/*  922 */       } catch (Exception exception) {
/*  923 */         paramADSABRSTATUS.addDebug("UpdateWWTECHCOMPAT(), unable to close statement. " + exception);
/*      */       } 
/*      */     } 
/*  926 */     return i;
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
/*  949 */     String str = "SELECT Activity, TimeOfChange from " + m_strODSSchema + m_tablename + " WHERE  SystemEntityType = ?  AND SystemEntityId = ?  AND SystemOS = ?  AND GroupEntityType = ?  AND GroupEntityId = ?  AND OSEntityType = ?  AND OSEntityId = ?  AND OS = ?  AND OptionEntityType = ?  AND OptionEntityId = ? with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  959 */     String[] arrayOfString = null;
/*      */     
/*  961 */     PreparedStatement preparedStatement = null;
/*  962 */     ResultSet resultSet = null;
/*      */     
/*      */     try {
/*  965 */       preparedStatement = this.m_conODS.prepareStatement(str);
/*  966 */       preparedStatement.setString(1, paramWWCOMPAT.getSystemEntityType());
/*  967 */       preparedStatement.setInt(2, paramWWCOMPAT.getSystemEntityId());
/*  968 */       preparedStatement.setString(3, paramWWCOMPAT.getSystemOS());
/*  969 */       preparedStatement.setString(4, paramWWCOMPAT.getGroupEntityType());
/*  970 */       preparedStatement.setInt(5, paramWWCOMPAT.getGroupEntityId());
/*  971 */       preparedStatement.setString(6, paramWWCOMPAT.getOSEntityType());
/*  972 */       preparedStatement.setInt(7, paramWWCOMPAT.getOSEntityId());
/*  973 */       preparedStatement.setString(8, paramWWCOMPAT.getOS());
/*  974 */       preparedStatement.setString(9, paramWWCOMPAT.getOptionEntityType());
/*  975 */       preparedStatement.setInt(10, paramWWCOMPAT.getOptionEntityId());
/*  976 */       resultSet = preparedStatement.executeQuery();
/*  977 */       if (resultSet.next()) {
/*  978 */         String str1 = resultSet.getString("Activity");
/*  979 */         String str2 = resultSet.getString("TimeOfChange");
/*  980 */         arrayOfString = new String[] { str1, str2 };
/*  981 */         paramADSABRSTATUS.addDebug(" reslut Activity : " + str1 + "  TimeOfChange: " + str2);
/*      */       } else {
/*      */         
/*  984 */         paramADSABRSTATUS.addDebug(" could not find reslut Activity");
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  989 */       if (resultSet != null) {
/*  990 */         resultSet.close();
/*  991 */         resultSet = null;
/*      */       } 
/*  993 */       if (preparedStatement != null) {
/*  994 */         preparedStatement.close();
/*  995 */         preparedStatement = null;
/*      */       } 
/*      */     } 
/*  998 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void setSystemSoLvVct(Vector<String> paramVector, EntityItem paramEntityItem) {
/* 1005 */     if (paramEntityItem != null) {
/* 1006 */       String str = paramEntityItem.getEntityType();
/* 1007 */       EANFlagAttribute eANFlagAttribute = null;
/* 1008 */       if ("MODEL".equals(str) || "LSEOBUNDLE".equals(str)) {
/* 1009 */         eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("OSLEVEL");
/*      */       } else {
/* 1011 */         eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("OS");
/*      */       } 
/* 1013 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/* 1015 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1016 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/* 1018 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 1019 */             paramVector.add(arrayOfMetaFlag[b].getFlagCode());
/*      */           }
/*      */         } 
/*      */       } else {
/* 1023 */         paramVector.add("");
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
/* 1036 */     String str = null;
/* 1037 */     if (!"".equals(paramString1)) {
/* 1038 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 1039 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString2);
/* 1040 */       if (eANFlagAttribute == null || !eANFlagAttribute.isSelected(paramString1)) {
/* 1041 */         str = "Delete";
/*      */       } else {
/* 1043 */         entityItem = paramDiffEntity.getPriorEntityItem();
/* 1044 */         eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString2);
/* 1045 */         if (eANFlagAttribute == null || !eANFlagAttribute.isSelected(paramString1)) {
/* 1046 */           str = "Update";
/*      */         }
/*      */       } 
/*      */     } else {
/* 1050 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 1051 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString2);
/* 1052 */       if (eANFlagAttribute != null) {
/* 1053 */         str = "Delete";
/*      */       } else {
/* 1055 */         entityItem = paramDiffEntity.getPriorEntityItem();
/* 1056 */         eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString2);
/* 1057 */         if (eANFlagAttribute != null) {
/* 1058 */           str = "Update";
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1063 */     return str;
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
/* 1081 */     String[] arrayOfString = null;
/*      */     
/* 1083 */     if (paramString != null && paramVector.contains("10589")) {
/* 1084 */       arrayOfString = new String[] { "10589" };
/* 1085 */     } else if ("10589".equals(paramString)) {
/* 1086 */       arrayOfString = new String[paramVector.size()];
/* 1087 */       paramVector.copyInto((Object[])arrayOfString);
/* 1088 */     } else if (paramString != null && paramVector.contains(paramString)) {
/* 1089 */       arrayOfString = new String[] { paramString };
/* 1090 */     } else if (paramString != null && paramVector.contains("")) {
/* 1091 */       arrayOfString = new String[] { "" };
/*      */     } 
/*      */     
/* 1094 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String getValue(DiffEntity paramDiffEntity, String paramString) {
/* 1103 */     String str1 = null;
/* 1104 */     String str2 = "";
/* 1105 */     String str3 = "";
/* 1106 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 1107 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 1108 */     if (paramDiffEntity.isDeleted()) {
/* 1109 */       str3 = PokUtils.getAttributeValue(entityItem2, paramString, ", ", "", false);
/* 1110 */     } else if (paramDiffEntity.isNew()) {
/* 1111 */       str2 = PokUtils.getAttributeValue(entityItem1, paramString, ", ", "", false);
/*      */     } else {
/* 1113 */       str3 = PokUtils.getAttributeValue(entityItem2, paramString, ", ", "", false);
/* 1114 */       str2 = PokUtils.getAttributeValue(entityItem1, paramString, ", ", "", false);
/*      */     } 
/*      */     
/* 1117 */     if (!str3.equals(str2)) {
/* 1118 */       str1 = str2;
/*      */     }
/* 1120 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String getFlagValue(DiffEntity paramDiffEntity, String paramString) {
/* 1130 */     String str1 = null;
/* 1131 */     String str2 = "";
/* 1132 */     String str3 = "";
/* 1133 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 1134 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 1135 */     if (paramDiffEntity.isDeleted()) {
/* 1136 */       str3 = PokUtils.getAttributeFlagValue(entityItem2, paramString);
/* 1137 */       if (str3 == null) {
/* 1138 */         str3 = "";
/*      */       }
/* 1140 */     } else if (paramDiffEntity.isNew()) {
/* 1141 */       str2 = PokUtils.getAttributeFlagValue(entityItem1, paramString);
/* 1142 */       if (str2 == null) {
/* 1143 */         str2 = "";
/*      */       }
/*      */     } else {
/* 1146 */       str3 = PokUtils.getAttributeFlagValue(entityItem2, paramString);
/* 1147 */       str2 = PokUtils.getAttributeFlagValue(entityItem1, paramString);
/* 1148 */       if (str2 == null) {
/* 1149 */         str2 = "";
/*      */       }
/* 1151 */       if (str3 == null) {
/* 1152 */         str3 = "";
/*      */       }
/*      */     } 
/*      */     
/* 1156 */     if (!str3.equals(str2)) {
/* 1157 */       str1 = str2;
/*      */     }
/*      */     
/* 1160 */     return str1;
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
/* 1192 */     setConnection();
/*      */     
/* 1194 */     String str1 = "";
/*      */     
/* 1196 */     str1 = getEntityStatus(paramString1, paramInt1, paramString6);
/* 1197 */     if (!str1.equals("0020") && !str1.equals("0040") && !str1.equals("")) {
/* 1198 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :SystemEntity " + paramString1 + paramInt1 + " Status : " + str1);
/* 1199 */       return false;
/*      */     } 
/*      */     
/* 1202 */     String str2 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1235 */     boolean bool1 = true;
/* 1236 */     String str3 = "";
/* 1237 */     String str4 = "";
/*      */     
/* 1239 */     str1 = getEntityStatus(paramString2, paramInt2, paramString6);
/* 1240 */     if (!str1.equals("0020") && !str1.equals("0040")) {
/* 1241 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :CG " + paramString2 + paramInt2 + " Status : " + str1);
/* 1242 */       return false;
/*      */     } 
/*      */     
/* 1245 */     str3 = getEntityFlagUnique(paramString2, paramInt2, "OKTOPUB", paramString6);
/* 1246 */     bool1 = str3.equalsIgnoreCase("DEL") ? false : true;
/* 1247 */     if (!bool1) {
/* 1248 */       paramADSABRSTATUS.addDebug("isOKTOPUB is delete :CG " + paramString2 + paramInt2 + " Status : " + str1);
/* 1249 */       return false;
/*      */     } 
/*      */     
/* 1252 */     str4 = getEntityFlagUnique(paramString2, paramInt2, "BRANDCD", paramString6);
/*      */ 
/*      */ 
/*      */     
/* 1256 */     boolean bool2 = false;
/* 1257 */     String str5 = "";
/*      */     
/* 1259 */     str1 = getEntityStatus(paramString3, paramInt3, paramString6);
/* 1260 */     if (!str1.equals("0020") && !str1.equals("0040")) {
/* 1261 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :CGOS " + paramString3 + paramInt3 + " Status : " + str1);
/* 1262 */       return false;
/*      */     } 
/*      */     
/* 1265 */     str5 = getEntityFlagUnique(paramString3, paramInt3, "OS", paramString6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1271 */     if (str5.equals(OSIndependent) || str5.equals("")) {
/* 1272 */       bool2 = true;
/*      */     }
/* 1274 */     if (!bool2) {
/* 1275 */       paramADSABRSTATUS.addDebug(paramString3 + paramInt3 + " does not have OS attr equal to OS Independent 10589.");
/* 1276 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1282 */     boolean bool3 = true;
/* 1283 */     String str6 = "";
/* 1284 */     String str7 = "";
/* 1285 */     String str8 = "";
/* 1286 */     String str9 = "";
/*      */     
/* 1288 */     str1 = getEntityStatus(paramString4, paramInt4, paramString6);
/* 1289 */     if (!str1.equals("0020") && !str1.equals("0040")) {
/* 1290 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :" + paramString4 + paramInt4 + " Status : " + str1);
/* 1291 */       return false;
/*      */     } 
/*      */     
/* 1294 */     str6 = getEntityFlagUnique(paramString4, paramInt4, "COMPATPUBFLG", paramString6);
/* 1295 */     bool3 = str6.equalsIgnoreCase("DEL") ? false : true;
/* 1296 */     if (!bool3) {
/* 1297 */       paramADSABRSTATUS.addDebug("COMPATPUBFLG is delete :" + paramString4 + paramInt4 + " Status : " + str1);
/* 1298 */       return false;
/*      */     } 
/* 1300 */     str7 = getEntityTextValue(paramString4, paramInt4, "PUBFROM", paramString6);
/* 1301 */     str8 = getEntityTextValue(paramString4, paramInt4, "PUBTO", paramString6);
/* 1302 */     str9 = getEntityFlagUnique(paramString4, paramInt4, "RELTYPE", paramString6);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1307 */     str1 = getEntityStatus(paramString5, paramInt5, paramString6);
/* 1308 */     if (!str1.equals("0020") && !str1.equals("0040") && !str1.equals("")) {
/* 1309 */       paramADSABRSTATUS.addDebug("Status is not RFR or Final :" + paramString5 + paramInt5 + " Status : " + str1);
/* 1310 */       return false;
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
/* 1355 */     if ("WWSEO".equals(paramString1) || "LSEOBUNDLE".equals(paramString1) || "MODEL".equals(paramString1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1361 */       setWWCOMPAT(str4, paramString1, paramInt1, str2, paramString2, paramInt2, str3, paramString3, paramInt3, str5, paramString5, paramInt5, str6, str9, str7, str8, paramVector1);
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
/* 1378 */       paramADSABRSTATUS.addDebug("Not support for this release :" + paramString1 + paramInt1);
/* 1379 */       return false;
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
/* 1431 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void putWWCOMPATVector(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector1, EntityItem paramEntityItem, ResultSet paramResultSet, Vector paramVector2, Vector paramVector3, Vector paramVector4, String paramString1, String paramString2) throws SQLException {
/* 1439 */     String str1 = "";
/*      */     
/* 1441 */     String str2 = "";
/*      */     
/* 1443 */     String str3 = "";
/*      */     
/* 1445 */     String str4 = "";
/*      */     
/* 1447 */     String str5 = "";
/*      */     
/* 1449 */     byte b = 1;
/* 1450 */     long l = System.currentTimeMillis();
/* 1451 */     boolean bool1 = (paramVector2.size() == 0) ? true : false;
/* 1452 */     boolean bool2 = (paramVector2.size() == 0) ? true : false;
/* 1453 */     if (paramResultSet != null) {
/* 1454 */       while (paramResultSet.next()) {
/* 1455 */         str1 = paramResultSet.getString("SystemEntityType");
/* 1456 */         int i = paramResultSet.getInt("SystemEntityId");
/* 1457 */         str2 = paramResultSet.getString("GroupEntityType");
/* 1458 */         int j = paramResultSet.getInt("GroupEntityId");
/* 1459 */         str3 = paramResultSet.getString("OSEntityType");
/* 1460 */         int k = paramResultSet.getInt("OSEntityId");
/* 1461 */         str4 = paramResultSet.getString("OSOPTIONType");
/* 1462 */         int m = paramResultSet.getInt("OSOPTIONId");
/* 1463 */         str5 = paramResultSet.getString("OptionEntityType");
/* 1464 */         int n = paramResultSet.getInt("OptionEntityID");
/* 1465 */         paramVector4.clear();
/* 1466 */         if (bool1) {
/* 1467 */           paramVector2.clear();
/*      */         }
/* 1469 */         if (bool2) {
/* 1470 */           paramVector3.clear();
/*      */         }
/* 1472 */         putvalidWWCOMPAT(paramADSABRSTATUS, paramEntityItem, paramVector4, paramVector2, paramVector3, str1, i, str2, j, str3, k, str4, m, str5, n, paramString2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1483 */         paramVector1.addAll(paramVector4);
/*      */         
/* 1485 */         if (paramVector1.size() >= WWCOMPAT_ROW_LIMIT) {
/* 1486 */           paramADSABRSTATUS.addDebug("Chunking size is " + WWCOMPAT_ROW_LIMIT + ". Start to run chunking " + b++ + " times.");
/* 1487 */           updateCompat(paramADSABRSTATUS, paramVector1, paramString1, paramString2);
/*      */           
/* 1489 */           paramVector1.clear();
/*      */         } 
/*      */       } 
/*      */       
/* 1493 */       if (paramVector1.size() > 0) {
/* 1494 */         updateCompat(paramADSABRSTATUS, paramVector1, paramString1, paramString2);
/*      */       }
/* 1496 */       paramADSABRSTATUS.addDebug("Time to getMatchingDateIds all WWCOMPATVector size:" + ((b - 1) * WWCOMPAT_ROW_LIMIT + paramVector1.size()) + "|" + paramEntityItem.getKey() + ": " + 
/* 1497 */           Stopwatch.format(System.currentTimeMillis() - l));
/*      */       
/* 1499 */       paramVector1.clear();
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
/* 1516 */     return getEntityFlagUnique(paramString1, paramInt, "STATUS", paramString2);
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
/* 1527 */     String str1 = "";
/* 1528 */     PreparedStatement preparedStatement = null;
/* 1529 */     ResultSet resultSet = null;
/* 1530 */     String str2 = " select attributevalue from opicm.flag where entitytype=? and entityid=? and attributecode=?  and effto > ? and valto > ?  and enterprise= 'SG' with ur";
/*      */ 
/*      */     
/* 1533 */     preparedStatement = this.m_conODS.prepareStatement(str2);
/* 1534 */     preparedStatement.setString(1, paramString1);
/* 1535 */     preparedStatement.setInt(2, paramInt);
/* 1536 */     preparedStatement.setString(3, paramString2);
/* 1537 */     preparedStatement.setString(4, paramString3);
/* 1538 */     preparedStatement.setString(5, paramString3);
/* 1539 */     resultSet = preparedStatement.executeQuery();
/* 1540 */     if (resultSet.next()) {
/* 1541 */       str1 = resultSet.getString("attributevalue");
/*      */     }
/* 1543 */     resultSet.close();
/* 1544 */     preparedStatement.close();
/* 1545 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getEntityTextValue(String paramString1, int paramInt, String paramString2, String paramString3) throws SQLException {
/* 1555 */     String str1 = "";
/* 1556 */     PreparedStatement preparedStatement = null;
/* 1557 */     ResultSet resultSet = null;
/* 1558 */     String str2 = " select attributevalue from opicm.text where entitytype=? and entityid=? and attributecode=?  and effto > ? and valto > ?  and enterprise= 'SG' with ur";
/*      */ 
/*      */     
/* 1561 */     preparedStatement = this.m_conODS.prepareStatement(str2);
/* 1562 */     preparedStatement.setString(1, paramString1);
/* 1563 */     preparedStatement.setInt(2, paramInt);
/* 1564 */     preparedStatement.setString(3, paramString2);
/* 1565 */     preparedStatement.setString(4, paramString3);
/* 1566 */     preparedStatement.setString(5, paramString3);
/* 1567 */     resultSet = preparedStatement.executeQuery();
/* 1568 */     if (resultSet.next()) {
/* 1569 */       str1 = resultSet.getString("attributevalue");
/*      */     }
/* 1571 */     resultSet.close();
/* 1572 */     preparedStatement.close();
/* 1573 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Vector getEntityFlagMulti(String paramString1, int paramInt, String paramString2, String paramString3) throws SQLException {
/* 1583 */     Vector<String> vector = new Vector();
/* 1584 */     PreparedStatement preparedStatement = null;
/* 1585 */     ResultSet resultSet = null;
/* 1586 */     String str = " select attributevalue from opicm.flag where entitytype=? and entityid=? and attributecode=?  and effto > ? and valto > ? and enterprise= 'SG' with ur";
/*      */ 
/*      */     
/* 1589 */     preparedStatement = this.m_conODS.prepareStatement(str);
/*      */ 
/*      */     
/* 1592 */     preparedStatement.setString(1, paramString1);
/* 1593 */     preparedStatement.setInt(2, paramInt);
/* 1594 */     preparedStatement.setString(3, paramString2);
/* 1595 */     preparedStatement.setString(4, paramString3);
/* 1596 */     preparedStatement.setString(5, paramString3);
/* 1597 */     resultSet = preparedStatement.executeQuery();
/* 1598 */     while (resultSet.next()) {
/* 1599 */       vector.add(resultSet.getString("attributevalue"));
/*      */     }
/* 1601 */     resultSet.close();
/* 1602 */     preparedStatement.close();
/* 1603 */     return vector;
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
/* 1626 */     WWCOMPAT wWCOMPAT = new WWCOMPAT();
/* 1627 */     wWCOMPAT.setBRANDCD_FC(paramString1);
/* 1628 */     wWCOMPAT.setSystemEntityType(paramString2);
/* 1629 */     wWCOMPAT.setSystemEntityId(paramInt1);
/* 1630 */     wWCOMPAT.setSystemOS(paramString3);
/* 1631 */     wWCOMPAT.setGroupEntityType(paramString4);
/* 1632 */     wWCOMPAT.setGroupEntityId(paramInt2);
/* 1633 */     wWCOMPAT.setOKTOPUB(paramString5);
/* 1634 */     wWCOMPAT.setOSEntityType(paramString6);
/* 1635 */     wWCOMPAT.setOSEntityId(paramInt3);
/* 1636 */     wWCOMPAT.setOS(paramString7);
/* 1637 */     wWCOMPAT.setOptionEntityType(paramString8);
/* 1638 */     wWCOMPAT.setOptionEntityId(paramInt4);
/* 1639 */     wWCOMPAT.setCompatibilityPublishingFlag(paramString9);
/* 1640 */     wWCOMPAT.setRelationshipType(paramString10);
/* 1641 */     wWCOMPAT.setPublishFrom(paramString11);
/* 1642 */     wWCOMPAT.setPublishTo(paramString12);
/* 1643 */     paramVector.add(wWCOMPAT);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStatusAttr() {
/* 1649 */     return "STATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/* 1658 */     return "$Revision: 1.16 $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setConnection() throws SQLException {
/* 1667 */     if (this.m_conODS == null) {
/*      */       try {
/* 1669 */         this
/* 1670 */           .m_conODS = DriverManager.getConnection(
/* 1671 */             MiddlewareServerProperties.getPDHDatabaseURL(), 
/* 1672 */             MiddlewareServerProperties.getPDHDatabaseUser(), 
/* 1673 */             AES256Utils.decrypt(MiddlewareServerProperties.getPDHDatabasePassword()));
/* 1674 */       } catch (SQLException sQLException) {
/*      */         
/* 1676 */         throw sQLException;
/* 1677 */       } catch (Exception exception) {
/*      */         
/* 1679 */         exception.printStackTrace();
/*      */       } 
/* 1681 */       this.m_conODS.setAutoCommit(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Connection getConnection() throws SQLException {
/* 1691 */     if (this.m_conODS == null) {
/* 1692 */       setConnection();
/*      */     }
/* 1694 */     return this.m_conODS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void releaseConn() {
/* 1702 */     this.m_conODS = null;
/*      */   }
/*      */   public String getMQCID() {
/* 1705 */     return null;
/*      */   }
/*      */   public String getCommMODELCGSql(String paramString) {
/* 1708 */     StringBuffer stringBuffer = new StringBuffer(" select                     \r\n   MODEL.entitytype                 as SystemEntityType,                  \r\n   MODEL.entityid                   as SystemEntityId,                    \r\n   MODELCG.entitytype               as GroupEntityType,                   \r\n   MODELCG.entityid                 as GroupEntityId,                     \r\n   MODELCGOS.entitytype             as OSEntityType,                      \r\n   MODELCGOS.entityid               as OSEntityId,                        \r\n   MDLCGOSMDL.entityid              as OSOPTIONId,                        \r\n   MDLCGOSMDL.entitytype            as OSOPTIONType,                      \r\n   MODEL2.entitytype                as OptionEntityType,                  \r\n   MODEL2.entityid                  as OptionEntityID                     \r\n from opicm.flag MODELCG                                                  \r\n inner join opicm.relator           as MDLCGMDL on                        \r\n    MDLCGMDL.entity1type            = 'MODELCG' and                       \r\n    MDLCGMDL.entity2type            = 'MODEL'  and                        \r\n    MDLCGMDL.entity1id              = MODELCG.entityid and                \r\n    MDLCGMDL.valto                  > current timestamp and               \r\n    MDLCGMDL.effto                  > current timestamp and               \r\n    MDLCGMDL.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as MODEL on                           \r\n    MODEL.entitytype                = 'MODEL' and                         \r\n    MODEL.entityid                  = MDLCGMDL.entity2id and              \r\n    MODEL.valto                     > current timestamp and               \r\n    MODEL.effto                     > current timestamp and               \r\n    MODEL.attributecode             = 'STATUS' and                        \r\n    MODEL.attributevalue            in('0040','0020') and              \r\n    MODEL.ENTERPRISE='SG'                                                 \r\n inner join opicm.relator           as MDLCGMDLCGOS on                    \r\n    MDLCGMDLCGOS.entity1type        = 'MODELCG' and                       \r\n    MDLCGMDLCGOS.entity2type        = 'MODELCGOS' and                     \r\n    MDLCGMDLCGOS.entity1id          = MODELCG.entityid and                \r\n    MDLCGMDLCGOS.valto              > current timestamp and               \r\n    MDLCGMDLCGOS.effto              > current timestamp and               \r\n    MDLCGMDLCGOS.ENTERPRISE         = 'SG'                                \r\n inner join opicm.flag              as MODELCGOS on                       \r\n    MODELCGOS.entitytype            = 'MODELCGOS' and                     \r\n    MODELCGOS.entityid              = MDLCGMDLCGOS.entity2id and          \r\n    MODELCGOS.valto                 > current timestamp and               \r\n    MODELCGOS.effto                 > current timestamp and               \r\n    MODELCGOS.attributecode         = 'STATUS' and                        \r\n    MODELCGOS.attributevalue        in('0040','0020') and                 \r\n    MODELCGOS.ENTERPRISE            = 'SG'                                \r\n inner join opicm.relator            as MDLCGOSMDL on                     \r\n    MDLCGOSMDL.entity1type          = 'MODELCGOS' and                     \r\n    MDLCGOSMDL.entity2type          = 'MODEL' and                         \r\n    MDLCGOSMDL.entity1id            = MODELCGOS.entityid and              \r\n    MDLCGOSMDL.valto                > current timestamp and               \r\n    MDLCGOSMDL.effto                > current timestamp and               \r\n    MDLCGOSMDL.ENTERPRISE           = 'SG'                                \r\n inner join opicm.flag              as OSOPTION  on                       \r\n    OSOPTION.entitytype             = MDLCGOSMDL.entitytype and           \r\n    OSOPTION.entityid               = MDLCGOSMDL.entityid and             \r\n    OSOPTION.valto                  > current timestamp and               \r\n    OSOPTION.effto                  > current timestamp and               \r\n    OSOPTION.attributecode          = 'STATUS' and                        \r\n    OSOPTION.attributevalue         in('0040','0020') and                 \r\n    OSOPTION.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as MODEL2 on                          \r\n    MODEL2.entitytype               = 'MODEL' and                         \r\n    MODEL2.entityid                 = MDLCGOSMDL.entity2id and            \r\n    MODEL2.valto                    > current timestamp and               \r\n    MODEL2.effto                    > current timestamp and               \r\n    MODEL2.attributecode            = 'STATUS' and                        \r\n    MODEL2.attributevalue           in('0040','0020') and              \r\n    MODEL2.ENTERPRISE               = 'SG'                                \r\n where                                                                    \r\n    MODELCG.entitytype              = 'MODELCG' and                       \r\n    MODELCG.valto                   > current timestamp and               \r\n    MODELCG.effto                   > current timestamp and               \r\n    MODELCG.attributecode           = 'STATUS' and                        \r\n    MODELCG.attributevalue          in('0040','0020') and                 \r\n    MODELCG.ENTERPRISE              = 'SG' and                            \r\n");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1780 */     stringBuffer.append(paramString);
/* 1781 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommSEOCGSql(String paramString) {
/* 1790 */     StringBuffer stringBuffer = new StringBuffer(" select                                        \r\n    SystemGroup.SystemId          as SystemEntityId,                                           \r\n    SystemGroup.SystemType        as SystemEntityType,                                         \r\n    SystemGroup.GroupId           as GroupEntityId,                                            \r\n    SystemGroup.GroupType         as GroupEntityType,                                          \r\n    OSOption.OSId                 as OSEntityId,                                               \r\n    OSOption.OSType               as OSEntityType,                                             \r\n    OSOption.OSOPTIONId           as OSOPTIONId,                                               \r\n    OSOption.OSOPTIONType         as OSOPTIONType,                                             \r\n    OSOption.OptionId             as OptionEntityID,                                           \r\n    OSOption.OptionType           as OptionEntityType                                          \r\n from                                                                     \r\n (                                                                        \r\n select                                                                   \r\n   SEOCG.entityid                   as GroupId,                           \r\n   SEOCG.entityType                 as GroupType,                         \r\n   LSEOBUNDLE.entityid              as SystemId,                          \r\n   LSEOBUNDLE.entityType            as SystemType                         \r\n from opicm.flag                    as SEOCG                              \r\n inner join opicm.relator           as SEOCGBDL on                        \r\n    SEOCGBDL.entity1type            = 'SEOCG' and                         \r\n    SEOCGBDL.entity2type            = 'LSEOBUNDLE' and                    \r\n    SEOCGBDL.entity1id              = SEOCG.entityid and                  \r\n    SEOCGBDL.valto                  > current timestamp and               \r\n    SEOCGBDL.effto                  > current timestamp and               \r\n    SEOCGBDL.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as LSEOBUNDLE on                      \r\n    LSEOBUNDLE.entitytype           = 'LSEOBUNDLE' and                    \r\n    LSEOBUNDLE.entityid             = SEOCGBDL.entity2id and              \r\n    LSEOBUNDLE.valto                > current timestamp and               \r\n    LSEOBUNDLE.effto                > current timestamp and               \r\n    LSEOBUNDLE.attributecode        = 'STATUS' and                        \r\n    LSEOBUNDLE.attributevalue       in('0040','0020') and              \r\n    LSEOBUNDLE.ENTERPRISE           = 'SG'                                \r\n where                                                                    \r\n    SEOCG.entitytype                = 'SEOCG' and                         \r\n    SEOCG.valto                     > current timestamp and               \r\n    SEOCG.effto                     > current timestamp and               \r\n    SEOCG.attributecode             = 'STATUS' and                        \r\n    SEOCG.attributevalue            in('0040','0020') and                 \r\n    SEOCG.ENTERPRISE                = 'SG'                                \r\n union all                                                                \r\n select                                                                   \r\n   SEOCG.entityid                   as GroupId,                           \r\n   SEOCG.entityType                 as GroupType,                         \r\n   MODEL.entityid                   as SystemId,                          \r\n   MODEL.entityType                 as SystemType                         \r\n from opicm.flag                    as SEOCG                              \r\n inner join opicm.relator           as SEOCGMDL on                        \r\n    SEOCGMDL.entity1type            = 'SEOCG' and                         \r\n    SEOCGMDL.entity2type            = 'MODEL'  and                        \r\n    SEOCGMDL.entity1id              = SEOCG.entityid and                  \r\n    SEOCGMDL.valto                  > current timestamp and               \r\n    SEOCGMDL.effto                  > current timestamp and               \r\n    SEOCGMDL.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as MODEL on                           \r\n    MODEL.entitytype                = 'MODEL' and                         \r\n    MODEL.entityid                  = SEOCGMDL.entity2id and              \r\n    MODEL.valto                     > current timestamp and               \r\n    MODEL.effto                     > current timestamp and               \r\n    MODEL.attributecode             = 'STATUS' and                        \r\n    MODEL.attributevalue            in('0040','0020') and              \r\n    MODEL.ENTERPRISE                = 'SG'                                \r\n where                                                                    \r\n    SEOCG.entitytype                = 'SEOCG' and                         \r\n    SEOCG.valto                     > current timestamp and               \r\n    SEOCG.effto                     > current timestamp and               \r\n    SEOCG.attributecode             = 'STATUS' and                        \r\n    SEOCG.attributevalue            in('0040','0020') and                 \r\n    SEOCG.ENTERPRISE                = 'SG'                                \r\n union all                                                                \r\n select                                                                   \r\n   SEOCG.entityid                   as GroupId,                           \r\n   SEOCG.entityType                 as GroupType,                         \r\n   WWSEO.entityid                   as SystemId,                          \r\n   WWSEO.entityType                 as SystemType                         \r\n from opicm.flag                    as SEOCG                              \r\n inner join opicm.relator           as SEOCGSEO on                        \r\n    SEOCGSEO.entity1type            = 'SEOCG' and                         \r\n    SEOCGSEO.entity2type            = 'WWSEO'  and                        \r\n    SEOCGSEO.entity1id              = SEOCG.entityid and                  \r\n    SEOCGSEO.valto                  > current timestamp and               \r\n    SEOCGSEO.effto                  > current timestamp and               \r\n    SEOCGSEO.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as WWSEO on                           \r\n    WWSEO.entitytype                = 'WWSEO' and                         \r\n    WWSEO.entityid                  = SEOCGSEO.entity2id and              \r\n    WWSEO.valto                     > current timestamp and               \r\n    WWSEO.effto                     > current timestamp and               \r\n    WWSEO.attributecode             = 'STATUS' and                        \r\n    WWSEO.attributevalue            in('0040','0020') and              \r\n    WWSEO.ENTERPRISE                = 'SG'                                \r\n where                                                                    \r\n    SEOCG.entitytype                = 'SEOCG' and                         \r\n    SEOCG.valto                     > current timestamp and               \r\n    SEOCG.effto                     > current timestamp and               \r\n    SEOCG.attributecode             = 'STATUS' and                        \r\n    SEOCG.attributevalue            in('0040','0020') and                 \r\n    SEOCG.ENTERPRISE                = 'SG'                                \r\n )                                                                        \r\n as SystemGroup                                                           \r\n inner join opicm.relator           as SEOCGSEOCGOS on                    \r\n    SEOCGSEOCGOS.entity1type        = 'SEOCG' and                         \r\n    SEOCGSEOCGOS.entity2type        = 'SEOCGOS'  and                      \r\n    SEOCGSEOCGOS.entity1id          = SystemGroup.GroupId and             \r\n    SEOCGSEOCGOS.valto              > current timestamp and               \r\n    SEOCGSEOCGOS.effto              > current timestamp and               \r\n    SEOCGSEOCGOS.ENTERPRISE         = 'SG'                                \r\n inner join                                                               \r\n (                                                                        \r\n select                                                                   \r\n   SEOCGOS.entityid                 as OSId,                              \r\n   SEOCGOS.entitytype               as OSType,                            \r\n   SEOCGOSBDL.entityid              as OSOPTIONId,                        \r\n   SEOCGOSBDL.entitytype            as OSOPTIONType,                      \r\n   LSEOBUNDLE.entityid              as OptionId,                          \r\n   LSEOBUNDLE.entityType            as OptionType                         \r\n from opicm.flag                    as SEOCGOS                            \r\n inner join opicm.relator           as SEOCGOSBDL on                      \r\n    SEOCGOSBDL.entity1type          = 'SEOCGOS' and                       \r\n    SEOCGOSBDL.entity2type          = 'LSEOBUNDLE'  and                   \r\n    SEOCGOSBDL.entity1id            = SEOCGOS.entityid and                \r\n    SEOCGOSBDL.valto                > current timestamp and               \r\n    SEOCGOSBDL.effto                > current timestamp and               \r\n    SEOCGOSBDL.ENTERPRISE           = 'SG'                                \r\n inner join opicm.flag              as OSOPTION  on                       \r\n    OSOPTION.entitytype             = SEOCGOSBDL.entitytype and           \r\n    OSOPTION.entityid               = SEOCGOSBDL.entityid and             \r\n    OSOPTION.valto                  > current timestamp and               \r\n    OSOPTION.effto                  > current timestamp and               \r\n    OSOPTION.attributecode          = 'STATUS' and                        \r\n    OSOPTION.attributevalue         in('0040','0020') and                 \r\n    OSOPTION.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as LSEOBUNDLE on                      \r\n    LSEOBUNDLE.entitytype           = 'LSEOBUNDLE' and                    \r\n    LSEOBUNDLE.entityid             = SEOCGOSBDL.entity2id and            \r\n    LSEOBUNDLE.valto                > current timestamp and               \r\n    LSEOBUNDLE.effto                > current timestamp and               \r\n    LSEOBUNDLE.attributecode        = 'STATUS' and                        \r\n    LSEOBUNDLE.attributevalue       in('0040','0020') and              \r\n    LSEOBUNDLE.ENTERPRISE           = 'SG'                                \r\n where                                                                    \r\n    SEOCGOS.entitytype              = 'SEOCGOS' and                       \r\n    SEOCGOS.valto                   > current timestamp and               \r\n    SEOCGOS.effto                   > current timestamp and               \r\n    SEOCGOS.attributecode           = 'STATUS' and                        \r\n    SEOCGOS.attributevalue          in('0040','0020') and                 \r\n    SEOCGOS.ENTERPRISE              = 'SG'                                \r\n union all                                                                \r\n select                                                                   \r\n     SEOCGOS.entityid               as OSId,                              \r\n     SEOCGOS.entitytype             as OSType,                            \r\n     SEOCGOSSVCSEO.entityid         as OSOPTIONId,                        \r\n     SEOCGOSSVCSEO.entitytype       as OSOPTIONType,                      \r\n     SVCSEO.entityid                as OptionId,                          \r\n     SVCSEO.entityType              as OptionType                         \r\n from opicm.flag                    as SEOCGOS                            \r\n inner join opicm.relator           as SEOCGOSSVCSEO on                   \r\n    SEOCGOSSVCSEO.entity1type       = 'SEOCGOS' and                       \r\n    SEOCGOSSVCSEO.entity2type       = 'SVCSEO'  and                       \r\n    SEOCGOSSVCSEO.entity1id         = SEOCGOS.entityid and                \r\n    SEOCGOSSVCSEO.valto             > current timestamp and               \r\n    SEOCGOSSVCSEO.effto             > current timestamp and               \r\n    SEOCGOSSVCSEO.ENTERPRISE        = 'SG'                                \r\n  inner join opicm.flag             as OSOPTION  on                       \r\n    OSOPTION.entitytype             = SEOCGOSSVCSEO.entitytype and        \r\n    OSOPTION.entityid               = SEOCGOSSVCSEO.entityid and          \r\n    OSOPTION.valto                  > current timestamp and               \r\n    OSOPTION.effto                  > current timestamp and               \r\n    OSOPTION.attributecode          = 'STATUS' and                        \r\n    OSOPTION.attributevalue         in('0040','0020') and                 \r\n    OSOPTION.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as SVCSEO on                          \r\n    SVCSEO.entitytype               = 'SVCSEO' and                        \r\n    SVCSEO.entityid                 = SEOCGOSSVCSEO.entity2id and         \r\n    SVCSEO.valto                    > current timestamp and               \r\n    SVCSEO.effto                    > current timestamp and               \r\n    SVCSEO.attributecode            = 'STATUS' and                        \r\n    SVCSEO.attributevalue           in('0040','0020') and              \r\n    SVCSEO.ENTERPRISE               = 'SG'                                \r\n where                                                                    \r\n    SEOCGOS.entitytype              = 'SEOCGOS' and                       \r\n    SEOCGOS.valto                   > current timestamp and               \r\n    SEOCGOS.effto                   > current timestamp and               \r\n    SEOCGOS.attributecode           = 'STATUS' and                        \r\n    SEOCGOS.attributevalue          in('0040','0020') and                 \r\n    SEOCGOS.ENTERPRISE              = 'SG'                                \r\n union all                                                                \r\n select                                                                   \r\n     SEOCGOS.entityid               as OSId,                              \r\n     SEOCGOS.entitytype             as OSType,                            \r\n     SEOCGOSSEO.entityid            as OSOPTIONId,                        \r\n     SEOCGOSSEO.entitytype          as OSOPTIONType,                      \r\n     WWSEO.entityid                 as OptionId,                          \r\n     WWSEO.entityType               as OptionType                         \r\n from opicm.flag                    as SEOCGOS                            \r\n inner join opicm.relator           as SEOCGOSSEO on                      \r\n    SEOCGOSSEO.entity1type          = 'SEOCGOS' and                       \r\n    SEOCGOSSEO.entity2type          = 'WWSEO'  and                        \r\n    SEOCGOSSEO.entity1id            = SEOCGOS.entityid and                \r\n    SEOCGOSSEO.valto                > current timestamp and               \r\n    SEOCGOSSEO.effto                > current timestamp and               \r\n    SEOCGOSSEO.ENTERPRISE           = 'SG'                                \r\n inner join opicm.flag              as OSOPTION  on                       \r\n    OSOPTION.entitytype             = SEOCGOSSEO.entitytype and           \r\n    OSOPTION.entityid               = SEOCGOSSEO.entityid and             \r\n    OSOPTION.valto                  > current timestamp and               \r\n    OSOPTION.effto                  > current timestamp and               \r\n    OSOPTION.attributecode          = 'STATUS' and                        \r\n    OSOPTION.attributevalue         in('0040','0020') and                 \r\n    OSOPTION.ENTERPRISE             = 'SG'                                \r\n inner join opicm.flag              as WWSEO on                           \r\n    WWSEO.entitytype                = 'WWSEO' and                         \r\n    WWSEO.entityid                  = SEOCGOSSEO.entity2id and            \r\n    WWSEO.valto                     > current timestamp and               \r\n    WWSEO.effto                     > current timestamp and               \r\n    WWSEO.attributecode             = 'STATUS' and                        \r\n    WWSEO.attributevalue            in('0040','0020') and              \r\n    WWSEO.ENTERPRISE                = 'SG'                                \r\n where                                                                    \r\n    SEOCGOS.entitytype              = 'SEOCGOS' and                       \r\n    SEOCGOS.valto                   > current timestamp and               \r\n    SEOCGOS.effto                   > current timestamp and               \r\n    SEOCGOS.attributecode           = 'STATUS' and                        \r\n    SEOCGOS.attributevalue          in('0040','0020') and                 \r\n    SEOCGOS.ENTERPRISE              = 'SG'                                \r\n                                                                          \r\n ) as OSOption on                                                         \r\n    OSOption.OSType                 = 'SEOCGOS' and                       \r\n    OSOption.OSId                   = SEOCGSEOCGOS.entity2id              \r\n");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2020 */     stringBuffer.append(paramString);
/* 2021 */     return stringBuffer.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSCOMPATGEN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */