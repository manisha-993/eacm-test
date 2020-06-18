//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ALWRCSOLABR002PDG.java,v $
// Revision 1.16  2008/09/04 20:38:13  wendy
// Cleanup RSA warnings
//
// Revision 1.15  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.14  2005/04/28 21:15:36  joan
// fixes
//
// Revision 1.13  2003/11/25 20:34:04  joan
// adjust copy
//
// Revision 1.12  2003/11/13 17:32:24  joan
// work on CR
//
// Revision 1.11  2003/11/12 23:23:06  joan
// work on CR
//
// Revision 1.10  2003/10/20 17:42:33  joan
// fb fixes
//
// Revision 1.9  2003/10/14 19:57:18  joan
// work on fb
//
// Revision 1.8  2003/10/06 23:34:21  joan
// work on LS ABR
//
// Revision 1.7  2003/09/15 21:39:29  joan
// change to Extract GA
//
// Revision 1.6  2003/09/09 22:07:29  dave
// removing profile links in attribute creation
//
// Revision 1.5  2003/09/04 21:55:18  joan
// fix fb
//
// Revision 1.4  2003/08/13 22:43:30  joan
// fix report
//
// Revision 1.3  2003/08/05 20:45:41  joan
// fix bug
//
// Revision 1.2  2003/08/05 15:30:29  joan
// fix error
//
// Revision 1.1  2003/08/04 22:23:08  joan
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

public class ALWRCSOLABR002PDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList();
	private Vector m_countriesVec = new Vector();
	private Vector m_countriesDescVec = new Vector();
	private String m_strPartNumber = null;

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: ALWRCSOLABR002PDG.java,v 1.16 2008/09/04 20:38:13 wendy Exp $");
  	}


	public ALWRCSOLABR002PDG(EANMetaFoundation  _mf, ALWRCSOLABR002PDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public ALWRCSOLABR002PDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("ALWRCSOLABR002PDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "ALWRCSOLABR002PDG";
  	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " ALWRCSOLABR002PDG executeProduct method";
		StringBuffer sbReturn = new StringBuffer();

		StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/PCDPDG3.txt";

		ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "EXTOFPDG1");

		// get GENERALAREA entity based on CSOL.GENAREANAME
		ExtractActionItem xaiGA = new ExtractActionItem(null, _db, _prof, "EXTCSOLGAA2");
		EntityItem[] aei = {m_eiPDG};
		EntityList el = EntityList.getEntityList(_db, _prof, xaiGA, aei);
		EntityGroup egGA = el.getEntityGroup("GENERALAREA");

		for (int i=0; i < m_countriesVec.size(); i++) {
			String strGENAREANAMECTYF = (String)m_countriesVec.elementAt(i);

			EntityItem eiGA = m_utility.getGA(egGA, strGENAREANAMECTYF);
			_db.test(eiGA != null, "GENERALAREA entity is null for " + strGENAREANAMECTYF);

			String strGeoDesc = (String)m_countriesDescVec.elementAt(i);
			OPICMList infoList = new OPICMList();
			infoList.put("CSOL", m_eiPDG);
			infoList.put("GAPARENT", eiGA);
			infoList.put("OF", m_eiRoot);
			infoList.put("GENAREANAMECTYF", strGENAREANAMECTYF);

			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, xai, strFileName);
			StringBuffer sbMissing = pdgObject.getMissingEntities();
			if (_bGenData) {
				if (sbMissing.toString().length() > 0) {
					//D.ebug(D.EBUG_SPEW,strTraceBase  +  " sbMissing: " + sbMissing.toString());
					generateData(_db, _prof, sbMissing,"");
					if (m_sbActivities.toString().length() > 0) {
						//D.ebug(D.EBUG_SPEW,strTraceBase + " m_sbActivities : "+ m_sbActivities.toString());
						sb.append("<br/><LI>Country Solution: " + adjustActivities(m_sbActivities.toString()));
						//D.ebug(D.EBUG_SPEW,strTraceBase  +  " sb adjust : " + sb.toString());
						m_sbActivities = new StringBuffer();
					} else {
						sb.append("<LI>CSOL already exists Part Number = " + m_strPartNumber + " and General Area = " + strGeoDesc);
					}
				} else {
					sb.append("<LI>CSOL already exists Part Number = " + m_strPartNumber + " and General Area = " + strGeoDesc);
				}
			}
		}
		m_sbActivities = sb;
		// change CSOL's ALWRSTATUS to Complete
		OPICMList infoList = new OPICMList();
		infoList.put("ALWRSTATUS", "ALWRSTATUS=ALWR0030");
		m_utility.updateAttribute(_db, _prof, m_eiPDG, infoList);

		return sbReturn;
	}

	private String adjustActivities(String _s) {

		String strReturn = "";
		StringTokenizer st = new StringTokenizer(_s, "\n");

		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			int iCreate = s.indexOf("Create");
			if (iCreate >= 0) {
				int i = s.indexOf("<ENTITYDISPLAY>");
				if (i >= 0) {
					s = s.substring(i + 15);
				}

				int j = s.indexOf("</ENTITYDISPLAY>");
				if (j >= 0) {
					s = s.substring(0, j);
				}
				return s;
			}
		}
		return strReturn;

	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _eiCSOL) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
		for (int i =0; i < _eiCSOL.getAttributeCount(); i++) {
			EANAttribute att = _eiCSOL.getAttribute(i);
			String textAtt = "";
			//String sFlagAtt = "";
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
					//		sFlagAtt = amf[f].getLongDescription().trim();
						//	sFlagClass = amf[f].getFlagCode().trim();
				//			index = f;
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

			if (att.getKey().equals("ALWRCTYALL")) {
				m_countriesVec = mFlagCode;
				m_countriesDescVec = mFlagAtt;
			} else if (att.getKey().equals("PNUMB_CT")) {
				m_strPartNumber = textAtt;
			}
		}

		if (m_countriesVec.size() <=0) {
			m_SBREx.add("The attribute ALWRCTYALL not populated.");
		}
	}

	protected void resetVariables() {
		//m_vctReturnEntityKeys = new Vector();
		//m_opList = new EANList();
		m_countriesVec = new Vector();
		m_countriesDescVec = new Vector();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " ALWRCSOLABR002PDG viewMissingEntities method";
		return null;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " ALWRCSOLABR002PDG executeAction method ";
		m_SBREx = new SBRException();
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				D.ebug(D.EBUG_SPEW,"CSOL entity is null");
				return;
			}

			checkPDGAttribute(_db, _prof, m_eiPDG);
			// validate data
			checkDataAvailability(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				throw m_SBREx;
			}

			checkMissingData(_db, _prof, true).toString();
		} catch (SBRException ex) {
			throw ex;
		}
	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		_prof = m_utility.setProfValOnEffOn(_db, _prof);

		EntityGroup eg = m_ABReList.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
		m_eiList.put("CSOL", m_eiPDG);

		// get OF
		eg = m_ABReList.getEntityGroup("OF");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "Offering entity is null");
	}
}
