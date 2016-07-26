package com.ibm.pprds.epimshw;

/**
 * Keys for property file parameters
 * 
 * @author tim
 *
 */
public class PropertyKeys
{
	public final static String KEY_JNDI_DATA_SOURCE = "epimshw.dataSource";
	public final static String KEY_SYSTEM_REAL_PATH = "system.realPath";
	public static final String KEY_CONFIG_FOLDER = "epimshw.system.configPath";

	public final static String KEY_SCHEMA = "epimshw.schema";
	public final static String KEY_SERIAL_FILE_PATH = "epimshw.SerialFilePath";	
	public final static String KEY_RFC_FILE_PATH = "epimshw.RFCFileLocation";
	
	public final static String KEY_OBJECT_TO_FILE_FLAG = "epimshw.ObjectToFile";
	public final static String KEY_OBJECT_TO_FILE_PATH = "epimshw.ObjectToFileLocation";
	
	public final static String KEY_PROVIDER_URL = "epimshw.initialContextProviderURL";
	public static final String KEY_DB_USEPOOL = "epimshw.useConnectionPool";
	public static final String KEY_DB_NONPOOLED_JBDC_DRIVER_CLASS = "epimshw.jdbcDriverClass";
	public static final String KEY_DB_NONPOOLED_NAME = "epimshw.nonPooledDBName";
	public static final String KEY_DB_NONPOOLED_USER = "epimshw.nonPooledDBUser";
	public static final String KEY_DB_NONPOOLED_PASSWORD = "epimshw.nonPooledDBPassword";

	public final static String KEY_SAP_MODE = "epimshw.SAPMode";
	public final static String KEY_SAP_DESTINATION = "epimshw.SAPDestination";
	public final static String KEY_SAP_HOST_IP ="epimshw.SAPHostIP";
	public final static String KEY_SAP_NUMBER = "epimshw.SAPNumber";
	public final static String KEY_SAP_GATEWAY = "epimshw.SAPGateway";
	public final static String KEY_SAP_GATEWAY_NUMBER = "epimshw.SAPGatewayNumber";
	public final static String KEY_SAP_SYSTEM_NAME = "epimshw.SAPSystemName";
	public final static String KEY_SAP_SYSTEM_GROUP = "epimshw.SAPSystemGroup";
	public final static String KEY_SAP_MESSAGE_SERVER = "epimshw.SAPMessageServer";
	public final static String KEY_SAP_LOAD_BALANCING = "epimshw.SAPLoadBalancing";
	public final static String KEY_SAP_CHECK_AUTHORIZATION = "epimshw.SAPCheckAuthorization";
	public final static String KEY_SAP_USER = "epimshw.SAPUser";
	public final static String KEY_SAP_PASSWORD = "epimshw.SAPPassword";
	public final static String KEY_SAP_CLIENT = "epimshw.SAPClient";
	public final static String KEY_SAP_LANGUAGE = "epimshw.SAPLanguage";

	public final static String KEY_EXTRA_PLANT = "epimshw.extraPlant";
	
	public final static String KEY_RFC_TEST_DELAY = "epimshw.TestRFCDelay";
	public final static String KEY_RFC_NUMBER_RETRIES = "epimshw.RFCRetries";
	public final static String KEY_RFC_WAIT_ON_RETRY_NUMBER = "epimshw.WaitOnRetry";
	public final static String KEY_RFC_ERROR_MODE = "epimshw.RFCErrorMode";
	public final static String KEY_RFC_FILE_FLAG = "epimshw.RFCFile";
	
	public final static String KEY_HWPIMS_IDENTITY_FLAG = "epimshw.HWPIMS_IDENTITY_flag";
	public final static String KEY_DATE_FORMAT = "epimshw.DateFormat";
	public final static String KEY_CUR_DATE = "epimshw.curDate";
	public final static String KEY_EXPLOSION_TOOL_PRODUCT_GROUP_MAX_SIZE = "epimshw.ExplosionToolProductGroupMaxSize";
	public final static String KEY_IGNORE_EXPL_PROFILE_PROMOTE_FLAG = "epimshw.IgnoreExplProfilePromoteFlag";
	public final static String KEY_INCLUDE_STREAMs = "epimshw.IncludeStreams";
	
	public final static String KEY_HWPIMS_CONTROLLER_SLEEP_INTERVAL = "epimshw.HWPIMSControllerSleepInterval";
	public final static String KEY_HWPIMS_CONTROL_TABLE_POLL_INTERVAL = "epimshw.ControlTablePollInteveral";
	public final static String KEY_HWPIMS_AGENT_COUNT = "epimshw.HWPIMSAgentCount";
	public final static String KEY_HWPIMS_UPDATE_MODE = "epimshw.UpdateMode";
	public final static String KEY_HWPIMS_LOG_MEMORY = "epimshw.LogMemory";
	public final static String KEY_STAT_CONTROL_AP = "epimshw.Stat_Control_AP";
	public final static String KEY_STAT_CONTROL_PP = "epimshw.Stat_Control_PP";
	
	public final static String KEY_PIPACKAGE_MAILHOST = "epimshw.PiPackageEmail.mailhost";
	public final static String KEY_PIPACKAGE_FROM = "epimshw.PiPackageEmail.from";
	public final static String KEY_PIPACKAGE_SUBJECT = "epimshw.PiPackageEmail.subject";
	
	public final static String KEY_QSM_FTP_SOURCE_FILE_DIRECTORY  = "epimshw.QSM.ftp.sourceDir";
	public final static String KEY_QSM_FTP_ROW_TERMINATOR  = "epimshw.QSM.ftp.rowTerminator";
	public final static String KEY_QSM_FTP_ID  = "epimshw.QSM.ftp.Id";
	public final static String KEY_QSM_FTP_PASSWORD = "epimshw.QSM.ftp.Password";
	public final static String KEY_QSM_FTP_DEST_SERVER = "epimshw.QSM.ftp.destServer";
	public final static String KEY_QSM_FTP_PWD = "epimshw.QSM.ftp.pwd";
	public final static String KEY_QSM_PRODUCT_FILENAME = "epimshw.QSM.ftp.product.Filename";
	public final static String KEY_QSM_CREF_FILENAME = "epimshw.QSM.ftp.cref.Filename";
	public final static String KEY_QSM_PRODUCT_RECLEN = "epimshw.QSM.ftp.product.recLength";
	public final static String KEY_QSM_CREF_RECLEN = "epimshw.QSM.ftp.cref.recLength";
	public final static String KEY_QSM_PRODUCT_FILETYPE = "epimshw.QSM.ftp.product.fileType";
	public final static String KEY_QSM_CREF_FILETYPE = "epimshw.QSM.ftp.cref.fileType";
	
	public final static String KEY_WWPRT_SERVER = "epimshw.WWPRTServer";
	public final static String KEY_WWPRT_APPNAME = "epimshw.WWPRTAppName";
	public final static String KEY_HWPIMS_WWPRT_UPDATE_MODE = "epimshw.wwprtupdate";
	
	// MQ configuration for EACM interface
	public final static String KEY_EACM_MQ_HOSTNAME = "epimshw.Eacm_MQ_Hostname";
	public final static String KEY_EACM_MQ_MGR_Name = "epimshw.Eacm_MQ_Mgr_Name";
	public final static String KEY_EACM_MQ_SVR_CHANNEL_NAME = "epimshw.Eacm_MQ_Svr_Channel_Name";
	public final static String KEY_EACM_MQ_LSR_Port = "epimshw.Eacm_MQ_Lsr_Port";
	public final static String KEY_EACM_MQ_QUEUE_NAME = "epimshw.Eacm_MQ_Queue_Name";	
	public final static String KEY_EACM_MQ_QUEUE_POLLING_INTERVAL = "epimshw.Eacm_MQ_Queue_Polling_Interval";
	public final static String KEY_EACM_NOTIFICATION_INTERVAL = "epimshw.eacm.notification.interval";
	
	// SMTP configuration for Mail
	public final static String KEY_EACM_SMTP_HOSTNAME = "epimshw.Eacm_Mail_Server";
	public final static String KEY_EACM_SMTP_FROM = "epimshw.Eacm_Mail_From";
	public final static String KEY_EACM_SMTP_TO = "epimshw.Eacm_Mail_To";
	public final static String KEY_EACM_SMTP_SUBJECT = "epimshw.Eacm_Mail_subject";
	public final static String KEY_EACM_SMTP_TEXT = "epimshw.Eacm_Mail_Text";
	
	// eACM interface properties
	public final static String KEY_EACM_PDH_SERVER_IP = "epimshw.eacm.pdh.serverIp";
	public final static String KEY_EACM_PDH_SERVER_PORT = "epimshw.eacm.pdh.serverPort";
	public final static String KEY_EACM_PDH_DATABASE_OBJECT_NAME = "epimshw.eacm.pdh.databaseObjectName";

	public final static String KEY_EACM_PDH_USER_ID = "epimshw.eacm.pdh.userId";
	public final static String KEY_EACM_PDH_PASSWORD = "epimshw.eacm.pdh.password";
	public final static String KEY_EACM_PDH_VERSION_LITERAL = "epimshw.eacm.pdh.versionLiteral";
	public final static String KEY_EACM_EXTRACT_LOG_LOCATION = "epimshw.eacm.extract.log.location";
	public final static String KEY_EACM_MAX_MAIL_RETRY_COUNTER= "epimshw.eacm.mail.retry.counter";
	
	// ePIMS SW Data Base Properties file info	
	public final static String KEY_JNDI_DATA_SOURCE_SW = "epimssw.dataSource";
	public final static String KEY_SCHEMA_SW = "epimssw.schema";
	public static final String KEY_DB_NONPOOLED_NAME_SW = "epimssw.nonPooledDBName";
	public static final String KEY_DB_NONPOOLED_USER_SW = "epimssw.nonPooledDBUser";
	public static final String KEY_DB_NONPOOLED_PASSWORD_SW = "epimssw.nonPooledDBPassword";
	public final static String KEY_PROVIDER_URL_SW = "epimssw.initialContextProviderURL";		
	
	// WWPRT Data Base Properties file info	
	public final static String KEY_JNDI_DATA_SOURCE_WWPRT = "wwprt.dataSource";
	public final static String KEY_SCHEMA_WWPRT = "wwprt.schema";
	public static final String KEY_DB_NONPOOLED_NAME_WWPRT = "wwprt.nonPooledDBName";
	public static final String KEY_DB_NONPOOLED_USER_WWPRT = "wwprt.nonPooledDBUser";
	public static final String KEY_DB_NONPOOLED_PASSWORD_WWPRT = "wwprt.nonPooledDBPassword";
	public final static String KEY_PROVIDER_URL_WWPRT = "wwprt.initialContextProviderURL";		

	// Exception Events
	public final static String KEY_UNHANDLED_EXCEPTION = "epimshw.unhandledException";
	
	//SEO Validation		
	public final static String KEY_SEO_VALIDATION = "epimshw.seoValidation";
	
	//commented by venkat
	//XccOnlySegmentAcronym
	//public final static String KEY_XCC_ONLY_SEGMENT_ACRONYM = "epimshw.xccOnlySegmentAcronym";
	public final static String KEY_XCC_ONLY_DIV="epimshw.xccOnlyDiv"; //RQ0724066720  xccOnlyDiv 
	
	//SEO Bundle Revenue BOM 
	public final static String KEY_ENABLE_SEO_BUNDLE_REVENUE_BOM = "epimshw.enableSeoBundleReveuneBom";
	public final static String KEY_ENABLE_SEO_BUNDLE_REVENUE_BOM_GUI = "epimshw.enableSeoBundleRevenueBomGui";
	// SAP Ledger Flag
	public final static String KEY_SAP_LEDGER = "epimshw.enableSapLedger";
	public final static String KEY_PROFIT_CENTER_PLANT = "epimshw.profitCenterPlant"; 
	public final static String KEY_CNTRY_TAX_REFRESH_TIME = "epimshw.cntryTaxRefreshTime";
	
	public final static String KEY_RFC_MAX_TRY_COUNT = "epimshw.rfcMaxTryCount";
	public final static String KEY_RFC_RETRY_WAIT_TIME = "epimshw.rfcRetryWaitTime";

}


