//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.table;


import java.awt.event.*;
import java.util.EventObject;

import javax.swing.table.TableCellEditor;

import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.FlagCellEditor;
import com.ibm.eacm.editor.TextCellEditor;
import com.ibm.eacm.mtrx.MatrixActionBase;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.Routines;

import COM.ibm.eannounce.objects.*;


/**
 * matrix crosstable for relators with attributes specified using RELATTR for matrix action
 * SG	Action/Attribute	MTRFEATURE	RELATTR	FEATURECABLE	QTY	
 * SG	Action/Attribute	MTRFEATURE	RELATTR	PRODSTRUCT	ORDERCODE	
 * @author Wendy Stimpson
 */
//$Log: RelAttrCrossTable.java,v $
//Revision 1.2  2013/10/29 21:15:15  wendy
//IN4468974 Can not save data when creating new product structure using Manage Product Structure
//
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class RelAttrCrossTable extends CrossTable  
{ 
	private static final long serialVersionUID = 1L;
    private TextCellEditor textTCE = null;
    private FlagCellEditor flagFCE = null;
    private MouseAdapter mouseL = null;

	/**
	 * allow derived classes to provide a different rsttablemodel
	 * @return
	 */
	protected RSTTableModel createTableModel(){ 
		return new MtrxTableModel(this,false,64);
	}

	/**
	 * crossTable for use with matrix when relators have attributes
	 */
	public RelAttrCrossTable(MatrixGroup mg, MatrixActionBase matab) {
		super(mg,matab);
		mouseL = new MouseAdapter() {    
			public void mouseClicked(MouseEvent me) {
				// jcombobox doesnt select properly 
				// still ugly but clears up
				repaint();
			}
		};
		addMouseListener(mouseL);
	}
	
	/**
	 * release memory, only RELATTR tables get locks
	 */
	public void dereference() {
		unlockTable();
        if(textTCE != null){
        	textTCE.dereference();
        	textTCE = null;
        }
        
        if(flagFCE != null){
        	flagFCE.dereference();
        	flagFCE = null;
        }
        
        removeMouseListener(mouseL);
        mouseL=null;
        
		super.dereference();
	}
	
    /* (non-Javadoc)
     * any edit that gets this far is valid
     * @see com.ibm.eacm.table.CrossTable#validCellChange(java.lang.Object, int, int)
     */
    protected boolean validCellChange(Object aValue, int row, int column) {
    	return true;
    }
    
    /* (non-Javadoc)
     * provide a way to get celllocks on an attribute that isnt editable but others are
     * @see com.ibm.eacm.table.RSTTable2#editCellAt(int, int, java.util.EventObject)
     */
    public boolean editCellAt(int row, int column, EventObject e){
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
    		if(AttrCellEditor.canLaunchEditorForEvent(e)){
    			if(getCellLock(row, column)){
    				repaint();
    			}
    		}
    	}
    	
    	// always install an editor for some types so user can view like long, xml and blob
    	return super.editCellAt(row, column, e);
    }
    
    /**
     * getCellEditor
     *
     * @param r
     * @param c
     * @return
     */
    public TableCellEditor getCellEditor(int r, int c) {
    	Object o = getValueAt(r, c);
    	if (o instanceof SingleFlagAttribute) {
    		if (flagFCE==null){
    			flagFCE = new FlagCellEditor();
    		}
    		flagFCE.setTable(this, r, c); // needed to check locks and handle delete key
    		flagFCE.addActionListener(this); //IN4468974
    		return flagFCE;
    	} else if (o instanceof TextAttribute) {
    		if (textTCE==null){
    			textTCE = new TextCellEditor();
    		}

    		textTCE.setTable(this, r, c); // needed to check locks and handle delete key
    		textTCE.addDocumentListener(this);
    		return textTCE;
    	}
    	return null;
    }
    
    /**
     * getFillComponent used in dialog as editor 
     * @param r
     * @param c
     * @return
     */
    public AttrCellEditor getFillComponent(int r, int c) {
        Object o = getValueAt(r, c);
        if (o instanceof SingleFlagAttribute) {
            if (flagFCE==null){
            	flagFCE = new FlagCellEditor();
            }
            flagFCE.setAttribute((SingleFlagAttribute) o);
            return flagFCE;
        } else if (o instanceof TextAttribute) {
            if (textTCE==null){
                textTCE = new TextCellEditor();
            }
            textTCE.setAttribute((TextAttribute) o);
            return textTCE;
        }
        return null;
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
				if(getCellLock(r, cols[c])){
					setValueAt(o, r, cols[c]);
				}
			}
		}
//		setUpdateArmed(true);
		//refreshTable(MATRIX, true);
		//repaint();
		
		resizeAndRepaint();
	}
	/**
	 * update selected rows and all columns with the specified value
	 * @param o
	 */
	public void verticalAdjust(Object o) {
		int cc = getColumnCount();
		int[] rows = getSelectedRows();
		for (int r = 0; r < rows.length; ++r) {
			for (int c = 1; c < cc; ++c) { // col0 is info only
				if(getCellLock(rows[r], c)){
					setValueAt(o, rows[r], c);
				}
			}
		}
		
		resizeAndRepaint();
	}
    /**
     * getInformation
     *
     * @return
     */
    public String getInformation() {
    	int r = getSelectedRow();
    	int c = getSelectedColumn();

    	Object obj = getValueAt(r, c);
    	if (obj instanceof EANAttribute){
    		return Routines.getInformation((EANAttribute)obj);
    	}

    	return Utils.getResource("nia"); 
    }
	/**
	 * get help text for the selected cell
	 * @return
	 */
	public String getHelpText() {
		int r = getSelectedRow();
		int c = getSelectedColumn();
		String help = null;
    	Object obj = getValueAt(r, c);
    	if (obj instanceof EANAttribute){
    		help = ((EANAttribute)obj).getHelp(EANAttribute.VALUE);
    	}

		return help;
	}
    /**
     * resetSelected
     */
    public void resetSelected() {
        int rows[] = getSelectedRows();
        int cols[] = getSelectedColumns();
        for (int r = 0; r < rows.length; ++r) {
            for (int c = 0; c < cols.length; ++c) {
                setValueAt(null, convertRowIndexToModel(rows[r]), convertColumnIndexToModel(cols[c]));
            }
        }
    }
}
