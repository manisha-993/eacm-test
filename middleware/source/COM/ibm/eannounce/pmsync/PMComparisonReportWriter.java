//
// Copyright (c) 2001-2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//$Log: PMComparisonReportWriter.java,v $
//Revision 1.28  2007/07/31 13:03:45  chris
//Rational Software Architect v7
//
//Revision 1.27  2005/10/24 23:30:21  dave
//minor changes for logging
//
//Revision 1.26  2005/01/27 17:29:32  gregg
//jtest
//
//Revision 1.25  2004/04/07 22:50:08  gregg
//Missing Part # text in report
//
//Revision 1.24  2004/04/07 22:35:48  gregg
//null ptr fix
//
//Revision 1.23  2004/04/06 21:29:03  gregg
//update report columns
//
//Revision 1.22  2004/03/17 21:38:32  gregg
//refining comps
//
//Revision 1.21  2004/03/17 20:54:54  gregg
//logically combining REV/CTO's for the sake of comparison (CR5740)
//
//Revision 1.20  2003/09/15 23:52:40  gregg
//ReportRow inner(inner) class now implementes *Comparable*
//
//Revision 1.19  2003/09/15 18:20:01  gregg
//sort report rows
//
//Revision 1.17  2003/09/11 22:54:18  gregg
//formatting
//
//Revision 1.16  2003/09/11 22:31:43  gregg
//summary fix
//
//Revision 1.15  2003/09/11 22:21:26  gregg
//update again ... null ptr fix??
//
//Revision 1.14  2003/09/11 21:47:52  gregg
//update
//
//Revision 1.13  2003/09/11 20:55:04  gregg
//update
//
//Revision 1.12  2003/09/11 20:06:54  gregg
//more formatting
//
//Revision 1.11  2003/09/11 19:52:51  gregg
//more match to 101 rpt
//
//Revision 1.10  2003/09/10 17:42:18  gregg
//begin update to match v101
//
//Revision 1.9  2003/08/07 21:22:37  gregg
//add ECL0 EntityID to report
//
//Revision 1.8  2003/08/01 23:21:48  gregg
//fix for comparing SBBs-ECL1s
//
//Revision 1.7  2003/05/30 00:09:48  gregg
//update
//
//Revision 1.6  2003/05/29 23:59:01  gregg
//non-matched tables
//
//Revision 1.5  2003/05/29 22:40:48  gregg
//update
//
//Revision 1.4  2003/05/29 00:30:04  gregg
//update
//
//Revision 1.3  2003/05/23 23:21:21  gregg
//update(still testing)
//
//Revision 1.2  2003/05/23 23:15:26  gregg
//update but still testing
//
//Revision 1.1  2003/05/22 20:52:41  gregg
//initial bootstrap load
//
//

package COM.ibm.eannounce.pmsync;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.Database;

/**
 * Generate a comparison report for PMSync.
 * This is currently used by ECABR1.
 * Report Template Defined by PMCoparisonReport.txt
 */
public class PMComparisonReportWriter extends TemplateBasedWriter {

	private EntityList m_el = null;
	private static final String REPORT_FILENAME = "PMComparisonReport.txt";

	private static final String EC_ENTTYPE = PMSyncEC.EC_ENTTYPE;
	private static final String ECL0_ENTTYPE = PMSyncECL0.ECL0_ENTTYPE;
	private static final String ECL1_ENTTYPE = PMSyncECL1.ECL1_ENTTYPE;
	private static final String SBB_ENTTYPE = PMSyncECL0.SBB_ENTTYPE;
	private static final String EC_NUMB_ATTCODE = PMSyncEC.EC_NUMB_ATTCODE;
	private static final String ECL0_NUMB_ATTCODE = PMSyncECL0.ECL0_PNUMB_ATTCODE;
	private static final String ECL0_DESC_ATTCODE = PMSyncECL0.ECL0_DESC_ATTCODE;
	private static final String CTO_PNUMB_ATTCODE = PMSyncECL0.CTO_PNUMB_ATTCODE;
	private static final String SBB_PNUMB_ATTCODE = PMSyncECL0.SBB_PNUMB_ATTCODE;
	private static final String ECL1_PNUMB_ATTCODE = PMSyncECL1.ECL1_PNUMB_ATTCODE;
	private static final String ECL1_BASICNAME_ATTCODE = PMSyncECL1.ECL1_BASICNAME_ATTCODE;
	private static final String CTO_ENTTYPE = PMSyncECL0.CTO_ENTTYPE;
	private static final String CTO_DESC_ATTCODE = PMSyncECL0.CTO_DESC_ATTCODE;
	private static final String SBB_PLANNINGRELEV_ATTCODE = PMSyncECL0.SBB_PLANNINGRELEV_ATTCODE;
	private static final String SBB_TYPE_ATTCODE = PMSyncECL0.SBB_TYPE_ATTCODE;

	// these are keys to replace w/ generated html in PMComparisonReport.txt
	private static final String EC_REPORT_TITLE_VAR = "$ECReportTitle$";
	private static final String PM_COMP_TABLES_VAR = "$PMComparisonTables$";
	// array easy access/looping
	private static final String[] SUB_VARS_ARRAY = { EC_REPORT_TITLE_VAR, PM_COMP_TABLES_VAR };
	//
	private StringBuffer m_sb = null;

	/**
     * PMComparisonReportWriter
     *
     * @param _el
     * @param _strFileName
     * @throws java.io.FileNotFoundException
     * @author gb
     */
	public PMComparisonReportWriter(EntityList _el, String _strFileName) throws FileNotFoundException {
		super(_strFileName, REPORT_FILENAME);
		m_el = _el;
	}

	/**
	 * gencomparisonReport
	 *
	 * @throws java.lang.Exception
	 * @author gb
	 */
	public void genComparisonReport() throws Exception {
		writeFile();
	}

	/**
	 * The template file to read from
	 *
	 * @return String
	 */
	protected String getTemplateFileName() {
		return REPORT_FILENAME;
	}

	/**
	 * The String to Substitute for the given Substitution Variable
	 *
	 * @return String
	 * @param _strSubVar
	 */
	protected String getSubstitutionString(String _strSubVar) {
		String strSub = null;
		if (_strSubVar.indexOf(EC_REPORT_TITLE_VAR) > -1) {
			strSub = getECReportTitle();
		} else if (_strSubVar.indexOf(PM_COMP_TABLES_VAR) > -1) {
			strSub = getPMComparisonTables();
		}
		return strSub;
	}

	private String getECReportTitle() {
		return getRootEntityItem().getAttribute(EC_NUMB_ATTCODE).toString() + " (" + EC_ENTTYPE + ":" + getRootEntityItem().getEntityID() + ")";
	}

	/**
	 * All tables
	 */
	private String getPMComparisonTables() {
		StringBuffer sb = new StringBuffer();
		Vector vctCTO_REV_pairs = null;
		EntityGroup egECL0 = getEntityGroup(ECL0_ENTTYPE);
		if (egECL0.getEntityItemCount() == 0) {
			sb.append("          <B>No EC Level 0's found for this EC.<B>");
		}
		vctCTO_REV_pairs = getCTO_REV_pairs(egECL0);
		System.err.println("getPMComparisonTables vctCTO_REV_pairs.size():" + vctCTO_REV_pairs.size());
		for (int i = 0; i < vctCTO_REV_pairs.size(); i++) {
			EntityItem[] eiPair = (EntityItem[]) vctCTO_REV_pairs.elementAt(i);
			if (eiPair[0] == null && eiPair[1] == null) {
				sb.append("NULLLLx2");
				System.err.println("getPMComparisonTables:found two null CTO_REV pairs");
				// this shouldnt ever happen
				continue;
			}
			sb.append(getECL0Table(eiPair[0], eiPair[1]));
		}
		return sb.toString();
	}

	private Vector getCTO_REV_pairs(EntityGroup _egECL0) {
		EntityItem[] aeiPair = null;
		Vector v = new Vector();
		Hashtable hashPN = new Hashtable();
		System.err.println("getCTO_REV_pairs _egECL0.getEntityITemCount():" + _egECL0.getEntityItemCount());
		OUTER_LOOP : for (int i = 0; i < _egECL0.getEntityItemCount(); i++) {
			EntityItem eiECL01 = _egECL0.getEntityItem(i);
			String strPN1 = PMSyncECL0.getPartNumber(eiECL01);
			System.err.println("getCTO_REV_pairs outer loop [" + i + "]:" + strPN1);
			if (hashPN.get(strPN1) != null) { // we already processed this guy
				System.err.println("getCTO_REV_pairs outer loop [" + i + "]:" + strPN1 + ": PN already processed, continuing outer loop");
				continue OUTER_LOOP;
			}
			aeiPair = new EntityItem[2];
			if (PMSyncECL0.isREV(eiECL01)) {
				aeiPair[1] = eiECL01;
			} else {
				aeiPair[0] = eiECL01;
			}
			// now mark this as found....
			hashPN.put(strPN1, strPN1);
			INNER_LOOP : for (int j = i + 1; j < _egECL0.getEntityItemCount(); j++) {
				EntityItem eiECL02 = _egECL0.getEntityItem(j);
				String strPN2 = PMSyncECL0.getPartNumber(eiECL02);
				System.err.println("getCTO_REV_pairs inner loop [" + j + "]:" + strPN2);
				if (hashPN.get(strPN2) != null) {
					// we already processed this guy
					System.err.println("getCTO_REV_pairs inner loop [" + j + "]:" + strPN2 + ": PN already processed, continuing inerr loop");
					continue INNER_LOOP;
				}
				if (PMSyncECL0.getConversePN(eiECL01).equals(strPN2)) {
					if (PMSyncECL0.isREV(eiECL02)) {
						aeiPair[1] = eiECL02;
					} else {
						aeiPair[0] = eiECL02;
					}
					hashPN.put(strPN2, strPN2);
					continue INNER_LOOP;
				}
			}
			v.addElement(aeiPair);
		}
		return v;
	}

	/**
	 * table/portlet for ~ONE~ ECL0
	 */
	private String getECL0Table(EntityItem _eiECL0_CTO, EntityItem _eiECL0_REV) {
		String strECL0_REV_hdr = null;
		EntityGroup egECL0 = getEntityGroup(ECL0_ENTTYPE);
		String strECL0_CTO_hdr = null;
		String strECL0 = null;
		if (_eiECL0_CTO != null) {
			strECL0_CTO_hdr = _eiECL0_CTO.getAttribute(ECL0_NUMB_ATTCODE).toString() + "(ECL0:" + _eiECL0_CTO.getEntityID() + ")</I>";
		}
		if (_eiECL0_REV != null) {
			strECL0_REV_hdr = _eiECL0_REV.getAttribute(ECL0_NUMB_ATTCODE).toString() + "(ECL0:" + _eiECL0_REV.getEntityID() + ")</I>";
		}
		strECL0 = egECL0.getLongDescription() + ": <I>" + (strECL0_CTO_hdr != null ? strECL0_CTO_hdr : "") + (strECL0_CTO_hdr != null && strECL0_REV_hdr != null ? ", " : "") + (strECL0_REV_hdr != null ? strECL0_REV_hdr : "");
		// simulate a 'portlet' type thing
		sb("");
		sb(10, "<!--Start getECL0Table()-->");
		sb(10, "<TABLE class=\"solid-border-all\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");

		sb(12, "<TR bgcolor=\"#CCCCFF\">");
		sb(14, "<TD class=\"dotted-bottom\">");
		sb(14, "<font color=\"black\"><small><b>&nbsp" + strECL0 + "</b></small></font>");
		sb(14, "</TD>");
		sb(12, "</TR>");

		try {

			//System.out.println("GAB - 1");

			EANList eListECL1s = null;
			Database dbCurrent = new Database();
			EntityGroup egCTO = PMSyncECL0.searchForCTOs(dbCurrent, getEntityGroup(ECL0_ENTTYPE), (_eiECL0_CTO != null ? _eiECL0_CTO : _eiECL0_REV));
			dbCurrent.freeStatement();
			dbCurrent.isPending();
			dbCurrent.commit();
			// child ECL1's
			eListECL1s = new EANList();

			//System.out.println("GAB - 2");
			// CTO's
			if (_eiECL0_CTO != null) {
				for (int iECL1 = 0; iECL1 < _eiECL0_CTO.getDownLinkCount(); iECL1++) {
					EntityItem eiDown = (EntityItem) _eiECL0_CTO.getDownLink(iECL1);
					if (eiDown.getEntityGroup().isRelator()) {
						eiDown = (EntityItem) eiDown.getDownLink(0);
					}
					if (eiDown.getEntityType().equals(ECL1_ENTTYPE)) {
						eListECL1s.put(eiDown);
					}
				}
			}
			// REV's
			if (_eiECL0_REV != null) {
				for (int iECL1 = 0; iECL1 < _eiECL0_REV.getDownLinkCount(); iECL1++) {
					EntityItem eiDown = (EntityItem) _eiECL0_REV.getDownLink(iECL1);
					if (eiDown.getEntityGroup().isRelator()) {
						eiDown = (EntityItem) eiDown.getDownLink(0);
					}
					if (eiDown.getEntityType().equals(ECL1_ENTTYPE)) {
						eListECL1s.put(eiDown);
					}
				}
			}

			//System.out.println("GAB - 3");

			if (egCTO == null || egCTO.getEntityItemCount() == 0) {

				//System.out.println("GAB - 4a");

				sb(12, "<TR>");
				sb(14, "<TD class=\"solid-bottom\">");
				sb(16, "<SPAN class\"table-title1\">");
				sb(16, "No Matching CTO found for " + (_eiECL0_CTO == null || _eiECL0_REV == null ? "this" : "these") + " ECL0.");
				sb(16, "</SPAN>");
				sb(14, "</TD>");
				sb(12, "</TR>");
			} else {

				//System.out.println("GAB - 4b");

				for (int iCTO = 0; iCTO < egCTO.getEntityItemCount(); iCTO++) {
					boolean bWhiteRow = true;
					EANList elNonMatchedSBBs = null;
					EANList elNonMatchedECL1s = null;
					ReportInfo reportInfo = null;
					//System.out.println("GAB - 5");
					Vector vctMatched = null;
					// NOW we need to grab this ~same~ EntityItem from our master EntityList to include downLinks
					EntityItem eiCTO = getEntityGroup(CTO_ENTTYPE).getEntityItem(CTO_ENTTYPE + egCTO.getEntityItem(iCTO).getEntityID());
					// child SBB's
					EANList eListSBBs = new EANList();

					for (int iSBB = 0; iSBB < eiCTO.getDownLinkCount(); iSBB++) {
						EntityItem eiDown = (EntityItem) eiCTO.getDownLink(iSBB);
						if (eiDown.getEntityGroup().isRelator()) {
							eiDown = (EntityItem) eiDown.getDownLink(0);
						}
						if (eiDown.getEntityType().equals(SBB_ENTTYPE)) {
							eListSBBs.put(eiDown);
						}
					}

					//System.out.println("GAB - 6");

					// at this point we have our list of ECL1's, SBB's for this ECL0.
					vctMatched = getMatched_ECL1s_SBBs(eListECL1s, eListSBBs);
					elNonMatchedSBBs = getNonMatchedSBBs(eListSBBs, vctMatched);
					elNonMatchedECL1s = getNonMatchedECL1s(eListECL1s, vctMatched);

					//System.out.println("GAB - 7");

					//
					// BEGIN Summary
					//
					sb(12, "<TR>");
					sb(14, "<TD class=\"solid-bottom\">");
					sb(16, "<SPAN class\"table-title1\">");
					if (_eiECL0_CTO != null) {
						String strECL0_CTO_smry = "PM Level 0 (CTO): (" + _eiECL0_CTO.getEntityType() + ":" + _eiECL0_CTO.getEntityID() + ") \"" + _eiECL0_CTO.getAttribute(ECL0_NUMB_ATTCODE) + "\"; \"" + _eiECL0_CTO.getAttribute(ECL0_DESC_ATTCODE) + "\"<BR>";
						sb(16, strECL0_CTO_smry);
					}
					if (_eiECL0_REV != null) {
						String strECL0_REV_smry = "PM Level 0 (REV): (" + _eiECL0_REV.getEntityType() + ":" + _eiECL0_REV.getEntityID() + ") \"" + _eiECL0_REV.getAttribute(ECL0_NUMB_ATTCODE) + "\"; \"" + _eiECL0_REV.getAttribute(ECL0_DESC_ATTCODE) + "\"<BR>";
						sb(16, strECL0_REV_smry);
					}
					sb(16, "e-announce CTO: (" + eiCTO.getEntityType() + ":" + eiCTO.getEntityID() + ") \"" + eiCTO.getAttribute(CTO_PNUMB_ATTCODE) + "\"; \"" + eiCTO.getAttribute(CTO_DESC_ATTCODE) + "\"<BR>");
					sb(16, "-- Total # of matching PM Level 1 - SBB Part Numbers: " + vctMatched.size() + "<BR>");
					sb(16, "-- Total # of SBB Part Numbers in e-announce but not in PM: " + elNonMatchedSBBs.size() + "<BR>");
					sb(16, "-- Total # of PM Level 1 Part Numbers in PM but not in e-announce: " + elNonMatchedECL1s.size() + "<BR>");
					sb(16, "</SPAN>");
					sb(14, "</TD>");
					sb(12, "</TR>");
					//
					// END Summary
					//

					///////////////////////
					// BEGIN Main Report //
					///////////////////////

					sb(12, "<TR>");
					sb(14, "<TD>");

					sb(16, "<CENTER>");
					sb(16, "<TABLE cellpadding=\"2\" cellspacing=\"0\" border=\"0\" width=\"98%\">");

					sb(18, "<TR>");
					sb(20, "<TD>");
					sb(22, "&nbsp;");
					sb(20, "</TD>");
					sb(18, "</TR>");

					//System.out.println("GAB - 8");

					reportInfo = new ReportInfo(vctMatched, elNonMatchedECL1s, elNonMatchedSBBs);

					sb(18, "<TR>");
					sb(20, "<TD>");
					sb(16, "<SPAN class\"table-title2\">");
					sb(16, "Report:");
					sb(16, "</SPAN>");
					sb(20, "</TD>");
					sb(18, "</TR>");

					//System.out.println("GAB - 9");

					sb(18, "<TR class=\"gray\">");
					for (int i = 0; i < reportInfo.getColumnHeaders().length; i++) {
						sb(20, "<TD class=\"dotted-right\">");
						sb(22, "<SPAN class=\"btn-66f\">&nbsp;" + reportInfo.getColumnHeaders()[i] + "</SPAN>");
						sb(20, "</TD>");
					}
					sb(18, "</TR>");

					//System.out.println("GAB - 10");

					for (int i = 0; i < reportInfo.getRowCount(); i++) {
						String[] saOneRow = reportInfo.getRow(i).toArray();
						sb(18, "<TR class=\"" + (bWhiteRow ? "white" : "gray") + "\">");
						for (int j = 0; j < saOneRow.length; j++) {
							sb(20, "<TD class=\"dotted-right\">");
							sb(22, saOneRow[j]);
							sb(20, "</TD>");
						}
						sb(18, "</TR>");
						bWhiteRow = !bWhiteRow;
					}

					//System.out.println("GAB - 11");

					//if(elNonMatchedSBBs.size() > 0) {
					//    sb(10,"<TR><TD colspan=\"" + reportInfo.getColumnHeaders().length + "\" class=\"solid-top\">* designates e-announce 7-digit Part Number</TD></TR>");
					//}

					sb(16, "</TABLE>");
					sb(16, "</CENTER>");

					sb(14, "</TD>");
					sb(12, "</TR>");

					///////////////////////
					// END Main Report //
					///////////////////////
				}
			}

		} catch (Exception exc) {
			exc.printStackTrace(System.err);
			System.out.println("\n\n\n\n\nGAB - EXC:" + exc + "\n\n\n\n");
			sb("<TR><TD><PRE>");
			exc.printStackTrace(this);
			sb("</PRE></TD></TR>");
		}
		sb(10, "</TABLE>");
		sb(10, "<BR><BR>");
		sb(10, "<!--End getECL0Table()-->");
		return getBufferString(true);
	}

	/**
	 * Get Matching ECL1s-SBBs
	 */
	private Vector getMatched_ECL1s_SBBs(EANList _eListECL1s, EANList _eListSBBs) {
		Vector vctMatched = new Vector();
		for (int iECL1 = 0; iECL1 < _eListECL1s.size(); iECL1++) {
			EntityItem eiECL1 = (EntityItem) _eListECL1s.getAt(iECL1);
			String strECL1_PN = eiECL1.getAttribute(ECL1_PNUMB_ATTCODE).toString();
			if (strECL1_PN.length() == 12) {
				strECL1_PN = strECL1_PN.substring(5, 12);
			} else {
				continue;
			}
			for (int iSBB = 0; iSBB < _eListSBBs.size(); iSBB++) {
				EntityItem eiSBB = (EntityItem) _eListSBBs.getAt(iSBB);
				String strSBB_PN = eiSBB.getAttribute(SBB_PNUMB_ATTCODE).toString();
				//check last 7 chars of ECL1 PM vs. entire SBB PM (replace any REV w/ CTO)
				if (strECL1_PN.equals(strSBB_PN)) {
					vctMatched.addElement(new EntityItem[] { eiECL1, eiSBB });
				}
				//System.err.println("GAB - strECL1_PN:" + strECL1_PN + ", strSBB_PN:" + strSBB_PN);
			}
		}
		return vctMatched;
	}

	/**
	 * Get Non-matched ECL1s
	 */
	private EANList getNonMatchedECL1s(EANList _eListECL1s, Vector _vctMatches) {
		EANList elReturn = new EANList();

		EANList elMatched = new EANList();
		for (int i = 0; i < _vctMatches.size(); i++) {
			EntityItem[] eiArr = (EntityItem[]) _vctMatches.elementAt(i);
			EntityItem eiECL1 = eiArr[0];
			elMatched.put(eiECL1);
		}

		for (int i = 0; i < _eListECL1s.size(); i++) {
			EntityItem eiECL1 = (EntityItem) _eListECL1s.getAt(i);
			// if not found in matched list...
			if (elMatched.indexOf(eiECL1) < 0) {
				elReturn.put(eiECL1);
			}
		}
		return elReturn;
	}

	/**
	 * Get Non-matched SBBs
	 */
	private EANList getNonMatchedSBBs(EANList _eListSBBs, Vector _vctMatches) {
		EANList elReturn = new EANList();

		EANList elMatched = new EANList();
		for (int i = 0; i < _vctMatches.size(); i++) {
			EntityItem[] eiArr = (EntityItem[]) _vctMatches.elementAt(i);
			EntityItem eiECL1 = eiArr[1];
			elMatched.put(eiECL1);
		}

		for (int i = 0; i < _eListSBBs.size(); i++) {
			EntityItem eiSBB = (EntityItem) _eListSBBs.getAt(i);
			// if not found in matched list...
			if (elMatched.indexOf(eiSBB) < 0) {
				elReturn.put(eiSBB);
			}
		}
		return elReturn;
	}

	////////////////////
	// UTITLITIES ETC //
	////////////////////
	private EntityItem getRootEntityItem() {
		return getRootEntityGroup().getEntityItem(0);
	}

	private EntityGroup getRootEntityGroup() {
		return getEntityList().getParentEntityGroup();
	}

	private EntityGroup getEntityGroup(String _strKey) {
		return getEntityList().getEntityGroup(_strKey);
	}

	private EntityList getEntityList() {
		return m_el;
	}

	/**
	 * @see COM.ibm.eannounce.pmsync.TemplateBasedWriter#getSubstitutionVariables()
	 * @author gb
	 */
	protected String[] getSubstitutionVariables() {
		return SUB_VARS_ARRAY;
	}

	private void resetStringBuffer() {
		m_sb = new StringBuffer();
	}

	private String getBufferString(boolean _bFlush) {
		String s = m_sb.toString();
		if (_bFlush) {
			resetStringBuffer();
		}
		return s;
	}

	private void sb(String _s) {
		if (m_sb == null) {
			resetStringBuffer();
		}
		m_sb.append(_s);
		m_sb.append("\n");
	}
	// help keep track of nested tables by placing specified # of spaces
	private void sb(int _iSpaces, String _s) {
		StringBuffer sbSpaces = new StringBuffer();
		for (int i = 0; i < _iSpaces; i++) {
			sbSpaces.append(" ");
		}
		_s = sbSpaces + _s;
		sb(_s);
	}

	/**
     * getVersion
     *
     * @return
     * @author gb
     */
	public static String getVersion() {
		return "$Id: PMComparisonReportWriter.java,v 1.28 2007/07/31 13:03:45 chris Exp $";
	}

	// just put everything into row/column format for easier access
	private class ReportInfo {

		private Vector m_vctRows = null;
		//private final String[] m_saColHdrs = {"Level 1 Part Number","Description (PM)","Basic Name (PM)","Entity ID (PM)","Description (e-announce)","Entity ID (e-announce)"};
		private final String[] m_saColHdrs = { "Comp Number (PM)", "Part Number (e-announce)", "Basic Name (PM)", "SBB Type (e-announce)", "Planning Relevant Flag (e-announce)" };

		private ReportInfo(Vector _vctMatched, EANList _elECL1, EANList _elSBB) {
			m_vctRows = new Vector();

			// 1) put the un-matched SBBs at beginning
			for (int i = 0; i < _elSBB.size(); i++) {
				EntityItem eiSBB = (EntityItem) _elSBB.getAt(i);
				m_vctRows.addElement(genOneRow(null, eiSBB));
			}

			// 2)
			for (int i = 0; i < _elECL1.size(); i++) {
				EntityItem eiECL1 = (EntityItem) _elECL1.getAt(i);
				m_vctRows.addElement(genOneRow(eiECL1, null));
			}

			// 3)
			for (int i = 0; i < _vctMatched.size(); i++) {
				EntityItem[] aEi = (EntityItem[]) _vctMatched.elementAt(i);
				EntityItem eiECL1 = aEi[0];
				EntityItem eiSBB = aEi[1];
				m_vctRows.addElement(genOneRow(eiECL1, eiSBB));
			}

			// NOW SORT!
			performSort();

		}

		private void performSort() {
			ReportRow[] aRows = new ReportRow[getRowCount()];
			for (int i = 0; i < getRowCount(); i++) {
				aRows[i] = getRow(i);
			}

			/////
			Arrays.sort(aRows);
			/////

			m_vctRows = new Vector();
			for (int i = 0; i < aRows.length; i++) {
				m_vctRows.addElement(aRows[i]);
			}
			return;
		}

		public int getRowCount() {
			return m_vctRows.size();
		}

		public ReportRow getRow(int _i) {
			return (ReportRow) m_vctRows.elementAt(_i);
		}

		public String[] getColumnHeaders() {
			return m_saColHdrs;
		}

		private ReportRow genOneRow(EntityItem _eiECL1, EntityItem _eiSBB) {
			return new ReportRow(_eiECL1, _eiSBB);
		}

		private class ReportRow implements Comparable {

			private String[] m_saRow = null;

			private ReportRow(EntityItem _eiECL1, EntityItem _eiSBB) {
				m_saRow = new String[getColumnHeaders().length];
				EANAttribute att1 = null;
				EANAttribute att2 = null;
				EANAttribute att3 = null;
				EANAttribute att4 = null;
				EANAttribute att5 = null;
				/*
				m_saRow[0] = ((_eiECL1 != null)?(_eiECL1.getAttribute(ECL1_PNUMB_ATTCODE).toString()):(_eiSBB.getAttribute(SBB_PNUMB_ATTCODE).toString()+"*"));
				m_saRow[1] = ((_eiECL1 != null)?(_eiECL1.getAttribute(ECL1_DESC_ATTCODE).toString()):("--"));
				m_saRow[2] = ((_eiECL1 != null)?(_eiECL1.getAttribute(ECL1_BASICNAME_ATTCODE).toString()):("--"));
				m_saRow[3] = ((_eiECL1 != null)?(_eiECL1.getEntityType()+":"+_eiECL1.getEntityID()):("<FONT color=\"#CC0000\">No Matching PM Part #</FONT>"));
				m_saRow[4] = ((_eiSBB != null)?(_eiSBB.getAttribute(SBB_DESC_ATTCODE).toString()):("--"));
				m_saRow[5] = ((_eiSBB != null)?(_eiSBB.getEntityType()+":"+_eiSBB.getEntityID()):("<FONT color=\"#CC0000\">No Matching e-announce Part #</FONT>"));
				*/

				if (_eiECL1 != null) {
					att1 = _eiECL1.getAttribute(ECL1_PNUMB_ATTCODE);
					att3 = _eiECL1.getAttribute(ECL1_BASICNAME_ATTCODE);
				}
				if (_eiSBB != null) {
					att2 = _eiSBB.getAttribute(SBB_PNUMB_ATTCODE);
					att4 = _eiSBB.getAttribute(SBB_TYPE_ATTCODE);
					att5 = _eiSBB.getAttribute(SBB_PLANNINGRELEV_ATTCODE);
				}

				m_saRow[0] = ((att1 != null) ? (att1.toString()) : ("<FONT color=\"#CC0000\">No Matching PM Part #</FONT>"));
				m_saRow[1] = ((att2 != null) ? (att2.toString()) : ("<FONT color=\"#CC0000\">No Matching e-announce Part #</FONT>"));
				m_saRow[2] = ((att3 != null) ? (att3.toString()) : ("--"));
				m_saRow[3] = ((att4 != null) ? (att4.toString()) : ("--"));
				m_saRow[4] = ((att5 != null) ? (att5.toString()) : ("--"));

			}

			public String[] toArray() {
				return m_saRow;
			}

			public int compareTo(Object _obj) {
				return this.toArray()[0].compareToIgnoreCase(((ReportRow) _obj).toArray()[0]);
			}

		}
	}
}
