//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.table;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.EACM;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.rend.GridRenderer;

import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;

import javax.swing.RowSorter.SortKey;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.editor.*;

import com.ibm.eacm.edit.EditController;
import com.ibm.eacm.edit.SpellCheckHandler;


/**
 * this is the table for the grid editor
 * @author Wendy Stimpson
 */
//$Log: GridTable.java,v $
//Revision 1.10  2013/11/12 21:55:37  wendy
//Don't autosort after fillpaste
//
//Revision 1.9  2013/11/07 18:09:40  wendy
//Add FillCopyEntity action
//
//Revision 1.8  2013/10/10 20:18:22  wendy
//check row exists after rollback
//
//Revision 1.7  2013/10/09 16:31:54  wendy
//add room for date calendar button in grid edit, chg update to prevent sort and jump
//
//Revision 1.6  2013/08/14 16:47:57  wendy
//resize columns after rollback
//
//Revision 1.5  2013/07/29 18:38:58  wendy
//Turn off autosort, update classifications after edit and resize cells
//
//Revision 1.4  2013/07/25 20:52:23  wendy
//added preference for grid edit auto sort
//
//Revision 1.3  2013/07/19 17:48:35  wendy
//pass window parent into mf cell editor
//
//Revision 1.2  2013/05/01 18:35:14  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class GridTable extends HorzTable implements DocumentListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	private static final int MAXCOLWIDTH = 162; // limit the initial width of a column

	private GridRenderer lockedFoundRend = null;
	private GridRenderer lockedRend = null;
	private GridRenderer foundRend = null;

	private TextCellEditor textTCE = null;
	private FlagCellEditor flagFCE = null;
	private DateCellEditor dateTCE = null;
	private TimeCellEditor timeTCE = null;
	private MultiCellEditor multiFCE = null;
	private BlobCellEditor blobTCE = null;
	private LongCellEditor longTCE = null;
	private XMLCellEditor xmlTCE = null;

	private FillVector vFill = null;
	private EditController editController = null;
	private boolean isDataEditable = false; // is the rowselectabletable editable? and profile current

	/**
	 * table used for grid editing
	 * @param list
	 * @param table
	 * @param ec
	 */
	public GridTable(EntityList list, RowSelectableTable table, EditController ec) {
		super(table,list.getProfile());
		init();

		isDataEditable = Utils.isEditable(table,getProfile());

		editController = ec;
	}

	/**
	 * allow derived classes to provide a different rsttablemodel
	 * @return
	 */
	protected RSTTableModel createTableModel(){
		return new GridTableModel(this);
	}


	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.RSTTable#setSortKeys()
	 */
	protected void setSortKeys(){
		List<? extends SortKey> origsortKeys = null;
		if(getRowSorter() != null){
			origsortKeys = ((TableRowSorter<?>)getRowSorter()).getSortKeys();
		}
		if (origsortKeys !=null && origsortKeys.size()>0){
			return; // keep the sort already specified
		}
		// must be done after the columns are setup in the model
		//The precedence of the columns in the sort is indicated by the order of the sort keys in the sort key list.
		List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		if (this.getColumnCount()>1){
			sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
		}
		setSortKeys(sortKeys);
	}
	/**
	 * get the key for the EANFoundation with implicators at the first selected row
	 * @return
	 */
	public String getRecordKey() {
		int row = getSelectedRow();
		if (row ==-1) { // nothing selected, so use first one
			return getEANKey(0);
		}
		return getEANKey(row);
	}

	/**
	 * @see javax.swing.JTable#getValueAt(int, int)
	 */
	public Object getValueAt(int r, int c) {
		return getValueAt(r, c, false);
	}

	/**
	 * need to override this because getValueAt doesnt return the attribute
	 * cant be in RSTTable because RelAttrCrossTable needs base java behavior
	 * @see javax.swing.JTable#prepareEditor(javax.swing.table.TableCellEditor, int, int)
	 */
	public Component prepareEditor(TableCellEditor editor, int row, int col) {
		Object o = getEANObject(row, col); // getValueAt returns a string, not the attribute

		boolean isSelected = isCellSelected(row, col);
		Component comp = editor.getTableCellEditorComponent(this, o, isSelected, row, col);
		if (comp instanceof MultiEditor) {
			Rectangle rect = getCellRect(row, col, false);
			rect.setSize(rect.width, 16);
			scrollRectToVisible(rect);
		}
		return comp;
	}

	/* (non-Javadoc)
	 * provide a way to get celllocks on an attribute that isnt editable but others are
	 * @see com.ibm.eacm.table.RSTTable#editCellAt(int, int, java.util.EventObject)
	 */
	public boolean editCellAt(int row, int column, EventObject anEvent){
		if (getCellEditor() != null && !getCellEditor().stopCellEditing()) {
			return false;
		}

		if (row < 0 || row >= getRowCount() ||
				column < 0 || column >= getColumnCount()) {
			return false;
		}

		if(isDataEditable && // table can edit and user prof is current
				!isCellLocked(row, column)){ // and dont have lock yet
			// try to get cell lock if editor will be launched
			if(AttrCellEditor.canLaunchEditorForEvent(anEvent)){
				if(getCellLock(row, column)){
					repaint();
				}
			}
		}

		// always install an editor for some types so user can view like long, xml and blob
		return super.editCellAt(row, column, anEvent);
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
	/**
	 * init
	 *
	 */
	protected void init() {
		super.init();

		foundRend = new GridRenderer();
		foundRend.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);

		lockedRend = new GridRenderer();
		lockedRend.setBorderKeys(SELECTED_BORDER_KEY, SELECTED_BORDER_KEY);
		lockedRend.setColorKeys(null, PREF_COLOR_LOCK);

		lockedFoundRend = new GridRenderer();
		lockedFoundRend.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
		lockedFoundRend.setBorderKeys(SELECTED_BORDER_KEY, SELECTED_BORDER_KEY);
		lockedFoundRend.setColorKeys(null, PREF_COLOR_LOCK);

		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setCellSelectionEnabled(true);

		setDefaultRenderer(Object.class, new GridRenderer());
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.RSTTable#adjustColumnSize()
	 */
	protected void adjustColumnSize() {
		TableColumnModel tcm = getColumnModel();
		// table will not be resized, date cells may be too small in grid edit
		if (getRowCount()>0){
			for (int i=0; i<tcm.getColumnCount(); i++){
				TableColumn tc = tcm.getColumn(i);
				int width = tc.getWidth();
				EANFoundation ef = ((RSTTable)this).getEANObject(0, i);

				if (ef instanceof TextAttribute) {
					TextAttribute txt = (TextAttribute) ef;
					EANMetaAttribute meta = txt.getMetaAttribute();
					if (meta != null && meta.isDate()) {
						//allow for calendar button
						width += CALENDAR_WIDTH_ADJUSTMENT;
					}
				}

				tc.setWidth(width);
				tc.setPreferredWidth(width);
				tc.setMinWidth(MIN_COL_WIDTH);
			}
		}
	}
	/**
	 * get the accessibility resource key
	 * @return
	 */
	protected String getAccessibilityKey() {
		return "accessible.horzTable";
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

		boolean isLocked = isDataEditable && isCellLocked(r, c) &&
				getModel().isCellEditable(convertRowIndexToModel(r),convertColumnIndexToModel(c));

		if (isLocked) {
			if (isFound) {
				return lockedFoundRend;
			}

			return lockedRend;
		}
		if (isFound) {
			return foundRend;
		}

		return getDefaultRenderer(Object.class);
	}
	/*
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#isReplaceable()
	 */
	public boolean isReplaceable() {
		return isDataEditable;
	}
	/* (non-Javadoc)
	 * used when resizecells()
	 * @see com.ibm.eacm.table.BaseTable#getColWidth(java.awt.FontMetrics, java.lang.Object)
	 */
	protected int getColWidth(FontMetrics fm,Object o) {
		String str = "";
		if (o != null) {
			str = o.toString();
			// must split it like the cellrenderer will
			StringTokenizer st = new StringTokenizer(str,NEWLINE);
			while(st.hasMoreTokens()){
				str = st.nextToken();
				break;
			}
		}

		if(str.length()>MAXCOLWIDTH){
			str = str.substring(0,MAXCOLWIDTH);
		}
		return fm.stringWidth(str);
	}
	/* (non-Javadoc)
	 * only one line of text in grid cell
	 * @see com.ibm.eacm.table.RSTTable#getRowHeight(java.awt.FontMetrics, int, int, java.lang.String)
	 */
	protected int getRowHeight(FontMetrics fm, int baseHeight, String str){
		return fm.getHeight();
	}
	/**
	 * used to determine if lock action should be enabled
	 * @return
	 */
	public boolean hasSelectedUnlockedRows() {
		int[] rr = getSelectedRows();
		for (int r=0; r<rr.length; r++){
			int viewid = rr[r];
			int mdlrow = convertRowIndexToModel(viewid);
			if(!isNewRow(mdlrow)){
				for(int c=0; c<getColumnCount(); c++){
					if(!isCellLocked(viewid, c) &&
							getModel().isCellEditable(mdlrow,convertColumnIndexToModel(c))){
						return true;
					}
				}
			}
		}

		return false;
	}
	/* (non-Javadoc)
	 * @see javax.swing.JTable#getCellEditor(int, int)
	 */
	public TableCellEditor getCellEditor(int r, int c) {
		Object o =this.getEANObject(r, c);
		if (o instanceof MultiFlagAttribute) {
			if (multiFCE==null){
				// get window parent - focus will go back to it when closed
				Window parent = EACM.getEACM();
				if(getTopLevelAncestor() instanceof Window){
					parent = (Window)getTopLevelAncestor();
				}
				multiFCE = new MultiCellEditor(parent,false);
			}
			multiFCE.setTable(this, r, c); // needed to handle delete key
			return multiFCE;
		}
		if (o instanceof EANFlagAttribute) {
			if (flagFCE==null){
				flagFCE = new FlagCellEditor(false);
			}
			flagFCE.setTable(this, r, c); // needed to handle delete key
			flagFCE.addActionListener(this);
			return flagFCE;
		}

		if (o instanceof LongTextAttribute) {
			if (longTCE==null){
				longTCE = new LongCellEditor();
			}
			longTCE.setTable(this, r, c); // needed to handle delete key
			longTCE.addDocumentListener(this);
			return longTCE;
		}
		if (o instanceof XMLAttribute) {
			if (xmlTCE==null){
				xmlTCE = new XMLCellEditor(editController);
			}
			xmlTCE.setTable(this, r, c); // needed to handle delete key
			return xmlTCE;
		} else if (o instanceof BlobAttribute) {
			if (blobTCE==null){
				blobTCE = new BlobCellEditor(editController,this);
			}
			blobTCE.setTable(this, r, c); // needed to handle delete key
			blobTCE.addActionListener(this); // used to recognize newly attached blob
			return blobTCE;
		}

		if (o instanceof TextAttribute) {
			TextAttribute txt = (TextAttribute) o;
			EANMetaAttribute meta = txt.getMetaAttribute();
			if (meta.isDate()) {
				if(dateTCE==null){
					dateTCE = new DateCellEditor(false);
				}
				dateTCE.setTable(this, r, c); // needed to handle delete key
				dateTCE.addDocumentListener(this);
				return dateTCE;
			} else if (meta.isTime()) {
				if(timeTCE==null){
					timeTCE = new TimeCellEditor(getProfile(),false);
				}
				timeTCE.setTable(this, r, c); // needed to handle delete key
				return timeTCE;
			} else {
				if (textTCE==null){
					textTCE = new TextCellEditor(false);
				}
				textTCE.setTable(this, r, c); // needed to handle delete key
				textTCE.addDocumentListener(this);
				return textTCE;
			}
		}

		return null;
	}


	/**
	 * is spell check required (meta has it) and it has not been done yet
	 * @return
	 */
	public boolean isSpellCheckRequired(){
		boolean req = false;
		int rows[] = getSelectedRows();
		int cols[] = getSelectedColumns();
		outerloop:for(int r=0; r<rows.length; r++){
			for(int c=0; c<cols.length; c++){
				if (isValidCell(rows[r], cols[c])) {
					EANAttribute attr = getAttribute(rows[r],cols[c]);
					if(attr instanceof LongTextAttribute || attr instanceof TextAttribute){
						EANMetaAttribute meta = attr.getMetaAttribute();
						req = meta.isSpellCheckable();
					}
					if(req){
						break outerloop;
					}
				}
			}
		}
		return req;
	}

	/**
	 * spellCheck - only called if editor is valid
	 * only text and longtext will have a spellcheck handler
	 * @return
	 */
	public SpellCheckHandler getSpellCheckHandler(){
		boolean chgd = false;

		int rows[] = getSelectedRows();
		int cols[] = getSelectedColumns();
		for(int r=0; r<rows.length; r++){
			for(int c=0; c<cols.length; c++){
				if (isValidCell(rows[r], cols[c])) {
					EANAttribute attr = getAttribute(rows[r],cols[c]);
					if(attr instanceof LongTextAttribute){
						chgd = attr.hasChanges() || hasNewChanges(rows[r],cols[c]);
						if(chgd){
							return ((LongCellEditor)getCellEditor(rows[r],cols[c])).getSpellCheckHandler();
						}
					}else if(attr instanceof TextAttribute){
						EANMetaAttribute meta = attr.getMetaAttribute();
						if (meta.isDate() || meta.isTime()) {
						}else{
							chgd = attr.hasChanges() || hasNewChanges(rows[r],cols[c]);
							if(chgd){
								return ((TextCellEditor)getCellEditor(rows[r],cols[c])).getSpellCheckHandler();
							}
						}
					}
				}
			}
		}
		return null;
	}
	/**
	 * dereference
	 *
	 */
	public void dereference() {
		if (lockedFoundRend != null) {
			lockedFoundRend.dereference();
			lockedFoundRend = null;
		}
		if (lockedRend != null) {
			lockedRend.dereference();
			lockedRend = null;
		}
		if (foundRend != null) {
			foundRend.dereference();
			foundRend = null;
		}

		if(textTCE != null){
			textTCE.dereference();
			textTCE = null;
		}

		if(flagFCE != null){
			flagFCE.dereference();
			flagFCE = null;
		}

		if(dateTCE != null){
			dateTCE.dereference();
			dateTCE = null;
		}

		if (multiFCE != null) {
			multiFCE.dereference();
			multiFCE = null;
		}

		if (timeTCE != null) {
			timeTCE.dereference();
			timeTCE = null;
		}
		if (blobTCE != null) {
			blobTCE.dereference();
			blobTCE = null;
		}

		if (longTCE != null) {
			longTCE.dereference();
			longTCE = null;
		}

		if (xmlTCE != null) {
			xmlTCE.dereference();
			xmlTCE = null;
		}

		if (vFill != null) {
			vFill.clear();
			vFill = null;
		}
		super.dereference();

		editController = null;
	}

	/**
	 * deactivateAttribute
	 */
	public void deactivateAttribute() {
		int rows[] = getSelectedRows();
		int cols[] = getSelectedColumns();
		int deactRow = -1;
		int deactCol = -1;
		for(int r=0; r<rows.length; r++){
			for(int c=0; c<cols.length; c++){
				if(getModel().isCellEditable(convertRowIndexToModel(rows[r]), convertColumnIndexToModel(cols[c]))){
					AttrCellEditor ace = (AttrCellEditor)this.getCellEditor(rows[r], cols[c]);
					if(ace.isCellEditable(null)){// this gets the lock too
						setValueAt(null, rows[r], cols[c]);
						if(deactRow==-1){
							deactRow=rows[r];
							deactCol=cols[c];
						}
					}
				}
			}
		}
		// refresh classifications
		((RSTTableModel)getModel()).refresh(false);
		resizeCells();

		// restore selection
		setRowSelectionInterval(deactRow, deactRow);
		setColumnSelectionInterval(deactCol, deactCol);
		
		scrollToRowCol(deactRow, deactCol);
	}

	/**
	 * used by deactivateattraction to enable it
	 * @return
	 */
	public boolean hasEditableAttrSelected() {
		int rows[] = getSelectedRows();
		int cols[] = getSelectedColumns();
		for(int r=0; r<rows.length; r++){
			for(int c=0; c<cols.length; c++){
				if (isCellEditable(rows[r], cols[c])) { // do rst and profile dialback check here
					// must drop to model to get celleditable, gridtable overrides to launch other editors
					if(getModel().isCellEditable(convertRowIndexToModel(rows[r]), convertColumnIndexToModel(cols[c]))){
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * lockRows - called on the background thread
	 */
	public void lockRows() {
		Profile prof = getProfile();
		EntityItem lockOwnerEI =EACM.getEACM().getLockMgr().getLockOwner(prof); // use hashtable if possible

		int[] rr = getSelectedRows();
		if(rr.length>0){
			Vector<Integer> keyVct = new Vector<Integer>();
			rowloop:for (int i = 0; i < rr.length; ++i) {
				int viewid = rr[i];
				String rowkey = getEANKey(rr[i]);
				// is this already locked?
				for(int c=0; c<getColumnCount(); c++){
					// cell may have lock, like model.attr when prodstruct is under edit but the model
					// is shared so the prodstruct may not have the lock yet, must check editable
					EANAttribute attr = getAttribute(viewid, c);
					if(attr!=null && attr.isEditable()){
						EntityItem ei = attr.getEntityItem();
						if(ei!=null && ei.getKey().equals(rowkey)&& isCellLocked(viewid, c)){
							continue rowloop;
						}
					}
				}
				keyVct.add(new Integer(convertRowIndexToModel(rr[i])));
			}
			if(keyVct.size()>0){
				int iKeys[] = new int[keyVct.size()];
				for(int i=0; i<iKeys.length; i++){
					iKeys[i]=keyVct.elementAt(i).intValue();
				}

				DBUtils.lockEntityItems(getRSTable(), iKeys,
						EACM.getEACM().getLockMgr().getLockList(prof,true), prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
			}
		}
	}
	/**
	 * called after lockRowsBG completes and is on the eventdispatch thread
	 */
	public void checkLockStatus() {
		Profile prof = getProfile();
		EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(prof); // use hashtable if possible

		int[] rr = getSelectedRows();
		for (int i = 0; i < rr.length; ++i) {
			EntityItem ei = (EntityItem) getSelectedItem(rr[i]);
			if(ei instanceof VEEditItem){
				EntityItem[] eai = ((VEEditItem)ei).getEditableItems();
				for(int a=0; a<eai.length; a++){
					EntityItem relitem = eai[a];
					if (!relitem.hasLock(lockOwnerEI, prof)) {
						LockGroup lock = relitem.getLockGroup();
						if (lock != null) {
							com.ibm.eacm.ui.UI.showErrorMessage(null,lock.toString());
							return;
						}
					}
				}
			}else{
				if (!ei.hasLock(lockOwnerEI, prof)) {
					LockGroup lock = ei.getLockGroup();
					if (lock != null) {
						com.ibm.eacm.ui.UI.showErrorMessage(null,lock.toString());
						return;
					}
				}
				EntityGroup eg = ei.getEntityGroup();
				if (eg.isAssoc() || eg.isRelator()) {
					EntityItem downEI = (EntityItem) ei.getDownLink(0);
					if (downEI != null) {
						if (!downEI.hasLock(lockOwnerEI, prof)) {
							LockGroup lg = downEI.getLockGroup();
							if (lg != null) {
								com.ibm.eacm.ui.UI.showErrorMessage(null,lg.toString());
							}
						}
					}
				}
			}
		}
	}

	/**
	 * rollback one row
	 * called by ResetRecordAction in evtthread
	 * called by unlockaction and chooseToSave in unlockaction on evt thread
	 * @param viewrow
	 */
	public void rollbackRow(int viewrow) {
		if (viewrow < 0 || viewrow >= getRowCount()) {
			return;
		}
		int cols[] = getSelectedColumns();
		((GridTableModel)getModel()).rollbackRow(convertRowIndexToModel(viewrow));
		//((GridTableModel)getModel()).updatedModel(); this sorts and changes selection
    	((GridTableModel)getModel()).fireTableRowsUpdated(viewrow,viewrow);
		
		if(viewrow <getRowCount()){
			// restore selection
			setRowSelectionInterval(viewrow, viewrow);

			for(int c=0; c<cols.length; c++){
				addColumnSelectionInterval(cols[c], cols[c]);
			}
		}

		// only different values will actually fire an event - make sure actions listening
		// reflect the correct state
		firePropertyChange(DATACHANGE_PROPERTY, true, false);
		
		if(this.getRowCount()>0){
			for(int c=0; c<cols.length; c++){
				resizeAfterEdit(viewrow,cols[c]);
			}
			scrollToRowCol(viewrow, cols[0]);
		}
	}

	/**
	 * @param viewRowsVct
	 */
	public void rollbackRows(Vector<Integer> viewRowsVct) {
		Vector<Integer> rowsToRemoveVct = new Vector<Integer>();
		Vector<Integer> selectedViewRowsVct = new Vector<Integer>();
		int cols[] = getSelectedColumns();

		for(int i=0; i<viewRowsVct.size(); i++){
			int viewrow = viewRowsVct.elementAt(i).intValue();
			int mdlrow = convertRowIndexToModel(viewrow);
			if(isNewRow(mdlrow)){
				// remove this row later
				rowsToRemoveVct.add(new Integer(mdlrow));
			}else{
				selectedViewRowsVct.add(viewRowsVct.elementAt(i));
				((RSTTableModel)getModel()).rollbackRow(mdlrow);
			}
		}
		Collections.sort(rowsToRemoveVct); // sort by row order
		// remove in reverse order
		for(int i=rowsToRemoveVct.size()-1; i>=0; i--){
			int mdlrow = rowsToRemoveVct.elementAt(i).intValue();
			// remove this row
			((GridTableModel)getModel()).removeRow(mdlrow);
		}

		if(rowsToRemoveVct.size()>0){
			((RSTTableModel)getModel()).updatedModel(); // removal of new rows requires this 
		}else{
			for(int r=0; r<getRowCount(); r++){
				((RSTTableModel)getModel()).fireTableRowsUpdated(r,r);
			}
		}

		if(getRowCount()>0 && cols.length>0){  // user may have removed the only row
			// restore selection
			for(int r=0; r<selectedViewRowsVct.size(); r++){
				int viewrow = selectedViewRowsVct.elementAt(r).intValue();
				if(viewrow < getRowCount()){
					addRowSelectionInterval(viewrow, viewrow);
					for(int c=0; c<cols.length; c++){
						addColumnSelectionInterval(cols[c], cols[c]);
					}
				}
			}
			selectedViewRowsVct.clear();
		}

		// only different values will actually fire an event - make sure actions listening
		// reflect the correct state
		firePropertyChange(DATACHANGE_PROPERTY, true, false);

		if(this.getRowCount()>0){
			for(int c=0; c<cols.length; c++){
				resizeAfterEdit(0,cols[c]);
			}
		}
		
		resizeCells();
	}

	private boolean isNewRow(int mdlrowid) {
		Object o = ((RSTTableModel)getModel()).getRowEAN(mdlrowid);
		if (o instanceof EntityItem) {
			return ((EntityItem) o).isNew();
		}
		return false;
	}

	/**
	 * derived classes must override to import from a ss
	 */
	protected boolean addRowImport() {
		boolean ok = getRSTable().addRow();
		((RSTTableModel)getModel()).refresh();
		return ok;
	}

	/**
	 * unlockRows - done on background thread
	 */
	public void unlockRows(Vector<Integer> locksVct) {
		if(locksVct.size()>0){
			int iKeys[] = new int[locksVct.size()];
			for(int i=0; i<locksVct.size(); i++){
				Integer key = locksVct.elementAt(i);
				// get mdl index for this view index
				iKeys[i] = convertRowIndexToModel(key.intValue());
			}
			DBUtils.unlockEntityItems(getRSTable(), iKeys, EACM.getEACM().getLockMgr().getLockList(getProfile(),true), getProfile(),
					EACM.getEACM().getLockMgr().getLockOwner(getProfile()), LockGroup.LOCK_NORMAL);

			// only different values will actually fire an event - make sure actions listening
			// reflect the correct state
			firePropertyChange(DATALOCKED_PROPERTY, true, false);
		}
	}
	/**
	 * select row with this entityitem
	 * @param ei
	 */
	public void selectRow(EntityItem ei) {
		int r = ((RSTTableModel)getModel()).getModelRowIndex(ei.getKey());
		int cc = -1;
		if (r >= 0) {
			r = this.convertRowIndexToView(r);
			setRowSelectionInterval(r, r);
			cc = getColumnCount();
			if (cc > 0) {
				setColumnSelectionInterval(0, cc-1);
			}

			scrollRectToVisible(getCellRect(r, 0, false));
			repaint();
		}
	}

	/**
	 * select the entire row for this viewrow index
	 * @param viewrowid
	 */
	public void selectRow(int viewrowid) {
		setRowSelectionInterval(viewrowid, viewrowid);
		int cc = getColumnCount();
		if (cc > 0) {
			setColumnSelectionInterval(0, cc-1);
		}
		scrollRectToVisible(getCellRect(viewrowid, 0, false));

		repaint();
	}
	/**
	 * select the first cell for this index in the model
	 * @param modelRowIndex
	 */
	public void selectModelRow(int modelRowIndex) {
		// find the corresponding view index
		int viewrowid = convertRowIndexToView(modelRowIndex);
		setRowSelectionInterval(viewrowid, viewrowid);
		setColumnSelectionInterval(0, 0);
		scrollRectToVisible(getCellRect(viewrowid, 0, false));

		repaint();
	}

	/**
	 * get the key for the first selected column
	 * @return
	 */
	public String getSelectionKey() {
		int col = getSelectedColumn();
		String key = null;
		if(col!=-1){
			key = ((RSTTableModel)getModel()).getColumnKey(convertColumnIndexToModel(col));
		}
		return key;
	}

	/**
	 * setSelection
	 * @param recKey
	 * @param selKey
	 */
	public void setSelection(String recKey, String selKey) {
		if(getRowSorter()!=null){
			// another editor may have changed data affecting the sort order, sort again
			((TableRowSorter<?>)getRowSorter()).sort();
		}

		int row = ((RSTTableModel)getModel()).getModelRowIndex(recKey);
		if(row !=-1){
			row = convertRowIndexToView(row);
		}
		int col = -1;
		if (selKey != null) {
			col = getColumnModel().getColumnIndex(selKey);
		}

		if(row==-1){
			row = 0;
		}
		if(col==-1){
			col = 0;
		}

		setRowSelectionInterval(row, row);
		setColumnSelectionInterval(col, col);
		scrollRectToVisible(getCellRect(row, col, false));

		resizeCells(); // user may have changed an attr in a diff editor since this was last opened
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
	 * find selected attributes for one row and add to the fill vector
	 */
	private void fillAdd(){
		int row = getSelectedRow();
		if (row < 0 || row >= getRowCount()) {
			return;
		}

		int[] cols = getSelectedColumns();
		boolean bMsg = true; // output the blob msg only 1 time
		for (int i = 0; i < cols.length; ++i) {
			EANAttribute attr = getAttribute(row, cols[i]);
			if (attr instanceof BlobAttribute) {
				if (bMsg) {
					//msg23047 = Blob Fields can not be fill copied.
					com.ibm.eacm.ui.UI.showErrorMessage(this,Utils.getResource("msg23047"));
					bMsg= false;
				}
				continue;
			}
			vFill.add(attr,convertColumnIndexToModel(cols[i]));
		}
	}

    /* (non-Javadoc)
     * called by FillCopyEntityAction
     * @see com.ibm.eacm.table.RSTTable#fillCopyEntity()
     */
    public void fillCopyEntity(){
    	int row = getSelectedRow();
		if (row < 0 || row >= getRowCount()) {
			return;
		}
		if(vFill==null){
			vFill = new FillVector();
		}else{
			vFill.clear();
		}

		boolean bMsg = true; // output the blob msg only 1 time
		// add all columns
		for (int i = 0; i < this.getColumnCount(); ++i) {
			EANAttribute attr = getAttribute(row, i);
			if (attr instanceof BlobAttribute) {
				if (bMsg) {
					//msg23047 = Blob Fields can not be fill copied.
					com.ibm.eacm.ui.UI.showErrorMessage(this,Utils.getResource("msg23047"));
					bMsg= false;
				}
				continue;
			}
			vFill.add(attr,convertColumnIndexToModel(i));
		}
    }
    
	/**
	 * used to enable fillpasteaction
	 * @return
	 */
	public boolean canFillPaste(){
		return vFill!=null && vFill.size()>0 && getSelectedRowCount()>0;
	}
	/**
	 * fillPaste - called on bg thread
	 */
	public void fillPaste() {
		int[] rows = getSelectedRows();
		lockRows();

		for (int r = 0; r < rows.length; ++r) {
			putFillValue(rows[r]);
		}
	}
	/**
	 * used by FillPasteAction - fire table events and repaint - called on event thread
	 */
	public void finishAction() {
		int[] rows = getSelectedRows();
		int cols[] = getSelectedColumns();

		//((RSTTableModel)getModel()).updatedModel(); this sorts and changes selection
		for(int r=0;r<rows.length;r++){
			int viewrow = rows[r];
			((RSTTableModel)getModel()).fireTableRowsUpdated(viewrow,viewrow);
		}
    	
		// restore selection
		if (cols.length>0 && rows.length>0){
			for(int r=0;r<rows.length;r++){
				addRowSelectionInterval(rows[r], rows[r]);
			}
			for(int c=0;c<cols.length;c++){
				addColumnSelectionInterval(cols[c], cols[c]);
			}

			scrollToRow(rows[0]);
		}else{
			if(getRowCount()>0){
				setColumnSelectionInterval(0, 0);
				setRowSelectionInterval(0, 0);
				scrollToRow(0);
			}
		}
		repaint();
	}
	/**
	 * put each fill attribute value into the selected row's attribute
	 * @param row
	 */
	private void putFillValue(int row) {
		Vector<TransitionItem> transVct = new Vector<TransitionItem>();

		boolean beep = false;
		for (int i = 0; i < vFill.size(); ++i) {
			EANAttribute fillAtt = vFill.getAttribute(i);
			EANAttribute localAtt = getAttribute(row, this.convertColumnIndexToView(vFill.getModelColumnId(i)));
			if(localAtt!= null){
				beep = processAttribute(fillAtt, localAtt,transVct) || beep;
			}else{
				beep = true;
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
		if(beep){
			UIManager.getLookAndFeel().provideErrorFeedback(this);
		}
	}
	/**
	 *
	 * @param vct
	 * @param report
	 */
	private void processTransitions(Vector<TransitionItem> vct, boolean report) {
		if (!vct.isEmpty()) {
			boolean bSuccess = false;
			Vector<TransitionItem> tmp = new Vector<TransitionItem>();
			for (int i=0;i<vct.size();++i) {
				TransitionItem eTran = vct.get(i);
				if (Utils.isDebug()) {
					Logger.getLogger(APP_PKG_NAME).log(Level.FINER,"processing transition(" + i + "): " + eTran);
				}
				if (eTran.process(report)) {
					bSuccess = true;
				} else if (!report) {
					tmp.add(eTran);
				}
			}
			if (report) {
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
	private boolean processAttribute(EANAttribute fillSrc, EANAttribute local,Vector<TransitionItem> transVct) {
		boolean beep = false;
		boolean beepdone = false;
		if (local.isEditable()) {
			if (local.hasLock(local.getKey(),EACM.getEACM().getLockMgr().getLockOwner(getProfile()), getProfile())) {
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
					UIManager.getLookAndFeel().provideErrorFeedback(this);
					beepdone = true;
					com.ibm.eacm.ui.UI.showException(this,bre);
				}
			}else{
				//UIManager.getLookAndFeel().provideErrorFeedback(this);
				beep = true;
				Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"user does not have lock for "+local.getKey());
			}
		}else{
			//UIManager.getLookAndFeel().provideErrorFeedback(this);
			beep = true;
			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,local.getKey()+" is not editable");
		}
		if(beepdone){
			beep = false; // ony need one beep
		}
		return beep; // just do it once at end
	}

	/**
	 * getUIPrefKey
	 *
	 * @return
	 */
	public String getUIPrefKey() {
		if (getRowCount() > 0) {
			EntityItem ei = (EntityItem)getRSTable().getRow(0);
			return "HORZ" + ei.getEntityType();
		}
		return "HORZ";
	}

	/*
     accessibility
	 */
	//	 ""		 --> default
	//	 ".ctab" --> cross table
	//	 ".horz" --> horizontal
	//	 ".mtrx" --> matrix
	//	 ".vert" --> vertical

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.BaseTable#getContext()
	 */
	protected String getContext() {
		return ".horz";
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
		} else {
			EANFoundation o =  ((RSTTableModel)getModel()).getRowEAN(mdlrow);
			if (o !=null) {
				return o.getKey();
			}
		}
		return super.getAccessibleRowNameAt(mdlrow);
	}


	/**
	 * hasHiddenAttributes
	 * @return
	 */
	public boolean hasHiddenAttributes() {
		EntityGroup eg = editController.getEntityGroup();
		if (eg != null) {
			int iDispCols = getColumnCount();
			int iTotalCols = eg.getActualColumnListCount();
			return iDispCols < iTotalCols;
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

}
