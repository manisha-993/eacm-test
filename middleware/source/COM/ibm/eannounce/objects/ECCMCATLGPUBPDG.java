//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ECCMCATLGPUBPDG.java,v $
// Revision 1.33  2008/02/01 22:10:05  wendy
// Cleanup RSA warnings
//
// Revision 1.32  2006/05/17 00:14:28  joan
// changes
//
// Revision 1.31  2006/05/17 00:06:31  joan
// changes
//
// Revision 1.30  2006/05/03 17:59:07  joan
// changes
//
// Revision 1.29  2006/05/02 23:40:10  joan
// changes
//
// Revision 1.28  2006/05/02 22:49:35  joan
// changes
//
// Revision 1.27  2006/05/02 16:55:51  joan
// fixes
//
// Revision 1.26  2006/05/02 16:30:13  joan
// changes
//
// Revision 1.25  2006/05/02 01:08:48  joan
// changes
//
// Revision 1.24  2006/05/02 00:23:39  joan
// changes
//
// Revision 1.23  2006/05/01 20:43:18  joan
// changes
//
// Revision 1.22  2006/05/01 15:19:31  joan
// fixes
//
// Revision 1.21  2006/04/29 16:46:28  joan
// changes
//
// Revision 1.20  2006/04/28 16:27:31  joan
// fixes
//
// Revision 1.19  2006/04/28 16:13:25  joan
// fixes
//
// Revision 1.18  2006/04/27 16:59:59  joan
// fixes
//
// Revision 1.17  2006/04/26 16:16:51  joan
// fixes
//
// Revision 1.16  2006/04/26 00:51:10  joan
// fixes
//
// Revision 1.15  2006/04/25 16:51:41  joan
// fixes
//
// Revision 1.14  2006/04/25 00:50:52  joan
// fixes
//
// Revision 1.13  2006/04/24 23:18:45  joan
// fixes
//
// Revision 1.12  2006/04/24 22:35:42  joan
// fixes
//
// Revision 1.11  2006/04/24 19:49:45  joan
// fixes
//
// Revision 1.10  2006/04/24 17:57:51  joan
// fixes
//
// Revision 1.9  2006/04/22 18:10:03  joan
// fixes
//
// Revision 1.8  2006/04/21 22:50:00  joan
// changes
//
// Revision 1.7  2006/04/21 21:18:44  joan
// fixes
//
// Revision 1.6  2006/04/21 21:10:56  joan
// fixes
//
// Revision 1.5  2006/04/21 16:49:38  joan
// changes
//
// Revision 1.4  2006/04/20 22:32:16  joan
// changes
//
// Revision 1.3  2006/04/20 20:02:15  joan
// changes
//
// Revision 1.2  2006/04/20 00:37:49  joan
// changes
//
// Revision 1.1  2006/04/18 23:23:36  joan
// add pdg
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
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.transactions.OPICMList;


/**
 * ECCMCATLGPUBPDG
 *
 */
public class ECCMCATLGPUBPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;

	private static final String ATT_CATAUDIENCE = "CATAUDIENCE";
	private static final String ATT_CATNEWOFF = "CATNEWOFF";
	private static final String ATT_CATLGMKTGDESC = "CATLGMKTGDESC";

	private static final String ATT_PF = "PUBFROM";
	//private static final String ATT_PT = "PUBTO";

	private static final String ATT_OFFCOUNTRY	= "OFFCOUNTRY";
	//private static final String ATT_GENAREANAME	= "GENAREANAME";

	public String m_strEntityType = null;
	OPICMList m_audList = new OPICMList();
	//private static final int CHUNK = 500;

    /*
    * Version info
    */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ECCMCATLGPUBPDG.java,v 1.33 2008/02/01 22:10:05 wendy Exp $";
    }

    /**
     * ECCMCATLGPUBPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ECCMCATLGPUBPDG(EANMetaFoundation _mf, ECCMCATLGPUBPDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    /**
     * This represents a PDG Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public ECCMCATLGPUBPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _strActionItemKey);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("ECCMCATLGPUBPDG:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "/n");
        return new String(strbResult);
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "ECCMCATLGPUBPDG";
    }


	private String getCatAudValue(Database _db, Profile _prof, String _strAudience) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
		String strTraceBase = "ECCMCATLGPUBPDG getCatAudValue method ";
		//String strCCOSOLCATALOGNAME = m_utility.getAttrValueDesc(eiCCTO, "CCOSOLCATALOGNAME");

		D.ebug(D.EBUG_SPEW,strTraceBase + " _strAudience: " + _strAudience);
		StringBuffer sbReturn = new StringBuffer();
		StringTokenizer stComma = new StringTokenizer(_strAudience, ",");
		boolean bFirst = true;

		while (stComma.hasMoreTokens()) {
			String strAud = stComma.nextToken();

			String strCatAud = (String)m_audList.get(strAud);
			if (strCatAud == null) {
				String[] aCatAud = m_utility.getFlagCodeForLikedDesc(_db, _prof, ATT_CATAUDIENCE, strAud);
				if (aCatAud == null || aCatAud.length <= 0) {
					strCatAud = "";
				} else {
					strCatAud = aCatAud[0];
				}
				m_audList.put(strAud, strCatAud);
			}

			if (strCatAud != null && strCatAud.length() > 0){
				if (!bFirst) {
					sbReturn.append(",");
				}
				sbReturn.append(strCatAud);
				bFirst = false;

			} else {
				D.ebug(D.EBUG_SPEW,strTraceBase + " unable to find CATAUDIENCE for desc: " + strAud);

			}

		}
		return sbReturn.toString();
	}

    /**
     * (non-Javadoc)
     * checkMissingDataForCCTO
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingDataCCTO(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    public StringBuffer checkMissingDataForCCTO(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase =  "ECCMCATLGPUBPDG checkMissingDataForCCTO method ";
        StringBuffer sbReturn = new StringBuffer();

		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);
		StringBuffer sb = new StringBuffer();
		String strSai = null;
		//OPICMList audList = new OPICMList();

		//String strDate = m_utility.getDate(strCurrentDate, 15);

		String strFileName = "PDGtemplates/ECCMCATLGPUB01.txt";

		String strOFFCOUNTRY1 = m_utility.getAttrValueDesc(m_eiPDG, ATT_OFFCOUNTRY);
		//String strOFFCOUNTRY2 = m_utility.getAttrValue(m_eiPDG, ATT_OFFCOUNTRY);
		String[] aGA = m_utility.getFlagCodeForExactDesc(_db, _prof, "GENAREANAME", strOFFCOUNTRY1);
		if (aGA == null || aGA.length <= 0) {
			D.ebug(D.EBUG_SPEW,strTraceBase + " unable to find GENAREANAME for desc: " + strOFFCOUNTRY1);
			return sbReturn;
		}

		String strGA = aGA[0];

		String strXai =  (String) m_xaiList.get("CCTO");
		ExtractActionItem xaiCCTO = new ExtractActionItem(null, _db, _prof, strXai);

		EntityItem[] aei = {m_eiPDG};

		EntityList elCCTO = EntityList.getEntityList(_db, _prof, xaiCCTO, aei);
		EntityGroup egCCTO = elCCTO.getEntityGroup("CCTO");

		if (elCCTO.getParentEntityGroup().getEntityType().equals("CCTO")) {
			egCCTO = elCCTO.getParentEntityGroup();
		}

		EntityItem eiCCTO = egCCTO.getEntityItem(m_eiPDG.getKey());
		D.ebug(D.EBUG_SPEW,strTraceBase + " eiCCTO: " + eiCCTO.getKey());

		String strCCTO_CCOSOLTARGANNDATE = m_utility.getAttrValueDesc(eiCCTO, "CCOSOLTARGANNDATE");
		String strCCOSOLCATALOGNAME = m_utility.getAttrValueDesc(eiCCTO, "CCOSOLCATALOGNAME");
		String strCATNEWOFF = "No";
		OPICMList infoList = new OPICMList();
		infoList.put("PDG", m_eiPDG);
		String strCatAud = getCatAudValue(_db, _prof, strCCOSOLCATALOGNAME);
		D.ebug(D.EBUG_SPEW,strTraceBase + " found strCatAud: " + strCatAud);
		if (strCatAud == null || strCatAud.length() <= 0) {
			return sbReturn;
		}

		// find CATCCTO
		sb = new StringBuffer();
		sb.append("map_GENAREANAME=" +  strGA + ";");
		sb.append("map_CATPNUMB=" +  m_utility.getAttrValue(eiCCTO, "CCOSOLPNUMB") + ";");
		strSai =  (String) m_saiList.get("CATCCTO");
		EntityItem[] aeiCATCCTO = m_utility.dynaSearch(_db, _prof, null, strSai, "CATCCTO", sb.toString());
		if (aeiCATCCTO == null || aeiCATCCTO.length <= 0) {
			// only create CATCCTO
			infoList.put("CCTO", eiCCTO);
			infoList.put("WG", m_eiRoot);
			infoList.put("AUDIENVALUE", strCatAud);

			//If NOW() < CCOSOLTARGANNDATE + 30 days then "Yes" else "No"
			if (strCCTO_CCOSOLTARGANNDATE.length() > 0) {
				int iDC2 = m_utility.dateCompare(strCurrentDate, m_utility.getDate(strCCTO_CCOSOLTARGANNDATE, 30));
				if (iDC2 == PDGUtility.EARLIER) {
					strCATNEWOFF = "Yes";
				}
			}

			infoList.put("CATNEWOFFVALUE", strCATNEWOFF);

			EntityItem[] aeiPR = getParentPR(elCCTO, eiCCTO);
			if (aeiPR != null && aeiPR.length > 0) {
				infoList.put("PR", aeiPR[0]);
			}

			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			TestPDGII pdgObject = new TestPDGII(_db, _prof, eiCCTO, infoList, strFileName );
			StringBuffer sbMissing = pdgObject.getMissingEntities();
			D.ebug(D.EBUG_SPEW,strTraceBase + " creating entity. ");
			generateDataII(_db, _prof, sbMissing, "");
			pdgObject = null;
			infoList = null;
		} else{
			// update attributes CATNEWOFF, PUBFROM, PUBTO, CATLGMKTGDESC, Set CATWORKFLOW to “Change” if updated.
			String strCCOSOLPNUMBDESC = m_utility.getAttrValue(eiCCTO, "CCOSOLPNUMBDESC");
			OPICMList attL = new OPICMList();
			EntityItem eiCATCCTO = aeiCATCCTO[0];
			String strPrevAudValue = m_utility.getAttrValue(eiCATCCTO, "CATAUDIENCE");
			if (!strPrevAudValue.equals(strCatAud)) {
				attL.put(ATT_CATAUDIENCE, ATT_CATAUDIENCE + "=" + strCatAud);
			}

			if (!m_utility.getAttrValue(eiCATCCTO, ATT_CATNEWOFF).equals(strCATNEWOFF)) {
				attL.put(ATT_CATNEWOFF, ATT_CATNEWOFF + "=" + strCATNEWOFF);
			}

			if (!m_utility.getAttrValue(eiCATCCTO, ATT_PF).equals(strCCTO_CCOSOLTARGANNDATE)) {
				attL.put(ATT_PF, ATT_PF + "=" + strCCTO_CCOSOLTARGANNDATE);
			}

			if (!m_utility.getAttrValue(eiCATCCTO, ATT_CATLGMKTGDESC).equals(strCCOSOLPNUMBDESC)) {
				attL.put(ATT_CATLGMKTGDESC, ATT_CATLGMKTGDESC + "=" + strCCOSOLPNUMBDESC);
			}

			if (attL.size() > 0) {
				String strWorkFlow = m_utility.getAttrValue(eiCATCCTO, "CATWORKFLOW");
				if (strWorkFlow.equals("Override")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=SalesStatusOverride");
				} else if (strWorkFlow.equals("Accept")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
				} else if (strWorkFlow.equals("New")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
				}
				_prof = m_utility.setProfValOnEffOn(_db, _prof);
				D.ebug(D.EBUG_SPEW,strTraceBase + " updating entity " + eiCATCCTO.getKey());
				m_utility.updateAttribute(_db, _prof, eiCATCCTO, attL);
			}
		}
		aeiCATCCTO = null;

		elCCTO = null;
		egCCTO = null;
		aei = null;

        return sbReturn;
    }

	private EntityItem[] getParentPR(EntityList _el, EntityItem _ei) {
	//	String strTraceBase = "ECCMCATLGPUBPDG getParentPR method ";
		EANList eiList = new EANList();
		//D.ebug(D.EBUG_SPEW,strTraceBase + " : " + _el.dump(false));
		if (_ei.getEntityType().equals("CCTO")) {
			Vector vCTO = m_utility.getParentEntityIds(_el, _ei.getEntityType(), _ei.getEntityID(), "CTO", "CCTOCTO");

			EntityGroup egPR = _el.getEntityGroup("PR");
			if (_el.getParentEntityGroup().getEntityType().equals("PR")) {
				egPR = _el.getParentEntityGroup();
			}

			//D.ebug(D.EBUG_SPEW,strTraceBase + " vCTO size: " + vCTO.size());

			for (int i =0; i < vCTO.size(); i++) {
				int iCTO = ((Integer)vCTO.elementAt(i)).intValue();
				//D.ebug(D.EBUG_SPEW, strTraceBase + " iCTO: " + iCTO);
				Vector vPR  = m_utility.getParentEntityIds(_el, "CTO", iCTO, "PR", "PRCTO");
				//D.ebug(D.EBUG_SPEW,strTraceBase + " vPR size: " + vPR.size());
				for (int j = 0; j < vPR.size(); j++) {
					int iPR = ((Integer)vPR.elementAt(j)).intValue();
					try {
						EntityItem eiPR = new EntityItem(egPR, null, "PR", iPR);
						eiPR = egPR.getEntityItem(eiPR.getKey());
						if (eiPR != null) {
							eiList.put(eiPR);
						}
					} catch (MiddlewareRequestException ex) {
						ex.printStackTrace();
					}
				}
			}
		} else if (_ei.getEntityType().equals("CSOL")) {
			Vector vOF = m_utility.getParentEntityIds(_el, _ei.getEntityType(), _ei.getEntityID(), "OF", "OFCSOL");

			EntityGroup egPR = _el.getEntityGroup("PR");
			if (_el.getParentEntityGroup().getEntityType().equals("PR")) {
				egPR = _el.getParentEntityGroup();
			}

			//D.ebug(D.EBUG_SPEW,strTraceBase + " vOF size: " + vOF.size());

			for (int i =0; i < vOF.size(); i++) {
				int iOF = ((Integer)vOF.elementAt(i)).intValue();
				//D.ebug(D.EBUG_SPEW, strTraceBase + " iOF: " + iOF);
				Vector vPR  = m_utility.getParentEntityIds(_el, "OF", iOF, "PR", "PROF");
				//D.ebug(D.EBUG_SPEW,strTraceBase + " vPR size: " + vPR.size());
				for (int j = 0; j < vPR.size(); j++) {
					int iPR = ((Integer)vPR.elementAt(j)).intValue();
					try {
						EntityItem eiPR = new EntityItem(egPR, null, "PR", iPR);
						eiPR = egPR.getEntityItem(eiPR.getKey());
						if (eiPR != null) {
							eiList.put(eiPR);
						}
					} catch (MiddlewareRequestException ex) {
						ex.printStackTrace();
					}
				}
			}
		} else if (_ei.getEntityType().equals("CVAR")) {
			Vector vVAR = m_utility.getParentEntityIds(_el, _ei.getEntityType(), _ei.getEntityID(), "VAR", "VARCVAR");

			EntityGroup egPR = _el.getEntityGroup("PR");
			if (_el.getParentEntityGroup().getEntityType().equals("PR")) {
				egPR = _el.getParentEntityGroup();
			}

			//D.ebug(D.EBUG_SPEW,strTraceBase + " vVAR size: " + vVAR.size());

			for (int i =0; i < vVAR.size(); i++) {
				int iVAR = ((Integer)vVAR.elementAt(i)).intValue();
				//D.ebug(D.EBUG_SPEW, strTraceBase + " iVAR: " + iVAR);
				Vector vPR  = m_utility.getParentEntityIds(_el, "VAR", iVAR, "PR", "PRVAR");
				//D.ebug(D.EBUG_SPEW,strTraceBase + " vPR size: " + vPR.size());
				for (int j = 0; j < vPR.size(); j++) {
					int iPR = ((Integer)vPR.elementAt(j)).intValue();
					try {
						EntityItem eiPR = new EntityItem(egPR, null, "PR", iPR);
						eiPR = egPR.getEntityItem(eiPR.getKey());
						if (eiPR != null) {
							eiList.put(eiPR);
						}
					} catch (MiddlewareRequestException ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		EntityItem[] aeiPR = new EntityItem[eiList.size()];
		eiList.copyTo(aeiPR);
		return aeiPR;
	}

	public EntityItem[] getArray(Profile _prof, String[] _asei, int _iStart, int _iEnd) {
		String strTraceBase = "ECCMCATLGPUBPDG getArray method ";
		System.out.println(strTraceBase + " start: " + _iStart + ", end: " + _iEnd);
		Vector v = new Vector();
		try {
			for (int i= _iStart; i < _iEnd; i++) {
				String strEI = _asei[i];
				StringTokenizer st = new StringTokenizer(strEI, ":");
				if (st.countTokens() == 2) {
					String strEntityType = st.nextToken();
					int iEntityID = Integer.parseInt(st.nextToken().trim());
					EntityItem ei = new EntityItem(null, _prof, strEntityType, iEntityID);
					v.addElement(ei);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		EntityItem[] aeiReturn = new EntityItem[v.size()];
		v.toArray(aeiReturn);
		return aeiReturn;
	}

    /**
     * (non-Javadoc)
     * checkMissingDataForCSOL
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingDataCSOL(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */

    public StringBuffer checkMissingDataForCSOL(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase =  "ECCMCATLGPUBPDG checkMissingDataForCSOL method ";
        StringBuffer sbReturn = new StringBuffer();

		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);
		StringBuffer sb = new StringBuffer();
		String strSai = null;
		//OPICMList audList = new OPICMList();

		//String strDate = m_utility.getDate(strCurrentDate, 15);

		//String strPDHDomain = "";
		String strFileName = "PDGtemplates/ECCMCATLGPUB02.txt";

		String strOFFCOUNTRY1 = m_utility.getAttrValueDesc(m_eiPDG, ATT_OFFCOUNTRY);
		//String strOFFCOUNTRY2 = m_utility.getAttrValue(m_eiPDG, ATT_OFFCOUNTRY);

		String[] aGA = m_utility.getFlagCodeForExactDesc(_db, _prof, "GENAREANAME", strOFFCOUNTRY1);
		if (aGA == null || aGA.length <= 0) {
			D.ebug(D.EBUG_SPEW,strTraceBase + " unable to find GENAREANAME for desc: " + strOFFCOUNTRY1);
			return sbReturn;
		}

		String strGA = aGA[0];

		String strXai =  (String) m_xaiList.get("CSOL");
		ExtractActionItem xaiCSOL = new ExtractActionItem(null, _db, _prof, strXai);


		EntityItem[] aei = {m_eiPDG};

		EntityList elCSOL = EntityList.getEntityList(_db, _prof, xaiCSOL, aei);
		EntityGroup egCSOL = elCSOL.getEntityGroup("CSOL");
		if (elCSOL.getParentEntityGroup().getEntityType().equals("CSOL")) {
			egCSOL = elCSOL.getParentEntityGroup();
		}


		EntityItem eiCSOL = egCSOL.getEntityItem(m_eiPDG.getKey());
		D.ebug(D.EBUG_SPEW,strTraceBase + " eiCSOL: " + eiCSOL.getKey());

		OPICMList infoList = new OPICMList();
		infoList.put("PDG", m_eiPDG);
		String strCATNEWOFF = "No";
		String strTARG_ANN_DATE_CT = m_utility.getAttrValueDesc(eiCSOL, "TARG_ANN_DATE_CT");
		String strPNUMB_DESC_CT = m_utility.getAttrValueDesc(eiCSOL, "PNUMB_DESC_CT");

		String strCATALOG_NAME_CT = m_utility.getAttrValueDesc(eiCSOL, "CATALOG_NAME_CT");
		String strCatAud = getCatAudValue(_db, _prof, strCATALOG_NAME_CT);
		D.ebug(D.EBUG_SPEW,strTraceBase + " found strCatAud: " + strCatAud);

		if (strCatAud == null || strCatAud.length() <= 0) {
			return sbReturn;
		}

		// find CATCSOL
		sb = new StringBuffer();

		sb.append("map_GENAREANAME=" +  strGA + ";");
		sb.append("map_CATPNUMB=" +  m_utility.getAttrValue(eiCSOL, "PNUMB_CT") + ";");
		strSai =  (String) m_saiList.get("CATCSOL");
		EntityItem[] aeiCATCSOL = m_utility.dynaSearch(_db, _prof, null, strSai, "CATCSOL", sb.toString());
		if (aeiCATCSOL == null || aeiCATCSOL.length <= 0) {
			// only create CATCSOL
			infoList.put("CSOL", eiCSOL);
			infoList.put("WG", m_eiRoot);
			infoList.put("AUDIENVALUE", strCatAud);

			//If NOW() < TARG_ANN_DATE_CT  + 30 days then "Yes" else "No"
			if (strTARG_ANN_DATE_CT.length() > 0) {
				int iDC2 = m_utility.dateCompare(strCurrentDate, m_utility.getDate(strTARG_ANN_DATE_CT, 30));
				if (iDC2 == PDGUtility.EARLIER) {
					strCATNEWOFF = "Yes";
				}
			}

			infoList.put("CATNEWOFFVALUE", strCATNEWOFF);

			EntityItem[] aeiPR = getParentPR(elCSOL, eiCSOL);
			if (aeiPR != null && aeiPR.length > 0) {
				infoList.put("PR", aeiPR[0]);
			}

			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			TestPDGII pdgObject = new TestPDGII(_db, _prof, eiCSOL, infoList, strFileName );
			StringBuffer sbMissing = pdgObject.getMissingEntities();
			D.ebug(D.EBUG_SPEW,strTraceBase + " creating entity. ");
			generateDataII(_db, _prof, sbMissing, "");
			pdgObject = null;
			infoList = null;
		} else{
			// update attributes CATNEWOFF, PUBFROM, PUBTO, CATLGMKTGDESC, Set CATWORKFLOW to “Change” if updated.

			OPICMList attL = new OPICMList();
			EntityItem eiCATCSOL = aeiCATCSOL[0];
			if (!m_utility.getAttrValue(eiCATCSOL, "CATAUDIENCE").equals(strCatAud)) {
				attL.put(ATT_CATAUDIENCE, ATT_CATAUDIENCE + "=" + strCatAud);
			}

			if (!m_utility.getAttrValue(eiCATCSOL, ATT_CATNEWOFF).equals(strCATNEWOFF)) {
				attL.put(ATT_CATNEWOFF, ATT_CATNEWOFF + "=" + strCATNEWOFF);
			}

			if (!m_utility.getAttrValue(eiCATCSOL, ATT_PF).equals(strTARG_ANN_DATE_CT)) {
				attL.put(ATT_PF, ATT_PF + "=" + strTARG_ANN_DATE_CT);
			}

			if (!m_utility.getAttrValue(eiCATCSOL, ATT_CATLGMKTGDESC).equals(strPNUMB_DESC_CT)) {
				attL.put(ATT_CATLGMKTGDESC, ATT_CATLGMKTGDESC + "=" + strPNUMB_DESC_CT);
			}

			if (attL.size() > 0) {
				String strWorkFlow = m_utility.getAttrValue(eiCATCSOL, "CATWORKFLOW");
				if (strWorkFlow.equals("Override")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=SalesStatusOverride");
				} else if (strWorkFlow.equals("Accept")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
				} else if (strWorkFlow.equals("New")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
				}
				_prof = m_utility.setProfValOnEffOn(_db, _prof);
				m_utility.updateAttribute(_db, _prof, eiCATCSOL, attL);
			}
		}

		aeiCATCSOL = null;
		elCSOL = null;
		egCSOL = null;
		aei = null;

        return sbReturn;
    }

    /**
     * (non-Javadoc)
     * checkMissingDataForCVAR
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingDataCVAR(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */

    public StringBuffer checkMissingDataForCVAR(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase =  "ECCMCATLGPUBPDG checkMissingDataForCVAR method ";
        StringBuffer sbReturn = new StringBuffer();

		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);
		StringBuffer sb = new StringBuffer();
		String strSai = null;
		//OPICMList audList = new OPICMList();
		//String strDate = m_utility.getDate(strCurrentDate, 15);

		//String strPDHDomain = "";
		String strFileName = "PDGtemplates/ECCMCATLGPUB03.txt";

		String strOFFCOUNTRY1 = m_utility.getAttrValueDesc(m_eiPDG, ATT_OFFCOUNTRY);
		//String strOFFCOUNTRY2 = m_utility.getAttrValue(m_eiPDG, ATT_OFFCOUNTRY);

		String[] aGA = m_utility.getFlagCodeForExactDesc(_db, _prof, "GENAREANAME", strOFFCOUNTRY1);
		if (aGA == null || aGA.length <= 0) {
			D.ebug(D.EBUG_SPEW,strTraceBase + " unable to find GENAREANAME for desc: " + strOFFCOUNTRY1);
			return sbReturn;
		}

		String strGA = aGA[0];

		String strXai =  (String) m_xaiList.get("CVAR");
		ExtractActionItem xaiCVAR = new ExtractActionItem(null, _db, _prof, strXai);

		EntityItem[] aei = {m_eiPDG};

		EntityList elCVAR = EntityList.getEntityList(_db, _prof, xaiCVAR, aei);
		EntityGroup egCVAR = elCVAR.getEntityGroup("CVAR");
		if (elCVAR.getParentEntityGroup().getEntityType().equals("CVAR")) {
			egCVAR = elCVAR.getParentEntityGroup();
		}

		EntityItem eiCVAR = egCVAR.getEntityItem(m_eiPDG.getKey());
		D.ebug(D.EBUG_SPEW,strTraceBase + " eiCVAR: " + eiCVAR.getKey());

		String strTARGANNDATE_CVAR = m_utility.getAttrValueDesc(eiCVAR, "TARGANNDATE_CVAR ");
		OPICMList infoList = new OPICMList();
		infoList.put("PDG", m_eiPDG);

		String strCATNEWOFF = "No";

		String strCATALOGNAME_CVAR = m_utility.getAttrValueDesc(eiCVAR, "CATALOGNAME_CVAR");

		String strCatAud = getCatAudValue(_db, _prof, strCATALOGNAME_CVAR);
		D.ebug(D.EBUG_SPEW,strTraceBase + " found strCatAud: " + strCatAud);
		if (strCatAud == null || strCatAud.length() <= 0) {
			return sbReturn;
		}

		// find CATCVAR
		sb = new StringBuffer();
		sb.append("map_GENAREANAME=" + strGA + ";");
		sb.append("map_CATPNUMB=" +  m_utility.getAttrValue(eiCVAR, "PNUMB_CT") + ";");
		strSai =  (String) m_saiList.get("CATCVAR");
		EntityItem[] aeiCATCVAR = m_utility.dynaSearch(_db, _prof, null, strSai, "CATCVAR", sb.toString());
		if (aeiCATCVAR == null || aeiCATCVAR.length <= 0) {
			// only create CATCVAR
			infoList.put("CVAR", eiCVAR);
			infoList.put("WG", m_eiRoot);
			infoList.put("AUDIENVALUE", strCatAud);
			//If NOW() < TARGANNDATE_CVAR  + 30 days then "Yes" else "No"
			if (strTARGANNDATE_CVAR .length() > 0) {
				int iDC2 = m_utility.dateCompare(strCurrentDate, m_utility.getDate(strTARGANNDATE_CVAR, 30));
				if (iDC2 == PDGUtility.EARLIER) {
					strCATNEWOFF = "Yes";
				}
			}

			infoList.put("CATNEWOFFVALUE", strCATNEWOFF);
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			TestPDGII pdgObject = new TestPDGII(_db, _prof, eiCVAR, infoList, strFileName );
			StringBuffer sbMissing = pdgObject.getMissingEntities();
			D.ebug(D.EBUG_SPEW,strTraceBase + " creating entity. ");
			generateDataII(_db, _prof, sbMissing, "");
			pdgObject = null;
			infoList = null;
		} else{
			// update attributes CATNEWOFF, PUBFROM, PUBTO, CATLGMKTGDESC, Set CATWORKFLOW to “Change” if updated.
			String strPNUMB_DESC_CT = m_utility.getAttrValue(eiCVAR, "PNUMB_DESC_CT");
			OPICMList attL = new OPICMList();
			EntityItem eiCATCVAR = aeiCATCVAR[0];
			if (!m_utility.getAttrValue(eiCATCVAR, "CATAUDIENCE").equals(strCatAud)) {
				attL.put(ATT_CATAUDIENCE, ATT_CATAUDIENCE + "=" + strCatAud);
			}

			if (!m_utility.getAttrValue(eiCATCVAR, ATT_CATNEWOFF).equals(strCATNEWOFF)) {
				attL.put(ATT_CATNEWOFF, ATT_CATNEWOFF + "=" + strCATNEWOFF);
			}

			if (!m_utility.getAttrValue(eiCATCVAR, ATT_PF).equals(strTARGANNDATE_CVAR)) {
				attL.put(ATT_PF, ATT_PF + "=" + strTARGANNDATE_CVAR);
			}

			if (!m_utility.getAttrValue(eiCATCVAR, ATT_CATLGMKTGDESC).equals(strPNUMB_DESC_CT)) {
				attL.put(ATT_CATLGMKTGDESC, ATT_CATLGMKTGDESC + "=" + strPNUMB_DESC_CT);
			}

			if (attL.size() > 0) {
				String strWorkFlow = m_utility.getAttrValue(eiCATCVAR, "CATWORKFLOW");
				if (strWorkFlow.equals("Override")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=SalesStatusOverride");
				} else if (strWorkFlow.equals("Accept")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
				} else if (strWorkFlow.equals("New")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
				}
				_prof = m_utility.setProfValOnEffOn(_db, _prof);
				m_utility.updateAttribute(_db, _prof, eiCATCVAR, attL);
			}
		}
		aeiCATCVAR = null;

		elCVAR = null;
		egCVAR = null;
		aei = null;

        return sbReturn;
	}

    /**
     * (non-Javadoc)
     * checkMissingDataForCB
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingDataCB(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */

    public StringBuffer checkMissingDataForCB(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase =  "ECCMCATLGPUBPDG checkMissingDataForCB method ";
        StringBuffer sbReturn = new StringBuffer();

		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);
		StringBuffer sb = new StringBuffer();
		String strSai = null;
//		OPICMList audList = new OPICMList();
		//String strDate = m_utility.getDate(strCurrentDate, 15);

		//String strPDHDomain = "";
		String strFileName = "PDGtemplates/ECCMCATLGPUB04.txt";
		String strOFFCOUNTRY1 = m_utility.getAttrValueDesc(m_eiPDG, ATT_OFFCOUNTRY);
	//	String strOFFCOUNTRY2 = m_utility.getAttrValue(m_eiPDG, ATT_OFFCOUNTRY);

		String[] aGA = m_utility.getFlagCodeForExactDesc(_db, _prof, "GENAREANAME", strOFFCOUNTRY1);
		if (aGA == null || aGA.length <= 0) {
			D.ebug(D.EBUG_SPEW,strTraceBase + " unable to find GENAREANAME for desc: " + strOFFCOUNTRY1);
			return sbReturn;
		}

		String strGA = aGA[0];

		String strXai =  (String) m_xaiList.get("CB");
		ExtractActionItem xaiCB = new ExtractActionItem(null, _db, _prof, strXai);

		EntityItem[] aei = {m_eiPDG};
		EntityList elCB = EntityList.getEntityList(_db, _prof, xaiCB, aei);
		EntityGroup egCB = elCB.getEntityGroup("CB");

		if (elCB.getParentEntityGroup().getEntityType().equals("CB")) {
			egCB = elCB.getParentEntityGroup();
		}

		EntityItem eiCB = egCB.getEntityItem(m_eiPDG.getKey());

		D.ebug(D.EBUG_DETAIL,strTraceBase + " eiCB: " + eiCB.getKey());
		OPICMList infoList = new OPICMList();
		infoList.put("PDG", m_eiPDG);

		String strCATNEWOFF = "No";
		String strTARGANNDATE_CB = m_utility.getAttrValueDesc(eiCB, "TARG_ANN_DATE_CB");

		String strCATALOGNAME_CB = m_utility.getAttrValueDesc(eiCB, "CATALOG_NAME_CB");
		String strCatAud = getCatAudValue(_db, _prof, strCATALOGNAME_CB);
		D.ebug(D.EBUG_SPEW,strTraceBase + " found strCatAud: " + strCatAud);
		if (strCatAud == null || strCatAud.length() <= 0) {
			return sbReturn;
		}

		// find CATCB
		sb = new StringBuffer();
		sb.append("map_GENAREANAME=" + strGA + ";");
		sb.append("map_CATPNUMB=" +  m_utility.getAttrValue(eiCB, "PNUMB_CT") + ";");
		strSai =  (String) m_saiList.get("CATCB");
		EntityItem[] aeiCATCB = m_utility.dynaSearch(_db, _prof, null, strSai, "CATCB", sb.toString());
		if (aeiCATCB == null || aeiCATCB.length <= 0) {
			// only create CATCB
			infoList.put("CB", eiCB);
			infoList.put("WG", m_eiRoot);
			infoList.put("AUDIENVALUE", strCatAud);

			//If NOW() < TARGANNDATE_CB  + 30 days then "Yes" else "No"
			if (strTARGANNDATE_CB .length() > 0) {
				int iDC2 = m_utility.dateCompare(strCurrentDate, m_utility.getDate(strTARGANNDATE_CB, 30));
				if (iDC2 == PDGUtility.EARLIER) {
					strCATNEWOFF = "Yes";
				}
			}

			infoList.put("CATNEWOFFVALUE", strCATNEWOFF);
			_prof = m_utility.setProfValOnEffOn(_db, _prof);
			TestPDGII pdgObject = new TestPDGII(_db, _prof, eiCB, infoList, strFileName );
			StringBuffer sbMissing = pdgObject.getMissingEntities();
			D.ebug(D.EBUG_SPEW,strTraceBase + " creating entity. ");
			generateDataII(_db, _prof, sbMissing, "");
			pdgObject = null;
			infoList = null;
		} else{
			// update attributes CATNEWOFF, PUBFROM, PUBTO, CATLGMKTGDESC, Set CATWORKFLOW to “Change” if updated.
			String strPNUMB_DESC_CT = m_utility.getAttrValue(eiCB, "PNUMB_DESC_CT");

			OPICMList attL = new OPICMList();
			EntityItem eiCATCB = aeiCATCB[0];
			if (!m_utility.getAttrValue(eiCATCB, "CATAUDIENCE").equals(strCatAud)) {
				attL.put(ATT_CATAUDIENCE, ATT_CATAUDIENCE + "=" + strCatAud);
			}

			if (!m_utility.getAttrValue(eiCATCB, ATT_CATNEWOFF).equals(strCATNEWOFF)) {
				attL.put(ATT_CATNEWOFF, ATT_CATNEWOFF + "=" + strCATNEWOFF);
			}

			if (!m_utility.getAttrValue(eiCATCB, ATT_PF).equals(strTARGANNDATE_CB)) {
				attL.put(ATT_PF, ATT_PF + "=" + strTARGANNDATE_CB);
			}

			if (!m_utility.getAttrValue(eiCATCB, ATT_CATLGMKTGDESC).equals(strPNUMB_DESC_CT)) {
				attL.put(ATT_CATLGMKTGDESC, ATT_CATLGMKTGDESC + "=" + strPNUMB_DESC_CT);
			}

			if (attL.size() > 0) {
				String strWorkFlow = m_utility.getAttrValue(eiCATCB, "CATWORKFLOW");
				if (strWorkFlow.equals("Override")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=SalesStatusOverride");
				} else if (strWorkFlow.equals("Accept")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
				} else if (strWorkFlow.equals("New")) {
					attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
				}

				_prof = m_utility.setProfValOnEffOn(_db, _prof);
				m_utility.updateAttribute(_db, _prof, eiCATCB, attL);
			}
		}
		aeiCATCB = null;
		elCB = null;
		egCB = null;
		aei = null;

        return sbReturn;
	}


    /**
     * (non-Javadoc)
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingData(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
  //      String strTraceBase =  "ECCMCATLGPUBPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();
        if (m_eiPDG.getEntityType().equals("CCTO")) {
			sbReturn = checkMissingDataForCCTO(_db, _prof, _bGenData);
		} else if (m_eiPDG.getEntityType().equals("CSOL")) {
			sbReturn = checkMissingDataForCSOL(_db, _prof, _bGenData);
		} else if (m_eiPDG.getEntityType().equals("CVAR")) {
			sbReturn = checkMissingDataForCVAR(_db, _prof, _bGenData);
		} else if (m_eiPDG.getEntityType().equals("CB")) {
			sbReturn = checkMissingDataForCB(_db, _prof, _bGenData);
		}
        return sbReturn;
	}

	/*private void scanAll(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//Scan all instance of CATLGPUB where CATNEWOFF = “Yes” and PUBFROM + 30 days < NOW().
		//Set CATNEWOFF = “No” and set CATWORKFLOW = “Change”.

		String strTraceBase = "ECCMCATLGPUBPDG scanAll method ";
		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);

		StringBuffer sb = new StringBuffer();
        sb.append("map_CATNEWOFF=Yes");
        String strSai =  (String) m_saiList.get("CATLGPUB");
		EntityItem[] aeiCATLGPUB = m_utility.dynaSearch(_db, _prof, null, strSai, "CATLGPUB", sb.toString());
		if (aeiCATLGPUB != null) {
			for (int i=0; i < aeiCATLGPUB.length; i++) {
				EntityItem eiCATLGPUB = aeiCATLGPUB[i];
				String strPF = m_utility.getAttrValue(eiCATLGPUB, ATT_PF);
				if (strPF.length() > 0) {
					int iDCPF = m_utility.dateCompare(m_utility.getDate(strPF, 30), strCurrentDate);
					if (iDCPF == PDGUtility.EARLIER) {
						OPICMList attL = new OPICMList();
						attL.put(ATT_CATNEWOFF, ATT_CATNEWOFF + "=No");
						String strWorkFlow = m_utility.getAttrValue(eiCATLGPUB, "CATWORKFLOW");

						if (strWorkFlow.equals("Override")) {
							attL.put("CATWORKFLOW", "CATWORKFLOW=SalesStatusOverride");
						} else if (strWorkFlow.equals("Accept")) {
							attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
						} else if (strWorkFlow.equals("New")) {
							attL.put("CATWORKFLOW", "CATWORKFLOW=Change");
						}


						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						D.ebug(D.EBUG_SPEW,strTraceBase + " scanning updating CATLGPUB: " + eiCATLGPUB.getKey());
						m_utility.updateAttribute(_db, _prof, eiCATLGPUB, attL);

					}

				}

			}
		}
	}*/

	/*private void removeCATLGPUB (Database _db, Profile _prof, String _strEntityType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		/ *
		Instances of CATLGPUB should be removed whenever the instance has:
		not been updated for the past 30 days
		and PUBTO < NOW() + 30 days
		* /
		String strTraceBase = "ECCMCATLGPUBPDG removeCATLGPUB method " + _strEntityType;
		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);
		String strDate = m_utility.getDate(strCurrentDate, 30);

		StringBuffer sb = new StringBuffer();
        sb.append("map_OFFCOUNTRY=" +  m_utility.getAttrValue(m_eiPDG, "OFFCOUNTRY"));
        String strSai =  (String) m_saiList.get(_strEntityType);
		EntityItem[] aeiCAT = m_utility.dynaSearch(_db, _prof, null, strSai, _strEntityType, sb.toString());
		if (aeiCAT != null) {
			for (int i=0; i < aeiCAT.length; i++) {
				EntityItem eiCAT = aeiCAT[i];

				String strPT = m_utility.getAttrValue(eiCAT, ATT_PT);
				if (strPT.length() > 0) {
					int iDCPT = m_utility.dateCompare(m_utility.getDate(strPT, 30), strCurrentDate);
					if (iDCPT == PDGUtility.EARLIER) {
						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						// check for last update
						EntityChangeHistoryGroup echg = new EntityChangeHistoryGroup(_db,_prof, eiCAT);
						EntityChangeHistoryItem echi = (EntityChangeHistoryItem)getCurrentChangeItem(echg);
						String strChangeDate = echi.getChangeDate();

						D.ebug(D.EBUG_SPEW,strTraceBase + " checking last update CAT: " + eiCAT.getKey());
						int iDate1 = m_utility.dateCompare(m_utility.getDate(strChangeDate.substring(0,10), 30), strCurrentDate);
						if (iDate1 == m_utility.EARLIER) {
							D.ebug(D.EBUG_SPEW,strTraceBase + " deactivate : " + eiCAT.getKey());
							_prof = m_utility.setProfValOnEffOn(_db, _prof);
							EANUtility.deactivateEntity(_db, _prof, eiCAT);
							//m_utility.updateAttribute(_db, _prof, eiCAT, dList);
						}
					}
				}
			}
		}
	}*/

    /**
     * (non-Javadoc)
     * checkPDGAttribute
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkPDGAttribute(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem)
     */
    protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
       /* for (int i = 0; i < _pdgEI.getAttributeCount(); i++) {
            EANAttribute att = _pdgEI.getAttribute(i);
            String textAtt = "";
            String sFlagAtt = "";
            String sFlagClass = "";
            Vector mFlagAtt = new Vector();

            int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            sFlagAtt = amf[f].getLongDescription().trim();
                            sFlagClass = amf[f].getFlagCode().trim();
                            index = f;
                            break;
                        }
                    }
                } else if (att instanceof MultiFlagAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            mFlagAtt.addElement(amf[f].getLongDescription().trim());
                        }
                    }
                }
            }
        }*/

    }

    /**
     * (non-Javadoc)
     * resetVariables
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#resetVariables()
     */
    protected void resetVariables() {
        m_eiList = new EANList();
    }

    /**
     * (non-Javadoc)
     * viewMissing
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#viewMissing(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        return null;
    }

	/*private static ChangeHistoryItem getCurrentChangeItem(ChangeHistoryGroup _chg) {
		for (int i = 0; i < _chg.getChangeHistoryItemCount(); i++) {
			ChangeHistoryItem chi = (ChangeHistoryItem) _chg.getChangeHistoryItem(i);

			if (chi.isValid()) {
				return chi;
			}
		}

		return null;
	}*/

    /**
     * (non-Javadoc)
     * executeAction
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#executeAction(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

        //ExtractActionItem eaItem = null;
        //EntityItem[] eiParm = new EntityItem[1];
        //EntityList el = null;
        //EntityGroup eg = null;

        String strTraceBase = " ECCMCATLGPUBPDG executeAction method ";
        m_SBREx = new SBRException();
        String strData = "";
        resetVariables();
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                D.ebug(D.EBUG_SPEW,"PDG entity is null");
                return;
            }

            _prof = m_utility.setProfValOnEffOn(_db, _prof);
			// get WG
			EntityGroup egWG = new EntityGroup(null, _db, _prof, "WG", "Edit", false);
			m_eiRoot = new EntityItem(egWG, _prof, _db, _prof.getWGName(), _prof.getWGID());

			EntityGroup egPDG = new EntityGroup(null, _db, _prof, m_eiPDG.getEntityType(), "Edit", false);
			m_eiPDG = new EntityItem(egPDG, _prof, _db, m_eiPDG.getEntityType(), m_eiPDG.getEntityID());

            // validate data
            if (m_SBREx.getErrorCount() > 0) {
                throw m_SBREx;
            }

            m_utility.resetActivities();
            m_sbActivities = new StringBuffer();
            m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
            strData = checkMissingData(_db, _prof, true).toString();
            m_sbActivities.append(strData);

        } catch (SBRException ex) {
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
            throw ex;
        }
    }

    /**
     * (non-Javadoc)
     * checkDataAvailability
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkDataAvailability(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem)
     */
    protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    }

}
