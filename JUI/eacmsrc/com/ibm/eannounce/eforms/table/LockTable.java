/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: LockTable.java,v $
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:05  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.2  2004/11/15 23:01:18  tony
 * improved table logic to sort only a single time on
 * navigation type table, instead of multiple times.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/10/31 17:30:48  tony
 * 52783
 *
 * Revision 1.8  2003/10/29 16:47:40  tony
 * 52728
 *
 * Revision 1.7  2003/06/13 17:32:52  tony
 * 51255 resize on refresh issue.
 *
 * Revision 1.6  2003/06/09 23:05:51  tony
 * 51255
 *
 * Revision 1.5  2003/06/09 15:52:15  tony
 * 51228
 *
 * Revision 1.4  2003/05/21 15:36:58  tony
 * 50836
 *
 * Revision 1.3  2003/05/15 18:49:50  tony
 * updated persistant lock functionality.
 *
 * Revision 1.2  2003/03/19 20:38:34  tony
 * updated logic to improve functionality.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.18  2002/11/11 22:55:41  tony
 * adjusted classification on the toggle
 *
 * Revision 1.17  2002/11/07 16:58:32  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.ibm.eannounce.eforms.action.ActionController;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class LockTable extends NavTable {
	private static final long serialVersionUID = 1L;
	/**
     * lockTable
     * @param _o
     * @param _table
     * @param _ac
     * @author Anthony C. Liberto
     */
    public LockTable(Object _o, RowSelectableTable _table, ActionController _ac) {
		super(_o,_table,_ac,true);
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
		updateModel(_table,true);												//51255
		return;																	//51255
	}																			//51255

	/**
     * updateModel
     * @param _table
     * @param _resize
     * @author Anthony C. Liberto
     */
    public void updateModel(RowSelectableTable _table, boolean _resize) {		//51255
		cgtm.setTable(_table);
		cgtm.resetKeys();
//51255		createDefaultColumnsFromModel();
		refreshTable(_resize);													//51255
		if (hasRows()) {							//51228
			setRowSelectionInterval(0,0);			//51228
		}											//51228
		if (hasColumns()) {							//51228
			setColumnSelectionInterval(0,0);		//51228
		}											//51228
		return;
	}

    /**
     * refreshTable
     *
     * @param _resize
     * @author Anthony C. Liberto
     */
    public void refreshTable(boolean _resize) {									//51255
		resizeAndRepaint();
//51255		if (!isTableEmpty()) {				//50836
		if (!isEmpty() && _resize) {										//51255
			resizeCells();
		}																		//51255
//51255		}									//50836
		repaint();
		return;
	}

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() {					 //21643
		return "LOCK";
	}

	/**
     * getSelectedObjects
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getSelectedObjects(boolean _new) {
		int[] rows = getSelectedRows();
		int ii = rows.length;
		LockItem[] out = null;
        if (ii <= 0) {												//013598
			return null;
		}											//013598
		out = new LockItem[ii];
		for (int i=0;i<ii;++i) {
			LockItem li = (LockItem)cgtm.getRow(rows[i]);
			out[i] = li;
		}
		return out;
	}
}
