/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: LockedFoundFlagRend.java,v $
 * Revision 1.2  2008/01/30 16:27:02  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:20  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:11  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.2  2005/01/26 17:43:26  tony
 * JTest Format Mods
 *
 * Revision 1.1.1.1  2004/02/10 16:59:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2003/10/03 16:39:14  tony
 * updated accessibility.
 *
 * Revision 1.6  2003/05/01 22:41:37  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.5  2003/05/01 21:07:28  tony
 * adjusted found rendering to improve performance
 *
 * Revision 1.4  2003/04/21 17:30:20  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.3  2003/04/11 20:02:34  tony
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
public class LockedFoundFlagRend extends EMLabel implements TableCellRenderer, EAccessConstants {
	private static final long serialVersionUID = 1L;
	
	/**
     * lockedFoundFlagRend
     * @author Anthony C. Liberto
     */
    public LockedFoundFlagRend() {
		setOpaque(true);
		setUseDefined(false);
		return;
	}

	/**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
		} else {
			setBackground(getPrefColor(PREF_COLOR_LOCK,DEFAULT_COLOR_LOCK));
			setForeground(table.getForeground());
		}
		if (hasFocus) {
			setBorder(EAccess.FOUND_FOCUS_BORDER);
		} else {
			setBorder(EAccess.FOUND_BORDER);
		}
		setFont(table.getFont());
		setText((value == null) ? "" : value.toString());
		if (table instanceof EAccessibleTable) {
			((EAccessibleTable)table).updateContext(this.getAccessibleContext(),row,column);
		}
		return this;
	}
}

