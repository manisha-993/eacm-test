//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SDMAINTPDG.java,v $
// Revision 1.9  2008/09/08 17:45:18  wendy
// Cleanup RSA warnings
//
// Revision 1.8  2006/02/20 21:39:48  joan
// clean up System.out.println
//
// Revision 1.7  2005/02/15 00:48:17  joan
// fixes
//
// Revision 1.6  2005/02/14 21:48:28  joan
// fixes
//
// Revision 1.5  2005/02/14 15:52:14  joan
// fixes
//
// Revision 1.4  2005/02/12 00:52:41  joan
// fixes
//
// Revision 1.3  2005/02/12 00:33:45  joan
// fixes
//
// Revision 1.2  2005/02/11 20:58:49  joan
// fixes
//
// Revision 1.1  2005/02/11 00:13:39  joan
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

public class SDMAINTPDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();

	private String m_strOP = null;
	//private String m_strIG = null;
	private String m_strMT = null;
	private String m_strMODEL  = null;
	private String m_strSD1MT = null;
	private String m_strSD1MODEL = null;
	//private String m_strComments = null;
	private String m_strSD2MT = null;
	private String m_strSD2MODEL = null;
	private String m_strDate = null;
	private EANList m_MODELList = new EANList();
	private EANList m_SD1List = new EANList();
	private EANList m_SD2List = new EANList();

	private static String ADD = "SD1ADD";
	private static String REMOVE = "SD1REM";
	private static String REPLACE = "SD1REP";
	private static String ADDMATCH = "SD1ADDM";
	private static String REMOVEMATCH = "SD1REMM";
	private static String UPDATEANNDATE = "SD1UPDA";

	private SBRException m_Warning = new SBRException();

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: SDMAINTPDG.java,v 1.9 2008/09/08 17:45:18 wendy Exp $");
  	}


	public SDMAINTPDG(EANMetaFoundation  _mf, SDMAINTPDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public SDMAINTPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("SDMAINTPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return new String(strbResult);
  	}

  	public String getPurpose() {
  		return "SDMAINTPDG";
  	}

	private StringBuffer removeDEVSUPPORT(Database _db, Profile _prof, EANList _pList, EANList _cList, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
	//	String strTraceBase = "SDMAINTPDG removeDEVSUPPORT method ";
		StringBuffer sbReturn = new StringBuffer();
		EntityItem[] aeic = new EntityItem[_cList.size()];
		_cList.copyTo(aeic);
		EntityItem[] aeip = new EntityItem[_pList.size()];
		_pList.copyTo(aeip);

		//D.ebug(D.EBUG_SPEW,strTraceBase + aeip.toString());
		//D.ebug(D.EBUG_SPEW,strTraceBase + aeic.toString());
		//for (int i=0; i < aeip.length; i++) {
		//	D.ebug(D.EBUG_SPEW,strTraceBase + " aeip: " + aeip[i].getKey());
		//}

		EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeip);
		EntityGroup eg = el.getEntityGroup("DEVSUPPORT");
		//D.ebug(D.EBUG_SPEW,strTraceBase + el.dump(false));
		if (eg != null) {

			if (eg.getEntityItemCount() > 0) {
				for (int i=0; i < eg.getEntityItemCount(); i++) {
					EntityItem eiR = eg.getEntityItem(i);
					EntityItem eiMODEL = (EntityItem)eiR.getUpLink(0);
					EntityItem eiSD = (EntityItem)eiR.getDownLink(0);
					if (_pList.get(eiMODEL.getKey()) != null && _cList.get(eiSD.getKey()) != null) {
						sbReturn.append("<MSG>Supported Device " + eiSD.toString() + " removed from Model " + eiMODEL.toString() + "</MSG>");
					}
				}
			}

			if (sbReturn.toString().length() <= 0) {
				sbReturn.append("<MSG>No DEVSUPPORT relators were found</MSG>");
				m_Warning.add("No DEVSUPPORT relators were found");
			}
		}
		if (_bGenData) {
			m_utility.removeLink(_db, _prof, el, aeip, aeic, "DEVSUPPORT");
		}
		return sbReturn;
	}

	private StringBuffer updateRelAttr(Database _db, Profile _prof, EANList _pList, EANList _cList, OPICMList _attList, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
	//	String strTraceBase = "SDMAINTPDG updateRelAttr method ";
		StringBuffer sbReturn = new StringBuffer();
		EntityItem[] aeip = new EntityItem[_pList.size()];
		_pList.copyTo(aeip);
		//EntityList el = new EntityList(_db, _prof, m_PDGxai, aeip, "MODEL");
		EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeip);
		EntityGroup eg = el.getEntityGroup("DEVSUPPORT");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				for (int i=0; i < eg.getEntityItemCount(); i++) {
					EntityItem eiR = eg.getEntityItem(i);
					EntityItem eiMODEL = (EntityItem)eiR.getUpLink(0);
					EntityItem eiSD = (EntityItem)eiR.getDownLink(0);
					if (_pList.get(eiMODEL.getKey()) != null && _cList.get(eiSD.getKey()) != null) {
						if (_bGenData) {
							m_utility.updateAttribute(_db, _prof, eiR, _attList);
						}

						sbReturn.append("<MSG>Update relator DEVSUPPORT Ann Date for Supported Device " + eiSD.toString() + " and Model " + eiMODEL.toString() + "</MSG>");
					}
				}
			}

			if (sbReturn.toString().length() <= 0) {
				sbReturn.append("<MSG>No DEVSUPPORT relators were found</MSG>");
				m_Warning.add("No DEVSUPPORT relators were found");
			}
		}
		return sbReturn;
	}

	private StringBuffer addDEVSUPPORT(Database _db, Profile _prof, EANList _MODELList, EANList _SDList, String _strFileName, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
	//	String strTraceBase = "SDMAINTPDG addDEVSUPPORT method ";
		StringBuffer sbReturn = new StringBuffer();

		for (int i=0; i < _SDList.size(); i++) {
			String strDate = m_strDate;
			EntityItem eiSD = (EntityItem) _SDList.getAt(i);
			for (int j=0; j < _MODELList.size(); j++) {
				EntityItem eiMODEL = (EntityItem) _MODELList.getAt(j);
				if (strDate == null || strDate.length() <= 0) {
					String strMDate = m_utility.getAttrValue(eiMODEL, "ANNDATE");
					String strSDDate = m_utility.getAttrValue(eiSD, "SDANNDATE");
					if (strMDate.length() > 0 && strSDDate.length() > 0) {
						int date = m_utility.dateCompare(strMDate, strSDDate);

						if (date == PDGUtility.LATER) {
							strDate = strMDate;
						} else {
							strDate = strSDDate;
						}
					} else if (strMDate.length() <= 0 && strSDDate.length() > 0) {
						strDate = strSDDate;
					} else if (strMDate.length() > 0 && strSDDate.length() <= 0) {
						strDate = strMDate;
					}
				}
				OPICMList infoList = new OPICMList();
				infoList.put("PDG", m_eiPDG);
				infoList.put("SD", eiSD);
				infoList.put("MODEL", eiMODEL);
				if (strDate != null && strDate.length() > 0) {
					infoList.put("DATE", strDate);
				}
				_prof = m_utility.setProfValOnEffOn(_db, _prof);
				TestPDG pdgObject = new TestPDG(_db, _prof, eiMODEL, infoList, m_PDGxai, _strFileName);
				StringBuffer sbMissing = pdgObject.getMissingEntities();
				pdgObject = null;
				infoList = null;
				String s = sbMissing.toString();

				if (s.length() > 0) {
					if (_bGenData) {
						m_bGetParentEIAttrs = true;
						generateData(_db, _prof, sbMissing,"");
					}
					sbReturn.append("<MSG>Supported Device " + eiSD.toString() + " added to Model " + eiMODEL.toString() + "</MSG>");
				} else {
					sbReturn.append("<MSG>Existing relator found between Supported Device " + eiSD.toString() + " and Model " + eiMODEL.toString() + "</MSG>");
					m_Warning.add("Existing relator found between Supported Device " + eiSD.toString() + " and Model " + eiMODEL.toString());
				}
			}
		}
		return sbReturn;
	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
	//	String strTraceBase = " SDMAINTPDG executeProduct method";
		StringBuffer sbReturn = new StringBuffer();

		if (m_strOP.equals(ADD)) { 	// add DEVSUPPORT
			sbReturn = addDEVSUPPORT(_db, _prof, m_MODELList, m_SD1List, "PDGtemplates/SDMAINT01.txt", _bGenData) ;
		} else if (m_strOP.equals(REMOVE)) {	// remove DEVSUPPORT
			sbReturn= removeDEVSUPPORT(_db, _prof, m_MODELList,m_SD1List, _bGenData);
		} else if (m_strOP.equals(REPLACE)) {	// REPLACE DEVSUPPORT

			sbReturn.append(removeDEVSUPPORT(_db, _prof, m_MODELList, m_SD2List,  _bGenData).toString());

			sbReturn.append(addDEVSUPPORT(_db, _prof, m_MODELList, m_SD1List, "PDGtemplates/SDMAINT01.txt", _bGenData).toString()); ;

		} else if (m_strOP.equals(ADDMATCH)) {	// add matching

			EntityItem[] aeip = new EntityItem[m_MODELList.size()];
			m_MODELList.copyTo(aeip);

			EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeip);
			EntityGroup eg = el.getEntityGroup("DEVSUPPORT");
			for (int i=0; i < eg.getEntityItemCount(); i++) {
				EntityItem eiR = eg.getEntityItem(i);
				EntityItem eiMODEL = (EntityItem) eiR.getUpLink(0);
				EntityItem eiSD = (EntityItem) eiR.getDownLink(0);
				if (m_MODELList.get(eiMODEL.getKey()) != null && m_SD1List.get(eiSD.getKey()) != null) {
					EANList tempList = new EANList();
					tempList.put(eiMODEL);
					sbReturn.append(addDEVSUPPORT(_db, _prof, tempList, m_SD2List, "PDGtemplates/SDMAINT01.txt", _bGenData).toString()) ;
				}
			}

		} else if (m_strOP.equals(REMOVEMATCH)) {	// remove matching
			EntityItem[] aeip = new EntityItem[m_MODELList.size()];
			m_MODELList.copyTo(aeip);

			EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeip);
			EntityGroup eg = el.getEntityGroup("DEVSUPPORT");

			for (int i=0; i < eg.getEntityItemCount(); i++) {
				EntityItem eiR = eg.getEntityItem(i);
				EntityItem eiP = (EntityItem) eiR.getUpLink(0);
				EntityItem eiC = (EntityItem) eiR.getDownLink(0);
				if (m_MODELList.get(eiP.getKey()) != null && m_SD1List.get(eiC.getKey()) != null) {
					EANList tempList = new EANList();
					tempList.put(eiP);

					sbReturn.append(removeDEVSUPPORT(_db, _prof, tempList, m_SD2List,  _bGenData).toString());

				}
			}
		} else if (m_strOP.equals(UPDATEANNDATE)) {
			if (m_strDate != null && m_strDate.length() > 0) {
				OPICMList attList = new OPICMList();
				attList.put("ANNDATE", "ANNDATE=" + m_strDate);
				sbReturn.append(updateRelAttr(_db, _prof, m_MODELList, m_SD1List, attList, _bGenData).toString());
			} else {
				m_SBREx.add("Announce Date is empty.");
				throw m_SBREx;
			}
		}
		return sbReturn;
	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
	//	String strTraceBase = " SDMAINTPDG checkPDGAttribute method";
		for (int i =0; i < _pdgEI.getAttributeCount(); i++) {
			EANAttribute att = _pdgEI.getAttribute(i);
			String textAtt = "";
	//		String sFlagAtt = "";
			String sFlagClass = "";
			Vector mFlagAtt = new Vector();

	//		int index = -1;
			if (att instanceof EANTextAttribute) {
				textAtt = ((String)att.get()).trim();
			} else if (att instanceof EANFlagAttribute) {
				if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
	//						sFlagAtt = amf[f].getLongDescription().trim();
							sFlagClass = amf[f].getFlagCode().trim();
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

			if (att.getKey().equals("PDGOPERSD1")) {
				m_strOP = sFlagClass;
			} else if (att.getKey().equals("AFSDMACHTYPEATR")) {
				m_strMT = textAtt;
			} else if (att.getKey().equals("AFSDMODELATR")) {
				m_strMODEL = textAtt;
			} else if (att.getKey().equals("AFSD1MACHTYPE")) {
				m_strSD1MT = textAtt;
			} else if (att.getKey().equals("AFSD1MODEL")) {
				m_strSD1MODEL = textAtt;
			} else if (att.getKey().equals("AFSD2MACHTYPE")) {
				m_strSD2MT = textAtt;
			} else if (att.getKey().equals("AFSD2MODEL")) {
				m_strSD2MODEL = textAtt;
			} else if (att.getKey().equals("AFANNDATE01")) {
				m_strDate = textAtt;
			}
		}

		if (m_strMT == null) {
			m_SBREx.add("Machine Type is empty.");
			return;
		}

		if (m_strMODEL == null) {
			m_SBREx.add("Model is empty.");
			return;
		}


		if (m_strSD1MT == null) {
			m_SBREx.add("Support Device Machine Type is empty.");
			return;
		}

		if (m_strSD1MODEL == null) {
			m_SBREx.add("Support Device Model is empty.");
			return;
		}

	}

	protected void resetVariables() {
		m_eiList = new EANList();
		m_MODELList = new EANList();
		m_SD1List = new EANList();
		m_SD2List = new EANList();
		m_Warning = new SBRException();

	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SDMAINTPDG viewMissingEntities method";

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		m_SBREx = new SBRException();
		resetVariables();
		if (m_eiPDG == null) {
			String s="PDG entity is null";
			return s.getBytes();
		}
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTSDPDG1");
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

		//m_sbActivities.append(m_utility.getViewXMLString(s));
		m_sbActivities.append(s);
		m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
		m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());
		String strTemp = m_utility.replace(s, "<MSG>", "");
		strTemp = m_utility.replace(strTemp, "</MSG>", "\n");
		s = strTemp;
		return s.getBytes();
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " SDMAINTPDG executeAction method ";
		m_SBREx = new SBRException();
		resetVariables();
		String strData = "";
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);
			if (m_eiPDG == null) {
				D.ebug(D.EBUG_SPEW,"PDG entity is null");
				return;
			}

			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTSDPDG1");
			EntityItem[] eiParm = {m_eiPDG};
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
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

			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
			m_utility.resetActivities();
			m_sbActivities = new StringBuffer();
			m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
			strData = checkMissingData(_db, _prof, true).toString();
			m_sbActivities.append(strData);

			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
		} catch (SBRException ex) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		}

		if (m_Warning.getErrorCount() > 0) {
			throw m_Warning;
		}
	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		StringBuffer sb = new StringBuffer();
		String strSai = null;

		if (m_strOP.equals(ADD) || m_strOP.equals(REMOVE) || m_strOP.equals(REPLACE) || m_strOP.equals(ADDMATCH) || m_strOP.equals(REMOVEMATCH) || m_strOP.equals(UPDATEANNDATE)) {
			// MODEL not found
			strSai = (String)m_saiList.get("MODEL");
			EntityItem[] aeiMODEL = null;
			Vector v = new Vector();
			String[] aFlagCodes = m_utility.getFlagCodeForLikedDesc(_db, _prof, "MACHTYPEATR", m_strMT.replace('?', '_'));

			for(int i =0; i < aFlagCodes.length; i++) {
				String strFlagCode = aFlagCodes[i];
				sb = new StringBuffer();
				sb.append("map_MACHTYPEATR=" + strFlagCode + ";");
				sb.append("map_MODELATR=" + m_strMODEL.replace('?', '_'));

				EntityItem[] aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
				if (aei != null && aei.length > 0) {
					for (int j=0; j < aei.length; j++) {
						v.addElement(aei[j]);
					}
				}
			}

			aeiMODEL = new EntityItem[v.size()];
			v.copyInto(aeiMODEL);
			if (aeiMODEL != null && aeiMODEL.length > 0) {
				for (int i=0; i < aeiMODEL.length; i++) {
					m_MODELList.put(aeiMODEL[i]);
					m_eiList.put(aeiMODEL[i]);
				}
			} else {
				m_SBREx.add("There're no MODELs for MACHTYPEATR=" + m_strMT + " and MODELATR=" + m_strMODEL + ".");
			}

			// SUPPDEVICE 1 not found

			strSai = (String)m_saiList.get("SUPPDEVICE");
			sb = new StringBuffer();
			sb.append("map_MACHTYPESD="	+ m_strSD1MT + ";");
			sb.append("map_MODELATR="	+ m_strSD1MODEL);

			EntityItem[] aeiSD1 = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "SUPPDEVICE", sb.toString());

			if (aeiSD1 != null && aeiSD1.length == 1) {
				m_SD1List.put(aeiSD1[0]);
			} else if (aeiSD1 != null && aeiSD1.length > 1) {
				m_SBREx.add("There're " + aeiSD1.length + " Support Devices that have MACHTYPE=" + m_strSD1MT + " and MODEL=" + m_strSD1MODEL + ".");
			} else {
				m_SBREx.add("There're no Support Device that has MACHTYPE=" + m_strSD1MT + " and MODEL=" + m_strSD1MODEL + ".");
			}

			if (m_strOP.equals(REPLACE) || m_strOP.equals(ADDMATCH) || m_strOP.equals(REMOVEMATCH)) {
				// SUPPDEVICE 2 not found

				strSai = (String)m_saiList.get("SUPPDEVICE");
				sb = new StringBuffer();
				sb.append("map_MACHTYPESD="	+ m_strSD2MT + ";");
				sb.append("map_MODELATR="	+ m_strSD2MODEL);

				EntityItem[] aeiSD2 = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "SUPPDEVICE", sb.toString());

				if (aeiSD2 != null && aeiSD2.length == 1) {
					m_SD2List.put(aeiSD2[0]);
				} else if (aeiSD2 != null && aeiSD2.length > 1) {
					m_SBREx.add("There're " + aeiSD2.length + " Support Devices that have MACHTYPE=" + m_strSD2MT + " and MODEL=" + m_strSD2MODEL + ".");
				} else {
					m_SBREx.add("There're no Support Device that has MACHTYPE=" + m_strSD2MT + " and MODEL=" + m_strSD2MODEL + ".");
				}
			}
		}

	}

}
