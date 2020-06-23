/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: CrossTable.java,v $
 * Revision 1.5  2010/07/10 15:52:04  wendy
 * output information for cell in matrix
 *
 * Revision 1.4  2010/06/23 20:23:30  wendy
 * Fix cant edit number of relators cell in matrix
 *
 * Revision 1.3  2009/05/28 13:50:52  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:11  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:04  tony
 * This is the initial load of OPICM
 *
 * Revision 1.26  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.25  2005/06/06 18:30:22  tony
 * stubbed out mult-attribute edit
 *
 * Revision 1.24  2005/06/03 20:51:07  tony
 * 24226110
 *
 * Revision 1.23  2005/04/20 17:20:38  tony
 * improved editing logic for keyboard functionality.
 *
 * Revision 1.22  2005/02/18 23:10:09  tony
 * USRO-R-TMAY-69QQSU
 *
 * Revision 1.21  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.20  2005/02/08 21:38:39  tony
 * JTest Formatting
 *
 * Revision 1.19  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.18  2005/02/01 15:33:14  tony
 * JTest Second Pass
 *
 * Revision 1.17  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.16  2005/01/19 23:06:15  tony
 * improved logic of acquiring object to update with.
 *
 * Revision 1.15  2005/01/19 17:57:37  tony
 * featureMatrix_copy
 *
 * Revision 1.14  2005/01/18 22:23:14  tony
 * featureMatrix_copy
 *
 * Revision 1.13  2005/01/18 19:04:05  tony
 * pivot modifications
 *
 * Revision 1.12  2005/01/14 20:32:12  tony
 * pivot
 *
 * Revision 1.11  2004/11/18 18:02:37  tony
 * adjusted keying logic based on middleware changes.
 *
 * Revision 1.10  2004/10/22 22:14:43  tony
 * auto_sort/size
 *
 * Revision 1.9  2004/09/16 21:18:36  tony
 * adjusted crosstab editing.
 *
 * Revision 1.8  2004/06/30 17:04:45  tony
 * improved feature matrix functionality.
 *
 * Revision 1.7  2004/06/24 15:29:42  tony
 * added fill capability to featureMatrix
 *
 * Revision 1.6  2004/06/23 17:39:05  tony
 * featureMatrix
 *
 * Revision 1.5  2004/06/23 17:22:56  tony
 * featureMatrix
 *
 * Revision 1.4  2004/06/22 23:28:27  tony
 * featureMatrix
 *
 * Revision 1.3  2004/06/22 18:06:23  tony
 * featureMatrix
 *
 * Revision 1.2  2004/06/10 14:35:30  tony
 * Feature Matrix
 *
 * Revision 1.1.1.1  2004/02/10 16:59:45  tony
 * This is the initial load of OPICM
 *
 * Revision 1.27  2003/12/17 16:46:11  tony
 * 52910
 *
 * Revision 1.26  2003/11/11 00:42:21  tony
 * accessibility update, added convenience method to table.
 *
 * Revision 1.25  2003/11/10 22:51:01  tony
 * accessibility update
 *
 * Revision 1.24  2003/10/29 16:47:39  tony
 * 52728
 *
 * Revision 1.23  2003/10/24 21:11:29  tony
 * 52719
 *
 * Revision 1.22  2003/10/03 16:39:11  tony
 * updated accessibility.
 *
 * Revision 1.21  2003/10/02 21:36:31  tony
 * added accessibility logic
 *
 * Revision 1.20  2003/09/24 18:12:30  tony
 * 52372
 *
 * Revision 1.19  2003/09/24 16:13:52  tony
 * crosstable header sort was not being properly
 * reflected in the matrix itself.
 *
 * Revision 1.18  2003/09/17 16:46:39  tony
 * 52292
 *
 * Revision 1.17  2003/09/17 15:35:28  tony
 * 52284
 *
 * Revision 1.16  2003/09/12 17:49:32  tony
 * 52196
 *
 * Revision 1.15  2003/09/11 18:09:24  tony
 * acl_20030911
 * updated addRow logic to sort based on a boolean.
 *
 * Revision 1.14  2003/07/10 20:38:46  tony
 * 51433 removed superEditCellAt method and calls.
 *
 * Revision 1.13  2003/07/10 18:02:20  tony
 * 51433
 *
 * Revision 1.12  2003/06/19 16:09:43  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.11  2003/06/04 18:48:06  tony
 * 51112
 * 51126
 *
 * Revision 1.10  2003/05/21 15:38:12  tony
 * updated table logic to allow for the table and model
 * to always know the specific type of table.  Based on a
 * table constant.
 *
 * Revision 1.9  2003/04/25 19:20:08  tony
 * added border to tables
 *
 * Revision 1.8  2003/04/24 15:33:11  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.7  2003/04/23 17:16:37  tony
 * adjusted rendering
 *
 * Revision 1.6  2003/04/21 20:03:13  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.5  2003/04/15 17:31:33  tony
 * changed to e-announce.focusborder
 *
 * Revision 1.4  2003/04/14 21:38:25  tony
 * updated table Logic.
 *
 * Revision 1.3  2003/03/24 19:56:14  tony
 * System enhancements to improve usability and
 * functionality of the application.
 *
 * Revision 1.2  2003/03/05 18:54:25  tony
 * accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.37  2002/12/09 21:57:23  tony
 * 23551 -rev1 improved logic so that existing columns will
 * not be resized, when new columns are added to the
 * crossTable.
 *
 * Revision 1.36  2002/12/09 19:16:11  tony
 * 23551
 *
 * Revision 1.35  2002/12/05 21:24:18  tony
 * 23519
 *
 * Revision 1.34  2002/12/02 18:38:55  tony
 * 23421
 *
 * Revision 1.33  2002/11/21 16:51:30  tony
 * 23317
 *
 * Revision 1.32  2002/11/14 22:02:56  tony
 * 23164
 *
 * Revision 1.31  2002/11/07 16:58:33  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;

import com.ibm.eannounce.eforms.action.*;
import com.ibm.eannounce.eforms.editor.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.erend.*;

import COM.ibm.eannounce.objects.*;

import java.awt.*;
import java.awt.datatransfer.*; //copy
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class CrossTable extends RSTable implements ETable, ActionListener { //20020107
	private static final long serialVersionUID = 1L;
	private FoundRend foundRend = new FoundRend();
    private CrossRend cRend = new CrossRend();
    private LockedDefaultRend lockedDefaultRenderer = new LockedDefaultRend();
    private LockedFoundRend lockedFoundRenderer = new LockedFoundRend();
    private ERowList rList = new ERowList();

    //	private CrossTabEditor editor = new CrossTabEditor(0,0,99,1);
    private CrossTabEditor editor = new CrossTabEditor();
    private FlagEditor featEdit = new FlagEditor(); //cr_featureMatrix
    private MatrixGroup mg = null; //cr_featureMatrix
    private TextEditor textEdit = new TextEditor();
    private MatrixAction mAction = null;
 //23519
    private boolean bReport = true; //23519

    /**
     * crossTable
     * @param _o
     * @param _table
     * @param _ac
     * @author Anthony C. Liberto
     */
    public CrossTable(Object _o, RowSelectableTable _table, ActionController _ac) {
        super(_o, _table, _ac, TABLE_CROSS);
        init();
        //51298		setReplaceable(_table.canEdit());
        setReplaceable(eaccess().isEditable(_table)); //51298
        rList.setFixedCellHeight(-1);
        rList.setTable(this);
    }

    /**
     * setMatrixAction
     * @param _action
     * @author Anthony C. Liberto
     */
    public void setMatrixAction(MatrixAction _action) {
        mAction = _action;
    }

    /**
     * getMatrixAction
     * @return
     * @author Anthony C. Liberto
     * /
    public MatrixAction getMatrixAction() {
        return mAction;
    }*/

    /*
     * eliminates sizing problem
     */
    /**
     * @see javax.swing.JTable#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
        return getValueAt(_r, _c, false);
    }

    /**
     * @see javax.swing.JTable#getRowMargin()
     * @author Anthony C. Liberto
     */
    public int getRowMargin() {
        return rowMargin;
    }

    /**
     * getColumnByKey
     * @param _col
     * @return
     * @author Anthony C. Liberto
     * /
    public EANFoundation getColumnByKey(int _col) {
        int col = convertColumnIndexToModel(_col);
        return cgtm.getColumn(col);
    }*/

    /**
     * isHidden
     * @return
     * @author Anthony C. Liberto
     * /
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
    }*/

    /*
    private void setTableColumnWidth(RSTableColumn _tc, int _width) {
        boolean hidden = (_width == 0);
        if (_width == Integer.MAX_VALUE) {
            _width = 15;
        }
        if (hidden) {
            _tc.setMinWidth(_width);
            _tc.setMaxWidth(_tc.getWidth());
        } else {
            //52728			_tc.setMinWidth(_tc.getMinimumPreferredWidth());
            _tc.setMinWidth(_tc.getMinimumAllowableWidth()); //52728
        }
        _tc.setWidth(_width);
        _tc.setPreferredWidth(_width);
        _tc.setResizable(!hidden); //19950
    }*/

    /*
     cr_featureMatrix
    	 public Component prepareEditor(TableCellEditor _editor, int _row, int _col) {
    		Object o = getValueAt(_row,_col);
            boolean isSelected = isCellSelected(_row,_col);
            Component comp = _editor.getTableCellEditorComponent(this, o, isSelected, _row, _col);
            return comp;
        }
     */

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
     * init
     *
     * @author Anthony C. Liberto
     */
    public void init() {
        int iRows = getRowCount(); //auto_sort/size
        initAccessibility("accessible.crssTable");
        setRowMargin(0);
        setBorder(UIManager.getBorder("eannounce.focusborder"));
        setColumnSelectionAllowed(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        resizeCells();
        if (eaccess().canSort(iRows)) { //auto_sort/size
            sort();
        } //auto_sort/size
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setBorder(UIManager.getBorder("eannounce.focusBorder"));
        setCellSelectionEnabled(true); //20020116
//multi-attribute
        setDefaultRenderer(Object.class, cRend); //accessible
        if (iRows > 0) {
            setRowSelectionInterval(0, 0);
        }
    }

    /**
     * getTableHeight
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getTableHeight() {
        int height = getRowHeight();
        int rows = getRowCount();
        return (height * rows);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
    }

    /**
     * setFillable
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setFillable(boolean _b) {
    }*/

    /**
     * isFillable
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isFillable() {
        return false;
    }*/

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
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _r, int _c, EventObject _e) {
     	boolean b = false;
    	int keyCode = 0;
    	KeyEvent ke = null;
    	finishEditing();
    	if (!cgtm.canEdit()) { //51298
    		return false; //51298
    	} //51298

    	if (_e instanceof MouseEvent) {
    		MouseEvent me = (MouseEvent) _e;
    		if (me.getClickCount() != 2) {
    			return false;
    		}
    	} else if (_e instanceof KeyEvent) {
    		ke = (KeyEvent) _e;
    		keyCode = ke.getKeyCode();
    		if (keyCode == KeyEvent.VK_DELETE) {
    			repaint();
    			return false;
    			//51126			} else if (ke.isControlDown() || ke.isMetaDown() || keyCode == KeyEvent.VK_F2 || !Character.isDefined(ke.getKeyChar())) {
    		} else if (ke.isControlDown() || ke.isAltDown() || ke.isMetaDown() || keyCode == KeyEvent.VK_F2 || !Character.isDefined(ke.getKeyChar())) { //51126
    			return false;
    		}
    	}
    	
    	if (showRelAttr() && !isCellLocked(_r, _c)) { // only try to get lock if relator attribute is shown.
    		if (!isCellLocked(_r, _c, true)) {
    			LockGroup lockGroup = getLockGroup(_r, _c); //lock_update
    			if (lockGroup != null) { //lock_update
    				setMessage(lockGroup.toString());
    				showError();
    			} //lock_update

    			return false;
    		}
    	}        

    	b = super.editCellAt(_r, _c, _e);
    	//cr_featureMatrix
    	if (b) {
    		TableCellEditor o = getCellEditor();							//acl_20050420
    		if (o instanceof FlagEditor && ke != null) {		//acl_20050420
    			FlagEditor fEdit = (FlagEditor)o;							//acl_20050420
    			fEdit.prepareToEdit(_e, this);								//acl_20050420
    			fEdit.preloadKeyEvent(ke);									//acl_20050420
    		} else {														//acl_20050420
    			editor.grabFocus();
    		}																//acl_20050420
    		repaint();														//acl_20050420
    	}
    	return b;
    }

    /**
     * need to unlock cells locked for editing
     */
    public void unlock() {
    	LockList ll = getLockList();
        if (ll != null) {
        	EntityItem lockOwnerEI = eaccess().getLockOwner();
        	if (lockOwnerEI!=null){
        		cgtm.getTable().unlock(getRemoteDatabaseInterface(), null, ll, getActiveProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);
        		if (eaccess().isMonitor()) {
        			eaccess().monitor("unlock", ll);
        		}
        	}
        }
    }

    /*
     cr_featureMatrix
    	public TableCellEditor getCellEditor(int _r, int _c) {
    		return editor;
    	}
     */

    /**
     * @see javax.swing.JTable#setValueAt(java.lang.Object, int, int)
     * @author Anthony C. Liberto
     */
    public void setValueAt(Object _o, int _r, int _c) {
        putValueAt(_o, _r, _c); //23164
    }

    /**
     * putValueAt
     * @author Anthony C. Liberto
     * @return boolean
     * @param _o
     * @param _rKey
     * @param _colKey
     */
    public boolean putValueAt(Object _o, String _rKey, String _colKey) { //acl_20021015
        return super.putValueAt(_o, _rKey, _colKey); //acl_20021015
    }

    /**
     * refreshList
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ERowList refreshList() {
        rList.setHeaderValue(cgtm.getTableTitle());
        rList.refresh(cgtm.getRowHeader());
        rList.revalidate(); //52719
        return rList;
    }

    /**
     * getList
     * @return
     * @author Anthony C. Liberto
     */
    public ERowList getList() {
        return rList;
    }

    /**
     * updateModel
     * @author Anthony C. Liberto
     * @param _table
     */
    public void updateModel(RowSelectableTable _table) {
        clearSelection();
        cgtm.updateModel(_table);
        createDefaultColumnsFromModel();
        resizeAndRepaint();
        resizeCells();
        return;
    }

    /*
     * filter
     */
    /**
     * filter
     *
     * @author Anthony C. Liberto
     */
    public void filter() { //19938
        super.filter(); //19938
		if (mAction != null) {								//USRO-R-TMAY-69QQSU
			mAction.refreshHeader(MatrixAction.CROSS);		//USRO-R-TMAY-69QQSU
		} else {
	        refreshList(); //19938
		}													//USRO-R-TMAY-69QQSU
    } //19938

    /**
     * resetFilter
     *
     * @author Anthony C. Liberto
     */
    public void resetFilter() {
        super.resetFilter();
        refreshList();
    }

    /**
     * moveColumn
     * @param _attCode
     * @param _vis
     * @param _pos
     * @return
     * @author Anthony C. Liberto
     * /
    public int moveColumn(String _attCode, boolean _vis, int _pos) {
        TableColumnModel tcm = getColumnModel();
        int indx = tcm.getColumnIndex(_attCode);
        RSTableColumn tc = null;
        if (indx < 0 || indx >= getColumnCount()) {
            return _pos;
        }
        moveColumn(indx, _pos);
        tc = (RSTableColumn) getColumn(_attCode); //20110
        if (tc != null && !_vis) {
            int width = tc.getWidth();
            if (width > 0) {
                setTableColumnWidth(tc, 0); //20110
            }
        }
        return ++_pos;
    }*/

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (cellEditor != null) { //21864
            cellEditor.cancelCellEditing(); //21864
        }
        super.dereference();
        if (foundRend != null) {
            foundRend.removeAll();
            foundRend.removeNotify();
            foundRend = null;
        }
        if (cRend != null) {
            cRend.removeAll();
            cRend.removeNotify();
            cRend = null;
        }
        if (lockedDefaultRenderer!=null){
        	lockedDefaultRenderer.removeAll();
        	lockedDefaultRenderer.removeNotify();
        	lockedDefaultRenderer = null;
        }
        
        if (lockedFoundRenderer!=null){
        	lockedFoundRenderer.removeAll();
        	lockedFoundRenderer.removeNotify();
        	lockedFoundRenderer = null;
        }
        if (editor != null) {
            editor.dereference();
            editor = null;
        }
        if (featEdit != null) { //cr_featureMatrix
            featEdit.dereference(); //cr_featureMatrix
            featEdit = null; //cr_featureMatrix
        } //cr_featureMatrix

        if (textEdit != null) {
            textEdit.dereference();
            textEdit = null;
        }

        mg = null; //cr_featureMatrix

        removeAll();
        removeNotify(); //acl_Mem_20020131
    }

  /*  private void deactivateAttribute(int _row, int _col) {
        if (isValidCell(_row, _col)) {
            setValueAt(null, _row, _col);
        }
    }*/

   /* private void deactivateAttribute(int[] _rows, int[] _cols) {
        int rr = _rows.length;
        int cc = _cols.length;
        for (int r = 0; r < rr; ++r) {
            for (int c = 0; c < cc; ++c) {
                deactivateAttribute(_rows[r], _cols[c]);
            }
        }
    }*/

    /**
     * deactivateAttribute
     * @author Anthony C. Liberto
     * /
    public void deactivateAttribute() {
        deactivateAttribute(getSelectedRows(), getSelectedColumns());
        return;
    }*/



    /**
     * stopEditing
     * @author Anthony C. Liberto
     * /
    public void stopEditing() {
        if (isEditing()) {
            editingCanStop();
        }
    }*/
    /* deprecated
    	public Point moveToError(OPICMBusinessRuleException bre) {
    		return new Point(0,0);
    	}
    */
    /**
     * moveToError
     * @author Anthony C. Liberto
     * @param bre
     */
    public void moveToError(EANBusinessRuleException bre) { //20069
        Object o = bre.getObject(0);
        Point pt = new Point(0, 0);
        if (o instanceof EANAttribute) {
            EANAttribute att = (EANAttribute) o;
            EntityItem ei = (EntityItem) att.getParent();
            pt = selectCell(ei, att.getAttributeCode());
        } else if (o instanceof EntityItem) {
            EntityItem ei = (EntityItem) o;
            pt = selectCellPoint(ei, 0);
        }

        if (pt == null) {
            return;
        }
        if (pt.x >= getRowCount() || pt.x < 0) {
            return;
        }
        if (pt.y >= getColumnCount() || pt.y < 0) {
            return;
        }
        setRowSelectionInterval(pt.x, pt.x);
        setColumnSelectionInterval(pt.y, pt.y);
        scrollRectToVisible(getCellRect(pt.x, pt.y, false));
        grabFocus();
    }

    /**
     * selectCellPoint
     * @param _ei
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    private Point selectCellPoint(EntityItem _ei, int c) {
        int r = cgtm.getRowIndex(_ei.getKey());
        return new Point(r, c);
    }

    /**
     * selectCell
     * @param _ei
     * @param code
     * @return
     * @author Anthony C. Liberto
     */
    private Point selectCell(EntityItem _ei, String code) {
        int r = cgtm.getRowIndex(_ei.getKey());
        //dwb_20041117		int c = convertColumnIndexToView(cgtm.getColumnIndex(_ei.getEntityType() + ":" + code));
        String sKey = eaccess().getKey(_ei, code); //dwb_20041117
        int c = convertColumnIndexToView(cgtm.getColumnIndex(sKey));
        EntityItem eip = (EntityItem) _ei.getUpLink(0);
        if (r < 0) {
            r = cgtm.getRowIndex(eip.getKey());
        }

        return new Point(r, c);
    }

    /**
     * canSpellCheck
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean canSpellCheck(int _r, int _c) {
        return false;
    }*/

    /**
     * spellCheck
     *
     * @author Anthony C. Liberto
     */
    public void spellCheck() {
    }
    /**
     * duplicate
     * @param _copies
     * @author Anthony C. Liberto
     * /
    public void duplicate(int _copies) {
    }*/
    /**
     * duplicate
     * @param _row
     * @param _copies
     * @author Anthony C. Liberto
     * / 
    public void duplicate(int _row, int _copies) {
    }*/

    /**
     * getRecordKey
     * @return
     * @author Anthony C. Liberto
     * /
    public String getRecordKey() {
        int row = getSelectedRow();
        if (row < 0 || row > getRowCount()) {
            return "";
        } else {
            return cgtm.getRowKey(row);
        }
    }*/

    /**
     * getSelectionKey
     * @return
     * @author Anthony C. Liberto
     * /
    public String getSelectionKey() {
        int col = convertColumnIndexToModel(getSelectedColumn());
        return cgtm.getColumnKey(col);
    }*/

    /**
     * setSelection
     * @param _recKey
     * @param _selKey
     * @author Anthony C. Liberto
     * /
    public void setSelection(String _recKey, String _selKey) {
        int row = cgtm.getViewRowIndex(_recKey);
        int col = cgtm.getColumnIndex(_selKey);
        if (isValidCell(row, col)) {
            setRowSelectionInterval(row, row);
            setColumnSelectionInterval(col, col);
            scrollRectToVisible(getCellRect(row, col, false));
        } else if (isValidCell(row, 0)) {
            setRowSelectionInterval(row, row);
            setColumnSelectionInterval(0, 0);
            scrollRectToVisible(getCellRect(row, 0, false));
        } else if (isValidCell(0, 0)) {
            setRowSelectionInterval(0, 0);
            setColumnSelectionInterval(0, 0);
            scrollRectToVisible(getCellRect(0, 0, false));
        }
    }*/

    //acl_20030911	public void addRow() {											//19983
    //acl_20030911		super.addRow();												//19983

    /**
     * addRow
     *
     * @param _sort
     * @author Anthony C. Liberto
     */
    public void addRow(boolean _sort) { //acl_20030911
        super.addRow(_sort); //acl_20030911
        refreshList(); //19983
    } //19983

    /**
     * removeRows
     *
     * @author Anthony C. Liberto
     */
    public void removeRows() { //19983
        super.removeRows(); //19983
        refreshList(); //19983
    } //19983

    /**
     * rollback
     *
     * @author Anthony C. Liberto
     */
    public void rollback() { //19983
        super.rollback(); //19983
        refreshList(); //19983
    } //19983

    /**
     * rollbackRow
     *
     * @author Anthony C. Liberto
     */
    public void rollbackRow() { //19983
        super.rollbackRow(); //19983
        refreshList(); //19983
    } //19983

    /**
     * rollbackSingle
     *
     * @author Anthony C. Liberto
     */
    public void rollbackSingle() { //19983
        int c = -1;
        super.rollbackSingle(); //19983
        c = convertColumnIndexToModel(getSelectedColumn()); //19983
        if (c == cgtm.getRowHeaderIndex()) { //19983
            refreshList();
        } //19983
    } //19983

    /*
     * tooltip
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
                return getValueAt(row, col, true).toString();
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

    /**
     * removeNewRows
     * @author Anthony C. Liberto
     * /
    public void removeNewRows() { //19920
        int rr = getRowCount(); //19920
        for (int i = (rr - 1); i >= 0; --i) { //19920
            int mRow = cgtm.getModelRowIndex(i); //19920
            if (cgtm.isNewModelRow(mRow)) { //19920
                cgtm.removeRow(mRow);
            } //19920
        } //19920
        if (getRowCount() == 0) { //19920
            //acl_20030911			cgtm.addRow(true);						//19920
            cgtm.addRow(true, true); //acl_20030911
        } //19920
        setRowSelectionInterval(0, 0); //19920
        setColumnSelectionInterval(0, 0); //19920
        refreshList(); //20041
    } //19920
    */


    /**
     * @see javax.swing.JTable#isCellEditable(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(int _r, int _c) {
        return true; 
        
    }

    /**
     * isCellLocked
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isCellLocked(int _r, int _c) {
        return true;
    } use base class*/

    /**
     * isCellLocked
     *
     * @param _r
     * @param _c
     * @param _acquireLock
     * @return
     * @author Anthony C. Liberto
     */
    /*public boolean isCellLocked(int _r, int _c, boolean _acquireLock) {
        return true;
    }use base class*/

    /**
     * isPasteable
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPasteable(int _r, int _c) {
        return true;
        //fixme
    }

    /**
     * showSort
     *
     * @author Anthony C. Liberto
     */
    public void showSort() {
        super.showSort();
        if (mAction != null) {
            mAction.refreshHeader(MatrixAction.CROSS);
            mAction.setSort(MatrixAction.MATRIX, getKeys(), true);
            mAction.refreshHeader(MatrixAction.MATRIX);
        }
    }

    /**
     * sortHeader
     *
     * @param _asc
     * @author Anthony C. Liberto
     */
    public void sortHeader(boolean _asc) {
        super.sortHeader(_asc);
        if (mAction != null) {
            if (isPivot()) {
                mAction.refreshHeader(MatrixAction.CROSS);
            } else {
                mAction.refreshHeader(MatrixAction.CROSS);
                mAction.setSort(MatrixAction.MATRIX, cgtm.getRowKeyArray(), true);
                mAction.refreshHeader(MatrixAction.MATRIX);
                mAction.synchronizeSortHeader(MatrixAction.MATRIX);
            }
        }
    }

    /**
     * getInformation
     *
     * @return
     */
    protected String getInformation() {
    	int r = getSelectedRow();
    	int c = getSelectedColumn();

    	if (isValidCell(r, c)) {
    		Object obj = getValueAt(r, c);
    		if (obj instanceof EANAttribute){
    			return Routines.getInformation((EANAttribute)obj);
    		}

    		EANFoundation colFoundation =cgtm.getColumn(convertColumnIndexToModel(c));
    		EANFoundation rowFoundation = (EANFoundation)cgtm.getRow(r);

    		StringBuffer sb = new StringBuffer();
    		String rowKey = null;
    		String colKey = null;

    		if (colFoundation instanceof EntityItem){
    			colKey = colFoundation.getKey();
    		}

    		if (rowFoundation instanceof EntityItem){
    			rowKey = rowFoundation.getKey();
    		}

    		if (rowKey != null){
    			sb.append("Entity: "+rowKey+RETURN);
    		}

    		if (colKey != null){
    			for (int i=0; i<mg.getMatrixItemCount(); i++){
    				MatrixItem mi = mg.getMatrixItem(i);
    				String childKey = mi.getChildEntity().getKey();
    				String parentKey = mi.getParentEntity().getKey();
    				if((colKey.equals(parentKey) && rowKey.equals(childKey))||
    						(rowKey.equals(parentKey) && colKey.equals(childKey)))
    				{
    					//sb.append("Relator count: "+mi.getRelatorList().size()+NEWLINE);
    					if (mi.getRelatorList().size()==0){
    						sb.append("Relators: None"+RETURN);
    					}else{
    						sb.append("Relators: "+RETURN);	
    						for (int rl=0; rl<mi.getRelatorList().size(); rl++){
    							EntityItem ei = (EntityItem) mi.getRelatorList().getAt(rl);
    							sb.append("     "+ei.getKey()+RETURN);
    						}
    					}
    					break;
    				}
    			}
    			sb.append("Entity: "+colKey+RETURN);
    		}

    		if(sb.length()>0){
    			return sb.toString();
    		}

    	}
    	return getString("nia"); 
    }

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() {
        return "CROSS" + mAction.getKey();

    }

    /**
     * resizeAfterEdit
     *
     * @param _row
     * @param _col
     * @author Anthony C. Liberto
     */
    protected void resizeAfterEdit(int _row, int _col) { //23317
        //52292		resizeEditColumn(_col);									//23317
    } //23317

    /*
    23519
    */
    /**
     * replaceAllValue
     * @author Anthony C. Liberto
     * @param _find
     * @param _replace
     * @param _multi
     * @param _case
     * @param _increment
     */
    public void replaceAllValue(String _find, String _replace, boolean _multi, boolean _case, int _increment) {
        bReport = false;
        if (Routines.isInt(_replace)) {
            super.replaceAllValue(_find, _replace, _multi, _case, _increment);
        } else {
            setCode("msg11024.1");
            setParmCount(2);
            setParm(0, _find);
            setParm(1, _replace);
            showFYI();
        }
        bReport = true;
    }

    /**
     * replaceString
     * @author Anthony C. Liberto
     * @return boolean
     * @param _old
     * @param _new
     * @param _r
     * @param _c
     */
    public boolean replaceString(String _old, String _new, int _r, int _c) {
        Object o = null;
        if (bReport) {
            if (!Routines.isInt(_new)) {
                setCode("msg11024.1");
                setParmCount(2);
                setParm(0, _old);
                setParm(1, _new);
                showFYI();
                return false;
            }
        }
        o = getValueAt(_r, _c);
        if (o != null) {
            String newStr = Routines.replaceString(o.toString(), _old, _new);
            int i = Routines.toInt(newStr);
            if (i >= 0 && i <= 99) {
                setValueAt(newStr, _r, _c);
            }
        }
        repaint();
        return true;
    }

    /**
     * isEditable
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditable(int _r, int _c) {
        return isValidCell(_r, _c);
    }

    /**
     * isReplaceableAttribute
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isReplaceableAttribute(int _r, int _c) {
        return !showRelAttr();
    }

    /*
     23551
    */
    /**
     * addColumn
     * @author Anthony C. Liberto
     * @param _ei
     */
    public void addColumn(EntityItem[] _ei) {
        int cStart = -1;
        int cc = -1;
        int width = -1;
        if (_ei == null) {
            return;
        }
        cStart = cgtm.getColumnCount();
        cgtm.addColumn(_ei);
        cc = cgtm.getColumnCount();
        for (int c = cStart; c < cc; ++c) {
            RSTableColumn newColumn = new RSTableColumn(c);
            newColumn.setKey(cgtm.getColumnKey(c));
            newColumn.setName(cgtm.getColumnName(c));
            //52728				newColumn.setMinWidth(newColumn.getMinimumPreferredWidth());
            newColumn.setMinWidth(newColumn.getMinimumAllowableWidth()); //52728
            addColumn(newColumn);
            width = getHeaderWidth(newColumn) + 10;
            newColumn.setWidth(width);
            newColumn.setPreferredWidth(width);
        }
    }

    /*
     51112
     */
    /**
     * sortHeader
     * @author Anthony C. Liberto
     */
    public void sortHeader() {
        rList.sort();
    }

    /*
     52196
     */
    /**
     * @see javax.swing.JTable#createDefaultColumnModel()
     * @author Anthony C. Liberto
     */
    protected TableColumnModel createDefaultColumnModel() {
        return new RSTableColumnModel() {
        	private static final long serialVersionUID = 1L;
        	public Comparator createComparator() {
                return new EComparator(true) {
                    public Object getObject(Object _o, int _index) {
                        if (_o != null && _o instanceof TableColumn) {
                            return ((TableColumn) _o).getHeaderValue();
                        }
                        return _o;
                    }
                };
            }

            public void sort() {
                int cc = getColumnCount();
                TableColumn[] sortItems = new TableColumn[cc];
                for (int c = 0; c < cc; ++c) {
                    sortItems[c] = getColumn(c);
                }
                sort(sortItems);
                for (int c = 0; c < cc; ++c) {
//JTest                    moveColumn(sortItems[c], c);
                    super.moveColumn(sortItems[c], c);          //JTest
                }
            }
        };
    }
    /*
     52284
     */
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#processHeight(int[])
     * @author Anthony C. Liberto
     */
    protected int processHeight(int[] _height) {
        int rr = _height.length;
        int h = getRowHeight();
        for (int r = 0; r < rr; ++r) {
            if (isRowFiltered(r) || _height[r] < 0) {
                setRowHeight(r, 0);
            } else {
                setRowHeight(r, h);
            }
        }
        return rr * h;
    }

    /*
     52372
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
            public String getColumnName(int _i) {
                return Routines.truncate(super.getColumnName(_i), 64);
            }
        };
    }

    /*
     accessibility
     */
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#getContext()
     * @author Anthony C. Liberto
     */
    protected String getContext() {
        return ".ctab";
    }

    /**
     * getAccessibleValueAt
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public Object getAccessibleValueAt(int _row, int _col) {
        return getValueAt(_row, _col);
    }

    /**
     * getAccessibleColumnNameAt
     *
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleColumnNameAt(int _col) {
        return getColumnName(_col);
    }

    /**
     * getAccessibleRowNameAt
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleRowNameAt(int _row) {
        if (rList != null) {
            if (rList.getDataSize() > _row && _row >= 0) {
                Object o = rList.getItemAt(_row);
                if (o != null) {
                    return o.toString();
                }
            }
        }
        return super.getAccessibleRowNameAt(_row);
    }

    /**
     * isAccessibleCellEditable
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAccessibleCellEditable(int _row, int _col) {
        return true;
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
     cr_featureMatrix
     */
    /**
     * @see javax.swing.JTable#prepareEditor(javax.swing.table.TableCellEditor, int, int)
     * @author Anthony C. Liberto
     */
    public Component prepareEditor(TableCellEditor _editor, int _row, int _col) {
        Object o = getValueAt(_row, _col);
        boolean isSelected = isCellSelected(_row, _col);
        Component comp = _editor.getTableCellEditorComponent(this, o, isSelected, _row, _col);
        return comp;
    }

    /**
     * getCellEditor
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public TableCellEditor getCellEditor(int _r, int _c) {
        Object o = getValueAt(_r, _c);
        if (showRelAttr()) {
//multi-attribute
            if (o instanceof SingleFlagAttribute) {
                featEdit.setAttribute((SingleFlagAttribute) o);
                cellEditor = featEdit;
                return featEdit;
            } else if (o instanceof TextAttribute) {
                textEdit.setAttribute((TextAttribute) o);
                cellEditor = textEdit;
                return textEdit;
            }

        }
        cellEditor = editor;
        return editor;
    }

    /**
     * @see javax.swing.event.CellEditorListener#editingStopped(javax.swing.event.ChangeEvent)
     * @author Anthony C. Liberto
     */
    public void editingStopped(ChangeEvent _ce) {
        TableCellEditor tmpEditor = getCellEditor();
        if (tmpEditor != null) {
            Object value = tmpEditor.getCellEditorValue();
            putValueAt(value, getEditingRowKey(), getEditingColumnKey());
            removeEditor(tmpEditor);
            requestFocus();
        }
    }

    /**
     * resetSelected
     * @author Anthony C. Liberto
     */
    public void resetSelected() {
        int rows[] = getSelectedRows();
        int cols[] = getSelectedColumns();
        for (int r = 0; r < rows.length; ++r) {
            for (int c = 0; c < cols.length; ++c) {
                if (showRelAttr()) {
                    putValueAt(null, rows[r], convertColumnIndexToModel(cols[c]));
                } else {
                    putValueAt("0", rows[r], convertColumnIndexToModel(cols[c]));
                }
            }
        }
        refreshList();
    }

    /**
     * setMatrixGroup
     * @param _mg
     * @author Anthony C. Liberto
     */
    public void setMatrixGroup(MatrixGroup _mg) {
        mg = _mg;
    }

    /**
     * getMatrixGroup
     * @return
     * @author Anthony C. Liberto
     */
    public MatrixGroup getMatrixGroup() {
        return mg;
    }

    /**
     * showRelAttr
     * @return
     * @author Anthony C. Liberto
     */
    public boolean showRelAttr() {
        if (mg != null) {
            return mg.showRelAttr();
        }
        return false;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#setFound(int, int)
     * @author Anthony C. Liberto
     */
    protected void setFound(int _r, int _c) {
        String key = getKey(_r);
        foundItem.putMap(key, getColumnKey(_c));
        findPt.x = _r;
        findPt.y = _c;
        setRowSelectionInterval(_r, _r);
        setColumnSelectionInterval(_c, _c);
        scrollRectToVisible(getCellRect(_r, _c, true));
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isFound(int, int)
     * @author Anthony C. Liberto
     */
    protected boolean isFound(int _r, int _c) {
        String key = getKey(_r);
        if (key == null || foundItem == null) {
            return false;
        }
        if (!foundItem.containsKey(key)) {
            return false;
        }
        return foundItem.mapContains(key, getColumnKey(_c));
    }

    /**
     * getCellRenderer
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public TableCellRenderer getCellRenderer(int _r, int _c) {
    	boolean isLocked = hasLock(_r, _c);
    	boolean isFound = isFound(_r, _c);
        
        if (isLocked){
        	 if (isFound) {
                 return lockedFoundRenderer;
             }
        	return lockedDefaultRenderer;
        }
        if (isFound) {
            return foundRend;
        }

        return cRend;
    }

    /*
     feature fill
     */
    /**
     * getFillComponent
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Component getFillComponent(int _r, int _c) {
        Object o = getValueAt(_r, _c);
//multi-attribute
        if (o instanceof SingleFlagAttribute) {
            featEdit.setAttribute((SingleFlagAttribute) o);
            return featEdit;
        } else if (o instanceof TextAttribute) {
            textEdit.setAttribute((TextAttribute) o);
            return textEdit;
        }
        return null;
    }

    /*
     featureMatrix_copy
     */
    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
        Transferable trans = null;
        if (isEditing()) {
            cancelEdit();
        }
        trans = getClipboardContents();
        if (trans != null) {
            try {
                String strPaste = (String) trans.getTransferData(DataFlavor.stringFlavor);
                paste(strPaste.trim());
            } catch (Exception _ufe) {
                _ufe.printStackTrace();
            }
        }
    }

    /**
     * paste
     * @param _s
     * @author Anthony C. Liberto
     */
    private void paste(String _s) {
        int[] rows = getSelectedRows();
        int[] cols = getSelectedColumns();
        int rr = rows.length;
        int cc = cols.length;
        Object val = null;
        boolean bFirstTime = true;
        for (int r = 0; r < rr; ++r) {
            for (int c = 0; c < cc; ++c) {
                if (bFirstTime) {
                    bFirstTime = false;
                    val = getPasteObject(rows[r], cols[c], _s);
                }
                putValueAt(val, rows[r], convertColumnIndexToModel(cols[c]));
            }
        }
        repaint();
    }

    /**
     * getPasteObject
     * @param _r
     * @param _c
     * @param _val
     * @return
     * @author Anthony C. Liberto
     */
    private Object getPasteObject(int _r, int _c, String _val) {
        Object o = getValueAt(_r, _c);
        if (o instanceof SingleFlagAttribute) {
            SingleFlagAttribute att = (SingleFlagAttribute) o;
            MetaFlag[] mFlags = (MetaFlag[]) att.get();
            if (mFlags != null) {
                int ii = mFlags.length;
                for (int i = 0; i < ii; ++i) {
                    if (mFlags[i] != null) {
                        if (descriptionEquals(mFlags[i], _val)) {
                            mFlags[i].setSelected(true);
                        } else if (mFlags[i].isSelected()) {
                            mFlags[i].setSelected(false);
                        }
                    }
                }
            }
            return mFlags;
        } else if (o instanceof TextAttribute) {
			return _val;			//MN_24226110
//MN_24226110            return o;
        }
        return null;
    }

    /**
     * descriptionEquals
     * @author Anthony C. Liberto
     * @return boolean
     * @param _flag
     * @param _data
     */
    protected boolean descriptionEquals(MetaFlag _flag, String _data) {
        return _flag.getLongDescription().equalsIgnoreCase(_data);
    }

}
