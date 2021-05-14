//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.eacm.edit.EditController;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;

/*********************************************************************
 * This is used to control the tree for nls nodes
 * @author Wendy Stimpson
 */
//$Log: NLSTreeScroll.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class NLSTreeScroll extends JScrollPane implements EACMGlobals {
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	private NLSTreeModel tm = new NLSTreeModel();
	private NLSTree tree = new NLSTree(tm);
	private MouseAdapter mouseListener = null;
	private KeyAdapter keyListener = null;
	private Vector<NLSNode> vctNode = new Vector<NLSNode>();
	private EntityItem entityItem = null;

	/*
	 sort based on type with the following order of
	 precedence.... edit, create, read-only.
	 then sort that alphabetically.
	 */
	private Comparator<NLSNode> nodeComparator = new java.util.Comparator<NLSNode>(){
		public int compare(NLSNode node1, NLSNode node2) {
			if(node1.getType()==node2.getType()){
				return node1.toString().compareToIgnoreCase(node2.toString());
			}else{
				return (node1.getType()<node2.getType() ? -1 : (node1.getType()==node2.getType() ? 0 : 1));
			}
		}
	};

	/**
	 * nlsActionTree
	 * @param _ec
	 */
	public NLSTreeScroll(EditController ec) {
		setViewportView(tree);
		editCtrl = ec;
		mouseListener = new MouseAdapter(){
			/* (non-Javadoc)
			 * tree selection changes with mouse pressed, so move to that path
			 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			 */
			public void mousePressed(MouseEvent me) {
				if (isEnabled()) {
					TreePath path = tree.getPathForLocation(me.getX(), me.getY());
					if (path != null) {
						tree.setSelectionPath(path);
						onClick(path);
					}
				}
			}
		};
		tree.addMouseListener(mouseListener);
		keyListener = new KeyAdapter(){
			public void keyPressed(KeyEvent _ke) {
				if (_ke.getKeyCode() == KeyEvent.VK_ENTER) {
					TreePath path = tree.getSelectionPath();
					if (path != null) {
						onClick(path);
					}
				}
			}
		};
		tree.addKeyListener(keyListener);
		setDoubleBuffered(true);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		tree.setEnabled(enabled);
	}
	/**
	 * load based on the nls used for this entity
	 *
	 * @param ei
	 */
	public void load(EntityItem ei) {
		if(entityItem != ei){
			entityItem = ei;
			reload();
		}
	}

	/**
	 * reload
	 */
	public void reload() {
		vctNode.clear();
		tm.clear();
		int[] nlsUsed = null;
		if (entityItem != null) {
			nlsUsed = getUsedNLS();
		}
			
		processNLS(nlsUsed);
		
		loadTree();
		tree.expandAll(tm.getRootNode());
		
		setSelection();
	}

	/**
	 * 	 we need to retrieve all the used nls ids from
	 * the uplink and downlink as well as the item
	 * itself....  Not sure if we should only do this
	 * on the RELATOR or if it should happen EVERYWHERE
	 *
	 * @return
	 */
	private int[] getUsedNLS() {
		int[] iOut = null;
		if (entityItem.isNew()) {									
			iOut = new int[0];										
			return iOut;										
		}														
		iOut = entityItem.getInUseNLSIDs();
		if (entityItem.hasDownLinks()) {
			EntityItem tmpEI = (EntityItem)entityItem.getDownLink(0);
			iOut = Routines.combine(iOut,tmpEI.getInUseNLSIDs());
		}
		if (entityItem.hasUpLinks()) {
			EntityItem tmpEI = (EntityItem)entityItem.getUpLink(0);
			iOut = Routines.combine(iOut,tmpEI.getInUseNLSIDs());
		}
		
		return iOut;
	}

	/**
	 * @param nlsUsed
	 */
	@SuppressWarnings("unchecked")
	private void processNLS(int[] nlsUsed) {
		Vector<Integer> vctNLS = new Vector<Integer>();
		Vector<NLSItem> vctRead = new Vector<NLSItem>(editCtrl.getProfile().getReadLanguages());
		Vector<NLSItem> vctWrite = new Vector<NLSItem>(editCtrl.getProfile().getWriteLanguages());

		if(nlsUsed!=null){
			for (int i=0;i<nlsUsed.length;++i) {
				vctNLS.add(new Integer(nlsUsed[i]));
			}
		}
		
		vctRead.removeAll(vctWrite);
		if (vctNLS.isEmpty()) {
			processDefault(vctRead,vctWrite);
		} else {
			processNLS(vctNLS,vctRead,NLS_READ_ONLY);
			processNLS(vctNLS,vctWrite,NLS_WRITE);
		}
		vctNLS.clear();
		vctRead.clear();
		vctWrite.clear();
	}

	/**
	 * no nls have been used yet
	 * @param readVct
	 * @param writeVct
	 */
	private void processDefault(Vector<NLSItem> readVct, Vector<NLSItem> writeVct) {
		NLSItem nlsItem = null;
		while (!readVct.isEmpty()) {
			nlsItem = readVct.remove(0);
			vctNode.add(new NLSNode(nlsItem,NLS_READ_ONLY));
		}
		while (!writeVct.isEmpty()) {
			nlsItem = writeVct.remove(0);
			vctNode.add(new NLSNode(nlsItem,NLS_CREATE));
		}
	}

	/**
	 * @param nlsVct
	 * @param itemVct
	 * @param type
	 */
	private void processNLS(Vector<Integer> nlsVct, Vector<NLSItem> itemVct, int type) {
		if (!nlsVct.isEmpty()) {
			while (!itemVct.isEmpty()) {
				NLSItem nlsItem = itemVct.remove(0);
				Integer iTest = new Integer(nlsItem.getNLSID());
				if (nlsVct.contains(iTest)) {
					nlsVct.remove(iTest);
					vctNode.add(new NLSNode(nlsItem,type));
				} else if (type == NLS_WRITE) {
					vctNode.add(new NLSNode(nlsItem,NLS_CREATE));
				}
			}
		}
	}

	/**
	 *  actually load the tree here....
	 */
	private void loadTree() {
		if (!vctNode.isEmpty()) {
			Collections.sort(vctNode,nodeComparator);
			for (int i=0;i<vctNode.size();++i) {
				tm.addNode(vctNode.elementAt(i));
			}
		}
	}

	/**
	 * setSelection to match the current profile
	 */
	private void setSelection() {
		NLSNode node = tm.getNode(editCtrl.getProfile().getReadLanguage());
		if (node != null) {
			tree.setSelectionPath(new TreePath(node.getPath()));
		}
	}

	/**
	 * release memory
	 */
	public void dereference() {
		removeAll();
		setUI(null);

		nodeComparator = null;
		editCtrl = null;
		entityItem = null;

		tm.dereference();
		tm = null;

		tree.removeMouseListener(mouseListener);
		mouseListener = null;
		tree.removeKeyListener(keyListener);
		keyListener = null;
		
		tree.dereference();
		tree = null;

		vctNode.clear();
		vctNode = null;
	}
	
	/**
	 * @param path
	 */
	private void onClick(TreePath path) {
		editCtrl.onNLSTreeClick(path.getLastPathComponent());
	}

	/**
	 * get width needed for divider location
	 * @return
	 */
	public int getPreferredWidth() {
		Dimension d = tree.getPreferredSize();
		return d.width + 15;
	}
}

