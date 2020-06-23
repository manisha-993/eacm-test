/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: HistTable.java,v $
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:04  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.3  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:45  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/12/01 17:43:07  tony
 * 52918
 *
 * Revision 1.8  2003/11/25 22:05:07  tony
 * accessibility enhancement.
 *
 * Revision 1.7  2003/10/29 16:47:39  tony
 * 52728
 *
 * Revision 1.6  2003/08/13 16:39:57  joan
 * 51710
 *
 * Revision 1.5  2003/06/13 17:32:52  tony
 * 51255 resize on refresh issue.
 *
 * Revision 1.4  2003/06/11 18:08:51  tony
 * 51269
 *
 * Revision 1.3  2003/04/11 20:02:31  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.table;
import COM.ibm.eannounce.objects.*;
import com.ibm.eannounce.erend.ERend;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class HistTable extends NavTable {
	private static final long serialVersionUID = 1L;
	/**
     * histTable
     * @param _o
     * @param _table
     * @author Anthony C. Liberto
     */
    public HistTable(Object _o, RowSelectableTable _table) {
		super(_o,_table);
//access		setRowSelectionAllowed(false);				//51269
//access		setColumnSelectionAllowed(false);			//51269
		sort(2, true);								//51710
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		//52918
		return;
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		setRowMargin(0);
		initAccessibility("accessible.histTable");
		setColumnSelectionAllowed(false);
		setAutoResizeMode(AUTO_RESIZE_OFF);
		setDefaultRenderer(Object.class, new ERend());
		resizeCells();
		setBorder(UIManager.getBorder("eannounce.focusBorder"));
		if (getRowCount() > 0) {
			setRowSelectionInterval(0,0);
		}
		if (getColumnCount() > 0) {
			setColumnSelectionInterval(0,0);
		}
		return;
	}

	/**
     * @see javax.swing.JTable#createDefaultColumnsFromModel()
     * @author Anthony C. Liberto
     */
    public void createDefaultColumnsFromModel() {
		if (cgtm != null) {
			int ii = cgtm.getColumnCount();
			int rows = cgtm.getRowCount();
			RSTableColumnModel cm = (RSTableColumnModel)getColumnModel();
			removeColumns(cm);
			for (int i = 0; i < ii; i++) {
				RSTableColumn newColumn = new RSTableColumn(i);
				newColumn.setKey(cgtm.getColumnKey(i));
				newColumn.setName(cgtm.getColumnName(i));
				if (rows > 0) {
					newColumn.setMeta(cgtm.getEANObject(0,i));
				}
//52728				newColumn.setMinWidth(newColumn.getMinimumPreferredWidth());
				newColumn.setMinWidth(newColumn.getMinimumAllowableWidth());		//52728
				addColumn(newColumn);
			}
		}
		return;
	}

	/**
     * updateModel
     * @author Anthony C. Liberto
     * @param _table
     */
    public void updateModel(RowSelectableTable _table) {
		cgtm.setTable(_table);
		cgtm.resetKeys();
		createDefaultColumnsFromModel();
		refreshTable(true);
		return;
	}

	/**
     * getTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTitle() {
		return cgtm.getTableTitle();
	}
}
