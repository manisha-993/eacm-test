//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.preference;


import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.PrintUtilities;
import java.awt.event.*;
import java.awt.print.PageFormat;

/**
 * This class manages preferences for print
 * @author Wendy Stimpson
 */
// $Log: PrintPref.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class PrintPref extends JPanel implements ActionListener,Preferencable
{
	/**
	 * print ratio
	 */
    public static final String PREF_PRINT_RATIO = "print.scale.ratio";
	/**
	 * print scale x
	 */
    public static final String PREF_PRINT_SCALE_X = "print.scale.x";
	/**
	 * print scale y
	 */
    public static final String PREF_PRINT_SCALE_Y = "print.scale.y";
	/**
	 * print orient
	 */
    public static final String PREF_PRINT_ORIENTATION = "print.orientation";
	/**
	 * def orientation
	 */
    public static final int PRINT_DEFAULT_ORIENTATION = PageFormat.PORTRAIT;
	/**
	 * default ratio
	 */
    public static final double PRINT_DEFAULT_RATIO = .5d;
    
	private static final long serialVersionUID = 1L;
	public static final boolean PRINT_DEFAULT_SCALE = false;

    private static final Dimension spinDimension = new Dimension(30, 20);
    private JLabel prntLbl = new JLabel(Utils.getResource("scale%"));
    private JSpinner prntSpin = new JSpinner(new SpinnerNumberModel(.5d, .3d, 5d, .10d));
    private JCheckBox prntChkX = new JCheckBox(Utils.getResource("scaleX2fit"));
    private JCheckBox prntChkY = new JCheckBox(Utils.getResource("scaleY2fit"));

    private ButtonGroup prntGroup = null;
    private JRadioButton prntRdoPortrait = new JRadioButton(Utils.getResource("print.portrait"));
    private JRadioButton prntRdoLandscape = new JRadioButton(Utils.getResource("print.landscape"));  		

    private JButton bSave = null;
    private JButton bReset = null;
    private JPanel btnPnl =null;
    private PrefMgr prefMgr;

    /**
     * print preferences
     */
    PrintPref(PrefMgr pm) {
    	prefMgr = pm;
        buildButtonPanel();
        initPrint();        
        loadPrint();
    }

    private void buildButtonPanel() {
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

    private void initPrint() {
        prntSpin.setSize(spinDimension);
        prntSpin.setPreferredSize(spinDimension);
        prntSpin.setToolTipText(Utils.getToolTip("scale%"));
        prntSpin.addChangeListener((SaveAction)bSave.getAction());
        
		//prevent invalid typed chars and push them into the spinnermodel immediately
		javax.swing.text.DefaultFormatter defForm = (javax.swing.text.DefaultFormatter)((JSpinner.DefaultEditor)prntSpin.getEditor()).getTextField().getFormatter();
		defForm.setAllowsInvalid(false);	
		defForm.setCommitsOnValidEdit(true);	

        prntGroup = new ButtonGroup();
        prntGroup.add(prntRdoPortrait);
        prntGroup.add(prntRdoLandscape);
        prntRdoPortrait.addActionListener(bSave.getAction());
        prntRdoLandscape.addActionListener(bSave.getAction());
        
        prntChkX.addActionListener(this);
        prntChkX.addActionListener(bSave.getAction());
        prntChkX.setActionCommand("printX");
        prntChkY.addActionListener(this);
        prntChkY.addActionListener(bSave.getAction());
        prntChkY.setActionCommand("printY");
        prntRdoPortrait.setMnemonic(Utils.getMnemonic("print.portrait"));
        prntRdoPortrait.setToolTipText(Utils.getToolTip("print.portrait"));
        prntRdoLandscape.setMnemonic(Utils.getMnemonic("print.landscape"));
        prntRdoLandscape.setToolTipText(Utils.getToolTip("print.landscape"));
        prntChkX.setMnemonic(Utils.getMnemonic("scaleX2fit"));
        prntChkX.setToolTipText(Utils.getToolTip("scaleX2fit"));
        prntChkY.setMnemonic(Utils.getMnemonic("scaleY2fit"));
        prntChkY.setToolTipText(Utils.getToolTip("scaleY2fit"));
        
        prntLbl.setLabelFor(prntSpin);
        prntLbl.setDisplayedMnemonic(Utils.getMnemonic("scale%"));
        
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
        GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
        col1.addComponent(prntLbl);
        col1.addComponent(prntRdoPortrait);
        col1.addComponent(prntChkX);
        leftToRight.addGroup(col1);
        GroupLayout.ParallelGroup col2 = layout.createParallelGroup();
        col2.addComponent(prntSpin);
        col2.addComponent(prntRdoLandscape);
        col2.addComponent(prntChkY);
        leftToRight.addGroup(col2);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        GroupLayout.ParallelGroup prcntRow = layout.createParallelGroup();
        prcntRow.addComponent(prntLbl);
        prcntRow.addComponent(prntSpin);
        topToBottom.addGroup(prcntRow);
        GroupLayout.ParallelGroup orientRow = layout.createParallelGroup();
        orientRow.addComponent(prntRdoPortrait);
        orientRow.addComponent(prntRdoLandscape);
        topToBottom.addGroup(orientRow);
        GroupLayout.ParallelGroup scaleRow = layout.createParallelGroup();
        scaleRow.addComponent(prntChkX);
        scaleRow.addComponent(prntChkY);
        topToBottom.addGroup(scaleRow);

        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);
        
        setBorder(BorderFactory.createTitledBorder(Utils.getResource("print.border")));// meet accessiblity 
    }

    private void setPrintSpinEnabled() {
        prntSpin.setEnabled(!(prntChkX.isSelected() || prntChkY.isSelected()));
    }

    private void setSpinScale(double _d) {
        prntSpin.setValue(new Double(_d));
    }

    private void resetPrint() {
        prefMgr.getPrefNode().putBoolean(PREF_PRINT_SCALE_X, PRINT_DEFAULT_SCALE);
        prefMgr.getPrefNode().putBoolean(PREF_PRINT_SCALE_Y, PRINT_DEFAULT_SCALE);
        prefMgr.getPrefNode().putDouble(PREF_PRINT_RATIO, PRINT_DEFAULT_RATIO);
        prefMgr.getPrefNode().putInt(PREF_PRINT_ORIENTATION, PRINT_DEFAULT_ORIENTATION);
        
        loadPrint();
        PrintUtilities.reset(); 
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
        PrintUtilities.reset(); 
    }

    private double getSpinScale() {
        Number num = ((SpinnerNumberModel) prntSpin.getModel()).getNumber();
        return num.doubleValue();
    }

    private void setScale(double _print) {
    	prefMgr.getPrefNode().putDouble(PREF_PRINT_RATIO, _print);
    }

    private double getScale() {
        return prefMgr.getPrefNode().getDouble(PREF_PRINT_RATIO, PRINT_DEFAULT_RATIO);
    }

    private void setScaleToFit(String _x, boolean _b) {
    	prefMgr.getPrefNode().putBoolean(_x, _b);
    }

    private boolean isScaleToFit(String _x) {
        return prefMgr.getPrefNode().getBoolean(_x, PRINT_DEFAULT_SCALE);
    }

    private void setPrintOrientation(int _i) {
    	prefMgr.getPrefNode().putInt(PREF_PRINT_ORIENTATION, _i);
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
    	return prefMgr.getPrefNode().getInt(PREF_PRINT_ORIENTATION, PRINT_DEFAULT_ORIENTATION);
    }
    public JPanel getButtonPanel() {
    	return btnPnl;
    }

    public void actionPerformed(ActionEvent e) {
    	String _action = e.getActionCommand();

    	if (_action.equals("printX") || _action.equals("printY")) {
    		setPrintSpinEnabled();
    	}
    }
    /**
     * dereference 
     * 
     */
    public void dereference() {
    	prntGroup.remove(prntRdoPortrait);
    	prntGroup.remove(prntRdoLandscape);
    	prntChkX.removeActionListener(this);
    	prntChkY.removeActionListener(this);
    	prntChkX.removeActionListener(bSave.getAction());
    	prntChkY.removeActionListener(bSave.getAction());
        prntRdoPortrait.removeActionListener(bSave.getAction());
        prntRdoLandscape.removeActionListener(bSave.getAction());
        prntSpin.removeChangeListener((SaveAction)bSave.getAction());
    	
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
    	prntLbl.setLabelFor(null);
    	prntLbl.setUI(null);
    	prntLbl.removeAll();
        prntLbl = null;
        prntSpin = null;
        prntChkX = null;
        prntChkY = null;

        prntGroup = null;
        prntRdoPortrait = null;
        prntRdoLandscape = null;  		

        bSave = null;
        bReset = null;
        btnPnl =null;
        prefMgr = null;
    }

    private class ResetAction extends EACMAction {
    	private static final long serialVersionUID = 1L;
    	private static final String CMD = "reset.pref";
    	ResetAction() {
    		super(CMD,"Print");
		}
    	public void actionPerformed(ActionEvent e) {
    		resetPrint();
    		bSave.setEnabled(false);
    	} 	
    }
    
    		
    private class SaveAction extends EACMAction implements ChangeListener {
    	private static final long serialVersionUID = 1L;
    	private static final String CMD = "save.pref";
    	SaveAction() {
    		super(CMD,"Print");
    		setEnabled(checkPrefs());
    	}

		private boolean checkPrefs(){
			boolean enable = false;
			
			if(prntChkX.isSelected()!=isScaleToFit(PREF_PRINT_SCALE_X)) {
				enable =true; 
			}
		        
			if(prntChkY.isSelected()!=isScaleToFit(PREF_PRINT_SCALE_Y)) {
				enable =true; 
			}
		       
			if(getScale()!=getSpinScale()) {
				enable =true; 
			}
			if(getPrintOrientation()==PRINT_DEFAULT_ORIENTATION && prntRdoLandscape.isSelected()){
				enable =true;
			}
			
			return enable;
		}
    	public void actionPerformed(ActionEvent e) {
    		if(e.getActionCommand().equals(this.getValue(Action.NAME))){
    			savePrint();
    			bSave.setEnabled(false);
    		}else{
    			bSave.setEnabled(checkPrefs());
    		}
    	}
		public void stateChanged(ChangeEvent e) {
			bSave.setEnabled(checkPrefs());
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
		loadPrint();
        PrintUtilities.reset(); 
	}
}
