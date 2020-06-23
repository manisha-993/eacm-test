//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.Component;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import com.ibm.eacm.edit.form.FormTable;
import com.ibm.eacm.table.CrossTable;
import com.ibm.eacm.table.MtrxTable;
import com.ibm.eacm.table.RSTTable;


/*********************************************************************
 * This is used for show entity data request
 * @author Wendy Stimpson
 */
//$Log: EntityDataAction.java,v $
//Revision 1.3  2013/09/13 18:22:51  wendy
//show dialog over parent frame
//
//Revision 1.2  2013/02/12 21:29:59  wendy
//move dialog location
//
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class EntityDataAction extends EACMAction
{
	private RSTTable curTab = null;
	private FormTable formTable = null;
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * @param _tab
	 */
	public EntityDataAction(RSTTable _tab) { //table was null in nav when try to create
		super(ENTITYDATA_ACTION,KeyEvent.VK_F12, Event.CTRL_MASK);
		setTable(_tab);
	}
	/**
	 * called when an RSTTable is loaded
	 * @param nt
	 */
	public void setTable(RSTTable nt){
		curTab = nt;
		formTable = null;
	    setEnabled(true);
	}
	/**
	 * called when formeditor is loaded
	 * @param nt
	 */
	public void setFormTable(FormTable nt){
		formTable = nt;
		curTab = null;
	    setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(curTab!=null){
			ok = curTab.getSelectedRowCount()==1;
			if (ok && (curTab instanceof MtrxTable || curTab instanceof CrossTable)){
				ok = curTab.getModel().getColumnCount()>1; // col0 is hidden
			}
		}else {
			ok = formTable != null;
		}
		return ok; 
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		curTab = null;
		formTable = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String info = "";
		Component comp = null;
		if(formTable !=null){
			info = formTable.getInformation();
			comp = formTable;
		}else{
			info = curTab.getInformation();
			//comp = curTab; put dialog over jui
			comp = curTab.getTopLevelAncestor();
		}
		com.ibm.eacm.ui.UI.showMessage(comp,"information-title", 
				JOptionPane.INFORMATION_MESSAGE, "information-acc", info);
	}
}

