//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012 All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.preference;

import java.awt.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.SerialPref;
import com.ibm.eacm.tabs.TabPanel;
import com.ibm.eacm.toolbar.*;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import java.awt.event.*;
import java.util.prefs.Preferences;

/**
 * This class manages preferences for toolbar layout
 * @author Wendy Stimpson
 */
// $Log: ToolbarPref.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class ToolbarPref extends JPanel implements EACMGlobals, ActionListener,
Preferencable, ListDataListener
{
	private static final long serialVersionUID = 1L;
    private JButton bSave = null;
    private JButton bReset = null;
    private JPanel btnPnl =null;
    
	private JButton btnUp = null;
	private JButton btnDown = null;
	private JButton btnRemove = null;
	private JButton btnInsert = null;
    
    private JPanel pnlTbarMisc = new JPanel(new GridLayout(3, 2, 5, 5));

    private JLabel lblTbarCombo = new JLabel(Utils.getResource("form.toolbar.select")); 
    private JComboBox tbarCombo = new JComboBox();
    private JLabel tbarLabelAvail = new JLabel(Utils.getResource("avail"));
    private ToolbarList tbarAvailList = new ToolbarList();
    private JScrollPane tbarAvailScroll = new JScrollPane(tbarAvailList);

    private JLabel tbarLabelCur = new JLabel(Utils.getResource("current"));
    private ToolbarList tbarCurList = new ToolbarList();
    private JScrollPane tbarCurScroll = new JScrollPane(tbarCurList);
    private JPanel tbarControl = new JPanel(new GridLayout(4,1,5,5));

    private JLabel tbarLabelOrient = new JLabel(Utils.getResource("orient"));
    private JComboBox tbarComboOrient = new JComboBox();
    private JLabel tbarLabelAlign = new JLabel(Utils.getResource("align"));
    private JComboBox tbarComboAlign = new JComboBox();
    private JCheckBox tBarFloatCheck = new JCheckBox(Utils.getResource("flot"));
    private JCheckBox tbarAnimate = new JCheckBox(Utils.getResource("animate"));
    private PrefMgr prefMgr;
	
	/**
	 * pref animate button
	 */
	private static final String PREF_ANIMATE_BUTTON = "eannounce.animate.button";
	/**
	 * def animate button
	 */
	private static final boolean DEFAULT_ANIMATE_BUTTON = true;
	
    public static boolean isAnimate() {
		return Preferences.userNodeForPackage(PrefMgr.class).getBoolean(PREF_ANIMATE_BUTTON, DEFAULT_ANIMATE_BUTTON);
	}

    private void addListeners(){
    	tbarCurList.getModel().addListDataListener(this);
    	tbarComboOrient.addActionListener(this);
    	tbarComboAlign.addActionListener(this);
    	tBarFloatCheck.addActionListener(this);
        tbarCombo.addActionListener(this);
    }
    private void removeListeners(){
    	tbarCurList.getModel().removeListDataListener(this);
    	tbarComboOrient.removeActionListener(this);
    	tbarComboAlign.removeActionListener(this);
      	tBarFloatCheck.removeActionListener(this);
        tbarCombo.removeActionListener(this);
    }
    ToolbarPref(PrefMgr pm) {
    	prefMgr = pm;
        init();
        setToolbarDefault();
        setBorder(BorderFactory.createTitledBorder(Utils.getResource("toolbar.border")));// meet accessiblity 
    }

    /**
     * init
     */
    private void init() {
        initToolbar();
    	createButtonPanel();
    }
    private void initToolbar() {
        loadToolbarCombos();
        
		btnUp = new JButton(new UpAction());
		btnDown = new JButton(new DownAction());
		btnRemove = new JButton(new RemoveAction());
		btnInsert = new JButton(new InsertAction());
		btnUp.setFocusable(false);
		btnDown.setFocusable(false);
		btnRemove.setFocusable(false);
		btnInsert.setFocusable(false);
		
		tbarControl.add(btnInsert);
		tbarControl.add(btnRemove);
		tbarControl.add(btnUp);
		tbarControl.add(btnDown);

        pnlTbarMisc.add(tbarLabelOrient);
        pnlTbarMisc.add(tbarComboOrient);
        pnlTbarMisc.add(tbarLabelAlign);
        pnlTbarMisc.add(tbarComboAlign);
        pnlTbarMisc.add(tBarFloatCheck);
		pnlTbarMisc.add(tbarAnimate);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
        leftToRight.addComponent(lblTbarCombo);
        leftToRight.addComponent(tbarCombo);
        GroupLayout.SequentialGroup  column= layout.createSequentialGroup();
        GroupLayout.ParallelGroup availCol = layout.createParallelGroup();
        availCol.addComponent(tbarLabelAvail);
        availCol.addComponent(tbarAvailScroll);
        column.addGroup(availCol);
        column.addComponent(tbarControl);
        GroupLayout.ParallelGroup curCol = layout.createParallelGroup();
        curCol.addComponent(tbarLabelCur);
        curCol.addComponent(tbarCurScroll);
        column.addGroup(curCol);
        leftToRight.addGroup(column);
        leftToRight.addComponent(pnlTbarMisc);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        topToBottom.addComponent(lblTbarCombo);
        topToBottom.addComponent(tbarCombo);
        GroupLayout.ParallelGroup toolbarlblRow = layout.createParallelGroup();
        toolbarlblRow.addComponent(tbarLabelAvail);
        toolbarlblRow.addComponent(tbarLabelCur);
        topToBottom.addGroup(toolbarlblRow);
        GroupLayout.ParallelGroup toolbarRow = layout.createParallelGroup();
        toolbarRow.addComponent(tbarAvailScroll);
        toolbarRow.addComponent(tbarControl);
        toolbarRow.addComponent(tbarCurScroll);
        topToBottom.addGroup(toolbarRow);
        topToBottom.addComponent(pnlTbarMisc);
 
        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom); 
        
        lblTbarCombo.setLabelFor(tbarCombo);
        lblTbarCombo.setDisplayedMnemonic(Utils.getMnemonic("form.toolbar.select"));
        tbarLabelAvail.setLabelFor(tbarAvailScroll);
        tbarLabelAvail.setDisplayedMnemonic(Utils.getMnemonic("avail"));
        tbarLabelCur.setLabelFor(tbarCurScroll);
        tbarLabelCur.setDisplayedMnemonic(Utils.getMnemonic("current"));
 
        tbarCombo.setSelectedItem(null);
        tbarComboAlign.setSelectedItem(null);
        tbarComboOrient.setSelectedItem(null);
        
        tbarCombo.setActionCommand("tbarCombo");

        tbarLabelOrient.setDisplayedMnemonic(Utils.getMnemonic("orient"));
        tbarLabelOrient.setLabelFor(tbarComboOrient);
        tbarLabelAlign.setDisplayedMnemonic(Utils.getMnemonic("align"));
        tbarLabelAlign.setLabelFor(tbarComboAlign);
        tBarFloatCheck.setMnemonic(Utils.getMnemonic("flot"));
        tbarAnimate.setMnemonic(Utils.getMnemonic("animate"));
        
        addListeners();
    }

    private void toolbarEnable() {
    	boolean _b = true;
        lblTbarCombo.setEnabled(_b);
        tbarCombo.setEnabled(_b);
        tbarLabelAvail.setEnabled(_b);
        tbarAvailList.setEnabled(_b);

        tbarLabelCur.setEnabled(_b);
        tbarCurList.setEnabled(_b);
        tbarControl.setEnabled(_b);

        tbarLabelOrient.setEnabled(_b);
        tbarComboOrient.setEnabled(_b);
        tbarLabelAlign.setEnabled(_b);
        tbarComboAlign.setEnabled(_b);
        tBarFloatCheck.setEnabled(_b);
        tbarAnimate.setEnabled(_b);
    }

    private void setToolbarDefault() {  
    	ComboItem item = DefaultToolbarLayout.NAV_BAR;
    	TabPanel eTab = EACM.getEACM().getCurrentTab();
    	if (eTab != null) {
    		item = eTab.getDefaultToolbarLayout();
    	}
    	removeListeners();
    	tbarCombo.setSelectedItem(item);
    	tbarAvailList.importToolbar(DefaultToolbarLayout.getAvailLayout(item));
    	tbarCurList.importToolbar(getCurrentLayout(item));
    	addListeners();
    	toolbarEnable();
    }
    private boolean toolbarChgs[] = null;
    private void loadToolbarCombos() {
        ComboItem[] bars = DefaultToolbarLayout.TOOLBARS;
        toolbarChgs = new boolean[bars.length];
        for (int i = 0; i < bars.length; ++i) {
            tbarCombo.addItem(bars[i]);
            toolbarChgs[i] = false;
        }
     
        tbarComboOrient.addItem(ComboItem.HORIZONTAL_ITEM);
        tbarComboOrient.addItem(ComboItem.VERTICAL_ITEM);
        tbarComboAlign.addItem(ComboItem.NORTH_ITEM);
        tbarComboAlign.addItem(ComboItem.SOUTH_ITEM);
        tbarComboAlign.addItem(ComboItem.EAST_ITEM);
        tbarComboAlign.addItem(ComboItem.WEST_ITEM);
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
    	btnPnl.add("East", bReset);
    	btnPnl.add("West", bSave);	
    }
    private ToolbarLayout getToolbar(ComboItem _item) {
    	return new ToolbarLayout(_item, (ComboItem) tbarComboOrient.getSelectedItem(), 
    			(ComboItem) tbarComboAlign.getSelectedItem(), tBarFloatCheck.isSelected(), 
    			tbarCurList.exportToolbar());
    }
    private ToolbarItem[] getCurrentLayout(ComboItem _item) {
    	ToolbarLayout ebar = ToolbarLayout.getToolbarLayout(_item);
        tbarComboOrient.setSelectedItem(ebar.getOrientation());
        tbarComboAlign.setSelectedItem(ebar.getAlignment());
        tBarFloatCheck.setSelected(ebar.isFloatable());
        tbarAnimate.setSelected(prefMgr.getPrefNode().getBoolean(PREF_ANIMATE_BUTTON, DEFAULT_ANIMATE_BUTTON));
        return ebar.getToolbarItems();
    }

    public JPanel getButtonPanel() {
    	return btnPnl;
    }
    /**
     * dereference 
     * 
     */
    public void dereference() {
     	tbarCurList.getModel().removeListDataListener(this);
        
        tbarAvailScroll.removeAll();
        tbarAvailScroll.setUI(null);
        tbarAvailScroll = null;
        
        tbarCurScroll.removeAll();
        tbarCurScroll.setUI(null);
        tbarCurScroll = null;
        
        tbarCombo.removeActionListener(this);
        tbarCombo.removeAllItems(); 
        tbarCombo = null;
      	tbarComboOrient.removeActionListener(this);
    	tbarComboAlign.removeActionListener(this);
    	tBarFloatCheck.removeActionListener(this);
    	tBarFloatCheck = null;
    	
        tbarComboOrient.removeAllItems(); 
        tbarComboOrient.setUI(null);
        tbarComboOrient = null;
        tbarComboAlign.removeAllItems(); 
        tbarComboAlign.setUI(null);
        tbarComboAlign = null;
        
        lblTbarCombo.setLabelFor(null);
        lblTbarCombo = null;
        tbarLabelAvail.setLabelFor(null);
        tbarLabelAvail = null;
        tbarLabelCur.setLabelFor(null);
        tbarLabelCur = null;
        tbarLabelOrient.setLabelFor(null);
        tbarLabelOrient = null;
        tbarLabelAlign.setLabelFor(null);
        tbarLabelAlign = null;
        
        pnlTbarMisc.removeAll();
        pnlTbarMisc.setUI(null);
        pnlTbarMisc = null;
        tbarControl.removeAll();
        tbarControl.setUI(null);
        tbarControl = null;

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
    	
       	InsertAction ia  = (InsertAction)btnInsert.getAction();
    	ia.dereference();
    	btnInsert.setAction(null);
    	btnInsert.setUI(null);
    	RemoveAction rva  = (RemoveAction)btnRemove.getAction();
    	rva.dereference();
    	btnRemove.setAction(null);
    	btnRemove.setUI(null);
    	UpAction ua  = (UpAction)btnUp.getAction();
    	ua.dereference();
    	btnUp.setAction(null);
    	btnUp.setUI(null);
    	DownAction da  = (DownAction)btnDown.getAction();
    	da.dereference();
    	btnDown.setAction(null);
    	btnDown.setUI(null);
    	
    	tbarAnimate.removeAll();
    	tbarAnimate.setUI(null);
    	tbarAnimate = null;
    	
        tbarCurList.dereference();
        tbarCurList = null;
        
        tbarAvailList.dereference();
        tbarAvailList = null;
        
    	removeAll();
    	setUI(null);
    	
        bSave = null;
        bReset = null;
        btnPnl =null;
        
        btnUp = null;
    	btnDown = null;
    	btnRemove = null;
    	btnInsert = null;
    	prefMgr = null;
    }
    private class ResetAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref";
		ResetAction() {
            super(CMD,"Toolbar layout");
		}
		public void actionPerformed(ActionEvent e) {
			bSave.setEnabled(true);
			Object o = tbarCombo.getSelectedItem();
			if (o instanceof ComboItem) {
				String key = ((ComboItem) o).getStringKey();
				SerialPref.removePref(key);
				removeListeners();
				tbarCurList.importToolbar(getCurrentLayout((ComboItem) o));
				addListeners();
				//msg11017.0 = The Changes you made may not take effect until the next time you log in.
				com.ibm.eacm.ui.UI.showFYI(ToolbarPref.this,Utils.getResource("msg11017.0"));
				toolbarChgs[tbarCombo.getSelectedIndex()] = false;
			}
	
			prefMgr.getPrefNode().putBoolean(PREF_ANIMATE_BUTTON, DEFAULT_ANIMATE_BUTTON);
		} 	
    }
    public void actionPerformed(ActionEvent e) {
		String _action = e.getActionCommand();

        if (_action.equals("tbarCombo")) {
            Object o = tbarCombo.getSelectedItem();
            if (o instanceof ComboItem) {
                tbarAvailList.importToolbar(DefaultToolbarLayout.getAvailLayout((ComboItem) o));
                removeListeners();
                tbarCurList.importToolbar(getCurrentLayout((ComboItem) o));
                addListeners();
            }
        }else{
        	toolbarChgs[tbarCombo.getSelectedIndex()] = true;
        }
    }
    private class SaveAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "save.pref";
		SaveAction() {
            super(CMD,"Toolbar layout");
           // setEnabled(checkCombo());
        }

		/*private boolean checkCombo(){
			boolean enable = false;
			LnF lnf = (LnF)lnfCombo.getSelectedItem();
			String key = SerialPref.getPref(LOOK_AND_FEEL, (String)null);
			if(lnf!=null && !lnf.form.equals(key)){
				enable = true;
			}
			
			return enable;
		}*/
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("lnfCombo")){
				// bSave.setEnabled(checkCombo());
			}else{
				Object o = tbarCombo.getSelectedItem();
				if (o instanceof ComboItem) {
					String key = ((ComboItem) o).getStringKey();
					SerialPref.putPref(key, getToolbar((ComboItem) o));
					//msg11017.0 = The Changes you made may not take effect until the next time you log in.
					com.ibm.eacm.ui.UI.showFYI(ToolbarPref.this,Utils.getResource("msg11017.0"));
					toolbarChgs[tbarCombo.getSelectedIndex()] = false;
				}
				prefMgr.getPrefNode().putBoolean(PREF_ANIMATE_BUTTON, tbarAnimate.isSelected());	
			}
		} 	
    }
    private class InsertAction extends EACMAction implements FocusListener{
		private static final long serialVersionUID = 1L;

		private static final String CMD = "toolbar.insert";
		InsertAction() {
            super(CMD);
            setEnabled(false);
            tbarAvailList.addFocusListener(this);
        }

		public void dereference(){
			tbarAvailList.removeFocusListener(this);
			super.dereference();
		}
		public void actionPerformed(ActionEvent e) {
			ToolbarItem item = tbarAvailList.getToolbarItem(tbarAvailList.getSelectedIndex());
			tbarCurList.add(item);
		}

		public void focusGained(FocusEvent e) {
			btnInsert.setEnabled(true); 
		}

		public void focusLost(FocusEvent e) {
			btnInsert.setEnabled(false);
		} 	
    } 
    private class RemoveAction extends EACMAction implements FocusListener{
		private static final long serialVersionUID = 1L;
		private static final String CMD = "toolbar.remove";
		RemoveAction() {
            super(CMD);
            setEnabled(false);
            
            tbarCurList.addFocusListener(this);
        }

		public void dereference(){
			tbarCurList.removeFocusListener(this);
			super.dereference();
		}
		public void actionPerformed(ActionEvent e) {
			tbarCurList.removeSelected();
		}

		public void focusGained(FocusEvent e) {
			btnRemove.setEnabled(true); 
		}

		public void focusLost(FocusEvent e) {
			btnRemove.setEnabled(false);
		} 	
    }
    private class DownAction extends EACMAction implements FocusListener{
		private static final long serialVersionUID = 1L;
		private static final String CMD = "toolbar.down";
		DownAction() {
            super(CMD);
            setEnabled(false);
            
            tbarCurList.addFocusListener(this);
        }

		public void dereference(){
			super.dereference();
			tbarCurList.removeFocusListener(this);
		}
		public void actionPerformed(ActionEvent e) {
			tbarCurList.moveItem(tbarCurList.getSelectedIndex(), 1);
		}

		public void focusGained(FocusEvent e) {
			btnDown.setEnabled(true); 
		}

		public void focusLost(FocusEvent e) {
			btnDown.setEnabled(false);
		} 	
    }
    private class UpAction extends EACMAction implements FocusListener{
		private static final long serialVersionUID = 1L;
		private static final String CMD = "toolbar.up";
		UpAction() {
            super(CMD);
            setEnabled(false);
            
            tbarCurList.addFocusListener(this);
        }

		public void dereference(){
			tbarCurList.removeFocusListener(this);
			super.dereference();
		}
		public void actionPerformed(ActionEvent e) {
			tbarCurList.moveItem(tbarCurList.getSelectedIndex(), -1);
		}

		public void focusGained(FocusEvent e) {
			btnUp.setEnabled(true); 
		}

		public void focusLost(FocusEvent e) {
			btnUp.setEnabled(false);
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
		boolean tbchgs = false;
		for (int i=0; i<toolbarChgs.length &&!tbchgs; i++){
			if (toolbarChgs[i]){
				tbchgs = true;
			}
		}
		return tbchgs;
	} 

	/* (non-Javadoc)
	 * @see javax.swing.event.ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)
	 */
	public void contentsChanged(ListDataEvent e) {
		toolbarChgs[tbarCombo.getSelectedIndex()] = true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListDataListener#intervalAdded(javax.swing.event.ListDataEvent)
	 */
	public void intervalAdded(ListDataEvent e) {
		toolbarChgs[tbarCombo.getSelectedIndex()] = true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListDataListener#intervalRemoved(javax.swing.event.ListDataEvent)
	 */
	public void intervalRemoved(ListDataEvent e) {
		toolbarChgs[tbarCombo.getSelectedIndex()] = true;
	}

	public void updateFromPrefs() {
		// these prefs are not in xml preferences, so nothing to do
	}

}
