/**
 * Copyright (c) 2004-2005 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * cr_6542
 *
 * @version 3.0a 2005/02/16
 * @author Anthony C. Liberto
 *
 * $Log: NLSNodeRenderer.java,v $
 * Revision 1.2  2008/01/30 16:27:02  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:20  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:20  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2005/09/08 17:59:11  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.2  2005/02/25 17:54:34  tony
 * 6542 change request wrap-up
 *
 * Revision 1.1  2005/02/18 16:54:01  tony
 * cr_6542
 *
 *
 */
package com.ibm.eannounce.erend;
import com.elogin.*;
import com.ibm.eannounce.eforms.edit.NLSNode;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTree;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NLSNodeRenderer extends TreeCellRend implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	/**
     * NLSNodeRenderer
     *
     * @author Anthony C. Liberto
     */
    public NLSNodeRenderer() {
		super();
		return;
	}

	/**
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     * @author Anthony C. Liberto
     */
    public Component getTreeCellRendererComponent(JTree _tree, Object _val, boolean _sel, boolean _expnd, boolean _leaf, int _row, boolean _focus) {
		JComponent comp = (JComponent)super.getTreeCellRendererComponent(_tree,_val,_sel,_expnd,_leaf,_row,_focus);
		if (comp instanceof ELabel && _val instanceof NLSNode) {
			ELabel lbl = (ELabel)comp;
			int type = ((NLSNode)_val).getType();
			if (type == NLS_READ_ONLY) {
				lbl.setIcon(eaccess().getImageIcon("view.gif"));
			} else if (type == NLS_WRITE) {
				lbl.setIcon(eaccess().getImageIcon("edit.gif"));
			} else if (type == NLS_CREATE) {
				lbl.setIcon(eaccess().getImageIcon("create.gif"));
			} else {
				lbl.setIcon(eaccess().getImageIcon("folder.gif"));
			}
		}
		return comp;
	}
}
