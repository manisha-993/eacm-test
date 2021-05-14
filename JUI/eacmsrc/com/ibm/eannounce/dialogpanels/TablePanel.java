/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: TablePanel.java,v $
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:25  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:29  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2003/12/18 16:48:14  tony
 * cleaned up code for future modification.
 *
 * Revision 1.10  2003/12/04 23:17:38  tony
 * accessibility
 *
 * Revision 1.9  2003/06/13 17:32:51  tony
 * 51255 resize on refresh issue.
 *
 * Revision 1.8  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.7  2003/03/29 00:06:27  tony
 * added remove Menu Logic
 *
 * Revision 1.6  2003/03/25 23:29:05  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.5  2003/03/20 23:59:36  tony
 * column order moved to preferences.
 * preferences refined.
 * Change History updated.
 * Default Column Order Stubs added
 *
 * Revision 1.4  2003/03/13 18:38:44  tony
 * accessibility and column Order.
 *
 * Revision 1.3  2003/03/12 23:51:11  tony
 * accessibility and column order
 *
 * Revision 1.2  2003/03/05 19:16:48  tony
 * AttributeChangeHistoryGroup addition.
 *
 * Revision 1.1  2003/03/04 22:36:43  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:44  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.EScrollPane;
import com.ibm.eannounce.eforms.table.HistTable;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class TablePanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	private EButton btnOK = new EButton(getString("ok"));

	private EPanel pButton = new EPanel(new GridLayout(1,1));
	/**
     * jsp
     */
    protected EScrollPane jsp = new EScrollPane();
	private HistTable nTable = null;
//	private Rectangle defRect = new Rectangle(0,0,1,1);

	/**
     * tablePanel
     * @author Anthony C. Liberto
     */
    public TablePanel() {
		super (new BorderLayout());
		jsp.setFocusable(false);
		init();
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
     * setTable
     * @param _group
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(EntityChangeHistoryGroup _group, RowSelectableTable _table) {
		if (nTable == null) {
			nTable = new HistTable(_group,_table);
			jsp.setViewportView(nTable);
		} else {
			nTable.setObject(_group);
			nTable.updateModel(_table);
		}
		nTable.refreshTable(true);
		prepareTable(nTable);
		return;
	}

	/**
     * setTable
     * @param _group
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(AttributeChangeHistoryGroup _group, RowSelectableTable _table) {
		if (nTable == null) {
			nTable = new HistTable(_group,_table);
			jsp.setViewportView(nTable);
		} else {
			nTable.setObject(_group);
			nTable.updateModel(_table);
		}
		nTable.refreshTable(true);
		prepareTable(nTable);
		return;
	}

	private void prepareTable(HistTable _table) {
		if (_table != null) {
			if (_table.getRowCount() > 0) {
				_table.setRowSelectionInterval(0,0);
			}
			if (_table.getColumnCount() > 0) {
				_table.setColumnSelectionInterval(0,0);
			}
			_table.requestFocus();
			_table.validate();
		}
		return;
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        Dimension d = new Dimension(600,400);
        pButton.add(btnOK);
		btnOK.addActionListener(this);
		add("Center",jsp);
		add("South", pButton);
		setModalCursor(true);
		btnOK.setMnemonic(getChar("ok-s"));
		jsp.setPreferredSize(d);
		jsp.setSize(d);
		return;
	}

	/**
     * initShow
     * @author Anthony C. Liberto
     */
    public void initShow() {
		return;
	}

	/**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
		disposeDialog();
		return;
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
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
		return nTable.getTitle();
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
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		if (jsp != null) {
			jsp.dereference();
			jsp = null;
		}
		return;
	}
}
