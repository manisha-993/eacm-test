//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: HWUpgradeModelPDG.java,v $
// Revision 1.20  2008/09/04 21:49:40  wendy
// Cleanup RSA warnings
//
// Revision 1.19  2003/10/24 23:08:24  joan
// fb fixes
//
// Revision 1.18  2003/10/21 16:21:30  joan
// fb fix
//
// Revision 1.17  2003/07/15 19:36:51  joan
// move changes from v111
//
// Revision 1.16  2003/07/03 22:44:53  joan
// move changes from v111
//
// Revision 1.15  2003/07/02 22:54:56  joan
// fix classification
//
// Revision 1.14  2003/07/02 16:10:45  joan
// fix geo
//
// Revision 1.13  2003/06/11 23:26:20  joan
// move changes
//
// Revision 1.12  2003/06/06 20:20:29  joan
// move changes from v111
//
// Revision 1.11  2003/05/29 00:03:37  joan
// fix feedback
//
// Revision 1.10  2003/05/13 17:41:51  joan
// fixes for report
//
// Revision 1.9  2003/05/08 17:38:24  joan
// fix fb
//
// Revision 1.8  2003/05/01 17:08:43  joan
// fix code for xml
//
// Revision 1.7  2003/04/30 20:14:36  joan
// add code for xml display
//
// Revision 1.6  2003/04/23 22:32:41  joan
// set profile valon effon
//
// Revision 1.5  2003/04/18 23:30:20  joan
// fix bugs
//
// Revision 1.4  2003/04/17 16:37:03  joan
// fix code
//
// Revision 1.3  2003/04/16 19:52:43  joan
// fix PDG creating entity
//
// Revision 1.2  2003/04/15 17:01:38  joan
// fix bugs
//
// Revision 1.1  2003/04/14 18:53:33  joan
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
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;

public class HWUpgradeModelPDG extends PDGActionItem {

  static final long serialVersionUID = 20011106L;

	private String m_strAfSerType = null;
	private String m_strAfFrSerType = null;
	private String m_strAfMachType = null;
	private String m_strAfModel = null;
	private String m_strAfFromMachType = null;
	private String m_strAfFromModel = null;
	private String m_strAnnCodeName = null;

	private	EANList m_availList = new EANList();
	private Vector m_vctReturnEntityKeys = new Vector();
	private EANList m_opList = new EANList();
	//private EANList m_fupList = new EANList();
	private StringBuffer m_sbData = new StringBuffer();
	private ExtractActionItem m_xai = null;

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: HWUpgradeModelPDG.java,v 1.20 2008/09/04 21:49:40 wendy Exp $");
  	}


	public HWUpgradeModelPDG(EANMetaFoundation  _mf, HWUpgradeModelPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	public HWUpgradeModelPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("HWUpgradeModelPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "HWUpgradeModelPDG";
  	}

	protected void generateData(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSearchKey) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = "HWUpgradeModelPDG generateData";
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
						} else if (strEntityType.equals("FUP")) {
							if (strAction.indexOf("TOFUP") > -1) {
								foundEI = (EntityItem)m_eiList.get("TOFUP");
							} else if (strAction.indexOf("FROMFUP") > -1) {
								foundEI = (EntityItem)m_eiList.get("FROMFUP");
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
						if (iLevel == 0 && strEntityType.equals("COMMERCIALOF")) {
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

						//link to Avail when create only
						int iCreateAttAvail = strAction.indexOf("createAttAvail");
						if (iCreateAttAvail > -1) {
							String strAttAvail = strAction.substring(iCreateAttAvail);
							int iEnd = strAttAvail.indexOf(";");
							if (iEnd > -1) {
								strAttAvail = strAttAvail.substring(0,iEnd);
							}

							int iU = strAttAvail.indexOf("_");
							String strRelator = null;
							if (iU > -1) {
								strRelator = strAttAvail.substring(iU+1);
							}

							if ( m_availList.size() <= 0 ) {
								String strAvailSai = (String)m_saiList.get("AVAIL");
								String strAvailCai = (String)m_caiList.get(strRelator);

								m_availList = m_utility.getAvailForFiveRegions(_db, _prof, m_eiPDG, ei, strAvailSai, strAvailCai);
							} else {
								int size = m_availList.size();
								EntityItem[] aei =  new EntityItem[size];
								for (int ii=0; ii < m_availList.size(); ii++) {
									EntityItem eiAvail = (EntityItem)m_availList.getAt(ii);
									aei[ii] = eiAvail;
								}
								m_utility.linkEntities(_db, _prof, ei, aei, strRelator);
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

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " HWUpgradeModelPDG checkMissingData method";

		m_eiBaseCOF = (EntityItem)m_eiList.get("TOBASE");
		EntityItem eiToFUP = (EntityItem)m_eiList.get("TOFUP");

		OPICMList infoList = new OPICMList();
		String strFileName = "PDGtemplates/HWUpgradeModel.txt";
		if (! m_strAfFromMachType.equals(m_strAfMachType)) {
			strFileName = "PDGtemplates/HWConvertModel.txt";
		}

		m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");
		EntityItem eiFromFUP = (EntityItem)m_eiList.get("FROMFUP");

		infoList.put("PDG", m_eiPDG);
		infoList.put("TOFUP", eiToFUP);
		infoList.put("FROMFUP", eiFromFUP);
		infoList.put("GEOIND", "GENAREASELECTION");

		_prof = m_utility.setProfValOnEffOn(_db, _prof);

		TestPDG pdgObject = new TestPDG(_db, _prof, m_eiBaseCOF, infoList, m_xai, strFileName);
		StringBuffer sbMissing = pdgObject.getMissingEntities();
		if (_bGenData) {
			generateData(_db, _prof, sbMissing, "");
		}

		m_sbData.append(sbMissing.toString());
		return m_sbData;
	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
		//String strTraceBase = " HWUpgradeModelPDG checkPDGAttribute method";
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
					//		index = f;
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
			} else if (att.getKey().equals("MACHTYPE")) {
				m_strAfMachType = textAtt;
			} else if (att.getKey().equals("MODEL")) {
				m_strAfModel = textAtt;
			} else if (att.getKey().equals("FROMMACHTYPE")) {
				m_strAfFromMachType = textAtt;
			} else if (att.getKey().equals("FROMMODEL")) {
				m_strAfFromModel = textAtt;
			} else if (att.getKey().equals("ANNCODENAME")) {
				m_strAnnCodeName = sFlagAtt;
			} else if (att.getKey().equals("AFHWFRSERTYPE")) {
				m_strAfFrSerType = sFlagAtt;
			}
		}

		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}

		if (m_strAfFrSerType == null || m_strAfFrSerType.length() <=0) {
			m_SBREx.add("From Series is required.");
		}
	}

	protected void resetVariables() {
		m_strAfSerType = null;
		m_strAfFrSerType = null;
		m_availList = new EANList();
		m_vctReturnEntityKeys = new Vector();
		m_opList = new EANList();
		m_eiList = new EANList();
		//m_fupList = new EANList();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " HWUpgradeModelPDG executeAction method";

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		m_SBREx = new SBRException();
		resetVariables();
		if (m_eiPDG == null) {
			String s="PDG entity is null";
			return s.getBytes();
		}
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT3");
		EntityItem[] eiParm = {m_eiPDG};
		EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
		EntityGroup eg = el.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());

		checkPDGAttribute(_db, _prof, m_eiPDG);

		// validate data
		checkDataAvailability(_db, _prof, m_eiPDG);
		if (m_SBREx.getErrorCount() > 0) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
			throw m_SBREx;
		}

		m_sbActivities = new StringBuffer();
		m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
		m_sbData = new StringBuffer();
		m_xai = new ExtractActionItem(null, _db, _prof, "EXTSWPDG1");
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
		String strTraceBase = " HWUpgradeModelPDG executeAction method";
		String strData = "";
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			m_SBREx = new SBRException();
			resetVariables();

			if (m_eiPDG == null) {
				System.out.println("PDG entity is null");
				return;
			}
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT3");
			EntityItem[] eiParm = {m_eiPDG};
			EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
			EntityGroup eg = el.getParentEntityGroup();
			m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
			eg = el.getEntityGroup("OFDEVLPROJ");
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiOFPROJ = eg.getEntityItem(0);
				}
			}
			_db.test(m_eiOFPROJ != null, "Offering Project entity is null");

			checkPDGAttribute(_db, _prof, m_eiPDG);
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
			m_xai = new ExtractActionItem(null, _db, _prof, "EXTSWPDG1");
			strData = checkMissingData(_db, _prof, true).toString();
			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
		} catch (SBRException ex) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		}
		if (strData.length() <= 0) {
			m_SBREx.add("Generating data is complete.  No data created during this run.");
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
		// make sure the Base COMMERCIALOF for the MTM already exists
		String strSai = (String)m_saiList.get("COMMERCIALOF");
		StringBuffer sb = new StringBuffer();
		sb.append("map_COFCAT=100;");
		sb.append("map_COFSUBCAT=208;");
		sb.append("map_COFGRP=301;");
		sb.append("map_COFSUBGRP=" + m_strAfSerType + ";");
		sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
		sb.append("map_MODEL=" + m_strAfModel);

		EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "COMMERCIALOF", sb.toString());
       	if (aeiCOM.length <= 0 ) {
      	    m_SBREx.add("The Hardware-System-Base-" + m_strAfSerType + " Commercial Offering must exist for " + m_strAfMachType + "-" + m_strAfModel);
       	} else if (aeiCOM.length > 1) {
       	    m_SBREx.add("There are " + aeiCOM.length + " existing Hardware-System-Base-" + m_strAfSerType + " Commercial Offering for " + m_strAfMachType + "-" + m_strAfModel);
       	} else {
       	    m_eiList.put("TOBASE", aeiCOM[0]);
       	}
/*
		// make sure the From Base COMMERCIALOF for the MTM already exists
		sb = new StringBuffer();
		sb.append("map_COFCAT=100;");
		sb.append("map_COFSUBCAT=208;");
		sb.append("map_COFGRP=301;");
		sb.append("map_COFSUBGRP=" + m_strAfFrSerType + ";");
		sb.append("map_MACHTYPE=" + m_strAfFromMachType + ";");
		sb.append("map_MODEL=" + m_strAfFromModel);

		aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "COMMERCIALOF", sb.toString());

       	if (aeiCOM.length <= 0 ) {
       	    m_SBREx.add("The Hardware-System-Base-" + m_strAfFrSerType + " Commercial Offering must exist for " + m_strAfFromMachType + "-" + m_strAfFromModel);
       	} else if (aeiCOM.length > 1) {
       	    m_SBREx.add("There are " + aeiCOM.length + " existing Hardware-System-Base-" + m_strAfFrSerType + " Commercial Offering for " + m_strAfFromMachType + "-" + m_strAfFromModel);
       	} else {
       	    m_eiList.put("FROMBASE", aeiCOM[0]);
       	}
*/
		// make sure the Hardware-Model-N/A-N/A FUP for the series and Model already exists
        sb = new StringBuffer();
        strSai = (String)m_saiList.get("FUP");
		sb.append("map_FUPCAT=100;");
		sb.append("map_FUPSUBCAT=502;");
		sb.append("map_FUPGRP=405;");
		sb.append("map_FUPSUBGRP=405;");
		sb.append("map_SERIES=" + m_strAfSerType + ";");
		sb.append("map_MODEL=" + m_strAfModel);

        EntityItem[] aeiToFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FUP", sb.toString());
        if (aeiToFUP.length <= 0 ) {
            m_SBREx.add("The Hardware-Model-N/A-N/A FUP for the input series " + m_strAfSerType + " and model " + m_strAfModel + " must exist.");
        } else if (aeiToFUP.length > 1) {
            m_SBREx.add("There are " + aeiToFUP.length + " existing Hardware-Model-N/A-N/A FUP for the input series " + m_strAfSerType + " and model " + m_strAfModel);
        } else {
            m_eiList.put("TOFUP", aeiToFUP[0]);
        }

       	sb = new StringBuffer();
       	strSai = (String)m_saiList.get("FUP");
		sb.append("map_FUPCAT=100;");
		sb.append("map_FUPSUBCAT=502;");
		sb.append("map_FUPGRP=405;");
		sb.append("map_FUPSUBGRP=405;");
		sb.append("map_SERIES=" + m_strAfFrSerType + ";");
		sb.append("map_MODEL=" + m_strAfFromModel);

       	EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FUP", sb.toString());
       	if (aeiFUP.length <= 0 ) {
       	    m_SBREx.add("The Hardware-Model-N/A-N/A FUP for the input series " + m_strAfFrSerType + " and model " + m_strAfFromModel + " must exist.");
       	} else if (aeiFUP.length > 1) {
       	    m_SBREx.add("There are " + aeiFUP.length + "  existing Hardware-Model-N/A-N/A FUP for the input series" + m_strAfFrSerType + " and model " + m_strAfFromModel);
       	} else {
       	    m_eiList.put("FROMFUP", aeiFUP[0]);
		}
	}
}
