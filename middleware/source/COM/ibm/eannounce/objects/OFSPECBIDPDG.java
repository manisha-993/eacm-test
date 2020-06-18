//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: OFSPECBIDPDG.java,v $
// Revision 1.25  2008/09/04 22:05:29  wendy
// Cleanup some RSA warnings
//
// Revision 1.24  2006/02/20 21:39:47  joan
// clean up System.out.println
//
// Revision 1.23  2005/03/09 00:27:20  joan
// work on display nav attributes
//
// Revision 1.22  2005/03/08 21:53:00  joan
// fixes
//
// Revision 1.21  2005/03/08 19:40:33  joan
// fixes
//
// Revision 1.20  2005/02/28 20:29:51  joan
// add throw exception
//
// Revision 1.19  2005/02/23 23:03:59  joan
// fixes
//
// Revision 1.18  2005/02/23 21:47:18  joan
// fixes
//
// Revision 1.17  2005/02/23 20:03:17  joan
// work on get parent from navigation
//
// Revision 1.16  2005/02/11 20:24:24  joan
// fixes
//
// Revision 1.15  2005/01/26 19:18:16  joan
// fixes
//
// Revision 1.14  2005/01/19 23:26:38  joan
// fixes
//
// Revision 1.13  2005/01/18 23:36:15  joan
// fixes
//
// Revision 1.12  2005/01/12 22:21:26  joan
// fixes
//
// Revision 1.11  2005/01/12 21:16:53  joan
// fixes
//
// Revision 1.10  2004/11/17 00:06:11  joan
// changes to getRowIndex key in rowselectable table
//
// Revision 1.9  2004/11/15 16:51:45  joan
// fix compile
//
// Revision 1.8  2004/11/15 16:36:40  joan
// work on CR
//
// Revision 1.7  2004/11/12 20:44:59  joan
// adjust error messages
//
// Revision 1.6  2004/11/11 19:20:29  joan
// fixes
//
// Revision 1.5  2004/08/09 17:00:09  joan
// fixes
//
// Revision 1.4  2004/08/05 20:36:16  joan
// work on special bid PDG
//
// Revision 1.3  2004/08/04 17:48:09  joan
// fix compile
//
// Revision 1.2  2004/08/04 17:43:04  joan
// fix bug
//
// Revision 1.1  2004/08/04 17:30:54  joan
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

public class OFSPECBIDPDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();
	//private EANList m_opList = new EANList();
	private Vector m_vctSelectedEI = new Vector();
	private String m_strPnumb = null;

	public static final Hashtable c_hshRelatorType = new Hashtable();
	static {
		c_hshRelatorType.put ("OFAUD","OFAUD");
		c_hshRelatorType.put ("OFBAT", "OFBAT");
		c_hshRelatorType.put ("OFCPG", "OFCPG");
		c_hshRelatorType.put ("OFCA", "OFCA");
		c_hshRelatorType.put ("OFCDR", "OFCDR");
		c_hshRelatorType.put ("OFCPGOS", "OFCPGOS");
		c_hshRelatorType.put ("OFDD", "OFDD");
		c_hshRelatorType.put ("OFED", "OFED");
		c_hshRelatorType.put ("OFEI", "OFEI");
		c_hshRelatorType.put ("OFFB", "OFFB");
		c_hshRelatorType.put ("OFFD", "OFFD");
		c_hshRelatorType.put ("OFFM", "OFFM");
		c_hshRelatorType.put ("OFGOA", "OFGOA");
		c_hshRelatorType.put ("OFHD", "OFHD");
		c_hshRelatorType.put ("OFGRA", "OFGRA");
		c_hshRelatorType.put ("OFHDC", "OFHDC");
		c_hshRelatorType.put ("OFIMG", "OFIMG");
		c_hshRelatorType.put ("OFIN", "OFIN");
		c_hshRelatorType.put ("OFKB", "OFKB");
		c_hshRelatorType.put ("OFMB", "OFMB");
		c_hshRelatorType.put ("OFMEM", "OFMEM");
		c_hshRelatorType.put ("OFMM", "OFMM");
		c_hshRelatorType.put ("OFMON", "OFMON");
		c_hshRelatorType.put ("OFNIC", "OFNIC");
		c_hshRelatorType.put ("OFPBY", "OFPBY");
		c_hshRelatorType.put ("OFNP", "OFNP");
		c_hshRelatorType.put ("OFPBYAVAIL", "OFPBYAVAIL");
		c_hshRelatorType.put ("OFPK", "OFPK");
		c_hshRelatorType.put ("OFPORT", "OFPORT");
		c_hshRelatorType.put ("OFPOS", "OFPOS");
		c_hshRelatorType.put ("OFPP", "OFPP");
		c_hshRelatorType.put ("OFPRC", "OFPRC");
		c_hshRelatorType.put ("OFPRJ", "OFPRJ");
		c_hshRelatorType.put ("OFPS", "OFPS");
		c_hshRelatorType.put ("OFPSL", "OFPSL");
		c_hshRelatorType.put ("OFPSLAVAIL", "OFPSLAVAIL");
		c_hshRelatorType.put ("OFRC", "OFRC");
		c_hshRelatorType.put ("OFSEC", "OFSEC");
		c_hshRelatorType.put ("OFSBB", "OFSBB");
		c_hshRelatorType.put ("OFSC", "OFSC");
		c_hshRelatorType.put ("OFSER", "OFSER");
		c_hshRelatorType.put ("OFSM", "OFSM");
		c_hshRelatorType.put ("OFSO", "OFSO");
		c_hshRelatorType.put ("OFSP", "OFSP");
		c_hshRelatorType.put ("OFTD", "OFTD");
		c_hshRelatorType.put ("OFTI", "OFTI");
		c_hshRelatorType.put ("OFTIF", "OFTIF");
		c_hshRelatorType.put ("OFWAR", "OFWAR");
		c_hshRelatorType.put ("OFWS", "OFWS");
		c_hshRelatorType.put ("CPGOF", "CPGOF");
		c_hshRelatorType.put ("PROF", "PROF");
	}

	public static final Hashtable c_hshLinkCopyEntity = new Hashtable();
	static {
		c_hshLinkCopyEntity.put ("OFDD", "OFDD");
		c_hshLinkCopyEntity.put ("OFGOA", "OFGOA");
		c_hshLinkCopyEntity.put ("OFPBYAVAIL", "OFPBYAVAIL");
		c_hshLinkCopyEntity.put ("OFPSLAVAIL", "OFPSLAVAIL");
	}

	public static final Hashtable c_hshLinkCopyRelator = new Hashtable();
	static {
		c_hshLinkCopyRelator.put ("OFSBB", "OFSBB");
	}

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: OFSPECBIDPDG.java,v 1.25 2008/09/04 22:05:29 wendy Exp $");
  	}


	public OFSPECBIDPDG(EANMetaFoundation  _mf, OFSPECBIDPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
		m_bGetParentNavInfo = true;
		m_iParentNavInfoLevel = 1;
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public OFSPECBIDPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);

		m_bCollectInfo = true;
		m_iCollectStep = 1;
		m_bGetParentNavInfo = true;
		m_iParentNavInfoLevel = 1;

	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("OFSPECBIDPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "OFSPECBIDPDG";
  	}

	public String getStepDescription(int iStep) {
		if (iStep == 1) {
			return "Select Parents/Children";
		}
		return "N/A";
	}

	public void setPDGCollectInfo(PDGCollectInfoList _cl, int _iStep, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
		//String strTraceBase = "OFSPECBIDPDG collectPDGInfo";
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				m_InfoList = _cl;
				int iPR = 0;
				StringBuffer sb = new StringBuffer();
				boolean bFirst = true;
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
		String strTraceBase = "OFSPECBIDPDG collectPDGInfo";
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				try {
					//get the offering
					_prof = m_utility.setProfValOnEffOn(_db, _prof);

					ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTMFMOF1");

					EntityItem[] eiParm = {m_eiPDG};

					EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);

					EntityGroup egParent = el.getParentEntityGroup();
					m_eiPDG = egParent.getEntityItem(m_eiPDG.getKey());
					for(int i=0; i < m_eiPDG.getUpLinkCount(); i++) {
						EntityItem eiR = (EntityItem)m_eiPDG.getUpLink(i);
						if (eiR.getEntityType().equals("OFPCDPDG2")) {
							m_eiRoot = (EntityItem) eiR.getUpLink(0);
							break;
						}
					}

					_db.test(m_eiRoot != null, "Offering entity is null");

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
							String strDesc = "U:" + ei.getEntityType() + ":" + ei.getEntityID() + ":PROF:" + (-iEntityID) + ":" + m_eiRoot.getEntityType() + ":" + m_eiRoot.getEntityID();
							PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, "PROF", ei.getKey(), strDesc);
							pi.m_aColInfos = new String[] {"Selected", eg.getLongDescription(), "Parent", ei.getNavAttrDescription()};
							m_InfoList.putCollectInfoItem(pi);
						}
					} else {
						D.ebug(D.EBUG_SPEW,strTraceBase + " m_aParentNavInfo is null");
					}

					for(int i=0; i < m_eiRoot.getUpLinkCount(); i++) {
						EntityItem eiR = (EntityItem)m_eiRoot.getUpLink(i);
						if (c_hshRelatorType.get(eiR.getEntityType()) != null) {
							EntityGroup egR = eiR.getEntityGroup();
							if (egR.isRelator()) {
								EntityItem eiP = (EntityItem) eiR.getUpLink(0);
								String strDesc = "U:" + eiP.getEntityType() + ":" + eiP.getEntityID() + ":" + eiR.getEntityType() + ":" + eiR.getEntityID() + ":" + m_eiRoot.getEntityType() + ":" + m_eiRoot.getEntityID();
								PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, egR.getKey(), eiP.getKey(), strDesc);
								pi.m_aColInfos = new String[] {"Selected", egR.getLongDescription(), "Parent", eiP.getNavAttrDescription()};
								m_InfoList.putCollectInfoItem(pi);
							}
						}
					}

					for(int i=0; i < m_eiRoot.getDownLinkCount(); i++) {
						EntityItem eiR = (EntityItem)m_eiRoot.getDownLink(i);
						if (c_hshRelatorType.get(eiR.getEntityType()) != null) {
							EntityGroup egR = eiR.getEntityGroup();
							if (egR.isRelator()) {
								EntityItem eiC = (EntityItem) eiR.getDownLink(0);
								String strDesc = "D:" + m_eiRoot.getEntityType() + ":" + m_eiRoot.getEntityID() + ":" + eiR.getEntityType() + ":" + eiR.getEntityID() + ":" + eiC.getEntityType() + ":" + eiC.getEntityID();
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
		//String strTraceBase = " OFSPECBIDPDG executeProduct method";
		StringBuffer sbReturn = new StringBuffer();

	//	StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/OFSPECBIDPDG1.txt";
		m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

		ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "EXTOFSPECBIDPDG");

		OPICMList infoList = new OPICMList();
		infoList.put("PDG", m_eiPDG);
		infoList.put("OF", m_eiRoot);

		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, xai, strFileName);
		StringBuffer sbMissing = pdgObject.getMissingEntities();
		if (_bGenData) {
			m_savedEIList = new EANList();
			generateData(_db, _prof, sbMissing,"");
			if (sbMissing.toString().length() <= 0) {
				// get already created VAR
				EntityList el = pdgObject.getEntityList();
				EntityGroup eg = el.getEntityGroup("OF");
				EANList list = new EANList();
				for (int i=0; i < eg.getEntityItemCount(); i++) {
					list.put(eg.getEntityItem(i));
				}
				StringBuffer sb1 = new StringBuffer();
				sb1.append("map_OFFERINGPNUMB=" + m_strPnumb);
				EntityItem ei = m_utility.findEntityItem(list, "OF", sb1.toString());
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
	//	String strTraceBase = "OFSPECBIDPDG linkToSelectedEntities";

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
	//	String strTraceBase = " OFSPECBIDPDG checkPDGAttribute method";
		for (int i =0; i < _afirmEI.getAttributeCount(); i++) {
			EANAttribute att = _afirmEI.getAttribute(i);
			String textAtt = "";
		//	String sFlagAtt = "";
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
					//		sFlagAtt = amf[f].getLongDescription().trim();
					//		sFlagClass = amf[f].getFlagCode().trim();
					//		index = f;
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
		//String m_strPnumb = null;
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " OFSPECBIDPDG viewMissingEntities method";

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		m_SBREx = new SBRException();
		resetVariables();
		if (m_eiPDG == null) {
			String s="PDG entity is null";
			return s.getBytes();
		}
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTPCDPDG2");
		EntityItem[] eiParm = {m_eiPDG};
		EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
		EntityGroup eg = el.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
		eg = el.getEntityGroup("OF");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "Offering entity is null");
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
		String strTraceBase = " OFSPECBIDPDG executeAction method ";
		m_SBREx = new SBRException();
		resetVariables();

		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				D.ebug(D.EBUG_SPEW,"PDG entity is null");
				return;
			}
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTPCDPDG2");
			EntityItem[] eiParm = {m_eiPDG};
			EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
			EntityGroup eg = el.getParentEntityGroup();
			m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
			eg = el.getEntityGroup("OF");
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiRoot = eg.getEntityItem(0);
				}
			}
			_db.test(m_eiRoot != null, "Offering entity is null");

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
				m_sbActivities.append("<MSG>There's already an Offering with the same part number " + m_strPnumb + " that has Make From Model Offering as parent.</MSG>");
			}
			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
			OPICMList attList = new OPICMList();
			attList.put("AFINFO", "AFINFO= ");
			m_utility.updateAttribute(_db, _prof, m_eiPDG, attList);
			if (strData.length() <= 0) {
				m_SBREx.add("There's already an Offering with the same part number " + m_strPnumb + " that has Make From Model Offering as parent.");
				throw m_SBREx;
			}
		} catch (SBRException ex) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		} catch (MiddlewareException ex) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;

		}
	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

	}

}
