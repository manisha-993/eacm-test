/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: NavDataSingle.java,v $
 * Revision 1.4  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.3  2008/02/21 19:18:52  wendy
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
 * Revision 1.14  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.13  2005/02/22 19:48:21  tony
 * //mn_22932182
 *
 * Revision 1.12  2005/02/09 19:29:51  tony
 * JTest After Scout
 *
 * Revision 1.11  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.10  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.9  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.8  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.7  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.6  2004/11/16 22:25:01  tony
 * improved sorting and resizing logic to improve table
 * performance.
 *
 * Revision 1.5  2004/11/08 19:01:40  tony
 * Improved sort logic to no longer sort multiple times on a
 * navigation reload.
 *
 * Revision 1.4  2004/06/30 17:04:17  tony
 * parent entity group display fixed.
 *
 * Revision 1.3  2004/06/22 18:04:48  tony
 * ParentEntityGroup show
 *
 * Revision 1.2  2004/03/25 23:37:19  tony
 * cr_216041310
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2003/12/30 21:39:11  tony
 * 53482
 *
 * Revision 1.10  2003/12/22 21:38:16  tony
 * 53451
 *
 * Revision 1.9  2003/08/28 22:54:32  tony
 * memory update
 *
 * Revision 1.8  2003/08/06 19:47:00  joan
 * 51449
 *
 * Revision 1.7  2003/06/03 19:51:53  tony
 * 51052
 *
 * Revision 1.6  2003/06/02 16:45:30  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.5  2003/04/03 16:19:09  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.4  2003/04/02 23:16:49  tony
 * 50333
 *
 * Revision 1.3  2003/03/21 20:54:32  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.2  2003/03/11 00:33:25  tony
 * accessibility changes
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.25  2002/11/07 16:58:30  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import com.ibm.eannounce.eforms.table.NavTable;
//import com.ibm.eannounce.eforms.table.SortDetail;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.exception.*;
import COM.ibm.eannounce.objects.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.UIManager;
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavDataSingle extends ETabbedPane implements NavData {
	private static final long serialVersionUID = 1L;
	/**
     * parent
     */
    protected Navigate parent = null;
	/**
     * eList
     */
    protected EntityList eList = null;
	/**
     * navDataSelector
     */
    protected NavDataSelector tabSelector = null;
	private transient MouseListener mouseL = null;
	private boolean selOnForm = false;
	private HashMap mapComparator = new HashMap();				//51052
	private EntityGroup peg = null;							//peg_show
	
	/**
     * navDataSingle
     * @param _parent
     * @author Anthony C. Liberto
     */
    public NavDataSingle(Navigate _parent) {
		super();
        Dimension d = new Dimension(200,200);
		parent = _parent;
		setMouseListener(parent.getPopupMenu());
		tabSelector = new NavDataSelector(this,1);
		setPreferredSize(d);
		setSize(d);
		setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		initAccessibility("accessible.navData");
	}

	/**
     * navDataSingle
     * @param _eList
     * @param _reload
     * @author Anthony C. Liberto
     */
    public NavDataSingle(EntityList _eList,boolean _reload) {
		super();
		tabSelector = new NavDataSelector(this,1);
		load(_eList,_reload);
		initAccessibility("accessible.navData");
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
    public EntityGroup load(EntityList _eList, boolean _reload) {
    	//Long t1 = EAccess.eaccess().timestamp("        NavDataSingle.load started");
		RowSelectableTable rst = null;
        EANFoundation[] ean = null;
        int ii = -1;
        int x = 0;
        // reset thread counts
        tabLoadedCnt=0;
        tabLoadThreadVct.clear();
        
        loadComparators();												//51052
		if (parent != null) {
			removeChangeListener(parent);
		}
		removeAll();
		eList = _eList;
		if (eList == null && parent != null) {
			addChangeListener(parent);
			return null;
		}

		rst = eList.getTable();						//rst_update

		ean = getSortedArray(rst);						//rst_update
		ii = ean.length;								//sort

		if (tabSelector != null) {
			tabSelector.clear();
			tabSelector.load(rst);										//rst_update
		}

		//Long t2 = EAccess.eaccess().timestamp("        NavDataSingle.load addtab started");
		// create a thread for each entitygroup if there are more than one
		peg = _eList.getParentEntityGroup();							//peg_show
		int tabcnt = 0;	
		/**/if (peg != null && _eList.isParentDisplayable()) {		
			tabcnt++;
		}															

		for (int i=0;i<ii;++i) {
			EntityGroup eg = _eList.getEntityGroup(ean[i].getKey());
			if (eg.isDisplayable()) {
				tabcnt++;
			}
		}/**/
		//tabcnt=1;//fixme remove this and above to use threads
		if (peg != null && _eList.isParentDisplayable()) {	
			if (tabcnt==1){
				addTab(peg,x++, _reload);
			}else{
				//thread it
				tabLoadThreadVct.add(new TabLoader(peg,x++, _reload));
			}
		}															

		for (int i=0;i<ii;++i) {
			EntityGroup eg = _eList.getEntityGroup(ean[i].getKey());
			if (eg.isDisplayable()) {
				if (tabcnt==1){
					addTab(eg,x++, _reload);
				}else{
					//thread it
					tabLoadThreadVct.add(new TabLoader(eg,x++, _reload));
				}
			}
		}
		if (tabcnt>1){ // use threads
			// create array to hold info needed to add the tab
			tabArray = new ScrollEntityGroup[tabLoadThreadVct.size()];
//			Long t3 = EAccess.eaccess().timestamp("        NavDataSingle.load threads started");
			for (int i=0; i<tabLoadThreadVct.size(); i++){
				Thread thd = (Thread)tabLoadThreadVct.elementAt(i);
				thd.start();
			}
			
			waitForTabSetup();
//			EAccess.eaccess().timestamp("        NavDataSingle.load threads ended",t3);
			// actually add the tab using the index now
			for (int i=0; i<tabArray.length; i++){
				addTab(tabArray[i], i);
				tabArray[i].dereference();
				tabArray[i] = null;
			}
		}
		//EAccess.eaccess().timestamp("        NavDataSingle.load addtab ended",t2);
		setSelectedIndex(0);
		if (parent != null) {
			addChangeListener(parent);
		}
		clearComparators();												//51052
		repaint();														//51052
		//EAccess.eaccess().timestamp("        NavDataSingle.load ended",t1);
	    // reset thread counts
        tabLoadedCnt=0;
        tabLoadThreadVct.clear();
        tabArray = null;
		return getEntityGroup(0);
	}

	private EANFoundation[] getSortedArray(RowSelectableTable _tbl) {		//rst_update
		EANFoundation[] ean = _tbl.getTableRowsAsArray();					//rst_update
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
	}

	/**
     * removeTab
     * @author Anthony C. Liberto
     * @param _s
     */
    public void removeTab(String _s) {
		int indx = getIndexOf(_s);
		if (indx >= 0) {
			NavTable nt = getTable(indx);	//leak
			if (nt != null) {					//leak
				nt.dereference();
			}			//leak
			removeTabAt(indx);
		}
		if (getTabCount() > 0) {
			setSelectedIndex(0);
		}
	}

	/**
     * getIndexOf
     * @author Anthony C. Liberto
     * @return int
     * @param _eg
     */
    public int getIndexOf(EntityGroup _eg) {
		return getIndexOf(_eg.getKey());
	}

	/**
     * removeListeners
     * @author Anthony C. Liberto
     */
    private void removeListeners() {
		int ii = getTabCount();
		for (int i=0;i<ii;++i) {
			NavTable nt = getTable(i);
			if (nt != null && parent != null) {
				nt.removePropertyChangeListener(parent);
			}
		}
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
			return (NavTable)c;

		} else if (c!= null && c instanceof EScrollPane) {
			return (NavTable)(((EScrollPane)c).getViewport().getView());
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
     * @param _s
     */
    public EntityGroup getEntityGroup(String _s) {
		if (peg != null) {								//peg_show
			if (peg.getKey().equals(_s)) {				//peg_show
				return peg;								//peg_show
			}											//peg_show
		}												//peg_show
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
     * addTab
     * @param _eg
     * @param _reload
     * @author Anthony C. Liberto
     *
    public void addTab(EntityGroup _eg, boolean _reload) {
		addTab(_eg, getTabCount(),_reload);
	}*/

    // no threads used here
	private void addTab(EntityGroup _eg, int _index, boolean _reload) {
		RowSelectableTable tbl = _eg.getEntityGroupTable();	
		NavTable nt = new NavTable(_eg,tbl,_reload);			
        EScrollPane scroll = null;

		nt.setFont(getFont());
		nt.addMouseListener(mouseL);
		if (parent != null) {
			nt.addPropertyChangeListener(parent);
			nt.setNavigate(parent);		//51449
		}
		scroll = new EScrollPane(nt);
		add(scroll,_index);
		setToolTipTextAt(_index,getTip(_eg));
		setTitleAt(_index,getTitle(_eg));
	}
    //some threads load faster than others, index is meaningless when loaded from thread so sort is lost
	private void addTab(ScrollEntityGroup seg, int _index) {		
		add(seg.scroll,_index);
		setToolTipTextAt(_index,getTip(seg.eg));
		setTitleAt(_index,getTitle(seg.eg));
	}
	// called by the thread to do everything but add the tab at the index
	private void setupTab(EntityGroup _eg, int _index, boolean _reload) {
		RowSelectableTable tbl = _eg.getEntityGroupTable();	
		NavTable nt = new NavTable(_eg,tbl,_reload);			
        EScrollPane scroll = null;
		nt.setFont(getFont());
		nt.addMouseListener(mouseL);
		if (parent != null) {
			nt.addPropertyChangeListener(parent);
			nt.setNavigate(parent);		//51449
		}
		scroll = new EScrollPane(nt);
		tabArray[_index]= new ScrollEntityGroup(_eg,scroll); // save for later add()
	}
	/**
     * refreshTitle
     * @author Anthony C. Liberto
     * @param _eg
     */
    public void refreshTitle(EntityGroup _eg) {
		String title = getTitle(_eg);
		int index = getIndexOf(_eg);
		setTitleAt(index,title);
	}

	/**
     * refreshTab
     * @author Anthony C. Liberto
     * @param _eg
     * @param _reload
     */
    public void refreshTab(EntityGroup _eg,boolean _reload) {
		int index = getIndexOf(_eg);
		if (index >= 0) {
			remove(index);
			addTab(_eg,index,_reload);
			setSelectedIndex(index);
		} else {
			int i = getIndexFor(_eg);
			addTab(_eg,i,_reload);
			setSelectedIndex(i);
		}
	}

	/**
     * setSelectedIndex
     * @param _s
     * @author Anthony C. Liberto
     */
    private void setSelectedIndex(String _s) {
		int index = getIndexOf(_s);
		if (index >= 0 && index < getEntityGroupCount()) {
			setSelectedIndex(index);
		}
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
	}

	/**
     * getIndexOf
     * @author Anthony C. Liberto
     * @return int
     * @param _s
     */
    public int getIndexOf(String _s) {
		int ii = getTabCount();
		for (int i=0;i<ii;++i) {
			EntityGroup eg = getEntityGroup(i);
//22886			if (eg.getKey().equals(_s))
			if (eg != null) {								//22886
				String s = eg.getKey();						//22886
				if (s != null && s.equals(_s)) {			//22886
					return i;
				}											//22886
			}												//22886
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

	/**
     * gotoTab
     * @author Anthony C. Liberto
     * @return boolean
     * @param _s
     */
    public boolean gotoTab(String _s) {
		int ii = getTabCount();
		for (int i=0;i<ii;++i) {
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
        if (_eg != null && _eg == peg) {			//peg_show
			s = INDICATE_PARENT + _eg.toString();	//peg_show
		} else {									//peg_show
			s = _eg.toString();
		}											//peg_show
		i = _eg.getEntityItemCount();
		if (i == 0) {
			return Routines.truncate(s,20);
		}
		return Routines.truncate(s,20) + " (" + i + ")";
	}

	private String getTip(EntityGroup _eg) {
		int i = _eg.getEntityItemCount();
		if (i == 0) {
			return _eg.toString();
		}
		return _eg.toString() + " (" + i + ")";
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		initAccessibility(null);
		removeAll();
		removeListeners();
		parent = null;
		if (eList != null) {
			eList.dereference();
			eList = null;
		}
		peg = null;						//peg_show
		mapComparator.clear();			//51052
		mapComparator = null;			//51052

		tabSelector.removeAll();
		removeAll();
	}

	/**
     * @see java.awt.Container#removeAll()
     * @author Anthony C. Liberto
     */
    public void removeAll() {
		while(getTabCount() > 0) {
			NavTable nt = getTable(0);
//			Component comp = getComponentAt(0);
			nt.removeMouseListener(mouseL);
			if (parent != null) {
				nt.removePropertyChangeListener(parent);
			}
			nt.dereference();
			removeTabAt(0);
		}
	}

    /**
     * getCurrentEntityItem
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

/*
 51052
*/
	private void loadComparators() {
		int ii = -1;
        clearComparators();
		ii = getTabCount();
		for (int i=0;i<ii;++i) {
			NavTable nt = getTable(i);
			if (nt != null) {
				mapComparator.put(nt.getUIPrefKey(), nt.getSortDetails());
			}
		}
	}

	private void clearComparators() {
		mapComparator.clear();
	}

	/**
     * resort
     * @param _nt
     * @author Anthony C. Liberto
     *
    public void resort(NavTable _nt) {
		String key = _nt.getUIPrefKey();
		if (mapComparator.containsKey(key)) {
//tablesort			_nt.sort((Comparator)mapComparator.remove(key));
			SortDetail sd = (SortDetail)mapComparator.remove(key);		//tablesort
			if (sd != null) {											//tablesort
				_nt.sort(sd.getColumns(),sd.getDirection());			//tablesort
			}															//tablesort
		}
	}*/

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
		NavTable nt = getTable();
		if (nt != null) {
			return nt.getFilterGroup();
		}
		return null;
	}

	/**
     * setFilterGroup
     * @author Anthony C. Liberto
     * @param _fg
     */
    public void setFilterGroup(FilterGroup _fg) {
		if (_fg != null) {
			NavTable nt = getTable();
			if (nt != null) {
//53482				nt.setFilterGroup(_fg);
				nt.setFilterGroup(_fg,true);		//53482
			}
		}
	}

    /**
     * refresh
     *
     * @author Anthony C. Liberto
     * /
    public void refresh() {
		NavTable nt = getTable();
		if (nt != null) {
			nt.refresh();
		}
	}*/

    /**
     * sort
     *
     * @author Anthony C. Liberto
     */
    public void sort() {
		int ii = getTabCount();				//22932182
		for (int i=0;i<ii;++i) {			//22932182
			NavTable nt = getTable(i);		//22932182
			if (nt != null) {				//22932182
				nt.sort();					//22932182
			}								//22932182
		}									//22932182
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
    private void initAccessibility(String _s) {
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
	}
    private int tabLoadedCnt=0;
    private Vector tabLoadThreadVct =new Vector();
    private ScrollEntityGroup[] tabArray = null;
    private synchronized void waitForTabSetup() {
        //This only loops once for each special event, which may not
        //be the event we're waiting for.
        while(tabLoadThreadVct.size()!=tabLoadedCnt) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        //System.out.println("Joy and efficiency have been achieved!");
    }

    private synchronized void tabLoaded(){
    	tabLoadedCnt++;
    	notifyAll();
    }

    /******************************************************************************
     * Thread to load a tab - used to improve performance
     */
    private class TabLoader extends Thread
    {
    	private EntityGroup eg;
    	private int index;
    	private boolean reload;
    	protected TabLoader(EntityGroup g, int id, boolean r){
    		eg = g;
    		index=id;
    		reload=r;
    	}
        /**
         * Try to load the tab.
         */
    	public void run()
    	{
    		//System.err.println("@@@@starting thread "+index);
    		setupTab(eg, index, reload);
   			tabLoaded();
   			//System.err.println("@@@@ending thread "+index);
   			eg=null; // release memory
    	}
    }
    private class ScrollEntityGroup{
    	EntityGroup eg;
    	EScrollPane scroll;
    	ScrollEntityGroup(EntityGroup _eg,EScrollPane _scroll){
    		eg = _eg;
    		scroll = _scroll;
    	}
    	void dereference(){
    		eg = null;
    		scroll = null;
    	}
    }
}
