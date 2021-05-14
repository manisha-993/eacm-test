/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: NavTable.java,v $
 * Revision 1.4  2009/05/26 13:46:26  wendy
 * Performance cleanup
 *
 * Revision 1.3  2008/02/21 19:18:51  wendy
 * Add access to change history for relators
 *
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:05  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.15  2005/03/07 17:54:21  tony
 * MN22319406
 *
 * Revision 1.14  2005/03/03 21:46:40  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.13  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.12  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.11  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.10  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.9  2004/11/16 22:25:01  tony
 * improved sorting and resizing logic to improve table
 * performance.
 *
 * Revision 1.8  2004/11/15 23:01:18  tony
 * improved table logic to sort only a single time on
 * navigation type table, instead of multiple times.
 *
 * Revision 1.7  2004/11/11 23:42:18  tony
 * speed resize
 *
 * Revision 1.6  2004/11/09 21:26:33  tony
 * isIndicateRelations()
 *
 * Revision 1.5  2004/11/08 19:01:52  tony
 * Improved sort logic to no longer sort multiple times on a
 * navigation reload.
 *
 * Revision 1.4  2004/10/22 22:14:43  tony
 * auto_sort/size
 *
 * Revision 1.3  2004/03/25 23:37:20  tony
 * cr_216041310
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.27  2003/12/01 17:43:55  tony
 * 52918
 *
 * Revision 1.26  2003/11/25 22:05:07  tony
 * accessibility enhancement.
 *
 * Revision 1.25  2003/11/11 00:42:21  tony
 * accessibility update, added convenience method to table.
 *
 * Revision 1.24  2003/10/29 16:47:39  tony
 * 52728
 *
 * Revision 1.23  2003/10/03 23:32:48  tony
 * accessibility update.
 *
 * Revision 1.22  2003/09/11 21:45:52  tony
 * bookmark filter.
 *
 * Revision 1.21  2003/09/10 00:08:52  tony
 * 52090
 *
 * Revision 1.20  2003/08/22 16:39:09  tony
 * general search
 *
 * Revision 1.19  2003/08/15 15:39:05  tony
 * cleaned-up code.
 *
 * Revision 1.18  2003/08/06 19:47:00  joan
 * 51449
 *
 * Revision 1.17  2003/07/25 15:42:12  tony
 * updated restore logic.
 *
 * Revision 1.16  2003/07/15 18:49:32  tony
 * 51449 usability hold
 *
 * Revision 1.15  2003/06/13 17:32:52  tony
 * 51255 resize on refresh issue.
 *
 * Revision 1.14  2003/06/10 16:46:48  tony
 * 51260
 *
 * Revision 1.13  2003/06/03 19:51:53  tony
 * 51052
 *
 * Revision 1.12  2003/06/02 16:45:30  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.11  2003/05/22 16:23:13  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.10  2003/05/21 15:38:12  tony
 * updated table logic to allow for the table and model
 * to always know the specific type of table.  Based on a
 * table constant.
 *
 * Revision 1.9  2003/05/07 15:55:49  tony
 * 24306
 *
 * Revision 1.8  2003/04/25 19:20:08  tony
 * added border to tables
 *
 * Revision 1.7  2003/04/15 17:31:34  tony
 * changed to e-announce.focusborder
 *
 * Revision 1.6  2003/04/14 21:38:25  tony
 * updated table Logic.
 *
 * Revision 1.5  2003/04/11 20:02:30  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import com.ibm.eannounce.eforms.action.ActionController;
import com.ibm.eannounce.eforms.navigate.Navigate;
import com.ibm.eannounce.erend.*;
import com.ibm.eannounce.exception.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.*;
import java.awt.datatransfer.*; //copy
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavTable extends RSTable implements ETable, ActionListener {
	private static final long serialVersionUID = 1L;
	private Navigate m_nav = null; //51449
    /**
     * sortable
     */
//    private boolean sortable = true;
    /**
     * searchable
     */
//    private boolean searchable = true;
    /**
     * replaceable
     */
//    private boolean replaceable = false;
    /**
     * filterable
     */
 //   private boolean filterable = true;
    /**
     * copyable
     */
//    private boolean copyable = true;
    /**
     * popupDisplayable
     */
//    protected boolean popupDisplayable = true;
    /**
     * columnOrderable
     */
    protected boolean columnOrderable = false;

    /**
     * foundRenderer
     */
    protected FoundRend foundRenderer = new FoundRend();
    /**
     * textCol
     */
    protected final int textCol = 0;

    /**
     * NavTable
     * @param _type
     * @author Anthony C. Liberto
     */
    public NavTable(int _type) { //gen_search
        super(null, null, _type); //gen_search
        setReplaceable(false); //gen_search
        init(); //gen_search
    } //gen_search

    /**
     * NavTable
     * @param _eg
     * @param _table
     * @param _reload
     * @author Anthony C. Liberto
     */
    public NavTable(EntityGroup _eg, RowSelectableTable _table, boolean _reload) {
        super(_eg, _table, TABLE_NAVIGATE);
        setSelectionMode(_eg);
        setReplaceable(false);
        init(_reload);
    }

    /**
     * NavTable
     * @param _o
     * @param _table
     * @author Anthony C. Liberto
     */
    public NavTable(Object _o, RowSelectableTable _table) {
        super(_o, _table, TABLE_NAVIGATE);
        //52918		setSelectionMode((NavActionItem)null);
        setReplaceable(false);
        init(false);
    }

    /**
     * NavTable
     * @param _o
     * @param _table
     * @param _ac
     * @param _reload
     * @author Anthony C. Liberto
     */
    protected NavTable(Object _o, RowSelectableTable _table, ActionController _ac, boolean _reload) {
        super(_o, _table, _ac, TABLE_NAVIGATE);
        setReplaceable(false);
        init(_reload);
    }

    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    public void init() {
        init(false);
    }

    /**
     * init
     * @param _reload
     * @author Anthony C. Liberto
     */
    private void init(boolean _reload) {
        int rows = getRowCount(); //auto_sort/size
        setRowMargin(0);
        initAccessibility("accessible.navTable");
        setColumnSelectionAllowed(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setDefaultRenderer(Object.class, new ERend());
        resizeCells();
        if (!_reload && eaccess().canSort(rows)) { //auto_sort/size
            sort();
        } //auto_sort/size
        setBorder(UIManager.getBorder("eannounce.focusBorder"));
        if (rows > 0) {
            setRowSelectionInterval(0, 0);
        }
        if (getColumnCount() > 0) { //21801
            setColumnSelectionInterval(0, 0);
        } //21801
        replayFilter(); //bookmark_filter
    }

    private void setSelectionMode(EntityGroup _group) {
        EntityList eList = _group.getEntityList();
        if (eList != null) {
            EANActionItem ean = eList.getParentActionItem();
            if (ean != null && ean instanceof NavActionItem) {
                setSelectionMode((NavActionItem) ean);
            }
        }
    }

    private void setSelectionMode(NavActionItem _item) {
        if (_item == null || _item.isSingleSelect()) {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } else {
            setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        revalidate();
    }

    /**
     * getCellRenderer
     *
     * @param r
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    public TableCellRenderer getCellRenderer(int r, int c) {
        if (isFound(r, c)) {
            return foundRenderer;
        }
        return super.getCellRenderer(r, c);
    }

    /**
     * updateModel
     * @author Anthony C. Liberto
     * @param _table
     */
    public void updateModel(RowSelectableTable _table) {
        cgtm.updateModel(_table);
        cgtm.resetKeys();
        refreshTable(true);
    }

    /**
     * isColumnVisible
     *
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isColumnVisible(int _col) {
        return true;
    }

    /**
     * setRowHeight
     *
     * @param _r
     * @param _height
     * @author Anthony C. Liberto
     */
    public void setRowHeight(int _r, int _height) {
        cgtm.setRowHeight(_r, _height);
    }

    /**
     * isRowVisible
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRowVisible(int _row) {
        if (getRowHeight(_row) == 0) {
            return false;
        }
        return true;
    }

    /**
     * isValidCell
     *
     * @param r
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValidCell(int r, int c) {
        if (r < 0 || r >= getRowCount()) {
            return false;

        } else if (c < 0 || c >= getColumnCount()) {
            return false;
        }
        return true;
    }

    /**
     * getSelectedObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSelectedObject() {
        return cgtm.getRow(getSelectedRow());
    }

    /**
     * getCurrentEntityItem - based on row
     *
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem getCurrentEntityItem(boolean _new) {
        int row = getSelectedRow();
        EntityItem ei = (EntityItem) cgtm.getRow(row);
  
        if (ei == null) {
            return null;
        } 
        if (_new) {
            try {
                return new EntityItem(ei);
            } catch (MiddlewareRequestException _mre) {
                _mre.printStackTrace();
                return ei;
            }
        } else {
            return ei;
        }
    }

    /**
     * getCurrentEntityItem - based on row and column
     * used to support getting entityhistory when row has entity - relator - entity
     */
    public EntityItem getCurrentEntityItem() {
        int row = getSelectedRow();
        EntityItem ei = null;
        
        int col = getSelectedColumn();
        if (isValidCell(row, col)) {
            EANAttribute att = getAttribute(row, col);
            ei = att.getEntityItem();         
        }        
        return ei;
    }
    /**
     * getEntityGroup
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getEntityGroup() {
        Object o = null;
        if (cgtm == null) {
            return null;
        }
        o = cgtm.getObject();
        if (o instanceof EntityGroup) {
            return (EntityGroup) o;
        }
        return null;
    }

    /**
     * invertSelection
     *
     * @author Anthony C. Liberto
     */
    public void invertSelection() {
        int ii = getRowCount();
        for (int i = 0; i < ii; ++i) {
            if (isRowSelected(i)) {
                removeRowSelectionInterval(i, i);

            } else {
                addRowSelectionInterval(i, i);
            }
        }
    }

    /**
     * setSearchable
     * find Methods
     *
     * @param _b
     */
    public void setSearchable(boolean _b) {
        searchable = _b;
    }

    /**
     * isSearchable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSearchable() {
        return searchable;
    }

    /**
     * setReplaceable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setReplaceable(boolean _b) {
        replaceable = _b;
    }

    /**
     * isReplaceable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isReplaceable() {
        return replaceable;
    }

    /**
     * findValue
     * @author Anthony C. Liberto
     * @param find
     * @param Multi
     * @param strCase
     * @param increment
     * @param _forReplace
     */
    public void findValue(String find, boolean Multi, boolean strCase, int increment, boolean _forReplace) {
        //String str = null;
        if (!isSearchable()) {
            return;
        }
        //str = 
        getNextValue(find, Multi, strCase, increment, getSelectedRow(), getSelectedColumn(), _forReplace);
    }

    /**
     * replaceValue
     * @author Anthony C. Liberto
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     */
    public void replaceValue(String find, String replace, boolean Multi, boolean strCase, int increment) {
        int r = getSelectedRow();
        int c = getSelectedColumn();
        String str = null;
        if (!isSearchable() || !isReplaceable()) {
            return;
        }
        if (r < 0) {
            r = 0;
        }
        if (c < 0) {
            c = 0;
        }
        str = getString(r, c);
        if (isFound(str, find, strCase)) {
            replaceString(find, replace, r, c);
        }
    }

    /**
     * replaceNextValue
     * @author Anthony C. Liberto
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     */
    public void replaceNextValue(String find, String replace, boolean Multi, boolean strCase, int increment) {
        if (!isSearchable() || !isReplaceable()) {
            return;
        }
        replaceValue(find, replace, Multi, strCase, increment);
        findValue(find, Multi, strCase, increment, true);
    }

    /**
     * replaceAllValue
     * @author Anthony C. Liberto
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     */
    public void replaceAllValue(String find, String replace, boolean Multi, boolean strCase, int increment) {
        int rr = getRowCount();
        int cc = getColumnCount();
        if (!isSearchable() || !isReplaceable()) {
            return;
        }
        for (int c = 0; c < cc; ++c) {
            for (int r = 0; r < rr; ++r) {
                String str = getString(r, c);
                if (isFound(str, find, strCase)) {
                    replaceString(find, replace, r, c);
                }
            }
        }
    }

    /**
     * hasFound
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasFound() { //20020301
        return !foundItem.isEmpty(); //20020301
    } //20020301

    /**
     * getString
     * @param r
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    private String getString(int r, int c) {
        if (isValidCell(r, c)) {
            Object o = getValueAt(r, c);
            if (o != null) {
                return o.toString();
            }
        }
        return "";
    }

    /**
     * isFound
     * @author Anthony C. Liberto
     * @return boolean
     * @param str
     * @param find
     * @param caseSensitive
     */
    protected boolean isFound(String str, String find, boolean caseSensitive) {
        if (caseSensitive) {
            if (str.indexOf(find) != -1) {
                return true;
            }
        } else {
            if (str.toUpperCase().indexOf(find.toUpperCase()) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * replaceString
     * @author Anthony C. Liberto
     * @return boolean
     * @param oldValue
     * @param newValue
     * @param r
     * @param c
     */
    public boolean replaceString(String oldValue, String newValue, int r, int c) {
        return false;
    }

    /**
     * getKey
     * Filter Area
     *
     * @return String
     * @param _i
     */
    public String getKey(int _i) {
        Object o = cgtm.getRow(_i);
        if (o != null) {
            if (o instanceof EntityItem) {
                return ((EntityItem) o).getKey();
            } else if (o instanceof WhereUsedItem) {
                return ((WhereUsedItem) o).getKey();
            } else if (o instanceof LockItem) {
                return ((LockItem) o).getKey();
            } else if (o instanceof InactiveItem) {
                return ((InactiveItem) o).getKey();
            }
        }
        return null;
    }

    /**
     * isRowFiltered
     *
     * @param r
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRowFiltered(int r) {
        String key = getKey(r);
        return hiddenRows.containsKey(key);
    }

    /**
     * resizeCells
     *
     * @author Anthony C. Liberto
     */
    public void resizeCells() {
        //		if (true) {
        //			speedSizeCells();
        //			return;
        //		}
        //		Date start = new Date();
        int rr = getRowCount();
        int cc = getColumnCount();
        FontMetrics fm = null;
        int Width = 0;
        RSTableColumn tc = null;
        int h = -1;
        if (!eaccess().canSize(rr)) { //auto_sort/size
            return; //auto_sort/size
        } //auto_sort/size
        fm = getFontMetrics(getFont());
        for (int c = 0; c < cc; ++c) {
            if (isColumnVisible(c)) {
                Width = getWidth(fm, getColumnName(c));
                for (int r = 0; r < rr; ++r) {
                    //tableresize					Object o = getValueAt(r,c);
                    Object o = getDirectValueAt(r, c); //tableresize
                    Width = Math.max(Width, getWidth(fm, o));
                }
            } else {
                Width = 0;
            }
            tc = (RSTableColumn) getColumnModel().getColumn(c);

            tc.setWidth(Width);
            tc.setPreferredWidth(Width);
            //52728			tc.setMinWidth(tc.getMinimumPreferredWidth());		//15165
            tc.setMinWidth(tc.getMinimumAllowableWidth()); //52728
        }
        h = getRowHeight();
        for (int r = 0; r < rr; ++r) {
            if (isRowFiltered(r)) {
                setRowHeight(r, 0);
            } else {
                setRowHeight(r, h);
            }
        }
        //		Date stop = new Date();
        //		System.out.println("resize took " + eaccess().compareDate(start,stop) + " seconds.");
    }

    /**
     * getWidth
     * @param fm
     * @param o
     * @return
     * @author Anthony C. Liberto
     */
    protected int getWidth(FontMetrics fm, Object o) {
        int w = 10;
        if (o != null) {
            if (o instanceof String) {
                w += fm.stringWidth((String) o);
            } else if (o instanceof Integer) {
                w += fm.stringWidth(((Integer) o).toString());
            } else {
                w += fm.stringWidth(o.toString());
            }
        }
        return w;
    }

    /**
     * setCopyable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setCopyable(boolean _b) {
        copyable = _b;
    }

    /**
     * isCopyable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCopyable() {
        return copyable;
    }

    /**
     * copy
     *
     * @return
     * @author Anthony C. Liberto
     */
    public TransferObject copy() {
        return copy("copy");
    }

    /**
     * cut
     *
     * @return
     * @author Anthony C. Liberto
     */
    public TransferObject cut() {
        if (m_nav != null) {
            m_nav.cut();
        }
        return null;
    }

    /**
     * removeRow
     * @param _eg
     * @param _ei
     * @author Anthony C. Liberto
     * /
    public void removeRow(EntityGroup _eg, EntityItem[] _ei) {
        int ii = -1;
        if (_ei == null) {
            return;
        }
        ii = _ei.length;
        for (int i = 0; i < ii; ++i) {
            cgtm.removeRow(_ei[i]);
            firePropertyChange("REMOVE_ROW", null, _eg);
        }

    }*/

    /**
     * canPaste
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canPaste() {
        if (isEditable() && isEnabled()) {
            return canPaste(true);
        }
        return false;
    }

    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
        if (isEditable() && isEnabled()) {
            Transferable transItem = getClipboardContents();
            if (transItem != null && transItem instanceof TransferObject) {
                TransferObject trans = (TransferObject) transItem;
                try {
                    DataFlavor flavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
                    Object o = trans.getTransferData(flavor);
                    paste(trans.getType(), o);
                } catch (Exception e) {
                    EAccess.report(e,false);
                    getToolkit().beep();
                }
            } else {
                getToolkit().beep();
            }
        }
    }

    /**
     * copy
     * @param _type
     * @return
     * @author Anthony C. Liberto
     */
    private TransferObject copy(String _type) {
        EntityItem[] ei = null;
        String s = null;
        try {
            ei = (EntityItem[]) getSelectedObjects(false, true);
        } catch (OutOfRangeException _range) {
            _range.printStackTrace();
            //51260			setMessage(_range.toString());
            //51260			showError();
            showException(_range, FYI_MESSAGE, OK); //51260
            return null;
        }
        s = export(ei, false);
        if (s != null) { //19892
            return setClipboardContents(s, _type, ei);
        }
        return null; //19892
    }

    /**
     * getRelatorType
     * @author Anthony C. Liberto
     * @return MetaLink
     * @param _eiKid
     */
    protected MetaLink getRelatorType(EntityItem[] _eiKid) {
        if (_eiKid == null || _eiKid.length == 0) {
            return null;
        }

        return getRelatorType(_eiKid[0].getEntityGroup());
    }

    /**
     * paste
     * @param _type
     * @param _o
     * @author Anthony C. Liberto
     */
    private void paste(String _type, Object _o) {
        EntityList eList = null;
        if (_type == null || _o == null) {
            return;
        }
        if (_o instanceof EntityItem[]) {
            EntityItem[] kids = (EntityItem[]) _o;
            //20020304			EntityItem[] parents = (EntityItem[])getSelectedObjects(true);
            EntityItem[] parents = null;

            try {
                parents = (EntityItem[]) getSelectedObjects(false, true); //20020304
            } catch (OutOfRangeException _range) {
                _range.printStackTrace();
                //51260				setMessage(_range.toString());
                //51260				showError();
                showException(_range, FYI_MESSAGE, OK); //51260
                return;
            }
            if (kids == null || parents == null) {
                showError("msg2008");
                setBusy(false);
                return;
            }
            if (_type.equalsIgnoreCase("copy")) {
                MetaLink rel = getRelatorType(kids);

                //				String rel = getCopyRelatorType(kids);
                if (rel != null) {
                    link(parents, kids, rel, EANUtility.LINK_COPY, getNumberCopies("msg3013"));
                    setBusy(false);
                    return;
                } else {
                    showError("msg3006");
                    setBusy(false);
                    return;
                }
            } else if (_type.equalsIgnoreCase("cut")) {
                MetaLink rel = getRelatorType(kids);
                if (rel != null) {
                    link(parents, kids, rel, EANUtility.LINK_MOVE, 1);
                    setBusy(false);
                    return;
                } else {
                    showError("msg23011");
                    setBusy(false);
                    return;
                }
            }
        }

        if (eList == null) {
            showError("msg23011");
        }
        setBusy(false);
    }

    /**
     * link
     * @param _eiParent
     * @param _eiChild
     * @param _rel
     * @param _linkType
     * @param _linkCount
     * @return
     * @author Anthony C. Liberto
     */
    private String link(EntityItem[] _eiParent, EntityItem[] _eiChild, MetaLink _rel, int _linkType, int _linkCount) {
        if (_rel == null) {
            return null;
        }

        if (dBase().link("", _eiParent, _eiChild, _rel, _linkType, _linkCount)) {
            return reportLink(_eiParent, _eiChild);
        }
        return null;
    }

    /**
     * reportLink
     * @param _eiParent
     * @param _eiChild
     * @return
     * @author Anthony C. Liberto
     */
    private String reportLink(EntityItem[] _eiParent, EntityItem[] _eiChild) {
        StringBuffer sb = new StringBuffer();
        String msg = " " + getString("c2") + " ";
        int pp = _eiParent.length;
        int cc = _eiChild.length;
        for (int p = 0; p < pp; ++p) {
            for (int c = 0; c < cc; ++c) {
                sb.append(_eiParent[p].toString() + msg + _eiChild[c].toString() + RETURN);
            }
        }
        return sb.toString();
    }

    /**
     * getParentEntityType
     * @param _ei
     * @return
     * @author Anthony C. Liberto
     * /
    protected String getParentEntityType(EntityItem _ei) {
        return null;
    }*/

    /**
     * getRelators
     * @param _kids
     * @return
     * @author Anthony C. Liberto
     * /
    protected EntityItem[] getRelators(EntityItem[] _kids) {
        return null;
    }*/

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
    }
    /**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _r, int _c, EventObject _e) {
        return false;
    } //locklist
    /**
     * moveToError
     * @author Anthony C. Liberto
     * @param bre
     */
    public void moveToError(EANBusinessRuleException bre) {
    }
    /*
     * hide column
     */

    /**
     * setTableColumnWidth
     * @param _tc
     * @param _width
     * @author Anthony C. Liberto
     * /
    protected void setTableColumnWidth(RSTableColumn _tc, int _width) {
        boolean hidden = (_width == 0);
        if (_width == Integer.MAX_VALUE) {
            _width = 15;
        }
        if (hidden) {
            _tc.setMinWidth(_width);
            _tc.setMaxWidth(_tc.getWidth());
        } else {
            //52728			_tc.setMinWidth(_tc.getMinimumPreferredWidth());
            _tc.setMinWidth(_tc.getMinimumAllowableWidth());
        } //52728
        _tc.setWidth(_width);
        _tc.setPreferredWidth(_width);
        _tc.setResizable(!hidden); //19950
        return;
    }*/

    /**
     * isHidden
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isHidden() {
        int cc = getColumnCount();
        TableColumn tc = null;
        for (int c = 0; c < cc; ++c) {
            tc = getColumnModel().getColumn(c);
            if (tc.getWidth() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() { //21643
        EntityGroup eg = getEntityGroup();
        if (eg != null) {
            return "NAV" + eg.getKey();
        } else {
            return "NAV";
        }
    }

    /**
     * setNavigate
     * @param _nav
     * @author Anthony C. Liberto
     */
    public void setNavigate(Navigate _nav) { //51449
        m_nav = _nav;
    }

    /*
     52090
     */
    /**
     * @see javax.swing.JComponent#createToolTip()
     * @author Anthony C. Liberto
     */
    public JToolTip createToolTip() {
        EMToolTip tip = new EMToolTip();
        tip.setComponent(this);
        return tip;
    }

    /**
     * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public String getToolTipText(MouseEvent _me) {
        Point pt = _me.getPoint();
        int row = rowAtPoint(pt);
        int col = columnAtPoint(pt);
        if (isValidCell(row, col)) {
            EANAttribute att = getAttribute(row, col);
            if (att instanceof MultiFlagAttribute) {
                return Routines.getDisplayString(getValueAt(row, col, true).toString(), true); //22733
            } else if (att instanceof LongTextAttribute) { //22223
                return Routines.getDisplayString(getValueAt(row, col, true).toString(), true); //22733
            } else if (att instanceof XMLAttribute) { //22223
                return Routines.getDisplayString(getValueAt(row, col, true).toString(), true); //22612
            }
        }
        return null;
    }

    /**
     * @see javax.swing.JComponent#getToolTipLocation(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public Point getToolTipLocation(MouseEvent _me) {
        Point pt = _me.getPoint();
        int row = rowAtPoint(pt);
        int col = columnAtPoint(pt);
        if (isValidCell(row, col)) {
            Point out = getCellRect(row, col, true).getLocation();
            out.translate(-1, -2);
            return out;
        }
        return null;
    }

    /*
     accessibility
     */

    /**
     * isAccessibleCellEditable
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAccessibleCellEditable(int _row, int _col) {
        return false;
    }

    /**
     * isAccessibleCellLocked
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAccessibleCellLocked(int _row, int _col) {
        return false;
    }

    /*
     indicateRelations
     */
    /**
     * createTableModel
     * @author Anthony C. Liberto
     * @return rsTableModel
     * @param _o
     * @param _table
     * @param _type
     */
    protected RSTableModel createTableModel(Object _o, RowSelectableTable _table, int _type) {
        return new RSTableModel(_o, _table, _type) {
            public boolean isIndicateRelations() {
                EntityGroup eg = getEntityGroup();
                if (eg != null) {
                    EntityList list = eg.getEntityList();
                    if (list != null) {
                        return list.showRelParentChild(eg.getEntityType());
                    }
                }
                return false;
            }
        };
    }

    /*
     quick

     */
    /**
     * speedSizeCells
     * @author Anthony C. Liberto
     * /
    public void speedSizeCells() {
        Date start = new Date();
        int rr = getRowCount();
        int cc = getColumnCount();
        int Width = 0;
        FontMetrics fm = null;
        RSTableColumn tc = null;
        int h = -1;
        Date stop = null;
        if (!eaccess().canSize(rr)) {
            return;
        }
        fm = getFontMetrics(getFont());
        for (int c = 0; c < cc; ++c) {
            EANFoundation col = (EANFoundation) cgtm.getModelColumn(convertColumnIndexToModel(c));
            String sParent = ((EntityGroup) (col).getParent()).getEntityType();
            String sKey = col.getKey();
            if (isColumnVisible(c)) {
                Width = getWidth(fm, getColumnName(c));
                for (int r = 0; r < rr; ++r) {
                    EntityItem row = (EntityItem) cgtm.getModelRow(r);
                    Object o = null;
                    if (row.hasDownLinks()) {
                        EANEntity eanDown = row.getDownLink(0);
                        if (eanDown.getEntityType().equals(sParent)) {
                            o = eanDown.getAttribute(sKey);
                        }
                    }
                    if (o == null && row.hasUpLinks()) {
                        EANEntity eanUp = row.getUpLink(0);
                        if (eanUp.getEntityType().equals(sParent)) {
                            o = eanUp.getAttribute(sKey);
                        }
                    }
                    if (o == null) {
                        o = row.getAttribute(sKey);
                    }
                    Width = Math.max(Width, getWidth(fm, o));
                }
            } else {
                Width = 0;
            }
            tc = (RSTableColumn) getColumnModel().getColumn(c);
            tc.setWidth(Width);
            tc.setPreferredWidth(Width);
            tc.setMinWidth(tc.getMinimumAllowableWidth()); //52728
        }
        h = getRowHeight();
        for (int r = 0; r < rr; ++r) {
            if (isRowFiltered(r)) {
                setRowHeight(r, 0);
            } else {
                setRowHeight(r, h);
            }
        }
        stop = new Date();
        System.out.println("speedsize took " + eaccess().compareDate(start, stop) + " seconds.");
    }*/
    /*
     tablesort
     */
    /**
     * getSortDetails
     * @return
     * @author Anthony C. Liberto
     */
    public SortDetail getSortDetails() {
        return cgtm.getSortDetails();
    }

/*
 MN22319406
 */
	/**
     * resetFilter
     *
     * @author Anthony C. Liberto
     */
    public void resetFilter() {
		super.resetFilter();
		if (m_nav != null) {
			m_nav.updateMenuActions();
		}
	}

	/**
     * filter
     *
     * @author Anthony C. Liberto
     */
    public void filter() {
		super.filter();
		if (m_nav != null) {
			m_nav.updateMenuActions();
		}
	}
}
