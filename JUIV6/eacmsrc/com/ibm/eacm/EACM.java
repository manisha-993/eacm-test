// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.eacm.mw.*;
import com.ibm.eacm.nav.EditPDGFrame;
import com.ibm.eacm.nav.LinkWizard;
import com.ibm.eacm.objects.*;

import com.ibm.eacm.preference.*;
import com.ibm.eacm.edit.EditController;

import java.awt.*;
import java.awt.event.*;

import com.ibm.eacm.tabs.ABRQSActionTab;
import com.ibm.eacm.tabs.CloseTabComponent;
import com.ibm.eacm.tabs.LockActionTab;
import com.ibm.eacm.tabs.NavController;

import com.ibm.eacm.tabs.TabPanel;
import com.ibm.eacm.ui.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.border.*;
import javax.swing.event.MenuKeyListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.*;

import com.ibm.eacm.actions.*;
import com.ibm.eannounce.version.Version;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import com.wallstreetwise.app.jspell.domain.JSpellDictionary;
import com.wallstreetwise.app.jspell.domain.JSpellDictionaryLocal;
import com.wallstreetwise.app.jspell.domain.JSpellException;
import com.wallstreetwise.app.jspell.domain.net.JSpellDictionaryServlet;
import com.wallstreetwise.app.jspell.domain.net.JSpellDictionarySocket;

/**
 * this is the main frame for the EACM application
 * @author Wendy Stimpson
 *
 */
//$Log: EACM.java,v $
//Revision 1.22  2015/03/31 11:24:03  stimpsow
//default to windows LAF
//
//Revision 1.21  2015/02/25 20:24:35  stimpsow
//Add logging to gcaction
//
//Revision 1.20  2015/01/05 19:15:34  stimpsow
//use Theme for background colors
//
//Revision 1.19  2014/06/20 18:14:59  wendy
//list log levels
//
//Revision 1.18  2014/01/15 21:53:52  wendy
//prevent null ptr if exit during load role
//
//Revision 1.17  2013/11/06 21:41:02  wendy
//release actions for menu
//
//Revision 1.16  2013/09/19 16:31:36  wendy
//add abr queue status
//
//Revision 1.15  2013/09/09 21:36:10  wendy
//enable current tab if close edit is cancelled
//
//Revision 1.14  2013/08/22 16:24:24  wendy
//show wait cursor when edit tab is closed
//
//Revision 1.13  2013/07/31 20:01:31  wendy
//can not use a separate thread when invoked from autoupdate
//
//Revision 1.12  2013/07/31 18:07:58  wendy
//select tab when closing so msgs reflect tab
//
//Revision 1.11  2013/07/31 00:47:12  wendy
//correct UAT refresh error
//
//Revision 1.10  2013/07/29 20:07:31  wendy
//try to adjust wg scroll based on screen size
//
//Revision 1.9  2013/07/22 18:32:22  wendy
//Make navigation splitpane divider easier to grab
//
//Revision 1.8  2013/07/22 18:19:00  wendy
//validate after setextendedstate
//
//Revision 1.7  2013/07/18 18:37:02  wendy
//fix compiler warnings
//
//Revision 1.6  2013/05/03 19:06:10  wendy
//prevent null ptr if no menubar exists when exit
//
//Revision 1.5  2013/04/09 17:23:06  wendy
//add boolean to enableallactions and disableallactions action methods
//
//Revision 1.4  2013/03/14 17:36:34  wendy
//disable actions when trying to close a tab
//
//Revision 1.3  2013/02/07 13:37:38  wendy
//log close tab
//
//Revision 1.2  2012/10/26 21:47:59  wendy
//fix copyright date
//
//Revision 1.1  2012/09/27 19:39:26  wendy
//Initial code
//
public class EACM extends JFrame implements EACMGlobals {
	private static final long serialVersionUID = 1L;
	private static final Logger logger, timingLogger;
	private static FileHandler loggerFileHandler = null;
	private static final String PREVTAB_ACTION = "prevTab";
	private static final String NEXTTAB_ACTION = "nextTab";
	private static final String GC_ACTION = "gc";

	private static EACM eacmFrame = null;
	private static SplashScreen splash = null;
	public static EACMTheme EACM_THEME = new EACMTheme();

	// preserve old stdout/stderr streams in case they might be useful
	public static PrintStream stdout;
	public static PrintStream stderr;

	private static final String GIF_RSRC[] = new String[]{
		"eannounce.selectedIcon", "dot.gif",
		"eannounce.unselectedIcon", "dot_opaque.gif",
		"RadioButtonMenuItem.checkIcon", "empty.gif",

		"eannounce.navtree.openIcon", "open.gif",
		"eannounce.navtree.closedIcon", "closed.gif",
		"eannounce.navtree.leafIcon", "leaf.gif"
	};

	private LockMgr lockMgr = new LockMgr();
	private LoginMgr loginMgr = null;
	private ExitAction exitAction = new ExitAction();
	private Hashtable<String, EACMAction> actionTbl = new Hashtable<String, EACMAction>();  // hang onto shared actions in one place
	private JSpellDictionary dictionary = null;

	private JPanel pMain = null;
	private TabbedMenuPane tabbedMenuPane = null;
	private FindRepMgr findRepMgr = new FindRepMgr();

	private JPanel pnlInstruct = null;

	//private EMLabel lblLastLoginDate = null; // CQ14860

	private JMenuBar mBar = null;
	private JMenu mnuFile = null;
	private JMenu mnuSystem = null;
	private JMenu mnuHelp = null;
	private JMenu mnuWorkgroup = null;
	private Vector<Window> userWindowsVct = new Vector<Window>();

	// private FileOutputStream fileOut = null;
	private PrintStream pStream = null;

	private StatusToolbar statusToolBar = null;

	static {
		logger = Logger.getLogger(APP_PKG_NAME);
		logger.setLevel(PrefMgr.getLoggerLevel(APP_PKG_NAME, Level.INFO));

		// separate logger for timing, can be turned on in preferences
		timingLogger= Logger.getLogger(TIMING_LOGGER);
		timingLogger.setLevel(PrefMgr.getLoggerLevel(TIMING_LOGGER, Level.OFF));

		setupLogging();
	}

	/***************
	 * using windows lnf
List.font             javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
TableHeader.font      javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
Panel.font            javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
TextArea.font         javax.swing.plaf.FontUIResource[family=Monospaced,name=Monospaced,style=plain,size=13]
ToggleButton.font     javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
ComboBox.font         javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
ScrollPane.font       javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
Spinner.font          javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
RadioButtonMenuItem.fontjavax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=13]
Slider.font           javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
EditorPane.font       javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
OptionPane.font       javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=13]
ToolBar.font          javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=13]
Tree.font             javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
CheckBoxMenuItem.font javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=13]
TitledBorder.font     javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
Table.font            javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
MenuBar.font          javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=13]
PopupMenu.font        javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=13]
Label.font            javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
MenuItem.font         javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=13]
TextField.font        javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
TextPane.font         javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
CheckBox.font         javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
ProgressBar.font      javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
FormattedTextField.font javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
ColorChooser.font     javax.swing.plaf.FontUIResource[family=Dialog,name=Dialog,style=plain,size=12]
Menu.font             javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=13]
PasswordField.font    javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
Viewport.font         javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
TabbedPane.font       javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
RadioButton.font      javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
ToolTip.font          javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=13]
Button.font           javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
	 * @param font
	 */
	public static void updateUIwithFontPref(Font font){
		updateUIwithFontPref(font, getEACM());
	}
	public static void updateUIwithFontPref(Font font, EACM eacm){
		String uifonts[] = new String[]{
				"Label.font","TextField.font", "ComboBox.font", "CheckBox.font",
				"RadioButton.font", "Button.font", "Spinner.font",
				"FormattedTextField.font", "List.font", "Panel.font",
				"Tree.font", "ScrollPane.font", "TableHeader.font", "Table.font",
				"Viewport.font", "TabbedPane.font", "TextArea.font"
		};

		Font boldversion = null;
		for(int i=0;i<uifonts.length;i++){
			Font old = UIManager.getFont(uifonts[i]);
			if(old.isBold()){
				if(boldversion==null){
					boldversion = new FontUIResource(font.deriveFont(Font.BOLD));
				}
				UIManager.put(uifonts[i], boldversion);
			}else{
				UIManager.put(uifonts[i], font);
			}
		}
		boldversion = null;
		/*
ToggleButton.font     javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
Slider.font           javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
EditorPane.font       javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
TitledBorder.font     javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
TextPane.font         javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
ProgressBar.font      javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
FormattedTextField.font javax.swing.plaf.FontUIResource[family=Tahoma,name=Tahoma,style=plain,size=11]
		 */
		//UIManager.put("PasswordField.font", font); dont change size, it is on an image

		SwingUtilities.updateComponentTreeUI(eacm);
		if (!eacm.instructionsShowing()) 	{
			SwingUtilities.updateComponentTreeUI(eacm.pnlInstruct);
		}

		EACMFrame findframe = eacm.getFindRepFrame();
		if (findframe != null) {
			SwingUtilities.updateComponentTreeUI(findframe);

			Dimension prefD = findframe.getPreferredSize();
			Dimension curD = findframe.getSize();
			// font chgs require different sizes
			if (curD.width != prefD.width || curD.height != prefD.height){
				findframe.setSize(prefD);
			}
		}else{
			SwingUtilities.updateComponentTreeUI(eacm.findRepMgr);
		}
		eacm.findRepMgr.addListeners();

		if ((eacm.getExtendedState()!=Frame.MAXIMIZED_BOTH)){
			Dimension prefD = eacm.getPreferredSize();
			Dimension curD = eacm.getSize();
			// font chgs require different sizes
			if (curD.width != prefD.width || curD.height != prefD.height){
				eacm.setSize(prefD);
			}
		}
	}
	/**
	 * @param color
MenuBar.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
RadioButton.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
CheckBox.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
ProgressBar.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
Label.background = 		javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
Spinner.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
Viewport.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
TabbedPane.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
MenuItem.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
TableHeader.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
ScrollPane.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
Button.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
Panel.background = 		javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
ColorChooser.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
SplitPane.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
CheckBoxMenuItem.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
Slider.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
ToggleButton.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
PopupMenu.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
ScrollBar.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
OptionPane.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
Menu.background = 		javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
ToolBar.background = 	javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]
RadioButtonMenuItem.background = javax.swing.plaf.ColorUIResource[r=212,g=208,b=200]

Desktop.background = 	javax.swing.plaf.ColorUIResource[r=58,g=110,b=165]
TextArea.background = 	javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
Table.background = 		javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
PasswordField.background = javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
TextPane.background = 	javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
ToolTip.background = 	javax.swing.plaf.ColorUIResource[r=255,g=255,b=225]
List.background = 		javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
Tree.background = 		javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
TextField.background = 	javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
Separator.background = 	javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
ComboBox.background = 	javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
FormattedTextField.background = javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
EditorPane.background = javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void updateUIWithColorPref() throws UnsupportedLookAndFeelException{
		updateUIWithColorPref(getEACM());
	}
	public static void updateUIWithColorPref(EACM eacm) throws UnsupportedLookAndFeelException{
		Color forgrd = null;
		if(ColorPref.canOverrideColor()){ // allow overrides
			forgrd = ColorPref.getForegroundColor();
		}else{
			forgrd = ColorPref.DEFAULT_COLOR_ENABLED_FOREGROUND;
		}

		Color curColor = UIManager.getColor("Panel.foreground");
		if (!curColor.equals(forgrd)) {
			Object[] keyValueList = {
					"Label.foreground", forgrd,
					"Button.foreground", forgrd,
					"Panel.foreground", forgrd,
					"MenuBar.foreground", forgrd,
					"Menu.foreground", forgrd,
					"MenuItem.foreground", forgrd,
					"CheckBox.foreground", forgrd,
					"TabbedPane.foreground", forgrd,
					"SplitPane.foreground", forgrd,
					"TableHeader.foreground", forgrd,
					"ToolBar.foreground", forgrd,
					"ScrollBar.foreground", forgrd,
					"ScrollPane.foreground", forgrd,
					"OptionPane.foreground", forgrd,
					"Viewport.foreground", forgrd,
					"ColorChooser.foreground", forgrd,
					"RadioButton.foreground", forgrd    		
			};

			UIManager.getDefaults().putDefaults(keyValueList);
		}

		if(UIManager.getColor(PREF_COLOR_ASSOC)!=null){ // if null eacm is initializing
			if (!ColorPref.getAssocColor().equals(UIManager.getColor(PREF_COLOR_ASSOC))){
				UIManager.put(PREF_COLOR_ASSOC,ColorPref.getAssocColor());
			}

			if (!ColorPref.getChildColor().equals(UIManager.getColor(PREF_COLOR_CHILD))){
				UIManager.put(PREF_COLOR_CHILD,ColorPref.getChildColor());
			}

			if (!ColorPref.getParentColor().equals(UIManager.getColor(PREF_COLOR_PARENT))){
				UIManager.put(PREF_COLOR_PARENT,ColorPref.getParentColor());
			}

			if (!ColorPref.getLockColor().equals(UIManager.getColor(PREF_COLOR_LOCK))){
				UIManager.put(PREF_COLOR_LOCK,ColorPref.getLockColor());
			}

			if (!ColorPref.getFoundColor().equals(UIManager.getColor(PREF_COLOR_FOUND))){
				UIManager.put(PREF_COLOR_FOUND,ColorPref.getFoundColor());
			}

			if (!ColorPref.getWUBackgroundColor().equals(UIManager.getColor(PREF_COLOR_WU_BG))){
				UIManager.put(PREF_COLOR_WU_BG,ColorPref.getWUBackgroundColor());
			}
		}

		if(ColorPref.canOverrideColor()){ // allow overrides 
			// turn off bold fonts
			UIManager.put("swing.boldMetal", Boolean.FALSE);

			MetalLookAndFeel.setCurrentTheme(EACM_THEME); // need to use a theme to be able to change backgrounds
			//only metal supports themes

			// re-install the Metal Look and Feel
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		}else {
			UIManager.setLookAndFeel(new WindowsLookAndFeel()); // default to windows LAF
		}

		updateComponentsUI(eacm);
	}

	public LockMgr getLockMgr() {
		return lockMgr;
	}
	public static EACM getEACM() {
		return eacmFrame;
	}

	/**
	 * is the instruction panel showing - called from exit action
	 * @return
	 */
	public boolean instructionsShowing(){
		if(pnlInstruct==null){
			return false;
		}
		return pnlInstruct.isShowing();
	}

	/**
	 * Update the ComponentUIs for all Components. This
	 * needs to be invoked for all windows.
	 * @param eacm
	 */
	public static void updateComponentsUI(EACM eacm){
		SwingUtilities.updateComponentTreeUI(eacm);
		if (!eacm.instructionsShowing())	{
			SwingUtilities.updateComponentTreeUI(eacm.pnlInstruct);
		}
		SwingUtilities.updateComponentTreeUI(eacm.loginMgr);
		SwingUtilities.updateComponentTreeUI(eacm.tabbedMenuPane);

		for(int i=0;i<eacm.tabbedMenuPane.getTabCount();i++){
			// menubar isnt updated on tabs that are not selected
			eacm.tabbedMenuPane.getTabPanel(i).updateComponentsUI();
		}

		if(eacm.mBar !=null){
			SwingUtilities.updateComponentTreeUI(eacm.mBar);
			for(int i2=0;i2<eacm.mBar.getMenuCount();i2++){
				updateMenuUI(eacm.mBar.getMenu(i2));
			} 
		}
		if(eacm.mnuWorkgroup!=null){
			updateMenuUI(eacm.mnuWorkgroup);
		}

		SwingUtilities.updateComponentTreeUI(eacm.findRepMgr);
		eacm.findRepMgr.addListeners();

		UIManager.put(ROW_REND_COLOR,UIManager.getColor("Panel.background").darker()); // needed to update editors
	}
	/**
	 * @param key
	 * @return
	 */
	public EACMAction getGlobalAction(String key){
		return actionTbl.get(key);
	}
	/**
	 * create the application frame
	 */
	private EACM() {
		loadGifs();

		createInstructPanel();

		createActions();

		setPercent(40);

		setupFolders();
		setPercent(30);

		initLogin();

		setPercent(70);

		setProperties();

		init();
		SwingUtilities.updateComponentTreeUI(this);
		setPercent(90);

		setVisible(true);
		this.toFront();

		setPercent(100);

		LoggingPrefMgr.listLogLevels();
	}

	private void createActions(){
		actionTbl.put(LOGOFF_ACTION, new LogOffAction());
		actionTbl.put(CLOSEALL_ACTION, new CloseAllAction());
		actionTbl.put(CLOSETAB_ACTION, new CloseTabAction());
		actionTbl.put(PAGESETUP_ACTION, new PageSetupAction());
		actionTbl.put(ABOUT_ACTION, new AboutAction());
		actionTbl.put(FMI_ACTION, new FMIAction());
		actionTbl.put(CONTENTS_ACTION, new ContentsAction());
		actionTbl.put(ATTRHELP_ACTION, new AttrHelpAction());
		actionTbl.put(MEMORY_ACTION, new MemoryAction());
		actionTbl.put(BREAK_ACTION, new BreakAction());
		actionTbl.put(PREF_ACTION, new PrefAction());
		actionTbl.put(RESETDATE_ACTION, new ResetDateAction());
		actionTbl.put(GC_ACTION, new GcAction());
		actionTbl.put(CHKUP_ACTION, new SWUpdateMgr());
		actionTbl.put(PREVTAB_ACTION, new PrevTabAction());
		actionTbl.put(NEXTTAB_ACTION, new NextTabAction());

		actionTbl.put(EXIT_ACTION, exitAction);
	}

	private void createInstructPanel(){
		JLabel lblInstruct = new JLabel(Utils.getResource("intro1")); //html splits it automatically
		lblInstruct.setLabelFor(pMain);
		lblInstruct.setHorizontalAlignment(SwingConstants.CENTER);

		pnlInstruct = new JPanel(null);
		GroupLayout layout = new GroupLayout(pnlInstruct);
		pnlInstruct.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(lblInstruct);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();

		topToBottom.addComponent(lblInstruct);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);
	}
	private void initLogin() {
		setIconImage(Utils.getImage(DEFAULT_ICON));

		loginMgr = new LoginMgr(exitAction,Utils.isArmed(LOCATION_CHOOSER_ARM_FILE));//need tbase and timer for init()

		tabbedMenuPane = new TabbedMenuPane();

		initDictionary();

		setApplicationTitle();

		pMain = new JPanel(new BorderLayout(5, 5));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(pMain,BorderLayout.CENTER);

		addWindowListener(exitAction);

		pMain.add(loginMgr,BorderLayout.CENTER);

		setResizable(false);// intro doesnt resize

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		pack();
		center();
	}
	/**
	 * setup the dictionary
	 */
	private void initDictionary() {
		String dictionaryType = EACMProperties.getProperty("dictionary.type","notSpecified");
		if (dictionaryType.equals("local")) {
			String sLocation = RESOURCE_DIRECTORY + EACMProperties.getProperty("dictionary.name");
			dictionary = new JSpellDictionaryLocal(sLocation);
			((JSpellDictionaryLocal) dictionary).setDictionaryFileName(sLocation);
		} else if (dictionaryType.equals("servlet")) {
			dictionary = new JSpellDictionaryServlet();
			((JSpellDictionaryServlet) dictionary).setURL(EACMProperties.getProperty("dictionary.url"));
		} else if (dictionaryType.equals("socket")) {
			dictionary = new JSpellDictionarySocket();
			((JSpellDictionarySocket) dictionary).setHost(EACMProperties.getProperty("dictionary.host"));
			String port = EACMProperties.getProperty("dictionary.port", "1557");
			((JSpellDictionarySocket) dictionary).setPort(Integer.parseInt(port));
		}
		if (dictionary != null) {
			try {
				dictionary.open();
				if(!dictionary.getDictionaryReady()){
					dictionary.close();
					dictionary = null;
				}
			} catch (JSpellException jse) {
				jse.printStackTrace();
				dictionary.close();
				dictionary = null;
				//msg11025.2 =dictionary open failed
				com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg11025.2"));
				return;
			}
			/* should the dictionary learn words */
			dictionary.setLearnWords(false);

			/* are words with numbers errors */
			dictionary.setIgnoreWordsWithNumbers(true);

			/* should the first word of a sentence be checked for proper capitalization */
			dictionary.setIgnoreFirstCaps(false);

			/* Ignore upper case words */
			dictionary.setIgnoreUpper(false);

			/* check text and return suggestions using ALL upper case */
			dictionary.setForceUpperCase(false);

			/* special case scenerio */
			dictionary.setIgnoreIrregularCaps(false);

			/* are double words errors? */
			dictionary.setIgnoreDoubleWords(false);
		}
	}
	private void init() {

		// CQ14860
		//lblLastLoginDate = new EMLabel("Last Login: ");
		//lblLastLoginDate.setHorizontalAlignment(SwingConstants.LEFT);
		//lblLastLoginDate.setBorder(new TitledBorder(""));
		// end CQ14860

		initMenu();

		statusToolBar = new StatusToolbar(); // not used until profile is selected, must be done after properties are setup
	}

	private void showInstruct() {
		pMain.add(pnlInstruct,BorderLayout.CENTER);
		//pMain.add(lblLastLoginDate,BorderLayout.SOUTH); // CQ14860
		pMain.getAccessibleContext().setAccessibleName(Utils.getResource("accessible.intro"));
		pMain.requestFocusInWindow();//.requestFocus();
		//      getAccessibleContext().setAccessibleName(lblInstruct.getText());
		//      getAccessibleContext().setAccessibleDescription(lblInstruct.getText());
	}

	private void hideInstruct() {
		pMain.remove(pnlInstruct);
		//pMain.remove(lblLastLoginDate); //CQ14860
		pMain.getAccessibleContext().setAccessibleName(null);
	}

	private void center() {
		Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(((sSize.width - getWidth()) / 2), ((sSize.height - getHeight()) / 2));
	}

	/*
	 * set properties for e-announce
	 */
	private void setProperties() {
		if(ColorPref.canOverrideColor()){
			try {
				updateUIWithColorPref(this);
			} catch (UnsupportedLookAndFeelException e) {
				logger.log(Level.WARNING,e.getMessage(),e);
			}
		}

		updateUIwithFontPref(FontPref.getPreferredFont(),this);

		if (Utils.isArmed(RMI_VERBOSE_ARM_FILE)) {
			System.setProperty("sun.rmi.client.logCalls", "true");
			System.setProperty("sun.rmi.server.logLevel", "VERBOSE");
			System.setProperty("sun.rmi.transport.logLevel", "VERBOSE");
			System.setProperty("sun.rmi.transport.tcp.logLevel", "VERBOSE");
		}
		System.setProperty("sun.rmi.transport.connectionTimeout",
				EACMProperties.getProperty("rmi.connect.timeout", "30000"));

		Object[] keyValueList = {
				FOCUS_BORDER_KEY, new LineBorder(Color.WHITE,1),
				WUFOCUS_BORDER_KEY, new LineBorder(Color.black, 1),
				NOFOCUS_BORDER_KEY, new EmptyBorder(1, 1, 1, 1),
				SELECTED_BORDER_KEY, new UnderlineBorder(Color.gray),
				LOWERED_BORDER_KEY, new BevelBorder(BevelBorder.LOWERED),
				RAISED_BORDER_KEY, new BevelBorder(BevelBorder.RAISED),
				ETCHED_BORDER_KEY, new EtchedBorder(EtchedBorder.LOWERED),
				EMPTY_BORDER_KEY, new EmptyBorder(2, 0, 2, 0),
				UNDERLINE_BORDER_KEY, new UnderlineBorder(Color.gray),
				FOUND_FOCUS_BORDER_KEY, com.ibm.eacm.ui.FoundLineBorder.FOUND_FOCUS_BORDER,
				FOUND_BORDER_KEY, com.ibm.eacm.ui.FoundLineBorder.FOUND_BORDER,

				PREF_COLOR_ASSOC,ColorPref.getAssocColor(),
				PREF_COLOR_CHILD,ColorPref.getChildColor(),
				PREF_COLOR_PARENT,ColorPref.getParentColor(),
				PREF_COLOR_LOCK,ColorPref.getLockColor(),
				PREF_COLOR_FOUND,ColorPref.getFoundColor(),
				PREF_COLOR_WU_BG,ColorPref.getWUBackgroundColor(),

				ROW_REND_COLOR,UIManager.getColor("Panel.background").darker(),

				MULTILINETOOLTIP_UI,new MultiLineToolTipUI(),

				TOOLBAR_BUTTON_DIM, new Dimension(22, 22), // make all toolbar buttons the same size

				"eannounce.minimum", new Dimension(0, 0),

				"eannounce.tab.selectedBorder", new LineBorder(Color.blue, 2),
				"eannounce.tab.nonSelectedBorder", new EmptyBorder(2, 2, 2, 2)
		};

		UIManager.getDefaults().putDefaults(keyValueList);

		String sVersion =  Utils.getResource("eannounce.version",(Object[])getVersionParms());
		EACMProperties.setProperty("eannounce.version", " " + Utils.getResource("appName") + " " + sVersion + " " +
				Utils.getResource("codeName"));
		EACMProperties.setProperty("eannounce.jar.version", SWUpdateMgr.getJarVersion());
	}

	/**
	 * construct the GUI immediately, without waiting for the images to finish loading.
	 */
	private void loadGifs(){
		/*     UIManager.put("eannounce.selectedIcon", Utils.getImageIcon("dot.gif"));
        UIManager.put("eannounce.unselectedIcon", Utils.getImageIcon("dot_opaque.gif"));
        UIManager.put("RadioButtonMenuItem.checkIcon", Utils.getImageIcon("empty.gif"));

    	UIManager.put("eannounce.navtree.openIcon", Utils.getImageIcon("open.gif"));
    	UIManager.put("eannounce.navtree.closedIcon", Utils.getImageIcon("closed.gif"));
    	UIManager.put("eannounce.navtree.leafIcon", Utils.getImageIcon("leaf.gif"));*/

		(new SwingWorker<ImageIcon[], Object>() {
			protected ImageIcon[] doInBackground() {
				for (int i = 0; i < GIF_RSRC.length; i+=2) {
					UIManager.put(GIF_RSRC[i], Utils.getImageIcon(GIF_RSRC[i+1]));
				}
				return null;
			}
		}).execute();
	}

	private static String[] getVersionParms() {
		Date bldDate = DateRoutines.parseDate(BUILD_DATE, Version.getDate());
		String[] parms = { DateRoutines.formatDate(RELEASE_DATE, bldDate),
				DateRoutines.formatDate(FORMATTED_DATE, bldDate),
				DateRoutines.formatDate(BUILD_DATE, bldDate),
				DateRoutines.formatDate(DATE_TIME_ONLY, bldDate)};
		return parms;
	}
	private void setupFolders(){
		String[] EACM_FOLDERS= new String[] {TEMP_FOLDER,SAVE_FOLDER,
				LOGS_FOLDER,RESOURCE_FOLDER,FUNCTION_FOLDER,CACHE_FOLDER};

		File folder = new File(UPDATE_FOLDER);
		if (folder.exists()) {
			// rename any autoUpdate jar
			File tmpjar = new File(UPDATE_DIRECTORY + "eaServer.tmp");
			if (tmpjar.exists()) {
				File curjar = new File(UPDATE_DIRECTORY + "eaServer.jar");
				if (curjar.delete()) {
					tmpjar.renameTo(curjar);
				}
			}
		} else {
			folder.mkdirs();
		}

		for (int i=0; i<EACM_FOLDERS.length; i++){
			folder = new File(EACM_FOLDERS[i]);
			if (!folder.exists()) {
				folder.mkdirs();
			}
		}
	}

	/**
	 * reportProperties
	 *
	 */
	public void reportProperties() {
		Toolkit toolKit = null;
		Dimension s = null;

		StringBuilder sb = new StringBuilder(RETURN);
		sb.append("LOGS_DIRECTORY: " + LOGS_DIRECTORY+RETURN);
		sb.append("HOME_DIRECTORY: " + HOME_DIRECTORY+RETURN);
		sb.append("TEMP_DIRECTORY: " + TEMP_DIRECTORY+RETURN);
		sb.append("JAR_DIRECTORY: " + JAR_DIRECTORY+RETURN);
		sb.append("RESOURCE_DIRECTORY: " + RESOURCE_DIRECTORY+RETURN);
		sb.append("FUNCTION_DIRECTORY: " + FUNCTION_DIRECTORY+RETURN);
		sb.append("CACHE_DIRECTORY: " + CACHE_DIRECTORY+RETURN);
		sb.append("MW_DESC: "+LoginMgr.getMWODescription()+RETURN);
		sb.append("MW_OBJECT: "+LoginMgr.getMWOName()+RETURN);
		sb.append("MW_IP: "+LoginMgr.getMWOIP()+RETURN);
		sb.append("MW_PORT: "+LoginMgr.getMWOPort()+RETURN);
		sb.append("REPORT_PREFIX: "+LoginMgr.getMWORptPrefix()+RETURN);

		sb.append(displayProperty("eannounce.mode.batch")+RETURN);
		sb.append(displayProperty("file.encoding")+RETURN);
		sb.append(displayProperty("user.home")+RETURN);
		sb.append(displayProperty("user.name")+RETURN);
		sb.append(displayProperty("user.language")+RETURN);
		sb.append(displayProperty("user.region")+RETURN);
		sb.append(displayProperty("user.country")+RETURN);
		sb.append(displayProperty("user.timezone")+RETURN);
		sb.append(displayProperty("user.dir")+RETURN);
		sb.append(displayProperty("os.arch")+RETURN);
		sb.append(displayProperty("os.name")+RETURN);
		sb.append(displayProperty("os.version")+RETURN);
		sb.append(displayProperty("sun.os.patch.level")+RETURN);
		sb.append(displayProperty("sun.cpu.isalist")+RETURN);
		sb.append(displayProperty("java.class.version")+RETURN);
		sb.append(displayProperty("java.rmi.server.hostname")+RETURN);
		sb.append(displayProperty("sun.arch.data.model")+RETURN);
		sb.append(displayProperty("java.compiler")+RETURN);
		sb.append(displayProperty("java.library.path")+RETURN);
		sb.append(displayProperty("java.vm.name")+RETURN);
		sb.append(displayProperty("java.specification.version")+RETURN);
		sb.append(displayProperty("java.vendor")+RETURN);
		sb.append(displayProperty("java.vm.version")+RETURN);
		sb.append(displayProperty("java.runtime.version")+RETURN);
		sb.append(displayProperty("java.vm.info")+RETURN);
		sb.append(displayProperty("java.fullversion")+RETURN);
		sb.append(displayProperty("java.runtime.name")+RETURN);
		sb.append(displayProperty("java.class.path")+RETURN);
		sb.append(displayProperty("java.home")+RETURN);
		sb.append(displayProperty("sun.boot.library.path")+RETURN);
		sb.append(displayProperty("sun.io.unicode.encoding")+RETURN);
		sb.append(displayProperty("lax.nl.current.vm")+RETURN);
		sb.append(displayProperty("lax.nl.java.option.java.heap.size.initial")+RETURN);
		sb.append(displayProperty("lax.nl.java.option.java.heap.size.max")+RETURN);
		sb.append(displayProperty("lax.nl.java.option.stack.size.initial")+RETURN);
		sb.append(displayProperty("lax.nl.java.option.stack.size.max")+RETURN);
		sb.append(displayProperty("lax.root.install.dir")+RETURN);
		sb.append(displayProperty("lax.application.name")+RETURN);
		sb.append(displayProperty("lax.generated.launcher.name")+RETURN);
		sb.append(displayProperty("lax.nl.java.option.additional")+RETURN);
		sb.append(displayProperty("lax.version")+RETURN);
		sb.append(displayProperty("lax.class.path")+RETURN);
		sb.append(displayProperty("lax.main.class")+RETURN);
		sb.append(displayProperty("lax.main.method")+RETURN);
		sb.append(displayProperty("lax.command.line.args")+RETURN);
		sb.append(displayProperty("lax.resource.dir")+RETURN);
		sb.append(displayProperty("lax.nl.java.compiler")+RETURN);
		sb.append(displayProperty("lax.nl.win32.java.compiler")+RETURN);
		sb.append(displayProperty("lax.user.dir")+RETURN);
		sb.append(displayProperty("lax.nl.java.launcher.main.method")+RETURN);
		sb.append(displayProperty("lax.nl.java.launcher.main.class")+RETURN);
		sb.append(displayProperty("lax.nl.java.option.verbose")+RETURN);
		sb.append(displayProperty("lax.nl.java.option.garbage.collection.extent")+RETURN);
		sb.append(displayProperty("lax.nl.java.option.garbage.collection.background.thread")+RETURN);
		sb.append(displayProperty("lax.nl.java.option.debugging")+RETURN);
		sb.append(displayProperty("lax.nl.env.path")+RETURN);
		sb.append(displayProperty("lax.java.compiler")+RETURN);
		sb.append(displayProperty("lax.stdout.redirect")+RETURN);
		sb.append(displayProperty("lax.stderr.redirect")+RETURN);
		sb.append(displayProperty("lax.nl.env.CLASSPATH")+RETURN);
		sb.append(displayProperty("lax.nl.env.PROCESSOR_ARCHITECTURE")+RETURN);
		sb.append(displayProperty("lax.nl.env.PROCESSOR_IDENTIFIER")+RETURN);
		sb.append(displayProperty("lax.nl.env.PROCESSOR_LEVEL")+RETURN);
		sb.append(displayProperty("lax.nl.env.PROCESSOR_REVISION")+RETURN);
		sb.append(displayProperty("lax.nl.env.NUMBER_OF_PROCESSORS")+RETURN);
		sb.append(displayProperty("lax.nl.env.OS")+RETURN);
		sb.append(displayProperty("lax.nl.env.COMPUTERNAME")+RETURN);
		sb.append(displayProperty("lax.nl.env.LOGONSERVER")+RETURN);
		sb.append(displayProperty("lax.nl.env.USERDOMAIN")+RETURN);
		sb.append(displayProperty("lax.nl.env.USERPROFILE")+RETURN);
		sb.append(displayProperty("lax.nl.env.windir")+RETURN);
		try {
			InetAddress add = InetAddress.getLocalHost();
			if (add != null) {
				InetAddress[] addra = InetAddress.getAllByName(add.getHostName());
				if (addra != null) {
					int ii = addra.length;
					for (int i = 0; i < ii; ++i) {
						sb.append("Local Internet Address[" + i + "]: " + addra[i].getHostAddress()+RETURN);
					}
				}
			}
		} catch (Exception x) {

		}

		sb.append("** properties complete **"+RETURN);
		toolKit = Toolkit.getDefaultToolkit();
		sb.append("Screen Res :  " + toolKit.getScreenResolution()+RETURN);
		s = toolKit.getScreenSize();
		sb.append("Screen Size:  " + s.width + " x " + s.height);
		logger.log(Level.INFO,sb.toString());
	}

	private String displayProperty(String key) {
		return "System.Property: " + key + " = " + EACMProperties.getProperty(key);
	}

	private static void setupLogging(){
		// preserve old stdout/stderr streams in case they might be useful
		stdout = System.out;
		stderr = System.err;
		try {
			// any errors thrown here are fatal
			Calendar cal = Calendar.getInstance();
			String datetime =DateRoutines.formatDate(DATE_TIME_ONLY, cal.getTime());
			String filename = LOGS_DIRECTORY+datetime+LOG_EXTENSION;
			loggerFileHandler = new FileHandler(filename);
			loggerFileHandler.setFormatter(new LogFormatter());
			Logger.getLogger("").addHandler(loggerFileHandler);

			Handler[] handlers = Logger.getLogger("").getHandlers();
			for (int i = 0; i < handlers.length; i++) {
				// remove console handler, will be redirecting mw logging here, it uses system.out!
				if (handlers[i] instanceof ConsoleHandler) {
					Logger.getLogger("").removeHandler(handlers[i]);
				}
			}

			// now rebind stdout/stderr to logger
			Logger stdlogger = Logger.getLogger(STDOUT_LEVEL);
			stdlogger.setLevel(Level.ALL);
			LoggingOutputStream los = new LoggingOutputStream(stdlogger, StdOutErrLevel.STDOUT);
			System.setOut(new PrintStream(los, true));

			stdlogger = Logger.getLogger(STDERR_LEVEL);
			stdlogger.setLevel(Level.ALL);
			los= new LoggingOutputStream(stdlogger, StdOutErrLevel.STDERR);
			System.setErr(new PrintStream(los, true));
			// remove aged logs
			int numdays = Preferences.userNodeForPackage(PrefMgr.class).getInt(MAXAGE_PREF, EACMProperties.getLogAge());
			if(numdays!=0){
				cal.add(Calendar.DATE, -numdays); // go back numdays
				Utils.removeOldLogFiles(DateRoutines.formatDate(DATE_TIME_ONLY, cal.getTime()));
			}

			Utils.deleteFiles(TEMP_DIRECTORY, null,86400000); //Nothing should be left in the Temp dir, allow 24 hrs age
		} catch (Throwable t) {
			stderr.println("Unexpected Error setting up logging\n" + t);
		}
	}

	/**
	 * user has logged in, if a default profile is specified, load it
	 * otherwise show the instruction panel and allow user to select a profile
	 * @param pSet
	 * @param defProf
	 */
	public void process(ProfileSet pSet,Profile defProf) {
		EACMAction profAct = createWorkgroupMenu(pSet,defProf);
		pMain.remove(loginMgr);

		setApplicationTitle(); // show current connection info

		if (profAct != null) {
			profAct.actionPerformed(null);
		} else {
			showInstruct();
			//lblLastLoginDate.setText("Last Login: "+pSet.getLastLoginTime());  // CQ14860

			pMain.revalidate();
			setMenubar();
			pack();
		}

		validate();
	}

	private void showTabPane() {
		if (!getTabbedPane().isShowing()) {// not added yet
			if (instructionsShowing()) {
				hideInstruct();
			}
			pMain.add(getTabbedPane(),BorderLayout.CENTER);
			pMain.revalidate();
		}
	}

	/**
	 * called from WGRoleAction.LoadWorker
	 * @param nc
	 */
	public void loadEntryNavigate(NavController nc){
		showTabPane();

		addTab(null, nc);

		if (!statusToolBar.isShowing()) {
			pMain.add(statusToolBar,BorderLayout.SOUTH);

			// look for a previous size and use it
			Preferences eacmNode = Preferences.userNodeForPackage(EACM.class);
			boolean extendedState = eacmNode.getBoolean("extendedState", true);
			int x = eacmNode.getInt("left", 0);
			int y = eacmNode.getInt("top", 0);
			int width = eacmNode.getInt("width", 700);
			int height = eacmNode.getInt("height", 500);

			if(extendedState){
				setExtendedState(MAXIMIZED_BOTH);
				invalidate();
			}else{
				setBounds(x,y,width,height);
			}

			validate();

			setResizable(true);
		}

		repaint();

		((SWUpdateMgr)actionTbl.get(CHKUP_ACTION)).autoCheckForUpdate(nc.getProfile());
	}


	public void savePreferences(){
		Preferences eacmNode = Preferences.userNodeForPackage(EACM.class);
		if(getExtendedState()!=Frame.MAXIMIZED_BOTH){
			// dont save size if user has not selected a profile or closed all tabs
			if(EACM.getEACM().getTabbedPane().isShowing()){
				// save window size
				eacmNode.putInt("left", getX());
				eacmNode.putInt("top", getY());
				eacmNode.putInt("width", getWidth());
				eacmNode.putInt("height", getHeight());
				eacmNode.putBoolean("extendedState", false);
			}
		}else{
			eacmNode.putBoolean("extendedState", true);
		}

		try {
			eacmNode.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	/**
	 * called by actions when using workers
	 */
	public void disableActionsAndWait(){
		waitingCnt++;

		logger.log(Level.FINE, "waitingcnt "+waitingCnt);

		disableActions();
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(tabbedMenuPane != null){
			tabbedMenuPane.disableActionsAndWait();
		}
	}
	private int waitingCnt=0;
	public void enableActionsAndRestore(){
		waitingCnt--;
		logger.log(Level.FINE, "waitingcnt "+waitingCnt);

		enableActions();
		setCursor(Cursor.getDefaultCursor());
		if(tabbedMenuPane != null){
			tabbedMenuPane.enableActionsAndRestore();
		}
	}
	/**
	 * called when sw image is updated
	 */
	public void disableAllActionsAndWait(){
		disableAllActionsAndWait(true);
	}
	/**
	 * called when sw image is updated
	 */
	public void disableAllActionsAndWait(boolean tabtoo){
		waitingCnt++;
		logger.log(Level.FINE, "waitingcnt "+waitingCnt);

		//disable all menus
		JMenuBar menubar = getRootPane().getJMenuBar();
		if(menubar!=null){
			for (int i=0; i<menubar.getMenuCount(); i++) {
				JMenu amenu = menubar.getMenu(i);
				amenu.setEnabled(false);
			}
		}
		//disable current tab
		if(getCurrentTab() !=null){
			if(tabtoo || // navigate worker may be running, dont always want to interfere
					getCurrentTab() instanceof EditController){ // disable edit and show cursor
				getCurrentTab().disableActionsAndWait();
			}
		}

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
	/**
	 * called when sw image is not available to load and eacm is not shutdown
	 */
	public void enableAllActionsAndRestore(){
		enableAllActionsAndRestore(true);
	}
	public void enableAllActionsAndRestore(boolean tabtoo){
		waitingCnt--;
		logger.log(Level.FINE, "waitingcnt "+waitingCnt);

		//enable all menus
		JMenuBar menubar = getRootPane().getJMenuBar();
		for (int i=0; i<menubar.getMenuCount(); i++) {
			JMenu amenu = menubar.getMenu(i);
			amenu.setEnabled(true);
		}
		//enable current tab
		if(getCurrentTab()!=null){
			if(tabtoo || // navigate worker may be running, dont always want to interfere
					getCurrentTab() instanceof EditController){ // enable edit and show cursor
				getCurrentTab().enableActionsAndRestore();
			}
		}

		setCursor(Cursor.getDefaultCursor());
	}

	private void disableActions(){
		mnuWorkgroup.setEnabled(false);

		actionTbl.get(PREVTAB_ACTION).setEnabled(false);
		actionTbl.get(NEXTTAB_ACTION).setEnabled(false);
		actionTbl.get(PREF_ACTION).setEnabled(false); // this looks at current tab
		actionTbl.get(RESETDATE_ACTION).setEnabled(false); // this looks at current tab
	}
	private void enableActions(){
		mnuWorkgroup.setEnabled(true);

		int tabmax = getTabCount() - 1;
		int selid = getSelectedIndex();
		adjustPrevNext(selid, tabmax);

		actionTbl.get(RESETDATE_ACTION).setEnabled(true);
		actionTbl.get(PREF_ACTION).setEnabled(true);
	}


	private void initMenu() {
		mBar = new JMenuBar();
		mBar.setVerifyInputWhenFocusTarget(false); // prevent date warning popup when want to close

		//BasicPopupMenuUI uses rootpane for focus before opening the menu.
		this.getRootPane().setVerifyInputWhenFocusTarget(false); // prevent date warning popup when want to close tab

		createHelpMenu();

		createFileMenu();

		createSystemMenu();
	}

	private void setMenubar() {
		mBar.add(mnuFile);
		mBar.add(mnuWorkgroup);
		mBar.add(mnuSystem);
		mBar.add(mnuHelp);
		getRootPane().setJMenuBar(mBar);
	}

	/**
	 * @param amenu
	 */
	public static void updateMenuUI(JMenu amenu){
		if(amenu==null){
			return;
		}
		if(amenu instanceof EANActionMenu){
			SwingUtilities.updateComponentTreeUI(amenu.getPopupMenu());
		}
		for (int ii=0; ii<amenu.getItemCount(); ii++) {
			JMenuItem item = amenu.getItem(ii);
			if (item==null) {// separators are null
				continue;
			}
			SwingUtilities.updateComponentTreeUI(item);

			if (item instanceof JMenu) {
				updateMenuUI((JMenu)item);
			}
		}
	}
	/**
	 * release memory for all menus except shared menus from a tab that is closing
	 * @param menubar
	 */
	public void closeTabMenus(JMenuBar menubar){
		for (int i=0; i<menubar.getMenuCount(); i++) {
			JMenu amenu = menubar.getMenu(i);
			// do not close shared menus
			if (amenu.equals(mnuWorkgroup) ||
					amenu.equals(mnuSystem)||
					amenu.equals(mnuHelp) ||
					amenu.equals(getTabMenu())){
				continue;
			}

			closeMenu(amenu);
		}
		menubar.removeAll();
		menubar.setUI(null);
	}
	/**
	 * @param amenu
	 */
	public static void closeMenu(JMenu amenu){
		for (int ii=0; ii<amenu.getItemCount(); ii++) {
			JMenuItem item = amenu.getItem(ii);
			if (item==null) {// separators are null
				continue;
			}
			if (item instanceof JMenu) {
				closeMenu((JMenu)item);
				continue;
			}
			closeMenuItem(item);
		}

		if(amenu instanceof EANActionMenu){
			((EANActionMenu)amenu).dereference();
		}
		amenu.removeAll();
		amenu.setUI(null);
		amenu.setAction(null);
	}

	/**
	 * @param item
	 */
	public static void closeMenuItem(JMenuItem item){
		EACMAction act = (EACMAction)item.getAction();
		if (act instanceof WGRoleAction){
			act.dereference(); // do this here, it is not part of the actionTbl
		}

		item.setAction(null);
		item.setUI(null);
		KeyListener[] kl = item.getKeyListeners();
		if (kl !=null){
			for (int ii=0; ii<kl.length; ii++){
				item.removeKeyListener(kl[ii]);
			}
			kl = null;
		}
		MenuKeyListener[] mkl =  item.getMenuKeyListeners();
		if (mkl !=null){
			for (int ii=0; ii<mkl.length; ii++){
				item.removeMenuKeyListener(mkl[ii]);
			}
			mkl = null;
		}

		ActionListener[] al = item.getActionListeners();
		if (al !=null){
			for (int ii=0; ii<al.length; ii++){
				item.removeActionListener(al[ii]);
			}
			al = null;
		}
		item.removeAll();
	}

	/**
	 * update menubar with menus for selected window
	 * note: JRE will do removeNotify() on the old menubar
	 * @param bar
	 *
	 */
	public void updateMenuBar(JMenuBar bar) {
		if (bar != null) {
			bar.add(mnuWorkgroup);
			bar.add(mnuSystem);
			bar.add(getTabMenu());

			bar.add(mnuHelp);
		}
		getRootPane().setJMenuBar(bar);
	}

	private void addActionMenuItem(JMenu menuBar, String actionName){
		JMenuItem mi = menuBar.add(getGlobalAction(actionName));
		mi.setVerifyInputWhenFocusTarget(false);
	}

	private void createHelpMenu() {
		mnuHelp = new JMenu(Utils.getResource(HELP_MENU));
		mnuHelp.setMnemonic(Utils.getMnemonic(HELP_MENU));

		addActionMenuItem(mnuHelp,CONTENTS_ACTION);
		addActionMenuItem(mnuHelp,ATTRHELP_ACTION);
		mnuHelp.addSeparator();
		addActionMenuItem(mnuHelp,FMI_ACTION);
		addActionMenuItem(mnuHelp,ABOUT_ACTION);
	}

	private void createSystemMenu() {
		mnuSystem = new JMenu(Utils.getResource(SYSTEM_MENU));
		mnuSystem.setMnemonic(Utils.getMnemonic(SYSTEM_MENU));

		addActionMenuItem(mnuSystem,MEMORY_ACTION);

		addActionMenuItem(mnuSystem,GC_ACTION);

		addActionMenuItem(mnuSystem,BREAK_ACTION);
		mnuSystem.addSeparator();

		addActionMenuItem(mnuSystem,PREF_ACTION);
		mnuSystem.addSeparator();

		addActionMenuItem(mnuSystem,CHKUP_ACTION);
		mnuSystem.addSeparator();

		addActionMenuItem(mnuSystem,NEXTTAB_ACTION);

		addActionMenuItem(mnuSystem,PREVTAB_ACTION);

		mnuSystem.addSeparator();

		addActionMenuItem(mnuSystem,RESETDATE_ACTION);

	}

	/**
	 * setVerifyInputWhenFocusTarget..
	 * Sets the value to indicate whether input verifier for the
	 * current focus owner will be called before this component requests
	 * focus. The default is true. Set to false on components such as a
	 * Cancel button or a scrollbar, which should activate even if the
	 * input in the current focus owner is not "passed" by the input
	 * verifier for that component.
	 */
	private void createFileMenu() {
		mnuFile = new JMenu(Utils.getResource(FILE_MENU));
		mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));
		mnuFile.setVerifyInputWhenFocusTarget(false); // prevent date warning popup when want to close

		addActionMenuItem(mnuFile,LOGOFF_ACTION);

		mnuFile.addSeparator();

		JMenuItem mi = mnuFile.add(exitAction);
		mi.setVerifyInputWhenFocusTarget(false);
	}


	private EACMAction createWorkgroupMenu(ProfileSet ps,Profile defProf) {
		EACMAction act = null;
		if(mnuWorkgroup==null){
			// must determine the number of wg that will fit - java6 is missing the scroll button
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			int wgscrollsize = 30;
			if(screen.height<900){
				wgscrollsize =20;
			}
			if(ps.size()>wgscrollsize){
				mnuWorkgroup = new EANActionMenu(Utils.getResource(WORKGROUP_MENU),wgscrollsize);
			}else{
				mnuWorkgroup = new JMenu(Utils.getResource(WORKGROUP_MENU));
			}
			mnuWorkgroup.setMnemonic(Utils.getMnemonic(WORKGROUP_MENU));
			mnuWorkgroup.getAccessibleContext().setAccessibleDescription(mnuWorkgroup.getText());
		}
		// to get a list of WG, and sort them
		Hashtable<String, WGRole> wgTbl = new Hashtable<String, WGRole>();
		for (int i = 0; i < ps.size(); i++) {
			Profile pi = ps.elementAt(i);
			String key = pi.getWGName()+pi.getWGID();

			WGRole wgrole = wgTbl.get(key);
			if (wgrole==null){
				wgrole = new WGRole(pi.getWGName(),pi.getWGID());
				wgTbl.put(key, wgrole);
			}
			wgrole.addProfile(pi);
		}
		Vector<WGRole> vWG = new Vector<WGRole>(wgTbl.values());
		Collections.sort(vWG);

		ProfComparator profcomp = new ProfComparator();
		// add to Workgroup menu
		for (int i = 0; i < vWG.size(); i++) {
			WGRole wgrole = vWG.elementAt(i);
			String WGName = wgrole.name;
			JMenu wgmenu = new JMenu(WGName);
			mnuWorkgroup.add(wgmenu);

			Collections.sort(wgrole.pVct,profcomp);
			for (int m=0; m<wgrole.pVct.size(); m++){
				Profile wgprof = wgrole.pVct.elementAt(m);
				WGRoleAction wgact = new WGRoleAction(wgprof);
				if (defProf!=null){ // find action for this profile
					if (defProf.getEnterprise().equals(wgprof.getEnterprise()) &&
							defProf.getOPWGID()== wgprof.getOPWGID()){
						act = wgact;
					}
				}
				JMenuItem mi = wgmenu.add(wgact);
				mi.setVerifyInputWhenFocusTarget(false);
			}
			wgrole.dereference();
		}
		vWG.clear();
		wgTbl.clear();

		return act;
	}
	private class WGRole implements Comparable<Object>
	{
		String name;
		//int wgid;
		Vector<Profile> pVct = null;
		WGRole(String n, int id){
			name = n;
			//wgid=id;
		}
		void addProfile(Profile p){
			if (pVct==null){
				pVct = new Vector<Profile>();
			}
			pVct.add(p);
		}
		void dereference(){
			name = null;
			if (pVct!=null){
				pVct.clear();
				pVct = null;
			}
		}

		public int compareTo(Object o) {
			return name.toUpperCase().compareTo(((WGRole)o).name.toUpperCase());
		}
	}

	public void listMemory(){
		MemoryAction act = (MemoryAction)getGlobalAction(MEMORY_ACTION);
		act.listMemory();
	}

	private void selectTab(int i) {
		int newIndex = getSelectedIndex() + i;
		setSelectedIndex(newIndex);
	}

	/**
	 * adjustPrevNext
	 * @param indx
	 * @param max
	 *
	 */
	public void adjustPrevNext(int indx, int max) {
		if(actionTbl==null){
			return; // deref ran
		}
		actionTbl.get(PREVTAB_ACTION).setEnabled(indx > 0 && max != 0);
		actionTbl.get(NEXTTAB_ACTION).setEnabled(indx < max && max != 0);
	}

	/**
	 * called when nls changes or active profile is changed
	 * setStatus
	 * @param p - this is always the activeprofile
	 *
	 */
	public void setStatus(Profile p) {
		if (p != null) {
			statusToolBar.updateStatus(p);
		}
	}

	/**
	 * exit
	 *
	 */
	public void exit() {
		if (EventQueue.isDispatchThread()) {
			exitAction.actionPerformed(null);
		}else {
			try {
				Runnable later = new Runnable() {
					public void run() {
						exitAction.actionPerformed(null);
					}
				};
				EventQueue.invokeAndWait(later);
			}catch (Throwable e) {
				System.err.println("Exception during exit:");
				e.printStackTrace();
			}
		}
	}

	/**
	 * exit immediately - do not change cursor using invokelater - jar will be replaced
	 *
	 */
	public void exitImmediately() {
		if (EventQueue.isDispatchThread()) {
			exitAction.exitNow();
		}else {
			try {
				Runnable later = new Runnable() {
					public void run() {
						exitAction.exitNow();
					}
				};
				EventQueue.invokeAndWait(later);
			}catch (InterruptedException e) {
				System.err.println("Exit was interrupted:");
				e.printStackTrace();
			}catch (InvocationTargetException e) {
				System.err.println("Exception during exit:");
				e.printStackTrace();
			}
		}
	}

	/**
	 * close
	 *index may not be the selected index if user presses button on tab
	 * @param tab
	 * @param index
	 * @return
	 */
	public boolean close(TabPanel tab, int index) {
		if (tab.canClose()) {
			logger.log(Level.INFO,"Closing "+tab);
			CloseTabComponent ctb = (CloseTabComponent)tabbedMenuPane.getTabComponentAt(index);
			ctb.dereference(tab.getAction(CLOSETAB_ACTION));

			tabbedMenuPane.removeTabAt(index);

			if (tabbedMenuPane.getTabCount()==0) {
				resetApplication();
			} else {
				validate();
				tabbedMenuPane.selectParent(tab);
			}
			tab.dereference();
			tab = null;
			repaint();
			return true;
		} else {
			tabbedMenuPane.setSelectedComponent((Component) tab);
		}
		return false;
	}

	/**
	 * close specified tab
	 * @param tab
	 * @param indx
	 * @return
	 *
	 */
	private boolean closeTab(TabPanel tab, int indx) {
		if (tab.canClose()) {
			logger.log(Level.INFO,"Closing "+tab);
			CloseTabComponent ctb = (CloseTabComponent)tabbedMenuPane.getTabComponentAt(indx);
			ctb.dereference(tab.getAction(CLOSETAB_ACTION));

			tabbedMenuPane.removeTabAt(indx);

			tab.dereference();
			tab = null;
			return true;
		} /*else {
        	tabbedMenuPane.setSelectedComponent((Component) tab);
        }*/
		return false;
	}

	/**
	 * close All tabs
	 * @param isExit
	 * @return
	 */
	public boolean canCloseAll(boolean isExit) {
		boolean allClosed = true;
		if (tabbedMenuPane != null) {
			for(int i=0;i<tabbedMenuPane.getTabCount(); i++){
				TabPanel tab = this.tabbedMenuPane.getTabPanel(i);
				tab.setShouldRefresh(false); // dont refresh, user is closing
			}
			while (tabbedMenuPane.getTabCount() > 0) {
				TabPanel c = tabbedMenuPane.getTabPanel(0);
				tabbedMenuPane.setSelectedComponent(c); // select this one so user knows where the msg came from
				if (!closeTab( c, 0)) {
					allClosed= false;
					break;
				}
			}
		}

		validate();
		if (!isExit && tabbedMenuPane.getTabCount()==0) {
			resetApplication();
		}

		repaint();

		return allClosed;
	}

	/**
	 * system is exiting, release memory and put signout in mw log
	 */
	public void dereference(){
		try {
			// make sure any changes persist
			Preferences.userNodeForPackage(PrefMgr.class).flush();
		} catch (BackingStoreException e1) {}

		removeWindowListener(exitAction);

		if(statusToolBar !=null){
			statusToolBar.dereference();
			statusToolBar = null;
		}

		if(actionTbl!=null){
			for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
				EACMAction action = (EACMAction)e.nextElement();
				action.dereference();
			}
			actionTbl.clear();
			actionTbl = null;
		}

		RMIMgr.getRmiMgr().dereference();

		tabbedMenuPane.dereference();
		tabbedMenuPane = null;

		findRepMgr.dereference();
		findRepMgr = null;

		//close the dictionary
		if (dictionary!=null) {
			dictionary.close();
			dictionary = null;
		}
		if(lockMgr !=null){
			lockMgr.dereference();
			lockMgr = null;
		}

		if(loginMgr !=null){
			loginMgr.dereference();
			loginMgr = null;
		}

		if (pMain != null){
			pMain.removeAll();
			pMain = null;
		}

		if (pnlInstruct != null){
			pnlInstruct.removeAll();
			pnlInstruct = null;
		}

		if (mBar!=null){
			for (int i=0; i<mBar.getMenuCount(); i++) {
				JMenu amenu = mBar.getMenu(i);
				EACM.closeMenu(amenu);
			}
			mBar.removeAll();
			mBar.setUI(null);
			mBar = null;
		}

		mnuWorkgroup = null;

		userWindowsVct.clear();
		userWindowsVct = null;

		loggerFileHandler.close();
		Logger.getLogger("").removeHandler(loggerFileHandler);

		if(pStream !=null){
			pStream.close();
			pStream = null;
		}
	}

	/**
	 * @return
	 */
	public JPanel getMainPane(){
		return pMain;
	}
	private void resetApplication() {

		//mnuWorkgroup is only instantiated after user successfully logs in
		if (pMain != null && pMain.isShowing() && mnuWorkgroup!=null) {
			pMain.remove(tabbedMenuPane);
			showInstruct();
			//mnuSystem.setEnabled("checkUp", false);
			actionTbl.get(CHKUP_ACTION).setEnabled(false);
			EACMAction act = actionTbl.get(RESETDATE_ACTION);
			if(act instanceof ResetDateAction) {
				((ResetDateAction)act).setCurrentTab(null);
			}

			pMain.revalidate();
			setMenubar();
			if (statusToolBar.isShowing()) {
				pMain.remove(statusToolBar);
			}
			pack();
		}
	}

	/**
	 * logOff
	 */
	public void logOff() {
		if (canCloseAll(false)) {
			hideInstruct();

			pMain.remove(statusToolBar);

			loginMgr.logOff();

			pMain.add(loginMgr,BorderLayout.CENTER);
			setJMenuBar(null);
			pack();
			center();

			setResizable(false); //login doesnt resize

			loginMgr.requestFocusInWindow();

			setApplicationTitle();

			//close any frames/dialogs for this user
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				EventListener listeners[] = win.getListeners(WindowListener.class);
				if (listeners!=null){
					for(int ii=0; ii<listeners.length; ii++) {
						if (listeners[ii] instanceof EACMAction){
							((EACMAction)listeners[ii]).actionPerformed(null);
							break;
						}
					}
					listeners=null;
				}
			}
			userWindowsVct.clear();

			closeMenu(mnuWorkgroup);
			mBar.remove(mnuWorkgroup);
			mnuWorkgroup = null;
		}
	}

	/**
	 * called when an EACMFrame or EACMDialog is instantiated
	 * @param win
	 */
	public void addUserWindow(Window win){
		synchronized(userWindowsVct){
			userWindowsVct.add(win);
		}
	}
	/**
	 * called when the EACMFrame or EACMDialog is dereferenced
	 * @param win
	 */
	public void removeUserWindow(Window win){
		synchronized(userWindowsVct){
			userWindowsVct.remove(win);
		}
	}

	/**
	 * get any current bookmarkframe
	 * @return
	 */
	public BookmarkFrame getBookMark(){
		synchronized(userWindowsVct){
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				if(win instanceof BookmarkFrame){
					return (BookmarkFrame)win;
				}
			}
		}
		return null;
	}
	/**
	 * get any current flag maint frame
	 * @return
	 */
	public MaintenanceFrame getFlagMaint(){
		synchronized(userWindowsVct){
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				if(win instanceof MaintenanceFrame){
					return (MaintenanceFrame)win;
				}
			}
		}
		return null;
	}
	public HistoryFrame getHistoryFrame(){
		synchronized(userWindowsVct){
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				if(win instanceof HistoryFrame){
					return (HistoryFrame)win;
				}
			}
		}
		return null;
	}
	public LinkWizard getLinkWizard(){
		synchronized(userWindowsVct){
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				if(win instanceof LinkWizard){
					return (LinkWizard)win;
				}
			}
		}
		return null;
	}
	public EditPDGFrame getEditPDGFrame(){
		synchronized(userWindowsVct){
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				if(win instanceof EditPDGFrame){
					return (EditPDGFrame)win;
				}
			}
		}
		return null;
	}
	/**
	 * get any frames for this uid, used when closing a tab and need to find any open frames
	 * @return
	 */
	public Vector<EACMFrame> getMyEACMFrames(String uid){
		Vector<EACMFrame> vct = new Vector<EACMFrame>();
		synchronized(userWindowsVct){
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				if(win instanceof EACMFrame){
					EACMFrame frame = (EACMFrame)win;
					if (frame.getUID().startsWith(uid)){
						vct.add(frame);
					}
				}
			}
		}
		return vct;
	}

	/**
	 * get the searchframe for this key
	 * @param actionkey
	 * @return
	 */
	public SearchFrame getSearchFrame(String actionkey){
		synchronized(userWindowsVct){
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				if(win instanceof SearchFrame){
					SearchFrame sf = (SearchFrame)win;
					if (sf.getUID().equals(actionkey)){
						return sf;
					}
				}
			}
		}
		return null;
	}

	/**
	 * get the pickframe for this key
	 * @param actionkey
	 * @return
	 */
	public PickFrame getPickFrame(String actionkey){
		synchronized(userWindowsVct){
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				if(win instanceof PickFrame){
					PickFrame sf = (PickFrame)win;
					if (sf.getUID().equals(actionkey)){
						return sf;
					}
				}
			}
		}
		return null;
	}

	/**
	 * called when a findable is closing, findrepmgr needs to know the current findable is no longer valid
	 * @return
	 */
	public FindRepMgr getFindMgr(){
		return findRepMgr;
	}

	/**
	 * called when findrep action is looking for the frame to show
	 * @return
	 */
	public FindRepFrame getFindRepFrame(){
		synchronized(userWindowsVct){
			for (int w=0; w<userWindowsVct.size(); w++){
				Window win = userWindowsVct.elementAt(w);
				if(win instanceof FindRepFrame){
					return (FindRepFrame)win;
				}
			}
		}
		return null;
	}
	/**
	 * put latest navigation into bookmarkframe
	 * @param item
	 */
	public void updateBookmarkAction(EANActionItem item,EANActionItem history[]) {
		if (item instanceof NavActionItem) {
			synchronized(userWindowsVct){
				for (int w=0; w<userWindowsVct.size(); w++){
					if(userWindowsVct.elementAt(w) instanceof BookmarkFrame){
						((BookmarkFrame)userWindowsVct.elementAt(w)).setActionItem(item,history);
						break;
					}
				}
			}
		}
	}

	/**
	 * setPast
	 *
	 */
	public void setPastStatus() {
		statusToolBar.setPastStatus();
	}

	/**
	 * setFilter
	 * @param b
	 *
	 */
	public void setFilterStatus(boolean b) {
		statusToolBar.setFilterStatus(b);
	}
	/**
	 * display hidden columns icon
	 * @param b
	 *
	 */
	public void setHiddenStatus(boolean b) {
		statusToolBar.setHiddenStatus(b);
	}

	/**
	 * enable/disable action.
	 * @param actionname
	 * @param b
	 */
	public void setActionEnabled(String actionname, boolean b) {
		actionTbl.get(actionname).setEnabled(b);
	}


	/**
	 * load from the result of a link action
	 * @param parTab
	 * @param list
	 *
	 */
	public void loadFromLink(TabPanel parTab, EntityList list) {
		showTabPane();
		EANActionItem action = list.getParentActionItem();
		if (action instanceof EditActionItem) {
			TabPanel eTab = new EditController(list, null);
			addTab(parTab, eTab);
		} else if (action instanceof CreateActionItem) {
			TabPanel eTab = new EditController(list, null);
			addTab(parTab, eTab);
		} else if ((action instanceof NavActionItem) ||(action instanceof SearchActionItem)) {
			NavController out = new NavController(list.getProfile());
			out.setParentProfile(list.getProfile());
			out.init(list);
			Utils.getTabTitle("navTab", out.getProfile());
			addTab(parTab, out);
		}
	}

	public TabPanel getTab(int i) {
		return tabbedMenuPane.getTabPanel(i);
	}
	public TabbedMenuPane getTabbedPane() {
		return tabbedMenuPane;
	}
	public void addTab(TabPanel parTab, TabPanel tabComponent) {
		tabbedMenuPane.addTab(parTab, tabComponent);
	}
	/**
	 * called when navigate pin is changed
	 * @param tab
	 * @param icon
	 */
	public void setIconAt(TabPanel tab, Icon icon) {
		tabbedMenuPane.setIconAt(tab, icon);
		tabbedMenuPane.revalidate();
	}
	public void setTitleAt(TabPanel tab, String title) {
		if (tabbedMenuPane != null && tab != null) {
			tabbedMenuPane.setTitleAt(tab, title);
			tabbedMenuPane.revalidate();
		}
	}
	public int getSelectedIndex() {
		if (tabbedMenuPane != null) {
			return tabbedMenuPane.getSelectedIndex();
		}
		return -1;
	}
	public TabPanel getCurrentTab() {
		if (tabbedMenuPane != null) {
			return tabbedMenuPane.getSelectedTab();
		}
		return null;
	}

	public int getNavigateIndex(Profile prof) {
		if (tabbedMenuPane != null) {
			return tabbedMenuPane.getNavigateIndex(prof);
		}
		return -1;
	}
	public void setSelectedIndex(int i) {
		if (tabbedMenuPane != null) {
			tabbedMenuPane.setSelectedIndex(i);
		}
	}
	public int getTabCount() {
		if (tabbedMenuPane != null) {
			return tabbedMenuPane.getTabCount();
		}
		return 0;
	}
	public JMenu getTabMenu() {
		if (tabbedMenuPane != null) {
			return tabbedMenuPane.getTabMenu();
		}
		return null;
	}
	public boolean viewLockExist(boolean all) {
		for (int i = 0; i < getTabCount(); ++i) {
			TabPanel etab = getTab(i);
			if (etab instanceof LockActionTab) {
				if (((LockActionTab) etab).viewLockExist(all)) {
					setSelectedIndex(i);
					return true;
				}
			}
		}
		return false;
	}
	public boolean abrQStatusExist() {
		for (int i = 0; i < getTabCount(); ++i) {
			TabPanel etab = getTab(i);
			if (etab instanceof ABRQSActionTab) {
				setSelectedIndex(i);
				return true;
			}
		}
		return false;
	}
	/**
	 * does a tab exist for this data, if so select it
	 * @param prof
	 * @param ei
	 * @param eai
	 * @return
	 */
	public boolean tabExists(Profile prof, EntityItem[] ei, EANActionItem eai) {
		if (Utils.isPast(prof)){
			return false;
		}
		for (int i = 0; i < tabbedMenuPane.getTabCount(); ++i) {
			TabPanel t = tabbedMenuPane.getTabPanel(i);
			// this looks for the same action and match of all entityitems, if there are less items, it still matches
			//if there are more items it wont match
			if (t.tabExistsWithAll(prof,ei, eai)) {
				tabbedMenuPane.setSelectedIndex(i);
				return true;
			}
			// this looks for the same action and match of any entityitems, dont want to let user
			// open a new tab with the same data
			if (t.tabExistsWithSome(prof,ei, eai)) {
				//msg7001.0 = One or more selected Entity Items are already in another tab for {0} Action.  \nPlease change selection.
				com.ibm.eacm.ui.UI.showWarning(null,Utils.getResource("msg7001.0",eai.getLongDescription()));
				return true;
			}
		}
		return false;
	}


	public void setWaitCursor(){
		getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
	public void setDefaultCursor(){
		getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * needed by editors
	 * @return
	 */
	public JSpellDictionary getDictionary() {
		return dictionary;
	}
	/**
	 * getComponent - used when showing tooltips
	 * @return
	 *
	 */
	protected JComponent getComponent() {
		return pMain;
	}


	//=====================================================================================================
	//=====================================================================================================

	public Profile getDefaultProfile(){
		return loginMgr.getDefaultProfile();
	}
	public ProfileSet getProfileSet(){
		return loginMgr.getProfileSet();
	}
	public ProfileSet updateActiveProfile(Profile p){
		return loginMgr.updateActiveProfile(p);
	}
	public void setActiveProfile(Profile p){
		loginMgr.setActiveProfile(p);
	}
	public Profile getActiveProfile(){
		return loginMgr.getActiveProfile();
	}
	/**
	 * refresh
	 *called when restore action has finished
	 * update any navigate tabs
	 * @param inAct
	 * @param prof
	 */
	public void refresh(InactiveItem[] inAct, Profile prof) {
		if (inAct != null) {
			for (int i = 0; i < getTabCount(); ++i) {
				TabPanel tab = getTab(i);
				if (tab instanceof NavController) {
					for (int x = 0; x <  inAct.length; ++x) {
						((NavController)tab).setShouldRefresh(inAct[x].getEntityType(), prof.getOPWGID(),
								getCode(inAct[x]));
					}
				}
			}
		}
	}

	private static int getCode(InactiveItem inAct) {
		if (inAct.getEntityClass().equals("Relator")) {
			return RELATOR_TYPE;
		}
		return CHILD_TYPE;
	}
	/**
	 * @return
	 */
	public Profile[] getActiveProfiles() {
		int ii = getTabCount();
		Profile[] out = new Profile[ii];
		for (int i = 0; i < ii; ++i) {
			TabPanel tab = getTab(i);
			out[i] = tab.getProfile();
		}
		return out;
	}
	/**
	 * show mw selection in accessible dialog
	 */
	public static void setMWObjectPreference(){
		com.ibm.eacm.preference.MWChooser mwchooser = new MWChooser();

		String[] options = {"save.pref"};
		String accdesc[] = { "chooseMW-acc" };
		int r = com.ibm.eacm.ui.UI.showAccessibleDialog(null,//Component parentComponent
				"chooseMW", //title
				JOptionPane.PLAIN_MESSAGE, //messageType
				JOptionPane.YES_OPTION, // optiontype
				"chooseMWDialog-acc", //accDialogDesc
				new Object[]{mwchooser}, //msgs
				options,  //button labels
				accdesc); //accButtonDescs

		options = null;
		accdesc = null;
		if (r == YES_BUTTON) {
			if(LoginMgr.getMWParser().getSelectedItem()!=null){
				SerialPref.putPref(MWParser.MIDDLEWARE_PROFILE_KEY,
						((MWObject)LoginMgr.getMWParser().getSelectedItem()).key());
			}
		}else if (r==CLOSED){
			mwchooser.restoreMWSelected();
		}
		mwchooser.dereference();
	}


	/**
	 *
	 * testers appear to be working in the wrong instance while testing.
	 * this will provide better feedback on the middleware service they
	 * are connected to.
	 */
	private void setApplicationTitle() {
		String str = Utils.getResource("appName");

		if (LoginMgr.getCurrentMWO()!= null) {
			str = Utils.getResource("appTitle", str, LoginMgr.getMWODescription());
		}
		setTitle(str);
	}

	/*************************
	 * Draw progressbar on splash screen
	 * @param percent
	 */
	private static void drawOnSplashScreen(int percent){
		if(splash!=null && splash.isVisible()){
			Rectangle bounds = splash.getBounds();
			Graphics2D g = splash.createGraphics();
			int height = 8;
			int width = bounds.width;
			int x=width/2;
			width=x; // just right half of the screen
			int y=bounds.height - height - 2;

			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, width*percent/100, height); // progress bar

			g.drawString("Loading...", x-70, y+8);

			splash.update();
		}
	}

	/**************
	 * update progress bar
	 * @param x
	 */
	private static void setPercent(int x) {
		drawOnSplashScreen(x);
	}
	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createEACM() {
		try{
			if (eacmFrame==null){
				eacmFrame= new EACM();  // run the application
			}
		}catch(Throwable ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	/*******************************************
	 * Java Platform, Standard Edition (Java SE, formerly known as J2SE) version 6, provides a solution
	 * that allows the application to show the splash screen much earlier, even before the virtual machine
	 * starts. Now, a Java application launcher is able to decode an image and display it in a simple
	 * nondecorated window. If your application is run from the command line or from a shortcut,
	 * use the -splash: Java application launcher option to show a splash screen:
	 * Images must reside relative to the 'user.dir' System property.
	 *
	 * The splash screen will close automatically when the first AWT or Swing window is displayed.

If you need to determine whether your code is running on the event dispatch thread,
invoke javax.swing.SwingUtilities.isEventDispatchThread.
	 *       * If you would like more information on focus, see
	 * <a href="http://java.sun.com/docs/books/tutorial/uiswing/misc/focus.html">
	 * How to Use the Focus Subsystem</a>,
	 * a section in <em>The Java Tutorial</em>.
	 * @param args
	 */
	public static void main(String[] args) {
		/*try{

    		UIDefaults defaults = UIManager.getDefaults();
    		System.out.println(defaults.size()+ " b4 properties defined !");

    		int i = 0;
    		for(Enumeration e = defaults.keys(); e.hasMoreElements(); i++){
    			Object key = e.nextElement();
    			if (key.toString().indexOf("Table")!= -1)
        			System.out.println(key.toString()+" = "+""+defaults.get(key));
    		}
    		//set the native system look and feel
    		System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ getSystemLookAndFeelClassName "+
    				UIManager.getSystemLookAndFeelClassName());

    		LookAndFeel laf = UIManager.getLookAndFeel();
    		System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  UIManager.getLookAndFeel() "+
    				laf);
    		UIManager.setLookAndFeel(//UIManager.getSystemLookAndFeelClassName());
    		LnfPref.getLookAndFeel());


    		defaults = UIManager.getDefaults();

    		i = 0;
    		for(Enumeration e = defaults.keys(); e.hasMoreElements(); i++){
    			Object key = e.nextElement();
    			if (key.toString().indexOf("background")!= -1)
    			System.out.println(key.toString()+" = "+""+defaults.get(key));
    		}

    		defaults = UIManager.getLookAndFeelDefaults();     // this is it before tampering
    		System.out.println(defaults.size()+ "getLookAndFeelDefaults defined !");

    		for(Enumeration e = defaults.keys(); e.hasMoreElements(); i++){
    			Object key = e.nextElement();
    			if (key.toString().indexOf("background")!= -1)
        			System.out.println(key.toString()+" = "+""+defaults.get(key));
    		}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}*/
		/* LnfPref isn't available to end users 
		 * 
		 * try{
    		UIManager.setLookAndFeel(LnfPref.getLookAndFeel());
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}*/
		try{		
			if(ColorPref.canOverrideColor()){ // allow overrides 
				// turn off bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);

				MetalLookAndFeel.setCurrentTheme(EACM_THEME); // need to use a theme to be able to change backgrounds
				//only metal supports themes

				UIManager.setLookAndFeel(new MetalLookAndFeel());
			}else {
				UIManager.setLookAndFeel(new WindowsLookAndFeel()); // default to windows LAF
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			splash = SplashScreen.getSplashScreen();
			setPercent(2); // update progress bar
			//Schedule a job for the event-dispatching thread:
			//creating and showing this application's GUI.
			//almost all code that creates or interacts with Swing components must run on the event dispatch thread.
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createEACM();
				}
			});
		}catch(Throwable ex){
			ex.printStackTrace();
			System.exit(-2);
		}
	}

	private class PrevTabAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		public PrevTabAction() {
			super(PREVTAB_ACTION,KeyEvent.VK_F3, Event.SHIFT_MASK);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			selectTab(-1);
		}
	}

	private class NextTabAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		public NextTabAction() {
			super(NEXTTAB_ACTION,KeyEvent.VK_F3, 0);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			selectTab(1);
		}
	}

	private class GcAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		public GcAction() {
			super(GC_ACTION,KeyEvent.VK_G, Event.CTRL_MASK + Event.SHIFT_MASK);
		}

		public void actionPerformed(ActionEvent e) {
			Utils.gc();
			logger.log(Level.INFO, "Cleared memory");
		}
	}
}
