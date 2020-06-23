/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: UsedAction.java,v $
 * Revision 1.7  2009/05/26 13:00:13  wendy
 * Performance cleanup
 *
 * Revision 1.6  2008/02/21 19:18:51  wendy
 * Add access to change history for relators
 *
 * Revision 1.5  2008/01/30 16:27:03  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.4  2007/10/29 21:18:46  wendy
 * Prevent hang if nothing was selected when link attempted in matrix
 *
 * Revision 1.3  2007/08/15 15:11:24  wendy
 * RQ0713072645- Enhancement 3
 *
 * Revision 1.2  2007/08/02 00:22:39  wendy
 * prevent hang
 *
 * Revision 1.1  2007/04/18 19:43:53  wendy
 * Reorganized JUI module
 *
 * Revision 1.5  2007/03/27 14:38:03  wendy
 * MN31311135 prevent null ptr
 *
 * Revision 1.4  2006/11/09 15:51:07  tony
 * more monitor logic
 *
 * Revision 1.3  2006/03/16 22:01:28  tony
 * Capture logging
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:53  tony
 * This is the initial load of OPICM
 *
 * Revision 1.21  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.20  2005/03/28 17:56:37  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.19  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.18  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.17  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.16  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.15  2005/01/20 17:09:32  tony
 * fixed logic error
 *
 * Revision 1.14  2005/01/10 22:30:29  tony
 * multiple edit from whereUsed.
 *
 * Revision 1.13  2005/01/07 18:21:42  tony
 * 6554
 * improved logic to adjust action list
 * in addition to table on create and edit or
 * table refresh.
 *
 * Revision 1.12  2005/01/07 18:05:46  tony
 * rm_20050107
 * adjusted logic to allow for menu items and toolbar containers
 * to be populated by an array of keys instead of just a single one.
 *
 * Revision 1.11  2005/01/05 23:47:16  tony
 * 6554
 * added edit capability to whereused action.
 *
 * Revision 1.10  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.9  2004/11/05 22:03:56  tony
 * searchable picklist
 *
 * Revision 1.8  2004/08/26 16:26:35  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.7  2004/08/09 21:22:12  tony
 * improved logging
 *
 * Revision 1.6  2004/06/08 20:41:30  tony
 * 5ZPTCX.2
 *
 * Revision 1.5  2004/06/07 19:42:17  tony
 * 5ZPTCX
 *
 * Revision 1.4  2004/02/27 18:54:22  tony
 * display statements
 *
 * Revision 1.3  2004/02/23 21:30:52  tony
 * e-announce13
 *
 * Revision 1.2  2004/02/20 22:17:59  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.45  2004/01/08 18:14:05  tony
 * 53412b
 *
 * Revision 1.44  2004/01/05 22:23:59  tony
 * 53476
 *
 * Revision 1.43  2003/12/30 20:42:39  tony
 * 53476
 *
 * Revision 1.42  2003/12/17 19:14:17  tony
 * acl_20031217
 * updated hidden logic and added comment for previous token
 *
 * Revision 1.41  2003/12/16 20:22:33  tony
 * 53412
 *
 * Revision 1.40  2003/12/09 22:32:05  tony
 * 53362
 *
 * Revision 1.39  2003/12/05 22:07:45  tony
 * updated focus logic
 *
 * Revision 1.38  2003/10/29 19:10:42  tony
 * acl_20031029
 *
 * Revision 1.37  2003/10/29 00:20:05  tony
 * removed System.out. statements.
 * REPLAYABLE_LOGFILE
 *
 * Revision 1.36  2003/10/20 16:37:13  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.35  2003/10/15 15:56:11  tony
 * added logic to trap null pointer.
 *
 * Revision 1.34  2003/10/15 15:30:48  tony
 * 52555
 *
 * Revision 1.33  2003/10/14 15:26:41  tony
 * 51832
 *
 * Revision 1.32  2003/09/30 16:34:49  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.31  2003/08/28 18:36:02  tony
 * 51975
 *
 * Revision 1.30  2003/08/27 14:33:36  tony
 * cr_TBD_3
 * refined logic.
 *
 * Revision 1.29  2003/08/26 21:17:09  tony
 * cr_TBD_3
 * update whereused-matrix & matrix-whereused functionality.
 *
 * Revision 1.28  2003/08/21 22:19:33  tony
 * 51876
 *
 * Revision 1.27  2003/08/15 15:37:27  tony
 * cr_0805036452
 *
 * Revision 1.26  2003/07/24 22:26:17  tony
 * 51528
 *
 * Revision 1.25  2003/07/18 18:59:09  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.24  2003/07/09 15:08:59  tony
 * 51417 -- (did not tag) updated menu creation logic
 * to use the properties file instead of constant values.
 *
 * Revision 1.23  2003/07/07 14:55:27  tony
 * added process methods
 *
 * Revision 1.22  2003/07/03 17:26:56  tony
 * updated logic to improve scripting
 *
 * Revision 1.21  2003/07/03 16:38:04  tony
 * improved scripting logic.
 *
 * Revision 1.20  2003/06/25 23:46:13  tony
 * adjusted logic to display picklist.
 *
 * Revision 1.19  2003/06/13 17:32:51  tony
 * 51255 resize on refresh issue.
 *
 * Revision 1.18  2003/06/05 17:07:37  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.17  2003/05/29 19:04:33  tony
 * 50944
 *
 * Revision 1.16  2003/05/28 18:29:12  tony
 * 50944
 *
 * Revision 1.15  2003/05/28 14:40:27  tony
 * 50946
 *
 * Revision 1.14  2003/05/21 17:05:01  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.13  2003/05/09 16:51:27  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.12  2003/04/23 21:00:06  tony
 * added functionality to prevent a duplicate whereused from
 * being displayed.
 *
 * Revision 1.11  2003/04/21 20:02:34  tony
 * updated logic to correlate to mw changes.
 *
 * Revision 1.10  2003/04/10 23:05:51  tony
 * fixed navActionTree Where Used Action.
 *
 * Revision 1.9  2003/04/03 16:19:07  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.8  2003/04/02 19:53:38  tony
 * adjusted logic.  Everytime a new tab is launched the
 * system must grab a new instance of the profile.
 * This will aid in session tagging.
 *
 * Revision 1.7  2003/04/01 21:18:40  tony
 * SessionTagging added to the e-announce
 * application.  Cleaned-up actionController.
 *
 * Revision 1.6  2003/03/27 16:24:59  tony
 * added session id as an identification component
 * of the profile, needed because of pinning aspect.
 *
 * Revision 1.5  2003/03/12 23:51:12  tony
 * accessibility and column order
 *
 * Revision 1.4  2003/03/11 00:33:24  tony
 * accessibility changes
 *
 * Revision 1.3  2003/03/04 22:34:50  tony
 * *** empty log message ***
 *
 * Revision 1.2  2003/03/04 16:52:54  tony
 * added logic for EntityHistoryGroup
 *
 * Revision 1.1.1.1  2003/03/03 18:03:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.47  2002/11/20 22:19:37  tony
 * added logic for toggle action in where used.
 *
 * Revision 1.46  2002/11/11 22:55:38  tony
 * adjusted classification on the toggle
 *
 * Revision 1.45  2002/11/09 00:01:57  tony
 * acl_20021108 changed JSplitPane to GSplitPane to
 * simplify controls and add functionality.  This should
 * assist in accessibility concerns.
 *
 * Revision 1.44  2002/11/07 16:58:25  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.action;
import com.elogin.*;
//import com.ibm.eannounce.eserver.ChatAction;
import com.ibm.eannounce.eforms.edit.*;
import com.ibm.eannounce.eforms.navigate.NavPick;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class UsedAction extends Action implements FocusListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
    private final static int COLUMN_RELATED = 4;

    private ESplitPane split = null;

    private UsedTable wTable = null;
    private EPanel tPnl = new EPanel(new BorderLayout());
    private EScrollPane tScroll = null;

    //private Component actComp = null;
    private NavPick pick = null;

//    private boolean showTree = false;

    private EannounceToolbar pTool = null;
    private EannounceToolbar cTool = null;
    private EannounceToolbar tTool = null;
    private WhereUsedList wList = null;

    /**
     * usedAction
     * @param _parent
     * @param _wList
     * @author Anthony C. Liberto
     */
    protected UsedAction(ActionController _parent, Object _wList) {
        super(_parent);//, _wList);
        wList = (WhereUsedList) _wList;
        
        createMenus();
        init();
        refreshMenu();
        pick = new NavPick(this, NavPick.WHERE_USED);
    }

    /**
     * requestFocus
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void requestFocus(int _i) {
          wTable.requestFocus();
    }

    /**
     * requestFocus
     *
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (wTable != null) {
            wTable.requestFocus();
        }
    }

    /**
     * getList
     * @return
     * @author Anthony C. Liberto
     * /
    private WhereUsedList getList() {
    	
        if (object instanceof WhereUsedList) {
            return (WhereUsedList) object;
        }
        return null;
    }*/

    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    protected void init() {
        super.init();
        generateTable();
        getSplitPane().setLeftComponent(getActionController().getTree());
        getSplitPane().setRightComponent(tPnl);
    }

    private void generateTable() {
//        String pos = "North";
        Dimension min = null;
        if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            tTool = ToolbarController.generateToolbar(DefaultToolbarLayout.USED_BAR_TABLE, this, null);
        }
        wTable = new UsedTable(wList, wList.getTable(), getActionController());
        wTable.addFocusListener(this);
        wTable.setParentObject(this);
        //51528		sort(wTable);
        wTable.sort(); //51528
        tScroll = new EScrollPane(wTable);

        min = UIManager.getDimension("eannounce.minimum");
        tScroll.setMinimumSize(min);
        tPnl.add("Center", tScroll);
        if (tTool != null) {
            tTool.setMinimumSize(min);
            tPnl.add(tTool.getAlignment(), tTool);
        }
        wTable.defaultSelect(); //53362
    }

    /**
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusGained(FocusEvent _fe) {
        Component c = _fe.getComponent();
        if (tTool != null) {
            tTool.setEnabled("f/r", c == wTable);
            tTool.setEnabled("fltr", c == wTable);
        }
        refreshMenu();
        getMenubar().setMenuEnabled("Table", c == wTable);
    }

    /**
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusLost(FocusEvent _fe) {
    }

    /**
     * getProfile
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected Profile getProfile() {
        return wList.getProfile();
    }

    /**
     * createMenus
     *
     * @author Anthony C. Liberto
     */
    protected void createMenus() {
        createFileMenu();
        createEditMenu(); //cr_0805036452
        createActionMenu();
        createTableMenu();
    }

    /**
     * removeMenus
     *
     * @author Anthony C. Liberto
     */
    public void removeMenus() { //53412
		if (getMenubar() != null ){//MN31311135 prevent null ptr if deref is called before this is
			removeFileMenu();
			removeEditMenu(); //cr_0805036452
			removeActionMenu();
			removeTableMenu();
		}
    }

    private void createActionMenu() {
        String strKey = getString("act");
        getMenubar().addSubMenu(strKey, "crte", this, 0, 0);
        getMenubar().addSubMenu(strKey, "edit", this, 0, 0); //6554
        getMenubar().addSubMenu(strKey, "used", this, 0, 0);
        getMenubar().addSeparator(strKey);
        getMenubar().addSubMenu(strKey, "link", this, KeyEvent.VK_P, Event.CTRL_MASK);
        getMenubar().addSubMenu(strKey, "rLink", this, KeyEvent.VK_DELETE, Event.CTRL_MASK);
        getMenubar().addSeparator(strKey);
        //cr_0805036452		getMenubar().addMenu(strKey,"add2cart",this,0,0,true);
        getMenubar().addMenu(strKey, "add2cart", this, KeyEvent.VK_A, Event.CTRL_MASK + Event.SHIFT_MASK, true); //cr_0805036452
        getMenubar().addSeparator(strKey);
        getMenubar().addMenu(strKey, "toglAct", this, KeyEvent.VK_T, Event.CTRL_MASK, true);
        if (EAccess.isCaptureMode()) {
        	getMenubar().addSeparator(strKey);
        	getMenubar().addMenu(strKey, "capture", this, KeyEvent.VK_F5, Event.CTRL_MASK + Event.SHIFT_MASK, true);
		}
        getMenubar().setMenuMnemonic(strKey, getChar("act-s"));
    }

    private void removeActionMenu() {
    	getMenubar().removeMenuItem("crte", this);
    	getMenubar().removeMenuItem("edit", this); //6554
    	getMenubar().removeMenuItem("used", this);
    	getMenubar().removeMenuItem("link", this);
    	getMenubar().removeMenuItem("rLink", this);
    	getMenubar().removeMenuItem("toglAct", this);
    	getMenubar().removeMenuItem("capture",this);
    	getMenubar().removeMenu(getString("act"));
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        appendLog("usedAction.actionPerformed(" + action + ")");
        if (isBusy()) {
            appendLog("    I am busy");
            return;
        }
        if (action.equals("mtrx")) { //cr_TBD_3
            loadAction(_ae.getSource(), 0); //cr_TBD_3
            return; //cr_TBD_3
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
        		eaccess().exit("exit used");
        		return;
        	} 
        	Long t11 = EAccess.eaccess().timestamp("UsedAction performAction "+action+" started");    	
        	if (action.equals("saveT")) {
        		eaccess().save(wList);
        	} else if (action.equals("f/r")) {
        		 wTable.showFind();
        	} else if (action.equals("fltr")) {
        		wTable.showFilter();
        	} else if (action.equals("srt")) {
        		wTable.showSort();
        	} else if (action.equals("hide")) { //51832
        		showHide(true); //51832
        	} else if (action.equals("unhide")) { //51832
        		showHide(false); //51832
        	} else if (action.equals("left")) {
        		wTable.moveColumn(true);
        	} else if (action.equals("right")) {
        		wTable.moveColumn(false);
        	} else if (action.equals("histI")) {
        		wTable.historyInfo();
        	} else if (action.equals("histR")) {
        		wTable.relatorHistoryInfo();
        	}else if (action.equals("fmi")) {
        		wTable.showInformation();
        	} else if (action.equals("crte")) {
        		create(wTable);
        	} else if (action.equals("capture")) {
        		if (wList != null) {
        			eaccess().capture(wList.dump(false));
        		}
        	} else if (action.equals("edit")) { //6554
        		edit(wTable); //6554
        	} else if (action.equals("used")) {
        		whereUsed(wTable);
        	} else if (action.equals("link")) { 		
        		link(wTable);
        	} else if (action.equals("toglAct")) {
        		toggleSplit();
        	} else if (action.equals("rLink")) {
        		removeLink(wTable);
        	} else if (action.equals("add2cart")) {
        		addToCart(wTable);
        	} else if (action.equals("selA")) {
        		selectAll(wTable);
        	} else if (action.equals("iSel")) {
        		invertSelection(wTable);
        	} else if (action.equals("eData")) { //50946
        		if (wTable != null) { //50946
        			wTable.showInformation();
        		} //50946
        	} //50946
        	EAccess.eaccess().timestamp("UsedAction performAction "+action+" ended",t11);
        }catch(Exception exc){
        	eaccess().showException(exc, null,ERROR_MESSAGE,OK);
        }        
        setBusy(false);
    }

    private void toggleSplit() {
        int divLoc = getSplitPane().getDividerLocation();
        if (divLoc < 10) {
            getSplitPane().setDividerLocation(getActionController().getTree().getPreferredWidth());
        } else {
            getSplitPane().setDividerLocation(0);
        }
        repaint();
    }
    private void selectAll(Component _c) {
        if (_c instanceof UsedTable) {
            ((UsedTable) _c).selectAll();
        }
    }

    private void invertSelection(Component _c) {
        if (_c instanceof UsedTable) {
            ((UsedTable) _c).invertSelection();
        }
    }

    /**
     * refreshMenu
     *
     * @author Anthony C. Liberto
     */
    protected void refreshMenu() {
        EANActionItem[] eai = getActionController().getTree().getActionItemArray(ACTION_PURPOSE_CREATE);
        boolean bHasData = wTable.hasRows() && wTable.hasColumns();
        boolean bHasEntity = wTable.hasEntity();
        String[] actRA = { ACTION_PURPOSE_NAVIGATE, ACTION_PURPOSE_SEARCH }; //rm_20050107
        if (getMenubar() != null) {
            getMenubar().adjustSubAction("crte", eai, bHasData, ACTION_ALL);
        }
        if (tTool != null) {
            tTool.adjustSubAction("crte", eai, bHasData, ACTION_ALL);
        }
        if (cTool != null) {
            cTool.adjustSubAction("crte", eai, bHasData, ACTION_ALL);
        }

        eai = getActionController().getTree().getActionItemArray(ACTION_PURPOSE_EDIT); //6554
        if (getMenubar() != null) {
            getMenubar().adjustSubAction("edit", eai, bHasData, ACTION_ALL);
        } //6554
        if (tTool != null) {
            tTool.adjustSubAction("edit", eai, bHasData, ACTION_ALL);
        } //6554
        if (cTool != null) {
            cTool.adjustSubAction("edit", eai, bHasData, ACTION_ALL);
        } //6554

        eai = getActionController().getTree().getActionItemArray(ACTION_PURPOSE_WHERE_USED);
        if (getMenubar() != null) {
            getMenubar().adjustSubAction("used", eai, bHasData && bHasEntity, ACTION_ALL);
        }
        if (tTool != null) {
            tTool.adjustSubAction("used", eai, bHasData && bHasEntity, ACTION_ALL);
        }
        if (cTool != null) {
            cTool.adjustSubAction("used", eai, bHasData && bHasEntity, ACTION_ALL);
        }

        //rm_20050107		eai = getActionController().getTree().getActionItemArray(ACTION_PURPOSE_NAVIGATE);
        eai = getActionController().getTree().getActionItemArray(actRA); //rm_20050107
        if (getMenubar() != null) {
            getMenubar().adjustSubAction("link", eai, bHasData, ACTION_ALL);
        }
        if (tTool != null) {
            tTool.adjustSubAction("link", eai, bHasData, ACTION_ALL);
        }
        if (cTool != null) {
            cTool.adjustSubAction("link", eai, bHasData, ACTION_ALL);
        }

        eai = getActionController().getTree().getActionItemArray(ACTION_PURPOSE_DELETE);
        if (getMenubar() != null) {
            getMenubar().adjustSubAction("rLink", eai, bHasData, ACTION_ALL);
        }
        if (tTool != null) {
            tTool.adjustSubAction("rLink", eai, bHasData, ACTION_ALL);
        }
        if (cTool != null) {
            cTool.adjustSubAction("rLink", eai, bHasData, ACTION_ALL);
        }

        eai = getActionController().getTree().getActionItemArray(ACTION_PURPOSE_MATRIX); //cr_TBD_3
        if (getMenubar() != null) {
            getMenubar().adjustSubAction("mtrx", eai, bHasData, ACTION_ALL);
        } //cr_TBD_3
        if (tTool != null) {
            tTool.adjustSubAction("mtrx", eai, bHasData, ACTION_ALL);
        } //cr_TBD_3
        if (cTool != null) {
            cTool.adjustSubAction("mtrx", eai, bHasData, ACTION_ALL);
        } //cr_TBD_3

        getMenubar().setEnabled("add2cart", wTable.hasEntities()); //50944
    }

    /**
     * getTableTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getTableTitle() {
        if (wTable != null) {
            return wTable.getTableTitle();
        }
        return "TBD WhereUsed";
    }

    private void create(UsedTable _table) {
        //52703		String key = _table.getKey(_table.getSelectedRow());
        //why???String key = new String(_table.getKey(_table.getSelectedRow())); //52703
        String key = _table.getKey(_table.getSelectedRow());
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //52703
            appendLog("<database method=\"whereUsed.create\" EntityItems=\"" + key + "\"/>");
        } //52703

        create(_table.create(key));
    }

    private void create(EntityList _eList) {
        if (_eList != null) {
            EditController ec = new EditController(_eList, null);
            ec.setParentProfile(getProfile());
            //kehrli_20030929			String name = ec.getTableTitle();
            ec.setSelectorEnabled(true);
            ec.setParentTab(getActionController()); //53476
            addTab(getActionController(), ec); //kehrli_20030929
        }
    }

    private void whereUsed(UsedTable _table) {
        //52703		String key = _table.getKey(_table.getSelectedRow());
        String key = new String(_table.getKey(_table.getSelectedRow())); //52703
        WhereUsedList tmpList = null;
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //52703
            appendLog("<database method=\"whereUsed.whereUsed\" EntityItems=\"" + key + "\"/>");
        } //52703
        tmpList = _table.whereUsed(key);
        if (tmpList != null) {
            ActionController acController = new ActionController(tmpList, key);
            acController.setParentProfile(getProfile());
            acController.setOPWGID(getActionController().getOPWGID());
            acController.setCart(getActionController().getCart());
            addTab(getActionController(), acController); //kehrli_20030929
            acController.requestFocus(-1);
        }
    }

    /**
     * link
     * @param _keys
     * @param _ei
     * @param _linkType
     * @return
     * @author Anthony C. Liberto
     */
    public boolean link(String[] _keys, EntityItem[] _ei, String _linkType) { //52555
        //51876		EANFoundation[] ean = wTable.link(_keys, _ei);
        EANFoundation[] ean = wTable.link(_keys, _ei, _linkType); //51876
        if (ean !=null){
    	    wTable.sort(); //51528
    	    wTable.refreshTable(true);
		}

        return (ean != null);
    }

    private void removeLink(UsedTable _table) {
        String[] keys = _table.getSelectedKeys();
        String [] validKeys = null;
        if (keys == null) {
            showError("msg23006");
            return;
        }
        validKeys = _table.validateKeys(keys, COLUMN_RELATED);
        if (validKeys == null) {
            showError("msg23006");
            return;
        }
        if (validKeys.length==1 && validKeys[0].equals("CANCELLED")){
            return;
		}
        //52703		appendLog("<database method=\"whereUsed.removeLink\" EntityItems=\"" + routines.toString(validKeys,ARRAY_DELIMIT) + "\"/>");
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //52703
            appendLog("<database method=\"whereUsed.removeLink\" EntityItems=\"" + Routines.toString(validKeys, ARRAY_DELIMIT) + "\"/>"); //52703
        } //52703

        _table.removeLink(validKeys);
    }

    private void addToCart(UsedTable _table) {
        String[] keys = _table.getSelectedKeys();
        if (keys != null) {
        	getCart().addToCart(_table.getEntityItems(keys));
        }
    }

    /**
     * getHelpText
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getHelpText() {
        return wTable.getHelpText();
    }

    /**
     * isHidden
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isHidden() {
        return wTable.isHidden();
    }

    /**
     * isFiltered
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isFiltered() {
        if (wTable != null) { //53412b
            return wTable.isFiltered();
        } //53412b
        return false; //53412b
    }

    /**
     * contains
     * @author Anthony C. Liberto
     * @return boolean
     * @param _eai
     * @param _ei
     */
    protected boolean contains(EntityItem[] _ei, EANActionItem _eai) {
        return wList.equivalent(_ei, _eai);      
      //  return false;
    }

    /**
     * performAction
     * @author Anthony C. Liberto
     * @param _ai
     * @param _navType
     */
    protected void performAction(EANActionItem _ai, int _navType) {
        if (isBusy()) {
            return;
        }
        setBusy(true);
        if (_ai instanceof CreateActionItem) {
            create(wTable);
        } else if (_ai instanceof EditActionItem) { //6554
            edit(wTable); //6554
        } else if (_ai instanceof NavActionItem) {
            link(wTable);
        } else if (_ai instanceof DeleteActionItem) {
            removeLink(wTable);
        } else if (_ai instanceof WhereUsedActionItem) {
            whereUsed(wTable);
        } else if (_ai instanceof MatrixActionItem) { //cr_TBD_3
            matrix((MatrixActionItem) _ai, wTable); //cr_TBD_3
        } else if (_ai instanceof SearchActionItem) {
            pick.setKeys(wTable.getSelectedKeys()); //searchable picklist
            processSearchAction((SearchActionItem) _ai); //searchable picklist
        }
        setBusy(false);
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
         if (tPnl != null) {
            tPnl.removeAll();
            tPnl.removeNotify();
            tPnl = null;
        }

 		if (wList != null) {
			wList.dereference();
			wList = null;
		}

        if (wTable != null) {
            wTable.removeFocusListener(this);
            wTable.dereference();
            wTable.removeAll();
            wTable.removeNotify();
            wTable = null;
        }

        if (tScroll != null) {
            tScroll.dereference();
            tScroll = null;
        }

        if (split != null) {
            split.removeAll();
            split.removeNotify();
            split = null;
        }

        if (pTool != null) {
            pTool.dereference();
            pTool = null;
        }

        if (tTool != null) {
            tTool.dereference();
            tTool = null;
        }

        if (cTool != null) {
            cTool.dereference();
            cTool = null;
        }

        //actComp = null;
        if (pick != null) {
            pick.dereference();
            pick = null;
        }
        super.dereference();
    }

    /**
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected Object getSearchableObject() { //22377
        return wTable; //22377
    } //22377

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        if (pTool != null) {
            pTool.refreshAppearance();
        }
        if (cTool != null) {
            cTool.refreshAppearance();
        }
        if (tTool != null) {
            tTool.refreshAppearance();
        }

        wTable.resizeCells();
    }

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_USEDACTION;
    }

    /**
     * process
     * @author Anthony C. Liberto
     * @param _action
     * @param _child
     * @param _method
     * @param _parent
     */
    protected void process(String _method, String _action, String[] _parent, String[] _child) {
        wTable.highlight(_parent);
        if (_method.equals("whereUsed.create")) {
            create(wTable);
        } else if (_method.equals("whereUsed.edit")) { //6554
            edit(wTable); //6554
        } else if (_method.equals("whereUsed.removeLink")) {
            removeLink(wTable);
        } else if (_method.equals("whereUsed.whereUsed")) {
            whereUsed(wTable);
        } else if (_method.equals("whereUsed.link")) {
            link(wTable);
        }
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
        if (wTable != null) {
            wTable.selectKeys(_keys);
        }
    }

    /*
     cr_0805036452
     */
    private void createEditMenu() {
        String strKey = getString("edit");
        getMenubar().addMenu(strKey, "selA", this, KeyEvent.VK_A, Event.CTRL_MASK, true);
        getMenubar().addMenu(strKey, "iSel", this, KeyEvent.VK_I, Event.CTRL_MASK, true);
    }

    private void removeEditMenu() {
    	getMenubar().removeMenuItem("selA", this);
    	getMenubar().removeMenuItem("iSel", this);
    }

    /*
     cr_TBD_3
     */
    private void matrix(MatrixActionItem _mai, UsedTable _table) {
        WhereUsedItem[] items = _table.getWhereUsedItems(false);
        MatrixList mList = null;
        if (items != null) {
            int ii = items.length;
            EntityItem[] ei = new EntityItem[ii];
            for (int i = 0; i < ii; ++i) {
                ei[i] = items[i].getOriginalEntityItem();
            }
            if (eaccess().editContains(ei, _mai)) {
                return;
            }
            mList = dBase().rexec(_mai, ei, this);
            if (mList != null) {
                ActionController acController = new ActionController(mList, getActionController().getParentKey());
                acController.setParentProfile(getProfile());
                acController.setOPWGID(getActionController().getOPWGID());
                acController.setCart(getActionController().getCart());
                //kehrli_20030929				String sWU = "Matrix";
                //51975				addTab(sWU + " -- " + acController.getTableTitle(), sWU, "mtrx.gif", acController, sWU + " -- " + acController.getTableTitle());
                //kehrli_20030929				addTab(getActionController(),sWU + " -- " + acController.getTableTitle(), sWU, "mtrx.gif", acController, sWU + " -- " + acController.getTableTitle());		//51975
                addTab(null, acController); //kehrli_20030929
                acController.requestFocus(-1);
            }
        }
    }

    private void loadAction(Object _o, int _navType) {
        if (_o instanceof SubAction) {
            performAction(getActionController().getTree().getActionItem(((SubAction) _o).getKey()), _navType);
        }
    }

    /**
     * getTabMenuTitle
     *
     * @return
     */
    protected String getTabMenuTitleKey() { return "whereused.title";}

    /**
     * getTabIcon
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getTabIconKey() {
        return "whereused.icon";
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
        if (wTable != null) {
            wTable.showHide(_b);
            eaccess().setHidden(_b); //acl_20031217
        }
    }
    /*
     52555
     */
    /**
     * getParentEntityItemAsArray
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getParentEntityItemAsArray() {
        WhereUsedItem[] items = wTable.getWhereUsedItems(false);
        if (items != null) {
            int ii = items.length;
            EntityItem[] ei = new EntityItem[ii];
            for (int i = 0; i < ii; ++i) {
                if (items[i] != null) {
                    ei[i] = items[i].getOriginalEntityItem();
                }
            }
            return ei;
        }
        return null;
    }
    /*
     53476
     */
    /**
     * refresh
     * @param _list
     * @author Anthony C. Liberto
     */
    protected void refresh(EntityList _list) {
        if (wTable != null && _list != null) {
            wTable.sort();
            wTable.refreshTable(true);
        }
    }

    /*
     5ZPTCX.2
     */
    /**
     * link
     * @param _lai
     * @param _parent
     * @param _child
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object link(LinkActionItem _lai, EntityItem[] _parent, EntityItem[] _child, Component _c) {
        Object o = null;
        if (wTable != null) {
            o = wTable.link(_lai, _parent, _child, _c);
        }
        return o;
    }

    /*
     searchable picklist
     */
    private void link(UsedTable _table) {
        String key = null;
        pick.setKeys(_table.getSelectedKeys());
        //52703		String key = _table.getKey(_table.getSelectedRow());
        key = _table.getKey(_table.getSelectedRow()); //52703
        if (key == null) {
            showError("msg2009");
            return;
        }

        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //52703
            appendLog("<database method=\"whereUsed.link\" EntityItems=\"" + key + "\"/>");
        } //52703
        processPicklist(key);
    }

    private void processPicklist(String _key) {
        EANActionItem[] act = getActionItemsAsArray(_key);
        if (act != null) {
            processActionItems(_key, act);
        }
    }

    private void processPicklistNavigate(String _key) {
        EntityList eList = wTable.generatePickList(_key);
        if (eList != null) {
            showPicklist(eList);
        }
    }

    private EANActionItem[] getActionItemsAsArray(String _key) {
        EANActionItem[] out = null;
        out = wTable.getActionItemsAsArray(_key);
        if (out == null || out.length < 1) {
            showFYI("msg23007.1");
            return null;
        }
        return out;
    }

    private void processActionItems(String _key, EANActionItem[] _in) {
        if (_in != null) {
            int ii = _in.length;
            for (int i = 0; i < ii; ++i) {
                if (_in[i] != null) {
                    if (_in[i] instanceof SearchActionItem) {
                    	Long t11 = EAccess.eaccess().timestamp("UsedAction search started");
                        processSearchAction((SearchActionItem) _in[i]);
                        EAccess.eaccess().timestamp("UsedAction search ended",t11);
                        return;
                    } else if (_in[i] instanceof NavActionItem) {
                    	Long t11 = EAccess.eaccess().timestamp("UsedAction picklistnav started");
                        processPicklistNavigate(_key);
                        EAccess.eaccess().timestamp("UsedAction picklistnav ended",t11);
                        return;
                    }
                }
            }
        }
    }

    private void processSearchAction(SearchActionItem _search) {
        if (_search.isGrabByEntityID()) {
            int iID = getSearchID();
            if (iID >= 0) {
                _search.setGrabEntityID(iID);
                showPicklist(dBase().getEntityList(_search, null, this));
            }
        } else {
            eaccess().showSearch(_search, 0, this);
        }
    }

    private int getSearchID() {
        String tmp = eaccess().showInput("searchID", null, this);
        if (tmp != null) {
            int iLen = tmp.length();
            if (iLen > 0) {
                if (iLen > 15) {
                    showError("msg13001.0");
                    return -1;
                }
                if (!Routines.isInt(tmp)) {
                    showError("msg13002.0");
                    return -1;

                }
                return Routines.toInt(tmp);
            }
        }
        return -1;
    }

    private boolean hasData(EntityList _eList) {
        if (_eList != null) {
            int ii = _eList.getEntityGroupCount();
            for (int i = 0; i < ii; ++i) {
                EntityGroup eg = _eList.getEntityGroup(i);
                if (eg != null) {
                    if (eg.isDisplayable() && eg.getEntityItemCount() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * showPicklist
     * @param _list
     * @return
     * @author Anthony C. Liberto
     */
    public boolean showPicklist(EntityList _list) {
        //System.out.println("usedAction.showPicklist()");
        if (_list == null) {
            showFYI("msg23007.1");
            return false;
        } else if (!hasData(_list)) {
            showFYI("msg23007.0");
            return false;
        }
        pick.showPicklist(_list);
        return true;
    }

    private void edit(UsedTable _table) {
        String[] keys = _table.getValidSelectedKeys(_table.getSelectedRow());
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) {
            appendLog("<database method=\"whereUsed.edit\" EntityItems=\"" + new String(Routines.toString(keys, ARRAY_DELIMIT)) + "\"/>");
        }
        if (keys != null) {
            if (keys.length != _table.getSelectedRowCount()) {
                showFYI("msg5023.0");
            }
            edit(_table.edit(keys));
        } else {
            showFYI("msg5022.0");
        }
    }

    private void edit(EntityList _eList) {
        if (_eList != null) {
            EditController ec = new EditController(_eList, null);
            ec.setParentProfile(getProfile());
            ec.setSelectorEnabled(true);
            ec.setParentTab(getActionController());
            addTab(getActionController(), ec);
        }
    }

    /**
     * refreshTable
     *
     * @author Anthony C. Liberto
     */
    protected void refreshTable() {
        setBusy(true);
        if (wList != null) {
            WhereUsedActionItem wuai = wList.getParentActionItem();
            if (wuai != null) {
                EntityGroup peg = wList.getParentEntityGroup();
                if (peg != null) {
                    EntityItem[] ei = peg.getEntityItemsAsArray();
                    WhereUsedList tmpList = dBase().rexec(wuai, ei, this);
                    if (tmpList != null) {
                        int row = wTable.getSelectedRow();
                        RowSelectableTable rst = tmpList.getTable();
                        wTable.updateModel(rst);
                        wTable.sort();
                        wTable.refreshTable(false);
                        wTable.reselectActions(row);
                        wList.dereference();
                        wList = tmpList;
                    }
                }
            }
        }
        setBusy(false);
    }
    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    protected void refreshToolbar() {
		if (pTool != null) {
			pTool.refreshToolbar();
		}
		if (cTool != null) {
			cTool.refreshToolbar();
		}
		if (tTool != null) {
			tTool.refreshToolbar();
		}
		refreshMenu();
	}
    
    //old or unused
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
        setParm(0, getActionController().getTableTitle());
        setParm(1, getActionController().getProfile().toString());
        name = getMessage();
        eaccess().clear();
        return name;
    }*/
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
     * closeLocalMenus
     *
     * @author Anthony C. Liberto
     * /
    public void closeLocalMenus() {
    }*/

    /**
     * getChatAction
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public ChatAction getChatAction() {
        EntityGroup group = null;
        if (wList != null) {
            ChatAction chat = new ChatAction();
            EANActionItem item = wList.getParentActionItem();
            if (item != null) {
                chat.setAction(item);
            }
            group = wList.getParentEntityGroup();
            if (group != null) {
                EntityItem[] tmp = group.getEntityItemsAsArray();
                if (tmp != null) {
                    chat.setSelectedItems(tmp);
                }
            }
            return chat;
        }
        return null;
    }*/
    /**
     * sort
     * @param _wTable
     * @author Anthony C. Liberto
     * /
    public void sort(UsedTable _wTable) {
        int[] iArray = { UsedTable.PARENT, UsedTable.DIRECTION, UsedTable.RELATOR, UsedTable.ENTITY_DISPLAY_NAME }; //21696
        boolean[] bArray = { true, false, true, true }; //21696
        _wTable.sort(iArray, bArray);
    }*/

    /**
     * getTable
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public JTable getTable() {
        return wTable;
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
     * refresh
     *
     * @author Anthony C. Liberto
     * /
    public void refresh() {
    }*/

    /**
     * update
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean update() {
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
     * moveColumn
     *
     * @param _left
     * @author Anthony C. Liberto
     * /
    public void moveColumn(boolean _left) {
    }*/

    /**
     * sort
     *
     * @param _ascending
     * @author Anthony C. Liberto
     * /
    public void sort(boolean _ascending) {
    }*/
	//private final static int AUTOMATIC = -1;
    //private final static int PARENT_TREE = 0;
    //private final static int CHILD_TREE = 1;
    //private final static int USED_TABLE = 2;
    //private final static int COLUMN_PARENT = 0;
    //private final static int COLUMN_RELATIONSHIP = 1;
    //private final static int COLUMN_DIRECTION = 2;
    //private final static int COLUMN_RELATED_DESC = 3;
}
