//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.edit;

import javax.swing.event.CellEditorListener;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.ibm.eacm.editor.AttrCellEditor;
import com.wallstreetwise.app.jspell.gui.JSpellSwingTextComponentWrapper;

/*********************************************************************
* This is used to notify listeners when spell check makes a change
* @author Wendy Stimpson
*/
//$Log: SpellCheckWrapper.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class SpellCheckWrapper extends JSpellSwingTextComponentWrapper {
	private AttrCellEditor cellEditor = null;
	private DocumentListener docListener = null;
	
	/**
	 * constructor
	 * @param ace
	 */
	public SpellCheckWrapper(AttrCellEditor ace){
		super((JTextComponent)ace.getComponent());
		cellEditor = ace;
	}
	
	/**
	 * provide access to the text document to add and remove listeners
	 * @return
	 */
	protected void addDocumentListener(DocumentListener dl){
		docListener = dl;
		((JTextComponent)cellEditor.getComponent()).getDocument().addDocumentListener(docListener);
	}
	
	/**
	 * spell check is starting, remove the doc listeners
	 */
	protected void spellCheckStarted(){
		((JTextComponent)cellEditor.getComponent()).getDocument().removeDocumentListener(docListener);
	}
	/**
	 *  spell check is ending, restore the doc listeners
	 */
	protected void spellCheckEnded(){
		((JTextComponent)cellEditor.getComponent()).getDocument().addDocumentListener(docListener);
	}
	
	/* (non-Javadoc)
	 * @see com.wallstreetwise.app.jspell.gui.JSpellSwingTextComponentWrapper#setText(java.lang.String)
	 */
	public void setText(String string){
		super.setText(string);
		// notify listeners contents have changed - only works with form editor because jtable has removed
		// listener when cell lost focus
		cellEditor.stopCellEditing();
		
		CellEditorListener[]listeners = cellEditor.getCellEditorListeners();
		if(listeners.length==0){
			// this is running in a JTable, so must go directly to the table
			cellEditor.updateSpellCheckedCell();
		}
	}
	
	/**
	 * release memory
	 */
	protected void dereference(){
		((JTextComponent)cellEditor.getComponent()).getDocument().removeDocumentListener(docListener);
		docListener = null;
		cellEditor = null;
	}
}
