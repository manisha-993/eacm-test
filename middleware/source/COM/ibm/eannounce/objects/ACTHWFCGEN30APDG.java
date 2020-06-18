//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ACTHWFCGEN30APDG.java,v $
// Revision 1.15  2008/09/04 19:46:30  wendy
// Cleanup RSA warnings
//
// Revision 1.14  2006/02/20 21:39:44  joan
// clean up System.out.println
//
// Revision 1.13  2005/02/28 20:29:50  joan
// add throw exception
//
// Revision 1.12  2004/11/17 00:06:11  joan
// changes to getRowIndex key in rowselectable table
//
// Revision 1.11  2004/10/11 22:10:30  joan
// fixes
//
// Revision 1.10  2004/09/17 00:06:24  joan
// fixes
//
// Revision 1.9  2004/09/16 20:20:27  joan
// fixes
//
// Revision 1.8  2004/09/16 18:45:01  bala
// fix
//
// Revision 1.7  2004/09/06 23:12:32  bala
// correct VE in Execute Action
//
// Revision 1.6  2004/09/06 19:22:09  bala
// remove series from the feature search
//
// Revision 1.5  2004/09/04 22:23:01  bala
// fixes
//
// Revision 1.4  2004/09/03 21:46:46  bala
// fix
//
// Revision 1.3  2004/09/03 21:15:06  bala
// fix
//
// Revision 1.2  2004/09/03 18:00:32  bala
// update templatefile
//
// Revision 1.1  2004/09/02 20:31:28  bala
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

public class ACTHWFCGEN30APDG extends PDGActionItem {

  static final long serialVersionUID = 20011106L;

	private String m_strAfSerType = null;
	private String m_strAfFrSerType = null;
	private String m_strToMachType = null;
	private String m_strAfModel = null;
	private Vector m_afToFeatureVec = new Vector();
	//private String m_strAfFromMachType = null;
	//private String m_strAfFromModel = null;
	private String m_strAnnCodeName = null;
	private Vector m_afFromFeatureVec = new Vector();

	//private	EANList m_availList = new EANList(EANMetaEntity.LIST_SIZE);
	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList(EANMetaEntity.LIST_SIZE);
	//private EANList m_fupList = new EANList(EANMetaEntity.LIST_SIZE);
	private EntityItem m_eiToFeature = null;
	private StringBuffer m_sbData = new StringBuffer();
	//private EANList m_upgradeList = new EANList();
	private Vector m_upgradeVec = new Vector();
	private OPICMList m_processedComboList = new OPICMList();

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: ACTHWFCGEN30APDG.java,v 1.15 2008/09/04 19:46:30 wendy Exp $");
  	}


	public ACTHWFCGEN30APDG(EANMetaFoundation  _mf, ACTHWFCGEN30APDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

	public ACTHWFCGEN30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("ACTHWFCGEN30APDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "ACTHWFCGEN30APDG";
  	}
	public String getStepDescription(int iStep) {
		if (iStep == 1) {
			return "Select Feature Conversion";
		}
		return "N/A";
	}

	public void setPDGCollectInfo(PDGCollectInfoList _cl, int _iStep, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				m_InfoList = _cl;
				EANList upgradeList = m_InfoList.getInfoList();
				StringBuffer sb = new StringBuffer();
				boolean bFirst = true;
        		for (int i=0; i < upgradeList.size(); i++) {
					PDGCollectInfoItem ci = (PDGCollectInfoItem)upgradeList.getAt(i);
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

	public PDGCollectInfoList collectInfo(int _iStep) {
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				try {
					EANTextAttribute att1 = (EANTextAttribute)m_eiPDG.getAttribute("AFTOFUPS");
					Vector vecTo = new Vector();
					if (att1 != null) {
						String s = (String)att1.get();
						vecTo = m_utility.sepLongText(s);
					}

					Vector vecFrom = new Vector();

					EANTextAttribute att2 = (EANTextAttribute)m_eiPDG.getAttribute("AFFROMFUPS");
					if (att2 != null) {
						String s = (String)att2.get();
						vecFrom = m_utility.sepLongText(s);
					}

					m_InfoList = new PDGCollectInfoList(this, getProfile(), "From\\To");
					for(int i=0; i < vecFrom.size(); i++) {
						String strFrom = (String)vecFrom.elementAt(i);
						for (int j=0; j< vecTo.size(); j++) {
							String strTo = (String)vecTo.elementAt(j);
							PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, getProfile(), false, strFrom, strTo, "From" + strFrom + " To " + strTo);
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
/*
	protected void generateData(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSearchKey) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = "ACTHWFCGEN30APDG generateData";
		try {
			boolean bRestart = false;
			StringTokenizer st = new StringTokenizer(_sbMissing.toString(),"\n");
			Hashtable ht = new Hashtable();
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				StringTokenizer st1 = new StringTokenizer(s,"|");

				if (st1.hasMoreTokens()) {
					String strParentEntity = st1.nextToken().trim();
					int iLevel = Integer.parseInt(st1.nextToken());

					// get parent for later links
					EntityItem eiParent = null;
					if (strParentEntity != null && strParentEntity.length() > 0) {
						StringTokenizer stParent = new StringTokenizer(strParentEntity,"-");
						if (stParent.hasMoreTokens()) {
							String strParentType = stParent.nextToken();
							int iParentID = Integer.parseInt(stParent.nextToken());
							eiParent = new EntityItem(null, _prof, strParentType, iParentID);
						}
					} else {
						eiParent = (EntityItem)ht.get((iLevel-1) + "");
					}

					// get stuff for Entity
					String strEntity = st1.nextToken();
					int i1 = strEntity.indexOf(":");
					String strEntityType = strEntity;
					String strAttributes = "";
					if (i1 > -1 ){
						strEntityType = strEntity.substring(0, i1);
						strAttributes = strEntity.substring(i1+1);
					}

					String strAction = st1.nextToken();
					String strRelatorInfo = st1.nextToken();


					int i = strAttributes.indexOf("GENAREASELECTION");
					String sGeo = "";
					if (i > -1) {
						sGeo = strAttributes.substring(i);
						StringTokenizer stGeo = new StringTokenizer(sGeo,";");
						if (stGeo.hasMoreTokens()) {
							sGeo = stGeo.nextToken();
						}
						stGeo = new StringTokenizer(sGeo,"=");
						if (stGeo.hasMoreTokens()) {
							sGeo = stGeo.nextToken();
							sGeo = stGeo.nextToken();
						}
					}

					//find the item if needed
					int iFind = strAction.indexOf("find");
					EntityItem foundEI = null;
					if (iFind > -1) {
						String strSai = (String)m_saiList.get(strEntityType);
						if (strEntityType.equals("AVAIL")) {
							if (sGeo.length() > 0) {
								foundEI = (EntityItem)m_availList.get(sGeo);
							}

							if (foundEI == null) {
								EntityItem[] aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
								if (aei.length > 0) {
									foundEI = aei[0];
									m_availList.put(sGeo, foundEI);
								}
							}
						} else if (strEntityType.equals("TOFEATURECODE")) {
							if (strAction.indexOf("FROMFEATURECODE") > -1) {
								foundEI = m_eiToFeature;
							} else {
								foundEI = (EntityItem)m_eiList.get(_strSearchKey);
							}
						} else {
							EntityItem[] aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
							if (aei.length > 0) {
								foundEI = aei[0];
							}
						}
					}

					// link them if there's command link
					int iLink = strAction.indexOf("linkParent");
					if (iLink > -1 && foundEI != null) {
						// use parent entity, relator,link
						ht.put(iLevel + "", foundEI);
						EntityItem[] aei = {foundEI};
						if (eiParent != null) {
							m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
							// after find/link, pull the VE, and test again if required.
							int iRestart = strAction.indexOf("restart");
							if (iRestart > -1) {
								bRestart = true;
								break;
							} else {
								// if not need to restart, continue with next item
								continue;
							}
						}
					}

					// create the item
					int iCreate = strAction.indexOf("create");
					if (iCreate > -1) {
						//prepare the list of attributes
						OPICMList attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);

						String strRelatorType = "";
						int iAttrO = strRelatorInfo.indexOf("[");
						if (iAttrO > -1) {
							strRelatorType = strRelatorInfo.substring(0,iAttrO);
						} else {
							strRelatorType = strRelatorInfo;
						}

						String strCai = (String)m_caiList.get(strRelatorType);
						if (eiParent == null) {
							eiParent = m_eiOFPROJ;
						}
						EntityItem ei = m_utility.createEntityByRST(_db, _prof, eiParent, attList, strCai, strRelatorType, strEntityType);
						_db.test(ei != null, " ei is null for: " + s);
						ht.put(iLevel + "", ei);

						if (strEntityType.equals("AVAIL") && sGeo.length() > 0) {
							// save AVAIL for later link to ORDEROF
							m_availList.put(sGeo, ei);
						}

						// save base COF for restart
						if (iLevel == 0 && strEntityType.equals("MODEL")) {
							m_eiBaseCOF = ei;
						}

						// save entities for later link to Offering Project
						int iLinkOP = strAction.indexOf("linkOP");
						if (iLinkOP > -1) {
							String strLinkOP = strAction.substring(iLinkOP);
							int iEnd = strLinkOP.indexOf(";");
							if (iEnd > -1) {
								strLinkOP=strLinkOP.substring(0,iEnd);
							}

							int iU = strLinkOP.indexOf("_");
							if (iU > -1) {
								String strRelator = strLinkOP.substring(iU+1);
								if (m_opList.get(ei) == null) {
									m_vctReturnEntityKeys.addElement(new ReturnRelatorKey(strRelator, -1, m_eiOFPROJ.getEntityType(), m_eiOFPROJ.getEntityID(), ei.getEntityType(), ei.getEntityID(), true));
									m_opList.put(ei);
								}
							}
						}
					}
				}
			}

			if (bRestart) {
				checkMissingData(_db, _prof, true);
			}

			// link entities to Offering Project
			if (m_vctReturnEntityKeys.size() > 0) {
				m_utility.link(_db, _prof, m_vctReturnEntityKeys);
			}

		} catch (SBRException ex) {
			if (m_vctReturnEntityKeys.size() > 0) {
				m_utility.link(_db, _prof, m_vctReturnEntityKeys);
			}
			throw ex;
		}
	}
*/
	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " ACTHWFCGEN30APDG checkMissingData method";

		//ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "EXTHWFCGEN30APDG");

		m_eiBaseCOF = (EntityItem)m_eiList.get("TOBASE");

		String strFileName = "PDGtemplates/HWFeatureConvert30a.txt";
		m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

		for (int i=0; i < m_upgradeVec.size(); i++) {
			String strUpgrade = (String) m_upgradeVec.elementAt(i);
			int index = strUpgrade.indexOf(":");
			String strFromFeature = strUpgrade.substring(0,index);
			String strToFeature = strUpgrade.substring(index+1);
			if (m_processedComboList.get(strFromFeature + strToFeature) == null) {

				EntityItem eiFromFeature = (EntityItem)m_eiList.get(strFromFeature);
				m_eiToFeature = (EntityItem)m_eiList.get(strToFeature);

				OPICMList infoList = new OPICMList();
				infoList.put("WG", m_eiRoot);
				infoList.put("PDG", m_eiPDG);
				infoList.put("TOFEATURE", m_eiToFeature);
				infoList.put("FROMFEATURE", eiFromFeature);
				infoList.put("GEOIND", "GENAREASELECTION");

				_prof = m_utility.setProfValOnEffOn(_db, _prof);

				TestPDG pdgObject = new TestPDG(_db, _prof, m_eiBaseCOF, infoList, m_PDGxai, strFileName);
				StringBuffer sbMissing = pdgObject.getMissingEntities();
				pdgObject = null;
				infoList = null;

				if (_bGenData) {
					generateData(_db, _prof, sbMissing, strFromFeature);
					//should save the combo to prevent from checking it again.
					m_processedComboList.put(strFromFeature + strToFeature, strFromFeature + strToFeature);
				}
				m_sbData.append(sbMissing.toString());
			}
		}

		return m_sbData;
	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
		//String strTraceBase = " ACTHWFCGEN30APDG checkPDGAttribute method";

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

			if (att.getKey().equals("AFHWSERTYPE")) {
				m_strAfSerType = sFlagAtt;
			} else if (att.getKey().equals("MACHTYPEATR")) {
				m_strToMachType = sFlagAtt;
			} else if (att.getKey().equals("MODELATR")) {
				m_strAfModel = textAtt;
			} else if (att.getKey().equals("AFTOFUPS")) {
				m_afToFeatureVec = m_utility.sepLongText(textAtt);            //Create the picklist thingie
			} else if (att.getKey().equals("FROMMACHTYPE")) {
				//m_strAfFromMachType = sFlagAtt;
			} else if (att.getKey().equals("FROMMODEL")) {
				//m_strAfFromModel = textAtt;
			} else if (att.getKey().equals("AFFROMFUPS")) {
				m_afFromFeatureVec = m_utility.sepLongText(textAtt);
			} else if (att.getKey().equals("ANNCODENAME")) {
				m_strAnnCodeName = sFlagAtt;
			} else if (att.getKey().equals("AFHWFRSERTYPE")) {
				m_strAfFrSerType = sFlagAtt;
			} else if (att.getKey().equals("AFINFO")) {
				m_upgradeVec = m_utility.sepLongText(textAtt);
			}
		}

		if (m_afToFeatureVec.size() <= 0) {
			m_SBREx.add("To FEATURECODE(s) is required for Feature Convert");
		}

		if (m_afFromFeatureVec.size() <= 0) {
			m_SBREx.add("From FEATURECODE(s) is required for Feature Convert");
		}

		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}

		if (m_upgradeVec == null || m_upgradeVec.size() <= 0)  {
			m_SBREx.add("Please select feature conversion.");
		}

	}

	protected void resetVariables() {
		m_strAfSerType = null;
		m_strAfFrSerType = null;
		m_strToMachType = null;
		m_strAfModel = null;
		//m_strAfFromMachType = null;
		//m_strAfFromModel = null;
		m_afFromFeatureVec = new Vector();

		//m_vctReturnEntityKeys = new Vector();
		//m_opList = new EANList();
		m_eiList = new EANList();
		//m_fupList = new EANList();
		m_eiToFeature = null;
		m_processedComboList = new OPICMList();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " ACTHWFCGEN30APDG viewMissing method";

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		m_SBREx = new SBRException();
		resetVariables();
		if (m_eiPDG == null) {
			String s="PDG entity is null";
			return s.getBytes();
		}
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXPDG30MODUPGRABR");
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
		_db.test(m_eiRoot != null, "WG entity is null");

		checkPDGAttribute(_db, _prof, m_eiPDG);
		if (m_SBREx.getErrorCount() > 0) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
			throw m_SBREx;
		}

		// validate data
		checkDataAvailability(_db, _prof, m_eiPDG);
		if (m_SBREx.getErrorCount() > 0) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
			throw m_SBREx;
		}

		m_sbActivities = new StringBuffer();
		m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
		m_sbData = new StringBuffer();
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
		String strTraceBase = " ACTHWFCGEN30APDG executeAction method";
		String strData = "";
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			m_SBREx = new SBRException();
			resetVariables();

			if (m_eiPDG == null) {
				D.ebug(D.EBUG_SPEW,"PDG entity is null");
				return;
			}
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXPDG30MODUPGRABR");
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
			_db.test(m_eiRoot != null, "WG entity is null");

			checkPDGAttribute(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
				throw m_SBREx;
			}

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
			strData = checkMissingData(_db, _prof, true).toString();
			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
		} catch (SBRException ex) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		}
		if (strData.length() <= 0) {
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
		// make sure the Base MODEL for the MTM already exists
		String strTraceBase = "ACTHWFCGEN30APDG checkDataAvailability method";
		String strSai = (String)m_saiList.get("MODEL");
		StringBuffer sb = new StringBuffer();
		sb.append("map_COFCAT=100;");
		sb.append("map_COFSUBCAT=126;");
		sb.append("map_COFGRP=150;");
		sb.append("map_COFSUBGRP=" + m_strAfSerType + ";");
		sb.append("map_MACHTYPEATR=" + m_strToMachType + ";");
		sb.append("map_MODELATR=" + m_strAfModel);

		EntityItem[] aeiMODEL = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
       	if (aeiMODEL.length <= 0 ) {
       	    m_SBREx.add("The Hardware-System-Base-" + m_strAfSerType + " MODEL must exist " + m_strToMachType + "-" + m_strAfModel);
       	} else if (aeiMODEL.length > 1) {
       	    m_SBREx.add("There are " + aeiMODEL.length + " existing Hardware-System-Base-" + m_strAfSerType + " MODEL with the same " + m_strToMachType + "-" + m_strAfModel);
       	} else {
       	    m_eiList.put("TOBASE", aeiMODEL[0]);
       	}

        // make sure the To feature exists
        strSai = (String)m_saiList.get("FEATURE");
        D.ebug(D.EBUG_SPEW,strTraceBase + " 00");
		for (int i=0; i < m_upgradeVec.size(); i++) {
			String strUpgrade = (String) m_upgradeVec.elementAt(i);
			D.ebug(D.EBUG_SPEW,strTraceBase + " 01 " + strUpgrade);
			int index = strUpgrade.indexOf(":");
			String strFrom = strUpgrade.substring(0,index);
			String strTo = strUpgrade.substring(index+1);

			if (strTo != null) {
				StringTokenizer st = new StringTokenizer(strTo, "|");
        //Use dynamic search for the FEATURE with  SERIES=input Series Type and To Feature code
				while (st.hasMoreTokens()) {
					String s = st.nextToken();
					sb = new StringBuffer();
					sb.append("map_SERIES=" + m_strAfSerType + ";");
					sb.append("map_FEATURECODE=" + s );
					EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FEATURE", sb.toString());
					if (aeiFUP.length <= 0 ) {
						m_SBREx.add("FEATURE for series " + m_strAfSerType + " and featurecode " + s + " must exist.");
					} else if (aeiFUP.length > 1) {
						m_SBREx.add("There are " + aeiFUP.length + " existing FEATURE with the same series " + m_strAfSerType + " and featurecode " + s );
					} else {
						m_eiList.put(strTo, aeiFUP[0]);
						break;
					}
				}
			}
      //Use dynamic search for the FEATURE with  SERIES =input From Series and from Feature Code.
			if (strFrom != null) {
				StringTokenizer st = new StringTokenizer(strFrom, "|");

				while (st.hasMoreTokens()) {
					String s = st.nextToken();
					sb = new StringBuffer();
					sb.append("map_SERIES=" + m_strAfFrSerType + ";");
					sb.append("map_FEATURECODE=" + s );
					EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FEATURE", sb.toString());
					if (aeiFUP.length <= 0 ) {
						m_SBREx.add("Hardware FEATURE for series " + m_strAfFrSerType + " and featurecode " + s + " must exist.");
					} else if (aeiFUP.length > 1) {
						m_SBREx.add("There are " + aeiFUP.length + " existing Hardware FEATURES with the same series " + m_strAfFrSerType + " and featurecode " + s );
					} else {
						m_eiList.put(strFrom, aeiFUP[0]);
						break;
					}
				}
			}
		}
	}
}
