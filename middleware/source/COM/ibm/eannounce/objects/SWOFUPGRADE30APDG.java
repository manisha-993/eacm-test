// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: SWOFUPGRADE30APDG.java,v $
// Revision 1.21  2008/09/05 21:25:47  wendy
// Cleanup RSA warnings
//
// Revision 1.20  2007/12/05 17:39:13  bala
// fix checkDataAvailability
//
// Revision 1.19  2007/09/06 12:29:19  couto
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.18  2006/05/16 00:08:11  joan
// changes
//
// Revision 1.17  2006/05/15 16:10:38  joan
// changes
//
// Revision 1.16  2006/05/08 21:25:14  joan
// add print trace
//
// Revision 1.15  2005/02/17 21:14:01  joan
// fixes
//
// Revision 1.14  2005/02/17 16:58:05  joan
// work on CR
//
// Revision 1.13  2005/01/13 00:40:57  joan
// fixes for null billing template
//
// Revision 1.12  2005/01/10 18:55:29  joan
// fixes
//
// Revision 1.11  2005/01/10 18:32:20  joan
// fixes
//
// Revision 1.10  2004/11/16 21:44:16  joan
// edit message
//
// Revision 1.9  2004/11/12 20:44:59  joan
// adjust error messages
//
// Revision 1.8  2004/11/11 19:20:29  joan
// fixes
//
// Revision 1.7  2004/10/21 20:19:01  joan
// add catch MiddlewareRequest
//
// Revision 1.6  2004/10/05 16:05:02  joan
// fixes
//
// Revision 1.5  2004/09/03 20:20:13  joan
// fixes
//
// Revision 1.4  2004/09/03 18:14:06  joan
// fixes
//
// Revision 1.3  2004/08/27 21:41:32  joan
// fixes
//
// Revision 1.2  2004/08/25 22:38:20  joan
// fixes
//
// Revision 1.1  2004/08/25 19:44:51  joan
// add new pdgs
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

public class SWOFUPGRADE30APDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;

	private String m_strAfReqType = null;
	//private String m_strAfSupplyTemplate = null;
	private String m_strAfOptFeature = "f00";
	//private String m_strAfPreloadedSW = null;
	private Vector m_afUpgradeVec = new Vector();
	private String m_strAfBillingTemplate = null;
	private String m_strAfMachType = null;
	private String m_strAfModel = null;
	//private Vector m_afOsLevelVec = new Vector();
	//private String m_strAfCreateMES = null;
	private String m_strAnnCodeName = null;

	//private	EANList m_availList = new EANList();
	private Vector m_vctReturnEntityKeys = new Vector();
	//private EntityList m_elTest = null;
	private EANList m_opList = new EANList();

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: SWOFUPGRADE30APDG.java,v 1.21 2008/09/05 21:25:47 wendy Exp $");
  	}


	public SWOFUPGRADE30APDG(EANMetaFoundation  _mf, SWOFUPGRADE30APDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	public SWOFUPGRADE30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("SWOFUPGRADE30APDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "SWOFUPGRADE30APDG";
  	}

	/*
  	private EntityItem findUSUpgrade(String _strEntityType, String _strAttributes) {
		if (m_elTest != null) {
			EntityGroup eg = m_elTest.getEntityGroup(_strEntityType);
			if (eg != null) {
				for (int i=0; i < eg.getEntityItemCount(); i++) {
					EntityItem ei = eg.getEntityItem(i);
					boolean bMatch = true;
					StringTokenizer st = new StringTokenizer(_strAttributes,";");
					while (st.hasMoreTokens()) {
						String s = st.nextToken();
						if (s.substring(0,3).equals("map")) {

							s = s.substring(4);
							StringTokenizer st1 = new StringTokenizer(s,"=");
							String strAttrCode = st1.nextToken().trim();
							String strAttrValue = st1.nextToken().trim();
							EANAttribute att = ei.getAttribute(strAttrCode);
							if (att != null) {
								if (att instanceof EANTextAttribute) {
									String value = (String)att.get();
									if (!value.equals(strAttrValue)) {
										bMatch = false;
									}
								} else if (att instanceof EANFlagAttribute) {
									MetaFlag[] amf = (MetaFlag[])att.get();
									for (int f=0; f < amf.length; f++) {
										if (amf[f].isSelected()) {
											String flagCode = amf[f].getFlagCode();
											String flagDesc = amf[f].getLongDescription();
											if ((!strAttrValue.equals(flagCode)) && (!strAttrValue.equals(flagDesc))) {
												bMatch = false;
											}
										}
									}
								}
							} else {
								bMatch = false;
							}
						}
					}
					if (bMatch) {
						return ei;
					}
				}
			}
		}
		return null;
	}
	*/

	protected void generateDataII(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSearchKey) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = "SWOFUPGRADE30APDG generateDataII";
                D.ebug(D.EBUG_SPEW,strTraceBase+":SearchKey is:"+_strSearchKey);
		try {
			StringTokenizer st = new StringTokenizer(_sbMissing.toString(),"\n");
			Hashtable ht = new Hashtable();
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				StringTokenizer st1 = new StringTokenizer(s,"|");

				if (st1.hasMoreTokens()) {
					String strParentEntity = st1.nextToken().trim();
					int iLevel = Integer.parseInt(st1.nextToken());
					String strDirection = st1.nextToken().trim();
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

					//find the item if needed
					int iFind = strAction.indexOf("find");
					EntityItem currentEI = null;
					if (iFind > -1) {
                                            D.ebug(D.EBUG_SPEW,strTraceBase+":Find:Attributes:"+strAttributes);
						if (strAttributes.indexOf("map") >= 0) {
							int iEqual = strAttributes.indexOf("=");
							String strHead = strAttributes.substring(4, iEqual);
							currentEI = m_utility.findEntityItem(m_eiList, strEntityType, strAttributes);
							if (currentEI == null) {
								String strSai = (String)m_saiList.get(strEntityType);
								EntityItem[] aei = null;
								if (strHead.indexOf(":") >= 0) {
									aei = m_utility.dynaSearchII(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
								} else {
									aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
								}

								if (aei.length > 0) {
									currentEI = aei[0];
									// save for later search
									m_eiList.put(currentEI);
								}

								if (currentEI != null) {
									ht.put(iLevel + "", currentEI);
								}
							}
						}
					}

					// link them if there's command link
					int iLink = strAction.indexOf("linkParent");
					if (iLink > -1 && currentEI != null) {
						// use parent entity, relator,link

						if (eiParent != null) {
							OPICMList ol = null;
							if (strDirection.equals("U")) {
								EntityItem[] aei = {eiParent};
								ol = m_utility.linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
							} else {
								EntityItem[] aei = {currentEI};
								ol = m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
							}

							for (int i=0; i < ol.size(); i++) {
								Object obj = ol.getAt(i);
								//System.out.println(strTraceBase + " obj: " + obj.toString());
								if (obj instanceof ReturnRelatorKey) {
									ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
									String strRType = rrk.getEntityType();
									EntityGroup eg = m_utility.getEntityGroup(strRType);
									if (eg ==null) {
										eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
									}
									_prof = m_utility.setProfValOnEffOn(_db, _prof);
									EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
									//System.out.println(strTraceBase + " ei: " + ei.dump(false));
									m_eiList.put(ei);
								}
							}
							// update attributes for found ei
							//OPICMList attList = m_utility.getAttributeListForUpdate(currentEI.getEntityType(), strAttributes);
							//m_utility.updateAttribute(_db, _prof, currentEI, attList);
							//attList = null;
						}
						continue;
					}

					// create the item
					int iCreate = strAction.indexOf("create");
					if (iCreate > -1) {
                                            D.ebug(D.EBUG_SPEW,strTraceBase+":Creating:"+strAction);
						String strRelatorType = "";
						int iAttrO = strRelatorInfo.indexOf("[");
						if (iAttrO > -1) {
							strRelatorType = strRelatorInfo.substring(0,iAttrO);
						} else {
							strRelatorType = strRelatorInfo;
						}

						if (eiParent == null) {
							eiParent = m_eiRoot;
						}
						//prepare the list of attributes
						OPICMList attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);
						String strCai = (String)m_caiList.get(strRelatorType);
						if (strDirection.equals("U")) {
							// create stand alone entity
							attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, "");
							strCai = (String)m_caiList.get(strEntityType);
						}

						if (strAction.indexOf("USUPGRADE") > -1) {  	// need to check for ORDEROF for the same upgrade group
							currentEI = m_utility.findEntityItem(m_eiList, strEntityType, strAttributes);

							if (currentEI != null) {
								EntityItem[] aei = {currentEI};
								m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
							}
						}

						if (currentEI == null) {
							currentEI = m_utility.createEntityByRST(_db, _prof, eiParent, attList, strCai, strRelatorType, strEntityType);
						}

						_db.test(currentEI != null, " ei is null for: " + s);
						ht.put(iLevel + "", currentEI);
						m_eiList.put(currentEI);

						if (strDirection.equals("U")) {
							// link to 1 level up
							EntityItem[] aei = {eiParent};
							OPICMList ol = m_utility.linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
							//System.out.println(strTraceBase + " ol size: " + ol.size());
							for (int i=0; i < ol.size(); i++) {
								Object obj = ol.getAt(i);
								//System.out.println(strTraceBase + " obj: " + obj.toString());
								if (obj instanceof ReturnRelatorKey) {
									ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
									String strRType = rrk.getEntityType();
									EntityGroup eg = m_utility.getEntityGroup(strRType);
									if (eg ==null) {
										eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
									}
									_prof = m_utility.setProfValOnEffOn(_db, _prof);
									EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
									//System.out.println(strTraceBase + " ei: " + ei.dump(false));
									m_eiList.put(ei);
								}
							}
						}

						// save entities for later link to Offering Project
						int iLinkRoot = strAction.indexOf("linkRoot");
						if (iLinkRoot > -1) {
							String strLinkRoot = strAction.substring(iLinkRoot);
							int iEnd = strLinkRoot.indexOf(";");
							if (iEnd > -1) {
								strLinkRoot=strLinkRoot.substring(0,iEnd);
							}
							int iU = strLinkRoot.indexOf("_");
							if (iU > -1) {
								String strRelator = strLinkRoot.substring(iU+1);
								if (m_opList.get(currentEI) == null) {
									m_vctReturnEntityKeys.addElement(new ReturnRelatorKey(strRelator, -1, m_eiRoot.getEntityType(), m_eiRoot.getEntityID(), currentEI.getEntityType(), currentEI.getEntityID(), true));
									m_opList.put(currentEI);
								}
							}
						}
					}
				}
			}

			if (m_vctReturnEntityKeys.size() > 0) {
				m_utility.link(_db, _prof, m_vctReturnEntityKeys);
			}
		} catch (SBRException ex) {
			// link entities to Offering Project
			if (m_vctReturnEntityKeys.size() > 0) {
				m_utility.link(_db, _prof, m_vctReturnEntityKeys);
			}
			throw ex;
		}
	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SWOFUPGRADE30APDG checkMissingData method";
		StringBuffer sbReturn = new StringBuffer();
		_db.debug(D.EBUG_DETAIL, strTraceBase);

		EntityItem eiBaseCOF = (EntityItem)m_eiList.get("APPBASE");
		_db.test(eiBaseCOF != null, " Base Commercial Offering is null");
		String strFileName = "PDGtemplates/OptFeatureUpgrade_30a.txt";
		if (m_strAfBillingTemplate != null) {
			if (m_strAfBillingTemplate.equals("IPLA06")) {
				strFileName = "PDGtemplates/OptFeatureUpgradeBTIPLA06_30a.txt";
			} else if (m_strAfBillingTemplate.equals("IPLA22")) {
				strFileName = "PDGtemplates/OptFeatureUpgradeBTIPLA22_30a.txt";
			} else if (m_strAfBillingTemplate.equals("IPLA25")) {
				strFileName = "PDGtemplates/OptFeatureUpgradeBTIPLA25_30a.txt";
			}
		}

		m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");
                _db.debug(D.EBUG_DETAIL, strTraceBase+"Using Template File"+strFileName);

		String ofid = m_utility.getOptFeatIDAbr(m_strAfOptFeature);

		for(int i=0; i < m_afUpgradeVec.size(); i++) {
			String strUpgrade = (String)m_afUpgradeVec.elementAt(i);

			StringTokenizer st = new StringTokenizer(strUpgrade, "-");

			String strUS = st.nextToken();
			String strMachType = st.nextToken();
			String strModel = st.nextToken();
			String strFeatureCode = "";
			if (st.hasMoreTokens()) {
				strFeatureCode = st.nextToken();
			}

			OPICMList infoList = new OPICMList();
			infoList.put("PDG", m_eiPDG);
			infoList.put("WG", m_eiRoot);
			EntityItem eiUP = (EntityItem)m_eiList.get(strUpgrade);
			if (eiUP != null) {
				infoList.put("UPSW", eiUP);
			}

			infoList.put("STRUPGRADE", strMachType + "-" + strModel + (strFeatureCode.length() > 0 ? "-" + strFeatureCode : ""));
			infoList.put("US", strUS);
			infoList.put("EMEA", (i+1)+"");
			infoList.put("OFID", ofid);
			infoList.put("GEOIND", "GENAREASELECTION");
			infoList.put("UPMACHTYPE", strMachType);
			infoList.put("UPMODEL", strModel);

			_prof = m_utility.setProfValOnEffOn(_db, _prof);

			TestPDGII pdgObject = new TestPDGII(_db, _prof, eiBaseCOF, infoList, m_PDGxai, strFileName);
			StringBuffer sbMissing = pdgObject.getMissingEntities();
			//m_elTest = pdgObject.getEntityList();
			pdgObject = null;
			infoList = null;

			if (_bGenData) {
				generateDataII(_db, _prof, sbMissing, strUpgrade);
			}
			sbReturn.append(sbMissing.toString());

		}

		return sbReturn;
	}

	private Vector sepUpgradeString(String _s) throws MiddlewareRequestException {
		Vector vecReturn = new Vector();
		StringTokenizer st = new StringTokenizer(_s, "\n");
		while (st.hasMoreElements()) {
			boolean bAdd = true;
			String str = st.nextToken().trim();

			StringTokenizer st1 = new StringTokenizer(str, "-");
			int i = st1.countTokens();

			if (i != 3) {
				m_SBREx.add(str + " is not in format x-pppp-ppp");
				bAdd = false;
			}

			char c = str.charAt(0);
			if (!Character.isDigit(c)) {
				bAdd = false;
				m_SBREx.add(str + " first character is not a digit.");
			}
			if (bAdd) {
				vecReturn.addElement(str);
			}
		}
		return vecReturn;
	}

	/*private Vector checkOSLEVELUnique(Vector _v) {
		Hashtable ht = new Hashtable();
		for (int i=0; i < _v.size(); i++) {
			String s = (String)_v.elementAt(i);
			String s1 = "@"; // allow for '--' to be selected, avoid StringIndexOutOfBoundsException
			if (s.length()>3){
				s1 = s.substring(3, 4);
			}
			if (ht.get(s1) == null) {
				ht.put(s1, s);
			} else {
				m_SBREx.add(" The 4th character of all selected OSLEVELs must be unique.");
			}
		}
		return _v;
	}*/

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
		String strTraceBase = " SWOFUPGRADE30APDG checkPDGAttribute method";
                _db.debug(D.EBUG_SPEW, strTraceBase);

		for (int i =0; i < _afirmEI.getAttributeCount(); i++) {
			EANAttribute att = _afirmEI.getAttribute(i);
			String textAtt = "";
			String sFlagAtt = "";
			//String sFlagClass = "";
			Vector mFlagAtt = new Vector();
                        _db.debug(D.EBUG_SPEW, strTraceBase+":Checking Attribute:"+att.getKey());

		//	int index = -1;
			if (att instanceof EANTextAttribute) {
				textAtt = ((String)att.get()).trim();
			} else if (att instanceof EANFlagAttribute) {
				if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
							sFlagAtt = amf[f].getLongDescription().trim();
		//					sFlagClass = amf[f].getFlagCode().trim();
		//					index = f;
							break;
						}
					}
				} else if (att instanceof MultiFlagAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
							mFlagAtt.addElement(amf[f].getLongDescription().trim());
						}
					}
				}
			}

			if (att.getKey().equals("AFSWREQTYPE")) {
				m_strAfReqType = sFlagAtt;
			} else if (att.getKey().equals("AFSUPPLYTEMPLATE")) {
				//m_strAfSupplyTemplate = sFlagAtt;
			} else if (att.getKey().equals("OPTFEATUREID")) {
				m_SBREx = m_utility.checkOptFeatureIDFormat(textAtt, PDGUtility.OF_PRODUCT, false, m_SBREx);
				m_strAfOptFeature = textAtt;
			} else if (att.getKey().equals("PRELOADEDSW")) {
				//m_strAfPreloadedSW = sFlagAtt;
			} else if (att.getKey().equals("AFSWUPGRADEFROM")) {
				m_afUpgradeVec = sepUpgradeString(textAtt);
			} else if (att.getKey().equals("AFBILLINGTEMPLATE")) {
				m_strAfBillingTemplate = sFlagAtt;
			} else if (att.getKey().equals("MACHTYPEATR")) {
				m_strAfMachType = sFlagAtt;
			} else if (att.getKey().equals("MODELATR")) {
				m_strAfModel = textAtt;
			} else if (att.getKey().equals("OSLEVEL")) {
				//m_afOsLevelVec = checkOSLEVELUnique(mFlagAtt);
			} else if (att.getKey().equals("AFSWCREATEMES")) {
				//m_strAfCreateMES = sFlagAtt;
			} else if (att.getKey().equals("GENAREASELECTION")) {
				m_SBREx = m_utility.checkGenAreaOverlap(mFlagAtt, m_SBREx);
			} else if (att.getKey().equals("ANNCODENAME")) {
				m_strAnnCodeName = sFlagAtt;
			}
		}
		if ( ! m_strAfReqType.equals("SWOptFeature")) {
			SBRException ex = new SBRException();
			ex.add(" Request Type:" + m_strAfReqType + ". This action item is for SW Optional Feature Request.");
			throw ex;
		}

		if (m_afUpgradeVec.size() <= 0) {
			m_SBREx.add("No upgrade paths.");
		}

		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}
		if (m_strAfBillingTemplate == null || m_strAfBillingTemplate.length() <=0) {
			m_SBREx.add("BILLINGTEMPLATE is required.");
		}

	}

	protected void resetVariables() {
	 	m_strAfReqType = null;
	 	//m_strAfSupplyTemplate = null;
	 	m_strAfOptFeature = "f00";
	 	//m_strAfPreloadedSW = null;
	 	m_afUpgradeVec = new Vector();
	 	m_strAfBillingTemplate = null;
	 	m_strAfMachType = null;
	 	m_strAfModel = null;
	 	//m_afOsLevelVec = new Vector();
	 	//m_strAfCreateMES = null;
		m_vctReturnEntityKeys = new Vector();
		//m_availList = new EANList();
		m_eiList = new EANList();
		m_opList = new EANList();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SWOFUPGRADE30APDG viewMissing method";

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		m_SBREx = new SBRException();
		resetVariables();
		if (m_eiPDG == null) {
			String s="PDG entity is null";
			return s.getBytes();
		}
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT1");
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

		checkPDGAttribute(_db, _prof, m_eiPDG);

		// validate data
		checkDataAvailability(_db, _prof, m_eiPDG);
		if (m_SBREx.getErrorCount() > 0) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
			throw m_SBREx;
		}

		m_sbActivities = new StringBuffer();
		m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
		String s = checkMissingData(_db, _prof,false).toString();
		if (s.length() <= 0) {
			s = "Generating data is complete";
		}
		m_sbActivities.append(m_utility.getViewXMLString(s));
		m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
		m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());

		return s.getBytes();
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SWOFUPGRADE30APDG executeAction method";
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

			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT1");
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

			checkPDGAttribute(_db, _prof, m_eiPDG);

			// validate data
			checkDataAvailability(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
				throw m_SBREx;
			}
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
			m_utility.resetActivities();
			m_sbActivities = new StringBuffer();
			m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
			strData = checkMissingData(_db, _prof, true).toString();
			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
		} catch (SBRException ex) {
			ex.printStackTrace();
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		} catch (MiddlewareException mex) {
			mex.printStackTrace();
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, mex.toString(), getLongDescription());
			throw mex;
		}

		if (strData.length() <= 0) {
			m_SBREx.add("Generating data is complete.  No data created during this run. (ok)");
			throw m_SBREx;
		}

		if ( !(m_strAfBillingTemplate.equals("IPLA06")
				|| m_strAfBillingTemplate.equals("IPLA22")
				|| m_strAfBillingTemplate.equals("IPLA25")
				|| m_strAfBillingTemplate.equals("NOBILLING"))) {
			m_SBREx.add("Billing codes must be added manually. (ok)");
			throw m_SBREx;
		}
	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		StringBuffer sb = new StringBuffer();
		// look for SW-Application-Base MODEL
		// make sure the Base COMMERCIALOF for the MTM already exists
		sb.append("map_COFCAT=101;");
		sb.append("map_COFSUBCAT=127;");
		sb.append("map_COFGRP=150;");
		sb.append("map_COFSUBGRP=010;");
		sb.append("map_MACHTYPEATR=" + m_strAfMachType + ";");
		sb.append("map_MODELATR=" + m_strAfModel+ ";");

		String strSai = (String)m_saiList.get("MODEL");
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		EntityItem[] aeiAAPCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());

		if (aeiAAPCOM.length <= 0) {
			if (!runBySPDG()) {
				m_SBREx.add("The MODEL with clasifications SW-Application-Base-N/A, MACHTYPE=" + m_strAfMachType + ", MODELATR=" + m_strAfModel+ " must be created before Initial MODEL.");
			}
		} else if (aeiAAPCOM.length > 1) {
			m_SBREx.add("There are " + aeiAAPCOM.length + " existing MODELs with clasifications SW-Application-Base-N/A, MACHTYPE=" + m_strAfMachType + ", MODELATR=" + m_strAfModel);
		} else {
			m_eiList.put("APPBASE", aeiAAPCOM[0]);
		}

                sb = new StringBuffer();  //Bala 11/9/07 as Search is always returning negative
		// make sure the Base COMMERCIALOF for the MTM already exists
                _db.debug(D.EBUG_SPEW,"checkDataAvailability: Checking for MODEL");
		sb.append("map_COFCAT=101;");
		sb.append("map_COFSUBCAT=130;");
		sb.append("map_COFGRP=150;");
		sb.append("map_MACHTYPEATR=" + m_strAfMachType + ";");
		sb.append("map_MODELATR=" + m_strAfModel+ ";");
		sb.append("map_OPTFEATUREID=" + m_strAfOptFeature);

		_prof = m_utility.setProfValOnEffOn(_db, _prof);

		EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());

		if (aeiCOM.length <= 0 ) {
			m_SBREx.add("The MODEL with clasifications SW-OptionalFeature-Base, MACHTYPE=" + m_strAfMachType + ", MODEL=" + m_strAfModel+ ", OPTFEATUREID=" + m_strAfOptFeature + " must be created before Upgrade MODEL.");
		} else if (aeiCOM.length > 1) {
			m_SBREx.add("There are " + aeiCOM.length + " existing MODELs with clasifications SW-OptionalFeature-Base, MACHTYPE=" + m_strAfMachType + ", MODEL=" + m_strAfModel+ ", OPTFEATUREID=" + m_strAfOptFeature);
		} else {
			m_eiList.put("BASE", aeiCOM[0]);
			EANAttribute att = (EANAttribute)aeiCOM[0].getAttribute("BILLINGTEMPLATE");
            if (att != null && att instanceof EANFlagAttribute) {
				MetaFlag[] amf = (MetaFlag[])att.get();
				for (int f=0; f < amf.length; f++) {
				   	if (amf[f].isSelected()) {
				    	String sFlagAtt = amf[f].getLongDescription().trim();
				       	if (!sFlagAtt.equals(m_strAfBillingTemplate)) {
							m_SBREx.add(" Billing Template on the request must match Billing Template on the Base MODEL " + sFlagAtt);
						}
				       	break;
				   	}
                }
			}
		}
	}
}
