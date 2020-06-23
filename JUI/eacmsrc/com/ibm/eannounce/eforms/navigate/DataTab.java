/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: DataTab.java,v $
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2005/09/20 17:48:04  tony
 * TIR USRO-R-PKUR-6GEJEY
 *
 * Revision 1.2  2005/09/12 19:03:14  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:00  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/05/16 18:22:35  tony
 * PKUR-6CCBXQ
 *
 * Revision 1.3  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 23:42:26  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2004/01/06 19:12:40  tony
 * 53500
 *
 * Revision 1.3  2003/06/12 22:23:41  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.2  2003/04/21 20:03:13  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/11 22:55:40  tony
 * adjusted classification on the toggle
 *
 * Revision 1.3  2002/11/07 16:58:29  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.eforms.table.NavTable;
import java.awt.*;
import java.awt.event.FocusListener;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DataTab extends EPanel {
	private static final long serialVersionUID = 1L;
	private EScrollPane scrollData = null;
	private EScrollPane scrollAction = null;
	private ETabbedPane eTab = new ETabbedPane();

	private NavAction action = null;
	private NavTable table = null;
	private FocusListener fl = null;

	/**
     * dataTab
     * @param _table
     * @param _name0
     * @param _action
     * @param _name1
     * @author Anthony C. Liberto
     */
    public DataTab(NavTable _table, String _name0, NavAction _action, String _name1) {
		super(new BorderLayout());
		setFocusable(false);
		table = _table;
		action = _action;
		scrollData = new EScrollPane(_table);
		eTab.add(scrollData,0);
		scrollAction = new EScrollPane((Component)_action);
		eTab.add(scrollAction, 1);
		eTab.setTitleAt(0,_name0);
		eTab.setTitleAt(1,_name1);
		add("Center",eTab);
		return;
	}

	/**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public NavTable getTable() {
		return table;
	}

	/**
     * getNavAction
     * @return
     * @author Anthony C. Liberto
     */
    public NavAction getNavAction() {
		return action;
	}

	/**
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     * @author Anthony C. Liberto
     */
    public void addFocusListener(FocusListener _fl) {
		fl = _fl;
		if (eTab != null) {
            eTab.addFocusListener(fl);
		}
		if (scrollData != null) {
            scrollData.addFocusListener(fl);
		}
		if (scrollAction != null) {
            scrollAction.addFocusListener(fl);
		}
		if (action != null) {
            action.addFocusListener(fl);
		}
		if (table != null) {
            table.addFocusListener(fl);
		}
		return;
	}

	/**
     * removeFocusListener
     * @author Anthony C. Liberto
     */
    public void removeFocusListener() {
		if (eTab != null) {
            eTab.removeFocusListener(fl);
		}
		if (scrollData != null) {
            scrollData.removeFocusListener(fl);
		}
		if (scrollAction != null) {
            scrollAction.removeFocusListener(fl);
		}
		if (action != null) {
            action.removeFocusListener(fl);
		}
		if (table != null) {
            table.removeFocusListener(fl);
		}
		fl = null;
		return;
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		removeFocusListener();
		if (scrollData != null) {
			scrollData.dereference();
			scrollData = null;
		}
		if (scrollAction != null) {
			scrollAction.dereference();
			scrollAction = null;
		}
		eTab.removeAll();
		eTab.removeNotify();
		action = null;
		table = null;
		removeAll();
		removeNotify();
		return;
	}
/*
 1.2h
*/
	/**
     * setSelectedIndex
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setSelectedIndex(int _i) {
		if (eTab != null) {
			eTab.setSelectedIndex(_i);
		}
		return;
	}
/*
 53500
 */
    /**
     * dataTab
     * @param _name0
     * @param _name1
     * @author Anthony C. Liberto
     */
    public DataTab(String _name0, String _name1) {
		return;
	}

/*
PKUR-6CCBXQ
    public void requestFocus() {
		if (table != null) {
			table.requestFocus();
		} else {
			Container parent = getParent();
			if (parent != null) {
				parent.requestFocus();
			}
		}
		return;
	}
*/
    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     * PKUR-6CCBXQ
     */
    public void requestFocus() {
		if (eTab != null) {
			int indx = eTab.getSelectedIndex();							//TIR USRO-R-PKUR-6GEJEY
//TIR USRO-R-PKUR-6GEJEY			Component c = eTab.getComponentAt(eTab.getSelectedIndex());
			if (indx < 0) {												//TIR USRO-R-PKUR-6GEJEY
				Container parent = getParent();							//TIR USRO-R-PKUR-6GEJEY
				if (parent != null) {									//TIR USRO-R-PKUR-6GEJEY
					parent.requestFocus();								//TIR USRO-R-PKUR-6GEJEY
				}														//TIR USRO-R-PKUR-6GEJEY
				return;													//TIR USRO-R-PKUR-6GEJEY
			}															//TIR USRO-R-PKUR-6GEJEY
			Component c = eTab.getComponentAt(indx);					//TIR USRO-R-PKUR-6GEJEY
			if (c != null) {
				c.requestFocus();
			} else {
				Container parent = getParent();
				if (parent != null) {
					parent.requestFocus();
				}
			}
		} else {
			Container parent = getParent();
			if (parent != null) {
				parent.requestFocus();
			}
		}
		return;
	}
}
