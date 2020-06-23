//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.ui;

import com.ibm.eacm.objects.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

/*********************************************************************
 * This is used for displaying an indeterminate progress bar while software is downloading
 * @author Wendy Stimpson
 */
//$Log: SWProgressDialog.java,v $
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//
public class SWProgressDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel pMain = new JPanel(new BorderLayout());
	private JPanel pTask = new JPanel(new BorderLayout());
	//download = Downloading Update File...
	private JLabel lTask = new JLabel(Utils.getResource("download"));
	private JProgressBar pbTask = new JProgressBar();

	/**
	 * Constructor
	 * @param _frame
	 * @param _title
	 */
	public SWProgressDialog(JFrame _frame, String _title) {
		super(_frame, _title);//,JDialog.ModalityType.APPLICATION_MODAL); cant do this, it blocks code
		init();
		setResizable(false);
	}

	private void init() {
		pTask.add(lTask,BorderLayout.NORTH);
		pTask.add(pbTask,BorderLayout.SOUTH);

		pMain.add(pTask,BorderLayout.NORTH);
		getContentPane().add(pMain,BorderLayout.CENTER);
		pbTask.setIndeterminate(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		// allow escape to close dialog to be accessible
		Action actionListener = new AbstractAction() { 
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent actionEvent) { 
				setVisible(false);
				dispose();
			} 
		} ;

		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "ESCAPE");
		getRootPane().getActionMap().put("ESCAPE", actionListener);

		pack();

		setLocationRelativeTo(getOwner());
	}
	public void setText(String txt){
		lTask.setText(txt);
		this.pack();
	}
}
