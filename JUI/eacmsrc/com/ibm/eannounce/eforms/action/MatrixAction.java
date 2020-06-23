/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: MatrixAction.java,v $
 * Revision 1.5  2010/06/23 20:16:41  wendy
 * Fix cant edit number of relators cell
 *
 * Revision 1.4  2009/05/28 12:33:23  wendy
 * Added show info and enforced meta rules
 *
 * Revision 1.3  2009/05/26 13:00:14  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:27:03  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:53  wendy
 * Reorganized JUI module
 *
 * Revision 1.5  2007/03/27 14:38:02  wendy
 * MN31311135 prevent null ptr
 *
 * Revision 1.4  2006/11/09 15:51:06  tony
 * more monitor logic
 *
 * Revision 1.3  2006/03/16 22:01:28  tony
 * Capture logging
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.35  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.34  2005/04/20 17:20:09  tony
 * fixed null pointer.
 *
 * Revision 1.33  2005/03/28 17:56:37  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.32  2005/02/18 23:10:09  tony
 * USRO-R-TMAY-69QQSU
 *
 * Revision 1.31  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.30  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.29  2005/02/03 16:22:33  tony
 * column filtering
 *
 * Revision 1.28  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.27  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.26  2005/01/20 17:09:54  tony
 * USRO-R-JSTT-68L7VW
 *
 * Revision 1.25  2005/01/19 17:57:37  tony
 * featureMatrix_copy
 *
 * Revision 1.24  2005/01/18 22:23:14  tony
 * featureMatrix_copy
 *
 * Revision 1.23  2005/01/18 21:40:28  tony
 * fixed menu for feature matrix
 *
 * Revision 1.22  2005/01/18 19:04:05  tony
 * pivot modifications
 *
 * Revision 1.21  2005/01/14 20:29:21  tony
 * xpnd_action
 * pivot
 *
 * Revision 1.20  2005/01/13 20:53:52  tony
 * fixed invalid loop and adjusted error messaging.
 *
 * Revision 1.19  2004/11/05 22:03:56  tony
 * searchable picklist
 *
 * Revision 1.18  2004/11/04 14:43:07  tony
 * searchable picklist
 *
 * Revision 1.17  2004/10/22 22:12:54  tony
 * TIR_65Y3M
 *
 * Revision 1.16  2004/09/16 21:18:36  tony
 * adjusted crosstab editing.
 *
 * Revision 1.15  2004/08/26 16:26:35  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.14  2004/08/09 21:22:12  tony
 * improved logging
 *
 * Revision 1.13  2004/07/29 22:38:28  tony
 * improved funtionality for 3.0a
 *
 * Revision 1.12  2004/06/30 17:03:48  tony
 * fixed feature matrix functionality.
 *
 * Revision 1.11  2004/06/24 15:29:41  tony
 * added fill capability to featureMatrix
 *
 * Revision 1.10  2004/06/23 17:22:32  tony
 * featureMatrix
 *
 * Revision 1.9  2004/06/22 23:28:27  tony
 * featureMatrix
 *
 * Revision 1.8  2004/06/22 18:03:53  tony
 * featureMatrix
 *
 * Revision 1.7  2004/06/10 20:52:46  tony
 * MN19467183
 *
 * Revision 1.6  2004/06/10 14:35:30  tony
 * Feature Matrix
 *
 * Revision 1.5  2004/03/26 21:21:54  tony
 * cr_916036951
 *
 * Revision 1.4  2004/03/25 23:37:20  tony
 * cr_216041310
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
 * Revision 1.55  2004/01/05 22:53:55  tony
 * 53498
 *
 * Revision 1.54  2003/12/19 19:23:00  tony
 * acl_20031219
 * fixed null pointer when adding to cart with whereused
 * from matrix.
 *
 * Revision 1.53  2003/12/17 19:14:18  tony
 * acl_20031217
 * updated hidden logic and added comment for previous token
 *
 * Revision 1.52  2003/12/17 16:46:11  tony
 * 52910
 *
 * Revision 1.51  2003/12/16 20:23:00  tony
 * 52910
 *
 * Revision 1.50  2003/12/11 22:29:40  tony
 * 52910
 *
 * Revision 1.49  2003/10/20 17:30:30  tony
 * 52633
 *
 * Revision 1.48  2003/10/17 22:45:50  tony
 * memory leak updated dereference.
 *
 * Revision 1.47  2003/10/14 15:26:41  tony
 * 51832
 *
 * Revision 1.46  2003/09/30 16:34:49  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.45  2003/09/29 17:19:06  tony
 * cr_7145/6452
 *
 * Revision 1.44  2003/09/22 22:28:55  tony
 * 52283
 *
 * Revision 1.43  2003/09/18 18:00:24  tony
 * 52311
 *
 * Revision 1.42  2003/09/17 19:20:42  tony
 * acl_20030917--
 * updated mass change to properly enable save button
 *
 * Revision 1.41  2003/09/17 17:24:18  tony
 * adjusted logic for mass update on crosstab.
 * and made sure it would point to the correct column
 *
 * Revision 1.40  2003/08/28 22:55:29  tony
 * memory enhancements
 *
 * Revision 1.39  2003/08/28 18:36:02  tony
 * 51975
 *
 * Revision 1.38  2003/08/27 14:33:36  tony
 * cr_TBD_3
 * refined logic.
 *
 * Revision 1.37  2003/08/26 21:17:09  tony
 * cr_TBD_3
 * update whereused-matrix & matrix-whereused functionality.
 *
 * Revision 1.36  2003/08/15 15:37:27  tony
 * cr_0805036452
 *
 * Revision 1.35  2003/07/31 15:37:05  tony
 * 51597
 *
 * Revision 1.35  2003/07/18 18:59:09  tony
 * Revision 1.34  2003/07/18 18:59:09  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.33  2003/07/14 17:39:01  tony
 * 51444
 *
 * Revision 1.32  2003/07/09 15:09:00  tony
 * 51417 -- (did not tag) updated menu creation logic
 * to use the properties file instead of constant values.
 *
 * Revision 1.31  2003/07/08 18:42:31  tony
 * 51413
 *
 * Revision 1.30  2003/07/07 18:56:08  tony
 * 51399
 *
 * Revision 1.29  2003/07/07 14:55:26  tony
 * added process methods
 *
 * Revision 1.28  2003/07/03 17:26:55  tony
 * updated logic to improve scripting
 *
 * Revision 1.27  2003/06/26 15:47:04  tony
 * updated messaging to clarify blank/empty picklists
 *
 * Revision 1.26  2003/06/19 18:30:43  tony
 * 51298
 *
 * Revision 1.25  2003/06/19 16:09:42  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.24  2003/06/13 17:32:51  tony
 * 51255 resize on refresh issue.
 *
 * Revision 1.23  2003/06/05 17:07:37  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.22  2003/06/04 18:48:37  tony
 * 51112
 *
 * Revision 1.21  2003/06/02 21:11:26  tony
 * 51056
 *
 * Revision 1.20  2003/06/02 16:45:29  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.19  2003/05/30 21:09:22  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.18  2003/05/29 21:20:44  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.17  2003/05/21 17:05:00  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.16  2003/05/20 22:51:49  tony
 * 24286 -- no menu short-cut repitition.
 *
 * Revision 1.15  2003/05/14 18:59:24  tony
 * 50649
 *
 * Revision 1.14  2003/05/13 22:45:04  tony
 * 50616
 * Switched keys from a string to a pointer to the
 * EANFoundation.
 *
 * Revision 1.13  2003/05/09 16:51:27  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.12  2003/05/07 17:30:39  tony
 * 50561
 *
 * Revision 1.11  2003/05/02 20:05:54  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.10  2003/04/22 23:01:43  tony
 * added logic to show the generated picklist
 *
 * Revision 1.9  2003/04/21 20:02:34  tony
 * updated logic to correlate to mw changes.
 *
 * Revision 1.8  2003/04/03 16:19:07  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.7  2003/04/02 19:53:38  tony
 * adjusted logic.  Everytime a new tab is launched the
 * system must grab a new instance of the profile.
 * This will aid in session tagging.
 *
 * Revision 1.6  2003/03/20 23:59:36  tony
 * column order moved to preferences.
 * preferences refined.
 * Change History updated.
 * Default Column Order Stubs added
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
 * Revision 1.2  2003/03/04 16:52:55  tony
 * added logic for EntityHistoryGroup
 *
 * Revision 1.1.1.1  2003/03/03 18:03:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.70  2002/11/22 00:11:47  tony
 * 23336
 *
 * Revision 1.69  2002/11/21 16:51:49  tony
 * 23317
 *
 * Revision 1.68  2002/11/11 22:55:37  tony
 * adjusted classification on the toggle
 *
 * Revision 1.67  2002/11/09 00:01:57  tony
 * acl_20021108 changed JSplitPane to GSplitPane to
 * simplify controls and add functionality.  This should
 * assist in accessibility concerns.
 *
 * Revision 1.66  2002/11/07 16:58:24  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.action;
//import com.ibm.eannounce.eserver.ChatAction;
import com.elogin.*;
import com.ibm.eannounce.dialogpanels.ComponentPanel;
import com.ibm.eannounce.eforms.edit.*;
import com.ibm.eannounce.eforms.navigate.*;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.exception.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MatrixAction extends Action implements FocusListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
    private static final int AUTOMATIC = -1;
    public static final int MATRIX = 0;
    public static final int CROSS = 1;

    private MatrixList mList = null;
    private MtrxTable mTable = null;
    private CrossTable cTable = null;

    private RSTable focusTable = null;

    private EScrollPane mScroll = null;
    private EScrollPane cScroll = new EScrollPane();
    private ESplitPane split = null;
    private ComponentPanel cPnl = new ComponentPanel();
    private JPanel mPanel = new JPanel(new BorderLayout());
    private JPanel cPanel = new JPanel(new BorderLayout());
    private ETabbedPane tPane = new ETabbedPane() {
    	private static final long serialVersionUID = 1L;
    	public void setSelectedIndex(int _i) {
    		super.setSelectedIndex(_i);
    		setSelectedCrossTable(_i);
    	}
    }; //cr_featureMatrix
    private String fTitle = null;

    private EannounceToolbar mTool = null;
    private EannounceToolbar cTool = null;

    private NavActionTree tree = null;
    private NavPick pick = null;
    private boolean bHidden = false; //acl_20031217

    /**
     * matrixAction
     * @param _parent
     * @param _mList
     * @author Anthony C. Liberto
     */
    protected MatrixAction(ActionController _parent, Object _mList) {
        super(_parent);//, _mList);
        mList = (MatrixList) _mList;
        
        if (isFeatureMatrix()) {
            if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
                cTool = ToolbarController.generateToolbar(DefaultToolbarLayout.CROSSTAB_BAR, this, null);
            }
            initFeatureMatrix();
        } else {
            if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
                mTool = ToolbarController.generateToolbar(DefaultToolbarLayout.MATRIX_BAR, this, null);
                cTool = ToolbarController.generateToolbar(DefaultToolbarLayout.CROSSTAB_BAR, this, null);
            }
            init();
            mTable.setActionController(_parent);
        }
        createMenus();
        //		if (actionExists(ACTION_PURPOSE_EDIT) || actionExists(ACTION_PURPOSE_WHERE_USED)) {
        refreshMenu();
        //		}

        pick = new NavPick(this, NavPick.MATRIX);
        refreshButtons(); //51057
    }

    /**
     * requestFocus
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void requestFocus(int _i) {
        if (_i == AUTOMATIC) {
            if (isFeatureMatrix()) {
                requestFocus(CROSS);
            } else {
                mTable.requestFocus();
            }
        } else if (_i == MATRIX) {
            mTable.requestFocus();
        } else if (_i == CROSS) {
            CrossTable crssTable = getCrossTable();
            if (crssTable != null) {
                crssTable.requestFocus();
            }
        }
    }

    /**
     * createMenus
     *
     * @author Anthony C. Liberto
     */
    protected void createMenus() {
        createFileMenu();
        if (!isFeatureMatrix()) { //cr_featureMatrix
            createViewMenu(); //22211
            createActionMenu();
        } else { //cr_featureMatrix
            createEditActionMenu(); //featurematrix_copy
            createFeatureActionMenu(); //cr_featureMatrix
        } //cr_featureMatrix
        createTableMenu();
    }

    /**
     * removeMenus
     *
     * @author Anthony C. Liberto
     */
    protected void removeMenus() {
		if (getMenubar() != null ){//MN31311135 prevent null ptr if deref is called before this is
			removeFileMenu();
			removeViewMenu(); //22211
			removeActionMenu();
			removeTableMenu();
			removeFeatureActionMenu(); //cr_FeatureMatrix
			removeEditActionMenu(); //featurematrix_copy
			getMenubar().removeAll();
		}
    }

    private void createActionMenu() {
        //		getMenubar().addMenu("Action", "save", this, KeyEvent.VK_S, Event.CTRL_MASK,false);
        String strKey = getString("act");
        getMenubar().addMenu(strKey, "cncl", this, KeyEvent.VK_Z, Event.CTRL_MASK, false);
        getMenubar().addSeparator(strKey);
        if (actionExists(ACTION_PURPOSE_EDIT)) {
        	getMenubar().addSubMenu(strKey, "edit", this, 0, 0);
        }
        if (actionExists(ACTION_PURPOSE_WHERE_USED)) {
        	getMenubar().addSubMenu(strKey, "used", this, 0, 0);
        }
        getMenubar().addMenu(strKey, "pick", this, KeyEvent.VK_L, Event.CTRL_MASK + Event.SHIFT_MASK, true);
        getMenubar().addSeparator(strKey);
        //cr_0805036452		getMenubar().addMenu(strKey, "deCol", this, 0,0,false);
        //cr_0805036452		getMenubar().addMenu(strKey, "deRow", this, 0,0,false);
        //cr_7145/6452		getMenubar().addMenu(strKey, "deCol", this, KeyEvent.VK_DELETE,0,false);							//cr_0805036452
        getMenubar().addMenu(strKey, "deCol", this, KeyEvent.VK_DELETE, Event.ALT_MASK, false); //cr_7145/6452
        getMenubar().addMenu(strKey, "deRow", this, KeyEvent.VK_DELETE, Event.CTRL_MASK, false); //cr_0805036452
        getMenubar().addMenu(strKey, "rstSel", this, KeyEvent.VK_DELETE, Event.SHIFT_MASK, false); //cr_featureMatrix
        getMenubar().addSeparator(strKey);
        getMenubar().addMenu(strKey, "aCol", this, 0, 0, false);
        getMenubar().addMenu(strKey, "aRow", this, 0, 0, false);
        getMenubar().setMenuMnemonic(strKey, getChar("act-s"));
    }

    private void removeActionMenu() {
        //		getMenubar().removeMenuItem("save", this);
    	getMenubar().removeMenuItem("cncl", this);
        if (actionExists(ACTION_PURPOSE_EDIT)) {
        	getMenubar().removeMenuItem("edit", this);
        }
        if (actionExists(ACTION_PURPOSE_WHERE_USED)) {
        	getMenubar().removeMenuItem("used", this);
        }
        getMenubar().removeMenuItem("pick", this);
        getMenubar().removeMenuItem("deCol", this);
        getMenubar().removeMenuItem("deRow", this);
        getMenubar().removeMenuItem("rstSel", this); //cr_featureMatrix
        getMenubar().removeMenuItem("aCol", this);
        getMenubar().removeMenuItem("aRow", this);
        getMenubar().removeMenu(getString("act"));
    }


    /**
     * createTableMenu
     *
     * @author Anthony C. Liberto
     */
    protected void createTableMenu() { //22045
        String strKey = getString("tbl");
        //cr_0805036452		getMenubar().addMenu(strKey,"left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK + Event.SHIFT_MASK,true);	//22045
        getMenubar().addMenu(strKey, "left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK, true); //cr_0805036452
        //cr_0805036452		getMenubar().addMenu(strKey,"right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK + Event.SHIFT_MASK,true);
        getMenubar().addMenu(strKey, "right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK, true); //cr_0805036452
        getMenubar().addSeparator(strKey); //220451
        getMenubar().addMenu(strKey, "srt", this, 0, 0, true); //22045
        getMenubar().addMenu(strKey, "srtH", this, 0, 0, true); //51112
        getMenubar().addSeparator(strKey); //51832
        getMenubar().addMenu(strKey, "hide", this, 0, 0, true); //51832
        getMenubar().addMenu(strKey, "unhide", this, 0, 0, true); //51832
        getMenubar().addSeparator(strKey); //pivot
        getMenubar().addMenu(strKey, "pivot", this, KeyEvent.VK_P, Event.CTRL_MASK, true); //pivot
        getMenubar().addSeparator(strKey); //22045
        getMenubar().addMenu(strKey, "selA", this, KeyEvent.VK_A, Event.CTRL_MASK, true); //22045
        getMenubar().addMenu(strKey, "iSel", this, KeyEvent.VK_I, Event.CTRL_MASK, true); //22045
        getMenubar().addSeparator(strKey); //22045
        getMenubar().addMenu(strKey, "f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true); //22045
        getMenubar().addMenu(strKey, "fltr", this, KeyEvent.VK_F8, 0, true); //cr_0805036452
        getMenubar().addSeparator(strKey); //52910
        getMenubar().addMenu(strKey, "saveO", this, 0, 0, true); //52910
        getMenubar().addMenu(strKey, "rvrtO", this, 0, 0, true); //52910
		if (EAccess.isCaptureMode()) {
	        getMenubar().addSeparator(strKey);
	        getMenubar().addMenu(strKey, "capture", this, KeyEvent.VK_F5, Event.CTRL_MASK + Event.SHIFT_MASK, true);
		}
		
		getMenubar().addSeparator(strKey);
        //51056		getMenubar().addMenu(strKey,"histI",this,KeyEvent.VK_F11, Event.CTRL_MASK, true);
		getMenubar().addMenu(strKey, "eData", this, KeyEvent.VK_F12, Event.CTRL_MASK, true);
		
        getMenubar().setMenuMnemonic(strKey, getChar("tbl-s")); //22045
    } //22045


    /**
     * removeTableMenu
     *
     * @author Anthony C. Liberto
     */
    protected void removeTableMenu() { //22045
		if (getMenubar() != null ){//MN31311135 prevent null ptr if deref is called before this is
			getMenubar().removeMenuItem("left", this); //22045
			getMenubar().removeMenuItem("right", this); //22045
			getMenubar().removeMenuItem("selA", this); //22045
			getMenubar().removeMenuItem("iSel", this); //22045
			getMenubar().removeMenuItem("srt", this); //22045
			getMenubar().removeMenuItem("srtH", this); //51112
			getMenubar().removeMenuItem("hide", this); //51832
			getMenubar().removeMenuItem("unhide", this); //51832
			getMenubar().removeMenuItem("pivot", this); //pivot
			getMenubar().removeMenuItem("f/r", this); //22045
			getMenubar().removeMenuItem("fltr", this); //22045
			//51056		getMenubar().removeMenuItem("histI",this);
			getMenubar().removeMenuItem("eData", this);	
			getMenubar().removeMenuItem("saveO", this); //52910
			getMenubar().removeMenuItem("rvrtO", this); //52910
			getMenubar().removeMenuItem("capture",this);
			getMenubar().removeMenu(getString("tbl")); //22045
		}
    } //22045

    private void createViewMenu() {
        String strKey = getString("view");
        getMenubar().addMenu(strKey, "toglAct", this, KeyEvent.VK_T, Event.CTRL_MASK, true); //21766
        getMenubar().addMenu(strKey, "toglCrs", this, KeyEvent.VK_T, Event.CTRL_MASK + Event.SHIFT_MASK, true); //acl_20021108
        getMenubar().setMenuMnemonic(strKey, getChar("view-s"));
    }

    private void removeViewMenu() {
    	getMenubar().removeMenuItem("toglAct", this);
    	getMenubar().removeMenuItem("toglCrs", this); //acl_20021108
        getMenubar().removeMenu(getString("view"));
    }

    /**
     * refreshMenu
     *
     * @author Anthony C. Liberto
     */
    protected void refreshMenu() {
        EANActionItem[] eai = getActionController().getTree().getActionItemArray(ACTION_PURPOSE_EDIT);
        boolean bHasData = false;
        if (isFeatureMatrix()) {
            CrossTable crssTable = getCrossTable();
            if (crssTable != null) {
                bHasData = crssTable.hasRows() && crssTable.hasColumns();
            }
        } else {
            bHasData = mTable.hasRows() && mTable.hasColumns();
        }
        if (getMenubar() != null) {
        	getMenubar().adjustSubAction("edit", eai, bHasData, ACTION_ALL);
        }
        if (mTool != null) {
            mTool.adjustSubAction("edit", eai, bHasData, ACTION_ALL);
        }

        eai = getActionController().getTree().getActionItemArray(ACTION_PURPOSE_WHERE_USED); //cr_TBD_3
        if (getMenubar() != null) {
        	getMenubar().adjustSubAction("used", eai, bHasData, ACTION_ALL);
        } //cr_TBD_3
        if (mTool != null) {
            mTool.adjustSubAction("used", eai, bHasData, ACTION_ALL);
        } //cr_TBD_3
    }


    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    protected void init() {
        ERowList mRowList = null;
        JViewport mView = null;
        RowSelectableTable rst = null;
        EntityGroup eg = null;
        RowSelectableTable actTbl = null;
        super.init();
        if (mList == null) {
            return;
        }
        rst = mList.getTable();
        mTable = new MtrxTable(mList, rst, getActionController());
        mTable.addFocusListener(this);
        mTable.setMatrixAction(this);
        mTable.setActionTree(getActionController().getTree()); //cr_TBD_3
        mScroll = new EScrollPane(mTable);

        mRowList = mTable.refreshList();
        mScroll.setRowHeaderView(mRowList);
        mView = mScroll.getRowHeader();
        mView.setViewSize(mRowList.getListSize());
        mView.setSize(mRowList.getListSize());
        mView.setPreferredSize(mRowList.getListSize());
        mScroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, mRowList.getHeader());

        refreshCrossTab();
        cTable.addFocusListener(this);

        processSplit();

        if (mTool != null) {
            mPanel.add(mTool.getAlignment(), mTool);
        }
        if (cTool != null) {
            cPanel.add(cTool.getAlignment(), cTool);
        }

        if (mTable.getRowCount() > 0) {
            mTable.setRowSelectionInterval(0, 0);
            if (mTable.getColumnCount() > 0) {
                mTable.setColumnSelectionInterval(0, 0);
            }
        }
        //51298		getActionController().getTree().load(mList.getEntityList().getParentEntityGroup());
        eg = mList.getEntityList().getParentEntityGroup(); //51298
        actTbl = eg.getActionGroupTable(); //rst_update
        getActionController().getTree().load(eaccess().getExecutableActionItems(eg, actTbl), eaccess().getActionTitle(eg, actTbl)); //51298
    }

    /**
     * refreshCrossTab
     * @author Anthony C. Liberto
     */
    public void refreshCrossTab() {
        ERowList cList = null;
        if (cTable != null) {
            cTable.setActionController(null);
            cTable.setMatrixAction(null);
        }
        cTable = mTable.getCrossTable();
        cTable.setActionController(getActionController());
        cTable.setMatrixAction(this);
        setSort(CROSS, mTable.getKeys(), true);
        cScroll.setViewportView(cTable);
        cList = cTable.refreshList();
        if (cTable.getRowCount() > 0) { //51444
            cTable.setRowSelectionInterval(0, 0); //51444
            if (cTable.getColumnCount() > 0) { //51444
                cTable.setColumnSelectionInterval(0, 0);
            } //51444
        } //51444
        if (cList != null) {
            JViewport cView = null;
            Dimension d = null;
            cScroll.setRowHeaderView(cList);
            cView = cScroll.getRowHeader();
            d = cList.getListSize();
            cView.setViewSize(d);
            cView.setSize(d);
            cView.setPreferredSize(d);
            cScroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, cList.getHeader());
        }
        adjustCrossActions(); //21845
    }

    private void processSplit() {
        String mtrxPos = null;
        String crossPos = null;
        Dimension min = null;
        split = new ESplitPane(getActionController().getSplitType());
        split.setOneTouchExpandable(getActionController().isOneTouch());
        mtrxPos = getActionController().getMatrixPosition();
        crossPos = getActionController().getCrossPosition();
        if (!isValidSplit(mtrxPos, crossPos)) {
            return;
        }

        mPanel.add("Center", mScroll);
        cPanel.add("Center", cScroll);
        addToSplit(split, mPanel, mtrxPos);
        addToSplit(split, cPanel, crossPos);

        min = UIManager.getDimension("eannounce.minimum");
        cPanel.setMinimumSize(min);
        mPanel.setMinimumSize(min);
        split.setMinimumSize(min);

        getSplitPane().setRightComponent(split);
        tree = getActionController().getTree();
        getSplitPane().setLeftComponent(tree);

        if (eaccess().isExpandAction()) { //xpnd_action
            getSplitPane().setDividerLocation(tree.getPreferredWidth()); //xpnd_action
        } else { //xpnd_action
            getSplitPane().setDividerLocation(0); //xpnd_action
        } //xpnd_action
        getSplitPane().setLastDividerLocation(tree.getPreferredWidth()); //21766

        split.setResizeWeight(getActionController().getSplitPostion());
        split.setDividerLocation(getActionController().getSplitPostion());
        split.setLastDividerLocation(eaccess().getLogin().getHeight() / 2);
    }

    private void toggleSplit() { //21766
        int divLoc = getSplitPane().getDividerLocation(); //21766
        if (divLoc < 10) { //21766
            getSplitPane().setDividerLocation(tree.getPreferredWidth()); //21766
        } else { //21766
            getSplitPane().setDividerLocation(0); //21766
        } //21766
        repaint(); //21766
    }

    private void addToSplit(ESplitPane _split, JPanel _pnl, String _pos) {
        if (_pos.equalsIgnoreCase("top")) {
            _split.setTopComponent(_pnl);

        } else if (_pos.equalsIgnoreCase("bottom")) {
            _split.setBottomComponent(_pnl);

        } else if (_pos.equalsIgnoreCase("left")) {
            _split.setLeftComponent(_pnl);

        } else if (_pos.equalsIgnoreCase("right")) {
            _split.setRightComponent(_pnl);
        }
    }

    /**
     * okToClose
     *
     * @param _reset
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean okToClose(boolean _reset) {
        CrossTable crssTable = getCrossTable();
        if (crssTable.isEditing()) {
            crssTable.finishEditing();
        }
        if (hasChanges()) {
            int action = -1;
            setCode("updtMsg2");
            action = showConfirm(YES_NO_CANCEL, true);
            eaccess().repaintImmediately();
            if (action == 0) {
                boolean b = false;
                setBusy(true);
                b = update();
                setBusy(false);
                if (b){
                	crssTable.unlock();
                }
                return b;
            } else if (action == 1) {
                if (_reset) {
                    setBusy(true);
                    cancel();
                    setBusy(false);
                }
                crssTable.unlock();
                return true;
            } else { //52633
                setBusy(false); //52633
            }
            return false;
        }
        crssTable.unlock();
        return true;

    }

    /*
     * edit stuff
     */
    /**
     * actionExists
     * @param _sAction
     * @return
     * @author Anthony C. Liberto
     */
    private boolean actionExists(String _sAction) {
        if (getActionController() != null) {
            return getActionController().actionExists(_sAction);
        }
        return false;
    }

    private void loadAction(Object _o, int _navType) {
        if (_o instanceof SubAction) {
            performAction(getActionController().getTree().getActionItem(((SubAction) _o).getKey()), _navType);
        }
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
        if (_ai instanceof EditActionItem) {
            if (focusTable == mTable) {
                EntityList eList = null;
                EditController ec = null;
                EntityItem[] ei = null;
                try {
                    ei = getActionController().getRelatedEntityItems(ActionController.PARENT, (EntityItem[]) mTable.getSelectedObjects(false, true));
                } catch (OutOfRangeException _range) {
                    _range.printStackTrace();
                    setMessage(_range.toString());
                    showError();
                    return;
                }
                if (ei != null) {
                    if (eaccess().editContains(ei, _ai)) {
                        return;
                    }
                    eList = dBase().getEntityList(_ai, ei, this);
                    ec = new EditController(eList, null);
                    ec.setParentProfile(getProfile());
                    addTab(getActionController(), ec); //kehrli_20030929
                    setBusy(false);
                    return;
                }
            }
            setCode("msg11014.1");
            setParm(getString("edit"));
            eaccess().showFYI(this);
        } else if (_ai instanceof WhereUsedActionItem) { //cr_TBD_3
            if (focusTable == mTable) { //cr_TBD_3
                EntityItem[] ei = null; //cr_TBD_3
                try { //cr_TBD_3
                    ei = getActionController().getRelatedEntityItems(ActionController.PARENT, (EntityItem[]) mTable.getSelectedObjects(false, true)); //cr_TBD_3
                } catch (OutOfRangeException _range) { //cr_TBD_3
                    _range.printStackTrace(); //cr_TBD_3
                    setMessage(_range.toString()); //cr_TBD_3
                    showError(); //cr_TBD_3
                    return; //cr_TBD_3
                } //cr_TBD_3
                if (ei != null) { //cr_TBD_3
                    WhereUsedList wList = null;
                    ActionController aControl = null;
                    //String name = null;
                    if (eaccess().editContains(ei, _ai)) { //cr_TBD_3
                        setBusy(false); //cr_TBD_3
                        return; //cr_TBD_3
                    } //cr_TBD_3
                    wList = dBase().rexec((WhereUsedActionItem) _ai, ei, this); //cr_TBD_3
                    aControl = new ActionController(wList, getActionController().getParentKey()); //cr_TBD_3
                    aControl.setParentProfile(getProfile()); //cr_TBD_3
                    aControl.setOPWGID(getActionController().getOPWGID()); //cr_TBD_3
                    aControl.setCart(getCart()); //acl_20031219
                    addTab(getActionController(), aControl); //kehrli_20030929
                    setBusy(false); //cr_TBD_3
                    return; //cr_TBD_3
                } //cr_TBD_3
            } //cr_TBD_3
        } //cr_TBD_3
        setBusy(false);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        appendLog("matrixAction.actionPerformed(" + action + ")");
        if (isBusy()) {
            appendLog("    I am busy");
            return;
        }
        if (action.equals("edit")) {
            loadAction(_ae.getSource(), 0);
            return;
        } else if (action.equals("used")) { //cr_TBD_3
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
        	} 
        	
        	Long t11 = EAccess.eaccess().timestamp("MatrixAction performAction "+action+" started");
        	if (action.equals("exit")) {
        		eaccess().exit("exit Matrix");
        	} else if (action.equals("saveT")) {
        		eaccess().save(mList);
        	} else if (action.equals("f/r")) {
        		if (focusTable != null) {
        			focusTable.showFind();
        		}
        	} else if (action.equals("fltr")) {
        		if (focusTable != null) {
        			focusTable.showFilter();
        		}
        	} else if (action.equals("pivot")) { //pivot
        		pivot(); //pivot
        	} else if (action.equals("deRow")) {
        		verticalAdjust(0);
        	} else if (action.equals("deCol")) {
        		horizontalAdjust(0);
        	} else if (action.equals("aRow")) {
        		if (isRelAttr()) {
        			verticalAdjust();
        		} else {
        			verticalAdjust(-1);
        		}
        	} else if (action.equals("aCol")) {
        		if (isRelAttr()) {
        			horizontalAdjust();
        		} else {
        			horizontalAdjust(-1);
        		}
        	} else if (action.equals("rstSel")) { //cr_featureMatrix
        		resetSelected(); //cr_featureMatrix
        	} else if (action.equals("pick")) {
        		generatePicklist();
        	} else if (action.equals("toglAct")) { //21766
        		toggleSplit(); //21766
        	} else if (action.equals("toglCrs")) { //acl_20021108
        		toggleCross(); //acl_20021108
        	} else if (action.equals("srt")) {
        		if (focusTable != null) {
        			focusTable.showSort();
        		}
        	} else if (action.equals("srtH")) { //51112
        		if (focusTable instanceof MtrxTable) { //51112
        			((MtrxTable) focusTable).sortHeader(); //51112
        		} else if (focusTable instanceof CrossTable) { //51112
        			((CrossTable) focusTable).sortHeader(); //51112
        		} //51112
        	} else if (action.equals("hide")) { //51832
        		showHide(true); //51832
        	} else if (action.equals("unhide")) { //51832
        		showHide(false); //51832
        	} else if (action.equals("left")) {
        		if (focusTable != null) {
        			focusTable.moveColumn(true);
        		}
        	} else if (action.equals("right")) {
        		if (focusTable != null) {
        			focusTable.moveColumn(false);
        		}
        	} else if (action.equals("selA")) {
        		if (focusTable != null) {
        			focusTable.selectAll();
        		}
        	} else if (action.equals("iSel")) {
        		if (focusTable != null) {
        			focusTable.invertSelection();
        		}
        	} else if (action.equals("histI")) {
        		mTable.historyInfo();
        	} else if (action.equals("eData")) {
        		if (focusTable != null) {
        			focusTable.showInformation();
        		}
        	} else if (action.equals("cncl")) {
        		cancel();
        	} else if (action.equals("save")) {
        		update();
        	} else if (action.equals("saveO")) { //52910
        		saveOrder(); //52910
        	} else if (action.equals("rvrtO")) { //52910
        		revertOrder(); //52910
        	} else if (action.equals("capture")) {
        		if (mList != null) {
        			eaccess().capture(mList.dump(false));
        		}
        	} else if (action.equals("copy")) { //featureMatrix_copy
        		copy(); //featureMatrix_copy
        	} else if (action.equals("pste")) { //featureMatrix_copy
        		paste(); //featureMatrix_copy
        	}
        	EAccess.eaccess().timestamp("MatrixAction performAction "+action+" ended",t11);
        }catch(Exception exc){
        	eaccess().showException(exc, null,ERROR_MESSAGE,OK);
        } 
        setBusy(false);
    }

    /**
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusGained(FocusEvent _fe) {
        Object o = _fe.getSource();
        System.out.println("MatrixAction.focusGained "+o);
        if (o instanceof RSTable) {
            focusTable = (RSTable) o;
        }
        if (o instanceof CrossTable) {
            if (mTool != null) {
                mTool.setEnabled("f/r", false);
                mTool.setEnabled("fltr", false);
            }
            setEnabled("edit", false);
            getActionController().setTreeEnabled(false);
            if (cTool != null) {
                cTool.setEnabled("f/r", true);
				cTool.setEnabled("fltr",true);
//pivotFilter                cTool.setEnabled("fltr", !isPivot()); //pivot
            }
            adjustCrossActions(); //21845
        } else if (o instanceof MtrxTable) {
            if (mTool != null) {
                mTool.setEnabled("f/r", true);
                mTool.setEnabled("fltr",true);
//pivotFilter                mTool.setEnabled("fltr", !isPivot()); //pivot
            }
            //50649			setEnabled("edit", true);
            setEnabled("edit", actionExists(ACTION_PURPOSE_EDIT)); //50649
            setEnabled("used", actionExists(ACTION_PURPOSE_WHERE_USED));
            getActionController().setTreeEnabled(true);
            if (cTool != null) {
                cTool.setEnabled("f/r", false);
                cTool.setEnabled("fltr", false);
            }
            adjustCrossActions(false); //21845
        }
    }

    /**
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusLost(FocusEvent _fe) {}

    /**
     * getProfile
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected Profile getProfile() {
        if (mList == null) {
            return null;
        }
        return mList.getProfile();
    }

    /**
     * copy
     *
     * @author Anthony C. Liberto
     */
    private void copy() {
        if (isFeatureMatrix()) { //featurematrix_copy
            CrossTable cTbl = getCrossTable(); //featurematrix_copy
            if (cTbl != null) { //featurematrix_copy
                cTbl.copy(); //featurematrix_copy
            } //featurematrix_copy
        } //featurematrix_copy
    }


    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    private void paste() {
        if (isFeatureMatrix()) { //featurematrix_copy
            CrossTable cTbl = getCrossTable(); //featurematrix_copy
            if (cTbl != null) { //featurematrix_copy
                cTbl.paste(); //featurematrix_copy
            } //featurematrix_copy
        } //featurematrix_copy
    }

    /**
     * getHelpText
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getHelpText() {
        return mTable.getHelpText();
    }

    /**
     * isFiltered
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isFiltered() {
        if (focusTable != null) {
            return focusTable.isFiltered();
        }
        return false;
    }

    /**
     * addColumn
     * @param _ei
     * @author Anthony C. Liberto
     */
    public void addColumn(EntityItem[] _ei) {
        CrossTable crssTable = getCrossTable();
        if (crssTable != null) {
	        crssTable.addColumn(_ei);
	        refreshCrossHeader(); //52283
	        if (crssTable.isPivot()) {
	            crssTable.pivotWrapup(false);
	        }
	        adjustCrossActions(crssTable.hasRows() && crssTable.hasColumns()); //lb_20050420
		}
    }

    private int getN() {
        return eaccess().getNumber("msg24003");
    }

    private void verticalAdjust(int _i) {
        CrossTable crssTable = getCrossTable();
        int cc = crssTable.getColumnCount();
        int[] rows = crssTable.getSelectedRows();
        int rr = rows.length; //51597
        if (rr == 0) { //51597
            setCode("msg12008.0"); //51597
            setParm(getString("fRow")); //51597
            showError(); //51597
            return; //51597
        } //51597
        if (_i < 0) { //51597
            _i = getN(); //51597
        } //51597
        if (_i < 0) { //51597
            return; //51597
        } //51597
        crssTable.setUpdateArmed(false); //acl_20030917
        for (int r = 0; r < rr; ++r) {
            for (int c = 0; c < cc; ++c) {
                crssTable.setValueAt(Integer.toString(_i), rows[r], crssTable.convertColumnIndexToModel(c));
            }
        }
        crssTable.setUpdateArmed(true); //acl_20030917
        refreshTable(MATRIX, true); //acl_20030917
        crssTable.repaint();
    }

    private void horizontalAdjust(int _i) {
        CrossTable crssTable = getCrossTable();
        int rr = crssTable.getRowCount();
        int[] cols = crssTable.getSelectedColumns();
        int cc = cols.length; //51597
        if (cc == 0) { //51597
            setCode("msg12008.0"); //51597
            setParm(getString("fCol")); //51597
            showError(); //51597
            return; //51597
        } //51597
        if (_i < 0) { //51597
            _i = getN(); //51597
        } //51597
        if (_i < 0) { //51597
            return; //51597
        } //51597
        crssTable.setUpdateArmed(false); //acl_20030917
        for (int c = 0; c < cc; ++c) {
            for (int r = 0; r < rr; ++r) {
                crssTable.setValueAt(Integer.toString(_i), r, crssTable.convertColumnIndexToModel(cols[c]));
            }
        }
        crssTable.setUpdateArmed(true); //acl_20030917
        refreshTable(MATRIX, true); //acl_20030917
        crssTable.repaint();
    }

    /**
     * setEnabled
     * @author Anthony C. Liberto
     * @param _enabled
     * @param _key
     */
    public void setEnabled(String _key, boolean _enabled) {
        getMenubar().setEnabled(_key, _enabled);
        if (mTool != null) {
            mTool.setEnabled(_key, _enabled);
        }
        if (cTool != null) {
            cTool.setEnabled(_key, _enabled);
        }
    }

    private void adjustCrossActions() { //21845
        boolean hasRows = false;
        boolean hasCols = false;
        if (focusTable != cTable) { //21845
            adjustCrossActions(false); //21845
            return; //21845
        } //21845
        hasRows = cTable.hasRows(); //21845
        hasCols = cTable.hasColumns(); //21845
        adjustCrossActions(hasRows && hasCols); //21845
    }

    private void adjustCrossActions(boolean _b) { //21845
        setEnabled("deRow", _b); //21845
        setEnabled("deCol", _b); //21845
        setEnabled("aRow", _b); //21845
        setEnabled("aCol", _b); //21845
        setEnabled("rstSel", _b); //cr_featureMatrix
    } //21845


    /**
     * update
     *
     * @return
     * @author Anthony C. Liberto
     */
    private boolean update() {
        boolean bCommit = false;
        if (cTable != null) { //53498
            cTable.saveCurrentEdit(); //53498
        } //53498
        bCommit = mTable.commit();
        refreshButtons();
        return bCommit;
    }

    private void cancel() {
        mTable.rollbackMatrix();
        refreshTable(0, true);
        refreshTable(1, true);
    }

    /**
     * refreshTable
     * @param _i
     * @param _r
     * @param _c
     * @param _resize
     * @author Anthony C. Liberto
     */
    protected void refreshTable(int _i, int _r, int _c, boolean _resize) { //23317
        if (isFeatureMatrix()) {
            requestFocus(CROSS);
            refreshButtons();
        } else if (_i == MATRIX) { //23317
            mTable.refreshTable(_r, _c, _resize); //23317
            refreshMatrixHeader(); //23317
            refreshButtons(); //23317
        } else if (_i == CROSS) { //23317
            cTable.refreshTable(_r, _c, _resize); //23317
        } //23317
    } //23317

    /**
     * refreshTable
     * @param _i
     * @param _resize
     * @author Anthony C. Liberto
     */
    protected void refreshTable(int _i, boolean _resize) {
        if (isFeatureMatrix()) {					//lb_nullpointer
            CrossTable ctbl = getCrossTable();		//lb_nullpointer
            if (ctbl != null) {						//lb_nullpointer
                ctbl.refreshTable(_resize);			//lb_nullpointer
            }										//lb_nullpointer
        } else if (_i == MATRIX) {
            mTable.refreshTable(_resize);
            refreshMatrixHeader();
            refreshButtons();
        } else if (_i == CROSS) {
            cTable.refreshTable(_resize);
        }
    }

    private void refreshButtons() {
        boolean bChanges = hasChanges();
        setEnabled("save", bChanges);
        setEnabled("cncl", bChanges);
    }

    /**
     * setSort
     * @param _i
     * @param _sort
     * @param _modelChanged
     * @author Anthony C. Liberto
     */
    public void setSort(int _i, EANFoundation[] _sort, boolean _modelChanged) {
        if (_i == MATRIX) {
            mTable.setSort(_sort, _modelChanged);
        } else if (_i == CROSS) {
            cTable.setSort(_sort, _modelChanged);
        }
    }

    /**
     * refreshHeader
     * @param _i
     * @author Anthony C. Liberto
     */
    public void refreshHeader(int _i) {
        if (_i == MATRIX) {
            refreshMatrixHeader();
        } else if (_i == CROSS) {
            refreshCrossHeader();
        }
    }

    private void refreshMatrixHeader() {
        JViewport mView = null;
        Dimension dSize = null;
        ERowList tmpList = null;
        if (isFeatureMatrix()) {
            return;
        }
        tmpList = mTable.refreshList();
        mScroll.setRowHeaderView(tmpList);
        mView = mScroll.getRowHeader();
        dSize = tmpList.getListSize();
        mView.setViewSize(dSize);
        mView.setSize(dSize);
        mView.setPreferredSize(dSize);
        tmpList.revalidate();
        mScroll.revalidate();
    }

    private void refreshCrossHeader() {
        JViewport cView = null;
        Dimension dSize = null;
        if (isFeatureMatrix()) {
            EScrollPane scroll = getScroll();
            CrossTable tbl = (CrossTable) scroll.getViewport().getView();
            ERowList cList = tbl.refreshList();
            scroll.setRowHeaderView(cList);
            cView = scroll.getRowHeader();
            dSize = cList.getListSize();
            cView.setViewSize(dSize);
            cView.setSize(dSize);
            cView.setPreferredSize(dSize);
            cList.revalidate();
            scroll.revalidate();
        } else {
            ERowList cList = cTable.refreshList();
            cScroll.setRowHeaderView(cList);
            cView = cScroll.getRowHeader();
            dSize = cList.getListSize();
            cView.setViewSize(dSize);
            cView.setSize(dSize);
            cView.setPreferredSize(dSize);
            cList.revalidate();
            cScroll.revalidate();
        }
    }

    /**
     * synchronizeSortHeader
     * @param _i
     * @author Anthony C. Liberto
     */
    public void synchronizeSortHeader(int _i) {
        if (isFeatureMatrix()) {
            return;
        }
        if (_i == MATRIX) {
            mTable.getList().toggleIcon(cTable.getList().getDirection());
        } else if (_i == CROSS) {
            cTable.getList().toggleIcon(mTable.getList().getDirection());
        }
    }


    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        tree = null; //21766
        if (pick != null) {
            pick.dereference();
            pick = null;
        }

        if (mList!=null){
        	mList.dereference();
        	mList = null;
        }
        focusTable = null;
        if (mTable != null) {
            mTable.dereference();
            //mTable.removeAll();
            mTable = null;
        }

        if (cTable != null) {
            cTable.dereference();
            //cTable.removeAll();
            cTable = null;
        }

        if (cTool != null) {
            cTool.dereference();
            cTool = null;
        }

        if (mTool != null) {
            mTool.dereference();
            mTool = null;
        }

        if (mScroll != null) {
            mScroll.dereference();
            mScroll = null;
        }

        if (cScroll != null) {
            cScroll.dereference();
            cScroll = null;
        }

        if (mPanel != null) {
            mPanel.removeAll();
            mPanel.removeNotify();
            mPanel = null;
        }

        if (cPanel != null) {
            cPanel.removeAll();
            cPanel.removeNotify();
            cPanel = null;
        }

        if (pick != null) {
            pick.dereference();
            //pick.removeAll();
            //pick.removeNotify();
        }

        if (mTool != null) {
            mTool.dereference();
           // mTool.removeAll();
           // mTool.removeNotify();
            mTool = null;
        }

        if (cTool != null) {
            cTool.dereference();
           // cTool.removeAll();
           // cTool.removeNotify();
            cTool = null;
        }

        if (cPnl != null) {
            cPnl.dereference();
            cPnl = null;
        }
        removeMenus();
        super.dereference();
    }

    /**
     * contains
     * @author Anthony C. Liberto
     * @return boolean
     * @param _eai
     * @param _ei
     */
    protected boolean contains(EntityItem[] _ei, EANActionItem _eai) {
        return mList.equivalent(_ei, _eai);
    }

    /**
     * getTableTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getTableTitle() {
        if (isFeatureMatrix()) {
            return fTitle;
        }
        return mTable.getTableTitle();
    }

    /**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return mList.getParentActionItem().getEntityType();
    }

    /**
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected Object getSearchableObject() { //22377
        return focusTable; //22377
    } //22377

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        mTable.refreshAppearance();
        cTable.refreshAppearance();

        if (mTool != null) {
            mTool.refreshAppearance();
        }
        if (cTool != null) {
            cTool.refreshAppearance();
        }

        refreshMatrixHeader();
        refreshCrossHeader();
    }

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_MATRIXACTION;
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
        if (tree != null) {
            EANActionItem ean = tree.getActionItem(_action);
            if (ean != null) {
                mTable.highlight(_parent);
                performAction(ean, 0);
            }
        }
    }

    /*
     51413
     */
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

    /*
     acl_20030718
     */
    /**
     * selectKeys
     * @author Anthony C. Liberto
     * @param _keys
     */
    protected void selectKeys(String[] _keys) {
        if (focusTable != null) {
            focusTable.selectKeys(_keys);
        }
    }

    /*
     52311
     */
    /**
     * requestFocus
     *
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (isFeatureMatrix()) {
            CrossTable ctbl = getCrossTable();
            if (ctbl != null) {
                ctbl.requestFocus();
            }
        } else if (mTable != null) {
            mTable.requestFocus();
        }
    }

    /**
     * getTabMenuTitle
     *
     * @return
     */
    protected String getTabMenuTitleKey() { return "matrix.title";}

    /**
     * getTabIcon
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getTabIconKey() {
        return "matrix.icon";
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
        if (mTable != null) {
            mTable.showHide(_b);
            bHidden = _b; //acl_20031217
            if (_b) { //acl_20031217
                eaccess().setHidden(_b); //acl_20031217
            } else { //acl_20031217
                eaccess().setHidden(hasHiddenAttributes()); //acl_20031217
            } //acl_20031217
        }
    }

    /*
     52910
     */
    /**
     * saveOrder
     * @author Anthony C. Liberto
     */
    private void saveOrder() {
        Exception x = null;
        if (mTable != null) {
            MetaColumnOrderGroup mcog = getMetaColumnOrderGroup();
            if (mcog != null) {
                mTable.adjustOrder(mcog);
                x = dBase().commit(mcog, this);
                if (x != null) {
                    showException(x, ERROR_MESSAGE, OK);
                }
            }
        }
    }

    /**
     * revertOrder
     * @author Anthony C. Liberto
     */
    private void revertOrder() {
        MetaColumnOrderGroup mcog = getMetaColumnOrderGroup();
        if (mcog != null && mTable != null) {
            Exception x = dBase().resetToDefaults(mcog, this);
            if (x == null) {
                mTable.revertOrder();
            } else {
                showException(x, ERROR_MESSAGE, OK);
            }
        }
    }

    private MetaColumnOrderGroup getMetaColumnOrderGroup() {
        if (mList != null) {
            return mList.getMetaColumnOrderGroup();
        }
        return null;
    }

    /*
     acl_20031217
     */
    /**
     * hasHiddenAttributes
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean hasHiddenAttributes() {
        MetaColumnOrderGroup mcog = getMetaColumnOrderGroup();
        if (mcog != null) {
            if (mcog.hasHiddenItems()) {
                return true;
            } else {
                return bHidden;
            }
        }
        return false;
    }

    /*
     cr_916036951
     */
    private void toggleCross() {
        if (split.getProportionalPosition() > .5D) {
            split.minimize();
        } else {
            split.maximize();
        }
        repaint();
    }

    /*
     cr_featureMatrix
     */
    private void resetSelected() {
        CrossTable tble = getCrossTable();
        if (tble != null) {
            tble.resetSelected();
        }
    }

    /**
     * initFeatureMatrix
     * @author Anthony C. Liberto
     */
    private void initFeatureMatrix() {
        EntityGroup eg = null;
        RowSelectableTable rst = null;
        RowSelectableTable actTbl = null;
        ERowList cList = null;
        int ii = -1;
        super.init();
        if (mList == null) {
            return;
        }
        cPnl.setMessage(getString("chooseVal"));
        cPnl.showButtons(true);
        rst = mList.getTable();
        mTable = new MtrxTable(mList, rst, getActionController());
        fTitle = mList.getTable().getTableTitle();
        ii = mList.getMatrixGroupCount();
        for (int i = ii - 1; i >= 0; --i) {
            MatrixGroup mg = mList.getMatrixGroup(i);
            if (mg != null) {
                CrossTable crssTable = new CrossTable(this, mg.getTable(), null);
                EScrollPane scroll = new EScrollPane(crssTable);
                crssTable.setLongDescription(false);
                crssTable.setActionController(getActionController());
                crssTable.setMatrixAction(this);
                crssTable.setMatrixGroup(mg);
                if (crssTable.getRowCount() > 0) {
                    crssTable.setRowSelectionInterval(0, 0);
                    if (crssTable.getColumnCount() > 0) {
                        crssTable.setColumnSelectionInterval(0, 0);
                    }
                }
                cList = crssTable.refreshList();
                if (cList != null) {
                    JViewport cView = null;
                    Dimension d = null;
                    scroll.setRowHeaderView(cList);
                    cView = scroll.getRowHeader();
                    d = cList.getListSize();
                    cView.setViewSize(d);
                    cView.setSize(d);
                    cView.setPreferredSize(d);
                    scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, cList.getHeader());
                }
                tPane.addTab(crssTable.getTableTitle(), scroll);
            }
        }
        if (cTool != null) {
            cPanel.add(cTool.getAlignment(), cTool);
        }
        cPanel.add("Center", tPane);

        getSplitPane().setRightComponent(cPanel);
        tree = getActionController().getTree();
        getSplitPane().setLeftComponent(tree);
        getSplitPane().setDividerLocation(0);
        getSplitPane().setLastDividerLocation(tree.getPreferredWidth());

        eg = mList.getEntityList().getParentEntityGroup();
        actTbl = eg.getActionGroupTable();
        getActionController().getTree().load(eaccess().getExecutableActionItems(eg, actTbl), eaccess().getActionTitle(eg, actTbl));
    }

    private void removeFeatureActionMenu() {
        getMenubar().removeMenuItem("cncl", this);
        getMenubar().removeMenuItem("edit", this);
        getMenubar().removeMenuItem("used", this);
        getMenubar().removeMenuItem("pick", this);
        getMenubar().removeMenuItem("deCol", this);
        getMenubar().removeMenuItem("deRow", this);
        getMenubar().removeMenuItem("rstSel", this);
        getMenubar().removeMenuItem("aCol", this);
        getMenubar().removeMenuItem("aRow", this);
        getMenubar().removeMenu(getString("act"));
    }

    private void createFeatureActionMenu() {
        String strKey = getString("act");
        getMenubar().addMenu(strKey, "cncl", this, KeyEvent.VK_Z, Event.CTRL_MASK, false);
        getMenubar().addSeparator(strKey);
        if (actionExists(ACTION_PURPOSE_EDIT)) {
            getMenubar().addSubMenu(strKey, "edit", this, 0, 0);
        }
        if (actionExists(ACTION_PURPOSE_WHERE_USED)) {
            getMenubar().addSubMenu(strKey, "used", this, 0, 0);
        }
        getMenubar().addMenu(strKey, "pick", this, KeyEvent.VK_L, Event.CTRL_MASK + Event.SHIFT_MASK, true);
        getMenubar().addSeparator(strKey);
        getMenubar().addMenu(strKey, "deCol", this, KeyEvent.VK_DELETE, Event.ALT_MASK, false); //cr_7145/6452
        getMenubar().addMenu(strKey, "deRow", this, KeyEvent.VK_DELETE, Event.CTRL_MASK, false); //cr_0805036452
        getMenubar().addMenu(strKey, "rstSel", this, KeyEvent.VK_DELETE, Event.SHIFT_MASK, false); //cr_featureMatrix
        getMenubar().addSeparator(strKey);
        getMenubar().addMenu(strKey, "aCol", this, 0, 0, false);
        getMenubar().addMenu(strKey, "aRow", this, 0, 0, false);
        getMenubar().setMenuMnemonic(strKey, getChar("act-s"));
    }

    /**
     * getScroll
     * @return
     * @author Anthony C. Liberto
     */
    private EScrollPane getScroll() {
        if (isFeatureMatrix()) {
            return getScroll(tPane.getSelectedIndex());
        }
        return null;
    }

    /**
     * getScroll
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    private EScrollPane getScroll(int _i) {
        Component comp = tPane.getComponentAt(_i);
        if (comp != null) {
            if (comp instanceof EScrollPane) {
                return (EScrollPane) comp;
            }
        }
        return null;
    }

    /**
     * getCrossTable
     * @return
     * @author Anthony C. Liberto
     */
    private CrossTable getCrossTable() {
        if (isFeatureMatrix()) {
            return getCrossTable(tPane.getSelectedIndex());
        }
        return cTable;
    }

    /**
     * getCrossTable
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    private CrossTable getCrossTable(int _i) {
        EScrollPane scroll = getScroll(_i);
        if (scroll != null) {
            return (CrossTable) scroll.getViewport().getView();
        }
        return null;
    }

    /**
     * hasChanges
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasChanges() {
        if (isFeatureMatrix()) {
            return mList.hasChanges();
        }
        return mTable.hasChanges();
    }

    /**
     * isFeatureMatrix
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isFeatureMatrix() {
        if (mList != null) {
            return mList.isFeatureMatrix();
        }
        return false;
    }

    /**
     * setSelectedCrossTable
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setSelectedCrossTable(int _i) {
        focusTable = getCrossTable(_i);
        if (focusTable != null) {
            boolean bRel = isRelAttr();
            boolean bHasData = focusTable.isFocusable();
            setEnabled("deRow", bHasData && !bRel);
            setEnabled("deCol", bHasData && !bRel);
            setEnabled("aRow", bHasData);
            setEnabled("aCol", bHasData);
            setEnabled("rstSel", bHasData);
        }
    }
    /*
     feature fill
     */
    private boolean isRelAttr() {
        if (focusTable != null && focusTable instanceof CrossTable) {
            return ((CrossTable) focusTable).showRelAttr();
        }
        return false;
    }

    private void verticalAdjust(CrossTable _tbl, Object _o) {
        int cc = _tbl.getColumnCount();
        int[] rows = _tbl.getSelectedRows();
        int rr = rows.length;
        if (rr == 0) {
            setCode("msg12008.0");
            setParm(getString("fRow"));
            showError();
            return;
        }
        _tbl.setUpdateArmed(false);
        for (int r = 0; r < rr; ++r) {
            for (int c = 0; c < cc; ++c) {
                _tbl.setValueAt(_o, rows[r], _tbl.convertColumnIndexToModel(c));
            }
        }
        _tbl.setUpdateArmed(true);
        refreshTable(MATRIX, true);
        _tbl.repaint();
    }

    private void verticalAdjust() {
        CrossTable tbl = getCrossTable();
        if (tbl != null) {
            Component fEdit = tbl.getFillComponent(tbl.getSelectedRow(), tbl.getSelectedColumn());
            cPnl.setComponent(fEdit);
            cPnl.updateDialog();
            show(this, cPnl, true);
            if (!cPnl.isCancel()) {
                verticalAdjust(tbl, cPnl.getValue());
            }
        }
    }

    private void horizontalAdjust(CrossTable _tbl, Object _o) {
        int rr = _tbl.getRowCount();
        int[] cols = _tbl.getSelectedColumns();
        int cc = cols.length;
        if (cc == 0) {
            setCode("msg12008.0");
            setParm(getString("fCol"));
            showError();
            return;
        }
        _tbl.setUpdateArmed(false);
        for (int c = 0; c < cc; ++c) {
            for (int r = 0; r < rr; ++r) {
                _tbl.setValueAt(_o, r, _tbl.convertColumnIndexToModel(cols[c]));
            }
        }
        _tbl.setUpdateArmed(true);
        refreshTable(MATRIX, true);
        _tbl.repaint();
    }

    private void horizontalAdjust() {
        CrossTable tbl = getCrossTable();
        if (tbl != null) {
            Component fEdit = tbl.getFillComponent(tbl.getSelectedRow(), tbl.getSelectedColumn());
            cPnl.setComponent(fEdit);
            cPnl.updateDialog();
            show(this, cPnl, true);
            if (!cPnl.isCancel()) {
                horizontalAdjust(tbl, cPnl.getValue());
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

    /**
     * showPicklist
     * @param _list
     * @return
     * @author Anthony C. Liberto
     */
    public boolean showPicklist(EntityList _list) {
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
    /*
     pivot
     */
    private void pivot() {
        if (isFeatureMatrix()) {
            CrossTable crssTable = getCrossTable();
            crssTable.clearSelection();
            crssTable.pivot();
            crssTable.pivotWrapup(true);
            crssTable.setRowSelectionInterval(0,0);
            crssTable.setColumnSelectionInterval(0,0);
        } else {
            mTable.clearSelection();
            mTable.pivot();
            cTable.clearSelection();
            cTable.pivot();
            mTable.pivotWrapup(true);
            mTable.setRowSelectionInterval(0,0);
            mTable.setColumnSelectionInterval(0,0);
            mTable.pivotResynch();
        }
        if (isPivot()) {
            getMenubar().renameItem("pivot", "unpivot");
//pivotFilter            setEnabled("fltr", false);
			setEnabled("srt",false);		//USRO-R-TMAY-69QQSU
        } else {
            getMenubar().renameItem("pivot", "pivot");
            if (mTool != null) {
                mTool.setEnabled("fltr", focusTable instanceof MtrxTable);
            }
            if (cTool != null) {
                cTool.setEnabled("fltr", focusTable instanceof CrossTable);
            }
//pivotFilter            getMenubar().setEnabled("fltr", true);
			setEnabled("srt",false);		//USRO-R-TMAY-69QQSU

        }
    }

    private boolean isPivot() {
        if (isFeatureMatrix()) {
            CrossTable cTbl = getCrossTable();
            if (cTbl != null) {
                return cTbl.isPivot();
            }
        } else if (mTable != null) {
            return mTable.isPivot();
        } else if (cTable != null) {
            return cTable.isPivot();
        }
        return false;
    }
    /*
     featureMatrix_copy
     */
    private void removeEditActionMenu() {
        getMenubar().removeMenuItem("copy", this);
        getMenubar().removeMenuItem("pste", this);
        getMenubar().removeMenu(getString("edit"));
    }

    private void createEditActionMenu() {
        String strKey = getString("edit");
        getMenubar().addMenu(strKey, "copy", this, KeyEvent.VK_C, Event.CTRL_MASK, true);
        getMenubar().addMenu(strKey, "pste", this, KeyEvent.VK_V, Event.CTRL_MASK, true);
        getMenubar().setMenuMnemonic(strKey, getChar("edit-s"));
    }

    /*
     USRO-R-JSTT-68L7VW
     */
    /**
     * generatePicklist
     * @author Anthony C. Liberto
     */
    private void generatePicklist() {
        EANActionItem[] act = getActionItemsAsArray();
        boolean bPickProcessed = false;
        if (act != null) {
            bPickProcessed = processActionItems(act);
        }
        if (!bPickProcessed) {
            bPickProcessed = generatePicklistNavigate();
        }
    }

    private EANActionItem[] getActionItemsAsArray() {
        EANActionItem[] out = null;
        if (isFeatureMatrix()) {
            CrossTable ctbl = getCrossTable();
            if (ctbl != null) {
                MatrixGroup mg = ctbl.getMatrixGroup();
                if (mg != null) {
                    out = ctbl.getActionItemsAsArray(0);
                }
            }
        } else {
            out = mTable.getActionItemsAsArray();
        }
        if (out == null || out.length < 1) {
            return null;
        }
        return out;
    }

    /**
     * generatePicklistNavigate
     * @return
     * @author Anthony C. Liberto
     */
    private boolean generatePicklistNavigate() {
        EntityList eList = null;
        if (isFeatureMatrix()) {
            CrossTable ctbl = getCrossTable();
            if (ctbl != null) {
                MatrixGroup mg = ctbl.getMatrixGroup();
                if (mg != null) {
                    eList = mTable.generatePickList(mg.getKey());
                }
            }
        } else {
            eList = mTable.generatePickList();
        }
        showPicklist(eList);
        return true;
    }

    private boolean processActionItems(EANActionItem[] _in) {
        if (_in != null) {
            int ii = _in.length;
            for (int i = 0; i < ii; ++i) {
                if (_in[i] != null) {
                    if (_in[i] instanceof SearchActionItem) {
                        processSearchAction((SearchActionItem) _in[i]);
                        return true;
                    } else if (_in[i] instanceof NavActionItem) {
                        generatePicklistNavigate();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    protected void refreshToolbar() {
		if (mTool != null) {
			mTool.refreshToolbar();
		}
		if (cTool != null) {
			cTool.refreshToolbar();
		}
		refreshMenu();
	}
    
    // obsolete or unused methods

    /**
     * getChatAction
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public ChatAction getChatAction() {
        EntityGroup group = null;
        if (mList != null) {
            ChatAction chat = new ChatAction();
            EANActionItem item = mList.getParentActionItem();
            if (item != null) {
                chat.setAction(item);
            }
            group = mList.getParentEntityGroup();
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
     * getTabToolTipText
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String getTabToolTipText() {
        return getTabMenuTitle();
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
        setParm(0, getActionController().getTableTitle());
        setParm(1, getActionController().getProfile().toString());
        name = getMessage();
        eaccess().clear();
        return name;
    }*/  


    /**
     * getTable
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public JTable getTable() {
        return null;
    }*/

    /**
     * refresh
     *
     * @author Anthony C. Liberto
     * /
    public void refresh() {
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

    /**
     * find
     *
     * @author Anthony C. Liberto
     * /
    private void find() {
        mTable.showFind();
    }*/

    /**
     * filter
     *
     * @author Anthony C. Liberto
     * fixme why is this never used?
     * /
    public void filter() {
        mTable.showFilter();
        return;
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
     * showHiddenRows
     * @param _i
     * @author Anthony C. Liberto
     * /
    public void showHiddenRows(int _i) {
        if (_i == MATRIX) {
            mTable.refreshTable(true);
        } else if (_i == CROSS) {
            cTable.refreshTable(true);
        }
        refreshHeader(_i);
        return;
    }*/

    /**
     * hideRow
     * @param _i
     * @param _row
     * @author Anthony C. Liberto
     * /
    public void hideRow(int _i, int _row) {
        if (_i == MATRIX) {
            mTable.refreshTable(true);
        } else if (_i == CROSS) {
            cTable.refreshTable(true);
        }
        refreshHeader(_i);
        return;
    }*/

    /**
     * closeLocalMenus
     *
     * @author Anthony C. Liberto
     * /
    public void closeLocalMenus() {
    }*/


}
