/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: NavDataTree.java,v $
 * Revision 1.3  2008/02/21 19:18:51  wendy
 * Add access to change history for relators
 *
 * Revision 1.2  2008/01/30 16:26:55  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:14  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:01  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/11/08 19:01:40  tony
 * Improved sort logic to no longer sort multiple times on a
 * navigation reload.
 *
 * Revision 1.2  2004/03/25 23:37:19  tony
 * cr_216041310
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/12/22 21:38:16  tony
 * 53451
 *
 * Revision 1.7  2003/08/27 17:19:26  tony
 * 51932
 *
 * Revision 1.6  2003/04/18 20:10:28  tony
 * added tab placement to preferences.
 *
 * Revision 1.5  2003/04/11 20:02:30  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.ibm.eannounce.eforms.navigate.tree.*;
import com.ibm.eannounce.eforms.table.NavTable;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;

import java.awt.event.*;
import javax.swing.UIManager;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavDataTree extends EScrollPane implements NavData {
	private static final long serialVersionUID = 1L;
	private EListTreeModel etm = new EListTreeModel();
	private NavTree tree = new NavTree(etm);

	/**
     * parent
     */
    protected Navigate parent = null;
	/**
     * eList
     */
    protected EntityList eList = null;
	private transient MouseListener mouseL = null;
	private boolean selOnForm = false;

	/**
     * navDataTree
     * @param _parent
     * @author Anthony C. Liberto
     */
    public NavDataTree(Navigate _parent) {
		super();
		setViewportView(tree);
		parent = _parent;
		setMouseListener(parent.getPopupMenu());
		tree.setShowsRootHandles(false);
		tree.setShowsHandles(false);
		tree.addMouseListener(mouseL);				//21893
		setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		return;
	}

	/**
     * navDataTree
     * @param _parent
     * @param _eList
     * @author Anthony C. Liberto
     */
    public NavDataTree(Navigate _parent, EntityList _eList) {
		super();
		setViewportView(tree);
		parent = _parent;
		load(_eList,false);
		tree.setShowsRootHandles(false);
		tree.setShowsHandles(false);
		setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		return;
	}

    /**
     * updateTabPlacement
     *
     * @param _revalidate
     * @author Anthony C. Liberto
     */
    public void updateTabPlacement(boolean _revalidate) {}

    /**
     * getNavigate
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Navigate getNavigate() {
		return parent;
	}

	/**
     * setMouseListener
     * @author Anthony C. Liberto
     * @param _l
     */
    public void setMouseListener(MouseListener _l) {
		mouseL = _l;
	}

    /**
     * getMouseListener
     *
     * @return
     * @author Anthony C. Liberto
     */
    public MouseListener getMouseListener() {
		return mouseL;
	}

	/**
     * load
     * @author Anthony C. Liberto
     * @return EntityGroup
     * @param _eList
     * @param _reload
     */
    public EntityGroup load(EntityList _eList,boolean _reload) {
		etm.load(_eList);
		return _eList.getEntityGroup(0);
	}

    /**
     * getAllEntityItems
     *
     * @param _new
     * @param _bEx
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getAllEntityItems(boolean _new, boolean _bEx) {
		return getSelectedEntityItems(_new, _bEx);
	}

    /**
     * getAllEntityItems
     *
     * @param _i
     * @param _new
     * @param _bEx
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getAllEntityItems(int _i, boolean _new, boolean _bEx) {
		return getSelectedEntityItems(_i,_new, _bEx);
	}

    /**
     * getSelectedEntityItems
     *
     * @param _new
     * @param _bEx
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getSelectedEntityItems(boolean _new, boolean _bEx) {
		return getSelectedEntityItems(getSelectedIndex(), _new, _bEx);
	}

    /**
     * getSelectedEntityItems
     *
     * @param _i
     * @param _new
     * @param _bEx
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getSelectedEntityItems(int _i, boolean _new, boolean _bEx) {
		return null;
	}

    /**
     * getCurrentEntityItem
     *
     * @param _b
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem getCurrentEntityItem(boolean _b) {
		return null;
	}
	/**
     * getCurrentEntityItem - gets it based on row and column
     */
    public EntityItem getCurrentEntityItem() {
        return null; 	
    }
	/**
     * setSelected
     * @author Anthony C. Liberto
     * @param _key
     * @param _keys
     */
    public void setSelected(String _key, String[] _keys) {
		NavTable nt = null;
        setSelectedIndex(_key);
		nt = getTable(_key);
		if (nt != null) {
			nt.highlight(_keys);
		}
		return;
	}

	/**
     * setSelectedIndex
     * @param _key
     * @author Anthony C. Liberto
     */
    public void setSelectedIndex(String _key) {}

	/**
     * removeTab
     * @author Anthony C. Liberto
     * @param _s
     */
    public void removeTab(String _s) {}

	/**
     * getIndexOf
     * @author Anthony C. Liberto
     * @return int
     * @param _eg
     */
    public int getIndexOf(EntityGroup _eg) {
		return -1;
	}

	/**
     * removeListeners
     * @author Anthony C. Liberto
     */
    public void removeListeners() {}

	/**
     * getTable
     * @author Anthony C. Liberto
     * @return NavTable
     * @param _key
     */
    public NavTable getTable(String _key) {
		return null;
	}

	/**
     * requestFocus
     * @author Anthony C. Liberto
     * @param _eg
     */
    public void requestFocus(EntityGroup _eg) {
		return;
	}

    /**
     * getTable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public NavTable getTable() {
		return getTable(getSelectedIndex());
	}

    /**
     * getTable
     *
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public NavTable getTable(int _i) {
		return null;
	}

    /**
     * export
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String export() {
		return null;
	}

    /**
     * getEntityGroup
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getEntityGroup() {
		return getEntityGroup(getSelectedIndex());
	}

    /**
     * getEntityGroup
     *
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getEntityGroup(int _i) {
		return null;
	}

	/**
     * getEntityGroup
     * @author Anthony C. Liberto
     * @return EntityGroup
     * @param _s
     */
    public EntityGroup getEntityGroup(String _s) {
		return eList.getEntityGroup(_s);
	}

    /**
     * getEntityGroupKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getEntityGroupKey() {
		EntityGroup eg = getEntityGroup();
		if (eg != null) {
			return eg.getKey();
		}
		return null;
	}

    /**
     * getMatrixActionItem
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem getMatrixActionItem() {
		return getMatrixActionItem(getSelectedIndex());
	}

    /**
     * getMatrixActionItem
     *
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem getMatrixActionItem(int _i) {
		return null;
	}

	/**
     * refreshTitle
     * @author Anthony C. Liberto
     * @param _eg
     */
    public void refreshTitle(EntityGroup _eg) {}

	/**
     * refreshTab
     * @author Anthony C. Liberto
     * @param _eg
     * @param _reload
     */
    public void refreshTab(EntityGroup _eg,boolean _reload) {}

    /**
     * toggleTab
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void toggleTab(int _i) {}

	/**
     * getIndexOf
     * @author Anthony C. Liberto
     * @return int
     * @param _s
     */
    public int getIndexOf(String _s) {
		return -1;
	}
/*
	private int getIndexFor(EntityGroup _eg) {
		return getIndexFor(_eg.toString());
	}
*/
    /**
     * getIndexFor
     *
     * @param _title
     * @return
     * @author Anthony C. Liberto
     */
    protected int getIndexFor(String _title) {
		return -1;
	}

    /**
     * getTabSelector
     *
     * @return
     * @author Anthony C. Liberto
     */
    public NavDataSelector getTabSelector() {
		return null;
	}

	/**
     * gotoTab
     * @author Anthony C. Liberto
     * @return boolean
     * @param _s
     */
    public boolean gotoTab(String _s) {
		return false;
	}
/*
	private String getTitle(EntityGroup _eg) {
		return null;
	}
*/
    /**
     * isSelectorOnForm
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSelectorOnForm() {
		return selOnForm;
	}

    /**
     * setSelectorOnForm
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSelectorOnForm(boolean _b) {
		selOnForm = _b;
		return;
	}

	private int getSelectedIndex() {
		return -1;
	}

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
		tree.addMouseListener(mouseL);				//21893
	}

    /**
     * getEntityGroupCount
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getEntityGroupCount() {
		return 1;
	}

/*
 51932
 */
	/**
     * addChangeListener
     * @author Anthony C. Liberto
     * @param _cl
     */
    public void addChangeListener(javax.swing.event.ChangeListener _cl) {}
	/**
     * removeChangeListener
     * @author Anthony C. Liberto
     * @param _cl
     */
    public void removeChangeListener(javax.swing.event.ChangeListener _cl) {}

/*
 53451
 */
    /**
     * getFilterGroup
     *
     * @return
     * @author Anthony C. Liberto
     */
    public FilterGroup getFilterGroup() {
		return null;
	}

	/**
     * setFilterGroup
     * @author Anthony C. Liberto
     * @param _fg
     */
    public void setFilterGroup(FilterGroup _fg) {}

    /**
     * refresh
     *
     * @author Anthony C. Liberto
     */
    public void refresh() {
		NavTable nt = getTable();
		if (nt != null) {
			nt.refresh();
		}
		return;
	}

    /**
     * sort
     *
     * @author Anthony C. Liberto
     */
    public void sort() {
		NavTable nt = getTable();
		if (nt != null) {
			nt.sort();
		}
		return;
	}

}
