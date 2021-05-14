// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
 * B.   Product Structure (MTM) Changes
 *
 * The columns are:
 *
 * Heading                  Description
 * Date/Time of Change      ValFrom of the Relator = PRODSTRUCT
 * Change Type              Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
 * Last Editor              From the Entity (First 10 characters)
 * MTM                      MODEL.MACHTYPEATR &-& MODEL.MODELATR
 * FC                       FEATURE.FEATURECODE
 * Ann Date Override        PRODSTRUCT.ANNDATE
 * Min Req                  PRODSTRUCT.SYSTEMMIN
 * Max                      PRODSTRUCT.SYSTEMMAX
 * OS Lev (Chg)             PRODSTRUCT.OSLEVEL (If the list is changed, then Change; else blank)
 * Order Type               PRODSTRUCT.ORDERCODE
 * CSU                      PRODSTRUCT.INSTALL CR042605498 added this
 * FM                       (Derived  see below)
 * Name                     FEATURE.COMNAME
 * Comments                 PRODSTRUCT.COMMENTS CR042605498 added this
 *
 * FM is derived as follows:
 *
 * Use entity FEATURE to find a matching MAPFEATURE. The matching is based on INVENTORYGROUP, HWFCCAT, and HWFCSUBCAT.
 * Given a matching entity, then this gives FMGROUPCODE and FMSUBGROUPCODE. Concatenate these two values.
 * This yields a two character code.
 *
 * This class handles an MTM that was created and deleted inside one interval
 * The PRODSTRUCT was not linked at fromtime or curtime (linkitem was null)
 *
 *@author     Wendy Stimpson
 *@created    Oct 6, 2004
 */
// $Log: FMChgMTMDelSet.java,v $
// Revision 1.11  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.10  2005/06/01 18:19:48  wendy
// Limited usage of AttributeChangeHistory and made changes for MN24140028
//
// Revision 1.9  2005/05/06 18:37:40  wendy
// CR042605498 approved
//
// Revision 1.8  2005/05/05 14:01:52  wendy
// Setup for CR042605498
//
// Revision 1.7  2004/11/09 17:25:18  wendy
// Added support for restored entities.
//
// Revision 1.6  2004/11/08 19:26:09  wendy
// comments
//
// Revision 1.5  2004/11/03 12:22:26  wendy
// Added hanging onto EntityChangeHistoryGroup
//
// Revision 1.4  2004/10/21 18:18:14  wendy
// Added check for match on relator returned in extract
//
// Revision 1.3  2004/10/19 19:59:24  wendy
// Correct setting profile DTS
//
// Revision 1.2  2004/10/19 16:49:44  wendy
// Reorganize for SD
//
// Revision 1.1  2004/10/15 23:38:48  wendy
// Init for FM Chg Log application
//
class FMChgMTMDelSet extends FMChgMTMSet
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.11 $";
    private int prodId;
    private int rootId;

    /********************************************************************************
    * Constructor
    * @param db Database object
    * @param prof Profile object for the current time
    * @param fm FMChgLogGen object driver for file generation
    * @param prodIdStr String with PRODSTRUCT entity id
    * @param rootId2 int with root FEATURE entity id
    * @param ftime String with dts of time 1 based on minimum date
    * @param time2 String with end timestamp of interval
    */
    FMChgMTMDelSet(Database db, Profile prof, FMChgLogGen fm, String prodIdStr, int rootId2, String ftime, String time2)
    {
        super(db, prof, fm, null, null, ftime, time2);
        prodId = Integer.parseInt(prodIdStr);
        this.rootId = rootId2;
    }

    /********************************************************************************
    * Calculate output for MTM structure created and deleted within the interval
    */
    void calculateOutput() throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        EntityItem prodItem = null;
        getLogGen().trace(D.EBUG_INFO,false,"FMChgMTMDelSet.calculateOutput() entered for ADDED AND REMOVED curItem PRODSTRUCT"+
            prodId+" root FEATURE"+rootId);

        prodItem = new EntityItem(null, getProfile(), "PRODSTRUCT", prodId);
        calculateDelMTMOutput(prodItem, "FEATURE", rootId);
    }
}
