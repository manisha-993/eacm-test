//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;
 
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.EACMGlobals;
 

/**
 * this class manages logging levels
 * @author Wendy Stimpson
 */
//$Log: LoggingPrefMgr.java,v $
//Revision 1.3  2014/06/20 18:14:07  wendy
//list log levels
//
//Revision 1.2  2014/05/01 19:35:15  wendy
//Add tablayoutpolicy to support scroll of action tabbed pane
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public class LoggingPrefMgr extends JPanel implements Preferencable,
EACMGlobals, ChangeListener
{
    private static final long serialVersionUID = 1L;
    
    private JTabbedPane tabbedPane = null;
    private GeneralLogInfo genInfo;
    private Loggers loggers;
 
    private JButton saveBtn = null;
    private JButton resetBtn = null;
    private JPanel btnPnl =null;
    private PrefMgr prefMgr;
    private EACMAction saveAction, resetAction;
 
    /**
     * list the current logger levels
     */
    public static void listLogLevels(){
        Loggers.listLogLevels();
    }
    
    /**
     * constructor logging level Chooser
     * @param pref
     */
    protected LoggingPrefMgr(PrefMgr pref) {
        super(new BorderLayout());
        
        prefMgr = pref;
    
        saveAction = new SaveAction();
        resetAction = new ResetAction();
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(BehaviorPref.getTabPlacement());
        genInfo = new GeneralLogInfo(pref,saveAction);
        tabbedPane.add(genInfo,0); 
        tabbedPane.setTitleAt(0, genInfo.getTitle());
        
        loggers = new Loggers(pref,saveAction);
        tabbedPane.add(loggers,1); 
        tabbedPane.setTitleAt(1, loggers.getTitle());
        
        add(tabbedPane,BorderLayout.CENTER);
        
        tabbedPane.addChangeListener(this);
        setBorder(BorderFactory.createTitledBorder(Utils.getResource("logging.border")));// meet accessibility 
        createButtonPanel();
    }
    /**
     * create the actions, buttons and panel 
     */
    private void createButtonPanel(){
        btnPnl = new JPanel(new GridLayout(1,3,2,0));
 
        saveBtn = new JButton(saveAction);
//        this is needed or the mnemonic doesnt activate
        saveBtn.setMnemonic((char)((Integer)saveBtn.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
        saveBtn.addKeyListener(prefMgr.getKeyListener());
 
        resetBtn = new JButton(resetAction);
        resetBtn.setMnemonic((char)((Integer)resetBtn.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
        resetBtn.addKeyListener(prefMgr.getKeyListener());
 
        btnPnl.add(saveBtn);    
        btnPnl.add(resetBtn);
    }
 
    /* (non-Javadoc)
     * @see com.ibm.eacm.preference.Preferencable#getButtonPanel()
     */
    public JPanel getButtonPanel() {
        return btnPnl;
    }
 
    /* (non-Javadoc)
     * @see com.ibm.eacm.preference.Preferencable#updateFromPrefs()
     */
    public void updateFromPrefs(){
        // preferences were imported, reset controls
        genInfo.updateFromPrefs();
        loggers.updateFromPrefs();
        saveAction.setEnabled(false);
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.preference.Preferencable#dereference()
     */
    public void dereference() {
        tabbedPane.removeChangeListener(this);
        
        saveAction.dereference();
        saveAction = null;
 
        resetAction.dereference();
        resetAction = null;
        
        tabbedPane.removeAll();
        tabbedPane.setUI(null);
        tabbedPane = null;
        
        genInfo.dereference();
        genInfo = null;
        
        loggers.dereference();
        loggers = null;
 
        // deref button panel
        btnPnl.removeAll();
        btnPnl.setUI(null);
 
        saveBtn.removeKeyListener(prefMgr.getKeyListener());
        saveBtn.setAction(null);
        saveBtn.setUI(null);
 
        resetBtn.removeKeyListener(prefMgr.getKeyListener());
        resetBtn.setAction(null);
        resetBtn.setUI(null);
 
        removeAll(); // does removeNotify on all children
        setUI(null);
        
        prefMgr = null;
    }
 
    /* (non-Javadoc)
     * notify user that changes have not been saved
     * @see com.ibm.eacm.preference.Preferencable#hasChanges()
     */
    public boolean hasChanges() { 
        boolean chgs = false;
        if (genInfo!=null){
            chgs = genInfo.hasChanges() || loggers.hasChanges();
        }
        return chgs;
    } 
    
    /**
     *  add log levels to the specified checkbox and set initial value
     * @param cb
     * @param curLvl
     */
    static void addLogLevels(JComboBox cb,Level curLvl){
        DefaultComboBoxModel comboModel = ((DefaultComboBoxModel)cb.getModel());
        comboModel.removeAllElements();
        comboModel.addElement(Level.ALL);
        comboModel.addElement(Level.FINEST);
        comboModel.addElement(Level.FINER);
        comboModel.addElement(Level.FINE);
        comboModel.addElement(Level.CONFIG);
        comboModel.addElement(Level.INFO);
        comboModel.addElement(Level.WARNING);
        comboModel.addElement(Level.SEVERE);
        comboModel.addElement(Level.OFF);    
 
        cb.setSelectedItem(curLvl);
    }
    
    private class ResetAction extends EACMAction {
        private static final long serialVersionUID = 1L;
        private static final String CMD = "reset.pref";
        ResetAction() {
            super(CMD,Utils.getResource("logging.pref"));
        }
 
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            if(tabbedPane.getSelectedComponent()==genInfo){
                genInfo.resetDefaults();
            }else{
                loggers.resetDefaults();
            }
            saveAction.setEnabled(false);
        }     
    }
    private class SaveAction extends EACMAction {
        private static final long serialVersionUID = 1L;
        private static final String CMD = "save.pref";
        SaveAction() {
            super(CMD,Utils.getResource("logging.pref"));
            setEnabled(false);
        }
 
        /* (non-Javadoc)
         * @see javax.swing.AbstractAction#setEnabled(boolean)
         */
        public void setEnabled(boolean newValue) {
            boolean ok = false;
            if(newValue){
                // enable based on tab selection
                if(tabbedPane.getSelectedComponent()==genInfo){
                    ok = genInfo.hasChanges();
                }else{
                    ok = loggers.hasChanges();
                }
            }
            super.setEnabled(ok);
        }
 
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            if(tabbedPane.getSelectedComponent()==genInfo){
                genInfo.save();
            }else{
                loggers.save();
                listLogLevels();
            }
 
            setEnabled(false);
        }     
    }
    /* (non-Javadoc)
     * called when tab selection changes
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        saveAction.setEnabled(true);
    }
	/* (non-Javadoc)
	 * @see com.ibm.eacm.preference.Preferencable#isClosing()
	 */
	public void isClosing() {
	}

}