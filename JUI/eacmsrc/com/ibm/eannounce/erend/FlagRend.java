/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: FlagRend.java,v $
 * Revision 1.2  2008/01/30 16:27:01  wendy
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
 * Revision 1.3  2005/01/28 17:54:20  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:43:26  tony
 * JTest Format Mods
 *
 * Revision 1.1.1.1  2004/02/10 16:59:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/10/03 16:39:14  tony
 * updated accessibility.
 *
 * Revision 1.7  2003/06/17 20:45:14  tony
 * updated logic to use underline instead of empty border
 *
 * Revision 1.6  2003/04/28 16:44:08  tony
 * adjusted rendering of vertical table.
 *
 * Revision 1.5  2003/04/11 20:02:34  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.erend;
import com.elogin.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FlagRend extends EMLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;
	/**
     * noFocusBorder
     */
    private Border noFocusBorder = UIManager.getBorder("eannounce.underlineBorder");
	/**
     * selectedBorder
     */
    private Border selectedBorder = UIManager.getBorder("eannounce.selectedBorder");
	/**
     * darkBorder
     */
    private Border darkBorder = UIManager.getBorder("eannounce.focusBorder");
	/**
     * flagRend
     * @author Anthony C. Liberto
     */
    public FlagRend() {
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
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
			setBorder(selectedBorder);
		} else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
			setBorder(noFocusBorder);
		}
		setFont(table.getFont());
		if (hasFocus) {
			setBorder(darkBorder);
		}
		setText((value == null) ? "" : value.toString());
		if (table instanceof EAccessibleTable) {
			((EAccessibleTable)table).updateContext(this.getAccessibleContext(),row,column);
		}
		return this;
	}
}

