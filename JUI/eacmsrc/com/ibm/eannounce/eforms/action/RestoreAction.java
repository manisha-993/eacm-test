/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: RestoreAction.java,v $
 * Revision 1.7  2009/10/27 19:21:17  wendy
 * Prevent null ptr in restore
 *
 * Revision 1.6  2009/06/09 11:32:43  wendy
 * Performance cleanup
 *
 * Revision 1.5  2009/05/26 13:00:13  wendy
 * Performance cleanup
 *
 * Revision 1.4  2008/02/21 19:18:51  wendy
 * Add access to change history for relators
 *
 * Revision 1.3  2008/01/30 16:27:03  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/08/02 00:19:28  wendy
 * prevent hang
 *
 * Revision 1.1  2007/04/18 19:43:53  wendy
 * Reorganized JUI module
 *
 * Revision 1.5  2007/03/27 14:38:02  wendy
 * MN31311135 prevent null ptr
 *
 * Revision 1.4  2006/11/09 15:51:07  tony
 * more monitor logic
 *
 * Revision 1.3  2006/04/20 19:07:49  tony
 * adjusted restore logic so that
 * restore is run in a sep thread.
 * this will prevent turning gray.
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:52  tony
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
 * Revision 1.3  2004/03/31 20:40:45  tony
 * 53771:600C35
 *
 * Revision 1.2  2004/02/20 22:17:59  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.21  2003/12/17 19:14:17  tony
 * acl_20031217
 * updated hidden logic and added comment for previous token
 *
 * Revision 1.20  2003/10/23 22:26:59  tony
 * 52682
 *
 * Revision 1.19  2003/10/20 16:37:13  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.18  2003/10/14 15:26:41  tony
 * 51832
 *
 * Revision 1.17  2003/09/30 16:34:49  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.16  2003/08/15 15:37:27  tony
 * cr_0805036452
 *
 * Revision 1.15  2003/07/29 16:58:29  tony
 * 51555
 *
 * Revision 1.14  2003/07/25 18:37:59  tony
 * updated restore logic
 *
 * Revision 1.13  2003/07/25 17:16:10  tony
 * updated restore capability
 *
 * Revision 1.12  2003/07/25 15:42:13  tony
 * updated restore logic.
 *
 * Revision 1.11  2003/07/24 17:31:52  tony
 * restore functionality addition.
 * First pass complete.
 *
 * Revision 1.10  2003/07/23 20:41:57  tony
 * added and enhanced restore logic
 *
 * Revision 1.9  2003/07/22 23:28:59  tony
 * updated logic
 *
 * Revision 1.8  2003/07/18 18:59:09  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.7  2003/07/09 15:08:59  tony
 * 51417 -- (did not tag) updated menu creation logic
 * to use the properties file instead of constant values.
 *
 * Revision 1.6  2003/07/07 14:55:27  tony
 * added process methods
 *
 * Revision 1.5  2003/07/03 17:26:56  tony
 * updated logic to improve scripting
 *
 * Revision 1.4  2003/04/03 16:19:07  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.3  2003/03/11 00:33:24  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/04 22:34:50  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.29  2002/11/11 22:55:38  tony
 * adjusted classification on the toggle
 *
 * Revision 1.28  2002/11/07 16:58:24  tony
 * added/adjusted copyright statement
 *
 */

package com.ibm.eannounce.eforms.action;
import com.elogin.EAccess;
import com.elogin.EPanel;
import com.elogin.ESwingWorker;
//import com.ibm.eannounce.eserver.ChatAction;
import com.ibm.eannounce.eforms.table.RestoreTable;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.Event;
import java.awt.event.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class RestoreAction extends Action {
	private static final long serialVersionUID = 1L;
	private RestoreTable table = null;
    private ETimeDate timeDate = new ETimeDate();
    private EScrollPane jsp = new EScrollPane();
    private EannounceToolbar tBar = null;
    private EPanel pnlMain = new EPanel(new java.awt.BorderLayout());
    private Profile prof = null;
    private InactiveGroup ig = null;

    /**
     * restoreAction
     * @param _parent
     * @param _o
     * @author Anthony C. Liberto
     */
    protected RestoreAction(ActionController _parent, Object _o) {
        super(_parent);//, _o);
        prof = getActiveProfile();
        createMenus();
        if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            tBar = ToolbarController.generateToolbar(DefaultToolbarLayout.RESTORE_BAR, this, null);
            add(tBar.getAlignment(), tBar);
        }
        pnlMain.add("North", timeDate);
        timeDate.setBorder(null);
        pnlMain.add("Center", jsp);
        add("Center", pnlMain);
        timeDate.setDateOpaque(true);
        timeDate.setDateValidatorType(com.ibm.eannounce.eforms.editor.MetaValidator.PAST_DATE_VALIDATOR);
        timeDate.setPast(true, true);
        refresh();
    }

    /**
     * requestFocus
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void requestFocus(int _i) {
        requestFocus();
    }

    /**
     * requestFocus
     *
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (hasRows()) {
            table.requestFocus();
        } else if (timeDate != null) {
            timeDate.requestFocus();
        }
    }

    private boolean hasRows() {
        if (table != null && table.getRowCount() > 0) {
            return true;
        }
        return false;
    }

    /**
     * setEnabled
     * @author Anthony C. Liberto
     * @param _enabled
     * @param _key
     */
    public void setEnabled(String _key, boolean _enabled) {
        getMenubar().setEnabled(_key, _enabled);
        if (tBar != null) {
            tBar.setEnabled(_key, _enabled);
        }
    }

    /**
     * createMenus
     *
     * @author Anthony C. Liberto
     */
    protected void createMenus() {
        createFileMenu();
        createActionMenu();
        createTableMenu();
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        appendLog("restoreAction.actionPerformed(" + action + ")");
        if (isBusy()) {
        	appendLog("    I am busy");
        	return;
        }
        try{
        	setBusy(true);
        	if (action.equals("clsT")) {
        		eaccess().close(getActionController());
        		return;
        	} else if (action.equals("clsA")) {
        		eaccess().closeAll();
        		return;
        	} else if (action.equals("exit")) {
        		eaccess().exit("exit restore");
        		return;
        	} 
        	Long t11 = EAccess.eaccess().timestamp("RestoreAction performAction "+action+" started");
        	if (action.equals("saveT")) {
        		eaccess().save(table);
        	} else if (action.equals("rfrsh")) {
        		refresh();
        	} else if (action.equals("rstr")) {
        		restore();
        	} else if (action.equals("left")) {
        		moveColumn(true);
        	} else if (action.equals("right")) {
        		moveColumn(false);
        	} else if (action.equals("selA")) {
        		selectAll();
        	} else if (action.equals("iSel")) {
        		invertSelection();
        	} else if (action.equals("srt")) {
        		sort();
        	} else if (action.equals("hide")) { //51832
        		showHide(true); //51832
        	} else if (action.equals("unhide")) { //51832
        		showHide(false); //51832
        	} else if (action.equals("f/r")) {
        		find();
        	} else if (action.equals("fltr")) {
        		filter();
        	} else if (action.equals("histI") || action.equals("histR")) {
        	} else if (action.equals("fmi")) {
        		showInformation();
        	}
        	EAccess.eaccess().timestamp("RestoreAction performAction "+action+" ended",t11);
        }catch(Exception exc){
        	eaccess().showException(exc, null,ERROR_MESSAGE,OK);
        } 
        setBusy(false);
    }

    private void createActionMenu() {
        String strKey = getString("act");
        //52682		getMenubar().addMenu(strKey,"rstr",this,KeyEvent.VK_R, Event.CTRL_MASK,true);
        getMenubar().addMenu(strKey, "rstr", this, KeyEvent.VK_R, Event.CTRL_MASK, !isReadOnly()); //52682
        getMenubar().addMenu(strKey, "rfrsh", this, KeyEvent.VK_F5, 0, true);
        getMenubar().setMenuMnemonic(strKey, getChar("act-s"));
    }

    /**
     * closeLocalMenus
     *
     * @author Anthony C. Liberto
     */
    private void closeLocalMenus() {
		if (getMenubar() != null ){//MN31311135 prevent null ptr if deref is called before this is
			getMenubar().removeMenuItem("rstr", this);
			getMenubar().removeMenuItem("rfrsh", this);
			getMenubar().removeMenu(getString("act"));
        }
    }

    /**
     * moveColumn
     *
     * @param _left
     * @author Anthony C. Liberto
     */
    private void moveColumn(boolean _left) {
        if (table != null) {
            table.moveColumn(_left);
        }
    }

    /**
     * selectAll
     * @author Anthony C. Liberto
     */
    private void selectAll() {
        if (table != null) {
            table.selectAll();
        }
    }

    /**
     * invertSelection
     * @author Anthony C. Liberto
     */
    private void invertSelection() {
        if (table != null) {
            table.invertSelection();
        }
    }

    /**
     * find
     *
     * @author Anthony C. Liberto
     */
    private void find() {
        if (table != null) {
            table.showFind();
        }
    }

    /**
     * filter
     *
     * @author Anthony C. Liberto
     */
    private void filter() {
        if (table != null) {
            table.showFilter();
        }
    }

    /**
     * showInformation
     * @author Anthony C. Liberto
     */
    private void showInformation() {
        if (table != null) {
            table.showInformation();
        }
    }

    /**
     * sort
     * @author Anthony C. Liberto
     */
    private void sort() {
        if (table != null) {
            table.showSort();
        }
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        closeLocalMenus();
        if (table != null) {
            table.dereference();
            //table.removeAll();
            //table.removeNotify();
            table = null;
        }

        if (jsp != null) {
            jsp.dereference();
            //jsp.removeAll();
           // jsp.removeNotify();
            jsp = null;
        }

        if (timeDate != null) {
            timeDate.dereference();
           // timeDate.removeAll();
           // timeDate.removeNotify();
            timeDate = null;
        }

        if (tBar != null) {
            tBar.dereference();
            //tBar.removeAll();
            //tBar.removeNotify();
            tBar = null;
        }

        if (pnlMain != null) {
            pnlMain.dereference();
            //pnlMain.removeAll();
            //pnlMain.removeNotify();
            pnlMain = null;
        }

        prof = null;
        if (ig!=null){
        	ig.dereference();
        	ig = null;
        }
        super.dereference();
    }

    /**
     * getTableTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getTableTitle() {
        return "RestoreActionTable";
    }

    /**
     * isFiltered
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isFiltered() {
        if (table != null) {
            return table.isFiltered();
        }
        return false;
    }

    /**
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected Object getSearchableObject() {
        return table;
    }

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        if (table != null) {
            table.resizeCells();
        }
    }

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_LOCKACTION;
    }

    /**
     * createTableMenu
     *
     * @author Anthony C. Liberto
     */
    protected void createTableMenu() {
        String strKey = getString("tbl");
        //cr_0805036452		getMenubar().addMenu(strKey,"left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK + Event.SHIFT_MASK,true);
        getMenubar().addMenu(strKey, "left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK, true); //cr_0805036452
        //cr_0805036452		getMenubar().addMenu(strKey,"right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK + Event.SHIFT_MASK,true);
        getMenubar().addMenu(strKey, "right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK, true); //cr_0805036452
        getMenubar().addSeparator(strKey);
        getMenubar().addMenu(strKey, "srt", this, 0, 0, true);
        getMenubar().addSeparator(strKey); //51832
        getMenubar().addMenu(strKey, "hide", this, 0, 0, true); //51832
        getMenubar().addMenu(strKey, "unhide", this, 0, 0, true); //51832
        getMenubar().addSeparator(strKey);
        getMenubar().addMenu(strKey, "f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true);
        //cr_0805036452		getMenubar().addMenu(strKey,"fltr",this, KeyEvent.VK_F8, Event.CTRL_MASK, true);
        getMenubar().addMenu(strKey, "fltr", this, KeyEvent.VK_F8, 0, true); //cr_0805036452
        getMenubar().setMenuMnemonic(strKey, getChar("tbl-s"));
    }

    /**
     * removeTableMenu
     *
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
			getMenubar().removeMenuItem("f/r", this);
			getMenubar().removeMenuItem("fltr", this);
			getMenubar().removeMenu(getString("tbl"));
        }
    }

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
     * setTableAvailable
     * @param _b
     * @author Anthony C. Liberto
     */
    private void setTableAvailable(boolean _b) {
    	getMenubar().setMenuEnabled("Table", _b);
        //52682		setEnabled("rstr",_b);
        setEnabled("rstr", _b && !isReadOnly()); //52682
        setEnabled("f/r", _b);
        setEnabled("fltr", _b);
    }

    /**
     * refresh
     *
     * @author Anthony C. Liberto
     */
    private void refresh() {
        RowSelectableTable rst = getRestoreTable();
        if (rst != null) {
            if (table == null) {
                table = new RestoreTable(this, rst, getActionController());
                jsp.setViewportView(table);
            } else {
                table.updateModel(rst);
            }
        }
        setTableAvailable(hasRows());
        requestFocus();
    }

    private RowSelectableTable getRestoreTable() {
        ig = dBase().getInactiveGroup(timeDate.getProcessDate(), prof.hasRoleFunction(Profile.ROLE_FUNCTION_SUPERVISOR), this);
        if (ig != null) {
            return ig.getTable();
        }
        return null;
    }
/*
 lBahner 20060420
 restore goes gray so i am breaking out into sep thread.
 */
 	private void restore() {
		final JComponent c = this;
		final ESwingWorker myWorker = new ESwingWorker() {
			public Object construct() {
				try{
					InactiveItem[] inAct = (InactiveItem[]) table.getSelectedObjects(false);
					if (inAct != null) {
						ig = dBase().removeInactiveItem(ig, inAct, c);
						if (ig != null) {
							table.updateModel(ig.getTable());
							setTableAvailable(hasRows());
							table.resort();
							// table seems to not reset itself if sequential restores are done, list is wrong len
							java.util.Vector tmp = new java.util.Vector();
							for (int i=0; i<inAct.length; i++){
								if (inAct[i]!=null){
									tmp.add(inAct[i]);
								}
							}
							if (tmp.size()!=inAct.length){
								appendLog("RestoreAction.restore found NULLS in InactiveItem[] ");
								inAct = new InactiveItem[tmp.size()];
								tmp.copyInto(inAct);
								tmp.clear();
							}
							return inAct;
						}
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in RestoreAction.restore.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
					setBusy(false);
				}

				return null;
			}

			/*public boolean isBreakable() {
				return false;
			}*/

			public void finished() {
				try{
					Object o = getValue();
					if (o instanceof InactiveItem[]) {
						if (EAccess.isMonitor()) {
							EAccess.monitor("restore",(InactiveItem[])o);
						}
						eaccess().refresh((InactiveItem[])o, getActiveProfile(), CHILD_TYPE);
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in RestoreAction.restore.ESwingWorker.finish() "+ex);
					ex.printStackTrace();
				}
				setWorker(null);
				setBusy(false);
			}
		};
		setWorker(myWorker);
	}

    /**
     * getProfile
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected Profile getProfile() {
        return prof;
    }

    /**
     * viewRestoreExist
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean viewRestoreExist() {
        Profile myProf = getProfile();
        Profile actProf = getActiveProfile();
        if (myProf != null && actProf != null) {
            if (myProf.getEnterprise().equals(actProf.getEnterprise())) {
                if (myProf.getOPWGID() == actProf.getOPWGID()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * getTabMenuTitle
     *
     * @return
     */
    protected String getTabMenuTitleKey() { return "restore.title";}

    /**
     * getTabIcon
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getTabIconKey() {
        return "restore.icon";
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

    /*
     52682
     */
    /**
     * isReadOnly
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isReadOnly() {
        return eaccess().isReadOnly(getActiveProfile());
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
    
    // unused or old
    /**
     * getChatAction
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public ChatAction getChatAction() {
        return null;
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
 * update
 *
 * @return
 * @author Anthony C. Liberto
 * /
public boolean update() {
    return restoreUpdate();
}*/
/**
 * getTabTitle
 *
 * @return
 * @author Anthony C. Liberto
 * /
public String getTabTitle() {
    String name = null;
    setCode("tab.title");
    setParmCount(2);
    setParm(0, .getTableTitle());
    setParm(1, .getProfile().toString());
    name = getMessage();
    eaccess().clear();
    return name;
}
*/
/**
 * getTabToolTipText
 *
 * @return
 * @author Anthony C. Liberto
 * /
public String getTabToolTipText() {
    return getTabMenuTitle();
}*/
    /**
     * getTable
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public JTable getTable() {
        return table;
    }*/
    /**
     * sort
     *
     * @param _ascending
     * @author Anthony C. Liberto
     * /
    public void sort(boolean _ascending) {
        if (table != null) {
            table.sort(_ascending);
        }
    }*/

    /**
     * getHelpText
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String getHelpText() {
        return getString("nia");
    }*/
    /**
     * okToClose
     *
     * @param _b
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean okToClose(boolean _b) {
        return true;
    }*/

    /**
     * copy
     *
     * @author Anthony C. Liberto
     * /
    public void copy() {
    }*/

    /**
     * paste
     *
     * @author Anthony C. Liberto
     * /
    public void paste() {
    }*/

    /**
     * performAction
     * @author Anthony C. Liberto
     * @param _ai
     * @param _navType
     * /
    public void performAction(EANActionItem _ai, int _navType) {
    }*/

    /**
     * refreshMenu
     *
     * @author Anthony C. Liberto
     * /
    public void refreshMenu() {
    }*/

    /**
     * isHidden
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isHidden() {
        return false;
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
}
