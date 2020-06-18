//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: HWProdInitialPDG.java,v $
// Revision 1.35  2008/09/04 21:33:01  wendy
// Cleanup RSA warnings
//
// Revision 1.34  2003/10/31 19:21:27  joan
// change constructor signature
//
// Revision 1.33  2003/10/30 19:33:14  joan
// add commit statement
//
// Revision 1.32  2003/10/29 22:01:39  joan
// comment out debug lines
//
// Revision 1.31  2003/10/28 21:54:45  joan
// fb fixes
//
// Revision 1.30  2003/10/24 23:08:24  joan
// fb fixes
//
// Revision 1.29  2003/10/17 17:42:11  joan
// fb fix
//
// Revision 1.28  2003/10/03 18:02:43  joan
// fix fb52329
//
// Revision 1.27  2003/09/29 20:26:08  joan
// fix fb52329
//
// Revision 1.26  2003/09/19 17:41:53  joan
// check memory
//
// Revision 1.25  2003/08/25 17:15:50  joan
// move changes from v1.1.1
//
// Revision 1.24  2003/07/15 19:36:51  joan
// move changes from v111
//
// Revision 1.23  2003/07/11 22:42:56  dave
// fixing a template
//
// Revision 1.22  2003/07/03 22:44:53  joan
// move changes from v111
//
// Revision 1.21  2003/07/02 22:54:56  joan
// fix classification
//
// Revision 1.20  2003/07/02 16:10:45  joan
// fix geo
//
// Revision 1.19  2003/06/25 18:44:01  joan
// move changes from v111
//
// Revision 1.16.2.5  2003/06/24 14:36:52  joan
// fix comments
//
// Revision 1.16.2.4  2003/06/23 23:08:14  joan
// add Package
//
// Revision 1.16.2.3  2003/06/11 20:39:19  joan
// fix bug
//
// Revision 1.16.2.2  2003/06/11 19:48:59  joan
// add check for ANNCODENAME
//
// Revision 1.16.2.1  2003/06/06 19:15:45  joan
// change signature on method viewMissingData
//
// Revision 1.16  2003/05/29 00:03:37  joan
// fix feedback
//
// Revision 1.15  2003/05/13 17:41:52  joan
// fixes for report
//
// Revision 1.14  2003/05/09 23:52:20  joan
// fix report
//
// Revision 1.13  2003/05/08 17:38:24  joan
// fix fb
//
// Revision 1.12  2003/05/05 21:39:15  joan
// work on xml report
//
// Revision 1.11  2003/04/23 22:32:41  joan
// set profile valon effon
//
// Revision 1.10  2003/04/17 20:06:33  joan
// fix bugs
//
// Revision 1.9  2003/04/17 16:37:03  joan
// fix code
//
// Revision 1.8  2003/04/16 19:52:43  joan
// fix PDG creating entity
//
// Revision 1.7  2003/04/15 17:01:38  joan
// fix bugs
//
// Revision 1.6  2003/04/03 22:38:59  joan
// adjust code
//
// Revision 1.5  2003/04/02 20:57:01  joan
// adjust resetVariable
//
// Revision 1.4  2003/04/01 18:43:30  joan
// add HWFeaturePDG
//
// Revision 1.3  2003/03/29 01:11:41  joan
// fix throw ex
//
// Revision 1.2  2003/03/29 00:11:08  joan
// fix bugs
//
// Revision 1.1  2003/03/28 21:36:09  joan
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

public class HWProdInitialPDG extends PDGActionItem {

  static final long serialVersionUID = 20011106L;

	private String m_strAfSerType = null;
	private String m_strAfMachType = null;
	private String m_strAfModel = null;
	private String m_strAfUniqueID = null;
	private String m_strAnnCodeName = null;
	private ExtractActionItem m_xai = null;
  //private Vector m_afOsLevelVec = new Vector();
	private Vector m_afProcessorVec = new Vector();
	private Vector m_afPackageVec = new Vector();

	private	EANList m_availList = new EANList();
	private Vector m_vctReturnEntityKeys = new Vector();
	private EANList m_opList = new EANList();
	private StringBuffer m_sbData = new StringBuffer();
	private EANList m_comboList = new EANList();
	private OPICMList m_processedComboList = new OPICMList();
	private boolean m_bComplete = false;
	int m_iExeCount = 0;
	public static final int COMBOLIMIT= 20;


  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: HWProdInitialPDG.java,v 1.35 2008/09/04 21:33:01 wendy Exp $");
  	}


	public HWProdInitialPDG(EANMetaFoundation  _mf, HWProdInitialPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

	public HWProdInitialPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	  	m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("HWProdInitialPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "HWProdInitialPDG";
  	}

	public String getStepDescription(int iStep) {
		if (iStep == 1) {
			return "Select Combination";
		}
		return "N/A";
	}

	public PDGCollectInfoList collectInfo(int iStep) {
		if (iStep == 1) {
			if (m_eiPDG != null) {
				try {
					EANTextAttribute att1 = (EANTextAttribute)m_eiPDG.getAttribute("AFHWPACKAGE");
					Vector vecPack = new Vector();
					if (att1 != null) {
						String s = (String)att1.get();
						vecPack = m_utility.sepLongText(s);
					}

					Vector vecProc = new Vector();

					EANTextAttribute att2 = (EANTextAttribute)m_eiPDG.getAttribute("AFHWPROCESSOR");
					if (att2 != null) {
						String s = (String)att2.get();
						vecProc = m_utility.sepLongText(s);
					}

					m_InfoList = new PDGCollectInfoList(this, getProfile(), "Processor\\Package");
					for(int i=0; i < vecProc.size(); i++) {
						String strProc = (String)vecProc.elementAt(i);
						PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, getProfile(), false, strProc, " ", strProc);
						m_InfoList.putCollectInfoItem(pi);
						for (int j=0; j< vecPack.size(); j++) {
							String strPack = (String)vecPack.elementAt(j);
							pi = new PDGCollectInfoItem(m_InfoList, getProfile(), false, strProc, strPack, strProc + "|" + strPack);
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
		//String strTraceBase = "HWProdInitialPDG generateData";
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
						// DWB
						stParent = null;
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
						stGeo = null;
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

						if (strEntityType.equals("AVAIL")) {
							// save AVAIL for later link to ORDEROF
							if (sGeo.length() > 0) {
								m_availList.put(sGeo, ei);
							}
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

						attList = null;			// memory
					}
				}
				// dwb more memory
				st1 = null;
			}

			// dwb more memory clean up
			st = null;
			ht = null;

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
		//String strTraceBase = " HWProdInitialPDG checkMissingData method";

	    m_eiBaseCOF = (EntityItem)m_eiList.get("BASE");
		_db.test(m_eiBaseCOF != null, " Base Commercial Offering is null");

        for (int i=0; i < m_comboList.size(); i++) {
			PDGCollectInfoItem ci = (PDGCollectInfoItem)m_comboList.getAt(i);
			String strProcessor = ci.getFirstItem().trim();
			String strPackage = ci.getSecondItem().trim();

			if (m_processedComboList.get(strProcessor + strPackage) == null) {
				int iCCIN = strProcessor.indexOf("|");
				String strCCIN = "";
				String strText1 = "";
				if (iCCIN > -1) {
					strCCIN = strProcessor.substring(0,iCCIN);
					strText1 = strProcessor.substring(iCCIN+1);
				} else {
					strCCIN = strProcessor;
				}

				OPICMList infoList = new OPICMList();
				infoList.put("PDG", m_eiPDG);
				infoList.put("CCIN", strCCIN);
				infoList.put("TEXT1", strText1);
				infoList.put("GEOIND", "GENAREASELECTION");
				if (strPackage != null && strPackage.length() > 0) {
					infoList.put("PACK", strPackage);
				}

				String strFileName = "PDGtemplates/HWProdInitial.txt";
				if (strPackage != null && strPackage.length() > 0) {
					strFileName = "PDGtemplates/HWProdInitial2.txt";
				}

				_prof = m_utility.setProfValOnEffOn(_db, _prof);

				TestPDG pdgObject = new TestPDG(_db, _prof, m_eiBaseCOF, infoList, m_xai, strFileName);
				StringBuffer sbMissing = pdgObject.getMissingEntities();
				pdgObject = null;		//this would free memories
				infoList = null;		//this would also free some memory
				if (_bGenData) {
					generateData(_db, _prof, sbMissing,"");
					//should save the combo to prevent from checking it again.
					m_processedComboList.put(strProcessor + strPackage, strProcessor + strPackage);
				}
				m_sbData.append(sbMissing.toString());
			}
		}

		return m_sbData;
	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
		//String strTraceBase = " HWProdInitialPDG checkPDGAttribute method";
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
			} else if (att.getKey().equals("AFHWUNIQUEID")) {
				m_strAfUniqueID = textAtt;
			} else if (att.getKey().equals("OSLEVEL")) {
				//m_afOsLevelVec = mFlagAtt;
			} else if (att.getKey().equals("AFHWPROCESSOR")) {
				m_afProcessorVec = sepCCIN(textAtt);
			} else if (att.getKey().equals("ANNCODENAME")) {
				m_strAnnCodeName = sFlagAtt;
			} else if (att.getKey().equals("AFHWPACKAGE")) {
				m_afPackageVec =  m_utility.sepLongText(textAtt);
				checkPackageFormat(m_afPackageVec);
			}
		}

		if (m_afProcessorVec.size() > 0 && (m_strAfUniqueID == null || m_strAfUniqueID.length() <= 0)) {
			m_SBREx.add("Unique ID is required if Processor Card has input");
		}

		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}

		if (m_InfoList != null) {
			m_comboList = m_InfoList.getInfoList();
		} else {
			m_comboList = null;
		}

		if (m_comboList == null || m_comboList.size() <= 0)  {
			if (m_afProcessorVec.size() == 1 && m_afPackageVec.size() <= 0) {
				// if input for processor is one, don't have to select the combination.
				// load in the combo list
				m_InfoList = new PDGCollectInfoList(this, getProfile(), "Processor\\Package");
				String strProc = (String)m_afProcessorVec.elementAt(0);
				PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, getProfile(), false, strProc, " ", strProc);
				m_InfoList.putCollectInfoItem(pi);
				m_InfoList.putMatrixValue(strProc + ": ", new Boolean(true));
				m_comboList = m_InfoList.getInfoList();
			} else if (m_afProcessorVec.size() > 1) {
				m_SBREx.add("Please select processor package combination.");
			}
		} else if (m_comboList.size() > COMBOLIMIT) {
			m_SBREx.add("Number of selected combinations exceeds the limit of " + COMBOLIMIT );
		}
	}

	protected void resetVariables() {
		m_strAfSerType = null;
		m_strAfMachType = null;
		m_strAfModel = null;
		m_strAfUniqueID = null;
		//m_afOsLevelVec = new Vector();
		m_afProcessorVec = new Vector();
		m_availList = new EANList();
		m_eiList = new EANList();
		m_processedComboList = new OPICMList();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " HWProdInitialPDG viewMissing method";
		String sReturn = "";
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			m_SBREx = new SBRException();
			resetVariables();
			if (m_eiPDG == null) {
				String s="PDG entity is null";
				return s.getBytes();
			}
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT2");
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
			// DWB only create once
			m_xai = new ExtractActionItem(null, _db, _prof, "EXTCOMMERCIALOF2");

			sReturn = checkMissingData(_db, _prof, false).toString();
			if (sReturn.length() <= 0) {
				sReturn = "Generating data is complete";
			}
			m_sbActivities.append(m_utility.getViewXMLString(sReturn));
			m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());
		} finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
		}
		return sReturn.getBytes();
	}

	private boolean checkCCINFormat(String _s) {
		if (_s.length() > 9) {
			m_SBREx.add(_s + ": max length is 9.");
			m_SBREx.setResetPDGCollectInfo(true);
			return false;
		}

		int iCCIN = _s.indexOf("|");
		String strCCIN = "";
		String strText = "";
		if (iCCIN > -1) {
			strCCIN = _s.substring(0,iCCIN);
			strText = _s.substring(iCCIN+1);
		} else {
			strCCIN = _s;
			m_SBREx.add(strCCIN + " format is NNNN|dddd.");
			m_SBREx.setResetPDGCollectInfo(true);
			return false;
		}

		if (strCCIN.length() > 4) {
			m_SBREx.add(strCCIN + ": max length is 4.");
			m_SBREx.setResetPDGCollectInfo(true);
		}

		if (strText.length() > 6) {
			m_SBREx.add(strText + ": max length is 6.");
			m_SBREx.setResetPDGCollectInfo(true);
		}

		for (int i = 0; i < strCCIN.length(); i++) {
		    char c = strCCIN.charAt(i);
		    if (!(Character.isLetterOrDigit(c) || Character.isSpaceChar(c))) {
				m_SBREx.add(strCCIN + " has to be alpha or integer.");
				m_SBREx.setResetPDGCollectInfo(true);
		        return false;
		    }
		}

		for (int i = 0; i < strText.length(); i++) {
		    char c = strText.charAt(i);
		    if (!(Character.isDigit(c))) {
				m_SBREx.add(strText + " has to be integer.");
				m_SBREx.setResetPDGCollectInfo(true);
		        return false;
		    }
		}

        return true;
	}

	private Vector sepCCIN(String _s) {
		Vector vecReturn = new Vector();
		StringTokenizer st = new StringTokenizer(_s, "\n");
		while (st.hasMoreElements()) {
			String str = st.nextToken().trim();
			if (checkCCINFormat(str)) {
				vecReturn.addElement(str);
			}
		}
		// DWB
		st = null;
		return vecReturn;
	}

	private void checkPackageFormat(Vector _v) {
		for (int i=0; i < _v.size(); i++) {
			String str = (String)_v.elementAt(i);
			checkPackageFormat(str);
		}
	}

	private boolean checkPackageFormat(String _s) {
		if (_s.length() > 6) {
			m_SBREx.add(_s + ": max length is 6.");
			m_SBREx.setResetPDGCollectInfo(true);
			return false;
		}

		for (int j = 0; j < _s.length(); j++) {
		    char c = _s.charAt(j);
		    if (!(Character.isLetterOrDigit(c) || Character.isSpaceChar(c))) {
				m_SBREx.add(_s + " has to be alpha or integer.");
				m_SBREx.setResetPDGCollectInfo(true);
				return false;
		    }
		}
		return true;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " HWProdInitialPDG executeAction method";

		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			m_SBREx = new SBRException();

			m_iExeCount++;
			if (m_iExeCount == 1) {
				m_bComplete = false;
				resetVariables();
				if (m_eiPDG == null) {
					System.out.println("PDG entity is null");
					return;
				}
				_prof = m_utility.setProfValOnEffOn(_db, _prof);
	//			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, m_utility.STATUS_SUBMIT, "", getLongDescription());

				ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT2");
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

				// DWB only create once
				m_xai = new ExtractActionItem(null, _db, _prof, "EXTCOMMERCIALOF2");
			}

			checkMissingData(_db, _prof, true).toString();
			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
			m_iExeCount = 0;
			m_bComplete = true;
		} catch (SBRException ex) {
			m_bComplete = true;
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		} catch (Exception x) {
			m_bComplete = true;
			x.printStackTrace();
		} finally {
			//System.out.println(strTraceBase + " m_bComplete: " + m_bComplete);
			if (!m_bComplete) {
				executeAction(_db, _prof);
			}

            _db.commit();
            _db.freeStatement();
            _db.isPending();
		}

		if (m_sbData.toString().length() <= 0) {
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
		StringBuffer sb = new StringBuffer();
		// make sure the Base COMMERCIALOF for the MTM already exists
			sb.append("map_COFCAT=100;");
			sb.append("map_COFSUBCAT=208;");
			sb.append("map_COFGRP=301;");
			sb.append("map_COFSUBGRP=" + m_strAfSerType + ";");
			sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
			sb.append("map_MODEL=" + m_strAfModel);

		String strSai = (String)m_saiList.get("COMMERCIALOF");
		EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "COMMERCIALOF", sb.toString());
		if (aeiCOM.length <= 0 ) {
		   m_SBREx.add("The Base Commercial Offering must be created before Initial Commercial Offering.");
		} else if (aeiCOM.length > 1) {
		   m_SBREx.add("There are " + aeiCOM.length + " existing Hardware-System-Base-" + m_strAfSerType + " COMMERCIALOF for the same machinetype and model.");
		} else {
		   m_eiList.put("BASE", aeiCOM[0]);
		}

		sb = null;
	}
}
