/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: NavDataForm.java,v $
 * Revision 1.3  2008/02/21 19:18:52  wendy
 * Add access to change history for relators
 *
 * Revision 1.2  2008/01/30 16:26:54  wendy
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
 * Revision 1.8  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/09 19:29:50  tony
 * JTest After Scout
 *
 * Revision 1.6  2005/02/09 18:55:23  tony
 * Scout Accessibility
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
 * Revision 1.13  2003/12/22 21:38:16  tony
 * 53451
 *
 * Revision 1.12  2003/08/28 22:54:32  tony
 * memory update
 *
 * Revision 1.11  2003/08/27 17:19:26  tony
 * 51932
 *
 * Revision 1.10  2003/08/06 19:47:00  joan
 * 51449
 *
 * Revision 1.9  2003/06/12 22:23:41  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.8  2003/06/02 16:45:30  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.7  2003/04/18 20:10:28  tony
 * added tab placement to preferences.
 *
 * Revision 1.6  2003/04/03 16:19:09  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.5  2003/04/02 23:16:49  tony
 * 50333
 *
 * Revision 1.4  2003/03/21 20:54:32  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.3  2003/03/11 00:33:25  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/05 18:54:25  tony
 * accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.19  2002/11/07 16:58:29  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import com.ibm.eannounce.eforms.table.NavTable;
import com.ibm.eannounce.exception.*;
import COM.ibm.eannounce.objects.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavDataForm extends HashMap implements NavData, FocusListener {
	private static final long serialVersionUID = 1L;
	private Navigate parent = null;
	private EntityList eList = null;
	private NavDataSelector tabSelector = null;
//	private boolean enabled = true;
	private Font myFont = null;
	private int indx = -1;
	private Vector v = new Vector();
	private transient MouseListener mouseL = null;
	private boolean selOnForm = false;
	private String strName = "navDataForm";

	/**
     * navDataForm
     * @param _parent
     * @author Anthony C. Liberto
     */
    public NavDataForm(Navigate _parent) {
		super();
		parent = _parent;
		setMouseListener(parent.getPopupMenu());
		tabSelector = new NavDataSelector(this,1);
		initAccessibility("accessible.navData");
		return;
	}

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

    /**
     * updateTabPlacement
     *
     * @param _revalidate
     * @author Anthony C. Liberto
     */
    public void updateTabPlacement(boolean _revalidate) {
		if (tabSelector != null) {
			tabSelector.updateTabPlacement(_revalidate);
		}
		return;
	}

    /**
     * getAccessibleContext
     *
     * @return
     * @author Anthony C. Liberto
     */
    public AccessibleContext getAccessibleContext() {
		return tabSelector.getAccessibleContext();
	}

	/**
     * setName
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setName(String _s) {
		strName = new String(_s);
	}

    /**
     * getName
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getName() {
		return strName;
	}

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
     * addMouseListener
     * @author Anthony C. Liberto
     * @param _ml
     */
    public void addMouseListener(MouseListener _ml) {			//21893
		tabSelector.addMouseListener(_ml);						//21893
	}															//21893

	/**
     * removeMouseListener
     * @author Anthony C. Liberto
     * @param _ml
     */
    public void removeMouseListener(MouseListener _ml) {		//21893
		tabSelector.removeMouseListener(_ml);					//21893
	}															//21893


	/**
     * navDataForm
     * @param _eList
     * @param _reload
     * @author Anthony C. Liberto
     */
    public NavDataForm(EntityList _eList, boolean _reload) {
		super();
		tabSelector = new NavDataSelector(this,1);
		load(_eList,_reload);
		return;
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
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusGained(FocusEvent _fe) {
		Object o = _fe.getSource();
		setSelectedIndex(getIndexOf(((NavTable)o).getEntityGroup()));
		return;
	}
	/**
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     * @author Anthony C. Liberto
     */
    public void focusLost(FocusEvent _fe) {}

	/**
     * setSelectedIndex
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setSelectedIndex(int _i) {
		indx = _i;
		if (parent != null) {
			parent.stateChanged();
		}
		return;
	}

	/**
     * getSelectedIndex
     * @return
     * @author Anthony C. Liberto
     */
    public int getSelectedIndex() {
		return indx;
	}

    /**
     * setVisible
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setVisible(boolean _b) {}

	/**
     * isVisible
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isVisible() {return true;}

    /**
     * setEnabled
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEnabled(boolean _b) {}
	/**
     * isEnabled
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEnabled() {return true;}
	/**
     * setFont
     * @author Anthony C. Liberto
     * @param _f
     */
    public void setFont (Font _f) {myFont = _f;}

    /**
     * getFont
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Font getFont() {return myFont;}

	/**
     * load
     * @author Anthony C. Liberto
     * @return EntityGroup
     * @param _eList
     * @param _reload
     */
    public EntityGroup load(EntityList _eList, boolean _reload) {
        RowSelectableTable rst = null;
        EANFoundation[] ean = null;
        int ii = -1;
        int x = 0;
        clear();
		v.clear();
		rst = _eList.getTable();
		if (tabSelector != null) {
			tabSelector.clear();
			tabSelector.load(rst);
		}
		eList = _eList;
		if (_eList == null) {
			return null;
		}
		ean = getSortedArray(rst);
		ii = ean.length;
		for (int i=0;i<ii;++i) {
			EntityGroup eg = _eList.getEntityGroup(ean[i].getKey());
			if (eg.isDisplayable()) {
				addTab(eg,x++,_reload);
			}
		}
		return getEntityGroup(0);
	}

	private EANFoundation[] getSortedArray(RowSelectableTable _table) {
		EANFoundation[] ean = _table.getTableRowsAsArray();
		Arrays.sort(ean,new EComparator(true));
		return ean;
//		int ii = _eList.getEntityGroupCount();
//		EntityGroup[] eg = new EntityGroup[ii];
//		for (int i=0;i<ii;++i)
//			eg[i] = _eList.getEntityGroup(i);
//		Arrays.sort(eg,new OComparator(true));
//		return eg;
	}

    /**
     * getAllEntityItems
     *
     * @param _new
     * @param _bEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getAllEntityItems(boolean _new, boolean _bEx) throws OutOfRangeException {
		return getAllEntityItems(getSelectedIndex(), _new, _bEx);
	}

    /**
     * getAllEntityItems
     *
     * @param _i
     * @param _new
     * @param _bEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getAllEntityItems(int _i, boolean _new, boolean _bEx) throws OutOfRangeException {
		NavTable nt = null;
        if (_i < 0) {
            return null;
		}
		nt = getTable(_i);
		if (nt != null) {
			return (EntityItem[])nt.getVisibleObjects(_new, _bEx);
		}
		return null;
	}

    /**
     * getSelectedEntityItems
     *
     * @param _new
     * @param _bEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getSelectedEntityItems(boolean _new, boolean _bEx) throws OutOfRangeException {
		return getSelectedEntityItems(getSelectedIndex(), _new, _bEx);
	}

    /**
     * getSelectedEntityItems
     *
     * @param _i
     * @param _new
     * @param _bEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getSelectedEntityItems(int _i, boolean _new, boolean _bEx) throws OutOfRangeException {
		NavTable nt = null;
        if (_i < 0) {
            return null;
		}
		nt = getTable(_i);
		if (nt != null) {
			return (EntityItem[])nt.getSelectedObjects(_new, _bEx);
		}
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
    public void setSelectedIndex(String _key) {
	}

	/**
     * gotoTab
     * @author Anthony C. Liberto
     * @return boolean
     * @param _s
     */
    public boolean gotoTab(String _s) {
		int ii = v.size();
		for (int i=0;i<ii;++i) {
			EntityGroup eg = getEntityGroup(i);
			String desc = eg.getEntityType().toUpperCase();
			if (desc.startsWith(_s.toUpperCase())) {
				setSelectedIndex(eg.getKey());
				return true;
			}
		}
		return false;
	}

	/**
     * removeTab
     * @author Anthony C. Liberto
     * @param _key
     */
    public void removeTab(String _key) {}

	/**
     * getIndexOf
     * @author Anthony C. Liberto
     * @return int
     * @param _eg
     */
    public int getIndexOf(EntityGroup _eg) {
		String key = _eg.getKey();
		if (containsKey(key)) {
			Object o = get(key);
			return v.indexOf(o);
		}
		return -1;
	}

	/**
     * getTable
     * @author Anthony C. Liberto
     * @return NavTable
     * @param _key
     */
    public NavTable getTable(String _key) {
		NavTable nt = getTable(getIndexOf(_key));
		if (nt != null) {
			return nt;
		}
		return null;
	}

	/**
     * requestFocus
     * @author Anthony C. Liberto
     * @param _eg
     */
    public void requestFocus(EntityGroup _eg) {
		NavTable nt = getTable(_eg.getKey());
		if (nt != null) {
            nt.requestFocus();
		}
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
		Component c = null;
        if (_i >= size() || _i < 0) {
            return null;
		}
		c = (Component)v.get(_i);
		if (c != null && c instanceof NavTable) {
			return (NavTable)c;
		}
		return null;
	}

    /**
     * export
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String export() {
		return getTable().export();
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
		NavTable nt = getTable(_i);
		if (nt != null) {
			return nt.getEntityGroup();
		}
		return null;
	}

	/**
     * getEntityGroup
     * @author Anthony C. Liberto
     * @return EntityGroup
     * @param _key
     */
    public EntityGroup getEntityGroup(String _key) {
		return eList.getEntityGroup(_key);
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
     * addTab
     * @param _eg
     * @author Anthony C. Liberto
     */
    public void addTab(EntityGroup _eg) {
		NavTable nt = new NavTable(_eg, _eg.getEntityGroupTable());
		nt.setFont(getFont());
		nt.addMouseListener(mouseL);
		if (parent != null) {
			nt.addPropertyChangeListener(parent);
			nt.setNavigate(parent);		//51449
		}
		nt.addFocusListener(this);
		put(_eg.getKey(), nt);
		v.add(nt);
	}

	/**
     * removeListeners
     * @author Anthony C. Liberto
     */
    public void removeListeners() {
		int ii = v.size();
		for (int i=0;i<ii;++i) {
			NavTable nt = getTable(i);
			if (nt != null && parent != null) {
				nt.removePropertyChangeListener(parent);
			}
		}
		return;
	}

	private void addTab(EntityGroup _eg, int _index, boolean _reload) {
		addTab(_eg);
		return;
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
    public void refreshTab(EntityGroup _eg,boolean _reload) {
		int index = getIndexOf(_eg);
		if (index >= 0) {
			v.remove(index);
			remove(_eg.getKey());
			addTab(_eg,index,_reload);
		} else {
			int i = getIndexFor(_eg);
			addTab(_eg,i,_reload);
		}
		return;
	}

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
		int ii = eList.getEntityGroupCount();
		for (int i=0;i<ii;++i) {
			EntityGroup eg = getEntityGroup(i);
			if (eg.getKey().equals(_s)) {
				return i;
			}
		}
		return -1;
	}

	private int getIndexFor(EntityGroup _eg) {
		return getIndexFor(_eg.toString());
	}

	private int getIndexFor(String _title) {
		int ii = eList.getEntityGroupCount();
		int iLower = 0;
		int iCur = 0;
		for (int i=0;i<ii;++i) {
			EntityGroup eg = getEntityGroup(i);
			iCur = _title.compareToIgnoreCase(eg.toString());
			if (iCur == 0) {
				return i;
			} else if (iCur > 0) {
				iLower = i + 1;
			} else if (iCur < 0) {
				return iLower;
			}
		}
		return ii;
	}

    /**
     * getTabSelector
     *
     * @return
     * @author Anthony C. Liberto
     */
    public NavDataSelector getTabSelector() {
		return tabSelector;
	}
/*
	private String getTitle(EntityGroup _eg) {
		String s = null;
        int i = -1;
		s = _eg.toString();
		i = _eg.getEntityItemCount();
		if (i == 0) {
			return routines.truncate(s,20);
		}
		return routines.truncate(s,20) + " (" + i + ")";
	}
*/
    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		initAccessibility(null);
		removeListeners();
		parent = null;
		if (eList != null) {
			eList.dereference();
			eList = null;
		}
		tabSelector.removeAll();
		v.clear();
		clear();
		return;
	}

    /**
     * getCurrentEntityItem - based on row only
     *
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem getCurrentEntityItem(boolean _new) {
		NavTable nt = getTable();
		if (nt == null) {
            return null;
		}
		return nt.getCurrentEntityItem(_new);
	}
	/**
     * getCurrentEntityItem - gets it based on row and column
     */
    public EntityItem getCurrentEntityItem() {
		NavTable nt = getTable();
		if (nt == null) {
            return null;
		}
		return nt.getCurrentEntityItem();   	
    }
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

    /**
     * getEntityGroupCount
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getEntityGroupCount() {
		return 1;
	}

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		return;
	}

	/**
     * addFocusListener
     * @author Anthony C. Liberto
     * @param _fl
     */
    public void addFocusListener(FocusListener _fl) {}

	/**
     * removeFocusListener
     * @author Anthony C. Liberto
     * @param _fl
     */
    public void removeFocusListener(FocusListener _fl){}

/*
 1.2h
*/
    /**
     * isShowing
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isShowing() {
		for (int i=0;i<v.size();++i) {
			Component c = (Component)v.get(i);
			if (c != null && c.isShowing()) {
				return true;
			}
		}
		return false;
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

/*
 accessibility
 */
	/**
     * initAccessibility
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void initAccessibility(String _s) {
		if (EAccess.isAccessible()) {
			AccessibleContext ac = getAccessibleContext();
			String strAccessible = null;
			if (ac != null) {
				if (_s == null) {
					ac.setAccessibleName(null);
					ac.setAccessibleDescription(null);
				} else {
					strAccessible = eaccess().getString(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
		}
		return;
	}
}

