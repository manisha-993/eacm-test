//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;

import javax.accessibility.AccessibleContext;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * This is base class for NLSTree and NavTree
 * @author Wendy Stimpson
 */
//$Log: EACMTree.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public abstract class EACMTree extends JTree 
{
	private static final long serialVersionUID = 1L;

	/**
     * @param model
     */
    public EACMTree(TreeModel model) {
		super(model);
	
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		getAccessibleContext().setAccessibleDescription(Utils.getResource(getAccessibilityKey()));
		initAccessibility(getAccessibilityKey());
	}
	
	/**
	 * get the key to use for accessibility information
	 * @return
	 */
	protected abstract String getAccessibilityKey();

	/**
     * dereference
     */
    public void dereference() {
		initAccessibility(null);
		removeAll();
		removeNotify();
		
		setUI(null);
	}

	/**
     * collapseAll
     */
    public void collapseAll() {
		collapseAll(null);
	}

	/**
     * collapseAll
     * @param selNode
     */
    public void collapseAll(TreeNode selNode) {
		TreeNode rootNode = getRootNode();
		if (rootNode != null) {
			collapseNode(rootNode);
			for (int i=0;i<rootNode.getChildCount();++i) {
				collapseNode(rootNode.getChildAt(i));
			}
			if (selNode != null) {
				setSelectionPath(getPath(selNode));
			}
		}
	}

	/**
     * collapseNode
     * @param node
     */
    private void collapseNode(TreeNode node) {
        if (node != null && !node.isLeaf()) {		
			collapsePath(getPath(node));
		}											
		if (node.getChildCount() > 0) {
			for (int i=0;i<node.getChildCount();++i) {
				collapseNode(node.getChildAt(i));
			}
		}
	}

	/**
     * expandAll
     */
    public void expandAll() {
		expandAll(null);
	}

	/**
     * expandAll
     * @param selNode
     */
    public void expandAll(TreeNode selNode) {
		TreeNode rootNode = getRootNode();
        if (rootNode != null) {
			expandNode(rootNode);
			for (int i=0;i<rootNode.getChildCount();++i) {
				expandNode(rootNode.getChildAt(i));
			}
			if (selNode != null) {
				setSelectionPath(getPath(selNode));
			}
		}
	}

	/**
     * expandNode
     * @param node
     */
    private void expandNode(TreeNode node) {
        if (!node.isLeaf()) {		
			expandPath(getPath(node));
		}										
		if (node.getChildCount() > 0) {
			for (int i=0;i<node.getChildCount();++i) {
				expandNode(node.getChildAt(i));
			}
		}
	}

	/**
     * if the node is a DefaultMutableNode use the getPath function
     *
     * @param node
     * @return
     */
    protected TreePath getPath(TreeNode node) {
    	if (node instanceof DefaultMutableTreeNode) {
    		return new TreePath(((DefaultMutableTreeNode)node).getPath());
    	} 
    	return null;
	}

	/**
     * getRootNode
     * @return
     */
    private TreeNode getRootNode() {
		TreeModel tm = getModel();
		Object root = tm.getRoot();
		if (root instanceof TreeNode) {
			return (TreeNode)root;
		}

		return null;
	}
    /**
     * initAccessibility
     *
     * @param s
     */
    private void initAccessibility(String s) {
    	AccessibleContext ac = getAccessibleContext();
    	if (ac != null) {
    		if (s == null) {
    			ac.setAccessibleName(null);
    			ac.setAccessibleDescription(null);
    		} else {
    			String strAccessible = Utils.getResource(s);
    			ac.setAccessibleName(strAccessible);
    			ac.setAccessibleDescription(strAccessible);
    		}
    	}
	}
}
