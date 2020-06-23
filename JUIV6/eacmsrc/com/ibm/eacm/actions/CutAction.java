//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import com.ibm.eacm.edit.form.FormTable;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.RSTTable;


/*************
* 
* cut to clipboard
* @author Wendy Stimpson
*/
//$Log: CutAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class CutAction extends EACMAction implements PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private RSTTable table = null;
	private FormTable formTable = null;

	public CutAction(RSTTable nt) {
		super(CUT_ACTION,KeyEvent.VK_X, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("cut.gif"));
	
		setTable(nt);
	}
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(TCE_PROPERTY)) {
			setEnabled(true);
		}
	}
	public void setTable(RSTTable nt){
		table = nt;
		formTable = null;
	    setEnabled(true);
	}
	/**
	 * called when formeditor is loaded
	 * @param nt
	 */
	public void setFormTable(FormTable nt){
		formTable = nt;
		table = null;
	    setEnabled(true);
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(table!=null){
			// must be editable, have lock and be a text attribute
			// cant do partial select unless editor is open for the cell
			ok = table.canCut();
		}else if(formTable!=null){
			ok = formTable.canCut();
		}
		return ok; 
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		table = null;
		formTable = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(table !=null){
			table.cut();
		}else if(formTable!=null){
			formTable.cut();
		}
	}

}