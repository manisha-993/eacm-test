/*      */ import java.sql.Connection;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Date;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ODSInitV2
/*      */ {
/*      */   private static final int COMMITCOUNT = 500;
/*      */   private static final int TWENTY = 20;
/*      */   private static final int NINETEEN = 19;
/*      */   private static final int EIGHTEEN = 18;
/*      */   private static final int SEVENTEEN = 17;
/*      */   private static final int SIXTEEN = 16;
/*      */   private static final int FIFTEEN = 15;
/*      */   private static final int FOURTEEN = 14;
/*      */   private static final int THIRTEEN = 13;
/*      */   private static final int TWELVE = 12;
/*      */   private static final int ELEVEN = 11;
/*      */   private static final int LOOPCOUNT = 5000;
/*  168 */   private String uidOPICM = "";
/*  169 */   private String pwdOPICM = "";
/*  170 */   private String dsnOPICM = "";
/*  171 */   private String schOPICM = "";
/*  172 */   private String urlOPICM = "";
/*      */   
/*  174 */   private String uidODS = "";
/*  175 */   private String pwdODS = "";
/*  176 */   private String dsnODS = "";
/*  177 */   private String schODS = "";
/*  178 */   private String urlODS = "";
/*      */   
/*  180 */   private String enterprise = "";
/*  181 */   private String cmd = "";
/*      */   
/*  183 */   private Connection conODS = null;
/*      */ 
/*      */   
/*  186 */   private Connection conOPICM = null;
/*      */ 
/*      */ 
/*      */   
/*  190 */   private int OPICM_TABLE_BUILDER_QRY = 0;
/*      */   
/*  192 */   private int OPICM_SCAN_RELATOR_TABLE_QRY = 1;
/*  193 */   private final int ODS_DROP_RELATOR_TABLE_QRY = 2;
/*  194 */   private final int ODS_CREATE_RELATOR_TABLE_QRY = 3;
/*  195 */   private final int ODS_PQ_INSERT_RELATOR_TABLE_QRY = 4;
/*      */ 
/*      */   
/*  198 */   private final int OPICM_SCAN_FLAG_TABLE_QRY = 5;
/*  199 */   private final int ODS_DROP_FLAG_TABLE_QRY = 6;
/*  200 */   private final int ODS_CREATE_FLAG_TABLE_QRY = 7;
/*  201 */   private final int ODS_PQ_INSERT_FLAG_TABLE_QRY = 8;
/*      */ 
/*      */   
/*  204 */   private final int ODS_DROP_LONGTEXT_TABLE_QRY = 9;
/*  205 */   private final int OPICM_SCAN_LONGTEXT_TABLE_QRY = 10;
/*  206 */   private final int ODS_CREATE_LONGTEXT_TABLE_QRY = 11;
/*  207 */   private final int ODS_PQ_INSERT_LONGTEXT_TABLE_QRY = 12;
/*      */ 
/*      */   
/*  210 */   private final int ODS_DROP_BLOB_TABLE_QRY = 13;
/*  211 */   private final int OPICM_SCAN_BLOB_TABLE_QRY = 14;
/*  212 */   private final int ODS_CREATE_BLOB_TABLE_QRY = 15;
/*  213 */   private final int ODS_PQ_INSERT_BLOB_TABLE_QRY = 16;
/*      */ 
/*      */   
/*  216 */   private final int ODS_DROP_MD_TABLE_QRY = 17;
/*  217 */   private final int OPICM_SCAN_MD_TABLE_QRY = 18;
/*  218 */   private final int ODS_CREATE_MD_TABLE_QRY = 19;
/*  219 */   private final int ODS_PQ_INSERT_MD_TABLE_QRY = 20;
/*      */   
/*  221 */   private SimpleDateFormat formatTS = new SimpleDateFormat("yyyy-MM-dd");
/*      */ 
/*      */ 
/*      */   
/*  225 */   private String todaystr = getFormatTS().format(new Date());
/*  226 */   private String myunow = getTodaystr() + "-00.00.00.000000";
/*  227 */   private String mynow = getTodaystr() + "-23.59.59.000000";
/*  228 */   private String forever = "9999-12-31-00.00.00.000000";
/*  229 */   private String epic = "1980-01-01-00.00.00.000000";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ODSInitV2(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) {
/*  256 */     setDsnOPICM(paramString1);
/*  257 */     setPwdOPICM(paramString3);
/*  258 */     setUidOPICM(paramString2);
/*  259 */     setUrlOPICM("jdbc:db2:" + paramString1);
/*  260 */     setSchOPICM(paramString4);
/*      */ 
/*      */     
/*  263 */     setDsnODS(paramString5);
/*  264 */     setPwdODS(paramString7);
/*  265 */     setUidODS(paramString6);
/*  266 */     setSchODS(paramString8);
/*      */     
/*  268 */     setUrlODS("jdbc:db2:" + paramString5);
/*      */     
/*  270 */     setEnterprise(paramString9);
/*  271 */     setCmd(paramString10);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  277 */       Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
/*  278 */       setConOPICM(
/*  279 */           DriverManager.getConnection(
/*  280 */             getUrlOPICM(), 
/*  281 */             getUidOPICM(), 
/*  282 */             getPwdOPICM()));
/*  283 */       setConODS(
/*  284 */           DriverManager.getConnection(getUrlODS(), getUidODS(), getPwdODS()));
/*  285 */       getConOPICM().setAutoCommit(true);
/*  286 */       getConODS().setAutoCommit(true);
/*  287 */     } catch (Exception exception) {
/*  288 */       System.err.println(new Date() + ":connection:err: " + exception.getMessage());
/*  289 */       System.exit(-1);
/*      */     } 
/*      */     
/*  292 */     mainline();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mainline() {
/*  301 */     System.out.println("Initializing for Enterprise" + getEnterprise());
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  306 */       if (getCmd().compareTo("relator") == 0) {
/*  307 */         System.out.println(new Date() + ":dminitV2:relators were requested.  Starting...");
/*      */         
/*  309 */         genRelatorTable();
/*  310 */         System.out.println(new Date() + ":dminitV2:relators were requested.  Wrapping Up...");
/*      */ 
/*      */       
/*      */       }
/*  314 */       else if (getCmd().compareTo("flag") == 0) {
/*  315 */         System.out.println(new Date() + ":dminitV2:Flags were requested.  Starting...");
/*      */         
/*  317 */         genFlagTable();
/*  318 */         System.out.println(new Date() + ":dminitV2:Flags were requested.  Wrapping Up...");
/*      */ 
/*      */       
/*      */       }
/*  322 */       else if (getCmd().compareTo("longtext") == 0) {
/*  323 */         System.out.println(new Date() + ":dminitV2:LongText was requested.  Starting...");
/*      */         
/*  325 */         genLongTextTable();
/*  326 */         System.out.println(new Date() + ":dminitV2:LongText was requested.  Wrapping Up...");
/*      */ 
/*      */       
/*      */       }
/*  330 */       else if (getCmd().compareTo("blob") == 0) {
/*  331 */         System.out.println(new Date() + ":dminitV2:Blobs were requested.  Starting...");
/*      */         
/*  333 */         genBlobTable();
/*  334 */         System.out.println(new Date() + ":dminitV2:Blobs were requested.  Wrapping Up...");
/*      */ 
/*      */       
/*      */       }
/*  338 */       else if (getCmd().compareTo("md") == 0) {
/*  339 */         System.out.println(new Date() + ":dminitV2:Metadescription were requested.  Starting...");
/*      */ 
/*      */         
/*  342 */         genMDTable();
/*  343 */         System.out.println(new Date() + ":dminitV2:Metadescription were requested.  Wrapping Up...");
/*      */       }
/*      */     
/*      */     }
/*  347 */     catch (SQLException sQLException) {
/*  348 */       System.err.println(new Date() + ":general-error" + sQLException.getMessage());
/*  349 */       sQLException.printStackTrace();
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
/*      */   private void genRelatorTable() throws SQLException {
/*  361 */     Statement statement = getConODS().createStatement();
/*  362 */     System.out.println(new Date() + ":genRelatorTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/*  366 */       statement.executeUpdate(getQueryStatement(2));
/*  367 */       System.out.println(new Date() + ":genRelatorTable:Dropped old table.");
/*  368 */       getConODS().commit();
/*      */     }
/*  370 */     catch (SQLException sQLException) {
/*  371 */       sQLException.printStackTrace();
/*  372 */       System.out.println(new Date() + ":genRelatorTable:Skipping Drop, Relator Table Not Found.");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  378 */       statement.executeUpdate(getQueryStatement(3));
/*  379 */       System.out.println(new Date() + ":genRelatorTable:Created new table.");
/*  380 */       popRelatorTable();
/*  381 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  383 */           getSchODS() + ".R1 ON " + 
/*      */           
/*  385 */           getSchODS() + ".RELATOR (ENTITY1ID,ENTITYTYPE) ");
/*      */       
/*  387 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  389 */           getSchODS() + ".R2 ON " + 
/*      */           
/*  391 */           getSchODS() + ".RELATOR (ENTITY2ID, ENTITYTYPE) ");
/*      */ 
/*      */       
/*  394 */       System.out.println(new Date() + ":genRelatorTable:Created R1,R2,R3,R4 indices on the relator table.");
/*      */     
/*      */     }
/*  397 */     catch (SQLException sQLException) {
/*  398 */       sQLException.printStackTrace();
/*  399 */       System.out.println(new Date() + ":genRelatorTable:Problem in Creating relator table.");
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/*  404 */       statement.close();
/*      */     } 
/*      */ 
/*      */     
/*  408 */     System.out.println("CREATERELATORTABLE:" + new Date() + ":FINISH");
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
/*      */   private void genFlagTable() throws SQLException {
/*  420 */     Statement statement = getConODS().createStatement();
/*      */     
/*  422 */     System.out.println(new Date() + ":genFlagTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/*  426 */       statement.executeUpdate(getQueryStatement(6));
/*  427 */       System.out.println(new Date() + ":genFlagTable:Dropped old table.");
/*  428 */       getConODS().commit();
/*      */     }
/*  430 */     catch (SQLException sQLException) {
/*  431 */       sQLException.printStackTrace();
/*  432 */       System.out.println(new Date() + ":genFlagTable:Skipping Drop, Table Not Found.");
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  437 */       statement.executeUpdate(getQueryStatement(7));
/*  438 */       System.out.println(new Date() + ":genFlagTable:Created new table.");
/*  439 */       popFlagTable();
/*      */       
/*  441 */       statement.executeUpdate("ALTER TABLE " + 
/*  442 */           getSchODS() + ".FLAG ADD PRIMARY KEY(ENTITYID,ENTITYTYPE,ATTRIBUTECODE,ATTRIBUTEVALUE)");
/*      */       
/*  444 */       System.out.println(new Date() + ":genFlagTable:Created Primary Key Index");
/*      */       
/*  446 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  448 */           getSchODS() + ".F1 ON " + 
/*      */           
/*  450 */           getSchODS() + ".FLAG (ENTITYID,ENTITYTYPE,ATTRIBUTECODE,VALFROM)");
/*      */       
/*  452 */       System.out.println(new Date() + ":genFlagTable:Created F1 Key Index");
/*      */       
/*  454 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  456 */           getSchODS() + ".F2 ON " + 
/*      */           
/*  458 */           getSchODS() + ".FLAG (ENTITYID,ENTITYTYPE,VALFROM)");
/*      */ 
/*      */       
/*  461 */       System.out.println(new Date() + ":genFlagTable:Created PK,F1,F2 indices on the Flag table.");
/*      */     }
/*  463 */     catch (SQLException sQLException) {
/*  464 */       sQLException.printStackTrace();
/*  465 */       System.out.println(new Date() + ":genFlagTable:Problem creating flag table.");
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/*  470 */       statement.close();
/*      */     } 
/*      */     
/*  473 */     System.out.println(new Date() + ":genFlagTable:Fini.");
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
/*      */   private void genBlobTable() throws SQLException {
/*  485 */     Statement statement = getConODS().createStatement();
/*  486 */     System.out.println(new Date() + ":genBlobTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/*  490 */       statement.executeUpdate(getQueryStatement(13));
/*  491 */       System.out.println(new Date() + ":genBlobTable:Dropped old table.");
/*  492 */       getConODS().commit();
/*      */     }
/*  494 */     catch (SQLException sQLException) {
/*  495 */       sQLException.printStackTrace();
/*  496 */       System.out.println(new Date() + ":genBlobTable:Skipping Drop, Table Not Found.");
/*      */     } 
/*      */     
/*      */     try {
/*  500 */       statement.executeUpdate(getQueryStatement(15));
/*  501 */       System.out.println(new Date() + ":genBlobTable:Created new table.");
/*  502 */       popBlobTable();
/*      */       
/*  504 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  506 */           getSchODS() + ".B1 ON " + 
/*      */           
/*  508 */           getSchODS() + ".BLOB (ENTITYID, ENTITYTYPE,ATTRIBUTECODE,NLSID,VALFROM)");
/*      */       
/*  510 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  512 */           getSchODS() + ".B2 ON " + 
/*      */           
/*  514 */           getSchODS() + ".BLOB (ENTITYID, ENTITYTYPE,NLSID,VALFROM)");
/*      */       
/*  516 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  518 */           getSchODS() + ".B3 ON " + 
/*      */           
/*  520 */           getSchODS() + ".BLOB (ENTITYID, ENTITYTYPE,VALFROM)");
/*      */ 
/*      */       
/*  523 */       System.out.println(new Date() + ":genBlobTable:Created B1,B2,B3 indices on the Blob table.");
/*      */ 
/*      */     
/*      */     }
/*  527 */     catch (SQLException sQLException) {
/*  528 */       sQLException.printStackTrace();
/*  529 */       System.out.println(new Date() + ":genBlobTable:Problem creating blob table.");
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/*  534 */       statement.close();
/*      */     } 
/*  536 */     System.out.println(new Date() + ":genBlobTable:Fini.");
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
/*      */   private void genLongTextTable() throws SQLException {
/*  548 */     Statement statement = getConODS().createStatement();
/*  549 */     System.out.println(new Date() + ":genLongTextTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/*  553 */       statement.executeUpdate(getQueryStatement(9));
/*  554 */       System.out.println(new Date() + ":genLongTextTable:Dropped old table.");
/*  555 */       getConODS().commit();
/*      */     }
/*  557 */     catch (SQLException sQLException) {
/*  558 */       sQLException.printStackTrace();
/*  559 */       System.out.println(new Date() + ":genLongTextTable:Skipping Drop, Table Not Found.");
/*      */     } 
/*      */     
/*      */     try {
/*  563 */       statement.executeUpdate(getQueryStatement(11));
/*  564 */       System.out.println(new Date() + ":genLongTextTable:Created new table.");
/*  565 */       popLongTextTable();
/*      */       
/*  567 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  569 */           getSchODS() + ".LT1 ON " + 
/*      */           
/*  571 */           getSchODS() + ".LONGTEXT (ENTITYID,ENTITYTYPE,ATTRIBUTECODE,NLSID, VALFROM)");
/*      */       
/*  573 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  575 */           getSchODS() + ".LT2 ON " + 
/*      */           
/*  577 */           getSchODS() + ".LONGTEXT (ENTITYID,ENTITYTYPE,NLSID, VALFROM)");
/*      */       
/*  579 */       statement.executeUpdate("CREATE INDEX " + 
/*      */           
/*  581 */           getSchODS() + ".LT3 ON " + 
/*      */           
/*  583 */           getSchODS() + ".LONGTEXT (ENTITYID,ENTITYTYPE, VALFROM)");
/*      */       
/*  585 */       System.out.println(new Date() + ":genLongTextTable:Created LT1, LT2, LT3 indices on the Long Texttable.");
/*      */     
/*      */     }
/*  588 */     catch (SQLException sQLException) {
/*  589 */       sQLException.printStackTrace();
/*  590 */       System.out.println(new Date() + ":genLongTextTable:Problem creating LongText Table.");
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/*  596 */       statement.close();
/*      */     } 
/*      */     
/*  599 */     System.out.println(new Date() + ":genLongTextTable:Fini.");
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
/*      */   private void genMDTable() throws SQLException {
/*  611 */     Statement statement = getConODS().createStatement();
/*  612 */     System.out.println(new Date() + ":genMDTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/*  616 */       statement.executeUpdate(getQueryStatement(17));
/*  617 */       System.out.println(new Date() + ":genMDTable:Dropped old table.");
/*  618 */       getConODS().commit();
/*      */     }
/*  620 */     catch (SQLException sQLException) {
/*  621 */       System.err.println(sQLException.getMessage());
/*  622 */       System.out.println(new Date() + ":genMDTable:Skipping Drop, Table Not Found.");
/*      */     } finally {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  628 */         statement.close();
/*      */       }
/*  630 */       catch (SQLException sQLException) {
/*  631 */         sQLException.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/*  635 */     statement.executeUpdate(getQueryStatement(19));
/*  636 */     System.out.println(new Date() + ":genMDTable:Created new MetaFlagValue table.");
/*      */     
/*  638 */     popMDTable();
/*      */ 
/*      */     
/*  641 */     System.out.println(new Date() + ":genMDTable:Fini.");
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
/*      */   public void popFlagTable() {
/*  654 */     byte b1 = 0;
/*  655 */     byte b2 = 0;
/*  656 */     byte b3 = 1;
/*  657 */     PreparedStatement preparedStatement1 = null;
/*  658 */     ResultSet resultSet = null;
/*  659 */     PreparedStatement preparedStatement2 = null;
/*  660 */     System.out.println(new Date() + ":popFlagTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/*  664 */       getConODS().setAutoCommit(false);
/*      */       
/*  666 */       preparedStatement1 = getConODS().prepareStatement(
/*  667 */           getQueryStatement(8));
/*      */       
/*  669 */       preparedStatement2 = getConOPICM().prepareStatement(
/*  670 */           getQueryStatement(5));
/*      */ 
/*      */ 
/*      */       
/*  674 */       System.out.println(new Date() + ":popFlagTable:OPICMFlagQueryBegins.");
/*  675 */       resultSet = preparedStatement2.executeQuery();
/*  676 */       System.out.println(new Date() + ":popFlagTable:OPICMFlagQueryEnds.");
/*      */       
/*  678 */       while (resultSet.next()) {
/*      */         
/*      */         try {
/*  681 */           preparedStatement1.setString(1, getMyunow());
/*  682 */           preparedStatement1.setString(2, resultSet.getString(1).trim());
/*  683 */           preparedStatement1.setInt(3, resultSet.getInt(2));
/*  684 */           preparedStatement1.setString(4, resultSet.getString(3).trim());
/*  685 */           preparedStatement1.setString(5, resultSet.getString(4).trim());
/*  686 */           preparedStatement1.executeUpdate();
/*      */           
/*  688 */           b1++;
/*  689 */           b3++;
/*  690 */           if (b3 == 'ᎈ') {
/*  691 */             System.out.println(new Date() + ":popFlagTable:commit on:" + b1);
/*  692 */             getConODS().commit();
/*  693 */             b3 = 1;
/*      */           } 
/*  695 */         } catch (SQLException sQLException) {
/*  696 */           b2++;
/*  697 */           System.err.println(new Date() + "popFlagTable:SQL Panic, Skipping insert:ET:" + resultSet
/*      */ 
/*      */               
/*  700 */               .getString(1).trim() + ":EID:" + resultSet
/*      */               
/*  702 */               .getInt(2) + ":COUNT:" + b1 + ":" + sQLException
/*      */ 
/*      */ 
/*      */               
/*  706 */               .getMessage());
/*      */         } 
/*      */       } 
/*  709 */       getConODS().commit();
/*  710 */       getConODS().setAutoCommit(true);
/*      */     }
/*  712 */     catch (SQLException sQLException) {
/*  713 */       System.err.println(new Date() + ":popFlagTable:" + sQLException.getMessage());
/*  714 */       System.exit(1);
/*      */     } finally {
/*      */       try {
/*  717 */         resultSet.close();
/*  718 */         preparedStatement2.close();
/*  719 */         preparedStatement1.close();
/*      */       }
/*  721 */       catch (SQLException sQLException) {
/*  722 */         sQLException.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/*  726 */     System.out.println("popFlagTable:" + new Date() + ":POPULATE:FINISH:INSERT-COUNT:" + b1 + ":ERRORS:" + b2);
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
/*      */   public void popRelatorTable() {
/*  744 */     byte b1 = 0;
/*  745 */     byte b2 = 0;
/*  746 */     byte b3 = 1;
/*  747 */     PreparedStatement preparedStatement1 = null;
/*  748 */     PreparedStatement preparedStatement2 = null;
/*  749 */     ResultSet resultSet = null;
/*  750 */     System.out.println(new Date() + ":popRelatorTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/*  754 */       getConODS().setAutoCommit(false);
/*      */ 
/*      */       
/*  757 */       preparedStatement1 = getConODS().prepareStatement(
/*  758 */           getQueryStatement(4));
/*      */       
/*  760 */       preparedStatement2 = getConOPICM().prepareStatement(
/*  761 */           getQueryStatement(getOPICM_SCAN_RELATOR_TABLE_QRY()));
/*      */ 
/*      */ 
/*      */       
/*  765 */       System.out.println(new Date() + ":populateRelatorTable:OPICMRelatorQueryBegins.");
/*      */       
/*  767 */       resultSet = preparedStatement2.executeQuery();
/*  768 */       System.out.println(new Date() + ":populateRelatorTable:OPICMRelatorQueryEnds.");
/*      */ 
/*      */       
/*  771 */       while (resultSet.next()) {
/*      */         
/*      */         try {
/*  774 */           preparedStatement1.setString(1, getMyunow());
/*  775 */           preparedStatement1.setString(2, resultSet.getString(1).trim());
/*  776 */           preparedStatement1.setInt(3, resultSet.getInt(2));
/*  777 */           preparedStatement1.setString(4, resultSet.getString(3).trim());
/*  778 */           preparedStatement1.setInt(5, resultSet.getInt(4));
/*  779 */           preparedStatement1.setString(6, resultSet.getString(5).trim());
/*  780 */           preparedStatement1.setInt(7, resultSet.getInt(6));
/*  781 */           preparedStatement1.executeUpdate();
/*      */           
/*  783 */           b1++;
/*  784 */           b3++;
/*  785 */           if (b3 == 'ᎈ') {
/*  786 */             System.out.println(new Date() + ":popRelatorTable:commit on:" + b1);
/*      */             
/*  788 */             getConODS().commit();
/*  789 */             b3 = 1;
/*      */           } 
/*  791 */         } catch (SQLException sQLException) {
/*  792 */           b2++;
/*  793 */           System.err.println(new Date() + "popRelatorTable:SQL Panic, Skipping insert:ET:" + resultSet
/*      */ 
/*      */               
/*  796 */               .getString(1).trim() + ":EID:" + resultSet
/*      */               
/*  798 */               .getInt(2) + ":COUNT:" + b1 + ":" + sQLException
/*      */ 
/*      */ 
/*      */               
/*  802 */               .getMessage());
/*      */         } 
/*      */       } 
/*  805 */       getConODS().commit();
/*  806 */       getConODS().setAutoCommit(true);
/*      */     }
/*  808 */     catch (SQLException sQLException) {
/*  809 */       System.err.println(new Date() + ":popRelatorTable:" + sQLException.getMessage());
/*  810 */       System.exit(1);
/*      */     } finally {
/*      */       try {
/*  813 */         resultSet.close();
/*  814 */         preparedStatement2.close();
/*  815 */         preparedStatement1.close();
/*      */       }
/*  817 */       catch (SQLException sQLException) {
/*  818 */         sQLException.printStackTrace();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  823 */     System.out.println(new Date() + ":popRelatorTable:Finished.  INSERT-COUNT:" + b1 + ":ERRORS:" + b2);
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
/*      */   public void popLongTextTable() {
/*  839 */     byte b1 = 0;
/*  840 */     byte b2 = 0;
/*  841 */     byte b3 = 1;
/*  842 */     PreparedStatement preparedStatement1 = null;
/*  843 */     PreparedStatement preparedStatement2 = null;
/*  844 */     ResultSet resultSet = null;
/*  845 */     System.out.println(new Date() + ":popLongTextTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/*  849 */       getConODS().setAutoCommit(false);
/*      */ 
/*      */       
/*  852 */       preparedStatement1 = getConODS().prepareStatement(
/*  853 */           getQueryStatement(12));
/*      */       
/*  855 */       preparedStatement2 = getConOPICM().prepareStatement(
/*  856 */           getQueryStatement(10));
/*      */ 
/*      */ 
/*      */       
/*  860 */       System.out.println(new Date() + ":popLongTextTable:OPICM LongText Query Begins.");
/*      */       
/*  862 */       resultSet = preparedStatement2.executeQuery();
/*  863 */       System.out.println(new Date() + ":popLongTextTable::OPICM LongText Query Ends.");
/*      */ 
/*      */       
/*  866 */       while (resultSet.next()) {
/*      */         
/*      */         try {
/*  869 */           preparedStatement1.setString(1, getMyunow());
/*  870 */           preparedStatement1.setString(2, resultSet.getString(1).trim());
/*  871 */           preparedStatement1.setInt(3, resultSet.getInt(2));
/*  872 */           preparedStatement1.setString(4, resultSet.getString(3).trim());
/*  873 */           preparedStatement1.setString(5, resultSet.getString(4).trim());
/*  874 */           preparedStatement1.setInt(6, resultSet.getInt(5));
/*  875 */           preparedStatement1.setString(7, resultSet.getString(6).trim());
/*  876 */           preparedStatement1.executeUpdate();
/*      */           
/*  878 */           b1++;
/*  879 */           b3++;
/*  880 */           if (b3 == 'ᎈ') {
/*  881 */             System.out.println(new Date() + ":popLongTextTable:commit on:" + b1);
/*      */             
/*  883 */             getConODS().commit();
/*  884 */             b3 = 1;
/*      */           } 
/*  886 */         } catch (SQLException sQLException) {
/*  887 */           b2++;
/*  888 */           System.err.println(new Date() + "popLongTextTable:SQL Panic, Skipping insert.  :ET:" + resultSet
/*      */ 
/*      */ 
/*      */               
/*  892 */               .getString(1).trim() + ":EID:" + resultSet
/*      */               
/*  894 */               .getInt(2) + ":AC: " + resultSet
/*      */               
/*  896 */               .getString(3).trim() + ":AT: " + resultSet
/*      */               
/*  898 */               .getString(4).trim() + ":NLS: " + resultSet
/*      */               
/*  900 */               .getInt(5) + ":COUNT:" + b1 + ":" + sQLException
/*      */ 
/*      */ 
/*      */               
/*  904 */               .getMessage());
/*      */         } 
/*      */       } 
/*  907 */       getConODS().commit();
/*  908 */       getConODS().setAutoCommit(true);
/*      */     }
/*  910 */     catch (SQLException sQLException) {
/*  911 */       System.err.println(new Date() + ":popLongTextTable:" + sQLException.getMessage());
/*  912 */       System.exit(1);
/*      */     } finally {
/*      */       try {
/*  915 */         resultSet.close();
/*  916 */         preparedStatement2.close();
/*  917 */         preparedStatement1.close();
/*      */       }
/*  919 */       catch (SQLException sQLException) {
/*  920 */         sQLException.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/*  924 */     System.out.println(new Date() + ":popLongTextTable:Finished.  # of Recs Processed:" + b1 + "  # number of errors:" + b2);
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
/*      */   public void popBlobTable() {
/*  942 */     byte b1 = 0;
/*  943 */     byte b2 = 0;
/*  944 */     byte b3 = 0;
/*  945 */     int i = 0;
/*  946 */     PreparedStatement preparedStatement1 = null;
/*  947 */     PreparedStatement preparedStatement2 = null;
/*  948 */     ResultSet resultSet = null;
/*  949 */     byte[] arrayOfByte = null;
/*  950 */     System.out.println(new Date() + ":popBlobTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/*  954 */       getConODS().setAutoCommit(false);
/*      */ 
/*      */       
/*  957 */       preparedStatement1 = getConODS().prepareStatement(
/*  958 */           getQueryStatement(16));
/*      */       
/*  960 */       preparedStatement2 = getConOPICM().prepareStatement(
/*  961 */           getQueryStatement(14));
/*      */ 
/*      */ 
/*      */       
/*  965 */       System.out.println(new Date() + ":popBlobTable:OPICM Blob Query Begins.");
/*  966 */       resultSet = preparedStatement2.executeQuery();
/*  967 */       System.out.println(new Date() + ":popBlobTable::OPICM Blob Query Ends");
/*      */       
/*  969 */       while (resultSet.next()) {
/*      */         try {
/*  971 */           preparedStatement1.clearParameters();
/*  972 */           preparedStatement1.setString(1, getMyunow());
/*  973 */           preparedStatement1.setString(2, resultSet.getString(1).trim());
/*  974 */           preparedStatement1.setInt(3, resultSet.getInt(2));
/*  975 */           preparedStatement1.setString(4, resultSet.getString(3).trim());
/*  976 */           preparedStatement1.setInt(5, resultSet.getInt(5));
/*  977 */           preparedStatement1.setString(6, resultSet.getString(6).trim());
/*  978 */           arrayOfByte = resultSet.getBytes(7);
/*  979 */           i = arrayOfByte.length;
/*  980 */           preparedStatement1.setBytes(7, arrayOfByte);
/*  981 */           preparedStatement1.executeUpdate();
/*      */           
/*  983 */           b1++;
/*  984 */           b3++;
/*  985 */           if (b3 == 'Ǵ') {
/*  986 */             System.out.println(new Date() + ":popBlobTable:commit on:" + b1);
/*  987 */             getConODS().commit();
/*  988 */             b3 = 1;
/*      */           } 
/*  990 */         } catch (SQLException sQLException) {
/*  991 */           b2++;
/*  992 */           System.err.println(new Date() + "popBlobTable:SQL Panic, Skipping insert.  :ET:" + resultSet
/*      */ 
/*      */ 
/*      */               
/*  996 */               .getString(1).trim() + ":EID:" + resultSet
/*      */               
/*  998 */               .getInt(2) + ":AC:" + resultSet
/*      */               
/* 1000 */               .getString(3).trim() + ":AT:" + resultSet
/*      */               
/* 1002 */               .getString(4).trim() + ":NLS:" + resultSet
/*      */               
/* 1004 */               .getInt(5) + ":BEX:" + resultSet
/*      */               
/* 1006 */               .getString(6).trim() + ":COUNT:" + b1 + ":" + sQLException
/*      */ 
/*      */ 
/*      */               
/* 1010 */               .getMessage());
/*      */         } 
/*      */       } 
/* 1013 */       getConODS().commit();
/* 1014 */       getConODS().setAutoCommit(true);
/*      */     }
/* 1016 */     catch (SQLException sQLException) {
/* 1017 */       System.err.println(new Date() + ":popBlobTable:" + sQLException.getMessage());
/* 1018 */       System.err.print(new Date() + ":popBlobTable:Error Occurred for :");
/*      */ 
/*      */       
/* 1021 */       System.err.println("     :BLOBSIZE:" + i);
/*      */       
/* 1023 */       System.err.println("     :COMMITTED: " + b1 + " RECORDS");
/*      */ 
/*      */ 
/*      */       
/* 1027 */       System.exit(1);
/*      */     } finally {
/*      */       try {
/* 1030 */         resultSet.close();
/* 1031 */         preparedStatement2.close();
/* 1032 */         preparedStatement1.close();
/*      */       }
/* 1034 */       catch (SQLException sQLException) {
/* 1035 */         sQLException.printStackTrace();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1040 */     System.out.println(new Date() + ":popBlobTable:Finished.  # of Recs Processed:" + b1 + "  # number of errors:" + b2);
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
/*      */   public void popMDTable() {
/* 1057 */     byte b1 = 0;
/* 1058 */     byte b2 = 0;
/* 1059 */     byte b3 = 1;
/* 1060 */     PreparedStatement preparedStatement1 = null;
/* 1061 */     PreparedStatement preparedStatement2 = null;
/* 1062 */     ResultSet resultSet = null;
/* 1063 */     System.out.println(new Date() + ":popMDTable:Start.");
/*      */ 
/*      */     
/*      */     try {
/* 1067 */       getConODS().setAutoCommit(false);
/*      */ 
/*      */       
/* 1070 */       preparedStatement1 = getConODS().prepareStatement(
/* 1071 */           getQueryStatement(20));
/*      */       
/* 1073 */       preparedStatement2 = getConOPICM().prepareStatement(
/* 1074 */           getQueryStatement(18));
/*      */ 
/*      */ 
/*      */       
/* 1078 */       System.out.println(new Date() + ":popMDTable:OPICM  MD Query Begins.");
/* 1079 */       resultSet = preparedStatement2.executeQuery();
/* 1080 */       System.out.println(new Date() + ":popMDTable::OPICM MD Query Ends.");
/*      */       
/* 1082 */       while (resultSet.next()) {
/*      */         
/*      */         try {
/* 1085 */           preparedStatement1.setString(1, getMyunow());
/* 1086 */           preparedStatement1.setString(2, resultSet.getString(2).trim());
/*      */           
/* 1088 */           preparedStatement1.setInt(3, resultSet.getInt(5));
/* 1089 */           preparedStatement1.setString(4, resultSet.getString(3).trim());
/*      */           
/* 1091 */           preparedStatement1.setString(5, resultSet.getString(4).trim());
/*      */           
/* 1093 */           preparedStatement1.setString(6, resultSet.getString(6).trim());
/*      */ 
/*      */           
/* 1096 */           preparedStatement1.executeUpdate();
/*      */           
/* 1098 */           b1++;
/* 1099 */           b3++;
/* 1100 */           if (b3 == 'ᎈ') {
/* 1101 */             System.out.println(new Date() + ":popMDTable:commit on:" + b1);
/* 1102 */             getConODS().commit();
/* 1103 */             b3 = 1;
/*      */           } 
/* 1105 */         } catch (SQLException sQLException) {
/* 1106 */           b2++;
/* 1107 */           System.err.println(new Date() + "popMDTable:SQL Panic, Skipping insert.  :DT: " + resultSet
/*      */ 
/*      */ 
/*      */               
/* 1111 */               .getString(2).trim() + ":DC: " + resultSet
/*      */               
/* 1113 */               .getString(3).trim() + ":LD: " + resultSet
/*      */               
/* 1115 */               .getString(4).trim() + ":SD: " + resultSet
/*      */               
/* 1117 */               .getString(6).trim() + ":NLS:" + resultSet
/*      */               
/* 1119 */               .getInt(5) + ":COUNT:" + b1 + ":" + sQLException
/*      */ 
/*      */ 
/*      */               
/* 1123 */               .getMessage());
/*      */         } 
/*      */       } 
/* 1126 */       getConODS().commit();
/* 1127 */       getConODS().setAutoCommit(true);
/*      */     }
/* 1129 */     catch (SQLException sQLException) {
/* 1130 */       System.err.println(new Date() + ":popMDTable:" + sQLException.getMessage());
/* 1131 */       System.exit(1);
/*      */     } finally {
/*      */       try {
/* 1134 */         resultSet.close();
/* 1135 */         preparedStatement2.close();
/* 1136 */         preparedStatement1.close();
/*      */       }
/* 1138 */       catch (SQLException sQLException) {
/* 1139 */         sQLException.printStackTrace();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1144 */     System.out.println(new Date() + ":popMDTable:Finished.  # of Recs Processed:" + b1 + "  # number of errors:" + b2);
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
/*      */   private String getQueryStatement(int paramInt) {
/* 1162 */     if (paramInt == 2)
/*      */     {
/* 1164 */       return "DROP TABLE " + getSchODS() + ".RELATOR";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1170 */     if (paramInt == 6)
/*      */     {
/* 1172 */       return "DROP TABLE " + getSchODS() + ".FLAG";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1178 */     if (paramInt == 9)
/*      */     {
/* 1180 */       return "DROP TABLE " + getSchODS() + ".LONGTEXT";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1186 */     if (paramInt == 13)
/*      */     {
/* 1188 */       return "DROP TABLE " + getSchODS() + ".BLOB";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1194 */     if (paramInt == 17)
/*      */     {
/* 1196 */       return "DROP TABLE " + getSchODS() + ".METAFLAGVALUE";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1202 */     if (paramInt == 3)
/*      */     {
/* 1204 */       return "CREATE TABLE " + 
/* 1205 */         getSchODS() + ".RELATOR (ENTITYTYPE CHAR(32) NOT NULL, ENTITYID INTEGER NOT NULL, ENTITY1TYPE CHAR (32) NOT NULL, ENTITY1ID INTEGER NOT NULL, ENTITY2TYPE CHAR (32) NOT NULL, ENTITY2ID INTEGER NOT NULL, VALFROM TIMESTAMP NOT NULL, PRIMARY KEY (ENTITYID,ENTITYTYPE)) ";
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
/* 1222 */     if (paramInt == 7)
/*      */     {
/* 1224 */       return "CREATE TABLE " + 
/* 1225 */         getSchODS() + ".FLAG (VALFROM TIMESTAMP NOT NULL, ENTITYTYPE CHAR(32) NOT NULL, ENTITYID INTEGER NOT NULL, ATTRIBUTECODE CHAR(32) NOT NULL, ATTRIBUTEVALUE CHAR(32) NOT NULL )";
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
/* 1240 */     if (paramInt == 11)
/*      */     {
/* 1242 */       return "CREATE TABLE " + 
/* 1243 */         getSchODS() + ".LONGTEXT (VALFROM TIMESTAMP NOT NULL, ENTITYTYPE CHAR(32) NOT NULL, ENTITYID INTEGER NOT NULL, ATTRIBUTECODE CHAR(32) NOT NULL, ATTRIBUTETYPE CHAR(8) NOT NULL, NLSID INT NOT NULL, ATTRIBUTEVALUE LONG VARCHAR, PRIMARY KEY (ENTITYID,ENTITYTYPE,ATTRIBUTECODE,NLSID)) ";
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
/* 1260 */     if (paramInt == 19)
/*      */     {
/* 1262 */       return "CREATE TABLE " + 
/* 1263 */         getSchODS() + ".METAFLAGVALUE (VALFROM TIMESTAMP NOT NULL, ATTRIBUTECODE CHAR(32) NOT NULL, NLSID INT NOT NULL, ATTRIBUTEVALUE CHAR(32) NOT NULL, SHORTDESCRIPTION CHAR(64), LONGDESCRIPTION CHAR(128), PRIMARY KEY (ATTRIBUTECODE,ATTRIBUTEVALUE,NLSID)) ";
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
/* 1280 */     if (paramInt == 15)
/*      */     {
/* 1282 */       return "CREATE TABLE " + 
/* 1283 */         getSchODS() + ".BLOB  (VALFROM TIMESTAMP NOT NULL, ENTITYTYPE CHAR(32) NOT NULL, ENTITYID INTEGER NOT NULL, ATTRIBUTECODE CHAR(32) NOT NULL, NLSID INT NOT NULL, BLOBEXTENSION CHAR(32) NOT NULL,ATTRIBUTEVALUE BLOB(10M) NOT NULL, PRIMARY KEY (ENTITYTYPE,ENTITYID,ATTRIBUTECODE,NLSID)) ";
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
/* 1300 */     if (paramInt == 4)
/*      */     {
/* 1302 */       return "INSERT INTO " + 
/* 1303 */         getSchODS() + ".RELATOR  ( VALFROM, ENTITYTYPE, ENTITYID, ENTITY1TYPE, ENTITY1ID, ENTITY2TYPE, ENTITY2ID   ) VALUES (?,?,?,?,?,?,?)";
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
/* 1319 */     if (paramInt == 8)
/*      */     {
/* 1321 */       return "INSERT INTO " + 
/* 1322 */         getSchODS() + ".FLAG  ( VALFROM, ENTITYTYPE, ENTITYID, ATTRIBUTECODE, ATTRIBUTEVALUE   ) VALUES (?,?,?,?,?)";
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
/* 1336 */     if (paramInt == 12)
/*      */     {
/* 1338 */       return "INSERT INTO " + 
/* 1339 */         getSchODS() + ".LONGTEXT  ( VALFROM, ENTITYTYPE, ENTITYID, ATTRIBUTECODE, ATTRIBUTETYPE, NLSID, ATTRIBUTEVALUE   ) VALUES (?,?,?,?,?,?,?)";
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
/* 1355 */     if (paramInt == 16)
/*      */     {
/* 1357 */       return "INSERT INTO " + 
/* 1358 */         getSchODS() + ".BLOB  ( VALFROM, ENTITYTYPE, ENTITYID, ATTRIBUTECODE, NLSID, BLOBEXTENSION, ATTRIBUTEVALUE   ) VALUES (?,?,?,?,?,?,?)";
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
/* 1373 */     if (paramInt == 20) {
/* 1374 */       return "INSERT INTO " + 
/* 1375 */         getSchODS() + ".METAFLAGVALUE  ( VALFROM, ATTRIBUTECODE, NLSID, ATTRIBUTEVALUE, LONGDESCRIPTION, SHORTDESCRIPTION   ) VALUES (?,?,?,?,?,?)";
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
/* 1391 */     if (paramInt == getOPICM_SCAN_RELATOR_TABLE_QRY())
/*      */     {
/* 1393 */       return "SELECT  EntityType,    EntityID,      Entity1Type,   Entity1ID,     Entity2Type,   Entity2ID      FROM " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1401 */         getSchOPICM() + ".RELATOR WHERE Enterprise = '" + 
/*      */ 
/*      */ 
/*      */         
/* 1405 */         getEnterprise() + "' AND  NOT (EffFrom = EffTo)  AND ValFrom <= '" + 
/*      */ 
/*      */ 
/*      */         
/* 1409 */         getMynow() + "' AND '" + 
/*      */         
/* 1411 */         getMynow() + "' < ValTo AND EffFrom <= '" + 
/*      */ 
/*      */         
/* 1414 */         getMynow() + "' AND '" + 
/*      */         
/* 1416 */         getMynow() + "' < EffTo  FOR READ ONLY";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1423 */     if (paramInt == 5)
/*      */     {
/* 1425 */       return "SELECT F.ENTITYTYPE, F.ENTITYID, F.ATTRIBUTECODE,F.ATTRIBUTEVALUE FROM " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1431 */         getSchOPICM() + ".FLAG F INNER JOIN " + 
/*      */ 
/*      */         
/* 1434 */         getSchOPICM() + ".metaentity MA ON MA.ENTERPRISE = F.ENTERPRISE AND MA.ENTITYTYPE = F.ATTRIBUTECODE AND MA.ENTITYCLASS = 'F' AND MA.ValFrom <= '" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1440 */         getMynow() + "' AND '" + 
/*      */         
/* 1442 */         getMynow() + "' < MA.ValTo AND MA.EffFrom <= '" + 
/*      */ 
/*      */         
/* 1445 */         getMynow() + "' AND '" + 
/*      */         
/* 1447 */         getMynow() + "' < MA.EffTo  WHERE F.ENTERPRISE = '" + 
/*      */ 
/*      */ 
/*      */         
/* 1451 */         getEnterprise() + "' AND  NOT (F.EffFrom = F.EffTo)  AND F.ValFrom <= '" + 
/*      */ 
/*      */ 
/*      */         
/* 1455 */         getMynow() + "' AND '" + 
/*      */         
/* 1457 */         getMynow() + "' < F.ValTo AND F.EffFrom <= '" + 
/*      */ 
/*      */         
/* 1460 */         getMynow() + "' AND '" + 
/*      */         
/* 1462 */         getMynow() + "' < F.EffTo   UNION SELECT F.ENTITYTYPE, F.ENTITYID, F.ATTRIBUTECODE,F.ATTRIBUTEVALUE FROM " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1471 */         getSchOPICM() + ".FLAG F INNER JOIN " + 
/*      */ 
/*      */         
/* 1474 */         getSchOPICM() + ".metaentity MA ON MA.ENTERPRISE = F.ENTERPRISE AND MA.ENTITYTYPE = F.ATTRIBUTECODE AND MA.ENTITYCLASS IN ('U','S') AND MA.ValFrom <= '" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1480 */         getMynow() + "' AND '" + 
/*      */         
/* 1482 */         getMynow() + "' < MA.ValTo AND MA.EffFrom <= '" + 
/*      */ 
/*      */         
/* 1485 */         getMynow() + "' AND '" + 
/*      */         
/* 1487 */         getMynow() + "' < MA.EffTo INNER JOIN " + 
/*      */ 
/*      */         
/* 1490 */         getSchOPICM() + ".METALINKATTR MLA   ON   MLA.ENTERPRISE =  F.ENTERPRISE  AND MA.ENTITYTYPE=MLA.LINKTYPE2  AND MLA.LINKCODE='EntityAttribute'  AND MLA.LINKTYPE='Entity/Attribute'   AND F.ENTITYTYPE=MLA.LINKTYPE1  AND MLA.LINKVALUE='A'                 AND MLA.ValFrom <= '" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1499 */         getMynow() + "' AND '" + 
/*      */         
/* 1501 */         getMynow() + "' < MLA.ValTo AND MLA.EffFrom <= '" + 
/*      */ 
/*      */         
/* 1504 */         getMynow() + "' AND '" + 
/*      */         
/* 1506 */         getMynow() + "' < MLA.EffTo  WHERE F.ENTERPRISE = '" + 
/*      */ 
/*      */ 
/*      */         
/* 1510 */         getEnterprise() + "' AND  NOT (F.EffFrom = F.EffTo)  AND F.ValFrom <= '" + 
/*      */ 
/*      */ 
/*      */         
/* 1514 */         getMynow() + "' AND '" + 
/*      */         
/* 1516 */         getMynow() + "' < F.ValTo AND F.EffFrom <= '" + 
/*      */ 
/*      */         
/* 1519 */         getMynow() + "' AND '" + 
/*      */         
/* 1521 */         getMynow() + "' < F.EffTo   FOR READ ONLY";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1529 */     if (paramInt == 10)
/*      */     {
/* 1531 */       return "SELECT LT.ENTITYTYPE, LT.ENTITYID, LT.ATTRIBUTECODE,MA.ENTITYCLASS AS ATTRIBUTETYPE,LT.NLSID, LT.ATTRIBUTEVALUE FROM " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1539 */         getSchOPICM() + ".LONGTEXT LT INNER JOIN " + 
/*      */ 
/*      */         
/* 1542 */         getSchOPICM() + ".metaentity MA ON MA.ENTERPRISE = LT.ENTERPRISE AND MA.ENTITYTYPE = LT.ATTRIBUTECODE AND MA.ENTITYCLASS in ('L','X') AND MA.ValFrom <= '" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1548 */         getMynow() + "' AND '" + 
/*      */         
/* 1550 */         getMynow() + "' < MA.ValTo AND  NOT (MA.EffFrom = MA.EffTo)  AND MA.EffFrom <= '" + 
/*      */ 
/*      */ 
/*      */         
/* 1554 */         getMynow() + "' AND '" + 
/*      */         
/* 1556 */         getMynow() + "' < MA.EffTo  WHERE LT.ENTERPRISE = '" + 
/*      */ 
/*      */ 
/*      */         
/* 1560 */         getEnterprise() + "' AND LT.ValFrom <= '" + 
/*      */ 
/*      */         
/* 1563 */         getMynow() + "' AND '" + 
/*      */         
/* 1565 */         getMynow() + "' < LT.ValTo AND  NOT (LT.EffFrom = LT.EffTo)  AND LT.EffFrom <= '" + 
/*      */ 
/*      */ 
/*      */         
/* 1569 */         getMynow() + "' AND '" + 
/*      */         
/* 1571 */         getMynow() + "' < LT.EffTo   FOR READ ONLY";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1580 */     if (paramInt == 14)
/*      */     {
/* 1582 */       return "SELECT B.ENTITYTYPE, B.ENTITYID, B.ATTRIBUTECODE AS ATTRIBUTECODE,MA.ENTITYCLASS AS ATTRIBUTETYPE,B.NLSID, B.BLOBEXTENSION, B.ATTRIBUTEVALUE FROM " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1591 */         getSchOPICM() + ".BLOB B INNER JOIN " + 
/*      */ 
/*      */         
/* 1594 */         getSchOPICM() + ".metaentity MA ON MA.ENTERPRISE = B.ENTERPRISE AND MA.ENTITYTYPE = B.ATTRIBUTECODE AND MA.ENTITYCLASS IN ('P','D','B') AND MA.ValFrom <= '" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1600 */         getMynow() + "' AND '" + 
/*      */         
/* 1602 */         getMynow() + "' < MA.ValTo AND  NOT (MA.EffFrom = MA.EffTo)  AND MA.EffFrom <= '" + 
/*      */ 
/*      */ 
/*      */         
/* 1606 */         getMynow() + "' AND '" + 
/*      */         
/* 1608 */         getMynow() + "' < MA.EffTo  WHERE B.ENTERPRISE = '" + 
/*      */ 
/*      */ 
/*      */         
/* 1612 */         getEnterprise() + "' AND B.ValFrom <= '" + 
/*      */ 
/*      */         
/* 1615 */         getMynow() + "' AND '" + 
/*      */         
/* 1617 */         getMynow() + "' < B.ValTo AND  NOT (B.EffFrom = B.EffTo)  AND B.EffFrom <= '" + 
/*      */ 
/*      */ 
/*      */         
/* 1621 */         getMynow() + "' AND '" + 
/*      */         
/* 1623 */         getMynow() + "' < B.EffTo   FOR READ ONLY";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1631 */     if (paramInt == 18)
/*      */     {
/* 1633 */       return "SELECT    VALFROM,    DESCRIPTIONTYPE,    DESCRIPTIONCLASS,    LONGDESCRIPTION,     NLSID,    SHORTDESCRIPTION FROM    " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1642 */         getSchOPICM() + ".METADESCRIPTION  WHERE    ENTERPRISE = '" + 
/*      */ 
/*      */ 
/*      */         
/* 1646 */         getEnterprise() + "' AND    ValFrom <= '" + 
/*      */ 
/*      */         
/* 1649 */         getMynow() + "'AND     '" + 
/*      */ 
/*      */         
/* 1652 */         getMynow() + "' < VALTO     AND    EffFrom <= '" + 
/*      */ 
/*      */         
/* 1655 */         getMynow() + "'  AND     '" + 
/*      */ 
/*      */         
/* 1658 */         getMynow() + "' < EFFTO      FOR READ ONLY ";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1667 */     return "ERROR";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void setUidOPICM(String paramString) {
/* 1673 */     this.uidOPICM = paramString;
/*      */   }
/*      */   
/*      */   String getUidOPICM() {
/* 1677 */     return this.uidOPICM;
/*      */   }
/*      */   
/*      */   void setPwdOPICM(String paramString) {
/* 1681 */     this.pwdOPICM = paramString;
/*      */   }
/*      */   
/*      */   String getPwdOPICM() {
/* 1685 */     return this.pwdOPICM;
/*      */   }
/*      */   
/*      */   void setDsnOPICM(String paramString) {
/* 1689 */     this.dsnOPICM = paramString;
/*      */   }
/*      */   
/*      */   String getDsnOPICM() {
/* 1693 */     return this.dsnOPICM;
/*      */   }
/*      */   
/*      */   void setSchOPICM(String paramString) {
/* 1697 */     this.schOPICM = paramString;
/*      */   }
/*      */   
/*      */   String getSchOPICM() {
/* 1701 */     return this.schOPICM;
/*      */   }
/*      */   
/*      */   void setUrlOPICM(String paramString) {
/* 1705 */     this.urlOPICM = paramString;
/*      */   }
/*      */   
/*      */   String getUrlOPICM() {
/* 1709 */     return this.urlOPICM;
/*      */   }
/*      */   
/*      */   void setUidODS(String paramString) {
/* 1713 */     this.uidODS = paramString;
/*      */   }
/*      */   
/*      */   String getUidODS() {
/* 1717 */     return this.uidODS;
/*      */   }
/*      */   
/*      */   void setPwdODS(String paramString) {
/* 1721 */     this.pwdODS = paramString;
/*      */   }
/*      */   
/*      */   String getPwdODS() {
/* 1725 */     return this.pwdODS;
/*      */   }
/*      */   
/*      */   void setDsnODS(String paramString) {
/* 1729 */     this.dsnODS = paramString;
/*      */   }
/*      */   
/*      */   String getDsnODS() {
/* 1733 */     return this.dsnODS;
/*      */   }
/*      */   
/*      */   void setSchODS(String paramString) {
/* 1737 */     this.schODS = paramString;
/*      */   }
/*      */   
/*      */   String getSchODS() {
/* 1741 */     return this.schODS;
/*      */   }
/*      */   
/*      */   void setUrlODS(String paramString) {
/* 1745 */     this.urlODS = paramString;
/*      */   }
/*      */   
/*      */   String getUrlODS() {
/* 1749 */     return this.urlODS;
/*      */   }
/*      */   
/*      */   void setEnterprise(String paramString) {
/* 1753 */     this.enterprise = paramString;
/*      */   }
/*      */   
/*      */   String getEnterprise() {
/* 1757 */     return this.enterprise;
/*      */   }
/*      */   
/*      */   void setCmd(String paramString) {
/* 1761 */     this.cmd = paramString;
/*      */   }
/*      */   
/*      */   String getCmd() {
/* 1765 */     return this.cmd;
/*      */   }
/*      */   
/*      */   void setConODS(Connection paramConnection) {
/* 1769 */     this.conODS = paramConnection;
/*      */   }
/*      */   
/*      */   Connection getConODS() {
/* 1773 */     return this.conODS;
/*      */   }
/*      */   
/*      */   void setConOPICM(Connection paramConnection) {
/* 1777 */     this.conOPICM = paramConnection;
/*      */   }
/*      */   
/*      */   Connection getConOPICM() {
/* 1781 */     return this.conOPICM;
/*      */   }
/*      */   
/*      */   void setOPICM_TABLE_BUILDER_QRY(int paramInt) {
/* 1785 */     this.OPICM_TABLE_BUILDER_QRY = paramInt;
/*      */   }
/*      */   
/*      */   int getOPICM_TABLE_BUILDER_QRY() {
/* 1789 */     return this.OPICM_TABLE_BUILDER_QRY;
/*      */   }
/*      */   
/*      */   void setOPICM_SCAN_RELATOR_TABLE_QRY(int paramInt) {
/* 1793 */     this.OPICM_SCAN_RELATOR_TABLE_QRY = paramInt;
/*      */   }
/*      */   
/*      */   int getOPICM_SCAN_RELATOR_TABLE_QRY() {
/* 1797 */     return this.OPICM_SCAN_RELATOR_TABLE_QRY;
/*      */   }
/*      */   
/*      */   void setFormatTS(SimpleDateFormat paramSimpleDateFormat) {
/* 1801 */     this.formatTS = paramSimpleDateFormat;
/*      */   }
/*      */   
/*      */   SimpleDateFormat getFormatTS() {
/* 1805 */     return this.formatTS;
/*      */   }
/*      */   
/*      */   void setTodaystr(String paramString) {
/* 1809 */     this.todaystr = paramString;
/*      */   }
/*      */   
/*      */   String getTodaystr() {
/* 1813 */     return this.todaystr;
/*      */   }
/*      */   
/*      */   void setMyunow(String paramString) {
/* 1817 */     this.myunow = paramString;
/*      */   }
/*      */   
/*      */   String getMyunow() {
/* 1821 */     return this.myunow;
/*      */   }
/*      */   
/*      */   void setMynow(String paramString) {
/* 1825 */     this.mynow = paramString;
/*      */   }
/*      */   
/*      */   String getMynow() {
/* 1829 */     return this.mynow;
/*      */   }
/*      */   
/*      */   void setForever(String paramString) {
/* 1833 */     this.forever = paramString;
/*      */   }
/*      */   
/*      */   String getForever() {
/* 1837 */     return this.forever;
/*      */   }
/*      */   
/*      */   void setEpic(String paramString) {
/* 1841 */     this.epic = paramString;
/*      */   }
/*      */   
/*      */   String getEpic() {
/* 1845 */     return this.epic;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\ODSInitV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */