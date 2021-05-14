/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: NavActionTree.java,v $
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.4  2007/03/15 19:28:40  couto
 * Change regarding MN31229572 (mouse issue).
 *
 * Revision 1.3  2007/02/23 19:31:10  couto
 * Changes regarding MN30258877 (mouse issue).
 *
 * Revision 1.2  2005/09/12 19:03:14  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:00  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.8  2005/07/20 19:36:22  tony
 * TIR 6E9S4E
 *
 * Revision 1.7  2005/03/03 21:46:40  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.6  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.3  2005/01/07 18:05:47  tony
 * rm_20050107
 * adjusted logic to allow for menu items and toolbar containers
 * to be populated by an array of keys instead of just a single one.
 *
 * Revision 1.2  2004/08/04 17:49:15  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2003/12/31 16:57:13  tony
 * cr_3312
 *
 * Revision 1.12  2003/12/30 20:41:27  tony
 * cr_3312
 *
 * Revision 1.11  2003/10/29 00:17:52  tony
 * removed test tag
 *
 * Revision 1.10  2003/10/20 16:37:14  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.9  2003/07/14 14:59:40  tony
 * 51433
 *
 * Revision 1.8  2003/06/19 16:09:41  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.7  2003/05/16 17:42:01  tony
 * added convenience methods to the default Tree.  This
 * flows up to other tree classes.
 *
 * Revision 1.6  2003/05/14 21:39:38  tony
 * fixed null pointer when no actiongroup.
 *
 * Revision 1.5  2003/05/14 21:34:39  tony
 * enhanced logic by adding test information.
 *
 * Revision 1.4  2003/04/03 16:19:08  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.3  2003/03/11 00:33:25  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/05 18:54:24  tony
 * accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2002/11/07 16:58:29  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.eforms.navigate.tree.*;
import com.ibm.eannounce.eforms.edit.EditController;
import com.ibm.eannounce.eforms.action.ActionController;
import java.awt.*;
import java.awt.event.*;
import COM.ibm.eannounce.objects.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavActionTree extends EScrollPane implements Accessible,
		NavAction, MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	//	private boolean bTestMode = isTestMode();
	private ActionTreeModel atm = new ActionTreeModel();
	private NavTree tree = new NavTree(atm);
	private Object parent = null;
	private int clicks = -1;
	
	//MN - 30258877
	//These get the mouse position
	private int xPosition = -1;
	private int yPosition = -1;

	/**
     * navActionTree
     * @param _parent
     * @param _clicks
     * @author Anthony C. Liberto
     */
    public NavActionTree(Object _parent, int _clicks) {
		super();
		setViewportView(tree);
		parent = _parent;
		clicks = _clicks;
		tree.setShowsRootHandles(false);
		tree.setShowsHandles(false);
		tree.getAccessibleContext().setAccessibleDescription(getString("accessible.navAction"));//access
		tree.addMouseListener(this);
		tree.addMouseMotionListener(this); //MN - 30258877 - adding MouseMotionListener to get mouse drags
		tree.addKeyListener(this);
		Dimension d = new Dimension(200,200);
		setPreferredSize(d);
		setSize(d);
		setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		return;
	}

	/**
     * getPreferredWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getPreferredWidth() {				//21766
		Dimension d = tree.getPreferredSize();		//21766
		return d.width + 15;						//21766
	}												//21766


	/**
     * @see java.awt.Component#setEnabled(boolean)
     * @author Anthony C. Liberto
     */
    public void setEnabled(boolean _b) {
		super.setEnabled(_b);
		tree.setEnabled(_b);
		return;
	}

	/**
     * getActionItem
     * @author Anthony C. Liberto
     * @return EANActionItem
     * @param _key
     */
    public EANActionItem getActionItem(String _key) {
		return atm.getActionItem(_key);
	}

/*
 rm_20050107
 */
	/**
     * getActionItemArray
     * @author Anthony C. Liberto
     * @return EANActionItem[]
     * @param _key
     */
    public EANActionItem[] getActionItemArray(String _key) {
		return atm.getActionItemArray(_key);
	}

	/**
     * actionExists
     * @param _sAction
     * @return
     * @author Anthony C. Liberto
     */
    public boolean actionExists(String _sAction) {
		return atm.actionExists(_sAction);
	}

	/**
     * @see java.awt.Component#setBackground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setBackground(Color _c) {
		super.setBackground(_c);
		if (tree != null) {
            tree.setBackground(_c);
		}
	}

	/**
     * @see java.awt.Component#setForeground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setForeground(Color _c) {
		super.setForeground(_c);
		if (tree != null) {
            tree.setForeground(_c);
		}
	}

	/**
     * @see java.awt.Component#setFont(java.awt.Font)
     * @author Anthony C. Liberto
     */
    public void setFont(Font _f) {
		super.setFont(_f);
		if (tree != null) {
            tree.setFont(_f);
		}
	}

	/**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     * updated for TIR 6E9S4E
     * MN - 30258877 - Code moved to mouseReleased
     */
	public void mouseClicked(MouseEvent _me) {}

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
     * MN - 30258877 - We are now getting mouse drags and clicks
     */
    public void mouseReleased(MouseEvent _me) {
    	
    	TreePath path = null;

		if (xPosition == -1) { // Single Click - We just get the mouse position
			xPosition = _me.getX();
			yPosition = _me.getY();
			path = tree.getPathForLocation(xPosition, yPosition);
		} else { // Drag and click
			// We will just execute the action if the clicked position and final
			// mouse drag position are in the same action
			int finalDragPosX = _me.getX();
			int finalDragPosY = _me.getY();

			path = tree.getPathForLocation(xPosition, yPosition);
			TreePath aux = tree.getPathForLocation(finalDragPosX, finalDragPosY);

			//MN 31229572 - need to check if path != null first
			if (path != null && !path.equals(aux)) { //If they aren't equal, we have different actions
				path = null;
			}
		}
		
		//clearing x and y positions
		xPosition = -1;
		yPosition = -1;
		
		Object obj = null;
		if (path == null) {
			return;
		}
		obj = path.getLastPathComponent();
		if (obj instanceof TreeNode) {
			TreeNode node = (TreeNode) obj;
			if (_me.getClickCount() == clicks && isEnabled()) {
				if (node.isLeaf()) {
					tree.setSelectionPath(path);
					navigate(path);
					return;
				}
			}
			if (tree.isExpanded(path)) {
				tree.collapsePath(path);
			} else {
				tree.expandPath(path);
			}
		}

		return;
    }
    
	/**
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 * @author Michel do Couto
	 * MN - 30258877
	 */
	public void mouseDragged(MouseEvent _me) {

		//getting the initial mouse drag position
		if (xPosition == -1) {
			xPosition = _me.getX();
			yPosition = _me.getY();
		}
		return;

	}

	/**
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 * @author Michel do Couto
	 */
	public void mouseMoved(MouseEvent _me) {
	}

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
			navigate(path);
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
/*
	private void load(EntityGroup _eg) {
		ActionGroup ag = null;
        if (_eg == null) {
            return;
		}
		ag = _eg.getActionGroup();
		load(_eg.getActionGroupTable(), (ag == null) ? null : ag.getKey());
		return;
	}
*/
	/**
     * load
     *
     * @author Anthony C. Liberto
     * @param _table
     * @param _title
     */
    protected void load(RowSelectableTable _table, String _title) {
		if (_table.getRowCount() == 0) {
			atm.setRootName(getString("noAvail"));
		} else {
			if (_title != null) {
				atm.setRootName(_table.getTableTitle() + " (" + _title + ")");
			} else {
				atm.setRootName(_table.getTableTitle());
			}
		}
		atm.load(_table,false,false);		//sort_remove
		refreshModel();
//cr_3312		expandAll();
		expandAll(getPrefBoolean(PREF_TREE_EXPANDED,DEFAULT_PREF_TREE_EXPANDED));					//cr_3312
		return;
	}

	/**
     * load
     * @param _table
     * @author Anthony C. Liberto
     */
    public void load(RowSelectableTable _table) {
		if (_table.getRowCount() == 0) {
			atm.setRootName(getString("noAvail"));
		} else {
			atm.setRootName(_table.getTableTitle());
		}
		atm.load(_table,false,false);		//sort_remove
		refreshModel();
//cr_3312		expandAll();
		expandAll(getPrefBoolean(PREF_TREE_EXPANDED,DEFAULT_PREF_TREE_EXPANDED));					//cr_3312
		return;
	}

	/**
     * load
     * @author Anthony C. Liberto
     * @param _ean
     * @param _sTitle
     */
    public void load(EANActionItem[] _ean, String _sTitle) {
		int ii = -1;
        clear();
		if (_ean == null) {
			atm.setRootName(getString("noAvail"));
			return;
		}
		ii = _ean.length;
		if (ii == 0) {
			atm.setRootName(getString("noAvail"));
		} else {
			atm.setRootName(_sTitle);
		}
		for (int i=0;i<ii;++i) {
			if (_ean[i] != null && !(_ean[i] instanceof MetaMaintActionItem)) {		//cr_FlagUpdate
				atm.addNode(_ean[i]);
			}																		//cr_FlagUpdate
		}
		refreshModel();
//cr_3312		expandAll();
		expandAll(getPrefBoolean(PREF_TREE_EXPANDED,DEFAULT_PREF_TREE_EXPANDED));					//cr_3312
		setViewPosition(0,0);						//51443
		return;
	}

	private void expandAll() {
		tree.expandAll(atm.getRootNode());
		return;
	}

	private void refreshModel() {
		tree.treeDidChange();
		tree.setSize(tree.getPreferredSize());
//		atm.reload();
//		tree.refreshModel();
		return;
	}


	/**
     * getActionItem
     * @param _path
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem getActionItem(TreePath _path) {
		if (_path != null) {
			Object o = _path.getLastPathComponent();
			if (o != null && o instanceof AbstractNode) {
				return ((AbstractNode)o).getActionItem();
			}
		}
		return null;
	}

    /**
     * getSelectedActionItem
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem getSelectedActionItem(){
		return getActionItem(tree.getSelectionPath());
	}

	private void navigate(TreePath _path) {
		navigate(getActionItem(_path));
	}

    /**
     * navigate
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void navigate(int _i) {
		if (_i >= 0) {
			navigate();
		}
		return;
	}

	private void navigate(EANActionItem _nai) {
		if (_nai != null) {
			if (parent instanceof Navigate) {
				Navigate nav = (Navigate)parent;
				nav.performAction(_nai, Navigate.NAVIGATE_LOAD);
			} else if (parent instanceof EditController) {
				EditController ec = (EditController)parent;
				ec.performAction(_nai, Navigate.NAVIGATE_OTHER);
			} else if (parent instanceof ActionController) {
				ActionController ac = (ActionController)parent;
				ac.performAction(_nai,Navigate.NAVIGATE_OTHER);
			}
		}
		return;
	}

    /**
     * navigate
     *
     * @author Anthony C. Liberto
     */
    public void navigate() {
		navigate(getSelectedActionItem());
	}

    /**
     * getNavigate
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Navigate getNavigate() {
		if (parent instanceof Navigate) {
			return (Navigate)parent;
		}
		return null;
	}

	/**
     * getEditController
     * @return
     * @author Anthony C. Liberto
     */
    public EditController getEditController() {
		if (parent instanceof EditController) {
			return (EditController)parent;
		}
		return null;
	}

	/**
     * getActionController
     * @return
     * @author Anthony C. Liberto
     */
    public ActionController getActionController() {
		if (parent instanceof ActionController) {
			return (ActionController)parent;
		}
		return null;
	}

    /**
     * clear
     *
     * @author Anthony C. Liberto
     */
    public void clear() {		//22862
		atm.clear();			//22862
		return;					//22862
	}							//22862

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		if (tree != null) {
			tree.setRowHeight(getFont());
			tree.repaint();
		}
		revalidate();
		repaint();
		return;
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		clear();				//22862
		parent = null;
		if (tree != null) {
			tree.removeMouseListener(this);
			tree.removeMouseMotionListener(this); //MN - 30258877
			tree.removeKeyListener(this);
			tree.dereference();
			tree.removeAll();
			tree.removeNotify();
			tree = null;
		}
		if (atm != null) {
			atm.dereference();
			atm.clear();
			atm = null;
		}
		removeAll();
		removeNotify();
		return;
	}

/*
 cr_3312
 */
    /**
     * expandAll
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void expandAll(boolean _b) {
		if (_b) {
			expandAll();
		} else {
			collapseAll();
		}
		return;
	}

	private void collapseAll() {
		tree.collapseAll(atm.getRootNode());
		return;
	}
/*
 rm_20050107
 */
	/**
     * ]getActionItemArray
     * @author Anthony C. Liberto
     * @return EANActionItem[]
     * @param _key
     */
    public EANActionItem[] getActionItemArray(String[] _key) {
		return atm.getActionItemArray(_key);
	}
}
