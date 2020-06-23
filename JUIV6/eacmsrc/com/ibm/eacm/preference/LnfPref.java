//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.preference;


import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.EACMGlobals;

import java.awt.event.*;
import java.util.prefs.Preferences;

/**
 * This class manages preferences for Java look and feel
 * @author Wendy Stimpson
 */
// $Log: LnfPref.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class LnfPref extends JPanel implements EACMGlobals,Preferencable
{
	private static final long serialVersionUID = 1L;
	private static final String PREF_LOOK_AND_FEEL = "java.look.feel";
	private static final String DEFAULT_LOOK_AND_FEEL ="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private JComboBox lnfCombo = new JComboBox();
    private JLabel lblLnf = new JLabel(Utils.getResource("lnf.name"));		

    private JButton bSave = null;
    private JButton bReset = null;
    private JPanel btnPnl =null;
    private PrefMgr prefMgr=null;

    /**
     * look and feel Chooser
     */
    LnfPref(PrefMgr pp)  {
    	prefMgr = pp;
        init();
        setBorder(BorderFactory.createTitledBorder(Utils.getResource("lnf.border")));// meet accessiblity 
        createButtonPanel();
    }

    /**
     * init
     */
    private void init() {
    	lnfCombo = new JComboBox(loadLookAndFeels());
    	lnfCombo.setActionCommand("lnfCombo");
    	lnfCombo.setToolTipText(Utils.getToolTip("lnf.name"));

    	lblLnf.setLabelFor(lnfCombo);
    	lblLnf.setDisplayedMnemonic(Utils.getMnemonic("lnf.name"));
    	
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
        leftToRight.addComponent(lblLnf);
        leftToRight.addComponent(lnfCombo);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        GroupLayout.ParallelGroup row = layout.createParallelGroup();
        row.addComponent(lblLnf);
        row.addComponent(lnfCombo);
        topToBottom.addGroup(row);

        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);
    }
    private void createButtonPanel(){
    	btnPnl = new JPanel(new BorderLayout(5, 5));

    	bSave = new JButton(new SaveAction());
//  	this is needed or the mnemonic doesnt activate
    	bSave.setMnemonic((char)((Integer)bSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
      	bSave.addKeyListener(prefMgr.getKeyListener());
    	lnfCombo.addActionListener(bSave.getAction());
    	
    	bReset = new JButton(new ResetAction());
    	bReset.setMnemonic((char)((Integer)bReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
      	bReset.addKeyListener(prefMgr.getKeyListener());
      	
    	btnPnl.add(bReset,BorderLayout.EAST);
    	btnPnl.add(bSave,BorderLayout.WEST);							
    }
    
    /* (non-Javadoc)
     * @see com.ibm.eacm.preference.Preferencable#getButtonPanel()
     */
    public JPanel getButtonPanel() {
    	return btnPnl;
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.preference.Preferencable#dereference()
     */
    public void dereference() {
    	lnfCombo.removeAllItems();
    	lnfCombo.removeActionListener(bSave.getAction());
    	lnfCombo = null;
    	lblLnf.setLabelFor(null);
    	lblLnf = null;

    	
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

    public static String getLookAndFeel(){
    	return Preferences.userNodeForPackage(PrefMgr.class).get(PREF_LOOK_AND_FEEL, DEFAULT_LOOK_AND_FEEL);
    }
    private DefaultComboBoxModel loadLookAndFeels() {
    	DefaultComboBoxModel dcm = new DefaultComboBoxModel();
    	
    	LookAndFeelInfo[] lfi = UIManager.getInstalledLookAndFeels();
    	
    	String lnfClsName = getLookAndFeel();
    	for (int i=0; i<lfi.length; i++){
    		LnF lnf = new LnF(lfi[i]);
    		 dcm.addElement(lnf);
    		 if (lfi[i].getClassName().equals(lnfClsName)){
    			 dcm.setSelectedItem(lnf);
    		 }
    	}
  
        return dcm;
    }
    private void updateUIs(){

    	try{
    		UIManager.setLookAndFeel(getLookAndFeel());
    		
    		EACM.updateComponentsUI(EACM.getEACM());

    		SwingUtilities.updateComponentTreeUI(prefMgr.getTopLevelAncestor());
    		// buttons are missed SwingUtilities.updateComponentTreeUI(colorChoose);
    		prefMgr.updateChooserUIs(); //must update other panels too
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private class LnF {
    	private LookAndFeelInfo lfi;
    	LnF(LookAndFeelInfo f){
    		lfi = f;
    	}
    	public String toString() { return lfi.getName(); }
    }
    
    private class ResetAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref";
		ResetAction() {
            super(CMD,"Java Look and Feel");
        }

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			prefMgr.getPrefNode().put(PREF_LOOK_AND_FEEL, DEFAULT_LOOK_AND_FEEL);
			bSave.setEnabled(true);
	
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        	public void run() {
	        		updateUIs();
	        	}
	        });
		} 	
    }
    private class SaveAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "save.pref";
		SaveAction() {
		    super(CMD,"Java Look and Feel");
            setEnabled(checkCombo());
        }

		private boolean checkCombo(){
			boolean enable = false;
			LnF lnf = (LnF)lnfCombo.getSelectedItem();
			String key = getLookAndFeel();
			
			if(lnf!=null && !lnf.lfi.getClassName().equals(key)){
				enable = true;
			}
			
			return enable;
		}
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("lnfCombo")){
				 bSave.setEnabled(checkCombo());
			}else{
				LnF lnf = (LnF)lnfCombo.getSelectedItem();
				if (lnf != null) {
					prefMgr.getPrefNode().put(PREF_LOOK_AND_FEEL, lnf.lfi.getClassName());
					bSave.setEnabled(false);

			    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
			    		public void run() {
			    			updateUIs();
			    		}
			    	});
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
		if (bSave!=null){
			chgs = bSave.isEnabled();
		}
		return chgs;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.preference.Preferencable#updateFromPrefs()
	 */
	public void updateFromPrefs() {
		bSave.setEnabled(false);

    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			updateUIs();
    		}
    	});
	} 
}
