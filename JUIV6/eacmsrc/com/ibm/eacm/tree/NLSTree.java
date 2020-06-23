//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;

import javax.swing.tree.DefaultTreeModel;

/*********************************************************************
 * This is used for NLSItems in a tree
 * @author Wendy Stimpson
 */
//$Log: NLSTree.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class NLSTree extends EACMTree {
	private static final long serialVersionUID = 1L;

    /**
     * nlsTree
     * @param dtm
     */
    public NLSTree(DefaultTreeModel dtm) {
		super(dtm);
		setCellRenderer(new NLSNodeRenderer());
		setDoubleBuffered(true);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.EACMTree#getAccessibilityKey()
	 */
	protected String getAccessibilityKey(){
		return "accessible.nlsTree";
	}
}

