package com.ibm.rdh.chw.caller;


import org.apache.log4j.Logger;

import com.ibm.pprds.epimshw.HWPIMSErrorInformation;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.TypeModel;
//import com.ibm.pprds.epimshw.util.ConfigManager;



//import com.sap.rfc.IRfcConnection;

/**
 * @author bobc
 */
public abstract class Rfc implements RfcReturnSeverityCodes
{
	
	protected static String RFC_OK_MESSAGE = "RFC execution was a Success" ;
	protected static String RFC_FAILED_MESSAGE = "RFC execution Failed" ; 
	protected static final String MSGDTL_KEY_LIST = "list";
	protected static final String CSW_PIMS_IDENTITY = "S";
	protected static final String XSW_PIMS_IDENTITY = "Y";	
	protected int sapRFCRetries = 0;
	protected int sapWaitOnRetry = 0;
	protected java.lang.String rfcName;
	protected java.lang.String streamFileName;
	protected java.lang.StringBuffer rfcInfo = new StringBuffer(1000);
	public java.lang.String Tab = "   ";

	//HWPIMSErrorInformation object is used hold error information that will be
		// written out to DB2 if an error occurs.
	protected HWPIMSErrorInformation hwpimsErrorInformation = null;

	//private SWO _swo;
	
	/**
	 * StringBuffer for storing rfc parameter items for logging
	 */
	private StringBuffer rfcParamList = new StringBuffer();

	protected Rfc () {
		super();
		logThisCall();
	}


	
	public abstract void execute() throws Exception ; 
	


	/**
	 * Describes what this rfc will do (not the results)
	 */
	public abstract String getTaskDescription();


	
	/**
	 * Retrieve boolean status of the results of the Rfc call.
	 * A Rfc call can be successful and still produce warnings
	 * and informational messages via getErrorInformation().
	 * To determine exact status check getSeverity().
	 * 
	 * @return true if the Rfc call was successful, false otherwise
	 */
	protected abstract boolean isSuccessful();
	
	/**
	 * Retrieve a formatted, informative message contianing
	 * ERROR, WARNING, or INFO details on the results of
	 * an rfc call.
	 */
	protected abstract String getErrorInformation();

	protected Logger getLog() {
		return 	LogManager.getLogManager().getPromoteLogger();
	}
//	
	protected abstract String getMaterialName();
//	private String getMatnr() {
//		return getSwo().getType() + getSwo().getModel() ; 
//	}	
	public abstract String getRfcName() ;


	protected void logExecution() {
		String sapCon = "on invalid connection"; 
		
//		else if (conn.isValid()) { 
//			sapCon = " on valid connection"; 
//		}
		getLog().debug( 
			"Executing: " 
			+ ClassUtil.getSimpleClassName( this ) 
			+ " which wraps rfc: " 
			+ getRfcName() 
			+ sapCon 
		) ;  
	}

	protected void logInitFailure( Exception e ) {
		getLog().error( "Failure initializing " + ClassUtil.getSimpleClassName( this ) +  
			"\n\t Exception was: " + e +
			"\n\t trace follows .... someplace \n"  ) ;
		e.printStackTrace() ; 
	}

	protected void logMethodCall(String methodName) {
		getLog().info( "Calling RFC: " + methodName ) ; 
	}

	protected void logSetter(String varName ) { 
		getLog().debug( "\t setting " + varName + " to NOTHING! It was not set on purpose. " ) ;  
		addParam(varName, "NOTHING");
	}

	protected void logSetter(String varName, String value) {
		getLog().debug( "\t setting " + varName + " to " + value ) ; 
		addParam(varName, value);
	}

	protected void logThisCall() {
		getLog().debug( "Created: " + ClassUtil.getSimpleClassName( this ) ) ;  
	}
	
	protected String getProfitCenter(String div){
		String profitCenter;
		if (isAlphaNumeric(div)){
			profitCenter= div;
		}else{
			profitCenter = "00000000" + div;
			
		}
		return profitCenter;
	}
	
	protected boolean isAlphaNumeric(String str){
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isLetter(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

//	public void setSwo(SWO swo) {
//		_swo = swo;
//	}
	public String getMsgNum()
	{
		return ClassUtil.getSimpleClassName(this).substring(0, 4);
	}
	
	protected String formatRfcErrorMessage(int returnCode, String errorText)
	{
		return "[" + getRfcName() + " " + getMaterialName() + "]" + errorText + " (rc = " + returnCode + ")";
	} 
	
	/**
	 * Default implementation to determine the severity of a response
	 * to an rfc call.
	 * Override in subclasses to provide specific behavior
	 * 
	 * @return OK - everything is good.
	 * INFO - the rfc responeded with an informational message.
	 * WARNING - the rfc posted a warning in its response.
	 * ERROR - the rfc responded with a code that can bee interpreted as a failure.
	 */
	public int getSeverity()
	{
		if (isSuccessful())
		{
			return OK;
		}
		return ERROR;
	}

	/**
	 * Get a description of thre execution results.
	 *
	 * @return Description of execution results.
	 */	
	public String getExecutionResultMessage()
	{
		if (isSuccessful())
		{
			return RFC_OK_MESSAGE;
		}
		return getErrorInformation();
	}

	/**
	 * Add a parameter and it's value to the rfc parameter list 	
	 */
	private void addParam(String varName, String value)
	{
		if (rfcParamList.length() > 0)
		{
			rfcParamList.append(", ");
		}
		rfcParamList.append(varName + ": " + value);
	}

	/**
	 * Get the parameter list for this RFC
	 *
	 * @return String parameter list for this RFC call
	 */	
	public String getRfcParameterList()
	{
		return rfcParamList.toString();
	}
		
//	public  static String determinePimsIdentity(String platformType ) { 
//		String pimsId = "";
//		if (platformType.equals("X")) {
//			pimsId = XSW_PIMS_IDENTITY;			
//		} else {
//			pimsId = CSW_PIMS_IDENTITY;
//		}
//		return pimsId;
//	}


//
//	public int getSapRFCRetries() {
//		return sapRFCRetries;
//	}
//
//
//
//	public void setSapRFCRetries(int sapRFCRetries) {
//		this.sapRFCRetries = sapRFCRetries;
//	}
//
//
//
//	public int getSapWaitOnRetry() {
//		return sapWaitOnRetry;
//	}


//
//	public void setSapWaitOnRetry(int sapWaitOnRetry) {
//		this.sapWaitOnRetry = sapWaitOnRetry;
//	}
//
//	/**
//	 * Insert the method's description here. Creation date: (11/22/00 3:58:07
//	 * PM)
//	 */
//	public void rfcDelayForTest() {
//
//		int testRFCDelay;
//
//		String testRFCDelayStr = ConfigManager.getConfigManager().getString(
//				PropertyKeys.KEY_RFC_TEST_DELAY, true);
//		if (testRFCDelayStr != null) {
//			try {
//				testRFCDelay = Integer.parseInt(testRFCDelayStr);
//				Thread.sleep(testRFCDelay * 1000);
//			} catch (NumberFormatException e) {
//				// Do nothing since we have already skipped the sleep
//			} catch (InterruptedException e1) {
//				// This should never happen
//			}
//		}
//
//	}
	/**
	 * Insert the method's description here.
	 * Creation date: (4/19/2001 4:33:49 PM)
	 * @return java.lang.String
//	 */
//	public void writeDataToFileCheck(String rfcInfoStr, String header, String transCounter) {
//
//		// If the first time called the appendFileFlag should be false.  Any other
//		// time it should be true;
//		try {
//
////			String streamFile = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_RFC_FILE_PATH);
//
//	/*		if (streamFile.equals("")) {
//				streamFile = "stream.dat";
//			}
//	*/
//			if (ConfigManager.getConfigManager().getString(PropertyKeys.KEY_RFC_FILE_FLAG, true).trim().equals("Y")) {
//
//				FileWriter f = new FileWriter(streamFileName,true);
//
//				Date curDate = new Date();
//				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss zzz");
//				//String dateFormat = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_DATE_FORMAT);
//				//SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
//				String outLine = "\nFUNC >> "+header;
//				if (transCounter != null && !transCounter.equals("")) {
//					outLine += "     TransCounter>>"+transCounter;
//				}
//				if ((ConfigManager.getConfigManager().getString(PropertyKeys.KEY_CUR_DATE, true).trim().equals("Y"))) {
//				outLine += "    Timestamp>>"+sdf.format(curDate);
//				}
//				outLine += "\n"+rfcName;
//				outLine += "\n"+rfcInfoStr;
//				outLine += "\n";
//				char buffer[] = new char[outLine.length()];
//				outLine.getChars(0, outLine.length(), buffer, 0);
//
//				f.write(buffer);
//				f.close();
//			}
//
//		} catch (Exception e) {
//			HWPIMSLog.Write("Error - writing to RFCData file. \n"
//					+ ExceptionUtility.getStackTrace(e),"E");
//
//		}
//
//
//	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2/22/2001 1:19:55 PM)
	 * @return java.lang.StringBuffer
	 */
	public StringBuffer reInitialize() {


		rfcInfo.setLength(0);
		rfcInfo.ensureCapacity(100);

		return rfcInfo;


	}

	public String getRfcInfo() {
		return rfcInfo.toString();
	}

	/**
	 * Return false only if the HWPIMS_IDENTITY_flag is set to "N"
	 * @return boolean
	 */
//	protected boolean includeHWPIMSIndicator() {
//
//		boolean	hwpimsFlag;
//		String hwpimsFlagStr = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_HWPIMS_IDENTITY_FLAG, true);
//		if (hwpimsFlagStr == null) {
//			hwpimsFlag = true;
//		} else if (hwpimsFlagStr.equals("N")) {
//			hwpimsFlag = false;
//		} else {
//			hwpimsFlag = true;
//		}
//
//
//		return hwpimsFlag;
//	}
	/**
	 * This method will set the hwpimsErrorInformation's object fields.
	 * 
	 * @param rfcRc
	 *            The return code from an rfc call.
	 * @param rfcError
	 *            The error message from a rfc called that did not execute
	 *            sucessfully.
	 * @param rfcMethod
	 *            The rfc method that ended in error.
	 * @param rfcName
	 *            The name of the rfc that ended in error.
	 * @param rfcInfo
	 *            Additional information about the rfc that ended in error.
	 * 
	 */
	public void generateErrorInformation(int rfcRc, String rfcError,
			String rfcMethod, String rfcName, String rfcInfo) {

		hwpimsErrorInformation.setMessage("Calling method: " + rfcMethod
				+ "; RFC called: " + rfcName + "; RC:" + rfcRc + " Msg: +"
				+ rfcError + rfcInfo);

		hwpimsErrorInformation.setStatus("R");
		hwpimsErrorInformation.setEvent("RFC");

	}
	
	/**
	 * Check if the TypeModel is a HIPO MODEL
	 * @param tm
	 * @return
	 */
	protected boolean isHIPOModel(String type, String model) {
		boolean isHIPO = false;
		if (("5313".equals(type) && "HPO".equals(model)) || ("5372".equals(type) && "IS5".equals(model))) {
			isHIPO = true;
		}
		return isHIPO;
	}
	
}
