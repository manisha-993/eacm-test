//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.preference;


import java.awt.*;

import javax.swing.*;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.Utils;

import java.awt.event.*;
import java.util.logging.Level;
import java.util.prefs.Preferences;

/**
 * This class manages preferences for navigation layout
 * @author Wendy Stimpson
 */
// $Log: NavLayoutPref.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class NavLayoutPref extends JPanel implements Preferencable
{
	private static final long serialVersionUID = 1L;
	private static final String PREF_NAV_LAYOUT = "nav.look.feel";
	
    private JComboBox nvlCombo = new JComboBox();
    private JLabel lblNvl = new JLabel(Utils.getResource("navlayout.name"));		

    private JButton bSave = null;
    private JButton bReset = null;
    private JPanel btnPnl =null;
    private PrefMgr prefMgr;

    /**
     * look and feel Chooser
     */
    NavLayoutPref(PrefMgr pm) {
    	prefMgr = pm;
        init();
        setBorder(BorderFactory.createTitledBorder(Utils.getResource("navlayout.border")));// meet accessiblity 
        createButtonPanel();
    }

    /**
     * init
     */
    private void init() {
    	nvlCombo = new JComboBox(loadNavLayouts());
    	nvlCombo.setActionCommand("nvlCombo");
    	nvlCombo.setToolTipText(Utils.getToolTip("navlayout.name"));

    	lblNvl.setLabelFor(nvlCombo);
    	lblNvl.setDisplayedMnemonic(Utils.getMnemonic("navlayout.name"));
    	
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
        leftToRight.addComponent(lblNvl);
        leftToRight.addComponent(nvlCombo);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        GroupLayout.ParallelGroup row = layout.createParallelGroup();
        row.addComponent(lblNvl);
        row.addComponent(nvlCombo);
        topToBottom.addGroup(row);

        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);
    }
    private void createButtonPanel(){
    	btnPnl = new JPanel(new BorderLayout(5, 5));

    	bSave = new JButton(new SaveAction());
//  	this is needed or the mnemonic doesnt activate
    	bSave.setMnemonic((char)((Integer)bSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	nvlCombo.addActionListener(bSave.getAction());
    	bSave.addKeyListener(prefMgr.getKeyListener());
    	
    	bReset = new JButton(new ResetAction());
    	bReset.setMnemonic((char)((Integer)bReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	bReset.addKeyListener(prefMgr.getKeyListener());
    	
    	btnPnl.add(bReset,BorderLayout.EAST);
    	btnPnl.add(bSave,BorderLayout.WEST);							
    }
    
    public JPanel getButtonPanel() {
    	return btnPnl;
    }
    /**
     * dereference 
     * 
     */
    public void dereference() {
    	nvlCombo.removeAllItems();
    	nvlCombo.removeActionListener(bSave.getAction());
    	nvlCombo.setUI(null);
    	nvlCombo = null;

    	lblNvl.setLabelFor(null);
    	lblNvl.removeAll();
    	lblNvl.setUI(null);
    	
    	// deref button panel
    	btnPnl.removeAll();
    	btnPnl.setUI(null);
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
    	
    	removeAll();
    	setUI(null);		

        bSave = null;
        bReset = null;
        btnPnl =null;
        prefMgr = null;
    }
    
    public static boolean isDualNavLayout() {return getNavLayout().equals("nav_dual.xml");}

    public static String getNavLayout(){
    	return Preferences.userNodeForPackage(PrefMgr.class).get(PREF_NAV_LAYOUT, EACMProperties.getDefaultForm());
    }
    private DefaultComboBoxModel loadNavLayouts() {
    	DefaultComboBoxModel dcm = new DefaultComboBoxModel();
        String[] strForm = EACMProperties.getAllForms();

        String defNVL =getNavLayout();
        if (strForm != null) {
            for (int i = 0; i < strForm.length; ++i) {
                String strDesc = Utils.getResource("nav.form.description." + i);
                if (strDesc==null){
                	PrefMgr.logger.log(Level.SEVERE,"Error: no description found for "+
                			"nav.form.description." + i);
                	continue;
                }
                NavLayout nvl = new NavLayout(strDesc,strForm[i]);
                dcm.addElement(nvl);
                if (strForm[i].equals(defNVL)){
                	dcm.setSelectedItem(nvl);
                }
            }
        }
           
        if (defNVL == null) {
        	dcm.setSelectedItem(null); 
        }
        
        return dcm;
    }
    private class NavLayout {
    	String desc;
    	String form;
    	NavLayout(String d, String f){
    		desc = d;
    		form = f;
    	}
    	public String toString() { return desc; }
    }
    
    private class ResetAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref";
		ResetAction() {
            super(CMD,"Navigation layout");
        }

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			String key = prefMgr.getPrefNode().get(PREF_NAV_LAYOUT, "null");
			if (!key.equals("null")){//had something to reset				
				prefMgr.getPrefNode().put(PREF_NAV_LAYOUT, EACMProperties.getDefaultForm());
	
				//msg11017.0 = The Changes you made may not take effect until the next time you log in.
				com.ibm.eacm.ui.UI.showFYI(NavLayoutPref.this,Utils.getResource("msg11017.0"));
			}
			// change selection to the current default
			String defNVL =getNavLayout();
			for (int i = 0; i < nvlCombo.getItemCount(); ++i) {
				NavLayout nvl = (NavLayout)nvlCombo.getItemAt(i);
				if (nvl.form.equals(defNVL)){
					nvlCombo.setSelectedItem(nvl);
					break;
				}
			}
		} 	
    }
    private class SaveAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "save.pref";
		SaveAction() {
			super(CMD,"Navigation layout");
            setEnabled(checkCombo());
        }
	
		private boolean checkCombo(){
			boolean enable = false;
			NavLayout nvl = (NavLayout)nvlCombo.getSelectedItem();
			String key = getNavLayout();
			if(nvl!=null && !nvl.form.equals(key)){
				enable = true;
			}
		
			return enable;
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("nvlCombo")){
				 bSave.setEnabled(checkCombo());
			}else{
				NavLayout nvl = (NavLayout)nvlCombo.getSelectedItem();
				if (nvl != null) {
					prefMgr.getPrefNode().put(PREF_NAV_LAYOUT, nvl.form);
					bSave.setEnabled(false);
					//msg11017.0 = The Changes you made may not take effect until the next time you log in.
					com.ibm.eacm.ui.UI.showFYI(NavLayoutPref.this,Utils.getResource("msg11017.0"));
				}
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
		NavLayout nvl = (NavLayout)nvlCombo.getSelectedItem();
		String key = getNavLayout();
		if(nvl!=null && !nvl.form.equals(key)){//user may have reset and wants defaults
			chgs = true;
		}

		return chgs;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.preference.Preferencable#updateFromPrefs()
	 */
	public void updateFromPrefs() {
		// change selection to the current default
		String defNVL =getNavLayout();
		for (int i = 0; i < nvlCombo.getItemCount(); ++i) {
			NavLayout nvl = (NavLayout)nvlCombo.getItemAt(i);
			if (nvl.form.equals(defNVL)){
				nvlCombo.setSelectedItem(nvl);
				break;
			}
		}
	}
}
