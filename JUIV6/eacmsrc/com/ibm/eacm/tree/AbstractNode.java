//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import COM.ibm.eannounce.objects.EANActionItem;
/**
 * This is the node base class
 * @author Wendy Stimpson
 */
// $Log: AbstractNode.java,v $
// Revision 1.2  2013/07/18 18:38:04  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:18  wendy
// Initial code
//
public abstract class AbstractNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1L;
	/**
     * AbstractNode
     * @param o
     */
    public AbstractNode(Object o) {
		super(o);
	}

    /**
     * does this node match this string
     * @param s
     * @return
     */
    public abstract boolean matches(String s);
    
    /**
     * get the category code or actionitem key for this node
     * @return
     */
    public abstract String getNodeKey();
	/**
     * getActionItem
     * @return
     */
    public EANActionItem getActionItem() {
		return null;
	}
}
