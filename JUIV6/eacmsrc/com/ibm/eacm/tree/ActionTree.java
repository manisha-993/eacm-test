//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;


import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;
/*********************************************************************
 * This is used to control the tree for actions and category
 */
//$Log: ActionTree.java,v $
//Revision 1.2  2013/07/18 18:38:04  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class ActionTree extends EACMTree implements EACMGlobals,ActionListener {
	private static final long serialVersionUID = 1L;

	private boolean showHandles = false;
	private JPopupMenu popup = null; 
	

    /**
     * get the expansion state of a tree.
     * 
     * @param tree
     * @return 
     */
    public static Vector<String> getExpansionState(JTree tree) {
    	Vector<String> tpVct = null;
		Enumeration<?> treeEnum = tree.getExpandedDescendants(new TreePath(tree.getModel().getRoot()));
		if(treeEnum!=null){
			// build a serializable vector
			tpVct = new Vector<String>();
			while (treeEnum.hasMoreElements()) {
				TreePath treePath = (TreePath) treeEnum.nextElement();
				AbstractNode lastnode = (AbstractNode)treePath.getLastPathComponent();
				tpVct.addElement(lastnode.getNodeKey());
			}
		}
		return tpVct;
    }

    /**
     * Restore the expansion state of a JTree.
     *
     * @param tree
     * @param tpVct an Vector of expansion state. 
     * @return
     */
    public static boolean loadExpansionState(ActionTree tree, Vector<String> tpVct) {
    	boolean loaded = false;
    	if (tpVct != null) {
    		for(int i=0;i<tpVct.size();i++) {
    			TreePath treePath = tree.findPath(tpVct.elementAt(i));
    			if(treePath!=null){
    				tree.expandPath(treePath);
    				loaded = true;
    			}
            }
        }
        return loaded;
    }

    /**
     * find the corresponding node in the local objects
     * @param nodekey
     * @return
     */
    private TreePath findPath(String nodekey){
		DefaultTreeModel model = (DefaultTreeModel)getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		Enumeration<?> breadthFirstEnum = root.breadthFirstEnumeration();
		while(breadthFirstEnum.hasMoreElements()){
			AbstractNode node = (AbstractNode)breadthFirstEnum.nextElement();
			if(node.getNodeKey().equals(nodekey)){
				return getPath(node);
			}
		}

		return null;
    }
    
	/**
     * NavTree
     * @param dtm
     */
    public ActionTree(DefaultTreeModel dtm) {
		super(dtm);
    	createPopupMenu();
		setCellRenderer(new ActionNodeRenderer());
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.EACMTree#getAccessibilityKey()
	 */
	protected String getAccessibilityKey(){
		return "accessible.navTree";
	}

	/**
     * setRowHeight
     * @param font
     */
    public void setRowHeight(Font font) {
		if (font != null) {
			FontMetrics fm = getFontMetrics(font);
			setRowHeight(fm.getHeight() + 4);
		}
	}
    /**
     * @see javax.swing.JComponent#updateUI()
     */
    public void updateUI() {
        setUI(new MyTreeUI());
        invalidate();
    }

    /**
     * dereference
     */
    public void dereference() {
		removePopupMenu();
		popup = null;
		super.dereference();
	}

	/**
     * createPopupMenu
     */
    private void createPopupMenu() {
		if (popup == null) {
			popup = new JPopupMenu();
			setComponentPopupMenu(popup);
		}

		JMenuItem mi = new JMenuItem(Utils.getResource(EXPANDALL_ACTION));  
		mi.setActionCommand(EXPANDALL_ACTION);
		mi.addActionListener(this);
		popup.add(mi);
		mi = new JMenuItem(Utils.getResource(COLLAPSEALL_ACTION));  
		mi.setActionCommand(COLLAPSEALL_ACTION);
		mi.addActionListener(this);
		popup.add(mi);
	}


    /**
     * remove the popop menu
     */
    private void removePopupMenu() {
		for (int ii=0; ii<popup.getComponentCount(); ii++) {
			Component comp = popup.getComponent(ii);
			if (comp instanceof JMenuItem) {// separators are null
				JMenuItem mi = (JMenuItem)comp;
				mi.removeActionListener(this);
				EACM.closeMenuItem(mi);
			}  
		}
		popup.setUI(null);
		popup.removeAll();
    }
	/**
     * actionPerformed
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
		String str = ae.getActionCommand();
		if (str.equals(EXPANDALL_ACTION)) {
			expandAll();
		} else if (str.equals(COLLAPSEALL_ACTION)) {
			collapseAll();
		}
	}
	private class MyTreeUI extends BasicTreeUI {
		/**
         * @see javax.swing.plaf.basic.BasicTreeUI#shouldPaintExpandControl(javax.swing.tree.TreePath, int, boolean, boolean, boolean)
         */
        protected boolean shouldPaintExpandControl(TreePath path, int row, boolean expanded, boolean beenExpanded,boolean isLeaf) {
            boolean tmpShowHandles = showHandles;
            boolean showRoot = getShowsRootHandles();
            int depth = -1;
			if (isLeaf) {
				return false;
			}
			if (!tmpShowHandles && !showRoot) {
				return false;
			}
			depth = path.getPathCount() - 1;
			if((depth == 0 || (depth == 1 && !isRootVisible())) && !showRoot) {
				return false;

			} else if ((depth > 0 || (depth > 1 && !isRootVisible())) && !tmpShowHandles) {
				return false;
			}
			return true;
		}

	}
}

