/*      */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*      */ 
/*      */ import com.ibm.eacm.AES256Utils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Date;
/*      */ import java.util.Properties;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WWCOMPATIDLABR
/*      */ {
/*   61 */   private static Properties c_props = null;
/*   62 */   private static Connection conOPICM = null;
/*      */   
/*   64 */   private static Properties WWCOMPAT_props = null;
/*      */   
/*      */   private static String OSIndependent;
/*      */   
/*      */   private static String WWTable;
/*      */   private static int Db2Version;
/*      */   
/*      */   public static void main(String[] paramArrayOfString) {
/*   72 */     System.out.println("-----------------------------------------------------------");
/*   73 */     Date date = new Date();
/*   74 */     long l1 = System.currentTimeMillis();
/*   75 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*   76 */     String str1 = simpleDateFormat.format(date);
/*   77 */     readPropertyFile();
/*   78 */     initWWCOMPAT();
/*   79 */     WWCOMPATIDLABR wWCOMPATIDLABR = new WWCOMPATIDLABR();
/*      */     
/*   81 */     wWCOMPATIDLABR.getConnect();
/*   82 */     wWCOMPATIDLABR.mainline();
/*      */     try {
/*   84 */       wWCOMPATIDLABR.closeConnection();
/*   85 */     } catch (SQLException sQLException) {
/*   86 */       sQLException.printStackTrace();
/*   87 */       System.exit(1);
/*      */     } 
/*      */     
/*   90 */     System.out.println("Success!!");
/*   91 */     System.out.println("-----------------------------------------------------------");
/*   92 */     date = new Date();
/*   93 */     long l2 = System.currentTimeMillis();
/*   94 */     String str2 = simpleDateFormat.format(date);
/*   95 */     System.out.println("version 2");
/*   96 */     System.out.println("Start Time= " + str1);
/*   97 */     System.out.println("End   Time= " + str2);
/*   98 */     System.out.println("Total Time= " + (l2 - l1) + " milliseconds");
/*   99 */     System.out.println("-----------------------------------------------------------");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readPropertyFile() {
/*  106 */     String str1 = System.getProperty("user.dir") + "/middleware.server.properties";
/*  107 */     String str2 = System.getProperty("user.dir") + "/wwcompat.properties";
/*  108 */     BufferedInputStream bufferedInputStream = null;
/*      */     try {
/*      */       try {
/*  111 */         bufferedInputStream = new BufferedInputStream(new FileInputStream(str1));
/*  112 */         c_props = new Properties();
/*  113 */         c_props.load(bufferedInputStream);
/*      */         
/*  115 */         bufferedInputStream = new BufferedInputStream(new FileInputStream(str2));
/*  116 */         WWCOMPAT_props = new Properties();
/*  117 */         WWCOMPAT_props.load(bufferedInputStream);
/*      */       } finally {
/*      */         
/*  120 */         bufferedInputStream.close();
/*      */       }
/*      */     
/*  123 */     } catch (IOException iOException) {
/*  124 */       System.out.println("Unable to loadProperties for " + str1 + " " + iOException);
/*  125 */       System.exit(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void initWWCOMPAT() {
/*  132 */     OSIndependent = WWCOMPAT_props.getProperty("OSIndependent");
/*  133 */     System.out.println("OSIndependent=" + OSIndependent);
/*  134 */     WWTable = WWCOMPAT_props.getProperty("WWTable");
/*  135 */     Db2Version = Integer.parseInt(WWCOMPAT_props.getProperty("Db2Version"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void getConnect() {
/*      */     try {
/*  142 */       Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
/*      */       
/*  144 */       String str1 = c_props.getProperty("pdh_database_url");
/*  145 */       String str2 = c_props.getProperty("pdh_database_user");
/*  146 */       String str3 = AES256Utils.decrypt(c_props.getProperty("pdh_database_password"));
/*      */       
/*  148 */       setConOPICM(DriverManager.getConnection(str1, str2, str3));
/*  149 */       getConOPICM().setAutoCommit(true);
/*  150 */     } catch (Exception exception) {
/*  151 */       System.out.println(new Date() + ":connection:err: " + exception.getMessage());
/*  152 */       System.exit(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mainline() {
/*  160 */     System.out.println("Initializing for WWCOMPATIDLABR version2");
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  165 */       clearTempTable();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  170 */       createTempTable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  176 */       insertDataIntoTempTable();
/*      */ 
/*      */ 
/*      */       
/*  180 */       processData();
/*      */ 
/*      */ 
/*      */       
/*  184 */       dropTempIndexAndTable();
/*      */     }
/*  186 */     catch (SQLException sQLException) {
/*  187 */       System.out.println(new Date() + ":general-error" + sQLException.getMessage());
/*  188 */       sQLException.printStackTrace();
/*      */       try {
/*  190 */         dropTempTable();
/*  191 */       } catch (SQLException sQLException1) {
/*  192 */         sQLException1.printStackTrace();
/*      */       } 
/*  194 */       System.exit(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dropTempIndexAndTable() throws SQLException {
/*  202 */     Statement statement = conOPICM.createStatement();
/*  203 */     String str = "";
/*  204 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  209 */     str = " drop index \"GBLI    \".\"SYSTEM_INDEX\"";
/*  210 */     i = statement.executeUpdate(str);
/*  211 */     if (i < 0) {
/*  212 */       System.out.println("drop SYSTEM_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  214 */       System.out.println("drop SYSTEM_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  217 */     str = " drop index \"GBLI    \".\"GROUP_INDEX\"";
/*  218 */     i = statement.executeUpdate(str);
/*  219 */     if (i < 0) {
/*  220 */       System.out.println("drop GROUP_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  222 */       System.out.println("drop GROUP_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  225 */     str = " drop index \"GBLI    \".\"OSOP_INDEX\"";
/*  226 */     i = statement.executeUpdate(str);
/*  227 */     if (i < 0) {
/*  228 */       System.out.println("drop OSOP_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  230 */       System.out.println("drop OSOP_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  233 */     str = " drop index \"GBLI    \".\"OS_INDEX\"";
/*  234 */     i = statement.executeUpdate(str);
/*  235 */     if (i < 0) {
/*  236 */       System.out.println("drop OS_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  238 */       System.out.println("drop OS_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  241 */     str = " drop index \"GBLI    \".\"OPTION_INDEX\"";
/*  242 */     i = statement.executeUpdate(str);
/*  243 */     if (i < 0) {
/*  244 */       System.out.println("drop OPTION_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  246 */       System.out.println("drop OPTION_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  253 */     str = " drop table \"GBLI    \".\"WWCOMPATSYSTEM\"";
/*  254 */     i = statement.executeUpdate(str);
/*  255 */     if (i < 0) {
/*  256 */       System.out.println("drop WWCOMPATSYSTEM temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  258 */       System.out.println("drop WWCOMPATSYSTEM temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  261 */     str = " drop table \"GBLI    \".\"WWCOMPATGROUP\"";
/*  262 */     i = statement.executeUpdate(str);
/*  263 */     if (i < 0) {
/*  264 */       System.out.println("drop WWCOMPATGROUP temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  266 */       System.out.println("drop WWCOMPATGROUP temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  269 */     str = " drop table \"GBLI    \".\"WWCOMPATOSOP\"";
/*  270 */     i = statement.executeUpdate(str);
/*  271 */     if (i < 0) {
/*  272 */       System.out.println("drop WWCOMPATOSOP temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  274 */       System.out.println("drop WWCOMPATOSOP temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  277 */     str = " drop table \"GBLI    \".\"WWCOMPATOS\"";
/*  278 */     i = statement.executeUpdate(str);
/*  279 */     if (i < 0) {
/*  280 */       System.out.println("drop WWCOMPATOS temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  282 */       System.out.println("drop WWCOMPATOS temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  285 */     str = " drop table \"GBLI    \".\"WWCOMPATOPTION\"";
/*  286 */     i = statement.executeUpdate(str);
/*  287 */     if (i < 0) {
/*  288 */       System.out.println("drop WWCOMPATOPTION temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  290 */       System.out.println("drop WWCOMPATOPTION temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dropTempTable() throws SQLException {
/*  299 */     Statement statement = conOPICM.createStatement();
/*  300 */     String str = "";
/*  301 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  307 */     str = " drop table \"GBLI    \".\"WWCOMPATSYSTEM\"";
/*  308 */     i = statement.executeUpdate(str);
/*  309 */     if (i < 0) {
/*  310 */       System.out.println("drop WWCOMPATSYSTEM temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  312 */       System.out.println("drop WWCOMPATSYSTEM temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  315 */     str = " drop table \"GBLI    \".\"WWCOMPATGROUP\"";
/*  316 */     i = statement.executeUpdate(str);
/*  317 */     if (i < 0) {
/*  318 */       System.out.println("drop WWCOMPATGROUP temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  320 */       System.out.println("drop WWCOMPATGROUP temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  323 */     str = " drop table \"GBLI    \".\"WWCOMPATOSOP\"";
/*  324 */     i = statement.executeUpdate(str);
/*  325 */     if (i < 0) {
/*  326 */       System.out.println("drop WWCOMPATOSOP temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  328 */       System.out.println("drop WWCOMPATOSOP temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  331 */     str = " drop table \"GBLI    \".\"WWCOMPATOS\"";
/*  332 */     i = statement.executeUpdate(str);
/*  333 */     if (i < 0) {
/*  334 */       System.out.println("drop WWCOMPATOS temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  336 */       System.out.println("drop WWCOMPATOS temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  339 */     str = " drop table \"GBLI    \".\"WWCOMPATOPTION\"";
/*  340 */     i = statement.executeUpdate(str);
/*  341 */     if (i < 0) {
/*  342 */       System.out.println("drop WWCOMPATOPTION temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  344 */       System.out.println("drop WWCOMPATOPTION temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insertDataIntoTempTable() throws SQLException {
/*  352 */     Statement statement = conOPICM.createStatement();
/*  353 */     int i = 0;
/*      */     
/*  355 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL);
/*  356 */     if (i < 0) {
/*  357 */       System.out.println("insert data into WWCOMPATSYSTEM table error!!    \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL);
/*      */     } else {
/*      */       
/*  360 */       System.out.println("insert data into WWCOMPATSYSTEM table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL);
/*      */     } 
/*      */ 
/*      */     
/*  364 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATGROUP_SQL);
/*  365 */     if (i < 0) {
/*  366 */       System.out.println("insert data into WWCOMPATGROUP table error!!    \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATGROUP_SQL);
/*      */     } else {
/*      */       
/*  369 */       System.out.println("insert data into WWCOMPATGROUP table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATGROUP_SQL);
/*      */     } 
/*      */ 
/*      */     
/*  373 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATOS_SQL);
/*  374 */     if (i < 0) {
/*  375 */       System.out.println("insert data into WWCOMPATOS table error!!  \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOS_SQL);
/*      */     } else {
/*      */       
/*  378 */       System.out.println("insert data into WWCOMPATOS table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOS_SQL);
/*      */     } 
/*      */ 
/*      */     
/*  382 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATOSOP_SQL);
/*  383 */     if (i < 0) {
/*  384 */       System.out.println("insert data into WWCOMPATOSOP table error!!  \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOSOP_SQL);
/*      */     } else {
/*      */       
/*  387 */       System.out.println("insert data into WWCOMPATOSOP table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOSOP_SQL);
/*      */     } 
/*      */ 
/*      */     
/*  391 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATOPTION_SQL);
/*  392 */     if (i < 0) {
/*  393 */       System.out.println("insert data into WWCOMPATSYSTEM table error!!  \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOPTION_SQL);
/*      */     } else {
/*      */       
/*  396 */       System.out.println("insert data into WWCOMPATSYSTEM table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOPTION_SQL);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  404 */     String str = " CREATE INDEX \"GBLI    \".\"SYSTEM_INDEX\" ON \"GBLI    \".\"WWCOMPATSYSTEM\" \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"OSLEVEL\" ASC)                                                      \r\n";
/*      */ 
/*      */ 
/*      */     
/*  408 */     i = statement.executeUpdate(str);
/*  409 */     if (i < 0) {
/*  410 */       System.out.println("Create SYSTEM_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  412 */       System.out.println("Create SYSTEM_INDEX temp Index success !! \r\n create sql =" + str);
/*      */     } 
/*      */ 
/*      */     
/*  416 */     str = " CREATE INDEX \"GBLI    \".\"GROUP_INDEX\" ON \"GBLI    \".\"WWCOMPATGROUP\"   \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"OKTOPUB\" ASC)                                                      \r\n";
/*      */ 
/*      */ 
/*      */     
/*  420 */     i = statement.executeUpdate(str);
/*  421 */     if (i < 0) {
/*  422 */       System.out.println("Create GROUP_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  424 */       System.out.println("Create GROUP_INDEX temp Index success !! \r\n create sql =" + str);
/*      */     } 
/*      */ 
/*      */     
/*  428 */     str = " CREATE INDEX \"GBLI    \".\"OSOP_INDEX\" ON \"GBLI    \".\"WWCOMPATOSOP\"     \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"COMPATPUBFLG\" ASC)                                                 \r\n";
/*      */ 
/*      */ 
/*      */     
/*  432 */     i = statement.executeUpdate(str);
/*  433 */     if (i < 0) {
/*  434 */       System.out.println("Create OSOP_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  436 */       System.out.println("Create OSOP_INDEX temp Index success !! \r\n create sql =" + str);
/*      */     } 
/*      */ 
/*      */     
/*  440 */     str = " CREATE INDEX \"GBLI    \".\"OS_INDEX\" ON \"GBLI    \".\"WWCOMPATOS\"         \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"OS\" ASC)                                                           \r\n";
/*      */ 
/*      */ 
/*      */     
/*  444 */     i = statement.executeUpdate(str);
/*  445 */     if (i < 0) {
/*  446 */       System.out.println("Create OS_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  448 */       System.out.println("Create OS_INDEX temp Index success !! \r\n create sql =" + str);
/*      */     } 
/*      */ 
/*      */     
/*  452 */     str = " CREATE INDEX \"GBLI    \".\"OPTION_INDEX\" ON \"GBLI    \".\"WWCOMPATOPTION\" \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"OSLEVEL\" ASC)                                                      \r\n";
/*      */ 
/*      */ 
/*      */     
/*  456 */     i = statement.executeUpdate(str);
/*  457 */     if (i < 0) {
/*  458 */       System.out.println("Create OPTION_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  460 */       System.out.println("Create OPTION_INDEX temp Index success !! \r\n create sql =" + str);
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
/*  475 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL);
/*  476 */     if (i < 0) {
/*  477 */       System.out.println("insert data into GBLI_WWCOMPATTEMP table for SEOCG error!!  \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL);
/*      */     } else {
/*      */       
/*  480 */       System.out.println("insert data into GBLI_WWCOMPATTEMP table for SEOCG success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createTempTable() throws SQLException {
/*  490 */     Statement statement = conOPICM.createStatement();
/*  491 */     String str = "";
/*  492 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  497 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATSYSTEM\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) , \r\n  \"ENTITYID\" INTEGER , \r\n  \"OSLEVEL\" VARCHAR(32) \r\n  ) IN \"TSPACE08\"   \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  502 */     i = statement.executeUpdate(str);
/*  503 */     if (i < 0) {
/*  504 */       System.out.println("Create WWCOMPATSYSTEM temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*      */       
/*  507 */       System.out.println("Create WWCOMPATSYSTEM temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  510 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATGROUP\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) ,                  \r\n  \"ENTITYID\" INTEGER ,                        \r\n  \"OKTOPUB\" VARCHAR(32),                      \r\n  \"BRANDCD\" VARCHAR(32)                       \r\n  ) IN \"TSPACE08\"                             \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  516 */     i = statement.executeUpdate(str);
/*  517 */     if (i < 0) {
/*  518 */       System.out.println("Create WWCOMPATGROUP temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*  520 */       System.out.println("Create WWCOMPATGROUP temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  523 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATOSOP\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) ,                 \r\n  \"ENTITYID\" INTEGER ,                       \r\n  \"COMPATPUBFLG\" VARCHAR(32),                \r\n  \"PUBFROM\" VARCHAR(32),                     \r\n  \"PUBTO\" VARCHAR(32),                       \r\n  \"RELTYPE\" VARCHAR(32)                      \r\n  ) IN \"TSPACE08\"                            \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  531 */     i = statement.executeUpdate(str);
/*  532 */     if (i < 0) {
/*  533 */       System.out.println("Create WWCOMPATOSOP temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*  535 */       System.out.println("Create WWCOMPATOSOP temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  538 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATOS\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) ,               \r\n  \"ENTITYID\" INTEGER ,                     \r\n  \"OS\" VARCHAR(32)                         \r\n  ) IN \"TSPACE08\"                          \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  543 */     i = statement.executeUpdate(str);
/*  544 */     if (i < 0) {
/*  545 */       System.out.println("Create WWCOMPATOS temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*  547 */       System.out.println("Create WWCOMPATOS temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  550 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATOPTION\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) ,                   \r\n  \"ENTITYID\" INTEGER ,                         \r\n  \"OSLEVEL\" VARCHAR(32)                        \r\n  ) IN \"TSPACE08\"                              \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  555 */     i = statement.executeUpdate(str);
/*  556 */     if (i < 0) {
/*  557 */       System.out.println("Create WWCOMPATOPTION temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*  559 */       System.out.println("Create WWCOMPATOPTION temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  562 */     statement.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processData() throws SQLException {
/*  570 */     if (Db2Version > 7) {
/*  571 */       importData();
/*      */     } else {
/*  573 */       insertData();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insertData() throws SQLException {
/*  581 */     Statement statement = conOPICM.createStatement();
/*  582 */     String str = "delete from " + WWTable;
/*  583 */     int i = statement.executeUpdate(str);
/*  584 */     if (i < 0) {
/*  585 */       System.out.println("delete " + WWTable + " error!!");
/*      */     } else {
/*  587 */       System.out.println("delete " + WWTable + " success rows=" + i);
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
/*  599 */     statement.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void importData() throws SQLException {
/*  606 */     String str1 = "CALL SYSPROC.ADMIN_CMD(?)";
/*  607 */     CallableStatement callableStatement = conOPICM.prepareCall(str1);
/*  608 */     String str2 = "import from /dev/null of del replace into " + WWTable;
/*  609 */     callableStatement.setString(1, str2);
/*  610 */     callableStatement.execute();
/*  611 */     callableStatement.close();
/*  612 */     System.out.println("import null into " + WWTable + " table!!");
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
/*      */   private void clearTempTable() throws SQLException {
/*  630 */     if (Db2Version > 7) {
/*  631 */       importTempTable();
/*      */     } else {
/*  633 */       deleteTempTable();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void importTempTable() throws SQLException {
/*  641 */     String str1 = "CALL SYSPROC.ADMIN_CMD(?)";
/*  642 */     CallableStatement callableStatement = conOPICM.prepareCall(str1);
/*  643 */     String str2 = "import from /dev/null of del replace into gbli.WWCOMPATTEMP";
/*  644 */     callableStatement.setString(1, str2);
/*  645 */     callableStatement.execute();
/*  646 */     ResultSet resultSet = callableStatement.getResultSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  654 */     if (resultSet.next()) {
/*      */ 
/*      */       
/*  657 */       int i = resultSet.getInt(1);
/*      */       
/*  659 */       int j = resultSet.getInt(2);
/*      */       
/*  661 */       int k = resultSet.getInt(3);
/*      */       
/*  663 */       int m = resultSet.getInt(4);
/*      */       
/*  665 */       int n = resultSet.getInt(5);
/*      */       
/*  667 */       int i1 = resultSet.getInt(6);
/*      */ 
/*      */ 
/*      */       
/*  671 */       String str3 = resultSet.getString(7);
/*      */ 
/*      */ 
/*      */       
/*  675 */       String str4 = resultSet.getString(8);
/*      */ 
/*      */       
/*  678 */       System.out.print("\nTotal number of rows read : ");
/*  679 */       System.out.println(i);
/*  680 */       System.out.print("Total number of rows skipped   : ");
/*  681 */       System.out.println(j);
/*  682 */       System.out.print("Total number of rows loaded    : ");
/*  683 */       System.out.println(k);
/*  684 */       System.out.print("Total number of rows rejected  : ");
/*  685 */       System.out.println(m);
/*  686 */       System.out.print("Total number of rows deleted   : ");
/*  687 */       System.out.println(n);
/*  688 */       System.out.print("Total number of rows committed : ");
/*  689 */       System.out.println(i1);
/*  690 */       System.out.print("SQL for retrieving the messages: ");
/*  691 */       System.out.println(str3);
/*  692 */       System.out.print("SQL for removing the messages  : ");
/*  693 */       System.out.println(str4);
/*      */     } 
/*      */     
/*  696 */     callableStatement.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteTempTable() throws SQLException {
/*  706 */     Statement statement = conOPICM.createStatement();
/*  707 */     String str = "delete from gbli.WWCOMPATTEMP";
/*  708 */     int i = statement.executeUpdate(str);
/*  709 */     if (i < 0) {
/*  710 */       System.out.println("delete error!!");
/*      */     } else {
/*  712 */       System.out.println("delete success rows=" + i);
/*      */     } 
/*  714 */     statement.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setConOPICM(Connection paramConnection) {
/*  723 */     conOPICM = paramConnection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Connection getConOPICM() {
/*  730 */     return conOPICM;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void closeConnection() throws SQLException {
/*  738 */     Connection connection = conOPICM;
/*  739 */     if (connection != null) {
/*      */       try {
/*  741 */         connection.rollback();
/*      */       }
/*  743 */       catch (Throwable throwable) {
/*  744 */         System.out.println("XMLMQAdapter.closeConnection(), unable to rollback. " + throwable);
/*      */       } finally {
/*      */         
/*  747 */         connection.close();
/*  748 */         connection = null;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  755 */   private String INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL = "insert into GBLI.WWCOMPATSYSTEM(entityid,entitytype,OSLEVEL)       \r\n  select entityid,entitytype,'' as OSLEVEL                         \r\n  from opicm.flag                                                  \r\n  where                                                            \r\n      entitytype in('LSEOBUNDLE','WWSEO','MODEL') and              \r\n      ENTERPRISE='SG' and                                          \r\n      valto > current timestamp and                                \r\n      effto > current timestamp and                                \r\n      ATTRIBUTEVALUE in('0040','0020') and                         \r\n      attributecode = 'STATUS'                                     \r\n  with ur                                                          \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  912 */   private String INSERT_INTO_GBLI_WWCOMPATGROUP_SQL = " insert into GBLI.WWCOMPATGROUP(entityid,entitytype,OKTOPUB,BRANDCD)  \r\n   select entityid,entitytype,OKTOPUB,BRANDCD from                    \r\n   (                                                                  \r\n     select AA.entityid,AA.entitytype,AA.OKTOPUB,BB.BRANDCD from      \r\n     (                                                                \r\n       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OKTOPUB     \r\n       from opicm.flag as A left join opicm.flag B                    \r\n       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n       A.entityid = B.entityid and                                    \r\n       A.entitytype = B.entitytype                                    \r\n       where                                                          \r\n       A.entitytype ='SEOCG' and                                      \r\n       A.ENTERPRISE='SG' and                                          \r\n       B.ENTERPRISE='SG' and                                          \r\n       A.valto > current timestamp and                                \r\n       A.effto > current timestamp and                                \r\n       B.valto > current timestamp and                                \r\n       B.effto > current timestamp and                                \r\n       B.attributecode = 'OKTOPUB' and                                \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       A.attributecode = 'STATUS'                                     \r\n       union                                                          \r\n       select C.entityid, C.entitytype,'' as OKTOPUB                  \r\n       from opicm.flag as C                                           \r\n       where                                                          \r\n       C.entitytype ='SEOCG' and                                      \r\n       C.ENTERPRISE='SG' and                                          \r\n       C.valto > current timestamp and                                \r\n       C.effto > current timestamp and                                \r\n       C.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       C.attributecode = 'STATUS' and                                 \r\n       C.entityid not in( select A.entityid                           \r\n       from opicm.flag as A left join opicm.flag B                    \r\n       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n       A.entityid = B.entityid and                                    \r\n       A.entitytype = B.entitytype                                    \r\n       where                                                          \r\n       A.entitytype ='SEOCG' and                                      \r\n       A.ENTERPRISE='SG' and                                          \r\n       B.ENTERPRISE='SG' and                                          \r\n       A.valto > current timestamp and                                \r\n       A.effto > current timestamp and                                \r\n       B.valto > current timestamp and                                \r\n       B.effto > current timestamp and                                \r\n       B.attributecode = 'OKTOPUB' and                                \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       A.attributecode = 'STATUS' )                                   \r\n     )                                                                \r\n     as AA,                                                           \r\n     (                                                                \r\n       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as BRANDCD     \r\n       from opicm.flag as A left join opicm.flag B                    \r\n       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n       A.entityid = B.entityid and                                    \r\n       A.entitytype = B.entitytype                                    \r\n       where                                                          \r\n       A.entitytype ='SEOCG' and                                      \r\n       A.ENTERPRISE='SG' and                                          \r\n       B.ENTERPRISE='SG' and                                          \r\n       A.valto > current timestamp and                                \r\n       A.effto > current timestamp and                                \r\n       B.valto > current timestamp and                                \r\n       B.effto > current timestamp and                                \r\n       B.attributecode = 'BRANDCD' and                                \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       A.attributecode = 'STATUS'                                     \r\n       union                                                          \r\n       select C.entityid, C.entitytype,'' as BRANDCD                  \r\n       from opicm.flag as C                                           \r\n       where                                                          \r\n       C.entitytype ='SEOCG' and                                      \r\n       C.ENTERPRISE='SG' and                                          \r\n       C.valto > current timestamp and                                \r\n       C.effto > current timestamp and                                \r\n       C.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       C.attributecode = 'STATUS' and                                 \r\n       C.entityid not in( select A.entityid                           \r\n       from opicm.flag as A left join opicm.flag B                    \r\n       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n       A.entityid = B.entityid and                                    \r\n       A.entitytype = B.entitytype                                    \r\n       where                                                          \r\n       A.entitytype ='SEOCG' and                                      \r\n       A.ENTERPRISE='SG' and                                          \r\n       B.ENTERPRISE='SG' and                                          \r\n       A.valto > current timestamp and                                \r\n       A.effto > current timestamp and                                \r\n       B.valto > current timestamp and                                \r\n       B.effto > current timestamp and                                \r\n       B.attributecode = 'BRANDCD' and                                \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       A.attributecode = 'STATUS' )                                   \r\n     )                                                                \r\n     as BB                                                            \r\n     where AA.ENTITYID=BB.ENTITYID                                    \r\n  ) as Group with ur                                                  \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1103 */   private String INSERT_INTO_GBLI_WWCOMPATOS_SQL = " insert into GBLI.WWCOMPATOS(entityid,entitytype,OS)           \r\n   select entityid,entitytype,OS from                          \r\n   (                                                           \r\n     select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OS     \r\n     from opicm.flag as A left join opicm.flag B               \r\n     on A.ENTERPRISE = B.ENTERPRISE and                        \r\n     A.entityid = B.entityid and                               \r\n     A.entitytype = B.entitytype                               \r\n     where                                                     \r\n     A.entitytype ='SEOCGOS' and                               \r\n     A.ENTERPRISE='SG' and                                     \r\n     B.ENTERPRISE='SG' and                                     \r\n     A.valto > current timestamp and                           \r\n     A.effto > current timestamp and                           \r\n     B.valto > current timestamp and                           \r\n     B.effto > current timestamp and                           \r\n     B.attributecode = 'OS' and                                \r\n     A.ATTRIBUTEVALUE in('0040','0020') and                    \r\n     A.attributecode = 'STATUS'                                \r\n     union                                                     \r\n     select C.entityid, C.entitytype,'' as OS                  \r\n     from opicm.flag as C                                      \r\n     where                                                     \r\n     C.entitytype ='SEOCGOS' and                               \r\n     C.ENTERPRISE='SG' and                                     \r\n     C.valto > current timestamp and                           \r\n     C.effto > current timestamp and                           \r\n     C.ATTRIBUTEVALUE in('0040','0020') and                    \r\n     C.attributecode = 'STATUS' and                            \r\n     C.entityid not in(select A.entityid                       \r\n     from opicm.flag as A left join opicm.flag B               \r\n     on A.ENTERPRISE = B.ENTERPRISE and                        \r\n     A.entityid = B.entityid and                               \r\n     A.entitytype = B.entitytype                               \r\n     where                                                     \r\n     A.entitytype ='SEOCGOS' and                               \r\n     A.ENTERPRISE='SG' and                                     \r\n     B.ENTERPRISE='SG' and                                     \r\n     A.valto > current timestamp and                           \r\n     A.effto > current timestamp and                           \r\n     B.valto > current timestamp and                           \r\n     B.effto > current timestamp and                           \r\n     B.attributecode = 'OS' and                                \r\n     A.ATTRIBUTEVALUE in('0040','0020') and                    \r\n     A.attributecode = 'STATUS')                               \r\n                                                               \r\n   ) as OS with ur                                             \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1195 */   private String INSERT_INTO_GBLI_WWCOMPATOSOP_SQL = " insert into GBLI.WWCOMPATOSOP(entityid,entitytype,COMPATPUBFLG,RELTYPE,PUBFROM,PUBTO)           \r\n    select entityid,entitytype,COMPATPUBFLG,RELTYPE,PUBFROM,PUBTO from                           \r\n    (                                                                                            \r\n       select AA.entityid,AA.entitytype,AA.COMPATPUBFLG,BB.RELTYPE,CC.PUBFROM,DD.PUBTO from      \r\n      (                                                                                          \r\n        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as COMPATPUBFLG                          \r\n        from opicm.flag as A left join opicm.flag B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'COMPATPUBFLG' and                                                     \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS'                                                               \r\n        union all                                                                                \r\n        select C.entityid, C.entitytype,'' as COMPATPUBFLG                                       \r\n        from opicm.flag as C                                                                     \r\n        where                                                                                    \r\n        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        C.ENTERPRISE='SG' and                                                                    \r\n        C.valto > current timestamp and                                                          \r\n        C.effto > current timestamp and                                                          \r\n        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        C.attributecode = 'STATUS' and                                                           \r\n        C.entityid not in( select A.entityid                                                     \r\n        from opicm.flag as A left join opicm.flag B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'COMPATPUBFLG' and                                                     \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS' )                                                             \r\n      )                                                                                          \r\n      as AA,                                                                                     \r\n      (                                                                                          \r\n        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as RELTYPE                               \r\n        from opicm.flag as A left join opicm.flag B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'RELTYPE' and                                                          \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS'                                                               \r\n        union all                                                                                \r\n        select C.entityid, C.entitytype,'' as RELTYPE                                            \r\n        from opicm.flag as C                                                                     \r\n        where                                                                                    \r\n        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        C.ENTERPRISE='SG' and                                                                    \r\n        C.valto > current timestamp and                                                          \r\n        C.effto > current timestamp and                                                          \r\n        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        C.attributecode = 'STATUS' and                                                           \r\n        C.entityid not in( select A.entityid                                                     \r\n        from opicm.flag as A left join opicm.flag B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'RELTYPE' and                                                          \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS' )                                                             \r\n      )                                                                                          \r\n      as BB,                                                                                     \r\n      (                                                                                          \r\n        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as PUBFROM                               \r\n        from opicm.flag as A left join opicm.text B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'PUBFROM' and                                                          \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS'                                                               \r\n        union all                                                                                \r\n        select C.entityid, C.entitytype,'' as PUBFROM                                            \r\n        from opicm.flag as C                                                                     \r\n        where                                                                                    \r\n        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        C.ENTERPRISE='SG' and                                                                    \r\n        C.valto > current timestamp and                                                          \r\n        C.effto > current timestamp and                                                          \r\n        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        C.attributecode = 'STATUS' and                                                           \r\n        C.entityid not in( select A.entityid                                                     \r\n        from opicm.flag as A left join opicm.text B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'PUBFROM' and                                                          \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS' )                                                             \r\n      )                                                                                          \r\n      as CC,                                                                                     \r\n     (                                                                                           \r\n        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as PUBTO                                 \r\n        from opicm.flag as A left join opicm.text B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'PUBTO' and                                                            \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS'                                                               \r\n        union all                                                                                \r\n        select C.entityid, C.entitytype,'' as PUBTO                                              \r\n        from opicm.flag as C                                                                     \r\n        where                                                                                    \r\n        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        C.ENTERPRISE='SG' and                                                                    \r\n        C.valto > current timestamp and                                                          \r\n        C.effto > current timestamp and                                                          \r\n        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        C.attributecode = 'STATUS' and                                                           \r\n        C.entityid not in( select A.entityid                                                     \r\n        from opicm.flag as A left join opicm.text B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'PUBTO' and                                                            \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS' )                                                             \r\n      )                                                                                          \r\n      as DD                                                                                      \r\n      where AA.ENTITYID=BB.ENTITYID                                                              \r\n      and AA.ENTITYID=CC.ENTITYID and AA.ENTITYID=DD.ENTITYID                                    \r\n    ) as OSOP with ur                                                                            \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1396 */   private String INSERT_INTO_GBLI_WWCOMPATOPTION_SQL = " insert into GBLI.WWCOMPATOPTION(entityid,entitytype,OSLEVEL)            \r\n   select entityid,entitytype,OSLEVEL from                               \r\n   (                                                                     \r\n     select * from                                                       \r\n     (                                                                   \r\n       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL        \r\n       from opicm.flag as A left join opicm.flag B                       \r\n       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n       A.entityid = B.entityid and                                       \r\n       A.entitytype = B.entitytype                                       \r\n       where                                                             \r\n       A.entitytype ='LSEOBUNDLE' and                                    \r\n       A.ENTERPRISE='SG' and                                             \r\n       B.ENTERPRISE='SG' and                                             \r\n       A.valto > current timestamp and                                   \r\n       A.effto > current timestamp and                                   \r\n       B.valto > current timestamp and                                   \r\n       B.effto > current timestamp and                                   \r\n       B.attributecode = 'OSLEVEL' and                                   \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       A.attributecode = 'STATUS'                                        \r\n       union all                                                         \r\n       select C.entityid, C.entitytype,'' as OSLEVEL                     \r\n       from opicm.flag as C                                              \r\n       where                                                             \r\n       C.entitytype ='LSEOBUNDLE' and                                    \r\n       C.ENTERPRISE='SG' and                                             \r\n       C.valto > current timestamp and                                   \r\n       C.effto > current timestamp and                                   \r\n       C.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       C.attributecode = 'STATUS' and                                    \r\n       C.entityid not in(select A.entityid                               \r\n       from opicm.flag as A left join opicm.flag B                       \r\n       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n       A.entityid = B.entityid and                                       \r\n       A.entitytype = B.entitytype                                       \r\n       where                                                             \r\n       A.entitytype ='LSEOBUNDLE' and                                    \r\n       A.ENTERPRISE='SG' and                                             \r\n       B.ENTERPRISE='SG' and                                             \r\n       A.valto > current timestamp and                                   \r\n       A.effto > current timestamp and                                   \r\n       B.valto > current timestamp and                                   \r\n       B.effto > current timestamp and                                   \r\n       B.attributecode = 'OSLEVEL' and                                   \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       A.attributecode = 'STATUS')                                       \r\n     ) as LSEOBUNDLE                                                     \r\n     union all                                                           \r\n     select * from                                                       \r\n     (                                                                   \r\n       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL        \r\n       from opicm.flag as A left join opicm.flag B                       \r\n       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n       A.entityid = B.entityid and                                       \r\n       A.entitytype = B.entitytype                                       \r\n       where                                                             \r\n       A.entitytype ='WWSEO' and                                         \r\n       A.ENTERPRISE='SG' and                                             \r\n       B.ENTERPRISE='SG' and                                             \r\n       A.valto > current timestamp and                                   \r\n       A.effto > current timestamp and                                   \r\n       B.valto > current timestamp and                                   \r\n       B.effto > current timestamp and                                   \r\n       B.attributecode = 'OS' and                                        \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       A.attributecode = 'STATUS'                                        \r\n       union all                                                         \r\n       select C.entityid, C.entitytype,'' as OSLEVEL                     \r\n       from opicm.flag as C                                              \r\n       where                                                             \r\n       C.entitytype ='WWSEO' and                                         \r\n       C.ENTERPRISE='SG' and                                             \r\n       C.valto > current timestamp and                                   \r\n       C.effto > current timestamp and                                   \r\n       C.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       C.attributecode = 'STATUS' and                                    \r\n       C.entityid not in(select A.entityid                               \r\n       from opicm.flag as A left join opicm.flag B                       \r\n       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n       A.entityid = B.entityid and                                       \r\n       A.entitytype = B.entitytype                                       \r\n       where                                                             \r\n       A.entitytype ='WWSEO' and                                         \r\n       A.ENTERPRISE='SG' and                                             \r\n       B.ENTERPRISE='SG' and                                             \r\n       A.valto > current timestamp and                                   \r\n       A.effto > current timestamp and                                   \r\n       B.valto > current timestamp and                                   \r\n       B.effto > current timestamp and                                   \r\n       B.attributecode = 'OS' and                                        \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       A.attributecode = 'STATUS')                                       \r\n     ) as WWSEO                                                          \r\n   ) as OPTION with ur                                                   \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1671 */   private String INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL = "  insert into GBLI.WWCOMPATTEMP(                                                        \r\n  ACTIVITY,UPDATED,TIMEOFCHANGE,BRANDCD_FC,SYSTEMENTITYTYPE,                            \r\n  SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OKTOPUB,                        \r\n  OSENTITYTYPE,OSENTITYID,OS,OPTIONENTITYTYPE,OPTIONENTITYID,                           \r\n  COMPATIBILITYPUBLISHINGFLAG,RELATIONSHIPTYPE,PUBLISHFROM,PUBLISHTO                    \r\n  )                                                                                     \r\n  select distinct 'A',current timestamp,current timestamp,BRANDCD,SYSTEMENTITYTYPE,     \r\n       SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OKTOPUB,                   \r\n       OSENTITYTYPE,OSENTITYID,OS,OptionENTITYTYPE,OptionENTITYID,                      \r\n       COMPATIBILITYPUBLISHINGFLAG,RELATIONSHIPTYPE,PUBLISHFROM,PUBLISHTO               \r\n  from                                                                                  \r\n  (                                                                                     \r\n  \tselect                                                                             \r\n   \t    SystemGroup.SystemId              as SYSTEMENTITYID,                           \r\n  \t    SystemGroup.SystemType            as SYSTEMENTITYTYPE,                         \r\n  \t    SystemGroup.OSLEVEL               as SYSTEMOS,                                 \r\n  \t    case SystemGroup.OSLEVEL                                                       \r\n  \t    when '" + OSIndependent + "' then 'ALL'                                            \r\n  \t    when '' then 'ALL'                                                             \r\n  \t    else SystemGroup.OSLEVEL end as SYSTEMOS2,                                     \r\n  \t    SystemGroup.GroupId               as GROUPENTITYID,                            \r\n  \t    SystemGroup.GroupType             as GROUPENTITYTYPE,                          \r\n  \t    SystemGroup.OKTOPUB               as OKTOPUB,                                  \r\n  \t    SystemGroup.BRANDCD               as BRANDCD,                                  \r\n  \t    OSOption.OSId                     as OSENTITYID,                               \r\n  \t    OSOption.OSType                   as OSENTITYTYPE,                             \r\n  \t    OSOption.OS                       as OS,                                       \r\n  \t    case OSOption.OS                                                               \r\n  \t    when '" + OSIndependent + "' then 'ALL'                                            \r\n  \t    when '' then 'ALL'                                                             \r\n  \t    else OSOption.OS end as OS2,                                                   \r\n  \t    OSOption.OSOPTIONId               as OSOPTIONENTITYID,                         \r\n  \t    OSOption.OSOPTIONType             as OSOPTIONENTITYTYPE,                       \r\n  \t    OSOption.COMPATPUBFLG             as COMPATIBILITYPUBLISHINGFLAG,              \r\n        OSOption.RELTYPE                  as RELATIONSHIPTYPE,                          \r\n        OSOption.PUBFROM                  as PUBLISHFROM,                               \r\n        OSOption.PUBTO                    as PUBLISHTO,                                 \r\n  \t    OSOption.OptionId                 as OptionENTITYID,                           \r\n  \t    OSOption.OptionType               as OptionENTITYTYPE,                         \r\n  \t    case OSOPTION.OSLEVEL                                                          \r\n  \t    when '" + OSIndependent + "' then 'ALL'                                            \r\n  \t    when '' then 'ALL'                                                             \r\n  \t    else OSOPTION.OSLEVEL end as OPTIONOS2                                         \r\n  \tfrom                                                                               \r\n  \t(                                                                                  \r\n    \tselect                                                                             \r\n    \t   Group.entityid                   as GroupId,                                    \r\n    \t   Group.entityType                 as GroupType,                                  \r\n    \t   Group.OKTOPUB                    as OKTOPUB,                                    \r\n    \t   Group.BRANDCD                    as BRANDCD,                                    \r\n    \t   System.entityid                  as SystemId,                                   \r\n    \t   System.entityType                as SystemType,                                 \r\n    \t   System.OSLEVEL                   as OSLEVEL                                     \r\n    \tfrom GBLI.WWCOMPATGROUP              as Group                                      \r\n    \tinner join opicm.relator            as SEOCGBDL on                                 \r\n    \t    SEOCGBDL.entity1type            = 'SEOCG' and                                  \r\n    \t    SEOCGBDL.entity2type            = 'LSEOBUNDLE' and                             \r\n    \t    SEOCGBDL.entity1id              = Group.entityid and                           \r\n    \t    SEOCGBDL.valto                  > current timestamp and                        \r\n    \t    SEOCGBDL.effto                  > current timestamp and                        \r\n    \t    SEOCGBDL.ENTERPRISE             = 'SG'                                         \r\n    \tinner join GBLI.WWCOMPATSYSTEM       as System on                                  \r\n    \t    System.entitytype               = 'LSEOBUNDLE' and                             \r\n    \t    System.entityid                 = SEOCGBDL.entity2id                           \r\n    \twhere                                                                              \r\n    \t    Group.entitytype                = 'SEOCG'                                      \r\n    \tunion all                                                                          \r\n    \tselect                                                                             \r\n    \t   Group.entityid                   as GroupId,                                    \r\n    \t   Group.entityType                 as GroupType,     \t                           \r\n    \t   Group.OKTOPUB                    as OKTOPUB,                                    \r\n    \t   Group.BRANDCD                    as BRANDCD,                                    \r\n    \t   System.entityid                  as SystemId,                                   \r\n    \t   System.entityType                as SystemType,                                 \r\n    \t   System.OSLEVEL                   as OSLEVEL                                     \r\n    \tfrom GBLI.WWCOMPATGROUP              as Group                                      \r\n    \tinner join opicm.relator            as SEOCGMDL on                                 \r\n    \t    SEOCGMDL.entity1type            = 'SEOCG' and                                  \r\n    \t    SEOCGMDL.entity2type            = 'MODEL'  and                                 \r\n    \t    SEOCGMDL.entity1id              = Group.entityid and                           \r\n    \t    SEOCGMDL.valto                  > current timestamp and                        \r\n    \t    SEOCGMDL.effto                  > current timestamp and                        \r\n    \t    SEOCGMDL.ENTERPRISE             = 'SG'                                         \r\n    \t inner join GBLI.WWCOMPATSYSTEM      as System on                                  \r\n    \t    System.entitytype               = 'MODEL' and                                  \r\n    \t    System.entityid                 = SEOCGMDL.entity2id                           \r\n    \t where                                                                             \r\n    \t    Group.entitytype                = 'SEOCG'                                      \r\n    \t union all                                                                         \r\n    \t select                                                                            \r\n    \t   Group.entityid                   as GroupId,                                    \r\n    \t   Group.entityType                 as GroupType,     \t                           \r\n    \t   Group.OKTOPUB                    as OKTOPUB,                                    \r\n    \t   Group.BRANDCD                    as BRANDCD,                                    \r\n    \t   System.entityid                  as SystemId,                                   \r\n    \t   System.entityType                as SystemType,                                 \r\n    \t   System.OSLEVEL                   as OSLEVEL                                     \r\n    \t from GBLI.WWCOMPATGROUP             as Group                                      \r\n    \t inner join opicm.relator           as SEOCGSEO on                                 \r\n    \t    SEOCGSEO.entity1type            = 'SEOCG' and                                  \r\n    \t    SEOCGSEO.entity2type            = 'WWSEO'  and                                 \r\n    \t    SEOCGSEO.entity1id              = Group.entityid and                           \r\n    \t    SEOCGSEO.valto                  > current timestamp and                        \r\n    \t    SEOCGSEO.effto                  > current timestamp and                        \r\n    \t    SEOCGSEO.ENTERPRISE             = 'SG'                                         \r\n    \t inner join GBLI.WWCOMPATSYSTEM      as System on                                  \r\n    \t    System.entitytype               = 'WWSEO' and                                  \r\n    \t    System.entityid                 = SEOCGSEO.entity2id                           \r\n    \t where                                                                             \r\n    \t    Group.entitytype                = 'SEOCG'                                      \r\n    )                                                                                   \r\n    as SystemGroup                                                                      \r\n    inner join opicm.relator              as GroupOS on                                 \r\n          GroupOS.entity1type               = 'SEOCG' and                               \r\n          GroupOS.entity2type               = 'SEOCGOS'  and                            \r\n          GroupOS.entity1id                 = SystemGroup.GroupId and                   \r\n          GroupOS.valto                     > current timestamp and                     \r\n          GroupOS.effto                     > current timestamp and                     \r\n          GroupOS.ENTERPRISE                = 'SG'                                      \r\n    inner join                                                                          \r\n    (                                                                                   \r\n    \t select                                                                            \r\n    \t     OS.entityid                      as OSId,                                     \r\n    \t     OS.entitytype                    as OSType,                                   \r\n    \t     OS.OS                            as OS,                                       \r\n    \t     OSOption.entityid                as OSOPTIONId,                               \r\n    \t     OSOption.entitytype              as OSOPTIONType,                             \r\n    \t     OSOP.COMPATPUBFLG              as COMPATPUBFLG,                               \r\n    \t     OSOP.RELTYPE                   as RELTYPE,                                    \r\n    \t     OSOP.PUBFROM                   as PUBFROM,                                    \r\n    \t     OSOP.PUBTO                     as PUBTO,                                      \r\n    \t     Option.entityid                  as OptionId,                                 \r\n    \t     Option.entitytype                as OptionType,                               \r\n    \t     Option.OSLEVEL                   as OSLEVEL                                   \r\n    \t from GBLI.WWCOMPATOS                as OS                                         \r\n    \t inner join opicm.relator           as OSOption on                                 \r\n    \t     OSOption.entity1type            = 'SEOCGOS' and                               \r\n    \t     OSOption.entity2type            = 'LSEOBUNDLE'  and                           \r\n    \t     OSOption.entity1id              = OS.entityid and                             \r\n    \t     OSOption.valto                  > current timestamp and                       \r\n    \t     OSOption.effto                  > current timestamp and                       \r\n    \t     OSOption.ENTERPRISE             = 'SG'                                        \r\n    \t inner join GBLI.WWCOMPATOSOP        as OSOP  on                                   \r\n    \t     OSOP.entitytype                 = OSOption.entitytype and                     \r\n    \t     OSOP.entityid                   = OSOption.entityid                           \r\n    \t inner join GBLI.WWCOMPATOPTION      as Option on                                  \r\n    \t     Option.entitytype               = 'LSEOBUNDLE' and                            \r\n    \t     Option.entityid                 = OSOption.entity2id                          \r\n    \t where                                                                             \r\n    \t     OS.entitytype                   = 'SEOCGOS'                                   \r\n    \t union all                                                                         \r\n    \t select                                                                            \r\n    \t     OS.entityid                    as OSId,                                       \r\n    \t     OS.entitytype                  as OSType,                                     \r\n    \t     OS.OS                          as OS,                                         \r\n    \t     OSOption.entityid              as OSOPTIONId,                                 \r\n    \t     OSOption.entitytype            as OSOPTIONType,                               \r\n    \t     OSOP.COMPATPUBFLG              as COMPATPUBFLG,                               \r\n    \t     OSOP.RELTYPE                   as RELTYPE,                                    \r\n    \t     OSOP.PUBFROM                   as PUBFROM,                                    \r\n    \t     OSOP.PUBTO                     as PUBTO,                                      \r\n    \t     Option.entityid                as OptionId,                                   \r\n    \t     Option.entitytype              as OptionType,                                 \r\n    \t     Option.OSLEVEL                 as OSLEVEL                                     \r\n    \t from GBLI.WWCOMPATOS                as OS                                         \r\n    \t inner join opicm.relator           as OSOption on                                 \r\n    \t    OSOption.entity1type            = 'SEOCGOS' and                                \r\n    \t    OSOption.entity2type            = 'WWSEO'  and                                 \r\n    \t    OSOption.entity1id              = OS.entityid and                              \r\n    \t    OSOption.valto                  > current timestamp and                        \r\n    \t    OSOption.effto                  > current timestamp and                        \r\n    \t    OSOption.ENTERPRISE             = 'SG'                                         \r\n    \t inner join GBLI.WWCOMPATOSOP        as OSOP  on                                   \r\n    \t    OSOP.entitytype                 = OSOption.entitytype and                      \r\n    \t    OSOP.entityid                   = OSOption.entityid                            \r\n    \t inner join GBLI.WWCOMPATOPTION      as Option on                                  \r\n    \t    Option.entitytype               = 'WWSEO' and                                  \r\n    \t    Option.entityid                 = OSOption.entity2id                           \r\n    \t where                                                                             \r\n    \t    OS.entitytype                   = 'SEOCGOS'                                    \r\n    \t                                                                                   \r\n    \t ) as OSOption on                                                                  \r\n    \t    OSOption.OSType                 = 'SEOCGOS' and                                \r\n    \t    OSOption.OSId                   = GroupOS.entity2id                            \r\n  ) as temp                                                                             \r\n  where (                                                                               \r\n    temp.OS2='ALL'                                                                      \r\n  ) and temp.OKTOPUB!='DEL' and temp.COMPATIBILITYPUBLISHINGFLAG!='DEL'                 \r\n  with ur                                                                               \r\n";
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\WWCOMPATIDLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */