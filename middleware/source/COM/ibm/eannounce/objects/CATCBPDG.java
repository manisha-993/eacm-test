//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CATCBPDG.java,v $
// Revision 1.4  2008/09/04 20:45:14  wendy
// Cleanup RSA warnings
//
// Revision 1.3  2006/05/19 17:16:21  joan
// changes
//
// Revision 1.2  2006/05/18 15:22:40  joan
// changes
//
// Revision 1.1  2006/05/17 23:09:16  joan
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
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.transactions.OPICMList;


/**
 * CATCBPDG
 *
 */
public class CATCBPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;

	private static final String ATT_CATAUDIENCE = "CATAUDIENCE";
	private static final String ATT_CATNEWOFF = "CATNEWOFF";
	private static final String ATT_CATLGMKTGDESC = "CATLGMKTGDESC";

	private static final String ATT_PF = "PUBFROM";
	//private static final String ATT_PT = "PUBTO";

	//private static final String ATT_OFFCOUNTRY	= "OFFCOUNTRY";
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
        return "$Id: CATCBPDG.java,v 1.4 2008/09/04 20:45:14 wendy Exp $";
    }

    /**
     * CATCBPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public CATCBPDG(EANMetaFoundation _mf, CATCBPDG _ai) throws MiddlewareRequestException {
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
    public CATCBPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
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
        strbResult.append("CATCBPDG:" + getKey() + ":desc:" + getLongDescription());
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
        return "CATCBPDG";
    }


	private String getCatAudValue(Database _db, Profile _prof, String _strAudience) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
		String strTraceBase = "CATCBPDG getCatAudValue method ";
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

	public EntityItem[] getArray(Profile _prof, String[] _asei, int _iStart, int _iEnd) {
		String strTraceBase = "CATCBPDG getArray method ";
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
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingDataCB(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */

    public StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase =  "CATCBPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();

		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);
		StringBuffer sb = new StringBuffer();
		String strSai = null;
		//OPICMList audList = new OPICMList();
		//String strDate = m_utility.getDate(strCurrentDate, 15);

		//String strPDHDomain = "";
		String strFileName = "PDGtemplates/ECCMCATLGPUB04.txt";

		String strGA = m_utility.getAttrValue(m_eiPDG, "GENAREANAME");

		String strXai =  (String) m_xaiList.get("CB");
		ExtractActionItem xaiCB = new ExtractActionItem(null, _db, _prof, strXai);

		EntityItem[] aei = {m_eiPDG};
		EntityList elCB = EntityList.getEntityList(_db, _prof, xaiCB, aei);
		EntityGroup egCB = elCB.getEntityGroup("CB");
		EntityGroup egCATCB = elCB.getEntityGroup("CATCB");

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
			//A CATCB is updated if it is a child of the CB and has the following identical attributes for the one being “included”:
			//•	CATLGCNTRY.OFFCOUNTRY = OFFCOUNTRY
			//•	CATLGCNTRY.CATAUDIENCE = CATAUDIENCE

			// update attributes CATNEWOFF, PUBFROM, PUBTO, CATLGMKTGDESC, Set CATWORKFLOW to “Change” if updated.
			String strPNUMB_DESC_CT = m_utility.getAttrValue(eiCB, "PNUMB_DESC_CT");

			for (int i = 0; i < aeiCATCB.length; i++) {
				OPICMList attL = new OPICMList();
				EntityItem eiCATCB = aeiCATCB[i];

				if (egCATCB.getEntityItem(eiCATCB.getKey()) == null) {
					continue;
				}

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
		}
		aeiCATCB = null;
		elCB = null;
		egCB = null;
		aei = null;

        return sbReturn;
	}

    /**
     * (non-Javadoc)
     * checkPDGAttribute
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkPDGAttribute(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem)
     */
    protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
        for (int i = 0; i < _pdgEI.getAttributeCount(); i++) {
            EANAttribute att = _pdgEI.getAttribute(i);
            //String textAtt = "";
            //String sFlagAtt = "";
            //String sFlagClass = "";
            Vector mFlagAtt = new Vector();

            //int index = -1;
            if (att instanceof EANTextAttribute) {
              //  textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                  //          sFlagAtt = amf[f].getLongDescription().trim();
                //            sFlagClass = amf[f].getFlagCode().trim();
              //              index = f;
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
        }

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


    /**
     * (non-Javadoc)
     * executeAction
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#executeAction(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

       // ExtractActionItem eaItem = null;
       // EntityItem[] eiParm = new EntityItem[1];
       // EntityList el = null;
       // EntityGroup eg = null;

        String strTraceBase = " CATCBPDG executeAction method ";
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
