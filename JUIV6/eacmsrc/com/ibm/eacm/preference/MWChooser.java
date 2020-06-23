//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.preference;

import com.ibm.eacm.mw.*;
import com.ibm.eacm.objects.SerialPref;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.actions.EACMAction;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * This class manages preferences for middleware locations
 * @author Wendy Stimpson
 */
// $Log: MWChooser.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class MWChooser extends JPanel implements ActionListener,EACMGlobals,Preferencable
{
	private static final long serialVersionUID = 1L;

    private JPanel btnPnl = null;
    private JPanel btnPnlWest = null;

    private JButton bSave = null;
    private JButton bAdd = null;
    private JButton bReset = null;

    private MWObject mwObj = null;
    private MWObject prevMwObj = null;
    private JComboBox mwCombo = null;

    private JLabel lblName0 = new JLabel(Utils.getResource("mw.name"));
    private JLabel lblName1 = new JLabel();
    private JLabel lblIP0 = new JLabel(Utils.getResource("mw.ip"));
    private JLabel lblIP1 = new JLabel();
    private JLabel lblPort0 = new JLabel(Utils.getResource("mw.port"));
    private JLabel lblPort1 = new JLabel();
    private JLabel lblReport0 = new JLabel(Utils.getResource("mw.report"));
    private JLabel lblReport1 = new JLabel();

    /**
     * mwChooser
     */
    public MWChooser() {
        init();
    }

    /**
     * init
     */
    private void init() {
        mwCombo = new JComboBox(LoginMgr.getMWParser());
        mwCombo.addActionListener(this);
        mwCombo.setActionCommand("mwCombo");
        mwCombo.setToolTipText(Utils.getResource("chooseMW-acc"));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup column = layout.createParallelGroup();
        column.addComponent(mwCombo);
        GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
        GroupLayout.ParallelGroup labelCol = layout.createParallelGroup();
        labelCol.addComponent(lblName0);
        labelCol.addComponent(lblIP0);
        labelCol.addComponent(lblPort0);
        labelCol.addComponent(lblReport0);
        leftToRight.addGroup(labelCol);
        GroupLayout.ParallelGroup valueCol = layout.createParallelGroup();
        valueCol.addComponent(lblName1);
        valueCol.addComponent(lblIP1);
        valueCol.addComponent(lblPort1);
        valueCol.addComponent(lblReport1);
        leftToRight.addGroup(valueCol);
        column.addGroup(leftToRight);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        topToBottom.addComponent(mwCombo);

        GroupLayout.ParallelGroup nameRow = layout.createParallelGroup();
        nameRow.addComponent(lblName0);
        nameRow.addComponent(lblName1);
        topToBottom.addGroup(nameRow);
        GroupLayout.ParallelGroup ipRow = layout.createParallelGroup();
        ipRow.addComponent(lblIP0);
        ipRow.addComponent(lblIP1);
        topToBottom.addGroup(ipRow);
        GroupLayout.ParallelGroup portRow = layout.createParallelGroup();
        portRow.addComponent(lblPort0);
        portRow.addComponent(lblPort1);
        topToBottom.addGroup(portRow);
        GroupLayout.ParallelGroup rptRow = layout.createParallelGroup();
        rptRow.addComponent(lblReport0);
        rptRow.addComponent(lblReport1);
        topToBottom.addGroup(rptRow);

        layout.setHorizontalGroup(column);
        layout.setVerticalGroup(topToBottom);

        setBorder(BorderFactory.createTitledBorder(Utils.getResource("mw.border")));// meet accessiblity

        loadMWObject();
        prevMwObj = (MWObject)mwCombo.getSelectedItem();
    }

    private void createButtons(){
    	btnPnl = new JPanel(new BorderLayout(5, 5));
    	btnPnlWest = new JPanel(new BorderLayout(5,5));

    	bSave = new JButton(new MWSaveAction());
//    	 this is needed or the mnemonic doesnt activate
    	bSave.setMnemonic((char)((Integer)bSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	mwCombo.addActionListener(bSave.getAction());

    	bAdd = new JButton(new MWAddAction());
    	bAdd.setMnemonic((char)((Integer)bAdd.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

    	bReset = new JButton(new MWResetAction());
    	bReset.setMnemonic((char)((Integer)bReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

    	btnPnl.add(bReset,BorderLayout.EAST);
    	btnPnlWest.add(bSave,BorderLayout.WEST);
    	btnPnlWest.add(bAdd,BorderLayout.EAST);
    	btnPnl.add(btnPnlWest,BorderLayout.WEST);
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.preference.Preferencable#getButtonPanel()
     */
    public JPanel getButtonPanel(){
    	if (btnPnl==null){
    		createButtons();
    	}
    	return btnPnl;
    }

    /**
     * dereference
     *
     */
    public void dereference() {
    	mwCombo.removeActionListener(this);
    	//do not clear mwcombo, model is shared
    	if (btnPnl!=null){
    		mwCombo.removeActionListener(bSave.getAction());
    		// deref button panel
    		btnPnl.removeAll();
    		btnPnl.setUI(null);
    		MWSaveAction sa  = (MWSaveAction)bSave.getAction();
    		sa.dereference();
    		bSave.setAction(null);
    		bSave.setUI(null);

    		MWResetAction ra  = (MWResetAction)bReset.getAction();
    		ra.dereference();
    		bReset.setAction(null);
    		bReset.setUI(null);

    		MWAddAction aa  = (MWAddAction)bAdd.getAction();
    		aa.dereference();
    		bAdd.setAction(null);
    		bAdd.setUI(null);
    		btnPnl = null;
    	    bSave = null;
    	    bAdd = null;
    	    bReset = null;
    	    btnPnlWest.removeAll();
    	    btnPnlWest.setUI(null);
    	    btnPnlWest = null;
    	}

        mwObj = null;
        prevMwObj = null;
        mwCombo = null;

        lblName0.removeAll();
        lblName0.setUI(null);
        lblName0 = null;
        lblName1.removeAll();
        lblName1.setUI(null);
        lblName1 = null;
        lblIP0.removeAll();
        lblIP0.setUI(null);
        lblIP0 = null;
        lblIP1.removeAll();
        lblIP1.setUI(null);
        lblIP1 = null;
        lblPort0.removeAll();
        lblPort0.setUI(null);
        lblPort0 = null;
        lblPort1.removeAll();
        lblPort1.setUI(null);
        lblPort1 = null;
        lblReport0.removeAll();
        lblReport0.setUI(null);
        lblReport0 = null;
        lblReport1.removeAll();
        lblReport1.setUI(null);
        lblReport1 = null;

        removeAll();
    	setUI(null);
    }

    private void loadMWObject() {
        mwObj = (MWObject)mwCombo.getSelectedItem();
        if (mwObj == null) {
            lblName1.setText("");
            lblIP1.setText("");
            lblPort1.setText("");
            lblReport1.setText("");
        } else {
            lblName1.setText(mwObj.getName());
            lblIP1.setText(mwObj.getIP());
            lblPort1.setText(Integer.toString(mwObj.getPort()));
            lblReport1.setText(mwObj.getReportPrefix());
        }
    }

    /**
     * Need ability to reset original selection if user closes the dialog after changing but before saving
     * -combobox is shared
     */
    public void restoreMWSelected() {
    	mwCombo.setSelectedItem(prevMwObj);
    }

    /**
     * actionPerformed
     *
     * @param _action
     */
	public void actionPerformed(ActionEvent e) {
		loadMWObject();
		if(getTopLevelAncestor()!=null){
			getTopLevelAncestor().setSize(getTopLevelAncestor().getPreferredSize());
		}
    }

	/**
	 * addMWLocation
	 * need complete xml to load
	 * <mw description="test addmw" name ="OPICM-Middleware" ip="pokxea9.pok.ibm.com" port="3012" report="http://pokxea9.pok.ibm.com/transform/oim/eacm/bui/" />
	 */
	private void addMWLocation() {
		String msg = Utils.getResource("mw.add.command");
		String mwloc="description=\"NEW DESC\" name=\"NEW NAME\" ip=\"NEW IP\" port=\"NEW PORT\" report=\"http://NEW URL\"";
		String mwXML = com.ibm.eacm.ui.UI.showInput(this,msg, mwloc,70);
		if (mwloc.equalsIgnoreCase(mwXML)){
			return;
		}
        if (!mwXML.startsWith("<")){
        	mwXML="<mw "+mwXML+" />";
        }
		if (mwXML!=null && mwXML.length()>0 &&
			LoginMgr.getMWParser().validateXMLAndLoad(this,mwXML)) { // was added to the collection
			// now add it to the file
			if (Utils.exists(RESOURCE_DIRECTORY, MWParser.MW_FILENAME)) {
				Utils.insertString(RESOURCE_DIRECTORY + MWParser.MW_FILENAME,mwXML,-1,EACM_FILE_ENCODE);
				mwCombo.setSelectedIndex(mwCombo.getItemCount()-1);
				//msg5025.4 = Middleware Location was successfully added.
				com.ibm.eacm.ui.UI.showMessage(null,"information-title",
						JOptionPane.INFORMATION_MESSAGE, "information-acc", Utils.getResource("msg5025.4"));
			}
		}
	}
    private class MWAddAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "mw.add";
		MWAddAction() {
            super(CMD);
        }
		public void actionPerformed(ActionEvent e) {
			addMWLocation();
		}
    }
    private class MWResetAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref";
		MWResetAction() {
            super(CMD,"Middleware");
            String key = SerialPref.getPref(MWParser.MIDDLEWARE_PROFILE_KEY, "null");
            setEnabled(!(key.equals("null")));
        }

		public void actionPerformed(ActionEvent e) {
			SerialPref.removePref(MWParser.MIDDLEWARE_PROFILE_KEY);
			bSave.setEnabled(true);
			bReset.setEnabled(false);
		}
    }
    private class MWSaveAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "save.pref";
		MWSaveAction() {
			super(CMD,"Middleware");
            setEnabled(checkMWCombo());
        }

		private boolean checkMWCombo(){
			boolean enable = false;
			MWObject mwo = (MWObject)mwCombo.getSelectedItem();
			String key = SerialPref.getPref(MWParser.MIDDLEWARE_PROFILE_KEY, "null");
			if(mwo!=null && !mwo.key().equals(key)){
				enable = true;
			}

			return enable;
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("mwCombo")){
				bSave.setEnabled(checkMWCombo());
			}else{
				if (mwObj != null) {
					SerialPref.putPref(MWParser.MIDDLEWARE_PROFILE_KEY, mwObj.key());
					prevMwObj = mwObj;
					bSave.setEnabled(false);
					bReset.setEnabled(true);
					//msg11017.0 = The Changes you made may not take effect until the next time you log in.
					com.ibm.eacm.ui.UI.showFYI(MWChooser.this,Utils.getResource("msg11017.0"));
				}
			}
		}
    }
	/* (non-Javadoc)
	 * called when dialog is closing
	 * @see com.ibm.eacm.preference.Preferencable#isClosing()
	 */
	public void isClosing() {
		try{
			restoreMWSelected();  // make sure unsaved value is not used
		}catch(Throwable exc){
			exc.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * notify user that changes have not been saved
	 * @see com.ibm.eacm.preference.Preferencable#hasChanges()
	 */
	public boolean hasChanges() {
		boolean chgs = false;
		if (bSave!=null){
			chgs = bSave.isEnabled() && bReset.isEnabled();
		}
		return chgs;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.preference.Preferencable#updateFromPrefs()
	 */
	public void updateFromPrefs() {
	}
}
