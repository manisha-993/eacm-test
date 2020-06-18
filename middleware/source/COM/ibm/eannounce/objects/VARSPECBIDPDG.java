//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: VARSPECBIDPDG.java,v $
// Revision 1.20  2008/09/08 17:31:42  wendy
// Cleanup some RSA warnings
//
// Revision 1.19  2005/03/09 00:27:20  joan
// work on display nav attributes
//
// Revision 1.18  2005/03/08 21:53:00  joan
// fixes
//
// Revision 1.17  2005/03/08 19:40:33  joan
// fixes
//
// Revision 1.16  2005/03/01 18:46:55  joan
// fixes
//
// Revision 1.15  2005/02/28 20:29:51  joan
// add throw exception
//
// Revision 1.14  2005/02/24 00:04:40  joan
// fixes
//
// Revision 1.13  2005/02/23 23:03:59  joan
// fixes
//
// Revision 1.12  2005/01/26 19:18:16  joan
// fixes
//
// Revision 1.11  2005/01/19 23:26:38  joan
// fixes
//
// Revision 1.10  2005/01/18 23:36:15  joan
// fixes
//
// Revision 1.9  2005/01/12 22:21:26  joan
// fixes
//
// Revision 1.8  2005/01/12 21:16:54  joan
// fixes
//
// Revision 1.7  2005/01/10 16:59:51  joan
// fixes
//
// Revision 1.6  2004/11/17 00:06:11  joan
// changes to getRowIndex key in rowselectable table
//
// Revision 1.5  2004/11/15 16:51:46  joan
// fix compile
//
// Revision 1.4  2004/11/15 16:36:40  joan
// work on CR
//
// Revision 1.3  2004/08/09 17:00:09  joan
// fixes
//
// Revision 1.2  2004/08/06 18:01:25  joan
// fixes
//
// Revision 1.1  2004/08/05 22:46:56  joan
// add new PDG
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

public class VARSPECBIDPDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList();
	private Vector m_vctSelectedEI = new Vector();
	private EntityItem m_eiVAR = null;
	private String m_strPnumb = null;
	public static final Hashtable c_hshRelatorType = new Hashtable();
	static {
		c_hshRelatorType.put ("VARDD","VARDD");
		c_hshRelatorType.put ("VARFB", "VARFB");
		c_hshRelatorType.put ("VARIMG", "VARIMG");
		c_hshRelatorType.put ("VARMM", "VARMM");
		c_hshRelatorType.put ("VARPBYAVAIL", "VARPBYAVAIL");
		c_hshRelatorType.put ("VARPSLAVAIL", "VARPSLAVAIL");
		c_hshRelatorType.put ("VARPK", "VARPK");
		c_hshRelatorType.put ("VARWAR", "VARWAR");
		c_hshRelatorType.put ("VARSBB", "VARSBB");
		c_hshRelatorType.put ("CPGVAR", "CPGVAR");
		c_hshRelatorType.put ("CTOVAR", "CTOVAR");
		c_hshRelatorType.put ("PRVAR", "PRVAR");
	}

	public static final Hashtable c_hshLinkCopyEntity = new Hashtable();
	static {
		c_hshLinkCopyEntity.put ("VARDD", "VARDD");
		c_hshLinkCopyEntity.put ("VARPBYAVAIL", "VARPBYAVAIL");
		c_hshLinkCopyEntity.put ("VARPSLAVAIL", "VARPSLAVAIL");
	}

	public static final Hashtable c_hshLinkCopyRelator = new Hashtable();
	static {
		c_hshLinkCopyRelator.put ("VARSBB", "VARSBB");
	}

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: VARSPECBIDPDG.java,v 1.20 2008/09/08 17:31:42 wendy Exp $");
  	}


	public VARSPECBIDPDG(EANMetaFoundation  _mf, VARSPECBIDPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
		m_bGetParentNavInfo = true;
		m_iParentNavInfoLevel = 1;

	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public VARSPECBIDPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);

		m_bCollectInfo = true;
		m_iCollectStep = 1;
		m_bGetParentNavInfo = true;
		m_iParentNavInfoLevel = 1;

	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("VARSPECBIDPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "VARSPECBIDPDG";
  	}

	public String getStepDescription(int iStep) {
		if (iStep == 1) {
			return "Select Parents/Children";
		}
		return "N/A";
	}

	public void setPDGCollectInfo(PDGCollectInfoList _cl, int _iStep, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				m_InfoList = _cl;
				StringBuffer sb = new StringBuffer();
				boolean bFirst = true;
				int iPR = 0;
				for (int i=0; i < m_InfoList.getCollectInfoItemCount(); i++) {
					PDGCollectInfoItem pi = m_InfoList.getCollectInfoItem(i);
					if (pi.isSelected()) {
						String strDesc = pi.toString();
						// check for number of Project selected
						StringTokenizer st1 = new StringTokenizer(strDesc, ":");
						if (st1.countTokens() == 7) {
							String strDirection = st1.nextToken().trim();
							String strParentType = st1.nextToken().trim();
							int iParentID = Integer.parseInt(st1.nextToken().trim());
							String strRelatorType = st1.nextToken().trim();
							int iRelatorID = Integer.parseInt(st1.nextToken().trim());
							String strChildType = st1.nextToken().trim();
							int iChildID = Integer.parseInt(st1.nextToken().trim());

							if (strParentType.equals("PR")) {
								iPR++;
							}

							if (iPR > 1) {
								m_SBREx = new SBRException();
								m_SBREx.add("Only one parent project can be selected.");
								throw m_SBREx;
							}
						}
						sb.append((!bFirst? "\n": "") + strDesc);
						bFirst = false;
					}
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

	public PDGCollectInfoList collectPDGInfo(Database _db, Profile _prof, int _iStep) {
		String strTraceBase = "VARSPECBIDPDG collectPDGInfo";
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				try {
					//get the offering
					_prof = m_utility.setProfValOnEffOn(_db, _prof);
					ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTMFMVAR1");

					EntityItem[] eiParm = {m_eiPDG};

					EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);

					EntityGroup egParent = el.getParentEntityGroup();
					m_eiPDG = egParent.getEntityItem(m_eiPDG.getKey());
					for(int i=0; i < m_eiPDG.getUpLinkCount(); i++) {
						EntityItem eiR = (EntityItem)m_eiPDG.getUpLink(i);
						if (eiR.getEntityType().equals("VARPCDPDG3")) {
							m_eiVAR = (EntityItem) eiR.getUpLink(0);
							break;
						}
					}

					_db.test(m_eiVAR != null, "Variant Offering entity is null");

					m_InfoList = new PDGCollectInfoList(this, getProfile(), "");
					m_InfoList.setMatrix(false);
					m_InfoList.setColNames(new String[]{"Selected", "Relationship Description", "Direction", "Entity Display Name"});

					// input parent nav info
					if (m_aParentNavInfo != null) {
						for (int i=0; i < m_aParentNavInfo.length; i++) {
							String strEntityType = m_aParentNavInfo[i].getEntityType();
							int iEntityID = m_aParentNavInfo[i].getEntityID();
							EntityGroup  eg = el.getEntityGroup(strEntityType);

							if (eg == null) {
								eg = new EntityGroup(null, _db, _prof, strEntityType, "Edit", true);
							}

							EntityItem ei = new EntityItem(eg, _prof, _db, strEntityType, iEntityID);
							String strDesc = "U:" + ei.getEntityType() + ":" + ei.getEntityID() + ":PRVAR:" + (-iEntityID) + ":" + m_eiVAR.getEntityType() + ":" + m_eiVAR.getEntityID();
							PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, "PRVAR", ei.getKey(), strDesc);
							pi.m_aColInfos = new String[] {"Selected", eg.getLongDescription(), "Parent", ei.getNavAttrDescription()};
							m_InfoList.putCollectInfoItem(pi);
						}
					} else {
						System.out.println(strTraceBase + " m_aParentNavInfo is null");
					}

					for(int i=0; i < m_eiVAR.getUpLinkCount(); i++) {
						EntityItem eiR = (EntityItem)m_eiVAR.getUpLink(i);
						if (c_hshRelatorType.get(eiR.getEntityType()) != null) {
							EntityGroup egR = eiR.getEntityGroup();
							if (egR.isRelator()) {
								EntityItem eiP = (EntityItem) eiR.getUpLink(0);
								String strDesc = "U:" + eiP.getEntityType() + ":" + eiP.getEntityID() + ":" + eiR.getEntityType() + ":" + eiR.getEntityID() + ":" + m_eiVAR.getEntityType() + ":" + m_eiVAR.getEntityID();
								PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, egR.getKey(), eiP.getKey(), strDesc);
								pi.m_aColInfos = new String[] {"Selected", egR.getLongDescription(), "Parent", eiP.getNavAttrDescription()};
								m_InfoList.putCollectInfoItem(pi);
							}
						}
					}

					for(int i=0; i < m_eiVAR.getDownLinkCount(); i++) {
						EntityItem eiR = (EntityItem)m_eiVAR.getDownLink(i);
						if (c_hshRelatorType.get(eiR.getEntityType()) != null) {
							EntityGroup egR = eiR.getEntityGroup();
							if (egR.isRelator()) {
								EntityItem eiC = (EntityItem) eiR.getDownLink(0);
								String strDesc = "D:" + m_eiVAR.getEntityType() + ":" + m_eiVAR.getEntityID() + ":" + eiR.getEntityType() + ":" + eiR.getEntityID() + ":" + eiC.getEntityType() + ":" + eiC.getEntityID();
								PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, egR.getKey(), eiC.getKey(), strDesc);
								pi.m_aColInfos = new String[] {"Selected", egR.getLongDescription(), "Child", eiC.getNavAttrDescription()};
								m_InfoList.putCollectInfoItem(pi);
							}
						}
					}

					// repopulate the list with previous selections
					String strSel = m_utility.getAttrValue(m_eiPDG, "AFINFO");
					StringTokenizer st = new StringTokenizer(strSel, "\n");
					while (st.hasMoreTokens()) {
						String str = st.nextToken();
						StringTokenizer st1 = new StringTokenizer(str, ":");
						String strDirection = st1.nextToken().trim();
						String strParentType = st1.nextToken().trim();
						int iParentID = Integer.parseInt(st1.nextToken().trim());
						String strRelatorType = st1.nextToken().trim();
						int iRelatorID = Integer.parseInt(st1.nextToken().trim());
						String strChildType = st1.nextToken().trim();
						int iChildID = Integer.parseInt(st1.nextToken().trim());
						String strKey = strRelatorType + strChildType + iChildID;
						if (strDirection.equals("U")) {
							strKey = strRelatorType + strParentType + iParentID;
						}

						PDGCollectInfoItem pi = m_InfoList.getCollectInfoItem(strKey);
						if (pi != null) {
							pi.setSelected(true);
						}
					}

			 	} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return m_InfoList;
	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
//		String strTraceBase = " VARSPECBIDPDG executeProduct method";
		StringBuffer sbReturn = new StringBuffer();

	//	StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/VARSPECBIDPDG1.txt";
		m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

		ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "EXTVARSPECBIDPDG");

		OPICMList infoList = new OPICMList();
		infoList.put("PDG", m_eiPDG);
		infoList.put("PR", m_eiRoot);
		infoList.put("VAR", m_eiVAR);

		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, xai, strFileName);
		StringBuffer sbMissing = pdgObject.getMissingEntities();
		if (_bGenData) {
			m_savedEIList = new EANList();
			generateData(_db, _prof, sbMissing,"");
			if (sbMissing.toString().length() <= 0) {
				// get already created VAR
				EntityList el = pdgObject.getEntityList();
				EntityGroup eg = el.getEntityGroup("VAR");
				EANList list = new EANList();
				for (int i=0; i < eg.getEntityItemCount(); i++) {
					list.put(eg.getEntityItem(i));
				}
				StringBuffer sb1 = new StringBuffer();
				sb1.append("map_OFFERINGPNUMB=" + m_strPnumb);
				EntityItem ei = m_utility.findEntityItem(list, "VAR", sb1.toString());
				if (ei != null) {
					m_savedEIList.put(ei);
				}
			}
			linkToSelectedEntities(_db, _prof, m_savedEIList);
		}

		sbReturn.append(sbMissing.toString());

		return sbReturn;
	}

	private void linkToSelectedEntities(Database _db, Profile _prof, EANList _eiList) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
//		String strTraceBase = "VARSPECBIDPDG linkToSelectedEntities";

		Vector vctReturnRelatorKeys = new Vector();
		Vector vctCopyReturnRelatorKeys = new Vector();
		for (int i=0; i < m_vctSelectedEI.size(); i++) {
			String str = (String)m_vctSelectedEI.elementAt(i);
			StringTokenizer st1 = new StringTokenizer(str, ":");
			String strDirection = st1.nextToken().trim();
			String strParentType = st1.nextToken().trim();
			int iParentID = Integer.parseInt(st1.nextToken().trim());
			String strRelatorType = st1.nextToken().trim();
			int iRelatorID = Integer.parseInt(st1.nextToken().trim());
			String strChildType = st1.nextToken().trim();
			int iChildID = Integer.parseInt(st1.nextToken().trim());
			for (int j=0; j < _eiList.size(); j++) {
				EntityItem ei = (EntityItem)_eiList.getAt(j);
				if(c_hshLinkCopyEntity.get(strRelatorType) != null) {
					if (strDirection.equals("U") && ei.getEntityType().equals(strChildType)) {
						vctCopyReturnRelatorKeys.addElement(new ReturnRelatorKey(strRelatorType, -1, strParentType, iParentID, ei.getEntityType(), ei.getEntityID(), true));
					}

					if (strDirection.equals("D") && ei.getEntityType().equals(strParentType)) {
						vctCopyReturnRelatorKeys.addElement(new ReturnRelatorKey(strRelatorType, -1, ei.getEntityType(), ei.getEntityID(), strChildType, iChildID,  true));
					}
				} else if(c_hshLinkCopyRelator.get(strRelatorType) != null) {
					Vector vctCopyRelReturnRelatorKeys = new Vector();
					if (strDirection.equals("U") && ei.getEntityType().equals(strChildType)) {
						vctCopyRelReturnRelatorKeys.addElement(new ReturnRelatorKey(strRelatorType, -1, strParentType, iParentID, ei.getEntityType(), ei.getEntityID(), true));
					}

					if (strDirection.equals("D") && ei.getEntityType().equals(strParentType)) {
						vctCopyRelReturnRelatorKeys.addElement(new ReturnRelatorKey(strRelatorType, -1, ei.getEntityType(), ei.getEntityID(), strChildType, iChildID,  true));
					}
					EntityItem eiR = new EntityItem(null, _prof, strRelatorType, iRelatorID);
					m_utility.linkCopyRel(_db, _prof, vctCopyRelReturnRelatorKeys, eiR);
				} else {
					if (strDirection.equals("U") && ei.getEntityType().equals(strChildType)) {
						vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strRelatorType, -1, strParentType, iParentID, ei.getEntityType(), ei.getEntityID(), true));
					}

					if (strDirection.equals("D") && ei.getEntityType().equals(strParentType)) {
						vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strRelatorType, -1, ei.getEntityType(), ei.getEntityID(), strChildType, iChildID,  true));
					}
				}
			}
		}

		if (vctReturnRelatorKeys.size() > 0) {
			m_utility.link(_db, _prof, vctReturnRelatorKeys);
		}

		if (vctCopyReturnRelatorKeys.size() > 0) {
			m_utility.linkCopy(_db, _prof, vctCopyReturnRelatorKeys, 1, "");
		}

	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
//		String strTraceBase = " VARSPECBIDPDG checkPDGAttribute method";
		for (int i =0; i < _afirmEI.getAttributeCount(); i++) {
			EANAttribute att = _afirmEI.getAttribute(i);
			String textAtt = "";
	//		String sFlagAtt = "";
		//	String sFlagClass = "";
			Vector mFlagAtt = new Vector();

			//int index = -1;
			if (att instanceof EANTextAttribute) {
				textAtt = ((String)att.get()).trim();
			} else if (att instanceof EANFlagAttribute) {
				if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
				//			sFlagAtt = amf[f].getLongDescription().trim();
					//		sFlagClass = amf[f].getFlagCode().trim();
						//	index = f;
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

			if (att.getKey().equals("AFINFO")) {
				m_vctSelectedEI = m_utility.sepLongText(textAtt);
			}
			if (att.getKey().equals("PDGOFPNUMB")) {
				m_strPnumb = textAtt;
			}

		}
	}

	protected void resetVariables() {
		//m_vctReturnEntityKeys = new Vector();
		//m_opList = new EANList();
		m_strPnumb = null;
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " VARSPECBIDPDG viewMissingEntities method";

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		m_SBREx = new SBRException();
		resetVariables();
		if (m_eiPDG == null) {
			String s="PDG entity is null";
			return s.getBytes();
		}
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTPCDPDG3");
		EntityItem[] eiParm = {m_eiPDG};
		EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
		EntityGroup eg = el.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
		for(int i=0; i < m_eiPDG.getUpLinkCount(); i++) {
			EntityItem eiR = (EntityItem)m_eiPDG.getUpLink(i);
			if (eiR.getEntityType().equals("VARPCDPDG3")) {
				m_eiVAR = (EntityItem) eiR.getUpLink(0);
				break;
			}
		}
		_db.test(m_eiVAR != null, "Variant Offering entity is null");

		eg = el.getEntityGroup("PR");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "Source Variant doesn't have Project parent");
		checkPDGAttribute(_db, _prof, m_eiPDG);
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
		String strTraceBase = " VARSPECBIDPDG executeAction method ";
		m_SBREx = new SBRException();
		resetVariables();
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				System.out.println("PDG entity is null");
				return;
			}
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTPCDPDG3");
			EntityItem[] eiParm = {m_eiPDG};
			EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
			EntityGroup eg = el.getParentEntityGroup();
			m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
			for(int i=0; i < m_eiPDG.getUpLinkCount(); i++) {
				EntityItem eiR = (EntityItem)m_eiPDG.getUpLink(i);
				if (eiR.getEntityType().equals("VARPCDPDG3")) {
					m_eiVAR = (EntityItem) eiR.getUpLink(0);
					break;
				}
			}

			_db.test(m_eiVAR != null, "Variant Offering entity is null");

			eg = el.getEntityGroup("PR");
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiRoot = eg.getEntityItem(0);
				}
			}
			_db.test(m_eiRoot != null, "Source Variant doesn't have Project parent");

			checkPDGAttribute(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
				throw m_SBREx;
			}

			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
			m_utility.resetActivities();
			m_sbActivities = new StringBuffer();
			m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
			String strData = checkMissingData(_db, _prof, true).toString();
			if (strData.length() <= 0) {
				m_sbActivities.append("<MSG>There's already an Variant Offering with the same part number " + m_strPnumb + " that has the Project of the source VAR as parent.");
			}

			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
			OPICMList attList = new OPICMList();
			attList.put("AFINFO", "AFINFO= ");
			m_utility.updateAttribute(_db, _prof, m_eiPDG, attList);
			if (strData.length() <= 0) {
				m_SBREx.add("There's already a Variant Offering with the same part number " + m_strPnumb + " that has the Project of the source VAR as parent.");
				throw m_SBREx;
			}


		} catch (SBRException ex) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		} catch (MiddlewareException ex) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_utility.replace(ex.toString(), "(ok)", ""), getLongDescription());
			throw ex;

		}
	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

	}

}
