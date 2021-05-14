//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;

import com.ibm.eacm.tree.AbstractNode;

/**
 * This is the category of the action, ActionNodes are listed under this dropdown
 * @author Wendy Stimpson
 */
// $Log: CategoryNode.java,v $
// Revision 1.1  2012/09/27 19:39:18  wendy
// Initial code
//
public class CategoryNode extends AbstractNode {
	private static final long serialVersionUID = 1L;
	private String code = null;

	/**
     * CategoryNode
     * @param code2
     * @param val
     */
    public CategoryNode(String code2, String val) {
		super(val);
		this.code = code2;
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.tree.AbstractNode#getNodeKey()
     */
    public String getNodeKey(){
    	return code;
    }

	/**
     * @see java.lang.Object#toString()
     */
    public String toString() {
		Object o = getUserObject();
		if (o != null) {
			return o.toString();
		}
		return "CategoryNode is Null";
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.tree.AbstractNode#matches(java.lang.String)
     */
    public boolean matches(String s) {
		return code.equals(s);
	}

	/**
	 * Automatically generated method: hashCode
	 *
	 * @return int
	 */
	public int hashCode() {
	    return 0;
	}
}
