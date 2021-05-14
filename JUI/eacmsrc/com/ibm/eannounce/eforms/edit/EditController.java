/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EditController.java,v $
 * Revision 1.9  2012/04/05 17:36:36  wendy
 * jre142 and win7 changes
 *
 * Revision 1.8  2009/05/22 14:18:51  wendy
 * Performance cleanup
 *
 * Revision 1.7  2009/03/10 21:20:47  wendy
 * attempt to prevent null ptr
 *
 * Revision 1.6  2008/02/20 21:06:56  wendy
 * Added support for excel import - RQ3522
 *
 * Revision 1.5  2008/02/13 21:10:37  wendy
 * Use lockitem from eaccess when possible
 *
 * Revision 1.4  2008/02/13 20:07:53  wendy
 * Get lockowner from eaccess to use entityitem from hashtable
 *
 * Revision 1.3  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/08/01 18:43:35  wendy
 * prevent hang
 *
 * Revision 1.1  2007/04/18 19:44:05  wendy
 * Reorganized JUI module
 *
 * Revision 1.8  2006/11/09 00:31:43  tony
 * added monitor functionality
 *
 * Revision 1.7  2006/03/16 22:01:28  tony
 * Capture logging
 *
 * Revision 1.6  2006/02/16 21:46:47  tony
 * USRO-R-MGRO-6M3L5K
 *
 * Revision 1.5  2005/10/31 17:21:59  tony
 * VEEdit_Iteration2
 * Added create logic.
 *
 * Revision 1.4  2005/10/13 20:11:22  joan
 * adjust for PDG
 *
 * Revision 1.3  2005/09/30 21:41:25  tony
 * USRO-R-PKUR-6GADSV
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:53  tony
 * This is the initial load of OPICM
 *
 * Revision 1.43  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.42  2005/09/01 20:34:53  tony
 * VEEdit
 *
 * Revision 1.41  2005/08/11 20:35:08  tony
 * MN_24995659
 *
 * Revision 1.40  2005/04/07 15:27:45  tony
 * TIR USRO-R-RLON-6B87HE
 *
 * Revision 1.39  2005/03/29 21:54:15  tony
 * rework of display workaround
 *
 * Revision 1.38  2005/03/28 17:56:37  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.37  2005/03/24 15:44:14  tony
 * adjusted flag maintenance functionality based on
 * demo feedback.
 *
 * Revision 1.36  2005/03/09 19:54:43  tony
 * 6542 toolbar update
 *
 * Revision 1.35  2005/03/08 20:33:53  tony
 * added updateFlagCodes logic
 *
 * Revision 1.34  2005/03/07 18:55:51  tony
 * enhancement update the NLSTree on the save so
 * that newly created NLS information shows up
 * as an update instead of as a create.
 *
 * Revision 1.33  2005/03/04 19:48:31  tony
 * hide maintenance function when not available and
 * properly enable maintenance function
 *
 * Revision 1.32  2005/03/04 19:08:24  tony
 * improved cr_FlagUpdate
 *
 * Revision 1.31  2005/03/03 21:46:40  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.30  2005/03/01 19:39:57  tony
 * 6245 added cancel option when nls change.
 *
 * Revision 1.29  2005/03/01 19:30:11  tony
 * 6245 enahnced logic to query about current edit
 * before NLS is toggled.
 *
 * Revision 1.28  2005/02/25 16:47:43  tony
 * 6542 change request wrap-up
 *
 * Revision 1.27  2005/02/24 19:14:08  tony
 * 6542 added default selection capability
 *
 * Revision 1.26  2005/02/23 20:22:01  tony
 * 6542
 *
 * Revision 1.25  2005/02/18 18:01:14  tony
 * cr_6542
 *
 * Revision 1.24  2005/02/18 16:53:24  tony
 * cr_6542
 *
 * Revision 1.23  2005/02/17 22:45:42  tony
 * cr_5254
 *
 * Revision 1.22  2005/02/17 18:55:51  tony
 * cr_6542
 *
 * Revision 1.21  2005/02/04 16:57:42  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.20  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.19  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.18  2005/02/01 22:06:32  tony
 * JTest Second Pass
 *
 * Revision 1.17  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.16  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.15  2005/01/14 19:50:22  tony
 * xpnd_action
 *
 * Revision 1.14  2005/01/05 23:47:16  tony
 * 6554
 * added edit capability to whereused action.
 *
 * Revision 1.13  2004/11/29 18:14:37  tony
 * USRO-R-RTAR-672S9Y updated paste activation logic.
 *
 * Revision 1.12  2004/11/19 18:01:51  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.11  2004/11/09 21:26:33  tony
 * isIndicateRelations()
 *
 * Revision 1.10  2004/11/02 00:31:53  joan
 * fixes for PDG dialog
 *
 * Revision 1.9  2004/08/19 15:31:37  tony
 * xl8r
 *
 * Revision 1.8  2004/08/19 15:12:05  tony
 * xl8r
 *
 * Revision 1.7  2004/08/09 21:22:12  tony
 * improved logging
 *
 * Revision 1.6  2004/08/04 17:49:14  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.5  2004/06/24 20:29:26  tony
 * TIR USRO-R-SWWE-629MHH
 *
 * Revision 1.4  2004/05/26 15:53:35  tony
 * 5ZBTCQ
 *
 * Revision 1.3  2004/03/30 21:34:52  tony
 * CR_0813025214
 *
 * Revision 1.2  2004/02/20 22:17:59  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.116  2004/01/14 18:47:57  tony
 * acl_20040114
 *   1)  updated logic to allow for manual load of serial pref.
 *   2)  trigger a middleware selection if no default middleware is defined.
 *   3)  prevent a put of parent when non new row is selected.
 *
 * Revision 1.115  2003/12/30 20:42:39  tony
 * 53476
 *
 * Revision 1.114  2003/12/22 19:03:54  tony
 * 53451
 *
 * Revision 1.113  2003/12/17 19:14:17  tony
 * acl_20031217
 * updated hidden logic and added comment for previous token
 *
 * Revision 1.112  2003/12/01 19:46:27  tony
 * accessibility
 *
 * Revision 1.111  2003/11/17 23:37:34  tony
 * 53032
 * clean-up
 *
 * Revision 1.110  2003/11/14 17:23:39  tony
 * accessibility
 *
 * Revision 1.109  2003/11/06 18:06:20  tony
 * 52836
 *
 * Revision 1.108  2003/10/30 20:38:58  tony
 * 52721
 *
 * Revision 1.107  2003/10/29 19:10:42  tony
 * acl_20031029
 *
 * Revision 1.106  2003/10/29 17:38:07  tony
 * 52727
 *
 * Revision 1.105  2003/10/29 03:50:46  tony
 * 52623
 *
 * Revision 1.104  2003/10/29 00:19:28  tony
 * removed System.out. statements.
 * REPLAYABLE_LOGFILE
 *
 * Revision 1.103  2003/10/27 23:52:12  tony
 * 52740
 *
 * Revision 1.102  2003/10/20 23:22:53  tony
 * 52642
 *
 * Revision 1.101  2003/10/20 16:37:14  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.100  2003/10/17 18:02:16  tony
 * 52614
 *
 * Revision 1.99  2003/10/14 15:26:41  tony
 * 51832
 *
 * Revision 1.98  2003/10/10 23:17:34  tony
 * 52527
 *
 * Revision 1.97  2003/10/08 20:09:59  tony
 * 52476
 *
 * Revision 1.96  2003/10/03 20:49:09  tony
 * 52459
 *
 * Revision 1.95  2003/09/30 16:35:14  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.94  2003/09/23 23:30:59  tony
 * 52351
 *
 * Revision 1.93  2003/09/23 19:34:11  tony
 * 52022
 *
 * Revision 1.92  2003/09/23 18:27:21  tony
 * 52354
 *
 * Revision 1.91  2003/09/19 18:11:07  tony
 * 52323
 *
 * Revision 1.90  2003/09/12 16:14:27  tony
 * 52189
 *
 * Revision 1.89  2003/09/11 18:09:23  tony
 * acl_20030911
 * updated addRow logic to sort based on a boolean.
 *
 * Revision 1.88  2003/09/11 16:31:03  tony
 * 52163
 *
 * Revision 1.87  2003/09/10 17:58:09  tony
 * 52128
 * added refreshmenu() call on end of add to properly enable
 * the parent selector.
 *
 * Revision 1.86  2003/09/10 15:49:16  tony
 * added in setBusy(false) before return statement to
 * prevent hanging busy state.
 *
 * Revision 1.85  2003/09/04 20:28:57  tony
 * simplified logic.
 *
 * Revision 1.84  2003/08/28 22:55:30  tony
 * memory enhancements
 *
 * Revision 1.83  2003/08/28 18:36:02  tony
 * 51975
 *
 * Revision 1.82  2003/08/26 21:15:46  tony
 * mw_update_20030825
 *
 * Revision 1.81  2003/08/22 21:34:55  tony
 * 51824
 * 51870
 *
 * Revision 1.80  2003/08/21 19:43:10  tony
 * 51391
 *
 * Revision 1.79  2003/08/21 18:10:05  tony
 * 51870
 *
 * Revision 1.78  2003/08/20 19:09:25  tony
 * update peer_create to allow for standalone create to
 * match peer_create.
 *
 * Revision 1.77  2003/08/20 16:43:28  joan
 * 51807
 *
 * Revision 1.76  2003/08/18 23:18:11  tony
 * cr_TBD
 *
 * Revision 1.75  2003/08/18 15:38:47  tony
 * cr_TBD
 *
 * Revision 1.74  2003/08/15 15:37:46  tony
 * cr_0805036452
 * cr_TBD
 *
 * Revision 1.73  2003/07/29 21:33:21  tony
 * cleaned-up code.
 *
 * Revision 1.72  2003/07/29 16:58:30  tony
 * 51555
 *
 * Revision 1.71  2003/07/22 15:43:29  tony
 * 51497
 *
 * Revision 1.70  2003/07/18 18:59:09  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.69  2003/07/11 17:00:16  tony
 * updated logic to perform default functions for
 * the preference panel.
 *
 * Revision 1.68  2003/07/09 15:09:00  tony
 * 51417 -- (did not tag) updated menu creation logic
 * to use the properties file instead of constant values.
 *
 * Revision 1.67  2003/07/03 17:26:56  tony
 * updated logic to improve scripting
 *
 * Revision 1.66  2003/07/03 16:38:04  tony
 * improved scripting logic.
 *
 * Revision 1.65  2003/07/03 00:41:40  tony
 * updated event logging so log parser can directly parse
 * the log file.
 *
 * Revision 1.64  2003/07/02 21:42:30  tony
 * updated logging to allow parsing of log file to
 * auto navigate development to the proper location
 * via the proper path.
 *
 * Revision 1.63  2003/07/02 16:43:24  tony
 * updated logging capabilities to allow for play back of
 * log files.
 *
 * Revision 1.62  2003/06/25 16:18:05  tony
 * 51353
 *
 * Revision 1.61  2003/06/19 18:30:25  tony
 * 51298
 *
 * Revision 1.60  2003/06/19 16:09:42  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.59  2003/06/16 23:29:15  tony
 * 51257
 *
 * Revision 1.58  2003/06/10 16:46:47  tony
 * 51260
 *
 * Revision 1.57  2003/06/09 21:12:32  tony
 * 51199
 *
 * Revision 1.56  2003/06/05 21:43:27  tony
 * only can save defaults if the table is editable.
 *
 * Revision 1.55  2003/06/05 21:33:59  tony
 * 24072
 *
 * Revision 1.54  2003/06/05 17:07:38  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.53  2003/06/02 19:46:14  tony
 * 24072
 *
 * Revision 1.52  2003/06/02 18:29:33  joan
 * 51068
 *
 * Revision 1.51  2003/06/02 16:28:14  joan
 * 51004
 *
 * Revision 1.50  2003/05/30 22:47:26  tony
 * 51017
 *
 * Revision 1.49  2003/05/30 21:09:16  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.48  2003/05/30 16:21:56  joan
 * 51010
 *
 * Revision 1.47  2003/05/29 21:20:46  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.46  2003/05/29 19:05:19  tony
 * updated report launching.
 *
 * Revision 1.45  2003/05/28 18:55:23  tony
 * 50937
 *
 * Revision 1.44  2003/05/28 16:26:25  tony
 * 50940
 *
 * Revision 1.43  2003/05/27 21:21:33  tony
 * updated url launching.
 *
 * Revision 1.42  2003/05/22 21:28:11  tony
 * updated reporting logic.
 *
 * Revision 1.41  2003/05/22 16:23:13  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.40  2003/05/21 17:05:01  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.39  2003/05/20 22:51:49  tony
 * 24286 -- no menu short-cut repitition.
 *
 * Revision 1.38  2003/05/13 22:45:04  tony
 * 50616
 * Switched keys from a string to a pointer to the
 * EANFoundation.
 *
 * Revision 1.37  2003/05/13 16:07:42  tony
 * 50621
 *
 * Revision 1.36  2003/05/09 16:51:28  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.35  2003/05/07 17:31:50  tony
 * 50561
 *
 * Revision 1.34  2003/05/06 20:40:17  joan
 * 50530
 *
 * Revision 1.33  2003/05/05 18:04:56  tony
 * 50515
 *
 * Revision 1.32  2003/05/02 17:51:53  tony
 * 50495 and
 * create at end of assoc.
 *
 * Revision 1.31  2003/05/01 22:41:35  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.30  2003/05/01 17:13:36  tony
 * 50471
 *
 * Revision 1.29  2003/04/29 20:03:57  tony
 * SBRActionItem remove implemented per a middleware
 * incompatibility issue.
 *
 * Revision 1.28  2003/04/22 16:39:10  tony
 * per dave updated logic to improve performance while
 * toggling between vertical and horizontal editors.
 *
 * Revision 1.27  2003/04/18 20:10:29  tony
 * added tab placement to preferences.
 *
 * Revision 1.26  2003/04/18 14:40:44  tony
 * enhanced record toggle logic per KC multiple create
 * did not toggle thru records properly.
 *
 * Revision 1.25  2003/04/16 17:19:08  tony
 * replaced hasPaste with can Paste
 *
 * Revision 1.24  2003/04/15 16:45:01  tony
 * added null pointer catch
 *
 * Revision 1.23  2003/04/11 20:02:28  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.edit;
import com.elogin.*;
//import com.ibm.eannounce.eserver.ChatAction;
import com.ibm.eannounce.eforms.action.ActionController;
//import com.ibm.eannounce.eforms.editor.MetaValidator;
import com.ibm.eannounce.eforms.navigate.*;
//import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.epanels.*;
import com.ibm.eannounce.dialogpanels.EditPDGPanel;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import org.xml.sax.Attributes;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EditController extends EParsePanel implements ActionListener, ETabable, EAccessConstants {
	private static final long serialVersionUID = 1L;
	/**
     * HORZ_EDITOR
     */
    public static final int HORZ_EDITOR = 0;
    /**
     * VERT_EDITOR
     */
    public static final int VERT_EDITOR = 1;
    /**
     * FORM_EDITOR
     */
    public static final int FORM_EDITOR = 2;
    /**
     * MAX_EDITOR
     */
    public static final int MAX_EDITOR = 3;

    /**
     * currentEditorType
     */
    private int currentEditorType = -1;
    /**
     * split
     */
    protected ESplitPane split = new ESplitPane();
    /**
     * pnlMain
     */
    protected EPanel pnlMain = new EPanel(new BorderLayout());
    private EannounceEditor[] edit = new EannounceEditor[MAX_EDITOR];
    private EntityList eList = null;
    private EANActionItem actionItem = null;

    /**
     * navKey
     */
    private String navKey = null;
    /**
     * id
     */
    private int id = -1;
    /**
     * bCommit
     */
 //   private boolean bCommit = false;

    private EMenubar menubar = new EMenubar();

    private String toolbarPosition = "West";
    private boolean floatable = true;
    //private String XML = null;

    //	private PDHParentItem dummy = new PDHParentItem("Dummy","Dummy","Dummy");

    private EntityItem[] parents = null;
    private EFlagBox parSel = null;
    private RowSelectableTable table = null;
    private EntityGroup eg = null;
    private NavActionTree tree = null;
    private NLSActionTree nlsTree = null;	//cr_6542
    private Navigate nav = null; //acl_20021022
    private boolean fireEvent = true;

    private ELabel sesLbl = new ELabel();
    private Profile parentProfile = null;
    private String sGif = null;
    private ETabable parTab = null; //51975
    private Vector vChildTab = new Vector(); //51975

    private EPanel pnlEdit = new EPanel(new BorderLayout()); //52022
    private MetaMaintActionItem maint = null;				//cr_FlagUpdate

    private EComparator parComp = new EComparator(true) {//52128
        public Object getObject(Object _o, int _index) {//52128
            if (_o instanceof EntityItem) { //52128
                return Routines.replace(((EntityItem) _o).toString(), ((EntityItem) _o).getKey() + ": ", ""); //52128
            } //52128
            return _o; //52128
        } //52128
    }; //52128

    /**
     * editController
     * @author Anthony C. Liberto
     */
    public EditController() {
        super(new BorderLayout());
        tree = new NavActionTree(this, 1);
        nlsTree = new NLSActionTree(this) {
        	private static final long serialVersionUID = 1L;
        	public void onClick(Object _node) {
				onNLSTreeClick(_node);
			}
		};
        configureSplit();
    }

    /**
     * editController
     * @param _eList
     * @param _nav
     * @author Anthony C. Liberto
     */
    public EditController(EntityList _eList, Navigate _nav) {
        super(new BorderLayout());
        Long t1 = eaccess().timestamp("editController.construct():00 entered at: ");
        nav = _nav; //acl_20021022
        tree = new NavActionTree(this, 1);
        nlsTree = new NLSActionTree(this) {
        	private static final long serialVersionUID = 1L;
        	public void onClick(Object _node) {
				onNLSTreeClick(_node);
			}
		};
        Dimension d = UIManager.getDimension("eannounce.minimum"); //21764
        tree.setPreferredSize(d); //21764
        init(_eList);
        eaccess().timestamp("editController.construct():05 after init at: ",t1);
        if (_nav != null) {
            setParentKey(_nav.getKey());
            setOPWGID(_nav.getOPWGID());
        }

        if (table.getRowCount() == 1) {
            if (isFormCapable()) {
                createEditor(FORM_EDITOR, (String) null);
                eaccess().timestamp("editController.construct():13 after create form at: ",t1);
                createMenu();
                setEnabled("next", false); //51017
                setEnabled("prev", false); //51017
                menubar.doClick("form");
                eaccess().timestamp("editController.construct():15 done createform: ",t1);
            } else {
                createEditor(VERT_EDITOR, (String) null);
                createMenu();
                setEnabled("next", false); //51017
                setEnabled("prev", false); //51017

                menubar.doClick("vert");
                eaccess().timestamp("editController.construct():19 after createvert at: ",t1);
            }
        } else {
            createEditor(HORZ_EDITOR, (String) null);
            eaccess().timestamp("editController.construct():22 after createhorz at: ",t1);
            EannounceEditor ed = getCurrentEditor();
            if (ed != null) {
                ed.sort(true);
                eaccess().timestamp("editController.construct():25 after sort at: ",t1);
            }
            createMenu();
            menubar.doClick("horz");
            eaccess().timestamp("editController.construct():28 done createhorz at: ",t1);
        }
        configureSplit();
        setSelectorEnabled(false);
        refreshMenu();
        eaccess().timestamp("editController.construct():32 ended at: ",t1);
    }

    /**
     * updateTabPlacement
     *
     * @param _revalidate
     * @author Anthony C. Liberto
     */
    public void updateTabPlacement(boolean _revalidate) {
    }

    /**
     * setGif
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setGif(String _s) {
        if (_s != null) {
            sGif = new String(_s);
        } else {
            sGif = null;
        }
    }

    /**
     * getGif
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getGif() {
        return sGif;
    }

    /**
     * setSessionTagText
     * @param _s
     * @author Anthony C. Liberto
     */
    private void setSessionTagText(String _s) {
        String msg = null;
        if (_s != null) {
            setCode("sesLbl");
            setParm(_s);
            msg = getMessage();
            sesLbl.setText(msg);
            sesLbl.setToolTipText(msg);
        }
        sesLbl.setVisible(_s != null);
    }

    /**
     * loadFromFile
     * @param _list
     * @return
     * @author Anthony C. Liberto
     */
    public static ETabable loadFromFile(EntityList _list) {
        return new EditController(_list, null);
    }

    /**
     * build
     * @param _sai
     * @author Anthony C. Liberto
     */
    public void build(SearchActionItem _sai) {
        //mw_update_20030825		table = _sai.getDynaSearchTable();
        if (table != null) { //52740
            gc(); //52740
        } //52740
        table = _sai.getDynaSearchTable(eaccess().getRemoteDatabaseInterface()); //mw_update_20030825
        pnlMain.add("Center", createEditor(VERT_EDITOR, _sai));
    }

    private EannounceEditor createEditor(int _type, SearchActionItem _sai) {
        if (!isValidDynaEditor(_type)) {
            return null;
        }
        if (edit[_type] == null) {
            switch (_type) {
            case VERT_EDITOR :
                edit[_type] = new VertEditor(this, _sai);
                break;
            case FORM_EDITOR :
                edit[_type] = new FormEditor(this, _sai);
                break;
            default :
            	break;
            }
        }
        currentEditorType = _type;
        return edit[_type];
    }

    private boolean isValidDynaEditor(int _i) {
        return (_i == VERT_EDITOR || _i == FORM_EDITOR);
    }

    /**
     * configureSplit
     * @author Anthony C. Liberto
     */
    private void configureSplit() {
        split.setOneTouchExpandable(true);
        split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        if (eaccess().isExpandAction()) { //xpnd_action
            split.setDividerLocation(tree.getPreferredWidth()); //xpnd_action
        } else { //xpnd_action
            split.setDividerLocation(0); //xpnd_action
        } //xpnd_action

        pnlMain.add("Center", split);
        add("Center", pnlMain);
        split.setLastDividerLocation(tree.getPreferredWidth());
        split.setLeftComponent(getTree());
        split.setRightComponent(pnlEdit); //52022
    }

    private void toggleSplit() {
        int divLoc = split.getDividerLocation();
        if (divLoc < 10) {
            split.setDividerLocation(tree.getPreferredWidth());
        } else {
            split.setDividerLocation(0);
        }
        repaint();
    }

    private void init(EntityList _eList) {
        eList = _eList;
        add("North", sesLbl);
        setSessionTagText(eList.getTagDisplay());
        actionItem = _eList.getParentActionItem();
        setTable(_eList);
        if (table.canCreate()) {
            buildParentSelector(_eList.getParentEntityGroup());
        }
        dereferenceEdit(false);
    }

    private void setTable(EntityList _eList) {
        RowSelectableTable rst = null;
        EANActionItem[] ean = null;
        if (_eList.isParentDisplayable()) {												//VEEdit
			EntityGroup teg = _eList.getParentEntityGroup();							//VEEdit
			table = teg.getEntityGroupTable();											//VEEdit
			eg = teg;																	//VEEdit
			rst = teg.getActionGroupTable(); 											//VEEdit
			ean = eaccess().getExecutableActionItems(teg, rst);              			//VEEdit
			retrieveMetaMaintAction(ean);												//VEEdit
			tree.load(ean, eaccess().getActionTitle(teg, rst)); 						//VEEdit
		} else {																		//VEEdit
	        int ii = _eList.getEntityGroupCount();
			for (int i = 0; i < ii; ++i) {
				EntityGroup teg = _eList.getEntityGroup(i);
				if (teg.isDisplayable()) {
					table = teg.getEntityGroupTable();
					eg = teg;
					//51298				tree.load(teg);
					rst = teg.getActionGroupTable(); //rstUpdate
					ean = eaccess().getExecutableActionItems(teg, rst);			         //cr_FlagUpdate
					retrieveMetaMaintAction(ean);												//cr_FlagUpdate
					tree.load(ean, eaccess().getActionTitle(teg, rst)); //51298
					break;
				}
	        }
		}
    }

    /**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public RowSelectableTable getTable() {
        return table;
    }

    /**
     * getTree
     * @return
     * @author Anthony C. Liberto
     */
    public NavActionTree getTree() {
        return tree;
    }

    private void buildParentSelector(EntityGroup _group) {
        if (_group == null) {
            parSel = new EFlagBox();
            parSel.setFont(this.getFont());
            parSel.insertItemAt("No Parent is Available", 0);
            parSel.setSelectedIndex(0); //51870
        } else {
            if (!_group.hasData()) {
                return;
            }
            parents = _group.getEntityItemsAsArray();

            Arrays.sort(parents, parComp); //52128

            parSel = new EFlagBox(parents);
            parSel.setFont(this.getFont());
            parSel.setMaxWidth(25);
            parSel.setActionCommand("parSel");
            parSel.addActionListener(this);
            if (getParentCount() > 1) {
                parSel.insertItemAt("Please Select Parent Item", 0);
            }
            if (getParentCount() > 0) {
                parSel.setSelectedIndex(0);
            }
            parSel.setToolTipText(parSel.getSelectedItem().toString());
        }
        //		parSel.setBorder(null);
        pnlMain.add("South", parSel);
    }

    /**
     * getTableTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTableTitle() {
        return table.toString();
    }
    /**
     * getEntityGroup
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getEntityGroup() {
        return eg;
    }

    /**
     * getProfile
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getProfile() {
        if (eList != null) { //51010
            return eList.getProfile();
        }

        return getActiveProfile(); //51010
    }

    private boolean dereferenceEdit(boolean _unlock) {
        if (currentEditorType != -1) {
            EannounceEditor ed = getCurrentEditor();
            //LockGroup lg = null;
            if (ed == null) {
                return true;
            }
            if (ed.okToClose(false)) {
            	/*wss i moved this.. validate was running after this
                for (int i = 0; i < MAX_EDITOR; ++i) {
                    if (edit[i] != null) {
                        remove((JComponent) edit[i]);
                        edit[i].dereference();
                        edit[i] = null;
                    }
                }*/
                if (_unlock) {
                    LockList ll = getLockList();
                    if (ll != null) {
                    	EntityItem lockOwnerEI = eaccess().getLockOwner();
                    	if (lockOwnerEI!=null){
                    		table.unlock(getRemoteDatabaseInterface(), null, ll, getActiveProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);
                    		if (eaccess().isMonitor()) {
                    			eaccess().monitor("unlock", ll);
                    		}
                    	}
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * gc
     * @author Anthony C. Liberto
     */
    public void gc() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
        	pnlMain.remove((JComponent) ed);
            ed.cancel();
            ed.updateModel(null);
            ed.dereference();
            ed = null;
            edit[VERT_EDITOR] = null;
            table = null;
        }
    }

    /**
     * close
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean close() {
        return dereferenceEdit(true);
    }

    //52614	public void select() {
    /**
     * select
     *
     * @param _bCloseAll
     * @author Anthony C. Liberto
     */
    public void select(boolean _bCloseAll) { //52614
        EannounceEditor ed = null;
        if (menubar != null) {
        	eaccess().setEMenuBar(menubar);
            revalidate();
        }
        eaccess().setFilter(hasFiltered());
        eaccess().setHidden(hasHiddenAttributes()); //52476
        eaccess().setActiveProfile(eList.getProfile());
        eaccess().setMenuEnabled(SYSTEM_MENU, "resetDate", false); //50495
        //52703		appendLog("<tabSelect tab=\"" + getPanelType() + "\" parentkey=\"" + getParentKey() + "\" opwgid=\"" + getOPWGID() + "\"/>");
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //52703
            try {
                appendLog("<tabSelect tab=\"" + getPanelType() + "\" parentkey=\"" + new String(getParentKey()) + "\" opwgid=\"" + getOPWGID() + "\"/>"); //52703
            } catch (Exception _x) {
                EAccess.report(_x,false);
            }
        } //52703
        requestFocus(); //50471
        ed = getCurrentEditor(); //52189
        if (ed != null) { //52189
            ed.select(); //52189
        } //52189
    }

    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() { //50471
        EannounceEditor ed = getCurrentEditor(); //50471
        if (ed != null) { //50471
            ed.requestFocus(); //50471
        } //50471
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (split != null) {
            split.dereference();
            split = null;
        }

        if (pnlMain != null) {
            pnlMain.dereference();
            pnlMain = null;
        }
        //wss i moved this here so done after validate() 
        if (edit!= null){
        	for (int i = 0; i < MAX_EDITOR; ++i) {
        		if (edit[i] != null) {
        			remove((JComponent) edit[i]);
        			edit[i].dereference();
        			edit[i] = null;
        		}
        	}
            
            edit = null;
        }

        if (eList != null) {
            eList.dereference(); 
            eList = null;
        }
        navKey = null;
        //toolbarPosition = null;
        table = null;
        eg = null;

        if (sesLbl != null) {
            sesLbl.dereference();
            sesLbl = null;
        }
        parentProfile = null;
        if (menubar != null) {
            closeMenu();
            Container cnt = menubar.getParent();
            if (cnt != null) {
                cnt.remove(menubar);
            }
            menubar.dereference();
            menubar = null;
        }
        if (parSel != null) {
            parSel.removeActionListener(this);
            parSel.removeAll();
            parSel.removeNotify();
            parSel = null;
        }
        if (pnlEdit != null) { //52022
            pnlEdit.dereference();
            pnlEdit = null; //52022
        } //52022

        parComp = null; //52128
        parents = null;
        sGif = null;

        nav = null;
        actionItem = null;
       // toolbarPosition = null;
        //XML = null;
        if (tree != null) {
            tree.dereference();
            tree = null;
        }
        if (nlsTree != null) {
			nlsTree.dereference();
			nlsTree = null;
		}
        
        if (vChildTab!= null){
        	while (!vChildTab.isEmpty()) {
        		ETabable tab = (ETabable) vChildTab.remove(0);
        		tab.setParentTab(getParentTab());
        	}
        	vChildTab = null;
        }
        if (parTab != null) {
            parTab.removeChildTab(this);
            parTab = null;
        }
        
        //these are called when the tab is removed ->JTabbedPane.removeTabAt(int)
        //removeAll();
        //super.dereference();
    }

    /**
     * getParentCount
     * @return
     * @author Anthony C. Liberto
     */
    private int getParentCount() {
        if (parents != null) {
            return parents.length;
        }
        return 0;
    }

    private EannounceEditor createEditor(int _type, String _key) {
        if (!isValidEditor(_type)) {
            return null;
        }
        if (edit[_type] == null) {
            switch (_type) {
            case HORZ_EDITOR :
                edit[_type] = new HorzEditor(eList, this);
                break;
            case VERT_EDITOR :
                edit[_type] = new VertEditor(eList, _key, this, new RecordToggle());
                break;
            case FORM_EDITOR :
                edit[_type] = new FormEditor(_key, this, new RecordToggle());
                break;
            default :
            	break;
            }
        }
        return edit[_type];
    }

    private boolean validEditor() {
      return (currentEditorType < MAX_EDITOR && currentEditorType >= 0); 
    }

    private void setEditor(int _type) {
        String curRecKey = null;
        String curSelKey = null;
        EannounceEditor curEdit = null;
        EannounceEditor newCurEdit = null;
        if (validEditor()) {
            if (!saveCurrentEdit()) {
                return;
            }
        }
        if (currentEditorType == _type) {
            return;
        }
        curEdit = getCurrentEditor();
        if (currentEditorType >= 0) {
            stopEditing();
            curRecKey = curEdit.getRecordKey();
            curSelKey = curEdit.getSelectionKey();
        }

        addEditor(_type, curRecKey);

        newCurEdit = getCurrentEditor();
        newCurEdit.synchronize();
        newCurEdit.setSelection(curRecKey, curSelKey);

        eaccess().setFilter(newCurEdit.hasFiltered());
        eaccess().setHidden(newCurEdit.hasHiddenAttributes());
        if (_type != FORM_EDITOR) {
            newCurEdit.prepareToEdit();
        }
        if (newCurEdit != null) {									//cr_6542_tbar
	        boolean bAllNLS = isAllNLS();				//cr_6542_tbar
            newCurEdit.setEnabled("toglTreeView",bAllNLS);	//cr_6542_tbar
	        newCurEdit.setVisible("toglTreeView",bAllNLS);	//cr_6542_tbar
		}													//cr_6542_tbar

        refreshUpdate();
    }

    /**
     * refreshUpdate
     * @author Anthony C. Liberto
     */
    public void refreshUpdate() {
//USRO-R-MGRO-6M3L5K        if (table.isDynaTable()) {
        if (table == null || table.isDynaTable()) {		//USRO-R-MGRO-6M3L5K
        } else {
            boolean changed = hasChanges();
            boolean masterChanged = hasMasterChanges();
//            boolean isGrid = (currentEditorType == HORZ_EDITOR);
            boolean isPast = isPast();
            boolean bCreate = table.canCreate();
            boolean bDefault = canEditDefault() && isNew(); //50937
            setEnabled("saveR", changed);
            setEnabled("saveA", masterChanged);
            setEnabled("rstA", changed);
            setEnabled("rstR", changed);
            setEnabled("rstS", changed);
            setEnabled("dup", bCreate && !isPast);
            setEnabled("crte", bCreate && !isPast);
            setEnabled("xl8r", bCreate && !isPast);//RQ3522
            setEnabled("dsave", bDefault); //50937
            setEnabled("dcncl", bDefault); //50937
            reloadNLSTree();
        }
        eaccess().validate(); //53032
    }

    /**
     * refreshMenu
     * @author Anthony C. Liberto
     */
    private void refreshMenu() {
        boolean bHasData = hasData();
        EANActionItem[] eai = tree.getActionItemArray(ACTION_PURPOSE_MATRIX);
        EannounceEditor ed = null;
        //52527		eannounceToolbar tbar = getEToolbar();

        adjustSubAction("mtrx", eai, bHasData, ACTION_ALL); //52527
        eai = tree.getActionItemArray(ACTION_PURPOSE_WORK_FLOW); //52527
        adjustSubAction("wFlow", eai, bHasData, ACTION_ALL); //52527

        ed = getCurrentEditor();
        refreshMaint(ed);
        if (ed != null) {
            boolean bNew = ed.isNew();
            boolean bDefault = canEditDefault() && bNew; //51257
            setEnabled("remove", bNew);
            setSelectorEnabled(bNew);
            setSelectedParent(ed.getSelectedObject());
            setEnabled("dsave", bDefault); //51257
            setEnabled("dcncl", bDefault); //51257
        }
    }

    /**
     * hasData
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasData() {
        RowSelectableTable rst = getTable(); //52527
        if (rst != null) { //52527
            return (rst.getRowCount() > 0 && rst.getColumnCount() > 0); //52527
        } //52527
        return false; //52527
    }

    private void refreshMenu(int _type) {
        boolean horzType = (_type == HORZ_EDITOR);
        boolean vertType = (_type == VERT_EDITOR);
        boolean formType = (_type == FORM_EDITOR);

        boolean bCreate = (table==null?false:table.canCreate());
        boolean bEdit = isEditable();
        boolean bFill = (horzType || vertType);
        setEnabled("crte", bCreate);
        setEnabled("xl8r", bCreate); // RQ3522
        setEnabled("lock", bEdit);
        setEnabled("ulck", bEdit);
        setEnabled("dup", bCreate);

        setEnabled("left", horzType);
        setEnabled("right", horzType);

        setEnabled("srt", horzType || vertType);
        setEnabled("fltr", horzType || vertType);
        setEnabled("selA", horzType || vertType);
        setEnabled("iSel", horzType || vertType);
        setEnabled("horz", !horzType);
        setEnabled("vert", !vertType);
        //		setEnabled("xl8r", !formType);			//xl8r
        setEnabled("form", !formType && isFormCapable());
        setEnabled("frze", horzType);
        setEnabled("thaw", horzType);
        setEnabled("rfrsh", formType);
        setEnabled("spll", bEdit); //5ZBTCQ
        if (horzType) {
            setEnabled("prev", false);
            setEnabled("next", false);
        }

        setVisible("Fillmenu", bFill);
        setEnabled("Fillmenu", bFill);
        refreshMenu();
    }

    /**
     * canPaste
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canPaste() {
        return eaccess().canPaste(false) && isEditable();
    }

    /**
     * setEnabled
     * @param _key
     * @param _enabled
     * @author Anthony C. Liberto
     */
    public void setEnabled(String _key, boolean _enabled) {
        menubar.setEnabled(_key, _enabled);
        for (int i = 0; i < MAX_EDITOR; ++i) {
            EannounceEditor ed = getEditor(i);
            if (ed != null) {
                ed.setEnabled(_key, _enabled);
            }
        }
    }

    /**
     * setVisible
     * @param _key
     * @param _visible
     * @author Anthony C. Liberto
     */
    protected void setVisible(String _key, boolean _visible) {
        menubar.setVisible(_key, _visible);
        for (int i = 0; i < MAX_EDITOR; ++i) {
            EannounceEditor ed = getEditor(i);
            if (ed != null) {
                ed.setVisible(_key, _visible);
            }
        }
    }

    private void addEditor(int _type, String _key) {
        EannounceEditor ed = createEditor(_type, _key);
        refreshMenu(_type); //52459
        //52022		split.setRightComponent((JComponent)ed);
        pnlEdit.removeAll(); //52022
        pnlEdit.add("Center", (JComponent) ed); //52022
        pnlEdit.revalidate(); //52022
        pnlEdit.repaint(); //52022
        ed.requestFocus();
        currentEditorType = _type;
        toggleMaint();
        //		repaint();
        //		revalidate();
    }
    
    private boolean isValidEditor(int _i) {
        if (_i < 0 || _i >= MAX_EDITOR) {
            return false;
        }
        return true;
    }

    /**
     * getEditor
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    private EannounceEditor getEditor(int _i) {
        if (isValidEditor(_i)) {
            return edit[_i];
        }
        return null;
    }

    /**
     * rollback
     * @author Anthony C. Liberto
     */
    public void rollback() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.rollback();
            ed.removeNewRows();
        }
        refreshUpdate();
    }

    /**
     * rollbackRow
     * @author Anthony C. Liberto
     */
    private void rollbackRow() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.rollbackRow();
        }
        refreshUpdate();
    }

    /**
     * rollbackSingle
     * @author Anthony C. Liberto
     */
    private void rollbackSingle() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.rollbackSingle();
        }
        refreshUpdate();
    }

    /**
     * commit
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean commit() {
        if (!saveCurrentEdit()) {
            return false;
        }
        return commitNoHors();
    }

    /**
     * commitNoHors
     * @return
     * @author Anthony C. Liberto
     */
    private boolean commitNoHors() {
        EannounceEditor ed = getCurrentEditor();
        boolean b = false;
        RowSelectableTable rst = null;
        if (ed != null) {
            b = ed.commit();
        }

        rst = getTable(); //51807
        rst.refresh(); //51807

        refreshUpdate();
        if (b) {
            setCommit(true);
        }
        return b;
    }

    /**
     * historyInfo
     * @author Anthony C. Liberto
     */
    private void historyInfo() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.historyInfo();
        }
    }

    /**
     * commitDefault
     * @return
     * @author Anthony C. Liberto
     */
    private boolean commitDefault() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.commitDefault();
        }
        return false;
    }

    /**
     * cancelDefault
     * @author Anthony C. Liberto
     */
    private void cancelDefault() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.cancelDefault();
        }
    }

    /**
     * canEditDefault
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canEditDefault() {
        Profile prof = getProfile();
        if (prof != null) {
            //24072			return (prof.hasRoleFunction(prof.ROLE_FUNCTION_DEFAULT_VALUES) && table.canCreate());
            return (prof.hasRoleFunction(Profile.ROLE_FUNCTION_DEFAULT_VALUES)); //24072
        }
        return false;
    }

    private HorzEditor getHorizontalEditor() {
        if (edit[HORZ_EDITOR] != null) {
            return (HorzEditor) edit[HORZ_EDITOR];
        }
        return null;
    }

    /**
     * saveCurrentEdit
     * @return
     * @author Anthony C. Liberto
     */
    private boolean saveCurrentEdit() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.saveCurrentEdit();
        }
        return false;
    }

    /**
     * commitAll
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean commitAll() {
        if (!saveCurrentEdit()) {
            return false;
        }
        return commitAllNoHors();
    }

    /**
     * commitAllNoHors
     * @return
     * @author Anthony C. Liberto
     */
    private boolean commitAllNoHors() {
        try {
            RowSelectableTable rst = getTable();
            rst.commit(getRemoteDatabaseInterface());
            rst.refresh();
            refreshUpdate();
            setCommit(true);
            return true;
        } catch (MiddlewareBusinessRuleException _mbre) {
            _mbre.printStackTrace();
            //51260			setMessage(_mbre.toString());
            //51260			showError();
            showException(_mbre, ERROR_MESSAGE, OK); //51260
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
            moveToError(_bre);
            //51260			setMessage(_bre.toString());
            //51260			showError();
            showException(_bre, ERROR_MESSAGE, OK); //51260
        } catch (Exception _re) {
            _re.printStackTrace();
            //51260			setMessage(_re.toString());
            //51260			showError();
            showException(_re, ERROR_MESSAGE, OK); //51260
        }
        return false;
    }

    /**
     * moveToError
     * @param _ebre
     * @author Anthony C. Liberto
     */
    public void moveToError(EANBusinessRuleException _ebre) {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.moveToError(_ebre);
        }
    }

    /**
     * moveToErrorPDG
     * @param _ebre
     * @author Anthony C. Liberto
     */
    public void moveToErrorPDG(EANBusinessRuleException _ebre) { //51068
        EannounceEditor ed = getCurrentEditor();
        if (ed != null && (ed instanceof VertEditor)) {
            VertEditor ve = (VertEditor) ed;
            ve.moveToErrorPDG(_ebre);
        }
    }

    //acl_20030911	public void addRow() {
    /**
     * addRow
     * @param _sort
     * @author Anthony C. Liberto
     */
    public void addRow(boolean _sort) { //acl_20030911
        EannounceEditor ed = null;
        eaccess().getNow(); //51391
        getActiveProfile().setNow(System.getProperty("mw.now")); //51391
        ed = getCurrentEditor();
        if (ed != null) {
            ed.addRow(_sort); //acl_20030911
            //acl_20030911			ed.addRow();
        }
        refreshUpdate();
        refreshMenu(); //acl_20030910
    }

    /**
     * getCurrentEditor
     * @return
     * @author Anthony C. Liberto
     */
    public EannounceEditor getCurrentEditor() {
        if (currentEditorType == -1) {
            return null;
        } else {//if (edit[currentEditorType] == null) {
        	return edit[currentEditorType];
        }
    }

    /**
     * refresh
     * @author Anthony C. Liberto
     */
    public void refresh() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.refresh();
        }
    }

    /**
     * getRecordCount
     * @return
     * @author Anthony C. Liberto
     * /
    public int getRecordCount() {
        return 0;
    }

    /**
     * getMetaCount
     * @return
     * @author Anthony C. Liberto
     * /
    public int getMetaCount() {
        return 0;
        //nfw		return group.getPDHMetaAttributeCount();
    }

    /**
     * hasChanges
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasChanges() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.hasChanges();
        }
        return false;
    }

    /**
     * hasMasterChanges
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean hasMasterChanges() {
        RowSelectableTable rst = getTable();
        if (rst != null) {
            return rst.hasChanges();
        }
        return false;
    }

    /**
     * getEntityItemCount
     * @return
     * @author Anthony C. Liberto
     */
    protected int getEntityItemCount() {
        RowSelectableTable rst = getTable();
        if (rst != null) {
            return rst.getRowCount();
        }
        return -1;
    }

    /**
     * isEditable
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isEditable() {
        //51298		return table.canEdit();
        return eaccess().isEditable(table); //51298
    }

    /**
     * isCreatable
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isCreatable() {
        return table.canCreate();
    }

    /**
     * isMatrixable
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isMatrixable() {
        return false;
        //nfw		return group.isMatrixable();
    }

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    private void createMenu() {
        Profile prof = null;
        createFileMenu();
        createEditMenu();
        createViewMenu();
        prof = getProfile(); //51199
        if (prof != null) { //51199
            if (!prof.hasRoleFunction(Profile.ROLE_FUNCTION_READONLY)) { //51199
                createFillMenu(); //51199
            } //51199
        } //51199
        createUpdateMenu();
        createTableMenu();
    }

    /**
     * createFileMenu
     * @author Anthony C. Liberto
     */
    private void createFileMenu() {
        String strKey = getString("file");
        menubar.addMenu(strKey, "clsT", this, KeyEvent.VK_W, Event.CTRL_MASK, true);
        menubar.addMenu(strKey, "clsA", this, KeyEvent.VK_W, Event.CTRL_MASK + Event.SHIFT_MASK, true);
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "saveR", this, KeyEvent.VK_S, Event.CTRL_MASK, false);
        if (canEditDefault()) { //50937
            menubar.addMenu(strKey, "dsave", this, KeyEvent.VK_S, Event.CTRL_MASK + Event.ALT_MASK, isNew());
        } //50937
        menubar.addMenu(strKey, "saveA", this, KeyEvent.VK_S, Event.CTRL_MASK + Event.SHIFT_MASK, false);
        if (EAccess.isTestMode()) { //51497
            menubar.addMenu(strKey, "saveT", this, 0, 0, true);
        } //51497
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "print", this, KeyEvent.VK_P, Event.CTRL_MASK, true);
        menubar.addMenu(strKey, "pageSetup", this, 0, 0, true);
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "exit", this, KeyEvent.VK_F4, Event.ALT_MASK, true);
        menubar.setMenuMnemonic(strKey, getChar("file-s"));
    }

    /**
     * createFillMenu
     * @author Anthony C. Liberto
     */
    private void createFillMenu() {
        String strKey = getString("fill");
        //cr_0805036452		menubar.addMenu(strKey, "fcopy", this, KeyEvent.VK_F9, Event.SHIFT_MASK,true);
        //cr_0805036452		menubar.addMenu(strKey, "fcopyr", this, KeyEvent.VK_F10, Event.SHIFT_MASK,true);
        //cr_0805036452		menubar.addMenu(strKey, "fapnd", this, KeyEvent.VK_F11, Event.SHIFT_MASK,true);
        //cr_0805036452		menubar.addMenu(strKey, "fpste", this, KeyEvent.VK_F12, Event.SHIFT_MASK,true);
        menubar.addMenu(strKey, "fcopy", this, KeyEvent.VK_F9, 0, true); //cr_0805036452
        if (EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            menubar.addMenu(strKey, "fcopyr", this, KeyEvent.VK_F9, Event.SHIFT_MASK, true); //access
        } else {
            menubar.addMenu(strKey, "fcopyr", this, KeyEvent.VK_F10, 0, true); //cr_0805036452
        }
        menubar.addMenu(strKey, "fapnd", this, KeyEvent.VK_F11, 0, true); //cr_0805036452
        menubar.addMenu(strKey, "fpste", this, KeyEvent.VK_F12, 0, true); //cr_0805036452
        menubar.setMenuMnemonic(strKey, getChar("fill-s"));
    }

    /**
     * createEditMenu
     * @author Anthony C. Liberto
     */
    private void createEditMenu() {
        String strKey = getString("edit");
        menubar.addMenu(strKey, "cut", this, KeyEvent.VK_X, Event.CTRL_MASK, isEditable());
        //52642		menubar.addMenu(strKey,"copy", this, KeyEvent.VK_C, Event.CTRL_MASK, isEditable());
        menubar.addMenu(strKey, "copy", this, KeyEvent.VK_C, Event.CTRL_MASK, true); //52642
        //52163		menubar.addMenu(strKey,"pste", this, KeyEvent.VK_V, Event.CTRL_MASK, canPaste());
        menubar.addMenu(strKey, "pste", this, KeyEvent.VK_V, Event.CTRL_MASK, true); //52163
        setEnabled("pste", canPaste()); //52163
        menubar.addMenu(strKey, "dup", this, KeyEvent.VK_INSERT, Event.CTRL_MASK, isEditable());
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "rstA", this, KeyEvent.VK_Z, Event.CTRL_MASK + Event.SHIFT_MASK, false);
        menubar.addMenu(strKey, "rstR", this, KeyEvent.VK_Z, Event.CTRL_MASK + Event.ALT_MASK, false);
        menubar.addMenu(strKey, "rstS", this, KeyEvent.VK_Z, Event.CTRL_MASK, false);
        if (canEditDefault()) {
            menubar.addMenu(strKey, "dcncl", this, 0, 0, isNew());
        }
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "spll", this, 0, 0, isEditable());
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true);
        menubar.setMenuMnemonic(strKey, getChar("edit-s"));
    }

    /**
     * createUpdateMenu
     * @author Anthony C. Liberto
     */
    private void createUpdateMenu() {
        String strKey = getString("updt");
        menubar.addMenu(strKey, "dActAtt", this, KeyEvent.VK_DELETE, 0, isEditable());
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "lock", this, KeyEvent.VK_L, Event.CTRL_MASK, isEditable());
        menubar.addMenu(strKey, "ulck", this, KeyEvent.VK_U, Event.CTRL_MASK, isEditable());
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "crte", this, KeyEvent.VK_N, Event.CTRL_MASK, isCreatable());
        if (isMaintenanceable()) {																			//cr_FlagUpdate
	        menubar.addMenu(strKey,"maint",this,KeyEvent.VK_M,Event.CTRL_MASK,true);						//cr_FlagUpdate
		}																									//cr_FlagUpdate
        //if (EAccess.isArmed(XL8R_ARM_FILE) && isCreatable()) { //xl8r
        if (isCreatable()) { // allow import from xls file without arm file RQ3522
            menubar.addMenu(strKey, "xl8r", this, KeyEvent.VK_8, Event.CTRL_MASK, true); //xl8r
        } //xl8r
        menubar.addMenu(strKey, "remove", this, 0, 0, false);
        menubar.setMenuMnemonic(strKey, getChar("updt-s"));
    }

    /**
     * createTableMenu
     * @author Anthony C. Liberto
     */
    private void createTableMenu() {
        String strKey = getString("tbl");
        //cr_0805036452		menubar.addMenu(strKey,"left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK + Event.SHIFT_MASK,true);
        menubar.addMenu(strKey, "left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK, true); //cr_0805036452
        //cr_0805036452		menubar.addMenu(strKey,"right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK + Event.SHIFT_MASK,true);
        menubar.addMenu(strKey, "right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK, true); //cr_0805036452
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "frze", this, 0, 0, true);
        menubar.addMenu(strKey, "thaw", this, 0, 0, true);
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "hide", this, 0, 0, true); //51832
        menubar.addMenu(strKey, "unhide", this, 0, 0, true); //51832
        menubar.addSeparator(strKey); //51832
        menubar.addMenu(strKey, "selA", this, KeyEvent.VK_A, Event.CTRL_MASK, true);
        menubar.addMenu(strKey, "iSel", this, KeyEvent.VK_I, Event.CTRL_MASK, true);
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "srt", this, 0, 0, true);
        menubar.addSeparator(strKey);
        //cr_0805036452		menubar.addMenu(strKey,"fltr",this, KeyEvent.VK_F8, Event.CTRL_MASK, true);
        menubar.addMenu(strKey, "fltr", this, KeyEvent.VK_F8, 0, true); //cr_0805036452
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "histI", this, KeyEvent.VK_F11, Event.CTRL_MASK, true);
        menubar.addMenu(strKey, "eData", this, KeyEvent.VK_F12, Event.CTRL_MASK, true);
        if (EAccess.isCaptureMode()) {
			menubar.addSeparator(strKey);
			menubar.addMenu(strKey, "capture", this, KeyEvent.VK_F5, Event.CTRL_MASK + Event.SHIFT_MASK, true);
		}
        menubar.setMenuMnemonic(strKey, getChar("tbl-s"));
    }

    /**
     * createViewMenu
     * @author Anthony C. Liberto
     */
    private void createViewMenu() {
        String strKey = getString("view");
        menubar.addMenu(strKey, "horz", this, KeyEvent.VK_1, Event.CTRL_MASK, true);
        menubar.addMenu(strKey, "vert", this, KeyEvent.VK_2, Event.CTRL_MASK, true);
        menubar.addMenu(strKey, "form", this, KeyEvent.VK_3, Event.CTRL_MASK, isFormCapable());
        if (actionExists(ACTION_PURPOSE_MATRIX)) {
            menubar.addSubMenu(strKey, "mtrx", this, 0, 0);
        }
        if (actionExists(ACTION_PURPOSE_WORK_FLOW)) { //52351
            menubar.addSubMenu(strKey, "wFlow", this, 0, 0); //52351
        }
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "rfrsh", this, 0, 0, true);
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "prev", this, KeyEvent.VK_P, Event.CTRL_MASK + Event.SHIFT_MASK, true);
        menubar.addMenu(strKey, "next", this, KeyEvent.VK_N, Event.CTRL_MASK + Event.SHIFT_MASK, true);
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "toglAct", this, KeyEvent.VK_T, Event.CTRL_MASK, true);
        if (isAllNLS()) {																							//cr_6542
	    	menubar.addMenu(strKey,"toglTreeView", this, KeyEvent.VK_T, Event.CTRL_MASK + Event.SHIFT_MASK,true);	//cr_6542
		}																											//cr_6542
        menubar.setMenuMnemonic(strKey, getChar("view-s"));
    }

    /**
     * closeMenu
     * @author Anthony C. Liberto
     */
    private void closeMenu() {
        menubar.removeMenuItem("clsT", this);
        menubar.removeMenuItem("clsA", this);
        menubar.removeMenuItem("exit", this);
        menubar.removeMenuItem("fcopy", this);
        menubar.removeMenuItem("fcopyr", this);
        menubar.removeMenuItem("fapnd", this);
        menubar.removeMenuItem("fpste", this);
        menubar.removeMenuItem("cut", this);
        menubar.removeMenuItem("copy", this);
        menubar.removeMenuItem("pste", this);
        menubar.removeMenuItem("dup", this);
        if (canEditDefault()) {
            menubar.removeMenuItem("dsave", this);
            menubar.removeMenuItem("dcncl", this);
        }
        menubar.removeMenuItem("spll", this);
        menubar.removeMenuItem("saveR", this);
        menubar.removeMenuItem("saveA", this);
        if (EAccess.isTestMode()) { //51497
            menubar.removeMenuItem("saveT", this);
        } //51497
        menubar.removeMenuItem("rstA", this);
        menubar.removeMenuItem("rstR", this);
        menubar.removeMenuItem("rstS", this);
        menubar.removeMenuItem("dActAtt", this);
        menubar.removeMenuItem("lock", this);
        menubar.removeMenuItem("ulck", this);
        menubar.removeMenuItem("crte", this);
        menubar.removeMenuItem("maint",this);	//cr_FlagUpdate
        menubar.removeMenuItem("xl8r", this); //xl8r
        menubar.removeMenuItem("left", this);
        menubar.removeMenuItem("right", this);
        menubar.removeMenuItem("selA", this);
        menubar.removeMenuItem("iSel", this);
        menubar.removeMenuItem("srt", this);
        menubar.removeMenuItem("f/r", this);
        menubar.removeMenuItem("fltr", this);
        menubar.removeMenuItem("histI", this);
        menubar.removeMenuItem("eData", this);
        menubar.removeMenuItem("capture",this);

        menubar.removeMenuItem("horz", this);
        menubar.removeMenuItem("vert", this);
        menubar.removeMenuItem("form", this);
        menubar.removeMenuItem("mtrx", this);
        menubar.removeMenuItem("wFlow", this); //52351
        menubar.removeMenuItem("print", this);
        menubar.removeMenuItem("pageSetup", this);
        menubar.removeMenuItem("frze", this);
        menubar.removeMenuItem("thaw", this);
        menubar.removeMenuItem("hide", this); //51832
        menubar.removeMenuItem("unhide", this); //51832
        menubar.removeMenuItem("remove", this);
        menubar.removeMenuItem("rfrsh", this);
        menubar.removeMenuItem("prev", this);
        menubar.removeMenuItem("next", this);
        menubar.removeMenuItem("toglAct", this);
        menubar.removeMenuItem("toglTreeView",this);		//cr_6542

        menubar.removeMenu(getString("file"));
        menubar.removeMenu(getString("fill"));
        menubar.removeMenu(getString("tbl"));
        menubar.removeMenu(getString("updt"));
        menubar.removeMenu(getString("view"));
        menubar.removeMenu(getString("edit"));
    }

    /*
     * matrix stuff
     */
    /**
     * actionExists
     * @param _sAction
     * @return
     * @author Anthony C. Liberto
     */
    private boolean actionExists(String _sAction) {
        return tree.actionExists(_sAction);
    }

    private void loadAction(Object _o, int _navType) {
        if (_o instanceof SubAction) {
            //51353			performAction(tree.getActionItem(((subAction)_o).getKey()), _navType);
            performAction(tree.getActionItem(((SubAction) _o).getKey()), _navType, false); //51353
        }
    }

    /**
     * performAction
     * @param _ai
     * @param _navType
     * @author Anthony C. Liberto
     */
    public void performAction(EANActionItem _ai, int _navType) { //51353
        performAction(_ai, _navType, true); //51353
    } //51353

    /**
     * performAction
     * @param _ai
     * @param _navType
     * @param _checkBusy
     * @author Anthony C. Liberto
     */
    private void performAction(EANActionItem _ai, int _navType, boolean _checkBusy) {
        //51353		if (isBusy()) return;
        if (_checkBusy && isBusy()) { //51353
            return; //51353
        } //51353
        setBusy(true);
        if (_ai instanceof MatrixActionItem) {
            MatrixActionItem mai = (MatrixActionItem) _ai;
            EannounceEditor tmpEdit = getCurrentEditor();
            EntityItem[] ei = tmpEdit.getEntityItems();
            MatrixList mList = null;
            if (ei != null) {
                if (eaccess().editContains(ei, _ai)) {
                    setBusy(false);
                    return;
                }
                mList = dBase().rexec(mai, ei, this);
                if (mList != null) {
                    ActionController ac = new ActionController(mList, getParentKey());
                    ac.setParentProfile(getProfile());
                    ac.setOPWGID(getOPWGID());
                    ac.setEntityItemArray(ei);
                    //51975					addTab("Matrix -- " + getTableTitle(), "Matrix", "mtrx.gif", ac, "Matrix -- " + getTableTitle());
                    //kehrli_20030929					addTab(this,"Matrix -- " + getTableTitle(), "Matrix", "mtrx.gif", ac, "Matrix -- " + getTableTitle());		//51975
                    addTab(this, ac); //kehrli_20030929
                    ac.requestFocus(-1);
                    setBusy(false);
                    return;
                }
            }
            setCode("msg11014.1");
            setParm(getString("mtrx"));
            eaccess().showFYI(this);
        } else if (_ai instanceof WorkflowActionItem) { //52351
            EannounceEditor tmpEdit = getCurrentEditor(); //52351
            if (tmpEdit != null) { //52351
                processWorkflow(tmpEdit, tmpEdit.getEntityItems(), (WorkflowActionItem) _ai); //52351
            } //52351
        } //52351

        setBusy(false);
    }

    private boolean mustPerform(String _action) {
        if (_action.equalsIgnoreCase("exit")) {
        	eaccess().exit("exit editcontroller");
            return true;
        } else if (_action.equalsIgnoreCase("vert")) {
            setEditor(VERT_EDITOR);
            return true;
        } else if (_action.equalsIgnoreCase("horz")) {
            setEditor(HORZ_EDITOR);
            return true;
        } else if (_action.equalsIgnoreCase("form")) {
            setEditor(FORM_EDITOR);
            return true;
        } else if (_action.equalsIgnoreCase("histI")) {
            historyInfo();
        } else if (_action.equalsIgnoreCase("eData")) {
            EannounceEditor ed = getCurrentEditor();
            if (ed != null) {
                ed.showInformation();
            }
        } else if (_action.equals("capture")) {
			if (eList != null) {
				eaccess().capture(eList.dump(false));
			}
		}
        return false;
    }

    private void mightPerform(String _action, ActionEvent _ae) {
        EannounceEditor ed = getCurrentEditor();
        if (ed == null) {
            return;
        } else if (_action.equalsIgnoreCase("clsT")) {
        	eaccess().close(this);
            return;
        } else if (_action.equalsIgnoreCase("clsA")) {
        	eaccess().closeAll();
            return;
        } else if (_action.equalsIgnoreCase("rstA")) {
            rollback();
        } else if (_action.equalsIgnoreCase("rstR")) {
            rollbackRow();
            refreshUpdate();
        } else if (_action.equalsIgnoreCase("rstS")) {
            rollbackSingle();
            refreshUpdate();
        } else if (_action.equalsIgnoreCase("dActAtt")) {
            ed.deactivateAttribute();
        } else if (_action.equalsIgnoreCase("saveR")) {
            commit();
            refreshMenu();
            refreshUpdate();
        } else if (_action.equals("saveT")) {
        	eaccess().save(eList);
        } else if (_action.equalsIgnoreCase("dsave")) {
            commitDefault();
        } else if (_action.equalsIgnoreCase("dcncl")) {
            cancelDefault();
        } else if (_action.equalsIgnoreCase("saveA")) {
            commitAll();
            refreshMenu();
            refreshUpdate();
            repaint(); //50940
        } else if (_action.equalsIgnoreCase("crte")) {
            //acl_20030911			addRow();
            addRow(false); //acl_20030911
		} else if (_action.equalsIgnoreCase("maint")) {		//cr_FlagUpdate
			processMetaMaint();								//cr_FlagUpdate
        } else if (_action.equalsIgnoreCase("xl8r")) { //xl8r
            ed.importTable(); //xl8r
            refreshUpdate(); //xl8r
        } else if (_action.equalsIgnoreCase("spll")) {
            ed.spellCheck();
        } else if (_action.equalsIgnoreCase("cut")) {
            ed.cut();
            //USRO-R-RTAR-672S9Y			setEnabled("pste", canPaste());
            setEnabled("pste", canPaste() && ed.isPasteable()); //USRO-R-RTAR-672S9Y
        } else if (_action.equalsIgnoreCase("copy")) {
            ed.copy();
            //USRO-R-RTAR-672S9Y			setEnabled("pste", canPaste());
            setEnabled("pste", canPaste() && ed.isPasteable()); //USRO-R-RTAR-672S9Y
        } else if (_action.equalsIgnoreCase("pste")) {
            ed.paste();
        } else if (_action.equalsIgnoreCase("dup")) {
            ed.duplicate();
            refreshUpdate();
        } else if (_action.equalsIgnoreCase("left")) {
            ed.moveColumn(true);
        } else if (_action.equalsIgnoreCase("right")) {
            ed.moveColumn(false);
        } else if (_action.equalsIgnoreCase("selA")) {
            ed.selectAll();
        } else if (_action.equalsIgnoreCase("iSel")) {
            ed.invertSelection();
        } else if (_action.equalsIgnoreCase("f/r")) {
            ed.find();
        } else if (_action.equalsIgnoreCase("fltr")) {
            ed.filter();
        } else if (_action.equalsIgnoreCase("sEff")) {
            ed.showEffectivity();
        } else if (_action.equalsIgnoreCase("fcopy")) {
            ed.fillClear();
            ed.fillCopy(false);
        } else if (_action.equalsIgnoreCase("fcopyr")) {
            ed.fillCopy(true);
        } else if (_action.equalsIgnoreCase("fapnd")) {
            ed.fillCopy(false);
        } else if (_action.equalsIgnoreCase("fpste")) {
            ed.fillPaste();
        } else if (_action.equalsIgnoreCase("lock")) {
            ed.lock();
        } else if (_action.equalsIgnoreCase("ulck")) {
            ed.unlock();
        } else if (_action.equalsIgnoreCase("mtrx")) {
            loadAction(_ae.getSource(), 0);
        } else if (_action.equalsIgnoreCase("wFlow")) { //52351
            loadAction(_ae.getSource(), 0); //52351
        } else if (_action.equalsIgnoreCase("parSel")) {
            EntityItem ei = getSelectedParent();
            if (ei != null) {
                if (ed.isNew()) { //acl_20040114
                    ed.setParentItem(ei);
                } //acl_20040114
            }
        } else if (_action.equalsIgnoreCase("srt")) {
            ed.showSort();
        } else if (_action.equalsIgnoreCase("print")) {
            ed.print();
        } else if (_action.equals("pageSetup")) {
            eaccess().pageSetup();
        } else if (_action.equalsIgnoreCase("remove")) {
            HorzEditor horz = null;
            ed.removeExtra();
            horz = getHorizontalEditor();
            if (horz != null && ed != horz) {
                horz.refreshRemove();
            }
            ed.freezeRefresh();										//USRO-R-PKUR-6GADSV
        } else if (_action.equalsIgnoreCase("frze")) {
            ed.freeze();
        } else if (_action.equalsIgnoreCase("thaw")) {
            ed.thaw();
        } else if (_action.equalsIgnoreCase("hide")) { //51832
            showHide(true); //51832
        } else if (_action.equalsIgnoreCase("unhide")) { //51832
            showHide(false); //51832
        } else if (_action.equalsIgnoreCase("toglAct")) {
            toggleSplit();
		} else if (_action.equalsIgnoreCase("toglTreeView")) {		//cr_6542
			toggleTreeView();										//cr_6542
        } else if (_action.equals("prev")) {
            ed.increment(-1);
        } else if (_action.equals("next")) {
            ed.increment(1);
        } else if (_action.equalsIgnoreCase("rfrsh")) {
            if (ed instanceof FormEditor) {
                ((FormEditor) ed).formRefresh();
                repaint();
                revalidate();
            }
        }
        setBusy(false);
    }

    /**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @param _ae
     */
    public void actionPerformed(final ActionEvent _ae) {
        final ESwingWorker myWorker = new ESwingWorker() {//51824
            public Object construct() {//51824
            	try{
					String command = _ae.getActionCommand();
					if (!fireEvent) {
						return null;
					}
					appendLog("editController action is: " + command);
					if (isBusy()) {
						appendLog("    I am busy");
						if (mustPerform(command)) {
							appendLog("        mustPerform");
						}
						return null;
					}
					setBusy(true); //50940
					if (!mustPerform(command)) {
						mightPerform(command, _ae);
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in EditController.actionPerformed.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
				}

                setBusy(false);
                return null;
            } //51824
            public boolean isInterruptable() { //51824
                return false; //51824
            } //51824
            public void finished() {
            } //51824
        }; //51824
        myWorker.start(); //51824
    }

    /**
     * stopEditing
     * @author Anthony C. Liberto
     */
    public void stopEditing() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.stopEditing();
        }
    }

    /**
     * getFormName
     * @return
     * @author Anthony C. Liberto
     */
    protected String getFormName() {
        if (actionItem != null) {
            if (actionItem instanceof EditActionItem) {
                return ((EditActionItem) actionItem).getFormKey() + ".html";
            } else if (actionItem instanceof CreateActionItem) {
                return ((CreateActionItem) actionItem).getFormKey() + ".html";
            }
        }
        return null;
    }

    /**
     * isFormCapable
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isFormCapable() {
        if (actionItem != null) {
            if (actionItem instanceof EditActionItem) {
                if (((EditActionItem) actionItem).isFormCapable()) {
                    return eaccess().xmlExists(getFormName());
                }
            } else if (actionItem instanceof CreateActionItem) {
                if (((CreateActionItem) actionItem).isFormCapable()) {
                    return eaccess().xmlExists(getFormName());
                }
            }
        }
        return false;
    }

    /*
     * Tabable
     */
    /**
     * setParentKey
     *
     * @author Anthony C. Liberto
     * @param _navKey
     */
    public void setParentKey(String _navKey) {
        navKey = _navKey;
    }

    /**
     * getParentKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getParentKey() {
        return navKey;
    }

    /**
     * setOPWGID
     *
     * @param _id
     * @author Anthony C. Liberto
     */
    public void setOPWGID(int _id) {
        id = _id;
    }

    /**
     * getOPWGID
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getOPWGID() {
        return id;
    }

    /**
     * setCommit
     * @param _b
     * @author Anthony C. Liberto
     */
    private void setCommit(boolean _b) {
        if (_b) {
            setShouldRefresh(getParentKey(), getOPWGID(), KEY_TYPE);
        }
    }

    /**
     * setShouldRefresh
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _eType
     * @param _opwg
     */
    public void setShouldRefresh(String _eType, int _opwg, int _code) {
        if (nav != null) {
            if (isRefreshableAction()) {
                nav.setShouldRefresh(_eType, _opwg, _code);
            }
        } else { //53476
            if (parTab != null) { //53476
                if (parTab instanceof ActionController) { //53476
                    ((ActionController) parTab).setShouldRefresh(true); //6554
                } //53476
            } //53476
        }
    }

    private boolean isRefreshableAction() {
        if (eList != null) {
            EANActionItem action = eList.getParentActionItem();
            if (action instanceof CreateActionItem) {
                return (((CreateActionItem) action).isPeerCreate() || isStandAlone((CreateActionItem) action)); //52836
            }
        }
        return true;
    }

    /**
     * setShouldRefresh
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setShouldRefresh(boolean _b) {
    }

    /**
     * shouldRefresh
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean shouldRefresh() {
        return false;
    }

    /**
     * getEntityType
     *
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public String getEntityType(int _i) {
        if (eg != null) {
            if (_i == 0) { //52727
                return eg.getEntityType(); //52727
            } else if (_i == 1) {
                return eg.getEntity1Type();
            } else if (_i == 2) {
                return eg.getEntity2Type();
            }
        }
        return null;
    }

    /**
     * refresh
     *
     * @param _breakable
     * @author Anthony C. Liberto
     */
    public void refresh(boolean _breakable) {
        NLSItem nlsView = eList.getNLSItem();
        NLSItem nlsCurrent = eaccess().getActiveNLSItem();
        EntityList el = null;
        EannounceEditor ed = null;
        if (nlsView.getNLSID() == nlsCurrent.getNLSID()) {
            eList.getProfile().setReadLanguage(nlsCurrent);
            repaint();
        } else {
            EANFoundation[] ean = table.getTableRowsAsArray();
            EntityItem[] eia = new EntityItem[ean.length];
            for (int i = 0; i < ean.length; i++) {
                if (ean[i] instanceof EntityItem) {
                    eia[i] = (EntityItem) ean[i];
                }
            }
            if (actionItem instanceof EditActionItem) {
                el = dBase().getEntityList(actionItem, eia, this);
            } else if (actionItem instanceof CreateActionItem) {
                el = dBase().getEntityList(actionItem, eia, this);
            }

            eList = el;
            setTable(el);
            ed = getCurrentEditor();
            if (ed != null) {
                ed.updateModel(table);
            }
        }
    }

    /**
     * getHelpText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getHelpText() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.getHelpText();
        }
        return getString("nia");
    }

    /*
     * relator selector
     */
    /**
     * setSelectedParent
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setSelectedParent(Object _o) {
        if (parSel == null) {
            return;
        }
        fireEvent = false;
        if (_o != null && _o instanceof EntityItem) {
            EntityItem ei = (EntityItem) _o;
			EntityGroup tmpEG = ei.getEntityGroup();		//MN_24995659
			EntityList el = tmpEG.getEntityList();			//MN_24995659
            EANEntity ean = null;
            loadNLSTree(ei);							//cr_6245
            if (el.isCreateParent()) {					//MN_24995659
				if (ei.hasDownLinks()) {				//MN_24995659
					ean = ei.getDownLink(0);			//MN_24995659
				}										//MN_24995659
			} else if (ei.hasUpLinks()) {
                ean = ei.getUpLink(0);
            }
            if (ean != null) {
                EntityItem parent = (EntityItem) ean;
                if (parent.getEntityID() < 0) {
                    parSel.setSelectedIndex(0);
                } else {
                    //53451					parSel.setSelectedItem(parent);
                    parSel.setSelectedParent(parent); //53451
                }
            } else {
                parSel.setSelectedIndex(0);
            }
            parSel.setToolTipText(parSel.getSelectedItem().toString());
        }
        fireEvent = true;
    }

    /**
     * getSelectedParent
     * @return
     * @author Anthony C. Liberto
     */
    private EntityItem getSelectedParent() {
        Object o = null;
        if (parSel == null) {
            return null;
        }
        o = parSel.getSelectedItem();
        if (o instanceof EntityItem) {
            return (EntityItem) o;
        }
        return null;
    }

    /**
     * setSelectorEnabled
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSelectorEnabled(boolean _b) {
        if (parSel != null) {
            parSel.setEnabled(_b);
        }
        if (isEditable()) {
            setEnabled("lock", !_b);
            setEnabled("ulck", !_b);
        }
    }

    /**
     * hasFiltered
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasFiltered() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.hasFiltered();
        }
        return false;
    }

    /**
     * hasHiddenAttributes
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasHiddenAttributes() { //52476
        EannounceEditor ed = getCurrentEditor(); //52476
        if (ed != null) { //52476
            return ed.hasHiddenAttributes(); //52476
        } //52476
        return false; //52476
    } //52476

    /**
     * revalidateForm
     * @param _att
     * @author Anthony C. Liberto
     */
    public void revalidateForm(EANAttribute _att) {
        EannounceEditor ed = null;
        if (!fireEvent) {
            return;
        }
        fireEvent = false;
        ed = getCurrentEditor();
        if (ed instanceof FormEditor) {
            ((FormEditor) ed).revalidateForm(_att);
        }
        fireEvent = true;
    }

    /**
     * contains
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _eai
     * @param _ei
     */
    public boolean contains(EntityItem[] _ei, EANActionItem _eai) {
        return eList.equivalent(_ei, _eai);
    }

    /**
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSearchableObject() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.getSearchableObject();
        }
        return null;
    }

    /**
     * canContinue
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canContinue() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.canContinue();
        }
        return false;
    }

    /**
     * repaintImmediately
     *
     * @author Anthony C. Liberto
     */
    public void repaintImmediately() {
        update(getGraphics());
    }

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        for (int i = 0; i < MAX_EDITOR; ++i) {
            if (edit[i] != null) {
                edit[i].refreshAppearance();
            }
        }
    }

    /**
     * buildPDGEdit
     * @param _rst
     * @param _pdgai
     * @author Anthony C. Liberto
     */
    public void buildPDGEdit(RowSelectableTable _rst, PDGActionItem _pdgai, EditPDGPanel _pdgPanel) {
        table = _rst;

        createEditor(VERT_EDITOR, _pdgai, _pdgPanel);
        setEditor(VERT_EDITOR);

    }

    private EannounceEditor createEditor(int _type, PDGActionItem _pdgai, EditPDGPanel _pdgPanel) {
        edit[_type] = new VertEditor(this, _pdgai, _pdgPanel);
        return edit[_type];
    }

    /**
     * dump
     * @param _sb
     * @param _brief
     * @return
     * @author Anthony C. Liberto
     */
    protected StringBuffer dump(StringBuffer _sb, boolean _brief) {
        _sb.append("editController..." + RETURN);
        if (table != null) {
            _sb.append("    table.title  : " + table.getTableTitle() + RETURN);
            _sb.append("    table.rows   : " + table.getRowCount() + RETURN);
            _sb.append("    table.columns: " + table.getColumnCount() + RETURN);
        }
        if (eList != null) {
            _sb.append("    entityList:  " + eList.dump(_brief) + RETURN);
        }
        if (actionItem != null) {
            _sb.append("    actionItem:  " + actionItem.dump(_brief) + RETURN);
        }
        if (eg != null) {
            _sb.append("    entityGroup: " + eg.dump(_brief) + RETURN);
        }
        return _sb;
    }

    /**
     * setParentProfile
     *
     * @author Anthony C. Liberto
     * @param _prof
     */
    public void setParentProfile(Profile _prof) {
        parentProfile = _prof;
    }

    /**
     * getParentProfile
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getParentProfile() {
        return parentProfile;
    }

    /*
     51257
     */
    /**
     * isNew
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isNew() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.isNew();
        }
        return false;
    }

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_EDITCONTROLLER;
    }

    /**
     * getVisiblePanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getVisiblePanelType() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.getPanelType();
        }
        return getPanelType();
    }

    /**
     * process
     *
     * @author Anthony C. Liberto
     * @param _action
     * @param _child
     * @param _method
     * @param _parent
     */
    public void process(String _method, String _action, String[] _parent, String[] _child) {
        if (tree != null) {
            EANActionItem ean = tree.getActionItem(_action);
            if (ean != null) {
                EannounceEditor ed = getCurrentEditor();
                if (ed != null) {
                    ed.highlight(_parent);
                    performAction(ean, 0);
                }
            }
        }
    }

    /*
     acl_20030718
     */
    /**
     * selectKeys
     *
     * @author Anthony C. Liberto
     * @param _keys
     */
    public void selectKeys(String[] _keys) {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.selectKeys(_keys);
        }
    }

    /*
     51975
     */

    /**
     * setParentTab
     *
     * @author Anthony C. Liberto
     * @param _tab
     */
    public void setParentTab(ETabable _tab) {
        parTab = _tab;
        if (parTab != null) {
            parTab.addChildTab(this);
        }
    }

    /**
     * getParentTab
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ETabable getParentTab() {
        return parTab;
    }

    /**
     * addChildTab
     *
     * @author Anthony C. Liberto
     * @param _tab
     */
    public void addChildTab(ETabable _tab) {
        vChildTab.add(_tab);
    }

    /**
     * removeChildTab
     *
     * @author Anthony C. Liberto
     * @return eTabable
     * @param _tab
     */
    public ETabable removeChildTab(ETabable _tab) {
        if (vChildTab.remove(_tab)) {
            return _tab;
        }
        return null;
    }

    /*
     52189
     */
    /**
     * deselect
     *
     * @author Anthony C. Liberto
     */
    public void deselect() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.deselect();
        }
    }
    /*
     52323
     */
    /**
     * freezeRefresh
     * @author Anthony C. Liberto
     */
    public void freezeRefresh() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.freezeRefresh();
        }
    }
    /*
     52354
     */
    /**
     * updateRecordLabel
     * @param _i
     * @author Anthony C. Liberto
     */
    public void updateRecordLabel(int _i) {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.updateRecordLabel(_i);
        }
    }
    /**
     * getTabTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabTitle() {
        String name = null;
        setCode("tab.title");
        setParmCount(2);
        setParm(0, getTableTitle());
        setParm(1, getProfile().toString());
        name = getMessage();
        eaccess().clear();
        return name;
    }

    /**
     * getTabMenuTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabMenuTitle() {
        String name = null;
        setCode("tab.title.menu");
        setParmCount(3);
        if (isCreate()) {
            setParm(0, getString("create.title"));
        } else {
            setParm(0, getString("edit.title"));
        }
        setParm(1, getTableTitle());
        setParm(2, getProfile().toString());
        name = getMessage();
        eaccess().clear();
        return name;
    }

    /**
     * getTabToolTipText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabToolTipText() {
        return getTabMenuTitle();
    }

    /**
     * getTabIcon
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Icon getTabIcon() {
        if (isCreate()) {
            return getImageIcon(getString("create.icon"));
        }
        return getImageIcon(getString("edit.icon"));
    }

    /**
     * isCreate
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isCreate() {
        if (eList != null) {
            EANActionItem act = eList.getParentActionItem();
            return (act instanceof CreateActionItem);
        }
        return false;
    }

    /*
     52527
     */
    /**
     * adjustSubAction
     * @param _key
     * @param _eai
     * @param _hasData
     * @param _type
     * @author Anthony C. Liberto
     */
    private void adjustSubAction(String _key, EANActionItem[] _eai, boolean _hasData, int _type) {
        menubar.adjustSubAction(_key, _eai, _hasData, _type);
        for (int i = 0; i < MAX_EDITOR; ++i) {
            EannounceEditor ed = getEditor(i);
            if (ed != null) {
                ed.adjustSubAction(_key, _eai, _hasData, _type);
            }
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
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.showHide(_b);
            eaccess().setHidden(ed.hasHiddenAttributes()); //acl_20031217
        }
    }

    /*
     52623
     */
    /**
     * processWorkflow
     * @param _edit
     * @param _ei
     * @param _action
     * @author Anthony C. Liberto
     */
    private void processWorkflow(EannounceEditor _edit, EntityItem[] _ei, WorkflowActionItem _action) {
        if (_ei != null && _action != null) {
            if (containsNew(_ei)) {
                eaccess().showError(this, "msg3007Flow");
            } else {
                dBase().rexec(_action, _ei, this);
            }
        }
    }

    private boolean containsNew(EntityItem[] _ei) {
        int ii = _ei.length;
        for (int i = 0; i < ii; ++i) {
            if (_ei[i].isNew()) {
                return true;
            }
        }
        return false;
    }

    /*
     52836
     */
    private boolean isStandAlone(CreateActionItem _action) {
        if (_action.isStandAlone()) {
            String eType = _action.getStandAloneEntityType();
            if (eType != null && nav != null) {
                String navType = nav.getEntityType(2);
                if (navType != null) {
                    return eType.equals(navType);
                }
            }
        }
        return false;
    }

    /*
     accessibility
     */
    /**
     * getDisplayableTableComponent
     * @return
     * @author Anthony C. Liberto
     */
    public Component getDisplayableTableComponent() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            Object o = ed.getSearchableObject();
            if (o != null && o instanceof Component) {
                return (Component) o;
            }
        }
        return null;
    }

    /*
     cr_0813025224
     */
    /**
     * getSelectedKeys
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getSelectedKeys() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.getSelectedKeys();
        }
        return null;
    }
    /*
     TIR USRO-R-SWWE-629MHH

     */
    /**
     * isIndicateRelations
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isIndicateRelations() {
        if (actionItem != null) {
            if (actionItem instanceof EditActionItem) {
                return ((EditActionItem) actionItem).isEditRelatorOnly();
            }
        }
        return false;
    }

    /**
     * refreshTable
     * @param _rst
     * @author Anthony C. Liberto
     */
    public void refreshTable(RowSelectableTable _rst) {
        EannounceEditor ed = null;
        table = _rst;
        ed = getCurrentEditor();
        if (ed != null) {
            ed.updateModel(table);
        }
    }
/*
 cr_6542
 */

    /**
     * loadNLSTree
     * @author Anthony C. Liberto
     * @param _ei
     */
    protected void loadNLSTree(EntityItem _ei) {
		if (isAllNLS()) {
			Profile prof = getProfile();
			nlsTree.load(_ei,prof);
			nlsTree.setSelection(prof.getReadLanguage());
		}
	}

	/**
     * reloadNLSTree
     *
     * @author Anthony C. Liberto
     */
    private void reloadNLSTree() {
		if (isAllNLS()) {
			Profile prof = getProfile();
			nlsTree.reload(prof);
			nlsTree.setSelection(prof.getReadLanguage());
		}
	}

	private void toggleTreeView() {
		Component visComponent = split.getLeftComponent();
//		int iLoc = split.getDividerLocation();
		if (visComponent == nlsTree) {
			split.setLeftComponent(tree);
			tree.revalidate();
	        split.setDividerLocation(0);							//cr_6542_tbar
		} else if (visComponent == tree) {
			split.setLeftComponent(nlsTree);
			nlsTree.revalidate();
	        split.setDividerLocation(nlsTree.getPreferredWidth());	//cr_6542_tbar
		}
	}

	private void onNLSTreeClick(Object _node) {
		if (_node != null && _node instanceof NLSNode) {
			NLSNode node = (NLSNode)_node;
			NLSItem nls = node.getNLSItem();
			if (nls != null) {
				EannounceEditor ed = getCurrentEditor();
				if (ed != null) {
					if (ed.isEditing()) {
						int reply = eaccess().showConfirm(this, YES_NO_CANCEL, "msg3020.0", true);
						if (reply == YES) {
							if (ed.commitEdit()) {
								toggleNLS(nls);
							}
						} else if (reply == NO) {
							ed.cancelEdit();
							toggleNLS(nls);
						} else {
							Profile prof = getProfile();
							if (prof != null) {
								nlsTree.setSelection(prof.getReadLanguage());
							}
						}
					} else {
						toggleNLS(nls);
					}
				}
			}
		}
	}

	private void toggleNLS(NLSItem _nls) {
		eaccess().setNLS(_nls);
		nlsRefresh();
	}

	private void nlsRefresh() {
	    EannounceEditor ed = getCurrentEditor();
		if (ed != null) {
			ed.nlsRefresh();
		}
	}

	private boolean isAllNLS() {
		if (eList != null) {
			EANActionItem ean = eList.getParentActionItem();
			if (ean != null) {
				return ean.isAllNLS();
			}
		}
		return false;
	}

/*
 cr_FlagUpdate
 */
	/**
     * processMetaMaint
     *
     * @author Anthony C. Liberto
     */
    private void processMetaMaint() {
		EannounceEditor ed = getCurrentEditor();
		if (ed != null && maint != null) {
			ed.processMaintAction();
		}
	}

	private void toggleMaint() {
		EannounceEditor ed = getCurrentEditor();
		if (ed != null) {
			if (ed instanceof VertEditor) {
				toggleMaint(ed.getEANMetaAttribute(0,1));
			} else {
				toggleMaint(ed.getEANMetaAttribute(0,0));
			}
			ed.setVisible("maint",isMaintenanceable());
		}
	}

	private void refreshMaint(EannounceEditor _ed) {
		if (isMaintenanceable()) {
			if (_ed != null) {
				toggleMaint(_ed.getSelectedEANMetaAttribute());
			} else {
				toggleMaint(null);
			}
		} else {
			toggleMaint(null);
		}
	}

	/**
     * toggleMaint
     *
     * @author Anthony C. Liberto
     * @param _att
     */
    public void toggleMaint(EANMetaAttribute _att) {
		setEnabled("maint",canMaintenance(_att));
	}

	/**
     * canMaintenance
     *
     * @author Anthony C. Liberto
     * @param _att
     * @return
     */
    private boolean canMaintenance(EANMetaAttribute _att) {
		if (maint != null) {
			return maint.canMaintenance(_att);
		}
		return false;
	}

	/**
	 * isMaintenanceable
	 *
	 * @author Anthony C. Liberto
	 * @return boolean
	 */
	private boolean isMaintenanceable() {
		Profile prof = getProfile();												//demo_update
		boolean bHasFunction = false;												//demo_update
		if (prof != null) {															//demo_update
			bHasFunction = prof.hasRoleFunction(Profile.ROLE_FUNCTION_ADDFLAG);		//demo_update
		}																			//demo_update
		return maint != null && bHasFunction;										//demo_update
//demo_update		return maint != null;
	}

	private void retrieveMetaMaintAction(EANActionItem[] _ean) {
		if (_ean != null) {
            int ii = _ean.length;
            maint = null;
			for (int i=0;i<ii;++i) {
				if (_ean[i] instanceof MetaMaintActionItem) {
					maint = (MetaMaintActionItem)_ean[i];
					return;
				}
			}
		}
	}

    /**
     * processMaintAction
     *
     * @author Anthony C. Liberto
     * @param _meta
     */
    protected void processMaintAction(EANMetaAttribute _meta) {
		if (maint != null) {
			MetaFlagMaintList maintList = dBase().rexec(maint,_meta,this);
			eaccess().show(this,eList,maintList);
		}
	}

    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    public void refreshToolbar() {
		EannounceEditor ed = getCurrentEditor();
		if (ed != null) {
			ed.refreshToolbar();
//TIR USRO-R-RLON-6B87HE			refreshMenu();
			refreshMenu(getEditorType(ed));		//TIR USRO-R-RLON-6B87HE
			refreshUpdate();
		}
	}

	/*
	TIR USRO-R-RLON-6B87HE
	*/
	private int getEditorType(EannounceEditor _ed) {
		if (_ed != null) {
		    if (_ed instanceof HorzEditor) {
		    	return HORZ_EDITOR;
			} else if (_ed instanceof VertEditor) {
			    return VERT_EDITOR;
			} else if (_ed instanceof FormEditor) {
                return FORM_EDITOR;
			}
		}
		return -1;
	}

	/**
	 * is this a VEEdit
	 * VEEdit_Iteration2
	 *
	 * @return boo
	 * @author tony
	 */
	protected boolean isVEEdit() {
		if (eList != null) {
			return eList.isVEEdit();
		}
		return false;
	}
// old or unused methods
    /*
    52351
   52623
   	protected void processWorkflow(editor _edit, EntityItem[] _ei, WorkflowActionItem _action) {
   		if (_ei != null && _action != null) {
   				dBase().rexec(_action,_ei,this);
   		}
   		return;
   	}
   */
    /**
     * getNLSTree
     * @return
     * @author Anthony C. Liberto
     * /
    public NLSActionTree getNLSTree() {
        return nlsTree;
    }*/

    /**
     * getChatAction
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public ChatAction getChatAction() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.getChatAction();
        }
        return null;
    }*/
	   /**
     * getValidator
     * @return
     * @author Anthony C. Liberto
     * /
    public MetaValidator getValidator() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            return ed.getValidator();
        }
        return null;
    }*/

    /**
     * cancel
     * @author Anthony C. Liberto
     * /
    public void cancel() {
        EannounceEditor ed = getCurrentEditor();
        if (ed != null) {
            ed.cancel();
        }
    }*/

    /*
    	action processing.
     */
/*
    private boolean validEntityItems(EntityItem[] _ei) {
        if (_ei == null) {
            return false;
        } else if (_ei.length == 0) {
            return false;
        }
        return true;
    }
*/
    /**
     * processLinkAction
     *
     * @author Anthony C. Liberto
     * @param _link
     * @param _ei
     * /
    protected void processLinkAction(LinkActionItem _link, EntityItem[] _ei) {
    }

    /**
     * processReportAction
     *
     * @author Anthony C. Liberto
     * @param _report
     * @param _ei
     * /
    protected void processReportAction(ReportActionItem _report, EntityItem[] _ei) {
        launchReport(_report, _ei);
    }

    /**
     * processWorkflowAction
     *
     * @author Anthony C. Liberto
     * @param _ai
     * @param _ei
     * /
    protected void processWorkflowAction(WorkflowActionItem _ai, EntityItem[] _ei) {
        if (_ei == null || _ei.length == 0) {
            showError("msg2009");
            return;
        }
        dBase().rexec(_ai, _ei, this);
    }

    /**
     * processDeleteAction
     *
     * @author Anthony C. Liberto
     * @param _ai
     * @param _ei
     * /
    protected void processDeleteAction(DeleteActionItem _ai, EntityItem[] _ei) {
        if (_ei == null || _ei.length == 0) {
            showError("msg2009");
            return;
        }
        dBase().rexec(_ai, _ei, this);
    }

    /**
     * processLockAction
     *
     * @author Anthony C. Liberto
     * @param _ai
     * @param _ei
     * /
    protected void processLockAction(LockActionItem _ai, EntityItem[] _ei) {
        if (_ei == null || _ei.length == 0) {
            showError("msg2009");
            return;
        }
        dBase().rexec(_ai, _ei, this);
    }

    /**
     * processExtractAction
     *
     * @author Anthony C. Liberto
     * @param _ai
     * @param _ei
     * /
    protected void processExtractAction(ExtractActionItem _ai, EntityItem[] _ei) {
        if (validEntityItems(_ei)) {
            EntityList tmpList = dBase().getEntityList(_ai, _ei, this);
            save(tmpList);
        } else {
            showError("msg2009");
        }
    }*/

    /*
     * form stuff - never seems to be used
     */
    
    private void processToolbar(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processToolbar(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    private void processToolbar(String _att, String _val) {
        if (_att.equalsIgnoreCase("Anchor")) {
            setToolbarPosition(_val);

        } else if (_att.equalsIgnoreCase("Floatable")) {
            if (_val.equalsIgnoreCase("true")) {
                setToolbarFloatable(true);
            } else if (_val.equalsIgnoreCase("false")) {
                setToolbarFloatable(false);
            }
        }
    }


    /**
     * processElement
     *
     * @author Anthony C. Liberto
     * @param tagName
     * @param atts
     */
    protected void processElement(String tagName, Attributes atts) {
        if (tagName.equalsIgnoreCase("TOOLBAR")) {
            processToolbar(atts);
        }
    }

    /**
     * processEndElement
     *
     * @author Anthony C. Liberto
     * @param tagName
     */
    protected void processEndElement(String tagName) {
        if (tagName.equalsIgnoreCase("TOOLBAR")) {
        }
    }
    /**
     * getParentSelector
     * @return
     * @author Anthony C. Liberto
     * /
    public JComponent getParentSelector() {
        return parSel;
    }*/

    /**
     * getMenubar
     * @return
     * @author Anthony C. Liberto
     * /
    public EMenubar getMenubar() {
        return menubar;
    }

    /**
     * getEToolbar
     * @return
     * @author Anthony C. Liberto
     * /
    public EannounceToolbar getEToolbar() {
        EannounceEditor ed = getCurrentEditor(); //22675
        if (ed != null) { //22675
            return ed.getEToolbar();
        }
        return null; //22675
    }*/

    /**
     * getFocusEditor
     * @return
     * @author Anthony C. Liberto
     * /
    public EannounceEditor getFocusEditor() {
        for (int i = 0; i < MAX_EDITOR; ++i) {
            if (edit[i] != null && edit[i].hasFocus()) {
                return edit[i];
            }
        }
        return null;
    }
    
    /**
     * removeNewRows
     * @author Anthony C. Liberto
     * /
    public void removeNewRows() {
        EannounceEditor e = getCurrentEditor();
        if (e != null) {
            e.removeNewRows();
        }
        refreshUpdate();
    }*/
	/*
    private void removeEditor(int _type) {
        remove((JComponent) createEditor(_type, (String) null));
        return;
    }
*/
    /**
     * getEToolbar
     * @param _i
     * @return
     * @author Anthony C. Liberto
     * /
    public EannounceToolbar getEToolbar(int _i) {
        if (_i < 0 || _i >= MAX_EDITOR) {
            return null;
        }
        return edit[_i].getEToolbar();
    }

    /**
     * refreshAll
     * @author Anthony C. Liberto
     * /
    public void refreshAll() {
        for (int i = 0; i < MAX_EDITOR; ++i) {
            if (edit[i] != null) {
                edit[i].refresh();
            }
        }
    }*/
    /**
     * getEntityList
     * @return
     * @author Anthony C. Liberto
     * /
    public EntityList getEntityList() {
        return eList;
    }

    /**
     * setToolbarPosition
     * @param _s
     * @author Anthony C. Liberto
     */
    private void setToolbarPosition(String _s) {
        if (isValidPosition(_s)) {
            toolbarPosition = _s;
        }
    }

    private boolean isValidPosition(String _s) {
        if (_s.equals("West") || _s.equals("East") || _s.equals("South") || _s.equals("North")) {
            return true;
        }
        return false;
    }

    /**
     * getToolbarPosition
     * @return
     * @author Anthony C. Liberto
     */
    public String getToolbarPosition() {
        return toolbarPosition;
    }

    /**
     * setToolbarFloatable
     * @param _floatable
     * @author Anthony C. Liberto
     */
    private void setToolbarFloatable(boolean _floatable) {
        floatable = _floatable;
    }

    /**
     * isToolbarFloatable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isToolbarFloatable() {
        return floatable;
    }

    /**
     * setTable
     * @param _table
     * @author Anthony C. Liberto
     * /
    public void setTable(RowSelectableTable _table) {
        table = _table;
    }*/

    /**
     * loadActionTree
     * @param _ean
     * @param _title
     * @author Anthony C. Liberto
     * /
    public void loadActionTree(EANActionItem[] _ean, String _title) {
        tree.load(_ean, _title);
    }*/
}
