//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: WWSEOABRALWRPDG.java,v $
// Revision 1.12  2008/09/08 17:32:47  wendy
// Cleanup RSA warnings
//
// Revision 1.11  2006/03/15 22:32:58  joan
// fixes
//
// Revision 1.10  2006/03/15 18:45:46  joan
// fixes
//
// Revision 1.9  2006/03/13 19:27:13  joan
// fixes
//
// Revision 1.8  2006/03/09 17:43:25  joan
// fixes
//
// Revision 1.7  2006/02/28 16:44:00  joan
// fixes
//
// Revision 1.6  2006/02/20 22:35:34  joan
// clean up system.out
//
// Revision 1.5  2006/02/09 20:42:26  joan
// fixes
//
// Revision 1.4  2006/02/09 16:30:26  joan
// fixes
//
// Revision 1.3  2006/02/08 19:28:01  joan
// fixes
//
// Revision 1.2  2006/02/08 00:04:24  joan
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

public class WWSEOABRALWRPDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	static final String ATT_CTRYPACKFCLIST = "CTRYPACKFCLIST";
	static final String ATT_LANGPACKFCLIST = "LANGPACKFCLIST";
	static final String ATT_PACKAGINGFCLIST = "PACKAGINGFCLIST";
	static final String ATT_PUBFCLIST = "PUBFCLIST";
	static final String ATT_OTHERFCLIST = "OTHERFCLIST";
	static final String ATT_XXPARTNO = "XXPARTNO";
	static final String ATT_CD = "CD";
	static final String ATT_FC = "FEATURECODE";
	static final String E_FEATURE = "FEATURE";
	static final String E_MODEL = "MODEL";
	static final String E_CDENTITY = "CDENTITY";

	//private Vector m_vctReturnEntityKeys = new Vector();
	private StringBuffer m_sbFeatures = new StringBuffer();
	private String m_strCD = null;
	private String m_strXXPARTNO = null;
	private OPICMList m_FCList = new OPICMList();
	private OPICMList m_prodstructList = new OPICMList();

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: WWSEOABRALWRPDG.java,v 1.12 2008/09/08 17:32:47 wendy Exp $");
  	}


	public WWSEOABRALWRPDG(EANMetaFoundation  _mf, WWSEOABRALWRPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public WWSEOABRALWRPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("WWSEOABRALWRPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "WWSEOABRALWRPDG";
  	}

	private void linkToProdstruct(Database _db, Profile _prof, EANList _eiList, EntityItem _eiProdstruct, String _strRelatorInfo) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = "WWSEOABRALWRPDG linkToProdstruct method";
		D.ebug(D.EBUG_SPEW,strTraceBase);
		EntityItem[] aeic = {_eiProdstruct};

 		for (int i=0; i < _eiList.size(); i++) {
			EntityItem ei = (EntityItem)_eiList.getAt(i);
	//		EntityItem[] aeip = {ei};
			m_utility.linkEntities(_db, _prof, ei, aeic, _strRelatorInfo);
		}
	}

	private OPICMList getFCList(EntityItem _ei) {
		// get the list of features
		OPICMList FCList = new OPICMList();
		StringBuffer sbFeatures = new StringBuffer();

		sbFeatures.append((sbFeatures.toString().length() > 0 ? "," : "") + m_utility.getAttrValue(_ei, ATT_CTRYPACKFCLIST));
		sbFeatures.append((sbFeatures.toString().length() > 0 ? "," : "") + m_utility.getAttrValue(_ei, ATT_LANGPACKFCLIST));
		sbFeatures.append((sbFeatures.toString().length() > 0 ? "," : "") + m_utility.getAttrValue(_ei, ATT_PACKAGINGFCLIST));
		sbFeatures.append((sbFeatures.toString().length() > 0 ? "," : "") + m_utility.getAttrValue(_ei, ATT_PUBFCLIST));
		sbFeatures.append((sbFeatures.toString().length() > 0 ? "," : "") + m_utility.getAttrValue(_ei, ATT_OTHERFCLIST));

		String strFeatures = sbFeatures.toString();
		StringTokenizer st = new StringTokenizer(strFeatures, ",");

		while (st.hasMoreTokens()) {
			String str = st.nextToken().trim();
			String strFeature = "";
		//	String strQuantity = "";
			int iQ = str.indexOf(":");
			if (iQ >= 0) {
				strFeature = str.substring(0, iQ);
	//			strQuantity = str.substring(iQ +1);
			} else {
				strFeature = str;
			}

			FCList.put(strFeature, str);
		}
		return FCList;
	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " WWSEOABRALWRPDG checkMissingData method";
		StringBuffer sbReturn = new StringBuffer();

		//StringBuffer sb = new StringBuffer();
		String strFileName = "PDGtemplates/WWSEOALWR1.txt";

		EntityGroup egCDENTITY = m_ABReList.getEntityGroup(E_CDENTITY);
		_db.debug(D.EBUG_DETAIL, strTraceBase);
		for (int c=0; c < egCDENTITY.getEntityItemCount(); c++) {
			EntityItem eiCDENTITY = egCDENTITY.getEntityItem(c);
			EANList prodList = new EANList();

			String strCD = m_utility.getAttrValue(eiCDENTITY, ATT_CD);

			String strSEOID = (m_strXXPARTNO.length() >=5 ? m_strXXPARTNO.substring(0, 5): m_strXXPARTNO) + strCD;
			m_FCList = getFCList(eiCDENTITY);
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
					OPICMList infoList = new OPICMList();

					infoList.put("WWSEO", m_eiPDG);
					infoList.put("CDENTITY", eiCDENTITY);
					infoList.put("PROD", eiProd);
					infoList.put("SEOID", strSEOID);
					infoList.put("QTY", strQuantity);

					m_eiList = new EANList();
					m_eiList.put(m_eiPDG);
					m_eiList.put(eiProd);

					//System.out.println(strTraceBase + " put in prodList: " + eiProd.getKey());
					prodList.put(eiProd);
					TestPDG pdgObject = new TestPDG(_db, _prof, m_eiPDG, infoList, m_PDGxai, strFileName);
					StringBuffer sbMissing = pdgObject.getMissingEntities();
					pdgObject = null;
					infoList = null;

					if (_bGenData) {
						if (sbMissing.toString().length() > 0) {
							_db.debug(D.EBUG_DETAIL, strTraceBase + " generating data");
							m_savedEIList = new EANList();
							generateData(_db, _prof, sbMissing, "");
							linkToProdstruct(_db, _prof, getSavedEIList(), eiProd, strRelatorInfo);
							EANList eiList = getSavedEIList();
							for (int l=0; l < eiList.size(); l++) {
								EntityItem eiLSEO = (EntityItem)eiList.getAt(l);
								_db.debug(D.EBUG_DETAIL, strTraceBase + " saved ei: " + eiLSEO.getKey());
								sbReturn.append("Create LSEO: " + eiLSEO.toString() + "</br>");
							}

						} else {
							// update the attributes for existing relators from LSEO to Prodstruct
							EntityItem[] aei = {m_eiPDG};
							_prof = m_utility.setProfValOnEffOn(_db, _prof);
							EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aei);
							EntityGroup eg = el.getEntityGroup("LSEOPRODSTRUCT");
							boolean bFound1 = false;
							_db.debug(D.EBUG_DETAIL, strTraceBase + " checking existing relator");
							for (int k=0; k < eg.getEntityItemCount(); k++) {
								EntityItem ei = eg.getEntityItem(k);
								EntityItem eip = (EntityItem) ei.getUpLink(0);
								EntityItem eic = (EntityItem) ei.getDownLink(0);
								String strValue = m_utility.getAttrValue(eip, "SEOID");
								if (strValue.equals(strSEOID)  && eic.getKey().equals(eiProd.getKey())) {
									OPICMList attList = new OPICMList();
									attList.put("CONFQTY", "CONFQTY=" + strQuantity);
									m_utility.updateAttribute(_db, _prof, ei, attList);
									bFound1 = true;
								}
							}

							if (!bFound1) {
								EntityGroup egLSEO = el.getEntityGroup("LSEO");
								for (int k=0; k < egLSEO.getEntityItemCount(); k++) {
									EntityItem eiLSEO = egLSEO.getEntityItem(k);
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
				}
			}
			// remove links from LSEO to unwanted PRODSTRUCT
			EntityItem[] aei = {m_eiPDG};
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aei);

			EntityGroup eg = el.getEntityGroup("LSEOPRODSTRUCT");

			for (int i=0; i < eg.getEntityItemCount(); i++) {
				EntityItem ei = eg.getEntityItem(i);
				EntityItem eip = (EntityItem) ei.getUpLink(0);
				EntityItem eic = (EntityItem) ei.getDownLink(0);
				String strValue = m_utility.getAttrValue(eip, "SEOID");
				//System.out.println(strTraceBase + " in remove: " + eip.getKey() + ", " + eic.getKey() + ", " + strValue + ", " + strSEOID);
				if (strValue.equals(strSEOID)) {
					boolean bFound = false;

					if (prodList.get(eic.getKey()) != null) {
						bFound = true;
					}

					//System.out.println(strTraceBase + " bFound: " + bFound);
					if (!bFound) {
						EntityItem[] aeic = {eic};
						//System.out.println(strTraceBase + " removing: " + eic.getKey());
						m_utility.removeLink(_db, _prof, el, eip, aeic, "LSEOPRODSTRUCT");
					}
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
        String strTraceBase = " WWSEOABRALWRPDG checkPDGAttribute method";
        _db.debug(D.EBUG_DETAIL, strTraceBase);
        _db.debug(D.EBUG_DETAIL, strTraceBase + ":Entity parm is " + _afirmEI.getKey() + ": which has :" + _afirmEI.getAttributeCount());
        for (int i = 0; i < _afirmEI.getAttributeCount(); i++) {
            EANAttribute att = _afirmEI.getAttribute(i);
            _db.debug(D.EBUG_DETAIL, strTraceBase + ":" + att.getAttributeCode());
            String textAtt = "";
        //	String sFlagAtt = "";
          //  String sFlagClass = "";
            Vector mFlagAtt = new Vector();
            Vector mFlagCode = new Vector();

            //int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
              //          sFlagAtt = amf[f].getLongDescription().trim();
                //        sFlagClass = amf[f].getFlagCode().trim();
                  //      index = f;
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

          if (att.getKey().equals(ATT_CTRYPACKFCLIST)) {
		      m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
          } else if (att.getKey().equals(ATT_LANGPACKFCLIST)) {
		      m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
          } else if (att.getKey().equals(ATT_PACKAGINGFCLIST)) {
		      m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
          } else if (att.getKey().equals(ATT_PUBFCLIST)) {
		      m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
          } else if (att.getKey().equals(ATT_OTHERFCLIST)) {
		      m_sbFeatures.append((m_sbFeatures.toString().length() > 0 ? "," : "") + textAtt);
          } else if (att.getKey().equals(ATT_CD)) {
		      m_strCD = textAtt;
	      }
  	  }

		if (m_strCD == null || m_strCD.length() <= 0) {
			m_SBREx.add("CD is blank.");
		}

		if (m_sbFeatures.toString().length() <= 0) {
			m_SBREx.add("Feature Lists are blank.");
		}
	}

	protected void resetVariables() {
		//m_vctReturnEntityKeys = new Vector();
		m_sbFeatures = new StringBuffer();
		m_strCD = null;
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
	//	String strTraceBase = " WWSEOABRALWRPDG viewMissingEntities method";
		return null;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " WWSEOABRALWRPDG executeAction method ";
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

		EntityGroup egParent = m_ABReList.getParentEntityGroup();
		EntityGroup egModel = null;
		EntityGroup egCDENTITY = null;
		EntityGroup egFEATURE = null;
		m_eiPDG = egParent.getEntityItem(m_eiPDG.getKey());
		m_eiList.put("WWSEO", m_eiPDG);
		m_strXXPARTNO = m_utility.getAttrValue(m_eiPDG, ATT_XXPARTNO);
		egModel = m_ABReList.getEntityGroup(E_MODEL);
		if (egModel != null) {
			if (egModel.getEntityItemCount() <= 0) {
				m_SBREx.add("There is no MODEL as parent of WWSEO.");
			} else if (egModel.getEntityItemCount() > 1) {
				m_SBREx.add("There are " + egModel.getEntityItemCount() + " MODELs as parent of WWSEO.");
			}
		}

		egCDENTITY = m_ABReList.getEntityGroup(E_CDENTITY);
		for (int i=0; i < egCDENTITY.getEntityItemCount(); i++) {
			EntityItem eiCDENTITY = egCDENTITY.getEntityItem(i);
			checkPDGAttribute(_db, _prof, eiCDENTITY);
		}

		// get the list of features
		String strFeatures = m_sbFeatures.toString();
		StringTokenizer st = new StringTokenizer(strFeatures, ",");
		egFEATURE = m_ABReList.getEntityGroup(E_FEATURE);
		while (st.hasMoreTokens()) {
			String str = st.nextToken().trim();
			String strFeature = "";
	//		String strQuantity = "";
			int iQ = str.indexOf(":");
			if (iQ >= 0) {
				strFeature = str.substring(0, iQ);
		//		strQuantity = str.substring(iQ +1);
			} else {
				strFeature = str;
			}

			//m_FCList.put(strFeature, str);

			EntityItem eiFeature = null;
			for (int i=0; i < egFEATURE.getEntityItemCount(); i++) {
				EntityItem ei = egFEATURE.getEntityItem(i);
				String strFValue = m_utility.getAttrValue(ei, ATT_FC);
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
