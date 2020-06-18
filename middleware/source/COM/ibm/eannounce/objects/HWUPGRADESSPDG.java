//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: HWUPGRADESSPDG.java,v $
// Revision 1.4  2008/09/04 21:46:54  wendy
// Cleanup RSA warnings
//
// Revision 1.3  2005/02/28 20:29:51  joan
// add throw exception
//
// Revision 1.2  2004/04/13 20:15:23  joan
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
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;

public class HWUPGRADESSPDG extends PDGActionItem {

  static final long serialVersionUID = 20011106L;

	//private String m_strAfSerType = null;
	//private String m_strAfFrSerType = null;
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
	private EntityItem m_eiToServ = null;
	private EntityItem m_eiToPack = null;
	private EntityItem m_eiFromServ = null;
	private EntityItem m_eiFromPack = null;
	private StringBuffer m_sbData = new StringBuffer();
	private Vector m_upgradeVec = new Vector();
	public static final int UPGRADELIMIT= 100;
	private OPICMList m_processedComboList = new OPICMList();

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: HWUPGRADESSPDG.java,v 1.4 2008/09/04 21:46:54 wendy Exp $");
  	}


	public HWUPGRADESSPDG(EANMetaFoundation  _mf, HWUPGRADESSPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

	public HWUPGRADESSPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("HWUPGRADESSPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "HWUPGRADESSPDG";
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
							if (strFrom.indexOf("|") >= 0 && strTo.indexOf("|") >= 0) {
								pi.setEditable(true);
							} else {
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
		//String strTraceBase = "HWUPGRADESSPDG generateData";
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
						foundEI = m_utility.findEntityItem(m_eiList, strEntityType, strAttributes) ;
						if (foundEI == null) {
							EntityItem[] aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
							if (aei.length > 0) {
								foundEI = aei[0];
								m_eiList.put(foundEI);

								if (foundEI.getEntityType().equals("AVAIL")) {
									m_availList.put(sGeo, foundEI);
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
		//String strTraceBase = " HWUPGRADESSPDG checkMissingData method";

		m_eiBaseCOF = (EntityItem)m_eiList.get("TOBASE");

		for (int i=0; i < m_upgradeVec.size(); i++) {
			String str = (String)m_upgradeVec.elementAt(i);
			int index = str.indexOf(":");
			String strFrom = str.substring(0, index);
			String strTo = str.substring(index+1);

			String strFromServ = null;
			String strToServ = null;
			String strFromPack = null;
			String strToPack = null;

			if (m_processedComboList.get(strFrom + strTo) == null) {
				StringTokenizer st1 = new StringTokenizer(strFrom, "|");
				int c =0;
				while (st1.hasMoreTokens()) {
					String s = st1.nextToken();
					if (c==0) {
						strFromServ = s;
					} else if (c==1) {
						strFromPack = s;
					}
					c++;
				}

				st1 = new StringTokenizer(strTo, "|");
				c =0;
				while (st1.hasMoreTokens()) {
					String s = st1.nextToken();
					if (c==0) {
						strToServ = s;
					} else if (c==1) {
						strToPack = s;
					}
					c++;
				}

				String strFileName = "";
				OPICMList infoList = new OPICMList();

				if (strFromServ != null && strToServ != null && strFromPack != null && strToPack != null) {
					// Upgrade from SERV|PACK to SERV|PACK
					m_eiFromPack = (EntityItem)m_eiList.get(strFromPack+"FUP");
					m_eiToPack = (EntityItem)m_eiList.get(strToPack+"FUP");

					infoList.put("FROMPACK", m_eiFromPack);
					infoList.put("TOPACK", m_eiToPack);
					if (!strFromServ.equals(strToServ) && !strFromPack.equals(strToPack)) {
						//rrrr not equal pppp, and dddd not equal iiii
						m_eiFromServ = (EntityItem)m_eiList.get(strFromServ+"FUP");
						m_eiToServ = (EntityItem)m_eiList.get(strToServ+"FUP");
						infoList.put("TOSERV", m_eiToServ);
						infoList.put("FROMSERV", m_eiFromServ);
						strFileName = "PDGtemplates/HWUpgradeServer1_Pack1toServer2_Pack2.txt";
					} else if (strFromServ.equals(strToServ) && !strFromPack.equals(strToPack)) {
						//rrrr = pppp, and dddd not equal iiii
						m_eiFromServ = (EntityItem)m_eiList.get(strFromServ+"OOF");
						infoList.put("FROMSERV", m_eiFromServ);
						strFileName = "PDGtemplates/HWUpgradeServer1_Pack1toServer1_Pack2.txt";
					}
				}

				if (strFileName.length() > 0) {
					infoList.put("PDG", m_eiPDG);
					infoList.put("GEOIND", "GENAREASELECTION");
					infoList.put("OFPROJ", m_eiOFPROJ);

					if (m_eiToPack != null) {
						EANFlagAttribute att = (EANFlagAttribute)m_eiToPack.getAttribute("FUPSUBCAT");
						if (att != null) {
							if (att.isSelected("711")) {		//InteractiveCard
								infoList.put("TOPACKSUBCAT", "600");
							} else if (att.isSelected("603")) {		//Package
								infoList.put("TOPACKSUBCAT", "603");
							} else if (att.isSelected("602")) {		//Memory
								infoList.put("TOPACKSUBCAT", "602");
							} else if (att.isSelected("604")) {		//Processor
								infoList.put("TOPACKSUBCAT", "604");
							} else if (att.isSelected("357")) {		//Server
								infoList.put("TOPACKSUBCAT", "357");
							} else {
								infoList.put("TOPACKSUBCAT", "405");
							}
						}
					}

					_prof = m_utility.setProfValOnEffOn(_db, _prof);

					TestPDG pdgObject = new TestPDG(_db, _prof, m_eiBaseCOF, infoList, m_PDGxai, strFileName);
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
		//String strTraceBase = " HWUPGRADESSPDG checkPDGAttribute method";
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
				//m_strAfSerType = sFlagAtt;
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
				//m_strAfFrSerType = sFlagAtt;
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
		//m_strAfSerType = null;
		//m_strAfFrSerType = null;
		m_strAfMachType = null;
		m_strAfModel = null;
		m_strAfFromMachType = null;
		m_strAfFromModel = null;
		m_afFromFUPVec = new Vector();

		m_vctReturnEntityKeys = new Vector();
		m_opList = new EANList();
		m_eiList = new EANList();
		m_eiToServ = null;
		m_eiToPack = null;
		m_eiFromServ = null;
		m_eiFromPack = null;
		m_processedComboList = new OPICMList();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " HWUPGRADESSPDG executeAction method";

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
		String strTraceBase = " HWUPGRADESSPDG executeAction method";
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
			strData = checkMissingData(_db, _prof, true).toString();
			m_sbActivities.append(m_utility.getActivities().toString());
			OPICMList attList = new OPICMList();
			attList.put("AFPDGERRORMSG", "AFPDGERRORMSG= ");
			attList.put("AFPDGSTATUS", "AFPDGSTATUS=" + PDGUtility.STATUS_COMPLETE + getLongDescription());
			m_utility.updateAttribute(_db, _prof, m_eiPDG, attList);

//			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, m_utility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
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
		sb.append("map_COFSUBGRP=400;");
		sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
		sb.append("map_MODEL=" + m_strAfModel);

		EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "COMMERCIALOF", sb.toString());
       	if (aeiCOM.length <= 0 ) {
       	    m_SBREx.add("The Hardware-System-Base-iSeries Commercial Offering must exist for " + m_strAfMachType + "-" + m_strAfModel);
       	} else if (aeiCOM.length > 1) {
       	    m_SBREx.add("There are " + aeiCOM.length + "  existing Hardware-System-Base-iSeries Commercial Offering for " + m_strAfMachType + "-" + m_strAfModel);
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

			String strFromServ = null;
			String strFromPack = null;
			String strToServ = null;
			String strToPack = null;

			if (strFrom != null) {
				StringTokenizer st = new StringTokenizer(strFrom, "|");
				int count =0;
				while (st.hasMoreTokens()) {
					String s = st.nextToken();
					if (count == 0) {
						strFromServ = s;
					} else {
						strFromPack = s;
					}
					count++;
				}
			}

			if (strTo != null) {
				StringTokenizer st = new StringTokenizer(strTo, "|");
				int count =0;
				while (st.hasMoreTokens()) {
					String s = st.nextToken();
					if (count == 0) {
						strToServ = s;
					} else {
						strToPack = s;
					}
					count++;
				}
			}

			if (strFromServ != null && strFromPack != null && strToServ != null && strToPack != null) {
				if (strFromServ.equals(strToServ)) {
					// rrrr=ssss, search for Software-FeatureCode OOF with MTM and ssss
					if (m_eiList.get(strFromServ+"OOF") == null) {
						strSai = (String)m_saiList.get("ORDEROF");
						sb = new StringBuffer();
						sb.append("map_OOFCAT=100;map_OOFSUBCAT=500;");
						sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
						sb.append("map_MODEL=" + m_strAfModel + ";");
						sb.append("map_FEATURECODE=" + strFromServ );

						if (m_utility.findEntityItem(m_eiList, "ORDEROF", sb.toString()) == null) {
							EntityItem[] aeiOOF = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "ORDEROF", sb.toString());
							if (aeiOOF.length <= 0 ) {
								m_SBREx.add("Hardware-FeatureCode ORDEROF for " + m_strAfMachType + "-" + m_strAfModel + " and featurecode " + strFromServ + " must exist.");
							} else if (aeiOOF.length > 1) {
								m_SBREx.add("There are " + aeiOOF.length + " existing Hardware-FeatureCode ORDEROF for " + m_strAfMachType + "-" + m_strAfModel + " and featurecode " + strFromServ);
							} else {
								m_eiList.put(strFromServ+"OOF", aeiOOF[0]);
							}
						}
					}
				} else {
					// rrrr not equals ssss, search for From Server FUP
					if (m_eiList.get(strFromServ+"FUP") == null) {
						strSai = (String)m_saiList.get("FUP");
						sb = new StringBuffer();
						sb.append("map_FUPCAT=100;");
						sb.append("map_FUPSUBCAT=357;");
						sb.append("map_FUPGRP=400;");
						sb.append("map_SERIES=6129;");
						sb.append("map_FEATURECODE=" + strFromServ );
						if (m_utility.findEntityItem(m_eiList, "FUP", sb.toString()) == null) {
							EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FUP", sb.toString());
							if (aeiFUP.length <= 0 ) {
								m_SBREx.add("Hardware-Server-iSeries FUP for SERIES=iSeries and featurecode " + strFromServ + " must exist.");
							} else if (aeiFUP.length > 1) {
								m_SBREx.add("There are " + aeiFUP.length + " existing Hardware-Server-iSeries FUP for SERIES=iSeries and featurecode " + strFromServ );
							} else {
								m_eiList.put(strFromServ+"FUP", aeiFUP[0]);
							}
						}
					}

					// search for To Server FUP
					if (m_eiList.get(strToServ+"FUP") == null) {
						strSai = (String)m_saiList.get("FUP");
						sb = new StringBuffer();
						sb.append("map_FUPCAT=100;");
						sb.append("map_FUPSUBCAT=357;");
						sb.append("map_FUPGRP=400;");
						sb.append("map_SERIES=6129;");
						sb.append("map_FEATURECODE=" + strToServ );
						if (m_utility.findEntityItem(m_eiList, "FUP", sb.toString()) == null) {
							EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FUP", sb.toString());
							if (aeiFUP.length <= 0 ) {
								m_SBREx.add("Hardware-Server-iSeries FUP for SERIES=iSeries and featurecode " + strToServ + " must exist.");
							} else if (aeiFUP.length > 1) {
								m_SBREx.add("There are " + aeiFUP.length + " existing Hardware-Server-iSeries FUP for SERIES=iSeries and featurecode " + strToServ );
							} else {
								m_eiList.put(strToServ+"FUP", aeiFUP[0]);
							}
						}
					}
				}

				// search for From Pack Hardware-Package-iSeries FUP
				if (m_eiList.get(strFromPack+"FUP") == null) {
					strSai = (String)m_saiList.get("FUP");
					sb = new StringBuffer();
					sb.append("map_FUPCAT=100;");
					sb.append("map_FUPSUBCAT=603;");
					sb.append("map_FUPGRP=400;");
					sb.append("map_SERIES=6129;");
					sb.append("map_FEATURECODE=" + strFromPack );
					if (m_utility.findEntityItem(m_eiList, "FUP", sb.toString()) == null) {
						EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FUP", sb.toString());
						if (aeiFUP.length <= 0 ) {
							m_SBREx.add("Hardware-Package-iSeries FUP for SERIES=iSeries and featurecode " + strFromPack + " must exist.");
						} else if (aeiFUP.length > 1) {
							m_SBREx.add("There are " + aeiFUP.length + " existing Hardware-Server-iSeries FUP for SERIES=iSeries and featurecode " + strFromPack );
						} else {
							m_eiList.put(strFromPack+"FUP", aeiFUP[0]);
						}
					}
				}

				// search for To Pack Hardware-Package-iSeries FUP
				if (m_eiList.get(strToPack+"FUP") == null) {
					strSai = (String)m_saiList.get("FUP");
					sb = new StringBuffer();
					sb.append("map_FUPCAT=100;");
					sb.append("map_FUPSUBCAT=603;");
					sb.append("map_FUPGRP=400;");
					sb.append("map_SERIES=6129;");
					sb.append("map_FEATURECODE=" + strToPack );
					if (m_utility.findEntityItem(m_eiList, "FUP", sb.toString()) == null) {
						EntityItem[] aeiFUP = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "FUP", sb.toString());
						if (aeiFUP.length <= 0 ) {
							m_SBREx.add("Hardware-Package-iSeries FUP for SERIES=iSeries and featurecode " + strToPack + " must exist.");
						} else if (aeiFUP.length > 1) {
							m_SBREx.add("There are " + aeiFUP.length + " existing Hardware-Server-iSeries FUP for SERIES=iSeries and featurecode " + strToPack );
						} else {
							m_eiList.put(strToPack+"FUP", aeiFUP[0]);
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
