//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.preference;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.EACMGlobals;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.awt.event.*;

/**
 * This class manages preferences for colors
 * @author Wendy Stimpson
 */
//$Log: ColorPref.java,v $
//Revision 1.6  2015/01/05 19:15:34  stimpsow
//use Theme for background colors
//
//Revision 1.5  2014/12/12 21:43:26  stimpsow
//Save color override based on mw instance
//
//Revision 1.4  2013/12/04 22:03:36  wendy
//allow colors to be set based on instance
//
//Revision 1.3  2013/07/18 20:10:06  wendy
//fix compiler warnings
//
//Revision 1.2  2013/07/18 18:58:16  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public class ColorPref extends JPanel implements ActionListener,Preferencable, EACMGlobals
{
	private static final long serialVersionUID = 1L;
	/**
	 * preferred color parameters preference keys
	 */
	public static final String PREF_COLOR_FOREGROUND = "foreground.color";
	public static final String PREF_COLOR_FOUND_FOCUS = "preferred.found.focus.color";
	
	private static final String PREF_COLOR_BACKGROUND = "background.color";
	private static final String PREF_COLOR_WU_BG = "wubackground.color";

	private static final String PREF_COLOR_REQUIRED = "preferred.required.color";
	private static final String PREF_COLOR_LOW_REQUIRED = "preferred.low.required.color";
	private static final String PREF_COLOR_LOW = "preferred.low.color";
	private static final String PREF_COLOR_OK = "preferred.ok.color";

	private static final String PREF_COLOR_OVERRIDE = "override.system.color"; 
	/**
	 * default colors
	 * must be ColorUIResource or UIManager cant use them correctly
	 *  Objects colored with java.awt.Color are not be modified if UI changes occur. 
	 *  Objects colored with javax.swing.plaf.ColorUIResource do respond. 
	 */
	public static final Color DEFAULT_COLOR_ENABLED_FOREGROUND = new ColorUIResource(Color.black);
		//UIManager.getLookAndFeelDefaults().getColor("Panel.foreground"); 
	// this matches the Metal cross platform LNF
	public static final Color DEFAULT_COLOR_ENABLED_BACKGROUND = new ColorUIResource(204,204,204);//Color.decode("#CCCCCC")
		//UIManager.getLookAndFeelDefaults().getColor("Panel.background");
	public static final Color DEFAULT_COLOR_HIGHLIGHT = new ColorUIResource(Color.decode("#000080"));
	public static final Color DEFAULT_COLOR_FOUND = new ColorUIResource(Color.blue);
	public static final Color DEFAULT_COLOR_FOUND_FOCUS = new ColorUIResource(Color.decode("#DE5AAd")); //magenta

	private static final Color DEFAULT_COLOR_REQUIRED = new ColorUIResource(Color.decode("#FFCCCC")); //red
	private static final Color DEFAULT_COLOR_LOW_REQUIRED = new ColorUIResource(Color.decode("#FFCE9C")); //orange
	private static final Color DEFAULT_COLOR_LOW = new ColorUIResource(Color.decode("#FFFF66")); //yellow
	private static final Color DEFAULT_COLOR_OK = new ColorUIResource(Color.decode("#CCFF99")); //green
	private static final Color DEFAULT_COLOR_LOCK = new ColorUIResource(Color.decode("#C6EFF7")); //cyan
	private static final Color DEFAULT_COLOR_ASSOC = new ColorUIResource(Color.decode("#EA6F75"));
	private static final Color DEFAULT_COLOR_CHILD = new ColorUIResource(Color.decode("#484473"));
	private static final Color DEFAULT_COLOR_PARENT = new ColorUIResource(Color.decode("#A21A02"));
	private static final boolean DEFAULT_COLOR_OVERRIDE = false; 
	private static final Color DEFAULT_COLOR_WU_BG = new ColorUIResource(Color.decode("#CCCCCC"));
	
    private ColorChooser colorChoose = null;
    private PrefMgr prefMgr=null;
    
	private JCheckBox chkOverride = new JCheckBox(Utils.getResource("color.override")); 
	private JPanel appPnl = new JPanel(new BorderLayout()); 
	
	private JPanel appPnl_0 = new JPanel(new GridLayout(3, 2, 0, 0));
	private JPanel appPnl_1 = new JPanel(new GridLayout(2, 2, 0, 0));
	private JPanel appPnl_2 = new JPanel(new GridLayout(3, 2, 0, 0));
	private JPanel appPnl_3 = new JPanel(new GridLayout(5, 2, 0, 0));
	private JPanel appPnl_North = new JPanel(new BorderLayout());
	private JPanel appPnl_South = new JPanel(new BorderLayout());

	private TitledBorder bord0 = new TitledBorder(Utils.getResource("enabled.border.title"));
	private TitledBorder bord1 = new TitledBorder(Utils.getResource("found.border.title"));
	private TitledBorder bord2 = new TitledBorder(Utils.getResource("relative.border.title"));
	private TitledBorder bord3 = new TitledBorder(Utils.getResource("edit.border.title"));

	private JLabel lblFore = new JLabel(Utils.getResource(PREF_COLOR_FOREGROUND));
	private JLabel lblBack = new JLabel(Utils.getResource(PREF_COLOR_BACKGROUND));
	private JLabel lblWuBg = new JLabel(Utils.getResource(PREF_COLOR_WU_BG));

	private JLabel lblFound = new JLabel(Utils.getResource(PREF_COLOR_FOUND));
	private JLabel lblFoundFocus = new JLabel(Utils.getResource(PREF_COLOR_FOUND_FOCUS));
	private JLabel lblAssoc = new JLabel(Utils.getResource(PREF_COLOR_ASSOC));
	private JLabel lblChild = new JLabel(Utils.getResource(PREF_COLOR_CHILD));
	private JLabel lblParent = new JLabel(Utils.getResource(PREF_COLOR_PARENT));
	private JLabel lblLock = new JLabel(Utils.getResource(PREF_COLOR_LOCK));
	private JLabel lblLow = new JLabel(Utils.getResource(PREF_COLOR_LOW));
	private JLabel lblLowReq = new JLabel(Utils.getResource(PREF_COLOR_LOW_REQUIRED));
	private JLabel lblCOK = new JLabel(Utils.getResource(PREF_COLOR_OK));
	private JLabel lblReq = new JLabel(Utils.getResource(PREF_COLOR_REQUIRED));
	
	private Hashtable<String, ColorAction> colorActionTbl = new Hashtable<String, ColorAction>();
	private ColorButton btnFore = null;
	private ColorButton btnBack = null;
	private ColorButton btnFound = null;
	private ColorButton btnFoundFocus = null;
	private ColorButton btnAssoc = null;
	private ColorButton btnWuBg = null;
	private ColorButton btnChild = null;
	private ColorButton btnParent = null;
	private ColorButton btnLock = null;
	private ColorButton btnLow = null;
	private ColorButton btnLowReq = null;
	private ColorButton btnCOK = null;
	private ColorButton btnReq = null;

    private JButton bSave = null;
    private JButton bReset = null;
    private JPanel btnPnl =null;

    /**
     * color Chooser
     */
    protected ColorPref(PrefMgr pp) {
        super(new BorderLayout());
        prefMgr = pp;
    	createActions();
    	init();
    	setBorder(BorderFactory.createTitledBorder(Utils.getResource("color.border")));// meet accessiblity 
    }
    private void createActions(){
		colorActionTbl.put(PREF_COLOR_FOREGROUND,new ColorAction(PREF_COLOR_FOREGROUND, DEFAULT_COLOR_ENABLED_FOREGROUND));
		colorActionTbl.put(PREF_COLOR_BACKGROUND,new ThemeAction(PREF_COLOR_BACKGROUND, DEFAULT_COLOR_ENABLED_BACKGROUND));

		colorActionTbl.put(PREF_COLOR_FOUND,new ColorAction(PREF_COLOR_FOUND, DEFAULT_COLOR_FOUND));
		colorActionTbl.put(PREF_COLOR_FOUND_FOCUS,new ColorAction(PREF_COLOR_FOUND_FOCUS, DEFAULT_COLOR_FOUND_FOCUS));

		colorActionTbl.put(PREF_COLOR_ASSOC,new ColorAction(PREF_COLOR_ASSOC, DEFAULT_COLOR_ASSOC));
		colorActionTbl.put(PREF_COLOR_WU_BG,new ColorAction(PREF_COLOR_WU_BG, DEFAULT_COLOR_WU_BG));
		
		colorActionTbl.put(PREF_COLOR_CHILD,new ColorAction(PREF_COLOR_CHILD, DEFAULT_COLOR_CHILD));
		colorActionTbl.put(PREF_COLOR_PARENT,new ColorAction(PREF_COLOR_PARENT, DEFAULT_COLOR_PARENT));

		colorActionTbl.put(PREF_COLOR_LOCK,new ColorAction(PREF_COLOR_LOCK, DEFAULT_COLOR_LOCK));
		colorActionTbl.put(PREF_COLOR_LOW,new ColorAction(PREF_COLOR_LOW, DEFAULT_COLOR_LOW));
		colorActionTbl.put(PREF_COLOR_LOW_REQUIRED,new ColorAction(PREF_COLOR_LOW_REQUIRED, DEFAULT_COLOR_LOW_REQUIRED));
		colorActionTbl.put(PREF_COLOR_OK,new ColorAction(PREF_COLOR_OK, DEFAULT_COLOR_OK));
		colorActionTbl.put(PREF_COLOR_REQUIRED,new ColorAction(PREF_COLOR_REQUIRED, DEFAULT_COLOR_REQUIRED));
		
		btnFore = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_FOREGROUND));
		btnBack = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_BACKGROUND));

		btnFound = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_FOUND));
		btnFoundFocus = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_FOUND_FOCUS));
		btnAssoc = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_ASSOC));
		btnWuBg = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_WU_BG));
		
		btnChild = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_CHILD));
		btnParent = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_PARENT));
		btnLock = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_LOCK));
		btnLow = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_LOW));
		btnLowReq = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_LOW_REQUIRED));
		btnCOK = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_OK));
		btnReq = new ColorButton((Action)colorActionTbl.get(PREF_COLOR_REQUIRED));
    }

    private void createButtonPanel(){
    	btnPnl = new JPanel(new BorderLayout(5, 5));

    	bSave = new JButton(new SaveAction());
//  	this is needed or the mnemonic doesnt activate
    	bSave.setMnemonic((char)((Integer)bSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	bSave.addKeyListener(prefMgr.getKeyListener());
    	    	
    	bReset = new JButton(new ResetAction());
    	bReset.setMnemonic((char)((Integer)bReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	bReset.addKeyListener(prefMgr.getKeyListener());
    	btnPnl.add(bReset,BorderLayout.EAST);
    	btnPnl.add(bSave,BorderLayout.WEST);	
    }
    
    private void init() {
        boolean bOver = prefMgr.getPrefNode().getBoolean(HOME+PREF_COLOR_OVERRIDE, DEFAULT_COLOR_OVERRIDE);
        lblFore.setLabelFor(btnFore);
        lblFore.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_FOREGROUND));
        
        lblBack.setLabelFor(btnBack);
        lblBack.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_BACKGROUND));
        
        lblWuBg.setLabelFor(btnWuBg);
        lblWuBg.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_WU_BG));
        
        lblFound.setLabelFor(btnFound);
        lblFound.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_FOUND));
        
        lblFoundFocus.setLabelFor(btnFoundFocus);
        lblFoundFocus.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_FOUND_FOCUS));
        
        lblAssoc.setLabelFor(btnAssoc);
        lblAssoc.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_ASSOC));
        
        lblChild.setLabelFor(btnChild);
        lblChild.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_CHILD));
        
        lblParent.setLabelFor(btnParent);
        lblParent.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_PARENT));
        
        lblLock.setLabelFor(btnLock);
        lblLock.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_LOCK));
        
        lblLow.setLabelFor(btnLow);
        lblLow.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_LOW));
        
        lblLowReq.setLabelFor(btnLowReq);
        lblLowReq.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_LOW_REQUIRED));
        
        lblCOK.setLabelFor(btnCOK);
        lblCOK.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_OK));
        
        lblReq.setLabelFor(btnReq);
        lblReq.setDisplayedMnemonic(Utils.getMnemonic(PREF_COLOR_REQUIRED));

        chkOverride.addActionListener(this);
        chkOverride.setOpaque(false);
        chkOverride.setMnemonic(Utils.getMnemonic("color.override"));
        chkOverride.setSelected(bOver); 
        
    	createButtonPanel();
    	
        setOverrideEnabled(bOver); 

        appPnl_0.add(lblFore);
        appPnl_0.add(btnFore);
        appPnl_0.add(lblBack);
        appPnl_0.add(btnBack);
        appPnl_0.add(lblWuBg);
        appPnl_0.add(btnWuBg);

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

        appPnl.add(chkOverride,BorderLayout.NORTH); 
        appPnl.add(appPnl_0,BorderLayout.SOUTH);

        appPnl_North.add(appPnl,BorderLayout.NORTH); 
        appPnl_North.add(appPnl_1,BorderLayout.SOUTH);
        appPnl_South.add(appPnl_2,BorderLayout.NORTH);
        appPnl_South.add(appPnl_3,BorderLayout.SOUTH);
        add(appPnl_North,BorderLayout.NORTH);
        add(appPnl_South,BorderLayout.SOUTH);
    }

    private void setOverrideEnabled(boolean _bOver) { 
        btnFore.setEnabled(_bOver); 
        btnBack.setEnabled(_bOver); 
        btnWuBg.setEnabled(_bOver); 
 
        btnFound.setEnabled(_bOver); 
        btnFoundFocus.setEnabled(_bOver); 

        btnAssoc.setEnabled(_bOver); 
        btnChild.setEnabled(_bOver); 
        btnParent.setEnabled(_bOver); 

        btnLock.setEnabled(_bOver); 
        btnLow.setEnabled(_bOver); 
        btnLowReq.setEnabled(_bOver); 
        btnCOK.setEnabled(_bOver); 
        btnReq.setEnabled(_bOver); 
   
        bReset.setEnabled(_bOver); 
    } 

    private void saveAppearance() {
    	if (chkOverride.isSelected()){
    		for (Enumeration<ColorAction> e = colorActionTbl.elements(); e.hasMoreElements();){
    			ColorAction action = (ColorAction)e.nextElement(); 
    			action.saveColorPreference();
    		}
    	}

    	prefMgr.getPrefNode().putBoolean(HOME+PREF_COLOR_OVERRIDE, chkOverride.isSelected()); 
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			updateUIs();
    		}
    	});
    }
    private void updateUIs(){
    	try {
			EACM.updateUIWithColorPref();
		} catch (UnsupportedLookAndFeelException e) {
			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,e.getMessage(),e);
		}
    	SwingUtilities.updateComponentTreeUI(prefMgr.getTopLevelAncestor());
    	// buttons are missed SwingUtilities.updateComponentTreeUI(colorChoose);
        prefMgr.updateChooserUIs(); //must update other panels too
        
        com.ibm.eacm.ui.FoundLineBorder.FOUND_FOCUS_BORDER.refreshAppearance();
        com.ibm.eacm.ui.FoundLineBorder.FOUND_BORDER.refreshAppearance();
    }

    /*********
     * restore system colors
     */
    private void resetAppearance() {
    	for (Enumeration<?> e = colorActionTbl.elements(); e.hasMoreElements();){
    		ColorAction action = (ColorAction)e.nextElement(); 
    		action.resetColorPreference();
		}
    	
        chkOverride.setSelected(DEFAULT_COLOR_OVERRIDE); 
        setOverrideEnabled(DEFAULT_COLOR_OVERRIDE); 
 
        prefMgr.getPrefNode().putBoolean(HOME+PREF_COLOR_OVERRIDE, DEFAULT_COLOR_OVERRIDE);
       
        bSave.setEnabled(false);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
        	public void run() {
        		updateUIs();
        	}
        });
    }
    
    public JPanel getButtonPanel() {
    	return btnPnl;
    }
    /**
     * release memory
     * 
     */
    public void dereference() {
    	if (colorChoose!=null){
    		colorChoose.dereference();
    		colorChoose = null;
    	}
        chkOverride.removeActionListener(this); 
        chkOverride.removeAll();
        chkOverride.setUI(null);
        chkOverride = null;
         
    	for (Enumeration<?> e = colorActionTbl.elements(); e.hasMoreElements();){
    		ColorAction action = (ColorAction)e.nextElement(); 
    		action.dereference();
		}
        colorActionTbl.clear();
        colorActionTbl = null;
           
        appPnl.removeAll();// does removeNotify on all children
        appPnl.setUI(null);
        appPnl = null;
    	
    	appPnl_0.removeAll();
    	appPnl_0.setUI(null);
    	appPnl_0 = null;
    	appPnl_1.removeAll();
    	appPnl_1.setUI(null);
    	appPnl_1 = null;
    	appPnl_2.removeAll();
    	appPnl_2.setUI(null);
    	appPnl_2 = null;
    	appPnl_3.removeAll();
    	appPnl_3.setUI(null);
    	appPnl_3 = null;
    	
    	appPnl_South.removeAll();
    	appPnl_South.setUI(null);
    	appPnl_South = null;
    	appPnl_North.removeAll();
    	appPnl_North.setUI(null);
    	appPnl_North = null;

    	lblFore.setLabelFor(null);
    	lblBack.setLabelFor(null);
    	lblWuBg.setLabelFor(null);
      	lblFore.setUI(null);
    	lblBack.setUI(null);
    	lblWuBg.setUI(null);
    	lblFore = null;
    	lblBack = null;
    	lblWuBg = null;
    	
    	lblFound.setLabelFor(null);
    	lblFoundFocus.setLabelFor(null);
    	lblAssoc.setLabelFor(null);
    	lblChild.setLabelFor(null);
    	lblParent.setLabelFor(null);
    	lblLock.setLabelFor(null);
    	lblLow.setLabelFor(null);
    	lblLowReq.setLabelFor(null);
    	lblCOK.setLabelFor(null);
    	lblReq.setLabelFor(null);
    	
     	lblFound.setUI(null);
    	lblFoundFocus.setUI(null);
    	lblAssoc.setUI(null);
    	lblChild.setUI(null);
    	lblParent.setUI(null);
    	lblLock.setUI(null);
    	lblLow.setUI(null);
    	lblLowReq.setUI(null);
    	lblCOK.setUI(null);
    	lblReq.setUI(null);
    	
     	lblFound=null;
    	lblFoundFocus=null;
    	lblAssoc=null;
    	lblChild=null;
    	lblParent=null;
    	lblLock=null;
    	lblLow=null;
    	lblLowReq=null;
    	lblCOK=null;
    	lblReq=null;

    	// release color buttons
        btnFore.dereference(); 
        btnBack.dereference(); 
        btnWuBg.dereference();

        btnFound.dereference(); 
        btnFoundFocus.dereference(); 

        btnAssoc.dereference(); 
        btnChild.dereference(); 
        btnParent.dereference(); 

        btnLock.dereference(); 
        btnLow.dereference(); 
        btnLowReq.dereference(); 
        btnCOK.dereference(); 
        btnReq.dereference(); 
        
        btnFore=null; 
        btnBack=null; 
        btnWuBg=null;

        btnFound=null; 
        btnFoundFocus=null; 

        btnAssoc=null; 
        btnChild=null; 
        btnParent=null; 

        btnLock=null; 
        btnLow=null; 
        btnLowReq=null; 
        btnCOK=null; 
        btnReq=null; 
                    	
    	// deref button panel
    	btnPnl.removeAll();
    	btnPnl.setUI(null);
    	btnPnl = null;
    	SaveAction sa  = (SaveAction)bSave.getAction();
    	sa.dereference();
    	bSave.removeKeyListener(prefMgr.getKeyListener());
    	bSave.setAction(null);
    	bSave.setUI(null);
    	ResetAction ra  = (ResetAction)bReset.getAction();
    	ra.dereference();
    	bReset.removeKeyListener(prefMgr.getKeyListener());
    	bReset.setAction(null);
    	bReset.setUI(null);
    	
    	bSave = null;
    	bReset = null;
    	prefMgr = null;
    	
    	removeAll();
    	setUI(null);
    	
    	bord0 = null;
    	bord1 = null;
    	bord2 = null;
    	bord3 = null;
    	prefMgr = null;
    }

	public void actionPerformed(ActionEvent e) { 
        setOverrideEnabled(chkOverride.isSelected()); 
        bSave.getAction().actionPerformed(null);
        if(!chkOverride.isSelected()){
        	com.ibm.eacm.EACM.EACM_THEME.resetDefaults();
        }
	}

	public static Color getRequiredColor() {
		return getColorForRGB(PREF_COLOR_REQUIRED,DEFAULT_COLOR_REQUIRED);
	}
	public static Color getColorForRGB(String prefkey, Color defColor){
		int rgb = Preferences.userNodeForPackage(PrefMgr.class).getInt(HOME+prefkey,defColor.getRGB());
		if(rgb==defColor.getRGB()){
			return defColor;
		}
		return new ColorUIResource(rgb);
	}
	public static Color getOkColor() {
		return getColorForRGB(PREF_COLOR_OK,DEFAULT_COLOR_OK);
	}
	public static Color getBackgroundColor() {
		return getColorForRGB(PREF_COLOR_BACKGROUND, DEFAULT_COLOR_ENABLED_BACKGROUND);
	}
	public static Color getWUBackgroundColor() {
		return getColorForRGB(PREF_COLOR_WU_BG, DEFAULT_COLOR_WU_BG);
	}
	public static Color getForegroundColor() {
		return getColorForRGB(PREF_COLOR_FOREGROUND, DEFAULT_COLOR_ENABLED_FOREGROUND);
	}

	public static boolean canOverrideColor() {
		return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(HOME+PREF_COLOR_OVERRIDE, DEFAULT_COLOR_OVERRIDE);
	}
	public static Color getAssocColor(){
		return getColorForRGB(PREF_COLOR_ASSOC,DEFAULT_COLOR_ASSOC);
	}
	public static Color getChildColor(){
		return getColorForRGB(PREF_COLOR_CHILD,DEFAULT_COLOR_CHILD);
	}
	public static Color getFoundColor(){
		return getColorForRGB(PREF_COLOR_FOUND,DEFAULT_COLOR_FOUND);
	}
	public static Color getLockColor(){
		return getColorForRGB(PREF_COLOR_LOCK,DEFAULT_COLOR_LOCK);
	}
	public static Color getParentColor(){
		return getColorForRGB(PREF_COLOR_PARENT, DEFAULT_COLOR_PARENT);
	}
	public static Color getLowColor(){
		return getColorForRGB(PREF_COLOR_LOW, DEFAULT_COLOR_LOW);
	}
	public static Color getLowRequiredColor(){
		return getColorForRGB(PREF_COLOR_LOW_REQUIRED, DEFAULT_COLOR_LOW_REQUIRED);
	}
	
	private Color getColor(Color _c) {
		if (colorChoose == null) {
			colorChoose = new ColorChooser(this);
		}
		return colorChoose.getColor(_c);
	}
	
	/**
	 * the change in background color requires a theme to do it properly
	 * the LAF must be switched to Metal to support theme
	 */
	private class ThemeAction extends ColorAction {
		private static final long serialVersionUID = 1L;
	
		ThemeAction(String s, Color c) {
			super(s,c);
	    }
	 
	    void saveColorPreference(){
	    	super.saveColorPreference();
	    	com.ibm.eacm.EACM.EACM_THEME.updateColors();	    	
	    }
	    void resetColorPreference(){
	    	super.resetColorPreference();
	    	com.ibm.eacm.EACM.EACM_THEME.resetDefaults();
	    }

	}

	private class ColorAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "chooseColor";
	
		ColorAction(String _s, Color _c) {
			super(CMD);
	        //putValue(Action.SHORT_DESCRIPTION, Utils.getToolTip(CMD));
	        putValue("DEFCOLOR",_c);
	        putValue("PREFKEY",_s);
	        putValue("CURCOLOR",getColorForRGB(_s, _c));
	    }
		public void dereference(){
			super.dereference();
			//putValue(Action.SHORT_DESCRIPTION, null);
			putValue("DEFCOLOR",null);
			putValue("PREFKEY",null);
			putValue("CURCOLOR",null);
		}
		private Color getSelectedColor() {
	    	// might not be the pref color until save is pressed
	        return (Color)getValue("CURCOLOR");
	    }
		private Color getDefaultColor() {
	        return (Color)getValue("DEFCOLOR");
	    }
	    private Color getPrefColor(){
	    	return getColorForRGB((String)getValue("PREFKEY"), getDefaultColor());
	    }
	    void updateFromPrefs(){
	    	Color prefcolor = getPrefColor();
	    	putValue("CURCOLOR",prefcolor);
	    	UIManager.put((String)getValue("PREFKEY"), prefcolor);
	    }
	    void saveColorPreference(){
	    	Color color  = getSelectedColor();
	    	// must be ColorUIResource or UIManager cant use them correctly
	    	if (!(color instanceof ColorUIResource)){
	    		color = new ColorUIResource(color);
	    	}
	    	prefMgr.getPrefNode().putInt(HOME+(String)getValue("PREFKEY"), color.getRGB());
	    	UIManager.put((String)getValue("PREFKEY"), color);
	    }
	    void resetColorPreference(){
	    	prefMgr.getPrefNode().putInt(HOME+(String)getValue("PREFKEY"), getDefaultColor().getRGB());
	    	putValue("CURCOLOR",getDefaultColor());
	    }
		public void actionPerformed(ActionEvent e) {
			Color color = getColor(getSelectedColor());
			if (color!=null){
				putValue("CURCOLOR",color);
				bSave.getAction().actionPerformed(null);
			}
			ColorPref.this.requestFocusInWindow();//move focus away from button so ESC can close dialog
		} 	
	}
	private class ResetAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref";
		ResetAction() {
	        super(CMD,"Color");
	    }

		public void actionPerformed(ActionEvent e) {
			resetAppearance();
		} 	
	}
	private class SaveAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "save.pref";
		SaveAction() {
	        super(CMD,"Color");
	        setEnabled(checkForChanges());
	    }
		private boolean checkForChanges(){
			boolean enable = (canOverrideColor()!=chkOverride.isSelected());
			
			if (!enable){
				// check colors
				for (Enumeration<?> e = colorActionTbl.elements(); e.hasMoreElements();){
					ColorAction action = (ColorAction)e.nextElement(); 
					if(!action.getPrefColor().equals(action.getSelectedColor())){
						enable = true;
						break;
					}
				}		
			}
	
			return enable;
		}

		public void actionPerformed(ActionEvent e) {
			if(e ==null){ // override or color changed
				bSave.setEnabled(checkForChanges());
			}else{ 
				saveAppearance();
				bSave.setEnabled(false);
			}
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
		boolean chgs = false;
		if (bSave!=null){
			chgs = bSave.isEnabled();
		}
		return chgs;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.preference.Preferencable#updateFromPrefs()
	 */
	public void updateFromPrefs() {
    	for (Enumeration<?> e = colorActionTbl.elements(); e.hasMoreElements();){
    		ColorAction action = (ColorAction)e.nextElement(); 
    		action.updateFromPrefs();
		}
    	
    	boolean override = prefMgr.getPrefNode().getBoolean(HOME+PREF_COLOR_OVERRIDE, DEFAULT_COLOR_OVERRIDE);
        chkOverride.setSelected(override); 
        setOverrideEnabled(override); 
 
        if(!override){
        	com.ibm.eacm.EACM.EACM_THEME.resetDefaults();
        }else{
        	com.ibm.eacm.EACM.EACM_THEME.updateColors();
        }

        bSave.setEnabled(false);
     
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
        	public void run() {
        		updateUIs();
        	}
        });
	} 
}
