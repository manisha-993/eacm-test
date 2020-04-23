//  (c) Copyright International Business Machines Corporation, 2001-2003
//  All Rights Reserved.
//
//$Log: ECABR1.java,v $
//Revision 1.11  2008/01/30 20:00:36  wendy
//Cleanup more RSA warnings
//
//Revision 1.10  2006/01/25 17:45:15  yang
//Jtest changes
//
//Revision 1.9  2005/01/31 16:30:05  joan
//make changes for Jtest
//
//Revision 1.8  2005/01/27 16:39:54  joan
//changes for Jtest
//
//Revision 1.7  2003/10/07 22:03:57  gregg
//setDGTitle method per bala's instructions
//
//Revision 1.6  2003/09/18 19:50:29  gregg
//inserted bala's changes for finally clause
//
//Revision 1.5  2003/07/11 00:15:00  gregg
//linking to DIV:1
//
//Revision 1.4  2003/06/04 03:53:01  dave
//un Staticing getABRVersion
//
//Revision 1.3  2003/06/04 03:44:21  dave
//minor syntax
//
//Revision 1.2  2003/06/04 03:41:39  dave
//adding getABRVersion
//
//Revision 1.1.1.1  2003/06/03 19:02:23  dave
//new 1.1.1 abr 
//
//Revision 1.8  2003/05/28 23:35:25  gregg
//null ptr fix
//
//Revision 1.7  2003/05/28 18:27:01  gregg
//set return codes/filename typo
//
//Revision 1.6  2003/05/22 21:42:46  gregg
//remove unused static constants
//
//Revision 1.5  2003/05/22 21:38:33  gregg
//simplifying...
//
//Revision 1.4  2003/05/22 21:33:17  gregg
//clear softlocks when done
//
//Revision 1.3  2003/05/22 21:06:06  gregg
//update
//
//Revision 1.2  2003/05/22 00:36:02  gregg
//correct package name
//
//Revision 1.1  2003/05/21 22:01:02  gregg
//initial load -- testing this guy
//
package COM.ibm.eannounce.abr.pcd;

//import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.io.*;
import COM.ibm.eannounce.pmsync.PMComparisonReportWriter;
import java.util.Vector;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
//import COM.ibm.opicmpdh.transactions.OPICMList;

/**
 * Use ABR implementation to provide PM's EC-CTO comparison report.
 */
public class ECABR1 extends PokBaseABR {

	/**
     * ABR
     *
     */
	public static final String ABR = "ECABR1";
	private static final String EC_ENTTYPE = "EC";
	private static final String EC_BLOB_ATTCODE = "ECBLOB1";
	private static final String REPORT_FILENAME = "PMComparisonReport.html";

	/**
	 * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
	 * @author Administrator
	 */
	public void execute_run() {
		try {
			start_ABRBuild();
			genComparisonReport();
		} catch (Exception exc) {
			logError("ECABR1.execute_run(): " + exc.getMessage());
			exc.printStackTrace(System.err);
		} finally {
			// begin 091803 added per bala's changes 
			setDGTitle("PM Comparison Report");
			setDGRptName("ECABR1"); //Set the report name 
			printDGSubmitString();
			//Stuff into report for subscription and notification
			// end 091803
			if (!isReadOnly()) {
				clearSoftLock();
			}
		}
		return;
	}

	private void genComparisonReport() throws Exception {
		try {
			PMComparisonReportWriter pmcrw =
				new PMComparisonReportWriter(
					getEntityList(),
					getABRItem().getFileName());
			getABRItem().setPrintWriter(pmcrw);
			pmcrw.genComparisonReport();
			getRootEntityItem().put(
				EC_ENTTYPE + ":" + EC_BLOB_ATTCODE,
				new OPICMBlobValue(
					getProfile().getReadLanguage().getNLSID(),
					REPORT_FILENAME,
					getReportBytes()));
			getRootEntityItem().commit(getDatabase(), null);
			// for now, we will link this EC to DIV:1 - hardcoded - here.
			// later, we will need to do this in PMSyncEC class.
			Vector vctReturnEntityKeys = new Vector();
			vctReturnEntityKeys.addElement(
				new ReturnRelatorKey(
					"DIVEC",
					-1,
					"DIV",
					1,
					"EC",
					getRootEntityItem().getEntityID(),
					true));
			//OPICMList ol =
				EANUtility.link(
					getDatabase(),
					getProfile(),
					vctReturnEntityKeys,
					"NODUPES",
					EANUtility.LINK_DEFAULT,
					1,
					false);
		} catch (Exception exc) {
			logError("ECABR1.genComparisonReport(): " + exc.getMessage());
			exc.printStackTrace(System.err);
			setReturnCode(UPDATE_ERROR);
			throw exc;
		}
		setReturnCode(PASS);
		return;
	}

	private byte[] getReportBytes() throws Exception {
		FileInputStream fis = new FileInputStream(getABRItem().getFileName());
		byte[] ba = new byte[fis.available()];
		fis.read(ba);
		fis.close();
		return ba;
	}

	private EntityList getEntityList() {
		if (m_elist == null) {
			logError("ECABR1.getEntityList() is null!!!");
		}
		return m_elist;
	}

	private EntityItem getRootEntityItem() {
		return getEntityList().getParentEntityGroup().getEntityItem(0);
	}

	/**
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 * @author Administrator
	 */
	public String getDescription() {
		return "";
	}

	/**
	 * getVersion
	 *
	 * @return
	 * @author Administrator
	 */
	public static String getVersion() {
		return ("$Id: ECABR1.java,v 1.11 2008/01/30 20:00:36 wendy Exp $");
	}

	/**
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 * @author Administrator
	 */
	public String getABRVersion() {
		return getVersion();
	}

}
