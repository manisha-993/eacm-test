/**
 * <pre>
 * (c) Copyright International Business Machines Corporation, 2003
 * All Rights Reserved.
 *
 * $Log: CVARABR002.java,v $
 * Revision 1.22  2008/01/30 19:27:44  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.21  2006/01/24 22:13:43  yang
 * Jtest changes
 *
 * Revision 1.20  2005/04/19 18:57:12  chris
 * Roll back CR4220
 *
 * Revision 1.16  2003/11/12 20:38:06  chris
 * put Revision back
 *
 * Revision 1.15  2003/11/06 22:01:19  bala
 * EC drop
 *
 * Revision 1.10  2003/10/28 20:57:25  cstolpe
 * Added Bala's changes
 *
 * Revision 1.9  2003/10/08 11:41:07  cstolpe
 * Fix related to FB 52441
 *
 * Revision 1.8  2003/09/22 18:38:43  cstolpe
 *  Fixes for FB 52200:4F0D30 and 52202:5537C6 neither defaulted to PASS or output a default  pass message
 *
 * Revision 1.7  2003/09/22 14:56:21  cstolpe
 * Fix for FB52241 Family and Series association name changed
 *
 * Revision 1.6  2003/09/11 21:44:26  cstolpe
 * Latest Updates
 *
 * Revision 1.5  2003/07/31 16:42:11  cstolpe
 * Remove java code to strip Revision. Done by build now.
 *
 * Revision 1.4  2003/07/28 15:34:43  cstolpe
 * Change DG submit (not final)
 *
 * Revision 1.3  2003/06/20 17:37:52  cstolpe
 * use AttributeChangeHistoryItem.getFlagCode()
 *
 * Revision 1.2  2003/06/19 17:50:48  cstolpe
 * Externalized strings to resource bundles
 *
 * Revision 1.1.1.1  2003/06/19 14:05:02  cstolpe
 * Initial Import
 *
 * </pre>
 *
 *@author     cstolpe
 *@created    May 8, 2003
 */
package COM.ibm.eannounce.abr.psg;

//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.text.*;

/**
 * CVARABR002
 *
 * @author Owner
 */
public final class CVARABR002 extends PokBaseABR {
	private MessageFormat mfOut = null;
	private Object[] mfParms = new String[10];
	private ResourceBundle msgs = null;
	//Order is Draft, Ready for Review, Validate, Final, Change Request
	//private static final String statusOrder = "1040302050";
	//STATUS_CVAR	10	Draft
	//STATUS_CVAR	40	Ready for Review
	//STATUS_CVAR	30	Validate
	//STATUS_CVAR	20	Final
	//STATUS_CVAR	50	Change Request
	//VARSTATUS	10	Draft
	//VARSTATUS	40	Ready for Review
	//VARSTATUS	30	Validate
	//VARSTATUS	20	Final
	//VARSTATUS	50	Change Request
	private StringBuffer rpt = new StringBuffer();

	/**
	 *  Execute ABR.
	 *
	 */
	public void execute_run() {
		EntityGroup eg = null;
		Vector vctVAR;
		Iterator itMeta;
		Iterator itVAR;
		Iterator itEI;
		String strVARSTATUS = null;
		String strSTATUS_CVAR = null;
		java.io.StringWriter exBuf;
		StringBuffer navName = new StringBuffer();
		try {
			start_ABRBuild();
			// NAME is navigate attributes
			eg =
				new EntityGroup(
					null,
					m_db,
					m_prof,
					getRootEntityType(),
					"Navigate");
			itMeta = eg.getMetaAttribute().values().iterator();
			while (itMeta.hasNext()) {
				EANMetaAttribute ma = (EANMetaAttribute) itMeta.next();
				navName.append(
					getAttributeValue(
						getRootEntityType(),
						getRootEntityID(),
						ma.getAttributeCode()));
				if (itMeta.hasNext()) {
					navName.append(" ");
				}
			}
			msgs =
				ResourceBundle.getBundle(
					this.getClass().getName(),
					getLocale(m_prof.getReadLanguage().getNLSID()));
			mfParms = new String[10];

			// Output Brand, Family, Series, Project
			mfOut = new MessageFormat(msgs.getString("PATH"));
			itEI =
				m_elist
				.getEntityGroup("PR")
				.getEntityItem()
				.values()
				.iterator();
			while (itEI.hasNext()) {
				EntityItem eiPR = (EntityItem) itEI.next();
				mfParms[0] =
					getAttributeValue(
						eiPR.getEntityType(),
						eiPR.getEntityID(),
						"BRANDCODE");
				mfParms[1] =
					getAttributeValue(
						eiPR.getEntityType(),
						eiPR.getEntityID(),
						"FAMNAMEASSOC");
				mfParms[2] =
					getAttributeValue(
						eiPR.getEntityType(),
						eiPR.getEntityID(),
						"SENAMEASSOC");
				mfParms[3] =
					getAttributeValue(
						eiPR.getEntityType(),
						eiPR.getEntityID(),
						"NAME");
				rpt.append(mfOut.format(mfParms));
			}

			// Output Root CVAR
			printEntity(getRootEntityType(), getRootEntityID(), 0);

			// Output VAR parents
			mfOut = new MessageFormat(msgs.getString("PARENT_STATUS"));
			vctVAR = getEntityIds("VAR", "VARCVAR");
			itVAR = vctVAR.iterator();
			while (itVAR.hasNext()) {
				int id = ((Integer) itVAR.next()).intValue();
				mfParms[0] = getEntityDescription("VAR");
				mfParms[1] = getAttributeValue("VAR", id, "NAME");
				mfParms[2] = getAttributeDescription("VAR", "VARSTATUS");
				mfParms[3] = getAttributeValue("VAR", id, "VARSTATUS");
				rpt.append(mfOut.format(mfParms));
			}

			// Compare status (only to first parent found)
			strVARSTATUS =
				getAttributeFlagEnabledValue(
					"VAR",
					((Integer) vctVAR.firstElement()).intValue(),
					"VARSTATUS",
					"");
			strSTATUS_CVAR =
				getAttributeFlagEnabledValue(
					getRootEntityType(),
					getRootEntityID(),
					"STATUS_CVAR",
					"");
			if (strVARSTATUS == null || strSTATUS_CVAR == null) {
				setReturnCode(FAIL);
				rpt.append(msgs.getString("STATUS_ERROR"));
			}
			setReturnCode(PASS);
			if ("0040".equals(strSTATUS_CVAR)) {
				if ("0020".equals(strVARSTATUS)) {
					setControlBlock();
					setFlagValue("STATUS_CVAR", "0020");
					rpt.append(msgs.getString("PASS_MSG"));
					setReturnCode(PASS);
				} else {
					setReturnCode(FAIL);
					rpt.append(msgs.getString("FAIL_MSG"));
				}
			} else {
				rpt.append(msgs.getString("STD_PASS_MSG"));
			}
		} catch (LockPDHEntityException le) {
			setReturnCode(FAIL);
			mfOut = new MessageFormat(msgs.getString("LOCK_ERROR"));
			mfParms[0] = ERR_IAB1007E;
			mfParms[1] = le.getMessage();
			rpt.append(mfOut.format(mfParms));
			logError(le.getMessage());
		} catch (UpdatePDHEntityException le) {
			setReturnCode(FAIL);
			mfOut = new MessageFormat(msgs.getString("UPDATE_ERROR"));
			mfParms[0] = le.getMessage();
			rpt.append(mfOut.format(mfParms));
			logError(le.getMessage());
		} catch (Exception exc) {
			setReturnCode(FAIL);
			// Report this error to both the datbase log and the PrintWriter
			mfOut = new MessageFormat(msgs.getString("EXCEPTION_ERROR"));
			mfParms[0] = m_abri.getABRCode();
			mfParms[1] = exc.getMessage();
			rpt.append(mfOut.format(mfParms));
			exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			rpt.append("<pre>");
			rpt.append(exBuf.getBuffer().toString());
			rpt.append("</pre>");
		} finally {
			setDGTitle(navName.toString());
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass("CTYABR");
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
		}
		// Insert Header into beginning of report
		navName.append((getReturnCode() == PASS) ? " Passed" : " Failed");
		mfOut = new MessageFormat(msgs.getString("HEADER"));
		mfParms[0] = getShortClassName(getClass());
		mfParms[1] = navName.toString();
		mfParms[2] = getNow();
		mfParms[3] = m_prof.getOPName();
		mfParms[4] = m_prof.getRoleDescription();
		mfParms[5] = getDescription();
		mfParms[6] = getABRVersion();
		rpt.insert(0, mfOut.format(mfParms));
		println(rpt.toString()); // Output the Report
		printDGSubmitString();
		buildReportFooter();
	}

	/**
	 *  Get ABR description
	 *
	 *@return    java.lang.String
	 */
	public String getDescription() {
		return msgs.getString("DESCRIPTION");
	}

	/**
	 *  Get the version
	 *
	 *@return    java.lang.String
	 */
	public String getABRVersion() {
		return "$Revision: 1.22 $";
	}
	/**
	 *  Sets the specified Flag Attribute on the Root Entity
	 *
	 *@param    strAttributeCode The Flag Attribute Code
	 *@param    strAttributeValue The Flag Attribute Value
	 */
	private void setFlagValue(
		String strAttributeCode,
		String strAttributeValue) {
		logMessage("****** strAttributeValue set to: " + strAttributeValue);

		if (strAttributeValue != null) {
			try {
				EntityItem eiParm =
					new EntityItem(
						null,
						m_prof,
						getEntityType(),
						getEntityID());
				ReturnEntityKey rek =
					new ReturnEntityKey(
						eiParm.getEntityType(),
						eiParm.getEntityID(),
						true);

				SingleFlag sf =
					new SingleFlag(
						m_prof.getEnterprise(),
						rek.getEntityType(),
						rek.getEntityID(),
						strAttributeCode,
						strAttributeValue,
						1,
						m_cbOn);
				Vector vctAtts = new Vector();
				Vector vctReturnsEntityKeys = new Vector();

				if (sf != null) {
					vctAtts.addElement(sf);

					rek.m_vctAttributes = vctAtts;
					vctReturnsEntityKeys.addElement(rek);

					m_db.update(m_prof, vctReturnsEntityKeys, false, false);
					m_db.commit();
				}
			} catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
				logMessage("setFlagValue: " + e.getMessage());
			} catch (Exception e) {
				logMessage("setFlagValue: " + e.getMessage());
			}
		}
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
	 *  Insert the method's description here. Creation date: (8/1/2001 8:23:46 AM)
	 *
	 *@param  _strEntityType  Description of the Parameter
	 *@param  _iEntityID      Description of the Parameter
	 *@param  _iLevel         Description of the Parameter
	 */
	public void printEntity(
		String _strEntityType,
		int _iEntityID,
		int _iLevel) {
		
		String strPSGClass = null;
		
		logMessage(
			"In printEntity _strEntityType"
				+ _strEntityType
				+ ":_iEntityID:"
				+ _iEntityID
				+ ":_iLevel:"
				+ _iLevel);

		strPSGClass = "";
		switch (_iLevel) {
		case 0 :
			{
				strPSGClass = "PsgReportSection";
				break;
			}
		case 1 :
			{
				strPSGClass = "PsgReportSectionII";
				break;
			}
		case 2 :
			{
				strPSGClass = "PsgReportSectionIII";
				break;
			}
		case 3 :
			{
				strPSGClass = "PsgReportSectionIV";
				break;
			}
		default :
			{
				strPSGClass = "PsgReportSectionV";
				break;
			}
		}
		logMessage("Printing table width");
		rpt.append(
			"<table width=\"100%\"><tr><td class=\""
				+ strPSGClass
				+ "\">"
				+ getEntityDescription(_strEntityType)
				+ ": "
				+ getAttributeValue(
					_strEntityType,
					_iEntityID,
					"NAME",
					DEF_NOT_POPULATED_HTML)
				+ "</td></tr></table>");
		logMessage("Printing Attributes");
		printAttributes(_strEntityType, _iEntityID, false, false);
	}
	/**
	 *  Insert the method's description here. Creation date: (8/1/2001 8:23:46 AM)
	 *
	 *@param  _strEntityType  Description of the Parameter
	 *@param  _iEntityID      Description of the Parameter
	 *@param  _bAll           Description of the Parameter
	 *@param  _bShortDesc     Description of the Parameter
	 */
	public void printAttributes(
		String _strEntityType,
		int _iEntityID,
		boolean _bAll,
		boolean _bShortDesc) {
		printAttributes(
			m_elist,
			_strEntityType,
			_iEntityID,
			_bAll,
			_bShortDesc);
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _elist          Description of the Parameter
	 *@param  _strEntityType  Description of the Parameter
	 *@param  _iEntityID      Description of the Parameter
	 *@param  _bAll           Description of the Parameter
	 *@param  _bShortDesc     Description of the Parameter
	 */
	public void printAttributes(
		EntityList _elist,
		String _strEntityType,
		int _iEntityID,
		boolean _bAll,
		boolean _bShortDesc) {
		//Get list of attributes for entity
		EntityItem entItem = null;
		EntityGroup eg = null;
		Hashtable htValues;
		Vector vctTmp;
		String[] astrCodeDesc = null;
		String streDesc = null;
		String strValue = null;
		String strDesc1 = null;
		int nRows = 0;
		int col2Index = 0;
		SortUtil sort = null;
		logMessage(
			"in Print Attributes _strEntityType"
				+ _strEntityType
				+ ":_iEntityID:"
				+ _iEntityID);

		if (_strEntityType.equals(getEntityType())) {
			//get the parent entity group for root entity
			eg = _elist.getParentEntityGroup();
		} else {
			eg = _elist.getEntityGroup(_strEntityType);
		}

		if (eg == null) {
			rpt.append(
				"<h3>Warning: Cannot locate an EnityGroup for "
					+ _strEntityType
					+ " so no attributes will be printed.</h3>");
			return;
		}

		entItem = eg.getEntityItem(_strEntityType, _iEntityID);
		//EntityItem entItem = entGroup.getEntityItem(_strEntityType,_iEntityID);
		if (entItem == null) {
			// Lets display the EntityType, entityId in the root..)
			entItem = eg.getEntityItem(0);
			rpt.append(
				"<h3>Warning: Attributes for "
					+ _strEntityType
					+ ":"
					+ _iEntityID
					+ " cannot be printed as it is not available in the Extract.</h3>");
			rpt.append(
				"<h3>Warning: Root Entityis "
					+ getEntityType()
					+ ":"
					+ getEntityID()
					+ ".</h3>");
			return;
		}

		streDesc = eg.getLongDescription();
		logMessage("Print Attributes Entity desc is " + streDesc);
		logMessage("Attribute count is" + entItem.getAttributeCount());
		htValues = new Hashtable();
		vctTmp = new Vector();
		for (int i = 0; i < entItem.getAttributeCount(); i++) {

			EANAttribute EANatt = entItem.getAttribute(i);
			logMessage("printAttributes " + EANatt.dump(false));
			logMessage("printAttributes " + EANatt.dump(true));

			strValue =
				getAttributeValue(
					_strEntityType,
					_iEntityID,
					EANatt.getAttributeCode(),
					DEF_NOT_POPULATED_HTML);
			strDesc1 = "";

			// Use short description or long description?
			if (_bShortDesc) {
				strDesc1 =
					getMetaDescription(
						_strEntityType,
						EANatt.getAttributeCode(),
						false);
			} else {
				strDesc1 =
					getAttributeDescription(
						_strEntityType,
						EANatt.getAttributeCode());
			}
			// Strip entity description from beginning of attribute description
			if (strDesc1.length() > streDesc.length()
				&& strDesc1.substring(0, streDesc.length()).equalsIgnoreCase(
					streDesc)) {
				strDesc1 = strDesc1.substring(streDesc.length());
			}
			// Did we only want populated attributes?
			if (_bAll || strValue != null) {
				// associate truncated description with its value
				htValues.put(strDesc1, strValue);
				// keep track of attributes to display
				vctTmp.add(strDesc1);
			}
		}
		astrCodeDesc = new String[entItem.getAttributeCount()];

		if (!_bAll) {
			astrCodeDesc = new String[vctTmp.size()];
			for (int i = 0; i < astrCodeDesc.length; i++) {
				astrCodeDesc[i] = (String) vctTmp.elementAt(i);
			}
		}

		// Sort on attribute code description
		sort = new SortUtil();
		sort.sort(astrCodeDesc);

		// Output attributes in a two column tale
		rpt.append("<table width=\"100%\">");
		nRows = astrCodeDesc.length - (astrCodeDesc.length / 2);
		for (int i = 0; i < nRows; i++) {
			rpt.append(
				"<tr><td class=\"PsgLabel\" valign=\"top\">"
					+ astrCodeDesc[i]
					+ "</td><td class=\"PsgText\" valign=\"top\">"
					+ htValues.get(astrCodeDesc[i])
					+ "</td>");
			col2Index = nRows + i;
			if (col2Index < astrCodeDesc.length) {
				rpt.append(
					"<td class=\"PsgLabel\" valign=\"top\">"
						+ astrCodeDesc[col2Index]
						+ "</td><td class=\"PsgText\" valign=\"top\">"
						+ htValues.get(astrCodeDesc[col2Index])
						+ "</td><tr>");
			} else {
				rpt.append(
					"<td class=\"PsgLabel\">"
						+ "</td><td class=\"PsgText\">"
						+ "</td><tr>");
			}
		}
		rpt.append("</table>\n<br />");
	}
	/**
	 *  Get the meta attribute or task description, null if not found
	 *
	 *@param  _strEtype     Description of the Parameter
	 *@param  _strAttrCode  Description of the Parameter
	 *@param  _bLongDesc    Description of the Parameter
	 *@return               The metaDescription value
	 *@returns              String attribute description
	 */

	private String getMetaDescription(
		String _strEtype,
		String _strAttrCode,
		boolean _bLongDesc) {
		return getMetaDescription(m_elist, _strEtype, _strAttrCode, _bLongDesc);
	}

	/**
	 *  Gets the metaDescription attribute of the PokBaseABR object
	 *
	 *@param  _elist        Description of the Parameter
	 *@param  _strEtype     Description of the Parameter
	 *@param  _strAttrCode  Description of the Parameter
	 *@param  _bLongDesc    Description of the Parameter
	 *@return               The metaDescription value
	 */
	private String getMetaDescription(
		EntityList _elist,
		String _strEtype,
		String _strAttrCode,
		boolean _bLongDesc) {
		EANMetaAttribute ema = null;
		String desc = null;
		EntityGroup entGroup = _elist.getEntityGroup(_strEtype);
		if (entGroup == null) {
			logError(
				"Did not find EntityGroup: "
					+ _strEtype
					+ " in entity list to extract getMetaDescription");
			return null;
		}

		ema = null;
		if (entGroup != null) {
			ema = entGroup.getMetaAttribute(_strAttrCode);
		}
		desc = null;
		if (ema != null) {
			if (_bLongDesc) {
				desc = ema.getLongDescription();
			} else {
				desc = ema.getShortDescription();
			}
		}
		return desc;
	}
}
