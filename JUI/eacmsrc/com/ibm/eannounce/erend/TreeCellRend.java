/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: TreeCellRend.java,v $
 * Revision 1.2  2008/01/30 16:27:02  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:20  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:20  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:43:27  tony
 * JTest Format Mods
 *
 * Revision 1.1.1.1  2004/02/10 16:59:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/06/05 19:20:19  tony
 * updated rendering so that focus does not cause
 * the item to not be displayed in it entirety
 *
 * Revision 1.7  2003/04/11 20:02:35  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.erend;
import com.elogin.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.TreeCellRenderer;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class TreeCellRend extends ELabel implements TreeCellRenderer, EAccessConstants {
	private static final long serialVersionUID = 1L;
	/**
     * noFocusBorder
     */
    private Border noFocusBorder = UIManager.getBorder("eannounce.noFocusBorder");
    /**
     * selectedBorder
     */
 //   private Border selectedBorder = UIManager.getBorder("eannounce.selectedBorder");
    /**
     * darkBorder
     */
    private Border darkBorder = UIManager.getBorder("eannounce.focusBorder");

    /**
     * treeCellRend
     * @author Anthony C. Liberto
     */
    public TreeCellRend() {
        super();
        setUseDefined(false);
        setOpaque(true);
        return;
    }

    /**
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     * @author Anthony C. Liberto
     */
    public Component getTreeCellRendererComponent(JTree _tree, Object _o, boolean _select, boolean _expand, boolean _leaf, int _row, boolean _focus) {
        if (_select) {
            setForeground(UIManager.getColor("Tree.selectionForeground"));
            setBackground(UIManager.getColor("Tree.selectionBackground"));
        } else {
            setForeground(_tree.getForeground());
            setBackground(_tree.getBackground());
        }
        setFont(_tree.getFont());
        if (_focus) {
            setBorder(darkBorder);
        } else {
            setBorder(noFocusBorder);
        }
        setText((_o == null) ? "" : _o.toString());
        return this;
    }
}
