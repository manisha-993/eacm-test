/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: LockAction.java,v $
 * Revision 1.4  2009/05/26 13:00:14  wendy
 * Performance cleanup
 *
 * Revision 1.3  2008/02/21 19:18:51  wendy
 * Add access to change history for relators
 *
 * Revision 1.2  2008/01/30 16:27:03  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:53  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2007/03/27 14:38:02  wendy
 * MN31311135 prevent null ptr
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.10  2005/03/28 17:56:37  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.9  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.8  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.7  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.5  2004/08/26 16:26:35  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.4  2004/08/09 21:22:12  tony
 * improved logging
 *
 * Revision 1.3  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.2  2004/02/20 22:17:59  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.27  2004/01/08 18:14:05  tony
 * 53412b
 *
 * Revision 1.26  2003/12/17 19:14:18  tony
 * acl_20031217
 * updated hidden logic and added comment for previous token
 *
 * Revision 1.25  2003/10/29 00:19:57  tony
 * removed System.out. statements.
 *
 * Revision 1.24  2003/10/14 15:26:40  tony
 * 51832
 *
 * Revision 1.23  2003/09/30 21:29:35  tony
 * 52395
 *
 * Revision 1.22  2003/09/30 16:34:49  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.21  2003/08/15 15:37:27  tony
 * cr_0805036452
 *
 * Revision 1.20  2003/07/18 18:59:08  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.19  2003/07/09 15:08:59  tony
 * 51417 -- (did not tag) updated menu creation logic
 * to use the properties file instead of constant values.
 *
 * Revision 1.18  2003/07/07 14:55:26  tony
 * added process methods
 *
 * Revision 1.17  2003/07/03 17:26:55  tony
 * updated logic to improve scripting
 *
 * Revision 1.16  2003/06/13 17:32:51  tony
 * 51255 resize on refresh issue.
 *
 * Revision 1.15  2003/06/09 23:05:51  tony
 * 51255
 *
 * Revision 1.14  2003/06/09 15:51:39  tony
 * 51228
 *
 * Revision 1.13  2003/06/05 22:11:55  tony
 * 51160
 *
 * Revision 1.12  2003/06/05 20:16:06  tony
 * 51157
 *
 * Revision 1.11  2003/05/30 21:09:21  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.10  2003/05/23 17:07:39  tony
 * 50892
 *
 * Revision 1.9  2003/05/21 15:47:42  tony
 * 50836
 *
 * Revision 1.8  2003/05/15 18:49:49  tony
 * updated persistant lock functionality.
 *
 * Revision 1.7  2003/05/09 16:51:27  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.6  2003/04/21 20:02:34  tony
 * updated logic to correlate to mw changes.
 *
 * Revision 1.5  2003/04/03 16:19:07  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.4  2003/03/19 20:36:58  tony
 * adjusted sort
 *
 * Revision 1.3  2003/03/11 00:33:23  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/04 22:34:50  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.27  2002/11/15 23:02:33  tony
 * fixed null pointer on close of locak action.
 *
 * Revision 1.26  2002/11/11 22:55:37  tony
 * adjusted classification on the toggle
 *
 * Revision 1.25  2002/11/07 16:58:24  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.action;
import com.elogin.EAccess;
import com.ibm.eannounce.eforms.table.LockTable;
//import com.ibm.eannounce.eserver.ChatAction;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.Event;
import java.awt.event.*;
//import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class LockAction extends Action {
	private static final long serialVersionUID = 1L;
	private LockTable table = null;
    private EScrollPane jsp = null;
    private EannounceToolbar tBar = null;
    private boolean bAllLock = false;
    private LockList locklist = null;

    /**
     * lockAction
     * @param _parent
     * @param _o
     * @author Anthony C. Liberto
     */
    protected LockAction(ActionController _parent, Object _o) {
        super(_parent);//, _o);
        locklist = (LockList) _o;
        createMenus();
        table = new LockTable(this, locklist.getTable(), _parent);
        jsp = new EScrollPane(table);
        adjustMenus(); //acl_Mem_20020204
        if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            tBar = ToolbarController.generateToolbar(DefaultToolbarLayout.LOCK_BAR, this, null);
            add(tBar.getAlignment(), tBar);
        }
        add("Center", jsp);
    }

    /**
     * requestFocus
     * @author Anthony C. Liberto
     * @param _i
     */
    protected void requestFocus(int _i) {
        table.requestFocus();
    }

    private boolean hasRows() { //acl_Mem_20020204
        if (table.getRowCount() > 0) { //acl_Mem_20020204
            return true;
        } //acl_Mem_20020204
        return false; //acl_Mem_20020204
    } //acl_Mem_20020204

    /**
     * setEnabled
     * @author Anthony C. Liberto
     * @param _enabled
     * @param _key
     */
    public void setEnabled(String _key, boolean _enabled) { //acl_Mem_20020204
        getMenubar().setEnabled(_key, _enabled); //acl_Mem_20020204
    } //acl_Mem_20020204

    private void adjustMenus() {
        boolean hasRows = hasRows();
        setEnabled("ulck", hasRows);
    }

    /**
     * createMenus
     * @author Anthony C. Liberto
     */
    protected void createMenus() {
        createFileMenu();
        createEditMenu(); //52395
        createActionMenu();
        createTableMenu();
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        appendLog("lockAction.actionPerformed(" + action + ")");
        if (isBusy()) {
            appendLog("    I am busy");
            return;
        }
        try{
        	setBusy(true);
        	if (action.equals("clsT")) {
        		eaccess().close(getActionController());
        		return;
        	} else if (action.equals("clsA")) { //20062
        		eaccess().closeAll(); //20062
        		return;
        	} else if (action.equals("exit")) {
        		eaccess().exit("Exit lock");
        		return;
        	} 
        	Long t11 = EAccess.eaccess().timestamp("LockAction performAction "+action+" started");
        	if (action.equals("saveT")) {
        		eaccess().save(locklist);
        	} else if (action.equals("rfrsh")) {
        		refresh(false);
        	} else if (action.equals("ulck")) {
        		unlock();
        		refresh(false);
        	} else if (action.equals("left")) {
        		table.moveColumn(true);
        	} else if (action.equals("right")) {
        		table.moveColumn(false);
        	} else if (action.equals("selA")) {
        		table.selectAll();
        	} else if (action.equals("iSel")) {
        		table.invertSelection();
        	} else if (action.equals("srt")) {
        		table.showSort();
        	} else if (action.equals("hide")) { //51832
        		showHide(true); //51832
        	} else if (action.equals("unhide")) { //51832
        		showHide(false); //51832
        	} else if (action.equals("f/r") || action.equals("find")) {
        		table.showFind();
        	} else if (action.equals("fltr")) {
        		table.showFilter();
        	} else if (action.equals("histI") || action.equals("histR")) {
        	} else if (action.equals("fmi")) {
        		table.showInformation();
        	}
        	EAccess.eaccess().timestamp("LockAction performAction "+action+" ended",t11);
        }catch(Exception exc){
        	eaccess().showException(exc, null,ERROR_MESSAGE,OK);
        } 
        setBusy(false);
    }

    /**
     * createActionMenu
     * @author Anthony C. Liberto
     */
    private void createActionMenu() {
        String strKey = getString("act");
        getMenubar().addMenu(strKey, "ulck", this, KeyEvent.VK_U, Event.CTRL_MASK, true);
        getMenubar().addSeparator(strKey);
        getMenubar().addMenu(strKey, "rfrsh", this, 0, 0, true);
        getMenubar().setMenuMnemonic(strKey, getChar("act-s"));
    }

    private void unlock() {
        LockList ll = getLockList();
        String out = null;
        if (ll != null) {
            LockItem[] li = getSelectedLockItems();
            if (li == null) {
                return;
            }
            out = dBase().unlock(ll, li);
            if (out != null) {
                setMessage(out);
                showError();
            }
        }
    }

    private LockItem[] getSelectedLockItems() {
        Object[] o = table.getSelectedObjects(false);
        return (LockItem[]) o;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() { //acl_Mem_20020204
    	if (locklist!=null){
    		locklist.dereference();
    		locklist = null;
    	}

        if (table != null) { //acl_Mem_20020204
            table.dereference(); //acl_Mem_20020204
            table = null; //acl_Mem_20020204
        } //acl_Mem_20020204

        if (jsp != null) { //acl_Mem_20020204
            jsp.dereference(); //acl_Mem_20020204
            jsp = null; //acl_Mem_20020204
        } //acl_Mem_20020204

        super.dereference(); //acl_Mem_20020204
    } //acl_Mem_20020204

    /*
     * non-used
     */
    /**
     * getTableTitle
     * @author Anthony C. Liberto
     * @return String
     */
    protected String getTableTitle() {
        return "lockActionTable";
    }

    /**
     * getProfile
     * @author Anthony C. Liberto
     * @return Profile
     */
    protected Profile getProfile() {
        //Object o = getObject();
    	if (locklist!=null){
    		return locklist.getProfile();
    	}
        //if (object instanceof LockList) {
          //  return ((LockList) object).getProfile();
       // }
        return null;
    }

    /**
     * isFiltered
     * @author Anthony C. Liberto
     * @return boolean
     */
    protected boolean isFiltered() {
        if (table != null) { //53412b
            return table.isFiltered();
        } //53412b
        return false; //53412b
    }

    /**
     * getSearchableObject
     * @author Anthony C. Liberto
     * @return Object
     */
    protected Object getSearchableObject() { //22377
        return table; //22377
    } //22377

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        table.resizeCells();
    }

    /**
     * getPanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getPanelType() {
        return TYPE_LOCKACTION;
    }

    /*
     50836
    */
    /**
     * getLockList
     * @author Anthony C. Liberto
     * @return LockList
     */
    public LockList getLockList() {
        //Object o = getObject();
    	if (locklist!=null){
    		return locklist;
    	}
       
        return super.getLockList();
    }
    /*
     50892
    */
    /**
     * setLockListAll
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setLockListAll(boolean _b) {
        bAllLock = _b;
    }

    /**
     * refresh
     * @param _resize
     * @author Anthony C. Liberto
     */
    private void refresh(boolean _resize) {
        LockList ll = null;
        table.clearSelection(); //51228
        //ll = getLockList();

        if (bAllLock) {
            ll = dBase().getAllSoftLocksForWGID(this);
            //setObject(ll);
        } else {
            ll = dBase().getLockList(true, this);
            //setObject(ll);
        }

        if (ll != null) {
        	locklist=ll;
            table.updateModel(ll.getTable(), _resize); //51255
            table.resort(); //51255
        }
    }

    /*
     51157
     */
    /**
     * createTableMenu
     * @author Anthony C. Liberto
     */
    protected void createTableMenu() {
        //cr_0805036452		getMenubar().addMenu("Table","left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK + Event.SHIFT_MASK,true);
    	getMenubar().addMenu("Table", "left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK, true); //cr_0805036452
        //cr_0805036452		getMenubar().addMenu("Table","right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK + Event.SHIFT_MASK,true);
    	getMenubar().addMenu("Table", "right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK, true); //cr_0805036452
    	getMenubar().addSeparator("Table");
    	getMenubar().addMenu("Table", "srt", this, 0, 0, true);
    	getMenubar().addSeparator("Table"); //51832
    	getMenubar().addMenu("Table", "hide", this, 0, 0, true); //51832
    	getMenubar().addMenu("Table", "unhide", this, 0, 0, true); //51832
    	getMenubar().addSeparator("Table");
        //52395		getMenubar().addMenu("Table","f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true);
        //cr_0805036452		getMenubar().addMenu("Table","fltr",this, KeyEvent.VK_F8, Event.CTRL_MASK, true);
    	getMenubar().addMenu("Table", "fltr", this, KeyEvent.VK_F8, 0, true); //cr_0805036452
    	getMenubar().setMenuMnemonic("Table", 'T');
    }

    /**
     * removeTableMenu
     * @author Anthony C. Liberto
     */
    protected void removeTableMenu() {
		if (getMenubar() != null ){//MN31311135 prevent null ptr if deref is called before this is
			getMenubar().removeMenuItem("left", this);
			getMenubar().removeMenuItem("right", this);
			getMenubar().removeMenuItem("selA", this);
			getMenubar().removeMenuItem("iSel", this);
			getMenubar().removeMenuItem("srt", this);
			getMenubar().removeMenuItem("hide", this); //51832
			getMenubar().removeMenuItem("unhide", this); //51832
			//52395		getMenubar().removeMenuItem("f/r", this);
			getMenubar().removeMenuItem("fltr", this);
			getMenubar().removeMenu("Table");
        }
    }

    /*
     51160
     */
    /**
     * viewLockExist
     * @param _b
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean viewLockExist(boolean _b) {
        if (bAllLock == _b) {
            Profile myProf = getProfile();
            Profile actProf = getActiveProfile();
            if (myProf != null && actProf != null) {
                if (myProf.getEnterprise().equals(actProf.getEnterprise())) {
                    if (myProf.getOPWGID() == actProf.getOPWGID()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     acl_20030718
     */
    /**
     * selectKeys
     * @author Anthony C. Liberto
     * @param _keys
     */
    protected void selectKeys(String[] _keys) {
        if (table != null) {
            table.selectKeys(_keys);
        }
    }
 
    /**
     * getTabMenuTitle
     * @author Anthony C. Liberto
     * @return String
     */
    protected String getTabMenuTitleKey() { 
    	String key = null;
    	if (bAllLock) {
    		key = "lock.wg.title";
        } else {
        	key = "lock.title";
        }
    	return key;
    }

    /**
     * getTabItem
     * @author Anthony C. Liberto
     * @return Icon
     */
    protected String getTabIconKey() {
        if (bAllLock) {
            return "lock.wg.icon";
        }
        return "lock.icon";
    }

    /*
     52395
     */
    /**
     * createEditMenu
     * @author Anthony C. Liberto
     */
    private void createEditMenu() {
    	getMenubar().addMenu("Edit", "f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true);
    	getMenubar().setMenuMnemonic("Edit", 'E');
    }

    /**
     * removeEditMenu
     * @author Anthony C. Liberto
     */
    private void removeEditMenu() {
		if (getMenubar() != null ){//MN31311135 prevent null ptr if deref is called before this is
			getMenubar().removeMenuItem("f/r", this);
			getMenubar().removeMenu("Edit");
		}
    }

    /**
     * removeActionMenu
     * @author Anthony C. Liberto
     */
    private void removeActionMenu() {
		if (getMenubar() != null ){//MN31311135 prevent null ptr if deref is called before this is
			getMenubar().removeMenuItem("ulck", this);
			getMenubar().removeMenuItem("rfrsh", this);
			getMenubar().removeMenu(getString("act"));
        }
    }

    /**
     * removeMenus
     * @author Anthony C. Liberto
     */
    protected void removeMenus() {
		if (getMenubar() != null ){//MN31311135 prevent null ptr if deref is called before this is
			removeFileMenu();
			removeEditMenu();
			removeActionMenu();
			removeTableMenu();
			getMenubar().removeAll();
			getMenubar().removeNotify();
        }
    }

    /*
     51832
     */
    /**
     * showHide
     * @param _b
     * @author Anthony C. Liberto
     */
    private void showHide(boolean _b) {
        if (table != null) {
            table.showHide(_b);
            eaccess().setHidden(_b); //acl_20031217
        }
    }

    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    protected void refreshToolbar() {
		if (tBar != null) {
			tBar.refreshToolbar();
		}
		refreshMenu();
	}
    // obsolete or unused methods

    /**
     * refresh
     * @author Anthony C. Liberto
     * /
    public void refresh() {
        refresh(false); //51255
        return; //51255
    } //51255
*/
    /**
     * getTabTitle
     * @author Anthony C. Liberto
     * @return String
     * /
    public String getTabTitle() {
        String name = null;
        setCode("tab.title");
        setParmCount(2);
        setParm(0, getActionController().getTableTitle());
        setParm(1, getActionController().getProfile().toString());
        name = getMessage();
        eaccess().clear();
        return name;
    }*/
    /**
     * getChatAction
     * @author Anthony C. Liberto
     * @return ChatAction
     * /
    public ChatAction getChatAction() {
        return null;
    }*/
    /**
     * getTabToolTipText
     * @author Anthony C. Liberto
     * @return String
     * /
    public String getTabToolTipText() {
        return getTabMenuTitle();
    }*/
    /**
     * process
     * @author Anthony C. Liberto
     * @param _action
     * @param _child
     * @param _method
     * @param _parent
     * /
    public void process(String _method, String _action, String[] _parent, String[] _child) {
    }*/
    /**
     * contains
     * @author Anthony C. Liberto
     * @return boolean
     * @param _eai
     * @param _ei
     * /
    public boolean contains(EntityItem[] _ei, EANActionItem _eai) {
        return false;
    }*/
    /**
     * okToClose
     * @author Anthony C. Liberto
     * @return boolean
     * @param _b
     * /
    protected boolean okToClose(boolean _b) {
        return true;
    }*/
    /**
     * copy
     * @author Anthony C. Liberto
     * /
    public void copy() {
    }*/
    /**
     * paste
     * @author Anthony C. Liberto
     * /
    public void paste() {
    }*/

    /**
     * isHidden
     * @author Anthony C. Liberto
     * @return boolean
     * /
    public boolean isHidden() {
        return false;
    }*/

    /**
     * update
     * @author Anthony C. Liberto
     * @return boolean
     * /
    public boolean update() {
        return true;
    }*/

    /**
     * moveColumn
     * @author Anthony C. Liberto
     * @param _left
     * /
    private void moveColumn(boolean _left) {
        table.moveColumn(_left);
    }*/

    /**
     * selectAll
     * @author Anthony C. Liberto
     * /
    private void selectAll() {
        table.selectAll();
    }*/

    /**
     * invertSelection
     * @author Anthony C. Liberto
     * /
    private void invertSelection() {
        table.invertSelection();
    }*/

    /**
     * sort
     * @author Anthony C. Liberto
     * @param _ascending
     * /
    public void sort(boolean _ascending) {
        table.sort(_ascending);
    }*/

    /**
     * getHelpText
     * @author Anthony C. Liberto
     * @return String
     * /
    public String getHelpText() {
        return getString("nia");
    }*/

    /**
     * find
     * @author Anthony C. Liberto
     * /
    private void find() {
        table.showFind();
    }*/

    /**
     * filter
     * @author Anthony C. Liberto
     * /
    private void filter() {
        table.showFilter();
    }*/

    /**
     * showInformation
     * @author Anthony C. Liberto
     * /
    private void showInformation() {
        table.showInformation();
    }*/

    /**
     * sort
     * @author Anthony C. Liberto
     * /
    private void sort() {
        table.showSort();
    }*/

    /**
     * \performAction
     * @author Anthony C. Liberto
     * @param _ai
     * @param _navType
     * /
    public void performAction(EANActionItem _ai, int _navType) {
    }*/

    /**
     * closeLocalMenus
     * @author Anthony C. Liberto
     * /
    private void closeLocalMenus() { //acl_Mem_20020201
        getMenubar().removeMenuItem("ulck", this); //acl_Mem_20020201
        getMenubar().removeMenuItem("rfrsh", this); //acl_Mem_20020201
        getMenubar().removeMenu(getString("act")); //acl_Mem_20020201
    } //acl_Mem_20020201
    */

    /**
     * getTable
     * @author Anthony C. Liberto
     * @return JTable
     * /
    public JTable getTable() {
        return table;
    }*/ 
    /**
     * refreshMenu
     * @author Anthony C. Liberto
     * /
    public void refreshMenu() {
        return;
    }*/

}
