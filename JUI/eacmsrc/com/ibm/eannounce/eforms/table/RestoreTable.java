/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: RestoreTable.java,v $
 * Revision 1.3  2009/05/26 13:42:12  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:06  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.3  2004/03/15 16:11:48  tony
 * 53719
 *
 * Revision 1.2  2004/03/15 16:05:40  tony
 * 53719
 *
 * Revision 1.1.1.1  2004/02/10 16:59:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2003/07/24 17:31:53  tony
 * restore functionality addition.
 * First pass complete.
 *
 * Revision 1.5  2003/07/23 20:41:57  tony
 * added and enhanced restore logic
 *
 * Revision 1.4  2003/07/22 23:30:13  tony
 * updated logic
 *
 * Revision 1.3  2003/04/15 17:31:34  tony
 * changed to e-announce.focusborder
 *
 * Revision 1.2  2003/04/14 21:38:25  tony
 * updated table Logic.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.22  2002/11/07 16:58:33  tony
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
public class RestoreTable extends LockTable implements ETable{
	private static final long serialVersionUID = 1L;
	/**
     * RestoreTable
     * @param _o
     * @param _table
     * @param _ac
     * @author Anthony C. Liberto
     */
    public RestoreTable(Object _o, RowSelectableTable _table, ActionController _ac) {
		super(_o,_table,_ac);
		cgtm.setType(TABLE_RESTORE);	//53719
	}

    /**
     * getSelectedObjects
     *
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getSelectedObjects(boolean _new) {
		int[] rows = getSelectedRows();
		int ii = rows.length;
		InactiveItem[] out = null;
        if (ii <= 0) {
			return null;
		}
		out = new InactiveItem[ii];
		for (int i=0;i<ii;++i) {
			InactiveItem ei = (InactiveItem)cgtm.getRow(rows[i]);
			out[i] = ei;
		}
		return out;
	}

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() {
		return "RESTORE";
	}

/*
 53719
 */
	/**
     * updateModel
     * @author Anthony C. Liberto
     * @param _table
     */
    public void updateModel(RowSelectableTable _table) {
		boolean bFilter = isFiltered();
		cgtm.updateModel(_table);
		resizeCells();
		if (bFilter) {
			filter();
		}
	}

}
