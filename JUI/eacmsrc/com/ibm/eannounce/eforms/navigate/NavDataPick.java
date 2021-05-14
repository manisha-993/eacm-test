/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: NavDataPick.java,v $
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
 * Revision 1.14  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.13  2005/02/09 19:29:50  tony
 * JTest After Scout
 *
 * Revision 1.12  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.11  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.10  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.9  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.8  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.7  2004/12/14 23:33:32  tony
 * cr_2115.2
 *
 * Revision 1.6  2004/11/24 19:01:18  tony
 * gb_20041124 -- added entitydata in testmode to search
 * and picklist functions.
 *
 * Revision 1.5  2004/11/24 17:16:25  tony
 * added braackets to code.
 *
 * Revision 1.4  2004/11/16 22:25:01  tony
 * improved sorting and resizing logic to improve table
 * performance.
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
 * Revision 1.12  2003/10/31 17:30:49  tony
 * 52783
 *
 * Revision 1.11  2003/08/19 15:23:03  tony
 * 51813
 *
 * Revision 1.10  2003/06/03 19:51:53  tony
 * 51052
 *
 * Revision 1.9  2003/06/02 16:45:30  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.8  2003/05/09 16:51:28  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.7  2003/05/07 15:55:48  tony
 * 24306
 *
 * Revision 1.6  2003/04/11 20:02:30  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import com.ibm.eannounce.eforms.action.*;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.exception.*;
import COM.ibm.eannounce.objects.*;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavDataPick extends ETabbedPane implements EAccessConstants, NavData, ActionListener {
	private static final long serialVersionUID = 1L;
	/**
     * entityList
     */
    protected EntityList eList = null;
    private EPopupMenu popup = new EPopupMenu("popup");
    private Navigate parent = null;
    private MatrixAction matrix = null;
    private UsedAction used = null;
    private boolean selOnForm = false;
    private InterfaceDialog id = null;
    private HashMap mapComparator = new HashMap(); //51052
    private Dimension dSize = null;

    /**
     * navDataPick
     * @param _parent
     * @author Anthony C. Liberto
     */
    public NavDataPick(Navigate _parent) {
        super();
        parent = _parent;
        createPopupMenu();
        dSize = new Dimension(200, 200);
        setPreferredSize(dSize);
        setSize(dSize);
        addMouseListener(popup);
        initAccessibility("accessible.navData");
        return;
    }

    /**
     * navDataPick
     * @param _matrix
     * @author Anthony C. Liberto
     */
    public NavDataPick(MatrixAction _matrix) {
        super();
        matrix = _matrix;
        createPopupMenu();
        setFont(_matrix.getFont());
        dSize = new Dimension(200, 200);
        setPreferredSize(dSize);
        setSize(dSize);
        addMouseListener(popup);
        initAccessibility("accessible.navData");
        return;
    }

    /**
     * navDataPick
     * @param _used
     * @author Anthony C. Liberto
     */
    public NavDataPick(UsedAction _used) {
        super();
        used = _used;
        createPopupMenu();
        setFont(_used.getFont());
        dSize = new Dimension(200, 200);
        setPreferredSize(dSize);
        setSize(dSize);
        addMouseListener(popup);
        initAccessibility("accessible.navData");
        return;
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
     * getMatrix
     * @return
     * @author Anthony C. Liberto
     */
    public MatrixAction getMatrix() {
        return matrix;
    }

    /**
     * getWhereUsed
     * @return
     * @author Anthony C. Liberto
     */
    public UsedAction getWhereUsed() {
        return used;
    }

    /**
     * setMouseListener
     * @author Anthony C. Liberto
     * @param _l
     */
    public void setMouseListener(MouseListener _l) {
    }

    /**
     * getMouseListener
     *
     * @return
     * @author Anthony C. Liberto
     */
    public MouseListener getMouseListener() {
        return null;
    }

    /**
     * getPopup
     * @return
     * @author Anthony C. Liberto
     */
    public EPopupMenu getPopup() {
        return popup;
    }

    /**
     * createPopupMenu
     * @author Anthony C. Liberto
     */
    protected void createPopupMenu() {
        popup.addPopupMenu("srt", "srt", this);
        popup.addSeparator();
        popup.addPopupMenu("f/r", "f/r", this);
        popup.addPopupMenu("fltr", "fltr", this);
        popup.addSeparator();
        popup.addPopupMenu("selA", "selA", this);
        popup.addPopupMenu("iSel", "iSel", this);
        return;
    }

    /**
     * removePopupMenu
     * @author Anthony C. Liberto
     */
    protected void removePopupMenu() {
        popup.removeMenu("srt", this);
        popup.removeMenu("f/r", this);
        popup.removeMenu("fltr", this);
        popup.removeMenu("selA", this);
        popup.removeMenu("iSel", this);
        return;
    }

    /**
     * setInterfaceDialog
     * @param _id
     * @author Anthony C. Liberto
     */
    public void setInterfaceDialog(InterfaceDialog _id) { //24306
        id = _id; //24306
        return; //24306
    } //24306

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        actionPerformed(_ae.getActionCommand());
        return;
    }

    /**
     * actionPerformed
     * @param _action
     * @author Anthony C. Liberto
     */
    public void actionPerformed(String _action) {
        if (_action == null) {
            return;
        } else if (_action.equals("srt")) {
            sort();
        } else if (_action.equals("f/r")) {
            find();
        } else if (_action.equals("fltr")) {
            filter();
        } else if (_action.equals("selA")) {
            selectAll();
        } else if (_action.equals("iSel")) {
            invertSelection();
        } else if (_action.equals("eData")) { //gb_20041124
            getInformation(); //gb_20041124
        } //gb_20041124
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
            nt.showSort(id);
        }
    }

    private void find() {
        NavTable nt = getTable();
        if (nt != null) {
            nt.showFind(id);
        }
    }

    private void filter() {
        NavTable nt = getTable();
        if (nt != null) {
            nt.showFilter(id);
        }
    }

    private void selectAll() {
        NavTable nt = getTable();
        if (nt != null) {
            nt.selectAll();
        }
    }

    private void invertSelection() {
        NavTable nt = getTable();
        if (nt != null) {
            nt.invertSelection();
        }
    }

    /**
     * getEntityList
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList getEntityList() {
        return eList;
    }

    /**
     * load
     * @author Anthony C. Liberto
     * @return EntityGroup
     * @param _eList
     * @param _reload
     */
    public EntityGroup load(EntityList _eList, boolean _reload) {
        EANFoundation[] ean = null;
        int ii = -1;
        int x = 0;

        loadComparators(); //51052
        removeAll();
        eList = _eList;
        if (_eList == null) {
            return null;
        }

        ean = getSortedArray(eList); //sort
        ii = ean.length; //sort

        if (isStatusView(_eList)) { //cr_2115.2
            for (int i = 0; i < ii; ++i) { //cr_2115.2
                EntityGroup eg = _eList.getEntityGroup(ean[i].getKey()); //cr_2115.2
                if (eg.isDisplayable()) { //cr_2115.2
                    addStatusTab(eg, x++, _reload); //cr_2115.2
                } //cr_2115.2
            } //cr_2115.2
        } else { //cr_2115.2
            for (int i = 0; i < ii; ++i) {
                EntityGroup eg = _eList.getEntityGroup(ean[i].getKey());
                if (eg.isDisplayable()) {
                    addTab(eg, x++, _reload);
                }
            }
        } //cr_2115.2
        setSelectedIndex(0);
        clearComparators(); //51052
        return getEntityGroup(0);
    }

    private EANFoundation[] getSortedArray(EntityList _eList) {
        EANFoundation[] ean = _eList.getTable().getTableRowsAsArray();
        Arrays.sort(ean, new EComparator(true));
        return ean;
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
            return (EntityItem[]) nt.getVisibleObjects(_new, _bEx);
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
            return (EntityItem[]) nt.getSelectedObjects(_new, _bEx);
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
     * removeTab
     * @author Anthony C. Liberto
     * @param _key
     */
    public void removeTab(String _key) {
        int indx = getIndexOf(_key);
        NavTable nt = getTable(indx); //leak
        if (nt != null) { //leak
            nt.dereference();
        } //leak

        removeTabAt(indx);
        if (getTabCount() > 0) {
            setSelectedIndex(0);
        }
        return;
    }

    /**
     * getIndexOf
     * @author Anthony C. Liberto
     * @return int
     * @param _eg
     */
    public int getIndexOf(EntityGroup _eg) {
        int ii = getTabCount();
        for (int i = 0; i < ii; ++i) {
            NavTable nt = getTable(i);
            if (nt != null && nt.getEntityGroup() == _eg) {
                return i;
            }
        }
        return -1;
    }

    /**
     * removeListeners
     * @author Anthony C. Liberto
     */
    public void removeListeners() {
        return;
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
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        NavTable nt = getTable();
        if (nt != null) {
            nt.requestFocus();
        } else {
            super.requestFocus();
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
        if (_i >= getTabCount() || _i < 0) {
            return null;
        }
        c = getComponentAt(_i);
        if (c != null && c instanceof NavTable) {
            return (NavTable) c;

        } else if (c != null && c instanceof EScrollPane) {
            return (NavTable) (((EScrollPane) c).getViewport().getView());
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
     * @param _reload
     * @author Anthony C. Liberto
     */
    public void addTab(EntityGroup _eg, boolean _reload) {
        addTab(_eg, getTabCount(), _reload);
    }

    private void addTab(EntityGroup _eg, int _index, boolean _reload) {
        EScrollPane scroll = null;
        String s = null;
        NavTable nt = new NavTable(_eg, _eg.getEntityGroupTable(), _reload) {
        	private static final long serialVersionUID = 1L;
        	public void setFilter(boolean _b) {
                updateFilterIcon(_b);
                return;
            }
        };
        nt.setFont(getFont());
        nt.addMouseListener(popup);
        scroll = new EScrollPane(nt);
        scroll.setScrollMode(javax.swing.JViewport.BLIT_SCROLL_MODE);		//MN24377449
        add(scroll, _index);
        s = getTitle(_eg);
        setToolTipTextAt(_index, s);
        //		setTitleAt(_index,routines.truncate(s,20));
        setTitleAt(_index, s);
        //51813		resort(nt);												//51052
        return;
    }

    /**
     * refreshTitle
     * @author Anthony C. Liberto
     * @param _eg
     */
    public void refreshTitle(EntityGroup _eg) {
        String title = getTitle(_eg);
        int index = getIndexOf(_eg);
        //		setTitleAt(index,routines.truncate(title,20));
        setTitleAt(index, title);
        return;
    }

    /**
     * refreshTab
     * @author Anthony C. Liberto
     * @param _eg
     * @param _reload
     */
    public void refreshTab(EntityGroup _eg, boolean _reload) {
        int index = getIndexOf(_eg);
        if (index >= 0) {
            remove(index);
            addTab(_eg, index, _reload);
            setSelectedIndex(index);
        } else {
            int i = getIndexFor(_eg);
            addTab(_eg, i, _reload);
            setSelectedIndex(i);
        }
        return;
    }

    /**
     * toggleTab
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void toggleTab(int _i) {
        if (_i >= 0) {
            setSelectedIndex(_i);
        }
        return;
    }

    /**
     * getIndexOf
     * @author Anthony C. Liberto
     * @return int
     * @param _s
     */
    public int getIndexOf(String _s) {
        int ii = getTabCount();
        for (int i = 0; i < ii; ++i) {
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
        int ii = getTabCount();
        int iLower = 0;
        int iCur = 0;
        for (int i = 0; i < ii; ++i) {
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
     * gotoTab
     * @author Anthony C. Liberto
     * @return boolean
     * @param _s
     */
    public boolean gotoTab(String _s) {
        int ii = getTabCount();
        for (int i = 0; i < ii; ++i) {
            EntityGroup eg = getEntityGroup(i);
            String desc = eg.toString().toUpperCase();
            if (desc.startsWith(_s.toUpperCase())) {
                setSelectedIndex(eg.getKey());
                return true;
            }
        }
        return false;
    }

    private String getTitle(EntityGroup _eg) {
        String s = null;
        int i = -1;
        s = _eg.toString();
        i = _eg.getEntityItemCount();
        if (i == 0) {
            return Routines.truncate(s, 20);
        }
        return Routines.truncate(s, 20) + " (" + i + ")";
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
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		initAccessibility(null);
        id = null;
        removePopupMenu();
        removeListeners();
        if (eList != null) {
            eList.dereference();
            eList = null;
        }

        mapComparator.clear(); //51052
        mapComparator = null; //51052

        removeAll();
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
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        NavTable nt = getTable();
        if (nt != null) {
            nt.refreshAppearance();
        }
        revalidate();
        repaint();
        return;
    }

    /**
     * getEntityGroupCount
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getEntityGroupCount() {
        return getTabCount();
    }

    /**
     * getPanelType
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_NAVDATAPICK;
    }

    /**
     * isPanelType
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPanelType(String _s) {
        return _s.equals(getPanelType());
    }
    /*
     24306
    */
    /**
     * updateFilterIcon
     * @param _b
     * @author Anthony C. Liberto
     */
    public void updateFilterIcon(boolean _b) {
    }

    /*
     51052
    */
    private void loadComparators() {
        int ii = -1;
        clearComparators();
        ii = getTabCount();
        for (int i = 0; i < ii; ++i) {
            NavTable nt = getTable(i);
            if (nt != null) {
                //tablesort				mapComparator.put(nt.getUIPrefKey(), nt.getClonedComparator());
                mapComparator.put(nt.getUIPrefKey(), nt.getSortDetails()); //tablesort
            }
        }
        return;
    }

    private void clearComparators() {
        mapComparator.clear();
        return;
    }

    /**
     * resort
     * @param _nt
     * @author Anthony C. Liberto
     */
    public void resort(NavTable _nt) {
        String key = _nt.getUIPrefKey();
        if (mapComparator.containsKey(key)) {
            //tablesort			_nt.sort((Comparator)mapComparator.remove(key));
            SortDetail sd = (SortDetail) mapComparator.remove(key); //tablesort
            if (sd != null) { //tablesort
                _nt.sort(sd.getColumns(), sd.getDirection()); //tablesort
            } //tablesort

        }
        return;
    }
    /*
     52783
     */
    /**
     * isEmpty
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEmpty() {
        NavTable nt = getTable();
        if (nt != null) {
            return nt.isEmpty();
        }
        return true;
    }
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
    public void setFilterGroup(FilterGroup _fg) {
    }

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

    /*
     gb_20041124
     */
    /**
     * getInformation
     * @author Anthony C. Liberto
     */
    protected void getInformation() {
        NavTable nt = getTable();
        if (nt != null) {
            nt.showInformation();
        }
        return;
    }

    /*
     cr_2115.2
     */
    private boolean isStatusView(EntityList _list) {
        if (_list != null) {
            return _list.isABRStatus();
        }
        return false;
    }

    private void addStatusTab(EntityGroup _eg, int _index, boolean _reload) {
        RowSelectableTable rst = _eg.getEntityGroupTable();
        NavTable nt = null;
        EScrollPane scroll = null;
        String s = null;
        /*
         if single display like vertical
         */
        if (rst != null && rst.getRowCount() == 1) {
            EANFoundation ean = rst.getRow(0);
            if (ean != null) {
                rst = ((EntityItem) ean).getEntityItemTable();
            }
        }

        nt = new NavTable(_eg, rst, _reload) {
        	private static final long serialVersionUID = 1L;
        	public void setFilter(boolean _b) {
                updateFilterIcon(_b);
                return;
            }
        };
        System.out.println("rows: " + rst.getRowCount());
        System.out.println("cols: " + rst.getColumnCount());
        nt.setFont(getFont());
        nt.addMouseListener(popup);
        scroll = new EScrollPane(nt);
        add(scroll, _index);
        s = getTitle(_eg);
        setToolTipTextAt(_index, s);
        setTitleAt(_index, s);
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
