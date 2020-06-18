//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SWCOPYBILLINGPDG.java,v $
// Revision 1.8  2008/09/05 21:25:47  wendy
// Cleanup RSA warnings
//
// Revision 1.7  2006/02/20 22:35:33  joan
// clean up system.out
//
// Revision 1.6  2004/03/12 23:19:12  joan
// changes from 1.2
//
// Revision 1.5  2004/02/13 18:10:26  joan
// fix bug
//
// Revision 1.4  2004/02/12 23:42:06  joan
// fix bug
//
// Revision 1.3  2004/01/05 22:33:25  joan
// adjust code
//
// Revision 1.2  2004/01/05 18:44:45  joan
// adjust code
//
// Revision 1.1  2003/12/23 18:44:31  joan
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

public class SWCOPYBILLINGPDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList();
	private EntityItem m_eiBCOF = null;

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: SWCOPYBILLINGPDG.java,v 1.8 2008/09/05 21:25:47 wendy Exp $");
  	}


	public SWCOPYBILLINGPDG(EANMetaFoundation  _mf, SWCOPYBILLINGPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public SWCOPYBILLINGPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("SWCOPYBILLINGPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "SWCOPYBILLINGPDG";
  	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SWCOPYBILLINGPDG checkMissingData method";
		D.ebug(D.EBUG_SPEW,strTraceBase);
		StringBuffer sbReturn = new StringBuffer();

		//StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/SWCOPYBILLING01.txt";

		OPICMList infoList = new OPICMList();
		infoList.put("COF", m_eiPDG);
//		D.ebug(D.EBUG_SPEW,strTraceBase + " m_eiPDG: " + m_eiPDG.dump(false));
		infoList.put("OFPROJ", m_eiRoot);
		if (m_eiBCOF != null) {
			infoList.put("BCOF", m_eiBCOF);
		}

		EntityGroup egOOF = m_ABReList.getEntityGroup("ORDEROF");
		EntityGroup egFUP = m_ABReList.getEntityGroup("FUP");
		Vector vOOF = m_utility.getChildrenEntityIds(m_ABReList, m_eiPDG.getEntityType(), m_eiPDG.getEntityID(), "ORDEROF", "AFCOFOOF");

		if (vOOF.size() > 0) {
			//D.ebug(D.EBUG_SPEW,strTraceBase + " 00");
			for (int i=0; i < vOOF.size(); i++) {
				int iOOFID = ((Integer)vOOF.elementAt(i)).intValue();
				EntityItem eiOOF = egOOF.getEntityItem(egOOF.getEntityType() + iOOFID);
				//D.ebug(D.EBUG_SPEW,strTraceBase + eiOOF.dump(false));
				infoList.put("OOF", eiOOF);
				m_eiList.put(eiOOF);

				Vector vFUP = m_utility.getChildrenEntityIds(m_ABReList, eiOOF.getEntityType(), eiOOF.getEntityID(), "FUP", "OOFFUP");
				if (vFUP.size() > 0) {
					//D.ebug(D.EBUG_SPEW,strTraceBase + " 01");
					for (int j=0; j < vFUP.size(); j++) {
						int iFupID = ((Integer)vFUP.elementAt(j)).intValue();
						EntityItem eiFUP = egFUP.getEntityItem(egFUP.getEntityType() + iFupID);
					//	D.ebug(D.EBUG_SPEW,strTraceBase + eiFUP.dump(false));
						infoList.put("FUP", eiFUP);
						m_eiList.put(eiFUP);

						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						TestPDG pdgObject = new TestPDG(_db, _prof, m_eiPDG, infoList, m_PDGxai, strFileName);
						StringBuffer sbMissing = pdgObject.getMissingEntities();
						sbReturn.append(sbMissing.toString());
						if (_bGenData && sbMissing.toString().length() > 0) {
							generateData(_db, _prof, sbMissing,"");
						}
					}
				} else {
					//D.ebug(D.EBUG_SPEW,strTraceBase + " 02");
					// no Function Points
					_prof = m_utility.setProfValOnEffOn(_db, _prof);
					TestPDG pdgObject = new TestPDG(_db, _prof, m_eiPDG, infoList, m_PDGxai, strFileName);
					StringBuffer sbMissing = pdgObject.getMissingEntities();
					sbReturn.append(sbMissing.toString());
					if (_bGenData && sbMissing.toString().length() > 0) {
						generateData(_db, _prof, sbMissing,"");
					}
				}

				infoList.remove("OOF");
				infoList.remove("FUP");
				EntityItem[] aeic = {eiOOF};
				D.ebug(D.EBUG_SPEW,"calling removeLink");
				m_utility.removeLink(_db, _prof,m_ABReList, m_eiPDG, aeic, "AFCOFOOF");
			}

		} else {
			D.ebug(D.EBUG_SPEW,strTraceBase + " no ORDEROF");
			D.ebug(D.EBUG_SPEW,"m_ABReList: " + m_ABReList.dump(false));
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
		//String strTraceBase = " SWCOPYBILLINGPDG viewMissing method";
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
		String strTraceBase = " SWCOPYBILLINGPDG executeAction method ";
		m_SBREx = new SBRException();
		String strData = "";
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				D.ebug(D.EBUG_SPEW,"COMMERCIALOF entity is null");
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

		// get Base COMMERCIALOF
		eg = m_ABReList.getEntityGroup("COMMERCIALOF");
		if (eg != null) {
			EANList tempList = new EANList();
			StringBuffer sb = new StringBuffer();

			sb.append("map_COFCAT=101;");
			sb.append("map_COFGRP=300");
			for (int i=0; i < eg.getEntityItemCount(); i++) {
				EntityItem ei = eg.getEntityItem(i);
				tempList.put(ei);
			}
			m_eiBCOF = m_utility.findEntityItem(tempList, "COMMERCIALOF", sb.toString());
			if (m_eiBCOF != null) {
				m_eiList.put(m_eiBCOF);
			}
		}

		m_eiList.put(m_eiPDG);
		m_eiList.put(m_eiRoot);
	}
}
