//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.objects;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.ui.FindRepFrame;


/******************************************************************************
 * This is used for find and replace, it maintains all state
 *
 * @author Wendy Stimpson
 */
//$Log: FindRepMgr.java,v $
//Revision 1.3  2013/09/13 18:28:07  wendy
//show frame if iconified
//
//Revision 1.2  2013/07/18 18:23:22  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class FindRepMgr extends JPanel implements EACMGlobals, PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.3 $";

	private JButton btnFind = null;
	private JButton btnReplace = null;
	private JButton btnReplaceNext = null;
	private JButton btnReplaceAll = null;
	private JButton btnReset = null;
	private JButton btnCancel = null;

	//checkbox
	private JCheckBox multColCB = new JCheckBox(Utils.getResource("mult"));
	private JCheckBox CBcaseSensitive=new JCheckBox(Utils.getResource("case"));
	private JCheckBox wrapCB=new JCheckBox(Utils.getResource("wrap"));

	//label
	private JLabel lblFind = new JLabel(Utils.getResource("fnd-l"));
	private JLabel lblReplace = new JLabel(Utils.getResource("rpl-l"));

	//Text fields
	private JComboBox findCombo=null, replaceCombo=null;
	private JTextField findField=null, replaceField=null;
	private ActionListener findComboListener=null, replaceComboListener=null; // need ref for later release

	//Radio Buttons
	private JRadioButton backwardRB = new JRadioButton(Utils.getResource("up"));
	private JRadioButton forwardRB = new JRadioButton(Utils.getResource("dwn"),true);

	private ButtonGroup btnGroup = new ButtonGroup();

	private Hashtable<String, EACMAction> actionTbl = new Hashtable<String, EACMAction>();

	private Findable findable = null;

	private EACMAction closeAction = null;

	/**
	 *
	 */
	public FindRepMgr()  {
		super(null);
		init();
		enableAll(false); // no findable yet
	}
	/**
	 * init components
	 */
	private void init() {
		createActions();

		CBcaseSensitive.setMnemonic(Utils.getMnemonic("case"));
		multColCB.setMnemonic(Utils.getMnemonic("mult"));
		wrapCB.setMnemonic(Utils.getMnemonic("wrap"));
		wrapCB.setSelected(true);

		findCombo = new JComboBox();
		findCombo.setEditable(true);
		replaceCombo = new JComboBox();
		replaceCombo.setEditable(true);

		JPanel m_pBtn = new JPanel(new GridLayout(1,7,5,5));

		btnFind = new JButton(actionTbl.get(FIND_ACTION));
		btnFind.setMnemonic((char)((Integer)btnFind.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(btnFind);

		btnReplace = new JButton(actionTbl.get(REPLACE_ACTION));
		btnReplace.setMnemonic((char)((Integer)btnReplace.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(btnReplace);

		btnReplaceNext = new JButton(actionTbl.get(REPLACENEXT_ACTION));
		btnReplaceNext.setMnemonic((char)((Integer)btnReplaceNext.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(btnReplaceNext);

		btnReplaceAll = new JButton(actionTbl.get(REPLACEALL_ACTION));
		btnReplaceAll.setMnemonic((char)((Integer)btnReplaceAll.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(btnReplaceAll);

		btnReset = new JButton(actionTbl.get(RESETCOLOR_ACTION));
		btnReset.setMnemonic((char)((Integer)btnReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(btnReset);

		btnCancel = new JButton(actionTbl.get(CANCEL_ACTION));
		btnCancel.setMnemonic((char)((Integer)btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(btnCancel);

		lblFind.setLabelFor(findCombo);
		lblFind.setDisplayedMnemonic(Utils.getMnemonic("fnd-l"));

		lblReplace.setLabelFor(replaceCombo);
		lblReplace.setDisplayedMnemonic(Utils.getMnemonic("rpl-l"));

		btnGroup = new ButtonGroup();
		forwardRB.setMnemonic(Utils.getMnemonic("dwn"));
		forwardRB.setToolTipText(Utils.getToolTip("dwn"));
		backwardRB.setMnemonic(Utils.getMnemonic("up"));
		backwardRB.setToolTipText(Utils.getToolTip("up"));

		btnGroup.add(backwardRB);
		btnGroup.add(forwardRB);

		JPanel pDir = new JPanel(new GridLayout(1, 2, 5, 5));
		pDir.setBorder(new TitledBorder("Direction"));
		pDir.add(backwardRB);
		pDir.add(forwardRB);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		GroupLayout.SequentialGroup  fndgrp= layout.createSequentialGroup();
		GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
		col1.addComponent(lblFind);
		col1.addComponent(lblReplace);
		fndgrp.addGroup(col1);
		GroupLayout.ParallelGroup col2 = layout.createParallelGroup();
		col2.addComponent(findCombo);
		col2.addComponent(replaceCombo);
		fndgrp.addGroup(col2);
		leftToRight.addGroup(fndgrp);

		GroupLayout.SequentialGroup  chkgrp= layout.createSequentialGroup();
		GroupLayout.ParallelGroup col3 = layout.createParallelGroup();
		col3.addComponent(CBcaseSensitive);
		col3.addComponent(multColCB);
		chkgrp.addGroup(col3);
		chkgrp.addComponent(wrapCB);
		chkgrp.addGap(100);
		chkgrp.addComponent(pDir,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE);
		leftToRight.addGroup(chkgrp);
		leftToRight.addComponent(m_pBtn);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		GroupLayout.ParallelGroup row = layout.createParallelGroup();
		row.addComponent(lblFind);
		row.addComponent(findCombo);
		topToBottom.addGroup(row);

		row = layout.createParallelGroup();
		row.addComponent(lblReplace);
		row.addComponent(replaceCombo);
		topToBottom.addGroup(row);

		row = layout.createParallelGroup();
		GroupLayout.SequentialGroup col = layout.createSequentialGroup();
		col.addComponent(CBcaseSensitive);
		col.addComponent(multColCB);
		row.addGroup(col);
		row.addComponent(wrapCB);
		row.addComponent(pDir);
		topToBottom.addGroup(row);

		topToBottom.addComponent(m_pBtn);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

		KeyboardFocusManager focusManager =
			KeyboardFocusManager.getCurrentKeyboardFocusManager();
		focusManager.addPropertyChangeListener("permanentFocusOwner", this);

		addListeners();
	}

	// these must be reinstalled when laf is changed
	public void addListeners()
	{
		// add listener to react to enter key-press in find field
		findComboListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				btnFind.requestFocusInWindow();
				actionTbl.get(FIND_ACTION).actionPerformed(e);
			}
		};
		findCombo.getEditor().addActionListener(findComboListener);
		//An editable combo box fires an action event when the user chooses an item from the menu
		//and when the user types Enter.
		findCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				if ("comboBoxEdited".equals(evt.getActionCommand())) {
					// User has typed in a string; only possible with an editable combobox
					String curSelection = findField.getText();
					if (findCombo.getSelectedIndex()==-1){
						findCombo.insertItemAt(curSelection,0);
						findCombo.setSelectedIndex(0);
					}else{
						for (int i=0; i<findCombo.getItemCount(); i++) {
							String item = (String)findCombo.getItemAt(i);
							if (item.equals(curSelection)) { // exact match
								findCombo.setSelectedIndex(i);
								break;
							}
						}
					}
				}

				// make sure action is enabled
				actionTbl.get(FIND_ACTION).setEnabled(findField.getText().length()!=0);
			}
		});

		// add listener to react to enter key-press in replace field
		replaceComboListener = new ActionListener() {
			public void actionPerformed(ActionEvent e)	{
				btnReplace.requestFocusInWindow();
				actionTbl.get(REPLACE_ACTION).actionPerformed(e);
			}
		};
		replaceCombo.getEditor().addActionListener(replaceComboListener);
		replaceCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)	{
				if ("comboBoxEdited".equals(evt.getActionCommand())) {
					// User has typed in a string; only possible with an editable combobox
					String curSelection = replaceField.getText();
					if (replaceCombo.getSelectedIndex()==-1){
						replaceCombo.insertItemAt(curSelection,0);
						replaceCombo.setSelectedIndex(0);
					}else{
						for (int i=0; i<replaceCombo.getItemCount(); i++) {
							String item = (String)replaceCombo.getItemAt(i);
							if (item.equals(curSelection)) { // exact match
								replaceCombo.setSelectedIndex(i);
								break;
							}
						}
					}
				}
				if (actionTbl.get(FIND_ACTION).isEnabled())	{
					// make sure action is enabled
					actionTbl.get(REPLACE_ACTION).setEnabled(replaceField.getText().length()!=0);
					actionTbl.get(REPLACEALL_ACTION).setEnabled(actionTbl.get(REPLACE_ACTION).isEnabled());
					actionTbl.get(REPLACENEXT_ACTION).setEnabled(actionTbl.get(REPLACE_ACTION).isEnabled());
				}
			}
		});

		// add key listener to textfield in combo box to enable/disable actions
		for (int i=0; i<findCombo.getComponentCount(); i++)	{
			Component comp = findCombo.getComponent(i);
			if (comp instanceof JTextField)	{
				findField = (JTextField)comp;
				// allow esc to get to the container
				findField.getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "none");

				findField.setToolTipText(Utils.getToolTip("fnd"));
				findField.addKeyListener(new KeyAdapter(){
					public void keyReleased(KeyEvent e)	{
						actionTbl.get(FIND_ACTION).setEnabled(findField.getText().length()!=0);
						if (findField.getText().length()==0){
							actionTbl.get(REPLACE_ACTION).setEnabled(false);
							actionTbl.get(REPLACEALL_ACTION).setEnabled(false);
							actionTbl.get(REPLACENEXT_ACTION).setEnabled(false);
						}else if(replaceField.getText().length()!=0&&replaceCombo.isEnabled()){
							actionTbl.get(REPLACE_ACTION).setEnabled(true);
							actionTbl.get(REPLACEALL_ACTION).setEnabled(true);
							actionTbl.get(REPLACENEXT_ACTION).setEnabled(true);
						}
					}
					public void keyTyped(KeyEvent e) {
						// popup stays open without this
						if(findCombo.isPopupVisible()){
							findCombo.hidePopup();
						}
					}
				});
				findField.addFocusListener(new FocusAdapter(){
					public void focusGained(FocusEvent e){ // Invoked when a component gains the keyboard focus
						if (findField.getText().length()>0)	{
							findField.setSelectionStart(0);
							findField.setSelectionEnd(findField.getText().length());
						}
					}
				});
				findField.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount()>0){
							// popup stays open without this
							if(findCombo.isPopupVisible()){
								findCombo.hidePopup();
							}
						}
					}
				});
				break;
			}
		}
		for (int i=0; i<replaceCombo.getComponentCount(); i++)	{
			Component comp = replaceCombo.getComponent(i);
			if (comp instanceof JTextField)	{
				replaceField = (JTextField)comp;
				replaceField.setToolTipText(Utils.getToolTip("rpl"));
				// allow esc to get to the container
				replaceField.getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "none");

				replaceField.addKeyListener(new KeyAdapter(){
					public void keyReleased(KeyEvent e)	{
						if (actionTbl.get(FIND_ACTION).isEnabled())	{
							actionTbl.get(REPLACE_ACTION).setEnabled(replaceField.getText().length()!=0);
							actionTbl.get(REPLACENEXT_ACTION).setEnabled(replaceField.getText().length()!=0);
							actionTbl.get(REPLACEALL_ACTION).setEnabled(replaceField.getText().length()!=0);
						}
					}
					public void keyTyped(KeyEvent e) {
						// popup stays open without this
						if(replaceCombo.isPopupVisible()){
							replaceCombo.hidePopup();
						}
					}
				});
				replaceField.addFocusListener(new FocusAdapter() {
					public void focusGained(FocusEvent e) {// Invoked when a component gains the keyboard focus
						if (replaceField.getText().length()>0)	{
							replaceField.setSelectionStart(0);
							replaceField.setSelectionEnd(replaceField.getText().length());
						}
					}
				});
				replaceField.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount()>0){
							// popup stays open without this
							if(replaceCombo.isPopupVisible()){
								replaceCombo.hidePopup();
							}
						}
					}
				});
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();
		if (("permanentFocusOwner".equals(prop))){
			Object obj = e.getNewValue();
			if (obj instanceof Component){
				Component comp = (Component)obj;
				while (comp != null){
					if ((comp instanceof FindableComp) ||
							(comp instanceof Findable) ||
							(comp instanceof FindRepFrame) ||
							(comp instanceof JOptionPane)){
						break;
					}
					comp = comp.getParent();
				}

				if (comp instanceof FindableComp){
					setFindable(((FindableComp)comp).getFindable());
				}else if (comp instanceof Findable){
					setFindable((Findable)comp);
				}else if (comp instanceof FindRepFrame){
//					System.err.println("findrepframe focusowner is me");
				}else if (comp instanceof JOptionPane){
//					System.err.println("JOptionPane must be user msg");
				}else if (obj instanceof com.ibm.eacm.ui.TabbedMenuPane){
					com.ibm.eacm.ui.TabbedMenuPane tabpane = (com.ibm.eacm.ui.TabbedMenuPane)obj;
					if(tabpane.getSelectedTab() instanceof FindableComp){
						setFindable(((FindableComp)tabpane.getSelectedTab()).getFindable());
					}
				}else {
//					System.err.println("\n\nfindrepmgr clearing findable");
					setFindable(null);
				}
			}
		}
	}
	private void setFindable(Findable f){
		if (findable!=f){
			findable = f;
			enableAll(findable!=null);
		}
	}
	private void enableAll(boolean b){
		if (b){
			findCombo.setEnabled(true);
			replaceCombo.setEnabled(findable.isReplaceable());

			actionTbl.get(FIND_ACTION).setEnabled(findField.getText().length()!=0);
			if (actionTbl.get(FIND_ACTION).isEnabled() &&replaceCombo.isEnabled())	{
				// make sure action is enabled
				actionTbl.get(REPLACE_ACTION).setEnabled(replaceField.getText().length()!=0);
				actionTbl.get(REPLACEALL_ACTION).setEnabled(actionTbl.get(REPLACE_ACTION).isEnabled());
				actionTbl.get(REPLACENEXT_ACTION).setEnabled(actionTbl.get(REPLACE_ACTION).isEnabled());
			}

			actionTbl.get(RESETCOLOR_ACTION).setEnabled(findable.hasFound());
			multColCB.setSelected(findable.isMultiColumn());
			multColCB.setEnabled(findable.isMultiColumn());
		}else{
			actionTbl.get(FIND_ACTION).setEnabled(false);
			actionTbl.get(REPLACE_ACTION).setEnabled(false);
			actionTbl.get(REPLACENEXT_ACTION).setEnabled(false);
			actionTbl.get(REPLACEALL_ACTION).setEnabled(false);
			actionTbl.get(RESETCOLOR_ACTION).setEnabled(false);

			findCombo.setEnabled(false);
			replaceCombo.setEnabled(false);
			multColCB.setEnabled(false);
		}
	}

	/**
	 * release memory
	 */
	public void dereference(){
		btnGroup.remove(backwardRB);
		btnGroup.remove(forwardRB);
		btnGroup = null;

		btnFind.setAction(null);
		btnFind.setUI(null);
		btnFind = null;

		btnReplace.setAction(null);
		btnReplace.setUI(null);
		btnReplace = null;

		btnReplaceNext.setAction(null);
		btnReplaceNext.setUI(null);
		btnReplaceNext = null;

		btnReplaceAll.setAction(null);
		btnReplaceAll.setUI(null);
		btnReplaceAll = null;

		btnReset.setAction(null);
		btnReset.setUI(null);
		btnReset = null;

		btnCancel.setAction(null);
		btnCancel.setUI(null);
		btnCancel = null;

		multColCB.setUI(null);
		multColCB = null;

		CBcaseSensitive.setUI(null);
		CBcaseSensitive = null;

		wrapCB.setUI(null);
		wrapCB = null;

		lblFind.setLabelFor(null);
		lblFind.removeAll();
		lblFind.setUI(null);
		lblFind = null;

		lblReplace.setLabelFor(null);
		lblReplace.removeAll();
		lblReplace.setUI(null);
		lblReplace = null;

		closeAction = null;

		findable = null;

		EventListener listeners[] = findCombo.getListeners(ActionListener.class);

		findCombo.getEditor().removeActionListener(findComboListener);
		replaceCombo.getEditor().removeActionListener(replaceComboListener);
		findComboListener = null;
		replaceComboListener = null;

		for(int i=0; i<listeners.length; i++) {
			findCombo.removeActionListener((ActionListener)listeners[i]);
		}
		findCombo=null;
		listeners =replaceCombo.getListeners(ActionListener.class);
		for(int i=0; i<listeners.length; i++) {
			replaceCombo.removeActionListener((ActionListener)listeners[i]);
		}
		replaceCombo = null;

		forwardRB = null;
		backwardRB = null;
		CBcaseSensitive = null;
		wrapCB = null;

		listeners = findField.getListeners(KeyListener.class);
		for(int i=0; i<listeners.length; i++) {
			findField.removeKeyListener((KeyListener)listeners[i]);
		}
		listeners = findField.getListeners(FocusListener.class);
		for(int i=0; i<listeners.length; i++) {
			findField.removeFocusListener((FocusListener)listeners[i]);
		}
		listeners = findField.getListeners(MouseListener.class);
		for(int i=0; i<listeners.length; i++) {
			findField.removeMouseListener((MouseListener)listeners[i]);
		}
		findField = null;

		listeners = replaceField.getListeners(KeyListener.class);
		for(int i=0; i<listeners.length; i++)  {
			replaceField.removeKeyListener((KeyListener)listeners[i]);
		}
		listeners = replaceField.getListeners(FocusListener.class);
		for(int i=0; i<listeners.length; i++) {
			replaceField.removeFocusListener((FocusListener)listeners[i]);
		}
		listeners = replaceField.getListeners(MouseListener.class);
		for(int i=0; i<listeners.length; i++) {
			replaceField.removeMouseListener((MouseListener)listeners[i]);
		}
		replaceField = null;

		if (actionTbl!=null){
			// deref actions here actionTbl
			for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
				EACMAction action = (EACMAction)e.nextElement();
				action.dereference();
			}
			actionTbl.clear();
			actionTbl = null;
		}

		removeAll();
		setUI(null);
		setLayout(null);
	}
	/*
    // this is used as a default search value, if there is a selection it is the starting
    // point.  'find next' will always use a selection if there is one.  'find/replace' will
    // open a dialog with the selected text in the find field
    private void setSelectedAsDefault()
    {
        // does user have a selection?
        String selText = editor.getSelectedText();
        if (selText!=null)
        {
            ComboBoxEditor cbe;
            // can't include newlines in content
            if (selText.indexOf(NEWLINE)==-1) {
                boolean addit=true;
                cbe = findCombo.getEditor();
                cbe.setItem(selText);
                // what if it is already in the list?
                for (int i=0; i<findCombo.getItemCount(); i++)
                {
                    String item = (String)findCombo.getItemAt(i);
                    if (item.equals(selText)) { // exact match
                        //return;
                        addit=false;
                        break;
                    }
                    // if case insensitive, don't add case differences to the list
                    if (!CBcaseSensitive.isSelected()&& item.equalsIgnoreCase(selText)) {
                        //return;
                        addit=false;
                        break;
                    }
                }
                if(addit){
                    findCombo.insertItemAt(selText,0);
                    findCombo.setSelectedIndex(0);
                }
            }
        }
    }
	 */
	/**
	 * create all of the actions, they are used by the buttons
	 */
	private void createActions() {
		EACMAction act = new FindAction();
		actionTbl.put(act.getActionKey(), act);
		act = new ReplaceAction();
		actionTbl.put(act.getActionKey(), act);
		act = new ReplaceNextAction();
		actionTbl.put(act.getActionKey(), act);
		act = new ReplaceAllAction();
		actionTbl.put(act.getActionKey(), act);
		act = new ResetColorAction();
		actionTbl.put(act.getActionKey(), act);
		act = new CancelAction();
		actionTbl.put(act.getActionKey(), act);
	}


	/**
	 * show the frame for find replace
	 */
	public void showFindRepFrame(){
		//make sure one isnt already open
		com.ibm.eacm.ui.FindRepFrame bmd = EACM.getEACM().getFindRepFrame();
		if (bmd==null){
			bmd = new com.ibm.eacm.ui.FindRepFrame(this);
			closeAction = bmd.getCloseAction();
			bmd.setVisible(true);
		}else{
    		if(bmd.getState()==com.ibm.eacm.ui.FindRepFrame.ICONIFIED){
    			bmd.setState(com.ibm.eacm.ui.FindRepFrame.NORMAL);
    		}
			bmd.toFront();
		}
	}
	/**
	 * find next string or show find frame
	 */
	public void findNext(){
		if (findField.getText().length()==0) {
			// show the frame
			showFindRepFrame();
		}
		else {
			findString();
		}
	}

	//======================================================================
	// actions
	private class FindAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		FindAction() {
			super(FIND_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent evt) {
			findString();
		}
	}
	// called by findAction or findNextAction
	private void findString()
	{
		String searchStr = findField.getText();
		boolean found = findable.findValue(searchStr,multColCB.isSelected(), CBcaseSensitive.isSelected(),
				forwardRB.isSelected(), wrapCB.isSelected());
		if(found){
			actionTbl.get(RESETCOLOR_ACTION).setEnabled(true);
		}else {
			Component comp = this;
			if(!comp.isShowing()){
				comp =(Component)findable;
			}
			// else put up a message dialog
			UIManager.getLookAndFeel().provideErrorFeedback(comp);

			//msg3003 = Cannot find string: {0}
			com.ibm.eacm.ui.UI.showErrorMessage(comp, Utils.getResource("msg3003",searchStr));
		}
	}

	//======================================================================
	// replace
	private class ReplaceAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		ReplaceAction() {
			super(REPLACE_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			String searchStr = findField.getText();
			String replace = replaceField.getText();
			int results = findable.replaceValue(searchStr, replace,CBcaseSensitive.isSelected());
			if (results==Findable.CELL_UNEDITABLE){
				com.ibm.eacm.ui.UI.showErrorMessage(FindRepMgr.this,Utils.getResource("msg11023.0"));
			}else if (results==Findable.ATTR_NOTREPLACEABLE){
				com.ibm.eacm.ui.UI.showErrorMessage(FindRepMgr.this,Utils.getResource("msg3005"));
			}else if (results==Findable.CELL_LOCKED){
				String lockinfo = findable.getLockInformation();
				if (lockinfo==null){
					lockinfo = "No lock information found";
				}
				com.ibm.eacm.ui.UI.showErrorMessage(FindRepMgr.this,lockinfo);
			}else if (results==Findable.NOT_FOUND){
				//msg3003 = Cannot find string: {0}
				com.ibm.eacm.ui.UI.showErrorMessage(FindRepMgr.this, Utils.getResource("msg3003",searchStr));
			}
		}
	}
	//======================================================================
	// replace
	private class ReplaceNextAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		ReplaceNextAction() {
			super(REPLACENEXT_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			String searchStr = findField.getText();
			String replace = replaceField.getText();
			int results = findable.replaceNextValue(searchStr, replace,multColCB.isSelected(),
					CBcaseSensitive.isSelected(),forwardRB.isSelected(), wrapCB.isSelected());
			if (results==Findable.CELL_UNEDITABLE){
				com.ibm.eacm.ui.UI.showErrorMessage(FindRepMgr.this,Utils.getResource("msg11023.0"));
			}else if (results==Findable.ATTR_NOTREPLACEABLE){
				com.ibm.eacm.ui.UI.showErrorMessage(FindRepMgr.this,Utils.getResource("msg3005"));
			}else if (results==Findable.CELL_LOCKED){
				String lockinfo = findable.getLockInformation();
				if (lockinfo==null){
					lockinfo = "No lock information found";
				}
				com.ibm.eacm.ui.UI.showErrorMessage(FindRepMgr.this,lockinfo);
			}else if (results==Findable.NOT_FOUND){
				//msg3003 = Cannot find string: {0}
				com.ibm.eacm.ui.UI.showErrorMessage(FindRepMgr.this, Utils.getResource("msg3003",searchStr));
			}
		}
	}
	//======================================================================
	// replace all
	private class ReplaceAllAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		ReplaceAllAction() {
			super(REPLACEALL_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			String searchStr = findField.getText();
			String replace = replaceField.getText();
			findable.replaceAllValue(searchStr, replace,multColCB.isSelected(),
					CBcaseSensitive.isSelected(),forwardRB.isSelected());
			actionTbl.get(RESETCOLOR_ACTION).setEnabled(findable.hasFound());
		}
	}
	//======================================================================
	// reset found indicator
	private class ResetColorAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		ResetColorAction() {
			super(RESETCOLOR_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			findable.resetFound();
			setEnabled(false);
			requestFocusInWindow();
		}
	}
	//======================================================================
	// cancel frame
	private class CancelAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		CancelAction() {
			super(CANCEL_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			closeAction.actionPerformed(e);
		}
	}
}
