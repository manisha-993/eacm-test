//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;


import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;
/*********************************************************************
 * Column header with buttons for the multi flag editor
 * @author Wendy Stimpson
 */
//$Log: MultiColHeader.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class MultiColHeader extends JPanel implements EACMGlobals {
	private static final long serialVersionUID = 1L;

	private JButton cancelButton = null;
	private JButton acceptButton = null;
	private JLabel descriptionLbl = new JLabel("flag description here");
	private JLabel typeAheadLbl = new JLabel("");

	private JPanel btnPanel = new JPanel(new BorderLayout());
	private MouseListener ml = null;
	
	/**
	 * constructor
	 */
	protected MultiColHeader(Action cnclAct, Action acceptAct) {
		super(new BorderLayout());
		cancelButton = new JButton(cnclAct);
		acceptButton = new JButton(acceptAct);
		init();

	}

	/* (non-Javadoc) 
	 * @see javax.swing.JComponent#requestFocusInWindow()
	 */
	public boolean requestFocusInWindow() {
		return acceptButton.requestFocusInWindow();
	}

	/*
	 * assembles the panel
	 */
	private void init() {
		descriptionLbl.setBorder(UIManager.getBorder(RAISED_BORDER_KEY));

		btnPanel.add(acceptButton,BorderLayout.EAST);
		btnPanel.add(cancelButton,BorderLayout.WEST);

		btnPanel.add(descriptionLbl,BorderLayout.CENTER);
		add(typeAheadLbl,BorderLayout.SOUTH); 
		typeAheadLbl.setVisible(Utils.isArmed(ENHANCED_FLAG_EDIT));
		add(btnPanel,BorderLayout.NORTH);

		Dimension descDim = descriptionLbl.getPreferredSize();
		Dimension forcedDim = new Dimension(descDim.height, descDim.height);
		cancelButton.setSize(forcedDim);
		cancelButton.setPreferredSize(forcedDim);
		cancelButton.setHideActionText(true);
		acceptButton.setSize(forcedDim);
		acceptButton.setPreferredSize(forcedDim);
		acceptButton.setHideActionText(true);
		
		setRequestFocusEnabled(false);
		setFocusable(false);
		
		acceptButton.setRequestFocusEnabled(false);
		cancelButton.setRequestFocusEnabled(false);

		ml = new MouseAdapter(){
		    /**
		     * MouseListener interface, need to capture events on description label and accept changes
		     */
		    public void mouseClicked(MouseEvent _me) { 
		    	acceptButton.doClick();
		    }
		};
		descriptionLbl.addMouseListener(ml);
	}

	/**
	 * adjust the label to the flag description
	 * @param _s
	 */
	protected void setDescription(String _s) {
		descriptionLbl.setText(_s);
	}

	/**
	 * release memory
	 */
	protected void dereference() {
		acceptButton.removeAll();
		acceptButton.setAction(null); 
		acceptButton.setUI(null);
		acceptButton = null;

		cancelButton.setAction(null); 
		cancelButton.setUI(null);
		cancelButton.removeAll();
		cancelButton = null;

		descriptionLbl.removeMouseListener(ml);
		descriptionLbl.removeAll();
		descriptionLbl.setUI(null);
		descriptionLbl = null;
		
		typeAheadLbl.removeAll();
		typeAheadLbl.setUI(null);
		typeAheadLbl = null;
		
		ml = null;

		btnPanel.removeAll();
		btnPanel.setUI(null);
		btnPanel = null;
		
		removeAll();
		setUI(null);
	}

	/**
	 * set enhancedflag type ahead guess
	 *
	 * @param _s
	 */
	protected void setLabelText(String _s) {
		typeAheadLbl.setText(_s);
	}
	protected String getLabelText() {
		return typeAheadLbl.getText();
	}
}
