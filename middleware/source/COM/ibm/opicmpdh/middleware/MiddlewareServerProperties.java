//Copyright (c) 2001,2011 International Business Machines Corp., Ltd.
//All Rights Reserved.
//Licensed for use in connection with IBM business only.

//$Log: MiddlewareServerProperties.java,v $
//Revision 1.77  2015/03/06 07:18:32  wangyul
//jdbc change to supprot type2 and type4
//
//Revision 1.77  2015/03/04 11:25:30  luis
//New property for jdbc driver name
//
//Revision 1.76  2014/07/24 20:59:30  wendy
//RCQ00303672 - provide way to turn off all email
//
//Revision 1.75  2014/06/26 19:09:48  wendy
//allow rmi serial port number to be defined
//
//Revision 1.74  2011/09/06 21:15:38  wendy
//Add version literal
//
//Revision 1.73  2011/08/11 18:03:36  wendy
//add generic way to access properties
//
//Revision 1.72  2010/06/22 18:44:25  wendy
//BH SR87, SR655 - Extend ComboUnique local rule
//
//Revision 1.71  2009/06/12 18:58:27  wendy
//Add support to set max times to try to send mail

//Revision 1.70  2007/08/08 17:28:36  wendy
//RQ0713072645 allow override of domain check

//Revision 1.69  2007/07/31 16:52:03  bala
//Chunking for call to gbl8000 using extract action item

//Revision 1.68  2005/12/19 17:48:50  roger
//Change keep alive default to zero which means same as disabled

//Revision 1.67  2005/12/19 16:32:47  roger
//New property for keep alive

//Revision 1.66  2005/08/25 19:52:21  joan
//remove methods for MQSeries

//Revision 1.65  2005/08/19 21:54:34  joan
//add mq

//Revision 1.64  2005/08/16 20:17:57  joan
//add method

//Revision 1.63  2005/05/26 15:29:53  tony
//adjusted var names

//Revision 1.62  2005/05/26 15:14:17  tony
//adjusted

//Revision 1.61  2005/05/24 17:25:46  joan
//test cvs

//Revision 1.60  2005/05/24 16:44:33  tony
//silver bullet

//Revision 1.59  2005/01/04 22:14:52  roger
//fix test

//Revision 1.58  2005/01/04 21:33:45  roger
//fix

//Revision 1.57  2005/01/04 20:50:05  roger
//Make usage logging conditional based on middleware property

//Revision 1.56  2004/07/25 23:49:47  dave
//fix on typo

//Revision 1.55  2004/07/25 23:35:20  dave
//trying to increase backlog on connections (make it controlled)
//by properties file

//Revision 1.54  2004/07/25 07:12:59  dave
//final tuning

//Revision 1.53  2004/07/25 04:24:40  dave
//more default changing

//Revision 1.52  2004/07/25 03:23:34  dave
//more RMI config exposure

//Revision 1.51  2004/07/25 01:11:52  dave
//increase default timeouts

//Revision 1.50  2004/07/24 23:50:28  dave
//trying some client settings.. expire the client side
//connection after 30 secs of non use

//Revision 1.49  2004/07/24 23:09:50  dave
//more RMI config control in props

//Revision 1.48  2004/07/24 22:16:57  dave
//more fixes to RMI

//Revision 1.47  2004/07/24 22:16:01  dave
//more RMI controls for trace and debug

//Revision 1.46  2004/07/24 22:13:05  dave
//more rmi logging options

//Revision 1.45  2004/07/24 22:09:03  dave
//more control RMI connection Pool

//Revision 1.44  2004/07/24 21:47:04  dave
//new RMI debuging properties (as opposed to -D)

//Revision 1.43  2003/06/02 21:39:02  roger
//Change property name

//Revision 1.42  2003/06/02 17:06:51  roger
//Property names have changed for PDH+ODS

//Revision 1.41  2003/06/02 16:53:33  roger
//Some properties needed for PDH & ODS

//Revision 1.40  2003/04/29 20:59:50  roger
//Include a FROM address property

//Revision 1.39  2003/04/29 16:46:39  roger
//Added new properties for sending SMTP mail

//Revision 1.38  2003/01/07 18:22:12  dave
//added ODS parm pulls for user/pwd/url

//Revision 1.37  2002/04/08 20:04:05  dave
//adding file closes

//Revision 1.36  2002/03/12 18:18:51  roger
//Remove display

//Revision 1.35  2002/03/12 17:22:02  roger
//Remove display

//Revision 1.34  2002/03/12 17:20:55  roger
//Remove display

//Revision 1.33  2002/03/11 15:09:56  roger
//Initialize counters

//Revision 1.32  2002/03/08 23:22:05  roger
//Unoptimize

//Revision 1.31  2002/03/08 22:53:37  roger
//Optimize

//Revision 1.30  2002/03/08 18:14:05  roger
//Don't show property file name

//Revision 1.29  2002/03/08 17:17:58  roger
//Don't use D.isplay here - recursive loop

//Revision 1.28  2002/03/08 03:03:03  roger
//Fix it

//Revision 1.27  2002/03/07 22:10:53  roger
//Use default if specified is length 0

//Revision 1.26  2002/03/07 21:07:31  roger
//Properties should have defaults

//Revision 1.25  2002/03/07 17:05:06  roger
//Run a test to find out why not using specified property file

//Revision 1.24  2002/03/06 23:20:27  roger
//Message is misleading

//Revision 1.23  2002/03/06 22:57:20  roger
//Output ~after~ getting properties

//Revision 1.22  2002/03/06 22:10:31  roger
//Handle System property

//Revision 1.21  2002/03/06 20:52:39  roger
//Make output D.isplay

//Revision 1.20  2002/03/06 18:37:38  roger
//Fix it

//Revision 1.19  2002/03/06 18:22:47  roger
//Derive the property file name from system property, or the default value

//Revision 1.18  2002/03/06 18:04:37  roger
//Test if System properties are being set by WASPool

//Revision 1.17  2002/01/16 17:09:51  roger
//Add property for redirection of D.ebug output

//Revision 1.16  2002/01/15 18:11:14  roger
//Add UDP logging feature

//Revision 1.15  2001/11/29 16:58:08  roger
//Change property names to be more accurate

//Revision 1.14  2001/11/06 18:55:23  roger
//No defaults for several properties

//Revision 1.13  2001/11/01 21:45:42  roger
//Implement SSL socket type

//Revision 1.12  2001/10/30 17:02:34  roger
//Put a method back in - for now

//Revision 1.11  2001/10/30 02:29:47  roger
//Small steps toward SSL support

//Revision 1.10  2001/08/24 00:25:38  roger
//Trouble getting member variable use method instead

//Revision 1.9  2001/08/22 16:52:58  roger
//Removed author RM

//Revision 1.8  2001/05/06 21:54:43  dave
//more msp vars

//Revision 1.7  2001/05/02 05:02:08  dave
//added target enterprise to properties files

//Revision 1.6  2001/03/26 16:33:21  roger
//Misc formatting clean up

//Revision 1.5  2001/03/21 00:01:08  roger
//Implement java class file branding in getVersion method

//Revision 1.4  2001/03/16 15:52:21  roger
//Added Log keyword


package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.util.*;

/**
 * Retrieve configuration properties for <code>Middleware</code>
 * @version @date
 */
public final class MiddlewareServerProperties extends Properties {

	// Class constants
	private static String c_strPropertiesFilename = "middleware.server.properties";

	// Class variables
	private static Properties c_propMiddleware = null;

	/**
	 * Main method which performs a simple test of this class
	 */
	public static void main(String arg[]) {
		System.out.println(MiddlewareServerProperties.getVersion());
		System.out.println(MiddlewareServerProperties.getPDHDatabaseURL());
		System.out.println(MiddlewareServerProperties.getPDHDatabaseUser());
		System.out.println(MiddlewareServerProperties.getPDHDatabasePassword());
	}

	/**
	 * Some class level initialization
	 */
	static {
		// Load the properties from file so they are available for each accessor method
		reloadProperties();
	}

	/**
	 * Don't let anyone instantiate this class.
	 */
	private MiddlewareServerProperties() {
	}

	/**
	 * Load the Middleware properties from the middleware.server.properties file
	 */
	private static final void loadProperties() {
		String strPropertyFilename = System.getProperty("com.ibm.opicmpdh.middleware.middlewareserverproperties.filename");
		if (strPropertyFilename != null) {
			c_strPropertiesFilename = strPropertyFilename;
		}
		try {
			if (c_propMiddleware == null) {
				c_propMiddleware = new Properties();
				FileInputStream inProperties = new FileInputStream(c_strPropertiesFilename);
				c_propMiddleware.load(inProperties);
				inProperties.close();
			}
		} catch (Exception x) {
//			D.ebug(D.EBUG_ERR, "Unable to loadProperties " + x); // D.ebug calls this class
			System.err.println("MiddlewareServerProperties Unable to loadProperties " + x);
		}
	}

	/**
	 * Reload the properties from the middleware.server.properties file
	 */
	public static final void reloadProperties() {
		loadProperties();
	}

	/**
	 * Return the Database URL property from the middleware.server.properties file
	 */
	public static final String getPDHDatabaseURL() {
		reloadProperties();
		return c_propMiddleware.getProperty("pdh_database_url", "");
	}

	/**
	 * Return the Database User property from the middleware.server.properties file
	 */
	public static final String getPDHDatabaseUser() {
		reloadProperties();
		return c_propMiddleware.getProperty("pdh_database_user", "");
	}

	/**
	 * Return the Database password property from the middleware.server.properties file
	 */
	public static final String getPDHDatabasePassword() {
		reloadProperties();
		return c_propMiddleware.getProperty("pdh_database_password", "");
	}

	/**
	 * Return the Database URL property from the middleware.server.properties file
	 */
	public static final String getODSDatabaseURL() {
		reloadProperties();
		return c_propMiddleware.getProperty("ods_database_url", "");
	}

	/**
	 * Return the Database User property from the middleware.server.properties file
	 */
	public static final String getODSDatabaseUser() {
		reloadProperties();
		return c_propMiddleware.getProperty("ods_database_user", "");
	}

	/**
	 * Return the Database password property from the middleware.server.properties file
	 */
	public static final String getODSDatabasePassword() {
		reloadProperties();
		return c_propMiddleware.getProperty("ods_database_password", "");
	}

	/**
	 * RTC1119727
	 * Return the ODS2 Database URL property from the middleware.server.properties file
	 */
	public static final String getODS2DatabaseURL() {
		reloadProperties();
		return c_propMiddleware.getProperty("ods2_database_url", "");
	}

	/**
	 *  RTC1119727
	 * Return the ODS2 Database User property from the middleware.server.properties file
	 */
	public static final String getODS2DatabaseUser() {
		reloadProperties();
		return c_propMiddleware.getProperty("ods2_database_user", "");
	}

	/**
	 *  RTC1119727
	 * Return the ODS2 Database password property from the middleware.server.properties file
	 */
	public static final String getODS2DatabasePassword() {
		reloadProperties();
		return c_propMiddleware.getProperty("ods2_database_password", "");
	}
	/**
	 * Return the number of database connections to pool property from the middleware.server.properties file (default = 1)
	 */
//	public static final int getPDHDatabaseConnections() {
	public static final int getDatabaseConnections() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("database_connections", "1"));
	}

//	/**
//	* Return the number of database connections to pool property from the middleware.server.properties file (default = 1)
//	*/
//	public static final int getODSDatabaseConnections() {
//	reloadProperties();
//	return Integer.parseInt(c_propMiddleware.getProperty("ods_database_connections", "1"));
//	}

	/**
	 * Return the Middleware RMI Object Name property from the middleware.server.properties file
	 * @see Middleware#connect()
	 */
	public static final String getDatabaseObjectName() {
		reloadProperties();
		return c_propMiddleware.getProperty("object_name", "");
	}

	/**
	 * Return the RMI Server bind IP Address property from the middleware.server.properties file
	 */
	public static final String getServerBindIpAddress() {
		reloadProperties();
		return c_propMiddleware.getProperty("server_bind_ip_address", "");
	}

	/**
	 * Return the RMI Server bind port property from the middleware.server.properties file
	 */
	public static final String getServerBindPort() {
		reloadProperties();
		return c_propMiddleware.getProperty("server_bind_port", "1099");
	}

	/**
	 * Return the Connection sleep wait property from the middleware.server.properties file (default = 10 ms)
	 */
	public static final int getWaitSleepTimeMS() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("wait_sleep_time_ms", "10"));
	}

	/**
	 * Return the Connection wait timeout property from the middleware.server.properties file (default = 10 sec.)
	 */
	public static final int getWaitTimeoutMS() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("wait_timeout_ms", "10000"));
	}

	/**
	 * Return the trace output property from the middleware.server.properties file (default = true)
	 */
	public static final boolean getTrace() {
		reloadProperties();
		return Boolean.valueOf(c_propMiddleware.getProperty("trace", "true")).booleanValue();
	}

	/**
	 * Return the test_db_connection property from the middleware.server.properties file (default = false)
	 */
	public static final boolean getTestConnect() {
		reloadProperties();
		return Boolean.valueOf(c_propMiddleware.getProperty("test_db_connection", "false")).booleanValue();
	}

	/**
	 * Return the maximum number of locks for connection before being reset
	 */
	public static final int getConnectionReset() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("connection_reset", "32000"));
	}

	/**
	 * Return the maximum number of locks for connection before being reset
	 */
	public static final int getConnectionCheckerQuantum() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("connection_checker_quantum", "30000"));
	}

	/**
	 * Return the amount of time a connection can be locked before assuming it is dead
	 */
	public static final int getConnectionDead() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("connection_dead", "60000"));
	}

	/**
	 * Return the amount of time to pause between making connections (to not swamp DB2)
	 */
	public static final int getConnectionPause() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("connection_pause", "1000"));
	}

	/**
	 * Return the amount of time to wait between keep alive action
	 */
	public static final int getKeepAliveQuantum() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("keepalive_quantum", "0"));
	}

	/**
	 * Return the debug trace level
	 */
	public static final int getDebugTraceLevel() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("debug_trace_level", "0"));
	}

	/**
	 * Return the inactivity restart quantum
	 */
	public static final long getInactiveRestartQuantum() {
		reloadProperties();
		return Long.parseLong(c_propMiddleware.getProperty("inactive_restart", "28800000"));
	}

	/**
	 * Return the time of day the middleware should shutdown
	 */
	public static final String getShutdownTimeOfDay() {
		reloadProperties();
		return c_propMiddleware.getProperty("shutdown_tod", "");
	}

	/**
	 * Return the middleware instance purpose
	 */
	public static final String getInstancePurpose() {
		reloadProperties();
		return c_propMiddleware.getProperty("instance_purpose", "U");
	}

	/**
	 * Return the socket type for RMI socket
	 */
	public static final String getSocketType() {
		reloadProperties();
		return c_propMiddleware.getProperty("socket_type", "");
	}

	/**
	 * Return the server Target Enterprise used for Reporting
	 */
	public static final String getTargetEnterprise() {
		reloadProperties();
		return c_propMiddleware.getProperty("server_enterprise", "");
	}

	/**
	 * Return the server Target Enterprise used for Reporting
	 */
	public static final String getWebsphereURL() {
		reloadProperties();
		return c_propMiddleware.getProperty("websphere_url", "");
	}

	/**
	 * Return the server Target Enterprise used for Reporting
	 */
	public static final String getABRInProcessCode() {
		reloadProperties();
		return c_propMiddleware.getProperty("abr_inprocesscode", "0050");
	}

	/**
	 * Return the name of the LDAP server to be used to authentication
	 */
	public static final String getServerLDAP() {
		reloadProperties();
		return c_propMiddleware.getProperty("ldap_server", "");
	}

	/**
	 * Return the pass phrase for ssl
	 */
	public static final String getSSLPassPhrase() {
		reloadProperties();
		return c_propMiddleware.getProperty("ssl_passphrase", "");
	}

	/**
	 * Return the KeyStore filename for ssl
	 */
	public static final String getSSLKeyStore() {
		reloadProperties();
		return c_propMiddleware.getProperty("ssl_keystore", "");
	}

	/**
	 * Return the TrustStore filename for ssl
	 */
	public static final String getSSLTrustStore() {
		reloadProperties();
		return c_propMiddleware.getProperty("ssl_truststore", "");
	}

	/**
	 * Return the loghost host name
	 */
	public static final String getLogHost() {
		reloadProperties();
		return c_propMiddleware.getProperty("loghost_host", "");
	}

	/**
	 * Return the loghost port
	 */
	public static final int getLogHostPort() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("loghost_port", "0"));
	}

	/**
	 * Return the log filename for redirection
	 */
	public static final String getLogFileName() {
		reloadProperties();
		return c_propMiddleware.getProperty("log_filename", "");
	}

	/**
	 * Return the SMTP server name
	 */
	public static final String getSMTPServer() {
		reloadProperties();
		return c_propMiddleware.getProperty("smtp_server", "mail");
	}

	/**
	 * Return the SMTP connection timeout value
	 */
	public static final String getSMTPConnectTimeout() {
		reloadProperties();
		return c_propMiddleware.getProperty("smtp_connect_timeout", "5000");
	}

	/**
	 * Return the SMTP send timeout value
	 */
	public static final String getSMTPSendTimeout() {
		reloadProperties();
		return c_propMiddleware.getProperty("smtp_send_timeout", "5000");
	}

	/**
	 * Return the mail from address
	 */
	public static final String getSMTPFromAddress() {
		reloadProperties();
		return c_propMiddleware.getProperty("smtp_from_address", "default_property@ibm.org");
	}


	/**
	 * RMI Logging and debug
	 */
	public static final String getJavaRMIServerLogCalls() {
		reloadProperties();
		return c_propMiddleware.getProperty("java.rmi.server.logCalls","true");
	}

	public static final String getSunRMIServerExceptionTrace() {
		reloadProperties();
		return c_propMiddleware.getProperty("sun.rmi.server.exceptionTrace","true");
	}

	public static final String getSunRMITransportTCPLogLevel() {
		reloadProperties();
		return c_propMiddleware.getProperty("sun.rmi.transport.tcp.logLevel","SILENT");
	}

	public static final String getSunRMITransportLogLevel() {
		reloadProperties();
		return c_propMiddleware.getProperty("sun.rmi.transport.logLevel","SILENT");
	}

	public static final String getSunRMITransportTCPReadTimeout() {
		reloadProperties();
		return c_propMiddleware.getProperty("sun.rmi.transport.tcp.readTimeout","240000");
	}

	public static final String getSunRMITransportTCPNoConnectionPool() {
		reloadProperties();
		return c_propMiddleware.getProperty("sun.rmi.transport.tcp.noConnectionPool","false");
	}

	public static final String getSunRMITransportTCPConnectionPool() {
		reloadProperties();
		return c_propMiddleware.getProperty("sun.rmi.transport.tcp.connectionPool","false");
	}

	public static final String getSunRMILogDebug() {
		reloadProperties();
		return c_propMiddleware.getProperty("sun.rmi.log.debug","false");
	}

	public static final String getSunRMIDGCLogLevel() {
		reloadProperties();
		return c_propMiddleware.getProperty("sun.rmi.dgc.logLevel","SILENT");
	}

	public static final String getSunRMITransportConnectionTimeout() {
		reloadProperties();
		return c_propMiddleware.getProperty("sun.rmi.transport.connectionTimeout","120000");
	}

	public static final String getJavaRMIDGCLeaseValue() {
		reloadProperties();
		return c_propMiddleware.getProperty("java.rmi.dgc.leaseValue","1800000");
	}

	public static final String getSunRMIDGCServerGCInterval() {
		return c_propMiddleware.getProperty("sun.rmi.dgc.server.gcInterval","600000");
	}

	public static final int  getServerSocketBacklogSize() {
		return Integer.parseInt(c_propMiddleware.getProperty("rmi.server.socket.backlog","500"));
	}

	public static final boolean getUsageLogging() {
		reloadProperties();
		return Boolean.valueOf(c_propMiddleware.getProperty("log_usage","false")).booleanValue();
	}

	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: MiddlewareServerProperties.java,v 1.77 2015/03/06 07:18:32 wangyul Exp $";
	}

	/**
	 * get target compiled version
	 * @author Anthony C. Liberto
	 */
	public static final String getTargetCompiledVersion() {
		return c_propMiddleware.getProperty("mw.target.compiled.version","9999-12-31-23.59.59.99");
	}

	/**
	 * get target client version
	 * @author Anthony C. Liberto
	 */
	public static final String getTargetClientVersion() {
		return c_propMiddleware.getProperty("mw.target.client.version","9999-12-31-23.59.59.99");
	}

	/**
	 * get target remote version
	 * @author Anthony C. Liberto
	 */
	public static final String getTargetServerVersion() {
		return c_propMiddleware.getProperty("mw.target.server.version","9999-12-31-23.59.59.99");
	}

	/**
	 * get target application version
	 * @author Anthony C. Liberto
	 */
	public static final String getTargetApplicationVersion() {
		return c_propMiddleware.getProperty("mw.target.application.version","9999-12-31-23.59.59.99");
	}

	/**
	 * get target application compiled version
	 * @author Anthony C. Liberto
	 */
	public static final String getTargetApplicationCompiledVersion() {
		return c_propMiddleware.getProperty("mw.target.application.compiled.version","9999-12-31-23.59.59.99");
	}

	/**
	 * get the Administrative contact
	 * @author Anthony C. Liberto
	 */
	public static final String getAdminContact() {
		return c_propMiddleware.getProperty("mw.target.admin.contact","v2ueh21@us.ibm.com");
	}

	public static final int getVeExtractChunkItemSize(String _strVe) {

		String strKey = "mw.ve.extract.chunksize"+"."+_strVe;
		return Integer.parseInt(c_propMiddleware.getProperty(strKey, "1000"));
	}
	/**
	 * Return enforce pdhdomain for actions  RQ0713072645
	 * having it in middleware properties allow ops to override this
	 * @return boolean
	 */
	public static final boolean getEnforcePDHDomain() {
		return c_propMiddleware.getProperty("enforce.pdhdomain", "false").equalsIgnoreCase("true");
	}
	/**
	 * Return number of attempts to sendmail
	 * @return int
	 */
	public static final int getSendMailMaxTries() {
		return Integer.parseInt(c_propMiddleware.getProperty("sendMailMaxTries", "3"));
	} 
    /**
     * RCQ00303672 
     * provide a way to turn off all email
     * @return boolean
     */
    public static final boolean getAllowSendingMail() {
        return Boolean.valueOf(c_propMiddleware.getProperty("allow_sending_mail","true")).booleanValue(); 
    }
	/**
	 * Return string used to delimit attribute codes, values and descriptions for uniqueness checks
	 * BH SR87, SR655
	 * @return
	 */
	public static final String getMetaDataDelimiter() {
		return c_propMiddleware.getProperty("mw.metadata.delimiter","|||");
	}
	/**
	 * Return string used for version literal
	 * @return
	 */
	public static final String getVersionLiteral() {
		return c_propMiddleware.getProperty("version_literal", "POTPALASDEENALAB");
	}
    /**
     * allow a way to get any value and specify a default
     * @param key  
     * @param defvalue
     * @return
     */
    public static final String getValue(String key,String defvalue) {
        return c_propMiddleware.getProperty(key, defvalue);
    } 
	/**
	 * Lenovo chgs CQ-290360
	 * Return the rmi serial port number on which to export object 
	 */
	public static final int getRMISerialPortNumber() {
		reloadProperties();
		return Integer.parseInt(c_propMiddleware.getProperty("rmi_serial_port_number", "0"));
	}
	/**
	 * Return string used for JDBC driver, default value for db2 jdbc type2
	 * @return
	 */
	public static final String getJdbcDriverClassName() {
		String driverClassName = c_propMiddleware.getProperty("jdbc_driver_class");
		if(driverClassName == null || "".equals(driverClassName)) {
			driverClassName = "COM.ibm.db2.jdbc.app.DB2Driver";
		}
		return driverClassName;
	}
}
