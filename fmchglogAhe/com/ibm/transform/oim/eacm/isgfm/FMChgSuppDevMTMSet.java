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
* Heading               Description
* Date/Time of Change   ValFrom of the Relator = MDLCGOSMDL
* Change Type           Add = New in this time period,Change = More than one record in this period,Remove = Currently Deactivated
* Last Editor           From the Relator( First 10 characters)
* MTM                   MDLCGMDL->MODELa.MACHTYPEATR &-& MODELa.MODELATR
* SptDev MTM            MDLCGOSMDL->MODELc.MACHTYPEATR &-&  MODELc.MODELATR
* Announce Date         MDLCGOSMDL.ANNDATE
* FM                    Derived  see below
* Name                  MDLCGOSMDL->MODELc.INTERNALNAME
*
* FM is derived as follows:
*
* Use entity MDLCGOSMDL->MODELc.to find a matching MAPSUPPDEVICE. The matching is based on
* INVENTORYGROUP from this MODEL’s parent MACHTYPE and COMPATDVCCAT from the MODEL itself.
* Given a matching entity MAPSUPPDEVICE using INVENTORYGROUP and FMGROUP, then this gives FMGROUPCODE.
* This yields a one character code.
*
* NOTE: There could be multiple MODELa for one MODELc, they may or may not be the same invgrp
* Changes of invgrp (MODELa) will not be captured.  Only the current MODELa will be used unless all MODELa
* are deleted/removed.  In that case, MODELa at fromtime will be used.
*
*@author     Wendy Stimpson
*@created    Oct 10, 2004
*/
// $Log: FMChgSuppDevMTMSet.java,v $
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
class FMChgSuppDevMTMSet extends FMChgSet
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.4 $";
    private String mtm="";
    // if SD MTM was created and deleted within an interval, the MODELa item must be found and used
    // before list is dereferenced
    private String sdmtm="";
    private String annDate="";
    private String fm="";
    private String name="";
    private String thekey="";

    private static final int MTM_COL = 8;
    private static final int SDMTM_COL = 8;
    private static final int ANNDATE_COL = 10;
    private static final int FM_COL = 1;
    private static final int NAME_COL = 80;
    private static final String EXTRACTNAME = "EX3MDLCGMDL";
    private static final String NULL_MDLA = "NULLMDLa";

    /********************************************************************************
    * Constructor from and cur items are MDLCGOSMDL items used to find if this item has changes to output
    * @param fitem EntityItem for fromtime interval
    * @param citem EntityItem for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgSuppDevMTMSet(EntityItem fitem, EntityItem citem,
        String time1, String time2) 
    {
        super(fitem, citem, time1, time2);
        if (getCurItem()!=null) {
            // root:relator
            thekey = getCurItem().getDownLink(0).getKey()+":"+getCurItem().getKey();
        }else if (getFromItem()!=null) {
            // root:relator
            thekey = getFromItem().getDownLink(0).getKey()+":"+getFromItem().getKey();
        }
    }

    /********************************************************************************
    * Constructor used to duplicate this ChgSet for an inventorygroup (MODELa)
    * @param copy FMChgSuppDevMTMSet object to duplicate
    * @param curModela EntityItem MODELa
    * @param fromModela EntityItem MODELa
    */
    FMChgSuppDevMTMSet(FMChgSuppDevMTMSet copy, EntityItem curModela, EntityItem fromModela)
    {
        super(copy);

		thekey = copy.thekey;

		// sortkey
		mtm=copy.mtm;
		sdmtm= copy.sdmtm;

        //make sure this doesn't interfere with uniqueness chk for chgset per invgrp
        if (curModela!=null) {
            // root:relator
            thekey = thekey+":"+curModela.getKey();
        }else if (fromModela!=null) {
            // root:relator
            thekey = thekey+":"+fromModela.getKey();
        }
        try{
			replaceModela(curModela,fromModela);
		}catch(Exception e){
			System.err.println("Exception replacing MODELa "+e);
		}
    }

    /********************************************************************************
    * Replace MODELa MTM in output string buffer, this is not part of the change criteria
    * the ChgSet was instantiated, checked for valid changes and then duplicated for each
    * invgrp.  MODELa controls the invgrp and is used for the MTM column
    *
    * @param curModela EntityItem MODELa could be null
    * @param fromModela EntityItem MODELa bould be null
    */
    private void replaceModela(EntityItem curModela, EntityItem fromModela) throws Exception
    {
        // replace NULLMDLa in output buffer with modela based on changetype
        String chgType = getChgType();
        if (!chgType.equals(FMChgLogGen.NOCHG)){
			// if there are 2 occurances, the first is from, next is current.. if one, just use current
		    int index = getOutputSb().toString().indexOf(NULL_MDLA);
		    int lastindex = getOutputSb().toString().lastIndexOf(NULL_MDLA);
		    if (index==lastindex) {
	            if (chgType.equals(FMChgLogGen.REMOVED)){
		            mtm = FMChgLogGen.getAttributeValue(fromModela, "MACHTYPEATR", ", ", "")+  //MODELa.MACHTYPEATR
		        	    "-"+FMChgLogGen.getAttributeValue(fromModela, "MODELATR", ", ", "");   //MODELa.MODELATR
				}else{
		            mtm = FMChgLogGen.getAttributeValue(curModela, "MACHTYPEATR", ", ", "")+  //MODELa.MACHTYPEATR
		        	    "-"+FMChgLogGen.getAttributeValue(curModela, "MODELATR", ", ", "");   //MODELa.MODELATR
				}
			    if (index !=-1) {
					getOutputSb().replace(index, index+MTM_COL,formatToWidth(mtm,MTM_COL));
				}
			}else {
	            String frommdlAmtm = FMChgLogGen.getAttributeValue(fromModela, "MACHTYPEATR", ", ", "")+  //MODELa.MACHTYPEATR
		        	    "-"+FMChgLogGen.getAttributeValue(fromModela, "MODELATR", ", ", "");   //MODELa.MODELATR

	            mtm = FMChgLogGen.getAttributeValue(curModela, "MACHTYPEATR", ", ", "")+  //MODELa.MACHTYPEATR
		        	    "-"+FMChgLogGen.getAttributeValue(curModela, "MODELATR", ", ", "");   //MODELa.MODELATR
			    if (index !=-1) {
					getOutputSb().replace(index, index+MTM_COL,formatToWidth(frommdlAmtm,MTM_COL));
				}
			    if (lastindex !=-1) {
					getOutputSb().replace(lastindex, lastindex+MTM_COL,formatToWidth(mtm,MTM_COL));
				}
			}
		}
	}

    /********************************************************************************
    * Get sort key for collections.sort()
    * @return String
    */
    String getSortKey() { return mtm+sdmtm;}

    /********************************************************************************
    * Release memory
    */
    void dereference()
    {
        super.dereference();
        mtm=null;
        sdmtm=null;
        annDate=null;
        fm=null;
        name=null;
        thekey=null;
    }
    /********************************************************************************
    * Restore default values
    */
    protected void reset() // reset for old vs new or add vs change records
    {
        super.reset();
        mtm="";
        sdmtm="";
        annDate="";
        fm="";
        name="";
    }

    /********************************************************************************
    * Find and set values for MODELa
    */
    protected void setModelAttr()
    {
		mtm=NULL_MDLA; // use this as a placeholder until the invgrp is known
    }

    /********************************************************************************
    * Find and set values for MDLCGOSMDL
    * @param devItem EntityItem
    * @throws java.lang.Exception
    */
    protected void setDevAttr(EntityItem devItem) throws Exception
    {
        annDate = FMChgLogGen.getAttributeValue(devItem, "ANNDATE", ", ", "");
    }
    /********************************************************************************
    * Find and set values for MODELc
    * @param mdlcItem EntityItem
    * @throws java.lang.Exception
    */
    protected void setSDAttr(FMChgLogGen logGen,EntityItem mdlcItem) throws Exception
    {
        sdmtm = FMChgLogGen.getAttributeValue(mdlcItem, "MACHTYPEATR", ", ", "")+"-"+
            FMChgLogGen.getAttributeValue(mdlcItem, "MODELATR", ", ", "");
        name = //getKey();
        	FMChgLogGen.getAttributeValue(mdlcItem, "INTERNALNAME", ", ", "");
        // derive FM
        fm=deriveSDFM(logGen,mdlcItem);
    }

    /********************************************************************************
    * Set output for changes in MTM structure using col widths
    Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *Col Width: 8   8       10      1   80
    */
    protected void setOutput()
    {
        getOutputSb().append(
            getDateChg()+COL_DELIMITER+  // ValFrom of the Entity
            getChangeType()+COL_DELIMITER+  //Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
            getLastEditor()+COL_DELIMITER+  //From the Entity (First 10 characters) not attribute
            formatToWidth(mtm,MTM_COL)+COL_DELIMITER+     //MODELa.MACHTYPEATR & - & MODELa.MODELATR
            formatToWidth(sdmtm,SDMTM_COL)+COL_DELIMITER+ //MODELc.MACHTYPEATR & MODELc.MODELATR
            formatToWidth(annDate,ANNDATE_COL)+COL_DELIMITER+   //MDLCGOSMDL.ANNDATE
            formatToWidth(fm,FM_COL)+COL_DELIMITER+ //derived
            formatToWidth(name,NAME_COL)+   //MODELc.INTERNALNAME
            FMChgLog.NEWLINE);
    }
    /********************************************************************************
    * Check to see if inventory group was changed in this interval
    * @return boolean
    */
    protected boolean invGrpChanged(FMChgLogGen logGen)
    {
        return false;
    }
    /********************************************************************************
    * Get column headers
    *Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *   SptDev  Announce
    *MTM MTM     Date    FM  Name
    *8   8       10      1   80
    */
    static String getColumnHeader()
    {
        String row1 = getDateTypeEditorHeaderRow1()+
            formatToWidth("",MTM_COL)+COL_DELIMITER+ //MTM
            formatToWidth("SptDev",SDMTM_COL)+COL_DELIMITER+ //SDMTM
            formatToWidth("Announce",ANNDATE_COL)+COL_DELIMITER+ //ANNDATE
            formatToWidth("",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("",NAME_COL)+FMChgLog.NEWLINE; //name

        String row2 = getDateTypeEditorHeaderRow2()+
            formatToWidth("MTM",MTM_COL)+COL_DELIMITER+ //MTM
            formatToWidth("MTM",SDMTM_COL)+COL_DELIMITER+ //SDMTM
            formatToWidth("Date",ANNDATE_COL)+COL_DELIMITER+ //ANNDATE
            formatToWidth("FM",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("Name",NAME_COL)+FMChgLog.NEWLINE; //name

        return row1+row2;
    }
    static String getSectionTitle() { return "Supported Device Matrix Changes";}
    static String getSectionInfo() { return "The following Supported Devices were added (Add), changed (Change) or deleted (Remove) from the MTM";}
    static String getNoneFndText() { return "No Supported Device Matrix changes found."; }

    /********************************************************************************
    * Get row values
    * @return String with all row values concatenated, used for any changes check
    */
    protected String getRowValues(FMChgLogGen logGen)
    {
//      return mtm+sdmtm+annDate+fm+formatToWidth(name,NAME_COL).trim();
        return annDate; // only use values from the relator for changes
    }
    /********************************************************************************
    * Get row values, untruncated!
    * @return String with all row values concatenated, used for debug
    */
    protected String getRowFullValues(FMChgLogGen logGen)
    {
        return mtm+sdmtm+annDate+fm+name;
    }
    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    protected void setRowValues(FMChgLogGen logGen,EntityItem theItem) throws Exception
    {
        // stuff from MDLCGOSMDL
        setDevAttr(theItem);
        // stuff from MODELc
        logGen.trace(D.EBUG_DETAIL,false,"FMChgSuppDevMTMSet.setRowValues() MODELc: "+theItem.getDownLink(0).getKey()+
            " on "+theItem.getProfile().getValOn());
        setSDAttr(logGen,(EntityItem)theItem.getDownLink(0));
        // stuff from MODELa has to be obtained after invgrp is found
        setModelAttr();
    }
    /********************************************************************************
    * Get extract name to use for structure check or just a pull for deleted roots
    * @return String
    */
    String getExtractName() { return EXTRACTNAME;}
    /********************************************************************************
    * Get root for structure, MTM uses FEATURE, SD uses MODELc
    * @return EntityItem
    */
    protected EntityItem getRootItem() {
        return (EntityItem)getFromItem().getDownLink(0);
    }

    /********************************************************************************
    * Calculate output for changes in MTM structure
    * Because the invgrp to output this information under is controlled by
    * MODELa and is several relators away, it is possible to have invgrp changes
    * that impact this suppdev (MDLCGOSMDL) but are not in MDLCGOSMDL.  This allows
    * output of this infomation without looking at the MDLCGOSMDL change history.
    */
    void calculateOutput(Database db,Profile profile,FMChgLogGen logGen) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        calculateMTMOutput(db,profile,logGen);
    }

    /********************************************************************************
    * Get key  MODELc:MDLCGOSMDL:MODELa
    * @return String
    */
    String getKey() { return thekey; }
}
