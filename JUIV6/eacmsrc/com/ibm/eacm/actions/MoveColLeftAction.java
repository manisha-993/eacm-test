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
import com.ibm.eacm.table.VertTable;


/*************
* 
* move column to the left 
* @author Wendy Stimpson
*/
//$Log: MoveColLeftAction.java,v $
//Revision 1.2  2015/04/07 22:18:23  stimpsow
//correct keybd accelerators
//
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class MoveColLeftAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private BaseTable table = null;
	public MoveColLeftAction(BaseTable nt) {
		super(MOVECOL_LEFT_ACTION, KeyEvent.VK_LEFT, Event.CTRL_MASK,true);
		setTable(nt);
	}
	public void setTable(BaseTable nt){
		table = nt;
		setEnabled(true);
	}
	public void setEnabled(boolean newValue) {
		boolean ok = false;
		if(newValue){
			if (table!=null && !(table instanceof VertTable)){ // vertical editor cant move columns
				int min = table.getColumnModel().getSelectionModel().getMinSelectionIndex();
				int max = table.getColumnModel().getSelectionModel().getMaxSelectionIndex();
				if (min != -1 && min==max){ // one cell selected
					boolean hasselected =table.getSelectedRow() >= 0;
					if (min == 1 && (table instanceof CrossTable || 
						table instanceof MtrxTable)){ // column0 has 0 width, it holds the row headers for easy sorting
						if(table.getColumnModel().getColumn(0).getWidth()==0){
							min = -1;  // skip the 'hidden' column
						}
					}

					ok = hasselected && min>0;	
				}	
			}
		}
		super.setEnabled(newValue && ok);
	}
	

	public void dereference(){
		super.dereference();
		table = null;
	}
	public void actionPerformed(ActionEvent e) {
		table.moveColumn(true);
	} 
}