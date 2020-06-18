//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ALWRCVARABR001PDG.java,v $
// Revision 1.16  2008/09/04 20:40:09  wendy
// Cleanup RSA warnings
//
// Revision 1.15  2006/02/14 16:59:11  joan
// some adjustments
//
// Revision 1.14  2005/04/28 21:15:36  joan
// fixes
//
// Revision 1.13  2003/12/05 00:26:37  joan
// add CD
//
// Revision 1.12  2003/11/25 20:34:04  joan
// adjust copy
//
// Revision 1.11  2003/11/13 20:53:23  joan
// work on CR
//
// Revision 1.10  2003/11/13 17:32:24  joan
// work on CR
//
// Revision 1.9  2003/10/20 17:42:34  joan
// fb fixes
//
// Revision 1.8  2003/10/14 19:57:18  joan
// work on fb
//
// Revision 1.7  2003/10/13 21:29:00  joan
// fix the report
//
// Revision 1.6  2003/10/06 23:34:21  joan
// work on LS ABR
//
// Revision 1.5  2003/09/15 21:39:30  joan
// change to Extract GA
//
// Revision 1.4  2003/09/09 22:07:29  dave
// removing profile links in attribute creation
//
// Revision 1.3  2003/09/04 21:55:18  joan
// fix fb
//
// Revision 1.2  2003/08/14 20:03:10  joan
// fix report
//
// Revision 1.1  2003/08/13 22:44:12  joan
// initial load
//
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

public class ALWRCVARABR001PDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList();
	private EANList m_cdList = new EANList();
	private EntityItem m_eiCDG = null;
	private EntityItem m_eiCDCurrent = null;

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: ALWRCVARABR001PDG.java,v 1.16 2008/09/04 20:40:09 wendy Exp $");
  	}


	public ALWRCVARABR001PDG(EANMetaFoundation  _mf, ALWRCVARABR001PDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public ALWRCVARABR001PDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("ALWRCVARABR001PDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "ALWRCVARABR001PDG";
  	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " ALWRCVARABR001PDG executeProduct method";
		StringBuffer sbReturn = new StringBuffer();

		StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/PCDPDG4.txt";

		ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "EXTVARPDG2");
		ExtractActionItem xaiGA = new ExtractActionItem(null, _db, _prof, "EXTCDGENERALAREA1");
		for (int i=0; i < m_cdList.size(); i++) {
			m_eiCDCurrent = (EntityItem)m_cdList.getAt(i);
			m_eiList.put(m_eiCDCurrent);

			// get GENERALAREA entity based on CD.GENAREANAME

			EntityItem[] aei = {m_eiCDCurrent};
			EntityList el = EntityList.getEntityList(_db, _prof, xaiGA, aei);
			EntityGroup egGA = el.getEntityGroup("GENERALAREA");

			sb.append("<br/>Country Designator: " + m_eiCDCurrent.toString());

			EANFlagAttribute attF = (EANFlagAttribute) m_eiCDCurrent.getAttribute("GENAREANAMECTYF");
			if (attF != null) {
				MetaFlag[] amf = (MetaFlag[])attF.get();
				boolean bPopulated = false;
				for (int x=0; x < amf.length; x++) {
					MetaFlag mf = amf[x];
					if (mf.isSelected()) {
						bPopulated = true;

						String strGENAREANAMECTYF = mf.getFlagCode();
						EntityItem eiGA = m_utility.getGA(egGA, strGENAREANAMECTYF);
						_db.test(eiGA != null, "GENERALAREA entity is null for " + strGENAREANAMECTYF);

						OPICMList infoList = new OPICMList();
						infoList.put("CVAR", m_eiPDG);
						infoList.put("GAPARENT", eiGA);
						infoList.put("VAR", m_eiRoot);
						infoList.put("CD_GENAREANAMECTYF", strGENAREANAMECTYF);
						infoList.put("CD", m_eiCDCurrent);

						EANAttribute att = m_eiPDG.getAttribute("PNUMB_CT");
						String strCVAR_T1 = "";
						if (att != null) {
							String s = att.toString();
							infoList.put("CVAR_T1", s.substring(0,5));
							strCVAR_T1 = s.substring(0,5);
						}

						att = m_eiCDCurrent.getAttribute("COUNTRY_DESIG");
						String strCD_T1 = "";
						if (att != null) {
							String s = att.toString();
							infoList.put("CD_T1", s.substring(0,2));
							strCD_T1 = s.substring(0,2);
						}

						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, xai, strFileName);
						StringBuffer sbMissing = pdgObject.getMissingEntities();
						if (_bGenData) {
							if (sbMissing.toString().length() > 0) {
								generateData(_db, _prof, sbMissing,"");
								if (m_sbActivities.toString().length() > 0) {
									sb.append("<br/><LI>Country Variant: " + adjustActivities(m_sbActivities.toString()));
									m_sbActivities = new StringBuffer();
								} else {
									sb.append("<br /><LI>CVAR already exists for Part Number =" + strCVAR_T1 + strCD_T1 + " and General Area = " + mf.getLongDescription());
								}
							} else {
								sb.append("<br /><LI>CVAR already exists for Part Number = " + strCVAR_T1 + strCD_T1 + " and General Area = " + mf.getLongDescription());
							}
						}
					}
				}

				if (!bPopulated) {
					sb.append("<br/><LI>No CVAR generated since the CD's GENAREANAMECTYF not populated.</UL>");
				}
			} else {
				sb.append("<br/><LI>No CVAR generated since the CD's GENAREANAMECTYF not populated.</UL>");
			}
		}

		m_sbActivities = sb;
		// change CVAR's ALWRSTATUS to Complete
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

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
	}

	protected void resetVariables() {
		//m_vctReturnEntityKeys = new Vector();
		//m_opList = new EANList();
		m_cdList = new EANList();
		m_eiCDG = null;
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " ALWRCVARABR001PDG viewMissingEntities method";
		return null;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " ALWRCVARABR001PDG executeAction method ";
		m_SBREx = new SBRException();
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				System.out.println("CVAR entity is null");
				return;
			}

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
		m_eiList.put("CVAR", m_eiPDG);

		// get VAR
		eg = m_ABReList.getEntityGroup("VAR");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "Variant entity is null");

		// get CDG entities
		eg = m_ABReList.getEntityGroup("CDG");
		if (eg != null) {
			m_eiCDG = eg.getEntityItem(0);
		}

		// get CD entities
		eg = m_ABReList.getEntityGroup("CDGCD");
		if (eg != null) {
			//boolean bFound = false;
			for (int i=0; i < eg.getEntityItemCount(); i++) {
				EntityItem ei = eg.getEntityItem(i);
				EntityItem eiu = (EntityItem)ei.getUpLink(0);
				EntityItem eid = (EntityItem)ei.getDownLink(0);
				if (eiu.getKey().equals(m_eiCDG.getKey())) {
					m_cdList.put(eid);
				}
			}
		}
	}
}
