//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.table;


import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.IntSpinnerCellEditor;
import com.ibm.eacm.mtrx.MatrixActionBase;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.rend.LabelRenderer;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.table.*;


/**
 * matrix cross table where number of relators can be specified in the cell (no RELATTR)
 * SG	Action/Attribute	MTRMODEL	TYPE	EntityType	MODEL
 * SG	Action/Attribute	MTRMODEL	ENTITYTYPE	Link	MODEL
 * SG	Action/Attribute	MTRMODEL	TYPE	NavAction	NAVMTRMODEL
 * @author Wendy Stimpson
 */
// $Log: CrossTable.java,v $
// Revision 1.4  2013/10/29 21:15:15  wendy
// IN4468974 Can not save data when creating new product structure using Manage Product Structure
//
// Revision 1.3  2013/07/29 18:38:57  wendy
// Turn off autosort, update classifications after edit and resize cells
//
// Revision 1.2  2013/07/18 18:57:40  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class CrossTable extends HorzTable implements ActionListener, DocumentListener
{
	private static final long serialVersionUID = 1L;
	private LabelRenderer foundRend = null;
	private LabelRenderer mtrxRend = null;
    private LabelRenderer cRend = null;
    private LabelRenderer lockedRenderer = null;
    private LabelRenderer lockedFoundRenderer = null;

    private IntSpinnerCellEditor numRelEditor = null;
    private Col0Listener col0listener;

    private MatrixGroup mg = null;
    // matrixgroup could change, keep fg and sortkeys by mg.key
    private Hashtable<String, FilterGroup> prevFgTbl = new Hashtable<String, FilterGroup>();
    private Hashtable<String, List<RowSorter.SortKey>> prevSortTbl = new Hashtable<String, List<RowSorter.SortKey>>();//sort keys

    private boolean bReport = true;
    private MatrixActionBase actionTab = null;
    private MtrxTable mTable = null;
    protected boolean isDataEditable = false; // is the rowselectabletable editable? and profile current

    private Vector<EntityGroup> entityGroup2DerefVct = null;

    /**
     * basic crossTable - relators do not have attributes
     */
    public CrossTable(MatrixGroup mg, MatrixActionBase matab) {
        super(mg.getTable(), matab.getProfile());

        isDataEditable = Utils.isEditable(mg.getTable(),getProfile());

        this.mg = mg;
        actionTab = matab;
        init();

        //will stop tab from going into col0
        col0listener = new Col0Listener();
        getColumnModel().getSelectionModel().addListSelectionListener(col0listener);
        addKeyListener(col0listener);
    }

    /* (non-Javadoc)
     * use derived column model to prevent moving col0
     * @see javax.swing.JTable#createDefaultColumnModel()
     */
    protected TableColumnModel createDefaultColumnModel() {
        return new MtrxTableColumnModel();
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
			String text = ((MtrxTableModel)getModel()).getTruncName(o.toString());
			w = fm.stringWidth(text);
		}
		return w;
	}
	/**
	 * allow derived classes to provide a different rsttablemodel
	 * @return
	 */
	protected RSTTableModel createTableModel(){
		return new MtrxTableModel(this,64);
	}

	/* (non-Javadoc)
	 * dont select col0 info
	 * @see javax.swing.JTable#setColumnSelectionInterval(int, int)
	 */
	public void setColumnSelectionInterval(int index0, int index1) {
		if (getColumnCount() > 1) {
			if (index0==0){
				index0=1;
			}
			if (index1==0){
				index1=1;
			}
			super.setColumnSelectionInterval(index0, index1);
		}
	}

	/* (non-Javadoc)
	 * matrixgroup needs columns sorted
	 * @see com.ibm.eacm.table.RSTTable2#createDefaultColumnsFromModel()
	 */
	public void createDefaultColumnsFromModel() {
		super.createDefaultColumnsFromModel();
		TableColumnModel tcm = getColumnModel();

		// sort the columns
		int cc = tcm.getColumnCount();
		if (cc>2){ // col 0 is row header info, col 1 is data, col 2 is data
			TableColumn[] sortItems = new TableColumn[cc-1];
			for (int c = 1; c < cc; ++c) { //skip col 0
				sortItems[c-1] = tcm.getColumn(c);
			}
			Arrays.sort(sortItems, new java.util.Comparator<TableColumn>(){
				public int compare(TableColumn o1, TableColumn o2) {
					return o1.getHeaderValue().toString().compareToIgnoreCase(o2.getHeaderValue().toString());
				}
			});

			cc--; //skip col 0
			for (int c = 0; c < cc; ++c) {
				tcm.moveColumn(tcm.getColumnIndex(sortItems[c].getIdentifier()),c+1);
				sortItems[c]=null;
			}
			sortItems = null;
		}

		if(cc>0){
			// hide column 0 - it is used for row header and hidden for sorting
			TableColumn tc = tcm.getColumn(0);
			tc.setMinWidth(0);
			tc.setMaxWidth(0);
			tc.setPreferredWidth(0);
			tc.setWidth(0);
		}
	}

    /* (non-Javadoc)
     * get the column name without truncating
     * @see com.ibm.eacm.table.BaseTable#getFullColumnName(int)
     */
    protected String getFullColumnName(int viewColumnIndex){
      	return ((MtrxTableModel)this.getModel()).getFullColumnName(convertColumnIndexToModel(viewColumnIndex));
    }

    /**
     * column 0 has zero width, it holds rowheader information, ignore it
     * @return
     */
    public boolean hasSelectedDataColumns(){
    	boolean hasSelection = false;
    	if(getSelectedRowCount()>0 && getModel().getColumnCount()>1){  // must have rows and columns selected
    		int selArray[] = getSelectedColumns();
    		// seems like bug in base code, after filter runs, even if cols are selected, it is not recognized
    		if (selArray.length==0){
    			hasSelection = true;
    		}
    		for (int i=0; i<selArray.length; i++){
    			if(convertColumnIndexToModel(selArray[i])!=0){
    				hasSelection = true;
    				break;
    			}
    		}
    	}

    	return hasSelection;
    }

    /**
     * do any uncommitted changes exist
     * @return
     */
    public boolean hasChanges() {		
    	return getMatrixGroup().hasChanges();
    }

    /**
     * need reference to the matrixtable when used in matrixactiontab
     * @param mtbl
     */
    protected void setMatrixTable(MtrxTable mtbl){
    	mTable = mtbl;
    }
	/**
	 * update selected columns and all rows with the specified value
	 * @param o
	 */
	public void horizontalAdjust(Object o) {
		int rr = getRowCount();
		int[] cols = getSelectedColumns();
		for (int c = 0; c < cols.length; ++c) {
			for (int r = 0; r < rr; ++r) {
				setValueAt(o, r, cols[c]);
			}
		}
		resizeAndRepaint();
		if (mTable!=null){
			mTable.revalidate();
			mTable.repaint();
		}
	}
	/**
	 * update selected rows and all columns with the specified value
	 * @param o
	 */
	public void verticalAdjust(Object o) {
		int cc = getColumnCount();
		int[] rows = getSelectedRows();
		for (int r = 0; r < rows.length; ++r) {
			for (int c = 0; c < cc; ++c) {
				setValueAt(o, rows[r], c);
			}
		}

		resizeAndRepaint();
		if (mTable!=null){
			mTable.revalidate();
			mTable.repaint();
		}
	}

    /**
     * called when a different matrix group has been selected in the MtrxTable
     * @param mg2
     * @param prof
     * @param requestFocus
     */
    public void updateMatrixGroup(MatrixGroup mg2, Profile prof,boolean requestFocus) {
    	this.mg = mg2;

    	FilterGroup fg = null;
    	// get any filters for the new mg, it may have been loaded before
		if (isPivoted()){
			fg = prevFgTbl.remove("COL"+mg.getKey());
		}else{
			fg = prevFgTbl.remove(mg.getKey());
		}

		List<RowSorter.SortKey> sortKeys = null;
		if (isPivoted()){
			sortKeys = prevSortTbl.get("COL"+mg.getKey());
		}else{
			sortKeys = prevSortTbl.get(mg.getKey());
		}

    	resetFilter();  // reset any filters for this table, they were for the previous matrixgroup

    	updateModel(mg.getTable(),prof,requestFocus);

        isDataEditable = Utils.isEditable(mg.getTable(),getProfile());

    	if(fg!=null){
    		if (isPivoted()){
    			setColFilterGroup(fg);
    			this.filter();
    		}else{
    			setFilterGroup(fg);
    			this.filter();
    		}
    	}
    	if (sortKeys!=null){
			setSortKeys(sortKeys);
		}
    }

	/*
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#isReplaceable()
	 */
	public boolean isReplaceable() {
		return isDataEditable;
	}
    /**
     * @see javax.swing.JTable#getValueAt(int, int)
     */
    public Object getValueAt(int r, int c) {
        return getValueAt(r, c, false);
    }

    /**
     * init
     */
    protected void init() {
    	super.init();

      	setSurrendersFocusOnKeystroke(true); // for accessibility, let user edit using keystrokes

        cRend = new LabelRenderer();

        foundRend = new LabelRenderer();
        foundRend.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);

        lockedRenderer = new LabelRenderer();
        lockedRenderer.setBorderKeys(SELECTED_BORDER_KEY, SELECTED_BORDER_KEY);
        lockedRenderer.setColorKeys(null, PREF_COLOR_LOCK);

        lockedFoundRenderer = new LabelRenderer();
        lockedFoundRenderer.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
        lockedFoundRenderer.setColorKeys(null, PREF_COLOR_LOCK);

    	mtrxRend = new LabelRenderer();
    	mtrxRend.setFocusBorderKeys(RAISED_BORDER_KEY, RAISED_BORDER_KEY);
    	mtrxRend.setSelectionColorKeys("Label.foreground", ROW_REND_COLOR);
    	mtrxRend.setColorKeys(null, ROW_REND_COLOR);
    	mtrxRend.setFocusable(false); // prevent tabbing to this - didnt' work

        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        setCellSelectionEnabled(true);

        setDefaultRenderer(Object.class, cRend);
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
    	return "accessible.crssTable";
    }
    /**
     * this pivots the rows and the columns
     */
    public void pivot(){
    	// changing tbl model will call valuechanged in actiontab before rst is set in new model
    	getSelectionModel().removeListSelectionListener(actionTab);
        getColumnModel().getSelectionModel().removeListSelectionListener(actionTab);
	    getColumnModel().getSelectionModel().removeListSelectionListener(col0listener);

    	cancelCurrentEdit();

    	clearSelection();
    	MtrxTableModel mtm = (MtrxTableModel)getModel();
    	if (mtm instanceof PivotMtrxTableModel){
    		if(isFiltered()){
    			prevFgTbl.put("COL"+mg.getKey(), getColFilterGroup());
    		}
    		setRSTModel(new MtrxTableModel(this,64),actionTab.getProfile());
    	}else{
    		if(isFiltered()){
    			prevFgTbl.put(mg.getKey(), getFilterGroup());
    		}

    		setRSTModel(new PivotMtrxTableModel(this,64),actionTab.getProfile());
    	}

    	getSelectionModel().addListSelectionListener(actionTab);
	    getColumnModel().getSelectionModel().addListSelectionListener(actionTab);
	    getColumnModel().getSelectionModel().addListSelectionListener(col0listener);

       	refreshHeaderModel(getTableTitle());

		//mtm.dereference(); - this messes with the rst
    	mtm.derefListeners();

        if (getColumnCount() > 1) {
            setColumnSelectionInterval(1, 1);
        }

        // update actions based on pivoted table - removed listener when model was swapped
        actionTab.refreshActions();
    }

    /* (non-Javadoc)
     * hang onto filtergroup if matrixgroup is changed
     * @see com.ibm.eacm.table.BaseTable#filter()
     */
    public void filter() {
    	super.filter();
 		if(isFiltered()){
 			if(isPivoted()){
 				prevFgTbl.put("COL"+mg.getKey(), getColFilterGroup());
 			}else{
 				prevFgTbl.put(mg.getKey(), getFilterGroup());
 			}
		}
    }
    /* (non-Javadoc)
     * clear any saved filtergroup
     * @see com.ibm.eacm.table.BaseTable#resetFilter()
     */
    public void resetFilter() {
    	super.resetFilter();
    	if(isPivoted()){
    		prevFgTbl.remove("COL"+mg.getKey());
    	}else{
    		prevFgTbl.remove(mg.getKey());
    	}
    }
	/* (non-Javadoc)
	 * one of the columns was sorted, update the list
	 * @see javax.swing.event.RowSorterListener#sorterChanged(javax.swing.event.RowSorterEvent)
	 */
    public void sorterChanged(RowSorterEvent e) {
    	super.sorterChanged(e);
    	if (mg!=null && e.getType() == RowSorterEvent.Type.SORTED) {
    		//must make copy
    		List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
    		for (int i=0; i<getRowSorter().getSortKeys().size(); i++){
    			RowSorter.SortKey skey = getRowSorter().getSortKeys().get(i);
    			sortKeys.add(new RowSorter.SortKey(skey.getColumn(), skey.getSortOrder()));
    		}
    		if (isPivoted()){
    			prevSortTbl.put("COL"+mg.getKey(), sortKeys);
    		}else{
    			prevSortTbl.put(mg.getKey(), sortKeys);
    		}
    	}
	}

    /* (non-Javadoc)
     * used for Filterable in FilterDialog
     * @see com.ibm.eacm.table.BaseTable#isPivoted()
     */
    public boolean isPivoted()  {return getModel() instanceof PivotMtrxTableModel;}

	/* (non-Javadoc)
	 * called when rsttable is updated in the tablemodel
	 * @see com.ibm.eacm.table.RSTTable2#setSortKeys()
	 */
	protected void setSortKeys(){
     	List<RowSorter.SortKey> sortKeys = null;

    	if (mg!=null){
    		if (isPivoted()){
    			sortKeys = prevSortTbl.get("COL"+mg.getKey());
    			FilterGroup fg = prevFgTbl.remove("COL"+mg.getKey());
    			if(fg!=null){
    				setColFilterGroup(fg);
    				this.filter();
    			}
    		}else{
    			sortKeys = prevSortTbl.get(mg.getKey());
    			FilterGroup fg = prevFgTbl.remove(mg.getKey());
    			if(fg!=null){
    				setFilterGroup(fg);
    				this.filter();
    			}
    		}
    	}

		if (sortKeys!=null){
			setSortKeys(sortKeys);
		}else{
			super.setSortKeys();
		}
	}

    /**
     * this listens to column selection chgs and keys
     * if tabbing is done, prevent from going into col0
     */
    private class Col0Listener extends KeyAdapter implements ListSelectionListener {
    	boolean isShiftDown = false;
    	//boolean isrepaintneeded = false;
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

    /**
     * dereference
     */
    public void dereference() {
        super.dereference();

        mTable = null;

        getColumnModel().getSelectionModel().removeListSelectionListener(col0listener);
        removeKeyListener(col0listener);
        col0listener = null;

        foundRend.dereference();
        foundRend = null;

        cRend.dereference();
        cRend = null;

        lockedRenderer.dereference();
        lockedRenderer = null;

        lockedFoundRenderer.dereference();
        lockedFoundRenderer = null;

        mtrxRend.dereference();
        mtrxRend = null;

        if (numRelEditor != null) {
            numRelEditor.dereference();
            numRelEditor = null;
        }

        mg = null;

        prevFgTbl.clear();
        prevFgTbl = null;

        prevSortTbl.clear();
        prevSortTbl = null;

        actionTab = null;

        if(entityGroup2DerefVct!=null){
        	for (int i=0; i<entityGroup2DerefVct.size(); i++){
        		EntityGroup eg = entityGroup2DerefVct.elementAt(i);
        		eg.dereference();
        	}
        	entityGroup2DerefVct.clear();
        	entityGroup2DerefVct = null;
        }

        removeAll();
        setUI(null);
    }

    /**
     * rollbackMatrix
     * called when user wants to cancel all changes
     */
    public void rollbackMatrix() {
		cancelCurrentEdit();
        ((MtrxTableModel)getModel()).rollbackMatrix();
       	// only different values will actually fire an event - make sure actions listening
		// reflect the correct state
		firePropertyChange(DATACHANGE_PROPERTY, true, false);
        resizeCells();
    }

	/* (non-Javadoc)
	 * used for unlock from lockactiontab, need the EANFoundation to rollback if unlocked manually
	 * @see com.ibm.eacm.table.RSTTable2#getCellEANFoundation(int, int)
	 */
	protected EANFoundation getCellEANFoundation(int mdlRow, int mdlCol){
     	EANFoundation colFoundation =((MtrxTableModel)getModel()).getColumnEAN(mdlCol);
    	EANFoundation rowFoundation = ((MtrxTableModel)getModel()).getRowEAN(mdlRow);

    	String rowKey = null;
    	String colKey = null;

    	if (colFoundation instanceof EntityItem){
    		colKey = colFoundation.getKey();
    	}

    	if (rowFoundation instanceof EntityItem){
    		rowKey = rowFoundation.getKey();
    	}
    	if (colKey != null){
    		for (int i=0; i<mg.getMatrixItemCount(); i++){
    			MatrixItem mi = mg.getMatrixItem(i);
    			String childKey = mi.getChildEntity().getKey();
    			String parentKey = mi.getParentEntity().getKey();
    			if((colKey.equals(parentKey) && rowKey.equals(childKey))||
    					(rowKey.equals(parentKey) && colKey.equals(childKey)))
    			{
    				return mi;
    			}
    		}
    	}

    	return null;
	}

	/**
	 * cell only has number of relators in it, there is no help text
	 * @return
	 */
	public String getHelpText() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.RSTTable#generateFoundKey(int, int, boolean)
	 */
	protected String generateFoundKey(int viewRow, int viewCol, boolean rowOnly) {
		return getInformation(viewRow,viewCol).replace('\n', ' ');
	}
    /**
     * getInformation
     *
     * @return
     */
    public String getInformation() {
    	int r = getSelectedRow();
    	int c = getSelectedColumn();
    	return getInformation(r,c);
    }

    /**
     * @param r
     * @param c
     * @return
     */
    private String getInformation(int r, int c) {
    	/* relattr table will have an attribute, this wont
    	Object obj = getValueAt(r, c);
    	if (obj instanceof EANAttribute){
    		return Routines.getInformation((EANAttribute)obj);
    	}*/
    	EANFoundation colFoundation =((MtrxTableModel)getModel()).getColumnEAN(convertColumnIndexToModel(c));
    	EANFoundation rowFoundation = ((MtrxTableModel)getModel()).getRowEAN(convertRowIndexToModel(r));

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
    		sb.append("Entity: "+rowKey+NEWLINE);
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
    					sb.append("Relators: None"+NEWLINE);
    				}else{
    					sb.append("Relators: "+NEWLINE);
    					for (int rl=0; rl<mi.getRelatorList().size(); rl++){
    						EntityItem ei = (EntityItem) mi.getRelatorList().getAt(rl);
    						sb.append("     "+ei.getKey()+NEWLINE);
    					}
    				}
    				break;
    			}
    		}
    		sb.append("Entity: "+colKey+NEWLINE);
    	}

    	if(sb.length()>0){
    		return sb.toString();
    	}

    	return Utils.getResource("nia");
    }

    /**
     * getUIPrefKey
     *
     * @return
     */
    public String getUIPrefKey() {
    	if(isPivoted()){
    		return "CROSS_PIVOT"+actionTab.getKey()+mg.getKey();
    	}
        return "CROSS"+actionTab.getKey()+mg.getKey();
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.RSTTable#replaceAllValue(java.lang.String, java.lang.String, boolean, boolean, boolean)
     */
    public void replaceAllValue(String findValue, String replace, boolean isMulti, boolean useCase, boolean isDown) {
        bReport = false;
        if (Routines.isInt(replace)) {
            super.replaceAllValue(findValue, replace,isMulti, useCase, isDown);
        } else {
        	//msg11024.1 = Unable to replace {0} with {1}.
        	com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg11024.1",findValue,replace));
        }
        bReport = true;
    }

	/* (non-Javadoc)
	 * no locks needed for this when used in matrixaction - used when find/replace is done
	 * @see com.ibm.eacm.table.RSTTable#getCellLock(int, int)
	 */
	public boolean getCellLock(int viewRowid, int viewColid) {
		if(mTable!=null){
			return true;
		}else{
			return super.getCellLock(viewRowid, viewColid);
		}
	}
    /**
     * replaceString
     * @param old
     * @param bnew
     * @param r
     * @param c
     */
    protected void replaceString(String old, String bnew, int r, int c) {
        Object o = null;
        if (bReport) {
            if (!Routines.isInt(bnew)) {
            	//msg11024.1 = Unable to replace {0} with {1}.
            	com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg11024.1",old,bnew));
                return;
            }
        }
        o = getValueAt(r, c);
        if (o != null) {
            String newStr = Routines.replace(o.toString(), old, bnew);
            int i = Routines.toInt(newStr);
            if (i >= 0 && i <= 99) {
                setValueAt(newStr, r, c);
            }
        }
        repaint();
    }

    /**
     * addColumn
     * @param ei
     */
    public void addColumn(EntityItem[] ei) {
        int cStart = ((MtrxTableModel)getModel()).getColumnCount();
        // add the entities to the rst
        ((MtrxTableModel)getModel()).addColumn(ei);
        // get new count
        int cc = ((MtrxTableModel)getModel()).getColumnCount();
		FontMetrics fm = getFontMetrics(getFont());
		// add each column to the tablecolumnmodel
        for (int c = cStart; c < cc; ++c) {
            TableColumn newColumn = new TableColumn(c);
            newColumn.setIdentifier(((MtrxTableModel)getModel()).getColumnKey(c));
            addColumn(newColumn);

            int width = getHeaderWidth(fm,newColumn);
            newColumn.setWidth(width);
            newColumn.setMinWidth(width);
            newColumn.setPreferredWidth(width);
        }

        // add header info for the new column(s)
     	refreshHeaderModel(getTableTitle());

        ((MtrxTableModel)getModel()).updatedModel();

        resizeAndRepaint();

        if(!isPivoted()){
        	scrollRectToVisible(getCellRect(0, cStart, true));
        }
        // these will need to be dereferenced later
        if(entityGroup2DerefVct==null){
        	entityGroup2DerefVct = new Vector<EntityGroup>();
        }
        entityGroup2DerefVct.add(ei[0].getEntityGroup()); // all items share one group
    }

    /**
     * getCellEditor
     *
     * @param r
     * @param c
     * @return
     */
    public TableCellEditor getCellEditor(int r, int c) {
        if (numRelEditor==null){
        	// this is the number of relators, not any attribute
        	numRelEditor = new IntSpinnerCellEditor(1,0,99,1);
        }

        numRelEditor.setTable(this, r, c); // support delete key

        return numRelEditor;
    }

    /**
     * set number of relators in this cell to 0
     * called by ResetSelectedAction
     */
    public void resetSelected() {
        int rows[] = getSelectedRows();
        int cols[] = getSelectedColumns();
        for (int r = 0; r < rows.length; ++r) {
            for (int c = 0; c < cols.length; ++c) {
                setValueAt("0", convertRowIndexToModel(rows[r]), convertColumnIndexToModel(cols[c]));
            }
        }
    }
    /* (non-Javadoc)
     * fire property chg when updated, save and cancel matrix action is a listener
     * @see javax.swing.JTable#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object aValue, int row, int column) {
    	if (!this.validCellChange(aValue, row, column)){
    		return;
    	}
        super.setValueAt(aValue, row, column);
        if(mTable!=null){
        	// crosstable is part of matrixtab, update the sizes of the cells
        	mTable.resizeCellsAndHeader();
        }
    }
    /**
     * "0" is used to clear all cells, dont set it
     * @param aValue
     * @param row
     * @param column
     * @return
     */
    protected boolean validCellChange(Object aValue, int row, int column) {
    	Object oldValue = getValueAt(row, column); // the object doesnt change, only its string representation
    	if (oldValue!=null){
    		if (oldValue.toString().trim().length()==0 && "0".equals(aValue)){ // aValue is null, nothing set now so ignore it
    			return false;
    		}
    	}
    	return true;
    }

    /**
     * getMatrixGroup
     * @return
     */
    public MatrixGroup getMatrixGroup() {
        return mg;
    }

    /**
     * called by delete col/row actions to determine enabled
     * called by adjust col/row actions to determine dialog prompt
     * showRelAttr
     * @return
     */
    public boolean showRelAttr() {
        if (mg != null) {
            return mg.showRelAttr();
        }
        return false;
    }

    /**
     * isReplaceableAttribute
     *
     * @param r
     * @param c
     * @return
     */
    protected boolean isReplaceableAttribute(int r, int c) {
    	if (c==0){
    		return false;
    	}
        return !showRelAttr();
    }

    /*
     accessibility
     */

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getContext()
     */
    protected String getContext() {
        return ".ctab";
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

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#isAccessibleCellEditable(int, int)
     */
    public boolean isAccessibleCellEditable(int row, int col) {
        return true;
    }

    /* (non-Javadoc)
     * @see javax.swing.JTable#getCellRenderer(int, int)
     */
    public TableCellRenderer getCellRenderer(int r, int c) {
    	if (convertColumnIndexToModel(c)==0){
    		return mtrxRend;
    	}
    	boolean isLocked = isCellLocked(r, c);
    	boolean isFound = isFound(r, c);

        if (isLocked){
        	 if (isFound) {
                 return lockedFoundRenderer;
             }
        	return lockedRenderer;
        }
        if (isFound) {
            return foundRend;
        }

        return cRend;
    }

    /* (non-Javadoc)
     * must override because the rowindex and colindex must be converted here and swapped
     * @see com.ibm.eacm.table.RSTTable2#hasLock(int, int)
     */
    public boolean isCellLocked(int rViewId, int cViewId) {
    	if(this.isPivoted()){
    		int row = convertRowIndexToModel(rViewId);
    		int col = convertColumnIndexToModel(cViewId) - 1; // col 0 is row header info
    		RSTTableModel rstm = (RSTTableModel)getModel();
    		return rstm.isCellLocked(rstm.getRowIndex(rstm.getRowKey(col)),
    				row+1,getProfile());  // 1 is subtracted in MtrxTableModel.hasLock()
    	}

        return super.isCellLocked(rViewId, cViewId);
    }

    /* (non-Javadoc)
     * prevent selection of info col0
     * @see javax.swing.JTable#selectAll()
     */
    public void selectAll() {
    	if (isEditing()) {
    		removeEditor();
    	}
    	if (getRowCount() > 0 && getColumnCount() > 1) {
    		setRowSelectionInterval(0, getRowCount()-1);
    		setColumnSelectionInterval(1, getColumnCount()-1);
    	}
    }

    /**
     * getFillComponent used in dialog as editor
     * only relattr table will have a value for this
     * @param r
     * @param c
     * @return
     */
    public AttrCellEditor getFillComponent(int r, int c) {
        return null;
    }

	/**
	 * used when text document changes to enable actions
	 * @param e
	 */
	public void changedUpdate(DocumentEvent e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, true, false);
			}
		});
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, true, false);
			}
		});
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, true, false);
			}
		});
	}
	/* (non-Javadoc)
	 * used when flag editor changes IN4468974
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		stopCellEditing(); // force flag changes to be recognized, update any other attrs
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, true, false);
			}
		});
	}

    //============================================================
    private class MtrxTableColumnModel extends DefaultTableColumnModel{
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * prevent moving of col0 info
		 * @see javax.swing.table.DefaultTableColumnModel#moveColumn(int, int)
		 */
		public void moveColumn(int columnIndex, int newIndex) {
        	if(columnIndex!=0 && newIndex!=0){
        		super.moveColumn(columnIndex, newIndex);
        	}
        }
    }
}
