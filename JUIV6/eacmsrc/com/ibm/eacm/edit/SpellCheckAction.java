//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
* This is used to execute spellcheck on the selected records 
* @author Wendy Stimpson
*/
//$Log: SpellCheckAction.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class SpellCheckAction extends EACMAction implements PropertyChangeListener
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * @param ed
	 */
	public SpellCheckAction(EditController ed) {
		super(SPELLCHK_ACTION);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("spell.gif"));
		editCtrl = ed;
		super.setEnabled(false);
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(EACM.getEACM().getDictionary()!=null && editCtrl.getCurrentEditor()!=null){
			ok = editCtrl.getCurrentEditor().isSpellCheckable();
		}

		return ok;
	}
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(DATACHANGE_PROPERTY)) {
			setEnabled(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//prevent message to user about requiring spellcheck
		final SpellCheckHandler spchk = editCtrl.getCurrentEditor().getSpellCheckHandler();
		final boolean checkReq = spchk.spellCheckRequired();
		spchk.setSpellCheckRequired(false);
		if (editCtrl.getCurrentEditor().canStopEditing()) {
			spchk.setSpellCheckRequired(checkReq);
			spchk.check(true); 
		}else{
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//display problem to user
					editCtrl.getCurrentEditor().stopCellEditing();
					spchk.setSpellCheckRequired(checkReq);
				}
			});
		}
	}

}

