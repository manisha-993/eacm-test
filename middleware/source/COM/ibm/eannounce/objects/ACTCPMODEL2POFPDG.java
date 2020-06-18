//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ACTCPMODEL2POFPDG.java,v $
// Revision 1.13  2008/09/04 19:40:01  wendy
// Cleanup RSA warnings
//
// Revision 1.12  2006/02/20 21:39:44  joan
// clean up System.out.println
//
// Revision 1.11  2005/01/13 03:10:45  joan
// fixes
//
// Revision 1.10  2005/01/13 02:20:11  joan
// work on copy relator
//
// Revision 1.9  2005/01/12 22:21:26  joan
// fixes
//
// Revision 1.8  2005/01/12 01:38:29  joan
// fixes
//
// Revision 1.7  2004/11/29 23:41:03  bala
// call generateII instead of generate
//
// Revision 1.6  2004/09/16 21:29:25  bala
// get WG as root entity
//
// Revision 1.5  2004/09/16 21:16:30  bala
// changing the object to use the new generate (testpdgII)
//
// Revision 1.4  2004/09/10 22:06:42  bala
// fixes
//
// Revision 1.3  2004/09/10 19:55:25  bala
// fix for nullpointer
//
// Revision 1.2  2004/09/10 19:24:43  bala
// add getParentEntityIds betw FEATURE and MODEL
//
// Revision 1.1  2004/09/09 22:08:07  bala
// check in
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

public class ACTCPMODEL2POFPDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList();
	//private EntityItem m_eiFEATURE = null;

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: ACTCPMODEL2POFPDG.java,v 1.13 2008/09/04 19:40:01 wendy Exp $");
  	}


	public ACTCPMODEL2POFPDG(EANMetaFoundation  _mf, ACTCPMODEL2POFPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public ACTCPMODEL2POFPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("ACTCPMODEL2POFPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "ACTCPMODEL2POFPDG";
  	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " ACTCPMODEL2POFPDG checkMissingData method";
			_db.debug(D.EBUG_DETAIL, strTraceBase+_bGenData);
		StringBuffer sbReturn = new StringBuffer();

		//StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/HWCPMODELTOPOF.txt";

		OPICMList infoList = new OPICMList();
		infoList.put("MODEL", m_eiPDG);
		String strAttributes = "map_INTERNALNAME=Copy of " + m_utility.getAttrValue(m_eiPDG, "INTERNALNAME");

		EntityGroup egFEATURE = m_ABReList.getEntityGroup("FEATURE");
		_db.test(egFEATURE != null, "checkMissingData:EntityGroup FEATURE is null");

		EntityGroup egPOF = m_ABReList.getEntityGroup("POF");
		EntityGroup egPRODSTRUCT = m_ABReList.getEntityGroup("PRODSTRUCT");
    	for (int i =0; i < egFEATURE.getEntityItemCount(); i++) {
			// this is to find the already copied MODEL
			EntityItem eiMODEL = m_utility.findEntityItem(m_eiList, "MODEL", strAttributes);

			EntityItem eiFEATURE = egFEATURE.getEntityItem(i);
			infoList.put("FEATURE", eiFEATURE);
			m_eiList.put("FEATURE", eiFEATURE);

			for (int k=0; k < egPRODSTRUCT.getEntityItemCount(); k++) {
				EntityItem eiPRODSTRUCT = egPRODSTRUCT.getEntityItem(k);
				EntityItem eiP = (EntityItem)eiPRODSTRUCT.getUpLink(0);
				EntityItem eiC = (EntityItem)eiPRODSTRUCT.getDownLink(0);
				if (eiP == null || eiC == null) {
					D.ebug(D.EBUG_SPEW,strTraceBase + " eiPRODSTRUCT doesn't have eiP or eiC");
					continue;
				}
				if (eiC.getKey().equals(m_eiPDG.getKey()) && eiP.getKey().equals(eiFEATURE.getKey())) {
					infoList.put("PRODSTRUCT", eiPRODSTRUCT);
					m_eiList.put("PRODSTRUCT", eiPRODSTRUCT);
				}
			}

			Vector vPOF = m_utility.getChildrenEntityIds(m_ABReList, eiFEATURE.getEntityType(), eiFEATURE.getEntityID(), "POF", "FCPOF");
		    if (vPOF.size() > 0) {
				_db.debug(D.EBUG_DETAIL, strTraceBase+"POF found!!");
			    for (int j=0; j < vPOF.size(); j++) {
			        int iPofID = ((Integer)vPOF.elementAt(j)).intValue();
			        EntityItem eiPOF = egPOF.getEntityItem(egPOF.getEntityType() + iPofID);

			        infoList.put("POF", eiPOF);
			        m_eiList.put("POF", eiPOF);

			        _prof = m_utility.setProfValOnEffOn(_db, _prof);
			        TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, m_PDGxai, strFileName);
			        StringBuffer sbMissing = pdgObject.getMissingEntities();
			        sbReturn.append(sbMissing.toString());
			        if (_bGenData && sbMissing.toString().length() > 0) {
				        generateDataII(_db, _prof, sbMissing,"");
			        }
			    }
		    } else {
      			// no physical offering
				_db.debug(D.EBUG_DETAIL, strTraceBase+" No POF found");
      			_prof = m_utility.setProfValOnEffOn(_db, _prof);
      			TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, m_PDGxai, strFileName);
      			StringBuffer sbMissing = pdgObject.getMissingEntities();
      			sbReturn.append(sbMissing.toString());
				_db.debug(D.EBUG_DETAIL, strTraceBase+" getmissingEntities returned:"+sbMissing.toString());
      			if (_bGenData && sbMissing.toString().length() > 0) {
      				generateDataII(_db, _prof, sbMissing,"");
      			}
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
		//String strTraceBase = " ACTCPMODEL2POFPDG viewMissing method";
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
		String strTraceBase = " ACTCPMODEL2POFPDG executeAction method ";
		m_SBREx = new SBRException();
		String strData = "";
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				D.ebug(D.EBUG_SPEW,"MODEL entity is null");
				return;
			}

			// validate data
			checkDataAvailability(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				throw m_SBREx;
			}

			_db.debug(D.EBUG_DETAIL, "Before checkMissing Data");
			strData = checkMissingData(_db, _prof, true).toString();
			_db.debug(D.EBUG_DETAIL, "After checkMissing Data");
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
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());        //This will be MODEL, since the PDG is launched  from the MODEL
		m_eiList.put("MODEL", m_eiPDG);

    	EntityGroup egWG = m_ABReList.getEntityGroup("WG");
		_db.test(egWG != null, "checkMissingData:EntityGroup WG is null");

    	m_eiRoot = egWG.getEntityItem(0);                                 //This is wg
	}
}
