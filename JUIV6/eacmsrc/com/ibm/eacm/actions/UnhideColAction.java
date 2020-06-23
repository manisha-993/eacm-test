//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.MtrxTable;
import com.ibm.eacm.table.VertTable;

/*************
* 
* unhide column
* @author Wendy Stimpson
*/
//$Log: UnhideColAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class UnhideColAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private BaseTable table = null;

	/**
	 * @param nt
	 */
	public UnhideColAction(BaseTable nt) {
		super(UNHIDECOL_ACTION);
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
			putValue(Action.NAME, Utils.getResource(UNHIDEROW_ACTION)); 
			String value = Utils.getToolTip(UNHIDEROW_ACTION);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}
		}else{
			putValue(Action.NAME, Utils.getResource(UNHIDECOL_ACTION));
			String value = Utils.getToolTip(UNHIDECOL_ACTION);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}
		}	
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean hiddencols = false;
		if (table!=null){
			hiddencols = table.hasHiddenCols();
			if (hiddencols && table instanceof MtrxTable){
				// check if only hidden in metacolorder
				hiddencols = table.getColumnCount()!=table.getModel().getColumnCount();
			}
		}

		return hiddencols;
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
		table.showHide(false);
		setEnabled(false);
	} 
}