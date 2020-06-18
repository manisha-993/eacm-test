//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: HWCOPYOFTOPOFPDG.java,v $
// Revision 1.7  2008/09/04 21:09:05  wendy
// Cleanup RSA warnings
//
// Revision 1.6  2003/12/10 21:01:03  joan
// adjust for ExcludeCopy
//
// Revision 1.5  2003/11/25 20:34:05  joan
// adjust copy
//
// Revision 1.4  2003/11/20 21:35:02  joan
// some changes
//
// Revision 1.3  2003/11/19 00:24:10  joan
// fix errors
//
// Revision 1.2  2003/11/18 19:01:50  joan
// fix constructor for extract
//
// Revision 1.1  2003/11/18 18:13:42  joan
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


public class HWCOPYOFTOPOFPDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList();
	private EntityItem m_eiFUP = null;

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: HWCOPYOFTOPOFPDG.java,v 1.7 2008/09/04 21:09:05 wendy Exp $");
  	}


	public HWCOPYOFTOPOFPDG(EANMetaFoundation  _mf, HWCOPYOFTOPOFPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public HWCOPYOFTOPOFPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("HWCOPYOFTOPOFPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "HWCOPYOFTOPOFPDG";
  	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " HWCOPYOFTOPOFPDG checkMissingData method";
		StringBuffer sbReturn = new StringBuffer();

		//StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/HWCOPYOFTOPOF1.txt";

		OPICMList infoList = new OPICMList();
		infoList.put("OFPROJ", m_eiRoot);
		infoList.put("OOF", m_eiPDG);
		infoList.put("FUP", m_eiFUP);

		EntityGroup egGrp = m_ABReList.getEntityGroup("FUPPOFMGMTGRP");
		EntityGroup egPOF = m_ABReList.getEntityGroup("PHYSICALOF");
		Vector vGrp = m_utility.getChildrenEntityIds(m_ABReList, m_eiFUP.getEntityType(), m_eiFUP.getEntityID(), "FUPPOFMGMTGRP", "FUPOWNSPOFOMG");

		if (vGrp.size() > 0) {
			for (int i=0; i < vGrp.size(); i++) {
				int iGrpID = ((Integer)vGrp.elementAt(i)).intValue();
				EntityItem eiGrp = egGrp.getEntityItem(egGrp.getEntityType() + iGrpID);

				infoList.put("GRP", eiGrp);
				m_eiList.put("GRP", eiGrp);

				Vector vPOF = m_utility.getChildrenEntityIds(m_ABReList, eiGrp.getEntityType(), eiGrp.getEntityID(), "PHYSICALOF", "POFMEMBERFUPOMG");
				if (vPOF.size() > 0) {
					for (int j=0; j < vPOF.size(); j++) {
						int iPofID = ((Integer)vPOF.elementAt(i)).intValue();
						EntityItem eiPOF = egPOF.getEntityItem(egPOF.getEntityType() + iPofID);

						infoList.put("POF", eiPOF);
						m_eiList.put("POF", eiPOF);

						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, m_PDGxai, strFileName);
						StringBuffer sbMissing = pdgObject.getMissingEntities();
						sbReturn.append(sbMissing.toString());
						if (_bGenData && sbMissing.toString().length() > 0) {
							generateData(_db, _prof, sbMissing,"");
						}
					}
				} else {
					// no physical offering
					_prof = m_utility.setProfValOnEffOn(_db, _prof);
					TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, m_PDGxai, strFileName);
					StringBuffer sbMissing = pdgObject.getMissingEntities();
					sbReturn.append(sbMissing.toString());
					if (_bGenData && sbMissing.toString().length() > 0) {
						generateData(_db, _prof, sbMissing,"");
					}
				}
			}
		} else {
			// no management group
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, m_PDGxai, strFileName);
			StringBuffer sbMissing = pdgObject.getMissingEntities();
			sbReturn.append(sbMissing.toString());
			if (_bGenData && sbMissing.toString().length() > 0) {
				generateData(_db, _prof, sbMissing,"");
			}
		}

		return sbReturn;
	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
	}

	protected void resetVariables() {
		//m_vctReturnEntityKeys = new Vector();
		//m_opList = new EANList();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " HWCOPYOFTOPOFPDG viewMissing method";
		return null;
	}

	private String adjustActivities(String _s) {
		StringTokenizer st = new StringTokenizer(_s, "\n");
		String sReturn = "";
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			int i = s.indexOf("<ENTITYDISPLAY>");
			if (i >= 0) {
				s = s.substring(i + 15);
			}

			int j = s.indexOf("</ENTITYDISPLAY>");
			if (j >= 0) {
				s = s.substring(0, j);
			}
			sReturn += "<br/>" + s;
		}

		return sReturn;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " HWCOPYOFTOPOFPDG executeAction method ";
		m_SBREx = new SBRException();
		String strData = "";
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				System.out.println("OOF entity is null");
				return;
			}

			// validate data
			checkDataAvailability(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				throw m_SBREx;
			}

			strData = checkMissingData(_db, _prof, true).toString();
			m_sbActivities.append(adjustActivities(m_utility.getActivities().toString()));
		} catch (SBRException ex) {
			throw ex;
		}
		if (strData.length() <= 0) {
			m_SBREx.add("Generating data is complete.  No data created during this run. (ok)");
			throw m_SBREx;
		}
	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		_prof = m_utility.setProfValOnEffOn(_db, _prof);

		EntityGroup eg = m_ABReList.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());

		// get Offering Project
		EANAttribute att = m_eiPDG.getAttribute("AFSESSIONENTITY");
		if (att != null) {
			String s = att.toString();
			int i = s.indexOf(":");
			if (i > 0) {
				String strEntityType = s.substring(0, i);
				int iEntityID = Integer.parseInt(s.substring(i+1));
				EntityGroup egProj = new EntityGroup(null, _db, _prof, strEntityType, "Edit", false);
				m_eiRoot = new EntityItem(egProj, _prof, _db, strEntityType, iEntityID);
			}
		}
		_db.test(m_eiRoot != null, "Offering Project is null");

		// get FUP
		Vector v = m_utility.getChildrenEntityIds(m_ABReList, m_eiPDG.getEntityType(), m_eiPDG.getEntityID(), "FUP", "OOFFUP");
		eg = m_ABReList.getEntityGroup("FUP");
		if (v.size() > 0) {
			int iID = ((Integer)v.elementAt(0)).intValue();
			m_eiFUP = eg.getEntityItem(eg.getEntityType() + iID);
		}
		_db.test(m_eiFUP != null, "Function Point is null");

		m_eiList.put("OOF", m_eiPDG);
		m_eiList.put(m_eiRoot);
		m_eiList.put("FUP", m_eiFUP);
	}
}
