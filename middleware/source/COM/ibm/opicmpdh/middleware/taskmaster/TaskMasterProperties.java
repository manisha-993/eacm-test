//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TaskMasterProperties.java,v $
// Revision 1.29  2013/09/11 18:40:37  wendy
// add property for refreshing queue after abr completes
//
// Revision 1.28  2013/05/24 14:12:50  wendy
// taskmaster perf enhancements include:
//  - Made idler classes case sensitive - can have 52 now
//  - Added callback to attempt to run another ABR as soon as one completes.
//  - Replaced string lookups with hash set
//  - if ABR is inprocess status when taskmaster starts, attempt to run the ABR again if readonly, if not readonly then fail it in the pdh and notify user that ABR was aborted
//
// Revision 1.27  2008/01/31 21:53:52  wendy
// Cleanup RSA warnings
//
// Revision 1.26  2006/02/28 18:30:18  roger
// For recurring ABRs
//
// Revision 1.25  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.24  2005/01/26 01:30:16  dave
// fix catch exception
//
// Revision 1.23  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.22  2004/08/17 15:39:34  roger
// Support interleaved execution of ABRs by OPENID
//
// Revision 1.21  2004/03/18 20:44:32  roger
// ABRs need execution class for Idler assignment
//
// Revision 1.20  2003/09/09 19:03:40  roger
// Implement a run mode which grabs a queue and exits when the queue is completed
//
// Revision 1.19  2002/10/15 16:42:25  roger
// Parameterize port settings to allow multiple instances of TM
//
// Revision 1.18  2002/10/14 19:47:40  roger
// Make user id a property
//
// Revision 1.17  2002/10/02 23:06:25  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;

 
import java.io.FileInputStream;

import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */

public final class TaskMasterProperties extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Class constants
	private static final String PROPERTIES_FILENAME = "taskmaster.properties";
	// Class variables
	private static Properties c_propTaskMaster = null;

	/**
	 * Main method which performs a simple test of this class
	 *
	 * @param arg
	 */
	public static void main(String arg[]) {
		Log.out(TaskMasterProperties.getVersion());
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
	private TaskMasterProperties() {
	}
	/**
	 * Load the TaskMaster properties from the taskmaster.properties file
	 */
	private static final void loadProperties() {

		FileInputStream inProperties = null;
		try {
			if (c_propTaskMaster == null) {
				c_propTaskMaster = new Properties();

				inProperties =
					new FileInputStream(PROPERTIES_FILENAME);

				c_propTaskMaster.load(inProperties);
			}
		} catch (Exception x) {
			Log.out(
				"Unable to loadProperties for "
					+ PROPERTIES_FILENAME
					+ " "
					+ x);
		} finally {
			if (inProperties != null) {
				try {
					inProperties.close();
				} catch (java.io.IOException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	/**
	 * Reload the properties from the middleware.server.properties file
	 */
	public static final void reloadProperties() {
		loadProperties();
	}
	/**
	 * Return
	 *
	 * @return String
	 */
	public static final String getCodebaseRMI() {
		reloadProperties();

		return c_propTaskMaster.getProperty(
			"rmi_codebase",
			"file:taskmaster.jar");
	}
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final int getConnectionLeaseDuration() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty("connection_lease_duration", "60000"));
	}
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final int getConnectionLeaseRefreshQuantum() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty(
				"connection_lease_refresh_quantum",
				"60000"));
	}
	/**
	 * Return the DB gate delay
	 *
	 * @return long
	 */
	public static final long getGateDBDelay() {
		reloadProperties();

		return Long.parseLong(
			c_propTaskMaster.getProperty("gate_db_delay", "125"));
	}
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final int getIdlerCount() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty("idler_count", "5"));
	}
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final int getIdlerShutDownDelay() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty("idler_shutdown_delay", "10000"));
	}
	/**
	 * Return the path to the Java interpreter
	 *
	 * @return String
	 */
	public static final String getJavaExecutableName() {
		reloadProperties();

		return c_propTaskMaster.getProperty("jvm_exec", "java");
	}
	/**
	 * Return the path to the Java interpreter
	 *
	 * @return String[]
	 */
	public static final String[] getJVMParameters() {

        String[] astrParms = null;
        String strParameters = null;
        StringTokenizer stParms = null;

        Vector vctParms = new Vector();

		reloadProperties();

		strParameters =
			c_propTaskMaster.getProperty(
				"jvm_parameters",
				"-ms128M -mx256M -Xss32M");

        stParms = new StringTokenizer(strParameters, " ");

		while (stParms.hasMoreTokens()) {
			vctParms.add(stParms.nextToken());
		}

		astrParms = (String[]) vctParms.toArray(new String[0]);

		return astrParms;
	}
	/**
	 * Return the max number of times to retry ping
	 *
	 * @return int
	 */
	public static final int getPingRetryCount() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty("ping_retry_count", "3"));
	}
	/**
	 * Return the ping delay quantum
	 *
	 * @return long
	 */
	public static final long getPingRetryDelay() {
		reloadProperties();

		return Long.parseLong(
			c_propTaskMaster.getProperty("ping_retry_delay", "1000"));
	}
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final int getPulseQuantum() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty("pulse_quantum", "10000"));
	}
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final int getRebindQuantum() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty("rebind_quantum", "60000"));
	}
	/**
	 * Return the RMI Server bind IP Address property from the middleware.server.properties file
	 *
	 * @return String
	 */
	public static final String getServerBindIpAddress() {
		reloadProperties();

		return c_propTaskMaster.getProperty(
			"server_bind_ip_address",
			"127.0.0.1");
	}
	///**
	// * Return the RMI Server bind port property from the middleware.server.properties file
	// */
	//  public static final String getServerBindPort() {
	//    reloadProperties();
	//
	//    return c_propTaskMaster.getProperty("server_bind_port", "1099");
	//  }
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final int getShutWatchQuantum() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty("shutwatch_quantum", "10000"));
	}
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final int getTaskCompletionWait() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty("task_completion_wait", "10000"));
	}
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final int getTaskMasterShutDownDelay() {
		reloadProperties();

		return Integer.parseInt(
			c_propTaskMaster.getProperty("taskmaster_shutdown_delay", "15000"));
	}
	/**
	 * Return the TaskMaster user Id
	 *
	 * @return String
	 */
	public static final String getUserId() {
		reloadProperties();

		return c_propTaskMaster.getProperty("user", "eannounce11taskmaster");
	}
	/**
	 * Return the port
	 *
	 * @return String
	 */
	public static final String getRegistryPort() {
		reloadProperties();

		return c_propTaskMaster.getProperty("rmiregistry_port", "1099");
	}
	/**
	 * Return the port
	 *
	 * @return String
	 */
	public static final String getActivationDaemonPort() {
		reloadProperties();

		return c_propTaskMaster.getProperty("rmid_port", "1098");
	}
	/**
	 * Return the run mode (NORMAL/QEXIT)
	 * NORMAL mode allows the Queue to refresh, QEXIT mode only refreshes first time
	 * @return String
	 */
	public static final String getRunMode() {
		reloadProperties();

		return (c_propTaskMaster.getProperty("run_mode", "NORMAL"))
		.toUpperCase();
	}
	/**
	 * Return the classes for specified Idler
	 *
	 * @return String
	 * @param iIdlerIndex
	 */
	public static final String getIdlerClasses(int iIdlerIndex) {
		reloadProperties();
 
		return (
			c_propTaskMaster.getProperty(
				"idler" + iIdlerIndex + "_classes",
				"A")); // support upper and lower case now
		//.toUpperCase();
	}
	/**
	 * Return the interleaved mode (Y/N)
	 *
	 * @return String
	 */
	public static final String getInterleavedMode() {
		reloadProperties();

		return (c_propTaskMaster.getProperty("interleaved_mode", "N"))
		.toUpperCase();
	}
    /**
     * Return the runs recurring property (default = false)
     */
    public static final boolean getRunsRecurring() {
        reloadProperties();
        return Boolean.valueOf(c_propTaskMaster.getProperty("runs_recurring", "false")).booleanValue();
    }
    /**
     * Return the refresh on abr completion property (default = true)
     */
    public static final boolean refreshQonCompletion() {
        reloadProperties();
        return Boolean.valueOf(c_propTaskMaster.getProperty("refreshq_oncompletion", "true")).booleanValue();
    }
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: TaskMasterProperties.java,v 1.29 2013/09/11 18:40:37 wendy Exp $";
	}
}
