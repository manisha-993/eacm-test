// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.isgfm;

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
// Revision 1.4  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.3  2006/04/07 13:24:41  wendy
// Add setting key
//
// Revision 1.2  2006/01/25 19:26:03  wendy
// AHE copyright
//
// Revision 1.1  2006/01/24 18:39:15  wendy
// Init for AHE
//
//
class FMChgMTMDelSet extends FMChgMTMSet
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.4 $";
    private int prodId;
    private int rootId;

    /********************************************************************************
    * Constructor
    * @param prodIdStr String with PRODSTRUCT entity id
    * @param rootId2 int with root FEATURE entity id
    * @param ftime String with dts of time 1 based on minimum date
    * @param time2 String with end timestamp of interval
     * @throws MiddlewareRequestException 
    */
    FMChgMTMDelSet(String prodIdStr, int rootId2, String ftime, String time2) throws MiddlewareRequestException
    {
        super(null, null, ftime, time2);
        prodId = Integer.parseInt(prodIdStr);
        this.rootId = rootId2;
    }

    /********************************************************************************
    * Calculate output for MTM structure created and deleted within the interval
    */
    void calculateOutput(Database dbCurrent,Profile profile,FMChgLogGen logGen) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        EntityItem prodItem = null;
        logGen.trace(D.EBUG_INFO,false,"FMChgMTMDelSet.calculateOutput() entered for ADDED AND REMOVED curItem PRODSTRUCT"+
            prodId+" root FEATURE"+rootId);

        prodItem = new EntityItem(null, profile, "PRODSTRUCT", prodId);
        calculateDelMTMOutput(dbCurrent,profile,logGen,prodItem, "FEATURE", rootId);
    }

    /********************************************************************************
    * Get key FEATURE:PRODSTRUCT
    * @return String
    */
    String getKey() {
        return "FEATURE"+rootId+":PRODSTRUCT"+prodId;
    }
}
