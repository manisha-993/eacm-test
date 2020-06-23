//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.ui.IntField;


/**
 * This class manages preferences for behavior
 * @author Wendy Stimpson
 */
// $Log: BehaviorPref.java,v $
// Revision 1.6  2014/05/01 19:35:15  wendy
// Add tablayoutpolicy to support scroll of action tabbed pane
//
// Revision 1.5  2013/11/11 18:18:47  wendy
// Add enhanced flag user preference
//
// Revision 1.4  2013/08/29 19:37:12  wendy
// only look at autolink.arm, not saved prefs
//
// Revision 1.3  2013/07/26 15:44:37  wendy
// remove floatflag frame default
//
// Revision 1.2  2013/07/25 20:51:54  wendy
// added preference for grid edit auto sort
//
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class BehaviorPref extends JPanel implements EACMGlobals,ChangeListener,
ActionListener, Preferencable, DocumentListener  {
	private static final long serialVersionUID = 1L;

    private static final String PREF_AUTO_SIZE_COUNT = "auto.size.count";
    private static final boolean DEFAULT_AUTO_SIZE = true;
    private static final String PREF_AUTO_SIZE = "auto.size";
    private static final String PREF_AUTO_SIZE_COLS_COUNT = "auto.size.cols.count";
    private static final int DEFAULT_ROW_SIZE_COUNT = 0;
    private static final int DEFAULT_COL_SIZE_COUNT = 20;
    //private static final String PREF_AUTO_LINK = "auto.link";
    private static final String PREF_LINK_TYPE = "eacm.link.type";
    private static final int DEFAULT_LINK_TYPE = 0;
	/**
	 * autolink by default
	 */
    private static final String AUTOLINK_DEFAULT = "autolink" + DEF_EXTENSION;
	/**
	 * update default
	 */
    private static final boolean PREF_UPDATE_DEFAULT = false;

    private static final String PREF_VERT_FLAG_FRAME = "vertical.use.frame.flag";
    private static final boolean DEFAULT_PREF_VERT_FLAG_FRAME = false;

    private static final String PREF_LOAD_BOOKMARK = "load.bookmark";
    private static final boolean DEFAULT_LOAD_BOOKMARK = true;
    private static final String PREF_NAV_SPLIT = "nav.split";
    private static final String PREF_NAV_SPLIT_LOCATION = "nav.split.location";
    private static final boolean DEFAULT_NAV_SPLIT = true;

    private static final String PREF_TREE_EXPANDED = "eacm.tree.expanded";
    private static final boolean DEFAULT_PREF_TREE_EXPANDED = false;
    private static final String PREF_USE_SAVEDTREE_EXPANSION="eacm.tree.savetwisties";
    private static final boolean DEFAULT_USE_SAVEDTREE_EXPANSION = false;
    private static final String PREF_TAB_PLACEMENT = "tab.layout";
    private static final String PREF_NAV_TAB_LAYOUT = "nav.tab.layoutpolicy";
    private static final int DEFAULT_REFRESH_TYPE = 1;
    private static final String PREF_REFRESH_TYPE = "eacm.refresh.type";
    private static final String PREF_ACTION_EXPANDED = "eacm.action.expanded";
    private static final String PREF_SHOWTT = "eacm.showtt";
    private static final String PREF_SHOWSRC = "eacm.showsrc";
    private static final boolean DEFAULT_PREF_ACTION_EXPANDED = false;
    private static final boolean DEFAULT_PREF_SHOWTT = true;
    private static final boolean DEFAULT_PREF_SHOWSRC = true;
    private static final int DEFAULT_WF_CLEAR_TYPE = 0;
    public static final String PREF_WF_CLEAR_TYPE = "eacm.clearWF.type";
    private static final String PREF_EDITAUTOSORT = "eacm.edit.autosort";
    private static final boolean DEFAULT_PREF_EDITAUTOSORT = false;

	private JButton bSave = null;
	private JButton bReset = null;
	private JPanel btnPnl =null;

	private JLabel lblNavTabLay = new JLabel(Utils.getResource("navtabLay")); // only used for the tabbedmenupane
	private JComboBox navtablayCombo = new JComboBox();

	private JLabel lblTabPlacement = new JLabel(Utils.getResource("tabPlacement"));
	private JComboBox bhvCombo = new JComboBox();

	private JLabel lblRefresh = new JLabel(Utils.getResource("refreshType"));
	private JComboBox refreshCombo = new JComboBox();

	private JLabel lblClearWF = new JLabel(Utils.getResource("clrWF"));
	private JComboBox clearWFCombo = new JComboBox();

	private JLabel lblLinkType = new JLabel(Utils.getResource("lnkmde"));
	private JComboBox linkTypeCombo = new JComboBox();

	private JCheckBox bookCheckBox = new JCheckBox(Utils.getResource("loadBookNew"));
	private JCheckBox navCheckBox = new JCheckBox(Utils.getResource("navdivloc"));

	private JCheckBox flagFrameCheckBox = new JCheckBox(Utils.getResource("vert.flag.frame"));
	private JCheckBox treeXpndCheckBox = new JCheckBox(Utils.getResource("tree.xpnd"));
	private JCheckBox saveTwistCheckBox = new JCheckBox(Utils.getResource("tree.save.xpnd"));

	private JCheckBox autoLinkCheckBox = new JCheckBox(Utils.getResource("auto.link"));
	private JCheckBox autoUpdateCheckBox = new JCheckBox(Utils.getResource("autoUpdate"));
	private JCheckBox actionXpndCheckBox = new JCheckBox(Utils.getResource("action.xpnd"));
	private JCheckBox showTTCheckBox = new JCheckBox(Utils.getResource("action.tt"));
	private JCheckBox autosortCheckBox = new JCheckBox(Utils.getResource("edit.autosort"));
	private JCheckBox enhancedFlagCheckBox = new JCheckBox(Utils.getResource("edit.enhancedflag"));

	private JCheckBox showSrcNameCheckBox = new JCheckBox(Utils.getResource("behavior.src"));
	
	private JCheckBox autoSizeCheckBox = new JCheckBox(Utils.getResource("autoSize"));
	private IntField ifldRowsSize = new IntField(6);
	private IntField ifldColsSize = new IntField(6);
	private PrefMgr prefMgr;
	private SaveAction saveAction;

	/**
	 * behavior Chooser
	 */
	protected BehaviorPref(PrefMgr pm) {
		prefMgr = pm;
		init();
	 	setBorder(BorderFactory.createTitledBorder(Utils.getResource("behavior.border")));// meet accessiblity

		buildButtonPanel();
		loadBehavior();
		addListeners();
	}

	private void addListeners(){
		autoSizeCheckBox.addChangeListener(this);

		bhvCombo.addActionListener(this);
		navtablayCombo.addActionListener(this);
		refreshCombo.addActionListener(this);
		clearWFCombo.addActionListener(this);
		linkTypeCombo.addActionListener(this);

		bookCheckBox.addActionListener(this);
		navCheckBox.addActionListener(this);

		flagFrameCheckBox.addActionListener(this);
		treeXpndCheckBox.addActionListener(this);
		saveTwistCheckBox.addActionListener(this);

		autoLinkCheckBox.addActionListener(this);
		autoUpdateCheckBox.addActionListener(this);
		actionXpndCheckBox.addActionListener(this);
		showTTCheckBox.addActionListener(this);
		autosortCheckBox.addActionListener(this);
		enhancedFlagCheckBox.addActionListener(this);
		showSrcNameCheckBox.addActionListener(this);
		
		autoSizeCheckBox.addActionListener(this);
		ifldRowsSize.getDocument().addDocumentListener(this);
		ifldColsSize.getDocument().addDocumentListener(this);
	}
	private void init() {
		initTabLayout();
		initTabPlacement();
		initRefreshType();
        initClearType();
        initLinkType();

        ifldRowsSize.setErrTitle("preference.err-title");
        ifldColsSize.setErrTitle("preference.err-title");

		ifldRowsSize.setToolTipText(Utils.getToolTip("autoSize-rows"));
		ifldColsSize.setToolTipText(Utils.getToolTip("autoSize-cols"));

		bookCheckBox.setToolTipText(Utils.getToolTip("loadBookNew"));
		bookCheckBox.setMnemonic(Utils.getMnemonic("loadBookNew"));
		navCheckBox.setToolTipText(Utils.getToolTip("navdivloc"));
		navCheckBox.setMnemonic(Utils.getMnemonic("navdivloc"));

		flagFrameCheckBox.setToolTipText(Utils.getToolTip("vert.flag.frame"));
		flagFrameCheckBox.setMnemonic(Utils.getMnemonic("vert.flag.frame"));
		treeXpndCheckBox.setToolTipText(Utils.getToolTip("tree.xpnd"));
		treeXpndCheckBox.setMnemonic(Utils.getMnemonic("tree.xpnd"));
		saveTwistCheckBox.setToolTipText(Utils.getToolTip("tree.save.xpnd"));
		saveTwistCheckBox.setMnemonic(Utils.getMnemonic("tree.save.xpnd"));
		autoLinkCheckBox.setToolTipText(Utils.getToolTip("auto.link"));
		autoLinkCheckBox.setMnemonic(Utils.getMnemonic("auto.link"));
		autoUpdateCheckBox.setMnemonic(Utils.getMnemonic("autoUpdate"));
		autoUpdateCheckBox.setToolTipText(Utils.getToolTip("autoUpdate"));
		actionXpndCheckBox.setMnemonic(Utils.getMnemonic("action.xpnd"));
		actionXpndCheckBox.setToolTipText(Utils.getToolTip("action.xpnd"));
		showTTCheckBox.setMnemonic(Utils.getMnemonic("action.tt"));
		showTTCheckBox.setToolTipText(Utils.getToolTip("action.tt"));
		autosortCheckBox.setMnemonic(Utils.getMnemonic("edit.autosort"));
		autosortCheckBox.setToolTipText(Utils.getToolTip("edit.autosort"));
		enhancedFlagCheckBox.setMnemonic(Utils.getMnemonic("edit.enhancedflag"));
		enhancedFlagCheckBox.setToolTipText("edit.enhancedflag");
		
		showSrcNameCheckBox.setMnemonic(Utils.getMnemonic("behavior.src"));
		showSrcNameCheckBox.setToolTipText(Utils.getToolTip("behavior.src"));
		
		autoSizeCheckBox.setMnemonic(Utils.getMnemonic("autoSize"));
		autoSizeCheckBox.setToolTipText(Utils.getToolTip("autoSize"));

        bookCheckBox.setOpaque(false);
        navCheckBox.setOpaque(false);

        flagFrameCheckBox.setOpaque(false);
        treeXpndCheckBox.setOpaque(false);
        saveTwistCheckBox.setOpaque(false);

        autoLinkCheckBox.setOpaque(false);
        actionXpndCheckBox.setOpaque(false);
        showTTCheckBox.setOpaque(false);
        autosortCheckBox.setOpaque(false);
        enhancedFlagCheckBox.setOpaque(false);
        showSrcNameCheckBox.setOpaque(false);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
        GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
        col1.addComponent(lblTabPlacement);
        col1.addComponent(lblNavTabLay);
        col1.addComponent(lblRefresh);
        col1.addComponent(lblClearWF);
        col1.addComponent(lblLinkType);
        col1.addComponent(bookCheckBox);
        col1.addComponent(flagFrameCheckBox);
        col1.addComponent(treeXpndCheckBox);
        col1.addComponent(autoSizeCheckBox);
        col1.addComponent(autoUpdateCheckBox);
        col1.addComponent(showTTCheckBox);
        col1.addComponent(autosortCheckBox);
        leftToRight.addGroup(col1);

        GroupLayout.ParallelGroup col2 = layout.createParallelGroup();
        col2.addComponent(bhvCombo);
        col2.addComponent(navtablayCombo);
        col2.addComponent(navCheckBox);
        col2.addComponent(saveTwistCheckBox);
        col2.addComponent(refreshCombo);
        col2.addComponent(clearWFCombo);
        col2.addComponent(linkTypeCombo);
        col2.addComponent(autoLinkCheckBox);
        col2.addComponent(showSrcNameCheckBox);
        col2.addComponent(enhancedFlagCheckBox);
        GroupLayout.SequentialGroup rowColSize = layout.createSequentialGroup();
        rowColSize.addComponent(ifldRowsSize);
        rowColSize.addComponent(ifldColsSize);
        col2.addGroup(rowColSize);
        col2.addComponent(actionXpndCheckBox);

        leftToRight.addGroup(col2);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        GroupLayout.ParallelGroup tabRow = layout.createParallelGroup();
        tabRow.addComponent(lblTabPlacement);
        tabRow.addComponent(bhvCombo);
        topToBottom.addGroup(tabRow);
        tabRow = layout.createParallelGroup();
        tabRow.addComponent(lblNavTabLay);
        tabRow.addComponent(navtablayCombo);
        topToBottom.addGroup(tabRow);
        GroupLayout.ParallelGroup refreshRow = layout.createParallelGroup();
        refreshRow.addComponent(lblRefresh);
        refreshRow.addComponent(refreshCombo);
        topToBottom.addGroup(refreshRow);
        GroupLayout.ParallelGroup clearwfRow = layout.createParallelGroup();
        clearwfRow.addComponent(lblClearWF);
        clearwfRow.addComponent(clearWFCombo);
        topToBottom.addGroup(clearwfRow);
        GroupLayout.ParallelGroup linktypeRow = layout.createParallelGroup();
        linktypeRow.addComponent(lblLinkType);
        linktypeRow.addComponent(linkTypeCombo);
        topToBottom.addGroup(linktypeRow);
        GroupLayout.ParallelGroup bookmarkRow = layout.createParallelGroup();
        bookmarkRow.addComponent(bookCheckBox);
        bookmarkRow.addComponent(navCheckBox);
        topToBottom.addGroup(bookmarkRow);
        GroupLayout.ParallelGroup gcRow = layout.createParallelGroup();
        gcRow.addComponent(flagFrameCheckBox);
        gcRow.addComponent(saveTwistCheckBox);
        topToBottom.addGroup(gcRow);

        GroupLayout.ParallelGroup xpandRow = layout.createParallelGroup();
        xpandRow.addComponent(treeXpndCheckBox);
        xpandRow.addComponent(autoLinkCheckBox);
        topToBottom.addGroup(xpandRow);
        GroupLayout.ParallelGroup sizeRow = layout.createParallelGroup();
        sizeRow.addComponent(autoSizeCheckBox);
        sizeRow.addComponent(ifldRowsSize);
        sizeRow.addComponent(ifldColsSize);
        topToBottom.addGroup(sizeRow);
        GroupLayout.ParallelGroup updateRow = layout.createParallelGroup();
        updateRow.addComponent(autoUpdateCheckBox);
        updateRow.addComponent(actionXpndCheckBox);
        topToBottom.addGroup(updateRow);

        GroupLayout.ParallelGroup ttRow = layout.createParallelGroup();
        ttRow.addComponent(showTTCheckBox);
        ttRow.addComponent(showSrcNameCheckBox);
        topToBottom.addGroup(ttRow);
        
        ttRow = layout.createParallelGroup();
        ttRow.addComponent(autosortCheckBox);
        ttRow.addComponent(enhancedFlagCheckBox);
        topToBottom.addGroup(ttRow);
        
        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);
    }

    private void initTabPlacement() {
		lblTabPlacement.setLabelFor(bhvCombo);
		lblTabPlacement.setDisplayedMnemonic(Utils.getMnemonic("tabLay"));
		bhvCombo.setToolTipText(Utils.getToolTip("tabLay"));

        String[] sArray = Routines.getStringArray(Utils.getResource("tabPos"), ",");
        for (int i = 0; i < sArray.length; ++i) {
            bhvCombo.addItem(sArray[i]);
        }
    }
    private void initTabLayout() {
		lblNavTabLay.setLabelFor(navtablayCombo);
		lblNavTabLay.setDisplayedMnemonic(Utils.getMnemonic("tabLay"));
		navtablayCombo.setToolTipText(Utils.getToolTip("tabLay"));

        String[] sArray = Routines.getStringArray(Utils.getResource("tabPolicy"), ",");
        for (int i = 0; i < sArray.length; ++i) {
        	navtablayCombo.addItem(sArray[i]);
        }
    }

    private void initRefreshType() {
		lblRefresh.setLabelFor(refreshCombo);
		lblRefresh.setDisplayedMnemonic(Utils.getMnemonic("refreshType"));
		refreshCombo.setToolTipText(Utils.getToolTip("refreshType"));

        String[] sArray = Routines.getStringArray(Utils.getResource("refType"), ",");
        for (int i = 0; i < sArray.length; ++i) {
            refreshCombo.addItem(sArray[i]);
        }
    }

    private void initClearType() {
		lblClearWF.setLabelFor(clearWFCombo);
		lblClearWF.setDisplayedMnemonic(Utils.getMnemonic("clrWF"));
		clearWFCombo.setToolTipText(Utils.getToolTip("clrWF"));

        String[] sArray = Routines.getStringArray(Utils.getResource("clrWFType"), ",");
        for (int i = 0; i < sArray.length; ++i) {
            clearWFCombo.addItem(sArray[i]);
        }
    }

    private void initLinkType() {
		lblLinkType.setLabelFor(linkTypeCombo);
		lblLinkType.setDisplayedMnemonic(Utils.getMnemonic("lnkmde"));
		linkTypeCombo.setToolTipText(Utils.getToolTip("lnkmde"));

        String[] sArray = EACMProperties.getLinkTypes();
        for (int i = 0; i < sArray.length; ++i) {
            linkTypeCombo.addItem(Utils.getResource(sArray[i]));
        }
    }

    private void buildButtonPanel(){
    	btnPnl = new JPanel(new BorderLayout(5, 5));

    	saveAction = new SaveAction();
    	bSave = new JButton(saveAction);
//  	this is needed or the mnemonic doesnt activate
    	bSave.setMnemonic((char)((Integer)bSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	bSave.addKeyListener(prefMgr.getKeyListener());

    	bReset = new JButton(new ResetAction());
    	bReset.setMnemonic((char)((Integer)bReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	bReset.addKeyListener(prefMgr.getKeyListener());

    	btnPnl.add(bReset,BorderLayout.EAST);
    	btnPnl.add(bSave,BorderLayout.WEST);
    }

    /**
     * should bookmarks go into a new tab
     * @return
     */
    public static boolean loadBookmark(){
    	return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_LOAD_BOOKMARK, DEFAULT_LOAD_BOOKMARK);
    }
    /**
     * get the saved value for location of navigation split pane divider location
     * @return
     */
    public static int getNavDividerLocation(){
    	int location = -1;
    	if(!NavLayoutPref.isDualNavLayout()){
    		if(Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_NAV_SPLIT, DEFAULT_NAV_SPLIT)){
    			// this is not saved as a preference because screen location and size is not
    			location = Preferences.userNodeForPackage(EACM.class).getInt(PREF_NAV_SPLIT_LOCATION, location);
    		}
    	}
    	return location;
    }
    /**
     * save the last nav divider location if save is checked
     * @param div
     */
    public static void setNavDividerLocation(int div){
    	if(!NavLayoutPref.isDualNavLayout()){
    		if(Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_NAV_SPLIT, DEFAULT_NAV_SPLIT)){
    			// this is not saved as a preference because screen location and size is not
    			Preferences.userNodeForPackage(EACM.class).putInt(PREF_NAV_SPLIT_LOCATION, div);
    		}
    	}
    }


    /**
     * should action tree be fully expanded 
     * @return
     */
    public static boolean isTreeExpanded(){
    	return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_TREE_EXPANDED,DEFAULT_PREF_TREE_EXPANDED);
    }
    public static boolean useSavedTwisties(){
       	return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_USE_SAVEDTREE_EXPANSION,DEFAULT_USE_SAVEDTREE_EXPANSION);
    }
    public static int getTabPlacement(){
    	return Preferences.userNodeForPackage(PrefMgr.class).getInt(PREF_TAB_PLACEMENT, SwingConstants.TOP);
    }
    public static int getNavTabLayoutPolicy(){
    	return Preferences.userNodeForPackage(PrefMgr.class).getInt(PREF_NAV_TAB_LAYOUT, JTabbedPane.WRAP_TAB_LAYOUT);
    }
    public static int getClearWFType(){
    	return Preferences.userNodeForPackage(PrefMgr.class).getInt(PREF_WF_CLEAR_TYPE, DEFAULT_WF_CLEAR_TYPE);
    }
    /**
     * move slider to show action tree for things like whereused
     * @return
     */
    public static boolean isExpandAction() {
        return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_ACTION_EXPANDED, DEFAULT_PREF_ACTION_EXPANDED);
    }
    /**
     * display tooltips on table cells
     * @return
     */
    public static boolean showTableTooltips() {
        return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_SHOWTT, DEFAULT_PREF_SHOWTT);
    }
    public static boolean isEditAutoSort() {
        return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_EDITAUTOSORT, DEFAULT_PREF_EDITAUTOSORT);
    }
    public static boolean showSrcMethodName() {
        return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_SHOWSRC, DEFAULT_PREF_SHOWSRC);
    }
    public static int getRefreshType(){
    	return Preferences.userNodeForPackage(PrefMgr.class).getInt(PREF_REFRESH_TYPE, DEFAULT_REFRESH_TYPE);
    }
    public static boolean alwaysRefresh() {
        int iTest = Routines.getIntProperty("refType.always");
        return getRefreshType() == iTest;
    }

    public static boolean neverRefresh() {
        int iTest = Routines.getIntProperty("refType.never");
        return getRefreshType() == iTest;
    }

    private void loadBehavior() {
        bookCheckBox.setSelected(loadBookmark());
        navCheckBox.setSelected(prefMgr.getPrefNode().getBoolean(PREF_NAV_SPLIT, DEFAULT_NAV_SPLIT));

        flagFrameCheckBox.setSelected(isVertFlagFrame());
        treeXpndCheckBox.setSelected(isTreeExpanded());
        saveTwistCheckBox.setSelected(useSavedTwisties());

        //only look at arm file autoLinkCheckBox.setSelected(prefMgr.getPrefNode().getBoolean(PREF_AUTO_LINK,Utils.isArmed(AUTOLINK_ARM_FILE)));
        autoLinkCheckBox.setSelected(Utils.isArmed(AUTOLINK_ARM_FILE));
        //only look at arm file
        enhancedFlagCheckBox.setSelected(Utils.isArmed(ENHANCED_FLAG_EDIT));

        if (EACMProperties.isUpdateMandatory()) {
			autoUpdateCheckBox.setSelected(true);
			autoUpdateCheckBox.setEnabled(false);
		} else {
	        autoUpdateCheckBox.setSelected(Utils.isArmed(AUTO_UPDATE_FILE));
		}

        autoSizeCheckBox.setSelected(prefMgr.getPrefNode().getBoolean(PREF_AUTO_SIZE, DEFAULT_AUTO_SIZE));
        actionXpndCheckBox.setSelected(isExpandAction());

        ifldRowsSize.setText(""+prefMgr.getPrefNode().getInt(PREF_AUTO_SIZE_COUNT, DEFAULT_ROW_SIZE_COUNT));
        ifldRowsSize.setEnabled(autoSizeCheckBox.isSelected());

        ifldColsSize.setText(""+getMaxColumns());
        ifldColsSize.setEnabled(autoSizeCheckBox.isSelected());

        bhvCombo.setSelectedIndex(getTabPlacement()-1); //SwingConstants is 1 based
        navtablayCombo.setSelectedIndex(getNavTabLayoutPolicy()); 
        refreshCombo.setSelectedIndex(getRefreshType());
        clearWFCombo.setSelectedIndex(getClearWFType());
        linkTypeCombo.setSelectedIndex(getLinkType());

        showTTCheckBox.setSelected(showTableTooltips());
        showSrcNameCheckBox.setSelected(showSrcMethodName());
        autosortCheckBox.setSelected(isEditAutoSort());
    }

    private void resetBehavior() {
    	prefMgr.getPrefNode().putInt(PREF_NAV_TAB_LAYOUT, JTabbedPane.WRAP_TAB_LAYOUT);
    	prefMgr.getPrefNode().putInt(PREF_TAB_PLACEMENT, SwingConstants.TOP);

    	prefMgr.getPrefNode().putBoolean(PREF_TREE_EXPANDED,DEFAULT_PREF_TREE_EXPANDED);
    	prefMgr.getPrefNode().putBoolean(PREF_USE_SAVEDTREE_EXPANSION,DEFAULT_USE_SAVEDTREE_EXPANSION);
    	
    	prefMgr.getPrefNode().putBoolean(PREF_ACTION_EXPANDED, DEFAULT_PREF_ACTION_EXPANDED);
    	prefMgr.getPrefNode().putBoolean(PREF_SHOWTT, DEFAULT_PREF_SHOWTT);
    	prefMgr.getPrefNode().putBoolean(PREF_EDITAUTOSORT, DEFAULT_PREF_EDITAUTOSORT);
    	
    	prefMgr.getPrefNode().putBoolean(PREF_SHOWSRC, DEFAULT_PREF_SHOWSRC);

    	prefMgr.getPrefNode().putBoolean(PREF_VERT_FLAG_FRAME, DEFAULT_PREF_VERT_FLAG_FRAME);
    	//prefMgr.getPrefNode().putBoolean(PREF_AUTO_LINK,Utils.isArmed(AUTOLINK_ARM_FILE));

    	prefMgr.getPrefNode().putInt(PREF_WF_CLEAR_TYPE, DEFAULT_WF_CLEAR_TYPE);

    	prefMgr.getPrefNode().putInt(PREF_AUTO_SIZE_COUNT, DEFAULT_ROW_SIZE_COUNT);
    	prefMgr.getPrefNode().putInt(PREF_AUTO_SIZE_COLS_COUNT, DEFAULT_COL_SIZE_COUNT);

    	prefMgr.getPrefNode().putInt(PREF_REFRESH_TYPE, DEFAULT_REFRESH_TYPE);
    	prefMgr.getPrefNode().putInt(PREF_LINK_TYPE, DEFAULT_LINK_TYPE);

        prefMgr.getPrefNode().putBoolean(PREF_AUTO_SIZE, DEFAULT_AUTO_SIZE);
        prefMgr.getPrefNode().putBoolean(PREF_LOAD_BOOKMARK, DEFAULT_LOAD_BOOKMARK);
        prefMgr.getPrefNode().putBoolean(PREF_NAV_SPLIT,DEFAULT_NAV_SPLIT);

        if (EACMProperties.isUpdateMandatory()) {
	        updateArmFunction(AUTO_UPDATE_FILE, true);
		} else {
			updateArmFunction(AUTO_UPDATE_FILE, PREF_UPDATE_DEFAULT);
		}
        updateArmFunction(AUTOLINK_ARM_FILE, Utils.isArmed(AUTOLINK_DEFAULT));
        loadBehavior();
    }
    private void saveBehavior() {
    	prefMgr.getPrefNode().putInt(PREF_TAB_PLACEMENT, (bhvCombo.getSelectedIndex() + 1)); //SwingConstants is 1 based
    	prefMgr.getPrefNode().putInt(PREF_NAV_TAB_LAYOUT, (navtablayCombo.getSelectedIndex()));

    	prefMgr.getPrefNode().putInt(PREF_REFRESH_TYPE, refreshCombo.getSelectedIndex());
        prefMgr.getPrefNode().putInt(PREF_WF_CLEAR_TYPE, clearWFCombo.getSelectedIndex());
        prefMgr.getPrefNode().putInt(PREF_LINK_TYPE, linkTypeCombo.getSelectedIndex());
        EACM.getEACM().getTabbedPane().updateTabPlacement();
        EACM.getEACM().getTabbedPane().updateTabLayoutPolicy();

        prefMgr.getPrefNode().putBoolean(PREF_LOAD_BOOKMARK, bookCheckBox.isSelected());
        prefMgr.getPrefNode().putBoolean(PREF_TREE_EXPANDED, treeXpndCheckBox.isSelected());
        prefMgr.getPrefNode().putBoolean(PREF_USE_SAVEDTREE_EXPANSION,saveTwistCheckBox.isSelected());

        prefMgr.getPrefNode().putBoolean(PREF_AUTO_SIZE, autoSizeCheckBox.isSelected());
     	//prefMgr.getPrefNode().putBoolean(PREF_AUTO_LINK,autoLinkCheckBox.isSelected());

     	prefMgr.getPrefNode().putInt(PREF_AUTO_SIZE_COUNT, Integer.parseInt(ifldRowsSize.getText().trim()));
     	prefMgr.getPrefNode().putInt(PREF_AUTO_SIZE_COLS_COUNT, Integer.parseInt(ifldColsSize.getText().trim()));

        prefMgr.getPrefNode().putBoolean(PREF_SHOWTT, showTTCheckBox.isSelected());
        prefMgr.getPrefNode().putBoolean(PREF_EDITAUTOSORT, autosortCheckBox.isSelected());
        
        prefMgr.getPrefNode().putBoolean(PREF_SHOWSRC, showSrcNameCheckBox.isSelected());
        
        prefMgr.getPrefNode().putBoolean(PREF_VERT_FLAG_FRAME, flagFrameCheckBox.isSelected());

        prefMgr.getPrefNode().putBoolean(PREF_ACTION_EXPANDED, actionXpndCheckBox.isSelected());
        prefMgr.getPrefNode().putBoolean(PREF_NAV_SPLIT, navCheckBox.isSelected());

        updateArmFunction(AUTOLINK_ARM_FILE, autoLinkCheckBox.isSelected());
        updateArmFunction(ENHANCED_FLAG_EDIT,enhancedFlagCheckBox.isSelected());
        updateArmFunction(AUTO_UPDATE_FILE, autoUpdateCheckBox.isSelected());
    }
    private void updateArmFunction(String _armFile, boolean _enabled) {
        if (_enabled) {
            if (!Utils.isArmed(_armFile)) {
            	Utils.arm(_armFile);
            }
        } else {
            if (Utils.isArmed(_armFile)) {
            	Utils.disarm(_armFile);
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent _ce) {
        Object o = _ce.getSource();
        if (o == autoSizeCheckBox) {
            ifldRowsSize.setEnabled(autoSizeCheckBox.isSelected());
            ifldColsSize.setEnabled(autoSizeCheckBox.isSelected());
            saveAction.setEnabled(true);
        }
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.preference.Preferencable#dereference()
     */
    public void dereference() {
		ifldRowsSize.getDocument().removeDocumentListener(this);
		ifldColsSize.getDocument().removeDocumentListener(this);
		
		bhvCombo.removeActionListener(this);
		navtablayCombo.removeActionListener(this);
		refreshCombo.removeActionListener(this);
		clearWFCombo.removeActionListener(this);
		linkTypeCombo.removeActionListener(this);

		bookCheckBox.removeActionListener(this);
		navCheckBox.removeActionListener(this);

		flagFrameCheckBox.removeActionListener(this);
		treeXpndCheckBox.removeActionListener(this);
		saveTwistCheckBox.removeActionListener(this);

		autoLinkCheckBox.removeActionListener(this);
		autoUpdateCheckBox.removeActionListener(this);
		actionXpndCheckBox.removeActionListener(this);
		showTTCheckBox.removeActionListener(this);
		autosortCheckBox.removeActionListener(this);
		enhancedFlagCheckBox.removeActionListener(this);
		showSrcNameCheckBox.removeActionListener(this);
		
		autoSizeCheckBox.removeActionListener(this);
		
		autoSizeCheckBox.removeChangeListener(this);
		autoSizeCheckBox.removeAll();
		autoSizeCheckBox.setUI(null);
		autoSizeCheckBox=null;

    	bookCheckBox.removeAll();
    	bookCheckBox.setUI(null);
    	bookCheckBox=null;

    	flagFrameCheckBox.removeAll();
    	flagFrameCheckBox.setUI(null);
    	flagFrameCheckBox=null;

    	treeXpndCheckBox.removeAll();
    	treeXpndCheckBox.setUI(null);
    	treeXpndCheckBox=null;
    	
    	saveTwistCheckBox.removeAll();
    	saveTwistCheckBox.setUI(null);
    	saveTwistCheckBox=null;
	
    	autoLinkCheckBox.removeAll();
    	autoLinkCheckBox.setUI(null);
    	autoLinkCheckBox=null;

    	autoUpdateCheckBox.removeAll();
    	autoUpdateCheckBox.setUI(null);
    	autoUpdateCheckBox=null;

    	actionXpndCheckBox.removeAll();
    	actionXpndCheckBox.setUI(null);
    	actionXpndCheckBox=null;

    	showTTCheckBox.removeAll();
    	showTTCheckBox.setUI(null);
    	showTTCheckBox=null;
    	
    	autosortCheckBox.removeAll();
    	autosortCheckBox.setUI(null);
    	autosortCheckBox=null;
    	
    	enhancedFlagCheckBox.removeAll();
    	enhancedFlagCheckBox.setUI(null);
    	enhancedFlagCheckBox = null;
    	
    	showSrcNameCheckBox.removeAll();
    	showSrcNameCheckBox.setUI(null);
    	showSrcNameCheckBox=null;

    	ifldRowsSize.dereference();
    	ifldRowsSize=null;
    	ifldColsSize.dereference();
    	ifldColsSize=null;

    	bhvCombo.removeAllItems();
    	bhvCombo.removeAll();
    	bhvCombo.setUI(null);
    	bhvCombo=null;
    	
    	navtablayCombo.removeAllItems();
    	navtablayCombo.removeAll();
    	navtablayCombo.setUI(null);
    	navtablayCombo=null;

    	refreshCombo.removeAllItems();
    	refreshCombo.removeAll();
    	refreshCombo.setUI(null);
    	refreshCombo=null;

    	clearWFCombo.removeAllItems();
    	clearWFCombo.removeAll();
    	clearWFCombo.setUI(null);
    	clearWFCombo=null;

    	linkTypeCombo.removeAllItems();
    	linkTypeCombo.removeAll();
    	linkTypeCombo.setUI(null);
    	linkTypeCombo=null;

    	lblLinkType.setLabelFor(null);
    	lblLinkType.removeAll();
    	lblLinkType.setUI(null);
    	lblLinkType=null;

    	lblClearWF.setLabelFor(null);
    	lblClearWF.removeAll();
    	lblClearWF.setUI(null);
    	lblClearWF=null;

    	lblRefresh.setLabelFor(null);
    	lblRefresh.removeAll();
    	lblRefresh.setUI(null);
    	lblRefresh=null;

    	lblTabPlacement.setLabelFor(null);
    	lblTabPlacement.removeAll();
    	lblTabPlacement.setUI(null);
    	lblTabPlacement=null;

    	lblNavTabLay.setLabelFor(null);
    	lblNavTabLay.removeAll();
    	lblNavTabLay.setUI(null);
    	lblNavTabLay=null;

    	saveAction.dereference();
    	saveAction = null;
    	// deref button panel
    	btnPnl.removeAll();
    	btnPnl.setUI(null);

    	bSave.removeKeyListener(prefMgr.getKeyListener());
    	bSave.setAction(null);
    	bSave.setUI(null);
    	ResetAction ra  = (ResetAction)bReset.getAction();
    	ra.dereference();
    	bReset.removeKeyListener(prefMgr.getKeyListener());
    	bReset.setAction(null);
    	bReset.setUI(null);

    	prefMgr = null;
    	removeAll();
    	setUI(null);
    }
    public JPanel getButtonPanel() {
    	return btnPnl;
    }

    public static boolean canSize(int _size) {
    	// if can auto size
        boolean b = Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_AUTO_SIZE, DEFAULT_AUTO_SIZE);
        if (b) {
        	// get max size
            int iMax = Preferences.userNodeForPackage(PrefMgr.class).getInt(PREF_AUTO_SIZE_COUNT, DEFAULT_ROW_SIZE_COUNT);
            if (iMax > 0) {
                b = iMax > _size;
            }
        }
        return b;
    }

    public static int getMaxColumns(){
    	return Preferences.userNodeForPackage(PrefMgr.class).getInt(PREF_AUTO_SIZE_COLS_COUNT, DEFAULT_COL_SIZE_COUNT);
    }
    public static int getLinkType(){
    	return Preferences.userNodeForPackage(PrefMgr.class).getInt(PREF_LINK_TYPE, DEFAULT_LINK_TYPE);
    }
    /**
     * eacm.link.code0=NODUPES
eacm.link.code1=
eacm.link.code2=REPLACEALL
     * @return
     */
    public static String getLinkTypeKey() {
        int i = getLinkType();
        return EACMProperties.getProperty("eacm.link.code" + i);
    }

    public static boolean isVertFlagFrame() {
        return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_VERT_FLAG_FRAME, DEFAULT_PREF_VERT_FLAG_FRAME);
    }

    private class ResetAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref";
		ResetAction() {
            super(CMD,"Behavior");
        }

		public void actionPerformed(ActionEvent e) {
			resetBehavior();
		}
    }
    private class SaveAction extends EACMAction {
    	private static final long serialVersionUID = 1L;
    	private static final String CMD = "save.pref";
    	SaveAction() {
    		super(CMD,"Behavior");
    		setEnabled(false);
    	}
		/* (non-Javadoc)
		 * @see com.ibm.eacm.actions.EACMAction#canEnable()
		 */
		protected boolean canEnable(){ 
			return checkValues(); 
		}
		
		private boolean checkValues(){
			if(loadBookmark()!=bookCheckBox.isSelected()){
				return true;
			}
	        if(navCheckBox.isSelected()!=prefMgr.getPrefNode().getBoolean(PREF_NAV_SPLIT, DEFAULT_NAV_SPLIT)){
	        	return true;
	        }
			if(isTreeExpanded()!=treeXpndCheckBox.isSelected()){
				return true;
			}
			if(useSavedTwisties()!=saveTwistCheckBox.isSelected()){
				return true;
			}
	   
			if(isVertFlagFrame()!=flagFrameCheckBox.isSelected()){
				return true;
			}

			if(isExpandAction()!=actionXpndCheckBox.isSelected()){
				return true;
			}
	
			if(getRefreshType()!=refreshCombo.getSelectedIndex()){
				return true;
			}
			if(getClearWFType()!=clearWFCombo.getSelectedIndex()){
				return true;
			}
			if(getLinkType()!=linkTypeCombo.getSelectedIndex()){
				return true;
			}
			
			//if(prefMgr.getPrefNode().getBoolean(PREF_AUTO_LINK,Utils.isArmed(AUTOLINK_ARM_FILE))!=autoLinkCheckBox.isSelected()){
			if(Utils.isArmed(AUTOLINK_ARM_FILE)!=autoLinkCheckBox.isSelected()){
				return true;
			}
			
			if(Utils.isArmed(ENHANCED_FLAG_EDIT) !=enhancedFlagCheckBox.isSelected()){
				return true;
			}

			if(!ifldColsSize.getText().equals(""+getMaxColumns())){
				return true;
			}
			if((getTabPlacement()-1)!=bhvCombo.getSelectedIndex()){ //SwingConstants is 1 based
				return true;
			}
			
			if((getNavTabLayoutPolicy())!=navtablayCombo.getSelectedIndex()){ 
				return true;
			}

	        if (!EACMProperties.isUpdateMandatory()) {
		        if(autoUpdateCheckBox.isSelected() !=Utils.isArmed(AUTO_UPDATE_FILE)){
		        	return true;
		        }
			}

	        if(autoSizeCheckBox.isSelected() != prefMgr.getPrefNode().getBoolean(PREF_AUTO_SIZE, DEFAULT_AUTO_SIZE)){
	        	return true;
	        }

	    	if(!ifldRowsSize.getText().equals(""+prefMgr.getPrefNode().getInt(PREF_AUTO_SIZE_COUNT, DEFAULT_ROW_SIZE_COUNT))){
				return true;
			}

	        if(showTTCheckBox.isSelected() != showTableTooltips()){
	        	return true;
	        }
	        
	        if(autosortCheckBox.isSelected() != isEditAutoSort()){
	        	return true;
	        }
	        
	        if(showSrcNameCheckBox.isSelected() != showSrcMethodName()){
	        	return true;
	        }

			return false;
		}
		public void actionPerformed(ActionEvent e) {
			saveBehavior();
			setEnabled(false);
		}
    }
	/* (non-Javadoc)
	 * called when dialog is closing
	 * @see com.ibm.eacm.preference.Preferencable#isClosing()
	 */
	public void isClosing() {}
	/* (non-Javadoc)
	 * notify user that changes have not been saved
	 * @see com.ibm.eacm.preference.Preferencable#hasChanges()
	 */
	public boolean hasChanges() { 
		return saveAction.isEnabled();
	} 

	/* (non-Javadoc)
	 * @see com.ibm.eacm.preference.Preferencable#updateFromPrefs()
	 */
	public void updateFromPrefs() {
		loadBehavior();
	}

	/**
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		saveAction.setEnabled(true);
	}

	/**
	 * @param e
	 */
	public void changedUpdate(DocumentEvent e) {
		saveAction.setEnabled(true);
	}

	public void insertUpdate(DocumentEvent e) {
		saveAction.setEnabled(true);
	}

	public void removeUpdate(DocumentEvent e) {
		saveAction.setEnabled(true);
	}
}
