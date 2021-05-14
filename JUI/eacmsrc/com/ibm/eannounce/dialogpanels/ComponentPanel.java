/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/07/14
 * @author Anthony C. Liberto
 *
 * $Log: ComponentPanel.java,v $
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/06/24 15:29:42  tony
 * added fill capability to featureMatrix
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/07/15 18:50:46  tony
 * enhanced program
 *
 * Revision 1.1  2003/07/14 17:41:01  tony
 * initial load
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ComponentPanel extends AccessibleDialogPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private EScrollPane jsp = null;
	private ELabel lbl = new ELabel("");


	private EPanel pnl = new EPanel(new GridLayout(1,3,3,3));

	private EButton btnOK = new EButton(getString("ok"));
	private EButton btnCancel = new EButton(getString("cncl"));
	private EButton btnReset = new EButton(getString("rstR"));

	private String tStatus = null;

	/**
     * componentPanel
     * @param _c
     * @author Anthony C. Liberto
     */
    public ComponentPanel(Component _c) {
		super (new BorderLayout());
		if (_c != null) {
			jsp = new EScrollPane(_c);
		} else {
			jsp = new EScrollPane();
		}
		init();
		return;
	}

	/**
     * componentPanel
     * @author Anthony C. Liberto
     */
    public ComponentPanel() {
		super (new BorderLayout());
		jsp = new EScrollPane();
		init();
		return;
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		pnl.add(btnOK);
		pnl.add(btnReset);
		pnl.add(btnCancel);
		btnOK.setActionCommand("ok");
		btnOK.addActionListener(this);
		btnOK.setMnemonic(getChar("ok-s"));
		btnCancel.setActionCommand("cancel");
		btnCancel.addActionListener(this);
		btnCancel.setMnemonic(getChar("cncl-s"));
		btnReset.setActionCommand("reset");
		btnReset.addActionListener(this);
		btnReset.setMnemonic(getChar("rstR-s"));

		add("North",lbl);
		add("Center",jsp);
		add("South",pnl);
		jsp.setFocusable(false);

		showButtons(false);
		setModalCursor(true);
		return;
	}

	/**
     * setComponent
     * @param _c
     * @author Anthony C. Liberto
     */
    public void setComponent(Component _c) {
		jsp.setViewportView(_c);
		return;
	}


	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		if (pnl != null) {
			pnl.removeAll();
			pnl.removeNotify();
			pnl = null;
		}

		btnOK.removeActionListener(this);
		btnCancel.removeActionListener(this);
		btnReset.removeActionListener(this);
		if (btnOK != null) {
			btnOK.dereference();
			btnOK = null;
		}

		if (btnReset != null) {
			btnReset.dereference();
			btnReset = null;
		}

		if (btnCancel != null) {
			btnCancel.dereference();
			btnCancel = null;
		}

		if (jsp != null) {
			jsp.dereference();
			jsp = null;
		}
		if (lbl != null) {
			lbl.dereference();
			lbl = null;
		}
		super.dereference();
		return;
	}

	/**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
		tStatus = _action;
		disposeDialog();
		return;
	}

	/**
     * isResizable
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isResizable() {
		return true;
	}

	/**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
		return null;
	}

	/**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {}

	/**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {}

	/**
     * initShow
     * @author Anthony C. Liberto
     */
    public void initShow() {}

/*
 feature fill

 */
	/**
     * showButtons
     * @param _b
     * @author Anthony C. Liberto
     */
    public void showButtons(boolean _b) {
		btnOK.setVisible(_b);
		btnOK.setEnabled(_b);
		btnReset.setVisible(_b);
		btnReset.setEnabled(_b);
		btnCancel.setVisible(_b);
		btnCancel.setEnabled(_b);
		pnl.setVisible(_b);
		pnl.setEnabled(_b);
		return;
	}

	/**
     * getComponent
     * @return
     * @author Anthony C. Liberto
     */
    public Component getComponent() {
		return jsp.getViewport().getView();
	}

	/**
     * setMessage
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setMessage(String _s) {
		lbl.setText(_s);
		return;
	}

	/**
     * getMessage
     * @author Anthony C. Liberto
     * @return String
     */
    public String getMessage() {
		return lbl.getText();
	}


	/**
     * updateDialog
     * @author Anthony C. Liberto
     */
    public void updateDialog() {
		packDialog();
		revalidate();
		return;
	}

	/**
     * getValue
     * @return
     * @author Anthony C. Liberto
     */
    public Object getValue() {
		if (tStatus.equals("ok")) {
			Component c = getComponent();
			if (c != null && c instanceof TableCellEditor) {
				return ((TableCellEditor)c).getCellEditorValue();
			}
			return c;
		}
		return null;
	}

	/**
     * isCancel
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCancel() {
		if (tStatus != null) {
			return tStatus.equals("cancel");
		}
		return true;
	}
}
