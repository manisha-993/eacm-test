/**
 * Copyright (c) 2004-2005 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * cr_6542
 *
 * @version 3.0a 2005/02/16
 * @author Anthony C. Liberto
 *
 * $Log: NLSTreeModel.java,v $
 * Revision 1.2  2008/01/30 16:27:07  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:06  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:54  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/24 19:14:08  tony
 * 6542 added default selection capability
 *
 * Revision 1.2  2005/02/24 18:43:17  tony
 * 6542 added sort capability
 *
 * Revision 1.1  2005/02/17 18:57:09  tony
 * cr_6542
 *
 */
package com.ibm.eannounce.eforms.edit;
import com.elogin.*;
import COM.ibm.opicmpdh.transactions.NLSItem;
import javax.swing.tree.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NLSTreeModel extends DefaultTreeModel implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private NLSTreeModel(NLSNode _an) {
		super(_an);
		return;
	}

    /**
     * nlsTreeModel
     *
     * @author Anthony C. Liberto
     */
    public NLSTreeModel() {
		this(new NLSNode(null,-1));
		return;
	}

    /**
     * clear
     *
     * @author Anthony C. Liberto
     */
    public void clear() {
		NLSNode an = getRootNode();
		if (an != null) {
			while(an.getChildCount() > 0) {
				removeNodeFromParent((NLSNode)an.getFirstChild());
			}
		}
		return;
	}

    /**
     * setRootName
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setRootName(String _s) {
		NLSNode root = getRootNode();
		if (root != null) {
			root.setUserObject(_s);
		}
		return;
	}

    /**
     * addNode
     *
     * @author Anthony C. Liberto
     * @param _item
     * @param _type
     */
    public void addNode(NLSItem _item, int _type) {
		NLSNode child = null;
        if (_item == null) {
			return;
		}
		child = new NLSNode(_item,_type);
		addNode(child);
		return;
	}

    /**
     * addNode
     *
     * @author Anthony C. Liberto
     * @param _node
     */
    public void addNode(NLSNode _node) {
		NLSNode parent = getRootNode();
		insertNodeInto(_node, parent, parent.getChildCount());
		return;
	}

    /**
     * getPath
     *
     * @author Anthony C. Liberto
     * @param _an
     * @return
     */
    public TreePath getPath(NLSNode _an) {
		return new TreePath(_an.getPath());
	}

    /**
     * getRootNode
     *
     * @author Anthony C. Liberto
     * @return
     */
    public NLSNode getRootNode() {
		Object o = getRoot();
		if (o instanceof NLSNode) {
			return (NLSNode)o;
		}
		return null;
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		clear();
		setRoot(null);
		return;
	}

    /**
     * getAllNodes
     *
     * @author Anthony C. Liberto
     * @return
     */
    public NLSNode[] getAllNodes() {
		NLSNode[] out = null;
		NLSNode[] children = getAllChildren();
		out = new NLSNode[children.length + 1];
		System.arraycopy(children,0,out,1,children.length);
		out[0] = getRootNode();
		return out;
	}

    /**
     * getAllChildren
     *
     * @author Anthony C. Liberto
     * @return
     */
	public NLSNode[] getAllChildren() {
		NLSNode parent = getRootNode();
		NLSNode[] out = null;
		if (parent != null) {
			int ii = getChildCount(parent);
			out = new NLSNode[ii];
			for (int i=0;i<ii;++i) {
				out[i] = (NLSNode)getChild(parent,i);
			}
		}
		return out;
	}

    /**
     * getNode
     * @param _item
     * @author Anthony C. Liberto
     * @return
     */
    public NLSNode getNode(NLSItem _item) {
		NLSNode[] children = getAllChildren();
		if (children != null) {
			int ii = children.length;
			for (int i=0;i<ii;++i) {
				if (_item.equals(children[i].getNLSItem())) {
					return children[i];
				}
			}
		}
		return null;
	}
}
