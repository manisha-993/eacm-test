//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;

import COM.ibm.eannounce.objects.EANActionItem;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.tree.AbstractNode;

/**
 * This is the action in the navigate pane
 * @author Wendy Stimpson
 */
// $Log: ActionNode.java,v $
// Revision 1.1  2012/09/27 19:39:18  wendy
// Initial code
//
public class ActionNode extends AbstractNode {
	private static final long serialVersionUID = 1L;

	/**
     * ActionNode
     * @param o
     */
    public ActionNode(EANActionItem o) {
		super(o);
	}

	/**
     * @see java.lang.Object#toString()
     */
    public String toString() {
		Object o = null;
        if (Utils.isTestMode()) {
			return getTip();
		}
		o = getUserObject();
		if (o != null) {
			return o.toString();
		}
		return "ActionNode is Null";
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.tree.AbstractNode#getNodeKey()
     */
    public String getNodeKey(){
    	Object o = getUserObject();
    	if (o instanceof EANActionItem) {
    		EANActionItem ean = (EANActionItem)o;
    		return ean.getActionItemKey();
    	}

		return "ActionNode is null";
    }
    
	/**
     * getTip
     * @return
     */
    private String getTip() {
		Object o = getUserObject();
		if (o != null) {
			if (o instanceof EANActionItem) {
				EANActionItem ean = (EANActionItem)o;
				return ean.toString() + " (" + ean.getActionItemKey() + ")";
			}
			return o.toString();
		}
		return "ActionNode is null";
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.tree.AbstractNode#matches(java.lang.String)
     */
    public boolean matches(String s) {
		Object o = getUserObject();
		if (o instanceof String) {
			return s.equals((String)o);
		} else if (o instanceof EANActionItem) {
			return s.equals(((EANActionItem)o).getKey());
		}
		return false;
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.tree.AbstractNode#getActionItem()
     */
    public EANActionItem getActionItem() {
		Object o = getUserObject();
		if (o instanceof EANActionItem) {
			return (EANActionItem)o;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
	    return 0;
	}
}
