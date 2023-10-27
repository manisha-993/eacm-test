/*      */ import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ODSCreateEntities
/*      */ {
/*      */   private String strEnterprise;
/*      */   private String struidPDH;
/*      */   private String strpwdPDH;
/*      */   private String strNLSOds;
/*      */   private String strSchemaPDH;
/*      */   private String struidODS;
/*      */   private String strpwdODS;
/*      */   private String strSchemaODS;
/*      */   private String strEntityTableName;
/*      */   private String strurlPDH;
/*      */   private String strurlODS;
/*      */   private String strRunMode;
/*      */   private String strNoData;
/*      */   private String strInitializeMode;
/*      */   private Connection conPDH;
/*      */   private Connection conODS;
/*      */   static final String strODSEntityCreateVersion = "OPICM 1.0";
/*      */   private boolean blnLive = false;
/*  192 */   private SimpleDateFormat c_sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
/*      */ 
/*      */   
/*  195 */   private String strTimeStampNow = "";
/*  196 */   private String strTimeStampBeginOfDay = "";
/*  197 */   private String strTimeStampEndOfDay = "";
/*      */ 
/*      */   
/*  200 */   private String strTimeStampForever = "9999-12-31-00.00.00.000000";
/*  201 */   private String strTimeStampLastRun = "";
/*      */   
/*      */   static final int PDH_GET_META_ENTITIES_ATTRIBUTES = 0;
/*      */   
/*      */   static final int PDH_GET_ALL_ENTITY = 1;
/*      */   
/*      */   static final int PDH_GET_ALL_TEXT = 2;
/*      */   
/*      */   static final int PDH_GET_ALL_UFLAG = 3;
/*      */   static final int PDH_GET_ENTITY_SNAPSHOT = 4;
/*      */   static final int PDH_GET_COLUMN_SIZE = 5;
/*      */   static final int ODS_SET_TIMETABLE = 6;
/*      */   static final int ODS_CREATE_ENTITY_TABLE = 7;
/*      */   static final int ODS_INSERT_ENTITY_ROW = 8;
/*      */   static final int ODS_DROP_ENTITY_TABLE = 9;
/*      */   static final int ODS_CREATE_TIMETABLE = 10;
/*      */   static final int ODS_INSERT_TIMETABLE_ROW = 11;
/*      */   static final int ODS_DROP_TIMETABLE = 12;
/*      */   static final int ODS_CREATE_ENTITY_INDEX = 13;
/*      */   static final int ODS_DROP_DELETELOG = 14;
/*      */   static final int ODS_CREATE_DELETELOG = 15;
/*  222 */   private int ODS_COLUMN_MULTIPLIER = 3;
/*      */   
/*      */   static final int ODS_COLUMN_MAXWIDTH = 254;
/*      */   
/*      */   static final String ODS_TEXT_WIDTH = "32";
/*      */   
/*      */   static final String ODS_UFLAG_WIDTH = "32";
/*      */   
/*      */   static final String ODS_ID_WIDTH = "64";
/*      */   
/*      */   static final String ODS_CAT_WIDTH = "32";
/*      */   
/*      */   static final String ODS_ATT_WIDTH = "32";
/*      */   
/*      */   static final String ODS_ET_WIDTH = "32";
/*      */   
/*      */   static final String ODS_PRIMARY_COLUMNS = " ENTITYTYPE CHAR(32 ) NOT NULL,  ENTITYID INTEGER NOT NULL,  NLSID INTEGER NOT NULL,   Valfrom TIMESTAMP NOT NULL,  Valto TIMESTAMP NOT NULL";
/*      */   
/*  240 */   private String strCurrentEntity = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ODSCreateEntities(String paramString1, String paramString2) {
/*  257 */     readPropertyFile();
/*      */     
/*  259 */     this.ODS_COLUMN_MULTIPLIER = this.strNLSOds.equals("ALL") ? 3 : 1;
/*      */     
/*  261 */     this.strEntityTableName = paramString1;
/*  262 */     this.strNoData = paramString2;
/*      */ 
/*      */     
/*  265 */     printOK("Current Run Level is :" + getVersion());
/*      */     
/*  267 */     if (this.strRunMode.equals("LIVE")) {
/*  268 */       this.blnLive = true;
/*  269 */       printOK("We are Live... Updating ODS" + (this.strNoData.equals("NODATA") ? " Creating empty tables." : " "));
/*      */     } else {
/*  271 */       printOK("This is a Dry Run... Check the output log");
/*      */     } 
/*      */ 
/*      */     
/*  275 */     DateFormat dateFormat = DateFormat.getDateInstance();
/*  276 */     SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
/*  277 */     SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
/*  278 */     String str = simpleDateFormat2.format(new Date());
/*      */     
/*  280 */     dateFormat.setCalendar(Calendar.getInstance());
/*  281 */     simpleDateFormat1.setCalendar(Calendar.getInstance());
/*  282 */     simpleDateFormat2.setCalendar(Calendar.getInstance());
/*      */     
/*  284 */     this.strTimeStampNow = simpleDateFormat1.format(new Date());
/*  285 */     this.strTimeStampBeginOfDay = str + "-00.00.00.000000";
/*  286 */     this.strTimeStampEndOfDay = str + "-23.59.59.999999";
/*  287 */     this.strTimeStampLastRun = "";
/*      */ 
/*      */     
/*  290 */     printOK("Registering the Driver Manager");
/*      */ 
/*      */     
/*      */     try {
/*  294 */       Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
/*      */ 
/*      */ 
/*      */       
/*  298 */       this.conPDH = DriverManager.getConnection(this.strurlPDH, this.struidPDH, this.strpwdPDH);
/*  299 */       this.conODS = DriverManager.getConnection(this.strurlODS, this.struidODS, this.strpwdODS);
/*      */       
/*  301 */       this.conPDH.setTransactionIsolation(4);
/*  302 */       this.conPDH.setAutoCommit(true);
/*      */     }
/*  304 */     catch (Exception exception) {
/*      */       
/*  306 */       printErr("Driver Manager Error!:" + exception.getMessage());
/*  307 */       System.exit(1);
/*      */     } 
/*      */     
/*  310 */     if (this.strRunMode.equals("LIVE") && this.strEntityTableName
/*  311 */       .equals("ALL")) {
/*  312 */       initializeTimeTable();
/*  313 */       setTimestampInTimetable();
/*  314 */       initializeDeleteLog();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  320 */     buildODSTables(buildODSDefinition());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  325 */     printOK("Closing connections");
/*      */     try {
/*  327 */       this.conPDH.close();
/*  328 */       this.conODS.close();
/*  329 */     } catch (SQLException sQLException) {
/*  330 */       printErr("Error while closing connections....\n" + sQLException.getMessage());
/*      */     } 
/*  332 */     printOK("Congratulations!! ODS Update complete!");
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
/*      */   private Vector buildODSDefinition() {
/*  349 */     Vector<String> vector = null;
/*  350 */     vector = new Vector();
/*  351 */     boolean bool = false;
/*  352 */     String str = null;
/*      */     
/*  354 */     printOK("Starting Entity Vector Build.");
/*  355 */     if (!this.strEntityTableName.equals("ALL") && this.strInitializeMode.equals("CONTINUE")) {
/*  356 */       bool = true;
/*  357 */       printOK("buildODSDefinition: Bypassing entities before " + this.strEntityTableName);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  362 */       PreparedStatement preparedStatement = this.conPDH.prepareStatement(getQueryStatement(0));
/*  363 */       ResultSet resultSet = preparedStatement.executeQuery();
/*  364 */       if (resultSet.next()) {
/*  365 */         vector = new Vector();
/*      */         do {
/*  367 */           str = resultSet.getString(1).trim();
/*  368 */           if (str.equals(this.strEntityTableName)) {
/*  369 */             bool = false;
/*      */           }
/*      */           
/*  372 */           if (bool)
/*  373 */             continue;  vector.addElement(str);
/*  374 */           vector.addElement(resultSet.getString(2).trim());
/*  375 */           vector.addElement(resultSet.getString(3).trim());
/*  376 */           vector.addElement(resultSet.getString(4).trim());
/*  377 */           vector.addElement(resultSet.getString(5).trim());
/*  378 */           vector.addElement(resultSet.getString(6).trim());
/*      */         }
/*  380 */         while (resultSet.next());
/*      */       } 
/*      */ 
/*      */       
/*  384 */       resultSet.close();
/*  385 */       preparedStatement.close();
/*      */       
/*  387 */       printOK("Finished Entity Vector Build.");
/*  388 */     } catch (SQLException sQLException) {
/*  389 */       printErr("buildODSDefinition:" + sQLException.getMessage());
/*      */     }
/*  391 */     catch (Exception exception) {
/*  392 */       printErr("buildODSDefinition:" + exception.getMessage());
/*  393 */       System.exit(1);
/*      */     } 
/*      */ 
/*      */     
/*  397 */     return vector;
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
/*      */   private void buildODSTables(Vector paramVector) {
/*  415 */     String str1 = null;
/*  416 */     String str2 = null;
/*  417 */     String str3 = null;
/*  418 */     int i = 0;
/*  419 */     String str4 = null;
/*  420 */     String str5 = null;
/*  421 */     String str6 = null;
/*  422 */     String str7 = null;
/*  423 */     Object object = null;
/*      */     
/*  425 */     String str8 = "";
/*      */     
/*  427 */     String str9 = "";
/*      */ 
/*      */     
/*      */     try {
/*  431 */       Enumeration<String> enumeration = paramVector.elements();
/*      */       
/*  433 */       while (enumeration.hasMoreElements()) {
/*      */         
/*  435 */         str6 = enumeration.nextElement();
/*  436 */         if (str1 == null) {
/*  437 */           str1 = str6;
/*  438 */           str9 = null;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  443 */         if (!str6.equals(str1)) {
/*  444 */           createODSTable(str1, str8, str4, str5);
/*      */ 
/*      */ 
/*      */           
/*  448 */           str1 = str6;
/*  449 */           str8 = "";
/*  450 */           str9 = "";
/*      */         } 
/*      */ 
/*      */         
/*  454 */         str2 = enumeration.nextElement();
/*      */         
/*  456 */         if (str2.equals(str9)) {
/*  457 */           printErr("buildODSTables:ERROR!:DUPLICATE ATTRIBUTE DEFINED IN METAENTITY FOR ENTITY :" + str6 + " ATTRIBUTE " + str9 + "   Exiting OCE!");
/*      */           
/*  459 */           System.exit(1);
/*      */         } else {
/*  461 */           str9 = str2;
/*      */         } 
/*      */         
/*  464 */         str3 = enumeration.nextElement();
/*      */         
/*  466 */         if (str3.equals("0")) {
/*  467 */           printErr("buildODSTables:ERROR!:ATTRIBUTE LENGTH NOT DEFINED IN METAATTRIBUTE FOR ENTITY :" + str6 + "ATTRIBUTE:" + str2 + " :DEFAULTING TO 64 VARCHARS");
/*      */           
/*  469 */           str3 = "64";
/*      */         } else {
/*      */           
/*  472 */           i = Integer.valueOf(str3).intValue();
/*  473 */           if (i * this.ODS_COLUMN_MULTIPLIER > 254) {
/*  474 */             i = 254;
/*      */           } else {
/*  476 */             i *= this.ODS_COLUMN_MULTIPLIER;
/*      */           } 
/*      */           
/*  479 */           str3 = Integer.toString(i);
/*      */         } 
/*  481 */         str4 = enumeration.nextElement();
/*  482 */         str5 = enumeration.nextElement();
/*  483 */         str7 = enumeration.nextElement();
/*      */         
/*  485 */         str8 = str8 + "," + str2 + " VARCHAR(" + str3 + ")";
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  491 */       createODSTable(str1, str8, str4, str5);
/*      */ 
/*      */     
/*      */     }
/*  495 */     catch (Exception exception) {
/*  496 */       printErr("buildODSTables:ERROR:" + exception.getMessage());
/*  497 */       printErr(str2 + "|" + str3);
/*  498 */       exception.printStackTrace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createODSTable(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  527 */     String str = "";
/*      */ 
/*      */     
/*      */     try {
/*  531 */       printOK("createODSTable:START:TABLE:" + paramString1);
/*  532 */       String str1 = "PRIMARY KEY (ENTITYID, NLSID)";
/*      */       
/*  534 */       DatabaseMetaData databaseMetaData = this.conODS.getMetaData();
/*  535 */       Object object1 = null;
/*  536 */       Object object2 = null;
/*      */       
/*  538 */       String[] arrayOfString = { "TABLE" };
/*  539 */       boolean bool = false;
/*  540 */       ResultSet resultSet = databaseMetaData.getTables(null, this.strSchemaODS, paramString1, arrayOfString);
/*  541 */       if (resultSet.next()) {
/*  542 */         bool = true;
/*      */       }
/*  544 */       resultSet.close();
/*      */       
/*  546 */       String str2 = "DROP TABLE " + this.strSchemaODS + "." + paramString1;
/*      */       
/*  548 */       if (bool) {
/*      */         try {
/*  550 */           printOK("createTable:DROP:EXECUTE:" + paramString1.trim());
/*  551 */           if (this.blnLive) {
/*  552 */             Statement statement = this.conODS.createStatement();
/*  553 */             statement.executeUpdate(str2);
/*  554 */             statement.close();
/*      */           } 
/*  556 */         } catch (SQLException sQLException) {
/*  557 */           printErr("createODSTable:DROP:SQL:ERROR: " + sQLException.getMessage());
/*  558 */           printErr("createODSTable:DROPPING ********EXIT OCE*********");
/*  559 */           printOK("createODSTable:DROPPING ********EXIT OCE*********");
/*  560 */           System.exit(1);
/*      */         } 
/*      */       } else {
/*  563 */         printOK("createODSTable:DROP:PASSING" + paramString1.trim());
/*      */       } 
/*      */       
/*  566 */       String str3 = "CREATE TABLE " + this.strSchemaODS + "." + paramString1 + " (" + " ENTITYTYPE CHAR(32 ) NOT NULL,  ENTITYID INTEGER NOT NULL,  NLSID INTEGER NOT NULL,   Valfrom TIMESTAMP NOT NULL,  Valto TIMESTAMP NOT NULL" + paramString2 + ") IN " + paramString3 + " INDEX IN " + paramString4;
/*      */ 
/*      */       
/*      */       try {
/*  570 */         if (this.blnLive) {
/*  571 */           Statement statement = this.conODS.createStatement();
/*  572 */           statement.executeUpdate(str3);
/*  573 */           statement.executeUpdate("CREATE UNIQUE INDEX " + this.strSchemaODS + "." + paramString1 + "1 ON " + this.strSchemaODS + "." + paramString1 + "(ENTITYID,NLSID )  ");
/*      */           
/*  575 */           statement.close();
/*      */         } 
/*  577 */       } catch (SQLException sQLException) {
/*  578 */         printErr("createODSTable:CREATE:SQL:ERROR:: \n" + str3 + ":" + sQLException.getMessage());
/*  579 */         printErr("createODSTable:CREATING:********EXIT OCE*********");
/*  580 */         printOK("createODSTable:CREATING:********EXIT OCE*********");
/*  581 */         System.exit(1);
/*      */       }
/*      */     
/*  584 */     } catch (SQLException sQLException) {}
/*      */     
/*  586 */     if (this.strNoData.equals("NODATA")) {
/*  587 */       printOK("Bypassing datamovement...only table will be created");
/*      */     }
/*      */     else {
/*      */       
/*  591 */       moveDataToODS(paramString1);
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
/*      */ 
/*      */   
/*      */   private void moveDataToODS(String paramString) {
/*  612 */     printOK("Starting Datamovement :" + paramString);
/*      */ 
/*      */ 
/*      */     
/*  616 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     try {
/*  618 */       DatabaseMetaData databaseMetaData = this.conODS.getMetaData();
/*  619 */       ResultSet resultSet1 = databaseMetaData.getColumns(null, "%", paramString, "%");
/*  620 */       while (resultSet1.next()) {
/*      */         
/*  622 */         printOK("Triple Byte Column Size " + resultSet1.getString(4).trim() + "|" + resultSet1.getInt(7));
/*  623 */         hashtable.put(resultSet1.getString(4).trim(), new Integer(resultSet1.getInt(7)));
/*      */       } 
/*  625 */     } catch (SQLException sQLException) {
/*  626 */       printErr("moveDataToODS:Error while scanning table " + paramString);
/*  627 */       printErr(sQLException.getMessage());
/*      */     } 
/*      */     
/*  630 */     PreparedStatement preparedStatement = null;
/*  631 */     ResultSet resultSet = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  637 */     int i = 0;
/*  638 */     int j = 0;
/*  639 */     byte b1 = 0;
/*  640 */     byte b2 = 0;
/*      */     
/*  642 */     ReturnDataResultSet returnDataResultSet = null;
/*  643 */     printOK("moveDataToODS:Scanning Entity :" + paramString);
/*      */ 
/*      */     
/*      */     try {
/*  647 */       String str = getQueryStatement(1);
/*  648 */       preparedStatement = this.conPDH.prepareStatement(str);
/*      */       
/*  650 */       preparedStatement.setString(1, this.strTimeStampNow);
/*  651 */       preparedStatement.setString(2, this.strTimeStampNow);
/*  652 */       preparedStatement.setString(3, this.strTimeStampNow);
/*  653 */       preparedStatement.setString(4, this.strTimeStampNow);
/*  654 */       preparedStatement.setString(5, this.strEnterprise);
/*  655 */       preparedStatement.setString(6, paramString);
/*  656 */       preparedStatement.setString(7, this.strTimeStampNow);
/*  657 */       preparedStatement.setString(8, this.strTimeStampNow);
/*  658 */       preparedStatement.setString(9, this.strTimeStampNow);
/*  659 */       preparedStatement.setString(10, this.strTimeStampNow);
/*      */       
/*  661 */       preparedStatement.setString(11, this.strTimeStampNow);
/*  662 */       preparedStatement.setString(12, this.strTimeStampNow);
/*  663 */       preparedStatement.setString(13, this.strTimeStampNow);
/*  664 */       preparedStatement.setString(14, this.strTimeStampNow);
/*  665 */       preparedStatement.setString(15, this.strEnterprise);
/*  666 */       preparedStatement.setString(16, paramString);
/*  667 */       preparedStatement.setString(17, this.strTimeStampNow);
/*  668 */       preparedStatement.setString(18, this.strTimeStampNow);
/*  669 */       preparedStatement.setString(19, this.strTimeStampNow);
/*  670 */       preparedStatement.setString(20, this.strTimeStampNow);
/*      */       
/*  672 */       preparedStatement.setString(21, this.strTimeStampNow);
/*  673 */       preparedStatement.setString(22, this.strTimeStampNow);
/*  674 */       preparedStatement.setString(23, this.strTimeStampNow);
/*  675 */       preparedStatement.setString(24, this.strTimeStampNow);
/*  676 */       preparedStatement.setString(25, this.strEnterprise);
/*  677 */       preparedStatement.setString(26, paramString);
/*  678 */       preparedStatement.setString(27, this.strTimeStampNow);
/*  679 */       preparedStatement.setString(28, this.strTimeStampNow);
/*  680 */       preparedStatement.setString(29, this.strTimeStampNow);
/*  681 */       preparedStatement.setString(30, this.strTimeStampNow);
/*      */       
/*  683 */       preparedStatement.setString(31, this.strTimeStampNow);
/*  684 */       preparedStatement.setString(32, this.strTimeStampNow);
/*  685 */       preparedStatement.setString(33, this.strTimeStampNow);
/*  686 */       preparedStatement.setString(34, this.strTimeStampNow);
/*  687 */       preparedStatement.setString(35, this.strEnterprise);
/*  688 */       preparedStatement.setString(36, paramString);
/*  689 */       preparedStatement.setString(37, this.strTimeStampNow);
/*  690 */       preparedStatement.setString(38, this.strTimeStampNow);
/*  691 */       preparedStatement.setString(39, this.strTimeStampNow);
/*  692 */       preparedStatement.setString(40, this.strTimeStampNow);
/*      */       
/*  694 */       resultSet = preparedStatement.executeQuery();
/*  695 */       returnDataResultSet = new ReturnDataResultSet(resultSet);
/*  696 */       resultSet.close();
/*  697 */       resultSet = null;
/*  698 */       preparedStatement.close();
/*  699 */       preparedStatement = null;
/*  700 */     } catch (SQLException sQLException) {
/*  701 */       printErr("moveDataToODS:Error while Scanning Entity " + paramString);
/*  702 */       printErr(sQLException.getMessage());
/*  703 */       sQLException.printStackTrace();
/*  704 */       System.exit(1);
/*      */     } 
/*  706 */     printOK("moveDataToODS:Scan complete for Entity :" + paramString + " found " + returnDataResultSet.size() + " Entities.");
/*      */     
/*      */     try {
/*  709 */       for (b1 = 0; b1 < returnDataResultSet.size(); b1++) {
/*  710 */         i = returnDataResultSet.getColumnInt(b1, 0);
/*  711 */         j = returnDataResultSet.getColumnInt(b1, 1);
/*      */         
/*  713 */         PreparedStatement preparedStatement1 = null;
/*  714 */         ResultSet resultSet1 = null;
/*      */         
/*  716 */         printOK("moveDataToODS:Extracting Snapshot for Entity :" + paramString + " :ID: " + i + " :NLS:" + j);
/*      */ 
/*      */         
/*      */         try {
/*  720 */           String str = getQueryStatement(4);
/*  721 */           preparedStatement1 = this.conPDH.prepareStatement(str);
/*  722 */           preparedStatement1.setString(1, this.strEnterprise);
/*  723 */           preparedStatement1.setString(2, this.strTimeStampNow);
/*  724 */           preparedStatement1.setString(3, this.strTimeStampNow);
/*  725 */           preparedStatement1.setString(4, this.strTimeStampNow);
/*  726 */           preparedStatement1.setString(5, this.strTimeStampNow);
/*      */           
/*  728 */           preparedStatement1.setString(6, this.strEnterprise);
/*  729 */           preparedStatement1.setString(7, this.strTimeStampNow);
/*  730 */           preparedStatement1.setString(8, this.strTimeStampNow);
/*  731 */           preparedStatement1.setString(9, this.strTimeStampNow);
/*  732 */           preparedStatement1.setString(10, this.strTimeStampNow);
/*      */           
/*  734 */           preparedStatement1.setString(11, this.strEnterprise);
/*  735 */           preparedStatement1.setString(12, paramString);
/*  736 */           preparedStatement1.setInt(13, i);
/*  737 */           preparedStatement1.setString(14, this.strTimeStampNow);
/*  738 */           preparedStatement1.setString(15, this.strTimeStampNow);
/*  739 */           preparedStatement1.setString(16, this.strTimeStampNow);
/*  740 */           preparedStatement1.setString(17, this.strTimeStampNow);
/*      */           
/*  742 */           preparedStatement1.setString(18, this.strEnterprise);
/*  743 */           preparedStatement1.setInt(19, j);
/*  744 */           preparedStatement1.setString(20, this.strTimeStampNow);
/*  745 */           preparedStatement1.setString(21, this.strTimeStampNow);
/*  746 */           preparedStatement1.setString(22, this.strTimeStampNow);
/*  747 */           preparedStatement1.setString(23, this.strTimeStampNow);
/*      */           
/*  749 */           preparedStatement1.setString(24, this.strEnterprise);
/*  750 */           preparedStatement1.setString(25, this.strTimeStampNow);
/*  751 */           preparedStatement1.setString(26, this.strTimeStampNow);
/*  752 */           preparedStatement1.setString(27, this.strTimeStampNow);
/*  753 */           preparedStatement1.setString(28, this.strTimeStampNow);
/*      */           
/*  755 */           preparedStatement1.setInt(29, j);
/*  756 */           preparedStatement1.setString(30, this.strTimeStampNow);
/*  757 */           preparedStatement1.setString(31, this.strTimeStampNow);
/*  758 */           preparedStatement1.setString(32, this.strTimeStampNow);
/*  759 */           preparedStatement1.setString(33, this.strTimeStampNow);
/*      */           
/*  761 */           resultSet1 = preparedStatement1.executeQuery();
/*  762 */         } catch (SQLException sQLException) {
/*  763 */           printErr("moveDataToODS:Error while Extracting Snapshot " + paramString + "ID: " + i + " NLS:" + j);
/*      */           
/*  765 */           printErr(sQLException.getMessage());
/*  766 */           System.exit(1);
/*      */         } 
/*  768 */         printOK("moveDataToODS:Snapshot Extraction complete :Entity :" + paramString + "ID: " + i + " NLS:" + j);
/*      */ 
/*      */         
/*  771 */         updateODSRow(hashtable, paramString, i, j, resultSet1);
/*      */ 
/*      */         
/*  774 */         resultSet1.close();
/*  775 */         preparedStatement1.close();
/*      */         
/*  777 */         if (b2 > 'Ǵ') {
/*  778 */           b2 = 0;
/*  779 */           printOK("moveDataToODS:COMMITTING at :" + b1);
/*  780 */           this.conODS.commit();
/*      */         } else {
/*  782 */           b2++;
/*      */         }
/*      */       
/*      */       }
/*      */     
/*  787 */     } catch (SQLException sQLException) {
/*  788 */       printErr("moveDataToODS: Error!" + sQLException.getMessage());
/*  789 */       sQLException.printStackTrace();
/*  790 */       System.exit(1);
/*      */     } 
/*      */     
/*  793 */     returnDataResultSet = null;
/*      */     
/*  795 */     printOK("Datamovement Complete :" + paramString + " No. of Entity/NLS rows processed :" + b1);
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
/*      */   private void updateODSRow(Hashtable paramHashtable, String paramString, int paramInt1, int paramInt2, ResultSet paramResultSet) {
/*  822 */     String str1 = null;
/*  823 */     String str2 = null;
/*  824 */     String str3 = null;
/*  825 */     int i = 0;
/*      */     
/*  827 */     String str4 = "INSERT INTO " + this.strSchemaODS + "." + paramString;
/*  828 */     String str5 = "(Valfrom,Valto,Entitytype,EntityID,NlsId";
/*  829 */     String str6 = "(?,?,?,?,?";
/*      */     
/*  831 */     Vector<String> vector = new Vector();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  836 */       if (paramResultSet.next()) {
/*      */         do {
/*  838 */           str1 = paramResultSet.getString(1);
/*  839 */           str2 = paramResultSet.getString(2);
/*      */           
/*  841 */           if (str1.equals(str3)) {
/*  842 */             printErr("updateODSRow:WARNING:Duplicate column name found:NAME:" + str1 + "DISCARDING VALUE" + str2);
/*      */           }
/*      */           else {
/*      */             
/*  846 */             str3 = str1;
/*  847 */             str5 = str5 + "," + str1.trim();
/*      */ 
/*      */             
/*  850 */             i = ((Integer)paramHashtable.get(str1.trim())).intValue();
/*      */             
/*  852 */             if (i != 254 && 
/*  853 */               str2.length() > i / this.ODS_COLUMN_MULTIPLIER) {
/*  854 */               printErr("updateODSRow:WARNING:" + paramString + ":" + str1.trim() + " column too wide:" + str2
/*  855 */                   .trim() + " TRUNCATING TO:" + str2
/*  856 */                   .substring(0, i / this.ODS_COLUMN_MULTIPLIER));
/*      */ 
/*      */ 
/*      */               
/*  860 */               str2 = str2.substring(0, i / this.ODS_COLUMN_MULTIPLIER);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  865 */             vector.addElement(str2);
/*      */             
/*  867 */             str6 = str6 + ",?";
/*      */           }
/*      */         
/*      */         }
/*  871 */         while (paramResultSet.next());
/*      */       } else {
/*  873 */         printErr("updateODSRow:WARNING:No Text/Unique flag Data found for Entity: " + paramString + "ID: " + paramInt1 + "NLS: " + paramInt2);
/*      */       } 
/*      */ 
/*      */       
/*  877 */       str5 = str5 + ")";
/*  878 */       str6 = str6 + ")";
/*      */ 
/*      */       
/*  881 */       PreparedStatement preparedStatement = this.conODS.prepareStatement(str4 + str5 + " VALUES " + str6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  888 */       preparedStatement.setString(1, this.strTimeStampNow);
/*  889 */       preparedStatement.setString(2, this.strTimeStampForever);
/*  890 */       preparedStatement.setString(3, paramString);
/*  891 */       preparedStatement.setInt(4, paramInt1);
/*  892 */       preparedStatement.setInt(5, paramInt2);
/*      */       
/*  894 */       Enumeration<String> enumeration = vector.elements();
/*  895 */       String str = null;
/*  896 */       byte b = 6;
/*      */       
/*  898 */       while (enumeration.hasMoreElements()) {
/*  899 */         str = enumeration.nextElement();
/*      */         
/*  901 */         if (str.equals(" ")) {
/*      */           
/*  903 */           preparedStatement.setString(b++, (String)null); continue;
/*      */         } 
/*  905 */         preparedStatement.setString(b++, str);
/*      */       } 
/*      */ 
/*      */       
/*  909 */       preparedStatement.execute();
/*  910 */       preparedStatement.close();
/*  911 */       preparedStatement = null;
/*      */     }
/*  913 */     catch (SQLException sQLException) {
/*      */ 
/*      */ 
/*      */       
/*  917 */       printErr("updateODSRow:ERROR During update:" + sQLException.getMessage());
/*  918 */       sQLException.printStackTrace();
/*  919 */       printErr("updateODSRow:ERROR During update:PROBLEM FOUND FOR ENTITYTYPE:" + paramString + ":ENTITYID:" + paramInt1 + ":NLSID:" + paramInt2);
/*      */ 
/*      */       
/*  922 */       printErr("updateODSRow:Contents of columns: " + str5);
/*  923 */       printErr("updateODSRow:Parameter list: " + str6);
/*  924 */       displayVectorContents("updateODSRow:Contents of columnValues :", vector);
/*  925 */     } catch (Exception exception) {
/*  926 */       printErr("updateODSRow:ERROR:Probably the has table column value is returning NULL" + exception.getMessage());
/*  927 */       printErr("updateODSRow:ERROR During update:PROBLEM FOUND FOR ENTITYTYPE:" + paramString + ":ENTITYID:" + paramInt1 + ":NLSID:" + paramInt2);
/*      */ 
/*      */       
/*  930 */       printErr("updateODSRow:Contents of columns: " + str5);
/*  931 */       printErr("updateODSRow:Parameter list: " + str6);
/*  932 */       displayVectorContents("updateODSRow:Contents of columnValues :", vector);
/*      */     } finally {
/*      */       
/*  935 */       vector = null;
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
/*      */   private void initializeDeleteLog() {
/*  950 */     String str1 = getQueryStatement(14);
/*  951 */     String str2 = getQueryStatement(15);
/*      */     try {
/*  953 */       DatabaseMetaData databaseMetaData = this.conODS.getMetaData();
/*  954 */       Object object1 = null;
/*  955 */       Object object2 = null;
/*      */       
/*  957 */       String[] arrayOfString = { "TABLE" };
/*  958 */       boolean bool = false;
/*  959 */       ResultSet resultSet = databaseMetaData.getTables(null, this.strSchemaODS, "DELETELOG", arrayOfString);
/*  960 */       if (resultSet.next()) {
/*  961 */         bool = true;
/*      */       }
/*  963 */       resultSet.close();
/*      */       
/*  965 */       if (bool) {
/*      */         
/*  967 */         Statement statement1 = this.conODS.createStatement();
/*  968 */         printOK("initializeDeleteLog:DROPPING DELETELOG");
/*  969 */         statement1.execute(str1);
/*  970 */         statement1.close();
/*      */       } else {
/*      */         
/*  973 */         printOK("initializeDeleteLog:DELETELOG DROP BYPASSED");
/*      */       } 
/*  975 */       printOK("initializeDeleteLog:CREATING NEW DELETELOG");
/*  976 */       Statement statement = this.conODS.createStatement();
/*  977 */       statement.execute(str2);
/*  978 */       statement.close();
/*      */     }
/*  980 */     catch (SQLException sQLException) {
/*  981 */       printErr("initializeDeleteLog:ERROR:" + sQLException.getMessage());
/*  982 */       System.exit(1);
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
/*      */   private void initializeTimeTable() {
/*  996 */     String str1 = getQueryStatement(12);
/*  997 */     String str2 = getQueryStatement(10);
/*      */     try {
/*  999 */       DatabaseMetaData databaseMetaData = this.conODS.getMetaData();
/* 1000 */       Object object1 = null;
/* 1001 */       Object object2 = null;
/*      */       
/* 1003 */       String[] arrayOfString = { "TABLE" };
/* 1004 */       boolean bool = false;
/* 1005 */       ResultSet resultSet = databaseMetaData.getTables(null, this.strSchemaODS, "TIMETABLE", arrayOfString);
/* 1006 */       if (resultSet.next()) {
/* 1007 */         bool = true;
/*      */       }
/* 1009 */       resultSet.close();
/*      */       
/* 1011 */       if (bool) {
/*      */         
/* 1013 */         Statement statement1 = this.conODS.createStatement();
/* 1014 */         printOK("initializeTimeTable:DROPPING TIMETABLE");
/* 1015 */         statement1.execute(str1);
/* 1016 */         statement1.close();
/*      */       } else {
/*      */         
/* 1019 */         printOK("initializeTimeTable:TIMETABLE DROP BYPASSED");
/*      */       } 
/* 1021 */       printOK("initializeTimeTable:CREATING NEW TIMETABLE");
/* 1022 */       Statement statement = this.conODS.createStatement();
/* 1023 */       statement.execute(str2);
/* 1024 */       statement.close();
/*      */     }
/* 1026 */     catch (SQLException sQLException) {
/* 1027 */       printErr("initializeTimeTable:ERROR:" + sQLException.getMessage());
/* 1028 */       System.exit(1);
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
/*      */   private void setTimestampInTimetable() {
/* 1044 */     String str = getQueryStatement(11);
/*      */     try {
/* 1046 */       Statement statement = this.conODS.createStatement();
/* 1047 */       statement.execute(str);
/* 1048 */       statement.close();
/*      */     }
/* 1050 */     catch (SQLException sQLException) {
/* 1051 */       printErr("setTimestampInTimetable:ERROR:Insert into timetable:" + sQLException.getMessage());
/* 1052 */       System.exit(1);
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
/*      */   
/*      */   private String getQueryStatement(int paramInt) {
/* 1072 */     switch (paramInt) {
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/* 1077 */         return "SELECT DISTINCT mea.linktype1 AS entitytype    ,UCASE(ma.entitytype) AS attributecode     ,RTRIM(coalesce(ml.linkValue,'0'))  as odsLength    ,RTRIM(coalesce(TS.LINKVALUE,'TSPACE01'))  as TABLESPACE    ,RTRIM(coalesce(IS.linkValue,'ISPACE02'))  as INDEXSPACE    ,ma.entityclass AS attributetype   FROM  " + this.strSchemaPDH + ".metaentity  me  INNER JOIN " + this.strSchemaPDH + ".metalinkattr mea ON    me.entitytype=mea.linktype1  " + ((
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1087 */           !this.strInitializeMode.equals("CONTINUE") && !this.strEntityTableName.equals("ALL")) ? (" AND mea.LinkType1 = '" + this.strEntityTableName + "' ") : " ") + "   AND mea.linktype='Entity/Attribute'     AND mea.linkcode='EntityAttribute'     AND mea.linkvalue='L'     AND me.enterprise = mea.enterprise    AND mea.valto > '" + this.strTimeStampNow + "'     AND mea.effto > '" + this.strTimeStampNow + "'    AND mea.valFrom <= '" + this.strTimeStampNow + "'     AND mea.effFrom <= '" + this.strTimeStampNow + "'  LEFT join " + this.strSchemaPDH + ".metalinkattr ml on    me.enterprise = ml.enterprise     AND mea.linktype2 = ml.linktype2     AND ml.linktype1='ODS'     AND ml.linktype = 'ODSLength'    AND ml.valto > '" + this.strTimeStampNow + "'     AND ml.effto > '" + this.strTimeStampNow + "'    AND ml.valFrom <= '" + this.strTimeStampNow + "'     AND ml.effFrom <= '" + this.strTimeStampNow + "'  LEFT join " + this.strSchemaPDH + ".metalinkattr TS on    me.enterprise = TS.enterprise    AND mea.linktype1 = TS.linktype2     AND ts.linktype1='ODS'     AND ts.linktype = 'ODSConfiguration'     AND ts.linkCode = 'TSPACE'    AND ts.valto > '" + this.strTimeStampNow + "'     AND ts.effto > '" + this.strTimeStampNow + "'    AND ts.valFrom <= '" + this.strTimeStampNow + "'     AND ts.effFrom <= '" + this.strTimeStampNow + "'  LEFT join " + this.strSchemaPDH + ".metalinkattr IS on    me.enterprise = ml.enterprise     AND mea.linktype1 = IS.linktype2     AND Is.linktype1='ODS'     AND Is.linktype = 'ODSConfiguration'     AND Is.linkCode = 'ISPACE'    AND is.valto > '" + this.strTimeStampNow + "'     AND is.effto > '" + this.strTimeStampNow + "'    AND is.valFrom <= '" + this.strTimeStampNow + "'     AND is.effFrom <= '" + this.strTimeStampNow + "'  INNER JOIN " + this.strSchemaPDH + ".metaentity ma ON    mea.linktype2=ma.entitytype    AND mea.enterprise = ma.enterprise    AND ma.entityclass IN ( 'I','T','U','S','A')    AND ma.valto > '" + this.strTimeStampNow + "'     AND ma.effto > '" + this.strTimeStampNow + "'    AND ma.valFrom <= '" + this.strTimeStampNow + "'     AND ma.effFrom <= '" + this.strTimeStampNow + "'  WHERE     me.entityclass IN ('Entity','Relator')    AND me.enterprise= '" + this.strEnterprise + "'     AND me.valto > '" + this.strTimeStampNow + "'     AND me.effto > '" + this.strTimeStampNow + "'     AND me.valFrom <= '" + this.strTimeStampNow + "'     AND me.effFrom <= '" + this.strTimeStampNow + "'   ORDER BY entitytype,attributecode FOR READ ONLY";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1142 */         return " SELECT         E.ENTITYID as ENTITYID       ,T.NLSID    as NLSID    FROM   " + this.strSchemaPDH + ".ENTITY E    INNER JOIN " + this.strSchemaPDH + ".TEXT T ON          T.Enterprise = E.Enterprise          AND T.EntityType = E.EntityType          AND T.EntityID = E.EntityID          AND T.ValFrom <= ?          AND  ? < T.ValTo          AND  T.EffFrom <= ?          AND  ? < T.EffTo    WHERE        E.EntityId > 0        AND E.Enterprise =   ?        AND E.EntityType = ?        AND E.ValFrom <= ?        AND  ? < E.ValTo        AND  E.EffFrom <= ?        AND  ? < E.EffTo   UNION  SELECT  E.ENTITYID as ENTITYID       ,LT.NLSID    as NLSID    FROM   " + this.strSchemaPDH + ".ENTITY E    INNER JOIN " + this.strSchemaPDH + ".LongText LT ON          LT.Enterprise = E.Enterprise          AND LT.EntityType = E.EntityType          AND LT.EntityID = E.EntityID          AND LT.ValFrom <= ?          AND  ? < LT.ValTo          AND  LT.EffFrom <= ?          AND  ? < LT.EffTo    WHERE          E.EntityId > 0          AND E.Enterprise =   ?          AND E.EntityType = ?          AND E.ValFrom <= ?          AND  ? < E.ValTo          AND  E.EffFrom <= ?          AND  ? < E.EffTo   UNION  SELECT        E.ENTITYID as ENTITYID       ,B.NLSID    as NLSID    FROM  " + this.strSchemaPDH + ".ENTITY E    INNER JOIN " + this.strSchemaPDH + ".BLOB B ON          B.Enterprise = E.Enterprise          AND B.EntityType = E.EntityType          AND B.EntityID = E.EntityID          AND B.ValFrom <= ?          AND  ? < B.ValTo          AND  B.EffFrom <= ?          AND  ? < B.EffTo    WHERE          E.EntityId > 0          AND E.Enterprise =   ?          AND E.EntityType = ?          AND E.ValFrom <= ?          AND  ? < E.ValTo          AND  E.EffFrom <= ?          AND  ? < E.EffTo  UNION  SELECT        E.ENTITYID as ENTITYID        ,1    as NLSID    FROM  " + this.strSchemaPDH + ".ENTITY E    INNER JOIN " + this.strSchemaPDH + ".FLAG F ON          F.Enterprise = E.Enterprise          AND F.EntityType = E.EntityType          AND F.EntityID = E.EntityID          AND F.ValFrom <= ?          AND  ? < F.ValTo          AND  F.EffFrom <= ?          AND  ? < F.EffTo    WHERE          E.EntityId > 0          AND E.Enterprise =   ?          AND E.EntityType = ?          AND E.ValFrom <= ?          AND  ? < E.ValTo          AND  E.EffFrom <= ?          AND  ? < E.EffTo  ORDER BY ENTITYID,NLSID ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 1228 */         return " WITH MYTABLE (ENTITYTYPE,ENTITYID,ATTRIBUTECODE,ATTRIBUTETYPE) AS   ( SELECT         E.EntityType       ,E.EntityID       ,UCASE(MA.EntityType)  as AttributeCode      ,MA.EntityClass as AttributeType    FROM  " + this.strSchemaPDH + ".ENTITY E        INNER JOIN  " + this.strSchemaPDH + ".metalinkattr ML ON          ML.Enterprise = ?            AND ML.linktype = 'Entity/Attribute'          AND ML.linktype1 = E.EntityType          AND ML.linkcode='EntityAttribute'         AND ML.linkvalue='L'         AND ML.ValFrom <= ? AND ? <ML.ValTo         AND ML.EffFrom <= ? AND ? < ML.EffTo     INNER JOIN  " + this.strSchemaPDH + ".MetaEntity MA ON         MA.Enterprise = ?         AND MA.EntityType = ML.Linktype2        AND MA.EntityClass in ('T','I','U','S')         AND MA.ValFrom <= ? AND ? <MA.ValTo         AND MA.EffFrom <= ? AND ? < MA.EffTo    WHERE       E.Enterprise = ?       AND E.EntityType = ?       AND E.Entityid = ?       AND E.ValFrom <= ? AND ? < E.ValTo       AND E.EffFrom <= ? AND ? < E.EffTo  )     SELECT DISTINCT        MT.AttributeCode  as AttributeCode                ,COALESCE(RTRIM(t.AttributeValue),' ') as AttributeValue  FROM MYTABLE MT  LEFT JOIN " + this.strSchemaPDH + ".TEXT T ON        T.enterprise = ?        AND T.entitytype = MT.entitytype        AND T.entityid = MT.entityid        AND UCASE(T.attributecode) = MT.attributecode        AND T.nlsid = ?        AND T.ValFrom <= ? AND ? < T.ValTo        AND T.EffFrom <= ? AND ? < T.EffTo  WHERE        MT.attributeType in ('T','I')  UNION ALL  SELECT DISTINCT         MT.AttributeCode  as AttributeCode                  ,COALESCE(RTRIM(MD.LongDescription),' ') as AttributeValue     FROM mytable MT  LEFT JOIN " + this.strSchemaPDH + ".flag F ON              F.enterprise = ?          AND F.entitytype = MT.entitytype          AND F.entityid = MT.entityid          AND UCASE(F.attributecode) = MT.attributecode          AND F.ValFrom <= ? AND ? < F.ValTo          AND F.EffFrom <= ? AND ? < F.EffTo  LEFT JOIN  " + this.strSchemaPDH + ".MetaDescription MD ON            MD.Enterprise = F.Enterprise         AND MD.descriptionType = F.AttributeCode        AND MD.DescriptionClass = F.AttributeValue        AND MD.NLSID = ?        AND MD.valfrom <= ? and  ? < MD.ValTo        AND MD.efffrom <= ? and  ?  < MD.effTo  WHERE  MT.attributeType IN  ('U','S')  ORDER BY attributecode ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 13:
/* 1297 */         return "CREATE UNIQUE INDEX " + this.strSchemaODS + "." + this.strCurrentEntity
/* 1298 */           .trim() + "1 ON " + this.strSchemaODS + "." + this.strCurrentEntity
/* 1299 */           .trim() + "(ENTITYID,NLSID )  ";
/*      */       case 8:
/*      */       case 9:
/* 1302 */         return "DROP TABLE " + this.strSchemaODS + "." + this.strCurrentEntity
/* 1303 */           .trim();
/*      */       case 10:
/* 1305 */         return "CREATE TABLE " + this.strSchemaODS + ".TIMETABLE (RUNTIME TIMESTAMP NOT NULL, RUNTYPE VARCHAR(1), PRIMARY KEY (RUNTIME)) ";
/*      */ 
/*      */       
/*      */       case 15:
/* 1309 */         return "CREATE TABLE " + this.strSchemaODS + ".DELETELOG ( ENTITYTYPE CHAR(" + "32" + " ) NOT NULL,  ENTITYID INTEGER NOT NULL,  NLSID INTEGER , ENTITY1TYPE CHAR(" + "32" + " ),  ENTITY1ID INTEGER , ENTITY2TYPE CHAR(" + "32" + " ),  ENTITY2ID INTEGER , OPICMTYPE CHAR(32) NOT NULL, ATTRIBUTECODE CHAR(32) ,  ATTRIBUTEVALUE CHAR(24) ,  VALFROM TIMESTAMP NOT NULL, PRIMARY KEY (OPICMTYPE,ENTITYTYPE,ENTITYID,VALFROM)) ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 12:
/* 1323 */         return "DROP TABLE " + this.strSchemaODS + ".TIMETABLE";
/*      */       
/*      */       case 14:
/* 1326 */         return "DROP TABLE " + this.strSchemaODS + ".DELETELOG";
/*      */       
/*      */       case 11:
/* 1329 */         return "INSERT INTO " + this.strSchemaODS + ".TIMETABLE (RUNTIME,RUNTYPE) VALUES('" + this.strTimeStampNow + "','I')";
/*      */     } 
/*      */ 
/*      */     
/* 1333 */     return "";
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
/*      */   private void printOK(String paramString) {
/* 1349 */     String str = this.c_sdfTimestamp.format(new Date());
/* 1350 */     System.out.println(str + " OCE: " + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printErr(String paramString) {
/* 1360 */     String str = this.c_sdfTimestamp.format(new Date());
/* 1361 */     System.err.println(str + " OCE: " + paramString);
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
/*      */   public static void displayVectorContents(String paramString, Vector paramVector) {
/* 1375 */     Vector vector = paramVector;
/* 1376 */     System.err.println(paramString);
/* 1377 */     Enumeration<String> enumeration = vector.elements();
/* 1378 */     while (enumeration.hasMoreElements()) {
/* 1379 */       System.err.println("    |" + enumeration.nextElement() + "|");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getVersion() {
/* 1390 */     return "$Id: ODSCreateEntityShell.java,v 1.25 2003/10/20 16:56:25 bala Exp $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readPropertyFile() {
/* 1398 */     printOK("Reading Datamover.properties");
/* 1399 */     Properties properties = new Properties();
/*      */     try {
/* 1401 */       properties.load(new FileInputStream("datamover.properties"));
/* 1402 */     } catch (IOException iOException) {
/* 1403 */       printOK("Error reading datamover.properties");
/* 1404 */       System.exit(1);
/*      */     } 
/*      */     
/* 1407 */     this.strurlODS = properties.getProperty("ODSURL");
/* 1408 */     this.struidODS = properties.getProperty("ODSID");
/* 1409 */     this.strpwdODS = properties.getProperty("ODSPWD");
/* 1410 */     this.strurlPDH = properties.getProperty("PDHURL");
/* 1411 */     this.struidPDH = properties.getProperty("PDHID");
/* 1412 */     this.strNLSOds = properties.getProperty("ODSNLS", "ALL");
/*      */     
/* 1414 */     this.strpwdPDH = properties.getProperty("PDHPWD");
/* 1415 */     this.strSchemaPDH = properties.getProperty("PDHSCHEMA");
/* 1416 */     this.strSchemaODS = properties.getProperty("ODSSCHEMA", "OPICM").toUpperCase();
/* 1417 */     this.strEnterprise = properties.getProperty("ENTERPRISE");
/* 1418 */     this.strRunMode = properties.getProperty("RUNMODE");
/* 1419 */     this.strInitializeMode = properties.getProperty("INITIALIZEMODE");
/*      */ 
/*      */ 
/*      */     
/* 1423 */     printOK("Read properties.");
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\ODSCreateEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */