/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * BorderCellRenderer provides Opicm with the ability to highlight
 * found items by painting the cell border red.
 *
 * @version 2.1b 2000/02/06
 * @author Anthony C. Liberto
 *
 * $Log: SingleRenderer.java,v $
 * Revision 1.2  2008/01/30 16:27:02  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:20  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:20  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:11  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 17:54:19  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:43:25  tony
 * JTest Format Mods
 *
 * Revision 1.1.1.1  2004/02/10 16:59:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/10/30 00:43:49  tony
 * 52767
 *
 * Revision 1.7  2003/10/03 16:39:13  tony
 * updated accessibility.
 *
 * Revision 1.6  2003/06/17 20:45:15  tony
 * updated logic to use underline instead of empty border
 *
 * Revision 1.5  2003/04/24 22:22:50  tony
 * updated rendering
 *
 * Revision 1.4  2003/03/18 22:39:13  tony
 * more accessibility updates.
 *
 * Revision 1.3  2003/03/14 00:06:54  tony
 * setUseDefined method of eLabel defined
 *
 * Revision 1.2  2003/03/13 18:38:45  tony
 * accessibility and column Order.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:53  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2003/01/31 21:03:21  tony
 * 24263
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
public class SingleRenderer extends ESLabel implements TableCellRenderer {
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
     * SingleRenderer
     * @author Anthony C. Liberto
     */
    public SingleRenderer() {
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
        //52767		setText((value == null) ? "" : routines.splitString(value.toString(),80));
        setText((value == null) ? "" : value.toString()); //52767
        if (table instanceof EAccessibleTable) {
            ((EAccessibleTable) table).updateContext(this.getAccessibleContext(), row, column);
        }
        return this;
    }
}
