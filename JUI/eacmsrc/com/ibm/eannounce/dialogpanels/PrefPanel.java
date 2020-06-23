/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: PrefPanel.java,v $
 * Revision 1.5  2009/05/26 12:29:24  wendy
 * Performance cleanup
 *
 * Revision 1.4  2008/08/04 14:03:44  wendy
 * CQ00006067-WI : LA CTO - Added support for QueryAction
 *
 * Revision 1.3  2008/02/01 15:45:03  wendy
 * Moved column size limit to user preferences
 *
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.33  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.32  2005/05/17 17:48:31  tony
 * updated logic to address update of e-announce application.
 * added madatory update functionality as well.
 * improved pref logic for mandatory updates.
 *
 * Revision 1.31  2005/02/10 19:01:12  tony
 * Button Animation
 *
 * Revision 1.30  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.29  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.28  2005/02/02 17:30:20  tony
 * JTest Second Pass
 *
 * Revision 1.27  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.26  2005/01/14 19:49:44  tony
 * xpnd_action
 *
 * Revision 1.25  2005/01/05 16:57:29  tony
 * removed N/A functions
 *
 * Revision 1.24  2004/12/17 19:23:44  tony
 * autolink was loading based on the def file instead
 * of the arm file.  Adjusted the code to properly
 * arm the behavior.
 *
 * Revision 1.23  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.22  2004/11/03 23:50:14  tony
 * USRO-R-DMKR-66CHMM
 *
 * Revision 1.21  2004/10/26 22:36:55  tony
 * //TIR 664NNE
 *
 * Revision 1.20  2004/10/25 17:36:28  tony
 * improved size/sort functionality.
 *
 * Revision 1.19  2004/10/22 22:15:30  tony
 * auto_sort/size
 *
 * Revision 1.18  2004/10/18 20:23:42  tony
 * adjusted system.out statements.
 *
 * Revision 1.17  2004/10/13 19:31:35  tony
 * added capability for autoDetectUpdate preference and
 * corresponding logic to support the function.
 *
 * Revision 1.16  2004/10/12 18:15:01  tony
 * *** empty log message ***
 *
 * Revision 1.15  2004/10/11 21:00:37  tony
 * updated linktype functionality to bring in line
 * with preferences functionality
 *
 * Revision 1.14  2004/09/30 17:34:14  tony
 * updated navigate form functionality to better handle what
 * folders are loaded based on installation sets.
 *
 * Revision 1.13  2004/09/16 21:20:16  tony
 * updated autolink logic
 *
 * Revision 1.12  2004/09/03 20:26:43  tony
 * accessibility
 *
 * Revision 1.11  2004/08/26 20:55:33  tony
 * updated for accessibility.
 *
 * Revision 1.10  2004/08/26 16:26:35  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.9  2004/08/04 17:49:14  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.8  2004/07/29 22:37:52  tony
 * added in approved autolink functionality.
 *
 * Revision 1.7  2004/06/30 17:02:59  tony
 * hide autolink
 *
 * Revision 1.6  2004/06/25 16:28:43  tony
 * added auto_link preference.
 *
 * Revision 1.5  2004/06/22 18:03:34  tony
 * accessibility
 *
 * Revision 1.4  2004/06/17 18:58:57  tony
 * cr_4215 cr0313024215
 *
 * Revision 1.3  2004/04/07 17:24:39  tony
 * updated opaque settings
 *
 * Revision 1.2  2004/03/03 00:01:42  tony
 * added to functionality, moved firewall to preference.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.73  2004/01/16 15:35:43  tony
 * 53547
 *
 * Revision 1.72  2004/01/07 20:29:50  tony
 * acl_20040107
 * updated logic to allow for non-present jar file.
 *
 * Revision 1.71  2003/12/31 17:33:25  tony
 * acl_20031231
 * updated reset of behavior preferences.
 *
 * Revision 1.70  2003/12/31 16:57:13  tony
 * cr_3312
 *
 * Revision 1.69  2003/12/16 20:23:45  tony
 * 53421
 *
 * Revision 1.68  2003/12/11 22:30:02  tony
 * cr_3274
 *
 * Revision 1.67  2003/12/08 21:57:18  tony
 * cr_3274
 *
 * Revision 1.66  2003/10/29 19:09:48  tony
 * 52730
 *
 * Revision 1.65  2003/10/29 18:17:56  tony
 * 52727
 *
 * Revision 1.64  2003/10/29 17:38:07  tony
 * 52727
 *
 * Revision 1.63  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.62  2003/10/22 21:04:44  tony
 * 52685
 *
 * Revision 1.61  2003/10/15 20:25:42  tony
 * 52576
 *
 * Revision 1.60  2003/10/13 19:46:21  tony
 * 52534
 *
 * Revision 1.59  2003/10/07 22:03:08  tony
 * refined accessibility changes.
 *
 * Revision 1.58  2003/10/07 21:40:38  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.57  2003/09/29 17:19:27  tony
 * 52439
 *
 * Revision 1.56  2003/09/25 15:54:52  tony
 * 52370
 *
 * Revision 1.55  2003/09/16 18:59:52  tony
 * 52277
 *
 * Revision 1.54  2003/09/11 22:32:50  tony
 * preference for bookmark filter
 *
 * Revision 1.53  2003/09/05 17:34:16  tony
 * 2003-09-05 memory enhancements
 *
 * Revision 1.52  2003/09/05 16:03:02  tony
 * acl_20030905 added automatic garbage collection
 * preference to the application.
 *
 * Revision 1.51  2003/08/01 18:15:46  joan
 * 51614
 *
 * Revision 1.50  2003/07/11 17:00:15  tony
 * updated logic to perform default functions for
 * the preference panel.
 *
 * Revision 1.49  2003/07/09 15:09:01  tony
 * 51417 -- (did not tag) updated menu creation logic
 * to use the properties file instead of constant values.
 *
 * Revision 1.48  2003/06/25 23:47:06  tony
 * adjusted LNF loading to enable to load from
 * resource file.  This means look and feels can
 * be added on the fly.
 *
 * Revision 1.47  2003/06/25 18:19:37  tony
 * 1.2h bookmark enhancements
 *
 * Revision 1.46  2003/06/23 20:42:18  tony
 * updated font preference logic.
 *
 * Revision 1.45  2003/06/20 22:35:39  tony
 * 1.2 modification.
 *
 * Revision 1.44  2003/06/06 19:31:26  tony
 * 20030506 updated logic to prevent pref panel repitition
 * on log on log off reinitializing.
 *
 * Revision 1.43  2003/06/05 19:18:15  tony
 * 51162
 *
 * Revision 1.42  2003/06/05 18:52:15  tony
 * adjusted initializing so that profile preference gets
 * properly cleared out.
 *
 * Revision 1.41  2003/05/20 17:39:29  tony
 * updated logic to improve functionality.
 *
 * Revision 1.40  2003/05/09 14:57:15  tony
 * 50525
 *
 * Revision 1.39  2003/05/06 14:24:14  tony
 * 50525
 *
 * Revision 1.38  2003/05/06 14:20:48  tony
 * 50525
 *
 * Revision 1.37  2003/05/06 00:07:19  tony
 * 50468
 *
 * Revision 1.36  2003/05/01 22:41:35  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.35  2003/05/01 17:13:12  tony
 * 22585 added usability label.
 *
 * Revision 1.34  2003/04/24 15:33:12  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.33  2003/04/22 16:37:03  tony
 * created MWChooser to update handling of default
 * middlewareLocation.
 *
 * Revision 1.32  2003/04/21 23:21:47  tony
 * added association renderer for used table.
 *
 * Revision 1.31  2003/04/21 22:14:47  tony
 * updated default logic to allow for parent and child
 * color defaults.  Updated the sort on the whereUsed table.
 *
 * Revision 1.30  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.29  2003/04/21 17:30:18  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.28  2003/04/18 20:10:29  tony
 * added tab placement to preferences.
 *
 * Revision 1.27  2003/04/09 22:50:05  tony
 * adjsuted logic on ColumnOrder so that the preference
 * will always appear.
 *
 * Revision 1.26  2003/04/09 17:40:02  tony
 * formatting updated.
 *
 * Revision 1.25  2003/04/04 19:28:20  tony
 * fixed default middleware location initialization issue.
 *
 * Revision 1.24  2003/04/03 22:28:20  tony
 * column order needed to be based on selected profile.
 * added profile selector and improved logic.
 *
 * Revision 1.23  2003/04/03 19:01:19  tony
 * 50328
 *
 * Revision 1.22  2003/04/03 16:19:07  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.21  2003/04/02 17:56:30  tony
 * 50331
 *
 * Revision 1.20  2003/04/02 00:53:47  tony
 * added rollbackDefault logic.
 *
 * Revision 1.19  2003/03/29 00:40:16  tony
 * adjsuted logic for column order so that
 * default order is now handled by an additional
 * button instead of an additonal preference item.
 *
 * Revision 1.18  2003/03/29 00:06:27  tony
 * added remove Menu Logic
 *
 * Revision 1.17  2003/03/26 19:22:29  tony
 * updated preference list selection logice to prevent
 * duplicate call to load preference.
 *
 * Revision 1.16  2003/03/26 17:05:38  tony
 * adjusted refreshlookandfeel logic.
 *
 * Revision 1.15  2003/03/26 15:44:08  tony
 * Adjusted Role Function for Default Column Order.
 * Added setUpdateDefault call for default order.
 *
 * Revision 1.14  2003/03/26 01:02:36  tony
 * adjusted toolbar logic. setSelectedItem to null on
 * ComboBox.
 *
 * Revision 1.13  2003/03/25 23:29:05  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.12  2003/03/24 23:46:25  tony
 * workgroup reset.
 * adjusted logic on eComboBoxUI
 * accessibility panel enhancements.
 *
 * Revision 1.11  2003/03/21 18:12:21  tony
 * refined preferences logic.
 *
 * Revision 1.10  2003/03/20 23:59:36  tony
 * column order moved to preferences.
 * preferences refined.
 * Change History updated.
 * Default Column Order Stubs added
 *
 * Revision 1.9  2003/03/18 22:39:11  tony
 * more accessibility updates.
 *
 * Revision 1.8  2003/03/17 23:32:30  tony
 * accessibility update.
 *
 * Revision 1.7  2003/03/13 21:17:02  tony
 * accessibility enhancements.
 *
 * Revision 1.6  2003/03/13 18:38:44  tony
 * accessibility and column Order.
 *
 * Revision 1.5  2003/03/12 23:51:10  tony
 * accessibility and column order
 *
 * Revision 1.4  2003/03/11 00:33:23  tony
 * accessibility changes
 *
 * Revision 1.3  2003/03/07 21:40:47  tony
 * Accessibility update
 *
 * Revision 1.2  2003/03/05 18:54:23  tony
 * accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.15  2002/11/11 22:55:37  tony
 * adjusted classification on the toggle
 *
 * Revision 1.14  2002/11/07 16:58:13  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.eforms.editor.EIntField;
import com.ibm.eannounce.eforms.toolbar.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.*;
import java.awt.print.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.Arrays;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class PrefPanel extends AccessibleDialogPanel implements EAccessConstants, ListSelectionListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	/*
     * prefDialog variables
     */
  
    private EPanel pCentral = new EPanel(new BorderLayout());

    private EList list = new EList();
    private EScrollPane lScroll = new EScrollPane(list);
    private Vector vList = new Vector();

    private String[] raList = { PREFERENCE_PROFILE, PREFERENCE_LOOK_AND_FEEL, PREFERENCE_TOOLBAR, PREFERENCE_MIDDLEWARE_LOCATION, PREFERENCE_PRINT, PREFERENCE_FONT, PREFERENCE_APPEARANCE, PREFERENCE_COLUMN_ORDER, PREFERENCE_BEHAVIOR };

    //	private prefItem colPref = new prefItem(PREFERENCE_COLUMN_ORDER);

    private EPanel btnPnl = new EPanel(new BorderLayout(5, 5));
    private EButton bClose = new EButton(getString("clse"));
    private EButton bOk = new EButton(getString("save.pref"));
    private EButton bReset = new EButton(getString("reset.pref"));
    private Dimension spinDimension = new Dimension(30, 20);

    /*
     * profile variables
     */
    private PrefTreeModel wModel = new PrefTreeModel(new ProfNode(getString("dwGroup"), false));
    private PrefTree wTree = new PrefTree(wModel);
    private EScrollPane wScroll = new EScrollPane(wTree);
    //52439	private int opID = -1;
    private String userName = null;
//    private boolean bModal = false; //acl_20021204

    /*
     * look and feel
     */
    private EPanel pnlLnf = new EPanel(new BorderLayout());
    private EComboBox lnfCombo = new EComboBox();
    private ELabel lblLnf = new ELabel(getString("lnf.name"));
    private HashMap hashLNF = new HashMap();

    /*
     * toolbar
     */

    private EPanel pnlTbarMain = new EPanel(new BorderLayout());
    private EPanel pnlTbarButton = new EPanel(new BorderLayout());
    private EPanel pnlTbarAvail = new EPanel(new BorderLayout());
    private EPanel pnlTbarCur = new EPanel(new BorderLayout());
    private EPanel pnlTbarHeader = new EPanel(new BorderLayout()); //22585
    private EPanel pnlTbarMisc = new EPanel(new GridLayout(3, 2, 5, 5));

    private ELabel lblTbarCombo = new ELabel(getString("form.toolbar.select")); //22585
    private EComboBox tbarCombo = new EComboBox();
    private ELabel tbarLabelAvail = new ELabel(getString("avail"));
    private ToolbarList tbarAvailList = new ToolbarList();
    private EScrollPane tbarAvailScroll = new EScrollPane(tbarAvailList);

    private ELabel tbarLabelCur = new ELabel(getString("cur"));
    private ToolbarList tbarCurList = new ToolbarList();
    private EScrollPane tbarCurScroll = new EScrollPane(tbarCurList);
    private ToolbarControl tbarControl = new ToolbarControl(tbarAvailList, tbarCurList);

    private ELabel tbarLabelOrient = new ELabel(getString("orient"));
    private EComboBox tbarComboOrient = new EComboBox();
    private ELabel tbarLabelAlign = new ELabel(getString("align"));
    private EComboBox tbarComboAlign = new EComboBox();
    private ECheckBox tbarCheck = new ECheckBox(getString("flot"));
    private ECheckBox tbarAnimate = new ECheckBox(getString("animate"));

    /*
     * behavior
     */
    private EPanel bhvPnl0 = new EPanel(new BorderLayout());
    private EPanel bhvPnl1 = new EPanel(new GridLayout(4, 2, 5, 5));
    private EPanel bhvPnl2 = new EPanel(new GridLayout(7, 2, 5, 5));
    private ELabel lblTabLay = new ELabel(getString("tabLay"));
    private EComboBox bhvCombo = new EComboBox();

    private ELabel lblRefresh = new ELabel(getString("refreshType")); //cr_3274
    private EComboBox bhvRefresh = new EComboBox(); //cr_3274

    private ELabel lblClearWF = new ELabel(getString("clrWF")); //cr_4215
    private EComboBox bhvClearWF = new EComboBox(); //cr_4215

    private ELabel lblLinkType = new ELabel(getString("lnkmde")); //link_type
    private EComboBox bhvLinkType = new EComboBox(); //link_type

    private JCheckBox bhvBookCheck = new JCheckBox(getString("loadBookNew"));
    private JCheckBox bhvBookFilter = new JCheckBox(getString("bookFilter"));
    private JCheckBox bhvAutoGC = new JCheckBox(getString("autoGC"));
    private JCheckBox bhvFlagFrame = new JCheckBox(getString("vert.flag.frame")); //52730
    private JCheckBox bhvTreeXpnd = new JCheckBox(getString("tree.xpnd")); //cr_3312
    private JCheckBox bhvBehindFire = new JCheckBox(getString("behind.firewall"));
    private JCheckBox bhvAutoLink = new JCheckBox(getString("auto.link")); //auto_link
    private JCheckBox bhvAccessible = new JCheckBox(getString("accessible")); //access
    private JCheckBox bhvAutoUpdate = new JCheckBox(getString("autoUpdate")); //auto_update
    private JCheckBox bhvActionXpnd = new JCheckBox(getString("action.xpnd")); //xpnd_action

    private JCheckBox bhvAutoSort = new JCheckBox(getString("autoSort")); //auto_sort/size
    private JCheckBox bhvAutoSize = new JCheckBox(getString("autoSize")); //auto_sort/size
    private EIntField ifldSort = new EIntField(6) {//auto_sort/size
    	private static final long serialVersionUID = 1L;
    	public void nonDigit() {//auto_sort/size
            issueNonDigitWarning(); //auto_sort/size
        } //auto_sort/size
        public void lengthExceeded() { //auto_sort/size
            issueLengthWarning(); //auto_sort/size
        } //auto_sort/size
    }; //auto_sort/size
    private EIntField ifldRowsSize = new EIntField(6) {//auto_sort/size
    	private static final long serialVersionUID = 1L;
    	public void nonDigit() {//auto_sort/size
            issueNonDigitWarning(); //auto_sort/size
        } //auto_sort/size
        public void lengthExceeded() { //auto_sort/size
            issueLengthWarning(); //auto_sort/size
        } //auto_sort/size
    }; //auto_sort/size

    private EIntField ifldColsSize = new EIntField(6) {//auto_sort/size
    	private static final long serialVersionUID = 1L;
    	public void nonDigit() {//auto_sort/size
            issueNonDigitWarning(); //auto_sort/size
        } //auto_sort/size
        public void lengthExceeded() { //auto_sort/size
            issueLengthWarning(); //auto_sort/size
        } //auto_sort/size
    }; //auto_sort/size


    /*
     * printing
     */

    private EPanel prntPOrient = new EPanel(new GridLayout(1, 2, 0, 0));
    private EPanel prntPCheck = new EPanel(new GridLayout(1, 2, 0, 0));
    private EPanel prntPNorth = new EPanel(new GridLayout(1, 2, 0, 0));
    private EPanel prntPCenter = new EPanel(new GridLayout(2, 1, 0, 0));

    private ELabel prntLbl = new ELabel(getString("scale%"));
    private ESpinner prntSpin = new ESpinner(new SpinnerNumberModel(.5d, .3d, 5d, .10d));
    private ECheckBox prntChkX = new ECheckBox(getString("scaleX2fit"));
    private ECheckBox prntChkY = new ECheckBox(getString("scaleY2fit"));

    private ButtonGroup prntGroup = null;
    private ERadioButton prntRdoPortrait = new ERadioButton(getString("print.portrait"));
    private ERadioButton prntRdoLandscape = new ERadioButton(getString("print.landscape"));

    /*
     * font
     */
    private EPanel fontPtop = new EPanel(new GridLayout(2, 2, 5, 5));
    private EPanel fontPbot = new EPanel(new BorderLayout());
    private EPanel fontPnl = new EPanel(new BorderLayout());
    private EPanel fontPMain = new EPanel(new BorderLayout());

    private ELabel lblFont = new ELabel(getString("font.name"));
    private ELabel lblSize = new ELabel(getString("font.size"));

    private GraphicsEnvironment environ = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
    private EComboBox fontBox = new EComboBox();
    private boolean bCoolRender = EAccess.isArmed(COOL_ARM_FILE);

    private ESpinner fontSize = new ESpinner(new SpinnerNumberModel(DEFAULT_FONT_SIZE, 9, 72, 1));

    private ECheckBox chkBold = new ECheckBox(getString("bold"));
    private ECheckBox chkItalic = new ECheckBox(getString("italic"));

    private JLabel lblDisplay = new JLabel("e-announce");
    private Font defFnt = null;
    private boolean bFontRefresh = true;

    /*
     * appearance
     */
    private JCheckBox chkOverride = new JCheckBox(getString("color.override")); //acl_20031007
    private EPanel appPnl = new EPanel(new BorderLayout()); //acl_20031007
    //removed N/A colors	private ePanel appPnl_0 = new ePanel(new GridLayout(4,2,0,0));
    private EPanel appPnl_0 = new EPanel(new GridLayout(2, 2, 0, 0));
    private EPanel appPnl_1 = new EPanel(new GridLayout(2, 2, 0, 0));
    private EPanel appPnl_2 = new EPanel(new GridLayout(3, 2, 0, 0));
    private EPanel appPnl_3 = new EPanel(new GridLayout(5, 2, 0, 0));
    private EPanel appPnl_North = new EPanel(new BorderLayout());
    private EPanel appPnl_South = new EPanel(new BorderLayout());
    private EPanel appPnlMain = new EPanel(new BorderLayout());

    private ETitledBorder bord0 = new ETitledBorder(getString("enabled.border.title"));
    private ETitledBorder bord1 = new ETitledBorder(getString("found.border.title"));
    private ETitledBorder bord2 = new ETitledBorder(getString("relative.border.title"));
    private ETitledBorder bord3 = new ETitledBorder(getString("edit.border.title"));

    private ELabel lblFore = new ELabel(getString("color.foreground"));
    private ELabel lblBack = new ELabel(getString("color.background"));
    private ELabel lblSelFore = new ELabel(getString("color.selection.foreground"));
    private ELabel lblSelBack = new ELabel(getString("color.selection.background"));
    private EColorButton btnFore = new EColorButton(PREF_COLOR_FOREGROUND, DEFAULT_COLOR_ENABLED_FOREGROUND);
    private EColorButton btnBack = new EColorButton(PREF_COLOR_BACKGROUND, DEFAULT_COLOR_ENABLED_BACKGROUND);
    private EColorButton btnSelFore = new EColorButton(PREF_COLOR_SELECTION_FOREGROUND, DEFAULT_COLOR_SELECTION_FOREGROUND);
    private EColorButton btnSelBack = new EColorButton(PREF_COLOR_SELECTION_BACKGROUND, DEFAULT_COLOR_SELECTION_BACKGROUND);

    private ELabel lblFound = new ELabel(getString("color.found"));
    private ELabel lblFoundFocus = new ELabel(getString("color.found.focus"));
    private EColorButton btnFound = new EColorButton(PREF_COLOR_FOUND, DEFAULT_COLOR_FOUND);
    private EColorButton btnFoundFocus = new EColorButton(PREF_COLOR_FOUND_FOCUS, DEFAULT_COLOR_FOUND_FOCUS);

    private ELabel lblAssoc = new ELabel(getString("color.assoc"));
    private ELabel lblChild = new ELabel(getString("color.child"));
    private ELabel lblParent = new ELabel(getString("color.parent"));
    private EColorButton btnAssoc = new EColorButton(PREF_COLOR_ASSOC, DEFAULT_COLOR_ASSOC);
    private EColorButton btnChild = new EColorButton(PREF_COLOR_CHILD, DEFAULT_COLOR_CHILD);
    private EColorButton btnParent = new EColorButton(PREF_COLOR_PARENT, DEFAULT_COLOR_PARENT);

    private ELabel lblLock = new ELabel(getString("color.lock"));
    private ELabel lblLow = new ELabel(getString("color.low"));
    private ELabel lblLowReq = new ELabel(getString("color.low.required"));
    private ELabel lblCOK = new ELabel(getString("color.ok"));
    private ELabel lblReq = new ELabel(getString("color.required"));
    private EColorButton btnLock = new EColorButton(PREF_COLOR_LOCK, DEFAULT_COLOR_LOCK);
    private EColorButton btnLow = new EColorButton(PREF_COLOR_LOW, DEFAULT_COLOR_LOW);
    private EColorButton btnLowReq = new EColorButton(PREF_COLOR_LOW_REQUIRED, DEFAULT_COLOR_LOW_REQUIRED);
    private EColorButton btnCOK = new EColorButton(PREF_COLOR_OK, DEFAULT_COLOR_OK);
    private EColorButton btnReq = new EColorButton(PREF_COLOR_REQUIRED, DEFAULT_COLOR_REQUIRED);

    /*
     * columnOrder
     */
    private EPanel ordBtnPnl = new EPanel(new GridLayout(1, 4, 5, 5));
    private EButton bOrdSave = new EButton(getString("save.pref"));
    private EButton bOrdDef = new EButton(getString("save.pref.default"));
    private EButton bOrdDefReset = new EButton(getString("reset.pref.default"));
    private EButton bOrdReset = new EButton(getString("reset.pref"));
    private OrderPanel ordPnl = new OrderPanel() {
    	private static final long serialVersionUID = 1L;
    	public void adjustDefaultButton() {
            boolean b = isDefaultCapable() && canSave();
            bOrdDef.setEnabled(b);
            bOrdDef.setVisible(b);
        }

        public void adjustButtonEnabled() { //50525
            boolean b = canSave(); //50525
            bOrdSave.setEnabled(b); //50525
            bOrdDefReset.setEnabled(b); //50525
            bOrdReset.setEnabled(b); //50525
            adjustDefaultButton(); //52727
        } //50525
    };

    /**
     * prefPanel
     * @author Anthony C. Liberto
     */
    public PrefPanel() {
        super(new BorderLayout());
        lScroll.setFocusable(false);
        add("West", lScroll);
        setFocusable(false);
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        //		if (mwParse == null) {
        //			mwParse = eaccess().getMWParser();
        initComponents(); //50468
        initPrefDialog();
        initDefaultProfile();
        initLNF();
        initToolbar();
        //			initMW();
        initBehavior();
        initModalCursor();
        initPrint();
        initFont();
        initAppearance();
        initOrder();
        list.setSelectedIndex(0);
        processPreference(PREFERENCE_PROFILE);
        //		}
    }

    /**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
        return null;
    }

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
    }
    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
    }

    private void initModalCursor() {
        setModalCursor(true);
    }

    private void initPrefDialog() {
        loadPrefList();

        bClose.addActionListener(this);
        bClose.setActionCommand("close");
        bClose.setMnemonic(getChar("clse-s"));

        btnPnl.add("West", bOk);
        bOk.addActionListener(this);
        bOk.setMnemonic(getChar("save.pref-s"));
        btnPnl.add("East", bReset);
        bReset.addActionListener(this);
        bReset.setMnemonic(getChar("reset.pref-s"));

        list.addListSelectionListener(this);

        add("Center", pCentral);
        add("South", bClose);
    }

    private void loadPrefList() {
        int ii = -1;
        vList.clear();
        ii = raList.length;
        for (int i = 0; i < ii; ++i) {
            if (raList[i].equals(PREFERENCE_TOOLBAR) || raList[i].equals(PREFERENCE_LOOK_AND_FEEL)) {
                if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
                    addPrefItem(raList[i]);
                }
            } else {
                addPrefItem(raList[i]);
            }
        }
        list.setListData(vList);
    }

    private void addPrefItem(String _s) {
        addPrefItem(new PrefItem(_s));
    }

    private void addPrefItem(PrefItem _item) {
        if (!vList.contains(_item)) {
            vList.add(_item);
        }
    }

    /**
     * removePrefItem
     *
     * @author Anthony C. Liberto
     * @param _item
     * /
    protected void removePrefItem(PrefItem _item) {
        vList.remove(_item);
    }*/

    /**
     * loadPref
     * @param _pref
     * @author Anthony C. Liberto
     */
    private void loadPref(String _pref) {
        if (isModalBusy()) {
            return;
        }
        setModalBusy(true);
        processPreference(_pref);
        setModalBusy(false);
    }

    private void processPreference(String _pref) {
        pCentral.removeAll();
        if (_pref.equals(PREFERENCE_PROFILE)) {
            bOk.setActionCommand("profSave");
            bReset.setActionCommand("profReset");
            pCentral.add("Center", wScroll);
            pCentral.add("South", btnPnl);
            wTree.setDefaultPath();
        } else if (_pref.equals(PREFERENCE_LOOK_AND_FEEL)) {
            bOk.setActionCommand("lnfSave");
            bReset.setActionCommand("lnfReset");
            pCentral.add("North", pnlLnf);
            pCentral.add("South", btnPnl);
            setLookAndFeelDefault();
        } else if (_pref.equals(PREFERENCE_TOOLBAR)) {
            bOk.setActionCommand("tbarSave");
            bReset.setActionCommand("tbarReset");
            pCentral.add("North", pnlTbarMain);
            pnlTbarButton.add("South", btnPnl);
            pCentral.add("South", pnlTbarButton);
            setToolbarDefault();
            //			tbarAvailList.requestFocus();
        } else if (_pref.equals(PREFERENCE_MIDDLEWARE_LOCATION)) {
            //			bOk.setActionCommand("mwSave");
            //			bReset.setActionCommand("mwReset");
            pCentral.add("Center", eaccess().getMiddlewareChooser(getParentDialog(), false));
            //			pCentral.add("South", btnPnl);
            //			setMiddlewareDefault();
        } else if (_pref.equals(PREFERENCE_PRINT)) {
            bOk.setActionCommand("savePrint");
            bReset.setActionCommand("resetPrint");
            pCentral.add("North", prntPNorth);
            pCentral.add("Center", prntPCenter);
            pCentral.add("South", btnPnl);
            loadPrint();
        } else if (_pref.equals(PREFERENCE_FONT)) {
            bOk.setActionCommand("saveFont");
            bReset.setActionCommand("resetFont");
            pCentral.add("North", fontPMain);
            pCentral.add("South", btnPnl);
            loadFont();
        } else if (_pref.equals(PREFERENCE_APPEARANCE)) {
            bOk.setActionCommand("saveAppearance");
            bReset.setActionCommand("resetAppearance");
            pCentral.add("North", appPnlMain);
            pCentral.add("South", btnPnl);
            loadAppearance();
        } else if (_pref.equals(PREFERENCE_COLUMN_ORDER) || _pref.equals(PREFERENCE_DEFAULT_COLUMN_ORDER)) {
            //52727			bOk.setActionCommand("saveOrder");					//51614
            //52727			bReset.setActionCommand("resetOrder");				//51614
            pCentral.add("North", ordPnl);
            pCentral.add("South", ordBtnPnl);
            loadOrderDefault();

            //52727		pCentral.add("South",btnPnl);
            //52727		loadOrderDefault();
            //			loadOrder(_pref);
        } else if (_pref.equals(PREFERENCE_BEHAVIOR)) {
            bOk.setActionCommand("save_behave");
            bReset.setActionCommand("reset_behave");
            pCentral.add("Center", bhvPnl0);
            pCentral.add("South", btnPnl);
            loadBehavior();
        }
        packDialog();
        list.ensureIndexIsVisible(list.getSelectedIndex());
        repaint();
    }

    private void fyiMsg(String _s) {
        eaccess().showFYI((Window) getParentDialog(), _s);
    }

    private void loadSelectedPreference() {
        Object o = list.getSelectedValue();
        if (o != null && o instanceof PrefItem) {
            loadPref(((PrefItem) o).getPrefKey());
        }
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        bClose.removeActionListener(this);
        bOk.removeActionListener(this);
        bReset.removeActionListener(this);

        dereferenceLNF();
        dereferenceToolbar();
        //		dereferenceMW();
        dereferencePrint();
        dereferenceFont();
        dereferenceAppearance();
        dereferenceBehavior();
        dereferenceOrder();

        if (lScroll != null) {
            lScroll.dereference();
            lScroll = null;
        }

        if (wScroll != null) {
            wScroll.dereference();
            wScroll = null;
        }

        if (tbarAvailScroll != null) {
            tbarAvailScroll.dereference();
            tbarAvailScroll = null;
        }

        if (tbarCurScroll != null) {
            tbarCurScroll.dereference();
            tbarCurScroll = null;
        }

        removeAll();
        removeNotify();
    }

    /*
     * default profile
     */
    private void initDefaultProfile() {
        loadProfileTree(generateProfileArray(eaccess().getProfileSet()));
    }

    private void loadProfileTree(Profile[] _prof) {
        ProfNode root = null;
        ProfNode dmtn = null;
        ProfNode tmp = null;
        int ii = -1;
        if (_prof == null) {
            return;
        }
        ii = _prof.length;
        if (ii < 0) { //52439
            return; //52439
        } //52439
        root = (ProfNode) wModel.getRoot();

        //Arrays.sort(_prof, profComp); //51162
        Arrays.sort(_prof, new ProfComparator()); //51162

        wModel.clear();
        for (int i = 0; i < ii; ++i) {
            if (dmtn == null || !dmtn.toString().equals(_prof[i].getWGName())) {
                if (dmtn != null) {
                    wTree.expandPath(new TreePath(dmtn.getPath()));
                }
                dmtn = new ProfNode(_prof[i].getWGName(), false);
                wModel.addNode(dmtn, root, root.getChildCount());
            }
            tmp = new ProfNode(_prof[i], true);
            wModel.addNode(tmp, dmtn, dmtn.getChildCount());
        }
        userName = _prof[0].getOPName(); //52439
        wTree.setSelectionModel(new PrefTreeSelectionModel());
        wTree.expandPath(new TreePath(dmtn.getPath()));
        wTree.treeDidChange();
        packDialog();
    }

    private Profile[] generateProfileArray(ProfileSet _ps) {
        Profile[] prof = null;
        if (_ps == null) {
            return null;
        }
        prof = _ps.toArray();
        Arrays.sort(prof, new EComparator());
        return prof;
    }
    /*
     * look and feel
     */
    private void initLNF() {
        loadLookAndFeels();
        pnlLnf.add("West", lblLnf);
        pnlLnf.add("East", lnfCombo);
        lblLnf.setLabelFor(lnfCombo);
        lblLnf.setUseDefined(false);
        lblLnf.setDisplayedMnemonic(getChar("lnf.name-s"));
    }

    private void dereferenceLNF() {
        list.removeListSelectionListener(this);
        wModel.dereference();
    }

    private void setLookAndFeel(Object _o) {
        if (_o != null && _o instanceof String) {
            String s = (String) _o;
            setPrefString(LOOK_AND_FEEL, s);
        } else if (_o == null) {
            clearPref(LOOK_AND_FEEL, true);
            setLookAndFeelDefault();
        }
    }

    private void setLookAndFeelDefault() {
        String defLNF = null;
        lnfCombo.setEnabled(!EAccess.isArmed(ACCESSIBLE_ARM_FILE));
        defLNF = getString("eannounce.accessible.default.form");
        if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            defLNF = getPrefString(LOOK_AND_FEEL, getString("eannounce.default.form"));
        }
        if (defLNF != null) {
            int ii = lnfCombo.getItemCount();
            for (int i = 0; i < ii; ++i) {
                Object o = hashLNF.get(lnfCombo.getItemAt(i));
                if (o instanceof String) {
                    if (defLNF.equals((String) o)) {
                        lnfCombo.setSelectedIndex(i);
                        return;
                    }
                }
            }
        }
        lnfCombo.setSelectedItem(null);
    }
    /*
     * toolbar
     */
    private void initToolbar() {
        loadToolbarCombos();

        pnlTbarAvail.add("North", tbarLabelAvail);
        pnlTbarAvail.add("West", tbarAvailScroll);
        pnlTbarAvail.add("East", tbarControl);

        pnlTbarCur.add("North", tbarLabelCur);
        pnlTbarCur.add("West", tbarCurScroll);

        pnlTbarMisc.add(tbarLabelOrient);
        pnlTbarMisc.add(tbarComboOrient);
        pnlTbarMisc.add(tbarLabelAlign);
        pnlTbarMisc.add(tbarComboAlign);
        pnlTbarMisc.add(tbarCheck);
		pnlTbarMisc.add(tbarAnimate);

        pnlTbarButton.add("North", pnlTbarMisc);

        pnlTbarHeader.add("North", lblTbarCombo); //22585
        pnlTbarHeader.add("South", tbarCombo); //22585

        pnlTbarMain.add("North", pnlTbarHeader); //22585
        pnlTbarMain.add("East", pnlTbarCur);
        pnlTbarMain.add("West", pnlTbarAvail);

        tbarCombo.addActionListener(this);
        tbarCombo.setActionCommand("tbarCombo");
        tbarCombo.setSelectedItem(null);
        tbarComboOrient.setSelectedItem(null);
        tbarComboAlign.setSelectedItem(null);

        tbarLabelOrient.setDisplayedMnemonic(getChar("orient-s"));
        tbarLabelOrient.setLabelFor(tbarComboOrient);
        tbarLabelAlign.setDisplayedMnemonic(getChar("align-s"));
        tbarLabelAlign.setLabelFor(tbarComboAlign);
        tbarCheck.setMnemonic(getChar("flot-s"));
        tbarAnimate.setMnemonic(getChar("animate-s"));
    }

    private void toolbarEnable(boolean _b) {
        lblTbarCombo.setEnabled(_b);
        tbarCombo.setEnabled(_b);
        tbarLabelAvail.setEnabled(_b);
        tbarAvailList.setEnabled(_b);

        tbarLabelCur.setEnabled(_b);
        tbarCurList.setEnabled(_b);
        tbarControl.setEnabled(_b);

        tbarLabelOrient.setEnabled(_b);
        tbarComboOrient.setEnabled(_b);
        tbarLabelAlign.setEnabled(_b);
        tbarComboAlign.setEnabled(_b);
        tbarCheck.setEnabled(_b);
        tbarAnimate.setEnabled(_b);
    }

    private void setToolbarDefault() {
        if (EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            tbarCombo.setSelectedItem(null);
            tbarAvailList.importToolbar(null);
            tbarCurList.importToolbar(null);
            toolbarEnable(false);
        } else {
            ComboItem item = DefaultToolbarLayout.NAV_BAR;
            ETabable eTab = eaccess().getCurrentTab();
            if (eTab != null) {
                String sPanelType = eTab.getVisiblePanelType();
                if (sPanelType.equals(TYPE_HORZEDITOR)) {
                    item = DefaultToolbarLayout.EDIT_HORZ_BAR;
                } else if (sPanelType.equals(TYPE_VERTEDITOR)) {
                    item = DefaultToolbarLayout.EDIT_VERT_BAR;
                } else if (sPanelType.equals(TYPE_FORMEDITOR)) {
                    item = DefaultToolbarLayout.EDIT_FORM_BAR;
                } else if (sPanelType.equals(TYPE_LOCKACTION)) {
                    item = DefaultToolbarLayout.LOCK_BAR;
                } else if (sPanelType.equals(TYPE_MATRIXACTION)) {
                    item = DefaultToolbarLayout.MATRIX_BAR;
                } else if (sPanelType.equals(TYPE_USEDACTION)) {
                    item = DefaultToolbarLayout.USED_BAR_TABLE;
                }else if (sPanelType.equals(TYPE_QUERYACTION)) {
                    item = DefaultToolbarLayout.QUERY_BAR;
                }
            }
            tbarCombo.setSelectedItem(item);
            tbarAvailList.importToolbar(DefaultToolbarLayout.getAvailLayout(item));
            tbarCurList.importToolbar(getCurrentLayout(item));
            toolbarEnable(true);
        }
    }

    private void dereferenceToolbar() {
        tbarCombo.removeActionListener(this);
        tbarControl.dereference();
    }

    private void loadToolbarCombos() {
        int ii = -1;
        ComboItem[] bars = DefaultToolbarLayout.getToolbars();
        tbarCombo.removeAllItems(); //20030506
        tbarComboOrient.removeAllItems(); //20030506
        tbarComboAlign.removeAllItems(); //20030506
        ii = bars.length;
        for (int i = 0; i < ii; ++i) {
            tbarCombo.addItem(bars[i]);
        }
        tbarComboOrient.addItem(ComboItem.HORIZONTAL_ITEM);
        tbarComboOrient.addItem(ComboItem.VERTICAL_ITEM);
        tbarComboAlign.addItem(ComboItem.NORTH_ITEM);
        tbarComboAlign.addItem(ComboItem.SOUTH_ITEM);
        tbarComboAlign.addItem(ComboItem.EAST_ITEM);
        tbarComboAlign.addItem(ComboItem.WEST_ITEM);
    }

    /*
     * print
     */

    private void initPrint() {
        prntSpin.setSize(spinDimension);
        prntSpin.setPreferredSize(spinDimension);

        if (prntGroup == null) {
            prntGroup = new ButtonGroup();
            prntGroup.add(prntRdoPortrait);
            prntGroup.add(prntRdoLandscape);
        }

        prntPOrient.setTransparent(true);
        prntPCheck.setTransparent(true);
        prntPCenter.setTransparent(true);

        prntChkX.addActionListener(this);
        prntChkX.setActionCommand("printX");
        prntChkY.addActionListener(this);
        prntChkY.setActionCommand("printY");
        prntRdoPortrait.setMnemonic(getChar("print.portrait-s"));
        prntRdoLandscape.setMnemonic(getChar("print.landscape-s"));
        prntChkX.setMnemonic(getChar("scaleX2fit-s"));
        prntChkY.setMnemonic(getChar("scaleY2fit-s"));

        prntPNorth.add(prntLbl);
        prntLbl.setLabelFor(prntSpin);
        prntLbl.setDisplayedMnemonic(getChar("scale%-s"));
        prntPNorth.add(prntSpin);

        prntPOrient.add(prntRdoPortrait);
        prntPOrient.add(prntRdoLandscape);

        prntPCheck.add(prntChkX);
        prntPCheck.add(prntChkY);

        prntPCenter.add(prntPOrient);
        prntPCenter.add(prntPCheck);
    }

    private void dereferencePrint() {
        prntChkX.removeActionListener(this);
        prntChkY.removeActionListener(this);
    }

    private void setPrintSpinEnabled() {
        prntSpin.setEnabled(!(prntChkX.isSelected() || prntChkY.isSelected()));
    }

    private void setSpinScale(double _d) {
        prntSpin.setValue(new Double(_d));
    }

    private void resetPrint() {
        clearPref(PREF_PRINT_SCALE_X, false);
        clearPref(PREF_PRINT_SCALE_Y, false);
        clearPref(PREF_PRINT_RATIO, false);
        clearPref(PREF_PRINT_ORIENTATION, true);
        eaccess().writePref();
        loadPrint();
        eaccess().printReset(); //50331
    }

    private void loadPrint() {
        setSpinScale(getScale());
        setOrientation(getPrintOrientation());
        prntChkX.setSelected(isScaleToFit(PREF_PRINT_SCALE_X));
        prntChkY.setSelected(isScaleToFit(PREF_PRINT_SCALE_Y));
        setPrintSpinEnabled();
    }

    private void savePrint() {
        setScale(getSpinScale());
        setScaleToFit(PREF_PRINT_SCALE_X, prntChkX.isSelected());
        setScaleToFit(PREF_PRINT_SCALE_Y, prntChkY.isSelected());
        setPrintOrientation(prntRdoPortrait.isSelected() ? PageFormat.PORTRAIT : PageFormat.LANDSCAPE);
        eaccess().printReset(); //50331
    }

    private double getSpinScale() {
        Number num = ((SpinnerNumberModel) prntSpin.getModel()).getNumber();
        return num.doubleValue();
    }

    private void setScale(double _print) {
    	eaccess().setPrefDouble(PREF_PRINT_RATIO, _print);
    }

    private double getScale() {
        return eaccess().getPrefDouble(PREF_PRINT_RATIO, PRINT_DEFAULT_RATIO);
    }

    private void setScaleToFit(String _x, boolean _b) {
    	eaccess().setPrefBoolean(_x, _b);
    }

    private boolean isScaleToFit(String _x) {
        return eaccess().getPrefBoolean(_x, false);
    }

    private void setPrintOrientation(int _i) {
        setPrefInt(PREF_PRINT_ORIENTATION, _i);
    }

    private void setOrientation(int _i) {
        switch (_i) {
        case PageFormat.LANDSCAPE :
            prntRdoLandscape.setSelected(true);
            break;
        default :
            prntRdoPortrait.setSelected(true);
            break;
        }
    }

    private int getPrintOrientation() {
        return eaccess().getPrefInt(PREF_PRINT_ORIENTATION, PRINT_DEFAULT_ORIENTATION);
    }

    /*
     * font
     */
    private void initFont() {
        Dimension d = new Dimension(50, 70);
        loadFonts(environ.getAllFonts());
        fontPtop.add(lblFont);
        fontPtop.add(fontBox);
        fontPtop.add(lblSize);
        fontPtop.add(fontSize);

        lblFont.setLabelFor(fontBox);
        lblFont.setDisplayedMnemonic(getChar("font.name-s"));
        lblFont.setUseDefined(false);
        lblSize.setLabelFor(fontSize);
        lblSize.setDisplayedMnemonic(getChar("font.size-s"));
        lblSize.setUseDefined(false);

        fontSize.setSize(spinDimension);
        fontSize.setPreferredSize(spinDimension);
        fontSize.setMinimumSize(spinDimension);
        lblDisplay.setSize(d);
        lblDisplay.setPreferredSize(d);
        lblDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        lblDisplay.setVerticalAlignment(SwingConstants.CENTER);

        fontPbot.add("West", chkBold);
        fontPbot.add("East", chkItalic);

        fontBox.addActionListener(this);
        fontSize.addChangeListener(this);
        chkBold.addActionListener(this);
        chkItalic.addActionListener(this);

        chkBold.setMnemonic(getChar("bold-s"));
        chkBold.setToolTipText(getString("bold-t"));
        chkItalic.setMnemonic(getChar("italic-s"));
        chkItalic.setToolTipText(getString("italic-t"));

        fontBox.setActionCommand("font_changed");
        chkBold.setActionCommand("font_changed");
        chkItalic.setActionCommand("font_changed");

        fontPnl.add("North", fontPtop);
        fontPnl.add("South", fontPbot);
        fontPMain.add("North", fontPnl);
        fontPMain.add("South", lblDisplay);
    }

    /**
     * loadFonts
     * @param _font
     * @author Anthony C. Liberto
     */
    private void loadFonts(Font[] _font) {
        int ii = -1;
        fontBox.removeAllItems();
        ii = _font.length;
        Arrays.sort(_font, new EComparator()); //52370
        for (int i = 0; i < ii; ++i) {
            if (bCoolRender) {
                if (isDisplayableFont(_font[i])) {
                    fontBox.addItem(_font[i].deriveFont(14f));
                }
            } else {
                fontBox.addItem(_font[i].getFontName());
            }
        }
    }

    private void dereferenceFont() {
        fontBox.removeActionListener(this);
        fontSize.removeChangeListener(this);
        chkBold.removeActionListener(this);
        chkItalic.removeActionListener(this);
    }

    private int getFontSize() {
        Number num = ((SpinnerNumberModel) fontSize.getModel()).getNumber();
        if (num != null) {
            return num.intValue();
        }
        return DEFAULT_FONT_SIZE;
    }

    private void setFontSize(int _i) {
        fontSize.setValue(new Integer(_i));
    }

    private String getFontFace() {
        Object o = fontBox.getSelectedItem();
        if (o instanceof String) {
            String str = (String) o;
            if (str != null) {
                return str;
            }
        } else if (o instanceof Font) {
            Font f = (Font) o;
            if (f != null) {
                return f.getFontName();
            }
        }
        return DEFAULT_FONT_FACE;
    }

    private void setFontFace(String _s) {
        int ii = -1;
        if (_s == null) {
            fontBox.setSelectedItem(null);
            return;
        }
        ii = fontBox.getItemCount();
        for (int i = 0; i < ii; ++i) {
            Object o = fontBox.getItemAt(i);
            if (o instanceof Font) {
                Font f = (Font) o;
                if (f.getFontName().equals(_s)) {
                    fontBox.setSelectedIndex(i);
                    return;
                }
            } else if (o instanceof String) {
                String s = (String) o;
                if (s.equals(_s)) {
                    fontBox.setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    private int getFontStyle() {
        int out = Font.PLAIN;
        if (chkBold.isSelected()) {
            out += Font.BOLD;
        }
        if (chkItalic.isSelected()) {
            out += Font.ITALIC;
        }
        return out;
    }

    private void setFontStyle(int _i) {
        switch (_i) {
        case Font.PLAIN :
            chkBold.setSelected(false);
            chkItalic.setSelected(false);
            break;
        case Font.BOLD :
            chkBold.setSelected(true);
            chkItalic.setSelected(false);
            break;
        case Font.ITALIC :
            chkBold.setSelected(false);
            chkItalic.setSelected(true);
            break;
        case (Font.BOLD + Font.ITALIC) :
        default :
            chkBold.setSelected(true);
            chkItalic.setSelected(true);
            break;
        }
    }

    private void updateFont(String _s) {
        defFnt = new Font(_s, getFontStyle(), getFontSize());
        lblDisplay.setFont(defFnt);
        lblDisplay.revalidate();
        //		packDialog();
    }

    private void loadFont() {
        String strFace = null;
        bFontRefresh = false;
        setFontSize(eaccess().getPrefInt(PREF_FONT_SIZE, DEFAULT_FONT_SIZE));
        setFontStyle(eaccess().getPrefInt(PREF_FONT_STYLE, DEFAULT_FONT_STYLE));
        strFace = getPrefString(PREF_FONT_FACE, DEFAULT_FONT_FACE);
        loadFont(strFace);
        updateFont(strFace);
        bFontRefresh = true;
    }

    private void loadFont(String _fontFace) {
        boolean bFontFound = false;
        int i = 0;
        int ii = -1;
        fontBox.setSelectedItem(null);
        ii = fontBox.getItemCount();
        for (i = 0; i < ii; ++i) {
            Object o = fontBox.getItemAt(i);
            if (o instanceof Font) {
                Font f = (Font) o;
                if (f.getFontName().equals(_fontFace)) {
                    bFontFound = true;
                    break;
                }
            } else if (o instanceof String) {
                if (_fontFace.equals((String) o)) {
                    bFontFound = true;
                    break;
                }
            }
        }
        bFontRefresh = false;
        if (bFontFound) {
            fontBox.setSelectedIndex(i);
            defFnt = new Font(_fontFace, getFontStyle(), getFontSize());
            setFontFace(_fontFace);
        } else {
            defFnt = getFont();
            setFontFace(defFnt.getFontName());
        }
        chkBold.setSelected(defFnt.isBold());
        chkItalic.setSelected(defFnt.isItalic());
        setFontSize(defFnt.getSize());
        lblDisplay.setForeground(eaccess().getPrefColor(PREF_COLOR_FOREGROUND, DEFAULT_COLOR_ENABLED_FOREGROUND));
        bFontRefresh = true;
    }

    private void saveFont() {
        setPrefString(PREF_FONT_FACE, getFontFace());
        setPrefInt(PREF_FONT_SIZE, getFontSize());
        setPrefInt(PREF_FONT_STYLE, getFontStyle());
        eaccess().setPrefFont(PREF_FONT, defFnt);
        refreshApp();
        refreshAppearance();
        validateDialog();
        //		packDialog();
        lblDisplay.revalidate();
    }

    private void resetFont() {
        clearPref(PREF_FONT_FACE, false);
        clearPref(PREF_FONT_SIZE, false);
        clearPref(PREF_FONT_STYLE, false);
        clearPref(PREF_FONT, true);
        loadFont();
        refreshApp();
        refreshAppearance();
        validateDialog();
        //		packDialog();
    }

    /*
     * appearance
     */
    private void initAppearance() {
        boolean bOver = false;
        btnFore.addActionListener(this);
        btnFore.setActionCommand("enabled.foreground");
        lblFore.setLabelFor(btnFore);

        btnBack.addActionListener(this);
        btnBack.setActionCommand("enabled.background");
        lblBack.setLabelFor(btnBack);

        btnSelFore.addActionListener(this);
        btnSelFore.setActionCommand("selection.foreground");
        lblSelFore.setLabelFor(btnSelFore);

        btnSelBack.addActionListener(this);
        btnSelBack.setActionCommand("selection.background");
        lblSelBack.setLabelFor(btnSelBack);

        btnFound.addActionListener(this);
        btnFound.setActionCommand("found.color");
        lblFound.setLabelFor(btnFound);

        btnFoundFocus.addActionListener(this);
        btnFoundFocus.setActionCommand("found.focus.color");
        lblFoundFocus.setLabelFor(btnFoundFocus);

        btnAssoc.addActionListener(this);
        btnAssoc.setActionCommand("assoc.color");
        lblAssoc.setLabelFor(btnAssoc);

        btnChild.addActionListener(this);
        btnChild.setActionCommand("child.color");
        lblChild.setLabelFor(btnChild);

        btnParent.addActionListener(this);
        btnParent.setActionCommand("parent.color");
        lblParent.setLabelFor(btnParent);

        btnLock.addActionListener(this);
        btnLock.setActionCommand("lock.color");
        lblLock.setLabelFor(btnLock);

        btnLow.addActionListener(this);
        btnLow.setActionCommand("low.color");
        lblLow.setLabelFor(btnLow);

        btnLowReq.addActionListener(this);
        btnLowReq.setActionCommand("low.required.color");
        lblLowReq.setLabelFor(btnLowReq);

        btnCOK.addActionListener(this);
        btnCOK.setActionCommand("ok.color");
        lblCOK.setLabelFor(btnCOK);

        btnReq.addActionListener(this);
        btnReq.setActionCommand("required.color");
        lblReq.setLabelFor(btnReq);

        chkOverride.addActionListener(this); //acl_20031007
        chkOverride.setActionCommand("override.color"); //acl_20031007
        bOver = eaccess().canOverrideColor(); //acl_20031007
        chkOverride.setSelected(bOver); //acl_20031007
        chkOverride.setOpaque(false);
        chkOverride.setMnemonic(getChar("color.override-s"));
        setOverrideEnabled(bOver); //acl_20031007

        appPnl_0.add(lblFore);
        appPnl_0.add(btnFore);
        appPnl_0.add(lblBack);
        appPnl_0.add(btnBack);
        /*
        removed N/A colors
        		appPnl_0.add(lblSelFore);
        		appPnl_0.add(btnSelFore);
        		appPnl_0.add(lblSelBack);
        		appPnl_0.add(btnSelBack);
        */
        appPnl_1.add(lblFound);
        appPnl_1.add(btnFound);
        appPnl_1.add(lblFoundFocus);
        appPnl_1.add(btnFoundFocus);

        appPnl_2.add(lblAssoc);
        appPnl_2.add(btnAssoc);
        appPnl_2.add(lblChild);
        appPnl_2.add(btnChild);
        appPnl_2.add(lblParent);
        appPnl_2.add(btnParent);

        appPnl_3.add(lblLock);
        appPnl_3.add(btnLock);
        appPnl_3.add(lblLow);
        appPnl_3.add(btnLow);
        appPnl_3.add(lblLowReq);
        appPnl_3.add(btnLowReq);
        appPnl_3.add(lblCOK);
        appPnl_3.add(btnCOK);
        appPnl_3.add(lblReq);
        appPnl_3.add(btnReq);

        appPnl_0.setBorder(bord0);
        appPnl_1.setBorder(bord1);
        appPnl_2.setBorder(bord2);
        appPnl_3.setBorder(bord3);

        appPnl.add("North", chkOverride); //acl_20031007
        appPnl.add("South", appPnl_0); //acl_20031007

        //acl_20031007		appPnl_North.add("North",appPnl_0);
        appPnl_North.add("North", appPnl); //acl_20031007
        appPnl_North.add("South", appPnl_1);
        appPnl_South.add("North", appPnl_2);
        appPnl_South.add("South", appPnl_3);
        appPnlMain.add("North", appPnl_North);
        appPnlMain.add("South", appPnl_South);
    }

    private void dereferenceAppearance() {
        chkOverride.removeActionListener(this); //acl_20031007
        btnBack.removeActionListener(this);
        btnFore.removeActionListener(this);
        btnSelBack.removeActionListener(this);
        btnSelFore.removeActionListener(this);

        btnFound.removeActionListener(this);
        btnFoundFocus.removeActionListener(this);

        btnAssoc.removeActionListener(this);
        btnChild.removeActionListener(this);
        btnParent.removeActionListener(this);

        btnReq.removeActionListener(this);
        btnLowReq.removeActionListener(this);
        btnLow.removeActionListener(this);
        btnCOK.removeActionListener(this);
        btnLock.removeActionListener(this);
    }

    private void setOverrideEnabled(boolean _bOver) { //acl_20031007
        lblFore.setEnabled(_bOver); //acl_20031007
        btnFore.setEnabled(_bOver); //acl_20031007
        lblBack.setEnabled(_bOver); //acl_20031007
        btnBack.setEnabled(_bOver); //acl_20031007
        lblSelFore.setEnabled(_bOver); //acl_20031007
        btnSelFore.setEnabled(_bOver); //acl_20031007
        lblSelBack.setEnabled(_bOver); //acl_20031007
        btnSelBack.setEnabled(_bOver); //acl_20031007

        lblFound.setEnabled(_bOver); //acl_20031007
        btnFound.setEnabled(_bOver); //acl_20031007
        lblFoundFocus.setEnabled(_bOver); //acl_20031007
        btnFoundFocus.setEnabled(_bOver); //acl_20031007

        lblAssoc.setEnabled(_bOver); //acl_20031007
        btnAssoc.setEnabled(_bOver); //acl_20031007
        lblChild.setEnabled(_bOver); //acl_20031007
        btnChild.setEnabled(_bOver); //acl_20031007
        lblParent.setEnabled(_bOver); //acl_20031007
        btnParent.setEnabled(_bOver); //acl_20031007

        lblLock.setEnabled(_bOver); //acl_20031007
        btnLock.setEnabled(_bOver); //acl_20031007
        lblLow.setEnabled(_bOver); //acl_20031007
        btnLow.setEnabled(_bOver); //acl_20031007
        lblLowReq.setEnabled(_bOver); //acl_20031007
        btnLowReq.setEnabled(_bOver); //acl_20031007
        lblCOK.setEnabled(_bOver); //acl_20031007
        btnCOK.setEnabled(_bOver); //acl_20031007
        lblReq.setEnabled(_bOver); //acl_20031007
        btnReq.setEnabled(_bOver); //acl_20031007
    } //acl_20031007

    /**
     * getSelectedEnabledForeground
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedEnabledForeground(boolean _choose) {
        return btnFore.getSelectedColor(_choose);
    }

    /**
     * getSelectedEnabledBackground
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedEnabledBackground(boolean _choose) {
        return btnBack.getSelectedColor(_choose);
    }

    /**
     * getSelectedSelectionForeground
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedSelectionForeground(boolean _choose) {
        return btnSelFore.getSelectedColor(_choose);
    }

    /**
     * getSelectedSelectionBackground
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedSelectionBackground(boolean _choose) {
        return btnSelBack.getSelectedColor(_choose);
    }

    /**
     * getSelectedRequiredColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedRequiredColor(boolean _choose) {
        return btnReq.getSelectedColor(_choose);
    }

    /**
     * getSelectedLowRequiredColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedLowRequiredColor(boolean _choose) {
        return btnLowReq.getSelectedColor(_choose);
    }

    /**
     * getSelectedLowColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedLowColor(boolean _choose) {
        return btnLow.getSelectedColor(_choose);
    }

    /**
     * getSelectedOKColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedOKColor(boolean _choose) {
        return btnCOK.getSelectedColor(_choose);
    }

    /**
     * getSelectedLockColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedLockColor(boolean _choose) {
        return btnLock.getSelectedColor(_choose);
    }

    /**
     * getSelectedAssocColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedAssocColor(boolean _choose) {
        return btnAssoc.getSelectedColor(_choose);
    }

    /**
     * getSelectedChildColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedChildColor(boolean _choose) {
        return btnChild.getSelectedColor(_choose);
    }

    /**
     * getSelectedParentColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedParentColor(boolean _choose) {
        return btnParent.getSelectedColor(_choose);
    }

    /**
     * getSelectedFoundColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedFoundColor(boolean _choose) {
        return btnFound.getSelectedColor(_choose);
    }

    /**
     * getSelectedFoundFocusColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    private Color getSelectedFoundFocusColor(boolean _choose) {
        return btnFoundFocus.getSelectedColor(_choose);
    }

    private void loadAppearance() {
    }

    private void saveAppearance() {
    	eaccess().setPrefColor(PREF_COLOR_FOREGROUND, getSelectedEnabledForeground(false));
    	eaccess().setPrefColor(PREF_COLOR_BACKGROUND, getSelectedEnabledBackground(false));
    	eaccess().setPrefColor(PREF_COLOR_SELECTION_FOREGROUND, getSelectedSelectionForeground(false));
    	eaccess().setPrefColor(PREF_COLOR_SELECTION_BACKGROUND, getSelectedSelectionBackground(false));
    	eaccess().setPrefColor(PREF_COLOR_REQUIRED, getSelectedRequiredColor(false));
    	eaccess().setPrefColor(PREF_COLOR_LOW_REQUIRED, getSelectedLowRequiredColor(false));
    	eaccess().setPrefColor(PREF_COLOR_LOW, getSelectedLowColor(false));
    	eaccess().setPrefColor(PREF_COLOR_OK, getSelectedOKColor(false));
    	eaccess().setPrefColor(PREF_COLOR_LOCK, getSelectedLockColor(false));
    	eaccess().setPrefColor(PREF_COLOR_FOUND, getSelectedFoundColor(false));
    	eaccess().setPrefColor(PREF_COLOR_FOUND_FOCUS, getSelectedFoundFocusColor(false));
    	eaccess().setPrefColor(PREF_COLOR_ASSOC, getSelectedAssocColor(false));
    	eaccess().setPrefColor(PREF_COLOR_CHILD, getSelectedChildColor(false));
    	eaccess().setPrefColor(PREF_COLOR_PARENT, getSelectedParentColor(false));
        eaccess().setPrefBoolean(PREF_COLOR_OVERRIDE, chkOverride.isSelected()); //acl_20031007
        refreshApp();
    }

    private void resetAppearance() {
        btnFore.setSelectedColor(DEFAULT_COLOR_ENABLED_FOREGROUND); //52685
        btnBack.setSelectedColor(DEFAULT_COLOR_ENABLED_BACKGROUND); //52685
        btnSelFore.setSelectedColor(DEFAULT_COLOR_SELECTION_FOREGROUND); //52685
        btnSelBack.setSelectedColor(DEFAULT_COLOR_SELECTION_BACKGROUND); //52685
        btnReq.setSelectedColor(DEFAULT_COLOR_REQUIRED); //52685
        btnLowReq.setSelectedColor(DEFAULT_COLOR_LOW_REQUIRED); //52685
        btnLow.setSelectedColor(DEFAULT_COLOR_LOW); //52685
        btnCOK.setSelectedColor(DEFAULT_COLOR_OK); //52685
        btnLock.setSelectedColor(DEFAULT_COLOR_LOCK); //52685
        btnFound.setSelectedColor(DEFAULT_COLOR_FOUND); //52685
        btnFoundFocus.setSelectedColor(DEFAULT_COLOR_FOUND_FOCUS); //52685
        btnAssoc.setSelectedColor(DEFAULT_COLOR_ASSOC); //52685
        btnChild.setSelectedColor(DEFAULT_COLOR_CHILD); //52685
        btnParent.setSelectedColor(DEFAULT_COLOR_PARENT); //52685
        chkOverride.setSelected(DEFAULT_COLOR_OVERRIDE); //52685
        setOverrideEnabled(DEFAULT_COLOR_OVERRIDE); //52685
        saveAppearance(); //52685
        //vootkur_20031021		refreshApp();
    }

    private void refreshApp() {
        eaccess().refreshDialogAppearance();
        EAccess.FOUND_FOCUS_BORDER.refreshAppearance();
        EAccess.FOUND_BORDER.refreshAppearance();
        eaccess().getLogin().replayDefaultColor(); //52534
        repaintImmediately();
        revalidate();
        repaint();
    }

    /*
     * order
     */
    private void initOrder() {
        ordPnl.loadProfileBox(eaccess().getProfileSet());

        ordBtnPnl.add(bOrdSave);
        ordBtnPnl.add(bOrdDef);
        ordBtnPnl.add(bOrdDefReset);
        ordBtnPnl.add(bOrdReset);

        bOrdSave.addActionListener(this);
        bOrdDef.addActionListener(this);
        bOrdDefReset.addActionListener(this);
        bOrdReset.addActionListener(this);

        bOrdSave.setMnemonic(getChar("save.pref-s"));
        bOrdDef.setMnemonic(getChar("save.pref.default-s"));
        bOrdDefReset.setMnemonic(getChar("reset.pref.default-s"));
        bOrdReset.setMnemonic(getChar("reset.pref-s"));

        bOrdSave.setActionCommand("saveOrder");
        bOrdDef.setActionCommand("saveDefOrder");
        bOrdDefReset.setActionCommand("resetDefOrder");
        bOrdReset.setActionCommand("resetOrder");

        bOrdSave.setEnabled(false); //50525
        bOrdDefReset.setEnabled(false); //50525
        bOrdReset.setEnabled(false); //50525
    }

    private void dereferenceOrder() {
        bOrdSave.removeActionListener(this);
        bOrdDef.removeActionListener(this);
        bOrdDefReset.removeActionListener(this);
        bOrdReset.removeActionListener(this);
    }

    private void loadOrderDefault() {
        ETabable eTab = eaccess().getCurrentTab();
        if (eTab != null) {
            String eType = eTab.getEntityType(2); //52727
            if (eType != null) { //52727
                ordPnl.loadProfile(getActiveProfile(), eType); //52727
            } else { //52727
                eType = eTab.getEntityType(0); //52727
                if (eType != null) { //52727
                    ordPnl.loadProfile(getActiveProfile(), eType); //52727
                } else { //52727
                    eType = eTab.getEntityType(1); //52727
                    ordPnl.loadProfile(getActiveProfile(), eType); //52727
                } //52727
            } //52727
            //52727			ordPnl.loadProfile(getActiveProfile(),eTab.getEntityType(2));
        } else {
            ordPnl.loadProfile(getActiveProfile(), null);
        }
    }

    /*
     * behavior
     */

    private void initBehavior() {
        bhvPnl1.add(lblTabLay);
        lblTabLay.setLabelFor(bhvCombo);
        bhvPnl1.add(bhvCombo);
        bhvPnl1.add(lblRefresh); //cr_3274
        lblRefresh.setLabelFor(bhvRefresh);
        bhvPnl1.add(bhvRefresh); //cr_3274

        bhvPnl1.add(lblClearWF); //cr_4215
        lblClearWF.setLabelFor(bhvClearWF); //cr_4215
        bhvPnl1.add(bhvClearWF); //cr_4215

        bhvPnl1.add(lblLinkType); //link_type
        lblLinkType.setLabelFor(bhvLinkType); //link_type
        bhvPnl1.add(bhvLinkType); //link_type

        bhvPnl0.add("North", bhvPnl1);
        bhvPnl2.add(bhvBookCheck);
        bhvPnl2.add(bhvBookFilter);
        bhvPnl2.add(bhvAutoGC);
        bhvPnl2.add(bhvFlagFrame); //52730
        bhvPnl2.add(bhvTreeXpnd); //cr_3312
        bhvPnl2.add(bhvBehindFire);
        bhvPnl2.add(bhvAutoLink); //auto_link
        bhvPnl2.add(bhvAccessible); //accessible
        bhvPnl2.add(bhvAutoSort); //auto_sort/size
        bhvPnl2.add(ifldSort); //auto_sort/size
        bhvPnl2.add(bhvAutoSize); //auto_sort/size

//        bhvPnl2.add(ifldRowsSize); //auto_sort/size
        EPanel sizePanel = new EPanel(new GridLayout(1, 2, 0, 0));
        sizePanel.add(ifldRowsSize); //auto_sort/size
        sizePanel.add(ifldColsSize); //auto_sort/size
        ifldRowsSize.setToolTipText(getString("autoSize-rows-t")); //auto_sort/size
        ifldColsSize.setToolTipText(getString("autoSize-cols-t")); //auto_sort/size

        bhvPnl2.add(sizePanel); //auto_sort/size

        bhvPnl2.add(bhvAutoUpdate); //auto_update
        bhvPnl2.add(bhvActionXpnd); //xpnd_action
        bhvPnl0.add("South", bhvPnl2);
        bhvBookCheck.setToolTipText(getString("loadBookNew-t"));
        bhvBookCheck.setMnemonic(getChar("loadBookNew-s"));
        bhvAutoGC.setToolTipText(getString("autoGC-t"));
        bhvAutoGC.setMnemonic(getChar("autoGC-s"));
        bhvFlagFrame.setToolTipText(getString("vert.flag.frame-t")); //52730
        bhvFlagFrame.setMnemonic(getChar("vert.flag.frame-s")); //52730
        bhvBookFilter.setToolTipText(getString("bookFilter-t"));
        bhvBookFilter.setMnemonic(getChar("bookFilter-s"));
        bhvTreeXpnd.setToolTipText(getString("tree.xpnd-t")); //cr_3312
        bhvTreeXpnd.setMnemonic(getChar("tree.xpnd-s")); //cr_3312
        bhvBehindFire.setToolTipText(getString("behind.firewall-t"));
        bhvBehindFire.setMnemonic(getChar("behind.firewall-s")); //auto_link
        bhvAutoLink.setToolTipText(getString("auto.link-t")); //auto_link
        bhvAutoLink.setMnemonic(getChar("auto.link-s"));
        bhvAccessible.setMnemonic(getChar("accessible-s")); //accessible
        bhvAccessible.setToolTipText(getString("accessible-t")); //accessible
        bhvAutoUpdate.setMnemonic(getChar("autoUpdate-s")); //auto_update
        bhvAutoUpdate.setToolTipText(getString("autoUpdate-t")); //auto_update
        bhvActionXpnd.setMnemonic(getChar("action.xpnd-s")); //xpnd_action
        bhvActionXpnd.setToolTipText(getString("action.xpnd-t")); //xpnd_action
        ifldSort.setPrepend(false); //auto_sort/size
        ifldRowsSize.setPrepend(false); //auto_sort/size

        ifldColsSize.setPrepend(false); //auto_sort/size

        bhvAutoSort.setMnemonic(getChar("autoSort-s")); //auto_sort/size
        bhvAutoSort.setToolTipText(getString("autoSort-t")); //auto_sort/size
        bhvAutoSort.addChangeListener(this); //auto_sort/size
        bhvAutoSize.setMnemonic(getChar("autoSize-s")); //auto_sort/size
        bhvAutoSize.setToolTipText(getString("autoSize-t")); //auto_sort/size
        bhvAutoSize.addChangeListener(this); //auto_sort/size

        initTabLayout();
        initRefreshType(); //cr_3274
        initClearType(); //cr_4215
        initLinkType(); //link_type
        bhvBookCheck.setOpaque(false);
        bhvBookFilter.setOpaque(false);
        bhvAutoGC.setOpaque(false);
        bhvFlagFrame.setOpaque(false);
        bhvTreeXpnd.setOpaque(false);
        bhvBehindFire.setOpaque(false);
        bhvAutoLink.setOpaque(false); //auto_link
        bhvAccessible.setOpaque(false); //accessible
        bhvActionXpnd.setOpaque(false); //xpnd_action
    }

    private void initTabLayout() {
        String[] sArray = eaccess().getStringArray("tabPos");
        bhvCombo.removeAllItems(); //20030506
        for (int i = 0; i < sArray.length; ++i) {
            bhvCombo.addItem(sArray[i]);
        }
    }

    private void initRefreshType() { //cr_3274
        String[] sArray = eaccess().getStringArray("refType"); //cr_3274
        bhvRefresh.removeAllItems(); //cr_3274
        for (int i = 0; i < sArray.length; ++i) { //cr_3274
            bhvRefresh.addItem(sArray[i]); //cr_3274
        } //cr_3274
    }

    private void initClearType() { //cr_4215
        String[] sArray = eaccess().getStringArray("clrWFType"); //cr_4215
        bhvClearWF.removeAllItems(); //cr_4215
        for (int i = 0; i < sArray.length; ++i) { //cr_4215
            bhvClearWF.addItem(sArray[i]); //cr_4215
        } //cr_4215
    } //cr_4215

    private void initLinkType() { //link_type
        String[] sArray = eaccess().getStringArray("eannounce.link.type"); //link_type
        bhvLinkType.removeAllItems(); //link_type
        for (int i = 0; i < sArray.length; ++i) { //link_type
            bhvLinkType.addItem(getString(sArray[i])); //link_type
        } //link_type
    } //link_type

    private void loadBehavior() {
        int i = -1;
        bhvBookCheck.setSelected(eaccess().getPrefBoolean(PREF_LOAD_BOOKMARK, DEFAULT_LOAD_BOOKMARK));
        bhvBookFilter.setSelected(eaccess().getPrefBoolean(PREF_BOOKMARK_FILTER, DEFAULT_BOOKMARK_FILTER));
        bhvAutoGC.setSelected(eaccess().getPrefBoolean(PREF_AUTO_GC, DEFAULT_AUTO_GC));
        //USRO-R-DMKR-66CHMM		bhvFlagFrame.setSelected(getPrefBoolean(PREF_VERT_FLAG_FRAME,DEFAULT_VERTICAL_USE_FRAME_FLAG));		//52730
        bhvFlagFrame.setSelected(eaccess().getPrefBoolean(PREF_VERT_FLAG_FRAME, EAccess.isArmed(FLOATABLE_FLAG_DEFAULT))); //USRO-R-DMKR-66CHMM
        bhvTreeXpnd.setSelected(eaccess().getPrefBoolean(PREF_TREE_EXPANDED, DEFAULT_PREF_TREE_EXPANDED)); //cr_3312
        bhvBehindFire.setSelected(eaccess().getPrefBoolean(PREF_BEHIND_FIREWALL, DEFAULT_BEHIND_FIREWALL));
        bhvAutoLink.setSelected(eaccess().getPrefBoolean(PREF_AUTO_LINK, EAccess.isArmed(AUTOLINK_ARM_FILE))); //auto_link
        bhvAccessible.setSelected(EAccess.isArmed(ACCESSIBLE_ARM_FILE)); //accessible
        if (MANDATORY_UPDATE) {
			bhvAutoUpdate.setSelected(true);
			bhvAutoUpdate.setEnabled(false);
		} else {
	        bhvAutoUpdate.setSelected(EAccess.isArmed(AUTO_UPDATE_FILE)); //auto_update
		}
        bhvAutoSort.setSelected(eaccess().getPrefBoolean(PREF_AUTO_SORT, DEFAULT_AUTO_SORT)); //auto_sort/size
        bhvAutoSize.setSelected(eaccess().getPrefBoolean(PREF_AUTO_SIZE, DEFAULT_AUTO_SIZE)); //auto_sort/size
        bhvActionXpnd.setSelected(eaccess().getPrefBoolean(PREF_ACTION_EXPANDED, DEFAULT_PREF_ACTION_EXPANDED)); //xpnd_action
        ifldSort.setText(getPrefString(PREF_AUTO_SORT_COUNT, DEFAULT_SORT_SIZE_COUNT)); //auto_sort/size
        ifldSort.setEnabled(bhvAutoSort.isSelected()); //auto_sort/size
        ifldRowsSize.setText(getPrefString(PREF_AUTO_SIZE_COUNT, DEFAULT_SORT_SIZE_COUNT)); //auto_sort/size
        ifldRowsSize.setEnabled(bhvAutoSize.isSelected()); //auto_sort/size

        ifldColsSize.setText(getPrefString(PREF_AUTO_SIZE_COLS_COUNT, DEFAULT_COL_SIZE_COUNT)); //auto_sort/size
        ifldColsSize.setEnabled(bhvAutoSize.isSelected()); //auto_sort/size

        i = eaccess().getPrefInt(PREF_TAB_LAYOUT, 0);
        if (i > 0) {
            i -= 1;
        }
        bhvCombo.setSelectedIndex(i);
        i = eaccess().getPrefInt(PREF_REFRESH_TYPE, DEFAULT_REFRESH_TYPE); //cr_3274
        bhvRefresh.setSelectedIndex(i); //cr_3274

        i = eaccess().getPrefInt(PREF_WF_CLEAR_TYPE, DEFAULT_WF_CLEAR_TYPE); //cr_4215
        bhvClearWF.setSelectedIndex(i); //cr_4215

        i = eaccess().getPrefInt(PREF_LINK_TYPE, DEFAULT_LINK_TYPE); //link_type
        bhvLinkType.setSelectedIndex(i); //link_type
    }

    private void dereferenceBehavior() { }

    private void saveBehavior() {
        int i = (bhvCombo.getSelectedIndex() + 1);
        setPrefInt(PREF_TAB_LAYOUT, i);
        i = bhvRefresh.getSelectedIndex(); //cr_3274
        setPrefInt(PREF_REFRESH_TYPE, i); //cr_3274
        i = bhvClearWF.getSelectedIndex(); //cr_4215
        setPrefInt(PREF_WF_CLEAR_TYPE, i); //cr_4215
        i = bhvLinkType.getSelectedIndex(); //link_type
        setPrefInt(PREF_LINK_TYPE, i); //link_type
        eaccess().getTabbedPane().updateTabPlacement(true);
        eaccess().setPrefBoolean(PREF_LOAD_BOOKMARK, bhvBookCheck.isSelected());
        eaccess().setPrefBoolean(PREF_BOOKMARK_FILTER, bhvBookFilter.isSelected());
        eaccess().setPrefBoolean(PREF_TREE_EXPANDED, bhvTreeXpnd.isSelected()); //cr_3312
        eaccess().setPrefBoolean(PREF_BEHIND_FIREWALL, bhvBehindFire.isSelected());
        adjustFunction(AUTOLINK_ARM_FILE, bhvAutoLink.isSelected()); //auto_link
        eaccess().setPrefBoolean(PREF_AUTO_SORT, bhvAutoSort.isSelected()); //auto_sort/size
        eaccess().setPrefBoolean(PREF_AUTO_SIZE, bhvAutoSize.isSelected()); //auto_sort/size
        setPrefString(PREF_AUTO_SIZE_COUNT, ifldRowsSize.getText().trim()); //auto_sort/size

        setPrefString(PREF_AUTO_SIZE_COLS_COUNT, ifldColsSize.getText().trim()); //auto_size
        setPrefString(PREF_AUTO_SORT_COUNT, ifldSort.getText().trim()); //auto_sort/size
        eaccess().setPrefBoolean(PREF_ACTION_EXPANDED, bhvActionXpnd.isSelected()); //xpnd_action
        eaccess().setAutoGC(bhvAutoGC.isSelected());
        eaccess().setPrefBoolean(PREF_AUTO_GC, eaccess().isAutoGC());
        eaccess().setPrefBoolean(PREF_VERT_FLAG_FRAME, bhvFlagFrame.isSelected()); //52730
        if (adjustFunction(ACCESSIBLE_ARM_FILE, bhvAccessible.isSelected())) { //access
            fyiMsg("msg11017.1"); //access
        } //access
        adjustFunction(AUTO_UPDATE_FILE, bhvAutoUpdate.isSelected()); //auto_update
    }

    private boolean adjustFunction(String _armFile, boolean _enabled) {
        if (_enabled) {
            if (!EAccess.isArmed(_armFile)) {
                eaccess().arm(_armFile);
                return true;
            }
        } else {
            if (EAccess.isArmed(_armFile)) {
                eaccess().disarm(_armFile);
                return true;
            }
        }
        return false;
    }

    private void resetBehavior() {
        clearPref(PREF_TAB_LAYOUT, false);
        clearPref(PREF_LOAD_BOOKMARK, false);
        clearPref(PREF_AUTO_GC, false);
        clearPref(PREF_TREE_EXPANDED, false); //cr_3312
        clearPref(PREF_VERT_FLAG_FRAME, false); //52730
        clearPref(PREF_BOOKMARK_FILTER, false);
        clearPref(PREF_WF_CLEAR_TYPE, false); //cr_4215
        clearPref(PREF_BEHIND_FIREWALL, false);
        clearPref(PREF_AUTO_SIZE, false); //auto_sort/size
        clearPref(PREF_AUTO_SIZE_COUNT, false); //auto_sort/size
        clearPref(PREF_AUTO_SIZE_COLS_COUNT, false);
        clearPref(PREF_AUTO_SORT, false); //auto_sort/size
        clearPref(PREF_AUTO_SORT_COUNT, false); //auto_sort/size
        clearPref(PREF_REFRESH_TYPE, true); //53421
        adjustFunction(ACCESSIBLE_ARM_FILE, PREF_ACCESSIBLE_DEFAULT); //access
        if (MANDATORY_UPDATE) {
	        adjustFunction(AUTO_UPDATE_FILE, true);
		} else {
			adjustFunction(AUTO_UPDATE_FILE, PREF_UPDATE_DEFAULT); //auto_update
		}
        adjustFunction(AUTOLINK_ARM_FILE, EAccess.isArmed(AUTOLINK_DEFAULT)); //auto_link
        loadBehavior();
    }

    /*
     * listeners
     */

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     * @author Anthony C. Liberto
     */
    public void stateChanged(ChangeEvent _ce) {
        Object o = _ce.getSource(); //auto_sort/size
        if (o == bhvAutoSize) { //auto_sort/size
            ifldRowsSize.setEnabled(bhvAutoSize.isSelected()); //auto_sort/size
            ifldColsSize.setEnabled(bhvAutoSize.isSelected()); //auto_sort/size
        } else if (o == bhvAutoSort) { //auto_sort/size
            ifldSort.setEnabled(bhvAutoSort.isSelected()); //auto_sort/size
        } else { //auto_sort/size
            actionPerformed("font_changed");
        } //auto_sort/size
    }

    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        if (isModalBusy()) {
            return;
        }
        setModalBusy(true);
        if (_action.equals("profReset")) {
            //52439			resetDefaultProfile(opID);
            wTree.clearSelection(); //52439
            eaccess().resetDefaultProfile(userName); //52439
            fyiMsg("msg11017.0");
        } else if (_action.equals("profSave")) {
            Profile prof = wTree.getSelectedProfile(); //50525
            if (prof != null) { //50525
            	eaccess().setDefaultProfile(prof);
                //53547				fyiMsg("msg11017.0");
            } //50525
        } else if (_action.equals("lnfSave")) {
            Object o = lnfCombo.getSelectedItem(); //50525
            if (o != null) { //50525
                setLookAndFeel(hashLNF.get(o));
                fyiMsg("msg11017.0");
            } //50525
        } else if (_action.equals("lnfReset")) { //52576
            setLookAndFeel(null); //52576
        } else if (_action.equals("close")) {
            hideDialog();
            setModalBusy(false);
        } else if (_action.equals("tbarCombo")) {
            Object o = tbarCombo.getSelectedItem();
            if (o instanceof ComboItem) {
                tbarAvailList.importToolbar(DefaultToolbarLayout.getAvailLayout((ComboItem) o));
                tbarCurList.importToolbar(getCurrentLayout((ComboItem) o));
            }
        } else if (_action.equals("tbarReset")) {
            Object o = tbarCombo.getSelectedItem();
            if (o instanceof ComboItem) {
                String key = ((ComboItem) o).getStringKey();
                if (key != null) { //50525
                    clearPref(key, true);
                    tbarCurList.importToolbar(getCurrentLayout((ComboItem) o));
                    fyiMsg("msg11017.0");
                } //50525
            }
            clearPref(PREF_ANIMATE_BUTTON,true);
        } else if (_action.equals("tbarSave")) {
            Object o = tbarCombo.getSelectedItem();
            if (o instanceof ComboItem) {
                String key = ((ComboItem) o).getStringKey();
                Object obj = getSerialToolbar((ComboItem) o); //50525
                if (obj != null) { //50525
                	eaccess().setPrefObject(key, obj);
                    fyiMsg("msg11017.0");
                } //50525
            }
            eaccess().setPrefBoolean(PREF_ANIMATE_BUTTON, tbarAnimate.isSelected());
        } else if (_action.equals("printX") || _action.equals("printY")) {
            setPrintSpinEnabled();
        } else if (_action.equals("savePrint")) {
            savePrint();
        } else if (_action.equals("resetPrint")) {
            resetPrint();
        } else if (_action.equals("font_changed")) {
            if (bFontRefresh) {
                updateFont(getFontFace());
            }
        } else if (_action.equals("saveFont")) {
            saveFont();
        } else if (_action.equals("resetFont")) {
            resetFont();
        } else if (_action.equals("enabled.foreground")) {
            getSelectedEnabledForeground(true);
        } else if (_action.equals("enabled.background")) {
            getSelectedEnabledBackground(true);
        } else if (_action.equals("selection.foreground")) {
            getSelectedSelectionForeground(true);
        } else if (_action.equals("selection.background")) {
            getSelectedSelectionBackground(true);
        } else if (_action.equals("required.color")) {
            getSelectedRequiredColor(true);
        } else if (_action.equals("override.color")) { //acl_20031007
            setOverrideEnabled(chkOverride.isSelected()); //acl_20031007
        } else if (_action.equals("low.required.color")) {
            getSelectedLowRequiredColor(true);
        } else if (_action.equals("low.color")) {
            getSelectedLowColor(true);
        } else if (_action.equals("ok.color")) {
            getSelectedOKColor(true);
        } else if (_action.equals("lock.color")) {
            getSelectedLockColor(true);
        } else if (_action.equals("found.color")) {
            getSelectedFoundColor(true);
        } else if (_action.equals("found.focus.color")) {
            getSelectedFoundFocusColor(true);
        } else if (_action.equals("assoc.color")) {
            getSelectedAssocColor(true);
        } else if (_action.equals("child.color")) {
            getSelectedChildColor(true);
        } else if (_action.equals("parent.color")) {
            getSelectedParentColor(true);
        } else if (_action.equals("saveAppearance")) {
            saveAppearance();
            fyiMsg("msg11017.0");
        } else if (_action.equals("resetAppearance")) {
            resetAppearance();
        } else if (_action.equals("saveOrder")) {
            ordPnl.commit(false);
        } else if (_action.equals("saveDefOrder")) {
            ordPnl.commit(true);
        } else if (_action.equals("resetDefOrder")) {
            ordPnl.rollbackDefault();
        } else if (_action.equals("resetOrder")) {
            ordPnl.rollback();
        } else if (_action.equals("save_behave")) {
            saveBehavior();
        } else if (_action.equals("reset_behave")) {
            resetBehavior();
        }
        setModalBusy(false);
    }

    private ToolbarItem[] getCurrentLayout(ComboItem _item) {
        EToolbar ebar = ToolbarController.getEToolbar(_item);
        tbarComboOrient.setSelectedItem(ebar.getOrientation());
        tbarComboAlign.setSelectedItem(ebar.getAlignment());
        tbarCheck.setSelected(ebar.isFloatable());
        tbarAnimate.setSelected(eaccess().getPrefBoolean(PREF_ANIMATE_BUTTON, DEFAULT_ANIMATE_BUTTON));
        return ebar.getToolbarItems();
    }

    /**
     * getSerialToolbar
     * @param _item
     * @return
     * @author Anthony C. Liberto
     */
    private SerialToolbar getSerialToolbar(ComboItem _item) {
        SerialToolbar out = new SerialToolbar(_item);
        out.setOrientation((ComboItem) tbarComboOrient.getSelectedItem());
        out.setAlignment((ComboItem) tbarComboAlign.getSelectedItem());
        out.setFloatable(tbarCheck.isSelected());
        out.setToolbarItems(tbarCurList.exportToolbar());
        return out;
    }

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     * @author Anthony C. Liberto
     */
    public void valueChanged(ListSelectionEvent _lse) {
        if (!_lse.getValueIsAdjusting()) {
            loadSelectedPreference();
        }
    }

    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
        //		initializeOrder();
        loadSelectedPreference();
        list.requestFocus();
    }

    /**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        return getString("pref.panel");
    }

    /**
     * isResizable
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isResizable() {
        return true;
    }

    /*
     * sub classes
     */
    private class PrefTree extends ETree {
    	private static final long serialVersionUID = 1L;
    	/**
         * prefTree
         * @param _model
         * @author Anthony C. Liberto
         */
        private PrefTree(TreeModel _model) {
            super(_model);
            setShowsRootHandles(false);
        }

        /**
         * @see javax.swing.JTree#setExpandedState(javax.swing.tree.TreePath, boolean)
         * @author Anthony C. Liberto
         */
        protected void setExpandedState(TreePath _path, boolean _state) {
            if (!_state) {
                return;
            }
            super.setExpandedState(_path, _state);
        }

        /**
         * setDefaultPath
         * @author Anthony C. Liberto
         */
        private void setDefaultPath() {
            Profile defProf = eaccess().getDefaultProfile();
            if (defProf != null) {
                int id = defProf.getOPWGID();
                ProfNode defNode = wModel.getNodeOPWGID(id);
                if (defNode != null) {
                    setSelectionPath(new TreePath(defNode.getPath()));
                }
            }
        }

        /**
         * getSelectedProfile
         * @return
         * @author Anthony C. Liberto
         */
        private Profile getSelectedProfile() {
            Object o = getLastSelectedPathComponent();
            if (o instanceof ProfNode) {
                return ((ProfNode) o).getProfile();
            }
            return null;
        }

    }

    private class PrefTreeSelectionModel extends DefaultTreeSelectionModel {
    	private static final long serialVersionUID = 1L;
    	/**
         * prefTreeSelectionModel
         * @author Anthony C. Liberto
         */
        private PrefTreeSelectionModel() {}

        /**
         * @see javax.swing.tree.TreeSelectionModel#getSelectionMode()
         * @author Anthony C. Liberto
         */
        public int getSelectionMode() {
            return DefaultTreeSelectionModel.SINGLE_TREE_SELECTION;
        }

        /**
         * @see javax.swing.tree.TreeSelectionModel#addSelectionPaths(javax.swing.tree.TreePath[])
         * @author Anthony C. Liberto
         */
        public void addSelectionPaths(TreePath[] _paths) {}

        /**
         * @see javax.swing.tree.TreeSelectionModel#setSelectionPath(javax.swing.tree.TreePath)
         * @author Anthony C. Liberto
         */
        public void setSelectionPath(TreePath _path) {
            Object o = _path.getLastPathComponent();
            if (o instanceof ProfNode) {
                if (!((ProfNode) o).isSelectable()) {
                    return;
                }
            }
            super.setSelectionPath(_path);
        }
    }

    private class PrefTreeModel extends DefaultTreeModel {
    	private static final long serialVersionUID = 1L;
    	private Vector v = new Vector();
        /**
         * prefTreeModel
         * @param _o
         * @author Anthony C. Liberto
         */
        private PrefTreeModel(TreeNode _o) {
            super(_o);
        }

        /**
         * clear
         * @author Anthony C. Liberto
         */
        private void clear() {
            ProfNode root = (ProfNode) getRoot();
            if (root != null) {
                while (root.getChildCount() > 0) {
                    removeNodeFromParent((MutableTreeNode) root.getFirstChild());
                }
            }
            v.clear();
        }

        /**
         * addNode
         * @param _child
         * @param _parent
         * @param _i
         * @author Anthony C. Liberto
         */
        private void addNode(ProfNode _child, ProfNode _parent, int _i) {
            v.add(_child);
            insertNodeInto(_child, _parent, _i);
        }

        /**
         * getNodeOPWGID
         * @param _i
         * @return
         * @author Anthony C. Liberto
         */
        private ProfNode getNodeOPWGID(int _i) {
            int ii = v.size();
            for (int i = 0; i < ii; ++i) {
                ProfNode node = (ProfNode) v.get(i);
                if (node.getOPWGID() == _i) {
                    return node;
                }
            }
            return null;
        }

        /**
         * dereference
         * @author Anthony C. Liberto
         */
        private void dereference() {
            clear();
            v = null;
        }
    }

    private class ProfNode extends DefaultMutableTreeNode {
    	private static final long serialVersionUID = 1L;
    	private boolean selectable = false;

        /**
         * profNode
         * @param _o
         * @param _selectable
         * @author Anthony C. Liberto
         */
        private ProfNode(Object _o, boolean _selectable) {
            super(_o);
            selectable = _selectable;
        }

        /**
         * isSelectable
         * @return
         * @author Anthony C. Liberto
         */
        private boolean isSelectable() {
            return selectable;
        }

        /**
         * getOPWGID
         * @return
         * @author Anthony C. Liberto
         */
        private int getOPWGID() {
            Profile prof = getProfile();
            if (prof != null) {
                return prof.getOPWGID();
            }
            return -1;
        }

        /**
         * getProfile
         * @return
         * @author Anthony C. Liberto
         */
        private Profile getProfile() {
            if (isSelectable()) {
                return (Profile) getUserObject();
            }
            return null;
        }
    }

    private class PrefItem extends ELabel {
    	private static final long serialVersionUID = 1L;
    	private String key = null;

        /**
         * prefItem
         * @param _key
         * @author Anthony C. Liberto
         */
        private PrefItem(String _key) {
            super();
            key = new String(_key);
            setText(toString());
            setUseDefined(false);
        }

        /**
         * getPrefKey
         * @return
         * @author Anthony C. Liberto
         */
        private String getPrefKey() {
            return key;
        }

        /**
         * equals
         * @param _s
         * @return
         * @author Anthony C. Liberto
         * /
        public boolean equals(String _s) {
            return key.equals(_s);
        }*/

        /**
         * @see java.lang.Object#toString()
         * @author Anthony C. Liberto
         */
        public String toString() {
            return getString(key);
        }
    }
    /*
     50468
    */
    private void initComponents() {
        list.setUseDefined(false);
        bClose.setUseDefined(false);
        bOk.setUseDefined(false);
        bReset.setUseDefined(false);
        wTree.setUseDefined(false);
        lnfCombo.setUseDefined(false);
        lblTbarCombo.setUseDefined(false);
        tbarCombo.setUseDefined(false);
        tbarLabelAvail.setUseDefined(false);
        tbarAvailList.setUseDefined(false);
        tbarLabelCur.setUseDefined(false);
        tbarCurList.setUseDefined(false);
        tbarControl.setUseDefined(false);

        tbarLabelOrient.setUseDefined(false);
        tbarComboOrient.setUseDefined(false);
        tbarLabelAlign.setUseDefined(false);
        tbarComboAlign.setUseDefined(false);
        tbarCheck.setUseDefined(false);
        tbarAnimate.setUseDefined(false);

        lblTabLay.setUseDefined(false);
        bhvCombo.setUseDefined(false);
        lblRefresh.setUseDefined(false); //cr_3274
        bhvRefresh.setUseDefined(false); //cr_3274

        lblClearWF.setUseDefined(false); //cr_4215
        bhvClearWF.setUseDefined(false); //cr_4215

        lblLinkType.setUseDefined(false); //link_type
        bhvLinkType.setUseDefined(false); //link_type

        prntLbl.setUseDefined(false);
        prntChkX.setUseDefined(false);
        prntChkY.setUseDefined(false);
        prntRdoPortrait.setUseDefined(false);
        prntRdoLandscape.setUseDefined(false);

        fontBox.setUseDefined(false);
        chkBold.setUseDefined(false);
        chkItalic.setUseDefined(false);

        bord0.setUseDefined(false);
        bord1.setUseDefined(false);
        bord2.setUseDefined(false);
        bord3.setUseDefined(false);

        lblFore.setUseDefined(false);
        lblBack.setUseDefined(false);
        lblSelFore.setUseDefined(false);
        lblSelBack.setUseDefined(false);

        lblFound.setUseDefined(false);
        lblFoundFocus.setUseDefined(false);

        lblAssoc.setUseDefined(false);
        lblChild.setUseDefined(false);
        lblParent.setUseDefined(false);

        lblLock.setUseDefined(false);
        lblLow.setUseDefined(false);
        lblLowReq.setUseDefined(false);
        lblCOK.setUseDefined(false);
        lblReq.setUseDefined(false);

        bOrdSave.setUseDefined(false);
        bOrdDef.setUseDefined(false);
        bOrdDefReset.setUseDefined(false);
        bOrdReset.setUseDefined(false);
    }

    private boolean isDisplayableFont(Font _f) {
        return (_f.canDisplayUpTo(_f.getFontName())) < 0;
    }

    /*
     acl_20040928
     */
    private void loadLookAndFeels() {
        //String strPrefix = null;
        String[] strForm = null;
        String strDesc = null;
        hashLNF.clear();
        lnfCombo.removeAllItems();
        //strPrefix = getString("nav.form.prefix");
        strForm = Routines.getStringArray(getString("nav.form.available"), ",");
        if (strForm != null) {
            int ii = strForm.length;
            for (int i = 0; i < ii; ++i) {
                strDesc = getString("nav.form.description." + i);
                hashLNF.put(strDesc, strForm[i]);
                lnfCombo.addItem(strDesc);
            }
        }
        packDialog();
    }

    /*
     auto_sort/size

     */

    private void issueNonDigitWarning() {
        showError("msg5021.0");
    }

    private void issueLengthWarning() {
        showError("msg5021.1");
    }
    private static class ProfComparator implements Comparator
    {
        public int compare(Object o1, Object o2) {
            if (o1 instanceof Profile && o2 instanceof Profile) {
                return compareTarget((Profile) o1, (Profile) o2);
            }
            return 0;
        }
        private int compareTarget(Profile o1, Profile o2)
        {
            String nfo1 = o1.getWGName() + o1.toString();
            String nfo2 = o2.getWGName() + o2.toString();
           
            return nfo1.compareTo(nfo2);
        }
    }
}
