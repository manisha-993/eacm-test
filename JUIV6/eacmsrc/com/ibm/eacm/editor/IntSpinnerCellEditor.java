//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.table.RSTTableModel;
import com.ibm.eacm.ui.IntSpinner;

/**
 * cell editor for crosstable, sets number of relators when RELATTR is not defined for a MatrixGroup
 * locks are not needed
 * @author Wendy Stimpson
 */
//$Log: IntSpinnerCellEditor.java,v $
//Revision 1.2  2013/10/17 17:06:14  wendy
//prevent scroll back to top after delete
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class IntSpinnerCellEditor extends AbstractCellEditor implements TableCellEditor, PasteEditor
{
	private static final long serialVersionUID = 1L;
	private IntSpinner intSpinner = null;
	private int clickCountToStart =2;
	private int viewRow=-1; 
	private int viewColumn=-1;
	private RSTTable rstTable = null;
	private int preloadKeyValue = -1;
	
	/**
	 * @param value
	 * @param minimum
	 * @param maximum
	 * @param stepSize
	 */
	public IntSpinnerCellEditor(int value, int minimum, int maximum, int stepSize){
		intSpinner = new IntSpinner(value, minimum, maximum, stepSize);
		//make it look like the renderer, left aligned
		((JSpinner.DefaultEditor)intSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
	}
	/**
	 * release memory
	 */
	public void dereference() {
		intSpinner.dereference();
		intSpinner = null;
		rstTable = null;
	}
	/**
	 * provide access to the table for delete key 
	 * @param rsttable
	 * @param row
	 * @param column
	 */
	public void setTable(RSTTable rsttable,int r, int col){
		rstTable = rsttable;
		viewRow = r;
		viewColumn=col;
	}
	/**
	 * called by JTable class to see if it should initiate the editing process
	 * if false, the editor component will not be installed
	 * @see javax.swing.AbstractCellEditor#isCellEditable(java.util.EventObject)
	 */
	public boolean isCellEditable(EventObject anEvent) {
		boolean isok = true;
		boolean isDelete = false;
		if (anEvent instanceof MouseEvent) { 
			isok= ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
		}
		if (anEvent instanceof KeyEvent) { 
			// allow Enter to start the editor
			isok= ((KeyEvent)anEvent).getKeyCode() == KeyEvent.VK_ENTER;
			isDelete = ((KeyEvent)anEvent).getKeyCode() == KeyEvent.VK_DELETE;
			if(Character.isDigit(((KeyEvent)anEvent).getKeyChar())){
				isok=true;
				preloadKeyValue=Character.digit(((KeyEvent)anEvent).getKeyChar(),10);
			}
		}

		if(isDelete && rstTable!=null){
			isok=false; // make sure editor wont open
			rstTable.setValueAt("0", viewRow, viewColumn);
			//rstTable.updateTable(); // refresh any classifications - dont scroll up
			((RSTTableModel)rstTable.getModel()).fireTableRowsUpdated(rstTable.convertRowIndexToModel(viewRow),rstTable.convertColumnIndexToModel(viewColumn));
		}
		return isok;
	}
    
	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value !=null) {
			if (value.toString().length()==0){
				value="0";
			}
			if(preloadKeyValue!=-1){
				intSpinner.setValue(preloadKeyValue);
				preloadKeyValue=-1;
			}else{
				intSpinner.setValue(Integer.parseInt(value.toString()));
			}
		}
		return intSpinner;
	}

    /**
     * return the edited value
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
	public Object getCellEditorValue() {
		return ""+intSpinner.getInt();
	}

	/* (non-Javadoc)
	 *  use editor rules when pasting
	 * @see com.ibm.eacm.editor.PasteEditor#paste(java.lang.Object, boolean)
	 */
	@SuppressWarnings("unchecked")
	public boolean paste(Object pasteObj, boolean editOpen){
		int pasteval = Integer.parseInt(pasteObj.toString());
		Integer val = new Integer(pasteval);
		SpinnerNumberModel snm = (SpinnerNumberModel)intSpinner.getModel();
		if (snm.getMaximum().compareTo(val)>=0 && 
				snm.getMinimum().compareTo(val)<=0 ){
			intSpinner.setValue(pasteval); 
			return true;
		}else{
			//msg80000 = Invalid value "{0}" specified for "{1}"
			com.ibm.eacm.ui.UI.showErrorMessage(intSpinner,
					Utils.getResource("msg80000",pasteObj,"integer"));  
			return false;
		}
	}
}
