//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LSEOBDLALWRPDG.java,v $
// Revision 1.4  2008/03/26 20:26:57  wendy
// Clean up RSA warnings
//
// Revision 1.3  2005/08/03 22:44:21  joan
// fixes
//
// Revision 1.2  2005/08/03 20:54:27  joan
// fixes
//
// Revision 1.1  2005/08/03 16:40:06  joan
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

public class LSEOBDLALWRPDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();
	private StringBuffer m_sbSEOIDs = new StringBuffer();
	private String m_strCD = null;
	private String m_strXXPARTNO = null;
	private OPICMList m_seoIdList = new OPICMList();
	private OPICMList m_lseoList = new OPICMList();
	private EntityItem m_eiLSEOBDL = null;

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: LSEOBDLALWRPDG.java,v 1.4 2008/03/26 20:26:57 wendy Exp $");
  	}


	public LSEOBDLALWRPDG(EANMetaFoundation  _mf, LSEOBDLALWRPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public LSEOBDLALWRPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("LSEOBDLALWRPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "LSEOBDLALWRPDG";
  	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " LSEOBDLALWRPDG executeProduct method";
		StringBuffer sbReturn = new StringBuffer();

		//StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/LSEOBDLALWR1.txt";
		String strSEOID = (m_strXXPARTNO.length() >=5 ? m_strXXPARTNO.substring(0, 5): m_strXXPARTNO) + m_strCD;

		for (int i =0; i < m_seoIdList.size(); i++) {
			String str = (String)m_seoIdList.getAt(i);
			String strSeoId = "";
			String strQuantity = "";
			int iQ = str.indexOf(":");
			if (iQ >= 0) {
				strSeoId = str.substring(0, iQ);
				strQuantity = str.substring(iQ +1);
			} else {
				strSeoId = str;
			}
			EntityItem eiLseo = (EntityItem)m_lseoList.get(strSeoId);
			if (eiLseo != null) {
				OPICMList infoList = new OPICMList();
				infoList.put("WG", m_eiRoot);
				infoList.put("LSEOBUNDLE", m_eiLSEOBDL);
				infoList.put("LSEOBDLALWR", m_eiPDG);
				infoList.put("LSEO", eiLseo);
				infoList.put("SEOID", strSEOID);
				infoList.put("QTY", strQuantity);

				m_eiList = new EANList();
				m_eiList.put(m_eiRoot);
				m_eiList.put(m_eiPDG);
				m_eiList.put(eiLseo);

				TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, m_PDGxai, strFileName);
				StringBuffer sbMissing = pdgObject.getMissingEntities();
				pdgObject = null;
				infoList = null;

				if (_bGenData) {
					if (sbMissing.toString().length() > 0) {
						generateData(_db, _prof, sbMissing, "");
					} else {
						// update the attributes for existing
						EntityItem[] aei = {m_eiRoot};
						EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aei);
						EntityGroup eg = el.getEntityGroup("LSEOBUNDLELSEO");

						for (int k=0; k < eg.getEntityItemCount(); k++) {
							EntityItem ei = eg.getEntityItem(k);
							EntityItem eip = (EntityItem) ei.getUpLink(0);
							EntityItem eic = (EntityItem) ei.getDownLink(0);
							String strValue = m_utility.getAttrValue(eip, "SEOID");
							if (strValue.equals(strSEOID) && eic.getKey().equals(eiLseo.getKey())) {
								OPICMList attList = new OPICMList();
								attList.put("LSEOQTY", "LSEOQTY=" + strQuantity);
								m_utility.updateAttribute(_db, _prof, ei, attList);
							}
						}

					}
				}
			}
		}

		// remove links from LSEO to unwanted PRODSTRUCT
		EntityItem[] aei = {m_eiRoot};
		EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aei);
		EntityGroup eg = el.getEntityGroup("LSEOBUNDLELSEO");

		for (int i=0; i < eg.getEntityItemCount(); i++) {
			EntityItem ei = eg.getEntityItem(i);
			EntityItem eip = (EntityItem) ei.getUpLink(0);
			EntityItem eic = (EntityItem) ei.getDownLink(0);
			String strValue = m_utility.getAttrValue(eip, "SEOID");
			if (strValue.equals(strSEOID)) {
				boolean bFound = false;
				for (int j=0; j < m_lseoList.size(); j++) {
					EntityItem eiprod = (EntityItem) m_lseoList.getAt(j);
					if (eiprod.getKey().equals(eic.getKey())) {
						bFound = true;
						break;
					}
				}

				if (!bFound) {
					EntityItem[] aeic = {eic};
					m_utility.removeLink(_db, _prof, el, eip, aeic, "LSEOBUNDLELSEO");
				}
			}
		}

		return sbReturn;
	}

	/*private String adjustActivities(String _s) {

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
    String strTraceBase = " LSEOBDLALWRPDG checkPDGAttribute method";
    _db.debug(D.EBUG_DETAIL, strTraceBase);
    _db.debug(D.EBUG_DETAIL, strTraceBase + ":Entity parm is " + _afirmEI.getKey() + ": which has :" + _afirmEI.getAttributeCount());
    for (int i = 0; i < _afirmEI.getAttributeCount(); i++) {
      EANAttribute att = _afirmEI.getAttribute(i);
      _db.debug(D.EBUG_DETAIL, strTraceBase + ":" + att.getAttributeCode());
      String textAtt = "";
      //String sFlagAtt = "";
      //String sFlagClass = "";
      Vector mFlagAtt = new Vector();
      Vector mFlagCode = new Vector();

      //int index = -1;
      if (att instanceof EANTextAttribute) {
        textAtt = ((String) att.get()).trim();
      } else if (att instanceof EANFlagAttribute) {
        if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
          /*MetaFlag[] amf = (MetaFlag[]) att.get();
          for (int f = 0; f < amf.length; f++) {
            if (amf[f].isSelected()) {
              sFlagAtt = amf[f].getLongDescription().trim();
              sFlagClass = amf[f].getFlagCode().trim();
              index = f;
              break;
            }
          }*/
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

      if (att.getKey().equals("HWLSEOLIST")) {
		  m_sbSEOIDs.append((m_sbSEOIDs.toString().length() > 0 ? "," : "") + textAtt);
      } else if (att.getKey().equals("SWLSEOLIST")) {
		  m_sbSEOIDs.append((m_sbSEOIDs.toString().length() > 0 ? "," : "") + textAtt);
      } else if (att.getKey().equals("HIPOLSEOLIST")) {
		  m_sbSEOIDs.append((m_sbSEOIDs.toString().length() > 0 ? "," : "") + textAtt);
      } else if (att.getKey().equals("SERVLSEOLIST")) {
		  m_sbSEOIDs.append((m_sbSEOIDs.toString().length() > 0 ? "," : "") + textAtt);
      } else if (att.getKey().equals("CD")) {
		  m_strCD = textAtt;
	  } else if (att.getKey().equals("XXPARTNO")) {
		  m_strXXPARTNO = textAtt;
	  }
  	}

		if (m_strCD == null || m_strCD.length() <= 0) {
			m_SBREx.add("CD is blank.");
		}

		if (m_strXXPARTNO == null || m_strXXPARTNO.length() <= 0) {
			m_SBREx.add("XXPARTNO is blank.");
		}


		if (m_sbSEOIDs.toString().length() <= 0) {
			m_SBREx.add("LSEO Lists are blank.");
		}
	}

	protected void resetVariables() {
		//m_vctReturnEntityKeys = new Vector();
		m_sbSEOIDs = new StringBuffer();
		m_strCD = null;
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " LSEOBDLALWRPDG viewMissingEntities method";
		return null;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " LSEOBDLALWRPDG executeAction method ";
		m_SBREx = new SBRException();
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				System.out.println("LSEOBDLALWR entity is null");
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
		m_eiList.put("LSEOBDLALWR", m_eiPDG);
		checkPDGAttribute(_db, _prof, m_eiPDG);
		// get WG
		eg = m_ABReList.getEntityGroup("WG");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "WG  entity is null");

		// get LSEOBUNDLE
		eg = m_ABReList.getEntityGroup("LSEOBUNDLE");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiLSEOBDL = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "WG  entity is null");

		// get the list of lseos
		String strSeoIDs = m_sbSEOIDs.toString();
		StringTokenizer st = new StringTokenizer(strSeoIDs, ",");
		while (st.hasMoreTokens()) {
			String str = st.nextToken().trim();
			String strSeoID = "";
			//String strQuantity = "";
			int iQ = str.indexOf(":");
			if (iQ >= 0) {
				strSeoID = str.substring(0, iQ);
				//strQuantity = str.substring(iQ +1);
			} else {
				strSeoID = str;
			}

			m_seoIdList.put(strSeoID, str);

			// search for Lseo
			StringBuffer sb = new StringBuffer();
			String strSai = (String)m_saiList.get("LSEO");
			sb.append("map_SEOID=" + strSeoID);

			EntityItem[] aeiLSEO = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "LSEO", sb.toString());
			if (aeiLSEO.length <= 0 ) {
				m_SBREx.add("LSEO for SEOID=" + strSeoID + " doesn't exist");
			} else if (aeiLSEO.length > 1) {
				m_SBREx.add("There are " + aeiLSEO.length + "  existing LSEO for SEOID=" + strSeoID);
			} else {
				m_lseoList.put(strSeoID, aeiLSEO[0]);
			}
		}
	}
}
