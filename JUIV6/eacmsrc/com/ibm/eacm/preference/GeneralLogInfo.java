//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.ui.IntSpinner;
 

/**
 * this class manages application log levels and log aging
 * @author Wendy Stimpson
 */
//$Log: GeneralLogInfo.java,v $
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
class GeneralLogInfo extends JPanel implements ActionListener, ChangeListener,
EACMGlobals {
    private static final long serialVersionUID = 1L;
    //logging.general.def = Set the default logging level for the application.
    private JLabel defLbl = new JLabel(Utils.getResource("logging.general.def"));
    //    logging.general.lvl = Default logging level:
    private JLabel lvlLbl = new JLabel(Utils.getResource("logging.general.lvl"));
    //    logging.general.set = Set the number of days after which aged log files will be deleted.
    private JLabel setLbl = new JLabel(Utils.getResource("logging.general.set"));
    //    logging.general.days = Aged file days:
    private JLabel ageLbl = new JLabel(Utils.getResource("logging.general.days"));
    
    private JComboBox levelCombo = null;        
    private IntSpinner daysSpinner=null;
    private Level curAppLevel;
    private int ageDays;
    private PrefMgr prefMgr;
    private EACMAction saveAction;
    
    /**
     *  constructor
     * @param pm
     * @param save
     */
    GeneralLogInfo(PrefMgr pm, EACMAction save){
        prefMgr = pm;
        saveAction = save; 
        curAppLevel = Logger.getLogger(APP_PKG_NAME).getLevel();
        ageDays = prefMgr.getPrefNode().getInt(MAXAGE_PREF, 
                EACMProperties.getLogAge());
        
        levelCombo = new JComboBox();
        levelCombo.setModel(new DefaultComboBoxModel());
        levelCombo.setEditable(false);
        LoggingPrefMgr.addLogLevels(levelCombo, curAppLevel);
 
        lvlLbl.setLabelFor(levelCombo);
        lvlLbl.setDisplayedMnemonic(Utils.getMnemonic("logging.general.lvl"));
        
        daysSpinner = new IntSpinner(ageDays,0,30,1);
        levelCombo.setBackground(((JSpinner.DefaultEditor)daysSpinner.getEditor()).getTextField().getBackground());
        ((JSpinner.DefaultEditor)daysSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
 
        ageLbl.setLabelFor(daysSpinner);
        ageLbl.setDisplayedMnemonic(Utils.getMnemonic("logging.general.days"));
        
        levelCombo.addActionListener(this);
        daysSpinner.addChangeListener(this);
        
        layoutComponents();
    }
 
    /**
     *  release memory
     */
    void dereference(){
        prefMgr = null;
        curAppLevel = null;
        saveAction = null;
        
        levelCombo.removeActionListener(this);
        daysSpinner.removeChangeListener(this);
        
        levelCombo.removeAllItems();
        levelCombo.setUI(null);
        levelCombo.removeAll();
        levelCombo = null;        
 
        daysSpinner.dereference();
        daysSpinner = null;
        
        defLbl.setUI(null);
        defLbl = null;
        
        setLbl.setUI(null);
        setLbl = null;
        
        ageLbl.setLabelFor(null);
        ageLbl.setUI(null);
        ageLbl = null;
        
        lvlLbl.setLabelFor(null);
        lvlLbl.setUI(null);
        lvlLbl = null;
    }
    
    /**
     *  did user make any changes
     * @return
     */
    boolean hasChanges(){
        return (!curAppLevel.equals(levelCombo.getSelectedItem()) ||
                ageDays!=daysSpinner.getInt());
    }
    
    /**
     *  save the preferences
     */
    void save(){
        Logger.getLogger(APP_PKG_NAME).setLevel((Level)levelCombo.getSelectedItem());
        curAppLevel = (Level)levelCombo.getSelectedItem();
        ageDays=daysSpinner.getInt();
        prefMgr.getPrefNode().put(APP_PKG_NAME, levelCombo.getSelectedItem().toString());
        prefMgr.getPrefNode().putInt(MAXAGE_PREF, daysSpinner.getInt());
    }
    /**
     *  reset to system settings
     */
    void resetDefaults(){
        prefMgr.getPrefNode().put(APP_PKG_NAME, Level.INFO.getName());
        levelCombo.setSelectedItem(Level.INFO);
        Logger.getLogger(APP_PKG_NAME).setLevel(Level.INFO);
        
        prefMgr.getPrefNode().putInt(MAXAGE_PREF, EACMProperties.getLogAge());
        daysSpinner.setValue(new Integer(EACMProperties.getLogAge()));
        
        curAppLevel = (Level)levelCombo.getSelectedItem();
        ageDays=daysSpinner.getInt();
    }
    
    /**
     *  reload with updated preferences
     */
    void updateFromPrefs(){
        ageDays = prefMgr.getPrefNode().getInt(MAXAGE_PREF, EACMProperties.getLogAge());
        daysSpinner.setValue(new Integer(ageDays));
 
        Level lvl = PrefMgr.getLoggerLevel(APP_PKG_NAME, Level.INFO);
        levelCombo.setSelectedItem(lvl);
        Logger.getLogger(APP_PKG_NAME).setLevel(lvl);
    }
    
    /**
     *  get the title for the tab
     * @return
     */
    String getTitle(){
        //logging.general = General
        return Utils.getResource("logging.general");
    }
    /**
     *  lay out the components
     */
    private void layoutComponents(){
           javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(defLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lvlLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(levelCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(daysSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(setLbl)))
                    .addContainerGap(79, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(defLbl)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lvlLbl)
                        .addComponent(levelCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(setLbl)
                    .addGap(4, 4, 4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ageLbl)
                        .addComponent(daysSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(40, Short.MAX_VALUE))
            );
    }
 
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        saveAction.setEnabled(true);// it will check for changes
    }
 
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        saveAction.setEnabled(true);// it will check for changes
    }
}