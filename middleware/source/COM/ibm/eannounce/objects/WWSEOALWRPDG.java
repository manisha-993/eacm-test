//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: WWSEOALWRPDG.java,v $
// Revision 1.6  2008/09/08 17:32:47  wendy
// Cleanup RSA warnings
//
// Revision 1.5  2006/02/20 22:35:34  joan
// clean up system.out
//
// Revision 1.4  2006/02/09 20:42:26  joan
// fixes
//
// Revision 1.3  2005/08/02 20:28:25  joan
// fixes
//
// Revision 1.2  2005/07/28 21:10:49  joan
// fixes
//
// Revision 1.1  2005/07/27 22:27:30  joan
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

public class WWSEOALWRPDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();

	private StringBuffer m_sbFeatures = new StringBuffer();
	//private String m_strCD = null;
	private String m_strSEOID = null;
	//private String m_strXXPARTNO = null;
	private OPICMList m_FCList = new OPICMList();
	private OPICMList m_prodstructList = new OPICMList();

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: WWSEOALWRPDG.java,v 1.6 2008/09/08 17:32:47 wendy Exp $");
  	}


	public WWSEOALWRPDG(EANMetaFoundation  _mf, WWSEOALWRPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public WWSEOALWRPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("WWSEOALWRPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "WWSEOALWRPDG";
  	}

	private void linkToProdstruct(Database _db, Profile _prof, EANList _eiList, EntityItem _eiProdstruct, String _strRelatorInfo) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = "WWSEOABRALWRPDG linkToProdstruct method";
		D.ebug(D.EBUG_SPEW,strTraceBase);
		EntityItem[] aeic = {_eiProdstruct};

 		for (int i=0; i < _eiList.size(); i++) {
			EntityItem ei = (EntityItem)_eiList.getAt(i);
		//	EntityItem[] aeip = {ei};
			m_utility.linkEntities(_db, _prof, ei, aeic, _strRelatorInfo);
		}
	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " WWSEOALWRPDG checkMissingData method";
		StringBuffer sbReturn = new StringBuffer();

		//StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/WWSEOALWR2.txt";
		String strSEOID = m_strSEOID;

		OPICMList infoList = new OPICMList();
		infoList.put("WWSEO", m_eiRoot);
		infoList.put("WWSEOALWR", m_eiPDG);
		infoList.put("SEOID", strSEOID);


		m_eiList = new EANList();
		m_eiList.put(m_eiRoot);
		m_eiList.put(m_eiPDG);

		TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, m_PDGxai, strFileName);
		StringBuffer sbMissing = pdgObject.getMissingEntities();
		pdgObject = null;
		infoList = null;

		if (_bGenData) {
			if (sbMissing.toString().length() > 0) {
				_db.debug(D.EBUG_DETAIL, strTraceBase + " generating data");
				generateData(_db, _prof, sbMissing, "");
				//linkToProdstruct(_db, _prof, getSavedEIList(), eiProd, strRelatorInfo);
				EANList eiList = getSavedEIList();
				for (int l=0; l < eiList.size(); l++) {
					EntityItem eiLSEO = (EntityItem)eiList.getAt(l);
					sbReturn.append("Create LSEO: " + eiLSEO.toString() + "</br>");
				}
			}
		}

		EntityItem[] aei = {m_eiRoot};
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aei);
		EntityGroup eg = el.getEntityGroup("LSEOPRODSTRUCT");
		_db.debug(D.EBUG_DETAIL, strTraceBase + " create links to Prodstruct");
		for (int i =0; i < m_FCList.size(); i++) {
			String str = (String)m_FCList.getAt(i);
			String strFeature = "";
			String strQuantity = "";
			String strRelatorInfo = "";
			int iQ = str.indexOf(":");
			if (iQ >= 0) {
				strFeature = str.substring(0, iQ);
				strQuantity = str.substring(iQ +1);
			} else {
				strFeature = str;
			}
			strRelatorInfo="LSEOPRODSTRUCT[CONFQTY=" + strQuantity + "]";
			EntityItem eiProd = (EntityItem)m_prodstructList.get(strFeature);
			if (eiProd != null) {
				boolean bFound1 = false;
				_db.debug(D.EBUG_DETAIL, strTraceBase + " checking existing relator");
				for (int k=0; k < eg.getEntityItemCount(); k++) {
					EntityItem ei = eg.getEntityItem(k);
					EntityItem eip = (EntityItem) ei.getUpLink(0);
					EntityItem eic = (EntityItem) ei.getDownLink(0);
					String strValue = m_utility.getAttrValue(eip, "SEOID");
					if (strValue.equals(strSEOID) && eic.getKey().equals(eiProd.getKey())) {
						OPICMList attList = new OPICMList();
						attList.put("CONFQTY", "CONFQTY=" + strQuantity);
						m_utility.updateAttribute(_db, _prof, ei, attList);
						bFound1 = true;
					}
				}
				_db.debug(D.EBUG_DETAIL, strTraceBase + " bFound1: " + bFound1);
				if (!bFound1) {
					EntityGroup egLSEO = el.getEntityGroup("LSEO");
					for (int k=0; k < egLSEO.getEntityItemCount(); k++) {
						EntityItem eiLSEO = egLSEO.getEntityItem(k);
						_db.debug(D.EBUG_DETAIL, strTraceBase + " checking eiLSEO: " + eiLSEO.getKey() );
						String strValue = m_utility.getAttrValue(eiLSEO, "SEOID");
						if (strValue.equals(strSEOID)) {
							EANList eiList = new EANList();
							eiList.put(eiLSEO);
							linkToProdstruct(_db, _prof, eiList, eiProd, strRelatorInfo);
						}
					}
				}
			}
		}

		// remove links from LSEO to unwanted PRODSTRUCT
		_db.debug(D.EBUG_DETAIL, strTraceBase + "remove links to unwanted Prodstruct");
		for (int i=0; i < eg.getEntityItemCount(); i++) {
			EntityItem ei = eg.getEntityItem(i);
			EntityItem eip = (EntityItem) ei.getUpLink(0);
			EntityItem eic = (EntityItem) ei.getDownLink(0);
			String strValue = m_utility.getAttrValue(eip, "SEOID");
			if (strValue.equals(strSEOID)) {
				boolean bFound = false;
				for (int j=0; j < m_prodstructList.size(); j++) {
					EntityItem eiprod = (EntityItem) m_prodstructList.getAt(j);
					if (eiprod.getKey().equals(eic.getKey())) {
						bFound = true;
						break;
					}
				}

				if (!bFound) {
					EntityItem[] aeic = {eic};
					m_utility.removeLink(_db, _prof, el, eip, aeic, "LSEOPRODSTRUCT");
				}
			}
		}

		return sbReturn;
	}
/*
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

	}*/

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
    String strTraceBase = " WWSEOALWRPDG checkPDGAttribute method";
    _db.debug(D.EBUG_DETAIL, strTraceBase);
    _db.debug(D.EBUG_DETAIL, strTraceBase + ":Entity parm is " + _afirmEI.getKey() + ": which has :" + _afirmEI.getAttributeCount());
    for (int i = 0; i < _afirmEI.getAttributeCount(); i++) {
      EANAttribute att = _afirmEI.getAttribute(i);
      _db.debug(D.EBUG_DETAIL, strTraceBase + ":" + att.getAttributeCode());
      String textAtt = "";
     // String sFlagAtt = "";
     // String sFlagClass = "";
      Vector mFlagAtt = new Vector();
      Vector mFlagCode = new Vector();

     // int index = -1;
      if (att instanceof EANTextAttribute) {
        textAtt = ((String) att.get()).trim();
      } else if (att instanceof EANFlagAttribute) {
        if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
          MetaFlag[] amf = (MetaFlag[]) att.get();
          for (int f = 0; f < amf.length; f++) {
            if (amf[f].isSelected()) {
       //       sFlagAtt = amf[f].getLongDescription().trim();
         //     sFlagClass = amf[f].getFlagCode().trim();
           //   index = f;
              break;
            }
          }
        } else if (att instanceof MultiFlagAttribute) {
          MetaFlag[] amf = (MetaFlag[]) att.get();
          for (int f = 0; f < amf.length; f++) {
            if (amf[f].isSelected()) {
              mFlagAtt.addElement(amf[f].getLongDescription().trim());
              mFlagCode.addElement(amf[f].getFlagCode().trim());
            }
          }
        }
      }

      if (att.getKey().equals("CTRYPACKFCLIST")) {
		  m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
      } else if (att.getKey().equals("LANGPACKFCLIST")) {
		  m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
      } else if (att.getKey().equals("PACKAGINGFCLIST")) {
		  m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
      } else if (att.getKey().equals("PUBFCLIST")) {
		  m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
      } else if (att.getKey().equals("OTHERFCLIST")) {
		  m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
      } else if (att.getKey().equals("CD")) {
		  //m_strCD = textAtt;
	  } else if (att.getKey().equals("XXPARTNO")) {
		  //m_strXXPARTNO = textAtt;
	  } else if (att.getKey().equals("SEOID")) {
		  m_strSEOID = textAtt;
	  }
  	}

		if (m_strSEOID == null || m_strSEOID.length() <= 0) {
			m_SBREx.add("SEOID is blank.");
		}

		if (m_sbFeatures.toString().length() <= 0) {
			m_SBREx.add("Feature Lists are blank.");
		}
	}

	protected void resetVariables() {
		//m_vctReturnEntityKeys = new Vector();
		m_sbFeatures = new StringBuffer();
		//m_strCD = null;
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
	//	String strTraceBase = " WWSEOALWRPDG viewMissingEntities method";
		return null;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " WWSEOALWRPDG executeAction method ";
		m_SBREx = new SBRException();
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				System.out.println("WWSEOALWR entity is null");
				return;
			}

			// validate data
			checkDataAvailability(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				throw m_SBREx;
			}

			m_sbActivities = checkMissingData(_db, _prof, true);
		} catch (SBRException ex) {
			throw ex;
		}
	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		_prof = m_utility.setProfValOnEffOn(_db, _prof);

		EntityGroup eg = m_ABReList.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
		m_eiList.put("WWSEOALWR", m_eiPDG);
		checkPDGAttribute(_db, _prof, m_eiPDG);
		// get WWSEO
		eg = m_ABReList.getEntityGroup("WWSEO");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "WWSEO entity is null");

		// get CD entities
		eg = m_ABReList.getEntityGroup("MODEL");
		if (eg != null) {
			if (eg.getEntityItemCount() <= 0) {
				m_SBREx.add("There is no MODEL as parent of WWSEO.");
			} else if (eg.getEntityItemCount() > 1) {
				m_SBREx.add("There are " + eg.getEntityItemCount() + " MODELs as parent of WWSEO.");
			}
		}

		// get the list of features
		String strFeatures = m_sbFeatures.toString();
		StringTokenizer st = new StringTokenizer(strFeatures, ",");
		eg = m_ABReList.getEntityGroup("FEATURE");
		while (st.hasMoreTokens()) {
			String str = st.nextToken().trim();
			String strFeature = "";
		//	String strQuantity = "";
			int iQ = str.indexOf(":");
			if (iQ >= 0) {
				strFeature = str.substring(0, iQ);
			//	strQuantity = str.substring(iQ +1);
			} else {
				strFeature = str;
			}

			m_FCList.put(strFeature, str);

			EntityItem eiFeature = null;
			for (int i=0; i < eg.getEntityItemCount(); i++) {
				EntityItem ei = eg.getEntityItem(i);
				String strFValue = m_utility.getAttrValue(ei, "FEATURECODE");
				if (strFValue.equals(strFeature)) {
					eiFeature = ei;
					break;
				}
			}

			if (eiFeature != null) {
				EntityItem eiProdstruct = (EntityItem) eiFeature.getDownLink(0);
				m_prodstructList.put(strFeature, eiProdstruct);
			} else {
				m_SBREx.add("Feature " + strFeature + " doesn't link to the MODEL.");
			}
		}
	}
}
