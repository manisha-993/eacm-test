package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

//$Log: WWCOMPATIDLABR.java,v $
//Revision 1.9  2012/12/12 05:58:47  wangyulo
//the wwcompat IDL change base on the document of BH FS ABR Catalog DB Compatibility Gen 20121030.doc
//
//Revision 1.8  2012/04/24 14:16:53  wangyulo
//move "insert into gbli.wwtechcompat" in the java code to aix script
//
//Revision 1.7  2011/09/19 07:41:18  guobin
//update the wwcompat IDL with the temp table and index to generata the data to price table, so that it can much improve the performance.
//
//Revision 1.6  2011/05/04 07:30:42  guobin
//change the comments
//
//Revision 1.5  2011/04/25 07:46:54  guobin
//add the check for the attribute of OS and OSLEVEL
//
//Revision 1.4  2011/04/22 07:26:18  guobin
//change the sql and check os
//
//Revision 1.3  2011/04/20 08:24:47  guobin
//change the sql and add the comments
//
//Revision 1.2  2011/04/14 08:52:34  guobin
//update the sql of WWCOMPATIDL
//
//Revision 1.1  2011/04/01 09:20:39  guobin
//add for the WWCOMPAT IDL
//
//Init for
//An IDL of this table (WWTECHCOMPAT) is required.
//
//	The section on Insert Additions covers the seven unique queries that should be used to populate this table from the PRICE schema.
//
//	Note:  TimeOfIDL is the time that they IDL is started. This can be set in a property file for the ABR.


public class WWCOMPATIDLABR {

	//Class variables
    private static Properties c_props = null;    
    private static Connection conOPICM = null;
    //get the parameter value from the properties 
    private static Properties WWCOMPAT_props = null;
    private static String OSIndependent;
    private static String WWTable;
    private static int Db2Version;
    //get the parameter value end
    
	public static void main(String[] args) {
		
		System.out.println("-----------------------------------------------------------");		
		Date d=new Date();
		long starttime = System.currentTimeMillis();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String StartDate = sf.format(d);		
		readPropertyFile();
		initWWCOMPAT();
		WWCOMPATIDLABR WWCOMPAT = new WWCOMPATIDLABR();
		//get connect
		WWCOMPAT.getConnect();		
		WWCOMPAT.mainline();
		try {
			WWCOMPAT.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Success!!");
		System.out.println("-----------------------------------------------------------");
		d = new Date();
		long endttime = System.currentTimeMillis();
		String EndDate = sf.format(d);
		System.out.println("version 2");
		System.out.println("Start Time= "+StartDate);
		System.out.println("End   Time= "+EndDate);
		System.out.println("Total Time= "+(endttime-starttime) +" milliseconds");
		System.out.println("-----------------------------------------------------------");
	}
	/**
	 * read the middleware.server.properties
	 * and read WWCOMPAT.properties
	 */
	private static void readPropertyFile()  {
		String filePath = System.getProperty("user.dir") + "/middleware.server.properties";
		String fileWWCOMPATPath = System.getProperty("user.dir") + "/wwcompat.properties";
		InputStream inProperties = null;
		try {
			try {
				inProperties = new BufferedInputStream(new FileInputStream(filePath));
				c_props = new Properties();
				c_props.load(inProperties);
				
				inProperties = new BufferedInputStream(new FileInputStream(fileWWCOMPATPath));
				WWCOMPAT_props = new Properties();
				WWCOMPAT_props.load(inProperties);
				
			} finally {
				inProperties.close();
			}

		} catch (IOException e) {
			System.out.println("Unable to loadProperties for " + filePath + " " + e);
			System.exit(1);
		}
	}
	/**
	 * init the parameter of the WWCOMPATIDLABR
	 */
	private static void initWWCOMPAT() {
		OSIndependent = WWCOMPAT_props.getProperty("OSIndependent");
		System.out.println("OSIndependent="+OSIndependent);
		WWTable = WWCOMPAT_props.getProperty("WWTable");
		Db2Version = Integer.parseInt(WWCOMPAT_props.getProperty("Db2Version"));
	}
	/**
	 * get the DB connection
	 */
	private void getConnect(){
		try {
			Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
			//Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
			String pdh_database_url = c_props.getProperty("pdh_database_url");
			String pdh_database_user = c_props.getProperty("pdh_database_user");
			String pdh_database_password = c_props.getProperty("pdh_database_password");

			setConOPICM(DriverManager.getConnection(pdh_database_url,pdh_database_user, pdh_database_password));
			getConOPICM().setAutoCommit(true);
		} catch (Exception ex) {
			System.out.println(new Date() + ":connection:err: "	+ ex.getMessage());
			System.exit(1);
		}
	}
	/**
	 * The section on Insert Additions covers the seven unique queries that should be used to populate this table from the PRICE schema.
	 * Note:  TimeOfIDL is the time that they IDL is started. This can be set in a property file for the ABR.
	 */
	private void mainline(){
		System.out.println("Initializing for WWCOMPATIDLABR version2" );	
		try {
			/**
			 * get the Updated time and delete the temp table
			 */			
			clearTempTable();
			
		    /**
		     * create 5 temp tables
		     */
			createTempTable();
			/**
			 * insert data into temp table
			 * create indexes for the temp table for the query
			 * then insert into WWCOMPATtemp table select from the temp table
			 */
			insertDataIntoTempTable();
			/**
			 * export and import data and clear temp table
			 */
			processData();
			/**
			 * drop temp indexes and tables
			 */
			dropTempIndexAndTable();
		}
		catch (SQLException ex) {
		      System.out.println(new Date() + ":general-error" + ex.getMessage());
		      ex.printStackTrace();
		      try {
		    	  dropTempTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      System.exit(1);
		}
	}
	/**
	 * Drop temp table and index
	 * @throws SQLException
	 */
	private void dropTempIndexAndTable() throws SQLException {
		Statement ddlstmt = conOPICM.createStatement();
		String sql ="";
		int iResult =0;
		/**
		 * drop temp index
		 */
		//1.SYSTEM_INDEX 
		sql =" drop index \"GBLI    \".\"SYSTEM_INDEX\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop SYSTEM_INDEX index error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop SYSTEM_INDEX index success !! \r\n drop sql =" +sql);
		}
        //2.GROUP_INDEX 
		sql =" drop index \"GBLI    \".\"GROUP_INDEX\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop GROUP_INDEX index error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop GROUP_INDEX index success !! \r\n drop sql =" +sql);
		}
        //3.OSOP_INDEX 
		sql =" drop index \"GBLI    \".\"OSOP_INDEX\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop OSOP_INDEX index error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop OSOP_INDEX index success !! \r\n drop sql =" +sql);
		}
        //4.OS_INDEX 
		sql =" drop index \"GBLI    \".\"OS_INDEX\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop OS_INDEX index error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop OS_INDEX index success !! \r\n drop sql =" +sql);
		}
        //5.OPTION_INDEX 
		sql =" drop index \"GBLI    \".\"OPTION_INDEX\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop OPTION_INDEX index error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop OPTION_INDEX index success !! \r\n drop sql =" +sql);
		}
		
		/**
		 * drop temp table
		 */
		//1.WWCOMPATSYSTEM 
		sql =" drop table \"GBLI    \".\"WWCOMPATSYSTEM\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATSYSTEM temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATSYSTEM temp table success !! \r\n drop sql =" +sql);
		}
		//2.WWCOMPATGROUP 
		sql =" drop table \"GBLI    \".\"WWCOMPATGROUP\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATGROUP temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATGROUP temp table success !! \r\n drop sql =" +sql);
		}
        //3.WWCOMPATOSOP 
		sql =" drop table \"GBLI    \".\"WWCOMPATOSOP\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATOSOP temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATOSOP temp table success !! \r\n drop sql =" +sql);
		}
        //4.WWCOMPATOS 
		sql =" drop table \"GBLI    \".\"WWCOMPATOS\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATOS temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATOS temp table success !! \r\n drop sql =" +sql);
		}
        //5.WWCOMPATOPTION 
		sql =" drop table \"GBLI    \".\"WWCOMPATOPTION\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATOPTION temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATOPTION temp table success !! \r\n drop sql =" +sql);
		}
	}
	
	/**
	 * drop temp table when the sql error
	 * @throws SQLException
	 */
	private void dropTempTable() throws SQLException {
		Statement ddlstmt = conOPICM.createStatement();
		String sql ="";
		int iResult =0;
		
		/**
		 * drop temp table
		 */
		//1.WWCOMPATSYSTEM 
		sql =" drop table \"GBLI    \".\"WWCOMPATSYSTEM\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATSYSTEM temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATSYSTEM temp table success !! \r\n drop sql =" +sql);
		}
		//2.WWCOMPATGROUP 
		sql =" drop table \"GBLI    \".\"WWCOMPATGROUP\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATGROUP temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATGROUP temp table success !! \r\n drop sql =" +sql);
		}
        //3.WWCOMPATOSOP 
		sql =" drop table \"GBLI    \".\"WWCOMPATOSOP\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATOSOP temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATOSOP temp table success !! \r\n drop sql =" +sql);
		}
        //4.WWCOMPATOS 
		sql =" drop table \"GBLI    \".\"WWCOMPATOS\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATOS temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATOS temp table success !! \r\n drop sql =" +sql);
		}
        //5.WWCOMPATOPTION 
		sql =" drop table \"GBLI    \".\"WWCOMPATOPTION\"" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("drop WWCOMPATOPTION temp table error!!  \r\n drop sql =" +sql);			
		}else{
			System.out.println("drop WWCOMPATOPTION temp table success !! \r\n drop sql =" +sql);
		}
	}
	/**
	 * insert data to temp table and wwcompat table
	 * @throws SQLException
	 */
	private void insertDataIntoTempTable() throws SQLException {
		Statement ddlstmt = conOPICM.createStatement();
		int iResult =0;
		//1.INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL
		iResult = ddlstmt.executeUpdate(INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL);
		if(iResult<0){
			System.out.println("insert data into WWCOMPATSYSTEM table error!!    \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL);			
		}else{
			System.out.println("insert data into WWCOMPATSYSTEM table success !! rows="+iResult+" \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL);
		}
		//2.INSERT_INTO_GBLI_WWCOMPATGROUP_SQL
		iResult = ddlstmt.executeUpdate(INSERT_INTO_GBLI_WWCOMPATGROUP_SQL);
		if(iResult<0){
			System.out.println("insert data into WWCOMPATGROUP table error!!    \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATGROUP_SQL);			
		}else{
			System.out.println("insert data into WWCOMPATGROUP table success !! rows="+iResult+" \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATGROUP_SQL);
		}
		//3.INSERT_INTO_GBLI_WWCOMPATOS_SQL
		iResult = ddlstmt.executeUpdate(INSERT_INTO_GBLI_WWCOMPATOS_SQL);
		if(iResult<0){
			System.out.println("insert data into WWCOMPATOS table error!!  \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATOS_SQL);			
		}else{
			System.out.println("insert data into WWCOMPATOS table success !! rows="+iResult+" \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATOS_SQL);
		}
		//4.INSERT_INTO_GBLI_WWCOMPATOSOP_SQL
		iResult = ddlstmt.executeUpdate(INSERT_INTO_GBLI_WWCOMPATOSOP_SQL);
		if(iResult<0){
			System.out.println("insert data into WWCOMPATOSOP table error!!  \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATOSOP_SQL);			
		}else{
			System.out.println("insert data into WWCOMPATOSOP table success !! rows="+iResult+" \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATOSOP_SQL);
		}
		//5.INSERT_INTO_GBLI_WWCOMPATOPTION_SQL
		iResult = ddlstmt.executeUpdate(INSERT_INTO_GBLI_WWCOMPATOPTION_SQL);
		if(iResult<0){
			System.out.println("insert data into WWCOMPATSYSTEM table error!!  \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATOPTION_SQL);			
		}else{
			System.out.println("insert data into WWCOMPATSYSTEM table success !! rows="+iResult+" \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATOPTION_SQL);
		}
		/**
		 * create index
		 */
		
		//1.SYSTEM_INDEX		
		String sql=" CREATE INDEX \"GBLI    \".\"SYSTEM_INDEX\" ON \"GBLI    \".\"WWCOMPATSYSTEM\" \r\n" +
	    	"  		(\"ENTITYTYPE\" ASC,                                                   \r\n" +
	    	"  		 \"ENTITYID\" ASC,                                                     \r\n" +
	    	"  		 \"OSLEVEL\" ASC)                                                      \r\n" ;        
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create SYSTEM_INDEX temp Index error!!    \r\n create sql =" +sql);		
		}else{
			System.out.println("Create SYSTEM_INDEX temp Index success !! \r\n create sql =" +sql);
		}
		
         //2.GROUP_INDEX		
		sql=" CREATE INDEX \"GBLI    \".\"GROUP_INDEX\" ON \"GBLI    \".\"WWCOMPATGROUP\"   \r\n" +
	    	"  		(\"ENTITYTYPE\" ASC,                                                   \r\n" +
	    	"  		 \"ENTITYID\" ASC,                                                     \r\n" +
	    	"  		 \"OKTOPUB\" ASC)                                                      \r\n" ;        
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create GROUP_INDEX temp Index error!!    \r\n create sql =" +sql);		
		}else{
			System.out.println("Create GROUP_INDEX temp Index success !! \r\n create sql =" +sql);
		}
		
        //3.OSOP_INDEX		
		sql=" CREATE INDEX \"GBLI    \".\"OSOP_INDEX\" ON \"GBLI    \".\"WWCOMPATOSOP\"     \r\n" +
	    	"  		(\"ENTITYTYPE\" ASC,                                                   \r\n" +
	    	"  		 \"ENTITYID\" ASC,                                                     \r\n" +
	    	"  		 \"COMPATPUBFLG\" ASC)                                                 \r\n" ;        
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create OSOP_INDEX temp Index error!!    \r\n create sql =" +sql);		
		}else{
			System.out.println("Create OSOP_INDEX temp Index success !! \r\n create sql =" +sql);
		}
		
        //4.OS_INDEX		
		sql=" CREATE INDEX \"GBLI    \".\"OS_INDEX\" ON \"GBLI    \".\"WWCOMPATOS\"         \r\n" +
	    	"  		(\"ENTITYTYPE\" ASC,                                                   \r\n" +
	    	"  		 \"ENTITYID\" ASC,                                                     \r\n" +
	    	"  		 \"OS\" ASC)                                                           \r\n" ;        
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create OS_INDEX temp Index error!!    \r\n create sql =" +sql);		
		}else{
			System.out.println("Create OS_INDEX temp Index success !! \r\n create sql =" +sql);
		}
		
	    //5.OPTION_INDEX		
		sql=" CREATE INDEX \"GBLI    \".\"OPTION_INDEX\" ON \"GBLI    \".\"WWCOMPATOPTION\" \r\n" +
	    	"  		(\"ENTITYTYPE\" ASC,                                                   \r\n" +
	    	"  		 \"ENTITYID\" ASC,                                                     \r\n" +
	    	"  		 \"OSLEVEL\" ASC)                                                      \r\n" ;        
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create OPTION_INDEX temp Index error!!    \r\n create sql =" +sql);		
		}else{
			System.out.println("Create OPTION_INDEX temp Index success !! \r\n create sql =" +sql);
		}
		
		
		//6.INSERT_INTO_GBLI_WWCOMPATTEMP_MODELCG_SQL
//		iResult = ddlstmt.executeUpdate(INSERT_INTO_GBLI_WWCOMPATTEMP_MODELCG_SQL);
//		if(iResult<0){
//			System.out.println("insert data into GBLI_WWCOMPATTEMP table for MODELCG error!!  \r\n insert sql =" 
//					+INSERT_INTO_GBLI_WWCOMPATTEMP_MODELCG_SQL);			
//		}else{
//			System.out.println("insert data into GBLI_WWCOMPATTEMP table for MODELCG success !! rows="+iResult+" \r\n insert sql =" 
//					+INSERT_INTO_GBLI_WWCOMPATTEMP_MODELCG_SQL);
//		}
		
        //7.INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL
		iResult = ddlstmt.executeUpdate(INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL);
		if(iResult<0){
			System.out.println("insert data into GBLI_WWCOMPATTEMP table for SEOCG error!!  \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL);			
		}else{
			System.out.println("insert data into GBLI_WWCOMPATTEMP table for SEOCG success !! rows="+iResult+" \r\n insert sql =" 
					+INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL);
		}
		
	}
	/**
	 * create temp table
	 * @throws SQLException
	 */
	private void createTempTable() throws SQLException {
		Statement ddlstmt = conOPICM.createStatement();
		String sql ="";
		int iResult =0;
		/**
		 * create temp table
		 */
		//1.WWCOMPATSYSTEM 
		sql =" CREATE TABLE \"GBLI    \".\"WWCOMPATSYSTEM\"  (  \r\n" +
             "  \"ENTITYTYPE\" VARCHAR(32) , \r\n" +
             "  \"ENTITYID\" INTEGER , \r\n" +
             "  \"OSLEVEL\" VARCHAR(32) \r\n" +
             "  ) IN \"TSPACE08\"   \r\n" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create WWCOMPATSYSTEM temp table error!!  \r\n create sql =" +sql);
			
		}else{
			System.out.println("Create WWCOMPATSYSTEM temp table success !! \r\n create sql =" +sql);
		}
		//2.WWCOMPATGROUP 
		sql=" CREATE TABLE \"GBLI    \".\"WWCOMPATGROUP\"  (  \r\n" +
        	"  \"ENTITYTYPE\" VARCHAR(32) ,                  \r\n" +
        	"  \"ENTITYID\" INTEGER ,                        \r\n" +
        	"  \"OKTOPUB\" VARCHAR(32),                      \r\n" +
        	"  \"BRANDCD\" VARCHAR(32)                       \r\n" +
        	"  ) IN \"TSPACE08\"                             \r\n" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create WWCOMPATGROUP temp table error!!  \r\n create sql =" +sql);		
		}else{
			System.out.println("Create WWCOMPATGROUP temp table success !! \r\n create sql =" +sql);
		}
		//3.WWCOMPATGROUP 		
		sql=" CREATE TABLE \"GBLI    \".\"WWCOMPATOSOP\"  (  \r\n" +
	    	"  \"ENTITYTYPE\" VARCHAR(32) ,                 \r\n" +
	    	"  \"ENTITYID\" INTEGER ,                       \r\n" +
	    	"  \"COMPATPUBFLG\" VARCHAR(32),                \r\n" +
	    	"  \"PUBFROM\" VARCHAR(32),                     \r\n" +
	    	"  \"PUBTO\" VARCHAR(32),                       \r\n" +
	    	"  \"RELTYPE\" VARCHAR(32)                      \r\n" +
	    	"  ) IN \"TSPACE08\"                            \r\n" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create WWCOMPATOSOP temp table error!!  \r\n create sql =" +sql);		
		}else{
			System.out.println("Create WWCOMPATOSOP temp table success !! \r\n create sql =" +sql);
		}
		//4.WWCOMPATOS 		
		sql=" CREATE TABLE \"GBLI    \".\"WWCOMPATOS\"  (  \r\n" +
	    	"  \"ENTITYTYPE\" VARCHAR(32) ,               \r\n" +
	    	"  \"ENTITYID\" INTEGER ,                     \r\n" +
	    	"  \"OS\" VARCHAR(32)                         \r\n" +
	    	"  ) IN \"TSPACE08\"                          \r\n" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create WWCOMPATOS temp table error!!  \r\n create sql =" +sql);		
		}else{
			System.out.println("Create WWCOMPATOS temp table success !! \r\n create sql =" +sql);
		}
		//5.WWCOMPATOPTION 		
		sql=" CREATE TABLE \"GBLI    \".\"WWCOMPATOPTION\"  (  \r\n" +
	    	"  \"ENTITYTYPE\" VARCHAR(32) ,                   \r\n" +
	    	"  \"ENTITYID\" INTEGER ,                         \r\n" +
	    	"  \"OSLEVEL\" VARCHAR(32)                        \r\n" +
	    	"  ) IN \"TSPACE08\"                              \r\n" ;
		iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("Create WWCOMPATOPTION temp table error!!  \r\n create sql =" +sql);		
		}else{
			System.out.println("Create WWCOMPATOPTION temp table success !! \r\n create sql =" +sql);
		}
		
		ddlstmt.close();
		
	}
	/**
     * import null or delete wwtable and then insert temp table data into wwtable
     * @throws SQLException
     */
	private void processData() throws SQLException {
		if(Db2Version>7){
			importData();			
		}else{
			insertData();
		}		
	}
	/**
	 * delete wwtable and then insert temp table data into wwtable
	 * @throws SQLException
	 */
	private void insertData() throws SQLException {
		Statement ddlstmt = conOPICM.createStatement();
		String sql ="delete from " + WWTable;
		int iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("delete " + WWTable + " error!!");
		}else{
			System.out.println("delete " + WWTable + " success rows="+iResult);
		}
		
		
		//move the insert into sql to the script of wwcompat-aix
//		sql ="insert into " + WWTable + " select * from gbli.WWCOMPATTEMP";
//		iResult = ddlstmt.executeUpdate(sql);
//		if(iResult<0){
//			System.out.println("insert " + WWTable + " error!!");
//		}else{
//			System.out.println("insert " + WWTable + " success rows="+iResult);
//		}
		ddlstmt.close();		
	}
    /**
     * import null into wwtable and then insert temp table data into wwtable
     * @throws SQLException
     */
	private void importData() throws SQLException {
		String sqlAdmin = "CALL SYSPROC.ADMIN_CMD(?)";
		CallableStatement callStmt = conOPICM.prepareCall(sqlAdmin);		
		String sqlImport ="import from /dev/null of del replace into " + WWTable; 			
		callStmt.setString(1, sqlImport);
		callStmt.execute();
		callStmt.close();
		System.out.println("import null into " + WWTable + " table!!");
		
		///move the insert into sql to the script of wwcompat-aix
//		Statement ddlstmt = conOPICM.createStatement();
//		String sql ="insert into " + WWTable + " select * from gbli.WWCOMPATTEMP";
//		int iResult = ddlstmt.executeUpdate(sql);
//		if(iResult<0){
//			System.out.println("insert " + WWTable + " error!!");
//		}else{
//			System.out.println("insert " + WWTable + " success rows="+iResult);
//		}
//		ddlstmt.close();		
	}
	/**
	 * clear the temp for the IDL
	 * @throws SQLException
	 */
	private void clearTempTable() throws SQLException{
		if(Db2Version>7){
			importTempTable();
		}else{
			deleteTempTable();
		}
	}
	
	/**
	 * @throws SQLException
	 */
	private void importTempTable() throws SQLException {
		String sqlAdmin = "CALL SYSPROC.ADMIN_CMD(?)";
		CallableStatement callStmt = conOPICM.prepareCall(sqlAdmin);
		String sqlImport ="import from /dev/null of del replace into gbli.WWCOMPATTEMP"; 			
		callStmt.setString(1, sqlImport);
		callStmt.execute();
		ResultSet rs1 = callStmt.getResultSet();	      
		// retrieving the resultset  
		int rows_read;
		int rows_skipped;
		int rows_loaded;
		int rows_rejected;
		int rows_deleted;
		int rows_committed;
		if( rs1.next())
		{ 
		  // retrieve the no of rows read
		  rows_read = rs1.getInt(1);
		  // retrieve the no of rows skipped
		  rows_skipped = rs1.getInt(2);
		  // retrieve the no of rows loaded
		  rows_loaded = rs1.getInt(3);
		  // retrieve the no of rows rejected
		  rows_rejected = rs1.getInt(4);
		  // retrieve the no of rows deleted
		  rows_deleted = rs1.getInt(5);
		  // retrieve the no of rows committed
		  rows_committed = rs1.getInt(6);

		  // retrieve the select stmt for message retrival 
		  // containing SYSPROC.ADMIN_GET_MSGS
		  String msg_retrieval = rs1.getString(7);
  
		  // retrive the stmt for message cleanup
		  // containing CALL of SYSPROC.ADMIN_REMOVE_MSGS
		  String msg_removal = rs1.getString(8);
     
		  // Displaying the resultset
		  System.out.print("\nTotal number of rows read : ");
		  System.out.println(rows_read);
		  System.out.print("Total number of rows skipped   : ");
		  System.out.println( rows_skipped);
		  System.out.print("Total number of rows loaded    : ");
		  System.out.println(rows_loaded);
		  System.out.print("Total number of rows rejected  : "); 
		  System.out.println(rows_rejected);
		  System.out.print("Total number of rows deleted   : "); 
		  System.out.println(rows_deleted);
		  System.out.print("Total number of rows committed : "); 
		  System.out.println(rows_committed);
		  System.out.print("SQL for retrieving the messages: "); 
		  System.out.println(msg_retrieval); 
		  System.out.print("SQL for removing the messages  : "); 
		  System.out.println(msg_removal);
		}
		
		callStmt.close();
	}

	

	/**
	 * delete the temp table
	 * @throws SQLException
	 */
	private void deleteTempTable() throws SQLException {
		Statement ddlstmt = conOPICM.createStatement();
		String sql ="delete from gbli.WWCOMPATTEMP";
		int iResult = ddlstmt.executeUpdate(sql);
		if(iResult<0){
			System.out.println("delete error!!");
		}else{
			System.out.println("delete success rows="+iResult);
		}
		ddlstmt.close();
	}

	
	/**
	 * set connect
	 * @param _conOPICM
	 */
	private static void setConOPICM(Connection _conOPICM) {
	    conOPICM = _conOPICM;
	}
	/**
	 * get connection
	 * @return
	 */
	Connection getConOPICM() {
	    return conOPICM;
	}
	/**
	 * close connection
	 * @throws java.sql.SQLException
	 */
	void closeConnection() throws java.sql.SQLException
    {
		Connection connection = conOPICM;
        if(connection != null) {
            try {
                connection.rollback();
            }
            catch (Throwable ex) {
                System.out.println("XMLMQAdapter.closeConnection(), unable to rollback. "+ ex);
            }
            finally {
                connection.close();
                connection = null;
            }
        }
    }
	/**
	 * LSEOBUNDLE ,MODEL, WWSEO use empty as OSLEVEL
	 */
	private String INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL =
        "insert into GBLI.WWCOMPATSYSTEM(entityid,entitytype,OSLEVEL)       \r\n" +
        "  select entityid,entitytype,'' as OSLEVEL                         \r\n" +
        "  from opicm.flag                                                  \r\n" +
        "  where                                                            \r\n" +
        "      entitytype in('LSEOBUNDLE','WWSEO','MODEL') and              \r\n" +
        "      ENTERPRISE='SG' and                                          \r\n" +
        "      valto > current timestamp and                                \r\n" +
        "      effto > current timestamp and                                \r\n" +
        "      ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
        "      attributecode = 'STATUS'                                     \r\n" +
        "  with ur                                                          \r\n" ;
	
//	private String INSERT_INTO_GBLI_WWCOMPATSYSTEM_SQL =
//        "insert into GBLI.WWCOMPATSYSTEM(entityid,entitytype,OSLEVEL)        \r\n" +
//        "  select entityid,entitytype,OSLEVEL from                          \r\n" +
//        "  (                                                                \r\n" +
//        "    select * from                                                  \r\n" +
//        "    (                                                              \r\n" +
//        "      select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL   \r\n" +
//        "      from opicm.flag as A left join opicm.flag B                  \r\n" +
//        "      on A.ENTERPRISE = B.ENTERPRISE and                           \r\n" +
//        "      A.entityid = B.entityid and                                  \r\n" +
//        "      A.entitytype = B.entitytype                                  \r\n" +
//        "      where                                                        \r\n" +
//        "      A.entitytype ='LSEOBUNDLE' and                               \r\n" +
//        "      A.ENTERPRISE='SG' and                                        \r\n" +
//        "      B.ENTERPRISE='SG' and                                        \r\n" +
//        "      A.valto > current timestamp and                              \r\n" +
//        "      A.effto > current timestamp and                              \r\n" +
//        "      B.valto > current timestamp and                              \r\n" +
//        "      B.effto > current timestamp and                              \r\n" +
//        "      B.attributecode = 'OSLEVEL' and                              \r\n" +
//        "      A.ATTRIBUTEVALUE in('0040','0020') and                       \r\n" +
//        "      A.attributecode = 'STATUS'                                   \r\n" +
//        "      union all                                                    \r\n" +
//        "      select C.entityid, C.entitytype,'' as OSLEVEL                \r\n" +
//        "      from opicm.flag as C                                         \r\n" +
//        "      where                                                        \r\n" +
//        "      C.entitytype ='LSEOBUNDLE' and                               \r\n" +
//        "      C.ENTERPRISE='SG' and                                        \r\n" +
//        "      C.valto > current timestamp and                              \r\n" +
//        "      C.effto > current timestamp and                              \r\n" +
//        "      C.ATTRIBUTEVALUE in('0040','0020') and                       \r\n" +
//        "      C.attributecode = 'STATUS' and                               \r\n" +
//        "      C.entityid not in(select A.entityid                          \r\n" +
//        "      from opicm.flag as A left join opicm.flag B                  \r\n" +
//        "      on A.ENTERPRISE = B.ENTERPRISE and                           \r\n" +
//        "      A.entityid = B.entityid and                                  \r\n" +
//        "      A.entitytype = B.entitytype                                  \r\n" +
//        "      where                                                        \r\n" +
//        "      A.entitytype ='LSEOBUNDLE' and                               \r\n" +
//        "      A.ENTERPRISE='SG' and                                        \r\n" +
//        "      B.ENTERPRISE='SG' and                                        \r\n" +
//        "      A.valto > current timestamp and                              \r\n" +
//        "      A.effto > current timestamp and                              \r\n" +
//        "      B.valto > current timestamp and                              \r\n" +
//        "      B.effto > current timestamp and                              \r\n" +
//        "      B.attributecode = 'OSLEVEL' and                              \r\n" +
//        "      A.ATTRIBUTEVALUE in('0040','0020') and                       \r\n" +
//        "      A.attributecode = 'STATUS')                                  \r\n" +
//        "    ) as LSEOBUNDLE                                                \r\n" +
//        "    union all                                                      \r\n" +
//        "    select * from                                                  \r\n" +
//        "    (                                                              \r\n" +
//        "      select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL   \r\n" +
//        "      from opicm.flag as A left join opicm.flag B                  \r\n" +
//        "      on A.ENTERPRISE = B.ENTERPRISE and                           \r\n" +
//        "      A.entityid = B.entityid and                                  \r\n" +
//        "      A.entitytype = B.entitytype                                  \r\n" +
//        "      where                                                        \r\n" +
//        "      A.entitytype ='WWSEO' and                                    \r\n" +
//        "      A.ENTERPRISE='SG' and                                        \r\n" +
//        "      B.ENTERPRISE='SG' and                                        \r\n" +
//        "      A.valto > current timestamp and                              \r\n" +
//        "      A.effto > current timestamp and                              \r\n" +
//        "      B.valto > current timestamp and                              \r\n" +
//        "      B.effto > current timestamp and                              \r\n" +
//        "      B.attributecode = 'OS' and                                   \r\n" +
//        "      A.ATTRIBUTEVALUE in('0040','0020') and                       \r\n" +
//        "      A.attributecode = 'STATUS'                                   \r\n" +
//        "      union all                                                    \r\n" +
//        "      select C.entityid, C.entitytype,'' as OSLEVEL                \r\n" +
//        "      from opicm.flag as C                                         \r\n" +
//        "      where                                                        \r\n" +
//        "      C.entitytype ='WWSEO' and                                    \r\n" +
//        "      C.ENTERPRISE='SG' and                                        \r\n" +
//        "      C.valto > current timestamp and                              \r\n" +
//        "      C.effto > current timestamp and                              \r\n" +
//        "      C.ATTRIBUTEVALUE in('0040','0020') and                       \r\n" +
//        "      C.attributecode = 'STATUS' and                               \r\n" +
//        "      C.entityid not in(select A.entityid                          \r\n" +
//        "      from opicm.flag as A left join opicm.flag B                  \r\n" +
//        "      on A.ENTERPRISE = B.ENTERPRISE and                           \r\n" +
//        "      A.entityid = B.entityid and                                  \r\n" +
//        "      A.entitytype = B.entitytype                                  \r\n" +
//        "      where                                                        \r\n" +
//        "      A.entitytype ='WWSEO' and                                    \r\n" +
//        "      A.ENTERPRISE='SG' and                                        \r\n" +
//        "      B.ENTERPRISE='SG' and                                        \r\n" +
//        "      A.valto > current timestamp and                              \r\n" +
//        "      A.effto > current timestamp and                              \r\n" +
//        "      B.valto > current timestamp and                              \r\n" +
//        "      B.effto > current timestamp and                              \r\n" +
//        "      B.attributecode = 'OS' and                                   \r\n" +
//        "      A.ATTRIBUTEVALUE in('0040','0020') and                       \r\n" +
//        "      A.attributecode = 'STATUS')                                  \r\n" +
//        "    ) as WWSEO                                                     \r\n" +
//        "    union all                                                      \r\n" +
//        "    select * from                                                  \r\n" +
//        "    (                                                              \r\n" +
//        "      select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL   \r\n" +
//        "      from opicm.flag as A left join opicm.flag B                  \r\n" +
//        "      on A.ENTERPRISE = B.ENTERPRISE and                           \r\n" +
//        "      A.entityid = B.entityid and                                  \r\n" +
//        "      A.entitytype = B.entitytype                                  \r\n" +
//        "      where                                                        \r\n" +
//        "      A.entitytype ='MODEL' and                                    \r\n" +
//        "      A.ENTERPRISE='SG' and                                        \r\n" +
//        "      B.ENTERPRISE='SG' and                                        \r\n" +
//        "      A.valto > current timestamp and                              \r\n" +
//        "      A.effto > current timestamp and                              \r\n" +
//        "      B.valto > current timestamp and                              \r\n" +
//        "      B.effto > current timestamp and                              \r\n" +
//        "      B.attributecode = 'OSLEVEL' and                              \r\n" +
//        "      A.ATTRIBUTEVALUE in('0040','0020') and                       \r\n" +
//        "      A.attributecode = 'STATUS'                                   \r\n" +
//        "      union all                                                    \r\n" +
//        "      select C.entityid, C.entitytype,'' as OSLEVEL                \r\n" +
//        "      from opicm.flag as C                                         \r\n" +
//        "      where                                                        \r\n" +
//        "      C.entitytype ='MODEL' and                                    \r\n" +
//        "      C.ENTERPRISE='SG' and                                        \r\n" +
//        "      C.valto > current timestamp and                              \r\n" +
//        "      C.effto > current timestamp and                              \r\n" +
//        "      C.ATTRIBUTEVALUE in('0040','0020') and                       \r\n" +
//        "      C.attributecode = 'STATUS' and                               \r\n" +
//        "      C.entityid not in(select A.entityid                          \r\n" +
//        "      from opicm.flag as A left join opicm.flag B                  \r\n" +
//        "      on A.ENTERPRISE = B.ENTERPRISE and                           \r\n" +
//        "      A.entityid = B.entityid and                                  \r\n" +
//        "      A.entitytype = B.entitytype                                  \r\n" +
//        "      where                                                        \r\n" +
//        "      A.entitytype ='MODEL' and                                    \r\n" +
//        "      A.ENTERPRISE='SG' and                                        \r\n" +
//        "      B.ENTERPRISE='SG' and                                        \r\n" +
//        "      A.valto > current timestamp and                              \r\n" +
//        "      A.effto > current timestamp and                              \r\n" +
//        "      B.valto > current timestamp and                              \r\n" +
//        "      B.effto > current timestamp and                              \r\n" +
//        "      B.attributecode = 'OSLEVEL' and                              \r\n" +
//        "      A.ATTRIBUTEVALUE in('0040','0020') and                       \r\n" +
//        "      A.attributecode = 'STATUS')                                  \r\n" +
//        "    ) as MODEL                                                     \r\n" +
//        "  ) as SYSTEM with ur                                              \r\n" ;
	
	
	private String INSERT_INTO_GBLI_WWCOMPATGROUP_SQL = 
	       " insert into GBLI.WWCOMPATGROUP(entityid,entitytype,OKTOPUB,BRANDCD)  \r\n" +
	       "   select entityid,entitytype,OKTOPUB,BRANDCD from                    \r\n" +
	       "   (                                                                  \r\n" +
	       "     select AA.entityid,AA.entitytype,AA.OKTOPUB,BB.BRANDCD from      \r\n" +
	       "     (                                                                \r\n" +
	       "       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OKTOPUB     \r\n" +
	       "       from opicm.flag as A left join opicm.flag B                    \r\n" +
	       "       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n" +
	       "       A.entityid = B.entityid and                                    \r\n" +
	       "       A.entitytype = B.entitytype                                    \r\n" +
	       "       where                                                          \r\n" +
	       "       A.entitytype ='SEOCG' and                                      \r\n" +
	       "       A.ENTERPRISE='SG' and                                          \r\n" +
	       "       B.ENTERPRISE='SG' and                                          \r\n" +
	       "       A.valto > current timestamp and                                \r\n" +
	       "       A.effto > current timestamp and                                \r\n" +
	       "       B.valto > current timestamp and                                \r\n" +
	       "       B.effto > current timestamp and                                \r\n" +
	       "       B.attributecode = 'OKTOPUB' and                                \r\n" +
	       "       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
	       "       A.attributecode = 'STATUS'                                     \r\n" +
	       "       union                                                          \r\n" +
	       "       select C.entityid, C.entitytype,'' as OKTOPUB                  \r\n" +
	       "       from opicm.flag as C                                           \r\n" +
	       "       where                                                          \r\n" +
	       "       C.entitytype ='SEOCG' and                                      \r\n" +
	       "       C.ENTERPRISE='SG' and                                          \r\n" +
	       "       C.valto > current timestamp and                                \r\n" +
	       "       C.effto > current timestamp and                                \r\n" +
	       "       C.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
	       "       C.attributecode = 'STATUS' and                                 \r\n" +
	       "       C.entityid not in( select A.entityid                           \r\n" +
	       "       from opicm.flag as A left join opicm.flag B                    \r\n" +
	       "       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n" +
	       "       A.entityid = B.entityid and                                    \r\n" +
	       "       A.entitytype = B.entitytype                                    \r\n" +
	       "       where                                                          \r\n" +
	       "       A.entitytype ='SEOCG' and                                      \r\n" +
	       "       A.ENTERPRISE='SG' and                                          \r\n" +
	       "       B.ENTERPRISE='SG' and                                          \r\n" +
	       "       A.valto > current timestamp and                                \r\n" +
	       "       A.effto > current timestamp and                                \r\n" +
	       "       B.valto > current timestamp and                                \r\n" +
	       "       B.effto > current timestamp and                                \r\n" +
	       "       B.attributecode = 'OKTOPUB' and                                \r\n" +
	       "       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
	       "       A.attributecode = 'STATUS' )                                   \r\n" +
	       "     )                                                                \r\n" +
	       "     as AA,                                                           \r\n" +
	       "     (                                                                \r\n" +
	       "       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as BRANDCD     \r\n" +
	       "       from opicm.flag as A left join opicm.flag B                    \r\n" +
	       "       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n" +
	       "       A.entityid = B.entityid and                                    \r\n" +
	       "       A.entitytype = B.entitytype                                    \r\n" +
	       "       where                                                          \r\n" +
	       "       A.entitytype ='SEOCG' and                                      \r\n" +
	       "       A.ENTERPRISE='SG' and                                          \r\n" +
	       "       B.ENTERPRISE='SG' and                                          \r\n" +
	       "       A.valto > current timestamp and                                \r\n" +
	       "       A.effto > current timestamp and                                \r\n" +
	       "       B.valto > current timestamp and                                \r\n" +
	       "       B.effto > current timestamp and                                \r\n" +
	       "       B.attributecode = 'BRANDCD' and                                \r\n" +
	       "       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
	       "       A.attributecode = 'STATUS'                                     \r\n" +
	       "       union                                                          \r\n" +
	       "       select C.entityid, C.entitytype,'' as BRANDCD                  \r\n" +
	       "       from opicm.flag as C                                           \r\n" +
	       "       where                                                          \r\n" +
	       "       C.entitytype ='SEOCG' and                                      \r\n" +
	       "       C.ENTERPRISE='SG' and                                          \r\n" +
	       "       C.valto > current timestamp and                                \r\n" +
	       "       C.effto > current timestamp and                                \r\n" +
	       "       C.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
	       "       C.attributecode = 'STATUS' and                                 \r\n" +
	       "       C.entityid not in( select A.entityid                           \r\n" +
	       "       from opicm.flag as A left join opicm.flag B                    \r\n" +
	       "       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n" +
	       "       A.entityid = B.entityid and                                    \r\n" +
	       "       A.entitytype = B.entitytype                                    \r\n" +
	       "       where                                                          \r\n" +
	       "       A.entitytype ='SEOCG' and                                      \r\n" +
	       "       A.ENTERPRISE='SG' and                                          \r\n" +
	       "       B.ENTERPRISE='SG' and                                          \r\n" +
	       "       A.valto > current timestamp and                                \r\n" +
	       "       A.effto > current timestamp and                                \r\n" +
	       "       B.valto > current timestamp and                                \r\n" +
	       "       B.effto > current timestamp and                                \r\n" +
	       "       B.attributecode = 'BRANDCD' and                                \r\n" +
	       "       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
	       "       A.attributecode = 'STATUS' )                                   \r\n" +
	       "     )                                                                \r\n" +
	       "     as BB                                                            \r\n" +
	       "     where AA.ENTITYID=BB.ENTITYID                                    \r\n" +
//	       "     union all                                                        \r\n" +
//	       "     select AA.entityid,AA.entitytype,AA.OKTOPUB,BB.BRANDCD from      \r\n" +
//	       "     (                                                                \r\n" +
//	       "       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OKTOPUB     \r\n" +
//	       "       from opicm.flag as A left join opicm.flag B                    \r\n" +
//	       "       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n" +
//	       "       A.entityid = B.entityid and                                    \r\n" +
//	       "       A.entitytype = B.entitytype                                    \r\n" +
//	       "       where                                                          \r\n" +
//	       "       A.entitytype ='MODELCG' and                                    \r\n" +
//	       "       A.ENTERPRISE='SG' and                                          \r\n" +
//	       "       B.ENTERPRISE='SG' and                                          \r\n" +
//	       "       A.valto > current timestamp and                                \r\n" +
//	       "       A.effto > current timestamp and                                \r\n" +
//	       "       B.valto > current timestamp and                                \r\n" +
//	       "       B.effto > current timestamp and                                \r\n" +
//	       "       B.attributecode = 'OKTOPUB' and                                \r\n" +
//	       "       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
//	       "       A.attributecode = 'STATUS'                                     \r\n" +
//	       "       union                                                          \r\n" +
//	       "       select C.entityid, C.entitytype,'' as OKTOPUB                  \r\n" +
//	       "       from opicm.flag as C                                           \r\n" +
//	       "       where                                                          \r\n" +
//	       "       C.entitytype ='MODELCG' and                                    \r\n" +
//	       "       C.ENTERPRISE='SG' and                                          \r\n" +
//	       "       C.valto > current timestamp and                                \r\n" +
//	       "       C.effto > current timestamp and                                \r\n" +
//	       "       C.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
//	       "       C.attributecode = 'STATUS' and                                 \r\n" +
//	       "       C.entityid not in( select A.entityid                           \r\n" +
//	       "       from opicm.flag as A left join opicm.flag B                    \r\n" +
//	       "       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n" +
//	       "       A.entityid = B.entityid and                                    \r\n" +
//	       "       A.entitytype = B.entitytype                                    \r\n" +
//	       "       where                                                          \r\n" +
//	       "       A.entitytype ='MODELCG' and                                    \r\n" +
//	       "       A.ENTERPRISE='SG' and                                          \r\n" +
//	       "       B.ENTERPRISE='SG' and                                          \r\n" +
//	       "       A.valto > current timestamp and                                \r\n" +
//	       "       A.effto > current timestamp and                                \r\n" +
//	       "       B.valto > current timestamp and                                \r\n" +
//	       "       B.effto > current timestamp and                                \r\n" +
//	       "       B.attributecode = 'OKTOPUB' and                                \r\n" +
//	       "       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
//	       "       A.attributecode = 'STATUS' )                                   \r\n" +
//	       "     )                                                                \r\n" +
//	       "     as AA,                                                           \r\n" +
//	       "     (                                                                \r\n" +
//	       "       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as BRANDCD     \r\n" +
//	       "       from opicm.flag as A left join opicm.flag B                    \r\n" +
//	       "       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n" +
//	       "       A.entityid = B.entityid and                                    \r\n" +
//	       "       A.entitytype = B.entitytype                                    \r\n" +
//	       "       where                                                          \r\n" +
//	       "       A.entitytype ='MODELCG' and                                    \r\n" +
//	       "       A.ENTERPRISE='SG' and                                          \r\n" +
//	       "       B.ENTERPRISE='SG' and                                          \r\n" +
//	       "       A.valto > current timestamp and                                \r\n" +
//	       "       A.effto > current timestamp and                                \r\n" +
//	       "       B.valto > current timestamp and                                \r\n" +
//	       "       B.effto > current timestamp and                                \r\n" +
//	       "       B.attributecode = 'BRANDCD' and                                \r\n" +
//	       "       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
//	       "       A.attributecode = 'STATUS'                                     \r\n" +
//	       "       union                                                          \r\n" +
//	       "       select C.entityid, C.entitytype,'' as BRANDCD                  \r\n" +
//	       "       from opicm.flag as C                                           \r\n" +
//	       "       where                                                          \r\n" +
//	       "       C.entitytype ='MODELCG' and                                    \r\n" +
//	       "       C.ENTERPRISE='SG' and                                          \r\n" +
//	       "       C.valto > current timestamp and                                \r\n" +
//	       "       C.effto > current timestamp and                                \r\n" +
//	       "       C.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
//	       "       C.attributecode = 'STATUS' and                                 \r\n" +
//	       "       C.entityid not in( select A.entityid                           \r\n" +
//	       "       from opicm.flag as A left join opicm.flag B                    \r\n" +
//	       "       on A.ENTERPRISE = B.ENTERPRISE and                             \r\n" +
//	       "       A.entityid = B.entityid and                                    \r\n" +
//	       "       A.entitytype = B.entitytype                                    \r\n" +
//	       "       where                                                          \r\n" +
//	       "       A.entitytype ='MODELCG' and                                    \r\n" +
//	       "       A.ENTERPRISE='SG' and                                          \r\n" +
//	       "       B.ENTERPRISE='SG' and                                          \r\n" +
//	       "       A.valto > current timestamp and                                \r\n" +
//	       "       A.effto > current timestamp and                                \r\n" +
//	       "       B.valto > current timestamp and                                \r\n" +
//	       "       B.effto > current timestamp and                                \r\n" +
//	       "       B.attributecode = 'BRANDCD' and                                \r\n" +
//	       "       A.ATTRIBUTEVALUE in('0040','0020') and                         \r\n" +
//	       "       A.attributecode = 'STATUS' )                                   \r\n" +
//	       "     )                                                                \r\n" +
//	       "     as BB                                                            \r\n" +
//	       "     where AA.ENTITYID=BB.ENTITYID                                    \r\n" +
	       "  ) as Group with ur                                                  \r\n" ;
	
	private String INSERT_INTO_GBLI_WWCOMPATOS_SQL = 
	      " insert into GBLI.WWCOMPATOS(entityid,entitytype,OS)           \r\n"+
	      "   select entityid,entitytype,OS from                          \r\n"+
	      "   (                                                           \r\n"+
	      "     select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OS     \r\n"+
	      "     from opicm.flag as A left join opicm.flag B               \r\n"+
	      "     on A.ENTERPRISE = B.ENTERPRISE and                        \r\n"+
	      "     A.entityid = B.entityid and                               \r\n"+
	      "     A.entitytype = B.entitytype                               \r\n"+
	      "     where                                                     \r\n"+
	      "     A.entitytype ='SEOCGOS' and                               \r\n"+
	      "     A.ENTERPRISE='SG' and                                     \r\n"+
	      "     B.ENTERPRISE='SG' and                                     \r\n"+
	      "     A.valto > current timestamp and                           \r\n"+
	      "     A.effto > current timestamp and                           \r\n"+
	      "     B.valto > current timestamp and                           \r\n"+
	      "     B.effto > current timestamp and                           \r\n"+
	      "     B.attributecode = 'OS' and                                \r\n"+
	      "     A.ATTRIBUTEVALUE in('0040','0020') and                    \r\n"+
	      "     A.attributecode = 'STATUS'                                \r\n"+
	      "     union                                                     \r\n"+
	      "     select C.entityid, C.entitytype,'' as OS                  \r\n"+
	      "     from opicm.flag as C                                      \r\n"+
	      "     where                                                     \r\n"+
	      "     C.entitytype ='SEOCGOS' and                               \r\n"+
	      "     C.ENTERPRISE='SG' and                                     \r\n"+
	      "     C.valto > current timestamp and                           \r\n"+
	      "     C.effto > current timestamp and                           \r\n"+
	      "     C.ATTRIBUTEVALUE in('0040','0020') and                    \r\n"+
	      "     C.attributecode = 'STATUS' and                            \r\n"+
	      "     C.entityid not in(select A.entityid                       \r\n"+
	      "     from opicm.flag as A left join opicm.flag B               \r\n"+
	      "     on A.ENTERPRISE = B.ENTERPRISE and                        \r\n"+
	      "     A.entityid = B.entityid and                               \r\n"+
	      "     A.entitytype = B.entitytype                               \r\n"+
	      "     where                                                     \r\n"+
	      "     A.entitytype ='SEOCGOS' and                               \r\n"+
	      "     A.ENTERPRISE='SG' and                                     \r\n"+
	      "     B.ENTERPRISE='SG' and                                     \r\n"+
	      "     A.valto > current timestamp and                           \r\n"+
	      "     A.effto > current timestamp and                           \r\n"+
	      "     B.valto > current timestamp and                           \r\n"+
	      "     B.effto > current timestamp and                           \r\n"+
	      "     B.attributecode = 'OS' and                                \r\n"+
	      "     A.ATTRIBUTEVALUE in('0040','0020') and                    \r\n"+
	      "     A.attributecode = 'STATUS')                               \r\n"+
	      "                                                               \r\n"+
//	      "     union all                                                 \r\n"+
//	      "                                                               \r\n"+
//	      "     select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OS     \r\n"+
//	      "     from opicm.flag as A left join opicm.flag B               \r\n"+
//	      "     on A.ENTERPRISE = B.ENTERPRISE and                        \r\n"+
//	      "     A.entityid = B.entityid and                               \r\n"+
//	      "     A.entitytype = B.entitytype                               \r\n"+
//	      "     where                                                     \r\n"+
//	      "     A.entitytype ='MODELCGOS' and                             \r\n"+
//	      "     A.ENTERPRISE='SG' and                                     \r\n"+
//	      "     B.ENTERPRISE='SG' and                                     \r\n"+
//	      "     A.valto > current timestamp and                           \r\n"+
//	      "     A.effto > current timestamp and                           \r\n"+
//	      "     B.valto > current timestamp and                           \r\n"+
//	      "     B.effto > current timestamp and                           \r\n"+
//	      "     B.attributecode = 'OS' and                                \r\n"+
//	      "     A.ATTRIBUTEVALUE in('0040','0020') and                    \r\n"+
//	      "     A.attributecode = 'STATUS'                                \r\n"+
//	      "     union                                                     \r\n"+
//	      "     select C.entityid, C.entitytype,'' as OS                  \r\n"+
//	      "     from opicm.flag as C                                      \r\n"+
//	      "     where                                                     \r\n"+
//	      "     C.entitytype ='MODELCGOS' and                             \r\n"+
//	      "     C.ENTERPRISE='SG' and                                     \r\n"+
//	      "     C.valto > current timestamp and                           \r\n"+
//	      "     C.effto > current timestamp and                           \r\n"+
//	      "     C.ATTRIBUTEVALUE in('0040','0020') and                    \r\n"+
//	      "     C.attributecode = 'STATUS' and                            \r\n"+
//	      "     C.entityid not in(select A.entityid                       \r\n"+
//	      "     from opicm.flag as A left join opicm.flag B               \r\n"+
//	      "     on A.ENTERPRISE = B.ENTERPRISE and                        \r\n"+
//	      "     A.entityid = B.entityid and                               \r\n"+
//	      "     A.entitytype = B.entitytype                               \r\n"+
//	      "     where                                                     \r\n"+
//	      "     A.entitytype ='MODELCGOS' and                             \r\n"+
//	      "     A.ENTERPRISE='SG' and                                     \r\n"+
//	      "     B.ENTERPRISE='SG' and                                     \r\n"+
//	      "     A.valto > current timestamp and                           \r\n"+
//	      "     A.effto > current timestamp and                           \r\n"+
//	      "     B.valto > current timestamp and                           \r\n"+
//	      "     B.effto > current timestamp and                           \r\n"+
//	      "     B.attributecode = 'OS' and                                \r\n"+
//	      "     A.ATTRIBUTEVALUE in('0040','0020') and                    \r\n"+
//	      "     A.attributecode = 'STATUS')                               \r\n"+
	      "   ) as OS with ur                                             \r\n";
	private String INSERT_INTO_GBLI_WWCOMPATOSOP_SQL =
	      " insert into GBLI.WWCOMPATOSOP(entityid,entitytype,COMPATPUBFLG,RELTYPE,PUBFROM,PUBTO)           \r\n" + 
	      "    select entityid,entitytype,COMPATPUBFLG,RELTYPE,PUBFROM,PUBTO from                           \r\n" +
	      "    (                                                                                            \r\n" +
	      "       select AA.entityid,AA.entitytype,AA.COMPATPUBFLG,BB.RELTYPE,CC.PUBFROM,DD.PUBTO from      \r\n" +
	      "      (                                                                                          \r\n" +
	      "        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as COMPATPUBFLG                          \r\n" +
	      "        from opicm.flag as A left join opicm.flag B                                              \r\n" +
	      "        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n" +
	      "        A.entityid = B.entityid and                                                              \r\n" +
	      "        A.entitytype = B.entitytype                                                              \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        A.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        A.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        B.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        A.valto > current timestamp and                                                          \r\n" +
	      "        A.effto > current timestamp and                                                          \r\n" +
	      "        B.valto > current timestamp and                                                          \r\n" +
	      "        B.effto > current timestamp and                                                          \r\n" +
	      "        B.attributecode = 'COMPATPUBFLG' and                                                     \r\n" +
	      "        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        A.attributecode = 'STATUS'                                                               \r\n" +
	      "        union all                                                                                \r\n" +
	      "        select C.entityid, C.entitytype,'' as COMPATPUBFLG                                       \r\n" +
	      "        from opicm.flag as C                                                                     \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        C.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        C.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        C.valto > current timestamp and                                                          \r\n" +
	      "        C.effto > current timestamp and                                                          \r\n" +
	      "        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        C.attributecode = 'STATUS' and                                                           \r\n" +
	      "        C.entityid not in( select A.entityid                                                     \r\n" +
	      "        from opicm.flag as A left join opicm.flag B                                              \r\n" +
	      "        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n" +
	      "        A.entityid = B.entityid and                                                              \r\n" +
	      "        A.entitytype = B.entitytype                                                              \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        A.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        A.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        B.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        A.valto > current timestamp and                                                          \r\n" +
	      "        A.effto > current timestamp and                                                          \r\n" +
	      "        B.valto > current timestamp and                                                          \r\n" +
	      "        B.effto > current timestamp and                                                          \r\n" +
	      "        B.attributecode = 'COMPATPUBFLG' and                                                     \r\n" +
	      "        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        A.attributecode = 'STATUS' )                                                             \r\n" +
	      "      )                                                                                          \r\n" +
	      "      as AA,                                                                                     \r\n" +
	      "      (                                                                                          \r\n" +
	      "        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as RELTYPE                               \r\n" +
	      "        from opicm.flag as A left join opicm.flag B                                              \r\n" +
	      "        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n" +
	      "        A.entityid = B.entityid and                                                              \r\n" +
	      "        A.entitytype = B.entitytype                                                              \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        A.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        A.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        B.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        A.valto > current timestamp and                                                          \r\n" +
	      "        A.effto > current timestamp and                                                          \r\n" +
	      "        B.valto > current timestamp and                                                          \r\n" +
	      "        B.effto > current timestamp and                                                          \r\n" +
	      "        B.attributecode = 'RELTYPE' and                                                          \r\n" +
	      "        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        A.attributecode = 'STATUS'                                                               \r\n" +
	      "        union all                                                                                \r\n" +
	      "        select C.entityid, C.entitytype,'' as RELTYPE                                            \r\n" +
	      "        from opicm.flag as C                                                                     \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        C.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        C.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        C.valto > current timestamp and                                                          \r\n" +
	      "        C.effto > current timestamp and                                                          \r\n" +
	      "        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        C.attributecode = 'STATUS' and                                                           \r\n" +
	      "        C.entityid not in( select A.entityid                                                     \r\n" +
	      "        from opicm.flag as A left join opicm.flag B                                              \r\n" +
	      "        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n" +
	      "        A.entityid = B.entityid and                                                              \r\n" +
	      "        A.entitytype = B.entitytype                                                              \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        A.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        A.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        B.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        A.valto > current timestamp and                                                          \r\n" +
	      "        A.effto > current timestamp and                                                          \r\n" +
	      "        B.valto > current timestamp and                                                          \r\n" +
	      "        B.effto > current timestamp and                                                          \r\n" +
	      "        B.attributecode = 'RELTYPE' and                                                          \r\n" +
	      "        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        A.attributecode = 'STATUS' )                                                             \r\n" +
	      "      )                                                                                          \r\n" +
	      "      as BB,                                                                                     \r\n" +
	      "      (                                                                                          \r\n" +
	      "        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as PUBFROM                               \r\n" +
	      "        from opicm.flag as A left join opicm.text B                                              \r\n" +
	      "        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n" +
	      "        A.entityid = B.entityid and                                                              \r\n" +
	      "        A.entitytype = B.entitytype                                                              \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        A.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        A.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        B.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        A.valto > current timestamp and                                                          \r\n" +
	      "        A.effto > current timestamp and                                                          \r\n" +
	      "        B.valto > current timestamp and                                                          \r\n" +
	      "        B.effto > current timestamp and                                                          \r\n" +
	      "        B.attributecode = 'PUBFROM' and                                                          \r\n" +
	      "        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        A.attributecode = 'STATUS'                                                               \r\n" +
	      "        union all                                                                                \r\n" +
	      "        select C.entityid, C.entitytype,'' as PUBFROM                                            \r\n" +
	      "        from opicm.flag as C                                                                     \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        C.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        C.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        C.valto > current timestamp and                                                          \r\n" +
	      "        C.effto > current timestamp and                                                          \r\n" +
	      "        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        C.attributecode = 'STATUS' and                                                           \r\n" +
	      "        C.entityid not in( select A.entityid                                                     \r\n" +
	      "        from opicm.flag as A left join opicm.text B                                              \r\n" +
	      "        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n" +
	      "        A.entityid = B.entityid and                                                              \r\n" +
	      "        A.entitytype = B.entitytype                                                              \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        A.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        A.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        B.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        A.valto > current timestamp and                                                          \r\n" +
	      "        A.effto > current timestamp and                                                          \r\n" +
	      "        B.valto > current timestamp and                                                          \r\n" +
	      "        B.effto > current timestamp and                                                          \r\n" +
	      "        B.attributecode = 'PUBFROM' and                                                          \r\n" +
	      "        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        A.attributecode = 'STATUS' )                                                             \r\n" +
	      "      )                                                                                          \r\n" +
	      "      as CC,                                                                                     \r\n" +
	      "     (                                                                                           \r\n" +
	      "        select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as PUBTO                                 \r\n" +
	      "        from opicm.flag as A left join opicm.text B                                              \r\n" +
	      "        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n" +
	      "        A.entityid = B.entityid and                                                              \r\n" +
	      "        A.entitytype = B.entitytype                                                              \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        A.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        A.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        B.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        A.valto > current timestamp and                                                          \r\n" +
	      "        A.effto > current timestamp and                                                          \r\n" +
	      "        B.valto > current timestamp and                                                          \r\n" +
	      "        B.effto > current timestamp and                                                          \r\n" +
	      "        B.attributecode = 'PUBTO' and                                                            \r\n" +
	      "        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        A.attributecode = 'STATUS'                                                               \r\n" +
	      "        union all                                                                                \r\n" +
	      "        select C.entityid, C.entitytype,'' as PUBTO                                              \r\n" +
	      "        from opicm.flag as C                                                                     \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        C.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        C.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        C.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        C.valto > current timestamp and                                                          \r\n" +
	      "        C.effto > current timestamp and                                                          \r\n" +
	      "        C.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        C.attributecode = 'STATUS' and                                                           \r\n" +
	      "        C.entityid not in( select A.entityid                                                     \r\n" +
	      "        from opicm.flag as A left join opicm.text B                                              \r\n" +
	      "        on A.ENTERPRISE = B.ENTERPRISE and                                                       \r\n" +
	      "        A.entityid = B.entityid and                                                              \r\n" +
	      "        A.entitytype = B.entitytype                                                              \r\n" +
	      "        where                                                                                    \r\n" +
	      //"        A.entitytype in('SEOCGOSSEO','SEOCGOSSVCSEO','SEOCGOSBDL','MDLCGOSMDL') and            \r\n" +
	      "        A.entitytype in('SEOCGOSSEO','SEOCGOSBDL') and                                           \r\n" +
	      "        A.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        B.ENTERPRISE='SG' and                                                                    \r\n" +
	      "        A.valto > current timestamp and                                                          \r\n" +
	      "        A.effto > current timestamp and                                                          \r\n" +
	      "        B.valto > current timestamp and                                                          \r\n" +
	      "        B.effto > current timestamp and                                                          \r\n" +
	      "        B.attributecode = 'PUBTO' and                                                            \r\n" +
	      "        A.ATTRIBUTEVALUE in('0040','0020') and                                                   \r\n" +
	      "        A.attributecode = 'STATUS' )                                                             \r\n" +
	      "      )                                                                                          \r\n" +
	      "      as DD                                                                                      \r\n" +
	      "      where AA.ENTITYID=BB.ENTITYID                                                              \r\n" +
	      "      and AA.ENTITYID=CC.ENTITYID and AA.ENTITYID=DD.ENTITYID                                    \r\n" +                             
	      "    ) as OSOP with ur                                                                            \r\n" ;
	
	private String INSERT_INTO_GBLI_WWCOMPATOPTION_SQL =
	      " insert into GBLI.WWCOMPATOPTION(entityid,entitytype,OSLEVEL)            \r\n" +
	      "   select entityid,entitytype,OSLEVEL from                               \r\n" +
	      "   (                                                                     \r\n" +
	      "     select * from                                                       \r\n" +
	      "     (                                                                   \r\n" +
	      "       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL        \r\n" +
	      "       from opicm.flag as A left join opicm.flag B                       \r\n" +
	      "       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n" +
	      "       A.entityid = B.entityid and                                       \r\n" +
	      "       A.entitytype = B.entitytype                                       \r\n" +
	      "       where                                                             \r\n" +
	      "       A.entitytype ='LSEOBUNDLE' and                                    \r\n" +
	      "       A.ENTERPRISE='SG' and                                             \r\n" +
	      "       B.ENTERPRISE='SG' and                                             \r\n" +
	      "       A.valto > current timestamp and                                   \r\n" +
	      "       A.effto > current timestamp and                                   \r\n" +
	      "       B.valto > current timestamp and                                   \r\n" +
	      "       B.effto > current timestamp and                                   \r\n" +
	      "       B.attributecode = 'OSLEVEL' and                                   \r\n" +
	      "       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
	      "       A.attributecode = 'STATUS'                                        \r\n" +
	      "       union all                                                         \r\n" +
	      "       select C.entityid, C.entitytype,'' as OSLEVEL                     \r\n" +
	      "       from opicm.flag as C                                              \r\n" +
	      "       where                                                             \r\n" +
	      "       C.entitytype ='LSEOBUNDLE' and                                    \r\n" +
	      "       C.ENTERPRISE='SG' and                                             \r\n" +
	      "       C.valto > current timestamp and                                   \r\n" +
	      "       C.effto > current timestamp and                                   \r\n" +
	      "       C.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
	      "       C.attributecode = 'STATUS' and                                    \r\n" +
	      "       C.entityid not in(select A.entityid                               \r\n" +
	      "       from opicm.flag as A left join opicm.flag B                       \r\n" +
	      "       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n" +
	      "       A.entityid = B.entityid and                                       \r\n" +
	      "       A.entitytype = B.entitytype                                       \r\n" +
	      "       where                                                             \r\n" +
	      "       A.entitytype ='LSEOBUNDLE' and                                    \r\n" +
	      "       A.ENTERPRISE='SG' and                                             \r\n" +
	      "       B.ENTERPRISE='SG' and                                             \r\n" +
	      "       A.valto > current timestamp and                                   \r\n" +
	      "       A.effto > current timestamp and                                   \r\n" +
	      "       B.valto > current timestamp and                                   \r\n" +
	      "       B.effto > current timestamp and                                   \r\n" +
	      "       B.attributecode = 'OSLEVEL' and                                   \r\n" +
	      "       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
	      "       A.attributecode = 'STATUS')                                       \r\n" +
	      "     ) as LSEOBUNDLE                                                     \r\n" +
	      "     union all                                                           \r\n" +
	      "     select * from                                                       \r\n" +
	      "     (                                                                   \r\n" +
	      "       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL        \r\n" +
	      "       from opicm.flag as A left join opicm.flag B                       \r\n" +
	      "       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n" +
	      "       A.entityid = B.entityid and                                       \r\n" +
	      "       A.entitytype = B.entitytype                                       \r\n" +
	      "       where                                                             \r\n" +
	      "       A.entitytype ='WWSEO' and                                         \r\n" +
	      "       A.ENTERPRISE='SG' and                                             \r\n" +
	      "       B.ENTERPRISE='SG' and                                             \r\n" +
	      "       A.valto > current timestamp and                                   \r\n" +
	      "       A.effto > current timestamp and                                   \r\n" +
	      "       B.valto > current timestamp and                                   \r\n" +
	      "       B.effto > current timestamp and                                   \r\n" +
	      "       B.attributecode = 'OS' and                                        \r\n" +
	      "       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
	      "       A.attributecode = 'STATUS'                                        \r\n" +
	      "       union all                                                         \r\n" +
	      "       select C.entityid, C.entitytype,'' as OSLEVEL                     \r\n" +
	      "       from opicm.flag as C                                              \r\n" +
	      "       where                                                             \r\n" +
	      "       C.entitytype ='WWSEO' and                                         \r\n" +
	      "       C.ENTERPRISE='SG' and                                             \r\n" +
	      "       C.valto > current timestamp and                                   \r\n" +
	      "       C.effto > current timestamp and                                   \r\n" +
	      "       C.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
	      "       C.attributecode = 'STATUS' and                                    \r\n" +
	      "       C.entityid not in(select A.entityid                               \r\n" +
	      "       from opicm.flag as A left join opicm.flag B                       \r\n" +
	      "       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n" +
	      "       A.entityid = B.entityid and                                       \r\n" +
	      "       A.entitytype = B.entitytype                                       \r\n" +
	      "       where                                                             \r\n" +
	      "       A.entitytype ='WWSEO' and                                         \r\n" +
	      "       A.ENTERPRISE='SG' and                                             \r\n" +
	      "       B.ENTERPRISE='SG' and                                             \r\n" +
	      "       A.valto > current timestamp and                                   \r\n" +
	      "       A.effto > current timestamp and                                   \r\n" +
	      "       B.valto > current timestamp and                                   \r\n" +
	      "       B.effto > current timestamp and                                   \r\n" +
	      "       B.attributecode = 'OS' and                                        \r\n" +
	      "       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
	      "       A.attributecode = 'STATUS')                                       \r\n" +
	      "     ) as WWSEO                                                          \r\n" +
//	      "     union all                                                           \r\n" +
//	      "     select * from                                                       \r\n" +
//	      "     (                                                                   \r\n" +
//	      "       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL        \r\n" +
//	      "       from opicm.flag as A left join opicm.flag B                       \r\n" +
//	      "       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n" +
//	      "       A.entityid = B.entityid and                                       \r\n" +
//	      "       A.entitytype = B.entitytype                                       \r\n" +
//	      "       where                                                             \r\n" +
//	      "       A.entitytype ='SVCSEO' and                                        \r\n" +
//	      "       A.ENTERPRISE='SG' and                                             \r\n" +
//	      "       B.ENTERPRISE='SG' and                                             \r\n" +
//	      "       A.valto > current timestamp and                                   \r\n" +
//	      "       A.effto > current timestamp and                                   \r\n" +
//	      "       B.valto > current timestamp and                                   \r\n" +
//	      "       B.effto > current timestamp and                                   \r\n" +
//	      "       B.attributecode = 'OSLEVEL' and                                   \r\n" +
//	      "       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
//	      "       A.attributecode = 'STATUS'                                        \r\n" +
//	      "       union all                                                         \r\n" +
//	      "       select C.entityid, C.entitytype,'' as OSLEVEL                     \r\n" +
//	      "       from opicm.flag as C                                              \r\n" +
//	      "       where                                                             \r\n" +
//	      "       C.entitytype ='SVCSEO' and                                        \r\n" +
//	      "       C.ENTERPRISE='SG' and                                             \r\n" +
//	      "       C.valto > current timestamp and                                   \r\n" +
//	      "       C.effto > current timestamp and                                   \r\n" +
//	      "       C.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
//	      "       C.attributecode = 'STATUS' and                                    \r\n" +
//	      "       C.entityid not in(select A.entityid                               \r\n" +
//	      "       from opicm.flag as A left join opicm.flag B                       \r\n" +
//	      "       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n" +
//	      "       A.entityid = B.entityid and                                       \r\n" +
//	      "       A.entitytype = B.entitytype                                       \r\n" +
//	      "       where                                                             \r\n" +
//	      "       A.entitytype ='SVCSEO' and                                        \r\n" +
//	      "       A.ENTERPRISE='SG' and                                             \r\n" +
//	      "       B.ENTERPRISE='SG' and                                             \r\n" +
//	      "       A.valto > current timestamp and                                   \r\n" +
//	      "       A.effto > current timestamp and                                   \r\n" +
//	      "       B.valto > current timestamp and                                   \r\n" +
//	      "       B.effto > current timestamp and                                   \r\n" +
//	      "       B.attributecode = 'OSLEVEL' and                                   \r\n" +
//	      "       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
//	      "       A.attributecode = 'STATUS')                                       \r\n" +
//	      "     ) as SVCSEO                                                         \r\n" +
//	      "     union all                                                           \r\n" +
//	      "     select * from                                                       \r\n" +
//	      "     (                                                                   \r\n" +
//	      "       select A.entityid,A.entitytype,B.ATTRIBUTEVALUE as OSLEVEL        \r\n" +
//	      "       from opicm.flag as A left join opicm.flag B                       \r\n" +
//	      "       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n" +
//	      "       A.entityid = B.entityid and                                       \r\n" +
//	      "       A.entitytype = B.entitytype                                       \r\n" +
//	      "       where                                                             \r\n" +
//	      "       A.entitytype ='MODEL' and                                         \r\n" +
//	      "       A.ENTERPRISE='SG' and                                             \r\n" +
//	      "       B.ENTERPRISE='SG' and                                             \r\n" +
//	      "       A.valto > current timestamp and                                   \r\n" +
//	      "       A.effto > current timestamp and                                   \r\n" +
//	      "       B.valto > current timestamp and                                   \r\n" +
//	      "       B.effto > current timestamp and                                   \r\n" +
//	      "       B.attributecode = 'OSLEVEL' and                                   \r\n" +
//	      "       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
//	      "       A.attributecode = 'STATUS'                                        \r\n" +
//	      "       union all                                                         \r\n" +
//	      "       select C.entityid, C.entitytype,'' as OSLEVEL                     \r\n" +
//	      "       from opicm.flag as C                                              \r\n" +
//	      "       where                                                             \r\n" +
//	      "       C.entitytype ='MODEL' and                                         \r\n" +
//	      "       C.ENTERPRISE='SG' and                                             \r\n" +
//	      "       C.valto > current timestamp and                                   \r\n" +
//	      "       C.effto > current timestamp and                                   \r\n" +
//	      "       C.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
//	      "       C.attributecode = 'STATUS' and                                    \r\n" +
//	      "       C.entityid not in(select A.entityid                               \r\n" +
//	      "       from opicm.flag as A left join opicm.flag B                       \r\n" +
//	      "       on A.ENTERPRISE = B.ENTERPRISE and                                \r\n" +
//	      "       A.entityid = B.entityid and                                       \r\n" +
//	      "       A.entitytype = B.entitytype                                       \r\n" +
//	      "       where                                                             \r\n" +
//	      "       A.entitytype ='MODEL' and                                         \r\n" +
//	      "       A.ENTERPRISE='SG' and                                             \r\n" +
//	      "       B.ENTERPRISE='SG' and                                             \r\n" +
//	      "       A.valto > current timestamp and                                   \r\n" +
//	      "       A.effto > current timestamp and                                   \r\n" +
//	      "       B.valto > current timestamp and                                   \r\n" +
//	      "       B.effto > current timestamp and                                   \r\n" +
//	      "       B.attributecode = 'OSLEVEL' and                                   \r\n" +
//	      "       A.ATTRIBUTEVALUE in('0040','0020') and                            \r\n" +
//	      "       A.attributecode = 'STATUS')                                       \r\n" +
//	      "     ) as MODEL                                                          \r\n" +
	      "   ) as OPTION with ur                                                   \r\n" ;
	
//	private String INSERT_INTO_GBLI_WWCOMPATTEMP_MODELCG_SQL =
//	      "  insert into GBLI.WWCOMPATTEMP(                                                        \r\n" +
//	      "   ACTIVITY,UPDATED,TIMEOFCHANGE,BRANDCD_FC,SYSTEMENTITYTYPE,                           \r\n" +
//	      "   SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OKTOPUB,                       \r\n" +
//	      "   OSENTITYTYPE,OSENTITYID,OS,OPTIONENTITYTYPE,OPTIONENTITYID,                          \r\n" +
//	      "   COMPATIBILITYPUBLISHINGFLAG,RELATIONSHIPTYPE,PUBLISHFROM,PUBLISHTO                   \r\n" +
//	      "   )                                                                                    \r\n" +
//	      "  select distinct 'A',current timestamp,current timestamp,BRANDCD,SYSTEMENTITYTYPE,     \r\n" +
//	      "        SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OKTOPUB,                  \r\n" +
//	      "        OSENTITYTYPE,OSENTITYID,OS,OptionENTITYTYPE,OptionENTITYID,                     \r\n" +
//	      "        COMPATIBILITYPUBLISHINGFLAG,RELATIONSHIPTYPE,PUBLISHFROM,PUBLISHTO              \r\n" +
//	      "  from                                                                                  \r\n" +
//	      " (                                                                                      \r\n" +
//	      "  select                                                                                \r\n" +
//	      "    System.entitytype                as SYSTEMENTITYTYPE,                               \r\n" +
//	      "    System.entityid                  as SYSTEMENTITYID,                                 \r\n" +
//	      "    System.OSLEVEL                   as SYSTEMOS,                                       \r\n" +
//	      "    case System.OSLEVEL                                                                 \r\n" +
//	      "    when '"+OSIndependent+"' then 'ALL'                                                 \r\n" +
//	      "    when '' then 'ALL'                                                                  \r\n" +
//	      "    else System.OSLEVEL end as SYSTEMOS2,                                               \r\n" +
//	      "    Group.entitytype                 as GROUPENTITYTYPE,                                \r\n" +
//	      "    Group.entityid                   as GROUPENTITYID,                                  \r\n" +
//	      "    Group.OKTOPUB                    as OKTOPUB,                                        \r\n" +
//	      "    Group.BRANDCD                    as BRANDCD,                                        \r\n" +
//	      "    OS.entitytype                    as OSENTITYTYPE,                                   \r\n" +
//	      "    OS.entityid                      as OSENTITYID,                                     \r\n" +
//	      "    OS.OS                            as OS,                                             \r\n" +
//	      "    case OS.OS                                                                          \r\n" +
//	      "    when '"+OSIndependent+"' then 'ALL'                                                 \r\n" +
//	      "    when '' then 'ALL'                                                                  \r\n" +
//	      "    else OS.OS end as OS2,                                                              \r\n" +
//	      "    OSOption.entityid                as OSOPTIONENTITYID,                               \r\n" +
//	      "    OSOption.entitytype              as OSOPTIONENTITYTYPE,                             \r\n" +
//	      "    OSOP.COMPATPUBFLG                as COMPATIBILITYPUBLISHINGFLAG,                    \r\n" +
//	      "    OSOP.RELTYPE                     as RELATIONSHIPTYPE,                               \r\n" +
//	      "    OSOP.PUBFROM                     as PUBLISHFROM,                                    \r\n" +
//	      "    OSOP.PUBTO                       as PUBLISHTO,                                      \r\n" +
//	      "    Option.entitytype                as OptionENTITYTYPE,                               \r\n" +
//	      "    Option.entityid                  as OptionENTITYID,                                 \r\n" +
//	      "    case Option.OSLEVEL                                                                 \r\n" +
//	      "    when '"+OSIndependent+"' then 'ALL'                                                 \r\n" +
//	      "    when '' then 'ALL'                                                                  \r\n" +
//	      "    else Option.OSLEVEL end as OPTIONOS2                                                \r\n" +
//	      "  from GBLI.WWCOMPATGROUP             as Group                                          \r\n" +
//	      "  inner join opicm.relator           as GroupSystem on                                  \r\n" +
//	      "     GroupSystem.entity1type         = 'MODELCG' and                                    \r\n" +
//	      "     GroupSystem.entity2type         = 'MODEL' and                                      \r\n" +
//	      "     GroupSystem.entity1id           = Group.entityid and                               \r\n" +
//	      "     GroupSystem.valto               > current timestamp and                            \r\n" +
//	      "     GroupSystem.effto               > current timestamp and                            \r\n" +
//	      "     GroupSystem.ENTERPRISE          = 'SG'                                             \r\n" +
//	      "  inner join GBLI.WWCOMPATSYSTEM      as System on                                      \r\n" +
//	      "     System.entitytype               = 'MODEL' and                                      \r\n" +
//	      "     System.entityid                 = GroupSystem.entity2id                            \r\n" +
//	      "  inner join opicm.relator           as GroupOS on                                      \r\n" +
//	      "     GroupOS.entity1type             = 'MODELCG' and                                    \r\n" +
//	      "     GroupOS.entity2type             = 'MODELCGOS' and                                  \r\n" +
//	      "     GroupOS.entity1id               = Group.entityid and                               \r\n" +
//	      "     GroupOS.valto                   > current timestamp and                            \r\n" +
//	      "     GroupOS.effto                   > current timestamp and                            \r\n" +
//	      "     GroupOS.ENTERPRISE              = 'SG'                                             \r\n" +
//	      "  inner join GBLI.WWCOMPATOS          as OS on                                          \r\n" +
//	      "     OS.entitytype                   = 'MODELCGOS' and                                  \r\n" +
//	      "     OS.entityid                     = GroupOS.entity2id                                \r\n" +
//	      "  inner join opicm.relator           as OSOption on                                     \r\n" +
//	      "     OSOption.entity1type            = 'MODELCGOS' and                                  \r\n" +
//	      "     OSOption.entity2type            = 'MODEL' and                                      \r\n" +
//	      "     OSOption.entity1id              = OS.entityid and                                  \r\n" +
//	      "     OSOption.valto                  > current timestamp and                            \r\n" +
//	      "     OSOption.effto                  > current timestamp and                            \r\n" +
//	      "     OSOption.ENTERPRISE             = 'SG'                                             \r\n" +
//	      "  inner join GBLI.WWCOMPATOSOP       as OSOP  on                                        \r\n" +
//	      "     OSOP.entitytype                 = OSOption.entitytype and                          \r\n" +
//	      "     OSOP.entityid                   = OSOption.entityid                                \r\n" +
//	      "  inner join GBLI.WWCOMPATOPTION      as Option on                                      \r\n" +
//	      "     Option.entitytype               = 'MODEL' and                                      \r\n" +
//	      "     Option.entityid                 = OSOption.entity2id                               \r\n" +
//	      " ) as temp                                                                              \r\n" +
//	      "  where                                                                                 \r\n" +
//	      "  (                                                                                     \r\n" +
//	      "    temp.OS2='ALL' or                                                                   \r\n" +
//	      "    ((temp.OS2=temp.SYSTEMOS2 or temp.SYSTEMOS2='ALL')                                  \r\n" +
//	      "    and (temp.OS2=temp.OPTIONOS2 or temp.OPTIONOS2='ALL'))                              \r\n" +
//	      "  ) and temp.OKTOPUB!='DEL' and temp.COMPATIBILITYPUBLISHINGFLAG!='DEL'                 \r\n" +
//	      "  with ur                                                                               \r\n" ;
	private String INSERT_INTO_GBLI_WWCOMPATTEMP_SEOCG_SQL =
	      "  insert into GBLI.WWCOMPATTEMP(                                                        \r\n" +
	      "  ACTIVITY,UPDATED,TIMEOFCHANGE,BRANDCD_FC,SYSTEMENTITYTYPE,                            \r\n" +
	      "  SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OKTOPUB,                        \r\n" +
	      "  OSENTITYTYPE,OSENTITYID,OS,OPTIONENTITYTYPE,OPTIONENTITYID,                           \r\n" +
	      "  COMPATIBILITYPUBLISHINGFLAG,RELATIONSHIPTYPE,PUBLISHFROM,PUBLISHTO                    \r\n" +
	      "  )                                                                                     \r\n" +
	      "  select distinct 'A',current timestamp,current timestamp,BRANDCD,SYSTEMENTITYTYPE,     \r\n" +
	      "       SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OKTOPUB,                   \r\n" +
	      "       OSENTITYTYPE,OSENTITYID,OS,OptionENTITYTYPE,OptionENTITYID,                      \r\n" +
	      "       COMPATIBILITYPUBLISHINGFLAG,RELATIONSHIPTYPE,PUBLISHFROM,PUBLISHTO               \r\n" +
	      "  from                                                                                  \r\n" +
	      "  (                                                                                     \r\n" +
	      "  	select                                                                             \r\n" +
	      "   	    SystemGroup.SystemId              as SYSTEMENTITYID,                           \r\n" +
	      "  	    SystemGroup.SystemType            as SYSTEMENTITYTYPE,                         \r\n" +
	      "  	    SystemGroup.OSLEVEL               as SYSTEMOS,                                 \r\n" +
	      "  	    case SystemGroup.OSLEVEL                                                       \r\n" +
	      "  	    when '"+OSIndependent+"' then 'ALL'                                            \r\n" +
	      "  	    when '' then 'ALL'                                                             \r\n" +
	      "  	    else SystemGroup.OSLEVEL end as SYSTEMOS2,                                     \r\n" +
	      "  	    SystemGroup.GroupId               as GROUPENTITYID,                            \r\n" +
	      "  	    SystemGroup.GroupType             as GROUPENTITYTYPE,                          \r\n" +
	      "  	    SystemGroup.OKTOPUB               as OKTOPUB,                                  \r\n" +
	      "  	    SystemGroup.BRANDCD               as BRANDCD,                                  \r\n" +
	      "  	    OSOption.OSId                     as OSENTITYID,                               \r\n" +
	      "  	    OSOption.OSType                   as OSENTITYTYPE,                             \r\n" +
	      "  	    OSOption.OS                       as OS,                                       \r\n" +
	      "  	    case OSOption.OS                                                               \r\n" +
	      "  	    when '"+OSIndependent+"' then 'ALL'                                            \r\n" +
	      "  	    when '' then 'ALL'                                                             \r\n" +
	      "  	    else OSOption.OS end as OS2,                                                   \r\n" +
	      "  	    OSOption.OSOPTIONId               as OSOPTIONENTITYID,                         \r\n" +
	      "  	    OSOption.OSOPTIONType             as OSOPTIONENTITYTYPE,                       \r\n" +
	      "  	    OSOption.COMPATPUBFLG             as COMPATIBILITYPUBLISHINGFLAG,              \r\n" +
	      "        OSOption.RELTYPE                  as RELATIONSHIPTYPE,                          \r\n" +
	      "        OSOption.PUBFROM                  as PUBLISHFROM,                               \r\n" +
	      "        OSOption.PUBTO                    as PUBLISHTO,                                 \r\n" +
	      "  	    OSOption.OptionId                 as OptionENTITYID,                           \r\n" +
	      "  	    OSOption.OptionType               as OptionENTITYTYPE,                         \r\n" +
	      "  	    case OSOPTION.OSLEVEL                                                          \r\n" +
	      "  	    when '"+OSIndependent+"' then 'ALL'                                            \r\n" +
	      "  	    when '' then 'ALL'                                                             \r\n" +
	      "  	    else OSOPTION.OSLEVEL end as OPTIONOS2                                         \r\n" +
	      "  	from                                                                               \r\n" +
	      "  	(                                                                                  \r\n" +
	      "    	select                                                                             \r\n" +
	      "    	   Group.entityid                   as GroupId,                                    \r\n" +
	      "    	   Group.entityType                 as GroupType,                                  \r\n" +
	      "    	   Group.OKTOPUB                    as OKTOPUB,                                    \r\n" +
	      "    	   Group.BRANDCD                    as BRANDCD,                                    \r\n" +
	      "    	   System.entityid                  as SystemId,                                   \r\n" +
	      "    	   System.entityType                as SystemType,                                 \r\n" +
	      "    	   System.OSLEVEL                   as OSLEVEL                                     \r\n" +
	      "    	from GBLI.WWCOMPATGROUP              as Group                                      \r\n" +
	      "    	inner join opicm.relator            as SEOCGBDL on                                 \r\n" +
	      "    	    SEOCGBDL.entity1type            = 'SEOCG' and                                  \r\n" +
	      "    	    SEOCGBDL.entity2type            = 'LSEOBUNDLE' and                             \r\n" +
	      "    	    SEOCGBDL.entity1id              = Group.entityid and                           \r\n" +
	      "    	    SEOCGBDL.valto                  > current timestamp and                        \r\n" +
	      "    	    SEOCGBDL.effto                  > current timestamp and                        \r\n" +
	      "    	    SEOCGBDL.ENTERPRISE             = 'SG'                                         \r\n" +
	      "    	inner join GBLI.WWCOMPATSYSTEM       as System on                                  \r\n" +
	      "    	    System.entitytype               = 'LSEOBUNDLE' and                             \r\n" +
	      "    	    System.entityid                 = SEOCGBDL.entity2id                           \r\n" +
	      "    	where                                                                              \r\n" +
	      "    	    Group.entitytype                = 'SEOCG'                                      \r\n" +
	      "    	union all                                                                          \r\n" +
	      "    	select                                                                             \r\n" +
	      "    	   Group.entityid                   as GroupId,                                    \r\n" +
	      "    	   Group.entityType                 as GroupType,     	                           \r\n" +
	      "    	   Group.OKTOPUB                    as OKTOPUB,                                    \r\n" +
	      "    	   Group.BRANDCD                    as BRANDCD,                                    \r\n" +
	      "    	   System.entityid                  as SystemId,                                   \r\n" +
	      "    	   System.entityType                as SystemType,                                 \r\n" +
	      "    	   System.OSLEVEL                   as OSLEVEL                                     \r\n" +
	      "    	from GBLI.WWCOMPATGROUP              as Group                                      \r\n" +
	      "    	inner join opicm.relator            as SEOCGMDL on                                 \r\n" +
	      "    	    SEOCGMDL.entity1type            = 'SEOCG' and                                  \r\n" +
	      "    	    SEOCGMDL.entity2type            = 'MODEL'  and                                 \r\n" +
	      "    	    SEOCGMDL.entity1id              = Group.entityid and                           \r\n" +
	      "    	    SEOCGMDL.valto                  > current timestamp and                        \r\n" +
	      "    	    SEOCGMDL.effto                  > current timestamp and                        \r\n" +
	      "    	    SEOCGMDL.ENTERPRISE             = 'SG'                                         \r\n" +
	      "    	 inner join GBLI.WWCOMPATSYSTEM      as System on                                  \r\n" +
	      "    	    System.entitytype               = 'MODEL' and                                  \r\n" +
	      "    	    System.entityid                 = SEOCGMDL.entity2id                           \r\n" +
	      "    	 where                                                                             \r\n" +
	      "    	    Group.entitytype                = 'SEOCG'                                      \r\n" +
	      "    	 union all                                                                         \r\n" +
	      "    	 select                                                                            \r\n" +
	      "    	   Group.entityid                   as GroupId,                                    \r\n" +
	      "    	   Group.entityType                 as GroupType,     	                           \r\n" +
	      "    	   Group.OKTOPUB                    as OKTOPUB,                                    \r\n" +
	      "    	   Group.BRANDCD                    as BRANDCD,                                    \r\n" +
	      "    	   System.entityid                  as SystemId,                                   \r\n" +
	      "    	   System.entityType                as SystemType,                                 \r\n" +
	      "    	   System.OSLEVEL                   as OSLEVEL                                     \r\n" +
	      "    	 from GBLI.WWCOMPATGROUP             as Group                                      \r\n" +
	      "    	 inner join opicm.relator           as SEOCGSEO on                                 \r\n" +
	      "    	    SEOCGSEO.entity1type            = 'SEOCG' and                                  \r\n" +
	      "    	    SEOCGSEO.entity2type            = 'WWSEO'  and                                 \r\n" +
	      "    	    SEOCGSEO.entity1id              = Group.entityid and                           \r\n" +
	      "    	    SEOCGSEO.valto                  > current timestamp and                        \r\n" +
	      "    	    SEOCGSEO.effto                  > current timestamp and                        \r\n" +
	      "    	    SEOCGSEO.ENTERPRISE             = 'SG'                                         \r\n" +
	      "    	 inner join GBLI.WWCOMPATSYSTEM      as System on                                  \r\n" +
	      "    	    System.entitytype               = 'WWSEO' and                                  \r\n" +
	      "    	    System.entityid                 = SEOCGSEO.entity2id                           \r\n" +
	      "    	 where                                                                             \r\n" +
	      "    	    Group.entitytype                = 'SEOCG'                                      \r\n" +
	      "    )                                                                                   \r\n" +
	      "    as SystemGroup                                                                      \r\n" +
	      "    inner join opicm.relator              as GroupOS on                                 \r\n" +
	      "          GroupOS.entity1type               = 'SEOCG' and                               \r\n" +
	      "          GroupOS.entity2type               = 'SEOCGOS'  and                            \r\n" +
	      "          GroupOS.entity1id                 = SystemGroup.GroupId and                   \r\n" +
	      "          GroupOS.valto                     > current timestamp and                     \r\n" +
	      "          GroupOS.effto                     > current timestamp and                     \r\n" +
	      "          GroupOS.ENTERPRISE                = 'SG'                                      \r\n" +
	      "    inner join                                                                          \r\n" +
	      "    (                                                                                   \r\n" +
	      "    	 select                                                                            \r\n" +
	      "    	     OS.entityid                      as OSId,                                     \r\n" +
	      "    	     OS.entitytype                    as OSType,                                   \r\n" +
	      "    	     OS.OS                            as OS,                                       \r\n" +
	      "    	     OSOption.entityid                as OSOPTIONId,                               \r\n" +
	      "    	     OSOption.entitytype              as OSOPTIONType,                             \r\n" +
	      "    	     OSOP.COMPATPUBFLG              as COMPATPUBFLG,                               \r\n" +
	      "    	     OSOP.RELTYPE                   as RELTYPE,                                    \r\n" +
	      "    	     OSOP.PUBFROM                   as PUBFROM,                                    \r\n" +
	      "    	     OSOP.PUBTO                     as PUBTO,                                      \r\n" +
	      "    	     Option.entityid                  as OptionId,                                 \r\n" +
	      "    	     Option.entitytype                as OptionType,                               \r\n" +
	      "    	     Option.OSLEVEL                   as OSLEVEL                                   \r\n" +
	      "    	 from GBLI.WWCOMPATOS                as OS                                         \r\n" +
	      "    	 inner join opicm.relator           as OSOption on                                 \r\n" +
	      "    	     OSOption.entity1type            = 'SEOCGOS' and                               \r\n" +
	      "    	     OSOption.entity2type            = 'LSEOBUNDLE'  and                           \r\n" +
	      "    	     OSOption.entity1id              = OS.entityid and                             \r\n" +
	      "    	     OSOption.valto                  > current timestamp and                       \r\n" +
	      "    	     OSOption.effto                  > current timestamp and                       \r\n" +
	      "    	     OSOption.ENTERPRISE             = 'SG'                                        \r\n" +
	      "    	 inner join GBLI.WWCOMPATOSOP        as OSOP  on                                   \r\n" +
	      "    	     OSOP.entitytype                 = OSOption.entitytype and                     \r\n" +
	      "    	     OSOP.entityid                   = OSOption.entityid                           \r\n" +
	      "    	 inner join GBLI.WWCOMPATOPTION      as Option on                                  \r\n" +
	      "    	     Option.entitytype               = 'LSEOBUNDLE' and                            \r\n" +
	      "    	     Option.entityid                 = OSOption.entity2id                          \r\n" +
	      "    	 where                                                                             \r\n" +
	      "    	     OS.entitytype                   = 'SEOCGOS'                                   \r\n" +
//	      "    	 union all                                                                         \r\n" +
//	      "    	 select                                                                            \r\n" +
//	      "    	     OS.entityid                    as OSId,                                       \r\n" +
//	      "    	     OS.entitytype                  as OSType,                                     \r\n" +
//	      "    	     OS.OS                          as OS,                                         \r\n" +
//	      "    	     OSOption.entityid              as OSOPTIONId,                                 \r\n" +
//	      "    	     OSOption.entitytype            as OSOPTIONType,                               \r\n" +
//	      "    	     OSOP.COMPATPUBFLG              as COMPATPUBFLG,                               \r\n" +
//	      "    	     OSOP.RELTYPE                   as RELTYPE,                                    \r\n" +
//	      "    	     OSOP.PUBFROM                   as PUBFROM,                                    \r\n" +
//	      "    	     OSOP.PUBTO                     as PUBTO,                                      \r\n" +
//	      "    	     Option.entityid                as OptionId,                                   \r\n" +
//	      "    	     Option.entitytype              as OptionType,                                 \r\n" +
//	      "    	     Option.OSLEVEL                 as OSLEVEL                                     \r\n" +
//	      "    	 from GBLI.WWCOMPATOS                as OS                                         \r\n" +
//	      "    	 inner join opicm.relator           as OSOption on                                 \r\n" +
//	      "    	     OSOption.entity1type            = 'SEOCGOS' and                               \r\n" +
//	      "    	     OSOption.entity2type            = 'SVCSEO'  and                               \r\n" +
//	      "    	     OSOption.entity1id              = OS.entityid and                             \r\n" +
//	      "    	    OSOption.valto                  > current timestamp and                        \r\n" +
//	      "    	    OSOption.effto                  > current timestamp and                        \r\n" +
//	      "    	    OSOption.ENTERPRISE             = 'SG'                                         \r\n" +
//	      "    	 inner join GBLI.WWCOMPATOSOP        as OSOP  on                                   \r\n" +
//	      "    	    OSOP.entitytype                 = OSOption.entitytype and                      \r\n" +
//	      "    	    OSOP.entityid                   = OSOption.entityid                            \r\n" +
//	      "    	 inner join GBLI.WWCOMPATOPTION      as Option on                                  \r\n" +
//	      "    	    Option.entitytype               = 'SVCSEO' and                                 \r\n" +
//	      "    	    Option.entityid                 = OSOption.entity2id                           \r\n" +
//	      "    	 where                                                                             \r\n" +
//	      "    	    OS.entitytype                   = 'SEOCGOS'                                    \r\n" +
	      "    	 union all                                                                         \r\n" +
	      "    	 select                                                                            \r\n" +
	      "    	     OS.entityid                    as OSId,                                       \r\n" +
	      "    	     OS.entitytype                  as OSType,                                     \r\n" +
	      "    	     OS.OS                          as OS,                                         \r\n" +
	      "    	     OSOption.entityid              as OSOPTIONId,                                 \r\n" +
	      "    	     OSOption.entitytype            as OSOPTIONType,                               \r\n" +
	      "    	     OSOP.COMPATPUBFLG              as COMPATPUBFLG,                               \r\n" +
	      "    	     OSOP.RELTYPE                   as RELTYPE,                                    \r\n" +
	      "    	     OSOP.PUBFROM                   as PUBFROM,                                    \r\n" +
	      "    	     OSOP.PUBTO                     as PUBTO,                                      \r\n" +
	      "    	     Option.entityid                as OptionId,                                   \r\n" +
	      "    	     Option.entitytype              as OptionType,                                 \r\n" +
	      "    	     Option.OSLEVEL                 as OSLEVEL                                     \r\n" +
	      "    	 from GBLI.WWCOMPATOS                as OS                                         \r\n" +
	      "    	 inner join opicm.relator           as OSOption on                                 \r\n" +
	      "    	    OSOption.entity1type            = 'SEOCGOS' and                                \r\n" +
	      "    	    OSOption.entity2type            = 'WWSEO'  and                                 \r\n" +
	      "    	    OSOption.entity1id              = OS.entityid and                              \r\n" +
	      "    	    OSOption.valto                  > current timestamp and                        \r\n" +
	      "    	    OSOption.effto                  > current timestamp and                        \r\n" +
	      "    	    OSOption.ENTERPRISE             = 'SG'                                         \r\n" +
	      "    	 inner join GBLI.WWCOMPATOSOP        as OSOP  on                                   \r\n" +
	      "    	    OSOP.entitytype                 = OSOption.entitytype and                      \r\n" +
	      "    	    OSOP.entityid                   = OSOption.entityid                            \r\n" +
	      "    	 inner join GBLI.WWCOMPATOPTION      as Option on                                  \r\n" +
	      "    	    Option.entitytype               = 'WWSEO' and                                  \r\n" +
	      "    	    Option.entityid                 = OSOption.entity2id                           \r\n" +
	      "    	 where                                                                             \r\n" +
	      "    	    OS.entitytype                   = 'SEOCGOS'                                    \r\n" +
	      "    	                                                                                   \r\n" +
	      "    	 ) as OSOption on                                                                  \r\n" +
	      "    	    OSOption.OSType                 = 'SEOCGOS' and                                \r\n" +
	      "    	    OSOption.OSId                   = GroupOS.entity2id                            \r\n" +
	      "  ) as temp                                                                             \r\n" +
	      "  where (                                                                               \r\n" +
	      "    temp.OS2='ALL'                                                                      \r\n" +
	      //"    temp.OS2='ALL' or                                                                 \r\n" +
	      //"    ((temp.OS2=temp.SYSTEMOS2 or temp.SYSTEMOS2='ALL')                                \r\n" +
	      //"    and (temp.OS2=temp.OPTIONOS2 or temp.OPTIONOS2='ALL'))                            \r\n" +
	      "  ) and temp.OKTOPUB!='DEL' and temp.COMPATIBILITYPUBLISHINGFLAG!='DEL'                 \r\n" +
	      "  with ur                                                                               \r\n" ;
	


}
