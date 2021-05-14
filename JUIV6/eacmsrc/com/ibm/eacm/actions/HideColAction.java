//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.CrossTable;
import com.ibm.eacm.table.MtrxTable;
import com.ibm.eacm.table.VertTable;


/*************
 * 
 * hide column
 *		
 * @author Wendy Stimpson
 */
//$Log: HideColAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class HideColAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private BaseTable table = null;
	private UnhideColAction unhideAction = null;

	/**
	 * @param nt
	 */
	public HideColAction(BaseTable nt,UnhideColAction unhide) {
		super(HIDECOL_ACTION);
		unhideAction = unhide;
		setTable(nt);
	}

	/**
	 * @param nt
	 */
	public void setTable(BaseTable nt){
		table = nt;
		setMenuText();
	    setEnabled(true);
	}

	private void setMenuText(){
		if (table instanceof VertTable){
			putValue(Action.NAME, Utils.getResource(HIDEROW_ACTION)); 
			String value = Utils.getToolTip(HIDEROW_ACTION);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}
		}else{
			putValue(Action.NAME, Utils.getResource(HIDECOL_ACTION));
			String value = Utils.getToolTip(HIDECOL_ACTION);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(table instanceof VertTable){ // vertical editor works on rows
			ok = table.getSelectedRowCount() > 0;
		}else if(table!=null){
			ok = table.getSelectedColumnCount() > 0;
			if (ok && (table instanceof MtrxTable || table instanceof CrossTable)){
				ok = table.getColumnCount()>2; // dont hide the last visible column, crosstable must have something
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
		table.showHide(true);
		unhideAction.setEnabled(true); // let it check for hidden cols
	} 
}