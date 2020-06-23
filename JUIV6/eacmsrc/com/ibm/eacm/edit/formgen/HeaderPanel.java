//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.formgen;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * this is the collapsible header and button on a formpanel
 * @author Wendy Stimpson
 */
//$Log: HeaderPanel.java,v $
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public class HeaderPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean canCollapse = true;
	private boolean isCollapsed = false;
	private NLSLabel title = new NLSLabel();
	private BasicArrowButton btnCollapsed = new BasicArrowButton(JButton.EAST);
	private BasicArrowButton btnExpanded = new BasicArrowButton(JButton.SOUTH);

	/**
     * headerPanel
     */
	protected HeaderPanel(ActionListener al) {
		super(new BorderLayout(5,5));
		adjustButton();
		btnCollapsed.setActionCommand("collapse");
		btnExpanded.setActionCommand("expand");
		add(title,BorderLayout.CENTER);
		setOpaque(false);
		
		btnCollapsed.addActionListener(al);
		btnExpanded.addActionListener(al);
	}

	/**
     * add a Title for specified nls
     * @param s
     * @param nls
     */
    protected void addTitle(String s, int nls) {
		title.addTitle(s,nls);
	}

	/**
     * set title for NLS
     * @param _nls
     */
    protected void setNLSTitle(int _nls) {
		title.setNLSTitle(_nls);
	}

	/**
     * setTitleColor
     * @param c
     */
    protected void setTitleColor(Color c) {
		title.setForeground(c);
	}

	/**
     * setCollapsible
     * @param b
     */
    protected void setCollapsible(boolean b) {
		canCollapse = b;
		btnCollapsed.setEnabled(b);
		btnExpanded.setEnabled(b);
	}

	/**
     * setCollapsed
     * @param b
     */
    protected void setCollapsed(boolean b) {
		if (canCollapse) {
			isCollapsed = b;
			adjustButton();
			validate();									
			repaint();
		}
	}

	/**
     * adjustButton
     */
    private void adjustButton() {
		if (isCollapsed) {
			remove(btnExpanded);
			add(btnCollapsed,BorderLayout.WEST);
		} else {
			remove(btnCollapsed);
			add(btnExpanded,BorderLayout.WEST);
        }
	}

    /**
     * release memory
     */
    protected void dereference(ActionListener al) {
		title.dereference();
		title = null;
		
		btnCollapsed.removeActionListener(al);
		btnExpanded.removeActionListener(al);
		
		btnCollapsed.removeAll();
		btnCollapsed.setUI(null);
		btnCollapsed = null;
		btnExpanded.removeAll();
		btnExpanded.setUI(null);
		btnExpanded = null;
	}
}
