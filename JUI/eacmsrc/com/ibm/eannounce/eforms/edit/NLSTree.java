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
 * $Log: NLSTree.java,v $
 * Revision 1.2  2008/01/30 16:27:07  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:06  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:54  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/03/07 18:55:51  tony
 * enhancement update the NLSTree on the save so
 * that newly created NLS information shows up
 * as an update instead of as a create.
 *
 * Revision 1.3  2005/02/18 16:53:24  tony
 * cr_6542
 *
 * Revision 1.2  2005/02/17 22:45:42  tony
 * cr_5254
 *
 * Revision 1.1  2005/02/17 18:57:09  tony
 * cr_6542
 *
 */
package com.ibm.eannounce.eforms.edit;
import com.elogin.EAccess;
import com.ibm.eannounce.eobjects.ETree;
import com.ibm.eannounce.erend.NLSNodeRenderer;
import javax.swing.tree.*;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NLSTree extends ETree {
	private static final long serialVersionUID = 1L;
	private boolean showHandles = true;

    /**
     * nlsTree
     *
     * @author Anthony C. Liberto
     * @param _dtm
     */
    public NLSTree(DefaultTreeModel _dtm) {
		super(_dtm);
		init();
		return;
	}

	private void init() {
		setCellRenderer(new NLSNodeRenderer());
		getAccessibleContext().setAccessibleDescription(getString("accessible.navTree"));
		initAccessibility("accessible.navTree");
		setDoubleBuffered(true);
		return;
	}

    /**
     * @see javax.swing.JComponent#updateUI()
     * @author Anthony C. Liberto
     */
    public void updateUI() {
        setUI(new MyTreeUI());
        invalidate();
        return;
    }

    /**
     * setShowsHandles
     *
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setShowsHandles(boolean _b) {
		showHandles = _b;
	}

    /**
     * getShowsHandles
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean getShowsHandles() {
		return showHandles;
	}

    /**
     * refreshModel
     *
     * @author Anthony C. Liberto
     */
    public void refreshModel() {
		firePropertyChange("model",null,null);
	}

	private class MyTreeUI extends BasicTreeUI {
        /**
         * @see javax.swing.plaf.basic.BasicTreeUI#shouldPaintExpandControl(javax.swing.tree.TreePath, int, boolean, boolean, boolean)
         * @author Anthony C. Liberto
         */
        protected boolean shouldPaintExpandControl(TreePath _path, int _row, boolean _expanded, boolean _beenExpanded,boolean _isLeaf) {
            boolean tmpShowHandles = getShowsHandles();
            boolean showRoot = getShowsRootHandles();
            int depth = -1;
			if (_isLeaf) {
				return false;
			}
			if (!tmpShowHandles && !showRoot) {
				return false;
			}
			depth = _path.getPathCount() - 1;
			if((depth == 0 || (depth == 1 && !isRootVisible())) && !showRoot) {
				return false;

			} else if ((depth > 0 || (depth > 1 && !isRootVisible())) && !tmpShowHandles) {
				return false;
			}
			return true;
		}

	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		initAccessibility(null);
		removeAll();
		removeNotify();
		return;
	}

    /**
     * initAccessibility
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void initAccessibility(String _s) {
		if (EAccess.isAccessible()) {
			AccessibleContext ac = getAccessibleContext();
			String strAccessible = null;
			if (ac != null) {
				if (_s == null) {
					ac.setAccessibleName(null);
					ac.setAccessibleDescription(null);
				} else {
					strAccessible = getString(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
		}
		return;
	}
}
