//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: COPYCOMPMODELPDG.java,v $
// Revision 1.9  2009/10/21 12:55:53  wendy
// MN40935134 fixed search problem
//
// Revision 1.8  2008/09/04 20:56:14  wendy
// Cleanup some RSA warnings
//
// Revision 1.7  2008/01/25 19:30:32  bala
// restrict PDG to MODELS of type "Group=150" (System)
//
// Revision 1.6  2008/01/21 21:54:28  bala
// formatting
//
// Revision 1.5  2007/09/11 19:25:59  wendy
// MN32841099 WGMODEL changed to WGMODELA
//
// Revision 1.4  2006/03/03 00:17:44  joan
// fixes
//
// Revision 1.3  2006/02/28 00:47:20  joan
// work on pdg
//
// Revision 1.2  2006/02/26 01:49:58  joan
// fixes
//
// Revision 1.1  2006/02/23 18:44:36  joan
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.Vector;
import java.util.Hashtable;
import java.util.StringTokenizer;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.transactions.OPICMList;

/**
 * COPYCOMPMODELPDG
 *
 * @author David Bigelow
 * To REPLACE the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class COPYCOMPMODELPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;

    private String m_strFROMMACHTYPE = null;
    private String m_strFROMMODEL = null;
    private String m_strTOMACHTYPE = null;
    private String m_strTOMODEL = null;
    private EntityItem[] m_aeiFromModel = null;
    private EntityItem[] m_aeiToModel = null;

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
        return "$Id: COPYCOMPMODELPDG.java,v 1.9 2009/10/21 12:55:53 wendy Exp $";
    }

    /**
     * COPYCOMPMODELPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public COPYCOMPMODELPDG(EANMetaFoundation _mf, COPYCOMPMODELPDG _ai) throws MiddlewareRequestException {
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
    public COPYCOMPMODELPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException,
        MiddlewareRequestException {
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
        strbResult.append("COPYCOMPMODELPDG:" + getKey() + ":desc:" + getLongDescription());
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
        return "COPYCOMPMODELPDG";
    }

    private OPICMList getEntityItemList(EntityList _el, StringBuffer _sb, EntityItem _eiFrom, EntityItem _eiEnd) {
        String strTraceBase = "COPYCOMPMODELPDG getEntityItemList method ";

        OPICMList list = new OPICMList();
        D.ebug(D.EBUG_SPEW, strTraceBase + _eiFrom.getKey() + ":" + _eiEnd.getKey());
        /*
         0:0:E:MODEL:84638:MODEL:84638:Root
         1:0:R:MDLCGMDL:41:MODEL:84638:U
         2:0:E:MODELCG:33:MDLCGMDL:41:U
         3:1:R:MDLCGMDLCGOS:16:MODELCG:33:D
         4:1:E:MODELCGOS:16:MDLCGMDLCGOS:16:D
         5:2:R:MDLCGOSMDL:29:MODELCGOS:16:D
         6:2:E:MODEL:84646:MDLCGOSMDL:29:D
         5:2:R:MDLCGOSMDL:32:MODELCGOS:16:D
         6:2:E:MODEL:84639:MDLCGOSMDL:32:D
         5:2:R:MDLCGOSMDL:38:MODELCGOS:16:D
         6:2:E:MODEL:84644:MDLCGOSMDL:38:D
         */
        StringTokenizer st1 = new StringTokenizer(_sb.toString(), NEW_LINE);
        while (st1.hasMoreTokens()) {
            StringTokenizer st2 = new StringTokenizer(st1.nextToken(), ":");
            if (st2.hasMoreTokens()) {
                int iDepth = Integer.parseInt(st2.nextToken());
                int iLevel = Integer.parseInt(st2.nextToken());
                String strClass = st2.nextToken();
                String strET = st2.nextToken();
                int iEID = Integer.parseInt(st2.nextToken());
                String strPET = st2.nextToken();
                int iPEID = Integer.parseInt(st2.nextToken());
                String strDir = st2.nextToken();

                if (!strDir.equals("Root")) {
                    if (strET.equals(_eiEnd.getEntityType()) && (iEID == _eiEnd.getEntityID())) {
                        break;
                    }
                    EntityGroup eg = _el.getEntityGroup(strET);
                    EntityItem ei = eg.getEntityItem(strET + iEID);
                    if (ei != null) {
                        int i1 = list.indexOf(strET);
                        if (i1 >= 0) {
                            list.remove(strET);
                            m_eiList.remove(strET);
                        }
                        D.ebug(D.EBUG_SPEW, strTraceBase + " put in list : " + ei.getKey());
                        list.put(strET, ei);
                        m_eiList.put(strET, ei);
                    }
                }
            }
        }

        return list;
    }

    /**
     * (non-Javadoc)
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingData(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException,
        MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "COPYCOMPMODELPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();
        String strFileName = "PDGtemplates/COPYCOMPMODEL01.txt";
        Hashtable hshMap = m_PDGxai.generateVESteps(_db, _prof, "MODEL");

        for (int i = 0; i < m_aeiFromModel.length; i++) {
            EntityItem eiFrom = m_aeiFromModel[i];
            _db.debug(D.EBUG_SPEW, strTraceBase + " eiFrom " + eiFrom.getKey());

            EntityItem[] aeiFrom = {eiFrom};

            for (int j = 0; j < m_aeiToModel.length; j++) {
                EntityItem eiTo = m_aeiToModel[j];
                _db.debug(D.EBUG_SPEW, strTraceBase + " eiTo " + eiTo.getKey());
                EntityItem[] aeiTo = {eiTo};
                _prof = m_utility.setProfValOnEffOn(_db, _prof);

                EntityList elFromModel = EntityList.getEntityList(_db, _prof, m_PDGxai, aeiFrom);
                EntityList elToModel = EntityList.getEntityList(_db, _prof, m_PDGxai, aeiTo);
                eiFrom = elFromModel.getParentEntityGroup().getEntityItem(eiFrom.getKey());
                StringBuffer sbFromModel = EntityList.pull(eiFrom, 0, eiFrom, new Hashtable(), 0, "Root", hshMap);
                _db.debug(D.EBUG_SPEW, strTraceBase + " el From Model pull for eiFrom: " + eiFrom.getKey() + "\n" + sbFromModel.toString());

                eiTo = elToModel.getParentEntityGroup().getEntityItem(eiTo.getKey());
                StringBuffer sbToModel = EntityList.pull(eiTo, 0, eiTo, new Hashtable(), 0, "Root", hshMap);
                _db.debug(D.EBUG_SPEW, strTraceBase + " el To Model pull for eiTo: " + eiTo.getKey() + "\n" + sbToModel.toString());

                EntityGroup egFromModel = elFromModel.getEntityGroup("MODEL");
                EntityGroup egToModel = elToModel.getEntityGroup("MODEL");
                //System.out.println(strTraceBase + " egFromModel " + egFromModel.dump(false));
                //System.out.println(strTraceBase + " egToModel " + egToModel.dump(false));
                _db.debug(D.EBUG_SPEW, strTraceBase + " egFromModel count " +
                          egFromModel.getEntityItemCount() + " egToModel count " + egToModel.getEntityItemCount());

                for (int g = 0; g < egFromModel.getEntityItemCount(); g++) {
                    EntityItem eiCompModel = egFromModel.getEntityItem(g);

                    if (egToModel.getEntityItem(eiCompModel.getKey()) == null) {
                        _db.debug(D.EBUG_SPEW, strTraceBase + " need to create compatibility for frommodel " + eiCompModel.getKey() +
                                  " and tomodel " + eiTo.getKey());
                        m_eiList = new EANList();
                        OPICMList infoList = getEntityItemList(elFromModel, sbFromModel, eiFrom, eiCompModel);
                        infoList.put("FROMMODEL", eiTo);
                        infoList.put("TOMODEL", eiCompModel);
                        m_eiList.put("FROMMODEL", eiTo);
                        m_eiList.put("TOMODEL", eiCompModel);

                        _prof = m_utility.setProfValOnEffOn(_db, _prof);

                        //TestPDGII pdgObject = new TestPDGII(_db, _prof, eiTo, infoList, strFileName);
                        TestPDGII pdgObject = new TestPDGII(_db, _prof, eiTo, infoList, m_PDGxai, strFileName);
                        StringBuffer sbMissing = pdgObject.getMissingEntities();
                        sbReturn.append(sbMissing.toString());
                        _db.debug(D.EBUG_SPEW, strTraceBase + " getmissingEntities returned:" + sbMissing.toString());
                        if (_bGenData && sbMissing.toString().length() > 0) {
                        	//fixme this makes it work.. eiParent is not null 
                        	//m_eiRoot = eiCompModel;// major guessing here waiting on linda/rupal and spec
                            generateDataII(_db, _prof, sbMissing, "");
                        }

                    }
                    else {
                        _db.debug(D.EBUG_SPEW, strTraceBase + " found compatibility for tomodel " + eiCompModel.getKey());
                    }
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
    protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareRequestException,
        MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
        for (int i = 0; i < _pdgEI.getAttributeCount(); i++) {
            EANAttribute att = _pdgEI.getAttribute(i);
            String textAtt = "";
           // String sFlagAtt = "";
            //String sFlagClass = "";
            Vector mFlagAtt = new Vector();

            //int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ( (String) att.get()).trim();
            }
            else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
              //              sFlagAtt = amf[f].getLongDescription().trim();
                //            sFlagClass = amf[f].getFlagCode().trim();
                  //          index = f;
                            break;
                        }
                    }
                }
                else if (att instanceof MultiFlagAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            mFlagAtt.addElement(amf[f].getLongDescription().trim());
                        }
                    }
                }
            }

            if (att.getKey().equals("CCMFROMMACHTYPE")) {
                m_strFROMMACHTYPE = textAtt;
            }
            else if (att.getKey().equals("CCMFROMMODEL")) {
                m_strFROMMODEL = textAtt;
            }
            else if (att.getKey().equals("CCMTOMACHTYPE")) {
                m_strTOMACHTYPE = textAtt;
            }
            else if (att.getKey().equals("CCMTOMODEL")) {
                m_strTOMODEL = textAtt;
            }
        }

        if (m_strFROMMACHTYPE == null) {
            m_SBREx.add("FROMMACHTYPE is empty.");
        }

        if (m_strFROMMODEL == null) {
            m_SBREx.add("FROMMODEL is empty.");
        }

        if (m_strTOMACHTYPE == null) {
            m_SBREx.add("TOMACHTYPE is empty.");
        }

        if (m_strTOMODEL == null) {
            m_SBREx.add("TOMODEL is empty.");
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
    public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException,
        SBRException {

        ExtractActionItem eaItem = null;
        EntityItem[] eiParm = new EntityItem[1];
        EntityList el = null;
        EntityGroup eg = null;
        String s = null;

        String strTraceBase = " COPYCOMPMODELPDG viewMissingEntities method";

        _db.debug(D.EBUG_DETAIL, strTraceBase);
        m_SBREx = new SBRException();
        resetVariables();
        if (m_eiPDG == null) {
            s = "PDG entity is null";
            return s.getBytes();
        }
        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR7");
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
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException,
        SBRException {

        ExtractActionItem eaItem = null;
        EntityItem[] eiParm = new EntityItem[1];
        EntityList el = null;
        EntityGroup eg = null;

        String strTraceBase = " COPYCOMPMODELPDG executeAction method ";
        m_SBREx = new SBRException();
        String strData = "";
        resetVariables();
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                _db.debug(D.EBUG_ERR, "PDG entity is null");
                return;
            }

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

            eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR7");
            eiParm[0] = m_eiPDG;
            _prof = m_utility.setProfValOnEffOn(_db, _prof);
            el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
            eg = el.getParentEntityGroup();
            m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());

            checkPDGAttribute(_db, _prof, m_eiPDG);
            // validate data
            if (m_SBREx.getErrorCount() == 0) {
                checkDataAvailability(_db, _prof, m_eiPDG);
            }
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
        }
        catch (SBRException ex) {
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
    protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareException,
        MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "COPYCOMPMODELPDG checkDataAvailability method ";
        String[] aFlagCodes = null;
        //EntityItem[] aeiMACHTYPE = null;
        //EntityItem[] aeiMODEL = null;
        EntityItem[] aei = null;

        String strSai = null;

        //Vector vMACHTYPE = new Vector();
        Vector v = new Vector();
        StringBuffer sb = new StringBuffer();
        _db.debug(D.EBUG_SPEW, strTraceBase);
        // search for MODEL
        strSai = (String) m_saiList.get("MODEL");
        aFlagCodes = m_utility.getFlagCodeForLikedDesc(_db, _prof, "MACHTYPEATR", m_strFROMMACHTYPE.replace('?', '_'));

        for (int i = 0; i < aFlagCodes.length; i++) {
            String strFlagCode = aFlagCodes[i];
            sb = new StringBuffer();
            sb.append("map_MACHTYPEATR=" + strFlagCode + ";");
            sb.append("map_MODELATR=" + m_strFROMMODEL.replace('?', '_')+ ";");
            //MN 40935134
           // cant do it this way because this is a dependent choice flag sb.append("map_COFGRP=150;");
            //search was failing because cofgrp was getting appended to modelatr, ';' was missing
            // when ';' was added, flag lookup failed because COFGRP is dependent choice

            aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
            if (aei != null && aei.length > 0) {
                for (int j = 0; j < aei.length; j++) {
                	// filter on COFGRP here
                	EntityItem mdlitem = aei[j];
                	EANFlagAttribute attr = (EANFlagAttribute)mdlitem.getAttribute("COFGRP");
                	_db.debug(D.EBUG_SPEW,strTraceBase+" checking FROM "+mdlitem.getKey()+" for COFGRP=150 attr: "+attr);
                	if(attr.isSelected("150")){
                		v.addElement(mdlitem);
                	}
                }
            }
        }
 
        if (v.size()== 0) {
            m_SBREx.add("There are no FROM MODELs for MACHTYPEATR=" + m_strFROMMACHTYPE + 
            		" and MODELATR=" + m_strFROMMODEL + " and COFGPR=150.");
        }else{
            m_aeiFromModel = new EntityItem[v.size()];
            v.copyInto(m_aeiFromModel);
        }

        // search for supported TOMODEL

        aFlagCodes = m_utility.getFlagCodeForLikedDesc(_db, _prof, "MACHTYPEATR", m_strTOMACHTYPE.replace('?', '_'));
        v = new Vector();
        for (int i = 0; i < aFlagCodes.length; i++) {
            String strFlagCode = aFlagCodes[i];
            sb = new StringBuffer();
            sb.append("map_MACHTYPEATR=" + strFlagCode + ";");
            sb.append("map_MODELATR=" + m_strTOMODEL.replace('?', '_')+ ";");
            //MN 40935134
            // cant do it this way because this is a dependent choice flag sb.append("map_COFGRP=150;");

            aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
            if (aei != null && aei.length > 0) {
                for (int j = 0; j < aei.length; j++) {
                  	// filter on COFGRP here
                	EntityItem mdlitem = aei[j];
                	EANFlagAttribute attr = (EANFlagAttribute)mdlitem.getAttribute("COFGRP");
                	_db.debug(D.EBUG_SPEW,strTraceBase+" checking TO "+mdlitem.getKey()+" for COFGRP=150 attr: "+attr);
                	
                	if(attr.isSelected("150")){
                		 v.addElement(mdlitem);
                	}
                }
            }
        }

        if (v.size()== 0) {
            m_SBREx.add("There are no TO MODELs for MACHTYPEATR=" + m_strTOMACHTYPE + 
            		" and MODELATR=" + m_strTOMODEL +" and COFGPR=150.");
        }else{
            m_aeiToModel = new EntityItem[v.size()];
            v.copyInto(m_aeiToModel);
        }
    }
}
