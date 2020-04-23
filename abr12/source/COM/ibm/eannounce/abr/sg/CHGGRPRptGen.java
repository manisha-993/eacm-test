// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import java.util.*;
import java.text.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
 * This class contains methods shared by the CHANGEGROUP ABR and BUI report
 * CR0420044342 and CR042004453
 *
 *@author     Wendy Stimpson
 *@created    Sept 30, 2004
 */
// CHGGRPRptGen.java,v
// Revision 1.15  2007/04/11 18:23:00  frosam
// WebKing Fixes
//
// Revision 1.14  2006/01/25 20:13:26  wendy
// AHE copyright
//
// Revision 1.13  2006/01/24 16:59:32  yang
// Jtest Changes
//
// Revision 1.12  2005/12/22 19:23:11  wendy
// Changes for DQA layout tables
//
// Revision 1.11  2005/12/19 16:07:15  wendy
// DQA changes for th id attributes and td headers attributes
//
// Revision 1.10  2005/10/06 13:14:47  wendy
// Convert to AHE format
//
// Revision 1.9  2005/07/13 13:57:22  couto
// <col> tags were all closed.
//
// Revision 1.8  2005/06/15 12:48:45  wendy
// Jtest chgs
//
// Revision 1.7  2004/11/18 14:53:53  wendy
// MessageFormat breaks if braces are in displayname, replace then restore them
//
// Revision 1.6  2004/11/17 20:43:16  wendy
// Added check for row using R too on getRowIndex
//
// Revision 1.5  2004/11/17 19:47:49  joan
// change to getRowIndex
//
// Revision 1.4  2004/11/17 17:04:25  joan
// changes due to rowselectabletable key
//
// Revision 1.3  2004/11/12 16:26:54  wendy
// Added check for X attr type
//
// Revision 1.2  2004/09/30 16:44:16  wendy
// Attempt to align all columns
//
// Revision 1.1  2004/09/30 15:25:47  wendy
// Init for OIM3.0a CR0420044342 and CR042004453
//
public class CHGGRPRptGen
{
    /** cvs revision number */
    public static final String VERSION = "1.15";
    static final String DELIMITER = "|";
    private Database dbCurrent;
    private Profile profile;
    private String startTime=null, endTime=null;
    private int cgEntityId=0;
    private String cgIdentifier=null;    // WARNING: right now, mw is putting "CHGGRPNAME" into AttrChgHistItem THIS IS NOT UNIQUE!
    private Hashtable metaTbl;      // Hashtable to save meta attributes, attempt to be more efficient
    private static final String DELETED = "Deleted";
    private static final String NEW = "New";
    private static final String CHANGED = "Changed";
    private static final String RESTORED = "Restored";
    private static final String OTHER_INDICATOR = "*";
    private static final String BLANK = "&nbsp;";
    private static final int MW_VENTITY_LIMIT = 100; // actual limit depends on data getting returned
    private static final String OPEN_BRACE_REP = "XOPENBRACEX";  // just replace open brace
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private static final int L16=16;
    private static final int BAD_ID = Integer.MIN_VALUE;

    /*
    CHGGRPSTATUS
    flag desc: Open         flagcode: Open
    flag desc: Cancelled    flagcode: Close
    flag desc: Submitted    flagcode: Invest
    */
    private static final String CHGGRPSTATUS_OPEN = "Open";

    /*
    CHANGEGROUP CHGGRPCHANGES   X   Changes Made
    CHANGEGROUP CHGGRPNAME      T   Name
    CHANGEGROUP CHGGRPREQDATE   T   Required Date
    CHANGEGROUP CHGGRPREQUEST   X   Request Description
    */
    private static final String[] CHGGRP_ATTR = new String[]
    {
        "CHGGRPREQDATE","CHGGRPREQUEST","CHGGRPCHANGES"//,"CHGGRPSTATUS"
    };

    /*
    MTM section 1

    MACHTYPE    BRAND           U   Brand
    MACHTYPE    INVENTORYGROUP  U   Inventory Group
    MACHTYPE    MACHTYPEATR     U   Machine Type
    MODEL       ANNDATE         T   Announce Date
    MODEL       COFCAT          U   Model Category
    MODEL       COFGRP          U   Model Group
    MODEL       COFSUBCAT       U   Model Subcategory
    MODEL       COFSUBGRP       U   Model Subgroup
    MODEL       COMMENTS        L   Comments
    MODEL       DESCRIPTION     L   Description
    MODEL       MACHTYPEATR     U   Machine Type
    MODEL       MKTGNAME        T   Marketing Name
    MODEL       MODELATR        T   Machine Model
    PRODSTRUCT  ANNDATE         T   Announce Date
    PRODSTRUCT  COMMENTS        L   Comments
    PRODSTRUCT  MKTGNAME        T   Marketing Name
    PRODSTRUCT  ORDERCODE       U   Original Order Code
    PRODSTRUCT  SYSTEMMAX       T   Maximum Allowed
    PRODSTRUCT  SYSTEMMIN       T   Minimum Required
    FEATURE     COMMENTS        L   Comments
    FEATURE     CONFIGURATORFLAG    U   Configurator Flag
    FEATURE     DESCRIPTION     L   Description
    FEATURE     FCTYPE          U   Feature Type
    FEATURE     FEATURECODE     T   Feature Code
    FEATURE     FIRSTANNDATE    T   First Announcement Date
    FEATURE     HWFCCAT         U   HW Feature Category
    FEATURE     HWFCSUBCAT      U   HW Feature Subcategory
    FEATURE     INVNAME         T   Price File Name
    FEATURE     MKTGNAME        T   Marketing Name
    FEATURE     WITHDRAWANNDATE_T   T   Global Withdrawal Announce Date - Target
    */
    private static final String[] MACHTYPE_ATTR = new String[]
    {
        "BRAND", "INVENTORYGROUP", "MACHTYPEATR"
    };
    private static final String[] MODEL_ATTR = new String[]
    {
        "ANNDATE", "DESCRIPTION","MACHTYPEATR","MODELATR","MKTGNAME","COFCAT", "COFSUBCAT",
        "COFGRP", "COFSUBGRP","COMMENTS"
    };
    private static final String[] PRODSTRUCT_ATTR = new String[]
    {
        "ANNDATE", "MKTGNAME","SYSTEMMIN", "SYSTEMMAX","ORDERCODE","COMMENTS"
    };
    private static final String[] FEATURE_ATTR = new String[]
    {
        "CONFIGURATORFLAG", "DESCRIPTION","FEATURECODE","FCTYPE",
        "FIRSTANNDATE","WITHDRAWANNDATE_T","MKTGNAME","INVNAME","HWFCCAT","HWFCSUBCAT","COMMENTS"
    };

    /* section 2
    MODELCONVERT    ANNDATE         T   Announce Date
    MODELCONVERT    COMMENTS        L   Comments
    MODELCONVERT    FROMMACHTYPE    T   From Machine Type
    MODELCONVERT    FROMMODEL       T   From Model
    MODELCONVERT    TOMACHTYPE      T   To Machine Type
    MODELCONVERT    TOMODEL         T   To Model
    MODELCONVERT    UPGRADETYPE     U   Upgrade Type
    MODELCONVERT    WITHDRAWDATE    T   Withdraw Date
    */
    private static final String[] MODELCNV_ATTR = new String[]
    {
        "FROMMACHTYPE","FROMMODEL","TOMACHTYPE", "TOMODEL","ANNDATE",
        "UPGRADETYPE","WITHDRAWDATE", "COMMENTS"
    };

    /* section 3
    FCTRANSACTION   ANNDATE         T   Announce Date
    FCTRANSACTION   COMMENTS        L   Comments
    FCTRANSACTION   FROMFEATURECODE T   From Feature Code
    FCTRANSACTION   FROMMACHTYPE    T   From Machine Type
    FCTRANSACTION   FROMMODEL       T   From Model
    FCTRANSACTION   MACHTYPEATR     U   Machine Type
    FCTRANSACTION   TOFEATURECODE   T   To Feature Code
    FCTRANSACTION   TOMACHTYPE      T   To Machine Type
    FCTRANSACTION   TOMODEL         T   To Model
    FCTRANSACTION   UPGRADETYPE     U   Upgrade Type
    FCTRANSACTION   WITHDRAWDATE    T   Withdraw Date
    */
    private static final String[] FCTRANS_ATTR = new String[]
    {
        "FROMMACHTYPE","FROMMODEL","FROMFEATURECODE",
        "TOMACHTYPE", "TOMODEL","TOFEATURECODE",
        "UPGRADETYPE","ANNDATE","WITHDRAWDATE", "COMMENTS"
    };

//  private Vector mtVct = new Vector();        // used for sort MACHTYPE for MTM portion
    // if not linking mdl to them, it can be a local var
    private Vector mdlVct = new Vector();       // used for sort MODEL for MTM portion
    private Hashtable chgTbl = new Hashtable(); // hang onto all SortedCG for MTM portion, some may just be labels
    private Vector featVct = new Vector();      // hang onto FEATURE for MTM portion, leftovers are sorted separately

    /**************************************************************************************
    * Constructor
    *
    * @param db Database object
    * @param p Profile object
    */
    public CHGGRPRptGen(Database db, Profile p)
    {
        dbCurrent = db;
        profile = p;
        metaTbl = new Hashtable();
    }

    /**************************************************************************************
    * Release memory
    */
    public void dereference()
    {
        dbCurrent = null;
        profile = null;
        metaTbl.clear();
        metaTbl = null;
//      mtVct.clear();
//      mtVct = null;
        mdlVct.clear();
        mdlVct = null;
        chgTbl.clear();
        chgTbl = null;
        featVct.clear();
        featVct = null;
    }

    /**************************************************************************************
    * Get the Heading info for this ChangeGroup and set timestamps
    * ABR and Report will get the EntityList for the CHANGEGROUP root entity
    *
    * @param list EntityList for CHANGEGROUP
    * @return String with report header
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
    * @throws java.sql.SQLException
    */
    public String getHeaderAndDTS(EntityList list) throws
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        EntityChangeHistoryGroup eChg = null;
        EANAttribute attStatus = null;

        // get current time stamp
        String curTime = dbCurrent.getNow(0);

        StringBuffer sb = new StringBuffer();
        EntityGroup eGrp = list.getParentEntityGroup();
        EntityItem chgItem = eGrp.getEntityItem(0);

        cgEntityId = chgItem.getEntityID();  // hang onto this.. profile.tranid may not match!!!

        // WARNING: using "CHGGRPNAME" to match AttributeChangeHistoryItem.changeGroup, this is not unique
        cgIdentifier = getAttributeValue(chgItem, "CHGGRPNAME", "", "", false).trim();

        // output heading.
        sb.append("<table width=\"560\" summary=\"layout\">"+NEWLINE);
        sb.append("<tr><td>Current Date: </td><td>"+
                (curTime.substring(0,curTime.length()-7))+"</td></tr>"+NEWLINE);
        sb.append("<tr><td>User: </td><td>"+profile.getOPName()+"</td></tr>"+NEWLINE);
        sb.append("<tr><td>User Role: </td><td>"+profile.getRoleCode()+"</td></tr>"+NEWLINE);
        sb.append("<tr><td colspan=\"2\">&nbsp;</td></tr>"+NEWLINE);

        //output the info for the change group
        for (int i=0; i<CHGGRP_ATTR.length; i++)
        {
            EANMetaAttribute ma = eGrp.getMetaAttribute(CHGGRP_ATTR[i]);
            String desc = CHGGRP_ATTR[i];
            if (ma != null){
                desc = ma.getActualLongDescription();}
            sb.append("<tr><td>"+desc+":</td><td>"+
                getAttributeValue(chgItem, CHGGRP_ATTR[i], "", "&nbsp;")
                +"</td></tr>"+NEWLINE);
        }
        sb.append("</table>"+NEWLINE);
        sb.append("<p><b>Note:</b> * denotes changes made outside of this "+eGrp.getLongDescription()+"</p>");

        // start timestamp is the creation date of the CG entity
        eChg = new EntityChangeHistoryGroup(dbCurrent, profile, chgItem);
        if (eChg ==null || eChg.getChangeHistoryItemCount()==0)
        {
            sb.append("<p><span style=\"color:#c00; font-weight:bold;\">Error: No Change history found for this "+eGrp.getLongDescription()+"</span></p>");
            cgEntityId = BAD_ID; // mark as error
        }
        else{
            startTime = eChg.getChangeHistoryItem(0).getChangeDate();

            /*sb.append("<!-- ChangeHistoryGroup for root entity "+chgItem.getEntityType()+":"+chgItem.getEntityID()+NEWLINE);
            for (int i=0; i<eChg.getChangeHistoryItemCount(); i++)
            {
                ChangeHistoryItem chi = eChg.getChangeHistoryItem(i);
                sb.append("ChangeHistoryItem["+i+"] user: "+chi.getUser()+" chgDate: "+chi.getChangeDate()+" isValid: "+chi.isValid()+NEWLINE);
            }
            sb.append("-->"+NEWLINE);*/

            // use current time as endtime if status = OPEN, else use last status chg timestamp
            endTime = curTime;

            attStatus = chgItem.getAttribute("CHGGRPSTATUS");
            sb.append("<!-- ");
            if (attStatus != null)
            {
                AttributeChangeHistoryGroup histGrp = dbCurrent.getAttributeChangeHistoryGroup(profile, attStatus);
                AttributeChangeHistoryItem achi =
                    (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-1);
                if (!(CHGGRPSTATUS_OPEN.equals(achi.getFlagCode())))  // use this timestamp, chggrp is not Open
                {
                    endTime = achi.getChangeDate();
                    sb.append("CHGGRPSTATUS was not Open, using flag dts, not curtime"+NEWLINE);
                }

                sb.append("ChangeHistoryGroup for Attribute: CHGGRPSTATUS"+NEWLINE);
                for (int i= histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
                {
                    AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
                    sb.append("AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
                        " isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode()+NEWLINE);
                }
            }
            else {
                sb.append("Error: Could not get AttributeHistory for CHGGRPSTATUS, it was null"+NEWLINE); }
            sb.append("-->"+NEWLINE);

            sb.append("<!-- Using starttime: "+startTime+" endtime: "+endTime+" tranId: "+profile.getTranID()+
                " "+chgItem.getEntityType()+":"+chgItem.getEntityID()+" cgIdentifier: *"+cgIdentifier+"* -->"+NEWLINE);

            if (profile.getTranID() != cgEntityId) {
                sb.append("<!-- WARNING: Using ChangeGroup entityId: "+cgEntityId+" instead of tranId: "+profile.getTranID()+
                        " -->"+NEWLINE);}
        }
        return sb.toString();
    }
//===============================================================================================================
// MTM section is split into smaller methods to allow comments to get sent back to browser to keep session active
//===============================================================================================================
    /**************************************************************************************
    * Find the MACHTYPE changes (MTM section 1) NOT all MODELs are associated to a MACHTYPE!
    * for now.. not tying it to mdl, just output it!
    EXRPT3MTM: RootEntity:MACHTYPE-->Assoc:MACHINETYPEMODELA--->Entity:MODEL<---Relator:PRODSTRUCT<---Entity:FEATURE
    * @return String with changed MACHTYPE
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    public String getMachType() throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        String value = "";

        if (cgEntityId != BAD_ID) { // no previous error
            StringBuffer sb = new StringBuffer("<h2>MTM</h2>"+NEWLINE);
            String extractName = "EXRPT3MTM";  // structure is needed but won't be returned by api

            ExtractActionItem eai = new ExtractActionItem(null, dbCurrent, profile, extractName);

            // get list of MACHTYPE that have this change grp id marker
            EntityList list = new EntityList(dbCurrent, profile, cgEntityId, eai, "MACHTYPE", startTime,endTime);

            Vector mtVct = new Vector();
            EntityGroup eGrp = list.getParentEntityGroup();

            sb.append("<!-- getMachType()"+NEWLINE+"EntityList for extract "+extractName+" for MACHTYPE contains the following entities: "+NEWLINE);
            sb.append(outputList(list));
            sb.append(" -->"+NEWLINE);

            sb.append("<h4>"+eGrp.getLongDescription()+"</h4>"+NEWLINE);
            for (int i=0; i<eGrp.getEntityItemCount(); i++)
            {
                // create a MT root for each
                EntityItem item = eGrp.getEntityItem(i);

                StringBuffer mtSb = new StringBuffer();
                EntityChangeHistoryGroup eHistGrp = new EntityChangeHistoryGroup(dbCurrent, profile, item);
                boolean mtchgs = getEntityInfo(item, MACHTYPE_ATTR, mtSb, eHistGrp);
                if (mtchgs)  // changes found
                {
                    String sortKey = getSortKey(item, new String[] {"MACHTYPEATR" }, eHistGrp);
                    SortedCG mtScg = new SortedCG(item, sortKey, mtSb.toString());
                    mtVct.addElement(mtScg);
                    //String key = item.getEntityType()+":"+item.getEntityID();
                    //chgTbl.put(key, mtScg);//is this still needed? not linking MT any more
                }
                else {
                    sb.append(mtSb.toString()); }// mtsb has debug info
            }

            if (mtVct.size()==0)
            {
                sb.append("<p>No changes found.</p>"+NEWLINE);
            }
            else
            {
                Collections.sort(mtVct);

                sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Machine type information\">"+NEWLINE+
                    "<colgroup><col width=\"8%\"/><col width=\"10%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"20%\"/>"+
                    "<col width=\"7%\"/><col width=\"15%\"/></colgroup>"+NEWLINE);

                for (int i=0; i<mtVct.size(); i++)
                {
                    SortedCG mtScg = (SortedCG)mtVct.elementAt(i);
                    sb.append(mtScg.getStructure(0));
                }
                sb.append("</table>"+NEWLINE);
                // release memory
                for (int i=0; i<mtVct.size(); i++)
                {
                    SortedCG mtScg = (SortedCG)mtVct.elementAt(i);
                    mtScg.dereference();
                }
                mtVct.clear();
            }
            mtVct = null;
            list.dereference(); // release memory
            value= sb.toString();
        }

        return value;
    }

    /**************************************************************************************
    * Find the MODEL and FEATURE changes (MTM section 1) hang onto these, they may
    * be linked.  after finding PRODSTRUCT chgs, merge these if possible
    * @return String with debug info
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    public String getModelAndFeature() throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        String value = "";

        if (cgEntityId != BAD_ID) { // no previous error
            EntityGroup featGrp = null;
            String entityType = "MODEL";
            StringBuffer sb = new StringBuffer();
            // get any tagged MODEL
            String extractName = "EXDUMMY";
            ExtractActionItem eai = new ExtractActionItem(null, dbCurrent, profile, extractName);
            EntityList list = new EntityList(dbCurrent, profile, cgEntityId, eai, entityType, startTime, endTime);

            // were any CG chgs found
            EntityGroup mdlGrp = list.getParentEntityGroup();
            sb.append("<!-- getModelAndFeature()"+NEWLINE+"EntityList for extract "+extractName+" for "+entityType+" contains the following entities: "+NEWLINE);
            sb.append(outputList(list));
            sb.append(" -->"+NEWLINE);

            for (int i=0; i<mdlGrp.getEntityItemCount(); i++)
            {
                EntityItem mdlItem = mdlGrp.getEntityItem(i);
                StringBuffer mdlSb = new StringBuffer();
                EntityChangeHistoryGroup eHistGrp = new EntityChangeHistoryGroup(dbCurrent, profile, mdlItem);
                boolean mdlchg = getEntityInfo(mdlItem, MODEL_ATTR, mdlSb,eHistGrp);
                if (!mdlchg)  // no changes found in subset of attr
                {
                    sb.append(mdlSb.toString()); // has debug info
                    continue;
                }
                else {
                    // build sort key
                    String sortKey = getSortKey(mdlItem, new String[] {"MACHTYPEATR","MODELATR" }, eHistGrp);
                    // this model has changes,
                    SortedCG mdlScg = new SortedCG(mdlItem, sortKey, mdlSb.toString());
                    String key = mdlItem.getEntityType()+":"+mdlItem.getEntityID();
                    sb.append("<!-- "+key+" had chgs  -->"+NEWLINE);
                    chgTbl.put(key,mdlScg);    // keep track of changed objs for prodstruct chk
                    mdlVct.addElement(mdlScg); // sorted later for display
                }
            }

            list.dereference(); // release memory

            // get any tagged FEATURE
            eai = new ExtractActionItem(null, dbCurrent, profile, extractName);
            entityType = "FEATURE";
            list = new EntityList(dbCurrent, profile, cgEntityId, eai, entityType, startTime,endTime);
            sb.append("<!-- getModelAndFeature()"+NEWLINE+"EntityList for extract "+extractName+" for "+entityType+" contains the following entities: "+NEWLINE);
            sb.append(outputList(list));
            sb.append(" -->"+NEWLINE);

            // were any CG chgs found
            featGrp = list.getParentEntityGroup();
            for (int i=0; i<featGrp.getEntityItemCount(); i++)
            {
                EntityItem featItem = featGrp.getEntityItem(i);
                StringBuffer featSb = new StringBuffer();
                EntityChangeHistoryGroup eHistGrp = new EntityChangeHistoryGroup(dbCurrent, profile, featItem);
                boolean featchg = getEntityInfo(featItem, FEATURE_ATTR,featSb,eHistGrp);
                if (!featchg)  // no changes found in subset of attr
                {
                    sb.append(featSb.toString()); // output debug info
                    continue;
                }
                else
                {
                    String key = featItem.getEntityType()+":"+featItem.getEntityID();
                    String sortKey = getSortKey(featItem, new String[] {"FEATURECODE" }, eHistGrp);
                    SortedCG featScg = new SortedCG(featItem, sortKey, featSb.toString());
                    // feature that are part of structure are sorted by featurecode alone
                    // standalone Feature are sorted by invgrp, then featurecode
                    String sortKey2 = getSortKey(featItem, new String[] {"INVENTORYGROUP" }, eHistGrp);
                    featScg.setAlternateSortKey(sortKey2+sortKey);

                    sb.append("<!-- "+key+" had chgs  -->"+NEWLINE);
                    chgTbl.put(key,featScg);     // keep track of changed objs for prodstruct chk
                    featVct.addElement(featScg); // save for prodstruct check
                }
            }

            list.dereference(); // release memory
            value = sb.toString();
        }
        return value;
    }

    /**************************************************************************************
    * Find the PRODSTRUCT changes (section 1) use EXRPT3FM,
    * by now all chgd MODEL and FEATURE have been found
    * the VE will return the PRODSTRUCT relator and both ends, see if either of these
    * ends are in the chgd list.  All must be displayed
    * if FEATURE are changed and not tied to one of these PRODSTRUCT, separate pull needed
    * to find MODEL, if it exists
    EXRPT3FM: RootEntity:FEATURE---->Relator:PRODSTRUCT---->Entity:MODEL
    * @return String with debug info
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    public String getProdStruct() throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        String value = "";
        if (cgEntityId != BAD_ID) { // no previous error
            StringBuffer sb = new StringBuffer();
            String entityType = "PRODSTRUCT"; // this is a relator root, it will pull both ends too!!
            // get any tagged PRODSTRUCT
            String extractName = "EXRPT3FM";

            ExtractActionItem eai = new ExtractActionItem(null, dbCurrent, profile, extractName);
            EntityList list = new EntityList(dbCurrent, profile, cgEntityId, eai, entityType, startTime,endTime);

            // were any CG chgs found
            EntityGroup prodGrp = list.getParentEntityGroup();
            sb.append("<!-- getProdStruct()"+NEWLINE+"EntityList for extract "+extractName+" for "+entityType+" contains the following entities: "+NEWLINE);
            sb.append(outputList(list));
            sb.append(" -->"+NEWLINE);

            for (int i=0; i<prodGrp.getEntityItemCount(); i++)
            {
                SortedCG prodScg = null;
                EntityItem prodItem = prodGrp.getEntityItem(i);
                String prodKey = prodItem.getEntityType()+":"+prodItem.getEntityID();
                StringBuffer prodSb = new StringBuffer();
                EntityChangeHistoryGroup eHistGrp = new EntityChangeHistoryGroup(dbCurrent, profile, prodItem);
                boolean prodchg = getEntityInfo(prodItem, PRODSTRUCT_ATTR, prodSb,eHistGrp);
                sb.append(NEWLINE+"<!-- Root proditem "+prodKey+" -->"+NEWLINE);
                if (!prodchg)  // no changes found in subset of attr
                {
                    sb.append(prodSb.toString()); // output debug info
                    continue;
                }
                prodScg = new SortedCG(prodItem, "", prodSb.toString());
                chgTbl.put(prodKey,prodScg);    // keep track of changed objs for later feature structure chk

                // look to see if MODEL is in chg table
                //  FEATURE-->PRODSTRUCT-->MODEL
                for (int u=0; u<prodItem.getDownLinkCount(); u++)
                {
                    EntityItem  mdlItem = (EntityItem)prodItem.getDownLink(u);
                    sb.append("<!--    dnlink mdlitem "+mdlItem.getEntityType()+":"+mdlItem.getEntityID()+" -->"+NEWLINE);
                    if (mdlItem.getEntityType().equals("MODEL"))
                    {
                        SortedCG mdlScg = (SortedCG)chgTbl.get(mdlItem.getEntityType()+":"+mdlItem.getEntityID());
                        sb.append("<!--    dnlink model scg "+mdlScg+" -->"+NEWLINE);
                        if (mdlScg==null)
                        {
                            // just create a label
                            String mdlkey = mdlItem.getEntityType()+":"+mdlItem.getEntityID();
                            String displayName = mdlItem.getEntityGroup().getLongDescription()+": "+
                                getDisplayName(mdlItem, " - ",", ");
                            String label = NEWLINE+"<!-- "+mdlkey+"  NO CG chgs -->"+NEWLINE+
                                "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">"+
                                displayName+"</th></tr>"+NEWLINE;
                            // mdlitem must be current at this point, don't need to get history
                            String sortKey = getSortKey(mdlItem, new String[] {"MACHTYPEATR","MODELATR" }, null);
                            mdlScg = new SortedCG(mdlItem, sortKey, label);
                            mdlVct.addElement(mdlScg);
                            chgTbl.put(mdlkey,mdlScg); // hang onto this, may be shared
                        }
                        mdlScg.addChild(prodScg); // for later display
                    }
                }

                // look to see if FEATURE is in chg table
                for (int u=0; u<prodItem.getUpLinkCount(); u++)
                {
                    EntityItem  featItem = (EntityItem)prodItem.getUpLink(u);
                    String key = featItem.getEntityType()+":"+featItem.getEntityID();
                    sb.append("<!--   uplink featitem "+key+" -->"+NEWLINE);
                    if (featItem.getEntityType().equals("FEATURE"))
                    {
                        SortedCG featScg = (SortedCG)chgTbl.get(key);
                        sb.append("<!--   uplink feat scg "+featScg+" -->"+NEWLINE);
                        if (featScg==null)
                        {
                            // just create a label
                            String displayName = featItem.getEntityGroup().getLongDescription()+": "+
                                getDisplayName(featItem, " - ",", ");
                            String label = NEWLINE+"<!-- "+key+"  NO CG chgs -->"+NEWLINE+
                                "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">"+
                                displayName+"</th></tr>"+NEWLINE;
                            // featitem is current, don't need history
                            String sortKey = getSortKey(featItem, new String[] {"FEATURECODE" }, null);
                            featScg = new SortedCG(featItem, sortKey, label);
                            chgTbl.put(key,featScg);// hang onto this, may be shared
                        }
                        else
                        {
                            // was found, but need to look for other models linked to this feature
                            // so leave featScg in the vector
                        }
                        prodScg.setSortKey(featScg.getSortKey()); // sort is based on FEATURE, not PRODSTRUCT
                        prodScg.addChild(featScg);
                    }
                }
            }

            list.dereference(); // release memory
            value= sb.toString();
        }
        return value;
    }

    /**************************************************************************************
    * Find other structure needed to complete MTM section
    * if FEATURE are changed, separate pull needed to find all MODELs even if
    * the FEATURE was tied to one of the chgd PRODSTRUCT
    * @return String with debug info
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    public String getOtherStructure() throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        StringBuffer sb = new StringBuffer();

        // build output for any CG tagged FEATURE (may have been found as part of PRODSTRUCT)
        if (featVct.size()>0)
        {
            // pull ve to get to model EXRPT3FM
            Vector tmp = new Vector();
            // mw has a limit of 50 for entity ids.. must break into groups of 50 or less
            if (featVct.size()>MW_VENTITY_LIMIT)
            {
                int numGrps = featVct.size()/MW_VENTITY_LIMIT;
                int numUsed=0;
                for (int i=0; i<=numGrps; i++)
                {
                    tmp.clear();
                    for (int x=0; x<MW_VENTITY_LIMIT; x++)
                    {
                        if (numUsed == featVct.size()) {
                            break; }
                        tmp.addElement(featVct.elementAt(numUsed++));
                    }
                    if (tmp.size()>0) {// could be 0 if num entities is multiple of limit
                        findModelForFeature(tmp,sb);}
                }
            }
            else
            {
                findModelForFeature(featVct,sb);
            }
            tmp.clear();
        }
        // there may be MODEL that are not tied to chgd prodstruct and/or feature
        // but are these substructures needed?  if so, look thru mdlVct and find any that do not
        // have prodstruct children.. and try to add to them, the mdlVct is used
        // to control final output

        // some FEATURE may still be standalone
        return sb.toString();
    }

    /**************************************************************************************
    * Get the value to use for sorting, if entity is deleted, must get values from history
    * @param item EntityItem to find key
    * @param attrCode String array of attr codes for sort key
    * @param eHistGrp EntityChangeHistoryGroup for this entity
    * @return String with sortkey
    */
    private String getSortKey(EntityItem item, String attrCode[], EntityChangeHistoryGroup eHistGrp)
    {
        StringBuffer sb = new StringBuffer();
        // if entity was deleted, all attr will be deactivated
        ChangeHistoryItem curEntityChi = null;
        if (eHistGrp!=null) {
            curEntityChi = eHistGrp.getChangeHistoryItem(eHistGrp.getChangeHistoryItemCount()-1); }// last one
        if (curEntityChi != null && !curEntityChi.isValid())
        {
            // all attr even if attr is deactivated at time entity is deleted, have the same last timestamp!
            RowSelectableTable itemTable = item.getEntityItemTable();
            for (int i=0; i<attrCode.length; i++)
            {
                String keyStr = item.getEntityType() + ":" + attrCode[i];
                try {
                    int row = itemTable.getRowIndex(keyStr);
                    if (row < 0) {
                        row = itemTable.getRowIndex(keyStr + ":C");
                    }
                    if (row < 0) {
                        row = itemTable.getRowIndex(keyStr + ":R");
                    }
                    if (row != -1)
                    {
                        EANAttribute attStatus = (EANAttribute) itemTable.getEANObject(row, 1);
                        if (attStatus != null)
                        {
                            AttributeChangeHistoryGroup histGrp = dbCurrent.getAttributeChangeHistoryGroup(profile, attStatus);
                            if (histGrp.getChangeHistoryItemCount()>1)// was previous one deactivated?
                            {
                                // was the attr was deactivated before the entity was deleted?
                                ChangeHistoryItem chi1 = histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-2);
                                if(!chi1.isActive())
                                {
                                    // attr was DELETED before Entity
                                    continue;
                                }
                            }
                            if (histGrp.getChangeHistoryItemCount()>0)
                            {
                                ChangeHistoryItem chi = histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-1);
                                sb.append(chi.get("ATTVAL",true).toString());
                            }
                        }
                    }
                } catch (Exception ee) {
                    if(ee==null){ // can't happen.. just make jtest happy
                        System.out.println(ee.getMessage());
                    }
                }
            }
        }
        else
        {
            for (int i=0; i<attrCode.length; i++) {
                sb.append(getAttributeValue(item, attrCode[i], "", "")); }
        }
        return sb.toString();
    }

    /**************************************************************************************
    * Get the structure for FEATURE changes using EXRPT3FM to find MODEL child
    * @param tmp Vector of EntityItems with CG tags
    * @param sb StringBuffer for debug
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    private void findModelForFeature(Vector tmp, StringBuffer sb) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
//      String extractName = "EXRPT3FMTM";  // structure is needed to find MACHTYPE parents.. not needed if not going to MT
        String extractName = "EXRPT3FM";  // structure is needed to find MODEL children
        String entityType = "FEATURE";
        ExtractActionItem eai = null;
        EntityList list = null;
        EntityGroup featGrp = null;
        EntityItem[] eiArray = new EntityItem[tmp.size()];
        for (int e=0; e<tmp.size(); e++)
        {
            SortedCG scg = (SortedCG)tmp.elementAt(e);
            eiArray[e] = new EntityItem(null, profile, "FEATURE", scg.getEntityId());
        }

        eai = new ExtractActionItem(null, dbCurrent, profile, extractName);

        // this may have some changes that are not in the set of attr
        list = dbCurrent.getEntityList(profile, eai,eiArray);

        sb.append("<!-- findModelForFeature()"+NEWLINE+"EntityList for extract "+extractName+" for "+entityType+" contains the following entities: "+NEWLINE);
        sb.append(outputList(list));
        sb.append(" -->"+NEWLINE);

        featGrp = list.getParentEntityGroup();
        for (int i=0; i<featGrp.getEntityItemCount(); i++)
        {
            EntityItem featItem = featGrp.getEntityItem(i);
            String key = featItem.getEntityType()+":"+featItem.getEntityID();
            SortedCG featScg = (SortedCG)chgTbl.get(key);
            sb.append("<!-- feature root "+key+" scg: "+featScg+" -->"+NEWLINE);

            // get PRODSTRUCT relators
            for (int d=0; d<featItem.getDownLinkCount(); d++)
            {
                EntityItem prodItem = (EntityItem)featItem.getDownLink(d);
                String prodkey = prodItem.getEntityType()+":"+prodItem.getEntityID();
                sb.append("<!--  downlink "+prodkey+" -->"+NEWLINE);
                if (prodItem.getEntityType().equals("PRODSTRUCT"))
                {
                    // add this
                    String displayName = prodItem.getEntityGroup().getLongDescription()+": "+
                        getDisplayName(prodItem, " - ",", ");
                    String label = NEWLINE+"<!-- "+prodkey+"  NO CG chgs -->"+NEWLINE+
                        "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">"+
                        displayName+"</th></tr>"+NEWLINE;

                    SortedCG prodScg = (SortedCG)chgTbl.get(prodkey);
                    if (prodScg==null) // wasn't tagged with a CG
                    {
                        prodScg = new SortedCG(prodItem, "", label);
                        prodScg.setSortKey(featScg.getSortKey()); // sort is based on FEATURE, not PRODSTRUCT

                        // add this feature as a child of prod.. for output only
                        prodScg.addChild(featScg);   // add to prodstruct

                        // get MODEL
                        for (int d2=0; d2<prodItem.getDownLinkCount(); d2++)
                        {
                            EntityItem mdlItem = (EntityItem)prodItem.getDownLink(d2);
                            sb.append("<!--      downlink2 "+mdlItem.getEntityType()+":"+mdlItem.getEntityID()+" -->"+NEWLINE);
                            if (mdlItem.getEntityType().equals("MODEL"))
                            {
                                addProdStructToModel(mdlItem, prodScg, sb);
                            }// end if MODEL
                        }
                    }

                    // remove it from the feature vector, it now has all structure
                    featVct.remove(featScg);
                } // end if PRODSTRUCT
            }
        }

        list.dereference(); // release memory
        for(int i=0; i<eiArray.length; i++) {
            eiArray[i] = null; }
    }
    /**************************************************************************************
    * Find or create a MODEL link for this PRODSTRUCT, the MODEL may have been previously found
    * with CG chgs
    * @param mdlItem EntityItem that was linked to a PRODSTRUCT with CG tags
    * @param prodScg SortedCG with PRODSTRUCT info
    * @param sb StringBuffer for debug
    */
    private void addProdStructToModel(EntityItem mdlItem, SortedCG prodScg, StringBuffer sb) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        String key = mdlItem.getEntityType()+":"+mdlItem.getEntityID();
        SortedCG mdlScg = (SortedCG)chgTbl.get(key);
        sb.append("<!-- mdl "+key+"  -->"+NEWLINE);
        if (mdlScg ==null) // this MODEL did not have chgs, add a label for it
        {
            // add this
            String displayName = mdlItem.getEntityGroup().getLongDescription()+": "+
                getDisplayName(mdlItem, " - ",", ");
            String label = NEWLINE+"<!-- "+key+"  NO CG chgs -->"+NEWLINE+
                "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">"+
                displayName+"</th></tr>"+NEWLINE;

            // mdlitem must be current at this point, don't need to get history
            String sortKey = getSortKey(mdlItem, new String[] {"MACHTYPEATR","MODELATR" }, null);
            sb.append("<!-- no match found in chgTbl for "+key+" -->"+NEWLINE);
            mdlScg = new SortedCG(mdlItem, sortKey, label);
            chgTbl.put(key,mdlScg); // hang onto this, may be shared
            mdlVct.addElement(mdlScg);
        }
        // add this prod as a child of model.. for output only
        mdlScg.addChild(prodScg);    // add to model
        sb.append("<!-- MDL SCG:: "+mdlScg+" -->"+NEWLINE);

        // get MACHTYPE parents, but using Assoc
        /*for (int d3=0; d3<mdlItem.getDownLinkCount(); d3++)
        {
            EANEntity assoc = mdlItem.getDownLink(d3);
            if (assoc.getEntityType().equals("MODELMACHINETYPEA"))
            {
                for (int d4=0; d4<assoc.getDownLinkCount(); d4++)
                {
                    EntityItem mtItem = (EntityItem)assoc.getDownLink(d4);
                    if (mtItem.getEntityType().equals("MACHTYPE"))
                    {
                        addModelToMT(mtItem, mdlScg, sb);
                    }
                }
            }
        }*/
    }

    /**************************************************************************************
    * Get the MTM changes (section 1) put all parts together
    * @return String with MTM structure info
    */
    public String getMTM()
    {
        String value ="";
        if (cgEntityId != BAD_ID) { // no previous error
            StringBuffer sb = new StringBuffer("<h4>Model</h4>"+NEWLINE);
            if (mdlVct.size()==0)
            {
                sb.append("<p>No changes found.</p>"+NEWLINE);
            }
            else
            {
                // sort the MODEL structure (includes PRODSTRUCT and FEATURE) and standalone MODEL all sorted together
                for (int i=0; i<mdlVct.size(); i++)
                {
                    ((SortedCG)mdlVct.elementAt(i)).sortIt();
                }
                Collections.sort(mdlVct);

                sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"MTM information\">"+NEWLINE+
                    "<colgroup><col width=\"8%\"/><col width=\"10%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"20%\"/>"+
                    "<col width=\"7%\"/><col width=\"15%\"/></colgroup>"+NEWLINE);

                for (int i=0; i<mdlVct.size(); i++)
                {
                    SortedCG mtScg = (SortedCG)mdlVct.elementAt(i);
                    sb.append(mtScg.getStructure(0));
                }
                sb.append("</table>"+NEWLINE);
            }

            sb.append("<h4>Stand-alone Feature</h4>"+NEWLINE);
            if (featVct.size()==0)
            {
                sb.append("<p>No changes found.</p>"+NEWLINE);
            }
            else // some features left over that weren't part of prodstruct
            {
                // fixme is this true? these FEATURES must be sorted by inventorygrp, then Featurecode
                // Features in part of structure are sorted by FC only
                for (int i=0; i<featVct.size(); i++)
                {
                    ((SortedCG)featVct.elementAt(i)).swapSortKey();
                }

                // sort the FEATURE
                Collections.sort(featVct);

                sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Feature information\">"+NEWLINE+
                    "<colgroup><col width=\"8%\"/><col width=\"10%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"20%\"/>"+
                    "<col width=\"7%\"/><col width=\"15%\"/></colgroup>"+NEWLINE);
                for (int i=0; i<featVct.size(); i++)
                {
                    SortedCG mtScg = (SortedCG)featVct.elementAt(i);
                    sb.append(mtScg.getStructure(0));
                }
                sb.append("</table>"+NEWLINE);
            }

            // release memory
            for (int i=0; i<mdlVct.size(); i++)
            {
                SortedCG mtScg = (SortedCG)mdlVct.elementAt(i);
                mtScg.dereference();
            }

            for (int i=0; i<featVct.size(); i++)
            {
                SortedCG mtScg = (SortedCG)featVct.elementAt(i);
                mtScg.dereference();
            }
            value = sb.toString();
        }

        return value;
    }

    /**************************************************************************************
    * Get the Model Conversion / Upgrade changes (section 2)
    * @return String with model conversion info
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    public String getModelConversion() throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        String value = "";
        if (cgEntityId != BAD_ID) { // no previous error
            StringBuffer sb = new StringBuffer("<h2>Model Conversion / Upgrade</h2>"+NEWLINE);
            String extractName = "EXDUMMY";  // only root info is needed

            ExtractActionItem eai = new ExtractActionItem(null, dbCurrent, profile, extractName);

            // get list of MODELCONVERT that have this change grp id marker
            EntityList list = new EntityList(dbCurrent, profile, cgEntityId, eai, "MODELCONVERT", startTime,  endTime);

            // output this root
            EntityGroup eGrp = list.getParentEntityGroup();
            sb.append("<!-- EntityList for extract "+extractName+" for MODELCONVERT contains the following entities: "+NEWLINE);
            sb.append(outputList(list));
            sb.append(" -->"+NEWLINE);
            if (eGrp== null || eGrp.getEntityItemCount()==0)
            {
                sb.append("<p>No changes found.</p>"+NEWLINE);
            }
            else {
                sb.append(fillInTable(eGrp, MODELCNV_ATTR)); }

            list.dereference(); // release memory
            value = sb.toString();
        }

        return value;
    }

    /**************************************************************************************
    * Get the Feature Transactions changes (section 3)
    * @return String
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    public String getFeatureTrans() throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        String value = "";
        if (cgEntityId != BAD_ID) { // no previous error
            StringBuffer sb = new StringBuffer("<h2>Feature Transactions</h2>"+NEWLINE);
            String extractName = "EXDUMMY";  // only root info is needed

            ExtractActionItem eai = new ExtractActionItem(null, dbCurrent, profile, extractName);
            // get list of FCTRANSACTION that have this change grp id marker
            EntityList list = new EntityList(dbCurrent, profile, cgEntityId, eai, "FCTRANSACTION", startTime,  endTime);

            // output this root
            EntityGroup eGrp = list.getParentEntityGroup();
            sb.append("<!-- EntityList for extract "+extractName+" for FCTRANSACTION contains the following entities: "+NEWLINE);
            sb.append(outputList(list));
            sb.append(" -->"+NEWLINE);
            if (eGrp== null || eGrp.getEntityItemCount()==0)  {
                sb.append("<p>No changes found.</p>"+NEWLINE);
            }
            else {
                sb.append(fillInTable(eGrp, FCTRANS_ATTR));
            }

            list.dereference(); // release memory
            value = sb.toString();
        }

        return value;
    }

    /**************************************************************************************
    * Get the table for this group of root entities.  Something in each root was found
    * to have been changed with this CG marker
    *
    * @param eGrp EntityGroup with root entityItems to display with changes
    * @param attrCodes String array of attribute codes to display if changed
    */
    private String fillInTable(EntityGroup eGrp, String[] attrCodes) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        StringBuffer sb = new StringBuffer();

        Vector scgVct = new Vector();
        for (int e=0; e<eGrp.getEntityItemCount(); e++)
        {
            EntityItem theItem = eGrp.getEntityItem(e);

            StringBuffer etSb = new StringBuffer();
            EntityChangeHistoryGroup eHistGrp = new EntityChangeHistoryGroup(dbCurrent, profile, theItem);
            boolean etchgs = getEntityInfo(theItem, attrCodes,etSb,eHistGrp);
            if (etchgs)  // changes found
            {
                SortedCG scg = null;
                String sortKey = "";
                ChangeHistoryItem curEntityChi = eHistGrp.getChangeHistoryItem(eHistGrp.getChangeHistoryItemCount()-1); // last one
                if (!curEntityChi.isValid()) {
                    sortKey = getDeletedDisplayName(theItem,"", true, null, true);}
                else {
                    sortKey = getDisplayName(theItem, "",""); }

                scg = new SortedCG(theItem, sortKey, etSb.toString());
                scgVct.addElement(scg);
            }
            else {
                sb.append(etSb.toString());} // etsb has debug info

        }
        if (scgVct.size()==0) // none of the entities had changes in the list of attr
        {
            sb.append("<p>No changes found.</p>"+NEWLINE);
        }
        else
        {
            Collections.sort(scgVct);

            sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Change group information\">"+NEWLINE+
                "<colgroup><col width=\"8%\"/><col width=\"10%\"/><col width=\"20%\"/><col width=\"20%\"/><col width=\"20%\"/>"+
                "<col width=\"7%\"/><col width=\"15%\"/></colgroup>"+NEWLINE);
            for (int i=0; i<scgVct.size(); i++)
            {
                SortedCG scg = (SortedCG)scgVct.elementAt(i);
                sb.append(scg.getStructure(0));
            }
            sb.append("</table>"+NEWLINE);

            // release memory
            for (int i=0; i<scgVct.size(); i++)
            {
                SortedCG scg = (SortedCG)scgVct.elementAt(i);
                scg.dereference();
            }
            scgVct.clear();
        }
        scgVct = null;

        return sb.toString();
    }

    /**************************************************************************************
    * Get the change information for this entity.  Something was found
    * to have been changed with this CG marker
    *
    * @param theItem EntityItem to display with changes
    * @param attrCodes String array of attribute codes to display if changed
    * @param sb StringBuffer for info
    * @param eHistGrp EntityChangeHistoryGroup for this entity
    * @return boolean true if changes were in the list of attrcodes
    */
    private boolean getEntityInfo(EntityItem theItem, String[] attrCodes, StringBuffer sb,
        EntityChangeHistoryGroup eHistGrp) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        String displayName = theItem.getEntityGroup().getLongDescription()+": "+getDisplayName(theItem, " - ",", ");
        int lineCnt=0;
        boolean attrChgsFnd = false;
        StringBuffer tmpSb = new StringBuffer();
        String label = null;

        boolean chgsFnd = false;
        boolean byCG = false;

        String entityDelInfo="";
        // if entity was deleted, all attr will be deactivated
        ChangeHistoryItem curEntityChi = eHistGrp.getChangeHistoryItem(eHistGrp.getChangeHistoryItemCount()-1); // last one
        sb.append(NEWLINE+"<!-- "+theItem.getEntityType()+":"+theItem.getEntityID()+" -->"+NEWLINE);
        if (!curEntityChi.isValid())
        {
            String dateChg = curEntityChi.getChangeDate();
            dateChg = dateChg.substring(0,dateChg.length()-L16);
            entityDelInfo = (theItem.getEntityGroup().isRelator()?"Relator":"Entity")+" was deleted on "+
                dateChg+" by "+curEntityChi.getUser()+" ["+curEntityChi.get("ROLE",true)+"]";
            sb.append("<!-- ****** Entity "+theItem.getEntityType()+":"+theItem.getEntityID()+
                " has been deleted by "+curEntityChi.getChangeGroup()+"-->"+NEWLINE);
            sb.append("<!-- ***** ChangeHistoryGroup for "+theItem.getEntityType()+":"+theItem.getEntityID()+NEWLINE);
            for (int i=eHistGrp.getChangeHistoryItemCount()-1; i>=0; i--)
            {
                ChangeHistoryItem chi = eHistGrp.getChangeHistoryItem(i);
                sb.append("ChangeHistoryItem["+i+"] chgGrp: "+chi.getChangeGroup()+" chgDate: "+
                    chi.getChangeDate()+" isValid: "+chi.isValid()+" isActive: "+chi.isActive()+NEWLINE);
            }
            sb.append("-->"+NEWLINE);

            byCG = wasChangedByCG(curEntityChi);
            // get display name, must get it before deletion
            displayName = getDeletedDisplayName(theItem, " - ", byCG,sb,false)
                +"<br />"+entityDelInfo;
            if (byCG){
                chgsFnd = true;}
        }

        label = "<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">"+
            displayName+"</th></tr>"+NEWLINE+
            "<tr style=\"background-color:#cef;\">"+
            "<th style=\"text-align:center;\" id=\"changeType\">Change Type</th>"+
            "<th style=\"text-align:center;\" id=\"attribute\">Attribute</th>"+
            "<th style=\"text-align:center;\" id=\"original\">Original Value</th>"+
            "<th style=\"text-align:center;\" id=\"chgValue\">Change Group Value</th>"+
            "<th style=\"text-align:center;\" id=\"current\">Current Value</th>"+
            "<th style=\"text-align:center;\" id=\"chgDate\">Date Changed</th>"+
            "<th style=\"text-align:center;\" id=\"chgBy\">Changed By</th></tr>"+NEWLINE;


        //output the info for this entity
        for (int i=0; i<attrCodes.length; i++)
        {
            String attrCode = attrCodes[i];

            // has this attr changed in this chg grp? if not, do not display
            AttributeChangeHistoryGroup histGrp = hasAttributeChgd(theItem, attrCode, sb);
            if (histGrp == null)
            {
                sb.append("<!-- "+attrCode+" was not changed in the CHANGEGROUP -->"+NEWLINE);
                continue;
            }
            attrChgsFnd = true;
            if (lineCnt==0) {// first one, so put label info out
                tmpSb.append(label);}
            tmpSb.append("<!-- "+attrCode+" was changed by CG -->"+NEWLINE);

            tmpSb.append(getAttributeInfo(theItem, attrCode, lineCnt++, histGrp));
        }

        if (!attrChgsFnd)
        {
            sb.append("<!-- ** NO CG chgs found in specified attr set for "+theItem.getEntityType()+":"+
                theItem.getEntityID()+" -->"+NEWLINE);
            if (chgsFnd) { // entity was deleted by CG.. could only get here if entity didn't have any specifed attr set
                sb.append("<tr style=\"{0}\" {1}><th colspan=\"7\" id=\"displayName\">"+
                    displayName+"</th></tr>"+NEWLINE);
            }
        }
        sb.append(tmpSb.toString());

        return chgsFnd || attrChgsFnd;
    }

    /**************************************************************************************
    * Check to see if this attribute was changed by this tran id
    *
    * @param theItem EntityItem to display with changes
    * @param attrCode String attribute code to check
    * @param sb StringBuffer for trace info
    * @return AttributeChangeHistoryGroup if changes were in the this attrcode, null if not
    */
    private AttributeChangeHistoryGroup hasAttributeChgd(EntityItem theItem, String attrCode, StringBuffer sb)
        throws
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.sql.SQLException
    {
        AttributeChangeHistoryGroup tranGrp = null;
        StringBuffer tmpSb = new StringBuffer();
        // this does NOT get 'deleted' attributes!!! attStatus be null even though a value existed previously
        //EANAttribute attStatus = theItem.getAttribute(attrCode);

        RowSelectableTable itemTable = theItem.getEntityItemTable();
        String keyStr = theItem.getEntityType() + ":" + attrCode;
        tmpSb.append("<!-- ");
        try {
            int row = itemTable.getRowIndex(keyStr);
            if (row < 0) {
                row = itemTable.getRowIndex(keyStr + ":C");
            }
            if (row < 0) {
                row = itemTable.getRowIndex(keyStr + ":R");
            }
            if (row != -1)
            {
                EANAttribute attStatus = (EANAttribute) itemTable.getEANObject(row, 1);
                if (attStatus != null)
                {
                    AttributeChangeHistoryGroup histGrp = dbCurrent.getAttributeChangeHistoryGroup(profile, attStatus);
                    tmpSb.append("ChangeHistoryGroup for Attribute: "+attrCode);

                    // does it have a tran id in the time window, if so return histgrp
                    for (int i= histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
                    {
                        ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
                        tmpSb.append(NEWLINE+"AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+
                            " isValid: "+chi.isValid()+
                            " isActive: "+chi.isActive()+" value: "+
                            convertToHTML(chi.get("ATTVAL",true).toString(), false)+// avoid XML attr value in comments
                            " chgGrp: *"+chi.getChangeGroup()+"*  user: "+chi.getUser());
                        if (wasChangedByCG(chi)){
                            tranGrp = histGrp;}
                    }
                }
                else{
                    tmpSb.append("EANAttribute was null for "+attrCode+" in RowSelectableTable!");}
            }
            else{
                tmpSb.append("Row not found for "+attrCode+" in RowSelectableTable!");}
        } catch (Exception ee) {
            tmpSb.append("Exception getting "+attrCode+" from RowSelectableTable: "+ee.getMessage()+"");
        }

        tmpSb.append(" -->"+NEWLINE);
        if (tranGrp==null)  {// no match, output trace now
            sb.append(tmpSb.toString());}

        return tranGrp;
    }

    /**************************************************************************************
    * Check to see if this history item was changed by this tran id
    *
    * @param chi ChangeHistoryItem to check
    * @return boolean true if CG found
    */
    private boolean wasChangedByCG(ChangeHistoryItem chi)
    {
        return cgIdentifier.equals(chi.getChangeGroup().trim()); // WARNING: use "CHGGRPNAME" match now!
    }

    /**************************************************************************************
    * Find index of first record touched by this CG
    *
    * @param histGrp AttributeChangeHistoryGroup to check
    * @return int index of first record
    */
    private int getFirstChangeId(AttributeChangeHistoryGroup histGrp) // earliest change made by CG
    {
        int id =-1;
        for (int i= 0; i<histGrp.getChangeHistoryItemCount(); i++)
        {
            ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
            if (wasChangedByCG(chi)){
                id= i;
                break;
            }
        }
        return id;
    }
    /**************************************************************************************
    * Find index of last record touched by this CG
    *
    * @param histGrp AttributeChangeHistoryGroup to check
    * @return int index of last record
    */
    private int getLastChangeId(AttributeChangeHistoryGroup histGrp) // most recent change made by CG
    {
        int id = -1;
        for (int i= histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
        {
            ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
            if (wasChangedByCG(chi)){
                id= i;
                break;
            }
        }
        return id;
    }

    /*********************************************
    * if this is called, there is at least one tran id in the time window.. there may be multiple
    * changes with or without the tran id
    *
    * Original Value - is the value just before the first change tagged by tranid
    * Change Group Value - is the value for the last change tagged by tranid
    * Current Value - is the T2 value (we may want to consider T2 always = NOW())
    *
    * Value ValFrom ValTo   Tranid
    * 1 2004/1/1    2004/1/15   1
    * 2 2004/1/15   2004/2/2    1
    * 3 2004/2/2    2004/2/10   10005
    * 4 2004/2/10   2004/3/20   10001
    * 5 2004/3/20   2004/4/2    1
    * 6 2004/4/2    2004/5/12   10005
    * 7 2004/5/12   2004/6/9    1
    * 8 2004/6/9    9999/12/31  1
    *
    * Given a ChangeGroup with an entityid = 10005 and a ValFrom of 2004/4/3 for the last status change (i.e. not = open) ,
    * then I would propose the following report:
    *   Original value:  2
    *   Change Group Value: 6
    *   Current Value:  8
    *
    * @param theItem EntityItem with CG tagged changes
    * @param attrCode String with attribute code
    * @param lineCnt  int used for alternate row color
    * @param histGrp AttributeChangeHistoryGroup with CG tags
    * @return String with table info for this attribute
    */
    private String getAttributeInfo(EntityItem theItem, String attrCode, int lineCnt, AttributeChangeHistoryGroup histGrp)
    {
        /*
        9/16
        when do timestamps come into play? i don't think they do, anything tagged with a CG is valid and prev value is before
        first tag and current value may be after last tag

        to find previous deletions in the history:
        there is a "isActive" column
        it is always 'Active' unless it was deactivated
        Wayne Kehrli    i.e. the row was "deleted" whenever "isActive" is not 'Active'

        Wendy   looks like the chg grp stuff is working, are 'New', 'Changed', 'Deleted' the only values you expect to see in the 'change type' column?  even if the attr is 'recreated' or the entity 'restored'?
        Wayne Kehrli    restored seems like a good idea
        Wendy   so if an attribute was 'deactivated' and then a value is set, i use 'restored'?
        Wayne Kehrli    yep
        Wendy   k, that should also apply for restored entities.. i think the attr will look the same from the attr point of view
        Wayne Kehrli    yes
        Wendy   k
        */
        StringBuffer sb = new StringBuffer();
        String chgType=CHANGED; // reflects last CG action
        String chgGrpVal = null; // last value set by CG
        String origVal = BLANK; // orig value is one before the first CG value, not the one before current value!
        String curVal = BLANK;  // current value, set by anyone
        ChangeHistoryItem curChi = null;
        int firstId = 0;
        int lastId = 0;
        ChangeHistoryItem lastChi = null;
        String chgdBy = null;
        String dateChg = null;

        EANMetaAttribute ma = theItem.getEntityGroup().getMetaAttribute(attrCode);
        String desc = attrCode;
        String attrType="";
        if (ma != null)
        {
            desc = ma.getActualLongDescription();
            attrType=ma.getAttributeType();
        }

        // last one is current
        curChi = histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-1);
        // were there multiple changes in window.. find first and last for CG
        firstId = getFirstChangeId(histGrp); // earliest
        lastId = getLastChangeId(histGrp); // most recent

        sb.append("<!-- ChangeHistoryGroup for Attribute: "+attrCode);
        for (int i= histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
        {
            ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
            sb.append(NEWLINE+"AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+
                " isValid: "+chi.isValid()+
                " isActive: "+chi.isActive()+" value: "+
                convertToHTML(chi.get("ATTVAL",true).toString(), false)+ // avoid XML attr value in comments
                " chgGrp: *"+chi.getChangeGroup()+"*  user: "+chi.getUser());
        }
        sb.append(" -->"+NEWLINE);
        sb.append("<!-- firstId: "+firstId+" lastId: "+lastId+" ");
        if (firstId==-1) {
            firstId=0; }// error.. can't happen.. but just in case!!
        if (lastId==-1) {
            lastId=0; }// error.. can't happen.. but just in case!!

        // find first change tied to this ChgGrp
        if (firstId != 0) // attr had a value before
        {
            ChangeHistoryItem prevChi = histGrp.getChangeHistoryItem(firstId-1);
            // orig value may be a deleted value
            if(!prevChi.isActive())
            {
                sb.append("CG recreated this attr at index: "+firstId+" ");
                origVal = BLANK;
                chgType=RESTORED;
            }
            else
            {
                if (attrType.equals("X")){
                    origVal = prevChi.get("ATTVAL",true).toString();}
                else{
                    origVal = convertToHTML(prevChi.get("ATTVAL",true).toString()); }//xlate for html
            }
        }
        else {// created by CG
            // if CG created it and also changed it.. origValue =blank because CG created, but was last CG action
            // a change
            if (lastId==0) { // CG made no other changes
                chgType=NEW;}
        }

        // last CG change is displayed
        lastChi = histGrp.getChangeHistoryItem(lastId);
        chgdBy = lastChi.getUser()+" ["+lastChi.get("ROLE",true)+"]";
        dateChg = lastChi.getChangeDate();
        dateChg = dateChg.substring(0,dateChg.length()-L16); // drop time
        // last CG value may be a deleted value
        if (!lastChi.isActive())// CG deleted this attr
        {
            sb.append(" DELETION by this CG was found at index: "+lastId+NEWLINE);
            chgGrpVal = BLANK;
            chgType = DELETED;
        }
        else
        {
            if (lastId != 0) // attr had a value before
            {
                ChangeHistoryItem prevChi = histGrp.getChangeHistoryItem(lastId-1);
                // prev value may be a deleted value
                if(!prevChi.isActive())
                {
                    sb.append("CG recreated this attr at index: "+lastId+" ");
                    chgType=RESTORED;
                }
            }

            if (attrType.equals("X")) {
                chgGrpVal = lastChi.get("ATTVAL",true).toString(); }
            else {
                chgGrpVal = convertToHTML(lastChi.get("ATTVAL",true).toString()); }//xlate for html
        }

        if (!curChi.isValid()) //if false, attr was deleted
        {
            sb.append("Attr is not valid");
            curVal = BLANK;
            if (!wasChangedByCG(curChi)) // deleted by someone else
            {
                sb.append(" AND was NOT deleted by this CG (was CG: "+curChi.getChangeGroup()+")"+NEWLINE);
                chgType= chgType+"/"+DELETED+OTHER_INDICATOR; // CG did not delete it
            }
            else  // CG did it
            {
                chgGrpVal = BLANK;
                chgType = DELETED;
                sb.append(" and was deleted by this CG"+NEWLINE);
            }
        }
        else // the attribute is valid
        {
            if (attrType.equals("X")){
                curVal = curChi.get("ATTVAL",true).toString();}
            else{
                curVal = convertToHTML(curChi.get("ATTVAL",true).toString()); }//xlate for html
            if (!chgGrpVal.equals(curVal))// was changed by something after this chggrp
            {
                if (!lastChi.isActive()){// CG deleted this attr
                    chgType= chgType+"/"+RESTORED+OTHER_INDICATOR; }// CG was not last change
                else{
                    chgType= chgType+"/"+CHANGED+OTHER_INDICATOR;} // CG was not last change
            }
        }

        sb.append(" -->"+NEWLINE);

        sb.append("<tr class=\""+(lineCnt%2!=0?"even":"odd")+"\">");
        // change type
        sb.append("<td headers=\"changeType displayName\">"+chgType+"</td>");
        // attr desc
        sb.append("<td headers=\"attribute displayName\">"+desc+"</td>");
        // prev value
        sb.append("<td headers=\"original displayName\">"+origVal+"</td>");
        // chg grp value
        sb.append("<td headers=\"chgValue displayName\">"+chgGrpVal+"</td>");
        // current value
        sb.append("<td headers=\"current displayName\">"+curVal+"</td>");
        // date changed
        sb.append("<td nowrap headers=\"chgDate displayName\">"+dateChg+"</td>");
        // changed by
        sb.append("<td headers=\"chgBy displayName\">"+chgdBy+"</td>");
        sb.append("</tr>"+NEWLINE);

        replaceBraces(sb);

        return sb.toString();
    }

    static void replaceBraces(StringBuffer sb)
    {
        // {RESER} in display name breaks MessageFormat, replace them
        replaceText(sb,"{", OPEN_BRACE_REP);
    }
    static void restoreBraces(StringBuffer sb)
    {
        // {RESER} in display name breaks MessageFormat, restore them
        replaceText(sb,OPEN_BRACE_REP,"{");
    }
    private static void replaceText(StringBuffer sb,String oldStr, String newStr)
    {
        int id = 0;
        while(id != -1)
        {
            id = sb.toString().indexOf(oldStr,id);
            if (id != -1)
            {
                sb.replace(id, id+oldStr.length(),newStr);
            }
        }
    }

//============================================================================================================
//============================================================================================================
    /**************************************************************************************
    * Get navigate attributes for a deleted entity
    *
    * @param entityItem EntityItem to get display name for
    * @param attrDelimiter String to use between display attributes
    * @param byCG boolean true if CG did the deletion
    * @param errSb StringBuffer
    * @param justName boolean
    * @return String with display name
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    public String getDeletedDisplayName(EntityItem entityItem,  String attrDelimiter, boolean byCG,
        StringBuffer errSb, boolean justName)
        throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        StringBuffer sb = new StringBuffer();
        EANList metaList = null;
        RowSelectableTable itemTable = null;
        boolean first = true;

        if (!justName) {
            sb.append(DELETED);}

        // check hashtable to see if we already got this meta
        metaList = (EANList)metaTbl.get(entityItem.getEntityType());
        if (metaList==null)
        {
            EntityGroup eg =  new EntityGroup(null,dbCurrent,profile,entityItem.getEntityType(),"Navigate");
            metaList = eg.getMetaAttribute();
            metaTbl.put(entityItem.getEntityType(), metaList);
        }
        if (!justName)
        {
            if (!byCG) {
                sb.append(OTHER_INDICATOR); }
            sb.append(" - "+entityItem.getEntityGroup().getLongDescription()+": ");
        }

        // all attr even if attr is deactivated at time entity is deleted, have the same last timestamp!
        itemTable = entityItem.getEntityItemTable();
        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            String keyStr = "" + entityItem.getEntityType() + ":" + ma.getAttributeCode();
            try {
                int row = itemTable.getRowIndex(keyStr);
                if (row < 0) {
                    row = itemTable.getRowIndex(keyStr + ":C");
                }
                if (row < 0) {
                    row = itemTable.getRowIndex(keyStr + ":R");
                }
                if (row != -1)
                {
                    EANAttribute attStatus = (EANAttribute) itemTable.getEANObject(row, 1);
                    if (attStatus != null)
                    {
                        AttributeChangeHistoryGroup histGrp = dbCurrent.getAttributeChangeHistoryGroup(profile, attStatus);
                        if (histGrp.getChangeHistoryItemCount()>1)// was previous one deactivated?
                        {
                            // was the attr was deactivated before the entity was deleted?
                            ChangeHistoryItem chi1 = histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-2);
                            if(!chi1.isActive())
                            {
                                if (errSb!=null){
                                    errSb.append("<!-- "+ma.getAttributeCode()+" was DELETED before Entity -->"+NEWLINE);}
                                continue;
                            }
                        }
                        if (histGrp.getChangeHistoryItemCount()>0)
                        {
                            ChangeHistoryItem chi = histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-1);
                            String value = chi.get("ATTVAL",true).toString();
                            if (!first){
                                sb.append(attrDelimiter);}
                            sb.append(value);
                            first=false;
                        }
                    }
                    else{
                        if (errSb!=null){
                            errSb.append("<!--getDeletedDisplayName() EANAttribute was null for "+ma.getAttributeCode()+
                                " in RowSelectableTable!-->"+NEWLINE);}
                    }
                }
            } catch (Exception ee) {
                java.io.ByteArrayOutputStream bs = new java.io.ByteArrayOutputStream();
                java.io.PrintWriter pw = null;
                try{
                    pw = new java.io.PrintWriter(bs,true);
                    ee.printStackTrace(pw);
                    if (errSb!=null){
                        errSb.append("<!--getDeletedDisplayName() Exception getting "+ma.getAttributeCode()+
                            " from RowSelectableTable: "+ee.getMessage()+" "+bs.toString()+"-->"+NEWLINE);}
                }catch(Exception d)
                {
                    System.err.println(d.getMessage());
                }
                finally{
                    if (pw!=null) {
                        pw.close();}
                }
            }
        }
        replaceBraces(sb);

        return sb.toString();
    }

    /**************************************************************************************
    * Get navigate attributes and return display name in order indicated.
    *
    * @param entityItem EntityItem to get display name for
    * @param attrDelimiter String to use between display attributes
    * @param flagDelimiter String to use between display flag attribute
    * @return String with display name
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    public String getDisplayName(EntityItem entityItem,
        String attrDelimiter, String flagDelimiter)
        throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        StringBuffer sb = new StringBuffer();

        // check hashtable to see if we already got this meta
        EANList metaList = (EANList)metaTbl.get(entityItem.getEntityType());
        if (metaList==null)
        {
            EntityGroup eg =  new EntityGroup(null,dbCurrent,entityItem.getProfile(),entityItem.getEntityType(),"Navigate");
            metaList = eg.getMetaAttribute();
            metaTbl.put(entityItem.getEntityType(), metaList);
        }

        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            String value = getAttributeValue(entityItem, ma.getAttributeCode(), flagDelimiter, null);
            if (value !=null)
            {
                if (sb.length()>0){
                    sb.append(attrDelimiter);}
                sb.append(value);
            }
        }

        replaceBraces(sb);

        return sb.toString();
    }

    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @returns Vector of EntityItems
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue)
    {
        return getAttributeValue(item, attCode, delim, defValue, true, "eannounce");
    }

    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @param convert    boolean if true, value will be converted to valid html
    * @returns Vector of EntityItems
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue,
        boolean convert)
    {
        return getAttributeValue(item, attCode, delim, defValue, convert, "eannounce");
    }

    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @param convert    boolean if true, value will be converted to valid html
    * @param applicationName String with application name for getblob
    * @returns Vector of EntityItems
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue,
        boolean convert, String applicationName)
    {
        String value=defValue;
        EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(attCode);
        if (metaAttr==null){
            value= ("<span style=\"color:#c00;\">Attribute &quot;"+attCode+"&quot; NOT found in &quot;"+
                item.getEntityType()+"&quot; META data.</span>");
        }
        else {
            EANAttribute attr = item.getAttribute(attCode);
            if (attr != null){
                StringBuffer sb = new StringBuffer();
                if (attr instanceof EANFlagAttribute)
                {
                    // Get all the Flag values.
                    MetaFlag[] mfArray = (MetaFlag[]) attr.get();
                    for (int i = 0; i < mfArray.length; i++)
                    {
                        // get selection
                        if (mfArray[i].isSelected())
                        {
                            if (sb.length()>0) {
                                sb.append(delim); }
                            // convert all flag descriptions too
                            if (convert) {
                                sb.append(convertToHTML(mfArray[i].toString())); }
                            else {
                                sb.append(mfArray[i].toString()); }
                        }
                    }
                }
                else if (attr instanceof EANTextAttribute)
                {
                    // L and T and I text attributes must be converted to prevent invalid html
                    if (metaAttr.getAttributeType().equals("T") || metaAttr.getAttributeType().equals("L")
                        || metaAttr.getAttributeType().equals("I")) // FB52179
                    {
                        // convert the html special chars
                        if (convert) {
                            sb.append(convertToHTML(attr.get().toString())); }
                        else{
                            sb.append(attr.get().toString());}
                    }
                    else{
                        sb.append(attr.get().toString());}
                }
                else if (attr instanceof EANBlobAttribute)
                {
                    // only 'B' binary now
                    if (metaAttr.getAttributeType().equals("B"))
                    {
                        EANBlobAttribute blobAtt = (EANBlobAttribute) attr;
                        // sometimes the entire file name, rather than just
                        // the extension, is stored in the m_strBlobExtension
                        // variable.
                        if (blobAtt.getBlobExtension().toUpperCase().endsWith(".GIF") ||
                            blobAtt.getBlobExtension().toUpperCase().endsWith(".JPG"))
                        {
                            sb.append("<img src='/"+applicationName+"/GetBlobAttribute?entityID=" + item.getEntityID() +
                                "&entityType=" + item.getEntityType() +
                                "&attributeCode=" + attCode +
                                "' />");  // close tag needed for XML
                        }
                        else
                        {
                            // the HTML field is a link to a temp file generated by the
                            // GetBlobAttribute Servlet. note, we use ouputMode=F to indicate we
                            // want to generate a temp file and
                            // execute a browser-redirect to the temp file.
                            /*sb.append("<a href='/"+applicationName+"/GetBlobAttribute?outputMode=F"+
                                "&entityID=" + item.getEntityID()+
                                "&entityType=" + item.getEntityType()+
                                "&attributeCode=" +attCode);
                            sb.append("' />");
                            sb.append("Download this file for viewing.</a>");*/

                            // add support for other binary types FB53628:6FF425
                            sb.append("<form action=\"/"+applicationName+"/PokXMLDownload\" name=\""+item.getEntityType()
                                    +item.getEntityID()+attCode+"\" method=\"post\"> "+NEWLINE);
                            sb.append("<input type=\"hidden\" name=\"entityType\" value=\"" + item.getEntityType() +"\" />"+NEWLINE);
                            sb.append("<input type=\"hidden\" name=\"entityID\" value=\"" + item.getEntityID() +"\" />"+NEWLINE);
                            sb.append("<input type=\"hidden\" name=\"downloadType\" value=\"blob\" />"+NEWLINE);
                            sb.append("<input type=\"hidden\" name=\"attributeCode\" value=\""+attCode+"\" />"+NEWLINE);
                            sb.append("<input type=\"submit\" value=\"Down load\" />"+NEWLINE);
                            sb.append("</form>"+NEWLINE);

                            //sb.append("<span style=\"color:#c00;\">Blob Attribute for "+attCode+", extension: "+
                            //  blobAtt.getBlobExtension()+" is NOT yet supported</span>");
                        }
                    }
                    else{
                        sb.append("<span style=\"color:#c00;\">Blob Attribute type &quot;"+metaAttr.getAttributeType()+
                            "&quot; for "+attCode+" NOT yet supported</span>");}
                }

                if (sb.length()>0){
                    value = sb.toString();
                }
            }
        }

        return value;
    }

    /********************************************************************************
    * Convert string into valid html.  Special HTML characters are converted.
    *
    * @param txt    String to convert
    * @returns String
    */
    public static String convertToHTML(String txt)
    {
        return convertToHTML(txt, true);
    }

    /********************************************************************************
    * Convert string into valid html.  Special HTML characters are converted.
    *
    * @param txt    String to convert
    * @param doSpace boolean if false do not convert whitespace
    * @returns String
    */
    public static String convertToHTML(String txt, boolean doSpace)
    {
        String text = txt;
        if (txt != null)
        {
            StringBuffer htmlSB = new StringBuffer();
            StringCharacterIterator sci = new StringCharacterIterator(txt);
            char ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '<': // could be saved as &lt; also. this will be &#60;
                case '>': // could be saved as &gt; also. this will be &#62;
                case '"': // could be saved as &quot; also. this will be &#34;
                // this should be included too, but left out to be consistent with west coast
                //case '&': // ignore entity references such as &lt; if user typed it, user will see it
                          // could be saved as &amp; also. this will be &#38;
                    htmlSB.append("&#"+((int)ch)+";");
                    break;
                case '\n':  // maintain new lines
                    if (doSpace){
                        htmlSB.append("<br />");}
                    else{
                        htmlSB.append(ch);}
                    break;
                default:
                    if (doSpace && Character.isSpaceChar(ch))// check for unicode space character
                    {
                        htmlSB.append("&#32;"); // this fails because extra whitespace, even &#32;, is discarded
                        // but left to be consistent with WestCoast code
    //                      htmlSB.append("&nbsp;"); // this will correctly maintain spaces
                    }
                    else{
                        htmlSB.append(ch);}
                    break;
                }
                ch = sci.next();
            }
            text = htmlSB.toString();
        }

        return text;
    }

    /********************************************************************************
    * Get attribute description
    * @param entityGroup EntityGroup
    * @param attrCode String attribute code to get description for
    * @param defValue String with default description
    *
    * @returns String
    */
    public static String getAttributeDescription(EntityGroup entityGroup, String attrCode, String defValue)
    {
        String desc = defValue;
        if (entityGroup!=null){
            EANMetaAttribute ma = entityGroup.getMetaAttribute(attrCode);
            if (ma != null){
                desc = ma.getActualLongDescription();}
        }

        return desc;
    }

    /*****************************************************************************
    * Get the current Flag Value for the specified attribute, null if not set
    *
    * @param entityItem EntityItem
    * @param attrCode String attribute code to get value for
    * @returns String attribute flag code
    */
    public static String getAttributeFlagValue(EntityItem entityItem, String attrCode)
    {
        String value=null;
        // Multi-flag values will be separated by |
        EANAttribute attr = entityItem.getAttribute(attrCode);
        if (attr != null){
            if (attr instanceof EANFlagAttribute)
            {
                StringBuffer sb = new StringBuffer();

                // Get the selected Flag codes.
                MetaFlag[] mfArray = (MetaFlag[]) attr.get();
                for (int i = 0; i < mfArray.length; i++)
                {
                    // get selection
                    if (mfArray[i].isSelected())
                    {
                        if (sb.length()>0){
                            sb.append(DELIMITER);}
                        sb.append(mfArray[i].getFlagCode());
                    }
                }
                value= sb.toString();
            }
        }

        return value;
    }

    /*****************************************************************************
    * output list for debug
    *
    * @param list EntityList
    * @returns String
    */
    public static String outputList(EntityList list) // debug
    {
        StringBuffer sb = new StringBuffer();
        EntityGroup peg =list.getParentEntityGroup();
        if (peg!=null)
        {
            sb.append(peg.getEntityType()+" : "+peg.getEntityItemCount()+" Parent entity items. ");
            if (peg.getEntityItemCount()>0)
            {
                sb.append("IDs(");
                for (int e=0; e<peg.getEntityItemCount(); e++){
                    sb.append(" "+peg.getEntityItem(e).getEntityID());}
                sb.append(")");
            }
            sb.append(NEWLINE);
        }

        for (int i=0; i<list.getEntityGroupCount(); i++)
        {
            EntityGroup eg =list.getEntityGroup(i);
            sb.append(eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
            if (eg.getEntityItemCount()>0)
            {
                sb.append("IDs(");
                for (int e=0; e<eg.getEntityItemCount(); e++){
                    sb.append(" "+eg.getEntityItem(e).getEntityID());}
                sb.append(")");
            }
            sb.append(NEWLINE);
        }
        return sb.toString();
    }

    /*************************************************************************************
    * Convenience classes
    **************************************************************************************/
    private static class SortedCG  implements Comparable
    {
        private String eType;
        private int eId;
        private Vector children = new Vector();
        private String sortKey, sortKey2;
        private String html;
        private static final String[] CSSLVL = new String[]
            //{"lstr1","eann_bg_t1","eann_bg_t2","eann_bg_t3","eann_bg_t4"};
        {"background-color:#69c;","background-color:#fe2;",
            "background-color:#bd6;","background-color:#f90;"};
        SortedCG(EntityItem item, String s, String html)
        {
            eType = item.getEntityType();
            eId = item.getEntityID();
            sortKey = s;
            sortKey2 = s;
            this.html=html;
        }
        String getSortKey() { return sortKey; }
        void setSortKey(String s) { sortKey = s; }
        void setAlternateSortKey(String s) { sortKey2 = s; }
        void swapSortKey()
        {
            String tmp = sortKey;
            sortKey = sortKey2;
            sortKey2 = tmp;
        }
        String getKeys()
        {
            StringBuffer sb = new StringBuffer(eType+":"+eId);
            for (int i=0; i<children.size(); i++)
            {
                if (i==0){
                    sb.append(" from [");}
                sb.append(" "+((SortedCG)children.elementAt(i)).getKeys());
            }
            if (children.size()>0) {
                sb.append("] ");}

            return sb.toString();
        }
        int getEntityId() { return eId; }
        void addChild(SortedCG scg) {children.addElement(scg);}
        String getStructure(int lvl)
        {
            MessageFormat msgf = new MessageFormat(html);
            StringBuffer sb = null;
            Object args[] = new Object[2];
            args[0] = CSSLVL[lvl];
            args[1] = " title=\""+getKeys()+"\"";

            sb = new StringBuffer(msgf.format(args));
            for (int i=0; i<children.size(); i++)
            {
                sb.append(((SortedCG)children.elementAt(i)).getStructure(lvl+1));
            }

            if (lvl==0) {// top level
                restoreBraces(sb);}  // replace any braces, they break MessageFormat

            return sb.toString();
        }
        public String toString()
        {
            StringBuffer sb = new StringBuffer(eType+":"+eId+" ["+sortKey+"]");
            if (!sortKey2.equals(sortKey)) {
                sb.append("["+sortKey2+"]");}
            if (children.size()>0) {
                sb.append(" (");}
            for (int i=0; i<children.size(); i++)
            {
                sb.append(((SortedCG)children.elementAt(i)).toString());
            }
            if (children.size()>0) {
                sb.append(") ");}
            return sb.toString();
        }

        void dereference()
        {
            if (children ==null) {
                return; } // objects are shared, so this may have been already called
            for (int i=0; i<children.size(); i++)
            {
                SortedCG scg = (SortedCG)children.elementAt(i);
                scg.dereference();
            }

            children.clear();
            children = null;
            html = null;
            sortKey=null;
            sortKey2=null;
            eType=null;
        }
        public int compareTo(Object o) // used by Collections.sort()
        {
            SortedCG sma = (SortedCG)o;
            // sort by key
            return (sortKey).compareTo(sma.sortKey);
        }
        boolean matches(EntityItem item)
        {
            return (eType.equals(item.getEntityType()) && eId==item.getEntityID());
        }
        void sortIt()
        {
            for (int i=0; i<children.size(); i++)
            {
                ((SortedCG)children.elementAt(i)).sortIt();
            }
            Collections.sort(children);
        }
        public boolean equals(Object obj) // used by Vector.contains()
        {
            SortedCG sma = (SortedCG)obj;
            return (eType.equals(sma.eType) && eId==sma.eId);
        }
        /**
        Define a 'hashCode()' method in the class.  This method should return a number
        based on the same fields that the 'equals()' method uses to test for equality.
        Ideally 'hashCode()' should produce the same value for two objects only when
        'equals()' return true when called on them.
        * @return int
        */
        public int hashCode() {
            return eType.hashCode()+eId;
        }
    }
}
