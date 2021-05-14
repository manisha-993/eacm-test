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
 * $Log: NLSActionTree.java,v $
 * Revision 1.2  2008/01/30 16:27:07  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:06  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:54  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.11  2005/03/23 18:44:29  tony
 * adjusted logic to eliminate english edit indicator on create.
 *
 * Revision 1.10  2005/03/09 19:54:43  tony
 * 6542 toolbar update
 *
 * Revision 1.9  2005/03/08 21:23:03  tony
 * updated getUsedNLS to ignore relator designation.
 *
 * Revision 1.8  2005/03/07 18:55:51  tony
 * enhancement update the NLSTree on the save so
 * that newly created NLS information shows up
 * as an update instead of as a create.
 *
 * Revision 1.7  2005/03/04 18:36:39  tony
 * enhanced logic
 *
 * Revision 1.6  2005/02/24 19:14:08  tony
 * 6542 added default selection capability
 *
 * Revision 1.5  2005/02/24 18:43:17  tony
 * 6542 added sort capability
 *
 * Revision 1.4  2005/02/18 17:06:53  tony
 * removed testing logic.
 *
 * Revision 1.3  2005/02/18 16:53:24  tony
 * cr_6542
 *
 * Revision 1.2  2005/02/17 22:45:42  tony
 * cr_5254
 *
 * Revision 1.1  2005/02/17 18:57:09  tony
 * cr_6542
 *
 */
package com.ibm.eannounce.eforms.edit;
import com.elogin.EAccessConstants;
import com.elogin.Routines;
import com.ibm.eannounce.eobjects.EScrollPane;
import com.ibm.eannounce.eserver.EComparator;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.tree.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NLSActionTree extends EScrollPane implements EAccessConstants, MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private EditController ec = null;
	private NLSTreeModel tm = new NLSTreeModel();
	private NLSTree tree = new NLSTree(tm);
	private Vector vctNode = new Vector();
	private boolean[] bAscend = {true, true};
	private EntityItem ei = null;

	/*
	 sort based on type with the following order of
	 precedence.... edit, create, read-only.
	 then sort that alphabetically.
	 */
	private EComparator comparator = new EComparator(bAscend) {
		public Object getObject(Object _o, int _i) {
			if (_o instanceof NLSNode) {
				if (_i == 0) {
					return new Integer(((NLSNode)_o).getType());
				}
			}
			return _o;
		}
	};

	/**
     * nlsActionTree
     *
     * @author Anthony C. Liberto
     * @param _ec
     */
    public NLSActionTree(EditController _ec) {
		super();
		setViewportView(tree);
		ec = _ec;
		tree.addMouseListener(this);
		tree.addKeyListener(this);
		setDoubleBuffered(true);
		return;
	}

	/**
     * load
     *
     * @author Anthony C. Liberto
     * @param _ei
     * @param _prof
     */
    public void load(EntityItem _ei, Profile _prof) {
        int[] nlsUsed = null;
		vctNode.clear();
		if (_ei != null) {
			ei = _ei;
			nlsUsed = getUsedNLS(_ei);
			if (nlsUsed != null) {
				processNLS(nlsUsed,_prof);
			}
		}
		loadTree();
		expandAll();
		return;
	}

	/**
     * reload
     *
     * @author Anthony C. Liberto
     * @param _prof
     */
    public void reload(Profile _prof) {
        int[] nlsUsed = null;
		vctNode.clear();
		if (ei != null) {
			nlsUsed = getUsedNLS(ei);
			if (nlsUsed != null) {
				processNLS(nlsUsed,_prof);
			}
		}
		loadTree();
		expandAll();
		return;
	}

	/*
	 we need to retrieve all the used nls ids from
	 the uplink and downlink as well as the item
	 itself....  Not sure if we should only do this
	 on the RELATOR or if it should happen EVERYWHERE
	 */
	private int[] getUsedNLS(EntityItem _ei) {
		int[] iOut = null;
		EntityItem tmpEI = null;
		if (_ei != null) {
			if (_ei.isNew()) {											//stolpe_20050322
				iOut = new int[0];										//stolpe_20050322
				return iOut;											//stolpe_20050322
			}															//stolpe_20050322
			iOut = _ei.getInUseNLSIDs();
			if (_ei.hasDownLinks()) {
				tmpEI = (EntityItem)_ei.getDownLink(0);
				iOut = Routines.combine(iOut,tmpEI.getInUseNLSIDs());
			}
			if (_ei.hasUpLinks()) {
				tmpEI = (EntityItem)_ei.getUpLink(0);
				iOut = Routines.combine(iOut,tmpEI.getInUseNLSIDs());
			}
		}
		return iOut;
	}

	private void processNLS(int[] _nls, Profile _prof) {
		if (_nls != null) {
			int ii = _nls.length;
			Vector vctNLS = null;
			Vector vctRead = null;
			Vector vctWrite = null;
			if (_prof != null) {
				vctNLS = new Vector();
				for (int i=0;i<ii;++i) {
					vctNLS.add(new Integer(_nls[i]));
				}
				vctRead = new Vector(_prof.getReadLanguages());
				vctWrite = new Vector(_prof.getWriteLanguages());
				vctRead.removeAll(vctWrite);
				if (vctNLS.isEmpty()) {
					processDefault(vctRead,vctWrite);
				} else {
					processNLS(vctNLS,vctRead,NLS_READ_ONLY);
					processNLS(vctNLS,vctWrite,NLS_WRITE);
				}
			}
		}
		return;
	}

	private void processDefault(Vector _read, Vector _write) {
		NLSItem nlsItem = null;
		while (!_read.isEmpty()) {
			nlsItem = (NLSItem)_read.remove(0);
			addNode(nlsItem,NLS_READ_ONLY);
		}
		while (!_write.isEmpty()) {
			nlsItem = (NLSItem)_write.remove(0);
			addNode(nlsItem,NLS_CREATE);
		}
		return;
	}

	private void processNLS(Vector _nls, Vector _vct, int _type) {
		NLSItem nlsItem = null;
		if (_nls != null && !_nls.isEmpty()) {
			if (_vct != null) {
				Integer iTest = null;
				while (!_vct.isEmpty()) {
					nlsItem = (NLSItem)_vct.remove(0);
					if (nlsItem != null) {
						iTest = new Integer(nlsItem.getNLSID());
						if (_nls.contains(iTest)) {
							_nls.remove(iTest);
							addNode(nlsItem,_type);
						} else if (_type == NLS_WRITE) {
							addNode(nlsItem,NLS_CREATE);
						}
					}
				}
			}
		}
		return;
	}

	/*
	 add to a temporary location so that order
	 can be refined further
	 */
	private void addNode(NLSItem _item, int _type) {
		vctNode.add(new NLSNode(_item,_type));
		return;
	}

	private void expandAll() {
		tree.expandAll(tm.getRootNode());
		return;
	}

	/*
	 actually load the tree here....
	 */
	private void loadTree() {
		if (!vctNode.isEmpty()) {
			NLSNode[] nodes = (NLSNode[])vctNode.toArray(new NLSNode[vctNode.size()]);
            int ii = nodes.length;
        	tm.clear();
			sort(nodes);
			for (int i=0;i<ii;++i) {
				tm.addNode(nodes[i]);
			}
		}
		return;
	}

	/*
	 sort the tree....
	 */
	private void sort(NLSNode[] _nodes) {
		Arrays.sort(_nodes,comparator);
		return;
	}

	/**
     * setSelection
     *
     * @author Anthony C. Liberto
     * @param _nls 
     */
	public void setSelection(NLSItem _nls) {
		NLSNode node = tm.getNode(_nls);
		if (node != null) {
			tree.setSelectionPath(new TreePath(node.getPath()));
		}
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		super.dereference();

		ec = null;
		ei = null;

		tm.dereference();
		tm = null;

		tree.removeMouseListener(this);
		tree.removeKeyListener(this);
		tree.dereference();
		tree = null;

		vctNode.clear();
		vctNode = null;
		return;
	}

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent _me) {
		if (isEnabled()) {
			TreePath path = tree.getPathForLocation(_me.getX(), _me.getY());
			if (path == null) {
                return;
			}
			tree.setSelectionPath(path);
			onClick(path);
		}
		return;
	}
    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent me) {}
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent me) {}
    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent me) {}
    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent me) {}

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent _ke) {
		if (_ke.getKeyCode() == KeyEvent.VK_ENTER) {
			TreePath path = tree.getSelectionPath();
			if (path == null) {
                return;
			}
			onClick(path);
		}
		return;
	}
    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent _ke) {}
    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent _ke) {}
    private void onClick(TreePath _path) {
		onClick(_path.getLastPathComponent());
		return;
	}
    /**
     * onClick
     *
     * @author Anthony C. Liberto
     * @param _node
     */
    public void onClick(Object _node) {}

	/**
     * getPreferredWidth
     *
     * cr_6542_tbar
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getPreferredWidth() {
		Dimension d = tree.getPreferredSize();
		return d.width + 15;
	}
}
