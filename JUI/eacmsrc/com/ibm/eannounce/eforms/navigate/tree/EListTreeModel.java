/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EListTreeModel.java,v $
 * Revision 1.2  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:58  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:03  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/31 20:47:46  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 23:42:28  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2002/11/07 16:58:32  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate.tree;
import javax.swing.tree.*;

import COM.ibm.eannounce.objects.*;
import java.util.*;
/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EListTreeModel extends DefaultTreeModel {
	private static final long serialVersionUID = 1L;
	/**
     * UP_LINK
     */
    public static final int UP_LINK   = 1;
	/**
     * DOWN_LILNK
     */
    public static final int DOWN_LINK = -1;

	private RowSelectableTable table = null;

	private DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode("Parent");
	private DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("Child");

	private HashMap map = new HashMap();

	/**
     * eListTreeModel
     * @param _o
     * @author Anthony C. Liberto
     */
    public EListTreeModel(Object _o) {
		super(new DefaultMutableTreeNode(_o));
		DefaultMutableTreeNode root = getRootNode();
		addNode(parentNode, root);
		addNode(childNode, root);
		return;
	}

	/**
     * eListTreeModel
     * @author Anthony C. Liberto
     */
    public EListTreeModel() {
		this(new String("root"));
		return;
	}

	/**
     * clear
     * @author Anthony C. Liberto
     */
    public void clear() {
		while(parentNode.getChildCount() > 0) {
			removeNodeFromParent((DefaultMutableTreeNode)parentNode.getFirstChild());
		}
		while(childNode.getChildCount() > 0) {
			removeNodeFromParent((DefaultMutableTreeNode)childNode.getFirstChild());
		}
		map.clear();
		return;
	}

	/**
     * setRoot
     * @param _eList
     * @author Anthony C. Liberto
     */
    public void setRoot(EntityList _eList) {
		DefaultMutableTreeNode root = getRootNode();
		root.setUserObject(_eList);
		return;
	}

	private void clearRoot() {
		super.setRoot(null);
	}

	/**
     * load
     * @param _eList
     * @author Anthony C. Liberto
     */
    public void load(EntityList _eList) {
		clear();
		setRoot(_eList);
		load(_eList.getParentEntityGroup(), parentNode, childNode);
		return;
	}

	private void load(EntityGroup _eg, DefaultMutableTreeNode _parent, DefaultMutableTreeNode _child) {
		int ii = _eg.getEntityItemCount();
		for (int i=0;i<ii;++i) {
			load(_eg.getEntityItem(i), _parent, _child);
		}
		return;
	}

	private void load(EntityItem _ei, DefaultMutableTreeNode _parent, DefaultMutableTreeNode _child){
		addNode(UP_LINK,_ei,_parent);
		addNode(DOWN_LINK,_ei,_child);
		return;
	}

	private void addNode(int _type, EntityItem _ei, DefaultMutableTreeNode _parent) {
		if (_type == UP_LINK) {
			int pp = _ei.getUpLinkCount();
			for (int p=0;p<pp;++p) {
				EntityItem ei = (EntityItem)_ei.getUpLink(p);
				DefaultMutableTreeNode child = getNode(ei);
				addNode(child,_parent);
				addNode(_type, ei, child);
			}
		} else if (_type == DOWN_LINK) {
			int cc = _ei.getDownLinkCount();
			for (int c=0;c<cc;++c) {
				EntityItem ei = (EntityItem)_ei.getDownLink(c);
				DefaultMutableTreeNode child = getNode(ei);
				addNode(child,_parent);
				addNode(_type, ei, child);
			}
		}
		return;
	}

	private DefaultMutableTreeNode getNode(EntityItem _ei) {
//		String key = _ei.toString();		//need a better key, but this will work for now.
//		if (map.containsKey(key))
//			return (DefaultMutableTreeNode)map.get(key);
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(_ei);
//		map.put(key,node);
		return node;
	}


	private void addNode(DefaultMutableTreeNode _child, DefaultMutableTreeNode _parent) {
		insertNodeInto(_child, _parent, _parent.getChildCount());
		return;
	}

	/**
     * getPath
     * @param _tn
     * @return
     * @author Anthony C. Liberto
     */
    public TreePath getPath(DefaultMutableTreeNode _tn) {
		return new TreePath(_tn.getPath());
	}

	/**
     * getRootNode
     * @return
     * @author Anthony C. Liberto
     */
    public DefaultMutableTreeNode getRootNode() {
		Object o = getRoot();
		if (o instanceof DefaultMutableTreeNode) {
			return (DefaultMutableTreeNode)o;
		}
		return null;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		clear();
		clearRoot();
		table = null;
		parentNode = null;
		childNode = null;
		return;
	}
}
