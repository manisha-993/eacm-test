//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.table;


import com.ibm.eacm.mtrx.AdjustColAction;
import com.ibm.eacm.mtrx.AdjustRowAction;
import com.ibm.eacm.mtrx.DeleteColAction;
import com.ibm.eacm.mtrx.DeleteRowAction;
import com.ibm.eacm.mtrx.MatrixActionTab;
import com.ibm.eacm.mtrx.ResetSelectedAction;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.rend.LabelRenderer;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import java.util.*;
import java.util.List;

/**
 * to test on ea6, w/w all my models, then not categorized->MTRMODEL
 * adding column 0 with matrix info, to allow proper sorting and filtering
 * @author Wendy Stimpson
 */
// $Log: MtrxTable.java,v $
// Revision 1.3  2013/07/31 17:10:29  wendy
// make sure rowsorter is not null before using it
//
// Revision 1.2  2013/07/18 18:57:40  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class MtrxTable extends RSTTable
{
	private static final long serialVersionUID = 1L;

	private LabelRenderer foundRend = null;
	private LabelRenderer mtrxRend = null;
    private LabelRenderer mRend = null;
    private CrossTable cTable = null;
    private Col0Listener col0listener;
    private MatrixActionTab actionTab = null;
    private FilterGroup prevFG = null;
    private FilterGroup prevColFG = null;

  	private List<RowSorter.SortKey> normSortKeys = null; // non pivot sort keys
	private List<RowSorter.SortKey> pivotedSortKeys = null; // pivoted sort keys

    /**
     * is this table pivoted
     * @return
     */
    public boolean isPivot() {return getModel() instanceof PivotMtrxTableModel;}

	/**
	 * allow derived classes to provide a different rsttablemodel
	 * @return
	 */
	protected RSTTableModel createTableModel(){
		return new MtrxTableModel(this,64);
	}
    /* (non-Javadoc)
     * use derived column model to prevent moving col0
     * @see javax.swing.JTable#createDefaultColumnModel()
     */
    protected TableColumnModel createDefaultColumnModel() {
        return new MtrxTableColumnModel();
    }
	/* (non-Javadoc)
	 * dont select col0 info
	 * @see javax.swing.JTable#setColumnSelectionInterval(int, int)
	 */
	public void setColumnSelectionInterval(int index0, int index1) {
		if (index0==0){
			index0=1;
		}
		if (index1==0){
			index1=1;
		}
		super.setColumnSelectionInterval(index0, index1);
	}
	/* (non-Javadoc)
	 * hide col 0 with the info
	 * @see com.ibm.eacm.table.RSTTable2#createDefaultColumnsFromModel()
	 */
	public void createDefaultColumnsFromModel() {
		super.createDefaultColumnsFromModel();
		TableColumnModel tcm = getColumnModel();

		if(tcm.getColumnCount()>0){
			// hide column 0
			TableColumn tc = tcm.getColumn(0);
			tc.setMinWidth(0);
			tc.setMaxWidth(0);
			tc.setPreferredWidth(0);
			tc.setWidth(0);
		}
	}
    /**
     * calculate height needed for this row
     * @param fm
     * @param baseHeight
     * @param str - could be multiline in some tables
     * @return
     */
    protected int getRowHeight(FontMetrics fm, int baseHeight, String str){
    	int numlines =  Routines.getCharacterCount(str,RETURN);
		return (numlines * fm.getHeight());
    }
    /**
     * mtrxTable
     */
    public MtrxTable(MatrixList mList, MatrixActionTab mAction) {
        super(mList.getTable(), mAction.getProfile());
        actionTab = mAction;

        init();

        setCrossTable(0, mList.getProfile());

        //will stop tab from going into col0
        col0listener = new Col0Listener();
        getColumnModel().getSelectionModel().addListSelectionListener(col0listener);
        addKeyListener(col0listener);

		// add these actions as focus listeners and disable when crosstable loses focus
		//MTRX_ADJUSTCOL
		addFocusListener(((AdjustColAction)actionTab.getAction(MTRX_ADJUSTCOL)));
		//MTRX_ADJUSTROW
		addFocusListener(((AdjustRowAction)actionTab.getAction(MTRX_ADJUSTROW)));
		//MTRX_DELETEROW
		addFocusListener(((DeleteRowAction)actionTab.getAction(MTRX_DELETEROW)));
		//MTRX_DELETECOL
		addFocusListener(((DeleteColAction)actionTab.getAction(MTRX_DELETECOL)));
		//MTRX_RESETSEL
		addFocusListener(((ResetSelectedAction)actionTab.getAction(MTRX_RESETSEL)));
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#init()
     */
    protected void init() {
      	super.init();

    	mRend = new LabelRenderer();
    	foundRend =  new LabelRenderer();
    	foundRend.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);

    	mtrxRend = new LabelRenderer();
    	mtrxRend.setFocusBorderKeys(RAISED_BORDER_KEY, RAISED_BORDER_KEY);
    	mtrxRend.setSelectionColorKeys("Label.foreground", ROW_REND_COLOR);
    	mtrxRend.setColorKeys(null, ROW_REND_COLOR);

        setCellSelectionEnabled(true);
        setDefaultRenderer(Object.class, mRend);

        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // must set this after changing selection mode
        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
        }
        if (getColumnCount() > 1) {
            setColumnSelectionInterval(1, 1);
        }
    }
	/**
	 * get the accessibility resource key
	 * @return
	 */
	protected String getAccessibilityKey() {
		return "accessible.mtrxTable";
	}
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getFullColumnName(int)
     */
    protected String getFullColumnName(int viewColumnIndex){
    	return ((MtrxTableModel)this.getModel()).getFullColumnName(convertColumnIndexToModel(viewColumnIndex));
    }
    /**
     * pivot this table by changing table model
     */
    public void pivot(){
    	// changing tbl model will call valuechanged in actiontab before rst is set in new model
    	getSelectionModel().removeListSelectionListener(actionTab);
		getColumnModel().getSelectionModel().removeListSelectionListener(actionTab);
	    getColumnModel().getSelectionModel().removeListSelectionListener(col0listener);

    	cTable.cancelCurrentEdit();

    	clearSelection();
    	MtrxTableModel mtm = (MtrxTableModel)getModel();

      	if (mtm instanceof PivotMtrxTableModel){
    		if(getRowSorter() != null && getRowSorter().getSortKeys()!=null){
    			//must make copy
    			pivotedSortKeys = new ArrayList<RowSorter.SortKey>();
    			for (int i=0; i<getRowSorter().getSortKeys().size(); i++){
    				RowSorter.SortKey skey = getRowSorter().getSortKeys().get(i);
    				pivotedSortKeys.add(new RowSorter.SortKey(skey.getColumn(), skey.getSortOrder()));
    			}
    		}else{
    			pivotedSortKeys = null;
    		}

    		if(isFiltered()){
    			prevColFG = getColFilterGroup();
    		}

    		setRSTModel(new MtrxTableModel(this,64), actionTab.getProfile());
    	}else{
    		if(getRowSorter() != null && getRowSorter().getSortKeys()!=null){
    			//must make copy
    			normSortKeys = new ArrayList<RowSorter.SortKey>();
    			for (int i=0; i<getRowSorter().getSortKeys().size(); i++){
    				RowSorter.SortKey skey = getRowSorter().getSortKeys().get(i);
    				normSortKeys.add(new RowSorter.SortKey(skey.getColumn(), skey.getSortOrder()));
    			}
    		}else{
    			normSortKeys = null;
    		}
    		if(isFiltered()){
    			prevFG = getFilterGroup();
    		}
    		setRSTModel(new PivotMtrxTableModel(this,64),actionTab.getProfile());
    	}

    	getSelectionModel().addListSelectionListener(actionTab);
		getColumnModel().getSelectionModel().addListSelectionListener(actionTab);
	    getColumnModel().getSelectionModel().addListSelectionListener(col0listener);

     	refreshHeaderModel(getTableTitle());

		//mtm.dereference(); - this messes with the rst
    	mtm.derefListeners();

    	// find the mg currently in the ctable
    	MatrixGroup mg = cTable.getMatrixGroup();
    	if(isPivot()){
        	for(int i=1;i<this.getRowCount(); i++){
        		String key = this.getRSTable().getColKey((convertRowIndexToModel(i)));
        		EANFoundation ean = ((MtrxTableModel)getModel()).getRowByKey(key);
        		if (mg ==ean){
        			if (getRowCount() > i) {
        				if (getColumnCount() > 1) {
        					setColumnSelectionInterval(1, 1);
        				}
        				setRowSelectionInterval(i, i);
        				scrollToRow(i);
        			}
        		    break;
        		}
        	}
    	}else{
        	for(int i=1;i<this.getColumnCount(); i++){
        		EANFoundation ean =((MtrxTableModel)getModel()).getColumnEAN(convertColumnIndexToModel(i));
        		if (mg ==ean){
        			if (getColumnCount() > i) {
        				setColumnSelectionInterval(i, i);
        				scrollToRow(0);
        			}
        			break;
        		}
        	}
    	}

    	cTable.pivot();
    }

	/* (non-Javadoc)
	 * called when rsttable is updated in the tablemodel
	 * @see com.ibm.eacm.table.RSTTable2#setSortKeys()
	 */
	protected void setSortKeys(){
     	List<RowSorter.SortKey> sortKeys = null;

    	MtrxTableModel mtm = (MtrxTableModel)getModel();
       	if (mtm instanceof PivotMtrxTableModel){
       		sortKeys = pivotedSortKeys;
      		if(prevColFG!=null){
       			setColFilterGroup(prevColFG);
       			this.filter();
       			prevColFG = null;
       		}
       	}else{
       		sortKeys = normSortKeys;
       		if(prevFG!=null){
       			setFilterGroup(prevFG);
       			this.filter();
       			prevFG = null;
       		}
       	}
		if (sortKeys!=null){
			setSortKeys(sortKeys);
		}else{
			super.setSortKeys();
		}
	}

    /**
     * rollbackMatrix
     * called when user wants to cancel all changes
     */
    public void rollbackMatrix() {
    	cTable.cancelCurrentEdit();
    	// matrixlist will rollback each matrixgroup
        ((MtrxTableModel)getModel()).rollbackMatrix();

      	// only different values will actually fire an event - make sure actions listening
		// reflect the correct state
		firePropertyChange(DATACHANGE_PROPERTY, true, false);

        resizeCells();
        cTable.resizeCells();
    }


    /**
     * @return
     */
    public EANActionItem[] getActionItemsAsArray() {
    	 return ((MtrxTableModel)getModel()).getActionItemsAsArray(getProfile(),getSelectedColumn());
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.RSTTable#getSelectedEntityItems(boolean, boolean)
     */
    public EntityItem[] getSelectedEntityItems(boolean _new, boolean _maxEx) throws OutOfRangeException {
        int[] rows = getSelectedRows();
        EntityItem[] out = null;
        if (rows.length > 0) {
        	if (_maxEx && rows.length > getRecordLimit(getProfile())) {
        		//msg12003.0 = \n{0} selectable items exceeds the maximum number.\nPlease select {1} or less items.
        		throw new OutOfRangeException(Utils.getResource("msg12003.0",""+rows.length,""+
        				getRecordLimit(getProfile())));
        	}

        	out = new EntityItem[rows.length];
        	for (int i = 0; i < rows.length; ++i) {
        		int row = this.convertRowIndexToModel(rows[i]);
        	  	EANFoundation rowFoundation = ((MtrxTableModel)getModel()).getRowEAN(row);
            	if (rowFoundation instanceof EntityItem){
            		out[i] = (EntityItem)rowFoundation;
            	}
        	}
        }
        return out;
    }
    /**
     * getCrossTable
     * @return
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
     */
    public TableCellRenderer getCellRenderer(int _r, int _c) {
    	if (convertColumnIndexToModel(_c)==0){
    		return mtrxRend;
    	}

        if (isFound(_r, _c)) {
            return foundRend;
        }
        return mRend;
    }

    /**
     * dereference
     *
     */
    public void dereference() {
        getColumnModel().getSelectionModel().removeListSelectionListener(col0listener);

        removeKeyListener(col0listener);
        col0listener = null;
		// add these actions as focus listeners and disable when crosstable loses focus
		removeFocusListener(((AdjustColAction)actionTab.getAction(MTRX_ADJUSTCOL)));
		//MTRX_ADJUSTROW
		removeFocusListener(((AdjustRowAction)actionTab.getAction(MTRX_ADJUSTROW)));
		//MTRX_DELETEROW
		removeFocusListener(((DeleteRowAction)actionTab.getAction(MTRX_DELETEROW)));
		//MTRX_DELETECOL
		removeFocusListener(((DeleteColAction)actionTab.getAction(MTRX_DELETECOL)));
		//MTRX_RESETSEL
		removeFocusListener(((ResetSelectedAction)actionTab.getAction(MTRX_RESETSEL)));

    	foundRend.dereference();
    	foundRend = null;

    	mtrxRend.dereference();
    	mtrxRend = null;

    	mRend.dereference();
    	mRend = null;

    	pivotedSortKeys = null;
    	normSortKeys = null; // non pivot sort keys
    	prevFG = null;
    	prevColFG = null;

		if (cTable != null) {
			cTable.getSelectionModel().removeListSelectionListener(actionTab);
			cTable.getColumnModel().getSelectionModel().removeListSelectionListener(actionTab);
			cTable.getColumnModel().removeColumnModelListener(actionTab);
			//MTRX_ADJUSTCOL
			cTable.removeFocusListener(((AdjustColAction)actionTab.getAction(MTRX_ADJUSTCOL)));
			//MTRX_ADJUSTROW
			cTable.removeFocusListener(((AdjustRowAction)actionTab.getAction(MTRX_ADJUSTROW)));
			//MTRX_DELETEROW
			cTable.removeFocusListener(((DeleteRowAction)actionTab.getAction(MTRX_DELETEROW)));
			//MTRX_DELETECOL
			cTable.removeFocusListener(((DeleteColAction)actionTab.getAction(MTRX_DELETECOL)));
			//MTRX_RESETSEL
			cTable.removeFocusListener(((ResetSelectedAction)actionTab.getAction(MTRX_RESETSEL)));

			cTable.dereference();
			cTable = null;
		}

	    actionTab = null;

    	super.dereference();
    }

    /* (non-Javadoc)
     * matrix table is never editable, stop it here
     * @see javax.swing.JTable#isCellEditable(int, int)
     */
    public boolean isCellEditable(int _r, int _c) {
        return false;
    }

    /**
     * get the width needed for this object
     * @param fm
     * @param o
     * @return
     */
    protected int getColWidth(FontMetrics fm, Object o) {
        int w = MIN_COL_WIDTH;
        if (o != null) {
             String str = o.toString();
             if (str.indexOf('\n') >= 0) {
            	 w = getWidth(fm, Routines.getStringArray(str,RETURN));
             }else {
            	 w = fm.stringWidth(Routines.truncate(str,164));
             }
        }
        return w;
    }

    private int getWidth(FontMetrics fm, String[] _str) {
    	int w = 0;
    	for (int i=0;i<_str.length;++i) {
    		w = Math.max(w,fm.stringWidth(_str[i]));
    	}
    	return w;
    }

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.RSTTable#generateFoundKey(int, int, boolean)
	 */
	protected String generateFoundKey(int viewRow, int viewCol, boolean rowOnly) {
	   	int mdlrow = convertRowIndexToModel(viewRow);
    	int mdlcol = convertColumnIndexToModel(viewCol);
		return getInformation(mdlrow,mdlcol).replace('\n', ' ');
	}
    /**
     * getInformation
     *
     * @return
     */
    public String getInformation() {
    	int r = convertRowIndexToModel(getSelectedRow());
    	int c = convertColumnIndexToModel(getSelectedColumn());

    	return getInformation(r, c);
    }
    /**
     * @param r
     * @param c
     * @return
     */
    private String getInformation(int r, int c) {
    	EANFoundation colFoundation =((MtrxTableModel)getModel()).getColumnEAN(c);
    	EANFoundation rowFoundation = ((MtrxTableModel)getModel()).getRowEAN(r);

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
    		sb.append("Entity: "+NEWLINE+"   "+eiKey+NEWLINE);
    	}

    	if (mg != null){
    		sb.append("MatrixGroup: "+NEWLINE);
    		for (int i=0; i<mg.getMatrixItemCount(); i++){
    			MatrixItem mi = mg.getMatrixItem(i);
    			if(mi.getRelatorList().size()==0){ // no relators for this parent-child
    				continue;
    			}
    			String childKey = mi.getChildEntity().getKey();
    			String parentKey = mi.getParentEntity().getKey();
    			if(eiKey.equals(parentKey)){
    				sb.append("   "+childKey+NEWLINE);
    				for (int rl=0; rl<mi.getRelatorList().size(); rl++){
    					EntityItem ei = (EntityItem) mi.getRelatorList().getAt(rl);
    					sb.append("       "+ei.getKey()+NEWLINE);
    				}
    			}else if(eiKey.equals(childKey)){
    				sb.append("   "+parentKey+NEWLINE);
    				for (int rl=0; rl<mi.getRelatorList().size(); rl++){
    					EntityItem ei = (EntityItem) mi.getRelatorList().getAt(rl);
    					sb.append("       "+ei.getKey()+NEWLINE);
    				}
    			}
    		}
    	}

    	if(sb.length()>0){
    		return sb.toString();
    	}
    	return Utils.getResource("nia");
    }

    /**
     * getUIPrefKey
     */
    public String getUIPrefKey() {
        return  "MTR"+(isPivot()?"PVT":"")+actionTab.getKey();
    }

    /**
     * getHelpText
     */
    public String getHelpText() {
        return Utils.getResource("nia"); // none available, dont go down to model level
    }

    /**
     * @see javax.swing.JTable#getValueAt(int, int)
     *
     */
    public Object getValueAt(int _r, int _c) {
        Object out = getValueAt(_r, _c, false);
        if (out instanceof String) {
            String[] sortArray = Routines.getStringArray((String) out, RETURN);
            if (sortArray != null) {
                Arrays.sort(sortArray);
                return Routines.toString(sortArray);
            }
        }
        return out;
    }

    /**
     * called to set or update crosstable when table is pivoted
     * @param _row
     */
    private void setCrossTableRow(int _row,Profile prof) {
     	if (_row != -1 && _row < getRowCount()) {
    		String key = this.getRSTable().getColKey((convertRowIndexToModel(_row)));

    		EANFoundation ean = ((MtrxTableModel)getModel()).getRowByKey(key);
    		if (ean instanceof MatrixGroup) {
    			MatrixGroup mg = (MatrixGroup) ean;
    			updateCrossTable(mg, prof);
    		}
    	}
    }
    /**
     * create or update the crosstable based on column selected
     * @param _viewcol
     */
    private void setCrossTable(int _viewcol, Profile prof) {
    	if (_viewcol != -1 && _viewcol < getColumnCount()) {
    		EANFoundation ean =((MtrxTableModel)getModel()).getColumnEAN(convertColumnIndexToModel(_viewcol));
    		if (ean instanceof MatrixGroup) {
    			MatrixGroup mg = (MatrixGroup) ean;
    			updateCrossTable(mg, prof);
    		}
    	}
    }
    private void updateCrossTable(MatrixGroup mg, Profile prof){
    	if (cTable == null) {
			cTable = new CrossTable(mg, actionTab);
			cTable.setMatrixTable(this);
			cTable.addFocusListener(actionTab);
			cTable.getSelectionModel().addListSelectionListener(actionTab);
			cTable.getColumnModel().addColumnModelListener(actionTab); // base class has listener methods
			cTable.getColumnModel().getSelectionModel().addListSelectionListener(actionTab);

			// add these actions as focus listeners and disable when crosstable loses focus
			//MTRX_ADJUSTCOL
			cTable.addFocusListener(((AdjustColAction)actionTab.getAction(MTRX_ADJUSTCOL)));
			//MTRX_ADJUSTROW
			cTable.addFocusListener(((AdjustRowAction)actionTab.getAction(MTRX_ADJUSTROW)));
			//MTRX_DELETEROW
			cTable.addFocusListener(((DeleteRowAction)actionTab.getAction(MTRX_DELETEROW)));
			//MTRX_DELETECOL
			cTable.addFocusListener(((DeleteColAction)actionTab.getAction(MTRX_DELETECOL)));
			//MTRX_RESETSEL
			cTable.addFocusListener(((ResetSelectedAction)actionTab.getAction(MTRX_RESETSEL)));
		} else {
			if(cTable.getMatrixGroup()!=mg){
			 	// changing tbl model will call valuechanged in actiontab before rst is set in new model
				cTable.getSelectionModel().removeListSelectionListener(actionTab);
				cTable.getColumnModel().getSelectionModel().removeListSelectionListener(actionTab);

				cTable.updateMatrixGroup(mg, prof, false);
				cTable.refreshHeaderModel(cTable.getTableTitle());

				cTable.getSelectionModel().addListSelectionListener(actionTab);
				cTable.getColumnModel().getSelectionModel().addListSelectionListener(actionTab);
			}
		}
    }

    /**
     * resize the cells and the row header, needed when save or crosstable update is done
     */
    public void resizeCellsAndHeader() {
    	resizeCells();
    	refreshHeaderModel(getTableTitle());
    }
    /**
     * set crosstable based on current selection
     */
    public void setCrossTable(Profile prof){
   		if (!isPivot()) { //not pivot
   			int min = getColumnModel().getSelectionModel().getMinSelectionIndex();
   			if (min != -1){
   				setCrossTable(getSelectedColumn(), prof);
   			} // not pivot
   		}else{
            setCrossTableRow(getSelectedRow(),prof);
   		} // is pivot
    }

    /**
     * check to see if the view column count matches the model column count
     * if not, a column was hidden
     * also a matrixgroup may be hidden in the metacolumnordergroup
     * @return
     */
    public boolean hasHiddenCols(){
    	return hasHiddenMatrixGroups()|| super.hasHiddenCols();
    }
    /**
     * does this table have any matrix groups hidden
     * @return
     */
    private boolean hasHiddenMatrixGroups() {
    	if(actionTab!=null){ // this method is called when model is updated, before actiontab is set
    		MetaColumnOrderGroup mcog = getMetaColumnOrderGroup();
    		if (mcog != null) {
    			if (mcog.hasHiddenItems()) {
    				return true;
    			}
    		}
    	}
        return false;
    }
    /**
     * get MetaColumnOrderGroup
     * called when restoring or saving the column info or enabling the hidden icon
     * @return
     */
    public MetaColumnOrderGroup getMetaColumnOrderGroup(){
    	return actionTab.getMatrixList().getMetaColumnOrderGroup();
    }

    /**
     * update MetaColumnOrderGroup with current column order and visible columns
     * @param _mcog
     */
    public void adjustOrder(MetaColumnOrderGroup _mcog) {
    	TableColumnModel tcm = getColumnModel();

    	int ii = tcm.getColumnCount();
    	HashMap<String, String> map = new HashMap<String, String>();
    	String attCode = null;
    	for (int i = 1; i < ii; ++i) { // col0 is info
    		TableColumn tc = tcm.getColumn(i);
    		if (tc != null) {
    			attCode = ((RSTTableModel)getModel()).getColumnKey(this.convertColumnIndexToModel(i));
    			if (attCode != null) {
    				MetaColumnOrderItem item = _mcog.getMetaColumnOrderItem(attCode);
    				if (item == null) {
    					String key = attCode+":C";
    					item = _mcog.getMetaColumnOrderItem(key);
    					if (item == null) {
    						key = attCode+":R";
    						item = _mcog.getMetaColumnOrderItem(key);
    					}
    				}
    				if (item != null) {
    					item.setVisible(true);
    					item.setColumnOrder(i-1); // col0 is info
    					map.put(attCode, attCode);
    				}
    			}
    		}
    	}

    	int xx = getModel().getColumnCount();
    	for (int x = 1; x < xx; ++x) {
    		attCode = ((RSTTableModel)getModel()).getColumnKey(x);
    		if (attCode != null && !map.containsKey(attCode)) {
    			MetaColumnOrderItem item = _mcog.getMetaColumnOrderItem(attCode);
    			if (item == null) {
    				String key = attCode+":C";
    				item = _mcog.getMetaColumnOrderItem(key);
    				if (item == null) {
    					key = attCode+":R";
    					item = _mcog.getMetaColumnOrderItem(key);
    				}
    			}
    			if (item != null) {
    				item.setVisible(false);
    				item.setColumnOrder(ii-1); // col0 is info
    				++ii;
    			}
    		}
    	}
    	map.clear();
    	map = null;
    }

    /**
     * revertOrder
     */
    public void revertOrder() {

        this.getRSTable().refresh();
        createDefaultColumnsFromModel();
        resizeCells();
        if (this.getRowCount()>0 && this.getColumnCount()>0) {
            setRowSelectionInterval(0, 0);
            setColumnSelectionInterval(0, 0);
        }
        revalidate();
    }

    /*
    accessibility
    */

   /* (non-Javadoc)
    * @see com.ibm.eacm.table.BaseTable#getContext()
    */
   protected String getContext() {
       return ".mtrx";
   }

   /* (non-Javadoc)
    * @see com.ibm.eacm.table.BaseTable#getAccessibleRowNameAt(int)
    */
   public String getAccessibleRowNameAt(int mdlrow) {
    	int headerColid = getRowHeaderColumnId();
   	if (headerColid != -1) {
   		Object o = getModel().getValueAt(mdlrow, headerColid);
   		if (o != null) {
   			return o.toString();
   		}
   	}
       return super.getAccessibleRowNameAt(mdlrow);
   }

   //============================================================
   /**
    * this listens to column selection chgs and keys
    * if tabbing is done, prevent from going into col0
    */
   private class Col0Listener extends KeyAdapter implements ListSelectionListener {
	   boolean isShiftDown = false;
	  // boolean isrepaintneeded = false;
	   public void keyPressed(KeyEvent e) {
		   if(e.getKeyCode()== KeyEvent.VK_TAB && e.isShiftDown()){
			   isShiftDown = true;
			}
		}

		public void keyReleased(KeyEvent e) {
			isShiftDown = false;
			if(e.getKeyCode()== KeyEvent.VK_TAB ||
					e.getKeyCode()== KeyEvent.VK_RIGHT ||
					e.getKeyCode()== KeyEvent.VK_LEFT){
				// jcombobox doesnt select properly when tab or arrow is used
				// still ugly but clears up
				repaint();
			}
		}

		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				// dont allow selecting col0, it has width=0 with info only
				if(getColumnModel().getSelectionModel().getMinSelectionIndex()==0 &&
						getColumnModel().getColumnCount()>1){
					int maxSel = getColumnModel().getSelectionModel().getMaxSelectionIndex();
					if(isShiftDown){ // wrap it
						// must do this later, this method is called from the column selection model
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								int selrow = getSelectionModel().getMinSelectionIndex();
								if(selrow==0){
									selrow = getRowCount()-1;
								}else{
									selrow--;
								}

								setRowSelectionInterval(selrow, selrow);
								setColumnSelectionInterval(getColumnCount()-1, getColumnCount()-1);
								scrollToRow(selrow);
							}
						});
					}else {
						setColumnSelectionInterval(1, maxSel);
					}
				}
			}
		}
   }
   //============================================================
   private class MtrxTableColumnModel extends DefaultTableColumnModel{
	   private static final long serialVersionUID = 1L;

	   public void moveColumn(int columnIndex, int newIndex) {
		   if(columnIndex!=0 && newIndex!=0){
			   super.moveColumn(columnIndex, newIndex);
		   }
	   }
   }
}
