//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTable;

import com.ibm.eacm.table.CrossTable;
import com.ibm.eacm.table.MtrxTable;

/*************
 * 
 * select all 
 * @author Wendy Stimpson
 */
// $Log: SelectAllAction.java,v $
// Revision 1.1  2012/09/27 19:39:17  wendy
// Initial code
//
public class SelectAllAction extends EACMAction  
{
	private JTable table = null;
	private static final long serialVersionUID = 1L;
	public SelectAllAction(JTable nt) {
        super(SELECTALL_ACTION,KeyEvent.VK_A, Event.CTRL_MASK);
        setTable(nt);
	}

	public void setTable(JTable nt){
	    table = nt;
	    setEnabled(true);
	}
	public void setEnabled(boolean newValue) {
		boolean ok = false;
		if(table!=null){
			ok = table.getRowCount() > 0;
			if (ok && (table instanceof MtrxTable || table instanceof CrossTable)){
				ok = table.getModel().getColumnCount()>1; // col0 is hidden
			}
		}
		super.setEnabled(newValue && ok);
	}
	public void dereference(){
		super.dereference();
		table = null;
	}
	public void actionPerformed(ActionEvent e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				table.selectAll();
			}
		});
	} 
}