package com.ibm.rdh.rfc.proxy;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R201createUpgradeValueForTypeMCCharacteristicTest extends RdhRestProxyTest{
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	
	@Test
	public void testR201_1(){
		try{
			logger.info("FROMTYPE");
			TypeModelUPGGeo typeModelUpg=new TypeModelUPGGeo();
			typeModelUpg.setFromModel("EFM");
			typeModelUpg.setFromType("EFT");
			typeModelUpg.setType("ET");
			typeModelUpg.setModel("EM");
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String FROMTOTYPE="FROMTYPE";
			String pimsIdentity="C";
			
			String charac = "MK_" + typeModelUpg.getFromType() + "_MTC";
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CHAR_MAINTAIN", charac);
			
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r201( typeModelUpg,chwA,FROMTOTYPE,pimsIdentity);
			
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + charac + "'");
			map.put("ZDMOBJTYP", "'CHR'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CHAR_MAINTAIN'");
			map.put("OBJECT_ID", "'" + charac + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Characteristic  " + charac
					+ " created/updated successfully.'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			assertNotNull(rowDetails);
			
		} catch (HWPIMSAbnormalException ex) {
			logger.info("error message= " + ex.getMessage());
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {

		}
	}
	
	@Test
	public void testR201_2(){
		try{
			logger.info("TOTYPE");
			TypeModelUPGGeo typeModelUpg=new TypeModelUPGGeo();
			typeModelUpg.setFromModel("EFM");
			typeModelUpg.setFromType("EFT");
			typeModelUpg.setType("ETY");
			typeModelUpg.setModel("EMO");
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String FROMTOTYPE="TOTYPE";
			String pimsIdentity="C";
			
			String charac = "MK_" + typeModelUpg.getType() + "_MTC";
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CHAR_MAINTAIN", charac);
			
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r201( typeModelUpg,chwA,FROMTOTYPE,pimsIdentity);
			
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + charac + "'");
			map.put("ZDMOBJTYP", "'CHR'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CHAR_MAINTAIN'");
			map.put("OBJECT_ID", "'" + charac + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Characteristic  " + charac
					+ " created/updated successfully.'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			assertNotNull(rowDetails);
			
		} catch (HWPIMSAbnormalException ex) {
			logger.info("error message= " + ex.getMessage());
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {

		}
	}

}
