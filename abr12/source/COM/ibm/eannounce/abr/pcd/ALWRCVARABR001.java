//(c) Copyright International Business Machines Corporation, 2001
//All Rights Reserved.</pre>

//$Log: ALWRCVARABR001.java,v $
//Revision 1.28  2010/07/12 21:35:09  wendy
//BH SR87, SR655 - extended combounique rule
//
//Revision 1.27  2008/01/30 19:27:19  wendy
//Cleanup RSA warnings

//Revision 1.26  2006/03/03 19:24:11  bala
//remove reference to Constants.CSS

//Revision 1.25  2006/01/25 17:45:03  yang
//Jtest changes

//Revision 1.24  2005/04/28 21:29:49  joan
//fixes

//Revision 1.23  2005/01/31 16:30:05  joan
//make changes for Jtest

//Revision 1.22  2005/01/27 16:39:54  joan
//changes for Jtest

//Revision 1.21  2004/12/13 21:02:47  joan
//fix for part number

//Revision 1.20  2004/03/02 18:02:58  joan
//fix bug

//Revision 1.19  2004/03/02 16:59:09  joan
//fix pull report

//Revision 1.18  2003/12/16 21:32:49  joan
//fb fix

//Revision 1.17  2003/11/13 20:53:41  joan
//work on CR

//Revision 1.16  2003/11/13 17:38:41  joan
//work on CR

//Revision 1.15  2003/10/29 15:41:00  joan
//fb fixes

//Revision 1.14  2003/10/23 17:10:06  joan
//adjust for rptclass

//Revision 1.13  2003/10/21 21:32:04  joan
//fix name

//Revision 1.12  2003/10/21 17:33:09  joan
//fix dgentity name

//Revision 1.11  2003/10/20 17:43:11  joan
//fb fixes

//Revision 1.10  2003/10/17 21:20:53  joan
//fb fixes

//Revision 1.9  2003/10/07 23:25:37  joan
//change for subs.

//Revision 1.8  2003/09/23 22:58:19  joan
//add changes

//Revision 1.7  2003/09/18 16:29:57  joan
//fix error

//Revision 1.6  2003/09/11 22:24:08  joan
//fb fixes

//Revision 1.5  2003/09/10 18:10:53  joan
//fix fb

//Revision 1.4  2003/08/27 15:59:05  joan
//add log

//Revision 1.3  2003/08/21 21:29:34  joan
//fix error

//Revision 1.2  2003/08/14 21:48:35  joan
//fix report

//Revision 1.1  2003/08/13 23:01:38  joan
//initial load


package COM.ibm.eannounce.abr.pcd;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
//import java.io.*;

/**
 * ALWRCVARABR001
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class ALWRCVARABR001 extends PokBaseABR {
	/**
	 *  Execute ABR.
	 *
	 */

	// Class constants
	public final static String ABR = new String("ALWRCVARABR001");
	/**
	 * FINAL
	 *
	 */
	public final static String FINAL = new String("0020");
	/**
	 * REVIEW
	 *
	 */
	public final static String REVIEW = new String("0040");

	private EntityGroup m_egParent = null;
	private EntityItem m_ei = null;
	//private final EntityItem m_eiVAR = null;

	/**
	 * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
	 * @author Administrator
	 */
	public void execute_run() {
		EntityGroup eg;
		EANList varList;
		EANList prList;
		EntityGroup egPR;
		EANList brList;
		EntityGroup egBR;
		EntityItem eiCDG;
		String strStatus;
		ALWRCVARABR001PDG pdg;
		StringBuffer sb;
		EANList eiList;
		ExtractActionItem xai;
		Vector vPartNo;
		try {
			start_ABRBuild();

			// Build the report header
			buildReportHeaderII();

			m_egParent = m_elist.getParentEntityGroup();
			m_ei = m_egParent.getEntityItem(0);
			println("<br><b>Country Variant: " + m_ei.getKey() + "</b>");

			printNavigateAttributes(m_ei, m_egParent, true);

			setReturnCode(PASS);

			//print General Area (Region)
			println(
					"<br/><b>General Area (Region) = </b>"
					+ getAttributeValue(
							m_elist,
							m_ei.getEntityType(),
							m_ei.getEntityID(),
							"GENAREANAMEREGION"));

			//==== check VAR entities =================
			log("ALWRCVARABR001 checking VAR");
			eg = m_elist.getEntityGroup("VAR");

			if (eg != null) {
				Vector vVAR =
					getParentEntityIds(
							m_ei.getEntityType(),
							m_ei.getEntityID(),
							"VAR",
							"VARCVAR");
				if (vVAR.size() <= 0) {
					println("<br /><font color=red>Failed. There is no VAR</font>");
					setReturnCode(FAIL);
				} else if (vVAR.size() > 1) {
					println("<br /><font color=red>Failed. There is not one and only one VAR.</font>");
					setReturnCode(FAIL);
				}

				//print Variant
				varList = new EANList();
				println("<br/></br/><b>Variant(s): </b>");
				for (int i = 0; i < vVAR.size(); i++) {
					int iID = ((Integer) vVAR.elementAt(i)).intValue();
					EntityItem eiVAR = eg.getEntityItem("VAR" + iID);
					println("</br/><LI>" + eiVAR.toString());
					varList.put(eiVAR);
				}

				//print Project
				prList = new EANList();
				egPR = m_elist.getEntityGroup("PR");
				println("<br/><br /><b>Project(s):</b>");
				for (int i = 0; i < varList.size(); i++) {
					EntityItem eiVAR = (EntityItem) varList.getAt(i);
					Vector vPR =
						getParentEntityIds(
								eiVAR.getEntityType(),
								eiVAR.getEntityID(),
								"PR",
								"PRVAR");
					for (int j = 0; j < vPR.size(); j++) {
						EntityItem eiPR =
							egPR.getEntityItem(
									egPR.getEntityType() + vPR.elementAt(j));
						if (prList.get(eiPR.getKey()) == null) {
							println("<br /><LI>" + eiPR.toString());
							prList.put(eiPR);
						}
					}
				}

				//print Brand
				brList = new EANList();
				egBR = m_elist.getEntityGroup("BR");
				println("<br/><br /><b>Brand(s):</b>");
				for (int i = 0; i < prList.size(); i++) {
					EntityItem eiPR = (EntityItem) prList.getAt(i);
					Vector vBR =
						getChildrenEntityIds(
								eiPR.getEntityType(),
								eiPR.getEntityID(),
								"BR",
								"PRBRANDCODEA");
					for (int k = 0; k < vBR.size(); k++) {
						EntityItem eiBR =
							egBR.getEntityItem(
									egBR.getEntityType() + vBR.elementAt(k));
						if (brList.get(eiBR.getKey()) == null) {
							println("<br /><LI>" + eiBR.toString());
							brList.put(eiBR);
						}
					}
				}
			} else {
				println("EntityGroup VAR is null\n");
				setReturnCode(FAIL);
			}

			//==== check CDG entities =================
			log("ALWRCVARABR001 checking CDG");
			eg = m_elist.getEntityGroup("CDG");
			eiCDG = null;
			if (eg != null) {
				Vector vCDG =
					getChildrenEntityIds(
							m_ei.getEntityType(),
							m_ei.getEntityID(),
							"CDG",
							"CVARCDG");
				if (vCDG.size() <= 0) {
					println("<br /><font color=red>Failed. There is no CDG</font>");
					setReturnCode(FAIL);
				} else if (vCDG.size() > 1) {
					println("<br /><font color=red>Failed. There is not one and only one CDG.</font>");
					setReturnCode(FAIL);
				} else {
					int iID = ((Integer) vCDG.elementAt(0)).intValue();
					eiCDG = eg.getEntityItem("CDG" + iID);
				}

				println("<br/></br/><b>Country Designator Group(s):</b>");
				for (int i = 0; i < vCDG.size(); i++) {
					int iID = ((Integer) vCDG.elementAt(i)).intValue();
					EntityItem ei = eg.getEntityItem("CDG" + iID);
					println("</br/><LI> " + ei.toString());
				}
			} else {
				println("EntityGroup CDG is null\n");
				setReturnCode(FAIL);
			}

			//=============check CD entities ==========================================
			log("ALWRCVARABR001 checking CD");
			if (eiCDG != null) {
				EntityGroup CDGroup = m_elist.getEntityGroup("CD");
				Vector vCD =
					getChildrenEntityIds(
							eiCDG.getEntityType(),
							eiCDG.getEntityID(),
							"CD",
							"CDGCD");
				if (vCD.size() <= 0) {
					println("</br/><font color=red>Failed. No CD exists in the CDG.</font>");
					println("</br/>" + eiCDG.toString());
					setReturnCode(FAIL);
				} else {
					for (int i = 0; i < vCD.size(); i++) {
						int iID = ((Integer) vCD.elementAt(i)).intValue();
						EntityItem ei = CDGroup.getEntityItem("CD" + iID);
						String strCD =
							getAttributeValue(
									m_elist,
									ei.getEntityType(),
									ei.getEntityID(),
									"COUNTRY_DESIG");
						if (strCD == null || strCD.length() <= 0) {
							println("</br/><font color=red>Failed. The attribute COUNTRY_DESIG is not populated.</font>");
							println("</br/>" + ei.toString());
							setReturnCode(FAIL);
						}
					}
				}
			}

			//=====For the source CVAR, check that STATUS_CVAR is equal to ‘Ready for Review’ or ‘Final’==
			strStatus =
				getAttributeFlagEnabledValue(
						m_elist,
						m_ei.getEntityType(),
						m_ei.getEntityID(),
						"STATUS_CVAR")
						.trim();
			if (!(strStatus.equals(FINAL) || strStatus.equals(REVIEW))) {
				println("</br/><font color=red>Failed. the CVAR is not in Ready for Review or Final status. </font>");
				setReturnCode(FAIL);
			}

			//============= run the PDG to generate data ==========================================
			if (getReturnCode() == PASS) {
				log("ALWRCVARABR001 generating data");
				pdg =
					new ALWRCVARABR001PDG(
							null,
							m_db,
							m_prof,
							"ALWRCVARABR001PDG");
				pdg.setEntityItem(m_ei);
				pdg.setABReList(m_elist);
				pdg.executeAction(m_db, m_prof);
				sb = pdg.getActivities();
				println("</br></br/><b>Generated Data:</b>");
				println("<br/>" + sb.toString());
				log("ALWRCVARABR001 finish generating data");

				// display result

				println("</br><br/><b>Relators: </b>");
				eiList = pdg.getSavedEIList();
				xai =
					new ExtractActionItem(
							null,
							m_db,
							m_prof,
							m_abri.getVEName());
				for (int i = 0; i < eiList.size(); i++) {
					EntityItem ei = (EntityItem) eiList.getAt(i);
					println(pullResultReport(ei, xai));
				}

				// clean up PartNo saved temporarily in TRSPARTNO table
				vPartNo = new Vector();
				for (int i = 0; i < eiList.size(); i++) {
					EntityItem ei = (EntityItem) eiList.getAt(i);

					for (int j = 0; j < ei.getAttributeCount(); j++) {
						EANAttribute att = ei.getAttribute(j);
						EANMetaAttribute meta = att.getMetaAttribute();
						if (att instanceof TextAttribute) {
							if (meta.isUnique()) {
								if (meta.getUniqueClass().equals("LEVEL1")) {
									vPartNo.addElement(
											att.toString() + meta.getKey());
								} else if (
										meta.getUniqueClass().equals("LEVEL2")) {
									vPartNo.addElement(
											att.toString()
											+ meta.getKey()
											+ ei.getEntityType());
								}
							} else if (meta.isComboUnique()) {
								Vector v = meta.getComboUniqueAttributeCode();

								for (int k = 0; k < v.size(); k++) {
									String strFlagAttributeCode = meta.getComboUniqueAttributeCode(k);
									EANAttribute att2 = ei.getAttribute(strFlagAttributeCode);
									if (att2 instanceof EANFlagAttribute) {
										String strFlagAttributeValue = ((EANFlagAttribute)att2).getFlagCodes();

										// Here .. we have to parse out all the flag codes ...
										StringTokenizer st = new StringTokenizer(strFlagAttributeValue,":");

										while (st.hasMoreTokens()) {
											String strFlagCode = st.nextToken();
											vPartNo.addElement(
													att.toString()
													+ ":"+ meta.getKey()
													+ ":"+ strFlagCode
													+ ":"+ strFlagAttributeCode);
										}
									}else if (att2 instanceof EANTextAttribute){
										vPartNo.addElement(
												att.toString()
												+ ":"+ meta.getKey()
												+ ":"+ att2.toString()
												+ ":"+ strFlagAttributeCode);
									}
								}
							}
						} else if (att instanceof SingleFlagAttribute) {
							EANFlagAttribute flagAtt = (EANFlagAttribute) att;
							if (meta.isUnique()) {
								if (meta.getUniqueClass().equals("EACHECK")) {
									vPartNo.addElement(
											att.toString()
											+ meta.getKey()
											+ ei.getEntityType());
								}
							} else if (meta.isComboUnique()) {
								Vector v = meta.getComboUniqueAttributeCode();
								for (int k = 0; k < v.size(); k++) {
									String strTextAttributeCode =  meta.getComboUniqueAttributeCode(k);
									EANAttribute att2 = ei.getAttribute(strTextAttributeCode);
									if (att2 instanceof EANTextAttribute) {
										String strTextAttributeValue =  att2.toString();
										vPartNo.addElement(
												flagAtt.getFlagCodes()
												+ ":" + meta.getKey()
												+ ":" + strTextAttributeValue
												+ ":" + strTextAttributeCode);
									}else if (att2 instanceof EANFlagAttribute){
										String strFlagAttributeValue =((EANFlagAttribute)att2).getFlagCodes();

										// Here .. we have to parse out all the flag codes ...
										StringTokenizer st =new StringTokenizer(strFlagAttributeValue,":");

										while (st.hasMoreTokens()) {
											String strFlagCode = st.nextToken();
											vPartNo.addElement(
													att.toString()
													+ ":"+ meta.getKey()
													+ ":"+ strFlagCode
													+ ":"+ strTextAttributeCode);
										}
									}
								}
							}
						} else if (att instanceof MultiFlagAttribute) {
							EANFlagAttribute flagAtt = (EANFlagAttribute) att;
							if (meta.isComboUnique()) {
								Vector v = meta.getComboUniqueAttributeCode();
								for (int k = 0; k < v.size(); k++) {
									String strTextAttributeCode = meta.getComboUniqueAttributeCode(k);
									EANAttribute att2 = ei.getAttribute(strTextAttributeCode);
									if (att2 instanceof EANTextAttribute) {
										String strTextAttributeValue = att2.toString();
										vPartNo.addElement(
												flagAtt.getFlagCodes()
												+ ":" + meta.getKey()
												+ ":" + strTextAttributeValue
												+ ":" + strTextAttributeCode);
									}else if (att2 instanceof EANFlagAttribute){
										String strFlagAttributeValue =((EANFlagAttribute)att2).getFlagCodes();

										// Here .. we have to parse out all the flag codes ...
										StringTokenizer st =new StringTokenizer(strFlagAttributeValue,":");

										while (st.hasMoreTokens()) {
											String strFlagCode = st.nextToken();
											vPartNo.addElement(
													att.toString()
													+ ":"+ meta.getKey()
													+ ":"+ strFlagCode
													+ ":"+ strTextAttributeCode);
										}
									}
								}
							}
						}
					}
				}

				for (int i = 0; i < vPartNo.size(); i++) {
					String strPartNo = (String) vPartNo.elementAt(i);
					PartNo.remove(m_db, strPartNo);
				}

			}

			println(
					"<br /><br /><b>"
					+ buildMessage(
							MSG_IAB2016I,
							new String[] {
									getABRDescription(),
									(getReturnCode() == PASS ? "Passed" : "Failed")})
									+ "</b>");

			log(
					buildLogMessage(
							MSG_IAB2016I,
							new String[] {
									getABRDescription(),
									(getReturnCode() == PASS ? "Passed" : "Failed")}));
		} catch (LockPDHEntityException le) {
			setReturnCode(UPDATE_ERROR);
			println(
					"<h3><font color=red>"
					+ ERR_IAB1007E
					+ "<br />"
					+ le.getMessage()
					+ "</font></h3>");
			logError(le.getMessage());
		} catch (UpdatePDHEntityException le) {
			setReturnCode(UPDATE_ERROR);
			println(
					"<h3><font color=red>UpdatePDH error: "
					+ le.getMessage()
					+ "</font></h3>");
			logError(le.getMessage());
		} catch (SBRException _sbrex) {
			setReturnCode(UPDATE_ERROR);
			println(
					"<h3><font color=red>Generate Data error: "
					+ _sbrex.toString()
					+ "</font></h3>");
			logError(_sbrex.toString());
		} catch (Exception exc) {
			// Report this error to both the datbase log and the PrintWriter
			println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
			println("" + exc);
			exc.printStackTrace();
			// don't overwrite an update exception
			if (getABRReturnCode() != UPDATE_ERROR) {
				setReturnCode(INTERNAL_ERROR);
			}
		} finally {
			String strNavName =
				getABREntityDesc(m_ei.getEntityType(), m_ei.getEntityID());
			String strABRAttrDesc = getMetaAttributeDescription(m_ei, ABR);

			String strDgName = strABRAttrDesc + " for " + strNavName;
			if (strDgName.length() > 64) {
				strDgName = strDgName.substring(0, 64);
			}

			setDGTitle(strDgName);

			printALWRInfo();

			// set DG submit string
			setDGString(getABRReturnCode());
			setDGRptName(ABR); //Set the report name
			setDGRptClass("ALWRCVAR");
			printDGSubmitString();
			//Stuff into report for subscription and notification

			// Tack on the DGString
			buildReportFooter();
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
		}
	}

	/**
	 *  Get the entity description to use in error messages
	 *
	 *@return             String
	 * @param _iEntityId
	 * @param _strEntityType
	 */
	protected String getABREntityDesc(String _strEntityType, int _iEntityId) {
		return getAttributeValue(
				m_elist,
				_strEntityType,
				_iEntityId,
				"PNUMB_CT")
				+ ", "
				+ getAttributeValue(
						m_elist,
						_strEntityType,
						_iEntityId,
				"GENAREANAMEREGION")
				+ ", "
				+ getAttributeValue(
						m_elist,
						_strEntityType,
						_iEntityId,
				"GENAREANAME")
				+ ", "
				+ getAttributeValue(
						m_elist,
						_strEntityType,
						_iEntityId,
				"TARGANNDATE_CVAR")
				+ ", "
				+ getAttributeValue(m_elist, _strEntityType, _iEntityId, "NAME");
	}

	/**
	 *  Get ABR description
	 *
	 *@return    java.lang.String
	 */
	public String getDescription() {
		return "The purpose of this ABR is to create one or more Country Variant Solutions based on an existing Country Variant Solution (CVAR).  A CVAR is generated for each Country Designator (CD)/Country combination in the related Country Designator Group (CDG) and the part number is determined by the CD.";
	}

	/**
	 *  Get any style that should be used for this page. Derived classes can
	 *  override this to set styles They must include the <style>...</style> tags
	 *
	 *@return    String
	 */
	protected String getStyle() {
		// Print out the PSG stylesheet
		return "";
	}

	/**
	 * getRevision
	 *
	 * @return
	 * @author Administrator
	 */
	public String getRevision() {
		return new String("$Revision: 1.28 $");
	}

	/**
	 * getVersion
	 *
	 * @return
	 * @author Administrator
	 */
	public static String getVersion() {
		return ("$Id: ALWRCVARABR001.java,v 1.28 2010/07/12 21:35:09 wendy Exp $");
	}

	/**
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 * @author Administrator
	 */
	public String getABRVersion() {
		return ("ALWRCVARABR001.java,v 1.2");
	}

	private void printALWRInfo() {
		String strNavName =
			getABREntityDesc(m_ei.getEntityType(), m_ei.getEntityID());
		String strABRAttrDesc = getMetaAttributeDescription(m_ei, ABR);

		//NAME = ALWR for (Entity Description of selected CSOL): Navigation Display Name of Selected CSOL | Return Code | ALWRSTATUS
		println("<br /><b>NAME = </b>" + strABRAttrDesc + " for " + strNavName);
		//RPTNAME = ALWRCVARABR001
		println("<br /><b> RPTNAME = </b>" + ABR);
		//TASKSTATUS = Return Code
		println(
				"<br /><b> TASKSTATUS = </b>"
				+ (getReturnCode() == PASS ? "Passed" : "Failed"));
		//PDHDOMAIN = from profile
		println(
				"<br/><b>PDH Domain = </b>"
				+ getAttributeValue(
						m_elist,
						m_ei.getEntityType(),
						m_ei.getEntityID(),
						"PDHDOMAIN"));
	}

	private String pullResultReport(EntityItem _ei, ExtractActionItem _xai) {
		StringBuffer sb;
		EntityList elist;
		EntityGroup eg;
		log("ALWRCSOLABR001 pullResultReport");

		sb = new StringBuffer();
		try {
			m_prof = refreshProfValOnEffOn(m_prof);
			EntityItem[] aei = { _ei };
			elist =
				EntityList.getEntityList(m_db, m_prof, _xai, aei);

			eg = elist.getParentEntityGroup();
			_ei = eg.getEntityItem(_ei.getKey());

			for (int u = 0; u < _ei.getUpLinkCount(); u++) {
				EntityItem eir = (EntityItem) _ei.getUpLink(u);
				EntityItem eip = (EntityItem) eir.getUpLink(0);
				EntityItem eic = (EntityItem) eir.getDownLink(0);
				sb.append(
						"<br />Relator: "
						+ eir.getKey()
						+ ", Parent: "
						+ eip.getKey()
						+ ", Child: "
						+ eic.getKey());
			}

			for (int d = 0; d < _ei.getDownLinkCount(); d++) {
				EntityItem eir = (EntityItem) _ei.getDownLink(d);
				EntityItem eip = (EntityItem) eir.getUpLink(0);
				EntityItem eic = (EntityItem) eir.getDownLink(0);
				sb.append(
						"<br />Relator: "
						+ eir.getKey()
						+ ", Parent: "
						+ eip.getKey()
						+ ", Child: "
						+ eic.getKey());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		return sb.toString();
	}

}
