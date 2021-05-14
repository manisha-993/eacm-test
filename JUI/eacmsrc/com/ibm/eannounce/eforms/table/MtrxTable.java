/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: MtrxTable.java,v $
 * Revision 1.3  2010/07/10 15:52:04  wendy
 * output information for cell in matrix
 *
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:05  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.10  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.9  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.8  2005/02/01 15:33:41  tony
 * JTest Second Pass
 * Updated filter logic to improve functionality
 *
 * Revision 1.7  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.5  2005/01/18 19:04:05  tony
 * pivot modifications
 *
 * Revision 1.4  2005/01/14 20:32:12  tony
 * pivot
 *
 * Revision 1.3  2004/06/22 18:06:23  tony
 * featureMatrix
 *
 * Revision 1.2  2004/02/27 18:53:41  tony
 * removed display statements.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.36  2003/12/17 16:46:11  tony
 * 52910
 *
 * Revision 1.35  2003/12/16 20:22:10  tony
 * 52910
 *
 * Revision 1.34  2003/12/11 18:25:11  tony
 * 52910
 *
 * Revision 1.33  2003/11/11 00:42:22  tony
 * accessibility update, added convenience method to table.
 *
 * Revision 1.32  2003/11/10 22:51:02  tony
 * accessibility update
 *
 * Revision 1.31  2003/10/29 16:47:40  tony
 * 52728
 *
 * Revision 1.30  2003/10/29 00:13:46  tony
 * removed System.out. statements
 *
 * Revision 1.29  2003/10/24 21:11:29  tony
 * 52719
 *
 * Revision 1.28  2003/10/21 18:27:49  tony
 * 52664
 *
 * Revision 1.27  2003/10/10 23:30:43  tony
 * 52520
 *
 * Revision 1.26  2003/10/08 20:10:38  tony
 * 52477 update
 *
 * Revision 1.25  2003/10/08 16:58:08  tony
 * 52477
 *
 * Revision 1.24  2003/10/08 16:55:24  tony
 * 52477
 *
 * Revision 1.23  2003/10/03 16:39:12  tony
 * updated accessibility.
 *
 * Revision 1.22  2003/10/02 21:36:31  tony
 * added accessibility logic
 *
 * Revision 1.21  2003/09/23 16:29:11  tony
 * acl_20030923
 * enhanced logic to run a synchronized resize thread to
 * improve edit performance on the matrix.
 *
 * Revision 1.20  2003/09/17 17:32:13  tony
 * 52291
 *
 * Revision 1.19  2003/09/17 17:24:45  tony
 * 52294
 *
 * Revision 1.18  2003/09/10 20:55:20  tony
 * 52147
 *
 * Revision 1.17  2003/09/05 17:33:09  tony
 * 2003-09-05 memory enhancements
 *
 * Revision 1.16  2003/08/28 22:53:17  tony
 * memory update
 *
 * Revision 1.15  2003/08/26 21:17:09  tony
 * cr_TBD_3
 * update whereused-matrix & matrix-whereused functionality.
 *
 * Revision 1.14  2003/08/18 17:58:09  tony
 * 51779
 *
 * Revision 1.13  2003/06/26 15:40:59  tony
 * MCI -- LAX
 * Updated logic for matrix, based on issues discovered by KC To La
 * Fixed filtering, editing, and matrix from edit for form and vert.
 *
 * Revision 1.12  2003/06/19 16:09:43  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.11  2003/06/04 18:47:15  tony
 * 51112
 *
 * Revision 1.10  2003/05/22 19:31:46  tony
 * 50885
 *
 * Revision 1.9  2003/05/21 15:38:11  tony
 * updated table logic to allow for the table and model
 * to always know the specific type of table.  Based on a
 * table constant.
 *
 * Revision 1.8  2003/04/25 19:20:08  tony
 * added border to tables
 *
 * Revision 1.7  2003/04/23 20:59:10  tony
 * adjusted matrix table resizeing
 *
 * Revision 1.6  2003/04/23 17:16:37  tony
 * adjusted rendering
 *
 * Revision 1.5  2003/04/15 17:31:34  tony
 * changed to e-announce.focusborder
 *
 * Revision 1.4  2003/04/14 21:38:25  tony
 * updated table Logic.
 *
 * Revision 1.3  2003/03/24 19:56:15  tony
 * System enhancements to improve usability and
 * functionality of the application.
 *
 * Revision 1.2  2003/03/05 18:54:26  tony
 * accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.37  2002/12/02 18:39:30  tony
 * 23421
 *
 * Revision 1.36  2002/11/26 22:27:07  tony
 * removed invalid character
 *
 * Revision 1.35  2002/11/26 22:18:01  tony
 * 23392
 *
 * Revision 1.34  2002/11/21 16:51:14  tony
 * 23317
 *
 * Revision 1.33  2002/11/07 16:58:34  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;

import com.ibm.eannounce.eforms.action.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.erend.*;
import COM.ibm.eannounce.objects.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MtrxTable extends RSTable implements ETable, ActionListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
	private HeaderTable header = new HeaderTable();

    private MatrixFoundRend foundRend = new MatrixFoundRend();
    private MatrixRend mRend = new MatrixRend();

 //   private final int textCol = 0;

    private ERowList rList = new ERowList();
    //private String[] fillKeys = null;

    private CrossTable cTable = null;
    private MatrixAction mAction = null;

    private EComparator compare = null; //52477

    /**
     * mtrxTable
     * @param _o
     * @param _table
     * @param _ac
     * @author Anthony C. Liberto
     */
    public MtrxTable(Object _o, RowSelectableTable _table, ActionController _ac) {
        super(_o, _table, _ac, TABLE_MATRIX);
        init();
        //51298		setReplaceable(_table.canEdit());
        setReplaceable(eaccess().isEditable(_table)); //51298
        rList.setFixedCellHeight(-1);
        rList.setTable(this);
        //fillKeys = new String[getColumnCount()];
        cTable = getCrossTable(0);
        sortHeader(true); //52291
        return;
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
     */
    public MatrixAction getMatrixAction() {
        return mAction;
    }

    private CrossTable getCrossTable(int _col) {
        int col = -1;
        if (_col < 0 || _col >= getColumnCount()) { //23392
            if (cTable == null) { //52910
                cTable = new CrossTable(this, null, null); //52910
            } else { //52910
                cTable.updateModel(null); //52910
            } //52910
            return cTable; //52910
            //52910			return null;								//23392
        } //23392

        col = convertColumnIndexToModel(_col);

        if (cgtm != null) { //52664 shafic_dhalla_20031021
            EANFoundation ean = cgtm.getColumn(col);
            if (ean instanceof MatrixGroup) {
                MatrixGroup mg = (MatrixGroup) ean;
                if (cTable == null) {
                    cTable = new CrossTable(this, mg.getTable(), null);
                } else {
                    cTable.updateModel(mg.getTable());
                }
                cTable.setMatrixGroup(mg);
                return cTable;
            }
        } //52664 shafic_dhalla_20031021

        if (cTable == null) { //52910
            cTable = new CrossTable(this, null, null); //52910
        } else { //52910
            cTable.updateModel(null); //52910
        } //52910
        return cTable; //52910

        //52910		return null;
    }

    /**
     * getCrossTable
     * @return
     * @author Anthony C. Liberto
     */
    public CrossTable getCrossTable() {
        return cTable;
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
        int c = convertColumnIndexToModel(_c);
        if (isFound(_r, c)) {
            return foundRend;
        }
        return mRend;
    }

    /*
     52477
    	public Object getValueAt(int _r, int _c) {
    		return getValueAt(_r,_c,false);
    	}
    */
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
     */
    public EANFoundation getColumnByKey(int _col) {
        int col = convertColumnIndexToModel(_col);
        return cgtm.getColumn(col);
    }

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
        return;
    }

    /**
     * @see javax.swing.JTable#prepareEditor(javax.swing.table.TableCellEditor, int, int)
     * @author Anthony C. Liberto
     */
    public Component prepareEditor(TableCellEditor _editor, int _row, int _col) {
        Object o = getEANObject(_row, _col);
        boolean isSelected = isCellSelected(_row, _col);
        Component comp = _editor.getTableCellEditorComponent(this, o, isSelected, _row, _col);
        return comp;
    }

    /**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _r, int _c, EventObject _e) {
        return false;
    }

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
        initAccessibility("accessible.mtrxTable");
        setRowMargin(0);
        setBorder(UIManager.getBorder("eannounce.focusborder"));
        setColumnSelectionAllowed(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        resizeCells();
        //52291		sort();
        setBorder(UIManager.getBorder("eannounce.focusBorder"));
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setCellSelectionEnabled(true); //20020116
        setDefaultRenderer(Object.class, mRend); //accessible
        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
        }
        return;
    }

    /**
     * getTableHeight
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getTableHeight() {
        int rr = getRowCount();
        Dimension d = getIntercellSpacing();
        int height = 0;
        for (int r = 0; r < rr; ++r) {
            height += getRowHeight(r) + d.height;
        }
        return height;
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
        return;
    }

    /**
     * replaceString
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _new
     * @param _old
     * @param _r
     */
    public boolean replaceString(String _old, String _new, int _r, int _c) {
        return false;
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
     */
    public void setFillable(boolean _b) {
        fillable = _b;
    }

    /**
     * isFillable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFillable() {
        return fillable;
    }

    /**
     * @see javax.swing.JTable#setValueAt(java.lang.Object, int, int)
     * @author Anthony C. Liberto
     */
    public void setValueAt(Object _o, int _r, int _c) {
    }

    /**
     * setValueAt
     * @param _o
     * @param _rKey
     * @param _colKey
     * @author Anthony C. Liberto
     */
    public void setValueAt(Object _o, String _rKey, String _colKey) {
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
		if (mAction != null) {								//20050201
			mAction.refreshHeader(MatrixAction.MATRIX);		//20050201
		} else {											//20050201
			refreshList(); //19938
		}													//20050201
        return; //19938
    } //19938

    /**
     * refreshList
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ERowList refreshList() {
        rList.refresh(cgtm.getRowHeader());
        rList.setHeaderValue(cgtm.getTableTitle());
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
     * moveColumn
     * @param _attCode
     * @param _vis
     * @param _pos
     * @return
     * @author Anthony C. Liberto
     */
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
    }
/*
    private boolean isVisible(TableColumn _tc) {
        if (_tc.getWidth() > 0) {
            return true;
        }
        return false;
    }
*/
    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (cellEditor != null) { //21864
            cellEditor.cancelCellEditing();
        } //21864
        if (header != null) {
            header.dereference();
            header = null;
        }

        if (foundRend != null) {
            foundRend.removeAll();
            foundRend.removeNotify();
            foundRend = null;
        }

        if (mRend != null) {
            mRend.removeAll();
            mRend.removeNotify();
            mRend = null;
        }

        if (cgtm != null) {
            cgtm.dereference();
            cgtm = null;
        }
        if (foundItem != null) {
            foundItem.clear();
            foundItem = null;
        }
        if (hiddenRows != null) {
            hiddenRows.clear();
            hiddenRows = null;
        }
        compare = null;
        super.dereference();
        return;
    }
/*
    private int getWidth(FontMetrics _fm, Object _o, int _rows) {
        int w = 20;
        if (_o == null) {
            return w;
        }
        if (_o instanceof String) {
            w += _fm.stringWidth((String) _o);
        } else if (_o instanceof Integer) {
            w += _fm.stringWidth(((Integer) _o).toString());
        }
        return w;
    }

    private boolean isCellOnScreen(int _r, int _c) {
        Rectangle a = getVisibleRect();
        Rectangle b = getCellRect(_r, _c, true);
        if (a.intersects(b)) {
            return true;
        }
        return false;
    }
*/
    /**
     * @see javax.swing.JTable#isCellEditable(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(int _r, int _c) {
        return false;
    }

    /**
     * isCellLocked
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCellLocked(int _r, int _c) {
        return false;
    }

    /**
     * isCellLocked
     *
     * @param _r
     * @param _c
     * @param _acquireLock
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCellLocked(int _r, int _c, boolean _acquireLock) {
        return false;
    }

    /**
     * isPasteable
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPasteable(int _r, int _c) {
        return false;
    }

    /*
     cr_TBD_3
    	public void columnSelectionChanged(ListSelectionEvent _lse) {
    		super.columnSelectionChanged(_lse);
    		getCrossTable(getSelectedColumn());
    		if (mAction != null)
    			mAction.refreshCrossTab();
    		return;																			//19937
    	}																					//19937
    */
    /**
     * showSort
     *
     * @author Anthony C. Liberto
     */
    public void showSort() {
        super.showSort();
        /*
         50885
        		if (mAction != null) {
        			mAction.refreshHeader(mAction.MATRIX);
        			mAction.setSort(mAction.CROSS,getKeys(),true);
        			mAction.refreshHeader(mAction.CROSS);
        		}
        */
        return;
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
                mAction.refreshHeader(MatrixAction.MATRIX);
            } else {
                mAction.refreshHeader(MatrixAction.MATRIX);
                mAction.setSort(MatrixAction.CROSS, cgtm.getRowKeyArray(), true);
                mAction.refreshHeader(MatrixAction.CROSS);
                mAction.synchronizeSortHeader(MatrixAction.CROSS);
            }
        }
        return;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isMultiLineClass(int)
     * @author Anthony C. Liberto
     */
    protected boolean isMultiLineClass(int _r) {
        return true;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isMatrixTableFormat()
     * @author Anthony C. Liberto
     */
    protected boolean isMatrixTableFormat() {
        return true;
    }

    /*
     * abstract stuff
     */
    /**
     * moveToError
     * @author Anthony C. Liberto
     * @param bre
     */
    public void moveToError(EANBusinessRuleException bre) {
    }

    /**
     * getInformation
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getInformation() {
        /*
        		int r = getSelectedRow();
        		int c = getSelectedColumn();
        		if (isValidCell(r,c)) {
        			EANFoundation colFoundation = cgtm.getColumn(convertColumnIndexToModel(c));
        			EANFoundation rowFoundation = (EANFoundation)cgtm.getRow(r);

        			if (colFoundation != null) {
        				colFoundation.dump(false);
        			}

        			if (rowFoundation != null) {
        				rowFoundation.dump(false);
        			}

        			Object o = cgtm.getObject();
        			if (o instanceof MatrixList) {
        				((MatrixList)o).dump(false);
        				((MatrixList)o).getEntityList().dump(false);
        			}
        		}
        */
    	
      	int r = getSelectedRow();
    	int c = convertColumnIndexToModel(getSelectedColumn());

    	EANFoundation colFoundation =cgtm.getColumn(c);
    	EANFoundation rowFoundation = (EANFoundation)cgtm.getRow(r);

    	// if not pivot then row is the entity and column is the matrixgroup
    	// if pivot then column is the entity and row is the matrixgroup
    	StringBuffer sb = new StringBuffer();
    	MatrixGroup mg = null;
    	String eiKey = null;
    
    	if (colFoundation instanceof MatrixGroup){
    		mg = (MatrixGroup)colFoundation;
    	}
    	if (colFoundation instanceof EntityItem){
    		eiKey = colFoundation.getKey();
    	}

    	if (rowFoundation instanceof MatrixGroup){
    		mg = (MatrixGroup)rowFoundation;
    	}
    	if (rowFoundation instanceof EntityItem){
    		eiKey = rowFoundation.getKey();
    	}

    	if (eiKey != null){
    		sb.append("Entity: "+RETURN+"   "+eiKey+RETURN);
    	}

    	if (mg != null){
    		sb.append("MatrixGroup: "+RETURN);
    		for (int i=0; i<mg.getMatrixItemCount(); i++){
    			MatrixItem mi = mg.getMatrixItem(i);
    			if(mi.getRelatorList().size()==0){ // no relators for this parent-child
    				continue;
    			}
    			String childKey = mi.getChildEntity().getKey();
    			String parentKey = mi.getParentEntity().getKey();
    			if(eiKey.equals(parentKey)){
    				sb.append("   "+childKey+RETURN);
    				for (int rl=0; rl<mi.getRelatorList().size(); rl++){
    					EntityItem ei = (EntityItem) mi.getRelatorList().getAt(rl);
    					sb.append("       "+ei.getKey()+RETURN);
    				}
    			}else if(eiKey.equals(childKey)){
    				sb.append("   "+parentKey+RETURN);
    				for (int rl=0; rl<mi.getRelatorList().size(); rl++){
    					EntityItem ei = (EntityItem) mi.getRelatorList().getAt(rl);
    					sb.append("       "+ei.getKey()+RETURN);
    				}
    			}
    		}
    	}

    	if(sb.length()>0){
    		return sb.toString();
    	}
    	 
        return getString("nia");
    }

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() { //21643
        return "MTR" + mAction.getKey();
    }

    /*
     acl_20030923
    	protected void resizeAfterEdit(int _row, int _col) {		//23317
    //51779		resizeEditColumn(_col);									//23317
    //51779		resizeEditRow(_row);									//23317
    		resizeCells();											//51779
    		return;													//23317
    	}															//23317
    */
    /*
     50885
    */

    /**
     * sort
     *
     * @param _i
     * @param _d
     * @author Anthony C. Liberto
     */
    public void sort(int[] _i, boolean[] _d) {
        super.sort(_i, _d);
        if (mAction != null) {
            mAction.refreshHeader(MatrixAction.MATRIX);
            mAction.setSort(MatrixAction.CROSS, getKeys(), true);
            mAction.refreshHeader(MatrixAction.CROSS);
        }
        return;
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
        return;
    }

    /*
     MCI--LAX
     */
    /**
     * resetFilter
     *
     * @author Anthony C. Liberto
     */
    public void resetFilter() {
        super.resetFilter();
        System.out.println("mtrxTable.resetFilter");
        refreshList();
        return;
    }

    /*
     cr_TBD_3
     */

    /**
     * @see javax.swing.event.TableColumnModelListener#columnSelectionChanged(javax.swing.event.ListSelectionEvent)
     * @author Anthony C. Liberto
     */
    public void columnSelectionChanged(ListSelectionEvent _lse) {
        super.columnSelectionChanged(_lse);
        if (!isPivot()) { //pivot
            if (!_lse.getValueIsAdjusting()) {
                getCrossTable(getSelectedColumn());
                if (mAction != null) {
                    mAction.refreshCrossTab();
                    if (tree != null) {
                        tree.load(ac.getAllActions(null), getTableTitle());
                        ac.refreshMenu();
                    }
                }
            }
        } //pivot
        return;
    }

    /*
     52147
     */
    /**
     * getHelpText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getHelpText() {
        return getString("nia");
    }

    /*
     52294
     */
    /**
     * resizeCells
     *
     * @author Anthony C. Liberto
     */
    public void resizeCells() {
        super.resizeCells();
        refreshList();
        return;
    }

    /*
     acl_20030923
     */
    /**
     * resizeAfterEdit
     *
     * @param _row
     * @param _col
     * @author Anthony C. Liberto
     */
    protected void resizeAfterEdit(final int _row, final int _col) {
        ESwingWorker resizeWorker = new ESwingWorker() {
            public Object construct() {
                resizeCellsSynch();
                return null;
            }
        };
        resizeWorker.start();
        return;
    }

    /**
     * resizeCellsSynch
     *
     * @author Anthony C. Liberto
     * @concurrency $none
     */
    protected synchronized void resizeCellsSynch() {
        resizeCells();
        return;
    }

    /*
     accessibility
     */
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#getContext()
     * @author Anthony C. Liberto
     */
    protected String getContext() {
        return ".mtrx";
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
            Object o = rList.getItemAt(_row);
            if (o != null) {
                return o.toString();
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
     52477
     */
    /**
     * @see javax.swing.JTable#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
        Object out = getValueAt(_r, _c, false);
        if (out != null && out instanceof String) {
            String[] sortArray = Routines.getStringArray((String) out, RETURN);
            if (sortArray != null) { //52520
                if (compare == null) {
                    compare = new EComparator();
                }
                Arrays.sort(sortArray, compare);
                return Routines.toString(sortArray);
            } //52520
        }
        return out;
    }

    /*
     52910
     */
    /**
     * adjustOrder
     * @param _mcog
     * @author Anthony C. Liberto
     */
    public void adjustOrder(MetaColumnOrderGroup _mcog) {
        int xx = -1;
        if (_mcog != null) {
            RSTableColumnModel tcm = (RSTableColumnModel) getColumnModel();
            if (tcm != null) {
                int ii = tcm.getColumnCount();
                HashMap map = new HashMap();
                String attCode = null;
                for (int i = 0; i < ii; ++i) {
                    RSTableColumn tc = (RSTableColumn) tcm.getColumn(i);
                    if (tc != null) {
                        attCode = tc.getKey();
                        if (attCode != null) {
                            MetaColumnOrderItem item = _mcog.getMetaColumnOrderItem(attCode);
                            if (item != null) {
                                item.setVisible(true);
                                item.setColumnOrder(i);
                                map.put(attCode, attCode);
                            }
                        }
                    }
                }
                xx = cgtm.getColumnCount();
                for (int x = 0; x < xx; ++x) {
                    attCode = cgtm.getColumnKey(x);
                    if (attCode != null && !map.containsKey(attCode)) {
                        MetaColumnOrderItem item = _mcog.getMetaColumnOrderItem(attCode);
                        if (item != null) {
                            item.setVisible(false);
                            item.setColumnOrder(ii);
                            ++ii;
                        }
                    }
                }
                map.clear();
                map = null;
            }
        }
        return;
    }

    /**
     * revertOrder
     * @author Anthony C. Liberto
     */
    public void revertOrder() {
        cgtm.refresh();
        createDefaultColumnsFromModel();
        resizeCells();
        if (isFocusable()) {
            setRowSelectionInterval(0, 0);
            setColumnSelectionInterval(0, 0);
        }
        revalidate();
        return;
    }

    /*
     cr_featureMatrix
     */
    /**
     * generateTable
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public CrossTable generateTable(int _col) {
        int col = convertColumnIndexToModel(_col);
        if (cgtm != null) {
            EANFoundation ean = cgtm.getColumn(col);
            if (ean instanceof MatrixGroup) {
                MatrixGroup mg = (MatrixGroup) ean;
                if (mg != null) {
                    return new CrossTable(this, mg.getTable(), null);
                }
            }
        }
        return null;
    }

    /*
     pivot
     */
    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     * @author Anthony C. Liberto
     */
    public void valueChanged(ListSelectionEvent _lse) {
        super.valueChanged(_lse);
        if (isPivot()) {
            if (!_lse.getValueIsAdjusting()) {
                getCrossTableRow(getSelectedRow());
                if (mAction != null) {
                    mAction.refreshCrossTab();
                    if (tree != null) {
                        tree.load(ac.getAllActions(null), getTableTitle());
                        ac.refreshMenu();
                    }
                }
            }
        }
        return;
    }

    private CrossTable getCrossTableRow(int _row) {
        String strKey = null;
        if (_row < 0 || _row >= getRowCount()) {
            if (cTable == null) {
                cTable = new CrossTable(this, null, null);
            } else {
                cTable.updateModel(null);
            }
            return cTable;
        }

        strKey = cgtm.getRowKey(_row);

        if (cgtm != null) {
            Object ean = cgtm.getRowByKey(strKey);
            if (ean != null && ean instanceof MatrixGroup) {
                MatrixGroup mg = (MatrixGroup) ean;
                if (cTable == null) {
                    cTable = new CrossTable(this, mg.getTable(), null);
                } else {
                    cTable.updateModel(mg.getTable());
                }
                cTable.setMatrixGroup(mg);
                return cTable;
            }
        }

        if (cTable == null) {
            cTable = new CrossTable(this, null, null);
        } else {
            cTable.updateModel(null);
        }
        return cTable;
    }

    /**
     * pivotResynch
     *
     * @author Anthony C. Liberto
     */
    public void pivotResynch() {
        if (mAction != null) {
            mAction.refreshHeader(MatrixAction.MATRIX);
            mAction.setSort(MatrixAction.CROSS, cgtm.getRowKeyArray(), true);
            mAction.refreshHeader(MatrixAction.CROSS);
            mAction.synchronizeSortHeader(MatrixAction.CROSS);
        }
        return;
    }
}
