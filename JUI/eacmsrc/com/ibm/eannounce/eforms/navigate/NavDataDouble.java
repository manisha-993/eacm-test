/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: NavDataDouble.java,v $
 * Revision 1.3  2008/02/21 19:18:52  wendy
 * Add access to change history for relators
 *
 * Revision 1.2  2008/01/30 16:26:55  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2005/10/31 20:03:37  tony
 * TIR USRO-SDHA-6HLPVU
 *
 * Revision 1.2  2005/09/12 19:03:14  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:01  tony
 * This is the initial load of OPICM
 *
 * Revision 1.15  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.14  2005/02/22 19:48:21  tony
 * //mn_22932182
 *
 * Revision 1.13  2005/02/09 19:29:50  tony
 * JTest After Scout
 *
 * Revision 1.12  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.11  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.10  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.9  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.8  2004/11/16 22:25:01  tony
 * improved sorting and resizing logic to improve table
 * performance.
 *
 * Revision 1.7  2004/11/08 19:01:40  tony
 * Improved sort logic to no longer sort multiple times on a
 * navigation reload.
 *
 * Revision 1.6  2004/10/11 20:58:59  tony
 * TIR USRO-R-JSTT-65NMN4
 *
 * Revision 1.5  2004/06/30 17:04:17  tony
 * parent entity group display fixed.
 *
 * Revision 1.4  2004/06/22 18:04:48  tony
 * ParentEntityGroup show
 *
 * Revision 1.3  2004/04/30 17:24:55  tony
 * 53774
 *
 * Revision 1.2  2004/03/25 23:37:19  tony
 * cr_216041310
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2004/01/06 19:12:40  tony
 * 53500
 *
 * Revision 1.15  2003/12/30 21:39:11  tony
 * 53482
 *
 * Revision 1.14  2003/12/22 21:38:16  tony
 * 53451
 *
 * Revision 1.13  2003/08/28 22:54:32  tony
 * memory update
 *
 * Revision 1.12  2003/08/06 19:47:00  joan
 * 51449
 *
 * Revision 1.11  2003/06/19 18:29:41  tony
 * 51298
 *
 * Revision 1.10  2003/06/19 16:09:42  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.9  2003/06/13 16:08:30  tony
 * 1.2h update, singleCart and various fixes.
 *
 * Revision 1.8  2003/06/12 22:23:41  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.7  2003/06/03 19:51:53  tony
 * 51052
 *
 * Revision 1.6  2003/06/02 16:45:29  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.5  2003/04/03 16:19:08  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.4  2003/04/02 23:16:50  tony
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
 * Revision 1.22  2002/11/07 16:58:29  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.exception.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.eannounce.eforms.table.NavTable;
import com.ibm.eannounce.eforms.table.SortDetail;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.*; //53774
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavDataDouble extends ETabbedPane implements NavData, MouseListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	private Navigate parent = null;
    private EntityList eList = null;
    private NavDataSelector tabSelector = null;
    private transient MouseListener mouseL = null;
    private transient FocusListener focusL = null;
    private HashMap mapComparator = new HashMap(); //51052

    private boolean selOnForm = false;
    private int actionType = -1;
    private int clicks = -1;

    private DataTab pnlDummy = new DataTab("Data", "Action"); //53500
    private EntityGroup peg = null; //peg_show

    /**
     * navDataDouble
     * @param _parent
     * @param _clicks
     * @param _actionType
     * @author Anthony C. Liberto
     */
    public NavDataDouble(Navigate _parent, int _clicks, int _actionType) {
        super();
        parent = _parent;
        clicks = _clicks;
        actionType = _actionType;
        setMouseListener(parent.getPopupMenu());
        tabSelector = new NavDataSelector(this, 1);
        Dimension d = new Dimension(200, 200);
        setPreferredSize(d);
        setSize(d);
        setMinimumSize(UIManager.getDimension("eannounce.minimum"));
        addChangeListener(this); //53774
        initAccessibility("accessible.navData");
        return;
    }

    /**
     * navDataDouble
     * @param _eList
     * @param _reload
     * @author Anthony C. Liberto
     */
    public NavDataDouble(EntityList _eList, boolean _reload) {
        super();
        tabSelector = new NavDataSelector(this, 1);
        load(_eList, _reload);
        setMinimumSize(UIManager.getDimension("eannounce.minimum"));
        addChangeListener(this); //53774
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
     * setMouseListener
     *
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
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     * @author Anthony C. Liberto
     */
    public void addFocusListener(FocusListener _fl) {
        focusL = _fl;
        super.addFocusListener(_fl);
        return;
    }

    /**
     * load
     * @author Anthony C. Liberto
     * @return EntityGroup
     * @param _eList
     * @param _reload
     */
    public EntityGroup load(EntityList _eList, boolean _reload) {
        RowSelectableTable _table = null;
        EANFoundation[] ean = null;
        int ii = -1;
        int x = 0;
        loadComparators(); //51052
        if (parent != null) {
            removeChangeListener(parent);
        }
        removeAll();
        eList = _eList;
        if (_eList == null) {
            return null;
        }
        //		int ii = _no.getNavigateGroupCount();

        _table = _eList.getTable(); //rst_table

        ean = getSortedArray(_table); //sort
        ii = ean.length; //sort
        if (tabSelector != null) {
            tabSelector.clear();
            tabSelector.load(_table);
        }

        peg = _eList.getParentEntityGroup(); //peg_show
        if (peg != null && _eList.isParentDisplayable()) { //peg_show
            addTab(peg, x++, _reload); //peg_show
        } //peg_show

        for (int i = 0; i < ii; ++i) {
            EntityGroup eg = _eList.getEntityGroup(ean[i].getKey());
            if (eg.isDisplayable()) {
                addTab(eg, x++, _reload);
            }
        }

        if (getTabCount() == 0) { //53500
            add(pnlDummy, 0); //53500
            setTitleAt(0, eaccess().getString("nia")); //53500
            clearComparators(); //53500
            return null; //53500
        } //53500

        setSelectedIndex(0);
        if (parent != null) {
            addChangeListener(parent);
        }
        clearComparators(); //51052
        return getEntityGroup(0);
    }

    private EANFoundation[] getSortedArray(RowSelectableTable _table) {
        EANFoundation[] ean = _table.getTableRowsAsArray();
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
		int index = getIndexOf(_key);							//TIR USRO-SDHA-6HLPVU
		if (index >= 0 && index < getEntityGroupCount()) {		//TIR USRO-SDHA-6HLPVU
			setSelectedIndex(index);							//TIR USRO-SDHA-6HLPVU
		}														//TIR USRO-SDHA-6HLPVU
		return;
    }

    /**
     * removeTab
     * @author Anthony C. Liberto
     * @param _key
     */
    public void removeTab(String _key) {
        int indx = getIndexOf(_key);
        getDataTab(indx).dereference();
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
            if (nt.getEntityGroup() == _eg) {
                return i;
            }
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
     * getDataTab
     * @param _eg
     * @return
     * @author Anthony C. Liberto
     */
    public DataTab getDataTab(EntityGroup _eg) {
        int ii = getTabCount();
        for (int i = 0; i < ii; ++i) {
            DataTab tab = getDataTab(i);
            if (tab != null) {
                NavTable nt = tab.getTable();
                if (nt != null && nt.getEntityGroup() == _eg) {
                    return tab;
                }
            }
        }
        return null;
    }

    /**
     * requestFocus
     * @author Anthony C. Liberto
     * @param _eg
     */
    public void requestFocus(EntityGroup _eg) {
        DataTab dt = getDataTab(_eg);
        if (dt != null) {
            //53500			dt.getTable().requestFocus();
            dt.requestFocus(); //53500
            if (parent != null) {
                parent.updateMenuActions(dt.getNavAction());
            }
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
        DataTab tab = getDataTab(_i);
        if (tab != null) {
            return tab.getTable();
        }
        return null;
    }

    /**
     * getNavAction
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public NavAction getNavAction(int _i) {
        DataTab tab = getDataTab(_i);
        if (tab != null) {
            return tab.getNavAction();
        }
        return null;
    }

    /**
     * getDataTab
     * @return
     * @author Anthony C. Liberto
     */
    public DataTab getDataTab() {
        return getDataTab(getSelectedIndex());
    }

    /**
     * getNavAction
     * @return
     * @author Anthony C. Liberto
     */
    public NavAction getNavAction() {
        DataTab tab = getDataTab();
        if (tab != null) {
            return tab.getNavAction();
        }
        return null;
    }

    /**
     * getDataTab
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public DataTab getDataTab(int _i) {
        if (_i >= getTabCount() || _i < 0) {
            return null;
        }
        return (DataTab) getComponentAt(_i);
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
        if (peg != null) { //peg_show
            if (peg.getKey().equals(_key)) { //peg_show
                return peg; //peg_show
            } //peg_show
        } //peg_show
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
        NavTable nt = new NavTable(_eg, _eg.getEntityGroupTable(), _reload);
        DataTab dTab = null;
        nt.setFont(getFont());
        nt.addMouseListener(mouseL);
        nt.addMouseListener(this);

        if (parent != null) {
            nt.addPropertyChangeListener(parent);
            nt.setNavigate(parent); //51449
        }

        dTab = new DataTab(nt, "Data", getNavAction(_eg, clicks, actionType), "Action");
        dTab.addFocusListener(focusL);
        add(dTab, _index);

        setToolTipTextAt(_index, getTip(_eg));
        setTitleAt(_index, getTitle(_eg));
        resort(nt); //51052
        return;
    }

    /**
     * removeListeners
     * @author Anthony C. Liberto
     */
    public void removeListeners() {
        int ii = getTabCount();
        for (int i = 0; i < ii; ++i) {
            NavTable nt = getTable(i);
            if (nt != null && parent != null) {
                nt.removePropertyChangeListener(parent);
            }
        }
        removeChangeListener(this); //53774
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
        if (eaccess().isDebug()) {
            EAccess.appendLog("navDataDouble.getIndexOf(" + _s + ")");
        }
        if (Routines.have(_s)) { //TIR USRO-R-JSTT-65NMN4
            int ii = getTabCount();
            for (int i = 0; i < ii; ++i) {
                EntityGroup eg = getEntityGroup(i);
                if (eg != null) { //TIR USRO-R-JSTT-65NMN4
                    String tmp = eg.getKey(); //TIR USRO-R-JSTT-65NMN4
                    if (Routines.have(tmp)) { //TIR USRO-R-JSTT-65NMN4
                        if (eaccess().isDebug()) {
                            EAccess.appendLog("    comparing to: " + tmp);
                        }
                        if (tmp.equals(_s)) { //TIR USRO-R-JSTT-65NMN4
                            return i; //TIR USRO-R-JSTT-65NMN4
                        } //TIR USRO-R-JSTT-65NMN4
                    } //TIR USRO-R-JSTT-65NMN4
                } //TIR USRO-R-JSTT-65NMN4
                //TIR USRO-R-JSTT-65NMN4				if (eg.getKey().equals(_s))
                //TIR USRO-R-JSTT-65NMN4					return i;
            }
        } //TIR USRO-R-JSTT-65NMN4
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

    private NavAction getNavAction(EntityGroup _eg, int _clicks, int _type) {
        NavAction action = null;
        switch (_type) {
        case Navigate.ACTION_BOX :
            action = new NavActionBox(parent);
            break;
        case Navigate.ACTION_LIST :
            action = new NavActionList(parent, _clicks);
            break;
        default :
            action = new NavActionTree(parent, _clicks);
            break;
        }
        if (action != null) {
            //51298			action.load(_eg);
            RowSelectableTable rst = _eg.getActionGroupTable();
            action.load(eaccess().getExecutableActionItems(_eg, rst), eaccess().getActionTitle(_eg, rst)); //51298
        }
        return action;
    }

    /**
     * getTabSelector
     *
     * @author Anthony C. Liberto
     * @return NavDataSelector
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
        //		if (_ng.isRoot())
        //			return "* " + _ng.getNavigateDescription();
        //		return _ng.getNavigateDescription();
        String s = null;
        int i = -1;
        if (_eg != null && _eg == peg) { //peg_show
            s = INDICATE_PARENT + _eg.toString(); //peg_show
        } else { //peg_show
            s = _eg.toString();
        } //peg_show
        i = _eg.getEntityItemCount();
        if (i == 0) {
            return Routines.truncate(s, 20);
        }
        return Routines.truncate(s, 20) + " (" + i + ")";
    }

    private String getTip(EntityGroup _eg) {
        int i = _eg.getEntityItemCount();
        if (i == 0) {
            return _eg.toString();
        }
        return _eg.toString() + " (" + i + ")";
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2) {
            Component c = getComponentAt(getSelectedIndex());
            if (c instanceof DataTab) {
                ((DataTab) c).setSelectedIndex(1);
            }
        }
    }
    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent me) {
        if (parent != null) {
            parent.getPopupMenu().mousePressed(me);
        }
        return;
    }
    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent me) {
    }
    //	public void handleEvent(java.awt.AWTEvent _e) {}
    //	public void coalescePaintEvent(java.awt.event.PaintEvent _e) {}
    //	public void dispose(){};

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		initAccessibility(null);
        removeListeners();
        parent = null;
        if (pnlDummy != null) { //53500
            pnlDummy.dereference(); //53500
            pnlDummy = null; //53500
        } //53500
        if (eList != null) {
            try {
                eList.dereference();
            } catch (Exception _x) {
                _x.printStackTrace();
                //there is no way of knowing if they are on the same list
                //so i can't tell if the list was already dereferenced.
            }
            eList = null;
        }
        peg = null; //peg_show
        mapComparator.clear(); //51052
        mapComparator = null; //51052

        tabSelector.removeAll();
        tabSelector = null;
        removeAll();
        return;
    }

    /**
     * @see java.awt.Container#removeAll()
     * @author Anthony C. Liberto
     */
    public void removeAll() {
        while (getTabCount() > 0) {
            NavTable nt = getTable(0);
            if (nt != null) {
                nt.removeMouseListener(mouseL);
                if (parent != null) {
                    nt.removePropertyChangeListener(parent);
                }
                nt.dereference();
            }
            removeTabAt(0);
        }
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
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        return;
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
        return getTabCount();
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
                mapComparator.put(nt.getUIPrefKey(), nt.getSortDetails());
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
            //tableSort			_nt.sort((Comparator)mapComparator.remove(key));
            SortDetail sd = (SortDetail) mapComparator.remove(key); //tablesort
            if (sd != null) { //tablesort
                _nt.sort(sd.getColumns(), sd.getDirection()); //tablesort
            } //tablesort
        }
        return;
    }

    /*
     1.2h
    */
    /**
     * @see javax.swing.JComponent#getBorder()
     * @author Anthony C. Liberto
     */
    public Border getBorder() {
        if (parent != null) {
            Border out = parent.getSelectedBorder();
            if (out != null) {
                return out;
            }
        }
        return super.getBorder();
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
                nt.setFilterGroup(_fg, true); //53482
            }
        }
        return;
    }

    /*
     53774
     */
    /**
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     * @author Anthony C. Liberto
     */
    public void stateChanged(ChangeEvent _ce) {
        if (parent != null) {
            parent.updateMenuActions();
        }
        return;
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

    /**
     * sort
     *
     * @author Anthony C. Liberto
     */
    public void sort() {
//22932182        NavTable nt = getTable();
//22932182        if (nt != null) {
//22932182            nt.sort();
//22932182        }
		int ii = getTabCount();				//22932182
		for (int i=0;i<ii;++i) {			//22932182
			NavTable nt = getTable(i);		//22932182
			if (nt != null) {				//22932182
				nt.sort();					//22932182
			}								//22932182
		}									//22932182
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
