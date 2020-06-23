//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * This is used for NLSItems in the tree
 * @author Wendy Stimpson
 */
//$Log: NLSNode.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class NLSNode extends DefaultMutableTreeNode implements EACMGlobals {
	private static final long serialVersionUID = 1L;
	private int type = -1;

    /**
     * nlsNode
     *
     * @param _o
     * @param _type
     */
    protected NLSNode(Object _o, int _type) {
		super(_o);
		type = _type;
	}

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
		Object o = getUserObject();
		if (o != null) {
			if (Utils.isTestMode()) {
				return o.toString() + getTypeString();
			}
			return o.toString();
		}
		//nlstree = NLS Language Tree
		return Utils.getResource("nlstree");
	}

    /**
     * getNLSItem
     *
     * @return
     */
    public NLSItem getNLSItem() {
		Object o = getUserObject();
		if (o instanceof NLSItem) {
			return (NLSItem)o;
		}
		return null;
	}

	/**
     * getType
     * @return
     */
	public int getType() {
		return type;
	}
	
	/**
	 * get the name of the icon for this type
	 * @return
	 */
	protected String getIconName(){
		switch(type){
		case NLS_READ_ONLY:
			return "view.gif";
		case NLS_WRITE:
			return "edit.gif";
		case NLS_CREATE:
			return "create.gif";
		}
		return "folder.gif";
	}

	/**
	 * @return
	 */
	private String getTypeString() {
		switch(type){
		case NLS_READ_ONLY:
			//nlstree.read =  (Read Only)
			return " "+Utils.getResource("nlstree.read");
		case NLS_WRITE:
			//nlstree.write=  (Update)
			return " "+Utils.getResource("nlstree.write");
		case NLS_CREATE:
			//nlstree.create =  (Create)
			return " "+Utils.getResource("nlstree.create");
		}
		return "";
	}
}

