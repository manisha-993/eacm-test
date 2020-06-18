//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SERVPACKPRESELECTPDG.java,v $
// Revision 1.16  2008/09/04 22:05:42  wendy
// Cleanup RSA warnings
//
// Revision 1.15  2006/02/20 21:39:48  joan
// clean up System.out.println
//
// Revision 1.14  2005/12/15 18:27:03  joan
// adjusted specs
//
// Revision 1.13  2005/12/05 23:20:53  joan
// fixes
//
// Revision 1.12  2005/11/30 18:25:42  joan
// fixes
//
// Revision 1.11  2005/11/30 16:43:44  joan
// fixes
//
// Revision 1.10  2005/11/16 20:37:08  joan
// fixes
//
// Revision 1.9  2005/10/20 16:26:13  joan
// fixes
//
// Revision 1.8  2005/09/07 16:51:30  joan
// fixes
//
// Revision 1.7  2005/09/01 16:51:14  joan
// fixes
//
// Revision 1.6  2005/08/30 22:05:23  joan
// fixes
//
// Revision 1.5  2005/08/30 20:37:54  joan
// fixes
//
// Revision 1.4  2005/08/29 19:10:56  joan
// add file
//
// Revision 1.3  2005/08/29 18:16:18  joan
// fixes
//
// Revision 1.2  2005/08/29 18:12:48  joan
// add more work
//
// Revision 1.1  2005/08/26 22:03:19  joan
// add new pdg
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
 * SERVPACKPRESELECTPDG
 *
 */
public class SERVPACKPRESELECTPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;


    private String m_strMT = null;
    private String m_strCOUNTRY = null;
    private String m_strLSEO = null;
    private EANList m_LSEOList = new EANList();

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
        return "$Id: SERVPACKPRESELECTPDG.java,v 1.16 2008/09/04 22:05:42 wendy Exp $";
    }

    /**
     * SERVPACKPRESELECTPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public SERVPACKPRESELECTPDG(EANMetaFoundation _mf, SERVPACKPRESELECTPDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    /**
     * This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public SERVPACKPRESELECTPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
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
        strbResult.append("SERVPACKPRESELECTPDG:" + getKey() + ":desc:" + getLongDescription());
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
        return "SERVPACKPRESELECTPDG";
    }


    /**
     * (non-Javadoc)
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingData(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase =  "SERVPACKPRESELECTPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();

		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);

		String strFileName = "PDGtemplates/SERVPACK01.txt";
		String strSai = (String) m_saiList.get("CATLGSVCPACPRESEL");
		EntityItem[] aei = null;
		EntityList el = null;
		EntityItem eiLSEO = null;
		EntityItem eiMODEL = null;
		EntityGroup egMODEL = null;
		ExtractActionItem xaiEXTSERVPACKPDG2 = new ExtractActionItem(null, _db, _prof, "EXTSERVPACKPDG2");

		StringTokenizer st = new StringTokenizer(m_strLSEO, ",");
		while (st.hasMoreTokens()) {
			String strSEOID = st.nextToken().trim();
			boolean bAdd = true;
			eiLSEO = (EntityItem) m_LSEOList.get(strSEOID);
            StringBuffer sb = new StringBuffer();
            sb.append("map_CATSEOID=" + strSEOID + ";");
            sb.append("map_CATMACHTYPE=" + m_strMT + ";");
            sb.append("map_OFFCOUNTRY=" + m_strCOUNTRY);

            aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "CATLGSVCPACPRESEL", sb.toString());

            if (aei == null || aei.length <= 0) {
				//If not, verify that the LSEO’s grandparent MODEL.COFCAT = Service.
				//If so, and LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT
				EntityItem[] aeip = {eiLSEO};
				el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeip);

				egMODEL = el.getEntityGroup("MODEL");
				if (egMODEL != null && egMODEL.getEntityItemCount() > 0) {
					for (int i=0; i < egMODEL.getEntityItemCount(); i++) {
						eiMODEL = egMODEL.getEntityItem(i);
						String strValue = m_utility.getAttrValue(eiMODEL, "COFCAT");
						if (!strValue.equals("102")) {
							bAdd = false;
							sbReturn.append("<MSG>MODEL " + eiMODEL.toString() + " is not a Service.</MSG>");
							m_SBREx.add("MODEL " + eiMODEL.toString() + " is not a Service.");
						}
					}
				} else {
					D.ebug(D.EBUG_SPEW,strTraceBase + " MODEL group is empty.");
				}

				/*
				The LSEO must also be compatible with the Machine Type (CATMACHTYPE).
				This is verified as explained in the prior sections. Assume that LSEO 3a
				is the proposed LSEO (has matching SEOID to CATSEOID). Then its parent WWSEO 3 would
				need to have a parent SEOCGOS with a parent SEOCG. That SEOCG would need to have a child
				WWSEO 1 with a parent Model 1 with CATMACHTYPE matching MODEL.MACHTYPEATR.
				*/

				EntityGroup egWWSEO = el.getEntityGroup("WWSEO");
				_db.test(egWWSEO != null, "egWWSEO is null");

				boolean bMTCompat = false;
				for (int w=0; w < egWWSEO.getEntityItemCount(); w++) {
					EntityItem eiWWSEO = egWWSEO.getEntityItem(w);
					EntityItem[] aeiWWSEO = {eiWWSEO};
					EntityList elWWSEO = EntityList.getEntityList(_db, _prof, xaiEXTSERVPACKPDG2, aeiWWSEO);
					EntityGroup egMODEL2 = elWWSEO.getEntityGroup("MODEL");
					_db.test(egMODEL2 != null, "egMODEL2 is null");
					for (int m=0; m < egMODEL2.getEntityItemCount(); m++) {
						EntityItem eiMODEL2 = egMODEL2.getEntityItem(m);
						String strMT2 = m_utility.getAttrValue(eiMODEL2, "MACHTYPEATR");
						if (strMT2.equals(m_strMT)) {
							bMTCompat = true;
						}
					}
				}

				if (!bMTCompat) {
					bAdd = false;
					sbReturn.append("<MSG>LSEO for SEOID=" + strSEOID + " is not compatible with MACHTYPE=" + m_strMT + ".</MSG>");
					m_SBREx.add("LSEO for SEOID=" + strSEOID + " is not compatible with MACHTYPE=" + m_strMT + ".");
				}

			} else {
				bAdd = false;
				sbReturn.append("<MSG>CATLGSVCPACPRESEL for SEOID=" + strSEOID + ", MACHTYPE=" + m_strMT + ", OFFCOUNTRY=" + m_strCOUNTRY +  " already exists.</MSG>");

			}

			// check LSEO's LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT LSEOUNPUBDATEMTRGT (only check LSEOUNPUBDATEMTRGT if it exists or consider it to be 9999-12-31 if it doesn’t exist)
			//D.ebug(D.EBUG_SPEW,strTraceBase + " check LSEO's LSEOPUBDATEMTRGT: " + eiLSEO.dump(false));
			String strLSEOPUBDATEMTRGT = m_utility.getAttrValue(eiLSEO, "LSEOPUBDATEMTRGT");
			String strLSEOUNPUBDATEMTRGT = m_utility.getAttrValue(eiLSEO, "LSEOUNPUBDATEMTRGT");
			if (strLSEOPUBDATEMTRGT == null || strLSEOPUBDATEMTRGT.length() <= 0) {
				bAdd = false;
				sbReturn.append("<MSG>LSEO " + eiLSEO.toString() + ". LSEOPUBDATEMTRGT is blank.</MSG>");
				m_SBREx.add("LSEO " + eiLSEO.toString() + ". LSEOPUBDATEMTRGT is blank.");
			}

			int iDate2 = -1;
			if (strLSEOUNPUBDATEMTRGT == null || strLSEOUNPUBDATEMTRGT.length() <= 0) {
				iDate2 = PDGUtility.LATER;
			} else {
				iDate2 = m_utility.dateCompare(strLSEOUNPUBDATEMTRGT, strCurrentDate);
			}

			if (bAdd) {
				//D.ebug(D.EBUG_SPEW,strTraceBase + ", " + strLSEOPUBDATEMTRGT + ", " + strLSEOUNPUBDATEMTRGT + ", " + strCurrentDate);
				//D.ebug(D.EBUG_SPEW,strTraceBase + Date.valueOf(strCurrentDate).toString());
				int iDate1 = m_utility.dateCompare(strLSEOPUBDATEMTRGT, strCurrentDate);

				//D.ebug(D.EBUG_SPEW,strTraceBase + " comparing dates " + iDate1 + ", " + iDate2);
				if (iDate1 != PDGUtility.LATER && iDate2 != PDGUtility.EARLIER) {
	                OPICMList infoList = new OPICMList();
	                infoList.put("PDG", m_eiPDG);
	                infoList.put("LSEO", eiLSEO);
	                infoList.put("MODEL", eiMODEL);
	                infoList.put("WG", m_eiRoot);
	                _prof = m_utility.setProfValOnEffOn(_db, _prof);
	               	TestPDG pdgObject = new TestPDG(_db, _prof, null, infoList, strFileName );
	                StringBuffer sbMissing = pdgObject.getMissingEntities();

	                pdgObject = null;
	                infoList = null;
	                if (_bGenData) {
	                    m_bGetParentEIAttrs = true;
	                    generateData(_db, _prof, sbMissing, "");
                	}
                	if (sbMissing.toString().length() > 0) {
						sbReturn.append("<MSG>Add CATLGSVCPACPRESEL for SEOID=" + strSEOID + ", MACHTYPE=" + m_strMT + ", OFFCOUNTRY=" + m_strCOUNTRY + "</MSG>");
					}
				} else {
					//D.ebug(D.EBUG_SPEW,strTraceBase + " dates doesn't meet requirement");
					sbReturn.append("<MSG>CATLGSVCPACPRESEL not added because LSEO's LSEOPUBDATEMTRGT and LSEOUNPUBDATEMTRGT don't meet requirements" + "</MSG>");
					m_SBREx.add("CATLGSVCPACPRESEL not added because LSEO's LSEOPUBDATEMTRGT and LSEOUNPUBDATEMTRGT don't meet requirements" + ".");
				}
			}
		}

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
            String textAtt = "";
          //  String sFlagAtt = "";
            String sFlagClass = "";
            Vector mFlagAtt = new Vector();

        //    int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                      //      sFlagAtt = amf[f].getLongDescription().trim();
                            sFlagClass = amf[f].getFlagCode().trim();
                      //      index = f;
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

            if (att.getKey().equals("CATMACHTYPE")) {
                m_strMT = sFlagClass;
            } else if (att.getKey().equals("OFFCOUNTRY")) {
                m_strCOUNTRY = sFlagClass;
            } else if (att.getKey().equals("LSEOIDLIST")) {
                m_strLSEO = textAtt;
            }
        }
		m_strMT = m_utility.getAttrValue(_pdgEI, "CATMACHTYPE");
        if (m_strMT == null) {
            m_SBREx.add("Machine Type is empty.");
            return;
        }

        if (m_strCOUNTRY == null) {
            m_SBREx.add("Offering Country is empty.");
            return;
        }

        if (m_strLSEO == null) {
            m_SBREx.add("LSEO ID List is empty.");
            return;
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

        ExtractActionItem eaItem = null;
        EntityItem[] eiParm = new EntityItem[1];
        EntityList el = null;
        EntityGroup eg = null;
        String s = null;

        String strTraceBase = " SERVPACKPRESELECTPDG viewMissingEntities method";

        _db.debug(D.EBUG_DETAIL, strTraceBase);
        m_SBREx = new SBRException();
        resetVariables();
        if (m_eiPDG == null) {
            s = "PDG entity is null";
            return s.getBytes();
        }
        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR3");
        eiParm[0] = m_eiPDG;
        el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
        eg = el.getParentEntityGroup();
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

        s = checkMissingData(_db, _prof, false).toString();
        if (s.length() <= 0) {
            s = "Generating data is complete";
        }

        m_sbActivities.append(m_utility.getViewXMLString(s));
        m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
        m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());

        return s.getBytes();

    }

    /**
     * (non-Javadoc)
     * executeAction
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#executeAction(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

        ExtractActionItem eaItem = null;
        EntityItem[] eiParm = new EntityItem[1];
        EntityList el = null;
        EntityGroup eg = null;

        String strTraceBase = " SERVPACKPRESELECTPDG executeAction method ";
        m_SBREx = new SBRException();
        String strData = "";
        resetVariables();
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                D.ebug(D.EBUG_SPEW,"PDG entity is null");
                return;
            }

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

            eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR3");
            eiParm[0] =  m_eiPDG;
            _prof = m_utility.setProfValOnEffOn(_db, _prof);
            el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
            eg = el.getParentEntityGroup();
            m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
			// get WG
			eg = el.getEntityGroup("WG");
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiRoot = eg.getEntityItem(0);
				}
			}
			_db.test(m_eiRoot != null, "WG  entity is null");

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
            if (m_SBREx.getErrorCount() > 0) {
                m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
                throw m_SBREx;
            }

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
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
		// search for LSEO
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(m_strLSEO, ",");
		String strSai = (String) m_saiList.get("LSEO");
		EntityItem[] aei = null;

		while (st.hasMoreTokens()) {
			String strSEOID = st.nextToken().trim();
            sb = new StringBuffer();
            sb.append("map_SEOID=" + strSEOID +";");
            sb.append("map_COUNTRYLIST=" + m_strCOUNTRY);

            aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "LSEO", sb.toString());
            if (aei != null && aei.length > 0) {
				//D.ebug(D.EBUG_SPEW,"found LSEO: " + aei[0].dump(false));
                m_LSEOList.put(strSEOID, aei[0]);
            } else {
	            m_SBREx.add("There're no LSEOs for SEOID=" + strSEOID + " and country list=" + m_strCOUNTRY);
			}
		}
    }

}
