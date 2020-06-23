//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;


import java.awt.Dimension;
import java.awt.FontMetrics;


import com.ibm.eacm.editor.SimpleMaxTextEditor;
import com.ibm.eacm.editor.SimpleTextCellEditor;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.rend.LabelRenderer;

import COM.ibm.eannounce.objects.*;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

/**
 *  this represents MetaFlagMaintList as a table
 * @author Wendy Stimpson
 */
// $Log: MaintTable.java,v $
// Revision 1.5  2014/02/10 21:31:34  wendy
// prevent null ptr if deref has run before done()
//
// Revision 1.4  2013/09/17 20:47:01  wendy
// make sure editor has focus
//
// Revision 1.3  2013/07/31 17:10:29  wendy
// make sure rowsorter is not null before using it
//
// Revision 1.2  2013/07/29 18:38:58  wendy
// Turn off autosort, update classifications after edit and resize cells
//
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class MaintTable extends HorzTable {
	private static final long serialVersionUID = 1L;

	private MetaFlagMaintList maintList = null;

    private LabelRenderer foundRenderer = null;
	private LabelRenderer lockedFoundRend = null;
	private LabelRenderer lockedRend = null;

	private SimpleTextCellEditor text;
	private SimpleMaxTextEditor maxText; // RQ110306297
	private DocumentListener docListener;
	private boolean isDataEditable = false; // is the rowselectabletable editable? and profile current


    /**
     * hang onto the document listener, add it after attribute is set
     * it is the CommitAction
     * @param dl
     */
    public void addDocumentListener(DocumentListener dl){
    	docListener = dl;
    }

    public void removeEditor() {
    	super.removeEditor();
    	if(docListener != null){
    		// hack to tell commitaction to check fields
    		docListener.changedUpdate(null);
    	}
    }
    /**
     * @param viewRowIndex
     * @return
     */
    public boolean isNew(int viewRowIndex){
    	int mdlrow = this.convertRowIndexToModel(viewRowIndex);
    	MetaFlagMaintItem mfi = maintList.getMetaFlagMaintItem(mdlrow);
    	if(mfi==null){
    		return false; // should not happen
    	}
    	return mfi.isNew();
    }
    /**
     * @param mflist
     */
    public MaintTable(MetaFlagMaintList mflist) {
        super(mflist.getTable(), mflist.getProfile());
        maintList = mflist;

        init();

    	isDataEditable = Utils.isEditable(maintList.getTable(),getProfile());
    }
	/*
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#isReplaceable()
	 */
	public boolean isReplaceable() {
		return isDataEditable;
	}

	/* (non-Javadoc)
	 * no locks on this table
	 * @see com.ibm.eacm.table.RSTTable#getCellLock(int, int)
	 */
	public boolean getCellLock(int viewRowid, int viewColid) {
		return true;
	}
    /**
     * get the meta flag key used for this maintlist
     * @return
     */
    public String getMetaFlagKey(){
    	return maintList.getMetaFlag().getKey();
    }
	/**
	 * do any uncommitted changes exist
	 * @return
	 */
	public boolean hasChanges() {
		boolean editchgs = false;
		if(isEditing()){
			Object editobj = getCellEditor().getCellEditorValue();
			Object orig = getValueAt(this.getSelectedRow(),this.getSelectedColumn());
			if(editobj!=null && orig!=null){
				editchgs = !editobj.toString().equals(orig.toString());
			}else{
				editchgs = editobj != orig;
			}
		}
		
		// look for chgs 'put' into the rst
		return editchgs || ((RSTTableModel)getModel()).hasChanges();
	}
    public void updateList(MetaFlagMaintList mflist){
    	cancelCurrentEdit();
    	//maintList.dereference();
        maintList = mflist;
    	isDataEditable = Utils.isEditable(maintList.getTable(),getProfile());
    	updateModel(mflist.getTable(), mflist.getProfile());
        // is the viewport size less than the preferred size
        Dimension vd = this.getPreferredScrollableViewportSize();
        Dimension pd = this.getPreferredSize();
        if(vd.width>pd.width){
        	setPreferredScrollableViewportSize(new Dimension(pd.width,vd.height));
        }
    }

    /**
     * init
     */
    protected void init() {
    	super.init();

    	setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	if(((TableRowSorter<?>)getRowSorter())!=null){
    		((TableRowSorter<?>)getRowSorter()).setSortsOnUpdates(false);
    	}

        // must set this after changing selection mode
        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
        }
        if (getColumnCount() > 0) {
            setColumnSelectionInterval(0, 0);
        }

        foundRenderer = new LabelRenderer();
        foundRenderer.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);

		lockedRend = new LabelRenderer();
		lockedRend.setBorderKeys(SELECTED_BORDER_KEY, SELECTED_BORDER_KEY);
		lockedRend.setColorKeys(null, PREF_COLOR_LOCK);

		lockedFoundRend = new LabelRenderer();
		lockedFoundRend.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
		lockedFoundRend.setBorderKeys(SELECTED_BORDER_KEY, SELECTED_BORDER_KEY);
		lockedFoundRend.setColorKeys(null, PREF_COLOR_LOCK);

    }
	/**
	 * get the accessibility resource key
	 * @return
	 */
	protected String getAccessibilityKey() {
		return "accessible.maintTbl";
	}
    /* (non-Javadoc)
     * release memory
     * @see com.ibm.eacm.table.RSTTable2#dereference()
     */
    public void dereference(){
    	super.dereference();

    	foundRenderer.dereference();
    	foundRenderer=null;

    	lockedFoundRend.dereference();
    	lockedFoundRend = null;

    	lockedRend.dereference();
    	lockedRend = null;


  	 	maintList.dereference();
    	maintList = null;
    	if(maxText !=null){
    		maxText.dereference();
    		maxText = null;
    	}
     	if(text !=null){
    		text.dereference();
    		text = null;
    	}
    	docListener = null;
    }

    /**
     * getCellRenderer
     *
     * @param r
     * @param c
     * @return
     */
    public TableCellRenderer getCellRenderer(int r, int c) {
    	boolean isFound = isFound(r, c);

    	boolean isEditable = isDataEditable && getModel().isCellEditable(convertRowIndexToModel(r),
    			convertColumnIndexToModel(c));
		if (isEditable) {
			if (isFound) {
				return lockedFoundRend;
			}

			return lockedRend;
		}
        if (isFound) {
            return foundRenderer;
        }
        return super.getCellRenderer(r, c);
    }

    /* (non-Javadoc)
     * @see javax.swing.JTable#getCellEditor(int, int)
     */
    public TableCellEditor getCellEditor(int r, int c) {
		TableCellEditor tce = null;
		Object o =this.getEANObject(r, c);
	    if (o instanceof SimpleTextAttribute) {
	    	final java.awt.Component editor;
			if (((SimpleTextAttribute)o).getKey().equals(MetaFlagMaintItem.LONGDESCRIPTION)){ // RQ110306297
				if(maxText==null){
					maxText = new SimpleMaxTextEditor(docListener);
				}

				MetaMaintActionItem mmai = maintList.getParentActionItem();
				maxText.setTextAttribute((SimpleTextAttribute)o);
				maxText.setMaxLen(mmai.getMaxLength(maintList.getMetaFlag()));
				maxText.setMetaAttr(maintList.getMetaFlag());
				tce = maxText;
				editor =maxText.getComponent();
			}else{
				if(text==null){
					text = new SimpleTextCellEditor(docListener);
				}
				text.setTextAttribute((SimpleTextAttribute)o);
				tce = text;
				editor = text.getComponent();
			}
			//make sure editor has focus -sometimes it doesnt if user just types a key
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					editor.requestFocusInWindow();
				}
			});
		}
		return tce;
	}

    /**
     * called to roll back single
     */
    public void rollbackSingle() {
    	int viewrow = getSelectedRow();
    	int viewColumnIndex = getSelectedColumn();

    	if (isValidCell(viewrow, viewColumnIndex)) {
    		((RSTTableModel)getModel()).rollback(convertRowIndexToModel(viewrow),
    				this.convertColumnIndexToModel(viewColumnIndex));
    		((RSTTableModel)getModel()).updatedModel();

    		// restore selection
    		setRowSelectionInterval(viewrow, viewrow);
    		setColumnSelectionInterval(viewColumnIndex,viewColumnIndex);

    		resizeCells(); // cell dimensions may have changed
    	}
    }
    /**
     * called to roll back all
     */
    public void rollback() {
    	((RSTTableModel)getModel()).rollback();

    	resizeCells(); // cell dimensions may have changed
    }

    /**
     * add a new row
     */
    public void addRow() {
		int iLastRow = getRowCount();
		((RSTTableModel)getModel()).addRow();
		if (getColumnCount() == 0) {
			createDefaultColumnsFromModel();
		}
		refresh();
		resizeCells();
		if (iLastRow < getRowCount()) {
			int viewRow = this.convertRowIndexToView(iLastRow);
			setRowSelectionInterval(viewRow,viewRow);
			setColumnSelectionInterval(0,0);
			scrollToRow(viewRow);
		}
	}
    /**
     * get the width needed for this object
     * @param fm
     * @param o
     * @return
     */
    protected int getColWidth(FontMetrics fm, Object o) {
        int w = MIN_COL_WIDTH;
        if(this.getColumnCount()==2){
        	w = MIN_COL_WIDTH*10; // force this to be wide
        }
        if (o != null) {
             w = Math.max(w,fm.stringWidth(o.toString()));
        }
        return w;
    }

    /**
     * getUIPrefKey
     *
     * @return
     */
    public String getUIPrefKey() {
        return "HORZ_MAINT";
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getAccessibleRowNameAt(int)
     */
    protected String getAccessibleRowNameAt(int row) {
    	EANFoundation o =  ((RSTTableModel)getModel()).getRowEAN(row);
		if (o instanceof EANFoundation) {
			return ((EANFoundation)o).getKey();
		}
		return super.getAccessibleRowNameAt(row);
	}
}
