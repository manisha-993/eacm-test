/**
 * <pre>
 * (c) Copyright International Business Machines Corporation, 2005
 * All Rights Reserved.
 * CR0117053428
 * Runs on SBB status changes (Ready for Review -> Final).
 *  ABR calculates the California Recycling Fee, SBB.CAFEE, based on Display element linked to SBB (SBBMON)
SBBABR001_class=COM.ibm.eannounce.abr.psg.SBBABR001
SBBABR001_enabled=true
SBBABR001_keepfile=true
SBBABR001_read_only=false
SBBABR001_idler_class=A
SBBABR001_vename=EXSBBABR001
 *
 * $Log: SBBABR001.java,v $
 * Revision 1.5  2006/01/24 22:16:03  yang
 * Jtest changes
 *
 * Revision 1.4  2005/10/06 14:59:17  wendy
 * Conform to new jtest config
 *
 * Revision 1.3  2005/02/14 01:16:53  wendy
 * TIR 69KVV7 added check for same Monitor linked more than once
 *
 * Revision 1.2  2005/02/08 18:29:11  joan
 * changes for Jtest
 *
 * Revision 1.1  2005/02/03 19:30:33  wendy
 * Init for CR0117053428
 *
 *
 * </pre>
 *
 *@author     Wendy Stimpson
 *@created    January 20, 2005
 */
package COM.ibm.eannounce.abr.psg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.text.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class SBBABR001 extends PokBaseABR {
	private Object[] mfParms = new String[10];
	private ResourceBundle bundle = null;
	private StringBuffer rptSb = new StringBuffer();
	private StringBuffer traceSb = new StringBuffer();
	private static final double SCREENSIZENOM_IN_VALUE = 15.0;
	private static final String CAFEE_Y1 = "0010"; // flag[0] Y1 code: 0010
	private static final String CAFEE_Y2 = "0020"; // flag[1] Y2 code: 0020
	//private static final String CAFEE_Y3 = "0030"; // flag[2] Y3 code: 0030

	private static final String DESCRIPTION =
		"SBB California Recycling Fee Calculation.<br/>This ABR calculates the California Recycling Fee based on Monitor Size.";
	private static final String SET_MSG = "<p>Set &quot;{0}&quot; to {1}.</p>";
	private static final String SIZE_MSG = "<p>&quot;{0}&quot; is {1}.</p>";
	private static final String NO_MON_MSG =
		"<p>ABR passed, but no {0} exists.</p>";
	private static final String ALREADYSET_MSG =
		"<p>&quot;{0}&quot; was already set to {1}.</p>";
	private static final String NULL_SCR =
		"<p>SBB cannot go Final, &quot;{0}&quot; is not populated.</p>";
	private static final String TOO_MANY =
		"<p>SBB cannot go Final, more than one {0} exists.</p>";
	private static final String SBBSTATUS =
		"<p>SBBABR001 cannot Pass because Status is not Ready for Review.</p>";
	private static final String CODE_ERROR =
		"<p>Error: did not find {0} in {1}.</p>";
	private static final String FLAG_ERROR =
		"<p>Error: {0} is not a MetaSingleFlagAttribute.</p>";
	private static final String VALUE_ERROR =
		"<p>Error: did not find a {0} code/value in the {1} attribute.</p>";
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);

	/**
	 *  Execute ABR.
	 *
	The report should show:
	ABR name and title = SBB California Recycling Fee Calculation
	Date Generated
	Who last changed the entity ==> userid and role
	Description: Calculates California Recycling Fee based on Monitor Size
	SBB Name: SBBPNUMBDESC
	
	Show results of calculation
	 */
	public void execute_run() {
		String HEADER =
			"<html><head><title>{0} {1}</title></head><body>"
			+ NEWLINE
			+ "<h1>{0} {1}</h1><p><b>Date: </b>{2}<br/><b>User: </b>{3} ({4})<br /><b>Description: </b>{5}<br /><b>SBB: </b>{6}</p>"
			+ NEWLINE
			+ "<!-- {7} -->";

		String navName = "";
		String sbbName = "";
		MessageFormat msgf = null;
		try {
			EntityItem sbbItem;
			String sbbStatus;
			boolean success;

			start_ABRBuild();

			// debug display list of groups
			rptSb.append(
				"<!-- DEBUG: SBBABR001 entered for "
					+ getEntityType()
					+ ":"
					+ getEntityID()
					+ NEWLINE
					+ outputList(m_elist)
					+ " -->"
					+ NEWLINE);

			bundle =
				ResourceBundle.getBundle(
					this.getClass().getName(),
					getLocale(m_prof.getReadLanguage().getNLSID()));

			// if VE is not defined, throw exception
			if (m_elist.getEntityGroupCount() == 0) {
				throw new Exception("EntityList did not have any groups. Verify that extract is defined.");
			}

			sbbItem = m_elist.getParentEntityGroup().getEntityItem(0);
			//  get SBB.SBBPNUMB
			sbbName =
				getAttributeValue(
					sbbItem.getEntityType(),
					sbbItem.getEntityID(),
					"SBBPNUMB",
					"");

			//Change Request [0050] Draft [0010] Final [0020] Ready for Review [0040]
			sbbStatus =
				getAttributeFlagEnabledValue(
					getRootEntityType(),
					getRootEntityID(),
					"SBBSTATUS",
					"");
			success = sbbStatus.equals("0040");
			if (!success) {
				// output other possible errors too
				EntityGroup egMON = m_elist.getEntityGroup("MON");
				rptSb.append(
					bundle == null
						? SBBSTATUS
						: bundle.getString("Error_SBBSTATUS") + NEWLINE);
				if (egMON.getEntityItemCount() > 1) {
					mfParms[0] = egMON.getLongDescription();
					//<p>SBB cannot go Final, more than one {0} exists..</p>
					msgf =
						new MessageFormat(
							bundle == null
							? TOO_MANY
							: bundle.getString("Error_TOO_MANY"));
					rptSb.append(msgf.format(mfParms) + NEWLINE);
				}
			} else {
				success = setCARecycleFee(sbbItem);
				//  Show results of calculation
			}

			// NAME is navigate attributes, get it after processing, CAFEE is part of name
			navName = getNavigationName();

			if (success) {
				EANMetaAttribute ma;
				// set SBBSTATUS to final
				triggerWorkFlow(sbbItem, "WFSBBSTATUS");

				ma = sbbItem.getEntityGroup().getMetaAttribute("SBBSTATUS");
				mfParms[0] = ma.getLongDescription();
				mfParms[1] = "Final";
				msgf =
					new MessageFormat(
						bundle == null ? SET_MSG : bundle.getString("SET_MSG"));
				rptSb.append(msgf.format(mfParms) + NEWLINE);

				setReturnCode(PASS);
			} else {
				setReturnCode(FAIL);
			}
		} catch (Throwable exc) {
			String Error_EXCEPTION =
				"<h3><font color=red>Error: {0}</font></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			msgf =
				new MessageFormat(
				bundle == null
					? Error_EXCEPTION
					: bundle.getString("Error_EXCEPTION"));
			mfParms[0] = exc.getMessage();
			rptSb.append(msgf.format(mfParms) + NEWLINE);
			msgf =
				new MessageFormat(
				bundle == null
					? Error_STACKTRACE
					: bundle.getString("Error_STACKTRACE"));
			mfParms[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(mfParms) + NEWLINE);
			logError("Exception: " + exc.getMessage());
			logError(exBuf.getBuffer().toString());
			setReturnCode(FAIL);
		} finally {
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass("SBBABR001");
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
		}

		// Insert Header into beginning of report
		msgf =
			new MessageFormat(
				bundle == null ? HEADER : bundle.getString("HEADER"));
		mfParms[0] = getShortClassName(getClass());
		mfParms[1] =
			navName + ((getReturnCode() == PASS) ? " Passed" : " Failed");
		mfParms[2] = getNow();
		mfParms[3] = m_prof.getOPName();
		mfParms[4] = m_prof.getRoleDescription();
		mfParms[5] = getDescription();
		mfParms[6] = sbbName;
		mfParms[7] = getABRVersion();
		rptSb.insert(0, msgf.format(mfParms) + NEWLINE);
		rptSb.append("<!-- DEBUG: " + traceSb.toString() + " -->");
		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		buildReportFooter();
	}

	/**
	*  ABR calculates the California Recycling Fee, SBB.CAFEE, based on Display element linked to SBB (SBBMON)
	e)  ABR Logic
	
	ABR looks at MON attribute SCREENSIZENOM_IN.
	
	If SBBMON = 0,
	Then PASS, set CAFEE to null,
	Set Status to FINAL,
	Print, ABR passed, but no MON exists.
	Else If SBBMON > 1,
	Then FAIL,
	Print ‘SBB cannot go Final, more than one Monitor exists.’
	Else If SCREENSIZENOM  <= 14.9,
	then PASS, set SBB.CAFEE to Y1,
	set Status to FINAL
	else If SCREENSIZENOM >= 15,
	then PASS, set SBB.CAFEE to Y2,
	set Status to FINAL
	else If SCREENSIZENOM = null,
	then FAIL,
	Print ‘SBB cannot go Final, California Recycling Fee is not populated,
	
	* @param sbbItem EntityItem SBB to have CAFEE attr set
	* @return boolean true if successful
	*/
	private boolean setCARecycleFee(EntityItem sbbItem)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
	COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	java.sql.SQLException {
		boolean success = false;
		EntityGroup egMON = m_elist.getEntityGroup("MON");

		String attrCode = "CAFEE";
		MessageFormat msgf = null;
		EANMetaAttribute ma =
			sbbItem.getEntityGroup().getMetaAttribute(attrCode);
		if (ma == null || !(ma instanceof MetaSingleFlagAttribute)) {
			mfParms[0] = attrCode;
			if (ma == null) {
				msgf =
					new MessageFormat(
					bundle == null
						? CODE_ERROR
						: bundle.getString("Error_CODE"));
				mfParms[1] = sbbItem.getLongDescription();
			} else {
				msgf =
					new MessageFormat(
					bundle == null
						? FLAG_ERROR
						: bundle.getString("Error_FLAG"));
			}
			rptSb.append(msgf.format(mfParms) + NEWLINE);
		} else {
			// add check if same MON is linked 2 times, TIR USRO-R-JSTT-69KVV7
			EntityGroup egSBBMON = m_elist.getEntityGroup("SBBMON");
			if (egSBBMON.getEntityItemCount() > 1) {
				deactivateUniqueFlag(sbbItem, ma); // null out CAFEE
				mfParms[0] = egMON.getLongDescription();
				//<p>SBB cannot go Final, more than one {0} exists..</p>
				msgf =
					new MessageFormat(
					bundle == null
						? TOO_MANY
						: bundle.getString("Error_TOO_MANY"));
				rptSb.append(msgf.format(mfParms) + NEWLINE);
				success = false;
			} else {
				switch (egMON.getEntityItemCount()) {
				case 0 :
					deactivateUniqueFlag(sbbItem, ma);
					mfParms[0] = egMON.getLongDescription();
					// <p>ABR passed, but no {0} exists.</p>
					msgf =
                    new MessageFormat(
                        bundle == null
                        ? NO_MON_MSG
                        : bundle.getString("NO_MON_MSG"));
					rptSb.append(msgf.format(mfParms) + NEWLINE);
					success = true;
					break;
				case 1 :
					success =
					setCARecycleFee(
						sbbItem,
						ma,
						egMON.getEntityItem(0));
                    break;
				default : //What should happen if there is more than one MON linked? Fail the ABR
					deactivateUniqueFlag(sbbItem, ma); // null out CAFEE
					mfParms[0] = egMON.getLongDescription();
					//<p>SBB cannot go Final, more than one {0} exists..</p>
					msgf =
						new MessageFormat(
						bundle == null
						? TOO_MANY
						: bundle.getString("Error_TOO_MANY"));
                    rptSb.append(msgf.format(mfParms) + NEWLINE);
					success = false;
					break;
				}
			}
		}
		return success;
	}

	/**
	*  ABR calculates the California Recycling Fee, SBB.CAFEE, based on Display element linked to SBB (SBBMON)
	* Look at the MON attribute SCREENSIZENOM_IN.
	* If SCREENSIZENOM_IN value is 14.9 or less, assign value "Y1".
	* If SCREENSIZENOM_IN value is 15.0 or greater, assign value "Y2".
	* If SCREENSIZENOM_IN is null, leave California Recycling Fee null.
	* @param sbbItem EntityItem SBB to have CAFEE attr set
	* @param ma EANMetaAttribute with flag attribute
	* @param monItem EntityItem MON to check screensize attr
	* @return boolean true if successful
	*/
	private boolean setCARecycleFee(
		EntityItem sbbItem,
		EANMetaAttribute ma,
		EntityItem monItem)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
	COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	java.sql.SQLException {
		boolean ok = false;
		String scrsize =
			getAttributeValue(
				monItem.getEntityType(),
				monItem.getEntityID(),
				"SCREENSIZENOM_IN",
				null);
		EANMetaAttribute mAttr =
			monItem.getEntityGroup().getMetaAttribute("SCREENSIZENOM_IN");
		MessageFormat msgf =
			new MessageFormat(
				bundle == null ? SIZE_MSG : bundle.getString("SIZE_MSG"));
		mfParms[0] = mAttr.getActualLongDescription();
		mfParms[1] = (scrsize == null ? "null" : scrsize);
		rptSb.append(msgf.format(mfParms) + NEWLINE);

		if (scrsize == null) // it is a required attr.. so can't happen...
        {
			traceSb.append(
				"Setting CAFEE to null because SCREENSIZENOM_IN was null for "
					+ monItem.getKey()
					+ NEWLINE);
			deactivateUniqueFlag(sbbItem, ma);

			//<p>SBB cannot go Final, &quot;{0}&quot; is not populated.</p>
			mfParms[0] = mAttr.getActualLongDescription();
			msgf =
				new MessageFormat(
					bundle == null
                ? NULL_SCR
				: bundle.getString("Error_NULL_SCR"));
			rptSb.append(msgf.format(mfParms) + NEWLINE);
		} else {
			double scrsizefl = Double.parseDouble(scrsize);
			String cafeeFlag = CAFEE_Y1;
			if (scrsizefl >= SCREENSIZENOM_IN_VALUE) { //15.0
				cafeeFlag = CAFEE_Y2;
			}

			traceSb.append(
				"SCREENSIZENOM_IN was "
					+ scrsizefl
					+ " for "
					+ monItem.getKey()
					+ NEWLINE);

			ok = setUniqueFlag(sbbItem, ma, cafeeFlag);
		}
		return ok;
	}

	/********************************************************************************
	* Delete unique flag attribute
	* @param ei EntityItem to delete attribute from
	* @param ma EANMetaAttribute with flag attribute
	*/
	private void deactivateUniqueFlag(EntityItem ei, EANMetaAttribute ma)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
	COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	java.sql.SQLException {
		String attrCode = ma.getAttributeCode();
		String curValue =
			getAttributeValue(
				ei.getEntityType(),
				ei.getEntityID(),
				attrCode,
				null);
		traceSb.append(
			"deactivateUniqueFlag: Setting "
				+ attrCode
				+ " to null for "
				+ ei.getKey()
				+ NEWLINE);
		traceSb.append(
			"deactivateUniqueFlag: "
				+ attrCode
				+ " current value "
				+ curValue
				+ NEWLINE);

		if (curValue != null) {
			//mfParms[0] = ma.getLongDescription();
			//mfParms[1] = "null";
			//MessageFormat msgf = new MessageFormat(bundle==null?ALREADYSET_MSG:bundle.getString("ALREADYSET_MSG"));
			//rptSb.append(msgf.format(mfParms)+NEWLINE);
			MessageFormat msgf =
				new MessageFormat(
					bundle == null ? SET_MSG : bundle.getString("SET_MSG"));

			// delete this attribute
			ei.put(ei.getEntityType() + ":" + attrCode, null);
			ei.commit(m_db, null);

			mfParms[0] = ma.getLongDescription();
			mfParms[1] = "null";

			rptSb.append(msgf.format(mfParms) + NEWLINE);
		}
	}

	/********************************************************************************
	* Create and/or set unique flag attribute to specified flag code
	* @param ei EntityItem to add/set attribute to
	* @param ma EANMetaAttribute with flag attribute
	* @param flagCode String with value
	*/
	private boolean setUniqueFlag(
		EntityItem ei,
		EANMetaAttribute ma,
		String flagCode)
		throws
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
	COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	java.sql.SQLException {
		boolean ok = false;
		String attrCode = ma.getAttributeCode();
		String curValue =
			getAttributeValue(
				ei.getEntityType(),
				ei.getEntityID(),
				attrCode,
				null);
		String curFlag =
			getAttributeFlagEnabledValue(
				ei.getEntityType(),
				ei.getEntityID(),
				attrCode,
				"");
		traceSb.append(
			"setUniqueFlag: Setting "
				+ attrCode
				+ " to "
				+ flagCode
				+ " for "
				+ ei.getKey()
				+ NEWLINE);
		traceSb.append(
			"setUniqueFlag: "
				+ attrCode
				+ " current value "
				+ curValue
				+ " curFlag: "
				+ curFlag
				+ NEWLINE);
		if (curFlag.equals(flagCode)) {
			MessageFormat msgf =
				new MessageFormat(
					bundle == null
					? ALREADYSET_MSG
					: bundle.getString("ALREADYSET_MSG"));
			mfParms[0] = ma.getLongDescription();
			mfParms[1] = curValue;
			rptSb.append(msgf.format(mfParms) + NEWLINE);
			ok = true;
		} else {
			MetaFlag[] mfa;
			EANFlagAttribute flagAttr =
				(EANFlagAttribute) ei.getAttribute(attrCode);
			if (flagAttr == null) {
				flagAttr =
					new SingleFlagAttribute(
						ei,
						m_prof,
						(MetaSingleFlagAttribute) ma);
				ei.putAttribute(flagAttr);
			}

			mfa = (MetaFlag[]) flagAttr.get();
			for (int i = 0;
				i < mfa.length;
				i++) // make sure all flags are turned off
            {
				mfa[i].setSelected(false);
			}

			for (int i = 0; i < mfa.length; i++) {
				if (mfa[i].getFlagCode().equals(flagCode)) {
					MessageFormat msgf =
						new MessageFormat(
							bundle == null
							? SET_MSG
							: bundle.getString("SET_MSG"));
					mfa[i].setSelected(true);
					flagAttr.put(mfa);
					ei.commit(m_db, null);

					mfParms[0] = ma.getLongDescription();
					mfParms[1] = mfa[i].getLongDescription();
					rptSb.append(msgf.format(mfParms) + NEWLINE);
					ok = true;
					break;
				}
			}

			if (!ok) {
				// failed if get here
				MessageFormat msgf =
					new MessageFormat(
						bundle == null
						? VALUE_ERROR
						: bundle.getString("Error_VALUE"));
				mfParms[0] = flagCode;
				mfParms[1] = ma.getLongDescription();
				rptSb.append(msgf.format(mfParms) + NEWLINE);
			}
		}
		return ok;
	}

	/**********************************************
	 *  Triggers the specified workflow to advance SBBSTATUS
	 *
	 * @param sbbItem EntityItem
	 * @param actionName Name of the workflow action.
	 * @exception  java.sql.SQLException
	 * @exception  COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @exception  COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	 * @exception  COM.ibm.eannounce.objects.WorkflowException
	 */
	private void triggerWorkFlow(EntityItem sbbItem, String actionName)
		throws
    java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.eannounce.objects.WorkflowException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		WorkflowActionItem wfai;
		EntityItem[] aItems = new EntityItem[1];
		aItems[0] = sbbItem;
		wfai = new WorkflowActionItem(null, m_db, m_prof, actionName);
		wfai.setEntityItems(aItems);
		m_db.executeAction(m_prof, wfai);
		aItems[0] = null;
	}

	/**********************************************************************************
	*  Get Name based on navigation attributes
	*
	*@return    java.lang.String
	*/
	private String getNavigationName()
		throws java.sql.SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		EntityGroup eg =
			new EntityGroup(
				null,
				m_db,
				m_prof,
				getRootEntityType(),
				"Navigate");
		EANList metaList = eg.getMetaAttribute();
		// iterator does not maintain navigate order
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(
				getAttributeValue(
					getRootEntityType(),
					getRootEntityID(),
					ma.getAttributeCode()));
			navName.append(" ");
		}

		return navName.toString();
	}

	/**
	 *  Get ABR description
	 *
	 *@return    java.lang.String
	 */
	public String getDescription() {
		String desc = DESCRIPTION;
		if (bundle != null) {
			desc = bundle.getString("DESCRIPTION");
		}
		return desc;
	}

	/**
	 *  Get the version
	 *
	 *@return    java.lang.String
	 */
	public String getABRVersion() {
		return "$Revision: 1.5 $";
	}
	private Locale getLocale(int nlsID) {
		Locale locale = null;
		switch (nlsID) {
		case 1 :
			locale = Locale.US;
		case 2 :
			locale = Locale.GERMAN;
			break;
		case 3 :
			locale = Locale.ITALIAN;
			break;
		case 4 :
			locale = Locale.JAPANESE;
			break;
		case 5 :
			locale = Locale.FRENCH;
			break;
		case 6 :
			locale = new Locale("es", "ES");
			break;
		case 7 :
			locale = Locale.UK;
			break;
		default :
			locale = Locale.US;
		}
		return locale;
	}
	/**
	 * @param list
	 * @return
	 * @author Administrator
	 */
	static String outputList(EntityList list) // debug
	{
		StringBuffer sb = new StringBuffer();
		EntityGroup peg = list.getParentEntityGroup();
		if (peg != null) {
			sb.append(
				peg.getEntityType()
					+ " : "
					+ peg.getEntityItemCount()
					+ " Parent entity items. ");
			if (peg.getEntityItemCount() > 0) {
				sb.append("IDs(");
				for (int e = 0; e < peg.getEntityItemCount(); e++) {
					sb.append(" " + peg.getEntityItem(e).getEntityID());
				}
				sb.append(")");
			}
			sb.append(NEWLINE);
		}

		for (int i = 0; i < list.getEntityGroupCount(); i++) {
			EntityGroup eg = list.getEntityGroup(i);
			sb.append(
				eg.getEntityType()
					+ " : "
					+ eg.getEntityItemCount()
					+ " entity items. ");
			if (eg.getEntityItemCount() > 0) {
				sb.append("IDs(");
				for (int e = 0; e < eg.getEntityItemCount(); e++) {
					sb.append(" " + eg.getEntityItem(e).getEntityID());
				}
				sb.append(")");
			}
			sb.append(NEWLINE);
		}
		return sb.toString();
	}
}
