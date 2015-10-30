package com.ibm.pprds.epimshw.util;

import org.apache.log4j.Logger;

import com.ibm.pprds.epimshw.PropertyKeys;

/**
 * Manage log4j logs.
 *	
 * 	Sample use statements
 *		LogManager lm = LogManager.getLogManager();
 *		lm.getClientLogger().debug("Use this for debugging level, can be very verbose");
 *		lm.getMiscLogger().info("Just informational messages");
 *		lm.getNonJDBCLogger().error("Connection events are not sent to the database");
 */
public class LogManager
{
	public static final String CLIENT_LOGGER = "epimshw.CHWPIMS_Client";
	public static final String SERVER_LOGGER = "epimshw.CHWPIMS_Server";
	public static final String MISC_LOGGER = "epimshw.CHWPIMS_Misc";
	public static final String PROMOTE_LOGGER = "epimshw.CHWPIMS_Promote";
	public static final String NON_JDBC_LOGGER = "epimshw.CHWPIMS_NonJDBC";
	
	private static LogManager myself;
	static private boolean configured= false;
	private static final String CONFIG_FILENAME = "LOG4J.properties";
	private static final long CONFIG_CHECK_MILLIS = 600000; //every 10 mintues
	
	public static final Logger clientLogger = Logger.getLogger(CLIENT_LOGGER);
	public static final Logger serverLogger = Logger.getLogger(SERVER_LOGGER);
	public static final Logger miscLogger = Logger.getLogger(MISC_LOGGER);
	public static final Logger nonJDBCLogger = Logger.getLogger(NON_JDBC_LOGGER);
	public static final Logger promoteLogger = Logger.getLogger(PROMOTE_LOGGER);

	private LogManager()
	{
	}
	
	public static synchronized LogManager getLogManager()
	{
		if (myself == null)
		{
			configureLog4J(null);
			myself = new LogManager();
		}
		return myself;
	}

	public Logger getClientLogger() {
		return clientLogger;
	}
	public Logger getMiscLogger() {
		return miscLogger;
	}

	public Logger getNonJDBCLogger() {
		return nonJDBCLogger;
	}
	public Logger getServerLogger() {
		return serverLogger;
	}

	public Logger getPromoteLogger() {
		return promoteLogger;
	}


	public static void configureLog4J()
	{
		ConfigManager config = ConfigManager.getConfigManager();
		String sysPath = config.getString(PropertyKeys.KEY_SYSTEM_REAL_PATH);
		String confPath = config.getString(PropertyKeys.KEY_CONFIG_FOLDER);
		String database = config.getString(PropertyKeys.KEY_CONFIG_FOLDER);
		if ((sysPath !=null) && (confPath !=null))
		{
			configureLog4J( sysPath + confPath + CONFIG_FILENAME);
		}
		else
		{
			if (null == sysPath)
			{
				nonJDBCLogger.warn("Property "	+ PropertyKeys.KEY_SYSTEM_REAL_PATH 
				+ " is not defined. Will use default Log4J configuration.");
			}
			if (null == confPath)
			{
				nonJDBCLogger.warn("Property "	+ PropertyKeys.KEY_CONFIG_FOLDER
				+ " is not defined. Will use default Log4J configuration.");
			}
			configureLog4J(null);
		}
	}

	public static void configureLog4J(String configFile)
	{
		if (!configured)
		{
			if (configFile != null)
			{
				org.apache.log4j.PropertyConfigurator.configureAndWatch(configFile,CONFIG_CHECK_MILLIS);
				nonJDBCLogger.info("Log configuration set to reload every " + CONFIG_CHECK_MILLIS + " milliseconds from " + configFile);
				configured = true;
			}
			else
			{
				System.out.println("DEBUG LogManager calling BasicConfigurator");
				org.apache.log4j.BasicConfigurator.configure();
				nonJDBCLogger.info("Location of log4j-init-file not set yet.  Using basic config.");
			}
		}
	}
		
	public static void printLogLevels()
	{
		printLogLevel(clientLogger);
		printLogLevel(serverLogger);
		printLogLevel(miscLogger);
		printLogLevel(nonJDBCLogger);
		printLogLevel(promoteLogger);
	}
	
	private static void printLogLevel(Logger log)
	{
		log.info("Logger " + log.getName() + " is logging at level " + 
			log.getLevel());
	}
	
}
