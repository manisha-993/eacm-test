/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: MatrixFoundRend.java,v $
 * Revision 1.2  2008/01/30 16:27:03  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:20  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:20  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:12  tony
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
 * Revision 1.4  2003/10/03 16:39:14  tony
 * updated accessibility.
 *
 * Revision 1.3  2003/05/01 22:41:37  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.2  2003/05/01 21:07:28  tony
 * adjusted found rendering to improve performance
 *
 * Revision 1.1  2003/04/23 17:18:19  tony
 * added matrix renderers.
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
public class MatrixFoundRend extends EMLabel implements TableCellRenderer, EAccessConstants {
	private static final long serialVersionUID = 1L;
    /**
     * matrixFoundRend
     * @author Anthony C. Liberto
     */
    public MatrixFoundRend() {
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
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        if (hasFocus) {
            setBorder(EAccess.FOUND_FOCUS_BORDER);
        } else {
            setBorder(EAccess.FOUND_BORDER);
        }
        setFont(table.getFont());
        setText((value == null) ? "" : value.toString());
        if (table instanceof EAccessibleTable) {
            ((EAccessibleTable) table).updateContext(this.getAccessibleContext(), row, column);
        }
        return this;
    }
}
