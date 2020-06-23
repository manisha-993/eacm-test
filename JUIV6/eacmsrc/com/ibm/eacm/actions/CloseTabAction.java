//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.Component;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.ibm.eacm.*;
import com.ibm.eacm.tabs.CloseTabComponent;
import com.ibm.eacm.tabs.TabPanel;
import com.ibm.eacm.ui.TabbedMenuPane;


/*********************************************************************
 * This is used for close current tab request
 * @author Wendy Stimpson
 */
//$Log: CloseTabAction.java,v $
//Revision 1.4  2013/08/21 00:19:41  wendy
//tab component does not check for enabled, do it in actionperformed
//
//Revision 1.3  2013/04/09 17:23:57  wendy
//pass false to enableallactions, navigate may be refreshing
//
//Revision 1.2  2013/03/14 17:35:45  wendy
//disable actions when trying to close a tab
//
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class CloseTabAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public CloseTabAction() {
		super(CLOSETAB_ACTION,KeyEvent.VK_W, Event.CTRL_MASK);
	}

	public void actionPerformed(final ActionEvent e) {
		if(!this.isEnabled()){
			// tab component is not disabled when this action is, check here
			UIManager.getLookAndFeel().provideErrorFeedback(null);
			return;
		}
		EACM.getEACM().disableAllActionsAndWait(false);
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TabbedMenuPane tabbedPane = EACM.getEACM().getTabbedPane(); 
				int selected = tabbedPane.getSelectedIndex();
				TabPanel curTab = tabbedPane.getSelectedTab();

				if(e.getSource() instanceof CloseTabComponent.TabButton){
					// user may have clicked close on a tab that was not selected
					Component tab = ((CloseTabComponent.TabButton)e.getSource()).getTabComponent();
					int i = tabbedPane.indexOfTabComponent(tab);
					if(i!=selected){
						selected = i;
						curTab = (TabPanel)tabbedPane.getComponentAt(i);
						tabbedPane.setSelectedIndex(selected);
					}
				}
				curTab.disableActionsAndWait();
				curTab.repaint();
				boolean tabClosed = EACM.getEACM().close(curTab,selected);
				if(!tabClosed){
					curTab.enableActionsAndRestore();
				}
				EACM.getEACM().enableAllActionsAndRestore(false);
			}
		});
	}
}

