// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.nav;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.NLSItem;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.nav.SerialObjectController;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;

/**
 * This class is written to disk and read back in when history is selected
 * Attempt to improve performance by hanging onto the entitylist and using
 * the list.pullDTS to determine if a new pull is needed
 *
 * It is only instantiated by a SerialObjectController
 * @author Wendy Stimpson
 */
//$Log: NavSerialObject.java,v $
//Revision 1.12  2015/02/27 12:24:05  stimpsow
//prevent null ptr if ro() fails
//
//Revision 1.11  2014/05/05 21:03:14  wendy
//force refresh if action is queuesourced
//
//Revision 1.10  2014/02/26 20:11:55  wendy
//force refresh when language changes
//
//Revision 1.9  2014/01/16 16:30:11  wendy
//RTC 1080054 - back nav going to navigateentry when homeenabled was set in the action
//
//Revision 1.8  2013/09/25 11:09:03  wendy
//prevent null ptr
//
//Revision 1.7  2013/07/31 18:06:25  wendy
//correct gotoprev with pinned nav
//
//Revision 1.6  2013/07/30 20:05:41  wendy
//added to trace msgs
//
//Revision 1.5  2013/07/18 18:29:18  wendy
//fix compiler warnings
//
//Revision 1.4  2013/05/01 18:35:12  wendy
//perf updates for large amt of data
//
//Revision 1.3  2013/03/11 14:30:33  wendy
//check for null navlist
//
//Revision 1.2  2013/02/05 18:23:21  wendy
//throw/handle exception if ro is null
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class NavSerialObject implements Serializable, EACMGlobals {
	static final long serialVersionUID = 19721222L;
	static int count=0;
	int mycnt=0; //used in debugging and logging
	private EANActionItem actionItem = null;
	private int nlsid=0;  // readlang is changed in the profile, so must maintain it here
	private EntityItem[] rootItems = null;
	private transient EntityList navList = null;		//perftest
	private String listExecutionDTS=null;
	private String fileName = null;
	private String strDisplayName = null;
	private transient SerialObjectController controller=null;

	/**
	 * Go thru controller for all writes
	 * @param sc
	 */
	protected void setController(SerialObjectController sc) {
		controller = sc;
	}

	/**
     * navSerialObject - used to load bookmarks
     * @param s
     * @param action
     * @param ei
     *
     */
    protected NavSerialObject(String s, NavActionItem action, EntityItem[] ei) {
    	fileName =s;
    	setAction(action);
		setItems(ei);
    	Logger.getLogger(APP_PKG_NAME).log(Level.INFO," created NavSerialObject"+mycnt+" for "+
    			action.getActionItemKey());
	}

	/**
     * navSerialObject
     * @param s
     * @param eList
     *
     */
    protected NavSerialObject(String s, EntityList eList) {
    	fileName =s;
    	navList = eList;
    	setAction(navList.getParentActionItem());
    	setItems(navList.getParentEntityGroup());
    	setDisplay();
    	count++;
    	mycnt=count;
    	
    	Logger.getLogger(APP_PKG_NAME).log(Level.INFO," created NavSerialObject"+mycnt+" for "+
    			eList.getParentActionItem().getActionItemKey());
	}

	/**
     * getFileName
     * @return
     *
     */
    protected String getFileName() {
		return fileName;
	}

	/**
     * setAction
     * @param action
     *
     */
    private void setAction(EANActionItem action) {
		try {
			// remove unneeded info by instantiating a new action
			if (action instanceof NavActionItem) {
				actionItem = new NavActionItem((NavActionItem)action);
			} else if (action instanceof SearchActionItem) {
				actionItem = new SearchActionItem((SearchActionItem)action);
			}
			if(actionItem !=null){
				// hang onto the nlsid, it can get changed in the actionitem
		    	nlsid = actionItem.getProfile().getReadLanguage().getNLSID();
			}
		} catch (MiddlewareRequestException mre) {
			mre.printStackTrace();
		}
	}

	/**
     * getAction
     * @return
     *
     */
    protected EANActionItem getAction() {
		return actionItem;
	}

	private void setItems(EntityGroup group) {
		EntityItem[] tmp = null;
        if (group == null) {
			rootItems = null;
			return;
		}
		tmp = group.getEntityItemsAsArray();
		if (tmp == null) {
			rootItems = null;
			return;
		}

		setItems(tmp);
	}
	private void setItems(EntityItem[] tmp) {
		if (actionItem instanceof SearchActionItem) {
			rootItems = null;
			return;
		}
		rootItems = new EntityItem[tmp.length];
		for (int i=0;i<tmp.length;++i) {
			try {
				rootItems[i] = new EntityItem(null,tmp[i].getProfile(),tmp[i].getEntityType(), tmp[i].getEntityID());	//mem
			} catch (MiddlewareRequestException mre) {
				mre.printStackTrace();
				rootItems[i] = null;
			}
		}
	}
	/**
     * getEntityItems
     * @return
     *
     */
	protected EntityItem[] getEntityItems() {
		return rootItems;
	}

    protected EntityList getNavList() { // perftest
    	return navList;
    }

	/**
     * resetKeys - list is being refreshed, keep markers
     * @param key
     * @param ei
     *
     */
    protected void resetKeys(String key, EntityItem[] ei) {
    	if(setCursorKeys(ei,key)){
    		controller.write(this);
    	}
	}

	/**
     * dereference
     *
     */
    protected void dereference() {
    	actionItem = null;
		rootItems = null;
		if (navList!=null){
			navList.dereference();
			navList = null;
		}
		fileName = null;
		strDisplayName = null;
		controller = null;
		listExecutionDTS=null;
	}

	/**
     * save information on this EntityList
     * @param ean
     * @param ei
     * @param history
     */
    private void updateEntityListInfo(EANActionItem ean, EntityItem[] ei,NavHistBox history) {
    	if(controller==null){
    		return;  // allow for this coming in after tab is closed and deref was done
    	}
    	if(navList==null){ // will be null if could not contact server
    		return;
    	}
		if (ean != null) {
			setAction(ean);
		}
		setDisplay();
		if (ei != null) {
			setItems(ei);
		}
		if(history!=null && actionItem !=null){
			history.update(actionItem);
		}
		controller.write(this);
	}

	/**
     * replay - called when nav table is refreshed or when loaded from history
     * @param profile
     * @param history
     * @param forceRefresh
     * @param isPinned - the list will be loaded into a new navigate - DONT change anything in this object
     * @return
     */
    protected EntityList replay(Profile profile, NavHistBox history, boolean forceRefresh,
    		boolean isPinned) {
    	//output brief info
    	if(actionItem!=null){
    		if(rootItems!=null && rootItems.length>0){
        		Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"NavSerialObj"+mycnt+" forceRefresh: "+
        				forceRefresh+" replaying action (" + actionItem.getKey() + ") on...");
    			for (int i = 0; i < rootItems.length; ++i) {
    				if (rootItems[i] != null) {
    					Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"  ei[" + i + "]: " + rootItems[i].getKey());
    				}
    			}
    		} else{
        		Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"NavSerialObj"+mycnt+" forceRefresh: "+
        				forceRefresh+" replaying action (" + actionItem.getKey() + ")");
    		}
    	}
		if (actionItem instanceof SearchActionItem) {
			navList = DBUtils.doSearch((SearchActionItem)actionItem,profile, null);
			if (!isPinned && navList!=null){
				updateEntityListInfo(navList.getParentActionItem(),null,history);
			}
			return navList;
		}

		if (rootItems == null || rootItems.length == 0) {
			if (actionItem instanceof NavActionItem) {
				if (((NavActionItem)actionItem).isHomeEnabled()) {
					Long t11 =System.currentTimeMillis();
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" starting");
					// this may not be navigateentry, use the actionitem RTC 1080054
					navList = DBUtils.navigate((NavActionItem)actionItem, rootItems, profile, null);
					if (!isPinned){
						updateEntityListInfo(navList.getParentActionItem(),null,history);
					}
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" replay homeenabled ended "+Utils.getDuration(t11));
					return navList;
				}
			}
			Long t11 =System.currentTimeMillis();
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" noroots starting");
			navList = DBUtils.getNavigateEntry(profile);
			if (!isPinned){
				updateEntityListInfo(null,null,history);
			}

			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+"replay noroots ended "+Utils.getDuration(t11));
			return navList;
		}

		if (actionItem instanceof NavActionItem) {
			Long t11 =System.currentTimeMillis();
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" replay starting listExecutionDTS: "+listExecutionDTS);
			// check if reload from db is needed or not perftest
			boolean pullNeeded = true;
			String servertime = "";
			if(!forceRefresh){
				// queue sourced needs a pull again - gbl8199x isn't finding when the entity is deleted
				if (((NavActionItem)actionItem).isQueueSourced()){
					forceRefresh = true;
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" navaction is queuesourced, force refresh ");
				}
			}
	    	// put in own thread to improve performance
			//navlist is null when this is read from disk (deserialized) - back arrow or history selection
			if(listExecutionDTS!=null && !Utils.isPast(actionItem.getProfile())){ // check if profile.valon was dialed back
				boolean readingFromFile = false;
				if(navList==null){ // this is a replay from backarrow or from history
					readingFromFile = true;
					Runnable readList = new Runnable() {
						public void run() {
							Long t1 =System.currentTimeMillis();
							Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" reading navlist started");
							readListDone = false;
							navList = controller.readNavList(fileName);
							Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" reading in navlist ended "+Utils.getDuration(t1));

							readListDone();
						}
					};

					Thread t = new Thread(readList);
					t.start();
				}

				if(!forceRefresh){
					Long t1 =System.currentTimeMillis();
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" check haschgs starting");
					pullNeeded = hasChanges(profile); // this will flag chgs in nav attributes and structure
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" check haschgs pullneeded "+pullNeeded+" ended "+Utils.getDuration(t1));
				}
				
				if(!pullNeeded){
					servertime = RMIMgr.getRmiMgr().getServerTime();
					if(readingFromFile){
						waitForReadList();
					}
				}
			}

			if (pullNeeded || navList==null){
			//end perftest
				navList = DBUtils.navigate((NavActionItem)actionItem, rootItems, profile, null);
				if (!isPinned && navList!=null){
					updateEntityListInfo(navList.getParentActionItem(),
							navList.getParentEntityGroup().getEntityItemsAsArray(),history);
				}
			}else{
				//reset pullDTS to servertime
				listExecutionDTS = servertime;
			}

			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"NavSerialObj"+mycnt+" replay ended "+Utils.getDuration(t11));

			return navList;
		}

		return navList;
	}  

    /**
     * edit has completed, nav attrs were replaced
     */
    protected void updateLastExecDTS(){
    	listExecutionDTS = RMIMgr.getRmiMgr().getServerTime();
    }
    
    private boolean hasChanges(Profile profile){
    	boolean hasChgs = true;
    	long t1 =System.currentTimeMillis();
    	Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
    	try {
    		// if read languages dont match, no need to check for other changes
    		if(profile.getReadLanguage().getNLSID() == nlsid){
    		//if(profile.getReadLanguage().equals(actionItem.getProfile().getReadLanguage())){
    			hasChgs = RMIMgr.getRmiMgr().getRemoteDatabaseInterface().hasChanges(profile,
    				rootItems, listExecutionDTS, actionItem.getActionItemKey());
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}

    	Logger.getLogger(TIMING_LOGGER).log(Level.INFO," ended "+Utils.getDuration(t1));

    	return hasChgs;
    }

    private boolean readListDone = false;
    private synchronized void readListDone(){
    	readListDone = true;
    	notifyAll();
    }
    private synchronized void waitForReadList() {
        //This only loops once for each special event, which may not
        //be the event we're waiting for.
        while(!readListDone) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }

	/**
     * setDisplay
     *
     */
    private void setDisplay() {
    	listExecutionDTS=navList.getExecutionDTS();
    	
    	//RQ0927066214
    	if (navList.getParentActionItem()instanceof NavActionItem){
    		strDisplayName = navList.getBreadCrumbs(50,",",":");
    	}else{
    		strDisplayName = navList.toString();
    		// hack to get around problem when action is created under nlsid!=1 but is switched to nlsid=1
    		if(strDisplayName.trim().toLowerCase().startsWith("from")){
    			java.util.Vector<?> langsVct = navList.getProfile().getReadLanguages();
    			for(int i=0; i<langsVct.size(); i++){
    				NLSItem nls = (NLSItem)langsVct.elementAt(i);
    				String tmp = navList.getParentActionItem().getLongDescription(nls.getNLSID());
    				if (tmp.trim().length()>0){
    					strDisplayName = tmp +strDisplayName;
    					break;
    				}
    			}
    		}
    	}
    	if (actionItem != null) {
    		actionItem.setDisplayName(strDisplayName);
		}
	}

	/**
     * @see java.lang.Object#toString()
     *
     */
    public String toString() {
    	String tmp = strDisplayName;
		if (actionItem != null) {
			tmp = actionItem.getDisplayName();		//bui-jui bookmark history
			if (tmp == null) {							//bui-jui bookmark history
				tmp = actionItem.toString();				//bui-jui bookmark history
			}											//bui-jui bookmark history
		}
		return Routines.truncate(tmp,150); // limit length for history list
	}

	/**
     * setCursorKeys - used in restoring cursor from history
     * @param ei
     * @param s
     */
    protected boolean setCursorKeys(EntityItem[] ei, String s) {
    	boolean changesFnd = false; // dont need to write this out if no changes were found

    	if (actionItem ==null) {
    		return changesFnd;
    	}
		String []curkeys =actionItem.getActionHistory().getSelectionKeys();
		if (ei != null) {
			int ii = ei.length;
			String [] keys = new String[ii];
			for (int i=0;i<ii;++i) {
				keys[i] = ei[i].getKey();
			}
			// look for any changes
			if(curkeys!=null && curkeys.length==keys.length){
				for(int i=0;i<curkeys.length;i++){
	    			if(!curkeys[i].equals(keys[i])){
	    				changesFnd = true;
	    				break;
	    			}
	    		}
			}else{
				changesFnd = true;
			}
			if(changesFnd){
				actionItem.getActionHistory().setSelectionKeys(keys);
			}
		}

		if (s != null) {
			String curkey = actionItem.getActionHistory().getSelectionKey();
			if(curkey==null || !curkey.equals(s)){
				actionItem.getActionHistory().setSelectionKey(s);
				changesFnd = true;
			}
		}
		
		return changesFnd;
	}

	/**
     * getKeys - used in restoring selection and cursor location
     * @return
     *
     */
    protected String[] getKeys() {
		if (actionItem != null) {
			return actionItem.getActionHistory().getSelectionKeys();
		}
		return null;
	}


	/**
     * getKey - used in restoring selection and cursor location
     * @return
     *
     */
    protected String getKey() {
		if (actionItem != null) {
			return actionItem.getActionHistory().getSelectionKey();
		}
		return null;
	}
}
