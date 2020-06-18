//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SWOFINITIALPDG.java,v $
// Revision 1.9  2008/11/13 18:28:40  wendy
// removed unused getGeoVec()
//
// Revision 1.8  2008/09/05 21:25:46  wendy
// Cleanup RSA warnings
//
// Revision 1.7  2004/03/12 23:19:11  joan
// changes from 1.2
//
// Revision 1.6  2004/02/19 18:03:45  joan
// fix bug
//
// Revision 1.5  2004/02/17 16:36:48  joan
// fix fb
//
// Revision 1.4  2003/12/23 20:43:33  joan
// fix error
//
// Revision 1.3  2003/12/23 18:44:05  joan
// fix extract
//
// Revision 1.2  2003/12/18 22:10:02  joan
// work on CR
//
// Revision 1.1  2003/12/17 17:54:17  joan
// change name
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

public class SWOFINITIALPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;

	private String m_strAfReqType = null;
	//private String m_strAfSupplyTemplate = null;
	private String m_strAfOptFeature = "f00";
	private String m_strAfBillingTemplate = null;
	private String m_strAfMachType = null;
	private String m_strAfModel = null;
	//private Vector m_afOsLevelVec = new Vector();
	private String m_strAfCreateMES = null;
	private String m_strAnnCodeName = null;

	private	EANList m_availList = new EANList();
	private Vector m_vctReturnEntityKeys = new Vector();
	private EANList m_opList = new EANList();
  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: SWOFINITIALPDG.java,v 1.9 2008/11/13 18:28:40 wendy Exp $");
  	}


	public SWOFINITIALPDG(EANMetaFoundation  _mf, SWOFINITIALPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	public SWOFINITIALPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("SWOFINITIALPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "SWOFINITIALPDG";
  	}

	protected void generateData(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSearchKey) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
	//	String strTraceBase = "SWOFINITIALPDG generateData";
		try {
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
						StringTokenizer stTemp = new StringTokenizer(sGeo,";");
						if (stTemp.hasMoreTokens()) {
							sGeo = stTemp.nextToken();
						}
						stTemp = new StringTokenizer(sGeo,"=");
						if (stTemp.hasMoreTokens()) {
							sGeo = stTemp.nextToken();
							sGeo = stTemp.nextToken();
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
							// update attributes for found ei
							OPICMList attList = m_utility.getAttributeListForUpdate(foundEI.getEntityType(), strAttributes);
							m_utility.updateAttribute(_db, _prof, foundEI, attList);
							attList = null;
						}
						continue;
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

						if (strEntityType.equals("AVAIL")) {
							// save AVAIL for later link to ORDEROF
							if (sGeo.length() > 0) {
								m_availList.put(sGeo, ei);
							}
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

			// link entities to Offering Project
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
		String strTraceBase = " SWOFINITIALPDG executeOptFeature method";
		StringBuffer sbReturn = new StringBuffer();
		_db.debug(D.EBUG_DETAIL, strTraceBase);

		EntityItem eiBaseCOF = (EntityItem)m_eiList.get("BASE");
//		_db.test(eiBaseCOF != null, " Base Commercial Offering is null");
		String strFileName = "";
		if (m_strAfBillingTemplate.equals("IPLA06")) {
			strFileName = "PDGtemplates/OptFeatureBTIPLA06_2.txt";
			if (m_strAfCreateMES != null && m_strAfCreateMES.equals("Yes")) {
				strFileName = "PDGtemplates/OptFeatureBTIPLA06_1.txt";
			}
		} else if (m_strAfBillingTemplate.equals("IPLA22")) {
			strFileName = "PDGtemplates/OptFeatureBTIPLA22.txt";
		} else if (m_strAfBillingTemplate.equals("IPLA25")) {
			strFileName = "PDGtemplates/OptFeatureBTIPLA25.txt";
		} else if (m_strAfBillingTemplate.equals("NOBILLING")) {
			return sbReturn;
		} else {
			strFileName = "PDGtemplates/OptFeatureBT.txt";
		}

		m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

		OPICMList infoList = new OPICMList();
		infoList.put("PDG", m_eiPDG);
		infoList.put("OFPROJ", m_eiOFPROJ);

		infoList.put("OFID", m_utility.getOptFeatIDAbr(m_strAfOptFeature));
		infoList.put("GEOIND", "GENAREASELECTION");

		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		TestPDG pdgObject = new TestPDG(_db,_prof,eiBaseCOF, infoList, m_PDGxai, strFileName);
		StringBuffer sbMissing = pdgObject.getMissingEntities();
		if (_bGenData) {
			generateData(_db, _prof, sbMissing,"");
		}
		sbReturn.append(sbMissing.toString());

		return sbReturn;
	}


	/*private Vector sepUpgradeString(String _s) throws MiddlewareRequestException {
		Vector vecReturn = new Vector();
		StringTokenizer st = new StringTokenizer(_s, "\n");
		while (st.hasMoreElements()) {
			String str = st.nextToken().trim();
			if (str.length() < 10) {
				m_SBREx.add(str + " is not in format x-pppp-ppp(-ffff)");
			} else {
				if (str.charAt(1) != '-' || str.charAt(6) != '-') {
					m_SBREx.add(str + " is not in format x-pppp-ppp(-ffff)");
				}
			}

			if (str.length() > 10 && str.charAt(10) != '-') {
				m_SBREx.add(str + " is not in format x-pppp-ppp(-ffff)");
			}

			char c = str.charAt(0);
			if (!Character.isDigit(c)) {
				m_SBREx.add(str + " first character is not a digit.");
			}
			vecReturn.addElement(str);
		}
		return vecReturn;
	}*/

	/*

	private Vector checkOSLEVELUnique(Vector _v) {
		Hashtable ht = new Hashtable();
		for (int i=0; i < _v.size(); i++) {
			String s = (String)_v.elementAt(i);
			String s1 = s.substring(3,4);
			if (ht.get(s1) == null) {
				ht.put(s1, s);
			} else {
				m_SBREx.add(" The 4th charater of all selected OSLEVELs must be unique.");
			}
		}
		return _v;
	}*/

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
		//String strTraceBase = " SWOFINITIALPDG checkPDGAttribute method";
		for (int i =0; i < _afirmEI.getAttributeCount(); i++) {
			EANAttribute att = _afirmEI.getAttribute(i);
			String textAtt = "";
			String sFlagAtt = "";
		//	String sFlagClass = "";
			Vector mFlagAtt = new Vector();

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
			} else if (att.getKey().equals("AFBILLINGTEMPLATE")) {
				m_strAfBillingTemplate = sFlagAtt;
			} else if (att.getKey().equals("MACHTYPE")) {
				m_strAfMachType = textAtt;
			} else if (att.getKey().equals("MODEL")) {
				m_strAfModel = textAtt;
			} else if (att.getKey().equals("OSLEVEL")) {
				//m_afOsLevelVec = checkOSLEVELUnique(mFlagAtt);
			} else if (att.getKey().equals("AFSWCREATEMES")) {
				m_strAfCreateMES = sFlagAtt;
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

//		if (m_strAfBillingTemplate.equals("NOBILLING")) {
//			m_SBREx.add("No billing template selected. ");
//		}
		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}

	}

	protected void resetVariables() {
	 	m_strAfReqType = null;
	 	//m_strAfSupplyTemplate = null;
	 	m_strAfOptFeature = "f00";
	 	m_strAfBillingTemplate = null;
	 	m_strAfMachType = null;
	 	m_strAfModel = null;
	 	//m_afOsLevelVec = new Vector();
	 	m_strAfCreateMES = null;
		m_vctReturnEntityKeys = new Vector();
		m_availList = new EANList();
		m_vctReturnEntityKeys = new Vector();
		m_eiList = new EANList();
		m_opList = new EANList();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SWOFINITIALPDG viewMissingData method";

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
		eg = el.getEntityGroup("OFDEVLPROJ");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiOFPROJ = eg.getEntityItem(0);
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
		if (!runBySPDG() && s.length() <= 0) {
			s = "Generating data is complete";
		}
		m_sbActivities.append(m_utility.getViewXMLString(s));
		m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
		m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());
		return s.getBytes();
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SWOFINITIALPDG executeAction method";
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
			eg = el.getEntityGroup("OFDEVLPROJ");
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiOFPROJ = eg.getEntityItem(0);
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
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		}

		if (strData.length() <= 0) {
			if (!runBySPDG()) {
				m_SBREx.add("Generating data is complete.  No data created during this run.");
				throw m_SBREx;
			}
		} else {
			setDataCreated(true);
			if ( !(m_strAfBillingTemplate.equals("IPLA06")
					|| m_strAfBillingTemplate.equals("IPLA22")
					|| m_strAfBillingTemplate.equals("IPLA25")
					|| m_strAfBillingTemplate.equals("NOBILLING"))) {
				m_SBREx.add("Billing codes must be added manually. (ok)");
				throw m_SBREx;
			}
		}


	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		StringBuffer sb = new StringBuffer();
		// make sure the Base COMMERCIALOF for the MTM already exists
		sb.append("map_COFCAT=101;");
		sb.append("map_COFSUBCAT=215;");
		sb.append("map_COFGRP=300;");
		sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
		sb.append("map_MODEL=" + m_strAfModel+ ";");
		sb.append("map_OPTFEATUREID=" + m_strAfOptFeature);

		String strSai = (String)m_saiList.get("COMMERCIALOF");
		EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "COMMERCIALOF", sb.toString());
		if (aeiCOM.length <= 0 ) {
			if (!runBySPDG()) {
				m_SBREx.add("The Base Commercial Offering must be created before Initial Commercial Offering.");
			}
		} else if (aeiCOM.length > 1) {
			m_SBREx.add("There are " + aeiCOM.length + " existing Base COMMERCIALOF for the same machinetype and model.");
		} else {
			m_eiList.put("BASE", aeiCOM[0]);
			EANAttribute att = (EANAttribute)aeiCOM[0].getAttribute("BILLINGTEMPLATE");
            if (att != null && att instanceof EANFlagAttribute) {
				MetaFlag[] amf = (MetaFlag[])att.get();
				for (int f=0; f < amf.length; f++) {
				   	if (amf[f].isSelected()) {
				    	String sFlagAtt = amf[f].getLongDescription().trim();
				       	if (!sFlagAtt.equals(m_strAfBillingTemplate)) {
							m_SBREx.add(" Billing Template on the request must match Billing Template on the Base COMMERCIALOF " + sFlagAtt);
						}
				       	break;
				   	}
                }
			}
		}
	}
}
