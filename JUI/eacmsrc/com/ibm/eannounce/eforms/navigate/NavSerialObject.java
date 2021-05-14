// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
//import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.io.Serializable;
import com.ibm.eannounce.eforms.navigate.SerialObjectController;

/**
 * This class is written to disk and read back in when history is selected
 * Attempt to improve performance by hanging onto the entitylist and using
 * the list.pullDTS to determine if a new pull is needed
 * 
 * It is only instantiated by a SerialObjectController
 *
 * $Log: NavSerialObject.java,v $
 * Revision 1.6  2012/04/05 17:37:51  wendy
 * jre142 and win7 changes
 *
 * Revision 1.5  2009/08/25 12:25:24  wendy
 * more debug
 *
 * Revision 1.4  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.3  2009/04/16 17:54:42  wendy
 * Cleanup history
 *
 * Revision 1.2  2007/06/18 19:11:31  wendy
 * RQ0927066214
 * XCC GX SR001.5.001 EACM Enabling Technology - Bread Crumbs
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2006/06/19 19:32:57  tony
 * added timing logic for refresh functionality
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:02  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/05/06 17:46:25  tony
 * bui-jui bookmark history
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 */
public class NavSerialObject implements Serializable {
	static final long serialVersionUID = 19721222L;
	private static final int MAX_IDLE_TIME = 2; // max amount of time to allow when looking for chgs
	private EANActionItem action = null;		
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
     * @param _s
     * @param _action
     * @param _ei
     * 
     */
    protected NavSerialObject(String _s, NavActionItem _action, EntityItem[] _ei) {
    	fileName =_s;
    	setAction(_action);
		setItems(_ei);
	}
static int count=0;
int mycnt=0;
	/**
     * navSerialObject
     * @param _s
     * @param _eList
     * 
     */
    protected NavSerialObject(String _s, EntityList _eList) {	//21689
    	fileName =_s;
    	navList = _eList;
    	setAction(navList.getParentActionItem());
    	setItems(navList.getParentEntityGroup());
    	setDisplay(); 
    	count++;
    	mycnt=count;
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
     * @param _action
     * 
     */
    private void setAction(EANActionItem _action) {
		try {
			// remove unneeded info by instantiating a new action
			if (_action instanceof NavActionItem) {
				action = new NavActionItem((NavActionItem)_action);
			} else if (_action instanceof SearchActionItem) {
				action = new SearchActionItem((SearchActionItem)_action);
			}
		} catch (MiddlewareRequestException _mre) {
			_mre.printStackTrace();
		}
	}

	/**
     * getAction
     * @return
     * 
     */
    protected EANActionItem getAction() {
		return action;
	}

	private void setItems(EntityGroup _group) {
		EntityItem[] tmp = null;
        if (_group == null) {
			rootItems = null;
			return;
		}
		tmp = _group.getEntityItemsAsArray();
		if (tmp == null) {
			rootItems = null;
			return;
		}

		setItems(tmp);
	}
	private void setItems(EntityItem[] tmp) {
		if (action instanceof SearchActionItem) {
			rootItems = null;
			return;
		}
		rootItems = new EntityItem[tmp.length];
		for (int i=0;i<tmp.length;++i) {
			try {
				rootItems[i] = new EntityItem(null,tmp[i].getProfile(),tmp[i].getEntityType(), tmp[i].getEntityID());	//mem
            } catch (MiddlewareRequestException _mre) {
				_mre.printStackTrace();
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
     * resetKeys
     * @param _key
     * @param _ei
     * 
     */
    protected void resetKeys(String _key, EntityItem[] _ei) {		
    	setCursorKeys(_ei,_key);								
		controller.write(this);
	}																	

	/**
     * dereference
     * 
     */
    // list is shared sometimes and should not be derefed when the nso is changed
    protected void clearList(){
    	navList = null;
    }
    protected void dereference() {
		action = null;
		rootItems = null;
		if (navList!=null){
			navList.dereference();
			navList = null;
		}
		fileName = null;
		strDisplayName = null;
		controller = null;
	}

	/**
     * replayEntityList
     * @param _ean
     * @param _ei
     * @return
     * 
     */
    private void replayEntityList(EANActionItem _ean, EntityItem[] _ei) {
		setDisplay();
		if (_ean != null) {
			setAction(_ean);
		}
		if (_ei != null) {
			setItems(_ei);
		}
		controller.write(this);
	}

	/**
     * replay
     * @return
     */
    protected EntityList replay() {
		if (action instanceof SearchActionItem) {
			navList = EAccess.eaccess().dBase().getEntityList(action,null,null);
			if (navList!=null){
				replayEntityList(navList.getParentActionItem(),null);
			}
			return navList;
		}

		if (rootItems == null || rootItems.length == 0) {
			if (action instanceof NavActionItem) {
				if (((NavActionItem)action).isHomeEnabled()) {
					Long t11 = EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" replay homeenabled started");
					navList = EAccess.eaccess().dBase().getEntityList(action,null,null);
					replayEntityList(navList.getParentActionItem(),null);
					EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" replay homeenabled ended",t11);
					return navList;
				}
			}
			Long t11 = EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" replay noroots started");
			navList = EAccess.eaccess().dBase().getEntityList(null,null,null);
			replayEntityList(null,null);
			EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" replay noroots ended",t11);
			return navList;
		}

		if (action instanceof NavActionItem) {
			Long t11 = EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" replay started");
			// check if reload from db is needed or not perftest
			boolean pullNeeded = true;
			String servertime = "";
	    	// put in own thread to improve performance
			if(navList==null && listExecutionDTS!=null // this is a replay, not a refresh
					&& !EAccess.eaccess().isPast()){ // check if profile.valon was dialed back
				// check if large timerange since last pull
				String strServerTime = EAccess.eaccess().getTBase().getNOW();
				com.ibm.eannounce.ISOCalendar isoCalendar = new com.ibm.eannounce.ISOCalendar(strServerTime);
				if (isoCalendar.getDiffHours(listExecutionDTS)<MAX_IDLE_TIME){ // if more than this then do a full pull
					Runnable readList = new Runnable() {
						public void run() {
							Long t1 = EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" reading in navlist started");
							readListDone = false;
							navList = controller.readNavList(fileName);
							EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" DONE reading in navlist ",t1);
							readListDone();				
						}
					};

					Thread t = new Thread(readList);
					t.start();  

					Long t1 = EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" check haschgs started");
					pullNeeded = EAccess.eaccess().dBase().hasChanges(rootItems, listExecutionDTS, action.getActionItemKey());
					EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" check haschgs ended pullNeeded "+pullNeeded,t1);

					if(!pullNeeded){
						servertime =EAccess.eaccess().getTBase().getNOW();
						waitForReadList();
					}
				}
				isoCalendar.dereference();
			}
	        		
			if (pullNeeded || navList==null){
			//end perftest
				navList = EAccess.eaccess().dBase().getEntityList(action, rootItems, null);
				if(navList!=null){
					replayEntityList(navList.getParentActionItem(),
							navList.getParentEntityGroup().getEntityItemsAsArray());
				}
			}else{
				//reset pullDTS to servertime
				listExecutionDTS = servertime;
			}
			EAccess.eaccess().timestamp("NavSerialObj"+mycnt+" replay ended",t11);
			return navList;
		}

		return navList;
	}
    private boolean readListDone = false;
    private synchronized void readListDone(){
    	readListDone = true;
    	notifyAll();
    }
    private synchronized void waitForReadList() {
        //This only loops once for each special event, which may not
        //be the event we're waiting for.
    	//System.out.println("waitForReadList entered");
        while(!readListDone) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        //System.out.println("waitForReadList Joy and efficiency have been achieved!");
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
    	}
    	if (action != null) {
			action.setDisplayName(strDisplayName);
		}
	}

	/**
     * @see java.lang.Object#toString()
     * 
     */
    public String toString() {
    	String tmp = strDisplayName;
		if (action != null) {
			tmp = action.getDisplayName();		//bui-jui bookmark history
			if (tmp == null) {							//bui-jui bookmark history
				tmp = action.toString();				//bui-jui bookmark history
			}											//bui-jui bookmark history
		}
		return Routines.truncate(tmp,150); // limit length for history list
	}

	/**
     * setCursorKeys - used in restoring cursor from history
     * @param _ei
     * @param _s
     */
    protected void setCursorKeys(EntityItem[] _ei, String _s) {
    	if (action ==null) {
    		return;
    	}
		if (_ei != null) {
			int ii = _ei.length;
			String [] keys = new String[ii];
			for (int i=0;i<ii;++i) {
				keys[i] = _ei[i].getKey();
			}
			action.getActionHistory().setSelectionKeys(keys);
		}
			
		if (_s != null) {
			action.getActionHistory().setSelectionKey(_s);
		}		
	}

	/**
     * getKeys - used in restoring selection and cursor location
     * @return
     * 
     */
    protected String[] getKeys() {
		if (action != null) {
			return action.getActionHistory().getSelectionKeys();
		}
		return null;
	}


	/**
     * getKey - used in restoring selection and cursor location
     * @return
     * 
     */
    protected String getKey() {
		if (action != null) {
			return action.getActionHistory().getSelectionKey();
		}
		return null;
	}
}
