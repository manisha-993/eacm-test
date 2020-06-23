/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: MultiColHeader.java,v $
 * Revision 1.2  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:13  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:58  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/05/25 20:59:57  tony
 * multiple flag enhancement
 *
 * Revision 1.4  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.3  2005/02/01 20:48:31  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:17  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/10/10 20:10:24  tony
 * 52499
 *
 * Revision 1.3  2003/10/08 22:19:08  tony
 * fixed null pointer.
 *
 * Revision 1.2  2003/03/04 22:34:51  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MultiColHeader extends EPanel {
	private static final long serialVersionUID = 1L;
	private EArrowButton arrow = new EArrowButton(BasicArrowButton.SOUTH,true);

	private JButton cancel = new ECancelButton();
	private ELabel description = new ELabel("flag description here");
	private ELabel lbl = new ELabel("");
	private Dimension buttonSize = new Dimension(25,25);

	private EPanel pnlCenter = new EPanel(new BorderLayout());

	private int minWidth = -1;

//	private boolean bFocusable = true;			//22616

	/**
     * multiColHeader
     * @author Anthony C. Liberto
     */
    public MultiColHeader() {
		this(0);
	}

	/**
     * multiColHeader
     * @param _i
     * @author Anthony C. Liberto
     */
    public MultiColHeader(int _i) {
		super(new BorderLayout());
		init();
		setMinimumWidth(_i);
		setFocusable(false);
		return;
	}

	/**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
		arrow.requestFocus();
	}

	/**
     * @see javax.swing.JComponent#grabFocus()
     * @author Anthony C. Liberto
     */
    public void grabFocus() {
		arrow.grabFocus();
	}

	/**
     * setHorizontalBounds
     * @param _rect
     * @author Anthony C. Liberto
     */
    public void setHorizontalBounds(Rectangle _rect) {
		Dimension d = new Dimension(_rect.width, _rect.height);
		setSize(d);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		return;
	}

	/**
     * setMinimumWidth
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setMinimumWidth(int _i) {
		minWidth = _i;
	}

	/**
     * getMinimumWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getMinimumWidth() {
		return minWidth;
	}

	/**
     * getPreferredWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getPreferredWidth() {
		Dimension d = getPreferredSize();
		return Math.max(d.width,minWidth);
	}

/*
 * assembles the panel
 */
	private void init() {
		arrow.setActionCommand("arrow");
		cancel.setActionCommand("cancel");

		description.setBorder(UIManager.getBorder("eannounce.raisedBorder"));

		add("East", arrow);
		add("West", cancel);
//		add("Center", description);

		pnlCenter.add("North",description);
		pnlCenter.add("South",lbl);
		lbl.setVisible(EAccess.isArmed(ENHANCED_FLAG_EDIT));
		add("Center",pnlCenter);



		cancel.setSize(buttonSize);
		cancel.setPreferredSize(buttonSize);
		cancel.setMinimumSize(buttonSize);

		setRequestFocusEnabled(false);
		arrow.setRequestFocusEnabled(false);
		cancel.setRequestFocusEnabled(false);
		return;
	}

/*
 * quick and easy method to retrieve the
 * actionCommand from a component.
 * Needed because of dependence on
 * MouseListener instead of ActionListener
 */
	/**
     * getActionCommand
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public String getActionCommand(Component _c) {
		if (_c == arrow || _c == description) {
			return arrow.getActionCommand();

		} else if (_c == cancel) {
			return cancel.getActionCommand();
		}
		return "";
	}

/*
 * mouse listener for the buttons.  Need to
 * use mouse listener instead of actionlistener
 * to allow for toggling of the popup.
 */
	/**
     * @see java.awt.Component#addMouseListener(java.awt.event.MouseListener)
     * @author Anthony C. Liberto
     */
    public void addMouseListener(MouseListener _ml) {
		arrow.addMouseListener(_ml);
		cancel.addMouseListener(_ml);
		description.addMouseListener(_ml);
		return;
	}

	/**
     * @see java.awt.Component#removeMouseListener(java.awt.event.MouseListener)
     * @author Anthony C. Liberto
     */
    public void removeMouseListener(MouseListener _ml) {
		if (arrow != null) {
            arrow.removeMouseListener(_ml);
		}
		if (cancel != null) {
            cancel.removeMouseListener(_ml);
		}
		if (description != null) {
            description.removeMouseListener(_ml);
		}
		return;
	}

	/**
     * @see java.awt.Component#addKeyListener(java.awt.event.KeyListener)
     * @author Anthony C. Liberto
     */
    public void addKeyListener(KeyListener _kl) {
		arrow.addKeyListener(_kl);
	}

	/**
     * @see java.awt.Component#removeKeyListener(java.awt.event.KeyListener)
     * @author Anthony C. Liberto
     */
    public void removeKeyListener(KeyListener _kl) {
		arrow.removeKeyListener(_kl);
	}

	/**
     * addListener
     * @param _fl
     * @author Anthony C. Liberto
     */
    public void addListener(FocusListener _fl) {			//acl_20021023
		arrow.addFocusListener(_fl);						//acl_20021023
		cancel.addFocusListener(_fl);						//acl_20021023
		return;												//acl_20021023
	}														//acl_20021023

	/**
     * removeListener
     * @param _fl
     * @author Anthony C. Liberto
     */
    public void removeListener(FocusListener _fl) {			//acl_20021023
		arrow.removeFocusListener(_fl);						//acl_20021023
		cancel.removeFocusListener(_fl);					//acl_20021023
		return;												//acl_20021023
	}														//acl_20021023

	/**
     * setButtonsVisible
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setButtonsVisible(boolean _b) {
		arrow.setVisible(_b);
		arrow.setEnabled(_b);								//52499
		cancel.setVisible(_b);
		cancel.setEnabled(_b);								//52499
		return;
	}

/*
 * adjust the label to the flag description
 */
	/**
     * setDescription
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setDescription(String _s) {
		description.setText(_s);
	}

	/**
     * getDescription
     * @return
     * @author Anthony C. Liberto
     */
    public String getDescription() {
		return description.getText();
	}

/*
 * utility method to remove the component
 * from memory
 */
    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		arrow.removeAll();
		arrow = null;

		cancel.removeAll();
		cancel = null;

		description.removeAll();
		description = null;

		removeAll();
		return;
	}

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
		return TYPE_MULTICOLHEADER;
	}

    /**
     * setLabelText
     *
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setLabelText(String _s) {
		lbl.setText(_s);
		return;
	}
}
