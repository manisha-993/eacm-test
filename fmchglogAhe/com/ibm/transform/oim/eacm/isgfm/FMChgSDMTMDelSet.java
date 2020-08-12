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
// Revision 1.4  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.3  2006/04/03 22:04:38  wendy
// OIM3.0b datamodel and Supported Device changes
//
// Revision 1.2  2006/01/25 19:26:03  wendy
// AHE copyright
//
// Revision 1.1  2006/01/24 18:39:15  wendy
// Init for AHE
//
//
class FMChgSDMTMDelSet extends FMChgSuppDevMTMSet
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.4 $";
    private int devSupId;
    private int rootId;
    private String mdlkey="";
    private String[] adjDateArray=null; // adjusted timestamps [0] is create, [1] is delete

    /********************************************************************************
    * Constructor
    * @param dsIdStr String with MDLCGOSMDL entity id
    * @param rootId2 int with root MODEL entity id
    * @param ftime String with dts of time 1 based on minimum date
    * @param time2 String with end timestamp of interval
     * @throws MiddlewareRequestException 
    */
    FMChgSDMTMDelSet(String dsIdStr, int rootId2,
    	String ftime, String time2) throws MiddlewareRequestException
    {
        super(null, null, ftime, time2);
        devSupId = Integer.parseInt(dsIdStr);
        this.rootId = rootId2;
    }

    /********************************************************************************
    * Constructor used to duplicate this ChgSet for an inventorygroup (MODELa)
    * @param copy FMChgSDMTMDelSet object to duplicate
    * @param curmodela EntityItem MODELa found using adjusted delete timestamps, may be null
    * @param frommodela EntityItem MODELa found using adjusted create timestamps, may be null
    */
    FMChgSDMTMDelSet(FMChgSDMTMDelSet copy, EntityItem curmodela, EntityItem frommodela)
    {
        super(copy,curmodela, frommodela);
        devSupId = copy.devSupId;
        rootId = copy.rootId;
        if (curmodela!=null) {  // neither should be null.. just a check to be safe
            // root:relator
            mdlkey = ":"+curmodela.getKey();
        }else if (frommodela!=null) {
            // root:relator
            mdlkey = ":"+frommodela.getKey();
        }
    }

    /********************************************************************************
    * Calculate output for MTM structure created and deleted within the interval
    */
    void calculateOutput(Database dbCurrent,Profile profile,FMChgLogGen logGen) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        EntityItem devItem = null;
        logGen.trace(D.EBUG_INFO,false,"FMChgSDMTMDelSet.calculateOutput() entered for ADDED AND REMOVED curItem MDLCGOSMDL"+
            devSupId+" root MODEL"+rootId);

//-- Entity:MODELc<----Relator:MDLCGOSMDL<---Entity:MODELCGOS
        devItem = new EntityItem(null, profile, "MDLCGOSMDL", devSupId);
        adjDateArray = calculateDelMTMOutput(dbCurrent,profile,logGen,devItem, "MODEL", rootId);
    }
    /********************************************************************************
    * Get the dates used for the extract, this can be used to get to the MODELa for invgrp
    * adjusted timestamps [0] is create, [1] is delete
    * @return String[]
    */
    String[] getExtractDates() { return adjDateArray;}
    /********************************************************************************
    * Get key MODELc:MDLCGOSMDL:MODELa
    * @return String
    */
    String getKey() {
        return "MODEL"+rootId+":MDLCGOSMDL"+devSupId+mdlkey;
    }
}
