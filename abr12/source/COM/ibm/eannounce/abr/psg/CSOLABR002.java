/**
 * <pre>
 * (c) Copyright International Business Machines Corporation, 2003
 * All Rights Reserved.
 *
 * $Log: CSOLABR002.java,v $
 * Revision 1.18  2006/01/24 22:13:26  yang
 * Jtest changes
 *
 * Revision 1.17  2005/01/31 16:30:06  joan
 * make changes for Jtest
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
 * Revision 1.9  2003/10/08 11:41:06  cstolpe
 * Fix related to FB 52441
 *
 * Revision 1.8  2003/09/25 18:24:22  cstolpe
 * Last minute spec change
 *
 * Revision 1.7  2003/09/22 14:56:20  cstolpe
 * Fix for FB52241 Family and Series association name changed
 *
 * Revision 1.6  2003/09/11 21:44:25  cstolpe
 * Latest Updates
 *
 * Revision 1.5  2003/07/31 16:42:12  cstolpe
 * Remove java code to strip Revision. Done by build now.
 *
 * Revision 1.4  2003/07/28 15:34:42  cstolpe
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
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public final class CSOLABR002 extends PokBaseABR {
	private MessageFormat mfOut = null;
	private Object[] mfParms = new String[10];
	private ResourceBundle msgs = null;
	private StringBuffer rpt = new StringBuffer();
	/**
	 *  Execute ABR.
	 *
	 */
	public void execute_run() {
		EntityGroup eg;
		Iterator itMeta;
		Iterator itEI;
		Iterator ids;
		Vector vctOF;
		boolean bCSOLHasKB;
		boolean bOFHasKB = false;
		String strCSOLStatus = null;
		
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

			// Print all OF parents
			vctOF =
				getParentEntityIds(
					getRootEntityType(),
					getRootEntityID(),
					"OF",
					"OFCSOL");
			ids = vctOF.iterator();
			while (ids.hasNext()) {
				Integer id = (Integer) ids.next();
				printEntity("OF", id.intValue(), 0);
			}

			// Print CSOL
			printEntity(getRootEntityType(), getRootEntityID(), 0);

			setReturnCode(PASS);

			// Check 1
			bCSOLHasKB =
				getChildrenEntityIds(
					getRootEntityType(),
					getRootEntityID(),
					"KB",
					"CSOLKB")
            .size()
			> 0;
			if (!bCSOLHasKB) {
				// No KB directly attached does it have on indirectly via a SBB?
				Vector vSBB =
					getChildrenEntityIds(
						getRootEntityType(),
						getRootEntityID(),
						"SBB",
						"CSOLSBB");
				Iterator itSBB = vSBB.iterator();
				while (itSBB.hasNext()) {
					Integer iSBB = (Integer) itSBB.next();
					Vector vKB =
						getChildrenEntityIds(
							"SBB",
							iSBB.intValue(),
							"KB",
							"SBBKB");
					bCSOLHasKB = vKB.size() > 0;
					if (bCSOLHasKB) {
						break;
					}
				}
			}
			bOFHasKB = false;
			if (!bCSOLHasKB) {
				//This business rule passed. The rule only applies to country solutions with key boards
				rpt.append(msgs.getString("CHECK2_PASS_MSG"));
			} else {
				Vector vOF =
					getParentEntityIds(
						getRootEntityType(),
						getRootEntityID(),
						"OF",
						"OFCSOL");
				Iterator itOF = vOF.iterator();
				while (itOF.hasNext()) {
					Integer iOF = (Integer) itOF.next();
					bOFHasKB =
						getChildrenEntityIds(
							"OF",
							iOF.intValue(),
							"KB",
							"OFKB")
                    .size()
					> 0;
					if (!bOFHasKB) {
						// No KB directly attached does it have on indirectly via a SBB?
						Vector vSBB =
							getChildrenEntityIds(
								"OF",
								iOF.intValue(),
								"SBB",
								"OFSBB");
						Iterator itSBB = vSBB.iterator();
						while (itSBB.hasNext()) {
							Integer iSBB = (Integer) itSBB.next();
							Vector vKB =
								getChildrenEntityIds(
									"SBB",
									iSBB.intValue(),
									"KB",
									"SBBKB");
							bOFHasKB = vKB.size() > 0;
							if (bOFHasKB) {
								break;
							}
						}
					} else {
						break; // found a KB on an OF. Now continue
					}
				}
				if (bOFHasKB) {
					rpt.append(msgs.getString("CHECK2_PASS_MSG"));
				} else {
					rpt.append(msgs.getString("CHECK2_FAIL_MSG"));
					setReturnCode(FAIL);
				}
			}

			// Check 2
			strCSOLStatus =
				getAttributeFlagEnabledValue(
					getRootEntityType(),
					getRootEntityID(),
					"CSOLSTATUS",
					"");
			if (getReturnCode() == PASS) {
				//Get parent OF of CSOL (only check first one returned by extract)
				int iOF =
					getParentEntityId(
						getRootEntityType(),
						getRootEntityID(),
						"OF",
						"OFCSOL");
				String strOFStatus =
					getAttributeFlagEnabledValue("OF", iOF, "STATUS", "");
				if (null == strCSOLStatus || null == strOFStatus) {
					setReturnCode(FAIL);
					rpt.append(msgs.getString("STATUS_ERROR"));
				}
				if ("0010".equals(strCSOLStatus)
					|| "0050".equals(strCSOLStatus)) { // Draft or Change Request
					if ("0020".equals(strOFStatus)
						|| "0040".equals(
							strOFStatus)) { // Final or Ready For Review
						setControlBlock();
						setFlagValue("CSOLSTATUS", "0040"); // Ready for review
					} else {
						rpt.append(msgs.getString("CHECK1_FAIL_MSG"));
						setReturnCode(FAIL);
					}
				} else if ("0020".equals(strCSOLStatus)) { // Final
					setControlBlock();
					setFlagValue("CSOLSTATUS", "0050"); // Change Request
					setReturnCode(PASS);
					rpt.append(msgs.getString("STD_PASS_MSG"));
				} else if ("0040".equals(strCSOLStatus)) { // Ready for review
					if ("0020".equals(strOFStatus)) {
						//rpt.append(msgs.getString("CHECK1_PASS_MSG"));
						setControlBlock();
						setFlagValue("CSOLSTATUS", "0020"); // Final
					} else {
						rpt.append(msgs.getString("CHECK1_FAIL_MSG"));
						setReturnCode(FAIL);
					}
				}
			} else {
				rpt.append(msgs.getString("SKIP_MSG"));
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
			java.io.StringWriter exBuf = new java.io.StringWriter();
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
		return "$Revision: 1.18 $";
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
		Vector vctTmp;
		SortUtil sort = null;
		int col2Index = 0;
		int nRows = 0;
		Hashtable htValues;
		String[] astrCodeDesc = null;
		String streDesc = null;
		String strValue = null;
		String strDesc1 = null;

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
