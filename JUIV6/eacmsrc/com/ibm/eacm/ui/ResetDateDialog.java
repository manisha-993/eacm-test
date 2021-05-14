//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.editor.*;
import com.ibm.eacm.objects.DateRoutines;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.tabs.TabPanel;


/******************************************************************************
* This is used to display the reset date dialog for dialing back the profile
* @author Wendy Stimpson
*/
//$Log: ResetDateDialog.java,v $
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//

public class ResetDateDialog extends EACMDialog implements EACMGlobals, KeyListener
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.1 $";

    private TimeEditor timeEditor = null;
    private DateEditor dateEditor = null;
    private DateSelector dateSelector =null;
	private TabPanel currentTab = null;

	private JPanel datePanel = new JPanel(null);

	private JButton m_btnOk = null;
	private JButton m_btnCancel = null;


	/**
	 */
	public ResetDateDialog(TabPanel t)  {
		super(null,"date.panel",JDialog.ModalityType.APPLICATION_MODAL);

		currentTab = t;
		setIconImage(null);
		init();

		finishSetup(EACM.getEACM());
		setSize(getPreferredSize());
	}

	/**
	 * init dialog components
	 */
	private void init() {
		createActions();
		dateEditor = new DateEditor(DateEditor.DateType.PAST_DATE,currentTab.getProfile());
		timeEditor = new TimeEditor(currentTab.getProfile());
		dateSelector = new DateSelector(DateEditor.DateType.PAST_DATE);

		dateSelector.setEditor(dateEditor);
		dateEditor.setCaretPosition(0);

	    JPanel dtPanel = new JPanel(new BorderLayout());
	    dtPanel.add(dateEditor,BorderLayout.WEST);
	    dtPanel.add(timeEditor,BorderLayout.EAST);

		JPanel m_pBtn = new JPanel(new GridLayout(1, 2, 10, 10));

		m_btnOk = new JButton(getAction(OK_ACTION));
		m_btnOk.setMnemonic((char)((Integer)m_btnOk.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnOk);

		m_btnCancel = new JButton(getAction(CANCEL_ACTION)){
			private static final long serialVersionUID = 1L;
			public void requestFocus(){
				dateEditor.setInputVerifier(null);
				super.requestFocus();
			}
		};
		m_btnCancel.setMnemonic((char)((Integer)m_btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnCancel);

		GroupLayout layout = new GroupLayout(datePanel);
		datePanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(dtPanel);
		leftToRight.addComponent(dateSelector);
		leftToRight.addComponent(m_pBtn);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(dtPanel);
		topToBottom.addComponent(dateSelector);
		topToBottom.addComponent(m_pBtn);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

		getContentPane().add(datePanel);
		getRootPane().setDefaultButton(m_btnOk);

		dateEditor.addKeyListener(this);
	}
    public void keyTyped(KeyEvent _ke) {}
    public void keyReleased(KeyEvent _ke) {}

	/**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     *
     */
    public void keyPressed(KeyEvent _ke) {
		if (_ke.getKeyCode() == KeyEvent.VK_ENTER) {
			if(dateEditor.isValidDate()){
				m_btnOk.doClick();
			}
			_ke.consume();//prevent dialog from getting this
		}
	}
	/******
	 * release memory
	 */
	public void dereference(){
		super.dereference(); // this derefs the actiontbl

		m_btnOk.setAction(null);
		m_btnOk.setUI(null);
		m_btnOk = null;
		m_btnCancel.setAction(null);
		m_btnCancel.setUI(null);
		m_btnCancel = null;

		dateSelector.dereference();
		dateSelector = null;

		dateEditor.removeKeyListener(this);
		dateEditor.dereference();
		dateEditor = null;
		timeEditor.dereference();
		timeEditor = null;

		datePanel.removeAll();
		datePanel.setUI(null);
		datePanel.setLayout(null);
		datePanel = null;
		
		currentTab = null;
	}

	/**
	 * create all of the actions
	 */
	private void createActions() {
		EACMAction act = new OkAction();
		addAction( act);
		act = new CancelAction();
		addAction( act);
	}

	private class OkAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		OkAction() {
			super(OK_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			boolean wasPast = currentTab.isDateDialedBack(); // profile already dialed back in time
			String td = dateEditor.getDate()+"-"+timeEditor.getFullTime();
			if (!wasPast && (td.equals(DateRoutines.getEOD()))){// date didnt change
			}else{
				currentTab.setProcessTime(td);
			}

			closeAction.actionPerformed(null); // close the dialog
		}
	}

	private class CancelAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		CancelAction() {
			super(CANCEL_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			closeAction.actionPerformed(e);
		}
	}
}