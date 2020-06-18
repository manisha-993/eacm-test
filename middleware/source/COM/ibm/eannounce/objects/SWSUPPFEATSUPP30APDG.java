// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: SWSUPPFEATSUPP30APDG.java,v $
// Revision 1.13  2008/09/08 17:32:46  wendy
// Cleanup RSA warnings
//
// Revision 1.12  2007/09/06 12:57:16  couto
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.11  2006/05/08 21:25:15  joan
// add print trace
//
// Revision 1.10  2005/08/04 14:39:26  joan
// fixes
//
// Revision 1.9  2004/11/12 20:44:59  joan
// adjust error messages
//
// Revision 1.8  2004/11/11 19:20:29  joan
// fixes
//
// Revision 1.7  2004/10/05 17:57:44  joan
// fixes
//
// Revision 1.6  2004/09/22 20:25:16  joan
// fixes
//
// Revision 1.5  2004/09/03 15:58:02  joan
// fixes
//
// Revision 1.4  2004/09/02 23:58:55  joan
// fixes
//
// Revision 1.3  2004/09/02 17:04:55  joan
// fixes
//
// Revision 1.2  2004/09/02 16:57:01  joan
// fixes
//
// Revision 1.1  2004/09/02 16:16:47  joan
// add files
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

public class SWSUPPFEATSUPP30APDG extends PDGActionItem {

  static final long serialVersionUID = 20011106L;

	private String m_strAfOptFeature = "m00";
	private Vector m_afSuppProdVec = new Vector();
	//private String m_strAfBillingTemplate = null;
	private String m_strAfMachType = null;
	private String m_strAfModel = null;
	private String m_strAnnCodeName = null;
	//private Vector m_afOsLevelVec = new Vector();
	private String m_strAfReqType = null;
	//private	EANList m_availList = new EANList();
	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList();

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: SWSUPPFEATSUPP30APDG.java,v 1.13 2008/09/08 17:32:46 wendy Exp $");
  	}

	public SWSUPPFEATSUPP30APDG(EANMetaFoundation  _mf, SWSUPPFEATSUPP30APDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	public SWSUPPFEATSUPP30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("SWSUPPFEATSUPP30APDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return strbResult.toString();
  	}

  	public String getPurpose() {
  		return "SWSUPPFEATSUPP30APDG";
  	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
//		String strTraceBase = " SWSUPPFEATSUPP30APDG checkMissingData method";
		StringBuffer sbReturn = new StringBuffer();
		EntityItem eiBaseCOF =(EntityItem)m_eiList.get("BASE");
		_db.test(eiBaseCOF != null, " Base MODEL is null");
		String strFileName = "PDGtemplates/SuppFeatSupp_30a.txt";
		m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

		for(int i=0; i < m_afSuppProdVec.size(); i++) {
			String strSuppProd = (String)m_afSuppProdVec.elementAt(i);
			int index = strSuppProd.indexOf("-");
			String strMachType = strSuppProd.substring(0,index);
			String strModel = strSuppProd.substring(index+1);

			OPICMList infoList = new OPICMList();
			infoList.put("PDG", m_eiPDG);
			infoList.put("SUPPMACHTYPE", strMachType);
			infoList.put("SUPPMODEL", strModel);
			infoList.put("SUPPOPTFEATUREID", "f" + m_strAfOptFeature.substring(1));
			infoList.put("GEOIND", "GENAREASELECTION");

			_prof = m_utility.setProfValOnEffOn(_db, _prof);

			TestPDGII pdgObject = new TestPDGII(_db, _prof, eiBaseCOF, infoList, m_PDGxai, strFileName);
			StringBuffer sbMissing = pdgObject.getMissingEntities();
	//		EntityList el = pdgObject.getEntityList();
			pdgObject = null;
			infoList = null;

			if (_bGenData) {
				generateDataII(_db, _prof, sbMissing, strSuppProd);
			}

			sbReturn.append(sbMissing.toString());
		}
		return sbReturn;
	}

/*	private Vector checkOSLEVELUnique(Vector _v) {
		Hashtable ht = new Hashtable();
		for (int i=0; i < _v.size(); i++) {
			String s = (String)_v.elementAt(i);
			String s1 = "@"; // allow for '--' to be selected, avoid StringIndexOutOfBoundsException
            if (s.length()>3){
				s1 = s.substring(3, 4);
			}
			if (ht.get(s1) == null) {
				ht.put(s1, s);
			} else {
				m_SBREx.add(" The 4th character of all selected OSLEVELs must be unique.");
			}
		}
		return _v;
	}*/

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
	//	String strTraceBase = " SWSUPPFEATSUPP30APDG checkPDGAttribute method";
		for (int i =0; i < _afirmEI.getAttributeCount(); i++) {
			EANAttribute att = _afirmEI.getAttribute(i);
			String textAtt = "";
			String sFlagAtt = "";
	//		String sFlagClass = "";
			Vector mFlagAtt = new Vector();

	//		int index = -1;
			if (att instanceof EANTextAttribute) {
				textAtt = ((String)att.get()).trim();
			} else if (att instanceof EANFlagAttribute) {
				if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
							sFlagAtt = amf[f].getLongDescription().trim();
	//						sFlagClass = amf[f].getFlagCode().trim();
	//						index = f;
							break;
						}
					}
				} else if (att instanceof MultiFlagAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
							mFlagAtt.addElement(amf[f].getLongDescription().trim());
						}
					}
				}
			}

			if (att.getKey().equals("AFSWSUPPREQUESTTYPE")) {
				m_strAfReqType = sFlagAtt;
			} else if (att.getKey().equals("OPTFEATUREID")) {
				m_SBREx = m_utility.checkOptFeatureIDFormat(textAtt, PDGUtility.OF_SUPPORT, false, m_SBREx);
				m_strAfOptFeature = textAtt;
			} else if (att.getKey().equals("MACHTYPEATR")) {
				m_strAfMachType = sFlagAtt;
			} else if (att.getKey().equals("MODELATR")) {
				m_strAfModel = textAtt;
			} else if (att.getKey().equals("OSLEVEL")) {
				//m_afOsLevelVec = checkOSLEVELUnique(mFlagAtt);
			} else if (att.getKey().equals("AFBILLINGTEMPLATE")) {
				//m_strAfBillingTemplate = sFlagAtt;
			} else if (att.getKey().equals("AFMTM")) {
				m_SBREx = checkSuppProdString(textAtt, m_SBREx);
				m_afSuppProdVec = m_utility.sepLongText(textAtt);
			} else if (att.getKey().equals("GENAREASELECTION")) {
				m_SBREx = m_utility.checkGenAreaOverlap(mFlagAtt, m_SBREx);
			} else if (att.getKey().equals("ANNCODENAME")) {
				m_strAnnCodeName = sFlagAtt;
			}
		}

		if ( ! "Support Feature".equals(m_strAfReqType)) {
			SBRException ex = new SBRException();
			ex.add(" Request Type:" + m_strAfReqType + ". This action item is for SW Support Feature Request.");
			throw ex;
		}

		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}

	}
	private SBRException checkSuppProdString(String _s, SBRException _sbrex) {
		StringTokenizer st = new StringTokenizer(_s, "\n");
		while (st.hasMoreElements()) {
			String str = st.nextToken().trim();
			int iSep = str.indexOf("-");
			if (iSep < 0) {
				_sbrex.add(str + " is not in format pppp-ppp");
			}
		}
		return _sbrex;
	}

	protected void resetVariables() {
	 	m_strAfOptFeature = "m00";
	 	m_afSuppProdVec = new Vector();
	 	//m_strAfBillingTemplate = null;
	 	m_strAfMachType = null;
	 	m_strAfModel = null;
	 	//m_afOsLevelVec = new Vector();
	 	//m_availList = new EANList();
		m_eiList = new EANList();
		//m_opList = new EANList();
		//m_vctReturnEntityKeys = new Vector();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SWSUPPFEATSUPP30APDG viewMissing method";

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		m_SBREx = new SBRException();
		resetVariables();
		if (m_eiPDG == null) {
			String s="PDG entity is null";
			return s.getBytes();
		}

		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT6");
		EntityItem[] eiParm = {m_eiPDG};
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
		EntityGroup eg = el.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
		eg = el.getEntityGroup("WG");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "Work Group entity is null");

		checkPDGAttribute(_db, _prof, m_eiPDG);
		// validate data
		checkDataAvailability(_db, _prof, m_eiPDG);
		if (m_SBREx.getErrorCount() > 0) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
			throw m_SBREx;
		}
		m_sbActivities = new StringBuffer();
		m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");

		String s = checkMissingData(_db, _prof, false).toString();
		if (s.length() <= 0) {
			s = "Generating data is complete";
		}
		m_sbActivities.append(m_utility.getViewXMLString(s));
		m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
		m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());
		return s.getBytes();
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SWSUPPFEATSUPP30APDG executeAction method";
	//	String strData = "";
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			m_SBREx = new SBRException();
			resetVariables();
			if (m_eiPDG == null) {
				System.out.println("PDG entity is null");
				return;
			}

			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT6");
			EntityItem[] eiParm = {m_eiPDG};
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
			EntityGroup eg = el.getParentEntityGroup();
			m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
			eg = el.getEntityGroup("WG");
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiRoot = eg.getEntityItem(0);
				}
			}
			_db.test(m_eiRoot != null, "Work Group entity is null");

			checkPDGAttribute(_db, _prof, m_eiPDG);
			// validate data
			checkDataAvailability(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
				throw m_SBREx;
			}
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
			m_utility.resetActivities();
			m_sbActivities = new StringBuffer();
			m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
			//strData = 
				checkMissingData(_db, _prof,true).toString();
			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
		} catch (SBRException ex) {
			ex.printStackTrace();
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			m_SBREx.add(ex.toString());
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
		sb.append("map_COFCAT=101;");
		sb.append("map_COFSUBCAT=135;");
		sb.append("map_COFGRP=150;");
		sb.append("map_MACHTYPEATR=" + m_strAfMachType + ";");
		sb.append("map_MODELATR=" + m_strAfModel+ ";");
		sb.append("map_OPTFEATUREID=" + m_strAfOptFeature);

		String strSai = (String)m_saiList.get("MODEL");
		EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());

		if (aeiCOM.length <= 0 ) {
			m_SBREx.add("The MODEL with classifications SW-SupportFeature-Base, MACHTYPE=" + m_strAfMachType + ", MODEL=" + m_strAfModel+ ", OPTFEATUREID=" + m_strAfOptFeature + " must be created before connecting to supported MODEL.");
		} else if (aeiCOM.length > 1) {
			m_SBREx.add("There are " + aeiCOM.length + " existing MODELs with classifications SW-SupportFeature-Base, MACHTYPE=" + m_strAfMachType + ", MODEL=" + m_strAfModel+ ", OPTFEATUREID=" + m_strAfOptFeature);
		} else {
			m_eiList.put("BASE", aeiCOM[0]);
		}

		//verify supported products
		for(int i=0; i < m_afSuppProdVec.size(); i++) {
			String strSuppProd = (String)m_afSuppProdVec.elementAt(i);
			int index = strSuppProd.indexOf("-");
			String strMachType = strSuppProd.substring(0,index);
			String strModel = strSuppProd.substring(index+1);

			// check if the supported products MODEL exists
			StringBuffer sb1 = new StringBuffer();
			sb1.append("map_COFCAT=101;");
			sb1.append("map_COFSUBCAT=130;");
			sb1.append("map_COFGRP=150;");
			sb1.append("map_COFSUBGRP=010;");
			sb1.append("map_MACHTYPEATR=" + strMachType + ";");
			sb1.append("map_MODELATR=" + strModel + ";");
			sb1.append("map_OPTFEATUREID=f" + m_strAfOptFeature.substring(1));

			String strSai1 = (String)m_saiList.get("MODEL");
			EntityItem[] aeiCOF = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai1, "MODEL", sb1.toString());
			if (aeiCOF == null || aeiCOF.length <= 0) {
				m_SBREx.add(" Supported Software-OptionalFeature-Base-N/A MODELs for " + strSuppProd + " and f" + m_strAfOptFeature.substring(1) + " not found.");
			} else {
				m_eiList.put(strSuppProd, aeiCOF[0]);
			}
		}
	}

}
