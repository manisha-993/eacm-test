//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.tree;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.MetaMaintActionItem;

import com.ibm.eacm.actions.EANAction;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.tabs.DataActionPanel;
import com.ibm.eacm.nav.Navigate;

/**
 * this is used for the action tree, it is in a scroll pane.
 * @author Wendy Stimpson
 */
//$Log: ActionTreeScroll.java,v $
//Revision 1.3  2014/10/27 19:33:19  wendy
//Set actions as 'none available' if no tab is added (entitygroup is null)
//
//Revision 1.2  2013/07/18 18:38:04  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class ActionTreeScroll extends JScrollPane implements
MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private ActionTreeModel atm = new ActionTreeModel();
	private ActionTree tree = new ActionTree(atm);
	private DataActionPanel parent = null;
	private int clicks = -1;
	private String parentKey;

	//These get the mouse position
	private int xPosition = -1;
	private int yPosition = -1;

	/**
	 * constructor
	 * @param par
	 * @param clcks
	 */
	public ActionTreeScroll(DataActionPanel par, int clcks) {
		setViewportView(tree);
		parent = par;
		clicks = clcks;
		tree.setShowsRootHandles(false);
		tree.getAccessibleContext().setAccessibleDescription(Utils.getResource("accessible.navAction"));//access
		tree.addMouseListener(this);
		tree.addMouseMotionListener(this);
		tree.addKeyListener(this);

		Dimension d = new Dimension(200,200);
		setPreferredSize(d);
		setSize(d);
		setMinimumSize(UIManager.getDimension("eannounce.minimum"));
	}

	/**
	 * getPreferredWidth
	 * @return
	 */
	public int getPreferredWidth() {
		Dimension d = tree.getPreferredSize();
		return d.width + 15;
	}

	/**
	 * @see java.awt.Component#setEnabled(boolean)
	 */
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		tree.setEnabled(b);
	}

	/**
	 * getActionItemArray
	 * @return EANActionItem[]
	 * @param key
	 */
	public EANActionItem[] getActionItemArray(String key) {
		return atm.getActionItemArray(key);
	}

	/**
	 * actionExists
	 * @param sAction
	 * @return
	 */
	public boolean actionExists(String sAction) {
		return atm.actionExists(sAction);
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * MN - 30258877 - Code moved to mouseReleased
	 */
	public void mouseClicked(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 * MN - 30258877 - We are now getting mouse drags and clicks
	 */
	public void mouseReleased(MouseEvent me) {

		TreePath path = null;

		if (xPosition == -1) { // Single Click - We just get the mouse position
			xPosition = me.getX();
			yPosition = me.getY();
			path = tree.getPathForLocation(xPosition, yPosition);
		} else { // Drag and click
			// We will just execute the action if the clicked position and final
			// mouse drag position are in the same action
			int finalDragPosX = me.getX();
			int finalDragPosY = me.getY();

			path = tree.getPathForLocation(xPosition, yPosition);
			TreePath aux = tree.getPathForLocation(finalDragPosX, finalDragPosY);

			//MN 31229572 - need to check if path != null first, if user moves the mouse they may not be the same
			if (path != null && !path.equals(aux)) { //If they aren't equal, we have different actions
				path = null;
				me.consume();
			}
		}

		//clearing x and y positions
		xPosition = -1;
		yPosition = -1;

		if (path != null) {
			Object obj = path.getLastPathComponent();
			if (obj instanceof TreeNode) {
				TreeNode node = (TreeNode) obj;
				if (me.getClickCount() == clicks && isEnabled()) {
					if (node.isLeaf()) {
						tree.setSelectionPath(path);
						navigate(getActionItem(node));
						me.consume();
						return;
					}
				}
				if (tree.isExpanded(path)) {
					tree.collapsePath(path);
				} else {
					if (((DefaultMutableTreeNode)node).getChildCount() > 0){
						// make sure the last node is visible in the viewport
						TreeNode lastnode = ((DefaultMutableTreeNode)node).getLastChild();
						tree.scrollPathToVisible(new TreePath(atm.getPathToRoot(lastnode)));
						//make sure the parent node is still visible
						tree.scrollPathToVisible(path);
					}
				}
			}
		}
	}

	/**
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 * MN - 30258877
	 */
	public void mouseDragged(MouseEvent me) {
		//getting the initial mouse drag position
		if (xPosition == -1) {
			xPosition = me.getX();
			yPosition = me.getY();
		}
	}

	/**
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me) {}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			TreePath path = tree.getSelectionPath();
			if (path != null) {
				Object obj = path.getLastPathComponent();
				if (obj instanceof TreeNode) {
					TreeNode node = (TreeNode) obj;
					if(node.isLeaf()){
						navigate(getActionItem(node));
					}else{
						if (tree.isExpanded(path)) {
							tree.collapsePath(path);
						} else {
							// make sure the last node is visible in the viewport
							TreeNode lastnode = ((DefaultMutableTreeNode)node).getLastChild();
						    tree.scrollPathToVisible(new TreePath(atm.getPathToRoot(lastnode)));
						    //make sure the parent node is still visible
						    tree.scrollPathToVisible(path);
						}
					}
				}
				ke.consume();
			}
		}
	}

	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}


	/**
	 * load
	 * @param ean
	 * @param sTitle
	 */
	public void load(EANActionItem[] ean, String sTitle) {
		load(ean, sTitle, null);
	}

	/**
	 * load
	 * @param ean
	 * @param sTitle
	 * @param key
	 */
	public void load(EANActionItem[] ean, String sTitle, String key) {
		int ii = 0;
		saveExpansionState();

		parentKey = key;
		clear();
		if (ean == null || ean.length == 0) {
			noneAvail();
		} else {
			ii = ean.length;
			atm.setRootName(sTitle);
		}

		for (int i=0;i<ii;++i) {
			if (ean[i] != null && !(ean[i] instanceof MetaMaintActionItem)) {
				atm.addNode(ean[i]);
			}
		}
		tree.treeDidChange();
		tree.setSize(tree.getPreferredSize());

		expandTree();

		getViewport().setViewPosition(new Point(0, 0));
	}

	/**
	 * expand the tree based on previous twistie or save preferences
	 * @param parentKey
	 */
	@SuppressWarnings("unchecked")
	private void expandTree() {
		boolean loadedPref = false;
		if(parentKey !=null && BehaviorPref.useSavedTwisties()){
			// try to use any saved tree expansion
			Vector<String> treeVct = (Vector<String>)SerialPref.getPref(parentKey);
			if(treeVct !=null){
				loadedPref = ActionTree.loadExpansionState(tree, (Vector<String>)treeVct);
			}
		}
		if(!loadedPref){
			if (BehaviorPref.isTreeExpanded()) {
				tree.expandAll(atm.getRootNode());
			} else {
				tree.collapseAll(atm.getRootNode());
			}
		}
	}

	private void saveExpansionState(){
		if(parentKey !=null){
			Vector<String> tpVct = ActionTree.getExpansionState(tree);
			if(tpVct!=null){
				SerialPref.putPref(parentKey, tpVct);
			}
		}
	}

	/**
	 * getActionItem
	 * @param node
	 * @return
	 */
	private EANActionItem getActionItem(TreeNode node) {
		if (node instanceof AbstractNode) {
			return ((AbstractNode)node).getActionItem();
		}
		return null;
	}

	private void navigate(EANActionItem nai) {
		if (nai != null) {
			EANAction act = parent.getEANAction(nai.getActionItemKey());
			if(act!=null){
				if(!act.isSetEnabled()){
					UIManager.getLookAndFeel().provideErrorFeedback(null);
					return;
				}
				if (parent instanceof Navigate) {
					// do not allow focus to follow focus traversal or nav[1].history will get focus when nav[0] tree executes
					com.ibm.eacm.EACM.getEACM().requestFocusInWindow();
				}
				act.actionPerformed(null);
				return;
			}else{
				UIManager.getLookAndFeel().provideErrorFeedback(null);
			}
		}
	}
	/**
	 * clear
	 *
	 */
	public void clear() {
		atm.clear();
	}
	
	/**
	 * use none available message
	 */
	public void noneAvail(){
		//noAvail = None Available
		atm.setRootName(Utils.getResource("noAvail"));
		atm.reload();
	}

	/**
	 * dereference
	 *
	 */
	public void dereference() {
		saveExpansionState();

		parentKey = null;
		clear();
		parent = null;
		if (tree != null) {
			tree.removeMouseListener(this);
			tree.removeMouseMotionListener(this);
			tree.removeKeyListener(this);
			tree.dereference();
			tree = null;
		}
		if (atm != null) {
			atm.dereference();
			atm = null;
		}
		removeAll();
	}

	/**
	 * getActionItemArray
	 * @return EANActionItem[]
	 * @param key
	 */
	public EANActionItem[] getActionItemArray(String[] key) {
		return atm.getActionItemArray(key);
	}
}
