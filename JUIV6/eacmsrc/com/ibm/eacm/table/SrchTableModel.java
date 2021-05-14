//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;

/******************************************************************************
* This is used for the tablemodel in the search editor
* @author Wendy Stimpson
*/
// $Log: SrchTableModel.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class SrchTableModel extends RSTTableModel { 
	private static final long serialVersionUID = 1L;

	/**
	 * create a search tablemodel that is always editable
	 * 
	 * @param table
	 * @param maxlen
	 */
	public SrchTableModel(RSTTable table){
		super(table);
	}
	
    /**
     * rollback - reset all changed values
     * this changes everything to default values.. even if user doesnt have the lock
     * only use this from search
     */
    protected void rollback() {
    	getRSTable().rollback(); 
    	updatedModel();
    }
    
	public boolean isCellEditable(int _r, int _c) {
		return true;
	}
}
