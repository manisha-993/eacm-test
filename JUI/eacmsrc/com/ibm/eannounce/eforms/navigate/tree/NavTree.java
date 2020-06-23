/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: NavTree.java,v $
 * Revision 1.2  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:58  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:03  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/06/02 20:41:59  tony
 * dwb3_20050602
 *
 * Revision 1.6  2005/02/09 19:29:51  tony
 * JTest After Scout
 *
 * Revision 1.5  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.4  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/31 20:47:46  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 23:42:28  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/10/20 16:37:15  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.7  2003/04/25 21:27:00  tony
 * updated NavTree rendering.
 *
 * Revision 1.6  2003/04/24 16:47:54  tony
 * adjusted opaque to allow for border painting.
 *
 * Revision 1.5  2003/04/24 15:33:11  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.4  2003/04/04 19:27:25  tony
 * cleaned up code
 *
 * Revision 1.3  2003/03/07 21:40:48  tony
 * Accessibility update
 *
 * Revision 1.2  2003/03/05 18:54:25  tony
 * accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:32  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate.tree;
import com.elogin.EAccess;
//import com.elogin.EPopupMenu;
import com.ibm.eannounce.eobjects.ETree;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavTree extends ETree {
	private static final long serialVersionUID = 1L;
	private Icon myOpenIcon = eaccess().getImageIcon("open.gif");
	private Icon myClosedIcon = eaccess().getImageIcon("closed.gif");
	private Icon myLeafIcon = eaccess().getImageIcon("leaf.gif");
	private boolean showHandles = true;

	/**
     * NavTree
     * @param _dtm
     * @author Anthony C. Liberto
     */
    public NavTree(DefaultTreeModel _dtm) {
		super(_dtm);
		init();
		return;
	}

	private void init() {
		setCellRenderer(new MyRenderer());
		getAccessibleContext().setAccessibleDescription(getString("accessible.navTree"));
		initAccessibility("accessible.navTree");
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
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setShowsHandles(boolean _b) {
		showHandles = _b;
	}

	/**
     * getShowsHandles
     * @return
     * @author Anthony C. Liberto
     */
    public boolean getShowsHandles() {
		return showHandles;
	}

	/**
     * refreshModel
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
		myOpenIcon = null;
		myClosedIcon = null;
		myLeafIcon = null;

		removeAll();
		removeNotify();
		return;
	}

	private class MyRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;
		public Component getTreeCellRendererComponent(JTree _tree, Object _val, boolean _sel, boolean _expnd, boolean _leaf, int _row, boolean _focus) {
			JComponent comp = (JComponent)super.getTreeCellRendererComponent(_tree,_val,_sel,_expnd,_leaf,_row,_focus);
			comp.setFont(_tree.getFont());
			if (_sel) {
				comp.setBackground(getSelectionBackground());
				comp.setForeground(getSelectionForeground());
				comp.setBorder(UIManager.getBorder("eannouce.selectedBorder"));
			} else {
				comp.setBackground(_tree.getBackground());
				comp.setForeground(_tree.getForeground());
				comp.setBorder(null);
			}
			if (_focus) {
				comp.setBorder(UIManager.getBorder("eannounce.focusBorder"));
			}
			comp.setOpaque(true);
			return comp;
		}

		public Icon getClosedIcon() {
			return myClosedIcon;
		}

		public Icon getLeafIcon() {
			return myLeafIcon;
		}

		public Icon getOpenIcon() {
			return myOpenIcon;
		}

		public Icon getDefaultClosedIcon() {
			return super.getDefaultClosedIcon();
		}

		public Icon getDefaultLeafIcon() {
			return super.getDefaultLeafIcon();
		}

		public Icon getDefaultOpenIcon() {
			return super.getDefaultOpenIcon();
		}
	}
/*
 accessibility
 */
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

	/**
     * createPopupMenu
     *
     * @author Anthony C. Liberto
     */
    public void createPopupMenu() {
		super.createPopupMenu();
		popup.addPopupMenu("xpndAll","xpndAll",this);
		popup.addPopupMenu("clpseAll","clpseAll",this);
		return;
	}

	/**
     * removePopupMenu
     *
     * @author Anthony C. Liberto
     */
    public void removePopupMenu() {
		if (popup != null) {
			popup.removeMenu("xpndAll",this);
			popup.removeMenu("clpseAll",this);
		}
		super.removePopupMenu();
		return;
	}

	/**
     * actionPerformed
     *
     * @param _ae
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
		String str = _ae.getActionCommand();
		if (str.equals("xpndAll")) {
			expandAll();
		} else if (str.equals("clpseAll")) {
			collapseAll();
		}
		return;
	}
}
