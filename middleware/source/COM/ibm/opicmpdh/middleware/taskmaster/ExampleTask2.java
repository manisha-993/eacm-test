//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ExampleTask2.java,v $
// Revision 1.7  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.6  2005/01/25 22:34:58  dave
// Jtest Syntax
//
// Revision 1.5  2002/10/02 23:06:10  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Stopwatch;


import COM.ibm.opicmpdh.transactions.OPICMABRItem;

import java.sql.ResultSet;




/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */
public class ExampleTask2 extends AbstractTask {
	private Database dbCurrent = null;
	private Profile profCurrent = null;
	private OPICMABRItem abriCurrent = null;

	/**
     * @author Dave
     */
    public ExampleTask2() {
	}
	/**
     * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_restart()
     * @author Dave
     */
    public void execute_restart() {
		logMessage("this task is not restartable!");  //$NON-NLS-1$
		this.execute_run();
	}
	/**
     * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
     * @author Dave
     */
    public void execute_run() {

		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rsTest = null;
		long lStart = System.currentTimeMillis();
		long lTime = 0;
		long lLast = 0;
		long i = 0;

		dbCurrent = getDatabase();
		profCurrent = getProfile();
		abriCurrent = getABRItem();

		logMessage(
			"EntityType = "  //$NON-NLS-1$
				+ abriCurrent.getEntityType()
				+ " ID = "  //$NON-NLS-1$
				+ abriCurrent.getEntityID());
		logMessage(profCurrent.dump());

		while ((lTime - lStart) < 20000) {
			++i;

			lTime = System.currentTimeMillis();

			if ((lTime - lLast) > 1000) {
				logMessage(
					"counter "  //$NON-NLS-1$
						+ i
						+ " time "  //$NON-NLS-1$
						+ Stopwatch.format(
							(lTime - lStart) - ((lTime - lStart) % 1000)));
				print(
					"counter "  //$NON-NLS-1$
						+ i
						+ " time "  //$NON-NLS-1$
						+ Stopwatch.format(
							(lTime - lStart) - ((lTime - lStart) % 1000)));
				dbCurrent.freeStatement();
				dbCurrent.isPending("example2a");  //$NON-NLS-1$

				try {
					rsTest = dbCurrent.callGBL2028(returnStatus);

					while (rsTest.next()) {
						logMessage(
							"now ==" + rsTest.getString(1).trim() + "==");  //$NON-NLS-1$  //$NON-NLS-2$
					}
				} catch (Exception x) {
					logMessage("trouble with database " + x);  //$NON-NLS-1$
				} finally {
					try {
						rsTest.close();

						rsTest = null;
					} catch (Exception x) {
						logMessage("rsTest.close() failed " + x);  //$NON-NLS-1$
					}

					dbCurrent.freeStatement();
					dbCurrent.isPending("example2b");  //$NON-NLS-1$
				}

				lLast = lTime;
			}
		}
	}
}
