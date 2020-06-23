//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;


import javax.swing.tree.DefaultTreeModel;

import COM.ibm.eannounce.objects.EANActionItem;

import com.ibm.eacm.objects.EANActionMap;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.tree.AbstractNode;
import com.ibm.eacm.tree.ActionNode;
import com.ibm.eacm.tree.CategoryNode;
/*********************************************************************
 * this is the treemodel for navigate actions
 * @author Wendy Stimpson
 */
//$Log: ActionTreeModel.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class ActionTreeModel extends DefaultTreeModel implements EACMGlobals {
	private static final long serialVersionUID = 1L;
	private EANActionMap actionMap = new EANActionMap();

	/**
     * ActionTreeModel
     */
    public ActionTreeModel() {
		super(new CategoryNode("ROOT","navActionTree -- ROOT"));
	}

	/**
     * clear
     */
    public void clear() {
		AbstractNode an = getRootNode();
		actionMap.clear();
		if (an != null) {
			while(an.getChildCount() > 0) {
				removeNodeFromParent((AbstractNode)an.getFirstChild());
			}
		}
	}

	/**
     * setRootName
     * @param s
     */
    public void setRootName(String s) {
		AbstractNode root = getRootNode();
		if (root != null) {
			root.setUserObject(s);
		}
	}

	/**
     * addNode
     * @param ean
     */
    public void addNode(EANActionItem ean) {							
        AbstractNode child = new ActionNode(ean);
        AbstractNode parent = getParentNode(ean.getCategoryCode(), ean.getCategoryShortDescription());
		insertNodeInto(child, parent, parent.getChildCount());
		actionMap.addActionItem(ean);
	}

	/**
     * getActionItem
     * @param key
     * @return
     */
    public EANActionItem getActionItem(String key) {
		return actionMap.getActionItem(key);
	}

	/**
     * actionExists
     * @param sAction
     * @return
     */
    public boolean actionExists(String sAction) {
    	return actionMap.actionExists(sAction);
	}

	/**
     * getActionItemArray
     * @param key
     * @return
     */
    public EANActionItem[] getActionItemArray(String key) {
		return actionMap.getActionItemArray(key);
	}

	/**
     * getParentNode
     * @param code
     * @param desc
     * @return
     */
    private AbstractNode getParentNode(String code, String desc) {
		AbstractNode root = getRootNode();
        AbstractNode child = null;
        AbstractNode child2 = null;
		if (root != null) {
			for (int i=0;i<root.getChildCount();++i) {
				child = (AbstractNode)root.getChildAt(i);
				if (child.matches(code)) {
					return child;
				}
			}
		}

		child2 = new CategoryNode(code,desc);
		insertNodeInto(child2,root,root.getChildCount());
		return child2;
	}

	/**
     * getRootNode
     * @return
     */
    public AbstractNode getRootNode() {
		Object o = getRoot();
		if (o instanceof AbstractNode) {
			return (AbstractNode)o;
		}
		return null;
	}

	/**
     * dereference
     */
    public void dereference() {
		clear();
		actionMap.dereference();
		actionMap = null;
		setRoot(null);
	}

	/**
     * getActionItemArray
     * @param key
     * @return
     */
    public EANActionItem[] getActionItemArray(String[] key) {
		return actionMap.getActionItemArray(key);
	}
}
