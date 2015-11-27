package com.ibm.rdh.rfc.proxy;



import java.util.Vector;

import org.apache.log4j.Logger;

import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;




import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.pprds.epimshw.util.RfcLogger;
import com.ibm.rdh.chw.caller.R100createTypeMaterialBasicView;
import com.ibm.rdh.chw.caller.R101createGenericPlantViewforMaterial;
import com.ibm.rdh.chw.caller.R102createSalesViewforMaterial;
import com.ibm.rdh.chw.caller.R103create001ClassificationForMGCommon;
import com.ibm.rdh.chw.caller.R166createSTPPlantViewForMaterial;
import com.ibm.rdh.chw.caller.R189createCFIPlantViewForType;
import com.ibm.rdh.chw.caller.Rfc;
import com.ibm.rdh.chw.caller.RfcReturnSeverityCodes;

//import com.ibm.rdh.rfc.ReturnDataObjectR001;
/**
 * Read only Rfc proxy.
 * Retrieves data via Rfcs but does not modify any SAP objects.
 * Proxy is responsible for logging rfc actions.
 * @author waltond
 */
public class RdhRestProxy extends RfcProxy implements RfcReturnSeverityCodes
{
		
	private int _sapRetryCount;
	private long _sapRetrySleepMillis;	
	private RfcLogger rfcLogger;
	
	protected static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	public RdhRestProxy()
	{ 
		super() ; 	
		setRfcFactory( new RfcFactory()) ; 
	}
	
	public RdhRestProxy(RfcLogger rfcLogger) {
		this();
		this.rfcLogger = rfcLogger;
	}
	

	@Override
	public void r100(CHWAnnouncement chwA, TypeModel typeModel,
			CHWGeoAnn chwAg, String newFlag, TypeModelUPGGeo tmUPGObj,
			String FromToType, String pimsIdentity) throws Exception {
		R100createTypeMaterialBasicView r = getFactory().getr100(chwA, typeModel,
				 chwAg,  newFlag,  tmUPGObj,
				 FromToType,  pimsIdentity) ;
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r); 				
	}	
	
	@Override
	public void r101(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String newFlag,
			String loadingGrp, TypeModelUPGGeo tmUPGObj, String FromToType,
			String pimsIdentity, String plantValue) throws Exception {
		R101createGenericPlantViewforMaterial r = getFactory().getr101(chwA,
				typeModel, chwAg,  newFlag, loadingGrp, 
				tmUPGObj, FromToType,  pimsIdentity, plantValue) ;
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r); 				
	}	
	
	public void r102(CHWAnnouncement chwA, TypeModel typeModel,
			String sapPlant, String newFlag, TypeModelUPGGeo tmUPGObj,
			String fromToType, String pimsIdentity, String flfilcd,
			String salesOrg, Vector taxCntryList) throws Exception {
		R102createSalesViewforMaterial r= getFactory().getr102(chwA, typeModel, sapPlant,  newFlag, tmUPGObj, fromToType, 
				pimsIdentity, flfilcd, salesOrg, taxCntryList);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	public void r103(TypeModel typeModel, String newFlag, CHWAnnouncement chwA,
			TypeModelUPGGeo tmUPGObj, String fromToType, String pimsIdentity) throws Exception {
		R103create001ClassificationForMGCommon r=getFactory().getr103(typeModel, newFlag, chwA, tmUPGObj,
				fromToType, pimsIdentity);
			logPromoteInfoMessage(r);
			r.evaluate();
			logPromoteResultMessage(r);
	}
	public void r166(CHWAnnouncement chwA, TypeModel typeModel,
			CHWGeoAnn chwAg, String storageLocation, String newFlag) throws Exception {
		R166createSTPPlantViewForMaterial r=getFactory().getr166(chwA,
			typeModel, chwAg, storageLocation, newFlag);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}
	public void r189(CHWAnnouncement chwA,
			TypeModel typeModel, String sapPlant, String newFlag,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)throws Exception {
		R189createCFIPlantViewForType r = getFactory().getr189(chwA,
			typeModel,  sapPlant, newFlag,
			tmUPGObj, FromToType,  pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
		
	}
	
	
//	public ArrayList r060( SWO swo, Announcement ann, Collection plants ) throws Exception { 
//		R060ReadPlantViewForMaterial r = getRfcFactory().getR060( swo, ann, plants ) ; 
//		logPromoteInfoMessage(r);
//		ArrayList marcArray = r.evaluate();
//		return marcArray;
//	}	
//
//	public void r061( SWO swo, Announcement ann, String plant ) throws Exception { 
//		R061MaintainPlantViewForProfitCenter r = getRfcFactory().getR061( swo, ann, plant ) ; 
//		logPromoteInfoMessage(r);
//	}	

	protected int getRetryCount() {
		return _sapRetryCount ; 
	}
	protected long getRetrySleepMillis() {
		return _sapRetrySleepMillis ; 
	}

	/**
	 * Log a warning message to the Promote Message log(s)
	 */	
	protected void logPromoteWarnMessage(String msgNum, String msgText)
		throws HWPIMSAbnormalException
	{
		
		if (null != rfcLogger)
		{
			rfcLogger.warn(msgNum+msgText);
		}
		
		if (null != logger)
		{
			logger.warn(msgNum+msgText);
		}
	}


	/**
	 * Log an error message to the Promote Message log(s)
	 */	
	protected void logPromoteErrorMessage(String msgNum, String msgText)
		throws HWPIMSAbnormalException
	{
		if (null != rfcLogger)
		{
			rfcLogger.error(msgNum + msgText);
		}
		if (null != logger)
		{
			logger.error(msgNum + msgText);
		}
	}

	protected void logPromoteInfoMessage(Rfc r)
		throws HWPIMSAbnormalException
	{
		logPromoteInfoMessage(r.getMsgNum(), r.getTaskDescription(), r.getRfcInfo());
	}


	/**
	 * Log an informational message to the Promote Message log(s)
	 */	
	protected void logPromoteInfoMessage(String msgNum, String msgText)
		throws HWPIMSAbnormalException
	{
		if (null != rfcLogger)
		{
			rfcLogger.info(msgNum + msgText);
		}
		if (null != logger)
		{
			logger.info(msgNum + msgText);
		}
	}

	/**
	 * Log an informational message to the Promote Message log(s)
	 * Message can have a parameter list which may be logged
	 * differently from the message body.  See PromoteMessageLogger
	 * for details.
	 */	
	protected void logPromoteInfoMessage(String msgNum, String msgText, String parameters)
		throws HWPIMSAbnormalException
	{
		if (null != rfcLogger)
		{
			rfcLogger.info(msgNum + msgText + parameters);
		}
		if (null != logger)
		{
				logger.info(msgNum +msgText + parameters);
		}
	}

	protected void logPromoteResultMessage(Rfc r)
		throws HWPIMSAbnormalException
	{
		logPromoteResultMessage(r.getMsgNum(), r.getSeverity(), r.getExecutionResultMessage());
	}; 		
	
	/**
	 * Log the results of an rfc call
	 */
	protected void logPromoteResultMessage(String msgNum, int severity, String msgText) 
		throws HWPIMSAbnormalException
	{
		if (severity == ERROR)
		{
			this.logPromoteErrorMessage(msgNum, msgText);
		}
		else if (severity == WARNING)
		{
			this.logPromoteWarnMessage(msgNum, msgText);
		}
		else if (severity == INFO)
		{
			this.logPromoteInfoMessage(msgNum, msgText);
		}
	}


	

	
}
