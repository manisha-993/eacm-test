// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: SWSUPPFEAT30APDG.java,v $
// Revision 1.12  2008/09/08 17:32:46  wendy
// Cleanup RSA warnings
//
// Revision 1.11  2007/09/06 12:57:16  couto
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.10  2006/05/08 21:25:15  joan
// add print trace
//
// Revision 1.9  2005/08/04 14:39:26  joan
// fixes
//
// Revision 1.8  2005/03/14 17:01:14  dave
// backing out multiple nls flag changes
//
// Revision 1.7  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.6  2004/11/12 20:44:59  joan
// adjust error messages
//
// Revision 1.5  2004/10/05 17:57:44  joan
// fixes
//
// Revision 1.4  2004/09/03 15:58:02  joan
// fixes
//
// Revision 1.3  2004/09/02 17:04:55  joan
// fixes
//
// Revision 1.2  2004/09/02 16:57:01  joan
// fixes
//
// Revision 1.1  2004/09/02 16:16:47  joan
// add files
//
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.transactions.OPICMList;

/**
 * SWSUPPFEAT30APDG
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SWSUPPFEAT30APDG extends PDGActionItem {

    private static final long serialVersionUID = 2830531999096504999L;
    
	private String m_strAfOptFeature = "m00";
    //private Vector m_afSuppProdVec = new Vector();
    //private String m_strAfBillingTemplate = null;
    private String m_strAfMachType = null;
    private String m_strAfModel = null;
    private String m_strAnnCodeName = null;
    //private Vector m_afOsLevelVec = new Vector();
    private String m_strAfReqType = null;

    //private EANList m_availList = new EANList();
    //private Vector m_vctReturnEntityKeys = new Vector();
    //private EANList m_opList = new EANList();

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
        return "$Id: SWSUPPFEAT30APDG.java,v 1.12 2008/09/08 17:32:46 wendy Exp $";
    }

    /**
     * SWSUPPFEAT30APDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public SWSUPPFEAT30APDG(EANMetaFoundation _mf, SWSUPPFEAT30APDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    /**
     * SWSUPPFEAT30APDG
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public SWSUPPFEAT30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
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
        strbResult.append("SWSUPPFEAT30APDG:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "/n");
        return strbResult.toString();
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "SWSUPPFEAT30APDG";
    }

    /**
     * (non-Javadoc)
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingData(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        StringBuffer sbReturn = new StringBuffer();

        EntityItem eiBaseCOF = (EntityItem) m_eiList.get("BASE");
        _db.test(eiBaseCOF != null, " Base MODEL is null");
        String strFileName = "PDGtemplates/SuppFeat_30a.txt";
        m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        m_utility.getOptFeatIDAbr(m_strAfOptFeature);

        OPICMList infoList = new OPICMList();
        infoList.put("WG", m_eiRoot);
        infoList.put("PDG", m_eiPDG);
        infoList.put("GEOIND", "GENAREASELECTION");
        TestPDGII pdgObject = new TestPDGII(_db, _prof, eiBaseCOF, infoList, m_PDGxai, strFileName);
        StringBuffer sbMissing = pdgObject.getMissingEntities();
        if (_bGenData) {
            generateDataII(_db, _prof, sbMissing, "");
        }

        sbReturn.append(sbMissing.toString());
        return sbReturn;
    }

  /*  private Vector checkOSLEVELUnique(Vector _v) {
        Hashtable ht = new Hashtable();
        for (int i = 0; i < _v.size(); i++) {
            String s = (String) _v.elementAt(i);
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

    /**
     * (non-Javadoc)
     * checkPDGAttribute
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkPDGAttribute(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem)
     */
    protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {

        for (int i = 0; i < _afirmEI.getAttributeCount(); i++) {
            EANAttribute att = _afirmEI.getAttribute(i);
            String textAtt = "";
            String sFlagAtt = "";
          //  String sFlagClass = "";
            Vector mFlagAtt = new Vector();

            //int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            sFlagAtt = amf[f].getLongDescription().trim();
              //              sFlagClass = amf[f].getFlagCode().trim();
                //            index = f;
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

            if (att.getKey().equals("AFSWSUPPREQUESTTYPE")) {
                m_strAfReqType = sFlagAtt;
            } else if (att.getKey().equals("OPTFEATUREID")) {
                m_SBREx = m_utility.checkOptFeatureIDFormat(textAtt, PDGUtility.OF_SUPPORT, false, m_SBREx);
                m_strAfOptFeature = textAtt;
            } else if (att.getKey().equals("MACHTYPEATR")) {
                m_strAfMachType = sFlagAtt;
            } else if (att.getKey().equals("MODELATR")) {
                m_strAfModel = textAtt;
            } else if (att.getKey().equals("OSLEVEL")) {
                //m_afOsLevelVec = checkOSLEVELUnique(mFlagAtt);
            } else if (att.getKey().equals("AFBILLINGTEMPLATE")) {
                //m_strAfBillingTemplate = sFlagAtt;
            } else if (att.getKey().equals("AFMTM")) {
                m_SBREx = m_utility.checkSuppProdString(textAtt, m_SBREx);
                //m_afSuppProdVec = m_utility.sepLongText(textAtt);
            } else if (att.getKey().equals("GENAREASELECTION")) {
                m_SBREx = m_utility.checkGenAreaOverlap(mFlagAtt, m_SBREx);
            } else if (att.getKey().equals("ANNCODENAME")) {
                m_strAnnCodeName = sFlagAtt;
            }
        }

        if (!"Support Feature".equals(m_strAfReqType)) {
            SBRException ex = new SBRException();
            ex.add(" Request Type:" + m_strAfReqType + ". This action item is for SW Support Feature Request.");
            throw ex;
        }

        if (m_strAnnCodeName == null || m_strAnnCodeName.length() <= 0) {
            m_SBREx.add("ANNCODENAME is required.");
        }

    }

    /**
     * (non-Javadoc)
     * resetVariables
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#resetVariables()
     */
    protected void resetVariables() {
        m_strAfOptFeature = "m00";
        //m_afSuppProdVec = new Vector();
        //m_strAfBillingTemplate = null;
        m_strAfMachType = null;
        m_strAfModel = null;
        //m_afOsLevelVec = new Vector();
        //m_availList = new EANList();
        m_eiList = new EANList();
        //m_opList = new EANList();
        //m_vctReturnEntityKeys = new Vector();
    }

    /**
     * (non-Javadoc)
     * viewMissing
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#viewMissing(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " SWSUPPFEAT30APDG viewMissing method";

        _db.debug(D.EBUG_DETAIL, strTraceBase);
        m_SBREx = new SBRException();
        resetVariables();
        if (m_eiPDG == null) {
            String s = "PDG entity is null";
            return s.getBytes();
        }

        ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT6");
        EntityItem[] eiParm = { m_eiPDG };
        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
        EntityGroup eg = el.getParentEntityGroup();
        m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
        eg = el.getEntityGroup("WG");
        if (eg != null) {
            if (eg.getEntityItemCount() > 0) {
                m_eiRoot = eg.getEntityItem(0);
            }
        }
        _db.test(m_eiRoot != null, "Work Group entity is null");

        checkPDGAttribute(_db, _prof, m_eiPDG);
        checkDataAvailability(_db, _prof, m_eiPDG);
        // validate data
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

    /**
     * (non-Javadoc)
     * executeAction
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#executeAction(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " SWSUPPFEAT30APDG executeAction method";
     //   String strData = "";
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            m_SBREx = new SBRException();
            resetVariables();
            if (m_eiPDG == null) {
                System.out.println("PDG entity is null");
                return;
            }

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

            ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT6");
            EntityItem[] eiParm = { m_eiPDG };
            _prof = m_utility.setProfValOnEffOn(_db, _prof);
            EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
            EntityGroup eg = el.getParentEntityGroup();
            m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
            eg = el.getEntityGroup("WG");
            if (eg != null) {
                if (eg.getEntityItemCount() > 0) {
                    m_eiRoot = eg.getEntityItem(0);
                }
            }
            _db.test(m_eiRoot != null, "Work Group entity is null");

            checkPDGAttribute(_db, _prof, m_eiPDG);
            checkDataAvailability(_db, _prof, m_eiPDG);
            // validate data
            if (m_SBREx.getErrorCount() > 0) {
                m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
                throw m_SBREx;
            }
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
            m_utility.resetActivities();
            m_sbActivities = new StringBuffer();
            m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
           // strData = 
            	checkMissingData(_db, _prof, true).toString();
            m_sbActivities.append(m_utility.getActivities().toString());
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
        } catch (SBRException ex) {
			ex.printStackTrace();
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
            throw ex;
        } catch (Exception ex) {
			ex.printStackTrace();
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			m_SBREx.add(ex.toString());
			throw m_SBREx;
		}
    }

    /**
     * @return true if successful, false if nothing to update or unsuccessful
     * @param _db
     * @param _bExpire
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return true;
    }

    /**
     * (non-Javadoc)
     * checkDataAvailability
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkDataAvailability(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem)
     */
    protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        StringBuffer sb = new StringBuffer();
        // make sure the Base MODEL for the MTM already exists
        sb.append("map_COFCAT=101;");
        sb.append("map_COFSUBCAT=134;");
        sb.append("map_COFGRP=150;");
        sb.append("map_COFSUBGRP=010;");
        sb.append("map_MACHTYPEATR=" + m_strAfMachType + ";");
        sb.append("map_MODELATR=" + m_strAfModel);

        String strSai = (String) m_saiList.get("MODEL");
        EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());

        if (aeiCOM.length <= 0) {
            m_SBREx.add("The MODEL with classifications SW-Support-Base-N/A, MACHTYPE=" + m_strAfMachType + ", MODEL=" + m_strAfModel + " must be created before any MaintFeature MODEL.");
        } else if (aeiCOM.length > 1) {
            m_SBREx.add("There are " + aeiCOM.length + " existing Base MODEL for the same machinetype and model.");
        } else {
            m_eiList.put("BASE", aeiCOM[0]);
        }
    }
}
