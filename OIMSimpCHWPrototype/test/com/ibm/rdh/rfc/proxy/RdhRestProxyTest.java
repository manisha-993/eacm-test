package com.ibm.rdh.rfc.proxy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.rfc.util.Logger;
import com.ibm.rdh.rfc.util.OutputData;
import com.ibm.rdh.rfc.util.RdhServiceUtil;
import com.ibm.rdh.rfc.Zdm_mat_psales_statusTableRow;

public class RdhRestProxyTest extends Assert {
	
	private static Logger logger = Logger.getInstance(RdhRestProxyTest.class);

	public String date_forever = "99991231";
	
	String r002_zdmFrdat = "20100331";
	
	static RdhRestProxy rdhRestProxy;
	
	Connection conn = null;
	
	
	public static final String GEO_US = "US";	
	public static final String GEO_LA = "LA";
//	private static final String GEO_CA = "CAN";
//	private static final String GEO_APAN = "JP";
//	private static final String GEO_EMEA = "EMEA";
//	private static final String GEO_AP = "AP";
//	private static final String GEO_CHINA = "CH";

	@BeforeClass
	/**
	 * A method should be created with the @BeforeClass annotation 
	 * which creates the initial SWO domain model 
	 * so it can be used by all test case methods.
	 */
    public static void onceExecutedBeforeAll() {		
		System.out.println("@BeforeClass: onceExecutedBeforeAll");		
//		Logfactory.getRfcLog().info("onceExecutedBeforeAll db successfully");
    	
    }
 
    @Before
    /**
     * A method should be created with the @Before annotation 
     * which connects to the RDH DB via the RdhRfcProxy 
     * before each test case method
     */
    public void executedBeforeEach() {
    	try {
    		ConfigManager cm = ConfigManager.getConfigManager();
    		//add
    		String LOCAL_REAL_PATH = ".\\properties\\dev";
    		cm.put(PropertyKeys.KEY_SYSTEM_REAL_PATH, LOCAL_REAL_PATH);
    		cm.addAllConfigFiles();
    		conn = ConnectionFactory.getConnection();			
			rdhRestProxy = new RdhRestProxy();
			
		} catch (ClassNotFoundException e) {
			logger.info("not fund class");
			//e.printStackTrace();
		} catch (SQLException ex) {
			assertTrue(ex instanceof SQLException);
			assertTrue(ex.getMessage().contains("User ID or Password invalid"));	
			logger.info("User ID or Password invalid");
		}
    	System.out.println("@Before: executedBeforeEach");
    }
    
    @After
    /**
     * A method should be created with the @After annotation 
     * which closes the RDH DB connection after each @Test method completes
     */
    public void executedAfterEach() {
    	if(conn != null)
        {
            try {
            	//it need commit the conn and then close it for the transaction
            	conn.commit();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }    	
        System.out.println("@After: executedAfterEach");
    }
 
    @AfterClass
    /**
     * A method should be created with the @AfterClass annotation to make sure 
     * the RDH DB connection is closed after exercising all test case methods.
     */
    public static void onceExecutedAfterAll() {
    	System.out.println("@AfterClass: onceExecutedAfterAll");
    }
   
    /**
     * delete the data in all tables
     * @param prodctID
     */
    public int deleteDataMatmCreate(String prodctID){
    	//table list
    	//MARA, MAKT, MARM, MARC, MVKE,  MLAN,
   			
		//first delete the exist data in the table
		int t1 = SqlHelper.runUpdateSql("delete from SAPR3.MARA where mandt='"+Constants.MANDT+"' and matnr='"+prodctID+"'", conn);
		int t2 = SqlHelper.runUpdateSql("delete from SAPR3.MAKT where mandt='"+Constants.MANDT+"' and matnr='"+prodctID+"'", conn);
		int t3 = SqlHelper.runUpdateSql("delete from SAPR3.MARM where mandt='"+Constants.MANDT+"' and matnr='"+prodctID+"'", conn);
		int t4 = SqlHelper.runUpdateSql("delete from SAPR3.MARC where mandt='"+Constants.MANDT+"' and matnr='"+prodctID+"'", conn);
		int t5 = SqlHelper.runUpdateSql("delete from SAPR3.MVKE where mandt='"+Constants.MANDT+"' and matnr='"+prodctID+"'", conn);
		int t6 = SqlHelper.runUpdateSql("delete from SAPR3.MLAN where mandt='"+Constants.MANDT+"' and matnr='"+prodctID+"'", conn);
		
		if(t1>=0 && t2>=0 && t3>=0 && t4>=0 && t5>=0 && t6>=0){
			return 0;
		}else{
			return -1;
		}
    	
    }

    public int deleteDataClassicationMaint(String prodctID){
    	//table list
    	//KSSK, INOB, AUSP
   			
		//first delete the exist data in the table
		int t1 = SqlHelper.runUpdateSql("delete from SAPR3.KSSK where mandt='"+Constants.MANDT+"' and objek='"+prodctID+"'", conn);
		int t2 = SqlHelper.runUpdateSql("delete from SAPR3.INOB where mandt='"+Constants.MANDT+"' and objek='"+prodctID+"'", conn);
		int t3 = SqlHelper.runUpdateSql("delete from SAPR3.AUSP where mandt='"+Constants.MANDT+"' and objek='"+prodctID+"'", conn);
		
		if(t1>=0 && t2>=0 && t3>=0){
			return 0;
		}else{
			return -1;
		}
    	
    }
//    /**
//     *  input matnr
//     * @param prodctID
//     * @return
//     */
//    public int prepareDeleteData(String prodctID, String Insert_changenr){
//    	swo = createSWO();
//    	swo.setProductID(prodctID);
//    	//first delete the exist data in the table
//		int t1 = SqlHelper.runUpdateSql("delete from SAPR3.MVKE where mandt='"+Constants.MANDT+"' and matnr='"+swo.getProductID()+"' with ur", conn);
//		int t2 = SqlHelper.runUpdateSql("delete from SAPR3.PCDHDR as pcdhdr where pcdhdr.objectclas = 'MATERIAL_N' "
//				+ " and pcdhdr.objectid='"+prodctID+"'"
//				+ " and pcdhdr.udate='" + date_forever + "' "
//				+ " and pcdhdr.change_ind ='U' with ur", conn);
//		
//		int t3 = SqlHelper.runUpdateSql("delete from SAPR3.PCDPOS as pcdpos where pcdpos.objectclas = 'MATERIAL_N' "
//				+ " and pcdpos.objectid ='"+prodctID+"'"
//				+ " and pcdpos.changenr='"+Insert_changenr+"' "
//				+ " and pcdpos.tabname = 'MVKE'"
//				+ " and pcdpos.fname = 'VMSTA'"
//				+ " with ur", conn);
//		int t4 = SqlHelper.runUpdateSql("delete from SAPR3.PCDPOS as pcdpos where pcdpos.objectclas = 'MATERIAL_N' "
//				+ " and pcdpos.objectid ='"+prodctID+"'"
//				+ " and pcdpos.changenr='"+Insert_changenr+"' "
//				+ " and pcdpos.tabname = 'MVKE'"
//				+ " and pcdpos.fname = 'VMSTD'"
//				+ " with ur", conn);
//		
//		
//		
//		if(t1>=0 && t2>=0 && t3>=0 && t4>=0){
//			return 0;
//		}else{
//			return -1;
//		}    	
//    }
    

    /**
     * 
     */
    public int prepareInsertData(Zdm_mat_psales_statusTableRow mrow,String Insert_changenr){
    	//then insert the data into the table
    	String insert_mvke_sql ="insert into SAPR3.mvke(MANDT,MATNR,VKORG,VTWEG,VMSTA,VMSTD) values('"+Constants.MANDT+"','"+mrow.getMatnr()+"','"+mrow.getVkorg()+"','"+mrow.getVtweg()+"','"+mrow.getZdmCstatus()+"','"+r002_zdmFrdat+"')";
    	
    	String insert_pcdhdr_sql ="insert into SAPR3.PCDHDR(MANDANT,OBJECTCLAS,OBJECTID,UDATE,CHANGE_IND,CHANGENR) values('"+Constants.MANDT+"','MATERIAL_N','"+mrow.getMatnr()+"','"+date_forever+"','U','"+Insert_changenr+"')";
    	
    	String insert_pcdpos_sql1 ="insert into SAPR3.PCDPOS(MANDANT,OBJECTCLAS,OBJECTID,CHANGENR,TABNAME,FNAME,value_new) values('"+Constants.MANDT+"','MATERIAL_N','"+mrow.getMatnr()+"','"+Insert_changenr+"','MVKE','VMSTA','"+mrow.getZdmPstatus()+"')";
    	String insert_pcdpos_sql2 ="insert into SAPR3.PCDPOS(MANDANT,OBJECTCLAS,OBJECTID,CHANGENR,TABNAME,FNAME,value_new) values('"+Constants.MANDT+"','MATERIAL_N','"+mrow.getMatnr()+"','"+Insert_changenr+"','MVKE','VMSTD','"+r002_zdmFrdat+"')";
    	
		int t1 = SqlHelper.runUpdateSql(insert_mvke_sql, conn);
		int t2 = SqlHelper.runUpdateSql(insert_pcdhdr_sql, conn);
		int t3 = SqlHelper.runUpdateSql(insert_pcdpos_sql1, conn);
		int t4 = SqlHelper.runUpdateSql(insert_pcdpos_sql2, conn);
		
		
		if(t1>=0 && t2>=0 && t3>=0 && t4>=0){
			return 0;
		}else{
			return -1;
		}    	
    }
    
    
    
    public void beforeParking(String productid){
    	String sql_parkingtable_delete = "delete from SAPR3.ZDM_PARKTABLE where mandt = '"+Constants.MANDT+"' and ZDMRELNUM='"+productid+"'";
		int t1 = SqlHelper.runUpdateSql(sql_parkingtable_delete, conn);
		assertTrue(t1>=0);
    }
    
    public void afterParking(String productid){
    	String sql_parkingtable_select = "select count(*) as count from SAPR3.ZDM_PARKTABLE where mandt = '"+Constants.MANDT+"' and ZDMRELNUM='"+productid+"'";
    	Hashtable<String, String> parkingTable = new Hashtable<String, String>();
		parkingTable = SqlHelper.getSingleRow(sql_parkingtable_select, conn);
		String count = parkingTable.get("count");
		//assertTrue("1".equals(count)); 
		System.out.println("ZDM_PARKTABLE count=" + count);
		assertTrue(count.compareTo("1")>=0);
		
    	
    }
    
    public void afterParking(String productid, boolean isMAT){
    	String sql_parkingtable_select = "select count(*) as count from SAPR3.ZDM_PARKTABLE where mandt = '"+Constants.MANDT+"' and ZDMRELNUM='"+productid+"'";
    	Hashtable<String, String> parkingTable = new Hashtable<String, String>();
		parkingTable = SqlHelper.getSingleRow(sql_parkingtable_select, conn);
		String count = parkingTable.get("count");
		System.out.println("count="+ count);
//		assertTrue("2".equals(count));
    }
    
    /**
     *  input matnr
     * @param prodctID
     * @return
     */
//    public int prepareDeleteDataR007(String prodctID, String Insert_changenr){
//    	swo = createSWO();
//    	swo.setProductID(prodctID);
//    	//first delete the exist data in the table
//    	//mara table
//		int t1 = SqlHelper.runUpdateSql("delete from SAPR3.mara where mandt='"+Constants.MANDT+"' and matnr='"+swo.getProductID()+"' with ur", conn);
//		
//		int t2 = SqlHelper.runUpdateSql("delete from SAPR3.MVKE where mandt='"+Constants.MANDT+"' and matnr='"+swo.getProductID()+"' with ur", conn);
//		
//		// Delete the found PCDHDR and PCDPOS records since these records will be re-created 
//		//with a new planned sales status (VMSTA) and valid-from date (VMSTD) in the follow-on steps
//		int t3 = SqlHelper.runUpdateSql("delete from SAPR3.PCDHDR as pcdhdr where pcdhdr.objectclas = 'MATERIAL_N' "
//				+ " and pcdhdr.objectid='"+prodctID+"'"
//				+ " and pcdhdr.udate='" + date_forever + "' "
//				+ " and pcdhdr.change_ind !='D' with ur", conn);
//		
//		int t4 = SqlHelper.runUpdateSql("delete from SAPR3.PCDPOS as pcdpos where pcdpos.objectclas = 'MATERIAL_N' "
//				+ " and pcdpos.objectid ='"+prodctID+"'"
//				+ " and pcdpos.changenr='"+Insert_changenr+"' "
//				+ " and pcdpos.tabname = 'MVKE'"
//				+ " and pcdpos.fname in('VMSTA', 'VMSTD') "
//				+ " with ur", conn);
//	
//   	if(t1>=0 && t2>=0 && t3>=0 && t4>=0){
//			return 0;
//		}else{
//			return -1;
//		}    	
//    }
    
    /**
     * 
     */
    public int prepareInsertDataR007(Zdm_mat_psales_statusTableRow mrow,String Insert_changenr){
    	//then insert the data into the table
    	String insert_mara_sql = " insert into SAPR3.MARA(MANDT,MATNR,ERSDA,ERNAM,LAEDA,AENAM,VPSTA,PSTAT,MTART,ZEINR,ZEIAR,SPART) "
    			               + " values('"+Constants.MANDT+"','"+mrow.getMatnr()+"','20150512','SDPI','20150511','SDPI','K','K','ZMAT','RFA002','RFA','2H')";
    	
    	String insert_mvke_sql ="insert into SAPR3.mvke(MANDT,MATNR,VKORG,VTWEG,VMSTA,VMSTD) values('"+Constants.MANDT+"','"+mrow.getMatnr()+"','"+mrow.getVkorg()+"','"+mrow.getVtweg()+"','YA','"+r002_zdmFrdat+"')";
	
		int t1 = SqlHelper.runUpdateSql(insert_mara_sql, conn);
		int t2 = SqlHelper.runUpdateSql(insert_mvke_sql, conn);
		
		if(t1>=0 && t2>=0){
			return 0;
		}else{
			return -1;
		}    	
    }
	
	
	
	protected int deleteKLAHRow(String klart,String class_id,String mandt) {
		//first delete the exist data in the table
				return SqlHelper.runUpdateSql("DELETE FROM SAPR3.KLAH WHERE KLART='"+klart+"' AND CLASS ='"+class_id+"' AND MANDT='"+mandt+"'", conn);
	}
	
	protected boolean selectKLAHRow(String klart,String class_id,String mandt) {
		//first delete the exist data in the table
				ResultSet rs = SqlHelper.runQuerySql("SELECT * FROM SAPR3.KLAH where KLART='"+klart+"' AND CLASS ='"+class_id+"'AND MANDT='"+mandt+"'", conn);
				try {
					return rs.next();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return false;
	}
	
	protected boolean selectSWORRow(String klart,String class_id,String mandt) {
		
				ResultSet rs = SqlHelper.runQuerySql("SELECT * FROM SAPR3.SWOR WHERE CLINT= ( SELECT CLINT FROM SAPR3.KLAH where KLART='"+klart+"' AND CLASS ='"+class_id+"'AND MANDT='"+mandt+"')", conn);
				try {
					return rs.next();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return false;
	}
	
	protected Map<String,Object>  selectTableRow(Map<String, String> map, String tableName) throws SQLException {
		
	   	 String QueryCondition=" WHERE ";
	   	 int count=0;
	   	 for (Map.Entry<String, String> entry : map.entrySet()) {
	   			 String key = entry.getKey();
	   			 String value = entry.getValue();
	   			 if(count>0){
	   				 QueryCondition+=" AND ";
	   			 }
	   			 QueryCondition+= key+"="+value;
	   			 count++;
	   			 } 
	   		
	   		String queryStr="SELECT * FROM SAPR3."+tableName+ QueryCondition;
	   		logger.debug(queryStr);
	   		ResultSet rs = SqlHelper.runQuerySql(queryStr, conn);
	   		ResultSetMetaData rsmd = rs.getMetaData();
	   		count = 1 ; // start counting from 1 always
	   		int columncount = rsmd.getColumnCount();
	   		Map<String,Object> details=	null;
	   		if(!rs.next()){
	   			return details;
	   		}
	   		while(columncount >=count){
	   			if(details==null){
	   				details=new HashMap<String,Object>();
	   			}
	   				details.put(rsmd.getColumnName(count), rs.getObject(count));
	   				count++;
	            }
	        return details;
	        
	   	}
	
	protected ArrayList<Hashtable<String, String>> getMultiRowInfo(Map<String, String> map, String tableName) throws SQLException {
		String QueryCondition=" WHERE ";
	   	 int count=0;
	   	 for (Map.Entry<String, String> entry : map.entrySet()) {
	   			 String key = entry.getKey();
	   			 String value = entry.getValue();
	   			 if(count>0){
	   				 QueryCondition+=" AND ";
	   			 }
	   			 QueryCondition+= key+"="+value;
	   			 count++;
	   			 } 
	   		
	   		String queryStr="SELECT * FROM SAPR3."+tableName+ QueryCondition;
	   		logger.debug(queryStr);
	   		return SqlHelper.getMultiRowInfo(queryStr,conn);
		
	}
	
	protected Map<String,Object> selectMastRow(String mandt,String matnr,String werks,String stlan) throws SQLException {
		
				ResultSet rs = SqlHelper.runQuerySql("SELECT * FROM SAPR3.MAST WHERE MANDT='"+mandt+"' AND MATNR ='"+matnr+"'AND WERKS='"+werks+"'AND STLAN='"+stlan+"'", conn);
				Map<String,Object> details=null;
					if(rs.next()){
					details=	new HashMap<String,Object>();	
					details.put("MANDT", rs.getString("MANDT"));
					details.put("MATNR", rs.getString("MATNR"));
					details.put("WERKS", rs.getString("WERKS"));
					details.put("STLAN", rs.getString("STLAN"));
					details.put("STLNR", rs.getString("STLNR"));
					details.put("STLAL", rs.getString("STLAL"));
					
					}
					
					return details;
				
			
	}
	
	protected int deleteSWORRow(String klart,String class_id,String mandt) {
		//first delete the exist data in the table
				return SqlHelper.runUpdateSql("DELETE FROM SAPR3.SWOR WHERE CLINT IN (SELECT CLINT FROM SAPR3.KLAH WHERE  KLART='"+klart+"' AND CLASS ='"+class_id+"' AND MANDT='"+mandt+"')", conn);
		
		
	}
	
	protected int deleteMaraRow(String mandt,String matnr) {
		//first delete the exist data in the table
				return SqlHelper.runUpdateSql("DELETE FROM SAPR3.MARA WHERE MANDT='"+mandt+"'AND MATNR='"+matnr+"'", conn);
		
		
	}
	protected int deleteMastRow(String mandt,String matnr,String werks,String stlan){
		String delete_mast_sql = " DELETE FROM SAPR3.MAST WHERE MANDT='"+mandt+"'AND  MATNR='"+matnr+"' AND WERKS='"+werks+"' AND STLAN='"+stlan+"'";
		return SqlHelper.runUpdateSql(delete_mast_sql, conn);
	}
	
	
	protected int deleteMarcRow(String mandt,String matnr,String werks) {
		//first delete the exist data in the table
				return SqlHelper.runUpdateSql("DELETE FROM SAPR3.MARC WHERE MANDT='"+mandt+"'AND  MATNR='"+matnr+"' AND WERKS='"+werks+"'", conn);
		
		
	}
	
	protected int 	deleteZdmDistributionRow(String mandt,String z_class,String activity,String objType){
		return SqlHelper.runUpdateSql("DELETE FROM SAPR3.ZDM_DISTRIBUTION WHERE MANDT='"+mandt+"' AND Z_CLASS='"+z_class+"' AND Z_ACTIVITY ='"+activity+"'", conn);
		 
	}
	
	protected int 	deleteParkingRow(String mandt,String z_class,String z_objType,String z_relNum){
		return SqlHelper.runUpdateSql("DELETE FROM SAPR3.ZDM_PARKTABLE WHERE MANDT='"+mandt+"' AND ZDMCLASS='"+z_class+"' AND ZDMOBJTYP ='"+z_objType+"' AND ZDMRELNUM='"+z_relNum +"'", conn);
		 
	}
	protected int 	deleteParkingRow_objKey(String mandt,String z_objKey,String z_objType,String z_relNum){
		return SqlHelper.runUpdateSql("DELETE FROM SAPR3.ZDM_PARKTABLE WHERE MANDT='"+mandt+"' AND ZDMOBJKEY='"+z_objKey+"' AND ZDMOBJTYP ='"+z_objType+"' AND ZDMRELNUM='"+z_relNum +"'", conn);
		 
	}
	protected int 	addZdmDistributionRow(String mandt,String z_class,String activity,String objType,String textType,String mestyp){
		return SqlHelper.runUpdateSql("INSERT INTO SAPR3.ZDM_DISTRIBUTION (MANDT, Z_CLASS, Z_ACTIVITY, Z_KZKFG, Z_MESTYP, Z_OBJTYP, Z_TEXT, Z_SOURCE, ZDM_SEND_ZDMCLF) VALUES ('"+mandt+"', '"+z_class+"', '"+activity+"', '', '"+mestyp+"', '"+objType+"', '"+textType+"', '', '')", conn);
		 
	}
	
	protected int 	deletezdmLogHdrAndzdmLogDtl(String mandt,String activeId,String objectId){
		SqlHelper.runUpdateSql("DELETE FROM SAPR3.ZDM_PARKTABLE WHERE ZDM_SESSION IN (SELECT ZSESSION FROM SAPR3.ZDM_LOGHDR WHERE MANDT='"+mandt+"' AND ACTIV_ID='"+activeId+"' AND OBJECT_ID ='"+objectId+"')", conn);
		SqlHelper.runUpdateSql("DELETE FROM SAPR3.ZDM_LOGDTL WHERE ZSESSION IN (SELECT ZSESSION FROM SAPR3.ZDM_LOGHDR WHERE MANDT='"+mandt+"' AND ACTIV_ID='"+activeId+"' AND OBJECT_ID ='"+objectId+"')", conn);
		return SqlHelper.runUpdateSql("DELETE FROM SAPR3.ZDM_LOGHDR WHERE MANDT='"+mandt+"' AND ACTIV_ID='"+activeId+"' AND OBJECT_ID ='"+objectId+"'", conn);
		 
	}	
	protected int 	deleteAenrAenvAeoiRow(String aennr_prefix,String andat,String aetxt) {
		SqlHelper.runUpdateSql("DELETE FROM SAPR3.AENV WHERE AENNR = (SELECT AENNR FROM SAPR3.AENR WHERE AENNR LIKE '"+aennr_prefix+"%' AND ANDAT='"+andat+"' AND AETXT ='"+aetxt+"')", conn);
		SqlHelper.runUpdateSql("DELETE FROM SAPR3.AEOI WHERE AENNR = (SELECT AENNR FROM SAPR3.AENR WHERE AENNR LIKE '"+aennr_prefix+"%' AND ANDAT='"+andat+"' AND AETXT ='"+aetxt+"')", conn);
		return SqlHelper.runUpdateSql("DELETE FROM SAPR3.AENR WHERE AENNR LIKE '"+aennr_prefix+"%' AND ANDAT='"+andat+"' AND AETXT ='"+aetxt+"'", conn);
		 
	}	
	
	
	protected int addKLAHRow(String klart,String class_id,String mandt) {
		String insertQuery ="INSERT INTO SAPR3.KLAH (MANDT, CLINT, KLART, CLASS, STATU, KLAGR, BGRSE, BGRKL, BGRKP, ANAME, ADATU, VNAME, VDATU, VONDT, BISDT, ANZUO, PRAUS, SICHT, DOKNR, DOKAR, DOKTL, DOKVR, DINKZ, NNORM, NORMN, NORMB, NRMT1, NRMT2, AUSGD, VERSD, VERSI, LEIST, VERWE, SPART, LREF3, WWSKZ, WWSSI, POTPR, CLOBK, CLMUL, CVIEW, DISST, MEINS, CLMOD, VWSTL, VWPLA, CLALT, LBREI, BNAME, MAXBL, KNOBJ, SHAD_UPDATE_TS, SHAD_UPDATE_IND, SAP_TS) " +
							"VALUES ('"+mandt+"','junittest','"+klart+"','"+class_id+"','1','','','','','HWPIMS','20071218','HWPIMS','20090316','20071218','99991231','00000','X','','','','','',' ','','','','','','00000000','00000000','00','',' ','',' ',' ',' ',' ',' ',' ','','','',' ',' ',' ',' ','000',' ',' ','000000000000000000','2009-03-25 08:57:13','U','2009-03-20 11:46:22')";
		return SqlHelper.runUpdateSql(insertQuery, conn);
	}
	

	
	protected int addSWORRow(String mandt ,String tab_SWOR_row_id) {
		String insertQuery ="INSERT INTO SAPR3.SWOR (MANDT, CLINT, SPRAS, KLPOS, KSCHL, KSCHG, SHAD_UPDATE_TS, SHAD_UPDATE_IND, SAP_TS) " +
        					"VALUES ('"+mandt+"','"+tab_SWOR_row_id+"', 'E', '1', 'Features for Type 5689', 'Features for Type 5689','2009-03-25 08:57:13','I','2009-03-20 11:46:22')";
		return SqlHelper.runUpdateSql(insertQuery, conn);
	}

	protected int deleteCABNRow(String mandt,String atnam) {
		//first delete the exist data in the table
		return SqlHelper.runUpdateSql("DELETE FROM SAPR3.CABN WHERE ATNAM='"+atnam+"' AND MANDT ='" +mandt+"'", conn);
		
	}
	
	protected int deleteCABNTRow(String mandt,String atnam) {
		//first delete the exist data in the table
		return SqlHelper.runUpdateSql("DELETE FROM SAPR3.CABNT WHERE ATINN IN (SELECT ATINN FROM SAPR3.CABN WHERE ATNAM='"+atnam+"' AND MANDT ='"+mandt+"')", conn);
		
	}

	protected int deleteRDXCustModelRow(String mandt,String zdm_class) {
		//first delete the exist data in the table
		return SqlHelper.runUpdateSql("DELETE FROM SAPR3.ZDM_RDXCUSTMODEL WHERE MANDT='"+mandt+"' AND ZDM_CLASS='"+zdm_class+"'", conn);
		
	}
	protected int addCABNRow(String mandt, String atinn, String atnam) {
		//insert CABN record
		String insertQuery ="INSERT INTO SAPR3.CABN (MANDT, ATINN, ADZHL, ATNAM )    VALUES ('"+mandt+"','"+atinn+"', '0000', '"+atnam+"')";
		return SqlHelper.runUpdateSql(insertQuery, conn);
		
	}
	
	protected int addCABNTRow(String mandt, String charact, String atinn) {
		//insert CABNT record
				String insertQuery ="INSERT INTO SAPR3.CABNT (MANDT, ATINN, SPRAS, ADZHL, ATBEZ, ATUE1, ATUE2, DATUV, TECHV, AENNR, LKENZ,  SHAD_UPDATE_IND) VALUES ('"+mandt+"', '"+atinn+"', 'E', '0000', 'Picense type', 'Pice', 'lse ', '00000000', '', '', '','I')";
		return SqlHelper.runUpdateSql(insertQuery, conn);
		
	}
	protected int addRDXCustModelRow(String mandt,String zdm_class,String rcvSystem,String zdm_system_type,String zdm_syst_default) {
		//first delete the exist data in the table
		return SqlHelper.runUpdateSql("INSERT INTO SAPR3.ZDM_RDXCUSTMODEL (MANDT, ZDM_CLASS, RCVSYSTEM, ZDM_SYSTEM_TYPE,ZDM_SYST_DEFAULT,ZDM_CREATE_USER )    VALUES ('"+mandt+"','"+zdm_class+"', '"+rcvSystem+"', '"+zdm_system_type+"', '"+zdm_syst_default+"', 'SDPI')", conn);
		
	}
	protected int deleteKSMLRow(String klart,String class_id,String mandt ) {
		//insert CABNT record
				String deleteKSMLQuery ="DELETE FROM SAPR3.KSML WHERE CLINT = ( SELECT CLINT FROM SAPR3.KLAH where KLART='"+klart+"' AND CLASS ='"+class_id+"'AND MANDT='"+mandt+"')";
		return SqlHelper.runUpdateSql(deleteKSMLQuery, conn);
		
	}
	protected int deleteT001WRow(String mandt,String werks ) {
		//delete T001W Row
				String deleteKSMLQuery ="DELETE FROM SAPR3.T001W WHERE  MANDT='"+mandt+"' AND WERKS='"+werks+"'";
		return SqlHelper.runUpdateSql(deleteKSMLQuery, conn);
		
	}
	
	
	protected int addMaraRow(String mandt,String matnr) {
		//insert Mara record
		String insert_mara_sql = " INSERT INTO SAPR3.MARA(MANDT,MATNR,ERSDA,ERNAM,LAEDA,AENAM,VPSTA,PSTAT,MTART,ZEINR,ZEIAR,SPART) "
	            + " values('"+mandt+"','"+matnr+"','20150512','SDPI','20150511','SDPI','K','K','ZMAT','RFA002','RFA','2H')";
		return SqlHelper.runUpdateSql(insert_mara_sql, conn);
		
	}
	
	protected int deleteZdmchwplcRow(String mandt,String matnr) {
		//insert Zdmchwplc record
		String insert_mara_sql = " DELETE FROM SAPR3.ZDMCHWPLC  WHERE MANDT='"+mandt+"' AND MATNR='"+matnr+"'";
		return SqlHelper.runUpdateSql(insert_mara_sql, conn);
		
	}
	
	protected int addZdmchwplcRow(String mandt,String matnr,String vkorg,String varcond) {
		//insert Zdmchwplc record
		String insert_mara_sql = " INSERT INTO SAPR3.ZDMCHWPLC (MANDT, MATNR, VKORG, VARCOND, DATBI, DATAB, VMSTA, ZDM_RFANUM, ZDM_CREATE_DATE, ZDM_CREATE_TIME, ZDM_CREATE_USER, ZDM_CREATE_IUSER, ZDM_UPDATE_DATE, ZDM_UPDATE_TIME, ZDM_UPDATE_USER, ZDM_UPDATE_IUSER, SHAD_UPDATE_IND) "
                + " VALUES ('"+mandt+"','"+matnr+"', '"+vkorg+"', '"+varcond+"', '20040906', '20051108', '', '5655KJ5', '20150921', '235605', 'SDPI', 'SDPI', '', '', '', '', '')";
		return SqlHelper.runUpdateSql(insert_mara_sql, conn);
		
	}
	
	
	protected int addT001WRow(String mandt,String werks) {
		//insert T001W record
		String insert_t001w_sql = " INSERT INTO SAPR3.T001W (IBMSNAP_INTENTSEQ, IBMSNAP_OPERATION, IBMSNAP_COMMITSEQ, IBMSNAP_LOGMARKER, MANDT, WERKS, NAME1, NAME2, STRAS, PFACH, PSTLZ, ORT01, EKORG, BWKEY, VKORG, CHAZV, FABKL, KKOWK, KORDB, BEDPL, LAND1, REGIO, COUNC, CITYC, ADRNR, IWERK, TXJCD, VTWEG, SPART, KUNNR, SPRAS, WKSOP, AWSLS, CHAZV_OLD, VLFKZ, LIFNR, BZIRK, ZONE1, TAXIW, BZQHL, LET01, LET02, LET03, TXNAM_MA1, TXNAM_MA2, TXNAM_MA3, BETOL, ACHVM, DVSART, FPRFW, J_1BBRANCH, MGVUPD, MISCH, NODETYPE, NSCHEMA, PKOSA, VTBFI) VALUES" + 
                "('', '', '', '2015-07-02 12:04:48', '"+ mandt+"', '"+werks+"', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 0, 0, 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '')";
		return SqlHelper.runUpdateSql(insert_t001w_sql, conn);
		
	}
	
	
	protected int addMarcRow(String mandt,String matnr,String werks) {
		//insert Marc record
		String insert_marc_sql = "INSERT INTO SAPR3.MARC (MANDT, MATNR, WERKS, PSTAT, LVORM, BWTTY, XCHAR, MMSTA, MMSTD, MAABC, KZKRI, EKGRP, AUSME, DISPR, DISMM, DISPO, KZDIE, PLIFZ, WEBAZ, PERKZ, AUSSS, DISLS, BESKZ, SOBSL, MINBE, EISBE, BSTMI, BSTMA, BSTFE, BSTRF, MABST, LOSFX, SBDKZ, LAGPR, ALTSL, KZAUS, AUSDT, NFMAT, KZBED, MISKZ, FHORI, PFREI, FFREI, RGEKZ, FEVOR, BEARZ, RUEZT, TRANZ, BASMG, DZEIT, MAXLZ, LZEIH, KZPRO, GPMKZ, UEETO, UEETK, UNETO, WZEIT, ATPKZ, VZUSL, HERBL, INSMK, SPROZ, QUAZT, SSQSS, MPDAU, KZPPV, KZDKZ, WSTGH, PRFRQ, NKMPR, UMLMC, LADGR, XCHPF, USEQU, LGRAD, AUFTL, PLVAR, OTYPE, OBJID, MTVFP, PERIV, KZKFK, VRVEZ, VBAMG, VBEAZ, LIZYK, BWSCL, KAUTB, KORDB, STAWN, HERKL, HERKR, EXPME, MTVER, PRCTR, TRAME, MRPPP, SAUFT, FXHOR, VRMOD, VINT1, VINT2, VERKZ, STLAL, STLAN, PLNNR, APLAL, LOSGR, SOBSK, FRTME, LGPRO, DISGR, KAUSF, QZGTP, QMATV, TAKZT, RWPRO, COPAM, ABCIN, AWSLS, SERNP, CUOBJ, STDPD, SFEPR, XMCNG, QSSYS, LFRHY, RDPRF, VRBMT, VRBWK, VRBDT, VRBFK, AUTRU, PREFE, PRENC, PRENO, PREND, PRENE, PRENG, ITARK, SERVG, KZKUP, STRGR, CUOBV, LGFSB, SCHGT, CCFIX, EPRIO, QMATA, RESVP, PLNTY, UOMGR, UMRSL, ABFAC, SFCPF, SHFLG, SHZET, MDACH, KZECH, MEGRU, MFRGR, VKUMC, VKTRW, KZAGL, SHAD_UPDATE_IND)" + 
								 " VALUES ('"+mandt+"', '"+matnr+"', '"+werks+"', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 0, 0, '', 0, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, '', '', '', '', '', '', '', '', '', '', '', '', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '', 0, 0, '', 0, '', '', 0, 0, '', 0, '', '', 0, 0, '', 0, '', '', '', 0, '', '', '', '', '', '', '', 0, 0, 0, '', '', '', '', '', '', '', '', '', '', 0, '', '', '', '', '', '', '', '', '', '', '', 0, '', '', '', '', 0, '', '', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 0, '', '', '', 0, '', '', '', '', '', '', '', 0, 0, '', 'I')";
		return SqlHelper.runUpdateSql(insert_marc_sql, conn);
		
	}
	protected int addMastRow(String mandt,String matnr,String werks,String stlan){
		String insert_mast_sql = " INSERT INTO SAPR3.MAST(MANDT,MATNR,WERKS,STLAN,STLNR,STLAL,SHAD_UPDATE_IND) "
	               + " values('"+mandt+"','"+matnr+"','"+werks+"','"+stlan+"','01943036','01','I')";
		return SqlHelper.runUpdateSql(insert_mast_sql, conn);
	}
	
	
	protected int addKSMLRow(String klart,String class_id,String mandt,String iMerk) {
		//insert KSML record
		
		//first delete the exist data in the table
		String clint = "";
		String atinn = "";
		ResultSet rs = SqlHelper.runQuerySql("SELECT * FROM SAPR3.KLAH where KLART='"+klart+"' AND CLASS ='"+class_id+"'AND MANDT='"+mandt+"'", conn);
		logger.info("RS Found");
		try {
			 rs.next();
			 clint = rs.getString("CLINT");
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet rs1 = SqlHelper.runQuerySql("SELECT * FROM SAPR3.CABN WHERE ATNAM='"+iMerk+"'", conn);
		logger.info("RS Found");
		try {
			 rs1.next();
			 atinn = rs1.getString("ATINN");
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		logger.info("Creating KSML row with CLINT " + clint);
				String addKSMLQuery ="INSERT INTO SAPR3.KSML (MANDT, CLINT, POSNR, ADZHL, ABTEI, DINKB, HERKU, IMERK, OMERK, KLART, RELEV, DATUV, TECHV, AENNR, LKENZ, VMERK, DPLEN, OFFST, BLLIN, DPTXT, CUSTR, JUSTR, COLOR, INTSF, INVER, CKBOX, INPUT, AMERK, SHAD_UPDATE_IND)VALUES ('"+mandt+"', '"+clint+"', '005', '0000', '', '', '', '"+atinn+"', '', '"+klart+"', '', '', '', '', '', '', '', 0, '', '', '', '', '', '', '', 0, 0, '', 'I')";

		return SqlHelper.runUpdateSql(addKSMLQuery, conn);
		
	}
	
	protected void setupParkTable(String swoId){
		String 	sql = " DELETE FROM SAPR3.ZDM_PARKTABLE WHERE ZDM_SESSION IN ( '1000000085', '1000000086', '1000000087')";
		 SqlHelper.runUpdateSql(sql, conn);
		 		sql = " DELETE FROM SAPR3.ZDM_LOGHDR WHERE ZSESSION IN ( '1000000085', '1000000086', '1000000087')";
		 SqlHelper.runUpdateSql(sql, conn);
		 
		 sql = " INSERT INTO SAPR3.ZDM_LOGHDR (MANDT, ZSESSION, USERID, CREATE_TIME, ACTIV_TYPE, ACTIV_ID, OBJECT_ID, STATUS) VALUES " +
				 "('030','1000000085','SDPI','2015-06-25 03:28:14','RFC','Z_DM_SAP_MATM_CREATE','5689R55','success')";
		 SqlHelper.runUpdateSql(sql, conn);
		 sql = " INSERT INTO SAPR3.ZDM_LOGHDR (MANDT, ZSESSION, USERID, CREATE_TIME, ACTIV_TYPE, ACTIV_ID, OBJECT_ID, STATUS) VALUES " +
				 "('030','1000000086','SDPI','2015-06-25 03:28:14','RFC','Z_DM_SAP_MATM_CREATE','5689R55','success')";
		 SqlHelper.runUpdateSql(sql, conn);
		 sql = " INSERT INTO SAPR3.ZDM_LOGHDR (MANDT, ZSESSION, USERID, CREATE_TIME, ACTIV_TYPE, ACTIV_ID, OBJECT_ID, STATUS) VALUES " +
				 "('030','1000000087','SDPI','2015-06-25 03:28:14','RFC','Z_DM_SAP_MATM_CREATE','5689R55','success')";
		 SqlHelper.runUpdateSql(sql, conn);
		 
		 sql = " INSERT INTO SAPR3.ZDM_PARKTABLE (MANDT, ZDMCLASS, ZDMLOGSYS, ZDMMSGTYP, ZDMOBJTYP, ZDMOBJKEY, ZDM_SESSION, ZDMRELNUM, ZDM_CHANGE_NUM, TABNAME, ZDM_STATUS, ZDM_DEPSEQ, ZDM_TEXT, ZDM_CHANGE_TYPE, ZDM_CDCHGNO, ZDM_REQ_PRIORITY, ZDM_BROADCAST, ZDM_SOURCE, ZDM_CREATE_DATE, ZDM_CREATE_TIME, ZDM_CREATE_USER, ZDM_UPDATE_DATE, ZDM_UPDATE_TIME, ZDM_UPDATE_USER, ZDM_IDOC_SESSION, ZDM_JOBNUM, ZDM_JOBNAME, ZDM_VARIANT, ZDM_SUPD_JOBNUM, ZDM_SOURCE_IDOC, ZDM_TARGET_IDOC, ZDM_SIDOC_STATUS, ZDM_TIDOC_STATUS) VALUES " +
				 "('030','MD_CSW_NA','FCQF100SW3',' ','CLS','300MK_5689_R1B_F','1000000085','"+swoId+"',' ','300MK_5689_R1B_F','H',' ',' ','CRE',' ','K',' ','HWPIMS','20150803','024230','SDPI','20150820','014433','SDPI','0000000000',' ',' ',' ',' ','0000000000000000','0000000000000000','  ','') ";
		 SqlHelper.runUpdateSql(sql, conn);
		 sql = " INSERT INTO SAPR3.ZDM_PARKTABLE (MANDT, ZDMCLASS, ZDMLOGSYS, ZDMMSGTYP, ZDMOBJTYP, ZDMOBJKEY, ZDM_SESSION, ZDMRELNUM, ZDM_CHANGE_NUM, TABNAME, ZDM_STATUS, ZDM_DEPSEQ, ZDM_TEXT, ZDM_CHANGE_TYPE, ZDM_CDCHGNO, ZDM_REQ_PRIORITY, ZDM_BROADCAST, ZDM_SOURCE, ZDM_CREATE_DATE, ZDM_CREATE_TIME, ZDM_CREATE_USER, ZDM_UPDATE_DATE, ZDM_UPDATE_TIME, ZDM_UPDATE_USER, ZDM_IDOC_SESSION, ZDM_JOBNUM, ZDM_JOBNAME, ZDM_VARIANT, ZDM_SUPD_JOBNUM, ZDM_SOURCE_IDOC, ZDM_TARGET_IDOC, ZDM_SIDOC_STATUS, ZDM_TIDOC_STATUS) VALUES " +
				 "('030','MD_CSW_NA','FCQF100SW3',' ','CLS','300MK_5689_R1B_F','1000000086','"+swoId+"',' ','300MK_5689_R1B_F','H',' ',' ','CRE',' ','K',' ','HWPIMS','20150803','024230','SDPI','20150820','014433','SDPI','0000000000',' ',' ',' ',' ','0000000000000000','0000000000000000','  ','')";
		 SqlHelper.runUpdateSql(sql, conn);
		 sql = " INSERT INTO SAPR3.ZDM_PARKTABLE (MANDT, ZDMCLASS, ZDMLOGSYS, ZDMMSGTYP, ZDMOBJTYP, ZDMOBJKEY, ZDM_SESSION, ZDMRELNUM, ZDM_CHANGE_NUM, TABNAME, ZDM_STATUS, ZDM_DEPSEQ, ZDM_TEXT, ZDM_CHANGE_TYPE, ZDM_CDCHGNO, ZDM_REQ_PRIORITY, ZDM_BROADCAST, ZDM_SOURCE, ZDM_CREATE_DATE, ZDM_CREATE_TIME, ZDM_CREATE_USER, ZDM_UPDATE_DATE, ZDM_UPDATE_TIME, ZDM_UPDATE_USER, ZDM_IDOC_SESSION, ZDM_JOBNUM, ZDM_JOBNAME, ZDM_VARIANT, ZDM_SUPD_JOBNUM, ZDM_SOURCE_IDOC, ZDM_TARGET_IDOC, ZDM_SIDOC_STATUS, ZDM_TIDOC_STATUS) VALUES " +
				 "('030','MD_CSW_NA','FCQF100SW3',' ','CLS','300MK_5689_R1B_F','1000000087','"+swoId+"',' ','300MK_5689_R1B_F','H',' ',' ','CRE',' ','K',' ','HWPIMS','20150803','024230','SDPI','20150820','014433','SDPI','0000000000',' ',' ',' ',' ','0000000000000000','0000000000000000','  ','')";
		 SqlHelper.runUpdateSql(sql, conn);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void checkRequiredInput(Hashtable reqireTable, String serviceName) throws IOException{
		Set<String> keys = reqireTable.keySet();  
        for(String key: keys){	        	
        	OutputData out = RdhServiceUtil.callService((String)reqireTable.get(key), serviceName);
        	assertEquals(4,out.getRFCRC());
        	assertTrue(out.getERROR_TEXT().contains(key));
        }		
	}

	
	
}
