//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.CrossTable;
import com.ibm.eacm.table.MtrxTable;


/*************
 * 
 * invert selection 
 * @author Wendy Stimpson
 */
//$Log: SelectInvertAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class SelectInvertAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private BaseTable table = null;
	
	public SelectInvertAction(BaseTable nt) {
        super(SELECTINV_ACTION, KeyEvent.VK_I, Event.CTRL_MASK);
        setTable(nt);
	}
	public void setTable(BaseTable nt){
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
		int rowSelStart = -1;
		int rowSelEnd = -1;
		for (int i = 0; i < table.getRowCount(); ++i) {
			if (table.isRowSelected(i)) {
				if (rowSelStart==-1){
					rowSelStart = i;
					rowSelEnd = i;
				}else{
					if (rowSelEnd+1 == i){
						rowSelEnd = i;
					} 
				}
			
				//table.removeRowSelectionInterval(i, i); this causes table to update for each change
			} else {
				if (rowSelStart!=-1){
					// remove the set of continuous selected items
					table.removeRowSelectionInterval(rowSelStart, rowSelEnd);
					rowSelEnd = -1;
					rowSelStart = -1;
				}
				table.addRowSelectionInterval(i, i);
			}
		}
		if (rowSelStart!=-1){
			// remove the set of continuous selected items
			table.removeRowSelectionInterval(rowSelStart, rowSelEnd);
		}
	} 
}