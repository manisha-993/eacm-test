//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.edit.SpellCheckHandler;
import com.ibm.eacm.edit.SpellCheckWrapper;
import com.ibm.eacm.edit.form.FormCellPanel;
import com.wallstreetwise.app.jspell.domain.JSpellParser;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.LongTextAttribute;

/**
 * this class is used to display a long text editor in a dialog
 * @author Wendy Stimpson
 */
//$Log: LongCellEditor.java,v $
//Revision 1.2  2013/08/14 16:53:21  wendy
//paste has cell listener to support type after paste
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class LongCellEditor extends AttrCellEditor 
{
	private static final long serialVersionUID = 1L;
    
	private LongEditor longEditor = null;
	private SpellCheckHandler spellCheckHandler = null;
	private DocumentListener docListener = null;
	
	//===============added for using in a form
	public FormCellPanel getFormCellPanel(){// added for formeditor
		if(longEditor!=null){
			return longEditor.getFormCellPanel();
		}else{
			return null;
		}
	}

	public void setForeground(Color fg) {
		longEditor.getEditorComponent().setForeground(fg);
	}
	public void setFont(Font font) {
		longEditor.getEditorComponent().setFont(font);
	}
    public void setOpaque(boolean isOpaque) {
    	longEditor.getEditorComponent().setOpaque(isOpaque);
    }
	//===============
	
	/**
	 * constructor for form edit
	 */
	public LongCellEditor(FormCellPanel f){
		super(false);
		longEditor = new LongEditor(this,f);
		// create spellcheckhandler
		JSpellParser sp = new JSpellParser(EACM.getEACM().getDictionary());
		spellCheckHandler = new SpellCheckHandler(sp,new SpellCheckWrapper(this));
	}
	
	/**
	 * constructor for vertical and grid edit
	 */
	public LongCellEditor(){
		super(false);
		longEditor = new LongEditor(this);
		// create spellcheckhandler
		JSpellParser sp = new JSpellParser(EACM.getEACM().getDictionary());
		spellCheckHandler = new SpellCheckHandler(sp,new SpellCheckWrapper(this));
	}
	
	public SpellCheckHandler getSpellCheckHandler(){
		return spellCheckHandler;
	}

	/**
	 * check if updates were valid to this attribute
	 * @param showmsg
	 * @return
	 */
	private boolean validateEdit(boolean showmsg){
     	// check any meta length rules and display error dialog
       	boolean validEdit = checkLength(longEditor.getEditorValue().toString(),showmsg);
       	if(validEdit){
       		if(hasNewChanges() && spellCheckHandler.isRequired() && !spellCheckHandler.getBusy()){
       			// execute spellchecking
       			spellCheckHandler.check(false);
       		}
       		  		
       		return true; 
       	}

    	return false;	
	}
    /**
     * called if the user clicks on a different cell, should hide the dialog here
     * returning true tells the table to use the partially edited value, that it is valid
     * @see javax.swing.AbstractCellEditor#stopCellEditing()
     */
	public boolean stopCellEditing() { 
		if(!validateEdit(true)){
			return false;
		}

		return super.stopCellEditing(); // this fires the event to all listeners
	}
    
    /**
     * get around focus issue with losing focus to longcellpanel
     * dont put up the error dialog until after focus is transferred
     * @return
     */
    public boolean canStopCellEditing(){
     	boolean valid = checkLength(longEditor.getEditorValue().toString(),false);
    	if(valid){
     	   	if(hasNewChanges() && spellCheckHandler.isRequired() && !spellCheckHandler.getBusy()){
         		valid = false;
         	}
    	}
    	return valid;
    }
	/**
	 * spell check is starting, remove the doc listeners
	 */
	public void spellCheckStarted(){
		if(docListener != null){
			((JTextComponent)getComponent()).getDocument().removeDocumentListener(docListener);
		}	
	}
	/**
	 *  spell check is ending, restore the doc listeners
	 */
	public void spellCheckEnded(){
		if(docListener != null){
			((JTextComponent)getComponent()).getDocument().addDocumentListener(docListener);
		}	
	}

	/**
	 * get the item selected - used in vert and grid edit
	 * @return
	 */
	public Object getSelection() { 
		String selection = longEditor.getSelectedText(); 
		String attrval = getAttribute().toString();
		//if user has not hit 'ok' the contents are not in the attribute, so copy will not have the value to paste
		if(attrval.equals(selection)){
			return getAttribute();
		}
		
		return selection;
	}
	/**
	 * use editor rules when pasting
	 * @param pasteObj  
	 * @return
	 */
	public boolean paste(Object pasteObj,boolean editOpen){
		StringBuilder sb = new StringBuilder();
		JTextComponent textComponent = (JTextComponent)getComponent();
		String data = pasteObj.toString();
		int selstart = 0;
		String curtext = textComponent.getText();
		if(curtext ==null){
			curtext = "";
		}
		
		if(editOpen){
			// is the entire string selected?
			selstart = textComponent.getSelectionStart();
			int selend = textComponent.getSelectionEnd();

			// build a new string representing the text after paste is done
			if(selstart!=0){
				sb.append(curtext.substring(0, selstart));
			}
			sb.append(data);
			if(selend!=curtext.length()){
				sb.append(curtext.substring(selend,curtext.length()));
			}
		}else{
			selstart = curtext.length();
			// if editor was not open, append to the entire contents
			sb.append(curtext+data);
		}
		
		// check to see if any errors would be flagged when inserting into the doc
		boolean textok = charValidation("",sb.toString());
		if(textok){
			textComponent.setText(sb.toString());
			selstart+=data.length();
			if(selstart<=sb.toString().length()){
				if(selstart>textComponent.getText().length()){
					selstart = textComponent.getText().length();
				}
				textComponent.setCaretPosition(selstart);
			}

			if(!editOpen){
				longEditor.setEditable(true);
				// this causes this to be executed on the event dispatch thread
				// after paste returns - otherwise all actions remain disabled, cant paste again
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						longEditor.showPopup();
					}
				});
			}
		}
		
		return textok; 
	}
	//--- needed for copy/paste support
    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * If more than one binding exists for the key, only the first valid one found is used. 
     * Input maps are checked in this order:
     * 1.The focused component's WHEN_FOCUSED input map.
     * 2.The focused component's WHEN_ANCESTOR_OF_FOCUSED_COMPONENT input map.
     * 3.The WHEN_ANCESTOR_OF_FOCUSED_COMPONENT input maps of the focused component's parent, and then its 
     * parent's parent, and so on, continuing up the containment hierarchy. Note: Input maps for disabled 
     * components are skipped.
     * 4.The WHEN_IN_FOCUSED_WINDOW input maps of all the enabled components in the focused window are searched. 
     * Because the order of searching the components is unpredictable, avoid duplicate WHEN_IN_FOCUSED_WINDOW bindings!
     * with standard keybindings
     * @param act
     * @param keystroke
     */
    public void registerEACMAction(EACMAction act, KeyStroke keystroke){
    	// must get to WHEN_FOCUSED input map too for textareas 
       	((JTextArea)getComponent()).getInputMap().put(keystroke, act.getActionKey());
        ((JTextArea)getComponent()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystroke, act.getActionKey());
    	((JTextArea)getComponent()).getActionMap().put(act.getActionKey(), act);		
    }
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keystroke
     */
    public void unregisterEACMAction(EACMAction act,KeyStroke keystroke){
       	((JTextArea)getComponent()).getInputMap().remove(keystroke);
      	((JTextArea)getComponent()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(keystroke);
    	((JTextArea)getComponent()).getActionMap().remove(act.getActionKey());
    }

	/**
	 * does this cell have the lock - need to override lock check for editors that can be view only
	 * @return
	 */
	protected boolean isCellLocked(){
		return true;
	}

	/**
     * called after the editor component is installed, initiate edit here, like show a dialog
     * not sure if this is still called, from JTable.editCellAt()
     * 					note that as of Java 2 platform v1.2, the call to
     *                  <code>shouldSelectCell</code> is no longer made
     * use it to pass the keybd event to the editor..
     * at com.ibm.eacm.editor.FlagCellEditor.shouldSelectCell(FlagCellEditor.java:71)
	at javax.swing.plaf.basic.BasicTableUI$Handler.adjustSelection(BasicTableUI.java:1101)
	at javax.swing.plaf.basic.BasicTableUI$Handler.mousePressed(BasicTableUI.java:1025)
     * @see javax.swing.AbstractCellEditor#shouldSelectCell(java.util.EventObject)
     */
    public boolean shouldSelectCell(EventObject anEvent) { 
  		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				longEditor.setEditable(isCellEditable());
				longEditor.showPopup();
			}
		});
    	return true; 
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.AttrCellEditor#preloadKeyEvent(java.awt.event.KeyEvent)
     */
    protected void preloadKeyEvent(KeyEvent anEvent){
    	int keyCode = anEvent.getKeyCode();
    	if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9 || 
    			keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z ||
    			keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9) {
    		// forward keystroke to editor
    		longEditor.preloadKeyEvent(anEvent);
    	}
    }
    /**
     * determine background color based on meta requirements
     * @param s
     * @return
     */
    protected Color getBackground(String s) {
    	if(!this.isCellEditable()){
    		return Color.WHITE;
    	}
    	return super.getBackground(s);
    }
    /**
     * return the edited value
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
    	return longEditor.getEditorValue(); 
    }
    /* (non-Javadoc)
     * return the component for use in joptionpane or form
     * @see com.ibm.eacm.editor.AttrCellEditor#getComponent()
     */
    public Component getComponent() {
    	return longEditor.getEditorComponent();
    }
    
    /**
     * hang onto the document listener, add it after attribute is set
     * @param dl
     */
    public void addDocumentListener(DocumentListener dl){
    	docListener = dl;
    }
	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//set the value in the editor here
		if (value instanceof LongTextAttribute) {
			setAttribute((LongTextAttribute) value);
		}
		return longEditor;
	} 
	/**
	 * set the attribute to use for this editor execution
	 * @param ean
	 */
	public void setAttribute(EANAttribute ean) {
		super.setAttribute(ean);
		if(docListener != null){
			((JTextComponent)getComponent()).getDocument().removeDocumentListener(docListener);
		}
		longEditor.setAttribute(ean);
		if (ean != null) {
			EANMetaAttribute meta = getAttribute().getMetaAttribute();
			spellCheckHandler.setSpellCheckRequired(meta.isSpellCheckable());
		}
		if(docListener != null){
			((JTextComponent)getComponent()).getDocument().addDocumentListener(docListener);
		}
	}
	/**
	 * release memory
	 */
	public void dereference(){
		spellCheckHandler.dereference();
		spellCheckHandler = null;
		
		if(docListener!=null){
			((JTextComponent)getComponent()).getDocument().removeDocumentListener(docListener);
			docListener = null;
		}
		longEditor.dereference();
		longEditor = null;
    	
    	super.dereference();
	}
}
