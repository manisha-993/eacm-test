// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: HWPRODINITIAL30APDG.java,v $
// Revision 1.9  2008/09/04 21:33:01  wendy
// Cleanup RSA warnings
//
// Revision 1.8  2007/09/14 18:28:34  couto
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.7  2005/02/28 20:29:51  joan
// add throw exception
//
// Revision 1.6  2004/11/17 00:06:11  joan
// changes to getRowIndex key in rowselectable table
//
// Revision 1.5  2004/11/12 20:44:58  joan
// adjust error messages
//
// Revision 1.4  2004/10/11 22:10:30  joan
// fixes
//
// Revision 1.3  2004/09/27 22:38:43  joan
// fixes
//
// Revision 1.2  2004/09/14 22:55:09  joan
// fixes
//
// Revision 1.1  2004/09/14 17:20:57  joan
// initial load
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.transactions.OPICMList;

public class HWPRODINITIAL30APDG extends PDGActionItem {

  static final long serialVersionUID = 20011106L;

	private String m_strAfSerType = null;
	private String m_strAfMachType = null;
	private String m_strAfModel = null;
	private String m_strAfUniqueID = null;
	private String m_strAnnCodeName = null;
  	//private Vector m_afOsLevelVec = new Vector();
	private Vector m_afProcessorVec = new Vector();
	private Vector m_afPackageVec = new Vector();

	//private	EANList m_availList = new EANList();
	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList();
	private StringBuffer m_sbData = new StringBuffer();
	//private EANList m_comboList = new EANList();
	private Vector m_comboVec = new Vector();
	private OPICMList m_processedComboList = new OPICMList();
	private boolean m_bComplete = false;
	int m_iExeCount = 0;
	public static final int COMBOLIMIT= 20;


  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: HWPRODINITIAL30APDG.java,v 1.9 2008/09/04 21:33:01 wendy Exp $");
  	}


	public HWPRODINITIAL30APDG(EANMetaFoundation  _mf, HWPRODINITIAL30APDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

	public HWPRODINITIAL30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	  	m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("HWPRODINITIAL30APDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return strbResult.toString();
  	}

  	public String getPurpose() {
  		return "HWPRODINITIAL30APDG";
  	}

	public String getStepDescription(int iStep) {
		if (iStep == 1) {
			return "Select Combination";
		}
		return "N/A";
	}

	public void setPDGCollectInfo(PDGCollectInfoList _cl, int _iStep, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				m_InfoList = _cl;
				EANList comboList = m_InfoList.getInfoList();
				StringBuffer sb = new StringBuffer();
				boolean bFirst = true;
        		for (int i=0; i < comboList.size(); i++) {
					PDGCollectInfoItem ci = (PDGCollectInfoItem)comboList.getAt(i);
					String strFrom = ci.getFirstItem();
					String strTo = ci.getSecondItem();
					sb.append((!bFirst? "\n": "") + strFrom + ":" + strTo);
					bFirst = false;
				}

				int r = _eiRst.getRowIndex(m_eiPDG.getEntityType() + ":AFINFO:C");
				if (r >= 0 && r < _eiRst.getRowCount()) {
					try {
						_eiRst.put(r,1,sb.toString());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public PDGCollectInfoList collectInfo(int iStep) {
		if (iStep == 1) {
			if (m_eiPDG != null) {
				try {
					EANTextAttribute att1 = (EANTextAttribute)m_eiPDG.getAttribute("AFHWPACKAGE");
					Vector vecPack = new Vector();
					if (att1 != null) {
						String s = (String)att1.get();
						vecPack = m_utility.sepLongText(s);
					}

					Vector vecProc = new Vector();

					EANTextAttribute att2 = (EANTextAttribute)m_eiPDG.getAttribute("AFHWPROCESSOR");
					if (att2 != null) {
						String s = (String)att2.get();
						vecProc = m_utility.sepLongText(s);
					}

					m_InfoList = new PDGCollectInfoList(this, getProfile(), "Processor\\Package");
					for(int i=0; i < vecProc.size(); i++) {
						String strProc = (String)vecProc.elementAt(i);
						PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, getProfile(), false, strProc, " ", strProc);
						m_InfoList.putCollectInfoItem(pi);
						for (int j=0; j< vecPack.size(); j++) {
							String strPack = (String)vecPack.elementAt(j);
							pi = new PDGCollectInfoItem(m_InfoList, getProfile(), false, strProc, strPack, strProc + "|" + strPack);
							m_InfoList.putCollectInfoItem(pi);
						}
					}
			 	} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return m_InfoList;
	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " HWPRODINITIAL30APDG checkMissingData method";

	    m_eiBaseCOF = (EntityItem)m_eiList.get("BASE");
		_db.test(m_eiBaseCOF != null, " Base Model is null");

        for (int i=0; i < m_comboVec.size(); i++) {
			String strCombo = (String)m_comboVec.elementAt(i);
			int index = strCombo.indexOf(":");
			String strProcessor = strCombo.substring(0, index);
			String strPackage = strCombo.substring(index+1);

			if (m_processedComboList.get(strProcessor + strPackage) == null) {

				OPICMList infoList = new OPICMList();
				infoList.put("WG", m_eiRoot);
				infoList.put("PDG", m_eiPDG);
				infoList.put("PROC", strProcessor);
				infoList.put("GEOIND", "GENAREASELECTION");
				if (strPackage != null && strPackage.length() > 0) {
					infoList.put("PACK", strPackage);
				}

				String strFileName = "PDGtemplates/HWProdInitial_30a.txt";
				if (strPackage != null && strPackage.length() > 0) {
					strFileName = "PDGtemplates/HWProdInitial2_30a.txt";
				}

				_prof = m_utility.setProfValOnEffOn(_db, _prof);

				TestPDGII pdgObject = new TestPDGII(_db, _prof, m_eiBaseCOF, infoList, m_PDGxai, strFileName);
				StringBuffer sbMissing = pdgObject.getMissingEntities();
				pdgObject = null;		//this would free memories
				infoList = null;		//this would also free some memory
				if (_bGenData) {
					generateDataII(_db, _prof, sbMissing,"");
					//should save the combo to prevent from checking it again.
					m_processedComboList.put(strProcessor + strPackage, strProcessor + strPackage);
				}
				m_sbData.append(sbMissing.toString());
			}
		}

		return m_sbData;
	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
		//String strTraceBase = " HWPRODINITIAL30APDG checkPDGAttribute method";
		for (int i =0; i < _afirmEI.getAttributeCount(); i++) {
			EANAttribute att = _afirmEI.getAttribute(i);
			String textAtt = "";
			String sFlagAtt = "";
			//String sFlagClass = "";
			Vector mFlagAtt = new Vector();
			Vector mFlagCode = new Vector();

			//int index = -1;
			if (att instanceof EANTextAttribute) {
				textAtt = ((String)att.get()).trim();
			} else if (att instanceof EANFlagAttribute) {
				if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
							sFlagAtt = amf[f].getLongDescription().trim();
				//			sFlagClass = amf[f].getFlagCode().trim();
					//		index = f;
							break;
						}
					}
				} else if (att instanceof MultiFlagAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
							mFlagAtt.addElement(amf[f].getLongDescription().trim());
							mFlagCode.addElement(amf[f].getFlagCode().trim());
						}
					}
				}
			}

			if (att.getKey().equals("AFHWSERTYPE")) {
				m_strAfSerType = sFlagAtt;
			} else if (att.getKey().equals("MACHTYPEATR")) {
				m_strAfMachType = sFlagAtt;
			} else if (att.getKey().equals("MODELATR")) {
				m_strAfModel = textAtt;
			} else if (att.getKey().equals("AFHWUNIQUEID")) {
				m_strAfUniqueID = textAtt;
			} else if (att.getKey().equals("OSLEVEL")) {
				//m_afOsLevelVec = mFlagAtt;
			} else if (att.getKey().equals("AFHWPROCESSOR")) {
				m_afProcessorVec = m_utility.sepLongText(textAtt);
			} else if (att.getKey().equals("ANNCODENAME")) {
				m_strAnnCodeName = sFlagAtt;
			} else if (att.getKey().equals("AFHWPACKAGE")) {
				m_afPackageVec =  m_utility.sepLongText(textAtt);
				checkPackageFormat(m_afPackageVec);
			} else if (att.getKey().equals("AFINFO")) {
				m_comboVec = m_utility.sepLongText(textAtt);
			}
		}

		if (m_afProcessorVec.size() > 0 && (m_strAfUniqueID == null || m_strAfUniqueID.length() <= 0)) {
			m_SBREx.add("Unique ID is required if Processor Card has input");
		}

		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}

		if (m_comboVec == null || m_comboVec.size() <= 0)  {
			if (m_afProcessorVec.size() == 1 && m_afPackageVec.size() <= 0) {
				// if input for processor is one, don't have to select the combination.
				// load in the combo list
				String strProc = (String)m_afProcessorVec.elementAt(0);
				m_comboVec.addElement(strProc + ": ");
			} else if (m_afProcessorVec.size() > 1) {
				m_SBREx.add("Please select processor package combination.");
			}
		} else if (m_comboVec.size() > COMBOLIMIT) {
			m_SBREx.add("Number of selected combinations exceeds the limit of " + COMBOLIMIT );
		}
	}

	protected void resetVariables() {
		m_strAfSerType = null;
		m_strAfMachType = null;
		m_strAfModel = null;
		m_strAfUniqueID = null;
		//m_afOsLevelVec = new Vector();
		m_afProcessorVec = new Vector();
		//m_availList = new EANList();
		m_eiList = new EANList();
		m_processedComboList = new OPICMList();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " HWPRODINITIAL30APDG viewMissing method";
		String sReturn = "";
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			m_SBREx = new SBRException();
			resetVariables();
			if (m_eiPDG == null) {
				String s="PDG entity is null";
				return s.getBytes();
			}
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT2");
			EntityItem[] eiParm = {m_eiPDG};
			EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
			EntityGroup eg = el.getParentEntityGroup();
			m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
			eg = el.getEntityGroup("WG");
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiRoot = eg.getEntityItem(0);
				}
			}

			checkPDGAttribute(_db, _prof, m_eiPDG);

			// validate data
			checkDataAvailability(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
				throw m_SBREx;
			}

			m_sbActivities = new StringBuffer();
			m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
			m_sbData = new StringBuffer();

			sReturn = checkMissingData(_db, _prof, false).toString();
			if (sReturn.length() <= 0) {
				sReturn = "Generating data is complete";
			}
			m_sbActivities.append(m_utility.getViewXMLString(sReturn));
			m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());
		} finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
		}
		return sReturn.getBytes();
	}

	private void checkPackageFormat(Vector _v) {
		for (int i=0; i < _v.size(); i++) {
			String str = (String)_v.elementAt(i);
			checkPackageFormat(str);
		}
	}

	private boolean checkPackageFormat(String _s) {
		if (_s.length() > 6) {
			m_SBREx.add(_s + ": max length is 6.");
			m_SBREx.setResetPDGCollectInfo(true);
			return false;
		}

		for (int j = 0; j < _s.length(); j++) {
		    char c = _s.charAt(j);
		    if (!(Character.isLetterOrDigit(c) || Character.isSpaceChar(c))) {
				m_SBREx.add(_s + " has to be alpha or integer.");
				m_SBREx.setResetPDGCollectInfo(true);
				return false;
		    }
		}
		return true;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " HWPRODINITIAL30APDG executeAction method";

		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			m_SBREx = new SBRException();

			m_iExeCount++;
			if (m_iExeCount == 1) {
				m_bComplete = false;
				resetVariables();
				if (m_eiPDG == null) {
					System.out.println("PDG entity is null");
					return;
				}
				_prof = m_utility.setProfValOnEffOn(_db, _prof);
	//			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, m_utility.STATUS_SUBMIT, "", getLongDescription());

				ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT2");
				EntityItem[] eiParm = {m_eiPDG};
				EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
				EntityGroup eg = el.getParentEntityGroup();
				m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
				eg = el.getEntityGroup("WG");
				if (eg != null) {
					if (eg.getEntityItemCount() > 0) {
						m_eiRoot = eg.getEntityItem(0);
					}
				}

				_db.test(m_eiRoot != null, "Workgroup entity is null");

				checkPDGAttribute(_db, _prof, m_eiPDG);
				checkDataAvailability(_db, _prof, m_eiPDG);
				if (m_SBREx.getErrorCount() > 0) {
					m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
					throw m_SBREx;
				}
				m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
				m_utility.resetActivities();
				m_sbActivities = new StringBuffer();
				m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
				m_sbData = new StringBuffer();
			}

			checkMissingData(_db, _prof, true).toString();
			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
			m_iExeCount = 0;
			m_bComplete = true;
		} catch (SBRException ex) {
			m_bComplete = true;
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		} catch (Exception x) {
			m_bComplete = true;
			x.printStackTrace();
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, x.toString(), getLongDescription());
			m_SBREx.add(x.toString());
			throw m_SBREx;
		} finally {
			//System.out.println(strTraceBase + " m_bComplete: " + m_bComplete);
			if (!m_bComplete) {
				executeAction(_db, _prof);
			}

            _db.commit();
            _db.freeStatement();
            _db.isPending();
		}

		if (m_sbData.toString().length() <= 0) {
			m_SBREx.add("Generating data is complete.  No data created during this run. (ok)");
			throw m_SBREx;
		}
	}

/**
 * @return true if successful, false if nothing to update or unsuccessful
 */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
	    return true;
	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		StringBuffer sb = new StringBuffer();
		// make sure the Base MODEL for the MTM already exists
		sb.append("map_COFCAT=100;");
		sb.append("map_COFSUBCAT=126;");
		sb.append("map_COFGRP=150;");
		sb.append("map_COFSUBGRP=" + m_strAfSerType + ";");
		sb.append("map_MACHTYPEATR=" + m_strAfMachType + ";");
		sb.append("map_MODELATR=" + m_strAfModel);

		String strSai = (String)m_saiList.get("MODEL");
		EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
		if (aeiCOM.length <= 0 ) {
		   m_SBREx.add("The Model with classifications HW-System-Base-" + m_strAfSerType + ", MACHTYPE=" + m_strAfMachType + ", MODEL=" + m_strAfModel + " must be created before Initial Model.");
		} else if (aeiCOM.length > 1) {
		   m_SBREx.add("There are " + aeiCOM.length + " existing MODELs with classifications HW-System-Base-" + m_strAfSerType + ", MACHTYPE=" + m_strAfMachType + ", MODEL=" + m_strAfModel);
		} else {
		   m_eiList.put("BASE", aeiCOM[0]);
		}

		sb = null;
	}
}
