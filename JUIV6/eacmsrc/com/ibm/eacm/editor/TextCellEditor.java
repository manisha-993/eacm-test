//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.edit.SpellCheckHandler;
import com.ibm.eacm.edit.SpellCheckWrapper;
import com.ibm.eacm.edit.form.FormCellPanel;
import com.wallstreetwise.app.jspell.domain.JSpellParser;

import java.util.EventListener;
import java.awt.event.*;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.TextAttribute;

/***************
 * 
 * Cell editor for TextAttribute 
 * - used in CrossTable and SearchTable and verttable2 and gridtable
 * @author Wendy Stimpson
 */
//$Log: TextCellEditor.java,v $
//Revision 1.8  2013/11/08 20:15:41  wendy
//only remove trailing new lines, cant trim()
//
//Revision 1.7  2013/11/07 20:43:52  wendy
//trim before text is replaced
//
//Revision 1.6  2013/11/06 20:57:29  wendy
//trim the pasted value
//
//Revision 1.5  2013/10/08 21:12:30  wendy
//make sure cursor is visible if launched with a keystroke
//
//Revision 1.4  2013/08/14 16:54:18  wendy
//paste has cell listener to support type after paste
//
//Revision 1.3  2013/05/23 14:48:53  wendy
//remove leading 0 check  - meta is inconsistent
//
//Revision 1.2  2013/05/22 11:47:02  wendy
//limit leading 0 for numeric only attrs
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class TextCellEditor extends AttrCellEditor implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Text textComponent = new Text();
	private SpellCheckHandler spellCheckHandler = null;
	private DocumentListener docListener = null;

	//===============added for using in a form
	public void setForeground(Color fg) {
		textComponent.setForeground(fg);
	}
	public void setFont(Font font) {
		textComponent.setFont(font);
	}
    public void setOpaque(boolean isOpaque) {
    	textComponent.setOpaque(isOpaque);
    }
    
	public void setFormCellPanel(FormCellPanel f) {textComponent.setFormCellPanel(f);};// added for formeditor
	public FormCellPanel getFormCellPanel(){// added for formeditor
		return textComponent.getFormCellPanel();
	}
	
	//====================================
	/**
	 * constructor
	 */
	public TextCellEditor() {
		this(false);
	}
	
	/**
	 * constructor
	 */
	public TextCellEditor(boolean insearch) {
		super(insearch);
		textComponent.addActionListener(this);
		
		// create spellcheckhandler
		JSpellParser sp = new JSpellParser(EACM.getEACM().getDictionary());
		spellCheckHandler = new SpellCheckHandler(sp,new SpellCheckWrapper(this));
	}
	private KeyEvent preloadedKeyEvt;
    /**
     * derived classed can preload a key event
     * @param anEvent
     */
    protected void preloadKeyEvent(KeyEvent anEvent){
    	preloadedKeyEvt = anEvent;
    }
	//--- needed for copy/paste support
    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * with standard keybindings
     * @param act
     * @param keystroke
     */
    public void registerEACMAction(EACMAction act, KeyStroke keystroke){
    	// must get to WHEN_FOCUSED input map too for textfields 
    	textComponent.getInputMap().put(keystroke, act.getActionKey());
       	textComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystroke, act.getActionKey());
       	textComponent.getActionMap().put(act.getActionKey(), act);		
    }
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keystroke
     */
    public void unregisterEACMAction(EACMAction act,KeyStroke keystroke){
    	textComponent.getInputMap().remove(keystroke);
    	textComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(keystroke);
    	textComponent.getActionMap().remove(act.getActionKey());
    }
    /**
     * perform the cut action
     */
    public void cut(){
    	textComponent.cut();
    }
	/**
	 * get the item selected - used in vert and grid edit
	 * @return
	 */
	public Object getSelection() { 
		String selection = textComponent.getSelectedText(); 
		String attrval = getAttribute().toString();
		//if user has not hit 'ok' the contents are not in the attribute, so copy will not have the value to paste
		if(attrval.equals(selection)){
			return getAttribute();
		}
		
		return selection;
	}
	
	//--- end needed for copy/paste support
    /* (non-Javadoc)
     * return the component for use in joptionpane
     * @see com.ibm.eacm.editor.AttrCellEditor#getComponent()
     */
    public Component getComponent() {
    	return textComponent;
    }
    
    /**
     * hang onto the document listener, add it after attribute is set
     * @param dl
     */
    public void addDocumentListener(DocumentListener dl){
    	docListener = dl;
    }
	/**
	 * @return
	 */
	public SpellCheckHandler getSpellCheckHandler(){
		return spellCheckHandler;
	}
	/**
	 * use editor rules when pasting
	 * @param pasteObj
	 * @return
	 */
	public boolean paste(Object pasteObj,boolean editOpen){
		StringBuilder sb = new StringBuilder();
		String data = trimTrailingNewlines(pasteObj.toString());
		int selstart = 0;
		String curtext = textComponent.getText();
		
		if(editOpen){
			// build a new string representing the text after paste is done
	
			// is the entire string selected?
			selstart = textComponent.getSelectionStart();
			int selend = textComponent.getSelectionEnd();
			
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

		String pasteValue = sb.toString();
		
		// check to see if any errors would be flagged when inserting into the doc
		boolean textok = charValidation("",pasteValue);
		if(textok){
			textComponent.setText(pasteValue);
			selstart+=data.length();
			if(selstart<=pasteValue.length()){
				textComponent.setCaretPosition(selstart);
			}
			
			textComponent.requestFocusInWindow();
		}

		return textok;
	}
    
    /**
     * called if the user clicks on a different cell, should hide the dialog here
     * returning true tells the table to use the partially edited value, that it is valid
     * @see javax.swing.AbstractCellEditor#stopCellEditing()
     */
    public boolean stopCellEditing() { 
     	// check any meta length rules, character validation was done as it was entered
    	// and display error dialog
		if(!validateEdit(true)){
			return false;
		}

       	return super.stopCellEditing(); // this fires the event to all listeners	
    }

    /**
     * if copied from excel, there is a trailing newline - remove it, cant trim, it removes spaces
     * @param content
     * @return
     */
    private String trimTrailingNewlines(String content){
    	if(content==null){
    		content ="";
    	}else {
    		//does it have a trailing newline? if so, remove it
    		if(content.endsWith(RETURN)){
    			int id = content.indexOf(RETURN);
    			content = content.substring(0,id);
    		}
    		if(content.endsWith(NEWLINE)){
    			int id = content.indexOf(NEWLINE);
    			content = content.substring(0,id);
    		}
    	}
		return content;
    }
	/**
	 * check if updates were valid to this attribute
	 * @param showmsg
	 * @return
	 */
	private boolean validateEdit(boolean showmsg){
     	// check any meta length rules, character validation was done as it was entered
    	// and display error dialog
       	boolean validEdit = checkLength(textComponent.getText(),showmsg);
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
	 * spell check is starting, remove the doc listeners
	 */
	public void spellCheckStarted(){
		if(docListener != null){
			textComponent.getDocument().removeDocumentListener(docListener);
		}	
	}
	/**
	 *  spell check is ending, restore the doc listeners
	 */
	public void spellCheckEnded(){
		if(docListener != null){
			textComponent.getDocument().addDocumentListener(docListener);
		}	
	}
    /**
     * get around focus issue with losing focus to longcellpanel
     * dont put up the error dialog until after focus is transferred
     * @return
     */
    public boolean canStopCellEditing(){
    	boolean valid = checkLength(textComponent.getText(),false);
    	if(valid){
     		if(hasNewChanges() && spellCheckHandler.isRequired()){
     			valid = false;
     		}
    	}
    	return valid;
    }
    
    /**
     * return the edited value
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
    	return textComponent.getText();
    }

	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {		
		//put into attr should be when edit is completed
		if (value instanceof TextAttribute) {
			setAttribute((TextAttribute) value);
		}
		return textComponent;
	} 

    /**
     * allow enter in textfield to stop edit
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
    	stopCellEditing();
    }
    
	/**
	 * set the attribute to use for this editor execution
	 * @param ean
	 */
	public void setAttribute(EANAttribute ean) {
		super.setAttribute(ean);
		String value = "";
		if (ean != null) {
			value = ean.toString();
			EANMetaAttribute meta = getAttribute().getMetaAttribute();
			spellCheckHandler.setSpellCheckRequired(meta.isSpellCheckable());
		}
		if(docListener != null){
			textComponent.getDocument().removeDocumentListener(docListener);
		}
		int caret = textComponent.getCaretPosition();
		textComponent.setDisplay(value);
		
		if(docListener != null){
			textComponent.getDocument().addDocumentListener(docListener);
		}
	
		if(caret>value.length()){ // dont go past end
			caret = 0;
		}
		textComponent.setCaretPosition(caret);
	}
	/**
	 * release memory
	 */
	public void dereference(){
		spellCheckHandler.dereference();
		spellCheckHandler = null;
		
	   	EventListener listeners[] = textComponent.getListeners(ActionListener.class);
    	if (listeners!=null){
    		for(int ii=0; ii<listeners.length; ii++) {
    			textComponent.removeActionListener((ActionListener)listeners[ii]);
    			listeners[ii]=null;
    		}
    	}
		if(docListener != null){
			textComponent.getDocument().removeDocumentListener(docListener);
			docListener = null;
		}
    	textComponent.dereference();
    	textComponent = null;
    	
    	super.dereference();
	}
	
	/**
	 * do any character validation as user keys it in
	 * @param addedStr
	 * @return
	 */
	private boolean charValidation(String addedStr){
		return super.charValidation(textComponent.getText(), addedStr);
	}

	/***************
	 * 
	 * used by the cell editor to display the values
	 *
	 */
    private class Text extends JTextField implements EditorComponent {
		private static final long serialVersionUID = 1L;
		private int colwidth = 0;
		private boolean byPassValidation = false;
		private FormCellPanel fcp = null; // added for formeditor
		void setFormCellPanel(FormCellPanel f) {fcp=f;}
		public FormCellPanel getFormCellPanel() { return fcp;}// added for formeditor
		
		Text(){
			setAutoscrolls(true);
		}
		/* (non-Javadoc)
		 * @see javax.swing.JComponent#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
		 */
		protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
			//key typed events go to editor
			if(preloadedKeyEvt != null && e.getID() == KeyEvent.KEY_TYPED){
				// make sure cursor position is at the end of the text
				setCaretPosition(getDocument().getLength());
				requestFocusInWindow(); // make sure cursor is visible
				if(e.getKeyChar()==KeyEvent.VK_SPACE &&
						preloadedKeyEvt.getKeyCode()==KeyEvent.VK_SPACE){
					// dont forward the space used to open the editor to the textfield	
					preloadedKeyEvt = null;
					return true;
				}

				preloadedKeyEvt = null;
			}

			return super.processKeyBinding(ks, e, condition, pressed);
		}
		
		void setDisplay(String s) {
			byPassValidation = true;
			setText(s);
			byPassValidation = false;
		}

		void dereference(){
			setUI(null);
			removeAll();
		}
    	//must do it like this or List in renderer doesnt have it
        public Color getBackground() {
        	if(this.getDocument()==null){
        		return super.getBackground(); 
        	}

        	return TextCellEditor.this.getBackground(getText());
        } 
        
        /* (non-Javadoc)
         * paste comes thru here if there is a selection
         * @see javax.swing.text.JTextComponent#replaceSelection(java.lang.String)
         */
        public void replaceSelection(String content) {
        	super.replaceSelection(trimTrailingNewlines(content));
        }
        /**
         * @see java.awt.Component#getPreferredSize()
         */
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            int minWidth = 4 * getColumnWidth();
            if (d.width < minWidth) {
                d.width = minWidth;
            }
            return d;
        }

        /**
         * @see javax.swing.JTextField#getColumnWidth()
         */
        protected int getColumnWidth() {
        	if (colwidth==0){
        		FontMetrics metrics = getFontMetrics(getFont());
        		colwidth= metrics.charWidth('W');
        	}
        	return colwidth;
        }
        /**
         * Sets the current font.  This removes cached column
         * width so the new font will be reflected. 
         * @param f the new font
         */
        public void setFont(Font f) {
            super.setFont(f);
            colwidth = 0;
        }

        /**
         * character validation
         *
         * @return Document
         */
        protected Document createDefaultModel() {
            return new MetaDoc();
        }

        private class MetaDoc extends PlainDocument {
        	private static final long serialVersionUID = 1L;
        	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        		if (str != null) {
        			if (byPassValidation) {
        				super.insertString(offs, str, a);
        			} else if (charValidation(str)) {	
        				// force replacement of leading 0 if this is an integer field
        				//if(isInteger() && (!isAlpha()) && this.getText(0, getLength()).equals("0")){
        				//	super.replace(0, getLength(), str, a);  FCTRANS.FROMMACHTYPE
        				//is integer and !alpha but needs to support 0018
        				//}else{
        					super.insertString(offs, str, a);
        				//}
        			}
        			repaint();
        		}
            }
        }
    }
}
