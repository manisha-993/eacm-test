// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: FMRPRODMAINTPDG.java,v $
// Revision 1.32  2008/01/31 16:48:16  wendy
// Updated for RQ1113074441 and RQ1116076636
//
// Revision 1.31  2007/09/17 12:43:06  couto
// MN32841099 WGFEATURE replaced by WGFEATUREA
//
// Revision 1.30  2005/06/15 20:58:49  joan
// fixes
//
// Revision 1.29  2005/03/14 22:00:07  joan
// fix null pointer
//
// Revision 1.28  2005/03/03 21:12:25  dave
// Jtest cleanup
//
// Revision 1.27  2005/02/15 00:48:17  joan
// fixes
//
// Revision 1.26  2005/02/14 15:52:14  joan
// fixes
//
// Revision 1.25  2005/02/11 20:24:24  joan
// fixes
//
// Revision 1.24  2005/02/08 20:48:32  joan
// fixes
//
// Revision 1.23  2005/02/08 18:30:30  joan
// CR5110
//
// Revision 1.22  2005/01/20 21:04:10  joan
// working on msg
//
// Revision 1.21  2005/01/18 00:34:20  bala
// html comment out entityKey content  in table
//
// Revision 1.20  2004/11/29 21:38:24  joan
// check for machinetype and model
//
// Revision 1.19  2004/11/28 14:30:40  bala
// change null check logic
//
// Revision 1.18  2004/11/27 22:28:16  bala
// fix nullpointer @removePRODSTRUCT
//
// Revision 1.17  2004/11/22 18:37:39  joan
// fixes
//
// Revision 1.16  2004/11/20 00:08:49  joan
// fixes
//
// Revision 1.15  2004/11/19 22:49:10  joan
// fixes
//
// Revision 1.14  2004/11/01 22:32:24  joan
// fixes
//
// Revision 1.13  2004/10/29 16:24:06  joan
// fixes
//
// Revision 1.12  2004/10/25 17:23:00  joan
// work on create parent
//
// Revision 1.11  2004/10/22 17:37:09  joan
// change attr
//
// Revision 1.10  2004/10/22 17:17:53  joan
// adjust code
//
// Revision 1.9  2004/10/22 17:07:05  joan
// change attr
//
// Revision 1.8  2004/09/14 23:40:35  joan
// fixes
//
// Revision 1.7  2004/09/02 21:31:18  joan
// fixes
//
// Revision 1.6  2004/09/02 21:29:16  joan
// debug
//
// Revision 1.5  2004/08/25 19:44:50  joan
// add new pdgs
//
// Revision 1.4  2004/08/13 21:32:23  joan
// fix error
//
// Revision 1.3  2004/08/12 21:13:55  joan
// fix bug
//
// Revision 1.2  2004/08/12 18:03:03  joan
// work on PDG
//
// Revision 1.1  2004/08/11 18:19:27  joan
// add new pdg
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.*;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.OPICMList;

/**
 * FMRPRODMAINTPDG
 * MODELs are limited to those with a Model Group (COFGRP) equal to Base (150) and is based on
 * the Operation Request Type. The applicable dialog input is shown along with the function required.
 * All input consists of zero to n characters and limited to the length of the comparable attribute.
 * Also, a '?' is an acceptable character and is considered to be a 'wild card' (i.e. a '?' will match
 * any single character).
 *
 * For example, a FC of '2' would match all FEATUREs with FC that starts with a '2' (e.g. 2123 and 2225).
 *
 * Another example using a 'wild card', a FC of '?2' will match all Features that have a '2' as the second
 * character of the Feature Code.
 *
 * IG is required and normally has a WORKGROUP default (use entity type FEATURE). IG is always used to
 * limit MACHTYPE (MT) and FEATURE (FC and FC2).
 *
 * When checking / matching, the following data model structure is used:
 *  MACHTYPE
 *  MACHTYPEATR
 *  INVENTORYGROUP
 *
 *  MODEL
 *  MACHTYPEATR
 *  MODELATR
 *  COFGRP = 150 (Base)
 *
 *  PRODSTRUCT
 *
 *  FEATURE
 *  FEATURECODE
 *  INVENTORYGROUP
 *
 * where:
 * -    there is an association from MACHTYPE.MACHTYPEATR to MODEL.MACHTYPEATR.
 * -    PRODSTRUCT is a relator from FEATURE to MODEL
 *
 * All operations require COMMENTS. If a COMMENTS is supplied, it will be added to the COMMENTS already
 * in PRODSTRUCT but separated by a line feed (i.e. a new paragraph is started). If a PRODSTRUCT is being
 * deleted, the COMMENTS is updated prior to the delete.
 *
 */
public class FMRPRODMAINTPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;

    private String m_strOP = null; // operation to perform
    private String m_strIG = null; // inventory group flag code from pdg or from wg.feature default
    private String m_strIGdesc = null; // inventory group flag desc
    private String m_strMT = null;
    private String mtFlagCodes[] = null; // array of MACHTYPEATR that have matching inventorygroup
    private String m_strMODEL = null;
    private String m_strFC1 = null;
    private String m_strFC2 = null;
    private String m_strComments = null;

    private static final String ADD = "OP01";
    private static final String REMOVE = "OP02";
    private static final String CHANGE = "OP03";
    private static final String ADDMATCH = "OP04";
    private static final String REMOVEMATCH = "OP05";
    private static final String COMMENT = "OP06";
    private static final String UPDATEFCDATES = "OP07";
    private static final String UPDATEWDATE = "OP08";
    private EntityItem defPsItem = null; // prodstruct entity used to populate all others
    private Vector m_modelVct = null; // models matching modelatr and machtypeatr for invgrp and cofgrp=base for ADD

    /**
     * getVersion
     *
     * @return String
     */
    public String getVersion() {
        return "$Id: FMRPRODMAINTPDG.java,v 1.32 2008/01/31 16:48:16 wendy Exp $";
    }

    /**
     * FMRPRODMAINTPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public FMRPRODMAINTPDG(EANMetaFoundation _mf, FMRPRODMAINTPDG _ai) throws MiddlewareRequestException {
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
    public FMRPRODMAINTPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _strActionItemKey);
    }

    /**
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("FMRPRODMAINTPDG:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "/n");
        return strbResult.toString();
    }

    /**
     * getPurpose
     *
     * @return String
     */
    public String getPurpose() {
        return "FMRPRODMAINTPDG";
    }

    /**
     * viewMissing
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#viewMissing(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public byte[] viewMissing(Database _db, Profile _prof)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String s = null;
        String strTraceBase = " FMRPRODMAINTPDG viewMissing method";

        _db.debug(D.EBUG_DETAIL, strTraceBase);
        if (m_eiPDG == null) {
            s = "PDG entity is null";
            return s.getBytes();
        }
    //long lStart = System.currentTimeMillis();

        checkDataAvailability(_db,_prof,m_eiPDG); // get the attr for the PDG, determine IG and find operation type
        if (m_SBREx.getErrorCount() > 0) {
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR,
                m_SBREx.toString(), getLongDescription());
            throw m_SBREx;
        }

        m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");

        s = checkMissingData(_db, _prof, false).toString();
    //long lFinish = System.currentTimeMillis();
    //long longDuration = lFinish - lStart;
    //_db.debug(D.EBUG_INFO, strTraceBase+" timing " + Stopwatch.format(longDuration));

        if (m_SBREx.getErrorCount() > 0) {
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
            throw m_SBREx;
        }

        if (s.length() == 0) {
            s = "Checking data is complete";
        }

        m_sbActivities.append(m_utility.getViewXMLString(s));
        m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
        m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());

        return s.getBytes();

    }

    /**
     * executeAction
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#executeAction(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public void executeAction(Database _db, Profile _prof)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String strTraceBase = " FMRPRODMAINTPDG executeAction method ";
        String strData = "";
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                System.out.println("PDG entity is null");
                return;
            }
    //long lStart = System.currentTimeMillis();

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

            checkDataAvailability(_db,_prof,m_eiPDG); // get the attr for the PDG, determine IG and find operation type
            if (m_SBREx.getErrorCount() > 0) {
                m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
                throw m_SBREx;
            }

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
            m_utility.resetActivities();
            m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
            strData = checkMissingData(_db, _prof, true).toString();

    //long lFinish = System.currentTimeMillis();
    //long longDuration = lFinish - lStart;
    //_db.debug(D.EBUG_INFO, strTraceBase+" timing " + Stopwatch.format(longDuration));

            if (m_SBREx.getErrorCount() > 0) {
                m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
                throw m_SBREx;
            }

            m_sbActivities.append(m_utility.getActivities().toString());
            if (strData.length() == 0) {
                // this doesnt make sense, only adds would return a msg
                //addMessage("No changes made during this run.");
            }
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
        } catch (SBRException ex) {
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
            throw ex;
        }
    }

    /**
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingData(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        StringBuffer sbReturn = new StringBuffer();

        try{
            if (m_strOP.equals(ADD)){
                // get PRODSTRUCT defaults from WG
                // search for FEATUREs using FC1 and invGrp
                // search for MODELs using COFGRP, use MACHTYPE and check invGrp
                // create PRODSTRUCTs
                sbReturn.append(add(_db, _prof,_bGenData));
            }else if(m_strOP.equals(REMOVE)){
                // search for PRODSTRUCTs using FC1 and FEATURE invgrp and MODEL COFGRP
                // add COMMENTS to each PRODSTRUCT and remove
                remove(_db, _prof, _bGenData);
            }else if(m_strOP.equals(CHANGE)){
                // search for PRODSTRUCTs using FC1 and FEATURE invgrp and MODEL COFGRP
                // add COMMENTS to each PRODSTRUCT and remove FC1
                // then do ADD using FC2
                sbReturn.append(change(_db, _prof,_bGenData));
            }else if(m_strOP.equals(ADDMATCH)){
                // search for PRODSTRUCTs using FC1 and FEATURE invgrp and MODEL COFGRP
                // then do ADD using FC2
                sbReturn.append(addMatching(_db, _prof, _bGenData));
            }else if(m_strOP.equals(REMOVEMATCH)){
                // search for PRODSTRUCTs using FC1 and FEATURE invgrp and MODEL COFGRP
                // search for PRODSTRUCTs using FC2 and FEATURE invgrp and MODEL COFGRP
                // add COMMENTS to each PRODSTRUCT and remove FC2
                removeMatching(_db, _prof, _bGenData);
            }else if(m_strOP.equals(COMMENT)){
                // search for PRODSTRUCTs using FC1 and FEATURE invgrp and MODEL COFGRP
                // add COMMENTS to each PRODSTRUCT
                addComment(_db, _prof,_bGenData);
            }else if(m_strOP.equals(UPDATEFCDATES)){
                // search for FEATUREs using FCLIST and invgrp
                // update FEATURE dates
                updateFeatureDates(_db, _prof,_bGenData);
            }else if(m_strOP.equals(UPDATEWDATE)){
                // search for FEATUREs using FC1 and invgrp
                // update FEATURE wddate
                withdrawFeature(_db, _prof,_bGenData);
            }
        }catch(LockException le){
            throw new MiddlewareException(le.getMessage());
        }catch(COM.ibm.eannounce.objects.EANBusinessRuleException bre){
            throw new MiddlewareException(bre.getMessage());
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
    }

    private void addDebug(String msg){
        m_sbActivities.append("<DEBUG>"+msg+"</DEBUG>"+ NEW_LINE);
    }
    private void addMessage(String msg){
        m_sbActivities.append("<MSG>"+msg+"</MSG>"+ NEW_LINE);
    }

    /**
     *
     * checkDataAvailability - init pdg
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkDataAvailability(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem)
     */
    protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        m_SBREx = new SBRException(); // start with an empty set of errors
        resetVariables();
        m_sbActivities = new StringBuffer();

        // populate the PDG with valid attributes, current one is empty
        // this works but getting the xml later expects an entitylist
        //EntityGroup eg = new EntityGroup(null, _db, _prof, m_eiPDG.getEntityType(), "Edit", false);
        //m_eiPDG = new EntityItem(eg, _prof, _db, m_eiPDG.getEntityType(), m_eiPDG.getEntityID());
        ExtractActionItem exItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR1");
        EntityList el = EntityList.getEntityList(_db, _prof, exItem, new EntityItem[]{m_eiPDG});
        m_eiPDG = el.getParentEntityGroup().getEntityItem(0);

        // find the inventorygroup to use
        setInventoryGroup(_db, _prof);
        if (m_strIG == null){
            m_SBREx.add("InventoryGroup could not be determined.");
            return;
        }
        // get the operation needed
        EANAttribute att = m_eiPDG.getAttribute("PDGOPER01");
        if (att !=null && att.toString().length()>0){
            MetaFlag[] amf = (MetaFlag[]) att.get();
            for (int f = 0; f < amf.length; f++) {
                if (amf[f].isSelected()) {
                    m_strOP = amf[f].getFlagCode().trim();
                    addDebug("Operation for "+m_eiPDG.getKey()+" is "+amf[f].getLongDescription());
                    break;
                }
            }
        }
        if (m_strOP == null){
            m_SBREx.add("Operation could not be determined.");
            return;
        }

        att = m_eiPDG.getAttribute("COMMENTS");
        if (att !=null && att.toString().length()>0){
            m_strComments = ((String) att.get()).trim();
        }

        att = m_eiPDG.getAttribute("PDGFC1");
        if (att !=null && att.toString().length()>0){
            m_strFC1 = ((String) att.get()).trim();
        }

        att = m_eiPDG.getAttribute("PDGFC2");
        if (att !=null && att.toString().length()>0){
            m_strFC2 = ((String) att.get()).trim();
        }

        // everything but update feature dates need model and machtype
        if (m_strOP.equals(UPDATEFCDATES) || m_strOP.equals(UPDATEWDATE)){
        }else{
            att = m_eiPDG.getAttribute("AFMACHTYPEATR");
            if (att !=null && att.toString().length()>0) {
                m_strMT = ((String) att.get()).trim();
                // cant use wildcard in flag search so find each one
                String flagCodes[]  = m_utility.getFlagCodeForLikedDesc(_db, _prof, "MACHTYPEATR",
                    m_strMT.replace('?', '_'));
                // check for IG on these machtype
                if (flagCodes != null && flagCodes.length>0){
                    Vector flagsVct = new Vector(1);
                    for (int i = 0; i < flagCodes.length; i++) {
                        StringBuffer sb = new StringBuffer("map_MACHTYPEATR=" + flagCodes[i] + ";");
                        sb.append("map_INVENTORYGROUP=" + m_strIG);
                        EntityItem aei[] = m_utility.dynaSearch(_db, _prof, m_eiPDG,
                            (String) m_saiList.get("MACHTYPE"),"MACHTYPE", sb.toString());
                        if (aei != null && aei.length > 0) {
                            flagsVct.addElement(flagCodes[i]);
                        }else{
                            addDebug("No MACHTYPE found for MACHTYPEATR="+flagCodes[i]+
                                " and INVENTORYGROUP=" + m_strIGdesc);
                        }
                    }
                    if (flagsVct.size()>0){
                        mtFlagCodes = new String[flagsVct.size()];
                        flagsVct.copyInto(mtFlagCodes);
                    }else{ // no MACHTYPE found for this string
                        m_SBREx.add("No Machine Type Entities found for MACHTYPEATR="+m_strMT+
                            " and INVENTORYGROUP=" + m_strIGdesc);
                        return;
                    }
                }else{ // no MACHTYPE found for this string
                    m_SBREx.add("No Machine Type Entities found for "+m_strMT);
                    return;
                }
            }else{
                m_SBREx.add("Machine Type is empty.");
                return;
            }

            att = m_eiPDG.getAttribute("AFMODELATR");
            if (att !=null && att.toString().length()>0) {
                m_strMODEL = ((String) att.get()).trim();
            }else {
                m_SBREx.add("Model is empty.");
                return;
            }
            if (m_strOP.equals(ADD) || m_strOP.equals(ADDMATCH) || m_strOP.equals(CHANGE)){ // only add prodstruct need this
                addPDGvaluesToDefaultProdstruct(_db,_prof);
            }
            if (m_strOP.equals(ADD)){ // need to find MODELs for ADD
                m_modelVct = new Vector(1);
                for (int i = 0; i < mtFlagCodes.length; i++) {
                    String strFlagCode = mtFlagCodes[i];
                    StringBuffer sb = new StringBuffer("map_MACHTYPEATR=" + strFlagCode + ";");
                    sb.append("map_MODELATR=" + m_strMODEL.replace('?', '_'));

                    EntityItem aei[] = m_utility.dynaSearch(_db, _prof, m_eiPDG, (String) m_saiList.get("MODEL"), "MODEL", sb.toString());
                    if (aei != null && aei.length > 0) {
                        for (int j = 0; j < aei.length; j++) {
                            EntityItem mdlItem = (EntityItem)aei[j];
                            String cofgrp = m_utility.getAttrValue(mdlItem, "COFGRP");
                            if (cofgrp.equals("150")){
                                m_modelVct.addElement(mdlItem);
                            }else{
                                addDebug("Skipping "+mdlItem.getKey()+" '"+cofgrp+"' is not Base");
                            }
                        }
                    }
                }

                if (m_modelVct.size()==0) {
                    m_SBREx.add("There are no MODELs for MACHTYPEATR=" + m_strMT + " and MODELATR=" + m_strMODEL + ".");
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

    /***************************************
    * 1.    Add
    *
    * IG
    * MT
    * MODEL
    * FC
    *
    * PRODSTRUCT attributes need to be collected in order to create the PRODSTRUCT relators.
    * The defaults should be from the PDH Metadata along with the Work Group defaults for
    * this entity type.
    *
    * The minimum set is:
    *   Original Order Code (ORDERCODE)
    *   Domains (PDHDOMAIN)
    * Optional attributes are:
    *   Announcement Date (ANNDATE)
    *   Compatibility (COMPATIBILITY)
    *   Customer Responsibilities (CUSTRESP)
    *   FRU Handling (FRUHANDLING)
    *   General Availability Date (GENAVAILDATE)
    *   Initial Order Maximum (INITORDERMAX)
    *   Customer Setup (INSTALL)
    *   Installability (INSTALLABILITY)
    *   Price File Name (INVNAME)
    *   Marketing Name (MKTGNAME)
    *   OS Level (OSLEVEL)
    *   OS Level Complement (OSLEVELCOMPLEMENT)
    *   Recommended Stocking Quantity (RECOMMSTOCKQUANT)
    *   Reference Documentation (REFDOC)
    *   Removal Price Support (REMOVEPRICE)
    *   Returned Parts MES (RETURNEDPARTS)
    *   Revenue Part Number (REVENUEPN)
    *   Maximum Allowed (SYSTEMMAX)
    *   Minimum Required (SYSTEMMIN)
    *   Withdraw Date (WITHDRAWDATE)
    *   Withdrawal Effective Date (WTHDRWEFFCTVDATE)
    *   Restricted (RESTRICTED)
    *
    * Obtain a list of matching FEATUREs based on FC.
    * Obtain a list of matching MODELs based on MT and MODEL.
    *
    * Create a PRODSTRUCT relator for every FEATURE MODEL pair.
    *
    */
    private String add(Database _db, Profile _prof, boolean _bGenData)
    throws SQLException, MiddlewareException,SBRException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        StringBuffer sb = new StringBuffer();
        if (m_strFC1 ==null){
            m_SBREx.add("Feature Code 1 is required for Add.");
            return sb.toString();
        }

        // create based on fc1
        EntityItem[] aeiFC = findFeatures(_db, _prof, m_strFC1);
        if (aeiFC != null && aeiFC.length > 0) {
            Vector fcVct = new Vector(1);

            // create prodstruct for each fc and model
            for (int ifc = 0; ifc < aeiFC.length; ifc++) {
                fcVct.addElement(aeiFC[ifc]);
            }

            sb.append(addPRODSTRUCT(_db, _prof, fcVct, m_modelVct, _bGenData));
            fcVct.clear();
        }

        return sb.toString();
    }

    /***************************************
    * create prodstruct between these sets of entities
    */
    private String addPRODSTRUCT(Database _db, Profile _prof, Vector _FCList,
        Vector _MODELList, boolean _bGenData)
        throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        StringBuffer sbReturn = new StringBuffer();

        for (int i = 0; i < _FCList.size(); i++) {
            EntityItem eiFC = (EntityItem) _FCList.elementAt(i);
            for (int j = 0; j < _MODELList.size(); j++) {
                EntityItem eiMODEL = (EntityItem) _MODELList.elementAt(j);
                sbReturn.append(addPRODSTRUCT(_db, _prof, eiFC, eiMODEL, _bGenData));
            }
        }
        return sbReturn.toString();
    }

    /***************************************
    * create prodstruct between these entities
    */
    private String addPRODSTRUCT(Database _db, Profile _prof, EntityItem eiFC,
        EntityItem eiMODEL, boolean _bGenData)
        throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        TestPDG pdgObject = null;
        StringBuffer sbMissing = null;

        OPICMList infoList = new OPICMList();
        infoList.put("PDG", defPsItem);
        infoList.put("FT", eiFC);
        infoList.put("MODEL", eiMODEL);

        pdgObject = new TestPDG(_db, _prof, eiFC, infoList, "PDGtemplates/FRMPRODMAINOP01.txt" );
        sbMissing = pdgObject.getMissingEntities();

        pdgObject = null;
        infoList = null;
        m_eiList = new EANList();
        m_eiList.put(eiFC);
        m_eiList.put(eiMODEL);
        addDebug("Adding prodstruct between "+eiFC.getKey() +" and "+eiMODEL.getKey());

        if (_bGenData) {
            m_bGetParentEIAttrs = true;
            // code will not create duplicates, so dont need to check for them first
            generateData(_db, _prof, sbMissing, "");
        }else{
            addMessage("Would create prodstruct between " + eiFC.getKey() +
                " and "+eiMODEL.getKey());
        }

        return sbMissing.toString();
    }

    /***************************************
    * 2.    Remove
    *
    * IG - Inventory Group (INVENTORYGROUP)
    * MT - Machine Type (AFMACHTYPEATR)
    * MODEL - Model (AFMODELATR)
    * FC - Feature Code 1 (PDGFC1)
    *
    * Remove the matching PRODSTRUCT based on MT, MODEL and FC.
    */
    private void remove(Database _db, Profile _prof, boolean _bGenData)
    throws
            COM.ibm.opicmpdh.middleware.LockException,
            COM.ibm.opicmpdh.middleware.MiddlewareException,
            COM.ibm.eannounce.objects.EANBusinessRuleException,
            COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
            java.sql.SQLException, SBRException
    {
        if (m_strFC1 ==null){
            m_SBREx.add("Feature Code 1 is required for Remove.");
            return;
        }
        // search for PRODSTRUCTs using FC1 and FEATURE invgrp and model COFGRP
        Vector listVct = findProdstructs(_db, _prof, m_strFC1);

        if (listVct.size() == 0) {
            m_SBREx.add("There are no PRODSTRUCT that have FEATURECODE matching " + m_strFC1 +
                ", INVENTORYGROUP=" + m_strIGdesc + ", Base MODEL="+m_strMODEL+
                "and MACHTYPE="+m_strMT+".");
        }else{
            Vector psVct = new Vector(1);
            for (int ii=0; ii<listVct.size(); ii++){
                EntityList list = (EntityList)listVct.elementAt(ii);
                EntityGroup eg = list.getEntityGroup("PRODSTRUCT");
                for (int i = 0; i < eg.getEntityItemCount(); i++) {
                    EntityItem ei = eg.getEntityItem(i);
                    psVct.addElement(ei);
                }
            }

            removeProdstructs(_db, _prof, psVct, _bGenData);

            psVct.clear();

            for (int ii=0; ii<listVct.size(); ii++){
                EntityList list = (EntityList)listVct.elementAt(ii);
                list.dereference();
            }

            listVct.clear();
        }
    }

    /***************************************
    * 3.    Change
    *
    * IG - Inventory Group (INVENTORYGROUP)
    * MT - Machine Type (AFMACHTYPEATR)
    * MODEL - Model (AFMODELATR)
    * FC - Feature Code 1 (PDGFC1)
    * FC2 - Feature Code 2 (PDGFC2)
    *
    * Remove the matching PRODSTRUCT based on MT, MODEL and FC. Every PRODSTRUCT that is
    * removed is 'replaced' by the creation of a PRODSTRUCT relator based on MODEL and FC2.
    * For PRODSTRUCT attributes, see the earlier section on 'Add'.
    */
    private String change(Database _db, Profile _prof, boolean _bGenData)
    throws
            COM.ibm.opicmpdh.middleware.LockException,
            COM.ibm.opicmpdh.middleware.MiddlewareException,
            COM.ibm.eannounce.objects.EANBusinessRuleException,
            COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
            java.sql.SQLException, SBRException
    {
        StringBuffer sb = new StringBuffer();
        if (m_strFC1 ==null){
            m_SBREx.add("Feature Code 1 is required for Change.");
            return sb.toString();
        }
        if (m_strFC2 ==null){
            m_SBREx.add("Feature Code 2 is required for Change.");
            return sb.toString();
        }

        // search for PRODSTRUCTs using FC1 and FEATURE invgrp and model COFGRP
        Vector listVct1 = findProdstructs(_db, _prof, m_strFC1);

        if (listVct1.size() == 0) {
            m_SBREx.add("There are no PRODSTRUCT that have FEATURECODE matching " + m_strFC1 +
                ", INVENTORYGROUP=" + m_strIGdesc + ", Base MODEL="+m_strMODEL+
                "and MACHTYPE="+m_strMT+".");
            return sb.toString();
        }

        // get keys to all model for FC1
        Vector mdlVct = new Vector();
        Vector psVct = new Vector(1);
        for (int ii=0; ii<listVct1.size(); ii++){
            EntityList list = (EntityList)listVct1.elementAt(ii);
            EntityGroup eg = list.getEntityGroup("PRODSTRUCT");
            for (int i = 0; i < eg.getEntityItemCount(); i++) {
                EntityItem psItem = eg.getEntityItem(i);
                EntityItem mdlItem = (EntityItem)psItem.getDownLink(0);
                addDebug("change found "+psItem.getKey()+", "+mdlItem.getKey()+" and "+
                    psItem.getUpLink(0).getKey()+" for "+m_strFC1);
                //String mtm = m_utility.getAttrValue(mdlItem, "MACHTYPEATR")+
                //  m_utility.getAttrValue(mdlItem, "MODELATR");
                mdlVct.addElement(mdlItem);
                psVct.addElement(psItem);
            }
        }

        removeProdstructs(_db, _prof, psVct, _bGenData);
        psVct.clear();

        // create based on fc2
        EntityItem[] aeiFC = findFeatures(_db, _prof, m_strFC2);
        if (aeiFC != null && aeiFC.length > 0) {
            Vector fcVct = new Vector(1);

            // create prodstruct for each fc2 and model
            for (int ifc = 0; ifc < aeiFC.length; ifc++) {
                fcVct.addElement(aeiFC[ifc]);
            }

            sb.append(addPRODSTRUCT(_db, _prof, fcVct, mdlVct, _bGenData));
            fcVct.clear();
        }

        for (int ii=0; ii<listVct1.size(); ii++){
            EntityList list = (EntityList)listVct1.elementAt(ii);
            list.dereference();
        }
        listVct1.clear();
        mdlVct.clear();

        return sb.toString();
    }

    /***************************************
    * 4.    Add Matching
    *
    * IG - Inventory Group (INVENTORYGROUP)
    * MT - Machine Type (AFMACHTYPEATR)
    * MODEL - Model (AFMODELATR)
    * FC - Feature Code 1 (PDGFC1)
    * FC2 - Feature Code 2 (PDGFC2)
    *
    * For every matching PRODSTRUCT based on MT, MODEL, and FC; create a PRODSTRUCT relator
    * based on MT, MODEL, and FC2. For PRODSTRUCT attributes, see the earlier section on 'Add'
    */
    private String addMatching(Database _db, Profile _prof, boolean _bGenData)
    throws SQLException, MiddlewareException,SBRException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        StringBuffer sb = new StringBuffer();
        if (m_strFC1 ==null){
            m_SBREx.add("Feature Code 1 is required for Add Matching.");
            return sb.toString();
        }
        if (m_strFC2 ==null){
            m_SBREx.add("Feature Code 2 is required for Add Matching.");
            return sb.toString();
        }

        // search for PRODSTRUCTs using FC1 and FEATURE invgrp and model COFGRP
        Vector listVct1 = findProdstructs(_db, _prof, m_strFC1);

        if (listVct1.size() == 0) {
            m_SBREx.add("There are no PRODSTRUCT that have FEATURECODE matching " + m_strFC1 +
                ", INVENTORYGROUP=" + m_strIGdesc + ", Base MODEL="+m_strMODEL+
                "and MACHTYPE="+m_strMT+".");
            return sb.toString();
        }

        // create based on fc2
        // find FC2 features, use MTM from FC1
        EntityItem[] aeiFC = findFeatures(_db, _prof, m_strFC2);
        if (aeiFC != null && aeiFC.length > 0) {
            // get set of models
            for (int ii=0; ii<listVct1.size(); ii++){
                EntityList list = (EntityList)listVct1.elementAt(ii);
                EntityGroup eg = list.getEntityGroup("PRODSTRUCT");
                for (int i = 0; i < eg.getEntityItemCount(); i++) {
                    EntityItem psItem = eg.getEntityItem(i);
                    EntityItem mdlItem = (EntityItem)psItem.getDownLink(0);

                    // create prodstruct for each fc and model
                    for (int ifc = 0; ifc < aeiFC.length; ifc++) {
                        EntityItem ei = (EntityItem) aeiFC[ifc];
                        sb.append(addPRODSTRUCT(_db, _prof, ei, mdlItem, _bGenData));
                    }
                }
            }
        }

        for (int ii=0; ii<listVct1.size(); ii++){
            EntityList list = (EntityList)listVct1.elementAt(ii);
            list.dereference();
        }
        listVct1.clear();

        return sb.toString();
    }

    /***************************************
    * 5.    Remove Matching
    *
    * IG - Inventory Group (INVENTORYGROUP)
    * MT - Machine Type (AFMACHTYPEATR)
    * MODEL - Model (AFMODELATR)
    * FC - Feature Code 1 (PDGFC1)
    * FC2 - Feature Code 2 (PDGFC2)
    *
    * For every MT, MODEL that has a PRODSTRUCT based on FC1 and has a PRODSTRUCT based on FC2,
    * remove the PRODSTRUCT based on FC2.
    */
    private void removeMatching(Database _db, Profile _prof, boolean _bGenData)
    throws
            COM.ibm.opicmpdh.middleware.LockException,
            COM.ibm.opicmpdh.middleware.MiddlewareException,
            COM.ibm.eannounce.objects.EANBusinessRuleException,
            COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
            java.sql.SQLException, SBRException
    {
        if (m_strFC1 ==null){
            m_SBREx.add("Feature Code 1 is required for Remove Matching.");
            return;
        }
        if (m_strFC2 ==null){
            m_SBREx.add("Feature Code 2 is required for Remove Matching.");
            return;
        }

        // search for PRODSTRUCTs using FC1 and FEATURE invgrp and model COFGRP
        Vector listVct1 = findProdstructs(_db, _prof, m_strFC1);

        if (listVct1.size() == 0) {
            m_SBREx.add("There are no PRODSTRUCT that have FEATURECODE matching " + m_strFC1 +
                ", INVENTORYGROUP=" + m_strIGdesc + ", Base MODEL="+m_strMODEL+
                "and MACHTYPE="+m_strMT+".");
            return;
        }

        // search for PRODSTRUCTs using FC2 and FEATURE invgrp and model COFGRP
        Vector listVct2 = findProdstructs(_db, _prof, m_strFC2);

        if (listVct2.size() == 0) {
            m_SBREx.add("There are no PRODSTRUCT that have FEATURECODE matching " + m_strFC2 +
                ", INVENTORYGROUP=" + m_strIGdesc + ", Base MODEL="+m_strMODEL+
                "and MACHTYPE="+m_strMT+".");

            for (int ii=0; ii<listVct1.size(); ii++){
                EntityList list = (EntityList)listVct1.elementAt(ii);
                list.dereference();
            }

            listVct1.clear();
            return;
        }

        // get keys to all model features for FC1
        Hashtable fc1Tbl = new Hashtable();
        for (int ii=0; ii<listVct1.size(); ii++){
            EntityList list = (EntityList)listVct1.elementAt(ii);
            EntityGroup eg = list.getEntityGroup("PRODSTRUCT");
            for (int i = 0; i < eg.getEntityItemCount(); i++) {
                EntityItem psItem = eg.getEntityItem(i);
                EntityItem mdlItem = (EntityItem)psItem.getDownLink(0);
                addDebug("removeMatching found "+psItem.getKey()+", "+mdlItem.getKey()+" and "+
                    psItem.getUpLink(0).getKey()+" for FC1 "+m_strFC1);
                String mtm = m_utility.getAttrValue(mdlItem, "MACHTYPEATR")+
                    m_utility.getAttrValue(mdlItem, "MODELATR");
                fc1Tbl.put(mtm,mtm);
            }
        }

        // check all model and features for FC2
        Vector psVct = new Vector(1);
        for (int ii=0; ii<listVct2.size(); ii++){
            EntityList list = (EntityList)listVct2.elementAt(ii);
            EntityGroup eg = list.getEntityGroup("PRODSTRUCT");
            for (int i = 0; i < eg.getEntityItemCount(); i++) {
                EntityItem psItem = eg.getEntityItem(i);
                EntityItem mdlItem = (EntityItem)psItem.getDownLink(0);
                addDebug("removeMatching found "+psItem.getKey()+", "+mdlItem.getKey()+" and "+
                    psItem.getUpLink(0).getKey()+" for FC2 "+m_strFC2);
                String mtm = m_utility.getAttrValue(mdlItem, "MACHTYPEATR")+
                    m_utility.getAttrValue(mdlItem, "MODELATR");
                if (fc1Tbl.containsKey(mtm)){
                    psVct.addElement(psItem);
                }
            }
        }

        removeProdstructs(_db, _prof, psVct, _bGenData);

        psVct.clear();

        for (int ii=0; ii<listVct1.size(); ii++){
            EntityList list = (EntityList)listVct1.elementAt(ii);
            list.dereference();
        }
        listVct1.clear();
        for (int ii=0; ii<listVct2.size(); ii++){
            EntityList list = (EntityList)listVct2.elementAt(ii);
            list.dereference();
        }
        listVct2.clear();
    }

    /***************************************
    * Remove these prodstructs
    *
    */
    private void removeProdstructs(Database _db, Profile _prof, Vector psVct, boolean _bGenData)
    throws
            COM.ibm.opicmpdh.middleware.LockException,
            COM.ibm.opicmpdh.middleware.MiddlewareException,
            COM.ibm.eannounce.objects.EANBusinessRuleException,
            COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
            java.sql.SQLException, SBRException
    {
        for (int ii=0; ii<psVct.size(); ii++){
            EntityItem ei = (EntityItem)psVct.elementAt(ii);
            addComment(_db, _prof, ei, _bGenData);
            addDebug("removing "+ei.getKey());
            if (!_bGenData){
                addMessage("Would delete " + ei.getKey());
            }
            else{
                addMessage("Deleted " + ei.getKey());
            }
        }

        if (_bGenData){
            EntityItem psItemArray[] = new EntityItem[psVct.size()];
            psVct.copyInto(psItemArray);

            DeleteActionItem deleteActionItem = new DeleteActionItem(null, _db, _prof, "DELPRODSTRUCT");
            deleteActionItem.setEntityItems(psItemArray);
            deleteActionItem.executeAction(_db, _prof);
        }
    }

    /***************************************
    * 6.    Comment
    *
    * IG - Inventory Group (INVENTORYGROUP)
    * MT - Machine Type (AFMACHTYPEATR)
    * MODEL - Model (AFMODELATR)
    * FC- Feature Code 1 (PDGFC1)
    *
    * For every matching PRODSTRUCT based on MT, MODEL, FC, update COMMENTS.
    */
    private void addComment(Database _db, Profile _prof, boolean _bGenData)
    throws SQLException, MiddlewareException,SBRException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        if (m_strComments ==null){
            m_SBREx.add("COMMENTS is required for Comment update.");
            return;
        }

        if (m_strFC1 ==null){
            m_SBREx.add("Feature Code 1 is required for Comment update.");
            return;
        }
        // search for PRODSTRUCTs using FC1 and FEATURE invgrp and model COFGRP
        // add COMMENTS to each PRODSTRUCT
        Vector listVct = findProdstructs(_db, _prof, m_strFC1);

        if (listVct.size() == 0) {
            m_SBREx.add("There are no PRODSTRUCT that have FEATURECODE matching " + m_strFC1 +
                ", INVENTORYGROUP=" + m_strIGdesc + ", Base MODEL="+m_strMODEL+
                "and MACHTYPE="+m_strMT+".");
        }else{
            for (int ii=0; ii<listVct.size(); ii++){
                EntityList list = (EntityList)listVct.elementAt(ii);
                EntityGroup eg = list.getEntityGroup("PRODSTRUCT");
                for (int i = 0; i < eg.getEntityItemCount(); i++) {
                    EntityItem ei = eg.getEntityItem(i);
                    addComment(_db, _prof, ei, _bGenData);
                }
                list.dereference();
            }
            listVct.clear();
        }
    }

    /***************************************
    * add comment to specified prodstruct
    */
    private void addComment(Database _db, Profile _prof, EntityItem psitem, boolean _bGenData)
    throws SQLException, MiddlewareException,SBRException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        if (m_strComments ==null){ // user may not have added a comment on a remove or change
            return;
        }

        EANAttribute att = psitem.getAttribute("COMMENTS");
        String strValue = ((att != null) ? att.toString() + NEW_LINE + m_strComments : m_strComments);
        if (_bGenData){
            OPICMList attList = new OPICMList();
            attList.put("COMMENTS", "COMMENTS=" + strValue);
            m_utility.updateAttribute(_db, _prof, psitem, attList);
            m_sbActivities.append("<ITEM><ACTION>Update COMMENTS=" + strValue + "</ACTION><ENTITYKEY>" + psitem.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(psitem.getLongDescription()) + "</ENTITYDISPLAY><PARENT></PARENT></ITEM>" + NEW_LINE);
        }else{
            addMessage("Would update COMMENTS=" + strValue + " on " + psitem.getKey());
        }
    }

    /***************************************
    * 7.    Update Feature Dates
    *
    * IG - Inventory Group (INVENTORYGROUP)
    * FCLIST  - Feature Code List (PDGFCLIST)
    * DATE - First Announcement Date (FIRSTANNDATE)
    * General Availability Date (GENAVAILDATE)
    *
    * For every matching FEATURE from the list of FEATURECODEs in FCLIST, update FIRSTANNDATE,
    * GENAVAILDATE if input is not null.
    */
    private void updateFeatureDates(Database _db, Profile _prof, boolean _bGenData)
    throws SQLException, MiddlewareException,SBRException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        String genDate = null;
        EANAttribute att = m_eiPDG.getAttribute("PDGFCLIST");
        if (att ==null || att.toString().length()==0){
            m_SBREx.add("FEATURELIST is required for Update Feature Dates.");
            return;
        }

        Vector vctFCodes = m_utility.sepLongText(((String) att.get()).trim());

        att = m_eiPDG.getAttribute("FIRSTANNDATE");
        if (att ==null || att.toString().length()==0){
            m_SBREx.add("FIRSTANNDATE is required for Update Feature Dates.");
            return;
        }

        String annDate = ((String) att.get()).trim();
        att = m_eiPDG.getAttribute("GENAVAILDATE");
        if (att != null){
            genDate = ((String) att.get()).trim();
        }

        OPICMList attList = new OPICMList();
        attList.put("FIRSTANNDATE", "FIRSTANNDATE=" + annDate);
        if (genDate !=null && genDate.length()>0){
            attList.put("GENAVAILDATE", "GENAVAILDATE=" + genDate);
        }

        Vector fcitemVct = new Vector(1);
        // check for each featurecode
        for (int i=0; i<vctFCodes.size(); i++){
            String strFC = (String) vctFCodes.elementAt(i);

            EntityItem[] aeiFC = findFeatures(_db, _prof, strFC);
            if (aeiFC != null && aeiFC.length > 0) {
                for (int j = 0; j < aeiFC.length; j++) {
                    fcitemVct.add(aeiFC[j]);
                }
            }
        }

        vctFCodes.clear();

        if (m_SBREx.getErrorCount() == 0) {
            // update each feature
            for (int i = 0; i < fcitemVct.size(); i++) {
                EntityItem ei = (EntityItem) fcitemVct.elementAt(i);
                if (_bGenData){
                    m_utility.updateAttribute(_db, _prof, ei, attList);
                    m_sbActivities.append("<ITEM><ACTION>Update FIRSTANNDATE=" + annDate + "</ACTION><ENTITYKEY>" + ei.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(ei.getLongDescription()) + "</ENTITYDISPLAY><PARENT></PARENT></ITEM>" + NEW_LINE);
                    if (genDate !=null && genDate.length()>0){
                        m_sbActivities.append("<ITEM><ACTION>Update GENAVAILDATE=" + genDate + "</ACTION><ENTITYKEY>" + ei.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(ei.getLongDescription()) + "</ENTITYDISPLAY><PARENT></PARENT></ITEM>" + NEW_LINE);
                    }
                }else{
                    addMessage("Would update FIRSTANNDATE=" + annDate + " on " + ei.getKey());
                    if (genDate !=null && genDate.length()>0){
                        addMessage("Would update GENAVAILDATE=" + genDate + " on " + ei.getKey());
                    }
                }
            }
        }
        fcitemVct.clear();
    }

    /***************************************
    * 8.    Withdraw Feature
    * IG - Inventory Group (INVENTORYGROUP)
    * FC - Feature Code 1 (PDGFC1)
    * Global Withdrawal Date Effective (WITHDRAWDATEEFF_T)
    *
    * Update WITHDRAWDATEEFF_T for the FEATURE with FC.
    *
    */
    private void withdrawFeature(Database _db, Profile _prof, boolean _bGenData)
    throws SQLException, MiddlewareException,SBRException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        if (m_strFC1 ==null){
            m_SBREx.add("FEATURECODE is required for Withdraw Feature.");
            return;
        }

        EANAttribute att = m_eiPDG.getAttribute("WITHDRAWDATEEFF_T");
        if (att ==null || att.toString().length()==0){
            m_SBREx.add("WITHDRAWDATEEFF_T is required for Withdraw Feature.");
            return;
        }
        String strDate = ((String) att.get()).trim();

        OPICMList attList = new OPICMList();
        attList.put("WITHDRAWDATEEFF_T", "WITHDRAWDATEEFF_T=" + strDate);

        EntityItem[] aeiFC = findFeatures(_db, _prof, m_strFC1);
        if (aeiFC != null && aeiFC.length > 0) {
            for (int i = 0; i < aeiFC.length; i++) {
                EntityItem ei = (EntityItem) aeiFC[i];
                m_utility.updateAttribute(_db, _prof, ei, attList);
                m_sbActivities.append("<ITEM><ACTION>Update WITHDRAWDATEEFF_T=" + strDate + "</ACTION><ENTITYKEY>" + ei.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(ei.getLongDescription()) + "</ENTITYDISPLAY><PARENT></PARENT></ITEM>" + NEW_LINE);
            }
        }
    }

    /***************************************
    * Find prodstructs using feature.fcode, feature.inventorygroup, model.modelatr
    * try each model.machtypeatr flag specified
    * then discard any prodstruct that have model.cofgrp that isnt base (150)
    *
    */
    private Vector findProdstructs(Database _db, Profile _prof, String fcode)
    throws SQLException, MiddlewareException,SBRException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        Vector listVct = new Vector(1);

        // MACHTYPEATR is a flag and cant pass in a wild card, must search for each one
        StringBuffer sb = new StringBuffer("map_FEATURE:INVENTORYGROUP=" + m_strIG + ";");
        sb.append("map_FEATURE:FEATURECODE=" + fcode.replace('?', '_')+";");
        sb.append("map_MODEL:MODELATR=" + m_strMODEL.replace('?', '_')+";");
        //sb.append("map_MODEL:COFGRP=150"+";");//this has classification so cant do directly

        String psSrchActionName = (String) m_saiList.get("PRODSTRUCT");// should be in meta
        if (psSrchActionName==null){
            psSrchActionName = "SRDPRODSTRUCT33";
        }

        for (int i = 0; i < mtFlagCodes.length; i++) {
            String strFlagCode = mtFlagCodes[i];
            EntityList list = m_utility.dynaSearchIIForEntityList(_db, _prof, m_eiPDG,
                psSrchActionName, "PRODSTRUCT", sb.toString()+"map_MODEL:MACHTYPEATR=" + strFlagCode);
            // group will be null if no matches are found
            EntityGroup psgrp = list.getEntityGroup("PRODSTRUCT");
            if (psgrp ==null || psgrp.getEntityItemCount()==0){
                addDebug("There are no PRODSTRUCT that have FEATURECODE matching " + fcode +
                    ", INVENTORYGROUP=" + m_strIGdesc + ", MODEL="+m_strMODEL+
                    " and MACHTYPE="+strFlagCode+".");
            }else{
                // must enforce model=base here because it has classification
                EntityItem eiArray[] = psgrp.getEntityItemsAsArray();
                for (int di=0;di<eiArray.length; di++)
                {
                    EntityItem psItem = eiArray[di];
                    EntityItem mdlItem = (EntityItem)psItem.getDownLink(0);
                    String cofgrp = m_utility.getAttrValue(mdlItem, "COFGRP");
                    if (!cofgrp.equals("150")){
                        addDebug(mdlItem.getKey()+" '"+cofgrp+"' is not Base, removing "+psItem.getKey());
                        psgrp.removeEntityItem(psItem);
                    }
                }

                if (psgrp.getEntityItemCount()==0){
                    addDebug("There are no PRODSTRUCT that have FEATURECODE matching " + fcode +
                        ", INVENTORYGROUP=" + m_strIGdesc + ", Base MODEL="+m_strMODEL+
                        " and MACHTYPE="+strFlagCode+".");
                }else{
                    listVct.addElement(list);
                }
            }
        }

        return listVct;
    }

    /***************************************
    * Find features
    *
    */
    private EntityItem[] findFeatures(Database _db, Profile _prof, String fcode)
    throws SQLException, MiddlewareException,SBRException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        StringBuffer sb = new StringBuffer("map_INVENTORYGROUP=" + m_strIG + ";");
        sb.append("map_FEATURECODE=" + fcode.replace('?', '_'));

        EntityItem[] aeiFC = m_utility.dynaSearch(_db, _prof, m_eiPDG,
            (String) m_saiList.get("FEATURE"), "FEATURE", sb.toString());
        if (aeiFC == null || aeiFC.length == 0) {
            m_SBREx.add("There are no FEATUREs that have FEATURECODE matching " + fcode + " and INVENTORYGROUP=" + m_strIGdesc + ".");
        }

        return aeiFC;
    }

    /****************************************
    * add PRODSTRUCT values from the PDG to the workgroup default PRODSTRUCT
    * this is used as the prodstruct template
    */
    private void addPDGvaluesToDefaultProdstruct(Database _db, Profile _prof)
    throws SQLException, MiddlewareException
    {
        int defaultIndex = _prof.getDefaultIndex(); // negative entity id used for default (saved by admin when creating a FEATURE for WG)

        //  put pdg attr in this entity and use this to create prodstructs

        EntityGroup eg = new EntityGroup(null, _db, _prof, "PRODSTRUCT", "Edit", false);
        defPsItem = new EntityItem(eg, _prof, _db, "PRODSTRUCT", defaultIndex);

        for (int i = 0; i < m_eiPDG.getAttributeCount(); i++) {
            EANAttribute att = m_eiPDG.getAttribute(i);
            EANMetaAttribute metaAttr = defPsItem.getEntityGroup().getMetaAttribute(att.getAttributeCode());
            if (metaAttr==null) { // not a PRODSTRUCT attribute
                continue;
            }

            defPsItem.putAttribute(att);
        }
    }

    /****************************************
    * get INVENTORYGROUP, either from PDG or from WG default FEATURE
    */
    private void setInventoryGroup(Database _db, Profile _prof)
    throws SQLException, MiddlewareException
    {
        getInventoryGroup(m_eiPDG);
        if (m_strIG ==null){
            // invgrp from FEATURE and default entity
            int defaultIndex = _prof.getDefaultIndex(); // negative entity id used for default (saved by admin when creating a FEATURE for WG)
            EntityGroup eg = new EntityGroup(null, _db, _prof, "FEATURE", "Edit", false);
            EntityItem theEntityItem = new EntityItem(eg, _prof, _db, "FEATURE", defaultIndex);

            getInventoryGroup(theEntityItem);
        }
    }

    /****************************************
    * get INVENTORYGROUP, either from PDG or from WG default FEATURE
    */
    private void getInventoryGroup(EntityItem item)
    {
        EANAttribute att = item.getAttribute("INVENTORYGROUP");
        if (att !=null && att.toString().length()>0){
            MetaFlag[] amf = (MetaFlag[]) att.get();
            for (int f = 0; f < amf.length; f++) {
                if (amf[f].isSelected()) {
                    m_strIG = amf[f].getFlagCode().trim();
                    m_strIGdesc = amf[f].getLongDescription();
                    addDebug("Found IG in "+item.getKey()+" "+m_strIGdesc);
                    break;
                }
            }
        }
    }

}
