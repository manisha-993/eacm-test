//javac -classpath $HOME/abrgenerator/script/ibmwebas.jar:.:./db2java.zip:/usr/java_dev2/jre/lib/rt.jar:$HOME/generator/source/middleware.jar PokBaseABR.java > errors
//
// (c) Copyright International Business Machines Corporation, 2001
// All Rights Reserved.

// WWTECHCOMPATABR.java,v
// Revision 1.4  2008/01/30 19:39:16  wendy
// Cleanup RSA warnings
//
// Revision 1.3  2007/09/28 02:41:08  jiehou
// *** empty log message ***
//
// Revision 1.2  2007/09/26 03:14:53  yang
// minor changes
//
// Revision 1.1  2007/09/26 02:59:15  yang
// initial load
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import java.sql.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.io.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class WWTECHCOMPATABR extends PokBaseABR {

	/**
	 * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
	 * @author Administrator
	 */
	public void execute_run() {
		EntityGroup eg;
		EntityItem ei;
		StringWriter writer;
		String x;
		try {

			start_ABRBuild(false);
			buildReportHeader();
			setNow();

			eg = m_db.getEntityGroup(m_prof, m_abri.getEntityType(), "Edit");
			ei = new EntityItem(eg, m_prof, m_db, m_abri.getEntityType(),
					m_abri.getEntityID());

			println("<b><tr><td class=\"PsgText\" width=\"100%\" >"
					+ getEntityType()
					+ ":</b></td><b><td class=\"PsgText\" width=\"100%\">"
					+ getEntityID() + "</b></td>");
			printNavigateAttributes(ei, eg, true);

			setReturnCode(PASS);
			if (getReturnCode() == PASS) {
				// rs = m_db.callGBL7777(null, null);
				for (int i = 0; i < 10; i++) {
					String Activity = "A";
					String TimeOfChange = "2007-09-26-02.35.01.553888";
					String SystemEntityType = "SystemEntity"+i;
					int SystemEntityId = i;
					String GroupEntityType = "GroupEntity"+i;
					int GroupEntityId = i;
					String OSEntityType = "OSEntity"+i;
					int OSEntityId = i;
					String OS = "OS";
					String OptionEntityType = "OptionEntity"+i;
					int OptionEntityId = i;
					String CompatibilityPublishingFlag = "Yes";
					String RelationshipType = "2";
					String PublishFrom = "From";
					String PublishTo = "To";
					callGBL7777(Activity, TimeOfChange, SystemEntityType,
							SystemEntityId, GroupEntityType, GroupEntityId,
							OSEntityType, OSEntityId, OS, OptionEntityType,
							OptionEntityId, CompatibilityPublishingFlag,
							RelationshipType, PublishFrom, PublishTo);
				}
			}

			println("<br /><b>"
					+ buildMessage(MSG_IAB2016I, new String[]{
							getABRDescription(),
							(getReturnCode() == PASS ? "Passed" : "Failed")})
					+ "</b>");

		} catch (LockPDHEntityException le) {
			setReturnCode(UPDATE_ERROR);
			println("<h3><font color=red>" + ERR_IAB1007E + "<br />"
					+ le.getMessage() + "</font></h3>");
		} catch (UpdatePDHEntityException le) {
			setReturnCode(UPDATE_ERROR);
			println("<h3><font color=red>UpdatePDH error: " + le.getMessage()
					+ "</font></h3>");
		} catch (Exception exc) {

			println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
			println("" + exc);

			// don't overwrite an update exception
			if (getABRReturnCode() != UPDATE_ERROR) {
				setReturnCode(INTERNAL_ERROR);
			}
			exc.printStackTrace();

			writer = new StringWriter();
			exc.printStackTrace(new PrintWriter(writer));
			x = writer.toString();
			println(x);

		} finally {

			setDGString(getABRReturnCode());
			setDGRptName("WWTECHCOMPATABR"); //Set the report name
			printDGSubmitString();
			//Stuff into report for subscription and notification
			buildReportFooter();
			if (!isReadOnly()) {
				clearSoftLock();
			}
		}
	}

	//reference callGBL2091
	public void callGBL7777(
			String Activity,
			String TimeOfChange,
			String SystemEntityType,
			int SystemEntityId,
			String GroupEntityType,
			int GroupEntityId,
			String OSEntityType,
			int OSEntityId,
			String OS,
			String OptionEntityType,
			int OptionEntityId,
			String CompatibilityPublishingFlag,
			String RelationshipType,
			String PublishFrom,
			String PublishTo			
	) throws SQLException, MiddlewareException {
		ReturnStatus returnStatus=null;
		Connection conn = null;
		CallableStatement m_cstmtHandle = null;
		conn = m_db.getPDHConnection();
		if (conn == null) {
			m_db.connect();
			conn = m_db.getPDHConnection();
		}
		String strSQL = "CALL opicm.GBL7777(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		m_cstmtHandle = conn.prepareCall(strSQL);
		m_cstmtHandle.registerOutParameter(1, Types.INTEGER);
		// Bind the method parameters to the SQL statement markers
		m_cstmtHandle.setString(1,Activity);
		m_cstmtHandle.setString(2,TimeOfChange);
		m_cstmtHandle.setString(3,SystemEntityType);
		m_cstmtHandle.setInt(4,SystemEntityId);
		m_cstmtHandle.setString(5,GroupEntityType);
		m_cstmtHandle.setInt(6,GroupEntityId);
		m_cstmtHandle.setString(7,OSEntityType);
		m_cstmtHandle.setInt(8,OSEntityId);
		m_cstmtHandle.setString(9,OS);
		m_cstmtHandle.setString(10,OptionEntityType);
		m_cstmtHandle.setInt(11,OptionEntityId);
		m_cstmtHandle.setString(12,CompatibilityPublishingFlag);
		m_cstmtHandle.setString(13,RelationshipType);
		m_cstmtHandle.setString(14,PublishFrom);
		m_cstmtHandle.setString(15,PublishTo);

        // Execute the SQL statement
        m_cstmtHandle.executeUpdate();
        // Retrieve the return status
        returnStatus.setValue(m_cstmtHandle.getInt(1));	
        m_cstmtHandle.close();
	}

	/**
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABREntityDesc(java.lang.String, int)
	 * @author Administrator
	 */
	protected String getABREntityDesc(String entityType, int entityId) {
		return null;
	}

	/**
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 * @author Administrator
	 */
	public String getDescription() {
		return "<br /><br />";
	}

	/**
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getStyle()
	 * @author Administrator
	 */
	protected String getStyle() {
		return "";
	}

	/**
	 * getevision
	 *
	 * @return
	 * @author Administrator
	 */
	public String getRevision() {
		return new String("1.4");
	}

	/**
	 * printPassMessage
	 *
	 * @author Administrator
	 */
	public void printPassMessage() {
		println("<br /><b>send to Production Management</b><br /><br />");
	}

	/**
	 * getVersion
	 *
	 * @return
	 * @author Administrator
	 */
	public static String getVersion() {
		return ("WWTECHCOMPATABR.java,v 1.4 2008/01/30 19:39:16 wendy Exp");
	}
	/**
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 * @author Administrator
	 */
	public String getABRVersion() {
		return getVersion();
	}

}
