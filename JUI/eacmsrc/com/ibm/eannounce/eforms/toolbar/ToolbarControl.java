/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ToolbarControl.java,v $
 * Revision 1.2  2008/01/30 16:27:04  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:15  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/03 16:38:54  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.4  2005/01/31 20:47:49  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 22:17:58  tony
 * JTest Modifications
 *
 * Revision 1.2  2004/09/03 20:26:18  tony
 * accessibility
 *
 * Revision 1.1.1.1  2004/02/10 16:59:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/05/06 00:07:19  tony
 * 50468
 *
 * Revision 1.2  2003/03/04 22:34:52  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/11/07 16:58:37  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import com.elogin.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.GridLayout;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ToolbarControl extends EPanel implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;
	private ToolbarList curList = null;
	private ToolbarList availList = null;

	private EButton btnUp = new EButton("up");
	private EButton btnDown = new EButton("down");
	private EButton btnRemove = new EButton("remove");
	private EButton btnInsert = new EButton("insert");

	/**
     * toolbarControl
     * @param _availList
     * @param _curList
     * @author Anthony C. Liberto
     */
    public ToolbarControl(ToolbarList _availList, ToolbarList _curList) {
		super(new GridLayout(4,1,5,5));
		availList = _availList;
		curList = _curList;

		curList.addFocusListener(this);
		availList.addFocusListener(this);
		init();
		return;
	}

	/**
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusGained(FocusEvent _fe) {
		Object source = _fe.getSource();
		btnUp.setEnabled(source == curList);
		btnDown.setEnabled(source == curList);
		btnRemove.setEnabled(source == curList);
		btnInsert.setEnabled(source == availList);
		return;
	}

	/**
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusLost(FocusEvent _fe) {}

	private void init() {
		btnUp.addActionListener(this);
		btnDown.addActionListener(this);
		btnRemove.addActionListener(this);
		btnInsert.addActionListener(this);

		btnUp.setFocusable(false);
		btnDown.setFocusable(false);
		btnRemove.setFocusable(false);
		btnInsert.setFocusable(false);

		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
		btnRemove.setEnabled(false);
		btnInsert.setEnabled(false);

		btnUp.setMnemonic('u');
		btnDown.setMnemonic('w');
		btnRemove.setMnemonic('e');
		btnInsert.setMnemonic('i');

		add(btnInsert);
		add(btnRemove);
		add(btnUp);
		add(btnDown);
		return;
	}

	/**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _ae
     */
    public void actionPerformed(ActionEvent _ae) {
		Object source = _ae.getSource();
		if (source == btnUp) {
			curList.moveItem(curList.getSelectedIndex(), -1);
		} else if (source == btnDown) {
			curList.moveItem(curList.getSelectedIndex(), 1);
		} else if (source == btnRemove) {
			curList.remove(curList.getSelectedIndex());
		} else if (source == btnInsert) {
			ToolbarItem item = availList.getToolbarItem(availList.getSelectedIndex());
			curList.add(new ToolbarItem(item));
		}
		return;
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		btnUp.removeActionListener(this);
		btnDown.removeActionListener(this);
		btnRemove.removeActionListener(this);
		btnInsert.removeActionListener(this);

		curList.removeFocusListener(this);
		availList.removeFocusListener(this);

		super.dereference();
		return;
	}

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
		return TYPE_TOOLBARCONTROL;
	}

    /**
     * setUseDefined
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseDefined(boolean _b) {
		btnUp.setUseDefined(_b);
		btnDown.setUseDefined(_b);
		btnRemove.setUseDefined(_b);
		btnInsert.setUseDefined(_b);
		return;
	}
}
