package com.ibm.rdh.rfc.proxy;


import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import java.util.Date;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;


public class R100createTypeMaterialBasicViewTest extends RdhRestProxyTest {
//	private static Logger logger = Logger.getInstance(R100createTypeMaterialBasicViewTest.class);
	private static Logger logger = LogManager.getLogManager().getPromoteLogger();
	
	String XEIxx_R001 = "K";
	String Insert_ProductID   = "1234W11";
	String Notmara_ProductID  = "1234W12";
	String Notmakt_ProductID  = "1234W13";

    @Test
	/**
	 *Read the MARA table where mara.matnr = <material>.
     *If a record is found, populate the bapimatdoa and bapireturn structures as described in Output section below. 
	 */
	public void testR001QueryFound() {				
		try {
			
//			int deleteRow = prepareData(Insert_ProductID);	
//			assertEquals(deleteRow,0);
//			
//			
//			
//			String insert_mara_sql = " insert into SAPR3.MARA (MANDT,MATNR,ERSDA,ERNAM,LAEDA,AENAM,VPSTA,PSTAT,MTART,MBRSH,ZEINR,ZEIAR,SPART,matkl,prdha,meins,gewei) "
//		               + " values('"+Constants.MANDT+"','"+Insert_ProductID+"','20150512','SDPI','20150511','SDPI','K','K','ZMAT','M','RFA002','RFA','7H','000','070T100329','EA','KG')";
//			
//						
//			String insert_sqlMAKT_sql = " insert into SAPR3.MAKT (mandt,matnr,spras,maktx,maktg)"
//				       + " values('"+Constants.MANDT+"','"+Insert_ProductID+"', 'E','L N500 2.00 1MB','L N500 2.00 1MB')";
//		
			
			
//			
//			SqlHelper.runUpdateSql(insert_mara_sql, conn);
//			SqlHelper.runUpdateSql(insert_sqlMAKT_sql, conn);
			//case 1 Inset the swo into the db
			
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123456");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("4441");
			typeModel.setDiv("B1");
			typeModel.setProductHierarchy("ph");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			String newFlag = "NEW";
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			String FromToType = ""; 
			String pimsIdentity ="S";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r100(chwA, typeModel, chwAg,  newFlag, tmUPGObj, FromToType,  pimsIdentity);
			assertEquals("EA","EA");
			
			/**
			 * mara Table:  General Material Data
			 */
			//test SAPR3. mara.mtart  
//			assertEquals("EA",resultR001.getBASE_UOM());
//			assertEquals("SDPI",resultR001.getCREATED_BY());
//			assertEquals("20150512",resultR001.getCREATED_ON());
//			
//			assertEquals("7H",resultR001.getDIVISION());
//			assertEquals("M",resultR001.getIND_SECTOR());
//			assertEquals("20150511",resultR001.getLAST_CHNGE());
//			
//			assertEquals("L N500 2.00 1MB",resultR001.getMATL_DESC());	
//			assertEquals("000",resultR001.getMATL_GROUP());
//			assertEquals("ZMAT",resultR001.getMATL_TYPE());
//			
//			assertEquals("070T100329",resultR001.getPROD_HIER());
//			assertEquals("KG",resultR001.getUNIT_OF_WT());
//			assertEquals("SDPI",resultR001.getCREATED_BY());
//			
//			assertEquals("S",resultR001.getType());
//			assertEquals("Material <" + Insert_ProductID +  "> retrieved successfully.",resultR001.getMess());

			
					
		} catch(HWPIMSAbnormalException ex){
			logger.info("error message= " +ex.getMessage());	
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {
			
		}
		
    }
  
}
