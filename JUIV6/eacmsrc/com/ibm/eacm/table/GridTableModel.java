//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;

/******************************************************************************
* This is used for the gridtable in the grid editor
* @author Wendy Stimpson
*/
// $Log: GridTableModel.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class GridTableModel extends RSTTableModel { 
	private static final long serialVersionUID = 1L;

	/**
	 * create a grid tablemodel
	 * 
	 * @param table
	 * @param maxlen
	 */
	public GridTableModel(RSTTable table){
		super(table);
	}
	
	/*protected boolean addRow(){
		boolean added = getRSTable().addRow();
		this.refresh(); // update rst references with new row
	    return added;
	}
	
	/**
	 * used for VEEdit adds
	 * @param key
	 * @return
	 * /
	protected boolean addRow(String key){
		boolean added =getRSTable().addRow(key);
		this.refresh();
	    return added;
	}*/
}
