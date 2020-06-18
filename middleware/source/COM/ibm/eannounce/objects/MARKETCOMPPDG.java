//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MARKETCOMPPDG.java,v $
// Revision 1.10  2008/09/04 19:37:28  wendy
// Cleanup RSA warnings
//
// Revision 1.9  2006/03/04 16:59:11  joan
// fixes
//
// Revision 1.8  2005/12/20 16:36:52  joan
// changes
//
// Revision 1.7  2005/12/08 21:02:59  joan
// fixes
//
// Revision 1.6  2005/11/21 18:16:13  joan
// fixes
//
// Revision 1.5  2005/11/16 20:37:08  joan
// fixes
//
// Revision 1.4  2005/11/15 19:23:21  joan
// fixes
//
// Revision 1.3  2005/09/07 16:51:30  joan
// fixes
//
// Revision 1.2  2005/09/01 16:51:14  joan
// fixes
//
// Revision 1.1  2005/08/29 19:10:56  joan
// add file
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


/**
 * MARKETCOMPPDG
 *
 */
public class MARKETCOMPPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;


    private String m_strLSEO = null;
   // private EANList m_LSEOList = new EANList();

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
        return "$Id: MARKETCOMPPDG.java,v 1.10 2008/09/04 19:37:28 wendy Exp $";
    }

    /**
     * MARKETCOMPPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public MARKETCOMPPDG(EANMetaFoundation _mf, MARKETCOMPPDG _ai) throws MiddlewareRequestException {
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
    public MARKETCOMPPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
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
        strbResult.append("MARKETCOMPPDG:" + getKey() + ":desc:" + getLongDescription());
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
        return "MARKETCOMPPDG";
    }


    /**
     * (non-Javadoc)
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingData(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
       // String strTraceBase =  "MARKETCOMPPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();

		String strSai = (String) m_saiList.get("CATLGMKTGCOMPAT");
		EntityItem[] aei = null;

		StringTokenizer st = new StringTokenizer(m_strLSEO, ",");
		while (st.hasMoreTokens()) {
			String strSEOID = st.nextToken().trim();
			//boolean bAdd = true;
            StringBuffer sb = new StringBuffer();
            sb.append("map_CATSEOID=" + strSEOID);

            aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "CATLGMKTGCOMPAT", sb.toString());
			if (aei == null || aei.length <= 0) {
				sbReturn.append("<MSG>CATLGMKTGCOMPAT for CATSEOID=" + strSEOID + " doesn't exist</MSG>");
			} else {
				for (int i=0; i < aei.length; i++) {
					EntityItem ei = aei[i];
					String strWorkFlow = m_utility.getAttrValue(ei, "CATWORKFLOW");
					OPICMList attList = new OPICMList();
					attList.put("OKTOPUB", "OKTOPUB=No");

					if (strWorkFlow.equals("Override")) {
						attList.put("CATWORKFLOW", "CATWORKFLOW=SalesStatusOverride");
					} else if (strWorkFlow.equals("Accept")) {
						attList.put("CATWORKFLOW", "CATWORKFLOW=Change");
					} else if (strWorkFlow.equals("New")) {
						attList.put("CATWORKFLOW", "CATWORKFLOW=Change");
					}

					m_utility.updateAttribute(_db, _prof, ei, attList);
					sbReturn.append("<MSG>Update CATLGMKTGCOMPAT " + ei.toString() + "</MSG>");
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
           // String sFlagAtt = "";
            //String sFlagClass = "";
            Vector mFlagAtt = new Vector();

            //int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
              //              sFlagAtt = amf[f].getLongDescription().trim();
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

			if (att.getKey().equals("LSEOIDLIST")) {
                m_strLSEO = textAtt;
            }
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

        String strTraceBase = " MARKETCOMPPDG viewMissingEntities method";

        _db.debug(D.EBUG_DETAIL, strTraceBase);
        m_SBREx = new SBRException();
        resetVariables();
        if (m_eiPDG == null) {
            s = "PDG entity is null";
            return s.getBytes();
        }
        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR4");
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

        String strTraceBase = " MARKETCOMPPDG executeAction method ";
        m_SBREx = new SBRException();
        String strData = "";
        resetVariables();
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                System.out.println("PDG entity is null");
                return;
            }

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

            eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR4");
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
    }

}
