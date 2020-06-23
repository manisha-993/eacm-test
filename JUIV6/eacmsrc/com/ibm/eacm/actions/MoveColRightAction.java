//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.ibm.eacm.table.CrossTable;
import com.ibm.eacm.table.MtrxTable;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.table.VertTable;

/*************
 * 
 * move column to the left 
 * @author Wendy Stimpson
 */
//$Log: MoveColRightAction.java,v $
//Revision 1.2  2015/04/07 22:18:23  stimpsow
//correct keybd accelerators
//
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class MoveColRightAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private RSTTable table = null;

	public MoveColRightAction(RSTTable nt) {
		super(MOVECOL_RIGHT_ACTION, KeyEvent.VK_RIGHT, Event.CTRL_MASK,true);
		setTable(nt);
	}
	/**
	 * @param nt
	 */
	public void setTable(RSTTable nt){
		table = nt;
		setEnabled(true);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;

		if (table!=null && !(table instanceof VertTable)){ // vertical editor cant move columns
			int min = table.getColumnModel().getSelectionModel().getMinSelectionIndex();
			int max = table.getColumnModel().getSelectionModel().getMaxSelectionIndex();
			if (min != -1 && min==max){ // one cell selected
				int columnCount = table.getColumnCount();
				if (max+2==columnCount && // if 2 col tbl, and last col.width==0, selected col would move past 0 width col
						(table instanceof CrossTable || 
								table instanceof MtrxTable)){ // column0 has 0 width, it holds the row headers for easy sorting
					if(table.getColumnModel().getColumn(columnCount-1).getWidth()==0){
						max = columnCount;  // skip the 'hidden' column
					}
				}

				boolean hasselected = table.getSelectedRowCount() > 0;
				ok = hasselected && max<columnCount-1;
			}
		}
		return ok;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		table = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		table.moveColumn(false);
	} 
}