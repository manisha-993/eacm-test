//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.tree;

import javax.swing.tree.DefaultTreeModel;
import COM.ibm.opicmpdh.transactions.NLSItem;

/*********************************************************************
 * This is used for nls nodes
 * @author Wendy Stimpson
 */
//$Log: NLSTreeModel.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class NLSTreeModel extends DefaultTreeModel 
{
	private static final long serialVersionUID = 1L;

	/**
	 * nlsTreeModel
	 *
	 */
	protected NLSTreeModel() {
		super(new NLSNode(null,-1));
	}

	/**
	 * clear
	 */
	protected void clear() {
		NLSNode an = getRootNode();
		if (an != null) {
			while(an.getChildCount() > 0) {
				removeNodeFromParent((NLSNode)an.getFirstChild());
			}
		}
	}

	/**
	 * addNode
	 *
	 * @param node
	 */
	protected void addNode(NLSNode node) {
		NLSNode parent = getRootNode();
		insertNodeInto(node, parent, parent.getChildCount());
	}
	/**
	 * getRootNode
	 *
	 * @return
	 */
	protected NLSNode getRootNode() {
		Object o = getRoot();
		if (o instanceof NLSNode) {
			return (NLSNode)o;
		}
		return null;
	}

	/**
	 * release memory
	 */
	protected void dereference() {
		clear();
		setRoot(null);
	}

	/**
	 * get the node for this nlsitem
	 * @param item
	 * @return
	 */
	protected NLSNode getNode(NLSItem item) {
		NLSNode parent = getRootNode();
		if (parent != null) {
			for (int i=0;i<getChildCount(parent);++i) {
				NLSNode node = (NLSNode)getChild(parent,i);
				if (item.equals(node.getNLSItem())){
					return node;
				}
			}
		}

		return null;
	}

}

