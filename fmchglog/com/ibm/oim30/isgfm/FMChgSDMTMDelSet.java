// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
* B.    Supported Device Matrix Changes
*
* The columns are:
*
* Heading                   Description
* Date/Time of Change       ValFrom of the Relator = DEVSUPPORT
* Change Type               Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
* Last Editor               From the Entity (First 10 characters)
* MTM                       MODEL.MACHTYPEATR &-& MODEL.MODELATR
* SptDev MTM                SUPPDEVICE.MACHTYPESD &-& SUPPDEVICE.MODELATR
* Announce Date             DEVSUPPORT.ANNDATE
* FM                        Derived  see below
* Name                      SUPPDEVICE.INTERNALNAME
*
* FM is derived as follows:
*
* Use entity SUPPDEVICE to find a matching MAPSUPPDEVICE. The matching is based on INVENTORYGROUP,
* and FMGROUP. Given a matching entity, then this gives FMGROUPCODE. This yields a one character code.
*
* This class handles an MTM that was created and deleted inside one interval
*
*@author     Wendy Stimpson
*@created    Oct 10, 2004
*/
// $Log: FMChgSDMTMDelSet.java,v $
// Revision 1.10  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.9  2005/06/01 18:19:48  wendy
// Limited usage of AttributeChangeHistory and made changes for MN24140028
//
// Revision 1.8  2005/05/05 14:01:51  wendy
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
class FMChgSDMTMDelSet extends FMChgSuppDevMTMSet
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.10 $";
    private int devSupId;
    private int rootId;

    /********************************************************************************
    * Constructor
    * @param db Database object
    * @param prof Profile object for the current time
    * @param fm FMChgLogGen object driver for file generation
    * @param dsIdStr String with DEVSUPPORT entity id
    * @param rootId2 int with root SUPPDEVICE entity id
    * @param ftime String with dts of time 1 based on minimum date
    * @param time2 String with end timestamp of interval
    */
    FMChgSDMTMDelSet(Database db, Profile prof, FMChgLogGen fm, String dsIdStr, int rootId2, String ftime, String time2)
    {
        super(db, prof, fm, null, null, ftime, time2);
        devSupId = Integer.parseInt(dsIdStr);
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
        EntityItem devItem = null;
        getLogGen().trace(D.EBUG_INFO,false,"FMChgSDMTMDelSet.calculateOutput() entered for ADDED AND REMOVED curItem DEVSUPPORT"+
            devSupId+" root SUPPDEVICE"+rootId);

//-- Entity:SUPPDEVICE<----Relator:DEVSUPPORT<-Entity:MODEL
        devItem = new EntityItem(null, getProfile(), "DEVSUPPORT", devSupId);
        calculateDelMTMOutput(devItem, "SUPPDEVICE", rootId);
    }
}
