// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2017  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.isgfm;

import java.io.Serializable;
import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
 * Abstract class to generate output for one Entity during a change interval
 * It has 2 EntityItems, one for the start of the interval and one for the current time
 *
 * CR042605498
 * 1. For the Product Structure (PRODSTRUCT) section
 *  Only report changes for the tracked attributes on PRODSTRUCT
 *  The current information (attributes / data elements) shown for reference will continue to be shown
 *  Machine Type, Model, Feature Code
 * 2. The first row will show the values prior to the change and the second row will show the values after the change
 * 3. The first row (data prior to the change) will not show the userid
 * 4. The date/time stamp will only be accurate (show) to the minute YYY-MM-DD HH:MM
 * 5. The date/time stamp for the change will be for the last change to any attribute on the entity
 * 6. RFA Changes Section - add Price File Name (INVNAME) - this is in addition to the Marketing Name
 * 7. Global Withdrawal Section
 *  Only include if either the Global Withdrawal Announce Date (WITHDRAWANNDATE_T) or the Global Withdrawal Date Effective (WITHDRAWDATEEFF_T) is changed
 * 8. Add Comments to the Feature Inventory Group section (FEATURE.COMMENTS) and to the Product Structure Section (PRODSTRUCT.COMMENTS). Use Comments field with 80 Max chars.
 * 9. Add column called "CSU" from Customer Setup from Product Structure and report any changes made.
 *       Current values are CE, CIF, Does Not Apply and N/A. (Truncate to 4-chars)
 *       This must be reported in the Product Structure Changes Section of the Change Log
 *@author     Wendy Stimpson
 *@created    Oct 6, 2004
 */
// $Log: FMChgSet.java,v $
// Revision 1.6  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.5  2013/02/07 12:14:05  wendy
// CQ236604 filter out changeitems made by cron jobs
//
// Revision 1.4  2006/04/03 22:04:38  wendy
// OIM3.0b datamodel and Supported Device changes
//
// Revision 1.3  2006/02/13 21:26:03  wendy
// Prevent null ptr if extract fails for deleted relator
//
// Revision 1.2  2006/01/25 19:26:03  wendy
// AHE copyright
//
// Revision 1.1  2006/01/24 18:39:15  wendy
// Init for AHE
//
//
abstract class FMChgSet implements Serializable, Comparable<FMChgSet>
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2017  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.6 $";

    private transient EntityItem fromItem; // not needed after getting serialized
    private transient EntityItem curItem;// not needed after getting serialized
    private String curItemKey, fromItemKey;
    private String curTime, fromTime;
    private StringBuffer outputSb = new StringBuffer();
    private String dateChg="";
    private String chgType=FMChgLogGen.NOCHG;
    private String lastEditor="";

    private static final String BLANKS="                                                                                ";
    static final String COL_DELIMITER=" ";
    static final int DATE_CHG_COL = 16; // CR042605498 drop seconds (was 19)
    static final int CHG_TYPE_COL = 7;
    static final int LAST_ED_COL = 10;
    static final int L11 = 11;
    static final int L13 = 13;
    static final int L14 = 14;
    static final int L16 = 16;
    static final int L17 = 17;
    static final int L19 = 19;
    static final int L20 = 20;

    /********************************************************************************
    * Used for collections.sort()
    * @param o Object
    * @return int
    */
    public int compareTo(FMChgSet o) // used by Collection.sort()
   	{
      // group according to role
      return getSortKey().compareTo(((FMChgSet)o).getSortKey());
    }

    /********************************************************************************
    * Get sort key for collections.sort()
    * @return String
    */
    String getSortKey() { return "";}
    /********************************************************************************
    * Constructor
    * @param fitem EntityItem for fromtime interval, may be null
    * @param citem EntityItem for curtime, may be null
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgSet(EntityItem fitem, EntityItem citem,
        String time1, String time2) 
    {
        fromItem = fitem;
        curItem = citem;
        if (curItem!=null) {
        	curItemKey = curItem.getKey();
        }
        if (fromItem!=null) {
        	fromItemKey = fromItem.getKey();
        }
        curTime = time2;
        fromTime = time1;
    }

    /********************************************************************************
    * Constructor
    * @param copy FMChgSet object to duplicate
    */
    FMChgSet(FMChgSet copy)
    {
        fromItem = copy.fromItem;
        curItem = copy.curItem;
        curTime = copy.curTime;
        fromTime = copy.fromTime;
        if (curItem!=null) {
        	curItemKey = curItem.getKey();
        }
        if (fromItem!=null) {
        	fromItemKey = fromItem.getKey();
        }
        dateChg=copy.dateChg;
   		chgType=copy.chgType;
    	lastEditor = copy.lastEditor;
    	// don't copy outputsb here
    	outputSb.append(copy.outputSb.toString());
    }

    /********************************************************************************
    * get item from time1
    * @return EntityItem
    */
    EntityItem getFromItem() { return fromItem; }
    /********************************************************************************
    * get item from time2
    * @return EntityItem
    */
    EntityItem getCurItem() { return curItem; }

    /********************************************************************************
    * get output buffer
    * @return StringBuffer
    */
    protected StringBuffer getOutputSb()  { return outputSb; }
    /********************************************************************************
    * get unformatted type of change
    * @return String
    */
    String getChgType() { return chgType; }
    /********************************************************************************
    * set type of change
    * @param c String
    */
    protected void setChgType(String c) { chgType=c; }

    /********************************************************************************
    * Release memory
    */
    void dereference()
    {
        fromItem = null;
        curItem = null;
        outputSb = null;
        dateChg=null;
        chgType=null;
        lastEditor=null;
        curTime=null;
        fromTime=null;
        curItemKey = null;
        fromItemKey = null;
    }

    /********************************************************************************
    * Restore default values
    */
    protected void reset() // reset for old vs new records
    {
        dateChg="";
        chgType=FMChgLogGen.NOCHG;
        lastEditor="";
    }

    /**
    * first row
    *Col Width:  19      7       10
    *                    Change  Last
    *Date/Time of Change Type    Editor
    *@return String
    */
    protected static String getDateTypeEditorHeaderRow1()
    {
        return formatToWidth("",DATE_CHG_COL)+COL_DELIMITER+
        formatToWidth("Change",CHG_TYPE_COL)+COL_DELIMITER+
        formatToWidth("Last",LAST_ED_COL)+COL_DELIMITER;
    }
    /**
    * second rom
    *@return String
    */
    protected static String getDateTypeEditorHeaderRow2()
    {
//      return formatToWidth("Date/Time of Change",DATE_CHG_COL)+COL_DELIMITER+// CR042605498 drop 'of'
        return formatToWidth("Date/Time Change",DATE_CHG_COL)+COL_DELIMITER+
        formatToWidth("Type",CHG_TYPE_COL)+COL_DELIMITER+
        formatToWidth("Editor",LAST_ED_COL)+COL_DELIMITER;
    }

    //Col Width:    19  7   10
    /********************************************************************************
    * Get date change field formatted to 19
    * @return String date change formatted to 19 chars long
    */
    String getDateChg() { return formatToWidth(dateChg,DATE_CHG_COL);   }
    /********************************************************************************
    * Set date change field
    * @param d String date change
    */
    void setDateChg(String d) { dateChg=d;   }
    /********************************************************************************
    * Get change type field formatted to 7
    * @return String change type formatted to 7 chars long
    */
    String getChangeType() { return formatToWidth(chgType,CHG_TYPE_COL); }
    /********************************************************************************
    * Get last editor field formatted to 10
    Wayne Kehrli    take first 10 characters from the left
    * @return String last editor formatted to 10 chars long
    */
    String getLastEditor() { return formatToWidth(lastEditor,LAST_ED_COL);}
    /********************************************************************************
    * Set last editor field when change history will not be used
    * @param editor String last editor
    */
    protected void setLastEditor(String editor) { lastEditor = editor;}

    /********************************************************************************
    * Get key
    * @return String
    */
    String getKey() {
        String key = "";
        if (curItemKey!=null) {
            key = curItemKey;
        }else if (fromItemKey!=null) {
            key = fromItemKey;
        }
        return key;
    }

    /********************************************************************************
    * Format string to specifed length, padded with blank if necessary
    Wendy   hi
    Wendy   Long Text like WDCOMMENTS can contain new lines, causing output to wrap when you look at the generated file,
    makes it appear that it doesn't fit in its column.. is this acceptable, or do I need to remove new lines?
    Rupal   hmmm if we remove them..would it screw up?
    Wendy   will it screw up if we don't?
    Rupal   let's remove it then..I will let Wayne know as well
    Wendy   ok
    if i edit COMMENTS in the BUI, new lines are saved as '\r\n'..
    if i edit COMMENTS in the JUI, new lines are '\n'.. causes problems with character counts

    * @param data String to format
    * @param len int with length to set
    * @return String formatted to x chars long
    */
    static String formatToWidth(String data, int len)
    {
        StringBuffer tmp = null;
        if (data ==null) {
            data =""; 
        }
        // remove new line characters
        data = data.replace('\n', ' ');
        data = data.replace('\r', ' ');
        tmp = new StringBuffer(data+BLANKS);
        tmp.setLength(len);
        return tmp.toString();
    }

    /********************************************************************************
    * Get output for this entity set
    * @return String with generated output
    */
    String getOutput() { 
    	return outputSb.toString();
    }

    /********************************************************************************
    * Calculate output for changes in root only, no structure checks
    */
    void calculateOutput(Database dbCurrent,Profile profile,FMChgLogGen logGen) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        ChangeHistoryItem createChi = null;
        ChangeHistoryItem lastChi = null;
        // find out if this was added or deleted, no structure so must look at entity history, need dates too
        EntityChangeHistoryGroup histGrp = logGen.getChgHistGroup(curItem.getKey());
        if (histGrp==null)  {
            histGrp = new EntityChangeHistoryGroup(dbCurrent, profile, curItem);
            logGen.addChgHistGroup(curItem.getKey(),histGrp);
        }

        //CQ236604 filter out changeitems made by cron jobs
        Vector<ChangeHistoryItem> changeHistVct = getFilteredChangeHistory(logGen,curItem,histGrp);
        
        //CQ236604 createChi = histGrp.getChangeHistoryItem(0);
        //CQ236604 lastChi = histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-1);
        if(changeHistVct.size()>0){
        	createChi = (ChangeHistoryItem)changeHistVct.firstElement();
        	lastChi =  (ChangeHistoryItem)changeHistVct.lastElement();

        	logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateOutput() entered "+curItem.getKey()+" Entity CREATE isActive: "+createChi.isActive()+
        			" isValid: "+createChi.isValid()+" chgdate: "+createChi.getChangeDate()+
        			" fromTime: "+fromTime );
        	logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateOutput() entered "+curItem.getKey()+" Entity LAST isActive: "+lastChi.isActive()+
        			" isValid: "+lastChi.isValid()+" chgdate: "+lastChi.getChangeDate());

        	// make sure lastChi is before curtime
        	if (lastChi.getChangeDate().compareTo(curTime)>0) {// date is AFTER curtime
        		//CQ236604 for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--) {
        		for (int i=changeHistVct.size()-1; i>=0; i--) {
        			//CQ236604 ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
        			ChangeHistoryItem chi = (ChangeHistoryItem)changeHistVct.elementAt(i);
        			// history is complete, if user makes chgs after chglog starts, don't pick them up!
        			if (chi.getChangeDate().compareTo(curTime)>0) { // date is AFTER curtime
        				logGen.trace(D.EBUG_DETAIL,false,curItem.getKey()+" skipping["+i+"] chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
        				continue;
        			}
        			lastChi=chi;
        			logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateOutput() "+curItem.getKey()+" Entity LAST isActive: "+lastChi.isActive()+
        					" isValid: "+lastChi.isValid()+" chgdate: "+lastChi.getChangeDate());
        			break;
        		}
        	}

        	if (createChi.getChangeDate().compareTo(fromTime)<=0) { // was created BEFORE this interval
        		if (!lastChi.isActive()) { // was DELETED
        			// entity was deleted
        			boolean wasActive = true;
        			String lastActiveDate = null;
        			ChangeHistoryItem firstActiveChi = createChi;
        			// was fromItem active or not?
        			//CQ236604 for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--) {
        			for (int i=changeHistVct.size()-1; i>=0; i--) {
        				//CQ236604 ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
        				ChangeHistoryItem chi = (ChangeHistoryItem)changeHistVct.elementAt(i);
        				logGen.trace(D.EBUG_DETAIL,false,curItem.getKey()+" CHI["+i+"] isActive: "+chi.isActive()+
        						" isValid: "+chi.isValid()+" chgdate: "+chi.getChangeDate());
        				// history is complete, if user makes chgs after chglog starts, don't pick them up!
        				if (chi.getChangeDate().compareTo(curTime)>0) {// date is AFTER curtime
        					logGen.trace(D.EBUG_DETAIL,false,curItem.getKey()+" skipping["+i+"] chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
        					continue;
        				}

        				if (chi.isActive())	{
        					if (lastActiveDate==null) { // set this with the first active one before deletion, don't overwrite
        						lastActiveDate = chi.getChangeDate(); }
        					firstActiveChi = chi; // this is the last active one after fromtime
        				}
        				if (chi.getChangeDate().compareTo(fromTime)<=0) {// date is BEFORE fromtime
        					wasActive = chi.isActive();
        					break;
        				}
        			}
        			// stuff from root
        			if (!wasActive) { // must have been deactivated at from time, then restored and then deleted again
        				logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateOutput() RESTORED and DELETED fromItem "+fromItem.getKey()+" was NOT active "+
        						"fromtime: "+fromTime+" firstactivedate: "+firstActiveChi.getChangeDate());
        				// from values will be null, use first active set after fromtime
        				findAddAndDeleteValues(dbCurrent,profile,logGen,firstActiveChi,lastChi);
        			} else {
        				logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateOutput() DELETED fromItem "+fromItem.getKey());
        				chgType = FMChgLogGen.DELETED;  //USRO-R-TMAY-66JRV4 was 'Removed'
        				lastEditor = lastChi.getUser();
        				dateChg = lastChi.getChangeDate();
        				if (lastActiveDate==null) {
        					lastActiveDate=fromTime; } // this can't really happen.. just in case
        				// do a pull using lastactive date, getting eanattribute for attribute chg history is not reliable
        				handleDeleted(dbCurrent,profile,logGen,lastActiveDate, dateChg, getExtractName());
        				//setRowValues(fromItem); // use item at from time.. commented out because it won't have last values, only values at time1
        				setOutput();
        			}
        		}
        		else { // still active
        			String curInfo = null;
        			String curAll = null;
        			String fromInfo = null;
        			String fromAll = null;
        			boolean outputChg = true;
        			// special case handling needed if entity was restored between fromtime and curtime
        			// deactivated at fromtime, then restored  if so, it is an ADD
        			// get date closest to fromtime but not after it
        			// don't need this check in calculateMTMOutput because pull at from time will not have the relator!!
        			//CQ236604 for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--) {
        			for (int i=changeHistVct.size()-1; i>=0; i--) {
        				//CQ236604 ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
        				ChangeHistoryItem chi = (ChangeHistoryItem)changeHistVct.elementAt(i);
        				// history is complete, if user makes chgs after chglog starts, don't pick them up!
        				if (chi.getChangeDate().compareTo(curTime)>0) { // date is AFTER curtime
        					logGen.trace(D.EBUG_DETAIL,false,curItem.getKey()+" skipping["+i+"] chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
        					continue;
        				}

        				if (chi.getChangeDate().compareTo(fromTime)<=0) {// date was before this interval
        					if (!chi.isActive()){
        						logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateOutput() CHI["+i+"] RESTORED curItem "+curItem.getKey()+" force ADD");
        						chgType = FMChgLogGen.ADDED;
        						// get most recent chg date and user
        						lastEditor = lastChi.getUser();
        						dateChg = lastChi.getChangeDate();
        						setRowValues(logGen,curItem);
        						setOutput();
        						return;
        					}
        					break; // now before fromtime, don't look any further back
        				}
        			}

        			logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateOutput() CHANGED curItem "+curItem.getKey());
        			// if chgs are in the set of attr put out current with 'changed' chgtype and orig with blank change type
        			// get values for cur first but don't output them
        			setRowValues(logGen,curItem);
        			curInfo = getRowValues(logGen);
        			curAll = getRowFullValues(logGen);
        			reset(); // reset all attr info

        			// get values for from
        			setRowValues(logGen,fromItem);
        			fromInfo = getRowValues(logGen);
        			fromAll = getRowFullValues(logGen);
        			logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateOutput() "+curItem.getKey()+" fromInfo: *"+fromInfo+"*");
        			logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateOutput() "+curItem.getKey()+"  curInfo: *"+curInfo+"*");
        			if (fromInfo.equals(curInfo)) { // something else must have changed
        				// just for debug info.. was the change after the truncation?
        				if (!fromAll.equals(curAll)) {
        					logGen.trace(D.EBUG_ERR,false,"FMChgSet.calculateOutput() WARNING "+curItem.getKey()+
        							" change was in truncation! fromAllInfo: *"+fromAll+"*"+"  curAllInfo: *"+curAll+"*");
        				}

        				outputChg = false;
        				logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateOutput() No differences found for "+curItem.getKey());

        				// but did inventorygroup chg, if so this is a chg
        				if (invGrpChanged(logGen))  {
        					logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateOutput() InventoryGroup was changed for "+curItem.getKey());
        					outputChg = true;
        				}
        			}
        			if (outputChg) {
        				// variables now hold from info
        				chgType = "";
        				// get date closest to fromtime but not after it
        				//CQ236604 for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--) {
        				for (int i=changeHistVct.size()-1; i>=0; i--) {
        					//CQ236604 ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
        					ChangeHistoryItem chi = (ChangeHistoryItem)changeHistVct.elementAt(i);
        					// history is complete, if user makes chgs after chglog starts, don't pick them up!
        					if (chi.getChangeDate().compareTo(curTime)>0) { // date is AFTER curtime
        						logGen.trace(D.EBUG_DETAIL,false,curItem.getKey()+" skipping["+i+"] chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
        						continue;
        					}

        					//logGen.trace(D.EBUG_DETAIL,false,curItem.getKey()+" isActive: "+chi.isActive()+
        					//      " isValid: "+chi.isValid()+" chgdate: "+chi.getChangeDate());
        					if (chi.getChangeDate().compareTo(fromTime)<=0) { // date was BEFORE fromtime
        						//lastEditor = chi.getUser(); // CR042605498 don't show editor on chg row
        						dateChg = chi.getChangeDate();
        						break;
        					}
        				}
        				setOutput();
        				reset();

        				// run it again for cur info
        				chgType = FMChgLogGen.CHANGED;
        				// get most recent chg date and user
        				lastEditor = lastChi.getUser();
        				dateChg = lastChi.getChangeDate();
        				setRowValues(logGen,curItem);
        				setOutput();
        			}
        		}
        	}
        	else // was created IN this interval
        	{
        		if (!lastChi.isActive())  {// was DELETED
        			logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateOutput() ADDED AND DELETED curItem "+curItem.getKey());
        			findAddAndDeleteValues(dbCurrent,profile,logGen,createChi,lastChi);
        		} else { // still active
        			logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateOutput() ADDED curItem "+curItem.getKey());
        			chgType = FMChgLogGen.ADDED; // current would reflect the change if there is any
        			lastEditor = createChi.getUser();
        			dateChg = createChi.getChangeDate();
        			// stuff from root
        			setRowValues(logGen,curItem);  // use current info
        			setOutput();
        		}
        	}
        }else{
        	logGen.trace(D.EBUG_ERR,false,"FMChgSet.calculateOutput() NO CHANGE HISTORY for "+curItem.getKey());
        	chgType = FMChgLogGen.NOCHG;	
        }
    }

    /********************************************************************************
    * Derived classes implement these to calculate output
    */
    /********************************************************************************
    * Get row values
    Wendy   should there even be a row?  for example.. what if the only change in the watched set of attributes is in
    COMMENTS and it is after the 80 chars.. do i output a row that says it changed.. but both rows will look the same?
    Rupal   right ..we only first track 80 chars
    Rupal   no more no less
    Rupal   if any changes after 80 chars
    Rupal   we ignore it
    * @return String with all row values concatenated, used for any changes check
    */
    abstract protected String getRowValues(FMChgLogGen logGen);
    /********************************************************************************
    * Get row values, untruncated!
    * @return String with all row values concatenated, used for debug when truncation is required
    */
    protected String getRowFullValues(FMChgLogGen logGen) { return getRowValues(logGen);}

    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    abstract protected void setRowValues(FMChgLogGen logGen,EntityItem theItem) throws Exception;
    /********************************************************************************
    * Set output for changes
    */
    abstract protected void setOutput();
    /********************************************************************************
    * Check to see if inventory group was changed in this interval
    * @return boolean
    */
    abstract protected boolean invGrpChanged(FMChgLogGen logGen);

    /********************************************************************************
    * Get extract name to use for structure check or just a pull for deleted roots
    * @return String
    */
    String getExtractName() { return "DUMMY"; }

    /********************************************************************************
    * Get root for structure, MTM uses FEATURE, SD uses MODELc
    * @return EntityItem
    */
    protected EntityItem getRootItem() { return null; }

    /********************************************************************************
    * Check to see if inventory group was changed in this interval
    * @param citem EntityItem for time2
    * @param fitem EntityItem for time1
    * @return boolean
    */
    protected boolean invGrpChanged(FMChgLogGen logGen,EntityItem citem, EntityItem fitem)
    {
        boolean changed =false;
        String curIG=null;
        String fromIG=null;
        if (citem!=null)
        {
            curIG = FMChgLogGen.getAttributeFlagValue(citem, "INVENTORYGROUP");
        }
        if (fitem!=null)
        {
            fromIG = FMChgLogGen.getAttributeFlagValue(fitem, "INVENTORYGROUP");
        }
        logGen.trace(D.EBUG_INFO,false,"FMChgSet.invGrpChanged() citem: "+(citem==null?"null":citem.getKey())+" fitem: "+
            (fitem==null?"null":fitem.getKey())+" curIG: "+curIG+" fromIG: "+fromIG);

        if (curIG==null) { // entity was deleted, don't flag that here
            curIG=fromIG;}
        if (fromIG==null) { // entity was added, don't flag that here
            fromIG=curIG; }

        if (curIG!=null) {
            changed=!curIG.equals(fromIG);
        }

        return changed;
    }

    /*******************************************************************************
    * Entity was Deleted in the interval, can not be used for structure
    * Do a pull using modified delete date
    * the RowSelectableTable from item.getEntityItemTable() does not have all the attributes in it
    * @param lastActiveDate String with last active DTS (before delete DTS)
    * @param deleteDate String with delete DTS
    * @param extractName String with extract action name
    */
    private void handleDeleted(Database dbCurrent,Profile profile,FMChgLogGen logGen,
    		String lastActiveDate, String deleteDate, String extractName) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Profile dtsProfile = null;
        ISOEntityDate isoDate = null;
        String adjDate = null;
        EntityList theList = null;
        EntityItem theItem = null;
        Vector<String> idVct = new Vector<String>();
        String rootType = fromItem.getEntityType();
        idVct.addElement(""+fromItem.getEntityID());

        logGen.trace(D.EBUG_INFO,false,"FMChgSet.handleDeleted() fromItem "+fromItem.getKey()+" lastActiveDate: "+
            lastActiveDate+" deleteDate: "+deleteDate);
        // need entityitems at valid time, inorder to get attribute historys for values
        dtsProfile = profile.getNewInstance(dbCurrent);

        // don't add an increment to the lastActiveDate to allow for slightly later attribute timestamps
        // find a time between these 2 dates
        isoDate = new ISOEntityDate(lastActiveDate, deleteDate);
        // subtract an increment from deletedate to allow for slightly earlier attribute timestamps
        adjDate = isoDate.getAdjustedDeleteDate();//isoDate.getAdjustedDate();

        dtsProfile.setValOnEffOn(adjDate, adjDate);

        theList = logGen.pullEntityItems(idVct, dtsProfile,extractName, rootType);
        // log the results
        logGen.trace(D.EBUG_INFO,true,"FMChgSet.handleDeleted() Entitylist for fromItem "+
        	fromItem.getKey()+" valon: "+adjDate+" "+FMChgLogGen.outputList(theList));

        theItem = theList.getParentEntityGroup().getEntityItem(0);
        setRowValues(logGen,theItem); // use item at adjusted last active time

        theList.dereference();
        isoDate.dereference();
    }

    /*******************************************************************************
    * Relator was Removed in the interval, used for structure
    * Do a pull using modified delete date
    * the RowSelectableTable from item.getEntityItemTable() does not have all the attributes in it
    * @param lastActiveDate String with last active DTS (before delete DTS)
    * @param deleteDate String with delete DTS
    * @param extractName String with extract action name
    * @param rootType String with root entity type for extract
    * @param rootId String with root entity id for extract
    */
    private void handleRemovedMTM(Database dbCurrent,Profile profile,FMChgLogGen logGen,
    		String lastActiveDate, String deleteDate, String extractName,
        String rootType, String rootId) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Profile dtsProfile = null;
        ISOEntityDate isoDate = null;
        String adjDate = null;
        EntityList theList = null;
        EntityItem theItem = null;
        Vector<String> idVct = new Vector<String>();
        idVct.addElement(rootId);

        logGen.trace(D.EBUG_INFO,false,"FMChgSet.handleRemovedMTM() fromItem "+fromItem.getKey()+" lastActiveDate: "+
            lastActiveDate+" deleteDate: "+deleteDate+" root: "+rootType+rootId);
        // need entityitems at valid time
        dtsProfile = profile.getNewInstance(dbCurrent);

        // can't add an increment to the lastActiveDate to allow for slightly later attribute timestamps
        // MN25369700  can't use last active date.. that may be too far back in time.. here the info on Model chgd
        // so wrong info was displayed---
        /*
        FM1eServer PRODSTRUCT134121 CHI[4] isActive: false isValid: false chgdate: 2005-09-13-11.46.43.158347
        FM1eServer PRODSTRUCT134121 CHI[3] isActive: false isValid: false chgdate: 2005-09-13-11.46.43.127231
        FM1eServer PRODSTRUCT134121 CHI[2] isActive: true isValid: false chgdate: 2005-09-07-23.31.30.116486
        FM1eServer FMChgSet.handleRemovedMTM() fromItem PRODSTRUCT134121 lastActiveDate: 2005-09-07-23.31.30.116486 deleteDate: 2005-09-13-11.46.43.158347
        */

        // find a time between these 2 dates
        isoDate = new ISOEntityDate(lastActiveDate, deleteDate);
        // subtract an increment from deletedate to allow for slightly earlier attribute timestamps
        adjDate = isoDate.getAdjustedDeleteDate();//isoDate.getAdjustedDate(); MN25369700

        dtsProfile.setValOnEffOn(adjDate, adjDate);

        theList = logGen.pullEntityItems(idVct, dtsProfile,extractName, rootType);
        // log the results, something strange going on
        logGen.trace(D.EBUG_INFO,true,"FMChgSet.handleRemovedMTM() Entitylist for fromItem "+
        	fromItem.getKey()+" root: "+rootType+rootId+" valon: "+adjDate+" "+FMChgLogGen.outputList(theList));

        // get relator to find values for
        theItem = theList.getEntityGroup(fromItem.getEntityType()).getEntityItem(fromItem.getKey());

        // make sure this item has up and down links
        if (theItem==null) {
        	logGen.trace(D.EBUG_ERR,true,"FMChgSet.handleRemovedMTM() ERROR extract missing "+fromItem.getKey()+
        		" relator. Cannot get values for parent or child!");
		}else{
    	    setRowValues(logGen,theItem); // use item at adjusted last active time
		}

        theList.dereference();
        isoDate.dereference();
    }

    /*******************************************************************************
    * Entity was Added or Restored and Deleted in the interval, NOT used for structure
    * Entity was Added and Deleted in the interval
    * if an entity is both created and deleted within a single interval?
    * Wayne Kehrli  preferred solution - show two rows - add followed by delete
    * @param lastActiveChi ChangeHistoryItem with last active change history item
    * @param deleteChi ChangeHistoryItem with last change history item
    */
    private void findAddAndDeleteValues(Database dbCurrent,Profile profile,FMChgLogGen logGen,
    		ChangeHistoryItem lastActiveChi,ChangeHistoryItem deleteChi)
        throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Profile dtsProfile = null;
        ISOEntityDate isoDate = null;
        String adjDate = null;
        EntityList theList = null;
        EntityItem theItem = null;

        String lastActiveDate = lastActiveChi.getChangeDate();
        String deleteDate = deleteChi.getChangeDate();
        Vector<String> idVct = new Vector<String>();
        String rootType = curItem.getEntityType();
        idVct.addElement(""+curItem.getEntityID());

        // first output added row
        chgType = FMChgLogGen.ADDED;
        lastEditor = lastActiveChi.getUser();
        dateChg = lastActiveDate;
        // do a pull using adjusted create date
        // need entityitems at valid time, inorder to get attribute historys for values
        dtsProfile = profile.getNewInstance(dbCurrent);

        // add an increment to the createDate to allow for slightly later attribute timestamps
        // find a time between these 2 dates
        isoDate = new ISOEntityDate(lastActiveDate, deleteDate);
        adjDate = isoDate.getAdjustedDate();
        logGen.trace(D.EBUG_INFO,false,"FMChgSet.findAddAndDeleteValues() curItem "+curItem.getKey()+" lastActiveDate: "+
            lastActiveDate+" adjDate: "+adjDate);

        dtsProfile.setValOnEffOn(adjDate, adjDate);

        theList = logGen.pullEntityItems(idVct, dtsProfile,getExtractName(), rootType);
        // log the results
        logGen.trace(D.EBUG_INFO,true,"FMChgSet.findAddAndDeleteValues() Entitylist for curItem "+
        	fromItem.getKey()+" valon: "+adjDate+" "+FMChgLogGen.outputList(theList));

        theItem = theList.getParentEntityGroup().getEntityItem(0);
        setRowValues(logGen,theItem); // use item at adjusted last active time
        setOutput();
        reset(); // reset all attr info

        theList.dereference();

        // output deleted
        chgType = FMChgLogGen.DELETED;
        lastEditor = deleteChi.getUser();
        dateChg = deleteDate;

        // subtract an increment from deletedate to allow for slightly earlier attribute timestamps
        adjDate = isoDate.getAdjustedDeleteDate();
        logGen.trace(D.EBUG_INFO,false,"FMChgSet.findAddAndDeleteValues() curItem "+curItem.getKey()+
            " deleteDate: "+deleteDate+" adjDate: "+adjDate);

        dtsProfile.setValOnEffOn(adjDate, adjDate);

        theList = logGen.pullEntityItems(idVct, dtsProfile,getExtractName(), rootType);
        // log the results
        logGen.trace(D.EBUG_INFO,true,"FMChgSet.findAddAndDeleteValues() Entitylist for curItem "+
        	fromItem.getKey()+" valon: "+adjDate+" "+FMChgLogGen.outputList(theList));

        theItem = theList.getParentEntityGroup().getEntityItem(0);
        setRowValues(logGen,theItem); // use item at adjusted delete time
        setOutput();

        theList.dereference();
        isoDate.dereference();
    }

    /**
     * CQ236604 filter out changeitems made by cron jobs
     * remove any change history items made by a cron job - system id
     * @param theItem
     * @param histGrp
     * @return
     */
    private Vector<ChangeHistoryItem> getFilteredChangeHistory(FMChgLogGen logGen,EntityItem theItem,EntityChangeHistoryGroup histGrp){
    	Vector<ChangeHistoryItem> histVct = logGen.getChgHistGroupVct(theItem.getKey());
    	if(histVct==null){	
    		histVct = new Vector<ChangeHistoryItem>(histGrp.getChangeHistoryItemCount());
    		logGen.addChgHistGroupVct(theItem.getKey(),histVct);
    		for (int i=0; i<histGrp.getChangeHistoryItemCount(); i++) {
    			ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
    			String user = chi.getUser();
    			if(user.toLowerCase().startsWith("no user token") || user.indexOf("@")!=-1){
    				histVct.add(chi);
    			}else{
    				logGen.trace(D.EBUG_ERR,false,"FMChgSet.getFilteredChangeHistory() for "+theItem.getKey()+
    						" ChangeHistoryItem["+i+"]:" + chi.getKey() +
    						" ChangeDate:" + chi.getChangeDate() + " isValid:" + chi.isValid()+
    						" isActive:" + chi.isActive() + " user:" + user+" is a system id and will be ignored ");
    			}
    		}
    	}
    	return histVct;
    }
    /********************************************************************************
    * Calculate output for changes in MTM structure
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    * @throws java.sql.SQLException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
    * @throws Exception
    */
    protected void calculateMTMOutput(Database dbCurrent,Profile profile,FMChgLogGen logGen) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        ChangeHistoryItem createChi = null;
        ChangeHistoryItem lastChi = null;
        EntityChangeHistoryGroup histGrp = null;

        EntityItem theItem = curItem;
        if (curItem==null) {
            theItem = fromItem; 
        }

        histGrp = logGen.getChgHistGroup(theItem.getKey());
        if (histGrp==null)  {
            histGrp = new EntityChangeHistoryGroup(dbCurrent, profile, theItem);
            logGen.addChgHistGroup(theItem.getKey(),histGrp);
        }
        //CQ236604 filter out changeitems made by cron jobs
        Vector<ChangeHistoryItem> changeHistVct = getFilteredChangeHistory(logGen,theItem,histGrp);
        
        //CQ236604 createChi = histGrp.getChangeHistoryItem(0);
        //CQ236604 lastChi = histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-1);
        if(changeHistVct.size()>0){
        	createChi = (ChangeHistoryItem)changeHistVct.firstElement();
        	lastChi =  (ChangeHistoryItem)changeHistVct.lastElement();
       
        	logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateMTMOutput() "+theItem.getKey()+" Entity CREATE isActive: "+createChi.isActive()+
        			" isValid: "+createChi.isValid()+" chgdate: "+createChi.getChangeDate());
        	logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateMTMOutput() "+theItem.getKey()+" Entity LAST isActive: "+lastChi.isActive()+
        			" isValid: "+lastChi.isValid()+" chgdate: "+lastChi.getChangeDate());

        	// make sure lastChi is before curtime
        	if (lastChi.getChangeDate().compareTo(curTime)>0) {// date is AFTER curtime
        		// CQ236604 for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--) {
        		for (int i=changeHistVct.size()-1; i>=0; i--) {
        			//CQ236604 ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
        			ChangeHistoryItem chi = (ChangeHistoryItem)changeHistVct.elementAt(i);
        			// history is complete, if user makes chgs after chglog starts, don't pick them up!
        			if (chi.getChangeDate().compareTo(curTime)>0) {// date is AFTER curtime
        				logGen.trace(D.EBUG_DETAIL,false,theItem.getKey()+" skipping["+i+"] chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
        				continue;
        			}
        			lastChi=chi;
        			logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateMTMOutput() "+theItem.getKey()+" Entity CHI["+i+"] LAST isActive: "+lastChi.isActive()+
        					" isValid: "+lastChi.isValid()+" chgdate: "+lastChi.getChangeDate());
        			break;
        		}
        	}
        	/*
        for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
        {
            ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
            logGen.trace(D.EBUG_DETAIL,false,theItem.getKey()+" isActive: "+chi.isActive()+
                    " isValid: "+chi.isValid()+" chgdate: "+chi.getChangeDate());
        }
        	 */
        	if (fromItem==null)  {// relator was added, wayne says just cur info is ok
        		chgType = FMChgLogGen.ADDED; // current would reflect the change if there is any
        		// was this relator restored or new?
        		if (createChi.getChangeDate().compareTo(fromTime)<=0) {// date was BEFORE fromtime
        			logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateMTMOutput() RESTORED curItem "+curItem.getKey());
        			//CQ236604 for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--) {
        			for (int i=changeHistVct.size()-1; i>=0; i--) {	
        				//CQ236604 ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
        				ChangeHistoryItem chi = (ChangeHistoryItem)changeHistVct.elementAt(i);
        				// history is complete, if user makes chgs after chglog starts, don't pick them up!
        				if (chi.getChangeDate().compareTo(curTime)>0) { // date is AFTER curtime
        					logGen.trace(D.EBUG_DETAIL,false,curItem.getKey()+" skipping["+i+"] chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
        					continue;
        				}

        				logGen.trace(D.EBUG_DETAIL,false,theItem.getKey()+" CHI["+i+"] isActive: "+chi.isActive()+
        						" isValid: "+chi.isValid()+" chgdate: "+chi.getChangeDate());
        				if (chi.getChangeDate().compareTo(fromTime)>=0) { // date was AFTER fromtime
        					// when isActive=false, that is the time it was deleted
        					if (chi.isValid()) {
        						createChi = chi;
        					} else { // get date when relator was restored
        						logGen.trace(D.EBUG_DETAIL,false,theItem.getKey()+" was removed on: "+chi.getChangeDate());
        						break;
        					}
        				} else {
        					logGen.trace(D.EBUG_DETAIL,false,theItem.getKey()+" "+chi.getChangeDate()+" is BEFORE fromtime "+fromTime);
        					break;
        				}
        			}
        		}
        		else  {
        			logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateMTMOutput() ADDED curItem "+curItem.getKey());
        		}

        		lastEditor = createChi.getUser();
        		dateChg = createChi.getChangeDate();
        		// stuff from Relator, Parent and Child
        		setRowValues(logGen,curItem);
        		setOutput();
        	} else if(curItem ==null) { // relator was removed
        		EntityItem rootItem = null;
        		String lastActiveDate = fromTime; // get start of interval, as default
        		logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateMTMOutput() REMOVED fromItem "+fromItem.getKey());

        		//CQ236604 for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--) {
        		for (int i=changeHistVct.size()-1; i>=0; i--) {
        			//CQ236604 ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
        			ChangeHistoryItem chi = (ChangeHistoryItem)changeHistVct.elementAt(i);
        			// history is complete, if user makes chgs after chglog starts, don't pick them up!
        			if (chi.getChangeDate().compareTo(curTime)>0) {// date is AFTER curtime
        				logGen.trace(D.EBUG_DETAIL,false,fromItem.getKey()+" skipping["+i+"] chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
        				continue;
        			}

        			logGen.trace(D.EBUG_DETAIL,false,fromItem.getKey()+" CHI["+i+"] isActive: "+chi.isActive()+
        					" isValid: "+chi.isValid()+" chgdate: "+chi.getChangeDate());

        			if (chi.isActive())	{
        				lastActiveDate = chi.getChangeDate(); // find values when relator was removed
        				break;
        			}
        		}

        		chgType = FMChgLogGen.REMOVED;
        		lastEditor = lastChi.getUser();
        		dateChg = lastChi.getChangeDate();
        		// stuff from Relator, Parent and Child
        		rootItem = getRootItem();
        		// do a pull using lastactive date, getting eanattribute for attribute chg history is not reliable
        		// MN25369700  can't use last active date.. that may be too far back in time.. here the info on Model chgd
        		// so wrong info was displayed---
        		/*
            FM1eServer PRODSTRUCT134121 CHI[4] isActive: false isValid: false chgdate: 2005-09-13-11.46.43.158347
            FM1eServer PRODSTRUCT134121 CHI[3] isActive: false isValid: false chgdate: 2005-09-13-11.46.43.127231
            FM1eServer PRODSTRUCT134121 CHI[2] isActive: true isValid: false chgdate: 2005-09-07-23.31.30.116486
            FM1eServer FMChgSet.handleRemovedMTM() fromItem PRODSTRUCT134121 lastActiveDate: 2005-09-07-23.31.30.116486 deleteDate: 2005-09-13-11.46.43.158347
        		 */
        		handleRemovedMTM(dbCurrent,profile,logGen,
        				lastActiveDate, dateChg, getExtractName(), rootItem.getEntityType(), ""+rootItem.getEntityID());
        		//  setRowValues(fromItem);//these may not be the actual deletion values, but they reflect fromtime values
        		setOutput();
        	}
        	else {// something was changed on it, but was it an attribute of interest or not? need old and new!!
        		String curInfo = null;
        		String curAll = null;
        		String fromInfo = null;
        		String fromAll = null;
        		boolean outputChg = true;
        		logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateMTMOutput() CHANGED curItem "+curItem.getKey());
        		// if chgs are in the set of attr put out current with 'changed' chgtype and orig with blank change type
        		// get values for cur first
        		// stuff from Relator, Parent and Child
        		setRowValues(logGen,curItem);
        		curInfo = getRowValues(logGen);
        		curAll = getRowFullValues(logGen);
        		reset(); // reset all attr info

        		// get values for from
        		// stuff from Relator, Parent and Child
        		setRowValues(logGen,fromItem);
        		fromInfo = getRowValues(logGen);
        		fromAll = getRowFullValues(logGen);
        		logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateMTMOutput() "+curItem.getKey()+" fromInfo: *"+fromInfo+"*");
        		logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateMTMOutput() "+curItem.getKey()+"  curInfo: *"+curInfo+"*");
        		if (fromInfo.equals(curInfo)){ // something else must have changed
        			// just for debug info.. was the change after the truncation?
        			if (!fromAll.equals(curAll)){
        				logGen.trace(D.EBUG_ERR,false,"FMChgSet.calculateMTMOutput() WARNING "+curItem.getKey()+
        						" change was in truncation! fromAllInfo: *"+fromAll+"*"+"  curAllInfo: *"+curAll+"*");
        			}
        			outputChg = false;
        			logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateMTMOutput() No differences found in MTM for "+curItem.getKey());
        			// but did inventorygroup chg, if so output this
        			if (invGrpChanged(logGen))	{
        				logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateMTMOutput() InventoryGroup was changed in MTM for "+curItem.getKey());
        				outputChg=true;
        			}
        		}
        		if (outputChg) {// something was changed
        			// variables now hold from info
        			chgType = "";

        			// for now just use relator timestamps.. will be misleading!
        			// get date closest to fromtime but not after it
        			//CQ236604 for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--) {
        			for (int i=changeHistVct.size()-1; i>=0; i--) {
        				//CQ236604 ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
        				ChangeHistoryItem chi = (ChangeHistoryItem)changeHistVct.elementAt(i);
        				// history is complete, if user makes chgs after chglog starts, don't pick them up!
        				if (chi.getChangeDate().compareTo(curTime)>0) { // date is AFTER curtime
        					logGen.trace(D.EBUG_DETAIL,false,curItem.getKey()+" skipping["+i+"] chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
        					continue;
        				}

        				//logGen.trace(D.EBUG_DETAIL,false,curItem.getKey()+" isActive: "+chi.isActive()+
        				//      " isValid: "+chi.isValid()+" chgdate: "+chi.getChangeDate());
        				if (chi.getChangeDate().compareTo(fromTime)<=0) { // date was BEFORE fromtime
        					//lastEditor = chi.getUser(); // CR042605498 don't show editor on chg row
        					dateChg = chi.getChangeDate();
        					break;
        				}
        			}

        			setOutput();
        			reset();

        			// run it again for cur info
        			chgType = FMChgLogGen.CHANGED;
        			// get most recent chg date and user
        			lastEditor = lastChi.getUser();
        			dateChg = lastChi.getChangeDate();
        			// stuff from Relator, Parent and Child
        			setRowValues(logGen,curItem);
        			setOutput();
        		}
        	}
        }else{
        	logGen.trace(D.EBUG_ERR,false,"FMChgSet.calculateMTMOutput() NO CHANGE HISTORY for "+theItem.getKey());
        	chgType = FMChgLogGen.NOCHG;	
        }
    }

    /********************************************************************************
    * Calculate output for MTM structure created and deleted within the interval
    */
    String[] calculateDelMTMOutput(Database dbCurrent,Profile profile,FMChgLogGen logGen,
    		EntityItem theItem, String rootType, int rootId) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        ChangeHistoryItem firstActiveChi = null;
        ChangeHistoryItem deleteChi = null;
        String dtsArray[]= new String[2];
        String createDate = null;
        String createUser = null;
        String deleteDate = null;
        String deleteUser = null;
        ISOEntityDate isoDate = null;
        String adjDate = null;
        Profile dtsProfile = null;
        EntityList createList = null;
        EntityItem theCurItem = null;
        EntityChangeHistoryGroup eHistGrp = null;

        String key = theItem.getKey();
        String eType = theItem.getEntityType();
        Vector<String> idVct = new Vector<String>();
        idVct.addElement(""+rootId);

        logGen.trace(D.EBUG_INFO,false,"FMChgSet.calculateDelMTMOutput() entered for ADDED AND REMOVED curItem "+
            key+" root "+rootType+rootId);

        // get valid DTS from theItem and then do pulls for valid info
        eHistGrp = logGen.getChgHistGroup(key);
        if (eHistGrp==null)
        {
            eHistGrp = new EntityChangeHistoryGroup(dbCurrent, profile, theItem);
            logGen.addChgHistGroup(theItem.getKey(),eHistGrp);
        }

        // need a valid date for pull! split the difference.. but what about a single entry??? mw error?
        if (eHistGrp.getChangeHistoryItemCount()<=1) {
            logGen.trace(D.EBUG_ERR,false,"calculateDelMTMOutput ERROR: "+key+" for root "+rootType+rootId+" only had "+
            	eHistGrp.getChangeHistoryItemCount()+" changeHistoryItem!");
        }
        else {
            // find firstActive date and delete date
            firstActiveChi = eHistGrp.getChangeHistoryItem(0); // create date is default
            deleteChi = eHistGrp.getChangeHistoryItem(eHistGrp.getChangeHistoryItemCount()-1);
            // make sure last Chi is before curtime
            if (deleteChi.getChangeDate().compareTo(curTime)>0) // date is AFTER curtime
            {
                for (int i=eHistGrp.getChangeHistoryItemCount()-1; i>=0; i--)  {
                    ChangeHistoryItem chi = eHistGrp.getChangeHistoryItem(i);
                    // history is complete, if user makes chgs after chglog starts, don't pick them up!
                    if (chi.getChangeDate().compareTo(curTime)>0) // date is AFTER curtime
                    {
                        continue;
                    }
                    deleteChi=chi;
                    break;
                }
            }

            logGen.trace(D.EBUG_DETAIL,false,"FMChgSet.calculateDelMTMOutput() EntityChangeHistory for "+key);
            for (int i= eHistGrp.getChangeHistoryItemCount()-1; i>=0; i--)   {
                ChangeHistoryItem chi = eHistGrp.getChangeHistoryItem(i);
                // history is complete, if user makes chgs after chglog starts, don't pick them up!
                if (chi.getChangeDate().compareTo(curTime)>0) // date is AFTER curtime
                {
                    logGen.trace(D.EBUG_DETAIL,false,theItem.getKey()+" skipping["+i+"] chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
                    continue;
                }

                logGen.trace(D.EBUG_DETAIL,false,"ChangeHistoryItem["+i+"] isActive:"+
                    chi.isActive()+" chgDate: "+chi.getChangeDate()+" user: "+chi.getUser());
                // look for first active time after fromtime
                if (chi.isActive())  {
                    firstActiveChi = chi; // this is the first active one after fromtime, keep overwriting as go back
                }
                if (chi.getChangeDate().compareTo(fromTime)<=0) // date is BEFORE fromtime
                {
                    logGen.trace(D.EBUG_DETAIL,false,"ChangeHistoryItem["+i+"] stopping loop chgdate: "+chi.getChangeDate()+
                        " predates fromTime: "+fromTime);
                    break;
                }
            }

            createDate = firstActiveChi.getChangeDate();
            createUser = firstActiveChi.getUser();
            deleteDate = deleteChi.getChangeDate();
            deleteUser = deleteChi.getUser();

            // add an increment to the createdate to allow for slightly later attribute timestamps
            // find a time between these 2 dates
            isoDate = new ISOEntityDate(createDate, deleteDate);
            adjDate = isoDate.getAdjustedDate();
            logGen.trace(D.EBUG_INFO,false,"calculateDelMTMOutput createDate: "+createDate+" adjdate: "+adjDate);
			dtsArray[0] = adjDate; // return the first valid date.. just after create

            // need entityitems at active time
            dtsProfile = profile.getNewInstance(dbCurrent);
            dtsProfile.setValOnEffOn(adjDate, adjDate);

            createList = logGen.pullEntityItems(idVct, dtsProfile,getExtractName(), rootType);

            logGen.trace(D.EBUG_INFO,false,"calculateDelMTMOutput EntityList for "+getExtractName()+" for adjdate: "+adjDate+
                " contains the following entities: ");
            logGen.trace(D.EBUG_INFO,false,FMChgLogGen.outputList(createList));
            // get entity with valid attributes
            theCurItem = createList.getEntityGroup(eType).getEntityItem(key);
            /*EntityChangeHistory for PRODSTRUCT53
            ChangeHistoryItem[0] isValid:false isActive:false chgDate: 2004-09-29-16.41.35.680117 user: kehrli@us.ibm.com
            calculateDelMTMOutput() EntityList for EXRPT3FM for adjdate: 2004-09-29-16.41.35.680117 contains the following entities:
            FEATURE : 1 Parent entity items. IDs( 10)
            FEATURE : 0 entity items.
            MODEL : 1 entity items. IDs( 21)
            PRODSTRUCT : 1 entity items. IDs( 52)

            NOTE: returned wrong prodstruct for this timestamp!!
            */

            if (theCurItem==null)  {
                theCurItem = createList.getEntityGroup(eType).getEntityItem(0);
                chgType = FMChgLogGen.NOCHG;
                logGen.trace(D.EBUG_ERR,false,"calculateDelMTMOutput ERROR: could not pull a valid extract for "+key
                    +" root "+rootType+rootId+" using date: "+adjDate+". It found "+
                    (theCurItem!=null?theCurItem.getKey():"null"));

                createList.dereference();
                isoDate.dereference();
            } else  {
                EntityList deleteList = null;
                EntityItem delItem = null;
                Profile dtsDeleteProfile = profile.getNewInstance(dbCurrent);
                // subtract an increment from deletedate to allow for slightly earlier attribute timestamps
                String adjDelDate = isoDate.getAdjustedDeleteDate();
                logGen.trace(D.EBUG_INFO,false,"calculateDelMTMOutput deleteDate: "+deleteDate+" adjDeldate: "+adjDelDate);
				dtsArray[1] = adjDelDate; // return the last valid date.. just before delete

                dtsDeleteProfile.setValOnEffOn(adjDelDate, adjDelDate);

                deleteList = logGen.pullEntityItems(idVct, dtsDeleteProfile,getExtractName(), rootType);

                logGen.trace(D.EBUG_INFO,false,"calculateDelMTMOutput EntityList for "+getExtractName()+" for adjDeldate: "+adjDelDate+
                    " contains the following entities: ");
                logGen.trace(D.EBUG_INFO,false,FMChgLogGen.outputList(deleteList));
                delItem = deleteList.getEntityGroup(eType).getEntityItem(key);

                if (delItem==null)
                {
                    delItem = deleteList.getEntityGroup(eType).getEntityItem(0);
                    chgType = FMChgLogGen.NOCHG;
                    logGen.trace(D.EBUG_ERR,false,"calculateDelMTMOutput ERROR: could not pull a valid extract for "+key
                        +" root "+rootType+rootId+" using date: "+adjDelDate+". It found "+
                        (delItem!=null?delItem.getKey():"null"));

                    createList.dereference();
                    deleteList.dereference();
                    isoDate.dereference();
                }
                else{
                    // first output added row
                    chgType = FMChgLogGen.ADDED;
                    lastEditor = createUser;
                    dateChg = createDate;
                    setRowValues(logGen,theCurItem);  // really the from item
                    setOutput();
                    reset(); // reset all attr info

                    // output removed
                    chgType = FMChgLogGen.REMOVED;
                    lastEditor = deleteUser;
                    dateChg = deleteDate;
                    setRowValues(logGen,delItem); // really the current item, the last one
                    setOutput();

                    createList.dereference();
                    deleteList.dereference();
                    isoDate.dereference();
                }
            }
        }
        return dtsArray;  // get suppdev MODELa using these timestamps [0] is create, [1] is delete
    }

    /********************************************************************************
    * Get FM code
    * FM is derived as follows:
    *
    * Use entity FEATURE to find a matching MAPFEATURE. The matching is based on INVENTORYGROUP, HWFCCAT, and HWFCSUBCAT.
    * Given a matching entity, then this gives FMGROUPCODE and FMSUBGROUPCODE. Concatenate these two values.
    * This yields a two character code.
    * @param featItem EntityItem  FEATURE to derive FM
    * @return String
    * @throws java.lang.Exception
    */
    protected String deriveFM(FMChgLogGen logGen,EntityItem featItem) throws Exception
    {
        String invGrp = FMChgLogGen.getAttributeFlagValue(featItem, "INVENTORYGROUP");
        String category = FMChgLogGen.getAttributeFlagValue(featItem, "HWFCCAT");
        String subCat = FMChgLogGen.getAttributeFlagValue(featItem, "HWFCSUBCAT");

        String fm= logGen.getFMmapping(invGrp+category+subCat);
        if (fm.length()==0)
        {
            String invGrpDesc = FMChgLogGen.getAttributeValue(featItem, "INVENTORYGROUP", "", "");
            String categoryDesc = FMChgLogGen.getAttributeValue(featItem, "HWFCCAT", "", "");
            String subCatDesc = FMChgLogGen.getAttributeValue(featItem, "HWFCSUBCAT", "", "");

            logGen.logDataError(featItem.getKey()+" did not have a FM mapping for "+invGrpDesc+categoryDesc+subCatDesc+
                " ["+invGrp+category+subCat+"] on "+featItem.getProfile().getValOn());
            logGen.trace(D.EBUG_WARN,false,featItem.getKey()+" did not have a FM mapping for "
                +invGrpDesc+categoryDesc+subCatDesc+" ["+invGrp+category+subCat+"] on "+
                featItem.getProfile().getValOn());
        }

        return fm;
    }

    /********************************************************************************
    * Get FM code
    * FM is derived as follows:
    *
    * Use entity MODELc to find a matching MAPSUPPDEVICE. The matching is based on
    * INVENTORYGROUP, and FMGROUP. Given a matching entity, then this gives FMGROUPCODE.
    * This yields a one character code.
    * @param mdlcItem EntityItem  MODELc to derive FM
    * @return String
    * @throws java.lang.Exception
    */
    protected String deriveSDFM(FMChgLogGen logGen,EntityItem mdlcItem) throws Exception
    {
        String invGrp=logGen.getMODELInvGrp(mdlcItem);
        String fm="";
        if (invGrp !=null) {  // MODELc MACHTYPEATR isn't linked to the WG
			String fmGrp = FMChgLogGen.getAttributeFlagValue(mdlcItem, "COMPATDVCCAT");
			fm= logGen.getFMSDmapping(invGrp+fmGrp);
			if (fm.length()==0)
			{
				String fmGrpDesc = FMChgLogGen.getAttributeValue(mdlcItem, "COMPATDVCCAT", "", "");
				logGen.logDataError(mdlcItem.getKey()+" did not have a SUPPDEVICE-FM mapping for "+logGen.getInvGrpDesc(invGrp)+
					fmGrpDesc+" ["+invGrp+fmGrp+"] on "+mdlcItem.getProfile().getValOn());
				logGen.trace(D.EBUG_WARN,false,"FMChgSet.deriveSDFM() "+mdlcItem.getKey()+" did not have a SUPPDEVICE-FM mapping for "+
					logGen.getInvGrpDesc(invGrp)+fmGrpDesc+" ["+invGrp+fmGrp+"] on "+mdlcItem.getProfile().getValOn());
			}
        }else {
            logGen.trace(D.EBUG_ERR,false,"FMChgSet.deriveSDFM() ERROR cannot derive FM, invGrp is null for "+mdlcItem.getKey());
            fm="";
        }

        return fm;
    }

    /********************************************************************************
    * Access private class functionality for adjusting a creation date
    * @param createDate String
    * @param deleteDate String
    * @return String
    */
    static String getISOEntityAdjustedDate(String createDate, String deleteDate)
    {

        ISOEntityDate isoEntityDate = new ISOEntityDate(createDate, deleteDate);
        String adjDate = isoEntityDate.getAdjustedDate();
        isoEntityDate.dereference();
        return adjDate;
    }

    /********************************************************************************
    * Access private class functionality for adjusting a deletion date
    * @param createDate String
    * @param deleteDate String
    * @return String
    */
    static String getISOEntityAdjustedDeleteDate(String createDate, String deleteDate)
    {
        ISOEntityDate isoEntityDate = new ISOEntityDate(createDate, deleteDate);
        String adjDate = isoEntityDate.getAdjustedDeleteDate();
        isoEntityDate.dereference();
        return adjDate;
    }

    /********************************************************************************
    * Convenience class
    * Used to adjust a date between entity creation and deletion date
    * Entity table is updated before Attribute table on a create
    * and updated after on a delete.  So can't use timestamps from Entity chg history
    * to do a pull and get valid attributes.
    *   FEATURE43[1] entity chgdate: 2005-05-27-08.57.38.935354
    *   FEATURE43[0] entity chgdate: 2005-05-27-08.31.15.102725
    *   AttributeChangeHistory for INVENTORYGROUP in FEATURE43
    *   FEATURE43 for INVENTORYGROUP[1] chgdate: 2005-05-27-08.57.38.402897
    *   FEATURE43 for INVENTORYGROUP[0] chgdate: 2005-05-27-08.31.25.402403
    Java allows classes to contain other classes.  Since Java byte code does not
    have this concept, the compiler translates inner classes into package-private
    classes.  Further, any "private" method or field in the container class that
    the inner class accesses can be seen by other classes in the same "package",
    with the exception that other classes in the package cannot write to a private
    field of the container class unless the inner class also writes to that field.
    */
    static private class ISOEntityDate
    {
        private GregorianCalendar calendar = new GregorianCalendar(); // doesnt' seem like any DateFormat will parse an ISO date
        private String microSecStr="";
        private String isoDate;
        private String delDate;
        private static final int MAGIC_NUMBER = 40;  // seconds to subtract/add from entity deletion/creation dts

        ISOEntityDate(String createDate, String deleteDate)
        {
            isoDate = createDate;
            delDate = deleteDate;
        }
        void dereference()
        {
            calendar = null;
            microSecStr = null;
            isoDate = null;
            delDate = null;
        }
        private void init(String theDate)
        {
            calendar.clear();
            calendar.set(Calendar.YEAR,Integer.parseInt(theDate.substring(0,4)));
            calendar.set(Calendar.MONTH,Integer.parseInt(theDate.substring(5,7))-1); // months are counted from 0
            calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(theDate.substring(8,10)));
            calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(theDate.substring(L11,L13)));
            calendar.set(Calendar.MINUTE,Integer.parseInt(theDate.substring(L14,L16)));
            calendar.set(Calendar.SECOND,Integer.parseInt(theDate.substring(L17,L19)));
            calendar.set(Calendar.MILLISECOND,0); // really want to set MICROSECOND but it doesn't support it
            microSecStr = theDate.substring(L20);
            while(microSecStr.length()<6){  // trailing 0 are dropped, restore
            	microSecStr=microSecStr+"0";
            }
        }

        String getAdjustedDate()  // add time to createdate
        {
            int amount = MAGIC_NUMBER; // seconds
            String tmpDate = null;
            init(isoDate);

            // add 40 seconds to createdate and make sure it is less than delete date
            tmpDate = adjustDate(amount);
            while(amount>0 && delDate.compareTo(tmpDate)<0)  {
                init(isoDate);  // reset the calendar
                amount-=10;
                tmpDate = adjustDate(amount);
            }
            return tmpDate;
        }
        String getAdjustedDeleteDate() // subtract time from delete date
        {
            int amount = MAGIC_NUMBER; // seconds
            String tmpDate = null;
            init(delDate);

            // subtract 40 seconds from deldate and make sure it is more than create date
            tmpDate = adjustDate(-amount);
            while(amount>0 && isoDate.compareTo(tmpDate)>0)  {
                init(delDate);  // reset the calendar
                amount-=10;
                tmpDate = adjustDate(-amount);
            }

            // if the calculated date ends up being the delete date, DONT use it, code must use the
            // last active date
            /* MN26522550
FM1eServer getMTMChgs() relator PRODSTRUCT139173 was DELETED
FM1eServer FMChgSet.calculateMTMOutput() PRODSTRUCT139173 Entity CREATE isActive: true isValid: false chgdate: 2005-12-06-12.04.51.243633
FM1eServer FMChgSet.calculateMTMOutput() PRODSTRUCT139173 Entity LAST isActive: false isValid: false chgdate: 2006-01-03-11.18.25.633452
FM1eServer FMChgSet.calculateMTMOutput() REMOVED fromItem PRODSTRUCT139173
FM1eServer PRODSTRUCT139173 CHI[3] isActive: false isValid: false chgdate: 2006-01-03-11.18.25.633452
FM1eServer PRODSTRUCT139173 CHI[2] isActive: true isValid: true chgdate: 2006-01-03-11.18.25.569265
FM1eServer FMChgSet.handleRemovedMTM() fromItem PRODSTRUCT139173 lastActiveDate: 2006-01-03-11.18.25.569265 deleteDate: 2006-01-03-11.18.25.633452
FMChgLogGen.pullEntityItems() for extract: EXRPT3FM root FEATURE id count: 1 valon: 2006-01-03-11.18.25.633452
ERROR: null
java.lang.NullPointerException
    at com.ibm.oim30.isgfm.FMChgMTMSet.setRowValues(FMChgMTMSet.java:321)
    at com.ibm.oim30.isgfm.FMChgSet.handleRemovedMTM(FMChgSet.java(Compiled Code))
            */

            if(tmpDate.equals(delDate)) {
                tmpDate=isoDate;
                init(isoDate);  // reset the calendar, this probably isn't needed, but set it to the date used
            }
            return tmpDate;
        }
        private String adjustDate(int amount)
        {
            StringBuffer datesb =null;
            int dts = 0;
            // must manipulate date, add X seconds?
            calendar.add(Calendar.SECOND, amount);
            // build new date string
            datesb = new StringBuffer(calendar.get(Calendar.YEAR)+"-");
            dts = calendar.get(Calendar.MONTH)+1; // months are counted from 0
            if (dts<10) {
                datesb.append("0");}
            datesb.append(dts+"-");
            dts = calendar.get(Calendar.DAY_OF_MONTH);
            if (dts<10) {
                datesb.append("0");}
            datesb.append(dts+"-");
            dts = calendar.get(Calendar.HOUR_OF_DAY);
            if (dts<10) {
                datesb.append("0");}
            datesb.append(dts+".");
            dts = calendar.get(Calendar.MINUTE);
            if (dts<10) {
                datesb.append("0");}
            datesb.append(dts+".");
            dts = calendar.get(Calendar.SECOND);
            if (dts<10) {
                datesb.append("0");}
            datesb.append(dts+"."+microSecStr);

            return datesb.toString();
        }
    }
}
