//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;


import java.awt.FontMetrics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ibm.eacm.EACM;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.rend.LabelRenderer;
import com.ibm.eacm.rend.LongRenderer;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.UIManager;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.edit.EditController;
import com.ibm.eacm.edit.SpellCheckHandler;

import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.BlobCellEditor;
import com.ibm.eacm.editor.DateCellEditor;
import com.ibm.eacm.editor.FlagCellEditor;
import com.ibm.eacm.editor.LongCellEditor;
import com.ibm.eacm.editor.MultiCellEditor;

import com.ibm.eacm.editor.TextCellEditor;
import com.ibm.eacm.editor.TimeCellEditor;
import com.ibm.eacm.editor.XMLCellEditor;


/**
 * this is the table for the vertical editor
 * @author Wendy Stimpson
 */
//$Log: VertTable.java,v $
//Revision 1.7  2013/11/07 18:09:39  wendy
//Add FillCopyEntity action
//
//Revision 1.6  2013/10/09 17:31:41  wendy
//scroll to selected row after deactivate
//
//Revision 1.5  2013/09/19 21:48:33  wendy
//turn off sort on model change
//
//Revision 1.4  2013/07/29 18:38:57  wendy
//Turn off autosort, update classifications after edit and resize cells
//
//Revision 1.3  2013/07/19 17:48:35  wendy
//pass window parent into mf cell editor
//
//Revision 1.2  2013/07/18 18:57:40  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class VertTable extends VerticalTable implements DocumentListener, ActionListener
{
	private static final long serialVersionUID = 1L;

    private EditController editController = null;
    private boolean isDataEditable = false; // is the rowselectabletable editable? and profile current
    private String uiprefkey = null;
    private List<RowFilter<Object,Object>> rowfilters = null;

    private LabelRenderer foundRenderer = null;
    private LabelRenderer lockedFoundRenderer = null;
    private LabelRenderer lockedRenderer = null;

    private LongRenderer longRenderer = null;
    private LongRenderer foundLongRenderer = null;
    private LongRenderer lockedLongRenderer = null;
    private LongRenderer lockedFoundLongRenderer = null;

    private LabelRenderer rowHeaderFoundRenderer = null;

    private BlobCellEditor blobTCE = null;
    private LongCellEditor longTCE = null;
    private XMLCellEditor xmlTCE = null;

	private FillVector vFill = null;

    /**
     * vertTable
     * @param ec2
     * @param ei
     */
    public VertTable(EditController ec2, EntityItem ei) {
    	super(ei.getProfile(),ei.getEntityItemTable(),"VERT");
    	editController=ec2;

    	isDataEditable = Utils.isEditable(getRSTable(),getProfile()); // check for dialed back timestamp and rst can be edited

    	getRSTable().setLongDescription(true);

    	uiprefkey = "VERT"+ei.getEntityType();

    	init();

    	setAutoCreateRowSorter(false); // do not sort this table, column order controls it
    	setRowSorter(null);

    	//must set selection after changing row sorter
    	if (getRowCount() > 0) {
    		setRowSelectionInterval(0, 0);
    	}
    	if (getColumnCount() > 1) {
    		setColumnSelectionInterval(1, 1);
    	}

    	resizeCells(); // do it again, no rowsorter now
    }

    /**
     * update the table model with this entityitem
     * @param ei - EntityItem to get RST from
     */
    public void updateModel(EntityItem ei) {
    	if(ei !=null){
    		RowSelectableTable rst = ei.getEntityItemTable();
    		rst.setLongDescription(true);
    		isDataEditable = Utils.isEditable(rst,getProfile());

    		((RSTTableModel)getModel()).updateModel(rst);

    		// set selection
    		setRowSelectionInterval(0, 0);
    		setColumnSelectionInterval(1, 1);
    	}else{
    		// all rows were deleted, use a copy of the master rst -
    		// it will get dereferenced when verttablemodel is updated
    		isDataEditable = false;
    		RowSelectableTable rst = this.editController.getEntityGroupTable();
    		((RSTTableModel)getModel()).updateModel(rst);
    	}
    }

    /**
     * init
     *
     */
    protected void init() {
        foundRenderer = new LabelRenderer();
        foundRenderer.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);

        rowHeaderFoundRenderer = new LabelRenderer();
        rowHeaderFoundRenderer.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
        rowHeaderFoundRenderer.setColorKeys(null, PREF_COLOR_FOUND);
        rowHeaderFoundRenderer.setSelectionColorKeys(null, PREF_COLOR_FOUND);

        lockedRenderer = new LabelRenderer();
        lockedRenderer.setBorderKeys(SELECTED_BORDER_KEY, SELECTED_BORDER_KEY);
        lockedRenderer.setColorKeys(null, PREF_COLOR_LOCK);

        lockedFoundRenderer = new LabelRenderer();
        lockedFoundRenderer.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
        lockedFoundRenderer.setColorKeys(null, PREF_COLOR_LOCK);

        longRenderer = new LongRenderer();
        foundLongRenderer = new LongRenderer();
        foundLongRenderer.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
        lockedLongRenderer = new LongRenderer();
        lockedLongRenderer.setBorderKeys(SELECTED_BORDER_KEY, SELECTED_BORDER_KEY);
        lockedLongRenderer.setColorKeys(null, PREF_COLOR_LOCK);
        lockedFoundLongRenderer = new LongRenderer();
        lockedFoundLongRenderer.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
        lockedFoundLongRenderer.setColorKeys(null, PREF_COLOR_LOCK);

    	super.init();
    	
		if(getRowSorter()!=null){
			((TableRowSorter<?>)getRowSorter()).setSortsOnUpdates(false);
		}
    }

    /**
     * getCellRenderer
     *
     * @param _r
     * @param _c
     * @return
     */
    public TableCellRenderer getCellRenderer(int _r, int _c) {
        boolean isFound = isFound(_r, _c);
        if (_c == 0) {
            if (isFound) {
                return rowHeaderFoundRenderer;
            }
            return rowHeaderRenderer;
        }

        boolean isLocked = isDataEditable && isCellLocked(_r, _c) &&
        	getModel().isCellEditable(convertRowIndexToModel(_r),convertColumnIndexToModel(_c));

        EANAttribute attr  = this.getAttribute(_r, _c);
        boolean isLong=(attr instanceof LongTextAttribute || attr instanceof XMLAttribute);

        if (isLocked) {
        	if (isFound) {
        		if(isLong){
        			return lockedFoundLongRenderer;
        		}
        		return lockedFoundRenderer;
        	}
        	if(isLong){
    			return lockedLongRenderer;
    		}
        	return lockedRenderer;
        }
        if (isFound) {
        	if(isLong){
    			return foundLongRenderer;
    		}
        	return foundRenderer;
        }

    	if(isLong){
			return longRenderer;
		}

        return getDefaultRenderer(Object.class);
    }

    /* (non-Javadoc)
     * @see javax.swing.JTable#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int column) {
    	boolean editable = isDataEditable; // avoid a per cell check
    	if(editable){
    		editable = getModel().isCellEditable(convertRowIndexToModel(row), convertColumnIndexToModel(column));
    	}

    	//always install an editor for some types so user can view like long, xml and blob
    	if(!editable){
    		EANAttribute attr = getAttribute(row, column);
    		if(attr instanceof BlobAttribute ||
    			attr instanceof LongTextAttribute ||
    			attr instanceof XMLAttribute){
    			editable = true; // must open editor in view only mode
    		}
    	}
    	return editable;
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.RSTTable#isCellPastable(int, int)
     */
    protected boolean isCellPastable(int row, int column) {
    	boolean editable = isDataEditable; // avoid a per cell check
    	if(editable){
    		editable = getModel().isCellEditable(convertRowIndexToModel(row), convertColumnIndexToModel(column));
    	}

    	//can not paste into xml or blob.  xml requires tag validation in its editor and blob loads a binary
    	if(editable){
    		EANAttribute attr = getAttribute(row, column);
    		if(attr instanceof BlobAttribute ||
    			attr instanceof XMLAttribute){
    			editable = false;
    		}
    	}
    	return editable;
    }

    /* (non-Javadoc)
     * provide a way to get celllocks on an attribute that isnt editable but others are
     * @see com.ibm.eacm.table.RSTTable#editCellAt(int, int, java.util.EventObject)
     */
    public boolean editCellAt(int row, int column, EventObject e){
    	if (getCellEditor() != null){
    		// is spellcheck required?
    		if(!stopCellEditing()){
    			return false;
    		}
    	}

    	if (row < 0 || row >= getRowCount()) {
    		return false;
    	}

    	// it is always column 1
    	if(isDataEditable && // table can edit and user prof is current
    			!isCellLocked(row, 1)){ // and dont have lock yet
    		// try to get cell lock if editor will be launched
    		if(AttrCellEditor.canLaunchEditorForEvent(e)){
    			if(getCellLock(row, 1)){
    				repaint();
    			}
    		}
    	}

    	// always install an editor for some types so user can view like long, xml and blob
    	return super.editCellAt(row, 1, e);
    }

	/*
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#isReplaceable()
	 */
	public boolean isReplaceable() {
		return isDataEditable;
	}
    /* (non-Javadoc)
     * @see javax.swing.JTable#getCellEditor(int, int)
     */
    public TableCellEditor getCellEditor(int _r, int _c) {
       	Object o =this.getEANObject(_r, _c);
    	if (o instanceof MultiFlagAttribute) {
    		if (multiFCE==null){
    			// get window parent - focus will go back to it when closed
    			Window parent = EACM.getEACM(); 
    			if(getTopLevelAncestor() instanceof Window){
    				parent = (Window)getTopLevelAncestor();
    			}
    			multiFCE = new MultiCellEditor(parent,false);
    		}
    		multiFCE.setTable(this, _r, _c); // needed to handle delete key
    		return multiFCE;
    	}
    	if (o instanceof EANFlagAttribute) {
    		if (flagFCE==null){
    			flagFCE = new FlagCellEditor(false);
    		}
      		flagFCE.setTable(this, _r, _c); // needed to handle delete key
      		flagFCE.addActionListener(this);
    		return flagFCE;
    	}

        if (o instanceof LongTextAttribute) {
        	if (longTCE==null){
        		longTCE = new LongCellEditor();
        		longTCE.addDocumentListener(this);
        		registerActionKeys(longTCE);
        	}
        	longTCE.setTable(this, _r, _c); // needed to handle delete key
    		return longTCE;
        }
        if (o instanceof XMLAttribute) {
           	if (xmlTCE==null){
           		xmlTCE = new XMLCellEditor(editController);
        	}
           	xmlTCE.setTable(this, _r, _c); // needed to handle delete key
    		return xmlTCE;
        } else if (o instanceof BlobAttribute) {
           	if (blobTCE==null){
           		blobTCE = new BlobCellEditor(editController,this);
        	}
           	blobTCE.setTable(this, _r, _c); // needed to handle delete key
    		blobTCE.addActionListener(this); // used to recognize newly attached blob
    		return blobTCE;
        }

    	if (o instanceof TextAttribute) {
    	    TextAttribute txt = (TextAttribute) o;
            EANMetaAttribute meta = txt.getMetaAttribute();
            if (meta.isDate()) {
                if(dateTCE==null){
                	dateTCE = new DateCellEditor(false);
            		registerActionKeys(dateTCE);
                }
                dateTCE.setTable(this, _r, _c); // needed to handle delete key
        		dateTCE.addDocumentListener(this);
                return dateTCE;
            } else if (meta.isTime()) {
            	if(timeTCE==null){
            		timeTCE = new TimeCellEditor(getProfile(),false);
            		timeTCE.addDocumentListener(this);
            		registerActionKeys(timeTCE);
            	}
            	timeTCE.setTable(this, _r, _c); // needed to handle delete key
            	return timeTCE;
            } else {
        		if (textTCE==null){
        			textTCE = new TextCellEditor(false);
        			textTCE.addDocumentListener(this);

        			registerActionKeys(textTCE);
        		}
        		textTCE.setTable(this, _r, _c); // needed to handle delete key
        		return textTCE;
            }
    	}

        return null;
    }

	/**
	 * spellCheck - only called if editor is valid
	 * only text and longtext will have a spellcheck handler
	 * @return
	 */
	public SpellCheckHandler getSpellCheckHandler(){
		boolean chgd = false;

    	int rows[] = getSelectedRows();
    	for(int r=0; r<rows.length; r++){
    		if (isValidCell(rows[r], 1)) {
    			EANAttribute attr = getAttribute(rows[r],1);
    			if(attr instanceof LongTextAttribute){
    				chgd = attr.hasChanges() || hasNewChanges(rows[r],1);
    				if(chgd){
    					return ((LongCellEditor)getCellEditor(rows[r],1)).getSpellCheckHandler();
    				}
    			}else if(attr instanceof TextAttribute){
    				EANMetaAttribute meta = attr.getMetaAttribute();
    				if (meta.isDate() || meta.isTime()) {
    				}else{
    					chgd = attr.hasChanges() || hasNewChanges(rows[r],1);
    					if(chgd){
    						return ((TextCellEditor)getCellEditor(rows[r],1)).getSpellCheckHandler();
    					}
    				}
    			}
    		}
    	}
    	return null;
	}

    /**
     * move to the specified attribute
     * @param attrKey
     */
    public void moveToError(String attrKey) {
    	int row =  ((RSTTableModel)getModel()).getModelRowIndex(attrKey);

    	row = convertRowIndexToView(row);

    	setRowSelectionInterval(row, row);
    	setColumnSelectionInterval(1, 1);

    	scrollRectToVisible(getCellRect(row, 1, false));
    	//dont use this grabFocus();
    	requestFocusInWindow(); // use this instead.
    }

    /**
     * getSelectionKey
     * @return
     */
    public String getSelectionKey() {
        int row = getSelectedRow();
        if(row == -1){
        	return null;
        }
        return ((RSTTableModel)getModel()).getRowKey(convertRowIndexToModel(row));
    }

    /**
     * select the specified attribute
     * @param _selKey
     */
    public void setSelection(String _selKey) {
    	if(_selKey!=null){
    		int row = ((RSTTableModel)getModel()).getModelRowIndex(_selKey);
    		if(row !=-1){
    			row = convertRowIndexToView(row);
    			setRowSelectionInterval(row, row);
    			setColumnSelectionInterval(1, 1);
    			scrollRectToVisible(getCellRect(row, 1, false));
    		}
    	}
		resizeCells(); // user may have changed an attr in a diff editor since this was last opened
    }
    /**
     * release memory
     */
    public void dereference() {
     	uiprefkey = null;

        if(textTCE != null){
    		unregisterActionKeys(textTCE);
        }

        if(dateTCE != null){
    		unregisterActionKeys(dateTCE);
        }

        if (timeTCE != null) {
    		unregisterActionKeys(timeTCE);
        }
        if (blobTCE != null) {
        	blobTCE.dereference();
        	blobTCE = null;
        }

        if (longTCE != null) {
        	unregisterActionKeys(longTCE);
        	longTCE.dereference();
        	longTCE = null;
        }

        if (xmlTCE != null) {
        	xmlTCE.dereference();
        	xmlTCE = null;
        }

        if (foundRenderer != null) {
        	foundRenderer.dereference();
            foundRenderer = null;
        }
        if (lockedFoundRenderer !=null) {
        	lockedFoundRenderer.dereference();
        	lockedFoundRenderer = null;
        }

        if (lockedRenderer !=null) {
        	lockedRenderer.dereference();
        	lockedRenderer = null;
        }

        if (rowHeaderFoundRenderer !=null) {
        	rowHeaderFoundRenderer.dereference();
        	rowHeaderFoundRenderer = null;
        }
        longRenderer.dereference();
        longRenderer = null;

        foundLongRenderer.dereference();
        foundLongRenderer = null;

        lockedLongRenderer.dereference();
        lockedLongRenderer = null;

        lockedFoundLongRenderer.dereference();
        lockedFoundLongRenderer = null;

       	if (rowfilters!=null){
			Iterator<RowFilter<Object, Object>> itr = rowfilters.listIterator();
			while (itr.hasNext()) {
				AttrFilter fif = (AttrFilter)itr.next();
				fif.dereference();
			}
    		rowfilters.clear();
    		rowfilters = null;
    	}
        super.dereference();

        editController = null;

        if(vFill !=null){
        	vFill.clear();
        	vFill = null;
        }
    }

    /**
     * deactivateAttribute
     */
    public void deactivateAttribute() {
        int rows[] = getSelectedRows();
    	int deactRow = -1;
        for (int i = 0; i < rows.length; ++i) {
        	if(getModel().isCellEditable(convertRowIndexToModel(rows[i]), convertColumnIndexToModel(1))){
        		AttrCellEditor ace = (AttrCellEditor)this.getCellEditor(rows[i], 1);
        		if(ace.isCellEditable(null)){// this gets the lock too
        			setValueAt(null, rows[i], 1);
        			if(deactRow==-1){
		 				deactRow=rows[i];
        			}
        		}
        	}
        }

    	refreshClassification();

        // restore selection
		setRowSelectionInterval(deactRow, deactRow);
		setColumnSelectionInterval(1, 1);
		scrollToRowCol(deactRow, 1);
    }
    /**
     * used by deactivateattraction to enable it
     * @return
     */
    public boolean hasEditableAttrSelected() {
        int rows[] = getSelectedRows();
        for(int i=0; i<rows.length; i++){
        	if (isCellEditable(rows[i], 1)) { // do rst and profile dialback check here
        		// must drop to model to get celleditable, verttable overrides to launch other editors
        		if(getModel().isCellEditable(convertRowIndexToModel(rows[i]), convertColumnIndexToModel(1))){
        			return true;
        		}
        	}
        }

    	return false;
    }
    /**
     * does the current editor have a change
     * @return
     */
	public boolean hasChangedAttrSelected() {
    	int rows[] = getSelectedRows();
    	for(int r=0; r<rows.length; r++){
    		if (isValidCell(rows[r], 1)) {
    			EANAttribute attr = getAttribute(rows[r],1);
    			if(hasNewChanges(rows[r],1) ||
    					(attr!=null && attr.hasChanges())){
    				return true;
    			}
    		}
    	}
    	return false;
    }

    /**
     * lockRows - called on the background thread
     *
     */
    public void lockRows() {
    	EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(getProfile()); // use hashtable if possible
    	DBUtils.lock(this.getRSTable(),EACM.getEACM().getLockMgr().getLockList(getProfile(),true),
    			getProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);
    }
    /**
     * called after lockRows completes and is on the eventdispatch thread
     */
    public void checkLockStatus() {
    	// find editable cells and check lock msg, cant look at only 0,1 because it may not be editable
    	for (int r=0; r<this.getRowCount(); r++){
    		for(int c=0; c<this.getColumnCount(); c++){
    			int modelRow = convertRowIndexToModel(r);
    			int modelCol = convertColumnIndexToModel(c);
    			boolean editable = getModel().isCellEditable(modelRow, modelCol);
    			if(editable && !isCellLocked(r, c)){
    				LockGroup lockGroup = ((RSTTableModel)getModel()).getLockGroup(modelRow,modelCol);  // dont use overridden method
        			if (lockGroup != null) {
        				com.ibm.eacm.ui.UI.showErrorMessage(this,lockGroup.toString());
        				return;
        			}
    			}
    		}
    	}

    	resizeCells();
    }

    /**
     * unlockRows
     *
     */
    public void unlockRows() {
    	EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(getProfile()); // use hashtable if possible
    	DBUtils.unlock(getRSTable(), EACM.getEACM().getLockMgr().getLockList(getProfile(),true), getProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);
    	// only different values will actually fire an event - make sure actions listening
    	// reflect the correct state
    	firePropertyChange(DATALOCKED_PROPERTY, true, false);
    }

    /**
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     * prevent selection of column 0
     */
    public void changeSelection(int _row, int _col, boolean _toggle, boolean _extend) {
        super.changeSelection(_row, convertColumnIndexToModel(1), _toggle, _extend);
    }

    /**
     * getUIPrefKey
     *
     * @return
     */
    public String getUIPrefKey() {
        return uiprefkey;
    }

    /**
     * refreshClassification
     */
    public void refreshClassification() {
        ((RSTTableModel)getModel()).refresh(false);
        resizeCells();
    }


	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.RSTTable#getHeaderWidth(java.awt.FontMetrics, javax.swing.table.TableColumn)
	 */
	protected int getHeaderWidth(FontMetrics fm,TableColumn _tc) {
		Object o = _tc.getHeaderValue();
		String str = "";
		if (o != null) {
			str = o.toString();
		}

		int strwidth = fm.stringWidth(str);
		return Math.max(MIN_BLOB_WIDTH,strwidth);
	}

	/**
	 *  append selected attributes - called by FillAppendAction
	 */
    public void fillAppend(){
    	if(vFill==null){
			vFill = new FillVector();
		}
    	fillAdd();
    }

	/**
	 *  copy selected attributes - called by FillCopyAction
	 */
	public void fillCopy() {
		if(vFill==null){
			vFill = new FillVector();
		}else{
			vFill.clear();
		}

		fillAdd();
	}
	/**
	 * find selected attributes add to the fill vector
	 */
	private void fillAdd(){
        int[] rows = getSelectedRows();
    	boolean bMsg = true; // output the blob msg only 1 time
        for (int r = 0; r < rows.length; ++r) {
			EANAttribute attr = getAttribute(rows[r], 1);
			if (attr instanceof BlobAttribute) {
				if (bMsg) {
					//msg23047 = Blob Fields can not be fill copied.
					com.ibm.eacm.ui.UI.showErrorMessage(this,Utils.getResource("msg23047"));
					bMsg= false;
				}
				continue;
			}
			vFill.add(attr,convertRowIndexToModel(rows[r]));
        }

	}

    public void fillCopyEntity(){
		if(vFill==null){
			vFill = new FillVector();
		}else{
			vFill.clear();
		}
		
    	boolean bMsg = true; // output the blob msg only 1 time

        for (int r = 0; r < this.getRowCount(); ++r) {
			EANAttribute attr = getAttribute(r, 1);
			if (attr instanceof BlobAttribute) {
				if (bMsg) {
					//msg23047 = Blob Fields can not be fill copied.
					com.ibm.eacm.ui.UI.showErrorMessage(this,Utils.getResource("msg23047"));
					bMsg= false;
				}
				continue;
			}
			vFill.add(attr,convertRowIndexToModel(r));
        }
    }
    
    /**
     * used to enable fillpasteaction
     * @return
     */
    public boolean canFillPaste(){
    	return vFill!=null && vFill.size()>0;
    }
	/**
	 * fillPaste -called on bg thread
	 */
    public void fillPaste() {
    	lockRows();
        checkLockStatus();

    	putFillValue();
    }
    /**
     * used by FillPasteAction - fire table events and repaint - called on event thread
     */
    public void finishAction() {
    	((RSTTableModel)getModel()).updatedModel();

    	resizeCells();
    	repaint();
    }
	/**
	 * put each fill attribute value into the selected row's attribute
	 */
	private void putFillValue() {
		Vector<TransitionItem> transVct = new Vector<TransitionItem>();

		for (int i = 0; i < vFill.size(); ++i) {
			EANAttribute fillAtt = vFill.getAttribute(i);
			EANAttribute localAtt = getAttribute(convertRowIndexToView(vFill.getModelColumnId(i)),1);
			if(localAtt!= null){
				processAttribute(fillAtt, localAtt,transVct);
			}else{
				UIManager.getLookAndFeel().provideErrorFeedback(this);
				Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"could not find attribute for "+fillAtt.getKey());
			}
		}

		if(transVct.size()>0){
			processTransitions(transVct, false);
			for (int i=0;i<transVct.size();++i) {
				transVct.get(i).dereference();
			}
			transVct.clear();
		}
	}
	/**
	 *
	 * @param _vct
	 * @param _report
	 */
	private void processTransitions(Vector<TransitionItem> _vct, boolean _report) {
		if (!_vct.isEmpty()) {
			boolean bSuccess = false;
			Vector<TransitionItem> tmp = new Vector<TransitionItem>();
			for (int i=0;i<_vct.size();++i) {
				TransitionItem eTran = _vct.get(i);
				if (Utils.isDebug()) {
					Logger.getLogger(APP_PKG_NAME).log(Level.FINER,"processing transition(" + i + "): " + eTran);
				}
				if (eTran.process(_report)) {
					bSuccess = true;
				} else if (!_report) {
					tmp.add(eTran);
				}
			}
			if (_report) {
				return;
			}
			if (bSuccess) {
				if (Utils.isDebug()) {
					Logger.getLogger(APP_PKG_NAME).log(Level.FINER,"    processed at least one bad transition");
				}
				processTransitions(tmp,false);
			} else {
				if (Utils.isDebug()) {
					Logger.getLogger(APP_PKG_NAME).log(Level.FINER,"    did NOT process at least one bad transition");
				}
				processTransitions(tmp,true);
			}
		}
	}

	/**
	 * put the fill attribute value in the local (selected) attribute
	 * @param fillSrc
	 * @param local
	 * @param transVct
	 */
	private void processAttribute(EANAttribute fillSrc, EANAttribute local,Vector<TransitionItem> transVct) {
		if (local.isEditable()) {
			if (local.hasLock(local.getKey(), EACM.getEACM().getLockMgr().getLockOwner(getProfile()), getProfile())) {
				try {
					boolean isok=true; // SR14 hack to get to warning msg in editor
					if (fillSrc.getMetaAttribute().isDate()){
						// editors are bypassed in fillcopy and fillpaste
						if(dateTCE==null){
							dateTCE = new DateCellEditor(false);
						}
						dateTCE.setAttribute(local);
						isok =dateTCE.isValid(fillSrc.toString());
					}

					if (isok){
						local.put(fillSrc.get());
					}
				} catch (StateTransitionException ste) {
					ste.printStackTrace();
					transVct.add(new TransitionItem(fillSrc, local));
				} catch (EANBusinessRuleException bre) {
					com.ibm.eacm.ui.UI.showException(this,bre);
				}
			}else{
				UIManager.getLookAndFeel().provideErrorFeedback(this);
				Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"user does not have lock for "+local.getKey());
			}
		}else{
			UIManager.getLookAndFeel().provideErrorFeedback(this);
			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,local.getKey()+" is not editable");
		}
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.VerticalTable#getValueAt(int, int)
     */
    public Object getValueAt(int _r, int _c) {
    	int row = convertRowIndexToModel(_r);
    	int col = convertColumnIndexToModel(_c);
    	if (editController !=null && editController.isIndicateRelations()) { // will be null when constructor runs
    		if (isChild(row, col)) {
    			return INDICATE_CHILD + ((RSTTableModel)getModel()).getValueAt(row, col, true);
    		} else if (isParent(row, col)) {
    			return INDICATE_PARENT + ((RSTTableModel)getModel()).getValueAt(row, col, true);
    		}
    	}
    	return ((RSTTableModel)getModel()).getValueAt(row, col, true);
    }

    /**
     * does this need a child indicator
     * @param row
     * @param col
     * @return
     */
    private boolean isChild(int row, int col) {
    	if (col == 0) {
    		return ((RSTTableModel)getModel()).isChild(row, col);
    	}
        return false;
    }

    /**
     * does this need a parent indicator
     * @param row
     * @param col
     * @return
     */
    private boolean isParent(int row, int col) {
    	if (col == 0) {
    		return ((RSTTableModel)getModel()).isParent(row, col);
    	}
    	return false;
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
	 * used when flag editor changes
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


    /* (non-Javadoc)
     * called by sortdialog when user has specified desired sort
     * and when mouse is clicked on the rowheaderlabel
     * @see com.ibm.eacm.objects.Sortable#sort(List <RowSorter.SortKey> list);
     */
    public void sort(List <RowSorter.SortKey> list){
    	// vertical editor should not sort automatically
    	if(getRowSorter()==null){
    		setRowSorter(new TableRowSorter<TableModel>(getModel()));
    		((TableRowSorter<?>)getRowSorter()).setSortsOnUpdates(false);
    	}

    	super.sort(list);
    	resizeCells();
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#filter()
     */
    public void filter() {
    	// vertical editor should not sort automatically
    	if(getRowSorter()==null){
    		setRowSorter(new TableRowSorter<TableModel>(getModel()));
   			((TableRowSorter<?>)getRowSorter()).setSortsOnUpdates(false);
    	}
    	super.filter();
    }

    //==================================================
    // HIDECOL_ACTION and UNHIDECOL_ACTION - this will be rows here
    public void showHide(boolean hide) {
    	if (hide) {
    		int[] rows = getSelectedRows();
    		clearSelection();

    		// build RowFilters
    		if(rowfilters==null){
    			rowfilters = new ArrayList<RowFilter<Object,Object>>(rows.length);
    		}
    		for (int i=0; i< rows.length; i++){
    			int viewrow = rows[i];
    			rowfilters.add(new AttrFilter(getAttribute(viewrow, 1), convertColumnIndexToModel(0),
    					convertColumnIndexToModel(1)));//rowid from the view
    		}
    		RowFilter<Object,Object> rf = RowFilter.andFilter(rowfilters);

    		if(getRowSorter()==null){
    			setRowSorter(new TableRowSorter<TableModel>(getModel()));
    		}
    		((TableRowSorter<?>)getRowSorter()).setRowFilter(rf);

    	} else {
    		rowfilters.clear();
    		((TableRowSorter<?>)getRowSorter()).setRowFilter(null);
    	}
    	if (this.getColumnCount()>0){
    		setColumnSelectionInterval(1, 1);
    	}
    	if (this.getRowCount()>0){
    		setRowSelectionInterval(0, 0);
    	}

    	EACM.getEACM().setHiddenStatus(hasHiddenCols());
    	resizeCells();
    }
    /**
     * rows are hidden here, use the same method
     * @return
     */
    public boolean hasHiddenCols(){
    	return this.getRowCount()!=this.getModel().getRowCount();
    }
    private static class AttrFilter extends RowFilter<Object,Object> {
        private int[] columns;
        private EANAttribute attr;

        AttrFilter(EANAttribute att, int... columns) {
            this.columns = columns;
            attr = att;
        }

        void dereference(){
        	columns=null;
        	attr = null;
        }
        /* (non-Javadoc)
         * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
         */
        public boolean include(Entry<? extends Object,? extends Object> value){
            int count = value.getValueCount();
            if (columns.length > 0) {
                for (int i = columns.length - 1; i >= 0; i--) {
                    int index = columns[i];
                    if (index < count) {
                        if (include(value, index)) {
                            return true;
                        }
                    }
                }
            } else {
                while (--count >= 0) {
                    if (include(value, count)) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected boolean include(Entry<? extends Object,? extends Object> value, int index) {
        	boolean match = false;
        	String strValue = value.getStringValue(index);
        	switch(index){
        	case 0:
        		match = strValue.equals(attr.getMetaAttribute().toString());
        		break;
        	case 1:
        		match = strValue.equals(attr.toString());
        		break;
        	}

        	return !match;
        }
    }
}

