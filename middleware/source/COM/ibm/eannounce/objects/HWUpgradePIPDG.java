//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: HWUpgradePIPDG.java,v $
// Revision 1.33  2008/09/04 21:46:54  wendy
// Cleanup RSA warnings
//
// Revision 1.32  2005/02/28 20:29:51  joan
// add throw exception
//
// Revision 1.31  2004/03/12 23:19:12  joan
// changes from 1.2
//
// Revision 1.30  2003/10/31 19:21:27  joan
// change constructor signature
//
// Revision 1.29  2003/10/24 23:08:24  joan
// fb fixes
//
// Revision 1.28  2003/10/16 20:39:13  joan
// try to make PDG run faster
//
// Revision 1.27  2003/10/07 21:50:07  joan
// fix queuedABR
//
// Revision 1.26  2003/10/06 20:35:00  joan
// fb fix
//
// Revision 1.25  2003/10/02 15:09:20  joan
// fb fix
//
// Revision 1.24  2003/10/01 15:22:46  joan
// work on queuedABR
//
// Revision 1.23  2003/09/26 20:46:28  joan
// add queuedABR method
//
// Revision 1.22  2003/09/23 23:53:00  joan
// work on upgrade
//
// Revision 1.21  2003/09/22 15:09:19  joan
// work on upgrade paths
//
// Revision 1.20  2003/09/10 18:29:03  joan
// fix CR
//
// Revision 1.19  2003/08/28 16:28:05  joan
// adjust link method to have link option
//
// Revision 1.18  2003/08/25 17:15:50  joan
// move changes from v1.1.1
//
// Revision 1.17  2003/07/15 19:36:51  joan
// move changes from v111
//
// Revision 1.16  2003/07/02 22:54:56  joan
// fix classification
//
// Revision 1.15  2003/07/02 16:10:45  joan
// fix geo
//
// Revision 1.14  2003/06/30 23:17:53  joan
// move changes from v111
//
// Revision 1.13  2003/06/25 18:44:01  joan
// move changes from v111
//
// Revision 1.10.2.4  2003/06/19 19:54:01  joan
// working on HW Upgrade
//
// Revision 1.10.2.3  2003/06/11 20:39:20  joan
// fix bug
//
// Revision 1.10.2.2  2003/06/11 19:48:59  joan
// add check for ANNCODENAME
//
// Revision 1.10.2.1  2003/06/06 19:15:45  joan
// change signature on method viewMissingData
//
// Revision 1.10  2003/05/29 00:03:37  joan
// fix feedback
//
// Revision 1.9  2003/05/13 17:41:49  joan
// fixes for report
//
// Revision 1.8  2003/05/08 17:38:25  joan
// fix fb
//
// Revision 1.7  2003/05/01 17:08:43  joan
// fix code for xml
//
// Revision 1.6  2003/04/29 19:55:20  joan
// fix bugs
//
// Revision 1.5  2003/04/23 22:32:41  joan
// set profile valon effon
//
// Revision 1.4  2003/04/18 23:30:20  joan
// fix bugs
//
// Revision 1.3  2003/04/18 21:03:50  joan
// fix bugs
//
// Revision 1.2  2003/04/18 20:03:16  joan
// add HWUpgrade
//
// Revision 1.1  2003/04/17 17:49:08  joan
// add Hardware Upgrade PDG
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

public class HWUpgradePIPDG extends PDGActionItem {

  static final long serialVersionUID = 20011106L;

	private String m_strAfSerType = null;
	private String m_strAfFrSerType = null;
	private String m_strAfMachType = null;
	private String m_strAfModel = null;
	private Vector m_afToFUPVec = new Vector();
	private String m_strAfFromMachType = null;
	private String m_strAfFromModel = null;
	private String m_strAnnCodeName = null;
	private Vector m_afFromFUPVec = new Vector();

	private	EANList m_availList = new EANList();
	private Vector m_vctReturnEntityKeys = new Vector();
	private EANList m_opList = new EANList();
	//private EANList m_fupList = new EANList();
	private EntityItem m_eiToProc = null;
	private EntityItem m_eiToICard = null;
	private EntityItem m_eiFromProc = null;
	private EntityItem m_eiFromICard = null;
	private StringBuffer m_sbData = new StringBuffer();
	private Vector m_upgradeVec = new Vector();
	public static final int UPGRADELIMIT= 100;
	private OPICMList m_processedComboList = new OPICMList();
	private OPICMList m_checkedSearchList = new OPICMList();
	private ExtractActionItem m_xai = null;

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: HWUpgradePIPDG.java,v 1.33 2008/09/04 21:46:54 wendy Exp $");
  	}


	public HWUpgradePIPDG(EANMetaFoundation  _mf, HWUpgradePIPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

	public HWUpgradePIPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("HWUpgradePIPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "HWUpgradePIPDG";
  	}

	public String getStepDescription(int iStep) {
		if (iStep == 1) {
			return "Select Upgrade";
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

				int r = _eiRst.getRowIndex(m_eiPDG.getEntityType() + ":AFINFO");
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
							if (strFrom.indexOf("|") >= 0 && strTo.indexOf("|") < 0) {
								pi.setEditable(false);
							}
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

	protected void generateData(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSearchKey) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = "HWUpgradePIPDG generateData";
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
						} else {
							foundEI = m_utility.findEntityItem(m_eiList, strEntityType, strAttributes) ;
							if (foundEI == null) {
								EntityItem[] aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
								if (aei.length > 0) {
									foundEI = aei[0];
									m_eiList.put(foundEI);
								}
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
							// update attributes for found ei
							OPICMList attList = m_utility.getAttributeListForUpdate(foundEI.getEntityType(), strAttributes);
							m_utility.updateAttribute(_db, _prof, foundEI, attList);
							attList = null;

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

						m_eiList.put(ei);

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

	public StringBuffer checkMissingDataAgain(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		return checkMissingData(_db, _prof, _bGenData);
	}

	public void savePDGStatus(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		if (m_sbActivities != null && m_utility != null) {
			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
		}
	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " HWUpgradePIPDG checkMissingData method";

		m_eiBaseCOF = (EntityItem)m_eiList.get("TOBASE");

		for (int i=0; i < m_upgradeVec.size(); i++) {
			String str = (String)m_upgradeVec.elementAt(i);
			int index = str.indexOf(":");
			String strFrom = str.substring(0, index);
			String strTo = str.substring(index+1);

			String strFromProc = null;
			String strToProc = null;
			String strFromICard = null;
			String strToICard = null;

			if (m_processedComboList.get(strFrom + strTo) == null) {
				StringTokenizer st1 = new StringTokenizer(strFrom, "|");
				int c =0;
				while (st1.hasMoreTokens()) {
					String s = st1.nextToken();
					if (c==0) {
						strFromProc = s;
						m_eiFromProc = (EntityItem)m_eiList.get(s+"FUP");
					} else if (c==1) {
						strFromICard = s;
						m_eiFromICard = (EntityItem)m_eiList.get(s+"FUP");
					}
					c++;
				}

				st1 = new StringTokenizer(strTo, "|");
				c =0;
				while (st1.hasMoreTokens()) {
					String s = st1.nextToken();
					if (c==0) {
						strToProc = s;
						m_eiToProc = (EntityItem)m_eiList.get(s+"FUP");
					} else if (c==1) {
						strToICard = s;
						if (strFrom.indexOf("|") >= 0) {
							m_eiToICard = (EntityItem)m_eiList.get(s+"FUP");
						} else {
							m_eiToICard = (EntityItem)m_eiList.get(s+"OOF");
						}
					}
					c++;
				}

				String strFileName = "";
				OPICMList infoList = new OPICMList();

				if (strFromProc != null && strToProc != null && strFromICard == null && strToICard == null) {
					// Upgrade from PROC to PROC
					strFileName = "PDGtemplates/HWUpgradeProc1toProc2.txt";
					if (m_strAfSerType.equals("iSeries")) {
						infoList.put("PROCUPGTYPE", "100");
					}
				} else if (strFromProc != null && strToProc != null && strFromICard == null && strToICard != null) {
					// Upgrade from PROC to PROC|ICARD
					strFileName = "PDGtemplates/HWUpgradeProc1toProc2_Icard1.txt";
					if (m_strAfSerType.equals("iSeries")) {
						infoList.put("PROCUPGTYPE", "110");
					}
				} else if (strFromProc != null && strToProc != null && strFromICard != null && strToICard != null) {
					// Upgrade from PROC|ICARD to PROC|ICARD

					if (!strFromProc.equals(strToProc) && !strFromICard.equals(strToICard)) {
						//series - iSeries, and rrrr not equal pppp, and dddd not equal iiii
						strFileName = "PDGtemplates/HWUpgradeProc1_Icard1toProc2_Icard2.txt";
						if (m_strAfSerType.equals("iSeries")) {
							infoList.put("PROCUPGTYPE", "140");
						}
					} else if (strFromProc.equals(strToProc) && !strFromICard.equals(strToICard)) {
						//series - iSeries, and rrrr = pppp, and dddd not equal iiii
						strFileName = "PDGtemplates/HWUpgradeProc1_Icard1toProc1_Icard2.txt";
						if (m_strAfSerType.equals("iSeries")) {
							infoList.put("PROCUPGTYPE", "120");
						}
					} else if (!strFromProc.equals(strToProc) && strFromICard.equals(strToICard)) {
						//series - iSeries, and rrrr not equal pppp, and dddd = iiii
						strFileName = "PDGtemplates/HWUpgradeProc1_Icard1toProc2_Icard1.txt";
						if (m_strAfSerType.equals("iSeries")) {
							infoList.put("PROCUPGTYPE", "130");
						}
					}
				}

				if (strFileName.length() > 0) {
//					m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

					infoList.put("PDG", m_eiPDG);
					infoList.put("TOPROC", m_eiToProc);
					infoList.put("FROMPROC", m_eiFromProc);
					infoList.put("GEOIND", "GENAREASELECTION");
					infoList.put("OFPROJ", m_eiOFPROJ);

					if (m_eiToICard != null) {
						infoList.put("TOICARD", m_eiToICard);
						EANFlagAttribute att = (EANFlagAttribute)m_eiToICard.getAttribute("FUPSUBCAT");
						if (att != null) {
							if (att.isSelected("711")) {		//InteractiveCard
								infoList.put("TOICARDSUBCAT", "600");
							} else if (att.isSelected("603")) {		//Package
								infoList.put("TOICARDSUBCAT", "603");
							} else if (att.isSelected("602")) {		//Memory
								infoList.put("TOICARDSUBCAT", "602");
							} else if (att.isSelected("604")) {		//Processor
								infoList.put("TOICARDSUBCAT", "604");
							} else {
								infoList.put("TOICARDSUBCAT", "405");
							}
						}
					}
					if (m_eiFromICard != null) {
						infoList.put("FROMICARD", m_eiFromICard);
					}
					_prof = m_utility.setProfValOnEffOn(_db, _prof);

					TestPDG pdgObject = new TestPDG(_db, _prof, m_eiBaseCOF, infoList, m_xai, strFileName);
					StringBuffer sbMissing = pdgObject.getMissingEntities();
					pdgObject = null;
					infoList = null;

					if (_bGenData) {
						generateData(_db, _prof, sbMissing, strFrom);
						//should save the combo to prevent from checking it again.
						m_processedComboList.put(strFrom + strTo, strFrom + strTo);
					}
					m_sbData.append(sbMissing.toString());
				}
			}
		}
		return m_sbData;
	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
		//String strTraceBase = " HWUpgradePIPDG checkPDGAttribute method";
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
						//	sFlagClass = amf[f].getFlagCode().trim();
						//	index = f;
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
			} else if (att.getKey().equals("AFTOFUPS")) {
				m_afToFUPVec = m_utility.sepLongText(textAtt);
			} else if (att.getKey().equals("FROMMACHTYPE")) {
				m_strAfFromMachType = textAtt;
			} else if (att.getKey().equals("FROMMODEL")) {
				m_strAfFromModel = textAtt;
			} else if (att.getKey().equals("AFFROMFUPS")) {
				m_afFromFUPVec = m_utility.sepLongText(textAtt);
			} else if (att.getKey().equals("ANNCODENAME")) {
				m_strAnnCodeName = sFlagAtt;
			} else if (att.getKey().equals("AFHWFRSERTYPE")) {
				m_strAfFrSerType = sFlagAtt;
			} else if (att.getKey().equals("AFINFO")) {
				m_upgradeVec = m_utility.sepLongText(textAtt);
			}
		}

		if (m_afToFUPVec.size() <= 0) {
			m_SBREx.add("To FUP(s) is required for HW Upgrade");
		}

		if (m_afFromFUPVec.size() <= 0) {
			m_SBREx.add("From FUP(s) is required for HW Upgrade");
		}

		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}

		if (m_upgradeVec == null || m_upgradeVec.size() <= 0)  {
			m_SBREx.add("Please select upgrade path.");
		} else if (m_upgradeVec.size() > UPGRADELIMIT) {
			m_SBREx.add("Number of selected upgrade paths exceeds the limit of " + UPGRADELIMIT + ". (ok)");
		}
	}

	protected void resetVariables() {
		m_strAfSerType = null;
		m_strAfFrSerType = null;
		m_strAfMachType = null;
		m_strAfModel = null;
		m_strAfFromMachType = null;
		m_strAfFromModel = null;
		m_afFromFUPVec = new Vector();

		m_vctReturnEntityKeys = new Vector();
		m_opList = new EANList();
		m_eiList = new EANList();
		//m_fupList = new EANList();
		m_eiToProc = null;
		m_eiToICard = null;
		m_eiFromProc = null;
		m_eiFromICard = null;
		m_processedComboList = new OPICMList();
		m_checkedSearchList = new OPICMList();
	}

	public void queuedABR(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " HWUpgradePIPDG queuedABR method";
		_db.debug(D.EBUG_DETAIL, strTraceBase);
		m_SBREx = new SBRException();

		if (m_eiPDG == null) {
			System.out.println("PDG entity is null");
			return;
		}

		OPICMList attL = new OPICMList();
		attL.put("AFHWUPGRADEABR01", "AFHWUPGRADEABR01=0020");
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		m_utility.updateAttribute(_db, _prof, m_eiPDG, attL);
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " HWUpgradePIPDG executeAction method";

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

		eg = el.getEntityGroup("OFDEVLPROJ");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiOFPROJ = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiOFPROJ != null, "Offering Project entity is null");

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
		String strTraceBase = " HWUpgradePIPDG executeAction method";
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
		// make sure the To Base COMMERCIALOF for the MTM already exists
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
       	    m_SBREx.add("There are " + aeiCOM.length + "  existing Hardware-System-Base-" + m_strAfSerType + " Commercial Offering for " + m_strAfMachType + "-" + m_strAfModel);
       	} else {
       	    m_eiList.put("TOBASE", aeiCOM[0]);
       	}

		// make sure the To FUPs exists
        strSai = (String)m_saiList.get("FUP");

		for (int i=0; i < m_upgradeVec.size(); i++) {
			String str = (String)m_upgradeVec.elementAt(i);
			int index = str.indexOf(":");
			String strFrom = str.substring(0, index);
			String strTo = str.substring(index+1);

			String strFromProc = null;
			String strFromIcard = null;
			String strToProc = null;
			String strToIcard = null;

			if (strFrom != null) {
				StringTokenizer st = new StringTokenizer(strFrom, "|");
				int count =0;
				while (st.hasMoreTokens()) {
					String s = st.nextToken();
					if (m_eiList.get(s+"FUP") == null) {
						strSai = (String)m_saiList.get("FUP");
						sb = new StringBuffer();
						sb.append("map_FUPCAT=100;");
						if (count == 0) {
							sb.append("map_FUPSUBCAT=604;");
							strFromProc = s;
						} else {
							strFromIcard = s;
						}
						sb.append("map_SERIES=" + m_strAfFrSerType + ";");
						sb.append("map_FEATURECODE=" + s );

						if (m_checkedSearchList.get(sb.toString()) == null) {
							EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FUP", sb.toString());
							if (aeiFUP.length <= 0 ) {
								m_SBREx.add("Hardware FUP for series " + m_strAfFrSerType + " and featurecode " + s + " must exist.");
							} else if (aeiFUP.length > 1) {
								m_SBREx.add("There are " + aeiFUP.length + " existing Hardware FUP for series " + m_strAfFrSerType + " and featurecode " + s );
							} else {
								m_eiList.put(s+"FUP", aeiFUP[0]);
							}
							m_checkedSearchList.put(sb.toString(), sb.toString());
						}
					}
					count++;
				}
			}

			if (strTo != null) {
				StringTokenizer st = new StringTokenizer(strTo, "|");
				int count =0;
				while (st.hasMoreTokens()) {
					String s = st.nextToken();

					if(count == 0) {
						strToProc = s;
						if (m_eiList.get(s+"FUP") == null) {
							strSai = (String)m_saiList.get("FUP");
							sb = new StringBuffer();
							sb.append("map_FUPCAT=100;");
							sb.append("map_FUPSUBCAT=604;");
							sb.append("map_SERIES=" + m_strAfSerType + ";");
							sb.append("map_FEATURECODE=" + s );

							if (m_checkedSearchList.get(sb.toString()) == null) {
								EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FUP", sb.toString());
								if (aeiFUP.length <= 0 ) {
									m_SBREx.add("Hardware-Processor FUP for series " + m_strAfSerType + " and featurecode " + s + " must exist.");
								} else if (aeiFUP.length > 1) {
									m_SBREx.add("There are " + aeiFUP.length + " existing Hardware FUP for series " + m_strAfSerType + " and featurecode " + s );
								} else {
									m_eiList.put(s+"FUP", aeiFUP[0]);
								}
								m_checkedSearchList.put(sb.toString(), sb.toString());
							}
						}
					} else if (count ==1) {
						strToIcard = s;
						if (strFrom.indexOf("|") > -1) {
							// upgrade Proc|Icard to Proc|Icard, need to look for FUP
							if (m_eiList.get(s+"FUP") == null) {
								strSai = (String)m_saiList.get("FUP");
								sb = new StringBuffer();
								sb.append("map_FUPCAT=100;");
								sb.append("map_SERIES=" + m_strAfSerType + ";");
								sb.append("map_FEATURECODE=" + s );

								if (m_checkedSearchList.get(sb.toString()) == null) {
									EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FUP", sb.toString());
									if (aeiFUP.length <= 0 ) {
										m_SBREx.add("Hardware FUP for series " + m_strAfSerType + " and featurecode " + s + " must exist.");
									} else if (aeiFUP.length > 1) {
										m_SBREx.add("There are " + aeiFUP.length + " existing Hardware FUP for series " + m_strAfSerType + " and featurecode " + s );
									} else {
										m_eiList.put(s+"FUP", aeiFUP[0]);
									}
									m_checkedSearchList.put(sb.toString(), sb.toString());
								}
							}
						} else {
							//upgrade Proc to Proc|Icard, need to look for ORDEROF
							if (m_eiList.get(s+"OOF") == null) {
								strSai = (String)m_saiList.get("ORDEROF");
								sb = new StringBuffer();
								sb.append("map_OOFCAT=100;");
								sb.append("map_OOFSUBCAT=500;");
								sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
								sb.append("map_MODEL=" + m_strAfModel + ";");
								sb.append("map_FEATURECODE=" + s );

								if (m_checkedSearchList.get(sb.toString()) == null) {
									EntityItem[] aeiOOF = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "ORDEROF", sb.toString());
									if (aeiOOF.length <= 0 ) {
										m_SBREx.add("Hardware-FeatureCode ORDEROF for " + m_strAfMachType + "-" + m_strAfModel + " and featurecode " + s + " must exist.");
									} else if (aeiOOF.length > 1) {
										m_SBREx.add("There are " + aeiOOF.length + " existing Hardware-FeatureCode ORDEROF for " + m_strAfMachType + "-" + m_strAfModel + " and featurecode " + s);
									} else {
										m_eiList.put(s+"OOF", aeiOOF[0]);
									}
									m_checkedSearchList.put(sb.toString(), sb.toString());
								}
							}
						}
					}

					count++;
				}
			}

			// check for upgrade P1F1 to P1F2 and upgrade P1F1 to P2F1
			if (strFromProc != null && strFromIcard != null && strToProc != null && strToIcard != null) {
				strSai = (String)m_saiList.get("ORDEROF");
				//If upgrade path of the type P1F1 to P1F2 is selected, an OOF classified
				//Hardware-FeatureCode-%-% for the input values of MACHTYPE, MODEL and
				//processor feature code (pppp) must exist.
				if (strFromProc.equals(strToProc)) {
					if (m_eiList.get(strFromProc+"OOF") == null) {
						sb = new StringBuffer();
						sb.append("map_OOFCAT=100;");
						sb.append("map_OOFSUBCAT=500;");
						sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
						sb.append("map_MODEL=" + m_strAfModel + ";");
						sb.append("map_FEATURECODE=" + strFromProc );

						if (m_checkedSearchList.get(sb.toString()) == null) {
							EntityItem[] aeiOOF = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "ORDEROF", sb.toString());
							if (aeiOOF.length <= 0 ) {
								m_SBREx.add("Hardware-FeatureCode ORDEROF for " + m_strAfMachType + "-" + m_strAfModel + " and featurecode " + strFromProc + " must exist.");
							} else if (aeiOOF.length > 1) {
								m_SBREx.add("There are " + aeiOOF.length + " existing Hardware-FeatureCode ORDEROF for " + m_strAfMachType + "-" + m_strAfModel + " and featurecode " + strFromProc);
							} else {
								m_eiList.put(strFromProc+"OOF", aeiOOF[0]);
							}
							m_checkedSearchList.put(sb.toString(), sb.toString());
						}
					}
				}

				//If upgrade path of the type P1F1 to P2F1 is selected, an OOF classified
				//Hardware-FeatureCode-%-% for the input values of MACHTYPE, MODEL and
				//Icard or Package feature code (iiii) must exist.
				if (strFromIcard.equals(strToIcard)) {
					if (m_eiList.get(strFromIcard+"OOF") == null) {
						sb = new StringBuffer();
						sb.append("map_OOFCAT=100;");
						sb.append("map_OOFSUBCAT=500;");
						sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
						sb.append("map_MODEL=" + m_strAfModel + ";");
						sb.append("map_FEATURECODE=" + strFromProc );

						if (m_checkedSearchList.get(sb.toString()) == null) {
							EntityItem[] aeiOOF = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "ORDEROF", sb.toString());
							if (aeiOOF.length <= 0 ) {
								m_SBREx.add("Hardware-FeatureCode ORDEROF for " + m_strAfMachType + "-" + m_strAfModel + " and featurecode " + strFromIcard + " must exist.");
							} else if (aeiOOF.length > 1) {
								m_SBREx.add("There are " + aeiOOF.length + " existing Hardware-FeatureCode ORDEROF for " + m_strAfMachType + "-" + m_strAfModel + " and featurecode " + strFromIcard);
							} else {
								m_eiList.put(strFromIcard+"OOF", aeiOOF[0]);
							}
							m_checkedSearchList.put(sb.toString(), sb.toString());
						}
					}
				}
			}
		}

		// check for Hardware-ModelUpgrade OOF if From MTM is different from the To MTM
		if (! m_strAfFromMachType.equals(m_strAfMachType) || ! m_strAfFromModel.equals(m_strAfModel)) {
			strSai = (String)m_saiList.get("ORDEROF");
			sb = new StringBuffer();
			sb.append("map_OOFCAT=100;");
			String s = "";
			if (m_strAfFromMachType.equals(m_strAfMachType)) {
				sb.append("map_OOFSUBCAT=504;");
				s = "Hardware-ModelUpgrade";
			} else {
				sb.append("map_OOFSUBCAT=503;");
				sb.append("map_FROMMACHTYPE=" + m_strAfFromMachType + ";");
				s = "Hardware-ModelConvert";
			}
			sb.append("map_OOFGRP=405;");
			sb.append("map_OOFSUBGRP=405;");
			sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
			sb.append("map_MODEL=" + m_strAfModel + ";");
			sb.append("map_FROMMODEL=" + m_strAfFromModel);

			EntityItem[] aeiOOF = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "ORDEROF", sb.toString());
			if (aeiOOF.length <= 0 ) {
				m_SBREx.add("ORDEROF for " + s + " from " + m_strAfFromMachType + "-" + m_strAfFromModel + " to " + m_strAfMachType + "-" + m_strAfModel +
					" doesn't exist. Please use Hardware Upgrade Model Data Generator to create one");
			}
		}
	}
}
