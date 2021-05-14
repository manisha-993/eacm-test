//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eannounce.eforms.table;

import javax.swing.UIManager;

import COM.ibm.eannounce.objects.RowSelectableTable;
import com.ibm.eannounce.eforms.action.ActionController;
import com.ibm.eannounce.erend.ERend;

/**********************************************************************************
 * This class is used for the QueryAction to display a view
 *
 */
//$Log: QueryTable.java,v $
//Revision 1.1  2008/08/04 14:10:14  wendy
//CQ00006067-WI : LA CTO - Added support for QueryAction
//
public class QueryTable extends NavTable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param _o
	 * @param _table
	 * @param _ac
	 */
	public QueryTable(Object _o, RowSelectableTable _table,	ActionController _ac) {
		super(_o, _table, _ac, true);
	}
	/**
     * defaultSelect
     */
    public void defaultSelect() {
        if (getColumnCount() > 0) {
			setColumnSelectionInterval(0,0);
		}
		if (getRowCount() > 0) {
			setRowSelectionInterval(0,0);
		}
	}
    /**
     * init
     */
    public void init() {
		setRowMargin(0);
		initAccessibility("accessible.query");
		setColumnSelectionAllowed(false);
		setAutoResizeMode(AUTO_RESIZE_OFF);
		setDefaultRenderer(Object.class, new ERend());
		resizeCells();
		setBorder(UIManager.getBorder("eannounce.focusBorder"));
	}   
     
    /**
     * getUIPrefKey
     */
    public String getUIPrefKey() {	
		return "QUERY";
	}    
}
