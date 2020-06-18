//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SWSubsFeatSuppPDG.java,v $
// Revision 1.28  2008/11/13 18:28:41  wendy
// removed unused getGeoVec()
//
// Revision 1.27  2008/09/05 21:25:46  wendy
// Cleanup RSA warnings
//
// Revision 1.26  2004/03/12 23:19:12  joan
// changes from 1.2
//
// Revision 1.25  2003/07/22 17:03:40  joan
// move changes from v111
//
// Revision 1.24  2003/07/15 19:36:54  joan
// move changes from v111
//
// Revision 1.23  2003/07/02 22:54:59  joan
// fix classification
//
// Revision 1.22  2003/07/02 16:10:48  joan
// fix geo
//
// Revision 1.21  2003/06/12 23:08:31  joan
// move changes from v111
//
// Revision 1.20  2003/06/11 23:26:23  joan
// move changes
//
// Revision 1.19  2003/06/06 20:20:32  joan
// move changes from v111
//
// Revision 1.18  2003/05/29 00:03:40  joan
// fix feedback
//
// Revision 1.17  2003/05/13 17:41:52  joan
// fixes for report
//
// Revision 1.16  2003/05/08 17:38:27  joan
// fix fb
//
// Revision 1.15  2003/05/05 21:39:17  joan
// work on xml report
//
// Revision 1.14  2003/04/23 22:32:43  joan
// set profile valon effon
//
// Revision 1.13  2003/04/21 20:43:17  joan
// fb fix
//
// Revision 1.12  2003/04/16 19:52:45  joan
// fix PDG creating entity
//
// Revision 1.11  2003/04/14 16:58:13  joan
// fix fb
//
// Revision 1.10  2003/04/10 17:20:01  joan
// fix compile
//
// Revision 1.9  2003/04/10 17:00:43  joan
// fix checking MTM
//
// Revision 1.8  2003/04/03 22:39:02  joan
// adjust code
//
// Revision 1.7  2003/04/02 20:57:03  joan
// adjust resetVariable
//
// Revision 1.6  2003/04/01 18:43:32  joan
// add HWFeaturePDG
//
// Revision 1.5  2003/03/28 00:48:09  joan
// adjust code
//
// Revision 1.4  2003/03/27 23:19:51  joan
// fix bugs
//
// Revision 1.3  2003/03/26 22:54:03  joan
// adjust code for optfeature id
//
// Revision 1.2  2003/03/26 20:37:32  joan
// add SW support and maintenance PDGs
//
// Revision 1.1  2003/03/20 21:36:18  joan
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

public class SWSubsFeatSuppPDG extends PDGActionItem {

  static final long serialVersionUID = 20011106L;

	private String m_strAfOptFeature = "s00";
	private Vector m_afSuppProdVec = new Vector();
	//private String m_strAfBillingTemplate = null;
	private String m_strAfMachType = null;
	private String m_strAfModel = null;
	//private Vector m_afOsLevelVec = new Vector();
	private String m_strAfReqType = null;
	private String m_strAnnCodeName = null;
	private	EANList m_availList = new EANList();
	private Vector m_vctReturnEntityKeys = new Vector();
	private EANList m_opList = new EANList();

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: SWSubsFeatSuppPDG.java,v 1.28 2008/11/13 18:28:41 wendy Exp $");
  	}


	public SWSubsFeatSuppPDG(EANMetaFoundation  _mf, SWSubsFeatSuppPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	public SWSubsFeatSuppPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("SWSubsFeatSuppPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "SWSubsFeatSuppPDG";
  	}

	protected void generateData(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSupportProd) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
	//	String strTraceBase = "SWSubsFeatSuppPDG generateData";
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
						} else if (strEntityType.equals("COMMERCIALOF")) {
							foundEI = (EntityItem)m_eiList.get(_strSupportProd);
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
			if (m_vctReturnEntityKeys.size() > 0) {
				m_utility.link(_db, _prof, m_vctReturnEntityKeys);
			}
			throw ex;
		}
	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " SWSubsFeatSuppPDG executeProduct method";
		StringBuffer sbReturn = new StringBuffer();
		EntityItem eiBaseCOF =(EntityItem)m_eiList.get("BASE");
		_db.test(eiBaseCOF != null, " Base Commercial Offering is null");

		String strFileName = "PDGtemplates/SubsFeatSupp.txt";
		m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

		ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "EXTCOMMERCIALOF2");
		for(int i=0; i < m_afSuppProdVec.size(); i++) {
			String strSuppProd = (String)m_afSuppProdVec.elementAt(i);

			OPICMList infoList = new OPICMList();
			infoList.put("PDG", m_eiPDG);
			infoList.put("SUPPMACHTYPE", strSuppProd.substring(0,4));
			infoList.put("SUPPMODEL", strSuppProd.substring(5));
			infoList.put("GEOIND", "GENAREASELECTION");
			_prof = m_utility.setProfValOnEffOn(_db, _prof);

			TestPDG pdgObject = new TestPDG(_db, _prof, eiBaseCOF, infoList, xai, strFileName);
			StringBuffer sbMissing = pdgObject.getMissingEntities();
		//	EntityList el = pdgObject.getEntityList();
			pdgObject = null;
			infoList = null;

			if (_bGenData) {
				generateData(_db, _prof, sbMissing, strSuppProd);
			}
			sbReturn.append(sbMissing.toString());
		}
		return sbReturn;
	}

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
	//	String strTraceBase = " SWSubsFeatSuppPDG checkPDGAttribute method";
		for (int i =0; i < _afirmEI.getAttributeCount(); i++) {
			EANAttribute att = _afirmEI.getAttribute(i);
			String textAtt = "";
			String sFlagAtt = "";
	//		String sFlagClass = "";
			Vector mFlagAtt = new Vector();

	//		int index = -1;
			if (att instanceof EANTextAttribute) {
				textAtt = ((String)att.get()).trim();
			} else if (att instanceof EANFlagAttribute) {
				if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
							sFlagAtt = amf[f].getLongDescription().trim();
	//						sFlagClass = amf[f].getFlagCode().trim();
	//						index = f;
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

			if (att.getKey().equals("AFSWSUBSREQUESTTYPE")) {
				m_strAfReqType = sFlagAtt;
			} else if (att.getKey().equals("OPTFEATUREID")) {
				m_SBREx = m_utility.checkOptFeatureIDFormat(textAtt, PDGUtility.OF_SUBSCRIPTION, false, m_SBREx);
				m_strAfOptFeature = textAtt;
			} else if (att.getKey().equals("MACHTYPE")) {
				m_strAfMachType = textAtt;
			} else if (att.getKey().equals("MODEL")) {
				m_strAfModel = textAtt;
			} else if (att.getKey().equals("OSLEVEL")) {
				//m_afOsLevelVec = checkOSLEVELUnique(mFlagAtt);
			} else if (att.getKey().equals("AFBILLINGTEMPLATE")) {
				//m_strAfBillingTemplate = sFlagAtt;
			} else if (att.getKey().equals("AFMTM")) {
				m_SBREx = m_utility.checkSuppProdString(textAtt, m_SBREx);
				m_afSuppProdVec = m_utility.sepLongText(textAtt);
			} else if (att.getKey().equals("GENAREASELECTION")) {
				m_SBREx = m_utility.checkGenAreaOverlap(mFlagAtt, m_SBREx);
			} else if (att.getKey().equals("ANNCODENAME")) {
				m_strAnnCodeName = sFlagAtt;
			}
		}

		if ( ! m_strAfReqType.equals("Subscription Feature")) {
			SBRException ex = new SBRException();
			ex.add(" Request Type:" + m_strAfReqType + ". This action item is for SW Subscription Feature Request.");
			throw ex;
		}
		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}

	}

	protected void resetVariables() {
	 	m_strAfOptFeature = "s00";
	 	m_afSuppProdVec = new Vector();
	 	//m_strAfBillingTemplate = null;
	 	m_strAfMachType = null;
	 	m_strAfModel = null;
	 	//m_afOsLevelVec = new Vector();
	 	m_availList = new EANList();
		m_eiList = new EANList();
		m_opList = new EANList();
		m_vctReturnEntityKeys = new Vector();
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SWSubsFeatSuppPDG executeAction method";

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		m_SBREx = new SBRException();
		resetVariables();
		if (m_eiPDG == null) {
			String s="PDG entity is null";
			return s.getBytes();
		}
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT4");
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
		String strTraceBase = " SWSubsFeatSuppPDG executeAction method";
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

			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT4");
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
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
			m_utility.resetActivities();
			m_sbActivities = new StringBuffer();
			m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
			strData = checkMissingData(_db, _prof,true).toString();
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
		StringBuffer sb = new StringBuffer();
		// make sure the Base COMMERCIALOF for the MTM already exists
		sb.append("map_COFCAT=101;");
		sb.append("map_COFSUBCAT=217;");
		sb.append("map_COFGRP=300;");
		sb.append("map_MACHTYPE=" + m_strAfMachType + ";");
		sb.append("map_MODEL=" + m_strAfModel+ ";");
		sb.append("map_OPTFEATUREID=" + m_strAfOptFeature);

		String strSai = (String)m_saiList.get("COMMERCIALOF");
		EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "COMMERCIALOF", sb.toString());

		if (aeiCOM.length <= 0 ) {
			m_SBREx.add("The Base Commercial Offering must be created before connecting to supported Commercial Offering.");
		} else if (aeiCOM.length > 1) {
			m_SBREx.add("There are " + aeiCOM.length + " existing Base COMMERCIALOF for the same machinetype and model.");
		} else {
			m_eiList.put("BASE", aeiCOM[0]);
		}

		//verify supported products
		for(int i=0; i < m_afSuppProdVec.size(); i++) {
			String strSuppProd = (String)m_afSuppProdVec.elementAt(i);
			String strMachType = strSuppProd.substring(0,4);
			String strModel = strSuppProd.substring(5);

			// check if the supported products COMMERCIALOF exists

			StringBuffer sb1 = new StringBuffer();
			sb1.append("map_COFCAT=101;");
			sb1.append("map_COFSUBCAT=OptionalFeature;");
			sb1.append("map_COFGRP=300;");
			sb1.append("map_MACHTYPE=" + strMachType + ";");
			sb1.append("map_MODEL=" + strModel + ";");
			sb1.append("map_OPTFEATUREID=f" + m_strAfOptFeature.substring(1));

			String strSai1 = (String)m_saiList.get("COMMERCIALOF");
			EntityItem[] aeiCOF = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai1, "COMMERCIALOF", sb1.toString());
			if (aeiCOF == null || aeiCOF.length <= 0) {
				m_SBREx.add(" Supported COMMERCIALOFs for " + strSuppProd + " not found.");
			} else {
				m_eiList.put(strSuppProd, aeiCOF[0]);
			}
		}
	}

}
