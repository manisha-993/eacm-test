//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eannounce.eserver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

/**
 * it is part of eaServer.jar and executes on the client
 * This is the frame used to display the progress and status of installation of updated software (jars and files)
 */
//$Log: UpdateStatus.java,v $
//Revision 1.2  2012/11/12 22:48:07  wendy
//AutoUpdate changes
//
//Revision 1.1  2012/09/27 19:39:24  wendy
//Initial code
//
public class UpdateStatus extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final String RETURN = System.getProperty("line.separator");
	private JPanel pnlMain = new JPanel(null);
	private JPanel pnlButton = new JPanel(new BorderLayout());
	private JTextArea jtaDisplay = new JTextArea();
	private JScrollPane scroll = new JScrollPane(jtaDisplay);
	private JProgressBar progress = new JProgressBar();
	private JButton button = null;
	private int iCount = 0;

	/**
	 * Constructor
	 */
	protected UpdateStatus(Updater ctrl) {
		super("EACM update");
		progress.setStringPainted(true);
		jtaDisplay.setEditable(false);

		init(ctrl);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private void init(Updater ctrl) {
		Dimension dMin = new Dimension(300, 150);
		scroll.setSize(dMin);
		scroll.setPreferredSize(dMin);
		scroll.setMinimumSize(dMin);

		UpdateStatusAction usa = new UpdateStatusAction(ctrl);
		button = new JButton(usa);

		addWindowListener(usa);

		// allow esc to get to the container
		jtaDisplay.getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "none");

        // allow escape to close dialog
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", usa);

        //this is needed or the mnemonic doesnt activate
        button.setMnemonic((char)((Integer)usa.getValue(Action.MNEMONIC_KEY)).intValue());

		jtaDisplay.append("Update Status..." + RETURN);

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		pnlButton.add(progress,BorderLayout.NORTH);
		pnlButton.add(button,BorderLayout.SOUTH);
		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(scroll);
		leftToRight.addComponent(pnlButton);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(scroll);
		topToBottom.addComponent(pnlButton,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE); // prevent vertical growth

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

		getContentPane().add(pnlMain);

		pack();
		setSize(getPreferredSize());

		//center the frame
		Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(((sSize.width - getWidth()) / 2), ((sSize.height - getHeight()) / 2));
	}

	protected void dereference(){
		removeWindowListener((WindowListener)button.getAction());

		UpdateStatusAction sa  = (UpdateStatusAction)button.getAction();
		sa.dereference();
		button.setAction(null);
		button.setUI(null);

		pnlMain.removeAll();
		pnlMain.setUI(null);
		pnlMain = null;

		scroll.removeAll();
		scroll.setUI(null);
		scroll = null;

		removeAll();
	}

	/**
	 * update is complete, enable the action to close the frame
	 */
	protected void setComplete() {
		button.getAction().setEnabled(true);
	}

    /**
	 * add message to textarea
	 * @param s
	 */
	protected void setMessage(String s) {
		jtaDisplay.append(s+RETURN);
		scrollToEnd();
	}

	/**
	 * set progress indicator
	 */
	protected void setIncrement(){
		progress.setValue(iCount++);
	}

	/**
	 * get information generated during update
	 */
	protected String getUpdateInformation() {
		return jtaDisplay.getText();
	}

	/**
	 * setMax
	 * @param max
	 */
	protected void setMax(int max) {
		progress.setMaximum(max);
		progress.setMinimum(0);
		iCount = 0;
	}

	/**
	 * scrollToEnd
	 */
	private void scrollToEnd() {
		JViewport view = scroll.getViewport();
		if (view != null) {
			Dimension d = jtaDisplay.getSize();
			view.setViewPosition(new Point(0, d.height));
		}
		jtaDisplay.revalidate();
	}

	private class UpdateStatusAction extends AbstractAction implements WindowListener
	{
		private static final long serialVersionUID = 1L;
		private Updater appController;
		UpdateStatusAction(Updater ctrl) {
			super("OK");

			putValue(Action.SHORT_DESCRIPTION, "restart application when installation is complete");
			putValue(Action.MNEMONIC_KEY, new Integer('o'));
			appController = ctrl;

			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			// the close operation was overridden, hide here
			UpdateStatus.this.setVisible(false);
			UpdateStatus.this.dispose();

			appController.restartApplication();

			UpdateStatus.this.dereference();
		}

		void dereference(){
			appController = null;
			putValue(Action.NAME, null);
			putValue(Action.SHORT_DESCRIPTION, null);
			putValue(Action.MNEMONIC_KEY,null);
		}

		/**
		 * Invoked when the user attempts to close the window
		 * from the window's system menu.
		 */
		public void windowClosing(WindowEvent e) {
			if(this.isEnabled()){
				actionPerformed(null);
			}else{
				UIManager.getLookAndFeel().provideErrorFeedback(null);
			}
		}

		/**
		 * Invoked when a window has been closed as the result
		 * of calling dispose on the window.
		 */
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
	}
}
