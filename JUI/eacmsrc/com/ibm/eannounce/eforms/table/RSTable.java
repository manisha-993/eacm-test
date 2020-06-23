// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002-2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * $Log: RSTable.java,v $
 * Revision 1.9  2012/04/05 18:43:32  wendy
 * fillcopy/fillpaste perf updates
 *
 * Revision 1.8  2009/05/26 13:46:26  wendy
 * Performance cleanup
 *
 * Revision 1.7  2009/04/09 21:14:36  wendy
 * Use datamodel for source of column id
 *
 * Revision 1.6  2009/04/08 20:00:11  wendy
 * Sort needs more than column name to find the attribute
 *
 * Revision 1.5  2008/08/18 18:32:49  wendy
 * Show added row when import ss in horizontal mode
 *
 * Revision 1.4  2008/03/26 20:47:44  wendy
 * prevent NPE in getRowHeight(int)
 *
 * Revision 1.3  2008/02/19 16:41:48  wendy
 * Add support for importing an XLS file
 *
 * Revision 1.2  2007/08/15 15:11:24  wendy
 * RQ0713072645- Enhancement 3
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.6  2007/03/30 02:31:35  tony
 * VEEdit Fix
 *
 * Revision 1.5  2006/05/02 17:11:51  tony
 * CR103103686
 *
 * Revision 1.4  2005/11/04 22:58:47  tony
 * adjusted
 *
 * Revision 1.3  2005/10/31 17:22:00  tony
 * VEEdit_Iteration2
 * Added create logic.
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:06  tony
 * This is the initial load of OPICM
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.exception.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import com.ibm.eannounce.eforms.editor.*;
import com.ibm.eannounce.eforms.edit.*;
import com.ibm.eannounce.eforms.action.ActionController;
import com.ibm.eannounce.eforms.navigate.NavActionTree;
import com.ibm.eannounce.einterface.XMLInterface;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.datatransfer.*; //copy
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract class RSTable extends GTable implements ETable, ActionListener, 
	XMLInterface, Importable
{
    /**
     * sortable
     */
    protected boolean sortable = true;
    /**
     * searchable
     */
    protected boolean searchable = true;
    /**
     * replaceable
     */
    protected boolean replaceable = false;
    /**
     * fillable
     */
    protected boolean fillable = true;
    /**
     * filterable
     */
    protected boolean filterable = true;
    /**
     * copyable
     */
    protected boolean copyable = true;
    /**
     * popupDisplayable
     */
    protected boolean popupDisplayable = true;
    /**
     * columnOrderable
     */
 //   protected boolean columnOrderable = false;

    /**
     * filterOn
     */
 //   protected boolean filterOn = false;
    /**
     * textCol
     */
 //   protected final int textCol = 0;

    /**
     * foundItem
     */
    protected EVector foundItem = new EVector();
    /**
     * hiddenRows
     */
    protected EVector hiddenRows = new EVector();
    /**
     * cgtm
     */
    protected RSTableModel cgtm = null;

    private String rowKey = null;
    private String colKey = null;

    //	protected longPopup lPopup = new longPopup();

    /**
     * dummy
     */
    protected EventObject dummyEvent = null;

    private boolean updateArmed = true;
    /**
     * ec
     */
    protected EditController ec = null; //19937
    /**
     * ac
     */
    protected ActionController ac = null;

    /**
     * tree
     */
    protected NavActionTree tree = null;
    /**
     * pObject
     */
    protected Object pObject = null;
    /**
     * touch
     */
    protected HashMap touch = new HashMap(); //21825

    private TableCellEditor viewer = null;

    private ETransitionGroup eTran = null; //52189

    /**
     * rsTable
     * @author Anthony C. Liberto
     */
    public RSTable() {
        super();
        eTran = new ETransitionGroup(this); //52189
    }

    /**
     * rsTable
     * @param _o
     * @param _table
     * @param _type
     * @author Anthony C. Liberto
     */
    public RSTable(Object _o, RowSelectableTable _table, int _type) {
        this(_o, _table, null, null, _type);
    }

    /**
     * rsTable
     * @param _o
     * @param _table
     * @param _ac
     * @param _type
     * @author Anthony C. Liberto
     */
    public RSTable(Object _o, RowSelectableTable _table, ActionController _ac, int _type) {
        this(_o, _table, null, _ac, _type);
    }

    /**
     * rsTable
     * @param _o
     * @param _table
     * @param _ec
     * @param _type
     * @author Anthony C. Liberto
     */
    public RSTable(EntityList _o, RowSelectableTable _table, EditController _ec, int _type) {
        this(_o, _table, _ec, null, _type);
    }

    /**
     * rsTable
     * @param _o
     * @param _table
     * @param _ec
     * @param _ac
     * @param _type
     * @author Anthony C. Liberto
     */
    public RSTable(Object _o, RowSelectableTable _table, EditController _ec, ActionController _ac, int _type) {
        if (_table == null) {
            //52372			cgtm = new rsTableModel(_o,null,_type);
            cgtm = createTableModel(_o, null, _type); //52372
            setModel(cgtm);
        } else {
            init(_o, _table, _ec, _ac, _type);
        }
        //		init();
        dummyEvent = new EventObject(this);
        eTran = new ETransitionGroup(this); //52189
    }

    private void init(Object _o, RowSelectableTable _table, EditController _ec, ActionController _ac, int _type) {
        //51298		setEditable(_table.canEdit());
        setEditable(eaccess().isEditable(_table)); //51298
        setActionController(_ac);
        setEditController(_ec);
        if (_ac != null) {
            setActionTree(_ac.getTree());
        } else if (_ec != null) {
            setActionTree(_ec.getTree());
        }
        //52372		cgtm = new rsTableModel(_o,_table,_type);
        cgtm = createTableModel(_o, _table, _type); //52372
        setModel(cgtm);
    }

    /**
     * @see javax.swing.JTable#createDefaultColumnModel()
     * @author Anthony C. Liberto
     */
    protected TableColumnModel createDefaultColumnModel() { //21694
        return new RSTableColumnModel(); //21694
    } //21694

    /**
     * @see javax.swing.JTable#createDefaultColumnsFromModel()
     * @author Anthony C. Liberto
     */
    public void createDefaultColumnsFromModel() {
        if (cgtm != null) {
            int ii = cgtm.getColumnCount();

            int rows = cgtm.getRowCount();
            RSTableColumnModel cm = (RSTableColumnModel) getColumnModel();
            removeColumns(cm);
            for (int i = 0; i < ii; i++) {
                RSTableColumn newColumn = new RSTableColumn(i);
                newColumn.setKey(cgtm.getColumnKey(i));
                newColumn.setName(cgtm.getColumnName(i));
                if (rows > 0) {
                    newColumn.setMeta(cgtm.getEANObject(0, i));
                }
                //52728				newColumn.setMinWidth(newColumn.getMinimumPreferredWidth());
                newColumn.setMinWidth(newColumn.getMinimumAllowableWidth()); //52728
                addColumn(newColumn);
            }
            cm.sort(); //52196
        }
    }

    /**
     * removeColumns
     * @param _tcm
     * @author Anthony C. Liberto
     */
    public void removeColumns(TableColumnModel _tcm) {
        while (_tcm.getColumnCount() > 0) {
            TableColumn tc = _tcm.getColumn(0);
            _tcm.removeColumn(tc);
            tc.removePropertyChangeListener((DefaultTableColumnModel) _tcm);
        }
    }

    /**
     * setUpdateArmed
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUpdateArmed(boolean _b) {
        updateArmed = _b;
    }

    /**
     * isDynaTable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDynaTable() { //dyna
        return cgtm.isDynaTable(); //dyna
    } //dyna

    /**
     * setPopupDisplayable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setPopupDisplayable(boolean _b) {
        popupDisplayable = _b;
    }

    /**
     * getVisibleColumnNames
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getVisibleColumnNames() {
        int cc = getColumnCount();
        Vector v = new Vector();
        SortColumn[] out = null;
        for (int c = 0; c < cc; ++c) {
            if (isColumnVisible(c)) { //53469
                v.add(new SortColumn(getColumnName(c),convertColumnIndexToModel(c))); 
            }
        }
        out = new SortColumn[v.size()];
        return v.toArray(out);
    }

    /**
     * isMultiColumn
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMultiColumn() {
        if (getColumnCount() > 1) {
            return true;
        }
        return false;
    }

    /**
     * isPopupDisplayable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPopupDisplayable() {
        return popupDisplayable;
    }

    /**
     * modelChanged
     * @author Anthony C. Liberto
     */
    public void modelChanged() {
        cgtm.modelChanged();
    }

    /**
     * modelChanged
     *
     * @param _type
     * @author Anthony C. Liberto
     */
    public void modelChanged(int _type) {
        if (cgtm != null) {
            cgtm.modelChanged(_type);
        }
    }

    /**
     * resizeAndPaint
     *
     * @param _sort
     * @author Anthony C. Liberto
     */
    public void resizeAndPaint(boolean _sort) {
        resizeAndRepaint();
        if (_sort) {
            sort();
        }
    }

    /**
     * setSortable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSortable(boolean _b) {
        sortable = _b;
    }

    /**
     * isSortable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSortable() {
        return sortable;
    }

    /**
     * sort
     *
     * @author Anthony C. Liberto
     */
    public void sort() {
        if (isSortable()) {
            cgtm.sort();
        }
    }

    /**
     * sort
     *
     * @param _asc
     * @author Anthony C. Liberto
     */
    public void sort(boolean _asc) {
        if (isSortable()) {
            cgtm.sort(getSelectedColumn(), _asc);
        }
    }

    /**
     * sort
     *
     * @param _col
     * @param _asc
     * @author Anthony C. Liberto
     */
    public void sort(int _col, boolean _asc) {
        if (isSortable()) {
            cgtm.sort(_col, _asc);
        }
    }

    /**
     * sortHeader
     * @param _asc
     * @author Anthony C. Liberto
     */
    public void sortHeader(boolean _asc) {
        if (isSortable()) {
            cgtm.sortHeader(_asc);
        }
    }
    /*
     tablesort
    	public void sort(Comparator _c) {
    		if (isSortable()) cgtm.sort(_c);
    	}
    */
    /**
     * setSort
     * @param _sort
     * @param _modelChanged
     * @author Anthony C. Liberto
     */
    public void setSort(EANFoundation[] _sort, boolean _modelChanged) {
        cgtm.setSort(_sort, _modelChanged);
    }

    /**
     * getKeys
     * @return
     * @author Anthony C. Liberto
     */
    public EANFoundation[] getKeys() {
        if (cgtm != null) {
            return cgtm.getKeys();
        }
        return null;
    }

    /**
     * highlight
     * @param _keys
     * @author Anthony C. Liberto
     */
    public void highlight(String[] _keys) {
        boolean bClear = true; //22810
        int ii = -1;
        if (_keys == null) {
            return;
        }
        ii = _keys.length;
        for (int i = 0; i < ii; ++i) {
            int r = cgtm.getViewRowIndex(_keys[i]);
            if (r >= 0) { //kChu_20030710
                if (bClear) { //22810
                    bClear = false; //22810
                    clearSelection(); //22810
                    scrollToRow(r); //dwb_autoscroll
                } //22810
                addRowSelectionInterval(r, r);
            } //kChu_20030710
        }
    }

    /**
     * childHighlight
     * @param _s
     * @author Anthony C. Liberto
     */
    public void childHighlight(String[] _s) {
        childHighlight(getEntityItems(_s));
    }

    /**
     * childHighlight
     * @param _ei
     * @author Anthony C. Liberto
     */
    public void childHighlight(EntityItem[] _ei) {
        boolean bClear = true; //22810
        int ii = _ei.length;
        String key = null;
        int r = -1;
        for (int i = 0; i < ii; ++i) {
            /*
             USRO-R-DMKR-66STZL
            			if (_ei[i].hasDownLinks()) {
            				key = _ei[i].getDownLink(0).getKey();
            			} else {
            				key = _ei[i].getKey();
            			}
            */
            boolean bDown = _ei[i].hasDownLinks(); //USRO-R-DMKR-66STZL
            System.out.println("orig key(" + i + "): " + _ei[i].getKey());
            System.out.println("    isRelator: " + eaccess().isRelator(_ei[i]));
            System.out.println("    bDown: " + bDown);
            if (eaccess().isRelator(_ei[i]) || !bDown) { //USRO-R-DMKR-66STZL
                key = _ei[i].getKey(); //USRO-R-DMKR-66STZL
            } else { //USRO-R-DMKR-66STZL
                key = _ei[i].getDownLink(0).getKey(); //USRO-R-DMKR-66STZL
            } //USRO-R-DMKR-66STZL
            System.out.println("    returned key: " + key);
            r = getViewRowIndex(key);
            System.out.println("    rowIndex: " + r);
            if (r >= 0) {
                if (bClear) { //22810
                    bClear = false; //22810
                    clearSelection(); //22810
                    scrollToRow(r); //dwb_autoscroll
                } //22810
                addRowSelectionInterval(r, r);
            }
        }
        requestFocus();
    }

    /**
     * scrollToRow
     * @param _row
     * @author Anthony C. Liberto
     */
    public void scrollToRow(int _row) { //dwb_autoscroll
        if (isValidCell(_row, 0)) { //dwb_autoscroll
            scrollRectToVisible(getCellRect(_row, 0, true)); //dwb_autoscroll
        } //dwb_autoscroll
    } //dwb_autoscroll

    /**
     * scrollToColumn
     * @param _col
     * @author Anthony C. Liberto
     */
    public void scrollToColumn(int _col) { //dwb_autoscroll
        if (isValidCell(0, _col)) { //dwb_autoscroll
            scrollRectToVisible(getCellRect(0, _col, true)); //dwb_autoscroll
        } //dwb_autoscroll
    } //dwb_autoscroll

    /**
     * getViewRowIndex
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public int getViewRowIndex(String _s) {
        return cgtm.getViewRowIndex(_s);
    }

    /**
     * getHelpText
     * @return
     * @author Anthony C. Liberto
     */
    public String getHelpText() {
        int r = getSelectedRow();
        int c = convertColumnIndexToModel(getSelectedColumn());
        if (isValidCell(r, c)) {
            return cgtm.getHelp(cgtm.getRowKey(r), cgtm.getColumnKey(c));
        }
        return null;
    }

    /**
     * @see javax.swing.JTable#getRowHeight(int)
     * @author Anthony C. Liberto
     */
    public int getRowHeight(int _row) {
        if (_row < 0 || _row >= getRowCount()|| cgtm == null) {
            return getRowHeight();
        }
        return getRowHeight(cgtm.getRowKey(_row));
    }

    /**
     * getRowHeight
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public int getRowHeight(String _s) {
        int height = -1;
        if (_s == null || cgtm == null) { //52030
            return getRowHeight(); //52030
        } //52030
        height = cgtm.getRowHeight(_s);
        if (height < 0) {
            return getRowHeight();
        }
        return height;
    }

    /**
     * @see javax.swing.JTable#setRowHeight(int, int)
     * @author Anthony C. Liberto
     */
    public void setRowHeight(int _r, int _height) {
        cgtm.setRowHeight(cgtm.getRowKey(_r), _height);
    }

    /**
     * setRowHeight
     * @param _key
     * @param _height
     * @author Anthony C. Liberto
     */
    public void setRowHeight(String _key, int _height) {
        cgtm.setRowHeight(_key, _height);
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
     * isRowVisible
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRowVisible(String _key) {
        if (getRowHeight(_key) == 0) {
            return false;
        }
        return true;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isValidCell(int, int)
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
     * getCurrentEntityItem
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem getCurrentEntityItem(boolean _new) {
        int row = getSelectedRow();
        EntityItem ei = (EntityItem) cgtm.getRow(row);
        if (ei == null) {
            return null;
        } else if (_new) {
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
     * historyInfo
     * @author Anthony C. Liberto
     */
    public void historyInfo() {
        int r = getSelectedRow();
        int c = getSelectedColumn();
        if (isValidCell(r, c)) {
            EANAttribute att = getAttribute(r, c);
            eaccess().getChangeHistory(att);
        }
    }

    //cr_1310	public Object[] getSelectedObjects(boolean _new) throws outOfRangeException {		//51024
    /**
     * getSelectedObjects
     *
     * @param _new
     * @param _maxEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getSelectedObjects(boolean _new, boolean _maxEx) throws OutOfRangeException { //cr_1310
        int[] rows = getSelectedRows();
        int ii = rows.length;
        EntityItem[] out = null;
        if (ii <= 0) { //013598
            return null;
        } //013598

//CR103103686        if (_maxEx && ii > MAXIMUM_EDIT_ROW) { //50802
		if (_maxEx && ii > eaccess().getRecordLimit()) {		//CR103103686
            setCode("msg12003.0"); //50999
//CR103103686            setParm(Routines.toString(MAXIMUM_EDIT_ROW)); //50999
			setParm(Routines.toString(eaccess().getRecordLimit()));		//CR103103686
            //51024			eaccess().showError(this);		//50999
            throw new OutOfRangeException(getMessage()); //51024
        } //50802

        out = new EntityItem[ii];
        for (int i = 0; i < ii; ++i) {
            EntityItem ei = (EntityItem) cgtm.getRow(rows[i]);
            if (_new) {
                try {
                    out[i] = new EntityItem(ei);
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                }
            } else {
                out[i] = ei;
            }
        }
        return out;
    }

    /**
     * getSelectedObject
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSelectedObject(boolean _new) {
        int row = getSelectedRow();
        if (row < 0 || row >= getRowCount()) {
            return null;
        }
        return cgtm.getRow(row);
    }

    //cr_1310	public Object[] getVisibleObjects(boolean _new) throws outOfRangeException {	//51024
    /**
     * getVisibleObjects
     *
     * @param _new
     * @param _maxEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getVisibleObjects(boolean _new, boolean _maxEx) throws OutOfRangeException { //cr_1310
        Vector v = new Vector();
        int rr = getRowCount();
        int ii = -1;
        EntityItem[] out = null;
//CR103103686        if (_maxEx && rr > MAXIMUM_EDIT_ROW) { //50802
		if (_maxEx && rr > eaccess().getRecordLimit()) {	//CR103103686
            setCode("msg12003.0"); //50999
//CR103103686            setParm(Routines.toString(MAXIMUM_EDIT_ROW)); //50999
			setParm(Routines.toString(eaccess().getRecordLimit()));		//CR103103686
            //51024			eaccess().showError(this);		//50999
            throw new OutOfRangeException(getMessage()); //51024
        } //50802

        for (int r = 0; r < rr; ++r) {
            if (isRowVisible(r)) {
                v.add(cgtm.getRow(r));
            }
        }
        if (v.isEmpty()) {
            return null;
        }
        ii = v.size();
        out = new EntityItem[ii];
        for (int i = 0; i < ii; ++i) {
            if (_new) {
                try {
                    out[i] = new EntityItem((EntityItem) v.get(i));
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                }
            } else {
                out[i] = (EntityItem) v.get(i);
            }
        }
        return out;
    }

    /**
     * getEntityItems
     * @param _keys
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getEntityItems(String[] _keys) {
        int ii = _keys.length;
        Vector v = new Vector();
        int xx = -1;
        EntityItem[] ei = null;
        for (int i = 0; i < ii; ++i) {
            Object o = cgtm.getRowByKey(_keys[i]);
            if (o instanceof EntityItem) {
                v.add(o);
            } else if (o instanceof WhereUsedItem) {
                WhereUsedItem wui = (WhereUsedItem) o;
                v.add(wui.getRelatedEntityItem());
            }
        }
        if (v.isEmpty()) {
            return null;
        }
        xx = v.size();
        ei = new EntityItem[xx];
        for (int x = 0; x < xx; ++x) {
            ei[x] = (EntityItem) v.get(x);
        }
        return ei;
    }

    /**
     * getSelectedVisibleRows
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int[] getSelectedVisibleRows() {
        Vector v = new Vector();
        int rows[] = getSelectedRows();
        int ii = rows.length;
        int[] out = null;
        for (int i = 0; i < ii; ++i) {
            if (isRowVisible(rows[i])) {
                v.add(new Integer(rows[i]));
            }
        }
        ii = v.size();
        out = new int[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = ((Integer) v.get(i)).intValue();
        }
        return out;
    }

    /**
     * getEntityGroup
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getEntityGroup() {
        Object o = cgtm.getObject();
        if (o instanceof EntityGroup) {
            return (EntityGroup) o;
        }
        return null;
    }

    /**
     * getInformation
     * @return
     * @author Anthony C. Liberto
     */
    protected String getInformation() {
        int r = getSelectedRow();
        int c = getSelectedColumn();
        if (isValidCell(r, c)) {
            EANAttribute att = getAttribute(r, c);
            return Routines.getInformation(att);
        }
        return getString("nia"); //20094
    }


    /**
     * getKey
     * @param _att
     * @return
     * @author Anthony C. Liberto
     */
    protected String getKey(EANAttribute _att) { //21825
        EANFoundation parent = _att.getParent(); //21825
        if (parent instanceof EntityItem) { //21825
            return ((EntityItem) parent).getKey() + _att.getAttributeCode(); //21825
        } //21825
        return _att.getAttributeCode() + _att.getKey(); //21825
    } //21825


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
        return isEditable(); //21818
    }

    /**
     * isReplaceableAttribute
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isReplaceableAttribute(int _r, int _c) { //22632
        EANAttribute att = getAttribute(_r, _c); //22632
        if (att != null) { //22632
            if (att instanceof TextAttribute) { //22632
                return true; //22632
            } else if (att instanceof LongTextAttribute) { //22632
                return true; //22632
                //51248			} else if (att instanceof XMLAttribute) {				//22632
                //51248				return true;										//22632
            } //22632
        } //22632
        return false; //22632
    } //22632

    /**
     * findValue
     * @author Anthony C. Liberto
     * @param Multi
     * @param _forReplace
     * @param find
     * @param increment
     * @param strCase
     */
    public void findValue(String find, boolean Multi, boolean strCase, int increment, boolean _forReplace) {
        if (isSearchable()) {
           getNextValue(find, Multi, strCase, increment, getSelectedRow(), getSelectedColumn(), _forReplace);
        }        
    }

    /**
     * replaceValue
     * @author Anthony C. Liberto
     * @param Multi
     * @param find
     * @param increment
     * @param replace
     * @param strCase
     */
    public void replaceValue(String find, String replace, boolean Multi, boolean strCase, int increment) {
        replaceValue(find, replace, Multi, strCase, increment, true); //23269
    } //23269

    private void replaceValue(String find, String replace, boolean Multi, boolean strCase, int increment, boolean _rprt) { //23269
        int r = -1;
        int c = -1;
        String str = null;
        if (!isSearchable() || !isReplaceable()) {
            return;
        }
        r = getSelectedRow();
        c = getSelectedColumn();
        if (r < 0) {
            r = 0;
        }
        if (c < 0) {
            c = 0;
        }
        str = getString(r, c, false); //22805
        if (isFound(str, find, strCase)) {
            if (isCellLocked(r, c, true)) { //23157
                if (!isCellEditable(r, c)) { //22805
                    showError("msg11023.0"); //22805
                } else { //22805
                    if (!isReplaceableAttribute(r, c)) { //22632
                        showError("msg3005"); //22632
                        return; //22632
                    } //22632
                    replaceString(find, replace, r, c);
                } //22805
            } else { //51258
                showLockInformation(r, c); //51258
            } //23157
        } else {
            if (_rprt) { //23269
                setCode("msg3003");
                setParm(find);
                showError();
            }
        } //21825
    }

    /**
     * replaceNextValue
     * @author Anthony C. Liberto
     * @param Multi
     * @param find
     * @param increment
     * @param replace
     * @param strCase
     */
    public void replaceNextValue(String find, String replace, boolean Multi, boolean strCase, int increment) {
        if (!isSearchable() || !isReplaceable()) {
            return;
        }
        replaceValue(find, replace, Multi, strCase, increment, false); //23269
        findValue(find, Multi, strCase, increment, false); //22805
    }

    /**
     * replaceAllValue
     * @author Anthony C. Liberto
     * @param Multi
     * @param find
     * @param increment
     * @param replace
     * @param strCase
     */
    public void replaceAllValue(String find, String replace, boolean Multi, boolean strCase, int increment) {
        int rr = -1;
        int cc = -1;
        if (!isSearchable() || !isReplaceable()) {
            return;
        }
        rr = getRowCount();
        cc = getColumnCount();
        for (int c = 0; c < cc; ++c) {
            for (int r = 0; r < rr; ++r) {
                String str = getString(r, c, true);
                if (isFound(str, find, strCase)) {
                    if (isCellLocked(r, c, true)) { //23157
                        if (isCellEditable(r, c)) { //23157
                            if (isReplaceableAttribute(r, c)) { //23157
                                if (canTouch(r, c)) { //21825
                                    setFound(r, c); //23355
                                    replaceString(find, replace, r, c);
                                } //21825
                            } //23157
                        } //23157
                    } //23157
                } //21825
            }
        }
        resizeCells(); //52045
        setCode("msg11024.0"); //23269
        setParmCount(2); //23269
        setParm(0, find); //23269
        setParm(1, replace); //23269
        showFYI(); //23269
    }

    /**
     * resetTouch
     *
     * @author Anthony C. Liberto
     */
    public void resetTouch() { //21825
        touch.clear(); //21825
    } //21825

    /**
     * resetFound
     *
     * @author Anthony C. Liberto
     */
    public void resetFound() {
        foundItem.clear();
        repaint();
        requestFocus();
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

    private String getStringReplace(int _r, int _c) {
        if (isEditable(_r, _c)) {
            return getStringSearch(_r, _c);
        }
        return "";
    }

    private String getStringSearch(int r, int c) {
        if (isValidCell(r, c)) {
            Object o = getValueAt(r, c);
            if (o != null) {
                return o.toString();
            }
        }
        return "";
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#getString(int, int, boolean)
     * @author Anthony C. Liberto
     */
    public String getString(int _r, int _c, boolean _forReplace) {
        if (_forReplace) {
            return getStringReplace(_r, _c);
        }
        return getStringSearch(_r, _c);
    }

    /**
     * getString
     * @param _key
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    protected String getString(String _key, int c) {
        Object o = getValueAt(_key, c);
        if (o != null) {
            return o.toString();
        }
        return "";
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#setFound(int, int)
     * @author Anthony C. Liberto
     */
    protected void setFound(int r, int c) {
        String key = getKey(r);
        foundItem.putMap(key, Integer.toString(convertColumnIndexToModel(c)));
        findPt.x = r;
        findPt.y = c;
        setRowSelectionInterval(r, r);
        setColumnSelectionInterval(c, c);
        scrollRectToVisible(getCellRect(r, c, true));
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isFound(int, int)
     * @author Anthony C. Liberto
     */
    protected boolean isFound(int r, int c) {
        String key = getKey(r);
        if (key == null || foundItem == null) { //52030
            return false; //52030
        } //52030
        if (!foundItem.containsKey(key)) {
            return false;
        }
        return foundItem.mapContains(key, Integer.toString(convertColumnIndexToModel(c)));
    }

    /**
     * isFound
     * @author Anthony C. Liberto
     * @return boolean
     * @param caseSensitive
     * @param find
     * @param str
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
     * @param oldValue
     * @param newValue
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public abstract boolean replaceString(String oldValue, String newValue, int _r, int _c);

    /**
     * canTouch
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canTouch(int _r, int _c) { //21825
        return true; //21825
    } //21825
    /**
     * Filter Area
     *
     * @param _b
     */
    public void setFilterable(boolean _b) {
        filterable = _b;
    }

    /**
     * isFilterable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFilterable() {
        return filterable;
    }

    /**
     * filter
     *
     * @author Anthony C. Liberto
     */
    public void filter() {
        if (saveCurrentEdit()) {
            cgtm.filter();
            resizeAndRepaint();
            setFilter(isFiltered());
            clearSelection(); //52143
            if (getRowCount() > 0) { //52143
                if (getColumnCount() > 0) { //52143
                    setRowSelectionInterval(0, 0); //52143
                    setColumnSelectionInterval(0, 0); //52143
                    scrollToRow(0); //52143
                } //52143
            } //52143
        }
    }

    /**
     * resetFilter
     *
     * @author Anthony C. Liberto
     */
    public void resetFilter() {
        cgtm.resetFilter();
        resizeAndRepaint();
        setFilter(isFiltered());
    }

    /**
     * getColumnKey
     * @author Anthony C. Liberto
     * @return String
     * @param _header
     */
    public String getColumnKey(String _header) {
        TableColumnModel tcm = getColumnModel();
        RSTableColumn col = null;
        int ii = tcm.getColumnCount();
        for (int i = 0; i < ii; ++i) {
            col = (RSTableColumn) tcm.getColumn(i);
            if (col.toString().equals(_header)) {
                return col.key();
            }
        }
        return "OFSTATUS";
    }

    /**
     * getKey
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey(int i) {
        if (cgtm != null) { //52030
            return cgtm.getRowKey(i);
        } //52030
        return null; //52030
    }

    /**
     * getSelectedKeys
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getSelectedKeys() {
        int[] rows = getSelectedRows();
        int ii = rows.length;
        String[] out = new String[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = getKey(rows[i]);
        }
        return out;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isRowFiltered(int)
     * @author Anthony C. Liberto
     */
    public boolean isRowFiltered(int r) {
        String key = getKey(r);
        return isFiltered(key);
    }

    /**
     * isFiltered
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFiltered(String _key) {
        return hiddenRows.containsKey(_key);
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
        if (isEditing()) {
            TableCellEditor tce = getCellEditor();
            if (tce instanceof EditorInterface) {
                ((EditorInterface) tce).copy();
            }
            return null;
        }
        return copy("copy");
    }

    /**
     * cut
     *
     * @return
     * @author Anthony C. Liberto
     */
    public TransferObject cut() {
        if (isEditing()) {
            TableCellEditor tce = getCellEditor();
            if (tce instanceof EditorInterface) {
                ((EditorInterface) tce).cut();
            }
            return null;
        }
        return copy("cut");
    }

    /**
     * canPaste
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canPaste() {
        return false;
    }

    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
        Transferable trans = null;
        System.out.println("paste()");
        if (isEditing()) {
            TableCellEditor tce = getCellEditor();
            if (tce instanceof EditorInterface) {
                System.out.println("    I am editing so rely on the editor");
                ((EditorInterface) tce).paste();
            }
        }else {
        	trans = getClipboardContents();
        	if (trans != null) {
        		try {
        			String strPaste = (String) trans.getTransferData(DataFlavor.stringFlavor);
        			System.out.println("    pasteing: " + strPaste);
        			if (Routines.isDelimited(strPaste, DELIMIT_CHAR)) {
        				System.out.println("        I am delimited");
        				paste(Routines.getStringArray(strPaste, RETURN), getSelectedRow(), getSelectedColumn());
        			} else {
        				System.out.println("        relying on the editor");
        				paste(getSelectedRow(), getSelectedColumn());
        			}
        		} catch (Exception _ufe) {
        			_ufe.printStackTrace();
        			paste(getSelectedRow(), getSelectedColumn());
        		}
        	} else {
        		System.out.println("paste relying on editor");
        		paste(getSelectedRow(), getSelectedColumn());
        	}
        }
    }

    /**
     * paste
     * @param _row
     * @param _col
     * @author Anthony C. Liberto
     */
    protected void paste(int _row, int _col) {
        if (editCellAt(_row, _col, dummyEvent)) {
            TableCellEditor tce = getCellEditor(); //52051
            if (tce != null) { //52051
                if (tce instanceof TextEditor) { //52051
                    ((TextEditor) tce).caretToEnd(); //52051
                } //52051
                System.out.println("paste on the editor " + tce.getClass().getName());
                ((EditorInterface) tce).paste(); //52051
            } //52051
		} else {
            System.out.println("i am not editable");
        }
    }

    /**
     * paste
     * @param _sArray
     * @param _rowStart
     * @param _colStart
     * @author Anthony C. Liberto
     */
    protected void paste(String[] _sArray, int _rowStart, int _colStart) {
        if (_sArray != null) {
            int ii = _sArray.length;
            int rows = getRowCount();
            int cols = getColumnCount();
            for (int i = 0; i < ii; ++i) {
                //				appendLog("Record " + i + " of " + ii + " is: "+ _sArray[i]);
                String[] tmp = Routines.getStringArray(_sArray[i], DELIMIT_CHAR);
                int xx = tmp.length;
                int row = _rowStart + i;
                if (!hasErrors(_rowStart, ii, _colStart, xx)) {
                    if (row > 0 && row < rows) {
                        for (int x = 0; x < xx; ++x) {
                            int col = _colStart + x;
                            if (col > 0 && col < cols) {
                                if (isValidCell(row, col)) {
                                    //									appendLog("set column " + col + " for row (" + row + ") to: " + tmp[x]);
                                    putValueAt(tmp[x], row, col);
                                }
                            }
                        }
                    }
                } else {
                    break;
                }
            }
        }
    }

    /**
     * hasErrors
     * @param _rowStart
     * @param _rows
     * @param _colStart
     * @param _cols
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean hasErrors(int _rowStart, int _rows, int _colStart, int _cols) {
        boolean error = false;
        for (int r = _rowStart; r < (_rowStart + _rows); ++r) {
            for (int c = _colStart; c < (_colStart + _cols); ++c) {
                if (isValidCell(r, c)) {
                    if (!isCellLocked(r, c, true)) {
                        setCode("msg11011.2");
                        error = true;
                        break;
                    }
                    if (!isPasteable(r, c)) {
                        setCode("msg11011.3");
                        error = true;
                        break;
                    }
                    if (!isCellEditable(r, c)) {
                        setCode("msg11011.4");
                        error = true;
                        break;
                    }
                }
            }
        }
        repaint();
        if (error) {
            showError();
        }
        return error;
    }

    private TransferObject copy(String _type) {
        boolean tmpBoo = false;
        int[] rows = null;
        int[] cols = null;
        int rr = -1;
        int cc = -1;
        StringBuffer sb = null;
        if (_type.equals("cut") && !cgtm.canEdit()) {
            return null;
        }
        tmpBoo = cgtm.isLongDescriptionEnabled();
        cgtm.setLongDescription(true);
        rows = getSelectedRows();
        cols = getSelectedColumns();
        rr = rows.length;
        cc = cols.length;
        sb = new StringBuffer();
        for (int r = 0; r < rr; ++r) {
            for (int c = 0; c < cc; ++c) {
                Object o = getValueAt(rows[r], cols[c]);
                if (_type.equals("cut")) {
                    if (isCellLocked(rows[r], cols[c], true) && isCellEditable(rows[r], cols[c])) { //22039
                        putValueAt(null, rows[r], cols[c]); //acl_20021015
                        paintImmediately(getCellRect(rows[r], cols[c], false));
                    } else {
                        showError("msg23013"); //22039
                        return null;
                    }
                }
                if (c > 0) {
                    sb.append(DELIMIT_CHAR);
                }
                //52617				sb.append(routines.replaceChar(o.toString(),'\n',','));
                sb.append(o.toString()); //52617
            }
            sb.append(RETURN);
        }
        cgtm.setLongDescription(tmpBoo);
        return setClipboardContents(sb.toString(), _type, null);
    }

    /**
     * print
     *
     * @author Anthony C. Liberto
     */
    public void print() {
        PrintTable pTable = new PrintTable((RSTableModel) getModel());
        pTable.updateFont(getFont()); //52029
        print(pTable);
        //		print(this);
    }

    /**
     * export
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String export() {
        EntityGroup eg = getEntityGroup();
        EntityItem[] ei = null;
        int ii = -1;
        if (eg == null || eg.getEntityItemCount() == 0) {
            return null;
        }
        ii = eg.getEntityItemCount();
        ei = new EntityItem[ii];
        for (int i = 0; i < ii; ++i) {
            ei[i] = eg.getEntityItem(i);
        }
        return export(ei, true);
    }


    /**
     * export
     *
     * @param _includeHeaders
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    protected String export(boolean _includeHeaders) throws OutOfRangeException {
        return export((EntityItem[]) getSelectedObjects(false, true), _includeHeaders);
    }

    /**
     * export
     * @param _ei
     * @param _includeHeaders
     * @return
     * @author Anthony C. Liberto
     */
    protected String export(EntityItem[] _ei, boolean _includeHeaders) {
        int rr = _ei.length;
        int cc = getColumnCount();
        StringBuffer out = new StringBuffer();
        if (_includeHeaders) {
            out.append('"');
            out.append("EntityType");
            out.append('"');
            out.append(",");
            out.append('"');
            out.append("EntityID");
            out.append('"');
            out.append(",");
            for (int c = 0; c < cc; ++c) {
                out.append('"');
                out.append(getColumnName(c));
                out.append('"');
                if (c < (cc - 1)) {
                    out.append(",");

                } else {
                    out.append(RETURN);
                }
            }
        }
        for (int r = 0; r < rr; ++r) {
            if (_includeHeaders) {
                out.append('"');
                out.append(_ei[r].getEntityType());
                out.append('"');
                out.append(",");
                out.append('"');
                out.append(_ei[r].getEntityID());
                out.append('"');
                out.append(",");
            }
            for (int c = 0; c < cc; ++c) {
                out.append('"');
                out.append(getString(_ei[r].getKey(), c)); //21523
                //21523				out.append(getValueAt(r,c).toString());
                out.append('"');
                if (c < (cc - 1)) {
                    out.append(",");

                } else {
                    out.append(RETURN);
                }
            }
        }
        return out.toString();
    }

    /**
     * getNumberCopies
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    protected int getNumberCopies(String _code) {
        return eaccess().getNumber(_code);
    }

    /**
     * getRelatorType
     * @param _eiKid
     * @return
     * @author Anthony C. Liberto
     */
    protected MetaLink getRelatorType(EntityItem[] _eiKid) {
        if (_eiKid == null || _eiKid.length == 0) {
            return null;
        }
        return getRelatorType(_eiKid[0].getEntityGroup());
    }

    /**
     * getRelatorType
     * @param _egKid
     * @return
     * @author Anthony C. Liberto
     */
    protected MetaLink getRelatorType(EntityGroup _egKid) {
        MetaLink[] metaLink = getEntityGroup().getMetaLinkList(_egKid);
        if (metaLink.length == 0) {
            return null;
        } else if (metaLink.length == 1) {
            return metaLink[0];
        }
        return eaccess().getRelator(metaLink);
    }

    /**
     * showFind
     *
     * @author Anthony C. Liberto
     */
    public void showFind() {
        //50874		eaccess().setPanelObject(FIND_PANEL,this);
        //5ZBTCQ		eaccess().show(FIND_PANEL,false);
        eaccess().show((Component) this, FIND_PANEL, false); //5ZBTCQ
    }

    /**
     * showFilter
     *
     * @author Anthony C. Liberto
     */
    public void showFilter() {
        //50874		eaccess().setPanelObject(FILTER_PANEL,this);
        //5ZBTCQ		eaccess().show(FILTER_PANEL,false);
        eaccess().show((Component) this, FILTER_PANEL, false); //5ZBTCQ
    }

    /**
     * showInformation
     *
     * @author Anthony C. Liberto
     */
    public void showInformation() {
        eaccess().showScrollDialog(getInformation());
    }

    /**
     * getColumn
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public RSTableColumn getColumn(int _col) {
        TableColumnModel tcm = getColumnModel();
        return (RSTableColumn) tcm.getColumn(_col);
    }
    /*
     dep
    	public booleanTableAttribute getRowBooleanTableAttribute(String _key) {
    		return new booleanTableAttribute(_key,getString(_key,0),isRowVisible(_key),isFiltered(_key));
    	}
    */
    /**
     * updateModel
     * @param _table
     * @author Anthony C. Liberto
     */
    public void updateModel(RowSelectableTable _table) {
        cgtm.updateModel(_table);
        resizeCells();
    }

    /**
     * setObject
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setObject(Object _o) {
        cgtm.setObject(_o);
    }

    /**
     * getFilterGroup
     *
     * @return
     * @author Anthony C. Liberto
     */
    public FilterGroup getFilterGroup() {
        return cgtm.getFilterGroup();
    }

    /**
     * getColFilterGroup
     *
     * @return
     * @author Anthony C. Liberto
     */
    public FilterGroup getColFilterGroup() {
        return cgtm.getColFilterGroup();
    }
 
    /**
     * getViewport
     * @return
     * @author Anthony C. Liberto
     */
    public JViewport getViewport() {
        Container parent = getParent();
        if (parent != null && parent instanceof JViewport) {
            return (JViewport) parent;
        }
        return null;
    }

    /**
     * getScrollPane
     * @return
     * @author Anthony C. Liberto
     */
    public EScrollPane getScrollPane() {
        JViewport view = getViewport();
        if (view != null) {
            Container parent = view.getParent();
            if (parent != null && parent instanceof EScrollPane) {
                return (EScrollPane) parent;
            }
        }
        return null;
    }

    /**
     * setViewportSize
     * @param _d
     * @author Anthony C. Liberto
     */
    public void setViewportSize(Dimension _d) {
        EScrollPane gsp = null;
        if (_d == null) {
            return;
        }
        gsp = getScrollPane();
        if (gsp != null) {
            JViewport view = gsp.getRowHeader();
            if (view != null) {
                view.setSize(_d);
                view.setPreferredSize(_d);
                view.setViewSize(_d);
            }
        }
    }

    /**
     * getTableWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getTableWidth() {
        RSTableColumnModel tcm = (RSTableColumnModel) getColumnModel();
        return tcm.getTotalColumnWidth();
    }

    /**
     * getTableHeight
     * @return
     * @author Anthony C. Liberto
     */
    public int getTableHeight() {
        int height = getRowHeight();
        int rows = getRowCount();
        return (height * rows);
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#dereference()
     * @author Anthony C. Liberto
     */
    public void dereference() {
        TableColumnModel cm = null;
        clearSelection(); //53809:78660D
        if (touch != null) {
            touch.clear(); //21825
            touch = null; //21825
        }
		initAccessibility(null);
        tree = null;
        cm = getColumnModel();

        removeColumns(cm);
        if (foundItem != null) {
            foundItem.removeAll();
            foundItem = null;
        }
        if (hiddenRows != null) {
            hiddenRows.removeAll();
            hiddenRows = null;
        }
        if (cgtm != null) {
            cgtm.dereference();
            cgtm = null;
        }
        ec = null;
        ac = null;
        dummyEvent = null;
        if (eTran != null) { //52189
            eTran.dereference(); //52189
            eTran = null; //52189
        } //52189
        
        super.dereference();
    }
    /*
     * editing stuff
     */
    /**
     * viewCellAt
     * @param _r
     * @param _c
     * @param _e
     * @author Anthony C. Liberto
     */
    protected void viewCellAt(int _r, int _c, EventObject _e) { //22248
        Rectangle rect = null;
        if (isViewing()) { //22248
            removeViewer();
        } //22248
        viewer = getCellViewer(_r, _c); //22248
        if (viewer != null) { //22248
            setColumnSelectionInterval(_c, _c); //22248
            setRowSelectionInterval(_r, _r); //22248
            if (viewer instanceof LongEditor) { //kc
                editorComp = null; //kc
            } else { //kc
                if (viewer instanceof BlobEditor) { //51559
                    if (isModalCursor()) { //51559
                        ((BlobEditor) viewer).setModalCursor(true); //51559
                    }
                }
                editorComp = prepareViewer(viewer, _r, _c); //22248
            } //kc
            setViewingRow(_r); //22248
            setViewingColumn(_c); //22248
            if (editorComp == null) { //22248
                removeViewer(); //22248
                return; //22248
            } //22248
            rect = getCellRect(_r, _c, false); //22248
            editorComp.setBounds(rect); //22248
            scrollRectToVisible(rect); //22248
            add(editorComp); //22248
            editorComp.validate(); //22248
            setCellEditor(viewer); //22248
            viewer.addCellEditorListener(this); //22248
            revalidate(); //52384
        } //22248
    } //22248

    /**
     * removeViewer
     * @author Anthony C. Liberto
     */
    protected void removeViewer() { //22248
        Rectangle cellRect = null;
        if (viewer != null) { //22248
            viewer.removeCellEditorListener(this); //22248
            //22248
            requestFocus(); //22248
            if (editorComp != null) { //22248
                if (viewer instanceof EditorInterface) { //22248
                    ((EditorInterface) viewer).removeEditor(); //22248
                }
                remove((Component) viewer); //22248
            } //22248
            //22248
            cellRect = getCellRect(editingRow, editingColumn, false); //22248
            //22248
            setCellEditor(null); //22248
            setEditingColumn(-1); //22248
            setEditingRow(-1); //22248
            viewer = null; //22248
            repaint(cellRect); //22428
        } //22428
    } //22428

    /**
     * isViewing
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isViewing() {
        return (viewer != null);
    }

    /**
     * setViewingRow
     * @param _r
     * @author Anthony C. Liberto
     */
    protected void setViewingRow(int _r) { //22248
        setEditingRow(_r); //22248
    } //22248
    //22248
    /**
     * setViewingColumn
     * @param _c
     * @author Anthony C. Liberto
     */
    protected void setViewingColumn(int _c) { //22248
        setEditingColumn(_c); //22248
    } //22248
    //22248
    /**
     * getCellViewer
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public TableCellEditor getCellViewer(int _r, int _c) { //22248
        return null; //22248
    } //22248

    /**
     * prepareViewer
     * @param _editor
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public Component prepareViewer(TableCellEditor _editor, int _row, int _col) { //22248
        return prepareEditor(_editor, _row, _col); //22248
    } //22248

    /**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _row, int _col, EventObject _e) {
        TableCellEditor tce = null;
		if (cellEditor != null && !cellEditor.stopCellEditing()) {
            return false;
        } else if (!isValidCell(_row, _col)) {
            return false;
        } else if (!isCellEditable(_row, _col)) {
            viewCellAt(_row, _col, _e);
            return false;
        }
        tce = getCellEditor(_row, _col);
        if (tce != null && tce.isCellEditable(_e)) {
            editorComp = prepareEditor(tce, _row, _col);
            if (editorComp == null) {
                removeEditor();
                return false;
            } else {
                if (!(editorComp instanceof LongEditor)) {
                    editorComp.setBounds(getCellRect(_row, _col, false));
                    add(editorComp);
                    editorComp.validate();
                }
                setCellEditor(tce);
                setEditingRowKey(_row);
                setEditingRow(_row);
                setEditingColumnKey(_col);
                setEditingColumn(_col);
                tce.addCellEditorListener(this);
                return true;
            }
        }
        return false;
    }

    /**
     * @see javax.swing.event.CellEditorListener#editingStopped(javax.swing.event.ChangeEvent)
     * @author Anthony C. Liberto
     */
    public void editingStopped(ChangeEvent _ce) {
        // Take in the new value
        TableCellEditor editor = getCellEditor();
        if (editor != null) {
            boolean bRequestFocus = !(editor instanceof LongEditor);
            Object value = editor.getCellEditorValue();
            if (editor instanceof EditorInterface) { //22588
                EANMetaAttribute meta = ((EditorInterface) editor).getMetaAttribute(); //22098
                if (putValueAt(value, rowKey, colKey)) { //acl_20021015
                    //50409					removeEditor();
                    removeEditor(editor);
                    if (meta != null && meta.isClassified() && isVerticalTableFormat()) { //22098
                        refreshClassification(); //22098
                    } //22098
                } else if (meta instanceof MetaStatusAttribute) { //53270
                    removeEditor(editor); //53270
                } //acl_20021015
                if (value != null && value instanceof MetaFlag[]) {
                    processFlags((MetaFlag[]) value);
                }
            } else if (editor instanceof CrossTabEditor) { //22588
                putValueAt(value, rowKey, colKey); //22588
                //50409				removeEditor();																//22588
                removeEditor(editor); //50409
            } //22588
            if (bRequestFocus) {
                requestFocus();
            }
        } //50409
    }

    /**
     * processFlags
     * @param _flags
     * @author Anthony C. Liberto
     */
    protected void processFlags(MetaFlag[] _flags) {
    }

    /**
     * removeEditor
     * @param _edit
     * @author Anthony C. Liberto
     */
    public void removeEditor(TableCellEditor _edit) {
        Rectangle cellRect = null;
        if (_edit != null) {
            _edit.removeCellEditorListener(this);

            if (editorComp != null) {
                if (editorComp instanceof EditorInterface) {
                    ((EditorInterface) editorComp).removeEditor();
                }
                remove(editorComp);
            }

            cellRect = getCellRect(editingRow, editingColumn, false);

            setCellEditor(null);
            resizeAfterEdit(getEditingRow(), getEditingColumn()); //50409
            setEditingColumn(-1);
            setEditingRow(-1);
            editorComp = null;
            repaint(cellRect);
        }
    }

    /*
     * convenience methods
     */
    /**
     * putValueAt
     * @param _o
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean putValueAt(Object _o, int _r, int _c) { //acl_20021015
        boolean b = cgtm.putValueAt(_o, cgtm.getRowIndex(cgtm.getRowKey(_r)), _c, this); //acl_20021015
        if (b) { //acl_20021015
            //23239			resizeCells();
            //50409			resizeAfterEdit(_r,_c);		//23239
            if (updateArmed) {
                if (ec != null) {
                    ec.refreshUpdate();
                } else if (ac != null) {

                    //23317					ac.refreshUpdate();
                    ac.refreshUpdate(_r, _c); //23317
                }
            }
        } //acl_20021015
        return b; //acl_20021015
    }

    /**
     * putValueAt
     * @param _o
     * @param _rKey
     * @param _colKey
     * @return
     * @author Anthony C. Liberto
     */
    public boolean putValueAt(Object _o, String _rKey, String _colKey) { //acl_20021015
        boolean b = cgtm.putValueAt(_o, cgtm.getRowIndex(_rKey), convertColumnIndexToModel(cgtm.getColumnIndex(_colKey)), this); //acl_20021015
        if (b) { //acl_20021015
            //23239			resizeCells();
            int r = getEditingRow(); //23317
            int c = getEditingColumn(); //23317
            //50409			resizeAfterEdit(r,c);		//23239
            if (updateArmed) {
                if (ec != null) {
                    ec.refreshUpdate();
                } else if (ac != null) {
                    //23317					ac.refreshUpdate();
                    ac.refreshUpdate(r, c); //23317
                }
            }
        } //acl_20021015
        return b; //acl_20021015
    }

    /**
     * putValueAt
     * @param _o
     * @param _rKey
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean putValueAt(Object _o, String _rKey, int _c) { //acl_20021015
        if (cgtm != null) {
            return cgtm.putValueAt(_o, cgtm.getRowIndex(_rKey), convertColumnIndexToModel(_c), this); //acl_20021015
        }
        return false;
    } //acl_20021015

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isHidden(int)
     * @author Anthony C. Liberto
     */
    public boolean isHidden(int _r) {
        if (cgtm != null) {
            return cgtm.isHidden(cgtm.getRowKey(_r));
        }
        return false;
    }

    /**
     * @see javax.swing.JTable#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
        if (cgtm != null) {
        	int rowid = cgtm.getRowIndex(cgtm.getRowKey(_r));
        	if (rowid!=-1){
        		return cgtm.getValueAt(rowid, convertColumnIndexToModel(_c));
        	}else{
        		System.err.println("##RSTable.getValueAt rowid for row "+_r+" is -1!!");
        	}
        }
        return null;
    }

    /**
     * getValueAt
     * @param _r
     * @param _c
     * @param _longDesc
     * @return
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c, boolean _longDesc) {
        if (cgtm != null) {
            return cgtm.getValueAt(cgtm.getRowIndex(cgtm.getRowKey(_r)), convertColumnIndexToModel(_c), _longDesc);
        }
        return null;
    }

    /**
     * getLockGroup
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public LockGroup getLockGroup(int _r, int _c) { //lock_update
        return getLockGroup(cgtm.getRowKey(_r), _c); //lock_update
    } //lock_update

    /**
     * getLockGroup
     * @param _key
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public LockGroup getLockGroup(String _key, int _c) { //lock_update
        if (cgtm != null) {
            return cgtm.getLockGroup(cgtm.getRowIndex(_key), convertColumnIndexToModel(_c)); //lock_update
        }
        return null;
    } //lock_update

    /**
     * getValueAt
     * @param _rowKey
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object getValueAt(String _rowKey, int _c) {
        if (cgtm != null) {
            return cgtm.getValueAt(cgtm.getRowIndex(_rowKey), convertColumnIndexToModel(_c));
        }
        return null;
    }

    /**
     * getEANObject
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object getEANObject(int _r, int _c) {
        if (cgtm != null) {
            return cgtm.getEANObject(cgtm.getRowIndex(cgtm.getRowKey(_r)), convertColumnIndexToModel(_c));
        }
        return null;
    }

    /**
     * getEANObject
     * @param _rKey
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object getEANObject(String _rKey, int _c) { //22089
        if (cgtm != null) {
            return cgtm.getEANObject(cgtm.getRowIndex(_rKey), convertColumnIndexToModel(_c)); //22089
        }
        return null;
    } //22089

    /**
     * getAttribute
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute(int _r, int _c) {
        Object o = getEANObject(_r, _c);
        if (o instanceof EANAttribute) {
            return (EANAttribute) o;
        }
        return null;
    }

    /**
     * getRowClass
     * @param _r
     * @return
     * @author Anthony C. Liberto
     */
    public Class getRowClass(int _r) {
        if (cgtm == null) {
            return null;
        }
        return cgtm.getRowClass(cgtm.getRowIndex(cgtm.getRowKey(_r)));
    }

    /**
     * @see javax.swing.JTable#isCellEditable(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(int _r, int _c) {
        if (cgtm != null) {
            return cgtm.isCellEditable(cgtm.getRowIndex(cgtm.getRowKey(_r)), convertColumnIndexToModel(_c));
        }
        return false;
    }
    //locklist
    /**
     * isCellLocked
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCellLocked(int _r, int _c) {
        if (cgtm != null) {
            return cgtm.hasLock(cgtm.getRowIndex(cgtm.getRowKey(_r)), convertColumnIndexToModel(_c));
        }
        return false;
    }

    /**
     * hasLock
     * @param _key
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasLock(String _key, int _c) {
        if (cgtm != null) {
            return cgtm.hasLock(cgtm.getRowIndex(_key), convertColumnIndexToModel(_c));
        }
        return false;
    }

    /**
     * hasLock
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasLock(int _r, int _c) { //22411
        if (cgtm != null) {
            return cgtm.hasLock(cgtm.getRowIndex(cgtm.getRowKey(_r)), convertColumnIndexToModel(_c)); //22411
        }
        return false;
    } //22411

    /**
     * isCellLocked
     * @param _r
     * @param _c
     * @param _acquireLock
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCellLocked(int _r, int _c, boolean _acquireLock) {
        if (cgtm != null) {
            return cgtm.isCellLocked(cgtm.getRowIndex(cgtm.getRowKey(_r)), convertColumnIndexToModel(_c), _acquireLock);
        }
        return false;
    }

    /**
     * isEditable
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditable(int _r, int _c) {
        if (cgtm != null) {
            return cgtm.isEditable(cgtm.getRowIndex(cgtm.getRowKey(_r)), convertColumnIndexToModel(_c));
        }
        return false;
    }

    /**
     * isPasteable
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPasteable(int _r, int _c) {
        if (cgtm != null) {
            return cgtm.isPasteable(cgtm.getRowIndex(cgtm.getRowKey(_r)), convertColumnIndexToModel(_c));
        }
        return false;
    }

    /**
     * getRow
     * @param _r
     * @return
     * @author Anthony C. Liberto
     */
    public Object getRow(int _r) {
        if (cgtm != null) {
            return cgtm.getRow(_r);
        }
        return null;
    }

    /**
     * getRow
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public Object getRow(String _key) {
        if (cgtm != null) {
            return cgtm.getRowByKey(_key);
        }
        return null;
    }

    /**
     * getRowKey
     * @param _r
     * @return
     * @author Anthony C. Liberto
     */
    public String getRowKey(int _r) {
        if (cgtm != null) {
            return cgtm.getRowKey(_r);
        }
        return null;
    }

    /*
     * end convenience methods
     */

    /**
     * setEditingRowKey
     * @param _row
     * @author Anthony C. Liberto
     */
    public void setEditingRowKey(int _row) {
        if (cgtm != null) {
            rowKey = new String(cgtm.getRowKey(_row));
        }
    }

    /**
     * getEditingRowKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getEditingRowKey() {
        return rowKey;
    }

    /**
     * getEditingRow
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public int getEditingRow(String _key) {
        if (_key == null) {
            return -1;
        }
        return cgtm.getRowIndex(_key);
    }

    /**
     * setEditingColumnKey
     * @param _col
     * @author Anthony C. Liberto
     */
    public void setEditingColumnKey(int _col) {
        if (cgtm != null) {
            colKey = new String(cgtm.getColumnKey(_col));
        }
    }

    /**
     * getEditingColumnKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getEditingColumnKey() {
        return colKey;
    }

    /**
     * getEditingColumn
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public int getEditingColumn(String _key) {
        if (_key == null) {
            return -1;
        }
        return cgtm.getColumnIndex(_key);
    }
    /*
     * rollback
     */
    /**
     * rollback
     * @author Anthony C. Liberto
     */
    public void rollback() {
    	if (isVEEdit()) {
    		int rr = getRowCount();
    		int cc = getColumnCount();
    		for (int r=0;r<rr;++r) {
    			for (int c=0;c<cc;++c) {
    				EANAttribute att = getAttribute(r,c);
    				if (att != null) {
    					att.rollback();
    				}
    			}
    		}
    	} else {
	        cgtm.rollback();
		}
        resizeCells();
    }

    /**
     * rollbackRow
     * @author Anthony C. Liberto
     */
    public void rollbackRow() {
        int r = getSelectedRow();
        if (r < 0 || r >= getRowCount()) {
            return;
        }
    	if (isVEEdit()) {
    		int cc = getColumnCount();
			for (int c=0;c<cc;++c) {
				EANAttribute att = getAttribute(r,c);
				if (att != null) {
					att.rollback();
				}
			}
    	} else {
	        cgtm.rollback(cgtm.getRowKey(r));
		}
        resizeCells();
    }

    /**
     * rollbackSingle
     * @author Anthony C. Liberto
     */
    public void rollbackSingle() {
        int r = getSelectedRow();
        int c = convertColumnIndexToModel(getSelectedColumn());
  	    if (isValidCell(r, c)) {
			if (isVEEdit()) {
				EANAttribute att = getAttribute(r,c);
				if (att != null) {
					att.rollback();
				}
			} else {
	            cgtm.rollback(cgtm.getRowKey(r), cgtm.getColumnKey(c));
	            resizeRow(r);
	            resizeColumn(c);
			}
        }
    }

    /**
     * rollbackMatrix
     * @author Anthony C. Liberto
     */
    public void rollbackMatrix() {
        cgtm.rollbackMatrix();
        resizeCells();
    }

    /*
     * resize
     */
    /**
     * getPreferredHeight
     * @return
     * @author Anthony C. Liberto
     */
    public int getPreferredHeight() {
        return getPreferredSize().height;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isMultiLineClass(int)
     * @author Anthony C. Liberto
     */
    protected boolean isMultiLineClass(int _row) { //21805
        Class cls = getRowClass(_row); //21805
        if (cls == LongTextAttribute.class) { //21805
            return true; //21805
        } else if (cls == XMLAttribute.class) { //22612
            return true; //22612
        } else if (cls == MultiFlagAttribute.class) { //22612
            return true; //22612
        } //21805
        return false; //21805
    } //21805

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#finishEditing()
     * @author Anthony C. Liberto
     */
    public void finishEditing() {
        if (isViewing()) {
            removeViewer();
        } else if (isEditing()) {
            TableCellEditor tce = getCellEditor();
            if (tce != null) {
                tce.stopCellEditing();
            }
        }
    }

    /**
     * commit
     * @return
     * @author Anthony C. Liberto
     */
    public boolean commit() {
        Exception e = null;
        if (isEditing()) {
            if (!canStopEditing()) {
                return false;
            }
        }

        e = cgtm.commit(this);
        if (e != null) {
            if (e instanceof MiddlewareBusinessRuleException) {
                //				moveToError((MiddlewareBusinessRuleException)e);
            } else if (e instanceof EANBusinessRuleException) {
                moveToError((EANBusinessRuleException) e);
            }
            //51260			setMessage(e.toString());
            //51260			showError();
            showException(e, ERROR_MESSAGE, OK); //51260
        } else {
            refresh();
            return true;
        }
        return false;
    }

    /**
     * commitDefault
     * @return
     * @author Anthony C. Liberto
     */
    public boolean commitDefault() {
        EntityItem ei = getCurrentEntityItem(false);
        if (ei != null) {
            try {
                ei.updateWGDefault(null, getRemoteDatabaseInterface());
            } catch (MiddlewareBusinessRuleException _mbre) {
                _mbre.printStackTrace();
            } catch (EANBusinessRuleException _bre) {
                _bre.printStackTrace();
            }
        }
        return true;
    }

    /**
     * cancelDefault
     * @return
     * @author Anthony C. Liberto
     */
    public void cancelDefault() {
        EntityItem ei = getCurrentEntityItem(false);
        if (ei != null) {
            try {
                ei.resetWGDefault(null, getRemoteDatabaseInterface());
            } catch (MiddlewareBusinessRuleException _mbre) {
                _mbre.printStackTrace();
            } catch (EANBusinessRuleException _bre) {
                _bre.printStackTrace();
            }
        }
    }

    /**
     * canStopEditing
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canStopEditing() {
        return getCellEditor().stopCellEditing();
    }

    /**
     * hasChanges
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanges() {
        return cgtm.hasChanges();
    }

    /**
     * showSort
     * @author Anthony C. Liberto
     */
    public void showSort() { //MSort
        //50874		eaccess().setPanelObject(SORT_PANEL,this);
        //5ZBTCQ.2		eaccess().show(SORT_PANEL,false);
        eaccess().show((Component) this, SORT_PANEL, false); //5ZBTCQ.2
    } //MSort

    /**
     * sort
     *
     * @param _i
     * @param _d
     * @author Anthony C. Liberto
     */
    public void sort(int[] _i, boolean[] _d) { //MSort
//		int[] c = null;												//MN_23318121
        String[] keys = null;
        if (isSortable()) {
            if (!isBusy()) { //23030
                setBusy(true);
                keys = getSelectedKeys();		        //MN_24262225
                cgtm.sort(_i,_d);
                if (keys != null) {						//MN_24262225
	                highlight(keys); 					//MN_24262225
				}										//MN_24262225
                setBusy(false); //23030
            } //23030
        } //23030
    }

    /**
     * addRow
     * @param _sort
     * @author Anthony C. Liberto
     */
    public void addRow(boolean _sort) { 
       //System.out.println("**** rsTable.addRow ****");
        //cgtm.addRow(false, _sort); // this prevents seeing the added row in horizontal edit when imported from ss
        cgtm.addRow(true, _sort);
        resizeCells();
    }

    /**
     * addRow
     * VEEdit_Iteration2
     * @param key
     * @param _sort
     * @author Anthony C. Liberto
     */
    public void addRow(String _key, boolean _sort) {
        //System.out.println("**** rsTable.addRow(" +_key + ")****");
        cgtm.addRow(_key,false, _sort);
        resizeCells();
    }

    /*
     * the passed in value should
     * already be established by the calling
     * method
     */
    /**
     * getAttributeObject
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object getAttributeObject(int _r, int _c) {
        return getAttributeObject(cgtm.getTable(), _r, _c);
    }

    /**
     * getAttributeObject
     * @param _table
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object getAttributeObject(RowSelectableTable _table, int _r, int _c) {
        Object o = _table.getEANObject(_r, convertColumnIndexToModel(_c));
        if (o instanceof SingleFlagAttribute) {
            return ((SingleFlagAttribute) o).get();
        } else if (o instanceof StatusAttribute) {
            return ((StatusAttribute) o).get();
        } else if (o instanceof MultiFlagAttribute) {
            return ((MultiFlagAttribute) o).get();
        } else if (o instanceof LongTextAttribute) {
            return ((LongTextAttribute) o).get();
        } else if (o instanceof BlobAttribute) {
            return ((BlobAttribute) o).get();
        } else if (o instanceof TextAttribute) {
            return ((TextAttribute) o).get();
        }
        return null;
    }

    //locklist
    /**
     * lockRows
     * @author Anthony C. Liberto
     */
    public void lockRows() {
        int[] rr = getSelectedRows();
        lockRows(rr);
    }

    /**
     * lockRows_orig
     * @param _rr
     * @author Anthony C. Liberto
     */
    public void lockRows_orig(int[] _rr) {
        Profile prof = getActiveProfile(); //22411
        EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
        //null;
        //try {
        //    lockOwnerEI = new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getActiveProfile().getOPWGID());
        //} catch (Exception ex) {
        //    ex.printStackTrace();
       // }

        for (int i = 0; i < _rr.length; i++) {
            int r = _rr[i];
            String key = null;
            EntityItem ei = null;
            if (r < 0 || r >= getRowCount()) {
                return;
            }
            key = cgtm.getRowKey(r);
            cgtm.lockRow(cgtm.getRowIndex(key), getLockList(), prof); //22411
            ei = (EntityItem) cgtm.getModelRow(cgtm.getRowIndex(key)); //20037
            if (ei != null && lockOwnerEI !=null) { //acl_20021115
                if (!ei.hasLock(lockOwnerEI, prof)) { //acl_20021115
                    LockGroup lock = ei.getLockGroup(); //acl_20021115
                    EntityGroup eg = null;
                    if (lock != null) { //acl_20021115
                        setMessage(lock.toString());
                        showError(); //acl_20021115
                        return; //acl_20021115
                    } //acl_20021115
                    eg = ei.getEntityGroup(); //acl_20021115
                    if (eg.isAssoc() || eg.isRelator()) { //acl_20021115
                        EntityItem downEI = (EntityItem) ei.getDownLink(0); //acl_20021115
                        if (downEI != null) { //acl_20021115
                            if (!downEI.hasLock(lockOwnerEI, prof)) { //acl_20021115
                                LockGroup lg = downEI.getLockGroup(); //acl_20021115
                                if (lg != null) { //acl_20021115
                                    setMessage(lg.toString());
                                    showError();
                                } //acl_20021115
                            } //acl_20021115
                        } //acl_20021115
                    } //acl_20021115
                } //acl_20021115
            } //acl_20021115
        }
    }

    /**
     * unlockRows_orig
     * @author Anthony C. Liberto
     */
    public void unlockRows_orig() {
        int[] rr = getSelectedRows();
        for (int i = 0; i < rr.length; i++) {
            int r = rr[i];
            if (r < 0 || r >= getRowCount()) {
                return;
            }
            cgtm.unlockRow(cgtm.getRowIndex(cgtm.getRowKey(r)), getLockList(), getActiveProfile());
        }
    }

    /**
     * prepareToEdit
     * @author Anthony C. Liberto
     */
    public void prepareToEdit() {
        requestFocus();
    }

    //removerow
    /**
     * removeRows
     * @author Anthony C. Liberto
     */
    public void removeRows() {
        int[] rr = getSelectedRows();
        int row = -1;
        for (int i = (rr.length - 1); i >= 0; i--) {
            int r = rr[i];
            if (r < 0 || r >= getRowCount()) {
                return;
            }
            //52343			cgtm.removeRow(cgtm.getRowIndex(cgtm.getRowKey(r)));
            cgtm.removeRow(cgtm.getRowIndex(cgtm.getModelKey(r))); //52343
        }
        row = getRowCount();
        if (row <= 0) { //21810
            addRow(true); //acl_20030911
            //acl_20030911			addRow();								//21810
        }
        if (isValidCell(0, 0)) { //19922
            setRowSelectionInterval(0, 0); //19922
            setColumnSelectionInterval(0, 0); //19922
            toggleEnabled(0, 0); //21737
        } //19922
    }

    /**
     * isNew
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isNew() { //22252
        return isNew(getSelectedRow()); //22252
    } //22252

    /**
     * isNew
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isNew(int _row) { //19937
    	if (cgtm != null) {										//MN24151359
	        return (cgtm.isNew(_row) && getRowCount() >= 1); 	//22252
		}														//MN24151359
		return false;											//MN24151359
    } //19937
    /*
     * editController pass
     */

    /**
     * setActionController
     * @param _ac
     * @author Anthony C. Liberto
     */
    public void setActionController(ActionController _ac) { //19937
        ac = _ac; //19937
    } //19937

    /**
     * getActionController
     * @return
     * @author Anthony C. Liberto
     */
    public ActionController getActionController() { //19937
        return ac; //19937
    } //19937

    /**
     * setEditController
     * @param _ec
     * @author Anthony C. Liberto
     */
    public void setEditController(EditController _ec) { //19937
        ec = _ec; //19937
    } //19937

    /**
     * getEditController
     * @return
     * @author Anthony C. Liberto
     */
    public EditController getEditController() { //19937
        return ec; //19937
    } //19937

    /**
     * setParentItem
     * @param _parent
     * @author Anthony C. Liberto
     */
    public void setParentItem(EntityItem _parent) {
        int[] rr = getSelectedRows();
        for (int i = (rr.length - 1); i >= 0; i--) {
            int r = rr[i];
            if (r < 0 || r >= getRowCount()) {
                return;
            }
            cgtm.setParentItem(cgtm.getRowIndex(cgtm.getRowKey(r)), _parent);
        }
    }

    /**
     * refreshTable
     * @param _resize
     * @author Anthony C. Liberto
     */
    public void refreshTable(boolean _resize) {
        resizeAndRepaint();
        if (_resize) {
            resizeCells();
            repaint();
        }
    }

    /**
     * refreshTable
     * @param _r
     * @param _c
     * @param _resize
     * @author Anthony C. Liberto
     */
    public void refreshTable(int _r, int _c, boolean _resize) { //23317
        resizeAndRepaint(); //23317
        if (_resize) {
            resizeAfterEdit(_r, _c); //23317
            repaint(); //23317
        }
    } //23317

    /**
     * refresh
     * @author Anthony C. Liberto
     */
    public void refresh() {
        cgtm.refresh();
    }

    /**
     * generatePickList
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList generatePickList() {
        return generatePickList(getSelectedColumn());
    }

    /**
     * generatePickList
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList generatePickList(int _i) {
        return cgtm.generatePickList(convertColumnIndexToModel(_i));
    }

    /**
     * addColumn
     * @param _ei
     * @author Anthony C. Liberto
     */
    public void addColumn(EntityItem[] _ei) {
        cgtm.addColumn(_ei);
        createDefaultColumnsFromModel();
    }

    /*
     * where used
     */
    //51876	public EANFoundation[] link(String[] _keys, EntityItem[] _ei) {
    //51876		EANFoundation[] ean = cgtm.link(_keys, _ei,this);
    /**
     * link
     * @param _keys
     * @param _ei
     * @param _linkType
     * @return
     */
    public EANFoundation[] link(String[] _keys, EntityItem[] _ei, String _linkType) { //51876
    	int rowid = getSelectedRow();
        EANFoundation[] ean = cgtm.link(_keys, _ei, _linkType, this); //51876
        setRowSelectionInterval(rowid,rowid); // if nothing is selected, it returns a number exceeding rowcount
        resizeCells();
        return ean;
    }

    /**
     * removeLink
     * @param _keys
     * @author Anthony C. Liberto
     */
    public void removeLink(String[] _keys) {
        if (_keys != null) {
        	cgtm.removeLink(_keys, this);
        	if (isValidCell(0, 0)) {
        		setRowSelectionInterval(0, 0);
        		setColumnSelectionInterval(0, 0);
        	}
        	resizeAndRepaint();
        }
    }

    /**
     * create
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList create(String _key) {
        return cgtm.create(_key);
    }

    /**
     * whereUsed
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public WhereUsedList whereUsed(String _key) {
        //joan.20030625		return cgtm.whereUsed(_key);
        return cgtm.whereUsed(_key, this); //joan.20030625
    }

    /**
     * generatePickList
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList generatePickList(String _key) {
        return cgtm.generatePickList(_key);
    }

    /*
     * spelling
     */
    /**
     * spellCheck
     * @author Anthony C. Liberto
     */
    public void spellCheck() {
        initSpellCheck();
        spellCheck(getSelectedRows(), getSelectedColumns());
        completeSpellCheck();
    }

    /**
     * spellCheck
     * @param rows
     * @param cols
     * @author Anthony C. Liberto
     */
    public void spellCheck(int[] rows, int[] cols) {
        boolean bResize = false; //51089
        int rr = rows.length;
        int cc = cols.length;
        for (int r = 0; r < rr; ++r) {
            int row = rows[r];
            if (!isCellLocked(row, cols[0])) {
                if (!isCellLocked(row, cols[0], true)) {
                    continue;
                }
                repaint();
            }
            for (int c = 0; c < cc; ++c) {
                int col = cols[c];
                if (isCellEditable(row, col)) {
                    Object o = getAttribute(row, col);
                    setRowSelectionInterval(row, row);
                    setColumnSelectionInterval(col, col);
                    scrollRectToVisible(getCellRect(row, col, true));
                    if (o == null) {
                    } else if (o instanceof LongTextAttribute) {
                        String str = spellCheck(getStringSearch(row, col), null);
                        setValueAt(str, row, col);
                        bResize = true; //51089
                    } else if (o instanceof TextAttribute) {
                        TextAttribute txt = (TextAttribute) o;
                        EANMetaAttribute meta = txt.getMetaAttribute();
                        if (!meta.isDate() && !meta.isTime()) {
                            String str = spellCheck(getStringSearch(row, col), null);
                            if (Routines.have(str)) { //50972
                                setValueAt(str, row, col);
                                bResize = true; //51089
                            } //50972
                        }
                    }
                }
            }
        }
        if (bResize) { //51089
            resizeCells();
        } //51089
        repaint();
    }

    /**
     * getActionItemsAsArray
     * @param _rowKey
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem[] getActionItemsAsArray(String _rowKey) {
        //joan.20030625		return cgtm.getActionItemsAsArray(_rowKey);
        return cgtm.getActionItemsAsArray(_rowKey, this); //joan.20030625
    }

    /**
     * getActionItemsAsArray
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem[] getActionItemsAsArray(int _row) {
        //joan.20030625		return cgtm.getActionItemsAsArray(_row);
        return cgtm.getActionItemsAsArray(_row, this); //joan.20030625
    }

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     * @author Anthony C. Liberto
     */
    public void valueChanged(ListSelectionEvent _lse) {
        superValueChanged(_lse);
        if (cgtm != null && !isEmpty()) {
            String key = cgtm.getRowKey(getSelectedRow());
            if (tree != null && key != null) {
                tree.load(getActionItemsAsArray(key), getTableTitle());
                if (ac != null) {
                    ac.refreshMenu();
                }
            }
        }
    }

    /**
     * setActionTree
     * @param _tree
     * @author Anthony C. Liberto
     */
    public void setActionTree(NavActionTree _tree) {
        tree = _tree;
    }

    /**
     * getActionTree
     * @return
     * @author Anthony C. Liberto
     */
    public NavActionTree getActionTree() {
        return tree;
    }

    /**
     * setParentObject
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setParentObject(Object _o) {
        pObject = _o;
    }

    /**
     * getParentObject
     * @return
     * @author Anthony C. Liberto
     */
    public Object getParentObject() {
        return pObject;
    }

    /**
     * resetDefaultOrder
     * @author Anthony C. Liberto
     */
    public void resetDefaultOrder() {
    }

    /*
     * abstract stuff
     */
    /**
     * moveColumn
     *
     * @param _left
     * @author Anthony C. Liberto
     */
    public void moveColumn(boolean _left) {
        int selectedColumn = getSelectedColumn();
        int columnCount = getColumnCount();
        int columnMultiplier = 1;
        int targetColumn = -1;
        int row = -1;
        if (selectedColumn < 0 || selectedColumn >= columnCount) {
            return;
        }
        if (_left) {
            columnMultiplier = -1;
        }
        targetColumn = getNextColumn(selectedColumn, columnMultiplier);
        if (targetColumn < 0 || targetColumn >= columnCount) {
            return;
        }
        moveColumn(selectedColumn, targetColumn);
        row = getSelectedRow();
        if (row < 0 || row >= getRowCount()) {
            return;
        }
        setRowSelectionInterval(row, row);
        setColumnSelectionInterval(targetColumn, targetColumn);
        scrollRectToVisible(getCellRect(row, targetColumn, false));
        repaint();
    }

    /**
     * getTableTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTableTitle() {
        if (cgtm != null) {
            return cgtm.getTableTitle();
        }
        return "";
    }

    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    public abstract void init();
    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public abstract void actionPerformed(ActionEvent _ae);
    /**
     * moveToError
     * @param bre
     * @author Anthony C. Liberto
     */
    public abstract void moveToError(EANBusinessRuleException bre);

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isHorizontalTableFormat()
     * @author Anthony C. Liberto
     */
    protected boolean isHorizontalTableFormat() {
        return cgtm.isType(TABLE_HORIZONTAL);
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isVerticalTableFormat()
     * @author Anthony C. Liberto
     */
    protected boolean isVerticalTableFormat() {
        return cgtm.isType(TABLE_VERTICAL);
    }

    /**
     * regenerate
     * @author Anthony C. Liberto
     */
    public void regenerate() { //21700
        createDefaultColumnsFromModel(); //21700
        resizeCells(); //21700
    } //21700

    /**
     * toggleEnabled
     * @param _row
     * @param _col
     * @author Anthony C. Liberto
     */
    protected void toggleEnabled(int _row, int _col) {
    } //21737

    /**
     * refreshClassification
     * @author Anthony C. Liberto
     */
    public void refreshClassification() {
    } //22098

    /**
     * canContinue
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canContinue() { //22455
        if (isEditing()) { //22455
            if (!editingCanStop()) { //22455
                return false; //22455
            } //22455
        } //22455
        return true; //22455
    } //22455

    /**
     * updateXML
     * @author Anthony C. Liberto
     * @return boolean
     * @param _s
     */
    public boolean updateXML(String _s) { //22429
        boolean b = false;
        if (rowKey == null || colKey == null) { //22429
            return false;
        } //22429
        b = putValueAt(_s, rowKey, colKey); //22429
        resizeAfterEdit(getEditingRow(rowKey), getEditingColumn(colKey));
        rowKey = null; //22429
        colKey = null; //22429
        revalidate(); //53373
        return b; //22429
    } //22429

    /**
     * isCopyable
     * @param _att
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isCopyable(EANAttribute _att) { //22468
        if (_att != null) { //22468
            EANMetaAttribute meta = _att.getMetaAttribute(); //22468
            if (meta != null) { //22468
                return !meta.isExcludeFromCopy(); //22468
            } //22468
        } //22468
        return false; //22468
    }

    /**
     * resort
     * @author Anthony C. Liberto
     */
    public void resort() { //22064
        cgtm.sortLast(); //22064
    } //22064

    /**
     * getColumnKeyArray
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getColumnKeyArray() { //22286
        int cc = getColumnCount(); //22286
        String[] out = new String[cc]; //22286
        for (int c = 0; c < cc; ++c) { //22286
            out[c] = getColumnKey(c); //22286
        } //22286
        return out; //22286
    } //22286

    /**
     * getColumnKey
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public String getColumnKey(int _col) { //22286
        TableColumnModel tcm = getColumnModel(); //22286
        RSTableColumn col = (RSTableColumn) tcm.getColumn(_col); //22286
        return col.getKey(); //22286
    } //22286

    //	public void setColumnKeyArray(String[] _ra) {					//22286
    //		cgtm.setColumnKeyArray(_ra);								//22286
    //		return;														//22286
    //	}																//22286

    /**
     * saveCurrentEdit
     * @return
     * @author Anthony C. Liberto
     */
    public boolean saveCurrentEdit() { //22920
        if (isEditing()) { //22920
            return editingCanStop(); //22920
        } //22920
        return true; //22920
    } //22920

    /**
     * resizeAfterEdit
     * @param _row
     * @param _col
     * @author Anthony C. Liberto
     */
    protected void resizeAfterEdit(int _row, int _col) { //23239
        resizeCells(); //23239
    } //23239

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        setRowHeight(getFont());
        resizeCells();
    }

    /**
     * cancelEdit
     *
     * @author Anthony C. Liberto
     */
    public void cancelEdit() {
        TableCellEditor editor = getCellEditor();
        if (editor != null) {
            removeEditor(editor);
        } else {
            removeEditor();
        }
        editingStopped(null);
    }

    /**
     * dump
     *
     * @param _brief
     * @return
     * @author Anthony C. Liberto
     */
    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer();
        sb.append("Table.getRowCount(): " + getRowCount() + RETURN);
        sb.append("Table.getColumnCount(): " + getColumnCount() + RETURN);
        sb.append(RETURN + "TableModel...." + RETURN);
        sb = cgtm.dump(sb, _brief);
        return sb.toString();
    }

    /*
     24306
    */
    /**
     * isFiltered
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFiltered() {
        return cgtm.isFiltered();
    }

    /**
     * isType
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isType(int _i) {
        return cgtm.isType(_i);
    }
    /*
     51258
     */
    /**
     * showLockInformation
     * @param _r
     * @param _c
     * @author Anthony C. Liberto
     */
    protected void showLockInformation(int _r, int _c) {
        LockGroup lockGroup = getLockGroup(_r, _c);
        if (lockGroup != null) {
            setMessage(lockGroup.toString());
            showError();
        }
    }

    /*
     acl_20030717
     */
    /**
     * lockRow - nothing uses this
     * @param _row
     * @author Anthony C. Liberto
     * /
    public void lockRow(int _row) {
        int[] row = new int[1];
        row[0] = _row;
        lockRows(row);
    }

    /*
     acl_20030718
     */
    /**
     * selectKeys
     * @param _s
     * @author Anthony C. Liberto
     */
    public void selectKeys(String[] _s) {
        boolean bClear = true; //22810
        EANFoundation[] rows = cgtm.getRowKeyArray();
        int iSelType = getSelectType(_s);
        int ii = _s.length;
        int xx = rows.length;
        for (int i = 0; i < ii; ++i) {
            for (int x = 0; x < xx; ++x) {
                String sKey = null;
                if (rows[x] instanceof EntityItem) {
                    if (iSelType == 0) {
                        sKey = ((EntityItem) rows[x]).getKey();
                    } else if (iSelType == 1) {
                        EntityItem ei = (EntityItem) rows[x];
                        if (ei.hasUpLinks()) {
                            sKey = ei.getUpLink(0).getKey();
                        }
                    } else if (iSelType == 2) {
                        EntityItem ei = (EntityItem) rows[x];
                        if (ei.hasDownLinks()) {
                            sKey = ei.getDownLink(0).getKey();
                        }
                    }
                }
                if (sKey.equalsIgnoreCase(_s[i])) {
                    int r = cgtm.getViewRowIndex(rows[x].getKey());
                    if (r >= 0) {
                        if (bClear) {
                            bClear = false;
                            clearSelection();
                            scrollToRow(r);
                        }
                        addRowSelectionInterval(r, r);
                        continue;
                    }
                }
            }
        }
    }

    private int getSelectType(String[] _s) {
        if (_s[0].equalsIgnoreCase("Relator")) {
            return 0;
        } else if (_s[0].equalsIgnoreCase("Parent")) {
            return 1;
        } else if (_s[0].equalsIgnoreCase("Child")) {
            return 2;
        }
        return 0;
    }
    /*
     51761
     */
    /**
     * getRowSelectableTable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public RowSelectableTable getRowSelectableTable() {
        return cgtm.getTable();
    }

    /*
     51935
     */
    /**
     * validateKeys
     * @param _keys
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public String[] validateKeys(String[] _keys, int _col) {
        int iReply = -1;
        int ii = -1;
        Vector v = null;
        int xx = -1;
        String[] out = null;
        if (_keys == null) {
            return null;
        }
        ii = _keys.length;
        setCode("msg12009.0");
        v = new Vector();
        for (int i = 0; i < ii; ++i) {
            Object o = getValueAt(_keys[i], _col);
            setParm(o.toString());
            if (Routines.have(o)) {
                if (iReply == 2) {
                    v.add(_keys[i]);
                } else {
                    iReply = eaccess().showConfirm(this, YES_NO_ALL_CANCEL, false);
                    if (iReply == 0 || iReply == 2) {
                        v.add(_keys[i]);
                    } else if (iReply == -1) { // user cancelled
						out = new String[1];
						out[0]="CANCELLED";
						return out;
                    }
                }
            }
        }
        if (v.isEmpty()) {
            return null;
        }
        xx = v.size();
        out = new String[xx];
        for (int x = 0; x < xx; ++x) {
            out[x] = (String) v.get(x);
        }
        return out;
    }

    /*
     bookmark_filter
     */
    /**
     * replayFilter
     * @author Anthony C. Liberto
     */
    protected void replayFilter() {
        if (eaccess().isBookmarkFilterGroup()) {
            EANActionItem ean = getParentAction();
            if (ean != null) {
                if (ean.hasFilterGroup()) {
                    //53482							setFilterGroup(ean.getFilterGroup());
                    setFilterGroup(ean.getFilterGroup(), true); //53482
//blame GB2                    setColFilterGroup(ean.getColFilterGroup(),true);		//filter_col
                }
            }
        }
    }

    //53482	public void setFilterGroup(FilterGroup _group) {
    /**
     * setFilterGroup
     * @author Anthony C. Liberto
     * @param _filter
     * @param _group
     */
    public void setFilterGroup(FilterGroup _group, boolean _filter) { //53482
        EntityGroup eg = null;
        cgtm.setFilterGroup(_group);
        eg = getEntityGroup();
        if (eg != null) {
            EntityList list = eg.getEntityList();
            if (list != null) {
                EANActionItem ean = list.getParentActionItem();
                if (ean != null) {
                    ean.setFilterGroup(_group);
                    if (_filter) { //53482
                        filter();
                    } //53482
                }
            }
        }
    }

    /**
     * setColFilterGroup
     * @author Anthony C. Liberto
     * @param _filter
     * @param _group
     */
    public void setColFilterGroup(FilterGroup _group, boolean _filter) { //53482
        EntityGroup eg = null;
        cgtm.setColFilterGroup(_group);
        eg = getEntityGroup();
        if (eg != null) {
            EntityList list = eg.getEntityList();
            if (list != null) {
                EANActionItem ean = list.getParentActionItem();
                if (ean != null) {
//blame GB2                    ean.setColFilterGroup(_group);
                    if (_filter) { //53482
                        filter();
                    } //53482
                }
            }
        }
    }

    /*
     52151
     */
    /**
     * processTransitionGroup
     * @author Anthony C. Liberto
     */
    protected void processTransitionGroup() {
        eTran.process();
    }

    /**
     * clearTransitionGroup
     * @author Anthony C. Liberto
     */
    protected void clearTransitionGroup() {
        eTran.clear();
    }

    /**
     * addTransitionItem
     * @param _orig
     * @param _new
     * @author Anthony C. Liberto
     */
    protected void addTransitionItem(EANAttribute _orig, EANAttribute _new) {
        if (eaccess().isDebug()) {
            appendLog("adding transition item " + _orig.getKey() + " for " + _new.getKey());
        }
        eTran.add(_orig, _new);
    }

    /**
     * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
     * @author Anthony C. Liberto
     */
    protected void paintChildren(Graphics _g) {
        //acl_20040115		if (_g != null || cgtm != null) {
        if (_g != null && cgtm != null) { //acl_20040115
            super.paintChildren(_g);
        }
    }

    /*
     52372
     */
    /**
     * createTableModel
     * @param _o
     * @param _table
     * @param _type
     * @return
     * @author Anthony C. Liberto
     */
    protected RSTableModel createTableModel(Object _o, RowSelectableTable _table, int _type) {
        return new RSTableModel(_o, _table, _type);
    }

    /**
     * superValueChanged
     * @param _lse
     * @author Anthony C. Liberto
     */
    protected void superValueChanged(ListSelectionEvent _lse) {
        super.valueChanged(_lse);
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
        return isEditable(_row, _col);
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
        return hasLock(_row, _col);
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#getAccessibleMetaAttribute(int, int)
     * @author Anthony C. Liberto
     */
    public EANMetaAttribute getAccessibleMetaAttribute(int _r, int _c) {
        if (!isDereferenced()) {
            Object o = getEANObject(_r, _c);
            if (o instanceof EANAttribute) {
                return ((EANAttribute) o).getMetaAttribute();
            }
        }
        return null;
    }

    /*
     51832
     */
    /**
     * showHide
     * @param _b
     * @author Anthony C. Liberto
     */
    public void showHide(boolean _b) {
        if (_b) {
            String[] cols = getSelectedColumnKeys();
            clearSelection();
            if (cols != null) {
                TableColumnModel tcm = getColumnModel();
                if (tcm != null) {
                    for (int i = 0; i < cols.length; ++i) {
                        TableColumn tc = tcm.getColumn(tcm.getColumnIndex(cols[i]));
                        if (tc != null) {
                            tcm.removeColumn(tc);
                        }
                    }
                    cgtm.modelChanged(TableModelEvent.DELETE);
                }
            }
        } else {
            createDefaultColumnsFromModel();
            cgtm.modelChanged(TableModelEvent.INSERT);
        }
        resizeCells();
        eaccess().validate(); //20031219
        if (isFocusable()) {
            setRowSelectionInterval(0, 0);
            setColumnSelectionInterval(0, 0);
            scrollToRow(0);
        }
    }

    /**
     * getSelectedColumnKeys
     * @return
     * @author Anthony C. Liberto
     */
    protected String[] getSelectedColumnKeys() {
        int[] cols = getSelectedColumns();
        if (cols != null) {
            int ii = cols.length;
            String[] out = new String[ii];
            TableColumnModel tcm = getColumnModel();
            for (int i = 0; i < ii; ++i) {
                RSTableColumn tc = (RSTableColumn) tcm.getColumn(cols[i]);
                if (tc != null) {
                    out[i] = tc.getKey();
                }
            }
            return out;
        }
        return null;
    }

    /**
     * getSelectedRowKeys
     * @return
     * @author Anthony C. Liberto
     */
    protected String[] getSelectedRowKeys() {
        int[] rows = getSelectedRows();
        if (rows != null) {
            int ii = rows.length;
            String[] out = new String[ii];
            for (int i = 0; i < ii; ++i) {
                out[i] = cgtm.getRowKey(rows[i]);
            }
            return out;
        }
        return null;
    }

    /*
     accessibility
     */
    /**
     * getAccessibleTableDescription
     * @author Anthony C. Liberto
     * @return String
     * @param _code
     */
    public String getAccessibleTableDescription(String _code) {
        return getString(_code);
    }

    /*
     acl_20040104
     */
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isDereferenced()
     * @author Anthony C. Liberto
     */
    public boolean isDereferenced() {
        //		return cgtm == null;
        return touch == null;
    }

    /*
     53583
     */
    /**
     * getParentAction
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem getParentAction() {
        EntityGroup eg = getEntityGroup();
        if (eg != null) {
            EntityList list = eg.getEntityList();
            if (list != null) {
                return list.getParentActionItem();
            }
        }
        return null;
    }

    /*
     accessibility null pointer
     */
    /*
    TIR USRO-R-DWES-66UMGM
    	protected void paintComponent(Graphics _g) {
    		routines.yield();
    		super.paintComponent(_g);
    		return;
       }
    */
    /**
     * setLongDescription
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setLongDescription(boolean _b) {
        if (cgtm != null) {
            cgtm.setLongDescription(_b);
        }
    }

    /*
     TIR USRO-R-DWES-66UMGM
     */
    /**
     * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
     * @author Anthony C. Liberto
     */
    public Component prepareRenderer(TableCellRenderer _rend, int _r, int _c) {
        if (_rend != null && isValidCell(_r, _c)) {
            return super.prepareRenderer(_rend, _r, _c);
        }
        return null;
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     * @author Anthony C. Liberto
     */
    protected void paintComponent(Graphics _g) {
        Routines.yield();
        if (!isDereferenced() && isFocusable() && hasTable()) {
            try {
                super.paintComponent(_g);
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
        }
    }

    /**
     * hasTable
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean hasTable() {
        if (cgtm != null) {
            return cgtm.hasTable();
        }
        return false;
    }
    /*
     dwb_20040726
     */
    /**
     * lockRows - used by fillpaste and lock action, also deactivateattribute
     * @param _rr
     * @author Anthony C. Liberto
     */
    public void lockRows(int[] _rr) {
//        StringBuffer sb = new StringBuffer();
        Profile prof = getActiveProfile();
        EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
        	//null;
        int ii = _rr.length;
        //String[] keys = new String[ii];
        int iKeys[] = new int[ii];

        for (int i = 0; i < ii; ++i) {
            int row = _rr[i];
            if (row >= 0 && row < getRowCount()) {
                //keys[i] = cgtm.getRowKey(row);
                iKeys[i] = cgtm.getRowIndex(cgtm.getRowKey(row));//keys[i]);
            }
        }
        cgtm.lockRows(iKeys, getLockList(), prof);
        
        //display any already locked messages
        for (int i = 0; i < ii; i++) {
            EntityItem ei = (EntityItem) cgtm.getModelRow(iKeys[i]);
            if (ei != null && lockOwnerEI != null) {
                if (!ei.hasLock(lockOwnerEI, prof)) {
                    LockGroup lock = ei.getLockGroup();
                    EntityGroup eg = null;
                    if (lock != null) {
                        setMessage(lock.toString());
                        showError();
                        return;
                    }
                    eg = ei.getEntityGroup();
                    if (eg.isAssoc() || eg.isRelator()) {
                        EntityItem downEI = (EntityItem) ei.getDownLink(0);
                        if (downEI != null) {
                            if (!downEI.hasLock(lockOwnerEI, prof)) {
                                LockGroup lg = downEI.getLockGroup();
                                if (lg != null) {
                                    setMessage(lg.toString());
                                    showError();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * unlockRows
     * @author Anthony C. Liberto
     */
    public void unlockRows() {
        int[] rr = getSelectedRows();
        int ii = rr.length;
        String[] keys = new String[ii];
        int iKeys[] = new int[ii];
        for (int i = 0; i < ii; ++i) {
            int row = rr[i];
            if (row >= 0 && row < getRowCount()) {
                keys[i] = cgtm.getRowKey(row);
                iKeys[i] = cgtm.getRowIndex(keys[i]);
            }
        }
        cgtm.unlockRows(iKeys, getLockList(), getActiveProfile());
    }

    /*
     xl8r
     */
    /**
     * getEntityItem
     * @param _row
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem getEntityItem(int _row, boolean _new) {
        EntityItem ei = (EntityItem) cgtm.getRow(_row);
        if (ei == null) {
            return null;
        } else if (_new) {
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
     * importTable
     */
    public void importTable() {
        //importString(eaccess().getImportString(this));
    	try{
    		eaccess().importFromFile(this); // use Importable interface to callback based on filetype selected
    	}catch(Throwable exc){
    		  setMessage(exc.toString());
    		  exc.printStackTrace();
              showError();
    	}
    }

    /** 
     * Importable interface
     * processXLSImport import from Excel xls ss
     * @param index int counter into number of rows in ss    
     * @param attrCodes String[] attribute codes
     * @param attrValue String[], some may be null
     */
    public EANBusinessRuleException processXLSImport(int index, String[] attrCodes, String[] attrValue)
    {
    	// populate a newly created entity with values from xls ss
    	return processImport(index, getRowCount()-1, attrCodes, attrValue);
    }  
    
    /** from importable interface, is the original implementation.. doesnt display errors
     * importString
     * @param _s
     */
    public void importString(String[] _s) {
        if (_s != null) {
            int ii = _s.length;
            String[] head = Routines.getStringArray(_s[0], TAB_DELIMIT, true);
            for (int i = 1; i < ii; ++i) {
                String[] data = Routines.getStringArray(_s[i], TAB_DELIMIT, true);
                processImport(i,getRowCount(), head, data);
            }
        }
    }

    /**
     * processImport
     * @param _record
     * @param _head String[] attribute codes, none should be null
     * @param _data String[] attribute values, some may be null
     * 
     */
    protected EANBusinessRuleException processImport(int index,int _record, String[] _head, String[] _data) {
    	EANBusinessRuleException bre = null;
        if (_head != null && _data != null) {      	
            int hh = _head.length;
            int dd = _data.length;
           
            EntityItem ei = getEntityItem(_record, false); // get current ei
            if (ei !=null && index==0 && ei.getEntityID()<0){ 
            	// current entity is newly created and is the first one, so fill it in with import values
            }else{
                addRowImport(); // create a new row and entityitem
                ei = getEntityItem(getRowCount()-1, false); 
            }
 
            if (ei==null){
                bre = new LocalRuleException("EntityItem can not be found");
                return bre;             	
            }
            EntityGroup eg = ei.getEntityGroup();
            if (eg.isRelator() || eg.isAssoc()) {
                EntityList elist = eg.getEntityList();
                boolean bCreateParent = false;

                if (elist != null) {
                    bCreateParent = elist.isCreateParent();
                }

                if (bCreateParent) {
                    ei = (EntityItem) ei.getUpLink(0);
                } else {
                    ei = (EntityItem) ei.getDownLink(0);
                }  
            }
            
            for (int h = 0; h < hh && h < dd; ++h) {
            	try {           		
            		processImportAttribute(ei, _head[h], _data[h]);
                } catch (EANBusinessRuleException _br) {
                	_br.printStackTrace();
                    if (bre==null){                     	
						bre = _br;
                    }else{
						//only one msg allowed per entityitem, attribute msgs will be accumulated
						// a null ptr will have first msg only
                    	bre.addException(_br); // accumulate all msgs
                    }   
                }
            }
            resizeCells();
            repaint();
        }else{
        	appendLog("processImport not executed because head or data was null head: "+_head + " data: "+_data);
        }
        return bre;
    }

    /**
     * addRowImport
     */
    public void addRowImport() {
        addRow(false);
    }

    /**
     * processImportAttribute
     * @param _ei   EntityItem to add attributes to
     * @param _head String attribute code
     * @param _data String attribute value
     */
    private void processImportAttribute(EntityItem _ei, String _head, String _data) 
    throws EANBusinessRuleException
    {
   		appendLog("processImportAttribute: entered for " + _ei.getKey() +" attrcode "+_head+" data "+_data); 
 
        if (Routines.have(_data)) {
            EANFoundation ean = null;
            try {
            	String key = _head;
            	// allow this to work with any old tabbed files that may already have entitytype as key
            	if (!key.startsWith(_ei.getEntityType()+":")){
            		key = _ei.getEntityType()+":"+_head;
            	}   
         	
                ean = _ei.getEANObject(key);            
            } catch (Exception _ex) {          	
                _ex.printStackTrace();
                String msg = _ex.getMessage();
                if (msg ==null || msg.length()==0){                 	
                	msg = _ex.getClass().getName()+" exception getting "+_head;
                }
                                
                EANBusinessRuleException bre = new LocalRuleException();
                bre.add(_ei, msg);
                throw bre;
            }
            if (ean != null) {
                if (!((EANAttribute) ean).isEditable()) {
                    appendLog("processImportAttribute: "+_head + " not editable.");
                    EANBusinessRuleException bre = new LocalRuleException();
                    bre.add(ean, _head + " not editable.");
                    throw bre;                   
                } else if (ean instanceof TextAttribute) {
                    ((TextAttribute) ean).put(_data);
                } else if (ean instanceof LongTextAttribute) {
                    ((LongTextAttribute) ean).put(_data);
                } else if (ean instanceof XMLAttribute) {
                	// make sure data is enclosed in tags
                	if (!_data.trim().startsWith("<")){
                		_data = "<pre>"+_data+"</pre>";
                	}
                    ((XMLAttribute) ean).put(_data);
                } else if ((ean instanceof SingleFlagAttribute) ||
                		(ean instanceof StatusAttribute) ||
                		(ean instanceof TaskAttribute))
                {
                	EANFlagAttribute att = (EANFlagAttribute) ean;
                    MetaFlag[] mFlags = (MetaFlag[]) att.get();
                    boolean foundFlag = false;
                    if (mFlags != null) {
                        int ii = mFlags.length;
                        for (int i = 0; i < ii; ++i) {
                            if (mFlags[i] != null) {
                                if (descriptionEquals(mFlags[i], _data)) {
                                	foundFlag = true;
                                    mFlags[i].setSelected(true);
                                } else if (mFlags[i].isSelected()) {
                                    mFlags[i].setSelected(false);
                                }
                            }
                        }
                        att.put(mFlags);                        
                    }                   
                    if (!foundFlag){
                    	StringBuffer sb = new StringBuffer();
                    	appendLog("processImportAttribute: "+_head + " could not find matching flag for "+_data);
                        EANBusinessRuleException bre = new LocalRuleException();
                        EANMetaFlagAttribute metaAttr = (EANMetaFlagAttribute)_ei.getEntityGroup().getMetaAttribute(_head);
                        if (metaAttr !=null){         
                        	// find any filtering attributes to warn user, these are needed before this attr
                            for (int x = 0; x < metaAttr.getMetaFlagCount(); x++) {
                                MetaFlag mf = metaAttr.getMetaFlag(x);
                                if (mf.toString().equals(_data)){
                                	for (int m=0; m<mf.getFilterCount();m++){
                                		MetaFlag mff = mf.getFilter(m);
                                		sb.append(mff.getParent().getKey()+" must be set first.");
                                		break;
                                	}
                                }
                            }                            
                        }

                        bre.add(ean, _head + " could not find matching flag for "+_data+" "+sb.toString());
                        throw bre;                         
                    }                    
                } else if (ean instanceof MultiFlagAttribute) {
                    MultiFlagAttribute att = (MultiFlagAttribute) ean;
                    int foundFlagCnt = 0;
                    String[] flags = Routines.getStringArray(_data, FLAG_DELIMIT, true);
                    if (flags != null) {
                        HashMap hash = loadKeys(flags);
                        if (hash != null) {
                            MetaFlag[] mFlags = (MetaFlag[]) att.get();
                            if (mFlags != null) {
                                int ii = mFlags.length;
                                for (int i = 0; i < ii && !hash.isEmpty(); ++i) {
                                    if (mFlags[i] != null) {
                                        if (keyExists(hash, mFlags[i])) {
                                            mFlags[i].setSelected(true);
                                            foundFlagCnt++;
                                        } else if (mFlags[i].isSelected()) {
                                            mFlags[i].setSelected(false);
                                        }
                                    }
                                }
                                att.put(mFlags);
                            }                           
                        }
                    
                        if (foundFlagCnt != flags.length ){
                        	StringBuffer sb = new StringBuffer();
                        	appendLog("processImportAttribute: "+_head + " could not find matching flags for "+_data);
                        	EANBusinessRuleException bre = new LocalRuleException();
                        	EANMetaFlagAttribute metaAttr = (EANMetaFlagAttribute)_ei.getEntityGroup().getMetaAttribute(_head);
                        	if (metaAttr !=null){         
                        		// find any filtering attributes to warn user, these are needed before this attr
                        		for (int x = 0; x < metaAttr.getMetaFlagCount(); x++) {
                        			MetaFlag mf = metaAttr.getMetaFlag(x);
                        			for (int k=0; k<flags.length; k++){
                        				if (mf.toString().equals(flags[k])){
                        					for (int m=0; m<mf.getFilterCount();m++){
                        						MetaFlag mff = mf.getFilter(m);
                        						sb.append("It is filtered by "+mff.getParent().getKey());
                        						break;
                        					}
                        				}
                        			}
                        		}                            
                        	}

                        	bre.add(ean, _head + " could not find matching flag for "+_data+" "+sb.toString());
                        	throw bre;                         
                        }   
                    }else{   // end flags!=null   
                    	appendLog("processImportAttribute: "+_head + " could not find matching flags for "+_data);
                    	EANBusinessRuleException bre = new LocalRuleException();
                    	bre.add(ean, _head + " could not find matching flags for "+_data);
                    	throw bre; 
                    }
                } else if (ean instanceof BlobAttribute) {
                    appendLog("processImportAttribute: Blob not supported for "+_head);
                    EANBusinessRuleException bre = new LocalRuleException();
                    bre.add(ean,_head + " Blob attribute is not supported");                    
                    throw bre;                      
                } else {
                    appendLog("processImportAttribute: not supported class: " + ean.getClass().getName());
                    EANBusinessRuleException bre = new LocalRuleException();
                    bre.add(ean,_head +" "+ean.getClass().getName()+
                    		" is not supported");                    
                    throw bre; 
                }
            }else{
                // some exceptions are caught when generating an attribute and this fails, so check for null now
            	EANBusinessRuleException bre = new LocalRuleException();
            	bre.add(_ei, _head+" not found in meta for "+_ei.getEntityType());
            	throw bre; 
            }
        }else{
        	appendLog("processImportAttribute: not updating "+_head+" data is null");        	
        }
    }

    /**
     * descriptionEquals
     * @param _flag
     * @param _data
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean descriptionEquals(MetaFlag _flag, String _data) {
        if (!caseSensitiveImport()) {
            return _flag.getLongDescription().equalsIgnoreCase(_data);
        }
        return _flag.getLongDescription().equals(_data);
    }

    /**
     * loadKeys
     * @param _desc
     * @return
     * @author Anthony C. Liberto
     */
    protected HashMap loadKeys(String[] _desc) {
        HashMap out = null;
        int ii = -1;
        if (_desc != null) {
            out = new HashMap();
            ii = _desc.length;
            for (int i = 0; i < ii; ++i) {
                if (!caseSensitiveImport()) {
                    out.put(_desc[i].toLowerCase(), _desc[i]);
                } else {
                    out.put(_desc[i], _desc[i]);
                }
            }
        }
        return out;
    }

    /**
     * keyExists
     * @param _map
     * @param _flag
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean keyExists(HashMap _map, MetaFlag _flag) {
        String key = null;
        boolean out = false;
        if (!caseSensitiveImport()) {
            key = _flag.getLongDescription().toLowerCase();
        } else {
            key = _flag.getLongDescription();
        }
        out = _map.containsKey(key);
        if (out) {
            _map.remove(key);
        }
        return out;
    }

    /*
     broke this out so it was easier to change
     if decide to use less rigid import at a later date.
     */
    /**
     * caseSensitiveImport
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean caseSensitiveImport() {
        return true;
    }

    /*
     searchable picklist
     */
    /**
     * getActionItemsAsArray
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem[] getActionItemsAsArray() {
        return getActionItemsAsArray(getSelectedColumn());
    }

    /**
     * getRowSelectableTableRowIndex
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public int getRowSelectableTableRowIndex(String _s) {
        if (cgtm != null) {
            return cgtm.getRowIndex(_s);
        }
        return -1;
    }

    /**
     * getRowSelectableTableColumnIndex
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public int getRowSelectableTableColumnIndex(String _s) {
        if (cgtm != null) {
            return cgtm.getColumnIndex(_s);
        }
        return -1;
    }
    /*
     tableresize
     */
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#getDirectValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getDirectValueAt(int _r, int _c) {
        if (cgtm != null) {
            return cgtm.getValueAt(_r, convertColumnIndexToModel(_c));
        }
        return null;
    }

    /*
     USRO-R-DMKR-66STZL
     */
    /**
     * scrollToFirstSelectedRow
     * @author Anthony C. Liberto
     */
    public void scrollToFirstSelectedRow() {
        scrollToRow(getSelectedRow());
    }

    /*
     USRO-R-RTAR-672S9Y
     */
    /**
     * isPasteable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPasteable() {
        return isPasteable(getSelectedRow(), getSelectedColumn());
    }

    /*
     NMHR-67QQZW
     */
    /**
     * highlight
     * @param _ei
     * @param _strEntity
     * @author Anthony C. Liberto
     */
    public void highlight(EntityItem[] _ei, String _strEntity) {
        boolean bClear = true;
        int ii = _ei.length;
        String key = null;
        int r = -1;
        for (int i = 0; i < ii; ++i) {
            key = getKey(_ei[i], _strEntity);
            r = getViewRowIndex(key);
            if (r >= 0) {
                if (bClear) {
                    bClear = false;
                    clearSelection();
                    scrollToRow(r);
                }
                addRowSelectionInterval(r, r);
            }
        }
        requestFocus();
    }

    private String getKey(EntityItem _ei, String _strEntity) {
        if (_strEntity != null) {
            if (_ei.hasDownLinks()) {
                EANEntity eanDown = _ei.getDownLink(0);
                String strDown = eanDown.getEntityType();
                if (strDown != null) {
                    if (strDown.equals(_strEntity)) {
                        return eanDown.getKey();
                    }
                }
            }
            if (_ei.hasUpLinks()) {
                EANEntity eanUp = _ei.getUpLink(0);
                String strUp = eanUp.getEntityType();
                if (strUp != null) {
                    if (strUp.equals(_strEntity)) {
                        return eanUp.getKey();
                    }
                }
            }
        }
        return _ei.getKey();
    }

    /*
     6554
     */
    /**
     * edit
     * @param _keys
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList edit(String[] _keys) {
        return cgtm.edit(_keys);
    }
    /*
     pivot
     */
    /**
     * isPivot
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPivot() {
        if (cgtm != null) {
            return cgtm.isPivot();
        }
        return false;
    }

    /**
     * setPivot
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setPivot(boolean _b) {
        if (cgtm != null) {
            cgtm.setPivot(_b);
        }
    }

    /**
     * pivot
     *
     * @author Anthony C. Liberto
     */
    public void pivot() {
        if (cgtm != null) {
            cgtm.pivot();
            createDefaultColumnsFromModel();
        }
    }

    /**
     * pivotWrapup
     * @param _bResize
     * @author Anthony C. Liberto
     */
    public void pivotWrapup(boolean _bResize) {
        refreshTable(_bResize);
        refreshList();
    }

    /**
     * pivotResynch
     *
     * @author Anthony C. Liberto
     */
    public void pivotResynch() {
    }

    /**
     * refreshList
     * @return
     * @author Anthony C. Liberto
     */
    public ERowList refreshList() {
        return null;
    }

	/**
	 * getEANMetaAttribute
	 *
	 * @author Anthony C. Liberto
	 * @param _row
	 * @param _col
	 * @return
	 */
    public EANMetaAttribute  getEANMetaAttribute(int _row, int _col) {
		EANAttribute ean = getAttribute(_row,_col);
		if (ean != null) {
            return ean.getMetaAttribute();
        }
        return null;
	}

	/**
	 * getEANMetaAttribute
	 *
	 * @author Anthony C. Liberto
	 * @return
	 */
    public EANMetaAttribute getSelectedEANMetaAttribute() {
		int r = getSelectedRow();
		int c = getSelectedColumn();
		if (isValidCell(r, c)) {
			return getEANMetaAttribute(r,c);
		}
		return null;
	}
    /*
     24306
     */
    /**
     * showFind
     * @param _id
     * @author Anthony C. Liberto
     */
    public void showFind(InterfaceDialog _id) {
        //50874		eaccess().setPanelObject(FIND_PANEL,this);
        eaccess().show((Window) _id, FIND_PANEL, false);
    }

    /**
     * showFilter
     * @param _id
     * @author Anthony C. Liberto
     */
    public void showFilter(InterfaceDialog _id) {
        //50874		eaccess().setPanelObject(FILTER_PANEL,this);
        eaccess().show((Window) _id, FILTER_PANEL, false);
    }

    /**
     * showSort
     * @param _id
     * @author Anthony C. Liberto
     */
    public void showSort(InterfaceDialog _id) {
        //50874		eaccess().setPanelObject(SORT_PANEL,this);
        eaccess().show((Window) _id, SORT_PANEL, false);
    }

    /**
     * getIndexFromHeader
     *
     * @param _s
     * @return
     * @author Anthony C. Liberto
     *
    public int getIndexFromHeader(String _s) {
		if (_s != null) {
			TableColumnModel tcm = getColumnModel();
	        if (tcm != null) {
		        int iIndex = ((RSTableColumnModel)tcm).getIndexFromHeader(_s);
		        return convertColumnIndexToModel(iIndex);
			}
		}
		return 0;
	}*/

    protected boolean isVEEdit() {
    	Object o = cgtm.getObject();
    	if (o instanceof EntityList) {
    		EntityList el = (EntityList)o;
    		return el.isVEEdit();
    	}
        return false;
    }

    protected void refreshWGDefaults() {
    	int rr = getRowCount();
    	int cc = getColumnCount();
    	for (int r=0;r<rr;++r) {
    		refreshWGDefaults(r,cc);
    	}
    }

	protected void refreshWGDefaults(int _row, int _cc) {
		for (int c = 0; c < _cc; ++c) {
			EANAttribute att = getAttribute(_row, c);
			if (att != null) {
				EANMetaAttribute meta = att.getMetaAttribute();
				if (meta != null) {
					if (meta.isWGDEFAULT()) {
						String[] as = meta.getWGDefaultValues();
						if (as != null) {
							for (int i = 0; i < as.length; i++) {
								if (att instanceof EANFlagAttribute) {
									((EANFlagAttribute)att).put(as[i], true);
								} else {
									try {
										att.put(as[i]);
									} catch (EANBusinessRuleException _rule) {
										_rule.printStackTrace();
									}
								}
								att.setActive(true);
							}
						}
					}
				}
			}
		}
	}
}
