//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;

import com.ibm.eacm.objects.Utils;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.event.*;

/**
 * Manages all preferences
 * @author Wendy Stimpson
 */
// $Log: PrefMgr.java,v $
// Revision 1.2  2013/08/16 16:24:51  wendy
// comment out java lnf
//
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class PrefMgr extends JPanel implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
    private static final String PREFERENCE_FONT = "font.pref";
    private static final String PREFERENCE_NAV_LAYOUT = "navlayout.pref";
    private static final String PREFERENCE_LNF = "lnf.pref";
    private static final String PREFERENCE_TOOLBAR = "tbar.pref";
    private static final String PREFERENCE_MIDDLEWARE_LOCATION = "mwLoc.pref";
    private static final String PREFERENCE_PROFILE = "prof.pref";
    private static final String PREFERENCE_PRINT = "print.pref";
    private static final String PREFERENCE_LOGGING = "logging.pref";
    private static final String PREFERENCE_APPEARANCE = "appearance.pref";
    private static final String PREFERENCE_COLUMN_ORDER = "colOrd.pref";
    private static final String PREFERENCE_BEHAVIOR = "bhvur.pref";

    private static final Hashtable<String,Level> LEVEL_TBL;
    protected static final Logger logger;
    static {
        LEVEL_TBL = new Hashtable<String,Level>();
        LEVEL_TBL.put(Level.ALL.getName(), Level.ALL);
        LEVEL_TBL.put(Level.FINEST.getName(), Level.FINEST);
        LEVEL_TBL.put(Level.FINER.getName(), Level.FINER);
        LEVEL_TBL.put(Level.FINE.getName(), Level.FINE);
        LEVEL_TBL.put(Level.CONFIG.getName(), Level.CONFIG);
        LEVEL_TBL.put(Level.INFO.getName(), Level.INFO);
        LEVEL_TBL.put(Level.WARNING.getName(), Level.WARNING);
        LEVEL_TBL.put(Level.SEVERE.getName(), Level.SEVERE);
        LEVEL_TBL.put(Level.OFF.getName(), Level.OFF);

        String loggername = com.ibm.eacm.preference.PrefMgr.class.getPackage().getName();
        logger = Logger.getLogger(loggername); 
        logger.setLevel(PrefMgr.getLoggerLevel(loggername,Level.INFO));
    }
    
    private JPanel pCentral = new JPanel(new BorderLayout());

    private JList list = new JList();
    private JScrollPane lScroll = new JScrollPane(list);
    private Vector<PrefItem> vList = new Vector<PrefItem>();

    private JButton bClose = null; 
    private Preferences prefNode;
    private KeyListener keyListener;
    
    /**
    * get the preference node for updates
    * @return
    */
    protected Preferences getPrefNode(){
        return prefNode;
    }
      
    protected KeyListener getKeyListener(){
    	return keyListener;
    }
    /**
     * prefPanel
     */
    protected PrefMgr(Action closeAction) {
        super(new BorderLayout());
        keyListener = new KeyAdapter(){
        	public void keyPressed(KeyEvent ke){
        		if(ke.getKeyCode() == KeyEvent.VK_ENTER){
        			if(ke.getSource() instanceof JButton){
        				JButton jb = (JButton)ke.getSource();
        				jb.doClick();
        			}
        			ke.consume();
        		}
        	}
        };
        bClose = new JButton(closeAction); 
//  	this is needed or the mnemonic doesnt activate
        bClose.setMnemonic((char)((Integer)bClose.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
        bClose.addKeyListener(keyListener);
        lScroll.setFocusable(false);
        add(lScroll,BorderLayout.WEST);
        setFocusable(false);
        init();
    }

    /**
     * init
     */
    private void init() {
    	// get preference node
        prefNode = Preferences.userNodeForPackage(PrefMgr.class);
        
    	loadPrefList();
    	
        list.addListSelectionListener(this);
        add(pCentral,BorderLayout.CENTER);
        add(bClose,BorderLayout.SOUTH);
 
        list.setSelectedIndex(0); // creates event
    }

    private void loadPrefList() {     
        vList.add(new ProfPrefItem());//PREFERENCE_PROFILE
        vList.add(new NavPrefItem());//PREFERENCE_NAV_LAYOUT
        vList.add(new ToolbarPrefItem());//PREFERENCE_TOOLBAR
        vList.add(new MWPrefItem());//PREFERENCE_MIDDLEWARE_LOCATION
        vList.add(new PrintPrefItem());//PREFERENCE_PRINT
        vList.add(new FontPrefItem());//PREFERENCE_FONT
        vList.add(new ColorPrefItem());//PREFERENCE_APPEARANCE
        vList.add(new OrderPrefItem());//PREFERENCE_COLUMN_ORDER
        vList.add(new BehaviorPrefItem());//PREFERENCE_BEHAVIOR
        vList.add(new LoggingPrefItem());//PREFERENCE_LOGGING
  //remove for now, prevent user confusion      vList.add(new LnfPrefItem());//PREFERENCE_LNF
           
        list.setListData(vList);
    }
    
    /**
     * 
     */
    protected void updateFromPrefs(){
    	// get the updated node
    	prefNode = Preferences.userNodeForPackage(PrefMgr.class);
    	for (int i=0; i<vList.size(); i++){
    		PrefItem pi = (PrefItem)vList.elementAt(i);
    		pi.updateFromPrefs();
    	}
    }
    
    /**
     * get the Level object for the logger
     * @param loggerName
     * @param defaultLevel
     * @return
     */
    public static Level getLoggerLevel(String loggerName,Level defaultLevel){
    	Preferences prefNode = Preferences.userNodeForPackage(PrefMgr.class);
    	String preflvl = prefNode.get(loggerName, defaultLevel.getName());
    	return getLevel(preflvl);
    }
    
    /**
     *  get the log level for this string
     * @param lvl 
     * @return
     */
    public static Level getLevel(String lvl){
        Level loglvl = LEVEL_TBL.get(lvl);
        if(loglvl==null){
            loglvl = Level.ALL;
        }
 
        return loglvl;
    }
 

    /********
     * Font or color was changed, update any chooser panels already created
     *
     */
    protected void updateChooserUIs(){  
    	for (int i=0; i<vList.size(); i++){
    		PrefItem pi = (PrefItem)vList.elementAt(i);
    		pi.updateUI();
    	}
    }

    /**
     * dialog is closing, cancel any workers
     */
    protected void isClosing(){
    	for (int i=0; i<vList.size(); i++){
    		PrefItem pi = (PrefItem)vList.elementAt(i);
    		pi.isClosing();
    	}
    }
    /**
     * check if dialog should close
     *
     */
    protected Vector<String> hasChanges(){
    	Vector<String> chgdVct = new Vector<String>(1);
    	for (int i=0; i<vList.size(); i++){
    		PrefItem pi = (PrefItem)vList.elementAt(i);
    		if (pi.hasChanges()){
    			chgdVct.add(pi.toString());
    		}
    	}
    	return chgdVct;
    }
    /**
     * release memory
     */
    protected void dereference() {
    	try {
    		// save any preferences
			prefNode.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		
    	prefNode = null;
    	for (int i=0; i<vList.size(); i++){
    		PrefItem pi = (PrefItem)vList.elementAt(i);
    		pi.dereference();
    	}
  
    	vList.clear();
    	
        list.removeListSelectionListener(this);
        list.removeAll();
        list.setUI(null);

        lScroll.removeAll();
        lScroll.setUI(null);

        removeAll();
        setUI(null);
        pCentral.removeAll();
        pCentral.setUI(null);
        pCentral=null;

        list = null;
        lScroll = null;
        vList = null;

        bClose.removeKeyListener(keyListener);
        bClose.setAction(null);
        bClose.setUI(null);
        bClose.removeAll();
        bClose = null; 
        
        keyListener = null;
    }

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent lse) {
    	if (!lse.getValueIsAdjusting()) {
    		Object o = list.getSelectedValue();
    		if (o instanceof PrefItem) {
    			PrefItem pfi = (PrefItem) o;    			

    			pCentral.removeAll();
    			pCentral.add(pfi.getPrefPanel(),BorderLayout.NORTH);
    			pCentral.add(pfi.getButtonPanel(),BorderLayout.SOUTH);

    			if (getTopLevelAncestor()!=null){// this is needed because init() calls this before added to top
    				((Window)this.getTopLevelAncestor()).pack();
    			}

    			list.ensureIndexIsVisible(list.getSelectedIndex());
    			repaint();
    		}
    	}
    }

    private abstract class PrefItem  {
    	private String value = null;
    	protected Preferencable prefChooser;
        PrefItem(String key) {
            value = " "+Utils.getResource(key)+" ";
        }
        abstract JPanel getPrefPanel();
        JPanel getButtonPanel(){
         	return ((Preferencable)getPrefPanel()).getButtonPanel();
        }
        void updateFromPrefs(){
        	getPrefPanel(); // make sure prefChooser is populated
        	prefChooser.updateFromPrefs();
        }
        void dereference(){
        	value = null;
        	if (prefChooser!=null){
        		prefChooser.dereference();
        		prefChooser = null;
        	}
        }
        void isClosing(){
        	if (prefChooser!=null){
        		prefChooser.isClosing();
        	}
        }
    	boolean hasChanges() { 
    		if (prefChooser!=null){
        		return prefChooser.hasChanges();
        	}
    		return false;
    	} 
        void updateUI(){
        	if (prefChooser != null){
        		if(((Component)prefChooser).getParent()!=pCentral){ // if pCentral was parent this isnt needed
        			SwingUtilities.updateComponentTreeUI((Component)prefChooser);
        			SwingUtilities.updateComponentTreeUI(prefChooser.getButtonPanel());
        		}
        	}
        }

        public String toString() {
            return value;
        }
    }

    /**
     * PREFERENCE_MIDDLEWARE_LOCATION
     */
    private class MWPrefItem extends PrefItem {
    	MWPrefItem() {
            super(PREFERENCE_MIDDLEWARE_LOCATION);
        }
        JPanel getPrefPanel(){
        	if (prefChooser==null){
        		prefChooser = new MWChooser();
        	}
        	return (JPanel)prefChooser;
        }
        MWChooser getMWChooser() { return (MWChooser)prefChooser;}
    }
    /**
     * PREFERENCE_PROFILE
     */
    private class ProfPrefItem extends PrefItem {
    	ProfPrefItem() {
            super(PREFERENCE_PROFILE);
        }
        JPanel getPrefPanel(){
        	if (prefChooser==null){
        		prefChooser = new ProfilePref(PrefMgr.this);
        	}
        	return (JPanel)prefChooser;
        }
    }
    /**
     * PREFERENCE_NAV_LAYOUT
     */
    private class NavPrefItem extends PrefItem {
    	NavPrefItem() {
            super(PREFERENCE_NAV_LAYOUT);
        }
        JPanel getPrefPanel(){
        	if (prefChooser==null){
        		prefChooser = new NavLayoutPref(PrefMgr.this);
        	}
        	return (JPanel)prefChooser;
        }
    }

    /**
     * PREFERENCE_LNF
     */
    private class LnfPrefItem extends PrefItem {
    	LnfPrefItem() {
            super(PREFERENCE_LNF);
        }
        JPanel getPrefPanel(){
        	if (prefChooser==null){
        		prefChooser = new LnfPref(PrefMgr.this);
        	}
        	return (JPanel)prefChooser;
        }
    }
    
    /**
     * PREFERENCE_TOOLBAR
     */
    private class ToolbarPrefItem extends PrefItem {
    	ToolbarPrefItem() {
            super(PREFERENCE_TOOLBAR);
        }
        JPanel getPrefPanel(){
        	if (prefChooser==null){
        		prefChooser = new ToolbarPref(PrefMgr.this);
        	}
        	return (JPanel)prefChooser;
        }
    }
    /**
     * PREFERENCE_BEHAVIOR
     */
    private class BehaviorPrefItem extends PrefItem {
    	BehaviorPrefItem() {
            super(PREFERENCE_BEHAVIOR);
        }
        JPanel getPrefPanel(){
           	if (prefChooser==null){
        		prefChooser = new BehaviorPref(PrefMgr.this);
        	}
        	return (JPanel)prefChooser;
        }
    }
    /**
     * PREFERENCE_PRINT
     */
    private class PrintPrefItem extends PrefItem {
    	PrintPrefItem() {
            super(PREFERENCE_PRINT);
        }
        JPanel getPrefPanel(){
           	if (prefChooser==null){
        		prefChooser = new PrintPref(PrefMgr.this);
        	}
        	return (JPanel)prefChooser;
        }
    }
    /**
     * PREFERENCE_LOGGING
     */
    private class LoggingPrefItem extends PrefItem {
    	LoggingPrefItem() {
            super(PREFERENCE_LOGGING);
        }
        JPanel getPrefPanel(){
           	if (prefChooser==null){
        		prefChooser = new LoggingPrefMgr(PrefMgr.this);
        	}
        	return (JPanel)prefChooser;
        }
    }
    /**
     * PREFERENCE_FONT
     */
    private class FontPrefItem extends PrefItem {
    	FontPrefItem() {
            super(PREFERENCE_FONT);
        }
        JPanel getPrefPanel(){
           	if (prefChooser==null){
        		prefChooser = new FontPref(PrefMgr.this);
        	}
        	return (JPanel)prefChooser;
        }
    }
    /**
     * PREFERENCE_APPEARANCE
     */
    private class ColorPrefItem extends PrefItem {
    	ColorPrefItem() {
            super(PREFERENCE_APPEARANCE);
        }
        JPanel getPrefPanel(){
           	if (prefChooser==null){
        		prefChooser = new ColorPref(PrefMgr.this);
        	}
        	return (JPanel)prefChooser;
        }
    }
    /**
     * PREFERENCE_COLUMN_ORDER
     */
    private class OrderPrefItem extends PrefItem {
    	OrderPrefItem() {
            super(PREFERENCE_COLUMN_ORDER);
        }
        JPanel getPrefPanel(){
           	if (prefChooser==null){
        		prefChooser = new OrderPref();
        	}
        	return (JPanel)prefChooser;
        }
        void updateUI(){
        	super.updateUI();
        	if (prefChooser != null){
        		((OrderPref)prefChooser).refreshAppearance();
        	}
        }
    }

}
