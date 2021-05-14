//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.table;


import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.ListSelectionModel;

import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.Profile;


import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.rend.LabelRenderer;

import com.ibm.eacm.editor.DateCellEditor;
import com.ibm.eacm.editor.FlagCellEditor;
import com.ibm.eacm.editor.MultiEditor;
import com.ibm.eacm.editor.TextCellEditor;
import com.ibm.eacm.editor.TimeCellEditor;
import com.ibm.eacm.editor.MultiCellEditor;

/******************************************************************************
 * This is used as a base table for search and vertical edit
 * @author Wendy Stimpson
 */
//$Log: VerticalTable.java,v $
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//

public class VerticalTable extends RSTTable {
	private static final long serialVersionUID = 1L;
	protected LabelRenderer rowHeaderRenderer = null;

	protected TextCellEditor textTCE = null;
	protected FlagCellEditor flagFCE = null;
	protected DateCellEditor dateTCE = null;
	protected TimeCellEditor timeTCE = null;
	protected MultiCellEditor multiFCE = null;
	private String uidKey = null;

	/**
	 * constructor
	 */
	public VerticalTable(Profile prof, RowSelectableTable table,String actionkey) {
		super(table, prof);

		uidKey = actionkey;
	}
	/* (non-Javadoc)
	 * will stop tab from going into header col  also prevent user from selecting header column with mouse
	 * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
	 */
	public void changeSelection(int row, int col, boolean toggle, boolean extend) {
		super.changeSelection(row, convertColumnIndexToModel(1), toggle, extend); // indexes are column model
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.BaseTable#init()
	 */
	protected void init() {
		super.init();

		setSurrendersFocusOnKeystroke(true); // for accessibility, let user edit using keystrokes

		rowHeaderRenderer = new LabelRenderer();
		rowHeaderRenderer.setFocusBorderKeys(LOWERED_BORDER_KEY, RAISED_BORDER_KEY);
		rowHeaderRenderer.setSelectionColorKeys(null, ROW_REND_COLOR);
		rowHeaderRenderer.setColorKeys(null, ROW_REND_COLOR);

		getTableHeader().setReorderingAllowed(false);
		// override base class settings
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);;
	}

	/**
	 * get the accessibility resource key
	 * @return
	 */
	protected String getAccessibilityKey() {
		return "accessible.vertTable";
	}
	/* (non-Javadoc)
	 * @see javax.swing.JTable#getValueAt(int, int)
	 */
	public Object getValueAt(int r, int c) {
		return getValueAt(r, c,true); // get all flag values
	}

	/**
	 * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
	 */
	public String getToolTipText(MouseEvent me) {
		return null;
	}

	/**
	 * need to override this because getValueAt doesnt return the attribute
	 * cant be in RSTTable2 because RelAttrCrossTable needs base java behavior
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

	/**
	 * getCellRenderer
	 *
	 * @param r
	 * @param c
	 * @return
	 */
	public TableCellRenderer getCellRenderer(int r, int c) {
		if (c == 0) {
			return rowHeaderRenderer;
		}

		return getDefaultRenderer(Object.class);
	}

	/**
	 * dereference
	 *
	 */
	public void dereference() {
		uidKey = null;
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

		if (rowHeaderRenderer !=null) {
			rowHeaderRenderer.dereference();
			rowHeaderRenderer = null;
		}

		super.dereference();
	}

	/**
	 * get attribute Information
	 *
	 * @return
	 */
	public String getInformation() {
		int r = getSelectedRow();
		int c = 1; //must be column 1
		if (isValidCell(r, c)) {
			EANAttribute att = getAttribute(r, c);
			return Routines.getInformation(att);
		}
		return Utils.getResource("nia");
	}

	/**
	 * getUIPrefKey
	 *
	 * @return
	 */
	public String getUIPrefKey() {
		return uidKey;
	}

	/* (non-Javadoc)
	 * resize height for changed flags or multiline values
	 * @see javax.swing.JTable#editingStopped(javax.swing.event.ChangeEvent)
	 */
	public void editingStopped(ChangeEvent e) {
		Object value = null;
		TableCellEditor editor = getCellEditor();
		if (editor != null) {
			value = editor.getCellEditorValue();
		}

		super.editingStopped(e);
		if(value!=null && (value instanceof MetaFlag[]
		                                             || Routines.addLineWraps(value.toString(), 80).indexOf(NEWLINE)!=-1)) { // multiline text
			resizeCells();
		}
	}

	/* (non-Javadoc)
	 * used when resizecells()
	 * @see com.ibm.eacm.table.BaseTable#getColWidth(java.awt.FontMetrics, java.lang.Object)
	 */
	protected int getColWidth(FontMetrics fm,Object o) {
		String str = "";
		if (o != null) {
			// must split it like the cellrenderer will
			str = Routines.addLineWraps(o.toString(), 80);
			if(str.length()>0 && str.indexOf(NEWLINE)!=-1){
				// multiflag will have newlines between each flag value, find longest and use that
				String valArray[] = Routines.getStringArray(str, NEWLINE);
				str=valArray[0];
				for (int i=0; i<valArray.length; i++){
					if(valArray[i].length()>str.length()){
						str = valArray[i];
					}
				}
			}
		}

		return Math.max(MIN_BLOB_WIDTH,fm.stringWidth(str));
	}
	/**
	 * calculate height needed for this row
	 * @param fm
	 * @param baseHeight
	 * @param str - could be multiline in some tables
	 * @return
	 */
	protected int getRowHeight(FontMetrics fm, int baseHeight, String str){
		str = Routines.addLineWraps(str, 80);
		return fm.getHeight() * Routines.getCharacterCount(str,NEWLINE);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.BaseTable#getContext()
	 */
	protected String getContext() {
		return ".vert";
	}

	/**
	 * getAccessibleValueAt
	 *
	 * @param row
	 * @param col
	 * @return
	 */
	protected Object getAccessibleValueAt(int row, int col) {
		return getValueAt(row, 1);
	}

	/**
	 * getAccessibleRowNameAt
	 *
	 * @param row
	 * @return
	 */
	protected String getAccessibleRowNameAt(int row) {
		Object o = getValueAt(row, 0);
		if (o != null) {
			return o.toString();
		}
		return "";
	}
}

