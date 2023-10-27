/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*      */ public class WWCOMPATIDLABR
/*      */ {
/*   58 */   private static Properties c_props = null;
/*   59 */   private static Connection conOPICM = null;
/*      */   
/*   61 */   private static Properties WWCOMPAT_props = null;
/*      */   
/*      */   private static String OSIndependent;
/*      */   
/*      */   private static String WWTable;
/*      */   private static int Db2Version;
/*      */   
/*      */   public static void main(String[] paramArrayOfString) {
/*   69 */     System.out.println("-----------------------------------------------------------");
/*   70 */     Date date = new Date();
/*   71 */     long l1 = System.currentTimeMillis();
/*   72 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*   73 */     String str1 = simpleDateFormat.format(date);
/*   74 */     readPropertyFile();
/*   75 */     initWWCOMPAT();
/*   76 */     WWCOMPATIDLABR wWCOMPATIDLABR = new WWCOMPATIDLABR();
/*      */     
/*   78 */     wWCOMPATIDLABR.getConnect();
/*   79 */     wWCOMPATIDLABR.mainline();
/*      */     try {
/*   81 */       wWCOMPATIDLABR.closeConnection();
/*   82 */     } catch (SQLException sQLException) {
/*   83 */       sQLException.printStackTrace();
/*   84 */       System.exit(1);
/*      */     } 
/*      */     
/*   87 */     System.out.println("Success!!");
/*   88 */     System.out.println("-----------------------------------------------------------");
/*   89 */     date = new Date();
/*   90 */     long l2 = System.currentTimeMillis();
/*   91 */     String str2 = simpleDateFormat.format(date);
/*   92 */     System.out.println("version 2");
/*   93 */     System.out.println("Start Time= " + str1);
/*   94 */     System.out.println("End   Time= " + str2);
/*   95 */     System.out.println("Total Time= " + (l2 - l1) + " milliseconds");
/*   96 */     System.out.println("-----------------------------------------------------------");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readPropertyFile() {
/*  103 */     String str1 = System.getProperty("user.dir") + "/middleware.server.properties";
/*  104 */     String str2 = System.getProperty("user.dir") + "/wwcompat.properties";
/*  105 */     BufferedInputStream bufferedInputStream = null;
/*      */     try {
/*      */       try {
/*  108 */         bufferedInputStream = new BufferedInputStream(new FileInputStream(str1));
/*  109 */         c_props = new Properties();
/*  110 */         c_props.load(bufferedInputStream);
/*      */         
/*  112 */         bufferedInputStream = new BufferedInputStream(new FileInputStream(str2));
/*  113 */         WWCOMPAT_props = new Properties();
/*  114 */         WWCOMPAT_props.load(bufferedInputStream);
/*      */       } finally {
/*      */         
/*  117 */         bufferedInputStream.close();
/*      */       }
/*      */     
/*  120 */     } catch (IOException iOException) {
/*  121 */       System.out.println("Unable to loadProperties for " + str1 + " " + iOException);
/*  122 */       System.exit(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void initWWCOMPAT() {
/*  129 */     OSIndependent = WWCOMPAT_props.getProperty("OSIndependent");
/*  130 */     System.out.println("OSIndependent=" + OSIndependent);
/*  131 */     WWTable = WWCOMPAT_props.getProperty("WWTable");
/*  132 */     Db2Version = Integer.parseInt(WWCOMPAT_props.getProperty("Db2Version"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void getConnect() {
/*      */     try {
/*  139 */       Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
/*      */       
/*  141 */       String str1 = c_props.getProperty("pdh_database_url");
/*  142 */       String str2 = c_props.getProperty("pdh_database_user");
/*  143 */       String str3 = c_props.getProperty("pdh_database_password");
/*      */       
/*  145 */       setConOPICM(DriverManager.getConnection(str1, str2, AES256Utils.decrypt(str3)));
/*  146 */       getConOPICM().setAutoCommit(true);
/*  147 */     } catch (Exception exception) {
/*  148 */       System.out.println(new Date() + ":connection:err: " + exception.getMessage());
/*  149 */       System.exit(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mainline() {
/*  157 */     System.out.println("Initializing for WWCOMPATIDLABR version2");
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  162 */       clearTempTable();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  167 */       createTempTable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  173 */       insertDataIntoTempTable();
/*      */ 
/*      */ 
/*      */       
/*  177 */       processData();
/*      */ 
/*      */ 
/*      */       
/*  181 */       dropTempIndexAndTable();
/*      */     }
/*  183 */     catch (SQLException sQLException) {
/*  184 */       System.out.println(new Date() + ":general-error" + sQLException.getMessage());
/*  185 */       sQLException.printStackTrace();
/*      */       try {
/*  187 */         dropTempTable();
/*  188 */       } catch (SQLException sQLException1) {
/*  189 */         sQLException1.printStackTrace();
/*      */       } 
/*  191 */       System.exit(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dropTempIndexAndTable() throws SQLException {
/*  199 */     Statement statement = conOPICM.createStatement();
/*  200 */     String str = "";
/*  201 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  206 */     str = " drop index \"GBLI    \".\"SYSTEM_INDEX\"";
/*  207 */     i = statement.executeUpdate(str);
/*  208 */     if (i < 0) {
/*  209 */       System.out.println("drop SYSTEM_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  211 */       System.out.println("drop SYSTEM_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  214 */     str = " drop index \"GBLI    \".\"GROUP_INDEX\"";
/*  215 */     i = statement.executeUpdate(str);
/*  216 */     if (i < 0) {
/*  217 */       System.out.println("drop GROUP_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  219 */       System.out.println("drop GROUP_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  222 */     str = " drop index \"GBLI    \".\"OSOP_INDEX\"";
/*  223 */     i = statement.executeUpdate(str);
/*  224 */     if (i < 0) {
/*  225 */       System.out.println("drop OSOP_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  227 */       System.out.println("drop OSOP_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  230 */     str = " drop index \"GBLI    \".\"OS_INDEX\"";
/*  231 */     i = statement.executeUpdate(str);
/*  232 */     if (i < 0) {
/*  233 */       System.out.println("drop OS_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  235 */       System.out.println("drop OS_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  238 */     str = " drop index \"GBLI    \".\"OPTION_INDEX\"";
/*  239 */     i = statement.executeUpdate(str);
/*  240 */     if (i < 0) {
/*  241 */       System.out.println("drop OPTION_INDEX index error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  243 */       System.out.println("drop OPTION_INDEX index success !! \r\n drop sql =" + str);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  250 */     str = " drop table \"GBLI    \".\"WWCOMPATSYSTEM\"";
/*  251 */     i = statement.executeUpdate(str);
/*  252 */     if (i < 0) {
/*  253 */       System.out.println("drop WWCOMPATSYSTEM temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  255 */       System.out.println("drop WWCOMPATSYSTEM temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  258 */     str = " drop table \"GBLI    \".\"WWCOMPATGROUP\"";
/*  259 */     i = statement.executeUpdate(str);
/*  260 */     if (i < 0) {
/*  261 */       System.out.println("drop WWCOMPATGROUP temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  263 */       System.out.println("drop WWCOMPATGROUP temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  266 */     str = " drop table \"GBLI    \".\"WWCOMPATOSOP\"";
/*  267 */     i = statement.executeUpdate(str);
/*  268 */     if (i < 0) {
/*  269 */       System.out.println("drop WWCOMPATOSOP temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  271 */       System.out.println("drop WWCOMPATOSOP temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  274 */     str = " drop table \"GBLI    \".\"WWCOMPATOS\"";
/*  275 */     i = statement.executeUpdate(str);
/*  276 */     if (i < 0) {
/*  277 */       System.out.println("drop WWCOMPATOS temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  279 */       System.out.println("drop WWCOMPATOS temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  282 */     str = " drop table \"GBLI    \".\"WWCOMPATOPTION\"";
/*  283 */     i = statement.executeUpdate(str);
/*  284 */     if (i < 0) {
/*  285 */       System.out.println("drop WWCOMPATOPTION temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  287 */       System.out.println("drop WWCOMPATOPTION temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dropTempTable() throws SQLException {
/*  296 */     Statement statement = conOPICM.createStatement();
/*  297 */     String str = "";
/*  298 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  304 */     str = " drop table \"GBLI    \".\"WWCOMPATSYSTEM\"";
/*  305 */     i = statement.executeUpdate(str);
/*  306 */     if (i < 0) {
/*  307 */       System.out.println("drop WWCOMPATSYSTEM temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  309 */       System.out.println("drop WWCOMPATSYSTEM temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  312 */     str = " drop table \"GBLI    \".\"WWCOMPATGROUP\"";
/*  313 */     i = statement.executeUpdate(str);
/*  314 */     if (i < 0) {
/*  315 */       System.out.println("drop WWCOMPATGROUP temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  317 */       System.out.println("drop WWCOMPATGROUP temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  320 */     str = " drop table \"GBLI    \".\"WWCOMPATOSOP\"";
/*  321 */     i = statement.executeUpdate(str);
/*  322 */     if (i < 0) {
/*  323 */       System.out.println("drop WWCOMPATOSOP temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  325 */       System.out.println("drop WWCOMPATOSOP temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  328 */     str = " drop table \"GBLI    \".\"WWCOMPATOS\"";
/*  329 */     i = statement.executeUpdate(str);
/*  330 */     if (i < 0) {
/*  331 */       System.out.println("drop WWCOMPATOS temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  333 */       System.out.println("drop WWCOMPATOS temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */     
/*  336 */     str = " drop table \"GBLI    \".\"WWCOMPATOPTION\"";
/*  337 */     i = statement.executeUpdate(str);
/*  338 */     if (i < 0) {
/*  339 */       System.out.println("drop WWCOMPATOPTION temp table error!!  \r\n drop sql =" + str);
/*      */     } else {
/*  341 */       System.out.println("drop WWCOMPATOPTION temp table success !! \r\n drop sql =" + str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insertDataIntoTempTable() throws SQLException {
/*  349 */     Statement statement = conOPICM.createStatement();
/*  350 */     int i = 0;
/*      */     
/*  352 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL);
/*  353 */     if (i < 0) {
/*  354 */       System.out.println("insert data into WWCOMPATSYSTEM table error!!    \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL);
/*      */     } else {
/*      */       
/*  357 */       System.out.println("insert data into WWCOMPATSYSTEM table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL);
/*      */     } 
/*      */ 
/*      */     
/*  361 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATGROUP_SQL);
/*  362 */     if (i < 0) {
/*  363 */       System.out.println("insert data into WWCOMPATGROUP table error!!    \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATGROUP_SQL);
/*      */     } else {
/*      */       
/*  366 */       System.out.println("insert data into WWCOMPATGROUP table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATGROUP_SQL);
/*      */     } 
/*      */ 
/*      */     
/*  370 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATOS_SQL);
/*  371 */     if (i < 0) {
/*  372 */       System.out.println("insert data into WWCOMPATOS table error!!  \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOS_SQL);
/*      */     } else {
/*      */       
/*  375 */       System.out.println("insert data into WWCOMPATOS table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOS_SQL);
/*      */     } 
/*      */ 
/*      */     
/*  379 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATOSOP_SQL);
/*  380 */     if (i < 0) {
/*  381 */       System.out.println("insert data into WWCOMPATOSOP table error!!  \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOSOP_SQL);
/*      */     } else {
/*      */       
/*  384 */       System.out.println("insert data into WWCOMPATOSOP table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOSOP_SQL);
/*      */     } 
/*      */ 
/*      */     
/*  388 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATOPTION_SQL);
/*  389 */     if (i < 0) {
/*  390 */       System.out.println("insert data into WWCOMPATSYSTEM table error!!  \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOPTION_SQL);
/*      */     } else {
/*      */       
/*  393 */       System.out.println("insert data into WWCOMPATSYSTEM table success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATOPTION_SQL);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  401 */     String str = " CREATE INDEX \"GBLI    \".\"SYSTEM_INDEX\" ON \"GBLI    \".\"WWCOMPATSYSTEM\" \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"OSLEVEL\" ASC)                                                      \r\n";
/*      */ 
/*      */ 
/*      */     
/*  405 */     i = statement.executeUpdate(str);
/*  406 */     if (i < 0) {
/*  407 */       System.out.println("Create SYSTEM_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  409 */       System.out.println("Create SYSTEM_INDEX temp Index success !! \r\n create sql =" + str);
/*      */     } 
/*      */ 
/*      */     
/*  413 */     str = " CREATE INDEX \"GBLI    \".\"GROUP_INDEX\" ON \"GBLI    \".\"WWCOMPATGROUP\"   \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"OKTOPUB\" ASC)                                                      \r\n";
/*      */ 
/*      */ 
/*      */     
/*  417 */     i = statement.executeUpdate(str);
/*  418 */     if (i < 0) {
/*  419 */       System.out.println("Create GROUP_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  421 */       System.out.println("Create GROUP_INDEX temp Index success !! \r\n create sql =" + str);
/*      */     } 
/*      */ 
/*      */     
/*  425 */     str = " CREATE INDEX \"GBLI    \".\"OSOP_INDEX\" ON \"GBLI    \".\"WWCOMPATOSOP\"     \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"COMPATPUBFLG\" ASC)                                                 \r\n";
/*      */ 
/*      */ 
/*      */     
/*  429 */     i = statement.executeUpdate(str);
/*  430 */     if (i < 0) {
/*  431 */       System.out.println("Create OSOP_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  433 */       System.out.println("Create OSOP_INDEX temp Index success !! \r\n create sql =" + str);
/*      */     } 
/*      */ 
/*      */     
/*  437 */     str = " CREATE INDEX \"GBLI    \".\"OS_INDEX\" ON \"GBLI    \".\"WWCOMPATOS\"         \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"OS\" ASC)                                                           \r\n";
/*      */ 
/*      */ 
/*      */     
/*  441 */     i = statement.executeUpdate(str);
/*  442 */     if (i < 0) {
/*  443 */       System.out.println("Create OS_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  445 */       System.out.println("Create OS_INDEX temp Index success !! \r\n create sql =" + str);
/*      */     } 
/*      */ 
/*      */     
/*  449 */     str = " CREATE INDEX \"GBLI    \".\"OPTION_INDEX\" ON \"GBLI    \".\"WWCOMPATOPTION\" \r\n  \t\t(\"ENTITYTYPE\" ASC,                                                   \r\n  \t\t \"ENTITYID\" ASC,                                                     \r\n  \t\t \"OSLEVEL\" ASC)                                                      \r\n";
/*      */ 
/*      */ 
/*      */     
/*  453 */     i = statement.executeUpdate(str);
/*  454 */     if (i < 0) {
/*  455 */       System.out.println("Create OPTION_INDEX temp Index error!!    \r\n create sql =" + str);
/*      */     } else {
/*  457 */       System.out.println("Create OPTION_INDEX temp Index success !! \r\n create sql =" + str);
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
/*  472 */     i = statement.executeUpdate(this.INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL);
/*  473 */     if (i < 0) {
/*  474 */       System.out.println("insert data into GBLI_WWCOMPATTEMP table for SEOCG error!!  \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL);
/*      */     } else {
/*      */       
/*  477 */       System.out.println("insert data into GBLI_WWCOMPATTEMP table for SEOCG success !! rows=" + i + " \r\n insert sql =" + this.INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createTempTable() throws SQLException {
/*  487 */     Statement statement = conOPICM.createStatement();
/*  488 */     String str = "";
/*  489 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  494 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATSYSTEM\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) , \r\n  \"ENTITYID\" INTEGER , \r\n  \"OSLEVEL\" VARCHAR(32) \r\n  ) IN \"TSPACE08\"   \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  499 */     i = statement.executeUpdate(str);
/*  500 */     if (i < 0) {
/*  501 */       System.out.println("Create WWCOMPATSYSTEM temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*      */       
/*  504 */       System.out.println("Create WWCOMPATSYSTEM temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  507 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATGROUP\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) ,                  \r\n  \"ENTITYID\" INTEGER ,                        \r\n  \"OKTOPUB\" VARCHAR(32),                      \r\n  \"BRANDCD\" VARCHAR(32)                       \r\n  ) IN \"TSPACE08\"                             \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  513 */     i = statement.executeUpdate(str);
/*  514 */     if (i < 0) {
/*  515 */       System.out.println("Create WWCOMPATGROUP temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*  517 */       System.out.println("Create WWCOMPATGROUP temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  520 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATOSOP\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) ,                 \r\n  \"ENTITYID\" INTEGER ,                       \r\n  \"COMPATPUBFLG\" VARCHAR(32),                \r\n  \"PUBFROM\" VARCHAR(32),                     \r\n  \"PUBTO\" VARCHAR(32),                       \r\n  \"RELTYPE\" VARCHAR(32)                      \r\n  ) IN \"TSPACE08\"                            \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  528 */     i = statement.executeUpdate(str);
/*  529 */     if (i < 0) {
/*  530 */       System.out.println("Create WWCOMPATOSOP temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*  532 */       System.out.println("Create WWCOMPATOSOP temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  535 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATOS\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) ,               \r\n  \"ENTITYID\" INTEGER ,                     \r\n  \"OS\" VARCHAR(32)                         \r\n  ) IN \"TSPACE08\"                          \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  540 */     i = statement.executeUpdate(str);
/*  541 */     if (i < 0) {
/*  542 */       System.out.println("Create WWCOMPATOS temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*  544 */       System.out.println("Create WWCOMPATOS temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  547 */     str = " CREATE TABLE \"GBLI    \".\"WWCOMPATOPTION\"  (  \r\n  \"ENTITYTYPE\" VARCHAR(32) ,                   \r\n  \"ENTITYID\" INTEGER ,                         \r\n  \"OSLEVEL\" VARCHAR(32)                        \r\n  ) IN \"TSPACE08\"                              \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  552 */     i = statement.executeUpdate(str);
/*  553 */     if (i < 0) {
/*  554 */       System.out.println("Create WWCOMPATOPTION temp table error!!  \r\n create sql =" + str);
/*      */     } else {
/*  556 */       System.out.println("Create WWCOMPATOPTION temp table success !! \r\n create sql =" + str);
/*      */     } 
/*      */     
/*  559 */     statement.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processData() throws SQLException {
/*  567 */     if (Db2Version > 7) {
/*  568 */       importData();
/*      */     } else {
/*  570 */       insertData();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insertData() throws SQLException {
/*  578 */     Statement statement = conOPICM.createStatement();
/*  579 */     String str = "delete from " + WWTable;
/*  580 */     int i = statement.executeUpdate(str);
/*  581 */     if (i < 0) {
/*  582 */       System.out.println("delete " + WWTable + " error!!");
/*      */     } else {
/*  584 */       System.out.println("delete " + WWTable + " success rows=" + i);
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
/*  596 */     statement.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void importData() throws SQLException {
/*  603 */     String str1 = "CALL SYSPROC.ADMIN_CMD(?)";
/*  604 */     CallableStatement callableStatement = conOPICM.prepareCall(str1);
/*  605 */     String str2 = "import from /dev/null of del replace into " + WWTable;
/*  606 */     callableStatement.setString(1, str2);
/*  607 */     callableStatement.execute();
/*  608 */     callableStatement.close();
/*  609 */     System.out.println("import null into " + WWTable + " table!!");
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
/*  627 */     if (Db2Version > 7) {
/*  628 */       importTempTable();
/*      */     } else {
/*  630 */       deleteTempTable();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void importTempTable() throws SQLException {
/*  638 */     String str1 = "CALL SYSPROC.ADMIN_CMD(?)";
/*  639 */     CallableStatement callableStatement = conOPICM.prepareCall(str1);
/*  640 */     String str2 = "import from /dev/null of del replace into gbli.WWCOMPATTEMP";
/*  641 */     callableStatement.setString(1, str2);
/*  642 */     callableStatement.execute();
/*  643 */     ResultSet resultSet = callableStatement.getResultSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  651 */     if (resultSet.next()) {
/*      */ 
/*      */       
/*  654 */       int i = resultSet.getInt(1);
/*      */       
/*  656 */       int j = resultSet.getInt(2);
/*      */       
/*  658 */       int k = resultSet.getInt(3);
/*      */       
/*  660 */       int m = resultSet.getInt(4);
/*      */       
/*  662 */       int n = resultSet.getInt(5);
/*      */       
/*  664 */       int i1 = resultSet.getInt(6);
/*      */ 
/*      */ 
/*      */       
/*  668 */       String str3 = resultSet.getString(7);
/*      */ 
/*      */ 
/*      */       
/*  672 */       String str4 = resultSet.getString(8);
/*      */ 
/*      */       
/*  675 */       System.out.print("\nTotal number of rows read : ");
/*  676 */       System.out.println(i);
/*  677 */       System.out.print("Total number of rows skipped   : ");
/*  678 */       System.out.println(j);
/*  679 */       System.out.print("Total number of rows loaded    : ");
/*  680 */       System.out.println(k);
/*  681 */       System.out.print("Total number of rows rejected  : ");
/*  682 */       System.out.println(m);
/*  683 */       System.out.print("Total number of rows deleted   : ");
/*  684 */       System.out.println(n);
/*  685 */       System.out.print("Total number of rows committed : ");
/*  686 */       System.out.println(i1);
/*  687 */       System.out.print("SQL for retrieving the messages: ");
/*  688 */       System.out.println(str3);
/*  689 */       System.out.print("SQL for removing the messages  : ");
/*  690 */       System.out.println(str4);
/*      */     } 
/*      */     
/*  693 */     callableStatement.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteTempTable() throws SQLException {
/*  703 */     Statement statement = conOPICM.createStatement();
/*  704 */     String str = "delete from gbli.WWCOMPATTEMP";
/*  705 */     int i = statement.executeUpdate(str);
/*  706 */     if (i < 0) {
/*  707 */       System.out.println("delete error!!");
/*      */     } else {
/*  709 */       System.out.println("delete success rows=" + i);
/*      */     } 
/*  711 */     statement.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setConOPICM(Connection paramConnection) {
/*  720 */     conOPICM = paramConnection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Connection getConOPICM() {
/*  727 */     return conOPICM;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void closeConnection() throws SQLException {
/*  735 */     Connection connection = conOPICM;
/*  736 */     if (connection != null) {
/*      */       try {
/*  738 */         connection.rollback();
/*      */       }
/*  740 */       catch (Throwable throwable) {
/*  741 */         System.out.println("XMLMQAdapter.closeConnection(), unable to rollback. " + throwable);
/*      */       } finally {
/*      */         
/*  744 */         connection.close();
/*  745 */         connection = null;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  752 */   private String INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL = "insert into GBLI.WWCOMPATSYSTEM(entityid,entitytype,OSLEVEL)       \r\n  select entityid,entitytype,'' as OSLEVEL                         \r\n  from opicm.flag                                                  \r\n  where                                                            \r\n      entitytype in('LSEOBUNDLE','WWSEO','MODEL') and              \r\n      ENTERPRISE='SG' and                                          \r\n      valto > current timestamp and                                \r\n      effto > current timestamp and                                \r\n      ATTRIBUTEVALUE in('0040','0020') and                         \r\n      attributecode = 'STATUS'                                     \r\n  with ur                                                          \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  909 */   private String INSERT_INTO_GBLI_WWCOMPATGROUP_SQL = " insert into GBLI.WWCOMPATGROUP(entityid,entitytype,OKTOPUB,BRANDCD)  \r\n   select entityid,entitytype,OKTOPUB,BRANDCD from                    \r\n   (                                                                  \r\n     select AA.entityid,AA.entitytype,AA.OKTOPUB,BB.BRANDCD from      \r\n     (                                                                \r\n       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OKTOPUB     \r\n       from opicm.flag as A left join opicm.flag B                    \r\n       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n       A.entityid = B.entityid and                                    \r\n       A.entitytype = B.entitytype                                    \r\n       where                                                          \r\n       A.entitytype ='SEOCG' and                                      \r\n       A.ENTERPRISE='SG' and                                          \r\n       B.ENTERPRISE='SG' and                                          \r\n       A.valto > current timestamp and                                \r\n       A.effto > current timestamp and                                \r\n       B.valto > current timestamp and                                \r\n       B.effto > current timestamp and                                \r\n       B.attributecode = 'OKTOPUB' and                                \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       A.attributecode = 'STATUS'                                     \r\n       union                                                          \r\n       select C.entityid, C.entitytype,'' as OKTOPUB                  \r\n       from opicm.flag as C                                           \r\n       where                                                          \r\n       C.entitytype ='SEOCG' and                                      \r\n       C.ENTERPRISE='SG' and                                          \r\n       C.valto > current timestamp and                                \r\n       C.effto > current timestamp and                                \r\n       C.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       C.attributecode = 'STATUS' and                                 \r\n       C.entityid not in( select A.entityid                           \r\n       from opicm.flag as A left join opicm.flag B                    \r\n       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n       A.entityid = B.entityid and                                    \r\n       A.entitytype = B.entitytype                                    \r\n       where                                                          \r\n       A.entitytype ='SEOCG' and                                      \r\n       A.ENTERPRISE='SG' and                                          \r\n       B.ENTERPRISE='SG' and                                          \r\n       A.valto > current timestamp and                                \r\n       A.effto > current timestamp and                                \r\n       B.valto > current timestamp and                                \r\n       B.effto > current timestamp and                                \r\n       B.attributecode = 'OKTOPUB' and                                \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       A.attributecode = 'STATUS' )                                   \r\n     )                                                                \r\n     as AA,                                                           \r\n     (                                                                \r\n       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as BRANDCD     \r\n       from opicm.flag as A left join opicm.flag B                    \r\n       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n       A.entityid = B.entityid and                                    \r\n       A.entitytype = B.entitytype                                    \r\n       where                                                          \r\n       A.entitytype ='SEOCG' and                                      \r\n       A.ENTERPRISE='SG' and                                          \r\n       B.ENTERPRISE='SG' and                                          \r\n       A.valto > current timestamp and                                \r\n       A.effto > current timestamp and                                \r\n       B.valto > current timestamp and                                \r\n       B.effto > current timestamp and                                \r\n       B.attributecode = 'BRANDCD' and                                \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       A.attributecode = 'STATUS'                                     \r\n       union                                                          \r\n       select C.entityid, C.entitytype,'' as BRANDCD                  \r\n       from opicm.flag as C                                           \r\n       where                                                          \r\n       C.entitytype ='SEOCG' and                                      \r\n       C.ENTERPRISE='SG' and                                          \r\n       C.valto > current timestamp and                                \r\n       C.effto > current timestamp and                                \r\n       C.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       C.attributecode = 'STATUS' and                                 \r\n       C.entityid not in( select A.entityid                           \r\n       from opicm.flag as A left join opicm.flag B                    \r\n       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n       A.entityid = B.entityid and                                    \r\n       A.entitytype = B.entitytype                                    \r\n       where                                                          \r\n       A.entitytype ='SEOCG' and                                      \r\n       A.ENTERPRISE='SG' and                                          \r\n       B.ENTERPRISE='SG' and                                          \r\n       A.valto > current timestamp and                                \r\n       A.effto > current timestamp and                                \r\n       B.valto > current timestamp and                                \r\n       B.effto > current timestamp and                                \r\n       B.attributecode = 'BRANDCD' and                                \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n       A.attributecode = 'STATUS' )                                   \r\n     )                                                                \r\n     as BB                                                            \r\n     where AA.ENTITYID=BB.ENTITYID                                    \r\n  ) as Group with ur                                                  \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1100 */   private String INSERT_INTO_GBLI_WWCOMPATOS_SQL = " insert into GBLI.WWCOMPATOS(entityid,entitytype,OS)           \r\n   select entityid,entitytype,OS from                          \r\n   (                                                           \r\n     select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OS     \r\n     from opicm.flag as A left join opicm.flag B               \r\n     on A.ENTERPRISE = B.ENTERPRISE and                        \r\n     A.entityid = B.entityid and                               \r\n     A.entitytype = B.entitytype                               \r\n     where                                                     \r\n     A.entitytype ='SEOCGOS' and                               \r\n     A.ENTERPRISE='SG' and                                     \r\n     B.ENTERPRISE='SG' and                                     \r\n     A.valto > current timestamp and                           \r\n     A.effto > current timestamp and                           \r\n     B.valto > current timestamp and                           \r\n     B.effto > current timestamp and                           \r\n     B.attributecode = 'OS' and                                \r\n     A.ATTRIBUTEVALUE in('0040','0020') and                    \r\n     A.attributecode = 'STATUS'                                \r\n     union                                                     \r\n     select C.entityid, C.entitytype,'' as OS                  \r\n     from opicm.flag as C                                      \r\n     where                                                     \r\n     C.entitytype ='SEOCGOS' and                               \r\n     C.ENTERPRISE='SG' and                                     \r\n     C.valto > current timestamp and                           \r\n     C.effto > current timestamp and                           \r\n     C.ATTRIBUTEVALUE in('0040','0020') and                    \r\n     C.attributecode = 'STATUS' and                            \r\n     C.entityid not in(select A.entityid                       \r\n     from opicm.flag as A left join opicm.flag B               \r\n     on A.ENTERPRISE = B.ENTERPRISE and                        \r\n     A.entityid = B.entityid and                               \r\n     A.entitytype = B.entitytype                               \r\n     where                                                     \r\n     A.entitytype ='SEOCGOS' and                               \r\n     A.ENTERPRISE='SG' and                                     \r\n     B.ENTERPRISE='SG' and                                     \r\n     A.valto > current timestamp and                           \r\n     A.effto > current timestamp and                           \r\n     B.valto > current timestamp and                           \r\n     B.effto > current timestamp and                           \r\n     B.attributecode = 'OS' and                                \r\n     A.ATTRIBUTEVALUE in('0040','0020') and                    \r\n     A.attributecode = 'STATUS')                               \r\n                                                               \r\n   ) as OS with ur                                             \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1192 */   private String INSERT_INTO_GBLI_WWCOMPATOSOP_SQL = " insert into GBLI.WWCOMPATOSOP(entityid,entitytype,COMPATPUBFLG,RELTYPE,PUBFROM,PUBTO)           \r\n    select entityid,entitytype,COMPATPUBFLG,RELTYPE,PUBFROM,PUBTO from                           \r\n    (                                                                                            \r\n       select AA.entityid,AA.entitytype,AA.COMPATPUBFLG,BB.RELTYPE,CC.PUBFROM,DD.PUBTO from      \r\n      (                                                                                          \r\n        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as COMPATPUBFLG                          \r\n        from opicm.flag as A left join opicm.flag B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'COMPATPUBFLG' and                                                     \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS'                                                               \r\n        union all                                                                                \r\n        select C.entityid, C.entitytype,'' as COMPATPUBFLG                                       \r\n        from opicm.flag as C                                                                     \r\n        where                                                                                    \r\n        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        C.ENTERPRISE='SG' and                                                                    \r\n        C.valto > current timestamp and                                                          \r\n        C.effto > current timestamp and                                                          \r\n        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        C.attributecode = 'STATUS' and                                                           \r\n        C.entityid not in( select A.entityid                                                     \r\n        from opicm.flag as A left join opicm.flag B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'COMPATPUBFLG' and                                                     \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS' )                                                             \r\n      )                                                                                          \r\n      as AA,                                                                                     \r\n      (                                                                                          \r\n        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as RELTYPE                               \r\n        from opicm.flag as A left join opicm.flag B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'RELTYPE' and                                                          \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS'                                                               \r\n        union all                                                                                \r\n        select C.entityid, C.entitytype,'' as RELTYPE                                            \r\n        from opicm.flag as C                                                                     \r\n        where                                                                                    \r\n        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        C.ENTERPRISE='SG' and                                                                    \r\n        C.valto > current timestamp and                                                          \r\n        C.effto > current timestamp and                                                          \r\n        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        C.attributecode = 'STATUS' and                                                           \r\n        C.entityid not in( select A.entityid                                                     \r\n        from opicm.flag as A left join opicm.flag B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'RELTYPE' and                                                          \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS' )                                                             \r\n      )                                                                                          \r\n      as BB,                                                                                     \r\n      (                                                                                          \r\n        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as PUBFROM                               \r\n        from opicm.flag as A left join opicm.text B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'PUBFROM' and                                                          \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS'                                                               \r\n        union all                                                                                \r\n        select C.entityid, C.entitytype,'' as PUBFROM                                            \r\n        from opicm.flag as C                                                                     \r\n        where                                                                                    \r\n        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        C.ENTERPRISE='SG' and                                                                    \r\n        C.valto > current timestamp and                                                          \r\n        C.effto > current timestamp and                                                          \r\n        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        C.attributecode = 'STATUS' and                                                           \r\n        C.entityid not in( select A.entityid                                                     \r\n        from opicm.flag as A left join opicm.text B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'PUBFROM' and                                                          \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS' )                                                             \r\n      )                                                                                          \r\n      as CC,                                                                                     \r\n     (                                                                                           \r\n        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as PUBTO                                 \r\n        from opicm.flag as A left join opicm.text B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'PUBTO' and                                                            \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS'                                                               \r\n        union all                                                                                \r\n        select C.entityid, C.entitytype,'' as PUBTO                                              \r\n        from opicm.flag as C                                                                     \r\n        where                                                                                    \r\n        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        C.ENTERPRISE='SG' and                                                                    \r\n        C.valto > current timestamp and                                                          \r\n        C.effto > current timestamp and                                                          \r\n        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        C.attributecode = 'STATUS' and                                                           \r\n        C.entityid not in( select A.entityid                                                     \r\n        from opicm.flag as A left join opicm.text B                                              \r\n        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n        A.entityid = B.entityid and                                                              \r\n        A.entitytype = B.entitytype                                                              \r\n        where                                                                                    \r\n        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n        A.ENTERPRISE='SG' and                                                                    \r\n        B.ENTERPRISE='SG' and                                                                    \r\n        A.valto > current timestamp and                                                          \r\n        A.effto > current timestamp and                                                          \r\n        B.valto > current timestamp and                                                          \r\n        B.effto > current timestamp and                                                          \r\n        B.attributecode = 'PUBTO' and                                                            \r\n        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n        A.attributecode = 'STATUS' )                                                             \r\n      )                                                                                          \r\n      as DD                                                                                      \r\n      where AA.ENTITYID=BB.ENTITYID                                                              \r\n      and AA.ENTITYID=CC.ENTITYID and AA.ENTITYID=DD.ENTITYID                                    \r\n    ) as OSOP with ur                                                                            \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1393 */   private String INSERT_INTO_GBLI_WWCOMPATOPTION_SQL = " insert into GBLI.WWCOMPATOPTION(entityid,entitytype,OSLEVEL)            \r\n   select entityid,entitytype,OSLEVEL from                               \r\n   (                                                                     \r\n     select * from                                                       \r\n     (                                                                   \r\n       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL        \r\n       from opicm.flag as A left join opicm.flag B                       \r\n       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n       A.entityid = B.entityid and                                       \r\n       A.entitytype = B.entitytype                                       \r\n       where                                                             \r\n       A.entitytype ='LSEOBUNDLE' and                                    \r\n       A.ENTERPRISE='SG' and                                             \r\n       B.ENTERPRISE='SG' and                                             \r\n       A.valto > current timestamp and                                   \r\n       A.effto > current timestamp and                                   \r\n       B.valto > current timestamp and                                   \r\n       B.effto > current timestamp and                                   \r\n       B.attributecode = 'OSLEVEL' and                                   \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       A.attributecode = 'STATUS'                                        \r\n       union all                                                         \r\n       select C.entityid, C.entitytype,'' as OSLEVEL                     \r\n       from opicm.flag as C                                              \r\n       where                                                             \r\n       C.entitytype ='LSEOBUNDLE' and                                    \r\n       C.ENTERPRISE='SG' and                                             \r\n       C.valto > current timestamp and                                   \r\n       C.effto > current timestamp and                                   \r\n       C.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       C.attributecode = 'STATUS' and                                    \r\n       C.entityid not in(select A.entityid                               \r\n       from opicm.flag as A left join opicm.flag B                       \r\n       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n       A.entityid = B.entityid and                                       \r\n       A.entitytype = B.entitytype                                       \r\n       where                                                             \r\n       A.entitytype ='LSEOBUNDLE' and                                    \r\n       A.ENTERPRISE='SG' and                                             \r\n       B.ENTERPRISE='SG' and                                             \r\n       A.valto > current timestamp and                                   \r\n       A.effto > current timestamp and                                   \r\n       B.valto > current timestamp and                                   \r\n       B.effto > current timestamp and                                   \r\n       B.attributecode = 'OSLEVEL' and                                   \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       A.attributecode = 'STATUS')                                       \r\n     ) as LSEOBUNDLE                                                     \r\n     union all                                                           \r\n     select * from                                                       \r\n     (                                                                   \r\n       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL        \r\n       from opicm.flag as A left join opicm.flag B                       \r\n       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n       A.entityid = B.entityid and                                       \r\n       A.entitytype = B.entitytype                                       \r\n       where                                                             \r\n       A.entitytype ='WWSEO' and                                         \r\n       A.ENTERPRISE='SG' and                                             \r\n       B.ENTERPRISE='SG' and                                             \r\n       A.valto > current timestamp and                                   \r\n       A.effto > current timestamp and                                   \r\n       B.valto > current timestamp and                                   \r\n       B.effto > current timestamp and                                   \r\n       B.attributecode = 'OS' and                                        \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       A.attributecode = 'STATUS'                                        \r\n       union all                                                         \r\n       select C.entityid, C.entitytype,'' as OSLEVEL                     \r\n       from opicm.flag as C                                              \r\n       where                                                             \r\n       C.entitytype ='WWSEO' and                                         \r\n       C.ENTERPRISE='SG' and                                             \r\n       C.valto > current timestamp and                                   \r\n       C.effto > current timestamp and                                   \r\n       C.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       C.attributecode = 'STATUS' and                                    \r\n       C.entityid not in(select A.entityid                               \r\n       from opicm.flag as A left join opicm.flag B                       \r\n       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n       A.entityid = B.entityid and                                       \r\n       A.entitytype = B.entitytype                                       \r\n       where                                                             \r\n       A.entitytype ='WWSEO' and                                         \r\n       A.ENTERPRISE='SG' and                                             \r\n       B.ENTERPRISE='SG' and                                             \r\n       A.valto > current timestamp and                                   \r\n       A.effto > current timestamp and                                   \r\n       B.valto > current timestamp and                                   \r\n       B.effto > current timestamp and                                   \r\n       B.attributecode = 'OS' and                                        \r\n       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n       A.attributecode = 'STATUS')                                       \r\n     ) as WWSEO                                                          \r\n   ) as OPTION with ur                                                   \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1668 */   private String INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL = "  insert into GBLI.WWCOMPATTEMP(                                                        \r\n  ACTIVITY,UPDATED,TIMEOFCHANGE,BRANDCD_FC,SYSTEMENTITYTYPE,                            \r\n  SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OKTOPUB,                        \r\n  OSENTITYTYPE,OSENTITYID,OS,OPTIONENTITYTYPE,OPTIONENTITYID,                           \r\n  COMPATIBILITYPUBLISHINGFLAG,RELATIONSHIPTYPE,PUBLISHFROM,PUBLISHTO                    \r\n  )                                                                                     \r\n  select distinct 'A',current timestamp,current timestamp,BRANDCD,SYSTEMENTITYTYPE,     \r\n       SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OKTOPUB,                   \r\n       OSENTITYTYPE,OSENTITYID,OS,OptionENTITYTYPE,OptionENTITYID,                      \r\n       COMPATIBILITYPUBLISHINGFLAG,RELATIONSHIPTYPE,PUBLISHFROM,PUBLISHTO               \r\n  from                                                                                  \r\n  (                                                                                     \r\n  \tselect                                                                             \r\n   \t    SystemGroup.SystemId              as SYSTEMENTITYID,                           \r\n  \t    SystemGroup.SystemType            as SYSTEMENTITYTYPE,                         \r\n  \t    SystemGroup.OSLEVEL               as SYSTEMOS,                                 \r\n  \t    case SystemGroup.OSLEVEL                                                       \r\n  \t    when '" + OSIndependent + "' then 'ALL'                                            \r\n  \t    when '' then 'ALL'                                                             \r\n  \t    else SystemGroup.OSLEVEL end as SYSTEMOS2,                                     \r\n  \t    SystemGroup.GroupId               as GROUPENTITYID,                            \r\n  \t    SystemGroup.GroupType             as GROUPENTITYTYPE,                          \r\n  \t    SystemGroup.OKTOPUB               as OKTOPUB,                                  \r\n  \t    SystemGroup.BRANDCD               as BRANDCD,                                  \r\n  \t    OSOption.OSId                     as OSENTITYID,                               \r\n  \t    OSOption.OSType                   as OSENTITYTYPE,                             \r\n  \t    OSOption.OS                       as OS,                                       \r\n  \t    case OSOption.OS                                                               \r\n  \t    when '" + OSIndependent + "' then 'ALL'                                            \r\n  \t    when '' then 'ALL'                                                             \r\n  \t    else OSOption.OS end as OS2,                                                   \r\n  \t    OSOption.OSOPTIONId               as OSOPTIONENTITYID,                         \r\n  \t    OSOption.OSOPTIONType             as OSOPTIONENTITYTYPE,                       \r\n  \t    OSOption.COMPATPUBFLG             as COMPATIBILITYPUBLISHINGFLAG,              \r\n        OSOption.RELTYPE                  as RELATIONSHIPTYPE,                          \r\n        OSOption.PUBFROM                  as PUBLISHFROM,                               \r\n        OSOption.PUBTO                    as PUBLISHTO,                                 \r\n  \t    OSOption.OptionId                 as OptionENTITYID,                           \r\n  \t    OSOption.OptionType               as OptionENTITYTYPE,                         \r\n  \t    case OSOPTION.OSLEVEL                                                          \r\n  \t    when '" + OSIndependent + "' then 'ALL'                                            \r\n  \t    when '' then 'ALL'                                                             \r\n  \t    else OSOPTION.OSLEVEL end as OPTIONOS2                                         \r\n  \tfrom                                                                               \r\n  \t(                                                                                  \r\n    \tselect                                                                             \r\n    \t   Group.entityid                   as GroupId,                                    \r\n    \t   Group.entityType                 as GroupType,                                  \r\n    \t   Group.OKTOPUB                    as OKTOPUB,                                    \r\n    \t   Group.BRANDCD                    as BRANDCD,                                    \r\n    \t   System.entityid                  as SystemId,                                   \r\n    \t   System.entityType                as SystemType,                                 \r\n    \t   System.OSLEVEL                   as OSLEVEL                                     \r\n    \tfrom GBLI.WWCOMPATGROUP              as Group                                      \r\n    \tinner join opicm.relator            as SEOCGBDL on                                 \r\n    \t    SEOCGBDL.entity1type            = 'SEOCG' and                                  \r\n    \t    SEOCGBDL.entity2type            = 'LSEOBUNDLE' and                             \r\n    \t    SEOCGBDL.entity1id              = Group.entityid and                           \r\n    \t    SEOCGBDL.valto                  > current timestamp and                        \r\n    \t    SEOCGBDL.effto                  > current timestamp and                        \r\n    \t    SEOCGBDL.ENTERPRISE             = 'SG'                                         \r\n    \tinner join GBLI.WWCOMPATSYSTEM       as System on                                  \r\n    \t    System.entitytype               = 'LSEOBUNDLE' and                             \r\n    \t    System.entityid                 = SEOCGBDL.entity2id                           \r\n    \twhere                                                                              \r\n    \t    Group.entitytype                = 'SEOCG'                                      \r\n    \tunion all                                                                          \r\n    \tselect                                                                             \r\n    \t   Group.entityid                   as GroupId,                                    \r\n    \t   Group.entityType                 as GroupType,     \t                           \r\n    \t   Group.OKTOPUB                    as OKTOPUB,                                    \r\n    \t   Group.BRANDCD                    as BRANDCD,                                    \r\n    \t   System.entityid                  as SystemId,                                   \r\n    \t   System.entityType                as SystemType,                                 \r\n    \t   System.OSLEVEL                   as OSLEVEL                                     \r\n    \tfrom GBLI.WWCOMPATGROUP              as Group                                      \r\n    \tinner join opicm.relator            as SEOCGMDL on                                 \r\n    \t    SEOCGMDL.entity1type            = 'SEOCG' and                                  \r\n    \t    SEOCGMDL.entity2type            = 'MODEL'  and                                 \r\n    \t    SEOCGMDL.entity1id              = Group.entityid and                           \r\n    \t    SEOCGMDL.valto                  > current timestamp and                        \r\n    \t    SEOCGMDL.effto                  > current timestamp and                        \r\n    \t    SEOCGMDL.ENTERPRISE             = 'SG'                                         \r\n    \t inner join GBLI.WWCOMPATSYSTEM      as System on                                  \r\n    \t    System.entitytype               = 'MODEL' and                                  \r\n    \t    System.entityid                 = SEOCGMDL.entity2id                           \r\n    \t where                                                                             \r\n    \t    Group.entitytype                = 'SEOCG'                                      \r\n    \t union all                                                                         \r\n    \t select                                                                            \r\n    \t   Group.entityid                   as GroupId,                                    \r\n    \t   Group.entityType                 as GroupType,     \t                           \r\n    \t   Group.OKTOPUB                    as OKTOPUB,                                    \r\n    \t   Group.BRANDCD                    as BRANDCD,                                    \r\n    \t   System.entityid                  as SystemId,                                   \r\n    \t   System.entityType                as SystemType,                                 \r\n    \t   System.OSLEVEL                   as OSLEVEL                                     \r\n    \t from GBLI.WWCOMPATGROUP             as Group                                      \r\n    \t inner join opicm.relator           as SEOCGSEO on                                 \r\n    \t    SEOCGSEO.entity1type            = 'SEOCG' and                                  \r\n    \t    SEOCGSEO.entity2type            = 'WWSEO'  and                                 \r\n    \t    SEOCGSEO.entity1id              = Group.entityid and                           \r\n    \t    SEOCGSEO.valto                  > current timestamp and                        \r\n    \t    SEOCGSEO.effto                  > current timestamp and                        \r\n    \t    SEOCGSEO.ENTERPRISE             = 'SG'                                         \r\n    \t inner join GBLI.WWCOMPATSYSTEM      as System on                                  \r\n    \t    System.entitytype               = 'WWSEO' and                                  \r\n    \t    System.entityid                 = SEOCGSEO.entity2id                           \r\n    \t where                                                                             \r\n    \t    Group.entitytype                = 'SEOCG'                                      \r\n    )                                                                                   \r\n    as SystemGroup                                                                      \r\n    inner join opicm.relator              as GroupOS on                                 \r\n          GroupOS.entity1type               = 'SEOCG' and                               \r\n          GroupOS.entity2type               = 'SEOCGOS'  and                            \r\n          GroupOS.entity1id                 = SystemGroup.GroupId and                   \r\n          GroupOS.valto                     > current timestamp and                     \r\n          GroupOS.effto                     > current timestamp and                     \r\n          GroupOS.ENTERPRISE                = 'SG'                                      \r\n    inner join                                                                          \r\n    (                                                                                   \r\n    \t select                                                                            \r\n    \t     OS.entityid                      as OSId,                                     \r\n    \t     OS.entitytype                    as OSType,                                   \r\n    \t     OS.OS                            as OS,                                       \r\n    \t     OSOption.entityid                as OSOPTIONId,                               \r\n    \t     OSOption.entitytype              as OSOPTIONType,                             \r\n    \t     OSOP.COMPATPUBFLG              as COMPATPUBFLG,                               \r\n    \t     OSOP.RELTYPE                   as RELTYPE,                                    \r\n    \t     OSOP.PUBFROM                   as PUBFROM,                                    \r\n    \t     OSOP.PUBTO                     as PUBTO,                                      \r\n    \t     Option.entityid                  as OptionId,                                 \r\n    \t     Option.entitytype                as OptionType,                               \r\n    \t     Option.OSLEVEL                   as OSLEVEL                                   \r\n    \t from GBLI.WWCOMPATOS                as OS                                         \r\n    \t inner join opicm.relator           as OSOption on                                 \r\n    \t     OSOption.entity1type            = 'SEOCGOS' and                               \r\n    \t     OSOption.entity2type            = 'LSEOBUNDLE'  and                           \r\n    \t     OSOption.entity1id              = OS.entityid and                             \r\n    \t     OSOption.valto                  > current timestamp and                       \r\n    \t     OSOption.effto                  > current timestamp and                       \r\n    \t     OSOption.ENTERPRISE             = 'SG'                                        \r\n    \t inner join GBLI.WWCOMPATOSOP        as OSOP  on                                   \r\n    \t     OSOP.entitytype                 = OSOption.entitytype and                     \r\n    \t     OSOP.entityid                   = OSOption.entityid                           \r\n    \t inner join GBLI.WWCOMPATOPTION      as Option on                                  \r\n    \t     Option.entitytype               = 'LSEOBUNDLE' and                            \r\n    \t     Option.entityid                 = OSOption.entity2id                          \r\n    \t where                                                                             \r\n    \t     OS.entitytype                   = 'SEOCGOS'                                   \r\n    \t union all                                                                         \r\n    \t select                                                                            \r\n    \t     OS.entityid                    as OSId,                                       \r\n    \t     OS.entitytype                  as OSType,                                     \r\n    \t     OS.OS                          as OS,                                         \r\n    \t     OSOption.entityid              as OSOPTIONId,                                 \r\n    \t     OSOption.entitytype            as OSOPTIONType,                               \r\n    \t     OSOP.COMPATPUBFLG              as COMPATPUBFLG,                               \r\n    \t     OSOP.RELTYPE                   as RELTYPE,                                    \r\n    \t     OSOP.PUBFROM                   as PUBFROM,                                    \r\n    \t     OSOP.PUBTO                     as PUBTO,                                      \r\n    \t     Option.entityid                as OptionId,                                   \r\n    \t     Option.entitytype              as OptionType,                                 \r\n    \t     Option.OSLEVEL                 as OSLEVEL                                     \r\n    \t from GBLI.WWCOMPATOS                as OS                                         \r\n    \t inner join opicm.relator           as OSOption on                                 \r\n    \t    OSOption.entity1type            = 'SEOCGOS' and                                \r\n    \t    OSOption.entity2type            = 'WWSEO'  and                                 \r\n    \t    OSOption.entity1id              = OS.entityid and                              \r\n    \t    OSOption.valto                  > current timestamp and                        \r\n    \t    OSOption.effto                  > current timestamp and                        \r\n    \t    OSOption.ENTERPRISE             = 'SG'                                         \r\n    \t inner join GBLI.WWCOMPATOSOP        as OSOP  on                                   \r\n    \t    OSOP.entitytype                 = OSOption.entitytype and                      \r\n    \t    OSOP.entityid                   = OSOption.entityid                            \r\n    \t inner join GBLI.WWCOMPATOPTION      as Option on                                  \r\n    \t    Option.entitytype               = 'WWSEO' and                                  \r\n    \t    Option.entityid                 = OSOption.entity2id                           \r\n    \t where                                                                             \r\n    \t    OS.entitytype                   = 'SEOCGOS'                                    \r\n    \t                                                                                   \r\n    \t ) as OSOption on                                                                  \r\n    \t    OSOption.OSType                 = 'SEOCGOS' and                                \r\n    \t    OSOption.OSId                   = GroupOS.entity2id                            \r\n  ) as temp                                                                             \r\n  where (                                                                               \r\n    temp.OS2='ALL'                                                                      \r\n  ) and temp.OKTOPUB!='DEL' and temp.COMPATIBILITYPUBLISHINGFLAG!='DEL'                 \r\n  with ur                                                                               \r\n";
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WWCOMPATIDLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */