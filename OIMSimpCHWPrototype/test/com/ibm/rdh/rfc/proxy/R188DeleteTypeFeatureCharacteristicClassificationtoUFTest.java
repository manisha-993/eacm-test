//package com.ibm.rdh.rfc.proxy;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.junit.Assert;
//import org.junit.Test;
//
//import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
//import com.ibm.pprds.epimshw.util.LogManager;
//import com.ibm.rdh.chw.entity.CHWAnnouncement;
//import com.ibm.rdh.chw.entity.TypeFeature;
//
//public class R188DeleteTypeFeatureCharacteristicClassificationtoUFTest extends RdhRestProxyTest{
//	private static Logger logger = LogManager.getLogManager()
//			.getPromoteLogger();
//	
//	@Test
//	public void testR188(){
//		try{
//			TypeFeature tfObj=new TypeFeature();
//			tfObj.setType("EACF");
//			tfObj.setFeature("1000");
//			CHWAnnouncement chwA=new CHWAnnouncement();
//			chwA.setAnnDocNo("123401");
//			String pimsIdentity="C";
//			
//			RdhRestProxy rfcProxy=new RdhRestProxy();
//			rfcProxy.r188(tfObj, chwA, pimsIdentity);
//			
//			String objectId="MK_" + tfObj.getType() + "_" + tfObj.getFeature();
//			String zdm_class="MK_" + tfObj.getType() + "_UF_" + tfObj.getFeature().substring(0,1) + "000";
//			
//			Map<String, String> map = new HashMap<String, String>();
//			Map<String, Object> rowDetails;
//
//			map.clear();
//			map.put("MANDT", "'" + Constants.MANDT + "'");
//			map.put("ZDMOBJKEY", "'300" + zdm_class + "'");
//			map.put("ZDMOBJTYP", "'CLS'");
//			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
//			assertNotNull(rowDetails);
//			
//			map.clear();
//			map.put("MANDT", "'" + Constants.MANDT + "'");
//			map.put("ACTIV_ID", "'Z_DM_SAP_CHAR_DELETE'");
//			map.put("OBJECT_ID", "'" + objectId + "'");
//			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
//			assertNotNull(rowDetails);
//			String sessionId = (String) rowDetails.get("ZSESSION");
//			String status = (String) rowDetails.get("STATUS");
//			assertEquals(status, "success");
//
//			map.clear();
//			map.put("ZSESSION", "'" + sessionId + "'");
//			map.put("TEXT", "'Association of characteristic  " + objectId + " successfully removed from classification  " + zdm_class + "/300'");
//			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
//			assertNotNull(rowDetails);
//		}catch (HWPIMSAbnormalException ex) {
//			logger.info("error message= " + ex.getMessage());
//			Assert.fail("error message= " + ex.getMessage());
//		} catch (Exception e) {
//			e.getStackTrace();
//			Assert.fail("There is some error :" + e.getMessage());
//		} finally {
//
//		}
//	}
//}
