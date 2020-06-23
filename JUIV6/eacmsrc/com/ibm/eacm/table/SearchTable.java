//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;


import java.awt.Window;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.EACM;
import com.ibm.eacm.editor.DateCellEditor;
import com.ibm.eacm.editor.FlagCellEditor;
import com.ibm.eacm.editor.TextCellEditor;
import com.ibm.eacm.editor.TimeCellEditor;
import com.ibm.eacm.editor.MultiCellEditor;
import com.ibm.eacm.rend.LabelRenderer;

/******************************************************************************
* This is used for the table in the search editor
* @author Wendy Stimpson
*/
// $Log: SearchTable.java,v $
// Revision 1.2  2013/07/19 17:48:35  wendy
// pass window parent into mf cell editor
//
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//

public class SearchTable extends VerticalTable {
	private static final long serialVersionUID = 1L;

    /**
     * constructor
     */
    public SearchTable(Profile prof, RowSelectableTable _table,String srchkey) {
        super(prof,_table,srchkey);
   
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

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#init()
     */
    protected void init() {
    	super.init();
    	LabelRenderer defaultRenderer = new LabelRenderer();
    	defaultRenderer.setBorderKeys(SELECTED_BORDER_KEY, SELECTED_BORDER_KEY);
    	defaultRenderer.setColorKeys(null, PREF_COLOR_LOCK);

    	// replace the default set in base class - this one will be deref by base class too
    	Object renderer = getDefaultRenderer(Object.class);
    	if(renderer instanceof LabelRenderer){
    		((LabelRenderer)renderer).dereference();
    	}
    	setDefaultRenderer(Object.class, defaultRenderer);
    }
	/**
	 * allow derived classes to provide a different rsttablemodel
	 * @return
	 */
	protected RSTTableModel createTableModel(){ 
		return new SrchTableModel(this);
	}

    /**
     * getCellRenderer
     *
     * @param _r
     * @param _c
     * @return
     */
    public TableCellRenderer getCellRenderer(int _r, int _c) {
        if (_c == 0) {
            return rowHeaderRenderer;
        }

        return getDefaultRenderer(Object.class);
    }

    /**
     * getCellEditor
     *
     * @param _r
     * @param _c
     * @return
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
    			multiFCE = new MultiCellEditor(parent,true);
    		}
    		multiFCE.setTable(this, _r, _c); // needed to handle delete key

    		return multiFCE;
    	}
    	if (o instanceof EANFlagAttribute) {
    		if (flagFCE==null){
    			flagFCE = new FlagCellEditor(true);
    		}
      		flagFCE.setTable(this, _r, _c); // needed to handle delete key
    		return flagFCE;
    	} 
    	if (o instanceof TextAttribute) {
    	    TextAttribute txt = (TextAttribute) o;
            EANMetaAttribute meta = txt.getMetaAttribute();
            if (meta.isDate()) {
                if(dateTCE==null){
                	dateTCE = new DateCellEditor(true);
                }
                dateTCE.setTable(this, _r, _c); // needed to handle delete key
                return dateTCE;
            } else if (meta.isTime()) {
            	if(timeTCE==null){
            		timeTCE = new TimeCellEditor(getProfile(),true);
            	}
            	timeTCE.setTable(this, _r, _c); // needed to handle delete key
            	return timeTCE;
            } else {
        		if (textTCE==null){
        			textTCE = new TextCellEditor(true);
        		}
        		textTCE.setTable(this, _r, _c); // needed to handle delete key
        		return textTCE;
            }

    	}
    	return null;
    }

    /**
     * rollback - reset all changed values
     */
    public void rollback() {
        ((SrchTableModel)getModel()).rollback();

      	// only different values will actually fire an event - make sure actions listening
		// reflect the correct state
		firePropertyChange(DATACHANGE_PROPERTY, true, false);
		
        resizeCells();
    }
    /**
     * getUIPrefKey
     *
     * @return
     */
    public String getUIPrefKey() { 
         return "SRCH"+super.getUIPrefKey();
    }
}

