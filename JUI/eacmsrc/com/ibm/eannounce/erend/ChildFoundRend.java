/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ChildFoundRend.java,v $
 * Revision 1.2  2008/01/30 16:27:01  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:19  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:11  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 17:54:20  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:43:26  tony
 * JTest Format Mods
 *
 * Revision 1.1.1.1  2004/02/10 16:59:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2003/10/03 16:39:13  tony
 * updated accessibility.
 *
 * Revision 1.10  2003/09/17 21:16:17  tony
 * 52289
 *
 * Revision 1.9  2003/05/01 22:41:36  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.8  2003/05/01 21:07:28  tony
 * adjusted found rendering to improve performance
 *
 * Revision 1.7  2003/04/24 22:22:49  tony
 * updated rendering
 *
 * Revision 1.6  2003/04/24 15:33:12  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.5  2003/04/21 23:21:48  tony
 * added association renderer for used table.
 *
 * Revision 1.4  2003/04/11 20:02:33  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.erend;
import com.elogin.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ChildFoundRend extends ELabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;
	/**
     * childFoundRend
     * @author Anthony C. Liberto
     */
    public ChildFoundRend() {
		setOpaque(true);
		setUseDefined(false);
		return;
	}

	/**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setFont(table.getFont());
//52289		setForeground(getPrefColor(PREF_COLOR_CHILD,DEFAULT_COLOR_CHILD));
//52289		setBackground(getPrefColor(PREF_COLOR_FOUND,DEFAULT_COLOR_FOUND));
		if (isSelected) {											//52289
			setForeground(table.getSelectionForeground());			//52289
			setBackground(table.getSelectionBackground());			//52289
		} else {													//52289
			setForeground(table.getForeground());					//52289
			setBackground(table.getBackground());					//52289
		}															//52289

		if (hasFocus) {
			setBorder(EAccess.FOUND_FOCUS_BORDER);
		} else {
			setBorder(EAccess.FOUND_BORDER);
		}
		setText((value == null) ? "" : value.toString());
		if (table instanceof EAccessibleTable) {
			((EAccessibleTable)table).updateContext(this.getAccessibleContext(),row,column);
		}
		return this;
	}
}

